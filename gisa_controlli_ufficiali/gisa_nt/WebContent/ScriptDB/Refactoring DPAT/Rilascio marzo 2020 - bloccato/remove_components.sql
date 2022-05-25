-- da lanciare quando si riprenderà l'attivita'' sul DPAT 2020 (cancellazioni e aggiornamento dei codici interni).
--remove table
drop table dpat_attribuzione_competenze;
drop table  dpat_attribuzione_competenze_attivita;
drop table dpat_attribuzione_competenze_indicatori;
drop table dpat_attribuzione_competenze_piani;
drop table dpat_attribuzione_competenze_sezione;
drop table dpat_codici_attivita;
drop table  dpat_codici_indicatore;
drop table  dpat_coefficiente;
drop table   dpat_competenze_struttura_indicatore;
drop table   dpat_formula;
drop table  dpat_indicatore;
drop table  dpat_indicatore_;
drop table  dpat_log_modifica_piano;
drop table  dpat_risorse_strumentali;
drop table  dpat_risorse_strumentali_strutture;
drop table dpat_sezione;
drop table  dpat_sezione_;
drop table dpat_strumento_calcolo_storico_esportazioni;
drop table  dpat_strutture_asl_disabilitate;
drop table  dpat_piano_attivita_new_18gen;
drop table  dpat_piano;
drop table  dpat_piano_;
drop table  dpat_attivita;
drop table  dpat_attivita_;
DROP TABLE public."lookup_tipo_ispezione_OLD";


--script cancella funzioni dpat
drop function public.dpat_aggiorna_codici_indicatore();
drop function public.dpat_attribuzione_competenze_indicatori();
drop function public.dpat_cancella_dpat_propagato( idasl integer, annoinput integer)
drop function public.dpat_duplica_modello(
    anno_i integer,
    anno_target_i integer);
-- già eliminata drop function public.dpat_elimina(tipo_classe text, anno integer, id integer) 	
drop function public.dpat_insert(
    id_indicatore integer,
    tipo_inserimento text,
    id_piano integer,
    descrizione text,
    codice_esame text,
    asl text,
    tipo_attivita text,
    alias text,
    codice_alias text,
    tipo_piano_att_ind text,
    anno integer);
drop function public.dpat_insert_dummy(
    anno_input integer,
    id_piano integer,
    stato_input integer);
drop function public.dpat_insert_dummy_child_per_ultimo_piano_attivita_inserito(anno_in integer);
drop function public.dpat_insert_indicatore(
    id_indicatore integer,
    descrizione_input text,
    codice_esame_input text,
    asl text,
    alias_input text,
    codice_alias_input text,
    stato_input integer,
    tipo_inserimento text,
    anno_input integer,
    id_piano integer);
drop function public.dpat_insert_indicatore_before_or_after(
    id_indicatore integer,
    descrizione text,
    codice_esame text,
    asl text,
    alias text,
    tipo_inserimento text,
    stato_input text,
    stato_incrementa_ordine text,
    codice_alias text);
drop function public.dpat_insert_nominativo();
drop function dpat_insert_struttura(anno_input integer,
    id_asl_input integer,
    tipo_struttura_input integer,
    descrizione_lunga_input text,
    id_utente_input integer,
    id_padre_input integer);
drop function public.dpat_strumento_calcolo_propaga(
    idasl integer,
    annoinput integer);

-- remove view
drop view dpat_programmazioni_ui_strutture;
drop view dpat_strumento_calcolo_congela_entita_per_reportistica;
drop view public.elenco_attivita_ispezioni_dpat;
drop view lookup_piano_monitoraggio_vista;
drop view lookup_tipo_ispezione_vista;
drop view report_tipo_e_numero_ncf;
drop view report_tipo_e_numero_ncf_operatore;
drop view tipo_e_numero_ncf;



