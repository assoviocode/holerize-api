CREATE TABLE `pedido_importacao` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `cpf` varchar(11) NOT NULL,
  `senha` varchar(20) NOT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'NOVO',
  `log` varchar(255) DEFAULT NULL,
  `tipo_erro` varchar(100) DEFAULT NULL,
  `ano_de` int unsigned NOT NULL,
  `ano_ate` int unsigned NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
);