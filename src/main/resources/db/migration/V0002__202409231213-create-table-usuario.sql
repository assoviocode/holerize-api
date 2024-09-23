create table usuario (
	id bigint unsigned not null auto_increment primary key,
	nome varchar(255) not null,
	email varchar(255) default null,
	login varchar(255) not null unique,
	senha TEXT(255) not null,
  	created_at DATETIME(6) default null,
	updated_at DATETIME(6) default null,
	deleted_at DATETIME(6) default null
);