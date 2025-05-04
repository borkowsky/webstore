-- brands table
create table brands
(
    id         serial                 not null primary key,
    name       character varying(255) not null unique,
    image      character varying(255) not null,
    deleted    boolean                not null default false,
    deleted_by integer                         default null references users (id) on delete cascade,
    deleted_at timestamp                       default null
);
create index brands_name_idx on brands (name);

alter table products
    add brand_id integer default null references brands (id) on delete set null;
create index products_brand_idx on products (brand_id);