-- Chi: Bartolo Sansone
-- Cosa: Flusso 208 Gestione Invio ClassyFarm (Biosicurezza)
-- Quando: 19/10/21

-- Rinomino vecchia tabella delle risposte
alter table biosicurezza_risposte rename to biosicurezza_risposte_old;

-- Creo nuova tabella delle risposte
CREATE TABLE biosicurezza_risposte (id serial primary key, id_domanda integer, risposta text, tipo text, ordine integer, trashed_date timestamp without time zone, id_classyfarm integer);

-- Popolo risposte
select 'insert into biosicurezza_risposte (id_domanda, risposta, tipo, ordine) values ('||id||', ''Si'', ''radio'', 1);
insert into biosicurezza_risposte (id_domanda, risposta, tipo, ordine) values ('||id||', ''No'', ''radio'', 2);
insert into biosicurezza_risposte (id_domanda, risposta, tipo, ordine) values ('||id||', ''N/A'', ''radio'', 3);
insert into biosicurezza_risposte (id_domanda, risposta, tipo, ordine) values ('||id||', ''Note - Motivo NA'', ''textarea'', 4);' from biosicurezza_domande order by id asc;

-- Creo tabella istanze risposte
CREATE TABLE biosicurezza_istanze_risposte(id serial primary key, id_istanza integer, id_domanda integer, id_risposta integer, note text, trashed_date timestamp without time zone);

-- Importo vecchie risposte
insert into biosicurezza_istanze_risposte (id_istanza, id_domanda, id_risposta, note) 
select o.id_biosicurezza_istanze, o.id_biosicurezza_domande, r.id, null 
from biosicurezza_risposte_old o
left join biosicurezza_risposte r on r.id_domanda = o.id_biosicurezza_domande
where o.risposta = 'SI' and r.risposta = 'Si' and o.trashed_date is null;

insert into biosicurezza_istanze_risposte (id_istanza, id_domanda, id_risposta, note) 
select o.id_biosicurezza_istanze, o.id_biosicurezza_domande, r.id, null 
from biosicurezza_risposte_old o
left join biosicurezza_risposte r on r.id_domanda = o.id_biosicurezza_domande
where o.risposta = 'NO' and r.risposta = 'No' and o.trashed_date is null;

insert into biosicurezza_istanze_risposte (id_istanza, id_domanda, id_risposta, note) 
select o.id_biosicurezza_istanze, o.id_biosicurezza_domande, r.id, null 
from biosicurezza_risposte_old o
left join biosicurezza_risposte r on r.id_domanda = o.id_biosicurezza_domande
where o.risposta = 'NA' and r.risposta = 'N/A' and o.trashed_date is null;

insert into biosicurezza_istanze_risposte (id_istanza, id_domanda, id_risposta, note) 
select o.id_biosicurezza_istanze, o.id_biosicurezza_domande, r.id, o.motivo 
from biosicurezza_risposte_old o
left join biosicurezza_risposte r on r.id_domanda = o.id_biosicurezza_domande
where o.motivo <> '' and r.risposta = 'Note - Motivo NA' and o.trashed_date is null;

-- Query di verifica
select s.sezione, d.id, d.domanda, r.id, r.risposta, ir.note
from biosicurezza_istanze_risposte ir
left join biosicurezza_istanze i on i.id = ir.id_istanza
left join biosicurezza_domande d on d.id = ir.id_domanda
left join biosicurezza_sezioni s on s.id = d.id_sezione
left join biosicurezza_risposte r on r.id = ir.id_domanda
where ir.trashed_date is null and i.trashed_date is null and i.idcu = 1555654 order by d.ordine asc, r.ordine asc;

-- Creo e popolo lookup checklist classyfarm
CREATE TABLE lookup_chk_classyfarm_mod (code serial primary key, description text, default_item boolean default false, level integer default 0, enabled boolean default true, codice_gisa text, codice_classyfarm text);

insert into lookup_chk_classyfarm_mod(description, codice_gisa, codice_classyfarm)
values ('VALUTAZIONE DELLA BIOSICUREZZA: SUINI', 'biosicurezza-suini-03', '19');

-- Mapping domande

alter table biosicurezza_domande add column id_classyfarm integer;

update biosicurezza_domande set id_classyfarm =	603	 where ordine in (select substring('	1. Lazienda e dotata di unarea apposita, posta prima della barriera di entrata per la sosta dei veicoli del personale dellallevamento e/o visitatori? 	', 0, position('.' in '	1. Lazienda e dotata di unarea apposita, posta prima della barriera di entrata per la sosta dei veicoli del personale dellallevamento e/o visitatori? 	'))::integer);
update biosicurezza_domande set id_classyfarm =	692	 where ordine in (select substring('	2. Sono presenti e ben visibili allingresso cartelli di divieto di accesso per le persone non autorizzate? 	', 0, position('.' in '	2. Sono presenti e ben visibili allingresso cartelli di divieto di accesso per le persone non autorizzate? 	'))::integer);
update biosicurezza_domande set id_classyfarm =	718	 where ordine in (select substring('	3. Lazienda dispone di cancelli o sbarre idonee ad evitare lingresso diretto e non controllato di automezzi? 	', 0, position('.' in '	3. Lazienda dispone di cancelli o sbarre idonee ad evitare lingresso diretto e non controllato di automezzi? 	'))::integer);
update biosicurezza_domande set id_classyfarm =	694	 where ordine in (select substring('	4. Lazienda dispone di barriere fisiche o naturali che circoscrivono larea di stabulazione e di governo degli animali?	', 0, position('.' in '	4. Lazienda dispone di barriere fisiche o naturali che circoscrivono larea di stabulazione e di governo degli animali?	'))::integer);
update biosicurezza_domande set id_classyfarm =	695	 where ordine in (select substring('	5. Lazienda dispone di una zona filtro dotata di locali adibiti a spogliatoio del personale addetto al governo degli animali?	', 0, position('.' in '	5. Lazienda dispone di una zona filtro dotata di locali adibiti a spogliatoio del personale addetto al governo degli animali?	'))::integer);
update biosicurezza_domande set id_classyfarm =	696	 where ordine in (select substring('	6. Lazienda dispone di una zona filtro dotata di locali adibiti a spogliatoio dei visitatori?	', 0, position('.' in '	6. Lazienda dispone di una zona filtro dotata di locali adibiti a spogliatoio dei visitatori?	'))::integer);
update biosicurezza_domande set id_classyfarm =	697	 where ordine in (select substring('	7. Nella zona filtro, esiste una netta separazione tra la zona sporca e la zona pulita?	', 0, position('.' in '	7. Nella zona filtro, esiste una netta separazione tra la zona sporca e la zona pulita?	'))::integer);
update biosicurezza_domande set id_classyfarm =	698	 where ordine in (select substring('	8. Il personale/visitatori effettua la doccia prima dellingresso in azienda?	', 0, position('.' in '	8. Il personale/visitatori effettua la doccia prima dellingresso in azienda?	'))::integer);
update biosicurezza_domande set id_classyfarm =	699	 where ordine in (select substring('	9. Il personale/visitatori utilizza vestiario o tute e calzari  monouso che viene utilizzato esclusivamente in azienda? 	', 0, position('.' in '	9. Il personale/visitatori utilizza vestiario o tute e calzari  monouso che viene utilizzato esclusivamente in azienda? 	'))::integer);
update biosicurezza_domande set id_classyfarm =	700	 where ordine in (select substring('	10. Il personale/visitatori sono autorizzati a portare in azienda alimenti per uso personale? 	', 0, position('.' in '	10. Il personale/visitatori sono autorizzati a portare in azienda alimenti per uso personale? 	'))::integer);
update biosicurezza_domande set id_classyfarm =	701	 where ordine in (select substring('	11. E presente una planimetria, con capannoni e box numerati univocamente, mediante la quale sia possibile verificare il flusso unidirezionale degli spostamenti degli animali nellazienda e identificare i gruppi di animali?	', 0, position('.' in '	11. E presente una planimetria, con capannoni e box numerati univocamente, mediante la quale sia possibile verificare il flusso unidirezionale degli spostamenti degli animali nellazienda e identificare i gruppi di animali?	'))::integer);
update biosicurezza_domande set id_classyfarm =	702	 where ordine in (select substring('	12. Larea di stabulazione e governo degli animali, dispone di muro di cinta o di una recinzione idonee ad impedire lingresso di altri animali compresi quelli selvatici?	', 0, position('.' in '	12. Larea di stabulazione e governo degli animali, dispone di muro di cinta o di una recinzione idonee ad impedire lingresso di altri animali compresi quelli selvatici?	'))::integer);
update biosicurezza_domande set id_classyfarm =	703	 where ordine in (select substring('	13. I locali di stabulazione hanno pareti, pavimenti e serramenti a tenuta e in buono stato di manutenzione, senza soluzioni di continuita, pulibili e disinfettabili in modo efficace?	', 0, position('.' in '	13. I locali di stabulazione hanno pareti, pavimenti e serramenti a tenuta e in buono stato di manutenzione, senza soluzioni di continuita, pulibili e disinfettabili in modo efficace?	'))::integer);
update biosicurezza_domande set id_classyfarm =	704	 where ordine in (select substring('	14. Esiste una prassi di pulizia, lavaggio e disinfezione dei ricoveri, degli ambienti e delle attrezzature dellazienda e ove necessario, dopo la fine di ogni ciclo produttivo (anche per settori)?	', 0, position('.' in '	14. Esiste una prassi di pulizia, lavaggio e disinfezione dei ricoveri, degli ambienti e delle attrezzature dellazienda e ove necessario, dopo la fine di ogni ciclo produttivo (anche per settori)?	'))::integer);
update biosicurezza_domande set id_classyfarm =	705	 where ordine in (select substring('	15. Larea tutta intorno ai ricoveri degli animali e mantenuta pulita, coperta da ghiaia o con erba sfalciata, libera da ingombri, oggetti, attrezzature, macchinari, veicoli, ecc.   estranei alla funzionalita e gestione dellallevamento?	', 0, position('.' in '	15. Larea tutta intorno ai ricoveri degli animali e mantenuta pulita, coperta da ghiaia o con erba sfalciata, libera da ingombri, oggetti, attrezzature, macchinari, veicoli, ecc.   estranei alla funzionalita e gestione dellallevamento?	'))::integer);
update biosicurezza_domande set id_classyfarm =	706	 where ordine in (select substring('	16. Lallevamento dispone di punti di disinfezione tra i diversi capannoni?	', 0, position('.' in '	16. Lallevamento dispone di punti di disinfezione tra i diversi capannoni?	'))::integer);
update biosicurezza_domande set id_classyfarm =	707	 where ordine in (select substring('	17. E previsto e documentato un piano di derattizzazione?	', 0, position('.' in '	17. E previsto e documentato un piano di derattizzazione?	'))::integer);
update biosicurezza_domande set id_classyfarm =	708	 where ordine in (select substring('	18. La derattizzazione viene effettuata ad opera di una ditta specializzata esterna?	', 0, position('.' in '	18. La derattizzazione viene effettuata ad opera di una ditta specializzata esterna?	'))::integer);
update biosicurezza_domande set id_classyfarm =	709	 where ordine in (select substring('	19. E previsto e documentato un piano di disinfestazione?	', 0, position('.' in '	19. E previsto e documentato un piano di disinfestazione?	'))::integer);
update biosicurezza_domande set id_classyfarm =	710	 where ordine in (select substring('	20. La disinfestazione viene effettuata ad opera di una ditta specializzata esterna?	', 0, position('.' in '	20. La disinfestazione viene effettuata ad opera di una ditta specializzata esterna?	'))::integer);
update biosicurezza_domande set id_classyfarm =	711	 where ordine in (select substring('	21. Esiste documentazione relativa a corsi di formazione esterna o interna sulla biosicurezza e sui rischi di introduzione di malattie infettive e diffusive degli animali soggette a denuncia?	', 0, position('.' in '	21. Esiste documentazione relativa a corsi di formazione esterna o interna sulla biosicurezza e sui rischi di introduzione di malattie infettive e diffusive degli animali soggette a denuncia?	'))::integer);
update biosicurezza_domande set id_classyfarm =	712	 where ordine in (select substring('	22. Esiste un piano di profilassi vaccinale documentato?	', 0, position('.' in '	22. Esiste un piano di profilassi vaccinale documentato?	'))::integer);
update biosicurezza_domande set id_classyfarm =	713	 where ordine in (select substring('	23. Esiste una prassi igienica e sanitaria di gestione delle attrezzature utilizzate per la profilassi vaccinale e i trattamenti terapeutici individuali o di gruppo?	', 0, position('.' in '	23. Esiste una prassi igienica e sanitaria di gestione delle attrezzature utilizzate per la profilassi vaccinale e i trattamenti terapeutici individuali o di gruppo?	'))::integer);
update biosicurezza_domande set id_classyfarm =	714	 where ordine in (select substring('	24. Sono presenti eventuali risultati delle analisi, ufficiali o effettuate in autocontrollo, su campioni prelevati da animali o da altre matrici che abbiano rilevanza per la salute umana e animale?	', 0, position('.' in '	24. Sono presenti eventuali risultati delle analisi, ufficiali o effettuate in autocontrollo, su campioni prelevati da animali o da altre matrici che abbiano rilevanza per la salute umana e animale?	'))::integer);
update biosicurezza_domande set id_classyfarm =	715	 where ordine in (select substring('	25. Esiste un sistema di registrazione dei dati aziendali sanitari, di allevamento, di riproduzione e produzione?	', 0, position('.' in '	25. Esiste un sistema di registrazione dei dati aziendali sanitari, di allevamento, di riproduzione e produzione?	'))::integer);
update biosicurezza_domande set id_classyfarm =	716	 where ordine in (select substring('	26. Il personale addetto al governo degli animali ha contatti con altre aziende suinicole?	', 0, position('.' in '	26. Il personale addetto al governo degli animali ha contatti con altre aziende suinicole?	'))::integer);
update biosicurezza_domande set id_classyfarm =	717	 where ordine in (select substring('	27. E presente un registro dei visitatori con indicato almeno data, nome e cognome del visitatore, motivo della visita e targa dellautomezzo?	', 0, position('.' in '	27. E presente un registro dei visitatori con indicato almeno data, nome e cognome del visitatore, motivo della visita e targa dellautomezzo?	'))::integer);
update biosicurezza_domande set id_classyfarm =	719	 where ordine in (select substring('	28. E presente una documentazione attestante lavvenuta disinfezione degli automezzi?	', 0, position('.' in '	28. E presente una documentazione attestante lavvenuta disinfezione degli automezzi?	'))::integer);
update biosicurezza_domande set id_classyfarm =	720	 where ordine in (select substring('	29. Lallevamento dispone di una piazzola per la pulizia e la disinfezione degli automezzi localizzata in prossimita dellaccesso allallevamento o, in ogni caso, separata dallarea aziendale destinata alla stabulazione e al governo animali?	', 0, position('.' in '	29. Lallevamento dispone di una piazzola per la pulizia e la disinfezione degli automezzi localizzata in prossimita dellaccesso allallevamento o, in ogni caso, separata dallarea aziendale destinata alla stabulazione e al governo animali?	'))::integer);
update biosicurezza_domande set id_classyfarm =	722	 where ordine in (select substring('	30. Sono presenti apparecchiature a pressione FISSE per la pulizia, il lavaggio e la disinfezione?	', 0, position('.' in '	30. Sono presenti apparecchiature a pressione FISSE per la pulizia, il lavaggio e la disinfezione?	'))::integer);
update biosicurezza_domande set id_classyfarm =	723	 where ordine in (select substring('	31. Sono disponibili disinfettanti di provata efficacia nei confronti delle malattie vescicolari del suino e ASF?	', 0, position('.' in '	31. Sono disponibili disinfettanti di provata efficacia nei confronti delle malattie vescicolari del suino e ASF?	'))::integer);
update biosicurezza_domande set id_classyfarm =	724	 where ordine in (select substring('	32. Il carico/scarico dei suini vivi avviene allesterno dellarea di stabulazione e di governo degli animali?	', 0, position('.' in '	32. Il carico/scarico dei suini vivi avviene allesterno dellarea di stabulazione e di governo degli animali?	'))::integer);
update biosicurezza_domande set id_classyfarm =	725	 where ordine in (select substring('	33. Esiste una rampa/corridoio di carico/scarico degli animali vivi, fissa o mobile?	', 0, position('.' in '	33. Esiste una rampa/corridoio di carico/scarico degli animali vivi, fissa o mobile?	'))::integer);
update biosicurezza_domande set id_classyfarm =	726	 where ordine in (select substring('	34. Il carico dei suini vivi avviene con monocarico?	', 0, position('.' in '	34. Il carico dei suini vivi avviene con monocarico?	'))::integer);
update biosicurezza_domande set id_classyfarm =	727	 where ordine in (select substring('	35. Il carico degli scarti avviene allesterno larea di stabulazione e di governo degli animali?	', 0, position('.' in '	35. Il carico degli scarti avviene allesterno larea di stabulazione e di governo degli animali?	'))::integer);
update biosicurezza_domande set id_classyfarm =	728	 where ordine in (select substring('	36. Il carico degli scarti avviene con monocarico?	', 0, position('.' in '	36. Il carico degli scarti avviene con monocarico?	'))::integer);
update biosicurezza_domande set id_classyfarm =	729	 where ordine in (select substring('	37. Le carcasse degli animali morti sono rimosse dai locali di allevamento entro 24 ore dal decesso, conservate in un contenitore coibentato o in una cella frigorifera a tenuta, idonei e funzionanti, posti allesterno dellarea di governo degli animali, per leliminazione delle stesse conformemente alle disposizioni sanitarie? 	', 0, position('.' in '	37. Le carcasse degli animali morti sono rimosse dai locali di allevamento entro 24 ore dal decesso, conservate in un contenitore coibentato o in una cella frigorifera a tenuta, idonei e funzionanti, posti allesterno dellarea di governo degli animali, per leliminazione delle stesse conformemente alle disposizioni sanitarie? 	'))::integer);
update biosicurezza_domande set id_classyfarm =	730	 where ordine in (select substring('	38. Il carico dei suini morti avviene allesterno dellarea di stabulazione e governo degli animali?	', 0, position('.' in '	38. Il carico dei suini morti avviene allesterno dellarea di stabulazione e governo degli animali?	'))::integer);
update biosicurezza_domande set id_classyfarm =	731	 where ordine in (select substring('	39. Il contenitore/cella frigorifera dove vengono conservati i morti ha un accesso e un percorso differenziato da quello dellarea di stabulazione e governo degli animali?	', 0, position('.' in '	39. Il contenitore/cella frigorifera dove vengono conservati i morti ha un accesso e un percorso differenziato da quello dellarea di stabulazione e governo degli animali?	'))::integer);
update biosicurezza_domande set id_classyfarm =	732	 where ordine in (select substring('	40. Larea sottostante il contenitore/cella frigorifera dei morti, e idonea sia alla raccolta di eventuali materiali o liquidi percolanti sia alla pulizia e disinfezione?	', 0, position('.' in '	40. Larea sottostante il contenitore/cella frigorifera dei morti, e idonea sia alla raccolta di eventuali materiali o liquidi percolanti sia alla pulizia e disinfezione?	'))::integer);
update biosicurezza_domande set id_classyfarm =	733	 where ordine in (select substring('	41. Qualora le carcasse dei suinetti siano temporaneamente immagazzinate nei locali di allevamento, in attesa del loro allontanamento, i contenitori utilizzati sono adeguatamente sigillati ed idonei alla conservazione?	', 0, position('.' in '	41. Qualora le carcasse dei suinetti siano temporaneamente immagazzinate nei locali di allevamento, in attesa del loro allontanamento, i contenitori utilizzati sono adeguatamente sigillati ed idonei alla conservazione?	'))::integer);
update biosicurezza_domande set id_classyfarm =	734	 where ordine in (select substring('	42. Lo scarico del mangime avviene dallesterno dellarea di stabulazione e governo degli animali?	', 0, position('.' in '	42. Lo scarico del mangime avviene dallesterno dellarea di stabulazione e governo degli animali?	'))::integer);
update biosicurezza_domande set id_classyfarm =	735	 where ordine in (select substring('	43. I punti di ingresso, le aree di stoccaggio/contenitori dei mangimi sono coperti da griglie o sigillati per impedire lingresso di altri animali, ratti e insetti nocivi?	', 0, position('.' in '	43. I punti di ingresso, le aree di stoccaggio/contenitori dei mangimi sono coperti da griglie o sigillati per impedire lingresso di altri animali, ratti e insetti nocivi?	'))::integer);
update biosicurezza_domande set id_classyfarm =	736	 where ordine in (select substring('	44. Le aree sottostanti i silos dei mangimi consentono una efficace pulizia e il deflusso delle acque di lavaggio?	', 0, position('.' in '	44. Le aree sottostanti i silos dei mangimi consentono una efficace pulizia e il deflusso delle acque di lavaggio?	'))::integer);
update biosicurezza_domande set id_classyfarm =	737	 where ordine in (select substring('	45. Sono utilizzati per lalimentazione degli animali dei prodotti derivati dal latte? 	', 0, position('.' in '	45. Sono utilizzati per lalimentazione degli animali dei prodotti derivati dal latte? 	'))::integer);
update biosicurezza_domande set id_classyfarm =	738	 where ordine in (select substring('	46. Se sono utilizzati per lalimentazione degli animali dei prodotti derivati dal latte e presente il nulla-osta al loro utilizzo ed e garantita la loro tracciabilita?	', 0, position('.' in '	46. Se sono utilizzati per lalimentazione degli animali dei prodotti derivati dal latte e presente il nulla-osta al loro utilizzo ed e garantita la loro tracciabilita?	'))::integer);
update biosicurezza_domande set id_classyfarm =	739	 where ordine in (select substring('	47. Il punto di pesa degli animali, se presente, e ubicato allesterno dellarea di stabulazione e governo degli animali?	', 0, position('.' in '	47. Il punto di pesa degli animali, se presente, e ubicato allesterno dellarea di stabulazione e governo degli animali?	'))::integer);
update biosicurezza_domande set id_classyfarm =	740	 where ordine in (select substring('	48. Agli animali vengono somministrati rifiuti di ristorazione, mensa o avanzi casalinghi?	', 0, position('.' in '	48. Agli animali vengono somministrati rifiuti di ristorazione, mensa o avanzi casalinghi?	'))::integer);
update biosicurezza_domande set id_classyfarm =	741	 where ordine in (select substring('	49. Esistono ingressi per le operazioni di trasporto dei liquami differenziati da quelli dellarea di stabulazione e governo degli animali?	', 0, position('.' in '	49. Esistono ingressi per le operazioni di trasporto dei liquami differenziati da quelli dellarea di stabulazione e governo degli animali?	'))::integer);
update biosicurezza_domande set id_classyfarm =	742	 where ordine in (select substring('	50. I terreni attigui allazienda sono utilizzati per lo spandimento di liquami provenienti da altre aziende?	', 0, position('.' in '	50. I terreni attigui allazienda sono utilizzati per lo spandimento di liquami provenienti da altre aziende?	'))::integer);
update biosicurezza_domande set id_classyfarm =	743	 where ordine in (select substring('	51. In allevamento sono presenti animali domestici/da compagnia che possono avere accesso ai locali dove sono stabulati i suini?	', 0, position('.' in '	51. In allevamento sono presenti animali domestici/da compagnia che possono avere accesso ai locali dove sono stabulati i suini?	'))::integer);
update biosicurezza_domande set id_classyfarm =	744	 where ordine in (select substring('	52. Sono presenti delle reti antipassero o e comunque garantita limpossibilita di ingresso degli uccelli negli stabili?	', 0, position('.' in '	52. Sono presenti delle reti antipassero o e comunque garantita limpossibilita di ingresso degli uccelli negli stabili?	'))::integer);
update biosicurezza_domande set id_classyfarm =	745	 where ordine in (select substring('	53. La rimonta viene effettuata ad opera di riproduttori esterni?	', 0, position('.' in '	53. La rimonta viene effettuata ad opera di riproduttori esterni?	'))::integer);
update biosicurezza_domande set id_classyfarm =	746	 where ordine in (select substring('	54. Viene garantito lidoneo isolamento dei capi introdotti?	', 0, position('.' in '	54. Viene garantito lidoneo isolamento dei capi introdotti?	'))::integer);
update biosicurezza_domande set id_classyfarm =	747	 where ordine in (select substring('	55. Lallevamento dispone di locali separati fisicamente  funzionalmente per la quarantena dei riproduttori di nuova introduzione? 	', 0, position('.' in '	55. Lallevamento dispone di locali separati fisicamente  funzionalmente per la quarantena dei riproduttori di nuova introduzione? 	'))::integer);
update biosicurezza_domande set id_classyfarm =	748	 where ordine in (select substring('	56. Viene pratico il pieno/tutto vuoto e un idoneo periodo di vuoto sanitario?	', 0, position('.' in '	56. Viene pratico il pieno/tutto vuoto e un idoneo periodo di vuoto sanitario?	'))::integer);
update biosicurezza_domande set id_classyfarm =	749	 where ordine in (select substring('	57. Il personale non accudisce altri animali oltre a quelli della quarantena, diversamente e presente una zona filtro specifica per la quarantena?	', 0, position('.' in '	57. Il personale non accudisce altri animali oltre a quelli della quarantena, diversamente e presente una zona filtro specifica per la quarantena?	'))::integer);
update biosicurezza_domande set id_classyfarm =	750	 where ordine in (select substring('	58. I locali di quarantena dispongono di fossa/e separata/e?	', 0, position('.' in '	58. I locali di quarantena dispongono di fossa/e separata/e?	'))::integer);
update biosicurezza_domande set id_classyfarm =	751	 where ordine in (select substring('	59. I locali di quarantena dispongono di ingresso/i separato/i?	', 0, position('.' in '	59. I locali di quarantena dispongono di ingresso/i separato/i?	'))::integer);
update biosicurezza_domande set id_classyfarm =	752	 where ordine in (select substring('	60. Sono disponibili attrezzature destinate esclusivamente alla quarantena?	', 0, position('.' in '	60. Sono disponibili attrezzature destinate esclusivamente alla quarantena?	'))::integer);
update biosicurezza_domande set id_classyfarm =	753	 where ordine in (select substring('	61. Sono disponibili indumenti per il personale o monouso (tute e calzari) destinati esclusivamente alla quarantena?	', 0, position('.' in '	61. Sono disponibili indumenti per il personale o monouso (tute e calzari) destinati esclusivamente alla quarantena?	'))::integer);
update biosicurezza_domande set id_classyfarm =	754	 where ordine in (select substring('	62. E prevista lesecuzione pianificata di accertamenti diagnostici negli animali in quarantena? 	', 0, position('.' in '	62. E prevista lesecuzione pianificata di accertamenti diagnostici negli animali in quarantena? 	'))::integer);
update biosicurezza_domande set id_classyfarm =	755	 where ordine in (select substring('	63. E richiesta e disponibile alle aziende di provenienza una documentazione che attesti lo stato sanitario degli animali di nuova introduzione?	', 0, position('.' in '	63. E richiesta e disponibile alle aziende di provenienza una documentazione che attesti lo stato sanitario degli animali di nuova introduzione?	'))::integer);
update biosicurezza_domande set id_classyfarm =	995	 where ordine in (select substring('	64. La rimonta dei riproduttori viene effettuata con cadenza superiore a 3 mesi?	', 0, position('.' in '	64. La rimonta dei riproduttori viene effettuata con cadenza superiore a 3 mesi?	'))::integer);
update biosicurezza_domande set id_classyfarm =	763	 where ordine in (select substring('	65.  Lesame ecografico effettuato da operatori esterni?	', 0, position('.' in '	65.  Lesame ecografico effettuato da operatori esterni?	'))::integer);
update biosicurezza_domande set id_classyfarm =	764	 where ordine in (select substring('	66. Nel caso in cui si pratichi la fecondazione artificiale il materiale seminale questo proviene da centri di raccolta seme autorizzati?	', 0, position('.' in '	66. Nel caso in cui si pratichi la fecondazione artificiale il materiale seminale questo proviene da centri di raccolta seme autorizzati?	'))::integer);
update biosicurezza_domande set id_classyfarm =	765	 where ordine in (select substring('	67. Nel caso in cui si pratichi la monta naturale i verri sono stati sottoposti agli accertamenti diagnostici previsti per i riproduttori maschi della specie suina?	', 0, position('.' in '	67. Nel caso in cui si pratichi la monta naturale i verri sono stati sottoposti agli accertamenti diagnostici previsti per i riproduttori maschi della specie suina?	'))::integer);
update biosicurezza_domande set id_classyfarm =	766	 where ordine in (select substring('	68. I suinetti in sala parto sono destinati a piu di due allevamenti?	', 0, position('.' in '	68. I suinetti in sala parto sono destinati a piu di due allevamenti?	'))::integer);
update biosicurezza_domande set id_classyfarm =	767	 where ordine in (select substring('	69. I suini provengono da piu di un allevamento?	', 0, position('.' in '	69. I suini provengono da piu di un allevamento?	'))::integer);
update biosicurezza_domande set id_classyfarm =	768	 where ordine in (select substring('	70. In allevamento viene applicato il sistema tutto pieno/tutto vuoto per allevamento?	', 0, position('.' in '	70. In allevamento viene applicato il sistema tutto pieno/tutto vuoto per allevamento?	'))::integer);
update biosicurezza_domande set id_classyfarm =	769	 where ordine in (select substring('	71. In allevamento viene applicato il sistema tutto pieno/tutto vuoto per capannone? 	', 0, position('.' in '	71. In allevamento viene applicato il sistema tutto pieno/tutto vuoto per capannone? 	'))::integer);
update biosicurezza_domande set id_classyfarm =	770	 where ordine in (select substring('	72. In allevamento viene applicato il sistema tutto pieno/tutto vuoto per stanza? 	', 0, position('.' in '	72. In allevamento viene applicato il sistema tutto pieno/tutto vuoto per stanza? 	'))::integer);
update biosicurezza_domande set id_classyfarm =	771	 where ordine in (select substring('	73. I suini a fine ciclo sono destinati a piu di 1  allevamento?	', 0, position('.' in '	73. I suini a fine ciclo sono destinati a piu di 1  allevamento?	'))::integer);
update biosicurezza_domande set id_classyfarm =	772	 where ordine in (select substring('	74. I suini provengono da piu di un allevamento?	', 0, position('.' in '	74. I suini provengono da piu di un allevamento?	'))::integer);
update biosicurezza_domande set id_classyfarm =	773	 where ordine in (select substring('	75. In allevamento viene applicato il sistema tutto pieno/tutto vuoto per allevamento?	', 0, position('.' in '	75. In allevamento viene applicato il sistema tutto pieno/tutto vuoto per allevamento?	'))::integer);
update biosicurezza_domande set id_classyfarm =	774	 where ordine in (select substring('	76. In allevamento viene applicato il sistema tutto pieno/tutto vuoto per capannone? 	', 0, position('.' in '	76. In allevamento viene applicato il sistema tutto pieno/tutto vuoto per capannone? 	'))::integer);
update biosicurezza_domande set id_classyfarm =	775	 where ordine in (select substring('	77. In allevamento viene applicato il sistema tutto pieno/tutto vuoto per stanza? 	', 0, position('.' in '	77. In allevamento viene applicato il sistema tutto pieno/tutto vuoto per stanza? 	'))::integer);
update biosicurezza_domande set id_classyfarm =	776	 where ordine in (select substring('	78. I suini a fine ciclo sono destinati a solo macelli industriali?	', 0, position('.' in '	78. I suini a fine ciclo sono destinati a solo macelli industriali?	'))::integer);

update biosicurezza_risposte set risposta = 'Note - Motivo NA:' where risposta = 'Note - Motivo NA';

update biosicurezza_risposte set id_classyfarm =1690 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 603);update biosicurezza_risposte set id_classyfarm =1470 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 603);update biosicurezza_risposte set id_classyfarm =1469 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 603);update biosicurezza_risposte set id_classyfarm =1471 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 603);
update biosicurezza_risposte set id_classyfarm =1637 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 692);update biosicurezza_risposte set id_classyfarm =1638 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 692);update biosicurezza_risposte set id_classyfarm =1635 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 692);update biosicurezza_risposte set id_classyfarm =1636 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 692);
update biosicurezza_risposte set id_classyfarm =1698 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 718);update biosicurezza_risposte set id_classyfarm =1697 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 718);update biosicurezza_risposte set id_classyfarm =1700 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 718);update biosicurezza_risposte set id_classyfarm =1699 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 718);
update biosicurezza_risposte set id_classyfarm =1701 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 694);update biosicurezza_risposte set id_classyfarm =1702 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 694);update biosicurezza_risposte set id_classyfarm =1641 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 694);update biosicurezza_risposte set id_classyfarm =1642 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 694);
update biosicurezza_risposte set id_classyfarm =1644 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 695);update biosicurezza_risposte set id_classyfarm =1643 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 695);update biosicurezza_risposte set id_classyfarm =1704 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 695);update biosicurezza_risposte set id_classyfarm =1703 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 695);
update biosicurezza_risposte set id_classyfarm =1705 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 696);update biosicurezza_risposte set id_classyfarm =1706 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 696);update biosicurezza_risposte set id_classyfarm =1645 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 696);update biosicurezza_risposte set id_classyfarm =1646 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 696);
update biosicurezza_risposte set id_classyfarm =1648 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 697);update biosicurezza_risposte set id_classyfarm =1647 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 697);update biosicurezza_risposte set id_classyfarm =1707 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 697);update biosicurezza_risposte set id_classyfarm =1708 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 697);
update biosicurezza_risposte set id_classyfarm =1709 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 698);update biosicurezza_risposte set id_classyfarm =1710 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 698);update biosicurezza_risposte set id_classyfarm =1649 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 698);update biosicurezza_risposte set id_classyfarm =1650 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 698);
update biosicurezza_risposte set id_classyfarm =1652 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 699);update biosicurezza_risposte set id_classyfarm =1651 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 699);update biosicurezza_risposte set id_classyfarm =1711 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 699);update biosicurezza_risposte set id_classyfarm =1712 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 699);
update biosicurezza_risposte set id_classyfarm =1713 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 700);update biosicurezza_risposte set id_classyfarm =1714 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 700);update biosicurezza_risposte set id_classyfarm =1653 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 700);update biosicurezza_risposte set id_classyfarm =1654 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 700);
update biosicurezza_risposte set id_classyfarm =1657 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 701);update biosicurezza_risposte set id_classyfarm =1656 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 701);update biosicurezza_risposte set id_classyfarm =1715 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 701);update biosicurezza_risposte set id_classyfarm =1716 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 701);
update biosicurezza_risposte set id_classyfarm =1717 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 702);update biosicurezza_risposte set id_classyfarm =1718 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 702);update biosicurezza_risposte set id_classyfarm =1658 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 702);update biosicurezza_risposte set id_classyfarm =1659 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 702);
update biosicurezza_risposte set id_classyfarm =1661 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 703);update biosicurezza_risposte set id_classyfarm =1660 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 703);update biosicurezza_risposte set id_classyfarm =1719 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 703);update biosicurezza_risposte set id_classyfarm =1720 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 703);
update biosicurezza_risposte set id_classyfarm =1722 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 704);update biosicurezza_risposte set id_classyfarm =1721 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 704);update biosicurezza_risposte set id_classyfarm =1662 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 704);update biosicurezza_risposte set id_classyfarm =1663 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 704);
update biosicurezza_risposte set id_classyfarm =1665 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 705);update biosicurezza_risposte set id_classyfarm =1664 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 705);update biosicurezza_risposte set id_classyfarm =1723 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 705);update biosicurezza_risposte set id_classyfarm =1724 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 705);
update biosicurezza_risposte set id_classyfarm =1726 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 706);update biosicurezza_risposte set id_classyfarm =1725 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 706);update biosicurezza_risposte set id_classyfarm =1666 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 706);update biosicurezza_risposte set id_classyfarm =1667 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 706);
update biosicurezza_risposte set id_classyfarm =1669 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 707);update biosicurezza_risposte set id_classyfarm =1668 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 707);update biosicurezza_risposte set id_classyfarm =1727 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 707);update biosicurezza_risposte set id_classyfarm =1728 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 707);
update biosicurezza_risposte set id_classyfarm =1730 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 708);update biosicurezza_risposte set id_classyfarm =1729 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 708);update biosicurezza_risposte set id_classyfarm =1670 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 708);update biosicurezza_risposte set id_classyfarm =1671 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 708);
update biosicurezza_risposte set id_classyfarm =1673 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 709);update biosicurezza_risposte set id_classyfarm =1672 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 709);update biosicurezza_risposte set id_classyfarm =1731 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 709);update biosicurezza_risposte set id_classyfarm =1732 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 709);
update biosicurezza_risposte set id_classyfarm =1733 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 710);update biosicurezza_risposte set id_classyfarm =1734 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 710);update biosicurezza_risposte set id_classyfarm =1674 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 710);update biosicurezza_risposte set id_classyfarm =1675 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 710);
update biosicurezza_risposte set id_classyfarm =1677 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 711);update biosicurezza_risposte set id_classyfarm =1676 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 711);update biosicurezza_risposte set id_classyfarm =1735 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 711);update biosicurezza_risposte set id_classyfarm =1736 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 711);
update biosicurezza_risposte set id_classyfarm =1738 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 712);update biosicurezza_risposte set id_classyfarm =1737 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 712);update biosicurezza_risposte set id_classyfarm =1678 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 712);update biosicurezza_risposte set id_classyfarm =1679 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 712);
update biosicurezza_risposte set id_classyfarm =1681 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 713);update biosicurezza_risposte set id_classyfarm =1680 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 713);update biosicurezza_risposte set id_classyfarm =1739 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 713);update biosicurezza_risposte set id_classyfarm =1740 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 713);
update biosicurezza_risposte set id_classyfarm =1742 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 714);update biosicurezza_risposte set id_classyfarm =1741 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 714);update biosicurezza_risposte set id_classyfarm =1682 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 714);update biosicurezza_risposte set id_classyfarm =1683 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 714);
update biosicurezza_risposte set id_classyfarm =1685 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 715);update biosicurezza_risposte set id_classyfarm =1684 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 715);update biosicurezza_risposte set id_classyfarm =1743 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 715);update biosicurezza_risposte set id_classyfarm =1744 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 715);
update biosicurezza_risposte set id_classyfarm =1746 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 716);update biosicurezza_risposte set id_classyfarm =1745 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 716);update biosicurezza_risposte set id_classyfarm =1686 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 716);update biosicurezza_risposte set id_classyfarm =1687 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 716);
update biosicurezza_risposte set id_classyfarm =1689 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 717);update biosicurezza_risposte set id_classyfarm =1688 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 717);update biosicurezza_risposte set id_classyfarm =1747 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 717);update biosicurezza_risposte set id_classyfarm =1748 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 717);
update biosicurezza_risposte set id_classyfarm =1752 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 719);update biosicurezza_risposte set id_classyfarm =1751 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 719);update biosicurezza_risposte set id_classyfarm =1749 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 719);update biosicurezza_risposte set id_classyfarm =1750 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 719);
update biosicurezza_risposte set id_classyfarm =1754 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 720);update biosicurezza_risposte set id_classyfarm =1753 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 720);update biosicurezza_risposte set id_classyfarm =1755 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 720);update biosicurezza_risposte set id_classyfarm =1756 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 720);
update biosicurezza_risposte set id_classyfarm =1764 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 722);update biosicurezza_risposte set id_classyfarm =1763 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 722);update biosicurezza_risposte set id_classyfarm =1761 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 722);update biosicurezza_risposte set id_classyfarm =1762 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 722);
update biosicurezza_risposte set id_classyfarm =1766 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 723);update biosicurezza_risposte set id_classyfarm =1765 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 723);update biosicurezza_risposte set id_classyfarm =1767 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 723);update biosicurezza_risposte set id_classyfarm =1768 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 723);
update biosicurezza_risposte set id_classyfarm =1771 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 724);update biosicurezza_risposte set id_classyfarm =1769 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 724);update biosicurezza_risposte set id_classyfarm =1772 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 724);update biosicurezza_risposte set id_classyfarm =1770 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 724);
update biosicurezza_risposte set id_classyfarm =1774 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 725);update biosicurezza_risposte set id_classyfarm =1773 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 725);update biosicurezza_risposte set id_classyfarm =1775 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 725);update biosicurezza_risposte set id_classyfarm =1776 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 725);
update biosicurezza_risposte set id_classyfarm =1780 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 726);update biosicurezza_risposte set id_classyfarm =1779 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 726);update biosicurezza_risposte set id_classyfarm =1777 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 726);update biosicurezza_risposte set id_classyfarm =1778 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 726);
update biosicurezza_risposte set id_classyfarm =1782 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 727);update biosicurezza_risposte set id_classyfarm =1781 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 727);update biosicurezza_risposte set id_classyfarm =1783 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 727);update biosicurezza_risposte set id_classyfarm =1784 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 727);
update biosicurezza_risposte set id_classyfarm =1788 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 728);update biosicurezza_risposte set id_classyfarm =1787 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 728);update biosicurezza_risposte set id_classyfarm =1785 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 728);update biosicurezza_risposte set id_classyfarm =1786 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 728);
update biosicurezza_risposte set id_classyfarm =1790 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 729);update biosicurezza_risposte set id_classyfarm =1789 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 729);update biosicurezza_risposte set id_classyfarm =1791 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 729);update biosicurezza_risposte set id_classyfarm =1792 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 729);
update biosicurezza_risposte set id_classyfarm =1796 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 730);update biosicurezza_risposte set id_classyfarm =1795 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 730);update biosicurezza_risposte set id_classyfarm =1793 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 730);update biosicurezza_risposte set id_classyfarm =1794 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 730);
update biosicurezza_risposte set id_classyfarm =1798 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 731);update biosicurezza_risposte set id_classyfarm =1797 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 731);update biosicurezza_risposte set id_classyfarm =1799 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 731);update biosicurezza_risposte set id_classyfarm =1800 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 731);
update biosicurezza_risposte set id_classyfarm =1804 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 732);update biosicurezza_risposte set id_classyfarm =1803 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 732);update biosicurezza_risposte set id_classyfarm =1801 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 732);update biosicurezza_risposte set id_classyfarm =1802 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 732);
update biosicurezza_risposte set id_classyfarm =1806 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 733);update biosicurezza_risposte set id_classyfarm =1805 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 733);update biosicurezza_risposte set id_classyfarm =1807 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 733);update biosicurezza_risposte set id_classyfarm =1808 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 733);
update biosicurezza_risposte set id_classyfarm =1812 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 734);update biosicurezza_risposte set id_classyfarm =1811 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 734);update biosicurezza_risposte set id_classyfarm =1809 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 734);update biosicurezza_risposte set id_classyfarm =1810 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 734);
update biosicurezza_risposte set id_classyfarm =1814 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 735);update biosicurezza_risposte set id_classyfarm =1813 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 735);update biosicurezza_risposte set id_classyfarm =1815 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 735);update biosicurezza_risposte set id_classyfarm =1816 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 735);
update biosicurezza_risposte set id_classyfarm =1820 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 736);update biosicurezza_risposte set id_classyfarm =1819 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 736);update biosicurezza_risposte set id_classyfarm =1817 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 736);update biosicurezza_risposte set id_classyfarm =1818 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 736);
update biosicurezza_risposte set id_classyfarm =1822 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 737);update biosicurezza_risposte set id_classyfarm =1821 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 737);update biosicurezza_risposte set id_classyfarm =1823 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 737);update biosicurezza_risposte set id_classyfarm =1824 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 737);
update biosicurezza_risposte set id_classyfarm =1827 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 738);update biosicurezza_risposte set id_classyfarm =1828 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 738);update biosicurezza_risposte set id_classyfarm =1825 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 738);update biosicurezza_risposte set id_classyfarm =1826 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 738);
update biosicurezza_risposte set id_classyfarm =1830 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 739);update biosicurezza_risposte set id_classyfarm =1829 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 739);update biosicurezza_risposte set id_classyfarm =1832 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 739);update biosicurezza_risposte set id_classyfarm =1831 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 739);
update biosicurezza_risposte set id_classyfarm =1835 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 740);update biosicurezza_risposte set id_classyfarm =1836 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 740);update biosicurezza_risposte set id_classyfarm =1833 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 740);update biosicurezza_risposte set id_classyfarm =1834 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 740);
update biosicurezza_risposte set id_classyfarm =1838 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 741);update biosicurezza_risposte set id_classyfarm =1837 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 741);update biosicurezza_risposte set id_classyfarm =1840 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 741);update biosicurezza_risposte set id_classyfarm =1839 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 741);
update biosicurezza_risposte set id_classyfarm =1843 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 742);update biosicurezza_risposte set id_classyfarm =1844 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 742);update biosicurezza_risposte set id_classyfarm =1841 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 742);update biosicurezza_risposte set id_classyfarm =1842 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 742);
update biosicurezza_risposte set id_classyfarm =1846 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 743);update biosicurezza_risposte set id_classyfarm =1845 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 743);update biosicurezza_risposte set id_classyfarm =1848 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 743);update biosicurezza_risposte set id_classyfarm =1847 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 743);
update biosicurezza_risposte set id_classyfarm =1851 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 744);update biosicurezza_risposte set id_classyfarm =1852 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 744);update biosicurezza_risposte set id_classyfarm =1849 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 744);update biosicurezza_risposte set id_classyfarm =1850 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 744);
update biosicurezza_risposte set id_classyfarm =1854 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 745);update biosicurezza_risposte set id_classyfarm =1853 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 745);update biosicurezza_risposte set id_classyfarm =1855 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 745);update biosicurezza_risposte set id_classyfarm =1856 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 745);
update biosicurezza_risposte set id_classyfarm =1859 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 746);update biosicurezza_risposte set id_classyfarm =1860 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 746);update biosicurezza_risposte set id_classyfarm =1857 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 746);update biosicurezza_risposte set id_classyfarm =1858 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 746);
update biosicurezza_risposte set id_classyfarm =1862 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 747);update biosicurezza_risposte set id_classyfarm =1861 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 747);update biosicurezza_risposte set id_classyfarm =1864 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 747);update biosicurezza_risposte set id_classyfarm =1863 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 747);
update biosicurezza_risposte set id_classyfarm =1867 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 748);update biosicurezza_risposte set id_classyfarm =1868 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 748);update biosicurezza_risposte set id_classyfarm =1865 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 748);update biosicurezza_risposte set id_classyfarm =1866 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 748);
update biosicurezza_risposte set id_classyfarm =1870 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 749);update biosicurezza_risposte set id_classyfarm =1869 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 749);update biosicurezza_risposte set id_classyfarm =1872 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 749);update biosicurezza_risposte set id_classyfarm =1871 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 749);
update biosicurezza_risposte set id_classyfarm =1875 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 750);update biosicurezza_risposte set id_classyfarm =1876 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 750);update biosicurezza_risposte set id_classyfarm =1873 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 750);update biosicurezza_risposte set id_classyfarm =1874 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 750);
update biosicurezza_risposte set id_classyfarm =1878 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 751);update biosicurezza_risposte set id_classyfarm =1877 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 751);update biosicurezza_risposte set id_classyfarm =1880 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 751);update biosicurezza_risposte set id_classyfarm =1879 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 751);
update biosicurezza_risposte set id_classyfarm =1883 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 752);update biosicurezza_risposte set id_classyfarm =1884 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 752);update biosicurezza_risposte set id_classyfarm =1881 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 752);update biosicurezza_risposte set id_classyfarm =1882 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 752);
update biosicurezza_risposte set id_classyfarm =1886 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 753);update biosicurezza_risposte set id_classyfarm =1885 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 753);update biosicurezza_risposte set id_classyfarm =1888 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 753);update biosicurezza_risposte set id_classyfarm =1887 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 753);
update biosicurezza_risposte set id_classyfarm =1891 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 754);update biosicurezza_risposte set id_classyfarm =1892 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 754);update biosicurezza_risposte set id_classyfarm =1889 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 754);update biosicurezza_risposte set id_classyfarm =1890 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 754);
update biosicurezza_risposte set id_classyfarm =1894 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 755);update biosicurezza_risposte set id_classyfarm =1893 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 755);update biosicurezza_risposte set id_classyfarm =1896 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 755);update biosicurezza_risposte set id_classyfarm =1895 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 755);
update biosicurezza_risposte set id_classyfarm =2630 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 995);update biosicurezza_risposte set id_classyfarm =2629 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 995);update biosicurezza_risposte set id_classyfarm =2627 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 995);update biosicurezza_risposte set id_classyfarm =2628 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 995);
update biosicurezza_risposte set id_classyfarm =1922 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 763);update biosicurezza_risposte set id_classyfarm =1921 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 763);update biosicurezza_risposte set id_classyfarm =1923 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 763);update biosicurezza_risposte set id_classyfarm =1924 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 763);
update biosicurezza_risposte set id_classyfarm =1928 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 764);update biosicurezza_risposte set id_classyfarm =1927 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 764);update biosicurezza_risposte set id_classyfarm =1925 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 764);update biosicurezza_risposte set id_classyfarm =1926 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 764);
update biosicurezza_risposte set id_classyfarm =1930 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 765);update biosicurezza_risposte set id_classyfarm =1929 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 765);update biosicurezza_risposte set id_classyfarm =1931 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 765);update biosicurezza_risposte set id_classyfarm =1932 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 765);
update biosicurezza_risposte set id_classyfarm =1936 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 766);update biosicurezza_risposte set id_classyfarm =1935 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 766);update biosicurezza_risposte set id_classyfarm =1933 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 766);update biosicurezza_risposte set id_classyfarm =1934 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 766);
update biosicurezza_risposte set id_classyfarm =1940 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 767);update biosicurezza_risposte set id_classyfarm =1939 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 767);update biosicurezza_risposte set id_classyfarm =1937 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 767);update biosicurezza_risposte set id_classyfarm =1938 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 767);
update biosicurezza_risposte set id_classyfarm =1944 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 768);update biosicurezza_risposte set id_classyfarm =1943 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 768);update biosicurezza_risposte set id_classyfarm =1941 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 768);update biosicurezza_risposte set id_classyfarm =1942 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 768);
update biosicurezza_risposte set id_classyfarm =1946 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 769);update biosicurezza_risposte set id_classyfarm =1945 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 769);update biosicurezza_risposte set id_classyfarm =1947 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 769);update biosicurezza_risposte set id_classyfarm =1948 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 769);
update biosicurezza_risposte set id_classyfarm =1952 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 770);update biosicurezza_risposte set id_classyfarm =1951 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 770);update biosicurezza_risposte set id_classyfarm =1949 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 770);update biosicurezza_risposte set id_classyfarm =1950 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 770);
update biosicurezza_risposte set id_classyfarm =1954 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 771);update biosicurezza_risposte set id_classyfarm =1953 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 771);update biosicurezza_risposte set id_classyfarm =1955 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 771);update biosicurezza_risposte set id_classyfarm =1956 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 771);
update biosicurezza_risposte set id_classyfarm =1958 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 772);update biosicurezza_risposte set id_classyfarm =1957 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 772);update biosicurezza_risposte set id_classyfarm =1959 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 772);update biosicurezza_risposte set id_classyfarm =1960 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 772);
update biosicurezza_risposte set id_classyfarm =1962 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 773);update biosicurezza_risposte set id_classyfarm =1961 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 773);update biosicurezza_risposte set id_classyfarm =1963 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 773);update biosicurezza_risposte set id_classyfarm =1964 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 773);
update biosicurezza_risposte set id_classyfarm =1968 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 774);update biosicurezza_risposte set id_classyfarm =1967 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 774);update biosicurezza_risposte set id_classyfarm =1965 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 774);update biosicurezza_risposte set id_classyfarm =1966 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 774);
update biosicurezza_risposte set id_classyfarm =1970 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 775);update biosicurezza_risposte set id_classyfarm =1969 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 775);update biosicurezza_risposte set id_classyfarm =1971 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 775);update biosicurezza_risposte set id_classyfarm =1972 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 775);
update biosicurezza_risposte set id_classyfarm =1976 where risposta ilike 'Note - Motivo NA:' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 776);update biosicurezza_risposte set id_classyfarm =1975 where risposta ilike 'N/A' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 776);update biosicurezza_risposte set id_classyfarm =1973 where risposta ilike 'Si' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 776);update biosicurezza_risposte set id_classyfarm =1974 where risposta ilike 'No' and id_domanda in (select id from biosicurezza_domande  where id_classyfarm = 776);

-- Creo e popolo domande/risposte ext
CREATE TABLE biosicurezza_domande_ext (id serial, domanda text, codice text, trashed_date timestamp without time zone, id_classyfarm integer);
alter sequence biosicurezza_domande_ext_id_seq RESTART with 10000000;

insert into biosicurezza_domande_ext(domanda, codice, id_classyfarm) values ('Ndeg totale animali', 'NDEG_TOT', 691);
insert into biosicurezza_domande_ext(domanda, codice, id_classyfarm) values ('NOME E COGNOME DEL PROPRIETARIO/DETENTORE/CONDUTTORE PRESENTE ALLISPEZIONE', 'NOMECOGNOME_PRESENTE', 827);
insert into biosicurezza_domande_ext(domanda, codice, id_classyfarm) values ('Tipologia di suini presenti', 'TIPO_SUINI', 94);

CREATE TABLE biosicurezza_risposte_ext (id serial, id_domanda integer, risposta text, tipo text, trashed_date timestamp without time zone, id_classyfarm integer);
alter sequence biosicurezza_risposte_ext_id_seq RESTART with 10000000;

insert into biosicurezza_risposte_ext(id_domanda, risposta, tipo, id_classyfarm) values ((select id from biosicurezza_domande_ext where codice = 'NDEG_TOT'), '', 'text', 1634);
insert into biosicurezza_risposte_ext(id_domanda, risposta, tipo, id_classyfarm) values ((select id from biosicurezza_domande_ext where codice = 'NOMECOGNOME_PRESENTE'),'', 'text', 2153);
insert into biosicurezza_risposte_ext(id_domanda, risposta, tipo, id_classyfarm) values ((select id from biosicurezza_domande_ext where codice = 'NOMECOGNOME_PRESENTE'),'NA', 'checkbox', 3346);
insert into biosicurezza_risposte_ext(id_domanda, risposta, tipo, id_classyfarm) values ((select id from biosicurezza_domande_ext where codice = 'TIPO_SUINI'),'', 'text', 223);

-- Creo nuova tabella per salvare le risposte estese

CREATE TABLE biosicurezza_dati_ext (
id serial primary key, id_istanza integer, num_totale_animali text, nome_cognome_proprietario text, tipo_suini text, trashed_date timestamp without time zone);

-- Costruisco json di lista domande e risposte per setchecklist (salvo per riutilizzare con farmacosorveglianza)

SELECT 1, '{"IDCLCheckList": "19","IDCheckList": "biosicurezza-suini-03","IDRegione": "U150","IDASL": "R201","DataValidità": "2021-01-01T07:47:46.559Z","Domande": ['

UNION

(select 2, 
'{"IDCLDomanda": '||d.id_classyfarm||',"IDDomanda": '||d.id||',"DescrDomanda": "'|| regexp_replace(replace(d.domanda, '''', ''), E'[\\n\\r]+', ' ', 'g' )||'","Risposte": ['
|| string_agg('{"IDRisposta": '||r.id||',"Valore": "'||r.risposta||'", "IDCLRisposta": '||r.id_classyfarm||'}', ',')
||']},'

from biosicurezza_domande d
join biosicurezza_risposte r on d.id = r.id_domanda
group by d.id_classyfarm, d.id, d.domanda
order by d.ordine)

UNION

(select 3, 
'{"IDCLDomanda": '||d.id_classyfarm||',"IDDomanda": '||d.id||',"DescrDomanda": "'|| regexp_replace(replace(d.domanda, '''', ''), E'[\\n\\r]+', ' ', 'g' )||'","Risposte": ['
|| string_agg('{"IDRisposta": '||r.id||',"Valore": "'||r.risposta||'", "IDCLRisposta": '||r.id_classyfarm||'}', ',')
||']},'

from biosicurezza_domande_ext d
join biosicurezza_risposte_ext r on d.id = r.id_domanda
group by d.id_classyfarm, d.id, d.domanda
order by d.id)

UNION

SELECT 4, ']}'

order by 1

-- Importante: cancellare l'ultima virgola nell'ultimo pezzo
-- ID ASL CABLATA A R201 FINO A QUANDO NON LEVANO OBBLIGATORIETA
-- inviare questo json a setChecklist

------------------------------------------ GESTIONE INVIO

alter table biosicurezza_istanze add column data_invio timestamp without time zone;
alter table biosicurezza_istanze add column id_esito_classyfarm integer;
alter table biosicurezza_istanze add column descrizione_errore_classyfarm text;
alter table biosicurezza_istanze add column descrizione_messaggio_classyfarm text;

CREATE OR REPLACE FUNCTION public.get_info_chk_biosicurezza_istanza(
    IN _idcu integer)
  RETURNS TABLE(id integer, bozza boolean, id_esito_classyfarm integer, descrizione_errore_classyfarm text, descrizione_messaggio_classyfarm text, data_invio timestamp without time zone) AS
$BODY$
DECLARE
r RECORD;	
BEGIN
RETURN QUERY
select ist.id, ist.bozza, ist.id_esito_classyfarm, ist.descrizione_errore_classyfarm, ist.descrizione_messaggio_classyfarm, ist.data_invio from biosicurezza_istanze ist 
where ist.idcu = _idcu and ist.trashed_date is null;

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_info_chk_biosicurezza_istanza(integer)
  OWNER TO postgres;

  CREATE OR REPLACE FUNCTION public.get_chiamata_ws_login_classyfarm(_username text, _password text)
  RETURNS text AS
$BODY$
DECLARE
	ret text;
BEGIN

select 
concat('{"username": "', _username, '","password": "', _password, '"}') into ret;

RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_biosicurezza_risposte(_id integer)
  RETURNS text AS
$BODY$
DECLARE
	ret text;
BEGIN

select

string_agg(concat('{"IDDomanda": ', risp.id_domanda, ',
      "DescrDomanda": "', regexp_replace(replace(risp.descr_domanda, '''', ''), E'[\\n\\r]+', ' ', 'g' ), '",
      "IDRisposta": ', risp.id_risposta, ',
      "Valore": "', risp.valore, '"}'), ',') into ret
      
FROM (
SELECT

d.id as id_domanda,
d.domanda as descr_domanda,
r.id as id_risposta,
ir.note as valore
from biosicurezza_istanze i
left join biosicurezza_istanze_risposte ir on ir.id_istanza = i.id
left join biosicurezza_domande d on d.id = ir.id_domanda
left join biosicurezza_risposte r on r.id = ir.id_risposta
where i.id = _id and i.trashed_date is null and ir.trashed_date is null

UNION

select 
(select id from biosicurezza_domande_ext where codice = 'NDEG_TOT') as id_domanda,
(select domanda from biosicurezza_domande_ext where codice = 'NDEG_TOT')as descr_domanda,
(select id from biosicurezza_risposte_ext where risposta = '' and id_domanda in (select id from biosicurezza_domande_ext where codice = 'NDEG_TOT')) as id_risposta,
(select num_totale_animali from biosicurezza_dati_ext where id_istanza = _id and trashed_date is null) as valore

UNION

select 
(select id from biosicurezza_domande_ext where codice = 'NOMECOGNOME_PRESENTE') as id_domanda,
(select domanda from biosicurezza_domande_ext where codice = 'NOMECOGNOME_PRESENTE')as descr_domanda,
(select id from biosicurezza_risposte_ext where risposta = '' and id_domanda in (select id from biosicurezza_domande_ext where codice = 'NOMECOGNOME_PRESENTE')) as id_risposta,
(select nome_cognome_proprietario from biosicurezza_dati_ext where id_istanza = _id and trashed_date is null) as valore

UNION

select 
(select id from biosicurezza_domande_ext where codice = 'TIPO_SUINI') as id_domanda,
(select domanda from biosicurezza_domande_ext where codice = 'TIPO_SUINI')as descr_domanda,
(select id from biosicurezza_risposte_ext where risposta = '' and id_domanda in (select id from biosicurezza_domande_ext where codice = 'TIPO_SUINI')) as id_risposta,
(select tipo_suini from biosicurezza_dati_ext where id_istanza = _id and trashed_date is null) as valore
) risp;

 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;


CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_biosicurezza(_id integer)
  RETURNS text AS
$BODY$
DECLARE
	ret text;
BEGIN

select
concat(
'{ "IDCLCheckList": "', (select codice_classyfarm from lookup_chk_classyfarm_mod where codice_gisa = 'biosicurezza-suini-03') , '",
  "IDCL": "', 'biosicurezza-suini-03' , '",
  "IDRegione": "', 'U150' , '",
  "IDASL": "', 'R'||t.site_id , '",
  "DataVisita": "', t.assigned_date , '",
  "DataCompilazione": "', i.entered , '",
  "IDAzienda": "', o.account_number , '",
  "IDFiscale": "', o.codice_fiscale_rappresentante , '",
  "CodiceSpecie": "', o.specie_allevamento , '",
  "TpOperazione": 0,
  "ListaRisposte": [' , (select * from get_chiamata_ws_insert_biosicurezza_risposte (_id)) ,'
  ]}') into ret
  
  from biosicurezza_istanze i
  left join ticket t on t.ticketid = i.idcu
  left join organization o on o.org_id = t.org_id
  where i.id = _id;

 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
  
alter table biosicurezza_istanze add column note text;

CREATE TABLE log_invio_biosicurezza(id serial primary key, data_invio timestamp without time zone default now(), id_utente integer, tipo_invio text, id_invio_massivo integer, id_esito_classyfarm integer, descrizione_errore_classyfarm text, descrizione_messaggio_classyfarm text, id_istanza integer, id_controllo integer);

CREATE OR REPLACE FUNCTION public.biosicurezza_ist_aggiorna_stato(
    _id integer,
    _bozza boolean,
    _id_utente integer)
  RETURNS boolean AS
$BODY$

BEGIN
update biosicurezza_istanze set note = concat_ws('*',note,'Aggiornamento dello stato tramite dbi biosicurezza_ist_aggiorna_stato(integer,boolean, integer)'),
bozza= _bozza, modifiedby = _id_utente, modified=now() where id=_id;

return true;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;

  
CREATE OR REPLACE FUNCTION public.biosicurezza_ist_aggiorna_invio(
    _id integer,
    _id_esito_classyfarm integer,
    _descrizione_errore_classyfarm text,
    _descrizione_messaggio_classyfarm text,
    _id_invio_massivo integer,
    _id_utente integer)
  RETURNS boolean AS
$BODY$
DECLARE
idAlleg integer;

BEGIN

update biosicurezza_istanze set note = concat_ws('*',note,'Aggiornamento dello stato tramite dbi biosicurezza_ist_aggiorna_invio(integer,integer, text, text, integer)'),
id_esito_classyfarm = _id_esito_classyfarm, descrizione_errore_classyfarm =_descrizione_errore_classyfarm, descrizione_messaggio_classyfarm= _descrizione_messaggio_classyfarm, modifiedby = _id_utente, modified=now(), data_invio=now() where id=_id;

insert into log_invio_biosicurezza(id_utente, tipo_invio, id_invio_massivo, id_esito_classyfarm, descrizione_errore_classyfarm, descrizione_messaggio_classyfarm, id_istanza, id_controllo) values (_id_utente, case when _id_invio_massivo > 0 then 'massivo' else 'puntuale' end, _id_invio_massivo, _id_esito_classyfarm, _descrizione_errore_classyfarm, _descrizione_messaggio_classyfarm, _id, (select idcu from biosicurezza_istanze where id = _id))  ;

return true;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;

CREATE OR REPLACE FUNCTION public.get_esistenza_biosicurezza_istanza(_id_controllo integer)
  RETURNS boolean AS
$BODY$
DECLARE
	num_biosicurezza integer;
BEGIN

select count(*) into num_biosicurezza from biosicurezza_istanze where trashed_date is null and idcu = _id_controllo and bozza=false;

raise info '[Num checklist BIOSICUREZZA] %', num_biosicurezza;
	if(num_biosicurezza > 0) then
		return true;
	else 
		return false;
	end if;

END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;