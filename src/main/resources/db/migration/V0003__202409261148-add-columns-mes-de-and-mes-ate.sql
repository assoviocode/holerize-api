alter table pedido_importacao
add column `mes_de` int unsigned NOT NULL after `tipo_erro`,
add column `mes_ate` int unsigned NOT NULL after `ano_de`;