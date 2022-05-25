
-- DB: GISA
-- Estraggo le coordinate di tutti i canili in OPU

select
o.id_stabilimento, o.ragione_sociale, i.via, c.nome, i.latitudine, i.longitudine, 'update opu_indirizzo set latitudine ='||latitudine||', longitudine='||longitudine||' where id in (select id_indirizzo from opu_stabilimento where id_stabilimento_gisa = '||id_stabilimento||');'
from opu_operatori_denormalizzati_view o 
left join opu_indirizzo i on i.id = o.id_indirizzo
left join comuni1 c on c.id = i.comune
 WHERE attivita ilike '%canile%'
 
 -- Aggiorno le coordinate su BDU di tutti i canili che hanno id_stabilimento_gisa in questa lista: lancio l'ultima colonna sul DB: BDU
 
 
 -- DB: BDU
 -- Estrazione canili: controllo se ci sono canili senza coordinate
 
 select
o.id_rel_stab_lp, o.ragione_sociale, i.via, c.nome, i.latitudine, i.longitudine, s.id_stabilimento_gisa
from opu_operatori_denormalizzati o 
left join opu_relazione_stabilimento_linee_produttive rel on rel.id = o.id_rel_stab_lp
left join opu_stabilimento s on s.id = rel.id_stabilimento
left join opu_indirizzo i on i.id = s.id_indirizzo
left join comuni1 c on c.id = i.comune
 WHERE o.id_opu_operatore >= 0 AND  o.id_linea_produttiva in ( 5) and (i.latitudine <=0 or i.longitudine <=0)


-- DB: GISA
-- Estraggo le coordinate di tutti i canili in ORGANIZATION

select 
a.latitude, a.longitude
,o.org_id_canina, o.name, 'update opu_indirizzo set latitudine = '||a.latitude||', longitudine = '||a.longitude||' where id in (select s.id_indirizzo from opu_operatore o left join opu_stabilimento s on o.id = s.id_operatore left join opu_indirizzo i on i.id = s.id_indirizzo where o.id = '||o.org_id_canina||');'
 from organization o left join organization_address a on o.org_id = a.org_id
 where o.tipologia = 10 and o.trashed_date is null and o.import_opu is not true
 and a.address_type = 5
 order by a.latitude desc, a.longitude desc

 -- Aggiorno le coordinate su BDU di tutti i canili che hanno id_operatore = org_id_canina in questa lista: lancio l'ultima colonna sul DB: BDU
 -- Ovviamente per funzionare deve esserci valorizzata la colonna org_id_canina su gisa e deve corrispondere a un opu_operatore esistente in bdu
 
 
 -- Canili mancanti
 
 select * from (

select
o.id_stabilimento, o.ragione_sociale, o.partita_iva, i.via, c.nome, i.latitudine, i.longitudine, 'NUOVA ANAGRAFICA' as anag
from opu_operatori_denormalizzati_view o 
left join opu_indirizzo i on i.id = o.id_indirizzo
left join comuni1 c on c.id = i.comune
 WHERE attivita ilike '%canile%'
 and (i.latitudine <=0 or i.longitudine <=0)

 union

select 
o.org_id, o.name, o.partita_iva, a.addrline1, a.city, a.latitude, a.longitude, 'VECCHIA ANAGRAFICA' as anag
 from organization o left join organization_address a on o.org_id = a.org_id
 where o.tipologia = 10 and o.trashed_date is null and o.import_opu is not true
  and (a.latitude <=0 or a.longitude <=0)
 and a.address_type = 5

 ) aa order by anag

