<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<a name="Profilassi Leishmania"></a>
<h3 id="Profilassi Leishmania">Profilassi Leishmania</h3>

<br> Il modulo "Profilassi Leishmania" racchiude al suo
											interno le seguenti funzionalit�:

											<ul>
												<li>Inserimento registrazioni di prelievo campioni
													Leishmania</li>
												<li>Recupero esiti esami leshmaniosi</li>
											</ul>

											<br></br> In particolare, attraverso il sottomodulo "Aggiungi
											registrazione prelievo campioni leishmania", gli LL.PP.
											avranno la possibilit� di inserire le informazioni sui
											prelievi campioni effettuati e destinati all'invio per le
											richieste di esami leshmaniosi. I LP avranno la possibilit�
											di inserire registrazioni di prelievo anche per i microchips
											non anagrafati direttamenti da loro. I dati necessari per
											l'inserimento di una registrazione di prelievo saranno:
											<ul>
												<li>Microchip dell'animale oggetto del prelievo</li>
												<li>La data di prelievo del campione</li>
												<li>Note eventuali</li>
											</ul>

											<br></br> L'utente sar� avvisato in caso di dati errati o
											mancanti. In particolare tutti i dati saranno obbligatori, ad
											eccezione delle note. Il sistema verificher� la correttezza
											del microchip , in particolare la correttezza formale, la
											presenza in banca dati, l'esistenza in uno stato corretto
											(animale vivente, di specie cane, non in uno stato di
											smarrimento o furto). Se il microchip superer� i contolli di
											cui sopra, si sbloccher� il campo per l'inserimento della
											data del prelievo e il veterinario potr� procedere con
											l'invio dei dati. Il sistema provveder� alla memorizzazione
											della registrazione e, successivamente, permetter� la stampa
											dei seguenti documenti: certificato di prelievo campioni
											(formato pdf) barcode relativo al microchip per cui � stato
											effettuato il prelievo (formato immagine) E' importante
											tenere presente che in seguito non sar� possibile la ristampa
											del certificato di prelievo, pertanto � opportuno stampare /
											salvare tale certificato subito dopo l'inserimento dei dati
											di prelievo. Il secondo sottomodulo a disposizione degli
											utenti, "Recupero esiti esami leshmaniosi", permetter� il
											recupero, per la visualizzazione, degli esiti gi� ricevuti da
											SIGLA / IZSM. La maschera di ricerca esiti preveder� i
											seguenti campi di ricerca: Valore esito Intervallo temporale
											Asl animale (attuale, che potrebbe essere diversa da quella
											all'inserimento dell'esito) I risultati della ricerca
											includeranno tutti gli esiti ricevuti per quei michrochips
											per i quali il LP ha effettuato almeno una richiesta di
											prelievo con data antecedente la data dell'esito,
											indipendentemente dal Veterinario prelevatore. Dalla pagina
											di lista degli esiti, cliccando sul microchip, il veterinario
											privato potr� accedere direttamente al dettaglio animale se
											si tratta di un cane che egli stesso ha anagrafato in banca
											dati. In caso contrario, il microchip non sar� cliccabile ma
											sar� visibile solo l'esito.