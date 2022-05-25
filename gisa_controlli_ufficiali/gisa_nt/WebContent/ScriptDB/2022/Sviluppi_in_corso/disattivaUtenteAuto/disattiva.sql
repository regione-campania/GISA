

CREATE OR REPLACE FUNCTION public.disattiva_utente(IN _id_utente integer)
  RETURNS text AS
$BODY$
 DECLARE 
	
	conta integer;	
	esito text;
	descrizione_errore text;
  BEGIN

	descrizione_errore ='';
	esito='';
	
	conta := (select count(*) from 
		  guc_utenti_ u 
		  join guc_ruoli r on u.id = r.id_utente
		  where u.enabled and u.cessato <> true and u.data_scadenza is null and 
		  u.id = _id_utente and (r.data_scadenza is null and (r.trashed is null or r.trashed =false))
		  and r.ruolo_integer > 0
		  );
	
	if(conta > 0) then
		raise info 'Utente attivo nel sistema.';
		esito = 'KO';
		descrizione_errore = 'Non si puo'' procedere alla disattivazione dell''utente.';
	else 
		raise info 'Utente da disattivare';
		update guc_utenti_ set note_internal_use_only_hd=concat_ws('***',note_internal_use_only_hd,'Disattivazione automatica utente tramite funzione public.disattiva_utente('||_id_utente||')'), data_scadenza= current_timestamp, enabled=false where id = _id_utente;
		
		esito = 'OK';
		descrizione_errore = '';
	end if;
	 			   
	return '{"Esito" : "'||esito||'", "DescrizioneErrore" : "'||descrizione_errore ||'"}';
	
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.disattiva_utente(integer)
  OWNER TO postgres;
  
  
  -- aggiornamento raggruppa
  
CREATE OR REPLACE FUNCTION public.raggruppa_utente_vam_bdu_asl(
    _id_guc_utente_1 integer,
    _id_ruolo_guc_bdu integer,
    _id_guc_utente_2 integer,
    _id_ruolo_guc_vam integer,
    username_rif text)
  RETURNS text AS
$BODY$

  DECLARE 
	esito text;
	descrizione_errore text;
	conta integer;
	username_out text;
	password_out text;
	id_guc_out text;
	output_guc text;
	output_bdu text;
	output_vam text;
	_query text;
	indice integer;
	_asl_id integer;
	msg text;
	ruolo_bdu text;
	ruolo_vam text;
	num_clinica integer;
	clinica integer[];
	id_cl integer;
	output text;
  BEGIN
	descrizione_errore='';
	esito='';
	output_bdu = '';
	output_vam = '';
	output_guc := '';
	output := '';

	--recupero asl
	_asl_id := (select asl_id from guc_utenti where username ilike username_rif);

	ruolo_bdu := (select ruolo_string from guc_ruoli where id_utente= _id_guc_utente_1 and ruolo_integer = _id_ruolo_guc_bdu and (trashed is null or trashed = false) and endpoint ilike 'bdu');
	ruolo_vam := (select ruolo_string from guc_ruoli where id_utente= _id_guc_utente_2 and ruolo_integer = _id_ruolo_guc_vam and (trashed is null or trashed = false) and endpoint ilike 'vam');

	--disabilito tutti e 2 gli utenti
	output_bdu := (select * from spid.elimina_ruolo_utente_guc(_id_guc_utente_1,_id_ruolo_guc_bdu,'bdu'));
	raise info 'output bdu %', output_bdu;

	output_vam := (select * from spid.elimina_ruolo_utente_guc(_id_guc_utente_2,_id_ruolo_guc_vam,'vam'));

	-- ne creo uno nuovo per guc, bdu e vam 
	select concat(
	concat('select * from dbi_insert_utente_guc(',
	'''', (select codice_fiscale from guc_utenti where username ilike username_rif), '''', ', ',
	'''', (select cognome from guc_utenti where username ilike username_rif), '''', ', ',
	'''', (select email from guc_utenti where username ilike username_rif), '''', ', ',
	'''', '1', '''', ', ',
	6567, ', ',
	'NULL::timestamp without time zone', ', ', 
	6567, ', ',
	'''', 'INSERIMENTO UTENTE PER RICHIESTA DI RAGGRUPPAMENTO UTENTE', '''', ', ',
	'''', (select nome from guc_utenti where username ilike username_rif), '''',  ', ',
	'''', null::character varying, '''', ', ', 
	'''', null::character varying, '''', ', ', 
	(_asl_id), ', ',
	'''', null::character varying, '''', ', ', 
	-1, ', ',
	'''', '', '''', ', '), 
	concat('''', (select coalesce(luogo,'') from guc_utenti where username ilike username_rif), '''', ', ',
	'''', (select coalesce(num_autorizzazione,'') from  guc_utenti where username ilike username_rif), '''', ', ', 
	-1, ', ',
	-1, ', ',
	'''', '', '''', ', ',
	'''', '', '''', ', ',
	'''', (select coalesce(id_provincia_iscrizione_albo_vet_privato,-1) from guc_utenti where username ilike username_rif), '''', ', ',
	'''', (select nr_iscrione_albo_vet_privato from guc_utenti where username ilike username_rif), '''', ', ',
	0, ', ',
	'''', '', '''', ', ',
	'''', '', '''', ', ',
	'''', '', '''', ', ',
	'''', '', '''', ', ',
	'''', '', '''', ', ',
	'''', '', '''', ', ',
	'''', '', '''', ', ',
	0, ', '), 
	concat('''', null::character varying, '''', ', ', 
	'''', (select telefono from guc_utenti where username ilike username_rif), '''', ', ', 
	-1, ', ',--id ruolo gisa
	'''', '', '''', ', ', --descrizione ruolo gisa
	-1, ', ', -- id ruolo gisa_ext
	'''', '' , '''', ', ',--descrizione ruolo gisa_ext
	(_id_ruolo_guc_bdu), ', ',
	'''', (ruolo_bdu), '''', ', ',
	(_id_ruolo_guc_vam), ', ',
	'''', (ruolo_vam), '''', ', ',
	-1, ', ',
	'''', '', '''', ', ',
	-1 , ', ', -- digemon
	'''', '', '''', ', ', -- ruolo digemon
	'''', '-1', '''', ', ',
	-1, ', ',
	'''', null::character varying, '''', ', ', 
	-1, ', ',
	-1, ', ',
	'''', '', '''', ', ',
	'''', null::character varying, '''', ', ', 
	-1, ', ',
	'''', '', '''', ', ',
	'''', '', '''', ', ',
	-1, ', ',
	'''', '', '''', ', ',
	'''', '', '''', '); ')) into _query;

	EXECUTE FORMAT(_query) INTO output_guc;
	output_guc := '{"Esito" : "'||output_guc ||'"}';
	raise info 'output guc: %', output_guc;
	
	-- se inserimento di GUC è andato a buon fine
	
	if(position('OK' in output_guc) > 0) then
		raise info 'entro qui???';
		username_out := (select split_part(output_guc,';;','3'));
		raise info ' username %', username_out;
		id_guc_out := (select split_part(output_guc,';;','2'));
		raise info ' id_guc_out %', id_guc_out;
	
		-- bdu
		select concat(
		'select * from dbi_insert_utente(', 
		'''', username_out, '''', ', ',
		'''', md5(username_out), '''', ', ',
		_id_ruolo_guc_bdu, ', ',
		6567, ', ',
		6567, ', ',
		'''', 'true', '''', ', ',
		_asl_id, ', ',
		'''', (select nome from guc_utenti  where username ilike username_rif), '''',  ', ',
		'''', (select cognome from guc_utenti  where username ilike username_rif), '''', ', ',
		'''', (select codice_fiscale from guc_utenti  where username ilike username_rif), '''', ', ',
		'''', 'INSERIMENTO UTENTE PER RICHIESTA DI RAGGRUPPAMENTO UTENTE', '''', ', ',
		'''', (select luogo from guc_utenti where username ilike username_rif), '''', ', ',
		'''', null::character varying, '''', ', ', 
		'''', (select email from guc_utenti where username ilike username_rif), '''', ', ',
		'NULL::timestamp with time zone', ', ', 
		-1, ', ',
		-1, ', ',	
		'''', (select coalesce(id_provincia_iscrizione_albo_vet_privato,-1) from guc_utenti where username ilike username_rif), '''', ', ',
		'''', (select nr_iscrione_albo_vet_privato from guc_utenti where username ilike username_rif), '''', ', ',
		'''', (select telefono from guc_utenti  where username ilike username_rif), '''', '); ') into _query;	

		--output_bdu := (select * from spid.esegui_query(_query, 'bdu', 6567,'dbname=bdu'));
		output_bdu  := (select t1.output FROM dblink('dbname=bdu'::text, _query) as t1(output text));

		raise info 'output bdu: %', output_bdu;
		output_bdu := '{"Esito" : "'||output_bdu ||'"}';

		-- recupero cliniche vam
		num_clinica :=(select count(*) from guc_cliniche_vam  where id_utente = _id_guc_utente_2);
                clinica:= (select array(select distinct id_clinica  from guc_cliniche_vam  where id_utente = _id_guc_utente_2));
		
		indice := 0;
		WHILE indice < num_clinica 
		LOOP
			id_cl = clinica[indice+1];
			raise info 'clinica %', id_cl;
			select concat(
			'select * from dbi_insert_utente(', 
			'''', username_out, '''', ', ',
			'''', md5(username_out), '''', ', ',
			(_id_ruolo_guc_vam), ', ',
			6567, ', ',
			6567, ', ',
			'''', '1', '''', ', ',
			_asl_id, ', ',
			'''', (select nome from guc_utenti where username ilike username_rif), '''',  ', ',
			'''', (select cognome from guc_utenti where username ilike username_rif), '''', ', ',
			'''', (select codice_fiscale from guc_utenti where username ilike username_rif), '''', ', ',
			'''', 'INSERIMENTO UTENTE PER RICHIESTA DI RAGGRUPPAMENTO UTENTE', '''', ', ',
			'''', (select coalesce(luogo,'') from guc_utenti where username ilike username_rif), '''', ', ',
			'''', null::character varying, '''', ', ', 
			'''', (select email from guc_utenti where username ilike username_rif), '''', ', ',
			'NULL::timestamp with time zone', ', ', 
			id_cl::int, ', ', -----------------------------------inserisco clinica poi ciclo dopo
			'''', '-1', '''', ', ',
			-1, ', ',
			'''', null::character varying, '''', ', ', 
			'''', (select telefono from guc_utenti where username ilike username_rif), '''', '); ') into _query;

			output_vam  := (select t1.output FROM dblink('dbname=vam'::text, _query) as t1(output text));
			output_vam := '{"Esito" : "'||output_vam ||'"}';

			indice = indice+1;
		END LOOP;	
		
		--vam
		if(get_json_valore(output_vam, 'Esito')='OK') then
		-- aggiungere le cliniche in GUC

			_query = (select concat('insert into guc_cliniche_vam(id_clinica, descrizione_clinica, id_utente) (select id_clinica, descrizione_clinica, '||id_guc_out||' from guc_cliniche_vam  where id_utente = '||_id_guc_utente_2||') returning ''OK'';'));
			raise info 'stampa query insert vam cliniche%', _query;
			_query := (select * from spid.esegui_query(_query, 'guc', 6567,''));
			raise info 'output query insert vam cliniche: %', _query;

		end if;

		msg := 'OK';
		-- richiamo disattiva utente su utente BDU e VAM
		output := (select * from public.disattiva_utente(_id_guc_utente_1));
		output := (select * from public.disattiva_utente(_id_guc_utente_2));

	else 
		msg := 'KO';
		descrizione_errore := 'Inserimento in GUC fallito.';
	end if;
	return '{"Esito" : "'||msg||'", "DescrizioneErrore" : "'||descrizione_errore||'","username" : "'||username_out||'"}';
	return msg;

  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.raggruppa_utente_vam_bdu_asl(integer, integer, integer, integer, text)
  OWNER TO postgres;

  -- processa ELIMINA
  
CREATE OR REPLACE FUNCTION spid.processa_richiesta_elimina(
    _numero_richiesta text,
    _id_utente integer)
  RETURNS text AS
$BODY$
declare
	esito integer;

	esito_processa text;

	query_endpoint text;
	query text;

	output_gisa text;
	output_gisa_ext text;
	output_bdu text;
	output_vam text;
	output_digemon text;
	output_guc text;
	_codice_fiscale text;
	output_guc_gisa_ext text;
	output_guc_bdu text;
	output_guc_vam text;
	output_guc_digemon text;
	output_guc_gisa text;

	output_disattiva text;
	_id_guc_ruoli integer;
	_endpoint_guc_ruoli text;
	_id_utente_guc_ruoli integer;
	_id_ruolo_guc_ruoli integer;

	username_out text;
BEGIN
	output_gisa = '{}';
	output_gisa_ext = '{}';
	output_bdu = '{}' ;
	output_vam = '{}';
	output_digemon = '{}';
	output_guc = '{}';
	-- elimino utente prima in guc e poi nell'EP.
	_id_guc_ruoli := (select id_guc_ruoli from spid.get_lista_richieste(_numero_richiesta));
	_endpoint_guc_ruoli := (select endpoint_guc_ruoli from spid.get_lista_richieste(_numero_richiesta));
	_id_utente_guc_ruoli := (select id_utente from guc_ruoli where id = _id_guc_ruoli);
	_id_ruolo_guc_ruoli := (select ruolo_integer from guc_ruoli where id = _id_guc_ruoli);
	username_out := (select username from guc_utenti where id = _id_utente_guc_ruoli);
	
	_codice_fiscale := (select codice_fiscale from spid.get_lista_richieste(_numero_richiesta));
	/*ep_gisa := (select coalesce(id_ruolo_gisa,-1) from spid.get_lista_richieste(_numero_richiesta));
	ep_gisa_ext := (select coalesce(id_ruolo_gisa_ext,-1) from spid.get_lista_richieste(_numero_richiesta));
	ep_bdu := (select coalesce(id_ruolo_bdu,-1) from spid.get_lista_richieste(_numero_richiesta));
	ep_vam := (select coalesce(id_ruolo_vam, -1) from spid.get_lista_richieste(_numero_richiesta));
	ep_digemon := (select coalesce(id_ruolo_digemon,-1) from spid.get_lista_richieste(_numero_richiesta));

	_ruolo_gisa := (select coalesce(ruolo_gisa,'') from spid.get_lista_richieste(_numero_richiesta));
	_ruolo_gisa_ext := (select coalesce(ruolo_gisa_ext,'')from spid.get_lista_richieste(_numero_richiesta));
	_ruolo_bdu := (select coalesce(ruolo_bdu,'') from spid.get_lista_richieste(_numero_richiesta));
	_ruolo_vam := (select coalesce(ruolo_vam,'') from spid.get_lista_richieste(_numero_richiesta));
	_ruolo_digemon :=(select coalesce(ruolo_digemon,'') from spid.get_lista_richieste(_numero_richiesta));
*/

	if (_endpoint_guc_ruoli ilike 'gisa') then
		
		-- chiamo guc e gestisco l'output
			query  := 'select * from spid.elimina_ruolo_utente_guc('||_id_utente_guc_ruoli||','||_id_ruolo_guc_ruoli||',''Gisa'')';
			output_guc := (select * from spid.esegui_query(query , 'guc', _id_utente,''));
			output_gisa := COALESCE(output_guc, '');

			if (get_json_valore(output_guc, 'Esito') = 'OK') then
				query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'gisa', 'elimina', _id_utente, username_out, '', now()::timestamp without time zone - interval '1 DAY',_id_ruolo_guc_ruoli, -1));
				output_gisa := (select * from spid.esegui_query(query_endpoint, 'gisa', _id_utente,'host=dbGISAL dbname=gisa'));
			end if;	
		  end if; -- fine ep_gisa

	if (_endpoint_guc_ruoli ilike 'gisa_ext') then
		
		-- chiamo guc e gestisco l'output
			query  := 'select * from spid.elimina_ruolo_utente_guc('||_id_utente_guc_ruoli||','||_id_ruolo_guc_ruoli||',''Gisa_ext'')';
			output_guc := (select * from spid.esegui_query(query , 'guc', _id_utente,''));
			output_gisa_ext := COALESCE(output_guc, '');

			if (get_json_valore(output_guc, 'Esito') = 'OK') then
				query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'gisa_ext', 'elimina', _id_utente, username_out, '', (now()::timestamp without time zone - interval '1 DAY'),_id_ruolo_guc_ruoli, -1));
				output_gisa_ext := (select * from spid.esegui_query(query_endpoint, 'gisa_ext', _id_utente,'host=dbGISAL dbname=gisa'));
			end if;	
		end if; -- fine ep_gisa_ext
        
	if (_endpoint_guc_ruoli ilike 'bdu') then
		
			query  := 'select * from spid.elimina_ruolo_utente_guc('||_id_utente_guc_ruoli||','||_id_ruolo_guc_ruoli||',''bdu'')';
			output_guc := (select * from spid.esegui_query(query , 'guc', _id_utente,''));
			output_bdu := COALESCE(output_guc, '');
			if (get_json_valore(output_guc, 'Esito') = 'OK') then
				query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'bdu', 'elimina', _id_utente, username_out, '', (now()::timestamp without time zone - interval '1 DAY'),_id_ruolo_guc_ruoli, -1));
				output_bdu := (select * from spid.esegui_query(query_endpoint, 'bdu', _id_utente,'host=dbBDUL dbname=bdu'));
				
			end if;	
		end if; -- fine ep_bdu
        
	if (_endpoint_guc_ruoli ilike 'vam') then
		
		-- chiamo guc e gestisco l'output
			query  := 'select * from spid.elimina_ruolo_utente_guc('||_id_utente_guc_ruoli||','||_id_ruolo_guc_ruoli||',''vam'')';
			output_guc := (select * from spid.esegui_query(query , 'guc', _id_utente,''));
			output_vam := COALESCE(output_guc, '');

			if (get_json_valore(output_guc, 'Esito') = 'OK') then
				query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'vam', 'elimina', _id_utente, username_out, '', (now()::timestamp without time zone - interval '1 DAY'),_id_ruolo_guc_ruoli, -1));
				output_vam := (select * from spid.esegui_query(query_endpoint, 'vam', _id_utente,'host=dbVAML dbname=vam'));
			end if;	
		  end if; -- fine ep_vam

	if (_endpoint_guc_ruoli ilike 'digemon') then
		
			query  := 'select * from spid.elimina_ruolo_utente_guc('||_id_utente_guc_ruoli||','||_id_ruolo_guc_ruoli||',''digemon'')';
			output_guc := (select * from spid.esegui_query(query , 'guc', _id_utente,''));
			output_digemon := COALESCE(output_guc, '');

			if (get_json_valore(output_guc, 'Esito') = 'OK') then
				query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'digemon', 'elimina', _id_utente, username_out, '', (now()::timestamp without time zone - interval '1 DAY'),_id_ruolo_guc_ruoli, -1));
				output_digemon := (select * from spid.esegui_query(query_endpoint, 'digemon', _id_utente,'host=dbdigemonL dbname=digemon_u'));
			end if;	
		 end if; -- fine ep_digemon
       
	esito_processa = '{"EndPoint" : "GUC", "Risultato": '||output_guc||', "CodiceFiscale": "'||_codice_fiscale||'","ListaEndPoint" : [
					{"EndPoint" : "GISA", "Risultato" : ['||output_gisa||']},
					{"EndPoint" : "GISA_EXT", "Risultato" : ['||output_gisa_ext||']},
					{"EndPoint" : "BDR", "Risultato" : ['||output_bdu||']},
					{"EndPoint" : "VAM", "Risultato" : ['||output_vam||']},
					{"EndPoint" : "DIGEMON", "Risultato" : ['||output_digemon||']}]}';	
				   
	raise info '[STAMPA ESITO PROCESSA] %', esito_processa;

	IF get_json_valore(output_gisa, 'Esito')='OK' THEN
		esito = 1;
	END IF;
	IF get_json_valore(output_gisa_ext, 'Esito')='OK' THEN
		esito = 1;
	END IF;
	IF get_json_valore(output_bdu, 'Esito')='OK' THEN
		esito = 1;
	END IF;
	IF get_json_valore(output_vam, 'Esito')='OK' THEN
		esito = 1;
	END IF;
	IF get_json_valore(output_digemon, 'Esito')='OK' THEN
		esito = 1;
	END IF;

	output_disattiva := (select * from public.disattiva_utente(_id_utente_guc_ruoli));

	UPDATE spid.spid_registrazioni_esiti set trashed_date = now() where numero_richiesta = _numero_richiesta and trashed_date is null;
	insert into spid.spid_registrazioni_esiti(numero_richiesta, esito_guc, esito_gisa, esito_gisa_ext, esito_bdu, esito_vam, esito_digemon,id_utente_esito, stato, json_esito) 
	values (_numero_richiesta,true,(case when get_json_valore(output_gisa, 'Esito')='OK' then true when get_json_valore(output_gisa, 'Esito')='KO' then false else null end),
			       (case when get_json_valore(output_gisa_ext, 'Esito')='OK' then true when get_json_valore(output_gisa_ext, 'Esito')='KO' then false else null end),
			       (case when get_json_valore(output_bdu, 'Esito')='OK' then true when get_json_valore(output_bdu, 'Esito')='KO' then false else null end),
			       (case when get_json_valore(output_vam, 'Esito')='OK' then true when get_json_valore(output_vam, 'Esito')='KO' then false else null end),
			       (case when get_json_valore(output_digemon, 'Esito')='OK' then true when get_json_valore(output_digemon, 'Esito')='KO' then false else null end),
					       _id_utente, (case when esito = 1 then 1 end)
						,esito_processa);					
		   
	return esito_processa;
	
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION spid.processa_richiesta_elimina(text, integer)
  OWNER TO postgres;

