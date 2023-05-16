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

package org.techgrow.openapi.enums;

/**
 * @author clay
 * @date 2023-05-04
 */
public enum WechatMsgType implements BaseEnum<BizCode, String> {

	/**
	 * 文本消息
	 */
	TEXT("text", "Text Message"),

	/**
	 * 事件消息
	 */
	EVENT("event", "Event Message");

	private final String value;

	private final String description;

	WechatMsgType(String value, String description) {
		this.value = value;
		this.description = description;
	}

	@Override
	public String getValue() {
		return this.value;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	/**
	 * Match value
	 * @param value
	 * @return
	 */
	public boolean match(String value) {
		return this.value.equals(value);
	}

}
