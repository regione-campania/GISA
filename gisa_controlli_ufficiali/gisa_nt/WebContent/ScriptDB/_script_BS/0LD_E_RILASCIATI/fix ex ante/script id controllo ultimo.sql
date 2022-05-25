
update opu_stabilimento set id_controllo_ultima_categorizzazione = 1022194  where id = 229370;


select max(assigned_date), ticketid, alt_id, 'update sintesis_stabilimento set id_controllo_ultima_categorizzazione = ' || ticketid || ' where alt_id = ' || alt_id || ';' from ticket where provvedimenti_prescrittivi = 5 and alt_id in (
100001753,
100001772,
100001195,
100000370,
100001746,
100000127,
100000352,
100001198,
100000566,
100001437,
100001437,
100001562)
group by ticketid