



-- CHI: Bartolo Sansone
--  COSA: Operazioni tipo da eseguire nell'import di un macello
--  QUANDO: 26/01/2015


--Import
 update m_capi set id_macello = 20154900 where id_macello = 51251
 update m_capi_sedute set id_macello = 20154900 where id_macello = 51251
 update m_vpm_tamponi set id_macello =20171925 where id_macello = 51251
 update m_partite set id_macello =20171925 where id_macello = 51251;
update m_partite_sedute set id_macello =20171925 where id_macello = 51251;

 
  
