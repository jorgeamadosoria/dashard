CREATE SEQUENCE public.devices_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.devices_id_seq
  OWNER TO postgres;

CREATE SEQUENCE public.metrics_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.metrics_id_seq
  OWNER TO postgres;

CREATE SEQUENCE public.switches_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.switches_id_seq
  OWNER TO postgres;

CREATE TABLE public.devices
(
  id integer NOT NULL DEFAULT nextval('devices_id_seq'::regclass),
  name text,
  description text,
  access_id text,
  enabled boolean DEFAULT true
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.devices
  OWNER TO postgres;


CREATE TABLE public.metrics
(
  id integer NOT NULL DEFAULT nextval('metrics_id_seq'::regclass),
  device_id integer,
  name text,
  value text,
  date timestamp without time zone,
  code text,
  type text,
  enabled boolean DEFAULT true
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.metrics
  OWNER TO postgres;

CREATE TABLE public.switches
(
  id integer NOT NULL DEFAULT nextval('switches_id_seq'::regclass),
  name text,
  description text,
  pin integer,
  device_id integer,
  parent_id integer,
  state integer,
  enabled boolean DEFAULT true,
  status text DEFAULT 'SYNCED'::text
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.switches
  OWNER TO postgres;

  create table users(
    username varchar(50) not null,
    password varchar(50) not null,
    enabled boolean not null default true,
     PRIMARY KEY (username)
);

create table authorities (
    username varchar(50) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users foreign key(username) references users(username)
);
  