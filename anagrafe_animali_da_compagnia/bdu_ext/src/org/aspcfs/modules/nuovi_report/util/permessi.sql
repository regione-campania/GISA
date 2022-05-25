INSERT INTO permission_category (category_id, category,level,enabled,active, folders, lookups, viewpoints, categories,scheduled_events, object_events, reports, webdav, logos,constant, action_plans, custom_list_views)
VALUES(28,'Nuovi Report',10000,TRUE,TRUE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,1001,FALSE,FALSE);



INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(401,28,'nuovi-report',TRUE,FALSE,FALSE,FALSE,'Accesso al Modulo',1,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(402,28,'cani-anagrafati',TRUE,FALSE,FALSE,FALSE,'Report Cani anagrafati',10,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(403,28,'cani-catturati',TRUE,FALSE,FALSE,FALSE,'Report Cani catturati',10,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(404,28,'cani-sterilizzati',TRUE,FALSE,FALSE,FALSE,'Report Cani sterilizzati',10,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(405,28,'cani-restituiti',TRUE,FALSE,FALSE,FALSE,'Report Cani restituiti',10,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(406,28,'cani-smarriti',TRUE,FALSE,FALSE,FALSE,'Report Cani smarriti',10,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(407,28,'cessioni',TRUE,FALSE,FALSE,FALSE,'Report cessioni',10,TRUE,TRUE,FALSE);





 INSERT INTO permission_category(
            category_id, category, description, "level", enabled, active, 
        folders, lookups, viewpoints, categories, scheduled_events, object_events, 
        reports, webdav, logos, constant, action_plans, custom_list_views)
VALUES (98, 'Report Veterinari','',10000, true,true, false,false, false, false,false, false, false, false, false, 1002, false, false);

    
INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (510,98,'nuovi-report-veterinari',true,false,false,false,'Accesso al Modulo',1,true,true,false);


INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (511,98,'report-veterinari-sterilizzati',true,false,false,false,'Report cani sterilizzati',10,true,true,false);

INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (512,98,'report-veterinari-anagrafati',true,false,false,false,'Report cani anagrafati',10,true,true,false);


            