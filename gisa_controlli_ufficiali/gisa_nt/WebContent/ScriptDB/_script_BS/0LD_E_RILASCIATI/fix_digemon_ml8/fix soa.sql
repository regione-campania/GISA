

select * from sintesis_importa_cu_da_organization(60111, 1242);
update organization set trashed_date = now(),note_hd ='cancellato perche in SINTESIS' where org_id = 60111;
select * from org_insert_into_ricerche_anagrafiche_old_materializzata(60111);
