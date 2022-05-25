--colonna di appoggio per costruire la concatenazione via || cap || provincia || comune || toponimo || civico;
alter table opu_indirizzo add column via_cap_prov_com_topo_civico text;
update opu_indirizzo  set via_cap_prov_com_topo_civico  = via || cap || provincia || comune || toponimo || civico;

--conto i duplicati di indirizzi
--960 record
select count(via_cap_prov_com_topo_civico)as num_duplicati, via_cap_prov_com_topo_civico, ''''||via_cap_prov_com_topo_civico ||'',''
from opu_indirizzo where (via is not null and via !='') and (cap is not null and cap !='') and (provincia is not null and provincia !='') and (toponimo is not null and toponimo > 0) and 
(civico is not null and civico !='')
group by via_cap_prov_com_topo_civico
having count(via_cap_prov_com_topo_civico) =2
order by num_duplicati

create table opu_indirizzo_temp as (
select * from opu_indirizzo 
where via_cap_prov_com_topo_civico in(<qui copio tutti i valori restituiti dalla precedente>))



select * from opu_indirizzo  where via_cap_prov_com_topo_civico ='840226554632091'