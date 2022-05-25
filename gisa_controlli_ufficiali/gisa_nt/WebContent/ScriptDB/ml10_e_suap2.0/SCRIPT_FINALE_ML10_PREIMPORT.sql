-- script predisposizione ML10 senza infasamento DATI

--1 creazione tabella masterlist10 e import da csv
---------------------------------------------------------------- RILASCIATI IN UFFICIALE --------------------------------------------------------------------------------------
CREATE TABLE public.masterlist10
(
  NORMA	text,
  codice_norma	text,
  CODICE_SEZIONE text,	
  MACROAREA	 text,
  CODICE_ATTIVITA	 text,
  CODICE_PRODOTTO_SPECIE	 text,
  AGGREGAZIONE	 text,
  LINEA_attivita text,	
  CAT_ex_ante	 text,
  codice_univoco	 text,
  attivita_specifica_attributi  text,	
  CARATTERIZZAZIONE_SPECIFICA	 text,
  DETTAGLIO_SPECIALIZZAZIONE_PRODUTTIVA	 text,
  PROCEDURA_AMMINISTRATIVA	 text,
  CHI_INSERISCE_PRATICA	 text,
  CODICE_NAZIONALE_RICHIESTO	 text,
  SCHEDA_SUPPLEMENTARE	 text,
  Tipo	 text,
  sede_mobile text,	
  sede_fissa	 text,
  Apicoltura	 text,
  registrati	 text,
  riconosciuti	 text,
  gestiti_sintesis	 text,
  propagati_BDU	 text,
  propagati_VAM	 text,
  Categorizzabili	 text,
  note text

)
WITH (
  OIDS=FALSE
);

-- import da csv pgadmin latin1 - delimiter ; 
----------------------------------------------------------------FINO A QUI RILASCIATI IN UFFICIALE --------------------------------------------------------------------------------------

--2) alter table

-- alter table 
alter table master_list_macroarea add column rev integer;
alter table master_list_aggregazione  add column rev integer;
alter table master_list_linea_attivita add column rev integer;
alter table master_list_macroarea add column trashed_date timestamp;
alter table master_list_aggregazione  add column trashed_date timestamp;
alter table master_list_linea_attivita add column trashed_date timestamp;
alter table master_list_macroarea add column entered timestamp;
alter table master_list_aggregazione  add column entered timestamp;
alter table master_list_linea_attivita add column entered timestamp;
alter table master_list_macroarea add column modified timestamp;
alter table master_list_aggregazione  add column modified timestamp;
alter table master_list_linea_attivita add column modified timestamp;
alter table master_list_macroarea add column enteredby integer;
alter table master_list_aggregazione  add column enteredby integer;
alter table master_list_linea_attivita add column enteredby integer;
alter table master_list_macroarea add column modifiedby integer;
alter table master_list_aggregazione  add column modifiedby integer;
alter table master_list_flag_linee_attivita add column rev integer;
alter table master_list_flag_linee_attivita  add column trashed timestamp;
alter table master_list_flag_linee_attivita add column entered timestamp;
alter table master_list_flag_linee_attivita add column modified timestamp;
alter table master_list_flag_linee_attivita  add column enteredby integer;
alter table master_list_flag_linee_attivita  add column modifiedby integer;
alter table master_list_flag_linee_attivita  add column id_linea integer;
alter table master_list_flag_linee_attivita  add column mercato boolean;
--alter table master_list_flag_linee_attivita  add column operatore_mercato boolean;
alter table master_list_flag_linee_attivita  add column visibilita_asl boolean;
alter table master_list_flag_linee_attivita  add column visibilita_regione boolean;
alter table ml8_linee_attivita_nuove_materializzata add column rev integer;
alter table ml8_linee_attivita_nuove_materializzata add column codice_norma text;

--3) -- recupero codice_nazionale_richiesto e flag riconoscibili
select 'update masterlist10 set codice_nazionale_richiesto  = '''||codice_nazionale_richiesto||''' where codice_univoco = '''||codice_univoco||''';', codice_univoco
from master_list_linea_attivita  where codice_nazionale_richiesto <> '';

--4) rimuovere le dipendenze  '%ml8_linee_attivita_nuove %' e riscrittura dbi

--dbi_get_controlli_ufficiali_su_linee_produttive --ok
--get_esistenza_allevamento --ok
--get_esistenza_allevamento --ok
--opu_can_pnaa --ok
--proponi_candidati_mapping --> ok
--suap_query_richiesta --ok
--suap_query_validazione_scia_richiesta_perlinee --ok
--cu_dati_linea --ok
--suap_insert_attivita_apicoltura_da_richiesta --ok


-- Function: public.dbi_get_controlli_ufficiali_su_linee_produttive(integer)
-- DROP FUNCTION public.dbi_get_controlli_ufficiali_su_linee_produttive(integer);

CREATE OR REPLACE FUNCTION public.dbi_get_controlli_ufficiali_su_linee_produttive(IN idstabilimento integer)
  RETURNS TABLE(idcontrollo integer, id_stabilimento integer, id_rel_stab_lp_out integer, id_linea_master_list_out integer, enabled_out boolean, descrizione_out text, id_norma_out integer, id_stato_out integer) AS
$BODY$
DECLARE
	
	 	
BEGIN
		FOR
		idcontrollo,id_stabilimento,id_rel_stab_lp_out ,id_linea_master_list_out ,enabled_out ,descrizione_out, id_norma_out, id_stato_out 
		in

		select distinct cu.ticketid,cu.id_stabilimento,r.id as id_rel_stab_lp ,r.id_linea_produttiva as id_linea_master_list,r.enabled,path_descrizione, ll.id_norma, r.stato

from opu_relazione_stabilimento_linee_produttive r 
left join linee_attivita_controlli_ufficiali on r.id = linee_attivita_controlli_ufficiali.id_linea_attivita and linee_attivita_controlli_ufficiali.trashed_date is null
left join ticket cu on cu.ticketid = linee_attivita_controlli_ufficiali.id_controllo_ufficiale and cu.id_stabilimento = r.id_stabilimento and cu.trashed_date is null
left join ml8_linee_attivita_nuove_materializzata ll on ll.id_nuova_linea_attivita=r.id_linea_produttiva
where r.id_stabilimento =idstabilimento and r.enabled 
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
    
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.dbi_get_controlli_ufficiali_su_linee_produttive(integer)
  OWNER TO postgres;

-- Function: public.get_esistenza_allevamento(text, text, text, text, text)

-- DROP FUNCTION public.get_esistenza_allevamento(text, text, text, text, text);

CREATE OR REPLACE FUNCTION public.get_esistenza_allevamento(
    codazienda text,
    codfiscale text,
    codspecie text,
    codorientamento text,
    descorientamento text)
  RETURNS boolean AS
$BODY$
   DECLARE
esiste boolean;
output_orgid integer;
output_stabid integer;

BEGIN

esiste:= false;

output_orgid := (select org_id from organization where trashed_date is null and account_number ilike codAzienda and codice_fiscale_rappresentante ilike codFiscale and specie_allevamento = codSpecie::integer and orientamento_prod ilike descOrientamento limit 1);

if (output_orgid>0) THEN
esiste=true;
raise info 'ESISTE IN ORGANIZATION ORG_ID %', output_orgid;
END IF;

if (esiste is false) THEN

output_stabid:= (
select s.id 
FROM opu_stabilimento s 
LEFT JOIN opu_operatore o on o.id = s.id_operatore
LEFT JOIN opu_rel_operatore_soggetto_fisico orosf on orosf.id_operatore = o.id
LEFT JOIN opu_soggetto_fisico sogg on sogg.id = orosf.id_soggetto_fisico
LEFT JOIN opu_relazione_stabilimento_linee_produttive rel on rel.id_stabilimento = s.id and rel.enabled
LEFT join ml8_linee_attivita_nuove_materializzata l on l.id_nuova_linea_attivita = rel.id_linea_produttiva
LEFT join ml8_linee_attivita_nuove_materializzata l_terzo on l_terzo.id_nuova_linea_attivita = l.id_attivita
LEFT join ml8_linee_attivita_nuove_materializzata l_quarto on l_quarto.id_nuova_linea_attivita::text = split_part(l.path_id, ';', 5)

WHERE 
(rel.codice_nazionale ilike codAzienda or rel.codice_ufficiale_esistente ilike codAzienda)
and sogg.codice_fiscale ilike codFiscale
and l_terzo.decodifica_specie_bdn = codSpecie
and (l_quarto.id_nuova_linea_attivita is null or l_quarto.decodifica_codice_orientamento_bdn = 'M')
and s.trashed_date is null
and o.trashed_date is null
and sogg.trashed_date is null
limit 1
);

END IF;

if (output_stabid>0) THEN
esiste=true;
raise info 'ESISTE IN OPU ID_STAB %', output_stabid;
END IF;
	
	RETURN esiste;

END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_esistenza_allevamento(text, text, text, text, text)
  OWNER TO postgres;

-- Function: public.get_esistenza_allevamento(text, text, text, text, text, text)

-- DROP FUNCTION public.get_esistenza_allevamento(text, text, text, text, text, text);

CREATE OR REPLACE FUNCTION public.get_esistenza_allevamento(
    codazienda text,
    codfiscale text,
    codspecie text,
    codorientamento text,
    descorientamento text,
    descstato text)
  RETURNS boolean AS
$BODY$
   DECLARE
esiste boolean;
output_orgid integer;
output_stabid integer;

BEGIN

esiste:= false;

output_orgid := (
select org_id from organization 
where trashed_date is null 
and account_number ilike codAzienda 
and codice_fiscale_rappresentante ilike codFiscale 
and specie_allevamento = codSpecie::integer 
and orientamento_prod ilike descOrientamento 
and((descstato='cessato' and date2 is not null) or (descstato<>'cessato'))
and((descstato='attivo' and date2 is null) or (descstato<>'attivo'))
limit 1);

if (output_orgid>0) THEN
esiste=true;
raise info 'ESISTE IN ORGANIZATION ORG_ID %', output_orgid;
END IF;

if (esiste is false) THEN

output_stabid:= (
select s.id 
FROM opu_stabilimento s 
LEFT JOIN opu_operatore o on o.id = s.id_operatore
LEFT JOIN opu_rel_operatore_soggetto_fisico orosf on orosf.id_operatore = o.id
LEFT JOIN opu_soggetto_fisico sogg on sogg.id = orosf.id_soggetto_fisico
LEFT JOIN opu_relazione_stabilimento_linee_produttive rel on rel.id_stabilimento = s.id and rel.enabled
LEFT join ml8_linee_attivita_nuove_materializzata l on l.id_nuova_linea_attivita = rel.id_linea_produttiva
LEFT join ml8_linee_attivita_nuove_materializzata l_terzo on l_terzo.id_nuova_linea_attivita = l.id_attivita
LEFT join ml8_linee_attivita_nuove_materializzata l_quarto on l_quarto.id_nuova_linea_attivita::text = split_part(l.path_id, ';', 5)

WHERE 
(rel.codice_nazionale ilike codAzienda or rel.codice_ufficiale_esistente ilike codAzienda)
and sogg.codice_fiscale ilike codFiscale
and l_terzo.decodifica_specie_bdn = codSpecie
and (l_quarto.id_nuova_linea_attivita is null or l_quarto.decodifica_codice_orientamento_bdn = 'M')
and s.trashed_date is null
and o.trashed_date is null
and sogg.trashed_date is null
and((descstato='cessato' and s.stato=4) or (descstato<>'cessato'))
and((descstato='attivo' and s.stato<>4) or (descstato<>'attivo'))
limit 1
);

END IF;

if (output_stabid>0) THEN
esiste=true;
raise info 'ESISTE IN OPU ID_STAB %', output_stabid;
END IF;
	
	RETURN esiste;

END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_esistenza_allevamento(text, text, text, text, text, text)
  OWNER TO postgres;

-- Function: public.suap_query_richiesta(integer, integer)

-- DROP FUNCTION public.suap_query_richiesta(integer, integer);

CREATE OR REPLACE FUNCTION public.suap_query_richiesta(
    IN idtiporichiesta integer,
    IN idrichiesta integer)
  RETURNS TABLE(tipo_richiesta text, tipo_impresa text, tipo_societa text, id_richiesta integer, ragione_sociale text, partita_iva text, codice_fiscale_impresa text, comune_sede_legale text, istat_legale text, cap_sede_legale text, prov_sede_legale text, domicilio_digitale text, cf_rapp_sede_legale text, nome_rapp_sede_legale text, cognome_rapp_sede_legale text, indirizzo_rapp_sede_legale text, comune_stab text, indirizzo_stab text, cap_stab text, prov_stab text, tipo_attivita text, carattere text, data_inizio_attivita timestamp without time zone, data_fine_attivita timestamp without time zone, macroarea text, aggregazione text, linea_attivita text, indirizzo_sede_legale text, istat_operativo character varying, id_macroarea text, id_aggregazione text, id_nuova_linea_attivita text, lat_stab double precision, long_stab double precision, sesso_rappr character, data_nascita_rappr timestamp without time zone, nazione_nascita_rappr text, comune_nascita_rappr text, nazione_residenza_rappr character varying, provincia_residenza_rappr character, comune_residenza_rappr character varying, cap_residenza_rappr character) AS
$BODY$
BEGIN
FOR Tipo_richiesta, tipo_impresa,tipo_societa, id_richiesta, ragione_sociale, partita_iva, codice_fiscale_impresa,comune_sede_legale, istat_legale, cap_sede_legale, prov_sede_legale,domicilio_digitale, cf_rapp_sede_legale, nome_rapp_sede_legale, cognome_rapp_sede_legale, indirizzo_rapp_sede_legale,comune_stab, indirizzo_stab, cap_stab, prov_stab,tipo_attivita ,carattere, data_inizio_attivita,data_fine_attivita,macroarea,aggregazione,linea_attivita,indirizzo_sede_legale, istat_operativo, id_macroarea, id_aggregazione, id_nuova_linea_attivita, lat_stab, long_stab

	,sesso_rappr, data_nascita_rappr, nazione_nascita_rappr,comune_nascita_rappr, nazione_residenza_rappr, provincia_residenza_rappr, comune_residenza_rappr, cap_residenza_rappr
		in
--select * from ml8_linee_attivita_nuove_materializzata limit 1
--select * from master_list_suap limit 1  
--select * from suap_ric_scia_operatori_denormalizzati_view
select distinct op.description as Tipo_richiesta, s.tipo_impresa,s.tipo_societa, s.id_opu_operatore as id_richiesta, s.ragione_sociale, s.partita_iva, s.codice_fiscale_impresa,s.comune_sede_legale, s.istat_legale, s.cap_sede_legale, s.prov_sede_legale,s.domicilio_digitale, s.cf_rapp_sede_legale, s.nome_rapp_sede_legale, s.cognome_rapp_sede_legale, s.indirizzo_rapp_sede_legale
	,CASE WHEN s.comune_stab = 'n.d' THEN NULL END as comune_stab, s.indirizzo_stab, s.cap_stab, s.prov_stab,lta.description as tipo_attivita ,ltc.description as carattere, s.data_inizio_attivita,s.data_fine_attivita,s.macroarea,s.aggregazione,s.linea_attivita 
	,s.indirizzo_sede_legale
	,CASE WHEN s.istat_operativo::integer = -1 THEN NULL END as istat_operativo, linee.id_macroarea,linee.id_aggregazione, linee.id_nuova_linea_attivita
	,s.lat_stab
	,s.long_stab
	
	,osf.sesso
	,osf.data_nascita, 
	CASE WHEN osf.provenienza_estera = true THEN 'altro' ELSE 'Italia' END AS nazione_nascita_rappr
	,osf.comune_nascita
	,oi.nazione
	,lp.description --provincia residenza rapp legale
	,com.nome
	,oi.cap
 from  suap_ric_scia_operatori_denormalizzati_view s 
 left join suap_ric_scia_soggetto_fisico osf on s.cf_rapp_sede_legale = osf.codice_fiscale
 left join opu_indirizzo oi on osf.indirizzo_id = oi.id
 left join comuni1 com on oi.comune = com.id
 left join lookup_province lp on lp.code::text = oi.provincia
 join opu_lookup_tipologia_attivita lta on lta.code = s.stab_id_attivita 
 join opu_lookup_tipologia_carattere ltc on ltc.code = s.stab_id_carattere 
 join suap_lookup_tipo_richiesta op on op.code = s.id_tipo_richiesta
 left join ml8_linee_attivita_nuove_materializzata linee on s.id_linea_attivita_stab = linee.id_nuova_linea_attivita
where 1=1

AND (
    (idRichiesta>-1 AND id_opu_operatore = idRichiesta)
     OR (idRichiesta=-1)
  )

AND (
    (idTipoRichiesta>-1 AND id_tipo_richiesta = idTipoRichiesta)
     OR (idTipoRichiesta=-1)
  )

    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.suap_query_richiesta(integer, integer)
  OWNER TO postgres;

-- Function: public.suap_query_validazione_scia_richiesta_perlinee(integer, integer)

-- DROP FUNCTION public.suap_query_validazione_scia_richiesta_perlinee(integer, integer);

CREATE OR REPLACE FUNCTION public.suap_query_validazione_scia_richiesta_perlinee(
    IN idstabilimento integer,
    IN idrichiestasuap integer)
  RETURNS TABLE(numero_registrazione text, codice_nazionale text, macroarea text, aggregazione text, attivita text, id_macroarea text, id_aggregazione text, id_nuova_linea_attivita text) AS
$BODY$
BEGIN
FOR  numero_registrazione, codice_nazionale, macroarea , aggregazione , attivita ,id_macroarea , id_aggregazione , id_nuova_linea_attivita 

IN


select distinct o.linea_numero_registrazione, o.linea_codice_nazionale, replace(o.macroarea,'->','-'), replace(o.aggregazione,'->','|'), replace(o.attivita,'->','|') , ola.id_macroarea, ola.id_aggregazione, o.id_linea_attivita_stab


from opu_operatori_denormalizzati_view o 
 left join opu_soggetto_fisico osf on o.cf_rapp_sede_legale = osf.codice_fiscale
 left join opu_indirizzo oi on osf.indirizzo_id = oi.id
 left join comuni1 com on oi.comune = com.id
 left join lookup_province lp on oi.provincia = lp.code::text
join suap_opu_relazione_richiesta_id_opu_rel_stab_lp rel on o.id_linea_attivita=rel.id_opu_rel_stab_lp
join suap_ric_scia_operatore ric on ric.id = rel.id_suap_ric_scia_operatore
join suap_lookup_tipo_richiesta tr on tr.code = ric.id_tipo_richiesta
join ml8_linee_attivita_nuove_materializzata ola on ola.id_nuova_linea_attivita = o.id_linea_attivita_stab
--join opu_relazione_stabilimento_linee_produttive srsr on o.id_stabilimento = srsr.id_stabilimento
--where rel.id_suap_ric_scia_operatore=2801

and 1=1
--select * from opu_operatori_denormalizzati_view order by id_opu_operatore desc  
AND (
    (idStabilimento>-1 AND o.id_stabilimento = idstabilimento)
     OR (idStabilimento=-1)
  )

    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.suap_query_validazione_scia_richiesta_perlinee(integer, integer)
  OWNER TO postgres;

-- Function: public_functions.cu_dati_linea(integer)

-- DROP FUNCTION public_functions.cu_dati_linea(integer);

CREATE OR REPLACE FUNCTION public_functions.cu_dati_linea(IN id_controllo integer)
  RETURNS TABLE("Linea sottoposta a controllo" text) AS
$BODY$
DECLARE
r RECORD;	
BEGIN
FOR "Linea sottoposta a controllo"
in
SELECT 
cod.description || ' : ' || cod.short_description || ' <br/> ' || la.categoria || ' : ' || la.linea_attivita as linea_sottoposta 
FROM la_imprese_linee_attivita i  
left join ml8_linee_attivita_nuove_materializzata opu on opu.id_attivita = i.id_attivita_masterlist and opu.id_norma =1, linee_attivita_controlli_ufficiali lacu, la_rel_ateco_attivita rel, la_linee_attivita la, lookup_codistat cod  
WHERE i.id=lacu.id_linea_attivita  
AND i.id_rel_ateco_attivita=rel.id  
AND rel.id_linee_attivita=la.id  
AND rel.id_lookup_codistat=cod.code  
and(
  (id_controllo>-1 and lacu.id_controllo_ufficiale = id_controllo)
 or (id_controllo=-1)
  )

    UNION

  SELECT linea_attivita_stabilimenti_soa || ' <br/> ' ||  linea_attivita_stabilimenti_soa_desc FROM linee_attivita_controlli_ufficiali_stab_soa where 1=1
  and(
  (id_controllo>-1 and id_controllo_ufficiale = id_controllo)
 or (id_controllo=-1)
  )

  
 LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.cu_dati_linea(integer)
  OWNER TO postgres;

-- Function: public_functions.suap_insert_attivita_apicoltura_da_richiesta(integer, integer)

-- DROP FUNCTION public_functions.suap_insert_attivita_apicoltura_da_richiesta(integer, integer);

CREATE OR REPLACE FUNCTION public_functions.suap_insert_attivita_apicoltura_da_richiesta(
    id_richiesta_suap integer,
    idutenteasl integer)
  RETURNS boolean AS
$BODY$
DECLARE
proprietarioapicoltore persona ;
partita_iva_ric text;
sedelegale integer ;
tipoimpresa integer;
idAsl integer;
idimpresaApicoltura integer;

idrelsedelegale integer;
idrelsf integer;

verificaesistenzaApicoltore integer;

nome_soggetto_fisico text;
cognome_soggetto_fisico text;
index_spazio integer;
array_ragione_sociale text[];
ragione_sociale_ text;

BEGIN

/*A PARTIRE DALLA RICHIESTA RECUPERO/ INSERISCO IL SOGGETTOFISICO*/

proprietarioapicoltore:=(select   public_functions.suap_insert_soggetto_fisico(id_richiesta_suap, -1, idutenteasl))  ;

raise notice 'INSERIMENTO SOGGETTO FISICO : %',proprietarioapicoltore;


select partita_iva into partita_iva_ric from suap_ric_scia_operatore  where id = id_richiesta_suap ;

raise notice 'partita_iva : %',partita_iva_ric;

/*RECUPERO L'INDIRIZZO DELLA SEDE LEGALE A PARTIRE DALLA RICHIESTA :
	SE IL TIPO DI IMPRESA è INDIVIDUALE LA SEDE LEGALE è UGUALE ALLA RESIDENZA DEL PROPRIETARIO
	ALTRIMENTI LA RECUPERO DALLA SEDE LEGALE DELLA RICHIESA
*/
select tipo_impresa into tipoimpresa from suap_ric_scia_operatore where id = id_richiesta_suap;

raise notice '--> RECUPERO SEDE LEGALE IN BASE AL TIPO DI IMPRESA: %',tipoimpresa;

if tipoimpresa=1
then
sedelegale:=proprietarioapicoltore.id_indirizzo ;
else
sedelegale:=(select id_indirizzo from suap_ric_scia_operatore where id =id_richiesta_suap);
end if ;

raise info 'RECUPERO ID ASL';

idAsl:=(select codiceistatasl::int from comuni1 where id = (select comune from opu_indirizzo where id = sedelegale));


select count(*) from apicoltura_imprese where codice_fiscale_impresa ilike partita_iva_ric and stato !=4 and stato !=3 and stato != 8 and trashed_date is null into verificaesistenzaApicoltore ;

raise notice  'VERIFICA ESISTENZA IN APICOLTURA IMPRESA - num_record: %   - CF: %'  , verificaesistenzaApicoltore, proprietarioapicoltore.cf ;

if verificaesistenzaApicoltore>0

then
raise info 'GIA'' ESISTE: NESSUN INSERIMENTO IN APICOLTURA_IMPRESA';
return false;
else
/*
INSERISCO IN APICOLTURA_IMPRESE
*/
raise info 'INSERIMENTO IN APICOLTURA_IMPRESA';
idimpresaApicoltura:=(select max(id)+1 from apicoltura_imprese);
insert into apicoltura_imprese ( id,
ragione_sociale, codice_fiscale_impresa,partita_iva,tipo_attivita_apicoltura,data_inizio,id_asl,stato,flag_scia,id_richiesta_suap,
domicilio_digitale,
enteredby,modifiedby,entered, modified,flag_laboratorio_annesso
)
(select idimpresaApicoltura,
op.ragione_sociale,op.partita_iva,op.partita_iva,1,st.data_ricezione_richiesta,idAsl,5,true,id_richiesta_suap,op.domicilio_digitale,
idutenteasl,idutenteasl,current_timestamp,current_timestamp,case when path_descrizione ilike '%con laboratorio%' then true else false end
from suap_ric_scia_operatore op
join suap_ric_scia_stabilimento st on op.id=st.id_operatore
join suap_ric_scia_relazione_stabilimento_linee_produttive rlp on rlp.id_stabilimento = st.id
join ml8_linee_attivita_nuove_materializzata lp on lp.id_nuova_linea_attivita = rlp.id_linea_produttiva
where op.id =id_richiesta_suap and st.tipo_attivita=3
);

 
 
/*
INSERISCO IN APICOLTURA_IMPRESE
*/

idrelsedelegale:=(select max(id)+1 from apicoltura_relazione_imprese_sede_legale);

insert into apicoltura_relazione_imprese_sede_legale(id,id_apicoltura_imprese,id_indirizzo,tipologia_sede,stato_sede,enabled,modified_by)
values(idrelsedelegale,idimpresaApicoltura,sedelegale,1,1,true,idutenteasl);



/*
INSERISCO IN OPU_soggetto_fisico
*/

ragione_sociale_ := trim((select op.ragione_sociale from suap_ric_scia_operatore op where id=id_richiesta_suap));
index_spazio := position(' ' in ragione_sociale_);

if(index_spazio > 1) then 
	array_ragione_sociale := regexp_split_to_array(ragione_sociale_, ' ');
	cognome_soggetto_fisico := substring(ragione_sociale_ from 1 for index_spazio);
	nome_soggetto_fisico := substring(ragione_sociale_ from index_spazio for length(ragione_sociale_));
else
	cognome_soggetto_fisico := 'DITTA';
	nome_soggetto_fisico := ragione_sociale_;
end if;

insert into opu_soggetto_fisico (cognome,nome,
codice_fiscale,enteredby,modifiedby,indirizzo_id)
 (
select 
cognome_soggetto_fisico,nome_soggetto_fisico,
op.partita_iva,idutenteasl,idutenteasl,sedelegale
from suap_ric_scia_operatore op
join suap_ric_scia_stabilimento st on op.id=st.id_operatore
join suap_ric_scia_relazione_stabilimento_linee_produttive rlp on rlp.id_stabilimento = st.id
join ml8_linee_attivita_nuove_materializzata lp on lp.id_nuova_linea_attivita = rlp.id_linea_produttiva
where op.id =id_richiesta_suap and st.tipo_attivita=3
) returning id into proprietarioapicoltore.id;

idrelsf:=(select max(id)+1 from apicoltura_rel_impresa_soggetto_fisico);
insert into apicoltura_rel_impresa_soggetto_fisico(id,id_apicoltura_imprese,id_soggetto_fisico,tipo_soggetto_fisico,enabled,data_inizio)
values(idrelsf,idimpresaApicoltura,proprietarioapicoltore.id,1,true,current_timestamp);


return true ;
end if;

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.suap_insert_attivita_apicoltura_da_richiesta(integer, integer)
  OWNER TO postgres;

  -- Function: public.proponi_candidati_mapping(integer)

-- DROP FUNCTION public.proponi_candidati_mapping(integer);

CREATE OR REPLACE FUNCTION public.proponi_candidati_mapping(IN orgid integer)
  RETURNS TABLE(idvecchialinea_out integer, idnuovalinea integer, id_macroareanuovalinea integer, macroareanuovalinea text, id_aggregazionenuovalinea integer, aggregazionenuovalinea text, id_attivitanuova integer, attivitanuova text, id_norma integer, ranking integer, idvecchialineaoriginale_out integer) AS
$BODY$
declare
	rec1 record; 
	idvecchialinea integer;
	idvecchialineaOriginale integer;
begin
	for idvecchialinea,idvecchialineaOriginale in select id_vecchia_linea,id_vecchia_originale from get_vecchie_linee_da_organization(orgId) --per ognuna delle linee vecchie possibili, per quella tipologia
	loop
		--ottengo tutti i mapping già effettuati con i ranking

		for rec1 in select la.id_nuova_linea_attivita as nuovalinea,
				   la.macroarea,la.id_macroarea,la.aggregazione,la.id_aggregazione
				   ,la.attivita,la.id_attivita,la.id_norma, kb.ranking 
			from knowledge_based_mapping kb  
			join ml8_linee_attivita_nuove_materializzata la on kb.id_nuova_linea::integer = la.id_nuova_linea_attivita 
			where kb.id_vecchia_linea = idvecchialinea order by kb.ranking desc
		loop
			idvecchialinea_out := idvecchialinea;
			idnuovalinea := rec1.nuovalinea; 
			id_macroareanuovalinea := rec1.id_macroarea;
			macroareanuovalinea:= rec1.macroarea;
			id_aggregazionenuovalinea := rec1.id_aggregazione;
			aggregazionenuovalinea := rec1.aggregazione;
			id_attivitanuova := rec1.id_attivita;
			attivitanuova := rec1.attivita;
			id_norma := rec1.id_norma;
			ranking := rec1.ranking;
			idvecchialineaOriginale_out:=idvecchialineaOriginale;
			return next;

		end loop;
	end loop;
	
end;

$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.proponi_candidati_mapping(integer)
  OWNER TO postgres;


CREATE OR REPLACE FUNCTION public.gestisci_mapping(
    org_idi integer,
    stabidi integer,
    tipologiai integer,
    id_vecchia_lineai integer,
    id_linea_produttiva_nuova integer,
    id_norma_nuova integer,
    inserisci_entry_perstab boolean)
  RETURNS void AS
$BODY$

declare
	tTemp integer;
	rec3 record;
	tId integer;
	
begin

	--ognuna di queste è un mapping da cui possiamo ottenere informazioni
	--controllo per prima cosa se non esisteva già entry per questo mapping in knowledge_based_mapping
	select count(*) into tTemp from knowledge_based_mapping  where id_vecchia_linea = id_vecchia_lineaI and id_nuova_linea::integer = id_linea_produttiva_nuova;

	if tTemp = 0 then --se non c'era già, aggiungo l'entry prima in knowledge_based_mapping
		select * into rec3 from ml8_linee_attivita_nuove_materializzata  where id_nuova_linea_attivita = id_linea_produttiva_nuova; --per le info su macroarea etc
					
		--raise info 'non esisteva ancora entry di mapping per nuova linea %, % , % , %',rec2.id_linea_produttiva, rec3.macroarea,rec3.aggregazione,rec3.attivita;
					
		insert into knowledge_based_mapping(id_tipologia_operatore,id_vecchia_linea,id_nuova_linea,id_norma,macroarea,
										id_macroarea,aggregazione,id_aggregazione,attivita
										,id_attivita,entered,enteredby,enabled,ranking,note,base_knowledge) 

						values(tipologiaI,id_vecchia_lineaI,id_linea_produttiva_nuova,id_norma_nuova,
						rec3.macroarea,
						rec3.id_macroarea,
						rec3.aggregazione
						,rec3.id_aggregazione
						,rec3.attivita
						,rec3.id_attivita
						,current_timestamp
						,6567
						,true
						,1 -- rank inizializzato a 1
						,null
						,false --non è base knowledge 
					);
					
					--poi nel ranking...
					--recupero id entry appena inserita
					
		select id into tId from knowledge_based_mapping where id_vecchia_linea = id_vecchia_lineaI and id_nuova_linea::integer = id_linea_produttiva_nuova;
		--raise info 'inserita nuova riga (id %) in knowledge_based_mapping per mapping %->%',tId,id_vecchia_lineaI,id_linea_produttiva_nuova;
		 
		 


	else	--allora aggiorno il rank
		select id into tId from knowledge_based_mapping where id_vecchia_linea = id_vecchia_lineaI and id_nuova_linea::integer = id_linea_produttiva_nuova;
		--raise info 'trovata riga esistente (id %) in knowledge_based_mapping per mapping %->%',tId,id_vecchia_lineaI, id_linea_produttiva_nuova;
		update knowledge_based_mapping set ranking = ranking +1 where id = tId;
		
		--raise info 'aggiornato ranking per id di knowledge based mapping %',tId;
	end if;
				
	--in ogni caso devo agganciare orgid e stabid alla nuova entry di mapping(o a quella che già esisteva ed e' stata incrementata)
	--se il flag inserisci_entry_perstab è true
	if inserisci_entry_perstab = true
	then			
		insert into org_importati_to_mapping values(org_idI,stabidI, tId);
		--raise info 'inserito entry in tabella orgid->mapping id %',tId;	
	end if;
	

end;

$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.gestisci_mapping(integer, integer, integer, integer, integer, integer, boolean)
  OWNER TO postgres;
------------------------------------------------------------------------------------------------------------------FUNZIONI
 drop table master_list_livelli_aggiuntivi;
 drop table master_list_livelli_aggiuntivi_values;
 drop table master_list_allegati_procedure_relazione;  
 drop view master_list_da_condividere_con_sup;  
 --drop table master_list_sk_elenco;  
drop view master_list_suap_allegati_linee_attivita;
drop view opu_campi_estesi_lda_view;
drop view ml8_linee_attivita_nuove;
drop table ml8_master_list; 

update scheda_operatore_metadati set sql_origine = replace(sql_origine, 'ml8_linee_attivita_nuove ', 'ml8_linee_attivita_nuove_materializzata ') where sql_origine ilike '%ml8_linee_attivita_nuove %';

--5)INTERVENTI SQL MASTER LIST 10 IMPORT 

-- CREAZIONE VIEW PER ML10
-- drop view master_list_view 
CREATE OR REPLACE VIEW master_list_view AS
SELECT master_list_macroarea.id as id_nuova_linea_attivita,
         true as enabled,
          master_list_macroarea.id as id_macroarea,
           null::integer  as id_aggregazione,
          null::integer  as id_attivita,
          master_list_macroarea.codice_sezione as  codice_macroarea,
          '' as codice_aggregazione,
          '' as codice_attivita,
           master_list_macroarea.macroarea,
           '' as aggregazione,
           '' as attivita,
           master_list_macroarea.id_norma,
           master_list_macroarea.norma,
           norme.codice_norma,
	   master_list_macroarea.macroarea as descrizione,
	   1 as livello,
           -1 as id_padre,
           '-1'::character varying(1000) AS path_id,
           master_list_macroarea.macroarea::character varying(1000) AS path_desc,
           master_list_macroarea.codice_sezione as codice,
           master_list_macroarea.codice_sezione::character varying(1000) AS path_codice
           FROM master_list_macroarea  
           left join opu_lookup_norme_master_list norme on norme.code = master_list_macroarea.id_norma
          WHERE trashed_date is null and rev = 10
        UNION ALL
         SELECT t.id as id_nuova_linea_attivita ,true as enabled, t.id_macroarea as id_macroarea,
          t.id as id_aggregazione,
          null::integer as id_attivita,
           rt.codice_sezione as codice_macroarea,
           t.codice_attivita as codice_aggregazione,
           '' as codice_attivita,
           rt.macroarea,
           t.aggregazione,
           '' as attivita,
           rt.id_norma,
            rt.norma,
            norme2.codice_norma,
            t.aggregazione as descrizione,
            2 as livello, 
            t.id_macroarea as id_padre, 
            (((rt.id::text || ';'::text) || t.id))::character varying(1000) AS path_id,
            (((rt.macroarea::text || '->'::text) || t.aggregazione))::character varying(1000) AS path_desc,
            t.codice_attivita as codice,
           (((rt.codice_sezione::text || '->'::text) || t.codice_attivita))::character varying(1000) AS path_codice
           FROM master_list_aggregazione t
             JOIN master_list_macroarea rt ON rt.id = t.id_macroarea
            left join opu_lookup_norme_master_list norme2 on norme2.code = rt.id_norma
	where t.trashed_date is null and rt.trashed_date is null and rt.rev=10 and t.rev=10
          UNION ALL
          SELECT a.id as id_nuova_linea_attivita, true as enabled, 
          rt3.id as id_macroarea,
           rt2.id as id_aggregazione, 
           a.id as id_attivita,
           rt3.codice_sezione as codice_macroarea,
           rt2.codice_attivita as codice_aggregazione,
           a.codice_prodotto_specie as codice_attivita,
		rt3.macroarea,
		rt2.aggregazione,
           a.linea_attivita as attivita,
           rt3.id_norma,
           norme3.codice_norma,
           rt3.norma,
           a.linea_attivita as descrizione,
           3 as livello, 
           a.id_aggregazione as id_padre, 
           (((rt3.id::text || ';'::text) ||(rt2.id::text || ';'::text) || a.id))::character varying(1000) AS path_id,
           (((rt3.macroarea::text || '->'::text) ||(rt2.aggregazione::text || '->'::text) || a.linea_attivita))::character varying(1000) AS path_desc,
           concat_ws('-',rt3.codice_sezione, rt2.codice_attivita, a.codice_prodotto_specie) as codice,
           (((rt3.codice_sezione::text || '->'::text) ||(rt2.codice_attivita::text || '->'::text) || a.codice_prodotto_specie))::character varying(1000) AS path_codice
        FROM master_list_linea_attivita a
          JOIN master_list_aggregazione rt2 ON rt2.id = a.id_aggregazione
            JOIN master_list_macroarea rt3 ON rt3.id = rt2.id_macroarea
     left join opu_lookup_norme_master_list norme3 on norme3.code = rt3.id_norma
            where a.trashed_date is null and rt2.trashed_date is null and rt3.trashed_date is null and a.rev=10 and rt2.rev=10 and rt3.rev=10;


CREATE OR REPLACE FUNCTION public.insert_into_ml_materializzata(IN _id_linea integer default null::integer)
  RETURNS integer AS
$BODY$
DECLARE

BEGIN

insert into ml8_linee_attivita_nuove_materializzata (
 id_nuova_linea_attivita, enabled, id_macroarea, id_aggregazione, id_attivita, codice_macroarea, codice_aggregazione, codice_attivita, macroarea, aggregazione,
 attivita, id_norma, norma, codice_norma, descrizione, livello, id_padre, path_id, path_descrizione, codice, path_codice, rev
) select id_nuova_linea_attivita, enabled, id_macroarea, id_aggregazione, id_attivita, codice_macroarea, codice_aggregazione, codice_attivita, macroarea, aggregazione,
 attivita, id_norma, norma, codice_norma, descrizione, livello, id_padre, path_id, path_desc, codice, path_codice, 10 as rev from master_list_view 
 where 1=1
 and (_id_linea is null or id_nuova_linea_attivita = _id_linea);

       return 1;


 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.insert_into_ml_materializzata(integer)
  OWNER TO postgres;

--select * from public.insert_into_ml_materializzata(40499) 
-- refresh_materializzata masterlist
CREATE OR REPLACE FUNCTION public.refresh_ml_materializzata(IN _id_linea integer default null::integer)
  RETURNS integer AS
$BODY$
DECLARE
BEGIN

delete from ml8_linee_attivita_nuove_materializzata where 1=1 
-- solo le nuove linee 
and id_nuova_linea_attivita in (select id_nuova_linea_attivita from master_list_view)
and (null is null or id_nuova_linea_attivita = null);

insert into ml8_linee_attivita_nuove_materializzata (
 id_nuova_linea_attivita, enabled, id_macroarea, id_aggregazione, id_attivita, codice_macroarea, codice_aggregazione, codice_attivita, macroarea, aggregazione,
 attivita, id_norma, norma, descrizione, livello, id_padre, path_id, path_descrizione, codice, path_codice,rev
) select id_nuova_linea_attivita, enabled, id_macroarea, id_aggregazione, id_attivita, codice_macroarea, codice_aggregazione, codice_attivita, macroarea, aggregazione,
 attivita, id_norma, norma, descrizione, livello, id_padre, path_id, path_desc, codice, path_codice, 10 as rev from master_list_view 
 where 1=1
 and (_id_linea is null or id_nuova_linea_attivita = _id_linea);

return 1;

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.refresh_ml_materializzata(integer)
  OWNER TO postgres;

CREATE OR REPLACE FUNCTION public.import_master_list(IN _rev integer)
  RETURNS TABLE(_norma text, _codice_norma text, _codice_sezione text, _macroarea text, _codice_attivita text, _codice_prodotto_specie text, _aggregazione text, _linea_attivita text, _codice_univoco text, _procedura_amministrativa text, _chi_inserisce_pratica text, _codice_nazionale_richiesto text, _scheda_supplementare text, _tipo text, _mobile text, _fisso text, _apicoltura text, _registrati text, _riconosciuti text, _gestiti_sintesis text, _propagati_bdu text, _propagati_vam text, _categorizzabili text, _no_scia text, _cat_ex_ante text) AS
$BODY$
DECLARE

tot integer;
curr_index integer;
_id_macroarea integer;
_id_aggregazione integer;
_id_linea integer;

BEGIN

tot := 0;
curr_index := 0;

for
  _norma , --ok
  _codice_norma , --ok
  _codice_sezione , --ok
  _macroarea , --ok
  _codice_attivita , --ok
  _codice_prodotto_specie , --ok
  _aggregazione , --ok
  _linea_attivita , --ok
  _codice_univoco ,--ok
  _procedura_amministrativa , --ok (diventa nota in linea_attivita)
  _chi_inserisce_pratica ,
  _codice_nazionale_richiesto , --ok
  _scheda_supplementare , --ok
  _tipo , --ok
  _mobile , --ok
  _fisso , --ok
  _apicoltura ,--ok
  _registrati ,--ok
  _riconosciuti , --ok
  _gestiti_sintesis , --ok
  _propagati_bdu ,--ok
  _propagati_vam ,--ok
  _categorizzabili , --ok
   _no_scia, 
  _cat_ex_ante --ok,
in
select 
  norma ,
  codice_norma ,
  codice_sezione ,
  macroarea ,
  codice_attivita ,
  codice_prodotto_specie ,
  aggregazione ,
  linea_attivita ,
  codice_univoco ,
  procedura_amministrativa ,
  chi_inserisce_pratica ,
  codice_nazionale_richiesto ,
  scheda_supplementare ,
  tipo ,
  sede_mobile ,
  sede_fissa ,
  apicoltura ,
  registrati ,
  riconosciuti ,
  gestiti_sintesis ,
  propagati_bdu ,
  propagati_vam ,
  categorizzabili  ,
  no_scia,
  cat_ex_ante from masterlist10 where 1=1

loop

curr_index := curr_index+1;
 raise info '--------------------------------------------------------------------------------------------------------';
 raise info '------------------------------------- [IMPORT IN CORSO] %-------------------------------------', curr_index;
 raise info '--------------------------------------------------------------------------------------------------------';


-- inserimento macroarea
--insert into master_list_macroarea (codice_sezione, codice_norma, norma, macroarea, entered, enteredby, rev)  values (_codice_sezione, _codice_norma, _norma,_macroarea,now(),6567,_rev) returning id into id_macroarea; 

SELECT id into _id_macroarea FROM master_list_macroarea where rev = _rev AND codice_sezione = _codice_sezione AND id_norma = (select code from opu_lookup_norme_master_list where trim(codice_norma) ilike trim(_codice_norma) and enabled);

IF _id_macroarea IS NULL OR _id_macroarea <=0 THEN

insert into master_list_macroarea (codice_sezione, codice_norma, norma, id_norma, macroarea, entered, enteredby, rev)  values (_codice_sezione, _codice_norma, _norma, 
(select code from opu_lookup_norme_master_list where trim(codice_norma) ilike trim(_codice_norma) and enabled), _macroarea,now(),6567,_rev) returning id into _id_macroarea;

END IF;

raise info 'id macroarea: %', _id_macroarea;

-- inserimento aggregazione

SELECT id into _id_aggregazione FROM master_list_aggregazione where rev = _rev AND codice_attivita = _codice_attivita AND id_macroarea = _id_macroarea;

IF _id_aggregazione IS NULL OR _id_aggregazione <=0 THEN

insert into master_list_aggregazione(codice_attivita, aggregazione, id_macroarea,entered, enteredby, rev)  values (_codice_attivita, _aggregazione, _id_macroarea, now(),6567,_rev) returning id into _id_aggregazione ;

END IF;

raise info 'id aggregazione: %', _id_aggregazione;

-- inserimento linea
insert into master_list_linea_attivita (codice_prodotto_specie, linea_attivita, tipo, scheda_supplementare, note, codice_univoco, codice_nazionale_richiesto, id_aggregazione,entered, enteredby, rev) 
values (_codice_prodotto_specie, _linea_attivita, _tipo, _scheda_supplementare, _procedura_amministrativa, _codice_univoco, _codice_nazionale_richiesto, _id_aggregazione, now(),6567,_rev ) returning id into _id_linea ;

 raise info 'id linea: %', _id_linea;

-- saltiamo insert per allegati
-- saltiamo insert per livelli aggiuntivi
-- inserimento flag manca la definizione dell'operatore al mercato e no scia - default false
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam, categorizzabili, no_scia, categoria_rischio_default, entered, enteredby, rev,id_linea, 
mercato,operatore_mercato) values 
(concat_ws('-', _codice_sezione, _codice_attivita, _codice_prodotto_specie), 
case when _mobile='x' then true 
else false end, 
case when _fisso='x' then true 
else false end,
case when _apicoltura='x' then true 
else false end,
case when _registrati='x' then true 
else false end,
case when _riconosciuti='x' then true 
else false end,
case when _gestiti_sintesis='x' then true 
else false end,
case when _propagati_bdu='x' then true 
else false end,
case when _propagati_vam='x' then true 
else false end,
case when _categorizzabili='x' then true 
else false end,
case when _no_scia ='x' then true 
else false end,
case when _cat_ex_ante='1' then 1
 when _cat_ex_ante='2' then 2
 when _cat_ex_ante='3' then 3
 when _cat_ex_ante='4' then 4
 when _cat_ex_ante='5' then 5
else null end,
now(),6567,_rev,_id_linea,
case when concat_ws('-', _codice_sezione, _codice_attivita, _codice_prodotto_specie)  = 'MS.B-MS.B00-MS.B00.300' or concat_ws('-', _codice_sezione, _codice_attivita, _codice_prodotto_specie) = 'MS.B-MS.B80-MS.B80.600' then true end,-- new 07/12
case when concat_ws('-', _codice_sezione, _codice_attivita, _codice_prodotto_specie)  = 'COM-COMING-OPMERC' then true end-- new 07/12
);

   
end loop;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.import_master_list(integer)
  OWNER TO postgres;


--5) CANCELLA RECORD FITTIZIO
delete from masterlist10 where norma='NORMA';

--6)----- script BS per no scia ----------------------------------------CRITICAL
alter table masterlist10 add column no_scia text;

insert into masterlist10
select norme.description as norma, 
norme.codice_norma,
 ml8.codice_macroarea as codice_sezione, 
 ml8.macroarea, 
 ml8.codice_aggregazione as codice_attivita, 
 split_part( ml8.codice_attivita::text, '-'::text, 3) as codice_prodotto_specie, 
 ml8.aggregazione,
  ml8.attivita as linea_attivita,
  '' as cat_ex_ante, 
  ml8.codice as codice_univoco,
   '' as attivita_specifica_attributi, '' as caratterizzazione_specifica, '' as dettaglio_specializzazione_produttiva, 'No-SCIA' as procedura_amministrativa, '' as chi_inserisce_pratica, '' as codice_nazionale_richiesto, '' as scheda_supplementare, '' as tipo, case when mobile then 'x' else '' end as sede_mobile
, case when fisso then 'x' else '' end as sede_fissa, case when apicoltura then 'x' else '' end as apicoltura , case when registrabili then 'x' else '' end as registrati, case when riconoscibili then 'x' else '' end as riconosciuti, case when sintesis then 'x' else '' end as gestiti_sintesis, case when bdu then 'x' else '' end as propagati_bdu, case when vam then 'x' else '' end as propagati_vam, case when categorizzabili then 'x' else '' end as categorizzabili, '' as note, case when no_scia then 'x' else '' end as no_scia
 from ml8_linee_attivita_nuove_materializzata ml8
 JOIN opu_lookup_norme_master_list norme on norme.code = ml8.id_norma
join master_list_flag_linee_attivita flag on flag.codice_univoco = ml8.codice
where flag.no_scia and (ml8.rev is null or ml8.rev = 8);

--7) aggiornare la revision A 8
update master_list_macroarea  set rev=8 where rev is null;
update master_list_aggregazione set rev=8 where rev is null;
update master_list_linea_attivita set rev=8 where rev is null;
update master_list_flag_linee_attivita set rev=8 where rev is null;
update ml8_linee_attivita_nuove_materializzata  set rev=8 where rev is null;

-- 7. aggiorno id_linea nella tabella dei flag rev 8
select distinct  'update master_list_flag_linee_attivita set id_linea='||m.id_nuova_linea_attivita||' where codice_univoco='''||l.codice_univoco||''' and rev=8;'
from ml8_linee_attivita_nuove_materializzata m join master_list_flag_linee_attivita l on l.codice_univoco = m.codice 
and m.livello=3 and m.rev=8

--8. Aggiornamento scheda centralizzata
update scheda_operatore_metadati set sql_origine = replace(sql_origine, 'left join master_list_flag_linee_attivita m on m.codice_univoco= o.codice_attivita
', 'left join master_list_flag_linee_attivita m on m.id_linea= o.id_linea_attivita_stab 
') where sql_origine ilike '%left join master_list_flag_linee_attivita m on m.codice_univoco= o.codice_attivita
%'

--9) aggiornamento view per join su id_linea al posto del codice

CREATE OR REPLACE VIEW public.ricerca_anagrafiche AS 
 SELECT DISTINCT o.org_id AS riferimento_id,
    'orgId'::text AS riferimento_id_nome,
    'org_id'::text AS riferimento_id_nome_col,
    'organization'::text AS riferimento_id_nome_tab,
    oa1.address_id AS id_indirizzo_impresa,
    oa5.address_id AS id_sede_operativa,
    oa7.address_id AS sede_mobile_o_altro,
    'organization_address'::text AS riferimento_nome_tab_indirizzi,
    '-1'::integer AS id_impresa,
    '-'::text AS riferimento_nome_tab_impresa,
    '-1'::integer AS id_soggetto_fisico,
    '-'::text AS riferimento_nome_tab_soggetto_fisico,
    '-1'::integer AS id_attivita,
    true AS pregresso_o_import,
    o.org_id AS riferimento_org_id,
    o.entered AS data_inserimento,
    o.name AS ragione_sociale,
    o.site_id AS asl_rif,
    l_1.description AS asl,
    o.codice_fiscale,
    o.codice_fiscale_rappresentante,
    o.partita_iva,
        CASE
            WHEN o.tipologia = 97 AND o.categoria_rischio IS NULL THEN 3
            WHEN o.tipologia = 97 AND o.categoria_rischio = '-1'::integer THEN 3
            ELSE o.categoria_rischio
        END AS categoria_rischio,
    o.prossimo_controllo,
        CASE
            WHEN o.tipologia = ANY (ARRAY[3, 97]) THEN concat_ws(' '::text, o.numaut, o.tipo_stab)::character varying
            WHEN o.tipologia <> 1 AND COALESCE(o.numaut, o.account_number) IS NOT NULL THEN COALESCE(o.numaut, o.account_number)
            ELSE ''::character varying
        END AS num_riconoscimento,
        CASE
            WHEN o.tipologia = 1 THEN o.account_number
            ELSE ''::character varying
        END AS n_reg,
        CASE
            WHEN o.tipologia = 1 THEN o.account_number
            WHEN o.tipologia <> 1 AND COALESCE(o.numaut, o.account_number) IS NOT NULL THEN COALESCE(o.numaut, o.account_number)
            ELSE ''::character varying
        END AS n_linea,
        CASE
            WHEN o.tipologia = 2 THEN op_prop.nominativo
            WHEN o.nome_rappresentante IS NULL AND o.cognome_rappresentante IS NULL THEN 'Non specificato'::text
            WHEN o.nome_rappresentante::text = ' '::text AND o.cognome_rappresentante::text = ' '::text THEN 'Non specificato'::text
            ELSE (o.nome_rappresentante::text || ' '::text) || o.cognome_rappresentante::text
        END AS nominativo_rappresentante,
        CASE
            WHEN o.tipologia = ANY (ARRAY[151, 802, 152, 10, 20, 2, 800, 801]) THEN 'Con Sede Fissa'::text
            WHEN o.tipologia = 1 AND (o.tipo_dest::text = 'Es. Commerciale'::text OR length(btrim(o.tipo_dest::text)) = 0 OR o.tipo_dest::text = 'Distributori'::text) THEN 'Con Sede Fissa'::text
            WHEN o.tipologia = 1 AND o.tipo_dest::text = 'Autoveicolo'::text THEN 'Senza Sede Fissa'::text
            ELSE 'N.D.'::text
        END AS tipo_attivita_descrizione,
        CASE
            WHEN o.tipologia = ANY (ARRAY[151, 802, 152, 10, 20, 2, 800, 801]) THEN 1
            WHEN o.tipologia = 1 AND (o.tipo_dest::text = 'Es. Commerciale'::text OR length(btrim(o.tipo_dest::text)) = 0 OR o.tipo_dest::text = 'Distributori'::text) THEN 1
            WHEN o.tipologia = 1 AND o.tipo_dest::text = 'Autoveicolo'::text THEN 2
            ELSE '-1'::integer
        END AS tipo_attivita,
        CASE
            WHEN o.tipologia = 1 AND o.data_in_carattere IS NOT NULL AND o.data_fine_carattere IS NOT NULL THEN to_date(o.data_in_carattere::text, 'yyyy/mm/dd'::text)::timestamp without time zone
            WHEN o.tipologia = ANY (ARRAY[2, 9]) THEN to_date(o.date1::text, 'yyyy/mm/dd'::text)::timestamp without time zone
            WHEN o.tipologia = ANY (ARRAY[800, 801, 13, 802]) THEN o.date2
            WHEN o.tipologia = 152 AND o.stato ~~* '%attivo%'::text THEN to_date(o.data_cambio_stato::text, 'yyyy/mm/dd'::text)::timestamp without time zone
            WHEN o.tipologia = 17 THEN to_date(o.data_in_carattere::text, 'yyyy/mm/dd'::text)::timestamp without time zone
            ELSE to_date(o.datapresentazione::text, 'yyyy/mm/dd'::text)::timestamp without time zone
        END AS data_inizio_attivita,
        CASE
            WHEN o.tipologia = 1 AND o.source = 1 AND o.data_fine_carattere IS NOT NULL AND o.data_fine_carattere < 'now'::text::date THEN o.data_fine_carattere
            WHEN o.tipologia = 1 AND o.source <> 1 AND (o.cessato = 1 OR o.cessato = 2) THEN o.contract_end
            WHEN o.tipologia = ANY (ARRAY[2, 9]) THEN COALESCE(to_date(o.date2::text, 'yyyy/mm/dd'::text)::timestamp without time zone, o.data_cambio_stato)
            WHEN o.tipologia = 152 AND (o.stato ~* '%sospeso%'::text OR o.stato ~* '%cessato%'::text OR o.stato ~~* '%sospeso%'::text) THEN to_date(o.data_cambio_stato::text, 'yyyy/mm/dd'::text)::timestamp without time zone
            WHEN o.tipologia = ANY (ARRAY[800, 801, 13, 802]) THEN o.date1
            ELSE NULL::timestamp without time zone
        END AS data_fine_attivita,
        CASE
            WHEN o.tipologia = 1 AND o.source = 1 AND o.data_fine_carattere IS NOT NULL AND o.data_fine_carattere < now() THEN 'Cessato'::text
            WHEN o.tipologia = 1 AND o.source = 1 AND o.data_fine_carattere IS NOT NULL AND o.data_fine_carattere > now() THEN 'Attivo'::text
            WHEN o.tipologia = 1 AND (o.source = 1 OR o.source IS NULL) AND o.data_fine_carattere IS NULL AND o.cessato IS NULL THEN 'Attivo'::text
            WHEN o.tipologia = 1 AND o.source = 2 AND o.cessato = 0 THEN 'Attivo'::text
            WHEN o.tipologia = 1 AND (o.source = 2 OR o.source IS NULL) AND o.cessato = 1 THEN 'Cessato'::text
            WHEN o.tipologia = 1 AND o.source = 2 AND o.cessato = 2 THEN 'Sospeso'::text
            WHEN o.tipologia = 3 AND o.direct_bill = true THEN 'Non specificato'::text
            WHEN o.tipologia = 2 THEN
            CASE
                WHEN o.date2 IS NOT NULL THEN 'Cessato'::text
                ELSE 'Attivo'::text
            END
            WHEN o.tipologia = 20 AND o.data_chiusura_canile IS NOT NULL THEN 'Cessato'::text
            WHEN o.stato_lab = 0 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 'Attivo'::text
            WHEN o.stato_lab = 1 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 'Revocato'::text
            WHEN o.stato_lab = 2 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 'Sospeso'::text
            WHEN o.stato_lab = 3 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 'In Domanda'::text
            WHEN o.stato_lab = 4 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 'Cessato'::text
            WHEN o.stato_istruttoria IS NOT NULL AND (o.tipologia = ANY (ARRAY[3, 97])) THEN lss.description::text
            WHEN o.stato_lab IS NULL AND (o.tipologia = ANY (ARRAY[151, 152])) THEN o.stato
            ELSE 'N.D'::text
        END AS stato,
        CASE
            WHEN o.tipologia = 20 AND o.data_chiusura_canile IS NOT NULL THEN 4
            WHEN o.tipologia = 1 AND o.source = 1 AND o.data_fine_carattere IS NOT NULL AND o.data_fine_carattere < now() THEN 4
            WHEN o.tipologia = 1 AND o.source = 1 AND o.data_fine_carattere IS NOT NULL AND o.data_fine_carattere > now() THEN 0
            WHEN o.tipologia = 1 AND (o.source = 1 OR o.source IS NULL) AND o.data_fine_carattere IS NULL AND o.cessato IS NULL THEN 0
            WHEN o.tipologia = 1 AND o.source = 2 AND o.cessato = 0 THEN 0
            WHEN o.tipologia = 1 AND (o.source = 2 OR o.source IS NULL) AND o.cessato = 1 THEN 4
            WHEN o.tipologia = 1 AND o.source = 2 AND o.cessato = 2 THEN 2
            WHEN o.tipologia = 3 AND o.direct_bill = true THEN '-1'::integer
            WHEN o.tipologia = 2 THEN
            CASE
                WHEN o.date2 IS NOT NULL THEN 4
                ELSE 0
            END
            WHEN o.stato_lab = 0 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 0
            WHEN o.stato_lab = 1 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 1
            WHEN o.stato_lab = 2 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 2
            WHEN o.stato_lab = 3 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 3
            WHEN o.stato_lab = 4 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 4
            WHEN o.stato_istruttoria IS NOT NULL AND (o.tipologia = ANY (ARRAY[3, 97])) THEN lss.code
            ELSE 0
        END AS id_stato,
        CASE
            WHEN o.tipologia = 2 THEN COALESCE(c.nome, 'N.D'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa5.city, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa7.city, 'N.D.'::character varying)
            ELSE COALESCE(((oa5.city::text || ', '::text) || oa5.civico)::character varying, ((oa7.city::text || ' '::text) || oa7.civico)::character varying, ((oa1.city::text || ', '::text) || oa1.civico)::character varying, 'N.D.'::character varying)
        END AS comune,
        CASE
            WHEN o.tipologia = 2 THEN COALESCE(az.prov_sede_azienda, ''::text)::character varying
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa5.state, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa7.state, 'N.D.'::character varying)
            WHEN o.site_id = 201 AND o.tipologia <> 2 THEN 'AV'::text::character varying
            WHEN o.site_id = 202 AND o.tipologia <> 2 THEN 'BN'::text::character varying
            WHEN o.site_id = 203 AND o.tipologia <> 2 THEN 'CE'::text::character varying
            WHEN o.tipologia <> 2 AND (o.site_id = 204 OR o.site_id = 205 OR o.site_id = 206) THEN 'NA'::text::character varying
            WHEN o.site_id = 207 AND o.tipologia <> 2 THEN 'SA'::text::character varying
            ELSE COALESCE(oa5.state, oa7.state, oa1.state, 'N.D.'::character varying)
        END AS provincia_stab,
        CASE
            WHEN o.tipologia = 2 THEN COALESCE(az.indrizzo_azienda, ''::text)::character varying
            ELSE COALESCE(((oa5.addrline1::text || ', '::text) || oa5.civico)::character varying, ((oa7.addrline1::text || ', '::text) || oa7.civico)::character varying, ((oa1.addrline1::text || ', '::text) || oa1.civico)::character varying, 'N.D.'::character varying)
        END AS indirizzo,
        CASE
            WHEN o.tipologia = 2 THEN COALESCE(az.cap_azienda, ''::text)::character varying
            ELSE COALESCE(oa5.postalcode, oa7.postalcode, oa1.addrline1, 'N.D.'::character varying)
        END AS cap_stab,
        CASE
            WHEN o.tipologia = 2 THEN COALESCE(az.latitudine, az.latitudine_geo, oa5.latitude)
            ELSE COALESCE(oa5.latitude, oa7.latitude, oa1.latitude)
        END AS latitudine_stab,
        CASE
            WHEN o.tipologia = 2 THEN COALESCE(az.longitudine, az.longitudine_geo, oa5.longitude)
            ELSE COALESCE(oa5.longitude, oa7.longitude, oa1.longitude)
        END AS longitudine_stab,
    COALESCE(oa1.city, oa5.city, 'N.D.'::character varying) AS comune_leg,
    COALESCE(oa1.state, oa5.state, 'N.D.'::character varying) AS provincia_leg,
    COALESCE(oa1.addrline1, oa5.addrline1, 'N.D.'::character varying) AS indirizzo_leg,
    COALESCE(oa1.postalcode, oa5.postalcode, 'N.D.'::character varying) AS cap_leg,
    COALESCE(oa1.latitude, oa5.latitude) AS latitudine_leg,
    COALESCE(oa1.longitude, oa5.longitude) AS longitudine_leg,
    COALESCE(mltemp2.macroarea, mltemp.macroarea, ml8.macroarea, tsa.macroarea) AS macroarea,
    COALESCE(mltemp2.aggregazione, mltemp.aggregazione, ml8.aggregazione, tsa.aggregazione) AS aggregazione,
    concat_ws('->'::text, COALESCE(mltemp2.macroarea, mltemp.macroarea, ml8.macroarea, tsa.macroarea), COALESCE(mltemp2.aggregazione, mltemp.aggregazione, ml8.aggregazione, tsa.aggregazione), COALESCE(mltemp2.attivita, mltemp.attivita, ml8.attivita, tsa.attivita)) AS attivita,
        CASE
            WHEN o.tipologia = 1 THEN concat(COALESCE(lcd.description, ''::character varying), '->', COALESCE(mltemp2.macroarea, mltemp.macroarea, ml8.macroarea, tsa.macroarea), '->', COALESCE(mltemp2.aggregazione, mltemp.aggregazione, ml8.aggregazione, tsa.aggregazione), '->', COALESCE(mltemp2.attivita, mltemp.attivita, ml8.attivita, tsa.attivita))::character varying::text
            WHEN mltemp.macroarea IS NOT NULL THEN mltemp.path_descrizione::text
            ELSE ''::text
        END AS path_attivita_completo,
        CASE
            WHEN mltemp.macroarea IS NOT NULL THEN NULL::text
            ELSE 'Non previsto'::text
        END AS gestione_masterlist,
        CASE
            WHEN o.tipologia = 20 THEN concat(nm.description, ' (ex ', lto.description, ')')
            ELSE nm.description
        END AS norma,
    nm.code AS id_norma,
    o.tipologia AS tipologia_operatore,
    o.nome_correntista AS targa,
        CASE
            WHEN o.trashed_date IS NULL THEN 0
            ELSE 3
        END AS tipo_ricerca_anagrafica,
    'red'::text AS color,
        CASE
            WHEN o.tipologia = 1 THEN o.account_number::text
            ELSE NULL::text
        END AS n_reg_old,
        CASE
            WHEN ml.registrabili OR ml.riconoscibili IS NULL AND ml.registrabili IS NULL THEN 1
            WHEN ml.riconoscibili THEN 2
            ELSE NULL::integer
        END AS id_tipo_linea_reg_ric,
    COALESCE(tsa.id, lai.id, el.id, o.org_id) AS id_linea,
    NULL::text AS matricola,
    COALESCE(mltemp2.codice_macroarea, mltemp.codice_macroarea, ml8.codice_macroarea) AS codice_macroarea,
    COALESCE(mltemp2.codice_aggregazione, mltemp.codice_aggregazione, ml8.codice_aggregazione) AS codice_aggregazione,
    COALESCE("substring"(mltemp2.codice_attivita, length(mltemp2.codice_macroarea) + length(mltemp2.codice_aggregazione) + 3, length(mltemp2.codice_attivita)), "substring"(mltemp.codice_attivita, length(mltemp.codice_macroarea) + length(mltemp.codice_aggregazione) + 3, length(mltemp.codice_attivita)), "substring"(ml8.codice_attivita, length(ml8.codice_macroarea) + length(ml8.codice_aggregazione) + 3, length(ml8.codice_attivita))) AS codice_attivita,
    o.miscela
   FROM organization o
     LEFT JOIN la_imprese_linee_attivita lai ON lai.org_id = o.org_id AND lai.trashed_date IS NULL
     LEFT JOIN stabilimenti_sottoattivita ssa ON ssa.id_stabilimento = o.org_id
     LEFT JOIN soa_sottoattivita ssoa ON ssoa.id_soa = o.org_id
     LEFT JOIN aziende az ON az.cod_azienda = o.account_number::text
     LEFT JOIN comuni1 c ON c.istat::text = ('0'::text || az.cod_comune_azienda)
     LEFT JOIN opu_lookup_norme_master_list_rel_tipologia_organzation norme ON norme.tipologia_organization = o.tipologia AND COALESCE(norme.tipo_molluschi_bivalvi, '-1'::integer) = COALESCE(o.tipologia_acque, '-1'::integer)
     LEFT JOIN opu_lookup_norme_master_list nm ON nm.code = norme.id_opu_lookup_norme_master_list
     LEFT JOIN ml8_linee_attivita_nuove_materializzata ml8 ON ml8.codice = norme.codice_univoco_ml
     LEFT JOIN elenco_attivita_osm_reg el ON el.org_id = o.org_id
     LEFT JOIN lookup_attivita_osm_reg reg ON reg.code = el.id_attivita
     LEFT JOIN linee_attivita_ml8_temp l ON l.org_id = o.org_id AND (l.tipo_attivita_osm IS NULL OR l.tipo_attivita_osm = reg.code)
     LEFT JOIN ml8_linee_attivita_nuove_materializzata mltemp ON mltemp.codice = l.codice_univoco_ml and mltemp.rev = 8
     LEFT JOIN ml8_linee_attivita_nuove_materializzata mltemp2 ON mltemp2.id_nuova_linea_attivita = lai.id_attivita_masterlist
     LEFT JOIN lookup_tipologia_operatore lto ON lto.code = o.tipologia
     LEFT JOIN lista_linee_vecchia_anagrafica_stabilimenti_soggetti_a_scia tsa ON tsa.org_id = o.org_id
     LEFT JOIN lookup_stati_stabilimenti lss ON lss.code = o.stato_istruttoria
     LEFT JOIN operatori_allevamenti op_prop ON o.codice_fiscale_rappresentante::text = op_prop.cf
     LEFT JOIN lookup_site_id l_1 ON l_1.code = o.site_id
     LEFT JOIN organization_address oa5 ON oa5.org_id = o.org_id AND oa5.address_type = 5 AND oa5.trasheddate IS NULL
     LEFT JOIN organization_address oa1 ON oa1.org_id = o.org_id AND oa1.address_type = 1 AND oa1.trasheddate IS NULL
     LEFT JOIN organization_address oa7 ON oa7.org_id = o.org_id AND oa7.address_type = 7 AND oa7.trasheddate IS NULL
     LEFT JOIN la_rel_ateco_attivita rat ON lai.id_rel_ateco_attivita = rat.id
     LEFT JOIN lookup_codistat lcd ON rat.id_lookup_codistat = lcd.code
     LEFT JOIN master_list_flag_linee_attivita ml ON ml.codice_univoco = ((((COALESCE(mltemp2.codice_macroarea, mltemp.codice_macroarea, ml8.macroarea) || '-'::text) || COALESCE(mltemp2.codice_aggregazione, mltemp.codice_aggregazione, ml8.codice_aggregazione)) || '-'::text) || COALESCE("substring"(mltemp2.codice_attivita, length(mltemp2.codice_macroarea) + length(mltemp2.codice_aggregazione) + 3, length(mltemp2.codice_attivita)), "substring"(mltemp.codice_attivita, length(mltemp.codice_macroarea) + length(mltemp.codice_aggregazione) + 3, length(mltemp.codice_attivita)), "substring"(ml8.codice_attivita, length(ml8.codice_macroarea) + length(ml8.codice_aggregazione) + 3, length(ml8.codice_attivita))))
  WHERE o.org_id <> 0 AND o.org_id <> 10000000 AND o.tipologia <> 0 AND o.trashed_date IS NULL AND o.import_opu IS NOT TRUE AND ((o.tipologia = ANY (ARRAY[1, 151, 802, 152, 10, 20, 2, 800, 801])) OR o.tipologia = 3 AND o.direct_bill = false)
UNION
 SELECT global_org_view.riferimento_id,
    global_org_view.riferimento_id_nome,
    global_org_view.riferimento_id_nome_col,
    global_org_view.riferimento_id_nome_tab,
    global_org_view.id_indirizzo_impresa,
    global_org_view.id_sede_operativa,
    global_org_view.sede_mobile_o_altro,
    global_org_view.riferimento_nome_tab_indirizzi,
    global_org_view.id_impresa,
    global_org_view.riferimento_nome_tab_impresa,
    global_org_view.id_soggetto_fisico,
    global_org_view.riferimento_nome_tab_soggetto_fisico,
    global_org_view.id_attivita,
    true AS pregresso_o_import,
    global_org_view.riferimento_id AS riferimento_org_id,
    global_org_view.data_inserimento,
    global_org_view.ragione_sociale,
    global_org_view.asl_rif,
    global_org_view.asl,
    global_org_view.codice_fiscale,
    global_org_view.codice_fiscale_rappresentante,
    global_org_view.partita_iva,
    global_org_view.categoria_rischio,
    global_org_view.prossimo_controllo,
    global_org_view.num_riconoscimento,
    global_org_view.n_reg,
    global_org_view.n_linea,
    global_org_view.nominativo_rappresentante,
    global_org_view.tipo_attivita_descrizione,
    global_org_view.tipo_attivita,
    global_org_view.data_inizio_attivita,
    global_org_view.data_fine_attivita,
    global_org_view.stato,
    global_org_view.id_stato,
    global_org_view.comune,
    global_org_view.provincia_stab,
    global_org_view.indirizzo,
    global_org_view.cap_stab,
    global_org_view.latitudine_stab,
    global_org_view.longitudine_stab,
    global_org_view.comune_leg,
    global_org_view.provincia_leg,
    global_org_view.indirizzo_leg,
    global_org_view.cap_leg,
    global_org_view.latitudine_leg,
    global_org_view.longitudine_leg,
    global_org_view.macroarea,
    global_org_view.aggregazione,
    global_org_view.attivita,
    global_org_view.path_attivita_completo,
    global_org_view.gestione_masterlist,
    global_org_view.norma,
    global_org_view.id_norma,
    global_org_view.tipologia_operatore,
    global_org_view.targa,
    global_org_view.tipo_ricerca_anagrafica,
    global_org_view.color,
    global_org_view.n_reg_old,
    global_org_view.id_tipo_linea_produttiva AS id_tipo_linea_reg_ric,
    global_org_view.id_linea,
    NULL::text AS matricola,
    global_org_view.codice_macroarea,
    global_org_view.codice_aggregazione,
    global_org_view.codice_attivita,
    NULL::boolean AS miscela
   FROM global_org_view
UNION
 SELECT global_ric_view.riferimento_id,
    global_ric_view.riferimento_id_nome,
    global_ric_view.riferimento_id_nome_col,
    global_ric_view.riferimento_id_nome_tab,
    global_ric_view.id_indirizzo_impresa,
    global_ric_view.id_sede_operativa,
    global_ric_view.sede_mobile_o_altro,
    global_ric_view.riferimento_nome_tab_indirizzi,
    global_ric_view.id_impresa,
    global_ric_view.riferimento_nome_tab_impresa,
    global_ric_view.id_soggetto_fisico,
    global_ric_view.riferimento_nome_tab_soggetto_fisico,
    global_ric_view.id_attivita,
    true AS pregresso_o_import,
    global_ric_view.riferimento_id AS riferimento_org_id,
    global_ric_view.data_inserimento,
    global_ric_view.ragione_sociale,
    global_ric_view.asl_rif,
    global_ric_view.asl,
    global_ric_view.codice_fiscale,
    global_ric_view.codice_fiscale_rappresentante,
    global_ric_view.partita_iva,
    global_ric_view.categoria_rischio,
    NULL::timestamp without time zone AS prossimo_controllo,
    global_ric_view.num_riconoscimento,
    global_ric_view.n_reg,
    global_ric_view.n_linea,
    global_ric_view.nominativo_rappresentante,
    global_ric_view.tipo_attivita_descrizione,
    global_ric_view.tipo_attivita,
    global_ric_view.data_inizio_attivita,
    global_ric_view.data_fine_attivita,
    global_ric_view.stato,
    global_ric_view.id_stato,
    global_ric_view.comune,
    global_ric_view.provincia_stab,
    global_ric_view.indirizzo,
    global_ric_view.cap_stab,
    global_ric_view.latitudine_stab,
    global_ric_view.longitudine_stab,
    global_ric_view.comune_leg,
    global_ric_view.provincia_leg,
    global_ric_view.indirizzo_leg,
    global_ric_view.cap_leg,
    global_ric_view.latitudine_leg,
    global_ric_view.longitudine_leg,
    global_ric_view.macroarea,
    global_ric_view.aggregazione,
    global_ric_view.attivita,
    'Non presente'::text AS path_attivita_completo,
    'Non previsto'::text AS gestione_masterlist,
    global_ric_view.norma,
    global_ric_view.id_norma,
    global_ric_view.tipologia_operatore,
    global_ric_view.targa,
    global_ric_view.tipo_ricerca_anagrafica,
    global_ric_view.color,
    global_ric_view.n_reg_old,
    global_ric_view.id_tipo_linea_produttiva AS id_tipo_linea_reg_ric,
    global_ric_view.id_linea_attivita AS id_linea,
    NULL::text AS matricola,
    global_ric_view.codice_macroarea,
    global_ric_view.codice_aggregazione,
    global_ric_view.codice_attivita,
    NULL::boolean AS miscela
   FROM global_ric_view
  WHERE global_ric_view.id_stato = ANY (ARRAY[0, 7, 2])
UNION
 SELECT DISTINCT o.id_stabilimento AS riferimento_id,
    'stabId'::text AS riferimento_id_nome,
    'id_stabilimento'::text AS riferimento_id_nome_col,
    'opu_stabilimento'::text AS riferimento_id_nome_tab,
    o.id_indirizzo_operatore AS id_indirizzo_impresa,
    o.id_indirizzo AS id_sede_operativa,
    '-1'::integer AS sede_mobile_o_altro,
    'opu_indirizzo'::text AS riferimento_nome_tab_indirizzi,
    o.id_opu_operatore AS id_impresa,
    'opu_operatore'::text AS riferimento_nome_tab_impresa,
    o.id_soggetto_fisico,
    'opu_soggetto_fisico'::text AS riferimento_nome_tab_soggetto_fisico,
    o.id_linea_attivita_stab AS id_attivita,
    o.pregresso_o_import,
    o.riferimento_org_id,
    o.stab_entered AS data_inserimento,
    o.ragione_sociale,
    o.id_asl_stab AS asl_rif,
    o.stab_asl AS asl,
    o.codice_fiscale_impresa AS codice_fiscale,
    o.cf_rapp_sede_legale AS codice_fiscale_rappresentante,
    o.partita_iva,
    o.categoria_rischio,
    o.data_prossimo_controllo AS prossimo_controllo,
        CASE
            WHEN o.linea_codice_ufficiale_esistente ~~* 'U150%'::text THEN o.linea_codice_nazionale
            ELSE COALESCE(o.linea_codice_nazionale, o.linea_codice_ufficiale_esistente)
        END AS num_riconoscimento,
    o.numero_registrazione AS n_reg,
        CASE
            WHEN norme.codice_norma = '852-04'::text THEN COALESCE(NULLIF(o.linea_codice_nazionale, ''::text), NULLIF(o.linea_numero_registrazione, ''::text), NULLIF(o.linea_codice_ufficiale_esistente, ''::text))
            ELSE COALESCE(NULLIF(o.linea_codice_nazionale, ''::text), NULLIF(o.linea_codice_ufficiale_esistente, ''::text), NULLIF(o.linea_numero_registrazione, ''::text))
        END AS n_linea,
    concat_ws(' '::text, o.nome_rapp_sede_legale, o.cognome_rapp_sede_legale) AS nominativo_rappresentante,
    o.stab_descrizione_attivita AS tipo_attivita_descrizione,
    o.stab_id_attivita AS tipo_attivita,
    o.data_inizio_attivita,
    o.data_fine_attivita,
    o.linea_stato_text AS stato,
    o.linea_stato AS id_stato,
        CASE
            WHEN o.stab_id_attivita = 1 THEN o.comune_stab
            WHEN o.stab_id_attivita = 2 AND o.impresa_id_tipo_impresa = 1 THEN o.comune_residenza
            WHEN o.stab_id_attivita = 2 AND o.impresa_id_tipo_impresa <> 1 THEN o.comune_sede_legale
            ELSE NULL::character varying
        END AS comune,
    o.prov_stab AS provincia_stab,
    o.indirizzo_stab AS indirizzo,
    o.cap_stab,
    o.lat_stab AS latitudine_stab,
    o.long_stab AS longitudine_stab,
    o.comune_sede_legale AS comune_leg,
    o.prov_sede_legale AS provincia_leg,
    o.indirizzo_sede_legale AS indirizzo_leg,
    o.cap_sede_legale AS cap_leg,
    NULL::double precision AS latitudine_leg,
    NULL::double precision AS longitudine_leg,
    o.macroarea,
    o.aggregazione,
    o.attivita,
    o.path_attivita_completo,
        CASE
            WHEN o.flag_nuova_gestione IS NULL OR o.flag_nuova_gestione = false THEN 'LINEA NON AGGIORNATA SECONDO MASTER LIST.'::text
            ELSE NULL::text
        END AS gestione_masterlist,
    norme.description AS norma,
    o.id_norma,
    999 AS tipologia_operatore,
    m.targa,
    1 AS tipo_ricerca_anagrafica,
    'gray'::text AS color,
    o.linea_codice_ufficiale_esistente AS n_reg_old,
    o.id_tipo_linea_produttiva AS id_tipo_linea_reg_ric,
    o.id_linea_attivita AS id_linea,
    d.matricola,
    o.codice_macroarea,
    o.codice_aggregazione,
    o.codice_attivita_only AS codice_attivita,
    NULL::boolean AS miscela
   FROM opu_operatori_denormalizzati_view o
     LEFT JOIN opu_stabilimento_mobile m ON m.id_stabilimento = o.id_stabilimento
     LEFT JOIN opu_stabilimento_mobile_distributori d ON d.id_rel_stab_linea = o.id_linea_attivita
     LEFT JOIN opu_lookup_norme_master_list norme ON o.id_norma = norme.code
UNION
 SELECT DISTINCT o.id_stabilimento AS riferimento_id,
    'stabId'::text AS riferimento_id_nome,
    'id_stabilimento'::text AS riferimento_id_nome_col,
    'apicoltura_imprese'::text AS riferimento_id_nome_tab,
    o.id_indirizzo_operatore AS id_indirizzo_impresa,
    o.id_indirizzo AS id_sede_operativa,
    '-1'::integer AS sede_mobile_o_altro,
    'opu_indirizzo'::text AS riferimento_nome_tab_indirizzi,
    o.id_apicoltura_imprese AS id_impresa,
    'apicoltura_imprese'::text AS riferimento_nome_tab_impresa,
    COALESCE(o.id_soggetto_fisico, o.id_detentore) AS id_soggetto_fisico,
    'opu_soggetto_fisico'::text AS riferimento_nome_tab_soggetto_fisico,
    '-1'::integer AS id_attivita,
    false AS pregresso_o_import,
    '-1'::integer AS riferimento_org_id,
    o.entered AS data_inserimento,
    o.ragione_sociale,
    o.id_asl_apicoltore AS asl_rif,
    o.asl_apicoltore AS asl,
    o.codice_fiscale_impresa AS codice_fiscale,
    o.cf_rapp_sede_legale AS codice_fiscale_rappresentante,
    o.partita_iva,
    o.categoria_rischio,
    o.data_prossimo_controllo AS prossimo_controllo,
    COALESCE(o.codice_azienda, ''::text) AS num_riconoscimento,
    ''::text AS n_reg,
    COALESCE(o.codice_azienda, ''::text) AS n_linea,
    concat_ws(' '::text, o.nome_rapp_sede_legale, o.cognome_rapp_sede_legale) AS nominativo_rappresentante,
    o.tipo_descrizione_attivita AS tipo_attivita_descrizione,
    o.tipo_attivita,
    o.data_inizio_attivita::timestamp without time zone AS data_inizio_attivita,
    o.data_fine_attivita::timestamp without time zone AS data_fine_attivita,
    o.stato_stab AS stato,
    0 AS id_stato,
    o.comune_stab AS comune,
    o.prov_stab AS provincia_stab,
    o.indirizzo_stab AS indirizzo,
    o.cap_stab,
    o.latitudine AS latitudine_stab,
    o.longitudine AS longitudine_stab,
    o.comune_sede_legale AS comune_leg,
    o.prov_sede_legale AS provincia_leg,
    o.indirizzo_sede_legale AS indirizzo_leg,
    o.cap_sede_legale AS cap_leg,
    NULL::double precision AS latitudine_leg,
    NULL::double precision AS longitudine_leg,
    o.macroarea,
    o.aggregazione,
    COALESCE(o.attivita, ''::text) AS attivita,
    'Non presente'::text AS path_attivita_completo,
    'Non previsto'::text AS gestione_masterlist,
    'APICOLTURA'::text AS norma,
    17 AS id_norma,
    1000 AS tipologia_operatore,
    NULL::text AS targa,
    1 AS tipo_ricerca_anagrafica,
    'gray'::text AS color,
    NULL::text AS n_reg_old,
    1 AS id_tipo_linea_reg_ric,
    o.id_stabilimento AS id_linea,
    NULL::text AS matricola,
    o.codice_macroarea,
    o.codice_aggregazione,
    o.codice_attivita,
    NULL::boolean AS miscela
   FROM apicoltura_apiari_denormalizzati_view o
UNION
 SELECT DISTINCT o.alt_id AS riferimento_id,
    'altId'::text AS riferimento_id_nome,
    'alt_id'::text AS riferimento_id_nome_col,
    'sintesis_stabilimento'::text AS riferimento_id_nome_tab,
    o.id_indirizzo_operatore AS id_indirizzo_impresa,
    o.id_indirizzo AS id_sede_operativa,
    '-1'::integer AS sede_mobile_o_altro,
    'sintesis_indirizzo'::text AS riferimento_nome_tab_indirizzi,
    o.id_opu_operatore AS id_impresa,
    'sintesis_operatore'::text AS riferimento_nome_tab_impresa,
    o.id_soggetto_fisico,
    'sintesis_soggetto_fisico'::text AS riferimento_nome_tab_soggetto_fisico,
    o.id_linea_attivita_stab AS id_attivita,
    false AS pregresso_o_import,
    '-1'::integer AS riferimento_org_id,
    o.stab_entered AS data_inserimento,
    o.ragione_sociale,
    o.id_asl_stab AS asl_rif,
    o.stab_asl AS asl,
    o.codice_fiscale_impresa AS codice_fiscale,
    o.cf_rapp_sede_legale AS codice_fiscale_rappresentante,
    o.partita_iva,
    o.categoria_rischio,
    o.data_prossimo_controllo AS prossimo_controllo,
    o.approval_number AS num_riconoscimento,
    ''::text AS n_reg,
    o.approval_number AS n_linea,
    concat_ws(' '::text, o.nome_rapp_sede_legale, o.cognome_rapp_sede_legale) AS nominativo_rappresentante,
    o.stab_descrizione_attivita AS tipo_attivita_descrizione,
    o.stab_id_attivita AS tipo_attivita,
    o.data_inizio_attivita,
    o.data_fine_attivita,
    o.linea_stato_text AS stato,
    o.linea_stato AS id_stato,
    o.comune_stab AS comune,
    o.prov_stab AS provincia_stab,
    o.indirizzo_stab AS indirizzo,
    o.cap_stab,
    o.lat_stab AS latitudine_stab,
    o.long_stab AS longitudine_stab,
    o.comune_sede_legale AS comune_leg,
    o.prov_sede_legale AS provincia_leg,
    o.indirizzo_sede_legale AS indirizzo_leg,
    o.cap_sede_legale AS cap_leg,
    NULL::double precision AS latitudine_leg,
    NULL::double precision AS longitudine_leg,
    o.macroarea,
    o.aggregazione,
    o.attivita,
    o.path_attivita_completo,
    NULL::text AS gestione_masterlist,
    o.norma,
    o.id_norma,
    2000 AS tipologia_operatore,
    m.targa,
    1 AS tipo_ricerca_anagrafica,
    'gray'::text AS color,
    o.linea_codice_ufficiale_esistente AS n_reg_old,
    o.id_tipo_linea_produttiva AS id_tipo_linea_reg_ric,
    o.id_linea_attivita AS id_linea,
    m.numero_identificativo AS matricola,
    o.codice_macroarea,
    o.codice_aggregazione,
    o.codice_attivita_only AS codice_attivita,
    NULL::boolean AS miscela
   FROM sintesis_operatori_denormalizzati_view o
     LEFT JOIN sintesis_stabilimento_mobile m ON m.id_rel_stab_lp = o.id_linea_attivita;

ALTER TABLE public.ricerca_anagrafiche
  OWNER TO postgres;


CREATE OR REPLACE FUNCTION public_functions.insert_linee_attivita_controlli_ufficiali(
    _id_controllo_ufficiale integer,
    _id_linea_attivita integer,
    _riferimento_nome_tab character varying,
    _note text DEFAULT NULL::text,
    _id_linea_attivita_old integer DEFAULT NULL::integer,
    _id_vecchio_tipo_operatore integer DEFAULT NULL::integer,
    user_id integer DEFAULT NULL::integer)
  RETURNS text AS
$BODY$
  DECLARE
     _codice_linea character varying;
     _provvedimenti_prescrittivi integer;
     _id_lacu integer;
  BEGIN

select provvedimenti_prescrittivi into _provvedimenti_prescrittivi from ticket where ticketid = _id_controllo_ufficiale;
IF _provvedimenti_prescrittivi <> 3 THEN
        update linee_attivita_controlli_ufficiali set note_internal_use_only = concat(note_internal_use_only, current_timestamp, '. Cancellazione record per import controlli ufficiali fatto da utente ', user_id ), modified=current_timestamp, modified_by = user_id,  trashed_date = now() where id_controllo_ufficiale = _id_controllo_ufficiale;
        END IF;
        
	-- recupero codice linea
        _codice_linea := ( select distinct(codice) from (
           select codice from opu_relazione_stabilimento_linee_produttive rel 
           join ml8_linee_attivita_nuove_materializzata ml 
           on ml.id_nuova_linea_attivita = rel.id_linea_produttiva 
           where rel.id = _id_linea_attivita and _riferimento_nome_tab = 'opu_relazione_stabilimento_linee_produttive'
           union
           select distinct codice from org_linee_attivita_view  
	   where (id = _id_linea_attivita and  (_riferimento_nome_tab not in ('opu_relazione_stabilimento_linee_produttive', 'sintesis_relazione_stabilimento_linee_produttive','anagrafica.rel_stabilimenti_linee_attivita' )) and ( _id_vecchio_tipo_operatore is null or tipo = _id_vecchio_tipo_operatore)) or
	   _id_linea_attivita = -1 and tipo = _id_vecchio_tipo_operatore 
	   union
           select codice from suap_ric_scia_linee_attivita_stabilimenti_view where id = _id_linea_attivita and _riferimento_nome_tab = 'suap_ric_scia_relazione_stabilimento_linee_produttive'
           union   
           select codice from sintesis_linee_attivita_stabilimenti_view where id = _id_linea_attivita and _riferimento_nome_tab = 'sintesis_relazione_stabilimento_linee_produttive'
           union
           select 'OPR-OPR-X' from anagrafica.linee_attivita_stabilimenti_view where id = _id_linea_attivita and _riferimento_nome_tab = 'anagrafica.rel_stabilimenti_linee_attivita'
           ) aa limit 1
	);
select id_controllo_ufficiale into _id_lacu from linee_attivita_controlli_ufficiali where id_controllo_ufficiale = 	_id_controllo_ufficiale and id_linea_attivita = _id_linea_attivita and trashed_date is null;

IF _id_lacu IS NULL or _id_lacu<=0 THEN
insert into linee_attivita_controlli_ufficiali(id_controllo_ufficiale, id_linea_attivita, riferimento_nome_tab, codice_linea, note, id_linea_attivita_old, id_vecchio_tipo_operatore,entered,entered_by,note_internal_use_only)
	values(_id_controllo_ufficiale,_id_linea_attivita,_riferimento_nome_tab, _codice_linea, _note, _id_linea_attivita_old, _id_vecchio_tipo_operatore,current_timestamp,user_id, concat(current_timestamp || ' - Inserito record a seguito di import da parte di utente ' || user_id || '.'));
	END IF;
	
	return 'OK';
  END;
 $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.insert_linee_attivita_controlli_ufficiali(integer, integer, character varying, text, integer, integer, integer)
  OWNER TO postgres;


  -- Function: public.get_dettaglio_richiesta(integer)

-- DROP FUNCTION public.get_dettaglio_richiesta(integer);

CREATE OR REPLACE FUNCTION public.get_dettaglio_richiesta(IN _altid integer)
  RETURNS TABLE(id_opu_operatore integer, ragione_sociale text, partita_iva text, codice_fiscale_impresa text, indirizzo_sede_legale text, comune_sede_legale text, istat_legale text, cap_sede_legale text, prov_sede_legale text, note text, entered timestamp without time zone, modified timestamp without time zone, enteredby integer, modifiedby integer, domicilio_digitale text, comune text, id_asl integer, id_stabilimento integer, comune_stab text, istat_operativo text, indirizzo_stab text, cap_stab text, prov_stab text, cf_rapp_sede_legale text, nome_rapp_sede_legale text, cognome_rapp_sede_legale text, codice_registrazione text, id_norma integer, cf_correntista text, codice_attivita text, primario boolean, attivita text, data_inizio timestamp without time zone, data_fine timestamp without time zone, numero_registrazione text, indirizzo_rapp_sede_legale text, stato text, solo_attivita text, data_inizio_attivita timestamp without time zone, data_fine_attivita timestamp without time zone, id_stato integer, path_attivita_completo text, id_indirizzo integer, flag_nuova_gestione boolean, tipo_impresa text, tipo_societa text, id_asl_stab integer, id_linea_attivita integer, data_mod_attivita timestamp without time zone, stab_entered timestamp without time zone, linea_numero_registrazione text, linea_stato integer, linea_stato_text text, id_indirizzo_operatore integer, id_linea_attivita_stab integer, note_operatore text, note_stabilimento text, flag_pnaa boolean, stab_inserito_da text, stab_id_attivita integer, stab_descrizione_attivita text, stab_id_carattere integer, stab_descrizione_carattere text, impresa_id_tipo_impresa integer, stab_asl text, id_tipo_richiesta integer, descrizione_tipo_richiesta text, validato boolean, data_nascita timestamp without time zone, comune_nascita text, id_comune_richiesta integer, comune_richiesta text, macroarea text, aggregazione text, linea_attivita text, id_tipo_impresa integer, id_topo_societa integer, lat_stab double precision, long_stab double precision, stab_cessazione_stabilimento boolean, numero_registrazione_variazione text, partita_iva_variazione text, alt_id integer, permesso text, via_sede_stab text, civico_sede_stab text, toponimo_sede_stab text, via_stabilimento_calcolata text, civico_stabilimento_calcolato text, comune_residenza text, note_validazione text, stato_validazione integer, sospensione_stabilimento boolean, data_inizio_sospensione timestamp without time zone, id_tipo_linea_produttiva integer, validabile boolean, codice_azienda_apicoltura text, cessazione_stabilimento boolean, id_soggetto_fisico integer, codice_macroarea text, codice_aggregazione text, codice_attivita_only text) AS
$BODY$
DECLARE
r RECORD;	

BEGIN

FOR 

id_opu_operatore, ragione_sociale, partita_iva, codice_fiscale_impresa, 
       indirizzo_sede_legale, comune_sede_legale, istat_legale, cap_sede_legale, 
       prov_sede_legale, note, entered, modified, enteredby, modifiedby, 
       domicilio_digitale, comune, id_asl, id_stabilimento, comune_stab, 
       istat_operativo, indirizzo_stab, cap_stab, prov_stab, cf_rapp_sede_legale, 
       nome_rapp_sede_legale, cognome_rapp_sede_legale, codice_registrazione, 
       id_norma, cf_correntista, codice_attivita, primario, attivita, 
       data_inizio, data_fine, numero_registrazione, indirizzo_rapp_sede_legale, 
       stato, solo_attivita, data_inizio_attivita, data_fine_attivita, 
       id_stato, path_attivita_completo, id_indirizzo, flag_nuova_gestione, 
       tipo_impresa, tipo_societa, id_asl_stab, id_linea_attivita, data_mod_attivita, 
       stab_entered, linea_numero_registrazione, linea_stato, linea_stato_text, 
       id_indirizzo_operatore, id_linea_attivita_stab, note_operatore, 
       note_stabilimento, flag_pnaa, stab_inserito_da, stab_id_attivita, 
       stab_descrizione_attivita, stab_id_carattere, stab_descrizione_carattere, 
       impresa_id_tipo_impresa, stab_asl, id_tipo_richiesta, descrizione_tipo_richiesta, 
       validato, data_nascita, comune_nascita, id_comune_richiesta, 
       comune_richiesta, macroarea, aggregazione, linea_attivita, id_tipo_impresa, 
       id_topo_societa, lat_stab, long_stab, stab_cessazione_stabilimento, 
       numero_registrazione_variazione, partita_iva_variazione, alt_id, 
       permesso, via_sede_stab, civico_sede_stab, toponimo_sede_stab, 
       via_stabilimento_calcolata, civico_stabilimento_calcolato, comune_residenza, 
       note_validazione, stato_validazione, sospensione_stabilimento, 
       data_inizio_sospensione, id_tipo_linea_produttiva, validabile, 
       codice_azienda_apicoltura, cessazione_stabilimento, id_soggetto_fisico, 
       codice_macroarea, codice_aggregazione, 
       codice_attivita_only

in

    SELECT DISTINCT o.id AS id_opu_operatore,
    o.ragione_sociale,
    o.partita_iva,
    o.codice_fiscale_impresa,
    (((
        CASE
            WHEN topsedeop.description IS NOT NULL THEN topsedeop.description
            ELSE ''::character varying
        END::text || ' '::text) || sedeop.via::text) || ' '::text) ||
        CASE
            WHEN sedeop.civico IS NOT NULL THEN sedeop.civico
            ELSE ''::text
        END AS indirizzo_sede_legale,
    comunisl.nome AS comune_sede_legale,
    comunisl.istat AS istat_legale,
    sedeop.cap AS cap_sede_legale,
    provsedeop.description AS prov_sede_legale,
    o.note,
    o.entered,
    o.modified,
    o.enteredby,
    o.modifiedby,
    o.domicilio_digitale,
    i.comune,
    stab.id_asl,
    stab.id AS id_stabilimento,
    comuni1.nome AS comune_stab,
    comuni1.istat AS istat_operativo,
    (((
        CASE
            WHEN topsedestab.description IS NOT NULL THEN topsedestab.description
            ELSE ''::character varying
        END::text || ' '::text) || sedestab.via::text) || ' '::text) ||
        CASE
            WHEN sedestab.civico IS NOT NULL THEN sedestab.civico
            ELSE ''::text
        END AS indirizzo_stab,
    sedestab.cap AS cap_stab,
    provsedestab.description AS prov_stab,
    soggsl.codice_fiscale AS cf_rapp_sede_legale,
    soggsl.nome AS nome_rapp_sede_legale,
    soggsl.cognome AS cognome_rapp_sede_legale,
    stab.numero_registrazione AS codice_registrazione,
    latt.id_norma,
    latt.codice_attivita AS cf_correntista,
    latt.codice_attivita,
    lps.primario,
    (((latt.macroarea || '->'::text) || latt.aggregazione) || '->'::text) || latt.attivita AS attivita,
    lps.data_inizio,
    lps.data_fine,
    stab.numero_registrazione,
    (((((((
        CASE
            WHEN topsoggind.description IS NOT NULL THEN topsoggind.description
            ELSE ''::character varying
        END::text || ' '::text) ||
        CASE
            WHEN soggind.via IS NOT NULL THEN soggind.via
            ELSE ''::character varying
        END::text) || ', '::text) ||
        CASE
            WHEN soggind.civico IS NOT NULL THEN soggind.civico
            ELSE ''::text
        END) || ' '::text) ||
        CASE
            WHEN comunisoggind.nome IS NOT NULL THEN comunisoggind.nome
            ELSE ''::character varying
        END::text) || ' '::text) ||
        CASE
            WHEN provsoggind.description IS NOT NULL THEN ('('::text || provsoggind.cod_provincia) || ')'::text
            ELSE ''::text
        END AS indirizzo_rapp_sede_legale,
        CASE
            WHEN lps.stato = 0 THEN 'DA VALIDARE'::character varying(50)
            WHEN lps.stato = 1 THEN 'VALIDATO'::character varying(50)
            WHEN lps.stato = 2 THEN 'RESPINTO'::character varying(50)
            WHEN stab.stato = 0 THEN 'DA VALIDARE'::character varying(50)
            WHEN stab.stato = 1 THEN 'VALIDATO'::character varying(50)
            WHEN stab.stato = 2 THEN 'RESPINTO'::character varying(50)
            ELSE ''::character varying(50)
        END AS stato,
    latt.attivita AS solo_attivita,
    stab.data_inizio_attivita,
    stab.data_fine_attivita,
    COALESCE(lps.stato, stab.stato) AS id_stato,
    latt.path_descrizione AS path_attivita_completo,
    stab.id_indirizzo,
    true AS flag_nuova_gestione,
    loti.description AS tipo_impresa,
    lotis.description AS tipo_societa,
    stab.id_asl AS id_asl_stab,
    lps.id AS id_linea_attivita,
    lps.modified AS data_mod_attivita,
    stab.entered AS stab_entered,
    lps.numero_registrazione AS linea_numero_registrazione,
    lps.stato AS linea_stato,
    statilinea.description AS linea_stato_text,
    sedeop.id AS id_indirizzo_operatore,
    lps.id_linea_produttiva AS id_linea_attivita_stab,
    o.note AS note_operatore,
    stab.note AS note_stabilimento,
    latt.flag_pnaa,
    (con.namefirst::text || ' '::text) || con.namelast::text AS stab_inserito_da,
        CASE
            WHEN ml.fisso THEN 1
            WHEN ml.mobile THEN 2
            ELSE '-1'::integer
        END AS stab_id_attivita,
        CASE
            WHEN ml.fisso THEN 'Con Sede Fissa'::text
            WHEN ml.mobile THEN 'Senza Sede Fissa'::text
            ELSE 'N.D.'::text
        END AS stab_descrizione_attivita,
    stab.tipo_carattere AS stab_id_carattere,
    lsc.description AS stab_descrizione_carattere,
    o.tipo_impresa AS impresa_id_tipo_impresa,
    asl.description AS stab_asl,
    o.id_tipo_richiesta,
    suapr.description AS descrizione_tipo_richiesta,
    o.validato,
    soggsl.data_nascita,
    soggsl.comune_nascita,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN comuni1.id
            WHEN o.tipo_impresa = 1 AND (stab.tipo_attivita = 2 OR stab.tipo_attivita = 3) THEN comunisoggind.id
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN comuni1.id
            WHEN o.tipo_impresa <> 1 AND (stab.tipo_attivita = 2 OR stab.tipo_attivita = 3) THEN comunisl.id
            ELSE NULL::integer
        END AS id_comune_richiesta,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN comuni1.nome
            WHEN o.tipo_impresa = 1 AND (stab.tipo_attivita = 2 OR stab.tipo_attivita = 3) THEN comunisoggind.nome
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN comuni1.nome
            WHEN o.tipo_impresa <> 1 AND (stab.tipo_attivita = 2 OR stab.tipo_attivita = 3) THEN comunisl.nome
            ELSE NULL::character varying
        END AS comune_richiesta,
    latt.macroarea,
    latt.aggregazione,
    latt.attivita AS linea_attivita,
    o.tipo_impresa AS id_tipo_impresa,
    o.tipo_societa AS id_topo_societa,
    sedestab.latitudine AS lat_stab,
    sedestab.longitudine AS long_stab,
    stab.cessazione_stabilimento AS stab_cessazione_stabilimento,
    stab.numero_registrazione_variazione,
    stab.partita_iva_variazione,
    stab.alt_id,
    ll.short_description AS permesso,
    i.via AS via_sede_stab,
    i.civico AS civico_sede_stab,
    i.toponimo AS toponimo_sede_stab,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.via::text)::character varying
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 2 THEN btrim(soggind.via::text)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.via::text)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 2 THEN btrim(sedeop.via::text)::character varying
            ELSE NULL::character varying
        END AS via_stabilimento_calcolata,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.civico)::character varying
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 2 THEN btrim(soggind.civico)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.civico)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 2 THEN btrim(sedeop.civico)::character varying
            ELSE NULL::character varying
        END AS civico_stabilimento_calcolato,
    comunisoggind.nome AS comune_residenza,
    stab.note_validazione,
    stab.stato_validazione,
    stab.sospensione_stabilimento,
    stab.data_inizio_sospensione,
    latt.id_lookup_configurazione_validazione AS id_tipo_linea_produttiva,
        CASE
            WHEN apicoltura.id <= 0 OR apicoltura.id IS NULL THEN true
            WHEN apicoltura.id > 0 AND apicoltura.sincronizzato_bdn = true THEN true
            ELSE false
        END AS validabile,
    apicoltura.codice_azienda AS codice_azienda_apicoltura,
    stab.cessazione_stabilimento,
    rels.id_soggetto_fisico,
    latt.codice_macroarea,
    latt.codice_aggregazione,
    "substring"(latt.codice_attivita, length(latt.codice_macroarea) + length(latt.codice_aggregazione) + 3, length(latt.codice_attivita)) AS codice_attivita_only
   FROM suap_ric_scia_operatore o
        JOIN suap_ric_scia_stabilimento stab ON stab.id_operatore = o.id and (( _altId>0 and stab.alt_id = _altId) or ( _altId=-1))
   JOIN suap_ric_scia_rel_operatore_soggetto_fisico rels ON rels.id_operatore = o.id AND rels.enabled
     JOIN suap_ric_scia_soggetto_fisico soggsl ON soggsl.id = rels.id_soggetto_fisico
       JOIN opu_indirizzo sedestab ON sedestab.id = stab.id_indirizzo

     LEFT JOIN apicoltura_imprese apicoltura ON o.id = apicoltura.id_richiesta_suap
     LEFT JOIN suap_lookup_tipo_richiesta suapr ON suapr.code = o.id_tipo_richiesta
     LEFT JOIN opu_indirizzo soggind ON soggind.id = soggsl.indirizzo_id
     LEFT JOIN comuni1 comunisoggind ON comunisoggind.id = soggind.comune
     LEFT JOIN opu_indirizzo sedeop ON sedeop.id = o.id_indirizzo
     LEFT JOIN comuni1 comunisl ON sedeop.comune = comunisl.id
     LEFT JOIN suap_ric_scia_relazione_stabilimento_linee_produttive lps ON lps.id_stabilimento = stab.id AND lps.enabled
     LEFT JOIN lookup_stato_lab stati ON stati.code = stab.stato
     LEFT JOIN ml8_linee_attivita_nuove_materializzata latt ON latt.id_nuova_linea_attivita = lps.id_linea_produttiva
     LEFT JOIN master_list_flag_linee_attivita ml ON ml.id_linea = latt.id_nuova_linea_attivita
     LEFT JOIN lookup_flusso_originale_ml lconf ON lconf.code = latt.id_lookup_configurazione_validazione
     LEFT JOIN lookup_ente_scia ll ON lconf.ente_validazione = ll.code
     LEFT JOIN opu_lookup_norme_master_list nor ON nor.code = latt.id_norma
     LEFT JOIN comuni1 ON sedestab.comune = comuni1.id
     LEFT JOIN opu_indirizzo i ON i.id = stab.id_indirizzo
     LEFT JOIN lookup_province provsedestab ON provsedestab.code::text = sedestab.provincia::text
     LEFT JOIN lookup_province provsedeop ON provsedeop.code::text = sedeop.provincia::text
     LEFT JOIN lookup_province provsoggind ON provsoggind.code::text = soggind.provincia::text
     LEFT JOIN lookup_toponimi topsedeop ON sedeop.toponimo = topsedeop.code
     LEFT JOIN lookup_toponimi topsedestab ON sedestab.toponimo = topsedestab.code
     LEFT JOIN lookup_toponimi topsoggind ON soggind.toponimo = topsoggind.code
     LEFT JOIN lookup_opu_tipo_impresa loti ON loti.code = o.tipo_impresa
     LEFT JOIN lookup_opu_tipo_impresa_societa lotis ON lotis.code = o.tipo_societa
     LEFT JOIN lookup_stato_lab statilinea ON statilinea.code = lps.stato
     LEFT JOIN access acc ON acc.user_id = stab.entered_by
     LEFT JOIN contact con ON con.contact_id = acc.contact_id
     LEFT JOIN opu_lookup_tipologia_attivita lsa ON lsa.code = stab.tipo_attivita
     LEFT JOIN opu_lookup_tipologia_carattere lsc ON lsc.code = stab.tipo_carattere
     LEFT JOIN lookup_site_id asl ON asl.code = stab.id_asl
  WHERE o.trashed_date IS NULL AND stab.trashed_date IS NULL

    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_dettaglio_richiesta(integer)
  OWNER TO postgres;

  

-- View: public.suap_ric_scia_operatori_denormalizzati_view

-- DROP VIEW public.suap_ric_scia_operatori_denormalizzati_view;

CREATE OR REPLACE VIEW public.suap_ric_scia_operatori_denormalizzati_view AS 
 SELECT DISTINCT o.id AS id_opu_operatore,
    o.ragione_sociale,
    o.partita_iva,
    o.codice_fiscale_impresa,
    (((
        CASE
            WHEN topsedeop.description IS NOT NULL THEN topsedeop.description
            ELSE ''::character varying
        END::text || ' '::text) || sedeop.via::text) || ' '::text) ||
        CASE
            WHEN sedeop.civico IS NOT NULL THEN sedeop.civico
            ELSE ''::text
        END AS indirizzo_sede_legale,
    comunisl.nome AS comune_sede_legale,
    comunisl.istat AS istat_legale,
    sedeop.cap AS cap_sede_legale,
    provsedeop.description AS prov_sede_legale,
    o.note,
    o.entered,
    o.modified,
    o.enteredby,
    o.modifiedby,
    o.domicilio_digitale,
    i.comune,
    stab.id_asl,
    stab.id AS id_stabilimento,
    comuni1.nome AS comune_stab,
    comuni1.istat AS istat_operativo,
    (((
        CASE
            WHEN topsedestab.description IS NOT NULL THEN topsedestab.description
            ELSE ''::character varying
        END::text || ' '::text) || sedestab.via::text) || ' '::text) ||
        CASE
            WHEN sedestab.civico IS NOT NULL THEN sedestab.civico
            ELSE ''::text
        END AS indirizzo_stab,
    sedestab.cap AS cap_stab,
    provsedestab.description AS prov_stab,
    soggsl.codice_fiscale AS cf_rapp_sede_legale,
    soggsl.nome AS nome_rapp_sede_legale,
    soggsl.cognome AS cognome_rapp_sede_legale,
    stab.numero_registrazione AS codice_registrazione,
    latt.id_norma,
    latt.codice_attivita AS cf_correntista,
    latt.codice_attivita,
    lps.primario,
    (((latt.macroarea || '->'::text) || latt.aggregazione) || '->'::text) || latt.attivita AS attivita,
    lps.data_inizio,
    lps.data_fine,
    stab.numero_registrazione,
    (((((((
        CASE
            WHEN topsoggind.description IS NOT NULL THEN topsoggind.description
            ELSE ''::character varying
        END::text || ' '::text) ||
        CASE
            WHEN soggind.via IS NOT NULL THEN soggind.via
            ELSE ''::character varying
        END::text) || ', '::text) ||
        CASE
            WHEN soggind.civico IS NOT NULL THEN soggind.civico
            ELSE ''::text
        END) || ' '::text) ||
        CASE
            WHEN comunisoggind.nome IS NOT NULL THEN comunisoggind.nome
            ELSE ''::character varying
        END::text) || ' '::text) ||
        CASE
            WHEN provsoggind.description IS NOT NULL THEN ('('::text || provsoggind.cod_provincia) || ')'::text
            ELSE ''::text
        END AS indirizzo_rapp_sede_legale,
        CASE
            WHEN lps.stato = 0 THEN 'DA VALIDARE'::character varying(50)
            WHEN lps.stato = 1 THEN 'VALIDATO'::character varying(50)
            WHEN lps.stato = 2 THEN 'RESPINTO'::character varying(50)
            WHEN stab.stato = 0 THEN 'DA VALIDARE'::character varying(50)
            WHEN stab.stato = 1 THEN 'VALIDATO'::character varying(50)
            WHEN stab.stato = 2 THEN 'RESPINTO'::character varying(50)
            ELSE ''::character varying(50)
        END AS stato,
    latt.attivita AS solo_attivita,
    stab.data_inizio_attivita,
    stab.data_fine_attivita,
    COALESCE(lps.stato, stab.stato) AS id_stato,
    latt.path_descrizione AS path_attivita_completo,
    stab.id_indirizzo,
    true AS flag_nuova_gestione,
    loti.description AS tipo_impresa,
    lotis.description AS tipo_societa,
    stab.id_asl AS id_asl_stab,
    lps.id AS id_linea_attivita,
    lps.modified AS data_mod_attivita,
    stab.entered AS stab_entered,
    lps.numero_registrazione AS linea_numero_registrazione,
    lps.stato AS linea_stato,
    statilinea.description AS linea_stato_text,
    sedeop.id AS id_indirizzo_operatore,
    lps.id_linea_produttiva AS id_linea_attivita_stab,
    o.note AS note_operatore,
    stab.note AS note_stabilimento,
    latt.flag_pnaa,
    (con.namefirst::text || ' '::text) || con.namelast::text AS stab_inserito_da,
        CASE
            WHEN ml.fisso THEN 1
            WHEN ml.mobile THEN 2
            ELSE '-1'::integer
        END AS stab_id_attivita,
        CASE
            WHEN ml.fisso THEN 'Con Sede Fissa'::text
            WHEN ml.mobile THEN 'Senza Sede Fissa'::text
            ELSE 'N.D.'::text
        END AS stab_descrizione_attivita,
    stab.tipo_carattere AS stab_id_carattere,
    lsc.description AS stab_descrizione_carattere,
    o.tipo_impresa AS impresa_id_tipo_impresa,
    asl.description AS stab_asl,
    o.id_tipo_richiesta,
    suapr.description AS descrizione_tipo_richiesta,
    o.validato,
    soggsl.data_nascita,
    soggsl.comune_nascita,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN comuni1.id
            WHEN o.tipo_impresa = 1 AND (stab.tipo_attivita = 2 OR stab.tipo_attivita = 3) THEN comunisoggind.id
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN comuni1.id
            WHEN o.tipo_impresa <> 1 AND (stab.tipo_attivita = 2 OR stab.tipo_attivita = 3) THEN comunisl.id
            ELSE NULL::integer
        END AS id_comune_richiesta,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN comuni1.nome
            WHEN o.tipo_impresa = 1 AND (stab.tipo_attivita = 2 OR stab.tipo_attivita = 3) THEN comunisoggind.nome
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN comuni1.nome
            WHEN o.tipo_impresa <> 1 AND (stab.tipo_attivita = 2 OR stab.tipo_attivita = 3) THEN comunisl.nome
            ELSE NULL::character varying
        END AS comune_richiesta,
    latt.macroarea,
    latt.aggregazione,
    latt.attivita AS linea_attivita,
    o.tipo_impresa AS id_tipo_impresa,
    o.tipo_societa AS id_topo_societa,
    sedestab.latitudine AS lat_stab,
    sedestab.longitudine AS long_stab,
    stab.cessazione_stabilimento AS stab_cessazione_stabilimento,
    stab.numero_registrazione_variazione,
    stab.partita_iva_variazione,
    stab.alt_id,
    ll.short_description AS permesso,
    i.via AS via_sede_stab,
    i.civico AS civico_sede_stab,
    i.toponimo AS toponimo_sede_stab,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.via::text)::character varying
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 2 THEN btrim(soggind.via::text)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.via::text)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 2 THEN btrim(sedeop.via::text)::character varying
            ELSE NULL::character varying
        END AS via_stabilimento_calcolata,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.civico)::character varying
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 2 THEN btrim(soggind.civico)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.civico)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 2 THEN btrim(sedeop.civico)::character varying
            ELSE NULL::character varying
        END AS civico_stabilimento_calcolato,
    comunisoggind.nome AS comune_residenza,
    stab.note_validazione,
    stab.stato_validazione,
    stab.sospensione_stabilimento,
    stab.data_inizio_sospensione,
        CASE
            WHEN ml.registrabili OR ml.riconoscibili IS NULL AND ml.registrabili IS NULL THEN 1
            WHEN ml.riconoscibili THEN 2
            ELSE NULL::integer
        END AS id_tipo_linea_produttiva,
        CASE
            WHEN apicoltura.id <= 0 OR apicoltura.id IS NULL THEN true
            WHEN apicoltura.id > 0 AND apicoltura.sincronizzato_bdn = true THEN true
            ELSE false
        END AS validabile,
    apicoltura.codice_azienda AS codice_azienda_apicoltura,
    stab.cessazione_stabilimento,
    rels.id_soggetto_fisico,
    latt.codice_macroarea,
    latt.codice_aggregazione,
    "substring"(latt.codice_attivita, length(latt.codice_macroarea) + length(latt.codice_aggregazione) + 3, length(latt.codice_attivita)) AS codice_attivita_only
   FROM suap_ric_scia_operatore o
     LEFT JOIN apicoltura_imprese apicoltura ON o.id = apicoltura.id_richiesta_suap
     LEFT JOIN suap_lookup_tipo_richiesta suapr ON suapr.code = o.id_tipo_richiesta
     JOIN suap_ric_scia_rel_operatore_soggetto_fisico rels ON rels.id_operatore = o.id AND rels.enabled
     JOIN suap_ric_scia_soggetto_fisico soggsl ON soggsl.id = rels.id_soggetto_fisico
     LEFT JOIN opu_indirizzo soggind ON soggind.id = soggsl.indirizzo_id
     LEFT JOIN comuni1 comunisoggind ON comunisoggind.id = soggind.comune
     LEFT JOIN opu_indirizzo sedeop ON sedeop.id = o.id_indirizzo
     LEFT JOIN comuni1 comunisl ON sedeop.comune = comunisl.id
     JOIN suap_ric_scia_stabilimento stab ON stab.id_operatore = o.id
     LEFT JOIN suap_ric_scia_relazione_stabilimento_linee_produttive lps ON lps.id_stabilimento = stab.id AND lps.enabled
     LEFT JOIN lookup_stato_lab stati ON stati.code = stab.stato
     LEFT JOIN ml8_linee_attivita_nuove_materializzata latt ON latt.id_nuova_linea_attivita = lps.id_linea_produttiva
     LEFT JOIN master_list_flag_linee_attivita ml ON ml.id_linea = latt.id_nuova_linea_attivita
     LEFT JOIN lookup_flusso_originale_ml lconf ON lconf.code = latt.id_lookup_configurazione_validazione
     LEFT JOIN lookup_ente_scia ll ON lconf.ente_validazione = ll.code
     LEFT JOIN opu_lookup_norme_master_list nor ON nor.code = latt.id_norma
     JOIN opu_indirizzo sedestab ON sedestab.id = stab.id_indirizzo
     LEFT JOIN comuni1 ON sedestab.comune = comuni1.id
     LEFT JOIN opu_indirizzo i ON i.id = stab.id_indirizzo
     LEFT JOIN lookup_province provsedestab ON provsedestab.code::text = sedestab.provincia::text
     LEFT JOIN lookup_province provsedeop ON provsedeop.code::text = sedeop.provincia::text
     LEFT JOIN lookup_province provsoggind ON provsoggind.code::text = soggind.provincia::text
     LEFT JOIN lookup_toponimi topsedeop ON sedeop.toponimo = topsedeop.code
     LEFT JOIN lookup_toponimi topsedestab ON sedestab.toponimo = topsedestab.code
     LEFT JOIN lookup_toponimi topsoggind ON soggind.toponimo = topsoggind.code
     LEFT JOIN lookup_opu_tipo_impresa loti ON loti.code = o.tipo_impresa
     LEFT JOIN lookup_opu_tipo_impresa_societa lotis ON lotis.code = o.tipo_societa
     LEFT JOIN lookup_stato_lab statilinea ON statilinea.code = lps.stato
     LEFT JOIN access acc ON acc.user_id = stab.entered_by
     LEFT JOIN contact con ON con.contact_id = acc.contact_id
     LEFT JOIN opu_lookup_tipologia_attivita lsa ON lsa.code = stab.tipo_attivita
     LEFT JOIN opu_lookup_tipologia_carattere lsc ON lsc.code = stab.tipo_carattere
     LEFT JOIN lookup_site_id asl ON asl.code = stab.id_asl
  WHERE o.trashed_date IS NULL AND stab.trashed_date IS NULL;

ALTER TABLE public.suap_ric_scia_operatori_denormalizzati_view
  OWNER TO postgres;

  
--10) inserimento gruppo regione e relativi ruoli
--select * from lookup_gruppo_ruoli  
insert into lookup_gruppo_ruoli (description, enabled, level) values ('GRUPPO REGIONE', true,0);
insert into rel_gruppo_ruoli  (id_gruppo, id_ruolo) values (15,40);
insert into rel_gruppo_ruoli  (id_gruppo, id_ruolo) values (15,324);
insert into rel_gruppo_ruoli  (id_gruppo, id_ruolo) values (15,53);
insert into rel_gruppo_ruoli  (id_gruppo, id_ruolo) values (15,27);
--NU.RE.CU.
--ORSA DIREZIONE
--Funzionari Regionali
--Responsabile Regione


--11 aggiornamento visibilita asl e visibilta regione per rev 8
select 'update master_list_flag_linee_attivita set visibilita_asl=false, visibilita_regione=true where rev=8 and codice_univoco='''||codice_univoco||''';',
* from ugp_gruppi_permessi_linee where id_gruppo  = 2; --43
select 'update master_list_flag_linee_attivita set visibilita_asl=true, visibilita_regione=false where rev=8 and codice_univoco='''||codice_univoco||''';',
* from ugp_gruppi_permessi_linee where id_gruppo  = 1; --306
update master_list_flag_linee_attivita set visibilita_asl  = true where rev=8 and visibilita_regione is null --120
