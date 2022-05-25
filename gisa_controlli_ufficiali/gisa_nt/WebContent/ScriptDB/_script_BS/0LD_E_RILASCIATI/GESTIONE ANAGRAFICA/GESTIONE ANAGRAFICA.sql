---------------- SCRIPT NUMERI REGISTRAZIONE -------------------

-- Creazione tabella dei progressivi

-- già esistente
CREATE TABLE anagrafica.progressivi_comune_provincia(progressivo integer, cod_comune text, cod_provincia text, entered timestamp without time zone default now());

-- Infasamento dei massimi progressivi attualmente in opu

insert into anagrafica.progressivi_comune_provincia (cod_comune, cod_provincia, progressivo)

select istat, provincia, max(prog::integer) from (

select 
substring(numero_registrazione from 5 for 3) as istat,
substring(numero_registrazione from 8 for 2) as provincia,
max(substring(numero_registrazione from 10 for length(numero_registrazione)) ) as prog	
from opu_stabilimento 
where numero_registrazione is not null  
group by 
substring(numero_registrazione from 5 for 3),
substring(numero_registrazione from 8 for 2)

union

select 
substring(numero_registrazione from 5 for 3) as istat,
substring(numero_registrazione from 8 for 2) as provincia,
max(substring(numero_registrazione from 10 for length(numero_registrazione)) ) as prog	
from suap_richieste_validazioni_prenotazione_numero_registrazione 
where numero_registrazione is not null
group by 
substring(numero_registrazione from 5 for 3),
substring(numero_registrazione from 8 for 2)

union

select 
substring(numero_registrazione from 5 for 3) as istat,
substring(numero_registrazione from 8 for 2) as provincia,
max(substring(numero_registrazione from 10 for length(numero_registrazione)) ) as prog	
from suap_ric_scia_stabilimento 
where numero_registrazione is not null
group by 
substring(numero_registrazione from 5 for 3),
substring(numero_registrazione from 8 for 2)
) aa group by aa.istat, aa.provincia;


-- già esistente

-- Creazione DBI che dati comune e provincia restituisce il numero registrazione

-- Function: anagrafica.genera_numero_registrazione(text, text)

-- DROP FUNCTION anagrafica.genera_numero_registrazione(text, text);

CREATE OR REPLACE FUNCTION anagrafica.genera_numero_registrazione(_codComune text, _codProvincia text)
  RETURNS text AS
$BODY$
DECLARE
_numRegistrazione text ;
_progressivo integer;

BEGIN

_progressivo = 0;

-- calcolo il progressivo per quei codici
select COALESCE(max(progressivo), 0) into _progressivo from anagrafica.progressivi_comune_provincia where cod_comune = _codComune and cod_provincia = _codProvincia;
_progressivo = _progressivo+1;
insert into anagrafica.progressivi_comune_provincia(progressivo, cod_comune, cod_provincia) values (_progressivo, _codComune, _codProvincia);

raise info 'PROGRESSIVO: %', _progressivo;

_numRegistrazione := 'U150' || _codComune || _codProvincia || lpad(_progressivo||'', 6, '0');
raise info 'NUM REGISTRAZIONE: %', _numRegistrazione;

return _numRegistrazione ;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION anagrafica.genera_numero_registrazione(text, text)
  OWNER TO postgres;
  
-- Funzione di anagrafica che recupera l'indirizzo corretto e chiama la dbi della generazione  

CREATE TYPE anagrafica.codici_indirizzo AS
   (cod_comune text,
    cod_provincia text);
ALTER TYPE anagrafica.codici_indirizzo
  OWNER TO postgres;


-- Function: anagrafica.recupera_dati_indirizzo_per_numero_registrazione(integer)

-- DROP FUNCTION anagrafica.recupera_dati_indirizzo_per_numero_registrazione(integer);

CREATE OR REPLACE FUNCTION anagrafica.recupera_dati_indirizzo_per_numero_registrazione(_idStabilimento integer)
  RETURNS anagrafica.codici_indirizzo AS
$BODY$
DECLARE
_idIndirizzo integer;
_idComune integer;
_idProvincia integer;
_codComune text;
_codProvincia text;

_codiciIndirizzo anagrafica.codici_indirizzo;

BEGIN

_idIndirizzo := -1;

-- sede operativa
select relsi.id_indirizzo into _idIndirizzo from anagrafica.rel_stabilimenti_indirizzi relsi where relsi.id_stabilimento = _idStabilimento and relsi.data_scadenza is null and relsi.data_cancellazione is null;
raise info 'CERCO ID INDIRIZZO SEDE OPERATIVA: %', _idIndirizzo;

-- sede legale
IF _idIndirizzo<0 THEN
select relii.id_indirizzo into _idIndirizzo from 
anagrafica.stabilimenti s
join anagrafica.rel_imprese_stabilimenti relis on relis.id_stabilimento = s.id and relis.data_cancellazione is null and relis.data_scadenza is null
join anagrafica.imprese i on i.id = relis.id_impresa and i.data_cancellazione is null
join anagrafica.rel_imprese_indirizzi relii on relii.id_impresa = i.id and relii.data_cancellazione is null and relii.data_scadenza is null 
where s.id = _idStabilimento and s.data_cancellazione is null;
raise info 'CERCO ID INDIRIZZO SEDE LEGALE: %', _idIndirizzo;
END IF;

-- residenza
IF _idIndirizzo<0 THEN
select relsfi.id_indirizzo  into _idIndirizzo from 
anagrafica.stabilimenti s
join anagrafica.rel_imprese_stabilimenti relis on relis.id_stabilimento = s.id and relis.data_cancellazione is null and relis.data_scadenza is null
join anagrafica.imprese i on i.id = relis.id_impresa and i.data_cancellazione is null
join anagrafica.rel_imprese_soggetti_fisici relisf on relisf.id_impresa = i.id and relisf.data_cancellazione is null and relisf.data_scadenza is null
join anagrafica.soggetti_fisici sf on sf.id = relisf.id_soggetto_fisico and sf.data_cancellazione is null
join anagrafica.rel_soggetti_fisici_indirizzi relsfi on relsfi.id_soggetto_fisico = sf.id and relsfi.data_cancellazione is null and relsfi.data_scadenza is null 
where s.id = _idStabilimento  and s.data_cancellazione is null;
raise info 'CERCO ID INDIRIZZO RESIDENZA: %', _idIndirizzo;
END IF;

IF _idIndirizzo>0 THEN
-- se ho trovato un indirizzo, recupero i codici di comune e provincia
select comune into _idComune from anagrafica.indirizzi where id = _idIndirizzo;
select id_provincia into _idProvincia from anagrafica.comuni where id = _idComune;
select cod_comune into _codComune from anagrafica.comuni where id = _idComune;
select cod_provincia into _codProvincia from anagrafica.lookup_province where code = _idProvincia;
ELSE
-- altrimenti uso codici di default
_codComune := '000';
_codProvincia := 'ND';
END IF;

-- Controllo se uno dei due e' null, in quel caso lo metto di default
IF _codComune is null THEN
_codComune := '000';
END IF;
IF _codProvincia is null THEN
_codProvincia := 'ND';
END IF;

raise info 'COD COMUNE: %', _codComune;
raise info 'COD PROVINCIA: %', _codProvincia;

select _codComune, _codProvincia into _codiciIndirizzo;

return _codiciIndirizzo ;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION anagrafica.recupera_dati_indirizzo_per_numero_registrazione(integer)
  OWNER TO postgres;

  -- dbi per aggiornamento del numero registrazione
  
  
CREATE OR REPLACE FUNCTION anagrafica.anagrafica_inserisci_numero_registrazione_stabilimento(
    _idstabilimento integer)
  RETURNS text AS
$BODY$

  DECLARE ret_num_registrazione text;
  _codComune text;
  _codProvincia text;

BEGIN

select cod_comune, cod_provincia into _codComune, _codProvincia from anagrafica.recupera_dati_indirizzo_per_numero_registrazione(_idstabilimento);
select genera_numero_registrazione into ret_num_registrazione from anagrafica.genera_numero_registrazione(_codComune, _codProvincia);
update anagrafica.stabilimenti set numero_registrazione = ret_num_registrazione where id = _idstabilimento and data_cancellazione is null ;
 RETURN ret_num_registrazione;

  
  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION anagrafica.anagrafica_inserisci_numero_registrazione_stabilimento(integer)
  OWNER TO postgres;
  
  
  
  ------------- POST IMPORT MASSIVO DEI PRIVATI
---LO FA ALESSANDRO
  -- prepopolazione numeri registrazione post import
  -- NOTA: A QUESTO PUNTO IN SVILUPPO CI SONO INDIRIZZI VUOTI CHE SARANNO GENERATI CON 000 E ND
  
  select id, 'select * from anagrafica.anagrafica_inserisci_numero_registrazione_stabilimento('||id||');' from anagrafica.stabilimenti;
  
   select 'update anagrafica.rel_stabilimenti_linee_attivita set numero_registrazione = ''' || stab.numero_registrazione || '001'' where id = '||rel.id||';',
rel.id,stab.numero_registrazione
from anagrafica.rel_stabilimenti_linee_attivita rel
join anagrafica.stabilimenti stab on stab.id = rel.id_stabilimento;
--- FINE LO FA ALESSANDRO


---------------- AGGIORNO GLI ID LINEA ATTIVITA NULL  -------------------

select 'update anagrafica.rel_stabilimenti_linee_attivita set id_attivita = ' || l.id_nuova_linea_attivita || ' where id = ' || rel.id ||'; ', rel.* from anagrafica.rel_stabilimenti_linee_attivita rel left join ml8_linee_attivita_nuove_materializzata l on l.codice = rel.codice_univoco
where rel.id_attivita is null and rel.data_cancellazione is null


  ------------- IMPORT CU PRIVATI ----------------


-- import cu privati

  select m.riferimento_id, s.alt_id,
'update ticket set alt_id = '||s.alt_id||', org_id_old = org_id, org_id = null, note_internal_use_only = concat_ws('';'', note_internal_use_only, ''Import massivo PRIVATI. CU spostato da vecchia anagrafica org_id='||m.riferimento_id||' a nuova anagrafica alt_id= '||s.alt_id||''') where org_id = '||m.riferimento_id||';'
from anagrafica.anagrafica_mappatura_old_new m
left join anagrafica.stabilimenti s on m.id_stabilimento_new = s.id


select 'insert into anagrafica.linee_attivita_controlli_ufficiali (id_controllo_ufficiale , id_linea_attivita, note) values ('|| t.ticketid ||', ' || rel.id ||', ''Import massivo PRIVATI. Relazione generata automaticamente.'');'
from 
anagrafica.anagrafica_mappatura_old_new m
left join anagrafica.stabilimenti s on s.id = m.id_stabilimento_new
left join anagrafica.rel_stabilimenti_linee_attivita rel on rel.id_stabilimento = s.id and rel.data_scadenza is null and rel.data_cancellazione is null
left join ticket t on t.alt_id = s.alt_id
where t.provvedimenti_prescrittivi = 4


-- OPU: la dbi di genera numero usa la stessa di anagrafica

-- OPU: la dbi di genera numero usa la stessa di anagrafica

-- Function: public_functions.opu_genera_numero_registrazione(integer)

-- DROP FUNCTION public_functions.opu_genera_numero_registrazione(integer);

CREATE OR REPLACE FUNCTION public_functions.opu_genera_numero_registrazione(idstabilimento integer)
  RETURNS text AS
$BODY$
DECLARE
numeroRegistrazione text ;
tipoimpresa int;
tipoattivita int ;
comune int ; 
_codComune text;
_codProvincia text;
BEGIN

tipoimpresa := (select tipo_impresa from opu_operatore where id in (select id_operatore from opu_stabilimento where id = idStabilimento));
tipoattivita := ( select tipo_attivita from opu_stabilimento where id = idStabilimento);


if tipoimpresa=1
then
	if tipoattivita=2
		then 
			comune:=(select ind.comune from opu_indirizzo ind 
				join opu_soggetto_fisico sogg on sogg.indirizzo_id = ind.id
				join opu_rel_operatore_soggetto_fisico rel on rel.id_soggetto_fisico=sogg.id
				where rel.id_operatore in (select id_operatore from opu_stabilimento where id = idStabilimento) );
		else
		if tipoattivita=1
		then
			comune:=(select ind.comune from opu_indirizzo ind 
				join opu_stabilimento st on st.id_indirizzo = ind.id
				where st.id=idStabilimento  );
		end if ;
	end if ;
else
	if tipoattivita=1
		then
			comune:=(select ind.comune from opu_indirizzo ind 
				join opu_stabilimento st on st.id_indirizzo = ind.id
				where st.id =idStabilimento );
		end if ;
	if tipoattivita=2
		then
			comune:=(select ind.comune from opu_indirizzo ind 
				join opu_operatore st on st.id_indirizzo = ind.id
				where st.id in (select id_operatore from opu_stabilimento where id = idStabilimento) );
		end if ;
end if ;			

raise info 'Valore di comune : %',comune;

select comuni1.cod_comune, lp.cod_provincia into _codComune, _codProvincia from comuni1 join lookup_province lp on lp.code=  comuni1.cod_provincia::int where comuni1.id = comune;
numeroRegistrazione:= (select genera_numero_registrazione from anagrafica.genera_numero_registrazione(_codComune, _codProvincia));

return numeroRegistrazione ;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public_functions.opu_genera_numero_registrazione(integer)
  OWNER TO postgres;

  
  -- Function: public_functions.suap_genera_numero_registrazione(integer, text)

-- DROP FUNCTION public_functions.suap_genera_numero_registrazione(integer, text);

CREATE OR REPLACE FUNCTION public_functions.suap_genera_numero_registrazione(
    idrichiesta integer,
    modalita text)
  RETURNS text AS
$BODY$
DECLARE
numeroRegistrazione text ;
tipoimpresa int;
tipoattivita int ;
comune int ; 
idStabilimento int ;
_codComune text;
_codProvincia text;
BEGIN

tipoimpresa := (select tipo_impresa from suap_ric_scia_operatore where id = idrichiesta);
tipoattivita := ( select tipo_attivita from suap_ric_scia_stabilimento where id_operatore = idrichiesta);
idStabilimento := ( select id from suap_ric_scia_stabilimento where id_operatore = idrichiesta);


if tipoimpresa=1
then
	if tipoattivita=2 or tipoattivita=3
		then 
			comune:=(select ind.comune from opu_indirizzo ind 
				join suap_ric_scia_soggetto_fisico sogg on sogg.indirizzo_id = ind.id
				join suap_ric_scia_rel_operatore_soggetto_fisico rel on rel.id_soggetto_fisico=sogg.id
				where rel.id_operatore =idrichiesta );
		else
		if tipoattivita=1
		then
			comune:=(select ind.comune from opu_indirizzo ind 
				join suap_ric_scia_stabilimento st on st.id_indirizzo = ind.id
				where st.id_operatore =idrichiesta );
		end if ;
	end if ;
else
	if tipoattivita=1
		then
			comune:=(select ind.comune from opu_indirizzo ind 
				join suap_ric_scia_stabilimento st on st.id_indirizzo = ind.id
				where st.id_operatore =idrichiesta );
		end if ;
	if tipoattivita=2 or tipoattivita=3
		then
			comune:=(select ind.comune from opu_indirizzo ind 
				join suap_ric_scia_operatore st on st.id_indirizzo = ind.id
				where st.id =idrichiesta );
		end if ;
end if ;			

raise info 'Valore di comune : %',comune;
if modalita ilike 'r'
then
numeroRegistrazione :=(select numero_registrazione from suap_richieste_validazioni_prenotazione_numero_registrazione where id_richiesta = idrichiesta);

if numeroRegistrazione is not null
then 
return  numeroRegistrazione ;
else
numeroRegistrazione :=(select numero_registrazione from suap_ric_scia_stabilimento where id_operatore = idrichiesta);
if numeroRegistrazione is not null then 
return numeroRegistrazione ;
else

select comuni1.cod_comune, lp.cod_provincia into _codComune, _codProvincia from comuni1 join lookup_province lp on lp.code=  comuni1.cod_provincia::int where comuni1.id = comune;
numeroRegistrazione:= (select genera_numero_registrazione from anagrafica.genera_numero_registrazione(_codComune, _codProvincia));

insert into suap_richieste_validazioni_prenotazione_numero_registrazione (id_richiesta,numero_registrazione)  
values
(
 idrichiesta , numeroRegistrazione
);


return numeroRegistrazione ;
end if ;
end if ;
end if ;

if modalita ilike 'w'
then 
numeroRegistrazione :=(select numero_registrazione from suap_ric_scia_stabilimento where id_operatore = idrichiesta);



if numeroRegistrazione is null
then 

numeroRegistrazione :=(select numero_registrazione from suap_richieste_validazioni_prenotazione_numero_registrazione where id_richiesta = idrichiesta);
if numeroRegistrazione is not null then
update suap_ric_scia_stabilimento set numero_registrazione = numeroRegistrazione  where id_operatore = idrichiesta ;

else
select comuni1.cod_comune, lp.cod_provincia into _codComune, _codProvincia from comuni1 join lookup_province lp on lp.code=  comuni1.cod_provincia::int where comuni1.id = comune;
numeroRegistrazione:= (select genera_numero_registrazione from anagrafica.genera_numero_registrazione(_codComune, _codProvincia));
update suap_ric_scia_stabilimento set numero_registrazione = numeroRegistrazione  where id_operatore = idrichiesta ;

end if ;
end if ;
end if ;
return numeroRegistrazione ;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public_functions.suap_genera_numero_registrazione(integer, text)
  OWNER TO postgres;

  
  --Gestione schede centralizzate
  
  update lookup_tipo_scheda_operatore set description ='Operatori Privati (Nuova Anagrafica)' where code = 35;

select * from aggiorna_scheda_centralizzata(35)


---------------- SCRIPT RICERCA GLOBALE -------------------

-- ricerca globale

-- View: public.view_globale_trashed_no_trashed_minimale

-- DROP VIEW public.view_globale_trashed_no_trashed_minimale;

CREATE OR REPLACE VIEW public.view_globale_trashed_no_trashed_minimale AS 
 SELECT DISTINCT controllo.num_verbale,
    controllo.orgidt,
    controllo.id_stabilimento,
    controllo.id_apiario,
    controllo.alt_id,
    controllo.ticketid,
    controllo.identificativo,
    controllo.id_nc,
    controllo.tipo_attivita,
    controllo.data_cancellazione_attivita,
    controllo.id_cu,
    controllo.tipologia_campioni,
    controllo.motivazione_piano_campione,
    controllo.motivazione_campione,
    controllo.tipo_piano_monitoraggio,
    controllo.sottopiano,
    controllo.esito,
    controllo.analita,
    controllo.matrice,
    controllo.data_inizio_controllo,
    controllo.data_chiusura_controllo,
    controllo.anno_chiusura,
    anagrafica.riferimento_id AS org_id,
    anagrafica.ragione_sociale,
    anagrafica.asl_rif,
    anagrafica.asl,
    anagrafica.tipologia_operatore AS tipologia,
    lto.description AS tipologia_operatore,
    NULL::timestamp without time zone AS data_cancellazione_operatore
   FROM ( SELECT
                CASE
                    WHEN b.barcode IS NOT NULL AND t.location IS NOT NULL THEN (t.location::text || ' - '::text) || b.barcode::text
                    WHEN b.barcode IS NULL THEN t.location::text
                    ELSE NULL::text
                END AS num_verbale,
            t.org_id AS orgidt,
            t.id_stabilimento,
            t.id_apiario,
            t.alt_id,
            t.ticketid,
            t.identificativo,
            t.id_nonconformita AS id_nc,
            t.tipologia AS tipo_attivita,
            t.trashed_date AS data_cancellazione_attivita,
                CASE
                    WHEN t.tipologia = 1 OR t.tipologia = 9 OR t.tipologia = 15 THEN ( SELECT tt.id_controllo_ufficiale
                       FROM ticket tt
                      WHERE tt.ticketid = t.id_nonconformita)
                    ELSE t.id_controllo_ufficiale
                END AS id_cu,
                CASE
                    WHEN t.tipologia = 1 THEN 'Sanzioni'::text
                    WHEN t.tipologia = 2 THEN 'Campioni'::text
                    WHEN t.tipologia = 6 THEN 'Notizie di reato'::text
                    WHEN t.tipologia = 7 THEN 'Tamponi'::text
                    WHEN t.tipologia = 8 THEN 'Non conformitÃ '::text
                    WHEN t.tipologia = 9 THEN 'Sequestri'::text
                    WHEN t.tipologia = 15 THEN 'Follow up'::text
                    WHEN t.tipologia = 3 THEN 'Controlli Ufficiali'::text
                    ELSE 'Altro'::text
                END AS tipologia_campioni,
            t.motivazione_piano_campione,
                CASE
                    WHEN lti.code = 2 THEN lpiano.description::text::character varying
                    ELSE lti.description
                END AS motivazione_campione,
                CASE
                    WHEN lpianopadre.code > 0 THEN lpianopadre.description
                    ELSE lpiano.description
                END AS tipo_piano_monitoraggio,
            lpiano.description AS sottopiano,
                CASE
                    WHEN t.sanzioni_amministrative > 0 AND analiti.esito_id IS NULL THEN esito.description::text
                    WHEN analiti.esito_id > 0 THEN esitonew.description::text
                    WHEN t.tipologia = 2 AND t.sanzioni_amministrative < 0 AND analiti.esito_id < 0 THEN 'Da Attendere'::text
                    ELSE 'N.D'::text
                END AS esito,
            analiti.cammino AS analita,
            matrici.cammino AS matrice,
            to_date(t.assigned_date::text, 'yyyy/mm/dd'::text) AS data_inizio_controllo,
            to_date(t.closed::text, 'yyyy/mm/dd'::text) AS data_chiusura_controllo,
                CASE
                    WHEN t.closed IS NULL THEN 'N.D.'::text
                    ELSE date_part('year'::text, t.closed)::text
                END AS anno_chiusura
           FROM ticket t
             LEFT JOIN barcode_osa b ON b.id_campione::text = t.ticketid::text
             LEFT JOIN analiti_campioni analiti ON analiti.id_campione = t.ticketid
             LEFT JOIN matrici_campioni matrici ON matrici.id_campione = t.ticketid
             LEFT JOIN lookup_tipo_ispezione lti ON lti.code = t.motivazione_campione
             LEFT JOIN lookup_piano_monitoraggio lpiano ON lpiano.code = t.motivazione_piano_campione
             LEFT JOIN lookup_piano_monitoraggio lpianopadre ON lpianopadre.code = lpiano.id_padre
             LEFT JOIN lookup_esito_campione esito ON t.sanzioni_amministrative = esito.code
             LEFT JOIN lookup_esito_campione esitonew ON analiti.esito_id = esitonew.code) controllo
     JOIN ( SELECT ricerche_anagrafiche_old_materializzata.riferimento_id,
            ricerche_anagrafiche_old_materializzata.riferimento_id_nome,
            ricerche_anagrafiche_old_materializzata.riferimento_id_nome_col,
            ricerche_anagrafiche_old_materializzata.riferimento_id_nome_tab,
            ricerche_anagrafiche_old_materializzata.data_inserimento,
            ricerche_anagrafiche_old_materializzata.ragione_sociale,
            ricerche_anagrafiche_old_materializzata.asl_rif,
            ricerche_anagrafiche_old_materializzata.asl,
            ricerche_anagrafiche_old_materializzata.codice_fiscale,
            ricerche_anagrafiche_old_materializzata.codice_fiscale_rappresentante,
            ricerche_anagrafiche_old_materializzata.partita_iva,
            ricerche_anagrafiche_old_materializzata.categoria_rischio,
            ricerche_anagrafiche_old_materializzata.prossimo_controllo,
            ricerche_anagrafiche_old_materializzata.num_riconoscimento,
            ricerche_anagrafiche_old_materializzata.n_reg,
            ricerche_anagrafiche_old_materializzata.n_linea,
            ricerche_anagrafiche_old_materializzata.nominativo_rappresentante,
            ricerche_anagrafiche_old_materializzata.tipo_attivita_descrizione,
            ricerche_anagrafiche_old_materializzata.tipo_attivita,
            ricerche_anagrafiche_old_materializzata.data_inizio_attivita,
            ricerche_anagrafiche_old_materializzata.data_fine_attivita,
            ricerche_anagrafiche_old_materializzata.stato,
            ricerche_anagrafiche_old_materializzata.id_stato,
            ricerche_anagrafiche_old_materializzata.comune,
            ricerche_anagrafiche_old_materializzata.provincia_stab,
            ricerche_anagrafiche_old_materializzata.indirizzo,
            ricerche_anagrafiche_old_materializzata.cap_stab,
            ricerche_anagrafiche_old_materializzata.comune_leg,
            ricerche_anagrafiche_old_materializzata.provincia_leg,
            ricerche_anagrafiche_old_materializzata.indirizzo_leg,
            ricerche_anagrafiche_old_materializzata.cap_leg,
            ricerche_anagrafiche_old_materializzata.macroarea,
            ricerche_anagrafiche_old_materializzata.aggregazione,
            ricerche_anagrafiche_old_materializzata.attivita,
            ricerche_anagrafiche_old_materializzata.path_attivita_completo,
            ricerche_anagrafiche_old_materializzata.gestione_masterlist,
            ricerche_anagrafiche_old_materializzata.norma,
            ricerche_anagrafiche_old_materializzata.id_norma,
            ricerche_anagrafiche_old_materializzata.tipologia_operatore,
            ricerche_anagrafiche_old_materializzata.targa,
            ricerche_anagrafiche_old_materializzata.tipo_ricerca_anagrafica,
            ricerche_anagrafiche_old_materializzata.color,
            ricerche_anagrafiche_old_materializzata.n_reg_old,
            ricerche_anagrafiche_old_materializzata.id_tipo_linea_reg_ric,
            ricerche_anagrafiche_old_materializzata.id_controllo_ultima_categorizzazione,
            ricerche_anagrafiche_old_materializzata.id_linea
           FROM ricerche_anagrafiche_old_materializzata) anagrafica ON (controllo.orgidt = anagrafica.riferimento_id AND anagrafica.riferimento_id_nome_tab = 'organization'::text) OR (controllo.id_stabilimento = anagrafica.riferimento_id AND anagrafica.riferimento_id_nome_tab = 'opu_stabilimento'::text) OR (controllo.id_apiario = anagrafica.riferimento_id AND anagrafica.riferimento_id_nome_tab = 'apicoltura_apiari'::text) OR (controllo.alt_id = anagrafica.riferimento_id AND anagrafica.riferimento_id_nome_tab = 'suap_ric_scia_stabilimento'::text) OR (controllo.alt_id = anagrafica.riferimento_id AND anagrafica.riferimento_id_nome_tab = 'sintesis_stabilimento'::text) OR (controllo.alt_id = anagrafica.riferimento_id AND anagrafica.riferimento_id_nome_tab = 'stabilimenti'::text)
     LEFT JOIN lookup_tipologia_operatore lto ON lto.code = anagrafica.tipologia_operatore;

ALTER TABLE public.view_globale_trashed_no_trashed_minimale
  OWNER TO postgres;
  
---------------- SCRIPT DIGEMON  -------------------
  
  -- Function: public_functions.dbi_get_controlli_ufficiali_eseguiti_anagraficanew(timestamp without time zone, timestamp without time zone)

-- DROP FUNCTION public_functions.dbi_get_controlli_ufficiali_eseguiti_anagraficanew(timestamp without time zone, timestamp without time zone);

CREATE OR REPLACE FUNCTION public_functions.dbi_get_controlli_ufficiali_eseguiti_anagraficanew(
    IN data_1 timestamp without time zone,
    IN data_2 timestamp without time zone)
  RETURNS TABLE(id_controllo_ufficiale integer, riferimento_id integer, riferimento_id_nome text, riferimento_id_nome_col text, riferimento_id_nome_tab text, id_asl integer, asl character varying, tipo_controllo character varying, tipo_ispezione_o_audit text, tipo_piano_monitoraggio text, id_piano integer, id_attivita integer, tipo_controllo_bpi text, tipo_controllo_haccp text, oggetto_del_controllo text, punteggio text, data_inizio_controllo date, anno_controllo text, data_chiusura_controllo date, aggiornata_cat_controllo text, categoria_rischio integer, prossimo_controllo timestamp with time zone, tipo_checklist text, linea_attivita_sottoposta_a_controllo text, unita_operativa text, id_struttura_uo integer, supervisionato_in_data timestamp without time zone, supervisionato_da character varying, supervisione_note text, congruo_supervisione text, note text, tipo_piano_monitoraggio_old text, codice_interno_univoco_uo integer, codice_interno_piano text, area_appartenenza_uo text, id_linea_controllata integer) AS
$BODY$
DECLARE
	r RECORD;
	 	
BEGIN
		FOR 
		id_controllo_ufficiale,
		riferimento_id,
		riferimento_id_nome,
		riferimento_id_nome_col,
		riferimento_id_nome_tab,
		id_asl,  
		asl , 
		tipo_controllo  , 
		tipo_ispezione_o_audit , 
		tipo_piano_monitoraggio ,
		id_piano, id_attivita,
		tipo_controllo_bpi , tipo_controllo_haccp , 
		oggetto_del_controllo , punteggio , 
		data_inizio_controllo , anno_controllo,  data_chiusura_controllo, aggiornata_cat_controllo, categoria_rischio, prossimo_controllo, 
		tipo_checklist, 
		linea_attivita_sottoposta_a_controllo,
		unita_operativa, id_struttura_uo, supervisionato_in_data, supervisionato_da, supervisione_note, congruo_supervisione,note,  
		tipo_piano_monitoraggio_old,
		codice_interno_univoco_uo,codice_interno_piano,
		area_appartenenza_uo,id_linea_controllata
		in
		
		select t.ticketid,
		t.alt_id AS riferimento_id,
             'altId'::text AS riferimento_id_nome,
            'alt_id'::text AS riferimento_id_nome_col,
            'stabilimenti'::text AS riferimento_id_nome_tab,
            asl.code AS id_asl,
            asl.description AS asl,
            ltc.description AS tipo_controllo,

    CASE
                    WHEN tcu.audit_tipo > 0 AND tcu.tipo_audit <= 0 THEN lat.description::text
                    WHEN tcu.tipo_audit > 0 THEN (lat.description::text || ' - '::text) || lta.description::text
                    WHEN tcu.tipoispezione > 0 AND tcu.pianomonitoraggio > 0 then  (CASE WHEN dp.tipo_attivita_piano ilike '%attivit%' THEN 'ATTIVITA ' ELSE 'PIANO ' END ) || dp.alias_attivita || ' ' || dp.descrizione_attivita
                    WHEN tcu.tipoispezione > 0 AND tcu.pianomonitoraggio <= 0 AND tcu.tipoispezione <> 89 AND tcu.tipoispezione <> 2 
                    THEN (CASE WHEN da.tipo_attivita_piano ilike '%attivit%' THEN 'ATTIVITA ' ELSE 'PIANO ' END ) || da.alias_attivita || ' ' || da.descrizione_attivita
                    ELSE 'N.D'::text
                END AS tipo_ispezione_o_audit,
                CASE
                    WHEN tcu.tipoispezione > 0 AND lti.codice_interno !~~* '2a'::text THEN concat_ws(': ',da.alias_indicatore,da.descrizione_indicatore)
                    WHEN lti.codice_interno ~~* '2A'::text AND t.provvedimenti_prescrittivi = 4 THEN concat_ws(': ',dp.alias_indicatore,dp.descrizione_indicatore)
                    ELSE 'N.D'::text
                END AS tipo_piano_monitoraggio,
                dp.id_fisico_indicatore AS id_piano,
                case when dp.id_fisico_indicatore > 0 then null
                else da.id_fisico_indicatore
                end as id_attivita,
            CASE
            WHEN tcu.tipo_audit = 2 AND t.provvedimenti_prescrittivi = 3 THEN 'BPI-'::text || lbpi.description::text
            ELSE 'N.D'::text
        END AS tipo_controllo_bpi,
        CASE
            WHEN tcu.tipo_audit = 3 AND t.provvedimenti_prescrittivi = 3 THEN 'HACCP-'::text || lhaccp.description::text
            ELSE 'N.D'::text
        END AS tipo_controllo_haccp,
               CASE
            WHEN oggcu.ispezione > 0 THEN (lim.description_old::text || ': '::text) || lisp.description::text
            ELSE 'N.D'::text
        END  AS oggetto_del_controllo,
                CASE
                    WHEN t.punteggio < 3 THEN 'Favorevole'::text
                    WHEN t.punteggio >= 3 THEN 'Non Favorevole'::text
                    ELSE 'N.D.'::text
                END AS punteggio,
            to_date(t.assigned_date::text, 'yyyy/mm/dd'::text) AS data_inizio_controllo,
                CASE
                    WHEN t.assigned_date IS NULL THEN 'N.D.'::text
                    ELSE date_part('year'::text, t.assigned_date)::text
                END AS anno_controllo,
            to_date(t.closed::text, 'yyyy/mm/dd'::text) AS data_chiusura_controllo,
                CASE
                    WHEN t.isaggiornata_categoria = true AND t.provvedimenti_prescrittivi = 5 THEN 'CONTROLLO CATEGORIZZATO'::text
                    WHEN t.isaggiornata_categoria = false AND t.provvedimenti_prescrittivi = 5 THEN 'CONTROLLO NON CATEGORIZZATO'::text
                    WHEN t.provvedimenti_prescrittivi <> 5 THEN 'NON PREVISTA CATEGORIZZAZIONE'::text
                    ELSE NULL::text
                END AS aggiornata_cat_controllo,
            t.categoria_rischio,
            t.data_prossimo_controllo,
                CASE
                    WHEN audit.c IS NOT NULL and audit.c = 'T' THEN 'Temporanea'
                    WHEN audit.c IS NOT NULL and audit.c = 'D' THEN 'Definitiva'
                    WHEN (audit.c is null or audit.c = '') and audit.id_controllo is not null THEN 'Presenti più checklist'
                    ELSE 'Non Presente'::text
                END AS tipo_checklist,
                   (((
                CASE
                    WHEN lattcu.macroarea IS NOT NULL THEN lattcu.macroarea
                    ELSE ''::text
                END || '|'::text) ||
                CASE
                    WHEN lattcu.aggregazione IS NOT NULL THEN lattcu.aggregazione
                    ELSE ''::text
                END) || '|'::text) || lattcu.attivita AS linea_attivita_sottoposta_a_controllo,
                CASE
                    WHEN t.provvedimenti_prescrittivi = 4 AND (pp.n_livello <= 0 OR pp.n_livello IS NULL) THEN (
                    CASE
                        WHEN pp.descrizione_struttura IS NOT NULL THEN pp.descrizione_struttura
                        ELSE ''::character varying
                    END::text || '-'::text) || n.descrizione_struttura::text
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 1 THEN (
                    CASE
                        WHEN pp.descrizione_struttura IS NOT NULL THEN pp.descrizione_struttura::text
                        ELSE ''::text
                    END || '-'::text) || n.descrizione_struttura::text
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 2 THEN (
                    CASE
                        WHEN pp.descrizione_struttura IS NOT NULL THEN pp.descrizione_struttura::text
                        ELSE ''::text
                    END || '-'::text) || n.descrizione_struttura::text
                    WHEN t.provvedimenti_prescrittivi <> 4 AND (ppp.n_livello <= 0 OR ppp.n_livello IS NULL) THEN (
                    CASE
                        WHEN ppp.descrizione_struttura IS NOT NULL THEN ppp.descrizione_struttura
                        ELSE ''::character varying
                    END::text || '-'::text) || nn.descrizione_struttura::text
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 1 THEN (
                    CASE
                        WHEN ppp.descrizione_struttura IS NOT NULL THEN ppp.descrizione_struttura
                        ELSE ''::character varying
                    END::text || '-'::text) || nn.descrizione_struttura::text
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 2 THEN (
                    CASE
                        WHEN ppp.descrizione_struttura IS NOT NULL THEN ppp.descrizione_struttura
                        ELSE ''::character varying
                    END::text || '-'::text) || nn.descrizione_struttura::text
                    ELSE NULL::text
                END AS unita_operativa,
               
                CASE
                    WHEN t.provvedimenti_prescrittivi = 4 AND (pp.n_livello <= 0 OR pp.n_livello IS NULL) THEN n.id
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 1 THEN n.id
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 2 THEN n.id
                    WHEN t.provvedimenti_prescrittivi <> 4 AND (ppp.n_livello <= 0 OR ppp.n_livello IS NULL) THEN nn.id
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 1 THEN nn.id
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 2 THEN nn.id
                    ELSE '-1'::integer
                END AS id_struttura_uo,
            t.supervisionato_in_data,
                CASE
                    WHEN c.namelast IS NOT NULL AND c.namefirst IS NOT NULL THEN (c.namelast::text || c.namefirst::text)::character varying
                    WHEN c.namelast IS NOT NULL AND c.namefirst IS NULL THEN c.namelast
                    ELSE c.namefirst
                END AS supervisionato_da,
            t.supervisione_note,
                CASE
                    WHEN t.supervisione_flag_congruo THEN 'SI'::text
                    WHEN t.supervisione_flag_congruo = false THEN 'NO'::text
                    ELSE 'N.D.'::text
                END AS congruo_supervisione,
            t.problem AS note,
 CASE
                    WHEN lti.codice_interno ~~* '2A'::text AND t.provvedimenti_prescrittivi = 4 AND lpiani.enabled = false THEN
                    CASE
                        WHEN lpianipadre.description::text IS NOT NULL THEN lpianipadre.description::text
                        ELSE ''::text
                    END ||
                    CASE
                        WHEN lpiani.description::text IS NOT NULL THEN lpiani.description::text
                        ELSE ''::text
                    END
                    WHEN lti.codice_interno !~~* '2a'::text THEN lti.description::text
                    ELSE 'N.D'::text
                END AS tipo_piano_monitoraggio_old,
            
                CASE
                    WHEN nn.codice_interno_fk > 0 THEN nn.codice_interno_fk
                    ELSE n.codice_interno_fk
                END AS codice_interno_univoco_uo,
            COALESCE(lpiani.codice, lti.codice) AS codice_interno_piano_attivita,
             CASE
                    WHEN t.provvedimenti_prescrittivi = 4 AND (pp.n_livello <= 0 OR pp.n_livello IS NULL) THEN n.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 1 THEN n.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 2 THEN n.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi <> 4 AND (ppp.n_livello <= 0 OR ppp.n_livello IS NULL) THEN nn.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 1 THEN nn.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 2 THEN nn.descrizione_area_struttura
                    ELSE NULL::character varying(50)
                END AS descrizione_area_struttura,orslp.id as id_linea_controllo
 from ticket t              
 LEFT JOIN contact_ c ON c.user_id = t.supervisionato_da
             LEFT JOIN checklist_controlli audit ON audit.id_controllo::text = t.id_controllo_ufficiale::text
             JOIN lookup_site_id asl ON t.site_id = asl.code
             JOIN lookup_tipo_controllo ltc ON t.provvedimenti_prescrittivi = ltc.code
             LEFT JOIN tipocontrolloufficialeimprese tcu ON t.ticketid = tcu.idcontrollo AND (tcu.tipoispezione > 0 OR tcu.audit_tipo > 0 OR tcu.tipo_audit > 0) AND tcu.enabled = true
	     LEFT JOIN tipocontrolloufficialeimprese oggcu ON t.ticketid = oggcu.idcontrollo AND oggcu.ispezione > 0
	     LEFT JOIN lookup_ispezione lisp ON oggcu.ispezione = lisp.code
             LEFT JOIN lookup_ispezione_macrocategorie lim ON lim.code = lisp.level AND lim.enabled
	     LEFT JOIN lookup_bpi lbpi ON tcu.bpi = lbpi.code
	     LEFT JOIN lookup_haccp lhaccp ON tcu.haccp = lhaccp.code
             LEFT JOIN anagrafica.linee_attivita_controlli_ufficiali lacc ON lacc.id_controllo_ufficiale = t.ticketid
             LEFT JOIN anagrafica.rel_stabilimenti_linee_attivita orslp ON orslp.id = lacc.id_linea_attivita and orslp.data_scadenza is null
             LEFT JOIN ml8_linee_attivita_nuove_materializzata lattcu ON orslp.id_attivita = lattcu.id_nuova_linea_attivita
             LEFT JOIN ml8_linee_attivita_nuove_materializzata latt ON latt.id_nuova_linea_attivita = orslp.id_attivita
             LEFT JOIN lookup_tipo_ispezione lti ON tcu.tipoispezione = lti.code
             LEFT JOIN lookup_audit_tipo lat ON tcu.audit_tipo = lat.code
             LEFT JOIN lookup_tipo_audit lta ON tcu.tipo_audit = lta.code
             LEFT JOIN lookup_piano_monitoraggio lpiani ON tcu.pianomonitoraggio = lpiani.code AND t.assigned_date <= COALESCE(lpiani.data_scadenza, 'now'::text::date::timestamp without time zone)
             LEFT JOIN lookup_piano_monitoraggio lpianipadre ON lpiani.id_padre = lpianipadre.code AND lpianipadre.id_padre <= 0

	    -- JOIN NEW
	    LEFT JOIN view_motivi_linearizzati_dpat da on da.id_fisico_indicatore = tcu.tipoispezione 
	    LEFT JOIN view_motivi_linearizzati_dpat dp on dp.id_fisico_indicatore = tcu.pianomonitoraggio 
	    --fine join new
             LEFT JOIN dpat_strutture_asl n ON tcu.id_unita_operativa = n.id
             LEFT JOIN dpat_strutture_asl pp ON n.id_padre = pp.id
             LEFT JOIN unita_operative_controllo uoc ON uoc.id_controllo = t.ticketid
             LEFT JOIN dpat_strutture_asl nn ON nn.id = uoc.id_unita_operativa
             LEFT JOIN dpat_strutture_asl ppp ON nn.id_padre = ppp.id
	     WHERE t.tipologia = 3 and t.assigned_date  
	     between data_1 and data_2  AND t.trashed_date IS NULL 
	     AND t.alt_id > 0 and (select return_code from gestione_id_alternativo(t.alt_id, -1))=8
	
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
    
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.dbi_get_controlli_ufficiali_eseguiti_anagraficanew(timestamp without time zone, timestamp without time zone)
  OWNER TO postgres;

  
  -- Function: public_functions.dbi_get_controlli_ufficiali_eseguitinew(timestamp without time zone, timestamp without time zone)

-- DROP FUNCTION public_functions.dbi_get_controlli_ufficiali_eseguitinew(timestamp without time zone, timestamp without time zone);

CREATE OR REPLACE FUNCTION public_functions.dbi_get_controlli_ufficiali_eseguitinew(
    IN data_1 timestamp without time zone,
    IN data_2 timestamp without time zone)
  RETURNS TABLE(id_controllo_ufficiale integer, riferimento_id integer, riferimento_id_nome text, riferimento_id_nome_col text, riferimento_id_nome_tab text, id_asl integer, asl character varying, tipo_controllo character varying, tipo_ispezione_o_audit text, tipo_piano_monitoraggio text, id_piano integer, id_attivita integer, tipo_controllo_bpi text, tipo_controllo_haccp text, oggetto_del_controllo text, punteggio text, data_inizio_controllo date, anno_controllo text, data_chiusura_controllo date, aggiornata_cat_controllo text, categoria_rischio integer, prossimo_controllo timestamp with time zone, tipo_checklist text, linea_attivita_sottoposta_a_controllo text, unita_operativa text, id_struttura_uo integer, supervisionato_in_data timestamp without time zone, supervisionato_da character varying, supervisione_note text, congruo_supervisione text, note text, tipo_piano_monitoraggio_old text, codice_interno_univoco_uo integer, codice_interno_piano text, area_appartenenza_uo text, id_record_anagrafica integer) AS
$BODY$
DECLARE
	r RECORD;
	 	
BEGIN
		FOR 
		id_controllo_ufficiale,
		riferimento_id,
		riferimento_id_nome,
		riferimento_id_nome_col,
		riferimento_id_nome_tab,
		id_asl,  
		asl , 
		tipo_controllo  , 
		tipo_ispezione_o_audit , 
		tipo_piano_monitoraggio ,
		id_piano, id_attivita,
		tipo_controllo_bpi , tipo_controllo_haccp , 
		oggetto_del_controllo , punteggio , 
		data_inizio_controllo , anno_controllo,  data_chiusura_controllo, aggiornata_cat_controllo, categoria_rischio, prossimo_controllo, 
		tipo_checklist, 
		linea_attivita_sottoposta_a_controllo,
		unita_operativa, id_struttura_uo, supervisionato_in_data, supervisionato_da, supervisione_note, congruo_supervisione,note,  
		tipo_piano_monitoraggio_old,
		codice_interno_univoco_uo,codice_interno_piano,
		area_appartenenza_uo,id_record_anagrafica
		in
		select distinct
v.id_controllo_ufficiale , v.riferimento_id , v.riferimento_id_nome , v.riferimento_id_nome_col , 
v.riferimento_id_nome_tab , v.id_asl , v.asl , v.tipo_controllo , v.tipo_ispezione_o_audit , v.tipo_piano_monitoraggio , v.id_piano , 
v.id_attivita , v.tipo_controllo_bpi , v.tipo_controllo_haccp , v.oggetto_del_controllo , v.punteggio , v.data_inizio_controllo , 

v.anno_controllo , v.data_chiusura_controllo , v.aggiornata_cat_controllo , v.categoria_rischio , v.prossimo_controllo, 
v.tipo_checklist , v.linea_attivita_sottoposta_a_controllo , v.unita_operativa , v.id_struttura_uo , v.supervisionato_in_data , 
v.supervisionato_da , v.supervisione_note , v.congruo_supervisione ,v.note , v.tipo_piano_monitoraggio_old , v.codice_interno_univoco_uo , 
v.codice_interno_piano , v.area_appartenenza_uo ,COALESCE(anag.id_record_anagrafica, anag_nolinea.id_record_anagrafica)
		 from
		(
		select * from public_functions.dbi_get_controlli_ufficiali_eseguiti_vecchia_anagraficanew(data_1,data_2)
		UNION
		select * from public_functions.dbi_get_controlli_ufficiali_eseguiti_nuova_anagraficanew(data_1,data_2)
		UNION
		select * from public_functions.dbi_get_controlli_ufficiali_eseguiti_apicolturanew(data_1,data_2)
		UNION
		select * from public_functions.dbi_get_controlli_ufficiali_eseguiti_sintesisnew(data_1,data_2)
		UNION
		select * from public_functions.dbi_get_controlli_ufficiali_eseguiti_anagraficanew(data_1,data_2)
	)v
	
	left join  ricerche_anagrafiche_old_materializzata_temp anag on id_linea =id_linea_controllata and v.riferimento_id = anag.riferimento_id and anag.riferimento_id_nome_col = v.riferimento_id_nome_col

	left join (select distinct on (a.riferimento_id, a.riferimento_id_nome_col) a.riferimento_id, a.riferimento_id_nome_col, a.id_record_anagrafica from ricerche_anagrafiche_old_materializzata_temp a) anag_nolinea on v.riferimento_id = anag_nolinea.riferimento_id and anag_nolinea.riferimento_id_nome_col = v.riferimento_id_nome_col

    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
    
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.dbi_get_controlli_ufficiali_eseguitinew(timestamp without time zone, timestamp without time zone)
  OWNER TO postgres;

