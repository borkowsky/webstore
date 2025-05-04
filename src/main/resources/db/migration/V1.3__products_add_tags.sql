alter table products
    add tags character varying(255)[] not null default '{}';
create index products_tags_idx on products using gin (tags array_ops);