-- Table: public.lookup_ugp_gruppi

-- DROP TABLE public.lookup_ugp_gruppi;

CREATE TABLE public.lookup_ugp_gruppi
(
  code integer NOT NULL,
  description character varying(50) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT lookup_ugp_gruppi_pkey PRIMARY KEY (code)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.lookup_ugp_gruppi
  OWNER TO postgres;
GRANT ALL ON TABLE public.lookup_ugp_gruppi TO postgres;

insert into lookup_ugp_gruppi (code, description) values (1, 'ASL');
insert into lookup_ugp_gruppi (code, description) values (2, 'REGIONE');



CREATE TABLE public.ugp_gruppi_ruoli
(
  id serial,
  id_gruppo integer,
  id_ruolo integer,
  CONSTRAINT ugp_gruppi_ruoli_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.ugp_gruppi_ruoli
  OWNER TO postgres;
GRANT ALL ON TABLE public.ugp_gruppi_ruoli TO postgres;



INSERT INTO ugp_gruppi_ruoli (id_ruolo, id_gruppo) values (44, 1);
INSERT INTO ugp_gruppi_ruoli (id_ruolo, id_gruppo) values (59, 1);
INSERT INTO ugp_gruppi_ruoli (id_ruolo, id_gruppo) values (222, 1);
INSERT INTO ugp_gruppi_ruoli (id_ruolo, id_gruppo) values (21, 1);
INSERT INTO ugp_gruppi_ruoli (id_ruolo, id_gruppo) values (97, 1);
INSERT INTO ugp_gruppi_ruoli (id_ruolo, id_gruppo) values (45, 1);
INSERT INTO ugp_gruppi_ruoli (id_ruolo, id_gruppo) values (19, 1);
INSERT INTO ugp_gruppi_ruoli (id_ruolo, id_gruppo) values (98, 1);
INSERT INTO ugp_gruppi_ruoli (id_ruolo, id_gruppo) values (46, 1);
INSERT INTO ugp_gruppi_ruoli (id_ruolo, id_gruppo) values (43, 1);
INSERT INTO ugp_gruppi_ruoli (id_ruolo, id_gruppo) values (42, 1);
INSERT INTO ugp_gruppi_ruoli (id_ruolo, id_gruppo) values (56, 1);
INSERT INTO ugp_gruppi_ruoli (id_ruolo, id_gruppo) values (41, 1);

INSERT INTO ugp_gruppi_ruoli (id_ruolo, id_gruppo) values (40,2);
INSERT INTO ugp_gruppi_ruoli (id_ruolo, id_gruppo) values (324,2);
INSERT INTO ugp_gruppi_ruoli (id_ruolo, id_gruppo) values (53,2);
INSERT INTO ugp_gruppi_ruoli (id_ruolo, id_gruppo) values (31,2);
INSERT INTO ugp_gruppi_ruoli (id_ruolo, id_gruppo) values (27,2);








CREATE TABLE public.ugp_permessi
(
  id serial,
  permesso text,
  CONSTRAINT ugp_permessi_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.ugp_permessi
  OWNER TO postgres;
GRANT ALL ON TABLE public.ugp_permessi TO postgres;

insert into ugp_permessi(id, permesso) values (1, 'view');
insert into ugp_permessi(id, permesso) values (2, 'all');





CREATE TABLE public.ugp_gruppi_permessi_linee
(
  id serial,
  codice_univoco text,
  id_gruppo integer,
  id_permesso integer,
  CONSTRAINT ugp_gruppi_permessi_linee_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.ugp_gruppi_permessi_linee
  OWNER TO postgres;
GRANT ALL ON TABLE public.ugp_gruppi_permessi_linee TO postgres;


select
distinct l.codice_univoco, 'insert into ugp_gruppi_permessi_linee(codice_univoco, id_gruppo, id_permesso) values(''' || l.codice_univoco || ''', 1, 2);' 
 from master_list_macroarea m
join master_list_aggregazione a on a.id_macroarea = m.id
join master_list_linea_attivita l on l.id_aggregazione = a.id
left join lookup_flusso_originale_ml f on f.code = a.id_flusso_originale

where 1=1
and a.id_flusso_originale = 4
and l.chi_inserisce_pratica = 'ASL/SUAP' 

UNION

select
distinct l.codice_univoco, 'insert into ugp_gruppi_permessi_linee(codice_univoco, id_gruppo, id_permesso) values(''' || l.codice_univoco || ''', 2, 2);' 
 from master_list_macroarea m
join master_list_aggregazione a on a.id_macroarea = m.id
join master_list_linea_attivita l on l.id_aggregazione = a.id
left join lookup_flusso_originale_ml f on f.code = a.id_flusso_originale

where 1=1
and a.id_flusso_originale = 4
and l.chi_inserisce_pratica = 'REGIONE' 

UNION

select
distinct l.codice_univoco, 'insert into ugp_gruppi_permessi_linee(codice_univoco, id_gruppo, id_permesso) values(''' || l.codice_univoco || ''', 1, 2);' 
 from master_list_macroarea m
join master_list_aggregazione a on a.id_macroarea = m.id
join master_list_linea_attivita l on l.id_aggregazione = a.id
left join lookup_flusso_originale_ml f on f.code = a.id_flusso_originale

where 1=1
and a.id_flusso_originale = 6
and l.chi_inserisce_pratica = 'ASL/SUAP' 

UNION

select
distinct l.codice_univoco, 'insert into ugp_gruppi_permessi_linee(codice_univoco, id_gruppo, id_permesso) values(''' || l.codice_univoco || ''', 2, 2);' 
 from master_list_macroarea m
join master_list_aggregazione a on a.id_macroarea = m.id
join master_list_linea_attivita l on l.id_aggregazione = a.id
left join lookup_flusso_originale_ml f on f.code = a.id_flusso_originale

where 1=1
and a.id_flusso_originale = 6
and l.chi_inserisce_pratica = 'REGIONE' 



--- fix permessi


-- Function: public_functions.suap_get_lista_linee_produttive(integer)

-- DROP FUNCTION public_functions.suap_get_lista_linee_produttive(integer);

CREATE OR REPLACE FUNCTION public_functions.suap_get_lista_linee_produttive(IN idstabilimentoscia integer)
  RETURNS TABLE(id_tipo_linea_produttiva integer, codice_nazionale text, id_linea_attivita integer, descrizione_linea_attivita text, data_fine timestamp without time zone, data_inizio timestamp without time zone, stato integer, primario boolean, id integer, id_rel_stab_lp integer, norma text, macro text, id_macrocategoria integer, codice text, id_norma integer, aggregazione text, id_categoria integer, attivita text, id_attivita integer, id_lookup_configurazione_validazione integer, descr_label text, permesso text) AS
$BODY$
DECLARE

BEGIN
FOR id_tipo_linea_produttiva,codice_nazionale,id_linea_attivita,descrizione_linea_attivita,data_fine,data_inizio,stato,
		primario,id,id_rel_stab_lp,norma, macro,id_macrocategoria,codice,id_norma,aggregazione,id_categoria,attivita,id_attivita,
		id_lookup_configurazione_validazione,descr_label,permesso
		in

		
	
select  distinct l.id_lookup_configurazione_validazione,rslp.codice_nazionale,rslp.id_linea_produttiva,
				l.path_descrizione , rslp.data_fine,rslp.data_inizio,rslp.stato,rslp.primario,
				l.id_nuova_linea_attivita,rslp.id ,l.norma , l.macroarea ,l.id_macroarea ,
				l.codice_attivita,l.id_norma,l.aggregazione ,l.id_aggregazione ,l.attivita ,
				l.id_attivita , l.id_lookup_configurazione_validazione,ll.description,ll.short_description as permesso 
				from ml8_linee_attivita_nuove_materializzata l 
				join suap_ric_scia_relazione_stabilimento_linee_produttive rslp on rslp.id_linea_produttiva = l.id_nuova_linea_attivita and rslp.enabled=true 

left join lookup_flusso_originale_ml lconf on lconf.code = l.id_lookup_configurazione_validazione 
left join master_list_linea_attivita linea on linea.id = l.id_nuova_linea_attivita
left join lookup_ente_scia ll on linea.chi_inserisce_pratica ilike ll.description 
				where rslp.id_stabilimento=idStabilimentoScia

		
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.suap_get_lista_linee_produttive(integer)
  OWNER TO postgres;
