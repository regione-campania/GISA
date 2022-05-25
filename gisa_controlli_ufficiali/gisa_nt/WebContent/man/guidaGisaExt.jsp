<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script>

function vaiALink(link)
{
	document.location.href='#'+link;
}

function openPopup(url) {
	  title  = '_types';
	  width  =  '1000';
	  height =  '600';
	  resize =  'yes';
	  bars   =  'yes';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open(url, title, windowParams);
}

</script> 



<link rel="stylesheet" documentale_url="" href="screen_guida.css" type="text/css" media="screen" />

<div id="container">
	<div id="header">
		<h1>
			<a href="#" onClick="window.print()"><img alt="Stampa" src="printer-icon.png" align="middle" ></img></a>Manuale utente GISA
		</h1>
		
	</div>
	
	<div id="content-container">
		<div id="section-navigation">
			<ul>
				<li><a href="#mycfs">Home</a></li>
				<li><a href="#allerte">Allerte</a></li>
				<li><a href="#controlli">Gestione Controlli Ufficiali</a></li>
				<li><a href="#registro_trasgressori">Registro trasgressori</a></li>
			</ul>
		</div>
		<div id="content">
			
<div style="background:#ffffb2"><b>Introduzione</b> <br/>
<p align="justify">GISA &egrave; il sistema Regionale per la Gestione Informatizzata Servizi e Attivit&agrave; nel quale &egrave; possibile visualizzare le 
anagrafiche degli OSA ed inserire i controlli ufficiali con eventuali non conformit&agrave; gravi rilevate e relativi follow up (diffide, notizie di reato, sequestri, sanzioni).
</br>
Le anagrafiche sono organizzate in diversi cavalieri a seconda della normativa che li regola (es. stabilimenti Reg. 852 e stabilimenti Ric. CEE 853) per 
cui &egrave; possibile ricercarli accedendo allo specifico cavaliere filtrando per i filtri disponibili (si rende noto che allo scopo di rendere il pi&ugrave; efficiente possibile la ricerca
&egrave; opportuno non compilare tutti i filtri e nei campi di testo scrivere solo parole chiave). <br>
Oltre alle anagrafiche degli OSA regolarmente registrati/riconosciuti sono disponibili anche le anagrafiche di "Altri operatori" come:</p>
<ul>
	<li>Privati</li>
	<li>Abusivi</li>
	<li>Distributori automatici</li>
	<li>Attivit&agrave; mobili fuori ambito ASL</li>
	<li>Altri operatori non presenti altrove</li>
</ul>
<p  align="justify">E' disponibile inoltre la gestione delle anagrafiche di canili, colonie, proprietari di animali da compagnia 
ed operatori commerciali di animali da compagnia. Tali anagrafiche sono accessibili dal cavaliere "Macroarea I.U.V.".<br><br>
Ricercato l'OSA &egrave; poi possibile associarvi i controlli ufficiali effettuati.</p>
<br/><br/>
<b>Note:</b> <br/>
<i>Il sistema mette a disposizione tutte le anagrafiche degli OSA con la possibilit&agrave; di allegarvi qualsiasi documento mediante la sezione "Documenti" accessibile tramite l'apposito men&ugrave; verticale. 
Qualora un OSA ricercato non risulti disponibile &egrave; possibile 
fare richiesta di inserimento all'Help Desk (contatti:0817865279/7865154) ad eccezione dei casi in cui i controlli effettuati 
riguardino i cani padronali ed il cavaliere "altri operatori". In tal caso si potr&agrave; inserire l'anagrafica autonomamente.Ad esempio nel caso di un trasportatore estero, non presente nel sistema, è possibile inserire l'anagrafica e successivamente inserire un eventuale controllo ufficiale senza alcun supporto dall'Help Desk.</i>			
		</div>	

<a name="mycfs"></a>

<H1>Sezione Home</H1>
<p  align="justify">
La Home &egrave; la pagina iniziale dalla quale &egrave; possibile accedere, mediante appositi link, alle funzioni:</p>
<ul>
<li>Segnalazioni: il modulo delle segnalazioni consente di inviare in tempo reale una segnalazione, 
sia essa un malfunzionamento, un suggerimento migliorativo o 
la comunicazione di un OSA non trovato, all'HELP DESK. Quest'ultimo provveder&agrave; 
a prendere in carico la segnalazione ed a fornire supporto in breve tempo.</li>

<a target="_blank" href="http://mon.gisacampania.it/tutorial/segnalazioni.mp4"><br>
<span style ="font-weight:bold;">Clicca qui per visualizzare il video </span></a><br>
<br>

<br><li>Comunicazioni interne: il modulo delle comunicazioni interne pu&ograve; essere utilizzato per inviare messaggio ad altri utenti, appartenenti anche a profili differenti dal proprio, anagrafati nel sistema.</li>
</ul>
<p  align="justify">Oltre alle funzioni di cui sopra, nella Home &egrave; disponibile anche lo scadenzario dei controlli ufficiali.</p>

<H2>Scadenzario Controlli Ufficiali</H2>
<p style="text-align: justify">Nella sezione home tramite il
pulsante "scadenziario controlli ufficiali" e' possibile visualizzare la
lista dei controlli ufficiali aperti e chiusi di propria competenza. <br>

<H3>Controlli Aperti</H3> 
<p align="justify">Nella lista dei controlli aperti sono riportati tutti i controlli ufficiali inseriti dall'utente loggato nel sistema, che si trovano 
nello stato aperto. 
I controlli fanno riferimento agli operatori (Stabiliemtni 852, Stabilimenti 853, ecc...) la cui attivit&agrave; non e' nello stato cessato o sospeso. 
Per ogni controllo aperto viene indicato se ci sono non conformit&agrave; presenti. 
</p>

<H3>Controlli Chiusi</H3>
<p align="justify">Nella lista dei controlli chiusi sono riportati tutti i controlli ufficiali con almeno un followup, inseriti sempre dall'utente loggato nel 
sistema, che si trovano nello stato chiuso e la cui data di inizio attivit&agrave; ricade nell'ultimo mese a partire dalla data di odierna. I controlli fanno 
sempre riferimento agli operatori (Stabiliemtni 852, Stabilimenti 853, ecc...) la cui attivit&agrave; non e' nello stato cessato o sospeso. Esempio :</p>
<br><br> 
Cu : 15022
<br> Data Inizio : 25/03/2012
<br> Data corrente : 31/03/2012
<br> Stato : chiuso
<br> Altri Followup : SI
<br><br>
Siccome la data inizio controllo e' compresa tra il 21/02/2012
e il 31/03/2012 il controllo viene visualizzato nello scadenzario. 
<br> -----------------------------------------------
<br> Cu : 15022
<br> Data Inizio : 25/03/2012
<br> Data corrente : 30/04/2012
<br> Stato : chiuso
<br> Altri Followup : SI
<br>
<br><p align="justify"> siccome la data inizio controllo non e' compresa tra il 31/03/2012 e il 31/03/2012 il controllo non viene visualizzato nello scadenzario. 
<br><br></p>
<div class="fine" style="height: 50px;">&nbsp;</div>

<a name="allerte"></a>
<H1>Allerte</H1>
<p align="justify"><br> Il modulo &egrave; stato realizzato allo scopo di avere una
gestione omogenea in ambito regionale del sistema di allerta, al fine di
garantire la tutela della salute pubblica. Una allerta potrebbe essere generata mediante: </p>
<ul>
	<li>segnalazioni in arrivo: allerta originati al di fuori della
	ASL, che riguardano alimenti non prodotti ma distribuiti nel territorio
	di competenza della ASL;</li>
	<li>segnalazioni in arrivo: allerta originati al di fuori della
	ASL, che riguardano alimenti prodotti nel territorio di competenza
	della ASL;</li>
	<li>segnalazioni in partenza dalla ASL: attivazione del sistema di
	allerta per riscontri di &ldquo;frode tossica o di prodotti alimentari
	nocivi o pericolosi per la salute pubblica&rdquo;;</li>
	<li>segnalazioni in partenza dalla ASL: irregolarit&agrave;
	analitiche che, in base ai rilievi epidemiologici e/o alle informazioni
	disponibili, non costituiscono un pericolo per la salute pubblica.</li>
</ul>
<p align="justify">
Le segnalazioni possono pervenire alla Regione dal Ministero della
Salute, da altre Regioni o da altre ASL, per il riscontro di
&ldquo;frode tossica o di prodotti alimentari nocivi o pericolosi per la
salute pubblica&rdquo;. In qualche caso i Servizi delle ASL sono
direttamente avvisati dall&rsquo;Organo che attiva il sistema di
allerta. In linea di massima, verranno inoltrate dalla Direzione
Sanit&agrave; Pubblica alle ASL solamente le segnalazioni per le quali
risulti certa o probabile l&rsquo;immissione sul mercato
dell&rsquo;alimento nel rispettivo territorio di competenza o qualora
risultino comunque coinvolte imprese operanti sul territorio di
competenza delle ASL. Le segnalazioni possono riguardare un alimento
prodotto, confezionato o introdotto in Italia da una ditta avente sede
nel territorio di competenza della ASL, oppure un alimento che risulta
essere stato distribuito nel territorio della ASL. In particolare, in
GISA &egrave; presente la possibilit&agrave; di creare una allerta ed a
seconda delle ASL coinvolte vengono stabiliti i controlli da effettuare
a determinati operatori.
I Controlli stabiliti da effettuare vengono scalati all'atto di inserimento
di un nuovo controllo di tipo sistema allarme rapido, l'allerta viene selezionata
dall'operatore asl che sta inserendo il controllo e allegando la lista di distribuzione.
Ogni allerta tiene traccia dei controlli da eseguire, controlli eseguiti aperti e controlli
eseguiti chiusi.<br>
L' ASL potr&agrave; chiudere l'allerta quando sono stati inseriti e chiusi tutti i controlli richiesti.
La chiusura pu&ograve; avvenire anche in maniera forzata (cio&egrave; anche se non sono stati eseguiti i 
controlli richiesti) e dopo che l'allerta stata chiusa potr&agrave; essere possibile scaricare l'allegato F.<br>
Anche se una Allerta viene chiusa e tutti i CU risultano effettuati possibile fare una RIPIANIFICAZIONE dei CU, assegnando nuovi controlli da effettuare alle varie ASL.
<br>

Quando si effettua una operazione di chiusura, cliccando sull'apposito bottone, deve essere 
specificata una data definitiva di trasmissione dati.<br>
Si aprir&agrave; una popup nella quale sar&agrave; possibile settare tale data, visibile successivamente nel dettaglio dell'allerta.</p>
<div class="fine" style="height: 50px;">&nbsp;</div>

<a name="controlli"></a>
<H1>Gestione Controlli Ufficiali</H1>
<br>

<a target="_blank" href="http://mon.gisacampania.it/tutorial/inserisci_controllo_ufficiale.mp4"><br>
<span style ="font-weight:bold;">Clicca qui per visualizzare il video </span></a><br>
<br>


<p align="justify">L'accesso alla gestione dei controlli ufficiali in GISA non &egrave diretto, ma &egrave legato all'entit&agrave; a cui il controllo deve
essere associato. Infatti se si vuole, ad esempio, aggiungere un nuovo controllo allo stabilimento 852 "Mario Rossi s.a.s" o si vuole prendere visione
di tutti i controlli ufficiali gi&agrave; avuti dalla stessa impresa, occorre anzitutto accedere al modulo 'stabilimenti 852' e fare una ricerca dell'impresa.
Una volta acceduto alla scheda del dettaglio impresa, occorre cliccare sulla voce "Controlli Ufficiali" dal men&ugrave in visione verticale per vedere l'elenco 
controlli gi&agrave; esistente e poter aggiungere un nuovo controllo ufficiale; oppure &egrave; possibile aggiungere un nuovo controllo ufficiale cliccando direttamente sul men&ugrave; 
verticale "Aggiungi Controllo Ufficiale". Nel caso in cui, non risultano ancora inseriti in GISA controlli ufficiali per l'impresa in oggetto, 
il sistema ci informa che non &egrave stato trovato alcun controllo. 
<br><br></p>
<IMG SRC="images/elenco_cu.jpg" NAME="elenco_cu.jpg" ALIGN=LEFT WIDTH=637 HEIGHT=367 BORDER=0><BR CLEAR=LEFT><br><br>
<H3>Funzionalit&agrave; di Inserimento</H3>
<P align="justify">L'inserimento di un nuovo controllo ufficiale avviene cliccando sul link "Aggiungi Nuovo Controllo Ufficiale". Tra i dati da
inserire per ogni nuovo controllo ce ne sono alcuni (quelli contrassegnati da *) che vanno necessariamente compilati per poter salvare il controllo nel sistema.
<br><br></P>
<p style="border: solid;" align="justify"><b>N.B.: ATTENDERE IL CARICAMENTO COMPLETO DELLA PAGINA, PRIMA DI PROVARE AD EDITARE O SALVARE I CONTENUTI
DEL CONTROLLO UFFICIALE. ASSICURARSI QUINDI CHE LA SCHEDA DEL BROWSER SIA FERMA E NON IN CARICAMENTO.</b></p>
<br> I controlli ufficiali si distinguono in tre tipologie: 
<br>
<ul>
	<li><u>Audit</u></li>
	Si tratta degli audit eseguiti dalle AASSLL sulle imprese.<br><br>
	<li><u>Ispezione semplice</u></li>
	Si tratta delle ispezioni che vengono normalmente effettuate sugli OSA dalle ASL o da altre autorit&agrave; competenti (come i NAS).
	<br><br>
	<li><u>Ispezione con la tecnica di Sorveglianza</u></li>
	Si tratta dei controlli di categorizzazione del rschio degli OSA effettuati dalle ASL con l'utilizzo di checklist.
</ul>
<br><br> 
Tra i campi da compilare in fase di inserimento di un controllo ufficiale vi sono:
<br><br>
<ul>
	<li><b>ASL:</b> settato di default con l'ASL dell'OSA controllato</li>
	<li><b>Operatore Controllato:</b> settato di default con la ragione sociale dell'OSA controllato</li>
	<li><b>Tecnica del Controllo:</b> qualora l'utente che sta inserendo il controllo &egrave; di tipo ASL allora avr&agrave; a disposizione un elenco di
	 voci selezionabili (AUDIT, ispezione con la tecnica della sorveglianza, ecc. ) mentre le altre tipologie di utenti (es. i NAS)
	  troveranno il campo gi&agrave; settato di default come ispezione semplice</li>
	<li><b>Linea Attivit&agrave; Sottoposta a Controllo</b>: tale campo &egrave;
	una lista contenente tutte le linee di attivit&agrave; dell'impresa e tra
	esse occorre selezionare quella a cui il controllo fa riferimento. In
	caso di sorveglianza non esiste il concetto di linea attivit&agrave;
	sottoposta a controllo mentre in caso di audit possibile
	selezionare pilinee di attivit&agrave;.</li>
	<li><b>Data Inizio e Data Fine Controllo:</b>campi compilabili mediante apposito calendario. 
	Qualora la data fine controllo non venga popolata il sistema la setta in automatico  uguale alla data inizio controllo.</li>
	<li><b>Oggetto del Controllo:</b> tale campo compare e va compilato solo per
	controlli di tipo ispezione semplice e ispezione con la tecnica della
	sorveglianza. E' a selezione multipla poich&egrave; l'utente l'ispettore potrebbe aver controllato pi&ugrave; cose durante lo stesso controllo.
	</li>
	<li><b>Nucleo Ispettivo:</b> si tratta dell'elenco delle qualifiche e dei nominativi degli ispettori che hanno partecipato al controllo.<br>
	NOTA: qualora all'ispezione abbia partecipato il personale ASL il controllo nel sistema GISA 
	viene inserito dall'utente ASL specificando nel nucleo ispettivo gli ispettori presenti al controllo.</li>
	<li><b>Note:</b> &egrave; un campo di testo libero in cui l'utente pu&ograve; annotare qualsiasi cosa emersa nel corso del controllo ufficiale.</li>
</ul>
<br><br>
<IMG SRC="images/add_cu.jpg" NAME="add_cu.jpg" ALIGN=LEFT WIDTH=637 HEIGHT=567 BORDER=0>
<BR CLEAR=LEFT><br><br> 
<p align="justify">Dal momento che ogni controllo ufficiale &egrave; legato alla linea di attivit&agrave; a cui si riferisce, al momento del click sul tasto
"Inserisci" viene mostrato il messaggio che ricorda all'utente che vanno aggiunti pi&ugrave; controlli ufficiali qualora siano state controllate
pi&ugrave; linee di attivit&agrave; per una stessa impresa. Questo vale per tutti i tipi di controllo ufficiale tranne le ispezioni in sorveglianza. 
<br><br></p>

<P style="text-align: justify">
<H3>Funzionalit&agrave; di Dettaglio</H3>
<P ALIGN=justify>Una volta inserito il controllo ufficiale, viene visualizzata la scheda di dettaglio dalla quale possibile compiere le seguenti azioni:</P>
<ul>
	<li>Aprire il controllo in modifica (cliccando sul bottone "Modifica") qualora ci siano ulteriori campi da compilare o modificare.</li><br>
	<li>Aggiungere al controllo eventuali non conformit&agrave; (camponi o tamponi) riscontrate nel corso del controllo cliccando sull'apposito link. 
	Dal momento che &egrave; possibile cliccare una sola volta sul link "Inserisci non Conformit&agrave; Rilevate", tutte le non conformit&agrave; riscontrate nel corso
		del controllo vanno descritte in un unico "contenitore" delle non conformit&agrave;</li><br>
	<li>Chiudere il controllo (cliccando sul bottone "chiudi") "se e solo se" completo di tutti i dati necessari; questo perchuna volta chiuso 
		non pipossibile riaprirlo (a meno che non si contatti l'Help Desk). Infatti il sistema chiede la conferma visualizzando un apposito messaggio. 
		Inoltre, non &egrave; possibile chiudere un controllo se non vengono chiuse tutte le sottoattivit&agrave; in esso inserite. In caso contrario il sistema blocca 
		l'operazione e fornisce un messaggio all'utente.</li>
</ul>

<H2>Definizione dei vincoli di inserimento e modifica CU</H2>
<p align="justify">
In GISA &egrave; nata la necessit&agrave; di definire i vincoli per l'inserimento dei controlli ufficiali, introducendo il concetto di blocco laddove
sono in corso delle rendicontazioni sui controlli per evitare di alterare i dati ed introdurre incongruenze. La regola intorno alla quale ruotano tutti i vincoli per
l'inserimento e la modifica di un controllo la seguente:
<u>"Per l'anno n si possono inserire e modificare i CU dell'anno n fino al 10 Gennaio dell'anno n+1"</u></p>
<ul type="square">
	<li><b>Vincolo 1: Inserimento di un controllo successivo alla data corrente</b></li><br>
	Tale vincolo indica che non possibile inserire controlli ufficiali con data successiva alla data corrente.<br><br>
	<li><b>Vincolo 2: Inserimento/Modifica di due controlli con la stessa "Data inizio controllo"</b> </li><br>
	Quando si vuole inserire un controllo con la stessa Data inizio controllo di un altro, il sistema mostrer&agrave; un messaggio di questo tipo 
	ATTENZIONE! Risulta che in questo stabilimento stato gi&agrave; effettuato un controllo in questa data. Sei sicuro di continuare; 
	se l'utente clicca su OK, allora sar&agrave; possibile concludere l'inserimento, viceversa, ovvero se clicca su ANNULLA, no. 
	Lo stesso ragionamento andr&agrave; esteso per l'operazione di modifica.<br><br>
	<li><b>Vincolo 3: Inserimento/Modifica di un controllo in presenza di blocchi </b></li><br>
	Per l'anno n sono previsti 4 blocchi trimestrali durante i quali non potranno essere fatti inserimenti e modifiche dei controlli. 
	La situazione sar&agrave; la seguente:<br><br>
	<ul type="circle">
		<dl>
		<dd><li>[Primo blocco] dal 01/01 al 31/03;</dd></li>
		<dd><li>[Secondo blocco] dal 01/04 al 30/06;</dd></li>
		<dd><li>[Terzo blocco] dal 01/07 al 30/09;</dd></li>
		<dd><li>[Quarto blocco] dal 01/10 al 31/12;</dd></li>
	</dl>
	</ul>
	<br>
	<p align="justify">L'attivazione/disattivazione dei blocchi sar&agrave; una azione regolata da opportuni permessi e sar&agrave; disponibile solo al ruolo "ORSA DIREZIONE".
	<br>In particolare &egrave; possibile, cliccando sul cavaliere "Home - "Blocco Controlli" - "Aggiungi blocco", limitare la modifica/inserimento di Controlli ufficiali.<br>
	Nello specifico selezionando:</p>
	<ul>
		<li>Consentita modifica/inserimento di controlli con data compresa in dal - al<br>
			Si possono inserire o modificare controlli ufficiali con data compresa tra le date selezionate.<br>
		<li>Non consentita modifica/inserimento di controlli con data compresa in dal - al<br>
			Non &egrave; possibile inserire o modificare controlli ufficiali con data compresa tra le date selezionate.<br>
			Per entrambe le restrizioni inserite &egrave; possibile motivare il blocco inserendo una nota nel campo Note.<br><br>
	</ul>
	<u>Nello stato "blocco":</u><br><br>
	<ol>
		<li>non sar&agrave; possibile inserire e modificare i controlli che hanno una data che ricade nel blocco di riferimento. 
		(ovvero CU le cui date di inizio controllo ricadono nell'intervallo "data blocco-data fine blocco").
		Il sistema in questo caso mostrer&agrave; il seguente messaggio: ATTENZIONE! 
		Il controllo in questo momento non puessere inserito/modificato in GISA perchattualmente in vigore 
		un blocco temporaneo per i controlli dal [DATA INIZIO BLOCCO] AL [DATA FINE BLOCCO] ai fini della rendicontazione trimestrale."</li>
		<br>
		<li>non dovr&agrave; comparire nessun bottone che alteri lo stato di tutti quei controlli la cui "data inizio controllo" ricade nel 
		range del blocco di riferimento. 
		Nel dettaglio dei CU non modificabili, comparir&agrave; il messaggio riportato al punto 1.</li>
		<br>
		<li>non dovr&agrave; comparire nessun bottone che alteri lo stato delle sottoattivit&agrave; legate al controllo la cui data inizio 
		controllo ricade nel range del blocco di riferimento.</li>
		<br>
		<li>Nel dettaglio dei controlli ufficiali, la cui data inizio controllo rientra nell'anno n-1, comparir&agrave; il seguente messaggio: 
		"ATTENZIONE! La modifica ad un controllo dell'anno precedente puavvenire solo tramite richiesta all'Help Desk".</li>
	</ol><br>
	<u>Nello stato di "sblocco"</u> 
	non ci saranno limiti per l'anno n sulle operazioni di inserimento e modifica CU, ovvero sar&agrave; possibile inserire e modificare i controlli ufficiali 
	per tutto l'anno n.
	<br><br>
	<li><b>Vincolo 4: Inserimento/Modifica di un controllo in Sorveglianza</b></li><br>
	Per i controlli di tipo "Sorveglianza" si aggiungono anche i seguenti vincoli:<br><br>
	<ul type="disc">
		<li>Non possibile inserire una sorveglianza se ne esiste un'altra aperta per lo stesso stabilimento. 
		In questo caso, il sistema restituir&agrave; un messaggio del tipo ATTENZIONE : non possibile inserire un 
		nuovo controllo in Sorveglianza. Esistono controlli ufficiali in sorveglianza ancora aperti. 
		Chiudere prima i seguenti controlli: [id_controllo];</li>
		<li>L'inserimento dei controlli in sorveglianza basato sulla categoria di rischio (CDR). 
		In particolare, i controlli andranno effettuati secondo questa regola con una tolleranza di giorni pari circa al 20% del limite superiore:</li>
		<ul type="circle">
			<li>se la CDR = 5  1 anno</li>
			<li>se la CDR = 4  2 anni</li>
			<li>se la CDR = 3  3 anni</li>
			<li>se la CDR = 2  4 anni</li>
			<li>se la CDR = 1  5 anni</li>
		</ul>
	</ul><br>
	<li><b>Vincolo 5: Campi modificabili in un CU</b></li><br>
	Tutte le operazioni di modifica CU potranno essere effettuati su tutti i campi ad eccezione della Tecnica di controllo. 
	Inoltre, non sar&agrave; possibile modificare i motivi di ispezione a cui risultano associati i campioni. 
	In questo caso, il sistema mostra il seguente messaggio: <font color="red">"Attenzione il tipo di controllo non modificabile! 
	Esistono campioni nel controllo ufficiale"</font><br><br>
	<li><b>Vincolo 6: Controlli Pregressi  Anni precedenti</b></li><br>
	Le straordinarie operazioni di modifica CU per gli anni precedenti saranno possibili solo ed esclusivamente all'HD I livello,
	su esplicita richiesta, a partire dal 16 giorno del nuovo anno e naturalmente in assenza di blocchi. 
	(Es. Si conclude l'anno 2014. L'utente ha tempo fino al 15 Gennaio 2015 per recuperare dati su CU dell'anno precedente. 
	Se si va oltre questo limite, bisogner&agrave; fare la richiesta all'HD).
</ul><br>


<H2>Gestione evento Blocco/Sblocco Modifica CU</H2>
<br>
Nel sistema possibile modificare alcuni dati del CU per lanno corrente purchnon esista un evento di blocco che impedisce tale azione.<br> 
In particolare, potranno essere modificati i tipi e le motivazione dei CU solo nelle seguenti condizioni:
<ul>
	<li>Si possono modificare i controlli se non vi alcun evento di blocco modifica.</li>
    <li>Si possono modificare i controlli che non hanno campioni associati: in questo caso il sistema avvisa l'utente con un messaggio che ricorda la 
		dipendenza del motivo di ispezione del CU con quella del campione gi&agrave; presente e gli impedisce di proseguire.</li>
    <li>Si possono modificare i controlli che hanno data inizio controllo maggiore della data di evento blocco.</li>
</ul>     
La gestione degli eventi di blocco e sblocco della modifica CU regolata da un opportuno permesso, attribuito per ora al ruolo di "Responsabile Regione".<br><br><br>		

<H2>Gestione non conformit&agrave;</H2>
<P ALIGN=justify><br> Nel corso di un controllo ufficiale possono essere riscontrate delle non conformit&agrave; formali, significative o gravi da cui possono 
scaturire dei follow up ( sanzioni, sequestri, notizie di reato o altri follow up). In GISA le non conformit&agrave; riscontrate nel corso di un controllo sono 
accessibili dal dettaglio del controllo cliccando sull'apposito link. <br><br>
<IMG SRC="images/nc_rilevate.bmp" NAME="nc_rilevate.bmp" ALIGN=LEFT WIDTH=637 HEIGHT=100 BORDER=0><BR CLEAR=LEFT><br>

<H2>Funzionalit&agrave; inserimento</H2>
<P ALIGN=justify><br> In GISA l'inserimento delle non conformit&agrave; avviene cliccando sul link "Inserisci Non Conformit&agrave; Rilevate" dal dettaglio del 
controllo ufficiale a cui vanno associate.<br><br></P>
<IMG SRC="images/nc_rilevate2.bmp" NAME="nc_rilevate2.bmp" ALIGN=LEFT WIDTH=637 HEIGHT=267 BORDER=0><BR CLEAR=LEFT><br><br> 
<p align="justify">E' previsto l'inserimento di almeno un tipo di non conformit&agrave;: formali, significative o gravi e nel caso in cui tale requisito non venga rispettato il 
sistema risponder&agrave; con il seguente messaggio bloccante.<br><br></p>

<a target="_blank" href="http://mon.gisacampania.it/tutorial/inserisci_non_conformita.mp4"><br>
<span style ="font-weight:bold;">Clicca qui per visualizzare il video </span></a><br>
<br>

<H2>Gestione dei campioni</H2>
<P ALIGN=justify><br>Nell'ambito di un controllo ufficiale,è possibile aggiungere/modificare/cancellare un campione specificando la motivazione abbinata al CU, la matrice, a selezione singola, e il tipo di analisi, a scelta multipla, e il laboratorio di destinazione.
È possibile aggiungere informazioni aggiuntive al campione, in modo facoltativo.

<a target="_blank" href="http://mon.gisacampania.it/tutorial/aggiungi_campione.mp4"><br>
<span style ="font-weight:bold;">Clicca qui per visualizzare il video </span></a><br>
<br>


<H2>Gestione esito campioni</H2>
<P ALIGN=justify>Per un campione associato ad un controllo ufficiale è possibile inserire l'esito direttamente dal dettaglio del campione.In particolare sono presenti due tabelle:
"Informazioni campione da laboratorio" nella quale è possibile inserire:
<ul>
	<li>Codice accettazione</li>
	<li>Data accettazione</li>
	<li>Risultato laboratorio</li>
	<li>Data risultato</li>
</ul>
<br>
<IMG SRC="images/informazioni_campione.PNG"	ALIGN=BOTTOM  BORDER=0><BR CLEAR=LEFT><br>
I dati inseriti in questa tabella sono modificabili successivamente anche se si chiude il campione o il controllo ufficiale cliaccando sul pulsante "RIAPRI INFORMAZIONI LABORATORIO".
<BR><BR>
L'altra tabella contiene l'esito del campione.<br>
Le informazioni inserite in questa tabella una volta cliccato sul tasto "salva" non sono più modificabili.

<IMG SRC="images/esito_campione.PNG" ALIGN=BOTTOM  BORDER=0><BR CLEAR=LEFT><br>

<a name="serverdocumentale"></a>


<a target="_blank" href="http://mon.gisacampania.it/tutorial/inserisci_esito.mp4"><br>
<span style ="font-weight:bold;">Clicca qui per visualizzare il video </span></a><br>
<br>





<H2>Inserimento di un tipo di non conformit&agrave;</H2>
<P ALIGN=justify><br> Per inserire una tipologia di non conformit&agrave; (formali, significative o gravi) basta selezionare il tab corrispondente. 
Per ogni non conformit&agrave; riscontrata occorre specificare:<br></P>
<ul>
	<li>una descrizione</li>
	<li>il settore a cui la non conformit&agrave; si riferisce (es.	alimenti per il consumo umano). La lista delle voci selezionabili per tale campo 
		corrisponde alla lista delle voci selezionate come "oggetto del controllo" nel controllo ufficiale corrispondente.</li>
</ul>
NOTA: le ASL possono inserire non conformit&agrave; per tutte e tre le tipologie mentre le altre autorità competenti possono solo inserire non conformità di tipo GRAVE.
<br>
<IMG SRC="images/nc_rilevate3.bmp" NAME="nc_rilevate3.bmp" ALIGN=LEFT WIDTH=637 HEIGHT=467 BORDER=0><BR CLEAR=LEFT><br><br> 
<p align="justify">
Nel caso in cui siano state riscontrate pidi una non conformit&agrave;, l'utente potr&agrave; aggiungerne altre cliccando semplicemente sul pulsante 
"Inserisci NC". L'aggiunta di una nuova non conformit&agrave; sar&agrave; possibile solo se i campi della precedente sono stati inseriti, altrimenti 
il sistema avverte che non possibile aggiungerne una nuova finchnon sono stati completati i campi precedenti. Inoltre ogni volta che viene aggiunta 
una non conformit&agrave; le precedenti non sono pimodificabili. Il numero massimo di non conformit&agrave; che possono essere inserite sono 25 per le 
formali e significative, 15 per le gravi. Qualora l'utente si accorgesse di dover eliminare una non conformit&agrave; lo pufare cliccando sul bottone 
"Elimina NC". Inoltre mediante il tasto "Reset NC" possibile azzerare il lavoro svolto eliminando le non conformit&agrave; e i follow up inseriti, 
riportando lo stato del tab a quello iniziale. Il campo "Valutazione del Rischio" (presente in alto) globale per ogni tipologia di non conformit&agrave; 
(non esiste per le gravi). Il punteggio viene incrementato automaticamente dal sistema ogni volta che una nuova non conformit&agrave; viene aggiunta.
Per le non conformit&agrave; formali il punteggio incrementato di 1 punto, per le significative di 7 punti per le gravi di 25 punti(controlli ufficiali 
inseriti con data antecedente al 31/12/2014),o 50 punti(controlli ufficiali dell'anno corrente). Di volta in volta varia anche il punteggio totale della 
non conformit&agrave; che viene aggiornato comunque in automatico. <br><br> 
Per completare l'inserimento e abilitare il tasto di inserimento globale della non conformit&agrave;, oppure per aggiungere un'altra tipologia di 
non conformit&agrave; (formali, significative, gravi), dopo aver aggiunto le non conformit&agrave; di una certa tipologia obbligatorio l'inserimento di 
almeno un follow up corrispondente. Nel caso delle non conformit&agrave; gravi obbligatorio l'inserimento di almeno una delle seguenti attivit&agrave;: 
sanzione, nota di reato o sequestro, mentre per le formali e significative altri follow up. <br><br></p>

<IMG SRC="images/follow_up.bmp" NAME="follow_up.bmp" ALIGN=LEFT WIDTH=637 HEIGHT=467 BORDER=0><BR CLEAR=LEFT><br> 
<p align="justify">
E' possibile l'aggiunta dei follow up mediante gli appositi bottoni. Cliccando si apre la maschera di inserimento e finch&egrave; l'utente non esce dall'aggiunta 
del follow up (sanzione, sequestro, notizia di reato) non pueffettuare altre operazioni nel sistema. Dopo aver inserito il follow up (sanzione, sequestro, 
notizia di reato), la finestra verr&agrave; chiusa e l'utente vede apparire il follow up inserito nella tabella in basso e pucontinuare o con l'inserimento
degli altri tipi di non conformit&agrave; o con l'inserimento globale. Non sar&agrave; possibile accedere al dettaglio dei follow up aggiunti se non si inserisce 
prima il contenitore globale delle non conformit&agrave; (cosa possibile solo se si clicca sul bottone "Inserisci>"). <br><br>

<b>IMPORTANTE : Si ricorda che per poter spostarsi da una tipologia all'altra di non conformit&agrave; l'utente dovr&agrave; aver inserito almeno una non 
conformit&agrave; e almeno una attivit&agrave; per il tab in cui si trova, oppure non aver inserito niente. Solo quando i tre tab sono stati completati 
sar&agrave; possibile l'intera non conformit&agrave;.</b><br><br> 
Alle non conformit&agrave; aggiunte il sistema attribuisce in automatico un identificativo che le rende univoche in GISA. <br><br></p>

<H2>Modifica di un tipo di non conformit&agrave;</H2><br> 
<p align="justify">Cliccando sul tasto modifica appare la pagina di modifica
della non conformit&agrave; con i dati inseriti in fase di inserimento. Per
ogni tipologia di non conformit&agrave; inserita (formale, significativa e/o
grave) consentita la modifica dei campi descrizione e oggetto delle
non conformit&agrave; riscontrate, con la possibilit&agrave; di aggiungerne altre. 
<br>
<br> Le non conformit&agrave; di una certa tipologia registrate in fase
di inserimento non potranno essere eliminate ma solamente modificate.
Stesso discorso per i follow up (sanzioni, sequestri, ecc. ): i follow
up registrati in fase di inserimento non possono essere cancellati 
possibile solo aggiungerne altri. Il pulsante "Inserisci NC" permette
l'inserimento di una nuova non conformit&agrave; mentre il pulsante "Elimina
NC" permette di eliminare le non conformit&agrave; inserite in fase di
modifica e non quelle registrate in fase di inserimento. Il pulsante
"Reset NC" azzera il lavoro svolto in fase di modifica, lasciando
inalterati i dati registrati in fase di inserimento. </p>
<br>
<br>
<H2>Gestione Diffida/Sanzioni Amministrative</H2>	
<p align="justify">Nell'ambito della DEPENALIZZAZIONE è stato introdotto il concetto di DIFFIDA, ovvero la possibilità di 
fornire una sorta di bonus (valido per 5 anni) ad un'OSA che infrange una determinata norma per la quale dovrebbe essere soggetto a SANZIONE AMMINISTRATIVA.<br>
L'OSA potr&agrave; quindi essere diffidato per una stessa norma una sola volta ogni cinque anni e qualora nei cinque anni successivi alla diffida violi di nuovo la 
stessa norma scatterà direttamente la sanzione sensa alcuna possibilità di riduzione dell'importo dovuto.
</p>
<H3>Inserimento Diffida/Sanzione</H3>
<p align="justify">Per inserire una diffida ad un controllo ufficiale basta cliccare su Non conformit&agrave;  grave e successivamnte su Inserisci diffida.
A questo punto si aprir&agrave;  una finestra contenente le informazioni da inserire.
<br></p>

<IMG SRC="images/inserimento_diffida.PNG" NAME="inserimento_diffida.PNG" WIDTH=600 BORDER=0><br>

<br><p align="justify">
Successivamente, il dettaglio delle diffide inserite &egrave; possibile visualizzarlo nella lista delle non conformit&agrave;. Come mostrato in figura:

<br>

<IMG SRC="images/dettaglio_diffida_conformita.PNG" NAME="dettaglio_diffida_conformita.PNG" 
	WIDTH=600 BORDER=0 /> <br>

Oppure nel dettaglio dell'OSA:</p>
<IMG SRC="images/dettaglio_diffida_operatore.PNG" NAME="dettaglio_diffida_operatore.PNG"
	WIDTH="600" BORDER=0 />

<br>
<p align="justify">
Per inserire una Sanzione, invece, basta cliccare su Inserisci Sanzione e inserire le informazioni.</p>
<br>
<IMG SRC="images/inserimento_sanzione.PNG" NAME="inserimento_sanzione.PNG"
	WIDTH="600" BORDER=0 />
<br>
<p align="justify">
Se in fase di inserimento della sanzione vengono cliccate norme che risultano gi&agrave; precedentemente inserite in diffida un messaggio avvisa l'utente senza impedirne l'inserimento.<br>
Sar&agrave;  possibile visualizzare il dettaglio delle sanzioni nella lista delle non conformit&agrave;. Tutte le sanzioni inserite nei controlli ufficiali CHIUSI verranno riportate nel registro trasgressori.
</p>
<br>
<br>
<H2>Nuova Gestione del nucleo ispettivo</H2>	
<p align="justify">Per nucleo ispettivo di intende il gruppo di persone che partecipano a un controllo ufficiale.<br>
In GISA e' possibile, nell'ambito dell' inserimento di un nuovo controllo, associare fino a massimo  dieci partecipanti.
La compilazione avviene nel seguente modo :</p>
<ul>
<li>Nella prima lista viene selezionata la qualifica/ruolo della persona che si intende inserire (come mostrato nella figura sottostante)</li>
<br><IMG SRC="images/nucleo-1.png" NAME="nucleo-1.png" WIDTH=600 BORDER=0 /> <br>
<li>Il sistema controllerà se la qualifica/ruolo selezionata rientra nel DPAT come ruolo possibile all'interno dell'organizzazione ASL. In caso affermativo, comparirà un link "Seleziona Componente"</li>
<br><IMG SRC="images/nucleo-2.png" NAME="nucleo-1.png" WIDTH=600 BORDER=0 /> <br>
<li><p align="justify">Cliccando su "Seleziona Componente", il sistema mostrerà la lista di tutto il personale ASL presente nello "strumento di calcolo" 
per l'anno in corso con la qualifica selezionata.<br>
Se nella lista manca un utente significa che non è associato a nessuna struttura nello strumento di calcolo.
Le Informazioni riportate nella lista sono:</p>
				<ol>
				<li>Struttura di appartenenza dell'utente (Struttura complessa + semplice)</li>
				<li>Nominativo (cognome-nome)</li>
				</ol>
<p align="justify"><br>
E' possibile ordinare/filtrare l'elenco nominativi (per poter ordinare basta cliccare sull'intestazione della colonna per cui si vuole effettuare 
l'ordinamento mentre per rendere più rapida la ricerca di un nominativo l'utente potrà utilizzare gli appositi campi filtro disponibili in testa alla colonna).
Dopo aver spuntato i partecipanti al controllo, cliccando sul pulsante "Fatto" si completa l'operazione di aggiunta dei nominativi al nucleo ispettivo
Nel caso vengano spuntati utenti gia selezionati in precedenza, 
il sistema bloccherà l'inserimento mostrando il seguente messaggio :<br>
<font color="red">"IL COMPONENTE ROSSI MARIO E' STATO GIA' SELEZIONATO"</font><br>
<br><IMG SRC="images/nucleo-5.png" NAME="nucleo-5.png" WIDTH=600 BORDER=0 /> <br>
<br><IMG SRC="images/nucleo-6.png" NAME="nucleo-6.png" WIDTH=600 BORDER=0 /> <br><br>
In caso di errore nella selezione, per poter cambiare uno dei nominativi impostati, occorre cambiare la rispettiva 
qualifica, in questo modo ricomparirà il link "Seleziona Componente" , 
tramite il quale si potrà  rimpostare il componente che si intende cambiare.

</p>				
	</li>
<li>
<p align="justify">
Nel caso in cui la qualifica/ruolo selezionata rientra nel DPAT come ruolo possibile all'interno dell'organizzazione ASL  
il sistema mostrerà un campo di testo editabile in cui inserire il nome del componente che ha partecipato al controllo.
</p>
</li>	
<li>
<p align="justify">
Nel caso in cui la qualifica/ruolo selezionata non rientra nel DPAT come ruolo possibile all'interno dell'organizzazione ASL 
 e tuttavia esistono utenti Gisa accreditati (es.: Criuv) il sistema mostyrerà la lista dei componenti priva di informazioni di struttura.

</p>
</li>				
</ul>
<h3>NOTE</h3>
<ol>
<li>esistono particolari qualifiche per le quali è previsto l'anonimato ovvero la possibilità di specificare 
come partecipante al nucleo un codice identificativo diverso dal nome e cognome.
</li>
<li>come convenuto col dr Giannoni, anche  in caso di anonimato o utenti non anagrafati i campi "Inserito da" e 
"Modificato da" conterranno comunque il nominativo di chi ha iserito/modificato il controllo ufficiale.
</li>
</ol>


<div class="fine" style="height: 50px;">&nbsp;</div>


<a name="registro_trasgressori"></a>
<H2>Registro trasgressori</H2>
<p align="justify">E' possibile accedere tramite questo cavaliere al registro dei trasgressori dell'anno in corso e degli anni precedenti.Vengono salvati in questo registro solo 
controlli ufficiali, per l'anno corrente, contenenti NC gravi per le quali &egrave; stata registrata:</p>
<ul> 
	<li>SANZIONE AMMINISTRATIVA per specifiche norme violate.</li>
	<li>SEQUESTRO di tipo AMMINISTRATIVO.<br>
		Alcuni campi vengono popolati automaticamente con i dati presenti nei controlli ufficiali altri invece dovranno essere popolati manualmente.<br>
		Di seguito elencati i campi precompilati automaticamente dal controllo:</li>
	<li>N. PROG. : progressivo</li>
	<li>ID CONTROLLO : d controllo ufficiale in GISA</li>
	<li>ENTE ACCERTATORE 1/2/3 : tipologia componente nucleo ispettivo 1/2/3(es. NAS, Servizio Veterinario, NA1C Ecc.)</li>
	<li>P.V. N. : contiene il valore del campo "numero processo verbale" presente nelle sanzioni in GISA</li>
	<li>EFFETTUATO SEQUESTRO N. : contiene il valore del campo "numero verbale" presente nei sequestri in GISA </li>
	<li>DATA ACCERTAMENTO : contiene il valore del campo "data inizio controllo" presente nei controlli ufficiali in GISA </li>
	<li>TRASGRESSORE : contiene il contenuto del campo " trasgressore" presente nelle sanzioni in GISA</li>
	<li>OBBLIGATO IN SOLIDO : contiene il contenuto del campo "obbligato in solido" presente nelle sanzioni in GISA</li>
	<li>IMPORTO SANZIONE RIDOTTA : contiene il contenuto del campo importo sanzione ridotta" presente nelle sanzioni in GISA</li><br>
</ul> 
<p align="justify">Una volta compilati i campi &egrave; possibile stampare il pdf relativo cliccando su "PDF registro per l'anno corrente".<br>
Per visualizzare i registri trasgressori degli anni precedenti cliccare su "archivio" e si aprir&agrave; una scheda come quella riportata in figura nella 
quale &egrave; possibile scegliere l'anno di interesse,successivamente cliccare su "invia".<br></p>
<IMG SRC="images/Registro_trasgr.PNG" NAME="Registro_trasgr.png"width="595" height="222"><br>
Un esempio di registro trasgressori &egrave; possibile visualizzarlo cliccando sull'immagine qui sotto<br>
<a href="#" onclick="openPopup('images/Registro trasgressori/Registro_trasgr_2.png') ">
<IMG SRC="images/Registro_trasgr_2.PNG" NAME="Registro_trasgr_2.bmp" width="300"/></a><br>
NOTA : <b> Per i registri degli anni precedenti &egrave; possibile solo la visualizzazione ma non la modifica/inserimento dati</b><br>
<div class="fine" style="height: 50px;">&nbsp;</div>

</div><!-- chiusura div principale corpo guida -->

		<div id="aside">
			
<a href="#" onClick="openPopup('images/infografica1.png')"><img src="images/infografica1.png" width="200"/></a>
<br/>
<a href="#" onClick="openPopup('images/infografica2.png')"><img src="images/infografica2.png" width="200"/></a>	
		</div>
		<div id="footer">
			
		</div>
	</div>
</div>