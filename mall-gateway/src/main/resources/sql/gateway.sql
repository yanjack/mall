CREATE TABLE `gateway_interface` (
	`ID` INT(11) NOT NULL AUTO_INCREMENT,
	`SERVICE` VARCHAR(64) NULL DEFAULT NULL COMMENT '所属服务',
	`MODULE` VARCHAR(10) NULL DEFAULT NULL COMMENT '所属模块',
	`URL` VARCHAR(120) NOT NULL COMMENT '接口路径',
	`METHOD` VARCHAR(64) NULL DEFAULT NULL COMMENT '请求方法',
	`TOKEN` TINYINT(1) NOT NULL DEFAULT '1' COMMENT '是否需要token（1是 0否）',
	`CREATE_TIME` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY (`ID`),
	UNIQUE INDEX `gateway_url_method_index` (`URL`, `METHOD`)
)
COMMENT='网关接口'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=1669
;
