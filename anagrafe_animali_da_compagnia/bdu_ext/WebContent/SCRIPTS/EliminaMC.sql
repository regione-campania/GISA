CREATE UNIQUE INDEX evita_duplicati_banca_dati_a_priori
  ON public.microchips
  USING btree
  (microchip COLLATE pg_catalog."default")
  where trashed_date is null;

-- Prestare attenzione quando si esegue l'insert sulla tabella 'role permission', il campo permission_id dipende dall'id generato dall'insert precedente su permission

INSERT INTO public.permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, level, enabled, 
            active, viewpoints, permission_offline_view, permission_offline_add, 
            permission_offline_edit, permission_offline_delete, entered, 
            modified)
    VALUES ( nextval('permission_permission_id_seq'), 28, 'elimina', TRUE, FALSE, 
            FALSE, FALSE, 'Permesso Elimina Microchip per HD', 1, TRUE, 
            TRUE, FALSE, FALSE, FALSE, 
            FALSE, FALSE, now(), 
            now());

                        
                        
INSERT INTO public.role_permission(
            id, role_id, permission_id, role_view, role_add, role_edit, role_delete, 
            role_offline_view, role_offline_add, role_offline_edit, role_offline_delete, 
            entered, modified)
    VALUES (nextval('role_permission_id_seq'), 5, 590, TRUE, TRUE, TRUE, TRUE, 
            FALSE, FALSE, FALSE, FALSE, 
            now(), now());

                        
                        
INSERT INTO public.role_permission(
            id, role_id, permission_id, role_view, role_add, role_edit, role_delete, 
            role_offline_view, role_offline_add, role_offline_edit, role_offline_delete, 
            entered, modified)
    VALUES (nextval('role_permission_id_seq'), 6, 590, TRUE, TRUE, TRUE, TRUE, 
            FALSE, FALSE, FALSE, FALSE, 
            now(), now());