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

package org.techgrow.openapi.exception;

import org.techgrow.openapi.enums.BizCode;

/**
 * @author clay
 * @date 2023-04-28
 */
public class GlobalException extends RuntimeException {

	private final BizCode errorCode;

	public GlobalException() {
		this.errorCode = BizCode.INTERNAL_SYSTEM_ERROR;
	}

	public GlobalException(BizCode errorCode) {
		super(errorCode.getDescription());
		this.errorCode = errorCode;
	}

	public GlobalException(String message) {
		super(message);
		this.errorCode = BizCode.INTERNAL_SYSTEM_ERROR.setDescription(message);
	}

	public BizCode getErrorCode() {
		return errorCode;
	}

}
