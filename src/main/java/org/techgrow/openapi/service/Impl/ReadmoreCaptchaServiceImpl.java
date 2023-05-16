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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.techgrow.openapi.dto.ReadmoreCaptchaDto;
import org.techgrow.openapi.entity.ReadmoreCaptcha;
import org.techgrow.openapi.enums.TimeUnit;
import org.techgrow.openapi.mapper.ReadmoreCaptchaMapper;
import org.techgrow.openapi.properties.ReadmoreProperties;
import org.techgrow.openapi.service.ReadmoreCaptchaService;
import org.techgrow.openapi.utils.R;
import org.techgrow.openapi.utils.ReadmoreUtils;
import org.techgrow.openapi.vo.ReadmoreCaptchaVo;

import java.util.Date;

/**
 * @author clay
 * @date 2023-05-03
 */
@Slf4j
@Service
public class ReadmoreCaptchaServiceImpl extends ServiceImpl<ReadmoreCaptchaMapper, ReadmoreCaptcha>
		implements ReadmoreCaptchaService {

	@Autowired
	private ReadmoreUtils readmoreUtils;

	@Autowired
	private ReadmoreProperties readmoreProperties;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R generate(ReadmoreCaptchaDto dto) {
		String blogId = dto.getBlogId();
		String toOpenId = dto.getToOpenId();
		String fromOpenId = dto.getFromOpenId();

		String code = readmoreUtils.generateCaptcha();

		TimeUnit timeUnit = TimeUnit.getEnumByValue(readmoreProperties.getCaptcha().getExpiresUnit());
		String expireTimeUnit = timeUnit != null ? timeUnit.getDescription() : "";
		Date expireDate = getExpireDate();

		ReadmoreCaptchaVo resultVo = new ReadmoreCaptchaVo();
		resultVo.setCode(code);
		resultVo.setBlogId(blogId);
		resultVo.setExpireTime(expireDate);
		resultVo.setExpiresUnit(expireTimeUnit);
		resultVo.setExpiresValue(readmoreProperties.getCaptcha().getExpiresValue());

		// Ensure uniqueness of the captcha
		while (validate(blogId, code)) {
			code = readmoreUtils.generateCaptcha();
			resultVo.setCode(code);
		}

		// Save captcha
		ReadmoreCaptcha entity = new ReadmoreCaptcha();
		entity.setCode(code);
		entity.setBlogId(blogId);
		entity.setToOpenId(toOpenId);
		entity.setFromOpenId(fromOpenId);
		entity.setExpireTime(expireDate);
		super.save(entity);

		return R.ok().setData(resultVo);
	}

	@Override
	public ReadmoreCaptcha select(String blogId, String code) {
		return super.getOne(new QueryWrapper<ReadmoreCaptcha>().eq("code", code).eq("blog_id", blogId), false);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean delete(String blogId, String code) {
		return super.remove(new QueryWrapper<ReadmoreCaptcha>().eq("blog_id", blogId).eq("code", code));
	}

	@Override
	public boolean validate(String blogId, String code) {
		ReadmoreCaptcha captcha = super.getOne(new QueryWrapper<ReadmoreCaptcha>().eq("code", code)
			.eq("blog_id", blogId)
			.gt("expire_time", new Date()));
		return captcha != null;
	}

	/**
	 * Get expire date of the captcha
	 * @return Date
	 */
	private Date getExpireDate() {
		long expiresValue = readmoreProperties.getCaptcha().getExpiresValue();
		String expiresUnit = readmoreProperties.getCaptcha().getExpiresUnit();
		return TimeUnit.getExpiresDate(expiresValue, expiresUnit);
	}

}
