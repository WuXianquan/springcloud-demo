create database if not exists `sell` default charset utf8 collate utf8_general_ci;

SET foreign_key_checks = 0;
-- ----------------------------
-- Table structure for 't_order'
-- ----------------------------
DROP TABLE IF EXISTS t_order;
CREATE TABLE t_order (
  id bigint(20) NOT NULL,
  user_id bigint(20) NOT NULL,
  order_amount decimal(10,2) NOT NULL,
  order_status tinyint(4) NOT NULL,
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO t_order VALUES ('298690918834311168', '123456789', '510.00', '0', '2019-06-12 15:37:56');

-- ----------------------------
-- Table structure for 't_order_detail'
-- ----------------------------
DROP TABLE IF EXISTS t_order_detail;
CREATE TABLE t_order_detail (
  id bigint(20) NOT NULL,
  order_id bigint(20) NOT NULL,
  product_id bigint(20) NOT NULL,
  product_number int(10) NOT NULL,
  product_price decimal(5,2) NOT NULL,
  amount decimal(20,2) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_order_detail
-- ----------------------------
INSERT INTO t_order_detail VALUES ('298690924865720320', '298690918834311168', '298614743086075904', '5', '60.00', '300.00');
INSERT INTO t_order_detail VALUES ('298690926241452032', '298690918834311168', '298660629273055232', '3', '70.00', '210.00');

-- ----------------------------
-- Table structure for 't_product'
-- ----------------------------
DROP TABLE IF EXISTS t_product;
CREATE TABLE t_product (
  id bigint(20) NOT NULL,
  name varchar(255) NOT NULL,
  price decimal(5,2) NOT NULL,
  stock bigint(20) DEFAULT 0,
  frozen_stock bigint(20) DEFAULT 0,
  status tinyint(4) NOT NULL,
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_product
-- ----------------------------
INSERT INTO t_product VALUES ('298614743086075904', 'Java设计模式', '60.00', '15', '5', '1', '2019-06-12 10:35:13');
INSERT INTO t_product VALUES ('298660629273055232', 'Java从入门到放弃', '70.00', '27', '3', '1', '2019-06-12 13:37:33');

-- ----------------------------
-- Table structure for 't_user'
-- ----------------------------
DROP TABLE IF EXISTS t_user;
CREATE TABLE t_user (
  id bigint(20) NOT NULL,
  username varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  status tinyint(1) NOT NULL,
  score decimal(10,2) NOT NULL DEFAULT 0,
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO t_user VALUES ('123456', 'admin', '8715a4f66d9eeddba20b5453edc41bd7', 1, 0, '2019-06-12 10:35:13');

