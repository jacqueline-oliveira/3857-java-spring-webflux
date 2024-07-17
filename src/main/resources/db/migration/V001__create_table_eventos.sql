create table eventos(
    id bigserial not null,
    tipo varchar(30) not null,
    nome varchar(100) not null,
    data date,
    descricao varchar(200) not null,

    primary key(id)
);
