<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<a name="Gestione canili"></a>
<h3 id="Gestione canili">Gestione canili</h3>

L'aggiunta di canili autorizzati dalla regione
											Campania è un'attività riservata esclusivamente all'help desk
											di primo livello. Le informazioni necessarie all'aggiunta di
											un canile sono fondamentalmente 5: </br></br>

											<ul>

												<li>Dati rappresentante legale</li>


												<li>Dati sede legale</li>


												<li>Dati responsabile stabilimento</li>


												<li>Dati sede operativa</li>
												
												<li>Informazioni generiche</li>



											</ul>

											<br></br> Per procedere all'aggiunta del canile, l'utente
											deve accedere alla sezione "Proprietari/Detentori",
											sottomodulo "Aggiungi Proprietario". Si presenterà un menù a
											tendina dal quale scegliere la tipologia di operatore che si
											vuole inserire. La tipologia corretta per aggiungere un
											canile è quella contrassegnata dalla voce "Canile". Per
											completare l'inserimento l'utente sarà guidato attraverso due
											sezioni: nella prima aggiungerà i dati legali (sede e
											rappresentante), nella seconda i dati della sede
											operativa(responsabile e sede). 
											Nella seconda schermata saranno richieste anche le informazioni generiche, rappresentate
											nella figura seguente:
											 <IMG
												SRC="images/Gestione_Canili/info_aggiuntive.png"
												NAME="2.PNG" /></br></br>
											Le informazioni generiche non sono obbligatorie, ad eccezione del dato relativo alla superficie destinata al ricovero.
											Tale informazione è espressa in metri quadrati. I valori accettati sono solo numerici.
											Questa informazione è indispensabile alla funzionalità descritta nel seguito e relativa al controllo dell'occupazione dei canili.
											
											</br></br>
											<H2>Gestione controllo occupazione canili</H2>
											
											Il sistema permette il controllo dell'occupazione corrente di un canile. Il controllo è possibile in base alle seguenti informazioni memorizzate nel sistema:
											</br></br>
											<ul>

												<li>Superficie a disposizione del canile destinata al ricovero</li>


												<li>Numero animali attualmente presenti in canile (vivi, non smarriti, non in stato di furto)</li>


												<li>Informazione occupazione singole taglie (espressa in mq, tabella 1.0)</li>

											</ul>
											
											</br></br>
											In fase di inserimento animale in un canile, il sistema controlla l'attuale occupazione del canile e quella aggiornata con il nuovo inserimento.
											Se tale valore non supera la capienza della struttura o il numero di 350 cani detenuti, il sistema permetterà l'inserimento. In caso contrario il sistema bloccherà l'utente invitandolo a verificarne i dati.
											Il controllo è fatto in occasione di ogni ingresso al canile, quindi in fase di inserimento nuova anagrafica (randagio / privato) e 
											in fase di registrazioni di movimentazioni animali verso la struttura.
											Se il valore immesso in fase di inserimento anagrafica canile relativamente alla superficie destinata al ricovero è 0, il sistema non effettuerà il controllo.
											
											
											</br></br>
											<table border="1">
											<tr><th>Taglia</th><th>Occupazione (mq))</th>
											<tr><td>piccola (cani di peso non superiore a 2 kg)</td> <td align="center">2</td></tr>
											<tr><td>media (cani di peso non superiore a 8 kg)</td> <td align="center">3.5</td></tr>
											<tr><td>grande (cani di peso non superiore a 15kg)</td> <td align="center">4.5</td></tr>
											<tr><td>gigante (cani di peso superiore a 15 kg) </td> <td align="center">6</td></tr>
											
											</table>
											
											
<br/>		<br/>										
											
<h2>Aggiornamento Taglie Massivo</h2>

Questa funzionalità è disponibile nella maschera di dettaglio dei canili per utenti di ruolo 'Help Desk'.</br>
Cliccando sul tasto 'Aggiorna Taglie' vengono mostrati i campi 'motivazione', 'file' e il pulsante 'Salva'.</br>
<IMG SRC="images/Gestione_Canili/aggiorna_taglie_1.jpg" NAME="Aggiorna taglie" /></br></br>
Compilando questi due campi obbligatori e cliccando su 'Salva' il sistema elabora il file e procede all'aggiornamento delle taglie dei cani nel canile.<br/>
</br>
Il file caricato deve essere un csv con il carattere ";" usato come separatore.<br/>
Il formato di ogni riga del file è il seguente: microchip|tatuaggio|taglia.<br/>
I valori ammessi per il campo taglia sono:<br/>
<ul>
<li>1 = Piccola (cani di peso non superiore a 2 kg)</li>
<li>2 = Media (cani di peso non superiore a 8 kg)</li>
<li>3 = Grande (cani di peso non superiore a 15kg)</li>
<li>5 = Gigante (cani di peso superiore a 15 kg)</li>
</ul>
</br>
Il sistema effettua i seguenti controlli su ogni riga del file:
<ul>
<li>l'animale è di proprietà o detenuto nel canile;</li>
<li>l'animale esiste nel sistema;</li>
<li>la taglia è valorizzata;</li>
<li>il valore inserito per la taglia è uno dei 4 ammessi: 1,2,3 e 5;</li>
<li>il microchip o il tatuaggio sono valorizzati</li>
</ul>

Dopo l'elaborazione il sistema ritorna la seguente schermata di riepilogo riportante, per ogni riga del file dato in input, l'esito dell'elaborazione: 
in verde le righe con esito positivo e in rosso le righe con esito negativo.

<IMG SRC="images/Gestione_Canili/aggiorna_taglie_2.jpg" NAME="Aggiorna taglie - riepilogo" />

Il sistema aggiorna il campo note visualizzabile nel dettaglio ogni animale aggiungendo la motivazione inserita e la vecchia taglia.
<IMG SRC="images/Gestione_Canili/aggiorna_taglie_3.jpg" NAME="Aggiorna taglie - note su dettaglio animale" />
</br></br>

<h2>Guida sospensione/riattivazione ingressi cani in canili</h2>

La gestione della sospensione e riattivazione ingressi  per i canili consiste in un "blocco manuale" degli 
ingressi cani nei canili e deve essere utilizzata per "forzare" la sospensione degli ingressi anche quando 
i mq sono ancora disponibili. <br/>
Nella maschera di dettaglio del canile, per l'utente HD di I livello, sono disponibili all'occorrenza due 
bottoni::<br/>
<ul>
<li>Sospendi ingressi</li>
<li>Riattiva ingressi</li>
</ul>
<IMG SRC="images/blocco_canili/bloccoCanile01.PNG"/></br><br/>

<b><u>Sospendi ingressi</u></b><br/>
Inizialmente è visibile solo il bottone di sospensione ingressi. Cliccando su esso si aprirà una popup con 
i seguenti campi obbligatori:
<ul>
<ol>1) data sospensione ingressi</ol>
<ol>2) note</ol>
<ol>3) motivo a scelta da una lista precompilata:
	<ul>
	<li>Richiesta referenti Asl;</li>
	<li>Richiesta referenti regionali;</li>
	<li>Altro</li>
	</ul>
</ol>
</ul>
<IMG SRC="images/blocco_canili/bloccoCanile02.PNG"/></br><br/>

 
Successivamente, cliccando su "Inserisci", sarà disabilitata la possibilità di aggiungere animali al canile. 
I dati inseriti sono mostrati a video, nella scheda canile, con le seguenti informazioni:
<ul>
<li>Sospensione ingressi a partire dal GG/MM/AAAA</li>
<li>Note</li>
<li>Motivo</li>
</ul>

<IMG SRC="images/blocco_canili/bloccoCanile03.PNG"/></br><br/>


<b><u>Riattiva ingressi</u></b><br/>
È possibile riabilitare gli ingressi cliccando sul bottone "Riattiva ingressi". Si apre una maschera nella 
quale bisognerà inserire i seguenti campi obbligatori:
<ul>
<ol>1) data riattivazione ingressi</ol>
<ol>2) note</ol>
<ol>3) motivo a scelta da una lista precompilata:
	<ul>
	<li>Richiesta referenti Asl;</li>
	<li>Richiesta referenti regionali;</li>
	<li>Altro</li>
	</ul>
</ol>
</ul>

Successivamente, cliccando su "Inserisci", verrà riabilitata la possibilità di aggiungere animali al canile. 
I dati inseriti sono mostrati a video, nella scheda canile, con le seguenti informazioni:
<ul>
<li>riattivazione ingressi a partire dal GG/MM/AAAA</li>
<li>Note</li>
<li>Motivo</li>
</ul>

Sempre nel dettaglio del canile, qualora presente, sarà disponibile lo storico dei vari blocchi eseguiti.
 
<IMG SRC="images/blocco_canili/bloccoCanile09.PNG"/></br><br/>


Per quanto concerne la gestione delle date da inserire, in linea generale si dovrà seguire la seguente logica:
<ul>
<ol>1) la data di sospensione ingressi dovrà essere > della data inizio attività (qualora disponibile)</ol>
<ol>2) la data riattivazione ingressi dovrà essere > della data sospensione ingressi</ol>
</ul>

Il controllo se un canile è bloccato in ingresso o meno non avviene solo in fase di aggiunta di un cane, bensì 
in tutte le seguenti tipologie di registrazione:
<ul>
<li>aggiunta animale</li>
<li>cambio detentore</li>
<li>cattura</li>
<li>ricattura</li>
<li>cessione</li>
<li>presa in carico da cessione</li>
<li>ritrovamento</li>
<li>ritrovamento non denunciato</li>
<li>trasferimento</li>
<li>trasferimento canile</li>
<li>adozione da canile verso associazioni/canile</li>
</ul>

Si evidenzia inoltre che, il funzionamento del blocco ha valenza in tutti i suoi periodi delimitati da un 
inizio e fine sospensione ingressi (visualizzabili, come già accennato, nello storico dei blocchi). Ad esempio,
 se un canile risulta bloccato dal 11/01/2017 al 21/01/2017, tutte le registrazioni sopraelencate con 
 "Data registrazione"" compresa in questo RANGE  non saranno possibili. Il sistema resituirà un messaggio 
 come mostra la figura sottostante:
 
 <IMG SRC="images/blocco_canili/bloccoCanile08.PNG"/></br><br/>
 

E' bene tener presente che, qualora l'azione di sospensione o riattivazione ingressi sia programmata in data 
posteriore alla data odierna, essa non sarà EFFETTIVA fino alla data settata; ci sarà la possibilità di 
ANNULLARE l'eventuale azione.
 
 <IMG SRC="images/blocco_canili/bloccoCanile06.PNG"/></br><br/>
 

In conclusione, nella lista di canili ricercabili dal cavaliere 'Proprietari/Detentori' comparirà anche un 
campo riportante lo stato relativo al blocco manuale.

<IMG SRC="images/blocco_canili/bloccoCanile07.PNG"/></br>

 
E gli eventuali canili bloccati saranno visualizzati anche nella homepage.
 
 <IMG SRC="images/blocco_canili/bloccoCanile10.PNG"/></br><br/>
 


											