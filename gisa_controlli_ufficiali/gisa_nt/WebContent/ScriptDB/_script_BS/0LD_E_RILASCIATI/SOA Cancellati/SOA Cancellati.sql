---------------------------------- QUESTI NON HANNO CONTROLLI

----------------------- QUESTI HANNO PIU' DI UNA LINEA - DISABILITARE LINEA

-- U150044AV000259
select id, id_linea_produttiva, id_stabilimento, note_internal_use_hd_only, codice_univoco_ml from opu_relazione_stabilimento_linee_produttive where enabled and id_stabilimento in (select id from opu_stabilimento where numero_registrazione ilike 'U150044AV000259');

update opu_relazione_stabilimento_linee_produttive set note_internal_use_hd_only = concat_ws(';', note_internal_use_hd_only, 'Linea disabilitata perchè SOA non presente in SINTESIS come da mail GISA:soa da sistemare 27/03/18'), enabled = false where id = 212964;

select * from refresh_anagrafica(209270, 'opu');

-- U150109AV000196
select id, id_linea_produttiva, id_stabilimento, note_internal_use_hd_only, codice_univoco_ml from opu_relazione_stabilimento_linee_produttive where enabled and id_stabilimento in (select id from opu_stabilimento where numero_registrazione ilike 'U150109AV000196');

update opu_relazione_stabilimento_linee_produttive set note_internal_use_hd_only = concat_ws(';', note_internal_use_hd_only, 'Linea disabilitata perchè SOA non presente in SINTESIS come da mail GISA:soa da sistemare 27/03/18'), enabled = false where id = 219944;

select * from refresh_anagrafica(217734, 'opu');

--U150109AV000194
select id, id_linea_produttiva, id_stabilimento, note_internal_use_hd_only, codice_univoco_ml from opu_relazione_stabilimento_linee_produttive where enabled and id_stabilimento in (select id from opu_stabilimento where numero_registrazione ilike 'U150109AV000194');

update opu_relazione_stabilimento_linee_produttive set note_internal_use_hd_only = concat_ws(';', note_internal_use_hd_only, 'Linea disabilitata perchè SOA non presente in SINTESIS come da mail GISA:soa da sistemare 27/03/18'), enabled = false where id = 219713;

select * from refresh_anagrafica(217462, 'opu');

---------------------------------- QUESTI HANNO CONTROLLI (DA CANCELLARE)

----------------------- QUESTI HANNO PIU' DI UNA LINEA - CANCELLARE LINEA

--U150050NA000918
select id, id_linea_produttiva, id_stabilimento, note_internal_use_hd_only, codice_univoco_ml from opu_relazione_stabilimento_linee_produttive where enabled and id_stabilimento in (select id from opu_stabilimento where numero_registrazione ilike 'U150050NA000918');

update opu_relazione_stabilimento_linee_produttive set note_internal_use_hd_only = concat_ws(';', note_internal_use_hd_only, 'Linea disabilitata perchè SOA non presente in SINTESIS come da mail GISA:soa da sistemare 27/03/18'), enabled = false where id = 231108;

select * from refresh_anagrafica(229499, 'opu');

update ticket set trashed_date = now(), note_internal_use_only = concat_ws(';', note_internal_use_only, 'CU Cancellato perchè agganciato a SOA non presente in SINTESIS come da mail GISA:soa da sistemare 27/03/18') where ticketid in (select ticketid from ticket where  ticketid in (select id_controllo_ufficiale from opu_linee_attivita_controlli_ufficiali where id_linea_attivita = 231108));

--U150079CE000017
select id, id_linea_produttiva, id_stabilimento, note_internal_use_hd_only, codice_univoco_ml from opu_relazione_stabilimento_linee_produttive where enabled and id_stabilimento in (select id from opu_stabilimento where numero_registrazione ilike 'U150079CE000017');

update opu_relazione_stabilimento_linee_produttive set note_internal_use_hd_only = concat_ws(';', note_internal_use_hd_only, 'Linea disabilitata perchè SOA non presente in SINTESIS come da mail GISA:soa da sistemare 27/03/18'), enabled = false where id = 228679;

select * from refresh_anagrafica(95539, 'opu');

update ticket set trashed_date = now(), note_internal_use_only = concat_ws(';', note_internal_use_only, 'CU Cancellato perchè agganciato a SOA non presente in SINTESIS come da mail GISA:soa da sistemare 27/03/18') where ticketid in (select ticketid from ticket where  ticketid in (select id_controllo_ufficiale from opu_linee_attivita_controlli_ufficiali where id_linea_attivita = 228679));

----------------------- QUESTI HANNO UNA SOLA LINEA - CANCELLARE STABILIMENTO

--U150049NA005789
select id, id_linea_produttiva, id_stabilimento, note_internal_use_hd_only, codice_univoco_ml from opu_relazione_stabilimento_linee_produttive where enabled and id_stabilimento in (select id from opu_stabilimento where numero_registrazione ilike 'U150049NA005789');

update opu_relazione_stabilimento_linee_produttive set note_internal_use_hd_only = concat_ws(';', note_internal_use_hd_only, 'Linea disabilitata perchè SOA non presente in SINTESIS come da mail GISA:soa da sistemare 27/03/18'), enabled = false where id = 178908;

update opu_stabilimento set notes_hd =concat_ws(';', notes_hd, 'Stabilimento Cancellato perchè SOA non presente in SINTESIS come da mail GISA:soa da sistemare 27/03/18'), trashed_date = now() where id = 198598;

select * from refresh_anagrafica(198598, 'opu');

update ticket set trashed_date = now(), note_internal_use_only = concat_ws(';', note_internal_use_only, 'CU Cancellato perchè agganciato a SOA non presente in SINTESIS come da mail GISA:soa da sistemare 27/03/18') where ticketid in (select ticketid from ticket where  ticketid in (select id_controllo_ufficiale from opu_linee_attivita_controlli_ufficiali where id_linea_attivita = 178908));


--U150049NA005790
select id, id_linea_produttiva, id_stabilimento, note_internal_use_hd_only, codice_univoco_ml from opu_relazione_stabilimento_linee_produttive where enabled and id_stabilimento in (select id from opu_stabilimento where numero_registrazione ilike 'U150049NA005790');

update opu_relazione_stabilimento_linee_produttive set note_internal_use_hd_only = concat_ws(';', note_internal_use_hd_only, 'Linea disabilitata perchè SOA non presente in SINTESIS come da mail GISA:soa da sistemare 27/03/18'), enabled = false where id = 178906;

update opu_stabilimento set notes_hd =concat_ws(';', notes_hd, 'Stabilimento Cancellato perchè SOA non presente in SINTESIS come da mail GISA:soa da sistemare 27/03/18'), trashed_date = now() where id = 198597;

select * from refresh_anagrafica(198597, 'opu');

update ticket set trashed_date = now(), note_internal_use_only = concat_ws(';', note_internal_use_only, 'CU Cancellato perchè agganciato a SOA non presente in SINTESIS come da mail GISA:soa da sistemare 27/03/18') where ticketid in (select ticketid from ticket where  ticketid in (select id_controllo_ufficiale from opu_linee_attivita_controlli_ufficiali where id_linea_attivita = 178906));

--U150079SA000217
select id, id_linea_produttiva, id_stabilimento, note_internal_use_hd_only, codice_univoco_ml from opu_relazione_stabilimento_linee_produttive where enabled and id_stabilimento in (select id from opu_stabilimento where numero_registrazione ilike 'U150079SA000217');

update opu_relazione_stabilimento_linee_produttive set note_internal_use_hd_only = concat_ws(';', note_internal_use_hd_only, 'Linea disabilitata perchè SOA non presente in SINTESIS come da mail GISA:soa da sistemare 27/03/18'), enabled = false where id = 181345;

update opu_stabilimento set notes_hd =concat_ws(';', notes_hd, 'Stabilimento Cancellato perchè SOA non presente in SINTESIS come da mail GISA:soa da sistemare 27/03/18'), trashed_date = now() where id = 172789;

select * from refresh_anagrafica(172789, 'opu');

-- ne ha 80
update ticket set trashed_date = now(), note_internal_use_only = concat_ws(';', note_internal_use_only, 'CU Cancellato perchè agganciato a SOA non presente in SINTESIS come da mail GISA:soa da sistemare 27/03/18') where ticketid in (select ticketid from ticket where  ticketid in (select id_controllo_ufficiale from opu_linee_attivita_controlli_ufficiali where id_linea_attivita = 181345));


--U150099AV000511
select id, id_linea_produttiva, id_stabilimento, note_internal_use_hd_only, codice_univoco_ml from opu_relazione_stabilimento_linee_produttive where enabled and id_stabilimento in (select id from opu_stabilimento where numero_registrazione ilike 'U150099AV000511');

update opu_relazione_stabilimento_linee_produttive set note_internal_use_hd_only = concat_ws(';', note_internal_use_hd_only, 'Linea disabilitata perchè SOA non presente in SINTESIS come da mail GISA:soa da sistemare 27/03/18'), enabled = false where id = 219967;

update opu_stabilimento set notes_hd =concat_ws(';', notes_hd, 'Stabilimento Cancellato perchè SOA non presente in SINTESIS come da mail GISA:soa da sistemare 27/03/18'), trashed_date = now() where id = 217757;

select * from refresh_anagrafica(217757, 'opu');

update ticket set trashed_date = now(), note_internal_use_only = concat_ws(';', note_internal_use_only, 'CU Cancellato perchè agganciato a SOA non presente in SINTESIS come da mail GISA:soa da sistemare 27/03/18') where ticketid in (select ticketid from ticket where  ticketid in (select id_controllo_ufficiale from opu_linee_attivita_controlli_ufficiali where id_linea_attivita = 219967));

--U150099AV000512
select id, id_linea_produttiva, id_stabilimento, note_internal_use_hd_only, codice_univoco_ml from opu_relazione_stabilimento_linee_produttive where enabled and id_stabilimento in (select id from opu_stabilimento where numero_registrazione ilike 'U150099AV000512');

update opu_relazione_stabilimento_linee_produttive set note_internal_use_hd_only = concat_ws(';', note_internal_use_hd_only, 'Linea disabilitata perchè SOA non presente in SINTESIS come da mail GISA:soa da sistemare 27/03/18'), enabled = false where id = 219966;

update opu_stabilimento set notes_hd =concat_ws(';', notes_hd, 'Stabilimento Cancellato perchè SOA non presente in SINTESIS come da mail GISA:soa da sistemare 27/03/18'), trashed_date = now() where id = 217756;

select * from refresh_anagrafica(217756, 'opu');

update ticket set trashed_date = now(), note_internal_use_only = concat_ws(';', note_internal_use_only, 'CU Cancellato perchè agganciato a SOA non presente in SINTESIS come da mail GISA:soa da sistemare 27/03/18') where ticketid in (select ticketid from ticket where  ticketid in (select id_controllo_ufficiale from opu_linee_attivita_controlli_ufficiali where id_linea_attivita = 219966));

--U150137SA000852
select id, id_linea_produttiva, id_stabilimento, note_internal_use_hd_only, codice_univoco_ml from opu_relazione_stabilimento_linee_produttive where enabled and id_stabilimento in (select id from opu_stabilimento where numero_registrazione ilike 'U150137SA000852');

update opu_relazione_stabilimento_linee_produttive set note_internal_use_hd_only = concat_ws(';', note_internal_use_hd_only, 'Linea disabilitata perchè SOA non presente in SINTESIS come da mail GISA:soa da sistemare 27/03/18'), enabled = false where id = 201306;

update opu_stabilimento set notes_hd =concat_ws(';', notes_hd, 'Stabilimento Cancellato perchè SOA non presente in SINTESIS come da mail GISA:soa da sistemare 27/03/18'), trashed_date = now() where id = 193686;

select * from refresh_anagrafica(193686, 'opu');

update ticket set trashed_date = now(), note_internal_use_only = concat_ws(';', note_internal_use_only, 'CU Cancellato perchè agganciato a SOA non presente in SINTESIS come da mail GISA:soa da sistemare 27/03/18') where ticketid in (select ticketid from ticket where  ticketid in (select id_controllo_ufficiale from opu_linee_attivita_controlli_ufficiali where id_linea_attivita = 201306));
