--indirizzi di Napoli corretti
select dugt_cap || ' '  || topo_cap, *  from tab_cap order by 1

--indirizzi da bonificare
select * from opu_indirizzo where comune = 5279 and (cap is null or cap = '' or cap = '80100') order by via

--indirizzi da bonificare matchati senza civico, non nulli e non via = 'NAPOLI'
select 'update opu_indirizzo set cap = ''' || c.capi_cap || ''', note_internal_use_only = concat(note_internal_use_only, ''. ' || now() || ' - Flusso 162: aggiornato cap da ' || case when ind.cap is null then '' else ind.cap end || ' a ' || c.capi_cap || ''') where id = ' || ind.id || ';', ind.via, c.dugt_cap || ' '  || c.topo_cap , levenshtein(c.dugt_cap || ' '  || c.topo_cap, ind.via) 
from opu_indirizzo ind, tab_cap c
where ind.comune = 5279 and 
(ind.cap is null or ind.cap = '' or ind.cap = '80100') and 
levenshtein(c.dugt_cap || ' '  || c.topo_cap, ind.via) <5 and
(c.nciv_cap is null or c.nciv_cap = '' ) and
ind.via is not null and ind.via <> '' and
upper(ind.via) <> 'NAPOLI'
order by 4

--indirizzi da bonificare matchati con civico, non nulli e non via = 'NAPOLI'
select 'update opu_indirizzo set cap = ''' || c.capi_cap || ''', note_internal_use_only = concat(note_internal_use_only, ''. ' || now() || ' - Flusso 162: aggiornato cap da ' || case when ind.cap is null then '' else ind.cap end || ' a ' || c.capi_cap || ''') where id = ' || ind.id || ';', ind.via, c.dugt_cap || ' '  || c.topo_cap , c.nciv_cap, NULLIF(regexp_replace(ind.via, '\D','','g'), '')::numeric, levenshtein(c.dugt_cap || ' '  || c.topo_cap, ind.via) 
from opu_indirizzo ind, tab_cap c
where ind.comune = 5279 and 
(ind.cap is null or ind.cap = '' or ind.cap = '80100') and 
levenshtein(c.dugt_cap || ' '  || c.topo_cap, ind.via) <5 and
(c.nciv_cap is not null and c.nciv_cap <> '' ) and
ind.via is not null and ind.via <> '' and
(NULLIF(regexp_replace(ind.via, '\D','','g'), '')::numeric%2 =0) = c.pari and NULLIF(regexp_replace(ind.via, '\D','','g'), '')::numeric>= c.civ_inf::integer and NULLIF(regexp_replace(ind.via, '\D','','g'), '')::numeric<= c.civ_sup::integer and
upper(ind.via) <> 'NAPOLI'
order by 2