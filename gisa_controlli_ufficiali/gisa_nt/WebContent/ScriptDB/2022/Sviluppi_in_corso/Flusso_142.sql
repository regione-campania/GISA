alter table linee_attivita_controlli_ufficiali  add column categoria_quantitativa integer; -- potrebbe essere double

-- Function: public.get_linee_attivita(integer, text, boolean, integer)

-- DROP FUNCTION public.get_linee_attivita(integer, text, boolean, integer);

CREATE OR REPLACE FUNCTION public.get_linee_attivita(
    IN _riferimentoid integer,
    IN _riferimentoidnometab text,
    IN _primario boolean,
    IN _idcu integer)
  RETURNS TABLE(id integer, riferimento_id integer, id_rel_ateco_attivita integer, id_attivita_masterlist integer, mappato boolean, primario boolean, entered timestamp without time zone, entered_by integer, modified timestamp without time zone, modified_by integer, trashed_date timestamp without time zone, macroarea text, aggregazione text, attivita text, codice_linea text, categoria text, linea_attivita text, codice_istat text, descrizione_codice_istat text, id_attivita integer, enabled boolean) AS
$BODY$
DECLARE
r RECORD;	

BEGIN


IF (_riferimentoIdNomeTab = 'organization') THEN
FOR 
id , riferimento_id , id_rel_ateco_attivita , id_attivita_masterlist , mappato, primario, entered, entered_by, modified, modified_by, 
trashed_date, macroarea , aggregazione , attivita , codice_linea, categoria , linea_attivita , codice_istat , descrizione_codice_istat, id_attivita, enabled
in

select distinct
v.id , v.org_id , v.id_rel_ateco_attivita , v.id_attivita_masterlist , v.mappato, v.primario, v.entered, v.entered_by, v.modified, v.modified_by, 
v.trashed_date, v.macroarea , v.aggregazione , v.attivita , v.codice, v.categoria , coalesce(v.linea_attivita, v.attivita) as linea_attivita , v.codice_istat , v.descrizione_codice_istat, -1, true 
from org_linee_attivita_view v
left join linee_attivita_controlli_ufficiali lcu on v.id=lcu.id_linea_attivita
--left join ticket t on t.org_id = v.org_id
where 1=1 
and ((_riferimentoId>0 and v.org_id = _riferimentoId) or (_riferimentoId=-1))
and ((_idcu>0 and (lcu.id_controllo_ufficiale=_idcu and lcu.trashed_date is null)) or (_idcu=-1))

LOOP
RETURN NEXT;
END LOOP;
END IF;

IF (_riferimentoIdNomeTab = 'opu_stabilimento' or _riferimentoIdNomeTab = 'opu_relazione_stabilimento_linee_produttive') THEN
FOR 
id , riferimento_id , id_rel_ateco_attivita , id_attivita_masterlist , mappato, primario, entered, entered_by, modified, modified_by, trashed_date, macroarea , aggregazione , 
attivita , codice_linea, categoria , linea_attivita , codice_istat , descrizione_codice_istat, id_attivita, enabled
in

select  distinct
v.id , v.org_id , v.id_rel_ateco_attivita , -1 , null, v.primario, null, -1, null, -1, null, v.macroarea , null , null , v.codice, v.categoria , v.linea_attivita , v.codice_istat , null, v.id_attivita, v.enabled
from opu_linee_attivita_stabilimenti_view v
left join linee_attivita_controlli_ufficiali lcu on lcu.id_linea_attivita = v.id 
where 1=1 
and ((_riferimentoId>0 and v.org_id = _riferimentoId and v.enabled) or (_riferimentoId=-1))
and ((_idcu>0 and (lcu.id_controllo_ufficiale=_idcu and lcu.trashed_date is null)) or (_idcu=-1))

LOOP
RETURN NEXT;
END LOOP;
END IF;

IF (_riferimentoIdNomeTab = 'sintesis_stabilimento' or _riferimentoIdNomeTab = 'sintesis_relazione_stabilimento_linee_produttive') THEN
FOR 
id , riferimento_id , id_rel_ateco_attivita , id_attivita_masterlist , mappato, primario, entered, entered_by, modified, modified_by, 
trashed_date, macroarea , aggregazione , attivita , codice_linea, categoria , linea_attivita , codice_istat , descrizione_codice_istat, id_attivita, enabled
in

select  distinct
v.id , v.alt_id , v.id_rel_ateco_attivita , -1 , null, v.primario, null, -1, null, -1, null, v.macroarea , null , null , 
v.codice, v.categoria , v.linea_attivita , v.codice_istat , null, -1, v.enabled
from sintesis_linee_attivita_stabilimenti_view v
left join linee_attivita_controlli_ufficiali lcu on lcu.id_linea_attivita = v.id 
where 1=1 
and ((_riferimentoId>0 and v.alt_id = _riferimentoId and v.enabled) or (_riferimentoId=-1))
and ((_idcu>0 and (lcu.id_controllo_ufficiale=_idcu and lcu.trashed_date is null)) or (_idcu=-1))

LOOP
RETURN NEXT;
END LOOP;
END IF;


IF (_riferimentoIdNomeTab = 'suap_ric_scia_stabilimento') THEN
FOR 
id , riferimento_id , id_rel_ateco_attivita , id_attivita_masterlist , mappato, primario, entered, entered_by, modified, modified_by, 
trashed_date, macroarea , aggregazione , attivita , codice_linea, categoria , linea_attivita , codice_istat , descrizione_codice_istat, id_attivita, enabled
in

select  distinct
v.id , v.alt_id , v.id_rel_ateco_attivita , -1 , null, v.primario, null, -1, null, -1, null, v.macroarea , null , null , v.codice,
v.categoria , v.linea_attivita , v.codice_istat , null, -1, v.enabled
from suap_ric_scia_linee_attivita_stabilimenti_view v
left join linee_attivita_controlli_ufficiali lcu on lcu.id_linea_attivita = v.id 
where 1=1 
and ((_riferimentoId>0 and v.alt_id = _riferimentoId and v.enabled) or (_riferimentoId=-1))
and ((_idcu>0 and (lcu.id_controllo_ufficiale=_idcu and lcu.trashed_date is null)) or (_idcu=-1))


LOOP
RETURN NEXT;
END LOOP;
END IF;


IF (_riferimentoIdNomeTab = 'apicoltura_imprese') THEN
FOR 
id , riferimento_id , id_rel_ateco_attivita , id_attivita_masterlist , mappato, primario, entered, entered_by, modified, modified_by, 
trashed_date, macroarea , aggregazione , attivita ,codice_linea, categoria , linea_attivita , codice_istat , descrizione_codice_istat, id_attivita, enabled
in

select  distinct
v.id_linea , v.riferimento_id , -1 , -1 , null, false, null, -1, null, -1, null, v.macroarea , null , null , 
concat_ws('-',v.codice_macroarea,v.codice_aggregazione, v.codice_attivita), v.aggregazione as categoria , v.attivita , -1 , null, -1, true
from  ricerche_anagrafiche_old_materializzata  v
join ticket t on (t.id_apiario = v.riferimento_id and v.riferimento_id_nome_tab = 'apicoltura_imprese')
where 1=1
and ((_riferimentoId>0 and v.riferimento_id = _riferimentoId) or (_riferimentoId=-1))
and ((_idcu>0 and t.ticketid=_idcu and t.trashed_date is null) or (_idcu=-1))


LOOP
RETURN NEXT;
END LOOP;
END IF;

/*
IF (_riferimentoIdNomeTab = 'anagrafica.stabilimenti') THEN
FOR 
id , riferimento_id , id_rel_ateco_attivita , id_attivita_masterlist , mappato, primario, entered, entered_by, modified, modified_by, trashed_date, 
macroarea , aggregazione , attivita , codice_linea, categoria , linea_attivita , codice_istat , descrizione_codice_istat, id_attivita, enabled
in
select  distinct
v.id , v.alt_id , -1 , -1 , null, false, null, -1, null, -1, null, v.macroarea , null , null , 'OPR-OPR-X', v.categoria , v.linea_attivita , -1 , null, -1, true
from  anagrafica.linee_attivita_stabilimenti_view  v
left join linee_attivita_controlli_ufficiali lcu on lcu.id_linea_attivita = v.id 
where 1=1 
and ((_riferimentoId>0 and v.alt_id = _riferimentoId) or (_riferimentoId=-1))
and ((_idcu>0 and lcu.id_controllo_ufficiale=_idcu and lcu.trashed_date is null ) or (_idcu=-1))
*/
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_linee_attivita(integer, text, boolean, integer)
  OWNER TO postgres;



CREATE OR REPLACE FUNCTION public.get_checklist_by_idcontrollo(IN _id_controllo integer)
  RETURNS SETOF lookup_org_catrischio AS
$BODY$
DECLARE
	_riferimento_id_nome_tab text;
	_id_linea_ml integer;
	conta integer;

BEGIN

	conta := (select count(*) from linee_attivita_controlli_ufficiali  where id_controllo_ufficiale = _id_controllo and trashed_date is null);   
	if conta > 0 then
	        _riferimento_id_nome_tab := (select riferimento_nome_tab from linee_attivita_controlli_ufficiali  where id_controllo_ufficiale = _id_controllo and trashed_date is null);
	        if _riferimento_id_nome_tab ilike '%opu_%' then 
			_id_linea_ml := (select id_attivita from get_linee_attivita(-1,'opu_stabilimento', false, _id_controllo));
		elsif _riferimento_id_nome_tab ilike '%sintesis_%' then
			_id_linea_ml := (select id_attivita from get_linee_attivita(-1,'sintesis_stabilimento', false, _id_controllo));
		else -- si considerano solo i casi organization (pratiche suap e apicoltura al momento non prevedono la gestione)
			_id_linea_ml := (select id_attivita from get_linee_attivita(-1,'organization', false, _id_controllo));
		end if;
		
		RETURN QUERY
		select c.*
		from lookup_org_catrischio c
		left join rel_checklist_sorv_ml rel on rel.id_checklist = c.code
		where c.enabled and rel.enabled and rel.id_linea = _id_linea_ml; 
 	
	end if;
	
   END;
   $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_checklist_by_idcontrollo(integer)
  OWNER TO postgres;

-- select * from public.get_checklist_by_idcontrollo(1621420)


CREATE OR REPLACE FUNCTION public.aggiorna_categoria_rischio_quantitativa(IN _idcu integer)
  RETURNS boolean AS
$BODY$
DECLARE  
	categoria_rischio_quantitativa integer;
	punteggio integer;
BEGIN 
	punteggio := (SELECT SUM (livello_rischio)::integer as punteggio from audit where id_controllo::text = _idcu::text and trashed_date is null);  
	-- nel momento in cui sarà definito il range dei punteggi, vanno modificati i valori in parametri_categorizzazzione_osa e la query
	categoria_rischio_quantitativa := (select categoria_rischio from parametri_categorizzazzione_osa where tipo_operatore = 1 and (punteggio between range_da and range_a or (punteggio > range_da and range_a is null))); 
	-- aggiorno categoria di rischio per singola linea
	update linee_attivita_controlli_ufficiali set note_internal_use_only=concat_ws('***',note_internal_use_only,'Aggiornata la categoria di rischio '||categoria_rischio_quantitativa||' in data '||current_timestamp||''), 
	categoria_quantitativa = categoria_rischio_quantitativa where id_controllo_ufficiale = _idcu and trashed_date is null;
	update ticket set isaggiornata_categoria = true, note_internal_use_only=concat_ws('***',note_internal_use_only,'Aggiornato flag per categoria di rischio') where ticketid = _idcu;

	return true;

END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.aggiorna_categoria_rischio_quantitativa(integer)
  OWNER TO postgres;

/*
CREATE OR REPLACE FUNCTION public.get_categoria_rischio(
    IN _riferimento_id integer,
    IN _riferimento_id_nome_tab text,
    IN _codicelineaattivita text DEFAULT NULL::text)
  RETURNS TABLE(categorizzabile text, tipo_categorizzazione text, id_ultimo_controllo_categorizzazione integer, categoria_quantitativa integer, categoria_qualitativa text) AS
$BODY$
DECLARE
r RECORD;	
catExAnte integer;
isCategorizzabile boolean;
idLineaSoa integer;
idUltimoControllo integer;
catQuantitativa integer;
catQualitativa text;
tipoCategorizzazione text;

BEGIN

catExAnte := -1;
isCategorizzabile := false;
idLineaSoa := -1;
idUltimoControllo := -1;
catQuantitativa := -1;
catQualitativa := '';

-- Recupero la massima categoria ex ante per tutte le linee su questa anagrafica
select max(id) into catExAnte from master_list_flag_linee_attivita where codice_univoco  in (select codice from ml8_linee_attivita_nuove_materializzata where codice in (select concat_ws('-', codice_macroarea, codice_aggregazione, codice_attivita) 
from ricerche_anagrafiche_old_materializzata where riferimento_id = _riferimento_id and riferimento_id_nome_tab = _riferimento_id_nome_tab)) and categorizzabili;

--Gestire lo scenario della categoria di rischio per linea di attivita' in input.....
......
......
......--
IF catExAnte > 0 THEN
catExAnte := 3;
END IF;

raise info '[GET CATEGORIA RISCHIO] MAX EX ANTE: %', catExAnte;

-- Recupero se presente una linea SOA su questa anagrafica
select id_attivita into idLineaSoa from 
ricerche_anagrafiche_old_materializzata
where riferimento_id = _riferimento_id and riferimento_id_nome_tab = _riferimento_id_nome_tab
and norma ilike '%1069%' limit 1;

raise info '[GET CATEGORIA RISCHIO] ID LINEA SOA: %', idLineaSoa;

-- Se presente almeno una linea con la categoria ex ante, questa anagrafica è categorizzabile
IF catExAnte > 0 THEN
isCategorizzabile = true;
END IF;

-- Recupero i dati sull'ultimo controllo in sorveglianza
IF isCategorizzabile THEN

IF _riferimento_id_nome_tab = 'organization' THEN

select ticketid into idUltimoControllo from ticket where org_id = _riferimento_id and tipologia = 3 and provvedimenti_prescrittivi = 5 and trashed_date is null and closed is not null order by assigned_date desc limit 1;

END IF;

IF _riferimento_id_nome_tab = 'opu_stabilimento' THEN

select ticketid into idUltimoControllo from ticket where id_stabilimento = _riferimento_id and tipologia = 3 and provvedimenti_prescrittivi = 5 and trashed_date is null and closed is not null order by assigned_date desc limit 1;

END IF;

IF _riferimento_id_nome_tab = 'sintesis_stabilimento' THEN

select ticketid into idUltimoControllo from ticket where alt_id = _riferimento_id and tipologia = 3 and provvedimenti_prescrittivi = 5 and trashed_date is null and closed is not null order by assigned_date desc limit 1;

END IF;


raise info '[GET CATEGORIA RISCHIO] ID ULTIMO CONTROLLO: %', idUltimoControllo;


-- Recupero i dati sulla categoria
IF idUltimoControllo > 0 THEN
IF idLineaSoa > 0 THEN
select description into catQualitativa from lookup_categoriarischio_soa where code in (select livello_rischio from ticket where ticketid = idUltimoControllo);
ELSE
select categoria_quantitativa into catQuantitativa from linee_attivita_controlli_ufficiali where id_controllo_ufficiale = idUltimoControllo;
END IF;
END IF;

END IF;

-- Gestisco ex ante 
IF idLineaSoa IS NOT NULL and idUltimoControllo IS NULL THEN
raise info '[GET CATEGORIA RISCHIO] RECUPERO EX ANTE';

select description into catQualitativa from lookup_categoriarischio_soa where code = 92;
select null into catQuantitativa;
END IF;

IF idLineaSoa IS NULL and idUltimoControllo IS NULL THEN
raise info '[GET CATEGORIA RISCHIO] RECUPERO EX ANTE';
select null into catQualitativa ;
select catExAnte into catQuantitativa;
END IF;

raise info '[GET CATEGORIA RISCHIO] CAT QUALITATIVA: %', catQualitativa;
raise info '[GET CATEGORIA RISCHIO] CAT QUANTITATIVA: %', catQuantitativa;

FOR 

categorizzabile, tipo_categorizzazione, id_ultimo_controllo_categorizzazione, categoria_quantitativa, categoria_qualitativa 

in

select
case when isCategorizzabile then 'CATEGORIZZABILE' else 'NON CATEGORIZZABILE' end,
case when isCategorizzabile then case when idUltimoControllo > 0 then 'CATEGORIZZATO DA CU' else 'EX ANTE' end else '' end,
idUltimoControllo,
catQuantitativa,
catQualitativa


    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_categoria_rischio(integer, text, text)
  OWNER TO postgres;
  -----------------------------------------------------------
*/
--select * from get_categoria_rischio(251586,'opu_stabilimento','') 


-- MODIFICARE IL NOME PER EVITARE CHE SI CONFONDA RISPETTO A QUELLA DI BARTOLO GENERICA PER TT GLI OSA

CREATE OR REPLACE FUNCTION public.get_categoria_rischio_(IN _riferimento_id integer, _riferimento_id_nome_tab text)
  RETURNS TABLE(categoria_linea integer) AS
$BODY$
DECLARE
   rec integer;
   convert_rif_nome_tab text;
BEGIN

    if _riferimento_id_nome_tab = 'opu_stabilimento' then
	convert_rif_nome_tab = 'opu_relazione_stabilimento_linee_produttive';
    elsif _riferimento_id_nome_tab = 'sintesis_stabilimento' then
	convert_rif_nome_tab = 'sintesis_relazione_stabilimento_linee_produttive';
    else
	convert_rif_nome_tab='organization';
    end if;
		
     FOR rec IN
        select id_linea from ricerche_anagrafiche_old_materializzata  where riferimento_id  = _riferimento_id and riferimento_id_nome_tab = _riferimento_id_nome_tab
     LOOP
        RETURN QUERY
        with cte as (SELECT    
                 -- categoria di rischio da recuperare sia da cu che da EX ANTE
		 categoria_quantitativa --(select categoria_rischio_default from master_list_flag_linee_attivita  where trashed is null and id_linea = (	
					-- select id_linea_produttiva from opu_relazione_stabilimento_linee_produttive  where id = rec)) 
		 from linee_attivita_controlli_ufficiali l
		 join ticket t on t.ticketid = l.id_controllo_ufficiale and l.trashed_date is null and t.trashed_date is null  
		 where id_linea_attivita = rec and t.provvedimenti_prescrittivi = 5 and t.tipologia = 3 --and t.closed is not null 
		 and riferimento_nome_tab  = convert_rif_nome_tab
		 order by t.assigned_date desc limit 1 -- controllo se è opu
		)
	  select 
		  categoria_quantitativa 
	 from cte; --rec in input dovrebbe avere la singola linea
     END LOOP;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_categoria_rischio_(integer, text)
  OWNER TO postgres;

select * from public.get_categoria_rischio_(251586,'opu_stabilimento')


-- Function: public.get_punteggio_non_conformita(integer, timestamp without time zone)
-- DROP FUNCTION public.get_punteggio_non_conformita(integer, timestamp without time zone);

CREATE OR REPLACE FUNCTION public.get_categoria_rischio_media(IN _riferimento_id integer, _riferimento_id_nome_tab text)
  RETURNS double precision AS
$BODY$
DECLARE
	conta_linee integer;
	categoria_media double precision;
BEGIN
        conta_linee := (select count(id_linea) from ricerche_anagrafiche_old_materializzata  where riferimento_id  = _riferimento_id and riferimento_id_nome_tab = _riferimento_id_nome_tab);
        select sum(categoria_linea *1.0)/conta_linee into categoria_media from public.get_categoria_rischio_(_riferimento_id,_riferimento_id_nome_tab);

    
     RETURN categoria_media;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_categoria_rischio_media(integer, text)
  OWNER TO postgres;

select * from public.get_categoria_rischio_media(251586,'opu_stabilimento')


CREATE OR REPLACE FUNCTION public.get_categoria_rischio_qualitativa(IN _riferimento_id integer, _riferimento_id_nome_tab text)
  RETURNS text AS
$BODY$
DECLARE
	res double precision;
	categoria text;
BEGIN

        res :=  (select * from public.get_categoria_rischio_media(_riferimento_id,_riferimento_id_nome_tab));
        if res >= 0 AND res <= 3.5 then 
		categoria = 'RISCHIO BASSO';
        elsif res > 3.5 and res <= 4.4 then
		categoria = 'RISCHIO MEDIO';
	else 
		categoria = 'RISCHIO ALTO';
	END IF;

	return categoria;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_categoria_rischio_qualitativa(integer, text)
  OWNER TO postgres;

select * from public.get_categoria_rischio_qualitativa(251586,'opu_stabilimento')


/*
with cte_ext as (

	with cte_int as	(
		 select t.assigned_date, t.ticketid as id_controllo,
			t.categoria_rischio, (select description from lookup_categoria_rischio where code = t.livello_rischio) as livello_rischio, t.data_prossimo_controllo,
			case 	when t.alt_id > 0 then t.alt_id
				when t.id_stabilimento > 0 then t.id_stabilimento
				when t.id_apiario > 0 then t.id_apiario
				when t.org_id > 0 then t.org_id end as riferimento_id ,
		      case 	when t.alt_id > 0 then (select return_nome_tabella from gestione_id_alternativo(t.alt_id,-1))
				when t.id_stabilimento > 0 then 'opu_stabilimento'
				when t.id_apiario > 0 then 'apicoltura_imprese'
				when t.org_id > 0 then 'organization' end as
			riferimento_nome_tab
		 from ticket t
		 where t.tipologia=3 and trashed_date is null and t.provvedimenti_prescrittivi=5 and t.closed is not null
		 group by ticketid , riferimento_id, riferimento_nome_tab
		 order by riferimento_id, riferimento_nome_tab, assigned_date desc
	)
	      select distinct on (riferimento_id, riferimento_nome_tab)
	        id_controllo,
	        riferimento_id,
	        riferimento_nome_tab,
	        data_prossimo_controllo,
	        assigned_date,
		categoria_rischio,
		livello_rischio,
		id_controllo as id_controllo_ultima_categorizzazione,
		assigned_date as data_controllo_ultima_categorizzazione,
		case when id_controllo is null then 'EX ANTE' ELSE 'CATEGORIZZATO DA CU' end as tipo_categorizzazione
		from cte_int
	) 
    select distinct -- aggiunta della distinct 
	v.riferimento_id ,
	v.riferimento_id_nome_tab ,
	v.ragione_sociale,
	v.asl_rif ,
	v.asl ,
	v.codice_fiscale::text,
	v.codice_fiscale_rappresentante ,
	v.partita_iva::text,
	v.n_reg,
	v.nominativo_rappresentante ,
	v.comune,
	v.provincia_stab,
	v.indirizzo,
	v.cap_stab,
	v.comune_leg,
	v.provincia_leg ,
	v.indirizzo_leg,
	v.cap_leg,
	v.latitudine_stab ,
	v.longitudine_stab ,
	case when l.id_controllo is null then coalesce(l.categoria_rischio,v.categoria_rischio) else l.categoria_rischio end as categoria_rischio, -- integrazione per gestione categoria di rischio EX ANTE
	l.data_prossimo_controllo as prossimo_controllo,
	l.id_controllo as id_controllo_ultima_categorizzazione,
	l.assigned_date as data_controllo_ultima_categorizzazione,
        case when l.id_controllo is null then 'EX ANTE' ELSE 'CATEGORIZZATO DA CU' end as tipo_categorizzazione,
        v.data_inserimento,
       l.livello_rischio

    from ricerche_anagrafiche_old_materializzata v 
        left join cte_ext l on l.riferimento_id  = v.riferimento_id and l.riferimento_nome_tab = v.riferimento_id_nome_tab
    where v.data_inserimento between data_1 and data_2 
    */

-- DETTAGLIO ANAGRAFICA 
-- Function: public.get_valori_anagrafica_extra(integer)

-- DROP FUNCTION public.get_valori_anagrafica_extra_142(integer);

-- Function: public.get_valori_anagrafica_extra(integer)

-- DROP FUNCTION public.get_valori_anagrafica_extra_142(integer);

CREATE OR REPLACE FUNCTION public.get_valori_anagrafica_extra_142(IN _altid integer DEFAULT '-1'::integer)
  RETURNS TABLE(alt_id integer, inserito_da text, inserito_il text, categoria_di_rischio text,  data_inizio text, stato text, tipo_attivita text, note text, categorizzabili text, note_impresa text) AS
$BODY$
DECLARE
	r RECORD;
	descrizioneStato text;
	idLineaAttiva integer;
	flagCategorizzabili boolean;
	id_stato_stab integer;	
	_id_stabilimento integer;
	_cat_di_rischio integer;
	-- new per flusso 142
	_cat_di_rischio_qual text;
	categoria_di_rischio_qual text;
	categoria_default text;
BEGIN

	select get_stato_stabilimento into id_stato_stab from get_stato_stabilimento(_altid);

	IF id_stato_stab = 0 THEN
		descrizioneStato := 'Attivo';
	ELSIF id_stato_stab = 2 THEN
		descrizioneStato := 'Sospeso';
	ELSE 
		descrizioneStato := 'Cessato';
	END IF;

	select id into _id_stabilimento from opu_stabilimento o where o.alt_id = _altid; 

	update opu_stabilimento 
	set data_inizio_attivita = (select min(orels.data_inizio) from opu_relazione_stabilimento_linee_produttive orels where orels.id_stabilimento = _id_stabilimento and orels.enabled) 
	where id = _id_stabilimento;    

	select flag.categorizzabili into flagCategorizzabili 
		from opu_relazione_stabilimento_linee_produttive oprel join ml8_linee_attivita_nuove_materializzata ml8 on oprel.id_linea_produttiva  = ml8.id_nuova_linea_attivita
			join master_list_flag_linee_attivita flag on ml8.id_nuova_linea_attivita = flag.id_linea
		where  oprel.enabled and  oprel.id_stabilimento = (select os.id from opu_stabilimento os where os.alt_id = _altid) 
		and flag.categorizzabili 
		order by flag.categorizzabili desc;

	_cat_di_rischio_qual := (select * from get_categoria_rischio_qualitativa(_id_stabilimento,'opu_stabilimento'));

	-- diventa quella di default come label
	select coalesce(max(categoria_rischio_default))::integer into _cat_di_rischio 
		from opu_relazione_stabilimento_linee_produttive rel 
		join ml8_linee_attivita_nuove_materializzata ml8 on ml8.id_nuova_linea_attivita = rel.id_linea_produttiva 
		left join master_list_flag_linee_attivita flag on flag.id_linea = ml8.id_nuova_linea_attivita
		where flag.categorizzabili and rel.enabled and rel.id_stabilimento = _id_stabilimento;
	--	update opu_stabilimento set categoria_rischio = _cat_di_rischio where id = _id_stabilimento;
	--	perform refresh_anagrafica(_id_stabilimento, 'opu');
	--END IF;
		
	raise info ' CAT DI RISCHIO QUAL%', _cat_di_rischio_qual;
	raise info ' CAT DI RISCHIO DEFAULT%', concat_ws('-',flagCategorizzabili::text, _cat_di_rischio);

	RETURN QUERY 
		select 
		s.alt_id,
		CASE WHEN acc.user_id > 0 THEN 
			concat_ws(' ', con.namefirst, con.namelast) 
		ELSE concat_ws(' ', conext.namefirst, conext.namelast) 
		END as inserito_da,
		to_char(s.entered, 'dd/MM/yyyy') as inserito_il,
		_cat_di_rischio_qual::text AS categoria_di_rischio_qual,
		to_char(s.data_inizio_attivita, 'dd/MM/yyyy') as data_inizio,
		descrizioneStato as stato,
		lti.description::text as tipo_attivita,
		CASE WHEN (length(trim(s.note)) <> 0) THEN trim( regexp_replace(s.note, '\r|\n', ' ', 'g'))::text ELSE ''::text END as note,
		concat_ws(' ',(case when flagCategorizzabili then 'CATEGORIZZABILE CON CATEGORIA DI DEFAULT' ELSE 'NON CATEGORIZZABILE' end), _cat_di_rischio)::text as categoria_default,
		CASE WHEN (length(trim(o.note)) <> 0) THEN trim( regexp_replace(o.note, '\r|\n', ' ', 'g'))::text ELSE ''::text END as note_impresa
		from opu_stabilimento s
		join opu_operatore o on s.id_operatore = o.id
		left join access_ acc on acc.user_id = s.entered_by
		left join contact_ con on con.contact_id = acc.contact_id
		left join access_ext_ accext on accext.user_id = s.entered_by
		left join contact_ext_ conext on conext.contact_id = accext.contact_id
		left join lookup_tipo_attivita lti on lti.code = s.tipo_attivita

		where 1 = 1 and ( _altid = -1 or s.alt_id = _altid ); -- gestione valori opzionali

	--LOOP
	--	RETURN NEXT;
	--    END LOOP;
	     --RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_valori_anagrafica_extra_142(integer)
  OWNER TO postgres;

 -- test select * from get_valori_anagrafica_extra_142(20183564)
 
  -- Function: public.get_categoria_rischio_quantitativa(integer, text, integer)

-- DROP FUNCTION public.get_categoria_rischio_quantitativa(integer, text, integer);

CREATE OR REPLACE FUNCTION public.get_categoria_rischio_quantitativa(
    IN _riferimento_id integer DEFAULT (- 1),
    IN _riferimento_id_nome_tab text DEFAULT ''::text,
    IN _idcu integer DEFAULT (- 1))
  RETURNS TABLE(categoria_linea integer) AS
$BODY$
DECLARE
   rec integer;
   convert_rif_nome_tab text;
BEGIN

    if _riferimento_id_nome_tab = 'opu_stabilimento' then
	convert_rif_nome_tab = 'opu_relazione_stabilimento_linee_produttive';
    elsif _riferimento_id_nome_tab = 'sintesis_stabilimento' then
	convert_rif_nome_tab = 'sintesis_relazione_stabilimento_linee_produttive';
    else
	convert_rif_nome_tab='organization';
    end if;

    if _idcu > 0 then
	return query 
		select categoria_quantitativa from linee_attivita_controlli_ufficiali where trashed_date is null and id_controllo_ufficiale = _idcu;
    else 
	  FOR rec IN
        select id_linea from ricerche_anagrafiche_old_materializzata  where riferimento_id  = _riferimento_id and riferimento_id_nome_tab = _riferimento_id_nome_tab
     LOOP
        RETURN QUERY
        with cte as (SELECT    
                 -- categoria di rischio da recuperare sia da cu che da EX ANTE
		 categoria_quantitativa --(select categoria_rischio_default from master_list_flag_linee_attivita  where trashed is null and id_linea = (	
					-- select id_linea_produttiva from opu_relazione_stabilimento_linee_produttive  where id = rec)) 
		 from linee_attivita_controlli_ufficiali l
		 join ticket t on t.ticketid = l.id_controllo_ufficiale and l.trashed_date is null and t.trashed_date is null  
		 where id_linea_attivita = rec and t.provvedimenti_prescrittivi = 5 and t.tipologia = 3 --and t.closed is not null 
		 and riferimento_nome_tab  = convert_rif_nome_tab
		 order by t.assigned_date desc limit 1 -- controllo se � opu
		)
	  select 
		  categoria_quantitativa 
	 from cte; --rec in input dovrebbe avere la singola linea
     END LOOP;

    end if;
		
   
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_categoria_rischio_quantitativa(integer, text, integer)
  OWNER TO postgres;

/*script sorveglianza ????*/
  alter table linee_attivita_controlli_ufficiali  add column categoria_quantitativa integer; -- potrebbe essere double

-- Function: public.get_linee_attivita(integer, text, boolean, integer)

-- DROP FUNCTION public.get_linee_attivita(integer, text, boolean, integer);

CREATE OR REPLACE FUNCTION public.get_linee_attivita(
    IN _riferimentoid integer,
    IN _riferimentoidnometab text,
    IN _primario boolean,
    IN _idcu integer)
  RETURNS TABLE(id integer, riferimento_id integer, id_rel_ateco_attivita integer, id_attivita_masterlist integer, mappato boolean, primario boolean, entered timestamp without time zone, entered_by integer, modified timestamp without time zone, modified_by integer, trashed_date timestamp without time zone, macroarea text, aggregazione text, attivita text, codice_linea text, categoria text, linea_attivita text, codice_istat text, descrizione_codice_istat text, id_attivita integer, enabled boolean) AS
$BODY$
DECLARE
r RECORD;	

BEGIN


IF (_riferimentoIdNomeTab = 'organization') THEN
FOR 
id , riferimento_id , id_rel_ateco_attivita , id_attivita_masterlist , mappato, primario, entered, entered_by, modified, modified_by, 
trashed_date, macroarea , aggregazione , attivita , codice_linea, categoria , linea_attivita , codice_istat , descrizione_codice_istat, id_attivita, enabled
in

select distinct
v.id , v.org_id , v.id_rel_ateco_attivita , v.id_attivita_masterlist , v.mappato, v.primario, v.entered, v.entered_by, v.modified, v.modified_by, 
v.trashed_date, v.macroarea , v.aggregazione , v.attivita , v.codice, v.categoria , coalesce(v.linea_attivita, v.attivita) as linea_attivita , v.codice_istat , v.descrizione_codice_istat, -1, true 
from org_linee_attivita_view v
left join linee_attivita_controlli_ufficiali lcu on v.id=lcu.id_linea_attivita
--left join ticket t on t.org_id = v.org_id
where 1=1 
and ((_riferimentoId>0 and v.org_id = _riferimentoId) or (_riferimentoId=-1))
and ((_idcu>0 and (lcu.id_controllo_ufficiale=_idcu and lcu.trashed_date is null)) or (_idcu=-1))

LOOP
RETURN NEXT;
END LOOP;
END IF;

IF (_riferimentoIdNomeTab = 'opu_stabilimento' or _riferimentoIdNomeTab = 'opu_relazione_stabilimento_linee_produttive') THEN
FOR 
id , riferimento_id , id_rel_ateco_attivita , id_attivita_masterlist , mappato, primario, entered, entered_by, modified, modified_by, trashed_date, macroarea , aggregazione , 
attivita , codice_linea, categoria , linea_attivita , codice_istat , descrizione_codice_istat, id_attivita, enabled
in

select  distinct
v.id , v.org_id , v.id_rel_ateco_attivita , -1 , null, v.primario, null, -1, null, -1, null, v.macroarea , null , null , v.codice, v.categoria , v.linea_attivita , v.codice_istat , null, v.id_attivita, v.enabled
from opu_linee_attivita_stabilimenti_view v
left join linee_attivita_controlli_ufficiali lcu on lcu.id_linea_attivita = v.id 
where 1=1 
and ((_riferimentoId>0 and v.org_id = _riferimentoId and v.enabled) or (_riferimentoId=-1))
and ((_idcu>0 and (lcu.id_controllo_ufficiale=_idcu and lcu.trashed_date is null)) or (_idcu=-1))

LOOP
RETURN NEXT;
END LOOP;
END IF;

IF (_riferimentoIdNomeTab = 'sintesis_stabilimento' or _riferimentoIdNomeTab = 'sintesis_relazione_stabilimento_linee_produttive') THEN
FOR 
id , riferimento_id , id_rel_ateco_attivita , id_attivita_masterlist , mappato, primario, entered, entered_by, modified, modified_by, 
trashed_date, macroarea , aggregazione , attivita , codice_linea, categoria , linea_attivita , codice_istat , descrizione_codice_istat, id_attivita, enabled
in

select  distinct
v.id , v.alt_id , v.id_rel_ateco_attivita , -1 , null, v.primario, null, -1, null, -1, null, v.macroarea , null , null , 
v.codice, v.categoria , v.linea_attivita , v.codice_istat , null, -1, v.enabled
from sintesis_linee_attivita_stabilimenti_view v
left join linee_attivita_controlli_ufficiali lcu on lcu.id_linea_attivita = v.id 
where 1=1 
and ((_riferimentoId>0 and v.alt_id = _riferimentoId and v.enabled) or (_riferimentoId=-1))
and ((_idcu>0 and (lcu.id_controllo_ufficiale=_idcu and lcu.trashed_date is null)) or (_idcu=-1))

LOOP
RETURN NEXT;
END LOOP;
END IF;


IF (_riferimentoIdNomeTab = 'suap_ric_scia_stabilimento') THEN
FOR 
id , riferimento_id , id_rel_ateco_attivita , id_attivita_masterlist , mappato, primario, entered, entered_by, modified, modified_by, 
trashed_date, macroarea , aggregazione , attivita , codice_linea, categoria , linea_attivita , codice_istat , descrizione_codice_istat, id_attivita, enabled
in

select  distinct
v.id , v.alt_id , v.id_rel_ateco_attivita , -1 , null, v.primario, null, -1, null, -1, null, v.macroarea , null , null , v.codice,
v.categoria , v.linea_attivita , v.codice_istat , null, -1, v.enabled
from suap_ric_scia_linee_attivita_stabilimenti_view v
left join linee_attivita_controlli_ufficiali lcu on lcu.id_linea_attivita = v.id 
where 1=1 
and ((_riferimentoId>0 and v.alt_id = _riferimentoId and v.enabled) or (_riferimentoId=-1))
and ((_idcu>0 and (lcu.id_controllo_ufficiale=_idcu and lcu.trashed_date is null)) or (_idcu=-1))


LOOP
RETURN NEXT;
END LOOP;
END IF;


IF (_riferimentoIdNomeTab = 'apicoltura_imprese') THEN
FOR 
id , riferimento_id , id_rel_ateco_attivita , id_attivita_masterlist , mappato, primario, entered, entered_by, modified, modified_by, 
trashed_date, macroarea , aggregazione , attivita ,codice_linea, categoria , linea_attivita , codice_istat , descrizione_codice_istat, id_attivita, enabled
in

select  distinct
v.id_linea , v.riferimento_id , -1 , -1 , null, false, null, -1, null, -1, null, v.macroarea , null , null , 
concat_ws('-',v.codice_macroarea,v.codice_aggregazione, v.codice_attivita), v.aggregazione as categoria , v.attivita , -1 , null, -1, true
from  ricerche_anagrafiche_old_materializzata  v
join ticket t on (t.id_apiario = v.riferimento_id and v.riferimento_id_nome_tab = 'apicoltura_imprese')
where 1=1
and ((_riferimentoId>0 and v.riferimento_id = _riferimentoId) or (_riferimentoId=-1))
and ((_idcu>0 and t.ticketid=_idcu and t.trashed_date is null) or (_idcu=-1))


LOOP
RETURN NEXT;
END LOOP;
END IF;

/*
IF (_riferimentoIdNomeTab = 'anagrafica.stabilimenti') THEN
FOR 
id , riferimento_id , id_rel_ateco_attivita , id_attivita_masterlist , mappato, primario, entered, entered_by, modified, modified_by, trashed_date, 
macroarea , aggregazione , attivita , codice_linea, categoria , linea_attivita , codice_istat , descrizione_codice_istat, id_attivita, enabled
in
select  distinct
v.id , v.alt_id , -1 , -1 , null, false, null, -1, null, -1, null, v.macroarea , null , null , 'OPR-OPR-X', v.categoria , v.linea_attivita , -1 , null, -1, true
from  anagrafica.linee_attivita_stabilimenti_view  v
left join linee_attivita_controlli_ufficiali lcu on lcu.id_linea_attivita = v.id 
where 1=1 
and ((_riferimentoId>0 and v.alt_id = _riferimentoId) or (_riferimentoId=-1))
and ((_idcu>0 and lcu.id_controllo_ufficiale=_idcu and lcu.trashed_date is null ) or (_idcu=-1))
*/
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_linee_attivita(integer, text, boolean, integer)
  OWNER TO postgres;



CREATE OR REPLACE FUNCTION public.get_checklist_by_idcontrollo(IN _id_controllo integer)
  RETURNS SETOF lookup_org_catrischio AS
$BODY$
DECLARE
	_riferimento_id_nome_tab text;
	_id_linea_ml integer;
	conta integer;

BEGIN

	conta := (select count(*) from linee_attivita_controlli_ufficiali  where id_controllo_ufficiale = _id_controllo and trashed_date is null);   
	if conta > 0 then
	        _riferimento_id_nome_tab := (select riferimento_nome_tab from linee_attivita_controlli_ufficiali  where id_controllo_ufficiale = _id_controllo and trashed_date is null);
	        if _riferimento_id_nome_tab ilike '%opu_%' then 
			_id_linea_ml := (select id_attivita from get_linee_attivita(-1,'opu_stabilimento', false, _id_controllo));
		elsif _riferimento_id_nome_tab ilike '%sintesis_%' then
			_id_linea_ml := (select id_attivita from get_linee_attivita(-1,'sintesis_stabilimento', false, _id_controllo));
		else -- si considerano solo i casi organization (pratiche suap e apicoltura al momento non prevedono la gestione)
			_id_linea_ml := (select id_attivita from get_linee_attivita(-1,'organization', false, _id_controllo));
		end if;
		
		RETURN QUERY
		select c.*
		from lookup_org_catrischio c
		left join rel_checklist_sorv_ml rel on rel.id_checklist = c.code
		where c.enabled and rel.enabled and rel.id_linea = _id_linea_ml; 
 	
	end if;
	
   END;
   $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_checklist_by_idcontrollo(integer)
  OWNER TO postgres;

-- select * from public.get_checklist_by_idcontrollo(1621420)


CREATE OR REPLACE FUNCTION public.aggiorna_categoria_rischio_quantitativa(IN _idcu integer)
  RETURNS boolean AS
$BODY$
DECLARE  
	categoria_rischio_quantitativa integer;
	punteggio integer;
BEGIN 
	punteggio := (SELECT SUM (livello_rischio)::integer as punteggio from audit where id_controllo::text = _idcu::text and trashed_date is null);  
	-- nel momento in cui sarà definito il range dei punteggi, vanno modificati i valori in parametri_categorizzazzione_osa e la query
	categoria_rischio_quantitativa := (select categoria_rischio from parametri_categorizzazzione_osa where tipo_operatore = 1 and (punteggio between range_da and range_a or (punteggio > range_da and range_a is null))); 
	-- aggiorno categoria di rischio per singola linea
	update linee_attivita_controlli_ufficiali set note_internal_use_only=concat_ws('***',note_internal_use_only,'Aggiornata la categoria di rischio '||categoria_rischio_quantitativa||' in data '||current_timestamp||''), 
	categoria_quantitativa = categoria_rischio_quantitativa where id_controllo_ufficiale = _idcu and trashed_date is null;
	update ticket set isaggiornata_categoria = true, note_internal_use_only=concat_ws('***',note_internal_use_only,'Aggiornato flag per categoria di rischio') where ticketid = _idcu;

	return true;

END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.aggiorna_categoria_rischio_quantitativa(integer)
  OWNER TO postgres;

/*
CREATE OR REPLACE FUNCTION public.get_categoria_rischio(
    IN _riferimento_id integer,
    IN _riferimento_id_nome_tab text,
    IN _codicelineaattivita text DEFAULT NULL::text)
  RETURNS TABLE(categorizzabile text, tipo_categorizzazione text, id_ultimo_controllo_categorizzazione integer, categoria_quantitativa integer, categoria_qualitativa text) AS
$BODY$
DECLARE
r RECORD;	
catExAnte integer;
isCategorizzabile boolean;
idLineaSoa integer;
idUltimoControllo integer;
catQuantitativa integer;
catQualitativa text;
tipoCategorizzazione text;

BEGIN

catExAnte := -1;
isCategorizzabile := false;
idLineaSoa := -1;
idUltimoControllo := -1;
catQuantitativa := -1;
catQualitativa := '';

-- Recupero la massima categoria ex ante per tutte le linee su questa anagrafica
select max(id) into catExAnte from master_list_flag_linee_attivita where codice_univoco  in (select codice from ml8_linee_attivita_nuove_materializzata where codice in (select concat_ws('-', codice_macroarea, codice_aggregazione, codice_attivita) 
from ricerche_anagrafiche_old_materializzata where riferimento_id = _riferimento_id and riferimento_id_nome_tab = _riferimento_id_nome_tab)) and categorizzabili;

--Gestire lo scenario della categoria di rischio per linea di attivita' in input.....
......
......
......--
IF catExAnte > 0 THEN
catExAnte := 3;
END IF;

raise info '[GET CATEGORIA RISCHIO] MAX EX ANTE: %', catExAnte;

-- Recupero se presente una linea SOA su questa anagrafica
select id_attivita into idLineaSoa from 
ricerche_anagrafiche_old_materializzata
where riferimento_id = _riferimento_id and riferimento_id_nome_tab = _riferimento_id_nome_tab
and norma ilike '%1069%' limit 1;

raise info '[GET CATEGORIA RISCHIO] ID LINEA SOA: %', idLineaSoa;

-- Se presente almeno una linea con la categoria ex ante, questa anagrafica è categorizzabile
IF catExAnte > 0 THEN
isCategorizzabile = true;
END IF;

-- Recupero i dati sull'ultimo controllo in sorveglianza
IF isCategorizzabile THEN

IF _riferimento_id_nome_tab = 'organization' THEN

select ticketid into idUltimoControllo from ticket where org_id = _riferimento_id and tipologia = 3 and provvedimenti_prescrittivi = 5 and trashed_date is null and closed is not null order by assigned_date desc limit 1;

END IF;

IF _riferimento_id_nome_tab = 'opu_stabilimento' THEN

select ticketid into idUltimoControllo from ticket where id_stabilimento = _riferimento_id and tipologia = 3 and provvedimenti_prescrittivi = 5 and trashed_date is null and closed is not null order by assigned_date desc limit 1;

END IF;

IF _riferimento_id_nome_tab = 'sintesis_stabilimento' THEN

select ticketid into idUltimoControllo from ticket where alt_id = _riferimento_id and tipologia = 3 and provvedimenti_prescrittivi = 5 and trashed_date is null and closed is not null order by assigned_date desc limit 1;

END IF;


raise info '[GET CATEGORIA RISCHIO] ID ULTIMO CONTROLLO: %', idUltimoControllo;


-- Recupero i dati sulla categoria
IF idUltimoControllo > 0 THEN
IF idLineaSoa > 0 THEN
select description into catQualitativa from lookup_categoriarischio_soa where code in (select livello_rischio from ticket where ticketid = idUltimoControllo);
ELSE
select categoria_quantitativa into catQuantitativa from linee_attivita_controlli_ufficiali where id_controllo_ufficiale = idUltimoControllo;
END IF;
END IF;

END IF;

-- Gestisco ex ante 
IF idLineaSoa IS NOT NULL and idUltimoControllo IS NULL THEN
raise info '[GET CATEGORIA RISCHIO] RECUPERO EX ANTE';

select description into catQualitativa from lookup_categoriarischio_soa where code = 92;
select null into catQuantitativa;
END IF;

IF idLineaSoa IS NULL and idUltimoControllo IS NULL THEN
raise info '[GET CATEGORIA RISCHIO] RECUPERO EX ANTE';
select null into catQualitativa ;
select catExAnte into catQuantitativa;
END IF;

raise info '[GET CATEGORIA RISCHIO] CAT QUALITATIVA: %', catQualitativa;
raise info '[GET CATEGORIA RISCHIO] CAT QUANTITATIVA: %', catQuantitativa;

FOR 

categorizzabile, tipo_categorizzazione, id_ultimo_controllo_categorizzazione, categoria_quantitativa, categoria_qualitativa 

in

select
case when isCategorizzabile then 'CATEGORIZZABILE' else 'NON CATEGORIZZABILE' end,
case when isCategorizzabile then case when idUltimoControllo > 0 then 'CATEGORIZZATO DA CU' else 'EX ANTE' end else '' end,
idUltimoControllo,
catQuantitativa,
catQualitativa


    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_categoria_rischio(integer, text, text)
  OWNER TO postgres;
  -----------------------------------------------------------
*/
--select * from get_categoria_rischio(251586,'opu_stabilimento','') 


-- MODIFICARE IL NOME PER EVITARE CHE SI CONFONDA RISPETTO A QUELLA DI BARTOLO GENERICA PER TT GLI OSA

CREATE OR REPLACE FUNCTION public.get_categoria_rischio_(IN _riferimento_id integer, _riferimento_id_nome_tab text)
  RETURNS TABLE(categoria_linea integer) AS
$BODY$
DECLARE
   rec integer;
   convert_rif_nome_tab text;
BEGIN

    if _riferimento_id_nome_tab = 'opu_stabilimento' then
	convert_rif_nome_tab = 'opu_relazione_stabilimento_linee_produttive';
    elsif _riferimento_id_nome_tab = 'sintesis_stabilimento' then
	convert_rif_nome_tab = 'sintesis_relazione_stabilimento_linee_produttive';
    else
	convert_rif_nome_tab='organization';
    end if;
		
     FOR rec IN
        select id_linea from ricerche_anagrafiche_old_materializzata  where riferimento_id  = _riferimento_id and riferimento_id_nome_tab = _riferimento_id_nome_tab
     LOOP
        RETURN QUERY
        with cte as (SELECT    
                 -- categoria di rischio da recuperare sia da cu che da EX ANTE
		 categoria_quantitativa --(select categoria_rischio_default from master_list_flag_linee_attivita  where trashed is null and id_linea = (	
					-- select id_linea_produttiva from opu_relazione_stabilimento_linee_produttive  where id = rec)) 
		 from linee_attivita_controlli_ufficiali l
		 join ticket t on t.ticketid = l.id_controllo_ufficiale and l.trashed_date is null and t.trashed_date is null  
		 where id_linea_attivita = rec and t.provvedimenti_prescrittivi = 5 and t.tipologia = 3 --and t.closed is not null 
		 and riferimento_nome_tab  = convert_rif_nome_tab
		 order by t.assigned_date desc limit 1 -- controllo se è opu
		)
	  select 
		  categoria_quantitativa 
	 from cte; --rec in input dovrebbe avere la singola linea
     END LOOP;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_categoria_rischio_(integer, text)
  OWNER TO postgres;

select * from public.get_categoria_rischio_(251586,'opu_stabilimento')


-- Function: public.get_punteggio_non_conformita(integer, timestamp without time zone)
-- DROP FUNCTION public.get_punteggio_non_conformita(integer, timestamp without time zone);

CREATE OR REPLACE FUNCTION public.get_categoria_rischio_media(IN _riferimento_id integer, _riferimento_id_nome_tab text)
  RETURNS double precision AS
$BODY$
DECLARE
	conta_linee integer;
	categoria_media double precision;
BEGIN
        conta_linee := (select count(id_linea) from ricerche_anagrafiche_old_materializzata  where riferimento_id  = _riferimento_id and riferimento_id_nome_tab = _riferimento_id_nome_tab);
        select sum(categoria_linea *1.0)/conta_linee into categoria_media from public.get_categoria_rischio_(_riferimento_id,_riferimento_id_nome_tab);

    
     RETURN categoria_media;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_categoria_rischio_media(integer, text)
  OWNER TO postgres;

select * from public.get_categoria_rischio_media(251586,'opu_stabilimento')


CREATE OR REPLACE FUNCTION public.get_categoria_rischio_qualitativa(IN _riferimento_id integer, _riferimento_id_nome_tab text)
  RETURNS text AS
$BODY$
DECLARE
	res double precision;
	categoria text;
BEGIN

        res :=  (select * from public.get_categoria_rischio_media(_riferimento_id,_riferimento_id_nome_tab));
        if res >= 0 AND res <= 3.5 then 
		categoria = 'RISCHIO BASSO';
        elsif res > 3.5 and res <= 4.4 then
		categoria = 'RISCHIO MEDIO';
	else 
		categoria = 'RISCHIO ALTO';
	END IF;

	return categoria;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_categoria_rischio_qualitativa(integer, text)
  OWNER TO postgres;

select * from public.get_categoria_rischio_qualitativa(251586,'opu_stabilimento')


/*
with cte_ext as (

	with cte_int as	(
		 select t.assigned_date, t.ticketid as id_controllo,
			t.categoria_rischio, (select description from lookup_categoria_rischio where code = t.livello_rischio) as livello_rischio, t.data_prossimo_controllo,
			case 	when t.alt_id > 0 then t.alt_id
				when t.id_stabilimento > 0 then t.id_stabilimento
				when t.id_apiario > 0 then t.id_apiario
				when t.org_id > 0 then t.org_id end as riferimento_id ,
		      case 	when t.alt_id > 0 then (select return_nome_tabella from gestione_id_alternativo(t.alt_id,-1))
				when t.id_stabilimento > 0 then 'opu_stabilimento'
				when t.id_apiario > 0 then 'apicoltura_imprese'
				when t.org_id > 0 then 'organization' end as
			riferimento_nome_tab
		 from ticket t
		 where t.tipologia=3 and trashed_date is null and t.provvedimenti_prescrittivi=5 and t.closed is not null
		 group by ticketid , riferimento_id, riferimento_nome_tab
		 order by riferimento_id, riferimento_nome_tab, assigned_date desc
	)
	      select distinct on (riferimento_id, riferimento_nome_tab)
	        id_controllo,
	        riferimento_id,
	        riferimento_nome_tab,
	        data_prossimo_controllo,
	        assigned_date,
		categoria_rischio,
		livello_rischio,
		id_controllo as id_controllo_ultima_categorizzazione,
		assigned_date as data_controllo_ultima_categorizzazione,
		case when id_controllo is null then 'EX ANTE' ELSE 'CATEGORIZZATO DA CU' end as tipo_categorizzazione
		from cte_int
	) 
    select distinct -- aggiunta della distinct 
	v.riferimento_id ,
	v.riferimento_id_nome_tab ,
	v.ragione_sociale,
	v.asl_rif ,
	v.asl ,
	v.codice_fiscale::text,
	v.codice_fiscale_rappresentante ,
	v.partita_iva::text,
	v.n_reg,
	v.nominativo_rappresentante ,
	v.comune,
	v.provincia_stab,
	v.indirizzo,
	v.cap_stab,
	v.comune_leg,
	v.provincia_leg ,
	v.indirizzo_leg,
	v.cap_leg,
	v.latitudine_stab ,
	v.longitudine_stab ,
	case when l.id_controllo is null then coalesce(l.categoria_rischio,v.categoria_rischio) else l.categoria_rischio end as categoria_rischio, -- integrazione per gestione categoria di rischio EX ANTE
	l.data_prossimo_controllo as prossimo_controllo,
	l.id_controllo as id_controllo_ultima_categorizzazione,
	l.assigned_date as data_controllo_ultima_categorizzazione,
        case when l.id_controllo is null then 'EX ANTE' ELSE 'CATEGORIZZATO DA CU' end as tipo_categorizzazione,
        v.data_inserimento,
       l.livello_rischio

    from ricerche_anagrafiche_old_materializzata v 
        left join cte_ext l on l.riferimento_id  = v.riferimento_id and l.riferimento_nome_tab = v.riferimento_id_nome_tab
    where v.data_inserimento between data_1 and data_2 
    */

-- DETTAGLIO ANAGRAFICA 
-- Function: public.get_valori_anagrafica_extra(integer)

-- DROP FUNCTION public.get_valori_anagrafica_extra_142(integer);

-- Function: public.get_valori_anagrafica_extra(integer)

-- DROP FUNCTION public.get_valori_anagrafica_extra_142(integer);

CREATE OR REPLACE FUNCTION public.get_valori_anagrafica_extra_142(IN _altid integer DEFAULT '-1'::integer)
  RETURNS TABLE(alt_id integer, inserito_da text, inserito_il text, categoria_di_rischio text,  data_inizio text, stato text, tipo_attivita text, note text, categorizzabili text, note_impresa text) AS
$BODY$
DECLARE
	r RECORD;
	descrizioneStato text;
	idLineaAttiva integer;
	flagCategorizzabili boolean;
	id_stato_stab integer;	
	_id_stabilimento integer;
	_cat_di_rischio integer;
	-- new per flusso 142
	_cat_di_rischio_qual text;
	categoria_di_rischio_qual text;
	categoria_default text;
BEGIN

	select get_stato_stabilimento into id_stato_stab from get_stato_stabilimento(_altid);

	IF id_stato_stab = 0 THEN
		descrizioneStato := 'Attivo';
	ELSIF id_stato_stab = 2 THEN
		descrizioneStato := 'Sospeso';
	ELSE 
		descrizioneStato := 'Cessato';
	END IF;

	select id into _id_stabilimento from opu_stabilimento o where o.alt_id = _altid; 

	update opu_stabilimento 
	set data_inizio_attivita = (select min(orels.data_inizio) from opu_relazione_stabilimento_linee_produttive orels where orels.id_stabilimento = _id_stabilimento and orels.enabled) 
	where id = _id_stabilimento;    

	select flag.categorizzabili into flagCategorizzabili 
		from opu_relazione_stabilimento_linee_produttive oprel join ml8_linee_attivita_nuove_materializzata ml8 on oprel.id_linea_produttiva  = ml8.id_nuova_linea_attivita
			join master_list_flag_linee_attivita flag on ml8.id_nuova_linea_attivita = flag.id_linea
		where  oprel.enabled and  oprel.id_stabilimento = (select os.id from opu_stabilimento os where os.alt_id = _altid) 
		and flag.categorizzabili 
		order by flag.categorizzabili desc;

	_cat_di_rischio_qual := (select * from get_categoria_rischio_qualitativa(_id_stabilimento,'opu_stabilimento'));

	-- diventa quella di default come label
	select coalesce(max(categoria_rischio_default))::integer into _cat_di_rischio 
		from opu_relazione_stabilimento_linee_produttive rel 
		join ml8_linee_attivita_nuove_materializzata ml8 on ml8.id_nuova_linea_attivita = rel.id_linea_produttiva 
		left join master_list_flag_linee_attivita flag on flag.id_linea = ml8.id_nuova_linea_attivita
		where flag.categorizzabili and rel.enabled and rel.id_stabilimento = _id_stabilimento;
	--	update opu_stabilimento set categoria_rischio = _cat_di_rischio where id = _id_stabilimento;
	--	perform refresh_anagrafica(_id_stabilimento, 'opu');
	--END IF;
		
	raise info ' CAT DI RISCHIO QUAL%', _cat_di_rischio_qual;
	raise info ' CAT DI RISCHIO DEFAULT%', concat_ws('-',flagCategorizzabili::text, _cat_di_rischio);

	RETURN QUERY 
		select 
		s.alt_id,
		CASE WHEN acc.user_id > 0 THEN 
			concat_ws(' ', con.namefirst, con.namelast) 
		ELSE concat_ws(' ', conext.namefirst, conext.namelast) 
		END as inserito_da,
		to_char(s.entered, 'dd/MM/yyyy') as inserito_il,
		_cat_di_rischio_qual::text AS categoria_di_rischio_qual,
		to_char(s.data_inizio_attivita, 'dd/MM/yyyy') as data_inizio,
		descrizioneStato as stato,
		lti.description::text as tipo_attivita,
		CASE WHEN (length(trim(s.note)) <> 0) THEN trim( regexp_replace(s.note, '\r|\n', ' ', 'g'))::text ELSE ''::text END as note,
		concat_ws(' ',(case when flagCategorizzabili then 'CATEGORIZZABILE CON CATEGORIA DI DEFAULT' ELSE 'NON CATEGORIZZABILE' end), _cat_di_rischio)::text as categoria_default,
		CASE WHEN (length(trim(o.note)) <> 0) THEN trim( regexp_replace(o.note, '\r|\n', ' ', 'g'))::text ELSE ''::text END as note_impresa
		from opu_stabilimento s
		join opu_operatore o on s.id_operatore = o.id
		left join access_ acc on acc.user_id = s.entered_by
		left join contact_ con on con.contact_id = acc.contact_id
		left join access_ext_ accext on accext.user_id = s.entered_by
		left join contact_ext_ conext on conext.contact_id = accext.contact_id
		left join lookup_tipo_attivita lti on lti.code = s.tipo_attivita

		where 1 = 1 and ( _altid = -1 or s.alt_id = _altid ); -- gestione valori opzionali

	--LOOP
	--	RETURN NEXT;
	--    END LOOP;
	     --RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_valori_anagrafica_extra_142(integer)
  OWNER TO postgres;

 -- test select * from get_valori_anagrafica_extra_142(20183564)
 
  -- Function: public.get_categoria_rischio_quantitativa(integer, text, integer)

-- DROP FUNCTION public.get_categoria_rischio_quantitativa(integer, text, integer);

CREATE OR REPLACE FUNCTION public.get_categoria_rischio_quantitativa(
    IN _riferimento_id integer DEFAULT (- 1),
    IN _riferimento_id_nome_tab text DEFAULT ''::text,
    IN _idcu integer DEFAULT (- 1))
  RETURNS TABLE(categoria_linea integer) AS
$BODY$
DECLARE
   rec integer;
   convert_rif_nome_tab text;
BEGIN

    if _riferimento_id_nome_tab = 'opu_stabilimento' then
	convert_rif_nome_tab = 'opu_relazione_stabilimento_linee_produttive';
    elsif _riferimento_id_nome_tab = 'sintesis_stabilimento' then
	convert_rif_nome_tab = 'sintesis_relazione_stabilimento_linee_produttive';
    else
	convert_rif_nome_tab='organization';
    end if;

    if _idcu > 0 then
	return query 
		select categoria_quantitativa from linee_attivita_controlli_ufficiali where trashed_date is null and id_controllo_ufficiale = _idcu;
    else 
	  FOR rec IN
        select id_linea from ricerche_anagrafiche_old_materializzata  where riferimento_id  = _riferimento_id and riferimento_id_nome_tab = _riferimento_id_nome_tab
     LOOP
        RETURN QUERY
        with cte as (SELECT    
                 -- categoria di rischio da recuperare sia da cu che da EX ANTE
		 categoria_quantitativa --(select categoria_rischio_default from master_list_flag_linee_attivita  where trashed is null and id_linea = (	
					-- select id_linea_produttiva from opu_relazione_stabilimento_linee_produttive  where id = rec)) 
		 from linee_attivita_controlli_ufficiali l
		 join ticket t on t.ticketid = l.id_controllo_ufficiale and l.trashed_date is null and t.trashed_date is null  
		 where id_linea_attivita = rec and t.provvedimenti_prescrittivi = 5 and t.tipologia = 3 --and t.closed is not null 
		 and riferimento_nome_tab  = convert_rif_nome_tab
		 order by t.assigned_date desc limit 1 -- controllo se � opu
		)
	  select 
		  categoria_quantitativa 
	 from cte; --rec in input dovrebbe avere la singola linea
     END LOOP;

    end if;
		
   
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_categoria_rischio_quantitativa(integer, text, integer)
  OWNER TO postgres;

