CREATE TABLE state (
   id bigint not null auto_increment,
   name char(2) not null,
   primary key(id)
) engine=InnoDB default charset=utf8;

ALTER TABLE city ADD COLUMN state_id bigint not null;
ALTER TABLE city ADD CONSTRAINT fk_city_state FOREIGN KEY (state_id) REFERENCES state(id);