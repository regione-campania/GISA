insert into permission values (1396789108,12, 'link-matrix',true,false,false,false,'ACCESSO AL SOFTWARE MATRIX',10,true,true,false );
insert into permission values (1396789109,12, 'link-matrix-formule',true,false,false,false,'ACCESSO AL SOFTWARE MATRIX FORMULE',10,true,true,false );


insert into role_permission values(643298, 1,1396789108, true, false, false , false);
insert into role_permission values(643299, 31,1396789108, true, false, false , false);
insert into role_permission values(643300, 32,1396789108, true, false, false , false);
insert into role_permission values(643301, 1,1396789109, true, false, false , false);
insert into role_permission values(643302, 31,1396789109, true, false, false , false);
insert into role_permission values(643303, 32,1396789109, true, false, false , false);
insert into role_permission values(643304, 19,1396789108, true, false, false , false);
insert into role_permission values(643305, 21,1396789108, true, false, false , false);
insert into role_permission values(643306, 42,1396789108, true, false, false , false);
insert into role_permission values(643307, 43,1396789108, true, false, false , false);
insert into role_permission values(643308, 45,1396789108, true, false, false , false);
insert into role_permission values(643309, 46,1396789108, true, false, false , false);
insert into role_permission values(643310, 97,1396789108, true, false, false , false);
insert into role_permission values(643311, 98,1396789108, true, false, false , false);
insert into role_permission values(643312, 221,1396789108, true, false, false , false);
insert into role_permission values(643313, 222,1396789108, true, false, false , false);
insert into role_permission values(643314, 41,1396789108, true, false, false , false);
insert into role_permission values(643315, 59,1396789108, true, false, false , false);
insert into role_permission values(643316, 324,1396789108, true, false, false , false);
insert into role_permission values(643317, 324,1396789109, true, false, false , false);
insert into role_permission values(643318, 40,1396789108, true, false, false , false);
insert into role_permission values(643319, 40,1396789109, true, false, false , false);




select * from role where role_id in (select role_id from role_permission where permission_id = 1396789109 )






