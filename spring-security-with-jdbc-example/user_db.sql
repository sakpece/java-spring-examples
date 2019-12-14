
create schema user_db ; 

use user_db ;

create table user (user_id int not null ,
username varchar (50),
password varchar (50),
email varchar (50),
phone varchar (50)
);

insert into userdetails values (1,'sakpece','test123','sakpece@gmail.com','9989289129');

insert into userdetails values (2,'nasarath','test123','naasrath@gmail.com','9990889129');

create table role (user_id int not null , role varchar (50) );

insert into role values (1,'ADMIN');

insert into role values (2,'USER');

select u.username, r.role from userdetails u left join  role r on u.user_id = role.user_id where username='sakpece';

select * from userdetails u left join role r on u.user_id = r.user_id where username  = 'sakpece'; 


