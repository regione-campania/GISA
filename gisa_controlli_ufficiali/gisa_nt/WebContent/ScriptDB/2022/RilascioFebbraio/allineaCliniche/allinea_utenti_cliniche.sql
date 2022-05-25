-- VAM
--cambia dbhost in dblink

CREATE OR REPLACE FUNCTION public.allinea_tutte_cliniche_vam_per_hd(
    _codice_fiscale text,
    _ruolo_category text,
    id_clinica integer)
  RETURNS text AS
$BODY$
   DECLARE
	id_utente integer;
	us_id integer;
	esito text ;
BEGIN

	esito := '';
	us_id := nextVal('utenti_id_seq');
	insert into utenti_ (id, clinica,codice_fiscale,cognome,enabled,entered,nome, password,ruolo,username,superutente,asl_referenza)
	values (us_id, id_clinica, 
		(select codice_fiscale from utenti where codice_fiscale ilike _codice_fiscale and ruolo in ('1','2','16','17') limit 1),
		(select cognome from utenti where codice_fiscale ilike _codice_fiscale and ruolo in ('1','2','16','17') limit 1),
		true,
		now(),
		(select nome from utenti where codice_fiscale ilike _codice_fiscale and ruolo in ('1','2','16','17') limit 1),
		(select password from utenti where codice_fiscale ilike _codice_fiscale and ruolo in ('1','2','16','17') limit 1),
		(select ruolo from utenti where codice_fiscale ilike _codice_fiscale and ruolo in ('1','2','16','17') limit 1),
		(select username from utenti where codice_fiscale ilike _codice_fiscale and ruolo in ('1','2','16','17') limit 1),
		(select superutente from utenti where codice_fiscale ilike _codice_fiscale and ruolo in ('1','2','16','17') limit 1),
		(select asl_referenza from utenti where codice_fiscale ilike _codice_fiscale and ruolo in ('1','2','16','17') limit 1)
		);
	
	insert into secureobject(name) values (''||us_id||'');					
	insert into category_secureobject (categories_name, secureobjects_name) values (_ruolo_category, us_id);

	esito:= 'OK';
	
return esito;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.allinea_tutte_cliniche_vam_per_hd(text, text, integer)
  OWNER TO postgres;


--GUC
  CREATE OR REPLACE FUNCTION public.allinea_tutte_cliniche_vam_per_hd(IN  _id_utente_guc integer, id_utente_operazione integer)
  RETURNS text AS
$BODY$
DECLARE
   rec integer;
   conta_associazione integer;
   verifica_esistenza integer;
   query text;
   id_clinica_out integer;
   nome_clinica text;
   query_endpoint text;
   output_vam text;
   id_super_utente integer;
   id_nuovo_utente integer;
   categories_name text;
   codice_fiscale text;
   nome text;
   messaggio text;
BEGIN
	-- controllo preliminare su GUC per admin Vam
	verifica_esistenza := (select count(*) from guc_utenti u join guc_ruoli r on r.id_utente=u.id
	where (r.trashed is null or r.trashed is false) and u.enabled
	and r.ruolo_integer > 0 and r.endpoint ilike 'vam' 
	and r.ruolo_integer in (1,2,16,17) and u.id = _id_utente_guc);
	if (verifica_esistenza > 0) then 
	        raise info 'esiste in guc utente' ;
		codice_fiscale := (select g.codice_fiscale from guc_utenti g where g.id  = _id_utente_guc limit 1);
		id_super_utente :=(select id
				   --FROM dblink('host=dbVAML dbname=vam'::text, 'SELECT 
			FROM dblink('host=127.0.0.1 dbname=vam'::text, 'SELECT superutente from utenti where codice_fiscale ilike '''||codice_fiscale||''' limit 1') 
			t1(id integer));
		raise info 'id_super_utente %', id_super_utente;
		FOR rec IN
			select t1.id_clinica_out
				   --FROM dblink('host=dbVAML dbname=vam'::text, 'SELECT 
			FROM dblink('host=127.0.0.1 dbname=vam'::text, 'SELECT * from public.dbi_get_cliniche_utente_attive()') 
			t1(asl_id integer, id_clinica_out integer, clinica_out text)
		LOOP
			raise info 'id clinica: %', rec;
			conta_associazione := (select count(*) from guc_cliniche_vam where id_clinica  = rec and id_utente = _id_utente_guc);
			if (conta_associazione = 0) then -- nessuna associazione quindi inserisci la clinica in guc
				--parte I GUC
				insert into guc_cliniche_vam(id_clinica, descrizione_clinica, id_utente) values(rec, (select clinica_out
																--FROM dblink('host=dbVAML dbname=vam'::text, 'SELECT 
															FROM dblink('host=127.0.0.1 dbname=vam'::text, 'SELECT nome_clinica from public.dbi_get_cliniche_utente_attive() where id_clinica='||rec||'') 
															t1(clinica_out text)) 
				,_id_utente_guc);
				 -- parte 2 VAM
				 -- verifo se presente la clinica?
				 conta_associazione := (select t1.numero
							  FROM dblink('host=127.0.0.1 dbname=vam'::text, 'select count(*) from utenti where clinica  = '||rec||' and codice_fiscale ilike '''||codice_fiscale||'''') 
							  t1(numero integer));
				raise info 'numero associazione clinica %', conta_associazione;
				 if (conta_associazione = 0) then -- significa che in vam non esiste
					  raise info 'inserisco associazione in vam';
					  output_vam:= (select t1.esito
					  --FROM dblink('host=dbVAML dbname=vam'::text, 'SELECT 
								  FROM dblink('host=127.0.0.1 dbname=vam'::text, 'select * from public.allinea_tutte_cliniche_vam_per_hd('''||codice_fiscale||''',''Amministratore'','||rec||')')
								  t1(esito text)
							);
					raise info 'output vam: %',output_vam;
				end if; -- fine inserimento in vam
			end if;
			
		END LOOP;

		messaggio :='{"Esito" : "OK", "DescrizioneErrore" : ""}';

	else 
		messaggio :='{"Esito" : "KO", "DescrizioneErrore" : "Utente non presente in GU con ruolo di amministratore VAM"}';
	end if;
	
	return messaggio;
END;
$BODY$
  LANGUAGE plpgsql; 

select * from utenti where codice_fiscale ilike '%SNSBTL86S12G813J%'
select * from allinea_tutte_cliniche_vam_per_hd('SNSBTL86S12G813J','Amministratore',66)