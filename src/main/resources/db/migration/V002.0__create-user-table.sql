CREATE TABLE user (
    id bigint not null auto_increment,
    name varchar(100) not null,
    email varchar(150) unique not null,
    password varchar(255) not null,
    created_at datetime not null default current_timestamp,
    primary key (id)
) engine=InnoDB default charset=utf8;