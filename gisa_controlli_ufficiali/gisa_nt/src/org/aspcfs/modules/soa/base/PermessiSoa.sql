INSERT INTO permission_category (category_id, category, description, level,enabled,active, folders, lookups, viewpoints, categories,scheduled_events, object_events, reports, webdav, logos,constant, action_plans, custom_list_views)
VALUES(96,'Soa','Modulo Soa',800,TRUE,TRUE,TRUE,TRUE,FALSE,FALSE,FALSE,FALSE,TRUE,TRUE,FALSE,18,TRUE,FALSE);

INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES( 77106,96,'soa',TRUE,FALSE, FALSE, FALSE,'Accesso al modulo Soa',49,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES( 77107,96,'soa-dashboard',TRUE,FALSE,FALSE,FALSE,'Pannello',190,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77108,96,'soa-soa',TRUE,TRUE,TRUE,TRUE,'Soa',50,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77109,96,'accounts-accounts-soa-history',TRUE,TRUE,TRUE,TRUE,'Storia delle sanzioni',152,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77110,96,'soa-action-plans',TRUE,TRUE,TRUE,TRUE,'Action Plansa',340,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77111,96,'soa-assets',TRUE,TRUE,TRUE,TRUE,'Impianti',230,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77112,96,'soa-autoguide-inventory',TRUE,TRUE,TRUE,TRUE,'Inventario dei veicoli',210,FALSE,FALSE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77113,96,'soa-contributi-audit',TRUE,TRUE,FALSE,TRUE,'Esporta Dati Audit',170,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77114,96,'soa-contributi-reports',TRUE,TRUE,FALSE,TRUE,'Esporta dati contributi',172,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77115,96,'soa-directbill',TRUE,TRUE,TRUE,TRUE,'Direct Bill',360,FALSE,FALSE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77116,96,'soa-orders',TRUE,TRUE,TRUE,TRUE,'Ordini',280,FALSE,FALSE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77117,96,'soa-products',TRUE,TRUE,TRUE,TRUE,'Prodotti e Servizi',290,FALSE,FALSE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77118,96,'soa-projects',TRUE,FALSE,FALSE,FALSE,'Progetti',330,FALSE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77119,96,'soa-quotes',TRUE,TRUE,TRUE,TRUE,'Offerte',270,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77120,96,'soa-service-contracts',TRUE,TRUE,TRUE,TRUE,'Contratti di Servizio',220,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77121,96,'soa-soa-audit',TRUE,TRUE,TRUE,TRUE,'Audit',410,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77122,96,'soa-soa-audit-livello',TRUE,TRUE,TRUE,TRUE,'livello rischio Audit',153,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77123,96,'soa-soa-campioni',TRUE,TRUE,TRUE,TRUE,'Campioni',151,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77124,96,'soa-soa-tamponi-history',TRUE,TRUE,TRUE,TRUE,'history Tamponi',7000,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77125,96,'soa-soa-tamponi-documents',TRUE,TRUE,TRUE,TRUE,'documenti tamponi',7000,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77126,96,'soa-soa-campioni-documents',TRUE,TRUE,TRUE,TRUE,'Documenti sei Campioni',154,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77127,96,'soa-soa-campioni-history',TRUE,TRUE,TRUE,TRUE,'Storia Campioni',154,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77128,96,'soa-soa-cessazionevariazione',TRUE,TRUE,TRUE,TRUE,'Cessazione/Variazione',151,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77129,96,'soa-soa-cessazionevariazione-documents',TRUE,TRUE,TRUE,TRUE,'Documenti Cessazione/Variazione',154,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77130,96,'soa-soa-tamponi',TRUE,TRUE,TRUE,TRUE,'Tamponi',7000,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77131,96,'soa-upload',TRUE,TRUE,FALSE,FALSE,'Upload Soa',3,TRUE,TRUE,TRUE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77132,96,'soa-tipochecklist',TRUE,TRUE,TRUE,FALSE,'Aggiungi funzionalita' "Aggiungi check list"',153,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77133,96,'soa-soa-cessazionevariazione-history',TRUE,TRUE,TRUE,TRUE,'Storia Cessazione/ VAriazione',15445,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77134,96,'soa-soa-contact-updater',TRUE,FALSE,FALSE,FALSE,'Richiedi Aggiornamento informazioni del contatto',320,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77135,96,'soa-soa-contacts',TRUE,TRUE,TRUE,TRUE,'Contatti',52,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77136,96,'soa-soa-contacts-calls',TRUE,TRUE,TRUE,TRUE,'Attivita' contatti',54,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77137,96,'soa-soa-contacts-completed-calls',FALSE,FALSE,TRUE,FALSE,'Attivita' completate',55,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77138,96,'soa-soa-contacts-folders',TRUE,TRUE,TRUE,TRUE,'Contact Folders',370,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77139,96,'soa-soa-contacts-history',TRUE,TRUE,TRUE,TRUE,'Storia dei contatti',100,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77140,96,'soa-soa-contacts-imports',TRUE,TRUE,TRUE,TRUE,'Importa Soa/Contatti',300,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77141,96,'soa-soa-contacts-messages',TRUE,TRUE,TRUE,TRUE,'Messaggi dei Contatti',80,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77142,96,'soa-soa-contacts-move',TRUE,TRUE,FALSE,FALSE,'Sposta contatti in altri SOA',90,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77143,96,'soa-soa-contacts-opportunities',TRUE,TRUE,TRUE,TRUE,'Opportunita' Contatti',53,FALSE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77144,96,'soa-soa-contacts-opportunities-quotes',TRUE,TRUE,TRUE,TRUE,'Contact Opportunities Quotes',350,FALSE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77145,96,'soa-soa-contacts-opps-folders',TRUE,TRUE,TRUE,TRUE,'Contact Opportunities Folders',380,FALSE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77146,96,'soa-soa-contributi',TRUE,TRUE,TRUE,TRUE,'Permessi Contributi',421,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77147,96,'soa-soa-documents',TRUE,TRUE,TRUE,TRUE,'Documenti',160,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77148,96,'soa-soa-documentstore',TRUE,TRUE,TRUE,TRUE,'Soa Document Store',400,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77149,96,'soa-soa-folders',TRUE,TRUE,TRUE,TRUE,'Cartelle',51,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77150,96,'soa-soa-followup',TRUE,TRUE,TRUE,TRUE,'history followup',7000,FALSE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77151,96,'soa-soa-followup-documents',TRUE,TRUE,TRUE,TRUE,'followup',7000,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77152,96,'soa-soa-followup-history',TRUE,TRUE,TRUE,TRUE,'documenti followup',7000,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77153,96,'soa-soa-history',TRUE,TRUE,TRUE,TRUE,'storia SOA',180,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77154,96,'soa-soa-merce_in_out',TRUE,TRUE,TRUE,TRUE,'Merce In_Out',100,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77155,96,'soa-soa-nonconformita',TRUE,TRUE,TRUE,TRUE,'Non Conformita'',7000,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77156,96,'soa-soa-nonconformita-documents',TRUE,TRUE,TRUE,TRUE,'documenti non conformita'',7000,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77157,96,'soa-soa-nonconformita-history',TRUE,TRUE,TRUE,TRUE,'history non conformita'',7000,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77158,96,'soa-soa-opportunities',TRUE,TRUE,TRUE,TRUE,'opportunita'',110,FALSE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77159,96,'soa-soa-reati',TRUE,TRUE,TRUE,TRUE,'notizie di reato',7000,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77160,96,'soa-soa-reati-documents',TRUE,TRUE,TRUE,TRUE,'documenti notizie di reato',7000,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77161,96,'soa-soa-reati-history',TRUE,TRUE,TRUE,TRUE,'History notizie di reato',7000,FALSE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77162,96,'soa-soa-relationships',TRUE,TRUE,TRUE,TRUE,'Relazioni',310,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77163,96,'soa-soa-report',TRUE,FALSE,FALSE,FALSE,'report Dettaglio SOA',420,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77164,96,'soa-soa-reports',TRUE,TRUE,FALSE,TRUE,'Esporta Dato SOA',171,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77165,96,'soa-soa-revenue',TRUE,TRUE,TRUE,TRUE,'Reddito',200,FALSE,FALSE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77166,96,'soa-soa-sanzioni',TRUE,TRUE,TRUE,TRUE,'sanzioni',152,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77167,96,'soa-soa-sanzioni-documents',TRUE,TRUE,TRUE,TRUE,'Documenti delle sanzioni',156,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77168,96,'soa-soa-sequestri',TRUE,TRUE,TRUE,TRUE,'sequestri',7000,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77169,96,'soa-soa-sequestri-documents',TRUE,TRUE,TRUE,TRUE,'documenti sequestri',7000,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77170,96,'soa-soa-sequestri-history',TRUE,TRUE,TRUE,TRUE,'history sequestri',7000,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77171,96,'soa-soa-shareddocuments',TRUE,TRUE,TRUE,TRUE,'share Accounts Documents',390,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77172,96,'soa-soa-tickets',TRUE,TRUE,TRUE,TRUE,'Ticket',120,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77173,96,'soa-soa-tickets-activity-log',TRUE,TRUE,TRUE,TRUE,'Appuntamenti dei Ticket',250,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77174,96,'soa-soa-tickets-documents',TRUE,TRUE,TRUE,TRUE,'Documenti dei Ticket',150,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77175,96,'soa-soa-tickets-folders',TRUE,TRUE,TRUE,TRUE,'Cartella Ticket',140,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77176,96,'soa-soa-tickets-maintenance-report',TRUE,TRUE,TRUE,TRUE,'note di manutenzione dei ticket',240,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77177,96,'soa-soa-tickets-tasks',TRUE,TRUE,TRUE,TRUE,'Compiti Ticket',130,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77178,96,'soa-soa-vigilanza',TRUE,TRUE,TRUE,TRUE,'Vigilanza',151,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77179,96,'soa-soa-vigilanza-documents',TRUE,TRUE,TRUE,TRUE,'Documenti Vigilanza',154,TRUE,TRUE,FALSE);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active,viewpoints)
VALUES(77180,96,'soa-soa-vigilanza-history',TRUE,TRUE,TRUE,TRUE,'Storia Vigilanza',54,TRUE,TRUE,FALSE);



