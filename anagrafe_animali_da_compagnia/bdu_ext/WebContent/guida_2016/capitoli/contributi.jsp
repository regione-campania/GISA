<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<a name="Contributi Sterilizzazioni"></a>
<h3 id="Contributi Sterilizzazioni">Contributi Sterilizzazioni</h3>

					<br>

					<H2>Introduzione</H2>

					La pratica per il pagamento dei contributi relative alle
					sterilizzazioni dei cani, vede coinvolti tre tipologie di utenti
					presenti in Anagrafe: gli amministratori delle asl e i comuni da una parte e l'utente
					Regione dall'altra. Asl e comune sono coloro i quali si occupano di inviare le
					richieste per il pagamento dei contributi (l'asl per le pratiche per canili e il comune per le pratiche per i comuni limitatamente ai cani sterilizzati nel proprio comune).
					La Regione invece si occupa di approvare o respingere le pratiche di pagamento
					ricevute. L'applicativo presenta un insieme di progetti
					aperti per il pagamento del contributo di sterilizzazione, che
					vengono inseriti, da un utente con ruolo "Utente Regione". Ogni
					modifica da apportare al progetto deve essere supportata da una
					richiesta alla regione la quale eventualmente, dopo opportuni
					controlli, provvederà ad aprire una segnalazione all'help desk
					stesso nel caso di modifiche. <br> All'atto di
					una registrazione di un cane /gatto in anagrafe, è possibile
					inserire i dati relativi alla sterilizzazione e se è stato
					richiesto per esso il pagamento del contributo. Oppure,
					successivamente all'inserimento, è possibile effettuare una
					registrazione di sterilizzazione, indicando chi ha sterilizzato il
					cane/gatto, la data e se per esso è stato richiesto il pagamento
					del contributo. Per i veterinari privati la sterilizzazione è possibile solo se si seleziona il progetto in cui essa rientra.
					Gli LP possono inserire la sterilizzazione in fase di anagrafe animale oppure, se il cane è già anagrafato, mediante il cavaliere 'Sterilizzazioni'.
					Dopo aver inserito tale informazione, non sarà
					assolutamente possibile, modificare la data e il progetto del
					contributo.<br> <br>
					<H2>Progetti di sterilizzazione</H2>
					<br> I progetti di sterilizzazione vengono inseriti solo ed
					esclusivamente dagli utenti con ruolo Utente Regione. Ogni progetto
					prevede le seguenti informazioni:<br> <br>
					<ul>
						<li>Asl
						<li>Comuni
						<li>Oggeto del decreto
						<li>Numero del decreto
						<li>Data Decreto
						<li>Data Inzio sterilizzazione
						<li>Data fine sterilizzazione
						<li>Numero Decreto
						<li>Numero totale di cani catturati (pratiche canili)
						<li>Numero totale di cani padronali (pratiche canili)
						<li>Numero totale di gatti catturati (pratiche canili)
						<li>Numero totale di gatti padronali (pratiche canili)
						<li>Numero totale di cani maschi (pratiche per comuni)
						<li>Numero totale di cani femmina (pratiche per comuni)
						<li>Veterinari (pratiche per comuni)
					</ul>
					<br>
					<H2>Aggiunta di un animale ad un progetto</H2>
					<br> Di seguito sono indicate le modalit&agrave di aggiunta
					delle informazioni di sterilizzazioni sia per i cani che per i
					gatti.<br> <br>
					<h3>CASO 1. - INSERIMENTO DI UN CANE CATTURATO/PADRONALE CON
						STERILIZZAZIONE E RICHIESTA CONTRIBUTO</h3>
					<br> All'atto dell'inserimento di un cane, dovr&agrave essere
					selezionato in primis il proprietario e il detentore. Sulla base di
					tale informazione, nel momento in cui viene indicata la data di
					sterilizzazione, e selezionato il flag per la richiesta del
					pagamento del contributo, comparir&agrave un elenco, contenente
					tutti i progetti realizzati sull'asl di pertinenza, il cui
					intervallo di sterilizzazione comprende la data indicata e per cui
					ci sono delle posizioni aperte. All'atto dell'inserimento del cane,
					l'applicativo controller&agrave che il comune del proprietario
					coincida con un comune relativo al progetto, e che se il cane è
					catturato, vi siano posizioni disponibili come cani catturati,
					viceversa se si tratta di un padronale, vi siano posizioni aperte
					per i padronali. Altrimenti il software dar&agrave indicazioni su
					come procedere. Dopo aver inserito il cane, nella maschera di
					dettaglio, se tutto &egrave andato a buon fine, comparir&agrave la
					pratica associata al cane. Successivamente, le posizioni
					disponibili su tale progetto, non saranno più quelle iniziali, ma
					ce ne sarà una in meno. Quando tali posizioni saranno state
					occupate, e non ve ne sarà neanche una disponibile, allora il
					software avviserà l'utente di ciò.<br> <br>
					<h3>CASO 2. - INSERIMENTO DI UN GATTO
						CATTURATO/COLONIA/PADRONALE CON STERILIZZAZIONE E RICHIESTA
						CONTRIBUTO</h3>
					<br> Analogamente a quanto indicato sopra, le stesse modalità
					sono previste per l'inserimento di gatti. In particolare, i gatti
					di colonia, saranno considerati come i catturati, e pertanto
					andranno ad occupare le posizioni disponibili per i catturati.<br>
					<br>
					<h3>CASO 3. - INSERIMENTO DI UNA REGISTRAZIONE DI
						STERILIZZAZIONE E RICHIESTA CONTRIBUTO PER UN CANE/GATTO
						SUCCESSIVA ALL'INSERIMENTO</h3>
					<br> <br> Successivamente, le posizioni disponibili su
					tale progetto, non saranno più quelle iniziali, ma ce ne sarà una
					in meno, in quanto ogni qualvolta che una posizione viene occupata
					da un'animale, essa viene scalata da quelle libere. Quando tali
					posizioni saranno state occupate, e non ve ne sarà neanche una
					disponibile, allora il software avviserà l'utente di ciò. <br>
					
					<h3>CASO 4. - INSERIMENTO DI UN CANE CON STERILIZZAZIONE E RICHIESTA CONTRIBUTO PER VETERINARIO PRIVATO</h3>
					<br> All'atto dell'inserimento di un cane, deve essere
					selezionato il proprietario, il sesso e la data sterilizzazione. Sulla base di
					tali informazioni il sistema mostra l'elenco contenente
					tutti i progetti realizzati sul comune di residenza del proprietario e sul veterinario che sta inserendo la sterilizzazione, che sono aperti alla data indicata e 
					per cui ci sono ancora cani del sesso indicato da poter sterilizzare. Dopo aver inserito il cane, nella maschera di
					dettaglio viene mostrato un link per stamapre il certificato da presentare al comune. 
					Successivamente, le posizioni disponibili su tale progetto, non saranno più quelle iniziali, ma
					ce ne sarà una in meno. Quando tali posizioni saranno state
					occupate, e non ve ne sarà neanche una disponibile, allora il
					progetto non sar&agrave; pi&ugrave; utilizzabile per inserire sterilizzazioni.<br> <br>
					
					<h3>CASO 5. - INSERIMENTO DI UNA REGISTRAZIONE DI STERILIZZAZIONE PER VETERINARIO PRIVATO</h3>
					<br> <br>Per inserire una sterilizzazione su un cane gi&agrave; anagrafato nel sistema si deve usare il cavaliere 'Sterilizzazioni'.
					Inserire il microchip ne lformato valido di 15 caratteri numerici. Il cane con il microchip indicato deve avere uno stato congruo per l'inserimento della sterilizzazione (es.: non smarrito, non rubato, non sterilizzato).
					altrimenti non si pu&ograve; inserire la sterilizzazione. Dopo avere inserito il microchip il sistema mostra l'informazione relativa al comune di residenza del proprietario nel campo apposito. 
					Selezionare la data di sterilizzazione. Quando i campi microchip e data sono stati selezionati, il sistema controlla che ci sia un progetto per liberi professionisti avente i seguenti requisiti:
					- comune residenza proprietario ricade tra quelli del progetto;<br>
					- ci sono cani da sterilizzare per il sesso del cane inserito<br/>
					- il periodo di inizio e fine e progetto comprende la data inserita<br/>
					- il veterinario LP rientra tra quelli del progetto<br/>
					- il progetto è aperto e non è stato chiuso dalla Regione<br/><br/>
					
					Se c'è almeno un progetto con questi requisiti, la combo dei progetti viene popolata ed &egrave; possibile selezionarne uno per salvare la registrazione.
					Tutti i campi sono obbligatori. 


