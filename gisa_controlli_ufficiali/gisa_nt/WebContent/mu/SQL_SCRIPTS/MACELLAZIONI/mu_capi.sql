alter table mu_capi 
add flag_arrivato_deceduto boolean,
add nr_parti integer,
add nr_parti_assegnate integer,
add id_stato integer


alter table lookup_specie_mu 
add nr_parti_specie integer default 1