alter table pedido_importacao
add column usuario_id bigint unsigned not null after file,
add constraint fk_usuario_pedido_importacao foreign key(usuario_id) references usuario(id);