ALTER TABLE pedido_importacao
RENAME COLUMN mes_de TO mes_de_baixado;

ALTER TABLE pedido_importacao
RENAME COLUMN ano_de TO ano_de_baixado;

ALTER TABLE pedido_importacao
RENAME COLUMN mes_ate TO mes_ate_baixado;

ALTER TABLE pedido_importacao
RENAME COLUMN ano_ate TO ano_ate_baixado;

ALTER TABLE pedido_importacao
MODIFY COLUMN mes_de_baixado INT UNSIGNED NULL;

ALTER TABLE pedido_importacao
MODIFY COLUMN ano_de_baixado INT UNSIGNED NULL;

ALTER TABLE pedido_importacao
MODIFY COLUMN mes_ate_baixado INT UNSIGNED NULL;

ALTER TABLE pedido_importacao
MODIFY COLUMN ano_ate_baixado INT UNSIGNED NULL;

ALTER TABLE pedido_importacao
ADD COLUMN mes_de INT UNSIGNED AFTER tipo_erro;

ALTER TABLE pedido_importacao
ADD COLUMN ano_de INT UNSIGNED AFTER mes_de;

ALTER TABLE pedido_importacao
ADD COLUMN mes_ate INT UNSIGNED AFTER ano_de;

ALTER TABLE pedido_importacao
ADD COLUMN ano_ate INT UNSIGNED AFTER mes_ate;
