--script del 24/11 -> rif. mail Fwd: GISA: Flusso 010 - migrazione anagrafiche a ML8 - 15:22
select * from opu_relazione_stabilimento_linee_produttive  where id_linea_produttiva_old  =8087 -- MG-OPRA-M01 40243
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml='MG-OPRA-M01', id_linea_produttiva  = 40243 where id_linea_produttiva_old  =8087;
update suap_ric_scia_relazione_stabilimento_linee_produttive set codice_univoco_ml='MG-OPRA-M01', id_linea_produttiva  = 40243 where id_linea_produttiva_old  =8087;

select * from opu_relazione_stabilimento_linee_produttive  where id_linea_produttiva_old  =8083 -- MS.090-TCT-T 
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml='MS.090-TCT-T', id_linea_produttiva  = 40267 where id_linea_produttiva_old  =8083;
update suap_ric_scia_relazione_stabilimento_linee_produttive set codice_univoco_ml='MS.090-TCT-T', id_linea_produttiva  = 40267 where id_linea_produttiva_old  =8083;

select * from opu_relazione_stabilimento_linee_produttive  where id_linea_produttiva_old  = 8086 -- MS.090-TCT-T 
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml='MS.090-TCT-T', id_linea_produttiva  = 40267 where id_linea_produttiva_old  =8086;
update suap_ric_scia_relazione_stabilimento_linee_produttive set codice_univoco_ml='MS.090-TCT-T', id_linea_produttiva = 40267  where id_linea_produttiva_old  =8086;

select * from opu_relazione_stabilimento_linee_produttive  where id_linea_produttiva_old  in (8094,8088,8090) -- MG-DG
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml='MG-DG', id_linea_produttiva  =20070  where id_linea_produttiva_old in (8094,8088,8090);
update suap_ric_scia_relazione_stabilimento_linee_produttive set codice_univoco_ml='MG-DG', id_linea_produttiva = 20070 where id_linea_produttiva_old in (8094,8088,8090);