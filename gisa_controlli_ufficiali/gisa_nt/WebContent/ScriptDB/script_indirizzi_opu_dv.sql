SET CLIENT_ENCODING TO 'SQL_ASCII';

/* da lanciare solo la prima volta
drop table  IF EXISTS opu_indirizzo_backup;
create table opu_indirizzo_backup as select * from opu_indirizzo;
alter table opu_indirizzo add column clean  text;
alter table opu_indirizzo add column topon  text;
alter table opu_indirizzo add column via_old  text;
*/
 
update opu_indirizzo set clean='';

--copia in via i rec non vuoti e li ripulisce dei char not ascii
update opu_indirizzo set via_old=via ; 
update opu_indirizzo set via=left(via,position('ÃƒÂ' in via)-1)||'A'|| right(via,length(via)- position('ÃƒÂ' in via) - length('ÃƒÂ')), clean=clean || '-Z' where via similar to '%\303\203\302\240%';
update opu_indirizzo  set via =regexp_replace(via,'Ã‚Â°',''),clean=clean || '-70' where via similar to '%Ã‚Â°%';
update opu_indirizzo  set via =regexp_replace(via,'Â°',''),clean=clean || '-71' where via similar to '%Â°%';
update opu_indirizzo  set via =regexp_replace(via,'ÃƒÂ¬','A'),clean=clean||'-72' where via similar to '%ÃƒÂ¬%';
update opu_indirizzo  set via =regexp_replace(via,'Ã','A'),clean=clean||'-73' where via similar to '%Ã%';
--localita 3
update opu_indirizzo set via = regexp_replace(via,'LOC\.TA''','LOCALITA '), clean=clean||'-L3' where via similar to 'LOC.TA''%';
--localita 4
update opu_indirizzo set via = regexp_replace(via,'ÃƒÂ','LOCALITA'), clean=clean||'-L4' where via similar to '%ÃƒÂ%' ;
--localita 6
update opu_indirizzo set via = regexp_replace(via,'LOCALITÃ','LOCALITA'), clean=clean||'-L6' where via similar to 'LOCALITÃ%' ;
--localita 9
update opu_indirizzo set via = regexp_replace(via,'LOCALITÃƒÂ','LOCALITA'), clean=clean||'-L9' where via similar to 'LOCALITÃƒÂ%';
--localita 10
update opu_indirizzo set via = regexp_replace(via,'LOCALITÃƒÂ‚Ï¿½','LOCALITA'), clean=clean||'-L10' where via similar to 'LOCALITÃƒÂ‚Ï¿½%' ;

--
update opu_indirizzo set via=upper(trim(via));

--eliminazione punti linee e toponimi doppi
update opu_indirizzo  set via =regexp_replace(via,'(\.{0,}|-|\^)\s?',''),clean=clean||'-73' where via similar to '(.|-)%';
update opu_indirizzo set via = regexp_replace(via,'VIA VIALE','VIALE') ,clean=clean||'-74' where via similar to 'VIA VIALE%'; 
update opu_indirizzo set via = regexp_replace(via,'VIA VIA','VIA'), clean=clean||'-75' where via similar to 'VIA VIA %';
update opu_indirizzo set via = regexp_replace(via,'VIE','VIA'), clean=clean||'-76' where via similar to 'VIE %';
update opu_indirizzo set via = substring(via,position(' ' in via)+1), clean=clean||'-77' where via similar to 'VIA VICO(LO)?\s%';
update opu_indirizzo set via = substring(via,position(' ' in via)+1), clean=clean||'-78' where via similar to '%VIA C(.|/|\\)DA%';
update opu_indirizzo set via = substring(via,position(' ' in via)+1), clean=clean||'-79' where via similar to '%VIA C(.|/|\\)SO %';

--normalizzazione nomi 
update opu_indirizzo set via = regexp_replace(via,'16/0?9/1943','16 SETTEMBRE 1943'), clean=clean||'-V0' where via similar to '%16/0?9/1943%' ;

--normalizzazione toponimi
--viale 1
update opu_indirizzo set via = regexp_replace(via,'V\.LE','VIALE'), clean=clean||'-V1' where via similar to 'V.LE%' ;

--via
update opu_indirizzo set via=regexp_replace(via,'V\.(\s){0,}','VIA '),clean=clean||'-V2' where via similar to 'V.(\s){0,}%'  ;

--corso 1
update opu_indirizzo set via = regexp_replace(via,'C(\.|/|\s){1,}SO','CORSO'), clean=clean||'-C1' where via similar to 'C(.|/|\s){1,}SO%';
--corso 2
update opu_indirizzo set via = regexp_replace(via,'C\.{0,}(\s){0,}UMBERTO','CORSO UMBERTO'), clean=clean||'-C2' where via similar to 'C.{0,}(\s){0,}UMBERTO%';

--piazza 1
update opu_indirizzo set via = regexp_replace(via,'P(\.|/|\s|Z){0,}ZA','PIAZZA'), clean=clean||'-P1' where via similar to 'P(.|/|\s|Z){0,}ZA%';
--piazza 2
update opu_indirizzo set via = regexp_replace(via,'P\.VITTORIO','PIAZZA VITTORIO'), clean=clean||'-P2' where via similar to 'P.VITTORIO%';
--piazza 3
update opu_indirizzo set via = regexp_replace(via,'PAZZA','PIAZZA'), clean=clean||'-P3' where via similar to 'PAZZA%';
--piazza 4
update opu_indirizzo set via = regexp_replace(via,'PIAZA','PIAZZA'), clean=clean||'-P4' where via similar to 'PIAZA%';

--piazzetta 0
update opu_indirizzo set via = regexp_replace(via,'P\.TTA','PIAZZETTA'), clean=clean||'-P0' where via similar to 'P.TTA%' ;

--contrada 1
update opu_indirizzo set via = regexp_replace(via,'C(\\|7|-|,|\.|/|\s|Z){0,}DA','CONTRADA'), clean=clean||'-C1' where via similar to 'C(\\|7|-|,|.|/|\s|Z){0,}DA%';
--contrada 2
update opu_indirizzo set via = regexp_replace(via,'CONTRDA','CONTRADA'), clean=clean||'-C2' where via similar to 'CONTRDA%';
--contrada 3
update opu_indirizzo set via = regexp_replace(via,'CONTRADFA','CONTRADA'), clean=clean||'-C3' where via similar to 'CONTRADFA%';
--contrada 4
update opu_indirizzo set via = regexp_replace(via,'CONTRADA','CONTRADA '), clean=clean||'-C4' where upper(trim(via)) similar to 'CONTRADA[A-Z]%';
--contrada 5
update opu_indirizzo set via = regexp_replace(upper(via),'CONTRADA\.','CONTRADA'), clean=clean||'-C5' where upper(trim(via)) similar to 'CONTRADA.%';

--frazione 1
update opu_indirizzo set via = regexp_replace(via,'FRAZ\.{0,}(\s){0,}','FRAZIONE '), clean=clean||'-F1' where via similar to 'FRAZ.{0,}%';

--largo 1
update opu_indirizzo set via = regexp_replace(via,'L\.GO','LARGO'), clean=clean||'-L0' where via similar to 'L.GO%';

--localita 1
update opu_indirizzo set via = regexp_replace(via,'LOC\.?\s{1,}','LOCALITA '), clean=clean||'-L1' where via similar to 'LOC.?(\s){0,}%' ;
--localita 2
update opu_indirizzo set via = regexp_replace(via,'L\.TA','LOCALITA'), clean=clean||'-L3' where via similar to 'L.TA%' ;

--localita 5
update opu_indirizzo set via = regexp_replace(via,'LOCALITA''','LOCALITA'), clean=clean||'-L5' where via similar to 'LOCALITA''%' ;
--localita 7
update opu_indirizzo set via = regexp_replace(via,'LOCALIA','LOCALITA'), clean=clean||'-L7' where via similar to 'LOCALIA %' ;
--localita 8
update opu_indirizzo set via = regexp_replace(via,'LOCALIATA(''){0,}','LOCALITA'), clean=clean||'-L8' where via similar to 'LOCALIATA(''){0,}%';

--provinciale 1
update opu_indirizzo set via = regexp_replace(via,'PR(OV){0,}\.(LE){0,}','PROVINCIALE'), clean=clean||'-R1' where via similar to 'PR(OV){0,}.(LE){0,}%';
--provinciale 2
update opu_indirizzo set via = regexp_replace(via,'S\.{0,}P.\.{0,}','PROVINCIALE'), clean=clean||'-R2' where via similar to 'S.{0,}P.{0,}%' ;
--provinciale 3
update opu_indirizzo set via = regexp_replace(via,'S\.PROVINCIALE','PROVINCIALE'), clean=clean||'-R3' where via similar to 'S.PROVINCIALE%' ;
--provinciale 4
update opu_indirizzo set via = regexp_replace(via,'S\.PROVINCIALE','PROVINCIALE'), clean=clean||'-R4' where via similar to 'STRADA PROVINCIALE%' ;

--statale 1
update opu_indirizzo set via = regexp_replace(via,'S\.{0,}S\.{0,}(\s){0,}','STATALE '), clean=clean||'-S1' where via similar to 'S.{0,}S.{0,}(\s){0,}%' ;
--statale 4
update opu_indirizzo set via = regexp_replace(via,'ST(\s){1,}','STATALE '), clean=clean||'-S4' where via similar to 'ST(\s){0,}%';

--traversa 1
update opu_indirizzo set via = regexp_replace(via,'TR(A){0,}V\.(\s){0,}','TRAVERSA '), clean=clean||'-T1' where via similar to 'TR(A){0,}V.(\s){0,}%' ;

-- fine normalizzazione

--estrazione (e rimozione) toponimoc appoggio
update opu_indirizzo  set topon=trim(left(via,position(' ' in  via))), via=trim(substring(via,position(' ' in via)+1))
where id in (select id from opu_indirizzo  where left(via,position( ' ' in via)-1) in 
  ('CALATA','CONTRADA','CORSO','DISCESA','FRAZIONE','LARGHETTO','LARGO','LITORANEA','LOCALITA','LUNGOMARE','PIAZZA','PIAZZALE','PIAZZETTA','PROVINCIALE','SALITA','STATALE','STRADA','TRAVERSA','VARIANTE','VIA','VIALE','VICO','VICOLETTO','VICOLO')
  );

--casi particolari
UPDATE opu_indirizzo  set civico='SNC',clean=clean||'-X'  where (civico is null or civico='') and via similar to '%16 SETTEMBRE 1943';

--valorizzazione toponimo ufficiale
--with (select code,toponimo from lookup_toponimi, opu_indirizzo where  descrizione ilike '%'||topon||'%') as cte 
update  opu_indirizzo set clean=clean||'-K', toponimo=(select code from lookup_toponimi  where topon is not null and description ilike '%'||opu_indirizzo.topon||'%' limit 1);

--estrazione civico [A-Z]{0,}/?[0-9]{1,}/?[A-Z]{0,}$
UPDATE opu_indirizzo  set civico=trim(substring( via from '[A-Z]{0,}/?[0-9]{1,}/?[A-Z]{0,}$')),clean=clean||'-N1' 
	where (civico is null or civico='') ;

--estrazione via (rimozione numero) 
update opu_indirizzo  set via =trim(regexp_replace(via,'[A-Z]{0,}/?[0-9]{1,}/?[A-Z]{0,}$','')), clean=clean||'-Y' where clean not similar to '%-X%';
update opu_indirizzo  set via =regexp_replace(via,'\s(S|N)\.?$','');
update opu_indirizzo  set via =regexp_replace(via,',$','');


--ripulitura finale
update opu_indirizzo  set via =regexp_replace(via,',{0,}S\.{0,}N*\.{0,}C*\.*$',''),clean=clean||'-U1' where via similar to '%,{0,}S\.{0,}N*\.{0,}C*\.$' ;
update opu_indirizzo  set via =regexp_replace(via,',{0,}S\.{0,}N*\.{0,}C*\.*$',''),clean=clean||'-U2' where via similar to '%SNC' ;


select id,via_old as OLD, topon,toponimo, via, civico , clean from opu_indirizzo where topon is not null and length (via)>3 order by 2 limit 1000; 

/*
select comune||via||civico as i, count(comune||via||civico) from opu_indirizzo where length(via)>0 group by i  order by 2 desc limit 100

select description from lookup_toponimi  where description in
  ('CALATA','CONTRADA','CORSO','DISCESA','FRAZIONE','LARGHETTO','LARGO','LITORANEA','LOCALITA','LUNGOMARE','PIAZZA','PIAZZALE','PIAZZETTA','PROVINCIALE','SALITA','STATALE','STRADA','TRAVERSA','VARIANTE','VIA','VIALE','VICO','VICOLETTO','VICOLO')
order by 1
*/

