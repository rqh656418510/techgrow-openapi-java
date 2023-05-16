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

package org.techgrow.openapi.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author clay
 * @date 2023-04-28
 */
@Data
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReadmoreBrowseVo implements Serializable {

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 博客 ID
	 */
	private String blogId;

	/**
	 * 随机的概率
	 */
	private Double probability;

	/**
	 * IP 地址
	 */
	private String ip;

	/**
	 * 浏览器 UA
	 */
	private String ua;

	/**
	 * 来源 URL
	 */
	private String url;

	/**
	 * 来源标题
	 */
	private String title;

	/**
	 * 浏览标识
	 * <ul>
	 * <li>1 - 凭证解锁浏览</li>
	 * <li>2 - 随机解锁浏览</li>
	 * <li>3 - 验证码解锁浏览</li>
	 * <li>4 - 阀值解锁浏览</li>
	 * </ul>
	 */
	private int tag;

	/**
	 * 创建时间
	 */
	private Date createTime;

}