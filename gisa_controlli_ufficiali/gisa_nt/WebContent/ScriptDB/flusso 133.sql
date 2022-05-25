alter table ticket add column id_soggetto_fisico integer;
alter table ticket add column  ragione_sociale_vecchia text;
--Li cancello perchè dopo i chiarimenti di Giannoni queste info non servono piu
alter table ticket drop column id_soggetto_fisico;
alter table ticket drop column ragione_sociale_vecchia;

alter table ticket add column  partita_iva_vecchia text;

CREATE OR REPLACE FUNCTION public_functions.suap_validazione_scia_variazione_titolarita(
    idrichiesta integer,
    idstabilimentoopu integer,
    idutenteasl integer,
    statovalidazione integer,
    notevalidazione text)
  RETURNS esitovalidazione AS
$BODY$
DECLARE

idLog int;
loginsert BOOLEAN ;
esito esitovalidazione ;
operatore impresa;
stab stabilimento;
numregvariazione text ;
codicenazionale text;
idlineamasterlist int ;
idLineaRichiesta int ;
numregric text;
importAllegati boolean ;
altId int ;
codiceUfficialeEsistente text;

insertlinea boolean ;
numLineeVariazione integer;
idStabCreato int;
lineaRichiesta integer;
lineaOpu integer;
idImpresaVecchio int ;
partitaIvaVecchia text;
partitaIvaNuova text;
temp0 json;
temp1 json;
temp2 json;
msgStorico text;
BEGIN



if statoValidazione =1
then


idLog:=(select nextval('suap_log_validazioni_id_seq'));
insert into suap_log_validazioni (id,id_richiesta_operatore,nome_funzione,data_validazione) values(idLog,idRichiesta,'public_functions.suap_validazione_scia_variazione_titolarita',current_timestamp);

/*Dopo Aver Cessato Lo stabilimentoinserisco quello provenienre dalla variazione*/

select * into operatore from public_functions.suap_insert_impresa(idRichiesta,idLog,idutenteasl);
esito.id_impresa_opu:=operatore.id;
esito.id_esito:=operatore.id_esito;

raise info 'id_impresa_opu inserita %',esito.id_impresa_opu;

idImpresaVecchio:=(select id_operatore from opu_stabilimento where id = idstabilimentoopu);

partitaIvaVecchia:=(select partita_iva from opu_operatore where id = idImpresaVecchio);

raise info 'id impresa vecchio %',idImpresaVecchio;


insert into opu_operatore_variazione_titolarita_stabilimenti
(
id_stab  ,
id_operatore  ,
data_operazione ,
validato_da  ,
id_pratica_validata 
)
values ( idstabilimentoopu,idImpresaVecchio,current_timestamp,idutenteasl,idrichiesta);

raise info 'insert into opu_operatore_variazione_titolarita_stabilimenti values(%,%,%,%,% )',idstabilimentoopu,idImpresaVecchio,current_timestamp,idutenteasl,idrichiesta ;

update opu_stabilimento set id_operatore = esito.id_impresa_opu, modified = CURRENT_TIMESTAMP where id = idstabilimentoopu ;
raise info  'update opu_stabilimento set id_operatore = % where id = %' , esito.id_impresa_opu::text , idstabilimentoopu::text;

--modifica davide 30/05 PER SALVARE I DATI VECCHI E NUOVI ANAGRAFICI DELLA VARIAZIONE-----------------------------

update opu_relazione_stabilimento_linee_produttive set modified = CURRENT_TIMESTAMP where id_stabilimento = idstabilimentoopu;

--prendo dati anagrafici vecchio op
select array_to_json(array_agg(  a.*)) into temp0  from
(
select 'select * from opu_operatore op join opu_rel_operatore_soggetto_fisico rel on op.id = rel.id_operatore join opu_soggetto_fisico sogg on rel.id_soggetto_fisico = sogg.id where op.id = :idvecchioop: ' as QUERY_USATA, op.*, sogg.* from opu_operatore op join  opu_rel_operatore_soggetto_fisico rel
on op.id = rel.id_operatore join opu_soggetto_fisico sogg on rel.id_soggetto_fisico = sogg.id
where op.id = esito.id_impresa_opu
) a ;

--prendo dati anagrafici nuovo op
select array_to_json(array_agg(  a.*)) into temp1 from
(
select 'select * from opu_operatore op join opu_rel_operatore_soggetto_fisico rel on op.id = rel.id_operatore join opu_soggetto_fisico sogg on rel.id_soggetto_fisico = sogg.id where op.id = :idnuovoop: ' as QUERY_USATA, op.*, sogg.* from opu_operatore op join  opu_rel_operatore_soggetto_fisico rel
on op.id = rel.id_operatore join opu_soggetto_fisico sogg on rel.id_soggetto_fisico = sogg.id
where op.id = esito.id_impresa_opu
) a ;

--prendo i dati stabilimento
select (array_to_json(array_agg(a.*))) into temp2 from (select 'select * from opu_stabilimento where id = :idstabilimentoopu: ' as query_usata, opu_stabilimento.* from opu_stabilimento where opu_stabilimento.id =  idstabilimentoopu ) a  ;

 


insert into variaz_titolarita_nocessazione(id_stabilimento,id_operatore_vecchio,id_operatore_nuovo,data_operazione,id_user,dati_stabilimento,dati_vecchio_operatore,dati_nuovo_operatore) 
values(
	idstabilimentoopu
	,idImpresaVecchio
	,esito.id_impresa_opu
	,CURRENT_TIMESTAMP
	,idutenteasl,
	temp2,
	temp0,
	temp1
);

msgStorico = (select * from public_functions.suap_storico_richieste_insert(idstabilimentoopu,idrichiesta,idutenteasl));
---------------------------------------------------------------------------------------------------------------------

/*mantendo la relazione tra il nuovo e il vecchio tramite il numero di registrazione*/
update opu_stabilimento  set  codiceinternoreg =numregvariazione where id = stab.id ;
 
update ticket set id_stabilimento =stab.id where  alt_id =(select alt_id from suap_ric_scia_stabilimento where id_operatore=idrichiesta);

partitaIvaNuova := (select partita_iva from suap_ric_scia_operatore where id=idrichiesta);


if(partitaIvaVecchia<>partitaIvaNuova) then 
update ticket set data_chiusura = current_timestamp, partita_iva_vecchia = partitaIvaVecchia,
note_internal_use_only = concat(note_internal_use_only,'***** Impostata la data_chiusura = ' || current_timestamp || ' successivamente alla validazione della variazione titolarità con id '|| idrichiesta ||' fatta da utente ' || idutenteasl || ' .') 
where tipologia = 11 and id_stabilimento = idstabilimentoopu and data_chiusura is null and trashed_date is null;
end if;

altId :=(select alt_id from suap_ric_scia_stabilimento where id_operatore=idRichiesta);

esito.id_stabilimento_opu:= idstabilimentoopu;--idStabCreato;
 raise info 'esito.id_stabilimento_opu %', esito.id_stabilimento_opu;

esito.id_esito:=1;
update suap_ric_scia_operatore set validato = true where id=idRichiesta;
raise info 'update suap_ric_scia_operatore set validato = true where id = %',idRichiesta;



return esito ;

else
update suap_ric_scia_operatore set validato = true where id=idRichiesta;
update suap_ric_scia_stabilimento set    validazione_note  =notevalidazione, stato_validazione=statoValidazione,
validazione_da = idutenteasl
where id_operatore=idRichiesta;
esito.id_esito:=1;
return esito ;
end if;




 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public_functions.suap_validazione_scia_variazione_titolarita(integer, integer, integer, integer, text)
  OWNER TO postgres;




