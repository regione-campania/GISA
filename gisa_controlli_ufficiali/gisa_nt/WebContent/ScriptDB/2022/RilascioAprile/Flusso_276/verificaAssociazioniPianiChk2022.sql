
select * from lookup_chk_bns_mod  where enabled --suini 34
select alias_indicatore from dpat_indicatore_new  where id in (select id_indicatore from rel_indicatore_chk_bns  where id_lookup_chk_bns  =34)
A13_BA
a13_C
a13_B
A13_A

select * from lookup_chk_bns_mod  where enabled --vitelli 35
select alias_indicatore from dpat_indicatore_new  where id in (select id_indicatore from rel_indicatore_chk_bns  where id_lookup_chk_bns  =35) order by alias_indicatore
A13_BB
A13_BC
A13_BF
A13_D
a13_E
A13_EE
A13_EF
A13_EG
A13_IA
a13_JA
A13_KA


select * from lookup_chk_bns_mod  where enabled --bovini e buf 33
select alias_indicatore, id from dpat_indicatore_new  where id in (select id_indicatore from rel_indicatore_chk_bns  where id_lookup_chk_bns  =33) order by alias_indicatore
A13_BD
A13_BE
A13_F
a13_G
a13_H
A13_I
A13_J
A13_K

select * from lookup_chk_bns_mod  where enabled --broiler 37
select alias_indicatore, id from dpat_indicatore_new  where id in (select id_indicatore from rel_indicatore_chk_bns  where id_lookup_chk_bns  =37) order by alias_indicatore
A13_AE
a13_AF
a13_bk

select * from lookup_chk_bns_mod  where enabled --gallus 36
select alias_indicatore, id from dpat_indicatore_new  where id in (select id_indicatore from rel_indicatore_chk_bns  where id_lookup_chk_bns  =36) order by alias_indicatore
A13_AB
A13_AC
a13_bj

select * from lookup_chk_bns_mod  where enabled --altre specie 
select alias_indicatore, id from dpat_indicatore_new  where id in (select id_indicatore from rel_indicatore_chk_bns  where id_lookup_chk_bns  =38) order by alias_indicatore
A13_AH
A13_AI
A13_AQ
A13_AR
a13_AT
a13_AU
A13_AV
A13_BG
a13_bh
a13_bi
a13_bl
a13_bm
a13_bn
a13_bo
a13_bp
a13_bq
a13_br
a13_bs
a13_bt
a13_bu
A13_L
a13_m
A13_N
A13_o
A13_P
A13_Q
a13_r
A13_S
A13_T
A13_U
A13_V
A13_W
A13_x
A13_y
A13_Z






