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

package org.techgrow.openapi.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.techgrow.openapi.enums.BizCode;
import org.techgrow.openapi.exception.GlobalException;
import org.techgrow.openapi.utils.R;

import java.util.HashMap;
import java.util.Map;

/**
 * @author clay
 * @date 2023-04-28
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = BindException.class)
	@ResponseStatus(code = HttpStatus.OK)
	public R handleValidateException(Exception e) {
		log.error("Error message : ", e);
		Map<String, String> data = new HashMap<>();
		BindException bindException = (BindException) e;
		BindingResult validResult = bindException.getBindingResult();
		if (validResult.hasErrors()) {
			validResult.getFieldErrors().forEach(item -> {
				String errorMsg = item.getDefaultMessage();
				String fieldName = item.getField();
				data.put(fieldName, errorMsg);
			});
		}
		return R.error(BizCode.ILLEGAL_PARAMETER_ERROR).setData(data);
	}

	@ExceptionHandler(value = GlobalException.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public R handleGlobalException(Exception e) {
		log.error("Error message : ", e);
		GlobalException globalException = (GlobalException) e;
		BizCode errorCode = globalException.getErrorCode();
		if (errorCode == null) {
			errorCode = BizCode.INTERNAL_SYSTEM_ERROR;
		}
		return R.error(errorCode);
	}

	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public R handleException(Exception e) {
		log.error("Error message : ", e);
		return R.error(BizCode.INTERNAL_SYSTEM_ERROR);
	}

}
