-- Chi: Bartolo Sansone
-- Cosa: Farmacosorveglianza nuove richieste 
-- Quando: 13/05/21

-- MEDICI VETERINARI SPECIALISTI
insert into role_permission(permission_id, role_id, role_view, role_add, role_edit, role_delete) values ((select permission_id from permission where permission = 'chkfarmacosorveglianza-chkfarmacosorveglianza'), 222, true, true, true, true);

-- CHIUSURA CU


CREATE OR REPLACE FUNCTION public.get_esistenza_farmacosorveglianza_istanza(
    _id_controllo integer)
  RETURNS boolean AS
$BODY$
DECLARE
	num_farmacosorveglianza integer;
BEGIN
select count(*) into num_farmacosorveglianza from farmacosorveglianza_istanze where trashed_date is null and id_controllo = _id_controllo and bozza=false;

raise info '[Num checklist FARMACOSORVEGLIANZA] %', num_farmacosorveglianza;
	if(num_farmacosorveglianza > 0) then
		return true;
	else 
		return false;
	end if;

END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_esistenza_farmacosorveglianza_istanza(integer)
  OWNER TO postgres;

  -- ASSOCIAZIONE PIANI
  
  delete from rel_motivi_eventi_cu where id_evento_cu in (select code from lookup_eventi_motivi_cu where codice_evento = 'isFarmacosorveglianza');

insert into rel_motivi_eventi_cu (cod_raggrup_ind, id_evento_cu, entered, enteredby)
select distinct cod_raggruppamento, (select code from lookup_eventi_motivi_cu where codice_evento = 'isFarmacosorveglianza'), now(), 5885 from dpat_indicatore_new where alias_indicatore in ('A14_A',
'a14_AB',
'A14_AC',
'A14_AD',
'A14_AE',
'A14_AF',
'a14_AG',
'A14_AH',
'A14_AI',
'a14_AK',
'A14_AL',
'A14_AM',
'A14_AN',
'A14_AO',
'A14_AQ',
'A14_AR',
'A14_AU',
'A14_C',
'a14_D',
'a14_E',
'A14_F',
'A14_g',
'A14_H',
'A14_I',
'A14_J',
'A14_K',
'A14_L',
'A14_M',
'a14_N',
'A14_N',
'A14_O',
'a14_P',
'A14_R',
'A14_S',
'a14_t',
'a14_U',
'A14_V',
'A14_w',
'A14_X',
'A14_Y',
'A14_Z'
) and anno = 2021 and data_scadenza is null;

select d.alias_indicatore, r.*
from rel_motivi_eventi_cu r
join dpat_indicatore_new d on d.cod_raggruppamento = r.cod_raggrup_ind
where id_evento_cu in (select code from lookup_eventi_motivi_cu where codice_evento = 'isFarmacosorveglianza')
order by d.alias_indicatore asc

  
  