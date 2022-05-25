insert into lookup_specie_allevata(code,description,short_description,default_item,level,enabled,codice_categoria,codice_specie_capo)
values(201,'CROSTACEI','0201',false,9,true,9,null);
update lookup_specie_allevata set alt_short_description = 'CRO' where short_description = '0201';
update lookup_specie_allevata set alt_short_description = 'MOL' where short_description = '0200';


CREATE OR REPLACE FUNCTION public_functions.import_acquacoltura(
    inputdenominazione text,
    inputAsl text,
    inputcodazienda text,
    inputTipoAll text,
    inputcf text,
    inputTipologiaProduttiva text,
    idUtente integer,
    inputDataInizio text,
    inputProv text,
    inputVia text,
    inputCap text,
    inputLat text,
    inputLong text,
    inputComIstat text,
    inputCfResp text,
    inputSpecieAll text,
    allevId text ,
    inputSpecie text,
    nominativoProp text,
    nominativoResp text,
    indirizzoProp text,
    indirizzoResp text ,
    capProp text,
    capResp text,
    comIstatProp text,
    comIstatResp text,
    inputViaSedeOperativa text,
    inputComuneSedeOperativa text,
    inputCapSedeOperativa text,
    inputLatSedeOperativa text,
    inputLongSedeOperativa text  )
  RETURNS text AS
$BODY$
DECLARE
countAziende integer;
countOpAllevamenti integer;
countOpAllevamentiResp integer;
inputIdAsl integer;
inputIdComune integer;
outputOrgId integer; 
outputMsg text;
inputDescComune text; 
inputDescComuneProp text; 
inputDescComuneResp text; 
inputDescSpecie text; 
inputProvCodice text; 
BEGIN 


inputProvCodice:= (select codistat from lookup_province where cod_provincia = inputProv);
inputIdComune:= (select id from comuni1 where cod_comune = comIstatProp and cap = inputCap);
inputDescComune:= (select nome from comuni1 where cod_comune = inputComIstat and cap = inputCap);
inputDescComuneProp:= (select nome from comuni1 where cod_comune = comIstatProp and cap = capProp);
inputDescComuneResp:= (select nome from comuni1 where cod_comune = comIstatResp and cap = capResp);
inputDescSpecie:= (select description from lookup_specie_allevata where code = inputSpecie::integer);
inputIdAsl:=substring(inputAsl from 2 for 3)::integer;

if(inputTipologiaProduttiva is null) then 
	inputTipologiaProduttiva := '';
end if;

select max(org_id) + 1 from organization into outputOrgId;

INSERT INTO organization(
                        org_id,                   "name",  account_number, entered, enteredby, modified, modifiedby, enabled,    site_id, codice_fiscale, date1,                                         tipologia,    specie_allev,             orientamento_prod, tipologia_strutt, id_allevamento, codice_fiscale_rappresentante, cf_correntista, specie_allevamento   )
    VALUES (       outputOrgId,       inputdenominazione, inputcodazienda,   now(),  idUtente,    now(),   idUtente,    TRUE, inputIdAsl,        inputcf, inputDataInizio::timestamp without time zone ,         2, inputDescSpecie,     inputTipologiaProduttiva ,     inputTipoAll,        allevId,                       inputcf,    inputCfResp, inputSpecie::integer ) ;




countOpAllevamenti:= (select count(*) from operatori_allevamenti where cf = inputCf); 

raise info '%', countOpAllevamenti;

if(countOpAllevamenti=0) then

INSERT INTO operatori_allevamenti(
              id_asl,        cf, indirizzo,          cap,               comune,      prov, nominativo)
    VALUES (inputIdAsl, inputCf,  indirizzoProp, capProp,  inputDescComuneProp, inputProv, nominativoProp);
    end if;

countOpAllevamentiResp:= (select count(*) from operatori_allevamenti where cf = inputCfResp); 

raise info '%', countOpAllevamentiResp;

if(countOpAllevamentiResp=0 and upper(inputCf)!=upper(inputCfResp)) then

INSERT INTO operatori_allevamenti(
              id_asl,            cf, indirizzo,          cap,               comune,      prov, nominativo     )
    VALUES (inputIdAsl, inputCfResp,  indirizzoResp, capResp,  inputDescComuneResp, inputProv, nominativoResp);
    end if;


INSERT INTO organization_address(
            org_id,      address_type, addrline1,             city,     state,   country, postalcode, entered, enteredby, modified, modifiedby, primary_address,                    latitude,  longitude)
    VALUES (outputOrgId,            1,  inputVia,  inputDescComune, inputProv,  'Italia',   inputCap,   now(),  idUtente,    now(),   idUtente,            true,  inputLat::double precision,  inputLong::double precision);


    


countAziende:= (select count(*) from aziende where cod_azienda = inputcodazienda); 

if(countAziende=0) then

   INSERT INTO public.aziende(
                cod_azienda,              indrizzo_azienda,                        cod_comune_azienda,           cap_azienda, prov_sede_azienda,                              latitudine,                                longitudine,                   id_fiscale_prop,                              dat_apertura_azienda,                               latitudine_geo , longitudine_geo                               )
    VALUES (inputcodazienda,         inputViaSedeOperativa,           inputProvCodice|| inputComIstat, inputCapSedeOperativa,         inputProv, inputLatSedeOperativa::double precision,  inputLongSedeOperativa::double precision ,                           inputcf,      inputDataInizio::timestamp without time zone,       inputLatSedeOperativa::double precision, inputLongSedeOperativa::double precision      );
    end if;

            

outputMsg := 'Allevamento di Acquacoltura importato in GISA.';
   

return outputMsg;
	
END 

$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.import_acquacoltura(text, text, text, text, text, text, integer, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text)
  OWNER TO postgres;
