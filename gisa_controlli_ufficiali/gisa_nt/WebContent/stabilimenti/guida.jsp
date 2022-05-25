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
<body>
<p>
E' possibile inserire in gisa una richiesta per un nuovo stabilimento,
che dovr&agrave; essere completata (mediante l' inserimento del controllo documentale)
e approvata da parte dell'utente Stap. Tale gestione coinvolge i seguenti ruoli :

<ul>
<li>Ruolo Veterinari</li>
<li>Ruolo Stap</li>
<li>Ruolo NU.RE.CU</li>
</ul>

<br/><br/>

<table>
<tr>
<td><b>RUOLO VETERINARIO</b></th>
</tr>
<tr>
<td>
L'utente Veterinario potra' : 
<ul>
<li>Inserire una nuova Pratica</li>
<li>Inserire il controllo Documentale</li>
<li>Visualizzare le pratiche inserite</li>
<li>Inserire Controlli Ufficiali</li>

</ul>
<br>
E' Possibile inserire una nuova pratica mediante il link "Aggiungi Istruttoria" presente nel menu in alto
della maschera di ricerca degli stabilimenti.Lo stato dello stabilimento per il quale si sta facendo richiesta
sarà "IN DOMANDA". Dopo aver compilato tutti i campi obbligatori cliccando sul pulsante inserisci verrà inserita la 
pratica , e le verrà assegnato lo stato "Istruttoria Preliminare".
<br>
La pratica inserita dall'utente veterinario si troverà nello stato istruttoria preliminare fino a quando non sarà stata
effettuata dallo stesso utente la verifica del controllo documentale.
E' Possibile inserire la verifica dei documenti mediante il link "Controllo Documentale" presente nel menu a destra
a partire dal detaglio della pratica.Dopo aver salvato il controllo sui documenti la pratica passerà nello stato "Verifica Documentazione".
<br>
Attenzione : quanto si effettua una ricerca per una pratica occorre filtrare non solo per il campo tipo pratica ma selezionare
 anche come stato dello stabilimento "IN DOMANDA".
</td>
</tr>
</table>

<br><br><br>

<table>
<tr>
<td><b>RUOLO STAP</b></td>
</tr>
<tr>
<td>
L'utente STAP potra' : 
<ul>
<li>Visualizzare tutte le pratiche inserite dai veterinari</li>
<li>Inviare la richiesta definitiva per ottenere l'approval number da sintesi</li>
<li>Attribuire L'approval number</li>
<li>Inviare la richiesta condizionata</li>

</ul>
<br>
Per tutte le pratiche che si trovano nello stato "Verifica Documentazione" , lo stap potrà inviare 
la richiesta definitiva per ottenere l'approval number da sintesi e inserirlo in gisa , o inviare una richiesta condizionata
all'utente NU.RE.CU.
<br><br>
L'invio della richiesta definitiva è possibile mediante il pulsante "Invia Richiesta Definitiva" presente
nel dettaglio della pratica (che si trova nello stato Verifica Documentazione).
L'invio della richiesta definitiva avviene qualora lo stap non abbia riscontrato problemi nella verifica documentale,
e consente quindi l'avanzamento della pratica che passerà dallo stato "Verifica Documentazione" allo stato "Richiesta Riconoscimento Definitivo".
Nel momento in cui viene comunicato allo stap l'approval number da assegnare allo stabilimento , l'utente potrà inserirlo mediante il bottone
"attribuisci approval number".
Dopo aver assegnato il numero di registrazione la pratica passerà dallo stato "Richiesta Riconoscimento Definitivo" allo stato "Riconosciuto Provvisorio".
Per rendere lo stabilimento autorizzato , lo stap potrà modificare lo stato mediante il pulsante "Modifica Stato" che gli consentirà di rendere lo stabilimento
Autorizzato e di poter modificare lo stato dei vari impianti che compongono lo stabilimento.
Dopo aver reso lo stabilimento Autorizzato la pratica sarà stata Completata.
<br><br><br>
L'invio della richiesta condizionata è possibile mediante il pulsante "Invia Richiesta Condizionata" presente
nel dettaglio della pratica (che si trova nello stato Verifica Documentazione).
L'invio della richiesta condizionata avviene quando il controllo dei documenti non è andato a buon fine,
e di conseguenza viene richiesto alla regione un approval number condizionato da assegnare allo stabilimento.
L'approval number condizionato ha una scadenza di 3 mesi a partire dalla data di attribuzione.
Dopo aver cliccato su invia richiesta condizionata la pratica passerà dallo stato "Verifica Documentazione" allo
stato "Richiesta Riconoscimento Condizionato".
Successivamente è possibile inviare la richiesta di approval number condizionato alla regione mediante il pulsante "Invia richiesta Regione",
facendo passare la pratica nello stato "Inoltro Riconoscimento Condizionato Regione".
La regione attribuira il numero condizionato e la pratica passerà nello stato "Riconosciuto Condizionato".
Nello stato della pratica sarà indicato anche la scadenza di tale numero (tre mesi successivi alla data di assegnazione dell'approval number).
Lo stap potrà quindi inviare la richiesta definitiva (il giro è lo stesso di quello spiegato in precedenza), oppure chiedere una proroga di altri 3 mesi
(sei mesi successivi alla data di assegnazione dell'approval number). Mediante la proroga la pratica passerà dallo stato "Riconosciuto Condizionato" 
allo stato "Riconosciuto Condizionato in Proroga".
Se entro i sei mesi successivi alla data di assegnazione del numero condizionato la documentazione è stata messa a posto lo stap invierà la richiesta 
definitiva altrimenti Invierà una richiesta di revoca alla regione mediante il pulsante "Invia Richiesta revoca", facendo passare la pratica nello stato "Richiesta Revoca".
</td>
</tr>
</table>

<br><br><br>

<table>
<tr>
<td><b>RUOLO NU.RE.CU</b></td>
</tr>
<tr>
<td>
L'utente NU.RE.CU potra' : 
<ul>
<li>Visualizzare tutte le pratiche inserite dai veterinari nei vari stati</li>
<li>Attribuire il numero condizionato</li>
<li>Revocare lo stabilimento</li>


</ul>
<br>
Per tutte le pratiche che si trovano nello stato "Inoltro Riconoscimento Condizionato Regione", L'utente NU.RE.CU 
potrà assegnare l'approval number condizionato mediante il pulsante "Genera numero Condzionato" facendo avanzare la pratica nello stato "Riconosciuto Condizionato".
<br><br>
Per tutte le pratiche che si trovano nello stato "Richiesta Revoca" L'utente Nu.Re.Cu potrà effettuarne la revoca mediante il pulsante "Revoca" , completando cosi la pratica.
<br><br>

</td>
</tr>
</table>

</p>
</body>
</html>