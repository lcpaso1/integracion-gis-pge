--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: metodo; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE metodo (
    id bigint NOT NULL,
    version bigint NOT NULL,
    nombre character varying(255) NOT NULL,
    servicio_id bigint NOT NULL
);


ALTER TABLE public.metodo OWNER TO postgres;

--
-- Name: perfil; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE perfil (
    id bigint NOT NULL,
    version bigint NOT NULL,
    nombre character varying(255) NOT NULL
);


ALTER TABLE public.perfil OWNER TO postgres;

--
-- Name: perfil_metodo; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE perfil_metodo (
    perfil_metodos_id bigint,
    metodo_id bigint
);


ALTER TABLE public.perfil_metodo OWNER TO postgres;

--
-- Name: rol; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE rol (
    id bigint NOT NULL,
    version bigint NOT NULL,
    nombre character varying(255) NOT NULL
);


ALTER TABLE public.rol OWNER TO postgres;

--
-- Name: rol_perfil; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE rol_perfil (
    rol_perfiles_id bigint,
    perfil_id bigint
);


ALTER TABLE public.rol_perfil OWNER TO postgres;

--
-- Name: servicio; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE servicio (
    id bigint NOT NULL,
    version bigint NOT NULL,
    dir_fisica character varying(255) NOT NULL,
    dir_logica character varying(255) NOT NULL,
    nombre character varying(255) NOT NULL
);


ALTER TABLE public.servicio OWNER TO postgres;

--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('hibernate_sequence', 5, true);


--
-- Data for Name: metodo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY metodo (id, version, nombre, servicio_id) FROM stdin;
3	0	http://meteo.gub.uy/serviciosGis/Lluvias/getMap	1
\.


--
-- Data for Name: perfil; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY perfil (id, version, nombre) FROM stdin;
4	0	Publico_Meteorologia
\.


--
-- Data for Name: perfil_metodo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY perfil_metodo (perfil_metodos_id, metodo_id) FROM stdin;
4	3
\.


--
-- Data for Name: rol; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY rol (id, version, nombre) FROM stdin;
5	1	ou:publico
\.


--
-- Data for Name: rol_perfil; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY rol_perfil (rol_perfiles_id, perfil_id) FROM stdin;
5	4
\.


--
-- Data for Name: servicio; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY servicio (id, version, dir_fisica, dir_logica, nombre) FROM stdin;
1	0	http://localhost:8080	http://meteo.gub.uy/serviciosGis/Lluvias	meteorologia
2	0	http://localhost:8080	http://mtop.gub.uy/serviciosGis/Rutas	mtop_rutas
\.


--
-- Name: metodo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY metodo
    ADD CONSTRAINT metodo_pkey PRIMARY KEY (id);


--
-- Name: perfil_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY perfil
    ADD CONSTRAINT perfil_pkey PRIMARY KEY (id);


--
-- Name: rol_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rol
    ADD CONSTRAINT rol_pkey PRIMARY KEY (id);


--
-- Name: servicio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY servicio
    ADD CONSTRAINT servicio_pkey PRIMARY KEY (id);


--
-- Name: uk_5sp1r1csf8w09psuq7p8fatbs; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY servicio
    ADD CONSTRAINT uk_5sp1r1csf8w09psuq7p8fatbs UNIQUE (nombre);


--
-- Name: uk_bbwvgmvlibssky1e8mls20wos; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY servicio
    ADD CONSTRAINT uk_bbwvgmvlibssky1e8mls20wos UNIQUE (dir_logica);


--
-- Name: fk_12trsgwkg8136qkbyay33ier2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rol_perfil
    ADD CONSTRAINT fk_12trsgwkg8136qkbyay33ier2 FOREIGN KEY (rol_perfiles_id) REFERENCES rol(id);


--
-- Name: fk_1n8aml7baaurpjuyosh6v6r61; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY metodo
    ADD CONSTRAINT fk_1n8aml7baaurpjuyosh6v6r61 FOREIGN KEY (servicio_id) REFERENCES servicio(id);


--
-- Name: fk_8vy893ai353y2xxb17ydcpjl5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY perfil_metodo
    ADD CONSTRAINT fk_8vy893ai353y2xxb17ydcpjl5 FOREIGN KEY (perfil_metodos_id) REFERENCES perfil(id);


--
-- Name: fk_nkfeeo36xbaqy9ifmpb93np69; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY perfil_metodo
    ADD CONSTRAINT fk_nkfeeo36xbaqy9ifmpb93np69 FOREIGN KEY (metodo_id) REFERENCES metodo(id);


--
-- Name: fk_sk3bntof9bjnas6pgea2640ko; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rol_perfil
    ADD CONSTRAINT fk_sk3bntof9bjnas6pgea2640ko FOREIGN KEY (perfil_id) REFERENCES perfil(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

