alter table reviews drop column image;

alter table reviews add images character varying(255)[] not null default '{}'