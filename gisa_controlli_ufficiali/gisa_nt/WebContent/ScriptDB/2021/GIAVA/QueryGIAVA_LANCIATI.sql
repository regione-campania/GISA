
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
	
	-- se richiamo la funzione con parametri verifico se c'Ë corrispondenza con la ML10 e se non si tratta di linee NO-SCIA NON CATEGORIZZABILI GESTITE IN ALTRI STABILIMENTI
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
			--se si gestr‡ il codice norma nella tabella materializzata delle linee si potr‡ rimuovere il left join con le norme..
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
	
	IF (query_check > 0) then --c'Ë corrispondenza con ML
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
			--(r.id_attivita=m.id_linea) non si puÚ usare il join sugli id perchË non tutti hanno un idlinea riconducibile alla tabella di linee 
			left join ml8_linee_attivita_nuove_materializzata l on l.id_nuova_linea_attivita = m.id_linea and l.livello=3 -- si considerano le linee complete di attivita
			where 1=1
			and (_riferimento_id  is null or r.riferimento_id = _riferimento_id)
			and (_riferimento_id_nome_tab is null or r.riferimento_id_nome_tab = _riferimento_id_nome_tab)
		LOOP
			RETURN NEXT;
		END LOOP;
		RETURN;	

		
	ELSIF (check_linee_nc = 0 and check_linee_az = 0) THEN --restituisci tutto se non si tratta di no-scia nË di aziende zootecniche
		RAISE INFO 'Entro nel ciclo di MANCATA corrispondenza ML E di assenza di linee no-scia per cui restituisco tutti i record';
		FOR  	 
			riferimento_id, riferimento_id_nome_tab, codice_univoco, id_norma, codice_norma, id_macroarea, macroarea, codice_macroarea, id_aggregazione, aggregazione, codice_aggregazione, id_linea, attivita, codice_attivita, mobile, fisso, apicoltura, 
			registrabili, riconoscibili, sintesis, bdu, vam, no_scia, categorizzabili, rev, categoria_rischio_default
		in
			select distinct -1 as riferimento_id, '' as riferimento_id_nome_tab, m.codice_univoco, 
			--se si gestr‡ il codice norma nella tabella materializzata delle linee si potr‡ rimuovere il left join con le norme..
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


select distinct stato from ricerche_anagrafiche_old_materializzata where riferimento_id_nome_tab  ='organization' and tipologia_operatore=2   -- totale record 472731

select * from giava.get_info_masterlist(123241,'opu_stabilimento') --test ok
select * from giava.get_info_masterlist(100002018,'sintesis_stabilimento') -- test ok, 2 linee, 2 record
select * from giava.get_info_masterlist(31333,'organization') -- test ok, scenario di vecchia anagrafica con match sul codice ml
select * from giava.get_info_masterlist(2226,'apicoltura_imprese') -- ko apicoltura non ha riferimento a ML
select * from giava.get_info_masterlist(1057574, 'organization') -- ko allevamento non ha riferimento a ML


-- Function: digemon.get_info_masterlist(integer, text)

-- DROP FUNCTION digemon.get_info_masterlist(integer, text);

CREATE OR REPLACE FUNCTION giava.get_info_masterlist_test(
    IN _riferimento_id integer DEFAULT NULL::integer,
    IN _riferimento_id_nome_tab text DEFAULT NULL::text)
  RETURNS TABLE(riferimento_id integer, riferimento_id_nome_tab text, codice_univoco text, id_norma integer, codice_norma text, id_macroarea integer, macroarea text, codice_macroarea text, id_aggregazione integer, aggregazione text, codice_aggregazione text, id_linea integer, attivita text, codice_attivita text, mobile boolean, fisso boolean, apicoltura boolean, registrabili boolean, riconoscibili boolean, sintesis boolean, bdu boolean, vam boolean, no_scia boolean, categorizzabili boolean, rev integer, categoria_rischio_default integer) AS
$BODY$
DECLARE
	query_check int;
BEGIN

	-- se richiamo la funzione con parametri verifico se c'√® corrispondenza con la ML10
	IF ($1 is not null and $2 is not null) then
		query_check:= (select count(*) from ricerche_anagrafiche_old_materializzata r 
                       join master_list_flag_linee_attivita m on m.codice_univoco = concat_ws('-', r.codice_macroarea, r.codice_aggregazione, r.codice_attivita)
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
			--se si gestr√† il codice norma nella tabella materializzata delle linee si potr√† rimuovere il left join con le norme..
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

	IF (query_check > 0) then --c'√® corrispondenza con ML
		RAISE INFO 'Entro nel ciclo di corrispondenza ML';
		FOR  	 
			riferimento_id, riferimento_id_nome_tab, codice_univoco, id_norma, codice_norma, id_macroarea, macroarea, codice_macroarea, id_aggregazione, aggregazione, codice_aggregazione, id_linea, attivita, codice_attivita, mobile, fisso, apicoltura, 
			registrabili, riconoscibili, sintesis, bdu, vam, no_scia, categorizzabili, rev, categoria_rischio_default
		in
			select distinct r.riferimento_id, r.riferimento_id_nome_tab, m.codice_univoco, 
			l.id_norma, l.codice_norma, l.id_macroarea, l.macroarea, l.codice_macroarea, l.id_aggregazione, l.aggregazione, l.codice_aggregazione,l.id_nuova_linea_attivita as id_linea, l.attivita, l.codice_attivita, 
			m.mobile, m.fisso, m.apicoltura, m.registrabili, m.riconoscibili, m.sintesis, m.bdu, m.vam, m.no_scia, m.categorizzabili, m.rev, m.categoria_rischio_default
			from ricerche_anagrafiche_old_materializzata r -- partiamo da tutti gli stabilimenti
			left join master_list_flag_linee_attivita m on m.codice_univoco = concat_ws('-', r.codice_macroarea, r.codice_aggregazione, r.codice_attivita) and m.rev=10 -- considero l'ultima revisione, a parit√† di codice
			--(r.id_attivita=m.id_linea) non si pu√≤ usare il join sugli id perch√® non tutti hanno un idlinea riconducibile alla tabella di linee 
			left join ml8_linee_attivita_nuove_materializzata l on l.id_nuova_linea_attivita = m.id_linea and l.livello=3 -- si considerano le linee complete di attivita
			where 1=1
			and (_riferimento_id  is null or r.riferimento_id = _riferimento_id)
			and (_riferimento_id_nome_tab is null or r.riferimento_id_nome_tab = _riferimento_id_nome_tab)
		LOOP
			RETURN NEXT;
		END LOOP;
		RETURN;	
		
	ELSE --restituisci tutto
	RAISE INFO 'Entro nel ciclo di MANCATA corrispondenza ML e restituisco tutti i record';
		FOR  	 
			riferimento_id, riferimento_id_nome_tab, codice_univoco, id_norma, codice_norma, id_macroarea, macroarea, codice_macroarea, id_aggregazione, aggregazione, codice_aggregazione, id_linea, attivita, codice_attivita, mobile, fisso, apicoltura, 
			registrabili, riconoscibili, sintesis, bdu, vam, no_scia, categorizzabili, rev, categoria_rischio_default
in
			select distinct -1 as riferimento_id, '' as riferimento_id_nome_tab, m.codice_univoco, 
			--se si gestr√† il codice norma nella tabella materializzata delle linee si potr√† rimuovere il left join con le norme..
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

	end if;
	
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION giava.get_info_masterlist_test(integer, text)
  OWNER TO postgres;

select * from giava.get_info_masterlist_test(123241,'opu_stabilimento') --test ok
select * from giava.get_info_masterlist_test(100002018,'sintesis_stabilimento') -- test ok, 2 linee, 2 record
select * from giava.get_info_masterlist_test(31333,'organization') -- test ok, scenario di vecchia anagrafica con match sul codice ml
select * from giava.get_info_masterlist_test(2226,'apicoltura_imprese') -- ko apicoltura non ha riferimento a ML
select * from giava.get_info_masterlist_test(1057574, 'organization') -- ko allevamento non ha riferimento a ML e restituisce tutti i record
select * from giava.get_info_masterlist_test() -- tutti i record

----------------------------------------------------- da fare ------------------------------------------
DROP FUNCTION digemon.get_info_masterlist(integer, text);
DROP FUNCTION digemon.get_info_masterlist_test(integer, text);
DROP FUNCTION public.login_giava(text, text, text);
DROP FUNCTION public.login_giava(text, text);
----------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION digemon.get_info_masterlist(IN _codice_univoco text DEFAULT NULL::text)
  RETURNS TABLE(codice_univoco text, id_norma integer, codice_norma text, id_macroarea integer, macroarea text, codice_macroarea text, id_aggregazione integer, aggregazione text, codice_aggregazione text, id_linea integer, attivita text, codice_attivita text, mobile boolean, fisso boolean, apicoltura boolean, registrabili boolean, riconoscibili boolean, sintesis boolean, bdu boolean, vam boolean, no_scia boolean, categorizzabili boolean, rev integer, categoria_rischio_default integer) AS
$BODY$
DECLARE

BEGIN

FOR  	 
        codice_univoco, id_norma, codice_norma, id_macroarea, macroarea, codice_macroarea, id_aggregazione, aggregazione, codice_aggregazione, id_linea, attivita, codice_attivita, mobile, fisso, apicoltura, 
        registrabili, riconoscibili, sintesis, bdu, vam, no_scia, categorizzabili, rev, categoria_rischio_default
in
        select distinct m.codice_univoco, 
        --se si gestr√† il codice norma nella tabella materializzata delle linee si potr√† rimuovere il left join con le norme..
	l.id_norma, l.codice_norma, l.id_macroarea, l.macroarea, l.codice_macroarea, l.id_aggregazione, l.aggregazione, l.codice_aggregazione,l.id_nuova_linea_attivita as id_linea, l.attivita, l.codice_attivita, 
        m.mobile, m.fisso, m.apicoltura, m.registrabili, m.riconoscibili, m.sintesis, m.bdu, m.vam, m.no_scia, m.categorizzabili, m.rev, m.categoria_rischio_default
        from master_list_flag_linee_attivita m 
        join ml8_linee_attivita_nuove_materializzata l on l.id_nuova_linea_attivita = m.id_linea and l.livello=3
        where 1=1 
        and ( _codice_univoco is null or m.codice_univoco = _codice_univoco)

    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
  
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION digemon.get_info_masterlist(text)
  OWNER TO postgres;

--select * from  giava.login_giava('a.rispoli_trasp','fopoli2016',null)
--a.rispoli_trasp

  --pulizia checklist domandas

  --pulizia checklist domandas
update checklist set domanda = replace(domanda, '√≤', 'o''');
update checklist set domanda = replace(domanda, '√®', 'e''');
update checklist set domanda = replace(domanda, '√', 'a''');
update checklist set domanda =  replace(domanda, '¬∞', '∞');




