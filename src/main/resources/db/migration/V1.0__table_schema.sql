--users table
create type users_role as enum ('USER', 'MANAGER', 'ADMINISTRATOR');
create table if not exists users
(
    id         serial                 not null primary key,
    username   character varying(255) not null unique,
    password   character varying(255) not null,
    email      character varying(255) not null unique,
    role       users_role             not null default 'USER',
    balance    float                  not null default 0.0,
    deleted    boolean                not null default false,
    created_at timestamp              not null default now(),
    updated_at timestamp              not null default now(),
    deleted_at timestamp                       default null,
    deleted_by integer                         default null references users (id) on delete set null
);
create index if not exists users_email_idx on users (email);
create index if not exists users_username_idx on users (username);
create index if not exists users_deleted_idx on users (deleted);


-- categories table
create table if not exists categories
(
    id          serial                 not null primary key,
    name        character varying(255) not null,
    description text                            default null,
    icon        character varying(255)          default null,
    category_id integer                         default null references categories (id) on delete cascade,
    deleted     boolean                not null default false,
    enabled     boolean                not null default true,
    created_at  timestamp              not null default now(),
    updated_at  timestamp              not null default now(),
    deleted_at  timestamp                       default null,
    deleted_by  integer                         default null references users (id) on delete set null
);
create index if not exists categories_category_id_idx on categories (category_id);
create index if not exists categories_deleted_idx on categories (deleted);
create index if not exists categories_enabled_idx on categories (enabled);

-- products table
create table if not exists products
(
    id             serial                   not null primary key,
    name           character varying(255)   not null,
    description    text                     not null,
    category_id    integer                  not null references categories (id) on delete cascade,
    price          float                    not null,
    discount_price float                             default null,
    balance        integer                  not null,
    images         character varying(255)[] not null default '{}',
    deleted        boolean                  not null default false,
    enabled        boolean                  not null default true,
    created_at     timestamp                not null default now(),
    updated_at     timestamp                not null default now(),
    deleted_at     timestamp                         default null,
    deleted_by     integer                           default null references users (id) on delete set null
);
create index if not exists products_category_id_idx on products (category_id);
create index if not exists products_deleted_idx on products (deleted);
create index if not exists products_enabled_idx on products (enabled);

-- reviews table
create table if not exists reviews
(
    id         serial    not null primary key,
    rating     integer   not null,
    text       text                   default null,
    user_id    integer   not null references users (id) on delete cascade,
    product_id integer   not null references products (id) on delete cascade,
    image      character varying(255) default null,
    deleted    boolean   not null     default false,
    created_at timestamp not null     default now(),
    updated_at timestamp not null     default now(),
    deleted_at timestamp              default null,
    deleted_by integer                default null references users (id)
);
create index if not exists reviews_product_id_idx on reviews (product_id);
create index if not exists reviews_user_id_idx on reviews (user_id);
create index if not exists reviews_deleted_idx on reviews (deleted);

-- basket table
create table if not exists basket
(
    id         serial    not null primary key,
    user_id    integer   not null references users (id) on delete cascade,
    product_id integer   not null references products (id) on delete cascade,
    amount     integer   not null default 1,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now()
);
create index if not exists basket_user_id_idx on basket (user_id);

-- orders table
create type orders_status as enum ('CREATED', 'PAID', 'ACCEPTED', 'REJECTED', 'DELIVERY', 'DELIVERED', 'RECEIVED');
create table if not exists orders
(
    id         serial        not null primary key,
    user_id    integer       not null references users (id) on delete cascade,
    product_id integer       not null references products (id) on delete cascade,
    status     orders_status not null default 'CREATED',
    paid       float         not null,
    deleted    boolean       not null default false,
    created_at timestamp     not null default now(),
    updated_at timestamp     not null default now(),
    deleted_at timestamp              default null,
    deleted_by integer                default null references users (id)
);
create index if not exists orders_deleted_idx on orders (deleted);

-- favorites table
create table if not exists favorites
(
    id         serial    not null primary key,
    user_id    integer   not null references users (id) on delete cascade,
    product_id integer   not null references products (id) on delete cascade,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now()
);
create index if not exists favorites_user_id_idx on favorites (user_id);

-- events table
create table if not exists events
(
    id         serial    not null primary key,
    user_id    integer   not null references users (id) on delete cascade,
    text       text      not null,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now()
);
create index if not exists events_user_id_idx on events (user_id);

-- stats table
create table if not exists stats
(
    id         serial    not null primary key,
    users      integer   not null default 0,
    orders     integer   not null default 0,
    reviews    integer   not null default 0,
    paid       float     not null default 0.0,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now()
);