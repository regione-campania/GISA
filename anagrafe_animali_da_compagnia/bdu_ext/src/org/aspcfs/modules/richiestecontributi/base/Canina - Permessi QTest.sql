INSERT INTO permission_category(
            category_id, category, description, "level", enabled, active, 
            folders, lookups, viewpoints, categories, scheduled_events, object_events, 
            reports, webdav, logos, constant, action_plans, custom_list_views)
    VALUES (2010, 'QTest', 'Modulo QTest', 10046, true, true, 
            false, false, false, false, false, false, 
            false, false, false, 2004, false, false);
			
INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (10002, 2010, 'richiesta-query', true, false, 
            false, false, 'Q Test', 245, true, 
            true, false);