CREATE TABLE payment_type (
    id bigint not null auto_increment,
    description varchar(80) not null,
    primary key (id)
) engine=InnoDB default charset=utf8;