
alter table tipocontrolloufficialeimprese add column note_hd text;

update tipocontrolloufficialeimprese set ispezione = -1, enabled=false,  note_hd = concat('Campo ispezione (oggetto del controllo) resettato a -1 a causa di controllo in sorveglianza. Valore precedente: ', ispezione) where ispezione > 0 and idcontrollo in (select ticketid from ticket where tipologia = 3 and provvedimenti_prescrittivi = 5 and trashed_date is null)