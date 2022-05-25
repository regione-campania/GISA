select * from role Where description!=cast(description::bytea as text) OR  ROLE!=cast(ROLE::bytea as text) OR  ROLE_OLD!=cast(ROLE_OLD::bytea as text) OR NOTE!=cast(NOTE::bytea as text)
 
select NOTE,NOTE::BYTEA from role Where description!=cast(description::bytea as text) OR  ROLE!=cast(ROLE::bytea as text) OR  ROLE_OLD!=cast(ROLE_OLD::bytea as text) OR NOTE!=cast(NOTE::bytea as text)
select description,description::bytea from PERMISSION Where description!=cast(description::bytea as text)
 

update permission set description =regexp_replace(regexp_replace(description, 'à', 'a''', 'g'), '\012','')  where description!=cast(description::bytea as text);
 
update role set description =regexp_replace(regexp_replace(description, 'à', 'a''', 'g'), '\012','')  where description!=cast(description::bytea as text);
 
update role set description =regexp_replace(regexp_replace(description, '\303\240', 'a''', 'g'), '\012','')  where description!=cast(description::bytea as text);
update PERMISSION set description =regexp_replace(regexp_replace(description, '\303\240', 'a''', 'g'), '\012','')  where description!=cast(description::bytea as text);
 
update role set description =regexp_replace(regexp_replace(description, '\303\250', 'e''', 'g'), '\012','')  where description!=cast(description::bytea as text);
 
update role set NOTE =regexp_replace(description, '\350', 'e''', 'g') where NOTE!=cast(NOTE::bytea as text);