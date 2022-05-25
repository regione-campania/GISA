<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="nome" class="java.lang.String"	scope="request" />
<jsp:useBean id="cognome" class="java.lang.String" scope="request" />
<jsp:useBean id="comune" class="java.lang.String" scope="request" />
<jsp:useBean id="data" class="java.lang.String"	scope="request" />
<jsp:useBean id="sesso" class="java.lang.String" scope="request" />
<jsp:useBean id="cf" class="java.lang.String" scope="request" />

<script type="text/javascript" src="javascript/jquery.miny_1.7.2.js"></script>

<link href="javascript/datepicker/jquery.datepick.css" rel="stylesheet">
<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/opu.css" />
<link rel="stylesheet" type="text/css" href="css/jquery.calendars.picker.css">
<!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>-->
<script f src="dwr/interface/SuapDwr.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<script src="javascript/datepicker/jquery.plugin.js"></script>
<script src="javascript/datepicker/jquery.datepick.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/gestoreCodiceFiscale.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.plus.js"></script>
<script type="text/javascript" src="javascript/jquery.plugin.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.picker.js"></script>
<script src="javascript/parsedate.js"></script>
<script type="text/javascript" src="javascript/jquery.steps_modify.js"></script>
<script src="javascript/jquery-ui.js"></script>
<SCRIPT src="javascript/sintesis.js"></SCRIPT>
<SCRIPT src="javascript/upload.js"></SCRIPT>
<SCRIPT src="javascript/suapUtil.js"></SCRIPT>
<script src="javascript/jquery.form.js"></script>

<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
   var cal99 = new CalendarPopup();
   cal99.showYearNavigation();
   cal99.showYearNavigationInput();
   cal99.showNavigationDropdowns();
</SCRIPT> 

<%@ page import="java.util.*"%>
<%@ include file="../initPage.jsp"%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<script>
$(function() {
    $( "#comuneNascita" ).combobox();
});
</script>



<script>

function showHideSettaCF(){
	var cf = document.getElementById("cf").value;
	if (cf == '')
		document.getElementById("settaCF").style.display="none";
	else
		document.getElementById("settaCF").style.display="block";
}

function SettaCF(){
	var cf = document.getElementById("cf").value;
	window.opener.document.getElementById('<%=cf%>').value = cf;
	window.close();
}
</script>


<form>
<table>

<tr>
<th colspan="2">Generazione codice fiscale</th>
</tr>

<tr>
<td class="formLabel">NOME</td> 
<td><input type="text" name="nome" id="nome" value="<%=nome%>"/></td>
</tr>

<tr>
<td class="formLabel">COGNOME</td> 
<td><input type="text" name="cognome" id="cognome" value="<%=cognome%>"/></td>
</tr>

<tr>
<td class="formLabel">SESSO</td>
<td><input type="radio" name="sesso" id="sessoM" value="M" <%= ( "M".equalsIgnoreCase(sesso) ? "checked" : "") %>/> M <input type="radio" name="sesso" id="sessoF" value="F" <%= ( "F".equalsIgnoreCase(sesso) ? "checked" : "") %>/> F</td>
</tr>

<tr>
<td class="formLabel">DATA NASCITA</td>
<td><input type="text" size="15" readonly name="dataNascita" id="dataNascita" placeholder="dd/MM/YYYY" value="<%=data%>"></td>
</tr>
<div style="display: none;">&nbsp;&nbsp;<img id="calImg" src="images/cal.gif" alt="Popup" class="trigger"></div>
<script>$(function() { $('#dataNascita').datepick({dateFormat: 'dd/mm/yyyy', maxDate: 0, showOnFocus: false, showTrigger: '#calImg'});});</script>

<tr>
<td class="formLabel">COMUNE NASCITA</td>
<td><select name="comuneNascita" id="comuneNascita">

<%if (!comune.equals("")){ %>
<option value="<%=comune%>"><%=comune %></option>
<%} %>

<option value="">SELEZIONA COMUNE</option></select><input type="hidden" name="comuneNascitaTesto" id="comuneNascitaTesto" /></td>
</tr>

<tr>
<td class="formLabel">CODICE FISCALE</td>
<td><input type="text" name="cf"  value="" readonly="readonly" id="cf"/> <input type="button" id="calcoloCF" class="newButtonClass" value="CALCOLA CODICE FISCALE" onclick="javascript:CalcolaCF(document.forms[0].sesso,document.forms[0].nome,document.forms[0].cognome,document.getElementById('comuneNascitainput'),document.forms[0].dataNascita,'cf'); showHideSettaCF();"></input></td>
</tr>

<tr>
<td colspan="2" align="center">
<input type="button" id="settaCF" class="newButtonClass" value="USA CODICE FISCALE E CHIUDI FINESTRA" onclick="SettaCF()" style="display:none; background-color: green"></input>
</td>
</tr>

</table>
</form>

<i>Dal momento che potrebbero esserci casi di omocodia, per essere certi che il Codice Fiscale calcolato automaticamente sia corretto, l'operatore può verificarlo in Anagrafe Tributaria al seguente link:</i><br/><br/>

<center>
<a href="https://telematici.agenziaentrate.gov.it/VerificaCF/" target="_blank">https://telematici.agenziaentrate.gov.it/VerificaCF/</a>
</center>

