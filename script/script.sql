create schema gerenciamento_curriculo;

CREATE table login(
    id_login INTEGER primary key,
    email text not null unique,
    senha text not null    
);

CREATE SEQUENCE user_id START 1;

create table endereco(
	id_endereco integer,
	cep text not null,
	numero integer not null,
	logradouro text not null,
	bairro text not null,
	cidade text not null,
	primary key (id_endereco)
	on delete cascade
);

create sequence seq_endereco
increment 1
start 1;

create table candidato(
	id_candidato integer primary key,
	id_endereco integer not null,
	nome text not null,
	cpf char(11) not null unique,
	data_nascimento date not null,
	telefone char(14) not null unique,
	senioridade text not null,
	cargo text not null,
	curriculo_url text not null,
	constraint fk_candidato_endereco foreign key (id_endereco)
        references endereco (id_endereco)
        on delete cascade
);

create sequence seq_candidato
increment 1
start 1;

create table escolaridade(
	id_escolaridade integer,
	id_candidato integer not null,
	instituicao text not null,
	descricao text not null,
	nivel text not null,
	data_inicio date not null,
	data_fim date not null,
	primary key (id_escolaridade),
    constraint fk_escolaridade_candidato foreign key (id_candidato)
    references candidato (id_candidato)
    on delete cascade
);

create sequence seq_escolaridade
increment 1
start 1;

create table experiencia(
	id_experiencia integer,
	id_candidato integer not null,
	instituicao text not null,
	descricao text not null,
	cargo text not null,
	data_inicio date not null,
	data_fim date not null,
	primary key (id_experiencia),
    constraint fk_experiencia_candidato foreign key (id_candidato)
    references candidato (id_candidato)
    on delete cascade
);

create sequence seq_experiencia
increment 1
start 1;

create table vaga(
	id_vagas integer primary key
);

create table vaga_candidato(
	id_vagas integer,
	id_candidato integer,
	primary key(id_vagas, id_candidato),
	constraint fk_vagas_candidato_vaga foreign key (id_vagas) 
    references vaga (id_vagas), 
    constraint fk_vagas_candidato_candidato foreign key (id_candidato) 
    references candidato (id_candidato) 
    on delete cascade
);