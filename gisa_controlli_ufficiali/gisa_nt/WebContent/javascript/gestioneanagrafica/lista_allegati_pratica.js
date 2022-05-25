/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

var allegati = [
		{"code": "A", "desc": "planimetria dei locali in scala 1:100 debitamente timbrata e firmata da tecnico abilitato"},
		{"code": "B", "desc": "relazione tecnica igienico-sanitaria datata e firmata da un tecnico o dal titolare o rappresentante legale dell'impresa"},
		{"code": "C", "desc": "riferimenti catastali con la specifica delle coordinate geografiche espresse in formato WGS84, " +
			"latitudine e longitudine decimale (es. Lat 41.328719 Long 14.97556) rilevate al centro dell'azienda"},
		{"code": "G", "desc":"progetto dello stabilimento"},
		{"code": "H", "desc":"attestato di idonietà all'identificazione delle specie fungine"},
		{"code": "I", "desc":"relazione tecnica datata e firmata da un tecnico o dal titolare o dal rappresentate legale dell'impresa"},
		{"code": "J", "desc":"fotocopia tesserino di raccoglitore funghi"},
		{"code": "S", "desc":"fotocopia tesserino di raccoglitore o cercatore tartufi"},
		{"code": "T", "desc":"planimetria dei locali in scala 1:100 debitamente timbrata da tecnico abilitato "},
		{"code": "U", "desc":"concessione demaniale rilasciata dall'ente competente"},
		{"code": "V", "desc":"fotocopia decreto di classificazione specchio acqueo"},
		{"code": "W", "desc":"planimetria dei locali in scala 1:100 debitamente timbrata e firmata da tecnico abilitato"},
		{"code": "X", "desc":"elenco degli integratori"},
		{"code": "Y", "desc":"elenco degli additivi"},
		{"code": "Z", "desc":"relazione tecnica datata e firmata da un tecnicoo dal titolare o rappresentante legale dell’impresa"},
		{"code": "AA", "desc":"planimetria dei locali in scala 1:100 debitamente timbrata e firmata da tecnico abilitato"},
		{"code": "BB", "desc":"relazione tecnica igienico-sanitaria datata e firmata da un tecnico o dal titolare o rappresentante legale dell’impresa"},
		{"code": "DD", "desc":"relazione tecnica igienico-sanitatia datata e firmata da un tecnico o dal titolare o rappresentante legale dell'impresa"},
		{"code": "GG", "desc":"dichiarazione del rappresentante legale dell'impresa che autocertifica che lui stesso o l'impresa non hanno riportato condanne" +
				" penali per truffa o per commercio di medicinali irregolari nè sono stati coinvolti in reati inerenti i farmaci, puniti con sentenze passate in giudicato"},
		{"code": "II", "desc":"dichiarazione del rappresentante legale dell'impresa che autocertifica di essere a conoscenza che l'attivita di vendita diretta" +
				" può essere svolta solo alla presenza della persona che assume la responsabilità della vendita diretta"},
		{"code": "KK", "desc":"Autocertificazione di essere in possesso del diploma di laurea in Medicina Veterinaria e di essere iscritto all'albo con indicazione del" +
				"numero di iscrizione"},
		{"code": "LL", "desc":"Dichiarazione sostitutiva di atto di notorietà"},
		{"code": "OO", "desc":"Informazioni specifiche sull'organizzazione tecnica e commerciale"},
		{"code": "PP", "desc":"Modalità di certificazione degli embrioni prodotti"},
		{"code": "RR", "desc":"Attestazione per la persona responsabile dell'attività del possesso di una qualificata formazione professionale, o," +
				" in alternativa, attestato di possesso delle cognizioni necessarie all'esercizio di tale attività o una comprovata esperienza nel settore degli" +
				" animali da compagnia, rilasciata dal Servizio Veterinario dell'ASL competente per territorio, previo accertamento dei requisiti attitudinali"},
		{"code": "SS", "desc":"Studio tecnico sulla natura fisico chimica del terreno con particolare attenzione alla profondità della falda idrica"},
		{"code": "TT", "desc":"Dichiarazione da parte dell'azienda che gestisce l'acquedotto pubblico che sull'area individuata non ci sono falde inquinabili " +
				"(o certificazione equivalente)"},
		{"code": "UU", "desc":"Planimetria debitamente timbrata e firmata da tecnico abilitato riportante la superficie e le coordinate dei vertici della zona di " +
				"produzione, espresse in formato WGS84, latitudine e longitudine decimale (es. Lat 41.328719 Long 14.97556)"},
		{"code": "VV", "desc":"Copia della carta nautica con indicazione dell'ubicazione dei vertici della zona di produzione"},
		{"code": "WW", "desc":"Relazione descrittiva esaustiva dell'area e dell'attività che si intende svolgere datata e firmata da un tecnico o dal titolare" +
				" o rappresentante legale dell'impresa"},
		{"code": "CCC", "desc":"Informazioni specifiche sull'organizzazione tecnica e commerciale per la produzione e la distribuzione del materiale seminale"},
		{"code": "EEE", "desc":"Indicazioni specifiche sulla organizzazione della distribuzione"},
		{"code": "GGG", "desc":"Indicazione delle attrezzature utilizzate"},
		{"code": "HHH", "desc":"Ubicazione e descrizione dei locali del laboratorio stabile con il quale si è collegati"},
		{"code": "III", "desc":"Stralcio planimetrico con l'indicazione delle aree coperte e scoperte riservate al ricovero dei cani e relativa quadratura totale" +
				" espressa in mq"},
		{"code": "JJJ", "desc":"Documento di riconoscimento scannerizzato del titolare o del legale rappresentante"},
		{"code": "KKK", "desc":"Licenza di pesca"},
		{"code": "LLL", "desc":"Relazione tecnica igienico-sanitaria datata e firmata da un tecnico o dal titolare o rappresentante legale dell'impresa"},
		{"code": "MMM", "desc":"Relazione tecnica igienico-sanitaria datata e firmata da un tecnico o dal titolare o rappresentante legale dell'impresa, " +
				"riportante la descrizione di massima delle aree di erogazione degli interventi"},
		{"code": "NNN", "desc":"Relazione tecnica igienico-sanitaria datata e firmata da un tecnico o dal titolare o rappresentante legale dell'impresa"},
		{"code": "OOO", "desc":"Copia del provvedimento del competente Uffico Regionale dell'Assessorato all'Agricoltura relativo all'istituzione " +
				"dell'allevamento di cinghiali ai sensi dell'art. 13 L.R. 26/12 e art. 14 L.R. 8/96"},
		{"code": "PPP", "desc":"Attestazione di avvenuto pagamento dei diritti sanitari a favore della ASL"},
		{"code": "RRR", "desc":"Progetto di uno stabilimento dove verrà effettuata la vendita dei prodotti fitosanitari"},
		{"code": "SSS", "desc":"Copia della ricevuta della SCIA o copia della registrazione relativa alla propria stalla di sosta"},
		{"code": "TTT", "desc":"Copia della ricevuta della SCIA o copia della registrazione relativa al proprio allevamento"}
];
