<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>

<%@ include file="../initPage.jsp"%>
<link rel="stylesheet" type="text/css" href="style.css" />
<title>Manuale utente BDR</title>
<script>
	function vaiALink(link) {
		document.location.href = '#' + link;
	}
</script>

</head>

<body>
	<div id="container">
		<div id="header">
			<h1>
				<span class="off">Manuale Utente BDR</span>
			</h1>
		</div>
		<input type="submit" name="stampa" class="buttonClass"
			onclick="window.print();" value="Stampa" />
		<div id="leftmenu">

			<div id="leftmenu_top"></div>

			<div id="leftmenu_main">

				<h3>Sezioni della guida</h3>

				<ul>
					<li><a href="../speedtest/speedtest.html">Speed Test</a></li>
					<li><a href="#anagrafe_animali">Anagrafe animale</a></li>
					<li><a href="#registrazioni_animale">Registrazioni</a></li>
					<li><a href="#proprietari/detentori">Proprietari/Detentori</a></li>
					<li><a href="#Microchip">Microchip</a></li>
					<li><a href="#Contributi Sterilizzazioni">Contributi
							Sterilizzazioni</a></li>
					<li><a href="#Partite commerciali">Partite commerciali</a></li>
					<li><a href="#McNonStandard">McNonStandard</a></li>
					<li><a href="#Barcode cani in canile">Barcode cani in
							canile</a></li>
					<li><a href="#Cessioni incompiute">Cessioni incompiute</a></li>
					<li><a href="#Ricerca Microchip">Ricerca Microchip</a></li>
					<li><a href="#Registrazioni Fuori Dominio">Registrazioni
							fuori dominio asl</a></li>
					<dhv:permission name="anagrafe_canina_operazioni_hd-add">
						<li><a href="#Operazioni Help Desk">Operazioni Help Desk
								livello 1</a></li>
					</dhv:permission>
					<dhv:permission name="profilassi_leishmania_modulo-view">
						<li><a href="#Profilassi Leishmania">Profilassi
								Leishmania</a></li>
					</dhv:permission>
					<dhv:permission name="profilassi_rabbia_modulo-view">
						<li><a href="#Profilassi Rabbia">Profilassi Rabbia</a></li>
					</dhv:permission>
					<dhv:permission name="passaporto-view">
						<li><a href="#Passaporti">Banca dati passaporti a priori</a></li>
					</dhv:permission>
					<li><a href="#Gestione canili">Gestione Canili</a></li>
				</ul>
			</div>


			<div id="leftmenu_bottom"></div>
		</div>

		<div id="content">
			<div id="content_top"></div>

			<div id="content_main">
				<a name="anagrafe_animali"></a>
				<H1>Anagrafe animali</H1>
				<div id="content_guida">
					Il men&ugrave; anagrafe animali consente la gestione di tutte le
					specie animali presenti in banca dati.<br> <br> <IMG
						SRC="images/anagrafe_animali/anagrafe_animali.PNG"
						NAME="anagrafe_animali.PNG"/ >
				</div>
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
				<div id="content_guida">
					Il sottomen&ugrave; aggiungi animale permette l'inserimento di
					ognuna delle specie di animali che &egrave possibile censire in
					banca dati.<br> <IMG
						SRC="images/anagrafe_animali/anagrafe_animali_aggiungi.PNG"
						NAME="anagrafe_animali_aggiungi.PNG" />
				</div>
				<div id="content_guida">
					I campi contrassegnati con l'asterisco (<span style="color: red;">*</span>)
					sono obbligatori. Dal men&ugrave; a tendina accanto alla dicitura
					"Specie animale" &egrave possibile scegliere la specie dell'animale
					che si sta inserendo. In base alla specie scelta, le informazioni
					necessarie e/o obbligatorie possono variare. * Per dettagli
					sull'inserimento di animali in canile, vedere il paragrafo dedicato
					alla <a href="#Gestione canili">Gestione Canili</a>
				</div>


				<div class="fine" style="height: 50px;">&nbsp;</div>


				<a name="registrazioni_animale"></a>

				<H1>Registrazioni</H1>
				<div id="content_guida">
					<br> Nel modulo riservato alle Registrazioni &egrave;
					possibile effettuare esclusivamente operazioni di ricerca sulle
					registrazioni presenti nel sistema, la maschera si presente nel
					seguente modo:<br> <br> <IMG
						SRC="images/registrazioni/Imm_1.PNG" NAME="registrazioni.PNG"/ >
				</div>

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
			</div>
			<br>
			<H2>Tipologia registrazioni</H2>
			<br> A seconda dello stato dell'animale è possibile aggiungere una  registrazione tra le seguenti tipologie (alcune delle quali sono riportate in figura):<br>
			<br>
			<IMG SRC="images/mover0103/1.png" NAME="1.png"/ >
			<IMG SRC="images/mover0103/2.png" NAME="2.png"/ >
			<ul>
				<li>Adozione da canile
				<li>Cattura
				<li>Cessione
				<li>Decesso
				<li>Furto
				<li>Inserimento microchip
				<li>Presa in carico da cessione
				<li>Registrazione in BDU
				<li>Restituzione da canile
				<li>Rilascio Passaporto
				<li>Ritrovamento
				<li>Smarrimento
				<li>Sterilizzazione
				<li>Trasferimento
				<li>Trasferimento fuori regione
				<li>Adozione a distanza
				<li>Adozione da colonia
				<li>Cambio detentore  (dal canile può essere dato ad un privato solo tramite HD)
				<li>Cambio detentore
				<li>Controlli commerciali
				<li>Esito controlli
				<li>Inserimento secondo microchip/tatuaggio
				<li>Inserimento vaccinazioni
				<li>Morsicatore
				<li>Reimmissione
				<li>Ricattura
				<li>Rientro da fuori regione
				<li>Rientro da fuori stato
				<li>Trasferimento canile
				<li>Trasferimento fuori regione solo proprietario
				<li>Trasferimento fuori stato
				<li>Trasferimento verso sindaco (genera il trasferimento ad un sindaco senza necessit&agrave; di inserire  la cattura. Registrazione che pu&ograve; essere effettuata solo dall'HD)
			</ul>
			<br>




			<div class="fine" style="height: 50px;">&nbsp;</div>

			<a name="proprietari/detentori"></a>

			<H1>Proprietari/Detentori</H1>
			<div id="content_guida">
				<br> Nel modulo proprietari/detentori, è possibile fare due operazioni principali: 
				<ul>
					<li>Aggiungi proprietario
					<li>Ricerca proprietario
				</ul>
				<br> <IMG SRC="images/propriet_detent/Imm_propr1.PNG"
					NAME="propriet_detent.PNG"/ >
			</div>
			<br> <br>
			<H2>Aggiungi proprietario</H2>
			Inserire un nuovo proprietario è una operazione che non può essere fatta se non si è abilitati dal proprio ruolo. Ad es. un utente con ruolo "veterinario privato" non è abilitato ad inserire proprietari in anagrafe.
			Coloro che sono abilitati, possono inserire "solo"  le seguenti tipologie di proprietario: 
			<ul>
				<li>Privato 
				<li>Sindaco
				<li>Sindaco fuori regione 
				<li>Colonia felina
			</li>
			<IMG SRC="images/mover0103/3.png" NAME="3.PNG"/ >
			<br> <br>
			<H2>Ricerca proprietario</H2>
			Per effettuare la ricerca dei proprietari già presenti in anagrafica, è indispensabile selezionare una tra le tipologie proprietari. L'utente deve spuntare una tipologia di proprietario tra quelle presenti nell'elenco, ovvero: Privato, Sindaco, Operatore Commerciale / Importatore, Canile, Operatore Commerciale, Sindaco Fuori Regione, Colonia Felina. 
			Gli altri campi presenti nella maschera tipo ASL, Comune, P.IVA, Codice Fiscale, Nominativo Proprietario/Detentore sono valorizzabili facoltativamente.
			<br> <br> <IMG
				SRC="images/mover0103/4.png" NAME="4.PNG" /><br> <br>
				SOLO per i veterinari LLPP, sarà possibile fare una ulteriore ricerca visibile in home page: "Ricerca Proprietario" - Essa permetterà di inserire un microchip e visualizzare:
				<ul>
					<li>nome e cognome del proprietario</li> 
					<li>stato dell'animale</li>
					<li>ASL </li>
				</ul>
			<IMG SRC="images/mover0103/5.png" NAME="5.PNG" />
			<H2>Aggiungi proprietario</H2>
			<br> Come detto anche in altri paragrafi, esistono varie
			tipologie di proprietari, ovvero:
			<ul>
				<li>Privato
				<li>Sindaco
				<li>Operatore Commerciale/Importatore
				<li>Canile
				<li>Operatore Commerciale
				<li>Sindaco_FR
				<li>Colonia
			</ul>
			cosa importante da specificare &egrave che non con tutti i ruoli si
			ha la possibilit&agrave di inserire in anagrafe i vari tipi di
			proprietari. Un utente con ruolo "veterinario privato" non &egrave
			abilitato ad inserire proprietari in anagrafe.<br> <br>
			<H2>Inserimento di una colonia nel sistema</H2>
			<br> L'applicativo BDU permette il censimento di proprietari di
			tipo colonia. Per poter procedere al censimento di una colonia,
			l'operatore, una volta identificatosi nel sistema, deve accedere al
			modulo Proprietari/Detentori, sottomodulo Aggiungi Proprietario e
			scegliere, nella lista dei possibili operatori, quello con dicitura
			"Colonia".<br> Nella maschera che appare, le informazioni
			necessarie all'operatore per poter proseguire con successo
			all'inserimento della colonia sono:<br> <br>
			<ol type="A">
				<b><li>Informazioni anagrafiche sul Referente della colonia</b>
				<br> ( Completato questo primo step, l'operatore pu&ograve
				cliccare sul pulsante "Inserisci" per poter andare avanti con
				l'inserimento).
				<br>
				<br> Nella schermata successiva le informazioni richieste (non
				tutte obbligatorie) sono:
				<br>
				<br>
				<b><li>Indirizzo della colonia
				<li>Informazioni sulla colonia:</b>

				<ul>
					<li>Numero decreto identificativo della colonia
					<li>Data di registrazione colonia
					<li>Numero totale gatti + Informazione sull'esatezza di tale
						numero (Numero Presunto/Non presunto)
					<li>Data del censimento dei gatti della colonia
					<li>Numero totale gatto di sesso maschio + Informazione
						sull'esatezza di tale numero (Numero Presunto/Non presunto)
					<li>Numero totale gatti di sesso femmina + Informazione
						sull'esatezza di tale numero (Numero Presunto/Non presunto)
					<li>Nominativo del veterinario di riferimento della colonia
				</ul>
			</ol>
			<br> Delle informazioni elencate sopra, quelle obbligatorie
			saranno l'indirizzo della colonia, il numero di decreto, il numero
			totale di gatti (eventualmente presunto), la data di censimento della
			colonia, il nominativo del veterinario. <br> E' importante
			sapere, come vedremo in seguito, che il numero totale di gatti
			appartenente ad una colonia pu&ograve essere modificato nel tempo,
			attraverso l'inserimento nel sistema di una registrazione di modifica
			del numero di gatti appartenenti alla colonia. Tale registrazione
			modificher&agrave il numero totale di gatti senza interferire con
			eventuali gatti gi&agrave censiti e quindi con le informazioni
			calcolate che riguardano la colonia e che analizzeremo nel paragrafo
			successivo. <br> <br>
			<H2>Informazioni dinamiche delle colonie.</H2>
			<br> Una volta che una colonia &egrave stata anagrafata nel
			sistema BDU, &egrave possibile fondamentalmente su di essa eseguire
			due tipi di operazioni:<br> <br>
			<ol>
				<li>Registrare una modifica del numero di gatti appartenenti ad
					essa<br>
				<li>Censire in BDU i gatti appartenenti alla colonia<br>
			</ol>
			<br> Appena inserita in BDU, una colonia ha fondamentalmente
			solo l'informazione sul numero di gatti che la compongono
			(eventualmente presunto).<br> Il secondo passo &egrave censire
			questi gatti, immettendo nel sistema, per ognuno di essi, le
			informazioni che lo riguardano.<br> Per censire un gatto in una
			colonia esistono due alternative:<br>
			<ul type="square">
				<li>Cliccare sul pulsante Aggiungi gatto raggiungibile dal
					dettaglio della colonia.<br>
				<li>Accedere al men&ugrave Anagrafe animali, sottomen&ugrave
					Aggiungi animale e, dopo aver scelto la specie "Gatto", procedere
					all'inserimento scegliendo come proprietario la colonia a cui si
					vuole aggiungere il gatto.<br> I due modi di procedere sono
					equivalenti.<br> Quando viene censito un gatto appartenente ad
					una colonia, di questa si popolano congruentemente anche le
					informazioni dinamiche (cio&egrave quelle non immesse direttamente
					dall'operatore). In particolare tali informazioni (visibili dal
					dettaglio della colonia) sono:<br>
					<ol type="a">
						<li>Numero totale gatti identificati: tale numero indica il
							numero totale dei gatti che sono stati censiti nella colonia <br>
						<li>Numero totale gatti ancora da identificare: tale numero
							rappresenta la differenza tra il numero totale di gatti che la
							compongono (informazione statica inserita dall'operatore) e il
							numero di gatti gi&agrave identificati (censiti) del punto
							precedente <br>
						<li>Numero gatti sterilizzati: tale numero indica il numero
							di gatti, tra quelli gi&agrave identificati, per i quali nel
							sistema &egrave presente una registrazione di sterilizzazione.
							L'informazione sulla sterilizzazione pu&ograve essere stata
							immessa contestualmente all'inserimento in BDU o in seguito,
							attraverso la funzionalit&agrave di aggiunta registrazioni
							offerta dal sistema.<br>
						<li>Numero gatti da sterilizzare: tale numero indica il
							numero di gatti, tra quelli gi&agrave identificati, per i quali
							nel sistema non &egrave presente una registrazione di
							sterilizzazione <br>
					</ol> E' opportuno precisare a questo punto, per meglio comprendere le
					informazioni precedenti, che: <br>
				<li>Numero totale gatti identificati + Numero totale gatti da
					identificare = Numero totale gatti della colonia <br>
				<li>Numero gatti sterilizzati + Numero gatti da sterilizzare =
					Numero totale gatti identificati <br> (Ogni gatto appartenente
					alla colonia e identificato pu&ograve essere sterilizzato o meno)
				<li>Se per una colonia vale: <br>
				<li>Numero totale gatti identificati = Numero totale gatti
					della colonia e conseguentemente Numero totale gatti da
					identificare = 0, per il sistema vuol dire che tutti i gatti
					appartenenti alla colonia sono stati censiti e, quindi, non
					sar&agrave possibile pi&ugrave aggiungere ad essa gatti. Per poter
					procedere l'operatore deve prima effettuare una registrazione di
					modifica dei gatti totali presenti nella colonia.
			</ul>


			<br>
			<H2>Modifica della dimensione della colonia</H2>
			<br> Su una colonia gi&agrave esistente, &egrave possibile
			inserire una registrazione di modifica del numero totale di gatti.
			Per fare questo, l'operatore, dal dettaglio della colonia deve
			cliccare sul link "Registrazione di modifica dimensione della
			colonia". Si aprir&agrave una schermata in cui l'utente dovr&agrave
			inserire la data del nuovo censimento, il nuovo numero totale di
			gatti censiti nella colonia. E' opportuno specificare che questo
			numero deve essere il nuovo numero totale e non il numero di gatti da
			aggiungere al precedente censimento. Il sistema andr&agrave in errore
			se il nuovo numero immesso sar&agrave inferiore al numero di gatti
			gi&agrave censiti nella colonia, in quanto si creerebbero
			incongruenze. Da notare, però, che &egrave possibile inserire un
			numero inferiore a quello del precedente censimento solo nel caso il
			numero di gatti censiti &egrave inferiore al numero del precedente
			censimento e inferiore o uguale a quello del nuovo censimento che si
			sta registrando. Questo nuovo censimento sar&agrave il punto di
			partenza per il calcolo di tutte le propriet&agrave dinamiche della
			colonia descritte nel paragrafo precedente.



			<div class="fine" style="height: 50px;">&nbsp;</div>


			<a name="Microchip"></a>

			<H1>Microchip</H1>
			<div id="content_guida">
				<br> La maschera microchip si presenta nel seguente modo:<br>
				<br> <IMG SRC="images/mover0103/6.png"
					NAME="6.PNG" /><br> <br> Le operazioni
				possibili in questo modulo sono:
				<ul>
					<li>Ricerca
					<li>Importa
					<li>Scarico MC
					<li>Carico MC
				</ul>
				<br> Questa funzionalit&agrave &egrave usata quasi
				esclusivamente da utenti avente ruolo Veterinario Privato,
				poich&egrave gli viene assegnato dall'ASL una lista di microchip da
				assegnare, per cui il veterinario li importa (carica) e solo dopo
				aver concluso questa operazione, pu&ograve assegnare il microchip ad
				un animale e pu&ograve a questo punto anagrafarlo nel sistema. <br>
				
				Solo i veterinari privati (LLPP) avranno un controllo particolare: dovranno inserire MC con valore "380260..."   altrimenti al salvataggio dei dati,  saranno bloccati perché il MC non è valido.
				
				<div class="fine" style="height: 50px;">&nbsp;</div>


				<a name="Contributi Sterilizzazioni"></a>

				<H1>Contributi Sterilizzazioni</H1>
				<div id="content_guida">
					<br>

					<H2>Introduzione</H2>

					La pratica per il pagamento dei contributi relative alle
					sterilizzazioni dei cani, vede coinvolti due tipologie di utenti
					presenti in Anagrafe: gli amministratori delle asl e l'utente
					Regione. I primi, sono coloro i quali si occupano di inviare le
					richieste per il pagamento dei contributi stessi, il secondo,
					invece si occupa di approvare o respingere le pratiche di pagamento
					ricevute. L'applicativo presenter&agrave un insieme di progetti
					aperti per il pagamento del contributo di sterilizzazione, che
					verranno inseriti, da un utente con ruolo "Utente Regione". Ogni
					modifica da apportare al progetto, dovrà essere supportata da una
					richiesta alla regione, la quale eventualmente, dopo opportuni
					controlli, provvederà ad aprire una segnalazione all'help desk
					stesso nel caso di modifiche. Il pagamento dei contributi vede
					coinvolti sia i cani che i gatti registrati nell'Anagrafe della
					regione Campania. (sono esclusi i furetti).<br> All'atto di
					una registrazione di un cane /gatto in anagrafe, è possibile
					inserire i dati relativi alla sterilizzazione e se è stato
					richiesto per esso il pagamento del contributo. Oppure,
					successivamente all'inserimento, è possibile effettuare una
					registrazione di sterilizzazione, indicando chi ha sterilizzato il
					cane/gatto, la data e se per esso è stato richiesto il pagamento
					del contributo. In entrambi i casi, all'atto della selezione del
					flag del contributo, comparirà la lista dei progetti aperti, che
					coinvolgono l'asl dell'utente corrente. Nel caso in cui, si
					trattasse di un veterinario libero professionista accreditato, i
					progetti riguarderanno l'asl del proprietario dell'animale. In
					entrambi i casi, dopo aver inserito tale informazione, non sarà
					assolutamente possibile, modificare la data ed il flag del
					contributo.<br> <br>
					<H2>Progetti di sterilizzazione</H2>
					<br> I progetti di sterilizzazione vengono inseriti solo ed
					esclusivamente dagli utenti con ruolo Utente Regione. Ogni progetto
					prevede le seguenti informazioni:<br> <br>
					<ul>
						<li>Asl
						<li>Comuni coinvolti
						<li>Oggeto del decreto
						<li>Numero del decreto
						<li>Data Decreto
						<li>Data Inzio sterilizzazione
						<li>Data fine sterilizzazione
						<li>Numero Decreto
						<li>Numero totale di cani catturati
						<li>Numero totale di cani padronali
						<li>Numero totale di gatti catturati
						<li>Numero totale di gatti padronali
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








					<div class="fine" style="height: 50px;">&nbsp;</div>

					<div class="fine" style="height: 50px;"></div>





					<a name="Partite commerciali"></a>

					<H1>Partite commerciali</H1>
					<div id="content_guida">
						<br> La maschera relativa al modulo Partite Commerciali si
						presenta nel seguente modo: <br> <br> <IMG
							SRC="images/partite comm/Imm_pc1.PNG" NAME="Imm_pc1.PNG" /><br>
						<br> Le possibili operazioni che possono essere fatte in
						questo modulo sono:
						<ul>
							<li>Nuova partita commerciale
							<li>Visualizza partite commerciali
							<li>Richieste Importatori
						</ul>
						<div class="fine" style="height: 50px;">&nbsp;</div>

						<div class="fine" style="height: 50px;"></div>



						<a name="McNonStandard"></a>

						<H1>McNonStandard</H1>
						<div id="content_guida">
							<br> Nel modulo McNonStandard è presente un elenco di
							microchip che sono diversi dal solito, ovvero non sono 15 cifre
							numeriche. Attualmente non è più possibile inserirne altri. <br>
							<IMG SRC="images/MCnonstandard/Immagine_1.PNG"
								NAME="Immagine_1.PNG" /><br> <br>



							<div class="fine" style="height: 50px;">&nbsp;</div>

							<div class="fine" style="height: 50px;"></div>




							<a name="Barcode cani in canile"></a>

							<H1>Barcode cani in canile</H1>
							<div id="content_guida">
								<br> Dal modulo "Barcode cani in canile", l'operatore può
								generare il modulo "Scheda per l'invio di campioni all'IZS del
								Mezzogiorno" contenente i BARCODE relativi agli animali presenti
								in un dato canile.<br> <br> <IMG
									SRC="images/Barcode cani in canile/Immagine1.PNG"
									NAME="Immagine1.PNG" /><br> <br> La lista dei canili
								varia a seconda dell'ASL dell'operatore che opera nel sistema,
								per cui l'operatore ha la possibilità di selezionarne uno e
								procedendo con l'estrazione può visualizzare l'elenco dei cani
								presenti in esso, selezionando opportunamente i microchip dei
								cani di cui ha bisogno, può generare un'unica Scheda per l'invio
								di campioni all'IZS del Mezzogiorno, contenete tutti i barcode
								dei cani selezionati <br> <br> <IMG
									SRC="images/Barcode cani in canile/Immagine2.PNG"
									NAME="Immagine2.PNG" /><br> <br>



								<div class="fine" style="height: 50px;">&nbsp;</div>

								<div class="fine" style="height: 50px;"></div>


								<a name="Cessioni incompiute"></a>

								<H1>Cessioni Incompiute</H1>
								<div id="content_guida">
									<br> Nel modulo Cessioni incompiute, l'utente può
									visualizzare:
									<ul>
										<li>L'elenco delle Cessioni in ingresso alla propria ASL
											di appartenenza
										<li>L'elenco delle Cessioni in uscita, ovvero l'elenco
											delle cessioni effettuate dalla propria ASL verso altre ASL.




										
									</ul>

									<br> <IMG SRC="images/cessioni/Immagine1.PNG"
										NAME="Immagine1.PNG" /><br> <br>

									<div class="fine" style="height: 50px;">&nbsp;</div>

									<div class="fine" style="height: 50px;"></div>


									<a name="Ricerca Microchip"></a>

									<H1>Ricerca Microchip</H1>
									<div id="content_guida">
										<br> Nel modulo Ricerca Microchip, inserendo
										opportunamente un microchip presente in anagrafe, l'utente può
										generare due moduli:
										<ul>
											<li>Scheda per l'invio dei campioni
											<li>Scarica solo immagine barcode
										</ul>

										<br> <IMG SRC="images/Ricerca_mc/Immagine1.PNG"
											NAME="Immagine1.PNG" /><br> <br>

										<div class="fine" style="height: 50px;">&nbsp;</div>

										<div class="fine" style="height: 50px;"></div>



										<a name="Registrazioni Fuori Dominio"></a>

										<H1>Note sulle registrazioni effettuate fuori dominio asl</H1>
										<div id="content_guida">
											<br> E' stata introdotta, in BDU, la possibilità di
											inserire registrazioni a carico di animali non appartenenti
											all'asl di competenza dell'utente. Tali registrazioni si
											possono suddividere in due categorie, descritte nel seguito.
											La prima categoria comprende le registrazioni che non
											modificano il detentore del cane: <br></br>
											<ul>
												<li>Sterilizzazione</li>
												<li>Prelievo DNA</li>
												<li>Prelievo Campioni Leishmana</li>
												<li>Decesso</li>
												<li>Inserimento Vaccinazioni (Rabbia Leishmania)</li>
												<li>Rilascio Passaporto</li>
												<li>Rinnovo / Smarrimento Passaporto</li>
											</ul>
											<br></br> La seconda categoria comprende le registrazioni che
											modificano il detentore del cane: <br></br>
											<ul>
												<li>Ritrovamento</li>
												<li>Ritrovamento (Smarrimento non denunciato)</li>
												<li>Prelievo Campioni Leishmana</li>
												<li>Ricattura</li>
											</ul>
											<br> L'inserimento delle registrazioni del primo gruppo
											non modifica il normale flusso delle registrazioni possibili
											sugli animali, il secondo, invece, modifica il comportamento
											del flusso, in particolare permettendo all'asl fuori dominio
											di effettuare delle registrazioni normalmente non possibili
											ed eliminando, temporaneamente, per l'asl proprietaria, la
											possibilità di eseguirne altre. Come sempre, il flusso delle
											registrazioni dipende dallo stato dell'animale. Nel seguito
											evidenziamo gli scenari possibili a seconda dei vari casi. <br></br>
											<H2>Animali Padronali</H2>
											<br> <IMG
												SRC="images/liberalizzazione_registrazioni/1_padronali.png"
												NAME="1.PNG" /><br> <br> Nella figura 1 è
											visibile la lista delle registrazioni possibili fuori dominio
											asl su un animale padronale, non smarrito. In particolare, la
											registrazione che modifica il detentore dell'animale è quella
											di Ritrovamento non denunciato. Dopo l'inserimento di tale
											registrazione, il cane passa temporaneamente "in carico"
											all'asl che sta inserendo la registrazione, naturalmente non
											sarà visibile nessun cambiamento nell'asl proprietaria
											dell'animale. In seguito a ciò, sia l'asl proprietaria che
											l'asl con l'animale in carico possono inserire una
											registrazione che svincola l'asl "in carico" dalla detenzione
											dell'animale. In particolare possono farlo attraverso le
											registrazioni di (vedi Fig.2): <br></br>
											<ul>
												<li>Decesso</li>
												<li>Restituzione a proprietario</li>
												<li>Trasferimento canile</li>
											</ul>

											<br> <IMG
												SRC="images/liberalizzazione_registrazioni/2_padronali.png"
												NAME="2.PNG" /><br> <br> Restano attive,
											esclusivamente per l'asl con l'animale in carico, le
											registrazioni effettuabili fuori dominio asl e che non
											prevedono il cambio detentore, cioè quelle appartenenti alla
											prima categoria. E' opportuno notare, a questo punto, che il
											flusso è identico se trattasi di animale padronale in stato
											smarrito: l'unica eccezione è sulla registrazione di
											ritrovamento, che non sarà quella senza denuncia smarrimento
											ma il ritrovamento in seguito a uno smarrimento. <br></br>
											<H2>Animali Randagi (Precedentemente in canile)</H2>
											<br> <IMG
												SRC="images/liberalizzazione_registrazioni/3_randagi_canile.png"
												NAME="1.PNG" /><br> <br> Nella figura 3 è
											visibile la lista delle registrazioni possibili fuori dominio
											asl su un animale randagio, precedentemente in canile. In
											particolare, la registrazione che modifica il detentore
											dell'animale è quella di Ritrovamento non denunciato. Dopo
											l'inserimento di tale registrazione, il cane passa
											temporaneamente "in carico" all'asl che sta inserendo la
											registrazione, naturalmente non sarà visibile nessun
											cambiamento nell'asl proprietaria dell'animale. In seguito a
											ciò, sia l'asl proprietaria che l'asl con l'animale in carico
											possono inserire una registrazione che svincola l'asl "in
											carico" dalla detenzione dell'animale. In particolare possono
											farlo attraverso le registrazioni di (vedi Fig.4): <br></br>
											<ul>
												<li>Decesso</li>
												<li>Adozione</li>
												<li>Trasferimento a canile origine</li>
											</ul>

											<br> <IMG
												SRC="images/liberalizzazione_registrazioni/4_randagi_canile.png"
												NAME="2.PNG" /><br> <br> Restano attive ,
											esclusivamente per l'asl con l'animale in carico, le
											registrazioni effettuabili fuori dominio asl e che non
											prevedono il cambio detentore, cioè quelle appartenenti alla
											prima categoria. E' opportuno notare, a questo punto, che il
											flusso è identico se trattasi di animale padronale in stato
											smarrito: l'unica eccezione è sulla registrazione di
											ritrovamento, che non sarà quella senza denuncia smarrimento
											ma il ritrovamento in seguito a uno smarrimento. <br></br>
											<H2>Animali Randagi (Precedentemente sul territorio)</H2>
											<br> <IMG
												SRC="images/liberalizzazione_registrazioni/5_randagi_territorio.png"
												NAME="1.PNG" /><br> <br> Nella figura 5 è
											visibile la lista delle registrazioni possibili fuori dominio
											asl su un animale randagio, precedentemente sul territorio.
											In particolare, la registrazione che modifica il detentore
											dell'animale è quella di Ricattura. Dopo l'inserimento di
											tale registrazione, il cane passa temporaneamente "in carico"
											all'asl che sta inserendo la registrazione, naturalmente non
											sarà visibile nessun cambiamento nell'asl proprietaria
											dell'animale. In seguito a ciò, sia l'asl proprietaria che
											l'asl con l'animale in carico possono inserire una
											registrazione che svincola l'asl "in carico" dalla detenzione
											dell'animale. In particolare possono farlo attraverso le
											registrazioni di (vedi Fig.6): <br></br>
											<ul>
												<li>Decesso</li>
												<li>Adozione</li>
												<li>Trasferimento a canile</li>
												<li>Reimmissione</li>
											</ul>

											<br> <IMG
												SRC="images/liberalizzazione_registrazioni/6_randagi_territorio.png"
												NAME="2.PNG" /><br> <br> Restano attive,
											esclusivamente per l'asl con l'animale in carico, le
											registrazioni effettuabili fuori dominio asl e che non
											prevedono il cambio detentore, cioè quelle appartenenti alla
											prima categoria. E' opportuno notare, a questo punto, che il
											flusso è identico se trattasi di animale padronale in stato
											smarrito: l'unica eccezione è sulla registrazione di
											ritrovamento, che non sarà quella senza denuncia smarrimento
											ma il ritrovamento in seguito a uno smarrimento.



										</div>



										<div class="fine" style="height: 50px;">&nbsp;</div>

										<div class="fine" style="height: 50px;"></div>

										<dhv:permission name="anagrafe_canina_operazioni_hd-add">
											<a name="Operazioni Help Desk"></a>

											<H1>Operazioni riservate all' Help Desk di primo livello</H1>
											<div id="content_guida">
												<br> Sono state abilitate, per gli utenti Help Desk di
												primo livello, alcune funzionalità di modifica / correzione
												dati errati. Nel seguito i dettagli. <br></br>
												<H2>Sostituzione microchip</H2>
												<br> Per effettuare la modifica del primo microchip di
												un animale è necessario accedere al modulo "Anagrafe
												Animali", selezionare l'animale oggetto della modifica e
												accedere alla maschera di modifica. Da questa, il campo
												microchip risulterà modificabile. Effettuata la modifica,
												sarà necessario cliccare sul tasto "Salva". E' importante
												tenere presente che tale modifica avrà impatto anche sui
												sistemi VAM e GISA per quanto riguarda rispettivamente
												posizioni aperte (accettazioni in VAM, controlli inseriti in
												GISA a carico degli animali padronali). <br></br>
												<H2>Modifica Proprietario / Detentore animale</H2>
												<br> Per effettuare la modifica del proprietario o del
												detentore di un animale è necessario accedere al modulo
												"Anagrafe Animali", selezionare l'animale oggetto della
												modifica e accedere alla maschera di modifica. Da questa, è
												possibile effettuare la ricerca del nuovo proprietario o del
												nuovo detentore. Si precisa che l'operatore deve verificare,
												in primis, che la modifica non crei problemi e sia
												congruente con la storia precedente dell'animale. In
												particolare, il sistema avvertirà nesi seguenti casi che
												possono introdurre problemi nella storia dell'animale,
												tuttavia l'operatore avrà la facoltà di procedere: <br></br>
												<ul>
													<li>1. Risulta iscritto in banca dati da un periodo
														superiore ai 15 giorni</li>
													<br></br>
													<li>2. Ha delle registrazioni che implicano il
														trasferimento di proprietà in seguito alla prima
														iscrizione: Trasferimento Cessione Adozione Adozione fuori
														asl (In questo caso le informazioni relative a queste
														registrazioni e che possono includere informazioni sul
														proprietario / detentore antecedente la modifica non
														vengono aggiornate. La modifica di queste ultime
														operazioni continua ad essere una delle operazioni
														riservate all'HD di secondo livello.)</li>
													<br></br>
													<li>3. Viene selezionato un proprietario di asl
														diversa da quella attuale dell'animale. (In questo caso
														l'asl dell'animale viene modificata, restano invariate le
														informazioni sull'asl di inserimento animale e di tutte le
														registrazioni precedenti. La modifica di queste ultime
														operazioni continua ad essere una delle operazioni
														riservate all'HD di secondo livello.)</li>
												</ul>
												<br></br> L'operatore Help Desk avrà anche la possibilità di
												selezionare l'opzione "Aggiorna dati prima iscrizione ". Se
												selezionata questa opzione, saranno aggiornate al nuovo
												proprietario / detentore anche le informazioni di prima
												iscrizione. Effettuata la modifica, sarà necessario cliccare
												sul tasto "Salva". <br></br>

												<H2>Unificazione proprietari di tipo privato con
													trasferimento animali</H2>
												<br> Lo scopo di questa funzionalità è quella di
												permettere agli operatori HD primo livello l'unificazione di
												due proprietari con il relativo trasferimento di animali.
												Per usufruire di tale funzionalità è necessario accedere al
												menù "Proprietari/Detentori" e selezionare il sottomenù
												"Unificazione proprietari privati". Dalla maschera che si
												aprirà si dovranno selezionare il proprietario da eliminare
												e quello che lo sostituirà. La responsabilità
												dell'unificazione è dell' utente che esegue l'operazione: il
												sistema infatti non includerà nessuna verifica di
												similitudine tra i due soggetti coinvolti. Dopo avere
												confermato la sostituzione, il proprietario cancellato non
												sarà più visibile nel sistema e tutte le operazioni /
												registrazioni che lo coinvolgono saranno trasferite al nuovo
												soggetto. In particolare, quindi, saranno aggiornati al
												nuovo soggetto: <br></br>
												<ul>
													<li>Tutti i riferimenti di proprietari/detentore al
														soggetto cancellato</li>
													<li>Tutti i riferimenti nelle registrazioni animali
														che riguardano il proprietario/detentore corrente all'atto
														dell'inserimento e che si riferiscono al soggetto
														cancellato</li>
													<li>Tutti i riferimenti al soggetto cancellato
														presenti nelle registrazioni di movimentazione animali</li>
												</ul>
												<br></br>


												<H2>Eliminazione proprietari</H2>
												<br> Lo scopo di questa funzionalità è quella di
												permettere agli operatori HD primo livello la cancellazione
												dal sistema di proprietari / detentori. Per usufruire di
												tale funzionalità è necessario accedere al menù
												"Proprietari/Detentori", ricercare il proprietario da
												cancellare e, dalla pagina di dettaglio di quest'ultimo,
												cliccare sul pulsante "Elimina". Si aprirà una popup nella
												quale sarà obbligatorio inserire le motivazioni
												dell'operazione di eliminazione. Il sistema impedirà la
												cancellazione di soggetti attualmente proprietari o
												detentori di animali o coinvolti in registrazioni non
												cancellate a carico di qualche animale. <br></br>

											</div>

											<div class="fine" style="height: 50px;">&nbsp;</div>

											<div class="fine" style="height: 50px;"></div>

										</dhv:permission>

										<a name="Profilassi Leishmania"></a>

										<H1>Profilassi Leishmania</H1>
										<div id="content_guida">
											<br> Il modulo "Profilassi Leishmania" racchiude al suo
											interno le seguenti funzionalità:

											<ul>
												<li>Inserimento registrazioni di prelievo campioni
													Leishmania</li>
												<li>Recupero esiti esami leshmaniosi</li>
											</ul>

											<br></br> In particolare, attraverso il sottomodulo "Aggiungi
											registrazione prelievo campioni leishmania", gli LL.PP.
											avranno la possibilità di inserire le informazioni sui
											prelievi campioni effettuati e destinati all'invio per le
											richieste di esami leshmaniosi. I LP avranno la possibilità
											di inserire registrazioni di prelievo anche per i microchips
											non anagrafati direttamenti da loro. I dati necessari per
											l'inserimento di una registrazione di prelievo saranno:
											<ul>
												<li>Microchip dell'animale oggetto del prelievo</li>
												<li>La data di prelievo del campione</li>
												<li>Note eventuali</li>
											</ul>

											<br></br> L'utente sarà avvisato in caso di dati errati o
											mancanti. In particolare tutti i dati saranno obbligatori, ad
											eccezione delle note. Il sistema verificherà la correttezza
											del microchip , in particolare la correttezza formale, la
											presenza in banca dati, l'esistenza in uno stato corretto
											(animale vivente, di specie cane, non in uno stato di
											smarrimento o furto). Se il microchip supererà i contolli di
											cui sopra, si sbloccherà il campo per l'inserimento della
											data del prelievo e il veterinario potrà procedere con
											l'invio dei dati. Il sistema provvederà alla memorizzazione
											della registrazione e, successivamente, permetterà la stampa
											dei seguenti documenti: certificato di prelievo campioni
											(formato pdf) barcode relativo al microchip per cui è stato
											effettuato il prelievo (formato immagine) E' importante
											tenere presente che in seguito non sarà possibile la ristampa
											del certificato di prelievo, pertanto è opportuno stampare /
											salvare tale certificato subito dopo l'inserimento dei dati
											di prelievo. Il secondo sottomodulo a disposizione degli
											utenti, "Recupero esiti esami leshmaniosi", permetterà il
											recupero, per la visualizzazione, degli esiti già ricevuti da
											SIGLA / IZSM. La maschera di ricerca esiti prevederà i
											seguenti campi di ricerca: Valore esito Intervallo temporale
											Asl animale (attuale, che potrebbe essere diversa da quella
											all'inserimento dell'esito) I risultati della ricerca
											includeranno tutti gli esiti ricevuti per quei michrochips
											per i quali il LP ha effettuato almeno una richiesta di
											prelievo con data antecedente la data dell'esito,
											indipendentemente dal Veterinario prelevatore. Dalla pagina
											di lista degli esiti, cliccando sul microchip, il veterinario
											privato potrà accedere direttamente al dettaglio animale se
											si tratta di un cane che egli stesso ha anagrafato in banca
											dati. In caso contrario, il microchip non sarà cliccabile ma
											sarà visibile solo l'esito.

										</div>

										<div class="fine" style="height: 50px;">&nbsp;</div>

										<div class="fine" style="height: 50px;"></div>



										<dhv:permission name="passaporto-view">
											<a name="Passaporti"></a>

											<H1>Banca dati passaporti a priori</H1>
											<div id="content_guida">
												<br> Il modulo "Banca dati passaporti" prevede i
												seguenti moduli, abilitati in base ai permessi del ruolo
												utente collegato al sistema: </br>

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


												</br> </br>
												<H2>Regole di assegnazione passaporto</H2>

												All'atto dell'inserimento di una registrazione di
												rilascio/rinnovo passaporto il sistema controllerà che:
												<ul>
													<li>1. che la lunghezza del valore numerico inserito
														sia di 13 cifre</li>
													<li>2. che il valore numerico sia presente nella banca
														dati a priori, assegnato all'asl che sta rilasciando il
														passaporto e non utilizzato precedentemente.</li>
												</ul>



												In fase di inserimento nuova anagrafica animale non sarà
												possibile l'assegnazione di passaporti il cui valore risulta
												assegnato ad un'asl (precaricato nella banca dati apriori).
												Sarà quindi possibile l'assegnazione di un passaporto solo
												per gli animali provenienti da fuori regione e già dotati di
												un passaporto. Al salvataggio dei dati il sistema
												verificherà che il valore passaporto assegnato non
												appartenga alla banca dati a priori regionale, in caso
												affermativo impedirà all'utente di proseguire.
											</div>

											<div class="fine" style="height: 50px;">&nbsp;</div>

											<div class="fine" style="height: 50px;"></div>

										</dhv:permission>




										<a name="Gestione canili"></a>

										<H1>Gestione canili</H1>
										<div id="content_guida">
											<br> L'aggiunta di canili autorizzati dalla regione Campania è un'attività riservata esclusivamente all'help desk di primo livello. Le informazioni necessarie all'aggiunta di un canile sono 5:  </br></br>

											<ul>

												<li>Dati rappresentante legale</li>


												<li>Dati sede legale</li>


												<li>Dati responsabile stabilimento</li>


												<li>Dati sede operativa</li>
												
												<li>Informazioni generiche</li>



											</ul>

											<br></br> Per procedere all'aggiunta del canile, l'utente deve accedere alla sezione "Proprietari/Detentori", sottomodulo "Aggiungi Proprietario". Si presenterà un menù a tendina dal quale scegliere la tipologia di operatore che si vuole inserire. La tipologia corretta per aggiungere un canile è quella contrassegnata dalla voce "Canile". Per completare l'inserimento l'utente sarà guidato attraverso due sezioni: nella prima aggiungerà i dati legali (sede e rappresentante), nella seconda i dati della sede operativa(responsabile e sede). Nella seconda schermata saranno richieste anche le informazioni generiche, rappresentate nella figura seguente:
											 <IMG
												SRC="images/Gestione_Canili/info_aggiuntive.png"
												NAME="2.PNG" /></br></br>
											Le informazioni generiche non sono obbligatorie, ad eccezione del dato relativo alla superficie destinata al ricovero.
											Tale informazione è espressa in metri quadrati. I valori accettati sono solo numerici.
											Questa informazione è indispensabile alla funzionalità descritta nel seguito e relativa al controllo dell'occupazione dei canili.
											
											<IMG SRC="images/mover0103/7.png" NAME="7.PNG" />
											</br></br>
											<H2>Gestione controllo occupazione canili</H2>
											
											In fase di inserimento animale si avvia un controllo sull'occupazione delle superfici di un canile in base alle seguenti informazioni memorizzate nel sistema:
											</br></br>
											<ul>

												<li>Superficie a disposizione del canile destinata al ricovero</li>


												<li>Numero animali attualmente presenti in canile (vivi, non smarriti, non in stato di furto)</li>


												<li>Informazione occupazione singole taglie (espressa in mq, tabella 1.0)</li>

											</ul>
											
											</br></br>
											Già nella home page sarà mostrata in rosso la lista dei canili che risultano in sovraffollamento e per i quali consegue che "non sarà possibile registrare nuovi cani in ingresso se non ripristinano i valori di capienza".
											<IMG SRC="images/mover0103/8.png" NAME="8.PNG" />
											Il controllo è fatto in occasione di ogni ingresso al canile, quindi in fase di inserimento nuova anagrafica (randagio / privato) e in fase di registrazioni di movimentazioni animali verso la struttura. 
											L'area occupata viene indicata nella scheda dettaglio con il colore verde se l'indice di capienza è al di sotto dell'80%, in giallo se l'indice di capienza è tra l'80% e il 100% e rosso se esiste un sovraffollamento con percentuale maggiore del 100% .
											<IMG SRC="images/mover0103/9.png" NAME="9.PNG" />
											
											I cani inseriti occuperanno una superficie considerando la taglia presunta da adulti (vedi tabella 1.0) ma sarà considerata come piccola nel caso l'età  sia inferiore a 8 mesi.  
											Inoltre, nel caso di inserimento di cani privati (quindi non randagi), non sarà evidenziata la taglia e il peso ma solo la grandezza (piccola, media, grande e gigante).

											</br></br>
											<table border="1">
											<tr><th>Taglia</th><th>Occupazione (mq))</th>
											<tr><td>piccola (cani di peso non superiore a 2 kg)</td> <td align="center">2</td></tr>
											<tr><td>media (cani di peso non superiore a 8 kg)</td> <td align="center">3.5</td></tr>
											<tr><td>grande (cani di peso non superiore a 15kg)</td> <td align="center">4.5</td></tr>
											<tr><td>gigante (cani di peso superiore a 15 kg) </td> <td align="center">6</td></tr>
											
											</table>
											
											
										</div>

										<div class="fine" style="height: 50px;">&nbsp;</div>

										<div class="fine" style="height: 50px;"></div>


















									</div>
</body>
</html>