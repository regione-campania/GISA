-- Table: public.rel_checklist_sorv_ml

-- DROP TABLE public.rel_checklist_sorv_ml;

CREATE TABLE public.rel_checklist_sorv_ml
(
  id serial,
  id_linea integer,
  id_checklist integer,
  enabled boolean DEFAULT true,
  note_hd text
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.rel_checklist_sorv_ml
  OWNER TO postgres;

insert into rel_checklist_sorv_ml(id_linea, id_checklist, enabled) values(40861,2058 , true);

insert into lookup_org_catrischio (code, description, short_description, enabled, versione, versione_alfonso, num_chk) values(2058,'Deposito ingrosso (rev. 3)','AUDIT DEPOSIO INGROSSO', true, '3.1','DEPOSITO PRODOTTI ALIMENTARI', 11);
insert into lookup_org_catrischio (code, description, enabled) values(-1,'XX',false);

select codice from ml8_linee_attivita_nuove_materializzata where id_nuova_linea_attivita  = 40861
select * from master_list_flag_linee_attivita  where codice_univoco  = 'MS.B-MS.B00-MS.B00.100'
insert into checklist_type(code, catrischio_id, description, range, level, enabled) values(-1, -1, 'test', 0, 0, false );

CREATE SCHEMA giava
  AUTHORIZATION postgres;

-- Function: giava.get_info_masterlist(integer, text)

-- DROP FUNCTION giava.get_info_masterlist(integer, text);

CREATE OR REPLACE FUNCTION giava.get_info_masterlist(
    IN _riferimento_id integer DEFAULT NULL::integer,
    IN _riferimento_id_nome_tab text DEFAULT NULL::text)
  RETURNS TABLE(riferimento_id integer, riferimento_id_nome_tab text, codice_univoco text, id_norma integer, codice_norma text, id_macroarea integer, macroarea text, codice_macroarea text, id_aggregazione integer, aggregazione text, codice_aggregazione text, id_linea integer, attivita text, codice_attivita text, mobile boolean, fisso boolean, apicoltura boolean, registrabili boolean, riconoscibili boolean, sintesis boolean, bdu boolean, vam boolean, no_scia boolean, categorizzabili boolean, rev integer, categoria_rischio_default integer) AS
$BODY$
DECLARE
	query_check int;
	check_linee_nc int;
	check_linee_az int;
BEGIN
	
	-- se richiamo la funzione con parametri verifico se c'è corrispondenza con la ML10 e se non si tratta di linee NO-SCIA NON CATEGORIZZABILI GESTITE IN ALTRI STABILIMENTI
	IF ($1 is not null and $2 is not null) then

		check_linee_nc := (select count(*) from ricerche_anagrafiche_old_materializzata m where m.riferimento_id = _riferimento_id and m.riferimento_id_nome_tab = _riferimento_id_nome_tab and concat_ws('-', m.codice_macroarea, m.codice_aggregazione, m.codice_attivita) in 
				  ('TAV-TAV-X','ONPA-ONPA-X','PSB-PSB-X','AQRE-AQRE-AQRE','MOLLBAN-MOLLBAN-MOLLBAN','OAB-OAB-X','OFA-OFA-X','IUV-CF-CF','IUV-ZDC-ZDC'));
	        raise info 'totali linee no scie %', check_linee_nc;
		check_linee_az := (select count(*) from ricerche_anagrafiche_old_materializzata m where m.tipologia_operatore = 2 and m.riferimento_id = _riferimento_id 
				   and m.riferimento_id_nome_tab = _riferimento_id_nome_tab);
		raise info 'totali linee aziende zootecniche %', check_linee_az;
		query_check:= (select count(*) from ricerche_anagrafiche_old_materializzata r 
                       join master_list_flag_linee_attivita m on m.id_linea = r.id_attivita
                       where r.riferimento_id = _riferimento_id
                       and   r.riferimento_id_nome_tab = _riferimento_id_nome_tab);
                 RAISE NOTICE 'Eseguo query di controllo. Totale valori di match:%', query_check; 
	-- chiamo la dbi senza parametri
	ELSE
		FOR  	 
			riferimento_id, riferimento_id_nome_tab, codice_univoco, id_norma, codice_norma, id_macroarea, macroarea, codice_macroarea, id_aggregazione, aggregazione, codice_aggregazione, id_linea, attivita, codice_attivita, mobile, fisso, apicoltura, 
			registrabili, riconoscibili, sintesis, bdu, vam, no_scia, categorizzabili, rev, categoria_rischio_default
in
			select distinct -1 as riferimento_id, '' as riferimento_id_nome_tab, m.codice_univoco, 
			--se si gestrà il codice norma nella tabella materializzata delle linee si potrà rimuovere il left join con le norme..
			l.id_norma, l.codice_norma, l.id_macroarea, l.macroarea, l.codice_macroarea, l.id_aggregazione, l.aggregazione, l.codice_aggregazione,l.id_nuova_linea_attivita as id_linea, l.attivita, l.codice_attivita, 
			m.mobile, m.fisso, m.apicoltura, m.registrabili, m.riconoscibili, m.sintesis, m.bdu, m.vam, m.no_scia, m.categorizzabili, m.rev, m.categoria_rischio_default
			from master_list_flag_linee_attivita m 
			join ml8_linee_attivita_nuove_materializzata l on l.id_nuova_linea_attivita = m.id_linea and l.livello=3
			where 1=1 and m.rev=10
			--and ( _codice_univoco is null or m.codice_univoco = _codice_univoco)
		LOOP
			RETURN NEXT;
		END LOOP;
		RETURN;

	END IF; 
	
	IF (query_check > 0) then --c'è corrispondenza con ML
		RAISE INFO 'Entro nel ciclo di corrispondenza ML';
		FOR  	 
			riferimento_id, riferimento_id_nome_tab, codice_univoco, id_norma, codice_norma, id_macroarea, macroarea, codice_macroarea, id_aggregazione, aggregazione, codice_aggregazione, id_linea, attivita, codice_attivita, mobile, fisso, apicoltura, 
			registrabili, riconoscibili, sintesis, bdu, vam, no_scia, categorizzabili, rev, categoria_rischio_default
		in
			select distinct r.riferimento_id, r.riferimento_id_nome_tab, m.codice_univoco, 
			l.id_norma, l.codice_norma, l.id_macroarea, l.macroarea, l.codice_macroarea, l.id_aggregazione, l.aggregazione, l.codice_aggregazione,l.id_nuova_linea_attivita as id_linea, l.attivita, l.codice_attivita, 
			m.mobile, m.fisso, m.apicoltura, m.registrabili, m.riconoscibili, m.sintesis, m.bdu, m.vam, m.no_scia, m.categorizzabili, m.rev, m.categoria_rischio_default
			from ricerche_anagrafiche_old_materializzata r -- partiamo da tutti gli stabilimenti
			left join master_list_flag_linee_attivita m on m.id_linea = r.id_attivita --and m.rev=10 -- considero l'ultima revisione??Si deve togliere la rev.10?--
			--(r.id_attivita=m.id_linea) non si può usare il join sugli id perchè non tutti hanno un idlinea riconducibile alla tabella di linee 
			left join ml8_linee_attivita_nuove_materializzata l on l.id_nuova_linea_attivita = m.id_linea and l.livello=3 -- si considerano le linee complete di attivita
			where 1=1
			and (_riferimento_id  is null or r.riferimento_id = _riferimento_id)
			and (_riferimento_id_nome_tab is null or r.riferimento_id_nome_tab = _riferimento_id_nome_tab)
		LOOP
			RETURN NEXT;
		END LOOP;
		RETURN;	

		
	ELSIF (check_linee_nc = 0 and check_linee_az = 0) THEN --restituisci tutto se non si tratta di no-scia nè di aziende zootecniche
		RAISE INFO 'Entro nel ciclo di MANCATA corrispondenza ML E di assenza di linee no-scia per cui restituisco tutti i record';
		FOR  	 
			riferimento_id, riferimento_id_nome_tab, codice_univoco, id_norma, codice_norma, id_macroarea, macroarea, codice_macroarea, id_aggregazione, aggregazione, codice_aggregazione, id_linea, attivita, codice_attivita, mobile, fisso, apicoltura, 
			registrabili, riconoscibili, sintesis, bdu, vam, no_scia, categorizzabili, rev, categoria_rischio_default
		in
			select distinct -1 as riferimento_id, '' as riferimento_id_nome_tab, m.codice_univoco, 
			--se si gestrà il codice norma nella tabella materializzata delle linee si potrà rimuovere il left join con le norme..
			l.id_norma, l.codice_norma, l.id_macroarea, l.macroarea, l.codice_macroarea, l.id_aggregazione, l.aggregazione, l.codice_aggregazione,l.id_nuova_linea_attivita as id_linea, l.attivita, l.codice_attivita, 
			m.mobile, m.fisso, m.apicoltura, m.registrabili, m.riconoscibili, m.sintesis, m.bdu, m.vam, m.no_scia, m.categorizzabili, m.rev, m.categoria_rischio_default
			from master_list_flag_linee_attivita m 
			join ml8_linee_attivita_nuove_materializzata l on l.id_nuova_linea_attivita = m.id_linea and l.livello=3
			where 1=1 --and m.rev=10 (si deve togliere il riferimento alla ml10??)
			--and ( _codice_univoco is null or m.codice_univoco = _codice_univoco)
						
		LOOP
			RETURN NEXT;
		END LOOP;
		RETURN;	
		
	ELSIF (check_linee_az > 0) THEN
		RETURN QUERY SELECT -1, null, null, -1, null, -1, null, null, -1, null, null, -1, null, null, false, false, false,
		false, false, false, false, false, false,true, -1, -1;

	ELSE 
		RAISE INFO 'Entro nel ciclo di linee no-scia per cui non restituisco alcun record';
		RETURN;

	end if;
	
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION giava.get_info_masterlist(integer, text)
  OWNER TO postgres;

-- Function: giava.get_checklist_by_idlinea(integer)

-- DROP FUNCTION giava.get_checklist_by_idlinea(integer);

CREATE OR REPLACE FUNCTION giava.get_checklist_by_idlinea(IN _id_linea_ml integer)
  RETURNS TABLE(code integer, description text, short_description character varying, enabled boolean, versione double precision, num_chk integer) AS
$BODY$
DECLARE

	linea_categorizzabile boolean;
	conta_res integer;
BEGIN
	linea_categorizzabile := (select categorizzabili from master_list_flag_linee_attivita  where id_linea = _id_linea_ml);

	if linea_categorizzabile then

		conta_res := (select count(*) 
				from lookup_org_catrischio c
				left join rel_checklist_sorv_ml rel on rel.id_checklist = c.code
				where c.enabled and rel.enabled and rel.id_linea = _id_linea_ml);
				
		if conta_res > 0 then
			
			RETURN QUERY
				select c.code, c.versione_alfonso, c.short_description, c.enabled, c.versione, c.num_chk
				from lookup_org_catrischio c
				left join rel_checklist_sorv_ml rel on rel.id_checklist = c.code
				where c.enabled and rel.enabled and rel.id_linea = _id_linea_ml;
		else
			RETURN QUERY
			     select -1, '', ''::character varying, false, -1::double precision, -1 from lookup_org_catrischio limit 1;

		end if;
	else 
		return; 
	end if;

   END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION giava.get_checklist_by_idlinea(integer)
  OWNER TO postgres;


CREATE TABLE public.checklist_type
(
  code serial,
  catrischio_id integer NOT NULL,
  description character varying(300) NOT NULL,
  range integer DEFAULT 0,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  is_disabilitato boolean,
  is_disabilitato_solo_xlaprima boolean,
  is_disabilitabile integer,
  CONSTRAINT checklist_type_pkey PRIMARY KEY (code),
  CONSTRAINT checklist_type_catrischio_id_fkey FOREIGN KEY (catrischio_id)
      REFERENCES public.lookup_org_catrischio (code) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.checklist_type
  OWNER TO postgres;

-- Index: public.idx_checklist_type_catrischio_id

-- DROP INDEX public.idx_checklist_type_catrischio_id;

CREATE INDEX idx_checklist_type_catrischio_id
  ON public.checklist_type
  USING btree
  (catrischio_id);


insert into checklist_type(code, catrischio_id, description, range, level, enabled, is_disabilitato, is_disabilitato_solo_xlaprima,is_disabilitabile) values
(15524, 2058, 'CAPITOLO IV: SERVIZI IGIENICI', 5, 6, true, true, false,2);
insert into checklist_type(code, catrischio_id, description, range, level, enabled, is_disabilitato, is_disabilitato_solo_xlaprima,is_disabilitabile) values
(15526, 2058, 'CAPITOLO VI: PERSONALE', 10, 8, true, true, false,2);
insert into checklist_type(code, catrischio_id, description, range, level, enabled, is_disabilitato, is_disabilitato_solo_xlaprima,is_disabilitabile) values
(15523, 2058, 'CAPITOLO III: RIFIUTI ', 3, 5, true, false, false,0);
insert into checklist_type(code, catrischio_id, description, range, level, enabled, is_disabilitato, is_disabilitato_solo_xlaprima,is_disabilitabile) values
(15529, 2058, 'CAPITOLO IX: DATI STORICI (DA ESTRAPOLARE PREVENTIVAMENTE DALLA SCHEDA DELL''IMPRESA PRESENTE SUL SITO DELL''O.R.S.A.)', 20, 11, false, true, false,0);
insert into checklist_type(code, catrischio_id, description, range, level, enabled, is_disabilitato, is_disabilitato_solo_xlaprima,is_disabilitabile) values
(15519, 2058, 'CAPITOLO I: LOCALI,IMPIANTI ED ATTREZZATURE (PARTE COMUNE)', 20, 1, true, false, false,0);
insert into checklist_type(code, catrischio_id, description, range, level, enabled, is_disabilitato, is_disabilitato_solo_xlaprima,is_disabilitabile) values
(15520, 2058, 'CAPITOLO I A:  DEPOSITO DI CARNI FRESCHE NON TRASFORMATE', 60, 2, true, true, true,3);
insert into checklist_type(code, catrischio_id, description, range, level, enabled, is_disabilitato, is_disabilitato_solo_xlaprima,is_disabilitabile) values
(15521, 2058, 'CAPITOLO I B:  DEPOSITO DI PRODOTTI DELLA PESCA IN REGIME DI TEMPERATURA CONTROLLATA O VIVI', 60, 3, true, true, true,3);
insert into checklist_type(code, catrischio_id, description, range, level, enabled, is_disabilitato, is_disabilitato_solo_xlaprima,is_disabilitabile) values
(15522, 2058, 'CAPITOLO I C:  DEPOSITO DI PRODOTTI ALIMENTARI IN GENERE', 50, 4, true, true, true,3);
insert into checklist_type(code, catrischio_id, description, range, level, enabled, is_disabilitato, is_disabilitato_solo_xlaprima,is_disabilitabile) values
(15525, 2058, 'CAPITOLO V: SPOGLIATOIO', 8, 7, true, true, false,2);
insert into checklist_type(code, catrischio_id, description, range, level, enabled, is_disabilitato, is_disabilitato_solo_xlaprima,is_disabilitabile) values
(15527, 2058, 'CAPITOLO VII: ENTITA'' PRODUTTIVA,TARGET DI RIFERIMENTO E CARATTERISTICHE DEI PRODOTTI', 30, 9, true, false, false,0);
insert into checklist_type(code, catrischio_id, description, range, level, enabled, is_disabilitato, is_disabilitato_solo_xlaprima,is_disabilitabile) values
(15528, 2058, 'CAPITOLO VIII: PROVVEDIMENTI ADOTTATI', 30, 10, true, false, false,0);

-- domande caricate con dump 
delete from checklist where id not in (select id from checklist where checklist_type_id  in (select code from checklist_type where catrischio_id =2058))