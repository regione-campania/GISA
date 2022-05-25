-- RITA MELE
-- 02/09/21
-- AGGIORNAMENTO ID RUOLO PER LE SCHEDE SUPPLEMENTARI IN QUANTO COINCIDEVA CON QUELLO DELLE GUARDIE ZOOFILE EXT
ALTER TABLE public.role_permission DROP CONSTRAINT role_permission_role_id_fkey;
update role  set role_id  = 334 where role_id = 329;
update role_permission set role_id = 334 where role_id = 329;
update access_ set role_id = 334 where role_id = 329;
update rel_gruppo_ruoli set id_ruolo  =334 where id_ruolo = 329;
ALTER TABLE public.role_permission
  ADD CONSTRAINT role_permission_role_id_fkey FOREIGN KEY (role_id)
      REFERENCES public.role (role_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

      
update guc_ruoli set ruolo_integer = 334 where ruolo_integer = 329 and ruolo_string = 'Gestore schede supplementari'
