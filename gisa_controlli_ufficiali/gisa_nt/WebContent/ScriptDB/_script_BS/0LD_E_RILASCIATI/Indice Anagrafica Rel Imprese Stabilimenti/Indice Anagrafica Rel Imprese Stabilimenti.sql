-- Index: anagrafica.idx_rel_imprese_stabilimenti_stabilimenti_unq

-- DROP INDEX anagrafica.idx_rel_imprese_stabilimenti_stabilimenti_unq;

CREATE UNIQUE INDEX idx_rel_imprese_stabilimenti_stabilimenti_unq
  ON anagrafica.rel_imprese_stabilimenti
  USING btree
  (id_stabilimento)
  WHERE data_scadenza IS NULL AND data_cancellazione IS NULL;