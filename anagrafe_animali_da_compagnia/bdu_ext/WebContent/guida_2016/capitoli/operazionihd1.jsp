<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<dhv:permission name="anagrafe_canina_operazioni_hd-add">

<a name="Operazioni Help Desk"></a>
<h3 id="Operazioni Help Desk">Operazioni Help Desk</h3>


												<br> Sono state abilitate, per gli utenti Help Desk di
												primo livello, alcune funzionalità di modifica / correzione
												dati errati. Nel seguito i dettagli. <br></br>
												<H2>Sostituzione microchip</H2>
												<br> Per effettuare la modifica del primo microchip di
												un animale è necessario accedere al modulo "Anagrafe
												Animali", selezionare l'animale oggetto della modifica e
												accedere alla maschera di modifica. Da questa, il campo
												microchip risulterà modificabile. Effettuata la modifica,
												sarà necessario cliccare sul tasto "Salva". E' importante
												tenere presente che tale modifica avrà impatto anche sui
												sistemi VAM e GISA per quanto riguarda rispettivamente
												posizioni aperte (accettazioni in VAM, controlli inseriti in
												GISA a carico degli animali padronali). <br></br>
												<H2>Modifica Proprietario / Detentore animale</H2>
												<br> Per effettuare la modifica del proprietario o del
												detentore di un animale è necessario accedere al modulo
												"Anagrafe Animali", selezionare l'animale oggetto della
												modifica e accedere alla maschera di modifica. Da questa, è
												possibile effettuare la ricerca del nuovo proprietario o del
												nuovo detentore. Si precisa che l'operatore deve verificare,
												in primis, che la modifica non crei problemi e sia
												congruente con la storia precedente dell'animale. In
												particolare, il sistema avvertirà nesi seguenti casi che
												possono introdurre problemi nella storia dell'animale,
												tuttavia l'operatore avrà la facoltà di procedere: <br></br>
												<ul>
													<li>1. Risulta iscritto in banca dati da un periodo
														superiore ai 15 giorni</li>
													<br></br>
													<li>2. Ha delle registrazioni che implicano il
														trasferimento di proprietà in seguito alla prima
														iscrizione: Trasferimento Cessione Adozione Adozione fuori
														asl (In questo caso le informazioni relative a queste
														registrazioni e che possono includere informazioni sul
														proprietario / detentore antecedente la modifica non
														vengono aggiornate. La modifica di queste ultime
														operazioni continua ad essere una delle operazioni
														riservate all'HD di secondo livello.)</li>
													<br></br>
													<li>3. Viene selezionato un proprietario di asl
														diversa da quella attuale dell'animale. (In questo caso
														l'asl dell'animale viene modificata, restano invariate le
														informazioni sull'asl di inserimento animale e di tutte le
														registrazioni precedenti. La modifica di queste ultime
														operazioni continua ad essere una delle operazioni
														riservate all'HD di secondo livello.)</li>
												</ul>
												<br></br> L'operatore Help Desk avrà anche la possibilità di
												selezionare l'opzione "Aggiorna dati prima iscrizione ". Se
												selezionata questa opzione, saranno aggiornate al nuovo
												proprietario / detentore anche le informazioni di prima
												iscrizione. Effettuata la modifica, sarà necessario cliccare
												sul tasto "Salva". <br></br>

												<H2>Unificazione proprietari di tipo privato con
													trasferimento animali</H2>
												<br> Lo scopo di questa funzionalità è quella di
												permettere agli operatori HD primo livello l'unificazione di
												due proprietari con il relativo trasferimento di animali.
												Per usufruire di tale funzionalità è necessario accedere al
												menù "Proprietari/Detentori" e selezionare il sottomenù
												"Unificazione proprietari privati". Dalla maschera che si
												aprirà si dovranno selezionare il proprietario da eliminare
												e quello che lo sostituirà. La responsabilità
												dell'unificazione è dell' utente che esegue l'operazione: il
												sistema infatti non includerà nessuna verifica di
												similitudine tra i due soggetti coinvolti. Dopo avere
												confermato la sostituzione, il proprietario cancellato non
												sarà più visibile nel sistema e tutte le operazioni /
												registrazioni che lo coinvolgono saranno trasferite al nuovo
												soggetto. In particolare, quindi, saranno aggiornati al
												nuovo soggetto: <br></br>
												<ul>
													<li>Tutti i riferimenti di proprietari/detentore al
														soggetto cancellato</li>
													<li>Tutti i riferimenti nelle registrazioni animali
														che riguardano il proprietario/detentore corrente all'atto
														dell'inserimento e che si riferiscono al soggetto
														cancellato</li>
													<li>Tutti i riferimenti al soggetto cancellato
														presenti nelle registrazioni di movimentazione animali</li>
												</ul>
												<br></br>


												<H2>Eliminazione proprietari</H2>
												<br> Lo scopo di questa funzionalità è quella di
												permettere agli operatori HD primo livello la cancellazione
												dal sistema di proprietari / detentori. Per usufruire di
												tale funzionalità è necessario accedere al menù
												"Proprietari/Detentori", ricercare il proprietario da
												cancellare e, dalla pagina di dettaglio di quest'ultimo,
												cliccare sul pulsante "Elimina". Si aprirà una popup nella
												quale sarà obbligatorio inserire le motivazioni
												dell'operazione di eliminazione. Il sistema impedirà la
												cancellazione di soggetti attualmente proprietari o
												detentori di animali o coinvolti in registrazioni non
												cancellate a carico di qualche animale. <br></br>
												
												</dhv:permission>
