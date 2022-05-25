select * from opu_soggetto_fisico where indirizzo_id in (select id from opu_indirizzo where comune = 5279 and (cap is null or cap = '' or cap = '80100') and via <> '' and via is not null --and via ilike '%ghisleri%'
order by via);

select * from opu_stabilimento where id_indirizzo in (select id from opu_indirizzo where comune = 5279 and (cap is null or cap = '' or cap = '80100') and via <> '' and via is not null --and via ilike '%ghisleri%'
order by via);
select * from opu_operatore where id in (select id_operatore from opu_stabilimento where id_indirizzo in (select id from opu_indirizzo where comune = 5279 and (cap is null or cap = '' or cap = '80100') and via <> '' and via is not null --and via ilike '%ghisleri%'
order by via))

select * from opu_relazione_operatore_sede where id_indirizzo in (select id from opu_indirizzo where comune = 5279 and (cap is null or cap = '' or cap = '80100') and via <> '' and via is not null --and via ilike '%ghisleri%'
order by via);
select * from opu_operatore where id in (select id_operatore from opu_relazione_operatore_sede where id_indirizzo in (select id from opu_indirizzo where comune = 5279 and (cap is null or cap = '' or cap = '80100') and via <> '' and via is not null --and via ilike '%ghisleri%'
order by via))


"RMNPLA73C70F839K"
"NNNSFN72T44F839J"
"SCPSNT87H66F839E"
"GCCMNN77R42F839A"
"DMNFNC68L02C495B"
"SCPSNT87H66F839E"
