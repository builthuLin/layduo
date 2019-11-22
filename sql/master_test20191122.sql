/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50646
Source Host           : 127.0.0.1:3306
Source Database       : master_test

Target Server Type    : MYSQL
Target Server Version : 50646
File Encoding         : 65001

Date: 2019-11-22 19:01:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_config`
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `config_id` int(5) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='参数配置表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES ('1', '主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-blue', 'Y', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow');
INSERT INTO `sys_config` VALUES ('2', '用户管理-账号初始密码', 'sys.user.initPassword', '123456', 'Y', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '初始化密码 123456');
INSERT INTO `sys_config` VALUES ('3', '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-dark', 'Y', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '深色主题theme-dark，浅色主题theme-light');

-- ----------------------------
-- Table structure for `sys_dept`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父部门id',
  `ancestors` varchar(50) DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) DEFAULT '' COMMENT '部门名称',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `leader` varchar(20) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `status` char(1) DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('100', '0', '0', '若依科技', '0', '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00');
INSERT INTO `sys_dept` VALUES ('101', '100', '0,100', '深圳总公司', '1', '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00');
INSERT INTO `sys_dept` VALUES ('102', '100', '0,100', '长沙分公司', '2', '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00');
INSERT INTO `sys_dept` VALUES ('103', '101', '0,100,101', '研发部门', '1', '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00');
INSERT INTO `sys_dept` VALUES ('104', '101', '0,100,101', '市场部门', '2', '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00');
INSERT INTO `sys_dept` VALUES ('105', '101', '0,100,101', '测试部门', '3', '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00');
INSERT INTO `sys_dept` VALUES ('106', '101', '0,100,101', '财务部门', '4', '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00');
INSERT INTO `sys_dept` VALUES ('107', '101', '0,100,101', '运维部门', '5', '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00');
INSERT INTO `sys_dept` VALUES ('108', '102', '0,100,102', '市场部门', '1', '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00');
INSERT INTO `sys_dept` VALUES ('109', '102', '0,100,102', '财务部门', '2', '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00');

-- ----------------------------
-- Table structure for `sys_logininfor`
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfor`;
CREATE TABLE `sys_logininfor` (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `login_name` varchar(50) DEFAULT '' COMMENT '登录账号',
  `ipaddr` varchar(50) DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) DEFAULT '' COMMENT '操作系统',
  `status` char(1) DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) DEFAULT '' COMMENT '提示消息',
  `login_time` datetime DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=190 DEFAULT CHARSET=utf8 COMMENT='系统访问记录';

-- ----------------------------
-- Records of sys_logininfor
-- ----------------------------
INSERT INTO `sys_logininfor` VALUES ('100', 'admin', '127.0.0.1', '内网IP', 'Chrome', 'Windows 10', '1', '验证码错误', '2019-11-04 13:06:13');
INSERT INTO `sys_logininfor` VALUES ('101', 'admin', '127.0.0.1', '内网IP', 'Chrome', 'Windows 10', '0', '登录成功', '2019-11-04 13:06:18');
INSERT INTO `sys_logininfor` VALUES ('102', 'admin', '127.0.0.1', '内网IP', 'Microsoft Edge', 'Windows 10', '0', '登录成功', '2019-11-04 16:09:31');
INSERT INTO `sys_logininfor` VALUES ('103', 'admin', '127.0.0.1', '内网IP', 'Microsoft Edge', 'Windows 10', '0', '登录成功', '2019-11-04 17:29:15');
INSERT INTO `sys_logininfor` VALUES ('104', 'admin', '127.0.0.1', '内网IP', 'Chrome', 'Windows 10', '0', '登录成功', '2019-11-04 17:31:40');
INSERT INTO `sys_logininfor` VALUES ('105', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '0', '登录成功', '2019-11-12 09:31:34');
INSERT INTO `sys_logininfor` VALUES ('106', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '验证码错误', '2019-11-12 09:44:55');
INSERT INTO `sys_logininfor` VALUES ('107', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '0', '登录成功', '2019-11-12 09:46:39');
INSERT INTO `sys_logininfor` VALUES ('108', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '0', '登录成功', '2019-11-12 09:57:18');
INSERT INTO `sys_logininfor` VALUES ('109', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '0', '登录成功', '2019-11-12 10:13:50');
INSERT INTO `sys_logininfor` VALUES ('110', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Microsoft Edge', 'Windows 10', '1', '验证码错误', '2019-11-12 11:22:07');
INSERT INTO `sys_logininfor` VALUES ('111', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Microsoft Edge', 'Windows 10', '1', '验证码错误', '2019-11-12 11:22:12');
INSERT INTO `sys_logininfor` VALUES ('112', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Microsoft Edge', 'Windows 10', '0', '登录成功', '2019-11-12 11:22:40');
INSERT INTO `sys_logininfor` VALUES ('113', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Microsoft Edge', 'Windows 10', '0', '登录成功', '2019-11-12 11:24:10');
INSERT INTO `sys_logininfor` VALUES ('114', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Microsoft Edge', 'Windows 10', '0', '退出成功', '2019-11-12 11:40:55');
INSERT INTO `sys_logininfor` VALUES ('115', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Microsoft Edge', 'Windows 10', '0', '登录成功', '2019-11-12 14:17:03');
INSERT INTO `sys_logininfor` VALUES ('116', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Microsoft Edge', 'Windows 10', '0', '退出成功', '2019-11-12 14:19:14');
INSERT INTO `sys_logininfor` VALUES ('117', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '0', '登录成功', '2019-11-12 16:20:55');
INSERT INTO `sys_logininfor` VALUES ('118', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '0', '退出成功', '2019-11-12 16:34:32');
INSERT INTO `sys_logininfor` VALUES ('119', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '验证码错误', '2019-11-12 16:36:21');
INSERT INTO `sys_logininfor` VALUES ('120', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '验证码错误', '2019-11-12 16:36:23');
INSERT INTO `sys_logininfor` VALUES ('121', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '验证码错误', '2019-11-12 16:36:25');
INSERT INTO `sys_logininfor` VALUES ('122', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '验证码错误', '2019-11-12 16:36:25');
INSERT INTO `sys_logininfor` VALUES ('123', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '验证码错误', '2019-11-12 16:36:26');
INSERT INTO `sys_logininfor` VALUES ('124', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '验证码错误', '2019-11-12 16:36:27');
INSERT INTO `sys_logininfor` VALUES ('125', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '验证码错误', '2019-11-12 16:37:02');
INSERT INTO `sys_logininfor` VALUES ('126', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '验证码错误', '2019-11-12 16:37:06');
INSERT INTO `sys_logininfor` VALUES ('127', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '验证码错误', '2019-11-12 16:37:11');
INSERT INTO `sys_logininfor` VALUES ('128', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '验证码错误', '2019-11-12 16:37:15');
INSERT INTO `sys_logininfor` VALUES ('129', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '0', '登录成功', '2019-11-12 16:37:18');
INSERT INTO `sys_logininfor` VALUES ('130', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '验证码错误', '2019-11-12 16:58:19');
INSERT INTO `sys_logininfor` VALUES ('131', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '验证码错误', '2019-11-12 16:58:21');
INSERT INTO `sys_logininfor` VALUES ('132', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '验证码错误', '2019-11-12 16:58:22');
INSERT INTO `sys_logininfor` VALUES ('133', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '验证码错误', '2019-11-12 16:58:22');
INSERT INTO `sys_logininfor` VALUES ('134', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '验证码错误', '2019-11-12 16:58:23');
INSERT INTO `sys_logininfor` VALUES ('135', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '验证码错误', '2019-11-12 16:58:24');
INSERT INTO `sys_logininfor` VALUES ('136', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '验证码错误', '2019-11-12 16:58:27');
INSERT INTO `sys_logininfor` VALUES ('137', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '验证码错误', '2019-11-12 16:58:30');
INSERT INTO `sys_logininfor` VALUES ('138', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '验证码错误', '2019-11-12 17:02:42');
INSERT INTO `sys_logininfor` VALUES ('139', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '验证码错误', '2019-11-12 17:16:31');
INSERT INTO `sys_logininfor` VALUES ('140', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '验证码错误', '2019-11-12 17:16:34');
INSERT INTO `sys_logininfor` VALUES ('141', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '0', '登录成功', '2019-11-12 17:18:40');
INSERT INTO `sys_logininfor` VALUES ('142', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '0', '退出成功', '2019-11-12 17:30:10');
INSERT INTO `sys_logininfor` VALUES ('143', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '验证码错误', '2019-11-12 17:30:36');
INSERT INTO `sys_logininfor` VALUES ('144', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '密码输入错误1次', '2019-11-12 17:32:16');
INSERT INTO `sys_logininfor` VALUES ('145', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '密码输入错误2次', '2019-11-12 17:32:28');
INSERT INTO `sys_logininfor` VALUES ('146', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '密码输入错误3次', '2019-11-12 17:32:36');
INSERT INTO `sys_logininfor` VALUES ('147', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '验证码错误', '2019-11-12 17:32:39');
INSERT INTO `sys_logininfor` VALUES ('148', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '密码输入错误4次', '2019-11-12 17:32:43');
INSERT INTO `sys_logininfor` VALUES ('149', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '密码输入错误5次', '2019-11-12 17:32:50');
INSERT INTO `sys_logininfor` VALUES ('150', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2019-11-12 17:32:57');
INSERT INTO `sys_logininfor` VALUES ('151', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '密码输入错误1次', '2019-11-12 17:34:37');
INSERT INTO `sys_logininfor` VALUES ('152', 'admin1', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '用户不存在/密码错误', '2019-11-12 17:37:35');
INSERT INTO `sys_logininfor` VALUES ('153', 'admin1', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '用户不存在', '2019-11-12 17:55:46');
INSERT INTO `sys_logininfor` VALUES ('154', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '密码输入错误1次', '2019-11-12 17:55:59');
INSERT INTO `sys_logininfor` VALUES ('155', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '密码输入错误2次', '2019-11-12 17:56:31');
INSERT INTO `sys_logininfor` VALUES ('156', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '密码输入错误3次', '2019-11-12 17:56:34');
INSERT INTO `sys_logininfor` VALUES ('157', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '密码输入错误4次', '2019-11-12 17:56:37');
INSERT INTO `sys_logininfor` VALUES ('158', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '密码输入错误5次', '2019-11-12 17:56:43');
INSERT INTO `sys_logininfor` VALUES ('159', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '密码输入错误5次，帐户锁定1分钟', '2019-11-12 17:56:47');
INSERT INTO `sys_logininfor` VALUES ('160', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '1', '密码输入错误5次，帐户锁定1分钟', '2019-11-12 17:56:54');
INSERT INTO `sys_logininfor` VALUES ('161', 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10', '0', '登录成功', '2019-11-13 09:30:30');
INSERT INTO `sys_logininfor` VALUES ('162', 'admin', '127.0.0.1', '内网IP', 'Chrome', 'Windows 10', '0', '登录成功', '2019-11-13 15:36:01');
INSERT INTO `sys_logininfor` VALUES ('163', 'admin', '127.0.0.1', '内网IP', 'Chrome', 'Windows 10', '0', '退出成功', '2019-11-13 15:38:52');
INSERT INTO `sys_logininfor` VALUES ('164', 'admin', '127.0.0.1', '内网IP', 'Chrome', 'Windows 10', '0', '登录成功', '2019-11-13 15:39:17');
INSERT INTO `sys_logininfor` VALUES ('165', 'admin', '127.0.0.1', '内网IP', 'Chrome', 'Windows 10', '0', '退出成功', '2019-11-13 15:39:37');
INSERT INTO `sys_logininfor` VALUES ('166', 'admin', '127.0.0.1', '内网IP', 'Chrome', 'Windows 10', '0', '登录成功', '2019-11-13 15:40:24');
INSERT INTO `sys_logininfor` VALUES ('167', 'admin', '127.0.0.1', '内网IP', 'Microsoft Edge', 'Windows 10', '0', '登录成功', '2019-11-13 15:51:53');
INSERT INTO `sys_logininfor` VALUES ('168', 'admin', '127.0.0.1', '内网IP', 'Microsoft Edge', 'Windows 10', '0', '登录成功', '2019-11-13 15:53:17');
INSERT INTO `sys_logininfor` VALUES ('169', 'admin', '127.0.0.1', '内网IP', 'Microsoft Edge', 'Windows 10', '0', '登录成功', '2019-11-13 16:06:05');
INSERT INTO `sys_logininfor` VALUES ('170', 'admin', '127.0.0.1', '内网IP', 'Microsoft Edge', 'Windows 10', '0', '登录成功', '2019-11-13 16:06:24');
INSERT INTO `sys_logininfor` VALUES ('171', 'admin', '127.0.0.1', '内网IP', 'Chrome', 'Windows 10', '0', '登录成功', '2019-11-13 17:27:27');
INSERT INTO `sys_logininfor` VALUES ('172', 'admin', '127.0.0.1', '内网IP', 'Chrome', 'Windows 10', '0', '退出成功', '2019-11-13 17:36:40');
INSERT INTO `sys_logininfor` VALUES ('173', 'admin', '127.0.0.1', '内网IP', 'Chrome', 'Windows 10', '0', '登录成功', '2019-11-13 17:36:58');
INSERT INTO `sys_logininfor` VALUES ('174', 'admin', '127.0.0.1', '内网IP', 'Chrome', 'Windows 10', '0', '退出成功', '2019-11-13 17:39:00');
INSERT INTO `sys_logininfor` VALUES ('175', 'admin', '127.0.0.1', '内网IP', 'Chrome', 'Windows 10', '0', '登录成功', '2019-11-13 17:39:20');
INSERT INTO `sys_logininfor` VALUES ('176', 'admin', '127.0.0.1', '内网IP', 'Chrome', 'Windows 10', '0', '退出成功', '2019-11-13 17:42:27');
INSERT INTO `sys_logininfor` VALUES ('177', 'admin', '127.0.0.1', '内网IP', 'Chrome', 'Windows 10', '0', '登录成功', '2019-11-13 17:42:31');
INSERT INTO `sys_logininfor` VALUES ('178', 'admin', '127.0.0.1', '内网IP', 'Chrome', 'Windows 10', '0', '退出成功', '2019-11-14 17:57:43');
INSERT INTO `sys_logininfor` VALUES ('179', 'admin', '127.0.0.1', '内网IP', 'Chrome', 'Windows 10', '1', '验证码错误', '2019-11-14 18:11:53');
INSERT INTO `sys_logininfor` VALUES ('180', 'admin', '127.0.0.1', '内网IP', 'Chrome', 'Windows 10', '0', '登录成功', '2019-11-14 18:11:57');
INSERT INTO `sys_logininfor` VALUES ('181', 'admin', '127.0.0.1', '内网IP', 'Chrome', 'Windows 10', '0', '退出成功', '2019-11-14 18:19:01');
INSERT INTO `sys_logininfor` VALUES ('182', 'admin', '127.0.0.1', '内网IP', 'Chrome', 'Windows 10', '0', '登录成功', '2019-11-14 18:19:19');
INSERT INTO `sys_logininfor` VALUES ('183', 'admin', '127.0.0.1', '内网IP', 'Chrome', 'Windows 10', '0', '登录成功', '2019-11-15 15:07:40');
INSERT INTO `sys_logininfor` VALUES ('184', 'admin', '127.0.0.1', '内网IP', 'Chrome', 'Windows 10', '0', '退出成功', '2019-11-18 15:46:53');
INSERT INTO `sys_logininfor` VALUES ('185', 'admin', '127.0.0.1', '内网IP', 'Chrome', 'Windows 10', '0', '登录成功', '2019-11-18 16:01:51');
INSERT INTO `sys_logininfor` VALUES ('186', 'admin', '127.0.0.1', '内网IP', 'Chrome', 'Windows 10', '0', '退出成功', '2019-11-18 18:14:05');
INSERT INTO `sys_logininfor` VALUES ('187', 'admin', '127.0.0.1', '内网IP', 'Chrome', 'Windows 10', '0', '登录成功', '2019-11-18 18:22:34');
INSERT INTO `sys_logininfor` VALUES ('188', 'admin', '127.0.0.1', '内网IP', 'Microsoft Edge', 'Windows 10', '0', '登录成功', '2019-11-21 16:10:04');
INSERT INTO `sys_logininfor` VALUES ('189', 'admin', '127.0.0.1', '内网IP', 'Chrome', 'Windows 10', '0', '登录成功', '2019-11-21 16:11:55');

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(4) NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `status` char(1) NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色信息表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '管理员', 'admin', '1', '1', '0', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '管理员');
INSERT INTO `sys_role` VALUES ('2', '普通角色', 'common', '2', '2', '0', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '普通角色');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `login_name` varchar(30) NOT NULL COMMENT '登录账号',
  `user_name` varchar(30) NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) DEFAULT '' COMMENT '手机号码',
  `sex` char(1) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) DEFAULT '' COMMENT '头像路径',
  `password` varchar(50) DEFAULT '' COMMENT '密码',
  `salt` varchar(20) DEFAULT '' COMMENT '盐加密',
  `status` char(1) DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(50) DEFAULT '' COMMENT '最后登陆IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '103', 'admin', 'Layduo', '00', 'ry@163.com', '15888888888', '1', '/img/userhead.jpg', '29c67a30398638269fe600f73a054934', '111111', '0', '0', '127.0.0.1', '2019-11-21 16:11:55', 'admin', '2018-03-16 11:33:00', 'ry', '2019-11-21 16:11:55', '管理员');
INSERT INTO `sys_user` VALUES ('2', '105', 'ry', '若依', '00', 'ry@qq.com', '15666666666', '1', '', '8e6d98b90472783cc73c17047ddccf36', '222222', '0', '0', '127.0.0.1', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '测试员');

-- ----------------------------
-- Table structure for `sys_user_online`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_online`;
CREATE TABLE `sys_user_online` (
  `sessionId` varchar(50) NOT NULL DEFAULT '' COMMENT '用户会话id',
  `login_name` varchar(50) DEFAULT '' COMMENT '登录账号',
  `dept_name` varchar(50) DEFAULT '' COMMENT '部门名称',
  `ipaddr` varchar(50) DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) DEFAULT '' COMMENT '操作系统',
  `status` varchar(10) DEFAULT '' COMMENT '在线状态on_line在线off_line离线',
  `start_timestamp` datetime DEFAULT NULL COMMENT 'session创建时间',
  `last_access_time` datetime DEFAULT NULL COMMENT 'session最后访问时间',
  `expire_time` int(5) DEFAULT '0' COMMENT '超时时间，单位为分钟',
  PRIMARY KEY (`sessionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='在线用户记录';

-- ----------------------------
-- Records of sys_user_online
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户和角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1');
INSERT INTO `sys_user_role` VALUES ('2', '2');

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `user_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `user_name` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'root', '123456');
