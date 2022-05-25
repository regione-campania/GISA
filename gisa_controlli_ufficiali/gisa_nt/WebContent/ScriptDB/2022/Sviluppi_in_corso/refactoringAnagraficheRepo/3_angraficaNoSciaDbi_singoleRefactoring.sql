drop function public.get_valori_anagrafica(integer);

CREATE OR REPLACE FUNCTION public.get_valori_anagrafica(IN _altid integer DEFAULT -1::integer)
  RETURNS TABLE(alt_id integer, ragione_sociale_impresa text, partita_iva_impresa text, codice_fiscale_impresa text, tipo_impresa text, email_impresa text, telefono_impresa text, nome_rapp_legale text, cognome_rapp_legale text, sesso_rapp_legale text, nazione_nascita_rapp_legale text, "comune_nascita_rapp_legaleLabel" text, comune_nascita_estero_rapp_legale text, data_nascita_rapp_legale text, cf_rapp_legale text, email_rapp_legale text, telefono_rapp_legale text, nazione_residenza_rapp_legale text, provincia_residenza_rapp_legale text, comune_residenza_rapp_legale text, comune_residenza_estero_rapp_legale text, toponimo_residenza_rapp_legale text, via_residenza_rapp_legale text, civico_residenza_rapp_legale text, cap_residenza_rapp_legale text, nazione_sede_legale text, provincia_sede_legale text, comune_sede_legale text, comune_estero_sede_legale text, toponimo_sede_legale text, via_sede_legale text, civico_sede_legale text, cap_sede_legale text, nazione_stabilimento text, provincia_stabilimento text, comune_stabilimento text, toponimo_stabilimento text, via_stabilimento text, civico_stabilimento text, cap_stabilimento text, lat_stabilimento text, long_stabilimento text, numero_registrazione_stabilimento text, asl_stabilimento text, documento_identita text, note_stabilimento text, lat_sede_legale text, long_sede_legale text) AS
$BODY$
DECLARE
r RECORD;	
BEGIN
FOR 
alt_id, 
ragione_sociale_impresa  ,
partita_iva_impresa  ,
codice_fiscale_impresa  ,
tipo_impresa  ,
email_impresa  ,
telefono_impresa  ,
nome_rapp_legale  ,
cognome_rapp_legale  ,
sesso_rapp_legale  ,
nazione_nascita_rapp_legale  ,
"comune_nascita_rapp_legaleLabel"  ,
comune_nascita_estero_rapp_legale,
data_nascita_rapp_legale  ,
cf_rapp_legale  ,
email_rapp_legale  ,
telefono_rapp_legale  ,
nazione_residenza_rapp_legale  ,
provincia_residenza_rapp_legale  ,
comune_residenza_rapp_legale  ,
comune_residenza_estero_rapp_legale,
toponimo_residenza_rapp_legale  ,
via_residenza_rapp_legale  ,
civico_residenza_rapp_legale  ,
cap_residenza_rapp_legale  ,
nazione_sede_legale  ,
provincia_sede_legale  ,
comune_sede_legale  ,
comune_estero_sede_legale,
toponimo_sede_legale  ,
via_sede_legale  ,
civico_sede_legale  ,
cap_sede_legale  ,
nazione_stabilimento  ,
provincia_stabilimento  ,
comune_stabilimento  ,
toponimo_stabilimento  ,
via_stabilimento  ,
civico_stabilimento  ,
cap_stabilimento  ,
lat_stabilimento  ,
long_stabilimento,
numero_registrazione_stabilimento,
asl_stabilimento,
documento_identita, 
note_stabilimento,
lat_sede_legale, 
long_sede_legale


in
select distinct
s.alt_id,
o.ragione_sociale as ragione_sociale_impresa,
o.partita_iva as partita_iva_impresa,
o.codice_fiscale_impresa as codice_fiscale_impresa,
case 
	when length(lti.description) > 0 then lti.description
	when o.tipo_impresa = 1 then 'IMPRESA INDIVIDUALE'
	else (select description from lookup_tipo_impresa_societa where code=o.tipo_impresa)
end as tipo_impresa,
o.domicilio_digitale as email_impresa,
'' as telefono_impresa,

CASE WHEN ml8.codice ilike 'VET-AMBV-PU' or ml8.codice ilike 'VET-CLIV-PU' or ml8.codice ilike 'VET-OSPV-PU' 
	THEN concat('DIRETTORE GENERALE ASL ', ls.description) 
	ELSE sogg.nome 
	END as nome_rapp_legale,

sogg.cognome as cognome_rapp_legale,
sogg.sesso as sesso_rapp_legale,
CASE WHEN sogg.provenienza_estera THEN naz.description ELSE 'ITALIA' END as nazione_nascita_rapp_legale,
sogg.comune_nascita as "comune_nascita_rapp_legaleLabel",
CASE WHEN sogg.provenienza_estera THEN sogg.comune_nascita ELSE '' END as comune_nascita_estero_rapp_legale,
to_char(sogg.data_nascita, 'dd/MM/yyyy') as data_nascita_rapp_legale,
sogg.codice_fiscale as cf_rapp_legale,
sogg.email as email_rapp_legale,
sogg.telefono as telefono_rapp_legale,

soggind.nazione as nazione_residenza_rapp_legale,
CASE WHEN soggind.nazione ilike 'ITALIA' then soggprov.description else '' end as provincia_residenza_rapp_legale,
case when soggind.comune > 0 and soggind.nazione ilike 'ITALIA' then soggcom.nome else soggind.comune_testo end as comune_residenza_rapp_legale,
case when soggind.nazione ilike 'ITALIA' then '' else soggind.comune_testo end as comune_residenza_estero_rapp_legale,
CASE WHEN soggind.nazione ilike 'ITALIA' then soggtop.description else '' end as toponimo_residenza_rapp_legale,
CASE WHEN soggind.nazione ilike 'ITALIA' then soggind.via else '' end as via_residenza_rapp_legale,
CASE WHEN soggind.nazione ilike 'ITALIA' then soggind.civico else '' end as civico_residenza_rapp_legale,
CASE WHEN soggind.nazione ilike 'ITALIA' then soggind.cap else '' end as cap_residenza_rapp_legale,

opind.nazione as nazione_sede_legale,
CASE WHEN opind.nazione ilike 'ITALIA' then opprov.description else '' end as provincia_sede_legale,
case when opind.comune > 0 and opind.nazione ilike 'ITALIA' then opcom.nome else opind.comune_testo end as comune_sede_legale,
case when opind.nazione ilike 'ITALIA' then '' else opind.comune_testo end as comune_estero_sede_legale,
CASE WHEN opind.nazione ilike 'ITALIA' then optop.description else '' end as toponimo_sede_legale,
CASE WHEN opind.nazione ilike 'ITALIA' then opind.via else '' end as via_sede_legale,
CASE WHEN opind.nazione ilike 'ITALIA' then opind.civico else '' end as civico_sede_legale,
CASE WHEN opind.nazione ilike 'ITALIA' then opind.cap else '' end as cap_sede_legale,

stabind.nazione as  nazione_stabilimento,
stabprov.description as provincia_stabilimento,
stabcom.nome as comune_stabilimento,
stabtop.description as toponimo_stabilimento,
stabind.via as via_stabilimento,
stabind.civico as civico_stabilimento,
stabind.cap as cap_stabilimento,
stabind.latitudine as lat_stabilimento,
stabind.longitudine as long_stabilimento,
s.numero_registrazione,
ls.description as asl_stabilimento,
sogg.documento_identita as documento_identita, 
s.note as note_stabilimento,
opind.latitudine as lat_sede_legale, 
opind.longitudine as long_sede_legale

from opu_stabilimento s
join opu_operatore o on o.id = s.id_operatore and o.trashed_date is null
join opu_rel_operatore_soggetto_fisico relosf on relosf.id_operatore = o.id and relosf.enabled and relosf.data_fine is null
left join opu_soggetto_fisico sogg on sogg.id = relosf.id_soggetto_fisico and sogg.trashed_date is null
join opu_indirizzo stabind on stabind.id = s.id_indirizzo
left join opu_indirizzo opind on opind.id = o.id_indirizzo
left join opu_indirizzo soggind on soggind.id = sogg.indirizzo_id
join opu_relazione_stabilimento_linee_produttive rel on rel.id_stabilimento = s.id and rel.enabled
join ml8_linee_attivita_nuove_materializzata ml8 on rel.id_linea_produttiva = ml8.id_nuova_linea_attivita

left join lookup_tipo_impresa_societa lti on lti.code = o.tipo_societa

left join lookup_province soggprov on soggprov.code::text = soggind.provincia
left join comuni1 soggcom on soggcom.id = soggind.comune
left join lookup_toponimi soggtop on soggtop.code = soggind.toponimo

left join lookup_province opprov on opprov.code::text = opind.provincia
left join comuni1 opcom on opcom.id = opind.comune
left join lookup_toponimi optop on optop.code = opind.toponimo

left join lookup_province stabprov on stabprov.code::text = stabind.provincia
left join comuni1 stabcom on stabcom.id = stabind.comune
left join lookup_toponimi stabtop on stabtop.code = stabind.toponimo

left join lookup_nazioni naz on naz.code = sogg.id_nazione_nascita
left join lookup_site_id ls on s.id_asl = ls.code

where 1 = 1 and ( _altid = -1 or s.alt_id = _altid ) -- gestione valori opzionali

    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_valori_anagrafica(integer)
  OWNER TO postgres;

-- select * from public.get_valori_anagrafica(-1) limit 3;
-- Function: public.get_valori_anagrafica_extra(integer)

-- DROP FUNCTION public.get_valori_anagrafica_extra(integer);
drop function  public.get_valori_anagrafica_extra(integer);

CREATE OR REPLACE FUNCTION public.get_valori_anagrafica_extra(IN _altid integer default -1::integer)
  RETURNS TABLE(alt_id integer, inserito_da text, inserito_il text, categoria_di_rischio text, data_prossimo_controllo text, data_inizio text, stato text, tipo_attivita text, note text, categorizzabili text, note_impresa text) AS
$BODY$
DECLARE
	r RECORD;
	descrizioneStato text;
	idLineaAttiva integer;
	flagCategorizzabili boolean;
	id_stato_stab integer;	
	_id_stabilimento integer;
	_cat_di_rischio integer;
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
		where  oprel.enabled and  oprel.id_stabilimento = (select os.id from opu_stabilimento os where os.alt_id = _altid) order by flag.categorizzabili desc;

	select  coalesce(categoria_rischio,-1) into _cat_di_rischio from opu_stabilimento where id = _id_stabilimento;
	IF _cat_di_rischio < 0 and flagCategorizzabili THEN
		select coalesce(max(categoria_rischio_default),3)::integer into _cat_di_rischio 
			from opu_relazione_stabilimento_linee_produttive rel 
				join ml8_linee_attivita_nuove_materializzata ml8 on ml8.id_nuova_linea_attivita = rel.id_linea_produttiva 
				left join master_list_flag_linee_attivita flag on flag.id_linea = ml8.id_nuova_linea_attivita
			where flag.categorizzabili and rel.enabled and rel.id_stabilimento = _id_stabilimento;
		update opu_stabilimento set categoria_rischio = _cat_di_rischio where id = _id_stabilimento;
	END IF;
		

	FOR
	alt_id, inserito_da, inserito_il, categoria_di_rischio, data_prossimo_controllo, data_inizio, stato, tipo_attivita, note, categorizzabili, note_impresa
	in
	select 
        s.alt_id,
	CASE WHEN acc.user_id > 0 THEN 
		concat_ws(' ', con.namefirst, con.namelast) 
	ELSE concat_ws(' ', conext.namefirst, conext.namelast) 
	END as inserito_da,
	to_char(s.entered, 'dd/MM/yyyy') as inserito_il,

	coalesce(s.categoria_rischio::text,'')::text as categoria_di_rischio,
	
	CASE WHEN s.categoria_rischio is not null THEN 
		to_char(COALESCE(s.data_prossimo_controllo,  now()), 'dd/MM/yyyy') 
	ELSE ''
	END as data_prossimo_controllo,
	to_char(s.data_inizio_attivita, 'dd/MM/yyyy') as data_inizio,
	descrizioneStato as stato,
	lti.description as tipo_attivita,
	--s.note::text as note,
	CASE WHEN (length(trim(s.note)) <> 0) THEN trim( regexp_replace(s.note, '\r|\n', ' ', 'g'))::text ELSE ''::text END as note,
	flagCategorizzabili::text as categorizzabili,
	CASE WHEN (length(trim(o.note)) <> 0) THEN trim( regexp_replace(o.note, '\r|\n', ' ', 'g'))::text ELSE ''::text END as note_impresa


	from opu_stabilimento s
	join opu_operatore o on s.id_operatore = o.id
	left join access_ acc on acc.user_id = s.entered_by
	left join contact_ con on con.contact_id = acc.contact_id
	left join access_ext_ accext on accext.user_id = s.entered_by
	left join contact_ext_ conext on conext.contact_id = accext.contact_id
	left join lookup_tipo_attivita lti on lti.code = s.tipo_attivita

	where 1 = 1 and ( _altid = -1 or s.alt_id = _altid ) -- gestione valori opzionali

	    LOOP
		RETURN NEXT;
	     END LOOP;
	     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_valori_anagrafica_extra(integer)
  OWNER TO postgres;




 --select * from public.get_valori_anagrafica_extra(-1) limit 2;


DROP FUNCTION public.get_valori_anagrafica_linee(integer);
-- Function: public.get_valori_anagrafica_linee(integer)

-- DROP FUNCTION public.get_valori_anagrafica_linee(integer);

CREATE OR REPLACE FUNCTION public.get_valori_anagrafica_linee(IN _altid integer DEFAULT (- 1))
  RETURNS TABLE(alt_id integer, norma text, macroarea text, aggregazione text, attivita text, livelli_aggiuntivi text, carattere text, data_inizio text, data_fine text, numero_registrazione text, gia_codificato_come text, cun text, stato text, variazione_stato text) AS
$BODY$
DECLARE
r RECORD;	
BEGIN
FOR 

alt_id, norma , macroarea , aggregazione , attivita , livelli_aggiuntivi, carattere , data_inizio , data_fine , numero_registrazione , gia_codificato_come , cun , stato, variazione_stato


in
select 
s.alt_id,
norme.description as norma,
ml8.macroarea as macroarea,
ml8.aggregazione as aggregazione,
replace(replace(ml8.attivita::text,'"',''''), '''','''''') as attivita,

string_agg(distinct concat(l3.valore || '-><br/>' || l2.valore || '-><br/>' || l.valore || '<br/><br/>'), '') as livelli_aggiuntivi,
	 
coalesce(oltc.description,'PERMANENTE') as carattere,
to_char(rel.data_inizio, 'dd/MM/yyyy') as data_inizio,
to_char(rel.data_fine, 'dd/MM/yyyy') as data_fine,

CASE WHEN (flag.categorizzabili or flag.no_scia is false) THEN rel.numero_registrazione ELSE null::text END as numero_registrazione,

CASE WHEN (flag.categorizzabili or flag.no_scia is false) THEN rel.codice_ufficiale_esistente ELSE null::text END as gia_codificato_come,

CASE WHEN (rel.codice_nazionale is null or trim(rel.codice_nazionale) = '') THEN null::text ELSE rel.codice_nazionale END as cun,

CASE WHEN (flag.no_scia and lsl.code = 4) THEN concat(lsl.description, '/REVOCATO') ELSE lsl.description END as stato,

CASE WHEN string_agg(concat(lvso.description, to_char(vsos.data_variazione, 'dd/MM/yyyy')), '') <> '' then 
string_agg(concat(' Data ', lvso.description, ' ', to_char(vsos.data_variazione, 'dd/MM/yyyy')), '-><br/>' order by vsos.data_variazione ,vsos.data ) else 
 '' end 
as variazione_stato

from opu_stabilimento s
join opu_relazione_stabilimento_linee_produttive rel on rel.id_stabilimento = s.id and rel.enabled
left join ml8_linee_attivita_nuove_materializzata ml8 on ml8.id_nuova_linea_attivita = rel.id_linea_produttiva
left join opu_lookup_tipologia_carattere oltc on oltc.code = rel.tipo_carattere
left join opu_lookup_norme_master_list norme on norme.code = ml8.id_norma
left join lookup_stato_lab lsl on lsl.code = rel.stato
left join master_list_configuratore_livelli_aggiuntivi_values v on rel.id = v.id_istanza and v.checked
left join master_list_configuratore_livelli_aggiuntivi l on l.id = v.id_configuratore_livelli_aggiuntivi 
left join master_list_configuratore_livelli_aggiuntivi l2 on l2.id = l.id_padre
left join master_list_configuratore_livelli_aggiuntivi l3 on l3.id = l2.id_padre 
left join master_list_flag_linee_attivita flag on ml8.id_nuova_linea_attivita = flag.id_linea
left join variazione_stato_operazioni_storico vsos on vsos.id_stabilimento = s.id and vsos.id_rel_stab_lp = rel.id 
left join lookup_variazione_stato_operazioni lvso on lvso.code = vsos.id_operazione

where 1 = 1 and ( _altid = -1 or s.alt_id = _altid ) -- gestione valori opzionali

group by s.alt_id, norme.description, ml8.macroarea, ml8.aggregazione, ml8.attivita, oltc.description, rel.data_inizio, rel.data_fine, flag.categorizzabili, flag.no_scia, rel.numero_registrazione, rel.codice_ufficiale_esistente, rel.codice_nazionale, 
lsl.description, ml8.norma, lsl.code
order by ml8.norma asc

    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_valori_anagrafica_linee(integer)
  OWNER TO postgres;


--select * from public.get_valori_anagrafica_linee(-1) limit 2

drop function public.get_valori_anagrafica_estesi(integer);
CREATE OR REPLACE FUNCTION public.get_valori_anagrafica_estesi(IN _altid integer default -1::integer)
  RETURNS TABLE(alt_id integer, id_rel_stab_lp integer, nome_campo text, valore_campo text) AS
$BODY$
DECLARE
r RECORD;	
BEGIN
FOR 
alt_id, id_rel_stab_lp, nome_campo, valore_campo
in

select distinct
s.alt_id,
e.id_opu_rel_stab_lp, c.html_name, 
case when c.html_type <> 'select' then e.valore_campo
else 
EXEC('select string_agg(description, '', '') from '||c.sql_origine_lookup||' where code in('||e.valore_campo||')') end as valore_campo
FROM opu_campi_estesi e
left join configuratore_template_no_scia c on c.id = e.id_campo_configuratore
left join opu_relazione_stabilimento_linee_produttive rel on rel.id = e.id_opu_rel_stab_lp
left join opu_stabilimento s on s.id = rel.id_stabilimento
where rel.enabled and e.trashed_date is null and 
( _altid = -1 or s.alt_id = _altid ) -- gestione valori opzionali

    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_valori_anagrafica_estesi(integer)
  OWNER TO postgres;
  
--select * from public.get_valori_anagrafica_estesi(-1)
 -- select * from public.get_valori_anagrafica(-1) limit 2  
-- select * from anagrafica.gestione_id_alternativo(344548,2)
--select * from public.get_valori_anagrafica_estesi(-1)