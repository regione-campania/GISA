<p align="center">
<img src="https://github.com/regcampania/GISA/blob/main/sicurezza_lavoro/docs/logo-regione-campania.png">
</p>

# Sicurezza e prevenzione sui luoghi di lavoro
- [1. Descrizione e finalità del software](#1-descrizione-e-finalità-del-software)
  - [1.1 Gestione notifiche](#11-gestione-notifiche)
  - [1.2 Descrizione della struttura repository](#12-descrizione-della-struttura-repository)
  - [1.3 Contesto di utilizzo e casi d’uso](#13-contesto-di-utilizzo-e-casi-duso)
  - [1.4 Piattaforme abilitanti](#14-piattaforme-abilitanti)
  - [1.5 Interoperabilità con i sistemi esterni](#15-interoperabilità-con-i-sistemi-esterni)
  - [1.6 Link a pagine istituzionali relative al progetto](#16-link-a-pagine-istituzionali-relative-al-progetto)
  - [1.7 Interfaccia web](#17-interfaccia-web)
- [2. Architettura del software](#2-architettura-del-software)
- [3. Requisiti](#3-requisiti)
  - [3.1 Tecnologie utilizzate lato server](#31-tecnologie-utilizzate-lato-server)
  - [3.2 Tecnologie utilizzate lato client](#32-tecnologie-utilizzate-lato-client)
- [4. Riuso ed installazione](#4-riuso-ed-installazione)
  - [4.1 Build dai sorgenti](#41-build-dai-sorgenti)
  - [4.2 Riuso nell’ambito della stessa regione](#42-riuso-nellambito-della-stessa-regione)
  - [4.3 Riuso per enti di altre regioni](#43-riuso-per-enti-di-altre-regioni)
  - [4.4 Librerie esterne](#44-librerie-esterne)
  - [4.5 Creazione e import database](#45-creazione-e-import-database)
  - [4.6 Templates configurazione](#46-templates-configurazione)
  - [4.7 Installazione in un ambiente di sviluppo](#47-Installazione-in-un-ambiente-di-sviluppo)
  - [4.8 Installazione in un ambiente di produzione](#48-Installazione-in-un-ambiente-di-produzione)
- [5. Licenza](#5-licenza)
  - [5.1 Sicurezza lavoro](#51-Sicurezza-lavoro)
  - [5.2 Indirizzo e-mail segnalazioni di sicurezza](#52-indirizzo-e-mail-segnalazioni-di-sicurezza)
  - [5.3 Titolarità: Regione Campania](#53-titolarità-regione-campania)

# **1. Descrizione e finalità del software**

Il servizio **G.I.S.A. - Sicurezza e prevenzione sui luoghi di lavoro** consta di due moduli software: 

- Gestione delle notifiche dei cantieri e delle imprese per la prevenzione e sicurezza sui luoghi di lavoro.

- Gestione delle ispezioni effettuate su cantiere/imprese.

Al momento è disponibile in riuso il modulo **Gestione delle notifiche** che entro giugno 2022 sarà rilasciato in riuso anche come app per IOS ed Android.
Successivamente la Regione Campania forinirà in riuso anche il modulo di **Gestione delle ispezioni**.



## 1.1 Gestione notifiche

Il sistema è **multi-browser** e **responsive** quindi in grado di adattarsi graficamente in modo automatico al dispositivo con il quale viene utilizzato
(computer con diverse risoluzioni, tablet, smartphone, ecc), riducendo al massimo la necessità dell'utente di ridimensionare e scorrere i contenuti.

Di seguito alcuni screenshot del sistema:

![screen](./docs/sicurezza.png)

Figura 1. Home Sicurezza lavoro

![screen](./docs/sicurezza_mobile.png)

Figura 2. Visualizzazione in versione mobile dell'applicativo



Il link diretto al portale è:
http://sca.gisacampania.it/sicurezzalavoro/


Il sistema **Sicurezza e prevenzione sui luoghi di lavoro** fa parte dell'Ecosistema GISA.



L’***Ecosistema*** ***GISA*** è composto da diversi componenti tra cui:

- Autenticazione tramite ***SPID/CIE*** 
- ***Gestione Integrata Notifiche Sanitarie***: modulo di gestione delle pratiche di registrazione/riconoscimento degli OSA (Operatore del Settore Alimentare) provenienti dai SUAP con possibilità di alimentazione tramite cooperazione applicativa.
- ***MATRIX***: modulo di gestione della programmazione a livello regionale, con il *Documento di programmazione Annuale Regionale (DPAR)*, e a livello territoriale, con il *Documento di programmazione Annuale Territoriale (DPAT)*. Esso consente di stimare il fabbisogno delle risorse umane necessarie a compiere le attività programmate.
- ***Gestione Controlli Ufficiali***: registrazione di tutti i dati relativi ai controlli ufficiali (tipo AUDIT, Ispezioni Semplici, Ispezioni in Sorveglianza), comprese le non conformità riscontrate, sanzioni amministrative applicate con generazione Avvisi di Pagamento PagoPA, sequestri, notizie di reato, follow up, ecc… con stampa della documentazione ufficiale precompilata (verbale ispezione, verbali di campionamenti, ecc…).
- ***Gestione Allerte Alimentari***: Registrazione di tutti i dati relativi ai controlli ufficiali effettuati per il sistema europeo RASFF (Rapid Alert System Food and Feed).
- ***Registro Trasgressori*** e ***Gestione degli illeciti amministrativi*** e delle ***Ordinanze regionali*** in cooperazione applicativa con PagoPA.
- ***Reportistica Avanzata***: cruscotto per la rendicontazione delle attività programmate con possibilità di verificare il lavoro svolto dalle singole strutture territoriali afferenti ai Dipartimenti di Prevenzione delle AA.SS.LL.
- ***Macellazioni***: modulo di gestione dei dati relativi alle singole sedute di macellazione (controllo documentale, visita *Ante Mortem*, visita *Post Mortem*, libero consumo, ecc…) con possibilità di stampa di documentazione ufficiale (Registro macellazione, articolo 17, modello 10, ecc…).
- Cooperazione Applicativa tra ecosistema ***GISA*** e ***Sistemi Nazionali*** per scambio dati su:
  - dati OSM 
  - dati checklist BA
  - dati scorta farmaci
  - dati anagrafe animali da compagnia 
  - dati checklist biosicurezza
  - dati checklist farmaco-sorveglianza
  - anagrafiche SINTESIS
  - anagrafiche Allevamenti
- ***Sistema di Autovalutazione** **del** **rischio***: modulo software che consente a imprese e operatori del settore alimentare e/o veterinari di migliorare la conduzione della propria attività, sia nelle modalità decisionali che gestionali, acquisendo consapevolezza dei punti di forza e di debolezza relativi alla/e propria/e attività. In particolare, consente di individuare e valutare il proprio livello di rischio rispondendo alle domande di una *check list* che fa parte del Sistema Ufficiale di Valutazione del Rischio delle imprese e che è utilizzata durante i controlli ufficiali delle AA.SS.LL. con riferimento alla propria linea di attività. L’accesso come ospite all’Autovalutazione può essere utile a coloro che hanno intenzione di avvicinarsi alle linee di attività del settore alimentare e/o veterinari e che desiderano conoscere le check list che verranno utilizzate dagli ispettori per i controlli.
- ***Veterinary Activity Management (VAM):*** Sistema per la gestione delle principali funzionalità relative ad ospedali e ambulatori veterinari pubblici della Regione Campania e di tutte le attività prestazionali effettuate su animali d’affezione e sinantropi. Il sistema gestisce il percorso clinico e chirurgico di ogni animale (cane, gatto e sinantropo) e in particolare:
  - Accettazione
  - Trasferimenti
  - Cartelle cliniche
  - Fascicoli sanitari
  - Registro tumori animali
  - Interazione con la Banca Dati Regionale dell’Anagrafe cani, gatti e furetti.
- ***Anagrafe Animali da compagnia (cani, gatti e furetti):*** Sistema dotato di:
  - **Accesso libero**
    che consente di verificare la registrazione nell’Anagrafe regionale di un’animale da compagnia identificato con microchip (transponder);
  - **Accesso riservato agli utenti autorizzati e adeguatamente profilati**
    che permette di gestire le informazioni anagrafiche degli animali e dei loro proprietari/detentori e le relative registrazioni.
- ***BDA-R***: Banca dati apistica regionale integrata in cooperazione applicativa con la Banca dati apistica Nazionale ***BDA-N***.
- ***Sicurezza e prevenzione sui luoghi di lavoro:*** componente per la gestione delle notifiche relative ai cantieri e alle imprese in ambito della sicurezza sui luoghi di lavoro.


![screen](./docs/Aspose.Words.cf9e318a-c0df-40c2-8eee-7557ad70dba9.002.png)


Figura 3. Schema su Piano Regionale Integrato


L’intero ecosistema prevede diverse tipologie di utenti che possono essere schematizzati nelle seguenti macrocategorie:

- Utenti istituzionali Regionali
- Utenti istituzionali afferenti ai Dipartimenti di Prevenzione delle AA.SS.LL.
- Utenti istituzionali non appartenenti alle AA.SS.LL. : Forze dell’ordine, Esercito, Università, CRIUV, ecc…
- Utenti liberi professionisti: Veterinari Liberi Professionisti o delegati apicoltori, ecc…
- Utenti imprenditori: apicoltori, trasportatori, ecc...
- Privati cittadini: utenti che accedono alla componente di *Autovalutazione*.

Più precisamente le tipologie di utenti che possono registrarsi in GISA sono le seguenti:

- **ASL**
- **Regione**
- **Centri Riferimento Regionali**
- **IZSM**
- **ARPAC**
- **Osservatori Regionali**
- **Forze dell'Ordine (Polizia stradale, Polizia municipale, Carabinieri, NAS, NAC, ICQ, Guardia forestale)**
- **Esercito**
- **Guardie Zoofile Prefettizie**
- **Guardie Zoofile Regionali**
- **Gestori Acque di rete**
- **Apicoltore Autoconsumo**
- **Apicoltore Commerciale**
- **Delegato Apicoltore / Associazione**
- **Gestore Trasporti**
- **Gestore Distributori (erogatori cibi e bevande)**
- **Medico Veterinario libero professionista**
- **Operatore Settore Alimentare per autovalutazione**
- **Direttore Sanitario Canile** 

I Responsabili dei cantieri non necessitano della registrazione all’ecosistema GISA e possono inoltrare la notifica accedendo con SPID/CIE.

Il totale stimato degli attuali utilizzatori è di circa **11.000** utenti (peraltro in continua crescita) distribuiti variamente sui sottosistemi.

Entro giugno 2022 saranno presenti in G.I.S.A. anche le tipologie di utenti della prevenzione e sicurezza sui luoghi di lavoro di seguito elencati e la gestione del workflow delle ispezioni sui luoghi di lavoro:

- **Profilo Notificatore**
- **Profilo Ispettore SIML**
- **Profilo Ispettore SPSAL**
- **Profilo Direttore SIML**
- **Profilo Direttore SPSAL**
- **Profilo Regione**
- **Profilo Ispettore UPG**
- **Profilo Ispettore non UPG**


![screen](./docs/Aspose.Words.cf9e318a-c0df-40c2-8eee-7557ad70dba9.001.png)

Figura 4. Schematizzazione dei moduli dell’Ecosistema G.I.S.A.






## **1.2 Descrizione della struttura repository**

  - _./database_   script sql per la creazione della struttura del DB 

  - _./docs_       documentazione varia (cartella contenente file integrati nel readme: immagini, diagrammi, ecc.)  

  - _./templates_   file template per la configurazione del sistema

  - _./sicurezza_lavoro_    sorgenti e struttura di cartelle della piattaforma 


## **1.3 Contesto di utilizzo e casi d’uso**

 Il contesto di utilizzo e casi d'uso del Software sono descritti dettagliatamente nella [guida utente](https://sca.gisacampania.it/sicurezzalavoro/Manuale.pdf)
 
## **1.4 Piattaforme abilitanti**

Le piattaforme abilitanti sono:
    - **Autenticazione SPID/CIE**
	
## **1.5 Interoperabilità con i sistemi esterni**

1. **SPID / CIE – Regione Campania**
	


## **1.6 Link a pagine istituzionali relative al progetto**

- [G.I.S.A. - Sicurezza e prevenzione sui luoghi di lavoro](https://sca.gisacampania.it/sicurezzalavoro)

## **1.7 Interfaccia web**

**GISA - Sicurezza lavoro** è dotato di un interfaccia web semplice e intuitiva.
Questa la form di creazione notifica: 

![screen](./docs/screenshot_3.png)


Figura 5. Rappresentazione form notifica



# **2. Architettura del software**

L'architettura software cioè l'organizzazione di base del sistema, espressa dalle sue componenti, dalle relazioni tra di loro e con l'ambiente, e i principi che ne guidano il progetto e l'evoluzione.



![archittetura](./docs/gisa_arch1.png)


Figura 6. Processo registrazione utenti notificatori


<!--

![screen](./docs/gisa_arch2.png)


Figura 7. Organizzazione di base dell'Ecosistema GISA

-->

# **3. Requisiti**

## **3.1 Tecnologie utilizzate lato server**
 - [Node.js  >= 16.14](https://nodejs.org/it/)
 - [Angular  >= 13.2](https://angular.io/)
 - [Postgres  12.x ](https://computingforgeeks.com/how-to-install-postgresql-12-on-centos-7/)
 
## **3.2 Tecnologie utilizzate lato client**
- [Windows](https://www.microsoft.com/it-it/software-download/) (dalla versione 10 in poi)
- **Portatile** e **Responsive** per il mobile.
- In fase di sviluppo le app per **Android** e **iOS**.


 

# **4. Riuso ed installazione**

## **4.1 Build dai sorgenti**

Tecnicamente **GISA - SICUREZZA LAVORO** è un'applicazione in architettura web sviluppata con linguaggi Javascript/Typescript secondo il pattern _MVC_ (model view controller).

Come le applicazioni di questo genere **GISA - SICUREZZA LAVORO** è quindi composta da un back-end in funzione di _Model_ (in questo caso l'_RDBMS_ Postgresql) 
una serie di risorse di front-end web (_Angular_) in funzione di _View_
e infine, lato back-end, un webserver _NodeJS_, con il suo framework _Express_.  

 
## 4.2 Riuso nell’ambito della stessa regione

Nell’ottica del risparmio e della razionalizzazione delle risorse è opportuno che gli enti che insistono sullo stesso territorio regionale utilizzino la modalità **Multi-Tenant** al fine di installare un unico sistema a livello regionale.





## **4.3 Riuso per enti di altre regioni**
Al fine di avvalersi dei benefici del riuso così come concepito dal **CAD** si chiede di notificarlo come indicato nel paragrafo 5.3 al fine di evitare sprechi e frammentazioni.

*Nota: Se lo scopo è avviare un processo di sviluppo per modificare la propria versione di **GISA - SICUREZZA LAVORO**, potrebbe essere il caso di generare prima un proprio fork su GitHub e quindi clonarlo.*

Eseguire il seguente comando:

        git clone \
			  --depth 1  \
			  --filter=blob:none  \
			  --sparse \
			  https://github.com/regcampania/GISA \
			;
			cd GISA
			git sparse-checkout set sicurezza_lavoro

Sarà creata la directory sicurezza_lavoro. Da qui in avanti si farà riferimento a questa directory chiamandola "directory base".




## **4.4 Librerie esterne**

Le dipendenze necessarie sono autoinstallanti tramite NPM (Node Package Manager), il gestore di pacchetti ufficiale di NodeJS:

```
cd sicurezza_lavoro/NodeJsServer/
npm install
cd ../AngularClient/
npm install
```


## **4.5 Creazione e import database**


Assicurarsi che nel file di configurazione pg_hba.conf sia correttamente configurato l'accesso dell'IP del nodo Tomcat al database: 
```
 host         all         all       <ipapplicativo>       trust
```
```
systemctl reload postgresql-12.service
```

Creazione Database e import dello schema tramite i seguenti comandi, con _dbuser_ e _dbhost_ adeguatamente valorizzati :

```
psql -U <dbuser> -h <dbhost> -c "create database sicurezza_lavoro "
```

Posizionarsi nella directory _sicurezza_lavoro_ ed eseguire il comando: 

```
psql -U <dbuser> -h <dbhost> -d sicurezza_lavoro < database/sicurezzalavoro.sql
```



## **4.6 Templates configurazione**

Sotto la directory _./templates/NodeJsServer_ è presente il file template _config.js_ , da configurare ed inserire sotto la directory _./sicurezza_lavoro/NodeJsServer/config/_

la directory config deve essere creata con il comando: 

```
cd sicurezza_lavoro/NodeJsServer/
mkdir config
```


Sotto la directory _./templates/AngularClient_ è presente il file template _environment.ts_ (_environment.prod.ts_ per produzione), da configurare ed inserire sotto la directory _./sicurezza_lavoro/AngularClient/src/environments/_

la directory environments deve essere creata con il comando: 
```
cd sicurezza_lavoro/AngularClient/src/
mkdir environments
```


## **4.7 Installazione in un ambiente di sviluppo**

- Clonare il repository _SICUREZZA_LAVORO_ (Vedi paragrafo: 4.3 Riuso per enti di altre regioni):

        git clone \
			  --depth 1  \
			  --filter=blob:none  \
			  --sparse \
			  https://github.com/regcampania/GISA \
			;
			cd GISA
			git sparse-checkout set sicurezza_lavoro
		
- Installare _Visual_  _Studio_ _Code_ disponibile al seguente indirizzo: https://code.visualstudio.com/ , e importare i due progetti _NodeJsServer_ e _AngularClient_ .

- Creare ed importare database (Vedi paragrafo: Creazione e import database )

- Configurare i templates (Vedi paragrafo: Templates configurazione)

- Installare dipendenze (Vedi paragrafo: Librerie esterne)

- Lanciare il server NodeJS:
```
cd sicurezza_lavoro/NodeJsServer/
node main.js
```


- Lanciare il client Angular in modalità sviluppatore:
```
cd sicurezza_lavoro/AngularClient/
ng serve 
```


## **4.8 Installazione in un ambiente di produzione**

- Clonare il repository _SICUREZZA_LAVORO_ (Vedi paragrafo: 4.3 Riuso per enti di altre regioni):

        git clone \
			  --depth 1  \
			  --filter=blob:none  \
			  --sparse \
			  https://github.com/regcampania/GISA \
			;
			cd GISA
			git sparse-checkout set sicurezza_lavoro
		
- Creare ed importare database (Vedi paragrafo: Creazione e import database )

- Configurare i templates (Vedi paragrafo: Templates configurazione)

- Installare dipendenze (Vedi paragrafo: Librerie esterne)

- Compilare il client Angular:
```
ng build --configuration=production
```

- Installare PM2 (https://pm2.keymetrics.io/)
```
npm install pm2 -g
```

- Lanciare il server NodeJS con PM2:
```
pm2 start main.js -i max --name sicurezzaLavoro --time --production
```


# **5. Licenza**

## **5.1 Sicurezza lavoro**

Stato Software : Stabile

**Soggetti incaricati del mantenimento del progetto open source**

U.S. s.r.l. 
## **5.2 Indirizzo e-mail segnalazioni di sicurezza**
Ogni segnalazione di eventuali problemi di sicurezza o bug relativo al software presente in questo repository, va segnalato unicamente tramite e-mail agli indirizzi presente nel file security.txt disponibile a questo [link](http://www.gisacampania.it/.well-known/security.txt)

NOTA: Le segnalazioni non vanno inviate attraverso l’issue tracker pubblico ma devono essere inviate confidenzialmente agli indirizzi e-mail presenti nel security.txt.

Lo strumento issue tracker può essere utilizzato per le richieste di modifiche necessarie per implementare nuove funzionalità.

## **5.3 Titolarità: [Regione Campania](http://www.regione.campania.it/)**
Concesso in licenza a norma di: **AGPL versione 3**;

E' possibile utilizzare l'opera unicamente nel rispetto della Licenza.

Una copia della Licenza è disponibile al seguente indirizzo: <https://www.gnu.org/licenses/agpl-3.0.txt>

**NOTE:**

In caso di riuso, in toto o in parte, dell’ecosistema software G.I.S.A., è necessario notificare l’adozione in riuso tramite l’apertura di un ticket (o analogo meccanismo quale una pull request) in questo repository. Inoltre, al contempo per gli aspetti organizzativi utili a potenziare i benefici derivanti dalla pratica del riuso tra PP.AA., come la partecipazione alla **Cabina di regia** per la condivisione di eventuali modifiche/integrazioni o innovazioni, è necessario darne tempestiva comunicazione alle seguenti e-mail:

[paolo.sarnelli@regione.campania.it]() 

[cinzia.matonti@regione.campania.it]()	

Gli enti che aderiscono al riuso di GISA entreranno a far parte della Cabina di Regia per condividere e partecipare all’evoluzione di GISA insieme alle altre PP.AA.