-- creo tabella lookup flag

CREATE TABLE lookup_master_list_flag
(
  code integer NOT NULL,
  description character varying(50) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true
)
WITH (
  OIDS=FALSE
);
ALTER TABLE lookup_master_list_flag
  OWNER TO postgres;
  
  --popolo tabella
  insert into lookup_master_list_flag(code, description) values (1, 'Linea Fissa');
insert into lookup_master_list_flag(code, description) values (2, 'Linea Mobile');
insert into lookup_master_list_flag(code, description) values (3, 'Linea Apicoltura');
insert into lookup_master_list_flag(code, description) values (4, 'Linea Registrabile');
insert into lookup_master_list_flag(code, description) values (5, 'Linea Riconosciuto');
insert into lookup_master_list_flag(code, description) values (6, 'Linea Sintesis');
insert into lookup_master_list_flag(code, description) values (7, 'Linea Propagazione BDU');
insert into lookup_master_list_flag(code, description) values (8, 'Linea Propagazione VAM');

-- creo tabella flag
CREATE TABLE master_list_flag_linee_attivita (id serial PRIMARY KEY,codice_univoco TEXT, mobile BOOLEAN, fisso BOOLEAN, apicoltura BOOLEAN, registrabili BOOLEAN, riconoscibili BOOLEAN, sintesis BOOLEAN, bdu BOOLEAN, vam BOOLEAN);

-- popolo tabella
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('ALT-AUT-AUT',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('ALT-DIS-DIS',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('ALT-FORM-ALI',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('ALT-FORM-BANA',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('ALT-FORM-BENM',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('ALT-FORM-IAA',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('ALT-FORM-MIC',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('ALT-FORM-OPF',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('ALT-FORM-OPS',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('ALT-FORM-TRAS',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('ALT-HAC-HAC',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('ALT-LACT-021',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('ALT-PCT-020',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('FITO-E500A-E510A',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('FITO-E500A-E520A',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('IUV-ADCA-ADCA',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('IUV-CAN-CAN',false,true,false,true,false,false,true,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('IUV-CIM-CIM',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('IUV-CNSIAA-AAAC',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('IUV-CNSIAA-AAAS',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('IUV-CNSIAA-EAAC',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('IUV-CNSIAA-EAAS',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('IUV-CNSIAA-TAAC',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('IUV-CNSIAA-TAAS',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('IUV-CODAC-VED',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('IUV-CODAC-VEDCG',false,true,false,true,false,false,true,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('IUV-COIAC-VEI',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('IUV-COIAC-VEICG',false,true,false,true,false,false,true,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('IUV-CSIAA-AAAC',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('IUV-CSIAA-AAAS',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('IUV-CSIAA-EAAC',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('IUV-CSIAA-EAAS',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('IUV-CSIAA-TAAC',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('IUV-CSIAA-TAAS',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('IUV-FOAS-FORN',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('IUV-MOFI-FIER',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('IUV-TOE-TOE',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('IUV-TOE-TOEIT',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('FVET-ADVET-SAF',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('FVET-ADVET-SCF',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('FVET-ANVET-SAF',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('FVET-ANVET-SCF',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('FVET-COMM-SVDET',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('FVET-COMM-VDET',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('FVET-MVET-MVEF',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('FVET-PARVET-PARVED',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('FVET-STVET-STVEF',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('SPE-A-ALNAU-ALSC',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('SPE-A-ALNAU-ANSC',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('VET-AMBV-PR',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('VET-AMBV-PU',false,true,false,true,false,false,true,true);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('VET-CARV-TR',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('VET-CARV-PS',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('VET-CLIV-PR',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('VET-CLIV-PU',false,true,false,true,false,false,true,true);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('VET-LABV-LABV',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('VET-OSPV-PR',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('VET-OSPV-PU',false,true,false,true,false,false,true,true);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('VET-STUV-ACCA',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('VET-STUV-SACA',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('CG-EST-B-0121',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('CG-EST-B-0124',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('CG-EST-B-0125',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('CG-EST-B-0126',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('CG-EST-E-0121',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('CG-EST-E-0124',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('CG-EST-E-0125',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('CG-EST-E-0126',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('CG-EST-M-0121',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('CG-EST-M-0124',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('CG-EST-M-0125',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('CG-EST-M-0126',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('CG-EST-S-0121',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('CG-EST-S-0122',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('CG-EST-S-0124',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('CG-EST-S-0125',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('CG-EST-S-0126',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('CG-NAZ-B',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('CG-NAZ-D-0122',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('CG-NAZ-D-0126',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('CG-NAZ-E-004',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('CG-NAZ-E-005',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('CG-NAZ-E-006',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('CG-NAZ-E-007',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('CG-NAZ-E-008',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('CG-NAZ-E-009',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('CG-NAZ-P-0121',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('CG-NAZ-P-0122',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('CG-NAZ-R-003',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('CG-NAZ-R-007',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('CG-NAZ-V-0126',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('CG-NAZ-V-0126',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('CG-NAZ-V-0127',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('CG-NAZ-V-0127',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.000-ALCAT-ALTR',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.000-ALCAT-PRIV',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.000-ALPET-ALTR',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.000-ALPET-PRIV',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-1-COLL',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-1-INTP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-1-STORP_T',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-13-OALKHP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-13-OBIODP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-13-OBRGAP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-13-OHPHBP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-13-OHPHTP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-13-OTHER',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-13-PROCP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-14-OALKHP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-14-OBIODP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-14-OBRGAP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-14-OHPHBP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-14-OHPHTP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-14-OTHER',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-14-PROCP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-15-OALKHP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-15-OALKHP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-15-OBIODP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-15-OBIODP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-15-OBRGAP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-15-OBRGAP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-15-OHPHBP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-15-OHPHBP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-15-OHPHTP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-15-OHPHTP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-15-OTHER',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-15-OTHER',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-15-PROCP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-15-PROCP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-17-OLCP',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-18-OLCP',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-19-BIOGP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-2-COLL',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-2-INTP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-2-STORP_T',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-20-BIOGP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-21-BIOGP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-23-COMP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-24-COMP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-27-PETPP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-27-PETPR',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-28-API',false,true,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-28-BHHP',false,true,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-28-BLPT',false,true,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-28-GATRP',false,true,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-28-MIMC',false,true,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-28-OTHER_T',false,true,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-28-SERE',false,true,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-28-TAN',false,true,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-28-WHBF',false,true,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-29-API',false,true,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-29-BHHP',false,true,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-29-BLPT',false,true,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-29-GATRP',false,true,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-29-MIMC',false,true,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-29-OTHER_T',false,true,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-29-SERE',false,true,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-29-TAN',false,true,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-29-WHBF',false,true,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-3-COLL',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-3-INTP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-3-STORP_T',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-30-API',false,true,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-30-BHHP',false,true,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-30-BLPT',false,true,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-30-GATRP',false,true,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-30-MIMC',false,true,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-30-OTHER_T',false,true,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-30-SERE',false,true,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-30-TAN',false,true,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-30-UFERT',false,true,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-30-WHBF',false,true,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-31-OTHER_U',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-31-UDER',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-31-UDOG',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-31-UFUR',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-31-UINSE',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-31-UNEC',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-31-URBP',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-31-UWILD',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-31-UZOO',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-32-OTHER_U',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-32-UDER',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-32-UDOG',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-32-UFUR',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-32-UNEC',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-32-UNISE',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-32-URBP',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-32-UWILD',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-32-UZOO',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-33-OTHER_U',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-33-UDER',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-33-UDOG',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-33-UFUR',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-33-UNEC',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-33-UNISE',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-33-URBP',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-33-UWILD',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-33-UZOO',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-35-COLC',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-36-COLC',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-37-INTERM',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-37-PHAR',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-37-ROTHER',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-37-TRADER',true,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-37-TRANS',true,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-37-UCOSM',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-38-INTERM',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-38-PHAR',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-38-ROTHER',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-38-TRADER',true,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-38-TRANS',true,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-38-UCOSM',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-39-INTERM',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-39-PHAR',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-39-ROTHER',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-39-TRADER',true,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-39-TRANS',true,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-39-UCOSM',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-4-STORP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-4-STORP_T',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-40-UFERT',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-41-UFERT',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-42-UFERT',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-5-STORP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-5-STORP_T',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-6-STORP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-6-STORP_T',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-7-COIP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-7-COIPB',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-7-INCP',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-7-INCPB',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('1069-R-7-OCOMBTB',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MG-DG-M04',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MG-DG-M05',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MG-DG-M06',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MG-DG-M07',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MG-DG-M08',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MG-DG-M09',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MG-DG-M10',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MG-DG-M11',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MG-DG-M12',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MG-DG-M13',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MG-DG-M15',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MG-DG-M16',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MG-DG-M17',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MG-DG-M18',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MG-DG-M19',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MG-DG-M19',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MG-DG-M20',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MG-DG-M39',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MG-DG-M40',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MG-OPRA-M01',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MG-OPRZ-M02',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MG-OSMM-M29',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MG-OSMM-M31',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MG-OSMM-M32',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MG-OSMM-M33',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MG-OSMM-M34',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MR-PDD7-M35',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MR-PDD7-M36',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MR-PDD7-M38',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MR-DPNC-M21',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MR-DR-M22',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MR-DR-M23',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MR-DR-M24',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MR-DR-M25',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MR-DR-M26',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MR-DR-M27',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MR-DR-M28',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MR-DR3-MIAG',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MR-DR3-PROBD',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MR-DR3-TRASOL',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MR-DR3-TROAC',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MR-OSMM-M30',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MR-OSMM-M31',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.090-TCT-T',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.000-0130-AL-N',true,false,true,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.000-0130-AL-N',true,false,true,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.000-0130-AL-NLS',true,false,true,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.000-0130-AL-NLS',true,false,true,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.000-0130-AL-S',false,true,true,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.000-0130-AL-S',false,true,true,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.000-0130-AL-SLS',false,true,true,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.000-0130-AL-SLS',false,true,true,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.000-0131-AL-LU',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.000-AL-PEL',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.000-ALES-016',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.000-ALLOM-019',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.000-COBE-RIC',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.000-MS.000.100-017',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.000-MS.000.200-852IT1A101',true,false,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.000-MS.000.200-852IT1A201',true,false,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.000-MS.000.400-022',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.000-MS.000.400-852IT1A301',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.000-MS.000.400-852IT1A302',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.000-MS.000.500-ALT',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.000-MS.000.500-ALT',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.000-MS.000.500-FUN',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.000-MS.000.500-FUN',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.000-MS.000.500-TAR',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.000-MS.000.500-TAR',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.000-MS.000.600-018',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.000-MS.000.600-018',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.000-MS.000.700-CF',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.000-MS.000.700-CF',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.000-MS.000.700-SF',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.000-MS.000.700-SF',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-44-024',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.100-852IT2A001',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.100-852IT2A001',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.100-852IT2A001',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.100-852IT2A001',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.100-852IT2A002',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.100-852IT2A002',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.100-852IT2A002',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.100-852IT2A002',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.100-852IT2A003',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.100-852IT2A003',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.100-852IT2A003',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.100-852IT2A003',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.100-852IT2A003',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.100-852IT2A003',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.100-852IT2A003',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.100-852IT2A003',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.100-852IT2A003',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.100-852IT2A003',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.100-852IT2A003-B',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.100-852IT2A004',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.100-852IT2A004',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.100-852IT2A004',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.100-852IT2A004',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.200-852IT2A101',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.200-852IT2A101',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.300-852IT2A201',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.300-852IT2A201',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.300-852IT2A201',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.300-852IT2A201',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.300-852IT2A202',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.300-852IT2A202',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.400-852IT2A301',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.400-852IT2A301',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.400-852IT2A302',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.400-852IT2A302',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.400-852IT2A303',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.400-852IT2A303',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.400-852IT2A304',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.400-852IT2A304',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.500-852IT2A401',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.500-852IT2A401',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.500-852IT2A402',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.500-852IT2A402',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.500-852IT2A403',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.500-852IT2A403',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.500-852IT2A404',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.500-852IT2A404',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.600-852IT2A501',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.700-852IT2A601',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.010-MS.010.800-852IT2A701',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.020-43-023',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.020-MS.020.100-852IT3A001',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.020-MS.020.100-852IT3A001',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.020-MS.020.200-852IT3A101',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.020-MS.020.200-852IT3A101',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.020-MS.020.200-852IT3A102',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.020-MS.020.200-852IT3A102',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.020-MS.020.300-852IT3A201',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.020-MS.020.300-852IT3A201',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.020-MS.020.400-852IT3A301',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.020-MS.020.400-852IT3A301',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.020-MS.020.500-852IT3A401',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.020-MS.020.500-852IT3A401',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.030-MS.030.100-852IT4A001',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.030-MS.030.100-852IT4A002',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.030-MS.030.100-852IT4A003',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.030-MS.030.200-852IT4A101',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.030-MS.030.200-852IT4A102',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.040-MS.040.100-852IT5A001',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.040-MS.040.200-852IT5A101',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.040-MS.040.300-852IT5A201',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.040-MS.040.300-852IT5A201',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.040-MS.040.400-852IT5A301',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.040-MS.040.400-852IT5A301',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.040-MS.040.500-852IT5A401',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.040-MS.040.500-852IT5A401',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.040-MS.040.600-852IT5A501',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.040-MS.040.600-852IT5A501',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-HORE-009',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.100-852IT6A001',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.100-852IT6A001',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.100-852IT6A001',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.100-852IT6A001',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.100-852IT6A002',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-001',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-001',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-001',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-001',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-003',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-003',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-003',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-003',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-004',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-004',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-004',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-004',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-005',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-005',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-005',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-005',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-006',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-006',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-006',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-006',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-006',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-006',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-006',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-006',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-008',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-008',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-008',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-008',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-008',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-008',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-008',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-008',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-010',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-011',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-012',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-012',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-852IT6A101',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-852IT6A101',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-852IT6A101',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-852IT6A101',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-852IT6A101',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-852IT6A101',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-852IT6A101',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-852IT6A101',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-852IT6A102',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-852IT6A102',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-852IT6A102',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-852IT6A102',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-852IT6A102',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-852IT6A102',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-852IT6A102',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-852IT6A102',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-852IT6A103',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.050-MS.050.200-852IT6A103',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.100-852IT7A001',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.100-852IT7A002',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.100-852IT7A003',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.100-852IT7A003',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.200-852IT7A101',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.200-852IT7A101',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.200-852IT7A101',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.200-852IT7A101',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.200-852IT7A101',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.200-852IT7A101',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.200-852IT7A101',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.200-852IT7A101',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.200-852IT7A101',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.200-852IT7A101',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.200-852IT7A101',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.200-852IT7A101',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.200-852IT7A101',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.200-852IT7A101',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.200-852IT7A101',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.200-852IT7A101',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.200-852IT7A101',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.200-852IT7A101',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.200-852IT7A101',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.200-852IT7A101',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.200-852IT7A101',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.200-852IT7A101',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.200-852IT7A101',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.200-852IT7A101',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.200-852IT7A101-A',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.200-852IT7A101-A',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.200-852IT7A101-B',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.200-852IT7A102',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.200-852IT7A102',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.200-852IT7A103',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.200-852IT7A103',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.200-852IT7A104',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.200-852IT7A105',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.200-852IT7A105',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.200-852IT7A202-A',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.300-852IT7A201',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.300-852IT7A201',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.300-852IT7A201',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.300-852IT7A201',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.300-852IT7A201',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.300-852IT7A201',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.300-852IT7A201',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.300-852IT7A201',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.300-852IT7A201',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.300-852IT7A201',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.300-852IT7A201',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.300-852IT7A201',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.300-852IT7A202',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.300-852IT7A202',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.300-852IT7A202',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.400-852IT7A301',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.400-852IT7A302',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.060-MS.060.400-852IT7A303',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.070-MS.070.100-852IT8A001',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.070-MS.070.100-852IT8A002',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.070-MS.070.200-852IT8A101',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.080-MS.080.100-002',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.080-MS.080.100-010',false,true,false,false,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.090-MS.090.100-852ITAA002',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.090-MS.090.100-852ITAA003',true,false,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.A-BIB-015',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.A-MS.A10-CN-G',false,true,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.A-MS.A10-PC-G',false,true,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.A-MS.A10-PP-G',false,true,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.A-MS.A10-ST-G',false,true,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.A-MS.A11-CN-SG',false,true,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.A-MS.A11-PC-SG',false,true,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.A-MS.A11-PP-SG',false,true,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.A-MS.A11-ST-SG',false,true,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.A-MS.A12-CN-S',false,true,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.A-MS.A12-PC-S',false,true,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.A-MS.A12-PP-S',false,true,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.A-MS.A12-ST-S',false,true,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.A-MS.A30.100-852ITBA001',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.A-MS.A30.100-852ITBA002',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.A-MS.A30.200-011',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.A-MS.A30.200-013',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.A-MS.A30.300-011',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.A-MS.A30.300-013',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.A-MS.A30.400-011',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.A-MS.A30.400-013',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.A-MS.A30.500-852ITBA',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.A-MS.A30.500-852ITBA',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.A-MS.A30.500-852ITBA',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.A-MS.A30.500-852ITBA',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.A-MS.A30.500-852ITBA007',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.A-MS.A40.100-014',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.A-MS.A40.100-852ITEA001',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.A-MS.A40.200-014',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.A-MS.A40.200-852ITEA101',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.A-MS.A40.300-014',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.A-MS.A40.300-852ITEA201',false,false,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.M-MCA-A07P1',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.M-MCA-A07P2',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.M-MCA-A07P3',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.M-MCA-A07PD',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.M-MCA-A07PE',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.M-MCA-A07PF',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.M-MCA-A07PG',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.M-MCA-A07PK',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.M-MCA-A07PL',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.M-MCA-A07PP',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.M-MCA-A07PR-1',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.M-MCA-A07PR-2',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.M-MCA-A07PX',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.M-MCA-A07PY',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.M-MCA-A07PZ-1',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.M-MCA-A07PZ-2',false,true,false,true,false,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B00-MS.B00.100',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B00-MS.B00.200',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B00-MS.B00.300',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B10-MS.B10.100',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B10-MS.B10.100',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B10-MS.B10.100',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B10-MS.B10.100',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B10-MS.B10.100',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B10-MS.B10.100',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B10-MS.B10.100',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B10-MS.B10.100',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B10-MS.B10.100',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B10-MS.B10.100',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B10-MS.B10.100',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B10-MS.B10.100',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B10-MS.B10.100',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B10-MS.B10.100',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B10-MS.B10.200',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B10-MS.B10.200',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B20-MS.B20.100',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B20-MS.B20.200',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B30-MS.B30.100',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B30-MS.B30.100',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B30-MS.B30.100',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B30-MS.B30.100',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B30-MS.B30.100',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B30-MS.B30.200',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B30-MS.B30.200',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B40-MS.B40.100',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B40-MS.B40.200',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B50-MS.B50.100',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B50-MS.B50.200',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B50-MS.B50.300',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B60-MS.B60.100',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B60-MS.B60.100',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B70-MS.B70.100',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B70-MS.B70.200',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B80-MS.B80.100',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B80-MS.B80.100',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B80-MS.B80.100',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B80-MS.B80.200',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B80-MS.B80.300',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B80-MS.B80.400',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B80-MS.B80.500',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B80-MS.B80.600',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B80-MS.B80.700',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B80-MS.B80.800',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B90-MS.B90.100',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B90-MS.B90.200',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B90-MS.B90.300',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B90-MS.B90.400',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B90-MS.B90.400',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B90-MS.B90.400',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B90-MS.B90.400',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B90-MS.B90.400',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B90-MS.B90.400',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B90-MS.B90.400',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B90-MS.B90.400',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.B90-MS.B90.500',false,true,false,false,true,false,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.BA0-MS.BA0.100',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.BA0-MS.BA0.200',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.BA0-MS.BA0.300',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.BB0-MS.BB0.200',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.BC0-MS.BC0.100',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.BC0-MS.BC0.200',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.BD0-MS.BD0.100',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.BE0-MS.BE0.100',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.BE0-MS.BE0.200',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.BF0-MS.BF0.100',false,false,false,false,true,true,false,false);
insert into master_list_flag_linee_attivita (codice_univoco, mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam) values('MS.B-MS.BF0-MS.BF0.200',false,false,false,false,true,true,false,false);

-- Cancello i duplicati
DELETE
FROM
    master_list_flag_linee_attivita a
        USING master_list_flag_linee_attivita b
WHERE
    a.id < b.id
    AND a.codice_univoco = b.codice_univoco;
    
    
-- Creo dbi per recuperare i flag

    -- Function: public.get_flag_linea(text, text)

-- DROP FUNCTION public.get_flag_linea(text, text);

CREATE OR REPLACE FUNCTION public.get_flag_linea(
    _codiceunivoco text,
    _flag text)
  RETURNS boolean AS
$BODY$
DECLARE
	esito boolean;
	flagMobile boolean;
	flagFisso boolean;
	flagApicoltura boolean;
	flagRegistrabili boolean;
	flagRiconoscibili boolean;
	flagSintesis boolean;
	flagBdu boolean;
	flagVam boolean;
BEGIN

esito:= false;

select mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam 
into flagMobile, flagFisso, flagApicoltura, flagRegistrabili, flagRiconoscibili, flagSintesis, flagBdu, flagVam
from master_list_flag_linee_attivita where codice_univoco = _codiceunivoco limit 1;


IF _flag = 'mobile' THEN
esito:= flagMobile;
END IF;

IF _flag = 'fisso' THEN
esito:= flagFisso;
END IF;

IF _flag = 'apicoltura' THEN
esito:= flagApicoltura;
END IF;

IF _flag = 'registrabili' THEN
esito:= flagRegistrabili;
END IF;

IF _flag = 'sintesis' THEN
esito:= flagSintesis;
END IF;

IF _flag = 'riconoscibili' THEN
esito:= flagRiconoscibili;
END IF;

IF _flag = 'bdu' THEN
esito:= flagBdu;
END IF;

IF _flag = 'vam' THEN
esito:= flagVam;
END IF;


 RETURN esito;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.get_flag_linea(text, text)
  OWNER TO postgres;

    
CREATE OR REPLACE FUNCTION public.get_flag_linea(_codiceunivoco text, _flag integer)
  RETURNS boolean AS
$BODY$
DECLARE
	esito boolean;
	flagMobile boolean;
	flagFisso boolean;
	flagApicoltura boolean;
	flagRegistrabili boolean;
	flagRiconoscibili boolean;
	flagSintesis boolean;
	flagBdu boolean;
	flagVam boolean;
BEGIN

esito:= false;

select mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam 
into flagMobile, flagFisso, flagApicoltura, flagRegistrabili, flagRiconoscibili, flagSintesis, flagBdu, flagVam
from master_list_flag_linee_attivita where codice_univoco = _codiceunivoco limit 1;


IF _flag = 2 THEN
esito:= flagMobile;
END IF;

IF _flag = 1 THEN
esito:= flagFisso;
END IF;

IF _flag = 3 THEN
esito:= flagApicoltura;
END IF;

IF _flag = 4 THEN
esito:= flagRegistrabili;
END IF;

IF _flag = 6 THEN
esito:= flagSintesis;
END IF;

IF _flag = 5 THEN
esito:= flagRiconoscibili;
END IF;

IF _flag = 7 THEN
esito:= flagBdu;
END IF;

IF _flag = 8 THEN
esito:= flagVam;
END IF;


 RETURN esito;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.get_flag_linea(text, text)
  OWNER TO postgres;

  
  -- da id linea
  
  
CREATE OR REPLACE FUNCTION public.get_flag_linea(_idlinea integer, _flag integer)
  RETURNS boolean AS
$BODY$
DECLARE
	_codiceunivoco text;
	esito boolean;
	flagMobile boolean;
	flagFisso boolean;
	flagApicoltura boolean;
	flagRegistrabili boolean;
	flagRiconoscibili boolean;
	flagSintesis boolean;
	flagBdu boolean;
	flagVam boolean;
BEGIN

esito:= false;

select codice_attivita into _codiceunivoco from ml8_linee_attivita_nuove_materializzata where id_nuova_linea_attivita = _idlinea;

select get_flag_linea into esito from public.get_flag_linea(_codiceunivoco, _flag);

 RETURN esito;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.get_flag_linea(integer, integer)
  OWNER TO postgres;
    
    
    -- Aggiiornamento sistema
    
    -- Sintesis
    
    -- Function: public.mapping_linea_attivita_flag(integer, text, text)

-- DROP FUNCTION public.mapping_linea_attivita_flag(integer, text, text);

CREATE OR REPLACE FUNCTION public.mapping_linea_attivita_flag(
    idflag integer,
    attivita text,
    aggregazione text)
  RETURNS integer AS
$BODY$
DECLARE
inaggregazione text;
inattivita text;
id_linea_attivita integer;
BEGIN

inaggregazione = regexp_replace(aggregazione, '[^a-zA-Z0-9]', '', 'g');
inattivita = regexp_replace(attivita, '[^a-zA-Z0-9]', '', 'g');
 raise info 'aggregazione: % ', inaggregazione;
 raise info 'attivita: % ', inattivita;
id_linea_attivita := (select l.id from master_list_aggregazione a
join master_list_linea_attivita l on l.id_aggregazione = a.id
where get_flag_linea(l.codice_univoco, idflag) = true and regexp_replace(l.linea_attivita, '[^a-zA-Z0-9]', '', 'g') ilike inattivita and regexp_replace(a.aggregazione, '[^a-zA-Z0-9]', '', 'g') ilike inaggregazione);
 raise info 'id master list mappato al primo giro (mapping con master list): % ', id_linea_attivita;
 
-- Autoriconoscimento: Cerco il valore di linea master list mappato più spesso per questa combinazione nelle richieste già validate.
--if (id_linea_attivita is null) THEN
--id_linea_attivita := (select s.opu_id_linea_produttiva_master_list from sintesis_stabilimenti_import s where regexp_replace(s.attivita, '[^a-zA-Z0-9]', '', 'g') ilike regexp_replace(inattivita, '[^a-zA-Z0-9]', '', 'g') and regexp_replace(s.descrizione_sezione, '[^a-zA-Z0-9]', '', 'g') ilike  regexp_replace(inaggregazione, '[^a-zA-Z0-9]', '', 'g') and s.opu_id_linea_produttiva_master_list>40000 and s.stato_import = 0 group by s.opu_id_linea_produttiva_master_list having count(s.opu_id_linea_produttiva_master_list)>10  order by count(s.opu_id_linea_produttiva_master_list) desc limit 1);
--raise info 'id master list mappato al secondo giro (mapping con richieste): % ', id_linea_attivita;
--END IF;

return id_linea_attivita;


 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.mapping_linea_attivita_flag(integer, text, text)
  OWNER TO postgres;

-- propagazione bdu
  
  -- View: public.opu_operatori_denormalizzati_view_bdu

-- DROP VIEW public.opu_operatori_denormalizzati_view_bdu;

CREATE OR REPLACE VIEW public.opu_operatori_denormalizzati_view_bdu AS 
 SELECT opu_operatori_denormalizzati_view.flag_dia,
    opu_operatori_denormalizzati_view.id_opu_operatore,
    opu_operatori_denormalizzati_view.ragione_sociale,
    opu_operatori_denormalizzati_view.partita_iva,
    opu_operatori_denormalizzati_view.codice_fiscale_impresa,
    opu_operatori_denormalizzati_view.indirizzo_sede_legale,
    opu_operatori_denormalizzati_view.comune_sede_legale,
    opu_operatori_denormalizzati_view.istat_legale,
    opu_operatori_denormalizzati_view.cap_sede_legale,
    opu_operatori_denormalizzati_view.prov_sede_legale,
    opu_operatori_denormalizzati_view.note,
    opu_operatori_denormalizzati_view.entered,
    opu_operatori_denormalizzati_view.modified,
    opu_operatori_denormalizzati_view.enteredby,
    opu_operatori_denormalizzati_view.modifiedby,
    opu_operatori_denormalizzati_view.domicilio_digitale,
    opu_operatori_denormalizzati_view.flag_ric_ce,
    opu_operatori_denormalizzati_view.num_ric_ce,
    opu_operatori_denormalizzati_view.comune,
    opu_operatori_denormalizzati_view.id_asl,
    opu_operatori_denormalizzati_view.id_stabilimento,
    opu_operatori_denormalizzati_view.esito_import,
    opu_operatori_denormalizzati_view.data_import,
    opu_operatori_denormalizzati_view.cun,
    opu_operatori_denormalizzati_view.id_sinvsa,
    opu_operatori_denormalizzati_view.descrizione_errore,
    opu_operatori_denormalizzati_view.comune_stab,
    opu_operatori_denormalizzati_view.istat_operativo,
    opu_operatori_denormalizzati_view.indirizzo_stab,
    opu_operatori_denormalizzati_view.cap_stab,
    opu_operatori_denormalizzati_view.prov_stab,
    opu_operatori_denormalizzati_view.data_fine_dia,
    opu_operatori_denormalizzati_view.categoria_rischio,
    opu_operatori_denormalizzati_view.cf_rapp_sede_legale,
    opu_operatori_denormalizzati_view.nome_rapp_sede_legale,
    opu_operatori_denormalizzati_view.cognome_rapp_sede_legale,
    opu_operatori_denormalizzati_view.codice_registrazione,
    opu_operatori_denormalizzati_view.id_norma,
    opu_operatori_denormalizzati_view.cf_correntista,
    opu_operatori_denormalizzati_view.codice_attivita,
    opu_operatori_denormalizzati_view.primario,
    opu_operatori_denormalizzati_view.attivita,
    opu_operatori_denormalizzati_view.data_inizio,
    opu_operatori_denormalizzati_view.data_fine,
    opu_operatori_denormalizzati_view.numero_registrazione,
    opu_operatori_denormalizzati_view.indirizzo_rapp_sede_legale,
    opu_operatori_denormalizzati_view.stato,
    opu_operatori_denormalizzati_view.solo_attivita,
    opu_operatori_denormalizzati_view.data_inizio_attivita,
    opu_operatori_denormalizzati_view.data_fine_attivita,
    opu_operatori_denormalizzati_view.data_prossimo_controllo,
    opu_operatori_denormalizzati_view.id_stato,
    opu_operatori_denormalizzati_view.path_attivita_completo,
    opu_operatori_denormalizzati_view.id_indirizzo,
    opu_operatori_denormalizzati_view.linee_pregresse,
    opu_operatori_denormalizzati_view.flag_nuova_gestione,
    opu_operatori_denormalizzati_view.tipo_impresa,
    opu_operatori_denormalizzati_view.tipo_societa,
    opu_operatori_denormalizzati_view.codice_ufficiale_esistente,
    opu_operatori_denormalizzati_view.id_asl_stab,
    opu_operatori_denormalizzati_view.id_linea_attivita,
    opu_operatori_denormalizzati_view.data_mod_attivita,
    opu_operatori_denormalizzati_view.stab_entered,
    opu_operatori_denormalizzati_view.linea_numero_registrazione,
    opu_operatori_denormalizzati_view.linea_stato,
    opu_operatori_denormalizzati_view.linea_stato_text,
    opu_operatori_denormalizzati_view.linea_codice_ufficiale_esistente,
    opu_operatori_denormalizzati_view.stab_codice_ufficiale_esistente,
    opu_operatori_denormalizzati_view.id_indirizzo_operatore,
    opu_operatori_denormalizzati_view.import_opu,
    opu_operatori_denormalizzati_view.id_linea_attivita_stab,
    opu_operatori_denormalizzati_view.note_operatore,
    opu_operatori_denormalizzati_view.note_stabilimento,
    opu_operatori_denormalizzati_view.linea_codice_nazionale,
    opu_operatori_denormalizzati_view.flag_pnaa,
    opu_operatori_denormalizzati_view.stab_inserito_da,
    opu_operatori_denormalizzati_view.stab_id_attivita,
    opu_operatori_denormalizzati_view.stab_descrizione_attivita,
    opu_operatori_denormalizzati_view.stab_id_carattere,
    opu_operatori_denormalizzati_view.stab_descrizione_carattere,
    opu_operatori_denormalizzati_view.impresa_id_tipo_impresa,
    opu_operatori_denormalizzati_view.stab_asl,
    opu_operatori_denormalizzati_view.flag_clean,
    opu_operatori_denormalizzati_view.data_generazione_numero,
    opu_operatori_denormalizzati_view.lat_stab,
    opu_operatori_denormalizzati_view.long_stab,
    opu_operatori_denormalizzati_view.linea_entered,
    opu_operatori_denormalizzati_view.linea_modified,
    opu_operatori_denormalizzati_view.macroarea,
    opu_operatori_denormalizzati_view.aggregazione,
    opu_operatori_denormalizzati_view.attivita_xml,
    opu_operatori_denormalizzati_view.codiceistatasl_old,
    opu_operatori_denormalizzati_view.sigla_prov_operativa,
    opu_operatori_denormalizzati_view.sigla_prov_legale,
    opu_operatori_denormalizzati_view.sigla_prov_soggfisico,
    opu_operatori_denormalizzati_view.comune_residenza,
    opu_operatori_denormalizzati_view.data_nascita_rapp_sede_legale,
    opu_operatori_denormalizzati_view.impresa_id_tipo_societa,
    opu_operatori_denormalizzati_view.comune_nascita_rapp_sede_legale,
    opu_operatori_denormalizzati_view.sesso,
    opu_operatori_denormalizzati_view.civico,
    opu_operatori_denormalizzati_view.toponimo_residenza,
    opu_operatori_denormalizzati_view.id_toponimo_residenza,
    opu_operatori_denormalizzati_view.civico_sede_legale,
    opu_operatori_denormalizzati_view.tiponimo_sede_legale,
    opu_operatori_denormalizzati_view.toponimo_sede_legale,
    opu_operatori_denormalizzati_view.civico_sede_stab,
    opu_operatori_denormalizzati_view.tiponimo_sede_stab,
    opu_operatori_denormalizzati_view.toponimo_sede_stab,
    opu_operatori_denormalizzati_view.via_rapp_sede_legale,
    opu_operatori_denormalizzati_view.via_sede_legale,
    opu_operatori_denormalizzati_view.id_comune_richiesta,
    opu_operatori_denormalizzati_view.comune_richiesta,
    opu_operatori_denormalizzati_view.via_stabilimento_calcolata,
    opu_operatori_denormalizzati_view.civico_stabilimento_calcolato,
    opu_operatori_denormalizzati_view.cap_residenza,
    opu_operatori_denormalizzati_view.nazione_residenza,
    opu_operatori_denormalizzati_view.nazione_sede_legale,
    opu_operatori_denormalizzati_view.nazione_stab,
    opu_operatori_denormalizzati_view.id_lookup_tipo_linee_mobili,
    opu_operatori_denormalizzati_view.id_tipo_linea_produttiva,
    opu_operatori_denormalizzati_view.id_soggetto_fisico,
        CASE
            WHEN opu_operatori_denormalizzati_view.codice_attivita = ANY (ARRAY['IUV-CAN-CAN'::text, 'VET-AMBV-PU'::text, 'VET-CLIV-PU'::text, 'VET-OSPV-PU'::text]) THEN 5
            WHEN opu_operatori_denormalizzati_view.codice_attivita = ANY (ARRAY['IUV-CODAC-VEDCG'::text, 'IUV-COIAC-VEICG'::text]) THEN 6
            ELSE NULL::integer
        END AS id_linea_bdu
   FROM opu_operatori_denormalizzati_view
   left join master_list_flag_linee_attivita on master_list_flag_linee_attivita.codice_univoco = opu_operatori_denormalizzati_view.codice_attivita
  WHERE master_list_flag_linee_attivita.bdu = true;

ALTER TABLE public.opu_operatori_denormalizzati_view_bdu
  OWNER TO postgres;
GRANT ALL ON TABLE public.opu_operatori_denormalizzati_view_bdu TO postgres;
GRANT SELECT ON TABLE public.opu_operatori_denormalizzati_view_bdu TO report;

