INSERT INTO role(
            role_id, role, description, enteredby, entered, modifiedby, modified, 
            enabled, role_type)
    VALUES (5,'Amministratore asl','',2,'2014-01-23 11:24:00.888',2,'2014-01-23 11:24:00.888',TRUE, 0);

    INSERT INTO role(
            role_id, role, description, enteredby, entered, modifiedby, modified, 
            enabled, role_type)
    VALUES (7,'Utente comune','',2,'2014-01-23 11:24:00.888',2,'2014-01-23 11:24:00.888',TRUE, 0);
    
INSERT INTO role_permission(
             role_id, permission_id, role_view, role_add, role_edit, role_delete, 
            role_offline_view, role_offline_add, role_offline_edit, role_offline_delete, 
            entered, modified)
    VALUES (5,9,TRUE,TRUE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,'2014-01-23 11:26:57.975','2014-01-23 11:26:57.975');

    INSERT INTO role_permission(
            id, role_id, permission_id, role_view, role_add, role_edit, role_delete, 
            role_offline_view, role_offline_add, role_offline_edit, role_offline_delete, 
            entered, modified)
    VALUES (28,7,9,TRUE,FALSE,TRUE,FALSE,FALSE,FALSE,FALSE,FALSE,'2014-01-23 11:27:20.078','2014-01-23 11:27:20.078');

             INSERT INTO role_permission(
            id, role_id, permission_id, role_view, role_add, role_edit, role_delete, 
            role_offline_view, role_offline_add, role_offline_edit, role_offline_delete, 
            entered, modified)
    VALUES (
    32,7,4,TRUE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,'2014-01-23 11:37:26.442','2014-01-23 11:37:26.442');



     INSERT INTO role_permission(
            id, role_id, permission_id, role_view, role_add, role_edit, role_delete, 
            role_offline_view, role_offline_add, role_offline_edit, role_offline_delete, 
            entered, modified)
    VALUES (
    31,5,4,TRUE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,'2014-01-23 11:37:26.442','2014-01-23 11:37:26.442');

 INSERT INTO permission_category(
            category_id, category, description, level, enabled, active, folders, 
            lookups, viewpoints, categories, scheduled_events, object_events, 
            reports, webdav, logos, constant, action_plans, custom_list_views, 
            entered, modified, dashboards, customtabs, email_accounts)
    VALUES (   
4,'Prelievo DNA','',0,TRUE,TRUE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,4,FALSE,FALSE,'2014-01-23 11:20:39.441','2014-01-23 11:20:39.441',FALSE,FALSE,FALSE)



INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, level, enabled, 
            active, viewpoints, permission_offline_view, permission_offline_add, 
            permission_offline_edit, permission_offline_delete, entered, 
            modified)
    VALUES ( 9,4,'lista_convocazione',TRUE,TRUE,TRUE,TRUE,'Accesso modulo/funzioni gestione convocazioni DNA',0,TRUE,TRUE,FALSE,FALSE,FALSE,FALSE,FALSE,'2014-01-23 11:23:05.849','2014-01-23 11:23:05.849');