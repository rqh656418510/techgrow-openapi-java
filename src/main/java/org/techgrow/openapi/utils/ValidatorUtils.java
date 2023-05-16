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

import org.techgrow.openapi.enums.BizCode;
import org.techgrow.openapi.exception.GlobalException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author clay
 * @date 2023-05-08
 */
public class ValidatorUtils {

	private static final Validator VALIDATOR;

	static {
		VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
	}

	/**
	 * Validate parameter
	 * @param object
	 * @param groups
	 * @throws GlobalException
	 */
	public static void validateParameter(Object object, Class<?>... groups) throws GlobalException {
		Set<ConstraintViolation<Object>> constraintViolations = VALIDATOR.validate(object, groups);
		if (!constraintViolations.isEmpty()) {
			StringBuilder message = new StringBuilder();
			for (ConstraintViolation<Object> constraint : constraintViolations) {
				message.append(constraint.getMessage()).append("\n");
			}
			BizCode errorCode = BizCode.ILLEGAL_PARAMETER_ERROR.setDescription(message.toString());
			throw new GlobalException(errorCode);
		}
	}

}
