# Host: localhost  (Version: 5.5.53)
# Date: 2019-06-11 17:20:50
# Generator: MySQL-Front 5.3  (Build 4.234)

/*!40101 SET NAMES utf8 */;

#
# Structure for table "hostname_map"
#

CREATE TABLE `hostname_map` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `hostname` varchar(255) NOT NULL DEFAULT '' COMMENT '主机名称',
  `address` varchar(255) NOT NULL DEFAULT '' COMMENT '主机ip地址',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `hostId` varchar(255) DEFAULT NULL COMMENT 'id标识',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `address` (`address`),
  UNIQUE KEY `hostname` (`hostname`)
) ENGINE=MyISAM AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

#
# Data for table "hostname_map"
#

INSERT INTO `hostname_map` VALUES (19,'yunwei_server2','39.108.227.22','服务器','748209c9634b6d9de40e5863b24dbb47'),(22,'zhengxian','localhost','本机\n','c9af2aafbf82d13673989d0fc83ae448');

#
# Structure for table "nailing_robot"
#

CREATE TABLE `nailing_robot` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `rootId` varchar(255) NOT NULL DEFAULT '' COMMENT '机器人Id',
  `rootName` varchar(255) NOT NULL DEFAULT '' COMMENT '机器人名字',
  `rootToken` varchar(255) NOT NULL DEFAULT '' COMMENT '机器人Token',
  PRIMARY KEY (`Id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

#
# Data for table "nailing_robot"
#

INSERT INTO `nailing_robot` VALUES (2,'5ecef153b23c439d2c629d78e4d10ff8','开发测试机器人','2dced5c5d02e3a8b2769e61236bba976ab3dce7eb6de7883b14c2617145f7006'),(3,'ecb463b4a0c0664500238a93c7bd3be3','钉钉测试','2dced5c5d02e3a8b2769e61236bba976ab3dce7eb6de7883b14c2617145f7006');

#
# Structure for table "schedule"
#

CREATE TABLE `schedule` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `hostname` varchar(255) NOT NULL DEFAULT '' COMMENT '主机名字',
  `type` varchar(255) NOT NULL DEFAULT '' COMMENT '监控类型',
  `threshold` varchar(255) NOT NULL DEFAULT '0' COMMENT '阈值',
  `taskId` varchar(255) NOT NULL DEFAULT '0' COMMENT '定时任务Id',
  `taskName` varchar(255) NOT NULL DEFAULT '' COMMENT '任务名称',
  `taskType` varchar(255) NOT NULL DEFAULT '' COMMENT '定时类型（秒，分钟，小时，天）',
  `taskValue` varchar(255) NOT NULL DEFAULT '' COMMENT '任务时间',
  `taskState` varchar(255) NOT NULL DEFAULT '' COMMENT '任务状态（运行，暂停）',
  `operationType` varchar(255) NOT NULL DEFAULT '' COMMENT '存储判断监控（比如大于等于，小于等于）',
  `ReminderType` varchar(255) DEFAULT '' COMMENT '通知类型DingTalkRobot("钉钉机器人"), EMail("邮件通知"),SMS("手机短信");',
  `ReminderId` varchar(255) NOT NULL DEFAULT '' COMMENT '通知Id',
  `customExpression` varchar(255) DEFAULT NULL COMMENT '自定义通知表达式，空则默认',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `taskId` (`taskId`)
) ENGINE=MyISAM AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

#
# Data for table "schedule"
#

INSERT INTO `schedule` VALUES (38,'zhengxian','Cpu','60','274d0d8ee61aef85d8838ac035788c21','任务名称测试1','Hour','10','Stop','Gte','DingTalkRobot','5ecef153b23c439d2c629d78e4d10ff8','{主机名称}'),(39,'zhengxian','Memory','43','d46142c6e138052fbd4311549c219aa9','内存定时任务','Second','60','Stop','Gte','DingTalkRobot','5ecef153b23c439d2c629d78e4d10ff8','主机名称 ： {主机名称}\n监控类型 ：{监控类型}\n超过阈值数量 :{超过阈值数量}\n超过记录数据 ：{超过记录数据}'),(41,'zhengxian','Cpu','98','dd9de33d15fba516846d065aae5f33f5','钉钉测试发送','Second','60','Stop','Lte','DingTalkRobot','5ecef153b23c439d2c629d78e4d10ff8','前端测试：\n主机名称 ： {主机名称}\n监控类型 ：{监控类型}\n超过阈值数量 :{超过阈值数量}\n超过记录数据 ：{超过记录数据}');
