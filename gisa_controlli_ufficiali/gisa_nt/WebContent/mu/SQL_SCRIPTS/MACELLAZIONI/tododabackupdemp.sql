alter table mu_capi
add numero_parti_assegnate integer,
add numero_parti integer

alter table mu_macellazioni
add descrizione_luogo_verifica_morte_am text,
add comunucazione_altro_testo_comunicazioni text,
add id_capo integer,
add data_visita_pm timestamp without time zone,
add id_esito_am integer,
add id_esito_pm integer,
add data_visita_am timestamp without time zone


alter table mu_capi
add flag_arrivato_deceduto boolean


