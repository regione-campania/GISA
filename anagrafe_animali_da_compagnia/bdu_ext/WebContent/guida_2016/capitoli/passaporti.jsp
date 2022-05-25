<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<a name="Passaporti"></a>
<h3 id="Passaporti">Banca dati passaporti</h3>

<br> Il modulo "Banca dati passaporti" prevede i seguenti moduli, abilitati in base ai permessi del ruolo utente collegato al sistema: </br>

												<ul>
													<dhv:permission name="passaporto-view">
														<li>Ricerca Passaporti</li>
													</dhv:permission>
													<dhv:permission name="passaporto-edit">
														<li>Carico Passaporti da file</li>
													</dhv:permission>
													<dhv:permission name="passaporto-edit">
														<li>Carico singolo Passaporto</li>
													</dhv:permission>
													<dhv:permission name="passaporto-edit">
														<li>Scarico singolo Passaporto</li>
													</dhv:permission>
													<dhv:permission name="distribuzione_passaporti">
														<li>Distribuzione passaporti</li>
													</dhv:permission>

												</ul>

												<br></br>
												<dhv:permission name="passaporto-view">
												Attraverso il moduolo "Ricerca Passaporti" gli utenti
												accederanno a una pagina di riepilogo con la lista dei passaporti caricati, visualizzandone per
												ognuno le informazioni sulla disponibilità o sull'assegnamento.
													</dhv:permission>

												<br></br>
												<dhv:permission name="passaporto-edit">
												Attraverso il sottomuduolo "Carico Passaporti da file" l'utente può provvedere al caricamento di un insieme di valori passaporto destinati alle singole ASSLL.
												Il file deve essere in formato "CSV", formattato secondo le seguenti modalità:
												1° riga: "Numero_Passaporto","ASL" 
												2° riga e successive con i valori nr passaporto, id asl: "IT23334444333","203" 
												</dhv:permission>
												<br></br>
												<dhv:permission name="passaporto-edit">
												Attraverso il sottomodulo ""Carico singolo Passaporto" l'utente può provvedere al caricamento di un singolo valore passaporto assegnato a una determinata ASL.
												Campi obbligatori nel modulo sono l'asl assegnataria del passaporto e il valore numerico del passaporto.
												</dhv:permission>
												<br></br>
												<dhv:permission name="passaporto-edit">
												Attraverso il sottomodulo "Scarico singolo Passaporto" l'utente può provvedere alla disabilitazione di un singolo valore passaporto, rendendolo inutilizzabile per qualsiasi asl.
												</dhv:permission>
												<br></br>
												<dhv:permission name="distribuzione_passaporti-view">
												Attraverso il sottomodulo "Distribuzione passaporti", il referentre della UOC può assegnare i passaporti alle UOS della propria ASL. 
												Accedendo al modulo viene mostrato l'elenco delle UOS della propria ASL. Scegliere la UOS cliccando sul link 'Seleziona'.
												Comparirà la lista dei passaporti caricati sulla propria ASL e non ancora assegnati ad una UOS. Selezionare tramite la checkbox i passaporti desiderati e cliccare su "Associa alla UOS".
												</dhv:permission>


												</br> </br>
												<H2>Regole di assegnazione passaporto</H2>

												All'atto dell'inserimento di una registrazione di
												rilascio/rinnovo passaporto il sistema controllerà che:
												<ul>
													<li>1. la lunghezza del valore numerico inserito sia di 13 cifre</li>
													<li>2. il passaporto sia presente nella banca dati a priori e assegnato alla UOS selezionata in fase di accesso da VAM.</li>
												</ul>



												In fase di inserimento nuova anagrafica animale non sarà
												possibile l'assegnazione di passaporti il cui valore risulta precaricato nella banca dati a priori (anche se non associato ancora ad una UOS).
												Sarà quindi possibile l'assegnazione di un passaporto solo
												per gli animali provenienti da fuori regione e già dotati di
												un passaporto. Al salvataggio dei dati il sistema
												verificherà che il valore passaporto assegnato non
												appartenga alla banca dati a priori regionale, in caso
												affermativo impedirà all'utente di proseguire.