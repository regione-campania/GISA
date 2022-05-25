/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
if (window.location.host.split(".").lenght < 4) {
	try {

		var domain = window.location.hostname;
		domain = domain.substring(domain.indexOf('.') + 1);
		// alert(domain);
		document.domain = domain;
		// alert(document.domain);
	} catch (err) {
		alert('Errore setting domain');

	}
}
function continueVam(idEventoInserito) {

//alert(idEventoInserito); //per il futuro	
if (idEventoInserito != '-1')	{
$("#continue_buttons").css("visibility","visible");
if( $("#retry_buttons")!=null)
	$("#retry_buttons").css("visibility","hidden");
ShowToolTip(this, 'ATTENZIONE! E\' necessario cliccare sul pulsante <strong>Inserimento Registrazioni Completato</strong> per completare correttamente l\'inserimento della registrazione ');
$('a').removeAttr('href');
$('a').removeAttr('onclick');
}else{
	ShowToolTip(this, 'ATTENZIONE! Si e\' verificato un errore nell\'inserimento in BDU, contatta l\'helpdesk ');	
}
}


function ShowToolTip(e, strText) {
    if (strText != "") {
        $("#divToolTip").show();
        var x = e.pageX;
        var y = e.pageY;
        var div = document.getElementById("divToolTip");
        div.innerHTML = strText;
        div.style.left = (x - $('#divToolTip').width() - 6) + "px";
        div.style.top = (y - $('#divToolTip').height() - 6) + "px";
    }
} 