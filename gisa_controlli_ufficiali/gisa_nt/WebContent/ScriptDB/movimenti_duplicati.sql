--DUPLICATI
select count(*) ,data_movimentazione ,id_tipo_movimentazione ,num_apiari_spostati ,id_stabilimento_apiario_origine , id_bda_apiario_destinazione , coalesce(num_alveari_da_spostare,'0') as num_alveari_da_spostare ,coalesce(num_sciami_da_spostare,'0') asnum_sciami_da_spostare , coalesce(num_pacchi_da_spostare,'0')  as num_pacchi_da_spostare ,coalesce(num_regine_da_spostare,'0') as num_regine_da_spostare
from apicoltura_movimentazioni
WHERE trashed_date is null
group by data_movimentazione , id_tipo_movimentazione, num_apiari_spostati , id_stabilimento_apiario_origine , id_bda_apiario_destinazione , coalesce(num_alveari_da_spostare,'0') , coalesce(num_sciami_da_spostare,'0')  , coalesce(num_pacchi_da_spostare,'0') , coalesce(num_regine_da_spostare,'0')
having count(data_movimentazione) > 1 
order by data_movimentazione asc



--VISTA DUPLICATI
drop view apicoltura_movimentazioni_duplicati;
create view apicoltura_movimentazioni_duplicati as (select count(*) ,data_movimentazione , id_tipo_movimentazione , num_apiari_spostati , id_stabilimento_apiario_origine , id_bda_apiario_destinazione , num_alveari_da_spostare ,num_sciami_da_spostare ,num_pacchi_da_spostare ,num_regine_da_spostare 
from apicoltura_movimentazioni
WHERE trashed_date is null
group by data_movimentazione , id_tipo_movimentazione , num_apiari_spostati , id_stabilimento_apiario_origine , id_bda_apiario_destinazione , num_alveari_da_spostare , num_sciami_da_spostare , num_pacchi_da_spostare , num_regine_da_spostare
having count(data_movimentazione) > 1 
order by data_movimentazione asc)


--CORREGGI DUPLICATI
WITH subRequest as (
select * , coalesce(num_alveari_da_spostare,'0') as coal_num_alveari_da_spostare  ,coalesce(num_sciami_da_spostare,'0') as coal_num_sciami_da_spostare ,coalesce(num_pacchi_da_spostare,'0') as coal_num_pacchi_da_spostare ,coalesce(num_regine_da_spostare,'0') as coal_num_regine_da_spostare
from apicoltura_movimentazioni 
where (data_movimentazione , id_tipo_movimentazione , num_apiari_spostati , id_stabilimento_apiario_origine , id_bda_apiario_destinazione , coalesce(num_alveari_da_spostare,'0') ,coalesce(num_sciami_da_spostare,'0') ,coalesce(num_pacchi_da_spostare,'0') ,coalesce(num_regine_da_spostare,'0') ) in 
(
  select  data_movimentazione , id_tipo_movimentazione , num_apiari_spostati , id_stabilimento_apiario_origine , id_bda_apiario_destinazione , coalesce(num_alveari_da_spostare,'0') ,coalesce(num_sciami_da_spostare,'0') ,coalesce(num_pacchi_da_spostare,'0')  ,coalesce(num_regine_da_spostare,'0')  from apicoltura_movimentazioni_duplicati
)
FOR UPDATE
)
UPDATE apicoltura_movimentazioni as p set note = concat(p.note , '. Cancellato da help desk perchè duplicato in data ', now()) , trashed_date = now()
FROM subRequest
where p.id in 
             (
               select id 
               from apicoltura_movimentazioni p2
               where p2.data_movimentazione = subRequest.data_movimentazione and 
		     p2.id_tipo_movimentazione = subRequest.id_tipo_movimentazione and 
		     p2.num_apiari_spostati  = subRequest.num_apiari_spostati and 
		     p2.id_stabilimento_apiario_origine  = subRequest.id_stabilimento_apiario_origine and 
		     p2.id_bda_apiario_destinazione  = subRequest.id_bda_apiario_destinazione and 
		     coalesce(p2.num_alveari_da_spostare, '0')  = subRequest.coal_num_alveari_da_spostare and
		     coalesce(p2.num_sciami_da_spostare, '0')  = subRequest.coal_num_sciami_da_spostare and
		     coalesce(p2.num_pacchi_da_spostare, '0')  = subRequest.coal_num_pacchi_da_spostare and
		     coalesce(p2.num_regine_da_spostare, '0')  = subRequest.coal_num_regine_da_spostare and
		     trashed_date is null
	       order by entered desc 
	       limit 1
	     );



--VERIFICA UPDATE FATTI
select * from apicoltura_movimentazioni where trashed_date is not null order by trashed_date desc 
select * from apicoltura_movimentazioni where id in (5,6,7,8,9,10,11,12,13,14 )

--RIPRISTINO MOVIMENTAZIONI CANCELLATE
update apicoltura_movimentazioni set trashed_date = null where trashed_date >= '12/11/2018'



--VINCOLO
drop index duplicati_movimentazioni;
CREATE UNIQUE INDEX duplicati_movimentazioni
ON apicoltura_movimentazioni (data_movimentazione , id_tipo_movimentazione , num_apiari_spostati , id_stabilimento_apiario_origine , id_bda_apiario_destinazione , coalesce(num_alveari_da_spostare,'0') , coalesce(num_sciami_da_spostare,'0'), coalesce(num_pacchi_da_spostare,'0') , coalesce(num_regine_da_spostare,'0')) 
WHERE (trashed_date IS NULL)




