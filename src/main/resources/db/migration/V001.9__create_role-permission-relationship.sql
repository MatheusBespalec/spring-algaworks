CREATE TABLE role_permissions (
    role_id bigint not null,
    permission_id bigint not null,
    primary key (role_id, permission_id),
    constraint fk_permission_role foreign key (role_id) references role(id),
    constraint fk_role_permission foreign key (permission_id) references permission(id)
) engine=InnoDB default charset=utf8;