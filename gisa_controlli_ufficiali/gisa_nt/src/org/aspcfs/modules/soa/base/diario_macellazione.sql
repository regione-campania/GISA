insert into permission values(7200,33,'soa-soa-diario',TRUE,TRUE,TRUE,TRUE,'Diario di Macellazione',60000,TRUE,TRUE,FALSE);

CREATE TABLE soa_diario_macellazione
(
  id serial       PRIMARY KEY,
  id_soa int,
  id_specie       int,
  data            DATE,
  entered         TIMESTAMP,
  entered_by      int,
  modified        TIMESTAMP,
  modified_by     int,
  trashed_date    TIMESTAMP,
  enabled         boolean DEFAULT TRUE
);
