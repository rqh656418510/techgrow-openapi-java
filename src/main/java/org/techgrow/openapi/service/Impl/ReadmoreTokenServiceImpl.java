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

package org.techgrow.openapi.service.Impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.techgrow.openapi.dto.ReadmoreTokenDto;
import org.techgrow.openapi.entity.ReadmoreCaptcha;
import org.techgrow.openapi.entity.ReadmoreToken;
import org.techgrow.openapi.entity.WechatSubscribeUser;
import org.techgrow.openapi.enums.BizCode;
import org.techgrow.openapi.enums.Status;
import org.techgrow.openapi.mapper.ReadmoreTokenMapper;
import org.techgrow.openapi.properties.ReadmoreProperties;
import org.techgrow.openapi.service.ReadmoreCaptchaService;
import org.techgrow.openapi.service.ReadmoreTokenService;
import org.techgrow.openapi.service.WechatSubscribeUserService;
import org.techgrow.openapi.utils.R;
import org.techgrow.openapi.utils.ReadmoreUtils;
import org.techgrow.openapi.vo.ReadmoreTokenVo;

import java.util.Calendar;
import java.util.Date;

/**
 * @author clay
 * @date 2023-05-03
 */
@Slf4j
@Service
public class ReadmoreTokenServiceImpl extends ServiceImpl<ReadmoreTokenMapper, ReadmoreToken>
		implements ReadmoreTokenService {

	@Autowired
	private ReadmoreUtils readmoreUtils;

	@Autowired
	private ReadmoreProperties readmoreProperties;

	@Autowired
	private ReadmoreCaptchaService captchaService;

	@Autowired
	private WechatSubscribeUserService subscribeUserService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ReadmoreTokenVo generate(String blogId, String code) {
		ReadmoreCaptcha captcha = captchaService.select(blogId, code);
		String fromOpenId = captcha.getFromOpenId();
		String toOpenId = captcha.getToOpenId();

		Date expireDate = getExpireDate();
		String token = readmoreUtils.generateToken(blogId, fromOpenId, toOpenId, expireDate);

		// Ensure uniqueness of the token
		while (checkExisted(token)) {
			token = readmoreUtils.generateToken(blogId, fromOpenId, toOpenId, expireDate);
		}

		// Save token
		ReadmoreToken entity = new ReadmoreToken(blogId, token, expireDate);
		entity.setFromOpenId(fromOpenId);
		entity.setToOpenId(toOpenId);
		super.save(entity);

		return new ReadmoreTokenVo(blogId, token, expireDate);
	}

	@Override
	public R validate(ReadmoreTokenDto dto) {
		Claims claims = readmoreUtils.validateToken(dto.getToken());
		if (claims == null) {
			return R.error(BizCode.INVALID_TOKEN);
		}

		JSONObject jsonObject = JSON.parseObject(claims.getSubject());
		String fromOpenId = jsonObject.getString("fromOpenId");
		String toOpenId = jsonObject.getString("toOpenId");
		String blogId = jsonObject.getString("blogId");

		// Verify blog id
		if (!blogId.equals(dto.getBlogId()) || !readmoreProperties.getBlogId().equals(dto.getBlogId())) {
			return R.error(BizCode.INVALID_TOKEN);
		}

		if (StrUtil.isNotBlank(fromOpenId) && StrUtil.isNotBlank(toOpenId)) {
			// Verify if the user has subscribed
			WechatSubscribeUser subscribeUser = subscribeUserService.getUser(fromOpenId, toOpenId);
			if (subscribeUser == null) {
				return R.error(BizCode.INVALID_TOKEN);
			}
			// Verify if the user has cancelled subscribe
			if (subscribeUser.getUpdateTime() != null || subscribeUser.getStatus() == Status.INVALID) {
				return R.error(BizCode.INVALID_TOKEN);
			}
		}

		return R.ok();
	}

	@Override
	public boolean checkExisted(String token) {
		ReadmoreToken entity = super.getOne(new QueryWrapper<ReadmoreToken>().eq("token", token), false);
		return entity != null;
	}

	/**
	 * Get expire date of the token
	 * @return
	 */
	private Date getExpireDate() {
		int expireDays = readmoreProperties.getToken().getExpireDays();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, expireDays);
		return calendar.getTime();
	}

}
