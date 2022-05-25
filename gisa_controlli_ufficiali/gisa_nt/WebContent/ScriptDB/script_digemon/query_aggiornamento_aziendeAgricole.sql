--aggiornamento tabella master list suap presente in esercizios
update master_list_suap set id_norma = 1 where id_norma = 7;
--aggiornamento tabella materializzata norma
update opu_linee_attivita_nuove_materializzata set id_norma = 1 where id_norma = 7;
--aggiornamento di linee_pregresse a true per i 2 stabilimenti per cui non si vedeva il bottone di aggiornamento linee pregresse
update opu_stabilimento set linee_pregresse = true where id in (135123,133023);

delete from ricerche_anagrafiche_old_materializzata;
insert into ricerche_anagrafiche_old_materializzata (select * from ricerca_anagrafiche);

update opu_lookup_norme_master_list set enabled=false where code = 7;

