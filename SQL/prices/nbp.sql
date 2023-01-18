create table nbp
(
    id            bigint auto_increment
        primary key,
    added_date    date           null,
    currency      varchar(255)   null,
    exchange_rate decimal(19, 2) null,
    addedDate     date           null,
    exchangeRate  decimal(19, 2) null
)
    engine = MyISAM;

