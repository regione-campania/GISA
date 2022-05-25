--B42_A e B42_B dal configuratore DPAT PMACQ

select * from dpat_indicatore_new where anno=2022 and alias_indicatore ilike '%B42_%'
update dpat_indicatore_new set codice_esame  = 'PMACQ' where id in(10871,10872)
update lookup_piano_monitoraggio set codice_esame  = 'PMACQ' where code in(10871,10872)
select * from matrici where matrice_id=1161

alter table matrici add column codice_arpac text;
alter table analiti add column codice_arpac text;

update matrici set codice_arpac = 'ACQUA POTABILE-A03DK' where matrice_id=1161;
update matrici set codice_arpac = codice_esame where matrice_id=1;


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
    idanalita integer, -- è la matrice!!!
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

     insert into matrici_campioni (id_campione,id_matrice,cammino) values(idticket,idanalita,pathanalita);




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

