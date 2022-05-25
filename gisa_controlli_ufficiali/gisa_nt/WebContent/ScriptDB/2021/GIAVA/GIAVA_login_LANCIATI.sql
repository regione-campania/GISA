-- Function: public.login_giava(text, text)

-- DROP FUNCTION public.login_giava(text, text);

--select * from access_ext where user_id = 10000001 limit 4

CREATE SCHEMA giava
  AUTHORIZATION postgres;
-- Function: giava.login_giava(text, text, text)

-- DROP FUNCTION giava.login_giava(text, text, text);
-- Function: giava.login_giava(text, text, text)

-- DROP FUNCTION giava.login_giava(text, text, text);

-- Function: giava.login_giava(text, text, text)

-- DROP FUNCTION giava.login_giava(text, text, text);
-- Function: giava.login_giava(text, text, text)

-- DROP FUNCTION giava.login_giava(text, text, text);

CREATE OR REPLACE FUNCTION giava.login_giava(
    _username text DEFAULT NULL::text,
    _password text DEFAULT NULL::text,
    _cf text DEFAULT NULL::text)
  RETURNS SETOF json AS
$BODY$
DECLARE
	id_fiscale_allev text;
BEGIN

	if (_cf is not null and _cf <> '' and length(_cf) > 0) then
	raise info 'il codice fiscale risulta valorizzato';
	 return query
		select json_agg(tab)::json as lista_stabilimenti from
		(
		/*query da modificare */
			select distinct
				trim(acc.username::text) as username,
				trim(accdat.visibilita_delega::text) as cf,
				trim(ro.role::text) as role,
				trim(ro.role_id::text) as role_id,
				trim(acc.last_login::text) as last_login,
				trim(acc.user_id::text) as user_id,
				--trim(acc.site_id::text) as id_asl,
				trim(ram.asl_rif::text) as id_asl,
				trim(c.namefirst::text) as nome,
				trim(c.namelast::text) as cognome,
				trim(ram.riferimento_id::text) as riferimento_id,
				trim(ram.riferimento_id_nome::text) as riferimento_id_nome,
				trim(ram.riferimento_id_nome_tab::text) as riferimento_id_nome_tab,
				trim(ram.ragione_sociale::text) as ragione_sociale,
				trim(concat_ws('-', trim(ram.partita_iva), ram.comune,  case when ram.tipologia_operatore = 2 then 
						(select concat('', specie_allev) from organization where trashed_date is null and tipologia =2 and org_id = ram.riferimento_id) else '' end)) 
				as dati_anag
				
			from access_ext_ acc 
				left join role_ext ro on acc.role_id = ro.role_id
				left join contact_ext_ c on c.contact_id = acc.contact_id
				left join access_dati accdat on accdat.user_id = acc.user_id
				join ricerche_anagrafiche_old_materializzata ram on (ram.n_reg = split_part(accdat.num_registrazione_stab,'-',1) or ram.n_linea = split_part(accdat.num_registrazione_stab,'-',1)) and (length(ram.n_reg) > 0 or length(ram.n_linea) > 0)  -- DA TESTARE!
											-- and ram.riferimento_id_nome_tab = accdat.riferimento_id_nome_tab
			 where trim(accdat.visibilita_delega) = trim(_cf) 
				-- numreg non vuto??
				and length(accdat.num_registrazione_stab) > 0 
				-- new per codice_fiscale_proprietario ??
				and (case when split_part(accdat.num_registrazione_stab,'-',2) <> '' then ram.codice_fiscale_rappresentante = split_part(accdat.num_registrazione_stab,'-',2) else 1=1 end)
				and acc.enabled and ( acc.expires > now() or acc.expires is null ) --limit 1
		) tab;
	else 
		raise info 'il codice fiscale non risulta valorizzato';
		return query
		select json_agg(tab)::json as lista_stabilimenti from
		(
			/*query da modificare */
			select distinct
				trim(acc.username::text) as username,
				trim(accdat.visibilita_delega::text) as cf,
				trim(ro.role::text) as role,
				trim(ro.role_id::text) as role_id,
				trim(acc.last_login::text) as last_login,
				trim(acc.user_id::text) as user_id,
				--trim(acc.site_id::text) as id_asl,
				trim(c.namefirst::text) as nome,
				trim(c.namelast::text) as cognome,
				trim(ram.asl_rif::text) as id_asl,
				trim(ram.riferimento_id::text) as riferimento_id,
				trim(ram.riferimento_id_nome::text) as riferimento_id_nome,
				trim(ram.riferimento_id_nome_tab::text) as riferimento_id_nome_tab,
				trim(ram.ragione_sociale::text) as ragione_sociale,
				trim(concat_ws('-', trim(ram.partita_iva), ram.comune, 
						    case when ram.tipologia_operatore = 2 then (select concat('', specie_allev) from organization where trashed_date is null and tipologia =2 and org_id = ram.riferimento_id) else '' end)) as dati_anag
			from access_ext_ acc 
				left join role_ext ro on acc.role_id = ro.role_id
				left join contact_ext_ c on c.contact_id = acc.contact_id
				left join access_dati accdat on accdat.user_id = acc.user_id
				join ricerche_anagrafiche_old_materializzata ram on (ram.n_reg = split_part(accdat.num_registrazione_stab,'-',1) or ram.n_linea = split_part(accdat.num_registrazione_stab,'-',1))  -- DA TESTARE!
											-- and ram.riferimento_id_nome_tab = accdat.riferimento_id_nome_tab
			 where trim(acc.username) = trim(_username) 
				and trim(acc.password) = md5(trim(_password))
				-- valutare se aggiungere la condizione sul cf
				-- numreg non vuto??
				and length(accdat.num_registrazione_stab) > 0 
				-- new per codice_fiscale_proprietario ??
				and (case when split_part(accdat.num_registrazione_stab,'-',2) <> '' then ram.codice_fiscale_rappresentante = split_part(accdat.num_registrazione_stab,'-',2) else 1=1 end)
				and acc.enabled and ( acc.expires > now() or acc.expires is null ) 
		) tab;
	end if;

END;	
    
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION giava.login_giava(text, text, text)
  OWNER TO postgres;

  --select specie_allev from organization  where tipologia = 2 limit 44



select * from  giava.login_giava('','','pippobaudo111')
select * from  giava.login_giava('pippobaudo111','pippobaudo111','')
select * from  giava.login_giava(null,null,'VZZVCN73T09L259M') 
select * from  giava.login_giava('','','VZZVCN73T09L259M') 
select * from  giava.login_giava('pippobaudo111','pippobaudo111',null)
select * from giava.login_giava('', '', 'MTNCNZ70R59I073W')



CREATE OR REPLACE FUNCTION giava.dbi_insert_utente_giava(
    _nome text,
    _cognome text,
    _cf text,
    _username text,
    _password text,
    _riferimento_id integer,
    _riferimento_id_nome_tab text)
  RETURNS text AS
$BODY$
DECLARE

	_expires timestamp with time zone;
	_con_id integer;
	_user_id integer;
	_msg text ;
	_username_trovato text;
	_password_trovata text;
	_id_asl integer;
	_password_md5 text;
   
BEGIN

	_nome := trim(_nome);
	_cognome := trim(_cognome);
	_cf := trim(_cf);
	_username := trim(_username);
	_password := trim(_password);
	_riferimento_id_nome_tab := trim(_riferimento_id_nome_tab);
	
	_username_trovato := null;
	_password_trovata := null;
	
	select trim(username), _password into _username_trovato, _password_trovata 
		from access_ext_ 
			where trim(username) ilike trim(_username) and trim(password) ilike md5(_password) limit 1;

	IF _username_trovato is null and _password_trovata is null THEN
	
		_con_id := (select contact_id from contact_ext c 
				where trim(c.namefirst) ilike _nome and trim(c.namelast) ilike _cognome 
					and trim(c.codice_fiscale) ilike _cf and c.trashed_date is null limit 1);

		IF (_con_id is null) THEN
			_con_id := nextVal('contact_ext_contact_id_seq');
			
			INSERT INTO contact_ext_(contact_id, namefirst, namelast, enteredby, modifiedby, codice_fiscale, enabled) 
			VALUES ( _con_id, upper(_nome),  upper(_cognome), 964, 964, _cf, true );
			
		END IF;

		
		_password_md5 := md5(_password);
		_expires := now() + interval '6 month';
		_user_id := nextVal('access_user_id_ext_seq');

		select distinct asl_rif into _id_asl from ricerche_anagrafiche_old_materializzata 
			where riferimento_id = _riferimento_id and trim(riferimento_id_nome_tab) ilike trim(_riferimento_id_nome_tab) limit 1;
		
		insert into access_ext_(user_id, username, password, contact_id, role_id, enteredby, entered, modifiedby, modified, 
					expires, timezone, currency, language, site_id, enabled)
		values(_user_id, _username, _password_md5, _con_id, -1, 964, now(), 964, now(), _expires, 'Europe/Berlin', 'EUR', 'it_IT', _id_asl, true);

		insert into access_dati(user_id, site_id, riferimento_id, riferimento_id_nome_tab, num_registrazione_stab) 
		values(_user_id, _id_asl, _riferimento_id, _riferimento_id_nome_tab, (select case when length(n_reg)> 0 then n_reg else n_linea end as num 
		from ricerche_anagrafiche_old_materializzata where riferimento_id = _riferimento_id and riferimento_id_nome_tab = _riferimento_id_nome_tab limit 1)
);
		_msg := concat('OK, inserito utente con username: ', _username, ' e password: ', _password);
		return _msg;

	ELSE
		_msg := 'ERRORE: CREDENZIALI NON VALIDE IN QUANTO GIA UTILIZZATE, INSERIRE CREDENZIALI NON PRESENTI NEL SISTEMA';
		return _msg;
	END IF;

END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION giava.dbi_insert_utente_giava(text, text, text, text, text, integer, text)
  OWNER TO postgres;
  
  

CREATE OR REPLACE FUNCTION giava.dbi_verifica_esistenza_stab_giava(
    _riferimento_id integer,
    _riferimento_id_nome_tab text)
  RETURNS text AS
$BODY$
DECLARE

	_num_trovati integer;
   
BEGIN

	select count(*) into _num_trovati
		from access_dati ad join access_ext_ ae on ad.user_id = ae.user_id
		where --ad.riferimento_id = _riferimento_id 
			--and trim(ad.riferimento_id_nome_tab) ilike trim(_riferimento_id_nome_tab)
		ad.num_registrazione_stab in (select case when length(n_reg)> 0 then n_reg else n_linea end as num from ricerche_anagrafiche_old_materializzata where riferimento_id = _riferimento_id and riferimento_id_nome_tab = _riferimento_id_nome_tab limit 1
)	
			and ae.enabled and ( ae.expires > now() or ae.expires is null );

	return _num_trovati::text;


END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION giava.dbi_verifica_esistenza_stab_giava(integer, text)
  OWNER TO postgres;

--select * from giava.dbi_verifica_esistenza_stab_giava(100002018,'sintesis_stabilimento')

