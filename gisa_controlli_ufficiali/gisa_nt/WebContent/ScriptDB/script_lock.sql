--chi: Antonio Liguori
--cosa: tabelle per gestione lock restart
--quando: 06/12/2018



CREATE TABLE public.lock_action (
    id integer NOT NULL,
    name_action character varying(200),
    name_command character varying(200),
    enabled boolean
);

ALTER TABLE public.lock_action OWNER TO postgres;

CREATE SEQUENCE public.lock_action_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.lock_action_id_seq OWNER TO postgres;
ALTER SEQUENCE public.lock_action_id_seq OWNED BY public.lock_action.id;
ALTER TABLE ONLY public.lock_action ALTER COLUMN id SET DEFAULT nextval('public.lock_action_id_seq'::regclass);

INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (1, 'GisaSuapStab', 'Default', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (2, 'OpuStabVigilanza', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (3, 'OpuStab', 'AddStabilimentoPregresso', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (4, 'ApicolturaApiariVigilanza', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (5, 'AccountVigilanza', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (6, 'Campioni', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (7, 'AllevamentiVigilanza', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (8, 'AbusivismiVigilanza', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (9, 'AziendeZootecnicheVigilanza', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (11, 'CaniPadronaliVigilanza', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (12, 'DistributoriVigilanza', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (13, 'FarmacieVigilanza', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (14, 'ImbarcazioniVigilanza', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (15, 'LabHaccpVigilanza', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (16, 'OiaVigilanza', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (17, 'OpnonAltroveVigilanza', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (18, 'OperatoriFuoriRegioneVigilanza', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (19, 'OperatoriprivatiVigilanza', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (20, 'Distributori852Vigilanza', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (21, 'OSAnimaliVigilanza', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (22, 'OsmVigilanza', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (23, 'OsmRegistratiVigilanza', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (24, 'ParafarmacieVigilanza', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (25, 'StabilimentoSintesisActionVigilanza', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (26, 'SoaVigilanza', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (27, 'StabilimentiVigilanza', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (28, 'GisaSuapStabVigilanza', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (29, 'TrasportiVigilanza', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (30, 'PrintReportVigilanza', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (31, 'OperatoriCommercialiCampioni', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (32, 'AccountCampioni', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (33, 'OperatoriFuoriRegioneCampioni', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (34, 'DistributoriCampioni', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (35, 'MollCampioni', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (36, 'FarmacieCampioni', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (37, 'StabilimentiCampioni', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (38, 'OsmRegistratiCampioni', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (39, 'OsmCampioni', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (40, 'AbusivismiCampioni', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (41, 'OperatoriprivatiCampioni', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (42, 'AllevamentiCampioni', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (43, 'SoaCampioni', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (44, 'AziendeAgricoleCampioni', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (45, 'PuntiSbarcoCampioni', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (46, 'LabHaccpCampioni', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (47, 'OSAnimaliCampioni', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (48, 'ParafarmacieCampioni', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (49, 'ImbarcazioniCampioni', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (50, 'OpnonAltroveCampioni', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (51, 'RiproduzioneAnimaleCampioni', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (52, 'OiaCampioni', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (53, 'ZoneControlloCampioni', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (54, 'ColonieCampioni', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (55, 'Stabilimenti852Campioni', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (56, 'OpuStabCampioni', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (57, 'Distributori852Campioni', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (58, 'GisaSuapStabCampioni', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (59, 'StabilimentoSintesisActionCampioni', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (60, 'ApicolturaApiariCampioni', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (61, 'AziendeZootecnicheCampioni', 'Add', true);
INSERT INTO public.lock_action (id, name_action, name_command, enabled) VALUES (62, 'OpuStabVigilanza', 'Insert', true);

SELECT pg_catalog.setval('public.lock_action_id_seq', 1, true);

ALTER TABLE ONLY public.lock_action
    ADD CONSTRAINT lock_action_pkey PRIMARY KEY (id);



-- lock_state

CREATE TABLE public.lock_state (
    id integer NOT NULL,
    tipo character varying(100),
    lock_date timestamp with time zone,
    direction character varying(100)
);


ALTER TABLE public.lock_state OWNER TO postgres;

CREATE SEQUENCE public.lock_state_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.lock_state_id_seq OWNER TO postgres;
ALTER SEQUENCE public.lock_state_id_seq OWNED BY public.lock_state.id;
ALTER TABLE ONLY public.lock_state ALTER COLUMN id SET DEFAULT nextval('public.lock_state_id_seq'::regclass);

INSERT INTO public.lock_state (id, tipo, lock_date, direction) VALUES (1, 'restart', '2018-12-05 18:29:24.159676+01', 'out');
INSERT INTO public.lock_state (id, tipo, lock_date, direction) VALUES (2, 'restart', '2018-12-06 11:15:24.791133+01', 'in');

SELECT pg_catalog.setval('public.lock_state_id_seq', 1, false);

ALTER TABLE ONLY public.lock_state
    ADD CONSTRAINT lock_state_pkey PRIMARY KEY (id);