alter table pedido_importacao
add column uuid varchar(36) not null unique after id;