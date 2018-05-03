drop table if exists computer;
drop table if exists company;

create table company (
    ca_id                        bigint not null generated by default as identity(start with 1, increment by 1),
    ca_name                      varchar(255) not null,
    constraint pk_company primary key (ca_id))
;

create table computer (
    cu_id                        bigint not null generated by default as identity(start with 1, increment by 1),
    cu_name                      varchar(255),
    cu_introduced                datetime NULL,
    cu_discontinued              datetime NULL,
    ca_id                bigint default NULL,
    constraint pk_computer primary key (cu_id),
    constraint fk_company foreign key (ca_id) references company(ca_id))
;

insert into company (ca_id,ca_name) values (1,'Company 1');
insert into company (ca_id,ca_name) values (2,'Company 2');
insert into company (ca_id,ca_name) values (3,'COmpany 3');

insert into computer (cu_id,cu_name,cu_introduced,cu_discontinued,ca_id) values (1,'Computer 1','0001-01-01','0001-01-02',1);
insert into computer (cu_id,cu_name,cu_introduced,cu_discontinued,ca_id) values (2,null,null,null,null);
insert into computer (cu_id,cu_name,cu_introduced,cu_discontinued,ca_id) values (3,'Computer 3',null,'1994-09-12', 3);
