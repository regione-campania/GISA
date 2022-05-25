<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>


<%@page import="ext.aspcfs.modules.apiari.base.Delega"%>
<%@page import="java.util.Iterator"%>
<%@page import="ext.aspcfs.modules.apicolture.actions.CfUtil"%>

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

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/gestoreCodiceFiscale.js"></script>
<SCRIPT src="javascript/apiari.js"></SCRIPT>



<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/theme.css"></link>
<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/jquery.tablesorter.pages.css"></link>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.pager.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.widgets.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/tableJqueryFilterApi.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>


<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/opu.css" />



<script>


function checkFormApicoltore()
{
	
	
	 	formTest = true;
	    message = "";
	    alertMessage = "";
	   
	    form = document.apicoltoreModificaRecapiti ;
	    
	    
	   
	    
	    if (form.domicilioDigitale.value == ""){
	        message += "- Domicilio Digitale(Pec) richiesto\r\n";
	        formTest = false;
	     }
	    if (form.telefono1.value == ""){
	        message += "- Telefono Fisso richiesto\r\n";
	        formTest = false;
	     }
	    if (form.telefono2.value == ""){
	        message += "- Telefono Cellulare richiesto\r\n";
	        formTest = false;
	     }
	    
	    
	    
	    
	    
	    if (formTest == false) {
	        alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
	        return false;
	      } else {
	      
	        loadModalWindow();
	        form.submit();
	        return true;
	      }
		
}


	function mostraCampoExtra(value)
	
	{
		if (value=='1')
			{
				document.getElementById("extraField").style.display="";
			}
		else
			{
			document.getElementById("extraField").style.display="none";
			}
	}
	
	


		    
	 
     



  
  

  
</script>

<%@ include file="../initPage.jsp"%>
<form  name="apicoltoreModificaRecapiti" action="ApicolturaAttivita.do?command=UpdateApicoltoreRecapiti&auto-populate=true" method="POST" onsubmit="return checkForm()">


<input type="hidden" name="idStabilimento" id="idStabilimento" value="<%=StabilimentoDettaglio.getIdStabilimento() %>">
	
	<fieldset>
		<legend><b>ANAGRAFICA ATTIVITA</b></legend>
		<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		 <tr>
    <th colspan="2">
      <strong>MODIFICA RECAPITI</strong>
    </th>
  </tr>
  
  	
	<tr>
    		<td nowrap class="formLabel">
      			Domicilio Digitale<br>(Pec)
    		</td>
    		<td>
      			<input  type="email" size="100" name="domicilioDigitale" value="<%=toHtml2(StabilimentoDettaglio.getOperatore().getDomicilioDigitale()) %>" >    
      			<font color="red">*</font>
      			<%=showError(request, "domicilioDigitaleError") %>
      			
    		</td>
  		</tr>
  		
  		<tr>
    		<td nowrap class="formLabel">
      			Telefono Fisso
    		</td>
    		<td>
      			<input type="text" size="70" name="telefono1" value="<%=toHtml2(StabilimentoDettaglio.getOperatore().getTelefono1()) %>" >    
      			<font color="red">*</font>
      			<%=showError(request, "telefonoFissoError") %>
    		</td>
  		</tr>
  		
  		<tr>
    		<td nowrap class="formLabel">
      			Telefono Cellulare
    		</td>
    		<td>
      			<input type="text" size="70" name="telefono2" value="<%=toHtml2(StabilimentoDettaglio.getOperatore().getTelefono2()) %>" >    
      			<font color="red">*</font>
      				<%=showError(request, "telefonoCellulareError") %>
    		</td>
  		</tr>
  		
  		<tr>
    		<td nowrap class="formLabel">
      			Fax
    		</td>
    		<td>
      			<input type="text" size="70" name="fax" value="<%=toHtml2(StabilimentoDettaglio.getOperatore().getFax()) %>" >    
      			<font color="red">*</font>
      				<%=showError(request, "faxError") %>
    		</td>
  		</tr>
  		
  			
	</table>
	
	</fieldset>
	<br>
	<br>	
	

</form>











