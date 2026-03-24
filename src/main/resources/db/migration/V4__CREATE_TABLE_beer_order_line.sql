create table beer_order_line
(
    id           varchar(36) not null,
    beer_id VARCHAR(36),
    created_date datetime(6),
    last_modified_date datetime(6),
    order_quantity int,
    quantity_allocated int,
    version bigint,
    beer_order_id varchar(36),
    primary key (id),
    CONSTRAINT fk_beer_order_line_beer_order
        FOREIGN KEY (beer_order_id)
            REFERENCES beer_order(id),
    CONSTRAINT fk_beer_order_line_beer
        FOREIGN KEY (beer_id)
            REFERENCES beer(id)
)engine=InnoDB;