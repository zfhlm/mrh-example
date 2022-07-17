/*
Navicat MySQL Data Transfer

Source Server         : 192.168.140.130
Source Server Version : 50737
Source Host           : 192.168.140.130:3306
Source Database       : user

Target Server Type    : MYSQL
Target Server Version : 50737
File Encoding         : 65001

Date: 2022-07-16 03:19:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('278679882', '939c96f0-50ee-495f-abfa-00cfe2c010d2');
INSERT INTO `t_user` VALUES ('408538640', '4a7934dd-00d9-4de9-b030-a18d7fed073d');
INSERT INTO `t_user` VALUES ('679300095', '59c8ff82-59e4-48d5-9cdb-18c6c552e7be');
INSERT INTO `t_user` VALUES ('771787887', 'c9bfeec7-e552-4027-b348-52602ea86e26');
INSERT INTO `t_user` VALUES ('867810827', '719a27e6-db71-42f0-be4d-e59b131ca3c9');
INSERT INTO `t_user` VALUES ('879048444', 'd891af56-11b1-41af-a968-439ebf9199dd');
INSERT INTO `t_user` VALUES ('1059254293', 'e6c259a1-c958-492e-99f2-43f94331c333');
INSERT INTO `t_user` VALUES ('1080078845', '63418d71-064a-4b11-83ba-278647e924bc');
INSERT INTO `t_user` VALUES ('1170087471', '6f47c2dc-febd-46a5-9591-22ca56b275a5');
INSERT INTO `t_user` VALUES ('1190870025', '068f89c3-7183-40e6-8ffb-fd3b90b63af5');
INSERT INTO `t_user` VALUES ('1202570387', 'ccf1fbdb-f167-48e5-a726-6e33607d95b9');
INSERT INTO `t_user` VALUES ('1265947442', 'ea744a9a-e395-4db9-bc93-5ad603204c8c');
INSERT INTO `t_user` VALUES ('1279245211', '1b0ffa04-5c3b-401a-b2e7-f2ecb6776816');
INSERT INTO `t_user` VALUES ('1284135170', '61655784-d61e-4735-84ab-41c358ca05b2');
INSERT INTO `t_user` VALUES ('1531487222', '63b83180-7ecd-4cba-a001-52d0e98c84fa');
INSERT INTO `t_user` VALUES ('1667005633', '07b5d355-f1f3-45d2-abf7-02036216a5e2');
INSERT INTO `t_user` VALUES ('1816025914', 'ec9174db-eadf-463c-ab9c-383c886ebdd4');
INSERT INTO `t_user` VALUES ('2029192683', 'c0a51efb-9c8c-456e-a7b4-e9fdef567903');
INSERT INTO `t_user` VALUES ('2034546019', '6a66184f-1849-4b43-9209-7d7e25744329');
INSERT INTO `t_user` VALUES ('2069992207', '382553f6-b0a1-4077-871b-86ae291bf585');
