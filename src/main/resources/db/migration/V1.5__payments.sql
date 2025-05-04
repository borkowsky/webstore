create table payments
(
    id         serial    not null primary key,
    user_id    integer   not null,
    sum        float     not null default 0.0,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now(),
    deleted    boolean   not null default false,
    deleted_by integer            default null references users (id) on delete set null,
    deleted_at timestamp          default null
);
create index payments_user_id on payments (user_id);

alter table orders
    add payment_id integer default null references payments (id) on delete set null;
alter table orders
    drop column paid;