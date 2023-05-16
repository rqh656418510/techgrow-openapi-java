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

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.techgrow.openapi.dto.WechatAccessTokenDto;
import org.techgrow.openapi.properties.WechatProperties;

/**
 * @author clay
 * @date 2023-05-04
 */
@Slf4j
@Component
public class WechatApiUtils {

	@Autowired
	private WechatProperties wechatProperties;

	public static final String API_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

	/**
	 * Get access token
	 */
	public WechatAccessTokenDto getAccessToken() {
		try {
			String url = String.format(API_ACCESS_TOKEN, wechatProperties.getAppId(), wechatProperties.getAppSecret());
			String result = OkHttpUtils.get(url);
			log.debug("http response for get wechat access token : " + result);
			return JSONObject.parseObject(result, WechatAccessTokenDto.class);
		}
		catch (Exception e) {
			log.error("failed to get wechat access token : {}", e.getMessage());
		}
		return null;
	}

}