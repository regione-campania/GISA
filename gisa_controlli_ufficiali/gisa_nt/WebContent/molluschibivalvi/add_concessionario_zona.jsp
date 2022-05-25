<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.molluschibivalvi.base.*"%>

<%@page import="java.util.Date"%><jsp:useBean id="OrgDetails" class="org.aspcfs.modules.molluschibivalvi.base.Organization" scope="request" />
<jsp:useBean id="Concessionario" class="org.aspcfs.modules.molluschibivalvi.base.Concessionario" scope="request" />
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application" />
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request" />
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/popLookupSelect.js"></SCRIPT>

<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<%if(request.getAttribute("Inserito")!=null)
	{%>
	
	<script>
	opener.location.href='MolluschiBivalvi.do?command=Details&orgId=<%=OrgDetails.getId()%>';
	window.close();
	
	</script>
	
	<%} %>

<script>


function checkForm()
{
	formtest = true ;
	msg ='Controllare di aver selezionato i seguenti campi : \n';
	if(document.addAccount.numConcessione.value =='')
	{
		formtest = false ;
		msg += 'Il campo numero concessione deve essere popolato\n'
	}
	if(document.addAccount.dataConcessione.value =='' || document.addAccount.dataConcessione.value =='-1')
	{
		formtest = false ;
		msg  += 'Il campo data concessione deve essere popolato\n'
	}
	if(document.addAccount.dataScadenza.value =='' || document.addAccount.dataScadenza.value =='-1')
	{
		formtest = false ;
		msg += 'Il campo data Scadenza deve essere popolato\n'
	} 
	

	if(formtest==true)
		document.addAccount.submit();
	else
	{
		alert(msg);
		return false ;
	}
}
</script>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>
<%@ include file="../initPage.jsp"%>

<form id="addAccount" name="addAccount"
	action="MolluschiBivalvi.do?command=AggiungiConcessionario&auto-populate=true"
	method="post">

<table class="trails" cellspacing="0">
	<tr>
		<td width="100%"><a href="MolluschiBivalvi.do">Molluschi
		Bivalvi</a> > Aggiungi Concessionario</td>
	</tr>
</table>

<input type="button"
	value="Inserisci"
	name="Save" onClick="checkForm()"> 

<br />
<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="details">
	<tr>
		<th colspan="2"><strong>Aggiungi Dati Concessione</strong></th>
	</tr>

	<tr>
		<td nowrap="nowrap" class="formLabel">Zona di Produzione</td>
		<td>
		<input type = "hidden" name = idZona value = "<%=OrgDetails.getOrgId() %>">
		<%=OrgDetails.getName() %>
		</td>
	</tr>
	
	<tr>
		<td nowrap="nowrap" class="formLabel">Concessionario</td>
		<td>
		<input type = "hidden" name = "idConcessionario" value = "<%=Concessionario.getId() %>">
		<%=Concessionario.getName() %>
		</td>
	</tr>

	
	<tr>
		<td nowrap="nowrap" class="formLabel">Numero Concessione</td>
		<td>
		<input type = "text" name = "numConcessione" value = "">
		<font color="red">*</font>
		</td>
	</tr>

		<tr id = "zc2">
		<td nowrap class="formLabel" > Data Concessione</td>
		<td>
		
			<input readonly type="text" id="dataConcessione" name="dataConcessione" value = "" size="10" />
		<a href="#" onClick="cal19.select(document.forms[0].dataConcessione,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
       <font color="red">*</font>
       </td>
	</tr>
	
	<tr id = "zc2">
		<td nowrap class="formLabel" > Data Scadenza</td>
		<td>
		
			<input readonly type="text" id="dataScadenza" name="dataScadenza" value = "" size="10" />
		<a href="#" onClick="cal19.select(document.forms[0].dataScadenza,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
       <font color="red">*</font>
       </td>
	</tr>
	
</table>



<br>
<input type="button"
	value="Inserisci"
	name="Save" onClick="checkForm()"> 

<input type = "hidden" name = "dataSospensione" value = "" >
<input type = "hidden" name = "idSospensione" value = "-1" >
</form>

