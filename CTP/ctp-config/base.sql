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

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: conf_seguridad_publica; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE conf_seguridad_publica (
    id bigint NOT NULL,
    version bigint NOT NULL,
    perfil character varying(255) NOT NULL,
    rol character varying(255) NOT NULL,
    servicio_gis_id bigint,
    usuario character varying(255) NOT NULL
);


ALTER TABLE public.conf_seguridad_publica OWNER TO postgres;

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

--
-- Name: metodo; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE metodo (
    id bigint NOT NULL,
    version bigint NOT NULL,
    nombre character varying(255) NOT NULL,
    nombre_xml character varying(255) NOT NULL,
    seguridad_id bigint,
    servicio_gis_id bigint NOT NULL
);


ALTER TABLE public.metodo OWNER TO postgres;

--
-- Name: seguridad; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE seguridad (
    id bigint NOT NULL,
    version bigint NOT NULL,
    ip character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    perfil character varying(255) NOT NULL,
    roles character varying(255) NOT NULL,
    servicio_gis_id bigint,
    token character varying(255) NOT NULL,
    usuario character varying(255) NOT NULL
);


ALTER TABLE public.seguridad OWNER TO postgres;

--
-- Name: servicio_gis; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE servicio_gis (
    id bigint NOT NULL,
    version bigint NOT NULL,
    direccion_logica character varying(255) NOT NULL,
    direccion_proxy character varying(255) NOT NULL,
    nombre character varying(255) NOT NULL,
    publico boolean NOT NULL
);


ALTER TABLE public.servicio_gis OWNER TO postgres;

--
-- Data for Name: conf_seguridad_publica; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY conf_seguridad_publica (id, version, perfil, rol, servicio_gis_id, usuario) FROM stdin;
4	0	Publico_Meteorologia	ou:publico	1	pubmet
\.


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('hibernate_sequence', 4, true);


--
-- Data for Name: metodo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY metodo (id, version, nombre, nombre_xml, seguridad_id, servicio_gis_id) FROM stdin;
3	0	getMap	http://meteo.gub.uy/serviciosGis/Lluvias/getMap	\N	1
\.


--
-- Data for Name: seguridad; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY seguridad (id, version, ip, password, perfil, roles, servicio_gis_id, token, usuario) FROM stdin;
\.


--
-- Data for Name: servicio_gis; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY servicio_gis (id, version, direccion_logica, direccion_proxy, nombre, publico) FROM stdin;
1	0	http://meteo.gub.uy/serviciosGis/Lluvias	http://localhost:8080/GISWSwmsYwfs/GISWSwmsYwfs	meteorologia	f
2	0	http://meteo.gub.uy/serviciosGis/Lluvias	http://localhost:8080/GISWSwmsYwfs/GISWSwmsYwfs	mtop_rutas	f
\.


--
-- Name: conf_seguridad_publica_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY conf_seguridad_publica
    ADD CONSTRAINT conf_seguridad_publica_pkey PRIMARY KEY (id);


--
-- Name: metodo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY metodo
    ADD CONSTRAINT metodo_pkey PRIMARY KEY (id);


--
-- Name: seguridad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY seguridad
    ADD CONSTRAINT seguridad_pkey PRIMARY KEY (id);


--
-- Name: servicio_gis_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY servicio_gis
    ADD CONSTRAINT servicio_gis_pkey PRIMARY KEY (id);


--
-- Name: fk_6ohleqtwh10vfqq4yqvw4yb37; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY conf_seguridad_publica
    ADD CONSTRAINT fk_6ohleqtwh10vfqq4yqvw4yb37 FOREIGN KEY (servicio_gis_id) REFERENCES servicio_gis(id);


--
-- Name: fk_ieax7ai74elnioieajwuuimym; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY seguridad
    ADD CONSTRAINT fk_ieax7ai74elnioieajwuuimym FOREIGN KEY (servicio_gis_id) REFERENCES servicio_gis(id);


--
-- Name: fk_nkks5ptia28071jmoj5jrxwf1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY metodo
    ADD CONSTRAINT fk_nkks5ptia28071jmoj5jrxwf1 FOREIGN KEY (seguridad_id) REFERENCES seguridad(id);


--
-- Name: fk_tw8v2go2fxala723ssxmkoac; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY metodo
    ADD CONSTRAINT fk_tw8v2go2fxala723ssxmkoac FOREIGN KEY (servicio_gis_id) REFERENCES servicio_gis(id);


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

