create table orders_products
(
    id         serial  not null primary key,
    product_id integer not null references products (id) on delete cascade,
    order_id   integer not null references orders (id) on delete cascade,
    amount     integer not null
);

create index orders_products_idx on orders_products (product_id, order_id);

alter table orders drop column product_id;