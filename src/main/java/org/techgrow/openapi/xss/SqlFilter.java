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

package org.techgrow.openapi.xss;

import cn.hutool.core.util.StrUtil;
import org.techgrow.openapi.exception.GlobalException;

/**
 * @author clay
 * @date 2023-05-09
 */
public class SqlFilter {

	/**
	 * SQL 注入过滤
	 * @param str 待验证的字符串
	 */
	public static String sqlInject(String str) {
		if (StrUtil.isBlank(str)) {
			return null;
		}

		// 去掉'|"|;|\字符
		str = StrUtil.replace(str, "'", "");
		str = StrUtil.replace(str, "\"", "");
		str = StrUtil.replace(str, ";", "");
		str = StrUtil.replace(str, "\\", "");

		// 转换成小写
		str = str.toLowerCase();

		// 非法字符
		String[] keywords = { "master", "truncate", "insert", "select", "delete", "update", "declare", "alter",
				"drop" };

		// 判断是否包含非法字符
		for (String keyword : keywords) {
			if (str.contains(keyword)) {
				throw new GlobalException("包含非法字符");
			}
		}

		return str;
	}

}
