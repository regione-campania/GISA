<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script src="javascript/jquery.qrcode.min.js"></script>

<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="DownloadAppMobile.do?command=SuiteAppMobile">SUITE APP MOBILE GISA</a> > APP MOBILE IOS PREACCETTAZIONE SIGLA
		</td>
	</tr>
</table>

<input type="hidden" id="codice_riscatto_app" value="${codice_riscatto_app}"/>
<center>
	<h2>Download app iOS PreaccettazioneSIGLA</h2>
	Per installare l'app PreaccettazioneSIGLA basta scansionare il seguente qr-code oppure <br>
	utilizzare il codice sottostante nell'apposita sezione di App Store (App Store -> Account -> Usa carta regalo o codice promozionale).
	<br><br>
	<table style="width: 30%;">
		<tr>
			<td align="center" ><div id="qrcode"></div></td>
			<td align="center" >
				<font id="codice_label" style="font-size:20px">codice: <b>${codice_riscatto_app}</b></font>
				<br><img src="images/ios_icon.jpg"  width="80" height="50" >
			</td>
		</tr>
	</table>
	<br>
	<font color="red">
		Nota bene: il presente qr-code/codice è stato generato esclusivamente per l'utente loggato ed è valido per una sola installazione dell'app, se si
	    prova a reinstallare l'app con lo stesso qr-code/codice si otterrà un messaggio d'errore sullo smartphone! <br><br>
		Se l'utente intende re-installare l'app sul proprio smartphone iOS ci sono due alternative:<br>
		&#8226; se l'app è stata già scaricata sullo stesso smartphone, andare in App Store -> Account -> Acquisti e riscaricare l'app PreaccettazioneSIGLA <br>
		&#8226; rigenerare il qr-code/codice mediante il seguente link:
		<a href="#" 
		   onclick="loadModalWindowCustom('Attendere Prego...'); window.location.href='DownloadAppMobile.do?command=RigeneraAppMobileIosPreaccettazionesigla'"  
		   style="color:red;"><b>rigenera qr-code/codice</b></a>
			
	</font>
	<br><br>
</center>


<script>


var codice_riscatto = document.getElementById('codice_riscatto_app').value;

if(codice_riscatto.includes("errore_rigenerazione_codice_riscatto")){
	
	var split_string_codice = codice_riscatto.split("_");
	codice_riscatto = split_string_codice[split_string_codice.length -1];
	document.getElementById("codice_label").innerHTML = "codice: <b>"+ split_string_codice[split_string_codice.length -1] + "</b>";
	codice_riscatto = "https://buy.itunes.apple.com/WebObjects/MZFinance.woa/wa/freeProductCodeWizard?code=" + codice_riscatto;
	$("#qrcode").qrcode({ text: codice_riscatto, render: "table", width: 100, height: 100 });
	alert("Attenzione! Hai raggiunto il numero massimo di rigenerazioni giornaliere consentite. Per ulteriori informazioni contattare l'help desk.");
	
} else {
	codice_riscatto = "https://buy.itunes.apple.com/WebObjects/MZFinance.woa/wa/freeProductCodeWizard?code=" + codice_riscatto;
	$("#qrcode").qrcode({ text: codice_riscatto, render: "table", width: 100, height: 100 });
}



</script>