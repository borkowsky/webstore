alter table orders
    add address_id integer not null references addresses (id);