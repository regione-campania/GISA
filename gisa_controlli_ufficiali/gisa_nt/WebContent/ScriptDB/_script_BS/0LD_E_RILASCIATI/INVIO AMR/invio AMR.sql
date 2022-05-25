alter table dati_amr add column num_registrazione_provenienza text;

-- Modifica: creo tabella di associazione tra codice linea e cun sinvsa
CREATE TABLE cun_amr (id serial PRIMARY KEY, numero_registrazione TEXT, cun_sinvsa TEXT);


update lookup_piano_monitoraggio set codice_esame = 'PMAMR' where description ilike '%c39%' and anno = 2018 and codice_esame ='';

--veterinari

CREATE TABLE veterinari_accreditati_sinvsa(id serial primary key, id_asl integer, cognome text, nome text, codice_fiscale text, enabled boolean default true);

insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'ANDREOTTOLA','SOSSIO ANTONIO PASQUALE','NDRSSN58E17I163N');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'ANNICCHIARICO','FRANCO','NNCFNC55C08M203B');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'ANTOLINO','NICOLA','NTLNCL49M14A284K');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'BARBATO','TOBIA CARMINE','BRBTCR58S08F693Q');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'BELLAROSA','GIOVANNI','GNNBLL28A61E509E');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'BRANCA','PASQUALE','BRNPQL48E06L589H');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'CACCESE','GIUSEPPE','CCCGPP57P09F448N');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'CERRETA','PAOLO','CRRPLA59S24A509Y');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'CILETTI','GIUSEPPE','CLTGPP57L31A566L');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'CIONE','ALFONSO','CNILNS62T21B674V');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'CIRILLO','RITA','CRLRTI58S69A509D');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'COVELLUZZI','MICHELE','CVLMHL58D12I034V');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'CUSANO','OTO','CSNTOO56E08A399P');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'D''AMATO','VINCENZO','DMTVCN56P13G370T');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'DE LUNA','ROCCO','DLNRCC58B26B492U');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'DE PACE','ANTONIO GIUSEPPE','DPCNNG55L27D988C');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'DI GIULIO','MARIO','DGLMRA56E10B267S');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'DI LEO','GERARDO','DLIGRD56L05E245M');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'DI LEO','VITTORIO CESARE','DLIVTR56H07E245M');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'DI NAPOLI','MICHELE','DNPMHL58B18E746O');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'FAGGIANO','ALESSANDRO','FGGLSN55B05F694J');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'FERRUCCI','ANNUNZIATA','FRRNNZ58R52L589G');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'FOSCHI','GIACOMO','FSCGCM60D13A489X');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'GIORDANO','ANNAMARIA','GRDNMR62C60A509U');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'GIOVANNIELLO','ROCCO','GVNRCC62F23Z133X');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'GRASSO','FRANCESCO','GRSFNC60B22H128R');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'GRASSO','GIOVANNI','GRSGNN50P20D638M');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'LANZA','ANGELO RAFFAELE','LNZNLR58B07E206G');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'LODISE','ANTONIO','LDSNTN57M05B776U');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'MAURIELLO','ANTONIO','MRLNTN57R22F511D');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'MAURIELLO','CARMINE','MRLCMN59E30F511W');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'NITTI','MAURO','NTTMRA62C14I990O');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'PALLANTE','ALFONSO','PLLLNS63P30B674U');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'PARENTE','GERARDO','PRNGRD50S17F512U');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'PASCIUTI','GERARDO','PSCGRD56I01I062L');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'PATRONE','RAFFAELE','PTRRFL58L14A566K');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'PEDICINI','ALFONSINA','PDCLNS65M46A509V');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'PETITTO','ANTONIO','PTTNTN56E06L739P');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'PIO','ANTONIO','PIONNM55H08E397C');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'RACCA','CRESCENZO','RCCCSC60R06A509Z');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'RACIOPPI','AMEDEO','RCPMDA61B02C976U');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'RINALDO','GIOVANNI ANTONIO ROSARIO','RNLGNN58M06I163J');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'ROSSI','NICOLINO','RSSNLN73E05L399C');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'SOLAZZO','DOMENICO','SLZDNC53A01A881L');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'SPATOLA','ITALO','SPTTLI80R13G039I');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'VECCHIA','IMMACOLATA','VCCMCL67B41Z103Z');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'VITAGLIANO','PASQUALE','VTGPQL51M12I016J');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'ZARRA','ALESSANDRO','ZRRSVT61P15L102O');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (201,'ZARRA','SALVATORE ALESSANDRO','ZRRSVT61P15L102O');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'ALTIERI','GIOVANNI','LTRGNN55H23I455T');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'BARONE','ANGELAMARIA','BRNNLM62B67C476J');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'BARONE','ENRICO','BRNNRC56L19C719Q');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'CALABRESE','TONINO','CLBTNN55B05M093Y');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'CARLUCCI','DANILA','CRLDNL60T47A783M');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'CIARMOLI','VINCENZO E.','CRMVCN54A06F494O');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'D''ALLOCCO','STEFANO','DLLSFN58A22A783X');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'DE NICOLA','MASSIMO','DNCMSM60E26C525C');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'DI CHIARA','ARCANGELO','DCHRNG52P23A265Q');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'FRANCESCA','ALESSANDRO','FRNLSN64B09H501Q');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'GAUDIO','MICHELE','GDAMHL53C27A265T');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'GENTILCORE','ANTONIO','GNTNTN57E21D650H');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'GENTILE','MARIO','GNTMRA52H07C211P');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'IAMPIETRO','VINCENZO','MPTVCN54A29D650V');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'IAVECCHIA','COSIMO','VCCCSM62C03A783A');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'IZZO','BERARDINO','ZZIBRD65R11A783K');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'IZZO','GIUSEPPE ','ZZIGPP58A18H592L');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'LAVORGNA ','ANTIMO','LVRNTM56D30H955S');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'MADDALENA','ROBERTO','MDDRRT55E14C719U');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'MANCINELLI','ABELE','MNCBLA60H01E249D');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'MANCINI','ANGELO','MNCNGL58B14E249K');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'MASELLI','LUCIO','MSLLCU58P25F717Z');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'MATARAZZO','GIOVANNI','MTRGNN55D01M093I');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'MIGNONE','LELIO','NDSNBL12L23P342P');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'MUTO','CRESCENZO','MTUCSC71D22I073U');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'PALMIERI','ELIGIO','PLMLGE57D09D469T');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'PAPA ','RAFFAELE','PPARFL54B11C211I');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'PARENTE','ALESSANDRO','PRNLSN55T12C476R');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'PASCARELLA ','POMPEA','PSCPMP52E56Z114X');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'PESCATORE ','POMPEO','PSPPMP54E07A783T');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'ROMANO','GENNARO','RMNGNR56C23A783S');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'ROSSI','ALFREDO','RSSLRD60T12L219D');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'SATERIALE','FIORENTINO','STRFNT51P01L739W');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'TAGLIAFIERRO','MARIA','TGLMRA69C62A265E');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'TANGA','CARMINE','TNGCMN57E19H224T');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'TORIELLO ','ALFREDO','TRLLRD56B02F494J');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'TOZZI','MARA','TZZMRA70D54F839M');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'VECCHIOLLA','MICHELE','VCCMHL58L05H894J');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'ZABATTA','RAFFAELE','ZBTRFL57C05A783T');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'ZACCARI','GIOVANNI','ZCCGNN55S18C719E');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'ZERELLA','ANGELO','ZRLNGL60C10C476I');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (202,'ZURLO','GIANDONATO','ZRLGDN55M03A783P');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'ABBATIELLO','MICHELINA','BBTMHL69M54D386O');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'AFFINITA','GERARDO MARIO','FFNMGR56E30I233H');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'ALEMANNO','RICCARDO','LMNRCR71B21B963A');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'ALLOCCA','ANNA','LLCNNA64E58C697S');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'ANTROPOLI','MICHELINA SILVANA','NTRMHL63A57Z602R');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'AVILLA','ROSARIO','VLLRSR58P15E589Z');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'BIELE','GIOVANNI','BLIGNN58M04A783T');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'BOIANO','ROMOLO MASSIMO ADRIANO','BNORLM56L08H939V');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'BRUNO','FERNANDO','BRNFNN63H23A228P');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'BUONOMO','LUISA','BNMLSU62H62Z401X');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'CAMMISA','LUIGI','CMMLGU66D19F839X');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'CAMPANILE CASTALDO','PASQUALE','CMPPQL51D22B963L');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'CAPPABIANCA','MARIATERESA','CPPMTR71D60I234T');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'CAPUANO','MATTIA','CPNMTT51M24A512R');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'CASAPULLA','RAFFAELE','CSPRFL64C20B963Z');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'CASO','FRANCESCO','CSAFNC51P17H939I');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'CASSELLA','GIOVANNI','CSSGNN57D24B362B');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'CASTELLI ','ANTONIO','CSTNTN57E15D708D');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'CERVO','GIOVANNI ','CRVGNN62A31I234N');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'CERVO','RUGGIERO','CRVRGR49S07I234X');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'CHIAVARONE','PALMIRA','CHVPMR64P48F839J');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'CHIUCHIOLO','MICHELE','CHCMHL60R08F557J');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'CICATIELLO','GENNARO','CCTGNR64S05B963N');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'CIERVO','MICHELE','CRVMHL53P30B872X');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'CIOFFI','FRANCESCO','CFFFNC59M05F839V');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'CONCA','ALDO','CNCLDA58D19A200T');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'CONFREDA','GENNARO','CNFGNR67P23H939E');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'D''ANDREA','VINCENZO','DNDVCN67B04G596K');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'DE CESARE','ANGELO GIUSEPPE','DCSNLG53C04H436E');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'DE FELICE','GIOVANNI','DFLGNN56M09I285F');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'DE FILPO','VINCENZO LUCIANO','DFLVCN58T13L873M');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'DE GREGORIO','LUIGI','DGRLGU49P07B362U');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'DE IORIO','LUIGI ARTURO','DRELRT62C16L540I');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'DE MICCO','SALVATORE','DMCSVT62M52F839N');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'DEL PRETE','DALIA','DLPDLA65R67F839X');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'DEL VECCHIO ','EUGENIO','DLVGNE61A28H798S');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'DELLA SALA','LUISA','DLLLSU68T53A509S');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'DELLO STRITTO','GIOVANNI','DLLGNN59M20B963J');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'DELLO STRITTO','MARIO','DLLMRA56E24B963M');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'DI LILLO ','RAFFAELE','DLLRFL58D13M092W');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'DI LORIA','GIUSEPPE','DLRGPP71M05I838S');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'DI MARZO','RICCARDO','DMRRCR58D03I233U');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'DI PIETRO','ORNELLA','DPTRLL63E42B860B');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'ESPOSITO','PASQUALE','SPSPQL51S11F839Q');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'FELACO','GIUSEPPE','FLCGPP58A04B872M');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'FELIZIANI','ELENA','FLZLNE64H64I754Q');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'FERRARA','CARLO','FRRCRL58S03B963E');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'FIORILLO','GERARDO','FRLGRD59C29I234W');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'FLORIO','ALFONSO','FLRLNS56A04I233D');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'FUCCI','ERMANNO','FCCRNN68M04B963Z');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'GALASSO','DOMENICO','GLSDNC60M11D612R');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'GENTILE','MARIO','GNTMRA52H07C211P');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'GUARNIERI','ANTONIO','GRNNTN60L23A512J');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'IODICE','GIACOMO','DCIGCM61S07B963B');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'IZZO','ALFONSO','ZZILNS59L22B715Z');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'IZZO','GIUSEPPE POMPEO','ZZIGPP58A18H592L');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'IZZO','RAFFAELE','ZZIRFL58L25B445T');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'LA VECCHIA','FRANCESCO','LVCFNC55M13A243N');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'LANDINO','DONATO','LNDDNT54D10E038R');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'LANDOLFI','MARIA CRISTINA','LNDMCR61L64F839L');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'LAPERUDA','LUIGI','LPRLGU57T10F839L');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'LEONARDO','GIUSEPPE','LNRGPP61R26G620K');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'LITTO','PATRIZIA','LTTPRZ57D47G388R');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'LORDI','STEFANIA ','LRDSFN62A45F839Z');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'MARINO','ANGELA','MRNNGL70D70B963F');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'MASIELLO','RAFFAELE','MSLRFL58B19H202L');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'MASTROIANNI','ALDO MARIO SERGIO','MSTLMR57B27C960S');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'MAZZOTTA ','FRANCESCO','MZZFNC57H26G964H');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'MELILLO','CARMINE GIULIO','MLLCMN66M27Z133D');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'MEROLA','ANTONIO','MRLNTN61A01D228V');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'MEROLA','SILVESTRO','MRLSVS59A15D228V');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'MESOLELLA','CARMINE','MSLCMN59A12B365M');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'MEZZINO','LAURA','MZZLRA82C65F839W');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'MOLLO','MASSIMILIANO','MLLMSM94S09F839T');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'MONE','ANGELO ANTONIO','MNONLN61T16G541L');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'MONETA','PATRIZIA','MNTPRZ60R54F839W');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'MOSCHETTI','MARIA ELENA','MSCMLN64H55B990A');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'NICOLETTI','ANTONELLA','NCLNNL55H44B963X');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'NUNZIATA','CECILIA','NNZCCL59M47F839B');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'PAESANO','PAOLA','PSNPLA57B64F839N');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'PAONE','CRISPINO ','PNACSP56C17E754T');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'PAPACCIO','GIUSEPPE','PPCGPP59P22B287A');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'PAPAS','DANIELA','PPSDNL60A42F839H');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'PERROTTA','ANTONIO','PRRNTN63S20H834J');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'PERTICARA''','NADIR','PRTNDR61T20F839W');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'PESCE','VINCENZO','PSCVCN55A21D799G');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'PICCIRILLO','ANGELA','PCCNGL61C63B860G');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'PITRUZZELLA','CARLO ','PTRCRL62C03G482E');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'POCINO','GIOVANBATTISTA','PCNGNB53T03A200R');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'RAUCCI','RENATO','RCCRNT54E26E932R');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'RECCIA','STEFANO','RCCSFN66M06A512C');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'ROSSI','MARGHERITA ','RSSMGH51B50F352K');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'ROSSI','RENATA','RSSRNT66D61F839U');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'RUSSO','ANDREA','RSSNDR57L03D769X');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'RUSSO','GIOVANNI','RSSGNN58P27C211Y');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'RUSSO','GIUSEPPE','RSSGPP67E13Z133G');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'SABATINI','GIANPIERO','SBTGPR70H12A512I');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'SCALERA','CARLO','SCLCRL53L22E791S');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'SGAMBATO','PASQUALE','SGMPQL51A14H834B');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'SOLA','LUCIO','SLOLCU68E29C129U');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'SORICE ','GIULIO ','SRCGLI56L19A403J');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'STIRPE','MARIA LUISA','STRMLS53L42H501S');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'TORRE','VINCENZO','TRRVCN60C23F839Z');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'TORTORELLI','MICHELE','TRTMHL61D27A200D');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'UNGARO','ROCCO','NGRRCC59B18L439O');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'VENDEMIA','NICOLA','VNDNCL58M25G364L');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'VERRENGIA','LORENZO','VRRLNZ60S27F839D');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'VIOLA','ANTONIO','VLINTN54H09B581B');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'VITELLI','CARMINE','VTLCMN61T11I234S');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (203,'ZITO','FULVIO','ZTIFLV61R13E158Y');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'ALBANESE','MARCO','LBNMRC51C05A509J');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'ALFIERO','GIUSEPPE','LFRGPP69B01F839J');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'BONTEMPO','PAOLO','BNTPLA59T18B519I');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'CANALE','MARCO','CNLMRC61C26F839A');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'CAPUTO','VINCENZO','CPTVCN60B24F839R');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'CIARDIELLO','MARIA','CRDMRA55H62F839O');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'CITARELLA','DOMENICO','CTRDNC63C07L142N');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'CONFALONE','ASSUNTA','CNFSNT62L50F839H');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'CORTESE','MARCELLA','CRTMCL60M64F839E');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'COSTAGLIOLA','OLIMPIA','CSTLMP61B64F839E');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'CUTILLO','PATRIZIA','CTLPRZ62E52F839E');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'D''ACIERNO','CARMELA','DCRCML59C44Z133T');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'DANIELE','EUGENIA','DNLGNE62M42G964K');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'DE FILIPPO','BRUNO','DFLBRN53M12F839W');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'DE TOMMASO','MARIA ROSARIA','DTMMRS58C65F839T');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'DI MAGGIO','ANNAMARIA','DMGNMR60M43F839S');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'D''ISA','MARIANO','DSIMRN70T08F839T');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'ESPOSITO','VINCENZO','SPSVCN55L15F839G');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'GALASSO','ALESSANDRA','GLSLSN63D65F839K');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'GALLO','CONCETTA','GLLCCT61P64F839I');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'GENTILE','ARMIDA','GNTRMD57C41F839B');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'GIOVANNI','PEIS','PSEGNN53D30B779M');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'GUARNIERI','SERENA','GRNSRN74E61F839T');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'IZZILLO','DANIELA','ZZLDNL69H48F839Q');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'LINZALONE','ENRICO','LNZNRC48P02F839G');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'MANZO','GABRIELLA','MNZGRL62D61F839B');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'MARRA','MARISTELLA','MRRMST61B43F839F');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'MIZZONI','VINCENZO','MZZVCN65T20I838I');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'NAGAR','VITTORIO','NGRVTR59M03F839Z');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'NETTUNO','LUCIANO','NTTLCN57A23F839E');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'NISCI','BRUNA','NSCBRN71P51F839J');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'PAPPONE','FRANCESCO','PPPFNC67H10F839H');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'PENNACCHIO','SAVERIO','PNNSVR73T21G964D');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'PEZZULLO','LIDIA','PZZLDI59H66H330Z');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'PITARO','LAURA','PTRLRA69D65F839R');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'POMPAMEO','MARINA','PMPMRN60B65F839D');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'POPOLO ','GIUSEPPE','PPLGPP58R27F839M');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'RAIA','PASQUALE','RAIPQL56E22G190G');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'RINALDI','GIOVANNI ','RNLGNN56S10F839P');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'ROTONDO','GIORGIO','RTNGRG53M08F839M');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'SANTOJANNI','CARLA','SNTCRL70E49F839D');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'SPOLETO','ANTONIO','SPLNTN52A25F839U');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'TORTORA','LIVIA SABRINA','TRTLSB55M41F839B');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'TOSCANO','VALERIO','TSCVRM67L11B963K');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (204,'TUCCILLO','PIERO','TCCPRI59T24F839R');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,' DE FILIPPO','CIRA','DFLCRI67C61F839L');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'ABBRONZINO ','IMMACOLATA','BBRMCL67H51F839Z');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'AMBROSIO','NICOLA','MBRNCL69E05F839U');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'AULETTA','FRANCESCO','LTTFNC54C18D789Z');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'BALDONI','ROBERTA','BLDRRT75L62F839R');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'BARBATO','MASSIMO','BRBMSM55D06F839L');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'BURLIZZI','SERENA','BRLSRN61H55F839D');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'CAPRARO','EMANUELA','CPRMNL56A41F839N');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'CARLINO','DANIELA','CRLDNL69H69F839U');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'CATALANO','PATRIZIO','CTLPRZ56L09F839M');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'CAVALIERE','MARIA','CVLMRA71C54F839F');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'CERULO','MARIA','CRLMRA58C58E249A');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'COZZOLINO','ANNA MARIA','CZZNMR58L60F839Z');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'CRIMALDI','CUONO ANTONIO','CRMCNT61H06A024L');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'DE ROSA','ACHILLE','DRSCLL54L21G964O');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'DE SILVA','MARIO ','DSLMRA58R29L245E');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'DELLA GALA','GIACINTO','DLLGNT51R16F924M');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'DI SARNO','CIRO','DSRCRI60P22H892M');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'FARRIS','MARIA','FRRMRA58B46F839Q');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'GIGLIO','MARINA','GGLMRN60T44F839S');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'GIORDANO','GREGORIO','GRDGGR60B29G964B');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'LANZILLO','FRANCESCA','LNZFNC65R59F839N');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'MADDALUNO','MARIAGIUSEPPA','MDDMGS65C51G964P');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'MASTROVITA','MARCO','MSTMRC61A23B519E');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'ONOFRIO','SILVESTRE','SLVNFR70L22B925L');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'ORLANDO','RAFFAELA','RLNRFL59E51E906A');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'PACELLI','ANTONELLA','PCLNNL59A61F839N');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'PERES','GIACOMO','PRSGCM63S28F839Y');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'POTUTO','DONATO','PTTDNT63R14I264M');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'ROMANO','CLAUDIA','RMNCLD76P46G964N');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'ROSSI','FIORE','RSSFRI60P02F839A');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'ROTILI','FABRIZIA','RTLFRZ60T48A783N');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'RUSSO','BIAGIO','RSSBGI62E30B990U');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'SANNINO','RAFFAELE','SNNRFL60S01F839F');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'SIRAGUSA','CRISTINA','SRGCST68L55E932M');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'SPADA','ANGELO','SPDNGL62M07F839R');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'STEFANELLI','MARIA','STFMRA65S65F839N');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'TOTARO','PASQUALE','TTRPQL66H12G964D');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (205,'TOTARO ','GIUSEPPE','TTRGPP70P09F799C');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'ABAGNALE','LUDOVICO','BGNLVC56R17B076O');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'ANNUNZIATA ','PAOLO','NNNPLA56P23H931E');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'BOCCIA','ALFREDO','BCCLRD61R09G190M');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'BRUNO','ROSANNA','BRNRSN63T04F839S');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'BUONOCORE','NICOLA','BNCNCL52A01A068L');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'CALABRIA','CARMELA','CLBCML61B44F839U');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'CASALINO','FELICE','CSLFLC60S19G190Z');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'CASTELLANO','FRANCESCO SAVERIO','CSTFNC59P19F162X');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'CAVALLO','CARMELO','CVLCML66L01G538X');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'CERIELLO','VINCENZO','CRLVCN60A13I262J');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'CICOIRA','ORNELLA','CCRRLL62M42F839J');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'CIRINO','ANGELO','CRNNGL60E15A509X');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'COPPOLA','CATELLO','CPPCLL57T25G813S');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'CUOMO','AUGUSTO','CMUGST56C15L845Z');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'DADDONA','GIOVANNI','DDDGNN61R06F839N');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'D''ADDONA','GIOVANNI','DDDGNN61R06F839N');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'DONNARUMMA','ANTONIO','DNNNTN64T24L845P');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'FALSETTI','STEFANIA','FLSSFN68B45G902W');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'FINELLI','FABIO','FNLFBA65C18F839Y');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'FORNABAIO','RUGGIERO','FRNRGR58B23I954Q');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'GALLO','MARIAROSARIA ','GLLMRS65D51L245D');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'GARGIULO','GIANBATTISTA','GRGGBT54P28I208P');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'GIANBATTISTA','GARGIULO','GRGGBT54P28I208P');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'GIANNATTASIO','ANTONIO','GNNNTN58C23C129S');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'GIANNATTASIO ','ANTONIO','GNNNTN58C23C129S');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'GUIDA','RICCARDO','GDURCR61L15H501X');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'LORELLO','MASSIMO','LRLMSM62M01F839O');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'LUCANO ','PIETRO ','LCNPTR59R30B895S');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'LUCIBELLI','GIUSEPPE','LCBGPP62B24F839C');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'LUONGO','MARIA RAFFAELLA','LNGMRF61P62A228J');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'MALESANI','BRUNO AUGUSTO','MLSBNG59T06L781Z');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'MANNITI','FRANCESCO','MNNFNC57L26F839L');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'MARCHEGGIANI','RAFFAELE','MRCRFL60E20L835Y');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'MARCHIO''','VIRGINIO','MRCVGN57C09H243C');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'MASCOLO','LUCA','MSCLCU55H27A068B');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'MICILLO','PAOLO','MCLPLA52L03F839N');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'MIRANDA','ELVIRA','MRNNTN53P29F839D');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'MOLLICA','DOMENICO','MLLDNC60E10A509O');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'NOBILE','GIULIANA','NBLGLN61R42F839Z');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'ORLANDO','SALVATORE','RLNSVT66C12G568G');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'PANDOLFI','FIORELLA','PNDFLL57P42F839T');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'PAOLELLA','CLAUDIO ','PLLCLD58D08G190M');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'PAPARO','EMILIO ','PPRMLE62H08G813C');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'PAPPALARDO','ROSANNA FILOMENA','PPPRNN61D43C584H');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'PASQUA','ANTONIO','PSQNTN61L27G902I');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'PECORARO','ALFREDO','PCRLRD62H09H860T');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'PERTICARA''','NADIR','PRTNDR61T20F839W');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'PICCIOLO','EMANUELA LIDIA','PCCMLL59M54I930U');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'PINFILDI','MARIA','PNFMRA62B52F839U');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'PULERÃƑÂ ','GIUSEPPE','PLRGPP53B10A743O');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'RENZETTI ','ELIO','RNZLEI58B01G902V');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'ROSELLI','LUISA','RSLLSU69R60F839E');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'RUOCCO','FAUSTO','RCCFST61B07E557T');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'SALAMONE','ANTONIO','SLMNTN56H09B959E');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'SCIBELLI','ELIO','SCBLEI61H19F839P');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'SIANO','SERGIO','SNISRG55E22G283G');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'SOLA','FABIO','SLOFBA66M02C129E');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'SOLA','LUCIO','SLOLCU68E29C129U');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'SORRENTINO','ELENA','SRRLNE60L48G902Q');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'SORRENTINO','GIUSEPPE','SRRGPP40C22F839A');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'SPINELLI','CARMELA','SPNCMN59E66G813H');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'TIMORATI','GIOVANNI','TMRGNN56E09E791W');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'TONELLI','ANTONIETTA','TNLNNT66B64L259F');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'TROMBETTA','SIMONETTA PATRIZIA','TRMSNT59C42Z112R');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'TROTTA','MARCO','TRTMRC62S14L259S');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'VACCARO','GERARDO F.','VCCGRD62M23F108Y');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'VARRELLA','ANNA','VRRNNA62R43C129Z');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'VELLA','PIERLUIGI','VLLPLG61L24F839Z');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (206,'ZAMBOLI','ANTONIO GIUSEPPE','ZMBNNG53D24G762M');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'ALIANDRO','MARIO FRANCESCO','LNDMFR53R04E976Q');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'AMATO','ANIELLO','MTANLL62C09E098M');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'AMBROSIO','CARLO','MBRCRL60S05A509S');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'ANTICO','ANTONIO','NTCNTN58P01H394K');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'APICELLA','PASQUALE','PCLPQL54M16C361D');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'AULETTA','ENRICO','LTTRCC50R30U444G');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'AVENA','ROCCO','VNARCC60L04B374W');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'BARLETTA','RITA','BRLRTI56S42A186T');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'BEVILACQUA','VINCENZO','BVLVCN50P06H431K');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'BIFOLCO','GIANPIERO','BFLGPR65R02H803V');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'BISOGNO','GERARDO','BSGGRD58S20I444K');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'BOTTIGLIERI','EDUARDO','BTTDRD56H14H431I');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'BOVE','GAETANO','BVOGTN59H14L323L');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'BOVE','RAFFAELE','BVORFL61B09I262E');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'BRENGA','COSIMO','BRNCSM65M18D390L');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'CALICCHIO','GIUSEPPE','CLCGPP57E20L274F');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'CAMMARANO','FRANCO BIAGIO','CMMFNC56H05F731G');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'CAPO','GENNARO','CPAGNR58A23H703W');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'CAPUTO','ANIELLO','CPTNLL58D22A091D');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'CAPUTO','FRANCESCO','CPTFNC57A08H412T');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'CARBONE','ROBERTO','CRBRBT60E30I666K');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'CARBONE','ROBERTO','CRBRRT69H04C879J');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'CARIELLO','MARCELLO','CRLMCL56M03B242J');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'CASABURI','VENAZIO','CSBVZN59P24C584S');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'CERRUTI','MICHELE','CRRMHL58T03A128D');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'CESTARO','NICOLA','CSTNCL55S26H683U');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'CINIELLO','ANGELO','CNLNGL55P19G838P');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'CIRONE','GIOVANNI','CRNGNN66A29D292S');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'CITARELLA','ALFONSO','BLZGPP84H13L259E');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'CITARELLA','VINCENZO','CTRVCN56C09F913U');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'CITRO','ANGELO','CTRANG56U10I666K');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'COLANTUONO','SALVATORE','CLTSVT58S30E444K');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'COSTANTINO','GIOVANNI','CSTGNN55H16C069B');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'COZZA','ANTONIO','CZZNTN56L13H796Q');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'CUBICCIOTTI','GIUSEPPINA','CBCGPP59C59B492I');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'D''ANTONIO','MASSIMO','DNTMSM60B20A783Q');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'DE ROSA','CONCETTA','DRSCCT55M41L259O');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'DI DOMENICO','GIOVANNI','DDMGNN49S11C361J');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'DI GUIDA','ANTONIO','DGDNNN60T09I317T');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'DI NICOLA','CLARA','DNCCLR53B60F839P');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'DI NOIA','FELICE','DNIFLC55S20I451B');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'DI SANTI','FRANCO','DSNFNC55A13A484V');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'D''ORILIA','FRANCESCANTONIO','DRLFNC59M31G793H');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'D''ORILIA','FRANCESCOANTONIO','DRLFNC59M31G793H');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'ENRICO','AULETTA','LTTNRC52E22H703S');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'ERCOLANO','ESTER','RCLSTR57D52H703B');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'FEROLLA','BRUNA','FRLBRN60S30I333H');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'FERRARI','GAETANO','FRRGTN59E15H348X');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'FERRENTINO','MARIA','FRRMRA60C69A294Y');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'FERRO','GIOVANNI','FRRGNN59E03F625U');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'FIORDELISI','ENRICO','FRDNRC55C09I307G');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'FIORENTINO','GOFFREDO','FRNGFR66C20H913Q');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'FORNINO','GIUSEPPE','FRNGPP54D29G226T');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'FRUNZO','PASQUALE','FRNPQL54M24A128T');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'GALLO','ANTONIO','GLLNTN50C04G226V');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'GALLO','MICHELE','GLLMHL55R07F507S');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'GALLO','SERGIO','GLLSRG61A26A230B');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'GIAQUINTO','ROSA','GQNRSO56L46I726W');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'GIOIA','DAMIANO','GIODMN63S22A717K');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'GRIMALDI','TIZIANA','GRMTZN58R30I555Y');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'GUARIGLIA','PASQUALE','GRGPQL52S05A091Z');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'GUERCIO','MARIO','GRCMRA60T08E485J');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'IANNUZZI','FRANCESCO','NNZFNC57C09H564I');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'IMPROTA','ALFREDO','MPRLRD57H18H703V');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'INVERSO','CLAUDIO','NVRCLD56D24G121Z');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'LANGELLA','ADRIANA','LNGDRN56L60I438V');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'LAURITO','ANIELLO ','LRTNLL69M03B608R');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'LENZA','AUGUSTO','LNZGST55C02L628W');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'LODATO','NICOLA','LDTNCL57A22C361P');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'LOTIERZO','GIANCARLO','LTRGCR62M24L874V');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'MANDIA','ANNA','MNDNNA63L59A717Y');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'MARCIANO','ORAZIO','MRCRZO55D20E480O');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'MARINO','GIULIO','MRNGLI58C13G887B');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'MAROTTA','MARIA PIA','MRTMRP69A46A509G');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'MAZZEO','CINZIA','MZZCNZ60P63A717X');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'MELE','ROSANNA','MLERNN60M42L314C');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'METELLO','ANNA','MTLNNA61S44C361A');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'MILANO','TIZIANA','MLNTZN63A45F839G');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'MILONE','GAETANO','MLNGTN63M04G813E');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'MORENA','LUIGI','MRNLGU55B21G538G');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'MUCCIOLO','CLAUDIO','MCCCLD58L21C262B');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'NESE','AURELIANO','NSERLN70H16G121J');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'NESE','DOMENICO ANTONIO','NSEDNC51B23G121Z');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'PAGANO','ROSETTA','PGNRST56R20U555G');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'PAGANO','ROSETTA','VGNRDD59C55D011Z');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'PALLADINO','FRANCO','PLLFNC58D01F278F');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'PANETTA','ROCCO','PNTRCC57A30H683M');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'PAPA','MANLIO','PPAMNL67C30A509X');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'PARAGGIO','GERARDO','PRGGRD54A09F481J');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'PARENTE','GIUSEPPE','PRNGPP61R26I410U');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'PETTI','SALVATORE','PTTSVT50B26F913R');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'PISCOPIA','ANTONIO','PSCNTN48P19E976Z');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'PIZZOLANTE','GIUSEPPE','PZZGPP68S29Z700U');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'POLICHETTI','ANTONIO','PCLNTN56T01H431H');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'PUGLIESE','RAFFAELE','PGLRFL51D01H683I');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'QUARANTA','DOMENICO ANTONIO','QRNNND60A25G292F');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'RICCHIUTI','ANIELLO','RCCNLL52P01F967G');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'RIZZO','ANNAMARIA','RZZNMR59T48G538W');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'ROCCO','SIMONA','RCCSMN60S30I555H');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'ROCCO','SIMONA','RCCSMN67C58H703Q');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'RUSSO','GIOVANNI','RSSGNN54P15L323I');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'RUSSO','MATTEO','RSSMTT64C04A717V');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'SACCO','ANTONIO','SCCNTN64R14A230B');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'SCOPELLITI','ANTONINO','SCPNNN56L19I422C');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'SERRA','CARMINE','SRRCMN58T22A091P');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'SQUILLARO','VINCENZO','SQLVCN59H22A091X');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'STASIO','GIUSEPPE','STSGPP51S20I666K');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'STROLLO','GERARDO','STRGRD55D15I666K');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'TADDEO','LIBERATO','TDDLRT55M16B644N');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'TOMEI','GIUSEPPE','TMMGPP56D20I333J');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'TREZZA','GIOVANNI CARLO','TRZGNN56A31E483O');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'TROTTA','ANGELO','TRTNGL60P11D527I');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'VELLUTO','GIUSEPPE','VLTGPP66V11M333L');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'VENEZIANO','GIUSEPPE','VNZGPP57C18G230A');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'VILLAMAINA','GERARDO','VLMGRD55R30I555H');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'VILLANI ORLANDO','VINCENZO','VLLVCN59L26A294A');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'VUOZZO','DANIELA','VZZDNL64H56F735E');
insert into veterinari_accreditati_sinvsa(id_asl, cognome, nome, codice_fiscale) values (207,'ZAMBROTTA','PASQUALE','ZMBPQL53S17E409R');


CREATE OR REPLACE FUNCTION public.amr_get_lista_veterinari(
    IN _idasl integer
       )
  RETURNS TABLE(nome text, cf text) AS
$BODY$
DECLARE
r RECORD;	

BEGIN

FOR 

nome, cf

in

SELECT distinct concat_ws(' ', vet.nome, vet.cognome), vet.codice_fiscale FROM veterinari_accreditati_sinvsa vet WHERE   1=1 AND ((_idasl>0 and vet.id_asl = _idasl) or _idasl=-1)
AND vet.codice_fiscale in (select codice_fiscale  from lista_utenti_centralizzata where trashed_date is null and data_scadenza is null)
ORDER BY codice_fiscale asc 

    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  
  

--dbi: anche i macelli

-- Function: public.is_controllo_amr(integer)

-- DROP FUNCTION public.is_controllo_amr(integer);
CREATE OR REPLACE FUNCTION public.is_controllo_amr(_idcontrollo integer)
  RETURNS boolean AS
$BODY$
DECLARE
	_ticketId integer;
	esito boolean;
BEGIN

_ticketId := -1;
esito:= false;

_ticketId := (select distinct t.ticketid
from ticket t
left join tipocontrolloufficialeimprese tcu on tcu.idcontrollo = t.ticketid
left join lookup_piano_monitoraggio piano on piano.code = tcu.pianomonitoraggio
left join ricerche_anagrafiche_old_materializzata r on 
((r.riferimento_id = t.org_id and r.riferimento_id_nome_col = 'org_id') or (r.riferimento_id = t.id_stabilimento and r.riferimento_id_nome_col = 'id_stabilimento') or (r.riferimento_id = t.alt_id and r.riferimento_id_nome_col = 'alt_id'))
where t.tipologia = 3 and t.trashed_date is null and piano.codice_esame = 'PMAMR' and r.n_linea in (select numero_registrazione from cun_amr where cun_sinvsa is not null) and t.ticketid = _idcontrollo and t.ticketid in (select distinct id_controllo_ufficiale::integer from ticket c
left join lookup_piano_monitoraggio p on p.code = c.motivazione_piano_campione
where c.trashed_date is null and c.tipologia = 2 and c.id_controllo_ufficiale = _idcontrollo::text and p.codice_esame = 'PMAMR'));

if (_ticketId) > 0 THEN
esito = true;
END IF;

 RETURN esito;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.is_controllo_amr(integer)
  OWNER TO postgres;

  -- Function: public.lista_controlli_amr(integer, integer, text, text, text)

-- DROP FUNCTION public.lista_controlli_amr(integer, integer, text, text, text);

CREATE OR REPLACE FUNCTION public.lista_controlli_amr(
    IN _idcontrollo integer,
    IN _idasl integer,
    IN _numverbaleamr text,
    IN _datainizio text,
    IN _datafine text)
  RETURNS TABLE(id_controllo integer, id_asl integer, piano_monitoraggio text, num_verbale_amr text, data_controllo timestamp without time zone) AS
$BODY$
DECLARE
r RECORD;	

BEGIN

FOR 

id_controllo, id_asl, piano_monitoraggio, num_verbale_amr, data_controllo

in


select distinct t.ticketid, t.site_id, piano.description, d.num_verbale_amr, t.assigned_date
from ticket t
left join tipocontrolloufficialeimprese tcu on tcu.idcontrollo = t.ticketid
left join lookup_piano_monitoraggio piano on piano.code = tcu.pianomonitoraggio
left join dati_amr d on d.id_controllo = t.ticketid and d.enabled
left join ricerche_anagrafiche_old_materializzata ric on 
((ric.riferimento_id = t.org_id and ric.riferimento_id_nome_col = 'org_id') or (ric.riferimento_id = t.id_stabilimento and ric.riferimento_id_nome_col = 'id_stabilimento') or (ric.riferimento_id = t.alt_id and ric.riferimento_id_nome_col = 'alt_id'))

where t.tipologia = 3 and t.trashed_date is null and piano.codice_esame = 'PMAMR' 
and ric.n_linea in (select numero_registrazione from cun_amr where cun_sinvsa is not null)

and((_idcontrollo>0 and t.ticketid = _idcontrollo) or _idcontrollo = -1)
and((_idasl>0 and t.site_id = _idasl) or _idasl = -1)
and((_datainizio is not null and _datainizio <>'' and t.assigned_date >= _datainizio::timestamp without time zone) or _datainizio ='')
and((_datafine is not null and _datafine <>'' and t.assigned_date <= _datafine::timestamp without time zone) or _datafine ='')
order by t.assigned_date desc

    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.lista_controlli_amr(integer, integer, text, text, text)
  OWNER TO postgres;

  
-- Telefoni

CREATE TABLE asl_telefono(id_asl integer PRIMARY KEY, telefono text);
insert into asl_telefono values (201, '0825292664');
insert into asl_telefono  values (202, '0824308538');
insert into asl_telefono values  (203, '0818121203');
insert into asl_telefono  values (204, '0812549518');
insert into asl_telefono  values (205, '08118840161');
insert into asl_telefono  values (206, '0818729080');
insert into asl_telefono  values (207, '089693570');





CREATE OR REPLACE FUNCTION public.get_chiamata_ws_invio_amr(
    _idcu integer)
  RETURNS text AS
$BODY$
DECLARE
	ret text;
BEGIN

select 
concat(
'<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://sinsa.izs.it/services"><soapenv:Header><ser:SOAPAutorizzazione><ruoloCodice>WSREG</ruoloCodice><ruoloValoreCodice>150</ruoloValoreCodice></ser:SOAPAutorizzazione><ser:SOAPAutenticazione><password>na.izs34</password><username>izsna_006</username></ser:SOAPAutenticazione></soapenv:Header>
    <soapenv:Body>
       <ser:insertPrelievo>',
'<prelievo>
<assistCodFiscale></assistCodFiscale>',
case when piano.description ilike '%carn%refriger%' then '
<campioniPrelevati> 	
<altreInformazioni>	
<informazioni>	
<entry>	
<key>provenienzaSpiNumRegistrazione</key>	
<value>' || COALESCE(amr.num_registrazione_provenienza,'') || '</value>	
</entry>	
<entry>	
<key>paeseDiProvenienzaOrigineDelProdotto</key>	
<value>027</value> 	
</entry>	
<entry>	
<key>identificativoCampioneNumeroLotto</key>	
<value>' || CASE WHEN trim(campione.problem) != '' and campione.problem is not null THEN trim(campione.problem) ELSE '2017/12' END || '</value>	
</entry>	
<entry>	
<key>telefonoPrelevatore</key>	
<value>' || tel.telefono || '</value>	
</entry>	
</informazioni>	
</altreInformazioni>	
' 
when piano.description ilike '%intestino%' then '
<campioniPrelevati> 	
<altreInformazioni>	
<informazioni>	
<entry>	
<key>identificativoBoxPolli</key>	
<value>' || COALESCE(amr.locale, '') || '</value>
</entry>	
<entry>	
<key>dataAccasamento</key>	
<value>' || COALESCE(amr.data_accasamento, '') || '</value>	
</entry>	
<entry>	
<key>specieCampionatePMRA2016</key>	
<value>' || case when piano.description ilike '%poll%' then '1020301030106' when piano.description ilike '%tacchin%' then '1020301040101' end  || '</value>	
</entry>	
<entry>	
<key>telefonoPrelevatore</key>	
<value>' || tel.telefono  || '</value> 
</entry>	
</informazioni>	
</altreInformazioni>	
' end,
'<foodexCodice>'|| case when piano.description ilike '%intestino%' then 'INTE03' when piano.description ilike '%carn%poll%' then 'A01SP' when piano.description ilike '%carn%tacchin%' then 'A01SQ' else '' end ||'</foodexCodice>
<numAliquote>1</numAliquote> 
<numUnitaCampionarie>1</numUnitaCampionarie> 
<progressivoCampione>1</progressivoCampione> 
<provenienzaSpiNumRegistrazione>' || COALESCE(amr.num_registrazione_provenienza, '') || '</provenienzaSpiNumRegistrazione> 
<provenienzaTipoImpresa>' || case when r.path_attivita_completo ilike '%macello%' then 'A' else 'Z' end  || '</provenienzaTipoImpresa> 
</campioniPrelevati>
<contaminanti>
<contaminanteCodice>RAM</contaminanteCodice>
<note></note>
</contaminanti>
<contaminantiXml></contaminantiXml>
<cun>' || COALESCE(cunamr.cun_sinvsa, '') || '</cun>
<dataFinePrelievo>' || concat(to_char(campione.assigned_date, 'yyyy-MM-dd'), 'T', amr.ora_fine_prelievo, ':00.0' )  || '</dataFinePrelievo>
<dataInizioIspezione>' || concat(to_char(campione.assigned_date, 'yyyy-MM-dd'), 'T', amr.ora_inizio_prelievo, ':00.0' )  || '</dataInizioIspezione>
<dataPrelievo>' || concat(to_char(campione.assigned_date, 'yyyy-MM-dd'), 'T', amr.ora_inizio_prelievo, ':00.0' )  || '</dataPrelievo>
<laboratorioCodice>0201A</laboratorioCodice> 
<luogoPrelievoCodice>'|| case when piano.description ilike '%intestino%' then '8' when piano.description ilike '%carn%%' then '47' else '' end ||'</luogoPrelievoCodice>
<metodoCampionamentoCodice>001</metodoCampionamentoCodice>
<motivoCodice>023</motivoCodice>
<numeroScheda>' ||COALESCE(amr.num_verbale_amr, '') ||'</numeroScheda>
<pianoCodice>PMRA2018</pianoCodice>
<prelevatori>
<prelCodFiscale>' || COALESCE(amr.codice_fiscale_veterinario, '') ||'</prelCodFiscale>
</prelevatori>
<tipoImpresa>A</tipoImpresa>
</prelievo>',
'</ser:insertPrelievo>
</soapenv:Body>
</soapenv:Envelope>') into ret
from dati_amr amr
left join ticket cu on cu.ticketid = amr.id_controllo
left join ticket campione on campione.id_controllo_ufficiale =cu.ticketid::text and campione.tipologia = 2
left join lookup_piano_monitoraggio piano on piano.code = campione.motivazione_piano_campione
left join asl_telefono tel on tel.id_asl = cu.site_id
left join ricerche_anagrafiche_old_materializzata r on ((r.riferimento_id = cu.id_stabilimento and r.riferimento_id_nome_col = 'id_stabilimento') or (r.riferimento_id = cu.alt_id and r.riferimento_id_nome_col = 'alt_id')) and (r.n_linea in (select numero_registrazione from cun_amr where cun_sinvsa is not null)) 
left join cun_amr cunamr on cunamr.numero_registrazione = r.n_linea
where amr.id_controllo = _idcu   and amr.enabled;


 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_invio_amr(integer)
  OWNER TO postgres;


  
-- Query di esempio per invio AMR
-- 1115696
  
  
  -- Librerie da mettere sotto tomcat
  
  okhttp
  okio
  
  -- Storico chiamate
  CREATE TABLE ws_storico_chiamate(id serial primary key, url text, request text, response text, id_utente integer, data timestamp without time zone default now()) ;
  
  -- Scheda centralizzata
  
  update scheda_operatore_metadati set enabled = false where tipo_operatore = 32;insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Dati Impresa','capitolo','','','','A','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Tipo Impresa','','i.description','sintesis_stabilimento s join sintesis_operatore o on s.id_operatore = o.id join lookup_opu_tipo_impresa i on o.tipo_impresa = i.code','s.alt_id=#altid#','b','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Domicilio digitale','','o.domicilio_digitale','sintesis_stabilimento s join sintesis_operatore o on s.id_operatore = o.id','s.alt_id=#altid#','f','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Ragione sociale impresa','','o.ragione_sociale','sintesis_stabilimento s join sintesis_operatore o on s.id_operatore = o.id','s.alt_id=#altid#','AA','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Tipo Societa','','i.description','sintesis_stabilimento s join sintesis_operatore o on s.id_operatore = o.id join lookup_opu_tipo_impresa_societa i on o.tipo_societa = i.code','s.alt_id=#altid#','c','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Partita IVA','','o.partita_iva','sintesis_stabilimento s join sintesis_operatore o on s.id_operatore = o.id','s.alt_id=#altid#','d','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Codice Fiscale Impresa','','o.codice_fiscale_impresa','sintesis_stabilimento s join sintesis_operatore o on s.id_operatore = o.id','s.alt_id=#altid#','e','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','CATEGORIA DI RISCHIO','','soa.description','sintesis_stabilimento s left join lookup_categoriarischio_soa soa on soa.code= s.livello_rischio','s.alt_id=#altid# and s.id in (select s.id from sintesis_relazione_stabilimento_linee_produttive rel
join sintesis_stabilimento s on s.id = rel.id_stabilimento
join ml8_linee_attivita_nuove_materializzata linee on linee.id_nuova_linea_attivita = rel.id_linea_produttiva
join opu_lookup_norme_master_list norme on norme.code = linee.id_norma
where norme.codice_norma ilike ''1069-09'' and s.alt_id = #altid# limit 1)','NC','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','DATA PROSSIMO CONTROLLO','','''NON PREVISTA''','sintesis_stabilimento s','s.alt_id=#altid# and s.id in (select s.id from sintesis_relazione_stabilimento_linee_produttive rel
join sintesis_stabilimento s on s.id = rel.id_stabilimento
join ml8_linee_attivita_nuove_materializzata linee on linee.id_nuova_linea_attivita = rel.id_linea_produttiva
join opu_lookup_norme_master_list norme on norme.code = linee.id_norma
where norme.codice_norma ilike ''1069-09'' and s.alt_id = #altid# limit 1)','ND','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','ASL','asl','asl.description','sintesis_stabilimento s join lookup_site_id asl on asl.code = s.id_asl','s.alt_id=#altid#','NN','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Lista linee produttive','capitolo','','','','p','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Lista prodotti','','concat_ws('' -> '', ''<b>''||tit_scheda||''<b/>'', lista_prodotti) from(
select distinct s.tit_scheda, string_agg(s.nome || case when p.valore_prodotto <>'''' then ''(''||p.valore_prodotto||'')'' else '''' end, ''; '') as lista_prodotti','master_list_sk_elenco s
join sintesis_prodotti p on s.id = p.id_prodotto
join sintesis_relazione_stabilimento_linee_produttive rel on rel.id = p.id_linea
join sintesis_stabilimento st on st.id = rel.id_stabilimento','st.alt_id = #altid# and p.unchecked_date is null
group by tit_scheda
) aa
','R','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Dati Stabilimento','capitolo','','','','i','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Denominazione','','s.denominazione','sintesis_stabilimento s','s.alt_id=#altid#','l','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','CATEGORIA DI RISCHIO','','coalesce(categoria_rischio,3)','sintesis_stabilimento s','s.alt_id=#altid# and s.id not in (select s.id from sintesis_relazione_stabilimento_linee_produttive rel
join sintesis_stabilimento s on s.id = rel.id_stabilimento
join ml8_linee_attivita_nuove_materializzata linee on linee.id_nuova_linea_attivita = rel.id_linea_produttiva
join opu_lookup_norme_master_list norme on norme.code = linee.id_norma
where norme.codice_norma ilike ''1069-09'' and s.alt_id = #altid# limit 1)','NA','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','DATA PROSSIMO CONTROLLO','','case when s.data_prossimo_controllo is null then to_char(current_date,''dd-mm-yyyy'') else to_char(s.data_prossimo_controllo,''dd-mm-yyyy'') end ','sintesis_stabilimento s','s.alt_id=#altid# and s.id not in (select s.id from sintesis_relazione_stabilimento_linee_produttive rel
join sintesis_stabilimento s on s.id = rel.id_stabilimento
join ml8_linee_attivita_nuove_materializzata linee on linee.id_nuova_linea_attivita = rel.id_linea_produttiva
join opu_lookup_norme_master_list norme on norme.code = linee.id_norma
where norme.codice_norma ilike ''1069-09'' and s.alt_id = #altid# limit 1)','nb','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Indirizzo','',' concat_ws('' '', top.description, ind.via, ind.civico, com.nome, prov.description)','  sintesis_stabilimento s join sintesis_indirizzo ind on ind.id = s.id_indirizzo
  left join lookup_toponimi top on top.code = ind.toponimo
  left join comuni1 com on com.id = ind.comune
  left join lookup_province prov on prov.code::text = ind.provincia','s.alt_id=#altid#','o','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Linee produttive','','mac.macroarea || ''->'' || agg.aggregazione || ''->'' || linea.linea_attivita  || '' ['' || stati.description || '']''','sintesis_relazione_stabilimento_linee_produttive rel join sintesis_stabilimento s on s.id = rel.id_stabilimento 
join master_list_linea_attivita linea on linea.id = rel.id_linea_produttiva
join master_list_aggregazione agg on linea.id_aggregazione = agg.id
join master_list_macroarea mac on agg.id_macroarea = mac.id
left join lookup_stato_attivita_sintesis stati on stati.code = rel.stato','s.alt_id=#altid# and rel.enabled','q','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Stato','','stati.description','lookup_stato_stabilimento_sintesis stati left join sintesis_stabilimento s on s.stato = stati.code','s.alt_id=#altid#','m','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Approval number','barcode','s.approval_number','sintesis_stabilimento s','s.alt_id=#altid#','n','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Indirizzo sede legale','',' concat_ws('' '', top.description, ind.via, ind.civico, com.nome, prov.description)','  sintesis_stabilimento s join sintesis_operatore o on s.id_operatore = o.id join sintesis_indirizzo ind on ind.id = o.id_indirizzo
  left join lookup_toponimi top on top.code = ind.toponimo
  left join comuni1 com on com.id = ind.comune
  left join lookup_province prov on prov.code::text = ind.provincia','s.alt_id=#altid#','g','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Rappresentante legale','',' concat_ws('' '', sogg.nome, sogg.cognome, sogg.codice_fiscale, ''<br/>'', top.description, ind.via, ind.civico, com.nome, prov.description)',' sintesis_stabilimento s join sintesis_operatore o on s.id_operatore = o.id join sintesis_rel_operatore_soggetto_fisico rel on rel.id_operatore = o.id and rel.enabled and rel.data_fine is null left join sintesis_soggetto_fisico sogg on sogg.id = rel.id_soggetto_fisico left join sintesis_indirizzo ind on ind.id = sogg.indirizzo_id
 left join lookup_toponimi top on top.code = ind.toponimo
  left join comuni1 com on com.id = ind.comune
  left join lookup_province prov on prov.code::text = ind.provincia','s.alt_id=#altid#','h','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','CUN SINVSA','','c.cun_sinvsa',' sintesis_stabilimento s inner join cun_amr c on c.numero_registrazione ilike s.approval_number','s.alt_id=#altid#','N0','');