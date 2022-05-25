<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<html>
<head>
<title>
Guida Utente</title>
</head>
<body bgcolor="F5F5D1">
<p>
Stampe Moduli contiene la lista dei moduli disponibili, per ognuno dei quali è previsto l'inserimento di una data nel campo apposito e un bottone Genera Modulo che permette di visualizzare il pdf.
</p>
<b>Specifiche Modulo 1 - Modello Idatidosi:</b>
<ul>
<li>Il modulo sarà cumulativo: raccoglierà i dati di tutti gli animali;</li>
<li>Il modulo sarà compilato per gli animali di una singola azienda, passati in una seduta di macellazione, che risultano positivi all'Echinococco;</li>
<li>Per animale positivo all'Echinococco verranno selezionati:</li>
	<ul>
		<li>"Libero consumo con sequestro organi" in "Evidenza Visita PM -> Visita Post Mortem -> Esito",</li>
		<li>"Fegato" in "Evidenza Visita PM -> Visita Post Mortem -> Organi -> Organo",</li>
		<li>"Echinococco" in "Evidenza Visita PM -> Visita Post Mortem -> Organi -> Lesione anatomopatologica".</li>
	</ul>
</ul>
<br>
<b>Specifiche Modulo 2 - Modello Marchi:</b>
<ul>
<li>Il modulo sarà cumulativo: raccoglierà i dati di tutti gli animali;</li>
<li>Il modulo sarà compilato per gli animali di una singola azienda arrivati al macello in una determinata data ed aventi "Tipo di non conformità" rilevata in "Comunicazioni Esterne".</li>
</ul>
<br>
<b>Specifiche Modulo 3 - Modello Animali Infetti:</b>
<ul>
<li>Il modulo sarà cumulativo: raccoglierà i dati di tutti gli animali;</li>
<li>Il modulo sarà compilato per gli animali di una singola azienda, passati in una seduta di macellazione, e verrà replicato per ogni animale affetti da TBC o BRC;</li>
<li>Il modulo sarà compilato per tutti gli animali per i quali in "Controllo Documentale -> Animale" verrà selezionato TBC o BRC per "Controllo Documentale -> Animale -> Macellazione Differita  piani risanamento".</li>
</ul>
<br>
<b>Specifiche Modulo 4 - Modello Animali Gravidi:</b>
<ul>
<li>Il modulo sarà cumulativo: raccoglierà i dati di tutti gli animali;</li>
<li>Il modulo sarà compilato per gli animali di una singola azienda, passati in una seduta di macellazione;</li>
<li>Il modulo sarà compilato per tutti gli animali per i quali sarà selezionato "Utero Gravido" in "Evidenza Visita PM -> Campioni -> Matrice".</li>
</ul>
<br>
<b>Specifiche Modulo 5 - Modello TBC Rilevazione Macello:</b>     
<ul>
<li>Il modulo sarà cumulativo: raccoglierà i dati di tutti gli animali;</li>
<li>Il modulo sarà compilato per gli animali con TCB di una singola azienda, passati in una seduta di macellazione e verrà replicato per ogni speditore;</li>
<li>Il modulo verrà compilato nel caso in cui ci sia almeno un animale per il quale si sia selezionato "TBC" in "Controllo Documentale -> Animale -> Macellazione Differita  piani risanamento";</li>
<li>Gli organi colpiti corrisponderanno agli organi sequestrati (per inserire gli organi sequestrati bisogna selezionare "Evidenza Visita PM -> Visita Post Mortem -> Esito ->  Libero consumo con sequestro organi"). Nel modulo verranno inseriti solo gli organi sequestrati e aventi lesioni relative alla TBC;</li>
<li>Organi/sangue prelevati corrisponderanno ai campioni (Evidenza Visita PM -> Campioni). Nel modulo verranno inseriri solo i campioni con Molecole/Agente Etiologico relativo a TBC.</li>
</ul>
<br>
<b>Specifiche Modulo 6 - Modello BRC Rilevazione Macello:</b>
<ul>
<li>Il modulo sarà cumulativo: raccoglierà i dati di tutti gli animali;</li>
<li>Il modulo sarà compilato per gli animali con BRC di una singola azienda, passati in una seduta di macellazione e verrà replicato per ogni speditore;</li>
<li>Il modulo verrà compilato nel caso in cui ci sia almeno un animale per il quale si sia selezionato "BRC" in "Controllo Documentale -> Animale -> Macellazione Differita  piani risanamento";</li>
<li>Organi/sangue prelevati corrisponderanno ai campioni (Evidenza Visita PM -> Campioni). Nel modulo verranno inseriti solo i campioni con Molecole/Agente Etiologico relativo a BRC.</li>
</ul>
<br>
<b>Specifiche Modulo 7 - Modello TBC 10/33:</b>
<ul>
<li>Il modulo sarà legato ad un singolo capo;</li>
<li>Il modulo sarà compilato per l'animale con sequestro organi e organo con TBC di una singola azienda, passato in una seduta di macellazione;</li>
<li>Il modulo verrà compilato per ogni capo dell'azienda nel caso in cui ci sia almeno un animale con "Evidenza Visita PM -> Organi -> qualsiasi" e Lesione anatomopatologica -> TBC"  dopo aver selezionato "Libero consumo con sequestro organi" in "Evidenza Visita PM -> Visita Post Mortem -> Esito".</li>
</ul>
<br>
<b>Specifiche Modulo 8 - Modello Evidenze Ante Mortem:</b>
<ul>
<li>Il modulo sarà legato ad un singolo capo;</li>
<li>Il modulo sarà compilato per l'animale di una singola azienda arrivato al macello in una determinata data ed aventi non conformità;</li>
<li>Il modulo verrà compilato per ogni capo dell'azienda nel caso in cui ci sia almeno un animale con Non conformità a cui potrà/potranno essere associato/i uno (o più) provvedimento (i) adottato/i  in "Evidenza Visita AM" e relative note.</li>
</ul>
<br>
<b>Specifiche Modulo 9 - Modello Antecedente la Macellazione:</b>
<ul>
<li>Il modulo sarà legato ad un singolo capo;</li>
<li>Il modulo sarà compilato per l'animale di una singola azienda arrivato al macello in una determinata data e morto antecedentemente la macellazione;</li>
<li>Il modulo verrà compilato per ogni capo dell'azienda nel caso in cui ci sia almeno un animale morto antecedentemente la macellazione per cui è stata.</li>
</ul>    
<br>
<b>Specifiche Modulo 10 - Modello LEB:</b>
<ul>
<li>Il modulo sarà legato ad un singolo capo;</li>
<li>Il modulo sarà compilato per gli animali con LEB di una singola azienda, passato in una seduta di macellazione;</li>
<li>Il modulo verrà compilato per ogni capo dell'azienda nel caso in cui ci sia almeno un animale per il quale sia selezionato "LEB" in "Controllo Documentale -> Animale -> Macellazione Differita piani risanamento".</li>
</ul>
<br>
<b>Specifiche Modulo 11 - Modello Disinfezione Mezzi di Trasporto:</b>
<ul>
<li>Il modulo sarà cumulativo: raccoglierà i dati di tutti gli animali;</li>
<li>Il modulo sarà compilato per gli animali infetti da BRC/TBC di una singola azienda, passati in una seduta di macellazione;</li>
<li>Il modulo verrà compilato nel caso in cui ci sia almeno un animale per il quale si sia selezionato "BRC" o "TBC" in "Controllo Documentale -> Animale -> Macellazione Differita  piani risanamento" e conterrà le informazioni relative all'automezzo (tipo, targa e se si tratta di un trasporto superiore a 8 ore) se specificate in "Controllo Documentale -> Identificazione Mezzo di Trasporto".</li>
</ul>   

  

    

    

     
   

   

    
    

     
    

    

    

    

    


    


  

    

    

    

    

</body>
</html>