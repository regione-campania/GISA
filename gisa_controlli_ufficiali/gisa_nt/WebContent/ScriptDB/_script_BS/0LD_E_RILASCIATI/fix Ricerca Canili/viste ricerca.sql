CREATE VIEW lista_opu_operatori_canili as
select * from opu_operatori_denormalizzati_view where codice_attivita ilike 'IUV-CAN-CAN';


CREATE VIEW lista_opu_operatori_commerciali as
select * from opu_operatori_denormalizzati_view where codice_attivita in ('IUV-CODAC-VED', 'IUV-CODAC-VEDCG', 'IUV-COIAC-VEI')