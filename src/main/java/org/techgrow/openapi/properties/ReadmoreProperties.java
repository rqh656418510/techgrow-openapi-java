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

package org.techgrow.openapi.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author clay
 * @date 2023-05-04
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "techgrow.openapi.readmore")
public class ReadmoreProperties {

	/**
	 * 博客 ID
	 */
	private String blogId;

	/**
	 * 凭证
	 */
	private final Token token = new Token();

	/**
	 * 验证码
	 */
	private final Captcha captcha = new Captcha();

	@Data
	public static class Token {

		/**
		 * 凭证的有效天数
		 */
		private int expireDays;

		/**
		 * 凭证的签名密钥（86个字符以上）
		 */
		private String signKey;

	}

	@Data
	public static class Captcha {

		/**
		 * 验证码的长度
		 */
		private int length;

		/**
		 * 验证码的有效时间
		 */
		private long expiresValue;

		/**
		 * 验证码有效时间的单位
		 */
		private String expiresUnit;

		/**
		 * 获取验证码的API地址
		 */
		private String apiAddress;

		/**
		 * 获取验证码的公众号回复关键词
		 */
		private String replyKeyword;

	}

}
