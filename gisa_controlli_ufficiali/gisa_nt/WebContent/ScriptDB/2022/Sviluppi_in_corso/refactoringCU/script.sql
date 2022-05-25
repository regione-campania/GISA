CREATE OR REPLACE FUNCTION public.get_oggetto_del_controllo(IN _code integer)
  RETURNS TABLE(code integer, capitolo text, oggetto text, codice_evento text) AS
$BODY$
DECLARE
BEGIN
RETURN QUERY
	select i.code, m.description::text, i.description::text, e.codice_evento::text
	from lookup_ispezione_macrocategorie m 
	join lookup_ispezione i on i.id_macrocategoria = m.code
	left join rel_oggetti_eventi_cu rel on rel.cod_raggrup_oggetto = i.cod_raggruppamento
	left join lookup_eventi_oggetti_cu e on e.code = rel.id_evento_cu and rel.trashed_date is null
	where m.enabled and i.enabled 
	and ( _code = -1 or i.code = _code)
	order by m.level, i.description;
END;
$BODY$
  LANGUAGE plpgsql
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_oggetto_del_controllo(integer)
  OWNER TO postgres;

alter table lookup_ispezione add column id_macrocategoria integer;
alter table lookup_ispezione add column cod_raggruppamento text;

update lookup_ispezione set id_macrocategoria = level;

insert into permission (category_id,permission,permission_view,permission_add,permission_edit,permission_delete,description , level,enabled,active)
values (36,'gestionenuovacu',true,true,true,true,'GESTIONE CAVALIERE DEDICATO AI CU',10,true,true) returning id;

insert into role_permission (id, role_id, permission_id, role_view, role_add) values ((select max(id)+1 from role_permission),1,796,true, true); 
insert into role_permission (id, role_id, permission_id, role_view, role_add) values ((select max(id)+1 from role_permission),31,796,true, true); 
insert into role_permission (id, role_id, permission_id, role_view, role_add) values ((select max(id)+1 from role_permission),32,796,true, true); 


CREATE TABLE public.lookup_eventi_oggetti_cu
(
  code serial,
  description character varying(300),
  codice_evento character varying(300),
  old_description character varying(300),
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.lookup_eventi_oggetti_cu
  OWNER TO postgres;

-- Table: public.rel_motivi_eventi_cu

-- DROP TABLE public.rel_oggetti_eventi_cu;

CREATE TABLE public.rel_oggetti_eventi_cu
(
  code serial,
  id_evento_cu integer,
  trashed_date timestamp without time zone,
  entered timestamp without time zone,
  enteredby integer,
  modified timestamp without time zone,
  modifiedby integer,
  note_hd text,
  cod_raggrup_oggetto text
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.rel_oggetti_eventi_cu
  OWNER TO postgres;


insert into lookup_eventi_oggetti_cu(description, codice_evento, old_description, level, enabled) values ('Altro - Macroarea Alimenti uso umano', 'isAltroAlimentiUsoUmano', '-', 0, true);
insert into lookup_eventi_oggetti_cu(description, codice_evento, old_description, level, enabled) values ('Altro - Macroarea Alimentazione animale', 'isAltroAlimentazioneanimale', '37', 1, true);
insert into lookup_eventi_oggetti_cu(description, codice_evento, old_description, level, enabled) values ('Altro - Macroarea Impianti M.S.R e S.O.A', 'isAltroImpianti', '53', 2, true);
insert into lookup_eventi_oggetti_cu(description, codice_evento, old_description, level, enabled) values ('Altro - Macroarea M.S.R e S.O.A e rifiuti', 'isAltroRifiuti', '59', 3, true);
insert into lookup_eventi_oggetti_cu(description, codice_evento, old_description, level, enabled) values ('Altro - Macroarea Benessere animale', 'isAltroBenessereAnimale', '-', 4, true);
insert into lookup_eventi_oggetti_cu(description, codice_evento, old_description, level, enabled) values ('Altro - Macroarea Salute animale e iuv', 'isAltroSalute', '68', 5, true);
insert into lookup_eventi_oggetti_cu(description, codice_evento, old_description, level, enabled) values ('Altro - Macroarea Altro', 'isAltroAltro', '63', 6, true);
insert into lookup_eventi_oggetti_cu(description, codice_evento, old_description, level, enabled) values ('Altro - Macroarea Farmaci veterinari', 'isAltroFarmaciVeterinari', '123', 7, true);
insert into lookup_eventi_oggetti_cu(description, codice_evento, old_description, level, enabled) values ('Benessere Durante il trasporto', 'isBenessereTrasporto', '126', 8, true);

--select * from lookup_eventi_oggetti_cu    where enabled
update lookup_ispezione set cod_raggruppamento  = 'MALA' where code = 37;
update lookup_ispezione set cod_raggruppamento  = 'MIMS' where code = 53;
update lookup_ispezione set cod_raggruppamento  = 'MMSR' where code = 59;
update lookup_ispezione set cod_raggruppamento  = 'MSAI' where code = 68;
update lookup_ispezione set cod_raggruppamento  = 'MALT' where code = 63;
update lookup_ispezione set cod_raggruppamento  = 'MFV' where code = 123;
update lookup_ispezione set cod_raggruppamento  = 'BDT' where code = 126;

insert into rel_oggetti_eventi_cu (id_evento_cu, entered, enteredby, cod_raggrup_oggetto) values (2,now(),6567, 'MALA');
insert into rel_oggetti_eventi_cu (id_evento_cu, entered, enteredby, cod_raggrup_oggetto) values (3,now(),6567, 'MIMS');
insert into rel_oggetti_eventi_cu (id_evento_cu, entered, enteredby, cod_raggrup_oggetto) values (4,now(),6567, 'MMSR');
insert into rel_oggetti_eventi_cu (id_evento_cu, entered, enteredby, cod_raggrup_oggetto) values (6,now(),6567, 'MSAI');
insert into rel_oggetti_eventi_cu (id_evento_cu, entered, enteredby, cod_raggrup_oggetto) values (7,now(),6567, 'MALT');
insert into rel_oggetti_eventi_cu (id_evento_cu, entered, enteredby, cod_raggrup_oggetto) values (8,now(),6567, 'MFV');
insert into rel_oggetti_eventi_cu (id_evento_cu, entered, enteredby, cod_raggrup_oggetto) values (9,now(),6567, 'BDT');

------------------------ dbi per bean Anagrafica --------------------------------
CREATE OR REPLACE FUNCTION public.get_anagrafica_by_id(IN _riferimento_id integer, _riferimento_id_nome_tab text)
  RETURNS TABLE(ragione_sociale text, riferimento_id_nome_tab text, riferimento_id integer, partita_iva text, n_reg text) AS
$BODY$
DECLARE
BEGIN
RETURN QUERY
	 
select distinct m.ragione_sociale::text, m.riferimento_id_nome_tab, m.riferimento_id, m.partita_iva::text, m.n_reg::text 
from ricerche_anagrafiche_old_materializzata m
where m.riferimento_id = _riferimento_id and m.riferimento_id_nome_tab = _riferimento_id_nome_tab ;
END;
$BODY$
  LANGUAGE plpgsql
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_anagrafica_by_id(integer, text)
  OWNER TO postgres;

CREATE OR REPLACE FUNCTION public.get_anagrafica_linee_by_id(
    IN _riferimento_id integer,
    IN _riferimento_id_nome_tab text,
    IN _id_linea integer,
    IN _id_tecnica integer)
  RETURNS TABLE(n_linea text, id_linea integer, norma text, macroarea text, aggregazione text, attivita text, codice_macroarea text, codice_aggregazione text, codice_attivita text) AS
$BODY$
DECLARE
BEGIN
	if _id_tecnica = 5 then
		RETURN QUERY
		select distinct m.n_linea::text, m.id_linea, m.norma, m.macroarea, m.aggregazione, m.attivita, m.codice_macroarea, m.codice_aggregazione, m.codice_attivita 
		from ricerche_anagrafiche_old_materializzata m
		join master_list_flag_linee_attivita f on f.codice_univoco =  concat_ws('-', m.codice_macroarea, m.codice_aggregazione, m.codice_attivita)
		where m.riferimento_id = _riferimento_id and m.riferimento_id_nome_tab = _riferimento_id_nome_tab and f.categorizzabili
		order by id_linea asc;
	else
		RETURN QUERY
		select distinct m.n_linea::text, m.id_linea, m.norma, m.macroarea, m.aggregazione, m.attivita, m.codice_macroarea, m.codice_aggregazione, m.codice_attivita 
		from ricerche_anagrafiche_old_materializzata m
		where m.riferimento_id = _riferimento_id and m.riferimento_id_nome_tab = _riferimento_id_nome_tab 
		and ( _id_linea = -1 or m.id_linea = _id_linea)
		order by id_linea asc;
	end if;

END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_anagrafica_linee_by_id(integer, text, integer, integer)
  OWNER TO postgres;

  --select * from public.get_anagrafica_linee_by_id(183564,'opu_stabilimento',-1,-1)
  

--select * from public.get_anagrafica_by_id(183564, 'opu_stabilimento')
--select * from public.get_anagrafica_linee_by_id(183564, 'opu_stabilimento', 417534)
--select * from public.get_anagrafica_linee_by_id(183564, 'opu_stabilimento', -1)
--select * from public.get_oggetto_del_controllo(123);

  -- aggiornamento dbi per far restituire anche codice evento
  CREATE OR REPLACE FUNCTION public.get_motivi_cu(
    IN _tipologiaoperatore integer,
    IN anno_input integer,
    IN _tipo_attivita text[])
  RETURNS TABLE(cod_interno_ind text, codice_evento text, id_tipo_ispezione integer, descrizione_tipo_ispezione character varying, codice_int_tipo_ispe text, id_piano integer, descrizione_piano character varying, codice_int_piano integer, ordinamento integer, ordinamento_figli integer, livello_tipo_ispezione integer, data_scadenza timestamp without time zone, codice_interno_ind text, anno integer, codice text, tipo_attivita text) AS
$BODY$
DECLARE
	r RECORD;
BEGIN
	FOR cod_interno_ind , codice_evento, id_tipo_ispezione , descrizione_tipo_ispezione  , codice_int_tipo_ispe , id_piano , descrizione_piano  , codice_int_piano , ordinamento , ordinamento_figli , livello_tipo_ispezione , data_scadenza  , codice_interno_ind , anno , codice, tipo_attivita 
	
	in
	
        select a.cod_interno_ind, a.codice_evento, a.id_tipo_ispezione , a.descrizione_tipo_ispezione  , a.codice_int_tipo_ispe , a.id_piano , a.descrizione_piano  , a.codice_int_piano , a.ordinamento , a.ordinamento_figli , 
        a.livello_tipo_ispezione , a.data_scadenza  , a.codice_interno_ind , a.anno ,a.codice, a.tipo_attivita  from (  select * from (select distinct on (a.codice_interno_ind) a.codice_interno_ind as cod_interno_ind ,a.* 
        from (select c.*, ev.codice_evento
		from controlli_ufficiali_motivi_ispezione c 
		left join dpat_indicatore_new d on d.id = coalesce(c.id_tipo_ispezione, c.id_piano) 
		left join rel_motivi_eventi_cu rel on rel.cod_raggrup_ind = d.cod_raggruppamento and rel.trashed_date is null
		left join lookup_eventi_motivi_cu ev on ev.code = rel.id_evento_cu and ev.enabled 
		where  c.anno = anno_input and c.tipo_attivita = ANY (_tipo_attivita) and (c.data_scadenza > (now() + '1 day'::interval) OR c.data_scadenza IS NULL) ) a 
		order by cod_interno_ind,data_scadenza )bb  

		union  

		select * from (select  a.codice_interno_ind as cod_interno_ind ,a.* 
		            from (select c.*, ev.codice_evento from 
		            controlli_ufficiali_motivi_ispezione c 
		            left join dpat_indicatore_new d on d.id = coalesce(c.id_tipo_ispezione, c.id_piano) 
		            left join rel_motivi_eventi_cu rel on rel.cod_raggrup_ind = d.cod_raggruppamento and rel.trashed_date is null
			    left join lookup_eventi_motivi_cu ev on ev.code = rel.id_evento_cu and ev.enabled 
		            where c.anno = anno_input and c.tipo_attivita = ANY (_tipo_attivita) and (c.data_scadenza > (now() + '1 day'::interval) OR c.data_scadenza IS NULL) and (c.codice_interno_ind is null or c.codice_interno_ind = '0') ) a 
	        order by cod_interno_ind,data_scadenza )bb ) a 
        where 1=1 and
 (_tipologiaoperatore IN (select cmo.tipologia_operatore from cu_motivi_operatori cmo) AND a.codice in (select cmo.codice_piano_o_attivita from cu_motivi_operatori cmo where cmo.tipologia_operatore = _tipologiaoperatore))
 or (_tipologiaoperatore NOT IN (select cmo.tipologia_operatore from cu_motivi_operatori cmo))
         order by a.ordinamento,a.ordinamento_figli
        
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_motivi_cu(integer, integer, text[])
  OWNER TO postgres;


/*
 * -- test unitari
create table prova_json (
	anagraficacu json,
	tecnicacu json,
	lineacu json,
	daticu json,
	motivicu json,
	nucleocu json
	)
 * */
  
CREATE OR REPLACE FUNCTION public.cu_insert_anagrafica(IN _json_anagrafica json, IN _idutente integer)
  RETURNS integer AS
$BODY$	
DECLARE
	riferimento_id integer;
	riferimento_tab text;
	nomecol text = 'opu_stabilimento';
	resultid integer;
BEGIN
	  -- recupero il riferimento anagrafico
	  riferimento_id := _json_anagrafica ->> 'riferimentoId' ;
	  riferimento_tab := _json_anagrafica ->> 'riferimentoIdNomeTab';
	  raise info 'recupero id: %', riferimento_id;
	  raise info 'recupero tab: %', riferimento_tab;

	  if riferimento_tab = 'organization' then
		nomecol = 'org_id';
	  elsif riferimento_tab = 'opu_stabilimento' then
		nomecol = 'id_stabilimento';
	  elsif riferimento_tab = 'sintesis_stabilimento' then 
		nomecol = 'alt_id';
	  else 
		nomecol = 'id_apiario';
	  end if;

	  raise info 'recupero nomecol: %', nomecol;

	execute format('INSERT INTO ticket (ticketid, '||nomecol||', entered, enteredby, modifiedby, status_id, tipologia, problem) values
	  ((select max(ticketid) +1 from ticket where ticketid <10000000), '||riferimento_id||', now(), '||_idutente||','||_idutente||',1,3, '''') returning ticketid') into resultid;


	  return resultid;
	 		
END;
$BODY$
   LANGUAGE plpgsql VOLATILE 
  COST 100;
ALTER FUNCTION public.cu_insert_anagrafica(json, integer)
  OWNER TO postgres;


CREATE OR REPLACE FUNCTION public.cu_insert_tecnica(IN _json_tecnica json, _idcu integer)
  RETURNS integer AS
$BODY$	
BEGIN
	  -- effettuo l'update in quanto il record CU esiste gia'
	  UPDATE ticket set provvedimenti_prescrittivi = (_json_tecnica ->> 'id')::int where ticketid = _idcu;
	  return 1;		
END;
$BODY$
   LANGUAGE plpgsql VOLATILE 
  COST 100;
ALTER FUNCTION public.cu_insert_tecnica(json, integer)
  OWNER TO postgres;
  
CREATE OR REPLACE FUNCTION public.cu_insert_linee(IN _json_linea json, _idcu integer, _riferimento_nome_tab text)
  RETURNS integer AS
$BODY$	
DECLARE
	i json; 
BEGIN
	  -- per quante sono le linee, inserisci 
	  FOR i IN SELECT * FROM json_array_elements(_json_linea) 
	  LOOP
	      RAISE NOTICE 'id %', i->>'id';
		-- can do some processing here
		  INSERT INTO linee_attivita_controlli_ufficiali (id_controllo_ufficiale, id_linea_attivita, codice_linea, riferimento_nome_tab) values
		  (_idcu, (i->>'id')::integer, i->>'codice', _riferimento_nome_tab);
	  END LOOP;

    	 return 1;
END;
$BODY$
   LANGUAGE plpgsql VOLATILE 
  COST 100;
ALTER FUNCTION public.cu_insert_linee(json, integer, text)
  OWNER TO postgres;  
  
  
-- bozza dbi per oggetti. Da completare attivando con le query
CREATE OR REPLACE FUNCTION public.cu_insert_oggetti(IN _json_daticonoggetto json, _idcu integer)
  RETURNS integer AS
$BODY$	
DECLARE
	i json; 
	j json;
	chiave text;
	valore text;
BEGIN
	 -- FOR i IN SELECT * FROM json_array_elements(_json_daticonoggetto -> 'Oggetti') 
	  FOR i IN SELECT * FROM json_array_elements(_json_daticonoggetto) 

	  LOOP
	      RAISE NOTICE 'id %', i->>'id';
	      RAISE NOTICE 'campiEstesi %', i->>'CampiEstesi';
	      RAISE NOTICE 'campiEstesi json to text%', to_json(i->>'CampiEstesi'); 

	     FOR j IN select json_array_elements((i->>'CampiEstesi')::json) json
	     LOOP
	      -- restituisce più di una riga
		RAISE NOTICE 'json: %',j;
		chiave := (select * from json_object_keys(j::json));
		RAISE NOTICE 'json key: %', chiave; 
		valore := (select j->(json_object_keys(j::json)));
		RAISE NOTICE 'json value: %', valore; 

		update ticket set chiave = valore where ticketid = _idcu; ----------------> da provare
		
	     END LOOP;	
		
		 INSERT INTO tipocontrolloufficialeimprese (idcontrollo, tipo_audit, bpi, haccp, ispezione, audit_tipo, id, enabled) values
		 (_idcu, -1, -1, -1, (i->>'id')::integer, -1, true);----------------> da provare
	  END LOOP;


    	 return 1;
END;
$BODY$
   LANGUAGE plpgsql VOLATILE 
  COST 100;
ALTER FUNCTION public.cu_insert_oggetti(json, integer)
  OWNER TO postgres;

  --select * from  public.cu_insert_oggetti('{"oggetti": [{"id": "110", "campiEstesi": [], "codiceEvento": "null"}, {"id": "126", "campiEstesi": [{"animalitrasportati": "1"}, {"num_documento_accompagnamento": "testdoc"}, {"num_specie1": "111"}, {"num_specie2": ""}, {"num_specie4": ""}, {"num_specie10": "222"}, {"num_specie14": ""}, {"num_specie20": "333"}, {"num_specie21": ""}, {"num_specie22": ""}, {"num_specie23": ""}, {"num_specie24": ""}, {"num_specie25": ""}, {"num_specie26": ""}], "codiceEvento": "isBenessereTrasporto"}], "dataFine": "2021-09-29", "dataInizio": "2021-08-31"}',984)
------------------------------------------NUCLEO---------------------------------------------------------
-

CREATE OR REPLACE FUNCTION public.get_nucleo_qualifiche(IN _user_id integer)
  RETURNS TABLE(id integer, nome character varying, view_lista_componenti boolean, gruppo boolean) AS
$BODY$
DECLARE
gruppoRuolo integer;
idGruppoRuolo integer;
BEGIN

select COALESCE(a.super_ruolo,-1), COALESCE(a.code,-1) into gruppoRuolo, idGruppoRuolo from 
(
select r.super_ruolo, g.code from access_ a
join role r on a.role_id = r.role_id
left join rel_gruppo_ruoli rel on rel.id_ruolo = r.role_id 
left join lookup_gruppo_ruoli g on g.code = rel.id_gruppo and g.enabled
where a.user_id = _user_id
UNION
select r.super_ruolo, g.code from access_ext_ a
join role_ext r on a.role_id = r.role_id
left join rel_gruppo_ruoli rel on rel.id_ruolo = r.role_id 
left join lookup_gruppo_ruoli g on g.code = rel.id_gruppo and g.enabled
where a.user_id = _user_id) a;

raise info 'gruppoRuolo %', gruppoRuolo;
raise info 'idGruppoRuolo %', idGruppoRuolo;

RETURN QUERY
select l.code, l.description, l.view_lista_utenti_nucleo_ispettivo, l.gruppo 
from lookup_qualifiche l where in_nucleo_ispettivo

and ((gruppoRuolo = 2 AND l.level not in (1,10)) OR (gruppoRuolo IS NULL OR gruppoRuolo <>2))
and ((idGruppoRuolo = 16 AND l.level not in (1,10, 10000, 100001)) OR (idGruppoRuolo IS NULL OR idGruppoRuolo <>16))

ORDER BY l.level,l.description, l.enabled DESC;
END;

$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_nucleo_qualifiche(integer)
  OWNER TO postgres;


CREATE OR REPLACE FUNCTION public.get_nucleo_componenti
(IN _anno integer DEFAULT NULL::integer, 
IN _id_qualifica integer DEFAULT NULL::integer, 
_id_struttura text DEFAULT ''::text)
  RETURNS TABLE(id integer, nominativo text, id_struttura integer, nome_struttura text, id_qualifica integer, nome_qualifica text) AS
$BODY$
DECLARE
	lista_strutture text;
BEGIN
	lista_strutture := (select replace(replace(_id_struttura,'(',''''),')',''''));
	raise info 'lista: %', lista_strutture;
	raise info 'string to array input: %', string_to_array(lista_strutture,',');
	return query
		select d.id_anagrafica_nominativo, d.nominativo, d.id_struttura_semplice, concat_ws('->',d.desc_strutt_complessa,d.desc_strutt_semplice), _id_qualifica, d.qualifica
		from public.dpat_get_nominativi(null::integer, _anno::integer, null::text,null::integer,null::text,null,null, _id_qualifica) d
		--where 1=1 and (_id_struttura = '' or string_to_array(d.id_Struttura_semplice::text,',') <@ string_to_array('8167,8365'::text,',')); 
		where 1=1 and (_id_struttura = '' or string_to_array(d.id_Struttura_semplice::text,',') <@ string_to_array(lista_strutture,',')); 

END;
$BODY$
  LANGUAGE plpgsql
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_nucleo_componenti(integer, integer, text)
  OWNER TO postgres;

--select * from public.get_nucleo_componenti(2021, 45, '') 
--select * from public.get_nucleo_componenti(2021, 45,'8167,8365')

CREATE OR REPLACE FUNCTION public.dpat_get_nominativi(
    IN asl_input integer DEFAULT NULL::integer,
    IN anno_input integer DEFAULT NULL::integer,
    IN stato_input text DEFAULT NULL::text,
    IN id_struttura_complessa_input integer DEFAULT NULL::integer,
    IN descrizione_struttura_complessa_input text DEFAULT NULL::text,
    IN id_struttura_semplice_input integer DEFAULT NULL::integer,
    IN descrizione_struttura_semplice_input text DEFAULT NULL::text,
    IN id_qualifica_input integer DEFAULT NULL::integer)
  RETURNS TABLE(id_nominativo integer, id_anagrafica_nominativo integer, nominativo text, codice_fiscale text, qualifica text, data_scadenza_nominativo timestamp without time zone, id_struttura_semplice integer, desc_strutt_semplice text, stato_strutt_semplice integer, data_scadenza_strutt_semplice timestamp without time zone, id_strutt_complessa integer, desc_strutt_complessa text, data_scadenza_strutt_complessa timestamp without time zone, stato_strutt_complessa integer, id_asl integer, anno integer) AS
$BODY$
DECLARE
	r RECORD;
BEGIN
	FOR id_nominativo, id_anagrafica_nominativo, nominativo,  codice_fiscale, qualifica, data_scadenza_nominativo,id_struttura_semplice, desc_strutt_semplice, 
       stato_strutt_semplice, data_scadenza_strutt_semplice, id_strutt_complessa, desc_strutt_complessa,data_scadenza_strutt_complessa, stato_strutt_complessa,  
       id_asl, anno	
      in

select n.id as id_nominativo, n.id_anagrafica_nominativo, concat(c.namefirst, ' ', c.namelast) as nominativo,  c.codice_fiscale, lq.description as qualifica,  
       n.data_scadenza as data_scadenza_nominativo,n.id_dpat_strumento_calcolo_strutture as id_struttura_semplice, strutt_semplice.descrizione as desc_strutt_semplice, 
       strutt_semplice.stato as stato_strutt_semplice, strutt_semplice.data_scadenza as data_scadenza_strutt_semplice, strutt_complessa.id as id_strutt_complessa, 
       strutt_complessa.descrizione as desc_strutt_complessa, strutt_complessa.data_scadenza as data_scadenza_strutt_complessa, strutt_complessa.stato as stato_strutt_complessa,  
       strutt_complessa.id_asl, strutt_complessa.anno
from dpat_strumento_calcolo_nominativi n 
join dpat_strutture_asl strutt_semplice on strutt_semplice.id = n.id_struttura and strutt_semplice.disabilitato = false
join dpat_strutture_asl strutt_complessa on strutt_complessa.id = strutt_semplice.id_padre and strutt_complessa.disabilitato = false 
join lookup_qualifiche lq on lq.code = id_lookup_qualifica 
join access_ users on users.user_id =  n.id_anagrafica_nominativo
join contact_ c on c.contact_id = users.contact_id
where n.trashed_Date is null and 
      (strutt_semplice.stato::text = ANY (string_to_array(stato_input, ',')) or stato_input is null) and
      (strutt_complessa.id = id_struttura_complessa_input or id_struttura_complessa_input is null) and 
      (strutt_complessa.descrizione ilike '%' || descrizione_struttura_complessa_input || '%' or descrizione_struttura_complessa_input is null) and
      (strutt_semplice.id = id_struttura_semplice_input or id_struttura_semplice_input is null) and 
      -- NEW per refactoring nucleo
      (lq.code = id_qualifica_input or id_qualifica_input is null) and 
      ------ end new
      (strutt_semplice.descrizione ilike '%' || descrizione_struttura_semplice_input || '%' or descrizione_struttura_semplice_input is null) and
      strutt_complessa.id_strumento_calcolo in (select id 
						from dpat_strumento_calcolo 
						where (strutt_complessa.id_asl = asl_input or asl_input is null) and 
						      (strutt_complessa.anno = anno_input or anno_input is null) 
						)
order by lq.livello_qualifiche_dpat


        LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.dpat_get_nominativi(integer, integer, text, integer, text, integer, text, integer)
  OWNER TO postgres;
CREATE OR REPLACE FUNCTION public.cu_insert_nucleo(
    _json_daticonnucleo json,
    _idcu integer)
  RETURNS integer AS
$BODY$	
DECLARE
	i json; 
BEGIN
	  FOR i IN SELECT * FROM json_array_elements(_json_daticonnucleo) 
	  LOOP
	      RAISE INFO 'id nominativo %', i->>'id';
	      RAISE INFO 'struttura %', i->>'Struttura';
	      RAISE INFO 'id struttura %', (i->>'Struttura')::json ->> 'id'; 
	
		 INSERT INTO cu_nucleo (id_controllo_ufficiale, id_componente, enabled) values (_idcu, (i->>'id')::integer,true);
	  END LOOP;


    	 return 1;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.cu_insert_nucleo(json, integer)
  OWNER TO postgres;


------------------------------------------------------------ FINE NUCLEO -----------------------------------------------------------------------------
----------------------------------------------------------- MOTIVI CU --------------------------------------------------------------------------------


-- ispezioni con motivi  e per conto di nella stessa tabella (prevedere la tecnica in input)
CREATE OR REPLACE FUNCTION public.cu_insert_motivi(IN _json_daticonmotivi json, _idcu integer, _idtecnica integer)
  RETURNS integer AS
$BODY$	
DECLARE
	i json; 
	j json;
	chiave text;
	valore text;
BEGIN
	 -- FOR i IN SELECT * FROM json_array_elements(_json_daticonmotivi -> 'Motivi')
	  FOR i IN SELECT * FROM json_array_elements(_json_daticonmotivi) 
	  LOOP
	      RAISE INFO 'id %', i->>'idPianoMonitoraggio';
	      RAISE INFO 'idAttivita %', i->>'idAttivita';
	      RAISE INFO 'campiEstesi %', i->>'CampiEstesi';
	      RAISE INFO 'per conto di %', i->>'PerContoDi';

	      -- GESTIRE I CAMPI ESTESI
	     --FOR j IN select json_array_elements((i->>'CampiEstesi')::json) json
	     --LOOP
	      -- restituisce più di una riga
		--RAISE INFO 'json: %',j;
		--chiave := (select * from json_object_keys(j::json));
		--RAISE INFO 'json key: %', chiave; 
		--valore := (select j->(json_object_keys(j::json)));
		--RAISE INFO 'json value: %', valore; 

		--update ?; ----------------> da provare
		
	    -- END LOOP;	
	    
		-- per ogni motivo viene fatta una insert in tipocontrolloufficialeimprese (il json dovrebbe sempre passare valorizzati i campi  pianomonitoraggio, tipoispezione con -1 quando assenti)
		--if _id_tecnica <> 5 then 
			INSERT INTO tipocontrolloufficialeimprese (idcontrollo, tipo_audit, bpi, haccp, ispezione, audit_tipo, id, enabled, pianomonitoraggio, tipoispezione, id_unita_operativa) values
			(_idcu, -1, -1, -1, -1, -1, (select max(id)+1 from tipocontrolloufficialeimprese),true,(i->>'idPianoMonitoraggio')::int,(i->>'idAttivita')::int,  ((i->>'PerContoDi')::json ->> 'id')::int);----------------> da provare
		--else
		--in caso di VECCHIA sorveglianza, il per conto di si salva qui. Non si scrive in tipocontrolloufficialeimprese
			--INSERT INTO unita_operative_controllo(id_controllo, id_unita_operativa) values (_idcu, (i->>'PerContoDi')::json ->> 'id');
		--end if;
			
	  END LOOP;

    	 return 1;
END;
$BODY$
   LANGUAGE plpgsql VOLATILE 
  COST 100;
ALTER FUNCTION public.cu_insert_motivi(json, integer)
  OWNER TO postgres;

 --select * from public.cu_insert_motivi('{"Motivi":[
--	{"idPianoMonitoraggio":9581,"CampiEstesi":[],"idAttivita":89,"nome":"A1 - PIANO DI ERADICAZIONE DELLA TBC,BRC E LEB NEI BOVINI E BUFALINI >> A1_A - N. BOVINI DA CONTROLLARE DI AZIENDE U.I. O I.  (CONTROLLO DEL 100% DEGLI ANIMALI SOGGETTI A CONTROLLO)",
--	"codiceEvento":"null", "PerContoDi":{"nome":"UNITA OPERATIVA COMPLESSA IGIENE DEGLI ALIMENTI DI ORIGINE ANIMALE->UOV IAOA BAIANO\/LAURO","id":"8183"}
--	}]}'::json, 1234)

  ---------------------------------------------------- FINE MOTIVI ------------------------------------------------------------------------------------
  
  -- Function: public.get_asl_controllo(integer, text, integer, integer)

-- DROP FUNCTION public.get_asl_controllo(integer, text, integer, integer);

CREATE OR REPLACE FUNCTION public.get_asl_controllo(
    IN _riferimento_id integer,
    IN _riferimento_id_nome_tab text,
    IN _id_utente integer,
    IN _codiceasl integer)
  RETURNS TABLE(id integer, nome text) AS
$BODY$
DECLARE
	id_asl_recuperato integer;
	ruolo_utente integer;
	asl_recuperata text;
	id_asl_utente integer;
	asl_utente text;
BEGIN
	-- recupero ruolo utente
	ruolo_utente := (select role_id from access_ where user_id  = _id_utente UNION select role_id from access_ext_ where user_id  = _id_utente);
	raise info 'ruolo_utente: %', ruolo_utente;
	-- recupero ASL dello stabilimento
	id_asl_recuperato := (select asl_rif from ricerche_anagrafiche_old_materializzata where 
			      riferimento_id = _riferimento_id and riferimento_id_nome_tab = _riferimento_id_nome_tab and tipo_attivita =1 limit 1);
	asl_recuperata := (select asl from ricerche_anagrafiche_old_materializzata where  
			      riferimento_id = _riferimento_id and riferimento_id_nome_tab = _riferimento_id_nome_tab and tipo_attivita =1 limit 1);	
	-- recupero ASL dell'utente
	select ad.site_id, lsi.description into id_asl_utente, asl_utente
	from access_dati ad left join lookup_site_id lsi on lsi.code = ad.site_id
	where ad.user_id = _id_utente and ad.site_id > 0;		      	

	raise info 'id_asl_recuperato: %', id_asl_recuperato;
	raise info 'asl_recuperata: %', asl_recuperata;
	raise info 'id_asl_utente: %', id_asl_utente;
	raise info 'asl_utente: %', asl_utente;
			      	      	      
	-- se lo stabilimento è fisso, recupero ASL 
	if id_asl_recuperato > 0 then
		return query select id_asl_recuperato, asl_recuperata;
	-- se lo stabilimento è mobile e l'utente ha l'ASL, recupero ASL
	elsif (id_asl_recuperato is null or id_asl_recuperato <= 0) and id_asl_utente > 0 then
		return query select id_asl_utente, asl_utente;
	else -- è mobile o è un altro scenario...
		return query select code, description::text from lookup_site_id where enabled and code <> 16 order by code;
	end if;
	
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_asl_controllo(integer, text, integer, integer)
  OWNER TO postgres;


--select * from get_asl_controllo('183564','opu_stabilimento', 5885, -1);
------------------------------------------- INSERIMENTO GLOBALE CU -------------------------
  -- ispezioni con motivi  e per conto di nella stessa tabella (prevedere la tecnica in input)
  -- ispezioni con motivi  e per conto di nella stessa tabella (prevedere la tecnica in input)
  -- ispezioni con motivi  e per conto di nella stessa tabella (prevedere la tecnica in input)
CREATE OR REPLACE FUNCTION public.cu_insert_cu_globale(IN _json_dati json)
  RETURNS integer AS
$BODY$	
DECLARE
	anagrafica json; 
	utenti json;
	tecnicacu json;
	lineacu json;
	oggetticu json;
	motivicu json;
	nucleo json;
	datigenerici json; -- qui dovrebbero essere incluse anche le note
	asl json;

	idcontrollo integer;
	idutente integer;
	output integer;
	
BEGIN
	 -- mi ricavo i singoli oggetti JSON per blocchi
	tecnicacu :=  _json_dati ->'Tecnica';
	RAISE INFO 'json tecnica %',tecnicacu;

	anagrafica :=  _json_dati ->'Anagrafica'; 
	RAISE INFO 'json anagrafica %',anagrafica;

	utenti :=  _json_dati ->'Utente';
	RAISE INFO 'json utenti %',utenti;
	idutente := utenti -> 'userId';
	RAISE INFO 'idutente %',idutente;

	lineacu :=  _json_dati ->'Linee';
	RAISE INFO 'json lineacu %',lineacu;

	oggetticu :=  _json_dati ->'Oggetti';
	RAISE INFO 'json oggetticu %',oggetticu;

	motivicu :=  _json_dati ->'Motivi';
	RAISE INFO 'json motivicu %',motivicu;

	nucleo :=  _json_dati ->'Nucleo';
	RAISE INFO 'json nucleo %',nucleo;

	datigenerici := _json_dati ->'Dati';
	RAISE INFO 'json datigenerici %',datigenerici;

	asl := _json_dati -> 'Asl';
	RAISE INFO 'json asl %', asl;

	-- STEP 0: INSERIAMO IL RECORD JSON PER LOGO
	INSERT INTO cu_log_json(enteredby, json_cu) values(idutente,_json_dati);
	
	-- chiamo la dbi puntuale per ogni blocco
	-- STEP 1: INSERIAMO IL RECORD IN TICKET PER OTTENERE IL TICKETID
	idcontrollo := (SELECT * from public.cu_insert_anagrafica(anagrafica, idutente));
	-- STEP 2: INSERIAMO LA TECNICA
	output := (SELECT * from public.cu_insert_tecnica(tecnicacu, idcontrollo));

	-- STEP 3: INSERIAMO I DATI DEL CU + linee
	update ticket set problem = datigenerici -> 'note', site_id = (asl ->> 'id')::int, assigned_date  = (datigenerici ->> 'dataInizio')::timestamp without time zone, 
	data_fine_controllo  = (case when data_fine_controllo is not null then (datigenerici ->> 'dataFine')::timestamp without time zone else null end) where ticketid = idcontrollo;
	output := (SELECT * from public.cu_insert_linee(lineacu, idcontrollo, anagrafica ->> 'riferimentoIdNomeTab'));
	
	-- se non si tratta di sorveglianza
	if (tecnicacu ->> 'id')::int <> 5 then
		-- STEP 4: INSERIAMO GLI OGGETTI DEL CU + CAMPI ESTESI
		output :=(SELECT * from public.cu_insert_oggetti(oggetticu, idcontrollo));		
	end if;

	-- STEP 5: INSERIAMO I MOTIVI DEL CU + CAMPI ESTESI + PER CONTO DI
	output := (SELECT * from public.cu_insert_motivi(motivicu, idcontrollo, (tecnicacu ->> 'id')::int));
	
	-- STEP 6: INSERIAMO I COMPONENTI DEL NUCLEO
	output := (SELECT * FROM public.cu_insert_nucleo(nucleo,idcontrollo));
	
    	 return idcontrollo;
END;
$BODY$
   LANGUAGE plpgsql VOLATILE 
  COST 100;
ALTER FUNCTION public.cu_insert_cu_globale(json)
  OWNER TO postgres;

--select * from public.cu_insert_cu_globale('{"Tecnica":{"nome":"Ispezione con la Tecnica di Sorveglianza","id":5},"Linee":[{"codice":"MS.020-MS.020.500-852IT3A401","nome":"PRODOTTI DA FORNO E DI PASTICCERIA, GELATI E PIATTI PRONTI - PRODUZIONE, TRASFORMAZIONE E CONGELAMENTO -> PRODUZIONE DI CIBI PRONTI IN GENERE -> PRODOTTI DA FORNO E DI PASTICCERIA, GELATI E PIATTI PRONTI - PRODUZIONE, TRASFORMAZIONE E CONGELAMENTO->PRODUZIONE DI CIBI PRONTI IN GENERE->PRODUZIONE DI CIBI PRONTI IN GENERE (PRODOTTI DI GASTRONOMIA, DI ROSTICCERIA, DI FRIGGITORIA, ECC.)","id":"192439"}],"Dati":{"note":"ciao, sono una nota","dataInizio":"2021-08-31","dataFine":"2021-10-07"},"Anagrafica":{"partitaIva":"03280191218","riferimentoId":183564,"ragioneSociale":"A PIZZA DO CAFONE DI CERCIELLO CARMINE","riferimentoIdNomeTab":"opu_stabilimento"},"Nucleo":[{"nominativo":"RITO CARMINE GUERRERA","id":1184,"Qualifica":{"nome":"Infermiere","id":"120"},"Struttura":{"nome":"UNITA OPERATIVA COMPLESSA SERVIZIO IGIENE E SANITA PUBBLICA->UOC SISP 2 ","id":"8362"}},{"nominativo":"GERARDO GARGANO","id":452,"Qualifica":{"nome":"T.P.A.L","id":"41"},"Struttura":{"nome":"UNITA OPERATIVA COMPLESSA SERVIZIO IGIENE DEGLI ALIMENTI E DELLA NUTRIZIONE->UOPC SIAN ARIANO IRPINO","id":"8171"}},{"nominativo":"NIGRO CINZIA ","id":10005335,"Qualifica":{"nome":"NAC","id":"113"},"Struttura":{"nome":"","id":"-1"}},{"nominativo":"MICHELE FIORE RUTA","id":10006260,"Qualifica":{"nome":"NAS NA","id":"1020"},"Struttura":{"nome":"","id":"-1"}}],"Utente":{"userId":5885},"Oggetti":[],"Asl":{"nome":"NAPOLI 3 SUD","id":206},"Motivi":[{"idPianoMonitoraggio":0,"CampiEstesi":[],"idAttivita":9295,"nome":"ISPEZIONI CON LA TECNICA DELLA SORVEGLIANZA IN TUTTI I TIPI DI STABILIMENTO  - ATT B5 >> EFFETTUAZIONI DI N. ISPEZIONI CON LA TECNICA DELLA SORVEGLIANZA          ","codiceEvento":"null","PerContoDi":{"nome":"UNITA OPERATIVA COMPLESSA SERVIZIO IGIENE DEGLI ALIMENTI DI ORIGINE ANIMALE->UOV 2 VET B","id":"8484"}}]}'::json)

--select * from ticket where ticketid = 1621442
--select * from tipocontrolloufficialeimprese  where idcontrollo   = 1621437
--select * from linee_attivita_controlli_ufficiali  where id_controllo_ufficiale  = 1621441



  
  
  --select * from public.cu_insert_cu_globale('{"Tecnica":{"nome":"Ispezione con la Tecnica di Sorveglianza","id":5},"Linee":[{"codice":"MS.020-MS.020.500-852IT3A401","nome":"PRODOTTI DA FORNO E DI PASTICCERIA, GELATI E PIATTI PRONTI - PRODUZIONE, TRASFORMAZIONE E CONGELAMENTO -> PRODUZIONE DI CIBI PRONTI IN GENERE -> PRODOTTI DA FORNO E DI PASTICCERIA, GELATI E PIATTI PRONTI - PRODUZIONE, TRASFORMAZIONE E CONGELAMENTO->PRODUZIONE DI CIBI PRONTI IN GENERE->PRODUZIONE DI CIBI PRONTI IN GENERE (PRODOTTI DI GASTRONOMIA, DI ROSTICCERIA, DI FRIGGITORIA, ECC.)","id":"192439"}],"Dati":{"note":"ciao, sono una nota","dataInizio":"2021-08-31","dataFine":"2021-10-07"},"Anagrafica":{"partitaIva":"03280191218","riferimentoId":183564,"ragioneSociale":"A PIZZA DO CAFONE DI CERCIELLO CARMINE","riferimentoIdNomeTab":"opu_stabilimento"},"Nucleo":[{"nominativo":"RITO CARMINE GUERRERA","id":1184,"Qualifica":{"nome":"Infermiere","id":"120"},"Struttura":{"nome":"UNITA OPERATIVA COMPLESSA SERVIZIO IGIENE E SANITA PUBBLICA->UOC SISP 2 ","id":"8362"}},{"nominativo":"GERARDO GARGANO","id":452,"Qualifica":{"nome":"T.P.A.L","id":"41"},"Struttura":{"nome":"UNITA OPERATIVA COMPLESSA SERVIZIO IGIENE DEGLI ALIMENTI E DELLA NUTRIZIONE->UOPC SIAN ARIANO IRPINO","id":"8171"}},{"nominativo":"NIGRO CINZIA ","id":10005335,"Qualifica":{"nome":"NAC","id":"113"},"Struttura":{"nome":"","id":"-1"}},{"nominativo":"MICHELE FIORE RUTA","id":10006260,"Qualifica":{"nome":"NAS NA","id":"1020"},"Struttura":{"nome":"","id":"-1"}}],"Utente":{"userId":5885},"Oggetti":[],"Asl":{"nome":"NAPOLI 3 SUD","id":206},"Motivi":[{"idPianoMonitoraggio":0,"CampiEstesi":[],"idAttivita":9295,"nome":"ISPEZIONI CON LA TECNICA DELLA SORVEGLIANZA IN TUTTI I TIPI DI STABILIMENTO  - ATT B5 >> EFFETTUAZIONI DI N. ISPEZIONI CON LA TECNICA DELLA SORVEGLIANZA          ","codiceEvento":"null","PerContoDi":{"nome":"UNITA OPERATIVA COMPLESSA SERVIZIO IGIENE DEGLI ALIMENTI DI ORIGINE ANIMALE->UOV 2 VET B","id":"8484"}}]}'::json)
  
CREATE TABLE public.cu_log_json
(
  id serial,
  enteredby integer,
  entered timestamp without time zone DEFAULT now(),
  json_cu json
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.cu_log_json
  OWNER TO postgres;

CREATE OR REPLACE FUNCTION public.get_tecniche_by_id_anagrafica(IN _riferimento_id integer, _riferimento_id_nome_tab text)
  RETURNS TABLE(id integer, nome text) AS
$BODY$
DECLARE
	check_linea_moll int;
	check_esistenza_anag int;
	check_esistenza_macello int;
BEGIN 
	-- verifica punti di sbarco 
	check_linea_moll := (select count(*) from ricerche_anagrafiche_old_materializzata  where ( (codice_macroarea = 'MOLLBAN' and codice_aggregazione = 'MOLLBAN' and codice_attivita = 'MOLLBAN') or
				(codice_macroarea = 'MOLLZONE' and codice_aggregazione = 'MOLLZONE' and codice_attivita = 'MOLLZONE'))	and riferimento_id = _riferimento_id and riferimento_id_nome_tab = _riferimento_id_nome_tab);
	-- verifica 
	check_esistenza_macello := (select count(*) from ricerche_anagrafiche_old_materializzata  where path_attivita_completo ilike '%SH MACELLO%' and riferimento_id = _riferimento_id and riferimento_id_nome_tab = _riferimento_id_nome_tab);
				
	-- se non esiste l'anagrafica, siamo nel caso di autorita' competenti
	check_esistenza_anag := (select count(*) from ricerche_anagrafiche_old_materializzata  where riferimento_id = _riferimento_id and riferimento_id_nome_tab = _riferimento_id_nome_tab); 

	if check_esistenza_anag > 0 then -- non si tratta di autorità competenti...	
		if check_linea_moll > 0 then
			RETURN QUERY
				select code, description::text from lookup_tipo_controllo where enabled and code  in (1,21,20,19,18,4,5,3) order by description; -- escono le tecniche BASE + quelle ad hoc
		elseif check_esistenza_macello > 0 then
			RETURN QUERY
				select code, description::text from lookup_tipo_controllo where enabled and code  in (4,5,3,26) order by description; -- escono le tecniche BASE + quella di macellazione
		else 
			RETURN QUERY
				select code, description::text from lookup_tipo_controllo where enabled and code not in (23,2,7,22,1,21,20,19,18,26) order by description;  -- escono solo le tecniche base VALIDE
		end if;
	else
		RETURN QUERY
			select code, description::text from lookup_tipo_controllo where enabled and code in (7,22) order by description;
	end if;
	

END;
$BODY$
  LANGUAGE plpgsql
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_tecniche_by_id_anagrafica(integer, text)
  OWNER TO postgres;
  
  ------------------------------- per conto di ---------------------------------------
  
CREATE OR REPLACE FUNCTION public.get_percontodi_strutture_asl(IN _anno integer, _idasl integer, _idutente integer)
  RETURNS TABLE(id_asl integer, asl text, tipologia text, descrizione text, appartenenza text, id integer) AS
$BODY$
DECLARE

BEGIN 

	
	return query
	SELECT 
	strutt_semplice.id_asl as id_asl_struttura_semplice,
	asl.description::text as asl,
	tipooia.description::text as tipologia,
	strutt_semplice.descrizione::text as descrizione_struttura_semplice, 
        strutt_complessa.descrizione::text as appartenenza, 
        strutt_semplice.id as id_struttura_semplice	
	FROM dpat_strutture_asl strutt_complessa
	LEFT JOIN dpat_strutture_asl strutt_semplice on strutt_semplice.id_padre = strutt_complessa.id and 
                                           strutt_semplice.disabilitato = false and strutt_semplice.stato = 2 and strutt_semplice.enabled
        LEFT JOIN lookup_site_id asl on asl.code = strutt_semplice.id_asl
	LEFT JOIN lookup_tipologia_nodo_oia tipooia ON strutt_complessa.tipologia_struttura = tipooia.code 
	where strutt_complessa.disabilitato = false and  strutt_complessa.tipologia_struttura in(13,14) and strutt_complessa.stato=2 and strutt_complessa.enabled
        and (strutt_complessa.id_asl = _idasl or _idasl is null) 
        and strutt_complessa.id_strumento_calcolo in (select dpat_strumento_calcolo.id 
			                          from dpat_strumento_calcolo 
			                          where (dpat_strumento_calcolo.id_asl = _idasl or _idasl is null) and 
			                                (strutt_complessa.anno = _anno or _anno is null) 
			                          ) 
	order by strutt_complessa.descrizione::text,strutt_semplice.descrizione::text ;

END;
$BODY$
  LANGUAGE plpgsql
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_percontodi_strutture_asl(integer, integer, integer)
  OWNER TO postgres;


CREATE OR REPLACE FUNCTION public.get_percontodi_strutture_poli()
  RETURNS TABLE(id_asl integer, asl text, tipologia text, descrizione text, appartenenza text, id integer) AS
$BODY$
DECLARE

BEGIN 

	
	return query
	SELECT 
	strutt_complessa.id_asl as id_asl_struttura_semplice,
	'' as asl,
	tipooia.description::text as tipologia,
        strutt_complessa.descrizione::text as descrizione_struttura_semplice, 
        strutt_complessa.descrizione_padre::text as appartenenza,
        strutt_complessa.id as id	
	FROM dpat_strutture_asl strutt_complessa
	LEFT JOIN lookup_tipologia_nodo_oia tipooia ON strutt_complessa.tipologia_struttura = tipooia.code 
	where strutt_complessa.disabilitato = false and  strutt_complessa.tipologia_struttura in(40) and strutt_complessa.stato=2 and strutt_complessa.enabled
 
	order by strutt_complessa.descrizione::text;

END;
$BODY$
  LANGUAGE plpgsql
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_percontodi_strutture_poli()
  OWNER TO postgres;


CREATE OR REPLACE FUNCTION public.get_percontodi_strutture(IN _anno integer, _idasl integer, _idutente integer)
  RETURNS TABLE(id_asl integer, asl text, tipologia text, descrizione text, appartenenza text, id integer) AS
$BODY$
DECLARE
	ruolo integer;
	utente_non_asl integer;
BEGIN 
	ruolo := (select role_id from access_ where user_id = _idutente union select role_id from access_ext_ where user_id = _idutente);
	utente_non_asl := (select site_id from access_dati  where user_id  = _idutente);
	-- se sei utente CRR
	if (ruolo in (select role_id from role where role ilike '%polo%')) then
		return query 
			select * from public.get_percontodi_strutture_poli();
	elsif (utente_non_asl = -1 or utente_non_asl is null) then
		return query 
			(select * from public.get_percontodi_strutture_poli()
			union 
			select * from public.get_percontodi_strutture_asl(_anno, _idasl, _idutente) order by 2 desc nulls last);
	else
	    return query select * from public.get_percontodi_strutture_asl(_anno, _idasl, _idutente);
	end if;
	
END;
$BODY$
  LANGUAGE plpgsql
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_percontodi_strutture(integer, integer, integer)
  OWNER TO postgres;
  
 
CREATE EXTENSION unaccent;

  -- ispezioni con motivi  e per conto di nella stessa tabella (prevedere la tecnica in input)
CREATE OR REPLACE FUNCTION public.cu_dettaglio_cu_globale(IN _idcontrollo integer)
  RETURNS integer AS
$BODY$	
DECLARE
	tecnicacu json;
	daticu json;
	anagrafica json;
	utente json;
	asl json;
	motivi json;
	linee json;
	nucleo json;
	finale json;
	id_tecnica integer;
	anno_controllo integer;
	idasl integer;
	rifid integer;
	tipologia_operatore integer;
	rifnometab text;
	lineacontrollo text;
	path_linea text;
	
BEGIN
	-- chiamo la dbi puntuale per ogni blocco
	-- STEP 1: recuperiamo la tecnica es:{"nome":"Ispezione con la Tecnica di Sorveglianza","id":5}
	id_tecnica := (select provvedimenti_prescrittivi  from ticket where ticketid = _idcontrollo); --123586
	anno_controllo := (select date_part('year',assigned_date)::integer from ticket where ticketid = _idcontrollo);
	rifid := (select coalesce(org_id, id_stabilimento, alt_id, id_apiario) from ticket where ticketid = _idcontrollo);
	idasl := (select site_id from ticket where ticketid = _idcontrollo);
	rifnometab := (select case when org_id > 0 then 'organization' 
						when id_stabilimento > 0 then 'opu_stabilimento' 
						when alt_id > 0 then 'sintesis_stabilimento' 
						when id_apiario > 0 then 'apicoltura_imprese' end  from ticket where ticketid = _idcontrollo);
	tipologia_operatore := (select distinct m.tipologia_operatore from ricerche_anagrafiche_old_materializzata m where m.riferimento_id = rifid and m.riferimento_id_nome_tab = rifnometab );
	lineacontrollo := (select codice_linea from linee_attivita_controlli_ufficiali  where  id_controllo_ufficiale  = _idcontrollo and trashed_date is null);

	path_linea := (select path_descrizione from ml8_linee_attivita_nuove_materializzata where codice = lineacontrollo and livello = 3 limit 1);
	-- costruzione dei json
	tecnicacu := (select json_object_agg(nome,descrizione) from (select 'nome' as nome, description as descrizione from lookup_tipo_controllo where code = id_tecnica 
							union select 'id' as nome,  id_tecnica::text) a); 
        --tecnicacu := (select json_object_agg('Tecnica', tecnicacu));
	raise info 'json tecnica ricostruito%', tecnicacu;

	daticu := (select json_object_agg(nome,descrizione) from (select 'note' as nome, problem as descrizione from ticket where ticketid = _idcontrollo
								  union select 'dataInizio' as nome,  assigned_date::text from ticket where ticketid = _idcontrollo 
								  union select 'dataFine' as nome,  coalesce(data_chiusura::text,'') from ticket where ticketid = _idcontrollo ) b);

	--daticu := (select json_object_agg('Dati', daticu));
	raise info 'json daticu ricostruito%', daticu;

	anagrafica := (select json_object_agg(nome,descrizione) from (select 'partitaIva' as nome, trim(partita_iva) as descrizione from ricerche_anagrafiche_old_materializzata  where riferimento_id = rifid and riferimento_id_nome_tab = rifnometab
								  union select 'riferimentoId' as nome,  rifid::text
								  union select 'riferimentoIdNomeTab' as nome,  rifnometab::text 
								  union select 'ragioneSociale' as nome, ragione_sociale as descrizione from ricerche_anagrafiche_old_materializzata  where riferimento_id = rifid and riferimento_id_nome_tab = rifnometab) c);
	--anagrafica := (select json_object_agg('Anagrafica', anagrafica));
	raise info 'json anagrafica ricostruito%', anagrafica;

	utente := (select json_object_agg(nome,descrizione) from (select 'userId' as nome, enteredby as descrizione from ticket where ticketid = _idcontrollo) d); 
	--utente := (select json_object_agg('Utente', utente));
	raise info 'json utente ricostruito%', utente;

	asl := (select json_object_agg(nome,descrizione) from (select 'nome' as nome,  description as descrizione from lookup_site_id where code= idasl
								union select 'id' as nome, idasl::text) e); 
	--asl := (select json_object_agg('Asl', asl));
	raise info 'json asl ricostruito%', asl;
				  
	
	--"Linee":[{"codice":"MS.020-MS.020.500-852IT3A401","nome":"path completo","id":"192439"}],
	linee := (SELECT array_to_json(array_agg(row_to_json(t))) FROM (select lineacontrollo as codice, path_linea as nome, (select id_linea_attivita from linee_attivita_controlli_ufficiali  where trashed_date is null and id_controllo_ufficiale = _idcontrollo) as id 
										from ticket where ticketid = _idcontrollo) t);
	--linee := (select json_object_agg('Linee', linee));
	raise info 'json linee ricostruito%', linee;


	nucleo := (SELECT array_to_json(array_agg(row_to_json(t))) FROM (select a.nominativo as nominativo, a.id_componente as id, 
			(select json_object_agg(nome,descrizione) from (select 'nome' as nome, a.qualifica as descrizione 
					        union select 'id' as nome, a.id_qualifica::text) e) as "Qualifica",
			(select json_object_agg(nome,descrizione) from (select 'nome' as nome, a.struttura as descrizione 
					         union select 'id' as nome, a.id_struttura_semplice::text) e) as "Struttura"
		  from (					       
			select d.nominativo, c.id_componente, ac.role_id as id_qualifica, d.qualifica, d.id_struttura_semplice, concat_ws('->', d.desc_strutt_complessa, d.desc_strutt_semplice) as struttura 
			from cu_nucleo c
			left join public.dpat_get_nominativi(null,anno_controllo,null,null,null,null,null,null) d on d.id_anagrafica_nominativo = c.id_componente
			left join access_ ac on ac.user_id = c.id_componente -- è giusto?
			where id_controllo_ufficiale = _idcontrollo) a
			) t
		);

	--nucleo := (select json_object_agg('Nucleo', nucleo));
	raise info 'json nucleo ricostruito %', nucleo;

	motivi := (SELECT array_to_json(array_agg(row_to_json(t))) FROM (select a.pianomonitoraggio as "idPianoMonitoraggio", a.idattivita as "idAttivita", coalesce(a.motivo_piano, a.motivo_ispezione) as nome,
									(select json_object_agg(nome,descrizione) from (select 'nome' as nome, a.nomepercontodi as descrizione 
															union select 'id' as nome, a.id_percontodi::text) e) as "PerContoDi"
									from (					       
									select t.tipoispezione as idattivita, t.pianomonitoraggio, p.descrizione_piano as motivo_piano, d.descrizione_tipo_ispezione as motivo_ispezione,  coalesce (d.codice_evento, p.codice_evento) as codice_evento,
									t.id_unita_operativa as id_percontodi, concat_ws('->', s.descrizione_struttura_complessa, s.descrizione_struttura_semplice) as nomepercontodi
									from tipocontrolloufficialeimprese t
									left join public.get_motivi_cu(tipologia_operatore,anno_controllo,'{ATTIVITA-ISPEZIONE,ATTIVITA-SORVEGLIANZA, ATTIVITA-AUDIT}'::text[]) d on d.id_tipo_ispezione = t.tipoispezione and t.tipoispezione > 0 and t.enabled
									left join public.get_motivi_cu(tipologia_operatore,anno_controllo,'{PIANO}'::text[]) p on p.id_piano = t.pianomonitoraggio and t.pianomonitoraggio > 0 and t.pianomonitoraggio <> 89 and t.enabled
									left join public.dpat_get_strutture(null,anno_controllo,null,null,null,null,null,null,null,null,null) s on s.id_struttura_semplice = t.id_unita_operativa and t.enabled and t.id_unita_operativa > 0
									where t.idcontrollo = _idcontrollo and t.enabled and (t.tipoispezione > 0 or t.pianomonitoraggio > 0) ) a
									) t
		);

	--motivi := (select json_object_agg('Motivi', motivi));
	raise info 'json motivi ricostruito %', motivi;
	
	--raise info 'cosa succede %', (select concat_ws(' ', '"Tecnica":', tecnicacu, ',"Dati":', daticu, ',"Anagrafica":', anagrafica, ',"Utente":',utente, ',"Asl":', asl, ',"Linee":', linee, ',"Nucleo":', nucleo, ',"Motivi":', motivi)); --- provare a concatenare senza passare prt il json_object_agg

	--raise info 'json da convertire %', unaccent(concat('{',
	--(select concat_ws(' ', '"Tecnica":', tecnicacu, ',"Dati":', daticu, ',"Anagrafica":', anagrafica, ',"Utente":',utente, ',"Asl":', asl, ',"Linee":', linee, ',"Nucleo":', nucleo, ',"Motivi":', motivi)),'}'))::json;
	
	finale := (select unaccent(concat('{',
	(select concat_ws(' ', '"Tecnica":', tecnicacu, ',"Dati":', daticu, ',"Anagrafica":', anagrafica, ',"Utente":',utente, ',"Asl":', asl, ',"Linee":', linee, ',"Nucleo":', nucleo, ',"Motivi":', motivi)),'}'))::json);

	raise info 'json finale: %', finale;

    	return _idcontrollo;
END;
$BODY$
   LANGUAGE plpgsql  
  COST 100;
ALTER FUNCTION public.cu_dettaglio_cu_globale(integer)
  OWNER TO postgres;
