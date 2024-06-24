alter table pedido_importacao modify column ano_de int unsigned not null after log;
alter table pedido_importacao modify column ano_ate int unsigned not null after ano_de;