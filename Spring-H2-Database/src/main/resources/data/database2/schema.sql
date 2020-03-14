drop table if exists user_info;

create table user_info(
    id int primary key,
    user_name varchar(100),
    age smallint ,
    address varchar(200)
);