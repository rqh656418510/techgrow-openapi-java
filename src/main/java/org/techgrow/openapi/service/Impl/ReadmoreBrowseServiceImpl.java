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

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.techgrow.openapi.dto.PageDto;
import org.techgrow.openapi.dto.ReadmoreBrowseDto;
import org.techgrow.openapi.entity.ReadmoreBrowse;
import org.techgrow.openapi.mapper.ReadmoreBrowseMapper;
import org.techgrow.openapi.service.ReadmoreBrowseService;
import org.techgrow.openapi.utils.PageQuery;
import org.techgrow.openapi.utils.R;
import org.techgrow.openapi.vo.PageVo;
import org.techgrow.openapi.vo.ReadmoreBrowseVo;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author clay
 * @date 2023-05-03
 */
@Slf4j
@Service
public class ReadmoreBrowseServiceImpl extends ServiceImpl<ReadmoreBrowseMapper, ReadmoreBrowse>
		implements ReadmoreBrowseService {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R add(ReadmoreBrowseDto dto) {
		ReadmoreBrowse entity = new ReadmoreBrowse();
		BeanUtil.copyProperties(dto, entity);
		entity.setCreateTime(new Date());
		super.save(entity);
		return R.ok();
	}

	@Override
	public R list(String blogId, PageDto pageDto) {
		IPage<ReadmoreBrowse> page = this.page(new PageQuery<ReadmoreBrowse>().getPage(pageDto, "create_time", false),
				new QueryWrapper<ReadmoreBrowse>().eq("blog_id", blogId));

		List<ReadmoreBrowseVo> resultList = page.getRecords().stream().map(entity -> {
			ReadmoreBrowseVo vo = new ReadmoreBrowseVo();
			BeanUtil.copyProperties(entity, vo);
			return vo;
		}).collect(Collectors.toList());

		return R.ok().setData(new PageVo(page, resultList));
	}

}
