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

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.util.StrUtil;
import org.springframework.util.Assert;
import org.techgrow.openapi.exception.GlobalException;

import java.util.Date;

/**
 * @author clay
 * @date 2023-05-04
 */
public enum TimeUnit implements BaseEnum<TimeUnit, String> {

	/**
	 * 毫秒
	 */
	MICROS("Micros", "毫秒"),

	/**
	 * 秒
	 */
	SECONDS("Seconds", "秒"),

	/**
	 * 分钟
	 */
	MINUTES("Minutes", "分钟"),

	/**
	 * 小时
	 */
	HOURS("Hours", "小时"),

	/**
	 * 天
	 */
	DAYS("Days", "天"),

	/**
	 * 周
	 */
	WEEKS("Weeks", "周");

	private final String value;

	private final String description;

	TimeUnit(String value, String description) {
		this.value = value;
		this.description = description;
	}

	@Override
	public String getValue() {
		return this.value;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	/**
	 * Get time unit
	 * @param value
	 * @return
	 */
	public static TimeUnit getEnumByValue(String value) {
		if (StrUtil.isNotBlank(value)) {
			TimeUnit[] values = TimeUnit.values();
			for (TimeUnit unit : values) {
				if (unit.getValue().equalsIgnoreCase(value)) {
					return unit;
				}
			}
		}
		return null;
	}

	/**
	 * Get expire date
	 * @param expiresValue
	 * @param expiresUnit
	 * @return
	 */
	public static Date getExpiresDate(long expiresValue, String expiresUnit) {
		return getExpiresDate(expiresValue, getEnumByValue(expiresUnit));
	}

	/**
	 * Get expire date
	 * @param expiresValue
	 * @param expiresUnit
	 * @return
	 */
	public static Date getExpiresDate(long expiresValue, TimeUnit expiresUnit) {
		if (expiresUnit == null) {
			throw new GlobalException("Unknown time unit");
		}

		Date nowDate = new Date();
		switch (expiresUnit) {
			case MICROS:
				return new Date(nowDate.getTime() + DateUnit.MS.getMillis() * expiresValue);
			case SECONDS:
				return new Date(nowDate.getTime() + DateUnit.SECOND.getMillis() * expiresValue);
			case MINUTES:
				return new Date(nowDate.getTime() + DateUnit.MINUTE.getMillis() * expiresValue);
			case HOURS:
				return new Date(nowDate.getTime() + DateUnit.HOUR.getMillis() * expiresValue);
			case DAYS:
				return new Date(nowDate.getTime() + DateUnit.DAY.getMillis() * expiresValue);
			case WEEKS:
				return new Date(nowDate.getTime() + DateUnit.WEEK.getMillis() * expiresValue);
			default:
				throw new GlobalException("Unknown time unit");
		}
	}

}
