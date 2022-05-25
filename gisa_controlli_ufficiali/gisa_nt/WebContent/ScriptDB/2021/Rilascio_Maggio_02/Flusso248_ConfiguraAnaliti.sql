

alter table analiti add column codice_modello text;
update analiti set codice_modello = 'modello_2' where analiti_id in (2283,1,12,8); --modello_2 analisi genetica, batteriologico, virologico, micologico --> sarebbe il nostro modello 2
update analiti set codice_modello = 'modello_3' where analiti_id in (3,2,1898); -- modello_3 biotossicologico, altro, chimico --> sarebbe il nostro modello 3
update analiti set codice_modello = 'modello_4' where analiti_id in (5,10); -- modello_4 fisico, parassitologico --> sarebbe il nostro modello 4 fisico


CREATE OR REPLACE FUNCTION public.is_previsto_verbale_campione( IN _id_campione integer,
    IN _codice_modello text)
  RETURNS boolean AS
$BODY$
DECLARE
conta_modello integer;
conta_org integer;
BEGIN

	select org_id into conta_org from organization where trashed_date is null and org_id in (select org_id from ticket where ticketid = _id_campione) and tipologia = 201; -- test 1620138

	if(conta_org > 0 and conta_org is not null and _codice_modello = 'modello_1') then
		return true;
	elsif (conta_org > 0 and conta_org is not null and _codice_modello <> 'modello_1') then
		return false;
	else 
	
		select count(*) into conta_modello 
		from analiti_campioni ac
		left join analiti a4 on ac.analiti_id = a4.analiti_id --and a4.livello = 4
		left join analiti a3 on (a3.analiti_id = a4.id_padre or a3.analiti_id = ac.analiti_id) --and a3.livello = 3
		left join analiti a2 on a2.analiti_id = a3.id_padre ---and a2.livello = 2
		left join analiti a1 on a1.analiti_id = a2.id_padre and a1.livello = 1
		where ac.id_campione = _id_campione and a1.codice_modello = _codice_modello;
		

		raise info '%', conta_modello;
		
		IF (conta_modello > 0) then
			return true;
		else
			return false;
		end if;

	end if;

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.is_previsto_verbale_campione(integer,text)
  OWNER TO postgres;

--select * from moduli_campioni_html_fields  
alter table moduli_campioni_html_fields  RENAME tipo_analita  TO tipo_modulo;

update analiti set livello=3 where livello = 2 and id_padre in (select analiti_id from analiti where livello = 2);
update analiti set livello=4 where livello = 3 and id_padre in (select analiti_id from analiti where livello = 3);

--select * from is_previsto_verbale_campione(1623965, 'modello_2')
--select * from is_previsto_verbale_campione(1621918, 'modello_3')
--select * from is_previsto_verbale_campione(1624208, 'modello_4')
