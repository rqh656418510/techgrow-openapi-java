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

package org.techgrow.openapi.utils;

import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import org.techgrow.openapi.enums.WechatEventType;
import org.techgrow.openapi.enums.WechatMsgType;
import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;

import java.io.OutputStream;

/**
 * @author clay
 * @date 2023-05-04
 */
public class WechatMsgUtils {

	/**
	 * 是否为文本消息
	 * @param msg
	 * @return
	 */
	public static boolean isTextMsg(EventMessage msg) {
		return WechatMsgType.TEXT.match(msg.getMsgType());
	}

	/**
	 * 是否为关注消息
	 * @param msg
	 * @return
	 */
	public static boolean isSubscribeMsg(EventMessage msg) {
		return WechatMsgType.EVENT.match(msg.getMsgType()) && WechatEventType.SUBSCRIBE.match(msg.getEvent());
	}

	/**
	 * 是否为取消关注消息
	 * @param msg
	 * @return
	 */
	public static boolean isUnsubscribeMsg(EventMessage msg) {
		return WechatMsgType.EVENT.match(msg.getMsgType()) && WechatEventType.UNSUBSCRIBE.match(msg.getEvent());
	}

	/**
	 * 匹配消息关键词
	 * <p>
	 * 默认忽略大小写
	 * @param msg
	 * @param expectKey
	 * @return
	 */
	public static boolean matchMsgKey(EventMessage msg, String expectKey) {
		return matchMsgKey(msg, expectKey, true);
	}

	/**
	 * 匹配消息关键词
	 * @param msg
	 * @param expectKey
	 * @param ignoreCase
	 * @return
	 */
	public static boolean matchMsgKey(EventMessage msg, String expectKey, boolean ignoreCase) {
		if (!ignoreCase) {
			return expectKey.equals(msg.getContent().trim());
		}
		else {
			return expectKey.equalsIgnoreCase(msg.getContent().trim());
		}
	}

	/**
	 * 发送文本消息
	 * @param outputStream
	 * @param msgCrypt
	 * @param msg
	 * @param msgText
	 */
	public static void sendTextMessage(OutputStream outputStream, WXBizMsgCrypt msgCrypt, EventMessage msg,
			String msgText) {
		XMLMessage xmlTextMessage = new XMLTextMessage(msg.getFromUserName(), msg.getToUserName(), msgText);
		xmlTextMessage.outputStreamWrite(outputStream, msgCrypt);
	}

}