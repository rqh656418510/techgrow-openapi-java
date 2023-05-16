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
@ConfigurationProperties(prefix = "techgrow.openapi.wechat")
public class WechatProperties {

	/**
	 * 开发者 ID
	 */
	private String appId;

	/**
	 * 开发者密码
	 */
	private String appSecret;

	/**
	 * 令牌
	 */
	private String appToken;

	/**
	 * 消息加解密密钥
	 */
	private String encodingAesKey;

}
