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

/**
 * @author clay
 * @date 2023-05-09
 */
@Data
@ToString
@NoArgsConstructor
public class PageDto {

	/**
	 * 当前页码
	 */
	private long currPage = 1;

	/**
	 * 每页显示记录数
	 */
	private long pageSize = 10;

	/**
	 * 排序字段
	 */
	private String orderField;

	/**
	 * 排序方式
	 */
	private String orderMode;

	/**
	 * 升序排序
	 */
	public static final String ASC = "asc";

}
