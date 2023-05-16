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

package org.techgrow.openapi.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.techgrow.openapi.constants.ReadmoreConstatns;
import org.techgrow.openapi.entity.ReadmoreCaptcha;
import org.techgrow.openapi.utils.ReadmoreUtils;

import java.util.Date;

/**
 * @author clay
 * @date 2023-05-08
 */
@Slf4j
@Transactional
@SpringBootTest
public class ReadmoreCaptchaServiceTest {

	@Autowired
	private ReadmoreUtils readmoreUtils;

	@Autowired
	private ReadmoreCaptchaService captchaService;

	@Test
	@Rollback(value = false)
	public void save() {
		ReadmoreCaptcha captcha = new ReadmoreCaptcha();
		captcha.setBlogId(ReadmoreConstatns.BLOG_ID);
		captcha.setCode(readmoreUtils.generateCaptcha());
		captcha.setExpireTime(new Date());
		captchaService.save(captcha);
	}

}
