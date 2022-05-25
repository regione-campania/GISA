--select code, description, identificativo, enabled from lookup_tipo_acque where code > 0 order by identificativo


update lookup_tipo_acque set identificativo = 'H' where code = 38;

update organization set 
note_hd=concat_ws(';', note_hd, 'Codice modificato da '||account_number),  
account_number = replace(account_number, 'null', 'H')
where tipo_struttura = 38 
and account_number in ('61069null4034',
'064116null4103',
'62021null3874',
'63049null3849',
'063049null4283',
'63049null3677',
'65154null3740',
'063049null4236',
'064078null4165',
'065142null4051',
'063049null4510',
'63049null3852',
'63049null2716',
'63049null3854',
'063089null4118',
'063049null4235',
'62021null3873',
'063092null4111',
'63049null3850',
'063049null4242',
'063056null4113',
'063026null4107',
'064067null4132',
'061004null4487',
'63049null2494',
'061004null4488',
'63049null3851',
'065142null4050',
'063049null4213',
'063072null4129',
'63049null3848',
'063072null4128',
'063049null4210',
'061042null4311',
'063049null4151',
'064078null4166',
'62076null3877',
'61036null3846',
'061004null4486',
'63010null2588',
'063089null4119',
'63023null3685',
'63049null3646',
'63049null3845',
'063089null4121',
'62018null3871',
'63049null3847',
'064078null4168',
'65025null3522',
'064026null4209',
'63049null3853')

update organization set account_number = '65025E3522' where account_number = '65025null3522';
update organization set account_number = '63010D2588' where account_number = '63010null2588';

select org_id, account_number, tipo_struttura from organization where account_number ilike '%null%'



update organization set 
note_hd=concat_ws(';', note_hd, 'Codice modificato da '||account_number),  
account_number = replace(account_number, 'null', 'H')
where tipo_struttura = 38 
and account_number in (
'62077null3887',
'62077null3886',
'62076null3875',
'62030null3879',
'62076null3878',
'62030null3880',
'62030null3881')