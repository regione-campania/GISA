-- Devono essere CHIUSI i controlli ufficiali aperti con data inizio controllo < 2019
-- Sorveglianza: I CU con checklist definitiva e categoria aggiornata vanno CHIUSI
-- Sorveglianza: I CU con checklist definitive e categoria non aggiornata vanno CHIUSI e la categoria AGGIORNATA
-- Sorveglianza: I CU con checklist non definitiva vanno CANCELLATI
-- Sorveglianza: I CU senza checklist vanno CANCELLATI


CREATE OR REPLACE FUNCTION public.chiusura_cancellazione_ufficio_cu(_datamassima timestamp without time zone)
  RETURNS text AS
$BODY$
DECLARE  
 dataattuale timestamp without time zone;
 msg text;  
 numCuChiusi integer;
 numSottoattivitaChiuse integer;
 numCuSorveglianzaChiusi integer;
 numSottoattivitaSorveglianzaChiuse integer;
 numCuSorveglianzaCategoriaAggiornataChiusi integer;
numSottoattivitaSorveglianzaCategoriaAggiornataChiuse integer;
  numCuSorveglianzaCancellati integer;
 numSottoattivitaSorveglianzaCancellate integer;
BEGIN 

msg := '';
numCuChiusi := -1;
numSottoattivitaChiuse := -1;
numCuSorveglianzaChiusi := -1;
numSottoattivitaSorveglianzaChiuse := -1;
numCuSorveglianzaCategoriaAggiornataChiusi := -1;
numSottoattivitaSorveglianzaCategoriaAggiornataChiuse := -1;
numCuSorveglianzaCancellati := -1;
numSottoattivitaSorveglianzaCancellate := -1;

select now() into dataattuale;

-- Devono essere CHIUSI i controlli ufficiali aperti con data inizio controllo < 2019
-- Sorveglianza: I CU con checklist definitiva e categoria aggiornata vanno CHIUSI
-- Sorveglianza: I CU con checklist definitiva e categoria non aggiornata vanno CANCELLATI
-- Sorveglianza: I CU con checklist non definitiva vanno CANCELLATI
-- Sorveglianza: I CU senza checklist vanno CANCELLATI

RAISE INFO 'Inizio operazione di chiusura e cancellazione CU';

RAISE INFO 'Chiudo i CU (APERTI, NON SORVEGLIANZA, DATA INIZIO < %', _datamassima;
--- CHIUSURA CU < DATA
WITH rows AS (
update ticket set status_id = 2, closed = now(), note_internal_use_only = concat(note_internal_use_only, '; [', dataattuale, '] [CU CHIUSO D''UFFICIO] [DATA INIZIO<', _datamassima, ']')
where  tipologia = 3
and assigned_date < _datamassima
and provvedimenti_prescrittivi <> 5
and (status_id <> 2 or closed is null)
and trashed_date is null 
RETURNING 1
)
SELECT count(*) into numCuChiusi FROM rows;
RAISE INFO 'CU (Non Sorveglianza) Chiusi: %', numCuChiusi;

RAISE INFO 'Chiudo le SOTTOATTIVITA connesse ai CU appena chiusi';
--- CHIUSURA SOTTOATTIVITA CU < DATA
WITH rows AS (
update ticket set status_id = 2, closed = now(), note_internal_use_only = concat(note_internal_use_only, '; [', dataattuale, '] [SOTTOATTIVITA CHIUSA D''UFFICIO] [DATA INIZIO CU<', _datamassima, ']')
where tipologia <> 3
and (status_id <> 2 or closed is null)
and trashed_date is null
and id_controllo_ufficiale in (select ticketid::text from ticket where note_internal_use_only ilike concat('%[', dataattuale, '] [CU CHIUSO D''UFFICIO] [DATA INIZIO<', _datamassima, ']%'))
RETURNING 1
)
SELECT count(*) into numSottoattivitaChiuse FROM rows;
RAISE INFO 'Sottoattivita Chiuse: %', numSottoattivitaChiuse;

RAISE INFO 'Chiudo i CU (APERTI, SORVEGLIANZA, CATEGORIA AGGIORNATA, DATA INIZIO < %', _datamassima;
--- CHIUSURA CU SORVEGLIANZA < DATA CON CATEGORIA AGGIORNATA
WITH rows AS (
update ticket set status_id = 2, closed = now(), note_internal_use_only = concat(note_internal_use_only, '; [', dataattuale, '] [CU SORVEGLIANZA CHIUSO D''UFFICIO] [CATEGORIA AGGIORNATA] [DATA INIZIO<', _datamassima, ']')
where tipologia = 3
and assigned_date < _datamassima
and provvedimenti_prescrittivi = 5
and (status_id <> 2 or closed is null)
and trashed_date is null
and isaggiornata_categoria is true
RETURNING 1
)
SELECT count(*) into numCuSorveglianzaChiusi FROM rows;
RAISE INFO 'CU (Sorveglianza) Chiusi: %', numCuSorveglianzaChiusi;

RAISE INFO 'Chiudo le SOTTOATTIVITA connesse ai CU appena chiusi';
--- CHIUSURA SOTTOATTIVITA CU SORVEGLIANZA < DATA
WITH rows AS (
update ticket set status_id = 2, closed = now(), note_internal_use_only = concat(note_internal_use_only, '; [', dataattuale, '] [SOTTOATTIVITA CHIUSA D''UFFICIO] [DATA INIZIO CU<', _datamassima, ']')
where tipologia <> 3
and (status_id <> 2 or closed is null)
and trashed_date is null
and id_controllo_ufficiale in (select ticketid::text from ticket where note_internal_use_only ilike concat('%[', dataattuale, '] [CU SORVEGLIANZA CHIUSO D''UFFICIO] [CATEGORIA AGGIORNATA] [DATA INIZIO<', _datamassima, ']%'))
RETURNING 1
)
SELECT count(*) into numSottoattivitaSorveglianzaChiuse FROM rows;
RAISE INFO 'Sottoattivita Chiuse: %', numSottoattivitaSorveglianzaChiuse;

RAISE INFO 'Chiudo i CU (APERTI, SORVEGLIANZA, CHECKLIST DEFINITIVE, CATEGORIA NON AGGIORNATA DA AGGIORNARE, DATA INIZIO < %', _datamassima;
--- CHIUSURA E AGGIORNAMENTO CATEGORIA CU SORVEGLIANZA < DATA CON CATEGORIA NON AGGIORNATA E CHECKLIST DEFINITIVE

WITH rows AS (
update ticket set status_id = 2, closed = now(),  isaggiornata_categoria = true, categoria_rischio = (
select categoria_rischio from parametri_categorizzazzione_osa where tipo_operatore = 1 and (
(select sum(livello_rischio) from audit where stato = 'Definitiva' and is_principale and trashed_date is null and id_controllo = ticketid::text))
between range_da and range_a), note_internal_use_only = concat(note_internal_use_only, '; [', dataattuale, '] [CU SORVEGLIANZA CHIUSO D''UFFICIO] [CATEGORIA AGGIORNATA D''UFFICIO] [DATA INIZIO<', _datamassima, ']')
where tipologia = 3
and assigned_date < _datamassima
and provvedimenti_prescrittivi = 5
and (status_id <> 2 or closed is null)
and trashed_date is null
and isaggiornata_categoria is false
and ticketid::text in (select id_controllo from audit where stato = 'Definitiva' and trashed_date is null) 
and ticketid::text not in (select id_controllo from audit where stato <> 'Definitiva' and trashed_date is null) 
RETURNING 1
)
SELECT count(*) into numCuSorveglianzaCategoriaAggiornataChiusi FROM rows;
RAISE INFO 'CU (Sorveglianza) con aggiornamento Categoria Rischio Chiusi: %', numCuSorveglianzaCategoriaAggiornataChiusi;

RAISE INFO 'Chiudo le SOTTOATTIVITA connesse ai CU appena chiusi';
--- CHIUSURA SOTTOATTIVITA CU SORVEGLIANZA CATEGORIA AGGIORNATA < DATA
WITH rows AS (
update ticket set status_id = 2, closed = now(), note_internal_use_only = concat(note_internal_use_only, '; [', dataattuale, '] [SOTTOATTIVITA CHIUSA D''UFFICIO] [DATA INIZIO CU<', _datamassima, ']')
where tipologia <> 3
and (status_id <> 2 or closed is null)
and trashed_date is null
and id_controllo_ufficiale in (select ticketid::text from ticket where note_internal_use_only ilike concat('%[', dataattuale, '] [CU SORVEGLIANZA CHIUSO D''UFFICIO] [CATEGORIA AGGIORNATA D''UFFICIO] [DATA INIZIO<', _datamassima, ']%'))
RETURNING 1
)
SELECT count(*) into numSottoattivitaSorveglianzaCategoriaAggiornataChiuse FROM rows;
RAISE INFO 'Sottoattivita Chiuse: %', numSottoattivitaSorveglianzaCategoriaAggiornataChiuse;

RAISE INFO 'Cancello i CU (APERTI, SORVEGLIANZA, CATEGORIA NON AGGIORNATA, DATA INIZIO < %', _datamassima;
--- CANCELLAZIONE CU SORVEGLIANZA < DATA CON CATEGORIA NON AGGIORNATA
WITH rows AS (
update ticket set trashed_date = now(), note_internal_use_only = concat(note_internal_use_only, '; [', dataattuale, '] [CU SORVEGLIANZA CANCELLATO D''UFFICIO] [CATEGORIA NON AGGIORNATA] [DATA INIZIO<', _datamassima, ']')
where tipologia = 3
and assigned_date < _datamassima
and provvedimenti_prescrittivi = 5
and (status_id <> 2 or closed is null)
and trashed_date is null
and isaggiornata_categoria is not true
RETURNING 1
)
SELECT count(*) into numCuSorveglianzaCancellati FROM rows;
RAISE INFO 'CU (Sorveglianza) Cancellati: %', numCuSorveglianzaCancellati;

RAISE INFO 'Cancello le SOTTOATTIVITA connesse ai CU appena chiusi';
--- CANCELLAZIONE SOTTOATTIVITA CU SORVEGLIANZA < DATA CON CATEGORIA NON AGGIORNATA
WITH rows AS (
update ticket set trashed_date = now(), note_internal_use_only = concat(note_internal_use_only, '; [', dataattuale, '] [SOTTOATTIVITA CANCELLATA D''UFFICIO] [DATA INIZIO CU<', _datamassima, ']')
where tipologia <> 3
and (status_id <> 2 or closed is null)
and trashed_date is null
and id_controllo_ufficiale in (select ticketid::text from ticket where note_internal_use_only ilike concat('%[', dataattuale, '] [CU SORVEGLIANZA CHIUSO D''UFFICIO] [CATEGORIA AGGIORNATA] [DATA INIZIO<', _datamassima, ']%'))
RETURNING 1
)
SELECT count(*) into numSottoattivitaSorveglianzaCancellate FROM rows;
RAISE INFO 'Sottoattivita Cancellate: %', numSottoattivitaSorveglianzaCancellate;

select concat ('CU chiusi d''ufficio: ',  numCuChiusi, '; Sottoattivita chiuse: ', numSottoattivitaChiuse, '; CU sorveglianza chiusi d''ufficio: ',numCuSorveglianzaChiusi, '; Sottoattivita chiuse: ', numSottoattivitaSorveglianzaChiuse, '; CU sorveglianza chiusi d''ufficio dopo aggiornamento categoria: ',numCuSorveglianzaCategoriaAggiornataChiusi, '; Sottoattivita chiuse: ', numSottoattivitaSorveglianzaCategoriaAggiornataChiuse, '; CU sorveglianza cancellati: ', numCuSorveglianzaCancellati, '; Sottoattivita cancellate: ', numSottoattivitaSorveglianzaCancellate) into msg;

RAISE INFO '% ', msg;

return msg;

END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
