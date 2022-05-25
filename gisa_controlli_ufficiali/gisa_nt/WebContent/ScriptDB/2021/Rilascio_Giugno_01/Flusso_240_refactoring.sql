

--DROP FUNCTION public.get_modello_pnaa(integer); 
CREATE OR REPLACE FUNCTION public.get_modello_pnaa(IN _idcampione integer)
  RETURNS TABLE(id_campione integer  
		, enteredby integer
	, entered timestamp without time zone
	, id integer
	, numero_scheda text	
	, campione_motivazione text
	, campione_data timestamp without time zone
	, campione_verbale text
	, campione_codice_preaccettazione text
	, campione_asl text
	, campione_percontodi text
	, campione_anno text
	, campione_mese text
	, campione_giorno text
	, campione_ore text
	, campione_presente text
	, campione_sottoscritto text
	, campione_num_prelevati text --20sima colonna
	, id_dpa integer
	, id_strategia_campionamento int
	, id_metodo_campionamento int
	, id_programma_controllo int
	, programma_controllo_fa_specifica text
	, programma_controllo_an_specifica text
	, programma_controllo_ci_specifica text
	, programma_controllo_at_specifica text
	, programma_controllo_ao_specifica text
	, programma_controllo_az_specifica text
	, programma_controllo_co_fa_specifica text
	, programma_controllo_co_ci_specifica text
	, programma_controllo_in_ci_specifica text
	, programma_controllo_in_ra_specifica text
	, programma_controllo_in_pe_specifica text --35sima
	, micotossine_specifica text
	, altro_specifica text 
	, id_principi_additivi int
	, id_principi_additivi_co int
	, quantita_pa text
	, id_contaminanti int
	, prelevatore text
	, id_luogo_prelievo int
	, codice_luogo_prelievo text
	, targa_mezzo text
	, indirizzo_luogo_prelievo text
	, comune_luogo_prelievo text
	, provincia_luogo_prelievo text
	, lat_luogo_prelievo text
	, lon_luogo_prelievo text
	, ragione_sociale text
	, rappresentante_legale text
	, codice_fiscale text
	, detentore text
	, telefono text
	, id_matrice_campione integer --b1
        , lista_specie_vegetale_dichiarata text  
	, mangime_semplice_specifica text 
	, id_mangime_composto integer
	, id_premiscela_additivi integer
	, trattamento_mangime text --b2
	--, confezionamento text
	, id_confezionamento integer
	, ragione_sociale_ditta_produttrice text
	, indirizzo_ditta_produttrice text
	, lista_specie_alimento_destinato text
	, id_metodo_produzione integer
	, nome_commerciale_mangime text --b8
	, lista_stato_prodotto_prelievo text
	, responsabile_etichettatura text --b10
	, indirizzo_responsabile_etichettatura text --b11
	, paese_produzione text --b12
	, data_produzione text --b13
	, data_scadenza text --b14
	, num_lotto text --b15
	, dimensione_lotto text --b16
	, ingredienti text -- b17
	, commenti_mangime_prelevato text
	, laboratorio_destinazione text
	, id_allega_cartellino integer
	, descrizione_attrezzature text
	, num_punti text
	, num_ce text
	, volume text
	, operazioni text
	, volume_2 text
	, operazioni_2 text
	, numero_cf text
	, quantita_gml text
	, dichiarazione text
	, conservazione_campione text 
	, num_copie text
	, num_campioni_finali text
	, custode text
	, id_campione_finale integer
	, id_rinuncia_campione integer
	, codice_esame text
	, codice_osa text
	, lista_codice_matrice text
	, volume_3 text
	, num_campioni_finali_invio text
	, num_copie_invio text
	, destinazione_invio text
	, data_invio text
	, id_cg_ridotto integer
	, id_cg_cr integer
	, id_sequestro_partita integer
	, id_categoria_sottoprodotti integer
) AS
$BODY$
DECLARE
	 	
BEGIN
	return query
select
        t.ticketid as id_campione
	, v.enteredby
	, v.entered
	, v.id
	, v.numero_scheda
	, (select p.short_description::text from ticket t 
	join lookup_piano_monitoraggio p on t.motivazione_piano_campione = p.code
	where t.ticketid = _idcampione) as campione_motivazione
	, t.assigned_date as campione_data 
	, t.location::text as campione_verbale
	, (select codice_preaccettazione from preaccettazionesigla.get_codice_preaccettazione_da_campione(_idcampione::text)) as campione_codice_preaccettazione
	, asl.description::text as campione_asl
	, (select string_agg(n.descrizione_padre,'<br>') as per_conto_di_completo from tipocontrolloufficialeimprese tcu 
	left join dpat_strutture_asl n on n.id = tcu.id_unita_operativa   
	where tcu.idcontrollo = (select id_controllo_ufficiale::int from ticket where ticketid = _idcampione) and tcu.enabled and (tcu.tipoispezione > 0 or tcu.pianomonitoraggio > 0) 
	and tcu.id_unita_operativa > 0 )as campione_percontodi
	, date_part('year', t.assigned_date)::text as campione_anno
	, CASE WHEN date_part('month', t.assigned_date)= 1 then 'GENNAIO' WHEN date_part('month', t.assigned_date)= 2 then 'FEBBRAIO' WHEN date_part('month', t.assigned_date)= 3 then 'MARZO' WHEN date_part('month', t.assigned_date)= 4 then 'APRILE' WHEN date_part('month', t.assigned_date)= 5 then 'MAGGIO' WHEN date_part('month', t.assigned_date)= 6 then 'GIUGNO' WHEN date_part('month', t.assigned_date)= 7 then 'LUGLIO' WHEN date_part('month', t.assigned_date)= 8 then 'AGOSTO' WHEN date_part('month', t.assigned_date)= 9 then 'SETTEMBRE' WHEN date_part('month', t.assigned_date)= 10 then 'OTTOBRE' WHEN date_part('month', t.assigned_date)= 11 then 'NOVEMBRE' WHEN date_part('month', t.assigned_date)= 12 then 'DICEMBRE' END as campione_mese
	, date_part('day', t.assigned_date)::text as campione_giorno
	, v.campione_ore
	, v.campione_presente 
	, (select string_agg(CASE WHEN a.user_id > 0 THEN concat_ws(' ', c.namefirst, c.namelast) ELSE concat_ws(' ', ce.namefirst, ce.namelast) END, ', ') 
	   from cu_nucleo cun left join access_ a on a.user_id = cun.id_componente 
	   left join contact_ c on c.contact_id = a.contact_id left join access_ext_ ae on ae.user_id = cun.id_componente left join contact_ext_ ce 
	   on ce.contact_id = ae.contact_id where cun.id_controllo_ufficiale::int = (select id_controllo_ufficiale::int from ticket where ticketid = _idcampione)) as campione_sottoscritto 
	, v.campione_num_prelevati 
	, v.id_dpa
	, (select CASE 
			WHEN p.short_description::text ilike '%SORV%' THEN 2
			WHEN p.short_description::text ilike '%MON%' THEN 1
			WHEN p.short_description::text ilike '%EXTRAPIANO%' THEN 4 --- non è contemplato extrapiano mon e extrapiano sorv
			WHEN p.short_description::text ilike '%SOSPETT%' THEN 3
			ELSE 1
		END
		from ticket t 
		join lookup_piano_monitoraggio p on t.motivazione_piano_campione = p.code
		where t.ticketid = _idcampione) AS id_strategia_campionamento
	, v.id_metodo_campionamento
	, (select CASE  
			WHEN  p.short_description::text ~~* '%BSE%'::text OR p.short_description::text ~~* '%acquacoltura%'::text THEN 1
			WHEN  p.short_description::text ~~* '%SOSTANZE FARMACOLOGICHE%CARRY OVER)'::text THEN 10
			WHEN upper( p.short_description::text) ~~* '%tab. 3.2 finalità 1%'::text THEN 3
			WHEN upper( p.short_description::text) ~~* '%tab. 3.2 finalità 2%'::text THEN 4
			WHEN upper( p.short_description::text) ~~* '%tab. 3.2 finalità 3%'::text THEN 5
			WHEN upper( p.short_description::text) ~~* '%CONTAMINANTI%'::text OR p.short_description::text ~~* '%radionuclidi%'::text THEN 11
			WHEN  p.short_description::text ~~* '%DIOSSINE%'::text THEN 2
			WHEN  p.short_description::text ~~* '%MICOTOSSINE%'::text THEN 6
			WHEN  p.short_description::text ~~* '%SALMONELLA%'::text THEN 7
			WHEN upper(p.short_description::text) ~~* '%OGM AUTORIZZAT%'::text THEN 8
			WHEN upper(p.short_description::text) ~~* '%OGM NON AUTORIZZAT%'::text THEN 9
			ELSE 12 --altro
		END 
		from ticket t 
		join lookup_piano_monitoraggio p on t.motivazione_piano_campione = p.code
		where t.ticketid = _idcampione) AS id_programma_controllo --- da recuperare
	, v.programma_controllo_fa_specifica 
	, v.programma_controllo_an_specifica
	, v.programma_controllo_ci_specifica 
	, v.programma_controllo_at_specifica 
	, v.programma_controllo_ao_specifica 
	, v.programma_controllo_az_specifica 
	, v.programma_controllo_co_fa_specifica 
	, v.programma_controllo_co_ci_specifica   
	, v.programma_controllo_in_ci_specifica
	, v.programma_controllo_in_ra_specifica
	, v.programma_controllo_in_pe_specifica
	, v.micotossine_specifica
	, v.altro_specifica
	, v.id_principi_additivi
	, v.id_principi_additivi_co
	, v.quantita_pa 
	, v.id_contaminanti 	
	, (select string_agg(CASE WHEN a.user_id > 0 THEN concat_ws(' ', c.namefirst, c.namelast) ELSE concat_ws(' ', ce.namefirst, ce.namelast) END, ', ') 
	   from cu_nucleo cun left join access_ a on a.user_id = cun.id_componente 
	   left join contact_ c on c.contact_id = a.contact_id left join access_ext_ ae on ae.user_id = cun.id_componente left join contact_ext_ ce 
	   on ce.contact_id = ae.contact_id where cun.id_controllo_ufficiale::int = (select id_controllo_ufficiale::int from ticket where ticketid = _idcampione)) as prelevatore
	 , v.id_luogo_prelievo
	 , v.codice_luogo_prelievo 
	 , v.targa_mezzo 
	, CASE WHEN org.org_id > 0 THEN coalesce(az.indrizzo_azienda::text, orga.addrline1::text) WHEN opstab.id > 0 THEN opind.via::text WHEN sistab.id>0 THEN siind.via::text end, --a8
		CASE WHEN org.org_id > 0 THEN coalesce(azc.comune::text, orga.city::text) WHEN opstab.id > 0 THEN opc.nome::text WHEN sistab.id>0 THEN sic.nome::text end, --a9
		CASE WHEN org.org_id > 0 THEN 
		CASE when org.tipologia = 2 then coalesce(az.prov_sede_azienda::text,orga.city::text) 
		WHEN org.tipologia <> 2 and org.site_id = 201 then 'AV'
		WHEN org.tipologia <> 2 and org.site_id = 202 then 'BN'
		WHEN org.tipologia <> 2 and org.site_id = 203 then 'CE'
		WHEN org.tipologia <> 2 and org.site_id in (204,205,206) then 'NA'
		WHEN org.tipologia <> 2 and org.site_id = 207 then 'SA' END		
		WHEN opstab.id > 0 THEN opprov.description::text WHEN sistab.id>0 THEN siprov.description::text end, --a10
		CASE WHEN org.org_id > 0 THEN coalesce(az.latitudine_geo::text, latitude::text) WHEN opstab.id > 0 THEN opind.latitudine::text WHEN sistab.id>0 THEN siind.latitudine::text end, --a11_1
		CASE WHEN org.org_id > 0 THEN coalesce(az.longitudine_geo::text, longitude::text) WHEN opstab.id > 0 THEN opind.longitudine::text WHEN sistab.id>0 THEN siind.longitudine::text end, --a11_2
		CASE WHEN org.org_id > 0 THEN coalesce(op_prop.nominativo::text, org.name::text) WHEN opstab.id > 0 THEN opop.ragione_sociale::text WHEN sistab.id>0 THEN siop.ragione_sociale::text end, --a12
		CASE WHEN org.org_id > 0 THEN coalesce(op_prop.nominativo::text,concat(org.nome_rappresentante, ' ', org.cognome_rappresentante)::text) WHEN opstab.id > 0 THEN concat(opsf.nome, ' ', opsf.cognome)::text WHEN sistab.id>0 THEN concat(sisf.nome, ' ', sisf.cognome)::text end, --a13
		CASE WHEN org.org_id > 0 THEN COALESCE(op_prop.cf, org.codice_fiscale_rappresentante)::text WHEN opstab.id > 0 THEN opsf.codice_fiscale WHEN sistab.id>0 THEN sisf.codice_fiscale end, --a14
		CASE WHEN org.org_id > 0 THEN coalesce(op_detentore.nominativo::text,concat(org.nome_rappresentante, ' ', org.cognome_rappresentante)::text) WHEN opstab.id > 0 THEN opsf.codice_fiscale::text WHEN sistab.id>0 THEN sisf.codice_fiscale::text end --a15
	 , v.telefono
	 , v.id_matrice_campione  --b1
         , v.lista_specie_vegetale_dichiarata  
	 , v.mangime_semplice_specifica  
	 , v.id_mangime_composto
	 , v.id_premiscela_additivi
	 , v.trattamento_mangime  --b2
	 --, v.confezionamento 
	 , v.id_confezionamento
	 , v.ragione_sociale_ditta_produttrice 
	 , v.indirizzo_ditta_produttrice 
	 , v.lista_specie_alimento_destinato 
	 , v.id_metodo_produzione 
	 , v.nome_commerciale_mangime  --b8
	 , v.lista_stato_prodotto_prelievo 
	 , v.responsabile_etichettatura  --b10
	 , v.indirizzo_responsabile_etichettatura  --b11
	 , v.paese_produzione  --b12
	 , v.data_produzione --b13
	 , v.data_scadenza  --b14
	 , v.num_lotto  --b15
	 , v.dimensione_lotto  --b16
	 , v.ingredienti  -- b17
	 , v.commenti_mangime_prelevato   
	 , v.laboratorio_destinazione --- sezione C
	 , v.id_allega_cartellino 
	 , v.descrizione_attrezzature 
	 , v.num_punti 
	 , v.num_ce 
	 , v.volume 
	 , v.operazioni 
	 , v.volume_2 
	 , v.operazioni_2 
	 , v.numero_cf 
	 , v.quantita_gml 
	 , v.dichiarazione 
	 , v.conservazione_campione  
	 , v.num_copie 	
	 , v.num_campioni_finali 
	 , v.custode 
	 , v.id_campione_finale
	 , v.id_rinuncia_campione 
	 , (select p.codice_esame::text from ticket t 
		join lookup_piano_monitoraggio p on t.motivazione_piano_campione = p.code
		where t.ticketid = _idcampione) as codice_esame 
	, coalesce(org.account_number, opstab.numero_registrazione, sistab.numero_registrazione)::text as codice_osa 
	, (select concat_ws(',',m3.codice_esame,m2.codice_esame,m.codice_esame) from matrici_campioni mc left join 
	   matrici m on m.matrice_id = mc.id_matrice --figlio
	   left join matrici m2 on m2.matrice_id = m.id_padre
	   left join matrici m3 on m3.matrice_id = m2.id_padre
	  where mc.id_campione = _idcampione ) lista_codice_matrice
	, v.volume_3  
	, v.num_campioni_finali_invio 
	, v.num_copie_invio 
	, v.destinazione_invio 
	, v.data_invio 
	, v.id_cg_ridotto 
	, v.id_cg_cr 
	, v.id_sequestro_partita 
	, v.id_categoria_sottoprodotti
	from ticket t
	left join lookup_site_id asl on asl.code = t.site_id
	left join modello_pnaa_values v on v.id_campione = t.ticketid and v.trashed_date is null
	left join organization org on t.org_id > 0 and org.org_id = t.org_id and org.trashed_date is null
	-- gestione campi anagrafica
	left join organization_address orga on t.org_id > 0 and orga.org_id = t.org_id and org.trashed_date is null and orga.address_type=5
	left join aziende az ON az.cod_azienda = org.account_number::text AND org.tipologia = 2
	LEFT JOIN operatori_allevamenti op_detentore ON org.cf_correntista::text = op_detentore.cf
	LEFT JOIN operatori_allevamenti op_prop ON org.codice_fiscale_rappresentante::text = op_prop.cf
	LEFT JOIN comuni_old azc ON azc.codiceistatcomune::text = az.cod_comune_azienda
	left join opu_stabilimento opstab on t.id_stabilimento > 0 and opstab.id = t.id_stabilimento and opstab.trashed_date is null
	left join opu_operatore opop on opop.id = opstab.id_operatore
	left join opu_rel_operatore_soggetto_fisico oprelos on oprelos.id_operatore = opop.id
	left join opu_soggetto_fisico opsf on opsf.id = oprelos.id_soggetto_fisico
	left join opu_indirizzo opind on opind.id = opstab.id_indirizzo
	left join comuni1 opc on opc.id = opind.comune
	left join lookup_province opprov on opprov.code = opc.cod_provincia::integer
	left join sintesis_stabilimento sistab on t.alt_id > 0 and sistab.alt_id = t.alt_id and sistab.trashed_date is null
	left join sintesis_operatore siop on siop.id = sistab.id_operatore
	left join sintesis_rel_operatore_soggetto_fisico sirelos on sirelos.id_operatore = siop.id
	left join sintesis_soggetto_fisico sisf on sisf.id = sirelos.id_soggetto_fisico
	left join sintesis_indirizzo siind on siind.id = sistab.id_indirizzo
	left join comuni1 sic on sic.id = siind.comune
	left join lookup_province siprov on siprov.code = sic.cod_provincia::integer
	--left join ricerche_anagrafiche_old_materializzata r ON (t.alt_id = r.riferimento_id AND r.riferimento_id_nome_tab = 'sintesis_stabilimento'::text OR t.id_stabilimento = r.riferimento_id AND r.riferimento_id_nome_tab = 'opu_stabilimento'::text OR t.org_id = r.riferimento_id AND r.riferimento_id_nome_tab = 'organization'::text OR t.id_apiario = r.riferimento_id AND r.riferimento_id_nome_tab = 'apicoltura_imprese'::text) AND t.tipologia = 2 AND t.trashed_date IS NULL
	where t.ticketid = _idcampione and t.trashed_date is null;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_modello_pnaa(integer)
  OWNER TO postgres;

--select * from public.get_modello_pnaa(1594375)

--drop table public.modello_pnaa_values;
CREATE TABLE public.modello_pnaa_values
(
  id serial,
  enteredby integer,
  entered timestamp without time zone DEFAULT now(),
  trashed_date timestamp without time zone,
  id_campione integer,
  numero_scheda text,
  campione_ore text, 
  campione_presente text,
  telefono text,
  targa_mezzo text,
  id_luogo_prelievo integer,
  id_principi_additivi integer,
  id_principi_additivi_co integer,
  quantita_pa text,
  id_contaminanti integer,
  id_metodo_campionamento integer,
  id_dpa integer,
  campione_num_prelevati text,
  codice_luogo_prelievo text,
  micotossine_specifica text,
  altro_specifica text,
  programma_controllo_fa_specifica text,
  programma_controllo_an_specifica text,
  programma_controllo_ci_specifica text,
  programma_controllo_at_specifica text,
  programma_controllo_ao_specifica text,
  programma_controllo_az_specifica text,
  programma_controllo_co_fa_specifica text,
  programma_controllo_co_ci_specifica text,
  programma_controllo_in_ci_specifica text,
  programma_controllo_in_ra_specifica text,
  programma_controllo_in_pe_specifica text,
  id_matrice_campione integer --b1
  , mangime_semplice_specifica text 
  , id_mangime_composto integer
  , id_premiscela_additivi integer
  , trattamento_mangime text --b2
  --, confezionamento text
  , id_confezionamento integer
  , ragione_sociale_ditta_produttrice text
  , indirizzo_ditta_produttrice text
  , id_metodo_produzione integer
  , nome_commerciale_mangime text
  , responsabile_etichettatura text --b10
  , indirizzo_responsabile_etichettatura text --b11
  , paese_produzione text --b12
  , data_produzione text --b13
  , data_scadenza text --b14
  , num_lotto text --b15
  , dimensione_lotto text --b16
  , ingredienti text -- b17
  , commenti_mangime_prelevato text 
  , laboratorio_destinazione text--C 
  , id_allega_cartellino integer
  , descrizione_attrezzature text
  , num_punti text
  , num_ce text
  , volume text
  , operazioni text
  , volume_2 text
  , operazioni_2 text
  , numero_cf text
  , quantita_gml text
  , dichiarazione text
  , conservazione_campione text 
  , num_copie text
  , num_campioni_finali text
  , custode text
  , id_campione_finale integer
  , id_rinuncia_campione integer
  , lista_specie_vegetale_dichiarata text
  , lista_specie_alimento_destinato text
  , lista_stato_prodotto_prelievo text
  , volume_3 text
  , num_campioni_finali_invio text
  , num_copie_invio text
  , destinazione_invio text
  , data_invio text
  , id_cg_ridotto integer
  , id_cg_cr integer
  , id_sequestro_partita integer
  , id_categoria_sottoprodotti integer
)

WITH (
  OIDS=FALSE
);
ALTER TABLE public.modello_pnaa_values
  OWNER TO postgres;
--drop table lookup_pnaa_strategia_campionamento
CREATE TABLE public.lookup_pnaa_strategia_campionamento
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
ALTER TABLE public.lookup_pnaa_strategia_campionamento
  OWNER TO postgres;

	
insert into lookup_pnaa_strategia_campionamento (description,level) values('Piano Monitoraggio','1');
insert into lookup_pnaa_strategia_campionamento (description,level) values('Piano Sorveglianza ','2');
insert into lookup_pnaa_strategia_campionamento (description,level) values('Sospetto','3');
insert into lookup_pnaa_strategia_campionamento (description,level) values('Extra-Piano: Monitoraggio','4');
insert into lookup_pnaa_strategia_campionamento (description,level) values('Extra-Piano: Sorveglianza','5');

----------------------------------------------------------------18/06

CREATE TABLE public.lookup_pnaa_categoria_sottoprodotti
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
ALTER TABLE public.lookup_pnaa_categoria_sottoprodotti
  OWNER TO postgres;


insert into lookup_pnaa_categoria_sottoprodotti (description,level) values('1','1');
insert into lookup_pnaa_categoria_sottoprodotti (description,level) values('2','2');
insert into lookup_pnaa_categoria_sottoprodotti (description,level) values('3','3');


insert into lookup_matrice_campione_sinvsa_new (code,description, enabled) values (7,'Sottoprodotti di Cat.',true)
select * from lookup_matrice_campione_sinvsa_new order by level
update lookup_matrice_campione_sinvsa_new set level = code+1 where code >2
update lookup_matrice_campione_sinvsa_new set level = 2 where code =7
update lookup_matrice_campione_sinvsa_new set level = 3 where code =2

update lookup_luogo_prelievo  set description = 'Attivita'' di importazione (Primo deposito di materie prime importate)' where code=59;
--------------------------------------18/06-------------------------------------------------

--drop table lookup_pnaa_programma_controllo
CREATE TABLE public.lookup_pnaa_programma_controllo
(
  code serial,
  description character varying(150) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.lookup_pnaa_programma_controllo
  OWNER TO postgres;

--select * from lookup_pnaa_programma_controllo
insert into lookup_pnaa_programma_controllo (description,level) values('Costituenti di origine animale vietati','1');
insert into lookup_pnaa_programma_controllo (description,level) values('Diossine e PCB','2');
insert into lookup_pnaa_programma_controllo (description,level) values('Titolo','7');
insert into lookup_pnaa_programma_controllo (description,level) values('Uso illecito','8');
insert into lookup_pnaa_programma_controllo (description,level) values('Uso improprio','9');
insert into lookup_pnaa_programma_controllo (description,level) values('Micotossine e Tossine Vegetali','4');
insert into lookup_pnaa_programma_controllo (description,level) values('Salmonella','3'); -- (specificare )da creare text
insert into lookup_pnaa_programma_controllo (description,level) values('OGM Autorizzato','5');
insert into lookup_pnaa_programma_controllo (description,level) values('OGM NON Autorizzato','6');
insert into lookup_pnaa_programma_controllo (description,level) values('Principi farmacologicamente attivi e additivi per CARRY OVER','10');
insert into lookup_pnaa_programma_controllo (description,level) values('Contaminanti inorganici e composti azotati,composti organoclorurati, radionuclidi','11');
insert into lookup_pnaa_programma_controllo (description,level) values('Altro','12');

--select * from lookup_principi_farm_attivi_additivi    
--select * from lookup_principi_farm_attivi_additivi_carryover 
--select * from lookup_contaminanti     

CREATE TABLE public.lookup_pnaa_premiscela_additivi
(
  code serial,
  description character varying(150) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.lookup_pnaa_premiscela_additivi
  OWNER TO postgres;

insert into lookup_pnaa_premiscela_additivi (description,level) values('Additivi tecnologici','1');
insert into lookup_pnaa_premiscela_additivi (description,level) values('Additivi organolettici','2');
insert into lookup_pnaa_premiscela_additivi (description,level) values('Additivi nutrizionali','3');
insert into lookup_pnaa_premiscela_additivi (description,level) values('Additivi zootecnici','4');
insert into lookup_pnaa_premiscela_additivi (description,level) values('Coccidiostatico/istomonostaico','5');


--drop TABLE public.lookup_pnaa_mangime_composto
CREATE TABLE public.lookup_pnaa_mangime_composto
(
  code serial,
  description character varying(150) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.lookup_pnaa_mangime_composto
  OWNER TO postgres;

insert into lookup_pnaa_mangime_composto (description,level) values('Mangime completo','1');
insert into lookup_pnaa_mangime_composto (description,level) values('Mangime complementare','2');
insert into lookup_pnaa_mangime_composto (description,level) values('Mangime d''allattamento','3');

 CREATE TABLE public.lookup_pnaa_si_no
(
  code serial,
  description character varying(150) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.lookup_pnaa_si_no
  OWNER TO postgres;
insert into lookup_pnaa_si_no (description,level) values('SI','1');
insert into lookup_pnaa_si_no (description,level) values('NO','2');

CREATE TABLE public.lookup_pnaa_campione_finale
(
  code serial,
  description character varying(150) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.lookup_pnaa_campione_finale
  OWNER TO postgres;
 
insert into lookup_pnaa_campione_finale (description,level) values('un Campione Finale per conto del produttore','1');
insert into lookup_pnaa_campione_finale (description,level) values('un Campione Finale per conto proprio','2');

update lookup_matrice_campione_sinvsa_new    set level=code;

--- da lanciare
CREATE TABLE public.lookup_pnaa_confezionamento(
  code serial,
  description character varying(150) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.lookup_pnaa_confezionamento
  OWNER TO postgres;
 
insert into lookup_pnaa_confezionamento (description,level) values('AL=ALTRO','1');
insert into lookup_pnaa_confezionamento (description,level) values('BB=BIG BAG','2');
insert into lookup_pnaa_confezionamento (description,level) values('CI=CISTERNA','3');
insert into lookup_pnaa_confezionamento (description,level) values('LA=LATTINA','4');
insert into lookup_pnaa_confezionamento (description,level) values('NE=NESSUNO','5');
insert into lookup_pnaa_confezionamento (description,level) values('SA=SACCO','6');
insert into lookup_pnaa_confezionamento (description,level) values('TA=TANICA','7');

--select * from lookup_specie_alimento  
--insert into lookup_specie_alimento (code, description, level) values(18,'DPA','17');
--insert into lookup_specie_alimento (code,description, level) values(19,'NON DPA','18');
--- da lanciare
--delete from lookup_specie_alimento where code in (18,19)
update lookup_matrice_campione_sinvsa_new    set level=code;

---------------- nuova 
CREATE TABLE public.lookup_pnaa_cg_ridotto(
  code serial,
  description character varying(150) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.lookup_pnaa_cg_ridotto
  OWNER TO postgres;

 insert into lookup_pnaa_cg_ridotto (description,level) values('E'' stato ridotto','1');
 insert into lookup_pnaa_cg_ridotto (description,level) values('Non e'' stato ridotto','2');


CREATE TABLE public.lookup_pnaa_cg_cr(
  code serial,
  description character varying(150) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.lookup_pnaa_cg_cr
  OWNER TO postgres;


 insert into lookup_pnaa_cg_cr (description,level) values('CG','1');
 insert into lookup_pnaa_cg_cr (description,level) values('CR','2');


CREATE TABLE public.lookup_pnaa_sequestro_partita(
  code serial,
  description character varying(150) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.lookup_pnaa_sequestro_partita
  OWNER TO postgres;

 insert into lookup_pnaa_sequestro_partita (description,level) values('Viene posta in sequestro','1');
 insert into lookup_pnaa_sequestro_partita (description,level) values('Non viene posta in sequestro','2');




CREATE OR REPLACE FUNCTION public.genera_num_scheda_pnaa()
  RETURNS text AS
$BODY$
DECLARE

BEGIN
--codice= "AAAAXXXXOX";
return concat('R00',(SELECT DATE_PART('year',current_date)),(select nextval('scheda_pnaa_num_seq')),'O' );

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.genera_num_scheda_pnaa()
  OWNER TO postgres;


CREATE OR REPLACE FUNCTION public.upsert_modello_pnaa(_id_campione integer  
		, _enteredby integer
		, _campione_num_prelevati text
		, _id_metodo_campionamento int
		, _id_principi_additivi int
		, _id_principi_additivi_co int
		, _id_contaminanti int
		, _programma_controllo_fa_specifica text
		, _programma_controllo_an_specifica text
		, _programma_controllo_ci_specifica text
		, _programma_controllo_at_specifica text
		, _programma_controllo_ao_specifica text
		, _programma_controllo_az_specifica text
		, _programma_controllo_co_fa_specifica text
		, _programma_controllo_co_ci_specifica text
		, _programma_controllo_in_ci_specifica text
		, _programma_controllo_in_ra_specifica text
		, _programma_controllo_in_pe_specifica text 
		, _quantita_pa text
		, _id_luogo_prelievo int
		, _codice_luogo_prelievo text
		, _targa_mezzo text
		, _telefono text
		, _id_matrice_campione integer --b1
		, _mangime_semplice_specifica text 
		, _id_mangime_composto integer
		, _id_premiscela_additivi integer
		, _lista_specie_vegetale_dichiarata text  
		, _trattamento_mangime text --b2
		--, _confezionamento text
		, _id_confezionamento integer
		, _ragione_sociale_ditta_produttrice text
		, _indirizzo_ditta_produttrice text
		, _lista_specie_alimento_destinato text
		, _id_metodo_produzione integer
		, _nome_commerciale_mangime text --b8
		, _lista_stato_prodotto_prelievo text
		, _responsabile_etichettatura text --b10
		, _indirizzo_responsabile_etichettatura text --b11
		, _paese_produzione text --b12
		, _data_produzione text --b13
		, _data_scadenza text --b14
		, _num_lotto text --b15
		, _dimensione_lotto text --b16
		, _ingredienti text -- b17
		, _commenti_mangime_prelevato text
		, _laboratorio_destinazione text
		, _id_allega_cartellino integer
		, _descrizione_attrezzature text
		, _num_punti text
		, _num_ce text
		, _volume text
		, _operazioni text
		, _volume_2 text
		, _operazioni_2 text
		, _numero_cf text
		, _quantita_gml text
		, _dichiarazione text
		, _conservazione_campione text 
		, _num_copie text
		, _num_campioni_finali text
		, _custode text
		, _id_rinuncia_campione integer
		, _id_campione_finale integer
		, _micotossine_specifica text
		, _altro_specifica text 
		, _campione_ore text
		, _id_dpa integer
		, _volume_3 text
		, _num_campioni_finali_invio text
		, _num_copie_invio text
		, _destinazione_invio text
		, _data_invio text
		, _campione_presente text
		, _id_cg_ridotto integer
		, _id_cg_cr integer
		, _id_sequestro_partita integer
		, _id_categoria_sottoprodotti integer
		)
  RETURNS integer AS
$BODY$
DECLARE 
	id_modello_pnaa integer;
	conta_campione integer;
	_num_scheda text;
BEGIN

--2. Se esiste, recupera il suo numero_scheda e metti tutto a trashed
--3. Se non esiste, genera un numero_scheda-
--4 .Insert nei values

	conta_campione := (select count(*) from modello_pnaa_values where id_campione = _id_campione and trashed_date is null);
	raise info '%', conta_campione;
	if (conta_campione > 0) then 
		-- recupera numero scheda e metti tutti i record a trashed;
		_num_scheda := (select numero_scheda from modello_pnaa_values  where id_campione = _id_campione and trashed_date is null); --1594375 (R00202115574O)
		update modello_pnaa_values set trashed_date=current_date where id_campione = _id_campione and trashed_date is null;
	else
		-- genera numero scheda + insert
		_num_scheda := (select genera_num_scheda_pnaa from genera_num_scheda_pnaa());
		raise info '%', _num_scheda;
		-- insert nella tabella dei values
	end if;
		
	insert into modello_pnaa_values (
			numero_scheda
			,id_campione   
			, enteredby 
			, campione_num_prelevati 
			, id_metodo_campionamento 
			, id_principi_additivi 
			, id_principi_additivi_co
			, id_contaminanti 
			, programma_controllo_fa_specifica 
			, programma_controllo_an_specifica 
			, programma_controllo_ci_specifica 
			, programma_controllo_at_specifica 
			, programma_controllo_ao_specifica 
			, programma_controllo_az_specifica 
			, programma_controllo_co_fa_specifica 
			, programma_controllo_co_ci_specifica 
			, programma_controllo_in_ci_specifica 
			, programma_controllo_in_ra_specifica 
			, programma_controllo_in_pe_specifica  
			, quantita_pa  
			, id_luogo_prelievo 
			, codice_luogo_prelievo 
			, targa_mezzo 
			, telefono 
			, id_matrice_campione  --b1
			, mangime_semplice_specifica  
			, id_mangime_composto 
			, id_premiscela_additivi 
			, lista_specie_vegetale_dichiarata   
			, trattamento_mangime  --b2
			--, confezionamento 
			, id_confezionamento
			, ragione_sociale_ditta_produttrice 
			, indirizzo_ditta_produttrice 
			, lista_specie_alimento_destinato 
			, id_metodo_produzione 
			, nome_commerciale_mangime  --b8
			, lista_stato_prodotto_prelievo 
			, responsabile_etichettatura  --b10
			, indirizzo_responsabile_etichettatura  --b11
			, paese_produzione  --b12
			, data_produzione  --b13
			, data_scadenza  --b14
			, num_lotto  --b15
			, dimensione_lotto  --b16
			, ingredienti  -- b17
			, commenti_mangime_prelevato 
			, laboratorio_destinazione 
			, id_allega_cartellino 
			, descrizione_attrezzature 
			, num_punti 
			, num_ce 
			, volume 
			, operazioni 
			, volume_2 
			, operazioni_2 
			, numero_cf 
			, quantita_gml 
			, dichiarazione 
			, conservazione_campione  
			, num_copie 
			, num_campioni_finali 
			, custode 
			, id_rinuncia_campione 
			, id_campione_finale 
			, micotossine_specifica 
			, altro_specifica  
			, campione_ore 
			, id_dpa
			, volume_3
			, num_campioni_finali_invio 
			, num_copie_invio
			, destinazione_invio 
			, data_invio
			, campione_presente
			, id_cg_ridotto
			, id_cg_cr 
			, id_sequestro_partita
			, id_categoria_sottoprodotti
		) values
		(	
			_num_scheda
			, _id_campione   
			, _enteredby 
			, _campione_num_prelevati 
			, _id_metodo_campionamento 
			, _id_principi_additivi 
			, _id_principi_additivi_co 
			, _id_contaminanti 
			, _programma_controllo_fa_specifica 
			, _programma_controllo_an_specifica 
			, _programma_controllo_ci_specifica 
			, _programma_controllo_at_specifica 
			, _programma_controllo_ao_specifica 
			, _programma_controllo_az_specifica 
			, _programma_controllo_co_fa_specifica 
			, _programma_controllo_co_ci_specifica 
			, _programma_controllo_in_ci_specifica 
			, _programma_controllo_in_ra_specifica 
			, _programma_controllo_in_pe_specifica  
			, _quantita_pa 
			, _id_luogo_prelievo 
			, _codice_luogo_prelievo 
			, _targa_mezzo 
			, _telefono 
			, _id_matrice_campione  --b1
			, _mangime_semplice_specifica  
			, _id_mangime_composto 
			, _id_premiscela_additivi 
			, _lista_specie_vegetale_dichiarata   
			, _trattamento_mangime  --b2
			--, _confezionamento 
			, _id_confezionamento
			, _ragione_sociale_ditta_produttrice 
			, _indirizzo_ditta_produttrice 
			, _lista_specie_alimento_destinato 
			, _id_metodo_produzione 
			, _nome_commerciale_mangime  --b8
			, _lista_stato_prodotto_prelievo 
			, _responsabile_etichettatura  --b10
			, _indirizzo_responsabile_etichettatura  --b11
			, _paese_produzione  --b12
			, _data_produzione  --b13
			, _data_scadenza  --b14
			, _num_lotto  --b15
			, _dimensione_lotto  --b16
			, _ingredienti  -- b17
			, _commenti_mangime_prelevato 
			, _laboratorio_destinazione 
			, _id_allega_cartellino 
			, _descrizione_attrezzature 
			, _num_punti 
			, _num_ce 
			, _volume 
			, _operazioni 
			, _volume_2 
			, _operazioni_2 
			, _numero_cf 
			, _quantita_gml 
			, _dichiarazione 
			, _conservazione_campione  
			, _num_copie 
			, _num_campioni_finali 
			, _custode 
			, _id_rinuncia_campione 
			, _id_campione_finale 
			, _micotossine_specifica 
			, _altro_specifica 
			, _campione_ore
			, _id_dpa
			, _volume_3
			, _num_campioni_finali_invio
			, _num_copie_invio
			, _destinazione_invio 
			, _data_invio
			, _campione_presente
			, _id_cg_ridotto
			, _id_cg_cr 
			, _id_sequestro_partita
			, _id_categoria_sottoprodotti
		) 
	RETURNING id into id_modello_pnaa;

	return id_modello_pnaa;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

--select * from modello_pnaa_values  

  -- Function: public.is_controllo_chiudibile_allegati_sanzione(integer)

-- DROP FUNCTION public.is_controllo_chiudibile_allegati_sanzione(integer);
CREATE OR REPLACE FUNCTION public.is_campione_chiudibile_pnaa(_idcampione integer)
  RETURNS text AS
$BODY$
DECLARE
	messaggio text;
BEGIN

messaggio := '';

select string_agg(a.errore,' ') into messaggio from (
select distinct 
string_agg('Campione: ' ||  c.identificativo ||'; ', '') as errore
from ticket c
join dpat_indicatore_new dpat on dpat.id = c.motivazione_piano_campione and dpat.codice_esame in ('PNASV', 'PNAMT') -- valutare se mettere lookup_piano_monitoraggio?
left join modello_pnaa_values p on c.ticketid = p.id_campione and p.trashed_date is null
where c.ticketid = _idcampione and c.tipologia = 2 and c.trashed_date is null
and p.id is null group by c.ticketid
) a;

if messaggio <> '' then
messaggio := concat('Questo campione non puo essere chiuso a causa della presenza di modello PNAA non compilato. ', messaggio);
END IF;

 RETURN UPPER(messaggio);
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.is_campione_chiudibile_pnaa(integer)
  OWNER TO postgres;

  CREATE OR REPLACE FUNCTION public.is_controllo_chiudibile_pnaa(_idcu integer)
  RETURNS text AS
$BODY$
DECLARE
	messaggio text;
BEGIN

messaggio := '';

select string_agg(a.errore,' ') into messaggio from (
select distinct 
string_agg('Campione: ' ||  c.identificativo ||'; ', '') as errore
from ticket c
join ticket cu on cu.ticketid::text = c.id_controllo_ufficiale
join dpat_indicatore_new dpat on dpat.id = c.motivazione_piano_campione and dpat.codice_esame in ('PNASV', 'PNAMT') -- valutare se mettere lookup_piano_monitoraggio?
left join modello_pnaa_values p on c.ticketid = p.id_campione and p.trashed_date is null
where cu.ticketid = _idcu and c.tipologia = 2 and c.trashed_date is null
and p.id is null group by c.ticketid
) a;

if messaggio <> '' then
messaggio := concat('Questo controllo non puo essere chiuso a causa della presenza di campioni con modello PNAA non compilato. ', messaggio);
END IF;

 RETURN UPPER(messaggio);
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.is_controllo_chiudibile_pnaa(integer)
  OWNER TO postgres;
