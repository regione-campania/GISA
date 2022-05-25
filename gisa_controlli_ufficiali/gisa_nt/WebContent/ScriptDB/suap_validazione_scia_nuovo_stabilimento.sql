-- Function: public_functions.suap_validazione_scia_nuovo_stabilimento(integer, integer, integer, text)

-- DROP FUNCTION public_functions.suap_validazione_scia_nuovo_stabilimento(integer, integer, integer, text);

CREATE OR REPLACE FUNCTION public_functions.suap_validazione_scia_nuovo_stabilimento(
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
id_richiesta_suap int ;
validazioneCompleta boolean ;
altId int;
 pivaSearch text;  cfSearch text; civicoSearch text; viaSearch  text ;comuneSearch text;
loginsert BOOLEAN ;
importAllegati boolean;
id_tipo_attivita int ;

id_linea_master_list int ;
BEGIN

idLog:=(select nextval('suap_log_validazioni_id_seq'));
insert into suap_log_validazioni (id,id_richiesta_operatore,nome_funzione,data_validazione) values(idLog,id_rel_stab_lp,'public_functions.suap_validazione_scia_nuovo_stabilimento',current_timestamp);


idStabilimento:=(select distinct id_Stabilimento from suap_ric_scia_relazione_stabilimento_linee_produttive where id = id_rel_stab_lp);
id_richiesta_suap:=(select id_operatore from suap_ric_scia_stabilimento where id = idStabilimento);
loginsert:=(select * from public_functions.suap_log_operazioni(id_richiesta_suap ,'public_functions.suap_validazione_scia_nuovo_stabilimento' ,'Assegnato Numero Registrazione Su Stabilimento: '||numReg ,'suap_ric_scia_stabilimento.id' ,idStabilimento||'',idLog ));

numReg:=(select * from public_functions.suap_genera_numero_registrazione(id_richiesta_suap, 'w'));
raise info 'numReg : -> %',numReg;
/*NUMERO REGISTRAZIONE SU STABILIMENTO*/
if statoValidazione=1 
then
select partita_iva, codice_fiscale_impresa, civico_stabilimento_calcolato , via_stabilimento_calcolata ,comune_richiesta,stab_id_attivita,id_linea_attivita_stab
into pivaSearch,cfSearch,civicoSearch,viaSearch,comuneSearch,id_tipo_attivita,id_linea_master_list
from suap_ric_scia_operatori_denormalizzati_view 
where     id_linea_attivita =id_rel_stab_lp ;

select id_opu_operatore,id_stabilimento,4 into esito
from opu_operatori_denormalizzati_view where 
case when (pivaSearch is not null and pivaSearch <>'') then partita_iva ilike pivaSearch else codice_fiscale_impresa ilike cfSearch end
and comune_richiesta ilike trim(comuneSearch)
and via_stabilimento_calcolata ilike trim(viaSearch)
and civico_stabilimento_calcolato ilike trim(civicoSearch)
and stab_id_attivita=id_tipo_attivita and 
case when id_tipo_attivita=2 then id_linea_attivita_stab = id_linea_master_list else 1=1 end
and id_stato !=4 ;

if esito.id_stabilimento_opu>0
then 
return esito;
end if;
end if ;


raise info 'statoValidazione dopo iff  %',statoValidazione;


numReg:=(select numReg ||lpad(dbi_opu_get_progressivo_linea_per_stabilimento(numReg)||'',3,'0') ) ;

raise info 'numReg linea  %',numReg;

if statoValidazione=1 
then
update suap_ric_scia_relazione_stabilimento_linee_produttive set stato = statoValidazione, 
validato_da =idutenteasl ,validato_in_data =current_timestamp ,validazione_note =noteValidazione 
,numero_registrazione = numReg   
where id = id_rel_stab_lp  ; 
else
update suap_ric_scia_relazione_stabilimento_linee_produttive set stato = statoValidazione, 
validato_da =idutenteasl ,validato_in_data =current_timestamp ,validazione_note =noteValidazione 
where id = id_rel_stab_lp  ; 
end if;


raise info 'id_rel_stab_lp  %',id_rel_stab_lp;

validazioneCompleta:=(select * from public_functions.suap_controlla_validazione_scia_nuovo_stabilimento_linee(id_richiesta_suap ,idLog ));

raise info 'validazioneCompleta : %',validazioneCompleta;
if validazioneCompleta=true
then

select * into operatore from public_functions.suap_insert_impresa(id_richiesta_suap,idLog,idutenteasl);
esito.id_impresa_opu:=operatore.id;
esito.id_esito:=operatore.id_esito;

select * into stab from  public_functions.suap_insert_stabilimento(id_richiesta_suap,operatore,idLog,idutenteasl);
esito.id_stabilimento_opu:=stab.id;

raise info 'id stabilimento %',stab.id;
update suap_ric_scia_operatore set validato = true where id=id_richiesta_suap;

altId:=(select alt_id from suap_ric_scia_stabilimento where id_operatore=id_richiesta_suap);

/*IMPORT CONTROLLI UFFICIALI*/
update ticket set id_stabilimento =stab.id where  alt_id =altId;
update audit set alt_id=id_stabilimento,id_stabilimento =stab.id where  id_stabilimento =stab.id;

select * from public_functions.insert_linee_attivita_controlli_ufficiali
((select rrslp.id_controllo_ufficiale,rslp.id
from linee_attivita_controlli_ufficiali rrslp
join opu_relazione_stabilimento_linee_produttive rslp on rrslp.id_linea_attivita = rslp.id_suap_rel_stab_lp
where rslp.id_stabilimento =stab.id and rrslp.trashed_date is null
));
 
--insert into opu_linee_attivita_controlli_ufficiali (id_controllo_ufficiale,id_linea_attivita)
--(select rrslp.id_controllo_ufficiale,rslp.id
--from linee_attivita_controlli_ufficiali rrslp
--join opu_relazione_stabilimento_linee_produttive rslp on rrslp.id_linea_attivita = rslp.id_suap_rel_stab_lp
--where rslp.id_stabilimento =stab.id
--);

return esito ;
else

raise info 'statoValidazione %',validazioneCompleta;
validazioneCompleta:=(select * from public_functions.suap_controlla_validazione_scia_nuovo_stabilimento_linee(id_richiesta_suap ,idLog ));

if validazioneCompleta=false
then
esito.id_esito:=7;
else
esito.id_esito:=1;
update suap_ric_scia_operatore set validato = true where id=id_richiesta_suap;

end if ;

esito.id_impresa_opu:=id_richiesta_suap;
esito.id_stabilimento_opu=idStabilimento;

update suap_ric_scia_relazione_stabilimento_linee_produttive set stato = statoValidazione, 
validato_da =idutenteasl ,validato_in_data =current_timestamp ,validazione_note =noteValidazione 
where id = id_rel_stab_lp and stato=0 ; 


return esito;
end if ;



 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.suap_validazione_scia_nuovo_stabilimento(integer, integer, integer, text)
  OWNER TO postgres;
