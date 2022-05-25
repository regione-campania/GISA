
INSERT INTO permission_category(
            category_id, category, description, "level", enabled, active, 
            folders, lookups, viewpoints, categories, scheduled_events, object_events, 
            reports, webdav, logos, constant, action_plans, custom_list_views)
    VALUES (111,'Esercenti Fuori Regione','Modulo Esercenti Fuori Regione',222222,TRUE,TRUE,FALSE,
	FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,5464,FALSE,FALSE);
	
INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (900000,111,'esercentifuoriregione',TRUE,TRUE,TRUE,TRUE,'Accesso Modulo Esercenti Fuori Regione',10,TRUE,TRUE,FALSE);

 INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (900001,111,'esercentifuoriregione-esercentifuoriregione',TRUE,TRUE,TRUE,TRUE,'Esercenti Fuori Regione',15,TRUE,TRUE,FALSE);

INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (900002,111,'esercentifuoriregione-esercentifuoriregione-folders',TRUE,TRUE,TRUE,TRUE,'Cartelle',20,TRUE,TRUE,FALSE);

	
INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (900003,111,'esercentifuoriregione-dashboard',TRUE,TRUE,TRUE,TRUE,'Dashboard',20,TRUE,TRUE,FALSE);
	
INSERT INTO lookup_tipo_storico(
            code, description, default_item, "level", enabled)
    VALUES (11, 'Esercenti Fuori Regione', FALSE, 10, TRUE);     