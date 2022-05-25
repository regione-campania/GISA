--- cessazione automatica BDU

--bdu
alter table opu_relazione_stabilimento_linee_produttive add column data_fine_presunta boolean default false;


CREATE OR REPLACE FUNCTION public_functions.bdu_cessazione_automatica(
    orgIdC integer,
    dataCessazione timestamp without time zone,
    noteCessazione text)
  RETURNS text AS
$BODY$
DECLARE
idRelStabLp integer;
ragioneSociale text;
idOperatore text;
msgTemp text;
msg text;
BEGIN

msg := '';
idRelStabLp := (select id from opu_relazione_stabilimento_linee_produttive where id = orgIdC);

IF idRelStabLp > 0 THEN

ragioneSociale:= (select ragione_sociale from opu_operatori_denormalizzati_view where id_rel_stab_lp = idRelStabLp);
idOperatore:= (select id_opu_operatore from opu_operatori_denormalizzati_view where id_rel_stab_lp = idRelStabLp);

UPDATE opu_relazione_stabilimento_linee_produttive set data_fine = dataCessazione, data_fine_presunta = true, note_internal_use_only = concat_ws('; CESSAZIONE DA GISA: ', note_internal_use_only, noteCessazione) where id = idRelStabLp;

msgTemp := (select * from public_functions.update_opu_materializato(idOperatore));

msg:= 'Cessato operatore in BDU: ' || ragioneSociale || ';';

ELSE
msg:= 'Nessun operatore da cessare in BDU;';

END IF;

     RETURN msg;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
  
  
  
  --- cessazione VAM
  
  --vam
  alter table clinica add column data_cessazione timestamp without time zone;
alter table clinica add column data_cessazione_presunta boolean;
alter table clinica add column note_cessazione text;
alter table clinica add column note_hd text;


CREATE OR REPLACE FUNCTION public_functions.ricerca_clinica(
    inputNome text,
    inputAsl integer,
    inputStato integer)
  RETURNS text AS
$BODY$
DECLARE
outputJson text; 
BEGIN 


outputJson := (
select array_to_json(array_agg(t))
from (
  select c.id, c.nome, asl.description as asl, COALESCE(to_char(c.data_cessazione, 'dd/MM/yyyy'), '') as data_cessazione from clinica c
left join lookup_asl asl on asl.id = c.asl

where 1=1
and nome ilike '%'||inputNome||'%'
and ((inputAsl>1 and c.asl = inputAsl) or (inputAsl=-1))
and ((inputStato=0 and c.data_cessazione is null) or (inputStato=1 and c.data_cessazione is not null) or (inputStato=-1))
) t
);


return outputJson;
	

END 

$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.ricerca_clinica(text, integer, integer)
  OWNER TO postgres;






CREATE OR REPLACE FUNCTION public_functions.cessazione_clinica(
    inputIdClinica integer,
    inputData text,
    inputNote text)
  RETURNS text AS
$BODY$
DECLARE
inputDataTimestamp timestamp without time zone;
outputJson text; 
BEGIN 

inputDataTimestamp := to_timestamp(inputData,'dd/MM/yyyy');

update clinica set data_cessazione = inputDataTimestamp, data_cessazione_presunta = true, note_cessazione = inputNote, note_hd = 'Cessata tramite dbi in data '||now() where id = inputIdClinica and data_cessazione is null;


outputJson := (
select array_to_json(array_agg(t))
from (
  select c.id, c.nome, asl.description as asl, COALESCE(to_char(c.data_cessazione, 'dd/MM/yyyy'), '') as data_cessazione, note_cessazione, note_hd from clinica c
left join lookup_asl asl on asl.id = c.asl

where 1=1
and c.id = inputIdClinica
) t
);

return outputJson;
	
END 

$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.cessazione_clinica(integer, text, text)
  OWNER TO postgres;
  
  
  
-- Function: public.dbi_get_cliniche_utente()

-- DROP FUNCTION public.dbi_get_cliniche_utente();

CREATE OR REPLACE FUNCTION public.dbi_get_cliniche_utente()
  RETURNS TABLE(asl_id integer, id_clinica integer, nome_clinica text) AS
$BODY$
DECLARE
r RECORD;	
BEGIN
FOR asl_id, id_clinica, nome_clinica
in
select asl, id, concat(case when data_cessazione is not null then '(CESSATA) ' else '' end,  nome) as nome from clinica  where trashed_date is null order by asl,nome
LOOP
        RETURN NEXT;
END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.dbi_get_cliniche_utente()
  OWNER TO postgres;
  
  
  
  --- inserimento clinica VAM
  
  -- gisa
  
insert into permission (category_id, permission, permission_view, description) values (12, 'sincronizza-vam', true, 'SINCRONIZZAZIONI CLINICHE GISA-VAM');
insert into role_permission (role_id, permission_id, role_view) values (1, (select permission_id from permission where permission ilike 'sincronizza-vam'), true);
insert into role_permission (role_id, permission_id, role_view) values (32, (select permission_id from permission where permission ilike 'sincronizza-vam'), true);


-- vam
alter table clinica add column id_stabilimento_gisa integer;


		

CREATE OR REPLACE FUNCTION public_functions.inserimento_clinica(
    inputIdGisa integer,
    inputNome text,
    inputNomeBreve text,
    inputAsl text,
    inputComune text,
    inputIndirizzo text,
    inputEmail text,
    inputTelefono text,
    inputNoteHd text)
  RETURNS text AS
$BODY$
DECLARE
inputIdAsl integer;
inputIdComune integer;
outputIdClinica integer; 
outputMsg text; 
BEGIN 

outputIdClinica := (select id from clinica where id_stabilimento_gisa = inputIdGisa);

IF outputIdClinica >0 THEN

outputMsg := 'Esiste gia'' una clinica in VAM associata a questo stabilimento. Clinica non inserita.';

ELSE

inputIdAsl:= (select id from lookup_asl where description ilike inputAsl);
inputIdComune:= (select id from lookup_comuni where description ilike inputComune);

insert into clinica (nome, nome_breve, asl, comune, indirizzo, email, telefono, entered, note_hd, id_stabilimento_gisa) 
values (inputNome, inputNomeBreve, inputIdAsl, inputIdComune, inputIndirizzo, inputEmail, inputTelefono, now(), inputNoteHd, inputIdGisa);
outputMsg := 'Sincronizzazione effettuata. Clinica inserita in VAM.';

END IF;

return outputMsg;
	
END 

$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

  
  --bdu
  
  --variazione titolarita
  -- Function: public_functions.suap_variazione_titolarita(character varying, character varying, text, text, text, text, timestamp without time zone, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, integer, integer, text, text, text, text)

-- DROP FUNCTION public_functions.suap_variazione_titolarita(character varying, character varying, text, text, text, text, timestamp without time zone, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, integer, integer, text, text, text, text);

CREATE OR REPLACE FUNCTION public_functions.suap_variazione_titolarita(
    partita_iva character varying,
    codice_fiscale_impresa character varying,
    ragione_sociale text,
    nome_rapp_sede_legale_out text,
    cognome_rapp_sede_legale_out text,
    cf_rapp_sede_legale_out text,
    data_nascita_rapp_sede_legale_out timestamp without time zone,
    sesso_out text,
    comune_nascita_rapp_sede_legale_out text,
    sigla_prov_soggfisico_out text,
    comune_residenza_out text,
    indirizzo_rapp_sede_legale_out text,
    civico_residenza_out text,
    cap_residenza text,
    nazione_residenza text,
    sigla_provincia_legale text,
    comune_legale text,
    indirizzo_legale text,
    civico_legale text,
    cap_legale text,
    nazione_legale text,
    sigla_provincia_stab text,
    comune_stab text,
    indirizzo_stab text,
    civico_stab text,
    cap_stab text,
    nazione_stab text,
    id_asl_in integer,
    idutente integer,
    toponimo_rapp_sede_legale text,
    toponimo_legale text,
    toponimo_stab text,
    id_rel_stab_lp_gisa text)
  RETURNS integer AS
$BODY$
DECLARE
id_impresa integer;
id_soggetto_out integer;
id_stab_out integer;
id_stabl_out integer;
esito text;
id_operatore_old_out integer;
BEGIN

id_stab_out := (select id from opu_stabilimento where id_stabilimento_gisa = id_rel_stab_lp_gisa );
id_operatore_old_out := (select id_operatore from opu_stabilimento where id = id_stab_out);
-- 1. soggetto fisico ed indirizzo rappresentante legale che ritorna id_soggetto fisico
id_soggetto_out := (select * from public_functions.insert_soggetto_fisico_bdu(nome_rapp_sede_legale_out,cognome_rapp_sede_legale_out, cf_rapp_sede_legale_out, data_nascita_rapp_sede_legale_out, 
sesso_out,comune_nascita_rapp_sede_legale_out, sigla_prov_soggfisico_out, comune_residenza_out, indirizzo_rapp_sede_legale_out,toponimo_rapp_sede_legale,civico_residenza_out, cap_residenza, nazione_residenza,idutente));
--2 impresa in relazione al soggetto fisico e all'indirizzo restituisce una tupla con id operatore partita_iva, ragione sociale e cf...
id_impresa := (select * from public_functions.suap_insert_impresa_bdu(id_soggetto_out,partita_iva,codice_fiscale_impresa,ragione_sociale,sigla_provincia_legale,comune_legale,indirizzo_legale,toponimo_legale,
civico_legale,cap_legale,nazione_legale));

--aggiornamento id_operatore su opu_stabilimento
update opu_stabilimento set id_operatore  = id_impresa where id = id_stab_out;
--aggiornamento storico

insert into opu_operatore_variazione_titolarita_stabilimenti (
id_stab_bdu ,
id_operatore_old ,
id_operatore_new ,
id_stab_gisa,
data_operazione )
values (id_stab_out,id_operatore_old_out,id_impresa, id_rel_stab_lp_gisa, current_timestamp);


--aggiornamento vista materializzato tramite id_impresa
esito := (select * from public_functions.update_opu_materializato(id_impresa));
--aggiornamento informazioni canile da gisa campi estesi a canili bdu
--if id_stabl_out > 0 then
--  raise INFO '%', id_stabl_out;
--  esito_estesi := (select * from public_functions.aggiorna_info_canile(id_rel_stab_lp_gisa::int, id_stabl_out));
--end if;
return id_impresa;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public_functions.suap_variazione_titolarita(character varying, character varying, text, text, text, text, timestamp without time zone, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, integer, integer, text, text, text, text)
  OWNER TO postgres;

  


