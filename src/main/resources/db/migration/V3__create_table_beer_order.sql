create table beer_order
(
    id           varchar(36) not null,
    created_date datetime(6),
    customer_ref         varchar(255),
    last_modified_date datetime(6),
    version      bigint,
    customer_id varchar(36),
    primary key (id),
    CONSTRAINT fk_beer_order_customer
        FOREIGN KEY (customer_id)
            REFERENCES customer(id)
)engine=InnoDB;