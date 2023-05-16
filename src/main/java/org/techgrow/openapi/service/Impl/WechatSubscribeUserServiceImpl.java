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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.techgrow.openapi.dto.PageDto;
import org.techgrow.openapi.dto.WechatSubscribeUserDto;
import org.techgrow.openapi.entity.WechatSubscribeUser;
import org.techgrow.openapi.enums.Status;
import org.techgrow.openapi.mapper.WechatSubscribeUserMapper;
import org.techgrow.openapi.service.WechatSubscribeUserService;
import org.techgrow.openapi.utils.PageQuery;
import org.techgrow.openapi.utils.R;
import org.techgrow.openapi.vo.PageVo;
import org.techgrow.openapi.vo.WechatSubscribeUserVo;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author clay
 * @date 2023-05-03
 */
@Slf4j
@Service
public class WechatSubscribeUserServiceImpl extends ServiceImpl<WechatSubscribeUserMapper, WechatSubscribeUser>
		implements WechatSubscribeUserService {

	@Override
	public R list(PageDto pageDto) {
		IPage<WechatSubscribeUser> page = this
			.page(new PageQuery<WechatSubscribeUser>().getPage(pageDto, "create_time", false));

		List<WechatSubscribeUserVo> resultList = page.getRecords().stream().map(entity -> {
			WechatSubscribeUserVo vo = new WechatSubscribeUserVo();
			BeanUtils.copyProperties(entity, vo);
			return vo;
		}).collect(Collectors.toList());

		return R.ok().setData(new PageVo(page, resultList));
	}

	@Override
	public WechatSubscribeUser getUser(String fromOpenId, String toOpenId) {
		return this.baseMapper.getSubscribeUser(fromOpenId, toOpenId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R addUser(WechatSubscribeUserDto dto) {
		WechatSubscribeUser entity = new WechatSubscribeUser();
		entity.setCreateTime(new Date());
		entity.setToOpenId(dto.getToOpenId());
		entity.setFromOpenId(dto.getFromOpenId());
		entity.setStatus(Status.VALID);
		this.baseMapper.insertSubscribeUser(entity);
		return R.ok();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R updateStatus(String fromOpenId, String toOpenId, Status status) {
		WechatSubscribeUser entity = this.baseMapper.getSubscribeUser(fromOpenId, toOpenId);
		if (entity != null) {
			entity.setStatus(status);
			this.baseMapper.updateSubscribeUser(entity);
			return R.ok();
		}
		return R.error();
	}

}
