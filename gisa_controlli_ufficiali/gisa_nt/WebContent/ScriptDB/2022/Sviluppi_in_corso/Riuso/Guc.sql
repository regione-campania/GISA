delete from spid.log_query_generate;
delete from spid.sca_storico_login;
delete from spid.spid_registrazioni where numero_richiesta <> '2022-RIC-00000354';
delete from spid.spid_registrazioni_esiti where numero_richiesta <> '2022-RIC-00000354';
delete from spid.spid_registrazioni_flag where numero_richiesta <> '2022-RIC-00000354';

delete from extended_option where user_id not in (select id from guc_utenti_ where username = 'admin');
delete from guc_ruoli where id_utente not in (select id from guc_utenti_ where username = 'admin');
delete from guc_utenti_ where username <> 'admin';
delete from guc_cliniche_vam;
delete from guc_canili_bdu;
delete from guc_strutture_gisa;
delete from guc_ruoli_appo where username <> 'admin';
delete from guc_utenti_appo where username <> 'admin';
delete from guc_utenti_new where username <> 'admin';
delete from log_reload_utenti;
delete from log_ruoli_utenti;
delete from log_utenti;
delete from sca_storico_operazioni_utenti;
delete from storico_cambio_password;
delete from utenti where username <> 'admin';


SELECT schemaname,relname,n_live_tup 
  FROM pg_stat_user_tables  where n_live_tup > 0
ORDER BY n_live_tup DESC;
