--select * from role_permission where permission_id = 137 --16 entry
update role_permission set role_add = true where permission_id = 137


INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, level, enabled, 
            active, viewpoints, permission_offline_view, permission_offline_add, 
            permission_offline_edit, permission_offline_delete, entered, 
            modified) values (561,1,'proprietari_detentori_animali',TRUE,FALSE,FALSE,FALSE,
            'Permesso visualizzazione elenco animali da dettaglio proprietario',0,
            TRUE,TRUE,FALSE,FALSE,FALSE,FALSE,FALSE,'2013-10-23 12:42:39.657','2013-10-23 12:42:39.657')


            INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, level, enabled, 
            active, viewpoints, permission_offline_view, permission_offline_add, 
            permission_offline_edit, permission_offline_delete, entered, 
            modified) values (562,1,'proprietari_detentori_registrazioni',
            TRUE,FALSE,FALSE,FALSE,'Permesso visualizzazione elenco registrazioni da dettaglio proprietario',0,
            TRUE,TRUE,FALSE,FALSE,FALSE,FALSE,FALSE,'2013-10-23 12:42:39.657','2013-10-23 12:42:39.657')



            INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, level, enabled, 
            active, viewpoints, permission_offline_view, permission_offline_add, 
            permission_offline_edit, permission_offline_delete, entered, 
            modified) values
            (563,35,'anagrafe_canina_cronologia_modifiche',TRUE,FALSE,FALSE,FALSE,
            'Permesso visualizzazione tab cronologia modifiche da dettaglio animale',0,
            TRUE,TRUE,FALSE,FALSE,FALSE,FALSE,FALSE,'2013-10-30 12:03:34.227','2013-10-30 12:03:34.227')


INSERT INTO permission_category(
            category_id, category, description, level, enabled, active, folders, 
            lookups, viewpoints, categories, scheduled_events, object_events, 
            reports, webdav, logos, constant, action_plans, custom_list_views, 
            entered, modified, dashboards, customtabs, email_accounts)
    VALUES 
            (43,'Profilassi rabbia','',0,
            TRUE,TRUE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,12,FALSE,FALSE,
            '2013-10-31 09:33:04.386','2013-10-31 09:33:04.386',FALSE,FALSE,FALSE)

            INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, level, enabled, 
            active, viewpoints, permission_offline_view, permission_offline_add, 
            permission_offline_edit, permission_offline_delete, entered, 
            modified) values(
            564,43,'profilassi_rabbia_modulo',TRUE,FALSE,FALSE,FALSE,
            'Permesso visualizzazione modulo profilassi rabbia',0,
            TRUE,TRUE,FALSE,FALSE,FALSE,FALSE,FALSE,'2013-10-31 09:34:00.426','2013-10-31 09:34:00.426')



  INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, level, enabled, 
            active, viewpoints, permission_offline_view, permission_offline_add, 
            permission_offline_edit, permission_offline_delete, entered, 
            modified) values(
            565,43,'vaccinazione_anti_rabbia_inserimento',TRUE,TRUE,FALSE,FALSE,
            'Permesso inserimento vaccinazione anti rabbia',0,
            TRUE,TRUE,FALSE,FALSE,FALSE,FALSE,FALSE,'2013-10-31 09:34:22.474','2013-10-31 09:34:22.474')

              INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, level, enabled, 
            active, viewpoints, permission_offline_view, permission_offline_add, 
            permission_offline_edit, permission_offline_delete, entered, 
            modified) values(
            566,43,'vaccinazione_anti_rabbia_titolazione_anticorpi',TRUE,FALSE,FALSE,FALSE,
            'Permesso generazione modulo titolazione anticorpi rabbia',0,
            TRUE,TRUE,FALSE,FALSE,FALSE,FALSE,FALSE,'2013-10-31 09:34:52.634','2013-10-31 09:34:52.634'
            )

            
INSERT INTO permission_category(
            category_id, category, description, level, enabled, active, folders, 
            lookups, viewpoints, categories, scheduled_events, object_events, 
            reports, webdav, logos, constant, action_plans, custom_list_views, 
            entered, modified, dashboards, customtabs, email_accounts)
    VALUES 
            (44,'Estrazione Mensile','',10000,TRUE,TRUE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,10000,FALSE,FALSE,
            '2013-11-04 12:25:02.678','2013-11-04 12:25:02.678',FALSE,FALSE,FALSE)

INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, level, enabled, 
            active, viewpoints, permission_offline_view, permission_offline_add, 
            permission_offline_edit, permission_offline_delete, entered, 
            modified) values(
            568,44,'estrazioneMensile',TRUE,FALSE,FALSE,FALSE,
            'Accesso modulo Estrazione mensile canili',0,
            TRUE,TRUE,FALSE,FALSE,FALSE,FALSE,FALSE,'2013-11-04 12:25:05.87','2013-11-04 12:25:05.87'
            )

INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, level, enabled, 
            active, viewpoints, permission_offline_view, permission_offline_add, 
            permission_offline_edit, permission_offline_delete, entered, 
            modified) values
(
569,1,'proprietari_detentori_registrazioni_animale_view',
TRUE,FALSE,FALSE,FALSE,'Permesso di visualizzazione registrazioni su animali relative a un proprietario',1,
TRUE,TRUE,FALSE,FALSE,FALSE,FALSE,FALSE,'2013-11-05 09:59:14.963','2013-11-05 09:59:14.963')



INSERT INTO lookup_tipologia_registrazione(
            code, description, default_item, level, enabled, entered, modified, 
            title)
    VALUES (49,'Registrazione diretta a proprietario/detentore fuori regione',FALSE,null,FALSE,'2013-02-12 15:13:00.132','2013-02-12 15:13:00.132','''')


INSERT INTO registrazioni_wk(
            id, id_stato, id_registrazione, id_prossimo_stato)
    VALUES (276,1,49,6)


ALTER TABLE evento
ADD id_proprietario_corrente integer,
ADD id_detentore_corrente integer


update role_permission set role_view = false where role_id = 24 and permission_id = 137


--CAMBIO UTENTE HELP DESK
INSERT INTO role_permission(
            id, role_id, permission_id, role_view, role_add, role_edit, role_delete, 
            role_offline_view, role_offline_add, role_offline_edit, role_offline_delete, 
            entered, modified) values(1269,5,350,true,true,true,false,false,false,false,false,'2013-10-31 15:02:41.262','2013-10-31 15:02:41.262')
INSERT INTO role_permission(
            id, role_id, permission_id, role_view, role_add, role_edit, role_delete, 
            role_offline_view, role_offline_add, role_offline_edit, role_offline_delete, 
            entered, modified) values(1270,6,350,true,false,false,false,false,false,false,false,'2013-09-24 16:45:10.338','2013-09-24 16:45:10.338')



            