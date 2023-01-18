create table amazon_offers
(
    user_id   bigint not null,
    amazon_id bigint not null
)
    engine = MyISAM;

create index FK6l25179d0m4r1lxvm4l252vxx
    on amazon_offers (user_id);

create index FK7b81yaoqjesopbtw9yi99ntau
    on amazon_offers (amazon_id);

