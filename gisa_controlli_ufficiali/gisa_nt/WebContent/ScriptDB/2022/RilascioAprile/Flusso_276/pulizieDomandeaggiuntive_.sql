update chk_bns_domande_v6 set descrizione='LIBERTA'' DI MOVIMENTO' where descrizione ilike '%di movimento%'
update chk_bns_domande_v6 set sottotitolo=replace (sottotitolo, 'ma''/capo', 'mq/capo')
update chk_bns_domande_v6 set sottotitolo=replace (sottotitolo, 'ma''', 'mq')
update chk_bns_domande_v6 set quesito=replace (quesito, 'a'' disponibile', 'e'' disponibile')
update chk_bns_domande_v6 set quesito=replace (quesito, 'a'' presente', 'e'' presente')
update chk_bns_domande_v6 set sottotitolo=replace (sottotitolo, 'a'' presente', 'e'' presente')
update chk_bns_domande_v6 set quesito=replace (quesito, 'a'' sufficiente', 'e'' sufficiente')
update chk_bns_domande_v6 set sottotitolo=replace (sottotitolo, 'a'' sufficiente', 'e'' sufficiente')
update chk_bns_domande_v6 set quesito=replace (quesito, 'a'' tale', 'e'' tale')
update chk_bns_domande_v6 set quesito=replace (quesito, 'a'' regolarmente', 'e'' regolarmente')
