<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<html><head>
<meta http-equiv="content-type" content="text/html; charset=windows-1252"><script> 
 
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

<link rel="stylesheet" documentale_url="" href="screen_guida.css" type="text/css" media="screen">

</head><body><div id="container">
	<div id="header">
		<h1>
			Manuale utente GISA 
		</h1>
		<a onclick="window.print();"><img alt="Stampa" src="images/printer-icon.png"></a>	
	</div>
	
	<div id="content-container">
		<div id="section-navigation">
			<ul>
				<li><a href="../speedtest/speedtest.html">Speed Test</a></li>
				<li><a href="#mycfs">Home</a></li>
				<li><a href="#aiequidi">A.I. Equidi</a></li>
				<li><a href="#acquedirete">Acque di Rete</a></li>
				<li><a href="#altrioperatori">Altri Operatori</a></li>
				<li><a href="#oia">Autorita Competenti</a></li>
				<li><a href="#aziendeagricole">Aziende Agricole</a></li>
				<li><a href="#allevamenti">Aziende Zootecniche</a></li>
				<li><a href="#requestor">D.I.A.</a></li>
				<li><a href="#laboratorihaccp">Laboratori HACCP</a></li>
				<li><a href="#macroareaIUV">Macroarea I.U.V.</a></li>
				<li><a href="#molluschibivalvi">Molluschi Bivalvi</a></li>
				<li><a href="#farmacosorveglianza_home">Operatori 193</a></li>
				<li><a href="#osm_home">OSM</a></li>
				<li><a href="#punti_di_sbarco">Punti Di Sbarco</a></li>
				<li><a href="#soa">Soa</a></li>
				<li><a href="#accounts">Stabilimenti Registrati 852</a></li>
				<li><a href="#stabilimenti">Stabilimenti 853</a></li>
				<li><a href="#riproduzioneanimale">Strutture di riproduzione animale</a></li><br>
				<li><a href="#trasportoanimali">Trasporto Animali</a></li>
				<li><a href="#zonecontrollo">Zone di Controllo</a></li>
				<li><a href="#allerte_new">Allerte</a></li>
				<li><a href="#buffer">Buffer</a></li>
				<li><a href="#gestione%20checklist">CheckList</a></li>
				<li><a href="#dpat">DPAT</a></li>
				<li><a href="#ConfigurazioneDPAT">DPAT - Configurazione</a></li>
				<li><a href="#controlli">Gestione Controlli Ufficiali</a></li>
				<li><a href="#Gestioneesitocampioni">Gestione Esito campioni</a></li>
				<li><a href="#macellazioni">Macellazioni</a></li>
				<li><a href="#mu">Macellazioni Uniche</a></li>
				<li><a href="#stampa_verbali_prelievo">Prenotazione Campioni SIGLA</a></li><br>
				<li><a href="#registro_trasgressori">Registro trasgressori</a></li>
				<li><a href="#documents">Reperibilita Asl</a></li>
				<li><a href="#unita_crisi">Unità di crisi</a></li>
				<li><a href="#pratiche_suap">Pratiche SUAP</a></li>
				<li><a href="#Apicoltura">Apicoltura</a></li>
				<li><a href="#video">Video Tutorial</a></li>
				
			</ul>
		</div>
		<div id="content">
			

<a name="mycfs"></a>

<h1>Sezione Home</h1>
<br>
<h2>Scadenzario Controlli Ufficiali</h2>
<p style="text-align: justify">Nella sezione home tramite il
pulsante "scadenziario controlli ufficiali" e' possibile visualizzare la 
lista dei controlli ufficiali aperti, chiusi e quelli in sorveglianza
che stanno per scadere o che sono già scaduti.  <br>
Attenzione! Nel caso in cui il pulsante lampeggia sarà possibile
visualizzare anche la lista dei controlli che stanno per essere chiusi
da ufficio. La chiusura da ufficio avviene nel caso in cui l'utente non
avesse provveduto alla chiusura del controllo entro un mese dalla data
di inserimento. Riepilogando, nello scadenzario presente l'elenco di
tutti i controlli ufficiali inseriti dall'operatore loggato nel
sistema, che si trovano nei seguenti stati: 
</p><ul>
	<li>Controlli aperti</li>
	<li>Controlli chiusi</li>
	<li>Controlli (in sorveglianza) in scadenza</li>
</ul>
Inoltre, solo coloro che hanno il ruolo "VETERINARI RESPONSABILI 
STRUTTURA" potranno visualizzare, cliccando sull'apposito pulsante 
"Controlli Unità Operative", i 100 controlli ufficiali pivecchi aperti 
ed in sorveglianza, effettuati dall'asl a cui l'utente 
appartiene: nel dettaglio sarà riportata anche la colonna relativa 
all'Unità Operativa. 
<br><br>


<h2>Controlli Aperti</h2><br> 
Nella lista dei controlli aperti sono riportati tutti i controlli 
ufficiali inseriti dall'utente loggato nel sistema, che si trovano nello
 stato aperto. 
I controlli fanno riferimento agli operatori(Imprese, Stabilimenti, 
ecc...) la cui attività non e' nello stato cessato o sospeso. 
Per ogni controllo aperto viene indicato se ci sono attività presenti. 
<br><br>

<h2>Controlli Chiusi</h2><br>
Nella lista dei controlli chiusi sono riportati tutti i controlli 
ufficiali con almeno un followup (Altri Followup), inseriti sempre 
dall'utente loggato nel 
sistema, che si trovano nello stato chiuso e la cui data di inizio 
attività ricade nell'ultimo mese a partire dalla data di odierna. I 
controlli fanno 
sempre riferimento agli operatori (Imprese ,Stabilimenti,ecc...) la cui 
attività non e' nello stato cessato o sospeso. Esempio :
<br><br> 
Cu : 15022
<br> Data Inizio : 25/03/2012
<br> Data corrente : 31/03/2012
<br> Stato : chiuso
<br> Altri Followup : SI
<br><br>
siccome la data inizio controllo e' compresa tra il 21/02/2012
e il 31/03/2012 il controllo viene visualizzato nello scadenzario. 
<br> -----------------------------------------------
<br> Cu : 15022
<br> Data Inizio : 25/03/2012
<br> Data corrente : 30/04/2012
<br> Stato : chiuso
<br> Altri Followup : SI
<br>
<br> siccome la data inizio controllo non e' compresa tra il 31/03/2012 e
 il 31/03/2012 il controllo non viene visualizzato nello scadenzario. 
<br><br>

<h2>Controlli (in sorveglianza) in scadenza</h2><br>
Nella lista dei controlli in scadenza sono riportati tutti i controlli 
ufficiali di tipo Sorveglianza per i quali e' stata aggiornata la 
categoria di rischio, 
e la cui data prossimo controllo (assegnata dopo aver aggiornato la 
categoria di rischio) e' precedente alla data attuale (Controlli 
Scaduti), o e' compresa tra 
la data attuale e tre mesi successivi rispetto alla data attuale. 
Esempio :
<br>
<br> Cu : 15025
<br> Tipo Cu : Sorveglianza 
<br> Data Inizio : 25/03/2008
<br> Prossimo Cu : 27/03/2012
<br> Data corrente : 31/03/2012
<br>
<br> Nel caso in cui la data del prossimo controllo e' precedente alla 
data attuale il controllo verrà visualizzato nello stato: Scaduto. 
<br> ----------------------------------------------- 
<br> Cu : 15025
<br> Tipo Cu : Sorveglianza 
<br> Data Inizio : 25/03/2008
<br> Prossimo Cu : 27/04/2012
<br> Data corrente : 31/03/2012
<br>
<br> Nel caso in cui, la data del controllo successivo ricade in un 
periodo temporale maggiore dei tre mesi dalla data attuale (tra il 
31/03/2012 e il 31/06/2012) 
il controllo verrà visualizzato nello stato: Controllo prossimo alla 
scadenza. 
<br> ----------------------------------------------- 
<br> Cu : 15025
<br> Tipo Cu : Sorveglianza 
<br> Data Inizio : 25/03/2008
<br> Prossimo Cu : 27/08/2012
<br> Data corrente : 31/03/2012
<br>
<br> Siccome la data del prossimo controllo va oltre il 31/06/2012 il controllo non verrà visualizzato nello scadenzario 
<br><br>

<h2>Chiusura D'Ufficio</h2>
<br> Cliccando sul bottone "Controlli che verranno chiusi
d'Ufficio" verranno visualizzati tutti i controlli inseriti, dall'utente
loggato nel sistema, che si trovano nello stato aperto e per cui non
sono stati inseriti campioni e/o tamponi, o che sono stati inseriti ma
risultano chiusi. I controlli visualizzati sono tutti quelli per cui e'
trascorsa una settimana dalla data di inserimento e il sistema indica la
data in cui provvederà a chiuderli automaticamente, ovvero dopo
un mese dalla data di inserimento. Nel caso in cui il controllo abbia
una non conformità associata , il sistema provvederà a
chiudere anche la non conformità con i followup associati. 
<p></p>

<br>
<h2>Associazione Esiti-Campioni</h2><br>
Tra le funzionalità presenti nella sezione Home, e' stato aggiunto il 
link "Associazione Esiti-Campioni".
Tale funzionalità permetterà di ricercare i campioni senza esito 
presenti su capi e/o partite per i quali e' stato stampato l'articolo 17.
I filtri di ricerca presenti nella maschera sono i seguenti:<br>
<ul>
	<li>Numero matricola/partita: [campo di testo libero]</li>
	<li>Data macellazione: [campo data]</li>
</ul><br>

<img src="images/associacampione1.PNG" name="associacampione1.PNG" width="500" align="LEFT" border="0"> <br clear="left"><br>
Effettuata la ricerca il sistema visualizzerà i campioni senza esito 
trovati, ovviamente in base ai filtri opportunamente specificati
I campi visualizzati saranno:<br>
<ul>
	<li>Tipo macellazione: (Bovini | Ovicaprini)</li>
	<li>Numero matricola/partita</li>
	<li>Numero seduta</li>
	<li>Data Macellazione</li>
	<li>Impresa</li>
	<li>Motivo campione</li>
	<li>Matrice</li>
	<li>Tipo analisi</li>
	<li>Molecole/a agente etiologico</li>
	<li>Esito</li>
	<li>Data Esito</li>
</ul><br>

<img src="images/associacampione2.PNG" name="associacampione2.PNG" width="500" align="LEFT" border="0"> <br clear="left"><br>

Dopo la ricerca, sarà presente una lista di record, a questo punto 
l'utente potrà attribuire al campione scelto un "esito" e una "data 
esito" 
e potrà associarlo al campione mediante il bottone [ASSOCIA], presente 
nella maschera.<br>
Tale esito messo per il campione, saràï¿½ visibile nel dettaglio del campione presente nel Controllo Ufficiale.<br><br>

<br>
<h2>Configura Alberi per piani</h2>
<br>
<p style="text-align: justify">Dopo aver effettuato il login, dal 
cavaliere Home sarà disponibile un link dal nome Configura Alberi Per 
Piani a partire dal quale 
lutente potrà associare, per ciascun piano, le matrici e gli analiti. <br>
Tale operazione sarà possibile dopo aver cliccato rispettivamente su 
Analiti e Matrici, 
nella colonna di configurazione a seconda di quello che si vuole 
configurare; apparirà una popup contenente 
lintero albero, con i nodi (foglie dellalbero) selezionabili, ed un 
pulsante Salva Configurazione per salvare i dati nel sistema.<br><br>

<img src="images/blocchi1.png" name="blocchi1.png" height="267" width="637" align="LEFT" border="0"> <br clear="left">
<br>
<br>
<img src="images/blocchi2.png" name="blocchi2.png" height="267" width="637" align="LEFT" border="0"> <br clear="left">
<br>
<br>
Dopo aver associato i blocchi dei piani di monitoraggio di interesse dal
 pannello sopra descritto, sarà possibile a questo punto constatare gli 
effetti della configurazione nellaggiunta di un campione presso un OSA. <br>
Quindi, si ricerca un operatore, si inserisce un controllo ufficiale (in
 piano di monitoraggio di interesse) e si inserisce un campione che ha 
come motivazione del campionamento (o quesito diagnostico) il piano 
stesso. <br>
Allatto dellinserimento della matrice, non apparirà lintero albero ma 
solo quello che stato salvato in fase di configurazione; lo stesso 
accadrà per gli analiti. E chiaro che nel caso in cui non siano inserite
 configurazioni ad hoc, 
gli alberi matrici/analiti saranno visualizzati per intero, idem per i 
casi non previsti dalla configurazione. Questultima condizione si ha per
 alcuni piani e per tutti i motivi del campionamento che sono del tipo 
NON PIANO (Es. Ispezione per Sospetta Presenza N.C, per classificazione,
 etc).<br><br>

<img src="images/blocchi3.png" name="blocchi3.png" width="500" align="LEFT" border="0"> <br clear="left">
<br>
<br>
<img src="images/blocchi4.png" name="blocchi4.png" width="500" align="LEFT" border="0"> <br clear="left">

</p><div class="fine" style="height: 50px;">&nbsp;</div>


<a name="aiequidi"></a>
<h1>A.I. Equidi</h1>
<br> Qui data la possibilità di ricercare e visualizzare le
aziende equidi allo scopo di rilevare il numero di quelli presenti
stabilmente sul territorio e conoscere dove sono, da dove provengono e a
chi appartengono. Tali notizie possono essere ricercate usufruendo del
filtro per anno, Identificativo e num. Accertamento. 
<br>
<img src="images/equidi.PNG" name="equidi.PNG" height="367" width="637" align="LEFT" border="0"><br clear="LEFT"><br><br>

<div class="fine" style="height: 50px;">&nbsp;</div>


<a name="acquedirete"></a>
<h1>Acque di Rete</h1>
<h2>Premessa</h2>
<br>
<p style="text-align: justify">disponibile in GISA la gestione dei 
controlli sulle acque di rete ovvero tutti i controlli effettuati per il
 PIANO 59 - 
PIANO DI MONITORAGGIO ACQUE DESTINATE AL CONSUMO UMANO  - sui punti di 
prelievo presenti sul territorio regionale raggruppati per comune<br>
Le funzionalità disponibili saranno:
</p><ul>
	<li>ricerca/inserimento/modifica anagrafica pdp</li>
	<li>gestione controlli ufficiali versione semplificata (sola gestione controlli e campioni)</li>
</ul>
<p></p>

<br>
<h2>Accesso al modulo</h2>
<br>
<p style="text-align: justify">Il modulo accessibile dal cavaliere 'Acque di rete'. <br>
<img src="images/acque.png" name="acque.png" height="100" width="500" align="LEFT" border="0"> <br clear="left"><br>
Entrando nel modulo la prima pagina visualizzata sarà quella di ricerca 
da cui sara' possibile ricercare un punto di prelievo - PDP - per 
l'attribuzione dei controlli effettuati e qualora non risulti in banca 
dati sara' possibile aggiungerlo mediante la funzione "Aggiungi" (link 
in alto a sinistra).
<br><br>
</p>
<br>

<h2>Inserimento Punti di Prelievo</h2>
<br>
<p style="text-align: justify">Qualora un pdp non sia gia' presente in GISA lo si potrà inserire (mediante la funzione "Aggiungi" (link in alto a sinistra) 
compilando i seguenti campi:
</p><ul>
	<li>ASL*  sarà già settata di default</li>
	<li>denominazione*  campo di testo</li>
	<li>tipologia*  elenco</li>
	<li>ente gestore*  campo di testo</li>
	<li>stato*  attivo / Non attivo</li>
	<li>comune*  elenco comuni ASL</li>
	<li>indirizzo*  campo di testo</li>
</ul>
saranno obbligatori tutti i campi contrassegnati da *. 
Inoltre in fase di inserimento il sistema attribuirà un identificativo 
univoco con la seguente convenzione: "codiceistatcomune+codice codifica 
tipologia 
PDP+progressivo"". Gli stessi campi saranno contenuti anche nella 
maschera di modifica.
<br>
 
<img src="images/acque_2.png" name="acque_2.png" height="100" width="500" align="LEFT" border="0"> <br clear="left"><br>
	
Entrando nel modulo la prima pagina visualizzata sarà quella di ricerca 
da cui sara' possibile ricercare un punto di prelievo - PDP - per 
l'attribuzione dei controlli effettuati e qualora non risulti in banca 
dati sara' possibile aggiungerlo mediante 
la funzione "Aggiungi" (link in alto a sinistra).
<br><br>
<p></p>

<h2>Gestione Controlli Ufficiali</h2>
<br>
<p style="text-align: justify">Una volta acceduto alla scheda di 
dettaglio sara' possibile accedere alla gestione controlli ufficiali. Si
 tratta in ogni 
caso di un controllo di tipo Ispezione semplice  in piano di 
monitoraggio  Piano 59 con oggetto del controllo Igiene degli alimenti. 
Quindi nella scheda 
del controllo ufficiale risulteranno settati di default: ASL, tipo di 
controllo e oggetto del controllo. Non viene gestita l'informazione 
sulla linea 
attività sottoposta a controllo.
<br>
 
<img src="images/acque_3.png" name="acque_3.png" height="100" width="500" align="LEFT" border="0"> <br clear="left"><br><br>
Dal momento che nell'ambito di uno stesso controllo potranno esserci 
pipunti di prelievo controllati vi e' la possibilita', in fase 
inserimento 
controllo, di selezionare altri punti di prelievo (cliccando sul bottone
 "aggiungi punti di prelievo" si aprira' una maschera da cui si potranno
 
selezionare i punti di prelievo di interesse spuntando l'apposita 
checkbox).<br>
Per ogni PDP vengono gestite le seguenti informazioni:
</p><ul>
	<li>temperatura acqua  campo di testo</li>
	<li>cloro residuo  campo di testo</li>
	<li>ora prelievo  campo di testo</li>
	<li>protocollo di routine  checkbox </li>
	<li>protocollo di verifica  checkbox </li>
	<li>protocollo di replica</li>
	<li>altro  campo di testo </li>
</ul>
<br>
 
<img src="images/acque_4.png" name="acque_4.png" height="100" width="500" align="LEFT" border="0"> <br clear="left"><br>
<img src="images/acque_5.png" name="acque_5.png" height="100" width="500" align="LEFT" border="0"> <br clear="left"><br>
<br><br>
In fase di inserimento del controllo ufficiale (quando si clicca sul bottone "Inserisci") il sistema automaticamente 
crea tanti campioni quanti sono i PDP inseriti nel controllo ufficiale.
<br><br>
<img src="images/acque_6.png" name="acque_6.png" height="100" width="500" align="LEFT" border="0"> <br clear="left"><br><br>
Per ogni campione il sistema setta in automativo:
<ul>
	<li>Motivo campione: piano 59</li>
	<li>Matrice: acqua di rete</li>
	<li>Tipo analisi: il protocollo (routine, verifica, replica) 
specificato in fase di inserimento controllo.L'utente dovrà inseire solo
 
		l'esito del campione</li>
</ul>
<br><br>
<img src="images/acque_7.png" name="acque_7.png" height="100" width="500" align="LEFT" border="0"> <br clear="left"><br><br>
<p></p>

<h2>Gestione Verbale di Prelievo</h2>
Dal dettaglio di ciascun campione e' possibile stampare il verbale di 
prelievo cumulativo. Nel verbale sono presenti alcuni campi editabili 
(quelli di colore azzurro).
<br><br>
<br>
<p style="text-align: justify">
<br>
<img src="images/acque_8.png" name="acque_8.png" height="100" width="500" align="LEFT" border="0"> <br clear="left"><br><br>
</p>
<div class="fine" style="height: 50px;">&nbsp;</div>	

<a name="altrioperatori"></a>
<h1>Altri Operatori</h1>
<br> Nell'ambito del cavaliere "Altri Operatori", data la
possibilità di ricercare e visualizzare varie tipologie di Operatori: 
<ul>
	<li>Distributori Automatici: possibile accedere all'elenco dei distributori automatici presenti nella propria ASL.<br>
 
		Cliccando sulla denominazione dell'azienda, possibile andare nel 
dettaglio e visualizzare gli eventuali controlli ufficiali ad essa 
collegata. </li>
	<li>Imprese Fuori Ambito ASL: sempre dallo stesso cavaliere, possibile 
ricercare ed inserire nuove imprese erogatrici di distributori 
automatici 
		registrate presso altre ASL della Campania.<br>
		Cliccando sulla denominazione dell'azienda, possibile andare nel 
dettaglio e visualizzare gli eventuali controlli ufficiali ad essa 
collegata. </li>
	<li>Operatori Abusivi: possibile fare una ricerca, aggiungere un nuovo 
operatore abusivo ed il luogo dove stata rilevata l'attività abusiva 
		nella propria ASL. E' necessario inserire almeno i campi obbligatori.</li>
	<li>Privati: E' possibile fare una ricerca (anche per cod. Allerta) o inserire un privato per la propria ASL.</li>
	<li>Attività mobili fuori ambito ASL: possibile ricercare ed inserire nuove imprese erogatrici di attività mobili registrate presso
		altre ASL della Campania.<br> Cliccando sulla denominazione dell'azienda, possibile andare nel dettaglio e visualizzare gli eventuali 
		controlli ufficiali ad essa collegata.</li>
	<li>Altri operatori non presenti altrove: in questo modulo sono 
presenti gli Operatori che non possono essere registrati negli altri 
moduli gestiti in GISA.<br>
		Anche in questo caso, possibile fare una ricerca, modificare, 
visualizzare gli eventuali controlli ufficiali ad essa collegata.</li>
	<li>Imbarcazioni:Sono previsti i seguenti campi da gestire:
<ul>
	<li>Ufficio Marittimo di provenienza che un flag a scelta tra "In regione" e "Fuori regione"</li>
	<li>Data iscrizione agli Uffici Marittimi</li>
	<li>Identificativo della barca U.E(univoco ed obbligatorio)</li> 
	<li>Ragione Sociale dell' armatore obbligatorio</li>
	<li>Partita IVA/Codice Fiscale</li>
	<li>Tonnellate di stazza</li>
	<li>Tipo di pesca a scelta multipla da una lista precaricata</li>
	<li>Sistema di pesca a scelta multipla da una lista precaricata</li>
	<li>Numero iscrizione agli Uffici Marittimi</li>
	<li>Data cancellazione dagli Uffici Marittimi</li>
	<li>Presenza a bordo di impianto di refrigerazione</li>
	<li>Campo Note</li>
	<li>Sede legale armatore</li>
	<li>Ormeggio abituale</li>
</ul>
<br>
E' prevista, al pari degli altri operatori, la gestione dei controlli 
ufficiali e le sottoattività. La visibilità delle imbarcazioni impostata
 per tutti.
	
</li></ul>
<br><br>
<div class="fine" style="height: 100px;">&nbsp;</div>

<a name="oia"></a>
<h1>Autorità Competenti</h1>
<br> Nel menù in alto e' visibile una nuova linguetta Autorità Competenti. Nel
modulo Autorità Competenti e' possibile visualizzare la lista delle strutture della propria ASL.
L'utente ORSA , visualizzerà la lista delle strutture di tutte le asl più i nodi ORSA , OEV ,
SETTORE VETERINARIO , SANITA ANIMALE.
Selezionata la struttura dalla combo "Struttura Soggetta al Controllo" , cliccando sul
pulsante ricerca , il sistema mostrerà la lista dei controlli eseguiti sulla struttura selezionata.
I tipi di controllo inseriti sulle autorità competenti sono di tipo AUDIT, SUPERVISIONE PER SIMULAZIONE ed e'
stata aggiunta una nuova tipologia ovvero AUDIT di FOLLOW UP.
All'atto della chiusura di un controllo sulle autorità competenti sarà obbligatorio allegare il verbale del
rapporto conclusivo.
<br><br>
<u>Audit di Follow Up </u>
Tale tipologia di controllo e' inseribile ESCLUSIVAMENTE nel cavaliere 
delle Autorità Competenti.
La gestione e' la stessa dei controlli di tipo Audit, l'utente può 
scegliere tra "da ORSA" o "INTERNO".
Anche per AUDIT di Follow Up e' possibile effettuare una selezione
multipla dell'oggetto dell'audit stesso.
Anche per questo tipo di controllo, e' contemplato l'inserimento delle 
Osservazioni/Raccomandazioni, che deve essere 
la stessa delle non conformità, inoltre e' possibile inserirne N in ogni 
controllo.
In fase di inserimento/modifica di Osservazioni/Raccomandazioni, oltre 
al normale funzionamento riguardo alla selezione 
multipla dell'oggetto dell'audit e delle osservazioni, 
e' stata gestita una sezione "Dettagli Risoluzione", 
in cui sono presenti i campi: "Data Risoluzione" che e' un campo data, 
"Risolto" che e' SI-NO, "Descrizione" che e' un campo di testo, tutti non 
obligatori.
Questa sezione "Dettagli Risoluzione" e' comune alle Osservazioni, 
Raccomandazioni Significative e Gravi.
<br><br>

<h2>Inserimento controlli ufficiali per piani di monitoraggio</h2>
<br> Quando si inserisce un controllo ufficiale per piano di
monitoraggio, occorre selezionare uno o più piani. Per ogni piano
selezionato occorre stabilire l'unità operativa a cui associare il
controllo ufficiale, in modo tale da incrementare il numero di controlli
eseguiti per quella unità operativa.
<br> Il numero di controlli per una struttura (veterinaria o sian)
dato dal numero di controlli eseguiti dalla struttura (se ci sono) pi
il numero di controlli eseguiti dalle proprie unità operative. Il numero
di controlli eseguiti da un dipartimento di prevenzione sarà dato dal
numero di controlli eseguiti dal dipartimento (controlli pregressi in
quanto non sappiamo a quale unità operativa associare i controlli) pi
il numero di controlli eseguiti dalle strutture che compongono il
dipartimento.
<br> Il numero di controlli eseguiti rispetto ai pianificati sarà
visibile nella sezione Report del cavaliere Programmazione e Reporting
CU, anche qui il livello di visibilità gerarchico, per cui ogni
livello vede tutti i livelli sottostanti e non quelli che lo precedono.

<div class="fine" style="height: 50px;">&nbsp;</div>

<a name="aziendeagricole"></a>
<h1>Aziende Agricole</h1>
<br> In questa sezione viene gestita l'anagrafica delle aziende
agricole, con la possibilità di effettuare controlli ufficiali. 

Inoltre sono stati effettuati  tre tipi di interventi in GISA per questo cavaliere:
<ul>
	<li>Creata una apposita funzionalitàï¿½ disponibile solo ai ruoli di 
amministrazione di sistema (HD1, HD2, Amministratore, ecc.) che consente
 di 
		trasferire un operatore di tipo 'â‚¬Å“Aziende Agricole'â‚¬Â, dagli 
'â‚¬Å“stabilimenti 852'â‚¬Â a 'â‚¬Å“Aziende Agricole'â‚¬Â (che hanno 
come ATECO i codici afferenti 
		alla coltivazione primaria (da 01.11.10 a 01.28.00);</li>
	<li>Revisione/adeguamento della maschera attuale di aggiunta e modifica
 dati del cavaliere 'â‚¬Å“Aziende agricole'â‚¬Â allo scopo di 
consentire il 
		completamento dei dati anagrafici.</li>
	<li>Migrazione dei dati a livello DB dalle 852 ad Aziende agricole, 
adeguando i campi e mantenendo l'informazione ricercabile e 
visualizzabile, 
		siano essi importati o meno.</li>
</ul>
<br><br>

<h2>Inserimento Aziende Agricole</h2>
Nell'ambito della ristrutturazione delle Aziende Agricole, e' stato implementato un nuovo modello di struttura,
che si basa su tre concetti base fondamentali:
<br><br>
<ul>
	<li>IMPRESA [struttura legale] univoca in  GISA</li><br>
	<li>STABILIMENTI [struttura operativa] da 1 a n per ogni impresa</li><br>
	<li>LINEE PRODUTTIVE da 1 a n per ogni stabilimento</li><br>
</ul>
<br>
<img src="images/Immagine_1.PNG" name="Immagine_1.PNG" width="500" align="LEFT" border="0"><br clear="LEFT">
<br>
<br>
L'utente ha la possibilità di selezionare un'impresa tramite il link "RICERCA" presente nella maschera se esistente,
 oppure aggiungerla tramite il link "AGGIUNGI".<br>
La maschera che si presenta all'utente in fase di inserimento e':<br>
<br>
<img src="images/immagine_2.png" name="immagine_2.png" width="500" align="LEFT" border="0"><br clear="LEFT"><br><br>
 
Gli attributi che caratterizzano un'IMPRESA sono divisi in:<br><br>
<u>Dati Impresa</u>
<ul>
	<li>Ditta/Ragione/Denominazione sociale</li>
	<li>Partita Iva (per le societàï¿½ p.IVA e CF sono uguali mentre per le ditte individuali il CF e' quello del titolare)</li>
	<li>Codice Fiscale</li>
	<li>Note</li>
	<li>Domicilio Digitale</li>
</ul>
<u>Sede Legale</u>
<ul>
	<li>Provincia</li>
	<li>Comune</li>
	<li>Indirizzo</li>
	<li>C/O</li>
	<li>coordinate</li>
</ul>
<u>Rappresentante Legale</u>
<ul>
	<li>Nome</li>
	<li>Cognome</li>
	<li>sesso</li>
	<li>Data di Nascita</li>
	<li>Provincia di Nascita</li>
	<li>Comune di Nascita</li>
	<li>Provincia di residenza</li>
	<li>Comune di residenza</li>
	<li>Indirizzo di Residenza</li>
	<li>Codice Fiscale</li>
	<li>Documento di Identità</li>
</ul>
<br>
Ad ogni impresa saranno associati uno o più STABILIMENTI.<br>
Ogni stabilimento sarà caratterizzato da:<br>
<br>
<ul>
	<li>SEDE PRODUTTIVA</li>
	<ul type="circle">
		<li>ASL</li>
		<li>Provincia</li>
		<li>Comune</li>
		<li>Indirizzo</li>
		<li>CAP</li>
		<li>Coordinate</li>
    </ul>
	<li>ASL</li><br>
</ul>
<br>

Inoltre per completare l'inserimento di uno STABILIMENTO, deve essere 
aggiunta una Linea Produttiva. Nella maschera di aggiungi Stabilimento, 
l'utente trova il link <u>Aggiungi Linea Produttiva</u>, e cliccando si aprirà la maschera riportante l'elenco delle linee produttive e 
l'utente puàï¿½ selezionare una o più linee produttive per lo stesso stabilimento.<br><br>

<img src="images/listprod.png" name="listprod.png" width="500" align="LEFT" border="0"><br clear="LEFT"><br><br>

Una volta che e' stato inserito lo Stabilimento, le operazioni possibili 
sullo stabilimento riguardano le operazioni sui Controlli Uffiiciali,
le Variazioni o per meglio dire le volture e i documenti. Una voltura 
può coinvolge l'impresa, il soggetto fisico e lo stabilimento. 
Nell'ipotesi che lo stabilimento interessi più AA.SS.LL, la voltura 
riguarderà dapprima lo stabilimento e seguirà una richiesta 
di approvazione della volture alle stesse. Dopo l' approvazione da parte
 delle AA.SS.LL coinvolte, sarà possibile aggiornare i dati dell'impresa
 
con quelli inseriti in fase di voltura. Viceversa, se lo stabilimento 
interessa una sola ASL, allora la voltura potrà coinvolgere lo 
stabilimento 
e l'impresa.<br><br>
Al fine di allineare il nuovo modello delle Aziende Agricole il più 
possibile agli Stabilimenti 852, e' stata introdotta la possibilità
di effettuare la GENERAZIONE DEL NUMERO REGISTRAZIONE, per gli 
stabilimenti per i quali non e' presente. Il bottone [GENERA NUMERO 
REGISTRAZIONE] non 
dovrà essere presente nel dettaglio dell'azienda agricola, nel caso in 
cui lo stabilimento ha il numero registrazione. In tal caso, nella 
maschera 
di dettaglio dell'azienda agricola deve comparire il bottone 
[COMUNICAZIONE NUMERO REGISTRAZIONE]. Questo documento dovrà riportare 
il numero di 
registrazione generato. e' stata inoltre gestita la stampa del 
[CERTIFICATO DI CESSAZIONE], nel caso in cui lo stato dello stabilimento
 risulta CESSATO, 
ciò si verifica nel caso in cui tutte le linee produttive dello 
stabilimento hanno una data fine attività. Una ulteriore modifica e' 
stata apportata 
alla STAMPA SCHEDA in Azienda Agricola, in quanto nella lista delle 
linee produttive sono presenti anche quelle secondarie e nello stato 
cessato.<br><br>

Controlli Ufficiali
<ul>
	<li>I Controlli Ufficiali potranno essere inseriti sullo Stabilimento, e
 segue il normale iter dell'inserimento dei CU, inserimento, 
		modifica e cancellazione. L'utente preposto a tale inserimento vedrà 
gli stabilimenti della propria ASL, sui quali potràï¿½ inserire 
		i controlli ed avranno l'ASL dello Stabilimento, che ovviamente 
coincide con quella dell'utente stesso. I Controlli potranno essere 
		eseguiti sulle linee di attivitàï¿½ proprie dello stabilimento.</li><br><br>
</ul><br>
Variazione Sede Legale Impresa
<ul>
	<li>La variazione sede legale impresa permette all'utente di poter 
effettuare le modifiche riguardo ai campi della sede legale relativa 
all'impresa. 
		I campi della sede legale impresa, modificabili sono:</li>
	<ul type="circle">
		<li>SEDE LEGALE</li>
		<li>PROVINCIA</li>
		<li>COMUNE</li>
		<li>INDIRIZZO</li>
		<li>C/O</li>
		<li>LATITUDINE</li>
		<li>LONGITUDINE</li>
	</ul>
</ul>
<br>
Variazione Soggetto Fisico Impresa
<ul>
	<li>La variazione del soggetto fisico dell'impresa riguardano il RAPPRESENTANTE LEGALE, i campi modificabili sono:</li>
	<ul type="circle">
		<li>IN REGIONE</li>
		<li>NOME</li>
		<li>COGNOME</li>
		<li>SESSO</li>
		<li>DATA NASCITA</li>
		<li>PROVINCIA NASCITA</li>
		<li>COMUNE NASCITA</li>
		<li>PROVINCIA RESIDENZA</li>
		<li>COMUNE RESIDENZA</li>
		<li>INDIRIZZO RESIDENZA</li>
		<li>CODICE FISCALE</li>
		<li>DOCUMENTO DI IDENTITA'</li>
	</ul>
</ul>
Inoltre attraverso il codice fiscale, l'utente può verificare se il rappresentante legale in questione e' già
presente in GISA.<br><br>

Variazione Linee Attivita
<ul>
	<li>Riguardo alla variazione della linee di attività, le operazioni possibili sono eliminazione, aggiunta delle linee di attività.
		Modifiche riguardo alla data inizio e data fine delle attività e la scelta delle attività principali.</li>
</ul><br>
Documenti
<ul>
	<li>Come per gli altri cavalieri, nella sezione l'utente puàï¿½ creare cartelle, sottocartelle e caricare file.</li>
</ul><br>
Documenti PDF
<ul>
	<li>E' presente l'elenco dei documenti pdf generati nell'operatore e nei controlli.</li>
</ul><br>

<h2>Ricerca Aziende Agricole</h2>
Per quanto riguarda la Ricerca nelle Aziende Agricole, e' cambiato poco.
La ricerca e' possibile valorizzando opportunamente i campi
 ASL, Ragione sociale impresa, codice fiscale impresa, P.IVA impresa, comune sede operativa.
<br>

<div class="fine" style="height: 50px;">&nbsp;</div>


<a name="allevamenti"></a>
<h1>Aziende Zootecniche</h1>
<br>
<h2>Gestione Liste di riscontro sul Benessere Animale</h2>
<br>
<p style="text-align: justify">
In GISA, dopo aver aggiunto un controllo ufficiale in piano di 
monitoraggio legato al benessere animale (sottopiano da A13_A a A13_O), 
viene visualizzato un link tramite cui 
e' possibile compilare, salvare e stampare una lista di riscontro del 
benessere (Vedi Fig.1). <br>
Essa e' legata alla specie dell'azienda zootecnica selezionata, per cui se si ricerca un azienda con specie allevata "Caprini",
si vedrà la scheda del benessere per i caprini, se la specie "Bovini", allora si vedrà quella per i bovini e così via.<br>
In particolare e' possibile :
</p><ul>
	<li>Compilare le liste di riscontro del benessere</li>
	<li>Salvare in banca dati le liste di riscontro del benessere</li> 
	<li>Stampare le liste di riscontro del benessere</li>
	<!--<li>La compilazione e il salvataggio delle schede di rendicontazione per tutti i controlli inseriti in una data azienda zootecnica per cui risulta compilata 
		la relativa scheda del benessere.</li>
	<li>L'invio delle liste di riscontro del benessere tramite WS per ogni CU</li>-->
</ul>
<br>
<br>
<img src="images/piano_benessere.PNG" width="700" align="BOTTOM" border="0"><br>
<br>
<br>
Per i controlli ufficiali inseriti in data successiva al 01/07/2014  possono essere generati solo 4 tipi di allegati:<br>
<!--<ul>
	<!--
	<li>ALLEGATO 1 GALLINE OVAIOLE</li>
	<li>ALLEGATO 2 SUINI</li>
	<li>ALLEGATO 3 VITELLI</li>
	<li>ALLEGATO 4 FAGIANI</li>
	<li>ALLEGATO 4 CAPRINI</li>
	<li>ALLEGATO 4 AVICOLI MISTI</li>
	<li>ALLEGATO 4 BUFALINI</li>
	<li>ALLEGATO 4 BOVINI</li>
	<li>ALLEGATO 4 CAVALLI</li>
	<li>ALLEGATO 4 OVINI</li>
	<li>ALLEGATO 4 QUAGLIE</li>
	<li>ALLEGATO 4 PESCI</li>
	<li>ALLEGATO 4 CONIGLI</li>
	<li>ALLEGATO 5 POLLI DA CARNE</li>
</ul> 
-->
<ul>
<li>ALLEGATO 1 ALTRE SPECIE</li>
	<li>ALLEGATO 2 GALLINE OVAIOLE</li>
	<li>ALLEGATO 3 SUINI</li>
	<li>ALLEGATO 4 VITELLI</li>
</ul> 
Prima di generare il pdf dei suddetti allegati bisogna compilare tramite POPUP i dati del frontespizio (Compila Frontespizio).
<br><br>
<img src="images/bottone20frontespizio.bmp" width="700" align="BOTTOM" border="0"><br>
<br><br>

Cliccando il BOTTONE "Compila/Visualizza lista di riscontro"SPECIE""  si
 può cominciare a compilare la lista e successivamente scegliere se 
salvare temporaneamente oppure definitivamente.
Nel caso in cui si scelga la prima opzione sarà possibile modificare i 
dati inseriti nella popup del frontespizio, ma una volta salvato 
definitivamente sarà possibile solo generare il pdf tramite il bottone 
"GENERA PDF"
<ul>
	<li>Le colonne "SI" e "NO" sono liberamente selezionabili/deselezionabili e possono essere NON valorizzate</li>
	<li>La colonna "SI" può essere valorizzata in caso di punteggio &gt;= 0.</li>
	<li>La colonna "NO" e' vincolata al punteggio &gt; 0.</li>
	<li>E' presente un tasto "Reset" per ogni domanda nelle liste di riscontro per resettare la selezione del "SI" oppure del "NO".</li>
</ul>
I campi finali della checklist (Preavviso) sono recuperati dal controllo ufficiale.
<br>

Ogni scheda del benessere animale ha una parte generale relativa ai dati
  dell'azienda zootecnica selezionata, ed una parte "ad hoc", legata al 
tipo di 
scheda. La prima parte e' automaticamente recuperata dal sistema GISA, 
mentre quella "ad hoc" rappresenta l'insieme di tutte le domande che 
l'utente dovrà rispondere, inserendo un punteggio per i tipi di non 
conformità previsti, di tipo "A" (formale), di tipo "B" (significativo) e
 di tipo "C" (grave), ed eventuali osservazioni per ogni domanda. 
L'esito e il numero di irregolarità sono automaticamente gestiti dal 
sistema: se almeno uno dei campi riservati al punteggio e' valorizzato, 
allora l'esito viene settato a sfavorevole ("X" in corrispondenza della 
colonna "no"), viceversa favorevole ("V" in corrispondenza della colonna
 "si"); 
il numero di irregolarità, invece, sarà pari alla somma dei punteggi A, 
B, C per riga e quindi per ciascuna domanda (Vedi Fig.2). 
<br>
<br>
<img src="images/link2.PNG" width="700" align="BOTTOM" border="0"><br>
<br>
<br>
Altri campi automaticamente gestiti dal sistema saranno i totali delle 
irregolarità e dei punteggi A, B, C, alla fine di ogni scheda (Vedi 
Fig.3).
<br>
<br>
<img src="images/totale1.PNG" width="700" align="BOTTOM" border="0"><br>
<br>
<br>
Quindi, come già accennato precedentemente, dopo aver compilato la 
scheda, e' possibile salvare e stampare la stessa, tramite gli appositi 
bottoni (Vedi Fig.4). 
Cliccando su "Salva Temporaneo" oppure su "Salva Definitivo", partirà un
 messaggio che chiede conferma dell'operazione, a seguito della quale 
la finestra della lista di riscontro sul benessere verrà chiusa 
automaticamente.<br> 
Cliccando sul bottone "Stampa", invece, l'utente decide semplicemente di stampare il documento.
<br>
<br>
<img src="images/totale1_1.PNG" width="700" align="BOTTOM" border="0"><br>
Inoltre e' stato aggiunto il bottone verde "Chiudi CU senza compilare 
liste di riscontro" nel dettaglio dei controlli ufficiali delle aziende 
zootecniche.<br>
Il bottone funziona in questo modo:
<ul>
	<li> SE non sono state compilate liste di riscontro, o sono state 
compilate senza irregolarità(punteggio 0), il controllo viene chiuso 
normalmente</li>
	<li> SE sono state compilate liste di riscontro con 
irregolarità(punteggio &gt; 0), il controllo non viene chiuso e viene 
restituito un errore</li>
	<li> SE il controllo e' stato chiuso tramite questa nuova funzione, non appariranno più i bottoni relativi alle liste di riscontro</li>
</ul>
<br>
<img src="images/bottone_verde.PNG" width="700" align="BOTTOM" border="0"><br>
<br>
<br>
<!--<H2>Rendicontazione Benessere Animale</H2>
Nel cavaliere "Aziende Zootecniche", &egrave; reso disponibile, su permessi ad hoc, il link "Rendicontazione Benessere Animale".<br>
Per generare una scheda di rendicontazione &egrave; necessario specificare la specie allevata e l'anno di rendicontazione dei controlli. 
<BR>
<br>
<IMG SRC="images/rendic1.png" ALIGN=BOTTOM WIDTH="500" BORDER=0><br>
<br>
<br>
Cliccando sul pulsante "Visualizza", sar&agrave; mostrato a video una popup contenente la scheda relativa 
all'anno e alla specie selezionati. 
<BR>
<br>
<IMG SRC="images/rendic2.png" ALIGN=BOTTOM WIDTH="500" BORDER=0><br>
<br>
<br>
In questa scheda sono riportati i capitoli relativi alla lista di riscontro di interesse, 
con i punteggi A, B, C, e Numero Totale Irregolarit&agrave;, rispettivamente somme di tutti punteggi A, B, C, 
e Irregolarit&agrave;, inseriti in tutti i controlli relativo allanno selezionato. <br>
La scheda verr&agrave; compilata in automatico e potr&agrave; essere stampata con l'apposito pulsante di "Stampa". 
Nel caso in cui non risultassero rendicontazioni esistenti, per mancanza di liste di riscontro compilate,
il sistema segnaler&agrave; con un messaggio la mancata disponibilit&agrave; del modulo.

<H2>Invia CU del benessere animale</H2>
<br><br>
Nel cavaliere Aziende Zootecniche in GISA, e' presente il link  "Invia CU BA" tramite cui e' possibile inviare tutti i controlli 
del benessere animale relativi all' intervallo di date che &egrave; richiesto di selezionare.<br> 
I controlli saranno inviati alla BDN, tramite opportuni Web Services (WS). La funzionalit&agrave; di invio massivo generr&agrave un file di LOG con le seguenti informazioni:
<ul>
	<li>Data operazione</li>
	<li>Identificativo CU</li>
	<li>Data cU</li>
	<li>Codice Azienda</li>
	<li>Specie</li>
	<li>Esito Invio</li>
	<li>Errori</li>
	<li>Tipo Invio</li>
	<li>Inviato da</li>
</ul>	
<BR>
<br>
<IMG SRC="images/invio1.png" ALIGN=BOTTOM WIDTH="500" BORDER=0><br>
<br>
<br>
E' possibile inviare anche un singolo controllo avente come motivo di ispezione il piano relativo al benessere animale. Per quanto riguarda l'invio, sar&agrave; sufficiente 
raggiungere la scheda del dettaglio, accertarsi che il controllo sia chiuso e il pulsante "Invia al ministero"" visibile.<br>
Tramite questo bottone, sar&agrave; possibile inviare il controllo tramite i WS e visualizzare l'esito dell'invio alla BDN con eventuali errori, 
riepilogativa nel dettaglio del controllo stesso.
<BR>
<br>
<IMG SRC="images/invio2.png" ALIGN=BOTTOM WIDTH="500" BORDER=0><br>
<br>
<br>
-->
<h2>Gestione condizionalità nei Controllo Ufficiali per Aziende Zootecniche</h2>
<br>
In fase di inserimento di un Controllo Ufficiale con tipologia Ispezione
 Semplice e' stata inserita una nuova motivazione: CONDIZIONALITA', 
scegliendo questa motivazione e selezionando nel campo condizionalita: 
"ATTO B11: RINTRACCIABILITA' E SICUREZZA ALIMENTARE"
 nel controllo ufficiale esce il link  della scheda: <font color="blue">"Check-list controllo condizionalitàAtto B11-Sicurezza Alimentare"</font> che l'utente ha 
 l'obbligo di compilare e salvare in maniera definitiva prima di poter chiudere il Controllo Ufficiale.<br>

 <!--Si richiede una gestione simile alla sorveglianza, ovvero l'utente inserisce
il controllo ufficiale in GISA e dalla maschera di dettaglio dovrcompilare la
checklist prima della chiusura del controllo ufficiale. Una volta salvata la checklist
potressere salvata come pdf e stampata.-->
<br>
<br>
<br>
<img src="images/condizionalita.PNG" width="700" align="BOTTOM" border="0"><br>
<br>
<br>
Inoltre se si sceglie, in fase di inserimento controllo ufficiale, "NON 
NECESSITA DI CONDIZIONALITA'", e' possibile salvare il controllo 
ufficiale senza la generazionedella check-list.
 <img src="images/non_necessita_condizionalita.PNG" width="700" align="BOTTOM" border="0"><br>
 <br>
<h3> Ricompila Checklist Atto B11 aggiornata</h3>
In GISA, nel cavaliere " Aziende zootecniche", per i controlli in 
condizionalità e Atto B11 viene data agli utenti la possibilità di 
ricompilare la checklist aggiornata, 
<br> secondo il modello ministeriale per i casi in presenza di non 
conformità.
Pertanto nel dettaglio dei controlli ufficiali in Condizionalità ed atto
 B11, in caso di non conformità registrate nella scheda non ministeriale
 compilata con la vecchia scheda, 
<br> comparirà un bottone " Ricompila Checklist Atto B11 aggiornata" .
Cliccando su questo bottone, verrà eliminata la vecchia checklist per 
compilare la nuova, in modo da registrare le non conformità secondo 
l'ultimo e valido template.
La possibilità di ricompilare la checklist, avverrà naturalmente solo in
 presenza di schede B11 <br> 
" obsolete"(antecedenti al 01/07/2014).
<br>
<br>

 
<h2>Invio controlli Benessere Animale</h2>
Nel cavaliere "Aziende zootecniche" e' presente una funzionalità che consente di inviare i controlli del benessere animale.
La funzione si chiama "Invia CU BA" ed e' disponibile solo al ruolo HD I livello.<br>
Per fare chiarezza, di seguito verrà riportato il dettaglio della 
modalità di estrazione dei campi più significativi richiesti dalla BDN:
<ul>
	<li>Punteggio: somma dei punteggi A, B e C riportati nella lista di riscontro compilata</li>
	<li>Ragione sociale</li>
	<li>ID CU</li>
	<li>Data CU</li>
	<li>ASL</li>
	<li>Codice Azienda</li>
	<li>ID fiscale allevamento</li>
	<li>OCCODICE: e' la codifica relativa al nucleo ispettivo che ha partecipato all'ispezione. Alla BDN viene ritornato:
		<ul>
			<li>'001': ruolo NAS</li>
			<li>'002': ruolo Guardia Forestale</li>
			<li>'003': Veterinari e altri</li>
		</ul></li>
  <li>Flag CEE: di default false</li>
  <li>Stato CU: per l'invio deve essere CHIUSO o CHIUSO SENZA LISTA DI NON CONFORMITA'</li>
  <li>flag EXTRAPIANO: attualmente e' N. Sappiamo che il PIANO per il 
benessere animale e' A13 ma al 19/11/2015 non sappiamo quale sia 
l'EXTRAPIANO. </li>
  <li>ID fiscale detentore</li>
  <li>Esito Controllo: 'S' quando il punteggio e' &lt;=0; 'N' viceversa</li>  
	<li>Specie allevata</li>
  <li>Tipo Allegato</li>
  </ul>
  <table border="1px">
  <tbody><tr>
  	<td>Allegato GISA</td>
    <td>Allegato BDN</td>
    <td>Specie</td>
  </tr>
  <tr>
    <td>1</td>
    <td>4</td>
    <td>Polli da carne ed altre specie</td>
  </tr>
  <tr>
    <td>1</td>
    <td>5</td>
    <td>Avicoli</td>
  </tr>
  <tr>
    <td>2</td>
    <td>1</td>
    <td>Galline ovaiole</td>
  </tr>
  <tr>
    <td>3</td>
    <td>2</td>
    <td>Suini</td>
  </tr>
  <tr>
    <td>4</td>
    <td>3</td>
    <td>VITELLI</td>
  </tr>
  </tbody></table>
  
  <li>Flag vitelli: dipende dal piano. Per il piano A13_A ed il piano A13_B il flag e' settato a true, per tutti gli altri piani no.</li>


  


<div class="fine" style="height: 50px;">&nbsp;</div>

<a name="requestor"></a>
<h1>DIA</h1>
<br> Il modulo DIA presente in GISA e' stato predisposto per
la presentazione della Denuncia di Inizio Attivit&amp;agrave.
<br> La Denuncia di Inizio attività, ai fini sanitari,
e' necessaria per iniziare tutte le attività artigianali
e/o industriali, di produzione di generi alimentari, nonché tutti
gli aggiornamenti e le variazioni relative all'attività, ai
locali o al ciclo produttivo. E' inoltre prevista per i mezzi di
trasporto di alimenti, la vendita ed il deposito all'ingrosso di
sostanze alimentari.
<br> L'inserimento di una DIA in GISA e' possibile a vari
ruoli ed utenti operanti nell'ambito di una data asl. In fase di
aggiunta DIA e' possibile scegliere se si tratta di una DIA
SEMPLICE che una DIA DIFFERITA: 
<ul>
	<li>Con la DIA SEMPLICE, l’operatore e' legittimato ad
	iniziare l’attività contestualmente alla presentazione
	della DIA stessa;</li>
	<li>Con la DIA DIFFERITA, l’operatore e' legittimato
	ad iniziare l’attività decorsi 30 giorni dalla
	presentazione della DIA, salvo provvedimenti inibitori del Responsabile
	della ASL locale.</li>
</ul>
<br> 
<br>
Inoltre, cosa importante e' l'inserimento del codice
ATECO che e' un codice identificativo alfanumerico che classifica
le imprese quando entrano in contatto con le pubbliche istituzioni e
quando dialogano tra loro. Questo codice viene fornito
all’apertura di una nuova attività e ne rende possibile la
classificazione a livello contributivo. Grazie al codice Ateco e'
possibile stabilire la categoria di pertinenza della nostra
attività ai fini fiscali e statistici. Alla DIA, dopo essere
stata esaminata, viene attribuito un esito, che può essere
FAVOREVOLE, NON FAVOREVOLE o FAVOREVOLE CON LIEVI NON CONFORMITA'. A
seguito dell'inserimento di un esito e della data completamento DIA
viene generato un numero di registrazione dell'attivit&amp;agrave.
Tale attività può essere a sua volta cambiata in OSA e la
ritroveremo nel modulo Stabilimenti registrati 852. 

<div class="fine" style="height: 50px;">&nbsp;</div>


<a name="laboratorihaccp"></a>
<br>
<h1>Laboratori HACCP</h1><br>
<br> Nell'ambito del cavaliere "Laboratori HACCP", data la
possibilità di effettuare diverse operazioni tra cui: 
<ul>
	<li>Ricerca Laboratori o per Prove</li>
	<li>Aggiungi/Modifica/Aggiungi Elenco matrici: possibile
	aggiungere/modificare un laboratorio o una lista di matrici e
	denominazioni prove già presente in banca dati.</li>
	<li>Esporta elenco laboratori HACCP: estrazione in formato xls.</li>
</ul>
<br>
<div class="fine" style="height: 50px;">&nbsp;</div>

<a name="aiequidi">
<h1>A.I. Equidi</h1>
<br> Qui data la possibilità di ricercare e visualizzare le
aziende equidi allo scopo di rilevare il numero di quelli presenti
stabilmente sul territorio e conoscere dove sono, da dove provengono e a
chi appartengono. Tali notizie possono essere ricercate usufruendo del
filtro per anno, Identificativo e num. Accertamento. 
<br>
<img src="images/equidi.PNG" name="equidi.PNG" height="367" width="637" align="LEFT" border="0"><br clear="LEFT"><br><br>
<div class="fine" style="height: 50px;">&nbsp;</div>


</a><a name="macroareaIUV"></a>
<h1>Macroarea I.U.V.</h1>
<br> Nell'ambito del cavaliere "Macroarea I.U.V.", data la possibilità di effettuare diverse operazioni tra cui: 
<ul>
	<li>Ricerca Rapida Canili</li>
	<li>Ricerca Controlli ufficiali su Anagrafica Cani di Proprietà</li>
	<li>Ricerca Operatori Commerciali</li>
	<li>Ricerca Zone di controllo</li>
	<li>Ricerca Colonie</li>
</ul>
<br>
<h2>Ricerca Rapida Canili</h2> 
<br> E' possibile visualizzare l'elenco dei canili di competenza della propria ASL e già inseriti e gestiti in BDR. 
Da questo modulo si accede ai "Controlli Ufficiali" su canili e cani di proprietà con la possibilità di 
inserire e modificare (vedi link 'Controllo Ufficiale'). 
<br><br>
<h2>Ricerca Controlli ufficiali su Anagrafica Cani di Proprietà</h2> <br>
E' possibile accedere alle aree di informazioni sulla anagrafe canina 
tramite i tasti 'Accesso alla BDR' e 'Accesso alla BDN', come mostrato
nella figura sottostante. 
<img src="images/link_canina.PNG" name="link_canina.PNG" width="500" align="LEFT" border="0"><br clear="LEFT"><br><br>
<br> Da questo modulo si accede anche ai "Controlli Ufficiali" allo scopo di monitorare la lista dei controlli ufficiali di propria
competenza, ancora aperti e quelli in scadenza. 
<br>E' possibile inoltre accedere all'area 'verifica punteggio accumulato'. 
<br><br>
<h2>Ricerca Operatori Commerciali</h2> 
<br> E' possibile visualizzare l'elenco degli operatori commerciali di competenza della propria ASL. Da
questo modulo si accede ai "Controlli Ufficiali" con la possibilità di inserire e modificare (vedi link 'Controllo Ufficiale'). 
<br><br>
<h2>Operatori Commerciali</h2>
<br>
<p style="text-align: justify">E' stata introdotta la possibilità di
inserire da GISA un nuovo Operatore Commerciale la cui specie di animali
che si intende commercializzare diversa da cani, gatti e furetti. Gli
Operatori Commerciali che vendono cani, gatti e furetti sono importati
in GISA a partire dalla BDR.<br> Per aggiungere un nuovo Operatore
Commerciale bisogna accedere al cavaliere "Macroarea IUV", nella sezione
Operatori Commerciali e successivamente al link Aggiungi nuovo
Operatore. I campi obbligatori da inserire sono: ragione sociale, asl,
partita iva, comune sede legale ed operativa, indirizzo sede legale ed
operativa, nome, cognome, codice fiscale del legale rappresentante e la
specie di animali che si intende commercializzare. Le possibili scelte
delle specie sono: "RETTILI", "PESCI", "ANFIBI", "VOLATILI". In
qualsiasi momento possibile eseguire delle modifiche sulla scheda di
dettaglio di un Operatore Commerciale, cliccando sul pulsante
"modifica". Le modifiche effettuate non verranno comunicate alla BDR. 
</p>
<br>
<h2>Operatori Commerciali Provenienti Da BDR</h2>
<br>
<p style="text-align: justify">Ogni volta che in BDR viene inserito
un nuovo Operatore Commerciale, successivamente possibile ricercarlo
anche in GISA. Un Operatore Commerciale che stato inserito dalla BDR 
modificabile solo attraverso GISA e non dalla BDR, successivamente
eventuali modifiche effettate da GISA verranno trasmesse anche alla BDR.
<br>
<br>
</p>
<h2>Zone di Controllo</h2> <br> E' possibile visualizzare
l'elenco delle aree sottoposte a controllo effettuando una ricerca per
"Descrizione luogo del controllo" o valorizzando il campo "comune" oppure "ASL".
Da questo modulo si accede ai "Controlli
Ufficiali" con la possibilità di inserire e modificare (vedi link
'Controllo Ufficiale'). 
<br><br>

<h2>Aggiungi Nuova Zone di Controllo</h2>
<br>
<p style="text-align: justify">e' stata introdotta in gisa la possibilità 
di inserire un nuovo tipo di operatore "Zone di Controllo", al fine di
poter aggiungere controlli ufficiali.<br>
Il modulo relativo alle zone di controllo e' presente in Macroarea IUV.
Una zona di controllo potrà essere aggiunta medianre il link "Aggiungi nuova zone di controllo".
I campi da compilare al fine di poter inserire una zona di controlo sono:
</p><ul>
	<li>ASL</li>
	<li>Descrizione luogo del controllo</li>
	<li>Provincia</li>
	<li>Comune</li>
	<li>Indirizzo</li>
	<li>CAP</li>
</ul>

In fase di inserimento di una zona di controllo, se l'utente che 
effettua l'inserimento ha un'asl settata, il campo "ASL" della
maschera di inserimento esce già valorizzata con l'asl dell'utente che 
effettua l'inserimento, invece se l'utente non 
ha un'unica ASL settata, ma puàï¿½ inserire zone di controllo per tutte 
le ASL, potràï¿½ scegliere una tra le 7 ASL.
Anche il campo "Provincia" si setta automaticamente a seconda del comune
 scelto.
Il campo Indirizzo deve essere obbligatoriamente valorizzato, invece il 
campo CAP se non viene valorizzato dall'utente, il sistema lo setta in 
automatico
successivamente al salvataggio.
Il questo modulo e' prevista la stessa gestione dei controlli ufficiali 
(come per gli altri operatori),
ma con la possibilità,di replicare il controllo che si sta per inserire 
in altre zone.
Dopo aver compilato i classici campi (data del cu,tipo di 
controllo,oggetto del controllo,
nucleo ispettivo) , mediante il link "Aggiungi Zone" apparirà una popup 
che mostra tutte le zone presenti
per l'asl del controllo che si sta inserendo.<br> 
e' possibile selezionare tutte le zone che saranno soggette al controllo 
che si sta per inserire. Nel momento che si clicca sul pulsante 
"inserisci", il controllo
verrà inserito per tutte le zone che sono state selezionate.
<p></p>
<br><br <p="">
<h2>Colonie</h2>
<br>
<p style="text-align: justify">Coscome per i canili stata introdotta in gisa
la possibilita di poter inserire controlli ufficiali sulle colonie, in particolare i 
controlli per piano di monitoraggio "PIANO 83 PIANO DI MONITORAGGIO CENSIMENTO E CONTROLLO STATO SANITARIO
DELLE COLONIE FELINE".<br>
Una colonia non viene inserita da gisa, ma dall'anagrafe felina (Tra poco dalla banca dati unificata).
All'atto di inserimento di una colonia sul sistema felina, questa verra' replicata nel sistema gisa, 
in modo da poter inserire i controlli svolti su di essa.<br>
La modifica di una colonia viene comunicata da felina a gisa cosi come l'inserimento.
E' prevista la stessa gestione dei controlli ufficiali, anche se per le colonie stata realizzato
un meccanismo di replica sul sistema felina: ogni controllo inserito in gisa su una colonia
viene copiato in maniera minimale ( non tutti i campi ma solo : data,tipo di controllo data chiusura) 
nel sistema felina, in modo che felina tiene traccia del numero di controlli eseguiti sulle colonie.
</p>
<br>
<p></p>
<div class="fine" style="height: 50px;">&nbsp;</div>

<a name="molluschibivalvi"></a>
<h1>Molluschi Bivalvi</h1>
<br>
<p style="text-align: justify">Il cavaliere "molluschi bivalvi"
consente di inserire nel sistema le zone di produzione di molluschi
presenti nella regione campania e le aziende che hanno concessioni su di
esse. <br>Una zona di produzione puessere di tipo : "Banchi
naturali", "Zona di stabulazione", "Zone non in concessione/Impianti
abusivi", "Zone in concessione/impianti molluschicoltura", "Specchio
acqueo in fase di prima classificazione".
<br> Le operazioni che possono essere effettuate dall'utente medio
sono : 
</p><ul>
	<li>Ricerca Zona di produzione</li>
	<li>Ricerca di Concessionari</li>
	<li>Inserimento Zona di produzione</li>
	<li>Inserimento di Concessionari</li>
</ul>
L'utente Veterinario potrà inserire nel sistema solo le zone di
produzione di tipo "Specchio acqueo in fase di prima classificazione" e
"Zone non in concessione / Impianti abusivi". I campi gestiti per il
tipo di produzione "Specchio acqueo in fase di prima classificazione"
sono i seguenti :
<ul>
	<li>Asl</li>
	<li>tipo di mollusco (campo a selezione multipla)</li>
	<li>comune</li>
	<li>località</li>
	<li>coordinate</li>
</ul>
Ogni zona di produzione puavere massimo 6 coppie di coordinate
associate, di cui una obbligatoria. Gli altri campi obbligatori sono
asl, comune, località.  <br>
<br>
<img src="images/add_1.png" name="add_1.png" height="367" width="637" align="LEFT" border="0"><br clear="LEFT">
<br> I campi gestiti per il tipo di produzione "Zone non in
concessione/Impianti abusivi" sono i seguenti : 
<ul>
	<li>Asl</li>
	<li>tipo di mollusco (campo a selezione multipla)</li>
	<li>nome</li>
	<li>cognome</li>
	<li>note</li>
	<li>comune</li>
	<li>località</li>
	<li>coordinate</li>
</ul>
Anche in questo caso, ogni zona di produzione puavere max 6 coppie di
coordinate associate di cui una obbligatoria e gli altri campi
obbligatori sono asl, comune, località. <br>
<br>
<img src="images/add2.png" name="add2.png" height="367" width="637" align="LEFT" border="0"><br clear="LEFT">
<br>
<br> Ogni zona di produzione di tipo "Specchio acqueo in fase di
prima classificazione" dovrà essere classificato dall'operatore regione
mediante il pulsante "Classifica" presente nel dettaglio dello specchio
acqueo. Mediante il pulsante classifica si aprirà una nuova finestra in
cui l'operatore regione dovrà inserire i seguenti dati : 
<ul>
	<li>classificazione</li>
	<li>numero decreto</li>
	<li>data classificazione</li>
	<li>tipo di zona di produzione in cui classificare lo specchio
	acqueo (esempio banco naturale , stabulazione o impianto
	molluschicoltura)</li>
	<li>molluschi associati alla zona</li>
</ul>
Nel dettaglio di una zona, in fase di associazione di un nuovo concessionario vengono gestiti due campi non obbligatori:
 'numero concessione' e 'data concessione'. 


<b>NOTE:</b>
<ul>
	<li>Nella scheda delle zone in concessione viene gestito un campo 'CUN'
 obbligatorio contenente un numero di sette cifre che costituisce il 
Codice Univoco Nazionale.</li>
	<li>I campi classificazione e data classificazione contengono le 
informazioni relative alla prima classificazione della zona ovvero 
quella da decreto e il loro contenuto non cambia fino alla scadenza del 
decreto.</li>
	<li>Quando si inserisce una nuova zona come specchio acqueo da 
classificare nel e' disponibile una nuova funzione 'Rifiuta 
Classificazione'. Al click sul relativo bottone si apre una popup con i 
seguenti campi obbligatori:
	a.	assenza di concessione
	b.	esiti esami sfavorevoli
	c.	sorveglianza sanitaria non corretta
	d.	parere sfavorevole dell'ARPAC
	</li>   
</ul>
<br>
<img src="images/add3.png" name="add3.png" height="367" width="637" align="LEFT" border="0"><br clear="LEFT">
<br> 
Ogni ASL deve poter prendere visione delle concessioni scadute di propria competenza.<br>
Ogni zona di produzione di tipo "Zone in concessione/impianti
molluschicoltura" puavere associato una lista di concessionari.
L'operatore regione l'unico a poter associare concessionari ad una
zona, e lo pufare mediante il link "Aggiungi Concessionario" presente
in alto a destra nella scheda di dettaglio della Zona in
concessione/impianti molluschicoltura, cliccando sul tale link comparirà
la lista di tutti i concessionari presenti nel sistema, per cui 
possibile sceglierne uno tra quelli già presenti.
<br>
<br>
<img src="images/add4.png" name="add4.png" height="367" width="637" align="LEFT" border="0"><br clear="LEFT">
<br> Cliccando sul link "Inserisci" comparirà una finestra in cui
selezionare 
<ul>
	<li>numero concessione</li>
	<li>data concessione</li>
	<li>data scadenza concessione</li>
</ul>
Dopo aver inserito queste informazioni cliccando sul pulsante inserisci
si chiuderà la finestra e verrà ricaricata la pagina di dettaglio della
Zona in concessione/impianto molluschicoltura, visualizzando nella
tabella il concessionario che si deciso di associare. Il campo 'stato' contiene lo stato della concessione.  E' possibile
visualizzare la lista di tutte le concessioni che sono scadute o in
scadenza (cioe' 3 mesi prima della data di scadenza) , mediante il link
"Concessioni in scadenza" presente nel sottomenu in alto del modulo
molluschibivalvi.<br> Le concessioni in scadenzza possono essere
viste esclusivamente dall'utente regione. 
<br>
<br>
<img src="images/add5.png" name="add5.png" height="240" width="690" align="LEFT" border="0"><br clear="LEFT">
<br> Mediante il link "Aggiungi Concessionario" (presente nel
sottomenin alto del modulo molluschi bivalvi).
<br> Per inserire nel sistema nuovi concessionari, bisogna
compilare una scheda, in cui devono essere inseriti i seguenti dati: 
<ul>
	<li>asl</li>
	<li>ragione sociale</li>
	<li>cf impresa</li>
	<li>legale rappresentante</li>
	<li>comune</li>
	<li>indirizzo</li>
	<li>cap</li>
	<li>provincia</li>
	<li>latitudine</li>
	<li>longitudine</li>
</ul>
<br>
<img src="images/add6.png" name="add6.png" height="367" width="637" align="LEFT" border="0"><br clear="LEFT">
<br>
<br> Nella scheda di dettaglio di un concessionario, già presente
nel sistema, possibile visualizzare la lista di tutte le zone su cui
ha una concessione. 

<br>
<br>
<img src="images/add7.png" name="add7.png" height="367" width="637" align="LEFT" border="0"><br clear="LEFT">
<br> 
Per le zone di produzione sono gestiti: 
<ul>
	<li>Documenti</li>
	<li>Controlli Ufficiali</li>
</ul>
<br>
<h2>Controlli Ufficiali per Molluschi Bivalvi</h2>
La gestione dei controlli ufficiali e' un'estensione di quella presente 
negli altri cavalieri. Infatti oltre ai campi normalmente 
gestiti ve ne sono alcuni aggiuntivi.  Il controllo ufficiale puo' 
essere applicato alle zone di produzione (banchi naturali e zone in 
concessione). 
Tra le tecniche del controllo, oltre a quella gia' gestite (AUDIT, 
ispezione semplice) viene gestito il seguente elenco: 
<ul>
	<li>Divieto di pesca</li>
	<li>Mancanza imbarcazione</li>
	<li>Condizioni meteo/marine avverse</li>
	<li>Prodotti di taglia non commerciale</li>
	<li>Altro con campo note</li>
</ul><br>

In caso di controlli di tipo "Ispezione Semplice" viene gestito un nuovo
 campo non obbligatorio "Verifica quantitativo prodotto raccolto" 
composto da:
<ul>
	<li>una lista a selezione singola contenente le voci: "I semestre" e "II semestre"</li>
	<li>un campo "quintali" di tipo numerico</li>
</ul>
<br>
<h2>Storia Classificazioni</h2>

Questa funzionalita' (accessibile dal menu' verticale 'Storia 
Classificazioni') mostra l'elenco dei provvedimenti a cui la zona in 
concessione e' stata assoggettata dal piu' recente al meno recente. 
Nella pagina sono presenti due tabelline:
<ul>
<li>la prima contenente le informazioni relative ai decreti ovvero 
Numero Decreto|Data Classificazione|Data Fine Classificazione 
|Classe|Tipo Zona |Specie Molluschi;</li>
<li>la seconda contenente le informazioni relative ai provvedimenti 
ovvero Data Inizio Provvedimento|Data Fine Provvedimento|Provvedimento.</li>
</ul>

<div class="fine" style="height: 50px;">&nbsp;</div>


<a name="farmacosorveglianza_home"></a>



<h1>Operatori 193</h1>
<br> Il modulo "Operatori 193" strutturato come il modulo OSM. La
prima maschera visualizzata quella di scelta del sottomodulo a cui
accedere. Quindi presenterà due bottoni: 
<ul>
	<li>"Farmacie/Grossisti"</li>
	<li>"Parafarmacie/Farmacie"</li>
</ul>
In entrambi i sottomoduli sono presenti le seguenti funzionalità:
<ul>
	<li>Inserimento/modifica/cancellazione dati anagrafici;</li>
	<li>Gestione controlli ufficiali</li>
	<li>Gestione documenti</li>
</ul>
<br>
<br>
<h2>Gestione Allegato I</h2> <br>
<p style="text-align: justify">La funzionalità per la gestione
dell'Allegato I (scheda di rilevazione dati attività di
farmacosorveglianza) accessibile dal modulo "Operatori 193" al link
"Allegato I" in alto a sinistra.<br> Tale funzionalità 
attualmente visibile ai seguenti ruoli: 
</p><ul>
	<li>1. Supervisore ASL</li>
	<li>2. Regione</li>
</ul>
<br>
<h2>Supervisore ASL</h2><br> 
Il supervisore di ogni ASL puàï¿½ accedere alla tabella riepilogativa della propria ASL, compilarla (e modificarne
successivamente il contenuto se necessario) ed eventualmente esportarla in formato excel.
<br> Cliccando sul link "Allegato I", l'utente accede alla finestra di 
selezione dell'anno di riferimento e cliccando poi sul bottone
"invia" accede alla scheda da compilare. La scheda compilabile in tutte 
le caselle che la compongono semplicemente cliccandoci sopra e
digitando le cifre corrette (vedi figura sottostante cerchietto rosso).
<br> Il salvataggio delle informazioni inserite avviene "solo" cliccando
 sul pulsante evidenziato con il cerchietto verde di seguito. 
<br>
<img src="images/allegato1.PNG" name="allegato1.PNG" width="500" align="LEFT" border="0"><br clear="LEFT">
<br> Qualora venga digitato un carattere non numerico il sistema fornisce un messaggio di errore. Salvate le informazioni possibile
esportare la scheda cliccando sul link "Stampa Report".
<br>
<img src="images/allegato1_1.PNG" name="allegato1_1.PNG" width="500" align="LEFT" border="0"><br clear="LEFT">
<br>
<h2>Regione</h2><br> Tutti i dati inseriti saranno in automatico
disponibili al nodo regionale di competenza, il quale puaccedere alla
tabella riepilogativa globale (un'unica tabella in cui affluiscono tutti
i dati inseriti dalle AASSLL)in sola consultazione ed eventualmente
esportarla in formato excel.
<br>
<img src="images/allegato1_2.PNG" name="allegato1_2.PNG" width="500" align="LEFT" border="0"><br clear="LEFT">
<br>
<p></p>
<br>
<br>
<h2>Gestione Allegato II</h2>
<p style="text-align: justify">La funzionalità per la gestione
dell'Allegato II (Scheda prescrizione servizio veterinario) 
accessibile dal cavaliere "Farmacosorveglianza" al link "Allegato II" in
alto a sinistra. <br><br>

<img src="images/allegato_ii.bmp" name="allegato_ii.bmp" height="367" width="637" align="LEFT" border="0"> <br clear="left">
<br> Tale funzionalità attualmente visibile ai seguenti ruoli: 
<br>
<br>
</p><ul>
	<li>Dirigenti Servizi Veterinari ASL: Il supervisore di ogni ASL
	puaccedere alla tabella riepilogativa della propria ASL compilarla,
	modificarne il contenuto se necessario, ed eventualmente esportarla in
	formato excel. Cliccando sul link Allegato II, l'utente puaccedere
	alla finestra di selezione dell'anno di riferimento e cliccando poi sul
	pulsante "invia" accede alla scheda da compilare.</li>

	<br><br>
	<img src="images/allegati_ii_anno.bmp" name="allegato_ii_anno.bmp" width="500" align="LEFT" border="0">
	<br clear="LEFT"><br> 
	La scheda compilabile in tutte le sue parti semplicemente
	cliccandoci sopra ed inserendo le informazioni giuste nelle caselle
	giuste, successivamente cliccando sul pulsante di salva, si procede al
	salvataggio delle informazioni inserite. 
	
	<br><br>
	<img src="images/allegati_ii_salva.bmp" name="allegato_ii_anno_salva.bmp" height="367" width="637" align="LEFT" border="0">
	<br clear="LEFT"><br>
	Qualora venga digitato un carattere non numerico il sistema
	fornisce un messaggio di errore. Salvate le informazioni possibile
	esportare la scheda cliccando sul link "Stampa Report". Tutti i dati
	inseriti saranno in automatico disponibili al nodo regionale di
	competenza. 
	<br><br>
	<li>Responsabile Regionale Farmacosorveglianza. Il nodo regionale
	di competenza puaccedere alla tabella riepilogativa globale (un'unica
	tabella in cui affluiscono tutti i dati inseriti dalle AASSLL) in sola
	consultazione ed eventualmente esportarla in formato excel.
	<br><br>
	<img src="images/allegati_ii_regione.bmp" name="allegato_ii_anno_regione.bmp" height="467" width="637" align="LEFT" border="0">
	<br clear="LEFT"><br>
	</li>
</ul>
<p></p>
<div class="fine" style="height: 50px;">&nbsp;</div>

<a name="osm_home">
<h1>OSM</h1>
<br> L'acronimo OSM identifica gli Operatori del Settore dei
Mangimi, il modulo in GISA diviso in: 
<ul>
	<li>OSM RICONOSCIUTI, la cui lista viene periodicamente pubblicata dalla Regione Campania.
	</li><li>OSM REGISTRATI - PRODUTTORI PRIMARI, secondo il R.E. 183/2005 gestiti dalle ASL. 
</li></ul>
Qui sono gestite le anagrafiche, i controlli ed i documenti degli
operatori. <br>
<br>

<div class="fine" style="height: 50px;">&nbsp;</div>

</a><a name="punti_di_sbarco"></a>
<h1>Punti Di Sbarco</h1>
<br>
<p style="text-align: justify">In questo cavaliere sono presenti,
come operatori, i Punti di Sbarco ed i relativi controlli ufficiali.
Questi ultimi sono gestiti in modo leggermente diverso dagli altri
operatori e per questo la guida puessere di aiuto. <br>
<br>
</p><h2>Aggiunta di un Controllo ufficiale presso un Punto di Sbarco</h2>
<br> Dal momento che durante un controllo ufficiale presso un punto
di sbarco potrebbero presentarsi da 0 a n operatori, in fase di
inserimento, sarà possibile selezionare uno o pioperatori tra IAR con
ATECO "03.11.00, 03.12.00, 03.21.00, 03.22.00", imprese mobili ed
abusivi. Quindi nelle GUI del controllo ufficiale verrà gestita una
nuova tabella "Seleziona Operatore" che consentirà di aggiungere gli
operatori associati al punto di sbarco. 
<br>
<br>
<img src="images/lista_operatori.png" name="lista_operatori.png" width="500" align="LEFT" border="0"><br clear="LEFT">
<br> In fase di aggiunta C.U. (controllo ufficiale), possibile
aggiungere un Operatore Abusivo da associare al punto di sbarco: 
<br>
<br>
<img src="images/add_operatore_abusivo.png" name="add_operatore_abusivo.png" width="500" align="LEFT" border="0"><br clear="LEFT">
<br> Una volta selezionati gli operatori associati al controllo,
viene automaticamente riempita la tabella che ne riporta alcuni
dettagli. 
<br>
<br>
<img src="images/add_cu_ps.png" name="add_cu_ps.png" width="500" align="LEFT" border="0"><br clear="LEFT">
<br> Cliccando su "Inserisci", il sistema assegnerà un controllo
ufficiale sia al punto di sbarco che agli operatori eventualmenti
associati al controllo, replicandolo. In fase di modifica chiaramente si
potrà eliminare un eventuale operatore associato, aggiungerne uno nuovo
oppure modificare il controllo "padre", ovvero del punto di sbarco
stesso; se si decide invece, di cancellare un controllo presso un punto
di sbarco, allora verranno cancellati anche i controlli ufficiali legati
allo stesso, in modo trasparente all'utente. 
<p></p>
<br>
<br>

<div class="fine" style="height: 50px;">&nbsp;</div>

<a name="soa"></a>
<h1>SOA</h1>
<br>
<br>
<h2>SOA No Sintesi</h2>
<br>
<br>
<p style="text-align: justify">e' possibile inserire in gisa una
richiesta per un nuovo soa, che dovrà essere completata (mediante
l' inserimento del controllo documentale) e approvata da parte
dell'utente Funzionario Regionale. Tale gestione coinvolge i seguenti ruoli: <br>
<br>
</p><ul>
	<li>Ruolo Veterinari</li>
	<li>Ruolo Funzionario Regionale</li>
	<li>Ruolo ORSA</li>
	<li>Scadenzario Condizionati</li>
	<li>Scadenzario Sospesi</li>
</ul>
<br>
<br>
<h2>RUOLO VETERINARIO</h2>
L'utente Veterinario potrà: <br>
<br>
<ul>
	<li>Aprire una nuova istruttoria</li>
	<li>Controllo documentale</li>
	<li>Richiesta di riconoscimento "Definitivo" o "Condizionato" al Funzionario Regionale</li>
	<li>Aggiunta di Nuove Linee Produttive</li>
</ul>
<br>
<br>
<h2>Apertura di una nuova Istruttoria</h2>
<br> A partire dal modulo stabilimenti l'utente veterinario potrà
fare richiesta di inserimento di un nuovo soa mediante il link "Aggiungi
Istruttoria" presente nel menù in alto. I campi obblgatori da inserire
sono : ragione sociale, partita iva, data presentazione istanza, nome
rappresentante, cognome rappresentante, codice fiscale rappresentante,
sede operativa, e almeno una linea produttiva. Dopo aver compilato i
campi obbligatori la pratica verrà inserita nello stato "Istruttoria
Preliminare" e lo stato del soa sarà in domanda. 
<br>
<br>
<h2>Controllo Documentale</h2>
<br> Dopo aver inserito la richiesta di inserimento per un nuovo
soa , l'utente veterinario dovrà effettuare il controllo sui
documenti.L'inserimento del controllo sui documenti potrà essere
effettuato mediante il link "Controllo Documentale" presente nel menu
laterale della scheda del soa. L'utente veterinario dovrà spuntare i
documenti solo se sono stati controllati e sono in regola,
successivamente verrà eseguito un secondo controllo da parte dell'utente
Funzionario Regionale.Ci sono due modalità di salvataggio:Temporanea e definitivo. Finche'
il salvataggio temporaneo il controllo documentale sarà modificabile;
nel momento in cui diventa definitivo non lo sarà pi Sarà definitivo
solo se tutti i documenti risultano spuntati ovvero solo se l' esito 
favorevole per tutti i documenti. Dopo aver salvato il controllo
documentale in maniera definitiva la pratica passerà nello stato
"Verifica Documentazione Completata". 
<br>
<br>
<h2>Richiesta riconoscimento Definitivo o Condizionato</h2>
<br> Dopo aver completato la verifica sui documenti e passata la
pratica nello stato "Verifica Documentazione Completata" , l'utente
veterinario potrà inviare la richiesta di riconoscimento condizionato
del soa. La richiesta di riconoscimento condizionata fara passare lo
stato della pratica da verifica documentazione in "Richiesta
Riconoscimento Condizionato" , successivamente la pratica dovrà essere
lavorata dal Funzionario Regionale. Una volta passata la pratica al Funzionario Regionale, l'ASL potrà
visionare la scheda soa ma non potrà più operare sulla stessa 
<br>
<br>
<h2>RUOLO Funzionario Regionale</h2>
<br> L'utente Funzionario regionale potrà effettuare: 
<br>
<ul>
	<li>Controllo documentale</li>
	<li>Passaggio pratica al settore veterinario</li>
</ul>
<br>
<br>
<h2>Controllo documentale</h2>
<br> Controllo documentale: durante il controllo documentale il
Funzionario Regionale può richiedere dei chiarimenti all'ASL su alcuni documenti
compilando il campo delle note relativo al documento che manca. Anche
per il Funzionario regionale gestito il doppio salvataggio:temporaneo e definitivo. Il
salvataggio sarà definitivo solo se tutti i documenti risultano
spuntati.Qualora il controllo documentale da parte del Funzionario regionale non sia
andato a buon fine (non sono stati spuntati alcuni documenti) la pratica
ritorna nello stato "Istruttoria Preliminare", e quindi l'utente
veterinario deve lavorarla dinuovo.Se il controllo andato a buon fine
invece il Funzionario Regionale potrà inviare la pratica all'utente del settore
veterinario mediante il pulsante "inoltra pratica alla regione". 
<br>
<br>
<h2>Passaggio pratica al settore veterinario</h2>
<br> Dopo che il controllo documentale stato inserito e non ci
sono stati problemi nella verifica dei documenti ,il Funzionario Regionale potrà
inoltrare la richiesta di inserimento in sintesi all'utente del settore
veterinario.L'inoltro sarà possibile mediante il pulsante "inoltro
richiesta condizionata" se stata fatta richiesta di riconoscimenti
condizionato da parte del veterinario , "inoltra richiesta definitiva"
se invece la richiesta di riconoscimento da parte del veterinario era
definitiva.In entrambi i casi il pulsante visibile nella scheda del
soa. 
<br>
<br>
<h2>RUOLO ORSA</h2>
<br> L'utente ORSA potra' effettuare: 
<br>
<ul>
	<li>Attribuzione numero condizionato</li>
	<li>Attribuzione numero definitivo</li>
	<li>Revoca di un soa</li>
	<li>Modifica Stati Impianti</li>
</ul>
<br>
<br>
<h2>Attribuzione numero condizionato</h2>
<br> Dopo che il Funzionario Regionale ha inoltrato la pratica su richiesta di
riconoscimento condizionato al settore ORSA , essa sarà visibile
anche all'utente sdel settore veterinario. Tramite il pulsante
attribuzio numero condizionato l'utente potrà inserire un numero di
registrazione per il soa che avrà una scadenza di tre mesi dopo la data
di assegnazione.Dopo l'attribuzione del numero condizionato la pratica
passerà nello stato "Riconosciuto Condizionato". Trascorsi i tre mesi
dall'assegnazione del numero , l'utente veterinario potrà chiedere una
proroga di altri tre mesi , o rinviare una richiesta di "riconoscimento
definitiva" che dovrà successivamente essere lavorata da parte del
Funzionario Regionale.Trascorsi i 6 mesi (in caso di proroga) dall'attribuzione del
numero condizionato , potrà essere inviata sempre da parte dell'utente
veterinario , una richiesta di revoca o di riconoscimento definitivo. La
richiesta di revoca (o di riconoscimento definitivo) possibile
mediante il pulsante presente nella scheda del soa. La richiesta di
revoca fa avanzare la pratica nello stato "Richiesta Revoca" 
<br>
<br>
<h2>Attribuzione numero definitivo</h2>
<br> In caso di inoltro pratica da parte del Funzionario Regionale di richiesta di
riconoscimento definitiva , l'utente ORSA attribuirà al soa il
numero di registrazione dopo aver inserito il soa in
sintesi.L'attribuzione del numero di registrazione possibile mediante
il plsante "Attribuisci numer Registrazione", presente nella scheda
del soa e farà avanzare la pratica nello stato "Riconosciuto
Provvisorio". Successivamente mediante il pulsante modifica stato potrà
modificare lo stato del soa in "Autorizzaro" concludendo cosi l'iter
della pratica e facendola passare nello stato finale che "Completato".

<br>
<br>
<h2>Revoca di un soa</h2>
<br> In caso di richiesta di revoca l'utente ORSA potrà
revocare il soa mediante il pulsante revoca presente nella scheda del
soa. Lo stato del soapasserà da in domanda a "Revocato" e lo stato della
pratica avanzerà in "Completato". 
<br>
<br>
<h2>Modifica Stati Impianti</h2>
<br> Una volta completata la pratica , se lo stato dello
stabilimento "AUTORIZZATO" , "REVOCATO" , "SOSPESO" , "RICONOSCIUTO
CONDIZIONATO" , l'utente Funzionario Regionale potrà cambiare lo stato degli impianti
mediante il pulsante modifica stati. Se tutti gli impianti hanno come
stato "REVOCATO" , lo stabilimento sarà "Revocato". Se almeno uno 
autorizzato lo stabilimento avrà come stato "autorizzato", se sono tutti
revocati e uno sospeso , lo stabilimento sarà nello stato "sospeso". 
<br>
<br>
<h2>Scadenzario Condizionati</h2><br> 
A partire dal modulo Stabilimenti (vale anche per i SOA) , 
possibilie visualizzare la lista degli impianti condizionati in
scadenza. la scadenza di un impianto calcolata a partire dalla data di
inizio (o data del decreto) + 3 mesi in caso di riconoscimento
condizionato , 6 mesi in caso di proroga. trascorso questo periodo il
sistema visualizzerà l'impianto tramite il link "Scadenzario
condizionati". Lo stabilimento dovrà essere riconosciuto condizionato
per poter visualizzare gli impianti nello scadenzario. La data di
scadenza dell'intero stabilimento calcolata a partire dalla data piu
piccola degli impianti. 
<br>
<br>
<h2>Scadenzario Sospesi</h2>
<br> A partire dal modulo Stabilimento (vale anche per i SOA) 
possibile visualizzare la lista degli impianti che si trovano nello
stato sospeso e che sono in scadenza. Un impianto scade dopo 12 mesi
dalla data di inizio sospensione. 
<br>
<br>
<p></p>

<div class="fine" style="height: 50px;">&nbsp;</div>

<a name="accounts"></a>

<h1>Stabilimenti registrati 852</h1>
<br>
Il sistema distingue due tipologie di utenti:
<ul>
	<li>gli operatori delle ASL che possono:</li> 
		<ul>
			<li>inserire nuove Imprese Alimentari</li>
			<li>modificare pratiche delle Imprese Alimentari Registrate già esistenti</li>
 	   		<li>ricercare Imprese Alimentari Registrate già anagrafate.</li> 
		</ul>
    <li>i supervisori delle ASL che si occupano di:</li> 
		<ul>
			<li>eliminare Imprese Alimentari Registrate</li>
		</ul>
</ul>
Nota bene: Non e' più possibile inserire  'â‚¬Å“Stabilimenti registrati 
852'â‚¬Â che hanno come codici ATECO quelli relativi alla coltivazione 
primaria 
(da 'â‚¬Å“01.11.10'â‚¬Â a 'â‚¬Å“01.28.00'â‚¬Â)(vedi AZIENDE AGRICOLE)
<br>
Nel modulo sono presenti 4 link:
<ul>
	<li>Aggiungi: apre la finestra di aggiunta nuova Impresa Alimentare;</li>
	<li>Ricerca: apre la finestra di ricerca Impresa Alimentare Registrata;</li>
    <li>Camera commercio: inserendo almeno un filtro si apre la finestra
 di una Impresa Alimentare registrata alla Camera di Commercio che 
consente di poter 
		importare i dati dell'Impresa per evitare di doverla anagrafare 
nuovamente. Cliccando quindi su importa si aprirà la scheda dell'Impresa
 
		Alimentare a cui dovremo inserire almeno tutti i campi obbligatori 
ovvero quelli contrassegnati dall' asterico(*) rosso, dopodichpotremo 
cliccare su 
		inserisci e ritroveremo questa Impresa Alimentare in GISA.</li>
    <li>Reg Antecedenti a GISA: inserendo almeno un filtro si apre la 
finestra di un Impresa Alimentare Registrata che aveva registrazioni 
prima che fosse 
		attivo il GISA</li> 
</ul>
<br><br>

<h2>Ricerca Impresa Alimentare Registata</h2>
<br>
Dalla maschera iniziale, relativa al modulo Imprese alimentari 
Registrate possibile effettuare ricerche generiche, non specificando 
alcun parametro,
e ricerche specifiche, filtrando per uno o piparametri tra quelli 
elencati come ad esempio la Ragione Sociale, il Titolare o Legale 
Rappresentante, 
il Codice Fiscale Impresa, la Partita IVA, il Codice Istat Principale, 
lo Stato Impresa, il Comune, la Categoria di Rischio, il Codice Allerta.<br>
 
Per avviare la ricerca opportuno specificare come parametro di ricerca 
una sottostringa significativa. Ad esempio: se si vuole cercare la 
scheda 
relativa alla ragione sociale "F.lli Fabbrocini fu Antonio S.r.l." basta
 scrivere nel campo ragione sociale la sottostringa "Fabbrocini".<br>
Cliccando sul tasto "Ricerca" verranno restituite tutte le pratiche trovate nel sistema inerenti quel tipo di ricerca. <br>
Cliccando sul tasto "Pulisci" vengono svuotati tutti i campi compilati.
Tra i vari campi visualizzati vi sono:
<ul>
    <li>la ragione sociale</li>
    <li>il numero di registrazione</li>
    <li>la targa autoveicolo o la sede operativa</li>
    <li>la partita Iva</li>
    <li>il codice fiscale</li>
    <li>il codice istat principale</li>
    <li>la categoria di rischio</li>
    <li>il nome dell'utente che ha inserito l'Impresa Alimentare Registrata</li> 
</ul>
Cliccando sulla ragione sociale viene visualizzata la scheda dettagliata
 della singola pratica. Dal dettaglio possibile apportare le eventuali 
modifiche alla 
pratica, stampare la scheda dell'Impresa Alimentare e stampare la 
Comunicazione del Numero Di Registrazione.
Inoltre, possibile associare "Note", "Controlli Ufficiali" , "Documenti"
 e "Volture" alla specifica Impresa Alimentare Registrata mediante il 
menverticale.
<br><br> 

<h2>Inserimento nuova Impresa Registrata 852</h2>
<br>
Si supponga di voler inserire una nuova Impresa Alimentare registrata.<br>
 
Cliccando sul link "Aggiungi" in alto a sinistra, si apre la maschera di
 inserimento. Al fine di un corretto funzionamento del modulo necessario
 inserire almeno tutti i campi obbligatori e cioquelli contrassegnati 
dall'(*) rosso; qualora uno dei campi obbligatori non fosse compilato, 
non sarà possibile l'inserimento dell'Impresa Alimentare Registrata. 
<br>possibile inserire pratiche per tre tipologie di attività:
<ul>
	<li>"Fisse": cioquelle che hanno una sede operativa (es. una macelleria);</li>
    <li>"Mobili" e ciotutte quelle relative a venditori ambulanti ed automezzi (es.camion frigorifero);</li>
    <li>"Distributori", ovvero tutte le imprese erogatrici di distributori automatici.</li> 
</ul>
A seconda del tipo di attività, vengono gestite diverse tipologie di 
indirizzi. Si riporta un esempio di Impresa Alimentare Registrata con 
attività fissa. Tra i vari campi da inserire vi la ragione sociale che 
dovrà contenere il nome dell'operatore proprietario impresa. 
<br>Puessere ditta individuale con nome e cognome o nome società, mentre
 il campo denominazione conterrà il nome di fantasia. Inoltre, almeno 
uno tra partita iva e codice fiscale devono essere specificati.<br> 
Per la partita iva disponibile la funzionalità "verifica preesistenza" 
che consente di verificare se la partita iva che si sta inserendo già 
presente in GISA. Se il campo corrispondente alla partita iva si 
colorerà di rosso vorrà dire che la partita iva già presente, al 
contrario di
si colorerà di verde. Inoltre viene richiesto come  prima informazione 
se l'azienda e' italiana oppure estera e nel caso di impresa straniera 
residente nella CEE, si puàï¿½ inserire  una partita IVA senza il 
controllo degli 11 caratteri;La modifica ha impatto solo per gli 
stabilimenti 852 di tipologia 'â‚¬Å“Fissa'â‚¬Â in riferimento alla sede
 legale e vale sia per imprese 852 che imprese DIA.<br>
Per la compilazione del codice istat possibile selezionare uno dei 
codici ATECO disponibili, attraverso la funzionalità "seleziona", 
tenendo presente che il numero massimo di codici istat secondari pari a 
10.
Qualora nella lista dei codici ATECO non sia presente il codice 
necessario, comunicarlo mediante la funzione "segnalazioni" nella home 
page.<br> 
Il campo "carattere" consente di specificare se l'Impresa Alimentare 
Registrata relativa ad una attività permanente (ad ex. un bar) 
oppure temporanea (ad ex. una sagra). In questo caso, va specificata la 
data di inizio e di fine attività e si avrà cosanche l'opportunità di 
fleggare la casella di Cessazione Automatica dell'attività. Una volta 
inseriti tutti i dati relativi al rappresentante legale, agli indirizzi 
dell'Impresa con i relativi campi longitudine e latitudine la cui 
ricerca coordinate possibile farla dalla maschera che si trova in alto a
 destra della pagina, si puscegliere di:
<ul>
    <li>salvare la nuova pratica, cliccando su "Inserisci"</li>
    <li>annullare l'inserimento, cliccando su Annulla.</li> 
</ul>
Le Imprese Alimentari che si possono inserire direttamente da questa 
maschera, senza dover passare per la DIA, sono tutte quelle che esistono
 già 
sul territorio da anni ma che non sono ancora presenti in GISA, a meno 
di differenti procedure interne. Alle Imprese Alimentari Registrate 
legato il Modulo 
Controlli Ufficiali poichper poter inserire un Controllo Ufficiale 
bisogna accedere attraverso un'Impresa Alimentare. 
 <br><br>


<h2>Allineamento linee di attivittà</h2>
<br> Nella scheda del dettaglio di uno stabilimento 852, qualora 
presenti una o più vecchie linee di attività, ci sarà la possibilità
di aggiornarle nelle nuove linee produttive; infatti cliccando sul 
bottone giallo  "AGGIORNA LINEE DI ATTIVITA’" si aprirà
una maschera dove e' possibile evidenziare due blocchi (2 colonne):
<ul>
    <li>nella colonna di sinistra saranno visualizzate le linee di 
attività presenti per lo stabilimento con dei suggerimenti sottostanti 
(qualora presenti)
 	</li><li>nella colonna di destra, per ogni linea di attività, ci sarà 
la maschera di inserimento di una nuova linea produttiva con delle 
informazioni preselezionate (qualora presenti)
</li></ul>
Cliccando sul bottone giallo "AGGIORNA LINEE", le linee saranno 
aggiornate e scomparirà il bottone "AGGIORNA LINEE DI ATTIVITA’" dalla 
scheda del dettaglio.
<br>
<br>
<img src="images/allineamento_linee_852.bmp" name="add_operatore_abusivo.bmp" width="700" align="LEFT" border="0"><br clear="LEFT">
<br>
NB: l’aggiornamento delle vecchie linee e' obbligatorio solo nel caso 
dell’aggiunta di 
un controllo ufficiale; infatti in presenza di linee non aggiornate, al 
click sul menù laterale "AGGIUNGI UN CONTROLLO UFFICIALE" comparirà a 
video un messaggio di 
avvertimento. Tuttavia, si precisa che per la sola visualizzazione del 
dettaglio, di controlli ufficiali, documenti o altroche', tale 
aggiornamento non e' obbligatorio.
 
<div class="fine" style="height: 50px;">&nbsp;</div>


<a name="stabilimenti"></a>
<h1>Stabilimenti</h1>
<br>
<p style="text-align: justify">E' possibile inserire in GISA una
richiesta per un nuovo stabilimento, che dovrà essere completata
(mediante l'inserimento del controllo documentale) e approvata da parte
dell'utente Funzionario Regionale. Tale gestione coinvolge i seguenti ruoli : <br>
<br>
</p><ul>
	<li>Ruolo Veterinari</li>
	<li>Ruolo Funzionario Regionale</li>
	<li>Ruolo ORSA</li>
</ul>
<br>
<h2>Riconoscimento condizionato:</h2>
<br>
<ul>
	<li>
	<h3>Adempimenti dell'Asl</h3>
	</li>
	<br> Il Servizio Veterinario dell'Asl, acquisita dall'operatore
	l'istanza di riconoscimento dello stabilimento, inserisce in GISA i
	dati relativi allo stabilimento, rispettivamente attraverso l'apposita
	Sezione "Stabilimenti riconosciuti 853". In queste Sezioni,
	l'inserimento dei dati relativi al nuovo stabilimento deve essere
	effettuato dall'A.S.L. attraverso il link "Aggiungi istruttoria", che
	comprende tutti i dati dello stabilimento (ragione sociale, partita
	iva, data presentazione istanza, nome rappresentante, cognome
	rappresentante, codice fiscale rappresentante, sede operativa), ivi
	comprese le attività che l'operatore intende svolgere.
	<br>
	<br> Dopo aver compilato i campi obbligatori, i dati saranno
	salvati cliccando su "inserisci" e la pratica verrà inserita dal
	sistema nello stato "Istruttoria Preliminare" e lo stato dello
	stabilimento sarà "In domanda".
	<br>
	<br> Inseriti e salvati i dati dello stabilimento, l'utente Asl
	inserisce i dati relativi al controllo documentale, effettuato
	regolarmente dalla documentazione allegata all'istanza, accedendo
	all'apposito link "Controllo documentale" nel menù laterale della
	scheda dello stabilimento e, in caso di esito favorevole per ogni
	documento deve essere spuntato il campo appropriato. Un apposito campo
	note consente l'annotazione di qualsiasi notizia o informazione utile
	per ciascun controllo documentale. Ci sono due modalità di salvataggio:
	temporaneo e definitivo. Finchil salvataggio temporaneo il
	controllo documentale sarà modificabile; nel momento in cui diventa
	definitivo non lo sarà pi Sarà definitivo solo se tutti i documenti
	risultano spuntati ovvero solo se l'esito favorevole per tutti i
	documenti. Solo dopo aver ultimato il controllo documentale, ed
	effettuato il sopralluogo presso lo stabilimento con esito favorevole,
	l'Asl salva in maniera definitiva i dati inseriti. Nella ricerca, lo
	stato dello stabilimento resta "In domanda", e lo stato
	dell'istruttoria sarà "Verifica documentazione".
	<br>
	<br> Dopo aver completato la verifica sui documenti e passata la
	pratica nello stato "Verifica Documentazione Completata", l'Asl potrà
	inviare al Funzionario Regionale competente, come previsto dalla presente delibera,
	l'intero fascicolo cartaceo, cliccando poi in GISA su "Richiesta
	riconoscimento condizionato".
	<br>
	<br> La richiesta di riconoscimento condizionato fa avanzare la
	pratica nello stato "Richiesta Riconoscimento Condizionato", e l'Asl
	avrà terminato gli adempimenti di propria competenza, trasferita in
	questo modo al Funzionario Regionale Una volta passata la pratica al Funzionario Regionale, l'Asl
	potrà visionare la scheda stabilimento ma non potrà più operare sulla
	stessa. Lo stato dell'istruttoria sarà "Richiesta riconoscimento
	condizionato". 
	<br>
	<br>
	<li>
	<h3>Adempimenti del Funzionario Regionale</h3>
	</li>
	<br> Il Funzionario Regionale effettua la verifica di propria competenza sulla
	documentazione ricevuta dall'Asl, ed in caso di esito favorevole, per
	ciascun documento spunta il campo appropriato. Un apposito campo note
	consente l'annotazione di qualsiasi notizia o informazione utile per
	ciascun controllo documentale.
	<br>
	<br> Quando il Funzionario Regionale ha ultimato, con esito favorevole, il
	controllo documentale salva in maniera definitiva i dati inseriti e
	visualizzando la scheda dello stabilimento dovrà cliccare su "Inoltra
	pratica al Settore Veterinario", ed in questo modo avrà concluso l'iter
	di propria competenza. Anche per il Funzionario Regionale gestito il doppio
	salvataggio: temporaneo e definitivo. Il salvataggio sarà definitivo
	solo se tutti i documenti risultano spuntati. Nel caso in cui il
	controllo documentale da parte del Funzionario Regionale non sia andato a buon fine
	(non sono stati spuntati alcuni documenti) la pratica ritorna nello
	stato "Istruttoria Preliminare", e quindi l'utente ASL dovrà
	"lavorarla" di nuovo. Se il controllo andato a buon fine invece il
	Funzionario Regionale potrà inviare la pratica all'utente del Settore Veterinario
	mediante il pulsante "Inoltra pratica al Settore Veterinario" 
	<br>
	<br>
	<li>
	<h3>Adempimenti del Settore Veterinario</h3>
	</li>
	<br> Il Settore Veterinario visualizza tutti i dati dello
	stabilimento, ivi compreso il controllo documentale effettuato sia
	dall'Asl che dal Funzionario Regionale, ed attribuisce allo stabilimento il numero di
	riconoscimento, acquisito dal sistema nazionale SINTESI, cliccando su
	"Attribuisci riconoscimento condizionato", per inserire il numero di
	riconoscimento e la data nella quale stato assegnato da SINTESI in
	predetto numero. Il numero condizionato avrà una scadenza di tre mesi
	dopo la data di assegnazione. Oltre l'inserimento del numero
	condizionato , L'utente del settore Veterinario dovrà inserire anche la
	data del decreto per ogni impianto che compone lo stabilimento. Ogni
	impianto avrà quindi una scadenza di tre mesi a partire dalla data del
	decreto (sei in caso di proroga) Trascorsi i tre mesi dall'assegnazione
	del numero, l'utente Asl potrà chiedere per ogni impianto una proroga
	di altri tre mesi, o rinviare una richiesta di "Riconoscimento
	definitiva" che dovrà successivamente essere lavorata da parte del
	Funzionario Regionale. 
</ul>
<br>

<h2>Riconoscimento definitivo:</h2>
<br>
<ul>
	<li>
	<h3>Adempimenti dell'Asl</h3>
	</li>
	<br> Dopo che e stato assegnato il numero condizionato allo
	stabilimento da parte della regione , l'utente asl ha 3 mesi di tempo
	per inviare ina richiesta di riconoscimento definitiva per ogni
	impianto a partire dalla data del decreto. Nel caso in cui trascorrono
	i tre mesi si puo chiedere una proroga tramite il pulsante "Proroga"
	di altri tre mesi per ogni impianto. Nel caso in cui si vuole rendere
	riconosciuto un impianto l'utente asl dovr  cliccare sul pulsante
	"Richiesta riconoscimento Definitivo" , a quel punto la pratica
	passera al Funzionario Regionale. Se invece trascorrono i 6 mesi e l'impianto none
	ritenuto ancora a norma l'utente asl dovra inviare una richiesta di
	revoca che dova essere approvata direttamente dal settore veterinario
	, senza passare per il Funzionario Regionale 
	<br>
	<br>
	<li>
	<h3>Adempimenti del Funzionario Regionale</h3>
	</li>
	<br> Per tutte le richieste di riconoscimento definitivo ,
	l'utente Funzionario Regionale visionera la documentazione e se tutto e ok inoltrera la
	richiesta di riconoscimento definitivo di ogni impianto al settore
	Veterinario mediante il pulsante "inoltra al settore Veterinaio" 
	<br>
	<br>
	<li>
	<h3>Adempimenti del Settore Veterinario</h3>
	</li>
	<br> Il Settore Veterinario visione per ogni stabilimento le
	richieste di riconoscimento definitivo e quelle di revoca. Per gli
	impianti per i quali c'e' una richiesta di riconoscimento definito ,
	l'utente regione cliccando sul pulsante approva , approverà 
	l'impianto in questione , facendo passare lo stato dello stabilimento
	da riconosciuto condizionato a "Autorizzato". se ci sono richieste di
	revoca l'utente del settore veterinario cliccherà sul pulsante REVOCA
	che compare accanto a ogni impianto per cui si   fatta richiesta di
	revoca. Nel caso in cui tutti gli impianto sono stati revocati lo
	stabilimento andà nello stato Revocato 
</ul>
<br>
<br>
<h2>Cambi di ragione Sociale/ legale rappresentante</h2>
<br>
<ul>
	<li>
	<h3>Adempimenti dell'Asl</h3>
	</li>
	<br> Se lo stabilimento nello stato autorizzato o riconosciuto
	condizionato , l'utente asl , potrà fare richiesta di cambio della
	ragione sociale / partita iva e legale rappresentante , mediante il
	link Cambio ragione sociale presente nel menù destro della scheda
	dello stabilimento. Nel caso la prima voltura il sistema generera 2
	registrazioni per mantenere lo storico. Dopo aver inserito la voltura
	l'utente asl dovrà inoltrare la richiesta al Funzionario Regionale mediante il
	pulsante "Invia al Funzionario Regionale" presente nella scheda di dettaglio della
	voltura. 
	<br>
	<br>
	<li>
	<h3>Adempimenti del Funzionario Regionale</h3>
	</li>
	<br> Il FUnzionario Regionale visualizza mediante il link "Cambio ragione sociale"
	la richiesta di volture. Entrando nel dettaglio visualizzare il
	pulsante invia Regione , mediante questo pulsante si potrà scegliere
	se rimandare all'asl la pratica , o farla avanzare inviando la
	richiesta al settore veterinario per l'approvazione. 
	<br>
	<br>
	<li>
	<h3>Adempimenti del Settore Veterinario</h3>
	</li>
	<br> Il settore veterinario visualizzerà tutte le pratiche che il
	Funzionario Regionale ha inoltrato, e tramite il pulsante [approva] potrà approvare la
	voltura riportando i cambiamenti sulla scheda dello stabilimento. 
</ul>
<br>
<br>
<h2>Aggiunta Nuovi Impianti</h2>
<br>
<ul>
	<li>
	<h3>Adempimento dell'ASL</h3>
	</li>
	<br> L'utente veterinario a partire dalla scheda di dettaglio di
	uno stabilimento 853 ( o 1069) potrà aggiungere nuovi impianti
	mediante il link "Aggiungi Linee produttive. Ciccando su tale link si
	aprirà la scheda dello stabilimento con la possibilità di aggiungere
	nuove linee di produzione. Dopo averle aggiunte ciccando su salva , lo
	stato della pratica sarà "in domanda" , e lo stato delle linee
	produttive aggiunte sarà "in domanda". Aggiunte le linee produttive
	l'utente veterinario dovrà eseguire il controllo dei documenti
	mediante il il link presente nel menu a destra "Verifica
	Documentazione". Salvata la verifica dei documenti in maniera
	definitiva , nella scheda dello stabilimento apparirà il pulsante
	"Invia per riconoscimento condizionato". Inviando la richiesta di
	riconoscimento condizionato lo stato della pratica passerà nello stato
	"Invia richiesta Riconoscimento Condizionato" , e dovrà essere
	lavorata dal Funzionario Regionale. Quando l'utente ORSA rende condizionato
	l'impianto (nuovo) , l'utente asl lo vedrà nello stato "Riconosciuto
	condizionato". Per ogni impianto che si trova in questo stato l'utente
	asl potrà chiedere una proroga o una richiesta di riconoscimento
	definitivo. In caso di riconoscimento definitivo, il Funzionario Regionale dovrà
	inoltrare la richiesta al ORSA . I'utente ORSA renderà
	autorizzati i nuovi impianti mediante il pulsante approva. 
	<br>
	<br>
	<li>
	<h3>Adempimento del Funzionario Regionale</h3>
	</li>
	<br> Il Funzionario Regionale dovrà compilare il controllo dei documenti , e
	salvando scheda documentale in maniera definitiva , potrà inoltrare la
	richiesta al settore ORSA 
	<br><br>
	<li>
	<h3>Adempimento del Settore Veterinario</h3>
	</li>
	<br> L ' utente ORSA a partire dal dettaglio dello stabilimento
	visualizzerà il pulsante "attribuisci approval number", ciccando su
	questo pulsante apparirà l'approval number che stato assegnato in
	precedenza (non modificabile) , e la listategli impianti. Per ogni
	nuovo impianto dovrà essere specificata la data del decreto e dopo
	salvare i dati inseriti. Dopo il salvataggio lo stato di ogni nuovo
	impianto sarà riconosciuto condizionato. 
</ul>

<h2>Modifica Stati Stabilimento (Revoca , sospensione ..)</h2>
<br>
<ul>
	<li>
	<h3>Adempimento del settore Veterinario</h3>
	</li>
	<br> La modifica degli stati degli stabilimenti sara a cura del
	settore veterinario (Regione) , che a partire dalla scheda dello
	stabilimento potra cambiare gli stati degli impianti dello
	stabilimento. se almeno un impianto e autorizzato lo stabilimento sara
	sempre autorizzato. se sono tutti revocati e uno sospeso lo
	stabilimento sara sospeso. se sono tutti revocati lo stabilimento saa
	revocato. nel caso di sospensione dello stabilimento sara obbligatorio
	indicare la data di inizio sospensione. la sospensione ha una durata
	massima di 12 mesi e verra segnalata nello scadenzario 10 giorni prima
	che scade. 
	<br>
	<br>
</ul>

<div class="fine" style="height: 50px;">&nbsp;</div>

<a name="riproduzioneanimale"></a>
<h1>Strutture di riproduzione animale</h1>
<br> In GISA e' possibile gestire le anagrafiche delle strutture di
riproduzione animale che si dividono in: 
<ul>
	<li>stazioni di monta naturale pubblica equina</li>
	<li>stazioni di monta naturale privata equina</li>
	<li>stazioni di monta naturale pubblica bovina</li>
	<li>stazioni di inseminazione artificiale equina</li>
	<li>centri di produzione dello sperma</li>
	<li>centri di produzione embrioni</li>
	<li>gruppi di raccolta embrioni</li>
	<li>recapiti di materiale seminale</li>
</ul>
Come in tutti i cavalieri, anche qui possibile effettuare controlli
ufficiali sulle strutture. In aggiunta, vi la funzionalità di export
in formato xls di tutte quelle presenti nel sistema. <br>
<br>

<div class="fine" style="height: 50px;">&nbsp;</div>

<a name="trasportoanimali">
<h1>Trasporto Animali</h1>
<h2>Gestione trasporto animali vivi</h2>
<br>
Dalla pagina iniziale possibile scaricare i moduli di richiesta e autodichiarazione nei due formati WORD e PDF.<br>
I moduli presenti sono:
<ul>
    <li>Autorizzazione al trasporto tipo 1 (Allegato A1)</li>
    <li>Autorizzazione al trasporto tipo 2 (Allegato A2)</li>
    <li>Autodichiarazione della registrazione come produttori primari (Allegato G)</li>
    <li>Autodichiarazione della registrazione come trasportatore 'conto proprio' equidi (Allegato H)</li>
    <li>Istanza di omologazione (Allegato E)</li>
    <li>Checklist per la certificazione dei requisiti dei mezzi di trasporto per animali vivi per viaggi inferiori a 8 ore (Allegato B)</li> 
</ul>
Nel modulo sono presenti i link  "Aggiungi", per anagrafare un trasporto
 animale, e "Ricerca" per consentire la ricerca di un Trasporto animali 
sia generica, ovvero non specificando alcun tipo di parametro, sia 
specifica, ovvero filtrando tra uno o piparametri tra quelli elencati 
(es. il tipo di richiesta, il comune sede operativa, il codice azienda 
etc.) 
<br>Dopo averlo ricercato, cliccando sulla ragione sociale del 
trasporto, comparirà la scheda dettagliata della singola pratica da cui 
possibile fare diverse operazioni:
<ul>
    <li>modifica: consente di modificare alcuni campi del Trasporto animali</li>
    <li>elimina: consente di eliminare il trasporto animali (consentito eliminare un trasporto solo all'amministratore)</li>
    <li>carica autoveicoli: consente di caricare gli autoveicoli tramite file excel</li>
    <li>carica sedi operative: consente di caricare le sedi operative tramite file excel</li>
    <li>carica personale: consente di caricare il personale tramite file excel</li> 
</ul>
I Servizi Veterinari delle Aziende sanitarie, di competenza per la sede 
legale, potranno, tramite l'applicativo GISA, rilasciare una 
autorizzazione, valida 5 anni dalla data di emissione, con numero di 
autorizzazione cosdi seguito codificato: CE IT TX-NNN-NNNNNN dove: - X 
assume i valori S o L se lautorizzazione rispettivamente di tipo 1 o 
tipo 2; - NNN corrisponde al codice ISTAT della Regione; - NNNNNN un 
progressivo numerico da 000001 a 999999.
<br> Ad esempio (nel caso della Regione Piemonte che ha come codice 
regionale 010): CE IT TS-010-000001 (per viaggi &lt; 8 h o &lt; 12 h in 
ambito nazionale) CE IT TL-010-000001 (per viaggi &gt; 8 h o &gt; 12 h 
in ambito nazionale ). In questo modo i Servizi Veterinari potranno 
gestire mediante il sistema GISA due diversi registri informatizzati 
(Tipo 1 e Tipo 2: ognuno con propria numerazione progressiva) riportanti
 i dati relativi alle autorizzazioni rilasciate. 
<br>I dati contenuti nei registri potranno essere in ogni istante esportati e trasferiti verso altre banche dati.
<br>
<br>

<div class="fine" style="height: 50px;">&nbsp;</div>

</a><a name="allerte_new"></a>

<h1>Allerte</h1>
<br> Il presente modulo e' stato definito per permettere la
gestione in ambito regionale del sistema di allerta, al fine di
garantire la tutela della salute pubblica. Si possono riassumere vari
casi per cui vengono create le allerte adottando le misure piu' opportune: 
<ul>
	<li>segnalazioni in arrivo: allerta originati al di fuori della
	ASL, che riguardano alimenti non prodotti ma distribuiti nel territorio
	di competenza della ASL;</li>
	<li>segnalazioni in arrivo: allerta originati al di fuori della
	ASL, che riguardano alimenti prodotti nel territorio di competenza
	della ASL;</li>
	<li>segnalazioni in partenza dalla ASL: attivazione del sistema di
	allerta per riscontri di "frode tossica o di prodotti alimentari
	nocivi o pericolosi per la salute pubblica";</li>
	<li>segnalazioni in partenza dalla ASL: irregolarita'
	analitiche che, in base ai rilievi epidemiologici e/o alle informazioni
	disponibili, non costituiscono un pericolo per la salute pubblica.</li>
</ul>

Le segnalazioni possono pervenire alla Regione dal Ministero della
Salute, da altre Regioni o da altre ASL. Possono riguardare un prodotto, confezionato da una ditta avente sede
nel territorio di competenza della ASL, oppure che risulta
essere stato distribuito nel territorio della ASL. In particolare, in
GISA e' presente la possibilita' di creare una allerta e, a
seconda delle ASL coinvolte, vengono stabiliti i controlli da effettuare.
<br><br><br>

<br><h3>Il sistema gestisce un flusso di attivazione allerte che puo' essere diviso in flusso seguito dalla Regione e flusso seguito dalla ASL:</h3>
<ul>
<b>La Regione:</b>
<li>Riceve la segnalazione dell'allerta</li>
- Scenario 1: da SIAN o veterinari ASL Regione
- Scenario 2: da un ASL fuori regione
- Scenario 3: dal Ministero
- Scenario 4: dalla Comunita' europea
<li>Il coordinatore regionale attiva l'allerta in GISA. La chiudera' in seguito a riscontro da parte  delle ASL. La chiusura puo' essere "forzata" anche se non sono stati eseguiti i 
controlli richiesti.
<li>In seguito alla chiusura dell'allerta, potra' esser scaricato l'allegato F (modulo di riepilogo notizie relativo a tutte le ASL allertate)
<br> 
<br>
<b>La ASL:</b>
<li>I servizi delle ASL sono allertati per il territorio di competenza o per le imprese che operano sul territorio di
competenza delle ASL
<li>Il supervisore instrada e fa eseguire nell'organizzazione interna i controlli ufficiali pianificati dalla Regione fino al completamento previsto.
<li>chiude la lista di distribuzione per la propria ASL in caso di controlli completati.
<li>La chiusura fa in modo che possa essere scaricato anche l'allegato F (modulo di riepilogo notizie liste di distribuzione della propria ASL)
<li>Il sistema avvisera' il coordinatore regionale della chiusura.<br>
<br>
<h2>Inserimento allerta</h2>
Aprendo la pagina del cavaliere "allerta" sia per l'utente Regione che per l'utente ASL, si visualizza subito l'elenco delle proprie allerte.<br>
<br>Per la <b>Regione</b> si vedranno in alto i tasti: <br>
visualizza - aggiungi - ricerca- storico annuale - visualizza tutte le allerte -
<br><br>
Cliccando sul tasto "Aggiungi" e' possibile inserire una nuova allerta.<br>
<br>
<img src="images/Allerta_1.png" height="250" width="500" align="BOTTOM" border="0"><br clear="LEFT">
I campi che si dovranno compilare sono tra gli altri:<br>
<li>La lista di distribuzione (per ogni allerta deve essere aperta una lista di distribuzione nella quale e' possibile selezionare l'Asl di riferimento e la relativa impresa/stabilimento)</li>
<li>La tipologia di allerte (se in entrata o in uscita)</li>
<li>La data di apertura (come campo obbligatorio)</li>
<li>La notifica e il numero di notifica </li>
<li>La matrice e le analisi (che si inseriscono facendo aprire un menu' ad albero e cliccando sul tasto"+" fino all'ultima fase di inserimento) </li>
<li>Campi relativi all'oggetto della allerta (sono tutti campi obbligatori ) </li>
<li>Completato l'inserimento dei dati possiamo cliccare su "Inserisci" e visualizzare il dettaglio.</li>
<li>La chiusura dell'allerta, fa in modo che possa essere scaricato anche l'allegato F (modulo di riepilogo notizie liste di distribuzione della propria ASL)</li>
<br><br>E' possibile fare una "Ripianificazione" dei CU, assegnando nuovi controlli da effettuare alle varie ASL,
cliccando sul tasto <b>"RIPIANIFICA"</b>.<br>
<img src="images/Allerte_ripianifica.png" height="250" width="500" align="BOTTOM" border="0"><br <img="" src="images/Allerte_ripianifica.png" align="BOTTOM" width="500" height="250" border="0" clear="LEFT"><br clear="LEFT">

<br>
Per la <b>ASL</b> l'utente potra' visualizzare, nell'elenco delle allerte, quelle che la Regione ha aperto per la propria competenza.
<br> Si vedranno in alto i tasti: <br>
visualizza - ricerca<br>
<br>
<img src="images/Allerte_ASL.png" height="250" width="500" align="BOTTOM" border="0"><br <img="" src="images/Allerte_ASL.png" align="BOTTOM" width="500" height="250" border="0" clear="LEFT"><br clear="LEFT">
I campi che si visualizzeranno digitando su una allerta sono quelli compilati dall'utente regione:<br>
<li>La lista di distribuzione (nella quale sono riepilogati i dati realtivi alla impresa da controllare, la data di inizio e chiusura allerte e una o piu' ASL coinvolte)
<br><u>L'utente ASL deve andare nel cavaliere "anagrafica stabilimenti" per ricercare l'impresa segnalata che deve essere controllata tramite il controllo ufficiale di tipo "sistema allarme rapido". </U> > (vedi il cavaliere anagrafica stabilimenti)
<li>La scheda della lista di distribuzione (in questo campo sono segnalati i controlli aperti e che devono essere gestiti dalla ASL. Ogni controllo effettuato viene scalato dal numero di C.U. evidenziati
fino a quando non saranno completati. "SOLO" in questo momento la lista potra' essere chiusa)</li></li>
<br>
<img src="images/Allerte_CU.png" height="250" width="500" align="BOTTOM" border="0"><br <img="" src="images/Allerte_CU.png" align="BOTTOM" width="500" height="250" border="0" clear="LEFT"><br clear="LEFT">
<li>La chiusura dell'allerta, fa in modo che possa essere scaricato anche l'allegato F (modulo di riepilogo notizie liste di distribuzione della propria ASL)<br>
<br><br><br>

<a name="buffer"></a>
<h1>Buffer</h1>
<br>
E' prevista la gestione dei Buffer con le seguenti caratteristiche:
<ul>
	<li>La gestione sarà riservata al personale Orsa;</li>
	<li>Il buffer sarà costituito di una denominazione, un evento 
(creazione, chiusura, ), una data evento, un luogo (uno o picomuni della
 regione), 
		un campo testuale per eventuali annotazioni;</li>
	<li>Tutti gli eventi sono storicizzati;</li>
	<li>Tutti i Buffer che non risultino chiusi, saranno elencati nella lista descritta al punto A.</li>
</ul>
<div class="fine" style="height: 50px;">&nbsp;</div>

<a name="gestione checklist"></a>
<h1>Checklist</h1>
Per inserire un controllo ufficiale con checklist in sorveglianza
basta inserire un controllo ufficiale con tecnica di controllo : "ISPEZIONE CON LA TECNICA DELLA SORVEGLIANZA".<br>
Come mostrato in figura:
<br>
<br>
<img src="images/checklist_cu.png" height="550" width="750" align="BOTTOM" border="0"><br clear="LEFT">
<br>
<br>
Una volta inserito il controllo ufficiale e' possibile selezionare, dal 
dettaglio del controllo, il tipo di checklist che l'utente vuole 
compilare
selezionando "COMPILA CHECKLIST PRINCIPALE".<br>
<img src="images/scelta_tipo_checklist.png" height="550" width="750" align="BOTTOM" border="0"><br clear="LEFT">
<br>

<img src="images/bottone_checklist.png" height="550" width="750" align="BOTTOM" border="0"><br clear="LEFT">
<br>
<br>
A questo punto si aprirà una scheda contenente le domande da rispondere.
Il sistema, in base alla risposta inserita per ogni domanda, genererà un punteggio dal quale dipende 
la relativa "categoria di rischio".
Il punteggio della categoria di rischio varia da 1 a 5.
<br>
<br>
Quindi compilata la checklist e' possibile salvare in maniera temporanea cliccando sul bottone "SALVA TEMPORANEA" con 
la possibilità, quindi,di continuare in un secondo momento la compilazione della checklist.
<br>
<br>
<img src="images/salva_checklist.png" height="550" width="750" align="BOTTOM" border="0"><br clear="LEFT">
<br>
<br>
Il dettaglio del controllo quindi mostrerà ancora la checklist 
modificabile con lo "STATO CHECK LIST" impostato su TEMPORANEA .
Se invece si sceglie di salvare definitivamente non sarà più possibile 
modificare la checklist ma solamente aggiornare la categoria di rischio 
cliccando sul bottone "AGGIORNA CATEGORIA".
<br>
<br>
<img src="images/checklist_aggiorna_categoria.png" height="550" width="750" align="BOTTOM" border="0"><br clear="LEFT">
<br>
<br>
Così facendo verrà assegnata la categoria di rischio al controllo e quindi all'OSA di riferimento.
<br>
<br>
<img src="images/checklist_dettaglio_aggiornato.png" height="550" width="750" align="BOTTOM" border="0"><br clear="LEFT">


<!--<H2>Premessa</H2>
<br>
<P style="text-align: justify">Il meccanismo di checklist offline
&egrave; un operazione trasparente all&rsquo;utente. Non ha pertanto
effetto su quella che &egrave; l&rsquo;attuale gestione delle checklist.
I passi da seguire sono quindi, sostanzialmente gli stessi, tranne
ovviamente quando si vuole reperire la checklist che si stava compilando
in precedenza.</P>
<P style="text-align: justify">Il nuovo sistema di storaggio,
permette inoltre di reperire da un archivio, le checklist pendenti.
Ovvero, tutte quelle checklist che sono state salvate in modalit&agrave;
offline e persistono solamente nel browser dell&rsquo;utente e che (per
un motivo o per un altro) non sono state portate a termine e quindi
necessitano di essere ultimate prima di venire inviate a GISA in via
definitiva.</P>
<br>
<H2>Utilizzo delle Checklist Offline</H2>
<br>
<P style="text-align: justify">Per accedere quindi alla compilazione
di una checklist, la modalit&agrave; &egrave; quella di sempre: <BR>
<br>
<IMG SRC="images/0443625001333545811_checklist%20offline%20manuale%20utente.html_html_m301d4f0d.png" ALIGN=BOTTOM WIDTH=537 HEIGHT=167 BORDER=0><BR CLEAR=LEFT>
<br>
<OL>
	<LI><P STYLE="margin-bottom: 0in">L&rsquo;utente effettua
	l&rsquo;accesso in GISA</P>
	<LI><P STYLE="margin-bottom: 0in">L&rsquo;utente accede ad un controllo
	ufficiale</P>
	<LI><P STYLE="margin-bottom: 0in">L&rsquo;utente sceglie che tipo di
	checklist compilare</P>
	<LI><P STYLE="margin-bottom: 0in">L&rsquo;utente ultima correttamente
	la compilazione della checklist e la sottomette al sistema<BR><BR>
	</P>
</OL>

<P style="text-align: justify">E&rsquo; necessario per&ograve; dare
al browser l&rsquo;autorizzazione a salvare dati offline. Questa
operazione pu&ograve; essere effettuata cliccando sul bottone
&ldquo;permetti&rdquo; in alto a destra nel momento in cui ci si avvia
alla compilazione di qualsiasi checklist, come viene mostrato in figura:
<br>
<BR>
<IMG SRC="images/0443625001333545811_checklist%20offline%20manuale%20utente.html_html_m4db5ee1a.png" ALIGN=BOTTOM WIDTH=637 HEIGHT=267 BORDER=0><br CLEAR=LEFT><BR> 
Questa operazione consentir&agrave; di salvare la checklist in
modalit&agrave; offline nel caso ce ne fosse bisogno o se ne verifichi
l&rsquo;eventualit&agrave;. A supporto delle nuove funzionalit&agrave;
troveremo dei nuovi bottoni, ovvero: &ldquo;SALVA CHECKLIST&rdquo; ci
consentir&agrave; di salvare la checklist in maniera permanente in GISA,
ma nel caso in cui la connessione ad internet venga a mancare, o il
server di GISA si trovi down, lo stesso bottone si occuper&agrave;
automaticamente di salvare la checklist in modalit&agrave; offline.
L&rsquo;utente verr&agrave; avvisato da una finestra e dovr&agrave;
attendere un messaggio di avvenuto salvataggio. 
<BR><br>

<IMG SRC="images/0443625001333545811_checklist%20offline%20manuale%20utente.html_html_m27c6228a.png" ALIGN=BOTTOM WIDTH=537 HEIGHT=167 BORDER=0><BR CLEAR=LEFT><BR> 
Diversamente, il bottone &ldquo;SALVA TEMPORANEO&rdquo;
salver&agrave; , ad ogni pressione, automaticamente la checklist anche
in modalit&agrave; offline, oltre che parzialmente nel database di GISA.

<BR>
<BR>
<IMG SRC="images/0443625001333545811_checklist%20offline%20manuale%20utente.html_html_m1343645e.png" ALIGN=BOTTOM WIDTH=537 HEIGHT=167 BORDER=0><br CLEAR=LEFT><BR> 
Il pulsante &ldquo;CARICA CHECKLIST&rdquo; consente invece di
caricare tutte le risposte assegnate a quella checklist, ovvero tutte
quelle risposte (anche parziali) memorizzate offline fino a quel
momento. Questo significa, che per ogni checklist &egrave; possibile
salvare solo alcune porzioni e reperirle in un qualsivoglia momento. 
<BR>
<br>
<IMG SRC="images/0443625001333545811_checklist%20offline%20manuale%20utente.html_html_m34d49cc9.png" ALIGN=BOTTOM WIDTH=537 HEIGHT=167 BORDER=0><BR CLEAR=LEFT><BR> 
Infine, il bottone &ldquo;VISUALIZZA CHECKLIST&rdquo; pendenti
permette di visualizzare tutte le checklist pendenti, o in attesa di
essere completate. Nella tabella di output, viene mostrato l&rsquo;ID
della checklist, la tipologia e un link (vai alla checklist) che
permette all&rsquo;utente di posizionarsi direttamente su quel controllo
ufficiale, su quella checklist per eventualmente caricarla e quindi
completarla prima di salvarla definitivamente nel database online. 
<BR>
<br>
<IMG SRC="images/0443625001333545811_checklist%20offline%20manuale%20utente.html_html_m50c4b88c.png" ALIGN=BOTTOM WIDTH=537 HEIGHT=167 BORDER=0><BR CLEAR=LEFT><br>
</P>

<H2>Alcune note</H2>
<br>
<P style="text-align: justify">Il meccanismo attuale di
memorizzazione delle checklist offline consente di memorizzare tutte le
tipologie di checklist presenti in GISA, ma una ed una soltanto per ogni
tipologia. Questo significa che, se l&rsquo;utente si sta accingendo a
compilare una checklist sui canili e una sui suidi, il meccanismo di
memorizzazione le conserver&agrave; entrambe, ma non sar&agrave;
possibile compilarne un&rsquo;altra per i canili ne per i suidi. O
meglio: l&rsquo;operazione sar&agrave; possibile, ma l&rsquo;ultima
checklist offline di quel tipo salvata in memoria, verr&agrave;
sovrascritta dalla pi&ugrave; recente. Quindi in definitiva, &egrave;
possibile compilare N checklist, purch&egrave; siano diverse tra loro. I
dati salvati in maniera offline persistono soltanto sulla macchina
fisica (nello specifico il browser) di chi l&rsquo;ha compilata. Non
&egrave; quindi reperibile da altre postazioni o computers, ma &egrave;
legata ad un utilizzo strettamene personale.</P>

<div class="fine" style="height: 50px;">&nbsp;</div>
-->
<a name="dpat"></a>
<h1>DPAT</h1>
<h2>Premessa</h2><br>


Tale gestione prevede la compilazione dei fogli che formano il dpat ( 
Modello 4 - Strumento di calcolo, Modello 2 - Risorse Strumentali , 
Modello 5 - attribuzione delle competenze ) per ogni singola struttura 
complessa.
Cliccando sul cavaliere DPAT, il sistema chiederà di selezionare 
l'anno,l'asl e nel caso in cui l'anno e' 2016 , la struttura 
complessa/dipartimentale per cui si vuole lavorare (Fig. 1).

			<img src="images/scelta_asl.png" name="scelta_asl.png" height="250" width="900" align="LEFT" border="0"> <br clear="left"><br>
(Fig. 1)

Dopo aver scelto quindi la struttura su cui si desidera lavorare, 
cliccando sul pulsante Vai, l'utente potrà accedere al foglio dello 
strumento di calcolo.<br>

					<img src="images/modello4.png" name="modello4.png" height="250" width="900" align="LEFT" border="0"> <br clear="left"><br>

Nel caso in cui lo strumento di calcolo e' stato salvato definitivamente si sbloccano anche il Modello 5 e Modello 2.
(Fig 2).
					<img src="images/modelli.png" name="modelli.png" height="250" width="900" align="LEFT" border="0"> <br clear="left"><br>
(Fig. 2)
Aprendo lo strumento di calcolo (pulsante Modello 4) , il sistema 
presenta la lista delle strutture semplici che afferiscono alla 
struttura complessa precedentemente selezionata.
Le strutture semplici, inizialmente, coincidono con quelle dell'anno 
precedente (2015), oltre le strutture semplici ci sarÃ&nbsp; una 
struttura di tipo Direzione/Sede centrale che rappresenta 
la sede centrale della struttura complessa selezionata in precedenza. 

All'interno dello strumento di calcolo ,e' possibile : 
1. Associare le figure professionali alle strutture 
2. Aggiungere nuove Strutture
3. Assegnare i carichi di lavoro su persone e sulla struttura


Per associare le persone a una singola struttura occorre cliccare sul 
pulsante + presente a lato della descrizione della struttura (Fig. 3).
Dopo aver cliccato sul pulsante +, e' visualizzare la lista di tutte le 
persone presenti in Gisa con la rispettiva qualifica. L'utente quindi 
seleziona una o piu persone da aggiungere alla struttura e cliccando sul
 pulsante Salva il sistema ricarica la pagina con le persone inserite 
dall'utente.


				<img src="images/aggiungi_personale.png" name="aggiungi_personale.png" height="250" width="900" align="LEFT" border="0"> <br clear="left"><br>
(Fig. 3)

Dopo aver aggiunto le persone alla singola struttura, l'utente può  
modificare i carichi di lavoro cliccando sul pulsante Modifica Carichi 
di Lavoro.
A seconda del tipo di qualifica il sistema propone il carico di persona 
annuo minimo nel modo seguente :
<ul>
<li>Medico - Responsabile Struttura Complessa--&gt;368</li>
<li>Medico - Responsabile Struttura Semplice--&gt;368</li>
<li>Guardie zoofile--&gt;0</li>
<li>Medico Veterinario - Responsabile Struttura Complessa--&gt;368</li>
<li>Medico Veterinario - Responsabile Struttura Semplice--&gt;368</li>
<li>Medico - Responsabile Struttura Semplice Dipartimentale--&gt;368</li>
<li>Medico Veterinario - Responsabile Struttura Semplice Dipartimentale--&gt;368</li>
<li>Medico--&gt;368</li>
<li>Medico Veterinario--&gt;368</li>
<li>Altro Funzionario Laureato--&gt;0</li>
<li>MEDICI SPECIALISTI AMBULATORIALI--&gt;0</li>
<li>Medici veterinari specialisti--&gt;0</li>
<li>Infermiere--&gt;0</li>
<li>Amministrativi ASL--&gt;0</li>
<li>T.P.A.L--&gt;390</li>
</ul>
<br>
Dopo aver indicato la percentuale da sottrarre, cliccando sul pulsante 
Salva e Chiudi verrà attribuito il carico sulla persona, e sulla 
struttura.
Sulla struttura il carico viene calcolato eseguendo il 65 % sulla somma 
dei carichi di lavoro sulle persone, mentre l'80 % nel caso in cui la 
struttura complessa e' l'area A.

Dopo aver completato lo strumento di calcolo cliccando sul pulsante 
"Salva e chiudi", verrà bloccato il valore del carico della struttura 
complessa ( preso come somma di tutte le strutture che la compongono) e 
verrà utilizzato come valore di riferimento all'interno del foglio 
Attività (Fig 4).
In questo modo tutte le modifiche successive  non influenzeranno il 
valore iniziale del carico di lavoro della struttura complessa.


			<img src="images/carico_bloccato.png" name="carico_bloccato.png" height="250" width="900" align="LEFT" border="0"> <br clear="left"><br>
(Fig. 4 â€“ carico di lavoro bloccato al valore dello strumento di 
calcolo all'atto del salvataggio)

La compilazione del foglio attività non avviene piu direttamente dal 
sistema Gisa, ma solo tramite l'import del foglio Excel eseguito dalla 
regione

Oltre a bloccare il carico di lavoro sulla struttura complessa, la 
funzione di "Salva e chiudi" propaga le strutture e le persone nei 
controlli ufficiali ( Campo per conto di e nucleo ispettivo).
Dopo aver chiuso lo strumento di calcolo, sarÃ&nbsp; possibile compilare
 il modello 2 â€“ Risorse Strumentali e il modello 6 â€“ attribuzione 
delle competenze, a partire dalla struttura complessa.


<p></p>
<br>
<h2>Accesso al modulo</h2>
<br>
<p style="text-align: justify">Il modulo accessibile dal cavaliere DPAT.
<br>
<img src="images/dpat.png" name="dpat.png" height="200" width="600" align="LEFT" border="0"> <br clear="left">
	
Entrando nel modulo la prima pagina visualizzata e' quella di selezione 
dell'ASL (che per gli utenti delle AASSLL risulta settata) e 
dell'anno di riferimento per il DPAT e struttura di riferimento.
<br><br>
</p>
<br>
<h2>Gestione Modello 4</h2>
<br>
<p style="text-align: justify">Selezionati l'ASL , anno di riferimento e
 struttura per il DPAT, viene visualizzata una pagina contenente il 
pulsante "Modello 4". 
<br>
<img src="images/modello4.png" name="modello4.png" height="120" width="800" align="LEFT" border="0"> <br clear="left"><br>
Al primo accesso al foglio Strumento di calcolo sarà visibile l'insieme 
delle strutture e delle risorse per le strutture già caricate e validate
 nell'anno precedente.<br> 
Sarà possibile effettuare le seguenti operazioni:
</p><ul>
	<li>Nuova Struttura</li>
	<li>Modifica Struttura</li>
	<li>Disabilita Struttra</li>
	<li>Modifica parametri</li>
    <li>Aggiungere nominativo</li>
</ul>
<br>
<br>
<img src="images/sdc.png" name="sdc.png" height="600" width="700" align="LEFT" border="0"><br clear="left"><br>
<br>
<img src="images/sdc_mod.png" name="sdc_mod.png" height="600" width="700" align="LEFT" border="0"><br clear="left"><br>
<br>
L'aggiunta della nuova struttura avviene cliccando sul bottone "Nuova struttura".<br>
Si aprirà una nuova finestra popup che richiede di specificare:
<li>Denominazione struttura: campo di testo libero che rappresenta il nome della struttura</li>
<li>Tipologia di struttura: campo lista a selezione singola mediante il quale specificare la tipologia struttura (es. U.O.S. )</li>
Cliccando sul bottone "Inserisci", si conclude l'inserimento della nuova
 struttura ed essa e' visibile nel DPAT. Cliccando su "Annulla",
si ritorna allo strumento di calcolo. 
<br>
<br>
Cliccando su "modifica struttura" e' possibile anche modificare la 
struttura. Può essere modificata la denominazione della struttura e la 
tipologia.
Naturalmente, il sistema non consente; la modifica di una struttura che 
ha altre strutture dipendenti. In questo ultimo caso, infatti, e' 
mostrato
un messaggio a video.<br>
Per ritornare allo strumento di calcolo, senza salvare le proprie modifiche  basta cliccare sul bottone "Annulla".
<br>
<br>
<img src="images/sdc_mod_1.png" name="sdc_mod_1.png" height="500" width="600" align="LEFT" border="0"><br clear="left"><br>
<br>
e' possibile eliminare una struttura tramite il bottone "Disabilita struttura". E' richiesto di specificare la data
a partire dalla quale la struttura selezionata, non dovrà più comparire nello strumento di calcolo.
<br> 
<br>
<img src="images/sdc_mod_2.png" name="sdc_mod_2.png" height="500" width="600" align="LEFT" border="0"><br clear="left"><br>
<br>
Si possono modificare inoltre, i parametri relativi al carico di lavoro delle risorse all'interno della singola struttura. <br>
Quindi, cliccando sul bottone "Modifica Parametri", si aprirà una finestra popup in cui si potranno variare:
<ul>
  <li>FATTORI CHE INCIDONO SUL CARICO DI LAVORO MINIMO AD PERSONAM: campo di testo libero</li>
  <li>PERCENTUALE DI U.I. DA SOTTRARRE (%): campo di testo libero [valore compreso tra 0 e 100]</li>
  <li>CARICO DI LAVORO EFFETTIVO ANNUALE MINIMO AD PERSONAM IN U.I.: campo calcolato in automatico dal sistema</li>	
</ul>
Gli altri campi risultano non modificabili, direttamente dall'utente. Per quelli,
bisognerà contattare il servizio di Help Desk.<br>
Cliccando sul bottone "Modifica", i dati risulteranno salvati e visibili nello strumento di calcolo.
Per uscire dalla finestra, senza salvare, bisogna cliccare il bottone "Annulla".
<br>
<br>
<img src="images/sdc_mod_2.png" name="sdc_mod_2.png" height="500" width="600" align="LEFT" border="0"><br clear="left"><br>
<br>
Tramite il bottone "+", evidenziato nel cerchio in nero, e' possibile aggiungere un nominativo da associare ad una struttura.
<br>. Si aprirà una finestra popup con una lista di nominativi visualizzati per:
<ul>
  <li>ASL</li>
  <li>RUOLO</li>
  <li>NOME</li>
  <li>COGNOME</li>
  <li>CODICE FISCALE</li>
</ul>
La selezione dei nominativi e' multipla, quindi spuntando più checkbox, si potranno aggiungere più risorse
ad una stessa struttura. In base alla qualifica, viene settato il CARICO DI LAVORO TEORICO ANNUALE MINIMO AD PERSONAM IN U.I.:
<ul>
 <li>368 costante per medico/medico veterinario e le altre tipologie di medici e medici veterinari</li>
 <li>390 costante per TPAL</li>
 <li>0 costante per Amministrativi ASL</li>
 <li>testo libero [valore numerico compreso tra 0 e 368] per Altro funzionario laureato<br><br></li>
</ul>
<br>
<img src="images/sdc_mod_3.png" name="sdc_mod_3.png" height="500" width="600" align="LEFT" border="0"><br clear="left"><br>
<br>
<p style="text-align: justify">Man mano che si inseriscono i nominativi di una struttura il sistema ricalcola in automatico i carichi di lavoro 
per singola persona e per la struttura di appartenenza in U.I.. Qualora ci siano dei fattori che incidono negativamente 
sul carico di lavoro dei singoli nominativi e dell'intera struttura, vanno specificati negli appositi campi di testo e vanno 
indicate le percentuali di U.I. da sottrarre. <br>

</p><h2>Flessibilità dello SDC</h2> 
<br>
Con il DPAT flessibile, l'utente potrà agire sullo strumento di calcolo 
sempre. Quindi non ci sarà un salvataggio definitivo dello
Strumento di calcolo e tutto quello che viene registrato in termini di 
risorse e strutture si rifletterà rispettivamente nella gestione dei 
campi:
<ul>
  <li>Per conto di</li>
  <li>Nucleo ispettivo</li>
</ul>
nell'ambito dei controlli ufficiali. Quindi, se si aggiunge una nuova struttura, nella pratica:
<ul>
  <li>La nuova struttura sarà visibile nello strumento di calcolo;</li>
  <li>La nuova struttura sarà visibile nel foglio di attività</li>
  <li>La nuova struttura sarà visibile nel modello  2</li>
  <li>La nuova struttura, se di tipo semplice, o dipartimentale, sarà visibile nell'Allegato 6</li>
  <li>La nuova struttura sarà visibile nel campo "Per conto di" dei CU</li>
</ul>
<br>
<h2>Gestione Modello 2 - Elenco risorse strumentali</h2>
<br>
<p style="text-align: justify">Modello 2 -  Elenco risorse strumentali e' accessibile dopo aver salvato definitivamente  il modello 4. 
<br>
<img src="images/modelli.png" name="modelli.png" height="250" width="800" align="LEFT" border="0"> <br clear="left"><br>
L'elenco di tutte le U.O. sarà lo stesso inserito nello strumento di calcolo. L'utente dovrà compilare i 
vari campi (sono presenti controlli sulla tipologia di input inserita) e il sistema calcolerà in automatico i totali. 
<br>
<img src="images/dpat_10.png" name="dpat_10.png" height="500" width="500" align="LEFT" border="0"> <br clear="left"><br>

Completata la compilazione, la scheda passa alla modalità definitiva 
cliccando sul bottone Salva Definitivo. Nello stato definitivo il foglio
 non potrà essere modificato ma potrà 
essere consultato.
<br><br>
<img src="images/dpat_11.png" name="dpat_11.png" height="500" width="600" align="LEFT" border="0"> <br clear="left"><br>

</p><h3>Stampa PDF</h3>
<p style="text-align: justify">
Dopo il salvataggio in modalità definitiva l'utente potrà scaricare e 
stampare il Modello 2  Elenco risorse strumentali in formato PDF.
</p>
<br><br>
<h2>Gestione Modello 6  Attribuzione competenze</h2>
<br>
<p style="text-align: justify">Modello 6 -  Attribuzione competenze e' accessibile, dopo aver salvato definitivamente il Modello 4. 
<br>
<img src="images/dpat_tre_bottoni.png" name="dpat_tre_bottoni.png" height="120" width="800" align="LEFT" border="0"> <br clear="left"><br>
</p>
<p style="text-align: justify">
L'utente dovrà semplicemente cliccare nelle varie celle di interesse e il sistema in automatico vi inserirà una X. 
Qualora l'utente si accorge di un errore può annullare l'operazione ricliccando sulla cella spuntata.</p>
<br>
<img src="images/dpat_12.png" name="dpat_12.png" height="120" width="800" align="LEFT" border="0"> <br clear="left"><br>

<h3>Stampa PDF</h3>
<p style="text-align: justify">
Dopo il salvataggio in modalità definitiva l'utente potrà scaricare e stampare il Modello 6 in formato PDF.
</p>
<br>
<p style="text-align: justify"><br>


</p><h2>Gestione Modello 1 - 3 - 4 - 6</h2> 
I modelli 1 - 3 - 4 - 6 sono documentazioni che vengono allegate dal personale ORSA o AASSLL.
In particolare i modelli 1 - 4 - 6 possono essere caricati dagli utenti ORSA mentre il modello 3 dagli utenti AASSLL.<br>
Per allegare un file basta cliccare sul link "Carica Modelli Dpat".
<img src="images/carica20modelli.png" name="carica modelli.png" height="350" width="800" align="LEFT" border="0"> <br clear="left"><br>
A questo punto basta cliccare su "carica file" , selezionare il tipo di 
modello che si vuole caricare e cliccare su sfoglia per scegliere il 
proprio documento .
<img src="images/carica_file.png" name="carica_file.png" height="350" width="800" align="LEFT" border="0"> <br clear="left"><br>
Se andiamo nella home del Dpat come possiamo notare, successivamente al 
caricamento del file, il Modello 1 risulta sbloccato e visualizzabile.
<img src="images/modello20sbloccato.png" name="modello sbloccato.png" height="300" width="700" align="LEFT" border="0"> <br clear="left"><br>









<a name="ConfigurazioneDPAT"></a>
<br>
<h2>Configurazione DPAT</h2>
<p style="text-align: justify">
Facendo accesso con utenza ORSA e' possibile all'interno del cavaliere 
DPAT inserire o modificare i piani e i sottopiani di monitoraggio ed 
inserire le UI per i sottopiani e le sottoattività. 
Entrando nel modulo della pagina nella parte in alto a sinistra della 
schermata e' presente il link 'Configura Piani Dpat'.
<br>
<br>
<img src="images/piani1.png" name="piani1.png" height="200" width="600" align="LEFT" border="0"> <br clear="left"><br>

<br>
<br>
Di seguito una leggenda delle possibili operazioni effettuabili sui vari
 piani di monitoraggio e le relative icone corrispondenti.<br>
<img src="images/piani3.png" name="piani3.png" height="500" width="500" align="LEFT" border="0"> <br clear="left"><br><br>

</p><h2>Aggiungere un piano di monitoraggio</h2>
Per aggiungere un piano di monitoraggio dopo aver individuato la posizione  attraverso l'icona
<br>
<img src="images/piani4.png" name="piani4.png" height="70" width="70" align="LEFT" border="0"> <br clear="left"><br>
e' possibile inserire il piano sopra al piano padre individuato mentre utilizzando l'icona 
<br>
<img src="images/piani5.png" name="piani5.png" height="70" width="70" align="LEFT" border="0"> <br clear="left"><br>

si inserisce il piano sotto al piano padre individuato.<br>
In entrambi i casi la maschera di inserimento di un piano padre di monitoraggio e' la stessa.
<br><br>
In seguito all'utilizzo di una delle icone per l'aggiunta di un piano 
padre si apre la finestra per la compilazione del nuovo piano,<br>
<img src="images/piani8.png" name="piani8.png" height="300" width="500" align="LEFT" border="0"> <br clear="left">
all'interno della quale si inserisce la descrizione del  piano, l'alias 
ovvero una breve descrizione presente dopo la riga della sezione nel 
foglio allegato 5 - 
foglio delle attivita, si sceglie nel menu a tendina la sezione che si 
vuole associare al nuovo piano, si inserisce il tipo di attivitàï¿½ tra 
quelle elencate e 
il codice esame che si vuole associare al piano che si sta creando. Dopo
 aver compilato le sezioni descritte cliccando sul bottone INSERISCI si 
inserisce il 
piano di monitoraggio nella posizione richiesta.<br><br>
Per aggiungere un piano di monitoraggio figlio dopo aver individuato la posizione attraverso l'icona
<br>
<img src="images/piani6.png" name="piani6.png" height="70" width="70" align="LEFT" border="0"> <br clear="left">
e' possibile inserire il piano sopra al piano figlio individuato mentre utilizzando l'icona 
<br>
<img src="images/piani7.png" name="piani7.png" height="70" width="70" align="LEFT" border="0"> <br clear="left">
si inserisce il piano sotto al piano figlio individuato.<br>
In entrambi i casi la maschera di inserimento di un piano di monitoraggio e' la stessa.
<br><br>
In seguito all'utilizzo di una delle icone per l'aggiunta di un 
sottopiano si apre la finestra per la compilazione del nuovo piano 
figlio,<br>
<img src="images/piani9.png" name="piani9.png" height="300" width="500" align="LEFT" border="0"> <br clear="left">
all'interno della quale si inserisce la descrizione del piano , l'alias 
ovvero una breve descrizione presente dopo la riga della sezione nel 
foglio allegato 5 - 
foglio delle attivita, che si vuole associare al nuovo piano figlio, si 
inserisce il tipo di attivitàï¿½ tra quelle elencate e il codice esame 
che si vuole associare al 
piano che si sta creando. Dopo aver compilato le sezioni descritte 
cliccando sul bottone INSERISCI si inserisce il piano di monitoraggio 
figlio nella posizione 
richiesta.
<br><br>

<h2>Aggiungere un Sottopiano</h2>
Per aggiungere un sottopiano ad un piano padre che non ha sottopiani figli cliccare sull'icona<br>
<img src="images/piani10.png" name="piani10.png" height="70" width="70" align="LEFT" border="0"> <br clear="left">
Nella finestra che si apre e' possibile inserire la descrizione del nuovo
 sottopiano di monitoraggio ma non e' possibile inserire la sezione in 
quanto essa viene ereditata dal piano padre 
Cliccando su INSERISCI viene salvato il nuovo sottopiano che viene 
inserito nella posizione scelta.
<br>
<br>
<h2>Eliminare un Piano/Sottopiano</h2>
Per eliminare un piano oppure un sottopiano basta cliccare sull'icona <br>
<img src="images/piani11.png" name="piani11.png" height="70" width="70" align="LEFT" border="0"> <br clear="left">
Corrispondete al piano/sottopiano da eliminare. Se l'utente desidera 
eliminare il Piano di monitoraggio comprensivo di tutti i sottopiani 
appartenenti al piano 
padre basta cliccare sul bottone per l'eliminazione corrispondente al 
piano ed in automatico si elimineranno tutti i sottopiani legati al 
piano padre corrispondente.
<br><br>

<h2>Modificare un Piano/Sottopiano</h2>
Per modificare un piano oppure un sottopiano basta cliccare sull'icona <br>
<img src="images/piani12.png" name="piani12.png" height="70" width="70" align="LEFT" border="0"> <br clear="left"><br>
Cliccando sulla matita (Fig. 1) in corrispondenza di un piano padre , si
 aprirà la finestra tramite cui possono essere apportate le modifiche al
 piano.
Per un piano padre il tipo di modifiche previste sono :
<ul>
<li> Modifica attività</li>
<li> Spostamento Attività</li>
</ul>
<br><br>
e' possibile decidere quindi il tipo di operazione che si intende 
eseguire ( di default  e'  Modifica Attività) così come mostrato in 
figura.
<img src="images/modifica_attivita.png" name="modifica_attivita.png" height="350" width="800" align="LEFT" border="0"> <br clear="left"><br>
<br>
Si può modificare la descrizione del piano e il codice univoco.
Cliccando su aggiorna viene disabilitato il piano corrente e inserito il
 nuovo piano , mantenendo la relazione con i figli già esistenti.<br>
Nello spostamento dell'attività oltre a cambiare la descrizione del piano e il codice univoco,viene abilitato il campo sezione.
L'utente può scegliere la sezione in cui spostare il piano e la posizione all'interno della sezione.
Cliccando su aggiorna viene disabilitato il piano corrente e creato il piano nella posizione scelta dall'utente.<br>
<img src="images/sposta_attivita.png" name="sposta_attivita.png" height="350" width="800" align="LEFT" border="0"> <br clear="left"><br>
Allo stesso modo avvengono le modifiche sugli indicatori.<br>
Cliccando sulla matita si aprirà la finestra tramite la quale e' possibile apportare le modifiche agli indicatori.
<br>

I tipi di modifiche previsti per gli indicatori sono:
<ul>
<li>Modifica Indicatore</li>
<li>Sostituzione Indicatore con nuovo</li>
<li>Spostamento Indicatore</li>
</ul>
<img src="images/modifica_indicatore.png" name="modifica_indicatore.png" height="350" width="800" align="LEFT" border="0"> <br clear="left"><br>


Modifica Indicatore consente di variare la descrizione dell'indicatore 
mantenendo la storia con le precedenti versioni dello stesso.<br>

Sostituzione indicatore consente di variare la descrizione 
dell'indicatore , disabilitando quello corrente, azzerando la storia con
 il precedente.<br>


Spostamento indicatore consente di spostare l'indicatore da un piano/attivita a un altro, mantenendo la storia con il pregresso.
L'utente sceglierà in quale sezione e quale piano vuole spostare l'indicatore,  e in quale posizione inserirlo.







<h3>Importante:</h3>
Le modifiche ai piani e agli indicatori del DPAT, possono essere sempre 
fatte, sia nel caso in cui il DPAT risulta CONGELATO (salvato in maniera
 definitiva),
 sia se ancora risulta salvato in maniera temporanea, però c'ï¿½ da dire
 che si verificano due situazioni diverse, ovvero:<br><br>
CASO 1 - Se il DPAT non e' salvato definitivamente, le modifiche 
apportate ai piani e agli indicatori, a seconda della data inizio 
validità 
della modifica fatta, devono riflettersi nel Foglio delle Attività, 
nell'Allegato 6 e nella lista dei Piani, presenti nel Controllo 
Ufficiale.<br><br>

CASO 2 - Se il DPAT e' salvato definitivamente, le modifiche apportate ai
 piani e agli indicatori, a seconda della data inizio validità 
della modifica fatta, NON devono riflettersi nel Foglio delle Attività e
 nell'Allegato 6 ma devono comparire nella lista dei Piani, presenti nel
 Controllo Ufficiale.



<h2>Gestione Carichi di lavoro</h2>
<br>
Selezionata l'ASL e l'anno di riferimento per il configuratore dello 
strumento di calcolo viene visualizzata una pagina contenente l'elenco 
delle strutture e 
dei medici ad esse associati per l'ASL selezionata.
<br>

<img src="images/2015_2.png" name="2015_2.png" height="100" width="500" align="LEFT" border="0"> <br clear="left"><br>
	 
Al primo accesso al foglio Strumento di calcolo sarà visibile l'elenco delle strutture inserite nell'anno precedente. 
Per l'inserimento delle varie strutture occorre cliccare sul bottone Nuova Struttura. 
<br>
<img src="images/2015_3.png" name="2015_3.png" height="100" width="500" align="LEFT" border="0"> <br clear="left"><br>

Al click si aprirà una maschera per l'inserimento delle informazioni relative alla singola struttura :<br>
<li>Denominazione struttura  campo di testo libero in cui specificare il nome della struttura</li><br>
<li>Tipologia di struttura  campo lista a selezione singola mediante il quale specificare la tipologia struttura (es. U.O.S. )</li>
<br>
<img src="images/2015_4.png" name="2015_4.png" height="100" width="500" align="LEFT" border="0"> <br clear="left"><br>
Solo per tipologia struttura 'Struttura Semplice' si attiva un menu con 
l'elenco di tutte le strutture complesse inserite nel'ASL di 
riferimento.
<br>
<img src="images/2015_5.png" name="2015_5.png" height="100" width="500" align="LEFT" border="0"> <br clear="left"><br>

Una volta inserite le informazioni della struttura basterà cliccare sul 
bottone Inserisci (si chiuderà la finestra di inserimento dati) e lo 
strumento di calcolo verrà aggiornato. Qualora invece si voglia 
annullare l'operazione basterà cliccare sul pulsante Annulla.
<br>
<img src="images/2015_6.png" name="2015_6" height="80" width="600" align="LEFT" border="0"> <br clear="left"><br>

Una struttura inserita potrà essere modificata o eliminata cliccando 
sugli appositi bottoni. 
Inserita la struttura possibile, mediante il bottone +, aggiungere i 
nominativi appartenenti alla stessa. Le informazioni da inserire per 
ciascun nominativo 
sono quelle riportate nella figura seguente:
<br>
<img src="images/2015_7.png" name="2015_7.png" height="100" width="500" align="LEFT" border="0"> <br clear="left"><br>
Dovranno essere compilati i seguenti campi:<br>
<ul>
	<li>NOME E COGNOME  campi di testo libero in cui specificare il nominativo<br><br></li>
	<li>CODICE FISCALE<br><br></li>
	<li>MAIL<br><br></li>
	<li>TELEFONO<br><br></li>
	<li>QUALIFICA  lista a selezione singola<br></li>
		<ul>o medico<br><br></ul>
		<ul>o medico responsabile struttura semplice<br><br></ul>
		<ul>o medico responsabile struttura complessa<br><br></ul>
		<ul>o medico responsabile struttura semplice dipartimentale<br><br></ul>
		<ul>o medico veterinario<br><br></ul>
		<ul>o medico veterinario  responsabile struttura semplice<br><br></ul>
		<ul>o medico veterinario  responsabile struttura complessa<br><br></ul>
		<ul>o medico veterinario  responsabile struttura semplice dipartimentale<br><br></ul>
		<ul>o TPAL<br><br></ul>
		<ul>o amministrativo<br><br></ul>
		<ul>o altro funzionario laureato<br><br></ul>
</ul>
Un nominativo inserito potrà essere modificato o eliminato cliccando sugli appositi bottoni. 
<br>
<img src="images/2015_8.png" name="2015_8" height="100" width="600" align="LEFT" border="0"> <br clear="left"><br><br>
<p></p>
<div class="fine" style="height: 50px;">&nbsp;</div>	

<a name="controlli"></a>
<h1>Gestione Controlli Ufficiali</h1>
<br>
<p align="justify">L'accesso alla gestione dei controlli ufficiali in 
GISA non e' diretto, ma e' legato all'entità a cui il controllo deve
essere associato. Infatti se si vuole, ad esempio, aggiungere un nuovo 
controllo all'impresa "Mario Rossi s.a.s" o si vuole prendere visione
di tutti i controlli ufficiali già avuti dalla stessa impresa, occorre 
anzitutto accedere al modulo imprese e fare una ricerca dell'impresa.
Una volta effettuato l'accesso alla scheda del dettaglio impresa, 
occorre cliccare sulla voce "Controlli Ufficiali" dal menù in visione 
verticale. In questo 
modo si accede alla gestione dei controlli ufficiali relativi 
all'impresa "Mario Rossi s.a.s".<br> La prima pagina che vediamo e' 
quella contenente 
la lista dei controlli già effettuati sull'impresa; nel caso in cui, non
 risultano ancora inseriti in GISA controlli ufficiali per l'impresa in 
oggetto, 
il sistema ci informa che non e' stato trovato alcun controllo. 
<br><br>
<img src="images/controlli_ufficiali.png" name="controlli_ufficiali.png" height="367" width="637" align="LEFT" border="0"><br clear="LEFT"><br><br>
</p><h2>Aggiunta di un Controllo ufficiale</h2>
<p align="justify"><br>
<br> L'inserimento di un nuovo controllo ufficiale avviene cliccando sul
 link "Aggiungi Nuovo Controllo Ufficiale". Tra i dati da
inserire per ogni nuovo controllo ce ne sono alcuni (quelli 
contrassegnati da *) che vanno necessariamente compilati per poter 
salvare il controllo nel sistema.
<br><br>
</p><p style="border: solid;"><b>N.B.: ATTENDERE IL CARICAMENTO COMPLETO
 DELLA PAGINA, PRIMA DI PROVARE AD EDITARE O SALVARE I CONTENUTI
DEL CONTROLLO UFFICIALE. TALVOLTA, INFATTI, CAPITA DI AVERE LA 
SENSAZIONE CHE LA LISTA RELATIVA AI MOTIVI DELL'ISPEZIONE NON ESCA, IN 
REALTA' BISOGNA 
ATTENDERE IL COMPLETO CARICAMENTO DI TUTTE LE VOCI. ASSICURARSI QUINDI 
CHE LA SCHEDA DEL BROWSER SIA FERMA E NON IN CARICAMENTO.</b></p>
<br><br>
<img src="images/tipi_CU.png" name="tipi_CU.png" height="367" width="637" align="LEFT" border="0"><br clear="LEFT">
<br> I controlli ufficiali si distinguono in tre tipologie: 
<br><br>
<ul>
	<li><u>Audit</u></li>
	Si tratta degli audit eseguiti dalle AASSLL sulle imprese. Tale
	tipologia di controllo non e' prevista per le imprese 852 mobili ovvero
	i trasportatori reg. 852 e' possibile effettuare una selezione
	multipla per l'oggetto dell'audit; inoltre sia per l'oggetto BPI e
	HACCP e' obbligatorio aggiungere una o più voci da legare a detto controllo.
	<br><br>
	<li><u>Audit di Follow Up (valido per Autorità Competenti)</u></li>
	Tale tipologia di controllo e' inseribile ESCLUSIVAMENTE nel cavaliere 
delle Autorità Competenti.
	La gestione e' la stessa dei controlli di tipo Audit, l'utente può 
scegliere tra "ORSA" o "INTERNO".
	Anche per AUDIT di Follow Up e' possibile effettuare una selezione
	multipla dell'oggetto dell'audit stesso.
	Anche per questo tipo di controllo, e' contemplato l'inserimento delle 
Osservazioni/Raccomandazioni, che deve essere 
	la stessa delle non conformità, inoltre e' possibile inserirne N in ogni
 controllo.
	In fase di inserimento/modifica di Osservazioni/Raccomandazioni, oltre 
al normale funzionamento riguardo alla selezione 
	multipla dell'oggetto dell'audit e delle osservazioni, e' stata gestita 
una sezione "Dettagli Risoluzione", 
	in cui sono presenti i campi: "Data Risoluzione" che e' un campo data, 
"Risolto" che e' SI-NO, "Descrizione" che e' un campo di testo, tutti non 
obbligatori.
	Questa sezione "Dettagli Risoluzione" e' comune alle Osservazioni, 
Raccomandazioni Significative e Gravi.
	<br><br>
	<li><u>Ispezione semplice</u></li>
	piani di monitoraggio, reclamo, rilascio certificazioni, ecc Inoltre 
	consentito di selezionare i motivi dell'ispezione e, a seconda della
	scelta, viene visualizzata la sottosezione di riferimento, rispettando
	i vincoli di obbligatorietà gestiti opportunamente a livello software
	tramite messaggi prima del salvataggio.
	<br><br>
	Per i controlli in PIANO DI MONITORAGGIO occorre
	specificare, per ogni piano selezionato, la struttura a cui il
	controllo per quel piano deve essere attribuito. In questo modo il
	controllo in oggetto viene scalato dal totale dei controlli
	programmati dall'ASL per le proprie strutture di livello inferiore. 
	<br><br> 
	Per i controlli ufficiali con motivo di ispezione "ATTIVITA B46: 
SOSPETTO DI PRESENZA N.C.--&gt;ATT B46" e' prevista la caratterizzazione:
	<ul>
		<li>Per Emergenza Ambientali</li>
		<li>Per Altro Motivo</li>
	</ul>
	<img src="images/carat_d15.png" name="carat_d15.png" width="900px">
	<br><br>
	Nel caso si seleziona dall'apposita combo "Per emergenza ambientale", sarà visualizzata la lista dei Buffer 
	da cui ne sarà selezionato uno da associare al CU;
	<img src="images/buffer_emergenza.png" name="buffer_emergenza.png" width="900px">
	altrimenti, selezionando "Per altro motivo", non occorre
	specificare alcuna informazione aggiuntiva.		
	<br><br>
	<li><u>Ispezione con la tecnica di Sorveglianza</u></li>
	si tratta dei controlli di categorizzazione degli OSA. A ciascun
	controllo ufficiale di questo tipo deve essere associata almeno una
	checklist. Una volta compilata la checklist e inseriti tutti i dati
	sul controllo ufficiale e' possibile aggiornare la categoria di
	rischio. Per questa tipologia di controllo non e' previsto un punteggio
	per le non conformità visto che contenuto nelle checklist. Oltre al
	tipo di controllo e' obbligatorio specificare anche la data inizio
	controllo sulla quale esistono i seguenti vincoli:
	<br><br> 
	1. non e' possibile inserire nuovi controlli con data successiva alla data corrente <br> 
	2. non e' possibile inserire controlli con data antecedente di più di un mese dalla data corrente. <br><br>
 
	Questo vincolo e' valido per tutte le tipologie di controllo tranne 
quelli in piano di monitoraggio per i quali questo vincolo di
	quindici giorni dalla data corrente. Il campo data fine controllo non
	e' un campo obbligatorio ma qualora la data fine controllo non venga
	settata il sistema la setta in automatico uguale a quella di inizio
	controllo; inoltre e' possibile inserire un massimo di dieci componenti
	per il nucleo ispettivo.
</ul>
<br><br> 
Tra i campi obbligatori vi sono anche:
<br><br>
<ul>
	<li><u>Oggetto del Controllo</u>:
	<p align="justify">Tale campo compare e va compilato solo per
	controlli di tipo ispezione semplice.
	</p></li>
	<br>
	<li><u>Linea Attività Sottoposta a Controllo</u>: Tale campo e'
	una lista contenente tutte le linee di attività dell'impresa e tra
	esse occorre selezionare quella a cui il controllo fa riferimento. In
	caso di sorveglianza non esiste il concetto di linea attività
	sottoposta a controllo mentre in caso di audit e' possibile
	selezionare più linee di attività.</li>
</ul>
<br><br>
<img src="images/add_cu_impresa.png" name="lista_operatori.png" height="567" width="637" align="LEFT" border="0">
<br clear="LEFT"><br><br> 
Dal momento che ogni controllo ufficiale e' legato alla linea di attività
 a cui si riferisce, al momento del click sul tasto
"Inserisci" viene mostrato il messaggio che ricorda all'utente che vanno
 aggiunti più controlli ufficiali qualora siano state controllate
più linee di attività per una stessa impresa. Questo vale per tutti i 
tipi di controllo ufficiale tranne le ispezioni in sorveglianza. 
<br><br>

<h2>Gestione Progressivo</h2><br>
<br> Per ciascun documento, viene gestito un progressivo generato dal sistema che unico per ASL (del macello) e si azzera ogni anno.
<br> Sarà quindi nel formato: NUMERO/ASL/ANNO; se il documento viene 
generato più volte, tale progressivo non cambierà ad eccezione
del caso in cui siano state apportate modifiche al documento (es. 
modifica razza/specie di uno dei capi coinvolti, modifica del nome
speditore, ecc): in tal caso il sistema si "accorge" della modifica e 
genera un nuovo progressivo, indicando che il documento attuale
sostituisce il documento con il vecchio progressivo. 
<br><br>
<p style="text-align: justify">
</p><h2>Funzionalità di Dettaglio</h2><br><br>

<p align="justify">Una volta inserito il controllo ufficiale, viene 
visualizzata la scheda di dettaglio dalla quale e' possibile compiere le 
seguenti azioni:<br><br>
</p><ul>
	<li>Aprire il controllo in modifica (cliccando sul bottone "Modifica") qualora ci siano ulteriori campi da compilare o modificare.</li><br>
	<li>Aggiungere al controllo eventuali campioni e/o tamponi prelevati nel corso dello stesso cliccando sugli appositi link.</li><br>
	<li>Aggiungere al controllo eventuali non conformità riscontrate nel 
corso del controllo cliccando sull'apposito link. Dal momento che e'
		possibile cliccare una sola volta sul link "Inserisci non Conformità 
Rilevate", tutte le non conformità riscontrate nel corso
		del controllo vanno descritte in un unico "contenitore" delle non 
conformità</li><br>
	<li>Chiudere il controllo (cliccando sul bottone "chiudi") "se e solo 
se" completo di tutti i dati necessari; questo perche' una volta chiuso 
		non e' più possibile riaprirlo (a meno che non si contatti l'Help 
Desk). Infatti il sistema chiede la conferma visualizzando il seguente 
messaggio. 
		Inoltre, non e' possibile chiudere un controllo se non vengono chiusi 
prima tutti i campioni, tamponi, ecc. ad esso associati, infatti qualora
 si 
		tenti di farlo il sistema non consente di farlo e fornisce un 
messaggio all'utente.</li><br>
	<li>Stampare il Modello 5 - Verbale Ispezione.</li>	
</ul>
Con la Rev. 7 del 31/12/2015 il modello 5 e' stato modificato nelle voci	che lo compongono.<br>
In particolare:
<ul>
	<li>Motivi di ispezione:visualizzabili non più come checkbox ma come 
descrizione per ogni singola motivazione.("Attivitàï¿½"-"Piano di 
monitoraggio")</li>
	<li>Oggetto del controllo:sostituito il termine "Settori" con "Macroaree".</li>
	Per la Macroarea "Benessere animale durante il trasporto" sono state eliminate le voci:<br>
	"Partenza" e relative voci.<br>
	"Destinazione" e relative voci.<br>
	"Certificato sanitario n." e relative voci.
	<li>Non conformità rilevate:sostituito il punteggio della "non conformità grave" da 25 a 50 punti.</li>		
</ul><br>
 
<a name="diffida"></a>	

<h2>Inserimento Diffida/Sanzione</h2><br>
Per inserire una diffida ad un controllo ufficiale basta cliccare su Non
 conformità grave e successivamente su Inserisci diffida.
A questo punto si aprirà una finestra contenente le informazioni da 
inserire.
<br>

<img src="images/inserimento_diffida.png" name="inserimento_diffida.png" width="600" border="0"><br>

<br>
Successivamente,il dettaglio delle norme in diffida inserite e' possibile
 visualizzarlo nella lista delle non conformità. Come mostrato in 
figura:

<br>

<img src="images/dettaglio_diffida_conformita.png" name="dettaglio_diffida_conformita.png" width="600" border="0"> <br>

Oppure nel dettaglio dell'OSA (se le stesse norme non vengono inserite anche per una sanzione):
<img src="images/dettaglio_diffida_operatore.png" name="dettaglio_diffida_operatore.png" width="600" border="0">

<br>

Per inserire una Sanzione,invece , basta cliccare su Sanzione e inserire le informazioni.
<br>
<img src="images/inserimento_sanzione.png" name="inserimento_sanzione.png" width="600" border="0">
<br>
Se vengono inserite norme ,che risultano già precedentemente inserite in
 diffida,un alert avvisa l'utente senza impedirne l'inserimento ma 
eliminando dal dettaglio dell'OSA le norme.<br>
Sarà possibile visualizzare il dettaglio delle sanzioni nella lista delle non conformità.


<h2>Definizione dei vincoli di inserimento e modifica CU</h2><br><br>

In GISA e' nata la necessità di rivedere e quindi ridefinire i vincoli 
per l'inserimento e i controlli ufficiali, introducendo il concetto di 
blocco laddove
sono in corso delle rendicontazioni sui CU per evitare di alterare i 
dati ed introdurre incongruenze. La regola intorno alla quale ruotano 
tutti i vincoli per
l'inserimento e la modifica di un controllo e' la seguente:
<u>"Per l'anno n si possono inserire e modificare i CU dell'anno n fino al 10 Gennaio dell'anno n+1"</u>
<br><br>
<ul type="square">
	<li><b>Vincolo 1: Inserimento di un controllo successivo alla data corrente</b></li><br>
	Tale vincolo indica che non e' possibile inserire controlli ufficiali con data successiva alla data corrente.<br><br>
	<li><b>Vincolo 2: Inserimento/Modifica di due controlli con la stessa "Data inizio controllo"</b> </li><br>
	Quando si vuole inserire un controllo con la stessa Data inizio 
controllo di un altro, il sistema mostrerà un messaggio di questo tipo 
	ATTENZIONE! Risulta che in questo stabilimento e' stato già effettuato 
un controllo in questa data. Sei sicuro di continuare; 
	se l'utente clicca su OK, allora sarà possibile concludere 
l'inserimento, viceversa, se clicca su ANNULLA, no. 
	Lo stesso funzionamento avviene per l'operazione di modifica.<br><br>
	<li><b>Vincolo 3: Inserimento/Modifica di un controllo in presenza di blocchi </b></li><br>
	Per l'anno n sono previsti 4 blocchi trimestrali durante i quali non 
potranno essere fatti inserimenti e modifiche dei controlli. 
	La situazione sarà la seguente:<br><br>
	<ul type="circle">
		<dl>
		<dd><li>[Primo blocco] dal 01/01 al 31/03;</li></dd>
		<dd><li>[Secondo blocco] dal 01/04 al 30/06;</li></dd>
		<dd><li>[Terzo blocco] dal 01/07 al 30/09;</li></dd>
		<dd><li>[Quarto blocco] dal 01/10 al 31/12;</li></dd>
	</dl>
	</ul>
	<br>
	L'attivazione/disattivazione dei blocchi sarà una azione regolata da 
opportuni permessi e sarà disponibile solo al ruolo "ORSA DIREZIONE".
	<br>In particolare e' possibile,cliccando sul cavaliere "Home - "Blocco 
Controlli" - "Aggiungi blocco", limitare la modifica/inserimento di 
Controlli ufficiali.<br>
	Nello specifico selezionando:
	<ul>
		<li>Consentita modifica/inserimento di controlli con data compresa in dal - al<br>
			Si possono inserire o modificare controlli ufficiali con data compresa tra le date selezionate.<br>
		</li><li>Non consentita modifica/inserimento di controlli con data compresa in dal - al<br>
			Non e' possibile inserire o modificare controlli ufficiali con data compresa tra le date selezionate.<br>
			Per entrambe le restrizioni inserite e' possibile motivare il blocco inserendo una nota nel campo Note.<br><br>
	</li></ul>
	<u>Nello stato "blocco":</u><br><br>
	<ol>
		<li>non sarà possibile inserire e modificare i controlli che hanno una
 data che ricade nel blocco di riferimento. 
		(ovvero CU le cui date di inizio controllo ricadono nell'intervallo 
"data blocco-data fine blocco").
		Il sistema in questo caso mostrerà il seguente messaggio: ATTENZIONE! 
		Il controllo in questo momento non può essere inserito/modificato in 
GISA perche' attualmente in vigore 
		un blocco temporaneo per i controlli dal [DATA INIZIO BLOCCO] AL [DATA
 FINE BLOCCO] ai fini della rendicontazione trimestrale."</li>
		<br>
		<li>non dovrà comparire nessun bottone che alteri lo stato di tutti quei controlli la cui "data inizio controllo" ricade nel 
		range del blocco di riferimento. 
		Nel dettaglio dei CU non modificabili, comparirà il messaggio riportato al punto 1.</li>
		<br>
		<li>non dovrà comparire nessun bottone che alteri lo stato delle sottoattività legate al controllo la cui data inizio 
		controllo ricade nel range del blocco di riferimento.</li>
		<br>
		<li>Nel dettaglio dei controlli ufficiali, la cui data inizio controllo rientra nell'anno n-1, comparirà il seguente messaggio: 
		"ATTENZIONE! La modifica ad un controllo dell'anno precedente può avvenire solo tramite richiesta all'Help Desk".</li>
	</ol><br>
	<u>Nello stato di "sblocco"</u> 
	non ci saranno limiti per l'anno n sulle operazioni di inserimento e 
modifica CU, ovvero sarà possibile inserire e modificare i controlli 
ufficiali 
	per tutto l'anno n.
	<br><br>
	<li><b>Vincolo 4: Inserimento/Modifica di un controllo in Sorveglianza</b></li><br>
	Per i controlli di tipo "Sorveglianza" si aggiungono anche i seguenti vincoli:<br><br>
	<ul type="disc">
		<li>Non e' possibile inserire una sorveglianza se ne esiste un'altra aperta per lo stesso stabilimento. 
		In questo caso, il sistema restituirà un messaggio del tipo ATTENZIONE : non e' possibile inserire un 
		nuovo controllo in Sorveglianza. Esistono controlli ufficiali in sorveglianza ancora aperti. 
		Chiudere prima i seguenti controlli: [id_controllo];</li>
		<li>L'inserimento dei controlli in sorveglianza basato sulla categoria
 di rischio (CDR). 
		In particolare, i controlli andranno effettuati secondo questa regola 
con una tolleranza di giorni pari circa al 20% del limite superiore:</li>
		<ul type="circle">
			<li>se la CDR = 5  1 anno</li>
			<li>se la CDR = 4  2 anni</li>
			<li>se la CDR = 3  3 anni</li>
			<li>se la CDR = 2  4 anni</li>
			<li>se la CDR = 1  5 anni</li>
		</ul>
	</ul><br>
	<li><b>Vincolo 5: Campi modificabili in un CU</b></li><br>
	I CU possono essere modificati direttamente dall'utente solo nelle seguenti condizioni:
		<ul><li>stato APERTO</li>
		<li>assenza di campioni</li>
		<li>anno corrente</li></ul>
<br>
In ogni caso non potrà MAI essere modificata la "Tecnica del controllo"
	<br><br>
	<li><b>Vincolo 6: Controlli Pregressi  Anni precedenti</b></li><br>
	Le straordinarie operazioni di modifica CU per gli anni precedenti saranno possibili solo ed esclusivamente all'HD I livello,
	su esplicita richiesta, a partire dal 16 giorno del nuovo anno e naturalmente in assenza di blocchi. 
	(Es. Si conclude l'anno 2014. L'utente ha tempo fino al 15 Gennaio 2015 per recuperare dati su CU dell'anno precedente. 
	Se si va oltre questo limite, bisognerà fare la richiesta all'HD).
</ul><br>



<h2>ATT.D4: 'Attivitàï¿½ di macellazione di suini a domicilio: n.ispezioni'</h2><br>
<p align="justify">
Nell'ambito di un controllo ufficiale, quando si seleziona come tipo di 
Ispezione 'Ispezione Semplice' e motivo dell'ispezione 'ATT.D4: 
ATTIVITA' DI MACELLAZIONE 
DI SUINI A DOMICILIO: N.ISPEZIONI', saranno gestiti due campi aggiuntivi
 non obbligatori, da flaggare :
	</p><ul> 
		<li> 1. Prelievo campione per ricerca trichine;</li>
		<li> 2. Prelievo coagulo per MVS.</li>
	</ul>	
<br><br>
<img src="images/mac1.png" name="mac1.png" height="367" width="637" align="LEFT" border="0"><br clear="LEFT"><br><br>
	
Nel caso in cui l'utente selezioni entrambi i punti di cui sopra, 
verranno automaticamente inseriti due campioni separati, oltre al 
controllo ufficiale.<br><br> 
Per il punto 1, di default il campione avràï¿½ la matrice di Organi e 
Tessuti vari -&gt; Diaframma e analita  di tipo Parassitologico -&gt; 
Parassiti -&gt; Trichinella. <br>
Se invece viene selezionato il punto 2, allora di default la matrice 
saràï¿½; Sangue -&gt; Sangue Coagulato -&gt; Suini e lanalita Virologico
 -&gt; Anticorpi -&gt; Malattia vescicolare suina. <br>
Il campione, per essere inserito necessita anche delle seguenti informazioni:
<ul> 
	<li> Data del prelievo, che in questo caso saràï¿½ valorizzata con la data del controllo ufficiale inserito;</li>
	<li> Laboratorio di destinazione, settato per default diventa IZSM.</li>
	<li> Un numero di verbale che potràï¿½ essere settato dall'utente nel 
dettaglio del campione automaticamente inserito, cliccando sull'apposito
 
		bottone "completa campione"</li>
</ul>
<p></p>
Per concludere correttamente l'inserimento del CU di questo tipo, e' 
necessario completare i dati tramite un nuovo bottone DATI ESTESI PER 
ATT.D4: 
ATTIVITA' DI MACELLAZIONE DI SUINI A DOMICILIO: N.ISPEZIONI. una volta 
cliccato si potranno settare le seguenti informazioni:
<br><br>
<img src="images/dom1.png" name="dom1.png" height="367" width="637" align="LEFT" border="0"><br clear="LEFT"><br><br>
Controllo documentale effettuato:
<ul>
	<li>SI - Viene attivato il campo 'numero capi' nel quale si possono 
scelgliere 1, 2 o 3 ed in base a questa scelta si devono editare i campi
 'DATA ACQUISTO n'
		(ovvero data Mod.4 acquisto) ed 'ALLEVAMENTO ORIGINE n' dove n e' il 
numero dei relativi capi.</li>
	<li>NO - I restanti campi non sono obbligatori</li>
</ul><br>
Concluso l'inserimento il dettaglio del controllo ufficiale si presenteràï¿½ come in figura seguente.
<br><br>
<img src="images/dom2.png" name="dom2.png" height="367" width="637" align="LEFT" border="0"><br clear="LEFT"><br><br>
Naturalmente dopo il salvataggio del controllo ufficiale si potràï¿½ 
tornare nel suddetto in modalitàï¿½ modifica per completare o integrare 
le info mancanti.
<br><br>

<h2>Vincoli sui Controlli Ufficiali in Sorveglianza</h2><br><br>
<p align="justify">Al fine di garantire una corretta gestione della 
categorizzazione del rischio si reso necessario porre dei vincoli sulla 
gestione dei 
controlli ufficiali in sorveglianza. I vincoli sono necessari 
poichall'inserimento della prima checklist viene calcolato e inserito il
 punteggio pregresso. 
Se la situazione non dovesse essere "congelata" (o a causa di controlli 
aperti e quindi modificabili o a causa di nuovi controlli precedenti) si
 darebbe 
origine ad incongruenze sui dati non facilmente gestibili.<br><br> 
Supponendo, ad esempio, di voler attribuire all'impresa "Mario Rossi s.a.s." un nuovo controllo <br><br>
</p><ul>
	<li>se su tale impresa esistono già dei controlli ufficiali non in 
sorveglianza ancora aperti ed antecedenti al controllo in sorveglianza 
		che si sta inserendo non sarà possibile inserire alcuna checklist. 
Fanno eccezione tutti i controlli non in sorveglianza ancora aperti 
		in quanto in attesa di esito di campioni o tamponi e quelli eseguiti 
nell'ultima settimana a partire dalla data del	controllo. Nel caso in 
cui 
		tale vincolo non viene rispettato il sistema fornisce il seguente 
messaggio e non consente di andare avanti.</li><br>
	<li>se il controllo in sorveglianza viene normalmente inserito completo
 delle eventuali checklist, non pipossibile inserire	nuovi controlli 
ufficiali 
		non in sorveglianza con data antecedente al	controllo in oggetto. 
Fanno eccezione tutti i controlli non in sorveglianza eseguiti 
nell'ultima 
		settimana a partire dalla data del controllo. Nel caso in cui tale 
vincolo non viene rispettato il	sistema fornisce il seguente messaggio e
 non 
		consente di andare avanti.</li><br>
	<li>se per l'impresa esiste un controllo ufficiale in sorveglianza 
aperto, non si potrà inserirne un altro fin quando l'ultimo non stato 
chiuso. 
		Nel caso in cui tale vincolo non viene rispettato il sistema fornisce 
il seguente messaggio e non consente di andare avanti.</li><br>
	<li>deve essere evitato l'inserimento di due controlli di sorveglianza 
troppo ravvicinati. Quindi non potrà esser inserito il controllo in 
sorveglianza 
		se ne esiste un altro antecedente rispetto al quale non passato un 
intervallo di tempo minimo, quantificato	in: <intervallo attribuito="" dalla="" categoria="" di="" rischio="" -="" 20%=""></intervallo></li><br>
</ul>
<b>NOTA 1</b>
: se un controllo non in sorveglianza stato già incorporato in un 
calcolo di punteggio precedente viene comunque riutilizzato qualora 
rientri 
nell'intervallo di 5 anni di picontrolli in sorveglianza.<br><br>
<b>NOTA 2</b>
: se un controllo viene tenuto aperto oltre la settimana (a parte quelli
 composti da campioni e/o tamponi) alla login dell'operatore la	cosa 
sarà 
segnalata con un messaggio nella HOME-PAGE.<br><br>
<b>NOTA 3</b>
: i controlli non in sorveglianza aperti (e l'unico motivo valido deve 
essere perchin attesa di esito campione e/o tampone) non vengono	
utilizzati nel 
computo del punteggio fino a quando non vengono chiusi.	<br><br>
<br>

<h2>Gestione evento Blocco/Sblocco Modifica CU</h2>
<br>
Nel sistema possibile modificare alcuni dati del CU per lanno corrente 
purchnon esista un evento di blocco che impedisce tale azione.<br> 
In particolare, potranno essere modificati i tipi e le motivazione dei CU solo nelle seguenti condizioni:
<ul>
	<li>Si possono modificare i controlli se non vi alcun evento di blocco modifica.</li>
    <li>Si possono modificare i controlli che non hanno campioni 
associati: in questo caso il sistema avvisa l'utente con un messaggio 
che ricorda la 
		dipendenza del motivo di ispezione del CU con quella del campione già 
presente e gli impedisce di proseguire.</li>
    <li>Si possono modificare i controlli che hanno data inizio controllo maggiore della data di evento blocco.</li>
</ul>     
La gestione degli eventi di blocco e sblocco della modifica CU regolata 
da un opportuno permesso, attribuito per ora al ruolo di "Responsabile 
Regione".<br><br><br>		

<h2>Gestione non conformità</h2>
<p align="justify"><br> Nel corso di un controllo ufficiale possono 
essere riscontrate delle non conformità formali, significative o gravi 
da cui possono 
scaturire dei follow up ( sanzioni, sequestri, notizie di reato o altri 
follow up). In GISA le non conformità riscontrate nel corso di un 
controllo sono 
accessibili dal dettaglio del controllo cliccando sull'apposito link. <br><br>
<img src="images/nc_rilevate.png" name="nc_rilevate.png" height="100" width="637" align="LEFT" border="0"><br clear="LEFT"><br>

</p><h2>Funzionalità inserimento</h2>
<p align="justify"><br> In GISA l'inserimento delle non conformità avviene cliccando sul link "Inserisci Non Conformità Rilevate" dal dettaglio del 
controllo ufficiale a cui vanno associate.<br><br>
<img src="images/nc_rilevate2.png" name="nc_rilevate2.png" height="267" width="637" align="LEFT" border="0"><br clear="LEFT"><br><br>
 
previsto l'inserimento di almeno un tipo di non conformità: formali, 
significative o gravi e nel caso in cui tale requisito non venga 
rispettato il 
sistema risponderà con il seguente messaggio bloccante.<br><br>

</p><h2>Inserimento di un tipo di non conformità</h2>
<p align="justify"><br> Per inserire una tipologia di non conformità (formali, significative o gravi) basta selezionare il tab corrispondente. 
Per ogni non conformità riscontrata occorre specificare:<br>
</p><ul>
	<li>una descrizione</li>
	<li>il settore a cui la non conformità si riferisce (es.	alimenti per 
il consumo umano). La lista delle voci selezionabili per tale campo 
		corrisponde alla lista delle voci selezionate come "oggetto del 
controllo" nel controllo ufficiale corrispondente.</li>
</ul>
<br>
<img src="images/nc_rilevate3.png" name="nc_rilevate3.png" height="467" width="637" align="LEFT" border="0"><br clear="LEFT"><br><br>
 

Nel caso in cui siano state riscontrate pidi una non conformità, 
l'utente potrà aggiungerne altre cliccando semplicemente sul pulsante 
"Inserisci NC". L'aggiunta di una nuova non conformità sarà possibile 
solo se i campi della precedente sono stati inseriti, altrimenti 
il sistema avverte che non possibile aggiungerne una nuova finchnon sono
 stati completati i campi precedenti. Inoltre ogni volta che viene 
aggiunta 
una non conformità le precedenti non sono pimodificabili. Il numero 
massimo di non conformità che possono essere inserite sono 25 per le 
formali e significative, 15 per le gravi. Qualora l'utente si accorgesse
 di dover eliminare una non conformità lo pufare cliccando sul bottone 
"Elimina NC". Inoltre mediante il tasto "Reset NC" possibile azzerare il
 lavoro svolto eliminando le non conformità e i follow up inseriti, 
riportando lo stato del tab a quello iniziale. Il campo "Valutazione del
 Rischio" (presente in alto) globale per ogni tipologia di non 
conformità 
(non esiste per le gravi). Il punteggio viene incrementato 
automaticamente dal sistema ogni volta che una nuova non conformità 
viene aggiunta.
Per le non conformità formali il punteggio incrementato di 1 punto, per 
le significative di 7 punti per le gravi di 25 punti(controlli ufficiali
 
inseriti con data antecedente al 31/12/2014),o 50 punti(controlli 
ufficiali dell'anno corrente). Di volta in volta varia anche il 
punteggio totale della 
non conformità che viene aggiornato comunque in automatico. <br><br> 
Per completare l'inserimento e abilitare il tasto di inserimento globale
 della non conformità, oppure per aggiungere un'altra tipologia di 
non conformità (formali, significative, gravi), dopo aver aggiunto le 
non conformità di una certa tipologia obbligatorio l'inserimento di 
almeno un follow up corrispondente. Nel caso delle non conformità gravi 
obbligatorio l'inserimento di almeno una delle seguenti attività: 
sanzione, nota di reato o sequestro, mentre per le formali e 
significative altri follow up. <br><br>

<img src="images/follow_up.png" name="follow_up.png" height="467" width="637" align="LEFT" border="0"><br clear="LEFT"><br>
 

E' possibile l'aggiunta dei follow up mediante gli appositi bottoni. 
Cliccando si apre la maschera di inserimento e finche' l'utente non esce 
dall'aggiunta 
del follow up (sanzione, sequestro, notizia di reato) non pueffettuare 
altre operazioni nel sistema. Dopo aver inserito il follow up (sanzione,
 sequestro, 
notizia di reato), la finestra verrà chiusa e l'utente vede apparire il 
follow up inserito nella tabella in basso e pucontinuare o con 
l'inserimento
degli altri tipi di non conformità o con l'inserimento globale. Non sarà
 possibile accedere al dettaglio dei follow up aggiunti se non si 
inserisce 
prima il contenitore globale delle non conformità (cosa possibile solo 
se si clicca sul bottone "Inserisci&gt;"). <br><br>

<b>IMPORTANTE : Si ricorda che per poter spostarsi da una tipologia 
all'altra di non conformità l'utente dovrà aver inserito almeno una non 
conformità e almeno una attività per il tab in cui si trova, oppure non 
aver inserito niente. Solo quando i tre tab sono stati completati 
sarà possibile l'intera non conformità.</b><br><br> 
Alle non conformità aggiunte il sistema attribuisce in automatico un identificativo che le rende univoche in GISA. <br><br>

<h2>Modifica di un tipo di non conformità</h2><br> 
Cliccando sul tasto modifica appare la pagina di modifica
della non conformità con i dati inseriti in fase di inserimento. Per
ogni tipologia di non conformità inserita (formale, significativa e/o
grave) consentita la modifica dei campi descrizione e oggetto delle
non conformità riscontrate, con la possibilità di aggiungerne altre. 
<br>
<br> Le non conformità di una certa tipologia registrate in fase
di inserimento non potranno essere eliminate ma solamente modificate.
Stesso discorso per i follow up (sanzioni, sequestri, ecc. ): i follow
up registrati in fase di inserimento non possono essere cancellati 
possibile solo aggiungerne altri. Il pulsante "Inserisci NC" permette
l'inserimento di una nuova non conformità mentre il pulsante "Elimina
NC" permette di eliminare le non conformità inserite in fase di
modifica e non quelle registrate in fase di inserimento. Il pulsante
"Reset NC" azzera il lavoro svolto in fase di modifica, lasciando
inalterati i dati registrati in fase di inserimento. 
<br>
<br>
<h2>Inserisci Non conformità non a carico del  soggetto ispezionato</h2><br>
Successivamente all'inserimento di un controllo ufficiale e' possibile 
anche inserire una "non conformità non a carico del soggetto 
ispezionato" cliccando sull'apposito bottone.<br>
Come mostrato in figura:<br>
<img src="images/nc_non_a_carico.png" name="nc_non_a_carico.png" height="467" width="637" align="LEFT" border="0"><br clear="LEFT"><br>
<br>
Successivamente si aprirà una scheda nella quale ,cliccando su 
"seleziona da GISA" ,e' possibile ricercare in GISA  l'OSA a cui 
attribuire il provvedimento.
<br>
<img src="images/ricerca_gisa.png" name="ricerca_gisa.png" height="467" width="637" align="LEFT" border="0"><br clear="LEFT"><br>
<br><br>
Come possiamo notare nell'immagine precedente:<br>
e' possibile inserire esclusivamente Non conformità gravi.<br>
In particolare i provvedimenti che si possono inserire sono Sanzione , Sequestro ,Notizie di reato ed     
Altri follow up.<br>
Inoltre per quanto riguarda le "non conformità non a carico del soggetto
 ispezionato" non viene attribuito alcun punteggio come accade invece 
nelle "Non conformità a carico del soggetto ispezionato."<br>
Per quanto riguarda le limitazioni e vincoli valgono le stesse regole 
riportate precedentemente nelle "Non conformità a carico del soggetto 
ispezionato"con l'unica eccezione per quanto riguarda la possibilità di 
inserire più  
"Non Conformità non a carico del soggetto ispezionato" nello stesso 
controllo a differenza  delle "Non conformità a carico del soggetto 
ispezionato" che possono essere inserite solo una volta per ogni 
controllo ufficiale.<br>
<br><br>
<h2>Funzione di inserimento di sanzioni posticipate (fuori tempo massimo) per l'aggiornamento del Registro Trasgressori </h2>
Poich&egrave; esistono dei vincoli temporali sull'inserimento dei controlli ufficiali (attualmente sui primi 15 gg dell'anno successivo),
anche allo scopo di garantire la completezza del Registro Trasgressori si &egrave; reso necessario derogare alla regola consentendo l'inserimento di eventuali sanzioni 
"sfuggite". <br>
<br>
Di seguito i tre scenari in cui &egrave; possibile effettuare l'operazione:<br><br>
<u>Scenario 1</u><br>
Inserimento di una sanzione "fuori tempo massimo" dovuta a un esito non favorevole  di un campione  noto in un istante successivo alla scadenza dei termini di modificabilit&agrave; dei controlli.<br>
<u>Azione</u><br>
Sicuramente &egrave; gi&agrave; presente nella scheda dello stabilimento controllato il CU con relativo campione. Nel dettaglio del controllo sar&agrave; disponibile il link "Aggiungi sanzione posticipata per esito campione non favorevole".<br>
<img src="images/nc_post_nonfavorevole.PNG" name="nc_post_nonfavorevole.PNG" height="467" width="637" align="LEFT" border="0"><br clear="LEFT"><br>
N.B./Possibili criticit&agrave;:
<ul><li>Le sanzioni relative agli anni precedenti verranno messe in coda al registro di quell'anno</li></ul>
<ul><li>Nel registro trasgressori, il campo "data accertamento" &egrave; posto uguale al campo "Data esecuzione attivit&agrave;" della sanzione che a sua volta posta uguale alla "Data inizio controllo" (come in tutti i casi gestiti dal registro trasgressori)</li></ul>
<ul><li>Allo scopo di non alterare l'attuale meccanismo di gestione delle NC, utilizzando la funzionalit&agrave; descritta nello scenario 1, sar&agrave; possibile inserire non solo la sanzione ma pi&ugrave; in generale qualsiasi follow up di qualsiasi NC</li></ul>
<u>Scenario 2</u><br>
Inserimento di una sanzione "fuori tempo massimo" dovuta ad un errata operazione utente che ha  tralasciato  l'inserimento della sanzione entro la scadenza dei termini di modificabilit&agrave; dei controlli.
<u>Azione</u><br>
Sicuramente &egrave; gi&agrave; presente nella scheda dello stabilimento controllato il CU nel quale viene descritta la NC grave. Nel dettaglio del controllo sar&agrave; disponibile il link "Aggiungi sanzione posticipata".
<br><br>
<img src="images/sanzioni_posticipate.PNG" name="sanzioni_posticipate.PNG" height="467" width="637" align="LEFT" border="0"><br clear="LEFT"><br>
<u>Scenario 3</u><br>
Inserimento di un CU "fuori tempo massimo" con una NC e relativa sanzione.<br>
<u>Azione</u><br>
Sar&agrave; possibile inserire il CU fittizio con le seguenti caratteristiche:
<ul><!--INIZIO LISTA-->
	<li>Tecnica del controllo: CU PER NON CONFORMIT&Agrave; POSTICIPATA </li>
	<li>Nucleo ispettivo settato in automatico: 
		<ul>
			<li>qualifica:  SANZIONI POSTICIPATE RUOLO FITTIZIO.</li>
			<li>nominativo: SANZIONI POSTICIPATE UTENTE FITTIZIO</li>
		</ul>	
	</li>
	<li>Oggetto del controllo: specificato dall'utente</li>
	<li>Data inizio controllo: specificata dall'utente (data relativa ad anno precedente)</li>
</ul><!--FINE LISTA-->
<br><br>
Completato l'inserimento del controllo, nel dettaglio sar&agrave; disponibile il link "Inserisci non conformit&agrave; rilevata".<br>
Operazione consentita a tutti gli utenti.
<img src="images/nc_post.PNG" name="nc_post.PNG" height="467" width="637" align="LEFT" border="0"><br clear="LEFT"><br>
<br>
N.B./Possibili criticit&agrave;:<br>
<ul>
	<li>Tutte le sanzioni posticipate saranno presenti nel registro trasgressori se la data del controllo sar&agrave; successiva o uguale alla data di istituzione del registro.(01.03.2015).</li>
	<li>Tutte le sanzioni posticipate e gli eventuali controlli inseriti attraverso queste funzioni saranno identificabili univocamente nella reportistica.</li>
<br><br>
<h2>Gestione stati nei Controlli ufficiali e nuovi vincoli su non conformità e sanzioni</h2>
<p align="justify"><br>
Lo stato del controllo ufficiale può assumere i seguenti valori: </p><ol>
<li>APERTO</li>
<li>CHIUSO</li>
<li>RIAPERTO</li>
</ol>
Appena dopo l'inserimento il controllo si troverà nello stato APERTO e le azioni possibili saranno:
<i> 
<ol>
<li>Chiudi</li>
<li>Modifica</li>
<li>Cancella</li>
</ol>
</i>
In caso di chiusura del controllo ufficiale, le azioni possibili saranno:
<i>
<ol>
<li>Apri di nuovo</li>
</ol>
</i>

In caso di inserimento di una Non conformità con sanzioni (ovviamente in caso di stato non CHIUSO) le azioni possibili saranno:
<i><ol>
<li>Chiudi</li>
<li>Modifica</li>
</ol></i>

Se la non conformità viene chiusa le azioni possibili saranno:
<i><ol>
<li>Apri di nuovo</li>
</ol></i>

L'azione Cancella non risulterà possibile. Lo sarà solo in presenza di N.C senza sanzioni.
<br>
In caso di inserimento di sanzioni, le azioni possibili dipenderanno 
dallo stato del controllo. Se quest'ultimo e' CHIUSO oppure RIAPERTO, non
 sarà possibile modificare, cancellare o riaprire la sanzione. 
<br>
Se lo stato del controllo e' APERTO, invece, sarà possibile fare:
<i><ol>
<li>Chiudi</li>
<li>Modifica</li>
<li>Cancella</li>
</ol></i>


<h2>Funzione di inserimento di sanzioni posticipate (fuori tempo massimo) per l'aggiornamento del Registro Trasgressori</h2> 
<br><br>
Allo scopo di garantire la completezza del Registro Trasgressori e' 
possibile inserire sanzioni relative a CU aggiunti nell'anno 2015. 
<br><br>
Di seguito i due scenari in cui e' possibile effettuare l'operazione:
<br>
<u>Scenario 1</u><br>
La necessite' dell'inserimento di una sanzione "fuori tempo massimo" 
nasce da un procedimento a cavallo tra anni diversi, nel caso di un 
campione il cui esito non favorevole risulta noto in un istante 
successivo alla chiusura del controllo.
<br>
<u>Azione</u><br>
Nel dettaglio del controllo, si attiva il link "Aggiungi Nc posticipate per esito non conforme", visibile all'ASL di competenza.
Le sanzioni 2015 inserite nell'anno corrente verranno messe in coda al registro del 2015.
<br>
<u>Scenario 2</u><br>
La necessità dell'inserimento di una sanzione "fuori tempo massimo" 
nasce da un errata operazione utente che ha inserito, completato e 
chiuso il CU tralasciando l'inserimento della sanzione.
<br>
<u>Azione</u><br>
Nel dettaglio del controllo, si attiva il link "Aggiungi Nc 
posticipata", nel caso in cui non esista alcuna già presente oppure 
"Modifica Nc posticipata", nel caso in cui esiste già una non conformità
 ma senza provvedimenti.
Il permesso per utilizzare queste ultime due funzioni e' concessa 
unicamente agli utenti con ruolo HD.
<br>
<br>


<br>
<br>
<h2>Stampe nei Campioni</h2>
<br>
<br> Nel dettaglio di un campione, oltre ai modelli SIN, per i cui 
dettagli si rimanda alla sezione apposita nel presente manuale, sono 
gestiti 4 modelli HTML: 
<ul>
	<li>Modello 1 per campione di molluschi (solo nel cavaliere "MOLLUSCHI BIVALVI")</li>
	<li>Modello 2 per campione batteriologico</li>
	<li>Modello 3 per campione chimico</li>
	<li>Modello 19 per campione sul PNAA</li>
	<li>Modello 6 per tampone</li>
</ul>
Cliccando sui rispettivi modelli, si aprirà una popup che conterrà in
alto a destra un bottone di "STAMPA", se si vorrà stampare il modulo,
ed una serie di informazioni, recuperate automaticamente dal sistema,
relative ai dati anagrafici dell'OSA presso cui stato fatto il
controllo ufficiale ed il relativo campione, qualche info sul
controllo ufficiale, e alcuni barcode di fondamentale importanza per
la condivisione del campione con SIGLA.
<br>
Di seguito  un esempio della creazione del Modello 1
<img src="images/pag_mod1.png" name="pag_mod1.png" width="500" align="LEFT" border="0">
<br clear="LEFT">
Cliccando su STAMPA MOD.1 VERBALE CAMPIONI MOLLUSCHI il sistema produrràï¿½ il vero e proprio verbale come da esempio seguente.
<img src="images/modello1-1.png" name="modello1-1.png" width="500" align="LEFT" border="0">
<br clear="LEFT">
<img src="images/modello1-2.png" name="modello1-2.png" width="500" align="LEFT" border="0">
<br clear="LEFT">
<br>
Di seguito  un esempio della creazione del Modello 2
<img src="images/pag_mod2.png" name="pag_mod2.png" width="500" align="LEFT" border="0">
<br clear="LEFT">
Cliccando su STAMPA MOD.2 VERBALE CAMPIONE BATTERIOLOGICO il sistema 
produrràï¿½ il vero e proprio verbale come da esempio seguente.
<img src="images/modello2-1.png" name="modello2-1.png" width="500" align="LEFT" border="0">
<br clear="LEFT">
<img src="images/modello2-2.png" name="modello2-2.png" width="500" align="LEFT" border="0">
<br clear="LEFT">
<br>Di seguito un esempio della creazione del Modello 3
<img src="images/pag_mod3.png" name="pag_mod3.png" width="500" align="LEFT" border="0">
<br clear="LEFT">
Cliccando su STAMPA MOD.3 VERBALE CAMPIONE CHIMICO il sistema produrràï¿½ il vero e proprio verbale come da esempio seguente.
<img src="images/modello3-1.png" name="modello3-1.png" width="500" align="LEFT" border="0">
<br clear="LEFT">
<img src="images/modello3-2.png" name="modello3-2.png" width="500" align="LEFT" border="0">
<br clear="LEFT">
<br>
Per generare il modello 19 relativo ai campioni per il PNAA bisogna seguire i seguenti passi:
<ul>
	<li>Inserire un controllo ufficiale contente come MOTIVAZIONE :Piano di monitoraggio.</li>
	<li>Selezionare come piano:A12 (piani mostrati in figura)</li>
	<li>Successivamnte cliccare su "Inserisci Campione".</li>
	<li>Selezionare come MOTIVAZIONE il piano inserito nel controllo ufficiale.</li>
	<li>Una volta selezionato il piano si apriràï¿½ una scheda nella quale e' possibile	inserire i dati per il campione PNAA.</li>
</ul>
<img src="images/piano_a12.png" name="piano_a12.png"><br><br>
<img src="images/popup_pnaa.PNG" name="popup_pnaa.PNG"><br><br>
I campi inseriti in questa scheda possono essere modificati 
successivamente cliccando su "MODIFICA DATI PNAA" nella scheda del 
campione.<br><br>
<img src="images/pag_mod19.png" name="pag_mod19.png" width="500" align="LEFT" border="0">
<br clear="LEFT">
Una volta inserite eventuali modifiche cliccando su "STAMPA VERBALE 
PNAA" e' possibile generare il pdf e quindi stampare il Verbale.
<img src="images/verbale_PNAA1.png" name="verbale_PNAA1.png" width="500" align="LEFT" border="0">
<br clear="LEFT">
<img src="images/verbale_PNAA2.png" name="verbale_PNAA2.png" width="500" align="LEFT" border="0">
<br clear="LEFT">
<img src="images/verbale_PNAA3.png" name="verbale_PNAA3.png" width="500" align="LEFT" border="0">
<br clear="LEFT">
<img src="images/verbale_PNAA4.png" name="verbale_PNAA4.png" width="500" align="LEFT" border="0">
<br clear="LEFT">
<br> In particolare il modello conterrà: 
<ul>
	<li>il barcode del numero del verbale, progressivo generato automaticamente dal sistema, e che si trova in alto a destra;</li>
	<li>il barcode del numero di registrazione dell'OSA, in basso,in corrispondenza della colonna "Codice OSA";</li>
	<li>il barcode del quesito diagnostico, ovvero della motivazione  
selezionata nell'ambito del campione, posizionato sempre in basso ed
		in corrispondenza della colonna "Codice Quesito diagnostico";</li>
	<li>il barcode della matrice selezionata nel campione. In realtà, il numero dei barcode puvariare da 1 a 3 a seconda dei
		livelli della matrice.</li>
</ul>
<br>
La descrizione dei barcode, fatta eccezione per il numero di registrazione e il progressivo del verbale, ricercabile nel modello
HTML:
<ul>
	<li>la motivazione si trova nella sezione in alto a sinistra
		"Campione effettuato per:"</li>
	<li>la matrice si trova in corrispondenza del rigo "al prelievo
		di un campione di"</li>
</ul>
<br>
<br>
<br>
<h2>Aggiunta di un provvedimento causato da n.c. a carico del soggetto ispezionato</h2><br>
Oltre alle non conformità, nel dettaglio di un controllo ufficiale, gestito il provvedimento causato da non
conformità a carico del soggetto ispezionato, in cui richiesto semplicemente di selezionare una serie di campi obbligatori
tra cui la lista di provvedimenti adottati.	<br><br>

<h2>Supervisione dei controlli</h2>
<p style="text-align: justify"><br> E' stata introdotta in gisa la
possibilita di effettuare attivita' di supervisione. Le attività
di supervisione possono essere distinte in tre categorie :
</p><ul>
	<li>CASO 1: Attività di supervisione sul campo</li>
 	<li>CASO 2: Attività di supervisione documentale</li>
 	<li>CASO 3: Attività di supervisione mediante simulazioni</li>
</ul> 	
<br><br>
CASO 1: per il caso 1 si pensato di gestire i controlli di supervisione come tipologie di controlli richiesti da altre
autorità. Quindi e' stata sostituita nella combo contentente i tipi di ispezione semplice, la voce "su
richiesta forze dell'ordine o altre autorità" con le seguenti voci:
<ul> 	
	<li>su richiesta forze dellordine o altre autorità</li>
	<li>su richiesta forze dellordine o altre autorità - supervisione del direttore / responsabile (1.1)</li>
	<li>su richiesta forze dellordine o altre autorità - supervisione con il direttore / responsabile (1.2)</li>
</ul>
<br>
CASO 2: per la gestione del caso 2 invece stata introdotta una nuova 
funzionalità  accessibile mediante bottone  che consente agli utenti 
abilitati di porre il controllo nello stato "supervisionato". 
Gli utenti che devono accedere a tale funzionalità sono tutti i medici e
 veterinari aventi "ruoli di direzione"(direttore dip. Prevenzione, 
direttore serv. Vet.,
direttore SIAN, responsabili struttura SIAN e vet.). 
Potra' essere posto nello stato supervisionato qualsiasi controllo 
aperto o
chiuso. Una volta posto nello stato supervisionato il bottone di 
supervisione scomparira' e nel dettaglio controllo
compariranno le seguenti informazioni:
<u>Controllo supervisionato da: in data:</u><br><br>

CASO 3: per il caso 3, nel cavaliere ASL, oltre a gestire i controlli di tipo AUDIT, prevista una nuova tipologia di
controllo chiamata "Supervisione per simulazione". Quindi l'elenco dei tipi di controllo oltre a contenere AUDIT deve contenere
anche la voce "supervisione per simulazione".<br>
In questo ultimo caso, l'utente sarà obbligato ad allegare un documento, che quasi sicuramente rappresenterà il verbale redatto 
durante il controllo. Questo documento sarà disponibile per il download in fase di modifica e di dettaglio senza poter essere 
modificato.<br> 
La gestione del controllo ufficiale per le restanti parti, rimane invariata.
<br><br>
<H2>Nuova Gestione del nucleo ispettivo</H2>	
<p align="justify">Per nucleo ispettivo si intende il gruppo di persone che partecipano a un controllo ufficiale.<br>
In GISA e' possibile, nell'ambito dell'inserimento di un nuovo controllo, associare i partecipanti.
nel seguente modo :</p>
<ul>
<li>Nella prima lista viene selezionata la qualifica/ruolo della persona che si intende inserire (come mostrato nella figura sottostante)</li><br>
<img src="images/nucleo-1.png" name="nucleo-1.png" height="300" width="600" align="LEFT" border="0"> <br clear="left"><br>
<li><p align="justify">Cliccando su &quot;Seleziona Voce&quot;, il sistema mostrera' la lista di tutto il personale ASL presente nella qualifica selezionata.<br>
<img src="images/nucleo-2.png" name="nucleo-2.png" height="50" align="LEFT" border="0"> <br clear="left">
</ul>
E' possibile ordinare/filtrare l'elenco nominativi utilizzando gli appositi campi filtro disponibili in testa alla colonna.
La scelta dei partecipanti al controllo ufficiale avviene mettendo un check accanto al nome e cliccando sul pulsante &quot;Fatto&quot;. Si completa in questo modo l'operazione di aggiunta dei nominativi al nucleo ispettivo.
<br>Se nella lista manca un utente, per determinate qualifiche e' possibile anagrafare un nuovo componente digitando il tasto che si visualizza in alto "anagrafa nuovo componente nucleo". <br/>
<img src="images/anagrafa_nucleo_bottone.png" name="nucleo" border="0"><br/><br/>
Si aprira' una nuova maschera che permette di inserire nome e cognome e salvare il nuovo soggetto.<br>
<img src="images/anagrafa_nucleo.png" name="nucleo" border="0"><br/><br/>
La lista delle qualifiche che prevedono il bottone di Anagrafa Componente e' la seguente : 
<ul>
<li>AGEA</li>
<li>AGENZIA DELLE DOGANE</li>
<li>ARPAC</li>
<li>ASSESSORATO AMBIENTE ED ECOLOGIA</li>
<li>ASSESSORATO ATTIVITA' PRODUTTIVE</li>
<li>C.F.S. AV</li>
<li>C.F.S. BN</li>
<li>C.F.S. CE</li>
<li>C.F.S. NA</li>
<li>C.F.S. SA</li>
<li>CORPO FORESTALE DELLO STATO</li>
<li>GUARDIA COSTIERA</li>
<li>GUARDIA DI FINANZA</li>
<li>GUARDIE ZOOFILE</li>
<li>I.C.Q.</li>
<li>NAC</li>
<li>NAS CE</li>
<li>NAS NA</li>
<li>NAS SA</li>
<li>NOE</li>
<li>PIF</li>
<li>POLIZIA</li>
<li>POLIZIA MUNICIPALE</li>
<li>POLIZIA PROVINCIALE</li>
<li>Polizia Stradale</li>
<li>USMAF</li>
<li>UTENTI ASL</li>
<li>UTENTI EXTRA ASL</li>
<li>UVAC</li>
</ul>
<br/>


<img src="images/nucleo-3.png" name="nucleo-3.png" height="300" width="600" align="LEFT" border="0"> <br clear="left"><br>
In caso di errore nella selezione, per poter cambiare uno dei nominativi impostati, bastera' digitare il campo "elimina" accanto al nome selezionato.

</p>				

<h3>NOTA</h3>
<ol>
<li>i campi &quot;Inserito da&quot; e 
&quot;Modificato da&quot; conterranno comunque il nominativo di chi ha inserito/modificato il controllo ufficiale.
</li>
</ol>
<br><br>
<h2>Gestione Esito campioni</h2>
Per un campione associato ad un controllo ufficiale e' possibile inserire
 l'esito direttamente dal dettaglio del campione.In particolare sono 
presenti due tabelle:
'â‚¬Å“Informazioni campione da laboratorio'â‚¬Â nella quale e' possibile
 inserire:
<ul>
	<li>Codice accettazione</li>
	<li>Data accettazione</li>
	<li>Risultato laboratorio</li>
	<li>Data risultato</li>
</ul>
<br>
<img src="images/informazioni_campione.PNG" align="BOTTOM" border="0"><br clear="LEFT"><br>
I dati inseriti in questa tabella sono modificabili successivamente 
anche se si chiude il campione o il controllo ufficiale cliaccando sul 
pulsante "RIAPRI INFORMAZIONI LABORATORIO".
<br><br>
L'altra tabella contiene l'esito del campione.<br>
Le informazioni inserite in questa tabella una volta cliccato sul tasto "salva" non sono più modificabili.

<img src="images/esito_campione.PNG" width="500" align="BOTTOM" border="0"><br clear="LEFT"><br>
<div class="fine" style="height: 50px;">&nbsp;</div>

<a name="macellazioni"></a> <br>
<h1>Macellazioni</h1>
<br>
<h2>Macelli</h2>
<br>
<p style="text-align: justify" dir="ltr">

La gestione dei macelli nel sistema GISA, e' presente nella 
sottosezione"Macellazioni" degli Stabilimenti Riconosciuti 853. 
àË† possibile ricercare uno dei macelli presenti nel sistema 
selezionando nel campo categoria: 'CARNE DEGLI UGULATI DOMESTICI' ed 
impianto: '
SH-MACELLO'. L'utente puàï¿½ a questo punto accedere al dettaglio dello 
stabilimento e selezionare 'Macellazioni' tra le etichette laterali, 
a questo punto,  selezionare la tipologia di macellazione desiderata 
cliccando sull'apposito pulsante, ovvero: "Bovini/Bufalini/Equidi" 
oppure '
Ovicaprini'.&nbsp;</p><p style="text-align: justify" dir="ltr"><br>
<img src="images/ovicaprini20lookup.bmp" name="ovicaprini lookup.bmp" width="500"><br clear="LEFT"><br>

</p><h1>Macellazioni Ovicaprine</h1>
<h2>Aggiunta/Modifica Partita</h2>

<p>
Le macellazioni ovicaprine sono gestite differentemente da quelle degli 
altri ungulati (bovini, bufalini, equini) per i quali i dati sono 
riferiti 
al singolo capo macellato/da macellare.  </p>

<p>
Per le specie ovicaprine&nbsp; e' gestita la partita di animali e non il 
singolo capo. E' possibile inoltre dopo aver creato la partita che 
comprende 
tutti i capi da macellare inserire per la singola partita una o più 
sedute di macellazione che si sommano fino a raggiungere il numero di 
capi 
della partita</p>

<p>
<span style="line-height: 115%">Come per tutte le sezioni del sistema GISA, solo i </span>
campi contrassegnati da asterisco rosso sono obbligatori. Gli altri, se 
inseriti, consentono una migliore gestione di tutte le informazioni 
relative 
alle macellazioni</p>

<p>La maschera principale delle macellazioni ovicaprine mostra l'elenco delle 
partite macellate nell'ultima seduta eseguita in ordine temporale (vedi figura 
seguente). E' possibile filtrate le partite tra Macellate (sia completamente che 
parzialmente)in base alla date e partite Non Macellate usando gli appositi 
pulsanti. Il filtro sulla data prende tutte le partite che hanno almeno una 
seduta effettuata nella data scelta.</p>

<p>
<br>
<img src="images/elenco_partite.PNG" name="Elenco Partite" width="500" align="LEFT" border="0"><br clear="LEFT"><br>
Il flag 'Stampa Art. 17' saràï¿½ visualizzato come segue:</p>

<ul>
	<li>SI</li>
	<li>Parzialmente</li>
	<li>NO</li>
</ul>

<p>
Quindi nel caso delle partite, la presenza della spunta nella colonna 
'Stampato Art.17' indica che tutti gli animali della partita sono stati 
macellati in una o più sedute per le quali e' stato stampato l'art.17 a 
meno delle eccezioni (es. capi morti o rubati). La modifica sulla 
partita e' 
possibile fin quando lo stato del flag non si trova su 'SI', quindi 
quando non e' stato completamente stampato l'Art.17.   </p>

<p>

Nella maschera delle macellazioni, e' presente il bottone [Aggiungi Partita] (vedi figura seguente)</p>

<p>

In fase di aggiunta partita, l'unico tab attivo saràï¿½ quello relativo al "Controllo documentale" nel quale l'utente puàï¿½ 
inserire molte informazioni relative alla partita e compilare obbligatoriamente i campi:</p>
<ul>
	<li>Azienda di provenienza della partita</li>
	<li>Proprietario delle carni (che saràï¿½ riportato nella stampa dell'articolo 17);</li>
	<li>il codice della partita</li>
	<li>data arrivo al macello</li>
	<li>veterinari addetti al controllo</li>
</ul>
in mancanza dei quali, verrà opportunamente avvisato tramite messaggio.

<br>
<img src="images/Controllo20documentale20ovicaprini.bmp" name="Controllo documentale ovicaprini.bmp" height="846" width="754" align="LEFT" border="0"> <br clear="LEFT"><br>

<p>Il numero della partita, che rappresenta l'identificativo univoco 
della partita stessa, viene generato automaticamente dal sistema GISA, 
ma c'e' al momento 
l'opzione con campo a testo libero&nbsp; per contraddistinguerlo con 
numerazione propria, se utilizzato sistema interno giàï¿½ consolidato 
(opzione peràï¿½ 
che si tenderàï¿½ a rendere disponibile solo in fase iniziale, in quanto
 tra gli obiettivi del sistema vi e' anche la sostituzione dei registri 
cartacei 
attualmente in uso con quelli informatici).</p>

<p>Dopo questa prima fase del Controllo Documentale, si potràï¿½ 
proseguire alle fasi successive in due modi diversi, o cliccando sul 
bottone [Altre operazioni] 
o accedendo direttamente al [Libero consumo].</p>

<p>Cliccando sul bottone [Altre operazioni], l'utente ha la 
possibilitàï¿½ di compilare le sezioni successive al controllo 
documentale, ovvero: "
Comunicazioni Esterne", "Morte antecedente macellazione", "Evidenza 
Visita Ante Mortem" e "Evidenza Visita Post Mortem". 
Si rammenta che queste operazioni non devono essere necessariamente 
compilate, ma sono utilizzate solo nel caso in cui deve essere 
registrato un evento 
(es. morte di animali in stalla, sull'automezzo, oppure rilievi 
particolari in fase di visita antemortem o post mortem, oppure se e' 
necessario fare 
comunicazioni a ditte o Enti esterni in relazione all'animale o partita 
di animali da macellare, ecc.).</p>

<p>Invece cliccando sul bottone [Libero consumo], l'utente puàï¿½ 
accedere direttamente alla compilazione della sezione "
Evidenza Visita Ante Mortem" e "Evidenza Visita Post Mortem", passando 
direttamente alla macellazione della partita o di parte di essa. 
Questo percorso, che rappresenta la quasi totalitàï¿½ delle opzioni in 
corso di macellazione, deve essere seguito nel caso in cui le carni 
vanno al libero 
consumo con ("Evidenza Visita Post Mortem") oppure senza ("Evidenza 
Visita Ante Mortem" ) sequestro di organi.</p>

<p>&nbsp;</p>

<p>Si ricorda, inoltre, che il tasto 'â‚¬Å“COMPLETA MACELLAZIONE E 
CLONA'â‚¬Â (fino ad ora era 'â‚¬Å“Salva e Clona'â‚¬Â) consente di 
salvare diverse informazioni invariate, utili 
per evitare di digitarle nuovamente nel caso in cui deve essere inserita
 una nuova partita o un nuovo animale proveniente dallo stesso 
speditore, azienda di origine, 
ecc.</p>

<p dir="ltr"><br> <br>

</p>
<h2>Import Capi nei macelli</h2>
Nella home page delle macellazioni bovine e' stato aggiunto il bottone IMPORTA DA FILE<br><br>
<img src="images/importa_da_file.png" name="importa_da_file.png" width="700px" align="LEFT" border="0"> <br clear="LEFT"><br>
Tramite il bottone, e' possibile selezionare un file (in formato CSV), 
redatto secondo uno standard concordato. Il sistema provvede ad estrarre
 da ogni riga del file le informazioni relative 
al singolo capo ed inserire la componente di controllo documentale. <br><br>

La nuova funzione permette di caricare un file CSV con i dati inseriti in questo modo:<br>
Riga 1: Intestazioni<br>
Colonna 1: COD.AZ.NASCITA<br>
Colonna 2: COD.AZ.PROVENIENZA<br>             	
Colonna 3: MATRICOLA<br>                      	
Colonna 4: SPECIE<br>                	
Colonna 5: SESSO<br>                   	
Colonna 6: DATA NASCITA<br>                   	
Colonna 8:  Categoria di rischio<br>
Colonna 9: Vincolo sanitario<br>
Colonna 11: Data Modello 4<br>
Colonna 14: Data arrivo Macello<br>
Colonna 15: Test BSE<br>
Colonna 16: Data prelievo BSE<br>
Colonna 17: Esito BSE<br>
Colonna 25: Veterinari addetto al controllo<br><br>

Successivamente all'import nella lista dei capi non macellati, in caso 
di capo importato e' possibile macellarlo in libero consumo tramite un 
apposito bottone.<br>
<img src="images/controllo_documentale.png" name="controllo_documentale.png" width="400px" align="LEFT" border="0"> <br clear="LEFT"><br>
Cliccando sul bottone, saranno prima effettuati i controlli minimi 
(Stato incompleto, data presente). il capo sarà poi macellato con i 
seguenti parametri:
<ul>
	<li>Esito visita PM: Libero consumo carni e visceri</li>
	<li>Destinatario Carni 1: Macello corrente</li>
	<li>Destinatario Carni 2: Macello corrente</li>
	<li>Stato Macellazione: OK.</li>
	
</ul>
<br>
e' possibile inoltre verificare la lista degli import inseriti per uno stabilimento cliccando nel menu laterale 
<img src="images/storico_macelli.png" name="storico_macelli.png" width="750px" align="LEFT" border="0"> <br clear="LEFT"><br>
<img src="images/lista_import.png" name="lista_import.png" width="750px" align="LEFT" border="0"> <br clear="LEFT"><br>










<h2>Altre Operazioni</h2>
<br> 
Di seguito l'elenco delle sezioni compilabili a seguito della fase del 
"Controllo Documentale":&nbsp;
Comunicazioni Esterne": i campi "comunicazione a", "data", "tipo non 
conformitàï¿½" e 
"provvedimenti adottati" verranno considerati come un blocco unico, per 
cui la 
valorizzazione di almeno uno di essi comporteràï¿½ l'obbligatorietàï¿½ 
di valorizzare 
anche gli altri. A livello visivo ciàï¿½ verràï¿½ evidenziato mediante 
la comparsa di 
asterischi.
Come prima riferito, questa Sezione e' utilizzata solo se e' necessario 
fare comunicazioni a persone, ditte o Enti esterni in relazione 
all'animale o partita di
 animali da macellare.
<br>
<img src="images/comunicazioni20esterne20Ovicaprini.bmp" name="comunicazioni esterne Ovicaprini.bmp" height="772" width="684" align="LEFT" border="0"><br clear="LEFT">
<br>
<ul>
	<li>"Morte antecedente macellazione": i campi "data", "luogo" e "causa"
 verranno considerati come un blocco unico, 
		per cui la valorizzazione di almeno uno di essi comporteràï¿½ 
l'obbligatorietàï¿½ di valorizzare anche gli altri. A livello visivo 
ciàï¿½ verràï¿½ 
		evidenziato mediante la comparsa di asterischi.</li>	
	<li>Visita Ante Mortem": i campi "data" e "provvedimento adottato" 
verranno considerati come un 
		blocco unico, per cui la valorizzazione di almeno uno di essi 
comporteràï¿½ l'obbligatorietàï¿½ di valorizzare anche gli altri. A 
livello visivo ciàï¿½ 
		verràï¿½ evidenziato mediante la comparsa di asterischi.</li>
	<li>Visita Post Mortem": il campo "data" e' obbligatorio. E' possibile 
specificare il numero di capi inviati all' eventuale destinatario 
		delle carni riempendo i campi obbligatori 'NUMERO CAPI OVINI' e 
'NUMERO CAPI CAPRINI'.  Ad ogni esito selezionato deve essere associato 
un 
		numero capi nel campo di testo che compare cliccando una voce della 
lista.&nbsp;</li>
	<li>Nel libero consumo, se le date di "Visita Ante Mortem" e di "Visita
 Post Mortem" non sono state giàï¿½ valorizzate precedentemente, 
		verranno automaticamente settate con la data di arrivo al macello e 
numero capi. I destinatari delle carni possono essere n, quindi si 
possono aggiungere 
		altri, oltre i 4 di default.</li>
</ul>
<br><br>
In caso di visita post mortem con sequestro organi, sarà possibile 
generare il Modello Echinococcosi da inviare al macello in caso di 
organo con lesione "Echinococco".
<br>
<img src="images/modello_echinococco.png" name="Modello echinococco" width="500" align="LEFT" border="0"><br clear="LEFT"><br>
 

<br><br>
In ognuno dei tab Comunicazioni Esterne , Morte Ant. Macellazioni , 
Evidenza visita AM , Evidenza visita PM esiste un controllo per cui il 
totale del 
numero capi specificato nelle sezioni della partita e delle eventuali 
sedute non deve superare il numero capi specificato nel controllo 
documentale.
<br><br>
<h1>Gestione Sedute</h1>
<br><br>
Tenuto conto che una partita di animali potrebbe essere macellata in più
 giornate, si e' ritenuto di gestire le sedute di macellazione per ogni 
partita.
<br><br>
Nell'elenco delle partite mostrato dopo la scelta del tipo macellazione 
"Ovicaprini" (vedi figura seguente), per ogni riga sono riportati lo 
stato (Aperta/Chiusa) e tre link corrispondenti ad altrettante 
operazioni:
<br><br>
<ul>
	<li>Aggiungi Seduta</li>
	<li>Lista Sedute</li>
	<li>Chiudi</li>
</ul>
<br>
<img src="images/elenco_partite.PNG" width="500" border="0"> <p></p><br><br>

<h1 dir="ltr">Aggiunta Seduta</h1>
<br><br>
E' possibile aggiungere sedute solo fino a quando la somma dei capi 
macellati e morti prima della macellazione risulta inferiore a quelli 
specificati nel controllo documentale.
<br>
Tuttavia, se questa somma e' ancora inferiore e' possibile bloccare 
l'inserimento di altre sedute chiudendo la partita come saràï¿½ 
spiegato nell'apposito paragrafo.
<br>
La pagina che si apre cliccando "Aggiungi Seduta" mostra in sola lettura le 
sezioni "Controllo Documentale" e "Comunicazioni Esterne" popolate con i dati 
della partita di origine, i restanti tab, invece, andranno ripopolati con le 
informazioni relative alla seduta che si sta aggiungendo. In particolare se si popolano i campi data 
in una delle sezioni visita 'Evidenzia Visita AM'/'Evidenzia Visita PM' si attiveràï¿½ il controllo su tutti 
i campi abblogatori delle due sezioni, in particolare saranno richiesti obbligatori i seguenti campi:
<br>
<br>
'Evidenzia Visita AM'
<ul>
	<li>DATA</li>
	<li>NUMERO CAPI OVINI</li>
	<li>NUMERO CAPI CAPRINI</li>
	<li>PROVVEDIMENTO ADOTTATO</li>
</ul>
<br>
'Evidenzia Visita PM'
<ul>
	<li>DATA MACELLAZIONE</li>
	<li>NUMERO CAPI OVINI IN DESTINATARI/ESERCENTI</li>
	<li>NUMERO CAPI CAPRINI IN DESTINATARI/ESERCENTI</li>
</ul>
<br>
Anche per le sedute vi e' il bottone LIBERO CONSUMO con il quale l'utente puàï¿½ accedere direttamente alla 
compilazione della sezione "Evidenza Visita Ante Mortem" e "Evidenza Visita Post Mortem", passando 
direttamente alla macellazione della seduta. Questo percorso, che rappresenta la quasi totalitàï¿½ delle opzioni 
in corso di macellazione, deve essere seguito nel caso in cui le carni vanno al libero consumo.
<br><br>
<h1>Lista Sedute</h1>
<br><br>	
Per visualizzare le sedute di macellazione relative alla partita 
cliccare sul link "Lista Sedute". Saràï¿½ mostrato un elenco come quello
 in figura.
<br><br>
<img src="images/elenco_sedute.bmp" width="500" border="0">
<br><br>
Per visualizzare una seduta cliccare sul link presente nella colonna 
"Seduta". Saràï¿½ mostrata la pagina con i dati nella quale e' anche
possibile stampare l'articolo 17 tramite apposito pulsante.
<br><br>
Il flag 'Stampa Articolo 17' indica se per quella seduta e' stato stampato il documento.
<br><br>
<img src="images/dettaglio_seduta.bmp" width="500" border="0">
<br><br>
Tramite il tasto "Modifica" saràï¿½ possibile accedere alla modifica.
<br><br>
<h1>Gestione sedute di macellazioni pregresse</h1>
<br>
Per quanto riguarda le macellazioni bovini e' possibile inserire una 
seduta di macellazione in data antecedente rispetto all'ultima seduta 
registrata.<br>
Per inserirla basta scegliere la data di interesse e flaggare la check 
"SEDUTA PREGRESSA" presente nella scheda "aggiungi una seduta di 
macellazione".<br>
Come mostrato in figura:
<br><br>
<img src="images/macellazione_pregressa.PNG" border="0">
<br><br>
Fatto questo basta cliccare su "AGGIUNGI A NUOVA DATA".
Cosi facendo si aprirà la lista delle macellazioni pregresse nella data 
precedentemente settata, nella quale e' possibile inserire i relativi 
capi.<br>Come mostrato in figura:<br><br>
<br><br>
<img src="images/lista_pregresse.PNG" border="0">
<br><br>

N.B. Per tutte le sedute di macellazione inserite con questa gestione non e' possibile stampare alcun tipo di documento.<br>


<h1>Chiusura Partite</h1>
<br><br>
Cliccando sul link "Chiudi" e' possibile chiudere la partita. Ne consegue
 che lo stato passerà da "Aperta" a "Chiusa" e da 
quel momento non sarà più possibile aggiungere altre sedute di 
macellazioni. Prima di chiudere la partita il sistema chiede di inserire
 delle note di 
chiusura tramite la seguente popup. Inoltre quando la partita viene 
chiusa tramite pulsante "Chiudi" e' possibile  visualizzare le 
motivazioni inserite in 
fase di chiusura tramite un apposito link posto nella lista delle 
partite		
<br><br>
<img src="images/chiudi_partita.bmp" width="500" border="0">
<br><br>
	
<h1>MACELLAZIONI BOVINI</h1>
<br><br>
<h2>Aggiungi Sessione Macellazione</h2>
<br>
Aggiunta nuova funzionalità che permette di inserire una nuova sessione 
di macellazione per data e suddividere quest'ultima in più sessioni.<br>
Il corretto funzionamento prevede che sia possibile effettuare due operazioni:
<ul>
	<li>Selezionare una nuova data di macellazione: Per aggiungere un capo ad una nuova sessione(quindi nuova data di macellazione), 
	aggiungere una nuova data utilizzando il bottone rosso in basso "Aggiungi a nuova data".</li>
	<li>Sessioni esistenti:
    Completato l'inserimento del capo e' possibile proseguire con 
ulteriori sessioni impostando la data della sessione di macellazione e 
cliccando
	su procedi nella schermata "Sessioni esistenti". Per aggiungere un 
ulteriore sessione di macellazione alla stessa data e' necessario 
completare 
	la prima sessione e successivamente si potràï¿½ aggiungere la sessione 
2.</li>
</ul><br><br>

	<img src="images/aggiungi_nuova_data.bmp" name="aggiungi_nuova_data.bmp" align="LEFT" border="0"><br clear="LEFT"><br><br>
	
<img src="images/aggiungi_sessione.bmp" name="aggiungi_sessione.bmp" align="LEFT" border="0"><br clear="LEFT"><br><br>
	
Per concludere definitivamente la sessione di macellazione occorre stampare l'articolo 17 e 
tutti i dati delle singole sessioni saranno riportati nel registro di macellazione.
<br><br>
<h2>Aggiunta/Modifica Capo</h2>
<br> In fase di aggiunta capo, l'unico tab attivo sarà quello
relativo al "Controllo documentale", gli altri tab verranno visualizzati
su espressa richiesta dell'utente cliccando sul bottone "Altre
operazioni" (che si trova vicino al bottone "Libero consumo"), a patto
che siano stati compilati i dati obbligatori (provenienza dell'animale,
matricola, data arrivo al macello, veterinari addetti al controllo), in
mancanza dei quali, l'utente verrà opportunamente avvisato tramite
messaggio. Invece nella fase di modifica, appariranno già tutti i tab
attivi per cui non sarà presente il bottone "Altre operazioni". 
<ul>
	<li>Per settare la provenienza dell'animale, opportuno
	valorizzare il campo "Codice Azienda di provenienza" in cui indicare il
	riferimento BDN all'azienda di provenienza del capo.</li>
	<li>La data di arrivo al macello potrà essere settata (solo nel
	caso in cui non sia già stata valorizzata) con la data odierna
	semplicemente portando il cursore del mouse nella relativa casella di
	testo, per qualsiasi altra data si potrà continuare ad usare
	normalmente il calendario.</li>
	<li>Le date relative a "Comunicazioni Esterne", "Ricezione
	Comunicazioni Esterne", "Morte antecedente macellazione", "Visita Ante
	Mortem" e "Visita Post Mortem" (solo nel caso in cui non siano già
	state valorizzate) potranno essere settate alla data di arrivo al
	macello semplicemente portando il cursore del mouse nella relativa
	casella di testo, per qualsiasi altra data si potrà continuare ad usare
	normalmente il calendario.</li>
	<li>Verrà data la possibilità di cancellare una determinata data
	cliccando su un'apposita icona posizionata vicino al calendario.</li>
	<li>Verrà gestita la categoria di rischio che verrà assegnata
	automaticamente dal sistema in funzione della compilazione dei dati
	relativi all'animale.<br> La categoria dovrà comparire nei verbali
	di prelievo di tronco encefalico per test BSE e nel registro di
	macellazione per una delle seguenti categorie: 
	<ul>
		<li>Bovini sani sopra i 72 mesi di età</li>
		<li>Macellazione in emergenza - &gt; test BSE se età BOVINI sopra i
		48 mesi</li>
		<li>Macellazione differita in caso di Brucellosi, TBC, Leucosi se
		i bovini superano i 48 mesi di età.</li>
		<li>Bovini morti durante il trasporto (test BSE obbligatorio).</li>
	</ul>
</li></ul>
<br>
<h2>Altri Tab</h2>
<br> Di seguito l'elenco dei tab presenti: 
<br>
<ul>
	<li>"Comunicazioni Esterne": i campi "comunicazione a", "data",
	"tipo non conformità" e "provvedimenti adottati" verranno considerati
	come un blocco unico, per cui la valorizzazione di almeno uno di essi
	comporterà l'obbligatorietà di valorizzare anche gli altri. A livello
	visivo civerrà evidenziato mediante la comparsa di asterischi.</li>
	<li>"Morte antecedente macellazione": i campi "data", "luogo" e
	"causa" verranno considerati come un blocco unico, per cui la
	valorizzazione di almeno uno di essi comporterà l'obbligatorietà di
	valorizzare anche gli altri. A livello visivo civerrà evidenziato
	mediante la comparsa di asterischi.</li>
	<li>"Visita Ante Mortem": i campi "data" e "provvedimento
	adottato" verranno considerati come un blocco unico, per cui la
	valorizzazione di almeno uno di essi comporterà l'obbligatorietà di
	valorizzare anche gli altri. A livello visivo civerrà evidenziato
	mediante la comparsa di asterischi.</li>
	<li>"Visita Post Mortem": i campi "data" e "destinatario carni"
	verranno considerati come un blocco unico, per cui la valorizzazione di
	almeno uno di essi comporterà l'obbligatorietà di valorizzare anche gli
	altri. A livello visivo civerrà evidenziato mediante la comparsa di
	asterischi.</li>
	<li>Nel libero consumo, se le date di "Visita Ante Mortem" e di
	"Visita Post Mortem" non sono state già valorizzate precedentemente,
	verranno automaticamente settate con la data di arrivo al macello.</li>
	<li>Il libero consumo, in fase di modifica, comporterà un
	aggiornamento dei dati del capo e non piil tentativo di inserire un
	nuovo capo, cosa che comportava un errore di matricola duplicata.</li>
	<li>Nel tab "Controllo Documentale" sarà modificata la gestione
	dei dati relativa alla provenienza dell'animale. Sarà data all'utente
	la possibilità di selezionare lo speditore da una popup; nel caso in
	cui non fosse presente, potrà aggiungerlo con l'apposito link e se,
	necessario, potrà modificarlo e cancellarlo nella popup stessa.</li>
</ul>
<br> 
<h2>Tamponi di Macellazione</h2>
<br> Di seguito l'elenco dei tab presenti: 
<br>
<ul>
	<li>E' possibile inserire per ogni capo macellato un tampone.
	L'inserimento del tampone per i capi macellati avviene nella
	sezione visita "post moertem" , o nel percorso abbreviato 
	"Libero consumo".<br>
	Per ogni seduta di macellazione previsto un solo tampone
	che viene associato a tutti i capi di quella seduta di macellazione.
	Se si sta inserendo il primo capo di una seduta di macellazione , 
	spuntando il check "Effettuato Tampone" , verranno visualizzati i
	campi relativi al tampone da inserire : piano (settato di default con il numero 83) ,
	tipo di carcassa (di default bovino) , tipo di ricerca , metodo.<br>
	Al prossimo capo inserito spuntanto il flag "Effettuato Tampone" ,
	verranno visualizzati i dati del tampone inseriti precedentemente , con la possibilita di modifica.
	All'atto dell'inserimento verrà creata una nuova associazione tra il tampone inserito sul 
	primo capo e il capo che si sta inserendo.
	In fase di modifica togliendo la spunta su un capo soggetto a tampone,
	verra eliminata logicamente l'associazione tra il tampone e il capo. </li>
	<li>Modello 10 : sarà possibile eseguire una stampa dei tampone per ogni seduta di macellazione 
	tramite il link "Modello 10" presente nella maschera delle macellazioni ungulati e dopo aver specificato
	una data di macellazione: saranno mostrate nell'elenco solo le date in cui effettivamente stato registrato
	un tampone.<br>
	Assicurarsi di eseguire la stampa solo dopo aver completato l'inserimento (o modifica) dei capi
	di una singola seduta in quanto dopo aver eseguito la stampa non sarà piu possibile agire sulla seduta 
	per cui si richiesto il modello 10. </li>
</ul>
<br>
Nella maschera di inserimento di un capo, precisamente nella
scheda relativa alla Visita Post Mortem, viene data la possibilità di
inserire un numero intero che identifichi quel capo all'interno della
sessione di macellazione. Per tale motivo il campo stato chiamato
"Progressivo Macellazione". Questi capi devono poter essere salvati in
modalità definitiva allo scopo di essere inclusi nel registro di
macellazione (ovviamente non si deve precludere la possibilità di
inserire successivamente gli esiti).
<br> Nella gestione dei macelli viene introdotto il concetto di
sessione di macellazione, in base al quale viene data la possibilità di
assegnare ad ogni capo di una sessione un numero (Progressivo di
Macellazione) e di mostrare la lista di tutti i capi appartenenti ad una
determinata sessione. Per sessione di macellazione si intende l'insieme
di tutti i capi che sono stati macellati nella stessa giornata dallo
stesso macello, pertanto, dato un macello, c'una corrispondenza
biunivoca tra sessione di macellazione e la data di macellazione. In
realtà, nel registro di macellazione saranno visibili anche i capi morti
prima della
<br> macellazione e in attesa di esito per il test BSE.
<br> Infine possibile avere pidi una seduta di macellazione
nella stessa data. L'utente, infatti, nel dettaglio di un capo, ha la
possibilità di aggiungere una nuova seduta di macellazione, chiudendo
implicitamente quella precedente. 
<br>
<br>
<img src="images/progressivo_macellazione.bmp" name="progressivo_macellazione.bmp" width="500" align="LEFT" border="0"><br clear="LEFT">
<br> Inoltre, quando si accede alla lista dei capi macellati da un
determinato macello, non vengono pimostrati tutti i capi, bensquelli
relativi all'ultima sessione (in altre parole tutti i capi con data di
macellazione uguale a quella pirecente). Viene poi data la possibilità
di visualizzare i capi relativi ad altre sessioni, scegliendo una data
tra quelle proposte nel mena tendina (dove vengono riportate le ultime
date di macellazioni del macello in questione) oppure selezionandola con
il calendario (la data scelta verrà in tal caso aggiunta nel mena
tendina).
<br> Nella lista delle date di macellazione sarà inserita anche la
voce "Capi non macellati" in modo da gestire quei capi che potrebbero
non essere macellati ma di cui richiesta la visualizzazione
nell'elenco. 
<br>
<br>
<img src="images/capi_macellati_e_non.bmp" name="capi_macellati_e_non.bmp" width="500" align="LEFT" border="0"><br clear="LEFT">
<br> Durante la compilazione della scheda "Aggiungi capo",
"Modifica capo" o "Libero consumo", se l'utente clicca in un punto della
pagina che comporta l'uscita dalla scheda stessa, verrà chiesto
all'utente se vuole effettivamente abbandonare la pagina o se vuole
provare a salvare i dati. 
<br>
<br>
<h2>Articolo 17</h2>
<br><br>
Per quanto riguarda gli OVICAPRINI:
<br>
La stampa dell'Articolo 17, secondo R.D. n. 3298 del 20.12.1928, deve 
essere consegnata al proprietario che saràï¿½ riportato nel documento.
Inoltre non e' necessario scegliere l'esercente per l'Art.17.

Nella  Visita PM vi e' la selezione multipla dei destinatari/esercenti 
,selezionabili secondo la struttura seguente per i destinatari/esercenti
 in regione:
<br>
<ul>
<li> 'SELEZIONA IMPRESA'
</li><li> 'SELEZIONA STABILIMENTO'
</li><li> 'INSERISCI ESERCENTE NON IN G.I.S.A.'
</li><li> 'MACELLO CORRENTE';
</li></ul>
selezionabili secondo la struttura seguente per i destinatari/esercenti fuori regione:
<br>
<ul>
<li> 'INSERISCI ESERCENTE FUORI REGIONE'
</li></ul>

<br>E' stata aggiunto il bottone  'Stampato Articolo 17' anche sulle sedute quindi ne consegue che:
<ul>
<li>Dopo la stampa dell'Art. 17  la modifica per le SEDUTE non e' 
possibile mentre per la PARTITA,dato che nessun dato della partita va 
nel documento, risulta 
	possibile modificarla. </li>
</ul>
<br>
Nella stampa dell' Art.17 e' stato inserito in alto a sinistra il 
riferimento a partita, seduta, proprietario degli animali e 
destinatario\esercente.
Al suo interno troveremo i seguenti campi:
<ul>
<li>Partita - numero associata alla partita di macellazione
</li><li>Seduta - numero di seduta se i capi sono inseriti in una seduta
</li><li>Ovini macellati - numero di ovini macellati nella partita\seduta
</li><li>Caprini macellati - numero di caprini macellati nella partita\seduta
</li><li>Categoria
</li><li>Mod 4 - Progressivo del modello 4 che accompagna l'animale
</li><li>Veterinari - L'elenco dei veterinari elencati nella sezione Visita Post Morte
</li><li>Esito Visita - L'esito della visita post Morte settato nella sezione medesima 
</li></ul>

<br> L'identificativo del documento relativo all'articolo 17 sarà
composto da 3 parti:
<br>
<ul>
	<li>Progressivo incrementato dal sistema, che sarà univoco per
	macello e si azzererà ogni anno;</li>
	<li>L'anno relativo alla sessione di macellazione</li>
	<li>approval number del macello.</li>
</ul>
<br> Per quanto riguarda i bovini, una volta che per un determinato
capo viene prodotto il pdf relativo all'articolo 17 (si pustampare
solo quando eventuali prelievi hanno l'esito), tale capo non sarà pi
modificabile, per cui nella scheda di dettaglio, al posto del tasto
"Modifica", verrà presentato un messaggio del tipo "Capo non
modificabile: per esso stato già stampato l'art. 17". Se per il capo
in questione ci sono dei prelievi in attesa di esito, il capo deve
essere apribile in modifica ma solo per l'inserimento degli esiti.
<br>
<br>


<h2>Errata corrige Art. 17</h2>
<br>Dal momento che l'art.17 si riferisce da 1 a n capi, l'errata 
corrige e' generata nel dettaglio del singolo capo. Oltre all'attuale 
messaggio 'Capo non modificabile': per esso e' stato già stampato l'art. 
17 o Modello 10', compare un bottone 'Errata Corrige' che consente  
all'utente di compilare il prestampato e di generarlo nel formato PDF.<br>
<img src="images/Erratta20corrige20Art.bmp" name="Erratta corrige Art .17 (2).bmp" width="500" align="LEFT" border="0"><br clear="LEFT"><br><br>
	
<ul>
	<li>I campi modificabili vengono visualizzati selezionando la relativa checkbox</li>
	<li>Nella modifica della matricola viene ricercato se la matricola 
appartiene già ad un 	capo in GISA(bloccante) o e' 
		presente in BDN (non bloccante). In caso di matricola 	presente in 
BDN, gli altri campi vengono precompilati con quelli recuperati.</li> 
	<li>Dopo il salvataggio del modello, si viene subito rimandati alla generazione automatica del documento.</li>
	<li>Per evitare l'abuso della funzione, e' stato settato a 10 il limite 
massimo di modelli Errata Corrige (e quindi conseguenti modifiche) 
		generabili per un dato capo. 	Superato quel limite, il bottone "Errata
 corrige" viene nascosto.</li>
	<li>Lista Errata corrige: In caso di Errata Corrige modificabile e' visualizzabile un 	link 'Moduli EC generati' in cui e' possibile 
		visualizzare tutti i pdf generati.</li>
</ul>
<br><br>
<img src="images/EC20generati.bmp" name="EC generati.bmp" width="500" align="LEFT" border="0"><br clear="LEFT"><br><br>

Per quanto riguarda le macellazioni ovicaprini il messaggio invece e' questo:<br>
Partita non modificabile: per essa e' stata giàï¿½ stampato l'art. 17 o Modello 10 ;
<br>Cliccando il bottone ERRATA CORRIGE ART.17 	si apre una scheda contenente le informazioni  modificabili.<br>
In particolare e' possibile:
<ul>
	<li>Modificare il numero della partita.</li>
	<li>I dati del Mod.4.</li>
	<li>I veterinari che hanno effettuato il controllo.</li>
	<li>I destinatari delle carni.</li>
</ul>
<br>
Nota:non e' possibile aggiungere ulteriori destinatari e modificare il numero dei capi.<br><br>

<h2>Salva e Clona</h2>
<br> A differenza del pulsante "Salva" che permette il salvataggio
delle informazioni inserite sul capo e il ritorno alla lista dei capi
macellati, "Salva e Clona" permette, una volta salvati i dati del capo,
di ritornare alla schermata di inserimento mantenendo perprecompilati
alcuni campi.
<br> In particolare si presentano già valorizzati il modello 4 con
relativa data, i veterinari addetti al controllo, il codice azienda 
la data di arrivo al macello e, se eventualmente specificati, i campi
relativi ai mezzi di trasporto. Nella lista dei
capi macellati, invece, stata aggiunta una nuova colonna ("Clona")
dove per ogni capo della lista presente un pulsante per effettuare
l'operazione di "clonazione". Per clonazione si intende la possibilità
di inserire un nuovo capo avente dei campi precompilati con le
informazioni relative al capo che si vuole duplicare. 
<br><br>
<img src="images/clona.bmp" name="clona.bmp" width="500" align="LEFT" border="0"> <br clear="left"><br><br>
	
<h2>Stampa Moduli Macelli</h2>
<br>
<p align="justify">Stampe Moduli contiene la lista dei moduli disponibili, per ognuno dei quali previsto 
l'inserimento di una data nel campo apposito e un bottone Genera Modulo che permette di visualizzare il pdf.</p>
Specifiche Modulo 1 - Modello Idatidosi:

<ul>
	<li>Il modulo sarà cumulativo: raccoglierà i dati di tutti gli animali;</li>
	<li>Il modulo sarà compilato per gli animali di una singola azienda, passati in una seduta di 
		macellazione, che risultano positivi all'Echinococco;</li>
	<li>Per animale positivo all'Echinococco verranno selezionati:</li>
	<ul>
		<li>"Libero consumo con sequestro organi" in "Evidenza Visita PM -&gt; Visita Post Mortem -&gt; Esito",</li>
		<li>"Fegato" in "Evidenza Visita PM -&gt; Visita Post Mortem -&gt; Organi -&gt; Organo",</li>
		<li>"Echinococco" in "Evidenza Visita PM -&gt; Visita Post Mortem -&gt; Organi -&gt; Lesione anatomopatologica".</li>
	</ul>
</ul>
<br> Specifiche Modulo 2 - Modello Marchi:

<ul>
	<li>Il modulo sarà cumulativo: raccoglierà i dati di tutti gli animali;</li>
	<li>Il modulo sarà compilato per gli animali di una singola  azienda arrivati al macello in una determinata 
		data ed aventi "Tipo di non conformità" rilevata in "Comunicazioni Esterne".</li>
</ul>
<br> Specifiche Modulo 3 - Modello Animali Infetti:

<ul>
	<li>Il modulo sarà cumulativo: raccoglierà i dati di tutti gli animali;</li>
	<li>Il modulo sarà compilato per gli animali di una singola azienda, passati in una seduta di macellazione, 
		e verrà replicato per ogni animale affetti da TBC o BRC;</li>
	<li>Il modulo sarà compilato per tutti gli animali per i quali in "Controllo Documentale -&gt; Animale" verrà 
		selezionato TBC o BRC per "Controllo Documentale -&gt; Animale -&gt; Macellazione Differita piani risanamento".</li>
</ul>
<br> Specifiche Modulo 4 - Modello Animali Gravidi:

<ul>
	<li>Il modulo sarà cumulativo: raccoglierà i dati di tutti gli animali;</li>
	<li>Il modulo sarà compilato per gli animali di una singola azienda, passati in una seduta di macellazione;</li>
	<li>Il modulo sarà compilato per tutti gli animali per i quali sarà 
selezionato "Utero Gravido" in "Evidenza Visita PM -&gt; Campioni -&gt;
		Matrice".</li>
</ul>
<br> Specifiche Modulo 5 - Modello TBC Rilevazione Macello:

<ul>
	<li>Il modulo sarà cumulativo: raccoglierà i dati di tutti gli animali;</li>
	<li>Il modulo sarà compilato per gli animali con TCB di una singola azienda, passati in una seduta di macellazione 
		e verrà replicato per ogni speditore;</li>
	<li>Il modulo verrà compilato nel caso in cui ci sia almeno un animale per il quale si sia selezionato "TBC" in 
		"Controllo Documentale -&gt; Animale -&gt; Macellazione Differita piani risanamento";</li>
	<li>Gli organi colpiti corrisponderanno agli organi sequestrati(per 
inserire gli organi sequestrati bisogna selezionare "Evidenza
		Visita PM -&gt; Visita Post Mortem -&gt; Esito -&gt; Libero consumo 
con sequestro organi"). Nel modulo verranno inseriti solo gli organi
		sequestrati e aventi lesioni relative alla TBC;</li>
	<li>Organi/sangue prelevati corrisponderanno ai campioni (Evidenza 
Visita PM -&gt; Campioni). Nel modulo verranno inseriri solo i campioni
		con Molecole/Agente Etiologico relativo a TBC.</li>
</ul>
<br> Specifiche Modulo 6 - Modello BRC Rilevazione Macello:

<ul>
	<li>Il modulo sarà cumulativo: raccoglierà i dati di tutti gli animali;</li>
	<li>Il modulo sarà compilato per gli animali con BRC di una singola azienda, passati in una seduta di macellazione 
		e verrà replicato per ogni speditore;</li>
	<li>Il modulo verrà compilato nel caso in cui ci sia almeno un animale per il quale si sia selezionato "BRC" in 
		"Controllo Documentale -&gt; Animale -&gt; Macellazione Differita piani risanamento";</li>
	<li>Organi/sangue prelevati corrisponderanno ai campioni (Evidenza 
Visita PM -&gt; Campioni). Nel modulo verranno inseriti solo i campioni
		con Molecole/Agente Etiologico relativo a BRC.</li>
</ul>
<br> Specifiche Modulo 7 - Modello TBC 10/33:

<ul>
	<li>Il modulo sarà legato ad un singolo capo;</li>
	<li>Il modulo sarà compilato per l'animale con sequestro organi e organo con TBC di una singola azienda, passato in una seduta di
		macellazione;</li>
	<li>Il modulo verrà compilato per ogni capo dell'azienda nel caso in 
cui ci sia almeno un animale con "Evidenza Visita PM -&gt; Organi -&gt;
		qualsiasi" e Lesione anatomopatologica -&gt; TBC" dopo aver 
selezionato "Libero consumo con sequestro organi" in "Evidenza Visita PM
 -&gt; Visita
		Post Mortem -&gt; Esito".</li>
</ul>
<br> Specifiche Modulo 8 - Modello Evidenze Ante Mortem:

<ul>
	<li>Il modulo sarà legato ad un singolo capo;</li>
	<li>Il modulo sarà compilato per l'animale di una singola azienda 
arrivato al macello in una determinata data ed aventi non conformità;</li>
	<li>Il modulo verrà compilato per ogni capo dell'azienda nel caso in 
cui ci sia almeno un animale con Non conformità a cui potrà/potranno
		essere associato/i uno (o pi provvedimento (i) adottato/i in "Evidenza
 Visita AM" e relative note.</li>
</ul>
<br> Specifiche Modulo 9 - Modello Antecedente la Macellazione:

<ul>
	<li>Il modulo sarà legato ad un singolo capo;</li>
	<li>Il modulo sarà compilato per l'animale di una singola azienda arrivato al macello in una determinata data e 
		morto antecedentemente la macellazione;</li>
	<li>Il modulo verrà compilato per ogni capo dell'azienda nel caso in cui ci sia almeno un animale morto antecedentemente 
		la macellazione per cui stata.</li>
</ul>
<br> Specifiche Modulo 10 - Modello LEB:

<ul>
	<li>Il modulo sarà legato ad un singolo capo;</li>
	<li>Il modulo sarà compilato per gli animali con LEB di una singola azienda, passato in una seduta di macellazione;</li>
	<li>Il modulo verrà compilato per ogni capo dell'azienda nel caso in 
cui ci sia almeno un animale per il quale sia selezionato "LEB" in
		"Controllo Documentale -&gt; Animale -&gt; Macellazione Differita 
piani risanamento".</li>
</ul>
<br> Specifiche Modulo 11 - Modello Disinfezione Mezzi di
Trasporto:

<ul>
	<li>Il modulo sarà cumulativo: raccoglierà i dati di tutti gli animali;</li>
	<li>Il modulo sarà compilato per gli animali infetti da BRC/TBC di una singola azienda, passati in una seduta di macellazione;</li>
	<li>Il modulo verrà compilato nel caso in cui ci sia almeno un animale 
per il quale si sia selezionato "BRC" o "TBC" in "Controllo
		Documentale -&gt; Animale -&gt; Macellazione Differita piani 
risanamento" e conterrà le informazioni relative all'automezzo (tipo, 
		targa e se si tratta di un trasporto superiore a 8 ore) se specificate
 in "Controllo Documentale -&gt; Identificazione Mezzo di Trasporto".</li>
</ul>
Gestione Progressivo<br>
Per ciascun documento, viene gestito un progressivo generato dal sistema
che unico per ASL (del macello) e si azzera ogni anno.<br> Sarà
quindi nel formato: NUMERO/ASL/ANNO; se il documento viene generato pi
volte, tale progressivo non cambierà ad eccezione del caso in cui siano
state apportate modifiche al documento (es. modifica razza/specie di uno
dei capi coinvolti, modifica del nome speditore, ecc...): in tal caso il
sistema si "accorge" della modifica e genera un nuovo progressivo,
indicando che il documento attuale sostituisce il documento con il
vecchio progressivo. 

<div class="fine" style="height: 50px;">&nbsp;</div>

<a name="mu"></a> <br>
<h1>Macellazioni Uniche</h1>
<br>
<p style="text-align: justify" dir="ltr">


</p><h2>1. Introduzione</h2>
<br>
Con l'introduzione della nuova gestione del modulo delle macellazioni, 
il processo di macellazione e' stato suddiviso in più steps, questo allo 
scopo di facilitare gli utenti nell'inserimento delle informazioni dei 
capi e di macellazione.<br>
Inoltre, sono state unificate le macellazioni bovine e ovicaprine: e' 
stato introdotto, infatti, per tutte le specie il concetto di partita.<br>
<img src="images/home.png" style="max-width:800px"><br>
<i>Illustrazione 0: Home macellazioni uniche</i>
<br clear="left"><br><br>

I passi attraverso che un utente dovrà seguire per procedere alla macellazione di un capo sono i seguenti:
<ol>
<li>Creazione partita</li>
<li>Definizione di una seduta di macellazione</li>
<li>Inserimento delle informazioni di macellazione per i singoli capi della seduta (o un sottogruppo di essi)</li>
</ol>
Nel seguito del documento saranno descritti nel dettaglio i passi precedenti.<br><br>

<h2>2. Creazione partita</h2>
<br>
Con la creazione di una nuova partita l'operatore registra l'arrivo al 
macello di uno o più animali, anche di specie diversa: e' possibile 
infatti la creazione di partite miste, con un numero arbitrario di 
animali.<br>
Per procedere alla creazione della partita e' necessario accedere al tab "Macellazione Unica" dal dettaglio del Macello.<br>
La prima pagina mostrerà tutte le seduta in corso di macellazione, con 
un primo dettaglio sintetico sugli animali inclusi e sullo stato 
complessivo <br>
<img src="images/listasedute.png" style="max-width:800px"><br>
<i>Illustrazione 1: Home macellazioni: lista sedute</i>
<br clear="left"><br><br>

Da questa pagina sarà possibile procedere con l'inserimento di una nuova
 partita, cliccando sul bottone "Aggiungi Partita". Si aprirà una 
maschera nella quale sarà necessario procedere all'inserimento di tutti i
 dati che rientrano nella fase di "Controllo Documentale".<br>
<img src="images/aggiungipartita.png" style="max-width:800px"> <br>
<i>Illustrazione 2: Inserimento Partita : dati controllo documentale</i>
<br clear="left"><br><br>

Questi dati saranno comuni a tutta la partita.<br><br>

La seconda fase dell'inserimento prevede la definizione degli animali 
costituenti la partita: e' necessario definire almeno un animale, di 
qualunque specie, per procedere al salvataggio. Non esiste nessun limite
 superiore al numero di animali facenti parte di una partita.<br>
E' necessario in questa fase specificare se sono arrivati al macello uno
 o più animali deceduti. Questa informazione e' necessaria sia per i capi
 di tipo Bovino, quindi con matricola, che per quelli non dotati di 
marca auricolare. <br><br>
<img src="images/aggiungicapi.png" style="max-width:800px"> <br>
<i>Illustrazione 3: Inserimento Partita : inserimento informazioni capi</i>
<br clear="left"><br><br>

E' necessario notare che, per ogni specie, la somma dei capi giunti 
deceduti e dei capi viventi deve corrispondere al totale dei capi 
arrivati al macello per quella determinata specie.<br>
Solo per i capi bovini, quindi dotati di marca auricolare, e' necessario 
inserire informazioni aggiuntive quali la matricola, la categoria, il 
sesso, la data di nascita, eventuali condizioni particolari.<br>
Inserite tutte le informazioni necessarie al controllo documentale e le 
informazioni sui capi componenti la partita si può cliccare sul pulsante
 "Inserisci Partita" per procedere al salvataggio delle informazioni nel
 sistema. I dati saranno disponibili in seguito per la visualizzazione.<br>
I capi facenti parte della partita, ad esclusione di quelli deceduti, 
saranno disponibili per l'inserimento in una seduta di macellazione 
(Vedi paragrafo 3).<br><br>

<h2>3. Definizione di una seduta di macellazione</h2>
<br>
Nel sistema il termine "seduta di macellazione" identifica un insieme di
 animali macellati nella stessa data (seduta). Gli animali possono 
essere di specie diversa e l'esito della macellazione può essere diverso
 per i singoli capi della seduta.<br>
Per procedere alla definizione di una seduta, l'utente dalla home page 
delle macellazioni (Fig. 1.0) deve cliccare sul pulsante "Aggiungi 
seduta". Si aprirà una maschera in cui inserire le informazioni della 
seduta, consistenti principalmente nella data (di macellazione) e nella 
lista degli animali componenti la seduta (Fig 4). Il sistema permette di
 inserire sedute con date pregresse e date future<br><br>
<img src="images/aggiungiseduta.png" style="max-width:800px"> <br>
<i>Illustrazione 4: Creazione seduta</i>
<br clear="left"><br><br>

E' opportuno notare che ad una seduta e' possibile aggiungere capi 
provenienti anche da partite (vedi paragrafo 1) differenti. Nella 
maschera di creazione seduta, infatti, sarà disponibile la lista delle 
partite per le quali esistono ancora capi non assegnati precedentemente a
 nessuna seduta di macellazione. Per aggiungere capi da una determinata 
partita e' necessaria cliccare sul corrispondente pulsante "Aggiungi capi
 da questa partita". Si aprirà una maschera con l'elenco dei capi ancora
 disponibili Fig. 5.<br><br>
<img src="images/sedutacapi.png" style="max-width:800px"> <br>
<i>Illustrazione 5: Aggiunta seduta: inserimento capi</i>
<br clear="left"><br><br>

Da questa maschera sarà possibile selezionare i capi secondo diverse modalità:
<ol>
<li>Singolarmente</li>
<li>Per specie</li>
<li>Un numero definito (minore del totale) di ogni specie</li>
</ol>

Nella Fig. 6 sono ad esempio selezionati:
<ul>
<li>1 capo di specie ovina
</li><li>Tutti i capi di specie caprina appartenenti alla partita</li>
<li>2 capi di specie agnelli sui 3 totali appartenenti alla partita</li>
</ul>

<img src="images/selezionecapi.png" style="max-width:800px"> <br>
<i>Illustrazione 6: Creazione seduta: esempio di selezione capi</i>
<br clear="left"><br><br>

Selezionati i capi, l'utente può cliccare sul pulsante "Conferma capi" 
per tornare alla pagina precedente. Questa conterrà ora un riepilogo 
delle informazioni sui capi inseriti Fig. 7. In questa pagina di 
riepilogo si potranno eliminare capi aggiunti erroneamente cliccando sul
 tasto apposito X.<br><br>

<img src="images/visualizzacapi.png" style="max-width:800px"> <br>
<i>Illustrazione 7: Inserimento seduta: visualizzazione informazioni inserite</i>
<br clear="left"><br><br>

Cliccando sul pulsante  "Inserisci seduta" la partita sarà 
definitivamente salvata nel sistema e l'utente sarà indirizzato alla 
pagina di inserimento delle informazioni di macellazione per i singoli 
capi, come descritto nel paragrafo 4. Cliccando sul link "Dettaglio 
Seduta" sarà possibile visualizzare i dettagli della seduta appena 
inserita  FIG.8<br><br>
<img src="images/dettaglioseduta.png" style="max-width:800px"> <br>
<i>Illustrazione 8: Dettaglio sintetico seduta</i>
<br clear="left"><br><br>

All'interno del dettaglio della seduta cliccando sul link AGGIUNGI CAPI 
si potranno aggiungere ulteriori capi alla seduta (fino a che questa non
 risulterà completa ovvero fino a che tutti i capi saranno macellati) 
come in FIG 8bis<br><br>
<img src="images/sedutapartite.png" style="max-width:800px"> <br>
<i>Illustrazione 8 bis: Dettaglio sintetico seduta</i>
<br clear="left"><br><br>

Da questa finestra si potranno sia inserire capi appartenenti a partite 
caricate precedentemente sia inserire nuove partite cliccando sul link 
apposito e seguendo la procedura di inserimento nuova partita descritta 
sopra. <br><br>

<h2>4. Inserimento informazioni di macellazione</h2>

Per procedere all'inserimento delle informazioni di macellazione e' 
necessario cliccare sul pulsante "Procedi" all'interno del dettaglio 
della seduta. Si aprirà una maschera dalla quale sarà possibile 
selezionare uno o un sottoinsieme dei capi della seduta che condividono 
le stesse informazioni di macellazione Fig. 9.<br><br>
<img src="images/macellacapi.png" style="max-width:800px"> <br>
<i>Illustrazione 9: Macellazione</i>
<br clear="left"><br><br>

Selezionati i capi per cui si vogliono inserire le informazioni di 
macellazione, il sistema permette di scegliere tra tre paths (percorsi 
di macellazione). I paths definiti nel sistema sono:<br>

<ol>
<li>Macellazione libero consumo</li>
<li>Morte ante macellazione</li>
<li>Macellazione con evidenza visita am/pm</li>
</ol>

Nel seguito viene descritto ogni path.<br><br>

<b>Macellazione libero consumo</b><br><br>
La macellazione secondo il path "Macellazione libero consumo" FIG. 10 
prevede la visita AM e la visita PM con i campi così come in figura.<br><br>
<img src="images/liberoconsumo.png" style="max-width:800px"> <br>
<i>Illustrazione 10: Libero Consumo</i>
<br clear="left"><br><br>

Sarà obbligatorio inserire almeno un veterinario addetto al controllo.<br><br>

<b>Morte ante macellazione</b><br><br>

La macellazione secondo il path "Morte ante macellazione" FIGG. 11, 12 
prevede le informazioni sulle comunicazioni eventualmente inviate e i 
dati della morte ante macellazione con i campi così come in figura.<br><br>
<img src="images/am.png" style="max-width:800px"> <br>
<br clear="left"><br>
<img src="images/am2.png" style="max-width:800px"> <br>
<i>Illustrazione 11 e 12: Morte ante macellazione</i>
<br clear="left"><br><br>

Condizione necessaria per il salvataggio dei dati sono la compilazione dei campi:
<ul>
<li>Data morte ante macellazione</li>
<li>Luogo verifica decesso morte ante macellazione</li>
<li>Descrizione causa visita ante mortem</li>
</ul> 

<h2>5. Gestione esercenti (Articolo 17)</h2>

<b>Visualizzazione</b><br><br>
La gestione degli esercenti (i destinatari delle carni) e' possibile all'interno del dettaglio di ogni seduta.<br>
Se nella seduta considerata esistono capi macellati e ancora non 
assegnati ad esercente, l'utente avrà la possibilità di accedere alla 
sezione "Gestione Esercenti (articolo 17)" cliccando sul corrispondente 
link.<br>
Si aprirà una popup che contiene la lista degli esercenti cui già sono 
stati assegnati animali macellati in quella seduta. Dalla stessa popup 
si potrà accedere alla pagina di creazione di un nuovo Articolo 17. Fig 
13.<br>
<img src="images/esercenti.png" style="max-width:800px"> <br>
<i>Illustrazione 13: Lista Esercenti</i>
<br clear="left"><br><br>

<b>Creazione nuovo articolo 17</b><br>
Cliccando sul link  aggiungi esercente si aprirà una maschera dalla 
quale sarà possibile la scelta dell'esercente e dei capi (parti intere, 
quarti o mezzene a seconda della specie)  da consegnare all'esercente 
Fig. 14.<br><br>
<img src="images/nuovoesercente.png" style="max-width:800px"> <br>
<i>Illustrazione 14: Aggiunta nuovo esercente</i>
<br clear="left"><br><br>

Dopo aver stampato l'art.17 esso risulterà visibile nella pagina 
visualizza art. 17 ma non potrà essere più modificato o eliminato.<br><br>
<img src="images/nuovoesercente2.png" style="max-width:800px"> <br>
<i>Illustrazione 14 bis: Aggiunta nuovo esercente</i>
<br clear="left"><br><br>

<b>Stampa registro macellazioni</b><br><br>

La stampa del registro delle macellazioni sarà effettuabile al livello 
di ogni seduta, quando tutti i capi appartenenti a questa risultano 
macellati ed assegnati ad esercente.<br><br>
Dalla maschera di dettaglio della seduta, a macellazione completata, 
sarà disponibile un link che ne permetterà la stampa Fig. 15.<br><br>
<img src="images/registro.png" style="max-width:800px"> <br>
<i>Illustrazione 15: Bottone Registro di Macellazione</i>
<br clear="left"><br><br>

Il registro delle macellazioni conterrà tutti gli animali macellati in 
quella seduta, visualizzandone per ognuono, eventualmente, lo stato di 
decesso ante macellazione.


<p></p>


<div class="fine" style="height: 50px;">&nbsp;</div>








<a name="stampa_verbali_prelievo"></a>
<h1>Prenotazione Campioni SIGLA</h1>
<br>
<p style="text-align: justify">
Per la condivisione dei dati sui campioni tra GISA-SIGLA sono stati gestiti i seguenti scenari:
</p><ul>
	<li>CASO 1: inserimento controllo ufficiale in GISA e generazione (con stampa) del verbale di prelievo.</li>
	<li>CASO 2: generazione (e stampa) del verbale da GISA prenotando il campione.</li>
</ul>
Il caso 1 già presente in GISA mentre il caso 2 possibile testarlo in 
ambiente DEMO seguendo la guida, riportata in questo documento. 
Tramite il cavaliere apposito Prenotazione Campioni SIGLA, lutente 
puscegliere loperatore presso cui andrà ad eseguire il campione e 
simularne 
l'inserimento in GISA. Il campione, inserito in questa fase, senza 
essere agganciato ad alcun controllo ufficiale, risulta nello stato 
prenotato e lutente 
potrà recuperarlo successivamente, quando dispone di tutti i dettagli 
necessari per agganciarlo definitivamente al controllo 
e vorrà sistemare le sue pratiche cartacee.
<br><br>
<u>PASSO 1: RICERCA OPERATORE</u>
<br><br>
Posizionarsi sul modulo Prenotazione Campioni SIGLA e ricercare l'operatore a cui si vuole associare il campione.
<br><br>
<u>PASSO 2: CREARE CAMPIONE FITTIZIO</u>
<ul>
	<li>Selezionare il modulo di interesse desiderato  (obbligatorio)</li>
	<li>Selezionare il modulo di interesse desiderato  (obbligatorio)</li>
	<li>Inserire le informazioni relative al campione (facoltative)</li>
	<li>Click su Prenota Campione</li><li>
&nbsp;</li></ul>
In tale scenario il numero del verbale viene generato automaticamente 
dal sistema ed assegnato al campione.
Per quanto riguarda la scelta dei Tipi di Analisi, nel caso in cui esse 
non siano inserite o differiscano dal modulo di interesse <br>
(es. si vuole fare un esame batteriologico, ma si vuole stampare un 
verbale di prelievo chimico), verrà visualizzato un messaggio che 
avvertirà dell' "anomalia". 
comunque possibile continuare con l'operazione.
<br>
<br>
<img src="images/pren1.png" height="367" width="637" border="0"> <br clear="LEFT">
<br>
<br>
<u>PASSO 3: STAMPA VERBALE</u>
Dopo aver prenotato il campione si viene rimandati al riepilogo dei dati inseriti. 
<br>
<br>
<img src="images/pren2.png" height="367" width="637" border="0"> <br clear="LEFT">
<br>
<br>
In tale schermata possibile procedere alla stampa del verbale (bottone 
Stampa Verbale) che conterrà le informazioni inserite in fase di 
prenotazione come ad esempio la Motivazione, la Matrice, ecc.
<br>

<br>
<h2>ASSOCIAZIONE CAMPIONE PRENOTATO AD UN CONTROLLO UFFICIALE</h2>
<br><br>
<u>PASSO 1: RICERCA OPERATORE</u>
<br>
<br>
Selezionare il cavaliere di interesse (es. Stabilimenti Registrati 852) e ricercare l'operatore desiderato.<br> 
Nella schermata di dettaglio presente un nuovo elemento nel menu laterale, Campioni Prenotati. 
Con la selezione di tale elemento verrà visualizzato l'elenco dei campioni fittizi, prenotati per 
quell'operatore e che possono essere associati ad un controllo ufficiale.
<br>
<br>
<img src="images/pren3.png" height="367" width="637" border="0"> <br clear="LEFT">
<br>
<br>
<u>PASSO 2: ASSOCIA CAMPIONE FITTIZIO</u>
<br><br>
Nella schermata dei campioni prenotati possibile procedere all'associazione selettiva dei campioni a 
determinati controlli ufficiali.<br>
Esistono vincoli per l'associazione del campione al controllo ufficiale.
 Infatti se per un campione in fase di prenotazione non stata 
specificata la motivazione, possibile associare tale campione a tutti i 
controlli ufficiali aperti, 
ovviamente relativi all'operatore selezionato.<br>
Invece nel caso in cui durante la fase di prenotazione stata specificata una motivazione, gli scenari possibili sono 2 : 
<ul>
	<li>possibile associare il campione  esistono controlli con la stessa motivazione.</li>
	<li>non possibile associare il campione  non esistono controlli con la stessa motivazione.</li>
</ul>
Qualora si decida di associare un campione X ad un controllo Y basterà 
selezionare il Controllo Ufficiale dall'insieme delle possibile scelte 
(menu a tendina), spuntare la checkbox di Azione associata e cliccare su
 Importa dati selezionati; verrà mostrato quindi l'elenco dei 
campioni prenotati aggiornato.
<br>
<br>
<img src="images/pren4.png" height="367" width="637" border="0"> <br clear="LEFT">
<br>
<br>
<u>PASSO 3: COMPLETA DATI DEL CAMPIONE</u>
<br>
<br>
<ul>
	<li>Selezionare il controllo ufficiale di riferimento</li>
	<li>Entrare nel dettaglio del campione desiderato</li>
	<li>Click su Completa Campione</li> 
	<li>Inserire le informazioni necessarie</li>
	<li>Click su Aggiorna</li>
</ul>
Durante tale fase  il campo Motivazione comprende oltre alle motivazioni
 relative al controllo ufficiale i casi di : Classificazione, Sospetta 
Contaminazione, 
Sospetta Malattia Infettiva/Parassitaria, Sospetto Presenza N.C., 
Sospetto Trattamento Farmacologico. 
<br><br>

<h2>GENERAZIONE AUTOMATICA NUMERO VERBALE</h2>
<br><br>
Se ad un controllo ufficiale viene aggiunto un campione che non proviene
 dal contesto SIGLA ora possibile far generare al sistema il numero del 
verbale. <br>
La generazione automatica del numero verbale possibile sfruttarla solo se tra i Tipi di Analisi vi almeno un'analisi di tipo 
Batteriologico  o Chimico.<br>
Per procedere in tal senso :
<ul>
	<li>Ricercare l'operatore di interesse</li>
	<li>Entrare nella schermata di dettaglio del controllo ufficiale desiderato</li>
	<li>Click su Inserisci Campione</li>
	<li>Inserire le informazioni necessarie</li>
	<li>Selezionare Genera Numero Verbale se si desidera un numero di 
verbale automatico oppure Inserisci Numero Verbale se si desidera 
specificare il numero del verbale in maniera autonoma.</li>
</ul>	
Nella figura sottostante, riportato un esempio della gestione del numero del verbale. <br>
Selezionando la checkbox Genera Verbale Prelievo verrà generato in automatico il numero del verbale 
mentre selezionando Inserisci Verbale Prelievo possibile specificare, nella casella sottostante, 
il numero del verbale. 
<br>
<br>
<img src="images/pren5.png" height="367" width="637" border="0"> <br clear="LEFT">
 
<div class="fine" style="height: 50px;">&nbsp;</div>

<a name="registro_trasgressori"></a>
<h2>Registro trasgressori</h2>
E' possibile accedere tramite questo cavaliere al registro dei trasgressori dell'anno in corso e degli anni precedenti. <br><br>
I requisiti che un controllo deve rispettare affinche' figuri nel registro trasgressori sono:
<i>
<ul>
<li> Presenza di una non conformità grave con sanzione amministrativa o sequestro di tipo amministrativo</li>
<li> Stato del controllo CHIUSO o RIAPERTO</li>
</ul>
</i>

Alcuni campi vengono popolati automaticamente con i dati presenti nei 
controlli ufficiali, altri invece dovranno essere popolati manualmente.<br>
Di seguito elencati i campi precompilati automaticamente dal controllo:<br>
<ul> 
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
Una volta compilati i campi e' possibile stampare il pdf relativo cliccando su "PDF registro per l'anno corrente".<br>
Per visualizzare i registri trasgressori degli anni precedenti cliccare 
su "archivio" e si aprirà una scheda come quella riportata in figura 
nella 
quale e' possibile scegliere l'anno di interesse,successivamente cliccare
 su "invia".<br>
<img src="images/Registro_trasgr.PNG" name="Registro_trasgr.png" height="222" width="595"><br>
Un esempio di registro trasgressori e' possibile visualizzarlo cliccando sull'immagine qui sotto<br>
<a href="#" onclick="openPopup('images/Registro trasgressori/Registro_trasgr_2.png') ">
<img src="images/Registro_trasgr_2.PNG" name="Registro_trasgr_2.bmp" width="300"></a><br>
NOTA : <b> Per i registri degli anni precedenti e' possibile solo la visualizzazione ma non la modifica/inserimento dati</b><br>
<div class="fine" style="height: 50px;">&nbsp;</div>

<a name="documents"></a>
<h1>Reperibilita' Asl</h1>
<br>
<p style="text-align: justify">Da oggi possibile visualizzare per
ogni asl l'elenco delle reperibilità. La visualizzazione di tali elenchi
possibile a partire dal cavaliere “Bacheca” selezionare il
link "Elenco reperibilità Asl" <br>
<br>
<img src="images/imgasl1.bmp" name="lista_operatori.bmp" height="367" width="637" align="LEFT" border="0"> <br clear="left">
<br>
<br>
<img src="images/imgasl2.bmp" name="add_operatore_abusivo.bmp" height="367" width="637" align="LEFT" border="0"> <br clear="LEFT">
<br> Ogni utente potra' visualizzare la lista (in effetti sarà
possibile visualizzare i file inseriti dalle Asl con tutte le
informazioni utili) e le reperibilità di tutte le Asl pur non facendone
parte. Cliccando sull'Asl interessata, l'utente autorizzato potrà
aggiungere o cancellare i file presenti.
<br> Cliccando sul link Allega lista Reperibilità 
<br>
<br>
<img src="images/imgasl3.bmp" name="add_cu_ps.bmp" height="167" width="637" align="LEFT" border="0"> <br clear="left">
<br> e selezionato il file da caricare, inserendo il testo per
loggetto ..
</p>
<br>
<br>
<img src="images/imgasl4.bmp" name="add_operatore_abusivo.bmp" height="267" width="637" align="LEFT" border="0"> <br clear="left">
<br> .. si avrà la pubblicazione delle informazioni volute. 
<br>
<br>
<img src="images/imgasl5.bmp" name="lista_operatori.bmp" height="167" width="637" align="LEFT" border="0"> <br clear="left"><br>

<div class="fine" style="height: 50px;">&nbsp;</div>

<a name="unita_crisi"></a><a>
<h1>Unità di crisi</h1>
<br> L’Unità di Crisi Regionale per unificare la
risposta sanitaria nell’eventualità di maxiemergenze
causate da eventi terroristici di natura NBCR o da eventi naturali od
antropici.
<br> In particolare nel modulo predisposto in GISA e'
presente un sorta di organigramma, riportante l'elenco dei
responsabili suddivisi per ambito di competenza. 

<div class="fine" style="height: 50px;">&nbsp;</div>

<a name="pratiche_suap"></a><a>
<h1>Pratiche SUAP e Gestione Stabilimenti</h1>
<br>Per la gestione delle anagrafiche, in GISA sono presenti tre cavalieri:
<li>Pratiche SUAP</li>
<li>Anagrafica Stabilimenti</li>
<li>Altri Stabilimenti</li>
<br>
<img src="images/scia_01.png" name="scia_01.png" height="267" width="637" align="LEFT" border="0"> <br clear="left">
<br><b>Pratiche SUAP (Richieste)</b><br>
<br>
<img src="images/scia0.png" name="scia0.png" height="267" width="637" align="LEFT" border="0"> <br clear="left">
<br>Accedendo al cavaliere, sono disponibili le funzioni :
<li>SCIA da validare: e' presente il bottone "Apri e Valida" per aprire il dettaglio della scheda e proseguire con la validazione.
<li>Ricerca: e'  possibile cercare solo nelle richieste.
<li>Gestione SCIA: per inserire nuove SCIA fisse,  SCIA mobili, apicolture nomadi e stanziali  e Riconoscimenti (questi ultimi di competenza regionale). 
<br>
<br>
<img src="images/scia_03.png" name="scia_03.png" height="267" width="637" align="LEFT" border="0"> <br clear="left">
<br>
<br><b>Gestione richieste attivita': Scia fisse e mobili</b><br>
<br>Cliccando sul bottone  "SCIA ATT. FISSE"  sara' possibile inserire le richieste  per uno stabilimento che ha il tipo di attivita' "FISSA".<br>
Viceversa, cliccando sul bottone "SCIA ATT. MOBILI" verra'  filtrato automaticamente il tipo di attivita' "MOBILE".<br>
In entrambi i casi, al clic sul bottone, si avvia una popup (come in figura), dove viene richiesto di specificare la data di arrivo della PEC dalla camera di commercio e di selezionare il comune per cui si intende operare. <br>
Il campo comune si riferisce allo stabilimento.<br> 
In caso di impresa mobile il comune si riferira' all'impresa.<br>
Nel caso di impresa mobile di tipo individuale, in cui non e' previsto ne' lo stabilimento ne' la sede legale, il comune sara' quello di residenza del legale rappresentante.
<br>
<br>
<img src="images/scia6.png" name="scia6.png" height="267" width="637" align="LEFT" border="0"> <br clear="left">
<br>
<br><b>Inserimento richiesta attivita' FISSA</b><br>
<br>
<img src="images/scia_02.png" name="scia_02.png" height="267" width="637" align="LEFT" border="0"> <br clear="left">
<br>In questa situazione l'utente dovra' specificare il tipo di operazione che vuole eseguire, selezionando una delle opzioni disponibili tra queste:
<li>INSERIMENTO NUOVO STABILIMENTO (2.1) 
<li>AGGIUNTA DI UNA O PIU' LINEE DI ATTIVITA' (2.3) 
<li>VARIAZIONE TITOLARITA' DI STABILIMENTO (2.4)
<li>MODIFICA ALLO STATO DEI LUOGHI (2.3)
<li>CHIUSURA STABILIMENTO O DI UNA O PIU' LINEE DI ATTIVITA'(2.5) <br>
Scelta l'operazione da effettuare, si potra' procedere al completamento tramite una maschera   guidata.
<br><br><b>NOTA</b><br>Si potrebbe evidenziare un messaggio di <b>"ATTENZIONE"</b> nel caso ci fosse un momentaneo  funzionamento lento della rete.<br>
In tal caso non sara' possibile allegare documenti ma sara' possible continuare l'inserimento dei dati.
<br><br>
<br><b>Inserimento nuovo stabilimento (2.1)</b><br>
<br>Supponiamo di aver selezionato la prima opzione. Di seguito i passi guidati per l'inserimento.<br>
<br>PASSO 1: Selezionare il tipo di impresa (Associazione, cooperativa, ente pubblico, impresa individuale, societa' di capitale, societa' di persone ) e cliccare il bottone "Avanti"
<img src="images/scia_1.png" name="scia_1.png" height="267" width="637" align="LEFT" border="0"> <br clear="left">
<br>
<br>
PASSO 2: Inserire i dati impresa e del rappresentante legale. Cliccare su "Avanti".
<br>
<img src="images/scia5.png" name="scia5.png" height="350" width="700" align="LEFT" border="0"> <br clear="left">
<br>
PASSO 3: Inserire i dati dello stabilimento. Se il carattere dell'attivita' e' "TEMPORANEO", verra' richiesto di specificare la data di Inizio e Fine attivita'. Cliccare su "Avanti".
<br>
<img src="images/scia7.png" name="scia7.png" height="267" width="637" align="LEFT" border="0"> <br clear="left">
<br>
PASSO 4: Selezionare una o piu' linee di attivita'. Tutte le linee sono recuperate dalla Master List .  Cliccare su "Avanti".
<br>
<img src="images/scia8.png" name="scia8.png" height="267" width="637" align="LEFT" border="0"> <br clear="left">
<br>
PASSO 5:  Dopo aver scelto le linee di attivita', seguira' l'inserimento dei vari documenti nella sezione "Allegati" se la linea di attivita' lo prevede.<br>
(Per gli allegati leggere attentamente il successivo paragrafo "verifica validita' documentazione allegata" ). <br>
Cliccando  su "Salva Pratica" e poi "Avanti" si completera' l'inserimento. <br>
<br>
PASSO 6: Viene visualizzato il dettaglio della scheda nella sezione "Riepilogo". Sara' possibile guardare la scheda in pdf e stamparla tramite l'apposito bottone "Stampa".
<br>
<img src="images/scia9.png" name="scia9.png" height="267" width="637" align="LEFT" border="0"> <br clear="left">
<br>
<br>
<br><b>Verifica validita' documentazione allegata</b><br>
<br>In fase di inserimento di una pratica SUAP,  il sistema chiedera' di allegare dei documenti, in base alla linea di attivita' e al tipo di operazione selezionata.<br>
Se per il comune per il quale si sta inserendo la SCIA e' previsto il controllo sugli allegati, nella maschera in cui si richiede di effettuare l'inserimento sara' mostrata una checkbox, flaggata ma modificabile, con il seguente messaggio "Tutti gli allegati firmati digitalmente". <br><br> 
<img src="images/scia10.png" name="scia10.png" height="267" width="637" align="LEFT" border="0"> <br clear="left">
<br>
Al salvataggio della pratica, tramite l'apposito bottone "Salva pratica", parte la verifica del formato caricato.<br>
In caso di documento NON firmato digitalmente, sara' mostrato a video il messaggio riportato nella figura sottostante: sara' possibile togliere il flag e salvare.<br>
<img src="images/scia11.png" name="scia11.png" height="267" width="637" align="LEFT" border="0"> <br clear="left">
<br>
<br><br><b>Aggiunta di una o piu' linee di attivita' (2.3.a)</b><br>
<br>Nel caso in cui si voglia procedere ad aggiungere una o piu' linee d'attivita' svolte nello stabilimento o ad apportare significative modifiche allo stato dei luoghi dello stabilimento, occorre una nuova notifica/SCIA secondo le procedure descritte al paragrafo 2.1 della delibera.<br>
In GISA, per le attivita' che richiedono SCIA e per il Riconoscimento, si dovra' cliccare il bottone mostrato in figura.<br>
<br>
<img src="images/scia12.png" name="scia12.png" height="267" width="637" align="LEFT" border="0"> <br clear="left">
<br>
<br>I passi da effettuare sono simili a quelli descritti nel paragrafo "Inserimento nuovo stabilimento (2.1)".
<br>In particolare:
<br>PASSO 1: Selezionare il tipo di impresa
<br>PASSO 2: Inserire i dati impresa e del rappresentante legale
<br>PASSO 3: Inserire i dati dello stabilimento. <u>In questa pagina e' richiesto l'inserimento facoltativo del num. di registrazione.</u>
<br>PASSO 4: Aggiunta di una o piu' linee di attivita'
<br>PASSO 5: Inserimento dei vari documenti nella sezione "Allegati" se la linea di attivita' lo prevede.
<br>PASSO 6: Dettaglio della scheda nella sezione "Riepilogo" ed eventuale stampa
<br>
<br><br><b>Variazione titolarita' di stabilimento (2.4)</b><br>
<br>Nel caso l'impresa titolare dello stabilimento sia variata, e' a carico dell'impresa subentrante l'obbligo di procedere ad una nuova SCIA secondo le procedure descritte al paragrafo 2.1.
<br>La presentazione della SCIA e' sufficiente anche per assolvere all'obbligo, teoricamente a carico dell'impresa cedente, della dovuta comunicazione in merito all'avvenuta cessazione dell'attivita'. 
<br>Si specifica che non viene considerata come variazione di impresa il cambio del rappresentante legale di una societa' di capitali ("s.r.l.", "s.p.a.", "s.a.p.a.") o di una cooperativa, in quanto non cambia la denominazione sociale.<br>
Viceversa il cambio del rappresentante legale di una societa' di persone ("societa' semplice", "s.a.s" e "s.n.c.") comporta l'obbligo dell'effettuazione di nuova SCIA, in quanto rappresenta una variazione della ragione sociale. 
<br>La presentazione della nuova SCIA comporta l'archiviazione dello stabilimento dell'impresa cessante. Nel sistema informatico GISA essa viene sostituita dalla scheda dello stabilimento condotto dalla nuova impresa.
<br>In GISA, l'operazione risulta possibile cliccando sul bottone "Variazione titolarita' di stabilimento" come mostrato in figura:<br>
<br>
<img src="images/scia13.png" name="scia13.png" height="267" width="637" align="LEFT" border="0"> <br clear="left">
<br>
<br>I passi da effettuare sono simili a quelli descritti nel paragrafo "Inserimento nuovo stabilimento (2.1)".
<br>In particolare:
<br>PASSO 1: Selezionare il tipo di impresa
<br>PASSO 2: Inserire i dati impresa e del rappresentante legale
<br>PASSO 3: Inserire i dati dello stabilimento. <u>In questa pagina viene richiesto di identificare lo stabilimento da cessare, tramite PARTITA IVA e/o numero di registrazione e poi si prosegue secondo lo standard..</u>
<br>PASSO 4: Inserimento di una o piu' linee di attivita'
<br>PASSO 5: Inserimento dei vari documenti nella sezione "Allegati" se la linea di attivita' lo prevede.
<br>PASSO 6: Dettaglio della scheda nella sezione "Riepilogo" ed eventuale stampa
<br>
<br><br><br><b>Modifica allo stato dei luoghi/layout (2.3.b)</b><br>
<br>Il pulsante "Modifica allo stato dei luoghi/layout" in fase di inserimento nuova pratica per attivita' con sede fissa, ha come fine il caricamento di un file che rappresenta la nuova planimetria dello stabilimento.
<br>
<img src="images/scia14.png" name="scia14.png" height="267" width="637" align="LEFT" border="0"> <br clear="left">
<br>
<br>I passi da effettuare sono simili a quelli descritti nel paragrafo "Inserimento nuovo stabilimento (2.1)".
<br>In particolare:
<br>PASSO 1: Selezionare il tipo di impresa
<br>PASSO 2: Inserire i dati impresa e del rappresentante legale
<br>PASSO 3: Inserire i dati dello stabilimento.
<br>PASSO 4: Inserimento di una o piu' linee di attivita'
<br>PASSO 5: Inserimento nella sezione "Allegati" <u>di un file della planimetria.</u>
<br>PASSO 6: Dettaglio della scheda nella sezione "Riepilogo" ed eventuale stampa
<br>
<br><br><br><b>Chiusura stabilimento o linea di attivita' (2.5)</b><br>
<br>Nel caso l'impresa voglia procedere alla cessazione definitiva dello stabilimento o voglia cessare una o piu' linee di attivita' per le quali lo stabilimento e' gia' registrato, ha l'obbligo di comunicarlo al SUAP.<br>
Le ASL con le modalita' consentite in GISA, dovranno registrare l'operazione di chiusura o cessazione dello stabilimento.
<br>
<img src="images/scia15.png" name="scia15.png" height="267" width="637" align="LEFT" border="0"> <br clear="left">
<br>
<br>I passi da effettuare sono simili a quelli descritti nel paragrafo "Inserimento nuovo stabilimento (2.1)".
<br>In particolare:
<br>PASSO 1: Selezionare il tipo di impresa
<br>PASSO 2: Inserire i dati impresa e del rappresentante legale
<br>PASSO 3: Inserire i dati dello stabilimento.
<br>PASSO 4: Inserimento <u> data di cessazione</u> per una o piu' linee di attivita'. <br>
<u>Se si cessano tutte le linee di attivita' viene automaticamente chiuso lo stabilimento.</u>
<br>PASSO 5:  Inserimento dei vari documenti nella sezione "Allegati" se la linea di attivita' lo prevede.
<br>PASSO 6: Dettaglio della scheda nella sezione "Riepilogo" ed eventuale stampa
<br>
<br><b>NOTA</b><br>Se non si intende cessare tutto lo stabilimento ma solo una linea, assicurarsi di specificare la linea di attivita' corretta.
<img src="images/scia16.png" name="scia16.png" height="267" width="637" align="LEFT" border="0"> <br clear="left">
<br><br>
<b>VALIDAZIONE</b>
<br>Una volta provveduto all'inserimento, la nuova "scia" sara' visibile nella home page di pratiche suap e sara' da validare.
<br>Si potra' ricercare la scia inserita attraverso la P.IVA, la denominazione e cliccare sul tasto "APRI E VALIDA"<br>
<img src="images/apri e valida.png" name="apri e valida.png" height="267" width="637" align="LEFT" border="0"> <br clear="left">
<br><br>
<br>Si aprira' la scheda ed in alto il bottone "prosegui" sul quale bisogna cliccare per avviare la validazione.
<img src="images/prosegui.png" name="prosegui.png" height="267" width="637" align="LEFT" border="0"> <br clear="left">
<br>
<br>Dal dettaglio della richiesta e' anche previsto un menu laterale "Allegati SCIA", in cui e' visibile la lista di tutti gli allegati alla pratica, in base alla linea di attivita' specificata.<br>
<img src="images/allegati_scia.png" name="allegati_scia.png" height="267" width="637" align="LEFT" border="0"> <br clear="left">
<br>La pagina successiva, conterra' i dati riepilogativi della scia e una o piu' linee di attivita' evidenziate con il colore giallo.<br>
<br>
<img src="images/valida.png" name="valida.png" height="267" width="637" align="LEFT" border="0"> <br clear="left">
<br><b>NOTA</b><br>Possono essere validate o respinte tutte o parte delle linee di attivita'. <br>
<br>
<br>Con il tasto "valida", si inserisce il num. ministeriale, le eventuali note e si salva. <br>
(Il numero ministeriale o in generale una codifica differente da quella regionale, viene richiesta dal sistema in base alla attivita' specificata)<br>
<br>
<img src="images/approval number.png" name="approval number.png" height="267" width="637" align="LEFT" border="0"> <br clear="left"><br>
Sara' necessario "chiudere e ricaricare la scheda" (vedi figura precedente) per completare l'operazione. <br>
La linea di attivita' sara' adesso di colore verde.<br> 
NON  si visualizzera' piu' il tasto "prosegui" nella scheda.<br>
Sara' emesso un numero di registrazione univoco per le singole linee di attivita'.
<br>
<br><b>ATTENZIONE</b> Se la validazione non va a buon fine, sara' evidenziato il motivo di tale problema in rosso dopo aver premuto il tasto valida.<br> 
<br>Da questo momento, la scia non sara' piu' presente in "Pratiche Suap" ma sara' visibile nel cavaliere "Anagrafica Stabilimenti".<br>
<br>
<a name="Anagrafica Stabilimenti"></a><a>
<h1>Anagrafica Stabilimenti</h1> 
Accedendo al cavaliere "Anagrafica Stabilimenti", l'utente puo' ricercare o importare  uno stabilimento.<br>
<br><b>NOTA</b>: <u>Si potra' ricercare una impresa di nuova registrazione o gia' presente in qualsiasi altro cavaliere</u> (ad eccezione del cavaliere "stabilimenti archiviati" che contiene le imprese per le quali non e' possibile fare alcuna operazione ma solo visionarle).<br>
<br>I tasti visibili nella parte alta sono: "ricerca" , "importa stabilimento da vecchia anagrafica" e "aggiungi stabilimento pregresso"
<br><img src="images/Anagrafica0.png" name="Anagrafica0.png" height="267" width="637" align="LEFT" border="0"> <br clear="left"><br>
<br><b>RICERCA</b>
<br>Con il tasto "ricerca" si visualizza la pagina dove vi sono numerosi campi di selezione. 
<br>Piu' filtri saranno compilati, piu' veloce  sara' la ricerca:
<li>Ricerca per Impresa: in questo campo e' possibile fare una ricerca nella nuova anagrafica o nella vecchia anagrafica (qualsiasi altro cavaliere), per P.IVA, ragione sociale...<br>
</li>
<li>Ricerca per Stabilimento sede operativa (ASL, Via, Comune,...)</li>
<li>Ricerca per linea di attivita'</li>
<li>Ricerca per legale rappresentante (nome, cod. fiscale)</li>
<br>
<br><b>Ricerca per impresa</b><br>
<br><img src="images/Anagrafica1.png" name="Anagrafica1.png" height="267" width="637" align="LEFT" border="0"> <br clear="left"><br>
I campi di ricerca nel settore legato all'impresa (vedi fig. sopra), possono essere:<br>
<br>Cerca in nuova anagrafica > la selezione di questo punto, fa in modo che la ricerca avvenga in tutte le imprese inserite nella nuova anagrafica.
<br>Selezionando questo tasto si visualizzano le scelte "registrabile" / "riconoscibile" "controlli per allerta" nel settore relativo allo stabilimento. <br>
Sara' possibile scegliere se l'impresa e' registrabile (in tal caso si puo' inserire come ulteriore filtro, il numero di registrazione univoco emesso in sede di validazione scia)
o riconoscibile (in tal caso si puo' inserire come ulteriore filtro, il numero di riconoscimento/CUN/Cod. Azienda manualmente)
<br>Si attiva anche il campo di ricerca "per allerte" dove e' possibile inserire il numero di allerta (aperta o chiusa)<br>
<br>Cerca in vecchia anagrafica/Operatori non soggetti a SCIA > la selezione di questo punto fa in modo che la ricerca avvenga in tutte le imprese inserite in qualsiasi cavaliere.
<br>Selezionando questo campo, si visualizzano come ulteriori scelte, il campo "nome/ditta/ragione sociale" e il campo "P.IVA"<br>
La targa sara' inserita come filtro solo se la sede dell'impresa e' mobile e non fissa.<br>
<br>
<br><b>Ricerca per stabilimento (sede operativa)</b><br>
<br><img src="images/Anagrafica2.png" name="Anagrafica2.png" height="267" width="637" align="LEFT" border="0"> <br clear="left"><br>
I campi di ricerca nel settore legato allo stabilimento (vedi fig. sopra), possono essere:<br>
<br>Il campo ASL > permette di restringere il campo di ricerca scegliendo l'ASL relativa allo stabilimento; se non si conosce il dato, restera' scritto "tutte" per fare una ricerca in tutte le ASL.<br>
<br>Gli altri campi di ricerca utilizzabili riguardano > il Comune e l'indirizzo qualora siano conosciuti.<br>
<br>Numero di registrazione > numero univoco emesso in sede di registrazione per le nuove o vecchie imprese. <br>
<br>Numero di riconoscimento/CUN/Cod. Azienda > numero che viene inserito manualmente dall'utente che valida. <br>
<br>
<br><br><b>Ricerca per linea di attivita'</b><br>
<br><img src="images/Anagrafica4.png" name="Anagrafica4.png" height="267" width="637" align="LEFT" border="0"> <br clear="left"><br>
Il campo di ricerca nel settore legato alla linea di attivita' (vedi fig. sopra) e' legato alle attivita' inserite.<br>
<br>Sara' possibile ricercare fino al terzo livello di linea di attivita'.<br>
<br><br><b>Ricerca per legale rappresentante</b><br>
<br><img src="images/Anagrafica5.png" name="Anagrafica5.png" height="267" width="637" align="LEFT" border="0"> <br clear="left"><br>
I campi di ricerca nel settore legato al legale rappresentante (vedi fig. sopra) possono essere:<br>
<br>Nominativo > Per inserire il nome e cognome del legale rappresentante. <br>
<br>Codice Fiscale > Per inserire il codice del legale rappresentante.<br>
<br>
<br>Cliccando sul tasto "RICERCA" si avvia la ricerca dell'impresa.
<br>Cliccando sul tasto "PULISCI" si annullano i dati inseriti come filtri ricerca.<br>
<br><br><b>Scheda impresa ricercata</b><br>
<br>Cliccando sull'impresa trovata, si puo' entrare nel dettaglio della scheda e si aprira' la pagina che riporta tutte le notizie immesse in fase di inserimento (dati relativi alla impresa, stabilimento, lista linee produttive). <br>
<img src="images/Anagrafica3.png" name="Anagrafica3.png" height="267" width="637" align="LEFT" border="0"> <br clear="left"><br>
<br>A destra della immagine, si possono individuare dei tasti (le specifiche dei Controlli Ufficiali, sono da leggere nel relativo paragrafo):
<li><u>Aggiungi controlli ufficiali</u> dove sara' possibile inserire un nuovo controllo sull'impresa. 
<br>I campi fissi che si visualizzeranno sono quelli relativi alla propria ASL ed al nome dell'impresa. Tutti gli altri campi dovranno essere inseriti.(vedi paragrafo Controlli Ufficiali)</li>
<li><u>Controlli ufficiali</u> dove sara' possibile leggere i controlli gia' aperti/chiusi per l'impresa trovata. Anche in questa pagina si potranno aggiungere nuovi controlli.</li>
<li><u>Allegati</u> per inserire la documentazione che "accompagna" l'impresa ed archiviarla; si possono creare nuove cartelle e caricare nuovi file. 
<li><u>Documenti PDF </u> per visualizzare l'elenco dei documenti inseriti.
<li><u>Storico richieste </u> dove sono riportate le notizie operative e le eventuali variazioni che riguardano l'impresa.<br> 
<br>
<br><b>IMPORTA DA VECCHIA ANAGRAFICA</b><br>
<img src="images/Anagrafica6.png" name="Anagrafica6.png" height="267" width="637" align="LEFT" border="0"> <br clear="left"><br>
<br> In questa pagina e' possibile recuperare una impresa ed importarla nella nuova anagrafica.<br>
<br>L'operazione puo' essere fatta SOLO dall'Help Desk di primo e secondo livello o dal Funzionario Regionale che provvederanno all'importazione in maniera forzata.<br> 
<br>
<br><b>IMPORTA STABILIMENTO PREGRESSO</b><br>
<br>
<img src="images/Anagrafica7.png" name="Anagrafica7.png" height="267" width="637" align="LEFT" border="0"> <br clear="left">
Oltre alla gestione SCIA, per attivita' fisse e mobili, esiste una gestione degli stabilimenti che non prevede SCIA. 
In questo campo, e' possibile aggiungere lo stabilimento pregresso, ovvero uno stabilimento gia' presente sul territorio ma che non e' stato mai registrato in GISA .<br>

<!--   
<br>
Nella pagina di Default, sono presenti tre menu laterali:
<li>Pratiche da validare</li>
<li>Pratiche non accolte (precedentemente archiviate con un motivo)</li>
<li>Pratiche evase (pratiche già validate)</li>
<br>
Cliccando su "Apri e valida", si viene rimandati ad una pagina di dettaglio della pratica in cui e' presente il bottone "Prosegui", come mostrato nella immagine sottostante. 
<br>
<br>
<img src="images/scia2.png" name="scia2.png" height="267" width="637" align="LEFT" border="0"> <br clear="left">
Tramite questo bottone e' possibile validare la richiesta, cliccando sul bottone “Valida” nella popup che viene mostrata.
<br>
<br>
<img src="images/scia3.png" name="scia3.png" height="267" width="637" align="LEFT" border="0"> <br clear="left">
<br>
Per tornare indietro, alla schermata di dettaglio dove e' presente il bottone "Prosegui", basta cliccare sul bottone "CHIUDI E RICARICA SCHEDA PRATICA".
Dal dettaglio della richiesta e' anche previsto un nuovo menu laterale "Allegati SCIA", in cui e' visibile la lista di tutti gli allegati alla pratica, in base alla linea specificata.
Un esempio e' mostrato nella figura che segue:
<br>
<br>
<img src="images/scia4.png" name="scia4.png" height="267" width="637" align="LEFT" border="0"> <br clear="left">
<br>
<div class="fine" style="height: 50px;">&nbsp;</div>
-->
</a><a name="Apicoltura"></a>
<h1>Apicoltura</h1>
<br>
A partire da questo modulo &egrave; possibile ricercare gli apiari presenti sul territorio regionale.
Il modulo consente di :
<ul>
<li>Gestire Deleghe</li>
<li>Gestione Richieste</li>
<li>Gestire le Anagrafiche Apiari</li>
<li>Gestire Operazioni Apiari</li>
<li>Ricercare apiari</li>
<li>Getire Controlli Ufficiali</li>
</ul>
<br>
<br>


<h2>Ricercare apiari</h2>
<br>
Cliccando sul modulo Apicoltura, il sistema mostrer&agrave; la maschera di ricerca degli apiari relativi all'asl di appartenenza dell'utente.
I possibili filtri che si possono applicare sono i seguenti : 
<ul>

<li>Apicoltore In Regione : di default &egrave; spuntata la voce SI (ricerca tutti gli apiari di apicoltori resedenti in regione)</li>
<li>Denominazione</li>
<li>Codice Fiscale Proprietario</li>
<li>Cun</li>
</ul>

<br>
<br>
<img src="images/ricerca_api.PNG" name="ricerca_api.PNG" height="267" width="637" align="LEFT" border="0"> <br clear="left">


<h2>Gestione Deleghe</h2>
<br>
Il modulo Gestione Deleghe consente all'asl di eseguire determinate operazioni su apiari per conto di un delegante.
Cliccando su Gestione Deleghe si accede alla sezione dove vengono 
elencate tutte le deleghe in carico all'operatore ASL loggato.
<img src="images/gestione_deleghe.PNG" name="gestione_deleghe.PNG" height="267" width="637" align="LEFT" border="0"> <br clear="left">
 
 Cliccando su "Nuova Delega" si pu&ograve; inserire una nuova delega compilando
 gli appositi campi all'interno della finestra mostrata nel seguito.
 
<br>
<img src="images/nuova_delega.PNG" name="nuova_delega.PNG" height="267" width="637" align="LEFT" border="0"> <br clear="left">
<br>
Dopo aver compilato tutti i campi, cliccando sul pulsante Salva il sistema ricaricher&agrave; la
pagina mostrando la delega inserita.
Cliccando sul pulsante "Seleziona Codice Fiscale Corrente" &egrave; possibile eseguire le operazioni sugli apiari
dell'apicoltore di cui l'asl &egrave; delegata : 
in particolare se esiste l'attivit&agrave; associata all'apicoltore il sistema mostrer&agrave; la maschera di dettaglio con la lista degli apiari presenti.
Nel caso in cui l'attivit&agrave; non &egrave; presente nel sistema , viene mostrata la maschera di aggiunta. 

<br>
<br>
<h2>Aggiungi Impresa</h2>
<br>
Cliccando sul link AGGIUNGI, dopo aver selezionato la delega, il sistema
ci guida nella finestra per l'inserimento dei dati della nuova impresa.
Dopo aver compilato tutti campi cliccando sul tasto "salva", il 
sistema ci guida alla schermata riepilogativa dell'impresa di 
apicoltura.
<br>
<img src="images/DettaglioImpresa.png" name="DettaglioImpresa.png" height="267" width="637" align="LEFT" border="0"> <br clear="left">
Nel dettaglio si presta particolare attenzione allo stato 
sincronizzazione che rester&agrave; di colore rosso fino a che l'impresa non 
verr&agrave; validata dal personale ASL.
<br>
<br>
<h2>Aggiungi Apiario</h2>
<br>
Sia per una nuova impresa di apicoltura sia per una contenente gi&agrave; n 
apiari, la procedura per l'inserimento di un nuovo apiario e' la stessa: 
cliccando su NUOVO APIARIO si aprir&agrave; la finestra per la compilazione dei
dati dell'apiario.
<br>
<br>
<img src="images/NuovoApiario.png" name="NuovoApiario.png" height="267" width="637" align="LEFT" border="0"> <br clear="left">
<br>Per un detentore gi&agrave; anagrafato, il codice fiscale pu&ograve; essere 
selezionato da una lista che  lo recupera; viceversa, cliccando sul 
tasto "Inserisci persona" e compilando tutti i dati richiesti, il 
sistema ci permette di registrare  i dati del detentore come nuovi.
<br>
<br>
<h2>Gestione Richieste</h2>
<br>
Tutte le pratiche inserite dagli apicoltori
in BD-api Regionale all'atto della validazione saranno dotate di codice
univoco nazionale attribuito dall'ASL di competenza (asl del comune di residenza dell'apicoltore)
e scelto a partire da quelli che i
servizi BDN daranno disponibili sul comune di competenza. 
Contestualmente alla validazione mediante attribuzione CUN avverr&agrave; 
inserimento dell'azienda apiaria in BDN.
Le imprese con almeno un apiario vengono inserite nell'elenco
gestioni richieste, come mostrato nella figura sottostante.
<br>
<br>
<img src="images/GestioneRichieste.png" name="GestioneRichieste.png" height="267" width="637" align="LEFT" border="0"> <br clear="left">

<br>L'operatore ASL di competenza pu&ograve; validare le imprese visibili in
elenco cliccando sul tasto VALIDA Apri modello A". Si ricorda che
l'impresa comparir&agrave; nell'elenco delle imprese da validare solo se essa
ha almeno un apiario collegato. Nella popup per la validazione,
cliccando sul tasto VERIFICA ESISTENZA IN BDN si pu&ograve; verificare se
esiste gi&agrave;  un codice azienda assegnato in precedenza per il codice
fiscale del delegante.
<br>
<br>
<img src="images/ValidaImpresa.png" name="ValidaImpresa.png" height="267" width="637" align="LEFT" border="0"> <br clear="left">
<br>Se per il codice fiscale selezionato non esiste un codice azienda 
associato, &egrave; possibile assegnarne uno nuovo a scelta tra un elenco 
selezionabile all'interno della sezione DAMMI UN NUOVO CODICE. All'atto 
dell'assegnazione del CUN, sar&agrave; messo a disposizione del Servizio 
Veterinario l'eventuale codice azienda assegnato in precedenza e 
recuperato da BDN e/o GISA in base alla corrispondenza con il COMUNE di 
residenza del PROPRIETARIO. A quel punto il Servizio Veterinario potr&agrave; 
scegliere di confermare il precedente codice come CUN.

<br>
<br>
<img src="images/VerificaEsistenzaBDN.png" name="VerificaEsistenzaBDN.png" height="267" width="637" align="LEFT" border="0"> <br clear="left">

<br>Cliccando quindi nel campo in corrispondenza di SELEZIONA CODICE DA 
LISTA comparir&agrave; una lista di codici che &egrave; possibile assegnare alla data 
azienda.
<br>
<br>
<img src="images/SelezionaCodiceNazionale.png" name="SelezionaCodiceNazionale.png" height="267" width="637" align="LEFT" border="0"> <br clear="left">
<br>
Cliccando sul codice desiderato e quindi poi su SALVA, il sistema 
riporta al riepilogo delle informazioni dell'impresa appena validata.
Si ricorda in particolare che gli apicoltori a cui in  BD-api Regionale &egrave;
 stato gi&agrave; attribuito un codice regionale dovranno essere di nuovo 
validati, allo stesso modo dei nuovi inserimenti, allo scopo di eseguire
 l'attribuzione anche a loro del codice univoco nazionale. Inoltre, le 
aziende di apicoltura, gi&agrave; presenti in Gisa, poiche' derivano da un 
recupero dati che ha generato informazioni parziali e incomplete, 
saranno messe in uno stato cosiddetto "off-line" e si dovr&agrave; per loro 
reinserire a carico dell'apicoltore o del suo delegato mediante la 
procedura standard basata sulla richiesta tramite Modello A inoltrata 
via BD-api Regionale.
<br>
<br>
<h2>CENSIMENTI APIARI</h2>
L'utente asl dopo aver selezionato una delega dal modulo gestione deleghe,
puo variare la consistenza del numero di alveari presenti in un apiario (l'apiario deve essere stato validato a partire dal modulo gestione richieste).
Il censimento &egrave; attivo solo in alcuni periodi dell'anno (da ottobre a dicembre)
<br>

<h2>NOMADISMO</h2>
L'operazione di nomadismo consente di eseguire gli spostamenti di alveari da un apiario verso un altro appartenente allo stesso apicoltore.
Cliccando sul pulsante nomadismo il sistema presenter&agrave; la maschera di inserimento.
nella prima parte della maschera viene chiesto all'utente la data di uscita degli alveari e la data del modello di accompagnamento.
Nella seconda parte vengono visualizzati i dati dell'apiario da cui gli alveari escono.
L'apiario di destinazione viene ricercato tramite il comune.
N.B. L'apiario di destinazione deve esistere nel sistema e deve essere sincronizzato con la banca dati nazionale.
Ogni movimento di nomadismo deve essere validata.
<br>

<h2>COMPRAVENDITA</h2>
L'operazione di compravendica consente di di eseguire spostamenti di alveari di un apicoltore 
verso un apiario appartenente a un altro apicoltore.
L'apicoltore di destinazione deve essere presente nel sistema e deve essere identificato mediante il codice azienda.
Ogni movimentazione di tipo compravendita deve essere validata.
<br>

<h2>GESTIONE RICHIESTE MOVIMENTAZIONI</h2>
A partire da questo sottomodulo vengono visualizzate tutte le richieste di movimentazioni per l'asl di competenza.
Tramite il pulsante valida , le movimentazioni vengono inserite nella banca dati nazionale.

NOTA. ogni tipo di movimentazione non aggiorna la consistenza degli apiari.
La consistenza viene aggiornata una volta l'anno mediante la funzione Censimento.
<br>

<h2>MOVIMENTAZIONI</h2>
Le operazioni di movimentazioni comprendono "Nomadismo" e "Compravendita".
<br>
<a name="video"></a>
<h2>Video tutorial</h2>
In questa sezione e' possibile visualizzare le videoguide per alcune 
delle funzionalit&agrave; presenti in Gisa.
Basta cliccare sul bottone relativo alla funzionalit&agrave; che ci interessa 
quindi si aprir&agrave; il video corrispondente in una nuova finestra.<br>



<br><br>
Inserimento controlli ufficiali per il cavaliere "autorità competenti".<br>
<input value="AUTORITÀ COMPETENTI - AUDIT INTERNO" onclick="openPopup('http://www.gisacampania.it/video/Audit_interno.mp4');return false" type="button"><br>

<input value="AUTORITÀ COMPETENTI - SIMULAZIONE PER SUPERVISIONE" onclick="openPopup('http://www.gisacampania.it/video/Simulazione_per_supervisione.mp4');return false" type="button"><br>
Inserimento Non conformità a carico e non a carico del soggetto ispezionato.<br>
<input value="INSERISCI NC." onclick="openPopup('http://www.gisacampania.it/video/inserisci_non_conformita_rilevata.mp4');return false" type="button"><br>
<input value="NC. A CARICO DI TERZI" onclick="openPopup('http://www.gisacampania.it/video/nc_a_carico_di_terzi.mp4');return false" type="button"><br>

Inserimento sanzione e visualizzazione di quest'ultima nel registro trasgressori.<br>

<input value="REGISTRO TRASGRESSORI" onclick="openPopup('http://www.gisacampania.it/video/Registro_trasgressori.mp4');return false" type="button"><br>

In questi video vedremo come stampare un verbale per un campione inserito o prenotato.<br>
<input value="STAMPA VERBALE CAMPIONE" onclick="openPopup('http://www.gisacampania.it/video/StampaVerbale6.mp4');return false" type="button"><br>
<input value="STAMPA VERBALE CAMPIONE PRENOTATO" onclick="openPopup('http://www.gisacampania.it/video/StampaVerbalePrenota2.mp4');return false" type="button">



</div><!-- chiusura div principale corpo guida -->

		<div id="aside">
			
<a href="#" onclick="openPopup('images/infografica1.png')"><img src="images/infografica1.png" width="200"></a>
<br>
<a href="#" onclick="openPopup('images/infografica2.png')"><img src="images/infografica2.png" width="200"></a>	
		</div>
		<div id="footer">
			
		</div>
	</div>
</div></body></html>