drop table if exists t_user;

create table t_user(
    id bigint identity,  /*identity 表示自增*/
    name varchar(50),
    address varchar(50),
    primary key (id)
);