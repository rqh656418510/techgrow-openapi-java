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
import org.techgrow.openapi.dto.ReadmoreTokenDto;
import org.techgrow.openapi.entity.ReadmoreToken;
import org.techgrow.openapi.utils.R;
import org.techgrow.openapi.vo.ReadmoreTokenVo;

/**
 * @author clay
 * @date 2023-05-03
 */
public interface ReadmoreTokenService extends IService<ReadmoreToken> {

	/**
	 * Generate token
	 * @param blogId
	 * @param code
	 * @return
	 */
	ReadmoreTokenVo generate(String blogId, String code);

	/**
	 * Validate token
	 * @param dto
	 * @return
	 */
	R validate(ReadmoreTokenDto dto);

	/**
	 * Check token existed
	 * @param token
	 * @return
	 */
	boolean checkExisted(String token);

}
