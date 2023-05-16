CREATE TABLE IF NOT EXISTS `tb_readmore_token` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `blog_id` varchar(64) DEFAULT NULL COMMENT '网站的ID',
  `token` varchar(500) NOT NULL COMMENT '凭证',
  `from_open_id` varchar(255) DEFAULT NULL COMMENT '发起关注的微信用户ID',
  `to_open_id` varchar(255) DEFAULT NULL COMMENT '被关注的微信用户ID',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX (`blog_id`),
  UNIQUE KEY `token_unique_index` (`token`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT '凭证';


CREATE TABLE IF NOT EXISTS `tb_readmore_captcha` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `blog_id` varchar(64) DEFAULT NULL COMMENT '网站的ID',
  `code` varchar(20) NOT NULL COMMENT '验证码',
  `from_open_id` varchar(255) DEFAULT NULL COMMENT '发起关注的微信用户ID',
  `to_open_id` varchar(255) DEFAULT NULL COMMENT '被关注的微信用户ID',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`),
  INDEX (`blog_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT '验证码';


CREATE TABLE IF NOT EXISTS `tb_readmore_browse` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `blog_id` varchar(64) DEFAULT NULL COMMENT '网站的ID',
  `probability` double DEFAULT NULL COMMENT '随机浏览的概率',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP 地址',
  `ua` text COMMENT '浏览器 UA',
  `url` varchar(500) DEFAULT NULL COMMENT '来源 URL',
  `title` varchar(255) DEFAULT NULL COMMENT '来源标题',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `tag` tinyint(1) NOT NULL COMMENT '浏览标识：1 - 凭证解锁浏览，2 - 随机解锁浏览，3 - 验证码解锁浏览，4 - 阀值解锁浏览',
  PRIMARY KEY (`id`),
  INDEX (`blog_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT '浏览记录';


CREATE TABLE IF NOT EXISTS `tb_wx_access_token` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `token` varchar(500) NOT NULL COMMENT '凭据',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `access_token_unique_index` (`token`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT '微信公众号的接口调用凭据';


CREATE TABLE IF NOT EXISTS `tb_wx_subscribe_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `from_open_id` varchar(255) NOT NULL COMMENT '发起关注的微信用户ID',
  `to_open_id` varchar(255) NOT NULL COMMENT '被关注的微信用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` int(11) DEFAULT '1' COMMENT '有效状态：1 有效，0 无效',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_multi_unique_index` (`from_open_id`, `to_open_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT '微信公众号的关注用户';

