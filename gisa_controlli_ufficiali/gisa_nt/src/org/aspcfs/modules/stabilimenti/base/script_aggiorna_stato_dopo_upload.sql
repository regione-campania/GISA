/*script da lanciare dopo l'aggiornamento stabilimenti riconosciuti per aggiornamento stato dei sospesi*/
select org_id , stato_lab, descrizione_stato_attivita from organization o
join stabilimenti_sottoattivita st on (o.org_id = st.id_stabilimento)
where progressivo_stabilimento in (17917
,2129
,2129
,17266
,17265
,20348
,20719
,17369
,671
,17314
,17305
,17782
,9151
,22561
,2029
,2029
,17840
,22304
,16869
,17014
,22599
,23073
,16979
,22618
,14326
,16996
,1367
,1709
,1709
,1709
,16995
) and tipologia = 3 order by id_stabilimento;


update organization set stato_lab = 2 where org_id in (
select org_id from organization o
join stabilimenti_sottoattivita st on (o.org_id = st.id_stabilimento)
where progressivo_stabilimento in (17917
,2129
,2129
,17266
,17265
,20348
,20719
,17369
,671
,17314
,17305
,17782
,9151
,22561
,2029
,2029
,17840
,22304
,16869
,17014
,22599
,23073
,16979
,22618
,14326
,16996
,1367
,1709
,1709
,1709
,16995
) and tipologia = 3 order by id_stabilimento);

