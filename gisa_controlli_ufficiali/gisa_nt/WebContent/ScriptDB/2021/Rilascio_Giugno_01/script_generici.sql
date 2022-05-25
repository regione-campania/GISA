
Chi: Rita MEle
COSA: inserito permesso per import in archiviati
QUANDO: 08/06/2021

insert into permission (category_id,permission,permission_view,permission_add,permission_edit,permission_delete,description , level,enabled,active)
values (12,'opu-import-archiviati',true,false,false,false,'IMPORT PER STABILIMENTI ARCHIVIATI',10,true,true)

select * from permission where permission  ilike '%import-archivi%'


insert into role_permission(role_id, permission_id, role_view) values(21,791,true);
insert into role_permission(role_id, permission_id, role_view) values(45,791,true);
insert into role_permission(role_id, permission_id, role_view) values(97,791,true);
insert into role_permission(role_id, permission_id, role_view) values(19,791,true);
insert into role_permission(role_id, permission_id, role_view) values(46,791,true);
insert into role_permission(role_id, permission_id, role_view) values(98,791,true);
insert into role_permission(role_id, permission_id, role_view) values(1,791,true);
insert into role_permission(role_id, permission_id, role_view) values(32,791,true);


-- Function: public.get_valori_anagrafica(integer)

-- DROP FUNCTION public.get_valori_anagrafica(integer);

CHI: Rita Mele
COSA: Aggiornamento dbi per tipo impresa recuperato da import
QUANDO: 11/06/2021
DROP FUNCTION get_valori_anagrafica(integer);
CREATE OR REPLACE FUNCTION public.get_valori_anagrafica(IN _altid integer)
  RETURNS TABLE(ragione_sociale_impresa text, partita_iva_impresa text, codice_fiscale_impresa text, tipo_impresa text, email_impresa text, telefono_impresa text, nome_rapp_legale text, cognome_rapp_legale text, sesso_rapp_legale text, nazione_nascita_rapp_legale text, "comune_nascita_rapp_legaleLabel" text, comune_nascita_estero_rapp_legale text, data_nascita_rapp_legale text, cf_rapp_legale text, email_rapp_legale text, telefono_rapp_legale text, nazione_residenza_rapp_legale text, provincia_residenza_rapp_legale text, comune_residenza_rapp_legale text, comune_residenza_estero_rapp_legale text, toponimo_residenza_rapp_legale text, via_residenza_rapp_legale text, civico_residenza_rapp_legale text, cap_residenza_rapp_legale text, nazione_sede_legale text, provincia_sede_legale text, comune_sede_legale text, comune_estero_sede_legale text, toponimo_sede_legale text, via_sede_legale text, civico_sede_legale text, cap_sede_legale text, nazione_stabilimento text, provincia_stabilimento text, comune_stabilimento text, toponimo_stabilimento text, via_stabilimento text, civico_stabilimento text, cap_stabilimento text, lat_stabilimento text, long_stabilimento text, numero_registrazione_stabilimento text, asl_stabilimento text, documento_identita text, note_stabilimento text, lat_sede_legale text, long_sede_legale text) AS
$BODY$
DECLARE
r RECORD;	
BEGIN
FOR 

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
o.ragione_sociale as ragione_sociale_impresa,
o.partita_iva as partita_iva_impresa,
o.codice_fiscale_impresa as codice_fiscale_impresa,
--lti.description as tipo_impresa,
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
left join opu_soggetto_fisico sogg on sogg.id = relosf.id_soggetto_fisico --and sogg.trashed_date is null
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

where s.alt_id = _altid

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
