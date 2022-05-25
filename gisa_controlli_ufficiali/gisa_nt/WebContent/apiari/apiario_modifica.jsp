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

function checkForm()
{
	
	
	 	formTest = true;
	    message = "";
	    alertMessage = "";
	   
	    form = document.forms['modifystabilimento'] ;
	    
	    
	    
	    if (form.idApicolturaClassificazione.value == "-1"){
	        message += "- Classificazione richiesto\r\n";
	        formTest = false;
	     }
	    
	 
	    
	    
	    if (form.idApicolturaSottospecie.value == "-1"){
	        message += "- Sottospecie richiesto\r\n";
	        formTest = false;
	     }
	    
	    if (form.idApicolturaModalita.value == "-1"){
	        message += "- Modalita richiesto\r\n";
	        formTest = false;
	     }
	    
	    if (form.dataApertura.value == ""){
	        message += "- Data Apertura richiesto\r\n";
	        formTest = false;
	     }
	    
	    if (form.numAlveari.value == ""){
	        message += "- Num alverai richiesto\r\n";
	        formTest = false;
	     }
	    if (form.numSciami.value == ""){
	        message += "- Num Sciami/Nuclei richiesto\r\n";
	        formTest = false;
	     }
	    
	   
	    
	    if (form.latitudine.value == ""){
	        message += "- Latitudine richiesto\r\n";
	        formTest = false;
	     }
	    if (form.longitudine.value == ""){
	        message += "- Longitudine richiesto\r\n";
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
	
	 $(function () {
		 $( "#dialogProprietario" ).dialog({
		    	autoOpen: false,
		        resizable: false,
		        closeOnEscape: false,
		       	title:"INSERIMENTO ANAGRAFICA PERSONA",
		        width:850,
		        height:500,
		        draggable: false,
		        modal: true,
		        buttons:{
		        	 "Salva": function() {$("#addpersona").submit(); } ,
		        	 "Esci" : function() { $(this).dialog("close");}
		        	
		        },
		        show: {
		            effect: "blind",
		            duration: 1000
		        },
		        hide: {
		            effect: "explode",
		            duration: 1000
		        }
		       
		    }).prev(".ui-dialog-titlebar").css("background","#bdcfff");
		 
	   
	});         
	
	
          
/*AUTOCOMPLETAMENTO PER GLI INDIRIZZI*/          
$(function() {
  
    $( "#addressLegaleCity" ).combobox();
    
    $( "#searchcodeIdComune" ).combobox();
    $( "#codFiscaleSoggetto" ).combobox();
    
     

});
 

  
</script>

<form  name="modifystabilimento" action="ApicolturaApiari.do?command=UpdateApiario&auto-populate=true" method="POST" onsubmit="return checkForm()">
<input type = "hidden" name = "idStabilimento" value="<%=StabilimentoDettaglio.getIdStabilimento() %>">
<input type="hidden" name="idOperatore" id="idOperatore" value="<%=StabilimentoDettaglio.getOperatore().getIdOperatore()%>">
	
	<fieldset>
		<legend><b>ANAGRAFICA APIARI</b></legend>
		<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		 <tr>
    <th colspan="2">
      <strong>INSERIMENTO UBICAZIONE APIARIO</strong>
    </th>
  </tr>

		
		<tr>
			<td nowrap class="formLabel">Denominazione</td>
			<td>
			<input type="hidden" name = "idOperatore" value="<%=StabilimentoDettaglio.getOperatore().getIdOperatore() %>">
			<%=StabilimentoDettaglio.getOperatore().getRagioneSociale() %></td>
		</tr>
		
		
			<tr>
				<td class="formLabel" nowrap>Codice Fiscale Proprietario</td>
				<td>
				<%=StabilimentoDettaglio.getOperatore().getRappLegale().getCodFiscale() %>
					
				</td>
			</tr>
			
			<tr>
				<td nowrap class="formLabel">Proprietario (cognome e nome) </td>
				<td>
				<%=StabilimentoDettaglio.getOperatore().getRappLegale().getCognome() +" "+ StabilimentoDettaglio.getOperatore().getRappLegale().getNome() %>
				</td>

			</tr>
			
			
			<tr >
				<td class="formLabel" nowrap>Codice Fiscale Detentore</td>
				<td>
				<%=StabilimentoDettaglio.getDetentore().getCodFiscale() %>
<!-- 				<input type="text" name="codFiscaleSoggetto" readonly="readonly" placeholder="RICERCA DIGITANDO ILCODICE FISCALE" -->
<!-- 					id="codFiscaleSoggetto" class="required" value ="" />  -->
					
				</td>
			</tr>
			
			<tr style="display: none">
				<td nowrap class="formLabel">Detentore (cognome e nome) </td>
				<td>
				<%=StabilimentoDettaglio.getDetentore().getNome()+" "+StabilimentoDettaglio.getDetentore().getCognome() %>
<!-- 				<input type="text" size="70" id="nominativo" name="nome" class="required" value ="" > -->
<!-- 				<input type = "hidden" name = "idSoggettoFisico" id="idSoggettoFisico"  value = "" > -->
<!-- 				<input type ="button" onclick="javascript: $( '#dialogProprietario' ).dialog('open')" value="Inserisci Persona"> -->
				
				</td>

			</tr>
			
			<tr>
				<td class="formLabel" nowrap>Classificazione</td>
				<td>
				<%=ApicolturaClassificazione.getHtmlSelect("idApicolturaClassificazione", StabilimentoDettaglio.getIdApicolturaClassificazione()) %>
				<font color = "red">*</font>
				<%=showError(request, "classificazioneError") %>
				</td>
			</tr>
			
			<tr>
				<td class="formLabel" nowrap>Sottospecie</td>
				<td>
				<%=ApicolturaSottospecie.getHtmlSelect("idApicolturaSottospecie",StabilimentoDettaglio.getIdApicolturaSottospecie()) %>
				<font color = "red">*</font>
				<%=showError(request, "sottospecieError") %>
				</td>
			</tr>
			
			<tr>
				<td class="formLabel" nowrap>Modalita Allevamento</td>
				<td>
				<%=ApicolturaModalita.getHtmlSelect("idApicolturaModalita", StabilimentoDettaglio.getIdApicolturaModalita()) %>
				<font color = "red">*</font>
				<%=showError(request, "modalitaError") %>
				</td>
			</tr>
			
			<tr>
				<td class="formLabel" nowrap>Data Apertura Attivita</td>
				<td>
				
				<%=toDateasString(StabilimentoDettaglio.getOperatore().getDataInizio()) %>
					<%=showError(request, "dataAperturaError") %>
					<%=showError(request, "dataInizioError") %>
				</td>
			</tr>
			
			<tr>
				<td class="formLabel" nowrap>Data Apertura Apiario</td>
				<td>
				<input type="text" size="70" name="dataApertura"
					id="dataInizioAttivita" class="required" placeholder="dd/MM/YYYY" readonly="readonly" value="<%=toDateasString(StabilimentoDettaglio.getDataApertura()) %>">
					
					<a href="#" onClick="cal19.select(document.forms[0].dataInizioAttivita,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
					<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
				
					<font color = "red">*</font>
					<%=showError(request, "dataAperturaError") %>
					<%=showError(request, "dataInizioError") %>
				</td>
			</tr>
			
			
	</table>
	
	</fieldset>
	<fieldset>
		<legend><b>DATI CENSIMENTO</b></legend>
		<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		
   
		<tr>
			<td nowrap class="formLabel">Numero Alveari</td>
			<td>
			<input type = "text" name = "numAlveari" id = "numAlveari" style="width: 50px;" value="<%=StabilimentoDettaglio.getNumAlveari()%>">
			<font color = "red">*</font>
			</td>
		</tr>
		<tr>
			<td nowrap class="formLabel">Numero Sciami / Nuclei</td>
			
			<td>
			<input type = "text" name = "numSciami" id = "numSciami" style="width: 50px;" value="<%=StabilimentoDettaglio.getNumSciami()%>">
			<font color = "red">*</font>
			</td>
		
		</tr>
		
	
		</table>
	
	</fieldset>
	<fieldset>
		<legend><b>DATI UBICAZIONE</b></legend>
		<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		
   <tr>
		<td nowrap class="formLabel">Comune</td>
		<td>
		<%=StabilimentoDettaglio.getSedeOperativa().getDescrizioneComune() %>

			
		</td>
	</tr>
	
	<tr>
		<td nowrap class="formLabel">
			Cap
		</td>
		<td>
		<%=StabilimentoDettaglio.getSedeOperativa().getCap()%>
<%-- 			<input type="text" size="70" name="presso" id ="presso"  style="width: 50px;" value="<%=StabilimentoDettaglio.getSedeOperativa().getCap()%>"> --%>
		</td>
	</tr>
	
	<tr id ="searchcodeIdprovinciaTR">

		<td nowrap class="formLabel">
			Sigla Provincia
		</td>
		<td>
		<%=StabilimentoDettaglio.getSedeOperativa().getDescrizione_provincia() %>
			
<%-- 			<input type="text" readonly="readonly" required="required" name="searchcodeIdprovinciaTesto" id="searchcodeIdprovinciaTesto" value = "<%=Stabilimentodetta %>" /> --%>
<%-- 			<input type="hidden"  required="required" name="searchcodeIdprovincia" id="searchcodeIdprovincia" value="<%=StabilimentoDettaglio.getSedeOperativa().getIdProvincia() %>" /> --%>
			
		</td>
	</tr>
	
	<tr>
		<td nowrap class="formLabel">
			Indirizzo
		</td>
		<td>
			
			<%=StabilimentoDettaglio.getSedeOperativa().getVia() %>
			
				<font color = "red">*</font>
					<%=showError(request, "indirizzoError") %>
		</td>
	</tr>
	
	
	
	
	<tr>
		<td nowrap class="formLabel">
			Latitudine
		</td>
		<td>
			
			<input type="text" name="latitudine" id="localitaSedeLegale" value="<%=StabilimentoDettaglio.getSedeOperativa().getLatitudine()%>"  />
			<font color = "red">*</font>
					<%=showError(request, "latitudineError") %>
		</td>
	</tr>
	<tr>
		<td nowrap class="formLabel">
			Longitudine
		</td>
		<td>
			
			<input type="text" name="longitudine" id="localitaSedeLegale" value="<%=StabilimentoDettaglio.getSedeOperativa().getLongitudine()%>" />
			<font color = "red">*</font>
					<%=showError(request, "longitudineError") %>
					
					<div id ="coordinateError"></div>
		</td>
	</tr>
	
	
	
	
		
		</table>
	
	</fieldset>		
	
	
	
</form>




	

