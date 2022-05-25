INSERT INTO public.lookup_tipologia_registrazione(
            code, description, default_item, level, enabled, entered, modified, 
            title, fuori_asl, effettuabile_con_animale_fuori_dominio_asl_da_asl_proprietaria, 
            effettuabile_con_animale_fuori_dominio_asl_da_asl_in_carico, 
            effettuabile_con_animale_fuori_dominio_asl_da_asl_diversa)
    VALUES ( 55, 'Trasferimento verso Sindaco',FALSE,1,TRUE,now(), now(),
    'Il trasferimento prevede la possibilità di selezionare un nuovo Proprietario Sindaco e detentore Canile appartenente alla Asl',
    FALSE,TRUE,TRUE,FALSE
);

INSERT INTO public.registrazioni_wk(id_stato, id_registrazione, id_prossimo_stato, only_hd)
    VALUES (2, 55, 3, true);

INSERT INTO public.registrazioni_wk(id_stato, id_registrazione, id_prossimo_stato, only_hd)
    VALUES (8, 55, 9, true);

INSERT INTO public.registrazioni_wk(id_stato, id_registrazione, id_prossimo_stato, only_hd)
    VALUES (19, 55, 3, true);

INSERT INTO public.registrazioni_wk(id_stato, id_registrazione, id_prossimo_stato, only_hd)
    VALUES (20, 55, 9, true);

INSERT INTO public.evento_html_fields(
            id_tipologia_evento, nome_campo, tipo_campo, label_campo, 
            ordine_campo, valore_campo, tipo_controlli_date, 
            label_campo_controlli_date, only_hd)
    VALUES (55, 'dataTrasferimento', 'data', 'Data del Trasferimento', 1, '''''', 'T2', 'Data trasferimento', 1);


INSERT INTO public.evento_html_fields(
            id_tipologia_evento, nome_campo, tipo_campo, label_campo, 
            ordine_campo, valore_campo, tipo_controlli_date, 
            label_campo_controlli_date, only_hd, label_link)
    VALUES (55, 'idProprietario', 'hidden', '', 2, '', null, '', 1,'Seleziona Proprietario');


INSERT INTO public.evento_html_fields(
            id_tipologia_evento, nome_campo, tipo_campo, label_campo, javascript_function, javascript_function_evento,
            ordine_campo, valore_campo, tipo_controlli_date, 
            label_campo_controlli_date, only_hd, label_link)
    VALUES (55, 'idProprietario', 'link', 'Seleziona il nuovo Proprietario', 'popUp(''OperatoreAction.do?command=SearchForm&tipologiaSoggetto=3&popup=true&tipoRegistrazione=55'')',
            'href', 3, '', null, '', 1,'Seleziona Proprietario');

INSERT INTO public.evento_html_fields(
            id_tipologia_evento, nome_campo, tipo_campo, label_campo, 
            ordine_campo, valore_campo, tipo_controlli_date, 
            label_campo_controlli_date, only_hd, label_link)
    VALUES (55, 'idDetentore', 'hidden', '', 4, '', null, 'Seleziona Detentore', 1,'');


INSERT INTO public.evento_html_fields(
            id_tipologia_evento, nome_campo, tipo_campo, label_campo, javascript_function, javascript_function_evento,
            ordine_campo, valore_campo, tipo_controlli_date, 
            label_campo_controlli_date, only_hd, label_link)
    VALUES (55, 'idDetentore', 'link', 'Seleziona il nuovo Detentore', 'popUp(''OperatoreAction.do?command=SearchForm&tipologiaSoggetto=4&popup=true&tipoRegistrazione=55'')',
            'href', 5, '', null, '', 1, 'Seleziona Detentore');


CREATE SEQUENCE public.evento_trasferimento_sindaco_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 423868
  CACHE 1;
ALTER TABLE public.evento_trasferimento_sindaco_id_seq
  OWNER TO postgres;



CREATE TABLE public.evento_trasferimento_sindaco
(
  id integer NOT NULL DEFAULT nextval('evento_trasferimento_sindaco_id_seq'::regclass),
  id_evento integer NOT NULL,
  data_trasferimento timestamp without time zone,
  id_nuovo_proprietario integer,
  id_vecchio_proprietario integer,
  id_nuovo_detentore integer,
  id_vecchio_detentore integer,
  id_comune_proprietario integer,
  luogo character varying,
  id_asl_vecchio_proprietario integer,
  id_asl_nuovo_proprietario integer,
  id_vecchio_proprietario_appoggio integer,
  CONSTRAINT evento_trasferimento_sindaco_pkey PRIMARY KEY (id),
  CONSTRAINT evento_trasferimento_sindaco_id_evento_fkey FOREIGN KEY (id_evento)
      REFERENCES public.evento (id_evento) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT evento_trasferimento_sindaco_id_nuovo_detentore_fkey FOREIGN KEY (id_nuovo_detentore)
      REFERENCES public.opu_relazione_stabilimento_linee_produttive (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT evento_trasferimento_sindaco_id_nuovo_proprietario_fkey FOREIGN KEY (id_nuovo_proprietario)
      REFERENCES public.opu_relazione_stabilimento_linee_produttive (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT evento_trasferimento_sindaco_id_vecchio_detentore_fkey FOREIGN KEY (id_vecchio_detentore)
      REFERENCES public.opu_relazione_stabilimento_linee_produttive (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT evento_trasferimento_sindaco_id_vecchio_proprietario_fkey FOREIGN KEY (id_vecchio_proprietario)
      REFERENCES public.opu_relazione_stabilimento_linee_produttive (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.evento_trasferimento_sindaco
  OWNER TO postgres;
GRANT ALL ON TABLE public.evento_trasferimento_sindaco TO postgres;
GRANT SELECT ON TABLE public.evento_trasferimento_sindaco TO usr_ro;
GRANT SELECT ON TABLE public.evento_trasferimento_sindaco TO report;


INSERT INTO public.mapping_registrazioni_specie_animale(
            id_registrazione, id_specie)
    VALUES ( 55, 1);
INSERT INTO public.mapping_registrazioni_specie_animale(
            id_registrazione, id_specie)
    VALUES ( 55, 2);
INSERT INTO public.mapping_registrazioni_specie_animale(
            id_registrazione, id_specie)
    VALUES ( 55, 3);



    
