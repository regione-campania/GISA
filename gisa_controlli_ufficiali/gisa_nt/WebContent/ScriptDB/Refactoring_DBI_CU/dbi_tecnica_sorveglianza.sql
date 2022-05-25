
------------------ DATI CU SORVEGLIANZA ------------------------------
CREATE OR REPLACE FUNCTION public_functions.get_controlli_sorveglianze(
    IN _asl integer DEFAULT NULL::integer,
    IN _anno integer DEFAULT (date_part('year'::text, now()))::integer,
    IN _solo_chiusi boolean DEFAULT true)
  RETURNS TABLE(id_controllo integer, data_inserimento timestamp without time zone, data_inizio_controllo timestamp without time zone, data_chiusura_controllo timestamp without time zone, data_fine_controllo timestamp without time zone, stato_controllo text, asl character varying, riferimento_id integer, riferimento_nome_tab text, id_asl integer, aggiornata_categoria boolean, categoria_rischio integer, data_prossimo_controllo timestamp without time zone) AS
$BODY$
DECLARE

BEGIN

FOR  	id_controllo, 
	data_inserimento, 
	Data_Inizio_Controllo ,
	Data_Chiusura_Controllo,
	data_fine_controllo,
	stato_controllo,
	Asl,
	riferimento_id,
	riferimento_nome_tab,
	id_asl,
	aggiornata_categoria,
	categoria_rischio,
	data_prossimo_controllo

	
in
             select t.ticketid as id_controllo,
     t.entered as data_inserimento,
     t.assigned_date as Data_Inizio_Controllo,
     t.closed as Data_Chiusura_Controllo,
     t.data_fine_controllo,
     case when t.status_id = 1 then 'aperto'
          when t.status_id = 2 then 'chiuso'
              when t.status_id = 3 then 'riaperto'
     end as stato_controllo,
     l.description as Asl,

	  case when t.alt_id > 0 then t.alt_id  
       when t.id_stabilimento > 0 then t.id_stabilimento  
       when t.id_apiario > 0 then t.id_apiario 
       when t.org_id > 0 then t.org_id end as riferimento_id ,

       case when t.alt_id > 0 then (select return_nome_tabella from gestione_id_alternativo(t.alt_id,-1)) 
       when t.id_stabilimento > 0 then 'opu_stabilimento'  
       when t.id_apiario > 0 then 'apicoltura_imprese'
       when t.org_id > 0 then 'organization' end as riferimento_nome_tab,

	l.code as id_asl,
	t.isaggiornata_categoria as aggiornata_categoria,
	t.categoria_rischio as categoria_rischio,
	t.data_prossimo_controllo as data_prossimo_controllo

from ticket t


left join lookup_site_id l on l.code  = t.site_id

     where provvedimenti_prescrittivi = 5 and tipologia = 3 and
trashed_date is null
     and ( _asl is null or t.site_id = _asl )
     and ( extract(year from t.assigned_date) = _anno)
     and ( /*_solo_chiusi */ _solo_chiusi is false or t.closed is not null)
  
    
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
  
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.get_controlli_sorveglianze(integer, integer, boolean)
  OWNER TO postgres;

 ----------------------- PER CONTO DI ---------------------------------------------
 CREATE OR REPLACE FUNCTION public_functions.get_controlli_sorveglianza_percontodi(
    IN _asl integer DEFAULT NULL::integer,
    IN _anno integer DEFAULT (date_part('year'::text, now()))::integer,
    IN _solo_chiusi boolean DEFAULT true)
  RETURNS TABLE(id_controllo integer, stato_controllo text, id_asl integer, asl character varying, struttura_complessa character varying, struttura_semplice character varying, per_conto_di_completo character varying, id_struttura_semplice integer, id_struttura_complessa integer) AS
$BODY$
DECLARE

BEGIN

for
	id_controllo,
	stato_controllo,
	id_asl,
	asl,
	struttura_complessa,
	struttura_semplice,
	per_conto_di_completo,
	id_struttura_semplice,
	id_struttura_complessa

	
in select

	t.ticketid as id_controllo,
	case
		when t.status_id = 1 then 'aperto'
		when t.status_id = 2 then 'chiuso'
		when t.status_id = 3 then 'riaperto'
	end as stato_controllo,
	l.code as  id_asl,
	l.description as asl,
	n.descrizione_padre as struttura_complessa,
	n.descrizione as struttura_semplice,
	n.pathdes as per_conto_di_completo,
	nn.id_padre as id_struttura_semplice,
	uoc.id_unita_operativa as id_struttura_complessa
from 
	ticket t
	left join lookup_site_id l on l.code  = t.site_id
	left join unita_operative_controllo un on un.id_controllo = t.ticketid
	left join dpat_strutture_asl n on un.id_unita_operativa = n.id
	LEFT JOIN dpat_strutture_asl pp ON n.id_padre = pp.id
	LEFT JOIN unita_operative_controllo uoc ON uoc.id_controllo = t.ticketid
	LEFT JOIN dpat_strutture_asl nn ON nn.id = uoc.id_unita_operativa
	LEFT JOIN dpat_strutture_asl ppp ON nn.id_padre = ppp.id
where 
	provvedimenti_prescrittivi = 5 and tipologia = 3 
	and trashed_date is null
    and ( _asl is null or t.site_id = _asl)
    and ( _anno is null or extract(year from t.assigned_date) = _anno)
    and (  _solo_chiusi is false or t.closed is not null)
  
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
  
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.get_controlli_sorveglianza_percontodi(integer, integer, boolean)
  OWNER TO postgres;

  ------------------------- NUCLEO ISPETTIVO ----------------------------
CREATE OR REPLACE FUNCTION public_functions.get_controlli_nucleoispettivo(
    IN _asl integer DEFAULT NULL::integer,
    IN _anno integer DEFAULT (date_part('year'::text, now()))::integer,
    IN _solo_chiusi boolean DEFAULT true,
    IN _provvedimenti_prescrittivi integer DEFAULT NULL::integer)
  RETURNS TABLE(id_controllo integer, data_inizio_controllo text, data_chiusura_controllo text, stato_controllo text, asl character varying, ruolo text, cognome text, nome text, id_componente integer, tecnica_controllo integer) AS
$BODY$
DECLARE

BEGIN

FOR  	id_controllo,
	Data_Inizio_Controllo,
	Data_Chiusura_Controllo,
	stato_controllo,
	Asl,
	ruolo,
	cognome,
	nome,
	id_componente,
	tecnica_controllo

in select 
     t.ticketid as id_controllo,
     t.assigned_date as Data_Inizio_Controllo,
     t.closed as Data_Chiusura_Controllo,
     case when t.status_id = 1 then 'aperto'
          when t.status_id = 2 then 'chiuso'
              when t.status_id = 3 then 'riaperto'
     end as stato_controllo,
	l.description as Asl,

	coalesce (rl.role,rle.role) as ruolo,
	coalesce (co.namelast,coe.namelast) as cognome,
	coalesce (co.namefirst,coe.namefirst) as nome,
	cn.id_componente as id_componente,

	t.provvedimenti_prescrittivi as tecnica_controllo
	
	
	

	from ticket t

left join lookup_site_id l on l.code  = t.site_id
left join cu_nucleo cn on cn.id_controllo_ufficiale = t.ticketid
left join access_ ac on ac.user_id = cn.id_componente
left join contact co on co.contact_id = ac.contact_id
left join role rl on rl.role_id = ac.role_id
left join access_ext_ ace on ace.user_id = cn.id_componente
left join contact_ext_ coe on coe.contact_id = ace.contact_id
left join role_ext rle on rle.role_id = ace.role_id


where t.tipologia = 3 and t.trashed_date is null
     and ( _asl is null or t.site_id = _asl )
     and ( _anno is null or date_part('year'::text, t.assigned_date) = _anno)
     and ( /*_solo_chiusi */ _solo_chiusi is false or t.closed is not null)
     and (_provvedimenti_prescrittivi is null or t.provvedimenti_prescrittivi = _provvedimenti_prescrittivi)
     
    
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
  
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.get_controlli_nucleoispettivo(integer, integer, boolean, integer)
  OWNER TO postgres;

 -------------------------- CHECKLIST SORVEGLIANZA ----------------------
 -- Function: public_functions.get_checklist_sorveglianza()

-- DROP FUNCTION public_functions.get_checklist_sorveglianza();

CREATE OR REPLACE FUNCTION public_functions.get_checklist_sorveglianza()
  RETURNS TABLE(id integer, id_controllo integer, is_principale boolean, stato text, livello_rischio integer) AS
$BODY$
DECLARE

BEGIN
RETURN QUERY

	select a.id, a.id_controllo, a.is_principale, a.stato, a.livello_rischio
	from audit a 
	where a.trashed_date is null order by 2;

   
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.get_checklist_sorveglianza()
  OWNER TO postgres;

  
