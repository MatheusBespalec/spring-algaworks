CREATE TABLE restaurant (
    id bigint not null auto_increment,
    name varchar(80) not null,
    freight_rate decimal(38, 2) not null default 0,
    created_at datetime not null default current_timestamp,
    updated_at datetime not null default current_timestamp on update current_timestamp,
    address_street varchar(150) not null,
    address_neighborhood varchar(80) not null,
    address_number varchar(30) not null,
    address_complement varchar(30),
    address_zip_code char(8) not null,
    address_city_id bigint not null,
    category_id bigint not null,
    primary key (id),
    constraint fk_restaurant_category foreign key (category_id) references restaurant_category(id),
    constraint fk_restaurant_city foreign key (address_city_id) references city(id)
) engine=InnoDB default charset=utf8;