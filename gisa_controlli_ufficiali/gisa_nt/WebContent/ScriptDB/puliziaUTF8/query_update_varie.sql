update lookup_condizionalita_new  set description=regexp_replace(description, '\226', '-', 'g')


update lookup_piano_monitoraggio_ set description =regexp_replace(regexp_replace(description, 'à', 'a''', 'g'), '\012','')  where description!=cast(description::bytea as text);
 
update lookup_piano_monitoraggio_ set description =regexp_replace(regexp_replace(description, '\303\240', 'a''', 'g'), '\012','')  where description!=cast(description::bytea as text);
 
update lookup_piano_monitoraggio_ set description =regexp_replace(regexp_replace(description, '\303\250', 'e''', 'g'), '\012','')  where description!=cast(description::bytea as text);
 
update lookup_piano_monitoraggio_ set description  =regexp_replace(description, '\350', 'e''', 'g') where description!=cast(description::bytea as text);

update lookup_piano_monitoraggio_ set description =regexp_replace(regexp_replace(description, '\303\250', 'e''', 'g'), '\012','')  where description!=cast(description::bytea as text);

update lookup_piano_monitoraggio_ set description =regexp_replace(regexp_replace(description, '\342\200\235', 'e''', 'g'), '\012','')  where description!=cast(description::bytea as text);

update lookup_piano_monitoraggio_ set description =regexp_replace(regexp_replace(description, '\342\200\231', 'A''', 'g'), '\012','')  where description!=cast(description::bytea as text);

update lookup_piano_monitoraggio_ set description =regexp_replace(regexp_replace(description, '\303\210', 'E''', 'g'), '\012','')  where description!=cast(description::bytea as text);

update lookup_piano_monitoraggio_ set description =regexp_replace(regexp_replace(description, '\226', '', 'g'), '\012','')  where description!=cast(description::bytea as text);

update lookup_piano_monitoraggio_ set description =regexp_replace(regexp_replace(description, '\015', '', 'g'), '\012','')  where description!=cast(description::bytea as text);

update lookup_piano_monitoraggio_ set description =regexp_replace(regexp_replace(description, '\222', '''', 'g'), '\012','')  where description!=cast(description::bytea as text);

update lookup_piano_monitoraggio_ set description =regexp_replace(regexp_replace(description, '\316\217', '', 'g'), '\012','')  where description!=cast(description::bytea as text);

update lookup_piano_monitoraggio_ set description =regexp_replace(regexp_replace(description, '\342\200\234', '"', 'g'), '\012','')  where description!=cast(description::bytea as text);

update lookup_piano_monitoraggio_ set description =regexp_replace(regexp_replace(description, '\342\200\235', '"', 'g'), '\012','')  where description!=cast(description::bytea as text);

update lookup_piano_monitoraggio_ set description =regexp_replace(regexp_replace(description, '\303\231', 'U''', 'g'), '\012','')  where description!=cast(description::bytea as text);

update lookup_piano_monitoraggio_ set description =regexp_replace(regexp_replace(description, '\342\211\245', '>=', 'g'), '\012','')  where description!=cast(description::bytea as text);

update lookup_piano_monitoraggio_ set description =regexp_replace(regexp_replace(description, '30\302\260', '40ima', 'g'), '\012','')  where description!=cast(description::bytea as text);

update lookup_piano_monitoraggio_ set description =regexp_replace(regexp_replace(description, '45\302\260', '45ima', 'g'), '\012','')  where description!=cast(description::bytea as text);

update lookup_condizionalita  set description=regexp_replace(description, '\226', '-', 'g') 
update lookup_condizionalita set description =regexp_replace(regexp_replace(description, '\340', 'a''', 'g'), '\012','')  where description!=cast(description::bytea as text);
update lookup_condizionalita set description =regexp_replace(regexp_replace(description, '\222', '''', 'g'), '\012','')  where description!=cast(description::bytea as text);



update opu_operatore set ragione_sociale =regexp_replace(regexp_replace(ragione_sociale, '\303\250', 'e''', 'g'), '\012','') ; 

update opu_operatore set ragione_sociale =regexp_replace(regexp_replace(ragione_sociale, '\342\200\235', 'e''', 'g'), '\012','');  

update opu_operatore set ragione_sociale =regexp_replace(regexp_replace(ragione_sociale, '\342\200\231', 'A''', 'g'), '\012','') ; 

update opu_operatore set ragione_sociale =regexp_replace(regexp_replace(ragione_sociale, '\303\240', 'A''', 'g'), '\012','');  

update opu_operatore set ragione_sociale =regexp_replace(regexp_replace(ragione_sociale, '\303\262', 'a''', 'g'), '\012','') ;

update opu_operatore set ragione_sociale =regexp_replace(regexp_replace(ragione_sociale, '\303\210', 'E''', 'g'), '\012','') ;

update opu_operatore set ragione_sociale =regexp_replace(regexp_replace(ragione_sociale, '\226', '', 'g'), '\012','') ;

update opu_operatore set ragione_sociale =regexp_replace(regexp_replace(ragione_sociale, '\015', '', 'g'), '\012','');
update opu_operatore set ragione_sociale =regexp_replace(regexp_replace(ragione_sociale, '\222', '''', 'g'), '\012',''); 

update opu_operatore set ragione_sociale =regexp_replace(regexp_replace(ragione_sociale, '\316\217', '', 'g'), '\012',''); 

update opu_operatore set ragione_sociale =regexp_replace(regexp_replace(ragione_sociale, '\342\200\234', '"', 'g'), '\012',''); 

update opu_operatore set ragione_sociale =regexp_replace(regexp_replace(ragione_sociale, '\342\200\235', '"', 'g'), '\012','') ;

update opu_operatore set ragione_sociale =regexp_replace(regexp_replace(ragione_sociale, '\357\277\275', '"', 'g'), '\012',''); 

update opu_operatore set ragione_sociale =regexp_replace(regexp_replace(ragione_sociale, '\303\271', 'u''', 'g'), '\012',''); 

update opu_operatore set ragione_sociale =regexp_replace(regexp_replace(ragione_sociale, '*', '', 'g'), '\012',''); 

update opu_operatore set ragione_sociale =regexp_replace(regexp_replace(ragione_sociale, 'Anull', 'A''', 'g'), '\012',''); 

update opu_operatore set ragione_sociale =regexp_replace(regexp_replace(ragione_sociale, 'Unull', 'U''', 'g'), '\012','') ;
 

select id, regexp_replace(regexp_replace(ragione_sociale, '[^\040-\176]', '', 'g'),'\*','','g') from opu_operatore where regexp_replace(ragione_sociale, '[^\040-\176]', '*', 'g') like '%*%';
 
update opu_operatore  set ragione_sociale = regexp_replace(regexp_replace(ragione_sociale, '[^\040-\176]', '', 'g'),'\*','','g') where regexp_replace(ragione_sociale, '[^\040-\176]', '*', 'g') like '%*%';