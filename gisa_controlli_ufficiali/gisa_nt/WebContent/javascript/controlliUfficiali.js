/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
var dataCu = '';
var flagEsisteCu = false;
function controlloEsistenzaCu(data, orgId) {
	dataCu = data;
	PopolaCombo.controlloEsistenzaCU(data, orgId, {
		async : false,
		callback : gestisciRistpostaEsistenzaCuCallBack
	});

}

function gestisciRistpostaEsistenzaCuCallBack(val) {
	if (val != -1 && document.getElementById('id') != null
			&& val != document.getElementById('id').value) {
		flagEsisteCu = true;

	}
}

function abilitaNoteDescrizioni() {

	value = document.getElementById("ispezione")
	att2 = false;
	att3 = false;
	att4 = false;
	att5 = false;
	att6 = false;
	att7 = false;
	att9 = false;

	for (i = 0; i < value.length; i++) {
		if (value[i].selected) {
			
			if (value[i].value == "37") { //ok
				att2 = true;
			}

			if (value[i].value == "53") {
				att5 = true;
			}

			if (value[i].value == "59") {
				att6 = true;
			}

			if (value[i].value == "63") {
				att7 = true;
			}

			if (value[i].value == "67") {
				att3 = true;
			}

			if (value[i].value == "68") {
				att4 = true;
			}
			
			if (value[i].value == "123") {
				att9 = true;
			}
}

	}



	if (att2 == true) {
		document.getElementById("desc_note2").style.display = "block";
	} else {
		document.getElementById("desc_note2").style.display = "none";
	}

	if (att3 == true) {
		document.getElementById("desc_note3").style.display = "block";
	} else {
		document.getElementById("desc_note3").style.display = "none";
	}

	if (att4 == true) {
		document.getElementById("desc_note4").style.display = "block";
	} else {
		document.getElementById("desc_note4").style.display = "none";
	}

	if (att5 == true) {
		document.getElementById("desc_note5").style.display = "block";
	} else {
		document.getElementById("desc_note5").style.display = "none";
	}

	if (att6 == true) {
		document.getElementById("desc_note6").style.display = "block";
	} else {
		document.getElementById("desc_note6").style.display = "none";
	}

	if (att7 == true) {
		document.getElementById("desc_note7").style.display = "block";
	} else {
		document.getElementById("desc_note7").style.display = "none";
	}

	if (att9 == true) {
		if (document.getElementById("desc_note9")!=null)
			document.getElementById("desc_note9").style.display = "block";
	} else {
		if (document.getElementById("desc_note9")!=null)
			document.getElementById("desc_note9").style.display = "none";
	}

}

// Abilita la visualizzazione della lookup_specie_trasportata
function abilitaSpecieTrasportata() {

	value = document.getElementById("ispezione")
	specie = false;

	for (i = 0; i < value.length; i++) {
		if (value[i].selected) {
			if (value[i].value == "126" )
			{
				specie = true;

			}

		}

	}

	if (specie == true) {
		document.getElementById("specieT").style.display = "";
	} else {
		document.getElementById("specieT").style.display = "none";
	}

}

function abilitaNumCapi() {
	value = document.getElementById("animalitrasp")
	att1 = false;
	att2 = false;
	att3 = false;
	att4 = false;
	att5 = false;
	att6 = false;
	att7 = false;
	att8 = false;
	att9 = false;
	att10 = false;
	att11 = false;
	att12 = false;
	att13 = false;
	att14 = false;
	att15 = false;
	att22 = false;
	att23 = false;
	att24 = false;
	att25 = false;
	att26 = false;

	for (i = 0; i < value.length; i++)
	// for(i=0;i<26;i++)
	{
		if (value[i].selected) {
			if (value[i].value == "1") {
				att1 = true;

			}

			if (value[i].value == "2") {
				att2 = true;
			}

			if (value[i].value == "4") {
				att3 = true;
			}

			if (value[i].value == "6") {
				att4 = true;
			}

			if (value[i].value == "10") {
				att5 = true;
			}

			if (value[i].value == "11") {
				att6 = true;
			}

			if (value[i].value == "12") {
				att7 = true;
			}

			if (value[i].value == "13") {
				att8 = true;
			}

			if (value[i].value == "14") {
				att9 = true;
			}

			if (value[i].value == "15") {
				att10 = true;
			}

			if (value[i].value == "16") {
				att11 = true;
			}

			if (value[i].value == "18") {
				att12 = true;
			}

			if (value[i].value == "19") {
				att13 = true;
			}

			if (value[i].value == "20") {
				att14 = true;
			}

			if (value[i].value == "21") {
				att15 = true;
			}
			if (value[i].value == "22") {
				att22 = true;
			}
			if (value[i].value == "23") {
				att23 = true;
			}
			if (value[i].value == "24") {
				att24 = true;
			}
			if (value[i].value == "25") {
				att25 = true;
			}
			if (value[i].value == "26") {
				att26 = true;
			}

		}

	}

	if (att1 == true) {
		document.getElementById("num_capo1").style.display = "";

	} else {
		document.getElementById("num_capo1").style.display = "none";
		$(document.getElementById("num_capo1")).find("input").val("");

	}

	if (att2 == true) {
		document.getElementById("num_capo2").style.display = "";

	} else {
		document.getElementById("num_capo2").style.display = "none";
		$(document.getElementById("num_capo2")).find("input").val("");
	}

	if (att3 == true) {
		document.getElementById("num_capo3").style.display = "";
	} else {
		document.getElementById("num_capo3").style.display = "none";
		$(document.getElementById("num_capo3")).find("input").val("");
	}

	if (att4 == true) {
		document.getElementById("num_capo4").style.display = "";

	} else {
		document.getElementById("num_capo4").style.display = "none";
		$(document.getElementById("num_capo4")).find("input").val("");
	}

	if (att5 == true) {
		document.getElementById("num_capo5").style.display = "";
	} else {
		document.getElementById("num_capo5").style.display = "none";
		$(document.getElementById("num_capo5")).find("input").val("");
	}

	if (att6 == true) {
		document.getElementById("num_capo6").style.display = "";

	} else {
		document.getElementById("num_capo6").style.display = "none";
		$(document.getElementById("num_capo6")).find("input").val("");

	}

	if (att7 == true) {
		document.getElementById("num_capo7").style.display = "";

	} else {
		document.getElementById("num_capo7").style.display = "none";
		$(document.getElementById("num_capo7")).find("input").val("");

	}

	if (att8 == true) {
		document.getElementById("num_capo8").style.display = "";

	} else {
		document.getElementById("num_capo8").style.display = "none";
		$(document.getElementById("num_capo8")).find("input").val("");

	}

	if (att9 == true) {
		document.getElementById("num_capo9").style.display = "";
	} else {
		document.getElementById("num_capo9").style.display = "none";
		$(document.getElementById("num_capo9")).find("input").val("");

	}
	if (att10 == true) {
		document.getElementById("num_capo10").style.display = "";

	} else {
		document.getElementById("num_capo10").style.display = "none";
		$(document.getElementById("num_capo10")).find("input").val("");

	}
	if (att11 == true) {
		document.getElementById("num_capo11").style.display = "";

	} else {
		document.getElementById("num_capo11").style.display = "none";
		$(document.getElementById("num_capo11")).find("input").val("");

	}
	if (att12 == true) {
		document.getElementById("num_capo12").style.display = "";

	} else {
		document.getElementById("num_capo12").style.display = "none";
		$(document.getElementById("num_capo12")).find("input").val("");

	}
	if (att13 == true) {
		document.getElementById("num_capo13").style.display = "";

	} else {
		document.getElementById("num_capo13").style.display = "none";
		$(document.getElementById("num_capo13")).find("input").val("");

	}
	if (att14 == true) {
		document.getElementById("num_capo14").style.display = "";

	} else {
		document.getElementById("num_capo14").style.display = "none";
		$(document.getElementById("num_capo14")).find("input").val("");

	}
	if (att15 == true) {
		document.getElementById("num_capo15").style.display = "";

	} else {
		document.getElementById("num_capo15").style.display = "none";
		$(document.getElementById("num_capo15")).find("input").val("");

	}
	if (att22 == true) {
		document.getElementById("num_capo22").style.display = "";

	} else {
		document.getElementById("num_capo22").style.display = "none";
		$(document.getElementById("num_capo22")).find("input").val("");

	}
	if (att23 == true) {
		document.getElementById("num_capo23").style.display = "";

	} else {
		document.getElementById("num_capo23").style.display = "none";
		$(document.getElementById("num_capo23")).find("input").val("");

	}
	if (att24 == true) {
		document.getElementById("num_capo24").style.display = "";

	} else {
		document.getElementById("num_capo24").style.display = "none";
		$(document.getElementById("num_capo24")).find("input").val("");

	}
	if (att25 == true) {
		document.getElementById("num_capo25").style.display = "";

	} else {
		document.getElementById("num_capo25").style.display = "none";
		$(document.getElementById("num_capo25")).find("input").val("");

	}
	if (att26 == true) {
		document.getElementById("num_capo26").style.display = "";

	} else {
		document.getElementById("num_capo26").style.display = "none";
		$(document.getElementById("num_capo26")).find("input").val("");

	}

}

function controlloDataFine(dataI, dataF) {
	var arr1 = dataI.split("/");
	var d1 = new Date(arr1[2], arr1[1] - 1, arr1[0]);

	var arr2 = dataF.split("/");
	var d2 = new Date(arr2[2], arr2[1] - 1, arr2[0]);

	var r1 = d1.getTime();
	var r2 = d2.getTime();

	if (r1 <= r2)
		return false;

	return true;

}

function checkPostData(data, dataReg) {
	if (compareDates(data.value, "d/MM/y", dataReg.value, "d/MM/y") == 1) {
		return false;
	} else {
		return true;
	}
}

function controlloData(stringa) {
	var now = new Date();
	var giorno = now.getDate();
	var mese = now.getMonth() + 1;
	var anno = now.getFullYear();
	var arr2 = stringa.split("/");
	var d2 = new Date(arr2[2], arr2[1] - 1, arr2[0]);
	var r1 = now.getTime();
	var r2 = d2.getTime();
	if (r2 > r1)
		return false;

	return true;

}

function controllo_data(stringa) {
	var espressione = /^[0-9]{2}\/[0-9]{2}\/[0-9]{4}$/;
	if (!espressione.test(stringa)) {
		return false;
	}
}

function abilitaSel2() {

	document.getElementById("nucleodue").style.display = "";

}

function abilitaSel3() {
	document.getElementById("nucleotre").style.display = "";

}

function abilitaSel4() {
	document.getElementById("nucleoquattro").style.display = "";

}

function abilitaSel5() {
	document.getElementById("nucleocinque").style.display = "";

}

function abilitaSel6() {
	document.getElementById("nucleosei").style.display = "";

}

function abilitaSel7() {
	document.getElementById("nucleosette").style.display = "";

}

function abilitaSel8() {
	document.getElementById("nucleootto").style.display = "";

}

function abilitaSel9() {
	document.getElementById("nucleonove").style.display = "";

}

function abilitaSel10() {
	document.getElementById("nucleodieci").style.display = "";

}

function abilita2() {
	document.getElementById("nucleodue").style.display = "";

}

function abilita3() {
	document.getElementById("nucleotre").style.display = "";
}

function abilita4() {
	document.getElementById("nucleoquattro").style.display = "";
}

function abilita5() {
	document.getElementById("nucleocinque").style.display = "";
}

function abilita6() {
	document.getElementById("nucleosei").style.display = "";
}

function abilita7() {
	document.getElementById("nucleosette").style.display = "";
}

function abilita8() {
	document.getElementById("nucleootto").style.display = "";
}

function abilita9() {
	document.getElementById("nucleonove").style.display = "";
}

function abilita10() {
	document.getElementById("nucleodieci").style.display = "";
}

function calcolaTotale() {
	var f = 0;
	var g = 0;
	var h = 0;

	if (document.getElementById("puntiFormali").value == "")
		f = 0;
	else {
		f = parseInt(document.getElementById("puntiFormali").value);

	}
	if (document.getElementById("puntiSignificativi").value == "")
		g = 0;
	else {
		g = parseInt(document.getElementById("puntiSignificativi").value);

	}
	if (document.getElementById("puntiGravi").value == "")
		h = 0;
	else {

		h = parseInt(document.getElementById("puntiGravi").value);

	}
	totale = document.getElementById("totale");

	totale.value = f + g + h;

}

function showMessaggioAllarme(form) {
	if (form.orgId != null) {
		orgId = form.orgId.value;
	}
	if (form.idStabilimento != null) {
		orgId = form.idStabilimento.value;
	}

	if (form.altId != null) {
		orgId = form.altId.value;
	}

	articolo = form.articoliAzioni.value;
	ControlliUfficiali.controlloSistemaAllarmeRapido(orgId, articolo,
			showMessageCallback);

}

function showMessageCallback(returnValue) {
	if (returnValue == 1) {
		alert('<< Attenzione. Nel caso in cui l OSA abbia gia subito una sanzione per gli stessi motivi, occorre procedere a sospendere l Impresa per il numero di giorni previsti. >>')

	}

}

function trim(str) {
	return str.replace(/^\s+|\s+$/g, "");
}

function controlloEsistenzaCuOpu(data, orgId) {
	dataCu = data;
	PopolaCombo.controlloEsistenzaCUOpu(data, orgId,
			gestisciRistpostaEsistenzaCuCallBack);

}

var formTest = true;

function checkFormModify(form) {
	isSupervisione = false;
	formTest = true;
	// Perch� ci sta questo controllo per la modifica?

	if (form.idStabilimento != null)
		controlloEsistenzaCuOpu(form.assignedDate.value,
				form.idStabilimento.value);

	entratoinpiano = false;
	message = "";
	
	if (form.tipoCampione.value == '4' || form.tipoCampione.value == '3') {
		tipiispezione = document.getElementsByName('tipoIspezione');
		if (tipiispezione != null)

			for (i = 0; i < tipiispezione.length; i++) {
				getCodiceInternoTipoIspezione(tipiispezione[i].value);

				if (codiceInternoTipoIspezione == '2a'
						&& entratoinpiano == false) {
					entratoinpiano = true;
					indice = 1;
					while (document.getElementById('uo' + indice) != null) {
						uo = document.getElementById('uo' + indice);
						if (uo.value == '-1') {
							message += label("",
									"- Selezionare la voce per Conto di \r\n");
							formTest = false;
						}
						indice++;
					}
				}

				else

				if (codiceInternoTipoIspezione != '2a') {

					if (document.getElementById('per_condo_di'
							+ tipiispezione[i].value) != null) {
						if (document.getElementById('per_condo_di'
								+ tipiispezione[i].value).value == '-1') {
							message += label("",
									"- Selezionare la voce per Conto di  Nelle Attivita Selezionate \r\n");
							formTest = false;

						}

					}

				}
				
				if (codiceInternoTipoIspezione!= null && codiceInternoTipoIspezione.toLowerCase() == '58a') {
					if (form.elements["azione"].value == null || form.elements["azione"].value == '') {
						message += label("", "- Indicare se Le azioni correttive risultano adeguate ed efficaci\r\n");
						formTest = false;
					}
				}
				

			} // FINE FOR

		if (document.getElementById('quintali') != null)

		{

			if (document.getElementById('quintali').value != '') {
				if (controlloNumerico(document.getElementById('quintali').value) == false) {
					message += label("",
							"- Controllare di aver inserito un numero \r\n");
					formTest = false;
				}
			}

		}
	} else {

		uo = document.getElementsByName("uo_controllo");
		if (uo != null) {
			for (i = 0; i < uo.length; i++) {

				if (uo.value == '-1') {
					message += label("",
							"- Selezionare la voce per Conto di \r\n");
					formTest = false;
				}

			}
		}
	}

	if (form.tipoCampione.value == '7') {
		if (document.getElementById('oggetto_audit').value == -1) {
			message += label("",
					"- Selezionare la voce Oggetto dell'Audit \r\n");
			formTest = false;
		}
	}

	if (form.struttura_asl != null) {
		sel = true;
		array = form.struttura_asl;
		for (i = 0; i < struttura_asl.length; i++) {
			if (array[i].value == '-1' && array[i].selected == true) {
				sel = false;
				break;
			}
		}

		if (document.getElementById('parte_asl').checked && sel == false) {
			message += label("", "- Selezionare la struttura controllata \r\n");
			formTest = false;
		}

	}

	if (form.quantita != null) {
		if (trim(form.quantita.value) != '') {
			value = new String(form.quantita.value);

			if (isNaN(value.replace(",", "."))) {
				message += label("",
						"- Valore di Quantita Partita non Valido. Inserire Cifre\r\n");
				formTest = false;
			} else {
				form.quantita.value = trim(value.replace(",", "."));
			}

		} else {

			form.quantita.value = "0";
		}
	}

	if (form.quantitaBloccata != null) {
		if (trim(form.quantitaBloccata.value) != '') {
			value = new String(form.quantitaBloccata.value);

			if (isNaN(value.replace(",", "."))) {
				message += label("",
						"- Valore di Quantita Partita Bloccata non Valido. Inserire Cifre\r\n");
				formTest = false;
			} else {
				form.quantitaBloccata.value = trim(value.replace(",", "."));
			}

		} else {

			form.quantitaBloccata.value = "0";
		}
	}

	if (form.peso != null)
		if (isNaN(form.peso.value.replace(",", "."))) {
			message += label("", "- Peso non Valido. Inserire Cifre\r\n");
			formTest = false;
		}

	if (form.struttura_asl != null) {
		sel = true;
		array = form.struttura_asl;
		for (i = 0; i < struttura_asl.length; i++) {
			if (array[i].value == '-1' && array[i].selected == true) {
				sel = false;
				break;
			}
		}

		if (document.getElementById('parte_asl').checked && sel == false) {
			message += label("", "- Selezionare la struttura controllata \r\n");
			formTest = false;
		}

	}

	if (form.quantita != null) {
		if (trim(form.quantita.value) != '') {
			value = new String(form.quantita.value);

			if (isNaN(value.replace(",", "."))) {
				message += label("",
						"- Valore di Quantita Partita non Valido. Inserire Cifre\r\n");
				formTest = false;
			} else {
				form.quantita.value = trim(value.replace(",", "."));
			}

		}
	}

	if (form.quantitaBloccata != null) {
		if (trim(form.quantitaBloccata.value) != '') {
			value = new String(form.quantitaBloccata.value);

			if (isNaN(value.replace(",", "."))) {
				message += label("",
						"- Valore di Quantita Partita Bloccata non Valido. Inserire Cifre\r\n");
				formTest = false;
			} else {
				form.quantitaBloccata.value = trim(value.replace(",", "."));
			}

		}
	}

	if (form.peso != null) {
		form.peso.value.replace(",", ".");
		if (isNaN(form.peso.value.replace(",", "."))) {
			message += label("", "- Peso non Valido. Inserire Cifre\r\n");
			formTest = false;
		}
	}

	if (form.tipoCampione.value != '5') {

		if (form.tipoCampione.value == '4') {

			/*
			 * if(form.id_linea_sottoposta_a_controllo!=null &&
			 * form.id_linea_sottoposta_a_controllo.value =='-1') { message +=
			 * label("","- Controllare di aver Selezionato almeno una linea di
			 * attivita.\r\n"); formTest = false; }
			 */

			if (form.id_linea_sottoposta_a_controllo
					&& form.id_linea_sottoposta_a_controllo.value == '-1') {
				message += label("",
						"- Controllare di aver Selezionato almeno una linea di attivita.\r\n");
				formTest = false;
			}

			if (form.codici_selezionabili && form.codici_selezionabili != null) {
				selected = false;

				array = form.codici_selezionabili;
				for (i = 0; i < array.length; i++) {
					if (array[i].value != '') {
						selected = true;
					}

				}
			}

			if (form.codici_selezionabili && form.codici_selezionabili != null
					&& selected == false) {
				message += label("",
						"- Controllare di aver Selezionato almeno una linea di attivita'.\r\n");
				formTest = false;
			}

			/*
			 * if(form.codici_selezionabili!=null &&
			 * form.codici_selezionabili.value =='') { message += label("","-
			 * Controllare di aver Selezionato almeno una linea di
			 * attivita.\r\n"); formTest = false; }
			 */

		} else {

			// if(form.id_linea_sottoposta_a_controllo!=null )
			if (form.id_linea_sottoposta_a_controllo) {
				option = form.id_linea_sottoposta_a_controllo;
				selected = false;
				for (i = 0; i < option.length; i++) {
					if (option[i].selected == true && option[i].value != '-1') {
						selected = true;
					}

				}
				if (selected == false) {

					message += label("",
							"- Controllare di aver Selezionato almeno una linea di attivita.\r\n");
					formTest = false;
				}
			}
			selected = false;

			// if(form.codici_selezionabili!= null )
			if (form.codici_selezionabili) {
				array = form.codici_selezionabili;
				for (i = 0; i < array.length; i++) {
					if (array[i].value != '') {
						selected = true;
					}

				}

				if (selected == false) {

					message += label("",
							"- Controllare di aver Selezionato almeno una linea di attivita.\r\n");
					formTest = false;
				}
			}

		}

	}

	if (form.tipoCampione.value != '2') {
		if (form.nucleo_ispettivo_1.value == '-1') {
			message += label("",
					"- Controllare di aver Selezionato almeno un nucleo Ispettivo.\r\n");
			formTest = false;
		}

		for (i = 1; i <= 10; i++) {
			if (document.getElementById('nucleo_ispettivo_' + i) != null) {
				if (document.getElementById('nucleo_ispettivo_' + i).value != '-1') {
					value = document.getElementById('nucleo_ispettivo_' + i).value;

					if (document.getElementById('risorse_' + i).value == '-1'
							&& document.getElementById('Utente_' + i).value == '') {

						message += label("",
								"- Controllare di aver Selezionato un Componente Per il Nucleo Ispettivo.\r\n");
						formTest = false;
					}

				}

			}
		}
	}
	
	//commento al 214
	
	if (document.getElementById('misureFormativetr')!=null && document.getElementById('misureFormativetr').style.display!= 'none' && form.misureFormative!= null && form.misureFormative.value == '' ) {
		message += label("",
				"- Controllare che il Campo \"Misure formative\" sia stato inserito\r\n");
		formTest = false;
	}
	
	if (document.getElementById('misureRestrittivetr')!=null && document.getElementById('misureRestrittivetr').style.display!= 'none' && form.misureRestrittive!= null && form.misureRestrittive.value == '') {
		message += label("",
				"- Controllare che il Campo \"Misure restrittive\" sia stato inserito\r\n");
		formTest = false;
	}
	
	if (document.getElementById('misureRiabilitativetr')!=null && document.getElementById('misureRiabilitativetr').style.display!= 'none' &&form.misureRiabilitative!= null && form.misureRiabilitative.value == '') {
		message += label("",
				"- Controllare che il Campo \"Misure riabilitative\" sia stato inserito\r\n");
		formTest = false;
	}
	

	if (form.via_luogocontrollo!= null && form.via_luogocontrollo.value == '') {
		message += label("",
				"- Controllare di aver Selezionato il luogo del controllo.\r\n");
		formTest = false;
	}
	
	//commento al 247
	if (document.getElementById('apiariSelezionatitr')!=null && document.getElementById('apiariSelezionatitr').style.display!= 'none' && form.apiariSelezionati!= null && form.apiariSelezionati.value == '' ) {
		message += label("",
				"- Controllare che il Campo \"Apiari Selezionati\" sia stato valorizzato\r\n");
		formTest = false;
	}
	if (document.getElementById('apiariSelezionatitr')!=null && document.getElementById('apiariSelezionatitr').style.display!= 'none' && form.apiariSelezionati!= null && form.apiariSelezionati.value == 'B' && form.apiariSelezionatiMotivo!= null && (form.apiariSelezionatiMotivo.value == '' || form.apiariSelezionatiMotivo.value == '-1') ) {
		message += label("",
				"- Controllare che il Campo \"Informazioni Stato Sanitario Apiari - Motivo\" sia stato valorizzato\r\n");
		formTest = false;
	}
	if (document.getElementById('apiariSelezionatitr')!=null && document.getElementById('apiariSelezionatitr').style.display!= 'none' && form.apiariSelezionati!= null && (form.apiariSelezionati.value == 'A' || form.apiariSelezionati.value == 'B') && form.apiariSelezionatiAlveariControllati!= null && form.apiariSelezionatiAlveariControllati.value == '' ) {
		message += label("",
				"- Controllare che il Campo \"Apiari Selezionati - Numero Alveari Controllati\" sia stato valorizzato\r\n");
		formTest = false;
	}
	if (document.getElementById('apiariSelezionatitr')!=null && document.getElementById('apiariSelezionatitr').style.display!= 'none' && form.apiariSelezionati!= null && (form.apiariSelezionati.value == 'A' || form.apiariSelezionati.value == 'B') && form.apiariSelezionatiEsito!= null && form.apiariSelezionatiEsito.value == '' ) {
		message += label("",
				"- Controllare che il Campo \"Apiari Selezionati - Esito\" sia stato valorizzato\r\n");
		formTest = false;
	}

	selected = false;
	if (form.alertText) {

		array = form.alertText;
		for (i = 0; i < array.length; i++) {
			if (array[i].value != '') {
				selected = true;
			}

		}

		// if (selected == false ){
		// if (form.tipoIspezione.value != "3" && form.tipoCampione.value!="5")
		// {
		// message += label("","- Controllare di aver inserito una linea di
		// attivita.\r\n");
		// formTest = false;
		// }
		// }
	}

	/*
	 * if(!form.assignedDate.value == "" && !form.dataFineControllo.value ==
	 * ""){
	 * 
	 * if (controlloDataFine(form.assignedDate.value,
	 * form.dataFineControllo.value)== true ){
	 * 
	 * message += label("","- La Data di Inizio Controllo non puo\' essere
	 * successiva a quella di Fine Controllo.\r\n"); formTest = false; } }
	 */
	

	isAllarmeRapido = false;
	if (form.contributi != null) {
		var numero = form.contributi.value;

		var arr_num = numero.split(".");

		if (numero.indexOf(",") != (-1)) {

			message += label(
					"check.vigilanza.richiedente.selezionato",
					"- Controllare che il sepratore delle cifre decimali per i contributi sia il punto anzich� la virgola \r\n");
			formTest = false;
		}
	}

	if (form.tipoCampione.value != "-1") {
		if (form.tipoCampione.value == "7") { // se
																				// ho
																				// selezionato
																				// audit
			if (form.auditTipo != null) {
				if (form.auditTipo.value == "1") {
					isbpi = false;
					ishaccp = false;
					isSelTipoAudit = false;

					if (form.tipoAudit != null) {
						isSelTipoAudit = true;
					}

					if (isSelTipoAudit == false) {
						message += label(
								"check.vigilanza.richiedente.selezionato",
								"- Controllare che il campo \"tipo Audit\" sia stato popolato\r\n");
						formTest = false;
					}
				}
			} else {
				message += label("check.vigilanza.richiedente.selezionato",
						"- Controllare che il campo \"tipo Audit\" sia stato popolato\r\n");
				formTest = false;

			}

		} else if (form.tipoCampione.value == "22") {
			isSupervisione = true;
		}
		if (isSupervisione == true) {
			if (document.getElementById("fileAllegareSupervisione") != null
					&& (document.getElementById("fileAllegareSupervisione").value == "-1" || document
							.getElementById("fileAllegareSupervisione").value == "")) {
				formTest = false;
				message += label("",
						"- Controllare di aver selezionato un \"Verbale\" \r\n");

			}
			if (form.isAllegatoSupervisione.value == "false"
					|| form.isAllegatoSupervisione.value == "") {
				formTest = false;
				message += label(
						"",
						"- Controllare di aver allegato il file cliccando sul pulsante  \"Allega\" \r\n");

			}

		}

		else {

			if (form.tipoCampione.value == "4" || form.tipoCampione.value == "3") {// se ho selezionato
													// ispezione o audit

				isSelTipoIspezione = false;
				isPianoMonitoraggio = false;
				isSospetto = false;
				if (form.tipoIspezione != null) {

					for (i = 0; i < document.getElementsByName('tipoIspezione').length; i++) {
						if (document.getElementsByName('tipoIspezione')[i].value != '-1') {
							isSelTipoIspezione = true;

							getCodiceInternoTipoIspezione(document
									.getElementsByName('tipoIspezione')[i].value);
							// codiceInternoTipoIspezione =
							// $("#"+document.getElementsByName('tipoIspezione')[i].id).attr("codiceinterno")
							if (codiceInternoTipoIspezione == '2a') {
								isPianoMonitoraggio = true;
							}
							if (codiceInternoTipoIspezione == '7a') {
								isAllarmeRapido = true;
							}
							if (codiceInternoTipoIspezione == '9a') {
								isSospetto = true;
							}
						}

					}
				} else {

					if (form.context == null)
						isSelTipoIspezione = true;

				}

				if (isSospetto) {
					if (form.tipoSosp.value == "-1") {
						formTest = false;
						message += label("",
								"- Controllare  di aver selezionato un  \"Tipo di sospetto\" \r\n");
					}

					if (form.idBuffer.value == "" && form.tipoSosp.value == "1") {
						formTest = false;
						message += label("",
								"- Controllare  di aver selezionato un \"Buffer\" \r\n");
					}
				}

				if (isSelTipoIspezione == false) // se non ho selezionato
													// niente come tipo
													// ispezione(in monitoraggio
													// non , e sorveglianza)
				{
					message += label("check.vigilanza.richiedente.selezionato",
							"- Controllare che il campo \"Tipo di Ispezione\" sia stato popolato\r\n");
					formTest = false;

				} else {

					if (isPianoMonitoraggio == true) // se ho selezionato
														// tipo ispezione in
														// monitoraggio
					{
						if ((form.piano_monitoraggio1 == null)
								|| (form.piano_monitoraggio1 != null && form.piano_monitoraggio1.value == "-1")) {// se
																													// non
																													// ho
																													// selezionato
																													// nessun
																													// piano
																													// di
																													// monitoraggio

							message += label(
									"check.vigilanza.richiedente.selezionato",
									"- Controllare che il \"Piano di monitoraggio\" sia stato selezionato\r\n");
							formTest = false;
						}

						i = 1;

						var condizionalita_A = false;
						var condizionalita_B = false;

						while (document
								.getElementById('piano_monitoraggio' + i) != null) {
							getCodiceInternoTipoPiano(document
									.getElementById('piano_monitoraggio' + i).value);

							if ((codiceInternoTipoIspezione == '982' || codiceInternoTipoIspezione == '983')
									&& flagCondizionalitaReturn == 'true')
								condizionalita_A = true;
							if (codiceInternoTipoIspezione == '1483'
									&& flagCondizionalitaReturn == 'true')
								condizionalita_B = true;

							if (codiceInternoTipoIspezione == '982' || codiceInternoTipoIspezione == '983') {
								if (document.getElementById('flag_preavviso') != null
										&& document
												.getElementById('flag_preavviso').value != 'N'
										&& document
												.getElementById('flag_preavviso').value != '-1'
										&& (document
												.getElementById('data_preavviso_ba') != null && document
												.getElementById('data_preavviso_ba').value == '')) {
									message += label(
											"check.vigilanza.richiedente.selezionato",
											"- Controllare che la \"Data Preavviso\" sia stata inserita\r\n");
									formTest = false;
									break;
								}

								if (document.getElementById('flag_checklist') != null
										&& document
												.getElementById('flag_checklist').value == '-1') {
									message += label(
											"check.vigilanza.richiedente.selezionato",
											"- Controllare che il Campo \"E' stata consegnata una copia della presente checklist?\" sia stato selezionato\r\n");
									formTest = false;
									break;
								}
								
								if (document.getElementById('flag_preavviso') != null
										&& document
												.getElementById('flag_preavviso').value == '-1') {
									message += label(
											"check.vigilanza.richiedente.selezionato",
											"- Controllare che il Campo \"Preavviso\" sia stato selezionato\r\n");
									formTest = false;
									break;
								}

								var pianovalore = document
										.getElementById('piano_monitoraggio'
												+ i).value;

								if (flagCondizionalitaReturn == 'true') {
									var optcondiz = document
											.getElementById("condizionalita");
									for (jj = 0; jj < optcondiz.length; jj++) {
										if (optcondiz[jj].selected == true
												&& optcondiz[jj].value == '-1') {
											message += label("",
													"- Selezionare la voce Condizionalita \r\n");
											formTest = false;

										}
									}
								}

							}
							i++;

						}

						if (condizionalita_A) {
							var condSelect = document
									.getElementById("condizionalita");
							var condSelected = new Array();
							for (var c = 0; c < condSelect.length; c++) {
								if (condSelect[c].selected) {
									condSelected.push(condSelect[c].value);
								}
							}

							if (condSelected.length == -0
									&& !document.getElementById("cond_cb").checked) {
								message += label("",
										"- Effettuare almeno una scelta di tipo Condizionalità. \r\n");
								formTest = false;
							}

						}

					}
				}

				if (form.ispezione.value == "-1" || form.ispezione.value == "") {// se
																					// non
																					// ho
																					// selezionato
																					// nessun
																					// piano
																					// di
																					// monitoraggio

					message += label("check.vigilanza.richiedente.selezionato",
							"- Controllare che il campo \"Oggetto del controllo\" sia stato popolato\r\n");
					formTest = false;

				}

				value_isp = document.getElementById("ispezione");
				for (i = 0; i < value_isp.length; i++) {
					if (value_isp[i].selected) {
						if (value_isp[i].value == "69"
								|| value_isp[i].value == "70"
								|| value_isp[i].value == "71"
								|| value_isp[i].value == "72"
								|| value_isp[i].value == "73"
								|| value_isp[i].value == "74"
								|| value_isp[i].value == "75"
								|| value_isp[i].value == "76") {

							if (document.getElementById("animalitrasp").value == "") {
								message += label(
										"check.vigilanza.richiedente.selezionato",
										"- Controllare che il campo \"Specie Animali Trasportati\" sia stato popolato\r\n");
								formTest = false;
							}

						}
					}
				}

				/* Controllare il numero relativo alla specie */
				specie = document.getElementById('animalitrasp');
				if (specie != null)
					for (i = 0; i < specie.length; i++) {
						if (!specie[i].selected) {
							if (specie[i].value != -1) {
								var elem = document.getElementById('num_specie'
										+ specie[i].value);
								if (elem != null)
									elem.value = -1;
							}

							// = '-1';
						} else {

							if (document.getElementById('num_specie'
									+ specie[i].value) != null) {

								if (document.getElementById('num_specie'
										+ specie[i].value).value == ""
										|| document.getElementById('num_specie'
												+ specie[i].value).value == "-1") {
									message += label(
											"check.vigilanza.richiedente.selezionato",
											"- Controllare che il campo \"Numero\" relativo alla specie selezionata sia popolato\r\n");
									formTest = false;
								}
								else if(parseInt(document.getElementById('num_specie'+ specie[i].value).value)<=0)
								{
									message += label(
										"check.vigilanza.richiedente.selezionato",
										"- Controllare che il campo \"Numero\" relativo alla specie selezionata sia un numero positivo\r\n");
									formTest = false;
								}

							}

						}
					}

				messaggio = false;

				if (form.contributi_rilascio_certificazione != null
						&& form.contributi_rilascio_certificazione.value != ''
						&& isNaN(form.contributi_rilascio_certificazione.value
								.replace(",", "."))) {
					message += label("",
							"- Valore di Contributi per Rilascio Cert. non Valido. Inserire Cifre\r\n");
					formTest = false;
				}

				if (form.contributi_risol_nc != null
						&& form.contributi_risol_nc.value != ''
						&& isNaN(form.contributi_risol_nc.value.replace(",",
								"."))) {
					message += label(
							"",
							"- Valore di Contributi per Risoluzione non Conf.. non Valido. Inserire Cifre\r\n");
					formTest = false;
				}

				if (form.contributi != null && form.contributi.value != ''
						&& isNaN(form.contributi.value.replace(",", "."))) {
					message += label("",
							"- Valore di Contributi non Valido. Inserire Cifre\r\n");
					formTest = false;
				}

				if (form.contributi_macellazione_urgenza != null
						&& form.contributi_macellazione_urgenza.value != ''
						&& isNaN(form.contributi_macellazione_urgenza.value
								.replace(",", "."))) {
					message += label(
							"",
							"- Valore di Contributi per Macellazione Urgenza non Valido. Inserire Cifre\r\n");
					formTest = false;
				}

				if (isAllarmeRapido == true) {

					if (form.idAllerta.value == "") {
						formTest = false;
						message += label(
								"check.vigilanza.data_richiesta.selezionato",
								"- Controllare  di aver selezionato una  \"Allerta \" \r\n");

					}

					if (form.procedureRitiro != null
							&& form.procedureRitiro.value == "-1") {
						formTest = false;
						message += label(
								"check.vigilanza.data_richiesta.selezionato",
								"- Controllare  di aver selezionato una proceduta di ritiro \r\n");

					}

					if ((form.procedureRichiamo != null && form.procedureRichiamo.value == "1")
							&& (form.motivoRichiamo != null && form.motivoRichiamo.value == '')) {
						formTest = false;
						message += label(
								"check.vigilanza.data_richiesta.selezionato",
								"- Controllare  di aver selezionato un motivo per le Procedure di richiamo non attivate \r\n");

					}

					if (isNaN(form.contributi_allarme_rapido.value.replace(",",
							"."))) {
						message += label("",
								"- Valore di Contributi per Allarme Rapido non Valido. Inserire Cifre\r\n");
						formTest = false;
					}

					if (form.esitoControllo.value == '13'
							|| form.esitoControllo.value == '14') {
						if (form.destinazioneDistribuzione.value == -1) {
							formTest = false;
							message += label(
									"check.vigilanza.data_richiesta.selezionato",
									"- Controllare che i campi relativi a \"Destinazione Distribuzione partita\" siano stati popolati\r\n");

						}

						/*
						 * if(document.getElementById("fileAllegare")!= null &&
						 * (document.getElementById("fileAllegare").value ==
						 * "-1"
						 * ||document.getElementById("fileAllegare").value=="" )) {
						 * formTest = false; message +=
						 * label("check.vigilanza.data_richiesta.selezionato","-
						 * Controllare di aver selezionato una \"Lista di
						 * distribuzione\" \r\n");
						 *  }
						 */

						if (document.getElementById("isAllegato") != null
								&& (document.getElementById("isAllegato").value == "false" || document
										.getElementById("isAllegato").value == "")) {
							formTest = false;
							message += label(
									"check.vigilanza.data_richiesta.selezionato",
									"- Controllare di aver allegato il file cliccando sul pulsante  \"Allega\" \r\n");

						}

					}

					if (form.esitoControllo.value == '-1') {
						formTest = false;
						message += label(
								"check.vigilanza.data_richiesta.selezionato",
								"- Controllare di aver Selezionato un   \"Esito Controllo\" \r\n");

					} else {
						if (form.esitoControllo.value == '7') {
							if (form.dataddt.value == "") {
								formTest = false;
								message += label(
										"check.vigilanza.data_richiesta.selezionato",
										"- Controllare  di aver inserito \"La Data Per DDT \" \r\n");

							}

							if (form.numDdt.value == "") {
								formTest = false;
								message += label(
										"check.vigilanza.data_richiesta.selezionato",
										"- Controllare di aver inserito \"Numero DDT \" \r\n");

							}

						} else {

							if (form.esitoControllo.value == '8') {

								if (form.quantita.value == "") {
									formTest = false;
									message += label(
											"check.vigilanza.data_richiesta.selezionato",
											"- Controllare  di aver inserito La \"Quantita \" \r\n");

								}

							} else {
								if (form.esitoControllo.value == '10'
										|| form.esitoControllo.value == '11') {
									if (form.quantitaBloccata.value == "") {
										formTest = false;
										message += label(
												"check.vigilanza.data_richiesta.selezionato",
												"- Controllare di aver inserito La \"Quantita Bloccata \" \r\n");

									}

								}
							}

						}

					}

					value = form.azioniAdottate;
					settatoAzione1 = false;
					settatoAzione2 = false;
					if (value != null)
						for (i = 0; i < value.length; i++) {
							if (value[i].selected == true) {

								if ((value[i].value == "-1")
										|| (value[i].value == "")) {
									formTest = false;
									message += label(
											"check.vigilanza.data_richiesta.selezionato",
											"- Controllare  di non aver selezionato la voce \"Seleziona Voce \" da Azioni Adottate \r\n");

								} else {
									if (value[i].value == "3") {
										// if(form.articoliAzioni.value=="-1")
										// {
										// formTest = false;
										// message +=
										// label("check.vigilanza.data_richiesta.selezionato","-
										// Controllare di aver Selezionato un
										// \"Articolo \" \r\n");
										//
										//
										// }
										// else
										// {
										messaggio = true;
										// }
									}
								}
							}

						}

				}

			} else {

				// if(form.tipoCampione.value=="5"){
				// if(form.ispezione.value=="-1" || form.ispezione.value=="")
				// {//se non ho selezionato nessun piano di monitoraggio
				//
				// message += label("check.vigilanza.richiedente.selezionato","-
				// Controllare che il campo \"Oggetto del controllo\" sia stato
				// popolato\r\n");
				// formTest = false;
				//
				// }
				// }
			}

		}
	}

	var dataInizioControllo = form.assignedDate.value;
	var flagDecesso = false;
	var flagDataDecessoPrecedenteDataControllo = false;

	var dateParts = dataInizioControllo.split("/");

	var datecu = new Date(dateParts[2], (dateParts[1] - 1), dateParts[0]);

	if (document.getElementById("size_p") != null) {
		numeroElementi = document.getElementById("size_p").value;

		for (i = 1; i <= numeroElementi; i++) {

			if (document.getElementById("mc_" + i)!=null)
				{
			if (document.getElementById("mc_" + i).value == ""
					&& document.getElementById("razza_" + i).value == ""
					&& document.getElementById("sesso_" + i).value == ""
					&& document.getElementById("mantello_" + i).value == ""
					&& document.getElementById("taglia_" + i).value == ""
					&& document.getElementById("data_nascita_cane_" + i).value == "" && ((document.getElementById("canicontrollatitr")!=null && document.getElementById("canicontrollatitr").style.display!='none') || (document.getElementById("canicontrollatitrCaniPadronali")!=null && document.getElementById("canicontrollatitrCaniPadronali").style.display!='none')))

			{
				message += label("check.vigilanza.richiedente.selezionato",
						"- Controllare di aver compilato qualche informazione sul cane controllato\r\n");
				formTest = false;
				break;
			}
			     

			var dataDecesso = '';
			if (document.getElementById("data_decesso_" + i) != null)
				dataDecesso = document.getElementById("data_decesso_" + i).value;

			if (dataDecesso != ''
					&& document.getElementById("data_decesso_" + i) != null) {
				flagDecesso = true;

				var dateParts2 = dataDecesso.split("/");

				var dateDec = new Date(dateParts2[2], (dateParts2[1] - 1),
						dateParts2[0]);

				if (dateDec < datecu) {
					flagDataDecessoPrecedenteDataControllo = true;

					break;
				}

				}
			}

		}

		if (flagDataDecessoPrecedenteDataControllo == true) {
			message += label(
					"check.vigilanza.richiedente.selezionato",
					"- Attenzione la data di decesso ("
							+ dataDecesso
							+ ") dell'animale è antecedente alla data di inizio controllo \r\n");
			formTest = false;
		}

	}

	if (form.nome_conducente != null && form.nome_conducente.value == "") {
		message += label("check.vigilanza.richiedente.selezionato",
				"- Controllare che il campo \"Nominativo Conduttore\" sia stato popolato\r\n");
		formTest = false;
	}

	if (form.nominativo_proprietario != null
			&& form.nominativo_proprietario.value == "") {
		message += label("check.vigilanza.richiedente.selezionato",
				"- Controllare che il campo \"Nominativo Proprietario\" sia stato popolato\r\n");
		formTest = false;
	}

	if (form.cf_proprietario != null && form.cf_proprietario.value == "") {
		message += label("check.vigilanza.richiedente.selezionato",
				"- Controllare che il campo \"CF Proprietario\" sia stato popolato\r\n");
		formTest = false;
	}

	if (form.siteId.value == "-1") {
		message += label("check.vigilanza.richiedente.selezionato",
				"- Controllare che il campo \"A.S.L.\" sia stato popolato\r\n");
		formTest = false;
	}

	if ((form.orgId != null && form.orgId.value == "-1") && (form.idStabilimentoopu != null && form.idStabilimentoopu.value == "-1")) {
		message += label("check.vigilanza.richiedente.selezionato",
				"- Controllare che \"Impresa\" sia stato selezionato\r\n");
		formTest = false;
	}

	// Controllo per il campo categoria trasportata
	if (document.getElementById("categoriaTrasportata") != null) {
		if (document.getElementById("categoriaTrasportata").value == -1) {
			message += label("check.vigilanza.richiedente.selezionato",
					"- Controllare che \"Categoria Trasportata\" sia stato selezionato\r\n");
			formTest = false;
		}
	}

	if (form.tipoCampione.value == "-1") {
		message += label("check.campioni.richiedente.selezionato",
				"- Controllare che \"Tipo di Controllo\" sia stato popolato\r\n");
		formTest = false;
	}

	if (form.assignedDate.value == "") {
		message += label("check.vigilanza.data_richiesta.selezionato",
				"- Controllare che il campo \"Data Inizio Controllo\" sia stato popolato\r\n");
		formTest = false;
	} else {
		flag = controllo_data(form.assignedDate.value)

		if (flag == false) {
			formTest = false;
			message += label(
					"check.vigilanza.data_richiesta.selezionato",
					"- Controllare che il campo \"Data Inizio Controllo\" sia stato popolato Correttamente\r\n");

		} else {

		}

	}

	// if (document.getElementById("flag_preavviso")!= null &&
	// document.getElementById("flag_preavviso").value!='-1' &&
	// document.getElementById("flag_preavviso").value!='N' &&
	// document.getElementById("data_preavviso_ba").value==''){
	// message += label("check.vigilanza.richiedente.selezionato","- Controllare
	// che il Campo \"Data Preavviso\" sia stato selezionato\r\n");
	// formTest = false;
	// }
	//	
	/*
	 * if(controlloData(form.assignedDate.value)==false){ formTest = false;
	 * message += label("check.vigilanza.data_richiesta.selezionato","-
	 * Controllare che il campo \"Data Inizio Controllo\" sia stato Precedente o
	 * uguale alla data attuale\r\n"); }
	 */
	messaggio = false;

	if (form.tipoIspezione != null && form.tipoIspezione.value == "7a") {

		if (form.idAllerta.value == "") {
			formTest = false;
			message += label("check.vigilanza.data_richiesta.selezionato",
					"- Controllare  di aver selezionato una  \"Allerta \" \r\n");

		}

		if (form.procedureRitiro != null && form.procedureRitiro.value == "-1") {
			formTest = false;
			message += label("check.vigilanza.data_richiesta.selezionato",
					"- Controllare  di aver selezionato una proceduta di ritiro \r\n");

		}

		if ((form.procedureRichiamo != null && form.procedureRichiamo.value == "1")
				&& (form.motivoRichiamo != null && form.motivoRichiamo.value == '')) {
			formTest = false;
			message += label(
					"check.vigilanza.data_richiesta.selezionato",
					"- Controllare  di aver selezionato un motivo per le Procedure di richiamo non attivate \r\n");

		}

		if (form.esitoControllo.value == 13 || form.esitoControllo.value == 14) {
			if (form.destinazioneDistribuzione.value == -1) {
				formTest = false;
				message += label(
						"check.vigilanza.data_richiesta.selezionato",
						"- Controllare che i campi relativi a \"Destinazione Distribuzione partita\" siano stati popolati\r\n");

			}

			if (form.subject != null && form.subject.value == "") {
				formTest = false;
				message += label(
						"check.vigilanza.data_richiesta.selezionato",
						"- Controllare che il campo \"Oggetto per lista distribuzione\" sia stato popolato\r\n");

			}

			if (document.getElementById("fileAllegare") != null
					&& document.getElementById("fileAllegare").value == "") {
				formTest = false;
				message += label("check.vigilanza.data_richiesta.selezionato",
						"- Controllare di aver selezionato una \"Lista di distribuzione\" \r\n");

			}

			if (document.getElementById("isAllegato") != null
					&& (document.getElementById("isAllegato").value == "false")) {
				formTest = false;
				message += label(
						"check.vigilanza.data_richiesta.selezionato",
						"- Controllare di aver allegato il file cliccando sul pulsante  \"Allega\" \r\n");

			}

		}

		if (form.esitoControllo.value == -1) {
			formTest = false;
			message += label("check.vigilanza.data_richiesta.selezionato",
					"- Controllare di aver Selezionato un   \"Esito Controllo\" \r\n");

		} else {
			if (form.esitoControllo.value == 7) {
				if (form.dataddt.value == "") {
					formTest = false;
					message += label(
							"check.vigilanza.data_richiesta.selezionato",
							"- Controllare  di aver inserito \"La Data Per DDT \" \r\n");

				}

				if (form.numDdt.value == "") {
					formTest = false;
					message += label(
							"check.vigilanza.data_richiesta.selezionato",
							"- Controllare di aver inserito \"Numero DDT \" \r\n");

				}

			} else {

				if (form.esitoControllo.value == 8) {

					if (form.quantita.value == "") {
						formTest = false;
						message += label(
								"check.vigilanza.data_richiesta.selezionato",
								"- Controllare  di aver inserito La \"Quantita \" \r\n");

					}

				} else {
					if (form.esitoControllo.value == 10
							|| form.esitoControllo.value == 11) {
						if (form.quantitaBloccata.value == "") {
							formTest = false;
							message += label(
									"check.vigilanza.data_richiesta.selezionato",
									"- Controllare di aver inserito La \"Quantita Bloccata \" \r\n");

						}

					}
				}

			}

		}

		value = form.azioniAdottate;
		settatoAzione1 = false;
		settatoAzione2 = false;
		for (i = 0; i < value.length; i++) {
			if (value[i].selected == true) {

				if ((value[i].value == "-1") || (value[i].value == "")) {
					formTest = false;
					message += label(
							"check.vigilanza.data_richiesta.selezionato",
							"- Controllare  di non aver selezionato la voce \"Seleziona Voce \" da Azioni Adottate \r\n");

				} else {
					if (value[i].value == "3") {
						// if(form.articoliAzioni.value=="-1")
						// {
						// formTest = false;
						// message +=
						// label("check.vigilanza.data_richiesta.selezionato","-
						// Controllare di aver Selezionato un \"Articolo \"
						// \r\n");
						//
						//
						// }
						// else
						// {
						messaggio = true;
						// }
					}
				}
			}

		}

	}

	if (form.closeNow) {
		if (form.closeNow.checked && form.solution.value == "") {
			message += label("check.ticket.resolution.atclose",
					"- Resolution needs to be filled in when closing a ticket\r\n");
			formTest = false;
		}
	}

	if (formTest) {
		if (typeof controllaDatiEstesi == 'function') {
			formTest = controllaDatiEstesi();
			if (!formTest)
				message += 'Dati estesi non compilati';
		}
	}

	if (form.dim != null && form.dim.value != 0) {
		alert("ATTENZIONE! Si ricorda che sara\' inserito un controllo per ogni operatore associato\r\n ");
	}

	if (formTest == false) {
		loadModalWindowUnlock();
		alert(label("check.form",
				"Form could not be saved, please check the following:\r\n\r\n")
				+ message);
		return false;
	} else {

		if (flagEsisteCu == true)
			formTest = confirm('Attenzione per la corrente impresa esiste gi� un controllo inserito in data '
					+ dataCu);
		dataCu = '';

		if (formTest == true && isAllarmeRapido == true) {

			showMessaggioAllarme(form);

		}

		if (formTest == true && form != null && form.tipoCampione.value ==4) {
			if (form.id_linea_sottoposta_a_controllo != null) {

				alert('"ATTENZIONE! Qualora siano state controllate, nel corso dello stesso controllo, piu\' linee attivita\' occorre inserire piu\' controlli (uno per ogni linea attivita\' sottoposta a controllo)"'
						.toUpperCase());
			}
		}

	}

	if (formTest == true) {

		if (document.getElementById('tipooperatorecanipadronali') == null) {
			setTimestampStartRichiesta();

			form.submit();
			return true;

		} else {

			if (flagDecesso == true) {
				aler("Attenzione stai inserendo un controllo su un animale deceduto. Continuare )");

				setTimestampStartRichiesta();
				form.submit();
				return true;

			}

		}
		// win.document.location=link;

	}
	loadModalWindowUnlock();
	return false;
}

function checkForm(form) {
	isSupervisione = false;
	formTest = true;
	// Perch� ci sta questo controllo per la modifica?

	if (form.orgId != null)
		controlloEsistenzaCu(form.assignedDate.value, form.orgId.value);

	if (form.idStabilimento != null)
		controlloEsistenzaCuOpu(form.assignedDate.value,
				form.idStabilimento.value);

	entratoinpiano = false;
	message = "";

	if (form.comuneControllo != null && form.comuneControllo.value == '') {
		message += label("",
				"- Selezionare Il comune in cui è avvenuto il controllo \r\n");
		formTest = false;
	}

	if (form.luogoControllo != null && form.luogoControllo.value == '') {
		message += label("",
				"- Compilare Il luogo in cui è avvenuto il controllo \r\n");
		formTest = false;
	}

	
	if (form.tipoCampione.value == '4' || form.tipoCampione.value == '3') {
		tipiispezione = document.getElementsByName('tipoIspezione');
		if (tipiispezione != null)

			for (i = 0; i < tipiispezione.length; i++) {
				getCodiceInternoTipoIspezione(tipiispezione[i].value);

				if (codiceInternoTipoIspezione == '2a'
						&& entratoinpiano == false) {
					entratoinpiano = true;
					indice = 1;
					while (document.getElementById('uo' + indice) != null) {
						uo = document.getElementById('uo' + indice);
						if (uo.value == '-1') {
							message += label("",
									"- Selezionare la voce per Conto di \r\n");
							formTest = false;
						}
						indice++;
					}
				} else

				if (codiceInternoTipoIspezione != '2a') {

					if (document.getElementById('per_condo_di'
							+ tipiispezione[i].value) != null) {
						if (document.getElementById('per_condo_di'
								+ tipiispezione[i].value).value == '-1') {
							message += label("",
									"- Selezionare la voce per Conto di  Nelle Attivita Selezionate \r\n");
							formTest = false;

						}

					}

				}
				
				if (codiceInternoTipoIspezione!= null && codiceInternoTipoIspezione.toLowerCase() == '58a') {
					if (form.elements["azione"].value == null || form.elements["azione"].value == '') {
						message += label("", "- Indicare se Le azioni correttive risultano adeguate ed efficaci\r\n");
						formTest = false;
					}
				}

			} // FINE FOR

		if (document.getElementById('quintali') != null)

		{

			if (document.getElementById('quintali').value != '') {
				if (controlloNumerico(document.getElementById('quintali').value) == false) {
					message += label("",
							"- Controllare di aver inserito un numero \r\n");
					formTest = false;
				}
			}

		}
	} else {

		if (form.tipoCampione.value == '2') {
			if (form.ispezione.value == "-1" || form.ispezione.value == "") {// se
																				// non
																				// ho
																				// selezionato
																				// nessun
																				// piano
																				// di
																				// monitoraggio

				message += label("check.vigilanza.richiedente.selezionato",
						"- Controllare che il campo \"Oggetto del controllo\" sia stato popolato\r\n");
				formTest = false;

			}
		}

		uo = document.getElementsByName("uo_controllo");
		if (uo != null) {
			for (i = 0; i < uo.length; i++) {

				if (uo.value == '-1') {
					message += label("",
							"- Selezionare la voce per Conto di \r\n");
					formTest = false;
				}

			}
		}

	}

	if (form.tipoCampione.value == '7') {
		if (document.getElementById('oggetto_audit').value == -1) {
			message += label("",
					"- Selezionare la voce Oggetto dell'Audit \r\n");
			formTest = false;
		}
	}

	if (form.struttura_asl != null) {
		sel = true;
		array = form.struttura_asl;
		for (i = 0; i < struttura_asl.length; i++) {
			if (array[i].value == '-1' && array[i].selected == true) {
				sel = false;
				break;
			}
		}

		if (document.getElementById('parte_asl').checked && sel == false) {
			message += label("", "- Selezionare la struttura controllata \r\n");
			formTest = false;
		}

	}

	if (form.quantita != null) {
		if (trim(form.quantita.value) != '') {
			value = new String(form.quantita.value);

			if (isNaN(value.replace(",", "."))) {
				message += label("",
						"- Valore di Quantita Partita non Valido. Inserire Cifre\r\n");
				formTest = false;
			} else {
				form.quantita.value = trim(value.replace(",", "."));
			}

		} else {

			form.quantita.value = "0";
		}
	}

	if (form.quantitaBloccata != null) {
		if (trim(form.quantitaBloccata.value) != '') {
			value = new String(form.quantitaBloccata.value);

			if (isNaN(value.replace(",", "."))) {
				message += label("",
						"- Valore di Quantita Partita Bloccata non Valido. Inserire Cifre\r\n");
				formTest = false;
			} else {
				form.quantitaBloccata.value = trim(value.replace(",", "."));
			}

		} else {

			form.quantitaBloccata.value = "0";
		}
	}

	if (form.peso != null)
		if (isNaN(form.peso.value.replace(",", "."))) {
			message += label("", "- Peso non Valido. Inserire Cifre\r\n");
			formTest = false;
		}

	if (form.struttura_asl != null) {
		sel = true;
		array = form.struttura_asl;
		for (i = 0; i < struttura_asl.length; i++) {
			if (array[i].value == '-1' && array[i].selected == true) {
				sel = false;
				break;
			}
		}

		if (document.getElementById('parte_asl').checked && sel == false) {
			message += label("", "- Selezionare la struttura controllata \r\n");
			formTest = false;
		}

	}

	if (form.quantita != null) {
		if (trim(form.quantita.value) != '') {
			value = new String(form.quantita.value);

			if (isNaN(value.replace(",", "."))) {
				message += label("",
						"- Valore di Quantita Partita non Valido. Inserire Cifre\r\n");
				formTest = false;
			} else {
				form.quantita.value = trim(value.replace(",", "."));
			}

		}
	}

	if (form.quantitaBloccata != null) {
		if (trim(form.quantitaBloccata.value) != '') {
			value = new String(form.quantitaBloccata.value);

			if (isNaN(value.replace(",", "."))) {
				message += label("",
						"- Valore di Quantita Partita Bloccata non Valido. Inserire Cifre\r\n");
				formTest = false;
			} else {
				form.quantitaBloccata.value = trim(value.replace(",", "."));
			}

		}
	}

	if (form.peso != null) {
		form.peso.value.replace(",", ".");
		if (isNaN(form.peso.value.replace(",", "."))) {
			message += label("", "- Peso non Valido. Inserire Cifre\r\n");
			formTest = false;
		}
	}

	if (form.tipoCampione.value != '5') {

		if (form.tipoCampione.value == '4') {

			/*
			 * if(form.id_linea_sottoposta_a_controllo!=null &&
			 * form.id_linea_sottoposta_a_controllo.value =='-1') { message +=
			 * label("","- Controllare di aver Selezionato almeno una linea di
			 * attivita.\r\n"); formTest = false; }
			 */

			if (form.id_linea_sottoposta_a_controllo
					&& form.id_linea_sottoposta_a_controllo.value == '-1') {
				message += label("",
						"- Controllare di aver Selezionato almeno una linea di attivita.\r\n");
				formTest = false;
			}

			if (form.codici_selezionabili && form.codici_selezionabili != null) {
				selected = false;

				array = form.codici_selezionabili;
				for (i = 0; i < array.length; i++) {
					if (array[i].value != '') {
						selected = true;
					}

				}
			}

			if (form.codici_selezionabili && form.codici_selezionabili != null
					&& selected == false) {
				message += label("",
						"- Controllare di aver Selezionato almeno una linea di attivita'.\r\n");
				formTest = false;
			}

			/*
			 * if(form.codici_selezionabili!=null &&
			 * form.codici_selezionabili.value =='') { message += label("","-
			 * Controllare di aver Selezionato almeno una linea di
			 * attivita.\r\n"); formTest = false; }
			 */

		} 
		
		else if (form.tipoCampione.value == '3') {
			var selezionati = 0;
			// if(form.id_linea_sottoposta_a_controllo!=null )
			if (form.id_linea_sottoposta_a_controllo) {
				option = form.id_linea_sottoposta_a_controllo;
				selected = true;
				for (i = 0; i < option.length; i++) {
					if (option[i].selected == true && option[i].value == '-1') {
						selected = false;
					}
					if (option[i].selected && option[i].value != '-1')
						selezionati++;
				}
				if (selezionati ==0)
					selected = false;
				
				if (selected == false) {
					message += label("",
							"- Controllare di aver Selezionato almeno una linea di attivita e di non aver selezionato anche la voce 'Selezionare una o piu linee'.\r\n");
					formTest = false;
				}
			}
			selected = false;

			// if(form.codici_selezionabili!= null )
			if (form.codici_selezionabili) {
				array = form.codici_selezionabili;
				for (i = 0; i < array.length; i++) {
					if (array[i].value != '') {
						selected = true;
					}

				}

				if (selected == false) {

					message += label("",
							"- Controllare di aver Selezionato almeno una linea di attivita.\r\n");
					formTest = false;
				}
			}
		
		}
				
		else {

			// if(form.id_linea_sottoposta_a_controllo!=null )
			if (form.id_linea_sottoposta_a_controllo) {
				option = form.id_linea_sottoposta_a_controllo;
				selected = false;
				for (i = 0; i < option.length; i++) {
					if (option[i].selected == true && option[i].value != '-1') {
						selected = true;
					}

				}
				if (selected == false) {
					message += label("",
							"- Controllare di aver Selezionato almeno una linea di attivita.\r\n");
					formTest = false;
				}
			}
			selected = false;

			// if(form.codici_selezionabili!= null )
			if (form.codici_selezionabili) {
				array = form.codici_selezionabili;
				for (i = 0; i < array.length; i++) {
					if (array[i].value != '') {
						selected = true;
					}

				}

				if (selected == false) {

					message += label("",
							"- Controllare di aver Selezionato almeno una linea di attivita.\r\n");
					formTest = false;
				}
			}

		}

	}

	if (form.tipoCampione.value != '2') {
		if (form.nucleo_ispettivo_1.value == '-1') {
			message += label("",
					"- Controllare di aver Selezionato almeno un nucleo Ispettivo.\r\n");
			formTest = false;
		}

		for (i = 1; i <= 10; i++) {
			if (document.getElementById('nucleo_ispettivo_' + i) != null) {
				if (document.getElementById('nucleo_ispettivo_' + i).value != '-1') {
					value = document.getElementById('nucleo_ispettivo_' + i).value;

					if (document.getElementById('risorse_' + i).value == '-1'
							&& document.getElementById('Utente_' + i).value == '') {

						message += label("",
								"- Controllare di aver Selezionato un Componente Per il Nucleo Ispettivo.\r\n");
						formTest = false;
					}

				}

			}
		}
	}
	
	//commento al 214
	
	if (document.getElementById('misureFormativetr')!=null && document.getElementById('misureFormativetr').style.display!= 'none' && form.misureFormative!= null && form.misureFormative.value == '' ) {
		message += label("",
				"- Controllare che il Campo \"Misure formative\" sia stato inserito\r\n");
		formTest = false;
	}
	
	if (document.getElementById('misureRestrittivetr')!=null && document.getElementById('misureRestrittivetr').style.display!= 'none' && form.misureRestrittive!= null && form.misureRestrittive.value == '') {
		message += label("",
				"- Controllare che il Campo \"Misure restrittive\" sia stato inserito\r\n");
		formTest = false;
	}
	
	if (document.getElementById('misureRiabilitativetr')!=null && document.getElementById('misureRiabilitativetr').style.display!= 'none' &&form.misureRiabilitative!= null && form.misureRiabilitative.value == '') {
		message += label("",
				"- Controllare che il Campo \"Misure riabilitative\" sia stato inserito\r\n");
		formTest = false;
	}
	
	if (form.via_luogocontrollo!= null && form.via_luogocontrollo.value == '') {
		message += label("",
				"- Controllare di aver Selezionato il luogo del controllo.\r\n");
		formTest = false;
	}

	//commento al 247
	if (document.getElementById('apiariSelezionatitr')!=null && document.getElementById('apiariSelezionatitr').style.display!= 'none' && form.apiariSelezionati!= null && form.apiariSelezionati.value == '' ) {
		message += label("",
				"- Controllare che il Campo \"Informazioni Stato Sanitario Apiari - Apiari Selezionati\" sia stato valorizzato\r\n");
		formTest = false;
	}
	if (document.getElementById('apiariSelezionatitr')!=null && document.getElementById('apiariSelezionatitr').style.display!= 'none' && form.apiariSelezionati!= null && (form.apiariSelezionati.value == 'B') && form.apiariSelezionatiMotivo!= null && (form.apiariSelezionatiMotivo.value == '' || form.apiariSelezionatiMotivo.value == '-1') ) {
		message += label("",
				"- Controllare che il Campo \"Informazioni Stato Sanitario Apiari - Motivo\" sia stato valorizzato\r\n");
		formTest = false;
	}
	if (document.getElementById('apiariSelezionatitr')!=null && document.getElementById('apiariSelezionatitr').style.display!= 'none' && form.apiariSelezionati!= null && (form.apiariSelezionati.value == 'A' || form.apiariSelezionati.value == 'B') && form.apiariSelezionatiAlveariControllati!= null && form.apiariSelezionatiAlveariControllati.value == '' ) {
		message += label("",
				"- Controllare che il Campo \"Informazioni Stato Sanitario Apiari - Numero Alveari Controllati\" sia stato valorizzato\r\n");
		formTest = false;
	}
	if (document.getElementById('apiariSelezionatitr')!=null && document.getElementById('apiariSelezionatitr').style.display!= 'none' && form.apiariSelezionati!= null && (form.apiariSelezionati.value == 'A' || form.apiariSelezionati.value == 'B') && form.apiariSelezionatiEsito!= null && form.apiariSelezionatiEsito.value == '' ) {
		message += label("",
				"- Controllare che il Campo \"Informazioni Stato Sanitario Apiari - Esito\" sia stato valorizzato\r\n");
		formTest = false;
	}
	
	selected = false;
	if (form.alertText) {

		array = form.alertText;
		for (i = 0; i < array.length; i++) {
			if (array[i].value != '') {
				selected = true;
			}

		}

		// if (selected == false ){
		// if (form.tipoIspezione.value != "3" && form.tipoCampione.value!="5")
		// {
		// message += label("","- Controllare di aver inserito una linea di
		// attivita.\r\n");
		// formTest = false;
		// }
		// }
	}

	/*
	 * if(!form.assignedDate.value == "" && !form.dataFineControllo.value ==
	 * ""){
	 * 
	 * if (controlloDataFine(form.assignedDate.value,
	 * form.dataFineControllo.value)== true ){
	 * 
	 * message += label("","- La Data di Inizio Controllo non puo\' essere
	 * successiva a quella di Fine Controllo.\r\n"); formTest = false; } }
	 */

	isAllarmeRapido = false;
	if (form.contributi != null) {
		var numero = form.contributi.value;

		var arr_num = numero.split(".");

		if (numero.indexOf(",") != (-1)) {

			message += label(
					"check.vigilanza.richiedente.selezionato",
					"- Controllare che il sepratore delle cifre decimali per i contributi sia il punto anzich� la virgola \r\n");
			formTest = false;
		}
	}

	if (form.tipoCampione.value != "-1") {
		if (form.tipoCampione.value == "7") { // se
																				// ho
																				// selezionato
																				// audit
			if (form.auditTipo != null) {
				if (form.auditTipo.value == "1") {
					isbpi = false;
					ishaccp = false;
					isSelTipoAudit = false;

					if (form.tipoAudit != null) {
						isSelTipoAudit = true;
					}

					if (isSelTipoAudit == false) {
						message += label(
								"check.vigilanza.richiedente.selezionato",
								"- Controllare che il campo \"tipo Audit\" sia stato popolato\r\n");
						formTest = false;
					}
				}
			} else {
				message += label("check.vigilanza.richiedente.selezionato",
						"- Controllare che il campo \"tipo Audit\" sia stato popolato\r\n");
				formTest = false;

			}

		} else if (form.tipoCampione.value == "22") {
			isSupervisione = true;
		}
		if (isSupervisione == true) {
			if (document.getElementById("fileAllegareSupervisione") != null
					&& (document.getElementById("fileAllegareSupervisione").value == "-1" || document
							.getElementById("fileAllegareSupervisione").value == "")) {
				formTest = false;
				message += label("",
						"- Controllare di aver selezionato un \"Verbale\" \r\n");

			}
			if (form.isAllegatoSupervisione.value == "false"
					|| form.isAllegatoSupervisione.value == "") {
				formTest = false;
				message += label(
						"",
						"- Controllare di aver allegato il file cliccando sul pulsante  \"Allega\" \r\n");

			}

		}

		else {

			if (form.tipoCampione.value == "3" || form.tipoCampione.value == "4") {// se ho selezionato
													// ispezione o audit

				isSelTipoIspezione = false;
				isPianoMonitoraggio = false;
				isSospetto = false;
				if (form.tipoIspezione != null) {

					for (i = 0; i < document.getElementsByName('tipoIspezione').length; i++) {
						if (document.getElementsByName('tipoIspezione')[i].value != '-1') {
							isSelTipoIspezione = true;

							getCodiceInternoTipoIspezione(document
									.getElementsByName('tipoIspezione')[i].value);
							// codiceInternoTipoIspezione =
							// $("#"+document.getElementsByName('tipoIspezione')[i].id).attr("codiceinterno")

							if (codiceInternoTipoIspezione == '2a') {
								isPianoMonitoraggio = true;
							}
							if (codiceInternoTipoIspezione == '7a') {
								isAllarmeRapido = true;
							}
							if (codiceInternoTipoIspezione == '9a') {
								isSospetto = true;
							}
						}

					}
				} else {

					if (form.context == null)
						isSelTipoIspezione = true;

				}

				if (isSospetto) {
					if (form.tipoSosp.value == "-1") {
						formTest = false;
						message += label("",
								"- Controllare  di aver selezionato un  \"Tipo di sospetto\" \r\n");
					}

					if (form.idBuffer.value == "" && form.tipoSosp.value == "1") {
						formTest = false;
						message += label("",
								"- Controllare  di aver selezionato un \"Buffer\" \r\n");
					}
				}

				if (isSelTipoIspezione == false) // se non ho selezionato
													// niente come tipo
													// ispezione(in monitoraggio
													// non , e sorveglianza)
				{
					message += label("check.vigilanza.richiedente.selezionato",
							"- Controllare che il campo \"Tipo di Ispezione\" sia stato popolato\r\n");
					formTest = false;

				} else {

					if (isPianoMonitoraggio == true) // se ho selezionato
														// tipo ispezione in
														// monitoraggio
					{
						if ((form.piano_monitoraggio1 == null)
								|| (form.piano_monitoraggio1 != null && form.piano_monitoraggio1.value == "-1")) {// se
																													// non
																													// ho
																													// selezionato
																													// nessun
																													// piano
																													// di
																													// monitoraggio

							message += label(
									"check.vigilanza.richiedente.selezionato",
									"- Controllare che il \"Piano di monitoraggio\" sia stato selezionato\r\n");
							formTest = false;
						}

						i = 1;

						var condizionalita_A = false;
						var condizionalita_B = false;

						while (document
								.getElementById('piano_monitoraggio' + i) != null) {
							getCodiceInternoTipoPiano(document
									.getElementById('piano_monitoraggio' + i).value);

							if ((codiceInternoTipoIspezione == '982' || codiceInternoTipoIspezione == '983')
									&& flagCondizionalitaReturn == 'true')
								condizionalita_A = true;
							if (codiceInternoTipoIspezione == '1483'
									&& flagCondizionalitaReturn == 'true')
								condizionalita_B = true;

							if (codiceInternoTipoIspezione == '982' || codiceInternoTipoIspezione == '983') {
								if (document.getElementById('flag_preavviso') != null
										&& document
												.getElementById('flag_preavviso').value != 'N'
										&& document
												.getElementById('flag_preavviso').value != '-1'
										&& (document
												.getElementById('data_preavviso_ba') != null && document
												.getElementById('data_preavviso_ba').value == '')) {
									message += label(
											"check.vigilanza.richiedente.selezionato",
											"- Controllare che la \"Data Preavviso\" sia stata inserita\r\n");
									formTest = false;
									// break;
								}

								if (document.getElementById('flag_preavviso') != null
										&& document
												.getElementById('flag_preavviso').value == '-1') {
									message += label(
											"check.vigilanza.richiedente.selezionato",
											"- Controllare che il Campo \"Preavviso\" sia stato selezionato\r\n");
									formTest = false;
									// break;
								}
								
								if (document.getElementById('flag_checklist') != null
										&& document
												.getElementById('flag_checklist').value == '-1') {
									message += label(
											"check.vigilanza.richiedente.selezionato",
											"- Controllare che il Campo \"E' stata consegnata una copia della presente checklist\" sia stato selezionato\r\n");
									formTest = false;
									// break;
								}

								var pianovalore = document
										.getElementById('piano_monitoraggio'
												+ i).value;

								if (flagCondizionalitaReturn == 'true') {
									var optcondiz = document
											.getElementById("condizionalita");
									for (jj = 0; jj < optcondiz.length; jj++) {
										if (optcondiz[jj].selected == true
												&& optcondiz[jj].value == '-1') {
											message += label("",
													"- Selezionare la voce Condizionalita \r\n");
											formTest = false;

										}
									}
								}

							}
							i++;

						}

						if (condizionalita_A) {
							var condSelect = document
									.getElementById("condizionalita");
							var condSelected = new Array();
							for (var c = 0; c < condSelect.length; c++) {
								if (condSelect[c].selected) {
									condSelected.push(condSelect[c].value);
								}
							}

							if (condSelected.length == -0
									&& !document.getElementById("cond_cb").checked) {
								message += label("",
										"- Effettuare almeno una scelta di tipo Condizionalità. \r\n");
								formTest = false;
							}

						}

					}
				}

				if (form.ispezione.value == "-1" || form.ispezione.value == "") {// se
																					// non
																					// ho
																					// selezionato
																					// nessun
																					// piano
																					// di
																					// monitoraggio

					message += label("check.vigilanza.richiedente.selezionato",
							"- Controllare che il campo \"Oggetto del controllo\" sia stato popolato\r\n");
					formTest = false;

				}

				value_isp = document.getElementById("ispezione");
				for (i = 0; i < value_isp.length; i++) {
					if (value_isp[i].selected) {
						if (value_isp[i].value == "69"
								|| value_isp[i].value == "70"
								|| value_isp[i].value == "71"
								|| value_isp[i].value == "72"
								|| value_isp[i].value == "73"
								|| value_isp[i].value == "74"
								|| value_isp[i].value == "75"
								|| value_isp[i].value == "76") {

							if (document.getElementById("animalitrasp").value == "") {
								message += label(
										"check.vigilanza.richiedente.selezionato",
										"- Controllare che il campo \"Specie Animali Trasportati\" sia stato popolato\r\n");
								formTest = false;
							}

						}
					}
				}

				/* Controllare il numero relativo alla specie */
				specie = document.getElementById('animalitrasp');
				if (specie != null)
					for (i = 0; i < specie.length; i++) {
						if (!specie[i].selected) {
							if (specie[i].value != -1) {
								var elem = document.getElementById('num_specie'
										+ specie[i].value);
								if (elem != null)
									elem.value = -1;
							}

							// = '-1';
						} else {

							if (document.getElementById('num_specie'
									+ specie[i].value) != null) {

								if (document.getElementById('num_specie'
										+ specie[i].value).value == ""
										|| document.getElementById('num_specie'
												+ specie[i].value).value == "-1") {
									message += label(
											"check.vigilanza.richiedente.selezionato",
											"- Controllare che il campo \"Numero\" relativo alla specie selezionata sia popolato\r\n");
									formTest = false;
								}
								else if(parseInt(document.getElementById('num_specie'+ specie[i].value).value)<=0)
								{
									message += label(
										"check.vigilanza.richiedente.selezionato",
										"- Controllare che il campo \"Numero\" relativo alla specie selezionata sia un numero positivo\r\n");
									formTest = false;
								}

							}

						}
					}

				messaggio = false;

				if (form.contributi_rilascio_certificazione != null
						&& form.contributi_rilascio_certificazione.value != ''
						&& isNaN(form.contributi_rilascio_certificazione.value
								.replace(",", "."))) {
					message += label("",
							"- Valore di Contributi per Rilascio Cert. non Valido. Inserire Cifre\r\n");
					formTest = false;
				}

				if (form.contributi_risol_nc != null
						&& form.contributi_risol_nc.value != ''
						&& isNaN(form.contributi_risol_nc.value.replace(",",
								"."))) {
					message += label(
							"",
							"- Valore di Contributi per Risoluzione non Conf.. non Valido. Inserire Cifre\r\n");
					formTest = false;
				}

				if (form.contributi != null && form.contributi.value != ''
						&& isNaN(form.contributi.value.replace(",", "."))) {
					message += label("",
							"- Valore di Contributi non Valido. Inserire Cifre\r\n");
					formTest = false;
				}

				if (form.contributi_macellazione_urgenza != null
						&& form.contributi_macellazione_urgenza.value != ''
						&& isNaN(form.contributi_macellazione_urgenza.value
								.replace(",", "."))) {
					message += label(
							"",
							"- Valore di Contributi per Macellazione Urgenza non Valido. Inserire Cifre\r\n");
					formTest = false;
				}

				if (isAllarmeRapido == true) {

					if (form.idAllerta.value == "") {
						formTest = false;
						message += label(
								"check.vigilanza.data_richiesta.selezionato",
								"- Controllare  di aver selezionato una  \"Allerta \" \r\n");

					}

					if (form.procedureRitiro != null
							&& form.procedureRitiro.value == "-1") {
						formTest = false;
						message += label(
								"check.vigilanza.data_richiesta.selezionato",
								"- Controllare  di aver selezionato una proceduta di ritiro \r\n");

					}

					if ((form.procedureRichiamo != null && form.procedureRichiamo.value == "1")
							&& (form.motivoRichiamo != null && form.motivoRichiamo.value == '')) {
						formTest = false;
						message += label(
								"check.vigilanza.data_richiesta.selezionato",
								"- Controllare  di aver selezionato un motivo per le Procedure di richiamo non attivate \r\n");

					}

					if (isNaN(form.contributi_allarme_rapido.value.replace(",",
							"."))) {
						message += label("",
								"- Valore di Contributi per Allarme Rapido non Valido. Inserire Cifre\r\n");
						formTest = false;
					}

					if (form.esitoControllo.value == '13'
							|| form.esitoControllo.value == '14') {
						if (form.destinazioneDistribuzione.value == -1) {
							formTest = false;
							message += label(
									"check.vigilanza.data_richiesta.selezionato",
									"- Controllare che i campi relativi a \"Destinazione Distribuzione partita\" siano stati popolati\r\n");

						}

						/*
						 * if(document.getElementById("fileAllegare")!= null &&
						 * (document.getElementById("fileAllegare").value ==
						 * "-1"
						 * ||document.getElementById("fileAllegare").value=="" )) {
						 * formTest = false; message +=
						 * label("check.vigilanza.data_richiesta.selezionato","-
						 * Controllare di aver selezionato una \"Lista di
						 * distribuzione\" \r\n");
						 *  }
						 */

						if (document.getElementById("isAllegato") != null
								&& (document.getElementById("isAllegato").value == "false" || document
										.getElementById("isAllegato").value == "")) {
							formTest = false;
							message += label(
									"check.vigilanza.data_richiesta.selezionato",
									"- Controllare di aver allegato il file cliccando sul pulsante  \"Allega\" \r\n");

						}

					}

					if (form.esitoControllo.value == '-1') {
						formTest = false;
						message += label(
								"check.vigilanza.data_richiesta.selezionato",
								"- Controllare di aver Selezionato un   \"Esito Controllo\" \r\n");

					} else {
						if (form.esitoControllo.value == '7') {
							if (form.dataddt.value == "") {
								formTest = false;
								message += label(
										"check.vigilanza.data_richiesta.selezionato",
										"- Controllare  di aver inserito \"La Data Per DDT \" \r\n");

							}

							if (form.numDdt.value == "") {
								formTest = false;
								message += label(
										"check.vigilanza.data_richiesta.selezionato",
										"- Controllare di aver inserito \"Numero DDT \" \r\n");

							}

						} else {

							if (form.esitoControllo.value == '8') {

								if (form.quantita.value == "") {
									formTest = false;
									message += label(
											"check.vigilanza.data_richiesta.selezionato",
											"- Controllare  di aver inserito La \"Quantita \" \r\n");

								}

							} else {
								if (form.esitoControllo.value == '10'
										|| form.esitoControllo.value == '11') {
									if (form.quantitaBloccata.value == "") {
										formTest = false;
										message += label(
												"check.vigilanza.data_richiesta.selezionato",
												"- Controllare di aver inserito La \"Quantita Bloccata \" \r\n");

									}

								}
							}

						}

					}

					value = form.azioniAdottate;
					settatoAzione1 = false;
					settatoAzione2 = false;
					if (value != null)
						for (i = 0; i < value.length; i++) {
							if (value[i].selected == true) {

								if ((value[i].value == "-1")
										|| (value[i].value == "")) {
									formTest = false;
									message += label(
											"check.vigilanza.data_richiesta.selezionato",
											"- Controllare  di non aver selezionato la voce \"Seleziona Voce \" da Azioni Adottate \r\n");

								} else {
									if (value[i].value == "3") {
										// if(form.articoliAzioni.value=="-1")
										// {
										// formTest = false;
										// message +=
										// label("check.vigilanza.data_richiesta.selezionato","-
										// Controllare di aver Selezionato un
										// \"Articolo \" \r\n");
										//
										//
										// }
										// else
										// {
										messaggio = true;
										// }
									}
								}
							}

						}

				}

			} else {

				// if(form.tipoCampione.value=="5"){
				// if(form.ispezione.value=="-1" || form.ispezione.value=="")
				// {//se non ho selezionato nessun piano di monitoraggio
				//
				// message += label("check.vigilanza.richiedente.selezionato","-
				// Controllare che il campo \"Oggetto del controllo\" sia stato
				// popolato\r\n");
				// formTest = false;
				//
				// }
				// }
			}

		}
	}

	var dataInizioControllo = form.assignedDate.value;
	var flagDecesso = false;
	var flagDataDecessoPrecedenteDataControllo = false;

	var dateParts = dataInizioControllo.split("/");

	var datecu = new Date(dateParts[2], (dateParts[1] - 1), dateParts[0]);

	if (document.getElementById("size_p") != null) {
		numeroElementi = document.getElementById("size_p").value;

		for (i = 1; i <= numeroElementi; i++) {

if (document.getElementById("mc_" + i) != null)
				{
			if (document.getElementById("mc_" + i).value == ""
					&& document.getElementById("razza_" + i).value == ""
					&& document.getElementById("sesso_" + i).value == ""
					&& document.getElementById("mantello_" + i).value == ""
					&& document.getElementById("taglia_" + i).value == ""
					&& document.getElementById("data_nascita_cane_" + i).value == "" && ((document.getElementById("canicontrollatitr")!=null && document.getElementById("canicontrollatitr").style.display!='none') || (document.getElementById("canicontrollatitrCaniPadronali")!=null && document.getElementById("canicontrollatitrCaniPadronali").style.display!='none')))
			{
				message += label("check.vigilanza.richiedente.selezionato",
						"- Controllare di aver compilato qualche informazione sul cane controllato\r\n");
				formTest = false;
				break;
			}

			var dataDecesso = '';
			if (document.getElementById("data_decesso_" + i) != null)
				dataDecesso = document.getElementById("data_decesso_" + i).value;

			if (dataDecesso != ''
					&& document.getElementById("data_decesso_" + i) != null) {
				flagDecesso = true;

				var dateParts2 = dataDecesso.split("/");

				var dateDec = new Date(dateParts2[2], (dateParts2[1] - 1),
						dateParts2[0]);

				if (dateDec < datecu) {
					flagDataDecessoPrecedenteDataControllo = true;

					break;
				}
				
					}

			}

		}

		if (flagDataDecessoPrecedenteDataControllo == true) {
			message += label(
					"check.vigilanza.richiedente.selezionato",
					"- Attenzione la data di decesso ("
							+ dataDecesso
							+ ") dell'animale è antecedente alla data di inizio controllo \r\n");
			formTest = false;
		}

	}

	if (form.nome_conducente != null && form.nome_conducente.value == "") {
		message += label("check.vigilanza.richiedente.selezionato",
				"- Controllare che il campo \"Nominativo Conduttore\" sia stato popolato\r\n");
		formTest = false;
	}

	if (form.nominativo_proprietario != null
			&& form.nominativo_proprietario.value == "") {
		message += label("check.vigilanza.richiedente.selezionato",
				"- Controllare che il campo \"Nominativo Proprietario\" sia stato popolato\r\n");
		formTest = false;
	}

	if (form.cf_proprietario != null && form.cf_proprietario.value == "") {
		message += label("check.vigilanza.richiedente.selezionato",
				"- Controllare che il campo \"CF Proprietario\" sia stato popolato\r\n");
		formTest = false;
	}

	if (form.siteId.value == "-1") {
		message += label("check.vigilanza.richiedente.selezionato",
				"- Controllare che il campo \"A.S.L.\" sia stato popolato\r\n");
		formTest = false;
	}

	if ((form.orgId != null && form.orgId.value == "-1") && (form.idStabilimentoopu != null && form.idStabilimentoopu.value == "-1")) {
		message += label("check.vigilanza.richiedente.selezionato",
				"- Controllare che \"Impresa\" sia stato selezionato\r\n");
		formTest = false;
	}

	// Controllo per il campo categoria trasportata
	if (document.getElementById("categoriaTrasportata") != null) {
		if (document.getElementById("categoriaTrasportata").value == -1) {
			message += label("check.vigilanza.richiedente.selezionato",
					"- Controllare che \"Categoria Trasportata\" sia stato selezionato\r\n");
			formTest = false;
		}
	}

	if (form.tipoCampione.value == "-1") {
		message += label("check.campioni.richiedente.selezionato",
				"- Controllare che \"Tipo di Controllo\" sia stato popolato\r\n");
		formTest = false;
	}

	if (form.assignedDate.value == "") {
		message += label("check.vigilanza.data_richiesta.selezionato",
				"- Controllare che il campo \"Data Inizio Controllo\" sia stato popolato\r\n");
		formTest = false;
	} else {
		flag = controllo_data(form.assignedDate.value)

		if (flag == false) {
			formTest = false;
			message += label(
					"check.vigilanza.data_richiesta.selezionato",
					"- Controllare che il campo \"Data Inizio Controllo\" sia stato popolato Correttamente\r\n");

		} else {

		}

	}

	if (document.getElementById("flag_preavviso") != null
			&& document.getElementById("flag_preavviso").value != '-1'
			&& document.getElementById("flag_preavviso").value != 'N'
			&& document.getElementById("data_preavviso_ba").value == '') {
		message += label("check.vigilanza.richiedente.selezionato",
				"- Controllare che il Campo \"Data Preavviso\" sia stato selezionato\r\n");
		formTest = false;
	}

	/*
	 * if(controlloData(form.assignedDate.value)==false){ formTest = false;
	 * message += label("check.vigilanza.data_richiesta.selezionato","-
	 * Controllare che il campo \"Data Inizio Controllo\" sia stato Precedente o
	 * uguale alla data attuale\r\n"); }
	 */
	messaggio = false;

	if (form.tipoIspezione != null && form.tipoIspezione.value == "7a") {

		if (form.idAllerta.value == "") {
			formTest = false;
			message += label("check.vigilanza.data_richiesta.selezionato",
					"- Controllare  di aver selezionato una  \"Allerta \" \r\n");

		}

		if (form.procedureRitiro != null && form.procedureRitiro.value == "-1") {
			formTest = false;
			message += label("check.vigilanza.data_richiesta.selezionato",
					"- Controllare  di aver selezionato una proceduta di ritiro \r\n");

		}

		if ((form.procedureRichiamo != null && form.procedureRichiamo.value == "1")
				&& (form.motivoRichiamo != null && form.motivoRichiamo.value == '')) {
			formTest = false;
			message += label(
					"check.vigilanza.data_richiesta.selezionato",
					"- Controllare  di aver selezionato un motivo per le Procedure di richiamo non attivate \r\n");

		}

		if (form.esitoControllo.value == 13 || form.esitoControllo.value == 14) {
			if (form.destinazioneDistribuzione.value == -1) {
				formTest = false;
				message += label(
						"check.vigilanza.data_richiesta.selezionato",
						"- Controllare che i campi relativi a \"Destinazione Distribuzione partita\" siano stati popolati\r\n");

			}

			if (form.subject != null && form.subject.value == "") {
				formTest = false;
				message += label(
						"check.vigilanza.data_richiesta.selezionato",
						"- Controllare che il campo \"Oggetto per lista distribuzione\" sia stato popolato\r\n");

			}

			if (document.getElementById("fileAllegare") != null
					&& document.getElementById("fileAllegare").value == "") {
				formTest = false;
				message += label("check.vigilanza.data_richiesta.selezionato",
						"- Controllare di aver selezionato una \"Lista di distribuzione\" \r\n");

			}

			if (document.getElementById("isAllegato") != null
					&& (document.getElementById("isAllegato").value == "false")) {
				formTest = false;
				message += label(
						"check.vigilanza.data_richiesta.selezionato",
						"- Controllare di aver allegato il file cliccando sul pulsante  \"Allega\" \r\n");

			}

		}

		if (form.esitoControllo.value == -1) {
			formTest = false;
			message += label("check.vigilanza.data_richiesta.selezionato",
					"- Controllare di aver Selezionato un   \"Esito Controllo\" \r\n");

		} else {
			if (form.esitoControllo.value == 7) {
				if (form.dataddt.value == "") {
					formTest = false;
					message += label(
							"check.vigilanza.data_richiesta.selezionato",
							"- Controllare  di aver inserito \"La Data Per DDT \" \r\n");

				}

				if (form.numDdt.value == "") {
					formTest = false;
					message += label(
							"check.vigilanza.data_richiesta.selezionato",
							"- Controllare di aver inserito \"Numero DDT \" \r\n");

				}

			} else {

				if (form.esitoControllo.value == 8) {

					if (form.quantita.value == "") {
						formTest = false;
						message += label(
								"check.vigilanza.data_richiesta.selezionato",
								"- Controllare  di aver inserito La \"Quantita \" \r\n");

					}

				} else {
					if (form.esitoControllo.value == 10
							|| form.esitoControllo.value == 11) {
						if (form.quantitaBloccata.value == "") {
							formTest = false;
							message += label(
									"check.vigilanza.data_richiesta.selezionato",
									"- Controllare di aver inserito La \"Quantita Bloccata \" \r\n");

						}

					}
				}

			}

		}

		value = form.azioniAdottate;
		settatoAzione1 = false;
		settatoAzione2 = false;
		for (i = 0; i < value.length; i++) {
			if (value[i].selected == true) {

				if ((value[i].value == "-1") || (value[i].value == "")) {
					formTest = false;
					message += label(
							"check.vigilanza.data_richiesta.selezionato",
							"- Controllare  di non aver selezionato la voce \"Seleziona Voce \" da Azioni Adottate \r\n");

				} else {
					if (value[i].value == "3") {
						// if(form.articoliAzioni.value=="-1")
						// {
						// formTest = false;
						// message +=
						// label("check.vigilanza.data_richiesta.selezionato","-
						// Controllare di aver Selezionato un \"Articolo \"
						// \r\n");
						//
						//
						// }
						// else
						// {
						messaggio = true;
						// }
					}
				}
			}

		}

	}

	if (form.closeNow) {
		if (form.closeNow.checked && form.solution.value == "") {
			message += label("check.ticket.resolution.atclose",
					"- Resolution needs to be filled in when closing a ticket\r\n");
			formTest = false;
		}
	}

	if (formTest) {
		if (typeof controllaDatiEstesi == 'function') {
			formTest = controllaDatiEstesi();
			if (!formTest)
				message += 'Dati estesi non compilati';
		}
	}

	if (form.dim != null && form.dim.value != 0) {
		alert("ATTENZIONE! Si ricorda che sara\' inserito un controllo per ogni operatore associato\r\n ");
	}

	if (formTest == false) {
		loadModalWindowUnlock();
		alert(label("check.form",
				"Form could not be saved, please check the following:\r\n\r\n")
				+ message);
		return false;
	} else {

		if (flagEsisteCu == true)
			formTest = confirm('Attenzione per la corrente impresa esiste gi� un controllo inserito in data '
					+ dataCu);
		dataCu = '';

		if (formTest == true && isAllarmeRapido == true) {

			showMessaggioAllarme(form);

		}

		if (formTest == true && form != null && form.tipoCampione.value == 4) {
			if (form.id_linea_sottoposta_a_controllo != null) {

				alert('"ATTENZIONE! Qualora siano state controllate, nel corso dello stesso controllo, piu\' linee attivita\' occorre inserire piu\' controlli (uno per ogni linea attivita\' sottoposta a controllo)"'
						.toUpperCase());
			}
		}

	}

	if (formTest == true) {

		if (document.getElementById('tipooperatorecanipadronali') == null) {
			setTimestampStartRichiesta();

			form.submit();
			return true;

		} else {

			if (flagDecesso == true) {
				aler("Attenzione stai inserendo un controllo su un animale deceduto. Continuare )");

				setTimestampStartRichiesta();
				form.submit();
				return true;

			}

		}
		// win.document.location=link;

	}
	loadModalWindowUnlock();
	return false;
}

function checkFormAcque2(form) {
	isSupervisione = false;
	formTest = true;
	// Perch� ci sta questo controllo per la modifica?

	if (form.orgId != null)
		controlloEsistenzaCu(form.assignedDate.value, form.orgId.value);

	if (form.idStabilimento != null)
		controlloEsistenzaCuOpu(form.assignedDate.value,
				form.idStabilimento.value);

	entratoinpiano = false;
	message = "";

	if (form.tipoCampione.value == '4' || form.tipoCampione.value == '3') {
		tipiispezione = document.getElementsByName('tipoIspezione');
		if (tipiispezione != null)

			for (i = 0; i < tipiispezione.length; i++) {
				getCodiceInternoTipoIspezione(tipiispezione[i].value);

				if (codiceInternoTipoIspezione == '2a'
						&& entratoinpiano == false) {
					entratoinpiano = true;
					indice = 1;
					while (document.getElementById('uo' + indice) != null) {
						uo = document.getElementById('uo' + indice);
						if (uo.value == '-1') {
							message += label("",
									"- Selezionare la voce per Conto di \r\n");
							formTest = false;
						}
						indice++;
					}
				}

				else

				if (codiceInternoTipoIspezione != '2a') {

					if (document.getElementById('per_condo_di'
							+ tipiispezione[i].value) != null) {
						if (document.getElementById('per_condo_di'
								+ tipiispezione[i].value).value == '-1') {
							message += label("",
									"- Selezionare la voce per Conto di  Nelle Attivita Selezionate \r\n");
							formTest = false;

						}

					}

				}

			} // FINE FOR

		if (document.getElementById('quintali') != null)

		{

			if (document.getElementById('quintali').value != '') {
				if (controlloNumerico(document.getElementById('quintali').value) == false) {
					message += label("",
							"- Controllare di aver inserito un numero \r\n");
					formTest = false;
				}
			}

		}
	} else {

		uo = document.getElementsByName("uo_controllo");
		if (uo != null) {
			for (i = 0; i < uo.length; i++) {

				if (uo.value == '-1') {
					message += label("",
							"- Selezionare la voce per Conto di \r\n");
					formTest = false;
				}

			}
		}
	}

	if (form.tipoCampione.value == '7') {
		if (document.getElementById('oggetto_audit').value == -1) {
			message += label("",
					"- Selezionare la voce Oggetto dell'Audit \r\n");
			formTest = false;
		}
	}

	if (form.struttura_asl != null) {
		sel = true;
		array = form.struttura_asl;
		for (i = 0; i < struttura_asl.length; i++) {
			if (array[i].value == '-1' && array[i].selected == true) {
				sel = false;
				break;
			}
		}

		if (document.getElementById('parte_asl').checked && sel == false) {
			message += label("", "- Selezionare la struttura controllata \r\n");
			formTest = false;
		}

	}

	if (form.quantita != null) {
		if (trim(form.quantita.value) != '') {
			value = new String(form.quantita.value);

			if (isNaN(value.replace(",", "."))) {
				message += label("",
						"- Valore di Quantita Partita non Valido. Inserire Cifre\r\n");
				formTest = false;
			} else {
				form.quantita.value = trim(value.replace(",", "."));
			}

		} else {

			form.quantita.value = "0";
		}
	}

	if (form.quantitaBloccata != null) {
		if (trim(form.quantitaBloccata.value) != '') {
			value = new String(form.quantitaBloccata.value);

			if (isNaN(value.replace(",", "."))) {
				message += label("",
						"- Valore di Quantita Partita Bloccata non Valido. Inserire Cifre\r\n");
				formTest = false;
			} else {
				form.quantitaBloccata.value = trim(value.replace(",", "."));
			}

		} else {

			form.quantitaBloccata.value = "0";
		}
	}

	if (form.peso != null)
		if (isNaN(form.peso.value.replace(",", "."))) {
			message += label("", "- Peso non Valido. Inserire Cifre\r\n");
			formTest = false;
		}

	if (form.struttura_asl != null) {
		sel = true;
		array = form.struttura_asl;
		for (i = 0; i < struttura_asl.length; i++) {
			if (array[i].value == '-1' && array[i].selected == true) {
				sel = false;
				break;
			}
		}

		if (document.getElementById('parte_asl').checked && sel == false) {
			message += label("", "- Selezionare la struttura controllata \r\n");
			formTest = false;
		}

	}

	if (form.quantita != null) {
		if (trim(form.quantita.value) != '') {
			value = new String(form.quantita.value);

			if (isNaN(value.replace(",", "."))) {
				message += label("",
						"- Valore di Quantita Partita non Valido. Inserire Cifre\r\n");
				formTest = false;
			} else {
				form.quantita.value = trim(value.replace(",", "."));
			}

		}
	}

	if (form.quantitaBloccata != null) {
		if (trim(form.quantitaBloccata.value) != '') {
			value = new String(form.quantitaBloccata.value);

			if (isNaN(value.replace(",", "."))) {
				message += label("",
						"- Valore di Quantita Partita Bloccata non Valido. Inserire Cifre\r\n");
				formTest = false;
			} else {
				form.quantitaBloccata.value = trim(value.replace(",", "."));
			}

		}
	}

	if (form.peso != null) {
		form.peso.value.replace(",", ".");
		if (isNaN(form.peso.value.replace(",", "."))) {
			message += label("", "- Peso non Valido. Inserire Cifre\r\n");
			formTest = false;
		}
	}

	if (form.tipoCampione.value != '5') {

		if (form.tipoCampione.value == '4') {

			/*
			 * if(form.id_linea_sottoposta_a_controllo!=null &&
			 * form.id_linea_sottoposta_a_controllo.value =='-1') { message +=
			 * label("","- Controllare di aver Selezionato almeno una linea di
			 * attivita.\r\n"); formTest = false; }
			 */

			if (form.id_linea_sottoposta_a_controllo
					&& form.id_linea_sottoposta_a_controllo.value == '-1') {
				message += label("",
						"- Controllare di aver Selezionato almeno una linea di attivita.\r\n");
				formTest = false;
			}

			if (form.codici_selezionabili && form.codici_selezionabili != null) {
				selected = false;

				array = form.codici_selezionabili;
				for (i = 0; i < array.length; i++) {
					if (array[i].value != '') {
						selected = true;
					}

				}
			}

			if (form.codici_selezionabili && form.codici_selezionabili != null
					&& selected == false) {
				message += label("",
						"- Controllare di aver Selezionato almeno una linea di attivita'.\r\n");
				formTest = false;
			}

			/*
			 * if(form.codici_selezionabili!=null &&
			 * form.codici_selezionabili.value =='') { message += label("","-
			 * Controllare di aver Selezionato almeno una linea di
			 * attivita.\r\n"); formTest = false; }
			 */

		} else {

			// if(form.id_linea_sottoposta_a_controllo!=null )
			if (form.id_linea_sottoposta_a_controllo) {
				option = form.id_linea_sottoposta_a_controllo;
				selected = false;
				for (i = 0; i < option.length; i++) {
					if (option[i].selected == true && option[i].value != '-1') {
						selected = true;
					}

				}
				if (selected == false) {

					message += label("",
							"- Controllare di aver Selezionato almeno una linea di attivita.\r\n");
					formTest = false;
				}
			}
			selected = false;

			// if(form.codici_selezionabili!= null )
			if (form.codici_selezionabili) {
				array = form.codici_selezionabili;
				for (i = 0; i < array.length; i++) {
					if (array[i].value != '') {
						selected = true;
					}

				}

				if (selected == false) {

					message += label("",
							"- Controllare di aver Selezionato almeno una linea di attivita.\r\n");
					formTest = false;
				}
			}

		}

	}

	if (form.tipoCampione.value != '2') {
		if (form.nucleo_ispettivo_1.value == '-1') {
			message += label("",
					"- Controllare di aver Selezionato almeno un nucleo Ispettivo.\r\n");
			formTest = false;
		}

		for (i = 1; i <= 10; i++) {
			if (document.getElementById('nucleo_ispettivo_' + i) != null) {
				if (document.getElementById('nucleo_ispettivo_' + i).value != '-1') {
					value = document.getElementById('nucleo_ispettivo_' + i).value;

					if (document.getElementById('risorse_' + i).value == '-1'
							&& document.getElementById('Utente_' + i).value == '') {

						message += label("",
								"- Controllare di aver Selezionato un Componente Per il Nucleo Ispettivo.\r\n");
						formTest = false;
					}

				}

			}
		}
	}

	selected = false;
	if (form.alertText) {

		array = form.alertText;
		for (i = 0; i < array.length; i++) {
			if (array[i].value != '') {
				selected = true;
			}

		}

		// if (selected == false ){
		// if (form.tipoIspezione.value != "3" && form.tipoCampione.value!="5")
		// {
		// message += label("","- Controllare di aver inserito una linea di
		// attivita.\r\n");
		// formTest = false;
		// }
		// }
	}

	/*
	 * if(!form.assignedDate.value == "" && !form.dataFineControllo.value ==
	 * ""){
	 * 
	 * if (controlloDataFine(form.assignedDate.value,
	 * form.dataFineControllo.value)== true ){
	 * 
	 * message += label("","- La Data di Inizio Controllo non puo\' essere
	 * successiva a quella di Fine Controllo.\r\n"); formTest = false; } }
	 */

	isAllarmeRapido = false;
	if (form.contributi != null) {
		var numero = form.contributi.value;

		var arr_num = numero.split(".");

		if (numero.indexOf(",") != (-1)) {

			message += label(
					"check.vigilanza.richiedente.selezionato",
					"- Controllare che il sepratore delle cifre decimali per i contributi sia il punto anzich� la virgola \r\n");
			formTest = false;
		}
	}

	if (form.tipoCampione.value != "-1") {
		if (form.tipoCampione.value == "7") { // se
																				// ho
																				// selezionato
																				// audit
			if (form.auditTipo != null) {
				if (form.auditTipo.value == "1") {
					isbpi = false;
					ishaccp = false;
					isSelTipoAudit = false;

					if (form.tipoAudit != null) {
						isSelTipoAudit = true;
					}

					if (isSelTipoAudit == false) {
						message += label(
								"check.vigilanza.richiedente.selezionato",
								"- Controllare che il campo \"tipo Audit\" sia stato popolato\r\n");
						formTest = false;
					}
				}
			} else {
				message += label("check.vigilanza.richiedente.selezionato",
						"- Controllare che il campo \"tipo Audit\" sia stato popolato\r\n");
				formTest = false;

			}

		} else if (form.tipoCampione.value == "22") {
			isSupervisione = true;
		}
		if (isSupervisione == true) {
			if (document.getElementById("fileAllegareSupervisione") != null
					&& (document.getElementById("fileAllegareSupervisione").value == "-1" || document
							.getElementById("fileAllegareSupervisione").value == "")) {
				formTest = false;
				message += label("",
						"- Controllare di aver selezionato un \"Verbale\" \r\n");

			}
			if (form.isAllegatoSupervisione.value == "false"
					|| form.isAllegatoSupervisione.value == "") {
				formTest = false;
				message += label(
						"",
						"- Controllare di aver allegato il file cliccando sul pulsante  \"Allega\" \r\n");

			}

		}

		else {

			if (form.tipoCampione.value == "3" || form.tipoCampione.value == "4") {// se ho selezionato
													// ispezione o audit

				isSelTipoIspezione = false;
				isPianoMonitoraggio = false;
				isSospetto = false;
				if (form.tipoIspezione != null) {

					for (i = 0; i < document.getElementsByName('tipoIspezione').length; i++) {
						if (document.getElementsByName('tipoIspezione')[i].value != '-1') {
							isSelTipoIspezione = true;

							getCodiceInternoTipoIspezione(document
									.getElementsByName('tipoIspezione')[i].value);
							// codiceInternoTipoIspezione =
							// $("#"+document.getElementsByName('tipoIspezione')[i].id).attr("codiceinterno")
							if (codiceInternoTipoIspezione == '2a') {
								isPianoMonitoraggio = true;
							}
							if (codiceInternoTipoIspezione == '7a') {
								isAllarmeRapido = true;
							}
							if (codiceInternoTipoIspezione == '9a') {
								isSospetto = true;
							}
						}

					}
				} else {

					if (form.context == null)
						isSelTipoIspezione = true;

				}

				if (isSospetto) {
					if (form.tipoSosp.value == "-1") {
						formTest = false;
						message += label("",
								"- Controllare  di aver selezionato un  \"Tipo di sospetto\" \r\n");
					}

					if (form.idBuffer.value == "" && form.tipoSosp.value == "1") {
						formTest = false;
						message += label("",
								"- Controllare  di aver selezionato un \"Buffer\" \r\n");
					}
				}

				if (isPianoMonitoraggio == true) // se ho selezionato tipo
													// ispezione in monitoraggio
				{
					if ((form.piano_monitoraggio1 == null)
							|| (form.piano_monitoraggio1 != null && form.piano_monitoraggio1.value == "-1")) {// se
																												// non
																												// ho
																												// selezionato
																												// nessun
																												// piano
																												// di
																												// monitoraggio

						message += label(
								"check.vigilanza.richiedente.selezionato",
								"- Controllare che il \"Piano di monitoraggio\" sia stato selezionato\r\n");
						formTest = false;
					}

					i = 1;
					while (document.getElementById('piano_monitoraggio' + i) != null) {

						getCodiceInternoTipoPiano(document
								.getElementById('piano_monitoraggio' + i).value);

						if (codiceInternoTipoIspezione == '982' || codiceInternoTipoIspezione == '983') {
							if (document.getElementById('flag_preavviso') != null
									&& document
											.getElementById('flag_preavviso').value != 'N'
									&& document
											.getElementById('flag_preavviso').value != '-1'
									&& (document
											.getElementById('data_preavviso_ba') != null && document
											.getElementById('data_preavviso_ba').value == '')) {
								message += label(
										"check.vigilanza.richiedente.selezionato",
										"- Controllare che la \"Data Preavviso\" sia stata inserita\r\n");
								formTest = false;
								break;
							}

							if (document.getElementById('flag_preavviso') != null
									&& document
											.getElementById('flag_preavviso').value == '-1') {
								message += label(
										"check.vigilanza.richiedente.selezionato",
										"- Controllare che il Campo \"Preavviso\" sia stato selezionato\r\n");
								formTest = false;
								break;
							}

							var pianovalore = document
									.getElementById('piano_monitoraggio' + i).value;

							if (flagCondizionalitaReturn == 'true') {
								var optcondiz = document
										.getElementById("condizionalita");
								for (jj = 0; jj < optcondiz.length; jj++) {
									if (optcondiz[jj].selected == true
											&& optcondiz[jj].value == '-1') {
										message += label("",
												"- Selezionare la voce Condizionalita \r\n");
										formTest = false;

									}
								}
							}

						}
						i++;

					}
				}

				
				if (form.ispezione.value == "-1" || form.ispezione.value == "") {// se
																					// non
																					// ho
																					// selezionato
																					// nessun
																					// piano
																					// di
																					// monitoraggio

					message += label("check.vigilanza.richiedente.selezionato",
							"- Controllare che il campo \"Oggetto del controllo\" sia stato popolato\r\n");
					formTest = false;

				}

				value_isp = document.getElementById("ispezione");
				for (i = 0; i < value_isp.length; i++) {
					if (value_isp[i].selected) {
						if (value_isp[i].value == "69"
								|| value_isp[i].value == "70"
								|| value_isp[i].value == "71"
								|| value_isp[i].value == "72"
								|| value_isp[i].value == "73"
								|| value_isp[i].value == "74"
								|| value_isp[i].value == "75"
								|| value_isp[i].value == "76") {

							if (document.getElementById("animalitrasp").value == "") {
								message += label(
										"check.vigilanza.richiedente.selezionato",
										"- Controllare che il campo \"Specie Animali Trasportati\" sia stato popolato\r\n");
								formTest = false;
							}

						}
					}
				}

				/* Controllare il numero relativo alla specie */
				specie = document.getElementById('animalitrasp');
				if (specie != null)
					for (i = 0; i < specie.length; i++) {
						if (!specie[i].selected) {
							if (specie[i].value != -1) {
								var elem = document.getElementById('num_specie'
										+ specie[i].value);
								if (elem != null)
									elem.value = -1;
							}

							// = '-1';
						} else {

							if (document.getElementById('num_specie'
									+ specie[i].value) != null) {

								if (document.getElementById('num_specie'
										+ specie[i].value).value == ""
										|| document.getElementById('num_specie'
												+ specie[i].value).value == "-1") {
									message += label(
											"check.vigilanza.richiedente.selezionato",
											"- Controllare che il campo \"Numero\" relativo alla specie selezionata sia popolato\r\n");
									formTest = false;
								}
								else if(parseInt(document.getElementById('num_specie'+ specie[i].value).value)<=0)
								{
									message += label(
										"check.vigilanza.richiedente.selezionato",
										"- Controllare che il campo \"Numero\" relativo alla specie selezionata sia un numero positivo\r\n");
									formTest = false;
								}

							}

						}
					}

				messaggio = false;

				if (form.contributi_rilascio_certificazione != null
						&& form.contributi_rilascio_certificazione.value != ''
						&& isNaN(form.contributi_rilascio_certificazione.value
								.replace(",", "."))) {
					message += label("",
							"- Valore di Contributi per Rilascio Cert. non Valido. Inserire Cifre\r\n");
					formTest = false;
				}

				if (form.contributi_risol_nc != null
						&& form.contributi_risol_nc.value != ''
						&& isNaN(form.contributi_risol_nc.value.replace(",",
								"."))) {
					message += label(
							"",
							"- Valore di Contributi per Risoluzione non Conf.. non Valido. Inserire Cifre\r\n");
					formTest = false;
				}

				if (form.contributi != null && form.contributi.value != ''
						&& isNaN(form.contributi.value.replace(",", "."))) {
					message += label("",
							"- Valore di Contributi non Valido. Inserire Cifre\r\n");
					formTest = false;
				}

				if (form.contributi_macellazione_urgenza != null
						&& form.contributi_macellazione_urgenza.value != ''
						&& isNaN(form.contributi_macellazione_urgenza.value
								.replace(",", "."))) {
					message += label(
							"",
							"- Valore di Contributi per Macellazione Urgenza non Valido. Inserire Cifre\r\n");
					formTest = false;
				}

				if (isAllarmeRapido == true) {

					if (form.idAllerta.value == "") {
						formTest = false;
						message += label(
								"check.vigilanza.data_richiesta.selezionato",
								"- Controllare  di aver selezionato una  \"Allerta \" \r\n");

					}

					if (form.procedureRitiro != null
							&& form.procedureRitiro.value == "-1") {
						formTest = false;
						message += label(
								"check.vigilanza.data_richiesta.selezionato",
								"- Controllare  di aver selezionato una proceduta di ritiro \r\n");

					}

					if ((form.procedureRichiamo != null && form.procedureRichiamo.value == "1")
							&& (form.motivoRichiamo != null && form.motivoRichiamo.value == '')) {
						formTest = false;
						message += label(
								"check.vigilanza.data_richiesta.selezionato",
								"- Controllare  di aver selezionato un motivo per le Procedure di richiamo non attivate \r\n");

					}

					if (isNaN(form.contributi_allarme_rapido.value.replace(",",
							"."))) {
						message += label("",
								"- Valore di Contributi per Allarme Rapido non Valido. Inserire Cifre\r\n");
						formTest = false;
					}

					if (form.esitoControllo.value == '13'
							|| form.esitoControllo.value == '14') {
						if (form.destinazioneDistribuzione.value == -1) {
							formTest = false;
							message += label(
									"check.vigilanza.data_richiesta.selezionato",
									"- Controllare che i campi relativi a \"Destinazione Distribuzione partita\" siano stati popolati\r\n");

						}

						/*
						 * if(document.getElementById("fileAllegare")!= null &&
						 * (document.getElementById("fileAllegare").value ==
						 * "-1"
						 * ||document.getElementById("fileAllegare").value=="" )) {
						 * formTest = false; message +=
						 * label("check.vigilanza.data_richiesta.selezionato","-
						 * Controllare di aver selezionato una \"Lista di
						 * distribuzione\" \r\n");
						 *  }
						 */

						if (document.getElementById("isAllegato") != null
								&& (document.getElementById("isAllegato").value == "false" || document
										.getElementById("isAllegato").value == "")) {
							formTest = false;
							message += label(
									"check.vigilanza.data_richiesta.selezionato",
									"- Controllare di aver allegato il file cliccando sul pulsante  \"Allega\" \r\n");

						}

					}

					if (form.esitoControllo.value == '-1') {
						formTest = false;
						message += label(
								"check.vigilanza.data_richiesta.selezionato",
								"- Controllare di aver Selezionato un   \"Esito Controllo\" \r\n");

					} else {
						if (form.esitoControllo.value == '7') {
							if (form.dataddt.value == "") {
								formTest = false;
								message += label(
										"check.vigilanza.data_richiesta.selezionato",
										"- Controllare  di aver inserito \"La Data Per DDT \" \r\n");

							}

							if (form.numDdt.value == "") {
								formTest = false;
								message += label(
										"check.vigilanza.data_richiesta.selezionato",
										"- Controllare di aver inserito \"Numero DDT \" \r\n");

							}

						} else {

							if (form.esitoControllo.value == '8') {

								if (form.quantita.value == "") {
									formTest = false;
									message += label(
											"check.vigilanza.data_richiesta.selezionato",
											"- Controllare  di aver inserito La \"Quantita \" \r\n");

								}

							} else {
								if (form.esitoControllo.value == '10'
										|| form.esitoControllo.value == '11') {
									if (form.quantitaBloccata.value == "") {
										formTest = false;
										message += label(
												"check.vigilanza.data_richiesta.selezionato",
												"- Controllare di aver inserito La \"Quantita Bloccata \" \r\n");

									}

								}
							}

						}

					}

					value = form.azioniAdottate;
					settatoAzione1 = false;
					settatoAzione2 = false;
					if (value != null)
						for (i = 0; i < value.length; i++) {
							if (value[i].selected == true) {

								if ((value[i].value == "-1")
										|| (value[i].value == "")) {
									formTest = false;
									message += label(
											"check.vigilanza.data_richiesta.selezionato",
											"- Controllare  di non aver selezionato la voce \"Seleziona Voce \" da Azioni Adottate \r\n");

								} else {
									if (value[i].value == "3") {
										// if(form.articoliAzioni.value=="-1")
										// {
										// formTest = false;
										// message +=
										// label("check.vigilanza.data_richiesta.selezionato","-
										// Controllare di aver Selezionato un
										// \"Articolo \" \r\n");
										//
										//
										// }
										// else
										// {
										messaggio = true;
										// }
									}
								}
							}

						}

				}

			} else {

				if (form.tipoCampione.value == "2") {// se ho selezionato
														// ispezione

					
					if (form.ispezione.value == "-1"
							|| form.ispezione.value == "") {// se non ho
															// selezionato
															// nessun piano di
															// monitoraggio

						message += label(
								"check.vigilanza.richiedente.selezionato",
								"- Controllare che il campo \"Oggetto del controllo\" sia stato popolato\r\n");
						formTest = false;

					}
				}

				// if(form.tipoCampione.value=="5"){
				// if(form.ispezione.value=="-1" || form.ispezione.value=="")
				// {//se non ho selezionato nessun piano di monitoraggio
				//
				// message += label("check.vigilanza.richiedente.selezionato","-
				// Controllare che il campo \"Oggetto del controllo\" sia stato
				// popolato\r\n");
				// formTest = false;
				//
				// }
				// }
			}

		}
	}

	var dataInizioControllo = form.assignedDate.value;
	var flagDecesso = false;
	var flagDataDecessoPrecedenteDataControllo = false;

	var dateParts = dataInizioControllo.split("/");

	var datecu = new Date(dateParts[2], (dateParts[1] - 1), dateParts[0]);

	if (document.getElementById("size_p") != null) {
		numeroElementi = document.getElementById("size_p").value;

		for (i = 1; i <= numeroElementi; i++) {

if (document.getElementById("mc_" + i)!=null)
				{
			if (document.getElementById("mc_" + i).value == ""
					&& document.getElementById("razza_" + i).value == ""
					&& document.getElementById("sesso_" + i).value == ""
					&& document.getElementById("mantello_" + i).value == ""
					&& document.getElementById("taglia_" + i).value == ""
					&& document.getElementById("data_nascita_cane_" + i).value == "" && ((document.getElementById("canicontrollatitr")!=null && document.getElementById("canicontrollatitr").style.display!='none') || (document.getElementById("canicontrollatitrCaniPadronali")!=null && document.getElementById("canicontrollatitrCaniPadronali").style.display!='none')))
			{
				message += label("check.vigilanza.richiedente.selezionato",
						"- Controllare di aver compilato qualche informazione sul cane controllato\r\n");
				formTest = false;
				break;
			}

			var dataDecesso = '';
			if (document.getElementById("data_decesso_" + i) != null)
				dataDecesso = document.getElementById("data_decesso_" + i).value;

			if (dataDecesso != ''
					&& document.getElementById("data_decesso_" + i) != null) {
				flagDecesso = true;

				var dateParts2 = dataDecesso.split("/");

				var dateDec = new Date(dateParts2[2], (dateParts2[1] - 1),
						dateParts2[0]);

				if (dateDec < datecu) {
					flagDataDecessoPrecedenteDataControllo = true;

					break;
				}
	}
			}

		}

		if (flagDataDecessoPrecedenteDataControllo == true) {
			message += label(
					"check.vigilanza.richiedente.selezionato",
					"- Attenzione la data di decesso ("
							+ dataDecesso
							+ ") dell'animale è antecedente alla data di inizio controllo \r\n");
			formTest = false;
		}

	}

	if (form.nome_conducente != null && form.nome_conducente.value == "") {
		message += label("check.vigilanza.richiedente.selezionato",
				"- Controllare che il campo \"Nominativo Conduttore\" sia stato popolato\r\n");
		formTest = false;
	}

	if (form.nominativo_proprietario != null
			&& form.nominativo_proprietario.value == "") {
		message += label("check.vigilanza.richiedente.selezionato",
				"- Controllare che il campo \"Nominativo Proprietario\" sia stato popolato\r\n");
		formTest = false;
	}

	if (form.cf_proprietario != null && form.cf_proprietario.value == "") {
		message += label("check.vigilanza.richiedente.selezionato",
				"- Controllare che il campo \"CF Proprietario\" sia stato popolato\r\n");
		formTest = false;
	}

	if (form.siteId.value == "-1") {
		message += label("check.vigilanza.richiedente.selezionato",
				"- Controllare che il campo \"A.S.L.\" sia stato popolato\r\n");
		formTest = false;
	}

	if ((form.orgId != null && form.orgId.value == "-1") && (form.idStabilimentoopu != null && form.idStabilimentoopu.value == "-1")) {
		message += label("check.vigilanza.richiedente.selezionato",
				"- Controllare che \"Impresa\" sia stato selezionato\r\n");
		formTest = false;
	}

	// Controllo per il campo categoria trasportata
	if (document.getElementById("categoriaTrasportata") != null) {
		if (document.getElementById("categoriaTrasportata").value == -1) {
			message += label("check.vigilanza.richiedente.selezionato",
					"- Controllare che \"Categoria Trasportata\" sia stato selezionato\r\n");
			formTest = false;
		}
	}

	if (form.tipoCampione.value == "-1") {
		message += label("check.campioni.richiedente.selezionato",
				"- Controllare che \"Tipo di Controllo\" sia stato popolato\r\n");
		formTest = false;
	}

	if (form.assignedDate.value == "") {
		message += label("check.vigilanza.data_richiesta.selezionato",
				"- Controllare che il campo \"Data Inizio Controllo\" sia stato popolato\r\n");
		formTest = false;
	} else {
		flag = controllo_data(form.assignedDate.value)

		if (flag == false) {
			formTest = false;
			message += label(
					"check.vigilanza.data_richiesta.selezionato",
					"- Controllare che il campo \"Data Inizio Controllo\" sia stato popolato Correttamente\r\n");

		} else {

		}

	}

	if (document.getElementById("flag_preavviso") != null
			&& document.getElementById("flag_preavviso").value != '-1'
			&& document.getElementById("flag_preavviso").value != 'N'
			&& document.getElementById("data_preavviso_ba").value == '') {
		message += label("check.vigilanza.richiedente.selezionato",
				"- Controllare che il Campo \"Data Preavviso\" sia stato selezionato\r\n");
		formTest = false;
	}

	/*
	 * if(controlloData(form.assignedDate.value)==false){ formTest = false;
	 * message += label("check.vigilanza.data_richiesta.selezionato","-
	 * Controllare che il campo \"Data Inizio Controllo\" sia stato Precedente o
	 * uguale alla data attuale\r\n"); }
	 */
	messaggio = false;

	if (form.tipoIspezione != null && form.tipoIspezione.value == "7a") {

		if (form.idAllerta.value == "") {
			formTest = false;
			message += label("check.vigilanza.data_richiesta.selezionato",
					"- Controllare  di aver selezionato una  \"Allerta \" \r\n");

		}

		if (form.procedureRitiro != null && form.procedureRitiro.value == "-1") {
			formTest = false;
			message += label("check.vigilanza.data_richiesta.selezionato",
					"- Controllare  di aver selezionato una proceduta di ritiro \r\n");

		}

		if ((form.procedureRichiamo != null && form.procedureRichiamo.value == "1")
				&& (form.motivoRichiamo != null && form.motivoRichiamo.value == '')) {
			formTest = false;
			message += label(
					"check.vigilanza.data_richiesta.selezionato",
					"- Controllare  di aver selezionato un motivo per le Procedure di richiamo non attivate \r\n");

		}

		if (form.esitoControllo.value == 13 || form.esitoControllo.value == 14) {
			if (form.destinazioneDistribuzione.value == -1) {
				formTest = false;
				message += label(
						"check.vigilanza.data_richiesta.selezionato",
						"- Controllare che i campi relativi a \"Destinazione Distribuzione partita\" siano stati popolati\r\n");

			}

			if (form.subject != null && form.subject.value == "") {
				formTest = false;
				message += label(
						"check.vigilanza.data_richiesta.selezionato",
						"- Controllare che il campo \"Oggetto per lista distribuzione\" sia stato popolato\r\n");

			}

			if (document.getElementById("fileAllegare") != null
					&& document.getElementById("fileAllegare").value == "") {
				formTest = false;
				message += label("check.vigilanza.data_richiesta.selezionato",
						"- Controllare di aver selezionato una \"Lista di distribuzione\" \r\n");

			}

			if (document.getElementById("isAllegato") != null
					&& (document.getElementById("isAllegato").value == "false")) {
				formTest = false;
				message += label(
						"check.vigilanza.data_richiesta.selezionato",
						"- Controllare di aver allegato il file cliccando sul pulsante  \"Allega\" \r\n");

			}

		}

		if (form.esitoControllo.value == -1) {
			formTest = false;
			message += label("check.vigilanza.data_richiesta.selezionato",
					"- Controllare di aver Selezionato un   \"Esito Controllo\" \r\n");

		} else {
			if (form.esitoControllo.value == 7) {
				if (form.dataddt.value == "") {
					formTest = false;
					message += label(
							"check.vigilanza.data_richiesta.selezionato",
							"- Controllare  di aver inserito \"La Data Per DDT \" \r\n");

				}

				if (form.numDdt.value == "") {
					formTest = false;
					message += label(
							"check.vigilanza.data_richiesta.selezionato",
							"- Controllare di aver inserito \"Numero DDT \" \r\n");

				}

			} else {

				if (form.esitoControllo.value == 8) {

					if (form.quantita.value == "") {
						formTest = false;
						message += label(
								"check.vigilanza.data_richiesta.selezionato",
								"- Controllare  di aver inserito La \"Quantita \" \r\n");

					}

				} else {
					if (form.esitoControllo.value == 10
							|| form.esitoControllo.value == 11) {
						if (form.quantitaBloccata.value == "") {
							formTest = false;
							message += label(
									"check.vigilanza.data_richiesta.selezionato",
									"- Controllare di aver inserito La \"Quantita Bloccata \" \r\n");

						}

					}
				}

			}

		}

		value = form.azioniAdottate;
		settatoAzione1 = false;
		settatoAzione2 = false;
		for (i = 0; i < value.length; i++) {
			if (value[i].selected == true) {

				if ((value[i].value == "-1") || (value[i].value == "")) {
					formTest = false;
					message += label(
							"check.vigilanza.data_richiesta.selezionato",
							"- Controllare  di non aver selezionato la voce \"Seleziona Voce \" da Azioni Adottate \r\n");

				} else {
					if (value[i].value == "3") {
						// if(form.articoliAzioni.value=="-1")
						// {
						// formTest = false;
						// message +=
						// label("check.vigilanza.data_richiesta.selezionato","-
						// Controllare di aver Selezionato un \"Articolo \"
						// \r\n");
						//
						//
						// }
						// else
						// {
						messaggio = true;
						// }
					}
				}
			}

		}

	}

	if (form.closeNow) {
		if (form.closeNow.checked && form.solution.value == "") {
			message += label("check.ticket.resolution.atclose",
					"- Resolution needs to be filled in when closing a ticket\r\n");
			formTest = false;
		}
	}

	if (formTest) {
		if (typeof controllaDatiEstesi == 'function') {
			formTest = controllaDatiEstesi();
			if (!formTest)
				message += 'Dati estesi non compilati';
		}
	}

	if (form.dim != null && form.dim.value != 0) {
		alert("ATTENZIONE! Si ricorda che sara\' inserito un controllo per ogni operatore associato\r\n ");
	}

	if (formTest == false) {
		loadModalWindowUnlock();
		alert(label("check.form",
				"Form could not be saved, please check the following:\r\n\r\n")
				+ message);
		return false;
	} else {

		if (flagEsisteCu == true)
			formTest = confirm('Attenzione per la corrente impresa esiste gi� un controllo inserito in data '
					+ dataCu);
		dataCu = '';

		if (formTest == true && isAllarmeRapido == true) {

			showMessaggioAllarme(form);

		}

		if (formTest == true && form != null && form.tipoCampione.value == 4) {
			if (form.id_linea_sottoposta_a_controllo != null) {

				alert('"ATTENZIONE! Qualora siano state controllate, nel corso dello stesso controllo, piu\' linee attivita\' occorre inserire piu\' controlli (uno per ogni linea attivita\' sottoposta a controllo)"'
						.toUpperCase());
			}
		}

	}

	if (formTest == true) {

		if (document.getElementById('tipooperatorecanipadronali') == null) {
			setTimestampStartRichiesta();

			form.submit();
			return true;

		} else {

			if (flagDecesso == true) {
				aler("Attenzione stai inserendo un controllo su un animale deceduto. Continuare )");

				setTimestampStartRichiesta();
				form.submit();
				return true;

			}

		}
		// win.document.location=link;

	}
	loadModalWindowUnlock();
	return false;
}

function resetNucleoIspettivo() {

	i = 2;
	while (document.getElementById('nucleoispettivo_' + i) != null) {
		document.getElementById('nucleoispettivo_' + i).parentNode
				.removeChild(document.getElementById('nucleoispettivo_' + i));
		i++;
	}

	document.getElementById('nucleo_ispettivo_1').value = '-1';
	fieldSelect = document.getElementById('risorse_1');
	removeOptions(fieldSelect);
	fieldSelect.style.display = "none";
	document.getElementById('elementi').value = 1;

}

function popLookupSelectorCuSoaAllevaElimina(siteid, size) {

	var clonato = document.getElementById('row' + '_' + size);

	clonato.parentNode.removeChild(clonato);

	size = document.getElementById('size');
	size.value = parseInt(size.value) - 1;
}

function piani(tipo) {

	if (tipo == '1') {

		document.getElementById("link_regionali").disabled = true;
		document.getElementById("link_territoriali").disabled = true;

	} else if (tipo == '2') {
		document.getElementById("link_nazionali").disabled = true;
		document.getElementById("link_territoriali").disabled = true;
	} else if (tipo == '3') {
		document.getElementById("link_regionali").disabled = true;
		document.getElementById("link_nazionali").disabled = true;
	}

}

function setAssignedDate(form) {
	resetAssignedDate();
	if (document.forms[form].assignedTo.value > 0) {
		document.forms[form].assignedDate.value = document.forms[form].currentDate.value;
	}
}

function resetAssignedDate(form) {
	document.forms[form].assignedDate.value = '';
}

function controlloNumerico(value) {
	if (isNaN(value)) {
		return false;
	}
	return true;

}

function removeParam(key, sourceURL) {
    var rtn = sourceURL.split("?")[0],
        param,
        params_arr = [],
        queryString = (sourceURL.indexOf("?") !== -1) ? sourceURL.split("?")[1] : "";
    if (queryString !== "") {
        params_arr = queryString.split("&");
        for (var i = params_arr.length - 1; i >= 0; i -= 1) {
            param = params_arr[i].split("=")[0];
            if (param === key) {
                params_arr.splice(i, 1);
            }
        }
        rtn = rtn + "?" + params_arr.join("&");
    }
    return rtn;
}

function reloadAddCU(val){
	
//	var url = window.location.href;    
//	url = removeParam("tipoCampione", url);
//	
//	
//	if (url.indexOf('?') > -1){
//	   url += '&tipoCampione='+val;
//	}else{
//	   url += '?tipoCampione='+val;
//	}
//	loadModalWindow();
//	window.location.href = url;
	loadModalWindow();
	window.location.href = val;
	
}

function provaFunzione(form) {
	
	var queryString = window.location.search;
	var urlParams = new URLSearchParams(queryString);
	var tipoCampione = urlParams.get('tipoCampione');
	if (tipoCampione!=null && tipoCampione!="")
		document.forms[form].tipoCampione.value = tipoCampione;
	
	var valore = document.forms[form].tipoCampione.value;

	document.getElementById('tr_select_motivo').style.display = "none";
	tipiispezione = document.getElementsByName('tipoIspezioneDialog');
	piani = document.getElementsByName('pianodialog');

	for (i = 0; i < piani.length; i++) {
		if (piani[i].checked) {

			piani[i].checked = false;

		}

	}

	for (i = 0; i < tipiispezione.length; i++) {
		if (document.getElementById('tipoIspezione' + tipiispezione[i].value) != null
				&& tipiispezione[i].checked) {

			tipiispezione[i].checked = false;
			document.getElementById('containerTipoIsezioneSelected')
					.removeChild(
							document.getElementById('tipoIspezione'
									+ tipiispezione[i].value));
		}

	}
	
	$("input[name='auditTipoDialog']:checked").each(function(i) {
		$(this).prop('checked', false);
	});
	
	
	settaTipoAudit();

	if (valore != "7")
		if (document.getElementById("auditFollowup") != null)
			document.getElementById("auditFollowup").style.display = "none";

	if (valore == "23" || valore == "7") {
		if (document.getElementById("hiddenSuper") != null)
			document.getElementById("hiddenSuper").style.display = "none";
		if (document.getElementById('molluschiquantitativo') != null)
			document.getElementById('molluschiquantitativo').style.display = "none";

		if (document.getElementById("lab1") != null)
			document.getElementById("lab1").style.display = "none";

		// document.forms[form].ispezione.value="-1";
		// document.forms[form].ispezione.style.display = "none";
		document.getElementById("oggetto_controllo").style.display = "none";
		if (document.getElementById("sorveglianza") != null)
			document.getElementById("sorveglianza").style.display = "none";
		if (valore != "3" && document.getElementById("non_sorveglianza") != null)
			document.getElementById("non_sorveglianza").style.display = "none";

		/*
		 * RIMUOVO I TIPI DI ISPEZIONE SELEZIONATI OGNI VOLTA CHE CAMBIA IL TIPO
		 * DI CONTROLLO
		 */

		mostraMenuTipoIspezione(form);

		document.getElementById('tr_select_motivo').style.display = "";
		document.getElementById('link_select_motivo_href').href = "#";
		document.getElementById('link_select_motivo_href').onclick = function() {
			$('#dialogMotiviAudit').dialog('open');
		};
		document.getElementById('link_select_motivo').innerHTML = "SELEZIONA MOTIVO AUDIT";

	}  else if (valore == "3") { //AUDIT

		if (document.getElementById("hiddenSuper") != null)
			document.getElementById("hiddenSuper").style.display = "none";
		if (document.getElementById('molluschiquantitativo') != null)
			document.getElementById('molluschiquantitativo').style.display = "none";

		if (document.getElementById("lab1") != null)
			document.getElementById("lab1").style.display = "none";

		// document.forms[form].ispezione.value="-1";
		// document.forms[form].ispezione.style.display = "none";
		document.getElementById("oggetto_controllo").style.display = "none";
		if (document.getElementById("sorveglianza") != null)
			document.getElementById("sorveglianza").style.display = "none";
		if (valore != "3" && document.getElementById("non_sorveglianza") != null)
			document.getElementById("non_sorveglianza").style.display = "none";

		/*
		 * RIMUOVO I TIPI DI ISPEZIONE SELEZIONATI OGNI VOLTA CHE CAMBIA IL TIPO
		 * DI CONTROLLO
		 */

		mostraMenuTipoIspezione(form);

		document.getElementById('tr_select_motivo').style.display = "";
		document.getElementById('link_select_motivo_href').href = "#";
		document.getElementById('link_select_motivo_href').onclick = function() {
			$('#dialogMotiviIspezione').dialog('open');
		};
		document.getElementById('link_select_motivo').innerHTML = "SELEZIONA MOTIVO AUDIT";
		
		if (document.getElementById("per_conto_di") != null)
			document.getElementById("per_conto_di").style.display = "none";
	
	} else {

		if (valore == "4") {
			tipiispezione = document.getElementsByName('tipoIspezioneDialog');
			for (i = 0; i < tipiispezione.length; i++) {
				if (document.getElementById('tipoIspezione'
						+ tipiispezione[i].value) != null
						&& tipiispezione[i].checked) {

					tipiispezione[i].checked = false;
					document.getElementById('containerTipoIsezioneSelected')
							.removeChild(
									document.getElementById('tipoIspezione'
											+ tipiispezione[i].value));
				}

			}

			if (document.getElementById("hiddenSuper") != null)
				document.getElementById("hiddenSuper").style.display = "none";
			if (document.getElementById('molluschiquantitativo') != null)
				document.getElementById('molluschiquantitativo').style.display = "";
			document.getElementById("oggetto_controllo").style.display = "";
			// document.forms[form].ispezione.style.display = "";

			// mostraMenu2(form);

			if (document.getElementById("lab1") != null)
				document.getElementById("lab1").style.display = "block";
			if (document.getElementById("sorveglianza") != null)
				document.getElementById("sorveglianza").style.display = "none";
			if (document.getElementById("non_sorveglianza") != null)
				document.getElementById("non_sorveglianza").style.display = "";

			if (document.getElementById("modificabile").value != 'null') {
				document.getElementById('tr_select_motivo').style.display = "";
				document.getElementById('link_select_motivo_href').href = "#";
				document.getElementById('link_select_motivo_href').onclick = function() {
					$('#dialogMotiviIspezione').dialog('open');
				};
				document.getElementById('link_select_motivo').innerHTML = "SELEZIONA MOTIVO ISPEZIONE";
			}
		} else {
			if (valore == "5") {
				tipiispezione = document
						.getElementsByName('tipoIspezioneDialog');
				for (i = 0; i < tipiispezione.length; i++) {
					if (document.getElementById('tipoIspezione'
							+ tipiispezione[i].value) != null
							&& tipiispezione[i].checked) {

						tipiispezione[i].checked = false;
						document
								.getElementById('containerTipoIsezioneSelected')
								.removeChild(
										document.getElementById('tipoIspezione'
												+ tipiispezione[i].value));
					}

				}

				if (document.getElementById("hiddenSuper") != null)
					document.getElementById("hiddenSuper").style.display = "none";
				if (document.getElementById('molluschiquantitativo') != null)
					document.getElementById('molluschiquantitativo').style.display = "none";
				document.getElementById("oggetto_controllo").style.display = "none";
				resetSelectElement(document.getElementById("ispezione"));

				
				
				// document.forms[form].ispezione.style.display = "";

				if (document.forms[form].bpi != null) {
					document.forms[form].bpi.value = "-1";
				}
				if (document.forms[form].haccp != null) {

					document.forms[form].haccp.value = "-1";

				}

				if (document.getElementById("sorveglianza") != null)

					document.getElementById("sorveglianza").style.display = "none";
				if (document.getElementById("non_sorveglianza") != null)

					document.getElementById("non_sorveglianza").style.display = "";

				if (document.getElementById("lab1") != null)
					document.getElementById("lab1").style.display = "none";
				tipiispezione = document
						.getElementsByName('tipoIspezioneDialog');

				for (i = 0; i < tipiispezione.length; i++) {
					if (document.getElementById('tipoIspezione'
							+ tipiispezione[i].value) != null
							&& tipiispezione[i].checked) {

						tipiispezione[i].checked = false;
						document
								.getElementById('containerTipoIsezioneSelected')
								.removeChild(
										document.getElementById('tipoIspezione'
												+ tipiispezione[i].value));
					}

				}
				mostraMenuTipoIspezione(form);
			} else if (valore == '22') { // GESTISCO CANCELLAZIONE DELLE
											// RIGHE SULL'AUDIT IN CASO DI
											// SCELTA SUPERVISIONE
				// isAllarmeRapido = true ;
				document.getElementById("hiddenSuper").style.display = "";

				document.getElementById("oggettoAudit").style.display = "none";
			} else {
				if (valore == '2') { // GESTISCO CANCELLAZIONE DELLE RIGHE
										// SULL'AUDIT IN CASO DI SCELTA
										// SUPERVISIONE
					// isAllarmeRapido = true ;

					document.getElementById("oggetto_controllo").style.display = "";
				} else {
					tipiispezione = document
							.getElementsByName('tipoIspezioneDialog');
					for (i = 0; i < tipiispezione.length; i++) {
						if (document.getElementById('tipoIspezione'
								+ tipiispezione[i].value) != null
								&& tipiispezione[i].checked) {

							tipiispezione[i].checked = false;
							document
									.getElementById(
											'containerTipoIsezioneSelected')
									.removeChild(
											document
													.getElementById('tipoIspezione'
															+ tipiispezione[i].value));
						}

					}

					if (document.getElementById('molluschiquantitativo') != null)
						document.getElementById('molluschiquantitativo').style.display = "none";

					if (document.getElementById("lab1") != null)
						document.getElementById("lab1").style.display = "none";

					// document.forms[form].ispezione.value="-1";
					// document.forms[form].ispezione.style.display = "none";
					document.getElementById("oggetto_controllo").style.display = "none";
					if (document.getElementById("sorveglianza") != null)
						document.getElementById("sorveglianza").style.display = "none";
					if (document.getElementById("non_sorveglianza") != null)
						document.getElementById("non_sorveglianza").style.display = "none";
					tipiispezione = document
							.getElementsByName('tipoIspezioneDialog');

					mostraMenuTipoIspezione(form);
				}
			}

			// mostraMenu2(form);
		}

	}
	$("input[name='tipoIspezioneDialog']:checked").each(function(i) {
		$(this).prop('checked', false);
	});
}

function abilitaDestinazione(form) {
	if (document.forms[form].esitoControllo.value == -1) {
		document.forms[form].destinazioneDistribuzione.style.display = "none";
		document.forms[form].destinazioneDistribuzione.value = "-1";
		// document.getElementById("hidden3").style.display="none";
		// document.forms[form].idFile.value="-1";
		document.getElementById("partita").style.display = "none";

	} else {
		if (document.forms[form].esitoControllo.value == 13
				|| document.forms[form].esitoControllo.value == 14) {
			document.forms[form].destinazioneDistribuzione.style.display = "block";
			// document.getElementById("hidden3").style.display="";
			document.getElementById("partita").style.display = "";
		} else {
			document.forms[form].destinazioneDistribuzione.style.display = "none";
			document.forms[form].destinazioneDistribuzione.value = "-1";
			// document.getElementById("hidden3").style.display="none";
			// document.forms[form].idFile.value="-1";
			document.getElementById("partita").style.display = "none";

		}
	}

}

function abilitaCampoNote(form) {
	element = document.forms[form].comunicazioneRischio1[0];

	if (element.checked && element.value == "0") {
		if (document.forms[form].noteRischio != null)
			document.forms[form].noteRischio.style.visibility = "visible";
	} else {
		element = document.forms[form].comunicazioneRischio1[1];
		if (element.checked && element.value == "1") {
			if (document.forms[form].noteRischio != null)
				document.forms[form].noteRischio.style.visibility = "hidden";
		}
	}
}

function gestisciMotivoRichiamo(val) {
	var divMotivo = document.getElementById("divMotivoRichiamo");
	var motivo = document.getElementById("motivoRichiamo");

	if (val == 0) {
		divMotivo.style.display = "none";
		motivo.value = '';
	} else {
		divMotivo.style.display = "block";
	}
}

function azioneSuAzioniAdottate(form) {
	value = document.forms[form].azioniAdottate;
	settatoAzione1 = false;
	settatoAzione2 = false;
	for (i = 0; i < value.length; i++) {
		if (value[i].selected == true) {

			if (value[i].value == "2" || value[i].value == "1") {

				if (document.forms[form].esitoControllo.value == "10"
						|| document.forms[form].esitoControllo.value == "11"
						|| document.forms[form].esitoControllo.value == "14") {
					document.getElementById("hiddenAzione1").style.display = "";
					document.getElementById("rowAzione1").innerHTML = "Quantita : "
							+ document.forms[form].quantitaBloccata.value
							+ "  " + document.forms[form].unitaMisura.value
				}
				// document.getElementById("hiddenAzione2").style.display="none";
				settatoAzione1 = true;

			} else {
				if (value[i].value == "3") {
					// document.getElementById("hiddenAzione1").style.display="none";
					// document.getElementById("hiddenAzione2").style.display="";
					//
					// settatoAzione2=true;
				}
			}
		}

	}
	if (settatoAzione1 == false && settatoAzione2 == false) {
		document.getElementById("hiddenAzione1").style.display = "none";
		// document.getElementById("hiddenAzione2").style.display="none";
	}
	if (settatoAzione1 == false) {
		document.getElementById("hiddenAzione1").style.display = "none";

	}
	// if(settatoAzione2==false)
	// {
	// document.getElementById("hiddenAzione2").style.display="none";
	//
	// }

}

function azioneSuEsitoControllo(form) {
	value = document.forms[form].esitoControllo.value;

	if (value == "7") {
		document.getElementById("hiddenEsito1").style.display = "";
		document.getElementById("hiddenEsito3").style.display = "none";
		document.getElementById("rowAzione1").innerHTML = "";
		document.getElementById("hiddenAzione1").style.display = "none"
		document.getElementById("hiddenEsito2").style.display = "";
		document.getElementById('misura').innerHTML = ' '
				+ document.forms[form].unitaMisura.value;
		document.getElementById("hiddenAzione1").style.display = "none"

	} else if (value == "4" || value == "5") {
		document.getElementById("hiddenEsito1").style.display = "none";
		document.getElementById("hiddenEsito2").style.display = "none";
		document.getElementById("hiddenEsito3").style.display = "none";
		document.getElementById("rowAzione1").innerHTML = "";
		document.getElementById('misura').innerHTML = ' '
				+ document.forms[form].unitaMisura.value;
		document.getElementById("hiddenAzione1").style.display = "none"

	} else if (value == "6" || value == "8") {
		document.getElementById("hiddenEsito1").style.display = "none";
		document.getElementById("hiddenEsito2").style.display = "";
		document.getElementById("hiddenEsito3").style.display = "none";
		document.getElementById("rowAzione1").innerHTML = "";
		document.getElementById('misura').innerHTML = ' '
				+ document.forms[form].unitaMisura.value;
		document.getElementById("hiddenAzione1").style.display = "none"

	} else {
		if (value == "10" || value == "11" || value == "14") {
			document.getElementById("hiddenEsito1").style.display = "none";
			document.getElementById("hiddenEsito2").style.display = "none";
			document.getElementById("hiddenEsito3").style.display = "";
			value = document.forms[form].azioniAdottate;
			document.getElementById('misura1').innerHTML = ' '
					+ document.forms[form].unitaMisura.value;
			for (i = 0; i < value.length; i++) {
				if (value[i].selected == true) {

					if (value[i].value == "2") {
						document.getElementById("rowAzione1").innerHTML = "Quantita : "
								+ document.forms[form].quantitaBloccata.value
								+ "  " + document.forms[form].unitaMisura.value

						document.getElementById("hiddenAzione1").style.display = ""
					}
				}
			}

		} else {
			document.getElementById("hiddenEsito1").style.display = "none";
			document.getElementById("hiddenEsito2").style.display = "none";
			document.getElementById("hiddenEsito3").style.display = "none";
			document.getElementById("rowAzione1").innerHTML = "";
			document.getElementById("hiddenAzione1").style.display = "none"

		}

	}

}

function onloadAllerta(form) {

	var valori = document.getElementsByName('tipoIspezione');

	if (document.getElementById('tipoCampione').value == '4' || document.getElementById('tipoCampione').value == '2') {
		document.getElementById('oggetto_controllo').style.display = '';
	} else if (document.getElementById('tipoCampione').value == '3') {
		if (window.location.href.indexOf("command=Add")>=0)
			document.getElementById('oggetto_controllo').style.display = '';
	} else {
		document.getElementById('oggetto_controllo').style.display = 'none';
	}
	isSel = false;
	for (i = 0; i < valori.length; i++) {

		getCodiceInternoTipoIspezione(valori[i].value);

		if (codiceInternoTipoIspezione == '7a') {
			isSel = true;

			document.forms[form].tipoIspezione.style.display = "";
			if (document.getElementById("lab1") != null)
				document.getElementById("lab1").style.display = "";
			if (document.getElementById("hidden1") != null)
				document.getElementById("hidden1").style.display = "";// display="block";
			if (document.getElementById("hidden2") != null)
				document.getElementById("hidden2").style.display = "";
			// if(document.getElementById("hidden3")!=null)
			// document.getElementById("hidden3").style.display="";
			if (document.getElementById("tableHidden") != null) {
				document.getElementById("tableHidden").style.display = "";// display="block";
				document.forms[form].destinazioneDistribuzione.style.background = "#FFFFFF";
				document.forms[form].destinazioneDistribuzione.style.display = "";
				if (document.getElementById("contributi") != null)
					document.getElementById("contributi").style.display = "";// display="block";

				document.getElementById('rowAzione1').innerHTML = 'Quantita : '
						+ document.forms[form].quantitaBloccata.value + '  '
						+ document.forms[form].unitaMisura.value;
				document.getElementById('misura').innerHTML = ' '
						+ document.forms[form].unitaMisura.value;
			}

			break;
		}
	}

}

function mostraMenuTipoIspezione(form) {

	isPianoMonitoraggio = false;
	isPianoAcqueRadio = false;
	isPianoAcqueNoRadio = false;
	isAltro = false;
	isAllarmeRapido = false;
	isRilascioCert = false;
	isNcPrec = false;
	isTossinfezione = false;
	isSospetto = false;
	isMacellazione = false;
	isMacellazioneUrgenza = false;
	isCaniAggressori = false;
	isStatoSanitarioApiari = false;

	isCampione = false;
	isSvincoli = false;
	isImportazioneScambio = false;
	isCondizionalita = false;
	entratonelcontrollopiani = false;
	mostraStrutturaAsl();

	// tipiispezione = $( "input[name='tipoIspezioneDialog']:checked" );

	tipiispezione = document.getElementsByName('tipoIspezioneDialog');

	/* RIMOZIONE DEGLI ELEMENTI SETTATI IN PRECEDENZA */
	for (i = 0; i < tipiispezione.length; i++) {
		var res = tipiispezione[i].value.split(";");

		if (document.getElementById("tipoIspezione" + res[0]) != null){
			document.getElementById('containerTipoIsezioneSelected')
					.removeChild(
							document.getElementById("tipoIspezione" + res[0]));
			 //alert("rimuovo "+res[0]);
			 }
	}

	tipiispezione2 = document.getElementsByName('tipoIspezione');
	// tipiispezione2 = $( "input[name='tipoIspezione']:checked" );

	for (i = 0; i < tipiispezione2.length; i++) {
		var res = tipiispezione2[i].value.split(";");

		if (document.getElementById("tipoIspezione" + res) != null){
			document
					.getElementById('containerTipoIsezioneSelected')
					.removeChild(document.getElementById("tipoIspezione" + res));
			//alert('rimuovo ancora' + res);
		}
	}
	
	
	for (var k=1; document.getElementById("clonepiano" + (k)) != null; k++)
	{
		document.getElementById("clonepiano"+ (k)).parentNode.removeChild(document.getElementById("clonepiano"+ (k)));
		var elem_num_piani = document.getElementById("num_piani");
		elem_num_piani.value = (parseInt(elem_num_piani.value) -1)+'' ;
	}
	
	

	arrayAllarmeRapido = new Array();
	var indiceArrayAllarme = 0;
	for (i = 0; i < tipiispezione.length; i++) {
		var res = tipiispezione[i].value.split(";");

		if (res[0] != '-1') {

			document.getElementById(tipiispezione[i].id).value = res[0];

			if (tipiispezione[i].checked) {
				// getCodiceInternoTipoIspezione(res[0]);

				codiceInternoTipoIspezione = $("#" + tipiispezione[i].id).attr(
						"codiceinterno");
				if (codiceInternoTipoIspezione != '2a') {
					var input = document.createElement("input");
					input.type = "hidden";
					input.name = "tipoIspezione"; // set the CSS class
					input.id = "tipoIspezione" + res[0]; // set the CSS class
					input.value = res[0];
					document.getElementById('containerTipoIsezioneSelected')
							.appendChild(input);

				}
				//					

				if (codiceInternoTipoIspezione == '2a'
						&& entratonelcontrollopiani == false) {

					var input = document.createElement("input");
					input.type = "hidden";
					input.name = "tipoIspezione"; // set the CSS class
					input.id = "tipoIspezione" + res[0]; // set the CSS class
					input.value = res[0];
					document.getElementById('containerTipoIsezioneSelected')
							.appendChild(input);

					entratonelcontrollopiani = true;
					var arrayValue = new Array();
					var arrayDescrizioni = new Array();

					/* Costruzione della lista dei piani */
					piani = document.getElementsByName('pianodialog');
					j = 0;
					for (z = 0; z < piani.length; z++) {
						
						if (piani[z].checked && piani[z].value != '') {

							
							
							if ($("#" + piani[z].id).attr("codiceinterno") == 1425)
								isPianoAcqueNoRadio = true;
							if ($("#" + piani[z].id).attr("codiceinterno") == 1409)
								isPianoAcqueRadio = true;
							
							arrayValue[j] = piani[z].value;
							arrayDescrizioni[j] = $("#" + piani[z].id).attr(
									"piano");
							j++;

						}
					}

					clonaNelPadrePiani(arrayValue, arrayDescrizioni);
					// document.getElementById('row_piano').style.display="";
					isPianoMonitoraggio = true;

				} else if (codiceInternoTipoIspezione == '14a') {
					if (tipiispezione[i].checked == true) {
						isMacellazione = true;
						document.getElementById("macellazione").style.display = "";
					}
				} else if (codiceInternoTipoIspezione == '28a') {
					if (tipiispezione[i].checked == true) {
						isMacellazioneUrgenza = true;
						document.getElementById("macellazione_urgenza").style.display = "";
					}
				}

				else

				if (codiceInternoTipoIspezione == '1a') {
					if (tipiispezione[i].checked == true) {
						isCampione = true;
						if (document.getElementById("seguitodicampionamento") != null)
							document.getElementById("seguitodicampionamento").style.display = "";
					}
				} else if (codiceInternoTipoIspezione == '20a') {
					if (tipiispezione[i].checked == true) {
						isSvincoli = true;
						if (document.getElementById("svincolisanitari") != null)
							document.getElementById("svincolisanitari").style.display = "";
					}
				}

				else

				if (codiceInternoTipoIspezione == '7a') {

					arrayAllarmeRapido[indiceArrayAllarme] = tipiispezione[i].value;
					indiceArrayAllarme++;
					if (tipiispezione[i].checked == true) {
						isAllarmeRapido = true;

						if (document.getElementById("hidden1") != null)
							document.getElementById("hidden1").style.display = "";

						if (document.getElementById("ispezione_generica_"
								+ tipiispezione[i].value) != null)
							document.getElementById("ispezione_generica_"
									+ tipiispezione[i].value).style.display = "";

						if (document.getElementById("hidden2") != null)
							document.getElementById("hidden2").style.display = "";

						if (document.forms[form].destinazioneDistribuzione != null) {
							document.forms[form].destinazioneDistribuzione.style.background = "#FFFFFF";
							document.forms[form].destinazioneDistribuzione.style.display = "none";
						}
						document.getElementById("tableHidden").style.display = "";

						azioneSuEsitoControllo(form);
						abilitaDestinazione(form);
					}

				} else

				if (codiceInternoTipoIspezione == '30a') {
					if (tipiispezione[i].checked == true) {
						isRilascioCert = true;
						if (document.getElementById("rilascio_certificazione") != null)
							document.getElementById("rilascio_certificazione").style.display = "";
					}

				} else if (codiceInternoTipoIspezione == '9A') {
					if (tipiispezione[i].checked == true) {
						isSospetto = true;
						if (document.getElementById("tipoSospetto") != null) {
							document.getElementById("tipoSospetto").style.display = "";
							document.getElementById("ispezione_generica_"
									+ tipiispezione[i].value).style.display = "";
							if (document.getElementById('tipoSosp').value == "1") {
								document.getElementById("tipoBuffer").style.display = "";
							}
						}

					}

				} else if (codiceInternoTipoIspezione == '58A') {
					if (tipiispezione[i].checked == true) {
						isNcPrec = true;
						if (document.getElementById("nonconformitaprec") != null)
							document.getElementById("nonconformitaprec").style.display = "";
					}

				} else if (tipiispezione[i].checked == true) {
					if (document.getElementById("ispezione_generica_"
							+ tipiispezione[i].value) != null)
						document.getElementById("ispezione_generica_"
								+ tipiispezione[i].value).style.display = "";
				}
			} else {
				if (tipiispezione[i].checked == false) {

					if (document.getElementById("ispezione_generica_"
							+ tipiispezione[i].value) != null)
						document.getElementById("ispezione_generica_"
								+ tipiispezione[i].value).style.display = "none";
				}
			}

			//commento al 214
			if(true) //mettere il commento per attivare il 214
if(tipiispezione[i].checked==true)
				{
			var idPianoToPass = tipiispezione[i].value.split(";")[0];
			if(idPianoToPass=='89')
				idPianoToPass = tipiispezione[i].id.split("_")[1];
			
			PopolaCombo.hasEventoMotivoCU("isStatoSanitarioApiari", idPianoToPass, -1, {
				async : false,
				callback : gestisciStatoSanitarioApiariCallBack
			});
				
			PopolaCombo.hasEventoMotivoCU("isCaniAggressori", idPianoToPass, -1, {
				async : false,
				callback : gestiscihasEventoMotivoCUCallBack
			});
			
			if(isCaniAggressori==false)
			{
			PopolaCombo.hasEventoMotivoCU("isCaniMorsicatori", idPianoToPass, -1, {
				async : false,
				callback : gestiscihasEventoMotivoCUCallBack
			});
			}
			
				}
		}

	}
	/*
	 * if (tipiispezione[i].value == '16' ) { isTossinfezione = true ;
	 * document.getElementById("tossinfezione").style.display="";
	 *  }
	 */
	/* CASI DI NON SELEZIONE */
	if (isPianoMonitoraggio == false) {
		if (document.getElementById("row_piano") != null)
			document.getElementById("row_piano").style.display = "none";
	
		if (document.URL.indexOf("AcqueRete") > -1){
			var resettato = 0;
			for (var k=0; k<10; k++){
				if (document.getElementById("clonepiano" + (k)) != null){
						if (resettato==0)
							addClonePianoVuoto("clonepiano"+k);

				document.getElementById("clonepiano"+ (k)).parentNode.removeChild(document.getElementById("clonepiano"+ (k)));
				var elem_num_piani = document.getElementById("num_piani");
				elem_num_piani.value = (parseInt(elem_num_piani.value) -1)+'' ;
				resettato++;
				}	
			}
		}	
	}

	/*
	 * if (isAltro ==false) {
	 * if(document.getElementById("ispezione_altro")!=null)
	 * document.getElementById("ispezione_altro").style.display="none";
	 * if(document.getElementById("label")!=null)
	 * document.getElementById("label").style.display="none"; }
	 */

	if (isAllarmeRapido == false) {

		if (document.getElementById("hidden1") != null)
			document.getElementById("hidden1").style.display = "none";

		for (i = 0; i < arrayAllarmeRapido.length; i++)
			if (document.getElementById("ispezione_generica_"
					+ arrayAllarmeRapido[i]) != null)
				document.getElementById("ispezione_generica_"
						+ arrayAllarmeRapido[i]).style.display = "none";

		if (document.getElementById("hidden2") != null)
			document.getElementById("hidden2").style.display = "none";

		if (document.forms[form].destinazioneDistribuzione != null) {
			document.forms[form].destinazioneDistribuzione.style.background = "#FFFFFF";
			document.forms[form].destinazioneDistribuzione.style.display = "none";
			document.getElementById("tableHidden").style.display = "none";
		}

	}

	if (isRilascioCert == false) {

		if (document.getElementById("rilascio_certificazione") != null)
			document.getElementById("rilascio_certificazione").style.display = "none";

	}
	if (isSospetto == false) {

		if (document.getElementById("tipoSospetto") != null) {
			document.getElementById("tipoSospetto").style.display = "none";
			if (document.getElementById("ispezione_generica_9") != null)
				document.getElementById("ispezione_generica_9").style.display = "none";
		}

	}

	if (isNcPrec == false) {

		if (document.getElementById("nonconformitaprec") != null)
			document.getElementById("nonconformitaprec").style.display = "none";

	}
	if (isMacellazione == false) {

		if (document.getElementById("macellazione") != null)
			document.getElementById("macellazione").style.display = "none";

	}
	if (isMacellazioneUrgenza == false) {

		if (document.getElementById("macellazione_urgenza") != null)
			document.getElementById("macellazione_urgenza").style.display = "none";

	}

	if (isCampione == false) {

		if (document.getElementById("seguitodicampionamento") != null)
			document.getElementById("seguitodicampionamento").style.display = "none";

	}
	/*
	 * if (isImportazioneScambio ==false) {
	 * 
	 * if(document.getElementById("importazionescambio")!=null)
	 * document.getElementById("importazionescambio").style.display="none";
	 * 
	 *  }
	 */

	if (isSvincoli == false) {

		if (document.getElementById("svincolisanitari") != null)
			document.getElementById("svincolisanitari").style.display = "none";

	}

	/*
	 * if (isTossinfezione ==false) {
	 * 
	 * document.getElementById("tossinfezione").style.display="none";
	 *  }
	 */

	// if (typeof mostraEstesi != 'undefined') {
	// mostraEstesi();
	// }
	
	var orgArray = new Array();
	var form = document.forms[0];
	for (var i = 0; i < form.length; i++) {
	    name = form.elements[i].name;
	    var startName = name.toLowerCase();
	    if (startName.startsWith('field4_')) {
	    	orgArray.push(startName.replace("field4_", ""));
	    }
	}
	
	
	for (var i=0; i<orgArray.length; i++){

		if (isPianoAcqueNoRadio){
		
		var field4 = document.getElementById("field4_"+orgArray[i]);
		var field5 = document.getElementById("field5_"+orgArray[i]);
		var field6 = document.getElementById("field6_"+orgArray[i]);
		var field7 = document.getElementById("field7_"+orgArray[i]);
		var field10 = document.getElementById("field10_"+orgArray[i]);
		var field11 = document.getElementById("field11_"+orgArray[i]);
		
		if (field4!=null){
			field4.disabled=false;
			field5.disabled=false;
			field6.disabled=false;
			field7.disabled=false;
			field11.disabled=false;
			
			field10.disabled = true;
			field10.checked = false;
			}

		if (document.getElementById("link_select_motivo")!=null)
			document.getElementById("link_select_motivo").style.display="none";
	}
	if (isPianoAcqueRadio){
		var field4 = document.getElementById("field4_"+orgArray[i]);
		var field5 = document.getElementById("field5_"+orgArray[i]);
		var field6 = document.getElementById("field6_"+orgArray[i]);
		var field7 = document.getElementById("field7_"+orgArray[i]);
		var field10 = document.getElementById("field10_"+orgArray[i]);
		var field11 = document.getElementById("field11_"+orgArray[i]);
		
		if (field10!=null){
			
			field4.checked=false;
			field5.checked=false;
			field6.checked=false;
			field7.checked=false;
			field11.checked=false;
			
			field4.disabled=true;
			field5.disabled=true;
			field6.disabled=true;
			field7.disabled=true;
			field11.disabled=true;

			field10.disabled=false;
			field10.checked=true;
			field10.onclick= function() { return false; }
			
		}
		
		if (document.getElementById("link_select_motivo")!=null)
			document.getElementById("link_select_motivo").style.display="none";
	}
	}
}


function gestiscihasEventoMotivoCUCallBack(val)
{
	if (val==true || val =='true')
	{
		//Flusso 214 riaperto
		//document.getElementById("misureFormativetr").style.display = "" ;
		//document.getElementById("misureRestrittivetr").style.display = "" ;
		//document.getElementById("misureRiabilitativetr").style.display = "" ;
		if(document.getElementById("canicontrollatitr")!=null)
		{
			document.getElementById("canicontrollatitr").style.display = "" ;
			for(var i=1;document.getElementById("mc_" + i)!=null;i++)
				document.getElementById("size_p").value = i;
			
		}
		isCaniAggressori = true;
	}
	else if(isCaniAggressori == false)
	{
		document.getElementById("misureFormativetr").style.display = "none" ;
		document.getElementById("misureRestrittivetr").style.display = "none" ;
		document.getElementById("misureRiabilitativetr").style.display = "none" ;
		if(document.getElementById("canicontrollatitr")!=null)
		{
			document.getElementById("canicontrollatitr").style.display = "none" ;
			document.getElementById("size_p").value = "0" ;
		}
	}
}

function gestisciStatoSanitarioApiariCallBack(val)
{ 
	if (val==true || val =='true')
	{
		if(document.getElementById("apiariSelezionatitr")!=null)
			document.getElementById("apiariSelezionatitr").style.display = "" ;
		isStatoSanitarioApiari = true;
	}
	else if (isStatoSanitarioApiari == false)
	{
		if(document.getElementById("apiariSelezionatitr")!=null)
			document.getElementById("apiariSelezionatitr").style.display = "none" ;
	}
}

function mostraMenu4(form) {

	var valore = '-1';

	if (document.getElementsByName('auditTipo') != null)
		valori = document.getElementsByName('auditTipo');

	for (i = 0; i < valori.length; i++) {
		valore = valori[i].value;
		if (valore == '1') {

			document.getElementById('oggettoAudit').style.display = 'none';
			if (document.getElementById("auditFollowup") != null)
				document.getElementById('auditFollowup').style.display = 'none';

		} else {
			if (valore == '2' || valore == '3' || valore == '101'
					|| valore == '102' || valore == '103') {

				document.getElementById('oggettoAudit').style.display = '';
				if (valore == '101' || valore == '102' || valore == '103') {
					if (document.getElementById("auditFollowup") != null)
						document.getElementById('auditFollowup').style.display = '';
				}

			} else if (valore == 6) {

				document.getElementById('oggettoAudit').style.display = 'none';
				if (document.getElementById("auditFollowup") != null)
					document.getElementById('auditFollowup').style.display = 'none';
			} else {

				document.getElementById('oggettoAudit').style.display = 'none';
				if (document.getElementById("auditFollowup") != null)
					document.getElementById('auditFollowup').style.display = 'none';
			}

			if (document.getElementById("label1") != null)
				document.getElementById("label1").style.display = "none";
			if (document.getElementById("label2") != null)
				document.getElementById("label2").style.display = "none";
		}
	}

}

function mostraMenu2(form) {

	bpiSel = false;
	haccpSel = false;

	$("#tipo_cu tr[name='tipo_audit']").each(function(i) {
		$(this).remove();

	});

	$("#containerTipoIsezioneSelected input[name='auditTipo']").each(
			function(i) {
				$(this).remove();

			});

	$("#containerTipoIsezioneSelected input[name='auditTipo']").each(
			function(i) {
				$(this).remove();

			});
	$("#containerTipoIsezioneSelected input[name='tipoAudit']").each(
			function(i) {
				$(this).remove();

			});
	$("#containerTipoIsezioneSelected input[name='bpi']").each(function(i) {
		$(this).remove();

	});
	$("#containerTipoIsezioneSelected input[name='haccp']").each(function(i) {
		$(this).remove();

	});

	// mostraMenuTipoIspezione(form);

}

function mostraMenu3(form) {

	var valore = document.forms[form].ispezione;
	var a = 0;

	for (var i = 0; i < valore.length; i++) {

		if (valore[i].value == "13" && valore[i].selected == true) {

			document.getElementById("fup").style.display = "block";
			a = 1;

		}
	}
	if (a == 0) {

		document.getElementById("fup").style.display = "none";

	}

}

function abilitaCodiceAllerta() {

	if (document.forms['details'].tipoIspezione != null) {
		getCodiceInternoTipoIspezione(document.forms['details'].tipoIspezione.value);
		if (codiceInternoTipoIspezione == '7a') {
			if (document.getElementById("hidden1") != null)
				document.getElementById("hidden1").style.display = "none";

			if (document.getElementById("ispezione_generica_"
					+ document.forms['details'].tipoIspezione.value) != null)
				document.getElementById("ispezione_generica_"
						+ document.forms['details'].tipoIspezione.value).style.display = "none";

		}

	}

}

function abilitaCodiceBuffer() {

	if (document.forms['details'].tipoIspezione != null
			&& document.forms['details'].tipoIspezione.value == "9a") {
		if (document.getElementById("tipoSospetto") != null)
			document.getElementById("tipoSospetto").style.display = "block";

	}

}

function initprovaFunzioneOLD(form) {

	if (document.forms[form].tipoCampione != null) {
		var valore = document.forms[form].tipoCampione.value;

		if (valore == "3") {

			document.getElementById("oggetto_controllo").style.display = "none";

			valori = document.getElementsByName('auditTipo');
			for (i = 0; i < valori.length; i++) {
				if (valori[i].value == '1' || valori[i].value == '2'
						|| valori[i].value == '3') {
					mostraMenu4(form);
				}

			}

			mostraMenuTipoIspezione(form);

			mostraMenu2(form);

			if (document.getElementById('molluschiquantitativo') != null)
				document.getElementById('molluschiquantitativo').style.display = "none";

		} else {
			if (valore == "4") {

				document.getElementById("oggetto_controllo").style.display = "";

				mostraMenu2(form);
				mostraMenuTipoIspezione(form);

				if (document.getElementById('molluschiquantitativo') != null)
					document.getElementById('molluschiquantitativo').style.display = "";

				if (document.getElementById("lab1") != null)
					document.getElementById("lab1").style.display = "none";

				mostraMenuTipoIspezione(form);

				mostraMenu2(form);

			} else {

				if (valore == "5") {
					document.getElementById("oggetto_controllo").style.display = "";

					mostraMenuTipoIspezione(form);

					if (document.getElementById('molluschiquantitativo') != null)
						document.getElementById('molluschiquantitativo').style.display = "none";
				} else {

					document.getElementById("oggetto_controllo").style.display = "none";

					mostraMenu4(form);

					mostraMenuTipoIspezione(form);
					mostraMenu2(form);
					if (document.getElementById('molluschiquantitativo') != null)
						document.getElementById('molluschiquantitativo').style.display = "none";

				}
			}
		}
	}

}

function initprovaFunzione(form) {

	if (document.forms[form].tipoCampione != null) {
		var valore = document.forms[form].tipoCampione.value;

		if (valore == "23" || valore == "7") {
			document.getElementById('tr_select_motivo').style.display = "";
			document.getElementById('link_select_motivo_href').href = "#";
			document.getElementById('link_select_motivo_href').onclick = function() {
				$('#dialogMotiviAudit').dialog('open');
			};
			document.getElementById('link_select_motivo').innerHTML = "SELEZIONA MOTIVO AUDIT";
			
				document.getElementById("oggetto_controllo").style.display = "none";
			
			mostraMenu4(form);

			if (document.getElementById('molluschiquantitativo') != null)
				document.getElementById('molluschiquantitativo').style.display = "none";

			if (document.getElementById("per_conto_di") != null)
				document.getElementById("per_conto_di").style.display = "";

			mostraMenuTipoIspezione(form);
		} else if (valore == "3") {

			document.getElementById('tr_select_motivo').style.display = "";
			document.getElementById('link_select_motivo_href').href = "#";
			document.getElementById('link_select_motivo_href').onclick = function() {
				$('#dialogMotiviIspezione').dialog('open');
			};
			document.getElementById('link_select_motivo').innerHTML = "SELEZIONA MOTIVO AUDIT";
			if (document.getElementById("data_iniziale")!=null){
				
				//CONTROLLO DATE SU AUDIT
				//DAL 2021 BISOGNA CANCELLARE TUTTO E LASCIARE SOLO STYLE.DISPLAY='' IN QUESTO IF
				var partsCu = document.getElementById("data_iniziale").value.split("/");
				var dateCu = new Date(partsCu[1] + "/" + partsCu[0] + "/" + partsCu[2]);
				var partsNuovoAudit = document.getElementById("dataNuovaGestioneOggettoControlloAudit").value.split("/");
				var dateNuovoAudit = new Date(partsNuovoAudit[1] + "/" + partsNuovoAudit[0] + "/" + partsNuovoAudit[2]);
				if (dateCu>=dateNuovoAudit)
					document.getElementById("oggetto_controllo").style.display = "";
				//FINE CONTROLLO DATE SU AUDIT
				
				}
			else
				document.getElementById("oggetto_controllo").style.display = "none";
			
			mostraMenu4(form);

			if (document.getElementById('molluschiquantitativo') != null)
				document.getElementById('molluschiquantitativo').style.display = "none";

			if (document.getElementById("per_conto_di") != null)
				document.getElementById("per_conto_di").style.display = "none";

			//mostraMenuTipoIspezione(form);
		
		}else {
		
			if (valore == "4") {

				document.getElementById("oggetto_controllo").style.display = "";
				// inserire qua

				if (document.getElementById("lab1") != null)
					document.getElementById("lab1").style.display = "block";

				if (document.getElementById("per_conto_di") != null)
					document.getElementById("per_conto_di").style.display = "none";

				if (document.getElementById("modificabile").value != 'null') {
					document.getElementById('tr_select_motivo').style.display = "";

					document.getElementById('link_select_motivo_href').href = "#";
					document.getElementById('link_select_motivo_href').onclick = function() {
						$('#dialogMotiviIspezione').dialog('open');
					};

					document.getElementById('link_select_motivo').innerHTML = "SELEZIONA MOTIVO ISPEZIONE";
				}

			} else {

				if (valore == "5") {
//					document.getElementById("oggetto_controllo").style.display = "";

					if (document.getElementById("per_conto_di") != null)
						document.getElementById("per_conto_di").style.display = "";

					if (document.getElementById('molluschiquantitativo') != null)
						document.getElementById('molluschiquantitativo').style.display = "none";

					document.getElementById('tr_select_motivo').style.display = "none";
				} else {

					document.getElementById("oggetto_controllo").style.display = "none";
					document.forms[form].auditTipo.style.display = "none";

					if (document.getElementById('molluschiquantitativo') != null)
						document.getElementById('molluschiquantitativo').style.display = "none";

				}
			}
		}
	}
	
	
	document.getElementById('tr_select_motivo').style.display = 'none';

}

function abilitaSistemaAllarmeRabido(form)

{

	// alert("stop");
	if (document.forms[form].tipoIspezione != null) {
		// alert("stop1");

		for (i = 0; i < document.getElementsByName('tipoIspezione').length; i++) {

			// if (document.getElementsByName('tipoIspezione')[i].checked==true)
			// {
			var valore = document.getElementsByName('tipoIspezione')[i].value;
			// codiceInternoTipoIspezione =
			// $("#"+document.getElementsByName('tipoIspezione')[i].id).attr("codiceinterno");
			getCodiceInternoTipoIspezione(valore);
			if (codiceInternoTipoIspezione == "7a") {
				// document.forms[form].tipoIspezione.style.display="";
				if (document.getElementById("lab1") != null)
					document.getElementById("lab1").style.display = "";
				document.getElementById("tableHidden").style.display = "";
				if (document.getElementById("hidden1") != null)
					document.getElementById("hidden1").style.display = "";// display="block";

				if (document.getElementById("ispezione_generica_" + valore) != null)
					document.getElementById("ispezione_generica_" + valore).style.display = "none";

				if (document.getElementById("hidden2") != null)
					document.getElementById("hidden2").style.display = "";

				if (document.forms[form].esitoControllo == 13
						|| document.forms[form].esitoControllo == 14) {
					// document.getElementById("hidden3").style.display="";
					if (document.forms[form].destinazioneDistribuzione != null) {
						document.forms[form].destinazioneDistribuzione.style.background = "#FFFFFF";
						document.forms[form].destinazioneDistribuzione.style.display = "";
					}
				}
				abilitaDestinazione(form);
				if (document.getElementById("contributi") != null)
					document.getElementById("contributi").style.display = "";// display="block";
				// display="block";
				abilitaCampoNote(form);

				document.getElementById('rowAzione1').innerHTML = 'Quantita : '
						+ document.forms[form].quantitaBloccata.value + ' '
						+ document.forms[form].unitaMisura.value;
				document.getElementById('misura').innerHTML = ' '
						+ document.forms[form].unitaMisura.value;
				azioneSuEsitoControllo(form);
				azioneSuAzioniAdottate(form);
				break;
				// }
			}
		}
	}

}

// function prova(val)
// {
//
//
//
// valore=document.getElementById('nucleo_ispettivo_'+val).value;
// elementi = val;
//
//
// if(valore=="-1")
// {
// document.getElementById("Medici_"+elementi).style.display="none";
// document.getElementById("Tpal_"+elementi).style.display="none";
// document.getElementById("Veterinari_"+elementi).style.display="none";
// document.getElementById("Utente_"+elementi).style.display="none";
// document.getElementById("Ref_"+elementi).style.display="none";
// document.getElementById("Amm_"+elementi).style.display="none";
// document.getElementById("criuv_"+elementi).style.display="none";
//
// }
// else
// {
// if(valore =="1" )
// {
//
// document.getElementById("Medici_"+elementi).style.display="none";
// document.getElementById("Tpal_"+elementi).style.display="none";
// document.getElementById("Veterinari_"+elementi).style.display="block";
// document.getElementById("Utente_"+elementi).style.display="none";
// document.getElementById("Ref_"+elementi).style.display="none";
// document.getElementById("Amm_"+elementi).style.display="none";
// document.getElementById("criuv_"+elementi).style.display="none";
//
// }
// else
// {
// if(valore=="2")
// {
// document.getElementById("Medici_"+elementi).style.display="block";
// document.getElementById("Tpal_"+elementi).style.display="none";
// document.getElementById("Veterinari_"+elementi).style.display="none";
// document.getElementById("Utente_"+elementi).style.display="none";
// document.getElementById("Ref_"+elementi).style.display="none";
// document.getElementById("Amm_"+elementi).style.display="none";
// document.getElementById("criuv_"+elementi).style.display="none";
//
// }
// else
// {
// if(valore == "23")
// {
// document.getElementById("Medici_"+elementi).style.display="none";
// document.getElementById("Tpal_"+elementi).style.display="block";
// document.getElementById("Veterinari_"+elementi).style.display="none";
// document.getElementById("Utente_"+elementi).style.display="none";
// document.getElementById("Ref_"+elementi).style.display="none";
// document.getElementById("Amm_"+elementi).style.display="none";
// document.getElementById("criuv_"+elementi).style.display="none";
//
// }
// else
// {
// if(valore == "24")
// {
// document.getElementById("Medici_"+elementi).style.display="none";
// document.getElementById("Tpal_"+elementi).style.display="none";
// document.getElementById("Veterinari_"+elementi).style.display="none";
// document.getElementById("Utente_"+elementi).style.display="none";
// document.getElementById("Ref_"+elementi).style.display="block";
// document.getElementById("Amm_"+elementi).style.display="none";
// document.getElementById("criuv_"+elementi).style.display="none";
//
// }
// else
// {
// if(valore == "25")
// {
// document.getElementById("Medici_"+elementi).style.display="none";
// document.getElementById("Tpal_"+elementi).style.display="none";
// document.getElementById("Veterinari_"+elementi).style.display="none";
// document.getElementById("Utente_"+elementi).style.display="none";
// document.getElementById("Ref_"+elementi).style.display="none";
// document.getElementById("Amm_"+elementi).style.display="block";
// document.getElementById("criuv_"+elementi).style.display="none";
//
// }
// else
// {
// if(valore == "26")
// {
// document.getElementById("Medici_"+elementi).style.display="none";
// document.getElementById("Tpal_"+elementi).style.display="none";
// document.getElementById("Veterinari_"+elementi).style.display="none";
// document.getElementById("Utente_"+elementi).style.display="none";
// document.getElementById("Ref_"+elementi).style.display="none";
// document.getElementById("Amm_"+elementi).style.display="none";
// document.getElementById("criuv_"+elementi).style.display="blcok";
//
// }
// else
// {
// document.getElementById("Medici_"+elementi).style.display="none";
// document.getElementById("Tpal_"+elementi).style.display="none";
// document.getElementById("Veterinari_"+elementi).style.display="none";
// document.getElementById("Utente_"+elementi).style.display="block";
// document.getElementById("Ref_"+elementi).style.display="none";
// document.getElementById("Amm_"+elementi).style.display="none";
// document.getElementById("criuv_"+elementi).style.display="none";
//
// }
// }
// }
//
// }
// }
//
// }
//
// }
// }

function clona() {

	var maxElementi = 10;
	var elementi;
	var elementoClone;
	var tableClonata;
	var tabella;

	var selezionato;
	var x;
	elementi = document.getElementById('elementi');
	if (elementi.value < maxElementi) {

		if (document.getElementById('nucleo_ispettivo_' + elementi.value).value != '-1') {

			elementi.value = parseInt(elementi.value) + 1;
			size = document.getElementById('size');
			size.value = parseInt(size.value) + 1;
			var primo_elemento = document.getElementById('nucleo_ispettivo_1');
			var indice = parseInt(elementi.value) - 1;

			x = document.getElementById('lcso_patologia_' + String(indice));
			if (primo_elemento != null && x == null) {
				selezionato = document.getElementById('nucleo_ispettivo_1').selectedIndex;
			} else if (primo_elemento == null && x != null) {
				selezionato = x.selectedIndex;
			}
			var clonato = document.getElementById('nucleo_ispettivo');

			/* clona riga vuota */
			clone = clonato.cloneNode(true);
			clone.getElementsByTagName('SELECT')[0].name = "nucleo_ispettivo_"
					+ elementi.value;
			clone.getElementsByTagName('SELECT')[0].id = "nucleo_ispettivo_"
					+ String(indice + 1);
			clone.getElementsByTagName('SELECT')[0].value = '-1';

			clone.getElementsByTagName('SELECT')[0].onchange = function() {
				prova(parseInt(indice) + 1);
			}
			// clone.getElementsByTagName('LABEL')[0].innerHtml = "Nucleo
			// Ispettivo "+elementi.value;

			/**
			 * LISTA VETERINARI
			 */

			clone.getElementsByTagName('SELECT')[1].id = "Veterinari_"
					+ elementi.value;
			clone.getElementsByTagName('SELECT')[1].style.display = "none";
			clone.getElementsByTagName('SELECT')[1].value = '-1';
			clone.getElementsByTagName('SELECT')[1].onchange = function() {
				clona();
				mostraUo(parseInt(indice) + 1);
				document.getElementById('componenteid_' + elementi.value).value = clone
						.getElementsByTagName('SELECT')[1].value;

			}
			clone.getElementsByTagName('SELECT')[1].name = "Veterinari_"
					+ elementi.value;

			/**
			 * LISTA MEDICI
			 */
			clone.getElementsByTagName('SELECT')[2].id = "Medici_"
					+ elementi.value;
			clone.getElementsByTagName('SELECT')[2].name = "Medici_"
					+ elementi.value;
			clone.getElementsByTagName('SELECT')[2].style.display = "none";
			clone.getElementsByTagName('SELECT')[2].value = '-1';
			clone.getElementsByTagName('SELECT')[2].onchange = function() {
				clona();
				mostraUo(parseInt(indice) + 1);
				document.getElementById('componenteid_' + elementi.value).value = clone
						.getElementsByTagName('SELECT')[2].value;

			}
			/**
			 * LISTA TPAL
			 */
			clone.getElementsByTagName('SELECT')[3].id = "Tpal_"
					+ elementi.value;
			clone.getElementsByTagName('SELECT')[3].name = "Tpal_"
					+ elementi.value;
			clone.getElementsByTagName('SELECT')[3].style.display = "none";
			clone.getElementsByTagName('SELECT')[3].value = '-1';
			clone.getElementsByTagName('SELECT')[3].onchange = function() {
				clona();
				mostraUo(parseInt(indice) + 1);
				document.getElementById('componenteid_' + elementi.value).value = clone
						.getElementsByTagName('SELECT')[3].value;

			}

			/**
			 * LISTA REFERENTE ALLERTE
			 */
			clone.getElementsByTagName('SELECT')[4].id = "Ref_"
					+ elementi.value;
			clone.getElementsByTagName('SELECT')[4].name = "Ref_"
					+ elementi.value;
			clone.getElementsByTagName('SELECT')[4].style.display = "none";
			clone.getElementsByTagName('SELECT')[4].value = '-1';
			clone.getElementsByTagName('SELECT')[4].onchange = function() {
				clona();
				mostraUo(parseInt(indice) + 1);
				document.getElementById('componenteid_' + elementi.value).value = clone
						.getElementsByTagName('SELECT')[4].value;

			}

			/**
			 * LISTA AMMINISTRATIVI
			 */
			clone.getElementsByTagName('SELECT')[5].id = "Amm_"
					+ elementi.value;
			clone.getElementsByTagName('SELECT')[5].name = "Amm_"
					+ elementi.value;
			clone.getElementsByTagName('SELECT')[5].style.display = "none";
			clone.getElementsByTagName('SELECT')[5].value = '-1';
			clone.getElementsByTagName('SELECT')[5].onchange = function() {
				clona();
				mostraUo(parseInt(indice) + 1);
				document.getElementById('componenteid_' + elementi.value).value = clone
						.getElementsByTagName('SELECT')[5].value;
			}

			clone.getElementsByTagName('INPUT')[0].name = "Utente_"
					+ elementi.value;
			clone.getElementsByTagName('INPUT')[0].id = "Utente_"
					+ elementi.value;
			clone.getElementsByTagName('INPUT')[0].style.display = "none";
			clone.getElementsByTagName('INPUT')[0].value = '';

			clone.getElementsByTagName('INPUT')[1].name = "componenteid_"
					+ elementi.value;
			clone.getElementsByTagName('INPUT')[1].id = "componenteid_"
					+ elementi.value;
			clone.getElementsByTagName('INPUT')[1].value = '';

			/* Lo rendo visibile */

			clone.id = "nucleoispettivo_" + elementi.value;
			/* Aggancio il nodo */
			clonato.parentNode.appendChild(clone);

			/* Lo rendo visibile */

			clone.style.visibility = "visible";

		}

	}
}

function resetElementiNucleoIspettivo(numElementi) {

	document.getElementById('size').value = (parseInt(numElementi) + 1);
	document.getElementById('elementi').value = (parseInt(numElementi) + 1);
}

function verificaChiusuraCu(flagChiusura) {
	if (flagChiusura != null) {
		if (flagChiusura == "1") {
			alert("Questo Controllo Ufficiale non puo essere chiuso. Ci sono attivita\' che non sono state ancora chiuse.");
			// alert("Controllo Ufficiale e sottosezioni chiuse.");
		} else if (flagChiusura == "5") {
			alert("Questo Controllo Ufficiale non puo essere chiuso a causa di checklist (sorveglianza o macelli) mancante o categoria di rischio non aggiornata. Le altre sottoattività sono state chiuse.");
		} else if (flagChiusura == "3") {
			alert("Chiusura del controllo ufficiale effettuata correttamente.");
		} else if (flagChiusura == "4") {
			alert("Controllo Ufficiale chiuso in attesa di esito (sottosezione Tamponi o Campioni).");
		} else if (flagChiusura == "5") {
			alert("Questo controllo ufficiale non puo essere chiuso a causa di checklist non salvate definitivamente.");
		} 
		
	}
}


function addClonePianoVuoto(rowId){
    var target = document.getElementById(rowId);
    var newElement = document.createElement('tr');
    newElement.id = "clonepiano";
    newElement.className ="containerBody";
    newElement.style.display="none";
    
    var newElementTd1 = newElement.insertCell(-1);
    newElementTd1.className="formLabel";
    newElementTd1.innerHTML ="<label id = \"piano\"><b></b></label>";
    
    var newElementTd2 = newElement.insertCell(-1);
    newElementTd2.innerHTML="<table class=\"noborder\"><tr><td colspan=\"2\"  ><input type = \"hidden\" name = \"piano_monitoraggio\" id = \"piano_value\" value = \"-1\"> <font color = \"red\">*</font><input type = \"hidden\" name = \"uo\" id = \"uo\" value=\"-1\"><div id =\"uodescr\" ></div><a href=\"#\" onclick=\"\" style=\"\"><font  color=\"#006699\" style=\"font-weight: bold;\">Seleziona Per Conto di</font></a></td></tr></table>";
    
    target.parentNode.insertBefore(newElement, target);
    return newElement;
}

function resetSelectElement(selectElement) {
    var options = selectElement.options;

    // Look for a default selected option
    for (var i=0, iLen=options.length; i<iLen; i++) {

        if (options[i].defaultSelected) {
            selectElement.selectedIndex = i;
            return;
        }
    }

    // If no option is the default, select first or none as appropriate
    selectElement.selectedIndex = -1; // or -1 for no option selected
}
