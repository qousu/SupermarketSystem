/*
 Navicat Premium Dump SQL

 Source Server         : 本机
 Source Server Type    : MySQL
 Source Server Version : 80039 (8.0.39)
 Source Host           : localhost:3306
 Source Schema         : supermarket

 Target Server Type    : MySQL
 Target Server Version : 80039 (8.0.39)
 File Encoding         : 65001

 Date: 05/04/2025 11:35:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `cart_id` int NOT NULL AUTO_INCREMENT COMMENT '购物车ID',
  `cart_user_id` int NOT NULL COMMENT '用户Id，用于绑定用户\r\n',
  `cart_goods` json NULL COMMENT '购物车信息，这是一个列表用于保存商品Id，以及加入购物车的时间',
  PRIMARY KEY (`cart_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cart
-- ----------------------------
INSERT INTO `cart` VALUES (1, 1, '[{\"price\": 325.6, \"addTime\": \"2025-04-03 22:27:07\", \"goodsId\": \"6b8a2e2d948e428d\", \"imageUrl\": \"/img/goods/6b8a2e2d948e428d.png\", \"quantity\": 2, \"goodsName\": \"GTB(电子版)\"}, {\"price\": 380.0, \"addTime\": \"2025-04-05 11:29:10\", \"goodsId\": \"141d53e4eaac40fa\", \"imageUrl\": \"/img/goods/141d53e4eaac40fa.png\", \"quantity\": 1, \"goodsName\": \"运动风衣\"}]');
INSERT INTO `cart` VALUES (4, 2, '[{\"price\": 19889.0, \"addTime\": \"2025-04-04 11:29:26\", \"goodsId\": \"e0c4e693c2f44fdb\", \"imageUrl\": \"/img/goods/e0c4e693c2f44fdb.png\", \"quantity\": 1, \"goodsName\": \"苹果18Plus\"}, {\"price\": 325.6, \"addTime\": \"2025-04-04 11:29:33\", \"goodsId\": \"6b8a2e2d948e428d\", \"imageUrl\": \"/img/goods/6b8a2e2d948e428d.png\", \"quantity\": 1, \"goodsName\": \"GTB(电子版)\"}]');
INSERT INTO `cart` VALUES (5, 12, '[{\"price\": 325.6, \"addTime\": \"2025-04-04 12:07:45\", \"goodsId\": \"6b8a2e2d948e428d\", \"imageUrl\": \"/img/goods/6b8a2e2d948e428d.png\", \"quantity\": 1, \"goodsName\": \"GTB(电子版)\"}, {\"price\": 26.9, \"addTime\": \"2025-04-04 20:11:57\", \"goodsId\": \"ff64d2e705fb4e20\", \"imageUrl\": \"/img/goods/ff64d2e705fb4e20.png\", \"quantity\": 1, \"goodsName\": \"洗衣液\"}]');

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `goods_id` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '商品id（UUID）',
  `goods_name` varchar(23) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `goods_type` bigint NOT NULL COMMENT '商品类似',
  `price` decimal(10, 2) NOT NULL COMMENT '价格',
  `image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '主图',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '商品介绍',
  `inventory` int NOT NULL COMMENT '库存',
  `datetime` datetime NOT NULL COMMENT '入库时间',
  `updateTime` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`goods_id`) USING BTREE,
  INDEX `goods type`(`goods_type` ASC) USING BTREE,
  CONSTRAINT `goods type` FOREIGN KEY (`goods_type`) REFERENCES `goodstype` (`goods_type_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('05b59bd6163d458b', '运动鞋', 6, 499.70, '/img/goods/05b59bd6163d458b.png', '你的跑步伴侣，优选运动鞋', 245, '2025-04-04 12:50:02', '2025-04-04 12:50:02');
INSERT INTO `goods` VALUES ('141d53e4eaac40fa', '运动风衣', 1, 380.00, '/img/goods/141d53e4eaac40fa.png', '运动风衣', 450, '2025-04-04 12:45:50', '2025-04-04 18:13:15');
INSERT INTO `goods` VALUES ('154c249116d544b7', '测试2', 5, 258.00, NULL, '测试2', 272, '2025-03-31 23:25:11', '2025-03-31 23:25:11');
INSERT INTO `goods` VALUES ('17faaf2045d24534', '测试5', 5, 4114.00, NULL, '测试', 665, '2025-03-31 23:26:12', '2025-03-31 23:26:12');
INSERT INTO `goods` VALUES ('4037395a69db4b17', '测试9', 2, 1214.00, NULL, '测试', 121, '2025-04-01 21:43:56', '2025-04-01 21:43:56');
INSERT INTO `goods` VALUES ('4cc55950710a4d05', '纸巾', 8, 15.00, '/img/goods/4cc55950710a4d05.png', '生活纸巾，4层抽纸，450抽大抽', 500, '2025-04-04 12:47:55', '2025-04-04 12:47:55');
INSERT INTO `goods` VALUES ('554761f03cf24898', '测试8', 4, 113.00, NULL, '测试', 1412, '2025-04-01 21:43:46', '2025-04-01 21:43:46');
INSERT INTO `goods` VALUES ('6b373b60a75d4e91', '智能热水壶', 8, 45.00, '/img/goods/6b373b60a75d4e91.png', '智能热水壶', 130, '2025-04-04 12:48:25', '2025-04-04 12:48:25');
INSERT INTO `goods` VALUES ('6b8a2e2d948e428d', 'GTB(电子版)', 4, 325.60, '/img/goods/6b8a2e2d948e428d.png', '国内特供版，限量', 33, '2025-03-03 23:16:15', '2025-04-03 23:22:18');
INSERT INTO `goods` VALUES ('7017080951d84ea7', '办公电脑', 3, 8999.90, NULL, '办公电脑', 33, '2025-04-04 12:42:39', '2025-04-04 12:42:39');
INSERT INTO `goods` VALUES ('7059480b465945e6', '腊肠', 1, 34.00, '/img/goods/7059480b465945e6.png', '二八腊肠，精品优选', 100, '2025-04-04 12:43:32', '2025-04-04 18:12:23');
INSERT INTO `goods` VALUES ('913e2e6aaaef445a', '测试15', 1, 141.00, NULL, '测试', 141, '2025-04-01 21:44:58', '2025-04-01 21:44:58');
INSERT INTO `goods` VALUES ('97a307b7658d4818', '测试6', 5, 1324.00, NULL, '测试', 12, '2025-04-01 21:43:21', '2025-04-01 21:43:21');
INSERT INTO `goods` VALUES ('98e4a61dc2ca4c76', '测试79', 6, 1387.00, NULL, '测试', 12, '2025-04-01 21:43:04', '2025-04-01 21:43:04');
INSERT INTO `goods` VALUES ('ab99f49e0c3f4a23', '测试10', 4, 1321.00, NULL, '测试', 12, '2025-04-01 21:44:08', '2025-04-01 21:44:08');
INSERT INTO `goods` VALUES ('ce1307ee592441dd', '悠闲鞋', 6, 499.00, '/img/goods/ce1307ee592441dd.png', '悠闲鞋', 124, '2025-04-04 12:44:27', '2025-04-04 12:44:27');
INSERT INTO `goods` VALUES ('d9939b6dd8b34bd9', '牛肉丸零食', 1, 69.90, '/img/goods/d9939b6dd8b34bd9.png', '1号饭堂一号饭堂开袋即食 潮汕特产休闲小吃泡面搭档500g 混合装', 1344, '2025-04-04 12:53:04', '2025-04-04 12:53:04');
INSERT INTO `goods` VALUES ('e0c4e693c2f44fdb', '苹果18Plus', 2, 19889.00, '/img/goods/e0c4e693c2f44fdb.png', '苹果18Plus，你值得拥有！', 30, '2025-03-31 23:23:12', '2025-03-31 23:23:12');
INSERT INTO `goods` VALUES ('e692d3b4a54c4d41', '小米19Plus', 2, 8888.00, '/img/goods/e692d3b4a54c4d41.png', '小米19Plus', 333, '2025-03-31 23:24:08', '2025-03-31 23:24:08');
INSERT INTO `goods` VALUES ('e7a9442baed84a57', '男士西装', 1, 1099.00, '/img/goods/e7a9442baed84a57.png', '男士西装', 123, '2025-04-04 12:45:10', '2025-04-04 16:58:28');
INSERT INTO `goods` VALUES ('ff64d2e705fb4e20', '洗衣液', 1, 26.90, '/img/goods/ff64d2e705fb4e20.png', '加量不加价', 145, '2025-04-04 12:49:06', '2025-04-04 17:54:27');

-- ----------------------------
-- Table structure for goodstype
-- ----------------------------
DROP TABLE IF EXISTS `goodstype`;
CREATE TABLE `goodstype`  (
  `goods_type_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品类型的Id',
  `goods_type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品类型的名称',
  PRIMARY KEY (`goods_type_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goodstype
-- ----------------------------
INSERT INTO `goodstype` VALUES (1, '美味食品');
INSERT INTO `goodstype` VALUES (2, '手机数码');
INSERT INTO `goodstype` VALUES (3, '电脑办公');
INSERT INTO `goodstype` VALUES (4, '游戏娱乐');
INSERT INTO `goodstype` VALUES (5, '音频设备');
INSERT INTO `goodstype` VALUES (6, '优品服饰');
INSERT INTO `goodstype` VALUES (7, '智能穿戴');
INSERT INTO `goodstype` VALUES (8, '生活用品');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(100) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(23) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名称',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户密码',
  `age` int NULL DEFAULT NULL COMMENT '用户年龄',
  `number` int NULL DEFAULT NULL COMMENT '手机号码',
  `email` varchar(23) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址信息',
  `is_admin` tinyint NOT NULL COMMENT '1管理员；0用户',
  `gender` int NULL DEFAULT NULL COMMENT '用户性别',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001, 'test', '1234', 33, NULL, NULL, NULL, 1, NULL, NULL);
INSERT INTO `user` VALUES (0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000002, 'lisi', '1234', 38, 100860, '178109@qq.com', '广东白云区123街道8号', 0, 0, '2025-03-22 21:52:02');
INSERT INTO `user` VALUES (0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000004, '王5', '101', 28, 1008710, '8759@qq.com', '广东白云区xxx街道66号', 0, 0, '2025-02-24 21:52:10');
INSERT INTO `user` VALUES (0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000005, '李琴', '00035', 77, 100860, NULL, '广东白云区xxx街道6号', 0, 0, '2022-05-25 21:52:14');
INSERT INTO `user` VALUES (0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000006, '张三', '123456789', 28, 100860, 'zhangsan@gemil.com', '广东白云区xxx街道77号', 0, 0, '2025-03-05 21:52:21');
INSERT INTO `user` VALUES (0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000008, '王丽', '12311', 48, 100860, '1234@qq.com', '广东白云区xxx街道68号', 0, 0, NULL);
INSERT INTO `user` VALUES (0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000009, '吴琪琪', '8888', 59, 100860, '1234@qq.com', '广东白云区xxx街道68号', 0, 1, NULL);
INSERT INTO `user` VALUES (0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000010, '吴三贵', '12379', 20, 100860, '1234@qq.com', '广东白云区xxx街道68号', 0, 1, '2025-03-23 13:17:31');
INSERT INTO `user` VALUES (0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000011, '吴梓琪', '1233', 19, 100860, 'zhangsan@example.com', '广东白云区xxx街道68号', 0, 1, '2025-03-23 23:09:31');
INSERT INTO `user` VALUES (0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000012, 'root', '123456789', NULL, NULL, '999@qq.com', NULL, 1, NULL, '2025-04-04 11:51:10');

SET FOREIGN_KEY_CHECKS = 1;
