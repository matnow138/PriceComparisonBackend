create table amazonoffer
(
    id             bigint auto_increment
        primary key,
    AddedDate      date           null,
    asin           varchar(255)   not null,
    CurrencySymbol varchar(255)   null,
    CurentPrice    decimal(19, 2) null,
    locale         varchar(255)   null,
    ProductName    varchar(255)   null,
    TargetPrice    decimal(19, 2) null
)
    engine = MyISAM;

