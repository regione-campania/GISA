<?php


$_CONFIG["db_guc"] = "";  

$_CONFIG["db_port"] = "";
$_CONFIG["db_user"] = "";
$_CONFIG["db_psw"] = "";
 
date_default_timezone_set("Europe/Rome");

$_CONFIG["ruoli"] = '{
    "tipologia_utente":[
       {
          "id":"1",
          "descr":"ASL"
       },
       {
          "id":"10",
          "descr":"Regione"
       },
       {
          "id":"15",
          "descr":"Centri Riferimento"
       },
       {
          "id":"12",
          "descr":"IZSM"
       },
       {
          "id":"16",
          "descr":"ARPAC"
       },
       {
          "id":"17",
          "descr":"Osservatori"
       },
       {
          "id":"2",
          "descr":"Forze dell\'Ordine"
       },
       {
          "id":"19",
          "descr":"Esercito \/ Forze Armate"
       },
       {
          "id":"11",
          "descr":"Guardie Zoofile Prefettizie"
       },
       {
          "id":"18",
          "descr":"Guardie Zoofile Regionali"
       },
       {
          "id":"3",
          "descr":"Gestori Acque di rete"
       },
       {
          "id":"4",
          "descr":"Apicoltore Autoconsumo"
       },
       {
          "id":"5",
          "descr":"Apicoltore Commerciale"
       },
       {
          "id":"6",
          "descr":"Delegato Apicoltore \/ Associazione"
       },
       {
          "id":"7",
          "descr":"Gestore Trasporti"
       },
       {
          "id":"20",
          "descr":"Gestore Distributori"
       },
       {
          "id":"9",
          "descr":"Veterinario libero professionista"
       },
       {
          "id":"13",
          "descr":"Operatore settore Alimentare per autovalutazione"
       },
       {
          "id":"14",
          "descr":"Direttore sanitario Canile"
       }
    ],
    "tipo_richiesta":[
       {
          "id":"1",
          "descr":"Creazione Account \/ Ruolo",
          "title":"Utilizzare questa funzionalit\u00e0 per creare una nuova utenza o aggiungere un nuovo ruolo per Account (codice fiscale) gi\u00e0 presenti "
       },
       {
          "id":"2",
          "descr":"Modifica ruolo ASL",
          "title":"Utilizzare questa funzionalit\u00e0 per modificare un ruolo gi\u00e0 assegnato ad un account con un altro ruolo"
       },
       {
          "id":"3",
          "descr":"Eliminazione Account \/ Ruolo",
          "title":"Utilizzare questa funzionalit\u00e0 in base al proprio account sar\u00e0 possibile eliminare un ruolo valido e associato (al proprio codice fiscale) fino a quel momento"
       }
    ],
    "asl":[
       {
          "id":"201",
          "descr":"AVELLINO"
       },
       {
          "id":"202",
          "descr":"BENEVENTO"
       },
       {
          "id":"203",
          "descr":"CASERTA"
       },
       {
          "id":"204",
          "descr":"NAPOLI 1 CENTRO"
       },
       {
          "id":"205",
          "descr":"NAPOLI 2 NORD"
       },
       {
          "id":"206",
          "descr":"NAPOLI 3 SUD"
       },
       {
          "id":"207",
          "descr":"SALERNO"
       }
    ],
    "ruoli_bdu":[
       {
          "id":"31",
          "descr":"Utente Canile"
       }
    ],
    "ruoli_asl_bdu":[
       {
          "id":"20",
          "descr":"Amministratore asl"
       },
       {
          "id":"18",
          "descr":"Anagrafe Canina"
       },
       {
          "id":"34",
          "descr":"Referente asl"
       },
       {
          "id":"36",
          "descr":"Referenti UOC"
       },
       {
          "id":"29",
          "descr":"Veterinario Area C"
       }
    ],
    "ruoli_regione_bdu":[
       {
          "id":"32",
          "descr":"UtenteRegione"
       }
    ],
    "ruoli_bdu_liberi_prof":[
       {
          "id":"24",
          "descr":"Veterinario Privato"
       }
    ],
    "ruoli_zoofile_bdu":[
       {
          "id":"35",
          "descr":"Guardie zoofile"
       }
    ],
    "ruoli_gisa":[
       {
          "id":"38",
          "descr":"Altre Autorita\' e Forze dell\'Ordine"
       },
       {
          "id":"96",
          "descr":"Altro Funzionario Laureato"
       },
       {
          "id":"44",
          "descr":"Amministrativi ASL"
       },
       {
          "id":"25",
          "descr":"Amministrativi Extra ASL"
       },
       {
          "id":"1",
          "descr":"Amministratore Sistema HD II livello"
       },
       {
          "id":"32",
          "descr":"Amministratore Sistema HD I Livello"
       },
       {
          "id":"328",
          "descr":"BDA-Reg"
       },
       {
          "id":"3368",
          "descr":"CERVENE"
       },
       {
          "id":"3342",
          "descr":"COAP"
       },
       {
          "id":"3371",
          "descr":"Controllo PSR"
       },
       {
          "id":"3367",
          "descr":"CRESAN"
       },
       {
          "id":"3369",
          "descr":"CRIPAT - PAT"
       },
       {
          "id":"3370",
          "descr":"CRIPAT - RIST. COLL."
       },
       {
          "id":"3366",
          "descr":"C.Ri.S.SA.P."
       },
       {
          "id":"3365",
          "descr":"CRIUV"
       },
       {
          "id":"59",
          "descr":"delegato asl"
       },
       {
          "id":"16",
          "descr":"Direttori Dipartimento di Prevenzione"
       },
       {
          "id":"40",
          "descr":"Funzionari Regionali"
       },
       {
          "id":"326",
          "descr":"Funzionari Regionali addetti al Registro Trasgressori GRUPPO 1"
       },
       {
          "id":"327",
          "descr":"Funzionari Regionali addetti al Registro Trasgressori GRUPPO 2"
       },
       {
          "id":"3372",
          "descr":"Gestore Macelli"
       },
       {
          "id":"3343",
          "descr":"Gestore Scarrabili"
       },
       {
          "id":"329",
          "descr":"Gestore schede supplementari"
       },
       {
          "id":"49",
          "descr":"GUEST"
       },
       {
          "id":"3340",
          "descr":"IZSM-Campania Trasparente"
       },
       {
          "id":"50",
          "descr":"Laboratori HACCP Manager"
       },
       {
          "id":"221",
          "descr":"MEDICI SPECIALISTI AMBULATORIALI"
       },
       {
          "id":"222",
          "descr":"medici veterinari specialisti"
       },
       {
          "id":"42",
          "descr":"Medico"
       },
       {
          "id":"3364",
          "descr":"MEDICO RESPONSABILE STRUT"
       },
       {
          "id":"21",
          "descr":"Medico - Responsabile Struttura Complessa"
       },
       {
          "id":"45",
          "descr":"Medico - Responsabile Struttura Semplice"
       },
       {
          "id":"97",
          "descr":"Medico - Responsabile Struttura Semplice Dipartimentale"
       },
       {
          "id":"43",
          "descr":"Medico Veterinario"
       },
       {
          "id":"19",
          "descr":"Medico Veterinario - Responsabile Struttura Complessa"
       },
       {
          "id":"46",
          "descr":"Medico Veterinario - Responsabile Struttura Semplice"
       },
       {
          "id":"98",
          "descr":"Medico Veterinario - Responsabile Struttura Semplice Dipartimentale"
       },
       {
          "id":"3339",
          "descr":"NON CONFORMITA POSTICIPATA"
       },
       {
          "id":"324",
          "descr":"NU.RE.CU."
       },
       {
          "id":"37",
          "descr":"Operatori Allerte REGIONE Sian"
       },
       {
          "id":"47",
          "descr":"Operatori Allerte REGIONE Veterinari"
       },
       {
          "id":"31",
          "descr":"ORSA"
       },
       {
          "id":"53",
          "descr":"ORSA DIREZIONE"
       },
       {
          "id":"100",
          "descr":"ORSA GESTIONE AUDIT PREGRESSI"
       },
       {
          "id":"3373",
          "descr":"POLO DIDATTICO INTEGRATO"
       },
       {
          "id":"33",
          "descr":"Referente Allerte ASL"
       },
       {
          "id":"3374",
          "descr":"Referente Regione"
       },
       {
          "id":"56",
          "descr":"Referenti ASL"
       },
       {
          "id":"3344",
          "descr":"Regione CU Temp"
       },
       {
          "id":"27",
          "descr":"Responsabile Regione"
       },
       {
          "id":"3341",
          "descr":"RUOLO DEMO"
       },
       {
          "id":"41",
          "descr":"T.P.A.L"
       }
    ],
    "ruoli_gisa_izsm":[
       {
          "id":"3340",
          "descr":"IZSM-Campania Trasparente"
       }
    ],
    "ruoli_apicoltore":[
       {
          "id":"10000002",
          "descr":"BDApi",
          "ext":true
       }
    ],
    "ruoli_delegato_apicoltore":[
       {
          "id":"10000001",
          "descr":"DELEGATO APICOLTORE",
          "ext":true
       }
    ],
    "ruoli_gestori_acque":[
       {
          "id":"10000006",
          "descr":"GESTORE ACQUE",
          "ext":true
       }
    ],
    "ruoli_trasporti":[
       {
          "id":"10000004",
          "descr":"GESTORE AUTOMEZZI\/DISTRIBUTORI",
          "ext":true
       }
    ],
    "ruoli_esercito":[
       {
          "id":"10000009",
          "descr":"ESERCITO ITALIANO",
          "ext":true
       }
    ],
    "ruoli_forze":[
       {
          "id":"10000005",
          "descr":"Carabinieri di Comando\/St",
          "ext":true
       },
       {
          "id":"224",
          "descr":"C.F.S. AV",
          "ext":true
       },
       {
          "id":"225",
          "descr":"C.F.S. BN",
          "ext":true
       },
       {
          "id":"226",
          "descr":"C.F.S. CE",
          "ext":true
       },
       {
          "id":"227",
          "descr":"C.F.S. NA",
          "ext":true
       },
       {
          "id":"228",
          "descr":"C.F.S. SA",
          "ext":true
       },
       {
          "id":"223",
          "descr":"CORPO FORESTALE DELLO STATO",
          "ext":true
       },
       {
          "id":"116",
          "descr":"GUARDIA COSTIERA",
          "ext":true
       },
       {
          "id":"115",
          "descr":"GUARDIA DI FINANZA",
          "ext":true
       },
       {
          "id":"113",
          "descr":"NAC",
          "ext":true
       },
       {
          "id":"118",
          "descr":"NAS CE",
          "ext":true
       },
       {
          "id":"1020",
          "descr":"NAS NA",
          "ext":true
       },
       {
          "id":"1123",
          "descr":"NAS SA",
          "ext":true
       },
       {
          "id":"114",
          "descr":"NOE",
          "ext":true
       },
       {
          "id":"105",
          "descr":"PIF",
          "ext":true
       },
       {
          "id":"10000000",
          "descr":"POLIZIA",
          "ext":true
       },
       {
          "id":"110",
          "descr":"POLIZIA MUNICIPALE",
          "ext":true
       },
       {
          "id":"111",
          "descr":"POLIZIA PROVINCIALE",
          "ext":true
       },
       {
          "id":"1019",
          "descr":"Polizia Stradale",
          "ext":true
       }
    ],
    "ruoli_arpac":[
       {
          "id":"117",
          "descr":"ARPAC",
          "ext":true
       }
    ],
    "gestori_acque":[
       {
          "id":"2",
          "nome":"ABC"
       },
       {
          "id":"19",
          "nome":"ACQUEDOTTO CAMPANO EX CASPEZ"
       },
       {
          "id":"10",
          "nome":"ACQUEDOTTO PUGLIESE"
       },
       {
          "id":"23",
          "nome":"ALTO CALORE"
       },
       {
          "id":"6",
          "nome":"ARIN"
       },
       {
          "id":"26",
          "nome":"ASIS SALERNITANA"
       },
       {
          "id":"47",
          "nome":"AUSINO"
       },
       {
          "id":"70",
          "nome":"Casal Servizi S.R.L."
       },
       {
          "id":"43",
          "nome":"COMUNE AMOROSI"
       },
       {
          "id":"50",
          "nome":"COMUNE BACOLI"
       },
       {
          "id":"68",
          "nome":"COMUNE BAGNOLI IRPINO"
       },
       {
          "id":"1",
          "nome":"COMUNE BASELICE"
       },
       {
          "id":"36",
          "nome":"COMUNE CALABRITTO"
       },
       {
          "id":"34",
          "nome":"COMUNE CAMPOLI DEL MONTE TABURNO"
       },
       {
          "id":"42",
          "nome":"COMUNE CAMPOLI MONTE TABURNO"
       },
       {
          "id":"24",
          "nome":"COMUNE CARIFE"
       },
       {
          "id":"20",
          "nome":"COMUNE CASALDUNI"
       },
       {
          "id":"7",
          "nome":"COMUNE CASTELFRANCO IN MISCANO"
       },
       {
          "id":"44",
          "nome":"COMUNE CASTELVETERE IN VAL FORTORE"
       },
       {
          "id":"21",
          "nome":"COMUNE CERRETO SANNITA"
       },
       {
          "id":"15",
          "nome":"COMUNE CIRCELLO"
       },
       {
          "id":"52",
          "nome":"COMUNE COLLIANO"
       },
       {
          "id":"30",
          "nome":"COMUNE CUSANO MUTRI"
       },
       {
          "id":"62",
          "nome":"Comune di Avella"
       },
       {
          "id":"61",
          "nome":"Comune di Baiano"
       },
       {
          "id":"69",
          "nome":"COMUNE DI CARDITO"
       },
       {
          "id":"59",
          "nome":"Comune di Frattaminore"
       },
       {
          "id":"64",
          "nome":"Comune di Palomonte"
       },
       {
          "id":"67",
          "nome":"COMUNE DI POZZUOLI"
       },
       {
          "id":"60",
          "nome":"Comune di Serino"
       },
       {
          "id":"18",
          "nome":"COMUNE DUGENTA"
       },
       {
          "id":"4",
          "nome":"COMUNE GINESTRA DEGLI SCHIAVONI"
       },
       {
          "id":"3",
          "nome":"COMUNE LIMATOLA"
       },
       {
          "id":"17",
          "nome":"COMUNE MARZANO APPIO"
       },
       {
          "id":"11",
          "nome":"COMUNE MOIANO"
       },
       {
          "id":"66",
          "nome":"COMUNE MONTE DI PROCIDA"
       },
       {
          "id":"37",
          "nome":"COMUNE MONTEFALCONE DI VAL FORTORE"
       },
       {
          "id":"8",
          "nome":"COMUNE MORCONE"
       },
       {
          "id":"63",
          "nome":"COMUNE MUGNANO DI NAPOLI"
       },
       {
          "id":"41",
          "nome":"COMUNE PIETRAROJA"
       },
       {
          "id":"31",
          "nome":"COMUNE PUGLIANELLO"
       },
       {
          "id":"12",
          "nome":"COMUNE SAN LORENZELLO"
       },
       {
          "id":"28",
          "nome":"COMUNE SAN LUPO"
       },
       {
          "id":"13",
          "nome":"COMUNE SAN MARCO DEI CAVOTI"
       },
       {
          "id":"39",
          "nome":"COMUNE SAN SALVATORE TELESINO"
       },
       {
          "id":"25",
          "nome":"COMUNE SASSINORO"
       },
       {
          "id":"32",
          "nome":"COMUNE SOLOFRA"
       },
       {
          "id":"5",
          "nome":"COMUNE SPERONE"
       },
       {
          "id":"9",
          "nome":"COMUNE TOCCO CAUDIO"
       },
       {
          "id":"54",
          "nome":"CONSAC GESTIONI IDRICHE SPA"
       },
       {
          "id":"27",
          "nome":"CONSORZIO ACQUEDOTTO FRAGNETO MONFORTE FRAGNETO L\'ABATE"
       },
       {
          "id":"53",
          "nome":"CONSORZIO GESTIONE SERVIZI S.C.A.R.L."
       },
       {
          "id":"45",
          "nome":"CONSORZIO IDRICO TERRA DI LAVORO CASERTA"
       },
       {
          "id":"56",
          "nome":"COSTRAME"
       },
       {
          "id":"40",
          "nome":"EVI"
       },
       {
          "id":"14",
          "nome":"GESAC"
       },
       {
          "id":"22",
          "nome":"GE.SE.SA."
       },
       {
          "id":"46",
          "nome":"GORI"
       },
       {
          "id":"48",
          "nome":"Idra Porto s.r.l."
       },
       {
          "id":"51",
          "nome":"Idrotech"
       },
       {
          "id":"57",
          "nome":"Italgas Acqua S.p.A."
       },
       {
          "id":"49",
          "nome":"ITALGAS RETI"
       },
       {
          "id":"35",
          "nome":"OTTOGAS"
       },
       {
          "id":"33",
          "nome":"REGIONECAMPANIA"
       },
       {
          "id":"16",
          "nome":"SALERNO SISTEMI"
       },
       {
          "id":"29",
          "nome":"SCPA"
       },
       {
          "id":"58",
          "nome":"Servizio Idrico Integrato del Comune di Quarto"
       },
       {
          "id":"38",
          "nome":"SOLOFRA SERVIZI SPA"
       }
    ],
    "comuni":[
       {
          "istat":"065001",
          "nome":"ACERNO",
          "id_asl_comune":"207"
       },
       {
          "istat":"063001",
          "nome":"ACERRA",
          "id_asl_comune":"205"
       },
       {
          "istat":"063002",
          "nome":"AFRAGOLA",
          "id_asl_comune":"205"
       },
       {
          "istat":"063003",
          "nome":"AGEROLA",
          "id_asl_comune":"206"
       },
       {
          "istat":"065002",
          "nome":"AGROPOLI",
          "id_asl_comune":"207"
       },
       {
          "istat":"064001",
          "nome":"AIELLO DEL SABATO",
          "id_asl_comune":"201"
       },
       {
          "istat":"061001",
          "nome":"AILANO",
          "id_asl_comune":"203"
       },
       {
          "istat":"062001",
          "nome":"AIROLA",
          "id_asl_comune":"202"
       },
       {
          "istat":"065003",
          "nome":"ALBANELLA",
          "id_asl_comune":"207"
       },
       {
          "istat":"065004",
          "nome":"ALFANO",
          "id_asl_comune":"207"
       },
       {
          "istat":"061002",
          "nome":"ALIFE",
          "id_asl_comune":"203"
       },
       {
          "istat":"064002",
          "nome":"ALTAVILLA IRPINA",
          "id_asl_comune":"201"
       },
       {
          "istat":"065005",
          "nome":"ALTAVILLA SILENTINA",
          "id_asl_comune":"207"
       },
       {
          "istat":"061003",
          "nome":"ALVIGNANO",
          "id_asl_comune":"203"
       },
       {
          "istat":"065006",
          "nome":"AMALFI",
          "id_asl_comune":"207"
       },
       {
          "istat":"062002",
          "nome":"AMOROSI",
          "id_asl_comune":"202"
       },
       {
          "istat":"063004",
          "nome":"ANACAPRI",
          "id_asl_comune":"204"
       },
       {
          "istat":"064003",
          "nome":"ANDRETTA",
          "id_asl_comune":"201"
       },
       {
          "istat":"065007",
          "nome":"ANGRI",
          "id_asl_comune":"207"
       },
       {
          "istat":"062003",
          "nome":"APICE",
          "id_asl_comune":"202"
       },
       {
          "istat":"062004",
          "nome":"APOLLOSA",
          "id_asl_comune":"202"
       },
       {
          "istat":"065008",
          "nome":"AQUARA",
          "id_asl_comune":"207"
       },
       {
          "istat":"064004",
          "nome":"AQUILONIA",
          "id_asl_comune":"201"
       },
       {
          "istat":"064005",
          "nome":"ARIANO IRPINO",
          "id_asl_comune":"201"
       },
       {
          "istat":"061004",
          "nome":"ARIENZO",
          "id_asl_comune":"203"
       },
       {
          "istat":"062005",
          "nome":"ARPAIA",
          "id_asl_comune":"202"
       },
       {
          "istat":"062006",
          "nome":"ARPAISE",
          "id_asl_comune":"202"
       },
       {
          "istat":"063005",
          "nome":"ARZANO",
          "id_asl_comune":"205"
       },
       {
          "istat":"065009",
          "nome":"ASCEA",
          "id_asl_comune":"207"
       },
       {
          "istat":"065010",
          "nome":"ATENA LUCANA",
          "id_asl_comune":"207"
       },
       {
          "istat":"065011",
          "nome":"ATRANI",
          "id_asl_comune":"207"
       },
       {
          "istat":"064006",
          "nome":"ATRIPALDA",
          "id_asl_comune":"201"
       },
       {
          "istat":"065012",
          "nome":"AULETTA",
          "id_asl_comune":"207"
       },
       {
          "istat":"064007",
          "nome":"AVELLA",
          "id_asl_comune":"201"
       },
       {
          "istat":"064008",
          "nome":"AVELLINO",
          "id_asl_comune":"201"
       },
       {
          "istat":"061005",
          "nome":"AVERSA",
          "id_asl_comune":"203"
       },
       {
          "istat":"063006",
          "nome":"BACOLI",
          "id_asl_comune":"205"
       },
       {
          "istat":"064009",
          "nome":"BAGNOLI IRPINO",
          "id_asl_comune":"201"
       },
       {
          "istat":"061006",
          "nome":"BAIA E LATINA",
          "id_asl_comune":"203"
       },
       {
          "istat":"064010",
          "nome":"BAIANO",
          "id_asl_comune":"201"
       },
       {
          "istat":"063007",
          "nome":"BARANO D\'ISCHIA",
          "id_asl_comune":"205"
       },
       {
          "istat":"065013",
          "nome":"BARONISSI",
          "id_asl_comune":"207"
       },
       {
          "istat":"062007",
          "nome":"BASELICE",
          "id_asl_comune":"202"
       },
       {
          "istat":"065014",
          "nome":"BATTIPAGLIA",
          "id_asl_comune":"207"
       },
       {
          "istat":"065158",
          "nome":"BELLIZZI",
          "id_asl_comune":"207"
       },
       {
          "istat":"061007",
          "nome":"BELLONA",
          "id_asl_comune":"203"
       },
       {
          "istat":"065015",
          "nome":"BELLOSGUARDO",
          "id_asl_comune":"207"
       },
       {
          "istat":"062008",
          "nome":"BENEVENTO",
          "id_asl_comune":"202"
       },
       {
          "istat":"064011",
          "nome":"BISACCIA",
          "id_asl_comune":"201"
       },
       {
          "istat":"062009",
          "nome":"BONEA",
          "id_asl_comune":"202"
       },
       {
          "istat":"064012",
          "nome":"BONITO",
          "id_asl_comune":"201"
       },
       {
          "istat":"063008",
          "nome":"BOSCOREALE",
          "id_asl_comune":"206"
       },
       {
          "istat":"063009",
          "nome":"BOSCOTRECASE",
          "id_asl_comune":"206"
       },
       {
          "istat":"065016",
          "nome":"BRACIGLIANO",
          "id_asl_comune":"207"
       },
       {
          "istat":"063010",
          "nome":"BRUSCIANO",
          "id_asl_comune":"206"
       },
       {
          "istat":"062010",
          "nome":"BUCCIANO",
          "id_asl_comune":"202"
       },
       {
          "istat":"065017",
          "nome":"BUCCINO",
          "id_asl_comune":"207"
       },
       {
          "istat":"065018",
          "nome":"BUONABITACOLO",
          "id_asl_comune":"207"
       },
       {
          "istat":"062011",
          "nome":"BUONALBERGO",
          "id_asl_comune":"202"
       },
       {
          "istat":"065019",
          "nome":"CAGGIANO",
          "id_asl_comune":"207"
       },
       {
          "istat":"061008",
          "nome":"CAIANELLO",
          "id_asl_comune":"203"
       },
       {
          "istat":"061009",
          "nome":"CAIAZZO",
          "id_asl_comune":"203"
       },
       {
          "istat":"064013",
          "nome":"CAIRANO",
          "id_asl_comune":"201"
       },
       {
          "istat":"063011",
          "nome":"CAIVANO",
          "id_asl_comune":"205"
       },
       {
          "istat":"064014",
          "nome":"CALABRITTO",
          "id_asl_comune":"201"
       },
       {
          "istat":"064015",
          "nome":"CALITRI",
          "id_asl_comune":"201"
       },
       {
          "istat":"065020",
          "nome":"CALVANICO",
          "id_asl_comune":"207"
       },
       {
          "istat":"062012",
          "nome":"CALVI",
          "id_asl_comune":"202"
       },
       {
          "istat":"061010",
          "nome":"CALVI RISORTA",
          "id_asl_comune":"203"
       },
       {
          "istat":"063012",
          "nome":"CALVIZZANO",
          "id_asl_comune":"205"
       },
       {
          "istat":"065021",
          "nome":"CAMEROTA",
          "id_asl_comune":"207"
       },
       {
          "istat":"061011",
          "nome":"CAMIGLIANO",
          "id_asl_comune":"203"
       },
       {
          "istat":"065022",
          "nome":"CAMPAGNA",
          "id_asl_comune":"207"
       },
       {
          "istat":"062013",
          "nome":"CAMPOLATTARO",
          "id_asl_comune":"202"
       },
       {
          "istat":"062014",
          "nome":"CAMPOLI DEL MONTE TABURNO",
          "id_asl_comune":"202"
       },
       {
          "istat":"065023",
          "nome":"CAMPORA",
          "id_asl_comune":"207"
       },
       {
          "istat":"063013",
          "nome":"CAMPOSANO",
          "id_asl_comune":"206"
       },
       {
          "istat":"061012",
          "nome":"CANCELLO ED ARNONE",
          "id_asl_comune":"203"
       },
       {
          "istat":"064016",
          "nome":"CANDIDA",
          "id_asl_comune":"201"
       },
       {
          "istat":"065024",
          "nome":"CANNALONGA",
          "id_asl_comune":"207"
       },
       {
          "istat":"065025",
          "nome":"CAPACCIO",
          "id_asl_comune":"207"
       },
       {
          "istat":"061013",
          "nome":"CAPODRISE",
          "id_asl_comune":"203"
       },
       {
          "istat":"064017",
          "nome":"CAPOSELE",
          "id_asl_comune":"201"
       },
       {
          "istat":"063014",
          "nome":"CAPRI",
          "id_asl_comune":"204"
       },
       {
          "istat":"061014",
          "nome":"CAPRIATI A VOLTURNO",
          "id_asl_comune":"203"
       },
       {
          "istat":"064018",
          "nome":"CAPRIGLIA IRPINA",
          "id_asl_comune":"201"
       },
       {
          "istat":"061015",
          "nome":"CAPUA",
          "id_asl_comune":"203"
       },
       {
          "istat":"063015",
          "nome":"CARBONARA DI NOLA",
          "id_asl_comune":"206"
       },
       {
          "istat":"063016",
          "nome":"CARDITO",
          "id_asl_comune":"205"
       },
       {
          "istat":"064019",
          "nome":"CARIFE",
          "id_asl_comune":"201"
       },
       {
          "istat":"061016",
          "nome":"CARINARO",
          "id_asl_comune":"203"
       },
       {
          "istat":"061017",
          "nome":"CARINOLA",
          "id_asl_comune":"203"
       },
       {
          "istat":"061018",
          "nome":"CASAGIOVE",
          "id_asl_comune":"203"
       },
       {
          "istat":"064020",
          "nome":"CASALBORE",
          "id_asl_comune":"201"
       },
       {
          "istat":"065026",
          "nome":"CASALBUONO",
          "id_asl_comune":"207"
       },
       {
          "istat":"061019",
          "nome":"CASAL DI PRINCIPE",
          "id_asl_comune":"203"
       },
       {
          "istat":"062015",
          "nome":"CASALDUNI",
          "id_asl_comune":"202"
       },
       {
          "istat":"065027",
          "nome":"CASALETTO SPARTANO",
          "id_asl_comune":"207"
       },
       {
          "istat":"063017",
          "nome":"CASALNUOVO DI NAPOLI",
          "id_asl_comune":"205"
       },
       {
          "istat":"061020",
          "nome":"CASALUCE",
          "id_asl_comune":"203"
       },
       {
          "istat":"065028",
          "nome":"CASAL VELINO",
          "id_asl_comune":"207"
       },
       {
          "istat":"063018",
          "nome":"CASAMARCIANO",
          "id_asl_comune":"206"
       },
       {
          "istat":"063019",
          "nome":"CASAMICCIOLA TERME",
          "id_asl_comune":"205"
       },
       {
          "istat":"063020",
          "nome":"CASANDRINO",
          "id_asl_comune":"205"
       },
       {
          "istat":"061103",
          "nome":"CASAPESENNA",
          "id_asl_comune":"203"
       },
       {
          "istat":"061021",
          "nome":"CASAPULLA",
          "id_asl_comune":"203"
       },
       {
          "istat":"063021",
          "nome":"CASAVATORE",
          "id_asl_comune":"205"
       },
       {
          "istat":"065029",
          "nome":"CASELLE IN PITTARI",
          "id_asl_comune":"207"
       },
       {
          "istat":"061022",
          "nome":"CASERTA",
          "id_asl_comune":"203"
       },
       {
          "istat":"063022",
          "nome":"CASOLA DI NAPOLI",
          "id_asl_comune":"206"
       },
       {
          "istat":"063023",
          "nome":"CASORIA",
          "id_asl_comune":"205"
       },
       {
          "istat":"064021",
          "nome":"CASSANO IRPINO",
          "id_asl_comune":"201"
       },
       {
          "istat":"064022",
          "nome":"CASTEL BARONIA",
          "id_asl_comune":"201"
       },
       {
          "istat":"061023",
          "nome":"CASTEL CAMPAGNANO",
          "id_asl_comune":"203"
       },
       {
          "istat":"065030",
          "nome":"CASTELCIVITA",
          "id_asl_comune":"207"
       },
       {
          "istat":"061024",
          "nome":"CASTEL DI SASSO",
          "id_asl_comune":"203"
       },
       {
          "istat":"064023",
          "nome":"CASTELFRANCI",
          "id_asl_comune":"201"
       },
       {
          "istat":"062016",
          "nome":"CASTELFRANCO IN MISCANO",
          "id_asl_comune":"202"
       },
       {
          "istat":"065031",
          "nome":"CASTELLABATE",
          "id_asl_comune":"207"
       },
       {
          "istat":"063024",
          "nome":"CASTELLAMMARE DI STABIA",
          "id_asl_comune":"206"
       },
       {
          "istat":"061025",
          "nome":"CASTELLO DEL MATESE",
          "id_asl_comune":"203"
       },
       {
          "istat":"063025",
          "nome":"CASTELLO DI CISTERNA",
          "id_asl_comune":"206"
       },
       {
          "istat":"061026",
          "nome":"CASTEL MORRONE",
          "id_asl_comune":"203"
       },
       {
          "istat":"065032",
          "nome":"CASTELNUOVO CILENTO",
          "id_asl_comune":"207"
       },
       {
          "istat":"065033",
          "nome":"CASTELNUOVO DI CONZA",
          "id_asl_comune":"207"
       },
       {
          "istat":"062017",
          "nome":"CASTELPAGANO",
          "id_asl_comune":"202"
       },
       {
          "istat":"062018",
          "nome":"CASTELPOTO",
          "id_asl_comune":"202"
       },
       {
          "istat":"065034",
          "nome":"CASTEL SAN GIORGIO",
          "id_asl_comune":"207"
       },
       {
          "istat":"065035",
          "nome":"CASTEL SAN LORENZO",
          "id_asl_comune":"207"
       },
       {
          "istat":"062019",
          "nome":"CASTELVENERE",
          "id_asl_comune":"202"
       },
       {
          "istat":"062020",
          "nome":"CASTELVETERE IN VAL FORTORE",
          "id_asl_comune":"202"
       },
       {
          "istat":"064024",
          "nome":"CASTELVETERE SUL CALORE",
          "id_asl_comune":"201"
       },
       {
          "istat":"061027",
          "nome":"CASTEL VOLTURNO",
          "id_asl_comune":"203"
       },
       {
          "istat":"065036",
          "nome":"CASTIGLIONE DEL GENOVESI",
          "id_asl_comune":"207"
       },
       {
          "istat":"062021",
          "nome":"CAUTANO",
          "id_asl_comune":"202"
       },
       {
          "istat":"065037",
          "nome":"CAVA DE\' TIRRENI",
          "id_asl_comune":"207"
       },
       {
          "istat":"065038",
          "nome":"CELLE DI BULGHERIA",
          "id_asl_comune":"207"
       },
       {
          "istat":"061102",
          "nome":"CELLOLE",
          "id_asl_comune":"203"
       },
       {
          "istat":"065039",
          "nome":"CENTOLA",
          "id_asl_comune":"207"
       },
       {
          "istat":"062022",
          "nome":"CEPPALONI",
          "id_asl_comune":"202"
       },
       {
          "istat":"065040",
          "nome":"CERASO",
          "id_asl_comune":"207"
       },
       {
          "istat":"063026",
          "nome":"CERCOLA",
          "id_asl_comune":"206"
       },
       {
          "istat":"062023",
          "nome":"CERRETO SANNITA",
          "id_asl_comune":"202"
       },
       {
          "istat":"064025",
          "nome":"CERVINARA",
          "id_asl_comune":"201"
       },
       {
          "istat":"061028",
          "nome":"CERVINO",
          "id_asl_comune":"203"
       },
       {
          "istat":"061029",
          "nome":"CESA",
          "id_asl_comune":"203"
       },
       {
          "istat":"064026",
          "nome":"CESINALI",
          "id_asl_comune":"201"
       },
       {
          "istat":"065041",
          "nome":"CETARA",
          "id_asl_comune":"207"
       },
       {
          "istat":"064027",
          "nome":"CHIANCHE",
          "id_asl_comune":"201"
       },
       {
          "istat":"064028",
          "nome":"CHIUSANO DI SAN DOMENICO",
          "id_asl_comune":"201"
       },
       {
          "istat":"063027",
          "nome":"CICCIANO",
          "id_asl_comune":"206"
       },
       {
          "istat":"065042",
          "nome":"CICERALE",
          "id_asl_comune":"207"
       },
       {
          "istat":"063028",
          "nome":"CIMITILE",
          "id_asl_comune":"206"
       },
       {
          "istat":"061030",
          "nome":"CIORLANO",
          "id_asl_comune":"203"
       },
       {
          "istat":"062024",
          "nome":"CIRCELLO",
          "id_asl_comune":"202"
       },
       {
          "istat":"062025",
          "nome":"COLLE SANNITA",
          "id_asl_comune":"202"
       },
       {
          "istat":"065043",
          "nome":"COLLIANO",
          "id_asl_comune":"207"
       },
       {
          "istat":"063029",
          "nome":"COMIZIANO",
          "id_asl_comune":"206"
       },
       {
          "istat":"065044",
          "nome":"CONCA DEI MARINI",
          "id_asl_comune":"207"
       },
       {
          "istat":"061031",
          "nome":"CONCA DELLA CAMPANIA",
          "id_asl_comune":"203"
       },
       {
          "istat":"064029",
          "nome":"CONTRADA",
          "id_asl_comune":"201"
       },
       {
          "istat":"065045",
          "nome":"CONTRONE",
          "id_asl_comune":"207"
       },
       {
          "istat":"065046",
          "nome":"CONTURSI TERME",
          "id_asl_comune":"207"
       },
       {
          "istat":"064030",
          "nome":"CONZA DELLA CAMPANIA",
          "id_asl_comune":"201"
       },
       {
          "istat":"065047",
          "nome":"CORBARA",
          "id_asl_comune":"207"
       },
       {
          "istat":"065048",
          "nome":"CORLETO MONFORTE",
          "id_asl_comune":"207"
       },
       {
          "istat":"063030",
          "nome":"CRISPANO",
          "id_asl_comune":"205"
       },
       {
          "istat":"065049",
          "nome":"CUCCARO VETERE",
          "id_asl_comune":"207"
       },
       {
          "istat":"061032",
          "nome":"CURTI",
          "id_asl_comune":"203"
       },
       {
          "istat":"062026",
          "nome":"CUSANO MUTRI",
          "id_asl_comune":"202"
       },
       {
          "istat":"064031",
          "nome":"DOMICELLA",
          "id_asl_comune":"201"
       },
       {
          "istat":"061033",
          "nome":"DRAGONI",
          "id_asl_comune":"203"
       },
       {
          "istat":"062027",
          "nome":"DUGENTA",
          "id_asl_comune":"202"
       },
       {
          "istat":"062028",
          "nome":"DURAZZANO",
          "id_asl_comune":"202"
       },
       {
          "istat":"065050",
          "nome":"EBOLI",
          "id_asl_comune":"207"
       },
       {
          "istat":"063064",
          "nome":"ERCOLANO",
          "id_asl_comune":"206"
       },
       {
          "istat":"062029",
          "nome":"FAICCHIO",
          "id_asl_comune":"202"
       },
       {
          "istat":"061101",
          "nome":"FALCIANO DEL MASSICO",
          "id_asl_comune":"203"
       },
       {
          "istat":"065051",
          "nome":"FELITTO",
          "id_asl_comune":"207"
       },
       {
          "istat":"065052",
          "nome":"FISCIANO",
          "id_asl_comune":"207"
       },
       {
          "istat":"064032",
          "nome":"FLUMERI",
          "id_asl_comune":"201"
       },
       {
          "istat":"062030",
          "nome":"FOGLIANISE",
          "id_asl_comune":"202"
       },
       {
          "istat":"062031",
          "nome":"FOIANO DI VAL FORTORE",
          "id_asl_comune":"202"
       },
       {
          "istat":"064033",
          "nome":"FONTANAROSA",
          "id_asl_comune":"201"
       },
       {
          "istat":"061034",
          "nome":"FONTEGRECA",
          "id_asl_comune":"203"
       },
       {
          "istat":"062032",
          "nome":"FORCHIA",
          "id_asl_comune":"202"
       },
       {
          "istat":"064034",
          "nome":"FORINO",
          "id_asl_comune":"201"
       },
       {
          "istat":"063031",
          "nome":"FORIO",
          "id_asl_comune":"205"
       },
       {
          "istat":"061035",
          "nome":"FORMICOLA",
          "id_asl_comune":"203"
       },
       {
          "istat":"062033",
          "nome":"FRAGNETO L\'ABATE",
          "id_asl_comune":"202"
       },
       {
          "istat":"062034",
          "nome":"FRAGNETO MONFORTE",
          "id_asl_comune":"202"
       },
       {
          "istat":"061036",
          "nome":"FRANCOLISE",
          "id_asl_comune":"203"
       },
       {
          "istat":"062035",
          "nome":"FRASSO TELESINO",
          "id_asl_comune":"202"
       },
       {
          "istat":"063032",
          "nome":"FRATTAMAGGIORE",
          "id_asl_comune":"205"
       },
       {
          "istat":"063033",
          "nome":"FRATTAMINORE",
          "id_asl_comune":"205"
       },
       {
          "istat":"064035",
          "nome":"FRIGENTO",
          "id_asl_comune":"201"
       },
       {
          "istat":"061037",
          "nome":"FRIGNANO",
          "id_asl_comune":"203"
       },
       {
          "istat":"065053",
          "nome":"FURORE",
          "id_asl_comune":"207"
       },
       {
          "istat":"065054",
          "nome":"FUTANI",
          "id_asl_comune":"207"
       },
       {
          "istat":"061038",
          "nome":"GALLO MATESE",
          "id_asl_comune":"203"
       },
       {
          "istat":"061039",
          "nome":"GALLUCCIO",
          "id_asl_comune":"203"
       },
       {
          "istat":"064036",
          "nome":"GESUALDO",
          "id_asl_comune":"201"
       },
       {
          "istat":"061040",
          "nome":"GIANO VETUSTO",
          "id_asl_comune":"203"
       },
       {
          "istat":"065055",
          "nome":"GIFFONI SEI CASALI",
          "id_asl_comune":"207"
       },
       {
          "istat":"065056",
          "nome":"GIFFONI VALLE PIANA",
          "id_asl_comune":"207"
       },
       {
          "istat":"062036",
          "nome":"GINESTRA DEGLI SCHIAVONI",
          "id_asl_comune":"202"
       },
       {
          "istat":"065057",
          "nome":"GIOI",
          "id_asl_comune":"207"
       },
       {
          "istat":"061041",
          "nome":"GIOIA SANNITICA",
          "id_asl_comune":"203"
       },
       {
          "istat":"063034",
          "nome":"GIUGLIANO IN CAMPANIA",
          "id_asl_comune":"205"
       },
       {
          "istat":"065058",
          "nome":"GIUNGANO",
          "id_asl_comune":"207"
       },
       {
          "istat":"063035",
          "nome":"GRAGNANO",
          "id_asl_comune":"206"
       },
       {
          "istat":"061042",
          "nome":"GRAZZANISE",
          "id_asl_comune":"203"
       },
       {
          "istat":"064037",
          "nome":"GRECI",
          "id_asl_comune":"201"
       },
       {
          "istat":"061043",
          "nome":"GRICIGNANO DI AVERSA",
          "id_asl_comune":"203"
       },
       {
          "istat":"064038",
          "nome":"GROTTAMINARDA",
          "id_asl_comune":"201"
       },
       {
          "istat":"064039",
          "nome":"GROTTOLELLA",
          "id_asl_comune":"201"
       },
       {
          "istat":"063036",
          "nome":"GRUMO NEVANO",
          "id_asl_comune":"205"
       },
       {
          "istat":"064040",
          "nome":"GUARDIA LOMBARDI",
          "id_asl_comune":"201"
       },
       {
          "istat":"062037",
          "nome":"GUARDIA SANFRAMONDI",
          "id_asl_comune":"202"
       },
       {
          "istat":"063037",
          "nome":"ISCHIA",
          "id_asl_comune":"205"
       },
       {
          "istat":"065059",
          "nome":"ISPANI",
          "id_asl_comune":"207"
       },
       {
          "istat":"063038",
          "nome":"LACCO AMENO",
          "id_asl_comune":"205"
       },
       {
          "istat":"064041",
          "nome":"LACEDONIA",
          "id_asl_comune":"201"
       },
       {
          "istat":"064042",
          "nome":"LAPIO",
          "id_asl_comune":"201"
       },
       {
          "istat":"065060",
          "nome":"LAUREANA CILENTO",
          "id_asl_comune":"207"
       },
       {
          "istat":"065061",
          "nome":"LAURINO",
          "id_asl_comune":"207"
       },
       {
          "istat":"065062",
          "nome":"LAURITO",
          "id_asl_comune":"207"
       },
       {
          "istat":"064043",
          "nome":"LAURO",
          "id_asl_comune":"201"
       },
       {
          "istat":"065063",
          "nome":"LAVIANO",
          "id_asl_comune":"207"
       },
       {
          "istat":"061044",
          "nome":"LETINO",
          "id_asl_comune":"203"
       },
       {
          "istat":"063039",
          "nome":"LETTERE",
          "id_asl_comune":"206"
       },
       {
          "istat":"061045",
          "nome":"LIBERI",
          "id_asl_comune":"203"
       },
       {
          "istat":"062038",
          "nome":"LIMATOLA",
          "id_asl_comune":"202"
       },
       {
          "istat":"064044",
          "nome":"LIONI",
          "id_asl_comune":"201"
       },
       {
          "istat":"063040",
          "nome":"LIVERI",
          "id_asl_comune":"206"
       },
       {
          "istat":"064045",
          "nome":"LUOGOSANO",
          "id_asl_comune":"201"
       },
       {
          "istat":"061046",
          "nome":"LUSCIANO",
          "id_asl_comune":"203"
       },
       {
          "istat":"065064",
          "nome":"LUSTRA",
          "id_asl_comune":"207"
       },
       {
          "istat":"061047",
          "nome":"MACERATA CAMPANIA",
          "id_asl_comune":"203"
       },
       {
          "istat":"061048",
          "nome":"MADDALONI",
          "id_asl_comune":"203"
       },
       {
          "istat":"065065",
          "nome":"MAGLIANO VETERE",
          "id_asl_comune":"207"
       },
       {
          "istat":"065066",
          "nome":"MAIORI",
          "id_asl_comune":"207"
       },
       {
          "istat":"064046",
          "nome":"MANOCALZATI",
          "id_asl_comune":"201"
       },
       {
          "istat":"063041",
          "nome":"MARANO DI NAPOLI",
          "id_asl_comune":"205"
       },
       {
          "istat":"061049",
          "nome":"MARCIANISE",
          "id_asl_comune":"203"
       },
       {
          "istat":"063042",
          "nome":"MARIGLIANELLA",
          "id_asl_comune":"206"
       },
       {
          "istat":"063043",
          "nome":"MARIGLIANO",
          "id_asl_comune":"206"
       },
       {
          "istat":"061050",
          "nome":"MARZANO APPIO",
          "id_asl_comune":"203"
       },
       {
          "istat":"064047",
          "nome":"MARZANO DI NOLA",
          "id_asl_comune":"201"
       },
       {
          "istat":"063092",
          "nome":"MASSA DI SOMMA",
          "id_asl_comune":"206"
       },
       {
          "istat":"063044",
          "nome":"MASSA LUBRENSE",
          "id_asl_comune":"206"
       },
       {
          "istat":"063045",
          "nome":"MELITO DI NAPOLI",
          "id_asl_comune":"205"
       },
       {
          "istat":"064048",
          "nome":"MELITO IRPINO",
          "id_asl_comune":"201"
       },
       {
          "istat":"062039",
          "nome":"MELIZZANO",
          "id_asl_comune":"202"
       },
       {
          "istat":"065067",
          "nome":"MERCATO SAN SEVERINO",
          "id_asl_comune":"207"
       },
       {
          "istat":"064049",
          "nome":"MERCOGLIANO",
          "id_asl_comune":"201"
       },
       {
          "istat":"063046",
          "nome":"META",
          "id_asl_comune":"206"
       },
       {
          "istat":"061051",
          "nome":"MIGNANO MONTE LUNGO",
          "id_asl_comune":"203"
       },
       {
          "istat":"065068",
          "nome":"MINORI",
          "id_asl_comune":"207"
       },
       {
          "istat":"064050",
          "nome":"MIRABELLA ECLANO",
          "id_asl_comune":"201"
       },
       {
          "istat":"062040",
          "nome":"MOIANO",
          "id_asl_comune":"202"
       },
       {
          "istat":"065069",
          "nome":"MOIO DELLA CIVITELLA",
          "id_asl_comune":"207"
       },
       {
          "istat":"062041",
          "nome":"MOLINARA",
          "id_asl_comune":"202"
       },
       {
          "istat":"061052",
          "nome":"MONDRAGONE",
          "id_asl_comune":"203"
       },
       {
          "istat":"064051",
          "nome":"MONTAGUTO",
          "id_asl_comune":"201"
       },
       {
          "istat":"065070",
          "nome":"MONTANO ANTILIA",
          "id_asl_comune":"207"
       },
       {
          "istat":"064052",
          "nome":"MONTECALVO IRPINO",
          "id_asl_comune":"201"
       },
       {
          "istat":"065071",
          "nome":"MONTECORICE",
          "id_asl_comune":"207"
       },
       {
          "istat":"065072",
          "nome":"MONTECORVINO PUGLIANO",
          "id_asl_comune":"207"
       },
       {
          "istat":"065073",
          "nome":"MONTECORVINO ROVELLA",
          "id_asl_comune":"207"
       },
       {
          "istat":"063047",
          "nome":"MONTE DI PROCIDA",
          "id_asl_comune":"205"
       },
       {
          "istat":"064053",
          "nome":"MONTEFALCIONE",
          "id_asl_comune":"201"
       },
       {
          "istat":"062042",
          "nome":"MONTEFALCONE DI VAL FORTORE",
          "id_asl_comune":"202"
       },
       {
          "istat":"065074",
          "nome":"MONTEFORTE CILENTO",
          "id_asl_comune":"207"
       },
       {
          "istat":"064054",
          "nome":"MONTEFORTE IRPINO",
          "id_asl_comune":"201"
       },
       {
          "istat":"064055",
          "nome":"MONTEFREDANE",
          "id_asl_comune":"201"
       },
       {
          "istat":"064056",
          "nome":"MONTEFUSCO",
          "id_asl_comune":"201"
       },
       {
          "istat":"064057",
          "nome":"MONTELLA",
          "id_asl_comune":"201"
       },
       {
          "istat":"064058",
          "nome":"MONTEMARANO",
          "id_asl_comune":"201"
       },
       {
          "istat":"064059",
          "nome":"MONTEMILETTO",
          "id_asl_comune":"201"
       },
       {
          "istat":"065075",
          "nome":"MONTE SAN GIACOMO",
          "id_asl_comune":"207"
       },
       {
          "istat":"065076",
          "nome":"MONTESANO SULLA MARCELLANA",
          "id_asl_comune":"207"
       },
       {
          "istat":"062043",
          "nome":"MONTESARCHIO",
          "id_asl_comune":"202"
       },
       {
          "istat":"064060",
          "nome":"MONTEVERDE",
          "id_asl_comune":"201"
       },
       {
          "istat":"064121",
          "nome":"Montoro",
          "id_asl_comune":"201"
       },
       {
          "istat":"062044",
          "nome":"MORCONE",
          "id_asl_comune":"202"
       },
       {
          "istat":"065077",
          "nome":"MORIGERATI",
          "id_asl_comune":"207"
       },
       {
          "istat":"064063",
          "nome":"MORRA DE SANCTIS",
          "id_asl_comune":"201"
       },
       {
          "istat":"064064",
          "nome":"MOSCHIANO",
          "id_asl_comune":"201"
       },
       {
          "istat":"064065",
          "nome":"MUGNANO DEL CARDINALE",
          "id_asl_comune":"201"
       },
       {
          "istat":"063048",
          "nome":"MUGNANO DI NAPOLI",
          "id_asl_comune":"205"
       },
       {
          "istat":"063049",
          "nome":"NAPOLI",
          "id_asl_comune":"204"
       },
       {
          "istat":"065078",
          "nome":"NOCERA INFERIORE",
          "id_asl_comune":"207"
       },
       {
          "istat":"065079",
          "nome":"NOCERA SUPERIORE",
          "id_asl_comune":"207"
       },
       {
          "istat":"063050",
          "nome":"NOLA",
          "id_asl_comune":"206"
       },
       {
          "istat":"065080",
          "nome":"NOVI VELIA",
          "id_asl_comune":"207"
       },
       {
          "istat":"064066",
          "nome":"NUSCO",
          "id_asl_comune":"201"
       },
       {
          "istat":"065081",
          "nome":"OGLIASTRO CILENTO",
          "id_asl_comune":"207"
       },
       {
          "istat":"065082",
          "nome":"OLEVANO SUL TUSCIANO",
          "id_asl_comune":"207"
       },
       {
          "istat":"065083",
          "nome":"OLIVETO CITRA",
          "id_asl_comune":"207"
       },
       {
          "istat":"065084",
          "nome":"OMIGNANO",
          "id_asl_comune":"207"
       },
       {
          "istat":"065085",
          "nome":"ORRIA",
          "id_asl_comune":"207"
       },
       {
          "istat":"061053",
          "nome":"ORTA DI ATELLA",
          "id_asl_comune":"203"
       },
       {
          "istat":"064067",
          "nome":"OSPEDALETTO D\'ALPINOLO",
          "id_asl_comune":"201"
       },
       {
          "istat":"065086",
          "nome":"OTTATI",
          "id_asl_comune":"207"
       },
       {
          "istat":"063051",
          "nome":"OTTAVIANO",
          "id_asl_comune":"206"
       },
       {
          "istat":"065087",
          "nome":"PADULA",
          "id_asl_comune":"207"
       },
       {
          "istat":"062045",
          "nome":"PADULI",
          "id_asl_comune":"202"
       },
       {
          "istat":"065088",
          "nome":"PAGANI",
          "id_asl_comune":"207"
       },
       {
          "istat":"064068",
          "nome":"PAGO DEL VALLO DI LAURO",
          "id_asl_comune":"201"
       },
       {
          "istat":"062046",
          "nome":"PAGO VEIANO",
          "id_asl_comune":"202"
       },
       {
          "istat":"063052",
          "nome":"PALMA CAMPANIA",
          "id_asl_comune":"206"
       },
       {
          "istat":"065089",
          "nome":"PALOMONTE",
          "id_asl_comune":"207"
       },
       {
          "istat":"062047",
          "nome":"PANNARANO",
          "id_asl_comune":"202"
       },
       {
          "istat":"062048",
          "nome":"PAOLISI",
          "id_asl_comune":"202"
       },
       {
          "istat":"061054",
          "nome":"PARETE",
          "id_asl_comune":"203"
       },
       {
          "istat":"064069",
          "nome":"PAROLISE",
          "id_asl_comune":"201"
       },
       {
          "istat":"061055",
          "nome":"PASTORANO",
          "id_asl_comune":"203"
       },
       {
          "istat":"064070",
          "nome":"PATERNOPOLI",
          "id_asl_comune":"201"
       },
       {
          "istat":"062049",
          "nome":"PAUPISI",
          "id_asl_comune":"202"
       },
       {
          "istat":"065090",
          "nome":"PELLEZZANO",
          "id_asl_comune":"207"
       },
       {
          "istat":"065091",
          "nome":"PERDIFUMO",
          "id_asl_comune":"207"
       },
       {
          "istat":"065092",
          "nome":"PERITO",
          "id_asl_comune":"207"
       },
       {
          "istat":"065093",
          "nome":"PERTOSA",
          "id_asl_comune":"207"
       },
       {
          "istat":"062050",
          "nome":"PESCO SANNITA",
          "id_asl_comune":"202"
       },
       {
          "istat":"065094",
          "nome":"PETINA",
          "id_asl_comune":"207"
       },
       {
          "istat":"064071",
          "nome":"PETRURO IRPINO",
          "id_asl_comune":"201"
       },
       {
          "istat":"065095",
          "nome":"PIAGGINE",
          "id_asl_comune":"207"
       },
       {
          "istat":"061056",
          "nome":"PIANA DI MONTE VERNA",
          "id_asl_comune":"203"
       },
       {
          "istat":"063053",
          "nome":"PIANO DI SORRENTO",
          "id_asl_comune":"206"
       },
       {
          "istat":"061057",
          "nome":"PIEDIMONTE MATESE",
          "id_asl_comune":"203"
       },
       {
          "istat":"064072",
          "nome":"PIETRADEFUSI",
          "id_asl_comune":"201"
       },
       {
          "istat":"061058",
          "nome":"PIETRAMELARA",
          "id_asl_comune":"203"
       },
       {
          "istat":"062051",
          "nome":"PIETRAROJA",
          "id_asl_comune":"202"
       },
       {
          "istat":"064073",
          "nome":"PIETRASTORNINA",
          "id_asl_comune":"201"
       },
       {
          "istat":"061059",
          "nome":"PIETRAVAIRANO",
          "id_asl_comune":"203"
       },
       {
          "istat":"062052",
          "nome":"PIETRELCINA",
          "id_asl_comune":"202"
       },
       {
          "istat":"061060",
          "nome":"PIGNATARO MAGGIORE",
          "id_asl_comune":"203"
       },
       {
          "istat":"063054",
          "nome":"PIMONTE",
          "id_asl_comune":"206"
       },
       {
          "istat":"065096",
          "nome":"PISCIOTTA",
          "id_asl_comune":"207"
       },
       {
          "istat":"063055",
          "nome":"POGGIOMARINO",
          "id_asl_comune":"206"
       },
       {
          "istat":"065097",
          "nome":"POLLA",
          "id_asl_comune":"207"
       },
       {
          "istat":"063056",
          "nome":"POLLENA TROCCHIA",
          "id_asl_comune":"206"
       },
       {
          "istat":"065098",
          "nome":"POLLICA",
          "id_asl_comune":"207"
       },
       {
          "istat":"063057",
          "nome":"POMIGLIANO D\'ARCO",
          "id_asl_comune":"206"
       },
       {
          "istat":"063058",
          "nome":"POMPEI",
          "id_asl_comune":"206"
       },
       {
          "istat":"062053",
          "nome":"PONTE",
          "id_asl_comune":"202"
       },
       {
          "istat":"065099",
          "nome":"PONTECAGNANO FAIANO",
          "id_asl_comune":"207"
       },
       {
          "istat":"062054",
          "nome":"PONTELANDOLFO",
          "id_asl_comune":"202"
       },
       {
          "istat":"061061",
          "nome":"PONTELATONE",
          "id_asl_comune":"203"
       },
       {
          "istat":"063059",
          "nome":"PORTICI",
          "id_asl_comune":"206"
       },
       {
          "istat":"061062",
          "nome":"PORTICO DI CASERTA",
          "id_asl_comune":"203"
       },
       {
          "istat":"065100",
          "nome":"POSITANO",
          "id_asl_comune":"207"
       },
       {
          "istat":"065101",
          "nome":"POSTIGLIONE",
          "id_asl_comune":"207"
       },
       {
          "istat":"063060",
          "nome":"POZZUOLI",
          "id_asl_comune":"205"
       },
       {
          "istat":"065102",
          "nome":"PRAIANO",
          "id_asl_comune":"207"
       },
       {
          "istat":"064074",
          "nome":"PRATA DI PRINCIPATO ULTRA",
          "id_asl_comune":"201"
       },
       {
          "istat":"061063",
          "nome":"PRATA SANNITA",
          "id_asl_comune":"203"
       },
       {
          "istat":"061064",
          "nome":"PRATELLA",
          "id_asl_comune":"203"
       },
       {
          "istat":"064075",
          "nome":"PRATOLA SERRA",
          "id_asl_comune":"201"
       },
       {
          "istat":"061065",
          "nome":"PRESENZANO",
          "id_asl_comune":"203"
       },
       {
          "istat":"065103",
          "nome":"PRIGNANO CILENTO",
          "id_asl_comune":"207"
       },
       {
          "istat":"063061",
          "nome":"PROCIDA",
          "id_asl_comune":"205"
       },
       {
          "istat":"062055",
          "nome":"PUGLIANELLO",
          "id_asl_comune":"202"
       },
       {
          "istat":"064076",
          "nome":"QUADRELLE",
          "id_asl_comune":"201"
       },
       {
          "istat":"063062",
          "nome":"QUALIANO",
          "id_asl_comune":"205"
       },
       {
          "istat":"063063",
          "nome":"QUARTO",
          "id_asl_comune":"205"
       },
       {
          "istat":"064077",
          "nome":"QUINDICI",
          "id_asl_comune":"201"
       },
       {
          "istat":"065104",
          "nome":"RAVELLO",
          "id_asl_comune":"207"
       },
       {
          "istat":"061066",
          "nome":"RAVISCANINA",
          "id_asl_comune":"203"
       },
       {
          "istat":"061067",
          "nome":"RECALE",
          "id_asl_comune":"203"
       },
       {
          "istat":"062056",
          "nome":"REINO",
          "id_asl_comune":"202"
       },
       {
          "istat":"061068",
          "nome":"RIARDO",
          "id_asl_comune":"203"
       },
       {
          "istat":"065105",
          "nome":"RICIGLIANO",
          "id_asl_comune":"207"
       },
       {
          "istat":"064078",
          "nome":"ROCCABASCERANA",
          "id_asl_comune":"201"
       },
       {
          "istat":"065106",
          "nome":"ROCCADASPIDE",
          "id_asl_comune":"207"
       },
       {
          "istat":"061069",
          "nome":"ROCCA D\'EVANDRO",
          "id_asl_comune":"203"
       },
       {
          "istat":"065107",
          "nome":"ROCCAGLORIOSA",
          "id_asl_comune":"207"
       },
       {
          "istat":"061070",
          "nome":"ROCCAMONFINA",
          "id_asl_comune":"203"
       },
       {
          "istat":"065108",
          "nome":"ROCCAPIEMONTE",
          "id_asl_comune":"207"
       },
       {
          "istat":"063065",
          "nome":"ROCCARAINOLA",
          "id_asl_comune":"206"
       },
       {
          "istat":"061071",
          "nome":"ROCCAROMANA",
          "id_asl_comune":"203"
       },
       {
          "istat":"064079",
          "nome":"ROCCA SAN FELICE",
          "id_asl_comune":"201"
       },
       {
          "istat":"061072",
          "nome":"ROCCHETTA E CROCE",
          "id_asl_comune":"203"
       },
       {
          "istat":"065109",
          "nome":"ROFRANO",
          "id_asl_comune":"207"
       },
       {
          "istat":"065110",
          "nome":"ROMAGNANO AL MONTE",
          "id_asl_comune":"207"
       },
       {
          "istat":"065111",
          "nome":"ROSCIGNO",
          "id_asl_comune":"207"
       },
       {
          "istat":"064080",
          "nome":"ROTONDI",
          "id_asl_comune":"201"
       },
       {
          "istat":"065112",
          "nome":"RUTINO",
          "id_asl_comune":"207"
       },
       {
          "istat":"061073",
          "nome":"RUVIANO",
          "id_asl_comune":"203"
       },
       {
          "istat":"065113",
          "nome":"SACCO",
          "id_asl_comune":"207"
       },
       {
          "istat":"065114",
          "nome":"SALA CONSILINA",
          "id_asl_comune":"207"
       },
       {
          "istat":"065115",
          "nome":"SALENTO",
          "id_asl_comune":"207"
       },
       {
          "istat":"065116",
          "nome":"SALERNO",
          "id_asl_comune":"207"
       },
       {
          "istat":"065117",
          "nome":"SALVITELLE",
          "id_asl_comune":"207"
       },
       {
          "istat":"064081",
          "nome":"SALZA IRPINA",
          "id_asl_comune":"201"
       },
       {
          "istat":"062057",
          "nome":"SAN BARTOLOMEO IN GALDO",
          "id_asl_comune":"202"
       },
       {
          "istat":"061074",
          "nome":"SAN CIPRIANO D\'AVERSA",
          "id_asl_comune":"203"
       },
       {
          "istat":"065118",
          "nome":"SAN CIPRIANO PICENTINO",
          "id_asl_comune":"207"
       },
       {
          "istat":"061075",
          "nome":"SAN FELICE A CANCELLO",
          "id_asl_comune":"203"
       },
       {
          "istat":"063066",
          "nome":"SAN GENNARO VESUVIANO",
          "id_asl_comune":"206"
       },
       {
          "istat":"063067",
          "nome":"SAN GIORGIO A CREMANO",
          "id_asl_comune":"206"
       },
       {
          "istat":"062058",
          "nome":"SAN GIORGIO DEL SANNIO",
          "id_asl_comune":"202"
       },
       {
          "istat":"062059",
          "nome":"SAN GIORGIO LA MOLARA",
          "id_asl_comune":"202"
       },
       {
          "istat":"065119",
          "nome":"SAN GIOVANNI A PIRO",
          "id_asl_comune":"207"
       },
       {
          "istat":"063068",
          "nome":"SAN GIUSEPPE VESUVIANO",
          "id_asl_comune":"206"
       },
       {
          "istat":"065120",
          "nome":"SAN GREGORIO MAGNO",
          "id_asl_comune":"207"
       },
       {
          "istat":"061076",
          "nome":"SAN GREGORIO MATESE",
          "id_asl_comune":"203"
       },
       {
          "istat":"062060",
          "nome":"SAN LEUCIO DEL SANNIO",
          "id_asl_comune":"202"
       },
       {
          "istat":"062061",
          "nome":"SAN LORENZELLO",
          "id_asl_comune":"202"
       },
       {
          "istat":"062062",
          "nome":"SAN LORENZO MAGGIORE",
          "id_asl_comune":"202"
       },
       {
          "istat":"062063",
          "nome":"SAN LUPO",
          "id_asl_comune":"202"
       },
       {
          "istat":"065121",
          "nome":"SAN MANGO PIEMONTE",
          "id_asl_comune":"207"
       },
       {
          "istat":"064082",
          "nome":"SAN MANGO SUL CALORE",
          "id_asl_comune":"201"
       },
       {
          "istat":"061077",
          "nome":"SAN MARCELLINO",
          "id_asl_comune":"203"
       },
       {
          "istat":"062064",
          "nome":"SAN MARCO DEI CAVOTI",
          "id_asl_comune":"202"
       },
       {
          "istat":"061104",
          "nome":"SAN MARCO EVANGELISTA",
          "id_asl_comune":"203"
       },
       {
          "istat":"062065",
          "nome":"SAN MARTINO SANNITA",
          "id_asl_comune":"202"
       },
       {
          "istat":"064083",
          "nome":"SAN MARTINO VALLE CAUDINA",
          "id_asl_comune":"201"
       },
       {
          "istat":"065122",
          "nome":"SAN MARZANO SUL SARNO",
          "id_asl_comune":"207"
       },
       {
          "istat":"065123",
          "nome":"SAN MAURO CILENTO",
          "id_asl_comune":"207"
       },
       {
          "istat":"065124",
          "nome":"SAN MAURO LA BRUCA",
          "id_asl_comune":"207"
       },
       {
          "istat":"064084",
          "nome":"SAN MICHELE DI SERINO",
          "id_asl_comune":"201"
       },
       {
          "istat":"062066",
          "nome":"SAN NAZZARO",
          "id_asl_comune":"202"
       },
       {
          "istat":"064085",
          "nome":"SAN NICOLA BARONIA",
          "id_asl_comune":"201"
       },
       {
          "istat":"061078",
          "nome":"SAN NICOLA LA STRADA",
          "id_asl_comune":"203"
       },
       {
          "istat":"062067",
          "nome":"SAN NICOLA MANFREDI",
          "id_asl_comune":"202"
       },
       {
          "istat":"063069",
          "nome":"SAN PAOLO BEL SITO",
          "id_asl_comune":"206"
       },
       {
          "istat":"065125",
          "nome":"SAN PIETRO AL TANAGRO",
          "id_asl_comune":"207"
       },
       {
          "istat":"061079",
          "nome":"SAN PIETRO INFINE",
          "id_asl_comune":"203"
       },
       {
          "istat":"061080",
          "nome":"SAN POTITO SANNITICO",
          "id_asl_comune":"203"
       },
       {
          "istat":"064086",
          "nome":"SAN POTITO ULTRA",
          "id_asl_comune":"201"
       },
       {
          "istat":"061081",
          "nome":"SAN PRISCO",
          "id_asl_comune":"203"
       },
       {
          "istat":"065126",
          "nome":"SAN RUFO",
          "id_asl_comune":"207"
       },
       {
          "istat":"062068",
          "nome":"SAN SALVATORE TELESINO",
          "id_asl_comune":"202"
       },
       {
          "istat":"063070",
          "nome":"SAN SEBASTIANO AL VESUVIO",
          "id_asl_comune":"206"
       },
       {
          "istat":"064087",
          "nome":"SAN SOSSIO BARONIA",
          "id_asl_comune":"201"
       },
       {
          "istat":"062069",
          "nome":"SANTA CROCE DEL SANNIO",
          "id_asl_comune":"202"
       },
       {
          "istat":"062070",
          "nome":"SANT\'AGATA DE\' GOTI",
          "id_asl_comune":"202"
       },
       {
          "istat":"063071",
          "nome":"SANT\'AGNELLO",
          "id_asl_comune":"206"
       },
       {
          "istat":"064088",
          "nome":"SANTA LUCIA DI SERINO",
          "id_asl_comune":"201"
       },
       {
          "istat":"061082",
          "nome":"SANTA MARIA A VICO",
          "id_asl_comune":"203"
       },
       {
          "istat":"061083",
          "nome":"SANTA MARIA CAPUA VETERE",
          "id_asl_comune":"203"
       },
       {
          "istat":"063090",
          "nome":"SANTA MARIA LA CARITA\'",
          "id_asl_comune":"206"
       },
       {
          "istat":"061084",
          "nome":"SANTA MARIA LA FOSSA",
          "id_asl_comune":"203"
       },
       {
          "istat":"065127",
          "nome":"SANTA MARINA",
          "id_asl_comune":"207"
       },
       {
          "istat":"061085",
          "nome":"SAN TAMMARO",
          "id_asl_comune":"203"
       },
       {
          "istat":"063072",
          "nome":"SANT\'ANASTASIA",
          "id_asl_comune":"206"
       },
       {
          "istat":"064089",
          "nome":"SANT\'ANDREA DI CONZA",
          "id_asl_comune":"201"
       },
       {
          "istat":"062071",
          "nome":"SANT\'ANGELO A CUPOLO",
          "id_asl_comune":"202"
       },
       {
          "istat":"065128",
          "nome":"SANT\'ANGELO A FASANELLA",
          "id_asl_comune":"207"
       },
       {
          "istat":"064090",
          "nome":"SANT\'ANGELO ALL\'ESCA",
          "id_asl_comune":"201"
       },
       {
          "istat":"064091",
          "nome":"SANT\'ANGELO A SCALA",
          "id_asl_comune":"201"
       },
       {
          "istat":"061086",
          "nome":"SANT\'ANGELO D\'ALIFE",
          "id_asl_comune":"203"
       },
       {
          "istat":"064092",
          "nome":"SANT\'ANGELO DEI LOMBARDI",
          "id_asl_comune":"201"
       },
       {
          "istat":"063073",
          "nome":"SANT\'ANTIMO",
          "id_asl_comune":"205"
       },
       {
          "istat":"063074",
          "nome":"SANT\'ANTONIO ABATE",
          "id_asl_comune":"206"
       },
       {
          "istat":"064093",
          "nome":"SANTA PAOLINA",
          "id_asl_comune":"201"
       },
       {
          "istat":"062078",
          "nome":"SANT\'ARCANGELO TRIMONTE",
          "id_asl_comune":"202"
       },
       {
          "istat":"061087",
          "nome":"SANT\'ARPINO",
          "id_asl_comune":"203"
       },
       {
          "istat":"065129",
          "nome":"SANT\'ARSENIO",
          "id_asl_comune":"207"
       },
       {
          "istat":"065130",
          "nome":"SANT\'EGIDIO DEL MONTE ALBINO",
          "id_asl_comune":"207"
       },
       {
          "istat":"065131",
          "nome":"SANTOMENNA",
          "id_asl_comune":"207"
       },
       {
          "istat":"064095",
          "nome":"SANTO STEFANO DEL SOLE",
          "id_asl_comune":"201"
       },
       {
          "istat":"065132",
          "nome":"SAN VALENTINO TORIO",
          "id_asl_comune":"207"
       },
       {
          "istat":"063075",
          "nome":"SAN VITALIANO",
          "id_asl_comune":"206"
       },
       {
          "istat":"065133",
          "nome":"SANZA",
          "id_asl_comune":"207"
       },
       {
          "istat":"065134",
          "nome":"SAPRI",
          "id_asl_comune":"207"
       },
       {
          "istat":"065135",
          "nome":"SARNO",
          "id_asl_comune":"207"
       },
       {
          "istat":"065136",
          "nome":"SASSANO",
          "id_asl_comune":"207"
       },
       {
          "istat":"062072",
          "nome":"SASSINORO",
          "id_asl_comune":"202"
       },
       {
          "istat":"063076",
          "nome":"SAVIANO",
          "id_asl_comune":"206"
       },
       {
          "istat":"064096",
          "nome":"SAVIGNANO IRPINO",
          "id_asl_comune":"201"
       },
       {
          "istat":"065137",
          "nome":"SCAFATI",
          "id_asl_comune":"207"
       },
       {
          "istat":"065138",
          "nome":"SCALA",
          "id_asl_comune":"207"
       },
       {
          "istat":"064097",
          "nome":"SCAMPITELLA",
          "id_asl_comune":"201"
       },
       {
          "istat":"063077",
          "nome":"SCISCIANO",
          "id_asl_comune":"206"
       },
       {
          "istat":"064098",
          "nome":"SENERCHIA",
          "id_asl_comune":"201"
       },
       {
          "istat":"064099",
          "nome":"SERINO",
          "id_asl_comune":"201"
       },
       {
          "istat":"065139",
          "nome":"SERRAMEZZANA",
          "id_asl_comune":"207"
       },
       {
          "istat":"063078",
          "nome":"SERRARA FONTANA",
          "id_asl_comune":"205"
       },
       {
          "istat":"065140",
          "nome":"SERRE",
          "id_asl_comune":"207"
       },
       {
          "istat":"061088",
          "nome":"SESSA AURUNCA",
          "id_asl_comune":"203"
       },
       {
          "istat":"065141",
          "nome":"SESSA CILENTO",
          "id_asl_comune":"207"
       },
       {
          "istat":"065142",
          "nome":"SIANO",
          "id_asl_comune":"207"
       },
       {
          "istat":"065143",
          "nome":"SICIGNANO DEGLI ALBURNI",
          "id_asl_comune":"207"
       },
       {
          "istat":"064100",
          "nome":"SIRIGNANO",
          "id_asl_comune":"201"
       },
       {
          "istat":"064101",
          "nome":"SOLOFRA",
          "id_asl_comune":"201"
       },
       {
          "istat":"062073",
          "nome":"SOLOPACA",
          "id_asl_comune":"202"
       },
       {
          "istat":"063079",
          "nome":"SOMMA VESUVIANA",
          "id_asl_comune":"206"
       },
       {
          "istat":"064102",
          "nome":"SORBO SERPICO",
          "id_asl_comune":"201"
       },
       {
          "istat":"063080",
          "nome":"SORRENTO",
          "id_asl_comune":"206"
       },
       {
          "istat":"061089",
          "nome":"SPARANISE",
          "id_asl_comune":"203"
       },
       {
          "istat":"064103",
          "nome":"SPERONE",
          "id_asl_comune":"201"
       },
       {
          "istat":"065144",
          "nome":"STELLA CILENTO",
          "id_asl_comune":"207"
       },
       {
          "istat":"065145",
          "nome":"STIO",
          "id_asl_comune":"207"
       },
       {
          "istat":"063081",
          "nome":"STRIANO",
          "id_asl_comune":"206"
       },
       {
          "istat":"064104",
          "nome":"STURNO",
          "id_asl_comune":"201"
       },
       {
          "istat":"061090",
          "nome":"SUCCIVO",
          "id_asl_comune":"203"
       },
       {
          "istat":"064105",
          "nome":"SUMMONTE",
          "id_asl_comune":"201"
       },
       {
          "istat":"064106",
          "nome":"TAURANO",
          "id_asl_comune":"201"
       },
       {
          "istat":"064107",
          "nome":"TAURASI",
          "id_asl_comune":"201"
       },
       {
          "istat":"061091",
          "nome":"TEANO",
          "id_asl_comune":"203"
       },
       {
          "istat":"065146",
          "nome":"TEGGIANO",
          "id_asl_comune":"207"
       },
       {
          "istat":"062074",
          "nome":"TELESE TERME",
          "id_asl_comune":"202"
       },
       {
          "istat":"064108",
          "nome":"TEORA",
          "id_asl_comune":"201"
       },
       {
          "istat":"063082",
          "nome":"TERZIGNO",
          "id_asl_comune":"206"
       },
       {
          "istat":"061092",
          "nome":"TEVEROLA",
          "id_asl_comune":"203"
       },
       {
          "istat":"062075",
          "nome":"TOCCO CAUDIO",
          "id_asl_comune":"202"
       },
       {
          "istat":"061093",
          "nome":"TORA E PICCILLI",
          "id_asl_comune":"203"
       },
       {
          "istat":"065147",
          "nome":"TORCHIARA",
          "id_asl_comune":"207"
       },
       {
          "istat":"064109",
          "nome":"TORELLA DEI LOMBARDI",
          "id_asl_comune":"201"
       },
       {
          "istat":"065148",
          "nome":"TORRACA",
          "id_asl_comune":"207"
       },
       {
          "istat":"063083",
          "nome":"TORRE ANNUNZIATA",
          "id_asl_comune":"206"
       },
       {
          "istat":"062076",
          "nome":"TORRECUSO",
          "id_asl_comune":"202"
       },
       {
          "istat":"063084",
          "nome":"TORRE DEL GRECO",
          "id_asl_comune":"206"
       },
       {
          "istat":"064110",
          "nome":"TORRE LE NOCELLE",
          "id_asl_comune":"201"
       },
       {
          "istat":"065149",
          "nome":"TORRE ORSAIA",
          "id_asl_comune":"207"
       },
       {
          "istat":"064111",
          "nome":"TORRIONI",
          "id_asl_comune":"201"
       },
       {
          "istat":"065150",
          "nome":"TORTORELLA",
          "id_asl_comune":"207"
       },
       {
          "istat":"065151",
          "nome":"TRAMONTI",
          "id_asl_comune":"207"
       },
       {
          "istat":"063091",
          "nome":"TRECASE",
          "id_asl_comune":"206"
       },
       {
          "istat":"065152",
          "nome":"TRENTINARA",
          "id_asl_comune":"207"
       },
       {
          "istat":"061094",
          "nome":"TRENTOLA-DUCENTA",
          "id_asl_comune":"203"
       },
       {
          "istat":"064112",
          "nome":"TREVICO",
          "id_asl_comune":"201"
       },
       {
          "istat":"063085",
          "nome":"TUFINO",
          "id_asl_comune":"206"
       },
       {
          "istat":"064113",
          "nome":"TUFO",
          "id_asl_comune":"201"
       },
       {
          "istat":"061095",
          "nome":"VAIRANO PATENORA",
          "id_asl_comune":"203"
       },
       {
          "istat":"064114",
          "nome":"VALLATA",
          "id_asl_comune":"201"
       },
       {
          "istat":"061096",
          "nome":"VALLE AGRICOLA",
          "id_asl_comune":"203"
       },
       {
          "istat":"065153",
          "nome":"VALLE DELL\'ANGELO",
          "id_asl_comune":"207"
       },
       {
          "istat":"061097",
          "nome":"VALLE DI MADDALONI",
          "id_asl_comune":"203"
       },
       {
          "istat":"064115",
          "nome":"VALLESACCARDA",
          "id_asl_comune":"201"
       },
       {
          "istat":"065154",
          "nome":"VALLO DELLA LUCANIA",
          "id_asl_comune":"207"
       },
       {
          "istat":"065155",
          "nome":"VALVA",
          "id_asl_comune":"207"
       },
       {
          "istat":"064116",
          "nome":"VENTICANO",
          "id_asl_comune":"201"
       },
       {
          "istat":"065156",
          "nome":"VIBONATI",
          "id_asl_comune":"207"
       },
       {
          "istat":"063086",
          "nome":"VICO EQUENSE",
          "id_asl_comune":"206"
       },
       {
          "istat":"065157",
          "nome":"VIETRI SUL MARE",
          "id_asl_comune":"207"
       },
       {
          "istat":"061098",
          "nome":"VILLA DI BRIANO",
          "id_asl_comune":"203"
       },
       {
          "istat":"061099",
          "nome":"VILLA LITERNO",
          "id_asl_comune":"203"
       },
       {
          "istat":"064117",
          "nome":"VILLAMAINA",
          "id_asl_comune":"201"
       },
       {
          "istat":"064118",
          "nome":"VILLANOVA DEL BATTISTA",
          "id_asl_comune":"201"
       },
       {
          "istat":"063087",
          "nome":"VILLARICCA",
          "id_asl_comune":"205"
       },
       {
          "istat":"063088",
          "nome":"VISCIANO",
          "id_asl_comune":"206"
       },
       {
          "istat":"062077",
          "nome":"VITULANO",
          "id_asl_comune":"202"
       },
       {
          "istat":"061100",
          "nome":"VITULAZIO",
          "id_asl_comune":"203"
       },
       {
          "istat":"063089",
          "nome":"VOLLA",
          "id_asl_comune":"206"
       },
       {
          "istat":"064119",
          "nome":"VOLTURARA IRPINA",
          "id_asl_comune":"201"
       },
       {
          "istat":"064120",
          "nome":"ZUNGOLI",
          "id_asl_comune":"201"
       }
    ],
    "ruoli_zoofile_gisa":[
       {
          "id":"329",
          "descr":"GUARDIE ZOOFILE",
          "ext":true
       }
    ],
    "ruoli_giava":[
       {
          "id":"10000008",
          "descr":"GIAVA",
          "ext":true
       }
    ],
    "ruoli_osservatori":[
       {
          "id":"31",
          "descr":"ORSA"
       },
       {
          "id":"53",
          "descr":"ORSA DIREZIONE"
       }
    ],
    "ruoli_crr":[
       {
          "id":"3368",
          "descr":"CERVENE"
       },
       {
          "id":"3367",
          "descr":"CRESAN"
       },
       {
          "id":"3369",
          "descr":"CRIPAT - PAT"
       },
       {
          "id":"3370",
          "descr":"CRIPAT - RIST. COLL."
       },
       {
          "id":"3366",
          "descr":"C.Ri.S.SA.P."
       },
       {
          "id":"3365",
          "descr":"CRIUV"
       },
       {
          "id":"3373",
          "descr":"POLO DIDATTICO INTEGRATO"
       }
    ],
    "ruoli_regione":[
       {
          "id":"328",
          "descr":"BDA-Reg"
       },
       {
          "id":"40",
          "descr":"Funzionari Regionali"
       },
       {
          "id":"326",
          "descr":"Funzionari Regionali addetti al Registro Trasgressori GRUPPO 1"
       },
       {
          "id":"327",
          "descr":"Funzionari Regionali addetti al Registro Trasgressori GRUPPO 2"
       },
       {
          "id":"324",
          "descr":"NU.RE.CU."
       },
       {
          "id":"37",
          "descr":"Operatori Allerte REGIONE Sian"
       },
       {
          "id":"47",
          "descr":"Operatori Allerte REGIONE Veterinari"
       },
       {
          "id":"27",
          "descr":"Responsabile Regione"
       }
    ],
    "ruoli_asl":[
       {
          "id":"96",
          "descr":"Altro Funzionario Laureato"
       },
       {
          "id":"44",
          "descr":"Amministrativi ASL"
       },
       {
          "id":"59",
          "descr":"delegato asl"
       },
       {
          "id":"16",
          "descr":"Direttori Dipartimento di Prevenzione"
       },
       {
          "id":"221",
          "descr":"MEDICI SPECIALISTI AMBULATORIALI"
       },
       {
          "id":"222",
          "descr":"medici veterinari specialisti"
       },
       {
          "id":"42",
          "descr":"Medico"
       },
       {
          "id":"21",
          "descr":"Medico - Responsabile Struttura Complessa"
       },
       {
          "id":"45",
          "descr":"Medico - Responsabile Struttura Semplice"
       },
       {
          "id":"97",
          "descr":"Medico - Responsabile Struttura Semplice Dipartimentale"
       },
       {
          "id":"43",
          "descr":"Medico Veterinario"
       },
       {
          "id":"19",
          "descr":"Medico Veterinario - Responsabile Struttura Complessa"
       },
       {
          "id":"46",
          "descr":"Medico Veterinario - Responsabile Struttura Semplice"
       },
       {
          "id":"98",
          "descr":"Medico Veterinario - Responsabile Struttura Semplice Dipartimentale"
       },
       {
          "id":"33",
          "descr":"Referente Allerte ASL"
       },
       {
          "id":"56",
          "descr":"Referenti ASL"
       },
       {
          "id":"41",
          "descr":"T.P.A.L"
       }
    ],
    "ruoli_vam":[
       {
          "id":"3",
          "descr":"Ambulatorio - Amministrativo"
       },
       {
          "id":"5",
          "descr":"Ambulatorio - Veterinario"
       },
       {
          "id":"2",
          "descr":"Amministratore VAM"
       },
       {
          "id":"14",
          "descr":"Referente Asl"
       }
    ],
    "cliniche_vam":[
       {
          "id":"66",
          "descr":"Amb. o Canile presso Atripalda",
          "id_asl":"201"
       },
       {
          "id":"65",
          "descr":"Amb. o Canile presso Avellino",
          "id_asl":"201"
       },
       {
          "id":"67",
          "descr":"Amb. o Canile presso Baiano",
          "id_asl":"201"
       },
       {
          "id":"64",
          "descr":"Amb. o Canile presso S.Angelo dei Lombardi",
          "id_asl":"201"
       },
       {
          "id":"45",
          "descr":"Ambulatorio veterinario Ariano Irpino",
          "id_asl":"201"
       },
       {
          "id":"93",
          "descr":"AMBULATORIO VETERINARIO asl avellino di Ariano Irpino",
          "id_asl":"201"
       },
       {
          "id":"44",
          "descr":"Ambulatorio veterinario Monteforte Irpino",
          "id_asl":"201"
       },
       {
          "id":"46",
          "descr":"Amb. veterinario presso Canile \"La Casa di Billy\"-Mirabella E.",
          "id_asl":"201"
       },
       {
          "id":"4",
          "descr":"Centro di sterilizzazione \"Il Vagabondo\"- Lioni",
          "id_asl":"201"
       },
       {
          "id":"22",
          "descr":"IZSM Avellino",
          "id_asl":"201"
       },
       {
          "id":"10",
          "descr":"AMBULATORIO VETERINARIO ASL BN",
          "id_asl":"202"
       },
       {
          "id":"47",
          "descr":"Ambulatorio Veterinario del canile comunale di Apice",
          "id_asl":"202"
       },
       {
          "id":"23",
          "descr":"IZSM Benevento",
          "id_asl":"202"
       },
       {
          "id":"88",
          "descr":"Ospedale Veterinario Asl BN - SAN GIORGIO DEL SANNIO",
          "id_asl":"202"
       },
       {
          "id":"51",
          "descr":"Unit\u00e0 Operativa Territoriale di Benevento",
          "id_asl":"202"
       },
       {
          "id":"48",
          "descr":"Unit\u00e0 Operativa Territoriale di Montesarchio",
          "id_asl":"202"
       },
       {
          "id":"50",
          "descr":"Unit\u00e0 Operativa Territoriale di Morcone",
          "id_asl":"202"
       },
       {
          "id":"49",
          "descr":"Unit\u00e0\u00a0Operativa Territoriale di Telese Terme",
          "id_asl":"202"
       },
       {
          "id":"56",
          "descr":"Amb.o Canile \"La casa del Cane: Di Matteo Enza\"-Dist. Alvignano",
          "id_asl":"203"
       },
       {
          "id":"57",
          "descr":"Ambulatorio canile Pontelatone- Distr. Caiazzo",
          "id_asl":"203"
       },
       {
          "id":"53",
          "descr":"Ambulatorio Comunale di Maddaloni",
          "id_asl":"203"
       },
       {
          "id":"55",
          "descr":"Ambulatorio Comunale di Marcianise",
          "id_asl":"203"
       },
       {
          "id":"61",
          "descr":"Ambulatorio Comunale S.Maria Capua Vetere",
          "id_asl":"203"
       },
       {
          "id":"60",
          "descr":"Ambulatorio-Distr. Mignano Montelungo",
          "id_asl":"203"
       },
       {
          "id":"58",
          "descr":"Ambulatorio- Distr. Piedimonte Matese",
          "id_asl":"203"
       },
       {
          "id":"59",
          "descr":"Ambulatorio- Distr. Teano",
          "id_asl":"203"
       },
       {
          "id":"52",
          "descr":"Ambulatorio Municipale di Caserta",
          "id_asl":"203"
       },
       {
          "id":"92",
          "descr":"AMBULATORIO VETERINARIO ASL CAIAZZO",
          "id_asl":"203"
       },
       {
          "id":"11",
          "descr":"Ambulatorio Veterinario ASL CE - Aversa",
          "id_asl":"203"
       },
       {
          "id":"108",
          "descr":"AMBULATORIO VETERINARIO DI VAIRANO PATENORA -SCALO - VAIRANO PATENORA",
          "id_asl":"203"
       },
       {
          "id":"24",
          "descr":"IZSM Caserta",
          "id_asl":"203"
       },
       {
          "id":"82",
          "descr":"Ambulatorio Capri",
          "id_asl":"204"
       },
       {
          "id":"26",
          "descr":"Dip.Pat. e San. - Unina",
          "id_asl":"204"
       },
       {
          "id":"21",
          "descr":"IZSM Portici",
          "id_asl":"204"
       },
       {
          "id":"1",
          "descr":"Presidio Ospedaliero Veterinario",
          "id_asl":"204"
       },
       {
          "id":"12",
          "descr":"Ambulatorio Ex NA 3",
          "id_asl":"205"
       },
       {
          "id":"103",
          "descr":"ambulatorio veterinario caivano",
          "id_asl":"205"
       },
       {
          "id":"98",
          "descr":"ASLNapoli2Nord - CAIVANO",
          "id_asl":"205"
       },
       {
          "id":"9",
          "descr":"Ospedale Fido e Felix",
          "id_asl":"205"
       },
       {
          "id":"2",
          "descr":"Ospedale Veterinario ASL NA 2 Nord",
          "id_asl":"205"
       },
       {
          "id":"89",
          "descr":"Ambulatorio SANITARIO ASL NAPOLI3SUD- OTTAVIANO",
          "id_asl":"206"
       },
       {
          "id":"5",
          "descr":"Ambulatorio veterinario ASL NA 3 Sud - Piano di Sorrento",
          "id_asl":"206"
       },
       {
          "id":"101",
          "descr":"ambulatorio veterinario - canile comune di pomigliano d\'arco - POMIGLIANO D\'ARCO",
          "id_asl":"206"
       },
       {
          "id":"73",
          "descr":"Ambulatorio Veterinario San Giuseppe Vesuviano",
          "id_asl":"206"
       },
       {
          "id":"100",
          "descr":"asl napoli 3 sud - MARIGLIANO",
          "id_asl":"206"
       },
       {
          "id":"90",
          "descr":"Canile municipale di Pomigliano ",
          "id_asl":"206"
       },
       {
          "id":"6",
          "descr":"Clinica Veterinaria ASL NA 3 Sud - Torre del Greco",
          "id_asl":"206"
       },
       {
          "id":"91",
          "descr":"Stazione Zoologica Anton Dohrn",
          "id_asl":"206"
       },
       {
          "id":"72",
          "descr":"U.O.C. Sanit\u00e0 Animale",
          "id_asl":"206"
       },
       {
          "id":"79",
          "descr":"U.O.V. 48 Marigliano",
          "id_asl":"206"
       },
       {
          "id":"74",
          "descr":"U.O.V. 49 Nola",
          "id_asl":"206"
       },
       {
          "id":"75",
          "descr":"U.O.V. 51 Sant\'Anastasia",
          "id_asl":"206"
       },
       {
          "id":"76",
          "descr":"U.O.V. 53 Castellammare di Stabia",
          "id_asl":"206"
       },
       {
          "id":"68",
          "descr":"U.O.V. 54 San Giorgio a Cremano",
          "id_asl":"206"
       },
       {
          "id":"77",
          "descr":"U.O.V. 55 Ercolano",
          "id_asl":"206"
       },
       {
          "id":"71",
          "descr":"U.O.V. 56 Torre Annunziata",
          "id_asl":"206"
       },
       {
          "id":"80",
          "descr":"U.O.V. 57 Torre del Greco",
          "id_asl":"206"
       },
       {
          "id":"70",
          "descr":"U.O.V. 58 Gragnano",
          "id_asl":"206"
       },
       {
          "id":"43",
          "descr":"U.O.V. 59 Piano di Sorrento",
          "id_asl":"206"
       },
       {
          "id":"27",
          "descr":"UOV di S.Giuseppe Vesuviano",
          "id_asl":"206"
       },
       {
          "id":"40",
          "descr":"Ambulatorio aperto dal II sem. 2012-Distr.Battipaglia",
          "id_asl":"207"
       },
       {
          "id":"32",
          "descr":"Ambulatorio ASL con degenza sanitaria di Cava de Tirreni",
          "id_asl":"207"
       },
       {
          "id":"33",
          "descr":"Ambulatorio ASL con degenza sanitaria di Maiori",
          "id_asl":"207"
       },
       {
          "id":"38",
          "descr":"Ambulatorio con gabbie per degenza sanitaria-Distr.Pontecagnano",
          "id_asl":"207"
       },
       {
          "id":"37",
          "descr":"Ambulatorio con gabbie per degenza sanitaria-Distr.Salerno",
          "id_asl":"207"
       },
       {
          "id":"39",
          "descr":"Ambulatorio-Distretto di Giffoni Valle Piana",
          "id_asl":"207"
       },
       {
          "id":"62",
          "descr":"Ambulatorio Polla",
          "id_asl":"207"
       },
       {
          "id":"34",
          "descr":"Ambulatorio presso canile municipale di Angri",
          "id_asl":"207"
       },
       {
          "id":"36",
          "descr":"Ambulatorio presso canile municipale di Nocera Inferiore",
          "id_asl":"207"
       },
       {
          "id":"35",
          "descr":"Ambulatorio presso canile municipale di Pagani",
          "id_asl":"207"
       },
       {
          "id":"41",
          "descr":"Ambulatorio riaperto dal II sem. 2012-Distr.Eboli",
          "id_asl":"207"
       },
       {
          "id":"3",
          "descr":"Ambulatorio SA - SALERNO",
          "id_asl":"207"
       },
       {
          "id":"42",
          "descr":"Ambulatorio temporaneamente chiuso-Distr.Buccino",
          "id_asl":"207"
       },
       {
          "id":"63",
          "descr":"Ambulatorio Torre Orsaia",
          "id_asl":"207"
       },
       {
          "id":"96",
          "descr":"ambulatorio veterinario - MOIO DELLA CIVITELLA",
          "id_asl":"207"
       },
       {
          "id":"107",
          "descr":"Ambulatorio veterinario ASL SALERNO - CAPACCIO",
          "id_asl":"207"
       },
       {
          "id":"106",
          "descr":"AMBULATORIO VETERINARIO ASL SALERNO - GIFFONI SEI CASALI",
          "id_asl":"207"
       },
       {
          "id":"97",
          "descr":"AMBULATORIO VETERINARIO ASL SALERNO - SERRE",
          "id_asl":"207"
       },
       {
          "id":"102",
          "descr":"Ambulatorio veterinario ASL SALERNO NORD - CAVA DE\' TIRRENI",
          "id_asl":"207"
       },
       {
          "id":"105",
          "descr":"ASSOCIAZIONE ZOOFILA PAGANESE ONLUS - PAGANI",
          "id_asl":"207"
       },
       {
          "id":"104",
          "descr":"Canile Rifugio comune di scafati - SCAFATI",
          "id_asl":"207"
       },
       {
          "id":"25",
          "descr":"IZSM Salerno",
          "id_asl":"207"
       },
       {
          "id":"30",
          "descr":"Oasi Felix",
          "id_asl":"207"
       },
       {
          "id":"31",
          "descr":"Rifugio comprensoriale per cani",
          "id_asl":"207"
       },
       {
          "id":"28",
          "descr":"UOV di Capaccio",
          "id_asl":"207"
       },
       {
          "id":"29",
          "descr":"UOV di Vallo della Lucania",
          "id_asl":"207"
       }
    ],
    "ruoli_izsm_vam":[
       {
          "id":"6",
          "descr":"IZSM"
       }
    ],
    "cliniche_vam_izsm":[
       {
          "id":"22",
          "descr":"IZSM Avellino",
          "id_asl":"201"
       },
       {
          "id":"23",
          "descr":"IZSM Benevento",
          "id_asl":"202"
       },
       {
          "id":"24",
          "descr":"IZSM Caserta",
          "id_asl":"203"
       },
       {
          "id":"21",
          "descr":"IZSM Portici",
          "id_asl":"204"
       },
       {
          "id":"25",
          "descr":"IZSM Salerno",
          "id_asl":"207"
       }
    ],
    "ruoli_digemon":[
       {
          "id":"99",
          "descr":"Statistiche"
       }
    ],
    "registrazione":[]
 }';
?>
