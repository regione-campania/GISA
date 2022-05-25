drop table  public.rel_checklist_sorv_ml;

CREATE TABLE public.rel_checklist_sorv_ml
(
  id serial,
  id_linea integer,
  id_checklist integer,
  enabled boolean default true
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.rel_checklist_sorv_ml
  OWNER TO postgres;

insert into rel_checklist_sorv_ml (id_linea, id_checklist) values (40564,2023);
insert into rel_checklist_sorv_ml (id_linea, id_checklist) values (40566,2023);

CREATE SCHEMA giava
  AUTHORIZATION postgres;

DROP FUNCTION digemon.get_checklist_by_idlinea(integer);
CREATE OR REPLACE FUNCTION giava.get_checklist_by_idlinea(IN _id_linea_ml integer)
  RETURNS TABLE(code integer, description text, short_description character varying, enabled boolean, versione double precision, num_chk integer) AS
$BODY$
DECLARE

	linea_categorizzabile boolean;
	conta_res integer;
BEGIN
	linea_categorizzabile := (select categorizzabili from master_list_flag_linee_attivita  where id_linea = _id_linea_ml);

	if linea_categorizzabile then

		conta_res := (select count(*) 
				from lookup_org_catrischio c
				left join rel_checklist_sorv_ml rel on rel.id_checklist = c.code
				where c.enabled and rel.enabled and rel.id_linea = _id_linea_ml);
				
		if conta_res > 0 then
			
			RETURN QUERY
				select c.code, c.versione_alfonso, c.short_description, c.enabled, c.versione, c.num_chk
				from lookup_org_catrischio c
				left join rel_checklist_sorv_ml rel on rel.id_checklist = c.code
				where c.enabled and rel.enabled and rel.id_linea = _id_linea_ml;
		else
			RETURN QUERY
			     select -1, '', ''::character varying, false, -1::double precision, -1 from lookup_org_catrischio limit 1;

		end if;
	else 
		return; 
	end if;

   END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION giava.get_checklist_by_idlinea(integer)
  OWNER TO postgres;


--select * from digemon.get_checklist_by_idlinea(40568) 

select * from lookup_org_catrischio  where enabled

alter table lookup_org_catrischio add column versione_alfonso text;
alter table lookup_org_catrischio add column num_chk integer;

update lookup_org_catrischio set versione_alfonso='AZIENDE ZOOTECNICHE CHE ALLEVANO SUINI DA RIPRODUZIONE E INGRASSO ', num_chk=1 where description='Aziende zoot all. suidi';
update lookup_org_catrischio set versione_alfonso='AZIENDE ZOOTECNICHE CHE ALLEVANO OVI-CAPRINI DA CARNE', num_chk=2 where description='Aziende zoot ov.caprine da carne';
update lookup_org_catrischio set versione_alfonso='AZIENDE ZOOTECNICHE CHE ALLEVANO BOVINI E/O BUFALINI DA RIPRODUZIONE E DA INGRASSO ', num_chk=3 where description='Aziende zoot. bov buf da carne';
update lookup_org_catrischio set versione_alfonso='AZIENDE ZOOTECNICHE CHE PRODUCONO LATTE DESTINATO AL TRATTAMENTO E/O ALLA TRASFORMAZIONE', num_chk=4 where description='Aziende zoot. prod. latte';
update lookup_org_catrischio set versione_alfonso='CENTRO DEPURAZIONE MOLLUSCHI BIVALVI ', num_chk=5 where description='CDM (rev. 3)';
update lookup_org_catrischio set versione_alfonso='CENTRO SPEDIZIONE MOLLUSCHI BIVALVI, ECHINODERMI, TUNICATI, GASTEROPODI MARINI ', num_chk=6 where description='CSM (rev. 3)';
update lookup_org_catrischio set versione_alfonso='CANILI', num_chk=7 where description='Canili';
update lookup_org_catrischio set versione_alfonso='CENTRO IMBALLAGGIO UOVA', num_chk=8 where description='Centro imballaggio uova';
update lookup_org_catrischio set versione_alfonso='CENTRO RACCOLTA LATTE', num_chk=9 where description='Centro raccolta latte (rev. 3)';
update lookup_org_catrischio set versione_alfonso='CENTRO GALLEGIANTE DI SPEDIZIONE  MOLLUSCHI BIVALVI, ECHINODERMI, TUNICATI, GASTEROPODI MARINI', num_chk=10 where description='CSM galleggiante (rev. 3)';
update lookup_org_catrischio set versione_alfonso='DEPOSITO PRODOTTI ALIMENTARI  ', num_chk=11 where description='Deposito ingrosso (rev. 3)';
update lookup_org_catrischio set versione_alfonso='ESERCIZIO DI SOMMINISTRAZIONE TIPO "A" ', num_chk=12 where description='Esercizio somministrazione A (rev. 3)';
update lookup_org_catrischio set versione_alfonso='ESERCIZIO DI SOMMINISTRAZIONE TIPO "B" ', num_chk=13 where description='Esercizio somministrazione B (rev. 3)';
update lookup_org_catrischio set versione_alfonso='ESERCIZIO DI VENDITA AL DETTAGLIO', num_chk=14 where description='Esercizio Vendita (rev 3)';
update lookup_org_catrischio set versione_alfonso='LABORATORIO FUNZIONALMENTE ANNESSO AD ESERCIZIO DI VENDITA ', num_chk=15 where description='Laboratorio Annesso Esercizio Vendita (rev. 3)';
update lookup_org_catrischio set versione_alfonso='LABORATORIO RICONOSCIUTO PER LA PRODUZIONE DI PRODOTTI A BASE DI CARNE', num_chk=16 where description='Lab. CE Prodotti Base Carni (rev. 3)';
update lookup_org_catrischio set versione_alfonso='LABORATORIO RICONOSCIUTO PER LA PRODUZIONE DI CARNI MACINATE, PREPARAZIONI DI CARNI E CARNI SEPARATE MECCANICAMENTE (CSM) ', num_chk=17 where description='Lab. CE Preparazione Carni (rev. 3)';
update lookup_org_catrischio set versione_alfonso='LABORATORIO DI SEZIONAMENTO RICONOSCIUTO', num_chk=18 where description='Lab. CE Sezionamento (rev. 3)';
update lookup_org_catrischio set versione_alfonso='LABORATORIO RICONOSCIUTO PER LA PRODUZIONE DI PRODOTTI DELLA PESCA E/O DI PRODOTTI ALIMENTARI A BASE DI PRODOTTI DELLA PESCA', num_chk=19 where description='Prodotti della pesca (rev. 3)';
update lookup_org_catrischio set versione_alfonso='STABILIMENTO PRODUZIONE DI PRODOTTI LATTIERO-CASEARI NON RICONOSCIUTO CE', num_chk=20 where description='Laboratorio prod. lattiero-caseari non ric.(rev 3)';
update lookup_org_catrischio set versione_alfonso='IMPRESA ALIMENTARE RICONOSCIUTA CE PER LA PRODUZIONE DI PRODOTTI LATTIERO-CASEARI', num_chk=21 where description='Laboratorio prod. lattiero-caseari ric. (rev. 3)';
update lookup_org_catrischio set versione_alfonso='LABORATORIO/INDUSTRIA', num_chk=22 where description='Laborat-industr. in genere (rev. 3)';
update lookup_org_catrischio set versione_alfonso='MACELLO AVICOLO', num_chk=23 where description='Macello Avicolo (rev. 3)';
update lookup_org_catrischio set versione_alfonso='MACELLO CUNICOLO E MACELLO  PICCOLA SELVAGGINA  ALLEVATA', num_chk=24 where description='Macelli Lagomorfi (rev. 3.1)';
update lookup_org_catrischio set versione_alfonso='MACELLO UNGULATI DOMESTICI ', num_chk=25 where description='Macelli ungulati (rev. 3)';
update lookup_org_catrischio set versione_alfonso='MANGIMIFICIO', num_chk=26 where description='Mangimificio(rev. 3)';
update lookup_org_catrischio set versione_alfonso='MERCATO ITTICO E IMPIANTI COLLETTIVI PER LE ASTE', num_chk=27 where description='Mercato ittico';
update lookup_org_catrischio set versione_alfonso='MOLINI  ', num_chk=28 where description='Molini';
update lookup_org_catrischio set versione_alfonso='SOMMINISTRAZIONE PASTI PER LA RISTORAZIONE COLLETTIVA', num_chk=29 where description='Rist. collettiva somministrazione';
update lookup_org_catrischio set versione_alfonso='PRODUZIONE PASTI PER LA RISTORAZIONE COLLETTIVA', num_chk=30 where description='Ristor. collettiva produzione';
update lookup_org_catrischio set versione_alfonso='ESERCIZIO DI VENDITA AL DETTAGLIO DI MANGIMI ', num_chk=31 where description='Rivendite di mangimi';
update lookup_org_catrischio set versione_alfonso='STABULARI PER LA SPERIMENTAZIONE ANIMALE', num_chk=32 where description='Sperimentazione animale (rev 4)';

--23-24
update master_list_flag_linee_attivita set num_chk =''  where id_linea=40865;
update rel_checklist_sorv_ml set enabled = false; -- disabilito le checklist attualmente inserite per test 

select 'insert into rel_checklist_sorv_ml(id_linea, id_checklist) values('||id_linea||',(select code from lookup_org_catrischio where enabled and num_chk='||num_chk::int||'));' 
from master_list_flag_linee_attivita where trashed is null and (num_chk <> '' or length(num_chk) > 1) and rev=10 --112 associazioni

-- recupero manuale per 
insert into rel_checklist_sorv_ml(id_linea, id_checklist) values(40865,(select code from lookup_org_catrischio where enabled and num_chk=23));
insert into rel_checklist_sorv_ml(id_linea, id_checklist) values(40865,(select code from lookup_org_catrischio where enabled and num_chk=24));
update master_list_flag_linee_attivita set num_chk ='23-24' where id_linea=40865;


-- TEST DBI
select * from rel_checklist_sorv_ml where enabled
select * from giava.get_checklist_by_idlinea(40885) -- linea categorizzabile
select * from giava.get_checklist_by_idlinea(40576) -- linea categorizzabile ma nn ha checklist associata -1
select * from giava.get_checklist_by_idlinea(40530) -- linea non categorizzabile ma con checklist associata BACO NEL DOMINIO
select * from giava.get_checklist_by_idlinea(40507) -- linea non categorizzabile ma con checklist associata BACO NEL DOMINIO

-- QUERY ANALISI
--query per verificare le anomalie
--select * from master_list_flag_linee_attivita  where categorizzabili =false and num_chk <> '' 
--select * from master_list_flag_linee_attivita  where categorizzabili  and num_chk = '' and rev=10 and trashed is null
--select * from master_list_flag_linee_attivita  where categorizzabili =false and num_chk = ''

-- new import checklist
delete from rel_checklist_sorv_ml --ok
update master_list_flag_linee_attivita set num_chk =''; 

update master_list_flag_linee_attivita set num_chk='15' where codice_univoco='MS.040-MS.040.300-852IT5A201';
update master_list_flag_linee_attivita set num_chk='15' where codice_univoco='MS.040-MS.040.400-852IT5A301';
update master_list_flag_linee_attivita set num_chk='20' where codice_univoco='MS.040-MS.040.600-852IT5A501';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.030-MS.030.100-852IT4A002';
update master_list_flag_linee_attivita set num_chk='11' where codice_univoco='MS.060-MS.060.100-852IT7A003';
update master_list_flag_linee_attivita set num_chk='14' where codice_univoco='MS.060-MS.060.200-852IT7A102';
update master_list_flag_linee_attivita set num_chk='14' where codice_univoco='MS.060-MS.060.200-852IT7A101-B';
update master_list_flag_linee_attivita set num_chk='14' where codice_univoco='MS.060-MS.060.200-852IT7A101-A';
update master_list_flag_linee_attivita set num_chk='14' where codice_univoco='MS.060-MS.060.200-852IT7A101';
update master_list_flag_linee_attivita set num_chk='11' where codice_univoco='MS.060-MS.060.100-852IT7A003';
update master_list_flag_linee_attivita set num_chk='11' where codice_univoco='MS.060-MS.060.100-852IT7A001';
update master_list_flag_linee_attivita set num_chk='11' where codice_univoco='MS.060-MS.060.100-852IT7A001';
update master_list_flag_linee_attivita set num_chk='11' where codice_univoco='MS.060-MS.060.100-852IT7A001';
update master_list_flag_linee_attivita set num_chk='11' where codice_univoco='MS.060-MS.060.100-852IT7A001';
update master_list_flag_linee_attivita set num_chk='14' where codice_univoco='MS.060-MS.060.200-852IT7A103';
update master_list_flag_linee_attivita set num_chk='11' where codice_univoco='MS.080-MS.080.100-10';
update master_list_flag_linee_attivita set num_chk='11' where codice_univoco='MS.080-MS.080.100-2';
update master_list_flag_linee_attivita set num_chk='11' where codice_univoco='MS.070-MS.070.200-852IT8A101';
update master_list_flag_linee_attivita set num_chk='11' where codice_univoco='MS.070-MS.070.100-852IT8A002';
update master_list_flag_linee_attivita set num_chk='11' where codice_univoco='MS.070-MS.070.100-852IT8A001';
update master_list_flag_linee_attivita set num_chk='7' where codice_univoco='IUV-ADCA-ADCA';
update master_list_flag_linee_attivita set num_chk='7' where codice_univoco='IUV-CAN-AAF';
update master_list_flag_linee_attivita set num_chk='7' where codice_univoco='IUV-CAN-ALLCAN';
update master_list_flag_linee_attivita set num_chk='7' where codice_univoco='IUV-CAN-ALLGAT';
update master_list_flag_linee_attivita set num_chk='7' where codice_univoco='IUV-CAN-DEG';
update master_list_flag_linee_attivita set num_chk='7' where codice_univoco='IUV-CAN-PEN';
update master_list_flag_linee_attivita set num_chk='7' where codice_univoco='IUV-CAN-RIFRIC';
update master_list_flag_linee_attivita set num_chk='7' where codice_univoco='IUV-CAN-RIFSAN';
update master_list_flag_linee_attivita set num_chk='26' where codice_univoco='MG-DG-M10';
update master_list_flag_linee_attivita set num_chk='26' where codice_univoco='MG-PDD7-M35';
update master_list_flag_linee_attivita set num_chk='31' where codice_univoco='MG-DG-M40';
update master_list_flag_linee_attivita set num_chk='28' where codice_univoco='MG-DG-M39';
update master_list_flag_linee_attivita set num_chk='31' where codice_univoco='MG-DG-M16';
update master_list_flag_linee_attivita set num_chk='31' where codice_univoco='MG-DG-M16';
update master_list_flag_linee_attivita set num_chk='31' where codice_univoco='MG-DG-M15';
update master_list_flag_linee_attivita set num_chk='31' where codice_univoco='MG-DG-M15';
update master_list_flag_linee_attivita set num_chk='31' where codice_univoco='MG-DG-M15';
update master_list_flag_linee_attivita set num_chk='26' where codice_univoco='MG-PDD7-M36';
update master_list_flag_linee_attivita set num_chk='31' where codice_univoco='MG-DG-M06';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.020-MS.020.100-852IT3A001';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.020-MS.020.500-852IT3A401';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.020-MS.020.400-852IT3A301';
update master_list_flag_linee_attivita set num_chk='20' where codice_univoco='MS.020-MS.020.300-852IT3A201';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.020-MS.020.200-852IT3A102';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.020-MS.020.200-852IT3A101';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.020-MS.020.200-852IT3A101';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.020-MS.020.200-852IT3A101';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='RI.A-RI.A00-RI.A00.000';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.A-MS.A30.100-852ITBA001';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.A-MS.A12-PC-S';
update master_list_flag_linee_attivita set num_chk='11' where codice_univoco='MS.A-MS.A11-ST-SG';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.A-MS.A11-PP-SG';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.A-MS.A11-PC-SG';
update master_list_flag_linee_attivita set num_chk='11' where codice_univoco='MS.A-MS.A10-ST-G';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.A-MS.A30.500-852ITBA';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.A-MS.A30.400-13';
update master_list_flag_linee_attivita set num_chk='11' where codice_univoco='MS.A-MS.A40.100-14';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.A-MS.A40.100-852ITEA001';
update master_list_flag_linee_attivita set num_chk='11' where codice_univoco='MS.A-MS.A40.200-14';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.A-MS.A40.200-852ITEA101';
update master_list_flag_linee_attivita set num_chk='11' where codice_univoco='MS.A-MS.A40.300-14';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.A-MS.A40.300-852ITEA201';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.A-MS.A30.200-13';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.A-MS.A30.300-13';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.A-MS.A10-PP-G';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.A-MS.A10-PC-G';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.A-MS.A12-PP-S';
update master_list_flag_linee_attivita set num_chk='11' where codice_univoco='MS.A-MS.A12-ST-S';
update master_list_flag_linee_attivita set num_chk='11' where codice_univoco='MS.B-MS.B80-MS.B80.200';
update master_list_flag_linee_attivita set num_chk='11' where codice_univoco='MS.B-MS.B00-MS.B00.100';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.B-MS.B00-MS.B00.200';
update master_list_flag_linee_attivita set num_chk='11' where codice_univoco='MS.B-MS.B00-MS.B00.300';
update master_list_flag_linee_attivita set num_chk='25' where codice_univoco='MS.B-MS.B10-MS.B10.100';
update master_list_flag_linee_attivita set num_chk='18' where codice_univoco='MS.B-MS.B10-MS.B10.200';
update master_list_flag_linee_attivita set num_chk='23-24' where codice_univoco='MS.B-MS.B20-MS.B20.100';
update master_list_flag_linee_attivita set num_chk='18' where codice_univoco='MS.B-MS.B20-MS.B20.200';
update master_list_flag_linee_attivita set num_chk='24' where codice_univoco='MS.B-MS.B30-MS.B30.100';
update master_list_flag_linee_attivita set num_chk='18' where codice_univoco='MS.B-MS.B30-MS.B30.200';
update master_list_flag_linee_attivita set num_chk='18' where codice_univoco='MS.B-MS.B40-MS.B40.100';
update master_list_flag_linee_attivita set num_chk='24' where codice_univoco='MS.B-MS.B40-MS.B40.200';
update master_list_flag_linee_attivita set num_chk='17' where codice_univoco='MS.B-MS.B50-MS.B50.100';
update master_list_flag_linee_attivita set num_chk='17' where codice_univoco='MS.B-MS.B50-MS.B50.200';
update master_list_flag_linee_attivita set num_chk='17' where codice_univoco='MS.B-MS.B50-MS.B50.300';
update master_list_flag_linee_attivita set num_chk='16' where codice_univoco='MS.B-MS.B60-MS.B60.100';
update master_list_flag_linee_attivita set num_chk='5' where codice_univoco='MS.B-MS.B70-MS.B70.100';
update master_list_flag_linee_attivita set num_chk='6' where codice_univoco='MS.B-MS.B70-MS.B70.200';
update master_list_flag_linee_attivita set num_chk='19' where codice_univoco='MS.B-MS.B80-MS.B80.500';
update master_list_flag_linee_attivita set num_chk='27' where codice_univoco='MS.B-MS.B80-MS.B80.600';
update master_list_flag_linee_attivita set num_chk='27' where codice_univoco='MS.B-MS.B80-MS.B80.700';
update master_list_flag_linee_attivita set num_chk='9' where codice_univoco='MS.B-MS.B90-MS.B90.100';
update master_list_flag_linee_attivita set num_chk='21' where codice_univoco='MS.B-MS.B90-MS.B90.300';
update master_list_flag_linee_attivita set num_chk='21' where codice_univoco='MS.B-MS.B90-MS.B90.400';
update master_list_flag_linee_attivita set num_chk='8' where codice_univoco='MS.B-MS.BA0-MS.BA0.100';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.B-MS.BA0-MS.BA0.200';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.B-MS.BA0-MS.BA0.300';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.B-MS.BB0-MS.BB0.200';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.B-MS.BC0-MS.BC0.200';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.B-MS.BD0-MS.BD0.100';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.B-MS.BE0-MS.BE0.200';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.B-MS.BF0-MS.BF0.200';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.B-MS.BG0-MS.BG0.100';
update master_list_flag_linee_attivita set num_chk='12' where codice_univoco='MS.050-MS.050.200-852IT6A101';
update master_list_flag_linee_attivita set num_chk='12' where codice_univoco='MS.050-MS.050.200-852IT6A101';
update master_list_flag_linee_attivita set num_chk='13' where codice_univoco='MS.050-MS.050.200-852IT6A102';
update master_list_flag_linee_attivita set num_chk='12' where codice_univoco='MS.050-MS.050.200-852IT6A103';
update master_list_flag_linee_attivita set num_chk='13' where codice_univoco='MS.050-MS.050.200-852IT6A102';
update master_list_flag_linee_attivita set num_chk='30' where codice_univoco='MS.050-MS.050.100-852IT6A001';
update master_list_flag_linee_attivita set num_chk='30' where codice_univoco='MS.050-MS.050.100-852IT6A001';
update master_list_flag_linee_attivita set num_chk='30' where codice_univoco='MS.050-MS.050.100-852IT6A001';
update master_list_flag_linee_attivita set num_chk='29' where codice_univoco='MS.050-MS.050.100-852IT6A002';
update master_list_flag_linee_attivita set num_chk='12' where codice_univoco='MS.050-MS.050.200-1';
update master_list_flag_linee_attivita set num_chk='12' where codice_univoco='MS.050-MS.050.200-1';
update master_list_flag_linee_attivita set num_chk='13' where codice_univoco='MS.050-MS.050.200-10';
update master_list_flag_linee_attivita set num_chk='12' where codice_univoco='MS.050-MS.050.200-8';
update master_list_flag_linee_attivita set num_chk='12' where codice_univoco='MS.050-MS.050.200-8';
update master_list_flag_linee_attivita set num_chk='32' where codice_univoco='SPE-A-ALNAU-ALSC';
update master_list_flag_linee_attivita set num_chk='28' where codice_univoco='MS.010-MS.010.500-852IT2A404';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.010-MS.010.500-852IT2A403';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.010-MS.010.500-852IT2A402';
update master_list_flag_linee_attivita set num_chk='28' where codice_univoco='MS.010-MS.010.500-852IT2A401';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.010-MS.010.200-852IT2A101';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.010-MS.010.100-852IT2A004';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.010-MS.010.100-852IT2A003-B';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.010-MS.010.100-852IT2A003';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.010-MS.010.100-852IT2A002';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.010-MS.010.100-852IT2A001';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.010-MS.010.800-852IT2A701';
update master_list_flag_linee_attivita set num_chk='22' where codice_univoco='MS.010-MS.010.700-852IT2A601';

update master_list_flag_linee_attivita set num_chk =''  where codice_univoco='MS.B-MS.B20-MS.B20.100';
select * from master_list_flag_linee_attivita where codice_univoco = 'MS.B-MS.B20-MS.B20.100' -- 40415, 40866?checkid

select 'insert into rel_checklist_sorv_ml(id_linea, id_checklist) values('||id_linea||',(select code from lookup_org_catrischio where enabled and num_chk='||num_chk::int||'));' 
from master_list_flag_linee_attivita where trashed is null and (num_chk <> '' or length(num_chk) > 1) --and rev=10 --112 associazioni

insert into rel_checklist_sorv_ml(id_linea, id_checklist) values(40866,(select code from lookup_org_catrischio where enabled and num_chk=23));
insert into rel_checklist_sorv_ml(id_linea, id_checklist) values(40866,(select code from lookup_org_catrischio where enabled and num_chk=24));
insert into rel_checklist_sorv_ml(id_linea, id_checklist) values(40415,(select code from lookup_org_catrischio where enabled and num_chk=23));
insert into rel_checklist_sorv_ml(id_linea, id_checklist) values(40415,(select code from lookup_org_catrischio where enabled and num_chk=24));
update master_list_flag_linee_attivita set num_chk ='23-24' where id_linea in (40415, 40866);

---- aggiornamento tabella materializzata
-- revision 27/09/2021 post #6280 prima parte in organization
COALESCE(mltemp2.id_nuova_linea_attivita, mltemp.id_nuova_linea_attivita, ml8.id_nuova_linea_attivita) AS id_attivita, --'-1'::integer AS id_attivita,
 
-- in global_org_view new 27/09 post #6280
    COALESCE(ml8.id_nuova_linea_attivita, mltemp.id_nuova_linea_attivita) AS id_attivita, --'-1'::integer AS id_attivita,
delete from ricerche_anagrafiche_old_materializzata where riferimento_id_nome_tab ='organization';
insert into ricerche_anagrafiche_old_materializzata (select * from ricerca_anagrafiche where riferimento_id_nome_tab ='organization');

-- insert linee tabelle dei flag
--zona di controllo
insert into  master_list_flag_linee_attivita(codice_univoco, rev, categorizzabili, id_linea,  entered, enteredby,note_hd, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam, no_scia) 
values ('IUV-ZDC-ZDC', 8, false, 40479, now(), 6567, 'Linea inserita a posteriore per GIAVA', false, true, false, true, false, false, false, false, false); -- noscia?
-- colonie feline
insert into  master_list_flag_linee_attivita(codice_univoco, rev, categorizzabili, id_linea,  entered, enteredby,note_hd, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam, no_scia) 
values ('IUV-CF-CF', 8, false, 40478, now(), 6567, 'Linea inserita a posteriore per GIAVA', false, true, false, true, false, false, false, false, false); -- noscia?

-- MS.A-MS.A40.100-14
update master_list_flag_linee_attivita set num_chk = '22' where id_linea =40388
insert into rel_checklist_sorv_ml (id_checklist, id_linea, enabled) values(2042,40388,true);
