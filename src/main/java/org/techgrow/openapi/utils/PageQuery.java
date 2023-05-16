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

package org.techgrow.openapi.utils;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.techgrow.openapi.dto.PageDto;
import org.techgrow.openapi.xss.SqlFilter;

/**
 * @author clay
 * @date 2023-05-09
 */
public class PageQuery<T> {

	public IPage<T> getPage(PageDto pagetDto) {
		return this.getPage(pagetDto, null, false);
	}

	public IPage<T> getPage(PageDto pagetDto, String defaultOrderField, boolean isAsc) {
		// 分页参数
		long currPage = pagetDto.getCurrPage();
		long pageSize = pagetDto.getPageSize();

		// 分页对象
		Page<T> page = new Page<>(currPage, pageSize);

		// 排序字段（防止SQL注入）
		String orderField = SqlFilter.sqlInject(pagetDto.getOrderField());
		String orderMode = pagetDto.getOrderMode();

		// 前端字段排序
		if (StrUtil.isNotBlank(orderField) && StrUtil.isNotBlank(orderMode)) {
			if (PageDto.ASC.equalsIgnoreCase(orderMode)) {
				return page.addOrder(OrderItem.asc(orderField));
			}
			else {
				return page.addOrder(OrderItem.desc(orderField));
			}
		}

		// 若没有默认排序字段，则不排序
		if (StrUtil.isBlank(defaultOrderField)) {
			return page;
		}

		// 默认排序
		if (isAsc) {
			page.addOrder(OrderItem.asc(defaultOrderField));
		}
		else {
			page.addOrder(OrderItem.desc(defaultOrderField));
		}

		return page;
	}

}
