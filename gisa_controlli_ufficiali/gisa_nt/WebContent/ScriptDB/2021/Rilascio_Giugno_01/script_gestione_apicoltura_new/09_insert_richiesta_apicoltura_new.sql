-- Function: public.insert_richiesta_apicoltura_new(hstore, hstore, integer, integer, text, integer)

-- DROP FUNCTION public.insert_richiesta_apicoltura_new(hstore, hstore, integer, integer, text, integer);

CREATE OR REPLACE FUNCTION public.insert_richiesta_apicoltura_new(
    campi_fissi hstore,
    campi_estesi hstore,
    utente_in integer,
    id_tipo_pratica_in integer,
    numero_pratica_in text,
    id_comune_in integer)
  RETURNS integer AS
$BODY$
DECLARE 

	_id_indirizzo_sogg_fisico integer;
	_id_sogg_fisico integer;
	_id_indirizzo_operatore integer;
	_id_operatore integer;
	_id_asl integer;
	_alt_id integer;
	_id_stabilimento integer;
	_id_linea integer;
	_id_pratica integer;
	_data_richiesta timestamp without time zone;
	_id_evento integer;
	r integer;
        
BEGIN	

	--inserimento indirizzo soggetto fisico
	_id_indirizzo_sogg_fisico := insert_opu_noscia_indirizzo((campi_fissi -> 'nazione_soggfis')::integer, campi_fissi -> 'cod_provincia_soggfis',
								 (campi_fissi -> 'cod_comune_soggfis')::integer, campi_fissi -> 'comune_residenza_estero_soggfis',
								 (campi_fissi -> 'toponimo_soggfis')::integer, campi_fissi -> 'via_soggfis', campi_fissi -> 'cap_soggfis', 
								 campi_fissi -> 'civico_soggfis', (campi_fissi -> 'latitudine_soggfis')::double precision, 
								 (campi_fissi -> 'longitudine_soggfis')::double precision, campi_fissi -> 'presso_soggfis');
	--inserimento soggetto fisico	
	_id_sogg_fisico := insert_ric_apicoltura_sogg_fis(campi_fissi -> 'nome_rapp_leg', campi_fissi -> 'cognome_rapp_leg', (campi_fissi -> 'nazione_nascita_rapp_leg')::integer,
							  campi_fissi -> 'codice_fiscale_rappresentante', campi_fissi -> 'sesso_rapp_leg', utente_in, 
							  campi_fissi -> 'telefono_rapp_leg', campi_fissi -> 'email_rapp_leg', _id_indirizzo_sogg_fisico,
							  campi_fissi -> 'comune_nascita_rapp_leg', (campi_fissi -> 'data_nascita_rapp_leg')::timestamp without time zone,
							  campi_fissi -> 'documento_identita');
	
	--inserimento indirizzo operatore
	_id_indirizzo_operatore := insert_opu_noscia_indirizzo((campi_fissi -> 'nazione_sede_legale')::integer, campi_fissi -> 'cod_provincia_sede_legale',
							       (campi_fissi -> 'cod_comune_sede_legale')::integer, campi_fissi -> 'comune_estero_sede_legale', 
							       (campi_fissi -> 'toponimo_sede_legale')::integer, campi_fissi -> 'via_sede_legale', 
							       campi_fissi -> 'cap_leg', campi_fissi -> 'civico_sede_legale', (campi_fissi -> 'latitudine_leg')::double precision, 
							       (campi_fissi -> 'longitudine_leg')::double precision, campi_fissi -> 'presso_sede_legale');
	--inserimento operatore
	_id_operatore := insert_ric_apicoltura_operatore(campi_fissi -> 'codice_fiscale', campi_fissi -> 'partita_iva', campi_fissi -> 'ragione_sociale',
							 utente_in, campi_fissi -> 'email_impresa', (campi_fissi -> 'tipo_impresa')::integer, 
							 _id_indirizzo_operatore);

	--inserimento relazione operatore soggetto fisico
	insert into suap_ric_scia_rel_operatore_soggetto_fisico(id_operatore, id_soggetto_fisico, tipo_soggetto_fisico, data_inizio, stato_ruolo, enabled)
	values(_id_operatore, _id_sogg_fisico, 1, now(), 1, true);
	
	--calcolo id asl per inserimento stabilimento
	select codiceistatasl::integer into _id_asl from comuni1 where id = (campi_fissi -> 'cod_comune_sede_legale')::integer;

	--inserimento stabilimento
	insert into suap_ric_scia_stabilimento(entered, modified, entered_by, id_operatore, modified_by, id_asl, id_indirizzo, stato, tipo_attivita, 
					       tipo_carattere, cessazione_stabilimento, stato_validazione, sospensione_stabilimento, tipo_reg_ric) 
	values(now(), now(), utente_in, _id_operatore, utente_in, _id_asl, -1, 7, 3, 1, false, 1, false, 2) returning id into _id_stabilimento;
		
	--calcolo alt_id in base alla partizione "public static final int ALT_OPU_RICHIESTE = 4"
	select return_alt_id into _alt_id from gestione_id_alternativo(_id_stabilimento, 4);
	update suap_ric_scia_stabilimento set alt_id = _alt_id where id = _id_stabilimento;
	
	--inserimento relazione linea stabilimento
	--inserimento relazione stabilimento linea di attivita
	FOR r IN SELECT distinct(split_part(key,'_',2)) FROM each(campi_fissi) where key ilike '%lineaattivita_%'
	    LOOP
		_id_linea := (campi_fissi -> concat('lineaattivita_', r,'_id_linea_attivita_ml'))::integer; 
		insert into suap_ric_scia_relazione_stabilimento_linee_produttive(id_linea_produttiva, id_stabilimento, stato, primario, enabled,
										  modified, modifiedby, tipo_attivita_produttiva)
		values(_id_linea, _id_stabilimento, 0, true, true, now(), utente_in, 0);
			
	    END LOOP;

	--recupero id pratica e data richiesta per settarlo in suap_ric_scia_stabilimento
	select id, coalesce(data_richiesta, entered) into _id_pratica, _data_richiesta 
		from pratiche_gins 
		where numero_pratica ilike numero_pratica_in 
			and id_comune_richiedente = id_comune_in 
			and id_causale = 1
			and apicoltura
			and trashed_date is null;

	--settiamo data_ricezione_richiesta in suap_ric_scia_stabilimento		
	update suap_ric_scia_stabilimento set data_ricezione_richiesta = _data_richiesta where id = _id_stabilimento;
	
	--refresh materializzata perform ric_insert_into_ricerche_anagrafiche_old_materializzata(altid integer)
	perform ric_insert_into_ricerche_anagrafiche_old_materializzata(_alt_id);
	--chiamata dbi 
	perform public_functions.suap_insert_attivita_apicoltura_da_richiesta(_id_operatore, utente_in);
	
	--gestione salvataggio evento_apicoltura pratica gins
	--inserisci dati evento
	insert into eventi_su_osa(id_stabilimento, alt_id, id_tipo_operazione, entered, enteredby)
	values(_id_stabilimento, _alt_id, id_tipo_pratica_in, now(), utente_in)  returning id into _id_evento;

	--inserisci dati relazione pratica evento
	insert into rel_eventi_pratiche(id_evento,id_pratica,entered,enteredby)
	values(_id_evento, _id_pratica, now(), utente_in);

	--_id_operatore sarebbe id_richiesta che appare nella lista delle vecchie pratiche suap
	return _id_stabilimento;
	
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.insert_richiesta_apicoltura_new(hstore, hstore, integer, integer, text, integer)
  OWNER TO postgres;
