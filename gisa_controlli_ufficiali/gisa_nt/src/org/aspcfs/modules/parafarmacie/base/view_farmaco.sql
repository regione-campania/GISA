CREATE OR REPLACE VIEW farmacie_view AS 
 SELECT ragione_sociale, description as asl, stato,data_cambio_stato, indirizzo, citta as comune,provincia, num_ric_ingrosso as numero_riconoscimento_ingrosso, data_ric_ingrosso, num_ric_dettaglio as numero_riconoscimento_dettaglio, data_ric_dettaglio
 FROM farmacie
 join lookup_site_id on (farmacie.site_id = lookup_site_id.code)
  WHERE trashed_date is null ORDER BY site_id;

  INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (3690,50,'parafarmacie-estrazione',TRUE,FALSE,FALSE,FALSE,'Estrazione Farmacie',15,TRUE,TRUE,FALSE);


