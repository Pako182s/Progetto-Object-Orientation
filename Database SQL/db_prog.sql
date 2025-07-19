--
-- PostgreSQL database dump
--

-- Dumped from database version 17.5
-- Dumped by pg_dump version 17.5

-- Started on 2025-07-14 16:41:02

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 229 (class 1259 OID 17040)
-- Name: documenti_team; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.documenti_team (
    id integer NOT NULL,
    nome_team character varying(255) NOT NULL,
    contenuto text,
    data_modifica timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.documenti_team OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 17039)
-- Name: documenti_team_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.documenti_team_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.documenti_team_id_seq OWNER TO postgres;

--
-- TOC entry 4885 (class 0 OID 0)
-- Dependencies: 228
-- Name: documenti_team_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.documenti_team_id_seq OWNED BY public.documenti_team.id;


--
-- TOC entry 218 (class 1259 OID 16595)
-- Name: hackaton; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.hackaton (
    id integer NOT NULL,
    titolo character varying(255) NOT NULL,
    sede character varying(255),
    data_inizio date,
    data_fine date,
    numero_max_iscritti integer,
    component_max_team integer,
    inizio_iscrizioni date,
    fine_iscrizioni date,
    descrizione_problema text
);


ALTER TABLE public.hackaton OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16594)
-- Name: hackaton_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.hackaton_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.hackaton_id_seq OWNER TO postgres;

--
-- TOC entry 4886 (class 0 OID 0)
-- Dependencies: 217
-- Name: hackaton_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.hackaton_id_seq OWNED BY public.hackaton.id;


--
-- TOC entry 223 (class 1259 OID 16935)
-- Name: inviti_team; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.inviti_team (
    id integer NOT NULL,
    nome_team character varying(255) NOT NULL,
    mail_utente_invitato character varying(255) NOT NULL,
    data_invito timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.inviti_team OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16934)
-- Name: inviti_team_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.inviti_team_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.inviti_team_id_seq OWNER TO postgres;

--
-- TOC entry 4887 (class 0 OID 0)
-- Dependencies: 222
-- Name: inviti_team_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.inviti_team_id_seq OWNED BY public.inviti_team.id;


--
-- TOC entry 227 (class 1259 OID 17025)
-- Name: problema_hackathon; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.problema_hackathon (
    id integer NOT NULL,
    titolo_hackathon character varying(255) NOT NULL,
    testo_problema text,
    data_modifica timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.problema_hackathon OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 17024)
-- Name: problema_hackathon_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.problema_hackathon_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.problema_hackathon_id_seq OWNER TO postgres;

--
-- TOC entry 4888 (class 0 OID 0)
-- Dependencies: 226
-- Name: problema_hackathon_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.problema_hackathon_id_seq OWNED BY public.problema_hackathon.id;


--
-- TOC entry 220 (class 1259 OID 16910)
-- Name: team; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.team (
    nome_team character varying(100) NOT NULL,
    data_creazione timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.team OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16916)
-- Name: team_membri; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.team_membri (
    nome_team character varying(100) NOT NULL,
    mail_utente character varying(100) NOT NULL,
    data_adesione timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.team_membri OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16608)
-- Name: utenti; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.utenti (
    nome character varying(50) NOT NULL,
    password character varying(255) NOT NULL,
    mail character varying(100) NOT NULL,
    ruolo character varying(20) DEFAULT 'utente'::character varying NOT NULL
);


ALTER TABLE public.utenti OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 16989)
-- Name: voti_giudici; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.voti_giudici (
    id integer NOT NULL,
    nome_team character varying(255) NOT NULL,
    mail_giudice character varying(255) NOT NULL,
    voto integer NOT NULL,
    commento text,
    data_voto timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT voti_giudici_voto_check CHECK (((voto >= 1) AND (voto <= 10)))
);


ALTER TABLE public.voti_giudici OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 16988)
-- Name: voti_giudici_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.voti_giudici_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.voti_giudici_id_seq OWNER TO postgres;

--
-- TOC entry 4889 (class 0 OID 0)
-- Dependencies: 224
-- Name: voti_giudici_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.voti_giudici_id_seq OWNED BY public.voti_giudici.id;


--
-- TOC entry 4683 (class 2604 OID 17043)
-- Name: documenti_team id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.documenti_team ALTER COLUMN id SET DEFAULT nextval('public.documenti_team_id_seq'::regclass);


--
-- TOC entry 4673 (class 2604 OID 16598)
-- Name: hackaton id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.hackaton ALTER COLUMN id SET DEFAULT nextval('public.hackaton_id_seq'::regclass);


--
-- TOC entry 4677 (class 2604 OID 16938)
-- Name: inviti_team id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.inviti_team ALTER COLUMN id SET DEFAULT nextval('public.inviti_team_id_seq'::regclass);


--
-- TOC entry 4681 (class 2604 OID 17028)
-- Name: problema_hackathon id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.problema_hackathon ALTER COLUMN id SET DEFAULT nextval('public.problema_hackathon_id_seq'::regclass);


--
-- TOC entry 4679 (class 2604 OID 16992)
-- Name: voti_giudici id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.voti_giudici ALTER COLUMN id SET DEFAULT nextval('public.voti_giudici_id_seq'::regclass);


--
-- TOC entry 4879 (class 0 OID 17040)
-- Dependencies: 229
-- Data for Name: documenti_team; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.documenti_team (id, nome_team, contenuto, data_modifica) FROM stdin;
1	cc	fsadfs	2025-07-13 01:24:09.623906
\.


--
-- TOC entry 4868 (class 0 OID 16595)
-- Dependencies: 218
-- Data for Name: hackaton; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.hackaton (id, titolo, sede, data_inizio, data_fine, numero_max_iscritti, component_max_team, inizio_iscrizioni, fine_iscrizioni, descrizione_problema) FROM stdin;
1	Hackathon 2025	Napoli	2025-07-13	2025-07-18	20	3	2025-07-11	2025-07-12	\N
\.


--
-- TOC entry 4873 (class 0 OID 16935)
-- Dependencies: 223
-- Data for Name: inviti_team; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.inviti_team (id, nome_team, mail_utente_invitato, data_invito) FROM stdin;
5	cc	luca	2025-07-13 01:24:03.97645
\.


--
-- TOC entry 4877 (class 0 OID 17025)
-- Dependencies: 227
-- Data for Name: problema_hackathon; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.problema_hackathon (id, titolo_hackathon, testo_problema, data_modifica) FROM stdin;
1	Hackathon 2025	dsfsdf	2025-07-13 01:21:54.675451
\.


--
-- TOC entry 4870 (class 0 OID 16910)
-- Dependencies: 220
-- Data for Name: team; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.team (nome_team, data_creazione) FROM stdin;
gg	2025-07-12 14:33:13.932168
ddddd	2025-07-12 14:33:55.136345
maddy	2025-07-12 14:42:29.165219
kgbjh	2025-07-12 16:01:01.75552
cc	2025-07-12 16:05:58.129689
\.


--
-- TOC entry 4871 (class 0 OID 16916)
-- Dependencies: 221
-- Data for Name: team_membri; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.team_membri (nome_team, mail_utente, data_adesione) FROM stdin;
gg	gg	2025-07-12 14:33:13.932168
ddddd	dd	2025-07-12 14:33:55.136345
maddy	fe	2025-07-12 14:42:29.165219
maddy	lu	2025-07-12 15:58:38.729393
maddy	k	2025-07-12 16:00:55.583221
kgbjh	k	2025-07-12 16:01:01.75552
cc	cc	2025-07-12 16:05:58.129689
cc	p	2025-07-12 16:06:14.378587
cc	ff	2025-07-13 01:24:24.396989
\.


--
-- TOC entry 4869 (class 0 OID 16608)
-- Dependencies: 219
-- Data for Name: utenti; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.utenti (nome, password, mail, ruolo) FROM stdin;
Giudice	1234	g@mail.com	giudice
giacomo	gg	gg	utente
ff	ff	ff	utente
luca	1234	luca	utente
ciao	cc	cc	utente
fernanda maddacazza	fe	fe	utente
luko	lu	lu	utente
pp	p	p	utente
porco	dd	dd	giudice
k	k	k	giudice
\.


--
-- TOC entry 4875 (class 0 OID 16989)
-- Dependencies: 225
-- Data for Name: voti_giudici; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.voti_giudici (id, nome_team, mail_giudice, voto, commento, data_voto) FROM stdin;
1	gg	k	3	\N	2025-07-12 20:35:10.109317
2	cc	k	3	\N	2025-07-12 20:35:15.695302
\.


--
-- TOC entry 4890 (class 0 OID 0)
-- Dependencies: 228
-- Name: documenti_team_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.documenti_team_id_seq', 1, true);


--
-- TOC entry 4891 (class 0 OID 0)
-- Dependencies: 217
-- Name: hackaton_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hackaton_id_seq', 1, true);


--
-- TOC entry 4892 (class 0 OID 0)
-- Dependencies: 222
-- Name: inviti_team_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.inviti_team_id_seq', 5, true);


--
-- TOC entry 4893 (class 0 OID 0)
-- Dependencies: 226
-- Name: problema_hackathon_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.problema_hackathon_id_seq', 1, true);


--
-- TOC entry 4894 (class 0 OID 0)
-- Dependencies: 224
-- Name: voti_giudici_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.voti_giudici_id_seq', 2, true);


--
-- TOC entry 4711 (class 2606 OID 17050)
-- Name: documenti_team documenti_team_nome_team_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.documenti_team
    ADD CONSTRAINT documenti_team_nome_team_key UNIQUE (nome_team);


--
-- TOC entry 4713 (class 2606 OID 17048)
-- Name: documenti_team documenti_team_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.documenti_team
    ADD CONSTRAINT documenti_team_pkey PRIMARY KEY (id);


--
-- TOC entry 4687 (class 2606 OID 16602)
-- Name: hackaton hackaton_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.hackaton
    ADD CONSTRAINT hackaton_pkey PRIMARY KEY (id);


--
-- TOC entry 4701 (class 2606 OID 16945)
-- Name: inviti_team inviti_team_nome_team_mail_utente_invitato_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.inviti_team
    ADD CONSTRAINT inviti_team_nome_team_mail_utente_invitato_key UNIQUE (nome_team, mail_utente_invitato);


--
-- TOC entry 4703 (class 2606 OID 16943)
-- Name: inviti_team inviti_team_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.inviti_team
    ADD CONSTRAINT inviti_team_pkey PRIMARY KEY (id);


--
-- TOC entry 4709 (class 2606 OID 17033)
-- Name: problema_hackathon problema_hackathon_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.problema_hackathon
    ADD CONSTRAINT problema_hackathon_pkey PRIMARY KEY (id);


--
-- TOC entry 4699 (class 2606 OID 16921)
-- Name: team_membri team_membri_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team_membri
    ADD CONSTRAINT team_membri_pkey PRIMARY KEY (nome_team, mail_utente);


--
-- TOC entry 4695 (class 2606 OID 16915)
-- Name: team team_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team
    ADD CONSTRAINT team_pkey PRIMARY KEY (nome_team);


--
-- TOC entry 4689 (class 2606 OID 17023)
-- Name: hackaton unique_titolo; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.hackaton
    ADD CONSTRAINT unique_titolo UNIQUE (titolo);


--
-- TOC entry 4691 (class 2606 OID 16678)
-- Name: utenti utenti_mail_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utenti
    ADD CONSTRAINT utenti_mail_unique UNIQUE (mail);


--
-- TOC entry 4693 (class 2606 OID 16613)
-- Name: utenti utenti_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utenti
    ADD CONSTRAINT utenti_pkey PRIMARY KEY (nome);


--
-- TOC entry 4705 (class 2606 OID 17000)
-- Name: voti_giudici voti_giudici_nome_team_mail_giudice_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.voti_giudici
    ADD CONSTRAINT voti_giudici_nome_team_mail_giudice_key UNIQUE (nome_team, mail_giudice);


--
-- TOC entry 4707 (class 2606 OID 16998)
-- Name: voti_giudici voti_giudici_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.voti_giudici
    ADD CONSTRAINT voti_giudici_pkey PRIMARY KEY (id);


--
-- TOC entry 4696 (class 1259 OID 16932)
-- Name: idx_team_membri_mail; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_team_membri_mail ON public.team_membri USING btree (mail_utente);


--
-- TOC entry 4697 (class 1259 OID 16933)
-- Name: idx_team_membri_nome_team; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_team_membri_nome_team ON public.team_membri USING btree (nome_team);


--
-- TOC entry 4721 (class 2606 OID 17051)
-- Name: documenti_team documenti_team_nome_team_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.documenti_team
    ADD CONSTRAINT documenti_team_nome_team_fkey FOREIGN KEY (nome_team) REFERENCES public.team(nome_team) ON DELETE CASCADE;


--
-- TOC entry 4716 (class 2606 OID 16951)
-- Name: inviti_team inviti_team_mail_utente_invitato_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.inviti_team
    ADD CONSTRAINT inviti_team_mail_utente_invitato_fkey FOREIGN KEY (mail_utente_invitato) REFERENCES public.utenti(mail) ON DELETE CASCADE;


--
-- TOC entry 4717 (class 2606 OID 16946)
-- Name: inviti_team inviti_team_nome_team_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.inviti_team
    ADD CONSTRAINT inviti_team_nome_team_fkey FOREIGN KEY (nome_team) REFERENCES public.team(nome_team) ON DELETE CASCADE;


--
-- TOC entry 4720 (class 2606 OID 17034)
-- Name: problema_hackathon problema_hackathon_titolo_hackathon_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.problema_hackathon
    ADD CONSTRAINT problema_hackathon_titolo_hackathon_fkey FOREIGN KEY (titolo_hackathon) REFERENCES public.hackaton(titolo) ON DELETE CASCADE;


--
-- TOC entry 4714 (class 2606 OID 16927)
-- Name: team_membri team_membri_mail_utente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team_membri
    ADD CONSTRAINT team_membri_mail_utente_fkey FOREIGN KEY (mail_utente) REFERENCES public.utenti(mail) ON DELETE CASCADE;


--
-- TOC entry 4715 (class 2606 OID 16922)
-- Name: team_membri team_membri_nome_team_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team_membri
    ADD CONSTRAINT team_membri_nome_team_fkey FOREIGN KEY (nome_team) REFERENCES public.team(nome_team) ON DELETE CASCADE;


--
-- TOC entry 4718 (class 2606 OID 17006)
-- Name: voti_giudici voti_giudici_mail_giudice_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.voti_giudici
    ADD CONSTRAINT voti_giudici_mail_giudice_fkey FOREIGN KEY (mail_giudice) REFERENCES public.utenti(mail) ON DELETE CASCADE;


--
-- TOC entry 4719 (class 2606 OID 17001)
-- Name: voti_giudici voti_giudici_nome_team_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.voti_giudici
    ADD CONSTRAINT voti_giudici_nome_team_fkey FOREIGN KEY (nome_team) REFERENCES public.team(nome_team) ON DELETE CASCADE;


-- Completed on 2025-07-14 16:41:03

--
-- PostgreSQL database dump complete
--

