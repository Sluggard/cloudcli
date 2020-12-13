CREATE TABLE `department_role_relation`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` int NOT NULL COMMENT '角色id',
  `department_id` int NOT NULL COMMENT '职位id',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `version` int NOT NULL DEFAULT 0 COMMENT '版本号',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) COMMENT = '职位角色关联信息表';

CREATE TABLE `organization_role_relation`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` int NOT NULL COMMENT '角色id',
  `organization_id` int NOT NULL COMMENT '组织id',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `version` int NOT NULL DEFAULT 0 COMMENT '版本号',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) COMMENT = '组织角色关联信息表';

CREATE TABLE `role_menu_relation`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` int NOT NULL COMMENT '角色id',
  `menu_id` int NOT NULL COMMENT '菜单id',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `version` int NOT NULL DEFAULT 0 COMMENT '版本号',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) COMMENT = '角色菜单关联信息表';

CREATE TABLE `role_resource_relation`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` int NOT NULL COMMENT '角色id',
  `resource_id` int NOT NULL COMMENT '资源id',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `version` int NOT NULL DEFAULT 0 COMMENT '版本号',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) COMMENT = '角色资源关联信息表';

CREATE TABLE `umps_menu`  (
  `id` int NOT NULL COMMENT '主键id',
  `menu_name` varchar(20) NOT NULL COMMENT '名称',
  `menu_code` varchar(20) NOT NULL COMMENT '编码',
  `parent_menu_id` int NOT NULL COMMENT '父菜单id',
  `menu_router` varchar(40) NULL COMMENT '路由地址',
  `menu_component` varchar(40) NULL COMMENT '前端组件',
  `sort_no` int NOT NULL DEFAULT 99 COMMENT '排序号',
  `menu_icon` varchar(40) NOT NULL COMMENT '图标',
  `resource_type` varchar(20) NOT NULL COMMENT '类型 menu 菜单 button 按钮',
  `remark` varchar(500) NULL COMMENT '备注',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `version` int NOT NULL DEFAULT 0 COMMENT '版本号',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) COMMENT = '菜单按钮信息表';

CREATE TABLE `upms_department`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `department_name` varchar(20) NOT NULL COMMENT '职位名称',
  `department_code` varchar(20) NOT NULL COMMENT '职位编码',
  `organization_id` int NOT NULL COMMENT '组织id',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `version` int NOT NULL DEFAULT 0 COMMENT '版本号',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) COMMENT = '职位信息表';

CREATE TABLE `upms_organization`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `organization_name` varchar(20) NOT NULL COMMENT '组织名称',
  `organization_code` varchar(20) NOT NULL COMMENT '组织编码',
  `parent_organization_id` int NULL COMMENT '父组织编码',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `version` int NOT NULL DEFAULT 0 COMMENT '版本号',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) COMMENT = '组织信息表';

CREATE TABLE `upms_resource`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `resource_name` varchar(20) NOT NULL COMMENT '资源名称',
  `resource_code` varchar(20) NOT NULL COMMENT '资源编码',
  `url` varchar(100) NOT NULL COMMENT '资源地址',
  `remark` varchar(500) NULL COMMENT '备注',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `version` int NOT NULL DEFAULT 0 COMMENT '版本号',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) COMMENT = '资源信息表';

CREATE TABLE `upms_role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_name` varchar(20) NOT NULL COMMENT '角色名称',
  `role_code` varchar(20) NOT NULL COMMENT '角色编码',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `version` int NOT NULL DEFAULT 0 COMMENT '版本号',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) COMMENT = '角色信息表';

CREATE TABLE `upms_user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `head_portrait` varchar(255) NULL COMMENT '头像',
  `real_name` varchar(10) NOT NULL COMMENT '姓名',
  `gender` tinyint(1) NOT NULL COMMENT '性别',
  `age` int NOT NULL COMMENT '年龄',
  `id_no` varchar(18) NULL COMMENT '身份证号码',
  `telephone` varchar(11) NOT NULL COMMENT '电话号码',
  `birth_day` date NULL COMMENT '生日',
  `remark` varchar(500) NULL COMMENT '备注',
  `enable` tinyint(1) NOT NULL DEFAULT 0 COMMENT '启用/禁用',
  `locked` tinyint(1) NOT NULL DEFAULT 0 COMMENT '锁定',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `version` int NOT NULL DEFAULT 0 COMMENT '版本号',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统后台用户信息表';

CREATE TABLE `upms_user_group`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_group_name` varchar(20) NOT NULL COMMENT '用户组名称',
  `user_group_code` varchar(20) NOT NULL COMMENT '用户组编码',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `version` int NOT NULL DEFAULT 0 COMMENT '版本号',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户组信息表';

CREATE TABLE `user_department_relation`  (
  `id` int ZEROFILL NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int NOT NULL COMMENT '用户id',
  `department_id` int NOT NULL COMMENT '职位id',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `version` int NOT NULL DEFAULT 0 COMMENT '版本号',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) COMMENT = '用户职位关联信息表';

CREATE TABLE `user_group_role_relation`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` int NOT NULL COMMENT '角色id',
  `user_group_id` int NOT NULL COMMENT '用户组id',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `version` int NOT NULL DEFAULT 0 COMMENT '版本号',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) COMMENT = '用户组角色关联信息表';

CREATE TABLE `user_organization_relation`  (
  `id` int ZEROFILL NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int NOT NULL COMMENT '用户id',
  `organization_id` int NOT NULL COMMENT '组织id',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `version` int NOT NULL DEFAULT 0 COMMENT '版本号',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) COMMENT = '用户组织关联信息表';

CREATE TABLE `user_user_group_relation`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int NOT NULL COMMENT '用户id',
  `user_group_id` int NOT NULL COMMENT '用户组id',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `version` int NOT NULL DEFAULT 0 COMMENT '版本号',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) COMMENT = '用户用户组关联信息表';

