
select a.microchip, a.data_nascita, s.description as specie, t.description as taglia, m.description as mantello, r.description as razza, asl.description as asl, a.data_inserimento, u.username as inserito_da
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
where a.microchip in 

('380260041174137',
      '380260020079611',
      '380260040183551',
      '380260040741142',
      '380260040742578',
      '380260100107379',
      '900062000150341',
      '900062000152712',
      '380260002068526',
      '968000001998520',
      '380260060021436',
      '941000001157908',
      '941000002736649',
      '953000010086404',
       '380260060022150',
       '380260080200628',
       '941000001705512',
       '941000001813441',
       '941000002122176',
       '941000001142439',
       '941000001166991',
       '941000001826335',
       '968000001962715',
       '968000001983578',
       '982000145472231') 
       order by a.microchip

