-- Function: public.genera_numero_registrazione_da_comune(integer)

-- DROP FUNCTION public.genera_numero_registrazione_da_comune(integer);

CREATE OR REPLACE FUNCTION public.genera_numero_registrazione_da_comune(_id_comune integer)
  RETURNS text AS
$BODY$
DECLARE 

	_numero_registrazione text;
	_codComune text;
	_codProvincia text;

        
BEGIN	

	select comuni1.cod_comune, lp.cod_provincia  into _codComune, _codProvincia 
		from comuni1 join lookup_province lp on lp.code =  comuni1.cod_provincia::int 
			where comuni1.id = _id_comune;
	_numero_registrazione:= (select genera_numero_registrazione from anagrafica.genera_numero_registrazione(_codComune, _codProvincia));

	return _numero_registrazione;
	      
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.genera_numero_registrazione_da_comune(integer)
  OWNER TO postgres;

-- Function: public.insert_noscia(hstore, hstore, integer)

-- DROP FUNCTION public.insert_noscia(hstore, hstore, integer);

CREATE OR REPLACE FUNCTION public.insert_noscia(
    campi_fissi hstore,
    campi_estesi hstore,
    utente_in integer)
  RETURNS integer AS
$BODY$
DECLARE 

	_id_indirizzo_sede_legale integer;
	_id_indirizzo_stabilimento integer;
	_id_indirizzo_sogg_fis integer;
	_id_sogg_fis integer;
	_id_operatore integer;
	_id_stabilimento integer;
	_comune_nascita_testo text;
        
BEGIN	

--fare le assegnazioni alla variabili dichiarate sopra sia nel caso di dati recuperati che di nuovo inserimento

	IF (length(trim(campi_fissi ->'id_impresa_recuperata')) <> 0) THEN	
		_id_operatore := (campi_fissi -> 'id_impresa_recuperata')::integer;
		--update opu_operatore
		update opu_operatore 
			set codice_fiscale_impresa = campi_fissi -> 'codice_fiscale', partita_iva = campi_fissi -> 'partita_iva',
			    ragione_sociale = campi_fissi -> 'ragione_sociale', domicilio_digitale = campi_fissi -> 'email_impresa',
			    tipo_impresa = (campi_fissi -> 'tipo_impresa')::integer 
			where id = _id_operatore;
			
		--IF (length(trim(campi_fissi ->'via_sede_legale')) <> 0 OR length(trim(campi_fissi -> 'comune_estero_sede_legale')) <> 0) THEN
		IF (length(trim(campi_fissi ->'via_sede_legale')) <> 0 OR ((campi_fissi -> 'nazione_sede_legale')::integer <> 106)) THEN
			--inserisci indirizzo impresa
			_id_indirizzo_sede_legale := insert_opu_noscia_indirizzo((campi_fissi -> 'nazione_sede_legale')::integer, campi_fissi -> 'cod_provincia_sede_legale',
								       (campi_fissi -> 'cod_comune_sede_legale')::integer, campi_fissi -> 'comune_estero_sede_legale', 
								       (campi_fissi -> 'toponimo_sede_legale')::integer, campi_fissi -> 'via_sede_legale', campi_fissi -> 'cap_leg', campi_fissi -> 'civico_sede_legale',
								       (campi_fissi -> 'latitudine_leg')::double precision, (campi_fissi -> 'longitudine_leg')::double precision,
								       campi_fissi -> 'presso_sede_legale');
			--update id indirizzo impresa
			update opu_operatore set id_indirizzo = _id_indirizzo_sede_legale where id = _id_operatore;
			
		END IF;

		IF (length(trim(campi_fissi ->'id_rapp_legale_recuperato')) <> 0) THEN
			_id_sogg_fis := (campi_fissi -> 'id_rapp_legale_recuperato')::integer;
			--update opu_soggetto_fisico
			--gestione nel caso di provenienza estera
			IF (campi_fissi -> 'nazione_nascita_rapp_leg') = '106' THEN
				select nome into _comune_nascita_testo from comuni1 where id = (campi_fissi -> 'comune_nascita_rapp_leg')::integer limit 1;
				update opu_soggetto_fisico 
					set cognome = campi_fissi -> 'cognome_rapp_leg', nome = campi_fissi -> 'nome_rapp_leg',
					    comune_nascita = _comune_nascita_testo, codice_fiscale = campi_fissi -> 'codice_fiscale_rappresentante',
					    sesso = campi_fissi -> 'sesso_rapp_leg', telefono = campi_fissi -> 'telefono_rapp_leg',
					    email = campi_fissi -> 'email_rapp_leg', data_nascita = (campi_fissi -> 'data_nascita_rapp_leg')::timestamp without time zone, 
					    provenienza_estera = false, id_nazione_nascita = (campi_fissi -> 'nazione_nascita_rapp_leg')::integer, 
					    id_comune_nascita = (campi_fissi -> 'comune_nascita_rapp_leg')::integer
				        where id = _id_sogg_fis;
			ELSIF (length(trim(campi_fissi ->'nazione_nascita_rapp_leg')) <> 0) THEN
				update opu_soggetto_fisico 
					set cognome = campi_fissi -> 'cognome_rapp_leg', nome = campi_fissi -> 'nome_rapp_leg',
					    comune_nascita = campi_fissi -> 'comune_nascita_rapp_leg', codice_fiscale = campi_fissi -> 'codice_fiscale_rappresentante',
					    sesso = campi_fissi -> 'sesso_rapp_leg', telefono = campi_fissi -> 'telefono_rapp_leg',
					    email = campi_fissi -> 'email_rapp_leg', data_nascita = (campi_fissi -> 'data_nascita_rapp_leg')::timestamp without time zone, 
					    provenienza_estera = true, id_nazione_nascita = (campi_fissi -> 'nazione_nascita_rapp_leg')::integer
				        where id = _id_sogg_fis;
				
			END IF;
				    
			--IF (length(trim(campi_fissi ->'via_soggfis')) <> 0 OR length(trim(campi_fissi -> 'comune_residenza_estero_soggfis')) <> 0) THEN
			IF (length(trim(campi_fissi ->'via_soggfis')) <> 0 OR ((campi_fissi -> 'nazione_soggfis')::integer <> 106)) THEN
				--insert indirizzo soggetto fisico
				_id_indirizzo_sogg_fis := insert_opu_noscia_indirizzo((campi_fissi -> 'nazione_soggfis')::integer, campi_fissi -> 'cod_provincia_soggfis',
								       (campi_fissi -> 'cod_comune_soggfis')::integer, campi_fissi -> 'comune_residenza_estero_soggfis',
								       (campi_fissi -> 'toponimo_soggfis')::integer, campi_fissi -> 'via_soggfis', campi_fissi -> 'cap_soggfis', campi_fissi -> 'civico_soggfis',
								       (campi_fissi -> 'latitudine_soggfis')::double precision, (campi_fissi -> 'longitudine_soggfis')::double precision,
								       campi_fissi -> 'presso_soggfis');
				--update id indirizzo soggetto fisico
				update opu_soggetto_fisico set indirizzo_id = _id_indirizzo_sogg_fis where id = _id_sogg_fis;
			END IF;
		ELSE
			--inserisci indirizzo soggetto fisico
			--IF (length(trim(campi_fissi ->'via_soggfis')) <> 0 OR length(trim(campi_fissi -> 'comune_residenza_estero_soggfis')) <> 0) THEN
			IF (length(trim(campi_fissi ->'via_soggfis')) <> 0 OR ((campi_fissi -> 'nazione_soggfis')::integer <> 106)) THEN
				_id_indirizzo_sogg_fis := insert_opu_noscia_indirizzo((campi_fissi -> 'nazione_soggfis')::integer, campi_fissi -> 'cod_provincia_soggfis',
									       (campi_fissi -> 'cod_comune_soggfis')::integer, campi_fissi -> 'comune_residenza_estero_soggfis',
									       (campi_fissi -> 'toponimo_soggfis')::integer, campi_fissi -> 'via_soggfis', campi_fissi -> 'cap_soggfis', campi_fissi -> 'civico_soggfis',
									       (campi_fissi -> 'latitudine_soggfis')::double precision, (campi_fissi -> 'longitudine_soggfis')::double precision,
									       campi_fissi -> 'presso_soggfis');
		        END IF;
			--inserisci soggetto fisico
			IF (length(trim(campi_fissi ->'codice_fiscale_rappresentante')) <> 0) THEN
				_id_sogg_fis := insert_opu_noscia_soggetto_fisico(campi_fissi -> 'nome_rapp_leg', campi_fissi -> 'cognome_rapp_leg', (campi_fissi -> 'nazione_nascita_rapp_leg')::integer,
									  campi_fissi -> 'codice_fiscale_rappresentante', campi_fissi -> 'sesso_rapp_leg', utente_in, 
									  campi_fissi -> 'telefono_rapp_leg', campi_fissi -> 'email_rapp_leg', _id_indirizzo_sogg_fis,
									  campi_fissi -> 'comune_nascita_rapp_leg', (campi_fissi -> 'data_nascita_rapp_leg')::timestamp without time zone);
		        END IF;
			--inserisci relazione operatore-soggettofisico
			--inserimento relazione opu_rel_operatore_soggetto_fisico
			IF _id_sogg_fis is not null AND _id_operatore is not null THEN
				perform insert_opu_noscia_rel_impresa_soggfis(_id_sogg_fis, _id_operatore);
			END IF;
		END IF;
	ELSE
		--inserisciti impresa e soggetto fisico ex novo (indirizzi compresi)
		--inserimento indirizzo soggetto fisico/rapp legale
		--IF (length(trim(campi_fissi ->'via_soggfis')) <> 0 OR length(trim(campi_fissi -> 'comune_residenza_estero_soggfis')) <> 0) THEN
		IF (length(trim(campi_fissi ->'via_soggfis')) <> 0 OR ((campi_fissi -> 'nazione_soggfis')::integer <> 106)) THEN
			_id_indirizzo_sogg_fis := insert_opu_noscia_indirizzo((campi_fissi -> 'nazione_soggfis')::integer, campi_fissi -> 'cod_provincia_soggfis',
									       (campi_fissi -> 'cod_comune_soggfis')::integer, campi_fissi -> 'comune_residenza_estero_soggfis',
									       (campi_fissi -> 'toponimo_soggfis')::integer, campi_fissi -> 'via_soggfis', campi_fissi -> 'cap_soggfis', campi_fissi -> 'civico_soggfis',
									       (campi_fissi -> 'latitudine_soggfis')::double precision, (campi_fissi -> 'longitudine_soggfis')::double precision,
									       campi_fissi -> 'presso_soggfis');
		END IF;

		--inserimento indirizzo sede legale/impresa
		--IF (length(trim(campi_fissi ->'via_sede_legale')) <> 0 OR length(trim(campi_fissi -> 'comune_estero_sede_legale')) <> 0) THEN
		IF (length(trim(campi_fissi ->'via_sede_legale')) <> 0 OR ((campi_fissi -> 'nazione_sede_legale')::integer <> 106)) THEN
			_id_indirizzo_sede_legale := insert_opu_noscia_indirizzo((campi_fissi -> 'nazione_sede_legale')::integer, campi_fissi -> 'cod_provincia_sede_legale',
									       (campi_fissi -> 'cod_comune_sede_legale')::integer, campi_fissi -> 'comune_estero_sede_legale', 
									       (campi_fissi -> 'toponimo_sede_legale')::integer, campi_fissi -> 'via_sede_legale', campi_fissi -> 'cap_leg', campi_fissi -> 'civico_sede_legale',
									       (campi_fissi -> 'latitudine_leg')::double precision, (campi_fissi -> 'longitudine_leg')::double precision,
									       campi_fissi -> 'presso_sede_legale');
		END IF;

		--inserimento soggetto fisico/rapp legale
		IF (length(trim(campi_fissi ->'codice_fiscale_rappresentante')) <> 0) THEN
			_id_sogg_fis := insert_opu_noscia_soggetto_fisico(campi_fissi -> 'nome_rapp_leg', campi_fissi -> 'cognome_rapp_leg', (campi_fissi -> 'nazione_nascita_rapp_leg')::integer,
									  campi_fissi -> 'codice_fiscale_rappresentante', campi_fissi -> 'sesso_rapp_leg', utente_in, 
									  campi_fissi -> 'telefono_rapp_leg', campi_fissi -> 'email_rapp_leg', _id_indirizzo_sogg_fis,
									  campi_fissi -> 'comune_nascita_rapp_leg', (campi_fissi -> 'data_nascita_rapp_leg')::timestamp without time zone);
		END IF;

		--inserimento impresa/ operatore		
		_id_operatore := insert_opu_noscia_impresa(campi_fissi -> 'codice_fiscale', campi_fissi -> 'partita_iva', campi_fissi -> 'ragione_sociale',
								   utente_in, campi_fissi -> 'email_impresa', (campi_fissi -> 'tipo_impresa')::integer, _id_indirizzo_sede_legale);
		

		--inserimento relazione opu_rel_operatore_soggetto_fisico
		IF _id_sogg_fis is not null AND _id_operatore is not null THEN
			perform insert_opu_noscia_rel_impresa_soggfis(_id_sogg_fis, _id_operatore);
		END IF;
	END IF;

	--gestione stabilimento
	IF (length(trim(campi_fissi ->'id_stabilimento')) <> 0) THEN
		--prendo id_stabilimento recuperato
		_id_stabilimento := (campi_fissi ->'id_stabilimento')::integer;
		update opu_stabilimento set categoria_rischio = 3 where id = _id_stabilimento;
	ELSE
		--inserimento indirizzo stabilimento
		IF (length(trim(campi_fissi ->'via_stab')) <> 0) THEN
			_id_indirizzo_stabilimento := insert_opu_noscia_indirizzo(106, campi_fissi -> 'cod_provincia_stab',
									       (campi_fissi -> 'cod_comune_stab')::integer, null, (campi_fissi -> 'toponimo_stab')::integer,
									       campi_fissi -> 'via_stab', campi_fissi -> 'cap_stab', campi_fissi -> 'civico_stab',
									       (campi_fissi -> 'latitudine_stab')::double precision, (campi_fissi -> 'longitudine_stab')::double precision,
									       campi_fissi -> 'presso_stab');
		END IF;
		
		--inserisci stabilimento e recupero id stabilimento inserito
		IF _id_indirizzo_stabilimento is not null THEN
			_id_stabilimento := insert_opu_noscia_stabilimento(_id_operatore, _id_indirizzo_stabilimento, utente_in, campi_fissi -> 'lineaattivita_1_codice_univoco_ml', campi_fissi -> 'numero_registrazione_stabilimento');
		END IF;
	END IF;

	--inserimento relazione stabilimento linea di attivita
	IF (_id_stabilimento is not null AND length(trim(campi_fissi ->'lineaattivita_1_codice_univoco_ml')) <> 0) THEN
		perform insert_opu_noscia_rel_stabilimento_linea(_id_stabilimento, campi_fissi -> 'lineaattivita_1_codice_univoco_ml', campi_fissi -> 'lineaattivita_1_num_riconoscimento', 
									utente_in, (campi_fissi -> 'lineaattivita_1_data_inizio_attivita')::timestamp without time zone, 
									(campi_fissi -> 'lineaattivita_1_data_fine_attivita')::timestamp without time zone, 
									(campi_fissi -> 'lineaattivita_1_tipo_carattere_attivita')::integer);
	END IF;	

	perform refresh_anagrafica(_id_stabilimento, 'opu');

	raise WARNING 'id finali: id stab %, id operatore %, id sogg fis %s, id indi stab %, id ind impresa %s, id ind sogg fis %', 
			_id_stabilimento, _id_operatore, _id_sogg_fis, _id_indirizzo_stabilimento, _id_indirizzo_sede_legale, _id_indirizzo_sogg_fis;
	
	return _id_stabilimento;
	
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.insert_noscia(hstore, hstore, integer)
  OWNER TO postgres;

-- Function: public.insert_opu_noscia_stabilimento(integer, integer, integer, text, text)

-- DROP FUNCTION public.insert_opu_noscia_stabilimento(integer, integer, integer, text, text);

CREATE OR REPLACE FUNCTION public.insert_opu_noscia_stabilimento(
    _id_operatore integer,
    _id_indirizzo integer,
    _enteredby integer,
    _codice_linea text,
    _numero_registrazione text)
  RETURNS integer AS
$BODY$
DECLARE 
	id_stabilimento integer;
	_comune integer;
	_id_asl integer;
	_tipo_attivita integer;
        
BEGIN	
	_tipo_attivita := 1;
	select 
		CASE 
			WHEN fisso THEN 1::integer
			ELSE 2::integer 
		END AS tatt into _tipo_attivita
	FROM master_list_flag_linee_attivita where codice_univoco ilike trim(_codice_linea);

	select comune into _comune from opu_indirizzo where id = _id_indirizzo;

	select codiceistatasl::integer into _id_asl from comuni1 where id = _comune; 

	insert into opu_stabilimento(entered, entered_by, id_operatore, id_asl, id_indirizzo, stato, numero_registrazione, categoria_rischio, tipo_attivita)
	values(now(), _enteredby, _id_operatore, _id_asl, _id_indirizzo, 0, _numero_registrazione, 3, _tipo_attivita) returning id into id_stabilimento;
		
	return id_stabilimento;
	      
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.insert_opu_noscia_stabilimento(integer, integer, integer, text, text)
  OWNER TO postgres;

-- Function: public.recupera_stabilimenti_daimpresa_noscia(text)

-- DROP FUNCTION public.recupera_stabilimenti_daimpresa_noscia(text);

CREATE OR REPLACE FUNCTION public.recupera_stabilimenti_daimpresa_noscia(_id_impresa text)
  RETURNS SETOF json AS
$BODY$

BEGIN

 
return query
select concat('[', string_agg(row_to_json(tab)::text, ','), ']')::json as lista_stabilimenti from
(select distinct
	ostab.id::text as id_stabilimento,
	lp.description as provincia_stabilimento,
	lp.code::text as "provinciaIdStabilimento",
	c.nome::text as comune_stabilimento,
	c.id::text as "comuneIdStabilimento",
	lt.description::text as toponimo_stabilimento,
	lt.code::text as "topIdStabilimento",
	oi.via::text as via_stabilimento,
	oi.civico as civico_stabilimento,
	oi.cap::text as cap_stabilimento,
	oi.latitudine::text as lat_stabilimento,
	oi.longitudine::text as long_stabilimento,
	ostab.numero_registrazione as numero_registrazione_stabilimento,
	ls.description as asl_stabilimento 
from opu_stabilimento ostab join opu_indirizzo oi on ostab.id_indirizzo = oi.id 
			    join opu_relazione_stabilimento_linee_produttive opurel on opurel.id_stabilimento = ostab.id
			    left join comuni1 c on oi.comune = c.id
			    left join lookup_province lp on lp.code = c.cod_provincia::integer
			    left join lookup_toponimi lt on lt.code = oi.toponimo
			    left join lookup_site_id ls on trim(c.codiceistatasl) = ls.code::text
	where ostab.id_operatore::text = trim(_id_impresa)
		and ostab.trashed_date is null
		and ostab.stato = 0
		and lt.enabled
		and opurel.enabled
	) tab;

END;	
    
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.recupera_stabilimenti_daimpresa_noscia(text)
  OWNER TO postgres;

-- Function: public.insert_opu_noscia_impresa(text, text, text, integer, text, integer, integer)

-- DROP FUNCTION public.insert_opu_noscia_impresa(text, text, text, integer, text, integer, integer);

CREATE OR REPLACE FUNCTION public.insert_opu_noscia_impresa(
    _codice_fiscale_impresa text,
    _partita_iva text,
    _ragione_sociale text,
    _enteredby integer,
    _domicilio_digitale text,
    _tipo_impresa integer,
    _id_indirizzo integer)
  RETURNS integer AS
$BODY$
DECLARE 
	id_impresa integer;
        
BEGIN	


	insert into opu_operatore(codice_fiscale_impresa, partita_iva, ragione_sociale, enteredby, entered, domicilio_digitale, tipo_societa, id_indirizzo) 
	values (_codice_fiscale_impresa, _partita_iva, _ragione_sociale, _enteredby, now(), _domicilio_digitale, _tipo_impresa, _id_indirizzo) returning id into id_impresa;

	return id_impresa;
	      
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.insert_opu_noscia_impresa(text, text, text, integer, text, integer, integer)
  OWNER TO postgres;

-- Function: public.verifica_esistenza_impresa_noscia(text)

-- DROP FUNCTION public.verifica_esistenza_impresa_noscia(text);

CREATE OR REPLACE FUNCTION public.verifica_esistenza_impresa_noscia(_partita_iva_in text)
  RETURNS SETOF json AS
$BODY$

BEGIN

 
return query
select concat('[', string_agg(row_to_json(tab)::text, ','), ']')::json as lista_imprese from
(
select distinct
       oo.id::text as id_impresa_recuperata,
       trim(oo.ragione_sociale) as ragione_sociale_impresa,
       trim(oo.partita_iva) as partita_iva_impresa, 
       trim(oo.codice_fiscale_impresa) as codice_fiscale_impresa, 
       trim(oo.domicilio_digitale) as email_impresa,
       oo.tipo_societa::text as tipo_impresa,
       ln.code::text as nazione_sede_legale,
       trim(oi.provincia) as "provinciaIdSedeLegale",
       trim(lp.description) as provincia_sede_legale,
       oi.comune::text as "comuneIdSedeLegale",
       case when ln.code <> 106 then trim(oi.comune_testo) else null end as comune_estero_sede_legale,
       case when ln.code = 106 then trim(c.nome) else null end as comune_sede_legale,
       oi.toponimo::text as "topIdSedeLegale",
       trim(lt.description) as toponimo_sede_legale,
       trim(oi.via) as via_sede_legale,
       trim(oi.civico) as civico_sede_legale,
       trim(oi.cap) as cap_sede_legale,
       trim(oi.presso) as presso_sede_legale,
       oi.latitudine::text as latitudine_sede_legale,
       oi.longitudine::text as longitudine_sede_legale,
       os.id::text as id_rapp_legale_recuperato,
       trim(os.nome) as nome_rapp_legale, 
       trim(os.cognome) as cognome_rapp_legale, 
       trim(os.sesso) as sesso_rapp_legale, 
       case when os.provenienza_estera = false then 106::text else os.id_nazione_nascita::text end as nazione_nascita_rapp_legale, 
       c2_sogg.id::text as comune_nascita_rapp_legale ,  
       case when os.provenienza_estera = true then trim(os.comune_nascita) end as comune_nascita_estero_rapp_legale ,
       to_char(os.data_nascita, 'YYYY-MM-DD') as data_nascita_rapp_legale,
       trim(os.codice_fiscale) as cf_rapp_legale,
       trim(os.email) as email_rapp_legale,
       trim(os.telefono) as telefono_rapp_legale,
       ln_sogg.code::text as nazione_residenza_rapp_legale,
       oi_sogg.provincia::text as "provinciaIdResidenzaRappLegale",
       trim(lp_sogg.description) as provincia_residenza_rapp_legale,
       oi_sogg.comune::text as "comuneIdResidenzaRappLegale",
       case when os.provenienza_estera = true then trim(oi_sogg.comune_testo) else null end as comune_residenza_estero_rapp_legale,
       case when os.provenienza_estera = false then trim(c_sogg.nome) else null end as comune_residenza_rapp_legale,
       oi_sogg.toponimo::text as "topIdResidenzaRappLegale" ,
       trim(lt_sogg.description) as toponimo_residenza_rapp_legale ,
       trim(oi_sogg.via) as via_residenza_rapp_legale,
       trim(oi_sogg.civico) as civico_residenza_rapp_legale,
       trim(oi_sogg.cap) as cap_residenza_rapp_legale,
       trim(oi_sogg.presso) as presso_rapp_legale,
       oi_sogg.latitudine::text as latitudine_rapp_legale,
       oi_sogg.longitudine::text as longitudine_rapp_legale
	from opu_operatore oo 
        join opu_stabilimento stab on stab.id_operatore = oo.id and stab.trashed_date is null
        join opu_relazione_stabilimento_linee_produttive rel on rel.id_stabilimento = stab.id
	left join opu_indirizzo oi on oo.id_indirizzo = oi.id 
	left join comuni1 c on oi.comune = c.id
	left join lookup_province lp on lp.code = c.cod_provincia::integer
	left join lookup_toponimi lt on lt.code = oi.toponimo and lt.enabled
	left join lookup_nazioni ln on trim(ln.description) ilike trim(oi.nazione)
	left join opu_rel_operatore_soggetto_fisico opurel on opurel.id_operatore = oo.id
	left join opu_soggetto_fisico os on opurel.id_soggetto_fisico = os.id
	left join opu_indirizzo oi_sogg on os.indirizzo_id = oi_sogg.id
	left join comuni1 c_sogg on oi_sogg.comune = c_sogg.id
	left join lookup_province lp_sogg on lp_sogg.code = c_sogg.cod_provincia::integer
	left join lookup_toponimi lt_sogg on lt_sogg.code = oi_sogg.toponimo and lt_sogg.enabled
	left join lookup_nazioni ln_sogg on trim(ln_sogg.description) ilike trim(oi_sogg.nazione) 
	left join comuni1 c2_sogg on trim(os.comune_nascita) ilike (c2_sogg.nome)		       
	where trim(oo.partita_iva) ilike trim(_partita_iva_in)
	and opurel.enabled and rel.enabled
	and opurel.data_fine is null
	and oo.trashed_date is null
	and os.trashed_date is null
        and stab.trashed_date is null

		      ) tab;

END;	
    
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.verifica_esistenza_impresa_noscia(text)
  OWNER TO postgres;

--backup table
pg_dump -t configuratore_template_no_scia gisa -U postgres -h <host>  > configuratore_template_no_scia.sql
pg_dump -t gruppi_template_no_scia gisa -U postgres -h <host>  > gruppi_template_no_scia.sql
pg_dump -t schema_anagrafica gisa -U postgres -h <host>  > schema_anagrafica.sql

DROP TABLE public.configuratore_template_no_scia;
DROP TABLE public.gruppi_template_no_scia;
DROP TABLE public.schema_anagrafica;

--Restore table configuratore_template_no_scia gruppi_template_no_scia e schema_anagrafica
psql -U postgres -h <host> -d <nome_db> < configuratore_template_no_scia.sql
psql -U postgres -h <host> -d <nome_db> < gruppi_template_no_scia.sql
psql -U postgres -h <host> -d <nome_db> < schema_anagrafica.sql



