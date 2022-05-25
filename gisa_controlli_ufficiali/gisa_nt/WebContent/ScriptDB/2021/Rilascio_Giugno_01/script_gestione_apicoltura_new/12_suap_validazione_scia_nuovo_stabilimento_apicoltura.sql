-- Function: public_functions.suap_validazione_scia_nuovo_stabilimento_apicoltura(integer, integer, integer, text)

-- DROP FUNCTION public_functions.suap_validazione_scia_nuovo_stabilimento_apicoltura(integer, integer, integer, text);

CREATE OR REPLACE FUNCTION public_functions.suap_validazione_scia_nuovo_stabilimento_apicoltura(
    id_rel_stab_lp integer,
    idutenteasl integer,
    statovalidazione integer,
    notevalidazione text)
  RETURNS esitovalidazione AS
$BODY$
DECLARE
operatore impresa;
stab stabilimento ;
esito esitovalidazione ;
idLog int;
idStabilimento int ;
numReg text;
id_richiestasuap int ;
validazioneCompleta boolean ;
altId int;
loginsert BOOLEAN ;
importAllegati boolean;
id_tipo_attivita int ;
_id_apiario integer;


cfRappresentante text ;
id_linea_master_list int ;
BEGIN

idStabilimento:=(select distinct id_Stabilimento from suap_ric_scia_relazione_stabilimento_linee_produttive where id = id_rel_stab_lp);
id_richiestasuap:=(select id_operatore from suap_ric_scia_stabilimento where id = idStabilimento);

if statovalidazione = 2 then
update suap_ric_scia_relazione_stabilimento_linee_produttive set validazione_note =noteValidazione, validato_in_data =current_timestamp, validato_da =idutenteasl, stato = statoValidazione where id = id_rel_stab_lp and stato=0 ; 
update suap_ric_scia_stabilimento SET note_validazione= 'PRATICA APICOLTURA RESPINTA' ,validazione_data = current_timestamp, stato_validazione = statoValidazione, validazione_da = idutenteasl where id = idStabilimento;
update suap_ric_scia_operatore set  validato =true where id = id_richiestasuap;
esito.id_esito:=1;
return esito;
end if;
   
select cf_rapp_sede_legale into cfRappresentante

from suap_ric_scia_operatori_denormalizzati_view 
where     id_linea_attivita =id_rel_stab_lp ;

select id,-1 as id_stab,4 into esito
from apicoltura_imprese imp where    
 imp.id_richiesta_suap=id_richiestasuap 
  and stato not in (4,3) and sincronizzato_bdn = true;

if esito.id_impresa_opu is null or esito.id_impresa_opu <=0
then 
return esito;
end if;


idLog:=(select nextval('suap_log_validazioni_id_seq'));
insert into suap_log_validazioni (id,id_richiesta_operatore,nome_funzione,data_validazione) values(idLog,id_rel_stab_lp,'public_functions.suap_validazione_scia_nuovo_stabilimento_apicoltura',current_timestamp);



/*NUMERO REGISTRAZIONE SU STABILIMENTO*/
numReg:=(select * from public_functions.suap_genera_numero_registrazione(id_richiestasuap, 'w'));
loginsert:=(select * from public_functions.suap_log_operazioni(id_richiestasuap ,'public_functions.suap_validazione_scia_nuovo_stabilimento' ,'Assegnato Numero Registrazione Su Stabilimento: '||numReg ,'suap_ric_scia_stabilimento.id' ,idStabilimento||'',idLog ));


numReg:=(select numReg ||lpad(dbi_opu_get_progressivo_linea_per_stabilimento(numReg)||'',3,'0') ) ;
update suap_ric_scia_relazione_stabilimento_linee_produttive set validazione_note =noteValidazione, validato_in_data =current_timestamp, validato_da =idutenteasl, stato = statoValidazione, numero_registrazione = numReg where id = id_rel_stab_lp and stato=0 ; 

loginsert:=(select * from public_functions.suap_log_operazioni(id_richiestasuap ,'public_functions.suap_validazione_scia_nuovo_stabilimento' ,'Assegnato Numero Registrazione Su linea : '||numReg ,'suap_ric_scia_relazione_stabilimento_linee_produttive.id' ,id_rel_stab_lp||'',idLog ));


update apicoltura_imprese set utente_validazione_scia =idutenteasl ,data_validazione_scia =current_timestamp, numero_registrazione =numReg
where apicoltura_imprese.id_richiesta_suap = id_richiestasuap;

update suap_ric_scia_operatore set  validato =true where id = id_richiestasuap;

--refresh materializzata
FOR _id_apiario IN select id from apicoltura_apiari where id_operatore = (select id from apicoltura_imprese where id_richiesta_suap = id_richiestasuap)
	LOOP
		perform api_insert_into_ricerche_anagrafiche_old_materializzata(_id_apiario);
	END LOOP;

esito.id_esito:=1;
esito.id_stabilimento_opu=esito.id_impresa_opu;

return esito ;



return esito;

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.suap_validazione_scia_nuovo_stabilimento_apicoltura(integer, integer, integer, text)
  OWNER TO postgres;
