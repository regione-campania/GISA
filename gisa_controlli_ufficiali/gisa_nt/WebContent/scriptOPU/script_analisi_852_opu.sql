
CHI: Rita Mele
COSA: creazione vista imprese da opu concatenando gli indirizzi degli stabilimenti
QUANDO: 11/01/2016

create view imprese_opu_view as (
select string_agg(a.toponimo||','||via||','||civico||','||a.comune,'|') as indirizzo_stabilimenti, a.id_operatore, a.partita_iva, a.codice_fiscale_impresa, a.ragione_sociale, a.data_inserimento 
from (
select 
case when lt.description is not null and lt.description !='' then lt.description
else '' 
end as toponimo,
case when oi.via is not null and oi.via != '' then oi.via 
else '' 
end as via,
case when c.nome is not null and c.nome !='' then c.nome
else ''
end as comune,
case when oi.civico is not null and oi.civico !='' then oi.civico
else ''
end as civico,
op.partita_iva, op.id as id_operatore, op.codice_fiscale_impresa, op.ragione_sociale, to_char(op.entered,'dd-mm-yyyy') as data_inserimento
from opu_operatore op
join opu_stabilimento os on os.id_operatore  = op.id
join opu_indirizzo oi on oi.id=os.id_indirizzo 
join lookup_toponimi lt on lt.code=oi.toponimo
join comuni1 c on c.id=oi.comune
where op.trashed_date is null and os.trashed_date is null 
--and partita_iva = '09099270960'
group by  partita_iva, op.ragione_sociale, op.id,data_inserimento, oi.via,oi.civico,c.nome, lt.description
order by partita_iva) a
group by partita_iva, ragione_sociale,data_inserimento, id_operatore, toponimo, codice_fiscale_impresa
)

--creazione vista imprese 852 attive che hanno almeno un CU e hanno una partita iva valida

CREATE OR REPLACE VIEW public.import_imprese_852 AS 
 SELECT a.org_id,
    btrim(a.partita_iva::text) AS partita_iva,
    a.site_id as id_asl,
    btrim(a.numero_registrazione::text) AS numero_registrazione,
    a.tipo_impresa,
    a.denominazione_852,
    a.stato_impresa,
    a.data_inizio_attivita,
    a.data_fine_attivita,
    a.note,
    a.descrizione_breve_attivita,
    a.email_rappresentante,
    a.telefono_rappresentante,
    a.data_nascita_rappresentante,
    a.luogo_nascita_rappresentante,
    a.comune_residenza_rapp,
    a.indirizzo_residenza_rapp,
    a.prov_residenza_rapp,
    a.categoria_rischio,
    a.indirizzo_org,
    a.latitudine,
    a.longitudine,
    a.indirizzo_legale,
    a.comune_legale,
    a.prov_legale,
    a.cap_legale
   FROM ( SELECT o.org_id,
            o.partita_iva,
            o.site_id,
            o.account_number AS numero_registrazione,
            o.tipo_dest AS tipo_impresa,
            o.name AS denominazione_852,
            oa1.addrline1 AS indirizzo_legale,
            oa1.city AS comune_legale,
            oa1.state AS prov_legale,
            oa1.postalcode AS cap_legale,
                CASE
                    WHEN o.source = 1 AND o.data_fine_carattere IS NOT NULL AND o.data_fine_carattere < 'now'::text::date OR o.source <> 1 AND o.cessato = 1 THEN 'Cessato'::text
                    WHEN o.source = 1 AND (o.data_fine_carattere IS NULL OR o.data_fine_carattere > 'now'::text::date) OR o.source <> 1 AND o.cessato = 0 THEN 'in Attivita'::text
                    WHEN o.source <> 1 AND o.cessato <> 1 AND o.cessato <> 0 THEN 'Sospeso'::text
                    ELSE 'In attivita'::text
                END AS stato_impresa,
                CASE
                    WHEN o.data_in_carattere IS NOT NULL AND o.data_fine_carattere IS NOT NULL THEN to_date(o.data_in_carattere::text, 'yyyy/mm/dd'::text)
                    ELSE to_date(o.datapresentazione::text, 'yyyy/mm/dd'::text)
                END AS data_inizio_attivita,
                CASE
                    WHEN o.source = 1 AND o.data_fine_carattere IS NOT NULL AND o.data_fine_carattere < 'now'::text::date THEN o.data_fine_carattere
                    WHEN o.source <> 1 AND (o.cessato = 1 OR o.cessato = 2) THEN o.contract_end
                    ELSE NULL::timestamp without time zone
                END AS data_fine_attivita,
            o.notes AS note,
            o.alert AS descrizione_breve_attivita,
            o.email_rappresentante,
            o.telefono_rappresentante,
            o.data_nascita_rappresentante,
            o.luogo_nascita_rappresentante,
            o.city_legale_rapp AS comune_residenza_rapp,
            o.address_legale_rapp AS indirizzo_residenza_rapp,
            o.prov_legale_rapp AS prov_residenza_rapp,
            o.categoria_rischio,
            (
                CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa5.addrline1, 'N.D.'::character varying)
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa7.addrline1, 'N.D.'::character varying)
                    ELSE 'N.D.'::character varying
                END::text || ','::text) ||
                CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN upper(regexp_replace(regexp_replace(COALESCE(oa5.city, 'N.D.'::character varying)::text, '''''|,|\r|\n|"|-| {2,}'::text, ' '::text, 'g'::text), '°'::text, '.'::text, 'g'::text))::character varying
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN upper(regexp_replace(regexp_replace(COALESCE(oa7.city, 'N.D.'::character varying)::text, '''''|,|\r|\n|"|-| {2,}'::text, ' '::text, 'g'::text), '°'::text, '.'::text, 'g'::text))::character varying
                    ELSE 'N.D.'::character varying
                END::text AS indirizzo_org,
                CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN oa5.latitude
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN oa7.latitude
                END as latitudine,
                CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN oa5.longitude
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN oa7.longitude
                END as longitudine
           FROM organization o
             LEFT JOIN organization_address oa5 ON oa5.org_id = o.org_id AND oa5.address_type = 5
             LEFT JOIN organization_address oa7 ON oa7.org_id = o.org_id AND oa7.address_type = 7
             LEFT JOIN organization_address oa1 ON oa1.org_id = o.org_id AND oa1.address_type = 1
             JOIN ticket t ON t.org_id = o.org_id
          WHERE o.trashed_date IS NULL AND o.tipologia = 1 AND o.import_opu = false AND t.trashed_date IS NULL AND o.name IS NOT NULL AND o.name::text <> ''::text AND o.partita_iva IS NOT NULL AND o.partita_iva <> ''::bpchar AND length(o.partita_iva::text) = 11 AND o.partita_iva !~ similar_escape('[0]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[1]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[2]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[3]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[4]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[5]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[6]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[7]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[8]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[9]{1,11}'::text, NULL::text)
          GROUP BY o.org_id, o.partita_iva, o.name, oa1.addrline1, oa1.city, oa1.state, oa1.postalcode, ((
                CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa5.addrline1, 'N.D.'::character varying)
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa7.addrline1, 'N.D.'::character varying)
                    ELSE 'N.D.'::character varying
                END::text || ','::text) ||
                CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN upper(regexp_replace(regexp_replace(COALESCE(oa5.city, 'N.D.'::character varying)::text, '''''|,|\r|\n|"|-| {2,}'::text, ' '::text, 'g'::text), '°'::text, '.'::text, 'g'::text))::character varying
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN upper(regexp_replace(regexp_replace(COALESCE(oa7.city, 'N.D.'::character varying)::text, '''''|,|\r|\n|"|-| {2,}'::text, ' '::text, 'g'::text), '°'::text, '.'::text, 'g'::text))::character varying
                    ELSE 'N.D.'::character varying
                END::text), CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN oa5.latitude
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN oa7.latitude
                END,
                CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN oa5.longitude
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN oa7.longitude
                END
         HAVING count(t.ticketid) > 0) a
  WHERE a.stato_impresa <> 'Cessato'::text AND a.stato_impresa <> 'Sospeso'::text;


--creazione vista imprese 852 con attività fuori mapping (00.00.00 oppure non mappata da ORSA o da eliminare secondo indicazioni ORSA.)
--Per questi 3 casi è stato effettuata una estrazione ad hoc per le ASL in data 11/01/2016
create view imprese_852_fuori_mapping as
select distinct(id_operatore), ateco  from (
select cod.description as ateco, i.org_id as id_operatore, i.id as id_la, i.id_attivita_masterlist as id_map, * FROM la_imprese_linee_attivita i, la_rel_ateco_attivita rel, la_linee_attivita la, lookup_codistat cod
		left join mapping_codice_ateco_master_list_2015_2016 map on map.codice_ateco = cod.description
                WHERE i.trashed_date IS NULL  
and cod.description in (	
'00.00.00',
'01.41.00',
'01.42.00',
'01.49.90',
'01.63.00',
'01.64.01',
'01.64.09',
'03.12.00',
'03.21.00',
'03.22.00',
'10.11.00',
'10.41.30',
'10.51.10',
'10.86.00',
'10.89.09',
'10.91.00',
'10.92.00',
'46.11.06',
'46.21.22',
'46.23.00',
'46.39.20',
'47.73.10',
'47.73.20',
'47.91.10',
'47.91.20',
'47.91.30',
'47.99.10',
'56.10.50',
'82.92.10')
               --AND org_id = ?
                AND i.id_rel_ateco_attivita=rel.id  
                AND rel.id_linee_attivita=la.id  
                AND rel.id_lookup_codistat=cod.code
) a left join organization o on o.org_id = a.org_id
 join ticket t on t.org_id = o.org_id
where o.trashed_date is null and o.import_opu= false and o.tipologia=1 and t.trashed_date is null
group by ateco, id_operatore
having count(ticketid) > 0
order by ateco

--funzione per verificare la similarità tra indirizzi tra quello dell'852 e dell'opu partendo da una partita_iva
CREATE OR REPLACE FUNCTION get_list_852_opu_from_partita_iva(IN partita_iva_input character varying, soglia_leven integer)
  RETURNS TABLE(partita_iva character varying,codice_fiscale_impresa character varying, org_id integer, 
  denominazione_852 character varying, indirizzo_852 text, denominazione_opu text, opu_id integer, indirizzo_stabilimenti text) AS
$BODY$
DECLARE
	r RECORD;
BEGIN
	FOR partita_iva ,codice_fiscale_impresa , org_id ,denominazione_852, indirizzo_852, denominazione_opu, opu_id,  indirizzo_stabilimenti 
	in
        SELECT a.partita_iva ,a.codice_fiscale_impresa , a.org_id ,a.denominazione_852, a.indirizzo_852, a.denominazione_opu, a.id_operatore, a.indirizzo_stabilimenti 
        from (
		select 
		regexp_replace(im.indirizzo_org, '  ', ' ', 'g') as indirizzo_852_n,
		opu.partita_iva, opu.codice_fiscale_impresa, im.org_id, im.denominazione_852, im.indirizzo_org as indirizzo_852, stato_impresa, opu.ragione_sociale as denominazione_opu, opu.id_operatore, opu.indirizzo_stabilimenti
		from imprese_opu_view  opu
		join import_imprese_852 im on im.partita_iva=opu.partita_iva
		where im.org_id not in (select id_operatore from imprese_852_fuori_mapping) )a
		where levenshtein(a.indirizzo_852_n,a.indirizzo_stabilimenti) <=soglia_leven and a.partita_iva= partita_iva_input
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION get_list_852_opu_from_partita_iva(character varying, integer)
  OWNER TO postgres;

--stessa funzione di prima ma senza partita_iva input

CREATE OR REPLACE FUNCTION get_list_852_opu_indirizzi_simili(soglia_leven integer)
  RETURNS TABLE(partita_iva character varying, codice_fiscale_impresa character varying, org_id integer, denominazione_852 character varying, indirizzo_852 text, 
   denominazione_opu text, opu_id integer, indirizzo_stabilimenti text) AS
$BODY$
DECLARE
	r RECORD;
BEGIN
	FOR partita_iva ,codice_fiscale_impresa , org_id ,denominazione_852, indirizzo_852 ,  denominazione_opu , opu_id , indirizzo_stabilimenti 
	in
        SELECT a.partita_iva ,a.codice_fiscale_impresa , a.org_id ,a.denominazione_852, a.indirizzo_852 , a.denominazione_opu , a.opu_id,  a.indirizzo_stabilimenti 
        from (
		select 
		regexp_replace(im.indirizzo_org, '  ', ' ', 'g') as indirizzo_852_n,
		opu.partita_iva, opu.codice_fiscale_impresa, im.org_id, im.denominazione_852, im.indirizzo_org as indirizzo_852, stato_impresa, opu.ragione_sociale as denominazione_opu, opu.id_operatore as opu_id, opu.indirizzo_stabilimenti
		from imprese_opu_view  opu
		join import_imprese_852 im on im.partita_iva=opu.partita_iva
		where im.org_id not in (select id_operatore from imprese_852_fuori_mapping) )a
		where levenshtein(a.indirizzo_852_n,a.indirizzo_stabilimenti) <=soglia_leven
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION get_list_852_opu_indirizzi_simili (integer)
  OWNER TO postgres;

----------------------------------------------
CHI: Rita Mele
COSA: Script per import imprese
QUANDO: 02/02/2016

CREATE OR REPLACE VIEW public.import_imprese_852 AS 
 SELECT a.org_id,
    btrim(a.partita_iva::text) AS partita_iva,
    a.site_id AS id_asl,
    btrim(a.numero_registrazione::text) AS numero_registrazione,
    a.tipo_impresa,
    a.denominazione_852,
    a.stato_impresa,
    a.data_inizio_attivita,
    a.data_fine_attivita,
    a.note,
    a.descrizione_breve_attivita,
    a.email_rappresentante,
    a.telefono_rappresentante,
    a.data_nascita_rappresentante,
    a.luogo_nascita_rappresentante,
    a.comune_residenza_rapp,
    a.indirizzo_residenza_rapp,
    a.prov_residenza_rapp,
    a.categoria_rischio,
    a.indirizzo_org,
    a.latitudine,
    a.longitudine,
    a.indirizzo_legale,
    a.comune_legale,
    a.prov_legale,
    a.cap_legale, 
    a.address_id_operativo,
    a.address_id_legale
   FROM ( SELECT o.org_id,
            o.partita_iva,
            o.site_id,
            o.account_number AS numero_registrazione,
            o.tipo_dest AS tipo_impresa,
            o.name AS denominazione_852,
            oa1.addrline1 AS indirizzo_legale,
            oa1.city AS comune_legale,
            oa1.state AS prov_legale,
            oa1.postalcode AS cap_legale,
                CASE
                    WHEN o.source = 1 AND o.data_fine_carattere IS NOT NULL AND o.data_fine_carattere < 'now'::text::date OR o.source <> 1 AND o.cessato = 1 THEN 'Cessato'::text
                    WHEN o.source = 1 AND (o.data_fine_carattere IS NULL OR o.data_fine_carattere > 'now'::text::date) OR o.source <> 1 AND o.cessato = 0 THEN 'in Attivita'::text
                    WHEN o.source <> 1 AND o.cessato <> 1 AND o.cessato <> 0 THEN 'Sospeso'::text
                    ELSE 'In attivita'::text
                END AS stato_impresa,
                CASE
                    WHEN o.data_in_carattere IS NOT NULL AND o.data_fine_carattere IS NOT NULL THEN to_date(o.data_in_carattere::text, 'yyyy/mm/dd'::text)
                    ELSE to_date(o.datapresentazione::text, 'yyyy/mm/dd'::text)
                END AS data_inizio_attivita,
                CASE
                    WHEN o.source = 1 AND o.data_fine_carattere IS NOT NULL AND o.data_fine_carattere < 'now'::text::date THEN o.data_fine_carattere
                    WHEN o.source <> 1 AND (o.cessato = 1 OR o.cessato = 2) THEN o.contract_end
                    ELSE NULL::timestamp without time zone
                END AS data_fine_attivita,
            o.notes AS note,
            o.alert AS descrizione_breve_attivita,
            o.email_rappresentante,
            o.telefono_rappresentante,
            o.data_nascita_rappresentante,
            o.luogo_nascita_rappresentante,
            o.city_legale_rapp AS comune_residenza_rapp,
            o.address_legale_rapp AS indirizzo_residenza_rapp,
            o.prov_legale_rapp AS prov_residenza_rapp,
            o.categoria_rischio,
            (
                CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa5.addrline1, 'N.D.'::character varying)
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa7.addrline1, 'N.D.'::character varying)
                    ELSE 'N.D.'::character varying
                END::text || ','::text) ||
                CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN upper(regexp_replace(regexp_replace(COALESCE(oa5.city, 'N.D.'::character varying)::text, '''''|,|\r|\n|"|-| {2,}'::text, ' '::text, 'g'::text), '°'::text, '.'::text, 'g'::text))::character varying
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN upper(regexp_replace(regexp_replace(COALESCE(oa7.city, 'N.D.'::character varying)::text, '''''|,|\r|\n|"|-| {2,}'::text, ' '::text, 'g'::text), '°'::text, '.'::text, 'g'::text))::character varying
                    ELSE 'N.D.'::character varying
                END::text AS indirizzo_org,
                CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN oa5.latitude
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN oa7.latitude
                    ELSE NULL::double precision
                END AS latitudine,
                CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN oa5.longitude
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN oa7.longitude
                    ELSE NULL::double precision
                END AS longitudine,
                CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN oa5.address_id
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN oa7.address_id
                    ELSE NULL::double precision
                END AS address_id_operativo,
                oa1.address_id AS address_id_legale
           FROM organization o
             LEFT JOIN organization_address oa5 ON oa5.org_id = o.org_id AND oa5.address_type = 5
             LEFT JOIN organization_address oa7 ON oa7.org_id = o.org_id AND oa7.address_type = 7
             LEFT JOIN organization_address oa1 ON oa1.org_id = o.org_id AND oa1.address_type = 1
             JOIN ticket t ON t.org_id = o.org_id
          WHERE o.trashed_date IS NULL AND o.tipologia = 1 AND o.import_opu = false AND t.trashed_date IS NULL AND o.name IS NOT NULL AND o.name::text <> ''::text AND o.partita_iva IS NOT NULL AND o.partita_iva <> ''::bpchar AND length(o.partita_iva::text) = 11 AND o.partita_iva !~ similar_escape('[0]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[1]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[2]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[3]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[4]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[5]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[6]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[7]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[8]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[9]{1,11}'::text, NULL::text)
          GROUP BY o.org_id, o.partita_iva, o.name, oa1.addrline1, oa1.city, oa1.state, oa1.postalcode, ((
                CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa5.addrline1, 'N.D.'::character varying)
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa7.addrline1, 'N.D.'::character varying)
                    ELSE 'N.D.'::character varying
                END::text || ','::text) ||
                CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN upper(regexp_replace(regexp_replace(COALESCE(oa5.city, 'N.D.'::character varying)::text, '''''|,|\r|\n|"|-| {2,}'::text, ' '::text, 'g'::text), '°'::text, '.'::text, 'g'::text))::character varying
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN upper(regexp_replace(regexp_replace(COALESCE(oa7.city, 'N.D.'::character varying)::text, '''''|,|\r|\n|"|-| {2,}'::text, ' '::text, 'g'::text), '°'::text, '.'::text, 'g'::text))::character varying
                    ELSE 'N.D.'::character varying
                END::text), (
                CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN oa5.latitude
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN oa7.latitude
                    ELSE NULL::double precision
                END), (
                CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN oa5.longitude
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN oa7.longitude
                    ELSE NULL::double precision
                END),
                CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN oa5.address_id
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN oa7.address_id
                    ELSE NULL::double precision
                END,
                oa1.address_id 
         HAVING count(t.ticketid) > 0) a
  WHERE a.stato_impresa <> 'Cessato'::text AND a.stato_impresa <> 'Sospeso'::text;

ALTER TABLE public.import_imprese_852
  OWNER TO postgres;


-- View: public.imprese_852_con_ateco_mappati

-- DROP VIEW public.imprese_852_con_ateco_mappati;

CREATE OR REPLACE VIEW public.imprese_852_con_ateco_mappati AS 
 SELECT cod.description AS ateco,
    i.org_id AS id_operatore,
    i.id_attivita_masterlist AS id_map,
    o.name AS denominazione,
    btrim(o.partita_iva::text) AS partita_iva,
    o.codice_fiscale AS codice_fiscale_impresa,
    o.nome_rappresentante,
    o.cognome_rappresentante,
    btrim(o.codice_fiscale_rappresentante::text) AS codice_fiscale_rappresentante
   FROM la_imprese_linee_attivita i,
    la_rel_ateco_attivita rel,
    la_linee_attivita la,
    organization o,
    lookup_codistat cod
     LEFT JOIN mapping_codice_ateco_master_list_2015_2016 map ON map.codice_ateco::text = cod.description::text
  WHERE i.trashed_date IS NULL AND o.trashed_date IS NULL AND o.import_opu = false AND o.tipologia = 1 AND o.partita_iva IS NOT NULL AND o.partita_iva <> ''::bpchar AND length(o.partita_iva::text) = 11 AND o.partita_iva !~ similar_escape('[0]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[1]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[2]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[3]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[4]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[5]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[6]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[7]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[8]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[9]{1,11}'::text, NULL::text) AND length(o.codice_fiscale_rappresentante::text) = 16 AND (cod.description::text <> ALL (ARRAY['00.00.00'::character varying::text, '01.41.00'::character varying::text, '01.42.00'::character varying::text, '01.49.90'::character varying::text, '01.63.00'::character varying::text, '01.64.01'::character varying::text, '01.64.09'::character varying::text, '03.12.00'::character varying::text, '03.21.00'::character varying::text, '03.22.00'::character varying::text, '10.11.00'::character varying::text, '10.41.30'::character varying::text, '10.51.10'::character varying::text, '10.86.00'::character varying::text, '10.89.09'::character varying::text, '10.91.00'::character varying::text, '10.92.00'::character varying::text, '46.11.06'::character varying::text, '46.21.22'::character varying::text, '46.23.00'::character varying::text, '46.39.20'::character varying::text, '47.73.10'::character varying::text, '47.73.20'::character varying::text, '47.91.20'::character varying::text, '56.10.50'::character varying::text, '82.92.10'::character varying::text])) AND i.id_rel_ateco_attivita = rel.id AND rel.id_linee_attivita = la.id AND rel.id_lookup_codistat = cod.code AND o.org_id = i.org_id;

ALTER TABLE public.imprese_852_con_ateco_mappati
  OWNER TO postgres;
  
-- Materialized View: public.import_852_mv

-- DROP MATERIALIZED VIEW public.import_852_mv;
CREATE MATERIALIZED VIEW  import_852_mv AS 
(select ii.*, ic.ateco, ic.id_map, ic.codice_fiscale_impresa, ic.nome_rappresentante, ic.cognome_rappresentante, ic.codice_fiscale_rappresentante
   FROM import_imprese_852 ii
     JOIN imprese_852_con_ateco_mappati ic ON ii.org_id = ic.id_operatore
  WHERE _cf_check_char("substring"(ic.codice_fiscale_rappresentante, 1, 15)) = "substring"(ic.codice_fiscale_rappresentante, 16, 16)
  )

---------------------------------------------------- REVISIONE 17/02/2016
CREATE OR REPLACE VIEW public.import_imprese_852 AS 
 SELECT a.org_id,
    btrim(a.partita_iva::text) AS partita_iva,
    a.site_id AS id_asl,
    btrim(a.numero_registrazione::text) AS numero_registrazione,
    a.tipo_impresa,
    a.denominazione_852,
    a.stato_impresa,
    a.data_inizio_attivita,
    a.data_fine_attivita,
    a.tipo_carattere,
    a.note,
    a.descrizione_breve_attivita,
    a.email_rappresentante,
    a.telefono_rappresentante,
    a.data_nascita_rappresentante,
    a.luogo_nascita_rappresentante,
    a.comune_residenza_rapp,
    a.indirizzo_residenza_rapp,
    a.prov_residenza_rapp,
    a.categoria_rischio,
    a.indirizzo_org,
    a.indirizzo_stabilimento,
    a.comune_stabilimento,
    a.provincia_stabilimento,
    a.cap_stabilimento,
    a.latitudine,
    a.longitudine,
    a.indirizzo_legale,
    a.comune_legale,
    a.prov_legale,
    a.cap_legale,
    a.address_id_operativo,
    a.address_id_legale
   FROM ( SELECT o.org_id,
            o.partita_iva,
            o.site_id,
            o.account_number AS numero_registrazione,
            o.tipo_dest AS tipo_impresa,
            o.name AS denominazione_852,
            oa1.addrline1 AS indirizzo_legale,
            oa1.city AS comune_legale,
            oa1.state AS prov_legale,
            oa1.postalcode AS cap_legale,
                CASE
                    WHEN o.source = 1 AND o.data_fine_carattere IS NOT NULL AND o.data_fine_carattere < 'now'::text::date OR o.source <> 1 AND o.cessato = 1 THEN 'Cessato'::text
                    WHEN o.source = 1 AND (o.data_fine_carattere IS NULL OR o.data_fine_carattere > 'now'::text::date) OR o.source <> 1 AND o.cessato = 0 THEN 'in Attivita'::text
                    WHEN o.source <> 1 AND o.cessato <> 1 AND o.cessato <> 0 THEN 'Sospeso'::text
                    ELSE 'In attivita'::text
                END AS stato_impresa,
                CASE
                    WHEN o.data_in_carattere IS NOT NULL AND o.data_fine_carattere IS NOT NULL THEN to_date(o.data_in_carattere::text, 'yyyy/mm/dd'::text)
                    ELSE to_date(o.datapresentazione::text, 'yyyy/mm/dd'::text)
                END AS data_inizio_attivita,
                CASE
                    WHEN o.source = 1 AND o.data_fine_carattere IS NOT NULL AND o.data_fine_carattere < 'now'::text::date THEN o.data_fine_carattere
                    WHEN o.source <> 1 AND (o.cessato = 1 OR o.cessato = 2) THEN o.contract_end
                    ELSE NULL::timestamp without time zone
                END AS data_fine_attivita,
                CASE
                    WHEN o.source = 1 AND o.data_fine_carattere IS NOT NULL AND o.data_fine_carattere < 'now'::text::date THEN 2
                    ELSE 1
                END AS tipo_carattere,
            o.notes AS note,
            o.alert AS descrizione_breve_attivita,
            o.email_rappresentante,
            o.telefono_rappresentante,
            o.data_nascita_rappresentante,
            o.luogo_nascita_rappresentante,
            o.city_legale_rapp AS comune_residenza_rapp,
            o.address_legale_rapp AS indirizzo_residenza_rapp,
            o.prov_legale_rapp AS prov_residenza_rapp,
            o.categoria_rischio,
            (
                CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa5.addrline1, 'N.D.'::character varying)
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa7.addrline1, 'N.D.'::character varying)
                    ELSE 'N.D.'::character varying
                END::text || ','::text) ||
                CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN upper(regexp_replace(regexp_replace(COALESCE(oa5.city, 'N.D.'::character varying)::text, '''''|,|\r|\n|"|-| {2,}'::text, ' '::text, 'g'::text), '°'::text, '.'::text, 'g'::text))::character varying
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN upper(regexp_replace(regexp_replace(COALESCE(oa7.city, 'N.D.'::character varying)::text, '''''|,|\r|\n|"|-| {2,}'::text, ' '::text, 'g'::text), '°'::text, '.'::text, 'g'::text))::character varying
                    ELSE 'N.D.'::character varying
                END::text AS indirizzo_org,
                CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa5.addrline1, 'N.D.'::character varying)
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa7.addrline1, 'N.D.'::character varying)
                    ELSE 'N.D.'::character varying
                END AS indirizzo_stabilimento,
                CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN upper(regexp_replace(regexp_replace(COALESCE(oa5.city, 'N.D.'::character varying)::text, '''''|,|\r|\n|"|-| {2,}'::text, ' '::text, 'g'::text), '°'::text, '.'::text, 'g'::text))::character varying
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN upper(regexp_replace(regexp_replace(COALESCE(oa7.city, 'N.D.'::character varying)::text, '''''|,|\r|\n|"|-| {2,}'::text, ' '::text, 'g'::text), '°'::text, '.'::text, 'g'::text))::character varying
                    ELSE 'N.D.'::character varying
                END::text AS comune_stabilimento,
                CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN upper(regexp_replace(regexp_replace(COALESCE(oa5.state, 'N.D.'::character varying)::text, '''''|,|\r|\n|"|-| {2,}'::text, ' '::text, 'g'::text), '°'::text, '.'::text, 'g'::text))::character varying
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN upper(regexp_replace(regexp_replace(COALESCE(oa7.state, 'N.D.'::character varying)::text, '''''|,|\r|\n|"|-| {2,}'::text, ' '::text, 'g'::text), '°'::text, '.'::text, 'g'::text))::character varying
                    ELSE 'N.D.'::character varying
                END::text AS provincia_stabilimento,
                CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN upper(regexp_replace(regexp_replace(COALESCE(oa5.postalcode, 'N.D.'::character varying)::text, '''''|,|\r|\n|"|-| {2,}'::text, ' '::text, 'g'::text), '°'::text, '.'::text, 'g'::text))::character varying
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN upper(regexp_replace(regexp_replace(COALESCE(oa7.postalcode, 'N.D.'::character varying)::text, '''''|,|\r|\n|"|-| {2,}'::text, ' '::text, 'g'::text), '°'::text, '.'::text, 'g'::text))::character varying
                    ELSE 'N.D.'::character varying
                END::text AS cap_stabilimento,
                CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN oa5.latitude
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN oa7.latitude
                    ELSE NULL::double precision
                END AS latitudine,
                CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN oa5.longitude
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN oa7.longitude
                    ELSE NULL::double precision
                END AS longitudine,
                CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN oa5.address_id::double precision
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN oa7.address_id::double precision
                    ELSE NULL::double precision
                END AS address_id_operativo,
            oa1.address_id AS address_id_legale
           FROM organization o
             LEFT JOIN organization_address oa5 ON oa5.org_id = o.org_id AND oa5.address_type = 5
             LEFT JOIN organization_address oa7 ON oa7.org_id = o.org_id AND oa7.address_type = 7
             LEFT JOIN organization_address oa1 ON oa1.org_id = o.org_id AND oa1.address_type = 1
             JOIN ticket t ON t.org_id = o.org_id
          WHERE o.trashed_date IS NULL AND o.tipologia = 1 AND o.import_opu = false AND t.trashed_date IS NULL AND (t.tipologia <> ALL (ARRAY[700, 4, 16])) AND o.name IS NOT NULL AND o.name::text <> ''::text AND o.partita_iva IS NOT NULL AND o.partita_iva <> ''::bpchar AND length(o.partita_iva::text) = 11 AND o.partita_iva !~ similar_escape('[0]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[1]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[2]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[3]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[4]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[5]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[6]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[7]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[8]{1,11}'::text, NULL::text) AND o.partita_iva !~ similar_escape('[9]{1,11}'::text, NULL::text)
          GROUP BY o.org_id, o.partita_iva, o.name, oa1.addrline1, oa1.city, oa1.state, oa1.postalcode, ((
                CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa5.addrline1, 'N.D.'::character varying)
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa7.addrline1, 'N.D.'::character varying)
                    ELSE 'N.D.'::character varying
                END::text || ','::text) ||
                CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN upper(regexp_replace(regexp_replace(COALESCE(oa5.city, 'N.D.'::character varying)::text, '''''|,|\r|\n|"|-| {2,}'::text, ' '::text, 'g'::text), '°'::text, '.'::text, 'g'::text))::character varying
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN upper(regexp_replace(regexp_replace(COALESCE(oa7.city, 'N.D.'::character varying)::text, '''''|,|\r|\n|"|-| {2,}'::text, ' '::text, 'g'::text), '°'::text, '.'::text, 'g'::text))::character varying
                    ELSE 'N.D.'::character varying
                END::text), (
                CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa5.addrline1, 'N.D.'::character varying)
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa7.addrline1, 'N.D.'::character varying)
                    ELSE 'N.D.'::character varying
                END), (
                CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN upper(regexp_replace(regexp_replace(COALESCE(oa5.city, 'N.D.'::character varying)::text, '''''|,|\r|\n|"|-| {2,}'::text, ' '::text, 'g'::text), '°'::text, '.'::text, 'g'::text))::character varying
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN upper(regexp_replace(regexp_replace(COALESCE(oa7.city, 'N.D.'::character varying)::text, '''''|,|\r|\n|"|-| {2,}'::text, ' '::text, 'g'::text), '°'::text, '.'::text, 'g'::text))::character varying
                    ELSE 'N.D.'::character varying
                END), (
                CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN upper(regexp_replace(regexp_replace(COALESCE(oa5.state, 'N.D.'::character varying)::text, '''''|,|\r|\n|"|-| {2,}'::text, ' '::text, 'g'::text), '°'::text, '.'::text, 'g'::text))::character varying
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN upper(regexp_replace(regexp_replace(COALESCE(oa7.state, 'N.D.'::character varying)::text, '''''|,|\r|\n|"|-| {2,}'::text, ' '::text, 'g'::text), '°'::text, '.'::text, 'g'::text))::character varying
                    ELSE 'N.D.'::character varying
                END), (
                CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN upper(regexp_replace(regexp_replace(COALESCE(oa5.postalcode, 'N.D.'::character varying)::text, '''''|,|\r|\n|"|-| {2,}'::text, ' '::text, 'g'::text), '°'::text, '.'::text, 'g'::text))::character varying
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN upper(regexp_replace(regexp_replace(COALESCE(oa7.postalcode, 'N.D.'::character varying)::text, '''''|,|\r|\n|"|-| {2,}'::text, ' '::text, 'g'::text), '°'::text, '.'::text, 'g'::text))::character varying
                    ELSE 'N.D.'::character varying
                END), (
                CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN oa5.latitude
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN oa7.latitude
                    ELSE NULL::double precision
                END), (
                CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN oa5.longitude
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN oa7.longitude
                    ELSE NULL::double precision
                END), (
                CASE
                    WHEN o.tipo_dest::text = 'Es. Commerciale'::text THEN oa5.address_id::double precision
                    WHEN o.tipo_dest::text = 'Autoveicolo'::text THEN oa7.address_id::double precision
                    ELSE NULL::double precision
                END), oa1.address_id
         HAVING count(t.ticketid) > 0) a
  WHERE a.stato_impresa <> 'Cessato'::text AND a.stato_impresa <> 'Sospeso'::text;

ALTER TABLE public.import_imprese_852
  OWNER TO postgres;

 -- View: public.imprese_852_con_ateco_mappati

-- DROP VIEW public.imprese_852_con_ateco_mappati;

CREATE OR REPLACE VIEW public.imprese_852_con_ateco_mappati AS 
 SELECT cod.description AS ateco,
    i.org_id AS id_operatore,
    i.id_attivita_masterlist AS id_map,
    o.name AS denominazione,
    btrim(o.partita_iva::text) AS partita_iva,
    o.codice_fiscale AS codice_fiscale_impresa,
    o.nome_rappresentante,
    o.cognome_rappresentante,
    btrim(o.codice_fiscale_rappresentante::text) AS codice_fiscale_rappresentante,
        CASE
            WHEN map.livello > 2 THEN map.id_attivita
            ELSE map.id_aggregazione
        END AS id_linea_produttiva,
    map.livello AS livello_linea
   FROM la_imprese_linee_attivita i,
    la_rel_ateco_attivita rel,
    la_linee_attivita la,
    organization o,
    lookup_codistat cod
     LEFT JOIN mapping_codice_ateco_master_list_2015_2016 map ON map.codice_ateco::text = cod.description::text
  WHERE i.trashed_date IS NULL AND o.trashed_date IS NULL AND o.import_opu = false AND o.tipologia = 1 
  AND o.partita_iva IS NOT NULL AND o.partita_iva <> ''::bpchar  AND length(o.partita_iva::text) = 11 
  AND o.partita_iva::varchar similar to '[0-9]{11}'
  and o.partita_iva::varchar not similar to '%[0]{5,11}%'
  and o.partita_iva::varchar not similar to '%[1]{5,11}%'
  and o.partita_iva::varchar not similar to '%[2]{5,11}%'
  and o.partita_iva::varchar not similar to '%[3]{5,11}%'
  and o.partita_iva::varchar not similar to '%[4]{5,11}%'
  and o.partita_iva::varchar not similar to '%[5]{5,11}%'
  and o.partita_iva::varchar not similar to '%[6]{5,11}%'
  and o.partita_iva::varchar not similar to '%[7]{5,11}%'
  and o.partita_iva::varchar not similar to '%[8]{5,11}%'
  and o.partita_iva::varchar not similar to '%[9]{5,11}%'
  AND length(o.codice_fiscale_rappresentante::text) = 16 
  AND (cod.description::text <> ALL (ARRAY['00.00.00'::character varying::text, '01.41.00'::character varying::text, '01.42.00'::character varying::text, '01.49.90'::character varying::text, '01.63.00'::character varying::text, '01.64.01'::character varying::text, '01.64.09'::character varying::text, '03.12.00'::character varying::text, '03.21.00'::character varying::text, '03.22.00'::character varying::text, '10.11.00'::character varying::text, '10.41.30'::character varying::text, '10.51.10'::character varying::text, '10.86.00'::character varying::text, '10.89.09'::character varying::text, '10.91.00'::character varying::text, '10.92.00'::character varying::text, '46.11.06'::character varying::text, '46.21.22'::character varying::text, '46.23.00'::character varying::text, '46.39.20'::character varying::text, '47.73.10'::character varying::text, '47.73.20'::character varying::text, '47.91.20'::character varying::text, '56.10.50'::character varying::text, '82.92.10'::character varying::text])) AND i.id_rel_ateco_attivita = rel.id AND rel.id_linee_attivita = la.id AND rel.id_lookup_codistat = cod.code AND o.org_id = i.org_id;

ALTER TABLE public.imprese_852_con_ateco_mappati
  OWNER TO postgres;


  

create table table_app as (
select *  
from import_852_mv where partita_iva in (
select distinct(partita_iva) from import_852_mv
except
select distinct(partita_iva) from opu_operatore where trashed_date is null 
except 
select distinct(codice_fiscale_impresa) from opu_operatore where trashed_Date is null 
) and codice_fiscale_rappresentante in (
select distinct(codice_fiscale_rappresentante)
from import_852_mv
except
select distinct(codice_fiscale)
from opu_soggetto_fisico where trashed_date is null 
) )




create table table_app as (
select *  
from import_852_mv where partita_iva in (
select distinct(partita_iva) from import_852_mv
except
select distinct(partita_iva) from opu_operatore where trashed_date is null 
except 
select distinct(codice_fiscale_impresa) from opu_operatore where trashed_Date is null 
) and codice_fiscale_rappresentante in (
select distinct(codice_fiscale_rappresentante)
from import_852_mv
except
select distinct(codice_fiscale)
from opu_soggetto_fisico where trashed_date is null 
) )

create table table_app_no_duplicati_fisse as (
select * from table_app where partita_iva in (
select partita_iva from table_app
group by partita_iva
having count(partita_iva) = 1) and codice_fiscale_rappresentante in(
select codice_fiscale_rappresentante from table_app
group by codice_fiscale_rappresentante
having count(codice_fiscale_rappresentante) = 1)
and tipo_impresa ilike '%commerciale%')


create table table_app_no_duplicati_mobili as (
select * from table_app where partita_iva in (
select partita_iva from table_app
group by partita_iva
having count(partita_iva) = 1) and codice_fiscale_rappresentante in(
select codice_fiscale_rappresentante from table_app
group by codice_fiscale_rappresentante
having count(codice_fiscale_rappresentante) = 1)
and tipo_impresa ilike '%autoveicol%')

  
 
--pulizia su opu
select 'update opu_operatore set trashed_date = now() where id='||o.id||';',*
 from opu_operatore o
join opu_stabilimento s on o.id=s.id_operatore
where o.trashed_date is not null and s.trashed_date is null

select s.trashed_date, 'update opu_operatore set trashed_date = now() where id='||o.id||';',*
 from opu_operatore o
join opu_stabilimento s on o.id=s.id_operatore
where o.trashed_date is null and s.trashed_date is not null

select count(*), codice_fiscale
from opu_soggetto_fisico
where trashed_Date is null
group by codice_fiscale
having count(codice_fiscale)>1

update opu_soggetto_fisico set note_hd='CANCELLATO PERCHE'' NESSUN RIFERIMENTO ESISTE NELLA TABELLA DI RELAZIONE TRA OPERATORE E SOGGETTO', TRASHED_DATE = now() where id in (
9049,21049,21581,20489,8891,21747,17004,19460,8995,20385,8977,9036,21038,21659,9022,19318,8775,22036,8939,20338)

update opu_rel_operatore_soggetto_fisico set id_soggetto_fisico  = 19524 where id_soggetto_fisico  in (19521, 19522, 19523)
update opu_soggetto_fisico  set trashed_date = now() where id in (19521, 19522, 19523)
update opu_rel_operatore_soggetto_fisico set id_soggetto_fisico  = 18752 where id_soggetto_fisico  =18747
update opu_soggetto_fisico  set trashed_date = now() where id = 18747

CREATE UNIQUE INDEX stop_cf_dup
  ON opu_soggetto_fisico
  USING btree
  (codice_fiscale COLLATE pg_catalog."default")
  WHERE trashed_date IS NULL AND codice_fiscale IS NOT NULL AND codice_fiscale::text <> ''::text;
alter table opu_operatore add column riferimento_ag int;
alter table opu_indirizzo add column note_hd text;
 
QUANDO:10/02/2016

select 'update opu_operatore set azienda_agricola = true, riferimento_org_id = null, riferimento_ag ='||o.riferimento_org_id||' where id='||o.id||';' ,* from log_import_852 l 
join opu_operatore o on l.orgid = o.riferimento_org_id
join opu_operatore_ oo on oo.id = o.riferimento_org_id
where l.importato = false and msg3 is null


CREATE TABLE public.log_import_852
(
  id serial,
  orgid integer,
  msg text,
  importato boolean,
  entered timestamp without time zone DEFAULT now(),
  msg1 text,
  msg2 text,
  msg3 text,
  msg4 text,
  msg5 text,
  msg6 text,
  nometabella text
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.log_import_852
  OWNER TO postgres;

update table_app_no_duplicati_mobili   set comune_stabilimento = 'TRENTOLA-DUCENTA' where comune_stabilimento = 'TRENTOLA DUCENTA'
update table_app_no_duplicati_fisse set categoria_rischio = 2 where categoria_rischio is null


  
  