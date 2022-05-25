
select data_caricamento,count(*) as conta from  public_functions.dbi_bdu_caricamenti_mc_lp(-1) 
--where data_caricamento > '01/01/2016'
group by data_caricamento 
order by conta desc




select login_utente_caricamento,count(*) as conta from  public_functions.dbi_bdu_caricamenti_mc_lp(-1) where data_caricamento = '2013-11-28 00:00:00' group by login_utente_caricamento 
order by conta desc





update microchips set data_caricamento = '2010-04-11 00:00:00' where microchip in 
(

select microchip  from  public_functions.dbi_bdu_caricamenti_mc_lp(-1) where data_caricamento = '2010-10-12 00:00:00' and login_utente_caricamento = '20409'
limit 200


)






