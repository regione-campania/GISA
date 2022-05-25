-- Inserire ruolo da guc, 
--access: false

--COAP
--Permessi copiati da "GUEST"

-- Rimuovere tutti i permessi
delete from role_permission where role_id = (select role_id from role where role = 'COAP');

-- Aggiungere tramite admin il permesso "Vedi pannello delle Performance" (View)


