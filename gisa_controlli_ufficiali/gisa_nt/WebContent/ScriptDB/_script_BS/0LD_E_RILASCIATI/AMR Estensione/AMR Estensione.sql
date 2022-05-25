 
-- CHI: Bartolo Sansone	
-- COSA: Gestione Estensioni AMR 
-- QUANDO: 25/01/2018

insert into permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled)
values (94, 'global-search-amr', true, true, true, true, 'Ricerca Globale CU AMR', 2000, true);



CREATE OR REPLACE FUNCTION public.is_controllo_amr(
    IN _idcontrollo integer)
  RETURNS boolean AS
$BODY$
DECLARE
	_ticketId integer;
	esito boolean;
BEGIN

_ticketId := -1;
esito:= false;

_ticketId := (select distinct t.ticketid
from ticket t
left join tipocontrolloufficialeimprese tcu on tcu.idcontrollo = t.ticketid
left join lookup_piano_monitoraggio piano on piano.code = tcu.pianomonitoraggio
left join ricerche_anagrafiche_old_materializzata r on 
((r.riferimento_id = t.org_id and r.riferimento_id_nome_col = 'org_id') or (r.riferimento_id = t.id_stabilimento and r.riferimento_id_nome_col = 'id_stabilimento') or (r.riferimento_id = t.alt_id and r.riferimento_id_nome_col = 'alt_id'))
where t.tipologia = 3 and t.trashed_date is null and piano.codice_esame = 'PMAMR' and r.n_linea in (select cun from osa_amr) and t.ticketid = _idcontrollo and t.ticketid in (select distinct id_controllo_ufficiale::integer from ticket c
left join lookup_piano_monitoraggio p on p.code = c.motivazione_piano_campione
where c.trashed_date is null and c.tipologia = 2 and c.id_controllo_ufficiale = _idcontrollo::text and p.codice_esame = 'PMAMR'));

if (_ticketId) > 0 THEN
esito = true;
END IF;

 RETURN esito;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
  
  
  --- bisogna fare una modific : i cu duplicati per più piani vanno raggruppati
  
  

CREATE OR REPLACE FUNCTION public.lista_controlli_amr(
    IN _idcontrollo integer,
    IN _idasl integer,
    IN _numverbaleamr text,
    IN _datainizio text,
    IN _datafine text
    )
  RETURNS TABLE(id_controllo integer, id_asl integer, piano_monitoraggio text, num_verbale_amr text, data_controllo timestamp without time zone) AS
$BODY$
DECLARE
r RECORD;	

BEGIN

FOR 

id_controllo, id_asl, piano_monitoraggio, num_verbale_amr, data_controllo

in


select distinct t.ticketid, t.site_id, piano.description, d.num_verbale_amr, t.assigned_date
from ticket t
left join tipocontrolloufficialeimprese tcu on tcu.idcontrollo = t.ticketid
left join lookup_piano_monitoraggio piano on piano.code = tcu.pianomonitoraggio
left join dati_amr d on d.id_controllo = t.ticketid and d.enabled
left join ricerche_anagrafiche_old_materializzata ric on 
((ric.riferimento_id = t.org_id and ric.riferimento_id_nome_col = 'org_id') or (ric.riferimento_id = t.id_stabilimento and ric.riferimento_id_nome_col = 'id_stabilimento') or (ric.riferimento_id = t.alt_id and ric.riferimento_id_nome_col = 'alt_id'))

where t.tipologia = 3 and t.trashed_date is null and piano.codice_esame = 'PMAMR' 
and ric.n_linea in (select cun from osa_amr)

and((_idcontrollo>0 and t.ticketid = _idcontrollo) or _idcontrollo = -1)
and((_idasl>0 and t.site_id = _idasl) or _idasl = -1)
and((_datainizio is not null and _datainizio <>'' and t.assigned_date >= _datainizio::timestamp without time zone) or _datainizio ='')
and((_datafine is not null and _datafine <>'' and t.assigned_date <= _datafine::timestamp without time zone) or _datafine ='')
order by t.assigned_date desc

    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;

  
  CREATE OR REPLACE FUNCTION public.amr_get_lista_veterinari(
    IN _idasl integer
       )
  RETURNS TABLE(nome text, cf text) AS
$BODY$
DECLARE
r RECORD;	

BEGIN

FOR 

nome, cf

in


SELECT distinct concat_ws(' ', namefirst, namelast), codice_fiscale FROM lista_utenti_centralizzata WHERE   1=1 AND in_nucleo_ispettivo  and codice_fiscale <> '' AND role_id in ( 47,19,46,98,43,222) AND contact_id_link > -1 AND role_id > -1 AND role_id IS NOT NULL and notes not ilike '%utente fittizio%' and ((_idasl>0 and site_id = _idasl) or _idasl=-1) ORDER BY codice_fiscale asc 

    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;


  
  -- Table: public.dati_amr

-- DROP TABLE public.dati_amr;

CREATE TABLE public.dati_amr
(
  id serial,
  id_controllo integer,
  num_verbale_amr text,
  ora_inizio_prelievo text,
  ora_fine_prelievo text,
  codice_fiscale_rappresentante text,
  telefono text,
  codice_allevamento text,
  id_fiscale_proprietario text,
  ragione_sociale text,
  indirizzo text,
  locale text,
  data_accasamento text,
  capacita text,
  codice_fiscale_veterinario text,
  enabled boolean DEFAULT TRUE,
  entered timestamp without time zone default now(),
  enteredby integer,
  data_invio timestamp without time zone,
  CONSTRAINT dati_amr_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.dati_amr
  OWNER TO postgres;
GRANT ALL ON TABLE public.dati_amr TO postgres;
GRANT SELECT ON TABLE public.dati_amr TO report;






    select distinct
    'da recuperare asl' as "telefonoPrelevatore",
    'Specie campionata 	LISTA CON VALORI RICAVATI DA TABELLA 	SI 	1020301030106=POLLO;1020301040101=TACCHINO' as  "specieCampionatePMRA2017",
    CASE WHEN trim(campione.problem) != '' and campione.problem is not null THEN trim(campione.problem) ELSE '2017/12' END as "identificativoCampioneNumeroLotto",
    'aspettiamo che ce lo facciano sapere' as "foodexCodice",
    r.n_linea as "cun",
    to_char(campione.assigned_date, 'yyyy-mm-dd')||'T'||amr.ora_fine_prelievo as "dataFinePrelievo",
    to_char(campione.assigned_date, 'yyyy-mm-dd')||'T'||amr.ora_inizio_prelievo as "dataPrelievo",
   campione.location as "numeroScheda",
    piano.codice_esame as "pianoCodice",
    amr.codice_fiscale_veterinario as "prelCodFiscale",
    '' as "tipoImpresa"

     from dati_amr amr
    left join ticket cu on cu.ticketid = amr.id_controllo
    left join ticket campione on campione.id_controllo_ufficiale =cu.ticketid::text and campione.tipologia = 2
    left join lookup_piano_monitoraggio piano on piano.code = campione.motivazione_piano_campione
    left join ricerche_anagrafiche_old_materializzata r on r.riferimento_id = cu.id_stabilimento and r.riferimento_id_nome_col = 'id_stabilimento' and r.n_linea in (select cun from osa_amr) 

    where amr.id_controllo = 1067483  and amr.enabled


