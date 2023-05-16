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
import org.techgrow.openapi.dto.WechatSubscribeUserDto;
import org.techgrow.openapi.entity.WechatSubscribeUser;
import org.techgrow.openapi.enums.Status;
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
public class WechatSubscribeMsgHandler extends WechatAbstractMsgHandler {

	@Autowired
	private WechatSubscribeUserService subscribeUserService;

	@Override
	public void handler(ServletOutputStream outputStream, WXBizMsgCrypt wxBizMsgCrypt, EventMessage message) {
		String msgText = "";
		String toOpenId = message.getToUserName();
		String fromOpenId = message.getFromUserName();

		WechatSubscribeUser subscribeUser = subscribeUserService.getUser(fromOpenId, toOpenId);
		if (subscribeUser != null) {
			msgText = WechatReplyContent.SUBSCRIBE_AGAIN;
			subscribeUserService.updateStatus(fromOpenId, toOpenId, Status.VALID);
		}
		else {
			msgText = WechatReplyContent.FIRST_SUBSCRIBE;
			WechatSubscribeUserDto user = new WechatSubscribeUserDto(fromOpenId, toOpenId);
			subscribeUserService.addUser(user);
		}
		WechatMsgUtils.sendTextMessage(outputStream, wxBizMsgCrypt, message, msgText);
	}

}
