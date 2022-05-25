--Riabilitazione utenti guardie zoofile
update guc_ruoli set trashed = false, note = concat(note, '. Settata trashed = false per riattivare gli account guarie zoofile come da mail di Avallone con oggetto ''accesso guardie zoofile'' del 23/07/2019') 
where trashed  and ruolo_integer = 329 and (note ilike '%Settata data_scadenza%per disabilitare temporaneamente gli account guarie zoofile in attesa di rivedere%');

update guc_utenti_ set enabled = true, note_internal_use_only_hd = concat(note_internal_use_only_hd, '. Settata enabled = true per riabilitare gli account guarie zoofile come da mail di Avallone con oggetto ''accesso guardie zoofile'' del 23/07/2019'), 
data_scadenza = null
where id in (
select id_utente from guc_ruoli where note ilike '%Settata trashed = %');

update access_ext_ set enabled = true, note_hd = concat(note_hd, '. Settata enabled = true per riabilitare gli account guarie zoofile come da mail di Avallone con oggetto ''accesso guardie zoofile'' del 23/07/2019'), 
data_scadenza = null
where (enabled is false or  data_scadenza is not null) and role_id = 329 and password <> '';


--Utenti con ultimo accesso
update access_ext_ set password= md5('algatto'), last_login = now() where username ilike '%algatto%';
select last_login , last_interaction_time, username, contact_ext_.namefirst, contact_ext_.namelast from access_ext_
left join contact_ext_  on access_ext_.user_id = contact_ext_.user_id or contact_ext_.contact_id = access_ext_.contact_id
where  access_ext_.note_hd ilike '%Settata data_scadenza%per disabilitare temporaneamente gli account guarie zoofile in attesa di rivedere%' order by 1


--PERMESSI SOLO SU IUV
--Permessi attuali
select 'update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = ' || r_p.id || ';' , r_p.id as id_r_p, r_p.permission_id,r_p.role_id,p.permission, r_p.role_view, r_p.role_add, r_p.role_edit, r_p.role_delete 
from role_permission_ext r_p, permission_ext p 
where r_p.role_id = 329 and r_p.permission_id = p.permission_id and p.enabled
order by 5

--Riabilitazione
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015202;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015319;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015235;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015237;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015314;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015242;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015241;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015236;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015057;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015292;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015312;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015332;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015295;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015060;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015350;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015353;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015293;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015296;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015344;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015106;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015104;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015351;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015059;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015294;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015287;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015327;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015284;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015288;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015283;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015285;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015281;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015282;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015286;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015163;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015143;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015144;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015145;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015223;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015050;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015226;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015341;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015230;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015231;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015227;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015310;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015232;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015228;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015225;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015233;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015210;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015279;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015272;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015277;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015275;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015273;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015208;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015346;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015311;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015165;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015347;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015243;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015325;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015320;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015247;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015251;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015245;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015342;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015244;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015329;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015137;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015140;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015089;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015141;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015120;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015138;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015133;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015052;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015135;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015201;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015322;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015269;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015267;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015263;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015270;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015264;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015265;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015112;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015338;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015115;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015114;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015116;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015117;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015111;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015298;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015317;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015211;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015207;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015209;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015205;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015308;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015212;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015206;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015303;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015076;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015336;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015086;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015085;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015080;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015071;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015087;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015081;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015083;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015088;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015291;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015074;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015328;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015128;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015122;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015127;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015113;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015130;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015107;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015254;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015200;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015321;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015343;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015261;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015259;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015257;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015260;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015255;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015354;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015339;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015300;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015301;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015103;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015142;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015082;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015066;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015139;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015197;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015148;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015051;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015330;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015154;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015150;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015155;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015151;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015152;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015153;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015187;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015214;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015213;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015318;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015222;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015309;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015220;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015217;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015219;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015215;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015188;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015189;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015316;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015196;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015195;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015192;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015194;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015191;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015193;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015190;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015079;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015097;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015136;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015105;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015131;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015118;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015132;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015134;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015129;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015302;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015278;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015326;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015274;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015266;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015271;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015268;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015262;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015276;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015203;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015158;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015355;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015331;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015345;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015070;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015077;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015094;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015058;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015290;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015084;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015199;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015990;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10016122;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10016155;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10016089;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10016023;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10016056;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015957;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015173;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015174;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015315;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015177;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015176;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015305;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015178;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015179;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015175;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015166;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015167;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015313;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015169;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015172;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015171;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015168;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015170;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015304;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015185;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015186;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015183;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015306;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015184;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015182;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015180;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015181;
update role_permission_ext set role_edit = false, role_delete = false, role_add = false, role_view = false where id = 10015110;
update role_permission_ext set role_add = true where permission_id = 400 and role_id = 329;


--ReloadUtenti

--Creazione ruolo guardia zoofile da corpo forestale
select * from role where role ilike '%zoofil%'
select 'insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,'|| permission_id || ',' ||  role_view ||',' ||  role_add ||',' ||  role_edit ||',' ||  role_delete ||',' ||  role_offline_view ||',' ||  role_offline_add ||',' ||  role_offline_edit ||',' ||  role_offline_delete ||',now(),now());', * from role_permission where role_id = 33

insert into role(role,enteredby,entered,modifiedby,enabled,role_type) values('Guardie zoofile',709,now(),709,true,0);


insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,94,true,false,false,false,false,false,false,false,now(),now());;
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,138,true,false,false,false,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,154,true,false,false,false,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,160,true,false,false,false,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,140,true,false,false,false,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,42,true,true,false,false,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,50,true,true,false,false,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,134,true,false,false,false,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,135,true,true,true,true,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,130,true,false,false,false,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,131,true,false,false,false,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,98,true,false,false,false,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,102,false,true,true,true,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,101,true,false,false,false,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,105,true,false,true,false,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,104,true,false,false,false,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,103,true,false,true,false,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,99,true,false,false,false,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,1,true,false,false,false,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,7,false,false,true,false,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,16,true,true,true,true,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,24,true,true,true,true,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,2,true,false,false,false,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,12,true,false,false,false,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,73,true,false,false,false,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,74,true,false,false,false,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,97,true,false,false,false,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,96,true,false,false,false,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,95,true,false,false,false,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,137,true,true,false,false,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,571,true,false,false,false,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,589,true,true,false,false,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,152,true,false,false,false,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,559,true,false,false,false,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,560,true,false,false,false,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,561,true,false,false,false,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,564,true,false,false,false,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,563,false,false,false,false,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,592,true,true,false,false,false,false,false,false,now(),now());
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values(35,593,true,true,false,false,false,false,false,false,now(),now());
