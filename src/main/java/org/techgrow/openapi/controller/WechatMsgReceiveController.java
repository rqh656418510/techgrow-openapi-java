/*
 * The Java implementation of TechGrow openapi.
 * Copyright (C) 2023-present, open.techgrow.cn
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.techgrow.openapi.controller;

import cn.hutool.core.util.StrUtil;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.techgrow.openapi.constants.WechatReplyContent;
import org.techgrow.openapi.entity.WechatAccessToken;
import org.techgrow.openapi.handler.WechatReadmoreMsgHandler;
import org.techgrow.openapi.handler.WechatSubscribeMsgHandler;
import org.techgrow.openapi.handler.WechatUnsubscribeMsgHandler;
import org.techgrow.openapi.properties.ReadmoreProperties;
import org.techgrow.openapi.properties.WechatProperties;
import org.techgrow.openapi.service.WechatAccessTokenService;
import org.techgrow.openapi.utils.WechatMsgUtils;
import weixin.popular.bean.message.EventMessage;
import weixin.popular.support.ExpireKey;
import weixin.popular.support.expirekey.DefaultExpireKey;
import weixin.popular.util.SignatureUtil;
import weixin.popular.util.StreamUtils;
import weixin.popular.util.XMLConverUtil;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * @author clay
 * @date 2023-05-04
 */
@Slf4j
@Controller
@RequestMapping("/api/readmore/wechat")
public class WechatMsgReceiveController {

	@Autowired
	private WechatProperties wechatProperties;

	@Autowired
	private ReadmoreProperties readmoreProperties;

	@Autowired
	private WechatAccessTokenService accessTokenService;

	@Autowired
	private WechatReadmoreMsgHandler readmoreMsgHandler;

	@Autowired
	private WechatSubscribeMsgHandler subscribeMsgHandler;

	@Autowired
	private WechatUnsubscribeMsgHandler unsubscribeMsgHandler;

	private static final ExpireKey expireKey = new DefaultExpireKey();

	/**
	 * Validate token
	 * @param request
	 * @param response
	 */
	@GetMapping("/receive")
	protected void verifyToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServletOutputStream outputStream = response.getOutputStream();
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");

		if (echostr != null) {
			outputStreamWrite(outputStream, echostr);
			return;
		}

		// verify request signature
		if (!signature.equals(SignatureUtil.generateEventMessageSignature(getAccessToken(), timestamp, nonce))) {
			log.error("The wechat request signature is invalid");
		}
		else {
			outputStreamWrite(outputStream, echostr);
		}
	}

	/**
	 * Receive wechat message (encryption mode)
	 * @param request
	 * @param response
	 */
	@PostMapping("/receive")
	protected void receiveMsg(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServletInputStream inputStream = request.getInputStream();
		ServletOutputStream outputStream = response.getOutputStream();
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		String encrypt_type = request.getParameter("encrypt_type");
		String msg_signature = request.getParameter("msg_signature");

		WXBizMsgCrypt wxBizMsgCrypt = null;
		boolean isAes = "aes".equals(encrypt_type);
		if (isAes) {
			try {
				wxBizMsgCrypt = new WXBizMsgCrypt(wechatProperties.getAppToken(), wechatProperties.getEncodingAesKey(),
						wechatProperties.getAppId());
			}
			catch (AesException e) {
				log.error(e.getLocalizedMessage());
			}
		}

		if (isAes && echostr != null) {
			try {
				echostr = URLDecoder.decode(echostr, "utf-8");
				String echostr_decrypt = wxBizMsgCrypt.verifyUrl(msg_signature, timestamp, nonce, echostr);
				outputStreamWrite(outputStream, echostr_decrypt);
				return;
			}
			catch (AesException e) {
				log.error(e.getLocalizedMessage());
			}
		}
		else if (echostr != null) {
			outputStreamWrite(outputStream, echostr);
			return;
		}

		EventMessage eventMessage = null;
		if (isAes) {
			try {
				String postData = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
				String xmlData = wxBizMsgCrypt.decryptMsg(msg_signature, timestamp, nonce, postData);
				eventMessage = XMLConverUtil.convertToObject(EventMessage.class, xmlData);
			}
			catch (AesException e) {
				log.error(e.getLocalizedMessage());
			}
		}
		else {
			// verify request signature
			if (!signature.equals(SignatureUtil.generateEventMessageSignature(getAccessToken(), timestamp, nonce))) {
				log.error("The wechat request signature is invalid");
				return;
			}

			if (inputStream != null) {
				eventMessage = XMLConverUtil.convertToObject(EventMessage.class, inputStream);
			}
		}

		String key = eventMessage.getFromUserName() + "__" + eventMessage.getToUserName() + "__"
				+ eventMessage.getMsgId() + "__" + eventMessage.getCreateTime();
		if (expireKey.exists(key)) {
			return;
		}
		else {
			expireKey.add(key);
		}

		// subscribe message
		if (WechatMsgUtils.isSubscribeMsg(eventMessage)) {
			subscribeMsgHandler.handler(outputStream, wxBizMsgCrypt, eventMessage);
			return;
		}

		// unsubscribe message
		if (WechatMsgUtils.isUnsubscribeMsg(eventMessage)) {
			unsubscribeMsgHandler.handler(outputStream, wxBizMsgCrypt, eventMessage);
			return;
		}

		// readmore message
		if (WechatMsgUtils.isTextMsg(eventMessage)
				&& WechatMsgUtils.matchMsgKey(eventMessage, readmoreProperties.getCaptcha().getReplyKeyword())) {
			readmoreMsgHandler.handler(outputStream, wxBizMsgCrypt, eventMessage);
			return;
		}

		// default message
		WechatMsgUtils.sendTextMessage(outputStream, wxBizMsgCrypt, eventMessage, WechatReplyContent.INVALID_KEYWORD);
	}

	/**
	 * Data flow output
	 * @param outputStream
	 * @param content
	 * @return
	 */
	private boolean outputStreamWrite(OutputStream outputStream, String content) {
		try {
			outputStream.write(content.getBytes(StandardCharsets.UTF_8));
		}
		catch (IOException e) {
			log.error(e.getLocalizedMessage());
			return false;
		}
		return true;
	}

	/**
	 * Get Access Token
	 * @return
	 */
	private String getAccessToken() {
		WechatAccessToken accessToken = accessTokenService.get();
		if (accessToken != null && StrUtil.isNotBlank(accessToken.getToken())) {
			return accessToken.getToken();
		}
		return "";
	}

}
