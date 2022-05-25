<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<a name="registrazioni_animale"></a>
<h3 id="registrazioni_animale">Registrazioni Animale</h3>

					<br> Nel modulo riservato alle Registrazioni &egrave;
					possibile effettuare esclusivamente operazioni di ricerca sulle
					registrazioni presenti nel sistema, la maschera si presente nel
					seguente modo:<br> <br> <IMG
						SRC="images/registrazioni/Imm_1.PNG" NAME="registrazioni.PNG"/ >

				L'operazione di ricerca &egrave molto semplice, in quanto l'utente
				pu&ograve effettuarla sia avendo informazioni sulla registrazione
				quindi pu&ograve opportunamente valorizzare i campi:
				<ul>
					<li>Tipologia registrazione
					<li>Identificativo registrazione
					<li>Specie animale
				</ul>
				<br> sia avendo a disposizioni informazioni temporali, del
				tipo:
				<ul>
					<li>Data inserimento in BDU
					<li>Data della registrazione
				</ul>
				<br> Dopo aver effettuato la ricerca, all'utente si
				presenter&agrave la maschera in cui &egrave presente
				l'identificativo della restrazione, la tipologia della
				registrazione, data inserimento in BDU, data effettiva in cui
				&egrave stata effettuata la registrazione, proprietari e detentore
				dell'animale, microchip ed Asl di riferimento dell'animale.<br>

				<br> <IMG SRC="images/registrazioni/Imm_2.PNG"
					NAME="elenco_registrazioni.PNG"/ >
			<br>
			<H2>Tipologia registrazioni</H2>
			<br/> A seconda dello stato dell'animale, &egrave; possibile
			aggiungere opportunamente le registrazioni tra le seguenti tipologie:<br/>
			
			N.B.:Si ricorda che, se la registrazione viene messa contestualmente all'inserimento dell'accettazione di Vam, l'origine inserimento visualizzata nel dettaglio sarà Vam.<br/>
			Negli altri casi (compreso 'Accesso BDR' da Vam) l'orgine sarà BDU.
					
			<br/>
			<br/>
				<b>Adozione da canile</b><br/><br/>
				<b>Adozione fuori asl</b><br/><br/>
				<b>Presa in Carico Adozione Fuori Asl</b><br/>
				<i>N.B.: Registrazione effettuabile solo per completare le adozioni fuori asl effettuate prima del 25/05/2022 e che prevedevano la validazione dell'asl destinataria.</i>
				Successivamente all'inserimento di registrazione di 'Adozione fuori Asl' un utente appartenente all'Asl di destinazione pu&ograve; inserire </br>
				una registrazione di 'Presa in carico adozione fuori Asl'.
				<IMG SRC="images/registrazioni/presa_carico_adozione_fuori_asl1.jpg" NAME="presa_carico_adozione_fuori_asl1.jsp" />
				</br>
				A questo punto se in fase di inserimento 'Adozione fuori Asl' &egrave; stato inserito ex-novo il proprietario baster&agrave; cliccare su </br>
				'Importa proprietario(Tale operazione &egrave; possibile solo se i dati sono completi)'.</br>
				Altrimenti se selezionato dalla lista Proprietari la procedura di import non andr&agrave; a buon fine in quanto, ovviamente , risulta gi&agrave; 
				inserito nel sistema.</br>
				In entrambi i casi &egrave; possibile comunque selezionare/inserire un nuovo proprietario in fase di presa in carico
				cliccando su 'Seleziona nuovo proprietario/Inserisci nuovo proprietario'.
				
				<IMG SRC="images/registrazioni/presa_carico_adozione_fuori_asl2.jpg" NAME="presa_carico_adozione_fuori_asl2.jsp"/ >
				
				N.B.: L'operazione di import sar&agrave; eseguita sempre partendo dai dati inseriti in adozione fuori asl. Quindi, se dopo aver selezionato o inserito </br>
				un proprietario si clicca su importa, il sistema tenter&agrave; di fare sempre l'import dei dati messi a mano in adozione fuori asl e, qualsiasi sia l'esito,</br>
				sar&agrave; perso il proprietario selezionato dalla popup o inserito ex novo in fase di presa in carico adozione.
				
				<b>Cattura </b><br/><br/>
				<b>Cessione</b><br/><br/>
				<b>Decesso</b><br/><br/>
				<b>Furto</b><br/><br/>
				<b>Inserimento microchip</b></br>
				<b>Presa in carico da cessione</b></br>
				Registrazione effettuabile solo per completare le cessioni effettuate prima del 10/05/2020 e che prevedevano la validazione dell'asl destinataria.
				<b>Registrazione in BDU</b></br>
				<b>Restituzione da canile</b></br>
				<b>Rilascio e Rinnovo/Smarrimento Passaporto</b></br>
				Requisito fondamentale per l'inserimento della registrazione di rilascio e smarrimento è che il numero passaporto sia presente nella banca dati a priori e assegnato alla UOS selezionata in fase di accesso da VAM.
				<br/>
				Per quanto riguarda il rinnovo (non smarrimento), questa si può fare direttamente dalla bdu in quanto in questo caso non è prevista la modiifca del numero passaporto e non è necessario il controllo sulla UOS scelta.
				<br/>
				In caso di smarrimento passaporto è necessario modificare il numero passaporto e controllare che questo sia associato alla propria UOS.<br/>
				In caso di rinnovo passaporto il numero non deve essere modificato e non sarà fatto alcun controllo di associazione alla propria UOS.
				Per cui, per inserire la registrazione di passaporto, bisogna accedere in vam e fare un'accettazione con motivo 'rilascio passaporto'.<br/>
				La 'Data di Scadenza Passaporto' viene generata automaticamente dal sistema (e non modificabile) a partire dall'ultima 'Vaccinazione Antirabbica'</br> 
				effettuata sulla base del tipo di vaccino inoculato. </br>
				Le registrazioni di Rilascio e Rinnovo Passaporto vengono bloccate in assenza di copertura antirabbica (data Scadenza copertura vaccinale minore o uguale alla data odierna)</br>
				La data scadenza copertura vaccinale viene cos&igrave; calcolata: data vaccinazione pi&ugrave; durata vaccino inoculato (uno, due o tre anni)</br>
				Di seguito il messaggio che compare se i requisiti non sono rispettati</br>
				<IMG SRC="images/registrazioni/passaporto1.jpg" NAME="passaporto1.jpg"/ >
				</br>
				Nel caso di vaccinazione inserita precedentemente all'applicazione della nuova gestione del tipo inoculato, la scadenza sar&agrave; settata a un anno.</br>
                Invece nel caso in cui il cane provenga da altra regione o dall'estero sar&agrave; possibile inserire, contestualmente all'inserimento in </br>
				anagrafica del cane, la registrazione del passaporto con relativa data di scadenza(campi testo liberi).</br>
				</br>
				Il sistema controllerà anche che la lunghezza del valore numerico inserito sia di 13 cifre.
                			
				<b>Ritrovamento</b></br>
				<b>Smarrimento</b></br>
				<b>Sterilizzazione</b></br>
				Nella maschera di inserimento, quando si seleziona 'ASL' nel campo 'Esecutore sterilizzazione', viene mostrato un campo obbligatorio
				'veterinari' con l'elenco dei veterinari raggruppato per ASL. Dall'elenco &egrave; si devono selezionare due veterinari.<br/>
				<b>Trasferimento</b></br>
				 Nel caso in cui l'animale appartiene ad un operatore commerciale, la registrazione viene salvata con nome 'Trasferimento tra operatori commerciali'<br/>
				<b>Trasferimento fuori regione</b></br>
				Visualizzando il dettaglio della registrazione &egrave; possibile stampare il certificato cliccando sul link 'Stampa certificato' come nell'immagine seguente<br/> 
				<IMG SRC="images/registrazioni/trasf_fuori regione_stampa_certificato.jpg" NAME="trasf_fuori regione_stampa_certificato.jpg"/ >
				<b>Adozione a distanza</b></br>
				<b>Adozione da colonia</b></br>
				<b>Cambio detentore</b></br>
				<b>Controlli commerciali</b></br>
				<b>Esito controlli</b></br>
				<b>Inserimento secondo microchip/tatuaggio</b></br>
				<b>Morsicatura</b></br>
				La registrazione di morsicatura &egrave; disponibile solo per i cani nello stato "Randagio", "Privato", "Randagio/sterilizzato", "Privato/Adozione",
				"Privato/sterilizzato" e "Privato/Sterilizzato/Adozione". <br/>
				Per inserire la registrazione si deve selezionare la data e la tipologia (interspecifica o intraspecifica).<br/>
				Se si seleziona come tipologia interspecifica si devono compilare obbligatoriamente i campi:</br>
				<ul>
					<li>Peso e massa</li>
					<li>Categorie a rischio</li>
					<li>Tipo di aggressione</li>
					<li>Prevedibilita</li>
					<li>Controllo del morso</li>
					<li>Tipo di morso</li>
					<li>Morso ripetuto</li>
					<li>Rilievi sull'aggressore</li>
					<li>Analisi gestione</li>
					<li>Veterinari (della propria asl)</li>
					<li>Misure restrittive</li>
					<li>Misure formative</li>
					<li>Misure riabilitative</li>
				</ul>
				
				Se si seleziona come tipologia intraspecifica si devono compilare obbligatoriamente i campi:</br>
				<ul>
					<li>Prevedibilit&agrave; evento</li>
					<li>Morso ripetuto</li>
					<li>Taglia aggressore</li>
					<li>Categoria vittima</li>
					<li>Rilievi sull'aggressore</li>
					<li>Taglia vittima</li>
					<li>Analisi gestione</li>
					<li>Veterinari (della propria asl)</li>
					<li>Misure restrittive</li>
					<li>Misure formative</li>
					<li>Misure riabilitative</li>
				</ul>
				
				Un altro campo obbligatorio &egrave; il controllo ufficiale corrispondente in GISA, 
				selezionato tra quelli chiusi, eseguiti sul MC su cui si sta inserendo la registrazione e non gi&agrave; associati ad altri eventi e che hanno come motivi di ispezione l'attivit&agrave; AO9_B o AO9_C. <br/> 
				
				
				
				Dopo aver compilato tutti i campi obbligatori cliccare su 'Salva' per procedere al salvataggio della registrazione.<br/>
				Se con i dati immessi l'animale entra nel REGISTRO UNICO CANI A RISCHIO ELEVATO DI AGGRESSIVITA' il sistema avverte l'utente con un avviso e si avr&agrave;
				 la possibilit&agrave; di rivedere i dati prima di procedere con la registrazione. Nell'avviso sar&agrave; mostrata anche la eventuale valutazione Dehasse, se tipologia interspecifica.<br/>
				I criteri per calcolare se un cane entra nel registro aggressori sono i seguenti:<br/>
				<u>Tipologia interspecifica</u><br/>
				- MORSO RIPETUTO: SI<br/>
				- RILIEVI SULL'AGGRESSORE -> ALTERAZIONI COMPORTAMENTALI: PRESENZA<br/>
				- ANALISI GESTIONE: SCORRETTA <br/><br/>
				
				N.B.: Incide nei criteri anche la valutazione Dehasse<br/>
				
				<u>Tipologia intraspecifica</u><br/>
				- PREVEDIBILITA' EVENTO: NO<br/>
				- MORSO RIPETUTO: SI<br/>
				- TAGLIA AGGRESSORE: GRANDE O GIGANTE<br/>
				- CATEGORIA VITTIMA: CUCCIOLO O ANZIANO O DISABILE<br/>
				- TAGLIA VITTIMA: PICCOLA<br/>
				- RILIEVI SULL'AGGRESSORE -> ALTERAZIONI COMPORTAMENTALI: PRESENZA<br/>
				- ANALISI GESTIONE: SCORRETTA<br/>
				
				Se il cane entra nel registro il sistema avverte l'utente mostrando un messaggio.<br/>
				
				La registrazione &egrave; consultabile a partire dalla lista registrazioni dell'animale.<br/>
				Nel dettaglio &egrave; possibile stampare i dati inseriti.<br/> 
				
				
				<b>Aggressione</b></br>
				La registrazione di aggressione &egrave; disponibile solo per i cani nello stato "Randagio", "Privato", "Randagio/sterilizzato", "Privato/Adozione",
				"Privato/sterilizzato" e "Privato/Sterilizzato/Adozione". <br/>
				Per inserire la registrazione si deve selezionare la data e la tipologia (interspecifica o intraspecifica).<br/>
				Se si seleziona come tipologia interspecifica si devono compilare obbligatoriamente i campi:</br>
				<ul>
					<li>Prevedibilita' evento</li>
					<li>Aggressione ripetuta</li>
					<li>Taglia aggressore</li>
					<li>Categoria vittima</li>
					<li>Rilievi sull'aggressore</li>
					<li>Analisi gestione</li>
					<li>Veterinari (della propria asl)</li>
					<li>Misure restrittive</li>
					<li>Misure formative</li>
					<li>Misure riabilitative</li>
				</ul>
				
				Se si seleziona come tipologia intraspecifica si devono compilare obbligatoriamente i campi:</br>
				<ul>
					<li>Prevedibilita' evento</li>
					<li>Aggressione ripetuta</li>
					<li>Taglia aggressore</li>
					<li>Categoria vittima</li>
					<li>Taglia vittima</li>
					<li>Rilievi sull'aggressore</li>
					<li>Analisi gestione</li>
					<li>Veterinari (della propria asl)</li>
					<li>Misure restrittive</li>
					<li>Misure formative</li>
					<li>Misure riabilitative</li>
				</ul>
				
				Un altro campo obbligatorio &egrave; il controllo ufficiale corrispondente in GISA, 
				selezionato tra quelli chiusi, eseguiti sul MC su cui si sta inserendo la registrazione e non gi&agrave; associati ad altri eventi e che hanno come motivo di ispezione l'attivit&agrave; B52. 
				
				Dopo aver compilato tutti i campi obbligatori cliccare su 'Salva' per procedere al salvataggio della registrazione.<br/>
				Se con i dati immessi l'animale entra nel REGISTRO UNICO CANI A RISCHIO ELEVATO DI AGGRESSIVITA' il sistema avverte l'utente con un avviso e si avr&agrave;
				 la possibilit&agrave; di rivedere i dati prima di procedere con la registrazione.<br/>
				I criteri per calcolare se un cane entra nel registro aggressori sono i seguenti:<br/>
				<u>Tipologia interspecifica</u><br/>
				- PREVEDIBILITA' EVENTO: NO<br/>
				- AGGRESSIONE RIPETUTA: SI<br/>
				- TAGLIA AGGRESSORE: GRANDE oppure GIGANTE<br/>
				- CATEGORIA VITTIMA: BAMBINO oppure ADULTO oppure ANZIANO oppure DISABILE<br/>
				- RILIEVI SULL'AGGRESSORE -> ALTERAZIONI COMPORTAMENTALI: PRESENZA<br/>
			    - ANALISI GESTIONE: SCORRETTA<br/>
				<br/>
				
				<u>Tipologia intraspecifica</u><br/>
				- PREVEDIBILITA' EVENTO: NO<br/>
				- AGGRESSIONE RIPETUTA: SI<br/>
				- TAGLIA AGGRESSORE: GRANDE oppure GIGANTE<br/>
				- CATEGORIA VITTIMA: BAMBINO oppure ADULTO oppure ANZIANO oppure DISABILE<br/>
				- RILIEVI SULL'AGGRESSORE -> ALTERAZIONI COMPORTAMENTALI: PRESENZA<br/>
			    - ANALISI GESTIONE: SCORRETTA<br/>
				
				Se il cane entra nel registro il sistema avverte l'utente mostrando un messaggio.<br/>
				
				La registrazione &egrave; consultabile a partire dalla lista registrazioni dell'animale.<br/>
				Nel dettaglio &egrave; possibile stampare i dati inseriti.<br/> 
				
				<b>Presa in Carico Adozione Fuori Asl</b></br>
				Successivamente all'inserimento di registrazione di 'Adozione fuori Asl' un utente appartenente all'Asl di destinazione pu&ograve; inserire </br>
				una registrazione di 'Presa in carico adozione fuori Asl'.
				<IMG SRC="images/registrazioni/presa_carico_adozione_fuori_asl1.jpg" NAME="presa_carico_adozione_fuori_asl1.jsp"/ >
				</br>
				A questo punto se in fase di inserimento 'Adozione fuori Asl' &egrave; stato inserito ex-novo il proprietario baster&agrave; cliccare su </br>
				'Importa proprietario(Tale operazione &egrave; possibile solo se i dati sono completi)'.</br>
				Altrimenti se selezionato dalla lista Proprietari la procedura di import non andr&agrave; a buon fine in quanto, ovviamente , risulta gi&agrave; 
				inserito nel sistema.</br>
				In entrambi i casi &egrave; possibile comunque selezionare/inserire un nuovo proprietario in fase di presa in carico
				cliccando su 'Seleziona nuovo proprietario/Inserisci nuovo proprietario'.
				
				<IMG SRC="images/registrazioni/presa_carico_adozione_fuori_asl2.jpg" NAME="presa_carico_adozione_fuori_asl2.jsp"/ >
				
				N.B.: L'operazione di import sar&agrave; eseguita sempre partendo dai dati inseriti in adozione fuori asl. Quindi, se dopo aver selezionato o inserito </br>
				un proprietario si clicca su importa, il sistema tenter&agrave; di fare sempre l'import dei dati messi a mano in adozione fuori asl e, qualsiasi sia l'esito,</br>
				sar&agrave; perso il proprietario selezionato dalla popup o inserito ex novo in fase di presa in carico adozione.
				
				<b>Reimmissione</b></br>
				<b>Ricattura</b></br>
				<b>Rientro da fuori regione</b></br>
				<b>Rientro da fuori stato</b></br>
				<b>Trasferimento canile</b></br>
				<b>Trasferimento fuori regione solo proprietario</b></br>
				<b>Trasferimento fuori stato</b></br>
				<b>Mutilazione</b><br/>
				La registrazione non e' possibile per animali randagi e reimmessi, in fase di cessione/adozione, deceduti, smarriti, rubati, fuori regione o fuori stato.<br/>
				 E' possibile la registrazione per cani del circuito commerciale o appartenenti a partite.<br/>
				 <b>Allontanamento</b><br/>
				 La data di registrazione deve ricadere nei due mesi (60 giorni) successivi alla nascita. <br/>
La registrazione non e' possibile per animali randagi e reimmessi, in fase di cessione/adozione, deceduti, smarriti, rubati, fuori regione o fuori stato.<br/>
E' possibile la registrazione per cani del circuito commerciale o appartenenti a partite.
			<br>
			Per inserire l'indirizzo, nelle registrazioni dove è previsto, seguire le istruzioni per l'inserimento della residenza illustrate nel paragrafo 'Aggiungi proprietario'<br>