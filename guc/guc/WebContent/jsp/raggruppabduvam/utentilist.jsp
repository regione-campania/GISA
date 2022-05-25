<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage=""%>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@page import="it.us.web.bean.raggruppabduvam.*"%>
<%@ page import="org.json.*"%>

<jsp:useBean id="listaUtentiBDU" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="listaUtentiVAM" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="esito" class="java.lang.String" scope="request"/>

<%@ include file="../guc/modalWindow.jsp"%>
<%@ include file="../guc/initPage.jsp"%>

<link rel="stylesheet" type="text/css" href="js/jquerypluginTableSorter/css/theme.css"></link>
<link rel="stylesheet" type="text/css" href="js/jquerypluginTableSorter/css/jquery.tablesorter.pages.css"></link>

<script type="text/javascript" src="js/jquerypluginTableSorter/jquery.tablesorter.js"></script>
<script type="text/javascript" src="js/jquerypluginTableSorter/jquery.tablesorter.pager.js"></script>

<script type="text/javascript" src="js/jquerypluginTableSorter/jquery.tablesorter.widgets.js"></script>
<script type="text/javascript" src="js/jquerypluginTableSorter/tableJqueryFilterDialogBduVam.js"></script>

<script>
function checkUtenti(idBDU, idVAM){
	var cfBDU = $("#cfBDU"+idBDU).val();
	var cfVAM = $("#cfVAM"+idVAM).val();
	var aslBDU = $("#aslBDU"+idBDU).val();
	var aslVAM = $("#aslVAM"+idVAM).val();
	
	if (cfBDU != '' && cfVAM != '')
		if (cfBDU.toUpperCase() != cfVAM.toUpperCase()){
			alert('Attenzione. I codici fiscali dei due utenti non coincidono.');
			return false;
		}	
	
		if (aslBDU != aslVAM){
			alert('Attenzione. Le ASL dei due utenti non coincidono.');
			return false;
		}	
	
	return true;
}


function raggruppa(ep){
	
	$("#sistemaRIF").val(ep);
	$("#dialogRaggruppa").dialog("close");
	document.getElementById("formUtenti").submit();
		
} 

function mostra(){ 
	
	var idBDU = '';
	var idVAM = '';
	
	var bdu = document.getElementsByName("utenteBDU");
	for (var i=0; i<bdu.length; i++){
		if (bdu[i].checked){
			idBDU = bdu[i].value;
			break;
		}
	}
	var vam = document.getElementsByName("utenteVAM");
	for (var i=0; i<vam.length; i++){
		if (vam[i].checked){
			idVAM = vam[i].value;
			break;
		}
	}
	
	if (idBDU=='' || idVAM == ''){
		alert("Selezionare un utente per BDU e uno per VAM!");
		return false;
	}
	
	if (checkUtenti(idBDU, idVAM) == false)
		return false;
	
	var usernameBDU = $("#usernameBDU"+idBDU).val();
	var usernameVAM = $("#usernameVAM"+idVAM).val();
	
	$("#labelUsername").html("<b>UTENTE BDU</b>: "+usernameBDU+"<br/> <b>UTENTE VAM</b>: "+usernameVAM);
	
	loadModalWindow();
	$("#dialogRaggruppa").dialog({
	    resizable: true,
	    height: 'auto',
	    modal : true,
	    width: 'auto',
        overflow:'scroll'
        });
}
function nascondi(){
	loadModalWindowUnlock();
	$("#dialogRaggruppa").dialog("close");
}

</script>

<% if (esito!=null && !esito.equals("")){
	JSONObject jsonEsito = new JSONObject(esito); %>
	
	<center>
	<table style="border: 1px solid black;  border-spacing: 40px 10px;" cellpadding="10" cellspacing="10" class="details">
	<tr><th colspan="2">ESITO OPERAZIONE</th></tr>
	
	<% if (jsonEsito.has("Esito")){ %>
	<tr><th>ESITO</th> <td><%=jsonEsito.get("Esito")%></td></tr>
	<%} %>
	<% if (jsonEsito.has("DescrizioneErrore") && !jsonEsito.get("DescrizioneErrore").equals("")){ %>
	<tr><th>DESCRIZIONE ERRORE</th> <td><%=jsonEsito.get("DescrizioneErrore")%></td></tr>
	<%} %>
	<% if (jsonEsito.has("username")){ %>
	<tr><th>USERNAME</th> <td><%=jsonEsito.get("username")%></td></tr>
	<%} %>
	
	</table>
	</center>
<br/><br/>
<% } %>


<div align="center">
		<a href="Index.us" onClick="loadModalWindow();" style="margin: 0px 0px 0px 50px; font-size: 20px">Indietro</a>
	</div>
<br/><br/>

<form method="post" action = "raggruppabduvam.GroupBduVam.us" id="formUtenti" name="formUtenti">

<center>
<input type="button" value="RAGGUPPA I DUE UTENTI SELEZIONATI" onClick="mostra()" style="width:500px; height: 50px; font-size: 20px"/>
</center>

<table style="width:100%" cellpadding="10" cellspacing="10">
<col width="50%">
<tr><td valign="top" style="border: 1px solid black; background: lightblue">

<center><b><font size="20px">BDU</font></b></center>
<table  class="tablesorter" id="tablelistautentibdu" style="width:100%">
<thead>
<tr class="tablesorter-headerRow" role="row">
<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="CODICE FISCALE" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">Codice Fiscale</div></th>
<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="USERNAME" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">Username</div></th>
<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="ASL" class="first-name filter-select"><div class="tablesorter-header-inner">ASL</div></th>
<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="RUOLO" class="first-name filter-select"><div class="tablesorter-header-inner">Ruolo</div></th>
<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRO" class="filter-false tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner"></div></th>
</tr>
</thead>
<tbody aria-relevant="all" aria-live="polite">

<% for (int i = 0; i< listaUtentiBDU.size(); i++) {  
Utente u = (Utente) listaUtentiBDU.get(i);
%>
<tr style="border:1px solid black;">
<td style="border:1px solid black;"><%=u.getCodiceFiscale() %></td>
<td style="border:1px solid black;"><%=u.getUsername() %></td>
<td style="border:1px solid black;"><%=u.getDescrizioneAsl() %></td>
<td style="border:1px solid black;"><%=u.getDescrizioneRuolo() %></td>
<td style="border:1px solid black;">
<input type="radio" name="utenteBDU" id="utenteBDU<%=u.getIdUtente()%>" value="<%=u.getIdUtente()%>"/>
<input type="hidden" name="ruoloBDU<%=u.getIdUtente()%>" id="ruoloBDU<%=u.getIdUtente()%>" value="<%=u.getIdRuolo()%>"/>
<input type="hidden" name="usernameBDU<%=u.getIdUtente()%>" id="usernameBDU<%=u.getIdUtente()%>" value="<%=u.getUsername()%>"/>
<input type="hidden" name="cfBDU<%=u.getIdUtente()%>" id="cfBDU<%=u.getIdUtente()%>" value="<%=u.getCodiceFiscale()%>"/>
<input type="hidden" name="aslBDU<%=u.getIdUtente()%>" id="aslBDU<%=u.getIdUtente()%>" value="<%=u.getIdAsl()%>"/>
</td>
</tr>
<%} %>
</tbody>
</table>

</td>

<td valign="top" style="border: 1px solid black; background: lightsalmon">

<center><b><font size="20px">VAM</font></b></center>
<table  class="tablesorter" id="tablelistautentivam" style="width:100%">
<thead>
<tr class="tablesorter-headerRow" role="row">
<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="CODICE FISCALE" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">Codice Fiscale</div></th>
<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="USERNAME" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">Username</div></th>
<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="ASL" class="first-name filter-select"><div class="tablesorter-header-inner">ASL</div></th>
<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="RUOLO" class="first-name filter-select"><div class="tablesorter-header-inner">Ruolo</div></th>
<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRO" class="filter-false tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner"></div></th>
</tr>
</thead>
<tbody aria-relevant="all" aria-live="polite">

<% for (int i = 0; i< listaUtentiVAM.size(); i++) {  
Utente u = (Utente) listaUtentiVAM.get(i);
%>
<tr style="border:1px solid black;">
<td style="border:1px solid black;"><%=u.getCodiceFiscale() %></td>
<td style="border:1px solid black;"><%=u.getUsername() %></td>
<td style="border:1px solid black;"><%=u.getDescrizioneAsl() %></td>
<td style="border:1px solid black;"><%=u.getDescrizioneRuolo() %></td>
<td style="border:1px solid black;">
<input type="radio" name="utenteVAM" id="utenteVAM<%=u.getIdUtente()%>" value="<%=u.getIdUtente()%>"/>
<input type="hidden" name="ruoloVAM<%=u.getIdUtente()%>" id="ruoloVAM<%=u.getIdUtente()%>" value="<%=u.getIdRuolo()%>"/>
<input type="hidden" name="usernameVAM<%=u.getIdUtente()%>" id="usernameVAM<%=u.getIdUtente()%>" value="<%=u.getUsername()%>"/>
<input type="hidden" name="cfVAM<%=u.getIdUtente()%>" id="cfVAM<%=u.getIdUtente()%>" value="<%=u.getCodiceFiscale()%>"/>
<input type="hidden" name="aslVAM<%=u.getIdUtente()%>" id="aslVAM<%=u.getIdUtente()%>" value="<%=u.getIdAsl()%>"/>
</td>
</tr>
<%} %>
</tbody>
</table>

</td>
</tr>
</table>

<input type="hidden" id="sistemaRIF" name="sistemaRIF" value=""/>

</form>	



<div id="dialogRaggruppa" style="display: none;" title="RAGGRUPPAMENTO UTENTI BDU/VAM">
<div align="center">Raggruppare i due utenti selezionati?

<br/><br/>

<label id="labelUsername"></label>

<br/><br/>

<input type="button" id="buttonAnnulla" value="ANNULLA" style="width:100px; height: 50px; font-size: 15px" onClick="nascondi()"/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type="button" id="buttonRaggruppaBDU" value="SI, USA UTENTE BDU COME UTENTE PRINCIPALE" style="width:400px; height: 50px; font-size: 15px" onClick="raggruppa('bdu')"/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type="button" id="buttonRaggruppaVAM" value="SI, USA UTENTE VAM COME UTENTE PRINCIPALE" style="width:400px; height: 50px; font-size: 15px" onClick="raggruppa('vam')"/>
</div>
</div>



