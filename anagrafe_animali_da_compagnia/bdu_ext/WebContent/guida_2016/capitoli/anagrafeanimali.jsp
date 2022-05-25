<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<a name="anagrafe_animali"></a>
<h3 id="anagrafe_animali">Anagrafe Animali</h3>


	Il men&ugrave; anagrafe animali consente la gestione di tutte le
					specie animali presenti in banca dati.<br> <br> <IMG
						SRC="images/anagrafe_animali/anagrafe_animali.PNG"
						NAME="anagrafe_animali.PNG"/ >
				
				<br> In questo modulo &egrave; possibile effettuare operazioni
				del tipo:
				<ul>
					<li>Aggiungi animali
					<li>Ricerca animali
					<li>Pregresso
					<li>Stampa certificati da compilare (Certificato di
						Iscrizione, Richiesta di Iscrizione, Richiesta di Adozione da
						rifugio)
				</ul>
				<br>
				<H2>Aggiungi animale</H2>
					Il sottomen&ugrave; aggiungi animale permette l'inserimento di
					ognuna delle specie di animali che &egrave possibile censire in
					banca dati.<br> <IMG
						SRC="images/anagrafe_animali/add.PNG"
						NAME="add.PNG" />

					I campi contrassegnati con l'asterisco (<span style="color: red;">*</span>)
					sono obbligatori. Dal men&ugrave; a tendina accanto alla dicitura
					"Specie animale" &egrave possibile scegliere la specie dell'animale
					che si sta inserendo. In base alla specie scelta, le informazioni
					necessarie e/o obbligatorie possono variare. * Per dettagli
					sull'inserimento di animali in canile, vedere il paragrafo dedicato
					alla <a href="#Gestione canili">Gestione Canili</a><br/>
					ATTENZIONE: non è consentito inserire animali con proprietari di altra nazione e, solo per i liberi professionisti e Università, di altra regione.<br/>
					N.B.:Si ricorda che, se l'animale viene anagrafato contestualmente all'inserimento dell'accettazione di Vam, l'origine inserimento visualizzata nel dettaglio sarà Vam.<br/>
					Negli altri casi (compreso 'Accesso BDR' da Vam) l'orgine sarà BDU.
					

<br/><br/>
<a name="provenienzaanimale"></a>
					<H2>Dati provenienza animale mai anagrafato</H2>
			
Nel caso di cani "non randagi" (cio&egrave; non attribuiti a proprietario Sindaco) di et&agrave; inferiore all'anno all'atto dell'inserimento in Anagrafe, &egrave; 
necessario specificare la provenienza dell'animale.<br/>
Le possibilit&agrave; sono:<br/>
1) Cane gi&agrave; anagrafato e quindi provenire da banca dati (anagrafe) di altra
regione.<br/>
2) Cane mai anagrafato proveniente da:
<ul>
<li>Nazione estera</li>
<li>Ex-proprietario</li>
<li>Ritrovamento</li>
<li>Acquisto online</li>
</ul>
In ultima analisi, qualora per l'utente veterinario che sta anagrafando il cane non sia possibile in nessun modo risalire alla provenienza del cane, &egrave;
necessario dichiarare esplicitamente questa impossibilit&agrave; mediante l'apposita spunta che comparir&agrave; dopo aver letto la pagina di manuale a cui rimanda il link 
"Click QUI per maggiori dettagli".
			
<br/>
					<IMG
						SRC="images/anagrafe_animali/add_new_version.png"
						NAME="add_.PNG" />
					<br/>

<br/>
<u>CASO 1: Provenienza da anagrafe altra regione</u><br/>
Qualora l'animale provenga da un'altra anagrafe e quindi sia gi&agrave; microcippato occorre spuntare
 il flag "Provenienza da anagrafe altra regione" e specificare la regione di provenienza 
 compilando eventualmente anche il campo note, come mostrato nella figura seguente:

	<IMG
						SRC="images/anagrafe_animali/add1.PNG"
						NAME="add1.PNG" />
					<br/>

NOTA: la gestione degli animali provenienti da altra anagrafe e quindi gi&agrave; microchippati &egrave; 
disponibile solo per gli utenti  con profilo ASL.<br/><br/>

<u>CASO 2: Provenienza animale mai anagrafato -  in regione</u>
Qualora l'animale provenga dalla Campania occorre scegliere l'opzione "In regione"  e 
successivamente specificarne la provenienza: "Da proprietario" o "Da ritrovamento".

	<IMG
						SRC="images/anagrafe_animali/add2.PNG"
						NAME="add2.PNG" />
					<br/>
Selezionando come provenienza "Da proprietario" il sistema chiede di specificare i 
dati del proprietario precedente mostrando la seguente schermata:


	<IMG
						SRC="images/anagrafe_animali/add3.PNG"
						NAME="add3.PNG" />
					<br/>
					
In caso di difficolt&agrave; nel reperire le informazioni complete del proprietario di provenienza 
(sia 'in regione' che 'fuori regione'), si ha la possibilit&agrave;, tramite il bottone "aggiungi", 
di disporre di una maschera di inserimento proprietario pi&ugrave; semplice dove sono considerati 
obbligatori i seguenti campi:
<ul>
<li>Cognome;</li>
<li>Sesso;</li>
<li>Comune di residenza.</li>
</ul>
Selezionando invece come provenienza "Da ritrovamento" il sistema chiede di specificare i 
dettagli sul luogo del ritrovamento mostrando la schermata seguente:


	<IMG
						SRC="images/anagrafe_animali/add4.PNG"
						NAME="add4.PNG" />
					<br/>
<br/>					
<u>CASO 3: Provenienza animale mai anagrafato  -  fuori regione</u>
Qualora l'animale provenga da fuori regione occorre scegliere l'opzione "Fuori regione" 
e successivamente specificarne la provenienza come nel CASO 2.


	<IMG
						SRC="images/anagrafe_animali/add5.PNG"
						NAME="add5.PNG" />
					<br/>
					
NOTA: nel caso di proprietario "Fuori Regione" sar&agrave; possibile solo l'aggiunta e non la ricerca 
dell'ex-proprietario.
<br/><br/>

<u>CASO 4: Provenienza animale mai anagrafato  -  da nazione estera</u>
<br/>Qualora l'animale provenga dall'estero occorre scegliere l'opzione "Provenienza da nazione 
estera"  e specificare la nazione di provenienza, come riportato nella schermata seguente:


	<IMG
						SRC="images/anagrafe_animali/add6.PNG"
						NAME="add6.PNG" />
					<br/>
					
NOTA: la gestione del 'fuori nazione' &egrave; disponibile solo per gli utenti  con profilo ASL.
<br/><br/>

<u>CASO 5: Informazioni mancanti sull'origine dell'animale</u>
<br>Qualora tutti i tentativi di ottenere informazioni complete dovessero fallire l'utente 
pu&ograve; scegliere l'opzione "Informazioni mancanti sull'origine animale". Spuntando tale flag 
il sistema mostra il seguente messaggio "Attenzione! Iscrizione in banca dati di cane privo 
di tracciabilit&agrave;." e disabilita tutti gli altri campi.<br/>

I dati inseriti saranno disponibili e visibili nella scheda di dettaglio dell'animale e nella
 registrazione di prima iscrizione. Inoltre nel documento "Richiesta di prima iscrizione" sar&agrave; 
 disponibile l'autocertificazione con le informazioni sulla provenienza animale dichiarate dal 
 proprietario in fase di iscrizione.
 
 
	<IMG
						SRC="images/anagrafe_animali/add7.PNG"
						NAME="add7.PNG" />
					<br/>
					
NOTA: 
<ul><li>I campi contrassegnati da * sono obbligatori.</li>
<li>Nel caso in cui si dovesse decidere di compilare il campo note occorre specificare un numero minimo di caratteri.</li>
</ul>
<br/>
<u>Gestione Acquisto on-line</u>
<br/>Qualora l'animale sia stato acquistato su internet &egrave; possibile spuntare l'opzione 
"Acquisto on-line" qualsiasi sia l'origine (in regione, fuori regione, ecc.). In caso di selezione 
bisogner&agrave; poi specificare i dettagli.


	<IMG
						SRC="images/anagrafe_animali/add8.PNG"
						NAME="add8.PNG" />
					<br/>
					
					
					<br/>
<u>Visualizzazione informazioni origine nel dettaglio animale</u><br/>
Le informazioni inserite sull'origine dell'animale, sono disponibili nella scheda 
dettaglio(e nella registrazione di iscrizione in anagrafe) . In fase di modifica scheda animale 
&egrave; possibile modificarle.

<br/>
					
<br/><u>Gestione "Allegati"</u><br/>
Per allegare il documento firmato (la richiesta di prima iscrizione con l'autocertificazione firmata 
a cura del proprietario animale) occorre accedere al men&ugrave; "Allegati":


	<IMG
						SRC="images/anagrafe_animali/add9.PNG"
						NAME="add9.PNG" />
					<br/>
					
Sono disponibili due funzioni:
<br><b>1)</b>&nbsp;&nbsp;	crea nuova cartella: permette di creare un archivio (cartella) di file. Una cartella creata pu&ograve; 
essere rinominata o cancellata mediante le apposite funzioni.


	<IMG
						SRC="images/anagrafe_animali/add10.PNG"
						NAME="add10.PNG" />
					<br/>
					

<br><b>2)</b>&nbsp;&nbsp;	carica file: permette di allegare i file. Un file caricato pu&ograve; essere cancellato mediante apposita funzione.

	<IMG
						SRC="images/anagrafe_animali/add11.PNG"
						NAME="add11.PNG" />
					<br/>


<br/><br/>


<H2>Verifica esistenza microchip</H2>
					
L'opzione consente di verificare la presenza o meno di microchip nel sistema tramite l'upload di un file.<br/>
Per ogni Microchip inserito nel file di input, è riportato se è presente o meno in banca dati.<br/><br/>

Qualora dovesse essere presente, sono indicate le seguenti informazioni: <br/>
<ul>
<li>Asl</li> 
<li>Se il cane è presente regolarmente o è stato cancellato logicamente dalla banca dati</li> 
<li>Eventuale data di sterilizzazione</li> 
<li>Eventuale Contributo regionale</li>
</ul> 
			
	<br/><br/>
			
<H2>Gestisci decessi</H2>
					
 Consente di cercare nel sistema animali che hanno più di 25 o 30 anni e di registrare un "decesso d'ufficio".	<br/>
 Nella maschera di ricerca &egrave; possibile filtrare i risultati per proprietario, detentore, stato(cessione) e età.<br/><br/>
 <IMG SRC="images/anagrafe_animali/decessi_dufficio.jpg" NAME="decessi d ufficio" /> <br/><br/>
 Dalla lista dei risultati di ricerca &egrave; possibile selezionare gli animali a cui si vuole applicare il decesso d'ufficio. Dopo aver effettuato la selezione di almeno un animale cliccare su
 'Procedi' per registrare il decesso.
 
 
 <br/><br/>

<H2>Recupera esiti esami leishmaniosi </H2>

Consente di recuperare gli esiti degli esami leishmaniosi filtrando per valore dell'esito: tutti/dubbio/negativo/positivo, asl di interesse e finestra temporale.

 <br/><br/>
 
<H2>Scheda adozione cani</H2>

Nella maschera di dettaglio di un cane randagio e detenuto in canile &egrave; disponibile in alto il link 'Scheda Adozione Cani' che riporta alla gestione dell'omonima funzione.<br/> 
Cliccando sul link si apre una form per compilare o visualizzare la checklist(se gi&agrave; inserita). <br/>
Per compilare la checklist si deve spuntare obbligatoriamente una e solo una voce relativa all'indice per ogni criterio.<br/>
La colonna punteggio viene aggiornata in tempo reale in base alla seguente regola:<br/>
- Indice Adeguato: Punteggio = 1;<br/>
- Indice Rivalutare: Punteggio = 2;<br/>
- Indice Inadeguato: Punteggio = 3;<br/>
Per salvare la scheda cliccare su 'Salva'.
<br/><br/>
Nel dettaglio della scheda adozione &egrave; possibile visualizzare i dati inseriti e la valutazione attribuita in automatico dal sistema sulla base del punteggio totale calcolato sommando i punteggi di ogni criterio:<br/>
- PUNTEGGIO TOTALE &lt;= 7: Adottabile;<br/>
- PUNTEGGIO TOTALE tra 8 e 12: Adottabile con riserva;<br/>
- PUNTEGGIO TOTALE &gt; 12: Non adottabile.
<br/><br/>
Inoltre nel dettaglio della scheda adozione &egrave; possibile:
- modificare i dati inseriti cliccando sul bottone 'Modifica'. La maschera di modifica si presenta uguale a quella di inserimento ma con i campi precompilati e valgono gli stessi criteri di obbligatoriet&agrave; e attribuzione del punteggio. La scheda inserita &egrave; modificabile fino a che il cane non viene adottato.  
- stampare la scheda salvata. Tutti i documenti precedentemente stampati sono sempre visibili cliccando sul tasto 'Stampa' della scheda.
<br/><br/>
Se per un'animale &egrave; stata inserita una scheda di adozione, nel dettaglio dello stesso vengono mostrate le seguenti informazioni:<br/>
- Valutazione;<br/>
- Data;<br/>
- Modificato da.<br/>


<br/><br/>
 
<H2>Scheda morsicatura cani</H2>

Nella maschera di dettaglio di un cane &egrave; disponibile in alto il link 'Scheda di Valutazione Rischio Morsicatore' che mostra la lista delle schede morsicatura inserite prima della gestione tramite registrazioni.<br/> 
<!-- La scheda &egrave; associata ad un evento di morsicatura, pertanto non sar&agrave; prevista la modifica di una scheda preesistente, ma solo l'aggiunta di una nuova scheda.<br/>
Cliccando sul link si apre una form per compilare o visualizzare la checklist(se gi&agrave; inserita). <br/>
Per compilare la checklist si deve spuntare obbligatoriamente una e solo una voce relativa all'indice per ogni criterio.<br/>
Per il criterio PESO E MASSA si devono inserire entrambi i valori numerici di peso e massa.
La colonna valore viene aggiornata in tempo reale utilizzando per il criterio A la formula (4 * peso cane/peso vittima) e per gli altri criteri il numero progressivo della singola voce.<br/>
Per salvare la scheda cliccare su 'Salva'.<br/>
Dopo il salvataggio il sistema scarica in automatico la stampa della scheda.
<br/><br/> -->
Nel dettaglio di una scheda di morsicatura &egrave; possibile visualizzare i dati inseriti e l'indice di rischio attribuito in automatico dal sistema sulla base del punteggio totale calcolato sommando i punteggi di ogni criterio:<br/>
- PUNTEGGIO TOTALE &lt;= 10: Rischio Basso;<br/>
- PUNTEGGIO TOTALE &gt; 10 e &lt;=14: Rischio Medio;<br/>
- PUNTEGGIO TOTALE &gt; 14 e &lt;=15,5: Rischio Elevato;<br/>
- PUNTEGGIO TOTALE &gt; 15.5: Rischio Considerevole.
<br/><br/>
Nel dettaglio della scheda morsicatura &egrave; possibile stampare la scheda salvata. <!-- Tutti i documenti precedentemente stampati sono sempre visibili cliccando sul tasto 'Stampa' della scheda. -->
<!-- <br/><br/>
Se per un'animale sono state inserite una o pi&ugrave; schede di morsicatura, nel dettaglio dello stesso vengono mostrate le seguenti informazioni basate sull'ultima scheda inserita:<br/>
- Rischio;<br/>
- Consiglio;<br/>
- Data;<br/>
- Modificato da.<br/>
In presenza di pi&ugrave; schede inserite, il tasto per accedere alla form di inserimento di una nuova checklist &egrave; presente nella scheda di dettaglio. -->



<H2>REGISTRO UNICO CANI A RISCHIO ELEVATO DI AGGRESSIVITA' O.M. 06/08/2003 e s.m.i. art 3 comma 3</H2>
Il registro &egrave; consultabile tramite l'apposito sottomenu posto nel cavaliere 'Anagrafe animali'.<br/>
Il registro elenca gli animali che hanno una registrazione di morsicatura o di aggressione dove si verificano le condizioni che sono stata descritte nei paragrafi riguardanti le due registrazioni.
Il registro &egrave; scaricabile dal sistema di reportistica.<br/>
Entrando nel registro si deve selezionare l'anno da consultare.
Per ogni anno sar&agrave; visualizzata la lista delle registrazioni. La lista &egrave; filtrata sulla base dell'asl utente e dell'asl del proprietario al momento dell'evento.<br/>
Cliccando sulla singola riga si visualizza il dettaglio del record del registro: registrazione della BDU e del controllo ufficiale inserito in Gisa.
L'asl del proprietario &egrave; quella al momento dell'evento
DATI PROPRIETARIO sono cognome e nome del proprietario al momento dell'evento.
I dati sulle prescrizioni sono recuperate dal controllo ufficiale messo in GISA. 
Nel dettaglio si pu&ograve;  stampare un documento con i dati del registro.


