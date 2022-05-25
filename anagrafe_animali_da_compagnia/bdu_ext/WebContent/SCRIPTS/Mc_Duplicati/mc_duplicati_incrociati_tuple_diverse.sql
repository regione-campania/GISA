with lista_mc as 
(
select id as id_mc, microchip as microchip1
from animale 
where trashed_date is null and 
data_cancellazione is null and
trim(microchip) <>  and
microchip is not null
) 
select  || id_mc ||   || id ||  microchip1, tatuaggio 
from animale, lista_mc
where microchip1 = tatuaggio and 
id_mc <> id


select a.id, a.microchip, a.tatuaggio,  a.data_nascita, s.description as specie, t.description as taglia, m.description as mantello, r.description as razza, asl.description as asl, a.data_inserimento, u.username as inserito_da
from animale a
left join lookup_specie s on s.code = a.id_specie
left join lookup_taglia t on t.code = a.id_taglia
left join lookup_mantello m on m.code = a.id_tipo_mantello
left join lookup_razza r on r.code = a.id_razza
left join lookup_site_id asl on asl.code = a.id_asl_riferimento
left join opu_relazione_stabilimento_linee_produttive relprop on relprop.id = a.id_proprietario
left join opu_stabilimento staprop on staprop.id = relprop.id_stabilimento
left join opu_operatore prop on prop.id = staprop.id_operatore
left join opu_relazione_stabilimento_linee_produttive reldet on reldet.id = a.id_detentore
left join opu_stabilimento stadet on stadet.id = reldet.id_stabilimento
left join opu_operatore det on det.id = stadet.id_operatore
left join access u on u.user_id = a.utente_inserimento
where a.id in 
(5646266,
5654769,
5705281,
5708365,
5718860,
5724660,
5729881,
5775034,
5781300,
5783091,
5787896,
5802426,
6172894,
5858593,
5893372,
5898859,
5913353,
5917514,
5926152,
5934486,
5941244,
5945618,
5950337,
5953934,
5963620,
5974481,
5982396,
5990771,
5991051,
6009371,
6013339,
6013437,
6016475,
6025770,
6027632,
6027963,
6029858,
6029962,
6030111,
6033886,
6035237,
6050739,
6052444,
6137041,
6067049,
6085249,
6089659,
6089778,
6089989,
6090572,
6090963,
6091011,
6100872,
6106396,
6111093,
6119257,
6119459,
6120746,
5646304,
6036286,
6052000,
5772130,
5718862,
5891780,
5729981,
6092664,
5771296,
5783115,
5802952,
5797471,
6171307,
6157268,
6105243,
6012203,
6121509,
5926152,
5917514,
5752941,
5948334,
5942350,
6126560,
6019857,
5761491,
5894094,
6024117,
5989914,
5989608,
6006802,
6019807,
5744064,
5813229,
5890325,
6035623,
6034913,
5878770,
6037186,
6050739,
6030538,
5656751,
6030111,
5925203,
6136914,
5936921,
6075747,
6089048,
6029564,
5971056,
5883327,
6106396,
5942399,
6104232,
6090963,
6155939,
6119459,
6119257,
6124882
) 
       order by a.microchip, a.tatuaggio
