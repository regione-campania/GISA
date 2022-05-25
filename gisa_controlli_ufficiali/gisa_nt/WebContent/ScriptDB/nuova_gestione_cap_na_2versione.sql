--Da lnciare solo dove provincia è diverso da intero
select distinct provincia, 'update opu_indirizzo set provincia = (select code from lookup_province where cod_provincia = ''' || i.provincia ||  ''' or upper(description) = upper(''' || i.provincia || ''')) where provincia = ''' || i.provincia || ''';' from opu_indirizzo i  order by provincia
