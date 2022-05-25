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
			<a href="#" onClick="window.print()"><img alt="Stampa" src="printer-icon.png" align="middle" ></img></a>Manuale utente BDU_ext
		</h1>
		
	</div>
	
	<div id="content-container">
		<div id="section-navigation">
			<ul>
				<li><a href="#partita">Aggiungi partita</a></li>
				<li><a href="#bdu">Inserimento animali da BDU</a></li>
				<li><a href="#portale">Inserimento animali dal Portale Importatori</a></li>
				<li><a href="#chiusura">Chiusura pratica</a></li>
				<li><a href="#respinta">Pratica respinta</a></li>
			</ul>
		</div>
		<div id="content">
			
			
		

<a name="partita"></a>

<H1>Aggiungi Partita</H1>
<br>
<p  align="justify">
E' possibile registrare in BDU partite commerciali provenienti dall'estero.Per procedere all'inserimento della partita basta cliccare sul cavaliere "Partita commerciale" e successivamente sul sottomodulo "nuova partita commerciale".Si aprirà una maschera attraverso la quale l'utente potrà inserire le informazioni della partita.
Dalla stessa maschera l'utente avrà la possibilità di inviare la prenotifica della partita appena inserita all'importatore.Dopo l'invio della prenotifica l'importatore potrà visualizzare il dettaglio ed interagire con la partita dal Portale Importatori.<br>
<br>
<a target="_blank" href="http://mon.gisacampania.it/tutorial/aggiungi_partita.mp4"><br>
<span style ="font-weight:bold;">Clicca qui per visualizzare il video </span></a><br>
<br>
Gli animali da assegnare alla partita appena inserita possono essere inseriti sia da utente ASL (BDU) che da utente Importatore (Portale Importatori).<br>

<a name="bdu"></a>
<H1>Inserimento animali in una partita da BDU</H1>
<p  align="justify">
<a target="_blank" href="http://mon.gisacampania.it/tutorial/aggiungi_animale_partita_bdu.mp4"><br>
<span style ="font-weight:bold;">Clicca qui per visualizzare il video </span></a><br>
<br>
Come accennato in precedenza, il dettaglio della partita viene trasmessa al Portale Importatori, accedendo al quale, l'operatore commerciale può visualizzarne i dettagli e completare l'inserimento delle informazioni.

<a name="portale"></a>
<H1>Visualizzazione ed inserimento animali in una partita nel Portale Importatori</H1>
<p  align="justify">
<a target="_blank" href="http://mon.gisacampania.it/tutorial/aggiungi_animale_portale.mp4"><br>
<span style ="font-weight:bold;">Clicca qui per visualizzare il video </span></a><br>
<br>
Come evidenziato nel video, lista degli animali inseriti in BDU dalle ASL non viene visualizzata nel Portale Importatori:
gli importatori avranno visibilità esclusivamente sulle informazioni degli animali inseriti nel portale.<br>
Solo successivamente, quando l'ASL approva la richiesta dell'operatore, verrà visualizzata in BDU la lista completa degli animali appartenenti alla partita.
<br>
<a name="chiusura"></a>
<H1>Chiusura pratica ed invio dettaglio partita all'Asl di appartenenza</H1>
<p  align="justify">


Completato l'inserimento degli animali della partita l'importatore può chiudere la partita ed inviarla all'Asl di competenza.
Una volta inviata la pratica all'Asl di appartenenza, quest'ultima, cliccando su "richieste importatori" può visualizzare le partite e decidere se importare o respingere la pratica.
Nel video vedremo come interagiscono i due sistemi quando la partita viene importata in BDU.<br>
<a target="_blank" href="http://mon.gisacampania.it/tutorial/invia_asl_di_appartenenza.mp4"><br>
<span style ="font-weight:bold;">Clicca qui per visualizzare il video </span></a><br>
<br>
<a name="respinta"></a>
<H1>Caso di pratica respinta</H1>
<p  align="justify">
Nel caso in cui la richiesta dell'importatore viene respinta, nel Portale Importatori la partita sarà impostata in automatico nello stato "Pratica respinta"
<br>
<a target="_blank" href="http://mon.gisacampania.it/tutorial/pratica_respinta.mp4"><br>
<span style ="font-weight:bold;">Clicca qui per visualizzare il video </span></a><br>
<br>







</div>	


</div><!-- chiusura div principale corpo guida -->


		<div id="footer">
			
		</div>
	