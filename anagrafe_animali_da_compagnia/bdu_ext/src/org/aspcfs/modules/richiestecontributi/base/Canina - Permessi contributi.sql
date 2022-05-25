INSERT INTO permission_category(
            category_id, category, description, "level", enabled, active, 
            folders, lookups, viewpoints, categories, scheduled_events, object_events, 
            reports, webdav, logos, constant, action_plans, custom_list_views)
    VALUES (2000, 'Contributi', 'Modulo Contributi', 10045, true, true, 
            false, false, false, false, false, false, 
            false, false, false, 2002, false, false);
			
INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (10000, 2000, 'richiesta-contributi', true, false, 
            false, false, 'Richiesta Contributi', 245, true, 
            true, false);
			
INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (10001, 2000, 'approvazione-contributi', true, false, 
            false, false, 'Approvazione Contributi', 245, true, 
            true, false);
			
INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (9999, 2000, 'contributi', true, false, 
            false, false, 'Contributi', 245, true, 
            true, false);
			
INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (9998, 2000, 'contributi-approvati', true, false, 
            false, false, 'Contributi Approvati', 245, true, 
            true, false);
			
INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (9997, 2000, 'contributi-respinti', true, false, 
            false, false, 'Contributi Respinti', 245, true, 
            true, false);
			
INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (9996, 2000, 'report-contributi', true, false, 
            false, false, 'Report Contributi', 245, true, 
            true, false);
			
//permesso per stampare i file pdf con i contributi approvati
INSERT INTO permission(
             category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (2000 , 'report-approvazione-contributi',true,false,false,false, 'Stampa pdf contributi',245,true,true,false);