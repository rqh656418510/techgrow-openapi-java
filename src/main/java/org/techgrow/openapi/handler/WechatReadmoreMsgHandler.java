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

package org.techgrow.openapi.handler;

import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.techgrow.openapi.constants.WechatReplyContent;
import org.techgrow.openapi.entity.WechatSubscribeUser;
import org.techgrow.openapi.properties.ReadmoreProperties;
import org.techgrow.openapi.service.WechatSubscribeUserService;
import org.techgrow.openapi.utils.WechatMsgUtils;
import weixin.popular.bean.message.EventMessage;

import javax.servlet.ServletOutputStream;

/**
 * @author clay
 * @date 2023-05-04
 */
@Slf4j
@Component
public class WechatReadmoreMsgHandler extends WechatAbstractMsgHandler {

	@Autowired
	private ReadmoreProperties readmoreProperties;

	@Autowired
	private WechatSubscribeUserService subscribeUserService;

	@Override
	public void handler(ServletOutputStream outputStream, WXBizMsgCrypt wxBizMsgCrypt, EventMessage message) {
		String toOpenId = message.getToUserName();
		String fromOpenId = message.getFromUserName();
		String blogId = readmoreProperties.getBlogId();

		WechatSubscribeUser subscribeUser = subscribeUserService.getUser(fromOpenId, toOpenId);
		if (subscribeUser != null && subscribeUser.getUpdateTime() != null) {
			String msgText = WechatReplyContent.ONCE_UNSUBSCRIBE;
			WechatMsgUtils.sendTextMessage(outputStream, wxBizMsgCrypt, message, msgText);
		}
		else {
			String captchaApi = readmoreProperties.getCaptcha().getApiAddress();
			String urlTemplate = "<a href=\"%s?blogId=%s&fromOpenId=%s&toOpenId=%s\">%s</a>";
			String msgText = String.format(urlTemplate, captchaApi, blogId, fromOpenId, toOpenId,
					WechatReplyContent.GET_CAPTCHA);
			WechatMsgUtils.sendTextMessage(outputStream, wxBizMsgCrypt, message, msgText);
		}
	}

}