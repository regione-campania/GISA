-- View: public.view_operatori_pnaa_modificata_new

-- DROP VIEW public.view_operatori_pnaa_modificata_new;


CREATE OR REPLACE VIEW public.view_operatori_pnaa_modificata AS 
 SELECT DISTINCT t.org_id,
    t.location AS num_verbale,
        CASE
            WHEN t.motivazione_piano_campione IS NOT NULL THEN lpm.description
            ELSE lti.description
        END AS motivazione,
    o.account_number AS numero_registrazione_osa,
        CASE
            WHEN t.motivazione_piano_campione IS NOT NULL THEN lpm.codice_esame
            ELSE lti.codice_esame
        END AS barcode_motivazione,
    t.ticketid AS id_campione,
    t2.ticketid AS id_controllo,
    l.description AS ente_appartenenza,
    ut.descrizione_lunga AS unita_territoriale,
    date_part('years'::text, t2.assigned_date) AS anno,
    date_part('day'::text, t2.assigned_date) AS giorno,
    date_part('month'::text, t2.assigned_date) AS mese,
    ((((((((
        CASE
            WHEN t2.componente_nucleo IS NOT NULL THEN t2.componente_nucleo || ' '::text
            ELSE ''::text
        END ||
        CASE
            WHEN t2.componente_nucleo_due IS NOT NULL THEN t2.componente_nucleo_due || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_tre IS NOT NULL THEN t2.componente_nucleo_tre || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_quattro IS NOT NULL THEN t2.componente_nucleo_quattro || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_cinque IS NOT NULL THEN t2.componente_nucleo_cinque || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_sei IS NOT NULL THEN t2.componente_nucleo_sei || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_sette IS NOT NULL THEN t2.componente_nucleo_sette || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_otto IS NOT NULL THEN t2.componente_nucleo_otto || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_nove IS NOT NULL THEN t2.componente_nucleo_nove || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_dieci IS NOT NULL THEN t2.componente_nucleo_dieci || ' '::text
            ELSE ''::text
        END AS componenti_nucleo_ispettivo,
        CASE
            WHEN lpm.description::text ~~* '%SORV%'::text THEN '007'::text
            WHEN lpm.description::text ~~* '%MON%'::text THEN '003'::text
            WHEN lpm.description::text ~~* '%EXTRAPIANO%'::text THEN '005'::text
            WHEN lti.description::text ~~* '%SOSPETT%'::text THEN '001'::text
            ELSE 'N.D.'::text
        END AS a1,
              CASE
            WHEN lpm.description::text ~~* '%BSE%'::text or lpm.description::text ~~* '%acquacoltura%'::text THEN '1'::text -- a,b,c,d,e
            WHEN lpm.description::text ~~* '%SOSTANZE FARMACOLOGICHE%CARRY OVER)'::text THEN '8'::text --h 
            --WHEN upper(lpm.description::text) ~~* '%SOSTANZE FARMACOLOGIC%'::text THEN '10'::text --NEW da gestire quando TITOLO uso illecito e improprio sn selezionati
            WHEN upper(lpm.description::text) ~~* '%tab. 3.2 finalità 1%'::text THEN '12'::text --NEW da gestire TITOLO
            WHEN upper(lpm.description::text) ~~* '%tab. 3.2 finalità 2%'::text THEN '13'::text --NEW da gestire uso illecito
            WHEN upper(lpm.description::text) ~~* '%tab. 3.2 finalità 3%'::text THEN '14'::text --NEW da gestire uso improprio
            --WHEN lpm.description::text ~* '%SOSTANZE FARMACOLOGICHE)'::text OR lpm.description::text ~* '%MONIT. OLIGOELEMENTI%'::text THEN '2'::text --f????????? a cosa corrisponde?
            WHEN upper(lpm.description::text) ~~* '%CONTAMINANTI%'::text or lpm.description::text ~~* '%radionuclidi%'::text THEN '3'::text -- u,m
            WHEN lpm.description::text ~~* '%DIOSSINE%'::text THEN '4'::text -- i,j 
            WHEN lpm.description::text ~~* '%MICOTOSSINE%'::text THEN '5'::text --k,l
            WHEN lpm.description::text ~~* '%SALMONELLA%'::text THEN '6'::text -- o, p
            WHEN upper(lpm.description::text) ~~* '%OGM AUTORIZZAT%'::text THEN '7'::text -- ogm autorizzato q ed s
            WHEN upper(lpm.description::text) ~~* '%OGM NON AUTORIZZAT%'::text THEN '11'::text -- NEW DA GESTIRE ogm non autorizzato r
            ELSE '9'::text --altro
        END AS a3,
    ((((((((
        CASE
            WHEN t2.componente_nucleo IS NOT NULL THEN t2.componente_nucleo || ' '::text
            ELSE ''::text
        END ||
        CASE
            WHEN t2.componente_nucleo_due IS NOT NULL THEN t2.componente_nucleo_due || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_tre IS NOT NULL THEN t2.componente_nucleo_tre || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_quattro IS NOT NULL THEN t2.componente_nucleo_quattro || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_cinque IS NOT NULL THEN t2.componente_nucleo_cinque || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_sei IS NOT NULL THEN t2.componente_nucleo_sei || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_sette IS NOT NULL THEN t2.componente_nucleo_sette || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_otto IS NOT NULL THEN t2.componente_nucleo_otto || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_nove IS NOT NULL THEN t2.componente_nucleo_nove || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_dieci IS NOT NULL THEN t2.componente_nucleo_dieci || ' '::text
            ELSE ''::text
        END AS a4,
    COALESCE(az.indrizzo_azienda, 'N.D.'::character varying::text)::character varying AS a8,
    COALESCE(c.comune, 'N.D.'::character varying) AS a9,
    COALESCE(az.prov_sede_azienda, 'N.D.'::character varying::text)::character varying AS a10,
    COALESCE(az.latitudine_geo, 0::double precision) AS a11_1,
    COALESCE(az.longitudine_geo, 0::double precision) AS a11_2,
    op_prop.nominativo::character varying AS a12,
    o.name AS a12b,
    op_prop.nominativo AS a13,
    COALESCE(op_prop.cf, o.codice_fiscale_rappresentante::text) AS a14,
    op_detentore.nominativo AS a15,
    COALESCE(op_detentore.cf, o.codice_fiscale_rappresentante::text) AS a15b,
    t.check_circuito_ogm AS b7
   FROM organization o
     LEFT JOIN ticket t ON t.org_id = o.org_id AND t.tipologia = 2 AND t.trashed_date IS NULL
     LEFT JOIN ticket t2 ON t2.ticketid = t.id_controllo_ufficiale::integer AND t2.tipologia = 3
     LEFT JOIN tipocontrolloufficialeimprese tcu ON tcu.idcontrollo = t2.ticketid AND tcu.enabled AND tcu.pianomonitoraggio = t.motivazione_piano_campione
     LEFT JOIN strutture_asl ut ON ut.id = tcu.id_unita_operativa
     LEFT JOIN matrici_campioni mc ON mc.id_campione = t.ticketid
     LEFT JOIN matrici m ON m.matrice_id = mc.id_matrice
     LEFT JOIN lookup_tipo_ispezione lti ON lti.code = t.motivazione_campione
     LEFT JOIN lookup_piano_monitoraggio lpm ON lpm.code = t.motivazione_piano_campione
     LEFT JOIN lookup_tipologia_operatore lto ON lto.code = o.tipologia
     LEFT JOIN aziende az ON az.cod_azienda = o.account_number::text AND o.tipologia = 2
     LEFT JOIN comuni_old c ON c.codiceistatcomune::text = az.cod_comune_azienda
     LEFT JOIN operatori_allevamenti op_detentore ON o.cf_correntista::text = op_detentore.cf
     LEFT JOIN operatori_allevamenti op_prop ON o.codice_fiscale_rappresentante::text = op_prop.cf
     LEFT JOIN lookup_site_id l ON l.code = t.site_id
  WHERE o.trashed_date IS NULL AND o.org_id <> 0 AND o.tipologia = 2 AND t.trashed_date IS NULL AND t2.trashed_date IS NULL AND (lpm.codice_interno = 370 OR lti.description::text ~~* '%SOSPETT%'::text)
UNION
 SELECT DISTINCT r.riferimento_id AS org_id,
    t.location AS num_verbale,
        CASE
            WHEN t.motivazione_piano_campione IS NOT NULL THEN lpm.description
            ELSE lti.description
        END AS motivazione,
    COALESCE(r.n_reg, r.n_linea) AS numero_registrazione_osa,
        CASE
            WHEN t.motivazione_piano_campione IS NOT NULL THEN lpm.codice_esame
            ELSE lti.codice_esame
        END AS barcode_motivazione,
    t.ticketid AS id_campione,
    t2.ticketid AS id_controllo,
    l.description AS ente_appartenenza,
    ut.descrizione_lunga AS unita_territoriale,
    date_part('years'::text, t2.assigned_date) AS anno,
    date_part('day'::text, t2.assigned_date) AS giorno,
    date_part('month'::text, t2.assigned_date) AS mese,
    ((((((((
        CASE
            WHEN t2.componente_nucleo IS NOT NULL THEN t2.componente_nucleo || ' '::text
            ELSE ''::text
        END ||
        CASE
            WHEN t2.componente_nucleo_due IS NOT NULL THEN t2.componente_nucleo_due || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_tre IS NOT NULL THEN t2.componente_nucleo_tre || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_quattro IS NOT NULL THEN t2.componente_nucleo_quattro || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_cinque IS NOT NULL THEN t2.componente_nucleo_cinque || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_sei IS NOT NULL THEN t2.componente_nucleo_sei || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_sette IS NOT NULL THEN t2.componente_nucleo_sette || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_otto IS NOT NULL THEN t2.componente_nucleo_otto || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_nove IS NOT NULL THEN t2.componente_nucleo_nove || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_dieci IS NOT NULL THEN t2.componente_nucleo_dieci || ' '::text
            ELSE ''::text
        END AS componenti_nucleo_ispettivo,
        CASE
            WHEN lpm.description::text ~~* '%SORV%'::text THEN '007'::text
            WHEN lpm.description::text ~~* '%MON%'::text THEN '003'::text
            WHEN lpm.description::text ~~* '%EXTRAPIANO%'::text THEN '005'::text
            WHEN lti.description::text ~~* '%SOSPETT%'::text THEN '001'::text
            ELSE 'N.D.'::text
        END AS a1,
           CASE
            WHEN lpm.description::text ~~* '%BSE%'::text or lpm.description::text ~~* '%acquacoltura%'::text THEN '1'::text -- a,b,c,d,e
            WHEN lpm.description::text ~~* '%SOSTANZE FARMACOLOGICHE%CARRY OVER)'::text THEN '8'::text --h 
            --WHEN upper(lpm.description::text) ~~* '%SOSTANZE FARMACOLOGIC%'::text THEN '10'::text --NEW da gestire quando TITOLO uso illecito e improprio sn selezionati
            WHEN upper(lpm.description::text) ~~* '%tab. 3.2 finalità 1%'::text THEN '12'::text --NEW da gestire TITOLO
            WHEN upper(lpm.description::text) ~~* '%tab. 3.2 finalità 2%'::text THEN '13'::text --NEW da gestire uso illecito
            WHEN upper(lpm.description::text) ~~* '%tab. 3.2 finalità 3%'::text THEN '14'::text --NEW da gestire uso improprio
            --WHEN lpm.description::text ~* '%SOSTANZE FARMACOLOGICHE)'::text OR lpm.description::text ~* '%MONIT. OLIGOELEMENTI%'::text THEN '2'::text --f????????? a cosa corrisponde?
            WHEN upper(lpm.description::text) ~~* '%CONTAMINANTI%'::text or lpm.description::text ~~* '%radionuclidi%'::text THEN '3'::text -- u,m
            WHEN lpm.description::text ~~* '%DIOSSINE%'::text THEN '4'::text -- i,j 
            WHEN lpm.description::text ~~* '%MICOTOSSINE%'::text THEN '5'::text --k,l
            WHEN lpm.description::text ~~* '%SALMONELLA%'::text THEN '6'::text -- o, p
            WHEN upper(lpm.description::text) ~~* '%OGM AUTORIZZAT%'::text THEN '7'::text -- ogm autorizzato q ed s
            WHEN upper(lpm.description::text) ~~* '%OGM NON AUTORIZZAT%'::text THEN '11'::text -- NEW DA GESTIRE ogm non autorizzato r
            ELSE '9'::text --altro
        END AS a3,
    ((((((((
        CASE
            WHEN t2.componente_nucleo IS NOT NULL THEN t2.componente_nucleo || ' '::text
            ELSE ''::text
        END ||
        CASE
            WHEN t2.componente_nucleo_due IS NOT NULL THEN t2.componente_nucleo_due || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_tre IS NOT NULL THEN t2.componente_nucleo_tre || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_quattro IS NOT NULL THEN t2.componente_nucleo_quattro || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_cinque IS NOT NULL THEN t2.componente_nucleo_cinque || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_sei IS NOT NULL THEN t2.componente_nucleo_sei || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_sette IS NOT NULL THEN t2.componente_nucleo_sette || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_otto IS NOT NULL THEN t2.componente_nucleo_otto || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_nove IS NOT NULL THEN t2.componente_nucleo_nove || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_dieci IS NOT NULL THEN t2.componente_nucleo_dieci || ' '::text
            ELSE ''::text
        END AS a4,
    r.indirizzo AS a8,
    r.comune AS a9,
    r.provincia_stab AS a10,
    r.latitudine_stab AS a11_1,
    r.longitudine_stab AS a11_2,
    r.ragione_sociale AS a12,
    r.ragione_sociale AS a12b,
    r.nominativo_rappresentante AS a13,
    r.codice_fiscale_rappresentante AS a14,
    r.nominativo_rappresentante AS a15,
    r.codice_fiscale_rappresentante AS a15b,
    t.check_circuito_ogm AS b7
   FROM ricerche_anagrafiche_old_materializzata r
     LEFT JOIN ticket t ON (t.alt_id = r.riferimento_id AND r.riferimento_id_nome_tab = 'sintesis_stabilimento'::text OR t.id_stabilimento = r.riferimento_id AND r.riferimento_id_nome_tab = 'opu_stabilimento'::text OR t.org_id = r.riferimento_id AND r.riferimento_id_nome_tab = 'organization'::text OR t.id_apiario = r.riferimento_id AND r.riferimento_id_nome_tab = 'apicoltura_imprese'::text) AND t.tipologia = 2 AND t.trashed_date IS NULL
     LEFT JOIN ticket t2 ON t2.ticketid = t.id_controllo_ufficiale::integer AND t2.tipologia = 3
     LEFT JOIN tipocontrolloufficialeimprese tcu ON tcu.idcontrollo = t2.ticketid AND tcu.enabled AND tcu.pianomonitoraggio = t.motivazione_piano_campione
     LEFT JOIN strutture_asl ut ON ut.id = tcu.id_unita_operativa
     LEFT JOIN matrici_campioni mc ON mc.id_campione = t.ticketid
     LEFT JOIN matrici m ON m.matrice_id = mc.id_matrice
     LEFT JOIN lookup_tipo_ispezione lti ON lti.code = t.motivazione_campione
     LEFT JOIN lookup_piano_monitoraggio lpm ON lpm.code = t.motivazione_piano_campione
     LEFT JOIN lookup_site_id l ON l.code = t.site_id
  WHERE r.tipologia_operatore <> 2 AND t2.trashed_date IS NULL AND (lpm.codice_interno = 370 OR lti.description::text ~~* '%SOSPETT%'::text);

ALTER TABLE public.view_operatori_pnaa_modificata
  OWNER TO postgres;

select * from campioni_html_fields
  -- insert
insert into campioni_html_fields(id, id_piano_monitoraggio, nome_campo, tipo_campo, label_campo, tabella_lookup, ordine_campo, only_hd, obbligatorio, gestione_pnaa, codice_interno_piano_monitoraggio, pregresso)
values                          ((select max(id)+1 from campioni_html_fields),370,'a3_attr_1','select','A3. Principi farmacologicamente attivi e additivi','lookup_principi_farm_attivi_additivi', 3,0, false, true, '370', false);
insert into campioni_html_fields(id, id_piano_monitoraggio, nome_campo, tipo_campo, label_campo, tabella_lookup, ordine_campo, only_hd, obbligatorio, gestione_pnaa, codice_interno_piano_monitoraggio, pregresso)
values                          ((select max(id)+1 from campioni_html_fields),370,'a3_attr_2','select','A3. Principi farmacologicamente attivi e additivi CARRY OVER','lookup_principi_farm_attivi_additivi_carryover', 3,0, false, true, '370', false);
insert into campioni_html_fields(id, id_piano_monitoraggio, nome_campo, tipo_campo, label_campo, ordine_campo, only_hd, obbligatorio, gestione_pnaa, codice_interno_piano_monitoraggio, pregresso)
values                          ((select max(id)+1 from campioni_html_fields), 370,'a3_attr_2_quantita','text','A3. Quantità di P.A./Coccidiostatico aggiunta in produzione nel lotto precedente', 3,0, false, true, '370', false);
insert into campioni_html_fields(id, id_piano_monitoraggio, nome_campo, tipo_campo, label_campo, tabella_lookup, ordine_campo, only_hd, obbligatorio, gestione_pnaa, codice_interno_piano_monitoraggio, pregresso)
values                          ((select max(id)+1 from campioni_html_fields), 370,'a3_attr_3','select','A3. Contaminanti inorganici e composti azotati pesticidi, radionuclidi','lookup_contaminanti',3,0, false, true, '370', false);

CREATE TABLE public.lookup_principi_farm_attivi_additivi
(
  code serial,
  description character varying(50) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.lookup_principi_farm_attivi_additivi
  OWNER TO postgres;

  
CREATE TABLE public.lookup_principi_farm_attivi_additivi_carryover
(
  code serial,
  description character varying(50) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.lookup_principi_farm_attivi_additivi_carryover
  OWNER TO postgres;

  
CREATE TABLE public.lookup_contaminanti
(
  code serial,
  description character varying(50) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.lookup_contaminanti
  OWNER TO postgres;


insert into lookup_principi_farm_attivi_additivi (description,level) values('Principi farm. attivi','0');
insert into lookup_principi_farm_attivi_additivi (description,level) values('additivi nutrizionali','1');
insert into lookup_principi_farm_attivi_additivi (description,level) values('cocciodiostatici/istomonostatici','2');
insert into lookup_principi_farm_attivi_additivi (description,level) values('additivi tecnologici','3');
insert into lookup_principi_farm_attivi_additivi (description,level) values('additivi organolettici','4');
insert into lookup_principi_farm_attivi_additivi (description,level) values('additivi zootecnici','5');

insert into lookup_principi_farm_attivi_additivi_carryover (description,level) values('Principi farm. attivi','0');
insert into lookup_principi_farm_attivi_additivi_carryover (description,level) values('Coccidiostatici/istomonostatici','1');

insert into lookup_contaminanti (description,level) values('contaminanti inorganici e composti azotati','0');
insert into lookup_contaminanti (description,level) values('radionuclidi','1');
insert into lookup_contaminanti (description,level) values('pesticidi','2');

--alter table campioni_html_fields add column enabled_campo boolean default true;
update campioni_html_fields set enabled_campo = false where id_piano_monitoraggio  = 370 and nome_campo ilike 'a3_attr';

update campioni_html_fields set ordine_campo = 1 where id_piano_monitoraggio  = 370 and enabled_campo and nome_campo in ('mangimi');
update campioni_html_fields set ordine_campo = 2 where id_piano_monitoraggio  = 370 and enabled_campo and nome_campo in ('a0');
update campioni_html_fields set ordine_campo = 3 where id_piano_monitoraggio  = 370 and enabled_campo and nome_campo in ('dpa');
update campioni_html_fields set ordine_campo = 4 where id_piano_monitoraggio  = 370 and enabled_campo and nome_campo in ('a2');
update campioni_html_fields set ordine_campo = 5 where id_piano_monitoraggio  = 370 and enabled_campo and nome_campo in ('a3_attr_1');
update campioni_html_fields set ordine_campo = 6 where id_piano_monitoraggio  = 370 and enabled_campo and nome_campo in ('a3_attr_2');
update campioni_html_fields set ordine_campo = 7 where id_piano_monitoraggio  = 370 and enabled_campo and nome_campo in ('a3_attr_2_quantita');
update campioni_html_fields set ordine_campo = 8 where id_piano_monitoraggio  = 370 and enabled_campo and nome_campo in ('a3_attr_3');
update campioni_html_fields set ordine_campo = 9 where id_piano_monitoraggio  = 370 and enabled_campo and nome_campo in ('a5');
update campioni_html_fields set ordine_campo = 10 where id_piano_monitoraggio  = 370 and enabled_campo and nome_campo in ('a5');
update campioni_html_fields set ordine_campo = 11 where id_piano_monitoraggio  = 370 and enabled_campo and nome_campo in ('a6');
update campioni_html_fields set ordine_campo = 12 where id_piano_monitoraggio  = 370 and enabled_campo and nome_campo in ('b1');
update campioni_html_fields set ordine_campo = 13 where id_piano_monitoraggio  = 370 and enabled_campo and nome_campo in ('b1_testo');
update campioni_html_fields set ordine_campo = 14 where id_piano_monitoraggio  = 370 and enabled_campo and nome_campo in ('b2');
update campioni_html_fields set ordine_campo = 15 where id_piano_monitoraggio  = 370 and enabled_campo and nome_campo in ('materia_prima');
update campioni_html_fields set ordine_campo = 16 where id_piano_monitoraggio  = 370 and enabled_campo and nome_campo in ('b4');
update campioni_html_fields set ordine_campo = 17 where id_piano_monitoraggio  = 370 and enabled_campo and nome_campo in ('b5');
update campioni_html_fields set ordine_campo = 18 where id_piano_monitoraggio  = 370 and enabled_campo and nome_campo in ('b6');
update campioni_html_fields set ordine_campo = 19 where id_piano_monitoraggio  = 370 and enabled_campo and nome_campo in ('b7');
update campioni_html_fields set ordine_campo = 20 where id_piano_monitoraggio  = 370 and enabled_campo and nome_campo in ('b8');
update campioni_html_fields set ordine_campo = 21 where id_piano_monitoraggio  = 370 and enabled_campo and nome_campo in ('b9');
update campioni_html_fields set ordine_campo = 22 where id_piano_monitoraggio  = 370 and enabled_campo and nome_campo in ('b10');
update campioni_html_fields set ordine_campo = 23 where id_piano_monitoraggio  = 370 and enabled_campo and nome_campo in ('b11');
update campioni_html_fields set ordine_campo = 24 where id_piano_monitoraggio  = 370 and enabled_campo and nome_campo in ('b12');
update campioni_html_fields set ordine_campo = 25 where id_piano_monitoraggio  = 370 and enabled_campo and nome_campo in ('b14');
update campioni_html_fields set ordine_campo = 26 where id_piano_monitoraggio  = 370 and enabled_campo and nome_campo in ('b15');
update campioni_html_fields set ordine_campo = 27 where id_piano_monitoraggio  = 370 and enabled_campo and nome_campo in ('b16');
update campioni_html_fields set ordine_campo = 28 where id_piano_monitoraggio  = 370 and enabled_campo and nome_campo in ('b17');

update campioni_html_fields set valore_campo = '' where nome_campo = 'a3_attr_2_quantita';

-- View: public.ricerca_anagrafiche

-- DROP VIEW public.ricerca_anagrafiche;

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
            ELSE COALESCE(oa5.city,oa1.city,oa7.city,'N.D.')::character varying
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
    COALESCE(mltemp2.codice_attivita, mltemp.codice_attivita, ml8.codice_attivita) AS codice_attivita,
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
     LEFT JOIN ml8_linee_attivita_nuove_materializzata mltemp ON mltemp.codice = l.codice_univoco_ml AND mltemp.rev = 8
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
     LEFT JOIN master_list_flag_linee_attivita ml ON ml.codice_univoco = ((((COALESCE(mltemp2.codice_macroarea, mltemp.codice_macroarea, ml8.codice_macroarea) || '-'::text) || COALESCE(mltemp2.codice_aggregazione, mltemp.codice_aggregazione, ml8.codice_aggregazione)) || '-'::text) || COALESCE(mltemp2.codice_attivita, mltemp.codice_attivita, ml8.codice_attivita))
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
    o.codice_attivita,
    NULL::boolean AS miscela
   FROM sintesis_operatori_denormalizzati_view o
     LEFT JOIN sintesis_stabilimento_mobile m ON m.id_rel_stab_lp = o.id_linea_attivita;

ALTER TABLE public.ricerca_anagrafiche
  OWNER TO postgres;

delete from ricerche_anagrafiche_old_materializzata;
insert into ricerche_anagrafiche_old_materializzata  (select * from ricerca_anagrafiche); 