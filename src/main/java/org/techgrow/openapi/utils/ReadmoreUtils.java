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

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.techgrow.openapi.properties.ReadmoreProperties;

import java.util.Date;
import java.util.Random;

/**
 * @author clay
 * @date 2023-05-04
 */
@Slf4j
@Component
public class ReadmoreUtils {

	@Autowired
	private ReadmoreProperties readmoreProperties;

	/**
	 * Generate captcha
	 */
	public String generateCaptcha() {
		final String saltChars = "1234567890";
		StringBuilder strBuilder = new StringBuilder();
		Random rnd = new Random();
		while (strBuilder.length() < readmoreProperties.getCaptcha().getLength()) {
			int index = (int) (rnd.nextFloat() * saltChars.length());
			strBuilder.append(saltChars.charAt(index));
		}
		return strBuilder.toString();
	}

	/**
	 * Generate token
	 */
	public String generateToken(String blogId, String fromOpenId, String toOpenId, Date expireDate) {
		Assert.isTrue(StrUtil.isNotBlank(blogId), "Blog id cannot be empty");
		Assert.notNull(expireDate, "The expire date of the token cannot be empty");

		Date nowDate = new Date();
		if (StrUtil.isBlank(fromOpenId)) {
			fromOpenId = "";
		}
		if (StrUtil.isBlank(toOpenId)) {
			toOpenId = "";
		}
		JSONObject jsonData = new JSONObject();
		jsonData.put("fromOpenId", fromOpenId);
		jsonData.put("toOpenId", toOpenId);
		jsonData.put("blogId", blogId);

		return Jwts.builder()
			.setHeaderParam("typ", "JWT")
			.setSubject(jsonData.toJSONString())
			.setIssuedAt(nowDate)
			.setExpiration(expireDate)
			.signWith(SignatureAlgorithm.HS512, readmoreProperties.getToken().getSignKey())
			.compact();
	}

	/**
	 * Validate token
	 */
	public Claims validateToken(String token) {
		if (StrUtil.isBlank(token)) {
			return null;
		}
		try {
			return Jwts.parser()
				.setSigningKey(readmoreProperties.getToken().getSignKey())
				.parseClaimsJws(token)
				.getBody();
		}
		catch (Exception e) {
			log.error("validate token occur error ", e);
			return null;
		}
	}

}
