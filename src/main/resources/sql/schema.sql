-- Database: dashard

-- DROP DATABASE dashard;

CREATE DATABASE dashard
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'Spanish_Uruguay.1252'
       LC_CTYPE = 'Spanish_Uruguay.1252'
       CONNECTION LIMIT = -1;

-- Sequence: public.devices_id_seq

-- DROP SEQUENCE public.devices_id_seq;

CREATE SEQUENCE public.devices_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 36
  CACHE 1;
ALTER TABLE public.devices_id_seq
  OWNER TO postgres;

  -- Sequence: public.metrics_id_seq

-- DROP SEQUENCE public.metrics_id_seq;

CREATE SEQUENCE public.metrics_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 70
  CACHE 1;
ALTER TABLE public.metrics_id_seq
  OWNER TO postgres;

  -- Sequence: public.switches_id_seq

-- DROP SEQUENCE public.switches_id_seq;

CREATE SEQUENCE public.switches_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 21
  CACHE 1;
ALTER TABLE public.switches_id_seq
  OWNER TO postgres;

  -- Table: public.authorities

-- DROP TABLE public.authorities;

CREATE TABLE public.authorities
(
  username character varying(50) NOT NULL,
  authority character varying(50) NOT NULL,
  CONSTRAINT fk_authorities_users FOREIGN KEY (username)
      REFERENCES public.users (username) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.authorities
  OWNER TO postgres;

  -- Table: public.devices

-- DROP TABLE public.devices;

CREATE TABLE public.devices
(
  id integer NOT NULL DEFAULT nextval('devices_id_seq'::regclass),
  name text,
  description text,
  access_id text,
  enabled boolean DEFAULT true,
  userid character varying(50),
  CONSTRAINT fk_device_user FOREIGN KEY (userid)
      REFERENCES public.users (username) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.devices
  OWNER TO postgres;

-- Index: public.fki_device_user

-- DROP INDEX public.fki_device_user;

CREATE INDEX fki_device_user
  ON public.devices
  USING btree
  (userid COLLATE pg_catalog."default");


  -- Table: public.metrics

-- DROP TABLE public.metrics;

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

-- Table: public.switches

-- DROP TABLE public.switches;

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

  -- Table: public.users

-- DROP TABLE public.users;

CREATE TABLE public.users
(
  username character varying(50) NOT NULL,
  password character varying(50) NOT NULL,
  enabled boolean NOT NULL DEFAULT true,
  CONSTRAINT users_pkey PRIMARY KEY (username)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.users
  OWNER TO postgres;
