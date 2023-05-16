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

package org.techgrow.openapi.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author clay
 * @date 2023-05-04
 */
@EnableCaching
@Configuration
public class CacheConfig {

	/**
	 * Cache names
	 */
	public enum Caches {

		/**
		 * Wechat access token
		 */
		WechatAccessToken(7000, 100);

		/**
		 * Expire time (seconds)
		 */
		private int ttl = 10;

		/**
		 * Maximum capacity
		 */
		private int maxSize = 1000;

		Caches() {

		}

		Caches(int ttl) {
			this.ttl = ttl;
		}

		Caches(int ttl, int maxSize) {
			this.ttl = ttl;
			this.maxSize = maxSize;
		}

		public int getMaxSize() {
			return this.maxSize;
		}

		public int getTtl() {
			return this.ttl;
		}

	}

	/**
	 * Cache manager
	 */
	@Bean
	public CacheManager caffeineCacheManager() {
		ArrayList<CaffeineCache> caches = new ArrayList<CaffeineCache>();
		for (Caches cache : Caches.values()) {
			caches.add(new CaffeineCache(cache.name(),
					Caffeine.newBuilder()
						.recordStats()
						.expireAfterWrite(cache.getTtl(), TimeUnit.SECONDS)
						.maximumSize(cache.getMaxSize())
						.build()));
		}
		SimpleCacheManager cacheManager = new SimpleCacheManager();
		cacheManager.setCaches(caches);
		return cacheManager;
	}

}