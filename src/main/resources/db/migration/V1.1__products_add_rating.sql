alter table products add rating float not null default 0.0;
create index products_rating_idx on products (rating);