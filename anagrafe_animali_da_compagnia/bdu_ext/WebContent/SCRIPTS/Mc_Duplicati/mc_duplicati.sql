create table animale_bak_20_gen_16 as select * from animale

--Ricerca mc duplicati
select microchip,count(microchip)
from animale 
where data_cancellazione is null and trashed_date is null and microchip is not null and microchip <> ''
group by microchip
having count(microchip)>1 

--Ricerca mc duplicati e disattivi
select microchip,count(microchip)
from animale 
where data_cancellazione is null and trashed_date = '01/01/1970' and microchip is not null and microchip <> ''
group by microchip
having count(microchip)>1 

--Ricerca mc duplicati dopo la disabilitazione
select microchip,count(microchip)
from animale 
where data_cancellazione is null and trashed_date = '01/01/1970' and microchip is not null and microchip <> ''
group by microchip
having count(microchip)>1 

--Ricerca mc con eventi
select a.id as id_animale, a.microchip, a.data_inserimento as data_inserimento_microchip, tipo_reg.description as registrazione, ev.entered as data_inserimento_registrazione
from animale a
left join evento ev on ev.id_animale = a.id and ev.data_cancellazione is null
left join lookup_tipologia_registrazione tipo_reg on tipo_reg.code = ev.id_tipologia_evento
where a.microchip in
(
select microchip
from animale 
where data_cancellazione is null and trashed_date is null and microchip is not null and microchip <> ''
group by microchip
having count(microchip)>1 
)

--Ricerca mc con eventi dopo la disabilitazione
select a.id as id_animale, a.microchip, a.data_inserimento as data_inserimento_microchip, tipo_reg.description as registrazione, ev.entered as data_inserimento_registrazione
from animale a
left join evento ev on ev.id_animale = a.id and ev.data_cancellazione is null
left join lookup_tipologia_registrazione tipo_reg on tipo_reg.code = ev.id_tipologia_evento
where a.microchip in
(
select microchip
from animale 
where data_cancellazione is null and trashed_date = '01/01/1970' and microchip is not null and microchip <> ''
group by microchip
having count(microchip)>1 
)

--Disabilitazione mc duplicati
update animale 
set note_internal_use_only = '.Cancellato temporaneamente in data 20/01/2016 per gestire i ###duplicati###',
trashed_date = '01/01/1970'   
where microchip in
(
select microchip--,count(microchip)
from animale 
where data_cancellazione is null and trashed_date is null and microchip is not null and microchip <> ''
group by microchip
having count(microchip)>1 
)


--Ricerca tatuaggi duplicati
select tatuaggio,count(tatuaggio)
from animale 
where data_cancellazione is null and trashed_date is null and tatuaggio is not null and tatuaggio <> ''
group by tatuaggio
having count(tatuaggio)>1 

--Ricerca tatuaggi duplicati dopo la disabilitazione
select tatuaggio,count(tatuaggio)
from animale 
where data_cancellazione is null and trashed_date = '01/01/1970' and tatuaggio is not null and tatuaggio <> ''
group by tatuaggio
having count(tatuaggio)>1

--Ricerca tatuaggi con eventi
select a.id as id_animale, a.tatuaggio, a.data_inserimento as data_inserimento_microchip, tipo_reg.description as registrazione, ev.entered as data_inserimento_registrazione
from animale a
left join evento ev on ev.id_animale = a.id and ev.data_cancellazione is null
left join lookup_tipologia_registrazione tipo_reg on tipo_reg.code = ev.id_tipologia_evento
where a.tatuaggio in
(
select tatuaggio
from animale 
where data_cancellazione is null and trashed_date is null and tatuaggio is not null and tatuaggio <> ''
group by tatuaggio
having count(tatuaggio)>1 
)

--Ricerca tatuaggi con eventi dopo la disabilitazione
select a.id as id_animale, a.microchip, a.tatuaggio,a.data_inserimento as data_inserimento_microchip, tipo_reg.description as registrazione, 
ev.entered as data_inserimento_registrazione,
a.data_nascita,
a.sesso,
a.id_taglia,
a.id_tipo_mantello
from animale a
left join evento ev on ev.id_animale = a.id and ev.data_cancellazione is null
left join lookup_tipologia_registrazione tipo_reg on tipo_reg.code = ev.id_tipologia_evento
where a.tatuaggio in
(
select tatuaggio
from animale 
where data_cancellazione is null and trashed_date = '01/01/1970' and tatuaggio is not null and tatuaggio <> ''
group by tatuaggio
having count(tatuaggio)>1 
) 
order by tatuaggio, microchip








--Riabilitazione tatuaggi con microchip valorizzato
with lista as (select a.id as id_animale, a.microchip, a.tatuaggio ,a.data_inserimento as data_inserimento_microchip, tipo_reg.description as registrazione, 
ev.entered as data_inserimento_registrazione,
a.data_nascita,
a.sesso,
a.id_taglia,
a.id_tipo_mantello,
row_number() over(order by a.id) as n
from animale a
left join evento ev on ev.id_animale = a.id and ev.data_cancellazione is null
left join lookup_tipologia_registrazione tipo_reg on tipo_reg.code = ev.id_tipologia_evento
where a.tatuaggio in
(
select tatuaggio
from animale 
where data_cancellazione is null and trashed_date = '01/01/1970' and tatuaggio is not null and tatuaggio <> ''
group by tatuaggio
having count(tatuaggio)>1 
) 
and a.microchip is not null and a.microchip <> ''
order by tatuaggio, microchip
)
--select tatuaggio from lista
update animale 
set tatuaggio = tatuaggio || '###' || (select lista.n from lista where lista.id_animale = id limit 1 ), trashed_date = null 
--using lista
where id in (select id_animale from lista)






--Riabilitazione restanti tatuaggi (senza mc valorizzato)
with lista as (select a.id as id_animale, a.microchip, a.tatuaggio ,a.data_inserimento as data_inserimento_microchip, tipo_reg.description as registrazione, 
ev.entered as data_inserimento_registrazione,
a.data_nascita,
a.sesso,
a.id_taglia,
a.id_tipo_mantello,
row_number() over(order by a.id) as n
from animale a
left join evento ev on ev.id_animale = a.id and ev.data_cancellazione is null
left join lookup_tipologia_registrazione tipo_reg on tipo_reg.code = ev.id_tipologia_evento
where a.tatuaggio in
(
select tatuaggio
from animale 
where data_cancellazione is null and trashed_date = '01/01/1970' and tatuaggio is not null and tatuaggio <> ''
group by tatuaggio
having count(tatuaggio)=1
) 
and a.microchip is not null and a.microchip <> ''
order by tatuaggio, microchip
)
update animale 
set trashed_date = null 
where id in (select id_animale from lista)





--Ricerca tatuaggi doppioni abilitati
select id as id_animale, microchip, tatuaggio
from animale 
where data_cancellazione is null and trashed_date is null and tatuaggio is not null and tatuaggio <> '' and note_internal_use_only ilike '%Cancellato temporaneamente%'
and tatuaggio ilike '%###%'










--Ricerca tatuaggi con eventi dopo la disabilitazione
select a.id as id_animale, a.microchip, a.tatuaggio,a.data_inserimento as data_inserimento_microchip, tipo_reg.description as registrazione, 
ev.entered as data_inserimento_registrazione,
a.data_nascita,
a.sesso,
a.id_taglia,
a.id_tipo_mantello
from animale a
left join evento ev on ev.id_animale = a.id and ev.data_cancellazione is null
left join lookup_tipologia_registrazione tipo_reg on tipo_reg.code = ev.id_tipologia_evento
where a.tatuaggio in
(
select tatuaggio
from animale 
where data_cancellazione is null and trashed_date = '01/01/1970' and tatuaggio is not null and tatuaggio <> ''
group by tatuaggio
having count(tatuaggio)>1 
) 
and a.data_inserimento > '01/01/2011'
order by tatuaggio, microchip



--Ricerca tatuaggi con eventi dopo la disabilitazione
select a.id as id_animale, a.microchip, a.tatuaggio,a.data_inserimento as data_inserimento_microchip, tipo_reg.description as registrazione, 
ev.entered as data_inserimento_registrazione,
a.data_nascita,
a.sesso,
a.id_taglia,
a.id_tipo_mantello
from animale a
left join evento ev on ev.id_animale = a.id and ev.data_cancellazione is null
left join lookup_tipologia_registrazione tipo_reg on tipo_reg.code = ev.id_tipologia_evento
where a.tatuaggio in
(
select tatuaggio
from animale 
where data_cancellazione is null and trashed_date = '01/01/1970' and tatuaggio is not null and tatuaggio <> ''
group by tatuaggio
having count(tatuaggio)>1 
) 
and a.data_inserimento > '01/01/2011' 
and (trim(microchip )= '' or microchip is null)
and tatuaggio SIMILAR TO '[0-9]{1,2}/[0-9]{1,2}/[0-9]{1,2,3,4}'
order by tatuaggio, microchip




--Pulizia tatuaggi sporchi (date)
select id from animale 
where tatuaggio SIMILAR TO '%[0-9]{1,2}/[0-9]{1,2}/[0-9]{1,4}%' and trashed_date is null and data_cancellazione is null 


update animale set note_internal_use_only || '. Valore tatuaggio rimosso:' || tatuaggio,
                   tatuaggio = null,
                   trashed_date = null
where id in 
(
select id from animale 
where tatuaggio SIMILAR TO '%[0-9]{1,2}/[0-9]{1,2}/[0-9]{1,4}%' and trashed_date is null and data_cancellazione is null 
)



--Pulizia tatuaggi sporchi (stringhe)
select id, * from animale 
where tatuaggio SIMILAR TO '[a-z,A-Z]{1,10}' and trashed_date is null and data_cancellazione is null 


update animale set note_internal_use_only = '. Valore tatuaggio rimosso:' || tatuaggio,
                   tatuaggio = null,
                   trashed_date = null
where id in 
(
select id from animale 
where tatuaggio SIMILAR TO '[a-z,A-Z]{1,10}' and trashed_date is null and data_cancellazione is null 
)

--Pulizia tatuaggi sporchi (numeri corti)
select id, * from animale 
where tatuaggio SIMILAR TO '[0-9]{1,3}' and trashed_date is null and data_cancellazione is null and (trim(microchip) <> '' and microchip is not null)


update animale set note_internal_use_only = '. Valore tatuaggio rimosso:' || tatuaggio,
                   tatuaggio = null,
                   trashed_date = null
where id in 
(
select id from animale 
where tatuaggio SIMILAR TO '[0-9]{1,3}' and trashed_date is null and data_cancellazione is null and (trim(microchip) <> '' and microchip is not null)
)

--Disabilitazione tatuaggi duplicati
update animale 
set note_internal_use_only = '.Cancellato temporaneamente in data 20/01/2016 per gestire i tatuaggi ###duplicati###',
trashed_date = '01/01/1970'   
where tatuaggio in
(
select tatuaggio--,count(tatuaggio)
from animale 
where data_cancellazione is null and trashed_date is null and tatuaggio is not null and tatuaggio <> ''
group by tatuaggio
having count(tatuaggio)>1 
)




CREATE UNIQUE INDEX stop_dup_mc ON animale (MICROCHIP) WHERE (data_cancellazione is null and trashed_date is null and microchip is not null and microchip <> '' );
CREATE UNIQUE INDEX stop_dup_tatuaggio ON animale (tatuaggio) WHERE (data_cancellazione is null and trashed_date is null and tatuaggio is not null and tatuaggio <> '' );



select a.id as id_animale, a.microchip, a.data_inserimento as data_inserimento_microchip, tipo_reg.description as registrazione, ev.entered as data_inserimento_registrazione
from animale a
left join evento ev on ev.id_animale = a.id and ev.data_cancellazione is null
left join lookup_tipologia_registrazione tipo_reg on tipo_reg.code = ev.id_tipologia_evento
where a.microchip in
(
'380260002068526',
'380260002274004',
'380260002329374',
'380260010048903',
'380260010265162',
'380260010284216',
'380260010308830',
'380260020079611',
'380260040183551',
'380260040528253',
'380260040741142',
'380260040742578',
'380260040776425',
'380260041064417',
'380260041174137',
'380260041244952',
'380260041821084',
'380260042078564',
'380260042091849',
'380260042091916',
'380260042164177',
'380260042167016',
'380260042282753',
'380260042333457',
'380260060021436',
'380260060022150',
'380260080200628',
'380260100102325',
'380260100107379',
'380260100211310',
'380260100211324',
'380260100311978',
'380260100313208',
'900032002329851',
'900062000150341',
'900062000152712',
'900108001304870',
'900108001814993',
'900108001815268',
'900108001817922',
'900108001818202',
'900108001818307',
'900108001907398',
'941000001142439',
'941000001157908',
'941000001166991',
'941000001705512',
'941000001813441',
'941000001826335',
'941000002122176',
'941000002736649',
'953000010086404',
'968000001962715',
'968000001983578',
'968000001998520',
'977200007312371',
'982000145472231'
) and a.data_cancellazione is null and a.trashed_date is null
order by a.microchip, ev.entered asc












select a.id as id_animale, a.microchip, a.tatuaggio, a.data_inserimento as data_inserimento_microchip, tipo_reg.description as registrazione, ev.entered as data_inserimento_registrazione
from animale a
left join evento ev on ev.id_animale = a.id and ev.data_cancellazione is null
left join lookup_tipologia_registrazione tipo_reg on tipo_reg.code = ev.id_tipologia_evento
where a.tatuaggio in
(
'2NA25263',
'2NA24630',
'2NA10801',
'2NA11353',
'2NA2279',
'2NA25084',
'2NA17733',
'2NA13712',
'1NA16679',
'2NA10576',
'1CE1719',
'2NA20003',
'2NA17424',
'2NA19773',
'2NA20923',
'2NA11598',
'3NA14328',
'2NA24571',
'3NA3262',
'2NA12678',
'2NA9651',
'2NA24866',
'2NA20577',
'2NA12289',
'2NA22123',
'3na17258',
'2NA10969',
'2NA15239',
'1NA0003',
'3na17351',
'5NA1B98',
'2NA22306',
'3NA13835',
'5NA2243',
'1NA12408',
'2NA24399',
'2NA10764',
'3NA13932',
'2NA20566',
'2NA24187',
'2NA4647',
'5NA0E1371',
'2NA24391',
'3na17013',
'2NA25433',
'2NA20759',
'2NA22934',
'2NA11414',
'2NA11966',
'5NAH483',
'2NA12422',
'3na14346',
'2NA11019',
'2NA9921',
'2NA24638',
'2NA24640',
'2NA2313',
'2NA20448',
'0BN90',
'2NA16614',
'2NA5051',
'2NA19492',
'2NA11581',
'2NA24776',
'2NA14563',
'1NA17579',
'2NA24197',
'2NA5015',
'2NA23111',
'5NA0E1408',
'5MZU9',
'2NA24381',
'3na17350',
'1NA6285',
'2NA13003',
'2NA2907',
'2NA15251',
'2NA2850',
'2NA24295',
'5NA0E1377',
'AN 3NA7825',
'2NA20571',
'04/04/2005',
'2NA22900',
'8DLA14',
'2NA24185',
'5NA0E318',
'2NA11768',
'2NA14319',
'2NA23694',
'2NA19602',
'2NA24703',
'2NA24425',
'1BV281',
'2NA11547',
'2NA11430',
'2NA21532',
'2NA25305',
'2NA17717',
'2NA11876',
'2NA10228',
'2NA18039',
'2NA20633',
'2NA24919',
'2NA11950',
'5NA0E1353',
'2NA24143',
'2NA20556',
'2NA24625',
'2NA24452',
'3NA12726',
'2NA21874',
'5NAP100',
'5NA1B04',
'5NAC260',
'1NAS',
'2NA1149',
'2NA22674',
'2NA25228',
'5NA0E744',
'luna',
'AN 3NA7743',
'2NA20670',
'2NA24827',
'7SA57',
'2MI2847',
'2NA2268',
'2NA5066',
'2NA23821',
'2NA11965',
'2NA15979',
'46NA0059',
'2NA12140',
'3NA3007',
'2NA5061',
'2NA20576',
'2NA21981',
'3NA13467',
'2NA19620',
'2NA20061',
'2NA24665',
'5NA0E1375',
'2NA13057',
'RMA7780',
'2NA13002',
'2NA11930',
'2NA16815',
'2NA14139',
'2NA12858',
'2NA1150',
'AN 3NA7921',
'2NA20714',
'2702966',
'2NA24219',
'2NA2522',
'3NA15958',
'2NA10882',
'2NA20574',
'2NA20512',
'3NA17467',
'3NA13911',
'2NA10337',
'17/02/2005',
'1NA11016',
'1NA17260',
'2NA20687',
'1CE1713',
'5NA2071',
'5NA1A18',
'2NA10863',
'2NA25133',
'1CE1718',
'2NA21530',
'2NA20115',
'2NA21419',
'1NA10873',
'2NA14101',
'2NA23753',
'2NA10799',
'2NA11987',
'2NA11703',
'2NA21452',
'2NA10145',
'2NA22671',
'2NA2275',
'2NA7043',
'3na17289',
'2NA7064',
'2NA9385',
'3NA15241',
'3NA153',
'2NA23768',
'2SIE10',
'to333',
'3NA15386',
'2NA17772',
'2NA24078',
'2NA22528',
'3NA2630',
'2NA24982',
'2NA25073',
'2NA24443',
'2NA24899',
'2NA2076',
'2NA22222',
'2NA17912',
'2NA24743',
'2NA20361',
'2NA23625',
'2NA9581',
'2NA25403',
'2NA11592',
'2NA9812',
'3NA14770',
'1NA1586',
'2NA11582',
'2NA10632',
'2NA19867',
'22NA821',
'1NA6561',
'3NA11357',
'2NA7376',
'2NA15252',
'2NA24298',
'2NA16831',
'29/03/2005',
'2NA12524',
'5NA0E1397',
'3NA3691',
'1NA4668',
'2NA19960',
'2NA23293',
'2NA23450',
'2NA20423',
'2NA2277',
'5NA0E1374',
'OR.DX',
'2NA10287',
'3na12197',
'2NA4695',
'2NA5055',
'2NA12881',
'2NA25304',
'2NA4847',
'5NA0E1407',
'5NA0E1174',
'2NA10575',
'2NA5062',
'2NA11713',
'2NA12970',
'5NA1B99',
'3NA13655',
'2NA20879',
'2NA20273',
'5NA162',
'2NA22637',
'2NA24643',
'3NA15751',
'2NA24072',
'2NA2424',
'2NA20142',
'2NA15322',
'2NA23496',
'2NA16967',
'610',
'3NA10541',
'2NA22122',
'2NA4798',
'2NA24616',
'2NA5270',
'2NA11452',
'2NA12139',
'2NA10495',
'no',
'2NA23047',
'2NA23010',
'2NA',
'2NA16203',
'2NA11159',
'2NA1113',
'2NA12650',
'9NA1681',
'3na16665',
'2NA2498',
'2NA13655',
'1NA12034',
'3NA4966',
'15/03/2005',
'2NA12681',
'2NA23015',
'3na17003',
'2NA23712',
'2NA13709',
'2NA24268',
'2NA24388',
'2NA24293',
'2NA7963',
'2NA24756',
'2CE239',
'2NA17243',
'5NA1B97',
'2NA10037',
'2NA22638',
'2NA23729',
'2NA15071',
'46NA0057',
'2NA24401',
'2NA24637',
'1CE1717',
'2NA2904',
'3NA12788',
'1NA17047',
'2NA19024',
'5NA0E1376',
'2NA20477',
'2NA12647',
'5NAOE1325',
'2NA12496',
'5NAT63',
'2NA11640',
'2NA10794',
'2NA15266',
'2NA10689',
'3NA17115',
'2NA23126',
'2NA16926',
'A1SA302',
'2NA24871',
'2NA11677',
'1CE315',
'2NA22860',
'2NA20568',
'2NA16587',
'2NA22373',
'2NA10616',
'2NA15250',
'3na17004',
'5NA0E1366',
'5NAC646',
'2NA12115',
'2NA23456',
'2NA12900',
'1CE1936',
'5NA1B85',
'2NA20639',
'2NA22434',
'2NA23620',
'1NA12203',
'2NA1151',
'2NA2267',
'1MGI16',
'2NA22632',
'2NA24770',
'2NA23721',
'3NA15503',
'2NA10924',
'2NA5043',
'2NA24396',
'2NA24442',
'2NA22925',
'2NA18269',
'2NA7945',
'2NA14386',
'2NA10535',
'1NA12108',
'3na16642',
'2NA9766',
'2NA18338',
'2NA21589',
'2NA12378',
'3NA11118',
'2NA21849',
'2NA13227',
'2NA24627',
'2NA20655',
'1NA0506',
'2NA11769',
'2NA10190',
'2NA24130',
'2NA2812',
'2NA20650',
'2NA11354',
'5NAOE1245',
'2NA12786',
'2NA10534',
'2NA23421',
'2NA24198',
'5NAR367',
'2NA16776',
'1CE1716',
'4sa619',
'2NA20011',
'2NA10712',
'2NA24961',
'2NA11852',
'2NA9944',
'2NA10791',
'3na12498',
'2NA16229',
'2NA13200',
'2NA23981',
'2NA2873',
'3NA15093',
'2NA21550',
'2NA20731',
'2NA12813',
'1CE1714',
'2NA20594',
'2NA12052',
'3NA14180',
'2NA20191',
'2NA13704',
'2NA17688',
'5NA0E1396',
'3NA10650',
'1NA1462',
'2NA12005',
'2NA24773',
'5NA2511',
'3NA3741',
'2NA24900',
'2NA24073',
'2NA9765',
'1NA10011',
'2NA22972',
'2NA24589',
'5NA0E1370',
'5NA1B11',
'7CE1200',
'2NA11834',
'07/03/2005',
'2NA24575',
'5NA0E1364',
'2NA10507',
'2NA22557',
'2NA24446',
'3NA3604',
'2NA2681',
'2NA24079',
'2NA11158',
'2NA1137',
'3NA3706',
'2NA9315',
'2NA11405',
'5NA0E1379',
'2NA23507',
'2NA20609',
'2NA11593',
'2NA22158',
'2NA24620',
'2NA13417',
'2NA12841',
'043BN212',
'1NA11235',
'2NA5002',
'2NA21845',
'2NA13001',
'1NA14366',
'1NA16612',
'2NA20185',
'2NA16984',
'2NA15527',
'2NA20578',
'2NA23842',
'2NA24907',
'2NA20151',
'2NA11265',
'2NA20382',
'2NA20567',
'2NA11173',
'2NA23605',
'2NA23707',
'2NA19993',
'2NA5314',
'2NA12230',
'2NA12483',
'2NA22686',
'2NA13059',
'1NA12172',
'2NA21249',
'2NA20572',
'2NA13292',
'2NA25201',
'2NA16525',
'2NA14477',
'2NA17559',
'2NA23801',
'2NA2730',
'3NA13451',
'3NA10208',
'2NA14574',
'2NA24611',
'2NA25254',
'2NA24074',
'2NA13658',
'2NA12234',
'2NA1215',
'2NA10077',
'2NA22353',
'2NA12209',
'2NA18149',
'2NA25446',
'2NA20706',
'3NA15869',
'2NA15137',
'2NA1144',
'2NA24426',
'2NA15952',
'2NA4530',
'2NA20450',
'2NA21800',
'7CE1103',
'2NA9207',
'2NA7626',
'2NA12018',
'2NA24077',
'1NA12304',
'3NA13787',
'2NA11091',
'2NA12010',
'2NA2755',
'3na14233',
'2NA19621',
'2NA13066',
'2NA11595',
'2NA12657',
'2NA14209',
'2NA23780',
'2NA6243',
'2NA14122',
'2NA13706',
'2NA14075',
'2NA20352',
'5NAB618',
'2NA24975',
'2NA11679',
'2NA2280',
'2NA12680',
'2NA19582',
'2NA14502',
'1NA12278',
'3NA16221',
'2NA12229',
'2NA11771',
'2NA12679',
'2NA24076',
'3NA6290',
'2NA11478',
'2NA20363',
'2NA9382',
'2NA2250',
'2NA9771',
'2NA24071',
'2NA19496',
'2NA24708',
'2NA2651',
'3NA13538',
'2NA25272',
'3NA15733',
'2NA11013',
'5NA2B33',
'2NA14199',
'2NA14642',
'2NA23005',
'2NA24802',
'2NA21008',
'2NA24467',
'2NA20643',
'3NA15112',
'043BN219',
'5NA0E1365',
'2NA24599',
'3na14232',
'2NA23926',
'2NA10881',
'5NA0E1323',
'2NA12682',
'2NA24070',
'2NA5039',
'2NA20573',
'08/03/2005',
'2NA24026',
'2NA12806',
'2NA10441',
'2CE3625',
'2NA25469',
'5CB03',
'3na18285',
'2NA12567',
'2NA23797',
'2NA11026',
'2NA10315',
'2NA24274',
'3na15255',
'2NA20491',
'2NA2243',
'3na16614'
) and (a.microchip = '' or a.microchip is null) and a.data_cancellazione is null and (a.trashed_date is null or a.trashed_date = '01/01/1970')
order by a.tatuaggio, ev.entered asc




