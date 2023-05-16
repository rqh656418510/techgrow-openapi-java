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
 * 阅读更多的凭证
 *
 * @author clay
 */
@Data
@ToString
@NoArgsConstructor
@TableName("tb_readmore_token")
public class ReadmoreToken implements Serializable {

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 博客 ID
	 */
	private String blogId;

	/**
	 * 凭证
	 */
	private String token;

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

	/**
	 * 更新时间
	 */
	private Date updateTime;

	public ReadmoreToken(String blogId, String token, Date expireTime) {
		this.blogId = blogId;
		this.token = token;
		this.expireTime = expireTime;
	}

}
