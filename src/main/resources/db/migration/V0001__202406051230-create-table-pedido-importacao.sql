create table pedido_importacao (
    id bigint unsigned auto_increment primary key,
    cpf varchar(11) not null,
    senha varchar(20) not null,
    status varchar(20) not null default "NOVO",
    log varchar(255),

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  	updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  	delete_at TIMESTAMP
);