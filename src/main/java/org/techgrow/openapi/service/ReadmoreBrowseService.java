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

package org.techgrow.openapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.techgrow.openapi.dto.PageDto;
import org.techgrow.openapi.dto.ReadmoreBrowseDto;
import org.techgrow.openapi.entity.ReadmoreBrowse;
import org.techgrow.openapi.utils.R;

/**
 * @author clay
 * @date 2023-05-03
 */
public interface ReadmoreBrowseService extends IService<ReadmoreBrowse> {

	/**
	 * Add
	 * @param dto
	 * @return R
	 */
	R add(ReadmoreBrowseDto dto);

	/**
	 * List
	 * @param blogId
	 * @param pageDto
	 * @return
	 */
	R list(String blogId, PageDto pageDto);

}
