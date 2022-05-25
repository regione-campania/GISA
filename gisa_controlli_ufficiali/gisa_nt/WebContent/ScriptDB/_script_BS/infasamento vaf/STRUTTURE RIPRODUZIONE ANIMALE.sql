
-- Chi: Bartolo Sansone
-- Quando: 19/12/2017
-- Cosa: VAF Riproduzione animale
-- GISA
-- View: public.view_anagrafica_attivita_strutture_riproduzione_animale

-- DROP VIEW public.view_anagrafica_attivita_strutture_riproduzione_animale;

CREATE OR REPLACE VIEW public.view_anagrafica_attivita_strutture_riproduzione_animale AS 
  

 SELECT DISTINCT allstab.riferimento_id,
    allstab.riferimento_id_nome,
    allstab.riferimento_id_nome_col,
    allstab.riferimento_id_nome_tab,
    allstab.data_inserimento,
    allstab.categoria_rischio,
    allstab.prossimo_controllo,
    allstab.id_indirizzo_impresa,
    allstab.id_sede_operativa,
    allstab.sede_mobile_o_altro,
    allstab.riferimento_nome_tab_indirizzi,
    allstab.id_impresa,
    allstab.riferimento_nome_tab_impresa,
    allstab.id_soggetto_fisico,
    allstab.riferimento_nome_tab_soggetto_fisico,
    allstab.id_attivita,
    allstab.pregresso_o_import,
    allstab.riferimento_org_id,
    allstab.tipo_attivita AS id_tipo_attivita,
    allstab.tipo_attivita_descrizione,
    btrim(allstab.ragione_sociale::text) AS ragione_sociale,
    btrim(allstab.codice_fiscale::text) AS codice_fiscale,
    btrim(allstab.partita_iva::text) AS partita_iva,
    NULL::text AS id_tipo_impresa_societa,
    NULL::text AS data_arrivo_pec,
    allstab.asl_rif,
    allstab.indirizzo_leg AS indirizzo_impresa,
    allstab.cap_leg AS cap_impresa,
    allstab.provincia_leg AS provincia_impresa,
    prov_impresa.code AS cod_provincia_impresa,
        CASE
            WHEN btrim(address_impresa.country::text) ~~* 'italia'::text THEN '106'::text::character varying
            ELSE address_impresa.country
        END AS nazione_impresa,
    comuni_impresa.id AS id_comune_impresa,
    allstab.comune_leg AS comune_impresa,
    o.codice_fiscale_rappresentante,
    NULL::text AS titolo_rappresentante,
    o.nome_rappresentante,
    o.cognome_rappresentante,
    o.luogo_nascita_rappresentante as comune_nascita_rappresentante,
    NULL::text AS sesso_rappresentante,
    o.telefono_rappresentante,
    o.fax AS fax_rappresentante,
    o.email_rappresentante,
    NULL::text AS telefono1_rappresentante,
    o.data_nascita_rappresentante,
    NULL::text AS documento_identita_rappresentante,
    NULL::text AS provenienza_estera_rappresentante,
    NULL::text AS provincia_nascita_rappresentante,
    NULL::text AS toponimo_rappresentante,
    NULL::text AS descr_indrizzo_rappresentante,
    NULL::text AS civico_rappresentante,
    NULL::text AS cap_rappresentante,
    NULL::text AS provincia_rappresentante,
    NULL::text AS nazione_rappresentante,
    NULL::text AS id_comune_rappresentante,
    NULL::text AS comune_rappresentante,
    allstab.indirizzo AS indirizzo_stab,
    NULL::text AS denominazione_stab,
    allstab.cap_stab,
    allstab.provincia_stab,
    prov_stab.code AS cod_provincia_stab,
        CASE
            WHEN btrim(address_stab.country::text) ~~* 'italia'::text THEN '106'::text::character varying
            ELSE address_stab.country
        END AS nazione_stab,
    comuni_stab.id AS id_comune_stab,
    allstab.comune AS comune_stab,
    NULL::text as carattere,
    null AS data_inizio_attivita_stab,
    null AS data_fine_attivita_stab,
    o.conto_corrente as tipo_veicolo,
    o.nome_correntista as identificativo_veicolo,
    o.notes as note_stabilimento,
     NULL::text as nome_rappresentante_stab,
    NULL::text as cognome_rappresentante_stab,
    NULL::text as comune_nascita_rappresentante_stab,
    NULL::text as data_nascita_rappresentante_stab,
    NULL::text AS documento_identita_rappresentante_stab,
    NULL::text AS provincia_rappresentante_stab,
    NULL::text AS comune_rappresentante_stab,
    NULL::text AS indirizzo_rappresentante_stab,
    -1 AS id_comune_rappresentante_stab
        
   FROM ricerche_anagrafiche_old_materializzata allstab
   LEFT JOIN organization o on o.org_id = allstab.riferimento_id
     LEFT JOIN organization_address address_impresa ON address_impresa.address_id = allstab.id_indirizzo_impresa
     LEFT JOIN organization_address address_stab ON address_stab.address_id = allstab.id_indirizzo_impresa
     LEFT JOIN comuni1 comuni_impresa ON upper(btrim(comuni_impresa.nome::text)) = upper(btrim(allstab.comune_leg::text))
     LEFT JOIN comuni1 comuni_stab ON upper(btrim(comuni_stab.nome::text)) = upper(btrim(allstab.comune::text))
     LEFT JOIN lookup_province prov_impresa ON upper(btrim(prov_impresa.cod_provincia)) = upper(btrim(allstab.provincia_leg::text))
     LEFT JOIN lookup_province prov_stab ON upper(btrim(prov_stab.cod_provincia)) = upper(btrim(allstab.provincia_stab::text))

  WHERE allstab.riferimento_id_nome_tab = 'organization'::text AND allstab.tipo_attivita = 1
and o.tipologia = 8;
ALTER TABLE public.view_anagrafica_attivita_strutture_riproduzione_animale
  OWNER TO postgres;
COMMENT ON VIEW public.view_anagrafica_attivita_strutture_riproduzione_animale
  IS ' STRUTTURE RIPRODUZIONE ANIMALE';

  


  -- View: public.strutture_riproduzione_animale_campi_estesi

-- DROP VIEW public.strutture_riproduzione_animale_campi_estesi;

CREATE OR REPLACE VIEW public.strutture_riproduzione_animale_campi_estesi AS 
  

 SELECT DISTINCT o.org_id as riferimento_id,
      case when centri.monta_equina_attive then 'STAZIONE DI MONTA NATURALE EQUINA'  when centri.monta_bovina_attive then 'STAZIONE DI MONTA NATURALE BOVINA'  when centri.stazione_inseminazione_equine then 'STAZIONE DI INSEMINAZIONE ARTIFICIALE EQUINA'  when centri.centro_produzione_embrioni then 'CENTRO DI PRODUZIONE EMBRIONI'  when centri.centro_produzione_sperma then 'CENTRO DI PRODUZIONE SPERMA'  when centri.gruppo_raccolta_embrioni then 'GRUPPO DI RACCOLTA EMBRIONI' else 'RECAPITI ATTIVI' end as tipo_struttura,
centri.codice_legge_30 as codice_L30,
centri.razza as razza,
centri.provv_aut as provvedimenti_autorizzazione,
centri.scadenza_aut as scadenza_autorizzazione,
centri.sede as centro_sede
        
   FROM organization o
   LEFT JOIN centri_riproduzione_animale centri on centri.org_id = o.org_id

  WHERE o.trashed_date is null and o.tipologia = 8;
ALTER TABLE public.strutture_riproduzione_animale_campi_estesi
  OWNER TO postgres;
COMMENT ON VIEW public.strutture_riproduzione_animale_campi_estesi
  IS ' STRUTTURE RIPRODUZIONE ANIMALE - CAMPI ESTESI';

  
  
  
  CREATE TABLE mapping_org_riproduzione_codice AS

select o.org_id as org_id, id_opu_lookup_norme_master_list as id_norma, o.tipologia as id_tipologia_operatore,'CG-NAZ-B' as codice_univoco_ml,true as map_completo
from organization o
join opu_lookup_norme_master_list_rel_tipologia_organzation norma on norma.tipologia_organization =o.tipologia
where o.tipologia = 8 and trashed_date is null and tipologia_strutt ilike '%CENTRO PROD EMBRIONI%'
UNION
select o.org_id as org_id, id_opu_lookup_norme_master_list as id_norma, o.tipologia as id_tipologia_operatore,'CG-NAZ-E-004' as codice_univoco_ml,true as map_completo
from organization o
join opu_lookup_norme_master_list_rel_tipologia_organzation norma on norma.tipologia_organization =o.tipologia
where o.tipologia = 8 and trashed_date is null and tipologia_strutt ilike '%CENTRO RACCOLTA EMBRIONI%'
UNION
select o.org_id as org_id, id_opu_lookup_norme_master_list as id_norma, o.tipologia as id_tipologia_operatore,'CG-NAZ-R-007' as codice_univoco_ml,true as map_completo
from organization o
join opu_lookup_norme_master_list_rel_tipologia_organzation norma on norma.tipologia_organization =o.tipologia
where o.tipologia = 8 and trashed_date is null and tipologia_strutt ilike '%RECAPITO AUTORIZZATO%'
union
select o.org_id as org_id, id_opu_lookup_norme_master_list as id_norma, o.tipologia as id_tipologia_operatore,'CG-NAZ-D-0126' as codice_univoco_ml,true as map_completo
from organization o
join opu_lookup_norme_master_list_rel_tipologia_organzation norma on norma.tipologia_organization =o.tipologia
where o.tipologia = 8 and trashed_date is null and tipologia_strutt ilike '%STAZ INSEMINAZ EQUIN%'
union
select o.org_id as org_id, id_opu_lookup_norme_master_list as id_norma, o.tipologia as id_tipologia_operatore,'CG-NAZ-P-0121' as codice_univoco_ml,true as map_completo
from organization o
join opu_lookup_norme_master_list_rel_tipologia_organzation norma on norma.tipologia_organization =o.tipologia
where o.tipologia = 8 and trashed_date is null and tipologia_strutt ilike '%STAZ MONTA BOVINA%'
union
select o.org_id as org_id, id_opu_lookup_norme_master_list as id_norma, o.tipologia as id_tipologia_operatore,'CG-NAZ-V-0127' as codice_univoco_ml,true as map_completo
from organization o
join opu_lookup_norme_master_list_rel_tipologia_organzation norma on norma.tipologia_organization =o.tipologia
where o.tipologia = 8 and trashed_date is null and tipologia_strutt ilike '%STAZ MONTA EQUINA%'
union
select o.org_id as org_id, id_opu_lookup_norme_master_list as id_norma, o.tipologia as id_tipologia_operatore,'CG-NAZ' as codice_univoco_ml,false as map_completo
from organization o
join opu_lookup_norme_master_list_rel_tipologia_organzation norma on norma.tipologia_organization =o.tipologia
where o.tipologia = 8 and trashed_date is null and tipologia_strutt ilike '%CENTRO PROD SPERMA%'



-- Function: public_functions.anagrafica_infasamento_strutture_riproduzione_animale()

-- DROP FUNCTION public_functions.anagrafica_infasamento_strutture_riproduzione_animale();

CREATE OR REPLACE FUNCTION public_functions.anagrafica_infasamento_strutture_riproduzione_animale()
  RETURNS text AS
$BODY$
 DECLARE
	recImpresa record;
	recLinea record;
	recStabInseriti record;
	recInserimento record;
	recCampiEstesi record;

	data_i timestamp without time zone;
	data_f timestamp without time zone;
	
	s text;
	utente integer;
	retInsOsa integer;
	qry text;
	idImpresaInserita integer;
	strConnTarget text;
	sqlLog text;
BEGIN 
strConnTarget:= 'dbname=vaf port=5432';
utente:=1; --utente amministratore
s:= ''; 

-- Elenco attività OPERATORI FUORI REGIONE AUTOVEICOLI mappate (totalmente o parzialmente) ML8 [ATECO]:1481
FOR recImpresa IN
	select
		riferimento_id,
		riferimento_id_nome,
		riferimento_id_nome_col,
		riferimento_id_nome_tab,
		data_inserimento,
		categoria_rischio,
		prossimo_controllo,
		note_stabilimento,
		data_inizio_attivita_stab,
		data_fine_attivita_stab,
		anag.codice_fiscale_rappresentante codice_fiscale_rappresentante,
		anag.cognome_rappresentante cognome_rappresentante,
		anag.nome_rappresentante nome_rappresentante,
		anag.data_nascita_rappresentante data_nascita_rappresentante,
		anag.comune_nascita_rappresentante comune_nascita_rappresentante,
		json_build_object('soggetto_fisico',json_build_object('titolo', anag.titolo_rappresentante,
									'cognome', anag.cognome_rappresentante,
									'nome', anag.nome_rappresentante,
									'comune_nascita', anag.comune_nascita_rappresentante,
									'codice_fiscale', anag.codice_fiscale_rappresentante,
									'sesso', anag.sesso_rappresentante,
									'telefono', anag.telefono_rappresentante,
									'fax', anag.fax_rappresentante,
									'email', anag.email_rappresentante,
									'telefono1', anag.telefono1_rappresentante,
									'data_nascita', anag.data_nascita_rappresentante,
									'documento_identita', anag.documento_identita_rappresentante,
									'provenienza_estera', anag.provenienza_estera_rappresentante,
									'provincia_nascita', anag.provincia_nascita_rappresentante,
									'indirizzo', json_build_object( 'toponimo', anag.toponimo_rappresentante,
													'descr_indrizzo', anag.descr_indrizzo_rappresentante,
													'civico', anag.civico_rappresentante,
													'cap', anag.cap_rappresentante,
													'provincia', anag.provincia_rappresentante,
													'nazione', anag.nazione_rappresentante,
													'comune', anag.id_comune_rappresentante
												       )
									)

								   
		
		) soggetto_fisico,
		json_build_object('impresa', json_build_object( 'ragione_sociale', anag.ragione_sociale,
								'codice_fiscale', anag.codice_fiscale,
								'partita_iva', anag.partita_iva,
								'id_tipo_impresa_societa', anag.id_tipo_impresa_societa,
								'data_arrivo_pec', anag.data_arrivo_pec,
								'indirizzo', json_build_object( 'toponimo', null,
												'descr_indrizzo', anag.indirizzo_impresa, --anag.descr_indirizzo_impresa,
												'civico',  null, --anag.civico_impresa,
												'cap', anag.cap_impresa,
												'provincia', anag.cod_provincia_impresa,
												'nazione', anag.nazione_impresa,
												'comune', anag.id_comune_impresa
												)
								)
		) impresa,
		json_build_object('soggetto_fisico_stabilimento',json_build_object('titolo', null,
									'cognome', anag.cognome_rappresentante_stab,
									'nome', anag.nome_rappresentante_stab,
									'comune_nascita', anag.comune_nascita_rappresentante_stab,
									'codice_fiscale', null,
									'sesso', null,
									'telefono', null,
									'fax', null,
									'email', null,
									'telefono1', null,
									'data_nascita', anag.data_nascita_rappresentante_stab,
									'documento_identita', anag.documento_identita_rappresentante_stab,
									'provenienza_estera', null,
									'provincia_nascita', null,
									'indirizzo', json_build_object( 'toponimo', null,
													'descr_indrizzo', anag.indirizzo_rappresentante_stab,
													'civico', null,
													'cap', null,
													'provincia', anag.provincia_rappresentante_stab,
													'nazione', null,
													'comune', anag.id_comune_rappresentante_stab
												       )
									)

								   
		
		) soggetto_fisico_stabilimento,
		json_build_object('stabilimento',json_build_object('denominazione', anag.denominazione_stab,
								   'id_asl', anag.asl_rif,
								   'id_stato',null,
								   'categoria_rischio',anag.categoria_rischio,
								   'data_prossimo_controllo',anag.prossimo_controllo,
                                                                   'note',anag.note_stabilimento,
								   'data_inizio_attivita',anag.data_inizio_attivita_stab,
								   'data_fine_attivita',anag.data_fine_attivita_stab,
								   'indirizzo', json_build_object('toponimo', null, --anag.toponimo_stab,
												  'descr_indrizzo', anag.indirizzo_stab, --anag.descr_indrizzo_stab,
												  'civico', null, --anag.civico_stab,
												  'cap', anag.cap_stab,
												  'provincia', anag.cod_provincia_stab,
												  'nazione', anag.nazione_stab,
												  'comune', anag.id_comune_stab
												)
								  )
								  

		) stabilimento
	from view_anagrafica_attivita_strutture_riproduzione_animale anag 
limit 2


LOOP
	for recCampiEstesi in 
		select 
			json_build_object('tipo_struttura', estesi.tipo_struttura,
								   'codice_l30',estesi.codice_l30,
								   'razza',estesi.razza,
								   'provvedimenti_autorizzazione', estesi.provvedimenti_autorizzazione,
								   'centro_sede',estesi.centro_sede,
								   'scadenza_autorizzazione',estesi.scadenza_autorizzazione
		) json_strutture_riproduzione_animale
		from 
			strutture_riproduzione_animale_campi_estesi estesi 
		where 
			riferimento_id=recImpresa.riferimento_id 
	
LOOP
	FOR recLinea IN
		select 
			 json_build_object('linee_attivita',
					   json_agg(json_build_object('idmacroarea', attivita.idmacroarea, 
								      'idaggregazione' , attivita.idaggregazione, 
								      'idattivita', attivita.idattivita,
								      'attivita', attivita.attivita, 
								      'codiceunivoco', attivita.codiceunivoco, 
								      'numregistrazione', attivita.n_reg,
								      'cun',attivita.num_riconoscimento,
								      'id_stato',attivita.id_stato_linea,
								      'id_tipo_attivita',attivita.tipo_attivita,
								      'data_inizio_attivita',data_inizio_attivita,
								      'data_fine_attivita',data_fine_attivita))
			 ) linee_attivita
		from
		(
			select distinct
				null::integer idmacroarea,	
				null::integer idaggregazione,
				null::integer idattivita,
				attivita,
				(select codice_univoco_ml from mapping_org_riproduzione_codice where org_id = riferimento_id) as codiceunivoco,--inserire codice
				n_reg,
				num_riconoscimento,
				ls.code id_stato_linea,
				ls.description stato_linea,
				tipo_attivita,
				data_inizio_attivita,
				data_fine_attivita
			from 
				ricerche_anagrafiche_old_materializzata anamat
				left join lookup_stati ls on lower(trim(ls.description))=lower(trim(anamat.stato))
			where 
				riferimento_id=recImpresa.riferimento_id and
				riferimento_id_nome=recImpresa.riferimento_id_nome and
				riferimento_id_nome_col=recImpresa.riferimento_id_nome_col and
				riferimento_id_nome_tab=recImpresa.riferimento_id_nome_tab
				
		) attivita
		
	LOOP
		qry:= 'select ret_operazione,ret_id_impresa from public_functions.anagrafica_inserisci_osa_estesi(' || '''' || replace(CAST(recImpresa.soggetto_fisico as text),'''','''''') || '''' || ',' || 
									     '''' || replace(CAST(recImpresa.impresa as text),'''','''''') || '''' || ',' ||
									     '''' || replace(CAST(recImpresa.stabilimento as text),'''','''''') || '''' || ',' || 
									     '''' || replace(CAST(recImpresa.soggetto_fisico_stabilimento as text),'''','''''') || '''' || ',' || 
									     '''' || replace(CAST(recLinea.linee_attivita as text),'''','''''') || '''' || ',' || 
									     									     '''' || replace(CAST(recCampiEstesi.json_strutture_riproduzione_animale as text),'''','''''') || '''' || ',' ||

									     '''' || utente || '''' || ')';
		
				
		PERFORM dblink_connect('dblink_trans',strConnTarget);
		begin
			for recInserimento in
				select * from dblink('dblink_trans',qry) as t1(ret_operazione_osa_inserito integer, ret_id_osa integer)
			loop
				--MAPPATURA new Impresa con old impresa
				FOR recStabInseriti IN
					select * from dblink('dblink_trans','select id_stabilimento from rel_imprese_stabilimenti where id_impresa=' || recInserimento.ret_id_osa) AS t_stabInseriti(idStabilimentoInserito integer)
				LOOP
					PERFORM dblink_exec('dblink_trans', 'insert into anagrafica_mappatura_old_new ' ||
							   '(id_impresa_new,riferimento_id,riferimento_id_nome,riferimento_id_nome_col,riferimento_id_nome_tab,id_stabilimento_new,entered_old) ' ||
							   'values(' || 
							   '''' || recInserimento.ret_id_osa || '''' || ',' || 
							   '''' || recImpresa.riferimento_id || '''' || ',' || 
							   '''' || recImpresa.riferimento_id_nome || '''' || ',' || 
							   '''' || recImpresa.riferimento_id_nome_col || '''' || ',' || 
							   '''' || recImpresa.riferimento_id_nome_tab || '''' || ',' || 
							   '''' || recStabInseriti.idStabilimentoInserito || '''' || ',' ||
							   '''' || recImpresa.data_inserimento || '''' || ')');
					PERFORM dblink('dblink_trans','COMMIT;');
				END LOOP;
			
				----GESTIONE LOG SOGGETTO FISICO IMPRESA e STABILIMENTO
				--(0) inserimento effettuato senza duplicati
				--(1) valore di ritorno se non esiste l'impresa
				--(2) valore di ritorno se esiste già il soggetto fisico di impresa
				--(3) valore di ritorno se esiste già il soggetto fisico di stabilimento
	
	
				--Log duplicati Soggetti Fisici di impresa
				if(recInserimento.ret_operazione_osa_inserito=2) then
					sqlLog:= concat('INSERT INTO anagrafica_log_duplicati_soggetti_fisici',
						'(codice_fiscale,nome,cognome,data_nascita,comune_nascita,riferimento_id,riferimento_nome_tab,new_id_impresa) ',
						'VALUES(') ||
					 concat_ws(',',	coalesce('''' || recImpresa.codice_fiscale_rappresentante || '''','null'),
							coalesce('''' || replace(recImpresa.nome_rappresentante,'''','''''') || '''','null'),
							coalesce('''' || replace(recImpresa.cognome_rappresentante,'''','''''') || '''','null'),
							coalesce('''' || recImpresa.data_nascita_rappresentante || '''','null'),  
							coalesce('''' || replace(recImpresa.comune_nascita_rappresentante,'''','''''') || '''','null'),
							'''' || recImpresa.riferimento_id || '''',  
							'''' || recImpresa.riferimento_id_nome_tab || '''', 
							recInserimento.ret_id_osa ||
							')');
							
					PERFORM dblink_exec('dblink_trans', sqlLog);
					PERFORM dblink('dblink_trans','COMMIT;');
					
				end if;
			
				--Log duplicati Soggetti Fisici di stabilimento
				if(recInserimento.ret_operazione_osa_inserito=3) then
					-- da gestire in futuro
				end if;
			end loop;
			EXCEPTION when others then
					RAISE NOTICE 'Errore % ==> riferimento_id:%  riferimento_id_nome:%  riferimento_id_nome_col:%  riferimento_id_nome_tab:%  json:%', 
						      SQLERRM, 
						      recImpresa.riferimento_id,
						      recImpresa.riferimento_id_nome,
						      recImpresa.riferimento_id_nome_col,
						      recImpresa.riferimento_id_nome_tab,
						      recImpresa.soggetto_fisico || ',' || recImpresa.impresa || ',' || recImpresa.stabilimento || ',' || recLinea.linee_attivita;	
		end;
		PERFORM dblink_disconnect('dblink_trans'); 
		
	END LOOP;
END LOOP;
END LOOP;


return 'OK';


END;
$BODY$
  LANGUAGE plpgsql STABLE
  COST 100;
ALTER FUNCTION public_functions.anagrafica_infasamento_strutture_riproduzione_animale()
  OWNER TO postgres;
