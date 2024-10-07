alter table pedido_importacao
modify column status varchar(20) not null default 'NA_FILA'