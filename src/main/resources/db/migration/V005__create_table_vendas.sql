create table vendas(
    id bigserial not null,
    ingresso_id bigint not null,
    total int not null,

    primary key(id)
);