CREATE SEQUENCE public.lookup_tipologia_altro_intervento_chirurgico_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 1000000
  START 1
  CACHE 1;
ALTER TABLE public.lookup_tipologia_altro_intervento_chirurgico_id_seq
  OWNER TO postgres;


CREATE TABLE public.lookup_tipologia_altro_intervento_chirurgico
(
  id integer NOT NULL DEFAULT nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'::regclass),
  descrizione character varying(64),
  is_group boolean,
  enabled boolean,
  level integer,
  CONSTRAINT lookup_tipologia_altro_intervento_chirurgico_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.lookup_tipologia_altro_intervento_chirurgico
  OWNER TO postgres;

CREATE TABLE tipo_intervento_tipologie
(
  tipo_intervento integer NOT NULL,
  tipologia integer NOT NULL,
  CONSTRAINT tipo_intervento_tipologie_pkey PRIMARY KEY (tipo_intervento, tipologia),
  CONSTRAINT fk8b98006f2afdab45 FOREIGN KEY (tipologia)
      REFERENCES public.lookup_tipologia_altro_intervento_chirurgico (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk8b98006f75c6b6de FOREIGN KEY (tipo_intervento)
      REFERENCES public.tipo_intervento (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tipo_intervento_tipologie
  OWNER TO postgres;






INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Chirurgia del tegumentario', true, true, 10);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Sutura cute', false, true, 20);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Trattamento fistole', false, true, 30);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Plastica cutanea', false, true, 40);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Cheiloplastica della plica labiale', false, true, 50);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Asportazione Neoplasia', false, true, 60);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Rimozione forasacco', false, true, 70);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Chirurgia Orecchio', true, true, 80);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Teca', false, true, 90);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Tecalbo', false, true, 100);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Osteotomia Bolla Timpanica', false, true, 110);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Trattamento Otoematoma', false, true, 120);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Rimozione forasacco', false, true, 130);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Conchectomia terapeutica', false, true, 140);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Chirurgia dell''occhio', true, true, 150);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Enucleazione globo oculare', false, true, 160);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Plissettature palpebre', false, true, 170);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Entropion', false, true, 180);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Ectropion', false, true, 190);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Riposizionamento della ghiandola sup. terza palpebra', false, true, 200);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Rimozione terza palpebra', false, true, 210);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Cheratectomia superficiale', false, true, 220);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Cataratta ', false, true, 230);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Neoformazioni palpebre', false, true, 240);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Congiuntivorinostomia', false, true, 250);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Lembo Congiuntivale', false, true, 260);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Proptosi', false, true, 270);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Dermoide', false, true, 280);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Glaucoma (crioterapia)', false, true, 290);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Glaucoma (drenaggio)', false, true, 300);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Trasposizione dotto di Stenone', false, true, 310);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Asportazione ciglia ectopiche', false, true, 320);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Chirurgia  Addome', true, true, 330);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Colonpessi  ', false, true, 340);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Cistopessi', false, true, 350);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Deferentopessi', false, true, 360);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Ernia Perineale', false, true, 370);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Marsupializzazione / Omentalizzazione  Cisti Paraprostatiche', false, true, 380);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Atresia anale ', false, true, 390);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Atresia Anorettale', false, true, 400);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Chirurgia dell''apparato digerente', true, true, 410);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Palatoschisi congenita/acquisita', false, true, 420);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Stafilectomia', false, true, 430);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Epulide', false, true, 440);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Mandibulectomia parziale', false, true, 450);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Esofagotomia', false, true, 460);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Megaesofago da IV arco aortico destro persistente', false, true, 470);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Gastropessi  circumcostale (dilatazione, torsione)', false, true, 480);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Gastrectomia/ gastrotomia', false, true, 490);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Cardioplastica/Piloroplastica', false, true, 500);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Intervento di Billroth', false, true, 510);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Shunt portosistemico', false, true, 520);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Enterotomia', false, true, 530);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Enterectomia/Enteroanastomosi', false, true, 540);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Tiflotectomia', false, true, 550);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Megacolon', false, true, 560);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Splenectomia', false, true, 570);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Torsione splenica', false, true, 580);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Neoplasie spleniche', false, true, 590);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Colecistotomia', false, true, 600);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Colelitiasi', false, true, 610);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Mucocele Biliare', false, true, 620);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Resezione Rettale', false, true, 630);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Riduzione Prolasso Rettale', false, true, 640);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Riduzione Ernia Perineale', false, true, 650);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Ablazione Seni Paranali', false, true, 660);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Chirurgia dell''apparato respiratorio', true, true, 670);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Rimozione forasacco narici', false, true, 680);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Plastica narici per stenosi', false, true, 690);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Stafilectomia', false, true, 700);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Eversione sacculi laringei', false, true, 710);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Paralisi laringea', false, true, 720);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Rinotomia', false, true, 730);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Laringectomia', false, true, 740);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Toracotomia terapeutica', false, true, 750);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Pectus exavatum', false, true, 760);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Ernia diaframmatica traumatica', false, true, 770);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Ernia diaframmatica peritoneopericardica', false, true, 780);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Ernia Iatale', false, true, 790);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'PDA', false, true, 800);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Applicazione drenaggio toracico', false, true, 810);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Chirurgia Apparato Urinario ', true, true, 820);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Nefrotomia', false, true, 830);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Nefrectomia', false, true, 840);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Nefropessi', false, true, 850);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Ectopia uretrale', false, true, 860);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Ureteretomia', false, true, 870);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Cistotomia', false, true, 880);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Cistectomia per neoplasia vescicale', false, true, 890);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Uretrostomia pubica', false, true, 900);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Uretrostomia prepubica', false, true, 910);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Uretrostomia perineale', false, true, 920);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Rimozione calcoli vescicali', false, true, 930);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Chirurgia Apparato Genitale Femminile', true, true, 940);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Mastectomia', false, true, 950);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Riduzione prolasso vaginale ', false, true, 960);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Trattamento fistole retto vaginali', false, true, 970);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Rimozione neoplasie vaginali', false, true, 980);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Chirurgia Apparato Genitale Maschile', true, true, 990);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Sutura lesioni traumatiche pene', false, true, 1000);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Sutura lesioni traumatiche prepuzio', false, true, 1010);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Sutura lesioni traumatiche testicoli', false, true, 1020);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Ipospadia', false, true, 1030);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Fimosi', false, true, 1040);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Parafimosi', false, true, 1050);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Amputazione del pene', false, true, 1060);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Orchiectomia per criptorchidismo inguinale', false, true, 1070);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Orchiectomia per criptorchidismo addominale', false, true, 1080);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Prostatectomia', false, true, 1090);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Chirurgia  Sistema Endocrino', true, true, 1100);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Adrenalectomia', false, true, 1110);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Pancreasectomia parziale', false, true, 1120);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Tiroidectomia', false, true, 1130);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Chirurgia Sistema Nervoso', true, true, 1140);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Rachicentesi', false, true, 1150);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Stabilizzazione spinale cervicale', false, true, 1160);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Ventral slot', false, true, 1170);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Stabilizzazione toraco-lombare', false, true, 1180);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Laminectomia dorsale', false, true, 1190);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Emilaminectomia dorsale', false, true, 1200);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Fratture/ lussazioni colonna vertebrale lombo-sacrale', false, true, 1210);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Chirurgia Ortopedica', true, true, 1220);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Tenoraffia', false, true, 1230);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Artrotomia terapeutica', false, true, 1240);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Osteosintesi Mandibolare/Mascellare', false, true, 1250);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Osteosintesi  con Banda di tensione', false, true, 1260);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Osteosintesi centro midollare', false, true, 1270);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Osteosintesi “A/O”', false, true, 1280);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Osteosintesi con fissatori esterni Monoplanari  ', false, true, 1290);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Osteosintesi con fissatori esterni biplanari ', false, true, 1300);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Osteosintesi con fissatori esterni trapassante  ', false, true, 1310);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Ostectomia testa del femore', false, true, 1320);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Applicazione apparato Ilizarov', false, true, 1330);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Riduzione Cruenta Lussazione ', false, true, 1340);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Riparazione rottura legamento crociato craniale extrarticolare  ', false, true, 1350);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Riparazione rottura legamento crociato craniale intrarticolare', false, true, 1360);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Ostectomia/Osteotomia correttiva', false, true, 1370);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Amputazione arto', false, true, 1380);
INSERT INTO lookup_tipologia_altro_intervento_chirurgico(             id, descrizione, is_group, enabled, level)  VALUES (nextval('lookup_tipologia_altro_intervento_chirurgico_id_seq'), 'Artrodesi “A/O”', false, true, 1390);


