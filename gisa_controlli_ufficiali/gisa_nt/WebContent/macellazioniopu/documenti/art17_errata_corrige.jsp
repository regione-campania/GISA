<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@page import="java.net.InetAddress"%>
  <%@ page import="java.util.*"%>
  <%@page import="org.aspcfs.modules.accounts.base.OrganizationAddress"%>
 <jsp:useBean id="OrgDetails" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
  <jsp:useBean id="nomeEsercente" class="java.lang.String" scope="request"/>
  <jsp:useBean id="indirizzoEsercente" class="java.lang.String" scope="request"/>
 <jsp:useBean id="Capo" class="org.aspcfs.modules.macellazioniopu.base.Capo" scope="request"/>
 <jsp:useBean id="aslList" class="org.aspcfs.utils.web.LookupList"	scope="request" />
 <jsp:useBean id="specieList" class="org.aspcfs.utils.web.LookupList" scope="request" />
  <jsp:useBean id="userIp" class="java.lang.String" scope="request"/>
   <jsp:useBean id="userName" class="java.lang.String" scope="request"/>
 <jsp:useBean id="timeNow" class="java.lang.String" scope="request"/>
<jsp:useBean id="art17" class="org.aspcfs.modules.macellazioniopu.base.Art17" scope="request"/>

<jsp:useBean id="nomeMacello" class="java.lang.String" scope="request"/>
<jsp:useBean id="comuneMacello" class="java.lang.String" scope="request"/>
<jsp:useBean id="approvalNumber" class="java.lang.String" scope="request"/>
<jsp:useBean id="aslMacello" class="java.lang.String" scope="request"/>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../../initPage.jsp" %>
<link rel="stylesheet" documentale_url="" href="css/moduli_print.css" type="text/css" media="print" />
<link rel="stylesheet" type="text/css" media="screen" documentale_url="" href="css/moduli_screen.css">
<!-- RELATIVO AL NUOVO CALENDARIO CON MESE E ANNO FACILMENTE MODIFICABILI -->
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>
<script src="javascript/geocodifica.js" type="text/javascript" language="JavaScript"></script>
<script src="dwr/interface/Geocodifica.js" type="text/javascript" language="JavaScript"></script>
<script src="dwr/engine.js" type="text/javascript" language="JavaScript"></script>
<script> function gestioneCb(scelta){
	
	var cb = document.getElementById('cb_'+scelta);
	var errati =  document.getElementById('div_'+scelta+'_errati');
	var corretti =  document.getElementById('div_'+scelta+'_corretti');
	if (cb.checked){
		errati.style.visibility='visible';
		corretti.style.visibility='visible';
	}
	else
		{
		errati.style.visibility='hidden';
		corretti.style.visibility='hidden';
		}

}

function checkForm(){
	var message = '';
	
	// Controlli su campi non nulli
	
	if (document.getElementById('sottoscritto').value=='')
		message = message+'\n Compilare il campo Sottoscritto';
	if (document.getElementById('motivo').value=='')
		message = message+'\n Compilare il campo Motivo';
	if (document.getElementById('cb_matricola').checked && document.getElementById('matricola').value =='')
		message = message+'\n Compilare il campo Matricola';
	if (document.getElementById('cb_datanascita').checked && document.getElementById('datanascita').value =='')
		message = message+'\n Compilare il campo Data di nascita';
// 	if (document.getElementById('cb_specie').checked && document.getElementById('specie').value =='')
// 		message = message+'\n Compilare il campo Specie';
 	if (document.getElementById('cb_sesso').checked && document.getElementById('sesso').value =='')
		message = message+'\n Compilare il campo Sesso';
	if (document.getElementById('cb_mod4').checked && document.getElementById('mod4').value =='')
		message = message+'\n Compilare il campo Riferimento Mod4';
	if (document.getElementById('cb_altro').checked && document.getElementById('altro_errato').value =='')
		message = message+'\n Compilare il campo Altro (errato)';
	if (document.getElementById('cb_altro').checked && document.getElementById('altro_corretto').value =='')
		message = message+'\n Compilare il campo Altro (corretto)';
	
	// Controlli su campi diversi da quelli errati
	
	if (document.getElementById('cb_matricola').checked && document.getElementById('matricola').value == document.getElementById('matricola_errata').value)
 		message = message+'\n Selezionare una Matricola diversa da quella errata.';
	if (document.getElementById('cb_datanascita').checked && document.getElementById('datanascita').value == document.getElementById('datanascita_errata').value )
 		message = message+'\n Selezionare una Data di Nascita diversa da quella errata.';
	if (document.getElementById('cb_sesso').checked && document.getElementById('sesso').value ==  document.getElementById('sesso_errato').value)
 		message = message+'\n Selezionare Sesso diverso da quello errato.';
	if (document.getElementById('cb_specie').checked && document.getElementById('specie').value == document.getElementById('specie_errata').value)
 		message = message+'\n Selezionare una Specie diversa da quella errata.';
	if (document.getElementById('cb_mod4').checked && document.getElementById('mod4').value ==document.getElementById('mod4_errato').value)
		message = message+'\n Selezionare un Riferimento Mod4 diverso da quello errato';
	
	if (message!='')
		{
		alert(message);
		return false;
		}
	return true;
}
</script>

<script>
function gestisciCapoEsistente(capo){

	//var form = document.main;
	if( capo.esistente )
	{
		//form.capo_esistente.value = 'si';
		alert( "Matricola " + capo.matricola + " già esistente" );
		document.getElementById("matricola").value="";
		
	}
	else
	{
		//form.capo_esistente.value = 'no';
		Geocodifica.getCapo( capo.matricola, getDatiCapo );
	}
	
}

function getDatiCapo( data )
{
	var matricola = document.getElementById("matricola").value;
	if(data.errore == 0){
		alert( "Capo " + matricola + " presente in BDN. Dati recuperati." );
		document.getElementById("datanascita").value=data.data_nascita;
		if (data.sesso){
			document.getElementById("sesso").value="M";
		}
		else {
			document.getElementById("sesso").value="F";
		}
// 		form.specie_from_bdn.value = data.specie;
		document.getElementById("specie").value=data.specie;
		
	}
	else if( data.errore == 1 ){
		alert( "Capo " + matricola + " non in BDN" );
	}
	else{
		alert( "I web services della BDN non sono al momento disponibili." );
	}

}

</script>
<html>
<body>

<table width="100%"">
<col width="33%"> <col width="33%">
<tr>
<td>
<div class="boxIdDocumento"></div><br/>
<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div>
<i><%=userIp %> - <%=userName %></i>
</td>
<td></td>
<td><div align="right"><img style="text-decoration: none;" height="80" documentale_url="" src="gestione_documenti/schede/images/<%=aslList.getSelectedValue(aslMacello).toLowerCase() %>.jpg" /></div></td>
</tr>

</table>

<br/>

<br/>

<center><b>Regione Campania<br/>
Azienda Sanitaria Locale <%=aslList.getSelectedValue(aslMacello)%><br/>
Servizio Veterinario<br/><br/>
ISPEZIONE DELLE CARNI</br/><br/>
<font size="5px"><U>ERRATA CORRIGE PER ART.17 (R.D. 20/12/1928, N. 3298) *</U></font></b></center>
<br/><br/>

<form method="post" name="form2" action="MacellazioniDocumentiOpu.do?command=SalvaModuleErrataCorrigeArt17">
<input type="hidden" id="idMacello" name="idMacello" value="<%=Capo.getId_macello()%>"/>
<input type="hidden" id="idCapo" name="idCapo" value="<%=Capo.getId()%>"/>
<input type="hidden" id="dataMacellazione" name="dataMacellazione" value="<%=toDateasString(Capo.getDataSessioneMacellazione()) %>"/>
<center>


<% String numArt17 ="";
numArt17 = art17.getProgressivo() + "/" + art17.getAnno() + "/" + approvalNumber;
%>
Il sottoscritto <input class="editField" type="text" id="sottoscritto" name="sottoscritto" size="30"/>, relativamente all'art.17 n° <%=numArt17 %>, elaborato e rilasciato presso il macello <%=nomeMacello %> del comune di <%=comuneMacello %>, per la data di macellazione <%=toDateasString(Capo.getDataSessioneMacellazione()) %> 
ed esercente <%=nomeEsercente %> <%=indirizzoEsercente%><br/><br/>
<b>DICHIARA CHE</b></center><br/><br/>
È stata erroneamente digitata l'informazione riguardante i campi del capo con matricola <b><u><%=Capo.getCd_matricola() %></u></b>:<br/><br/>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
<col width="33%"><col width="33%">
<tr><th style="border:1px solid black;">Dati</th>
<th style="border:1px solid black;">Dati errati</th>
<th style="border:1px solid black;">Dati corretti</th></tr>

<tr>
<td style="border:1px solid black;"><input type="checkbox" id="cb_matricola" name="cb_matricola" onClick="gestioneCb('matricola')"> La matricola del capo</td>
<td><div id="div_matricola_errati" style="visibility:hidden"><input class="layout" readonly type="text" id="matricola_errata" name="matricola_errata" value="<%=Capo.getCd_matricola() %>"/></div>&nbsp;</td>
<td style="border:1px solid black;"><div id="div_matricola_corretti" style="visibility:hidden"><input class="editField" type="text" id="matricola" name="matricola" size="20" onchange="Geocodifica.isCapoEsistente( this.value, gestisciCapoEsistente );"/></div></td>
</tr>
<tr>
<td style="border:1px solid black;"><input type="checkbox" id="cb_datanascita" name="cb_datanascita" onClick="gestioneCb('datanascita')"> La data di nascita del capo</td>
<td style="border:1px solid black;"><div id="div_datanascita_errati" style="visibility:hidden"><input class="layout" readonly type="text" id="datanascita_errata" name="datanascita_errata" value="<%=toDateasString(Capo.getCd_data_nascita()) %>"/> </div> &nbsp; </td>
<td style="border:1px solid black;"><div id="div_datanascita_corretti" style="visibility:hidden"><input class="editField" readonly type="text" id="datanascita" name="datanascita" size="10"/>
<a href="#" onClick="cal19.select(document.forms[0].datanascita,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a> 
</div> &nbsp; </td>
</tr>
<tr>
<td style="border:1px solid black;"><input type="checkbox" id="cb_specie" name="cb_specie" onClick="gestioneCb('specie')"> La specie del capo</td>
<td style="border:1px solid black;"><div id="div_specie_errati" style="visibility:hidden"><%=specieList.getSelectedValue( Capo.getCd_specie() ) %></div> &nbsp; </td>
<input type="hidden" id="specie_errata" name="specie_errata" value="<%=Capo.getCd_specie()%>"/>
<td style="border:1px solid black;"><div id="div_specie_corretti" style="visibility:hidden">	<%=specieList.getHtmlSelect("specie", Capo.getCd_specie())%></div> &nbsp; </td>
</tr>
<tr>
<td style="border:1px solid black;"><input type="checkbox" id="cb_sesso" name="cb_sesso" onClick="gestioneCb('sesso')"> Il sesso del capo</td>
<td style="border:1px solid black;"><div id="div_sesso_errati" style="visibility:hidden"><input class="layout" readonly type="text" id="sesso_errato" name="sesso_errato" size="1" value="<%=Capo.isCd_maschio() ? ("M") : ("F") %>"/></div> &nbsp; </td>
<td style="border:1px solid black;"><div id="div_sesso_corretti" style="visibility:hidden"><input class="layout" readonly type="text" id="sesso" name="sesso" size="1" value="<%=Capo.isCd_maschio() ? ("F") : ("M") %>"/> </div> &nbsp; </td>
</tr>
<tr>
<td style="border:1px solid black;"><input type="checkbox" id="cb_mod4" name="cb_mod4" onClick="gestioneCb('mod4')"> Il riferimento al Mod. 4 è</td>
<td style="border:1px solid black;"><div id="div_mod4_errati" style="visibility:hidden"><input class="layout" readonly type="text" id="mod4_errato" name="mod4_errato" value="<%=Capo.getCd_mod4() %>"/></div> &nbsp;</td>
<td style="border:1px solid black;"><div id="div_mod4_corretti" style="visibility:hidden"><input class="editField" type="text" id="mod4" name="mod4" size="20"/></div> &nbsp; </td>
</tr>
<tr>
<td style="border:1px solid black;"><input type="checkbox" id="cb_altro" name="cb_altro" onClick="gestioneCb('altro')"> Altro</td>
<td style="border:1px solid black;"><div id="div_altro_errati" style="visibility:hidden"><input class="editField" type="text" id="altro_errato" name="altro_errato" size="20"/></div> &nbsp; </td>
<td style="border:1px solid black;"><div id="div_altro_corretti" style="visibility:hidden"><input class="editField" type="text" id="altro_corretto" name="altro_corretto" size="20"/></div> &nbsp; </td>
</tr>
</table>
<br/>
Motivo della correzione: <input class="editField" type="text" id="motivo" name="motivo" size="70" value=""/> <br/><br/>

<table width="100%">
<col width="50%">
<tr>
<td>Data <br/>
<%=timeNow %></td>
<td><div align="right">Il veterinario ufficiale dell'ASL <%=aslList.getSelectedValue(aslMacello)%></div></td>
</table>
<br/><br/><br/>
* <i>Il presente modulo rappresenta una notifica documentale. Eventuali correzioni sui dati dovranno essere effettuate separatamente.</i>
<br/><br/>
<input id="salvaId" type="button" value ="Salva e stampa" onclick="if (checkForm(this.form)){if (confirm('ATTENZIONE! Il capo sarà aggiornato sulla base dell\'Errata Corrige generata. L\'utente si assume la responsabilità delle modifiche richieste.')){this.form.submit()}}" />
</form>
</body>
</html>