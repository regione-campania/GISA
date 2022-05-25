INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active, viewpoints) VALUES (362, 50, 'farmacie-farmacie-vigilanza', true, true, true, true, 'Controlli Ufficiali Farmacosorveglianza', 802, true, true, false);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active, viewpoints) VALUES (3612122, 50, 'farmacie-farmacie-nonconformita', true, true, true, true, 'Non Conformita Farmacosorveglianza', 802, true, true, false);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active, viewpoints) VALUES (3612122, 50, 'farmacie-farmacie-sanzioni', true, true, true, true, 'Sanzioni Farmacosorveglianza', 802, true, true, false);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active, viewpoints) VALUES (123112, 50, 'farmacie-farmacie-sequestri', true, true, true, true, 'Sequestri Farmacosorveglianza', 802, true, true, false);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active, viewpoints) VALUES (2211998, 50, 'farmacie-farmacie-reati', true, true, true, true, 'Reati Farmacosorveglianza', 802, true, true, false);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active, viewpoints) VALUES (812122, 50, 'farmacie-farmacie-followup', true, true, true, true, 'Followup Farmacosorveglianza', 802, true, true, false);
alter table ticket add id_farmacia integer;
alter table organization add id_farmacia integer ;


    alter table organization add stato text;
    alter table organization add num_ric_dettaglio text;
    
    alter table organization add num_ric_ingrosso text;

    alter table organization add data_cambio_stato timestamp;
    
    alter table organization add data_ric_ingrosso timestamp;
    
    alter table organization add data_verifica_annua timestamp;
    
    alter table organization add data_ric_dettaglio timestamp;
     alter table organization add pregresso boolean;
alter table organization add note1 text;