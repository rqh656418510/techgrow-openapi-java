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

package org.techgrow.openapi.controller;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.techgrow.openapi.constants.WechatReplyContent;
import org.techgrow.openapi.dto.ReadmoreCaptchaDto;
import org.techgrow.openapi.entity.WechatSubscribeUser;
import org.techgrow.openapi.enums.BizCode;
import org.techgrow.openapi.enums.Status;
import org.techgrow.openapi.properties.ReadmoreProperties;
import org.techgrow.openapi.service.ReadmoreCaptchaService;
import org.techgrow.openapi.service.ReadmoreTokenService;
import org.techgrow.openapi.service.WechatSubscribeUserService;
import org.techgrow.openapi.utils.R;
import org.techgrow.openapi.vo.ReadmoreTokenVo;

import javax.validation.Valid;

/**
 * @author clay
 * @date 2023-04-28
 */
@Slf4j
@Controller
@RequestMapping("/api/readmore/captcha")
public class ReadmoreCaptchaController {

	@Autowired
	private ReadmoreTokenService tokenService;

	@Autowired
	private ReadmoreCaptchaService captchaService;

	@Autowired
	private ReadmoreProperties readmoreProperties;

	@Autowired
	private WechatSubscribeUserService subscribeUserService;

	/**
	 * Generate captcha
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@PostMapping("/generate")
	public R generate(@RequestBody ReadmoreCaptchaDto dto) {
		String blogId = dto.getBlogId();
		String toOpenId = dto.getToOpenId();
		String fromOpenId = dto.getFromOpenId();

		// Verify blog id
		if (StrUtil.isBlank(blogId) || !readmoreProperties.getBlogId().equals(blogId)) {
			return R.error(BizCode.INVALID_BLOG_ID.setDescription("博客ID无效"));
		}
		// Verify subscribe
		if (StrUtil.isNotBlank(fromOpenId) && StrUtil.isNotBlank(toOpenId)) {
			// Verify if the user has subscribed
			WechatSubscribeUser subscribeUser = subscribeUserService.getUser(fromOpenId, toOpenId);
			if (subscribeUser == null) {
				return R.error(BizCode.WECHAT_UNSUBSCRIBE.setDescription(WechatReplyContent.CONFIRM_SUBSCRIBE));
			}
			// Verify if the user has cancelled subscribe
			else if (subscribeUser.getUpdateTime() != null || subscribeUser.getStatus().equals(Status.INVALID)) {
				return R.error(BizCode.WECHAT_CANCEL_SUBSCRIBE.setDescription(WechatReplyContent.ONCE_UNSUBSCRIBE));
			}
		}

		// Generate captcha
		return captchaService.generate(dto);
	}

	/**
	 * Generate captcha
	 * @param dto
	 * @param model
	 * @return
	 */
	@GetMapping("/generate")
	public String generate(ReadmoreCaptchaDto dto, Model model) {
		R result = null;
		try {
			// Generate captcha
			result = this.generate(dto);
		}
		catch (Exception e) {
			log.error(e.getMessage());
			result = R.error(BizCode.INTERNAL_SYSTEM_ERROR.setDescription("系统出错，请稍后再试"));
		}

		// Return html
		model.addAttribute("result", result);
		return "captcha";
	}

	/**
	 * Validate captcha
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@PostMapping("/validate")
	public R validate(@Valid @RequestBody ReadmoreCaptchaDto dto) {
		String blogId = dto.getBlogId();
		String code = dto.getCode();

		// Verify captcha
		boolean validateCaptcha = captchaService.validate(blogId, code);
		if (!validateCaptcha) {
			return R.error(BizCode.INVALID_CAPTCHA.setDescription("验证码无效"));
		}

		// Generate token
		ReadmoreTokenVo token = tokenService.generate(blogId, code);
		// Delete captcha
		captchaService.delete(blogId, code);
		// Return token
		return R.ok().setData(token);
	}

}
