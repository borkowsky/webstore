create type payments_status as enum ('CREATED', 'APPROVED', 'REJECTED');

alter table payments add status payments_status not null default 'CREATED';