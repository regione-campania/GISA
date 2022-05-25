--DA LANCIARE SU BDU

-- Function: public_functions.aggiorna_info_canile(integer, integer, integer)

-- DROP FUNCTION public_functions.aggiorna_info_canile(integer, integer, integer);

CREATE OR REPLACE FUNCTION public_functions.aggiorna_info_canile(
    idrelstablpgisa integer,
    idstabilimentobdu integer,
    idrelstablpbdu integer)
  RETURNS text AS
$BODY$
DECLARE
autorizzazione_si_no text;
autorizzazione_note text;
data_autorizzazione_ text;
mq_specie_ text;
linea integer;
altro text;
cursore refcursor;
curs refcursor;
curso refcursor;
referente_cognome text;
referente_nome text;
referente_cf text;
idSoggettCanile int ;
id_stabilimento_gisa int ;
data_autorizzazione_date timestamp without time zone;
BEGIN

select id_stabilimento into id_stabilimento_gisa from opu_operatori_denormalizzati_canili_opc_gisa where id_linea_attivita = idRelStabLpGisa;

SELECT * FROM crosstab( 'select id_opu_rel_stab_linea, id_linee_mobili_html_fields,valore_campo
	from fg_linee_mobili_fields_value where id_opu_rel_stab_linea =' || idRelStabLpGisa ||' order by id_rel_stab_linea,id_linee_mobili_html_fields')
as ct( linea integer,autorizzazione_si_no text, autorizzazione_note text,data_autorizzazione_ text, mq_specie_ text, referente_cognome text,referente_nome text,referente_cf text)
into  linea ,autorizzazione_si_no , autorizzazione_note ,data_autorizzazione_ , mq_specie_ , referente_cognome ,referente_nome ,referente_cf ;


          raise info 'mq_specie = %', mq_specie_;
          raise info 'linea = %', linea;
          raise info 'autorizzazione_si_no = %', autorizzazione_si_no;
          raise info 'autorizzazione_note = %', autorizzazione_note;
          raise info 'data_autorizzazione_ = %', data_autorizzazione_;

        if(data_autorizzazione_!='') then
          insert into opu_informazioni_canile (id_relazione_stabilimento_linea_produttiva, abusivo, centro_sterilizzazione, autorizzazione, data_autorizzazione,mq_disponibili) values
		(idRelStabLpBdu,false,false,autorizzazione_note,to_date(data_autorizzazione_::text, 'dd/MM/yyyy'::text)::timestamp without time zone, case when mq_specie_ is not null and mq_specie_ <> '' then mq_specie_::int else -1 end );
		else
		insert into opu_informazioni_canile (id_relazione_stabilimento_linea_produttiva, abusivo, centro_sterilizzazione, autorizzazione, data_autorizzazione,mq_disponibili) values
		(idRelStabLpBdu,false,false,autorizzazione_note,null,case when mq_specie_ is not null and mq_specie_ <> '' then mq_specie_::int else -1 end  );
		end if;
	
       idSoggettCanile:=(select * from public_functions.insert_soggetto_fisico_bdu(referente_nome , referente_cognome ,referente_cf));
       update opu_stabilimento set id_soggetto_fisico =idSoggettCanile where id =idstabilimentoBdu;

     RETURN 'OK';
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public_functions.aggiorna_info_canile(integer, integer, integer)
  OWNER TO postgres;
