/*
Navicat MySQL Data Transfer

Source Server         : 192.168.140.210
Source Server Version : 50735
Source Host           : 192.168.140.210:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50735
File Encoding         : 65001

Date: 2021-12-12 10:30:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `seata_one`
-- ----------------------------
DROP TABLE IF EXISTS `seata_one`;
CREATE TABLE `seata_one` (
`id`  int(11) NOT NULL ,
`name`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci

;

-- ----------------------------
-- Records of seata_one
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `seata_two`
-- ----------------------------
DROP TABLE IF EXISTS `seata_two`;
CREATE TABLE `seata_two` (
`id`  int(11) NOT NULL ,
`name`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci

;

-- ----------------------------
-- Records of seata_two
-- ----------------------------
BEGIN;
COMMIT;
