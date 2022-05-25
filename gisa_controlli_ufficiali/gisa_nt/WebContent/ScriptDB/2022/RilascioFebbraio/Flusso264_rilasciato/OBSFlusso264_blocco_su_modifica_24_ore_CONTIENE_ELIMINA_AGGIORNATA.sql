-- Non so quando vanno lanciati, ma sono sicuramente gli ultimi ad oggi 14/10 12.10

-- Nuova funzione elimina ruolo guc che controlla se c'è già una cancellazione lo stesso giorno

-- Function: spid.elimina_ruolo_utente_guc(integer, integer, text)

-- DROP FUNCTION spid.elimina_ruolo_utente_guc(integer, integer, text);

CREATE OR REPLACE FUNCTION spid.elimina_ruolo_utente_guc(
    _id_utente integer,
    _id_ruolo integer,
    _endpoint text)
  RETURNS text AS
$BODY$

  DECLARE 
	contaScadutiStessoGiorno integer;
	conta integer;	
	esito text;
	descrizione_errore text;
  BEGIN
	descrizione_errore='';
	esito='';

	contaScadutiStessoGiorno := (  select count(*)  from guc_ruoli where id_utente = _id_utente and endpoint = _endpoint
and date(data_scadenza) =  date(now() - interval '1 DAY'));

RAISE INFO '[elimina_ruolo_utente_guc] contaScadutiStessoGiorno %', contaScadutiStessoGiorno;
 
	IF contaScadutiStessoGiorno > 0 THEN
		esito = 'KO';
		descrizione_errore = 'Per questo codice fiscale risulta già presente una richiesta di modifica o cancellazione nelle 24 ore precedenti. Impossibile proseguire.';

	ELSE
	
	conta := (select count(*) from guc_utenti_ u 
		  join guc_ruoli r on u.id = r.id_utente
		  where u.enabled and u.cessato <> true and u.data_scadenza is null and 
		  u.id = _id_utente
		  and r.endpoint ilike _endpoint and r.ruolo_integer = _id_ruolo);
	
	if(conta > 1) then
		esito = 'KO';
		descrizione_errore = 'Impossibile identificare l''utente per questo codice fiscale e ruolo';
	elsif (conta = 0) then
		esito = 'KO';
		descrizione_errore = 'Non esiste alcun utente con questo codice fiscale e ruolo.';
	else 
		raise info 'Trovato 1 utente in GUC..';
		update guc_ruoli set trashed=true, data_scadenza = (now() - interval '1 DAY'), note=concat_ws('***',note,'Richiesta di disattivazione account') where 
		id_utente = _id_utente and ruolo_integer = _id_ruolo and endpoint ilike _endpoint;
		
		esito = 'OK';
	end if;
	END IF;
	
	return '{"Esito" : "'||esito||'", "DescrizioneErrore" : "'||descrizione_errore ||'"}';

  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION spid.elimina_ruolo_utente_guc(integer, integer, text)
  OWNER TO postgres;

  
  -- Nuova dbi processa modifica che legge correttamente il ko
  
  -- Function: spid.processa_richiesta_modifica(text, integer)

-- DROP FUNCTION spid.processa_richiesta_modifica(text, integer);

CREATE OR REPLACE FUNCTION spid.processa_richiesta_modifica(
    _numero_richiesta text,
    _idutente integer)
  RETURNS text AS
$BODY$
declare

	esito integer;
	output_guc_gisa text;
	output_guc_gisa_ext text;
	output_guc_bdu text;
	output_guc_vam text;
	output_guc_digemon text;
	username_out text;
	password_out text;
	
	ep_gisa integer;
	ep_gisa_ext integer;
	ep_bdu integer;
	ep_vam integer;
	ep_digemon integer;

	check_endpoint text;

	output_gisa text;
	output_gisa_ext text;
	output_bdu text;
	output_vam text;
	output_digemon text;
	query text;
	
	_codice_fiscale text;
	esito_processa text;
	query_endpoint text;
	output_guc text;

	indice integer;

	_id_guc_ruoli integer;
	_endpoint_guc_ruoli text;
	_id_utente_guc_ruoli integer;
	_id_ruolo_guc_ruoli integer;

BEGIN
	-- recupero CF e id_ruoli per ogni EP
	_id_guc_ruoli := (select id_guc_ruoli from spid.get_lista_richieste(_numero_richiesta));
	_endpoint_guc_ruoli := (select endpoint_guc_ruoli from spid.get_lista_richieste(_numero_richiesta));
	_id_utente_guc_ruoli := (select id_utente from guc_ruoli where id = _id_guc_ruoli);
	_id_ruolo_guc_ruoli := (select ruolo_integer from guc_ruoli where id = _id_guc_ruoli);
	username_out := (select username from guc_utenti where id = _id_utente_guc_ruoli);
	password_out := (select password from guc_utenti where id = _id_utente_guc_ruoli);

	_codice_fiscale := (select codice_fiscale from spid.get_lista_richieste(_numero_richiesta));
	ep_gisa := (select coalesce(id_ruolo_gisa,-1) from spid.get_lista_richieste(_numero_richiesta));
	ep_gisa_ext := (select coalesce(id_ruolo_gisa_ext,-1) from spid.get_lista_richieste(_numero_richiesta));
	ep_bdu := (select coalesce(id_ruolo_bdu,-1) from spid.get_lista_richieste(_numero_richiesta));
	ep_vam := (select coalesce(id_ruolo_vam, -1) from spid.get_lista_richieste(_numero_richiesta));
	ep_digemon := (select coalesce(id_ruolo_digemon,-1) from spid.get_lista_richieste(_numero_richiesta));

	output_gisa='{}';
	output_gisa_ext='{}';
	output_bdu='{}';
	output_vam='{}';
	output_digemon='{}';
	output_guc ='{}';
	
	if (ep_gisa > 0 and _endpoint_guc_ruoli ilike 'gisa') then
		
		
			query  := 'select * from spid.elimina_ruolo_utente_guc('||_id_utente_guc_ruoli||','||_id_ruolo_guc_ruoli||',''Gisa'')';
			output_guc_gisa := (select * from spid.esegui_query(query , 'guc', _idutente,''));
			output_gisa := COALESCE(output_guc_gisa, '');

			if (get_json_valore(output_guc_gisa, 'Esito') = 'OK') then
				query := 'select * from spid.insert_ruolo_utente_guc('||_id_utente_guc_ruoli||','||ep_gisa||',''Gisa'')';			
				output_guc_gisa := (select * from spid.esegui_query(query , 'guc', _idutente,''));
				if (get_json_valore(output_guc_gisa, 'Esito') = 'OK') then
					-- chiamo la dbi elimina utente
					query_endpoint :=( select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'gisa', 'elimina', _idutente, username_out, '', (now()::timestamp without time zone - interval '1 DAY'), _id_ruolo_guc_ruoli, -1));
					output_gisa := (select * from spid.esegui_query(query_endpoint, 'gisa', _idutente,'host=dbGISAL dbname=gisa'));
					if (get_json_valore(output_gisa, 'Esito') = 'OK') then -- insert utente
						query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'gisa', 'insert', _idutente, username_out, password_out, null::timestamp without time zone, -1, -1));
						output_gisa := (select * from spid.esegui_query(query_endpoint, 'gisa', _idutente,'host=dbGISAL dbname=gisa'));
						output_gisa := '{"Esito" : "'||output_gisa ||'"}';
					end if;
					--else
			--output_gisa := output_guc_gisa;
				end if;
			end if;	
	
	end if; -- fine ep_gisa

	if (ep_gisa_ext > 0 and _endpoint_guc_ruoli ilike 'gisa_ext') then
		
			query  := 'select * from spid.elimina_ruolo_utente_guc('||_id_utente_guc_ruoli||','||_id_ruolo_guc_ruoli||',''Gisa_ext'')';
			output_guc_gisa_ext := (select * from spid.esegui_query(query , 'guc', _idutente,''));
			output_gisa_ext := COALESCE(output_guc_gisa_ext, '');

			if (get_json_valore(output_guc_gisa_ext, 'Esito') = 'OK') then
				query := 'select * from spid.insert_ruolo_utente_guc('||_id_utente_guc_ruoli||','||ep_gisa_ext||',''Gisa_ext'')';			
				output_guc_gisa_ext := (select * from spid.esegui_query(query , 'guc', _idutente,''));
				if (get_json_valore(output_guc_gisa_ext, 'Esito') = 'OK') then
					-- chiamo la dbi elimina utente
					query_endpoint :=( select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'gisa_ext', 'elimina', _idutente, username_out, '', (now()::timestamp without time zone - interval '1 DAY'), _id_ruolo_guc_ruoli, -1));
					output_gisa_ext := (select * from spid.esegui_query(query_endpoint, 'gisa_ext', _idutente,'host=dbGISAL dbname=gisa'));
					if (get_json_valore(output_gisa_ext, 'Esito') = 'OK') then -- insert utente
						query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'gisa_ext', 'insert', _idutente, username_out, password_out, null::timestamp without time zone, -1, -1));
						output_gisa_ext := (select * from spid.esegui_query(query_endpoint, 'gisa_ext', _idutente,'host=dbGISAL dbname=gisa'));
						output_gisa_ext := '{"Esito" : "'||output_gisa_ext||'"}';
					end if;
					--else
			--output_gisa_ext := output_guc_gisa_ext;
				end if;
			end if;			        
			
	end if; -- fine ep_gisa_ext

	if (ep_bdu > 0 and _endpoint_guc_ruoli ilike 'bdu') then
		
			query  := 'select * from spid.elimina_ruolo_utente_guc('||_id_utente_guc_ruoli||','||_id_ruolo_guc_ruoli||',''bdu'')';
			output_guc_bdu := (select * from spid.esegui_query(query , 'guc', _idutente,''));
			output_bdu := COALESCE(output_guc_bdu, '');
			if (get_json_valore(output_guc_bdu, 'Esito') = 'OK') then
				query := 'select * from spid.insert_ruolo_utente_guc('||_id_utente_guc_ruoli||','||ep_bdu||',''bdu'')';			
				output_guc_bdu := (select * from spid.esegui_query(query , 'guc', _idutente,''));
				if (get_json_valore(output_guc_bdu, 'Esito') = 'OK') then
					-- chiamo la dbi elimina utente
					query_endpoint :=( select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'bdu', 'elimina', _idutente, username_out, '', (now()::timestamp without time zone - interval '1 DAY'), _id_ruolo_guc_ruoli, -1));
					output_bdu := (select * from spid.esegui_query(query_endpoint, 'bdu', _idutente,'host=dbBDUL dbname=bdu'));
					if (get_json_valore(output_bdu, 'Esito') = 'OK') then -- insert utente
						query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'bdu', 'insert', _idutente, username_out, password_out, null::timestamp without time zone, -1, -1));
						output_bdu := (select * from spid.esegui_query(query_endpoint, 'bdu', _idutente,'host=dbBDUL dbname=bdu'));
						output_bdu := '{"Esito" : "'||output_bdu||'"}';
					end if;
					--else
			--output_bdu := output_guc_bdu;
				end if;
			end if;		
		
	end if; -- fine ep_bdu
				  
	if (ep_vam > 0 and _endpoint_guc_ruoli ilike 'vam') then
		
			-- chiamo guc e gestisco l'output
			query  := 'select * from spid.elimina_ruolo_utente_guc('||_id_utente_guc_ruoli||','||_id_ruolo_guc_ruoli||',''vam'')';
			output_guc_vam := (select * from spid.esegui_query(query , 'guc', _idutente,''));
			output_vam := COALESCE(output_guc_vam, '');
			if (get_json_valore(output_guc_vam, 'Esito') = 'OK') then
				query := 'select * from spid.insert_ruolo_utente_guc('||_id_utente_guc_ruoli||','||ep_vam||',''vam'')';			
				output_guc_vam := (select * from spid.esegui_query(query , 'guc', _idutente,''));
				if (get_json_valore(output_guc_vam, 'Esito') = 'OK') then
					-- chiamo la dbi elimina utente
					query_endpoint :=( select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'vam', 'elimina', _idutente, username_out, '', (now()::timestamp without time zone - interval '1 DAY'), _id_ruolo_guc_ruoli, -1));
					output_vam := (select * from spid.esegui_query(query_endpoint, 'vam', _idutente,'host=dbVAML dbname=vam'));
					if (get_json_valore(output_vam, 'Esito') = 'OK') then -- insert utente

					indice := 0;
		WHILE indice < (select array_length (id_clinica_vam, 1) from spid.get_lista_richieste(_numero_richiesta)) LOOP
		
						query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'vam', 'insert', _idutente, username_out, password_out, null::timestamp without time zone, -1, (indice+1)));
						output_vam := (select * from spid.esegui_query(query_endpoint, 'vam', _idutente,'host=dbVAML dbname=vam'));
						output_vam := '{"Esito" : "'||output_vam||'"}';
						indice = indice+1;
		END LOOP;
					end if;
					--else
			--output_vam := output_guc_vam;
				end if;
			end if;		
		
	end if; -- fine ep_vam	  

	if (ep_digemon > 0 and _endpoint_guc_ruoli ilike 'digemon') then
		
			query  := 'select * from spid.elimina_ruolo_utente_guc('||_id_utente_guc_ruoli||','||_id_ruolo_guc_ruoli||',''digemon'')';
			output_guc_digemon := (select * from spid.esegui_query(query , 'guc', _idutente,''));
			output_digemon := COALESCE(output_guc_digemon, '');

			if (get_json_valore(output_guc_digemon, 'Esito') = 'OK') then
				query := 'select * from spid.insert_ruolo_utente_guc('||_id_utente_guc_ruoli||','||ep_digemon||',''digemon'')';			
				output_guc_digemon := (select * from spid.esegui_query(query , 'guc', _idutente,''));
				if (get_json_valore(output_guc_digemon, 'Esito') = 'OK') then
					-- chiamo la dbi elimina utente
					query_endpoint :=( select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'digemon', 'elimina', _idutente, username_out, '', (now()::timestamp without time zone - interval '1 DAY'), _id_ruolo_guc_ruoli, -1));
					output_digemon := (select * from spid.esegui_query(query_endpoint, 'digemon', _idutente,'host=dbDIGEMONL dbname=digemon_u'));
					if (get_json_valore(output_digemon, 'Esito') = 'OK') then -- insert utente
						query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'digemon', 'insert', _idutente, username_out, password_out, null::timestamp without time zone, -1, -1));
						output_digemon := (select * from spid.esegui_query(query_endpoint, 'digemon', _idutente,'host=dbDIGEMONL dbname=digemon_u'));
						output_digemon := '{"Esito" : "'||output_digemon||'"}';
					end if;
					--else
			--output_digemon := output_guc_digemon;
				end if;
			end if;	
		
	end if; -- fine ep_digemon
	


	esito_processa = '{"EndPoint" : "GUC", "Username" : "' || username_out || '", "CodiceFiscale": "'||_codice_fiscale||'","ListaEndPoint" : [
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
		
	-- update e insert richieste
	UPDATE spid.spid_registrazioni_esiti set trashed_date = now() where numero_richiesta = _numero_richiesta and trashed_date is null;
	insert into spid.spid_registrazioni_esiti(numero_richiesta, esito_guc, esito_gisa, esito_gisa_ext, esito_bdu, esito_vam, esito_digemon,id_utente_esito, stato, json_esito) 
	values (_numero_richiesta,true,(case when get_json_valore(output_gisa, 'Esito')='OK' then true when get_json_valore(output_gisa, 'Esito')='KO' then false else null end),
					       (case when get_json_valore(output_gisa_ext, 'Esito')='OK' then true when get_json_valore(output_gisa_ext, 'Esito')='KO' then false else null end),
					       (case when get_json_valore(output_bdu, 'Esito')='OK' then true when get_json_valore(output_bdu, 'Esito')='KO' then false else null end),
					       (case when get_json_valore(output_vam, 'Esito')='OK' then true when get_json_valore(output_vam, 'Esito')='KO' then false else null end),
					       (case when get_json_valore(output_digemon, 'Esito')='OK' then true when get_json_valore(output_digemon, 'Esito')='KO' then false else null end),_idutente, (case when esito = 1 then 1 end),esito_processa);
		

	return esito_processa;
	
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION spid.processa_richiesta_modifica(text, integer)
  OWNER TO postgres;

  
  -- Nuova dbi processa elimina che legge correttamente il ko
  
  -- Function: spid.processa_richiesta_elimina(text, integer)

-- DROP FUNCTION spid.processa_richiesta_elimina(text, integer);

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

