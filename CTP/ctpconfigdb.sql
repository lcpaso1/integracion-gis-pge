-- El comando para hacer este dump fue : pg_dump -U postgres ctpconfig > ctpconfigdb.sql
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
    nombre character varying(255) NOT NULL
);


ALTER TABLE public.metodo OWNER TO postgres;

--
-- Name: metodo_perfil; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE metodo_perfil (
    metodo_perfiles_id bigint,
    perfil_id bigint
);


ALTER TABLE public.metodo_perfil OWNER TO postgres;

--
-- Name: perfil; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE perfil (
    id bigint NOT NULL,
    version bigint NOT NULL,
    nombre character varying(255) NOT NULL,
    rol_id bigint NOT NULL
);


ALTER TABLE public.perfil OWNER TO postgres;

--
-- Name: perfil_metodos; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE perfil_metodos (
    metodo_id bigint NOT NULL,
    perfil_id bigint NOT NULL
);


ALTER TABLE public.perfil_metodos OWNER TO postgres;

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
-- Name: servicio_metodo; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE servicio_metodo (
    servicio_metodos_id bigint,
    metodo_id bigint
);


ALTER TABLE public.servicio_metodo OWNER TO postgres;

--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('hibernate_sequence', 10, true);


--
-- Data for Name: metodo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY metodo (id, version, nombre) FROM stdin;
7	1	Met1XXXX
9	0	madm_1
\.


--
-- Data for Name: metodo_perfil; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY metodo_perfil (metodo_perfiles_id, perfil_id) FROM stdin;
7	5
7	4
7	6
9	5
9	4
\.


--
-- Data for Name: perfil; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY perfil (id, version, nombre, rol_id) FROM stdin;
4	0	Perf1	2
5	0	Admin1	1
6	0	Admin2	2
8	0	perfrol2	2
\.


--
-- Data for Name: perfil_metodos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY perfil_metodos (metodo_id, perfil_id) FROM stdin;
\.


--
-- Data for Name: rol; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY rol (id, version, nombre) FROM stdin;
1	0	rol1
2	0	rol2
3	0	rol3
\.


--
-- Data for Name: servicio; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY servicio (id, version, dir_fisica, dir_logica, nombre) FROM stdin;
10	0	12.100.2.1	webiss	to->webis
\.


--
-- Data for Name: servicio_metodo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY servicio_metodo (servicio_metodos_id, metodo_id) FROM stdin;
10	9
10	7
\.


--
-- Name: metodo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY metodo
    ADD CONSTRAINT metodo_pkey PRIMARY KEY (id);


--
-- Name: perfil_metodos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY perfil_metodos
    ADD CONSTRAINT perfil_metodos_pkey PRIMARY KEY (perfil_id, metodo_id);


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
-- Name: fk_2a0c39uxwrl244bd0ddwvd134; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY servicio_metodo
    ADD CONSTRAINT fk_2a0c39uxwrl244bd0ddwvd134 FOREIGN KEY (servicio_metodos_id) REFERENCES servicio(id);


--
-- Name: fk_54njvm9mfmkkv1fix1ptxpe7l; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY perfil
    ADD CONSTRAINT fk_54njvm9mfmkkv1fix1ptxpe7l FOREIGN KEY (rol_id) REFERENCES rol(id);


--
-- Name: fk_5w72ayoccpyqpdsnnr2xq62oj; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY metodo_perfil
    ADD CONSTRAINT fk_5w72ayoccpyqpdsnnr2xq62oj FOREIGN KEY (perfil_id) REFERENCES perfil(id);


--
-- Name: fk_c1gyrr7p11v20l59i5lmnrlw2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY perfil_metodos
    ADD CONSTRAINT fk_c1gyrr7p11v20l59i5lmnrlw2 FOREIGN KEY (perfil_id) REFERENCES perfil(id);


--
-- Name: fk_ce50fdtpqrwdu2yeatyryemik; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY perfil_metodos
    ADD CONSTRAINT fk_ce50fdtpqrwdu2yeatyryemik FOREIGN KEY (metodo_id) REFERENCES metodo(id);


--
-- Name: fk_d35p1flwrjjgyepwdtq3etw0e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY metodo_perfil
    ADD CONSTRAINT fk_d35p1flwrjjgyepwdtq3etw0e FOREIGN KEY (metodo_perfiles_id) REFERENCES metodo(id);


--
-- Name: fk_m8hk5lapukg9qrrv0et6rko88; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY servicio_metodo
    ADD CONSTRAINT fk_m8hk5lapukg9qrrv0et6rko88 FOREIGN KEY (metodo_id) REFERENCES metodo(id);


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

