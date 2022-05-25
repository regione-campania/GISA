select 'update linee_mobili_fields_value set valore_campo =  REGEXP_REPLACE(upper(valore_campo),upper(''cani''),''' || (select code from lookup_tipo_operatore_commerciale where description ilike '%cani%') || ''')   where id = ' || id || ';',* from linee_mobili_fields_value where valore_campo ilike '%cani%' and id_linee_mobili_html_fields in (85,92) union
select 'update linee_mobili_fields_value set valore_campo =  REGEXP_REPLACE(upper(valore_campo),upper(''gatti''),''' || (select code from lookup_tipo_operatore_commerciale where description ilike '%gatti%') || ''')   where id = ' || id || ';',* from linee_mobili_fields_value where valore_campo ilike '%gatti%' and id_linee_mobili_html_fields in (85,92)  union
select 'update linee_mobili_fields_value set valore_campo =  REGEXP_REPLACE(upper(valore_campo),upper(''furetti''),''' || (select code from lookup_tipo_operatore_commerciale where description ilike '%furetti%') || ''')   where id = ' || id || ';',* from linee_mobili_fields_value where valore_campo ilike '%furetti%' and id_linee_mobili_html_fields in (85,92)  union
select 'update linee_mobili_fields_value set valore_campo =  REGEXP_REPLACE(upper(valore_campo),upper(''rettili''),''' || (select code from lookup_tipo_operatore_commerciale where description ilike '%rettili%') || ''')   where id = ' || id || ';',* from linee_mobili_fields_value where valore_campo ilike '%rettili%' and id_linee_mobili_html_fields in (85,92)  union
select 'update linee_mobili_fields_value set valore_campo =  REGEXP_REPLACE(upper(valore_campo),upper(''anfibi''),''' || (select code from lookup_tipo_operatore_commerciale where description ilike '%anfibi%') || ''')   where id = ' || id || ';',* from linee_mobili_fields_value where valore_campo ilike '%anfibi%' and id_linee_mobili_html_fields in (85,92)  union
select 'update linee_mobili_fields_value set valore_campo =  REGEXP_REPLACE(upper(valore_campo),upper(''volatili''),''' || (select code from lookup_tipo_operatore_commerciale where description ilike '%volatili%') || ''')   where id = ' || id || ';',* from linee_mobili_fields_value where valore_campo ilike '%volatili%' and id_linee_mobili_html_fields in (85,92)  union
select 'update linee_mobili_fields_value set valore_campo =  REGEXP_REPLACE(upper(valore_campo),upper(''roditori''),''' || (select code from lookup_tipo_operatore_commerciale where description ilike '%roditori%') || ''')   where id = ' || id || ';',* from linee_mobili_fields_value where valore_campo ilike '%roditori%' and id_linee_mobili_html_fields in (85,92)  union
select 'update linee_mobili_fields_value set valore_campo =  REGEXP_REPLACE(upper(valore_campo),upper(''INVERTEBRATI D''ACQUARIO''),''' || (select code from lookup_tipo_operatore_commerciale where description ilike '%INVERTEBRATI D''ACQUARIO%') || ''')   where id = ' || id || ';',* from linee_mobili_fields_value where valore_campo ilike '%INVERTEBRATI D''ACQUARIO%' and id_linee_mobili_html_fields in (85,92)  union
select 'update linee_mobili_fields_value set valore_campo =  REGEXP_REPLACE(upper(valore_campo),upper(''INSETTI''),''' || (select code from lookup_tipo_operatore_commerciale where description ilike '%INSETTI%') || ''')   where id = ' || id || ';',* from linee_mobili_fields_value where valore_campo ilike '%INSETTI%' and id_linee_mobili_html_fields in (85,92)  union
select 'update linee_mobili_fields_value set valore_campo =  REGEXP_REPLACE(upper(valore_campo),upper(''MUSTELIDI''),''' || (select code from lookup_tipo_operatore_commerciale where description ilike '%MUSTELIDI%') || ''')   where id = ' || id || ';',* from linee_mobili_fields_value where valore_campo ilike '%MUSTELIDI%' and id_linee_mobili_html_fields in (85,92)  union
select 'update linee_mobili_fields_value set valore_campo =  REGEXP_REPLACE(upper(valore_campo),upper(''PESCIE E TARTARUGHE D''ACQUARIO''),''' || (select code from lookup_tipo_operatore_commerciale where description ilike '%"PESCIE E TARTARUGHE D''ACQUARIO"%') || ''')   where id = ' || id || ';',* from linee_mobili_fields_value where valore_campo ilike '%PESCIE E TARTARUGHE D''ACQUARIO%' and id_linee_mobili_html_fields  in (85,92)  union
select 'update linee_mobili_fields_value set valore_campo =  REGEXP_REPLACE(upper(valore_campo),upper(''ANIMALI DA COMPAGNIA''),''' || '5;6' || ''')   where id = ' || id || ';',* from linee_mobili_fields_value where valore_campo ilike '%animali da compagnia%' and id_linee_mobili_html_fields in (85,92)  union
select 'update linee_mobili_fields_value set valore_campo =  REGEXP_REPLACE(upper(valore_campo),upper(''PESCI E TARTARUGHE D''ACQUARIO''),''' || (select code from lookup_tipo_operatore_commerciale where description ilike '%PESCIE E TARTARUGHE D''ACQUARIO%') || ''')   where id = ' || id || ';',* from linee_mobili_fields_value where valore_campo ilike '%PESCI E TARTARUGHE D''ACQUARIO%' and id_linee_mobili_html_fields in (85,92)  union
select 'update linee_mobili_fields_value set valore_campo =  REGEXP_REPLACE(upper(valore_campo),upper(''PESCI ornamentali''),''' || (select code from lookup_tipo_operatore_commerciale where description ilike 'PESCI') || ''')   where id = ' || id || ';',* from linee_mobili_fields_value where valore_campo ilike '%PESCI ornamentali%' and id_linee_mobili_html_fields in (85,92)  union
select 'update linee_mobili_fields_value set valore_campo =  REGEXP_REPLACE(upper(valore_campo),upper(''PESCI''),''' || (select code from lookup_tipo_operatore_commerciale where description ilike 'PESCI') || ''')   where id = ' || id || ';',* from linee_mobili_fields_value where valore_campo ilike '%PESCI%' and id_linee_mobili_html_fields in (85,92)  union
select 'update linee_mobili_fields_value set valore_campo =  REGEXP_REPLACE(upper(valore_campo),upper(''da compagnia''),''' || '5;6' || ''')   where id = ' || id || ';',* from linee_mobili_fields_value where valore_campo ilike '%da compagnia%' and id_linee_mobili_html_fields in (85,92) 


CREATE OR REPLACE FUNCTION get_description_specie_animali_campi_estesi(valore_in text)
  RETURNS text AS
$BODY$
DECLARE
	valore_out text;
	i integer;
	description_temp text;
	code_temp integer;
BEGIN
	i:=1;
	valore_out:='';
	while(split_part(valore_in,';',i) <> '') LOOP
		if(i>1) then valore_out:=concat(valore_out, ', '); end if;
		
		code_temp := split_part(valore_in,';',i);
		description_temp := (select description from lookup_tipo_operatore_commerciale where code = code_temp);
		valore_out:=concat(valore_out, description_temp);
		i:=i+1;
        END LOOP;
	
	
	RETURN valore_out;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION get_description_specie_animali_campi_estesi(text)
  OWNER TO postgres;


when tipo_campo = 'select' and label_campo ilike '%Specie animali%' then get_description_specie_animali_campi_estesi(valore_campo) 

