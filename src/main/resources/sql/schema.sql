CREATE SEQUENCE public.devices_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.devices_id_seq
  OWNER TO postgres;

CREATE SEQUENCE public.metrics_history_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.metrics_history_id_seq
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
  access_id text
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
  code text
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.metrics
  OWNER TO postgres;


CREATE TABLE public.metrics_history
(
  id integer NOT NULL DEFAULT nextval('metrics_history_id_seq'::regclass),
  device_id integer,
  value text,
  date timestamp without time zone DEFAULT now(),
  code text
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.metrics_history
  OWNER TO postgres;

CREATE TABLE public.switches
(
  id integer NOT NULL DEFAULT nextval('switches_id_seq'::regclass),
  name text,
  description text,
  pin integer,
  device_id integer,
  parent_id integer,
  state integer
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.switches
  OWNER TO postgres;
