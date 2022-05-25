-- Chi: Bartolo Sansone
-- Cosa: Flusso 234 - PagoPA 4.0 (post rev 7)
-- Quando: 02/11/2021

CREATE TABLE lookup_sanzioni_autorita_competenti(code serial primary key, description text, default_item boolean default false, level integer, enabled boolean default true);

CREATE TABLE lookup_sanzioni_enti_destinatari(code serial primary key, description text, default_item boolean default false, level integer, enabled boolean default true);

insert into lookup_sanzioni_autorita_competenti(description) values ('Altro');
insert into lookup_sanzioni_autorita_competenti(description) values ('Test');
insert into lookup_sanzioni_autorita_competenti(description) values ('Prova');

insert into lookup_sanzioni_enti_destinatari(description) values ('Altro');
insert into lookup_sanzioni_enti_destinatari(description) values ('Uno');
insert into lookup_sanzioni_enti_destinatari(description) values ('Due');

alter table ticket add column sanzione_competenza_uod boolean;
alter table ticket add column id_sanzione_autorita_competente integer;
alter table ticket add column id_sanzione_ente_destinatario integer;