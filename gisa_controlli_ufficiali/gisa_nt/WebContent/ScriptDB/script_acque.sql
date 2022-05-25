--chi: Antonio Liguori
--cosa: tipi e dbi per inserimento cu acque di rete
--quando: 06/12/2018


-- Type: public.codice

-- DROP TYPE public.codice;

CREATE TYPE public.codice AS
   (codice_interno text,
    flag_condizionalita text);
ALTER TYPE public.codice
  OWNER TO postgres;


-- Type: public.piano

-- DROP TYPE public.piano;

CREATE TYPE public.piano AS
   (tipo integer,
    code integer,
    description character varying,
    id integer,
    descrizione_lunga text,
    id_unita_operativa boolean);
ALTER TYPE public.piano
  OWNER TO postgres;



-- Function: public.insert_controllo_ufficiale_acque(integer, integer, integer, timestamp without time zone, integer, integer, integer, timestamp without time zone, integer, text, text, text, text, integer, integer, integer[], text[], integer, text, integer, integer, text[], text[], text[], boolean[], boolean[], boolean[], boolean[], text[], boolean[], boolean[], integer[], integer[], integer[], integer, integer[], integer[])

-- DROP FUNCTION public.insert_controllo_ufficiale_acque(integer, integer, integer, timestamp without time zone, integer, integer, integer, timestamp without time zone, integer, text, text, text, text, integer, integer, integer[], text[], integer, text, integer, integer, text[], text[], text[], boolean[], boolean[], boolean[], boolean[], text[], boolean[], boolean[], integer[], integer[], integer[], integer, integer[], integer[]);

CREATE OR REPLACE FUNCTION public.insert_controllo_ufficiale_acque(
    orgid integer,
    enteredby integer,
    modifiedby integer,
    assigned_date timestamp without time zone,
    siteid integer,
    provvedimentiprescrittivi integer,
    punteggio integer,
    datafinecontrollo timestamp without time zone,
    esitocontrollo integer,
    ipentered text,
    ipmodified text,
    tipocontrollo text,
    problemcu text,
    motivazione_campione integer,
    motivazione_piano_campione integer,
    idfoglia integer[],
    decsrprot text[],
    idanalita integer,
    pathanalita text,
    indice integer,
    sizecampioni integer,
    temperatura text[],
    cloro text[],
    ore text[],
    prot_routine boolean[],
    prot_verifica boolean[],
    prot_replica_micro boolean[],
    prot_replica_chim boolean[],
    altro text[],
    prot_radioattivita boolean[],
    prot_ricerca_fitosanitari boolean[],
    tipoispezione integer[],
    ispezioni integer[],
    componente integer[],
    sizecomponente integer,
    unitaoper integer[],
    piani integer[])
  RETURNS integer AS
$BODY$
DECLARE 

_idcontrollo integer;
_idcampione integer;
--esitoInsertPuntoPrelievo BOOLEAN;
puntoPrelievo integer;
esitoInsertNucleo BOOLEAN;

BEGIN

_idcontrollo := (SELECT * FROM public.insert_controllo(orgid,enteredby ,  modifiedby ,  assigned_date,    siteid ,    provvedimentiprescrittivi ,  punteggio ,    datafinecontrollo ,    esitocontrollo ,    ipentered ,    ipmodified , tipocontrollo, problemCu ));


  FOR i IN 1.. sizecampioni LOOP

_idcampione := (select * from public.insert_campione (orgid,assigned_date,motivazione_campione,motivazione_piano_campione,ipentered ,    ipmodified ,enteredby ,  modifiedby ,siteid,_idcontrollo::text,    idfoglia ,decsrprot,idanalita,pathanalita,i));
puntoPrelievo :=  (select * from public.insert_controllo_punto_prelievo(_idcontrollo,_idcampione,orgid,temperatura[i],cloro[i],ore[i], prot_routine[i], prot_verifica[i], prot_replica_micro[i] ,    prot_replica_chim[i] ,    altro[i] ,    prot_radioattivita[i] ,    prot_ricerca_fitosanitari[i]));

RAISE INFO 'puntoPrelievo: %',puntoPrelievo;


end loop;

esitoInsertNucleo := (select * from public.insert_controlliuff(_idcontrollo,tipoispezione ,modifiedby,ispezioni ,_idcampione,componente,sizeComponente, unitaOper, piani));

--esitoInsertNucleo := (select * from public.insert_controlliuff(_idcontrollo,tipoispezione ,pianomonitoraggio,modifiedby,ispezione ,_idcampione));

RETURN _idcontrollo;
	

       

END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.insert_controllo_ufficiale_acque(integer, integer, integer, timestamp without time zone, integer, integer, integer, timestamp without time zone, integer, text, text, text, text, integer, integer, integer[], text[], integer, text, integer, integer, text[], text[], text[], boolean[], boolean[], boolean[], boolean[], text[], boolean[], boolean[], integer[], integer[], integer[], integer, integer[], integer[])
  OWNER TO postgres;
  
  

-- Function: public.insert_controllo_punto_prelievo(integer, integer, integer, text, text, text, boolean, boolean, boolean, boolean, text, boolean, boolean)

-- DROP FUNCTION public.insert_controllo_punto_prelievo(integer, integer, integer, text, text, text, boolean, boolean, boolean, boolean, text, boolean, boolean);

CREATE OR REPLACE FUNCTION public.insert_controllo_punto_prelievo(
    id_controllo integer,
    id_campione integer,
    org_id_pdp integer,
    temperatura text,
    cloro text,
    ore text,
    prot_routine boolean,
    prot_verifica boolean,
    prot_replica_micro boolean,
    prot_replica_chim boolean,
    altro text,
    prot_radioattivita boolean,
    prot_ricerca_fitosanitari boolean)
  RETURNS integer AS
$BODY$
DECLARE 

idPrelievo integer;

BEGIN

insert into controlli_punti_di_prelievo_acque_rete (
id_controllo,
id_campione,
org_id_pdp,
temperatura,
cloro,
ore,
prot_routine,
prot_verifica, 
prot_replica_micro,
prot_replica_chim,
altro, 
prot_radioattivita, 
prot_ricerca_fitosanitari)

values
(
id_controllo,id_campione,
org_id_pdp,
temperatura,
cloro,
ore,
prot_routine,
prot_verifica,
prot_replica_micro,
prot_replica_chim,
altro,
prot_radioattivita,
prot_ricerca_fitosanitari
)
RETURNING id into idPrelievo;

RETURN idPrelievo;

END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.insert_controllo_punto_prelievo(integer, integer, integer, text, text, text, boolean, boolean, boolean, boolean, text, boolean, boolean)
  OWNER TO postgres;
  
  
-- Function: public.insert_campione(integer, timestamp without time zone, integer, integer, text, text, integer, integer, integer, text, integer[], text[], integer, text, integer)

-- DROP FUNCTION public.insert_campione(integer, timestamp without time zone, integer, integer, text, text, integer, integer, integer, text, integer[], text[], integer, text, integer);

CREATE OR REPLACE FUNCTION public.insert_campione(
    orgid integer,
    assigneddate timestamp without time zone,
    motivazione_campione integer,
    motivazione_piano_campione integer,
    ipentered text,
    ipmodified text,
    enteredby integer,
    modifiedby integer,
    siteid integer,
    idcontrolloufficiale text,
    idfoglia integer[],
    decsrprot text[],
    idanalita integer,
    pathanalita text,
    indice integer)
  RETURNS integer AS
$BODY$
DECLARE 

siglaProvIdCu text;
idTemp text;
asl text;
idticket integer;
foglia integer;
protocollo text;
RET BOOLEAN;

BEGIN

INSERT INTO ticket (
problem,
org_id,
id_stabilimento,
id_apiario,
alt_id, 
site_id,
ip_entered,
ip_modified, 
ticketid, 
motivazione_campione,
motivazione_piano_campione,
note_motivazione,
tipo_richiesta,
custom_data,
enteredBy,
modifiedBy,
assigned_date,
tipologia, 
sanzioni_penali ,
allerta, 
segnalazione, 
non_conformita, 
id_controllo_ufficiale, 
punteggio,
check_specie_mangimi,
location)


VALUES (
'PRELIEVO CAMPIONE PER RICERCA ACQUE DI RETE INSERITO DA CONTROLLO',
orgId, 
0,
0, 
0, 
SiteId, 
IpEntered,
IpModified, 
(select max(ticketid) +1 from ticket where ticketid <10000000),
Motivazione_campione,
Motivazione_piano_campione,
'',
'',
'', 
EnteredBy,
ModifiedBy,
assigneddate,
2, 
1 ,
'0',
'0', 
'0',
IdControlloUfficiale,
0,
'0',
concat_ws('',0,(select nextval('public.barcode_osa_serial_id_seq')+1))) 
RETURNING ticketid into idticket;

if (idticket > 0) then


IF SiteId = 201 THEN
       asl := 'AV';
ELSIF SiteId = 202 THEN
       asl := 'BN';
ELSIF SiteId = 203 THEN
       asl := 'CE';
ELSIF SiteId = 204 THEN
       asl := 'NA1';
ELSIF SiteId = 205 THEN
       asl := 'NA2Nord';
ELSIF SiteId = 206 THEN
       asl := 'NA3Sud';
ELSIF SiteId = 207 THEN
       asl := 'SA';
ELSE
      asl := 'FuoriRegione';
END IF;

--CASE
--  WHEN SiteId= 201 THEN
--             asl := 'AV';
--  WHEN SiteId=202 THEN
--             asl := 'BN';
--  WHEN SiteId=203 THEN
--             asl := 'CE';
--  WHEN SiteId=204 THEN
--             asl = 'NA1';
--  WHEN SiteId=205 THEN
--             asl = 'NA2Nord';
--  WHEN SiteId=206 THEN
--             asl = 'NA3Sud';
--  WHEN SiteId=207 THEN
--             asl = 'SA';
--  ELSE	
-- 	  asl := 'FuoriRegione';
--  END CASE;


RAISE INFO 'asl %',asl;
RAISE INFO 'IdControlloUfficiale %',IdControlloUfficiale;
RAISE INFO 'idticket %',idticket;

siglaProvIdCu :=concat(asl,IdControlloUfficiale, idticket);

update ticket set identificativo =  siglaProvIdCu where ticketid = idticket;

  --FOR i IN 1.. size LOOP
 
            foglia = idFoglia[indice];
            RAISE INFO 'idfogli %',foglia;
	    protocollo = decsrProt[indice];
	    RAISE INFO 'protocollo %',protocollo;

	    insert into analiti_campioni (id_campione,analiti_id,cammino) values(idticket,foglia,protocollo);

	    
   --END LOOP;

     insert into matrici_campioni (id_campione,id_matrice,cammino) values(idticket,foglia,pathanalita);




RAISE INFO 'siglaProvIdCu %',siglaProvIdCu;

--ret:=true;

RETURN idticket;
	
end if;
       
RETURN -1;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.insert_campione(integer, timestamp without time zone, integer, integer, text, text, integer, integer, integer, text, integer[], text[], integer, text, integer)
  OWNER TO postgres;

-- Function: public.insert_controlliuff(integer, integer[], integer, integer[], integer, integer[], integer, integer[], integer[])

-- DROP FUNCTION public.insert_controlliuff(integer, integer[], integer, integer[], integer, integer[], integer, integer[], integer[]);

CREATE OR REPLACE FUNCTION public.insert_controlliuff(
    _idcontrollo integer,
    tipoispezione integer[],
    modifiedby integer,
    _ispezione integer[],
    idcampione integer,
    componente integer[],
    sizecomponente integer,
    unitaoper integer[],
    piani integer[])
  RETURNS boolean AS
$BODY$
DECLARE 
_id_unita_operativa integer;
tipo_ispezione integer;
esito BOOLEAN;
dim integer;
dime integer;
idPiano integer;
pianoM piano;
idUo integer;
cod_interno codice;
BEGIN


dim:= array_length(tipoispezione,1);
RAISE INFO 'dim tipoIsp %',dim;

FOR i IN 1.. dim loop

tipo_ispezione:= tipoispezione[i];
RAISE INFO 'TIPO ISPEZIONE %',tipo_ispezione;
select distinct codice_interno::text ,null as flag_condizionalita INTO cod_interno from lookup_tipo_ispezione where code = tipo_ispezione;

RAISE INFO 'codice Interno %',cod_interno.codice_interno;

if ( lower(cod_interno.codice_interno)='2a') then



idPiano := piani[i];
idUo := unitaOper[i];


end if;

	

INSERT INTO tipocontrolloufficialeimprese(
idcontrollo,
tipo_audit,
bpi, 
haccp, 
tipoispezione, 
pianomonitoraggio,
ispezione,
audit_tipo,
id_unita_operativa,
oggetto_audit,
enabled,
modified,
modifiedby,
id_lookup_condizionalita
 )  
VALUES (
_idcontrollo,
-1,
-1, 
-1, 
tipo_ispezione, 
idPiano, 
-1, 
-1,
idUo,   
-1,
true,
current_timestamp,
modifiedby,
-1 
);

end loop;
delete from tipocontrolloufficialeimprese where idcontrollo = _idcontrollo and ispezione >0;

dime:= array_length(_ispezione,1);
RAISE INFO 'dim Ispezio%',dime;

FOR j IN 1.. dime loop

INSERT INTO tipocontrolloufficialeimprese(idcontrollo, tipo_audit, bpi, haccp, tipoispezione, pianomonitoraggio,ispezione,audit_tipo)  VALUES (_idcontrollo,-1, -1, -1, -1, -1, _ispezione[j], -1);
end loop;
raise info 'idCampione update %',idCampione;
update ticket set tipo_controllo = '- Ispezione: <br> <b>Motivi dell''''Ispezione</b> : <br> PIANO DI MONITORAGGIO<br> <br> Piani di monitoraggio<br>.<br>' where ticketid=_idcontrollo;



RAISE INFO 'dimComp: %', sizeComponente;

FOR k IN 1..sizeComponente LOOP
insert into cu_nucleo(id, id_controllo_ufficiale, id_componente) values ((SELECT nextval('public.cu_nucleo_id_seq')), _idcontrollo, componente[k]);
END LOOP; 


RETURN esito;
	

       

END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.insert_controlliuff(integer, integer[], integer, integer[], integer, integer[], integer, integer[], integer[])
  OWNER TO postgres;


-- Function: public.insert_controllo(integer, integer, integer, timestamp without time zone, integer, integer, integer, timestamp without time zone, integer, text, text, text, text)

-- DROP FUNCTION public.insert_controllo(integer, integer, integer, timestamp without time zone, integer, integer, integer, timestamp without time zone, integer, text, text, text, text);

CREATE OR REPLACE FUNCTION public.insert_controllo(
    orgid integer,
    enteredby integer,
    modifiedby integer,
    assigned_date timestamp without time zone,
    siteid integer,
    provvedimentiprescrittivi integer,
    punteggio integer,
    datafinecontrollo timestamp without time zone,
    esitocontrollo integer,
    ipentered text,
    ipmodified text,
    tipocontrollo text,
    problem text)
  RETURNS integer AS
$BODY$
DECLARE 

idControllo integer;

BEGIN

INSERT INTO ticket (
org_id,
enteredby,
modifiedby,
ticketid,
assigned_date,
status_id,
site_id,
tipologia,
provvedimenti_prescrittivi,
id_controllo_ufficiale,
punteggio,
data_fine_controllo,
esito_controllo,
ip_entered,
ip_modified,
tipo_controllo,
problem,
isaggiornata_categoria ,
flag_mod5)


VALUES (
    orgid,
    enteredby,
    modifiedby,
   (select max(ticketid) +1 from ticket where ticketid <10000000),
    assigned_date,
    1,
    siteid,
    3,
    provvedimentiprescrittivi,
    (select max(ticketid)+1 from ticket where ticketid <10000000),
    punteggio,
    datafinecontrollo,
    esitocontrollo,
    ipentered,
    ipmodified,
    tipocontrollo,
    problem,
    false,
    true
)
RETURNING ticketid into idControllo;



RETURN idControllo;
	

       

END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.insert_controllo(integer, integer, integer, timestamp without time zone, integer, integer, integer, timestamp without time zone, integer, text, text, text, text)
  OWNER TO postgres;
