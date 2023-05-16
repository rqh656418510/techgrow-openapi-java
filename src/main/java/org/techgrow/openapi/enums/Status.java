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

package org.techgrow.openapi.enums;

/**
 * @author clay
 * @date 2023-05-12
 */
public enum Status implements BaseEnum<Status, Integer> {

	/**
	 * 有效状态
	 */
	VALID(1, "Valid"),

	/**
	 * 无效状态
	 */
	INVALID(0, "Invalid");

	private final int value;

	private final String description;

	Status(int value, String description) {
		this.value = value;
		this.description = description;
	}

	@Override
	public Integer getValue() {
		return this.value;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	public boolean equals(Status status) {
		return this.value == status.getValue();
	}

}
