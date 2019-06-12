create database springcloud_demo;

create table t_order(
	id bigint(20) not null,
	user_id bigint(20) not null,
	order_amount decimal(10,2) not null,
	order_status tinyint(1) not null,
	create_time timestamp default current_timestamp,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

create table t_product(
	id bigint(20) not null,
	name varchar(255) not null,
	price decimal(5,2) not null,
	stock bigint null default 0,
	frozen_stock bigint null default 0,
	status tinyint(1) not null,
	create_time timestamp default current_timestamp,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

create table t_order_detail(
	id bigint(20) not null,
	order_id bigint(20) not null,
	product_id bigint(20) not null,
	product_number int(20) not null,
	product_price decimal(5,2) not null,
	amount decimal(5,2) not null,
	create_time timestamp default current_timestamp,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

