update organization set name =regexp_replace(name, '\247', '&', 'g') where name!=cast(name::bytea as text);

update organization set name =regexp_replace(name, 'O''\202', '', 'g') where name!=cast(name::bytea as text);

update organization set name =regexp_replace(name, 'O''\214', 'I''', 'g') where name!=cast(name::bytea as text);



update organization set name =regexp_replace(name, '\371', 'u''', 'g') where name!=cast(name::bytea as text);

update organization set name =regexp_replace(name, 'O''\254', 'A''', 'g') where name!=cast(name::bytea as text);

update organization set name =regexp_replace(name, '\303\251', 'e''', 'g') where name!=cast(name::bytea as text);

update organization set name =regexp_replace(name, 'Z\303\203 \254', 'Zi''', 'g') where name!=cast(name::bytea as text);

update organization set name =regexp_replace(name, '\302', '', 'g') where name!=cast(name::bytea as text);

update organization set name =regexp_replace(name, 'O''\203\342\202\254', 'A''', 'g') where name!=cast(name::bytea as text);

update organization set name =regexp_replace(name, '\362', 'o''', 'g') where name!=cast(name::bytea as text);

update organization set name= regexp_replace(name, '\\', '/')  where name ilike '%\\%';

update organization set name =regexp_replace(name, '\340', 'a''', 'g') where name!=cast(name::bytea as text);

update organization set name =regexp_replace(name, '\303\262', 'o''', 'g') where name!=cast(name::bytea as text);

update organization set name =regexp_replace(name, '\357\277\275', 'a''', 'g') where name!=cast(name::bytea as text);

update organization set name =regexp_replace(name, '\354', 'i''', 'g') where name!=cast(name::bytea as text);

update organization set name =regexp_replace(name, '\303\271', 'u''', 'g') where name!=cast(name::bytea as text);

update organization set name =regexp_replace(name, '\012', '', 'g');

update organization set name =regexp_replace(name, '\303\200', 'A''', 'g') where name!=cast(name::bytea as text);

update organization set name =regexp_replace(name, '\302\240', '', 'g') where name!=cast(name::bytea as text);

update organization set name =regexp_replace(name, '\303\240', 'a''', 'g') where name!=cast(name::bytea as text);

update organization set name =regexp_replace(name, '\303\250', 'e''', 'g') where name!=cast(name::bytea as text);
 
update organization set name  =regexp_replace(name, '\350', 'e''', 'g') where name!=cast(name::bytea as text);

update organization set name =regexp_replace(name, '\303\250', 'e''', 'g') where name!=cast(name::bytea as text);

update organization set name =regexp_replace(name, '\342\200\235', 'e''', 'g') where name!=cast(name::bytea as text);

update organization set name =regexp_replace(name, '\342\200\231', 'A''', 'g') where name!=cast(name::bytea as text);

update organization set name =regexp_replace(name, '\303\210', 'E''', 'g') where name!=cast(name::bytea as text);

update organization set name =regexp_replace(name, '\226', '', 'g') where name!=cast(name::bytea as text);

update organization set name =regexp_replace(name, '\015', '', 'g') where name!=cast(name::bytea as text);

update organization set name =regexp_replace(name, '\222', '''', 'g') where name!=cast(name::bytea as text);

update organization set name =regexp_replace(name, '\316\217', '', 'g') where name!=cast(name::bytea as text);

update organization set name =regexp_replace(name, '\342\200\234', '"', 'g') where name!=cast(name::bytea as text);

update organization set name =regexp_replace(name, '\342\200\235', '"', 'g') where name!=cast(name::bytea as text);

update organization set name =regexp_replace(name, '\303\231', 'U''', 'g') where name!=cast(name::bytea as text);

update organization set name =regexp_replace(name, '\260', '.', 'g') where name!=cast(name::bytea as text);

update organization set name =regexp_replace(name, '\342\200\223', '', 'g') where name!=cast(name::bytea as text);

update organization set name =regexp_replace(name, 'O''\242\342\202\254\342\204\242', '''', 'g') where name!=cast(name::bytea as text);

update organization set name =regexp_replace(name, ' \011', '', 'g') where name!=cast(name::bytea as text);

update organization set name =regexp_replace(name, ' \240', '', 'g') where name!=cast(name::bytea as text);

update organization set name =regexp_replace(name, ' \243', '', 'g') where name!=cast(name::bytea as text);