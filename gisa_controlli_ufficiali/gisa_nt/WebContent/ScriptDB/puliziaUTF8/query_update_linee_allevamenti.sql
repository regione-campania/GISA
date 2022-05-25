select 'update linee_nuove_allevamenti set id_nuova_linea_produttiva='||o.id_nuova_linea_attivita||' where org_id='||l.org_id||';' ,* 
from linee_nuove_allevamenti_view  l 
join opu_linee_attivita_nuove o on o.attivita=l.nuova_attivita
where o.enabled and livello = 3 

