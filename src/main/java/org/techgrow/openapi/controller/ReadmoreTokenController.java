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

package org.techgrow.openapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.techgrow.openapi.dto.ReadmoreTokenDto;
import org.techgrow.openapi.service.ReadmoreTokenService;
import org.techgrow.openapi.utils.R;

import javax.validation.Valid;

/**
 * @author clay
 * @date 2023-04-28
 */
@RestController
@RequestMapping("/api/readmore/token")
public class ReadmoreTokenController {

	@Autowired
	private ReadmoreTokenService tokenService;

	/**
	 * Validate token
	 * @param dto
	 * @return
	 */
	@PostMapping("/validate")
	public R validate(@Valid @RequestBody ReadmoreTokenDto dto) {
		return tokenService.validate(dto);
	}

}
