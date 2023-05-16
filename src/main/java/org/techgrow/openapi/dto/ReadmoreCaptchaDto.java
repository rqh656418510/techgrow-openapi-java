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

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author clay
 * @date 2023-04-28
 */
@Data
@ToString
@NoArgsConstructor
public class ReadmoreCaptchaDto implements Serializable {

	/**
	 * 博客 ID
	 */
	@NotBlank(message = "博客ID不能为空")
	private String blogId;

	/**
	 * 验证码
	 */
	@NotBlank(message = "验证码不能为空")
	private String code;

	/**
	 * 被关注的微信用户ID
	 */
	private String toOpenId;

	/**
	 * 发起关注的微信用户ID
	 */
	private String fromOpenId;

}