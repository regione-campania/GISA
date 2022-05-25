-- CHI: Bartolo Sansone	
-- COSA: Gestione Trasferimento Sede Operativa
-- QUANDO: 05/02/2018

insert into permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled)
values (29, 'trasferimento_sede_stabilimento', true, true, true, true, 'Trasferimento Sede Operativa', 2000, true);

insert into role_permission(permission_id, role_id, role_view, role_add, role_edit) values ((select permission_id from permission where permission ='trasferimento_sede_stabilimento'), 1, true, true, true);

insert into permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled)
values (29, 'trasferimento_sede_stabilimento_tutte_asl', true, true, true, true, 'Trasferimento Sede Operativa - vedi TUTTE le ASL', 2000, true);

insert into role_permission(permission_id, role_id, role_view, role_add, role_edit) values ((select permission_id from permission where permission ='trasferimento_sede_stabilimento_tutte_asl'), 1, true, true, true);

insert into permission (category_id, permission, permission_view, description, level, enabled)
values (29, 'trasferimento_sede_stabilimento_storico', true, 'Trasferimento Sede Operativa Storico', 2000, true);

insert into role_permission(permission_id, role_id, role_view) values ((select permission_id from permission where permission ='trasferimento_sede_stabilimento_storico'), 1, true);

create table opu_trasferimento_sede_storico(id serial primary key, data_trasferimento timestamp without time zone default now(), id_utente integer, id_stabilimento_vecchio integer, id_stabilimento_nuovo integer, id_indirizzo_vecchio integer, id_indirizzo_nuovo integer, id_asl_vecchia integer, id_asl_nuova integer);

alter table opu_stabilimento add column note_trasferimento text;
alter table opu_relazione_stabilimento_linee_produttive add column id_istanza_pre_trasferimento integer;


-- Function: public_functions.trasferimento_sede_stabilimento(integer, integer, integer, integer, text, text, text, double precision, double precision, integer, boolean)

-- DROP FUNCTION public_functions.trasferimento_sede_stabilimento(integer, integer, integer, integer, text, text, text, double precision, double precision, integer, boolean);

CREATE OR REPLACE FUNCTION public_functions.trasferimento_sede_stabilimento(
    _idstab integer,
    _idasl integer,
    _idcomune integer,
    _idtoponimo integer,
    _via text,
    _civico text,
    _cap text,
    _lat double precision,
    _lon double precision,
    _idutente integer,
    _generanuovinumeri boolean)
  RETURNS text AS
$BODY$
DECLARE
codice integer;
msg text;
idstabnuovo integer;
dataattuale timestamp without time zone;
idIndirizzoNuovo integer;
idProvincia text;
idAslVecchia integer;
idIndirizzoVecchio integer;
numRegistrazioneVecchio text;

numRegistrazioneNuovo text;
refresh boolean;
noteTrasferimento text;

BEGIN

codice:= -1;
msg:= '';
idstabnuovo := -1;
dataattuale := (select now());
idIndirizzoNuovo:= -1;
idProvincia:='';

-- SALVO DATI ATTUALI

select id_asl, id_indirizzo, numero_registrazione into idAslVecchia, idIndirizzoVecchio, numRegistrazioneVecchio from opu_stabilimento where id = _idstab;

-- INSERIMENTO NUOVO INDIRIZZO

idProvincia:= (select cod_provincia::integer||'' from comuni1 where id = _idcomune);
idIndirizzoNuovo=(select id from opu_indirizzo where comune = _idcomune and via ilike _via and cap = _cap and toponimo = _idtoponimo and civico ilike _civico);
raise info 'ID INDIRIZZO TROVATO: %', idIndirizzoNuovo;
if idIndirizzoNuovo is null THEN
insert into opu_indirizzo(nazione, cap, provincia, comune, toponimo, via, civico, latitudine, longitudine) values ('Italia', _cap, idProvincia, _idcomune, _idtoponimo, _via, _civico, _lat, _lon) returning id into idIndirizzoNuovo;
raise info 'ID INDIRIZZO INSERITO: %', idIndirizzoNuovo;
END IF;

-- INSERIMENTO NUOVO STABILIMENTO

insert into opu_stabilimento (entered, modified, entered_by, id_operatore, modified_by, id_asl, id_indirizzo, categoria_rischio, stato, tipo_attivita, tipo_carattere, data_inizio_attivita, data_generazione_numero, notes_hd, note_trasferimento) select dataattuale, dataattuale, _idutente, id_operatore, _idutente, _idasl, idIndirizzoNuovo, null, 0, tipo_attivita, tipo_carattere, data_inizio_attivita, data_generazione_numero, 'INSERITO TRAMITE FUNZIONALITA DI TRASFERIMENTO SEDE OPERATIVA DA UTENTE '||_idutente||' IN DATA '||dataattuale, note_trasferimento from opu_stabilimento where id = _idstab returning id into idstabnuovo;
raise info 'ID STABILIMENTO INSERITO: %', idstabnuovo;

if (idstabnuovo > 0) THEN

	-- CESSAZIONE STABILIMENTO ATTUALE

	update opu_stabilimento set stato=4,  modified=dataattuale, notes_hd=concat_ws(';', notes_hd, 'CESSATO TRAMITE FUNZIONALITA DI TRASFERIMENTO SEDE OPERATIVA DA UTENTE '||_idutente||' IN DATA '||dataattuale) where id = _idstab;
	raise info 'CESSAZIONE STABILIMENTO VECCHIO: %', _idstab;

	IF (_generanuovinumeri) THEN
		select opu_genera_numero_registrazione into numRegistrazioneNuovo from  public_functions.opu_genera_numero_registrazione(idstabnuovo);
		update opu_stabilimento set numero_registrazione = numRegistrazioneNuovo, data_generazione_numero = dataattuale where id = idstabnuovo;
	ELSE
		numRegistrazioneNuovo = numRegistrazioneVecchio;
		update opu_stabilimento set numero_registrazione = numRegistrazioneNuovo where id = idstabnuovo;
	END IF;

	update opu_stabilimento set note_trasferimento = concat_ws(';', note_trasferimento,  'Trasferito da '|| (select concat_ws(' ', s.numero_registrazione, t.description, via, civico, c.nome, a.description) from opu_indirizzo i left join lookup_toponimi t on t.code = i.toponimo left join comuni1 c on c.id = i.comune left join opu_stabilimento s on s.id_indirizzo = i.id left join lookup_site_id a on a.code = s.id_asl where s.id = _idstab) || ' a ' || (select concat_ws(' ', s.numero_registrazione, t.description, via, civico, c.nome, a.description) from opu_indirizzo i left join lookup_toponimi t on t.code = i.toponimo left join comuni1 c on c.id = i.comune left join opu_stabilimento s on s.id_indirizzo = i.id left join lookup_site_id a on a.code = s.id_asl where s.id = idstabnuovo)) where id = idstabnuovo;


	-- INSERIMENTO NUOVE LINEE

	insert into opu_relazione_stabilimento_linee_produttive (id_linea_produttiva, id_stabilimento, stato, data_inizio, modified, modifiedby, numero_registrazione, codice_ufficiale_esistente, codice_nazionale, entered, enteredby, codice_univoco_ml, enabled, primario, note_hd, id_linea_produttiva_old ,id_istanza_pre_trasferimento) select id_linea_produttiva, idstabnuovo, stato, dataattuale, dataattuale, _idutente, replace(numero_registrazione, numRegistrazioneVecchio, numRegistrazioneNuovo), codice_ufficiale_esistente, codice_nazionale, dataattuale, _idutente, codice_univoco_ml, enabled, primario, 'INSERITA TRAMITE FUNZIONALITA DI TRASFERIMENTO SEDE OPERATIVA DA UTENTE '||_idutente||' IN DATA '||dataattuale, id_linea_produttiva_old , id from opu_relazione_stabilimento_linee_produttive where id_stabilimento = _idstab and enabled;
raise info 'INSERITE NUOVE LINEE';

	-- INSERIMENTO LIVELLI AGGIUNTIVI VECCHIE LINEE

insert into master_list_configuratore_livelli_aggiuntivi_values (id_configuratore_livelli_aggiuntivi, id_istanza, checked)
select cv.id_configuratore_livelli_aggiuntivi, r.id, cv.checked 
from master_list_configuratore_livelli_aggiuntivi_values cv left join opu_relazione_stabilimento_linee_produttive r on cv.id_istanza = r.id_istanza_pre_trasferimento
where r.id_stabilimento = idstabnuovo;

	-- INSERIMENTO CAMPI ESTESI VECCHIE LINEE

insert into linee_mobili_fields_value (id_rel_stab_linea, id_linee_mobili_html_fields, valore_campo, indice, enabled, id_utente_inserimento, data_inserimento, note_hd)
select r.id, v.id_linee_mobili_html_fields, v.valore_campo, v.indice, v.enabled, v.id_utente_inserimento, v.data_inserimento, 'INSERITO TRAMITE FUNZIONALITA DI TRASFERIMENTO SEDE OPERATIVA EREDITATO DA ISTANZA '||id_istanza_pre_trasferimento 
from linee_mobili_fields_value v left join opu_relazione_stabilimento_linee_produttive r on v.id_rel_stab_linea = r.id_istanza_pre_trasferimento
where r.id_stabilimento = idstabnuovo;

insert into linee_mobili_fields_value (id_opu_rel_stab_linea, id_linee_mobili_html_fields, valore_campo, indice, enabled, id_utente_inserimento, data_inserimento, note_hd)
select r.id, v.id_linee_mobili_html_fields, v.valore_campo, v.indice, v.enabled, v.id_utente_inserimento, v.data_inserimento, 'INSERITO TRAMITE FUNZIONALITA DI TRASFERIMENTO SEDE OPERATIVA EREDITATO DA ISTANZA '||id_istanza_pre_trasferimento 
from linee_mobili_fields_value v left join opu_relazione_stabilimento_linee_produttive r on v.id_opu_rel_stab_linea = r.id_istanza_pre_trasferimento
where r.id_stabilimento = idstabnuovo;

	-- INSERIMENTO PRODOTTI VECCHIE LINEE

insert into sintesis_prodotti (id_linea, id_prodotto, valore_prodotto, checked_date, unchecked_date, origine)
select r.id, p.id_prodotto, p.valore_prodotto, p.checked_date, p.unchecked_date, p.origine
from sintesis_prodotti p left join opu_relazione_stabilimento_linee_produttive r on p.id_linea = r.id_istanza_pre_trasferimento
where r.id_stabilimento = idstabnuovo and p.origine ='opu';
	
	-- CESSAZIONE LINEE ATTIVE STABILIMENTO ATTUALE

	update opu_relazione_stabilimento_linee_produttive set stato=4, data_fine = dataattuale, modified=dataattuale, note_hd=concat_ws(';', note_hd, 'CESSATA TRAMITE FUNZIONALITA DI TRASFERIMENTO SEDE OPERATIVA DA UTENTE '||_idutente||' IN DATA '||dataattuale) where id_stabilimento = _idstab and stato<>4 and enabled;
raise info 'CESSAZIONE LINEE VECCHIE';

	-- SCRIVO NELLO STORICO

	insert into opu_trasferimento_sede_storico(data_trasferimento, id_utente, id_stabilimento_vecchio, id_stabilimento_nuovo, id_indirizzo_vecchio, id_indirizzo_nuovo, id_asl_vecchia, id_asl_nuova) values (dataattuale, _idutente, _idstab, idstabnuovo, idIndirizzoVecchio, idIndirizzoNuovo, idAslVecchia, _idasl);

	-- AGGIORNO RICERCA PER STABILIMENTI
refresh = (select * from opu_insert_into_ricerche_anagrafiche_old_materializzata(_idstab));
raise info 'AGGIORNATA TABELLA RICERCHE STABILIMENTO VECCHIO: %', _idstab;
refresh = (select * from opu_insert_into_ricerche_anagrafiche_old_materializzata(idstabnuovo));
raise info 'AGGIORNATA TABELLA RICERCHE STABILIMENTO NUOVO: %', idstabnuovo;

	codice=1;
	msg='STABILIMENTO TRASFERITO CON SUCCESSO';

	ELSE

	codice=2;
	msg='STABILIMENTO NON TRASFERITO - ERRORE GENERICO';
	
	END IF;

return codice||';;'||msg||';;'||idstabnuovo;

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public_functions.trasferimento_sede_stabilimento(integer, integer, integer, integer, text, text, text, double precision, double precision, integer, boolean)
  OWNER TO postgres;

  
  
  --- old old
  
  


CREATE OR REPLACE FUNCTION public_functions.trasferimento_sede_stabilimento(_idstab integer, _idasl integer, _idcomune integer, _idtoponimo integer, _via text, _civico text, _cap text, _lat float, _lon float, _idutente integer, _generanuovinumeri boolean)
  RETURNS text AS
$BODY$
DECLARE
codice integer;
msg text;
idstabnuovo integer;
dataattuale timestamp without time zone;
idIndirizzoNuovo integer;
idProvincia text;
idAslVecchia integer;
idIndirizzoVecchio integer;
numRegistrazioneVecchio text;

numRegistrazioneNuovo text;
refresh boolean;
noteTrasferimento text;

BEGIN

codice:= -1;
msg:= '';
idstabnuovo := -1;
dataattuale := (select now());
idIndirizzoNuovo:= -1;
idProvincia:='';

-- SALVO DATI ATTUALI

select id_asl, id_indirizzo, numero_registrazione into idAslVecchia, idIndirizzoVecchio, numRegistrazioneVecchio from opu_stabilimento where id = _idstab;

-- INSERIMENTO NUOVO INDIRIZZO

idProvincia:= (select cod_provincia::integer||'' from comuni1 where id = _idcomune);
idIndirizzoNuovo=(select id from opu_indirizzo where comune = _idcomune and via ilike _via and cap = _cap and toponimo = _idtoponimo and civico ilike _civico);
raise info 'ID INDIRIZZO TROVATO: %', idIndirizzoNuovo;
if idIndirizzoNuovo is null THEN
insert into opu_indirizzo(nazione, cap, provincia, comune, toponimo, via, civico, latitudine, longitudine) values ('Italia', _cap, idProvincia, _idcomune, _idtoponimo, _via, _civico, _lat, _lon) returning id into idIndirizzoNuovo;
raise info 'ID INDIRIZZO INSERITO: %', idIndirizzoNuovo;
END IF;

-- INSERIMENTO NUOVO STABILIMENTO

insert into opu_stabilimento (entered, modified, entered_by, id_operatore, modified_by, id_asl, id_indirizzo, categoria_rischio, stato, tipo_attivita, tipo_carattere, data_inizio_attivita, data_generazione_numero, notes_hd, note_trasferimento) select dataattuale, dataattuale, _idutente, id_operatore, _idutente, _idasl, idIndirizzoNuovo, null, 0, tipo_attivita, tipo_carattere, data_inizio_attivita, data_generazione_numero, 'INSERITO TRAMITE FUNZIONALITA DI TRASFERIMENTO SEDE OPERATIVA DA UTENTE '||_idutente||' IN DATA '||dataattuale, note_trasferimento from opu_stabilimento where id = _idstab returning id into idstabnuovo;
raise info 'ID STABILIMENTO INSERITO: %', idstabnuovo;

if (idstabnuovo > 0) THEN

	-- CESSAZIONE STABILIMENTO ATTUALE

	update opu_stabilimento set stato=4,  modified=dataattuale, notes_hd=concat_ws(';', notes_hd, 'CESSATO TRAMITE FUNZIONALITA DI TRASFERIMENTO SEDE OPERATIVA DA UTENTE '||_idutente||' IN DATA '||dataattuale) where id = _idstab;
	raise info 'CESSAZIONE STABILIMENTO VECCHIO: %', _idstab;

	IF (_generanuovinumeri) THEN
		select opu_genera_numero_registrazione into numRegistrazioneNuovo from  public_functions.opu_genera_numero_registrazione(idstabnuovo);
		update opu_stabilimento set numero_registrazione = numRegistrazioneNuovo, data_generazione_numero = dataattuale where id = idstabnuovo;
	ELSE
		numRegistrazioneNuovo = numRegistrazioneVecchio;
		update opu_stabilimento set numero_registrazione = numRegistrazioneNuovo where id = idstabnuovo;
	END IF;

	update opu_stabilimento set note_trasferimento = concat_ws(';', note_trasferimento,  'Trasferito da '|| (select concat_ws(' ', s.numero_registrazione, t.description, via, civico, c.nome, a.description) from opu_indirizzo i left join lookup_toponimi t on t.code = i.toponimo left join comuni1 c on c.id = i.comune left join opu_stabilimento s on s.id_indirizzo = i.id left join lookup_site_id a on a.code = s.id_asl where s.id = _idstab) || ' a ' || (select concat_ws(' ', s.numero_registrazione, t.description, via, civico, c.nome, a.description) from opu_indirizzo i left join lookup_toponimi t on t.code = i.toponimo left join comuni1 c on c.id = i.comune left join opu_stabilimento s on s.id_indirizzo = i.id left join lookup_site_id a on a.code = s.id_asl where s.id = idstabnuovo)) where id = idstabnuovo;


	-- INSERIMENTO NUOVE LINEE

	insert into opu_relazione_stabilimento_linee_produttive (id_linea_produttiva, id_stabilimento, stato, data_inizio, modified, modifiedby, numero_registrazione, codice_ufficiale_esistente, codice_nazionale, entered, enteredby, codice_univoco_ml, enabled, primario, note_hd, id_linea_produttiva_old ,id_istanza_pre_trasferimento) select id_linea_produttiva, idstabnuovo, 0, dataattuale, dataattuale, _idutente, replace(numero_registrazione, numRegistrazioneVecchio, numRegistrazioneNuovo), codice_ufficiale_esistente, codice_nazionale, dataattuale, _idutente, codice_univoco_ml, enabled, primario, 'INSERITA TRAMITE FUNZIONALITA DI TRASFERIMENTO SEDE OPERATIVA DA UTENTE '||_idutente||' IN DATA '||dataattuale, id_linea_produttiva_old , id from opu_relazione_stabilimento_linee_produttive where id_stabilimento = _idstab and enabled;
raise info 'INSERITE NUOVE LINEE';

	-- INSERIMENTO LIVELLI AGGIUNTIVI VECCHIE LINEE

insert into master_list_configuratore_livelli_aggiuntivi_values (id_configuratore_livelli_aggiuntivi, id_istanza, checked)
select cv.id_configuratore_livelli_aggiuntivi, r.id, cv.checked 
from master_list_configuratore_livelli_aggiuntivi_values cv left join opu_relazione_stabilimento_linee_produttive r on cv.id_istanza = r.id_istanza_pre_trasferimento
where r.id_stabilimento = idstabnuovo;

	-- INSERIMENTO CAMPI ESTESI VECCHIE LINEE

insert into linee_mobili_fields_value (id_rel_stab_linea, id_linee_mobili_html_fields, valore_campo, indice, enabled, id_utente_inserimento, data_inserimento, note_hd)
select r.id, v.id_linee_mobili_html_fields, v.valore_campo, v.indice, v.enabled, v.id_utente_inserimento, v.data_inserimento, 'INSERITO TRAMITE FUNZIONALITA DI TRASFERIMENTO SEDE OPERATIVA EREDITATO DA ISTANZA '||id_istanza_pre_trasferimento 
from linee_mobili_fields_value v left join opu_relazione_stabilimento_linee_produttive r on v.id_rel_stab_linea = r.id_istanza_pre_trasferimento
where r.id_stabilimento = idstabnuovo;

insert into linee_mobili_fields_value (id_opu_rel_stab_linea, id_linee_mobili_html_fields, valore_campo, indice, enabled, id_utente_inserimento, data_inserimento, note_hd)
select r.id, v.id_linee_mobili_html_fields, v.valore_campo, v.indice, v.enabled, v.id_utente_inserimento, v.data_inserimento, 'INSERITO TRAMITE FUNZIONALITA DI TRASFERIMENTO SEDE OPERATIVA EREDITATO DA ISTANZA '||id_istanza_pre_trasferimento 
from linee_mobili_fields_value v left join opu_relazione_stabilimento_linee_produttive r on v.id_opu_rel_stab_linea = r.id_istanza_pre_trasferimento
where r.id_stabilimento = idstabnuovo;

	-- INSERIMENTO PRODOTTI VECCHIE LINEE

insert into sintesis_prodotti (id_linea, id_prodotto, valore_prodotto, checked_date, unchecked_date, origine)
select r.id, p.id_prodotto, p.valore_prodotto, p.checked_date, p.unchecked_date, p.origine
from sintesis_prodotti p left join opu_relazione_stabilimento_linee_produttive r on p.id_linea = r.id_istanza_pre_trasferimento
where r.id_stabilimento = idstabnuovo and p.origine ='opu';
	
	-- CESSAZIONE LINEE ATTIVE STABILIMENTO ATTUALE

	update opu_relazione_stabilimento_linee_produttive set stato=4, data_fine = dataattuale, modified=dataattuale, note_hd=concat_ws(';', note_hd, 'CESSATA TRAMITE FUNZIONALITA DI TRASFERIMENTO SEDE OPERATIVA DA UTENTE '||_idutente||' IN DATA '||dataattuale) where id_stabilimento = _idstab and stato<>4 and enabled;
raise info 'CESSAZIONE LINEE VECCHIE';

	-- SCRIVO NELLO STORICO

	insert into opu_trasferimento_sede_storico(data_trasferimento, id_utente, id_stabilimento_vecchio, id_stabilimento_nuovo, id_indirizzo_vecchio, id_indirizzo_nuovo, id_asl_vecchia, id_asl_nuova) values (dataattuale, _idutente, _idstab, idstabnuovo, idIndirizzoVecchio, idIndirizzoNuovo, idAslVecchia, _idasl);

	-- AGGIORNO RICERCA PER STABILIMENTI
refresh = (select * from opu_insert_into_ricerche_anagrafiche_old_materializzata(_idstab));
raise info 'AGGIORNATA TABELLA RICERCHE STABILIMENTO VECCHIO: %', _idstab;
refresh = (select * from opu_insert_into_ricerche_anagrafiche_old_materializzata(idstabnuovo));
raise info 'AGGIORNATA TABELLA RICERCHE STABILIMENTO NUOVO: %', idstabnuovo;

	codice=1;
	msg='STABILIMENTO TRASFERITO CON SUCCESSO';

	ELSE

	codice=2;
	msg='STABILIMENTO NON TRASFERITO - ERRORE GENERICO';
	
	END IF;

return codice||';;'||msg||';;'||idstabnuovo;

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
  
  
  ---- old
CREATE OR REPLACE FUNCTION public_functions.trasferimento_sede_stabilimento(_idstab integer, _idasl integer, _idcomune integer, _idtoponimo integer, _via text, _civico text, _cap text, _lat float, _lon float, _idutente integer, _generanuovinumeri boolean)
  RETURNS text AS
$BODY$
DECLARE
codice integer;
msg text;
idstabnuovo integer;
dataattuale timestamp without time zone;
idIndirizzoNuovo integer;
idProvincia text;
idAslVecchia integer;
idIndirizzoVecchio integer;
numRegistrazioneVecchio text;

numRegistrazioneNuovo text;
refresh boolean;
noteTrasferimento text;

BEGIN

codice:= -1;
msg:= '';
idstabnuovo := -1;
dataattuale := (select now());
idIndirizzoNuovo:= -1;
idProvincia:='';

-- SALVO DATI ATTUALI

select id_asl, id_indirizzo, numero_registrazione into idAslVecchia, idIndirizzoVecchio, numRegistrazioneVecchio from opu_stabilimento where id = _idstab;

-- INSERIMENTO NUOVO INDIRIZZO

idProvincia:= (select cod_provincia::integer||'' from comuni1 where id = _idcomune);
idIndirizzoNuovo=(select id from opu_indirizzo where comune = _idcomune and via ilike _via and cap = _cap and toponimo = _idtoponimo and civico ilike _civico);
raise info 'ID INDIRIZZO TROVATO: %', idIndirizzoNuovo;
if idIndirizzoNuovo is null THEN
insert into opu_indirizzo(nazione, cap, provincia, comune, toponimo, via, civico, latitudine, longitudine) values ('Italia', _cap, idProvincia, _idcomune, _idtoponimo, _via, _civico, _lat, _lon) returning id into idIndirizzoNuovo;
raise info 'ID INDIRIZZO INSERITO: %', idIndirizzoNuovo;
END IF;

-- INSERIMENTO NUOVO STABILIMENTO

insert into opu_stabilimento (entered, modified, entered_by, id_operatore, modified_by, id_asl, id_indirizzo, categoria_rischio, stato, tipo_attivita, tipo_carattere, data_inizio_attivita, data_generazione_numero, notes_hd, note_trasferimento) select dataattuale, dataattuale, _idutente, id_operatore, _idutente, _idasl, idIndirizzoNuovo, null, 0, tipo_attivita, tipo_carattere, data_inizio_attivita, data_generazione_numero, 'INSERITO TRAMITE FUNZIONALITA DI TRASFERIMENTO SEDE OPERATIVA DA UTENTE '||_idutente||' IN DATA '||dataattuale, note_trasferimento from opu_stabilimento where id = _idstab returning id into idstabnuovo;
raise info 'ID STABILIMENTO INSERITO: %', idstabnuovo;

if (idstabnuovo > 0) THEN

	-- CESSAZIONE STABILIMENTO ATTUALE

	update opu_stabilimento set stato=4,  modified=dataattuale, notes_hd=concat_ws(';', notes_hd, 'CESSATO TRAMITE FUNZIONALITA DI TRASFERIMENTO SEDE OPERATIVA DA UTENTE '||_idutente||' IN DATA '||dataattuale) where id = _idstab;
	raise info 'CESSAZIONE STABILIMENTO VECCHIO: %', _idstab;

	IF (_generanuovinumeri) THEN
		select opu_genera_numero_registrazione into numRegistrazioneNuovo from  public_functions.opu_genera_numero_registrazione(idstabnuovo);
		update opu_stabilimento set numero_registrazione = numRegistrazioneNuovo, data_generazione_numero = dataattuale where id = idstabnuovo;
	ELSE
		numRegistrazioneNuovo = numRegistrazioneVecchio;
		update opu_stabilimento set numero_registrazione = numRegistrazioneNuovo where id = idstabnuovo;
	END IF;

	update opu_stabilimento set note_trasferimento = concat_ws(';', note_trasferimento,  'Trasferito da '|| (select concat_ws(' ', s.numero_registrazione, t.description, via, civico, c.nome, a.description) from opu_indirizzo i left join lookup_toponimi t on t.code = i.toponimo left join comuni1 c on c.id = i.comune left join opu_stabilimento s on s.id_indirizzo = i.id left join lookup_site_id a on a.code = s.id_asl where s.id = _idstab) || ' a ' || (select concat_ws(' ', s.numero_registrazione, t.description, via, civico, c.nome, a.description) from opu_indirizzo i left join lookup_toponimi t on t.code = i.toponimo left join comuni1 c on c.id = i.comune left join opu_stabilimento s on s.id_indirizzo = i.id left join lookup_site_id a on a.code = s.id_asl where s.id = idstabnuovo)) where id = idstabnuovo;


	-- INSERIMENTO NUOVE LINEE

	insert into opu_relazione_stabilimento_linee_produttive (id_linea_produttiva, id_stabilimento, stato, data_inizio, modified, modifiedby, numero_registrazione, codice_ufficiale_esistente, codice_nazionale, entered, enteredby, codice_univoco_ml, enabled, primario, note_hd, id_istanza_pre_trasferimento) select id_linea_produttiva, idstabnuovo, 0, dataattuale, dataattuale, _idutente, replace(numero_registrazione, numRegistrazioneVecchio, numRegistrazioneNuovo), codice_ufficiale_esistente, codice_nazionale, dataattuale, _idutente, codice_univoco_ml, enabled, primario, 'INSERITA TRAMITE FUNZIONALITA DI TRASFERIMENTO SEDE OPERATIVA DA UTENTE '||_idutente||' IN DATA '||dataattuale, id from opu_relazione_stabilimento_linee_produttive where id_stabilimento = _idstab and stato <> 4 and enabled;
raise info 'INSERITE NUOVE LINEE';
	
	-- CESSAZIONE LINEE ATTIVE STABILIMENTO ATTUALE

	update opu_relazione_stabilimento_linee_produttive set stato=4, data_fine = dataattuale, modified=dataattuale, note_hd=concat_ws(';', note_hd, 'CESSATA TRAMITE FUNZIONALITA DI TRASFERIMENTO SEDE OPERATIVA DA UTENTE '||_idutente||' IN DATA '||dataattuale) where id_stabilimento = _idstab and stato<>4 and enabled;
raise info 'CESSAZIONE LINEE VECCHIE';

	-- SCRIVO NELLO STORICO

	insert into opu_trasferimento_sede_storico(data_trasferimento, id_utente, id_stabilimento_vecchio, id_stabilimento_nuovo, id_indirizzo_vecchio, id_indirizzo_nuovo, id_asl_vecchia, id_asl_nuova) values (dataattuale, _idutente, _idstab, idstabnuovo, idIndirizzoVecchio, idIndirizzoNuovo, idAslVecchia, _idasl);

	-- AGGIORNO RICERCA PER STABILIMENTI
refresh = (select * from opu_insert_into_ricerche_anagrafiche_old_materializzata(_idstab));
raise info 'AGGIORNATA TABELLA RICERCHE STABILIMENTO VECCHIO: %', _idstab;
refresh = (select * from opu_insert_into_ricerche_anagrafiche_old_materializzata(idstabnuovo));
raise info 'AGGIORNATA TABELLA RICERCHE STABILIMENTO NUOVO: %', idstabnuovo;

	codice=1;
	msg='STABILIMENTO TRASFERITO CON SUCCESSO';

	ELSE

	codice=2;
	msg='STABILIMENTO NON TRASFERITO - ERRORE GENERICO';
	
	END IF;

return codice||';;'||msg||';;'||idstabnuovo;

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;








-- old
CREATE OR REPLACE FUNCTION public_functions.trasferimento_sede_stabilimento(_idstab integer, _idasl integer, _idcomune integer, _idtoponimo integer, _via text, _civico text, _cap text, _lat float, _lon float, _idutente integer)
  RETURNS text AS
$BODY$
DECLARE
codice integer;
msg text;
idstabnuovo integer;

BEGIN

codice:= -1;
msg:= '';
idstabnuovo := -1;

codice=1;
msg='STABILIMENTO TRASFERITO CON SUCCESSO';
idstabnuovo=_idstab;

return codice||';;'||msg||';;'||idstabnuovo;

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
