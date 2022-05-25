alter table opu_relazione_stabilimento_linee_produttive add column numero_registrazione_old text;

CREATE OR REPLACE FUNCTION funzioni_hd_per_segnalazioni.scorporamento_linea_da_stabilimento(_idIstanza integer, _numRegistrazione text,_idUtente integer)
  RETURNS text AS
$BODY$
DECLARE
msg text;
idStabilimento integer;
tipoFissoMobile integer;
flagMobile boolean;
numLinee integer;
numLineeIbride integer;

numeroRegistrazione text ;
tipoImpresa int;
tipoAttivita int ;
idComune int ; 
codiceComune text;
codiceProvincia text;

dataAttuale timestamp without time zone;
idStabilimentoNuovo integer;
numTicket integer;
notesHd text;
notesHdOrigine text;
numeroRegistrazionePrecedente text;

BEGIN

msg := '';
dataAttuale := CURRENT_TIMESTAMP;

IF _idIstanza<=0 or _idIstanza is null THEN
IF _numRegistrazione is not null and _numRegistrazione <> '' THEN
--Controllo aggiuntivo per estrarre solo numeri registrazioni linea non duplicati
select rel.id into _idIstanza from opu_relazione_stabilimento_linee_produttive rel where trim(rel.numero_registrazione) ilike trim(_numRegistrazione) and trim(rel.numero_registrazione) not in (select trim(numero_registrazione) from opu_relazione_stabilimento_linee_produttive where id<>rel.id and trim(numero_registrazione) ilike trim(_numRegistrazione) ); 
raise info 'Id istanza recuperato: % %', _idIstanza, _numRegistrazione;
END IF;
END IF;

IF _idIstanza<=0 or _idIstanza is null THEN
msg= 'KO! Errore input istanza. Indicare un id istanza valido al parametro 1 o un numero registrazione valido al parametro 2.';
return msg;
END IF;

-- Controllo esistenza istanza
select id into _idIstanza from opu_relazione_stabilimento_linee_produttive where enabled and id = _idIstanza;

IF _idIstanza<=0 or _idIstanza is null THEN
msg= 'KO! Istanza inesistente.';
return msg;
END IF;

-- Recupero id stabilimento origine
select id_stabilimento into idStabilimento from opu_relazione_stabilimento_linee_produttive where id = _idIstanza;
raise info 'Id Stabilimento: %',idStabilimento;

-- Recupero stabilimento origine 
select tipo_attivita, numero_registrazione  into tipoFissoMobile, numeroRegistrazionePrecedente from opu_stabilimento  where id = idStabilimento;
raise info 'Tipo Fisso/Mobile: %',tipoFissoMobile;

-- Recupero tipologia linea da scorporare
select f.mobile into flagMobile from master_list_flag_linee_attivita f
join ml8_linee_attivita_nuove_materializzata ml8 on ml8.codice_attivita = f.codice_univoco
join opu_relazione_stabilimento_linee_produttive rel on rel.id_linea_produttiva = ml8.id_nuova_linea_attivita
where rel.id = _idIstanza;
raise info 'Flag Mobile: %',flagMobile;

-- Recupero numero linee stabilimento
select count(*) into numLinee from opu_relazione_stabilimento_linee_produttive where id_stabilimento = idStabilimento and enabled;
raise info 'Num Linee: %',numLinee;

-- Recupero tipologia linee stabilimento
select count (*) into numLineeIbride from (select distinct f.mobile, f.fisso from master_list_flag_linee_attivita f
join ml8_linee_attivita_nuove_materializzata ml8 on ml8.codice_attivita = f.codice_univoco
join opu_relazione_stabilimento_linee_produttive rel on rel.id_linea_produttiva = ml8.id_nuova_linea_attivita
where rel.id_stabilimento = idStabilimento and rel.enabled) aa;
raise info 'Num Linee Ibride: %',numLineeIbride;

if tipoFissoMobile <> 1 THEN
msg = concat_ws(';', msg, 'Lo stabilimento non risulta FISSO.');
END IF;

if flagMobile is not true THEN
msg = concat_ws(';', msg, 'La linea non risulta MOBILE.');
END IF;

if numLinee <2 THEN
msg = concat_ws(';', msg, 'Lo stabilimento di origine non avrebbe linee sopravvissute allo scorporamento.');
END IF;


if numLineeIbride <2 THEN
msg = concat_ws(';', msg, 'Lo stabilimento di origine non risulta ibrido e non contiene linee fisse e mobili contemporaneamente.');
END IF;


IF msg <> '' THEN
msg = 'KO! '||msg||'. Si ricordano le condizioni necessarie: linea da scorporare MOBILE, stabilimento di origine FISSO, stabilimento di origine AVENTE PIU'' DI DUE LINEE, stabilimento di origine AVENTE LINEE FISSE E LINEE MOBILI.';
return msg;
END IF;

-- Procedo con lo scorporamento


-- Recupero i dati necessari alla generazione del numero registrazione
tipoImpresa := (select tipo_impresa from opu_operatore where id in (select id_operatore from opu_stabilimento where id = idStabilimento));
tipoAttivita := ( select tipo_attivita from opu_stabilimento where id = idStabilimento);


if tipoImpresa=1
then
	if tipoAttivita=2
		then 
			idComune:=(select ind.comune from opu_indirizzo ind 
				join opu_soggetto_fisico sogg on sogg.indirizzo_id = ind.id
				join opu_rel_operatore_soggetto_fisico rel on rel.id_soggetto_fisico=sogg.id
				where rel.id_operatore in (select id_operatore from opu_stabilimento where id = idStabilimento) );
		else
		if tipoAttivita=1
		then
			idComune:=(select ind.comune from opu_indirizzo ind 
				join opu_stabilimento st on st.id_indirizzo = ind.id
				where st.id=idStabilimento  );
		end if ;
	end if ;
else
	if tipoAttivita=1
		then
			idComune:=(select ind.comune from opu_indirizzo ind 
				join opu_stabilimento st on st.id_indirizzo = ind.id
				where st.id =idStabilimento );
		end if ;
	if tipoAttivita=2
		then
			idComune:=(select ind.comune from opu_indirizzo ind 
				join opu_operatore st on st.id_indirizzo = ind.id
				where st.id in (select id_operatore from opu_stabilimento where id = idStabilimento) );
		end if ;
end if ;			

raise info 'Valore di comune : %',idComune;

select comuni1.cod_comune, lp.cod_provincia into codiceComune, codiceProvincia from comuni1 join lookup_province lp on lp.code=  comuni1.cod_provincia::int where comuni1.id = idComune;
numeroRegistrazione:= (select genera_numero_registrazione from anagrafica.genera_numero_registrazione(codiceComune, codiceProvincia));
raise info 'Numero registrazione generato : %',numeroRegistrazione;

notesHd:= 'Stabilimento scorporato da vecchio Stabilimento id '||idStabilimento||' da utente id '||_idUtente||' con linea istanza '||_idIstanza||' in data '||dataAttuale;

-- Inserisco nuovo stabilimento
insert into opu_stabilimento (entered, entered_by, id_operatore, id_asl, id_indirizzo, riferimento_org_id, cun, id_sinvsa, stato, numero_registrazione, data_generazione_numero, tipo_attivita, tipo_carattere, data_inizio_attivita, data_fine_attivita, notes_hd, numero_registrazione_precedente) 
select dataAttuale, _idUtente, id_operatore, id_asl, id_indirizzo, riferimento_org_id, cun, id_sinvsa, stato, numeroRegistrazione, dataAttuale, 2, tipo_carattere, data_inizio_attivita, data_fine_attivita, notesHd, numeroRegistrazionePrecedente from opu_stabilimento where id = idStabilimento returning id into idStabilimentoNuovo;
raise info 'Stabilimento inserito id: %',idStabilimentoNuovo;

IF idStabilimentoNuovo<=0 THEN
msg= 'KO! Errore inserimento stabilimento.';
return msg;
END IF;

notesHdOrigine:= 'Stabilimento scorporato verso nuovo Stabilimento id '||idStabilimentoNuovo||' da utente id '||_idUtente||' con linea istanza '||_idIstanza||' in data '||dataAttuale;

-- Aggiorno istanza
update opu_relazione_stabilimento_linee_produttive set numero_registrazione_old = numero_registrazione, id_stabilimento = idStabilimentoNuovo, numero_registrazione = numeroRegistrazione||'001', note_internal_use_hd_only = concat_ws(';', note_internal_use_hd_only, 'Linea spostata da stabilimento '||idStabilimento||' a stabilimento '||idStabilimentoNuovo||' dopo scorporamento.') where id = _idIstanza;

-- Sposto CU interessati da vecchio a nuovo stabilimento
WITH rows AS (
update ticket set id_stabilimento = idStabilimentoNuovo, note_internal_use_only = concat_ws(';', 'CU Spostato da stabilimento '||idStabilimento||' a stabilimento '||idStabilimentoNuovo||' dopo scorporamento linea.') where tipologia = 3 and ticketid in (select id_controllo_ufficiale from opu_linee_attivita_controlli_ufficiali where id_linea_attivita = _idIstanza) RETURNING 1
)
SELECT count(*) into numTicket FROM rows;
raise info 'CU aggiornati num: %',numTicket;

-- Aggiorno stabilimento origine
update opu_stabilimento set notes_hd = concat_ws(';', notes_hd, notesHdOrigine) where id = idStabilimento;

-- Refresh ricerca materializzata
msg=concat_ws(';', msg, (select * from refresh_anagrafica(idStabilimento, 'opu')));
raise info 'Refresh ricerche materializzate Stabilimento id: %',idStabilimento;
msg=concat_ws(';', msg, (select * from refresh_anagrafica(idStabilimentoNuovo, 'opu')));
raise info 'Refresh ricerche materializzate Stabilimento id: %',idStabilimentoNuovo;

msg:=concat_ws(';', 'OK! Inserito stabilimento.', 'Id', idStabilimentoNuovo, 'Nuovo numero registrazione', numeroRegistrazione ,msg);

return msg;

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE 
  COST 100;
  
  -- Function: public_functions.suap_stabilimenti_trasportatori_distributori_per_proprietario(text)

-- DROP FUNCTION public_functions.suap_stabilimenti_trasportatori_distributori_per_proprietario(text);

CREATE OR REPLACE FUNCTION public_functions.suap_stabilimenti_trasportatori_distributori_per_proprietario(IN numreg text)
  RETURNS TABLE(partita_iva text, ragione_sociale text, codice_fiscale_proprietario text, nome_proprietario text, cognome_proprietario text, id_stabilimento integer, id_operatore integer, sede_legale text, numero_registrazione text, codice_ufficiale_esistente text, sede_operativa text, id_stato integer) AS
$BODY$
DECLARE

idStabilimento integer;

BEGIN

select o.id_stabilimento into idStabilimento
from opu_operatori_denormalizzati_view o
left join opu_stabilimento st on st.id = o.id_stabilimento
where st.numero_registrazione_precedente ilike trim(numreg) and o.id_stato !=4;

IF idStabilimento is null or idStabilimento<= 0 THEN

select o.id_stabilimento into idStabilimento
from opu_operatori_denormalizzati_view o
where o.numero_registrazione ilike trim(numreg) or o.codice_ufficiale_esistente ilike trim(numreg)
and o.id_stato !=4;

END IF;

FOR partita_iva, ragione_sociale,codice_fiscale_proprietario ,nome_proprietario ,cognome_proprietario ,
   id_stabilimento ,id_operatore  ,sede_legale ,numero_registrazione ,codice_ufficiale_esistente , sede_operativa ,id_stato
IN

select distinct o.partita_iva ,o.ragione_sociale,o.cf_rapp_sede_legale,o.nome_rapp_sede_legale,o.cognome_rapp_sede_legale
,o.id_stabilimento,o.id_opu_operatore,
case when stab_id_attivita = 2 and impresa_id_tipo_impresa = 1 then trim(indirizzo_rapp_sede_legale)
when stab_id_attivita = 2 and impresa_id_tipo_impresa != 1 then trim(indirizzo_sede_legale)
when stab_id_attivita = 1 then trim(indirizzo_sede_legale) end ,
o.numero_registrazione,
o.codice_ufficiale_esistente,
case when stab_id_attivita = 2 and impresa_id_tipo_impresa = 1 then trim(indirizzo_rapp_sede_legale)
when stab_id_attivita = 2 and impresa_id_tipo_impresa != 1 then trim(indirizzo_sede_legale)
when stab_id_attivita = 1 then trim(indirizzo_stab) end as sede_stabilimento,o.id_stato
from opu_operatori_denormalizzati_view o
where o.id_stabilimento = idStabilimento

LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.suap_stabilimenti_trasportatori_distributori_per_proprietario(text)
  OWNER TO postgres;

