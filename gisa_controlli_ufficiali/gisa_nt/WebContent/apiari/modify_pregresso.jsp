<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>


<%@page import="ext.aspcfs.modules.apiari.base.Stabilimento"%>
<%@page import="ext.aspcfs.modules.apiari.base.Delega"%>
<%@page import="java.util.Iterator"%>
<%@page import="ext.aspcfs.modules.apicolture.actions.CfUtil"%>
<jsp:useBean id="TipoAttivitaApi" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>


<jsp:useBean id="NazioniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="ListaDeleghe" class="ext.aspcfs.modules.apiari.base.DelegaList" scope="request" />

<jsp:useBean id="ApicolturaSottospecie" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ApicolturaModalita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ApicolturaClassificazione" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/opu.css" />

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/gestoreCodiceFiscale.js"></script>
<SCRIPT src="javascript/apiari.js"></SCRIPT>

<jsp:useBean id="OrgDetails" class="ext.aspcfs.modules.apiari.base.Operatore" scope="request" />


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
	
	
	$(document).ready(function(){
	    $('#dialogAlert').dialog({
	        modal: true,
	        buttons: {
	            "Chiudi": function() {
	                $( this ).dialog( "close" );
	                }
	            }
	        });
	    
	    $('#dialogAlert').dialog("open");
	    });
	
	
</SCRIPT>

<%@ include file="../initPage.jsp"%>

<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/opu.css" />



<div id="dialogAlert" title="ATTENZIONE">
  <p align="left"><font size="2em" >Per l'attivita di apicoltura associata al seguente codice fiscale <%=User.getSoggetto().getCodFiscale().toUpperCase() %> esistono informazioni da confermare.<br>Dopo Aver salvato la pratica con l'apposito pulsante SALVA questa sarà inviata all'asl di competenza per l'assegnazione del codice regionale</font></p>
</div>

<div id = "dialogProprietario">
<form name="addpersona" id="addpersona" action="ApicolturaAttivita.do?command=InsertPersona" method="POST">
<div id="messaggioErrore"></div>
<input  type="hidden" name = "indicepersona" id = "indicepersona">
<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
	
	
	
			
			<tr>
				<td nowrap class="formLabel">Nome </td>
				<td><input type="text" size="70" id="nome" name="nome" class="required">
					<div id="nomeError"></div>
				</td>

			</tr>

			<tr>
				<td nowrap class="formLabel"><label for="cognome-2">Cognome </label></td>
				<td><input type="text" size="70" id="cognome" name="cognome"
					class="required">
						<div id="cognomeError"></div>
					</td>
			</tr>

			<tr>
				<td nowrap class="formLabel"><label for="sesso-2">Sesso </label></td>
				<td><div class="test">
						<input type="radio" name="sesso" id="sesso1" value="M"
							checked="checked" class="required css-radio">
							<label for="sesso1" class="css-radiolabel radGroup2">M</label>
						 <input type="radio"
							name="sesso" id="sesso2" value="F" class="required css-radio">
						<label for="sesso2" class="css-radiolabel radGroup2">F</label>
					</div></td>
			</tr>

			<tr>
				<td nowrap class="formLabel"><label for="dataN-2">Data Nascita </label></td>
				<td><input type="text" size="70" name="dataNascita" readonly="readonly"
					id="dataNascita" class="required" placeholder="dd/MM/YYYY">
					
					<a href="#" onClick="cal19.select(document.forms['addpersona'].dataNascita,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
				
				<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
					
					</td>
			</tr>

			<tr>
				<td nowrap class="formLabel"><label for="nazioneN-2">Nazione Nascita </label></td>
				<td>
				<%NazioniList.setJsEvent("onchange=\"abilitaCodiceFiscale('nazioneNascita')\"") ; %>
				<%=NazioniList.getHtmlSelect("nazioneNascita", 106) %></td>
			</tr>


			<tr>
				<td nowrap class="formLabel"><label for="comuneNascita-2">Comune Nascita </label></td>
				<td><input type="text" size="70" name="comuneNascita"
					id="comuneNascita" class="required">
					
					</td>
			</tr>

			

			<tr>
				<td class="formLabel" nowrap>Codice Fiscale</td>
				<td><input type="text" name="codFiscaleSoggetto" readonly="readonly"
					id="codFiscaleSoggettoAdd" class="required" />  	<div id="cfError"></div>
					<input type="button" id="calcoloCF" class="newButtonClass"
					value="Calcola Cod. Fiscale"
					onclick="javascript:CalcolaCF(document.addpersona.sesso,document.addpersona.nome,document.addpersona.cognome,document.addpersona.comuneNascita,document.addpersona.dataNascita,'codFiscaleSoggettoAdd')"></input>
					
				</td>
			</tr>
			



			<tr>
				<td nowrap class="formLabel"><label for="nazioneN-2">Nazione Residenza </label></td>
				<td>
				<%NazioniList.setJsEvent("onchange=\"sbloccoProvincia('nazioneResidenza','addressLegaleCountry','addressLegaleCity','addressLegaleLine1')\"") ; %>
				<%=NazioniList.getHtmlSelect("nazioneResidenza", 106) %>
				<div id="nazioneError"></div>
				</td>
			</tr>
			
			
			

			<tr>
				<td class="formLabel" nowrap>Comune Residenza</td>
				<td><select name="addressLegaleCity" id="addressLegaleCity" class="required" >
						<option value="">Seleziona Comune</option>
				</select> <input type="hidden" name="addressLegaleCityTesto"
					id="addressLegaleCityTesto" />
					<div id="comuneError"></div>
					</td>
			</tr>
			
			<tr id ="addressLegaleCountryTR">
				<td class="formLabel" nowrap>Provincia Residenza</td>
				<td><input type= "hidden" id="addressLegaleCountry" class="required"
					name="addressLegaleCountry">
									<input type="text" id="addressLegaleCountryTesto" readonly="readonly"
					name="addressLegaleCountryTesto" /></td>
			</tr>


		<tr>
		<td nowrap class="formLabel">Indirizzo Residenza</td>
		<td>
			
			
			<input type="text" name="addressLegaleLine1Testo" id="addressLegaleLine1Testo" />
			<div id="indirizzoError"></div>
				
		</td>
	</tr>
	
	<tr>
    		<td nowrap class="formLabel">
      			Domicilio Digitale<br>(Pec)
    		</td>
    		<td>
      			<input type="text" size="70" name="domicilioDigitale" >    
    		</td>
  		</tr>

</table>
</form>
</div>

<script>
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

function checkForm()
{
	
	
	 	formTest = true;
	    message = "";
	    alertMessage = "";
	   
	    form = document.addstabilimento ;
	    
	    if (form.searchcodeIdComuneinput.value == ""){
	        message += "- Comune Sede richiesto\r\n";
	        formTest = false;
	     }
	    
	    if (form.presso.value == ""){
	        message += "- CAP richiesto\r\n";
	        formTest = false;
	     }
	    
	    if (form.ragioneSociale.value == ""){
	        message += "- Denominazione richiesto\r\n";
	        formTest = false;
	     }
	    
	    if (form.codFiscaleSoggetto.value == ""){
	        message += "- Codice Fiscale Proprietario richiesto\r\n";
	        formTest = false;
	     }
	    
	    if (form.nome.value == ""){
	        message += "- Proprietario (Cognome e Nome) richiesto\r\n";
	        formTest = false;
	     }
	    if (form.dataInizio.value == ""){
	        message += "- Proprietario (Cognome e Nome) richiesto\r\n";
	        formTest = false;
	     }
	    
	    if (form.viaTesto.value == ""){
	        message += "- Indirizzo richiesto\r\n";
	        formTest = false;
	     }
	    if (form.idTipoAttivita.value == "-1"){
	        message += "- Tipo Attivita richiesto\r\n";
	        formTest = false;
	     }
	    
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
	        return true;
	      }
		
}

	
	
	
	 
     

	
          
/*AUTOCOMPLETAMENTO PER GLI INDIRIZZI*/          
$(function() {
  
    $( "#addressLegaleCity" ).combobox();
    $( "#codice_azienda" ).combobox();
    
    $( "#searchcodeIdComune" ).combobox();
    
 
});



  
  

  
</script>

<form  name="addstabilimento" action="ApicolturaAttivita.do?command=UpdatePregresso&auto-populate=true" method="POST" onsubmit="return checkForm()">
<%
if (request.getAttribute("Exist") != null && !("").equals(request.getAttribute("Exist"))) 
{
%> 
<font color="red"><%=(String) request.getAttribute("Exist")%></font>
<%
}%>

<input type="hidden" name="sovrascrivi" id="sovrascrivi" value="n.d">
<input type = "hidden" name = "idOperatore" value ="<%=OrgDetails.getIdOperatore() %>"	>
	<fieldset>
		<legend><b>ANAGRAFICA ATTIVITA</b></legend>
		<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		 <tr>
    <th colspan="2">
      <strong>INSERIMENTO ATTIVITA DI APICOLTURA</strong>
    </th>
  </tr>
  
  


		<tr>
		<td nowrap class="formLabel">Comune Sede Legale</td>
		<td>
			<select name="searchcodeIdComune" id="searchcodeIdComune" class="required">
				<option value="<%=(OrgDetails.getSedeLegale()!=null && OrgDetails.getSedeLegale().getDescrizioneComune()!=null) ?OrgDetails.getSedeLegale().getComune() :"" %>"><%=(OrgDetails.getSedeLegale()!=null && OrgDetails.getSedeLegale().getDescrizioneComune()!=null) ?OrgDetails.getSedeLegale().getDescrizioneComune() :"" %></option>
			</select>
			<input type="hidden" name="searchcodeIdComuneTesto" id="searchcodeIdComuneTesto" /><font color="red">*</font>
			<%=showError(request, "comuneSedeLegaleError") %>
			
		</td>
	</tr>
	
	<tr>
		<td nowrap class="formLabel">
			Cap
		</td>
		<td>
			<input type="text" size="70" name="presso" maxlength="80" value="<%=(OrgDetails.getSedeLegale()!=null) ? toHtml2(OrgDetails.getSedeLegale().getCap()) : ""%>">
			<font color="red">*</font>
			<%=showError(request, "capSedeLegaleError") %>
		</td>
	</tr>
	
	<tr id ="searchcodeIdprovinciaTR">

		<td nowrap class="formLabel">
			Sigla Provincia
		</td>
		<td>
			
			<input type="text" readonly="readonly" required="required" name="searchcodeIdprovinciaTesto" id="searchcodeIdprovinciaTesto" value = "<%=(OrgDetails.getSedeLegale()!=null && OrgDetails.getSedeLegale().getDescrizioneComune()!=null) ?OrgDetails.getSedeLegale().getProvincia() :"" %>" />
			<input type="hidden"  required="required" name="searchcodeIdprovincia" id="searchcodeIdprovincia" />
			<font color="red">*</font>
						<%=showError(request, "provinciaSedeLegaleError") %>
			
			
			
		</td>
	</tr>
	
		
		<tr>
			<td nowrap class="formLabel">Denominazione</td>
			<td><input type="text" size="70"  id="ragioneSociale" class="required" name="ragioneSociale" value ="<%=toHtml2(OrgDetails.getRagioneSociale())%>"><font color="red">*</font></td>
		</tr>
		
		
		
		
			
			<tr>
				<td class="formLabel" nowrap>Codice Fiscale Proprietario</td>
				<td>
				<%=OrgDetails.getRappLegale().getCodFiscale() %>
				</td>
			</tr>
			
			<tr>
				<td nowrap class="formLabel">Proprietario (cognome e nome)</td>
				<td><input type="text" readonly="readonly" size="70" id="nominativo" name="nome" class="required" value ="<%=(User.getSoggetto()!=null && !CfUtil.extractCodiceFiscale(User.getSoggetto().getCodFiscale()).equals("")) ? User.getSoggetto().getNome()+" "+User.getSoggetto().getCognome():""  %>">
				<input type = "hidden" name = "idSoggettoFisico" id="idSoggettoFisico" value = "<%=User.getSoggetto()!=null ? User.getSoggetto().getIdSoggetto() : "" %>" >

				</td>

			</tr>
			
			
			<tr>
				<td class="formLabel" nowrap>Data Inizio Attivita</td>
				<td>
				<input type="text" size="70" name="dataInizio"
					id="dataInizioAttivita" readonly="readonly" class="required" placeholder="dd/MM/YYYY" value="<%=toDateasString(OrgDetails.getDataInizio()) %>">
					
					<a href="#" onClick="cal19.select(document.forms[0].dataInizioAttivita,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
				<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
					
					<font color="red">*</font>
					
				</td>
			</tr>
			
			
	<tr>
		<td nowrap class="formLabel">
			Indirizzo Sede Legale
		</td>
		<td>
			
			<input type="text" name="viaTesto" id="viaTesto" value="<%=(OrgDetails.getSedeLegale()!=null) ? toHtml2(OrgDetails.getSedeLegale().getVia()) : ""%>" />
			<font color="red">*</font>
			<%=showError(request, "viaSedeLegaleError") %>
		</td>
	</tr>
	
	
	<tr>
		<td nowrap class="formLabel">
			Localita Sede Legale
		</td>
		<td>
			
			<input type="text" name="localitaSedeLegale" id="localitaSedeLegale" />
		</td>
	</tr>
	
	<tr>
		<td nowrap class="formLabel">
			Tipo Attivita
		</td>
		<td>
			<%=TipoAttivitaApi.getHtmlSelect("idTipoAttivita", OrgDetails.getIdTipoAttivita()) %>
			<font color="red">*</font>
			      				<%=showError(request, "tipoAttivitaError") %>
			
			
		</td>
	</tr>
	
	<tr>
    		<td nowrap class="formLabel">
      			Domicilio Digitale<br>(Pec)
    		</td>
    		<td>
      			<input type="text" size="70" name="domicilioDigitale" value="<%=toHtml2(OrgDetails.getDomicilioDigitale()) %>" >    
      			<font color="red">*</font>
      			<%=showError(request, "domicilioDigitaleError") %>
      			
    		</td>
  		</tr>
  		
  		<tr>
    		<td nowrap class="formLabel">
      			Telefono Fisso
    		</td>
    		<td>
      			<input type="text" size="70" name="telefono1" value="<%=toHtml2(OrgDetails.getTelefono1()) %>" >    
      			<font color="red">*</font>
      			<%=showError(request, "telefonoFissoError") %>
    		</td>
  		</tr>
  		
  		<tr>
    		<td nowrap class="formLabel">
      			Telefono Cellulare
    		</td>
    		<td>
      			<input type="text" size="70" name="telefono2" value="<%=toHtml2(OrgDetails.getTelefono2()) %>" >    
      			<font color="red">*</font>
      				<%=showError(request, "telefonoCellulareError") %>
    		</td>
  		</tr>
  		
  		<tr>
    		<td nowrap class="formLabel">
      			Fax
    		</td>
    		<td>
      			<input type="text" size="70" name="fax" value="<%=toHtml2(OrgDetails.getFax())%>" >    
    		</td>
  		</tr>
  		
  		

	<tr style="display: none">
		<td nowrap class="formLabel">
			Partita Iva
		</td>
		<td>
			<input type="text" size="70" maxlength="11" id="partitaIva" class="required" name="partitaIva">
			</td>
	</tr>

	<tr id="codFiscaleTR" style="display: none">
		<td nowrap class="formLabel">
			Codice Fiscale<br>Impresa
		</td>
		<td>
			<input type="text" size="70" maxlength="16" id="codFiscale" class="required" name="codFiscale" value ="<%=CfUtil.extractCodiceFiscale(User.getSoggetto().getCodFiscale()) %>">
		</td>
	</tr>
	</table>
	
	</fieldset>
	<br>
	<br>	
	
	<!-- INIZIO LISTA APIARI  -->
	
	<%
	int i = 0 ;
	Iterator itApiari = OrgDetails.getListaStabilimenti().iterator();
	while (itApiari.hasNext())
	{
		i++;
		Stabilimento thisApiario = (Stabilimento)itApiari.next();
	%>
	<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
	<tr>
	 <th colspan="2">
      <strong>INSERIMENTO APIARI</strong>
    </th>
  </tr>
		<input type = "hidden" name="id_stabilimento" value = "<%=thisApiario.getIdStabilimento()%>"/>
		<tr>
				<td class="formLabel" nowrap>Codice Fiscale Detentore</td>
				<td><input type="text" name="codFiscaleSoggetto_<%=thisApiario.getIdStabilimento() %>" readonly="readonly" 
					id="codFiscaleSoggetto_<%=thisApiario.getIdStabilimento() %>" class="required" value="<%=(! CfUtil.extractCodiceFiscale(thisApiario.getDetentore().getCodFiscale()).equals("")) ? thisApiario.getDetentore().getCodFiscale() :"" %>" /> 
					
				</td>
			</tr>
			
			<tr>
				<td nowrap class="formLabel">Detentore (cognome e nome) </td>
				<td><input type="text" size="70" id="nominativo_<%=thisApiario.getIdStabilimento() %>" name="nome_<%=thisApiario.getIdStabilimento() %>" class="required" value="<%=(! CfUtil.extractCodiceFiscale(thisApiario.getDetentore().getCodFiscale()).equals("")) ? thisApiario.getDetentore().getNome() + " "+thisApiario.getDetentore().getCognome() : "" %>">
				<input type = "hidden" name = "idSoggettoFisico_<%=thisApiario.getIdStabilimento() %>" id="idSoggettoFisico_<%=thisApiario.getIdStabilimento() %>" value = "<%=(! CfUtil.extractCodiceFiscale(thisApiario.getDetentore().getCodFiscale()).equals("")) ? thisApiario.getDetentore().getIdSoggetto() :"" %>" >
				<input type ="button" onclick="javascript:document.getElementById('indicepersona').value='<%=thisApiario.getIdStabilimento() %>'; $( '#dialogProprietario' ).dialog('open')" value="Inserisci Persona">
				
				</td>

			</tr>
			
			<tr>
				<td class="formLabel" nowrap>Classificazione</td>
				<td>
				<%=ApicolturaClassificazione.getHtmlSelect("idApicolturaClassificazione_"+thisApiario.getIdStabilimento(), thisApiario.getIdApicolturaClassificazione()) %>
				<font color = "red">*</font>
				<%=showError(request, "classificazioneError") %>
				</td>
			</tr>
			
			<tr>
				<td class="formLabel" nowrap>Sottospecie</td>
				<td>
				<%=ApicolturaSottospecie.getHtmlSelect("idApicolturaSottospecie_"+thisApiario.getIdStabilimento(), thisApiario.getIdApicolturaSottospecie()) %>
				<font color = "red">*</font>
				<%=showError(request, "sottospecieError") %>
				</td>
			</tr>
			
			<tr>
				<td class="formLabel" nowrap>Modalita Allevamento</td>
				<td>
				<%=ApicolturaModalita.getHtmlSelect("idApicolturaModalita_"+thisApiario.getIdStabilimento(), thisApiario.getIdApicolturaModalita()) %>
				<font color = "red">*</font>
				<%=showError(request, "modalitaError") %>
				</td>
			</tr>
			
			<tr>
				<td class="formLabel" nowrap>Data Apertura</td>
				<td>
				<input type="text" size="70" required="required" name="dataApertura_<%=thisApiario.getIdStabilimento() %>"
					id="dataInizioAttivita_<%=thisApiario.getIdStabilimento() %>" value="<%=toDateasString(thisApiario.getDataApertura()) %>" readonly="readonly">
					
					<a href="#" onClick="cal19.select(document.forms[0].dataInizioAttivita_<%=thisApiario.getIdStabilimento() %>,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
					<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
				
					<font color = "red">*</font>
					<%=showError(request, "dataAperturaError") %>
				</td>
			</tr>
			
			<tr>
			<td nowrap class="formLabel">Numero Alveari</td>
			<td>
			<input type = "text" name = "numAlveari_<%=thisApiario.getIdStabilimento() %>" id = "numAlveari_<%=thisApiario.getIdStabilimento() %>" value = "<%=thisApiario.getNumAlveari() %>" style="width: 50px;">
			<font color = "red">*</font>
			</td>
		</tr>
		<tr>
			<td nowrap class="formLabel">Numero Sciami/Nuclei</td>
			
			<td>
			<input type = "text" name = "numSciami_<%=thisApiario.getIdStabilimento() %>" id = "numSciami_<%=thisApiario.getIdStabilimento() %>" value = "<%=thisApiario.getNumSciami() %>" style="width: 50px;">
			<font color = "red">*</font>
			</td>
		
		</tr>
		 <tr>
		<td nowrap class="formLabel">Comune</td>
		<td>
		
			<%=ComuniList.getHtmlSelect("comune_"+thisApiario.getIdStabilimento(), thisApiario.getSedeOperativa().getComune()) %>
			
		</td>
	</tr>
	
	<tr>
		<td nowrap class="formLabel">
			Cap
		</td>
		<td>
			<input type="text" size="70" name="presso_<%=thisApiario.getIdStabilimento()%>" id ="presso_<%=thisApiario.getIdStabilimento()%>"  value="<%=toHtml2(""+thisApiario.getSedeOperativa().getCap()) %>" style="width: 50px;">
		</td>
	</tr>
	
	<tr id ="searchcodeIdprovinciaTR">

		<td nowrap class="formLabel">
			Sigla Provincia
		</td>
		<td>
			
			<input type="text"  required="required" name="provincia_<%=thisApiario.getIdStabilimento()%>" value="<%=toHtml2(""+thisApiario.getSedeOperativa().getProvincia()) %>"/>
			
			
		</td>
	</tr>
	
	<tr>
		<td nowrap class="formLabel">
			Indirizzo
		</td>
		<td>
			
			<input type="text" required="required" name="viaTesto_<%=thisApiario.getIdStabilimento()%>" id="viaTesto_<%=thisApiario.getIdStabilimento()%>" value="<%=toHtml2(""+thisApiario.getSedeOperativa().getVia()) %>" />
				<font color = "red">*</font>
					
		</td>
	</tr>
	
	

	
	<tr>
		<td nowrap class="formLabel">
			Latitudine
		</td>
		<td>
			
			<input type="text"  required="required" readonly="readonly" name="latitudine_<%=thisApiario.getIdStabilimento()%>" id="localitaSedeLegale" value="<%=toHtml2(""+thisApiario.getSedeOperativa().getLatitudine()) %>"/>
			<font color = "red">*</font>
					<%=showError(request, "latitudineError") %>
		</td>
	</tr>
	<tr>
		<td nowrap class="formLabel">
			Longitudine
		</td>
		<td>
			
			<input type="text" required="required" readonly="readonly" name="longitudine_<%=thisApiario.getIdStabilimento()%>" id="localitaSedeLegale" value="<%=toHtml2(""+thisApiario.getSedeOperativa().getLongitudine()) %>" />
			<font color = "red">*</font>
					<%=showError(request, "longitudineError") %>
		</td>
	</tr>
	
	 <tr style="display:block">
    <td colspan="2">
    <input id="coord2button" type="button" value="Calcola Coordinate" 
    onclick="javascript:showCoordinate(document.getElementById('viaTesto').value, document.forms['addstabilimento'].comune_<%=thisApiario.getIdStabilimento()%>.value,document.forms['addstabilimento'].provincia_<%=thisApiario.getIdStabilimento()%>.value, document.forms['addstabilimento'].presso_<%=thisApiario.getIdStabilimento()%>.value, document.forms['addstabilimento'].latitudine_<%=thisApiario.getIdStabilimento()%>, document.forms['addstabilimento'].longitudine_<%=thisApiario.getIdStabilimento()%>);" />
     </td>
    </tr>
		
	</table>
	<br>
	
	<%} %>
	
		
	
	
	
		<input type ="submit" value="SALVA">
</form>



<script>

var campoLat;
var campoLong;
	function showCoordinate(address,city,prov,cap,campo_lat,campo_long)
	{
   campoLat = campo_lat;
   campoLong = campo_long;
   Geocodifica.getCoordinate(address,city,prov,cap,'','','',setGeocodedLatLonCoordinate);
   
   
}
function setGeocodedLatLonCoordinate(value)
{
	campoLat.value = value[1];;
	campoLong.value =value[0];
	
}

$("#addpersona").submit(function(e)
		{
		    var postData = $(this).serializeArray();
		    var formURL = $(this).attr("action");
		    $.ajax(
		    {
		        url : formURL,
		        type: "POST",
		        data : postData,
		        dataType : "json",
		        success:function(data, textStatus, jqXHR)
		        {
		        	if (data.EsitoInserimentoSoggettoFisico=='KO')
		        	{
		        		document.getElementById("messaggioErrore").innerHTML="<font color='red' font-size:small;>"+data.ErroreInserimento+"</font>";
		        		if (data.nomeError!=null)
		        			{
		        			document.getElementById("nomeError").innerHTML="<font color='red' font-size:small;>"+data.nomeError+"</font>";
		        			}
		        		else
		        			{
		        			document.getElementById("nomeError").innerHTML="";
		        			}
		        		
		        		
		        		if (data.cognomeError!=null)
	        			{
	        			document.getElementById("cognomeError").innerHTML="<font color='red' font-size:small;>"+data.cognomeError+"</font>";
	        			}
	        		else
	        			{
	        			document.getElementById("cognomeError").innerHTML="";
	        			}
		        		
		        		if (data.cfError!=null)
	        			{
	        			document.getElementById("cfError").innerHTML="<font color='red' font-size:small;>"+data.cfError+"</font>";
	        			}
	        		else
	        			{
	        			document.getElementById("cfError").innerHTML="";
	        			}
		        		
		        		if (data.ComuneError!=null)
	        			{
	        			document.getElementById("comuneError").innerHTML="<font color='red' font-size:small;>"+data.ComuneError+"</font>";
	        			}
	        		else
	        			{
	        			document.getElementById("comuneError").innerHTML="";
	        			}
		        		
		        		if (data.IndirizzoError!=null)
	        			{
	        			document.getElementById("indirizzoError").innerHTML="<font color='red' font-size:small;>"+data.IndirizzoError+"</font>";
	        			}
	        		else
	        			{
	        			document.getElementById("indirizzoError").innerHTML="";
	        			}
		        		
		        		if (data.nazioneError!=null)
	        			{
	        			document.getElementById("nazioneError").innerHTML="<font color='red' font-size:small;>"+data.nazioneError+"</font>";
	        			}
	        		else
	        			{
	        			document.getElementById("nazioneError").innerHTML="";
	        			}
		        		
		        		
		        		
		        	}
		        	else
		        		{
		        		alert('Persona Anagrafata con successo!');
		        		var idApiario =document.addpersona.indicepersona.value;
		        		document.getElementById("nominativo_"+idApiario).value=data.nominativoSoggettoFisico ;
		        		document.getElementById("idSoggettoFisico_"+idApiario).value=data.idSoggettoFisico ;
		        		document.getElementById("codFiscaleSoggetto_"+idApiario+"").value=data.cfSoggettoFisico ;
		        		$( '#dialogProprietario' ).dialog('close');
		        		}
		        	
		            //data: return data from server
		        },
		        error: function(jqXHR, textStatus, errorThrown)
		        {
		        	alert("errore");
		            //if fails     
		        }
		    });
		    e.preventDefault(); //STOP default action
		  
		});

</script>








