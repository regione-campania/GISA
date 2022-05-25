insert into permission(category_id,permission,permission_view,permission_add,permission_edit,permission_delete,description,level,enabled,active,viewpoints)
values(29,'prototipo_anag_noscia',true,true,true,true,'permesso per cavaliere no scia',40,true,true,false);
 
 
 
--SCRIPT DA MANDARE AD ALESSANDRO 
--DIRE AD ALESSANDRO

/*linearizza i paths */

create or replace view MASTER_LIST_DENORM
as
select 
a.id as id_macroarea, a.codice_sezione as codice_sezione_macroarea, a.codice_norma as codice_norma_macroarea,a.norma as macroarea_norma, a.macroarea as macroarea,     
b.id as id_aggregazione, b.codice_attivita as codice_attivita_aggregazione, b.aggregazione as aggregazione, b.id_flusso_originale as id_flusso_originale_aggregazione,
c.id as id_linea_attivita, c.codice_prodotto_specie as codice_prodotto_specie_linea_attivita, c.linea_attivita as linea_attivita, c.tipo as tipo_linea_attivita, 
c.scheda_supplementare as scheda_supplementare_linea_attivita, c.note as note_linea_attivita, c.mapping_ateco as mapping_ateco_linea_attivita, c.codice_univoco as codice_univoco_linea_attivita, c.codice_nazionale_richiesto as codice_nazionale_richiesto_linea_attivita, c.chi_inserisce_pratica as chi_inserisce_pratica_linea_attivita
,c.id_lookup_tipo_attivita

from master_list_macroarea  a join  master_list_aggregazione  b
on a.id = b.id_macroarea  
join master_list_linea_attivita c on c.id_aggregazione = b.id;

--BISOGNA TRASFORMARE IL CAMPO TIPO DI MASTER_LIST_lINEA_ATTIVITA in una FK integer verso una lookup del carattere (PERMANENTE/TEMPORANEA)
--(DIRE AD ALESSANDRO)
 
 
/*ATTENZIONE ORA I CAMPI ESTESI POSSONO ESSERE AGGANCIATI A TUTTI I LIVELLI */
/*qui inserisco campi estesi su un po' di linee_attivita */

select * from campi_estesi_templates_v2  
insert into campi_estesi_templates_v2(id_linea,template_json) 
	select id,
	 to_json(
		( 
		 '[ '
			
			 '{ "type" : "checkbox" , "name" : "SPOSATO", "label" : "SPOSATO"} '
			|| ',{ "type" : "radio" , "name" : "SEX" , "scelte" : [{"valore" : "m", "label" : "maschio"},{"valore" : "f", "label" : "femmina", "default" : "true"}] }'
		  ||' ]' 
		)::json 

	  )
	 from   master_list_aggregazione  where aggregazione ilike '%LACTARIUM%'


/*ne aggiungo altri sulla macroarea */
insert into campi_estesi_templates_v2(id_linea,template_json) 
	
	select master_list_macroarea.id,
	 to_json(
		( 
		 '[ '
			  '{ "type" : "select" , "name" : "NUM. BOX", "label" : "NUM.BOX",  "options" : [{"valore" : "1", "label" : "uno" , "type" : "option"},{"valore" : "2", "label" : "due","type" : "option", "default" : "true"},{"valore" : "3", "label" : "tre","type" : "option"}]  , "props" : [{"required" : "true"}] } '
			|| ' ,{ "type" : "text",  "name" : "RAGIONE SOCIALE", "label" : "RAGIONE SOCIALE"  ,  "attrs" : [{"placeholder" : "RAGIONE SOCIALE"}] , "props" : [{"required" : "true"}] } '
			
		  ||' ]' 
		)::json 

	  )
	 from   master_list_macroarea where macroarea ilike '%altro%' or macroarea ilike '%igiene urbana%'

/*AVVISA ALESSANDRO CH EORA LOOKUP TIPO IMPRESA SOCIETA E LOOKUP TIPO IMPRESA SONO FUSE in una NUOVA TABELLA (LOOKUP_TIPO_IMPRESA_SOC)*/
--script per fondere 
-- Table: public.lookup_opu_tipo_impresa_societa

-- DROP TABLE public.LOOKUP_TIPO_IMPRESA_SOC;

CREATE TABLE public.LOOKUP_TIPO_IMPRESA_SOC
(
  code  serial primary key,
  description character varying(250) NOT NULL,  
  enabled boolean DEFAULT true      
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.LOOKUP_TIPO_IMPRESA_SOC
  OWNER TO postgres;
GRANT ALL ON TABLE public.LOOKUP_TIPO_IMPRESA_SOC TO postgres;
GRANT SELECT ON TABLE public.LOOKUP_TIPO_IMPRESA_SOC TO usr_ro;
GRANT SELECT ON TABLE public.LOOKUP_TIPO_IMPRESA_SOC TO report;

delete from LOOKUP_TIPO_IMPRESA_SOC;
/*la popolo , concatenando le descrizioni delle 2 lookup, nel caso in cui non siano identiche (quando sono identiche e' un dummy value per la vecchia lookup_opu_tipo_impresa_societa quindi non e' utile )*/ 
insert into LOOKUP_TIPO_IMPRESA_SOC (description)
select concat_ws(' - ',a.description, CASE WHEN a.description != b.description THEN b.description ELSE '' END) 
   from lookup_opu_tipo_impresa_societa  b join lookup_opu_tipo_impresa a on b.code_lookup_opu_tipo_impresa = a.code
--PER LA GENERAZIONE STAMPA SCHEDA .. DA CONTINUARE
--PER LA SCHEDA OCCORRE AVERE UN'ENTRY  che denormalizza tutte le nuove tabelle

select * from imprese imp join rel_imprese_soggetti_fisici risf on risf.id_impresa = imp.id
join soggetti_fisici sogg on sogg.id = risf.id_soggetto_fisico
join rel_soggetti_fisici_opu_indirizzo rsfop on rsfop.id_soggetto_fisico = sogg.id
join opu_indirizzo indrappleg on indrappleg.id = rsfop.id_indirizzo
join lookup_toponimi top0 on top0.code = indrappleg.toponimo
join comuni1 com0 on comuni1.id = indrappleg.comune
join lookup_tipo_impresa_soc ltis on ltis.code = imp.id_tipo_impresa_societa
join rel_imprese_opu_indirizzo rioi on rioi.id_impresa = imp.id 
join opu_indirizzo ind on ind.id = rioi.id_indirizzo
join lookup_toponimi top1 on top1.code = ind.toponimo
join comuni1 com1 on com1.id = ind.comune
join rel_imprese_stabilimenti ris on ris.id_impresa = imp.id 
join stabilimenti stabs on stabs.id = ris.id_stabilimento
join rel_stabilimenti_opu_indirizzo rsoi on rsoi.id_stabilimento = stabs.id
join opu_indirizzo indstab on indstab.id = rsoi.id_indirizzo
join lookup_toponimi top2 on top2.code = indstab.toponimo
join comuni1 com2 on com2.id = indstab.comune

select * from lookup_tipo_scheda_operatore  
insert into lookup_tipo_scheda_operatore values((select max(code)+1 from lookup_tipo_scheda_operatore),'Anagrafica NoScia',false,0,true,'Scheda Operatore',false,'Scheda Anagrafica NoScia');

delete from scheda_operatore_metadati where id = (select code from lookup_tipo_scheda_operatore where description ilike 'Anagrafica NoScia');
insert into scheda_operatore_metadati values((select max(id)+1 from scheda_operatore_metadati),(select code from lookup_tipo_scheda_operatore where description ilike 'Anagrafica NoScia'),null,'TIPO IMPRESA SOCIETA',true,'capitolo',null,null,null,'a',0,null);
insert into scheda_operatore_metadati values((select max(id)+1 from scheda_operatore_metadati),(select code from lookup_tipo_scheda_operatore where description ilike 'Anagrafica NoScia'),null,'DATI IMPRESA',true,null,'distinct()',null,null,'a',0,null);

--E DI CONSEGUENZA VANNO MODIFICATE LE DBI CHE LAVORANO CON IMPRESA PER GESTIRE ANCHE QUESTO CAMPO
--DBI DI INSERIMENTO VERSIONE NUOVA:

-- DROP FUNCTION public.anagrafica_inserisci_impresa(character varying, character varying, character varying, integer, integer,timestamp without time zone);

CREATE OR REPLACE FUNCTION public.anagrafica_inserisci_impresa(
    _ragione_sociale character varying,
    _codfisc character varying,
    _piva character varying,
    _id_tipo_impresa_societa integer,
    _utente integer,
    _data_arrivo_pec timestamp without time zone)
  RETURNS integer AS
$BODY$

  DECLARE ret_id integer;
 
  BEGIN
    IF _ragione_sociale='' THEN
	_ragione_sociale=NULL;
    END IF;
    
    INSERT INTO imprese(ragione_sociale, codfisc, piva, id_tipo_impresa_societa,enteredby,data_arrivo_pec)
		VALUES (_ragione_sociale, _codfisc, _piva, _id_tipo_impresa_societa,_utente,_data_arrivo_pec)
		RETURNING id into ret_id;
  
  RETURN ret_id;
  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.anagrafica_inserisci_impresa(character varying, character varying, character varying, integer, integer, timestamp without time zone)
  OWNER TO postgres;


--DBI RICERCA (OBSOLETA)
-- Function: public.anagrafica_cerca_impresa(integer, character varying, character varying, character varying, integer, date, date,date,date)

-- DROP FUNCTION public.anagrafica_cerca_impresa(integer, character varying, character varying, character varying, integer, date, date,date,date);

CREATE OR REPLACE FUNCTION public.anagrafica_cerca_impresa(
    IN _id integer DEFAULT NULL::integer,
    IN _ragione_sociale character varying DEFAULT NULL::character varying,
    IN _codfisc character varying DEFAULT NULL::character varying,
    IN _piva character varying DEFAULT NULL::character varying,
    IN _id_tipo_impresa_societa integer DEFAULT NULL::integer,
    IN _data_inserimento_1 date DEFAULT NULL::date,
    IN _data_inserimento_2 date DEFAULT NULL::date,
    IN _data_arrivo_pec_1 date DEFAULT NULL::date, 
    IN _data_arrivo_pec_2 date DEFAULT NULL::date)
  RETURNS TABLE(id integer, ragione_sociale character varying, codfisc character varying, piva character varying, id_tipo_impresa_societa integer, descrizione_tipo_impresa_societa text, data_inserimento timestamp without time zone, data_arrivo_pec timestamp without time zone) AS
$BODY$
DECLARE
BEGIN
FOR id, ragione_sociale, codfisc, piva, id_tipo_impresa_societa, descrizione_tipo_impresa_societa, data_inserimento, data_arrivo_pec
in 
    select imprese.id, imprese.ragione_sociale, imprese.codfisc, imprese.piva, imprese.id_tipo_impresa_societa, lookup_tipo_impresa_soc.description, imprese.data_inserimento , imprese.data_arrivo_pec
    from imprese
    left join lookup_tipo_impresa_soc on lookup_tipo_impresa_soc.code=imprese.id_tipo_impresa_societa
    where 
    (_id is null or imprese.id = _id) 
    and (_ragione_sociale is null or imprese.ragione_sociale ilike _ragione_sociale || '%')
    and (_codfisc is null or imprese.codfisc = _codfisc)
    and (_piva is null or imprese.piva = _piva)
    and (_id_tipo_impresa_societa is null or imprese.id_tipo_impresa_societa = _id_tipo_impresa_societa)
    and (_data_inserimento_1 is null or imprese.data_inserimento >= to_timestamp( to_char(_data_inserimento_1,'YYYY-MM-DD') || ' 00:00:00.000000','YYYY-MM-DD HH24:MI:SS.US'))
    and (_data_inserimento_2 is null or imprese.data_inserimento <= to_timestamp(to_char(_data_inserimento_2,'YYYY-MM-DD') || ' 23:59:59.999999','YYYY-MM-DD HH24:MI:SS.US'))
    and (_data_arrivo_pec_1 is null or imprese.data_arrivo_pec >= to_timestamp(to_char(_data_arrivo_pec_1,'YYYY-MM-DD') || ' 00:00:00.000000','YYYY-MM-DD HH24:MI:SS.US'))
    and (_data_arrivo_pec_2 is null or imprese.data_arrivo_pec <= to_timestamp(to_char(_data_arrivo_pec_2,'YYYY-MM-DD') || ' 23:59:59.999999','YYYY-MM-DD HH24:MI:SS.US'))
    and imprese.data_scadenza is null and imprese.data_cancellazione is null
    
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.anagrafica_cerca_impresa(integer, character varying, character varying, character varying, integer, date, date,date,date)
  OWNER TO postgres;


--OBSOLETA
--avvisa alessandro che sulla tabella impresa ho aggiunto il campo DATA ARRIVO PEC 
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
alter table imprese add data_arrivo_pec timestamp without time zone;


--INTEGRAZIONE VISTA MATERIALIZZATE (DA CAMBIARE)
/*
select * from
impresa imp join --impresa
rel_imprese_soggetti_fisici relimpsogg on imp.id = relimpsogg.id_impresa left join 
opu_soggetto_fisico soggfis on relimpsogg.id_soggetto_fisico = soggfis.id left join --impresa -> soggetto fisico (rapp legale)
rel_soggetti_fisici_indirizzi relsoggfisind on soggfis.id = relsoggfisind.id_soggetto_fisico left join
opu_indirizzo indsoggettofis on relsoggfisind.id_indirizzo = indsoggettofis.id left join --soggetto fisico -> (rapp legale) suo indirizzo
comuni1 comunesoggfis on indsoggettofis.comune = comunesoggfis.id left join 
lookup_toponimi toponimosoggfis on indsoggettofis.toponimo = toponimosoggfis.code join
rel_imprese_stabilimenti relimpstab on imp.id = relimpstab.id_impresa join 
stabilimenti stab on stab.id = relimpstab.id_stabilimento join --impresa -> stabilimento
rel_stabilimenti_indirizzi relstabind on stab.id = relstabind.id_stabilimento left join
opu_indirizzo indstab on relstabind.id_indirizzo = indstab.id left join
comuni1 comunestab on indstab.comune = comunestab.id left join  
lookup_toponimi toponimoindstab on indstab.toponimo = toponimoindstab.code left join -- stabilimento -> indirizzo stabilimento
rel_imprese_indirizzi relindsedelegale on imp.id = relindsedelegale.id_impresa left join
opu_indirizzo indsedelegale on relindsedelegale.id_indirizzo = indsedelegale.id left join
comuni1 comunesedelegale on indsedelegale.comune = comunesedelegale.id left join
lookup_toponimi toponimisedelegale on indsedelegale.toponimo = toponimisedelegale.code  join-->stabilimento -> indirizzo sede legale
rel_stabilimenti_linee_attivita relstablinee on stab.id = relstablinee.id_stabilimento join
master_list_macroarea mlmacroarea on relstablinee.id_macroarea = mlmacroarea.id left join
master_list_aggregazione mlaggr on relstablinee.id_aggregazione = mlaggr.id left   join
master_list_linea_attivita mlatt on relstablinee.id_attivita = mlatt.id

where mlaggr.id_flusso_origine = NOSCIA
*/

--PROBABILE QUERY PER PRENDERE I NOSCIA DAL VECCHIO MODELLO ORGANIZATION 
/*select * from ricerche_anagrafiche_old_materializzata
where riferimento_id in 
(
select org_id--distinct c.description as desc_norma_RICHIESTEANAG 
from organization o join opu_lookup_norme_master_list_rel_tipologia_organzation b on o.tipologia = b.tipologia_organization
join  opu_lookup_norme_master_list c on c.code = b.id_opu_lookup_norme_master_list where 
o.org_id <> 0 AND o.tipologia <> 0 AND o.trashed_date IS NULL AND o.import_opu IS NOT TRUE AND ((o.tipologia <> ALL (ARRAY[6, 11, 211, 1, 97, 151, 802, 152, 10, 20, 2, 800, 801, 3])) OR o.tipologia = 3 AND o.direct_bill = true)
--and o.org_id = 1160467
)
--la tipologia di organization e' agganciata su opu_lookup_norme_master_list_rel_tipologia_organzation che mappa verso il code di opu_lookup_norme_master_list
--e questa diventa la norma in ricerche anagrafiche

select * from public_functions.anagrafica_inserisci_osa('{"soggetto_fisico":{"codice_fiscale":"123412321211111f","cognome":"amante","indirizzo":{"cap":"80142","descr_indirizzo":"II trav s.anna","comune":"5279","civico":"5","toponimo":"22"},"sesso":"F","telefono1":"342381231","data_nascita":"28\/04\/2099","nome":"walter","telefono":"342381231","comune_nascita":"NAPOLI","email":"test@test.it"}}'::json,'{"impresa":{"id_tipo_impresa_societa":"1","codice_fiscale":"12312312312","data_arrivo_pec":"28\/04\/1988","indirizzo":{"cap":"8022","descr_indirizzo":"Prova Cellole 14","comune":"5279","nazione":"Italia","civico":"54","provincia":"Napoli","toponimo":"1"},"ragione_sociale":"1","partita_iva":"12312312312"}}'::json,'{"stabilimento":{"indirizzo":{"cap":"80110","descr_indirizzo":"via test","comune":"1","civico":"5","provincia":"napoli","toponimo":"22"},"denominazione":"stabciccio","id_asl":"201"}}'::json,'{"linee_attivita":[{"idmacroarea":9,"idaggregazione":20033,"idattivita":40090,"numeroregistrazione":"","telefono":"","fax":"","cun":""}]}'::json,1951)
select * from imprese
 -- Function: public_functions.anagrafica_inserisci_osa(json, json, json, json, integer)
select * from public_functions.anagrafica_cerca_rel_impresa_stabilimento(NULL,57,NULL,NULL)

select * from public_functions.anagrafica_cerca_rel_impresa_stabilimento(57)


select * from public_functions.anagrafica_cerca_stabilimento(10)*/