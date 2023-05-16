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

package org.techgrow.openapi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 阅读更多的验证码
 *
 * @author clay
 */
@Data
@ToString
@NoArgsConstructor
@TableName("tb_readmore_captcha")
public class ReadmoreCaptcha implements Serializable {

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 博客ID
	 */
	private String blogId;

	/**
	 * 验证码
	 */
	private String code;

	/**
	 * 被关注的微信用户ID
	 */
	private String toOpenId;

	/**
	 * 发起关注的微信用户ID
	 */
	private String fromOpenId;

	/**
	 * 过期时间
	 */
	private Date expireTime;

}