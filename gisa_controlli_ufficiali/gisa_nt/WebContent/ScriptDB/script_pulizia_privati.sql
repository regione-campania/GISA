-- script update privati
update organization set note_hd =concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 768024. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id = 530535;
UPDATE ticket set org_id =768024, note_internal_use_only=concat_ws('-',note_internal_use_only,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 768024. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 530535)') where org_id = 530535;

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1051411. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id = 1054315;
UPDATE ticket set org_id =1051411, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1054315)') where org_id = 1054315;

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1158027. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id = 1158028;
UPDATE ticket set org_id =1158027, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1158028)') where org_id = 1158028;

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1020789. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id = 1020787;
UPDATE ticket set org_id =1020789, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1020787)') where org_id = 1020787;

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1167589. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id = 1167595;
UPDATE ticket set org_id =1167589, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1167589)') where org_id = 1167589;

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1054212. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id = 1054211;
UPDATE ticket set org_id =1054212, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1054211)') where org_id = 1054211;

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1031799. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id = 1031798;
UPDATE ticket set org_id =1031799, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1031798)') where org_id = 1031798;

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1053453. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id = 1053452;
UPDATE ticket set org_id =1053453, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1053452)') where org_id = 1053452;

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1054288. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1054286,1054284);
UPDATE ticket set org_id =1054288, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1054286,1054284)') where org_id in (1054286,1054284);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1172538. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1172534);
UPDATE ticket set org_id =1172538, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1172534)') where org_id in (1172534);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1069412. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1069410);
UPDATE ticket set org_id =1069412, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1069410)') where org_id in (1069410);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1054737,1090239. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1090236,1090107);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1054737. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1090239);
UPDATE ticket set org_id =1054737, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1090239))') where org_id in (1090239);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1054737. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1090239);
UPDATE ticket set org_id =1054737, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1090239))') where org_id in (1090239);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1051005. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1051004);
UPDATE ticket set org_id =1051005, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1051004))') where org_id in (1051004);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1056830. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1056551);
UPDATE ticket set org_id =1056830, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1056551))') where org_id in (1056551);

update organization set note_hd=concat_ws('-',note_hd,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1090486);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1090581. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1090487);
UPDATE ticket set org_id =1090581, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1090487))') where org_id in (1090487);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1087490. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1087489);
UPDATE ticket set org_id =1087490, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1087489))') where org_id in (1087489);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1008718. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (879450,878169);
UPDATE ticket set org_id =1008718, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 879450,878169))') where org_id in (879450,878169);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1025999. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1025997);
UPDATE ticket set org_id =1025999, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1025997))') where org_id in (1025997);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1175157. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1175118,1175094);
UPDATE ticket set org_id =1175157, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1175118,1175094))') where org_id in (1175118,1175094);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1087772. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1087533);
UPDATE ticket set org_id =1087772, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1087533))') where org_id in (1087533);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1029806. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1029599);
UPDATE ticket set org_id =1029806, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1029599))') where org_id in (1029599);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1029806. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1072806,1072814);
UPDATE ticket set org_id =1072809, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1072806,1072814))') where org_id in (1072806,1072814);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1053813. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1053810);
UPDATE ticket set org_id =1053813, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1053810))') where org_id in (1053810);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1051837. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1051835);
UPDATE ticket set org_id =1051837, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1051835))') where org_id in (1051835);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1162370. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1162368);
UPDATE ticket set org_id =1162370, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1162368))') where org_id in (1162368);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1046012. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1046040);
UPDATE ticket set org_id =1046012, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1046040))') where org_id in (1046040);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1090261. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1090260,1053600);
UPDATE ticket set org_id =1090261, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1090260,1053600))') where org_id in (1090260,1053600);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1092866. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1090260,1053600,1053605);
UPDATE ticket set org_id = 1092866, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1092864,1092865,1053605))') where org_id in (1092864,1092865,1053605);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1049368. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1049362,1049363);
UPDATE ticket set org_id = 1049368, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1092864,1092865,1053605))') where org_id in (1049362,1049363);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 769154. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (530536);
UPDATE ticket set org_id = 769154, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 530536))') where org_id in (530536);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1046635. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1046630);
UPDATE ticket set org_id = 1046635, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1046630))') where org_id in (1046630);
	                
update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1041805. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1041798);
UPDATE ticket set org_id = 1041805, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1041798))') where org_id in (1041798);
        
update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 893853. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (881095);
UPDATE ticket set org_id = 893853, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 881095))') where org_id in (881095);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 893853. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (881095);
UPDATE ticket set org_id = 893853, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 881095))') where org_id in (881095);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 893853. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1171455,1171454);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1160955. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1160954);
UPDATE ticket set org_id = 1160955, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1160954,1160955)') where org_id in (1160954);
                          
update organization set note_hd=concat_ws('-',note_hd,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1163873,1163874);
 
update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1068589. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1068588);
UPDATE ticket set org_id = 1068589, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1068588)') where org_id in (1068588);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 875299. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (875300);
UPDATE ticket set org_id = 875299, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 875300)') where org_id in (875300);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1088898. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1088247,1089833,1090105,1053603,1053695);
UPDATE ticket set org_id = 1088898, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1088247,1089833,1090105,1053603,1053695)') where org_id in (1088247,1089833,1090105,1053603,1053695);
	
update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1168161. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1168153,1168146);
UPDATE ticket set org_id = 1168161, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1168153,1168146)') where org_id in (1168153,1168146);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1164528. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1167473,1164529);
UPDATE ticket set org_id = 1164528, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1167473,1164529)') where org_id in (1167473,1164529);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1051814. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1050731,1050729);
UPDATE ticket set org_id = 1051814, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1050731,1050729)') where org_id in (1050731,1050729);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1163690,1163688);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1083585. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1083575);
UPDATE ticket set org_id = 1083585, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1083575)') where org_id in (1083575);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1160493. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1089159);
UPDATE ticket set org_id = 1160493, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1089159)') where org_id in (1089159);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1046853,1046915,1046861,1046858,1045894,1045898,1046544,1046856);
--------------------
update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1158720. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1045088,1045087,1049260);
UPDATE ticket set org_id = 1158720, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1045088,1045087,1049260)') where org_id in (1045088,1045087,1049260);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1090980. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1090978);
UPDATE ticket set org_id = 1090980, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1090978)') where org_id in (1090978);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1092762. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1092504);
UPDATE ticket set org_id = 1092762, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1092504)') where org_id in (1092504);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1106338. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1106229);
UPDATE ticket set org_id = 1106338, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1106229)') where org_id in (1106229);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1167426. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1167426);
UPDATE ticket set org_id = 1167426, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1167426)') where org_id in (1167426);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1004779. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1004779);
UPDATE ticket set org_id = 1004881, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1004779)') where org_id in (1004779);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1057665. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1058327);
UPDATE ticket set org_id = 1057665, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1058327)') where org_id in (1058327);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (900993,900989,900988);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1162510. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1162511);
UPDATE ticket set org_id = 1162510, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1162511)') where org_id in (1162511);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1087641. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1087631);
UPDATE ticket set org_id = 1087641, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1087631)') where org_id in (1087631);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1053457. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1053456);
UPDATE ticket set org_id = 1053457, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1053456)') where org_id in (1053456);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 876886. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (841865,1025250);
UPDATE ticket set org_id = 876886, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 841865,1025250)') where org_id in (841865,1025250);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1064023. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (889436);
UPDATE ticket set org_id = 1064023, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 889436)') where org_id in (889436);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1081090. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1081089,1081091);
UPDATE ticket set org_id = 1081090, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1081089,1081091)') where org_id in (1081089,1081091);

update organization set note_hd=concat_ws('-',note_hd,'ANAGRAFICA DI TIPO PRIVATI DUPLICATA DI 1007861. CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA.'), trashed_date = now() where org_id in (1007862);
UPDATE ticket set org_id = 1007861, note_internal_use_only=concat_ws('-',note_internal_use_only,'CANCELLAZIONE DA HD II LIVELLO PER IMPORT IN SCHEMA ANAGRAFICA. (ex org_id 1007862)') where org_id in (1007862);




