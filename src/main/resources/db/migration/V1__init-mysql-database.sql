
    drop table if exists beer;

    drop table if exists customer;
    
--    DROP TABLE if exists flyway_schema_history;

    create table beer (
        beer_style smallint not null,
        price decimal(38,2) not null,
        version integer,
        created_date datetime(6),
        updated_date datetime(6),
        id varchar(36) not null,
        beer_name varchar(50) not null,
        quantity_on_hand Integer,
        upc varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table customer (
        version integer,
        created_date datetime(6),
        last_modifieddate datetime(6),
        id varchar(36) not null,
        customer_name varchar(60) not null,
        primary key (id)
    ) engine=InnoDB;
