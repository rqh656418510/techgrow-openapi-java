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
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.techgrow.openapi.config.CacheConfig;
import org.techgrow.openapi.constants.Cachekey;
import org.techgrow.openapi.dto.WechatAccessTokenDto;
import org.techgrow.openapi.entity.WechatAccessToken;
import org.techgrow.openapi.enums.TimeUnit;
import org.techgrow.openapi.mapper.WechatAccessTokenMapper;
import org.techgrow.openapi.service.WechatAccessTokenService;
import org.techgrow.openapi.utils.WechatApiUtils;

import java.util.Date;

/**
 * @author clay
 * @date 2023-05-04
 */
@Slf4j
@Service
public class WechatAccessTokenServiceImpl extends ServiceImpl<WechatAccessTokenMapper, WechatAccessToken>
		implements WechatAccessTokenService {

	@Autowired
	private CacheManager cacheManager;

	@Autowired
	private WechatApiUtils wechatApiUtils;

	private Cache cache() {
		Cache cache = cacheManager.getCache(CacheConfig.Caches.WechatAccessToken.name());
		Assert.notNull(cache, "the wechat access token cache is empty");
		return cache;
	}

	@Override
	public WechatAccessToken get() {
		// Get from cache
		WechatAccessToken accessToken = cache().get(Cachekey.WECHAT_ACCESS_TOKEN, WechatAccessToken.class);
		if (accessToken == null || StrUtil.isBlank(accessToken.getToken())) {
			// Get from database
			QueryWrapper<WechatAccessToken> queryWrapper = new QueryWrapper<>();
			queryWrapper.gt("expire_time", new Date());
			accessToken = this.getOne(queryWrapper, false);
			if (accessToken == null || accessToken.getExpireTime().before(new Date())) {
				// Get from remote
				WechatAccessTokenDto newAccessToken = wechatApiUtils.getAccessToken();
				if (newAccessToken != null && StrUtil.isNotBlank(newAccessToken.getToken())) {
					accessToken = new WechatAccessToken();
					accessToken.setToken(newAccessToken.getToken());
					accessToken.setExpireTime(TimeUnit.getExpiresDate(newAccessToken.getExpireIn(), TimeUnit.SECONDS));
					// Save access token
					this.add(accessToken);
				}
			}
		}
		return accessToken;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void add(WechatAccessToken accessToken) {
		this.clear();
		this.save(accessToken);
		// Save to cache
		cache().put(Cachekey.WECHAT_ACCESS_TOKEN, accessToken);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void clear() {
		this.baseMapper.clear();
	}

}
