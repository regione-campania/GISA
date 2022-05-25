




update lookup_toponimi  set enabled = true 
where upper(description) in (
'ANGIPORTO',
'ANGIPORTO DEI',
'BANCHINA',
'CALATA',
'CALATA DEI',
'CALATA DELLA',
'CAVALCAVIA',
'CAVONE',
'CAVONE DELLE',
'CONTRADA',
'CORSO',
'CUPA',
'CUPA DEGLI',
'CUPA DEL',
'CUPA DELLA',
'CUPA DELLE',
'CUPA VICINALE',
'CUPA VICINALE DEI',
'CUPA VICINALE DELL''',
'DISCESA',
'DISCESA DEL',
'EMICICLO',
'FONDACO',
'FONDACO DEL',
'FOSSATO',
'GALLERIA',
'GIARDINI',
'GRADINI',
'GRADINI DEI',
'GRADINI DEL',
'GRADINI DELLA',
'GRADINI DELLE',
'GRADONI',
'GRADONI DI',
'LARGHETTO',
'LARGO',
'LARGO DEI',
'LARGO DEL',
'LARGO DELLE',
'LARGO PRIVATO',
'LARGO PROPRIO DI',
'MOLO',
'MOLO DEL',
'PARCO',
'PENDINO',
'PENNINATA',
'PIAZZA',
'PIAZZA DEGLI',
'PIAZZA DEI',
'PIAZZA DEL',
'PIAZZA DELL''',
'PIAZZA DELLA',
'PIAZZALE',
'PIAZZETTA',
'PIAZZETTA DEI',
'PIAZZETTA DEL',
'PIAZZETTA DELLA',
'PIAZZETTA DI',
'PONTILE',
'PROLUNGAMENTO',
'PROLUNGAMENTO DI VIA',
'RAMPA',
'RAMPA DEL',
'RAMPE',
'RAMPE DEL',
'RAMPE DELLA',
'RAMPE DELLE',
'RIVIERA DI',
'RUA',
'SALITA',
'SALITA DEI',
'SALITA DEL',
'SALITA DELLA',
'SALITA DELLO',
'SALITA DI',
'SBARCATOIO',
'SCALA',
'SCALE',
'SCALETTA',
'STAZIONE',
'STRADA',
'STRADA COMUNALE',
'STRADA COMUNALE CUPA',
'STRADA COMUNALE CUPA DELL''',
'STRADA COMUNALE DEI',
'STRADA COMUNALE DEL',
'STRADA COMUNALE DELL''',
'STRADA COMUNALE DETTA',
'STRADA DEL',
'STRADA DELLA',
'STRADA DI',
'STRADA VICINALE',
'STRADA VICINALE CUPA',
'STRADA VICINALE CUPA DEL',
'STRADONE',
'STRETTOLA',
'SUPPORTICO',
'TONDO DI',
'TRAVERSA',
'TRAVERSA DELLA',
'TRAVERSA DELLE',
'TRAVERSA DI',
'TRAVERSA DI VIA NUOVA',
'TRAVERSA I',
'TRAVERSA I CUPA',
'TRAVERSA I CUPA DEL',
'TRAVERSA I DELL''',
'TRAVERSA I DI VIA',
'TRAVERSA II',
'TRAVERSA II CUPA',
'TRAVERSA II DETTA',
'TRAVERSA II DI VIA',
'TRAVERSA II DI VIA PROVINCIALE',
'TRAVERSA III',
'TRAVERSA III CUPA',
'TRAVERSA III DI VIA',
'TRAVERSA III PRIVATA',
'TRAVERSA II PRIVATA',
'TRAVERSA II PRIVATA DELL''',
'TRAVERSA II VECCHIA',
'TRAVERSA I PRIVATA',
'TRAVERSA IV',
'TRAVERSA IV CUPA',
'TRAVERSA IV DI CORSO',
'TRAVERSA IV DI VIA',
'TRAVERSA I VECCHIA',
'TRAVERSA PRIVATA',
'TRAVERSA PRIVATA DEI',
'TRAVERSA V',
'TRAVERSA V DI VIA',
'TRAVERSA VI',
'VALLONE DEI',
'VANELLA',
'VARCO',
'VARCO DEL',
'VIA',
'VIA A',
'VIA AL',
'VIA COMUNALE',
'VIA COMUNALE DEL',
'VIA COMUNALE DELLE',
'VIA COMUNALE VECCHIA DI',
'VIA DEGLI',
'VIA DEI',
'VIA DEL',
'VIA DELL''',
'VIA DELLA',
'VIA DELLE',
'VIA DELLO',
'VIA DETTA',
'VIA DI',
'VIA FUORI PORTA',
'VIA GRANDE DEGLI',
'VIA I',
'VIA II DETTA',
'VIA IL',
'VIA LA',
'VIA LE',
'VIALE',
'VIALE DEGLI',
'VIALE DEI',
'VIALE DEL',
'VIALE DELL''',
'VIALE DELLA',
'VIALE DELLE',
'VIALE DELLO',
'VIALE DI',
'VIALE II',
'VIALE IL',
'VIALE PRIVATO',
'VIA NUOVA',
'VIA NUOVA DEL',
'VIA NUOVA DELLE',
'VIA NUOVA DETTA',
'VIA NUOVA DI',
'VIA PRIVATA',
'VIA PRIVATA DEL',
'VIA PRIVATA DELLE',
'VIA PROVINCIALE',
'VIA PROVINCIALE DELLE',
'VIA VECCHIA',
'VIA VECCHIA COMUNALE',
'VIA VECCHIA DI',
'VIA VICINALE',
'VIA VICINALE DEI',
'VIA VICINALE DELL''',
'VIA VICINALE PER',
'VICO',
'VICO DEGLI',
'VICO DEI',
'VICO DEL',
'VICO DELLA',
'VICO DELLE',
'VICO DELLO',
'VICO DETTO',
'VICO DI',
'VICO I',
'VICO I DEI',
'VICO I DELLA',
'VICO II',
'VICO II DEI',
'VICO II DEL',
'VICO II DELLA',
'VICO III',
'VICO III DEL',
'VICO IV',
'VICO IX',
'VICOLETTO',
'VICOLETTO DEI',
'VICOLETTO DEL',
'VICOLETTO DELLA',
'VICOLETTO DELLE',
'VICOLETTO DETTO',
'VICOLETTO I',
'VICOLETTO I DEL',
'VICOLETTO I DELLE',
'VICOLETTO II',
'VICOLETTO II DEL',
'VICOLETTO II DELLE',
'VICOLETTO III',
'VICOLETTO III DEL',
'VICOLETTO IV',
'VICOLETTO PRIVATO',
'VICO LUNGO',
'VICO LUNGO DEL',
'VICO NUOVO DELLA',
'VICO ROTTO A',
'VICO STORTO',
'VICO STORTO DEL',
'VICO STRETTO',
'VICO VI',
'VICO VII',
'VICO VIII',
'VICO X',
'VIOTTOLO'
) and
enabled is false















select max(code) from lookup_toponimi 
ALTER SEQUENCE lookup_toponimi_code_seq RESTART with 216


insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'TRAVERSA II PRIVATA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'SALITA DELLA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICOLETTO DEL', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'PIAZZA DELL''', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'STRADA DELLA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'TRAVERSA II', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIA A', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIALE II', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'STRADA COMUNALE DELL''', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIA NUOVA DI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIA PRIVATA DEL', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICOLETTO DELLE', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'SALITA DEI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'TRAVERSA IV DI CORSO', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIALE DELLA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'GRADINI DEI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'STRADA COMUNALE CUPA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'FONDACO DEL', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICO II', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'GRADINI DELLE', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICOLETTO III', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'GRADONI DI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICO STRETTO', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'TRAVERSA III CUPA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIA VICINALE PER', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'CUPA VICINALE DELL''', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'SALITA DEL', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'RAMPE DELLE', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIA VICINALE DEI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIA VECCHIA DI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICOLETTO DEI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'CUPA VICINALE DEI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VARCO DEL', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'ANGIPORTO DEI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICO STORTO DEL', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICO IX', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'CAVONE DELLE', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICOLETTO II DEL', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'RIVIERA DI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICO III DEL', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'RAMPA DEL', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIA COMUNALE VECCHIA DI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIALE DEL', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICOLETTO DELLA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICO DETTO', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIA AL', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICO VIII', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICOLETTO IV', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIA DEI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'SCALETTA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'STRADA VICINALE CUPA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'STRADA COMUNALE DETTA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'GRADINI DEL', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICO X', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIA NUOVA DELLE', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'TRAVERSA PRIVATA DEI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIALE DELLE', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'SALITA DI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICO DEL', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'TRAVERSA IV DI VIA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICO III', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'TRAVERSA I VECCHIA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'TRAVERSA V DI VIA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICO II DEI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'TRAVERSA II DI VIA PROVINCIALE', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIA DELLO', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'FOSSATO', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'LARGO PRIVATO', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICO II DEL', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'SALITA DELLO', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'PROLUNGAMENTO DI VIA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICO I DEI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'CUPA DELLA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'TRAVERSA III', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'LARGO DELLE', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'TRAVERSA DELLA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICO I DELLA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'PIAZZA DEI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'TRAVERSA II CUPA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'PIAZZETTA DELLA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'TRAVERSA V', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIA I', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIA DELLE', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIA DELLA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'STRADA COMUNALE DEI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIA NUOVA DETTA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'TRAVERSA I CUPA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'PIAZZA DEL', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VANELLA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICOLETTO III DEL', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICO DELLA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'TRAVERSA DI VIA NUOVA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'PIAZZETTA DI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIALE DELL''', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'STRADA DI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIOTTOLO',false,true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'TRAVERSA I DELL''', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIA LE', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'RAMPE DELLA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'MOLO DEL', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICO NUOVO DELLA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'TRAVERSA I DI VIA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'CUPA DELLE', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICO I', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'TONDO DI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'RAMPE DEL', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICO VII', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIA PROVINCIALE DELLE', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'TRAVERSA VI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIA DETTA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIA IL', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'TRAVERSA III PRIVATA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'TRAVERSA II DI VIA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIA VICINALE DELL''', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIA DELL''', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VALLONE DEI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICO STORTO', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICOLETTO II DELLE', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'TRAVERSA DELLE', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'TRAVERSA II DETTA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICOLETTO I', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICO ROTTO A', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'TRAVERSA I PRIVATA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'PIAZZA DEGLI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'LARGO PROPRIO DI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'CUPA DEL', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'TRAVERSA DI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'STRADA COMUNALE DEL', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICOLETTO I DEL', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIALE DEGLI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIA COMUNALE DELLE', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIA FUORI PORTA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIA NUOVA DEL', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICO DELLE', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'PIAZZA DELLA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICOLETTO II', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'TRAVERSA IV', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIALE DELLO', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'CALATA DELLA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICOLETTO DETTO', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICO VI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICO IV', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIALE IL', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICO II DELLA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIA PRIVATA DELLE', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICO LUNGO DEL', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICO DEGLI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'PIAZZETTA DEI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'LARGO DEL', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICO DI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'STRADA COMUNALE CUPA DELL''', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIALE DI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIALE DEI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'CALATA DEI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICO DEI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIA COMUNALE DEL', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'TRAVERSA IV CUPA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'DISCESA DEL', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICO LUNGO', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICOLETTO I DELLE', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'STRADA DEL', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'TRAVERSA III DI VIA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'PIAZZETTA DEL', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIA DEGLI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIA GRANDE DEGLI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIA II DETTA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'GRADINI DELLA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIA DEL', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICOLETTO PRIVATO', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'CUPA DEGLI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIA DI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIA VECCHIA COMUNALE', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'STRADA VICINALE CUPA DEL', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'TRAVERSA II VECCHIA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VIA LA', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'TRAVERSA II PRIVATA DELL''', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'VICO DELLO', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'TRAVERSA I CUPA DEL', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'LARGO DEI', false, true);
insert into lookup_toponimi(code,description, default_item, enabled) values( nextval('lookup_toponimi_code_seq') , 'TRAVERSA I', false, true);








update anagrafica.lookup_toponimi  set enabled = true 
where upper(description) in (
'ANGIPORTO',
'ANGIPORTO DEI',
'BANCHINA',
'CALATA',
'CALATA DEI',
'CALATA DELLA',
'CAVALCAVIA',
'CAVONE',
'CAVONE DELLE',
'CONTRADA',
'CORSO',
'CUPA',
'CUPA DEGLI',
'CUPA DEL',
'CUPA DELLA',
'CUPA DELLE',
'CUPA VICINALE',
'CUPA VICINALE DEI',
'CUPA VICINALE DELL''',
'DISCESA',
'DISCESA DEL',
'EMICICLO',
'FONDACO',
'FONDACO DEL',
'FOSSATO',
'GALLERIA',
'GIARDINI',
'GRADINI',
'GRADINI DEI',
'GRADINI DEL',
'GRADINI DELLA',
'GRADINI DELLE',
'GRADONI',
'GRADONI DI',
'LARGHETTO',
'LARGO',
'LARGO DEI',
'LARGO DEL',
'LARGO DELLE',
'LARGO PRIVATO',
'LARGO PROPRIO DI',
'MOLO',
'MOLO DEL',
'PARCO',
'PENDINO',
'PENNINATA',
'PIAZZA',
'PIAZZA DEGLI',
'PIAZZA DEI',
'PIAZZA DEL',
'PIAZZA DELL''',
'PIAZZA DELLA',
'PIAZZALE',
'PIAZZETTA',
'PIAZZETTA DEI',
'PIAZZETTA DEL',
'PIAZZETTA DELLA',
'PIAZZETTA DI',
'PONTILE',
'PROLUNGAMENTO',
'PROLUNGAMENTO DI VIA',
'RAMPA',
'RAMPA DEL',
'RAMPE',
'RAMPE DEL',
'RAMPE DELLA',
'RAMPE DELLE',
'RIVIERA DI',
'RUA',
'SALITA',
'SALITA DEI',
'SALITA DEL',
'SALITA DELLA',
'SALITA DELLO',
'SALITA DI',
'SBARCATOIO',
'SCALA',
'SCALE',
'SCALETTA',
'STAZIONE',
'STRADA',
'STRADA COMUNALE',
'STRADA COMUNALE CUPA',
'STRADA COMUNALE CUPA DELL''',
'STRADA COMUNALE DEI',
'STRADA COMUNALE DEL',
'STRADA COMUNALE DELL''',
'STRADA COMUNALE DETTA',
'STRADA DEL',
'STRADA DELLA',
'STRADA DI',
'STRADA VICINALE',
'STRADA VICINALE CUPA',
'STRADA VICINALE CUPA DEL',
'STRADONE',
'STRETTOLA',
'SUPPORTICO',
'TONDO DI',
'TRAVERSA',
'TRAVERSA DELLA',
'TRAVERSA DELLE',
'TRAVERSA DI',
'TRAVERSA DI VIA NUOVA',
'TRAVERSA I',
'TRAVERSA I CUPA',
'TRAVERSA I CUPA DEL',
'TRAVERSA I DELL''',
'TRAVERSA I DI VIA',
'TRAVERSA II',
'TRAVERSA II CUPA',
'TRAVERSA II DETTA',
'TRAVERSA II DI VIA',
'TRAVERSA II DI VIA PROVINCIALE',
'TRAVERSA III',
'TRAVERSA III CUPA',
'TRAVERSA III DI VIA',
'TRAVERSA III PRIVATA',
'TRAVERSA II PRIVATA',
'TRAVERSA II PRIVATA DELL''',
'TRAVERSA II VECCHIA',
'TRAVERSA I PRIVATA',
'TRAVERSA IV',
'TRAVERSA IV CUPA',
'TRAVERSA IV DI CORSO',
'TRAVERSA IV DI VIA',
'TRAVERSA I VECCHIA',
'TRAVERSA PRIVATA',
'TRAVERSA PRIVATA DEI',
'TRAVERSA V',
'TRAVERSA V DI VIA',
'TRAVERSA VI',
'VALLONE DEI',
'VANELLA',
'VARCO',
'VARCO DEL',
'VIA',
'VIA A',
'VIA AL',
'VIA COMUNALE',
'VIA COMUNALE DEL',
'VIA COMUNALE DELLE',
'VIA COMUNALE VECCHIA DI',
'VIA DEGLI',
'VIA DEI',
'VIA DEL',
'VIA DELL''',
'VIA DELLA',
'VIA DELLE',
'VIA DELLO',
'VIA DETTA',
'VIA DI',
'VIA FUORI PORTA',
'VIA GRANDE DEGLI',
'VIA I',
'VIA II DETTA',
'VIA IL',
'VIA LA',
'VIA LE',
'VIALE',
'VIALE DEGLI',
'VIALE DEI',
'VIALE DEL',
'VIALE DELL''',
'VIALE DELLA',
'VIALE DELLE',
'VIALE DELLO',
'VIALE DI',
'VIALE II',
'VIALE IL',
'VIALE PRIVATO',
'VIA NUOVA',
'VIA NUOVA DEL',
'VIA NUOVA DELLE',
'VIA NUOVA DETTA',
'VIA NUOVA DI',
'VIA PRIVATA',
'VIA PRIVATA DEL',
'VIA PRIVATA DELLE',
'VIA PROVINCIALE',
'VIA PROVINCIALE DELLE',
'VIA VECCHIA',
'VIA VECCHIA COMUNALE',
'VIA VECCHIA DI',
'VIA VICINALE',
'VIA VICINALE DEI',
'VIA VICINALE DELL''',
'VIA VICINALE PER',
'VICO',
'VICO DEGLI',
'VICO DEI',
'VICO DEL',
'VICO DELLA',
'VICO DELLE',
'VICO DELLO',
'VICO DETTO',
'VICO DI',
'VICO I',
'VICO I DEI',
'VICO I DELLA',
'VICO II',
'VICO II DEI',
'VICO II DEL',
'VICO II DELLA',
'VICO III',
'VICO III DEL',
'VICO IV',
'VICO IX',
'VICOLETTO',
'VICOLETTO DEI',
'VICOLETTO DEL',
'VICOLETTO DELLA',
'VICOLETTO DELLE',
'VICOLETTO DETTO',
'VICOLETTO I',
'VICOLETTO I DEL',
'VICOLETTO I DELLE',
'VICOLETTO II',
'VICOLETTO II DEL',
'VICOLETTO II DELLE',
'VICOLETTO III',
'VICOLETTO III DEL',
'VICOLETTO IV',
'VICOLETTO PRIVATO',
'VICO LUNGO',
'VICO LUNGO DEL',
'VICO NUOVO DELLA',
'VICO ROTTO A',
'VICO STORTO',
'VICO STORTO DEL',
'VICO STRETTO',
'VICO VI',
'VICO VII',
'VICO VIII',
'VICO X',
'VIOTTOLO'
) and
enabled is false



select max(code) from anagrafica.lookup_toponimi 
ALTER SEQUENCE anagrafica.lookup_toponimi_code_seq RESTART with 216

insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'TRAVERSA II PRIVATA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'SALITA DELLA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICOLETTO DEL', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'PIAZZA DELL''', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'STRADA DELLA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'TRAVERSA II', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIA A', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIALE II', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'STRADA COMUNALE DELL''', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIA NUOVA DI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIA PRIVATA DEL', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICOLETTO DELLE', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'SALITA DEI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'TRAVERSA IV DI CORSO', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIALE DELLA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'GRADINI DEI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'STRADA COMUNALE CUPA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'FONDACO DEL', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICO II', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'GRADINI DELLE', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICOLETTO III', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'GRADONI DI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICO STRETTO', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'TRAVERSA III CUPA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIA VICINALE PER', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'CUPA VICINALE DELL''', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'SALITA DEL', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'RAMPE DELLE', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIA VICINALE DEI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIA VECCHIA DI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICOLETTO DEI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'CUPA VICINALE DEI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VARCO DEL', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'ANGIPORTO DEI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICO STORTO DEL', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICO IX', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'CAVONE DELLE', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICOLETTO II DEL', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'RIVIERA DI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICO III DEL', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'RAMPA DEL', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIA COMUNALE VECCHIA DI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIALE DEL', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICOLETTO DELLA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICO DETTO', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIA AL', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICO VIII', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICOLETTO IV', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIA DEI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'SCALETTA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'STRADA VICINALE CUPA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'STRADA COMUNALE DETTA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'GRADINI DEL', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICO X', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIA NUOVA DELLE', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'TRAVERSA PRIVATA DEI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIALE DELLE', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'SALITA DI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICO DEL', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'TRAVERSA IV DI VIA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICO III', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'TRAVERSA I VECCHIA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'TRAVERSA V DI VIA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICO II DEI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'TRAVERSA II DI VIA PROVINCIALE', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIA DELLO', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'FOSSATO', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'LARGO PRIVATO', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICO II DEL', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'SALITA DELLO', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'PROLUNGAMENTO DI VIA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICO I DEI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'CUPA DELLA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'TRAVERSA III', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'LARGO DELLE', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'TRAVERSA DELLA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICO I DELLA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'PIAZZA DEI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'TRAVERSA II CUPA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'PIAZZETTA DELLA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'TRAVERSA V', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIA I', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIA DELLE', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIA DELLA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'STRADA COMUNALE DEI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIA NUOVA DETTA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'TRAVERSA I CUPA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'PIAZZA DEL', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VANELLA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICOLETTO III DEL', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICO DELLA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'TRAVERSA DI VIA NUOVA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'PIAZZETTA DI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIALE DELL''', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'STRADA DI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIOTTOLO',false,true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'TRAVERSA I DELL''', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIA LE', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'RAMPE DELLA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'MOLO DEL', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICO NUOVO DELLA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'TRAVERSA I DI VIA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'CUPA DELLE', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICO I', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'TONDO DI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'RAMPE DEL', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICO VII', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIA PROVINCIALE DELLE', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'TRAVERSA VI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIA DETTA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIA IL', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'TRAVERSA III PRIVATA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'TRAVERSA II DI VIA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIA VICINALE DELL''', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIA DELL''', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VALLONE DEI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICO STORTO', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICOLETTO II DELLE', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'TRAVERSA DELLE', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'TRAVERSA II DETTA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICOLETTO I', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICO ROTTO A', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'TRAVERSA I PRIVATA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'PIAZZA DEGLI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'LARGO PROPRIO DI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'CUPA DEL', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'TRAVERSA DI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'STRADA COMUNALE DEL', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICOLETTO I DEL', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIALE DEGLI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIA COMUNALE DELLE', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIA FUORI PORTA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIA NUOVA DEL', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICO DELLE', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'PIAZZA DELLA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICOLETTO II', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'TRAVERSA IV', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIALE DELLO', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'CALATA DELLA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICOLETTO DETTO', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICO VI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICO IV', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIALE IL', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICO II DELLA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIA PRIVATA DELLE', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICO LUNGO DEL', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICO DEGLI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'PIAZZETTA DEI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'LARGO DEL', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICO DI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'STRADA COMUNALE CUPA DELL''', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIALE DI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIALE DEI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'CALATA DEI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICO DEI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIA COMUNALE DEL', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'TRAVERSA IV CUPA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'DISCESA DEL', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICO LUNGO', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICOLETTO I DELLE', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'STRADA DEL', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'TRAVERSA III DI VIA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'PIAZZETTA DEL', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIA DEGLI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIA GRANDE DEGLI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIA II DETTA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'GRADINI DELLA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIA DEL', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICOLETTO PRIVATO', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'CUPA DEGLI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIA DI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIA VECCHIA COMUNALE', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'STRADA VICINALE CUPA DEL', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'TRAVERSA II VECCHIA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VIA LA', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'TRAVERSA II PRIVATA DELL''', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'VICO DELLO', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'TRAVERSA I CUPA DEL', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'LARGO DEI', false, true);
insert into anagrafica.lookup_toponimi(code,description, default_item, enabled) values( nextval('anagrafica.lookup_toponimi_code_seq') , 'TRAVERSA I', false, true);




				
