<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="StabilimentoDettaglio"
	class="ext.aspcfs.modules.apiari.base.Stabilimento" scope="request" />


<jsp:useBean id="TipoAttivitaApi"
	class="org.aspcfs.utils.web.LookupList" scope="request" />

<jsp:useBean id="LookupStati" class="org.aspcfs.utils.web.LookupList"
	scope="request" />

<jsp:useBean id="ApicolturaSottospecie"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ApicolturaModalita"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ApicolturaClassificazione"
	class="org.aspcfs.utils.web.LookupList" scope="request" />

<jsp:useBean id="NazioniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>


<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/opu.css" />
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/suapUtil.js"></script>

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/gestoreCodiceFiscale.js"></script>
<SCRIPT src="javascript/apiari.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>

<%@ include file="../initPage.jsp"%>

<script>

function checkFormModalita()
{	
	 	formTest = true;
	    message = "";
	    alertMessage = "";
	   
	    form = document.forms['formmodalita'];
	    

 	 	formTest = true;
 	    message = "";
 	    alertMessage = "";
 	   
 	    form = document.formmodalita ;
 	    
 	   if (form.idApicolturaModalita.value == "-1"){
 	        message += "- Modalita richiesta.\r\n";
 	        formTest = false;
 	     }
 	   
 	   if (form.dataModifica.value == ""){
 	        message += "- Data modifica richiesto\r\n";
 	        formTest = false;
 	     }
 	
 	  
 	   if (form.note.value == ""){
 	        message += "- Note richieste.\r\n";
 	        formTest = false;
 	     }
		
 	    
 	    if (formTest == false) {
 	        alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
 	        return false;
 	      } else {
 	      
 	        loadModalWindow();
 	        form.submit();
 	      }
 	    
		
}
	
	
</script>

<form name="formmodalita" id="formmodalita" action="ApicolturaApiari.do?command=UpdateModalitaApiario" method="POST" onsubmit="checkFormModalita();">

<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details" id ="">
	
		
		<input type ="hidden" name = "idStabilimento" id = "idStabilimento" value="<%=StabilimentoDettaglio.getIdStabilimento()%>"/>
		
	     	<tr>
				<td nowrap class="formLabel">Modalita allevamento</td>
				<td><%= ApicolturaModalita.getHtmlSelect("idApicolturaModalita", -1)%>
				</td>
			</tr>
			
		<tr>
				<td nowrap class="formLabel">Data Modifica</td>
				
				<td>
				<% 
				  SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
				%>
				<input type ="text" name="dataModifica" id="dataModifica" value="<%=sdf2.format(new java.util.Date(System.currentTimeMillis())) %>" readonly="readonly">
				</td>
			</tr>
			
			<tr>
				<td nowrap class="formLabel">Note</td>
				<td><textarea cols="20" rows="3" name="note" id="note"></textarea>
				</td>
			</tr>
		</table>	
			
</form>




	

