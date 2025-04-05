# 超市管理系统（SupermarKetSystem）

### 技术栈

这是一个后端基于SpringBoot的框架制作，其中SpringBoot整合了MybatisPlus、应用了thymeleaf模版引擎
前端上，基于Bootstrap框架和vue3的使用和JQuery库，以及前端三剑客html、css、javaScript
调试上，使用了git管理调试

## 数据库设计
```
CREATE DATABASE SimpleSupermarket;
USE SimpleSupermarket;

-- 用户表（包含管理员）
CREATE TABLE users (
user_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '用户唯一标识ID',
username VARCHAR(50) UNIQUE NOT NULL COMMENT '登录用户名（唯一）',
password_hash CHAR(60) NOT NULL COMMENT 'BCrypt加密后的密码',
email VARCHAR(100) UNIQUE NOT NULL COMMENT '用户邮箱（唯一）',
phone VARCHAR(20) COMMENT '联系电话（允许为空）',
address VARCHAR(255) COMMENT '简化地址信息（省市区+详细地址）',
is_admin BOOLEAN DEFAULT FALSE COMMENT '管理员标识：true-管理员，false-普通用户',
created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '账户创建时间'
) COMMENT='用户信息表';

-- 商品表
CREATE TABLE products (
product_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '商品唯一标识ID',
product_name VARCHAR(255) NOT NULL COMMENT '商品展示名称',
category VARCHAR(50) COMMENT '商品分类（如：食品/日用品/电器）',
price DECIMAL(10,2) NOT NULL CHECK (price > 0) COMMENT '当前售价（单位：元）',
stock INT DEFAULT 0 CHECK (stock >= 0) COMMENT '当前库存数量',
description TEXT COMMENT '商品详细描述',
image_url VARCHAR(255) COMMENT '商品主图URL地址',
is_available BOOLEAN DEFAULT TRUE COMMENT '上架状态：true-在售，false-下架',
created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '商品创建时间'
) COMMENT='商品信息表';

-- 购物车表
CREATE TABLE cart (
cart_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '购物车唯一标识ID',
user_id INT NOT NULL UNIQUE COMMENT '关联用户ID（每个用户一个购物车）',
items JSON COMMENT '购物车商品数据，格式：[{"product_id":1,"quantity":2}]',
updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间'
) COMMENT='用户购物车表';

-- 订单表
CREATE TABLE orders (
order_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '订单唯一标识ID（自增序列）',
user_id INT NOT NULL COMMENT '下单用户ID',
total DECIMAL(10,2) NOT NULL COMMENT '订单总金额（计算后金额）',
address VARCHAR(255) NOT NULL COMMENT '下单时的地址快照',
status ENUM('待支付', '已支付', '已发货', '已完成', '已取消') DEFAULT '待支付'
COMMENT '订单状态：待支付/已支付/已发货/已完成/已取消',
payment_method VARCHAR(20) COMMENT '支付方式（如：支付宝/微信支付）',
created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '订单创建时间'
) COMMENT='订单主表';

-- 订单商品表
CREATE TABLE order_items (
order_item_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '订单项唯一标识ID',
order_id INT NOT NULL COMMENT '关联订单ID',
product_id INT NOT NULL COMMENT '商品ID',
quantity INT NOT NULL COMMENT '购买数量',
price DECIMAL(10,2) NOT NULL COMMENT '下单时的商品单价快照'
) COMMENT='订单商品明细表';

-- 外键约束（在添加注释后单独声明）
ALTER TABLE cart ADD CONSTRAINT fk_cart_user
FOREIGN KEY (user_id) REFERENCES users(user_id);

ALTER TABLE orders ADD CONSTRAINT fk_order_user
FOREIGN KEY (user_id) REFERENCES users(user_id);

ALTER TABLE order_items ADD CONSTRAINT fk_orderitem_order
FOREIGN KEY (order_id) REFERENCES orders(order_id);

ALTER TABLE order_items ADD CONSTRAINT fk_orderitem_product
FOREIGN KEY (product_id) REFERENCES products(product_id);
```

### 项目结构设计

- main
  - java
    - org
      - example
        - supermarketsystem
          - pojo
          - dao
          - service
          - mapper
          - config
          - controller
          - utils
  - resources
    - static
    - templates
    - users
    - commons
    - error

### GitHub账号


