-- Table: lookup_site_id

-- DROP TABLE lookup_site_id;

CREATE TABLE lookup_tipologia_nodo_oia
(
  code serial NOT NULL,
  description character varying(300) NOT NULL,
  short_description character varying(300),
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT lookup_tipologia_nodo_oia_pkey PRIMARY KEY (code) 
)
WITH (
  OIDS=FALSE
);
ALTER TABLE lookup_tipologia_nodo_oia
  OWNER TO postgres;
  
alter table oia_nodo drop column tipologia_nodo ;
alter table oia_nodo add column id_tipologia_nodo_oia integer;
alter table oia_nodo drop column n_controlli_ufficiali ;
alter table oia_nodo drop column n_cu_campioni ;
alter table oia_nodo drop column permesso_modellatore  ;

INSERT INTO lookup_tipologia_nodo_oia(
             description, short_description, default_item, level, enabled)
    VALUES ('DIPARTIMENTO DI PERVENZIONE', 'DIP-PREVENZIONE', false, 0, true);

    INSERT INTO lookup_tipologia_nodo_oia(
             description, short_description, default_item, level, enabled)
    VALUES ('SERVIZIO VETERINARI', 'STRUTT.-VET', false, 1, true);

    
    INSERT INTO lookup_tipologia_nodo_oia(
             description, short_description, default_item, level, enabled)
    VALUES ('SERVIZIO IGIENE ALIMENTI E NUTRIZIONE', 'STRUTT.-SIAN', false, 2, true);

    
    INSERT INTO lookup_tipologia_nodo_oia(
             description, short_description, default_item, level, enabled)
    VALUES ('UOPC', 'UOPC', false, 3, true);

    
    INSERT INTO lookup_tipologia_nodo_oia(
             description, short_description, default_item, level, enabled)
    VALUES ('UOPV', 'UOPV', false, 4, true);
    
    
    
alter table oia_nodo drop column id_tipologia_nodo_oia ;
alter table oia_nodo add column tipologia_struttura int;

alter table oia_nodo drop column id_tipologia_nodo_oia ;
alter table oia_nodo add column tipologia_struttura int;
drop view view_pogrammazioni_piani_monitoraggio ;
drop view view_programmazioni_join_cu_campioni_eseguiti  ;
alter table cu_programmazioni_asl alter column


alter table cu_programmazioni_asl add id_nodo integer;
alter table tipocontrolloufficialeimprese  add id_unita_operativa integer;

--settare in cu programmazioni asl il campo id_asl con id nodo corrispondente.
update cu_programmazioni_asl set id_nodo = 8 where id_asl = 207 ;
update cu_programmazioni_asl set id_nodo = 9 where id_asl = 201 ;
update cu_programmazioni_asl set id_nodo = 10 where id_asl = 202 ;
update cu_programmazioni_asl set id_nodo = 11 where id_asl = 203 ;
update cu_programmazioni_asl set id_nodo = 12 where id_asl = 204 ;
update cu_programmazioni_asl set id_nodo = 13 where id_asl = 205 ;
update cu_programmazioni_asl set id_nodo = 14 where id_asl = 206 ;


--update in cu programmazioni_as settare cu pianificati a 0 dove vale -1


