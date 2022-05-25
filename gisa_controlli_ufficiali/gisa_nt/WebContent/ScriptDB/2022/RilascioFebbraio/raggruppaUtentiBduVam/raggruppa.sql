-- query per tirare fuori utenti che hanno ruolo ASL BDU e non VAM

CREATE OR REPLACE FUNCTION public.lista_utenti(IN _endpoint text DEFAULT ''::text)
  RETURNS TABLE(codice_fiscale text, username text, id_asl integer, descrizione_asl text, id_utente integer, sistema text, id_ruolo integer, descrizione_ruolo text) AS
$BODY$
DECLARE
BEGIN

	return query 
	select upper(a.codice_fiscale)::text as codice_fiscale, a.username::text as username, a.asl_id, (select nome::text from asl where id = a.asl_id),  r.id_utente, r.endpoint::text as endpoint, r.ruolo_integer, r.ruolo_string::text as ruolo
	from (
	select g.codice_fiscale, g.id, g.username, g.asl_id  from guc_utenti g where id in (
	(select l.id_utente from guc_ruoli l where  (l.trashed is null or l.trashed is false) and l.endpoint ilike '%bdu%' and l.ruolo_integer > 0 and l.ruolo_integer in (20,29,34,36,18)
	except
	select m.id_utente from guc_ruoli m where (m.trashed is null or m.trashed is false) and m.endpoint ilike '%vam%' and m.ruolo_integer > 0 and m.ruolo_integer in(16,1,2,17,3,5,14) )
	UNION
	-- query per tirare fuori utenti che hanno ruolo VAM e non BDU
	(
	select n.id_utente from guc_ruoli n where (n.trashed is null or n.trashed is false) and n.endpoint ilike '%vam%' and n.ruolo_integer > 0 and n.ruolo_integer in(16,1,2,17,3,5,14)
	except 
	select o.id_utente from guc_ruoli o where (o.trashed is null or o.trashed is false) and o.endpoint ilike '%bdu%' and o.ruolo_integer > 0 and o.ruolo_integer in (20,29,34,36,18)
	))
	and data_scadenza is null and enabled order by id desc
	) a join guc_ruoli r on r.id_utente= a.id 
	where (r.trashed is null or r.trashed is false) 
	and (r.endpoint ilike '%vam%' and (r.ruolo_integer > 0 and r.ruolo_integer in(16,1,2,17,3,5,14)) or (r.endpoint ilike '%bdu%' and r.ruolo_integer > 0 and r.ruolo_integer in (20,29,34,36,18)))
	and (_endpoint = '' or r.endpoint ilike _endpoint)
	order by a.codice_fiscale;

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.lista_utenti(text)
  OWNER TO postgres;

  CREATE OR REPLACE FUNCTION spid.disabilita_tutti_endpoint_utente(
    _utente text)
  RETURNS text AS
$BODY$
declare
	_id integer;
	id_utente_guc_ruoli integer;
	id_ruolo_guc_vam integer;
	id_ruolo_guc_bdu integer;
	id_ruolo_guc_gisa integer;
	id_ruolo_guc_digemon integer;
	id_ruolo_guc_gisa_ext integer;
	output_bdu text;
	output_vam text;
	output_gisa text;
	output_gisa_ext text;
	output_digemon text;
	query text;
BEGIN
	output_bdu = '';
	output_vam = '';
	output_gisa = '';
	output_gisa_ext = '';
	output_digemon = '';
	id_utente_guc_ruoli := (select id from guc_utenti where username ilike _utente and enabled and data_scadenza is null);
						
	id_ruolo_guc_bdu := (select ruolo_integer from guc_ruoli where id_utente = id_utente_guc_ruoli and endpoint ilike 'bdu' and (trashed is null or trashed = false));
	raise info 'id ruolo BDU: %', id_ruolo_guc_bdu;
		
	id_ruolo_guc_vam:= (select ruolo_integer from guc_ruoli where id_utente = id_utente_guc_ruoli and endpoint ilike 'vam' and (trashed is null or trashed = false));
	raise info 'id ruolo VAM: %', id_ruolo_guc_vam;

	id_ruolo_guc_gisa := (select ruolo_integer from guc_ruoli where id_utente = id_utente_guc_ruoli and endpoint ilike 'gisa' and (trashed is null or trashed = false));
	raise info 'id ruolo GISA: %', id_ruolo_guc_gisa;
			
	id_ruolo_guc_digemon := (select ruolo_integer from guc_ruoli where id_utente = id_utente_guc_ruoli and endpoint ilike 'digemon' and (trashed is null or trashed = false));
	raise info 'id ruolo DIGEMON: %', id_ruolo_guc_gisa;

	id_ruolo_guc_gisa_ext := (select ruolo_integer from guc_ruoli where id_utente = id_utente_guc_ruoli and endpoint ilike 'gisa_ext' and (trashed is null or trashed = false));
	raise info 'id ruolo GISA EXT: %', id_ruolo_guc_gisa;

	if(id_ruolo_guc_bdu > 0) then 
		query  := 'select * from spid.elimina_ruolo_utente_guc('||id_utente_guc_ruoli||','||id_ruolo_guc_bdu||',''bdu'')';
		output_bdu := (select * from spid.esegui_query(query , 'guc', 6567,''));
	end if;

	if(id_ruolo_guc_vam > 0) then 
		query  := 'select * from spid.elimina_ruolo_utente_guc('||id_utente_guc_ruoli||','||id_ruolo_guc_vam||',''vam'')';
		output_vam := (select * from spid.esegui_query(query , 'guc', 6567,''));
	end if;
		
	if(id_ruolo_guc_gisa > 0) then 
		query  := 'select * from spid.elimina_ruolo_utente_guc('||id_utente_guc_ruoli||','||id_ruolo_guc_gisa||',''Gisa'')';
		output_gisa := (select * from spid.esegui_query(query , 'guc', 6567,''));
	end if;	
				
	if (id_ruolo_guc_gisa_ext) then
		query  := 'select * from spid.elimina_ruolo_utente_guc('||id_utente_guc_ruoli||','||id_ruolo_guc_gisa_ext||',''Gisa_ext'')';
		output_gisa_ext := (select * from spid.esegui_query(query , 'guc', 6567,''));
	end if;	
	if (id_ruolo_guc_digemon) then
		query  := 'select * from spid.elimina_ruolo_utente_guc('||id_utente_guc_ruoli||','||id_ruolo_guc_digemon||',''Digemon'')';
		output_digemon := (select * from spid.esegui_query(query , 'guc', 6567,''));
	end if;

	return cooncat_ws('-', output_bdu, output_vam, output_gisa, output_gisa_ext, output_digemon);
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION spid.disabilita_tutti_endpoint_utente(text)
  OWNER TO postgres;


-- considero solo gli EP VAM e BDU
-- partiamo dalla premessa che l'utente 1 è sempre BDU mentre l'utente 2 è quello di VAM

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
  BEGIN
	descrizione_errore='';
	esito='';
	output_bdu = '';
	output_vam = '';
	output_guc := '';

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