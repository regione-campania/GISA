CREATE TABLE spid.spid_tipologia_utente_ruoli_endpoint(id serial primary key, endpoint text, id_ruolo integer, id_tipologia_utente integer, enabled boolean default true);

-- definizione gruppi per EP
-- gruppo ASL gisa
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',96,1, true);
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',44,1, true);
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',16,1, true);
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',221,1, true);
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',42,1, true);
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',21,1, true);
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',45,1, true);
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',97,1, true);
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',43,1, true);
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',19,1, true);
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',46,1, true);
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',98,1, true);
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',33,1, true);
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',56,1, true);
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',41,1, true);
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',59,1, true);
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',222,1, true);
-- gruppo ASL vam
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('vam',3,1, true);
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('vam',5,1, true);
-- gruppo ASL bdu
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('bdu',18,1, true);
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('bdu',29,1, true);
-- gruppo ASL digemon
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('digemon',99,1, true);

-- gruppo REGIONE GISA
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',40,10, true);
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',324,10, true);
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',27,10, true);
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',326,10, true);
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',327,10, true);
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',37,10, true);
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',47,10, true);
-- gruppo REGIONE BDU
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('bdu',32,10, true);
-- gruppo REGIONE DIGEMON
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('digemon',99,10, true);
-- gruppo CENTRO DI RIFERIMENTO GISA
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',3373,15, true);
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',3368,15, true);
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',3367,15, true);
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',3369,15, true);
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',3370,15, true);
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',3366,15, true);
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',3365,15, true);
-- gruppo IZSM
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',3340,12, true);
-- gruppo IZSM VAM
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',6,12, true);
-- gruppo IZSM digemon
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',99,12, true);
-- gruppo 16 ARPAC
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',117,16, true);
-- gruppo 17 OSSERVATORI
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',31,17, true);
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',53,17, true);
-- digemon
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('digemon',99,17, true);
-- gruppo 2 FORZE DELL ORDINE
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('bdu',26,2, true);
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('bdu',33,2, true);
-- gruppo 19 ESERCITO
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('bdu',26,19, true);
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('bdu',33,19, true);
-- gruppo 11 GUARDIE ZOOFILE PREFETTIZIE
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',329,11, true);
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('bdu',35,11, true);
-- gruppo 11 GUARDIE ZOOFILE PREFETTIZIE
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa',329,18, true);
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('bdu',35,18, true);
-- gruppo 3 GESTORI ACQUE
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa_ext',10000006,3, true);
-- gruppo 4 APICOLTORE AUTOCONSUMO
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa_ext',10000002,4, true);
-- gruppo 5 APICOLTORE COMMERCIALE
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa_ext',10000002,5, true);
-- gruppo 6 APICOLTORE DELEGATO
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa_ext',10000001,6, true);
-- gruppo 7 GESTORE TRASPORTI
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa_ext',10000004,7, true);
-- gruppo 20 GESTORE DISTRIBUTORI
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa_ext',10000004,20, true);
-- gruppo 9 VETERINARIO LP
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('bdu',24,9, true);
-- gruppo 13 OPERATORE AUTOVALUTAZIONE
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('gisa_ext',10000008,13, true);
-- gruppo 14 DIRETTORE CANILE
insert into spid.spid_tipologia_utente_ruoli_endpoint(endpoint, id_ruolo, id_tipologia_utente, enabled) values ('bdu',18,14, true);

-- Function: spid.check_validita_gruppo_ruolo_cf(text, integer, text)

-- DROP FUNCTION spid.check_validita_gruppo_ruolo_cf(text, integer, text);

-- Function: spid.check_validita_gruppo_ruolo_cf(text, integer, text)

-- DROP FUNCTION spid.check_validita_gruppo_ruolo_cf(text, integer, text);

CREATE OR REPLACE FUNCTION spid.check_validita_gruppo_ruolo_cf(
    _codice_fiscale text,
    _id_ruolo integer,
    _endpoint text)
  RETURNS text AS
$BODY$
 DECLARE 
	_idutente_presente integer;
	_idruolo_presente integer;
	_username_presente text;
	_password_presente text;
	conta integer;	
	out text;
	esito text;
	descrizione_errore text;
  BEGIN

	descrizione_errore ='';
	esito='';
	_idutente_presente = -1;
	_idruolo_presente = -1;
	_username_presente = '';
	_password_presente = '';
	
	conta := (select count(*) from guc_utenti_ u 
		  join guc_ruoli r on u.id = r.id_utente
join spid.spid_tipologia_utente_ruoli_endpoint sture on sture.id_ruolo = r.ruolo_integer and sture.endpoint ilike r.endpoint
join spid.spid_tipologia_utente_ruoli_endpoint sture2 on sture.id_tipologia_utente = sture2.id_tipologia_utente and sture.endpoint ilike sture2.endpoint
		  where u.enabled and u.cessato <> true and u.data_scadenza is null and 
		  u.codice_fiscale = _codice_fiscale and r.data_scadenza is null
		  and r.endpoint ilike _endpoint and sture2.id_ruolo = _id_ruolo);
	
	if(conta > 1) then
		raise info '[check_validita_gruppo_ruolo_cf] ERRORE. TROVATI PIU'' UTENTI PER IL GRUPPO DI QUESTA COPPIA RUOLO/ENDPOINT';
		esito = 'KO';
		descrizione_errore = 'Impossibile identificare univocamente utente soggetto a modifica. Risultano esistenti diversi utenti per la coppia codice fiscale/ruolo.';
	elsif (conta = 0) then
		raise info '[check_validita_gruppo_ruolo_cf] ERRORE. NON TROVATO NESSUN UTENTE PER IL GRUPPO DI QUESTA COPPIA RUOLO/ENDPOINT';
		esito = 'KO';
		descrizione_errore = 'Impossibile identificare utente soggetto a modifica. Non risulta esistente nessun utente per coppia codice fiscale/ruolo.';
	else 
		raise info '[check_validita_gruppo_ruolo_cf] Trovato utente!';
		select u.id, u.username, u.password, r.ruolo_integer  into _idutente_presente, _username_presente, _password_presente, _idruolo_presente from guc_utenti_ u 
		  join guc_ruoli r on u.id = r.id_utente
join spid.spid_tipologia_utente_ruoli_endpoint sture on sture.id_ruolo = r.ruolo_integer and sture.endpoint ilike r.endpoint
join spid.spid_tipologia_utente_ruoli_endpoint sture2 on sture.id_tipologia_utente = sture2.id_tipologia_utente and sture.endpoint ilike sture2.endpoint
		  where u.enabled and u.cessato <> true and u.data_scadenza is null and 
		  u.codice_fiscale = _codice_fiscale and r.data_scadenza is null
		  and r.endpoint ilike _endpoint and sture2.id_ruolo = _id_ruolo;

		raise info 'id utente GUC: %', _idutente_presente;
		
		esito = 'OK';
	end if;
	 
				   
	return '{"Esito" : "'||esito||'", "DescrizioneErrore" : "'||descrizione_errore ||'", "IdRuolo": "'||_idruolo_presente||'", "IdUtente": "'||_idutente_presente||'", "Username": "'||_username_presente||'" , "Password": "'||_password_presente||'"}';

	
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION spid.check_validita_gruppo_ruolo_cf(text, integer, text)
  OWNER TO postgres;
--select * from spid.processa_richiesta('2021-RIC-00000142', 12)

  -- Function: spid.processa_richiesta_modifica(text, integer)

-- DROP FUNCTION spid.processa_richiesta_modifica(text, integer);
-- Function: spid.processa_richiesta_modifica(text, integer)

-- DROP FUNCTION spid.processa_richiesta_modifica(text, integer);

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
BEGIN
	-- recupero CF e id_ruoli per ogni EP
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
	
	if (ep_gisa > 0) then
		-- richiamo su Gisa
		query := 'select * from spid.check_validita_gruppo_ruolo_cf('''||_codice_fiscale||''', '||ep_gisa||', ''gisa'');';
		check_endpoint := (select * from spid.esegui_query(query, 'guc', _idutente,''));
		output_gisa := check_endpoint;
		if get_json_valore(check_endpoint, 'Esito') = 'OK' then -- significa che è ok
			-- chiamo guc e gestisco l'output
			username_out := get_json_valore(check_endpoint, 'Username');
			password_out := get_json_valore(check_endpoint, 'Password');
			query  := 'select * from spid.elimina_ruolo_utente_guc('||get_json_valore(check_endpoint, 'IdUtente')||','||get_json_valore(check_endpoint, 'IdRuolo')||',''Gisa'')';
			output_guc_gisa := (select * from spid.esegui_query(query , 'guc', _idutente,''));
			if (get_json_valore(output_guc_gisa, 'Esito') = 'OK') then
				query := 'select * from spid.insert_ruolo_utente_guc('||get_json_valore(check_endpoint, 'IdUtente')||','||ep_gisa||',''Gisa'')';			
				output_guc_gisa := (select * from spid.esegui_query(query , 'guc', _idutente,''));
				if (get_json_valore(output_guc_gisa, 'Esito') = 'OK') then
					-- chiamo la dbi elimina utente
					query_endpoint :=( select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'gisa', 'elimina', _idutente, '', '', (now()::timestamp without time zone - interval '1 DAY'), get_json_valore(check_endpoint, 'IdRuolo')::integer));
					output_gisa := (select * from spid.esegui_query(query_endpoint, 'gisa', _idutente,'host=dbGISAL dbname=gisa'));
					if (get_json_valore(output_gisa, 'Esito') = 'OK') then -- insert utente
						query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'gisa', 'insert', _idutente, username_out, password_out, null::timestamp without time zone, -1));
						output_gisa := (select * from spid.esegui_query(query_endpoint, 'gisa', _idutente,'host=dbGISAL dbname=gisa'));
						output_gisa := '{"Esito" : "'||output_gisa ||'"}';
					end if;
				end if;
			end if;	
				esito =1;	
		end if;
		
	end if; -- fine ep_gisa

	if (ep_gisa_ext > 0) then
		query := 'select * from spid.check_validita_gruppo_ruolo_cf('''||_codice_fiscale||''', '||ep_gisa_ext||', ''gisa_ext'');';
		check_endpoint := (select * from spid.esegui_query(query, 'guc', _idutente,''));
		output_gisa_ext := check_endpoint;
		if get_json_valore(check_endpoint, 'Esito') = 'OK' then -- significa che è ok
			-- chiamo guc e gestisco l'output
			username_out := get_json_valore(check_endpoint, 'Username');
			password_out := get_json_valore(check_endpoint, 'Password');
			query  := 'select * from spid.elimina_ruolo_utente_guc('||get_json_valore(check_endpoint, 'IdUtente')||','||get_json_valore(check_endpoint, 'IdRuolo')||',''Gisa_ext'')';
			output_guc_gisa_ext := (select * from spid.esegui_query(query , 'guc', _idutente,''));
			if (get_json_valore(output_guc_gisa_ext, 'Esito') = 'OK') then
				query := 'select * from spid.insert_ruolo_utente_guc('||get_json_valore(check_endpoint, 'IdUtente')||','||ep_gisa_ext||',''Gisa_ext'')';			
				output_guc_gisa_ext := (select * from spid.esegui_query(query , 'guc', _idutente,''));
				if (get_json_valore(output_guc_gisa_ext, 'Esito') = 'OK') then
					-- chiamo la dbi elimina utente
					query_endpoint :=( select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'gisa_ext', 'elimina', _idutente, '', '', (now()::timestamp without time zone - interval '1 DAY'), get_json_valore(check_endpoint, 'IdRuolo')::integer));
					output_gisa_ext := (select * from spid.esegui_query(query_endpoint, 'gisa_ext', _idutente,'host=dbGISAL dbname=gisa'));
					if (get_json_valore(output_gisa_ext, 'Esito') = 'OK') then -- insert utente
						query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'gisa_ext', 'insert', _idutente, username_out, password_out, null::timestamp without time zone, -1));
						output_gisa_ext := (select * from spid.esegui_query(query_endpoint, 'gisa_ext', _idutente,'host=dbGISAL dbname=gisa'));
						output_gisa_ext := '{"Esito" : "'||output_gisa_ext||'"}';
					end if;
				end if;
			end if;			        
				esito =1;
		end if;
			
	end if; -- fine ep_gisa_ext

	if (ep_bdu > 0) then
		query := 'select * from spid.check_validita_gruppo_ruolo_cf('''||_codice_fiscale||''', '||ep_bdu||', ''bdu'');';
		check_endpoint := (select * from spid.esegui_query(query, 'guc', _idutente,''));
		output_bdu := check_endpoint;
		if get_json_valore(check_endpoint, 'Esito') = 'OK' then -- significa che è ok
			-- chiamo guc e gestisco l'output
			username_out := get_json_valore(check_endpoint, 'Username');
			password_out := get_json_valore(check_endpoint, 'Password');
			query  := 'select * from spid.elimina_ruolo_utente_guc('||get_json_valore(check_endpoint, 'IdUtente')||','||get_json_valore(check_endpoint, 'IdRuolo')||',''bdu'')';
			output_guc_bdu := (select * from spid.esegui_query(query , 'guc', _idutente,''));
			if (get_json_valore(output_guc_bdu, 'Esito') = 'OK') then
				query := 'select * from spid.insert_ruolo_utente_guc('||get_json_valore(check_endpoint, 'IdUtente')||','||ep_bdu||',''bdu'')';			
				output_guc_bdu := (select * from spid.esegui_query(query , 'guc', _idutente,''));
				if (get_json_valore(output_guc_bdu, 'Esito') = 'OK') then
					-- chiamo la dbi elimina utente
					query_endpoint :=( select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'bdu', 'elimina', _idutente, '', '', (now()::timestamp without time zone - interval '1 DAY'), get_json_valore(check_endpoint, 'IdRuolo')::integer));
					output_bdu := (select * from spid.esegui_query(query_endpoint, 'bdu', _idutente,'host=dbBDUL dbname=bdu'));
					if (get_json_valore(output_bdu, 'Esito') = 'OK') then -- insert utente
						query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'bdu', 'insert', _idutente, username_out, password_out, null::timestamp without time zone, -1));
						output_bdu := (select * from spid.esegui_query(query_endpoint, 'bdu', _idutente,'host=dbBDUL dbname=bdu'));
						output_bdu := '{"Esito" : "'||output_bdu||'"}';
					end if;
				end if;
			end if;		
				esito =1;	        
		end if;
	end if; -- fine ep_bdu
				  
	if (ep_vam > 0) then
		query := 'select * from spid.check_validita_gruppo_ruolo_cf('''||_codice_fiscale||''', '||ep_vam||', ''vam'');';
		check_endpoint := (select * from spid.esegui_query(query, 'guc', _idutente,''));
		output_vam := check_endpoint;
		if get_json_valore(check_endpoint, 'Esito') = 'OK' then -- significa che è ok
			username_out := get_json_valore(check_endpoint, 'Username');
			password_out := get_json_valore(check_endpoint, 'Password');
			-- chiamo guc e gestisco l'output
			query  := 'select * from spid.elimina_ruolo_utente_guc('||get_json_valore(check_endpoint, 'IdUtente')||','||get_json_valore(check_endpoint, 'IdRuolo')||',''vam'')';
			output_guc_vam := (select * from spid.esegui_query(query , 'guc', _idutente,''));
			if (get_json_valore(output_guc_vam, 'Esito') = 'OK') then
				query := 'select * from spid.insert_ruolo_utente_guc('||get_json_valore(check_endpoint, 'IdUtente')||','||ep_vam||',''vam'')';			
				output_guc_vam := (select * from spid.esegui_query(query , 'guc', _idutente,''));
				if (get_json_valore(output_guc_vam, 'Esito') = 'OK') then
					-- chiamo la dbi elimina utente
					query_endpoint :=( select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'vam', 'elimina', _idutente, '', '', (now()::timestamp without time zone - interval '1 DAY'), get_json_valore(check_endpoint, 'IdRuolo')::integer));
					output_vam := (select * from spid.esegui_query(query_endpoint, 'vam', _idutente,'host=dbVAML dbname=vam'));
					if (get_json_valore(output_vam, 'Esito') = 'OK') then -- insert utente
						query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'vam', 'insert', _idutente, username_out, password_out, null::timestamp without time zone, -1));
						output_vam := (select * from spid.esegui_query(query_endpoint, 'vam', _idutente,'host=dbVAML dbname=vam'));
						output_vam := '{"Esito" : "'||output_vam||'"}';
					end if;
				end if;
			end if;		
				esito =1;
		end if;
	end if; -- fine ep_vam	  

	if (ep_digemon > 0) then
		query := 'select * from spid.check_validita_gruppo_ruolo_cf('''||_codice_fiscale||''', '||ep_digemon||', ''digemon'');';
		check_endpoint := (select * from spid.esegui_query(query, 'guc', _idutente,''));
		output_digemon := check_endpoint;
		if get_json_valore(check_endpoint, 'Esito') = 'OK' then -- significa che è ok
			-- chiamo guc e gestisco l'output
			username_out := get_json_valore(check_endpoint, 'Username');
			password_out := get_json_valore(check_endpoint, 'Password');
			query  := 'select * from spid.elimina_ruolo_utente_guc('||get_json_valore(check_endpoint, 'IdUtente')||','||get_json_valore(check_endpoint, 'IdRuolo')||',''digemon'')';
			output_guc_digemon := (select * from spid.esegui_query(query , 'guc', _idutente,''));
			if (get_json_valore(output_guc_digemon, 'Esito') = 'OK') then
				query := 'select * from spid.insert_ruolo_utente_guc('||get_json_valore(check_endpoint, 'IdUtente')||','||ep_digemon||',''digemon'')';			
				output_guc_digemon := (select * from spid.esegui_query(query , 'guc', _idutente,''));
				if (get_json_valore(output_guc_digemon, 'Esito') = 'OK') then
					-- chiamo la dbi elimina utente
					query_endpoint :=( select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'digemon', 'elimina', _idutente, '', '', (now()::timestamp without time zone - interval '1 DAY'), get_json_valore(check_endpoint, 'IdRuolo')::integer));
					output_digemon := (select * from spid.esegui_query(query_endpoint, 'digemon', _idutente,'host=dbDIGEMONL dbname=digemon_u'));
					if (get_json_valore(output_digemon, 'Esito') = 'OK') then -- insert utente
						query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'digemon', 'insert', _idutente, username_out, password_out, null::timestamp without time zone, -1));
						output_digemon := (select * from spid.esegui_query(query_endpoint, 'digemon', _idutente,'host=dbDIGEMONL dbname=digemon_u'));
						output_digemon := '{"Esito" : "'||output_digemon||'"}';
					end if;
				end if;
			end if;	
				esito =1;
		end if;
	end if; -- fine ep_digemon
	


	esito_processa = '{"EndPoint" : "GUC", "CodiceFiscale": "'||_codice_fiscale||'","ListaEndPoint" : [
					{"EndPoint" : "GISA", "Risultato" : ['||output_gisa||']},
					{"EndPoint" : "GISA_EXT", "Risultato" : ['||output_gisa_ext||']},
					{"EndPoint" : "BDR", "Risultato" : ['||output_bdu||']},
					{"EndPoint" : "VAM", "Risultato" : ['||output_vam||']},
					{"EndPoint" : "DIGEMON", "Risultato" : ['||output_digemon||']}]}';
	raise info '[STAMPA ESITO PROCESSA] %', esito_processa;

		
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

