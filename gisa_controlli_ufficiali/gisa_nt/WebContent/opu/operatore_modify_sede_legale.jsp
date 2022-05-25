<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ taglib uri="/WEB-INF/taglib/systemcontext-taglib.tld" prefix="sc"%>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*"%>

<jsp:useBean id="Titolo" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Operatore" class="org.aspcfs.modules.opu.base.Operatore" scope="request" />
<jsp:useBean id="soggettoAdded" class="org.aspcfs.modules.opu.base.SoggettoFisico" scope="request" />
<jsp:useBean id="indirizzoAdded" class="org.aspcfs.modules.opu.base.Indirizzo" scope="request" />
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="provinciaAsl" class="org.aspcfs.modules.accounts.base.Provincia" scope="request" />
<jsp:useBean id="stabilimento" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request" />
<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<!-- RELATIVO AL NUOVO CALENDARIO CON MESE E ANNO FACILMENTE MODIFICABILI -->
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();
</SCRIPT>

<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/opu.css" />

<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/opu.js"></SCRIPT>

<script type="text/javascript">

$(function() {
  
    $( "#searchcodeIdprovincia" ).combobox();
    $( "#searchcodeIdComune" ).combobox();
    $( "#via" ).combobox();
    $( "#addressLegaleCity" ).combobox();
    $( "#addressLegaleLine1" ).combobox();

});
function checkForm()
{
	msg = "Attenzione Controllare di aver compilato i seguenti campi\n" ;

	
   


	

	document.forms[0].doContinue.value="true";
	

	

		if(document.forms[0].searchcodeIdprovincia.value=='-1')
		{
			document.forms[0].doContinue.value="false";
			msg+= "- Campo provincia sede legale richiesto \n" ;
		}	
	if(document.forms[0].via.value=='-1' && document.forms[0].viaTesto.value == '')
	{
		document.forms[0].doContinue.value="false";
		msg+= "- Campo indirizzo sede legale richiesto \n" ;
	}
	if(document.forms[0].searchcodeIdComune.value=='-1')
	{
		document.forms[0].doContinue.value="false";
		msg+= "- Campo comune sede legale richiesto \n" ;
	}
	

	
	

	
	

	if(document.forms[0].doContinue.value=="false")
	{
		$('#mask').hide();
		$('.window').hide();
			alert(msg);
	}
	else
	{
		document.forms[0].doContinue.value="true";
		document.forms[0].submit();
	}
		

}

</script>

<%@ include file="../initPage.jsp"%>

<dhv:evaluate if="<%=!isPopup(request)%>">
	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
		
 
		
		
			<td width="100%">
			
			<dhv:label name=""><a href="<%=StabilimentoDettaglio.getAction()+".do?command=SearchForm" %>" >Anagrafica Stabilimenti </a>-><a  href="<%=StabilimentoDettaglio.getAction()+".do?command=Details&stabId="+StabilimentoDettaglio.getIdStabilimento()%>">Scheda Impresa</a> ->Variazione Sede Legale </dhv:label>
			
			</td>
		</tr>
	</table>
	<%-- End Trails --%>
</dhv:evaluate>

<%
	boolean popUp = false;
	if (request.getParameter("popup") != null) {
		popUp = true;
	}
%> <%=addHiddenParams(request, "actionSource|popup")%> 




<%
String param = "stabId="+StabilimentoDettaglio.getIdStabilimento()+"&opId=" + StabilimentoDettaglio.getIdOperatore();
%>
<%
String nomeContainer = StabilimentoDettaglio.getContainer();

request.setAttribute("Operatore",StabilimentoDettaglio.getOperatore());
%>
<dhv:container name="<%=nomeContainer %>"  selected="variazioneslimpresa" object="Operatore" param="<%=param%>"  hideContainer="false">

<form id="addOperatore" name="addOperatore" action="<%=StabilimentoDettaglio.getAction() %>.do?command=UpdateSedeLegale&auto-populate=true" method="post">
<input type ="hidden" name = "doContinue" value = "true" >
<input type = "hidden" name = "idStabilimento" value = "<%=StabilimentoDettaglio.getIdStabilimento() %>"/>
<input type = "hidden" name = "idOperatore" value = "<%=StabilimentoDettaglio.getIdOperatore() %>"/>
<input type="button" value="Inserisci" name="Save" onClick="checkForm()">
<dhv:evaluate if="<%=popUp%>">
	<input type="button" value="Annulla" onClick="javascript:self.close();">
</dhv:evaluate>
<br />
<br />



<%
if (request.getAttribute("Exist") != null && !("").equals(request.getAttribute("Exist"))) 
{
%> 
<font color="red"><%=(String) request.getAttribute("Exist")%></font>
<%
}
%> 

<div id="info_sede">

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	<tr>
		<th colspan="2">
			<strong><dhv:label name="<%="opu.sede_legale_gisa"%>"></dhv:label></strong>
		</th>
	</tr>

			<input type = "hidden" name = "inregione"  id="inregione" value = "no">

	<tr>

		<td nowrap class="formLabel">
			<dhv:label name="opu.sede_legale.provincia"></dhv:label>
		</td>
		<td>
			<select name="searchcodeIdprovincia" id="searchcodeIdprovincia">
				<option value="-1">Inserire le prime 4 lettere</option>
			</select>
			
			<input type="hidden" name="searchcodeIdprovinciaTesto" id="searchcodeIdprovinciaTesto" />
			<font color="red">(*)</font> 
		</td>
	</tr>
	
	<tr>
		<td nowrap class="formLabel" name="province" id="province">
			<dhv:label name="opu.sede_legale.comune"></dhv:label>
		</td>
		<td>
			<select name="searchcodeIdComune" id="searchcodeIdComune">
				<option value="-1">Inserire le prime 4 lettere</option>
			</select>
			
			<input type="hidden" name="searchcodeIdComuneTesto" id="searchcodeIdComuneTesto" />
			<font color="red">(*)</font>
		</td>
	</tr>
	
	<tr>
		<td nowrap class="formLabel">
			<dhv:label name="opu.sede_legale.indirizzo"></dhv:label>
		</td>
		<td>
			<select name="via" id="via"> 
				<option value="-1" selected="selected">Inserire le prime 4 lettere</option>
			</select>
			
			<font color="red">(*)</font> 
			<input type="hidden" name="viaTesto" id="viaTesto" />
		</td>
	</tr>
	
	<tr>
		<td nowrap class="formLabel">
			<dhv:label name="opu.sede_legale.co"></dhv:label>
		</td>
		<td>
			<input type="text" size="40" name="presso" maxlength="80" value="<%=""%>">
		</td>
	</tr>


	<tr class="containerBody">
		<td class="formLabel" nowrap>
			<dhv:label name="opu.sede_legale.latitudine"></dhv:label>
		</td>
		<td>
			<input type="text" id="latitudine" name="latitudine" size="30" value="">
		</td>
	</tr>
	
	<tr class="containerBody">
		<td class="formLabel">
			<dhv:label name="opu.sede_legale.longitudine"></dhv:label>
		</td>
		<td>
			<input type="text" id="longitudine" name="longitudine" size="30" value="">
		</td>
	</tr>

	
</table>
</div>


<input type="button" value="Inserisci" name="Save" onClick="checkForm()">
<dhv:evaluate if="<%=popUp%>">
	<input type="button" value="Annulla" onClick="javascript:self.close();">
</dhv:evaluate>

</form>




</dhv:container>

