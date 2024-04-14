CREATE table product (
     id bigint not null auto_increment,
     name varchar(80) not null,
     price decimal(38, 2) not null,
     active bit default 1,
     restaurant_id bigint not null,
     primary key (id),
     constraint fk_product_restaurant foreign key (restaurant_id) references restaurant(id)
) engine=InnoDB default charset=utf8;