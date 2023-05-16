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

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import org.techgrow.openapi.enums.BizCode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author clay
 * @date 2023-04-28
 */
public class R extends HashMap<String, Object> {

	public static final String KEY_MSG = "msg";

	public static final String KEY_CODE = "code";

	public static final String KEY_DATA = "data";

	public R() {
		put(KEY_CODE, 0);
		put(KEY_MSG, "success");
	}

	public static R error() {
		return error(BizCode.INTERNAL_SYSTEM_ERROR);
	}

	public static R error(BizCode errorCode) {
		int code = errorCode.getValue();
		String msg = errorCode.getDescription();
		return error(code, msg);
	}

	public static R error(int code, String msg) {
		R r = new R();
		r.put(KEY_CODE, code);
		r.put(KEY_MSG, msg);
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		r.put(KEY_MSG, msg);
		return r;
	}

	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}

	public static R ok() {
		return new R();
	}

	@Override
	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}

	public R setData(Object data) {
		put(KEY_DATA, data);
		return this;
	}

	public String getMssage() {
		return (String) this.get(KEY_MSG);
	}

	public Integer getCode() {
		return Integer.parseInt(this.get(KEY_CODE).toString());
	}

	public <T> T getData(TypeReference<T> typeReference) {
		Object data = get(KEY_DATA);
		String jsonStr = JSON.toJSONString(data);
		return JSON.parseObject(jsonStr, typeReference);
	}

	public <T> T getData(String key, TypeReference<T> typeReference) {
		Object data = get(key);
		String jsonStr = JSON.toJSONString(data);
		return JSON.parseObject(jsonStr, typeReference);
	}

	public boolean checkOk() {
		return this.getCode() == 0;
	}

}
