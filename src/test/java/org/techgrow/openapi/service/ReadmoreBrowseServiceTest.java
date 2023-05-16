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

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.techgrow.openapi.constants.ReadmoreConstatns;
import org.techgrow.openapi.dto.PageDto;
import org.techgrow.openapi.entity.ReadmoreBrowse;
import org.techgrow.openapi.utils.R;

import java.util.Date;

/**
 * @author clay
 * @date 2023-05-08
 */
@Slf4j
@Transactional
@SpringBootTest
public class ReadmoreBrowseServiceTest {

	@Autowired
	private ReadmoreBrowseService browseService;

	@Test
	public void list() {
		PageDto pageDto = new PageDto();
		R result = browseService.list(ReadmoreConstatns.BLOG_ID, pageDto);
		log.info("{}", JSON.toJSONString(result));
	}

	@Test
	@Rollback(value = false)
	public void save() {
		ReadmoreBrowse entity = new ReadmoreBrowse();
		entity.setTag(1);
		entity.setProbability(0.9);
		entity.setIp("36.148.61.240");
		entity.setTitle("Hexo博客公众号引流教程");
		entity.setCreateTime(new Date());
		entity.setBlogId("18762-1609305354821-257");
		entity.setUrl("https://docs.techgrow.cn/v1/wechat/tutorial/hexo/");
		entity.setUa("Mozilla/5.0 (X11; Linux loongarch64) AppleWebKit/537.36 (KHTML, like Gecko)");
		browseService.save(entity);
	}

}
