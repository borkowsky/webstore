alter table reviews
    add order_id integer not null references orders (id);
create index reviews_order_id_idx on reviews (order_id);