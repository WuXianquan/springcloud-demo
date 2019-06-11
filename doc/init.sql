create database springcloud_demo;

create table t_order(
	id bigint not null,
	username varchar(20) not null,
	amount bigint not null,
	status tinyint not null,
	create_time timestamp default current_timestamp,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

create table t_product(
	id bigint not null,
	name varchar(255) not null,
	price bigint not null,
	stock bigint null default 0,
	frozen_stock bigint null default 0,
	status tinyint not null,
	create_time timestamp default current_timestamp,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

create table t_order_detail(
	id bigint not null,
	order_id bigint not null,
	product_id bigint not null,
	productNumber int(255) not null,
	productPrice bigint not null,
	amount bigint not null,
	create_time timestamp default current_timestamp,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

create table t_order_exit(
	order_id bigint not null,
	detail_id bigint not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

