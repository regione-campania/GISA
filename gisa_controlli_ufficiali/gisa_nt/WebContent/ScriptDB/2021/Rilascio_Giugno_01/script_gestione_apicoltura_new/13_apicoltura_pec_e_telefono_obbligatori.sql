
--elimino relazione tra i campi di configuratore_template_no_scia  con label ('DOMICILIO DIGITALE (PEC)', 'TELEFONO') della scheda apicoltura 'SCIA-APICOLTURA'
delete from schema_anagrafica where id in (
select sa.id from schema_anagrafica sa join configuratore_template_no_scia ctns on sa.id_campo_configuratore = ctns.id
	where sa.codice_univoco_ml ilike 'SCIA-APICOLTURA' and ctns.html_label in ('DOMICILIO DIGITALE (PEC)', 'TELEFONO')
);

--inserisco email impresa, email rapp legale e telefono rapp legale in configuratore_template_no_scia con required="true"
INSERT INTO configuratore_template_no_scia VALUES (570, 'DOMICILIO DIGITALE (PEC)', true, 'email_impresa', NULL, NULL, 'ae', 'text', NULL, NULL, NULL, 'email_impresa', '_b_email_impresa', NULL, 'size="50" maxlength="50" required="true"', NULL);
INSERT INTO configuratore_template_no_scia VALUES (571, 'DOMICILIO DIGITALE (PEC)', true, NULL, NULL, NULL, 'bt', 'text', NULL, NULL, NULL, 'email_rapp_legale', '_b_email_rapp_leg', NULL, 'size="50" maxlength="50" required="true"', NULL);
INSERT INTO configuratore_template_no_scia VALUES (572, 'TELEFONO', true, NULL, NULL, NULL, 'bu', 'text', NULL, NULL, NULL, 'telefono_rapp_legale', '_b_telefono_rapp_leg', NULL, 'maxlength="30" pattern="[0-9 \-+]{1,30}" required="true"', NULL);

--inserisco relazione email impresa, email rapp legale e telefono rapp legale in schema anagrafica con 'SCIA-APICOLTURA'
insert into schema_anagrafica(codice_univoco_ml, id_gruppo_template, id_campo_configuratore, campo_esteso, enabled) values('SCIA-APICOLTURA', 1, 570, false, true);
insert into schema_anagrafica(codice_univoco_ml, id_gruppo_template, id_campo_configuratore, campo_esteso, enabled) values('SCIA-APICOLTURA', 2, 571, false, true);
insert into schema_anagrafica(codice_univoco_ml, id_gruppo_template, id_campo_configuratore, campo_esteso, enabled) values('SCIA-APICOLTURA', 2, 572, false, true);

