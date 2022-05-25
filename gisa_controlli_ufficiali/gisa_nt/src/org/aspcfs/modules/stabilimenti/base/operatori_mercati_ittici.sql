insert into permission values(909,33,'stabilimenti-operatori-ittici',TRUE,false,false,false,'Gestione operatori mercati ittici',60000,TRUE,TRUE,FALSE);

CREATE TABLE operatori_associati_mercato_ittico
(
  id                serial NOT NULL PRIMARY KEY,
  id_mercato_ittico int    NOT NULL,
  id_operatore      int    NOT NULL,
  tipo_operatore    int    DEFAULT -1,
  entered	    timestamp DEFAULT CURRENT_TIMESTAMP,
  entered_by	    int    DEFAULT -1
);