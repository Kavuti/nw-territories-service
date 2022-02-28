create table if not exists region(
    id bigint not null primary key,
    description varchar(255) not null
);

create table if not exists territory(
    id bigint not null primary key,
    description varchar(255) not null,
    region_id bigint not null,
    constraint fk_territory_region foreign key (region_id) references region(id);
);

CREATE SEQUENCE if not exists seq_region
    INCREMENT BY 50
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1;

CREATE SEQUENCE if not exists seq_territory
    INCREMENT BY 50
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1;