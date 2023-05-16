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
 * @date 2023-04-28
 */
public enum BizCode implements BaseEnum<BizCode, Integer> {

	/**
	 * 非法请求来源
	 */
	ILLEGAL_SOURCE(403, "Illegal source"),

	/**
	 * 无效验证码
	 */
	INVALID_CAPTCHA(406, "Invalid captcha"),

	/**
	 * 无效博客 ID
	 */
	INVALID_BLOG_ID(407, "Invalid blog id"),

	/**
	 * 无效 Token
	 */
	INVALID_TOKEN(408, "Invalid token"),

	/**
	 * 微信公众号未关注
	 */
	WECHAT_UNSUBSCRIBE(409, "Wechat unsubscribe"),

	/**
	 * 微信公众号取消关注
	 */
	WECHAT_CANCEL_SUBSCRIBE(410, "Wechat cancel subscribe"),

	/**
	 * 内部系统错误
	 */
	INTERNAL_SYSTEM_ERROR(500, "Internal system error"),

	/**
	 * 非法参数错误
	 */
	ILLEGAL_PARAMETER_ERROR(502, "Illegal parameter error");

	private final int value;

	private String description;

	BizCode(int value, String description) {
		this.value = value;
		this.description = description;
	}

	@Override
	public Integer getValue() {
		return this.value;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	public BizCode setDescription(String description) {
		this.description = description;
		return this;
	}

}