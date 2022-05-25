select ind.id, concat(desc_via, ', ' , c.nome) 
from gestori_acque_indirizzi ind , 
comuni1 c ,
gestori_acque_punti_prelievo pp
where  (ind.latitudine < 39.988475  or ind.latitudine > 41.503754 or ind.longitudine < 13.7563172 or ind.longitudine > 15.8032837) and 
       ind.id_comune = c.id and
       pp.id_indirizzo = ind.id 

       

update gestori_acque_indirizzi set latitudine =	40.9285635	, longitudine =	14.2032077	       where id =        	3135	;
update gestori_acque_indirizzi set latitudine =	41.2408126	, longitudine =	13.9073173		where id = 3180	;
update gestori_acque_indirizzi set latitudine =	41.2408126	, longitudine =	13.9073173		where id = 3181	;
update gestori_acque_indirizzi set latitudine =	40.6471704	, longitudine =	14.7012585		where id = 3465	;
update gestori_acque_indirizzi set latitudine =	40.7006838	, longitudine =	14.504309		where id = 4281	;
update gestori_acque_indirizzi set latitudine =	41.2997934	, longitudine =	15.0864334		where id = 5795	;