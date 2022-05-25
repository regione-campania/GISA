
alter table lookup_destinazione_campione add column short_description text;
update lookup_destinazione_campione  set short_description = 'A' where code = 1;
update lookup_destinazione_campione  set short_description = 'S' where code = 2;

 -- Table: preaccettazione.lookup_stati_pa
-- DROP TABLE preaccettazionesigla.lookup_stati_pa;

CREATE TABLE preaccettazionesigla.lookup_ente
(
  code integer NOT NULL,
  description character varying,
  short_description character varying,
  enabled boolean
)
WITH (
  OIDS=FALSE
);
ALTER TABLE preaccettazionesigla.lookup_ente
  OWNER TO postgres;
  
GRANT ALL ON TABLE preaccettazionesigla.lookup_ente TO postgres;
GRANT ALL ON TABLE preaccettazionesigla.lookup_ente TO public;

insert into preaccettazionesigla.lookup_ente(code, description, short_description, enabled) values (1, 'Regione', 'G', true);
alter table preaccettazionesigla.codici_preaccettazione add column id_ente integer;
alter table preaccettazionesigla.codici_preaccettazione add column id_laboratorio integer;

update preaccettazionesigla.codici_preaccettazione set id_ente=1, id_laboratorio=2;
update preaccettazionesigla.codici_preaccettazione set id_ente=1, id_laboratorio=1 where prefix = 'G2A';


-- 
DROP FUNCTION preaccettazionesigla.get_elenco_preaccettazioni_da_linea_attivita(text);
CREATE OR REPLACE FUNCTION preaccettazionesigla.get_elenco_preaccettazioni_da_linea_attivita(IN _identificativo_linea text, IN _id_ente integer, IN _id_laboratorio integer)
  RETURNS TABLE(codice_preaccettazione text, data_conferma text, ragione_sociale text, userid integer, username character varying, desc_quesito text, desc_matrice text, id_quesito text, id_matrice text, ente text, laboratorio text) AS
$BODY$
DECLARE

BEGIN

	return query
	select distinct concat(t.prefix,t.anno,t.progres) as codice_preaccettazione,
	       to_char(t.entered, 'YYYY-MM-DD  HH24:MI:SS') as data_conferma,
	       ram.ragione_sociale::text as ragione_sociale,
	       t.enteredby,
	       a.username,
	       cp.quesito_diagnostico::text as desc_quesito,
	       cp.matrice_campione::text as desc_matrice,
	       cp.id_quesito::text as id_quesito,
	       cp.id_matrice::text as id_matrice,
	       le.description::text as ente,
	       ll.description::text as laboratorio
	 from ricerche_anagrafiche_old_materializzata ram
		join preaccettazionesigla.vw_ultimo_stato t 
			on concat(ram.riferimento_id, ram.riferimento_id_nome, ram.riferimento_id_nome_tab, ram.id_linea) = 
			   concat(t.lda_riferimento_id, t.lda_riferimento_id_nome, t.lda_riferimento_id_nome_tab, t.lda_id_linea)
		join access a on t.enteredby = a.user_id
		join preaccettazionesigla.codici_preaccettazione cp on cp.id = t.id_preaccettazione
		join lookup_destinazione_campione ll on ll.code = cp.id_laboratorio and ll.enabled
		join preaccettazionesigla.lookup_ente le on le.code = cp.id_ente and le.enabled
	   where concat(t.lda_riferimento_id,t.lda_riferimento_id_nome,t.lda_riferimento_id_nome_tab,t.lda_id_linea,t.tipologia_operatore) like _identificativo_linea 
		 and t.id_stato = 1 and cp.id_ente = _id_ente and cp.id_laboratorio = _id_laboratorio 
		 order by data_conferma desc limit 10;
 
	
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION preaccettazionesigla.get_elenco_preaccettazioni_da_linea_attivita(text, integer, integer)
  OWNER TO postgres;

--SELECT * FROM preaccettazionesigla.get_elenco_preaccettazioni_da_linea_attivita('183564stabIdopu_stabilimento192439999', 1, 2)


CREATE OR REPLACE FUNCTION preaccettazionesigla.get_elenco_laboratori_da_ente(_id_ente integer)
  RETURNS SETOF lookup_destinazione_campione AS
$BODY$

BEGIN	
	return query 
	-- filtro solo i laboratori codificati
	select * from lookup_destinazione_campione where short_description is not null;
	-- l'input andrà utilizzato quando si configura la info nel sistema
	
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION preaccettazionesigla.get_elenco_laboratori_da_ente(integer)
  OWNER TO postgres;


------------------------------------ refactoring 15/07/2021
-- Function: preaccettazionesigla.get_anagraficasigla(text, integer, text)

--DROP FUNCTION preaccettazionesigla.get_anagrafica(text, integer, text);----------------------> non ha funzionato

CREATE OR REPLACE FUNCTION preaccettazionesigla.get_anagrafica(
    IN _codpreacc text,
    IN _idutente integer,
    IN _iplettura text,
    IN _id_ente integer,
    IN _id_laboratorio integer)
  RETURNS TABLE(norma text, ragione_sociale character varying, partita_iva text, nominativo_rappresentante text, codice_fiscale_rappresentante character varying, asl_rif integer, asl character varying, indirizzo character varying, comune character varying, provincia_stab character varying, n_linea character varying, attivita text, data_ultima_mod timestamp without time zone, data_conferma timestamp without time zone, codice_preaccettazione text, codmatrice character varying, codanalita character varying, codquesdiagn character varying, data_prelievo timestamp without time zone, numero_verbale character varying, data_verbale timestamp without time zone, stato_preaccettazione text) AS
$BODY$
DECLARE

	id_preacc integer;
	_stato integer;
	
BEGIN

	select id into id_preacc from preaccettazionesigla.codici_preaccettazione
		where concat(prefix,anno,progres) ilike trim(_codpreacc);
	
	select id_stato into _stato from preaccettazionesigla.stati_preaccettazione 
		where id_preaccettazione = id_preacc order by id_stato desc limit 1;

	IF _stato = 1 THEN 

		perform preaccettazionesigla.lettura_da_laboratorio(_codpreacc,_idutente,_iplettura); 
		
		return query
		select distinct
		       ram.norma, --text
		       trim(regexp_replace(ram.ragione_sociale, '\s+', ' ', 'g'))::character varying(79) as ragione_sociale,
		       --ram.ragione_sociale, --character varying(300)
		       ram.partita_iva::text, --bpchar
		       ram.nominativo_rappresentante, --text
		       ram.codice_fiscale_rappresentante, --character varying
		       ram.asl_rif, --integer
		       ram.asl, --character varying(300)
		       trim(regexp_replace(ram.indirizzo, '\s+', ' ', 'g'))::character varying(49) as indirizzo, --character varying
		       ram.comune, --character varying
		       ram.provincia_stab, --character varying
		       ram.n_linea, --character varying
		       ram.attivita, --text
		       sp.modified, --timestamp without time zone
		       sp.entered, --timestamp without time zone
		       concat(cp.prefix, cp.anno, cp.progres) as codPreacc, --text
		       null::character varying as codMatrice, --cp.matrice_campione::character varying as codMatrice, --codMatrice
		       null::character varying as codAnalita, --codAnalita
		       null::character varying as codQuesDiagn, --cp.quesito_diagnostico::character varying as codQuesDiagn, --null::character varying as codQuesDiagn, --codQuesDiagn
		       null::timestamp without time zone as data_prelievo,--'2018-05-27'::timestamp without time zone as data_prelievo, 
		       null::character varying as numero_verbale,--'864833'::character varying as numero_verbale,
		       null::timestamp without time zone as data_verbale, --data_verbale
		       'Incompleto: non associato al campione'::text as stato_preaccettazione --stato della preaccettazione: completo sta per associato al campione
	
		from ricerche_anagrafiche_old_materializzata ram 
			join preaccettazionesigla.associazione_preaccettazione_entita ap
				on concat(ram.riferimento_id, ram.riferimento_id_nome, ram.riferimento_id_nome_tab, ram.id_linea, ram.tipologia_operatore) = 
				concat(ap.lda_riferimento_id, ap.lda_riferimento_id_nome, ap.lda_riferimento_id_nome_tab, ap.lda_id_linea, ap.tipologia_operatore)
			join preaccettazionesigla.stati_preaccettazione sp on sp.id = ap.id_stati
			join preaccettazionesigla.codici_preaccettazione cp on cp.id = sp.id_preaccettazione
		where concat(cp.prefix, cp.anno, cp.progres) ilike trim(_codPreacc) and sp.id_stato = 1
		-- adding 15/07
		and cp.id_ente = _id_ente and cp.id_laboratorio = _id_laboratorio;
 
	END IF;

	--se la preaccettazione del campione è completa
	IF _stato = 2 or _stato = 3 or _stato = 5 THEN

		perform preaccettazionesigla.lettura_da_laboratorio(_codpreacc,_idutente,_iplettura); 

		return query
		select distinct
		       ram.norma, --text
		       trim(regexp_replace(ram.ragione_sociale, '\s+', ' ', 'g'))::character varying(79) as ragione_sociale,
		       --ram.ragione_sociale, --character varying(300)
		       ram.partita_iva::text, --bpchar
		       ram.nominativo_rappresentante, --text
		       ram.codice_fiscale_rappresentante, --character varying
		       ram.asl_rif, --integer
		       ram.asl, --character varying(300)
		       trim(regexp_replace(ram.indirizzo, '\s+', ' ', 'g'))::character varying(49) as indirizzo, --character varying
		       ram.comune, --character varying
		       ram.provincia_stab, --character varying
		       ram.n_linea, --character varying
		       ram.attivita, --text
		       sp.modified, --timestamp without time zone
		       sp.entered, --timestamp without time zone
		       concat(cp.prefix, cp.anno, cp.progres) as codPreacc, --text
		       --null::character varying as codMatrice, 
		       --COALESCE(NULLIF(trim(m.codice_esame),''),NULLIF(trim(m.nome),''))::character varying as codMatrice, --codMatrice
		       --COALESCE(trim(m.codice_esame),'')::character varying as codMatrice,
		       CASE WHEN (length(trim(coalesce(m3.codice_esame,''))) = 0) THEN
				 COALESCE(trim(concat(m2.codice_esame || ';', m.codice_esame)),'')::character varying
			    ELSE 
				COALESCE(trim(concat(m3.codice_esame || ';', m2.codice_esame)),'')::character varying 
		       end as codMatrice,
		       
		       null::character varying as codAnalita, 
		       --COALESCE(NULLIF(trim(a.codice_esame),''),NULLIF(trim(a.nome),''))::character varying as codAnalita, --codAnalita
		       
		       --null::character varying as codQuesDiagn, 
		       --COALESCE(NULLIF(trim(qds.codice_esame),''),NULLIF(trim(qds.description),''))::character varying as codQuesDiagn, --codQuesDiagn
		       COALESCE(trim(qds.codice_esame),'')::character varying as codQuesDiagn,
		       t.assigned_date as data_prelievo,--'2018-05-27'::timestamp without time zone as data_prelievo, 
		       t.location as numero_verbale,--'864833'::character varying as numero_verbale,
		       t.assigned_date as data_verbale, --data_verbale
		       case when _stato = 5 then 
				'RISULTATO RICEVUTO'::text  --stato della preaccettazione: risultato ricevuto sta ricevuto esito esame inviato da sigla a gisa
			    else 
				'COMPLETO'::text  --stato della preaccettazione: completo sta per associato al campione
			    end as stato_preaccettazione
	
		from ricerche_anagrafiche_old_materializzata ram 
			join preaccettazionesigla.associazione_preaccettazione_entita ap
				on concat(ram.riferimento_id, ram.riferimento_id_nome, ram.riferimento_id_nome_tab, ram.id_linea, ram.tipologia_operatore) = 
				concat(ap.lda_riferimento_id, ap.lda_riferimento_id_nome, ap.lda_riferimento_id_nome_tab, ap.lda_id_linea, ap.tipologia_operatore)
			join preaccettazionesigla.stati_preaccettazione sp on sp.id = ap.id_stati
			join preaccettazionesigla.codici_preaccettazione cp on cp.id = sp.id_preaccettazione
			--verbale
			join ticket t on (t.ticketid = ap.riferimento_entita::integer and ap.tipo_entita ilike 'C')
			--matrice
			left join matrici_campioni mc on (mc.id_campione = ap.riferimento_entita::integer and ap.tipo_entita ilike 'C')
			left join matrici m on (mc.id_matrice = m.matrice_id)
			left join matrici m2 on (m2.matrice_id = m.id_padre)
			left join matrici m3 on (m3.matrice_id = m2.id_padre)
			--analita
			left join analiti_campioni ac on (ac.id_campione = ap.riferimento_entita::integer and ap.tipo_entita ilike 'C')
			left join analiti a on (a.analiti_id = ac.analiti_id)
			--quesito diagnostico
			left join quesiti_diagnostici_sigla qds on qds.code = t.motivazione_piano_campione  
		where concat(cp.prefix, cp.anno, cp.progres) ilike trim(_codPreacc) and sp.id_stato = 2
		-- adding 15/07
		and cp.id_ente = _id_ente and cp.id_laboratorio = _id_laboratorio;
 
	END IF;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION preaccettazionesigla.get_anagrafica(text, integer, text,integer, integer)
  OWNER TO postgres;


-- Function: preaccettazionesigla.lettura_da_sigla(text, integer, text)

DROP FUNCTION preaccettazionesigla.lettura_da_sigla(text, integer, text);

CREATE OR REPLACE FUNCTION preaccettazionesigla.lettura_da_laboratorio(
    IN _codpreacc text,
    IN _idutente integer,
    IN _iplettura text)
  RETURNS TABLE(_idout integer) AS
$BODY$
DECLARE
	id_preacc integer;
	_stato integer;
	_id integer;
BEGIN

	select id into id_preacc from preaccettazionesigla.codici_preaccettazione where trim(concat(prefix,anno,progres)) ilike trim(_codpreacc);

	select id, id_stato into _id, _stato from preaccettazionesigla.stati_preaccettazione 
		where id_preaccettazione = id_preacc order by id_stato desc limit 1;

	IF _stato = 1 THEN
		return query
		update preaccettazionesigla.stati_preaccettazione 
			set letto = now(),
			    ip_lettura = _iplettura
			where id = _id returning id;
		
	ELSIF _stato = 2 THEN
	
		return query
		insert into preaccettazionesigla.stati_preaccettazione (id_preaccettazione, id_stato, entered, enteredby, letto, ip_lettura)
			values (id_preacc, 3, now(), _idutente, now(), _iplettura) returning id;
			
	ELSIF _stato = 3 THEN
	
		return query
		update preaccettazionesigla.stati_preaccettazione 
			set modified = now(),
			    modifiedby = _idutente,
			    letto = now(),
			    ip_lettura = _iplettura
		        where id_preaccettazione = id_preacc and _stato = 3 and id_stato = 3 returning id;
		        
	ELSIF _stato = 5 THEN
	
		return query
		update preaccettazionesigla.stati_preaccettazione 
			set modified = now(),
			    modifiedby = _idutente,
			    letto = now(),
			    ip_lettura = _iplettura
		        where id_preaccettazione = id_preacc and _stato = 5 and id_stato = 5 returning id;
	
	END IF;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION preaccettazionesigla.lettura_da_laboratorio(text, integer, text)
  OWNER TO postgres;


CREATE OR REPLACE FUNCTION preaccettazionesigla.get_elenco_preaccettazioni(
    IN _id_ente integer,
    IN _id_laboratorio integer)
  RETURNS TABLE(codice_preaccettazione text, stato text, data_operazione text) AS
$BODY$
DECLARE
	codifica_laboratorio text;
BEGIN

	if _id_laboratorio = 1 then  -- ARPAC
		codifica_laboratorio := 'G2A';
	else 
		codifica_laboratorio := 'G2S';
	end if;
	
	return query
	select concat(p.prefix,p.anno,p.progres) as codice_preaccettazione, 
		lsp.descrizione as stato, 
		to_char(p.entered , 'YYYY-MM-DD  HH24:MI:SS') as data_operazione		
	from preaccettazionesigla.vw_ultimo_stato p join
		preaccettazionesigla.lookup_stati_pa lsp on p.id_stato = lsp.id
		where p.id_stato > 0 
		and prefix::text = codifica_laboratorio
		order by p.entered desc;
 	
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION preaccettazionesigla.get_elenco_preaccettazioni(integer, integer)
  OWNER TO postgres;

CREATE OR REPLACE FUNCTION preaccettazionesigla.report_interno_codici_preaccettazione()
  RETURNS TABLE(report text) AS
$BODY$
DECLARE

	
BEGIN
	return query
	select (concat('[', string_agg(row_to_json(tab)::text, ','), ']')::json)::text as report from(
	
	select asl::text as desc_asl,
	       coalesce(preac_generati,0)::text as associati_a_osa,
	       coalesce(preac_ass_campione,0)::text as associati_a_campione,
	       coalesce(preac_letti_da_laboratorio_campione,0)::text as letti_da_laboratorio_campione,
	       coalesce(preac_letti_da_laboratorio_solo_osa,0)::text as letti_da_laboratorio_solo_osa,
	       (coalesce(preac_ass_campione,0) - coalesce(preac_letti_da_laboratorio_campione,0))::text as associati_non_letti,
	       (coalesce(preac_generati,0) - coalesce(preac_ass_campione,0) - coalesce(preac_letti_da_laboratorio_solo_osa,0))::text as non_associati_non_letti
	from crosstab (
		'select desc_asl,
			id_asl,
			numero_preaccettazioni 
		    from preaccettazionesigla.temp_report_interno_codici_preaccettazione() order by id_asl desc, tipo_report'
	) as ct(ASL text, preac_generati integer, preac_ass_campione integer, preac_letti_da_laboratorio_campione integer, preac_letti_da_laboratorio_solo_osa integer) order by asl
	) tab;

END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION preaccettazionesigla.report_interno_codici_preaccettazione()
  OWNER TO postgres;

-- Function: preaccettazionesigla.temp_report_interno_codici_preaccettazione()

-- DROP FUNCTION preaccettazionesigla.temp_report_interno_codici_preaccettazione();

CREATE OR REPLACE FUNCTION preaccettazionesigla.temp_report_interno_codici_preaccettazione()
  RETURNS TABLE(numero_preaccettazioni integer, id_asl integer, desc_asl text, tipo_report text) AS
$BODY$
DECLARE

	
BEGIN
	return query
	select 
	    tab.count::integer as numero_preaccettazioni,
	    tab.asl_rif as id_asl,
	    tab.asl::text as desc_asl,
	    tab.tipo::text as tipo_report
	from(
	-- numero codici preaccettazione associati all osa raggruppati per asl
	select count(*), ra.asl_rif, ra.asl, 'a' as tipo 
		from preaccettazionesigla.associazione_preaccettazione_entita ape join
			ricerche_anagrafiche_old_materializzata ra on ra.riferimento_id = ape.lda_riferimento_id 
									and ra.riferimento_id_nome = ape.lda_riferimento_id_nome 
									and ra.riferimento_id_nome_tab = ape.lda_riferimento_id_nome_tab 
									and ra.id_linea = ape.lda_id_linea 
									and ra.tipologia_operatore = ape.tipologia_operatore
		where ape.tipo_entita = 'lda' group by ra.asl_rif, ra.asl
	UNION
	-- numero codici preaccettazione associati al campione raggruppati per asl
	select count(*), ra.asl_rif, ra.asl, 'b' as tipo
		from preaccettazionesigla.associazione_preaccettazione_entita ape join
			ricerche_anagrafiche_old_materializzata ra on ra.riferimento_id = ape.lda_riferimento_id 
									and ra.riferimento_id_nome = ape.lda_riferimento_id_nome 
									and ra.riferimento_id_nome_tab = ape.lda_riferimento_id_nome_tab 
									and ra.id_linea = ape.lda_id_linea 
									and ra.tipologia_operatore = ape.tipologia_operatore
		where ape.tipo_entita = 'C' group by ra.asl_rif, ra.asl
	UNION
	-- numero codici preaccettazione associati al campione e letti da sigla raggruppati per asl
	select count(*), ra.asl_rif, ra.asl, 'c' as tipo  
		from preaccettazionesigla.associazione_preaccettazione_entita ape join 
		     preaccettazionesigla.stati_preaccettazione sp on ape.id_stati = sp.id
		     join preaccettazionesigla.stati_preaccettazione sp2 on sp.id_preaccettazione = sp2.id_preaccettazione
		     join ricerche_anagrafiche_old_materializzata ra on ra.riferimento_id = ape.lda_riferimento_id 
									and ra.riferimento_id_nome = ape.lda_riferimento_id_nome 
									and ra.riferimento_id_nome_tab = ape.lda_riferimento_id_nome_tab 
									and ra.id_linea = ape.lda_id_linea 
									and ra.tipologia_operatore = ape.tipologia_operatore
			where ape.tipo_entita = 'C' and (sp2.letto is not null or sp2.id_stato = 3)  
			group by ra.asl_rif, ra.asl
	UNION
	-- numero codici preaccettazione associati al campione e letti da sigla raggruppati per asl
	select count(*), ra.asl_rif, ra.asl, 'd' as tipo  
		from preaccettazionesigla.associazione_preaccettazione_entita ape join 
		     preaccettazionesigla.stati_preaccettazione sp on ape.id_stati = sp.id
		     join preaccettazionesigla.stati_preaccettazione sp2 on sp.id_preaccettazione = sp2.id_preaccettazione
		     join ricerche_anagrafiche_old_materializzata ra on ra.riferimento_id = ape.lda_riferimento_id 
									and ra.riferimento_id_nome = ape.lda_riferimento_id_nome 
									and ra.riferimento_id_nome_tab = ape.lda_riferimento_id_nome_tab 
									and ra.id_linea = ape.lda_id_linea 
									and ra.tipologia_operatore = ape.tipologia_operatore
		     join preaccettazionesigla.vw_ultimo_stato us on us.cod_stato = sp2.id
			where sp2.letto is not null and us.id_stato = 1 
			group by ra.asl_rif, ra.asl)tab;
	
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION preaccettazionesigla.temp_report_interno_codici_preaccettazione()
  OWNER TO postgres;

  ALTER TABLE preaccettazionesigla.stati_preaccettazione RENAME COLUMN letto_da_sigla TO letto;
  ALTER TABLE preaccettazionesigla.stati_preaccettazione RENAME COLUMN ip_lettura_sigla TO ip_lettura;


DROP VIEW preaccettazionesigla.vw_ultimo_stato;
CREATE OR REPLACE VIEW preaccettazionesigla.vw_ultimo_stato AS 
 SELECT DISTINCT ON (c.id) s.id AS cod_stato,
    s.id_preaccettazione,
    s.enteredby,
    s.entered,
    c.prefix,
    c.anno,
    c.progres,
    c.id_ente,
    c.id_laboratorio,
    s.id_stato,
    a.riferimento_entita,
    a.tipo_entita,
    a.lda_riferimento_id,
    a.lda_riferimento_id_nome,
    a.lda_riferimento_id_nome_tab,
    a.lda_id_linea,
    a.tipologia_operatore
   FROM preaccettazionesigla.stati_preaccettazione s
     LEFT JOIN preaccettazionesigla.codici_preaccettazione c ON s.id_preaccettazione = c.id
     LEFT JOIN preaccettazionesigla.associazione_preaccettazione_entita a ON a.id_stati = s.id
  WHERE s.trashed_date IS NULL
  ORDER BY c.id, s.entered DESC;

ALTER TABLE preaccettazionesigla.vw_ultimo_stato
  OWNER TO postgres;
GRANT ALL ON TABLE preaccettazionesigla.vw_ultimo_stato TO postgres;
GRANT ALL ON TABLE preaccettazionesigla.vw_ultimo_stato TO public;



-- rename schema preaccettazione
select 'update pg_proc set prosrc = replace(prosrc, ''preaccettazionesigla.'', ''preaccettazione.'') where oid = '||oid||';' from pg_proc where prosrc ilike '%preaccettazionesigla%';
-- rename schema preaccettazione-----------> manualmente

update preaccettazione.lookup_stati_pa  set descrizione = 'CODICE PREACCETTAZIONE LETTO DA LABORATORIO' where id=3;


CREATE OR REPLACE FUNCTION preaccettazione.search_preaccettazione(filtri hstore)
  RETURNS SETOF json AS
$BODY$
DECLARE

	
BEGIN

	return query
	select concat('[', string_agg(row_to_json(tab2)::text, ','), ']')::json as lista_preaccettazioni from
	(select * from
		(select distinct
		       ram.ragione_sociale::text, --character varying(300)
		       ram.partita_iva::text, --bpchar
		       coalesce(nullif(trim(ram.n_reg),''), ram.num_riconoscimento)::text num_registrazione,
		       ram.asl_rif::text, --integer
		       ram.asl::text, --character varying(300)
		       ram.comune::text, --character varying
		       ram.attivita::text, --text
		       concat(cp.prefix, cp.anno, cp.progres)::text as codPreacc, --text
		       case when sp.id_stato = 1 then 'Incompleto: non associato al campione'::text
			    when sp.id_stato = 2 then 'Completo'::text
			    end 
			    as stato_preaccettazione, --stato della preaccettazione: completo sta per associato al campione
		       concat(c.namelast, ' ', c.namefirst)::text as utente,
		       t.identificativo::text as codice_campione,
		       t.id_controllo_ufficiale::text as id_cu,
		       -- new 
		       t.ticketid::text as id_campione,
		       t.location::text as numero_verbale,
		       to_char(cp.entered,'DD-MM-YYYY')::text as data_generazione,
		       sp.ip_lettura as ip_lettura,
		       to_char(sp.letto,'DD-MM-YYYY HH24:MI:SS')::text as data_lettura
			
		from preaccettazione.associazione_preaccettazione_entita ap
			join ricerche_anagrafiche_old_materializzata ram 
				on concat(ram.riferimento_id, ram.riferimento_id_nome, ram.riferimento_id_nome_tab, ram.id_linea, ram.tipologia_operatore) = 
				concat(ap.lda_riferimento_id, ap.lda_riferimento_id_nome, ap.lda_riferimento_id_nome_tab, ap.lda_id_linea, ap.tipologia_operatore)
			join preaccettazione.stati_preaccettazione sp on sp.id = ap.id_stati
			join preaccettazione.vw_ultimo_stato us on us.cod_stato = sp.id
			left join access_ ut on us.enteredby = ut.user_id
			left join contact c on ut.contact_id = c.contact_id and trim(concat(c.namelast, ' ', c.namefirst)::text) not ilike ''
			join preaccettazione.codici_preaccettazione cp on cp.id = sp.id_preaccettazione
			--verbale
			left join ticket t on (ap.tipo_entita ilike 'C' and t.ticketid::character varying = ap.riferimento_entita)
			  
		where us.id_stato in (1,2)
			and ( (filtri -> 'codice_preaccettazione' not ilike '' and concat(cp.prefix, cp.anno, cp.progres)::text ilike trim(filtri -> 'codice_preaccettazione')) or (filtri -> 'codice_preaccettazione' ilike '') ) 
			and ( ((filtri -> 'asl')::integer > 0 and ram.asl_rif = (filtri -> 'asl')::integer) or ((filtri -> 'asl')::integer = -1) ) 
			and ( (filtri -> 'osa' not ilike '' and trim(ram.ragione_sociale) ilike concat('%',filtri -> 'osa','%')) or (filtri -> 'osa' ilike '') )
			and ( (filtri -> 'partita_iva' not ilike '' and trim(ram.partita_iva::text) ilike filtri -> 'partita_iva') or (filtri -> 'partita_iva' ilike '') )
			and ( (filtri -> 'data_generazione' not ilike '' and to_char(cp.entered, 'DD-MM-YYYY') ilike trim(filtri -> 'data_generazione')) or (filtri -> 'data_generazione' ilike '') )
			and ( (filtri -> 'utente' not ilike '' 
				and (concat(c.namelast,' ',c.namefirst) ilike concat('%',filtri -> 'utente','%')  
					or concat(c.namefirst,' ',c.namelast) ilike concat('%',filtri -> 'utente','%')) 
			       ) or (filtri -> 'utente' ilike ''))

			--new
			and ( ((filtri -> 'laboratorio')::integer > 0 and cp.id_laboratorio = (filtri -> 'laboratorio')::integer) or ((filtri -> 'laboratorio')::integer = -1) ) 


		UNION 
		
		select distinct
			ra.ragione_sociale::text, --character varying(300)
			ra.partita_iva::text, --bpchar
			coalesce(nullif(trim(ra.n_reg),''), ra.num_riconoscimento)::text num_registrazione,
			ra.asl_rif::text, --integer
			ra.asl::text, --character varying(300)
			ra.comune::text, --character varying
			ra.attivita::text, --text
			concat(cp.prefix, cp.anno, cp.progres)::text as codPreacc, --text
			case when us.id_stato = 5 then 
				'RISULTATO RICEVUTO'::text  --stato della preaccettazione: risultato ricevuto sta ricevuto esito esame inviato da laboratorio a gisa
			    else 
				'LETTO'::text  --stato della preaccettazione: completo sta per associato al campione
			    end as stato_preaccettazione,
			concat(c.namelast, ' ', c.namefirst)::text as utente,
			t.identificativo::text as codice_campione,
			t.id_controllo_ufficiale::text as id_cu,
			-- new 
			t.ticketid::text as id_campione,
			t.location::text as numero_verbale,
			to_char(cp.entered,'DD-MM-YYYY')::text as data_generazione,
			sp2.ip_lettura as ip_lettura,
			to_char(sp2.letto,'DD-MM-YYYY HH24:MI:SS')::text as data_lettura
			 
		from preaccettazione.associazione_preaccettazione_entita ape join 
			     preaccettazione.stati_preaccettazione sp on ape.id_stati = sp.id
			     join preaccettazione.stati_preaccettazione sp2 on sp.id_preaccettazione = sp2.id_preaccettazione
			     join preaccettazione.vw_ultimo_stato us on us.cod_stato = sp2.id
			     join preaccettazione.codici_preaccettazione cp on cp.id = sp2.id_preaccettazione
			     left join access_ ut on cp.enteredby = ut.user_id
			     left join contact c on ut.contact_id = c.contact_id and trim(concat(c.namelast, ' ', c.namefirst)::text) not ilike ''
			     join ricerche_anagrafiche_old_materializzata ra on ra.riferimento_id = ape.lda_riferimento_id 
										and ra.riferimento_id_nome = ape.lda_riferimento_id_nome 
										and ra.riferimento_id_nome_tab = ape.lda_riferimento_id_nome_tab 
										and ra.id_linea = ape.lda_id_linea 
										and ra.tipologia_operatore = ape.tipologia_operatore
			     --verbale
			     left join ticket t on (ape.tipo_entita ilike 'C' and t.ticketid::character varying = ape.riferimento_entita)
			     where ape.tipo_entita = 'C' and (us.id_stato in (3,5))
				and ( (filtri -> 'codice_preaccettazione' not ilike '' and concat(cp.prefix, cp.anno, cp.progres)::text ilike trim(filtri -> 'codice_preaccettazione')) or (filtri -> 'codice_preaccettazione' ilike '') ) 
				and ( ((filtri -> 'asl')::integer > 0 and ra.asl_rif = (filtri -> 'asl')::integer) or ((filtri -> 'asl')::integer = -1) ) 
				and ( (filtri -> 'osa' not ilike '' and trim(ra.ragione_sociale) ilike concat('%',filtri -> 'osa','%')) or (filtri -> 'osa' ilike '') )
				and ( (filtri -> 'partita_iva' not ilike '' and trim(ra.partita_iva::text) ilike filtri -> 'partita_iva') or (filtri -> 'partita_iva' ilike '') )
				and ( (filtri -> 'data_generazione' not ilike '' and to_char(cp.entered, 'DD-MM-YYYY') ilike trim(filtri -> 'data_generazione')) or (filtri -> 'data_generazione'  ilike '') )
				and ( (filtri -> 'utente' not ilike '' 
					and (concat(c.namelast,' ',c.namefirst) ilike concat('%',filtri -> 'utente','%')  
						or concat(c.namefirst,' ',c.namelast) ilike concat('%',filtri -> 'utente','%')) 
				       ) or (filtri -> 'utente' ilike ''))
				--new
				and ( ((filtri -> 'laboratorio')::integer > 0 and cp.id_laboratorio = (filtri -> 'laboratorio')::integer) or ((filtri -> 'laboratorio')::integer = -1) ) 
	       	       
			) tab1  order by tab1.data_generazione::timestamp without time zone desc limit 100
		) tab2;
 
	
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION preaccettazione.search_preaccettazione(hstore)
  OWNER TO postgres;

----------------------------------------------------------------- fase II ----------------------- ritorno da SIGLA

CREATE OR REPLACE FUNCTION preaccettazione.dbi_ins_res_arpac(
    IN _codpreacc text,
    IN _descr_ris_esame text,
    IN _id_utente integer DEFAULT NULL::integer,
    IN _forza_riscrittura boolean DEFAULT false)
  RETURNS TABLE(_idout integer) AS
$BODY$
DECLARE
	id_preacc integer;
	_id_cmp integer;
	_note_esito_esame text;
	
BEGIN

	select id into id_preacc from preaccettazione.codici_preaccettazione where trim(concat(prefix,anno,progres)) ilike trim(_codpreacc);

	select id_cmp into _id_cmp from preaccettazione.get_id_cmp_da_codice_preaccettazione(_codpreacc);

	select coalesce(trim(note_esito_esame),'') into _note_esito_esame from ticket where ticketid =  _id_cmp;

	IF _id_utente is null or _id_utente = -1 THEN
			_id_utente := 6567;
	END IF;

	IF (length(trim(_note_esito_esame)) = 0 and length(trim(_descr_ris_esame)) <> 0) THEN

		UPDATE ticket set note_esito_esame = _descr_ris_esame where ticketid = _id_cmp;

		return query
		insert into preaccettazione.stati_preaccettazione (id_preaccettazione, id_stato, entered, enteredby)
			values (id_preacc, 5, now(), _id_utente) returning id;
			
	ELSIF (length(trim(_descr_ris_esame)) <> 0 and _forza_riscrittura) THEN
		UPDATE ticket set note_esito_esame = _descr_ris_esame where ticketid = _id_cmp;
		UPDATE preaccettazione.stati_preaccettazione 
			set modified = now(), 
			    modifiedby = _id_utente, 
			    note_hd = concat(trim(note_hd) || '--',' lettura forzata esito esame dai ws di ARPAC in data: ', to_char(now(), 'YYYY-MM-DD  HH24:MI:SS'), ' da id utente: ', _id_utente)
		WHERE id_preaccettazione = id_preacc and id_stato = 5;
	ELSE
		return query
		select 0;
	END IF;
			
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION preaccettazione.dbi_ins_res_arpac(text, text, integer, boolean)
  OWNER TO postgres;

---------------------------------------------------------------------- FUNZIONE GET ANAGRAFICA PER PREACCETTAZIONE 05/08/21 in collaudo dal 10/08 ---------------------------------------------------------------------------------
DROP FUNCTION preaccettazione.get_anagrafica(text, integer, text, integer, integer);
CREATE OR REPLACE FUNCTION preaccettazione.get_anagrafica(
    IN _codpreacc text,
    IN _idutente integer,
    IN _iplettura text,
    IN _id_ente integer,
    IN _id_laboratorio integer)
  RETURNS TABLE(norma text, ragione_sociale character varying, partita_iva text, nominativo_rappresentante text, codice_fiscale_rappresentante character varying, asl_rif integer, asl character varying, 
  indirizzo character varying, comune character varying, provincia_stab character varying, n_linea character varying, attivita text, cod_univoco_ml text, desc_linea_attr_1  text, desc_linea_attr_2 text, data_ultima_mod timestamp without time zone, 
  data_conferma timestamp without time zone, codice_preaccettazione text, codmatrice character varying, codanalita character varying, codquesdiagn character varying, 
  data_prelievo timestamp without time zone, numero_verbale character varying, data_verbale timestamp without time zone, stato_preaccettazione text) AS
$BODY$
DECLARE

	id_preacc integer;
	_stato integer;
	
BEGIN

	select id into id_preacc from preaccettazione.codici_preaccettazione
		where concat(prefix,anno,progres) ilike trim(_codpreacc);
	
	select id_stato into _stato from preaccettazione.stati_preaccettazione 
		where id_preaccettazione = id_preacc order by id_stato desc limit 1;

	IF _stato = 1 THEN 

		perform preaccettazione.lettura_da_laboratorio(_codpreacc,_idutente,_iplettura); 
		
		return query
		select distinct
		       ram.norma, --text
		       trim(regexp_replace(ram.ragione_sociale, '\s+', ' ', 'g'))::character varying(79) as ragione_sociale,
		       --ram.ragione_sociale, --character varying(300)
		       ram.partita_iva::text, --bpchar
		       ram.nominativo_rappresentante, --text
		       ram.codice_fiscale_rappresentante, --character varying
		       ram.asl_rif, --integer
		       ram.asl, --character varying(300)
		       trim(regexp_replace(ram.indirizzo, '\s+', ' ', 'g'))::character varying(49) as indirizzo, --character varying
		       ram.comune, --character varying
		       ram.provincia_stab, --character varying
		       ram.n_linea, --character varying
		       ram.attivita, --text
		       --------------inizio RQ1 flusso 254------------
		       concat_ws('-',ram.codice_macroarea, ram.codice_aggregazione, ram.codice_attivita) as codiceml,
		       case when ram.tipologia_operatore=2 then (select specie_allevamento from organization where org_id = ram.riferimento_id and ram.riferimento_id_nome_tab='organization')::text
		       else null::text
		       end as attr1,
		       case when ram.tipologia_operatore=2 then (select specie_allev from organization where org_id = ram.riferimento_id and ram.riferimento_id_nome_tab='organization')::text
		       else null::text
		       end as attr2,
		       -----------------fine RQ1 flusso 254-----------		       
		       sp.modified, --timestamp without time zone
		       sp.entered, --timestamp without time zone
		       concat(cp.prefix, cp.anno, cp.progres) as codPreacc, --text
		       null::character varying as codMatrice, --cp.matrice_campione::character varying as codMatrice, --codMatrice
		       null::character varying as codAnalita, --codAnalita
		       null::character varying as codQuesDiagn, --cp.quesito_diagnostico::character varying as codQuesDiagn, --null::character varying as codQuesDiagn, --codQuesDiagn
		       null::timestamp without time zone as data_prelievo,--'2018-05-27'::timestamp without time zone as data_prelievo, 
		       null::character varying as numero_verbale,--'864833'::character varying as numero_verbale,
		       null::timestamp without time zone as data_verbale, --data_verbale
		       'Incompleto: non associato al campione'::text as stato_preaccettazione --stato della preaccettazione: completo sta per associato al campione
	
		from ricerche_anagrafiche_old_materializzata ram 
			join preaccettazione.associazione_preaccettazione_entita ap
				on concat(ram.riferimento_id, ram.riferimento_id_nome, ram.riferimento_id_nome_tab, ram.id_linea, ram.tipologia_operatore) = 
				concat(ap.lda_riferimento_id, ap.lda_riferimento_id_nome, ap.lda_riferimento_id_nome_tab, ap.lda_id_linea, ap.tipologia_operatore)
			join preaccettazione.stati_preaccettazione sp on sp.id = ap.id_stati
			join preaccettazione.codici_preaccettazione cp on cp.id = sp.id_preaccettazione
		where concat(cp.prefix, cp.anno, cp.progres) ilike trim(_codPreacc) and sp.id_stato = 1
		-- adding 15/07
		and cp.id_ente = _id_ente and cp.id_laboratorio = _id_laboratorio;
 
	END IF;

	--se la preaccettazione del campione � completa
	IF _stato = 2 or _stato = 3 or _stato = 5 THEN

		perform preaccettazione.lettura_da_laboratorio(_codpreacc,_idutente,_iplettura); 

		return query
		select distinct
		       ram.norma, --text
		       trim(regexp_replace(ram.ragione_sociale, '\s+', ' ', 'g'))::character varying(79) as ragione_sociale,
		       --ram.ragione_sociale, --character varying(300)
		       ram.partita_iva::text, --bpchar
		       ram.nominativo_rappresentante, --text
		       ram.codice_fiscale_rappresentante, --character varying
		       ram.asl_rif, --integer
		       ram.asl, --character varying(300)
		       trim(regexp_replace(ram.indirizzo, '\s+', ' ', 'g'))::character varying(49) as indirizzo, --character varying
		       ram.comune, --character varying
		       ram.provincia_stab, --character varying
		       ram.n_linea, --character varying
		       ram.attivita, --text
		       --------------inizio RQ1 flusso 254------------
		      concat_ws('-',ram.codice_macroarea, ram.codice_aggregazione, ram.codice_attivita) as codiceml,
		       case when ram.tipologia_operatore=2 then (select specie_allevamento from organization where org_id = ram.riferimento_id and ram.riferimento_id_nome_tab='organization')::text
		       else null::text
		       end as attr1,
		       case when ram.tipologia_operatore=2 then (select specie_allev from organization where org_id = ram.riferimento_id and ram.riferimento_id_nome_tab='organization')::text
		       else null::text
		       end as attr2,
		       -----------------fine RQ1 flusso 254-----------
		       sp.modified, --timestamp without time zone
		       sp.entered, --timestamp without time zone
		       concat(cp.prefix, cp.anno, cp.progres) as codPreacc, --text
		       --null::character varying as codMatrice, 
		       --COALESCE(NULLIF(trim(m.codice_esame),''),NULLIF(trim(m.nome),''))::character varying as codMatrice, --codMatrice
		       --COALESCE(trim(m.codice_esame),'')::character varying as codMatrice,
		       CASE WHEN (length(trim(coalesce(m3.codice_esame,''))) = 0) THEN
				 COALESCE(trim(concat(m2.codice_esame || ';', m.codice_esame)),'')::character varying
			    ELSE 
				COALESCE(trim(concat(m3.codice_esame || ';', m2.codice_esame)),'')::character varying 
		       end as codMatrice,
		       
		       null::character varying as codAnalita, 
		       --COALESCE(NULLIF(trim(a.codice_esame),''),NULLIF(trim(a.nome),''))::character varying as codAnalita, --codAnalita
		       
		       --null::character varying as codQuesDiagn, 
		       --COALESCE(NULLIF(trim(qds.codice_esame),''),NULLIF(trim(qds.description),''))::character varying as codQuesDiagn, --codQuesDiagn
		       COALESCE(trim(qds.codice_esame),'')::character varying as codQuesDiagn,
		       t.assigned_date as data_prelievo,--'2018-05-27'::timestamp without time zone as data_prelievo, 
		       t.location as numero_verbale,--'864833'::character varying as numero_verbale,
		       t.assigned_date as data_verbale, --data_verbale
		       case when _stato = 5 then 
				'RISULTATO RICEVUTO'::text  --stato della preaccettazione: risultato ricevuto sta ricevuto esito esame inviato da sigla a gisa
			    else 
				'COMPLETO'::text  --stato della preaccettazione: completo sta per associato al campione
			    end as stato_preaccettazione
	
		from ricerche_anagrafiche_old_materializzata ram 
			join preaccettazione.associazione_preaccettazione_entita ap
				on concat(ram.riferimento_id, ram.riferimento_id_nome, ram.riferimento_id_nome_tab, ram.id_linea, ram.tipologia_operatore) = 
				concat(ap.lda_riferimento_id, ap.lda_riferimento_id_nome, ap.lda_riferimento_id_nome_tab, ap.lda_id_linea, ap.tipologia_operatore)
			join preaccettazione.stati_preaccettazione sp on sp.id = ap.id_stati
			join preaccettazione.codici_preaccettazione cp on cp.id = sp.id_preaccettazione
			--verbale
			join ticket t on (t.ticketid = ap.riferimento_entita::integer and ap.tipo_entita ilike 'C')
			--matrice
			left join matrici_campioni mc on (mc.id_campione = ap.riferimento_entita::integer and ap.tipo_entita ilike 'C')
			left join matrici m on (mc.id_matrice = m.matrice_id)
			left join matrici m2 on (m2.matrice_id = m.id_padre)
			left join matrici m3 on (m3.matrice_id = m2.id_padre)
			--analita
			left join analiti_campioni ac on (ac.id_campione = ap.riferimento_entita::integer and ap.tipo_entita ilike 'C')
			left join analiti a on (a.analiti_id = ac.analiti_id)
			--quesito diagnostico
			left join quesiti_diagnostici_sigla qds on qds.code = t.motivazione_piano_campione  
		where concat(cp.prefix, cp.anno, cp.progres) ilike trim(_codPreacc) and sp.id_stato = 2
		-- adding 15/07
		and cp.id_ente = _id_ente and cp.id_laboratorio = _id_laboratorio;
 
	END IF;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION preaccettazione.get_anagrafica(text, integer, text, integer, integer)
  OWNER TO postgres;
  -------------------------------------------------------------- 12/08
  -- Function: preaccettazione.cancella_preaccettazione(integer, integer, integer)

-- DROP FUNCTION preaccettazione.cancella_preaccettazione(integer, integer, integer);

CREATE OR REPLACE FUNCTION preaccettazione.cancella_preaccettazione(
    IN _id_campione integer,
    IN _idutente integer,
    IN _cancella integer)
  RETURNS TABLE(esito_cancellazione integer, errore_cancellazione text) AS
$BODY$
DECLARE

	temp_id_preacc integer;
	temp_stato_preacc integer;
	messaggio text;

BEGIN
	
	--recupero codice preaccettazione da campione
	select sp.id_preaccettazione into temp_id_preacc from preaccettazione.stati_preaccettazione sp 
	      join preaccettazione.associazione_preaccettazione_entita ape on sp.id = ape.id_stati
	      where ape.riferimento_entita like _id_campione::character varying and ape.tipo_entita like 'C';

	--recupero lo stato del codice di preaccettazione
	select id_stato into temp_stato_preacc from preaccettazione.vw_ultimo_stato where id_preaccettazione = temp_id_preacc;

	--0;CODICE PREACCETTAZIONE GENERATO
	--1;CODICE PREACCETTAZIONE ASSOCIATO ALL OSA
	--2;CODICE PREACCETTAZIONE ASSOCIATO AL CAMPIONE
	--3;CODICE PREACCETTAZIONE LETTO DA LABORATORIO
	--4;CODICE PREACCETTAZIONE ANNULLATO
	--5;RISULTATO RICEVUTO

	IF temp_stato_preacc = 3 or temp_stato_preacc = 5 THEN

		messaggio := concat('Cancellazione non eseguibile: la cancellazione non e'' eseguibile perche'' coinvolge un campione (', _id_campione,') il cui questito diagnostico e'' stato gia'' letto dal laboratorio di destinazione e/o con esito ricevuto.');
		return query
		select 1, messaggio from preaccettazione.lookup_stati_pa  limit 1;
		
	ELSE
		IF _cancella = 1 and temp_stato_preacc <> 4 THEN
			insert into preaccettazione.stati_preaccettazione(id_preaccettazione, id_stato, entered, enteredby, note_hd) 
			values (temp_id_preacc,4,now(),_idutente,'preaccettazione cancellata a seguito di cancellazione campione da gisa');
		END IF;
				
		messaggio :='';
		return query
		select 0, messaggio from preaccettazione.lookup_stati_pa  limit 1;
		
	END IF;
		
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION preaccettazione.cancella_preaccettazione(integer, integer, integer)
  OWNER TO postgres;



-- DROP FUNCTION preaccettazionesigla.get_codice_preaccettazione(integer, text, text,text,text);

CREATE OR REPLACE FUNCTION preaccettazione.get_codice_preaccettazione(
    IN _idutente integer,
    IN _ente integer DEFAULT -1::integer,
    IN _laboratorio integer DEFAULT -1::integer,
    IN _ip_chiamante text DEFAULT NULL::text,
    IN _user_agent_chiamante text DEFAULT NULL::text)
  RETURNS TABLE(_id integer, _prefix character varying, _anno character varying, _progres character varying) AS
$BODY$
DECLARE

	progressivo integer;
	id_preacc integer;
	codice_ente text;
	codice_laboratorio text;
BEGIN	

	codice_ente := (select short_description from preaccettazione.lookup_ente where code = _ente);
	codice_laboratorio := (select short_description from lookup_destinazione_campione where code = _laboratorio);

	select coalesce(max(progres::integer),0) into progressivo from preaccettazione.codici_preaccettazione where anno ilike to_char(now(), 'YYYY');
	progressivo := progressivo + 1;
	
	insert into preaccettazione.codici_preaccettazione(prefix,anno,progres,entered,enteredby, id_ente, id_laboratorio)
		values ( concat(codice_ente,'2',codice_laboratorio), 
			 to_char(now(), 'YYYY'), 
			 to_char(progressivo, 'fm000000'),
			 now(),  
			 _idUtente,
			 _ente,
			 _laboratorio) returning id into id_preacc;


	insert into preaccettazione.stati_preaccettazione(id_preaccettazione,id_stato,entered, enteredby, ip_chiamante, user_agent_chiamante)
		values (id_preacc,
			0,
			now(),  
			_idUtente,
			_ip_chiamante,
			_user_agent_chiamante);
	return query
	select id, prefix, anno, progres from preaccettazione.codici_preaccettazione where id = id_preacc;
	
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION preaccettazione.get_codice_preaccettazione(integer, integer, integer, text, text)
  OWNER TO postgres;

  

