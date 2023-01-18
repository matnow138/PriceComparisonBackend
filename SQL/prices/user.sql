create table user
(
    id       bigint auto_increment
        primary key,
    isActive bit          not null,
    lastName varchar(255) null,
    login    varchar(255) not null,
    mail     varchar(255) null,
    name     varchar(255) null,
    password varchar(255) not null
)
    engine = MyISAM;

