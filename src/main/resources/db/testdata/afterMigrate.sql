set foreign_key_checks = 0;

delete from city;
delete from payment_type;
delete from permission;
delete from product;
delete from restaurant;
delete from restaurant_category;
delete from restaurant_payment_types;
delete from role;
delete from role_permissions;
delete from state;
delete from user;

set foreign_key_checks = 1;

alter table city auto_increment = 1;
alter table payment_type auto_increment = 1;
alter table permission auto_increment = 1;
alter table product auto_increment = 1;
alter table restaurant auto_increment = 1;
alter table restaurant_category auto_increment = 1;
alter table restaurant_payment_types auto_increment = 1;
alter table role auto_increment = 1;
alter table role_permissions auto_increment = 1;
alter table state auto_increment = 1;
alter table user auto_increment = 1;

insert into state (name) values ("SP"), ("SC"), ("RJ");
insert into city (name, state_id) values ("Santo André", 1), ("Criciuma", 2), ("Rio de Janeiro", 3);
insert into payment_type (description) values ("Cartão de Débito"), ("Cartão de Crédito"), ("Dinheiro");
insert into restaurant_category (name) values ("Hamburgueria"), ("Pizzaria"), ("Comida Japonesa");

insert into restaurant
    (name, freight_rate, address_street, address_neighborhood, address_number, address_zip_code, address_city_id, category_id)
values
    ("TOP Burguer", 10, "Rua alfredo nobrega", "Vila Nova", 22, "08100352", 1, 1),
    ("Don Patrone", 12.5, "Av. Portugal", "Centro", 321, "05400654", 2, 2),
    ("Nipon", 15, "Av. Fortuna", "Jd. Oriental", 12, "49500999", 3, 3);

insert into restaurant_payment_types (restaurant_id, payment_type_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 1), (3, 2);