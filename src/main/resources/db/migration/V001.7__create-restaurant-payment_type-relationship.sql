CREATE TABLE restaurant_payment_types (
    payment_type_id bigint not null,
    restaurant_id bigint not null,
    primary key (restaurant_id, payment_type_id),
    constraint fk_payment_type_restaurant foreign key (restaurant_id) references restaurant(id),
    constraint fk_restaurant_payment_type foreign key (payment_type_id) references payment_type(id)
) engine=InnoDB default charset=utf8;