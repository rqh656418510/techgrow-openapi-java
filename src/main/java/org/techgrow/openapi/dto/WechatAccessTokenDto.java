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

package org.techgrow.openapi.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author clay
 * @date 2023-05-04
 */
@Data
@ToString
@NoArgsConstructor
public class WechatAccessTokenDto implements Serializable {

	/**
	 * 凭据
	 */
	@JSONField(name = "access_token")
	private String token;

	/**
	 * 有效时间（秒）
	 */
	@JSONField(name = "expires_in")
	private Long expireIn;

	/**
	 * 创建时间
	 */
	@JSONField(name = "create_time")
	private Date createTime;

}
