/*
 Navicat MySQL Data Transfer

 Source Server         : localmysql
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : smartrobot

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 26/08/2023 19:34:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for application
-- ----------------------------
DROP TABLE IF EXISTS `application`;
CREATE TABLE `application`  (
  `account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `isaccepted` int NULL DEFAULT NULL,
  `num` int NULL DEFAULT NULL,
  PRIMARY KEY (`account`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for evaluate
-- ----------------------------
DROP TABLE IF EXISTS `evaluate`;
CREATE TABLE `evaluate`  (
  `eid` int NOT NULL AUTO_INCREMENT,
  `rid` int NULL DEFAULT NULL,
  `account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `isevaluated` int NULL DEFAULT 0,
  PRIMARY KEY (`eid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for history
-- ----------------------------
DROP TABLE IF EXISTS `history`;
CREATE TABLE `history`  (
  `hid` int NOT NULL AUTO_INCREMENT,
  `account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mid` int NULL DEFAULT NULL,
  `rid` int NULL DEFAULT NULL,
  `htime` varchar(22) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`hid`) USING BTREE,
  INDEX `account`(`account`) USING BTREE,
  INDEX `rid`(`rid`) USING BTREE,
  CONSTRAINT `account` FOREIGN KEY (`account`) REFERENCES `login` (`account`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `rid` FOREIGN KEY (`rid`) REFERENCES `reply` (`rid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for login
-- ----------------------------
DROP TABLE IF EXISTS `login`;
CREATE TABLE `login`  (
  `account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`account`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `mid` int NOT NULL AUTO_INCREMENT,
  `account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mpath` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mcontent` varchar(1500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `hot` int NULL DEFAULT 0,
  `mtime` varchar(22) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `isreply` int UNSIGNED NULL DEFAULT 0,
  PRIMARY KEY (`mid`) USING BTREE,
  INDEX `account1`(`account`) USING BTREE,
  CONSTRAINT `account1` FOREIGN KEY (`account`) REFERENCES `login` (`account`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `delete_flag` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for reply
-- ----------------------------
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply`  (
  `rid` int NOT NULL AUTO_INCREMENT,
  `account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `rpath` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `rcontent` varchar(1500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `rtime` varchar(22) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mid` int NOT NULL,
  `rrank` int NULL DEFAULT 0,
  PRIMARY KEY (`rid`) USING BTREE,
  INDEX `account2`(`account`) USING BTREE,
  CONSTRAINT `account2` FOREIGN KEY (`account`) REFERENCES `login` (`account`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for userimage
-- ----------------------------
DROP TABLE IF EXISTS `userimage`;
CREATE TABLE `userimage`  (
  `account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `address` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  INDEX `account3`(`account`) USING BTREE,
  CONSTRAINT `account3` FOREIGN KEY (`account`) REFERENCES `login` (`account`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
