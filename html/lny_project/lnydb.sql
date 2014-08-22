create table users(
   uid int(11) primary key auto_increment,
   unique_id varchar(23) not null unique,
	name varchar(50),
	phonenumber varchar(80) not null unique,
   about varchar(100),
   degree varchar(50),
   seen boolean default false
);

create table tutorsubject(
	tid int(11) primary key auto_increment,
	phonenumber varchar(100) not null,
	criteria varchar(50) not null,
	subject varchar(50) not null,
	price decimal(10,2) not null
);

create table