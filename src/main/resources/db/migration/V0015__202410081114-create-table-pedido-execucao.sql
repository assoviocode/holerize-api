create table pedido_execucao(
    id bigint unsigned not null auto_increment primary key,
    uuid varchar(36) not null unique,
    pedido_importacao_id bigint unsigned not null,
    status varchar(20) not null default "NA_FILA",
    log text,
    tipo_erro varchar(100),
    mes_de int unsigned not null,
    ano_de int unsigned not null,
    mes_ate int unsigned not null,
    ano_ate int unsigned not null,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at timestamp NULL DEFAULT NULL,
    constraint fk_pedido_importacao_id_pedido_execucao foreign key (pedido_importacao_id) references pedido_importacao(id)
);

DELIMITER //

create trigger before_insert_pedido_execucao
before insert on pedido_execucao
for each row
begin
    if new.uuid is null then
        set new.uuid = UUID();
    end if;
end;
//

DELIMITER ;