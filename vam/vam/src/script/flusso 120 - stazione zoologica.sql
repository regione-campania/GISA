insert into permessi_funzione values(20,null, 'ACCESSO_BDU');
insert into permessi_subfunzione  values(83,null, 'MAIN',20);
insert into permessi_subfunzione  values(84,null, 'ADD',20);
insert into permessi_gui   values(110,null, 'MAIN',83);
insert into permessi_gui   values(111,null, 'MAIN',84);
insert into subject values ('ACCESSO_BDU->MAIN->MAIN');
insert into subject values ('ACCESSO_BDU->ADD->MAIN');
insert into capability values(730, 'Amministratore',null,'ACCESSO_BDU->MAIN->MAIN');
insert into capability values(731, 'Amministratore',null,'ACCESSO_BDU->ADD->MAIN');
insert into capability values(732, 'Ambulatorio - Amministrativo',null,'ACCESSO_BDU->MAIN->MAIN');
insert into capability values(733, 'Ambulatorio - Amministrativo',null,'ACCESSO_BDU->ADD->MAIN');
insert into capability values(734, 'Ambulatorio - Tecnico di supporto',null,'ACCESSO_BDU->MAIN->MAIN');
insert into capability values(735, 'Ambulatorio - Tecnico di supporto',null,'ACCESSO_BDU->ADD->MAIN');
insert into capability values(736, 'Ambulatorio - Veterinario',null,'ACCESSO_BDU->MAIN->MAIN');
insert into capability values(737, 'Ambulatorio - Veterinario',null,'ACCESSO_BDU->ADD->MAIN');
insert into capability values(738, 'HD1',null,'ACCESSO_BDU->MAIN->MAIN');
insert into capability values(739, 'HD1',null,'ACCESSO_BDU->ADD->MAIN');
insert into capability values(740, 'HD2',null,'ACCESSO_BDU->MAIN->MAIN');
insert into capability values(741, 'HD2',null,'ACCESSO_BDU->ADD->MAIN');
insert into capability values(742, 'Referente Asl',null,'ACCESSO_BDU->MAIN->MAIN');
insert into capability values(743, 'Referente Asl',null,'ACCESSO_BDU->ADD->MAIN');
insert into capability_permission values(730, 'w');
insert into capability_permission values(731, 'w');
insert into capability_permission values(732, 'w');
insert into capability_permission values(733, 'w');
insert into capability_permission values(734, 'w');
insert into capability_permission values(735, 'w');
insert into capability_permission values(736, 'w');
insert into capability_permission values(737, 'w');
insert into capability_permission values(738, 'w');
insert into capability_permission values(739, 'w');
insert into capability_permission values(740, 'w');
insert into capability_permission values(741, 'w');
insert into capability_permission values(742, 'w');
insert into capability_permission values(743, 'w');



