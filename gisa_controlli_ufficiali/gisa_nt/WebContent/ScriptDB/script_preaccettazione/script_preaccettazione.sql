Chi:Rita Mele	
Cosa: Aggiunto permesso per la visualizzazione del bottone Salva con preaccettazione SIGLA
Quando: 03/09/2018

--script per CASO D'USO 1.3 da lanciare
insert into permission (category_id,permission,permission_view,permission_add,permission_edit,permission_delete,description , level,enabled,active)
values (35,'campioni-campioni-addconpreaccettazione',true,false,false,false,'INSERISCI CAMPIONE CON PREACCETTAZIONE SIGLA',10,true,true);

insert into permission (category_id,permission,permission_view,permission_add,permission_edit,permission_delete,description , level,enabled,active)
values (35,'campioni-campioni-preaccettazionesenzacampione',true,false,false,false,'GENERA CODICE PREACCETTAZIONE SIGLA SENZA CAMPIONE',10,true,true);

insert into permission (category_id,permission,permission_view,permission_add,permission_edit,permission_delete,description , level,enabled,active)
values (35,'campioni-campioni-listacodicipreaccettazione',true,false,false,false,'VISUALIZZA LISTA CODICI PREACCETTAZIONE SIGLA',10,true,true);

insert into permission (category_id,permission,permission_view,permission_add,permission_edit,permission_delete,description , level,enabled,active)
values (35,'webgis-desktop',true,false,false,false,'WEBGIS DESKTOP',10,true,true);

--script abilitazione permesso report interno preaccettazione
insert into permission (category_id,permission,permission_view,permission_add,permission_edit,permission_delete,description , level,enabled,active)
values (35,'campioni-campioni-reportpreaccettazione',true,false,false,false,'REPORT INTERNO PREACCETTAZIONE SIGLA',10,true,true);