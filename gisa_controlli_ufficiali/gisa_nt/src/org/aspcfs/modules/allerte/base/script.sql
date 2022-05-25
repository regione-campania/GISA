insert into permission_category values(28,'Allerte',' ',900,TRUE,TRUE,TRUE,TRUE,FALSE,FALSE,FALSE,FALSE,TRUE,TRUE,FALSE,172,TRUE,FALSE);

insert into permission values(7101,28,'allerte-allerte',TRUE,TRUE,TRUE,TRUE,'Allerte',15,TRUE,TRUE,FALSE);
--insert into permission values(343,28,'allerte-allerte-folders',TRUE,TRUE,TRUE,TRUE,'Cartelle',30,TRUE,TRUE,FALSE);
--insert into permission values(344,28,'contacts',TRUE,TRUE,TRUE,TRUE,'Contatti',40,TRUE,TRUE,FALSE);											XXXX
insert into permission values(7102,28,'allerte-allerte-cu',TRUE,FALSE,FALSE,FALSE,'Definisci Numero C.U. Pianificati',45,TRUE,TRUE,FALSE);
insert into permission values(7103,28,'allerte-allerte-chiusura',TRUE,FALSE,FALSE,FALSE,'Chiusura Allerta',45,TRUE,TRUE,FALSE);
--insert into permission values(345,28,'allerte-allerte-contacts-opportunities',TRUE,TRUE,TRUE,TRUE,'Opportunit?ontatti',50,TRUE,TRUE,FALSE);
--insert into permission values(346,28,'allerte-allerte-contacts-calls',TRUE,TRUE,TRUE,TRUE,'Attivit?ontatti',60,TRUE,TRUE,FALSE);
--insert into permission values(347,28,'allerte-allerte-contacts-completed-calls',FALSE,FALSE,TRUE,FALSE,'Attivitacompletate',70,TRUE,TRUE,FALSE);
--insert into permission values(348,28,'allerte-allerte-contacts-history',TRUE,TRUE,TRUE,TRUE,'Storia dei Contatti',100,TRUE,TRUE,FALSE);
--insert into permission values(349,28,'allerte-allerte-contacts-messages',TRUE,TRUE,TRUE,TRUE,'Messaggi dei Contatti',80,TRUE,TRUE,FALSE);
--insert into permission values(350,28,'allerte-allerte-contacts-move',TRUE,FALSE,FALSE,FALSE,'Sposta contatti in altri account',90,TRUE,TRUE,FALSE);
--insert into permission values(351,28,'allerte-allerte-opportunities',TRUE,TRUE,TRUE,TRUE,'Opportunita',110,TRUE,TRUE,FALSE);
--insert into permission values(352,28,'allerte-allerte-tickets-tasks',TRUE,TRUE,TRUE,TRUE,'Compiti Ticket',130,TRUE,TRUE,FALSE);
--insert into permission values(353,28,'allerte-allerte-tickets',TRUE,TRUE,TRUE,TRUE,'Ticket',120,TRUE,TRUE,FALSE);
--insert into permission values(354,28,'allerte-allerte-tickets-folders',TRUE,TRUE,TRUE,TRUE,'Cartella Ticket',140,TRUE,TRUE,FALSE);
--insert into permission values(355,28,'allerte-allerte-tickets-documents',TRUE,TRUE,TRUE,TRUE,'Documenti dei Ticket',150,TRUE,TRUE,FALSE);
--insert into permission values(356,28,'allerte-allerte-history',TRUE,TRUE,TRUE,TRUE,'Storia Cliente',180,TRUE,TRUE,FALSE);
insert into permission values(7104,28,'allerte-allerte-documents',TRUE,TRUE,TRUE,TRUE,'Documenti',160,TRUE,TRUE,FALSE);
--insert into permission values(358,28,'allerte-allerte-reports',TRUE,TRUE,FALSE,TRUE,'Esporta Dati Allevamento',170,TRUE,TRUE,FALSE);
--insert into permission values(359,28,'allerte-dashboard',TRUE,FALSE,FALSE,FALSE,'Pannello',190,TRUE,TRUE,FALSE);
--insert into permission values(360,28,'allerte-allerte-revenue',TRUE,TRUE,TRUE,TRUE,'Reddito',200,FALSE,FALSE,FALSE);
--insert into permission values(361,28,'allerte-allerte-tickets-activity-log',TRUE,TRUE,TRUE,TRUE,'Appuntamenti dei Ticket',250,TRUE,TRUE,FALSE);
--insert into permission values(362,28,'allerte-allerte-tickets-maintenance-report',TRUE,TRUE,TRUE,TRUE,'Note di manutenzione dei Ticket',240,TRUE,TRUE,FALSE);
--insert into permission values(363,28,'allerte-assets',TRUE,TRUE,TRUE,TRUE,'Impianti',230,TRUE,TRUE,FALSE);
--insert into permission values(364,28,'allerte-service-contracts',TRUE,TRUE,TRUE,TRUE,'Contratti di Servizio',220,TRUE,TRUE,FALSE);
--insert into permission values(365,28,'allerte-autoguide-inventory',TRUE,TRUE,TRUE,TRUE,'Inventario dei veicoli',210,FALSE,FALSE,FALSE);
--insert into permission values(366,28,'allerte-quotes',TRUE,TRUE,TRUE,TRUE,'Offerte',270,TRUE,TRUE,FALSE);
--insert into permission values(367,28,'allerte-orders',TRUE,TRUE,TRUE,TRUE,'Ordini',280,FALSE,FALSE,FALSE);
--insert into permission values(368,28,'allerte-allerte-contacts-folders',TRUE,TRUE,TRUE,TRUE,'Cartelle',370,TRUE,TRUE,FALSE);
insert into permission values(7105,28,'allerte-allerte-storico',TRUE,FALSE,FALSE,FALSE,'Storico Annuale Allerte',420,TRUE,TRUE,FALSE);
--insert into permission values(370,28,'allerte-allerte-audit',TRUE,TRUE,TRUE,TRUE,'Audit',410,TRUE,TRUE,FALSE);
--insert into permission values(371,28,'allerte-allerte-documentstore',TRUE,TRUE,TRUE,TRUE,'Account Document Store',400,TRUE,TRUE,FALSE);
--insert into permission values(372,28,'allerte-allerte-shareddocuments',TRUE,TRUE,TRUE,TRUE,'Share Accounts Documents',390,TRUE,TRUE,FALSE);
--insert into permission values(373,28,'allerte-allerte-contacts-opps-folders',TRUE,TRUE,TRUE,TRUE,'Contact Opportunity Folders',380,TRUE,TRUE,FALSE);
--insert into permission values(374,28,'allerte-directbill',TRUE,TRUE,TRUE,TRUE,'Direct Bill',360,FALSE,FALSE,FALSE);
--insert into permission values(375,28,'allerte-allerte-contacts-opportunities-quotes',TRUE,TRUE,TRUE,TRUE,'Contact Opportunities Quotes',350,TRUE,TRUE,FALSE);
--insert into permission values(376,28,'allerte-action-plans',TRUE,TRUE,TRUE,TRUE,'Action Plans',340,TRUE,TRUE,FALSE);
--insert into permission values(377,28,'allerte-projects',TRUE,FALSE,FALSE,FALSE,'Progetti',330,TRUE,TRUE,FALSE);
--insert into permission values(378,28,'allerte-allerte-contact-updater',TRUE,FALSE,FALSE,FALSE,'Richiedi aggiornamento informazioni dle contatto',320,TRUE,TRUE,FALSE);
--insert into permission values(379,28,'allerte-allerte-relationships',TRUE,TRUE,TRUE,TRUE,'Relazioni',310,TRUE,TRUE,FALSE);
--insert into permission values(380,28,'allerte-allerte-contacts-imports',TRUE,TRUE,TRUE,TRUE,'Importa Accounts/Contatti',300,TRUE,TRUE,FALSE);
--insert into permission values(381,28,'allerte-products',TRUE,TRUE,TRUE,TRUE,'Prodotti e Servizi',290,FALSE,FALSE,FALSE);
insert into permission values(7100,28,'allerte',TRUE,FALSE,FALSE,FALSE,'Accesso al modulo Allerta',10,TRUE,TRUE,FALSE);
--insert into permission values(383,28,'allerte-allerte',TRUE,TRUE,TRUE,TRUE,'Pannello gestione Allerta',10,TRUE,TRUE,FALSE);

alter table ticket add data_apertura timestamp without time zone;
alter table ticket add data_eventuale_revoca timestamp without time zone;
alter table ticket add data_chiusura timestamp without time zone;
alter table ticket add progressivo_allerta integer;
alter table ticket add tipo_alimento integer;
alter table ticket add origine integer;
alter table ticket add alimento_interessato integer;
alter table ticket add non_conformita_alimento integer;
alter table ticket add lista_commercializzazione integer;

CREATE TABLE lookup_tipo_alimento
(
  code serial NOT NULL,
  description character varying(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT lookup_tipo_alimento_pkey PRIMARY KEY (code)
);

CREATE TABLE lookup_origine
(
  code serial NOT NULL,
  description character varying(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT lookup_origine_pkey PRIMARY KEY (code)
);

CREATE TABLE lookup_alimento_interessato
(
  code serial NOT NULL,
  description character varying(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT lookup_alimento_interessato_pkey PRIMARY KEY (code)
);

CREATE TABLE lookup_non_conformita
(
  code serial NOT NULL,
  description character varying(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT lookup_non_conformita_pkey PRIMARY KEY (code)
);

--DROP TABLE lookup_altre_irregolarita;
CREATE TABLE lookup_lista_commercializzazione
(
  code serial NOT NULL,
  description character varying(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT lookup_lista_commercializzazione_pkey PRIMARY KEY (code)
);



CREATE TABLE allerte_asl_coinvolte
(
  id                serial NOT NULL PRIMARY KEY,
  id_allerta        int    NOT NULL,
  id_asl            int    NOT NULL,
  cu_pianificati    int    DEFAULT -1,
  cu_pianificati_da int    DEFAULT -1,
  cu_eseguiti       int    DEFAULT 0,
  data_chiusura	    TIMESTAMP DEFAULT NULL,
  chiusa_da         int DEFAULT -1,
  note              VARCHAR(64),
  enabled           BOOLEAN DEFAULT TRUE
);

insert into lookup_tipo_alimento values(1,'Animale',FALSE,10,TRUE);
insert into lookup_tipo_alimento values(2,'Non Animale',FALSE,20,TRUE);
insert into lookup_tipo_alimento values(3,'Alimento ad Uso Zootecnico',FALSE,30,TRUE);

insert into lookup_origine values(1,'Sian Asl Regione',FALSE,10,TRUE);
insert into lookup_origine values(2,'Veterinari Asl Regione',FALSE,20,TRUE);
insert into lookup_origine values(3,'Asl Fuori Rregione',FALSE,30,TRUE);
insert into lookup_origine values(4,'Ministero',FALSE,40,TRUE);
insert into lookup_origine values(5,'CE',FALSE,50,TRUE);


insert into lookup_alimento_interessato values(1,'Miele',FALSE,10,TRUE);
insert into lookup_alimento_interessato values(2,'Uova',FALSE,20,TRUE);
insert into lookup_alimento_interessato values(3,'Latte',FALSE,30,TRUE);
insert into lookup_alimento_interessato values(4,'Carne',FALSE,40,TRUE);
insert into lookup_alimento_interessato values(5,'Pesce',FALSE,50,TRUE);
insert into lookup_alimento_interessato values(6,'Bevande',FALSE,60,TRUE);
insert into lookup_alimento_interessato values(7,'Conserve',FALSE,70,TRUE);
insert into lookup_alimento_interessato values(8,'Gelati',FALSE,80,TRUE);
insert into lookup_alimento_interessato values(9,'Frutta/Verdura',FALSE,90,TRUE);
insert into lookup_alimento_interessato values(10,'Mangimi',FALSE,100,TRUE);

insert into lookup_non_conformita values(1,'Additivo Oltre il Limite',FALSE,10,TRUE);
insert into lookup_non_conformita values(2,'Commercializzazione Illegale',FALSE,20,TRUE);
insert into lookup_non_conformita values(3,'Metalli Pesanti',FALSE,30,TRUE);
insert into lookup_non_conformita values(4,'Micotossine',FALSE,40,TRUE);
insert into lookup_non_conformita values(5,'OGM',FALSE,50,TRUE);
insert into lookup_non_conformita values(6,'Residui di Farmaci',FALSE,60,TRUE);
insert into lookup_non_conformita values(7,'Rischi Microbiologici',FALSE,70,TRUE);

insert into lookup_lista_commercializzazione values(1,'Con',FALSE,10,TRUE);
insert into lookup_lista_commercializzazione values(2,'Senza',FALSE,20,TRUE);



