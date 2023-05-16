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

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.techgrow.openapi.enums.BaseEnum;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @author clay
 * @date 2023-05-12
 */
@Slf4j
@NoArgsConstructor
public class AutoGenericEnumTypeHandler<E extends BaseEnum> extends BaseTypeHandler<E> {

	private Class<E> enumType;

	private E[] enums;

	public AutoGenericEnumTypeHandler(Class<E> type) {
		if (Objects.isNull(type)) {
			throw new IllegalArgumentException("Type argument cannot be null");
		}
		this.enumType = type;
		this.enums = type.getEnumConstants();
		if (Objects.isNull(this.enums)) {
			throw new IllegalArgumentException(type.getName() + " does not represent an enum type");
		}
	}

	private E loadEnum(Object index) {
		for (E e : enums) {
			if (e.getValue().toString().equals(index.toString())) {
				return e;
			}
		}
		throw new IllegalArgumentException(enumType.getName() + " unknown enumerated type index : " + index);
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
		ps.setObject(i, parameter.getValue());
	}

	@Override
	public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
		if (Objects.isNull(rs.getObject(columnName))) {
			return null;
		}
		Object index = rs.getObject(columnName);
		return loadEnum(index);
	}

	@Override
	public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		if (Objects.isNull(rs.getObject(columnIndex))) {
			return null;
		}
		Object index = rs.getObject(columnIndex);
		return loadEnum(index);
	}

	@Override
	public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		if (Objects.isNull(cs.getObject(columnIndex))) {
			return null;
		}
		Object index = cs.getObject(columnIndex);
		return loadEnum(index);
	}

}