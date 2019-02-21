drop database lexicon

create database lexicon

use lexicon

create table dict(

id int(11) auto_increment not null primary key,

type int(11) not null,

word nvarchar(20) not null,

createtime timestamp DEFAULT CURRENT_TIMESTAMP

);

create table publish(

id int(11) auto_increment not null primary key,

type int(11) not null,

version int(11) not null,

createtime timestamp DEFAULT CURRENT_TIMESTAMP

);