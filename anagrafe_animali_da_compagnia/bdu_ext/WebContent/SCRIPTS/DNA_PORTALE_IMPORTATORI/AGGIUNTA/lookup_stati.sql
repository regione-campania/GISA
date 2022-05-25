-- Table: lookup_stato_lista_convocazioni

-- DROP TABLE lookup_stato_lista_convocazioni;

CREATE TABLE lookup_stato_lista_convocazioni
(
  code serial NOT NULL,
  description character varying(300) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  entered timestamp without time zone,
  modified timestamp without time zone,
  CONSTRAINT lookup_stato_lista_convocazioni_pkey PRIMARY KEY (code)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE lookup_stato_lista_convocazioni
  OWNER TO postgres;


CREATE TABLE lookup_stato_convocati
(
  code serial NOT NULL,
  description character varying(300) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  entered timestamp without time zone,
  modified timestamp without time zone,
  CONSTRAINT lookup_stato_convocati_pkey PRIMARY KEY (code)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE lookup_stato_convocati
  OWNER TO postgres;



CREATE TABLE lookup_stato_convocazioni_temporali
(
  code serial NOT NULL,
  description character varying(300) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  entered timestamp without time zone,
  modified timestamp without time zone,
  CONSTRAINT lookup_stato_convocazioni_temporali_pkey PRIMARY KEY (code)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE lookup_stato_convocazioni_temporali
  OWNER TO postgres;





  ---FUNZIONE PER BDU
--drop function public_functions.getmicrochipsbycf(codice_fiscale_to_search text)
-- Function: public_functions.getmicrochipsbycf(text)

-- DROP FUNCTION public_functions.getmicrochipsbycf(text);

CREATE OR REPLACE FUNCTION public_functions.getmicrochipsbycf(IN codice_fiscale_to_search text)
  RETURNS TABLE(microchip_to_return text, flag_prelievo_dna_b boolean) AS
$BODY$
DECLARE
        r RECORD;
        
        
BEGIN
        
        
                FOR microchip_to_return,  flag_prelievo_dna_b
                in
        
select  microchip, cane.flag_prelievo_dna from animale a
left join cane on (a.id = cane.id_animale)
left join opu_operatori_denormalizzati p on (a.id_proprietario = p.id_rel_stab_lp)
where p.codice_fiscale = codice_fiscale_to_search
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
 $BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.getmicrochipsbycf(text)
  OWNER TO postgres;


  --select * from public_functions.getmicrochipsbycf('VLLSRG71A04F839D')VLLSRG71A04F839D

