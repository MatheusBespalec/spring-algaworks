insert into kitchen (name) values ("Brasileira"), ("Chinesa"), ("Indiana");

insert into restaurant (name, freight_rate, kitchen_id, created_at, updated_at) values ("100 Miséria", 10, 1, utc_timestamp, utc_timestamp), ("LigLig", 12.5, 2, utc_timestamp, utc_timestamp), ("Tuk Tuk", 0, 3, utc_timestamp, utc_timestamp)

insert into state (name) values ("SP"), ("SC"), ("RJ")
insert into city (name, state_id) values ("Santo André", 1), ("Criciuma", 2), ("Rio de Janeiro", 3)

insert into payment_type (description) values ("Cartão de Débito"), ("Cartão de Crédito"), ("Dinheiro")
insert into restaurant_payment_types (restaurant_id, payment_type_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 1), (3, 2)