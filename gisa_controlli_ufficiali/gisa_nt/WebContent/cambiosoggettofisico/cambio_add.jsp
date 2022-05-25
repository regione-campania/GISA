<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="NazioniList"     class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ToponimiList"    class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<jsp:useBean id="Operatore" class="org.aspcfs.modules.opu.base.Operatore" scope="request"/>
<jsp:useBean id="dataRichiesta" class="java.lang.String" scope="request" />
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants" %>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<link rel="stylesheet" type="text/css" href="opumodifica/css/styleModifica.css"></link>		

<script type="text/javascript" src="suap/javascriptsuap/suap_imprese.js"></script>
<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/opu.css" />
<link rel="stylesheet" type="text/css" href="css/jquery.calendars.picker.css">
<link href="javascript/datepicker/jquery.datepick.css" rel="stylesheet">
<script src="javascript/suap.jquery.steps.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/gestoreCodiceFiscale.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.plus.js"></script>
<script type="text/javascript" src="javascript/jquery.plugin.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.picker.js"></script>
<script src="javascript/parsedate.js"></script>
<script src="javascript/jquery-ui.js"></script>
<SCRIPT src="javascript/opu.js"></SCRIPT>
<SCRIPT src="javascript/upload.js"></SCRIPT>
<SCRIPT src="javascript/suapCittadinoUtil.js"></SCRIPT>
<script src="javascript/gestoreCodiceFiscale.js"></script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<script src="javascript/datepicker/jquery.plugin.js"></script>
<script src="javascript/datepicker/jquery.datepick.js"></script>

<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal99 = new CalendarPopup();
	cal99.showYearNavigation();
	cal99.showYearNavigationInput();
	cal99.showNavigationDropdowns();
</SCRIPT>

<script>
/*VALIDAZIONE DEL CAMPO DATA*/
$.validator.addMethod("dataFormat",function(value, element) {
        // put your own logic here, this is just a (crappy) example
        return value.match(/^\d\d?\/\d\d?\/\d\d\d\d$/);
        },
        "Inserire una data nel formato GG/MM/AAAA"
);
     

/*AUTOCOMPLETAMENTO PER GLI INDIRIZZI*/          
$(function(){
        //$( "#searchcodeIdComune" ).combobox();
    //  $( "#partitaIva" ).combobox();
    $( "#addressLegaleCountry" ).combobox();
    $( "#addressLegaleCity" ).combobox();
    $( "#comuneNascita" ).combobox();
 	$( "#addressLegaleLine1" ).combobox();
	$('#dataNascita2').datepick({dateFormat: 'dd/mm/yyyy', maxDate: 0, showOnFocus: false, showTrigger: '#calImg'});

});    
     
function svuotaCf(){
	var cf = document.getElementById("codFiscaleSoggetto");
	cf.value="";
}

function abilitaDisabilitaIndirizzo(tipo)
{
		var abilitare = document.getElementById('toponimoResidenza').disabled==true;
		if(abilitare && document.getElementById('nazioneResidenza').value!='106')
		{
			document.getElementById('toponimoResidenza').disabled=!abilitare;
			document.getElementById('civicoResidenza').readOnly=!abilitare;
			document.getElementById('capResidenza').readOnly=!abilitare;
			document.getElementById('addressLegaleLine1input').readOnly=!abilitare;
			document.getElementById('addressLegaleCitta').readOnly=!abilitare;
		}
		else if(!abilitare && document.getElementById('nazioneResidenza').value=='106')
		{
			document.getElementById('toponimoResidenza').disabled=!abilitare;
			document.getElementById('civicoResidenza').readOnly=!abilitare;
			document.getElementById('capResidenza').readOnly=!abilitare;
			document.getElementById('addressLegaleLine1input').readOnly=!abilitare;
			document.getElementById('addressLegaleCitta').readOnly=!abilitare;
		}
		document.getElementById('toponimoResidenza').value=-1;
		document.getElementById('civicoResidenza').value='';
		document.getElementById('capResidenza').value='';
		document.getElementById('addressLegaleLine1input').value='';
		document.getElementById('addressLegaleCountrySigla').value='';
		document.getElementById('addressLegaleCitta').value='';
	
}

function getSelectedText(elementId) {
    var elt = document.getElementById(elementId);
    if (elt.selectedIndex == -1)
        return null;
    return elt.options[elt.selectedIndex].text;
}

function checkForm(form){
	var esito = true;
	
	var nome = document.getElementById("nome").value;
	var cognome = document.getElementById("cognome").value;
	var dataNascita = document.getElementById("dataNascita2").value;
	var nazioneNascita = document.getElementById("nazioneNascita").value;
	var comuneNascita = document.getElementById("comuneNascitainput").value;
	var codiceFiscale = document.getElementById("codFiscaleSoggetto").value;
	var nazioneResidenza = document.getElementById("nazioneResidenza").value;
	var comuneResidenza= document.getElementById("addressLegaleCitta").value;
	var provinciaResidenza= document.getElementById("addressLegaleCountrySigla").value;
	var viaResidenza= document.getElementById("addressLegaleLine1input").value;
	var toponimoResidenza= document.getElementById("toponimoResidenza").value;
	var descrizioneToponimoResidenza = getSelectedText("toponimoResidenza");
	var civicoResidenza= document.getElementById("civicoResidenza").value;
	
	var msg = 'Impossibile salvare.';
	
	if (nome==''){
		msg+="\nNOME non inserito";
		esito=false;
	}
	if (cognome==''){
		msg+="\nCOGNOME non inserito.";
		esito=false;
	}
	if (dataNascita==''){
		msg+="\nDATA NASCITA non inserita.";
		esito=false;
	}
	if (nazioneNascita==-1){
		msg+="\nNAZIONE NASCITA non inserita.";
		esito=false;
	} 
	if (comuneNascita==''){
		msg+="\nCOMUNE NASCITA non inserito.";
		esito=false;
	}
	if (codiceFiscale==''){
		msg+="\nCODICE FISCALE non inserito.";
		esito=false;
	}
	if (nazioneResidenza==-1){
		msg+="\nNAZIONE RESIDENZA non inserita.";
		esito=false;
	} 
	if (provinciaResidenza==''){
		msg+="\nPROVINCIA RESIDENZA non inserita.";
		esito=false;
	}
	if (comuneResidenza==''){
		msg+="\nCOMUNE RESIDENZA non inserito.";
		esito=false;
	}
	if (toponimoResidenza==-1){
		msg+="\nTOPONIMO RESIDENZA non inserito.";
		esito=false;
	} 
	if (viaResidenza==''){
		msg+="\nVIA RESIDENZA non inserita.";
		esito=false;
	}
	if (civicoResidenza==''){
		msg+="\nCIVICO RESIDENZA non inserito.";
		esito=false;
	}
	
	if (esito==false){
		alert(msg);
		return false;
	}
	else {
		msg = "Dati inseriti per il nuovo soggetto fisico: \n\n";
		msg+=("NOME: "+nome+" "+cognome+"\t\tCODICE FISCALE: "+codiceFiscale+"\t\tINDIRIZZO RESIDENZA: "+descrizioneToponimoResidenza+" "+viaResidenza+" "+civicoResidenza).toUpperCase();
		msg+="\n\nConfermare?";
		
		if (confirm(msg)){
			loadModalWindow();
			form.submit();
			}
			
		else
			return false;
	}
}



</script>

<%@ include file="../initPage.jsp" %>


<%
String nomeContainer = StabilimentoDettaglio.getContainer();
nomeContainer = "suap";
String param = "stabId="+StabilimentoDettaglio.getIdStabilimento()+"&opId=" + StabilimentoDettaglio.getIdOperatore()+"&altId="+StabilimentoDettaglio.getAltId();
request.setAttribute("Operatore",StabilimentoDettaglio.getOperatore());
String siglaAsl = "-";
switch (StabilimentoDettaglio.getIdAsl()) {
case 201: siglaAsl = "AV"; break;
case 202: siglaAsl = "BN"; break;
case 203: siglaAsl = "CE"; break;
case 204: siglaAsl = "NA1C"; break;
case 205: siglaAsl = "NA2N"; break;
case 206: siglaAsl = "NA3S"; break;
case 207: siglaAsl = "SA"; break;
}

%>

<table class="trails" cellspacing="0">
<tr>
<td width="100%">
<a href=""><dhv:label name="">Anagrafica stabilimenti</dhv:label></a> >
<a href="OpuStab.do?command=SearchForm"><dhv:label name="">Ricerca</dhv:label></a> >
<a href="RicercaUnica.do?command=Search"><dhv:label name="">Risultato ricerca</dhv:label></a> >
Scheda Anagrafica Impresa
</td>
</tr>
</table>


<dhv:container name="<%=nomeContainer %>"  selected="details" object="Operatore" param="<%=param%>"  hideContainer="false">


<center>
<font size="3px">Operazione di variazione titolarita' per lo stabilimento:<br/>
<b><%=StabilimentoDettaglio.getOperatore().getRagioneSociale() %></b><br/>
<i><%=StabilimentoDettaglio.getNumero_registrazione() %></i><br/>
<table class="soggetto">
<tr><th colspan="4">ATTUALE RAPPRESENTANTE LEGALE</th></tr>
<tr><th>NOME</th><td><%=StabilimentoDettaglio.getOperatore().getRappLegale().getNome().toUpperCase() %> <%=StabilimentoDettaglio.getOperatore().getRappLegale().getCognome().toUpperCase() %></td>
<th>LUOGO E DATA DI NASCITA</th><td><%=StabilimentoDettaglio.getOperatore().getRappLegale().getComuneNascita().toUpperCase() %> IL <%=StabilimentoDettaglio.getOperatore().getRappLegale().getDataNascitaString().toUpperCase() %></td></tr>
<tr><th>CODICE FISCALE</th><td><%=StabilimentoDettaglio.getOperatore().getRappLegale().getCodFiscale().toUpperCase() %></td>
<th>INDIRIZZO RESIDENZA</th><td><%=ToponimiList.getSelectedValue(StabilimentoDettaglio.getOperatore().getRappLegale().getIndirizzo().getToponimo()).toUpperCase() %> <%=StabilimentoDettaglio.getOperatore().getRappLegale().getIndirizzo().getVia().toUpperCase() %> <%=StabilimentoDettaglio.getOperatore().getRappLegale().getIndirizzo().getCivico().toUpperCase() %>, <%=StabilimentoDettaglio.getOperatore().getRappLegale().getIndirizzo().getDescrizioneComune().toUpperCase() %> (<%=StabilimentoDettaglio.getOperatore().getRappLegale().getIndirizzo().getDescrizione_provincia().toUpperCase() %>)</td></tr>
</tr>
</table>
</center>
<br/><br/>


<center>

<form id="cambioSoggetto" name="cambioSoggetto" action="CambioSoggettoFisico.do?command=Insert&auto-populate=true" method="post">
	
	
		<table style="height: 100%; width: 87%">
			<tr>
				<td>NOME</td>
				<td><input type="text" size="70" id="nome" name="nome" onchange="svuotaCf()" oncopy="return false" oncut="return false" onpaste="return false" class="required" value="" ></td>
			</tr>
			<tr>
				<td><label for="cognome-2">COGNOME </label></td>
				<td><input type="text" size="70" id="cognome" name="cognome" onchange="svuotaCf()" oncopy="return false" oncut="return false" onpaste="return false" class="required" value="" ></td>
			</tr>
			<tr>
				<td><label for="sesso-2">SESSO </label></td>
				<td><div class="test">
						<input type="radio" name="sesso" id="sesso1" value="M"  onchange="svuotaCf()"
							checked="checked" class="required css-radio"> <label
							for="sesso1" class="css-radiolabel radGroup2">M</label> <input
							type="radio" name="sesso" id="sesso2" value="F"  onchange="svuotaCf()"
							class="required css-radio"> <label for="sesso2"
							class="css-radiolabel radGroup2">F</label>
					</div></td>
			</tr>
			<tr>
				<td><label for="dataN-2">DATA NASCITA </label></td>
				<td><input type="text" size="15" name="dataNascita" value=""
					id="dataNascita2" class="required" placeholder="dd/MM/YYYY">&nbsp;&nbsp;
				</td>
				
			</tr>
			<tr>
				<td><label for="nazioneN-2">NAZIONE NASCITA</label></td>
				<td>
					<%NazioniList.setJsEvent("onchange=\"abilitaCodiceFiscale('nazioneNascita');sbloccoProvincia('nazioneNascita',null,'comuneNascita',null)\"");%> 
					<%=NazioniList.getHtmlSelect("nazioneNascita", 106)%></td>
			</tr>
			<tr>
				<td nowrap>COMUNE NASCITA</td>
				<td><select name="comuneNascita" id="comuneNascita"
					class="required">
						
						<option value="">SELEZIONA COMUNE</option>
				</select> 
				<input type="hidden" name="comuneNascitaTesto" id="comuneNascitaTesto" value="" />
				</td>
			</tr>
			<tr>
				<td>CODICE FISCALE</td>
				<td>
					<span>
					<input type="text" name="codFiscaleSoggetto"
					readonly="readonly" id="codFiscaleSoggetto" class="required" value=""/>
					<input type="button" id="calcoloCF" class="newButtonClass"
					value="CALCOLA CODICE FISCALE"
					onclick="javascript:CalcolaCF(document.forms[0].sesso,document.forms[0].nome,document.forms[0].cognome,document.forms[0].comuneNascitainput,document.forms[0].dataNascita,'codFiscaleSoggetto')"/>
					</span>
				</td>
				<!-- <td>&nbsp;</td> -->
				<!-- <td>
					
				</td> -->
			</tr>
			<!-- <tr>
				
			</tr> -->
			<tr>
				<td><label for="nazioneN-2">NAZIONE RESIDENZA</label></td>
				<td>
					<%NazioniList.setJsEvent("onchange=\"sbloccoProvincia('nazioneResidenza','addressLegaleCountry','addressLegaleCity','addressLegaleLine1')\"");%> 
					<% NazioniList.setJsEvent("onChange=\"abilitaDisabilitaIndirizzo('residenzaRappLegale')\""); %>
					<%=NazioniList.getHtmlSelect("nazioneResidenza", 106)%></td>
			</tr>
			<tr>
				<td colspan="2">
					<font color="red">ATTENZIONE! Posizionarsi sul campo comune per inserire tutto l'indirizzo</font>
				</td>
			</tr>
			<tr>
				<td nowrap>COMUNE RESIDENZA</td>
				<td>
					
				
				
				<input type="hidden"  value="" name="addressLegaleCityId" id="addressLegaleCityId" />

					<input value="" size="50" onclick="selezionaIndirizzo('nazioneResidenza','callBackResidenzaRappLegale',this.value, false, '<%=siglaAsl %>')" type="text" name="addressLegaleCitta" id="addressLegaleCitta" placeholder="DENOMINAZIONE COMUNE"/>

					
					<input type="hidden" name="addressLegaleCityTesto"
					id="addressLegaleCityTesto"  value=""/></td>
			</tr>
			<tr id="addressLegaleCountryTR">
				<td nowrap>PROVINCIA RESIDENZA</td>
				<td>
					<input value="" readonly="true" size="50" type="text" name="addressLegaleCountrySigla" id="addressLegaleCountrySigla" placeholder="DENOMINAZIONE PROVINCIA"/>
			</td>
			</tr>
			<tr>
				<td>INDIRIZZO RESIDENZA</td>
				<td>
					<table class="noborder">
						<tr>
							<td>
							<%
							String toponimoDEfault = "VIA";
							
							%>
							<%=ToponimiList.getHtmlSelect("toponimoResidenza", toponimoDEfault)%>
							
							<input type="hidden" name="toponimoResidenzaId" id="toponimoResidenzaId" />
							
							</td>
							<td>
							
							<select name="addressLegaleLine1" id="addressLegaleLine1" class="required">
							
							
							</select>
<!-- 							<input type="text" name="addressLegaleLine1" -->
<!-- 								id="addressLegaleLine1" class="required" > -->
								
								
								</td>
							<td><input type="text" name="civicoResidenza" readonly
								id="civicoResidenza" size="5" placeholder="NUM." maxlength="15"
								required="required" value="" >
							</td>
							<td>
							<input type="text" name="capResidenza" id="capResidenza" readonly
							onfocus="chkCap(document.getElementById('addressLegaleCity').value,'capResidenza')"  title="DATI TITOLARE/LEGALE RAPPRESENTANTE: CAP INDIRIZZO RESIDENZA non valido. Tornare indietro e correggere il campo"
								size="4" placeholder="CAP" maxlength="5" required="required" value="">
								<input type="hidden" value="Calcola CAP" id="butCapResidenza" 
								onclick="calcolaCap(document.getElementById('addressLegaleCity').value, document.getElementById('toponimoResidenza').value, document.getElementById('addressLegaleLine1input').value, 'capResidenza');" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr style="display:none">
				<td>DOMICILIO DIGITALE<br>(PEC)
				</td>
				<td><input type="text" size="70" name="domicilioDigitalePecSF">
				</td>
			</tr>
		</table>
		
<input type="button" value="CONFERMA" onClick="checkForm(this.form)"/>
<input type="hidden" id="idStabilimento" name="idStabilimento" value="<%=StabilimentoDettaglio.getIdStabilimento()%>"/>
<input type="hidden" id="dataRichiesta" name="dataRichiesta" value="<%=dataRichiesta%>"/>
		
	
	</form>


</dhv:container>
	
	<div style="display: none;"> 
    &nbsp;&nbsp;<img id="calImg" src="images/cal.gif" alt="Popup" class="trigger"> 
	</div>
	
<script type="text/javascript">
	document.getElementById('toponimoResidenza').disabled=true;
</script>
