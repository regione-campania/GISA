ALTER TABLE gestori_acque_log_import ADD COLUMN errore_insert text;
ALTER TABLE gestori_acque_log_import ADD COLUMN errore_parsing_file text;

ALTER TABLE gestori_acque_log_import ADD COLUMN esito boolean;