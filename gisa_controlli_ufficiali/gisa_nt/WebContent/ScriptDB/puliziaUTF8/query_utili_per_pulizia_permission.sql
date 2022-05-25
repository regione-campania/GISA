--detect
select description,description::bytea, '->'|| regexp_replace(regexp_replace(description, 'à', 'a''', 'g'), '\012','')||'<-' from permission where description!=cast(description::bytea as text)
 
--correct
update permission set description =regexp_replace(regexp_replace(description, 'à', 'a''', 'g'), '\012','')||'<-'  where description!=cast(description::bytea as text)