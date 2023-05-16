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

package org.techgrow.openapi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.techgrow.openapi.enums.Status;

import java.io.Serializable;
import java.util.Date;

/**
 * @author clay
 * @date 2023-05-04
 */
@Data
@ToString
@NoArgsConstructor
@TableName("tb_wx_subscribe_user")
public class WechatSubscribeUser implements Serializable {

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 被关注的微信用户ID
	 */
	private String toOpenId;

	/**
	 * 发起关注的微信用户ID
	 */
	private String fromOpenId;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 订阅状态
	 */
	private Status status;

}