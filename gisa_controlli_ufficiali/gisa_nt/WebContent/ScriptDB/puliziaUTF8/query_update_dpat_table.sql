select description::bytea, description, * from dpat_indicatore_  where description!=cast(description::bytea as text);

update dpat_indicatore_ set description =regexp_replace(description, '\012', '', 'g') --where description!=cast(description::bytea as text);

update dpat_indicatore_ set description =regexp_replace(description, 'à', 'a''', 'g') where description!=cast(description::bytea as text);

update dpat_indicatore_ set description =regexp_replace(description, '\303\200', 'A''', 'g') where description!=cast(description::bytea as text);

update dpat_indicatore_ set description =regexp_replace(description, '\302\240', '', 'g') where description!=cast(description::bytea as text);

update dpat_indicatore_ set description =regexp_replace(description, '\303\240', 'a''', 'g') where description!=cast(description::bytea as text);

update dpat_indicatore_ set description =regexp_replace(description, '\303\250', 'e''', 'g') where description!=cast(description::bytea as text);
 
update dpat_indicatore_ set description  =regexp_replace(description, '\350', 'e''', 'g') where description!=cast(description::bytea as text);

update dpat_indicatore_ set description =regexp_replace(description, '\303\250', 'e''', 'g') where description!=cast(description::bytea as text);

update dpat_indicatore_ set description =regexp_replace(description, '\342\200\235', 'e''', 'g') where description!=cast(description::bytea as text);

update dpat_indicatore_ set description =regexp_replace(description, '\342\200\231', 'A''', 'g') where description!=cast(description::bytea as text);

update dpat_indicatore_ set description =regexp_replace(description, '\303\210', 'E''', 'g') where description!=cast(description::bytea as text);

update dpat_indicatore_ set description =regexp_replace(description, '\226', '', 'g') where description!=cast(description::bytea as text);

update dpat_indicatore_ set description =regexp_replace(description, '\015', '', 'g') where description!=cast(description::bytea as text);

update dpat_indicatore_ set description =regexp_replace(description, '\222', '''', 'g') where description!=cast(description::bytea as text);

update dpat_indicatore_ set description =regexp_replace(description, '\316\217', '', 'g') where description!=cast(description::bytea as text);

update dpat_indicatore_ set description =regexp_replace(description, '\342\200\234', '"', 'g') where description!=cast(description::bytea as text);

update dpat_indicatore_ set description =regexp_replace(description, '\342\200\235', '"', 'g') where description!=cast(description::bytea as text);

update dpat_indicatore_ set description =regexp_replace(description, '\303\231', 'U''', 'g') where description!=cast(description::bytea as text);

update dpat_indicatore_ set description =regexp_replace(description, '\342\211\245', '>=', 'g') where description!=cast(description::bytea as text);

update dpat_indicatore_ set description =regexp_replace(description, '30\302\260', '40ima', 'g') where description!=cast(description::bytea as text);

update dpat_indicatore_ set description =regexp_replace(description, '45\302\260', '45ima', 'g') where description!=cast(description::bytea as text);


update dpat_piano_ set description =regexp_replace(description, '\012', '', 'g') --where description!=cast(description::bytea as text);

update dpat_piano_ set description =regexp_replace(description, 'à', 'a''', 'g') where description!=cast(description::bytea as text);

update dpat_piano_ set description =regexp_replace(description, '\303\200', 'A''', 'g') where description!=cast(description::bytea as text);

update dpat_piano_ set description =regexp_replace(description, '\302\240', '', 'g') where description!=cast(description::bytea as text);

update dpat_piano_ set description =regexp_replace(description, '\303\240', 'a''', 'g') where description!=cast(description::bytea as text);

update dpat_piano_ set description =regexp_replace(description, '\303\250', 'e''', 'g') where description!=cast(description::bytea as text);
 
update dpat_piano_ set description  =regexp_replace(description, '\350', 'e''', 'g') where description!=cast(description::bytea as text);

update dpat_piano_ set description =regexp_replace(description, '\303\250', 'e''', 'g') where description!=cast(description::bytea as text);

update dpat_piano_ set description =regexp_replace(description, '\342\200\235', 'e''', 'g') where description!=cast(description::bytea as text);

update dpat_piano_ set description =regexp_replace(description, '\342\200\231', 'A''', 'g') where description!=cast(description::bytea as text);

update dpat_piano_ set description =regexp_replace(description, '\303\210', 'E''', 'g') where description!=cast(description::bytea as text);

update dpat_piano_ set description =regexp_replace(description, '\226', '', 'g') where description!=cast(description::bytea as text);

update dpat_piano_ set description =regexp_replace(description, '\015', '', 'g') where description!=cast(description::bytea as text);

update dpat_piano_ set description =regexp_replace(description, '\222', '''', 'g') where description!=cast(description::bytea as text);

update dpat_piano_ set description =regexp_replace(description, '\316\217', '', 'g') where description!=cast(description::bytea as text);

update dpat_piano_ set description =regexp_replace(description, '\342\200\234', '"', 'g') where description!=cast(description::bytea as text);

update dpat_piano_ set description =regexp_replace(description, '\342\200\235', '"', 'g') where description!=cast(description::bytea as text);

update dpat_piano_ set description =regexp_replace(description, '\303\231', 'U''', 'g') where description!=cast(description::bytea as text);

update dpat_piano_ set description =regexp_replace(description, '\342\211\245', '>=', 'g') where description!=cast(description::bytea as text);

update dpat_piano_ set description =regexp_replace(description, '30\302\260', '40ima', 'g') where description!=cast(description::bytea as text);

update dpat_piano_ set description =regexp_replace(description, '45\302\260', '45ima', 'g') where description!=cast(description::bytea as text);


update dpat_attivita_ set description =regexp_replace(description, '\012', '', 'g'); --where description!=cast(description::bytea as text);

update dpat_attivita_ set description =regexp_replace(description, 'à', 'a''', 'g') where description!=cast(description::bytea as text);

update dpat_attivita_ set description =regexp_replace(description, '\303\200', 'A''', 'g') where description!=cast(description::bytea as text);

update dpat_attivita_ set description =regexp_replace(description, '\302\240', '', 'g') where description!=cast(description::bytea as text);

update dpat_attivita_ set description =regexp_replace(description, '\303\240', 'a''', 'g') where description!=cast(description::bytea as text);

update dpat_attivita_ set description =regexp_replace(description, '\303\250', 'e''', 'g') where description!=cast(description::bytea as text);
 
update dpat_attivita_ set description  =regexp_replace(description, '\350', 'e''', 'g') where description!=cast(description::bytea as text);

update dpat_attivita_ set description =regexp_replace(description, '\303\250', 'e''', 'g') where description!=cast(description::bytea as text);

update dpat_attivita_ set description =regexp_replace(description, '\342\200\235', 'e''', 'g') where description!=cast(description::bytea as text);

update dpat_attivita_ set description =regexp_replace(description, '\342\200\231', 'A''', 'g') where description!=cast(description::bytea as text);

update dpat_attivita_ set description =regexp_replace(description, '\303\210', 'E''', 'g') where description!=cast(description::bytea as text);

update dpat_attivita_ set description =regexp_replace(description, '\226', '', 'g') where description!=cast(description::bytea as text);

update dpat_attivita_ set description =regexp_replace(description, '\015', '', 'g') where description!=cast(description::bytea as text);

update dpat_attivita_ set description =regexp_replace(description, '\222', '''', 'g') where description!=cast(description::bytea as text);

update dpat_attivita_ set description =regexp_replace(description, '\316\217', '', 'g') where description!=cast(description::bytea as text);

update dpat_attivita_ set description =regexp_replace(description, '\342\200\234', '"', 'g') where description!=cast(description::bytea as text);

update dpat_attivita_ set description =regexp_replace(description, '\342\200\235', '"', 'g') where description!=cast(description::bytea as text);

update dpat_attivita_ set description =regexp_replace(description, '\303\231', 'U''', 'g') where description!=cast(description::bytea as text);

update dpat_attivita_ set description =regexp_replace(description, '\342\211\245', '>=', 'g') where description!=cast(description::bytea as text);

update dpat_attivita_ set description =regexp_replace(description, '30\302\260', '40ima', 'g') where description!=cast(description::bytea as text);

update dpat_attivita_ set description =regexp_replace(description, '45\302\260', '45ima', 'g') where description!=cast(description::bytea as text);


update strutture_asl set descrizione_lunga =regexp_replace(descrizione_lunga, '\012', '', 'g'); --where descrizione_lunga!=cast(descrizione_lunga::bytea as text);

update strutture_asl set descrizione_lunga =regexp_replace(descrizione_lunga, 'à', 'a''', 'g') where descrizione_lunga!=cast(descrizione_lunga::bytea as text);

update strutture_asl set descrizione_lunga =regexp_replace(descrizione_lunga, '\303\200', 'A''', 'g') where descrizione_lunga!=cast(descrizione_lunga::bytea as text);

update strutture_asl set descrizione_lunga =regexp_replace(descrizione_lunga, '\302\240', '', 'g') where descrizione_lunga!=cast(descrizione_lunga::bytea as text);

update strutture_asl set descrizione_lunga =regexp_replace(descrizione_lunga, '\303\240', 'a''', 'g') where descrizione_lunga!=cast(descrizione_lunga::bytea as text);

update strutture_asl set descrizione_lunga =regexp_replace(descrizione_lunga, '\303\250', 'e''', 'g') where descrizione_lunga!=cast(descrizione_lunga::bytea as text);
 
update strutture_asl set descrizione_lunga  =regexp_replace(descrizione_lunga, '\350', 'e''', 'g') where descrizione_lunga!=cast(descrizione_lunga::bytea as text);

update strutture_asl set descrizione_lunga =regexp_replace(descrizione_lunga, '\303\250', 'e''', 'g') where descrizione_lunga!=cast(descrizione_lunga::bytea as text);

update strutture_asl set descrizione_lunga =regexp_replace(descrizione_lunga, '\342\200\235', 'e''', 'g') where descrizione_lunga!=cast(descrizione_lunga::bytea as text);

update strutture_asl set descrizione_lunga =regexp_replace(descrizione_lunga, '\342\200\231', 'A''', 'g') where descrizione_lunga!=cast(descrizione_lunga::bytea as text);

update strutture_asl set descrizione_lunga =regexp_replace(descrizione_lunga, '\303\210', 'E''', 'g') where descrizione_lunga!=cast(descrizione_lunga::bytea as text);

update strutture_asl set descrizione_lunga =regexp_replace(descrizione_lunga, '\226', '', 'g') where descrizione_lunga!=cast(descrizione_lunga::bytea as text);

update strutture_asl set descrizione_lunga =regexp_replace(descrizione_lunga, '\015', '', 'g') where descrizione_lunga!=cast(descrizione_lunga::bytea as text);

update strutture_asl set descrizione_lunga =regexp_replace(descrizione_lunga, '\222', '''', 'g') where descrizione_lunga!=cast(descrizione_lunga::bytea as text);

update strutture_asl set descrizione_lunga =regexp_replace(descrizione_lunga, '\316\217', '', 'g') where descrizione_lunga!=cast(descrizione_lunga::bytea as text);

update strutture_asl set descrizione_lunga =regexp_replace(descrizione_lunga, '\342\200\234', '"', 'g') where descrizione_lunga!=cast(descrizione_lunga::bytea as text);

update strutture_asl set descrizione_lunga =regexp_replace(descrizione_lunga, '\342\200\235', '"', 'g') where descrizione_lunga!=cast(descrizione_lunga::bytea as text);

update strutture_asl set descrizione_lunga =regexp_replace(descrizione_lunga, '\303\231', 'U''', 'g') where descrizione_lunga!=cast(descrizione_lunga::bytea as text);

update strutture_asl set descrizione_lunga =regexp_replace(descrizione_lunga, '\342\211\245', '>=', 'g') where descrizione_lunga!=cast(descrizione_lunga::bytea as text);

update strutture_asl set descrizione_lunga =regexp_replace(descrizione_lunga, '30\302\260', '40ima', 'g') where descrizione_lunga!=cast(descrizione_lunga::bytea as text);

update strutture_asl set descrizione_lunga =regexp_replace(descrizione_lunga, '45\302\260', '45ima', 'g') where descrizione_lunga!=cast(descrizione_lunga::bytea as text);
