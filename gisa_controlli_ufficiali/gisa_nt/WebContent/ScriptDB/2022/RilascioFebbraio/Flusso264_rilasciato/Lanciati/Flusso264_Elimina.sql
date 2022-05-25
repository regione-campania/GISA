
-- db guc
alter table guc_ruoli  add column data_scadenza timestamp without time zone;
-- Function: spid.processa_richiesta_elimina(text, integer)

-- DROP FUNCTION spid.processa_richiesta_elimina(text, integer);

-- Function: spid.processa_richiesta_elimina(text, integer)

-- DROP FUNCTION spid.processa_richiesta_elimina(text, integer);
-- Function: spid.processa_richiesta_elimina(text, integer)

-- DROP FUNCTION spid.processa_richiesta_elimina(text, integer);
-- Function: spid.processa_richiesta_elimina(text, integer)

-- DROP FUNCTION spid.processa_richiesta_elimina(text, integer);

CREATE OR REPLACE FUNCTION spid.processa_richiesta_elimina(
    _numero_richiesta text,
    _id_utente integer)
  RETURNS text AS
$BODY$
declare
	ep_gisa integer;
	ep_gisa_ext integer;
	ep_bdu integer;
	ep_vam integer;
	ep_digemon integer;

	_ruolo_gisa text;
	_ruolo_gisa_ext text;
	_ruolo_bdu text;
	_ruolo_vam text;
	_ruolo_digemon text;
	esito_processa text;

	esito_check text;
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
	
BEGIN
	output_gisa = '';
	output_gisa_ext = '';
	output_bdu = '' ;
	output_vam = '';
	output_digemon = '';
	output_guc = '[]';
	-- elimino utente prima in guc e poi nell'EP.
	_codice_fiscale := (select codice_fiscale from spid.get_lista_richieste(_numero_richiesta));
	ep_gisa := (select coalesce(id_ruolo_gisa,-1) from spid.get_lista_richieste(_numero_richiesta));
	ep_gisa_ext := (select coalesce(id_ruolo_gisa_ext,-1) from spid.get_lista_richieste(_numero_richiesta));
	ep_bdu := (select coalesce(id_ruolo_bdu,-1) from spid.get_lista_richieste(_numero_richiesta));
	ep_vam := (select coalesce(id_ruolo_vam, -1) from spid.get_lista_richieste(_numero_richiesta));
	ep_digemon := (select coalesce(id_ruolo_digemon,-1) from spid.get_lista_richieste(_numero_richiesta));

	_ruolo_gisa := (select coalesce(ruolo_gisa,'') from spid.get_lista_richieste(_numero_richiesta));
	_ruolo_gisa_ext := (select coalesce(ruolo_gisa_ext,'')from spid.get_lista_richieste(_numero_richiesta));
	_ruolo_bdu := (select coalesce(ruolo_bdu,'') from spid.get_lista_richieste(_numero_richiesta));
	_ruolo_vam := (select coalesce(ruolo_vam,'') from spid.get_lista_richieste(_numero_richiesta));
	_ruolo_digemon :=(select coalesce(ruolo_digemon,'') from spid.get_lista_richieste(_numero_richiesta));


	if (ep_gisa > 0) then
		-- richiamo su Gisa
		query := 'select * from spid.check_validita_ruolo_cf('''||_codice_fiscale||''', '||ep_gisa||', ''gisa'')';
		esito_check := (select * from spid.esegui_query(query, 'guc', _id_utente,''));
		if get_json_valore(esito_check, 'Esito')='OK' then -- significa che � ok
		-- chiamo guc e gestisco l'output
			query  := 'select * from spid.elimina_ruolo_utente_guc('||get_json_valore(esito_check, 'IdUtente')||','||get_json_valore(esito_check, 'IdRuolo')||',''Gisa'')';
			output_guc := (select * from spid.esegui_query(query , 'guc', _id_utente,''));
			if (get_json_valore(output_guc, 'Esito') = 'OK') then
				query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'gisa', 'elimina', _id_utente, '', '', now()::timestamp without time zone - interval '1 DAY',-1));
				output_gisa := (select * from spid.esegui_query(query_endpoint, 'gisa', _id_utente,'host=dbGISAL dbname=gisa'));
			end if;	
		else
			output_gisa = esito_check;		        
		end if;
        end if; -- fine ep_gisa

	if (ep_gisa_ext > 0) then
		-- richiamo su Gisa
		query := 'select * from spid.check_validita_ruolo_cf('''||_codice_fiscale||''', '||ep_gisa_ext||', ''gisa_ext'')';
		esito_check := (select * from spid.esegui_query(query, 'guc', _id_utente,''));
		if get_json_valore(esito_check, 'Esito')='OK' then -- significa che � ok
		-- chiamo guc e gestisco l'output
			query  := 'select * from spid.elimina_ruolo_utente_guc('||get_json_valore(esito_check, 'IdUtente')||','||get_json_valore(esito_check, 'IdRuolo')||',''Gisa_ext'')';
			output_guc := (select * from spid.esegui_query(query , 'guc', _id_utente,''));
			if (get_json_valore(output_guc, 'Esito') = 'OK') then
				query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'gisa_ext', 'elimina', _id_utente, '', '', (now()::timestamp without time zone - interval '1 DAY'),-1));
				output_gisa_ext := (select * from spid.esegui_query(query_endpoint, 'gisa_ext', _id_utente,'host=dbGISAL dbname=gisa'));
			end if;	
		else
			output_gisa_ext = esito_check;		        
		end if;
        end if; -- fine ep_gisa_ext
        
	if (ep_bdu > 0) then
		-- richiamo su BDU
		query := 'select * from spid.check_validita_ruolo_cf('''||_codice_fiscale||''', '||ep_bdu||', ''bdu'')';
		esito_check := (select * from spid.esegui_query(query, 'guc', _id_utente,''));
		if get_json_valore(esito_check, 'Esito')='OK' then -- significa che � ok
		-- chiamo guc e gestisco l'output
			query  := 'select * from spid.elimina_ruolo_utente_guc('||get_json_valore(esito_check, 'IdUtente')||','||get_json_valore(esito_check, 'IdRuolo')||',''bdu'')';
			output_guc := (select * from spid.esegui_query(query , 'guc', _id_utente,''));
			if (get_json_valore(output_guc, 'Esito') = 'OK') then
				query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'bdu', 'elimina', _id_utente, '', '', (now()::timestamp without time zone - interval '1 DAY'),-1));
				output_bdu := (select * from spid.esegui_query(query_endpoint, 'bdu', _id_utente,'host=dbBDUL dbname=bdu'));
				
			end if;	
		else
			output_bdu = esito_check;		        
		end if;
        end if; -- fine ep_bdu
        
	if (ep_vam > 0) then
		-- richiamo su vam
		query := 'select * from spid.check_validita_ruolo_cf('''||_codice_fiscale||''', '||ep_vam||', ''vam'')';
		esito_check := (select * from spid.esegui_query(query, 'guc', _id_utente,''));
		if get_json_valore(esito_check, 'Esito')='OK' then -- significa che � ok
		-- chiamo guc e gestisco l'output
			query  := 'select * from spid.elimina_ruolo_utente_guc('||get_json_valore(esito_check, 'IdUtente')||','||get_json_valore(esito_check, 'IdRuolo')||',''vam'')';
			output_guc := (select * from spid.esegui_query(query , 'guc', _id_utente,''));
			if (get_json_valore(output_guc, 'Esito') = 'OK') then
				query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'vam', 'elimina', _id_utente, '', '', (now()::timestamp without time zone - interval '1 DAY'),-1));
				output_vam := (select * from spid.esegui_query(query_endpoint, 'vam', _id_utente,'host=dbVAML dbname=vam'));
			end if;	
		else
			output_vam = esito_check;		        
		end if;
        end if; -- fine ep_vam

	if (ep_digemon > 0) then
		-- richiamo su digemon
		query := 'select * from spid.check_validita_ruolo_cf('''||_codice_fiscale||''', '||ep_digemon||', ''digemon'')';
		esito_check := (select * from spid.esegui_query(query, 'guc', _id_utente,''));
		if get_json_valore(esito_check, 'Esito')='OK' then -- significa che � ok
		-- chiamo guc e gestisco l'output
			query  := 'select * from spid.elimina_ruolo_utente_guc('||get_json_valore(esito_check, 'IdUtente')||','||get_json_valore(esito_check, 'IdRuolo')||',''digemon'')';
			output_guc := (select * from spid.esegui_query(query , 'guc', _id_utente,''));
			if (get_json_valore(output_guc, 'Esito') = 'OK') then
				query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'digemon', 'elimina', _id_utente, '', '', (now()::timestamp without time zone - interval '1 DAY'),-1));
				output_digemon := (select * from spid.esegui_query(query_endpoint, 'digemon', _id_utente,'host=dbdigemonL dbname=digemon_u'));
			end if;	
		else
			output_digemon = esito_check;		        
		end if;
        end if; -- fine ep_digemon
       
	esito_processa = '{"EndPoint" : "GUC", "Risultato": '||output_guc||', "CodiceFiscale": "'||_codice_fiscale||'","ListaEndPoint" : [
					{"EndPoint" : "GISA", "Risultato" : ['||output_gisa||']},
					{"EndPoint" : "GISA_EXT", "Risultato" : ['||output_gisa_ext||']},
					{"EndPoint" : "BDR", "Risultato" : ['||output_bdu||']},
					{"EndPoint" : "VAM", "Risultato" : ['||output_vam||']},
					{"EndPoint" : "DIGEMON", "Risultato" : ['||output_digemon||']}]}';	
				   
	raise info '[STAMPA ESITO PROCESSA] %', esito_processa;
	

	UPDATE spid.spid_registrazioni_esiti set trashed_date = now() where numero_richiesta = _numero_richiesta and trashed_date is null;
	insert into spid.spid_registrazioni_esiti(numero_richiesta, esito_guc, esito_gisa, esito_gisa_ext, esito_bdu, esito_vam, esito_digemon,id_utente_esito, stato, json_esito) 
	values (_numero_richiesta,true,(case when get_json_valore(output_gisa, 'Esito')='OK' then true when get_json_valore(output_gisa, 'Esito')='KO' then false else null end),
			       (case when get_json_valore(output_gisa_ext, 'Esito')='OK' then true when get_json_valore(output_gisa_ext, 'Esito')='KO' then false else null end),
			       (case when get_json_valore(output_bdu, 'Esito')='OK' then true when get_json_valore(output_bdu, 'Esito')='KO' then false else null end),
			       (case when get_json_valore(output_vam, 'Esito')='OK' then true when get_json_valore(output_vam, 'Esito')='KO' then false else null end),
			       (case when get_json_valore(output_digemon, 'Esito')='OK' then true when get_json_valore(output_digemon, 'Esito')='KO' then false else null end),
					       _id_utente, (case when output_guc = 'OK' then 1 end)
						,esito_processa);					
		   
	return esito_processa;
	
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION spid.processa_richiesta_elimina(text, integer)
  OWNER TO postgres;






-- db guc

-- Function: spid.elimina_ruolo_utente_guc(integer, integer, text)

-- DROP FUNCTION spid.elimina_ruolo_utente_guc(integer, integer, text);

CREATE OR REPLACE FUNCTION spid.elimina_ruolo_utente_guc(
    _id_utente integer,
    _id_ruolo integer,
    _endpoint text)
  RETURNS text AS
$BODY$

  DECLARE 

	conta integer;	
	esito text;
	descrizione_errore text;
  BEGIN
	descrizione_errore='';
	esito='';
	
	conta := (select count(*) from guc_utenti_ u 
		  join guc_ruoli r on u.id = r.id_utente
		  where u.enabled and u.cessato <> true and u.data_scadenza is null and 
		  u.id = _id_utente
		  and r.endpoint ilike _endpoint and r.ruolo_integer = _id_ruolo);
	
	if(conta > 1) then
		esito = 'KO';
		descrizione_errore = 'Impossibile identificare univocamente utente soggetto a cancellazione. Risultano esistenti diversi utenti per la coppia codice fiscale/ruolo.';
	elsif (conta = 0) then
		esito = 'KO';
		descrizione_errore = 'Impossibile identificare utente soggetto a cancellazione. Non risulta esistente nessun utente per coppia codice fiscale/ruolo.';
	else 
		raise info 'Trovato 1 utente in GUC..';
		update guc_ruoli set trashed=true, data_scadenza = (now() - interval '1 DAY'), note=concat_ws('***',note,'Richiesta di disattivazione account') where 
		id_utente = _id_utente and ruolo_integer = _id_ruolo and endpoint ilike _endpoint;
		
		esito = 'OK';
	end if;
	
	return '{"Esito" : "'||esito||'", "DescrizioneErrore" : "'||descrizione_errore ||'"}';

  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION spid.elimina_ruolo_utente_guc(integer, integer, text)
  OWNER TO postgres;


-- Function: spid.insert_ruolo_utente_guc(integer, integer, text)

-- DROP FUNCTION spid.insert_ruolo_utente_guc(integer, integer, text);
-- Function: spid.insert_ruolo_utente_guc(integer, integer, text)

-- DROP FUNCTION spid.insert_ruolo_utente_guc(integer, integer, text);

-- Function: spid.insert_ruolo_utente_guc(integer, integer, text)

-- DROP FUNCTION spid.insert_ruolo_utente_guc(integer, integer, text);

CREATE OR REPLACE FUNCTION spid.insert_ruolo_utente_guc(
    _id_utente integer,
    _id_ruolo integer,
    _endpoint text)
  RETURNS text AS
$BODY$

  DECLARE 

	conta integer;	
	esito text;
	descrizione_errore text;
  BEGIN
	descrizione_errore='';
	esito='';
	conta := (select count(*) from guc_utenti_ u 
		  join guc_ruoli r on u.id = r.id_utente
		  where u.enabled and u.cessato <> true and u.data_scadenza is null and 
		  u.id = _id_utente
		  and r.endpoint ilike _endpoint and r.ruolo_integer = _id_ruolo);
	
	if(conta > 0) then
		esito = 'KO';
		descrizione_errore = 'Impossibile inserire ruolo. Risulta esistente almeno un utente per la coppia codice fiscale/ruolo.';
	else
	insert into guc_ruoli (id_utente, ruolo_integer, ruolo_string, endpoint, note) values (_id_utente, _id_ruolo, (select ruolo_string from guc_ruoli where endpoint ilike _endpoint and ruolo_integer = _id_ruolo limit 1), (select endpoint from guc_ruoli where endpoint ilike _endpoint limit 1), '***Richiesta di modifica account');
		esito = 'OK';
	end if;
	
	return '{"Esito" : "'||esito||'", "DescrizioneErrore" : "'||descrizione_errore ||'"}';

  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION spid.insert_ruolo_utente_guc(integer, integer, text)
  OWNER TO postgres;




------------------------------------------------------------------------------------------------

-------------------------------------------------------------------------------------------------
---------------------------------------------------GISA------------------------------------------
-------------------------------------------------------------------------------------------------

-- Function: public.dbi_elimina_utente(text, integer, timestamp without time zone)

-- DROP FUNCTION public.dbi_elimina_utente(text, integer, timestamp without time zone);

CREATE OR REPLACE FUNCTION public.dbi_elimina_utente(
    _cf text,
    _role_id integer,
    _data_scadenza timestamp without time zone)
  RETURNS text AS
$BODY$
   DECLARE
esito text ;
errore text;
idUtente int;   
BEGIN

esito := '';
errore := '';

RAISE INFO '[dbi_elimina_utente] _cf %', _cf;
RAISE INFO '[dbi_elimina_utente] _role_id %', _role_id;
RAISE INFO '[dbi_elimina_utente] _data_scadenza %', _data_scadenza;

	select a.user_id into idUtente from access a 
	join contact c on a.contact_id = c.contact_id 
	where c.codice_fiscale ilike _cf and a.role_id = _role_id and a.trashed_date is null and a.enabled and a.data_scadenza is null;

RAISE INFO '[dbi_elimina_utente] idUtente %', idUtente;

	
	IF (idUtente is null) THEN
		esito='KO';
		errore='Impossibile identificare utente soggetto a cancellazione. Non risulta esistente nessun utente per la coppia codice fiscale/ruolo.';
	ELSE
	
		UPDATE access_ SET modified = now(), modifiedby = 964, data_scadenza = _data_scadenza, note_hd = concat_ws(';', note_hd, 'DATA SCADENZA VALORIZZATA TRAMITE dbi_elimina_utente') WHERE user_id = idUtente;
		esito = 'OK';
	END IF;
	RETURN '{"Esito" : "'||esito||'", "DescrizioneErrore": "'||errore||'"}';
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dbi_elimina_utente(text, integer, timestamp without time zone)
  OWNER TO postgres;


  -------------------------------------------------------------------------------------------------
---------------------------------------------------GISA------------------------------------------
-------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION public.dbi_elimina_utente_ext(_cf text, _role_id integer, _data_scadenza timestamp without time zone)
  RETURNS text AS
$BODY$
   DECLARE
esito text ;
errore text;
idUtente int;   
BEGIN
esito := '';
errore := '';

RAISE INFO '[dbi_elimina_utente] _cf %', _cf;
RAISE INFO '[dbi_elimina_utente] _role_id %', _role_id;
RAISE INFO '[dbi_elimina_utente] _data_scadenza %', _data_scadenza;

	select a.user_id into idUtente from access_ext a 
	join contact_ext c on a.contact_id = c.contact_id 
	where c.codice_fiscale ilike 'CAITZI80A01H703V' and a.role_id = _role_id and a.trashed_date is null and a.enabled and a.data_scadenza is null;

RAISE INFO '[dbi_elimina_utente] idUtente %', idUtente;

	IF (idUtente is null) THEN
		esito='KO';
		errore='Impossibile identificare utente soggetto a cancellazione. Non risulta esistente nessun utente per la coppia codice fiscale/ruolo.';
	ELSE
	
		UPDATE access_ext_ SET modified = now(), modifiedby = 964, data_scadenza = _data_scadenza, note_hd = concat_ws(';', note_hd, 'DATA SCADENZA VALORIZZATA TRAMITE dbi_elimina_utente') WHERE user_id = idUtente;
		esito = 'OK';
	END IF;
	RETURN '{"Esito" : "'||esito||'", "DescrizioneErrore": "'||errore||'"}';
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-------------------------------------------------------------------------------------------------
---------------------------------------------------BDU-------------------------------------------
-------------------------------------------------------------------------------------------------

alter table access_ add column note_hd text;

CREATE OR REPLACE FUNCTION public.dbi_elimina_utente(_cf text, _role_id integer, _data_scadenza timestamp without time zone)
  RETURNS text AS
$BODY$
   DECLARE
esito text ;
errore text;
idUtente int;   
BEGIN

esito := '';
errore := '';

RAISE INFO '[dbi_elimina_utente] _cf %', _cf;
RAISE INFO '[dbi_elimina_utente] _role_id %', _role_id;
RAISE INFO '[dbi_elimina_utente] _data_scadenza %', _data_scadenza;

	select a.user_id into idUtente from access a 
	join contact c on a.contact_id = c.contact_id 
	where c.codice_fiscale ilike _cf and a.role_id = _role_id and a.trashed_date is null and a.enabled and a.data_scadenza is null;

RAISE INFO '[dbi_elimina_utente] idUtente %', idUtente;
	
	IF (idUtente is null) THEN
		esito='KO';
		errore='Impossibile identificare utente soggetto a cancellazione. Non risulta esistente nessun utente per la coppia codice fiscale/ruolo.';
	ELSE
	
		UPDATE access_ SET modified = now(), modifiedby = 964, data_scadenza = _data_scadenza, note_hd = concat_ws(';', note_hd, 'DATA SCADENZA VALORIZZATA TRAMITE dbi_elimina_utente') WHERE user_id = idUtente;
		esito = 'OK';
	END IF;
	RETURN '{"Esito" : "'||esito||'", "DescrizioneErrore": "'||errore||'"}';
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-------------------------------------------------------------------------------------------------
------------------------------------------------VAM----------------------------------------------
-------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION public.dbi_elimina_utente(_cf text, _role_id integer, _data_scadenza timestamp without time zone)
  RETURNS text AS
$BODY$
   DECLARE
esito text ;
errore text;
idUtente int;   
BEGIN

esito := '';
errore := '';

RAISE INFO '[dbi_elimina_utente] _cf %', _cf;
RAISE INFO '[dbi_elimina_utente] _role_id %', _role_id;
RAISE INFO '[dbi_elimina_utente] _data_scadenza %', _data_scadenza;

	select a.id into idUtente from utenti a 
	where a.codice_fiscale ilike _cf and a.ruolo::integer = _role_id and a.trashed_date is null and a.enabled and a.data_scadenza is null;

RAISE INFO '[dbi_elimina_utente] idUtente %', idUtente;
	
	IF (idUtente is null) THEN
		esito='KO';
		errore='Impossibile identificare utente soggetto a cancellazione. Non risulta esistente nessun utente per la coppia codice fiscale/ruolo.';
	ELSE
	
		UPDATE utenti_ SET modified = now(), modified_by = 964, data_scadenza = _data_scadenza, note_internal_use_only_hd = concat_ws(';', note_internal_use_only_hd, 'DATA SCADENZA VALORIZZATA TRAMITE dbi_elimina_utente') WHERE id = idUtente;
		esito = 'OK';
	END IF;
	RETURN '{"Esito" : "'||esito||'", "DescrizioneErrore": "'||errore||'"}';
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-------------------------------------------------------------------------------------------------
------------------------------------------------DIGEMON------------------------------------------
-------------------------------------------------------------------------------------------------
alter table access_ add column note_hd text;

CREATE OR REPLACE FUNCTION public.dbi_elimina_utente(_cf text, _role_id integer, _data_scadenza timestamp without time zone)
  RETURNS text AS
$BODY$
   DECLARE
esito text ;
errore text;
idUtente int;   
BEGIN

esito := '';
errore := '';

RAISE INFO '[dbi_elimina_utente] _cf %', _cf;
RAISE INFO '[dbi_elimina_utente] _role_id %', _role_id;
RAISE INFO '[dbi_elimina_utente] _data_scadenza %', _data_scadenza;

	select a.user_id into idUtente from access a 
	join contact c on a.contact_id = c.contact_id 
	where c.codice_fiscale ilike _cf and a.role_id = _role_id and a.trashed_date is null and a.enabled and a.data_scadenza is null;

RAISE INFO '[dbi_elimina_utente] idUtente %', idUtente;
	
	IF (idUtente is null) THEN
		esito='KO';
		errore='Impossibile identificare utente soggetto a cancellazione. Non risulta esistente nessun utente per la coppia codice fiscale/ruolo.';
	ELSE
	
		UPDATE access_ SET modified = now(), modifiedby = 964, data_scadenza = _data_scadenza, note_hd = concat_ws(';', note_hd, 'DATA SCADENZA VALORIZZATA TRAMITE dbi_elimina_utente') WHERE user_id = idUtente;
		esito = 'OK';
	END IF;
	RETURN '{"Esito" : "'||esito||'", "DescrizioneErrore": "'||errore||'"}';
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;


