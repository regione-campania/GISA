<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="ext.aspcfs.modules.apiari.base.Delega"%>
<%@page import="java.util.Iterator"%>
<%@page import="ext.aspcfs.modules.apicolture.actions.CfUtil"%>
<jsp:useBean id="TipoAttivitaApi" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="NazioniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="ListaDeleghe" class="ext.aspcfs.modules.apiari.base.DelegaList" scope="request" />


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
</SCRIPT>

<%@ include file="../initPage.jsp"%>

<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/opu.css" />



<script>


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
	    
	    if (form.searchcodeIdComuneinput.value != form2.addressLegaleCity){
	        message += "- Comune Sede Legale non coincide con comune della residenza\r\n";
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
	        message += "- Data inizio richiesta\r\n";
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
function selezionaDelega(nominativoDelegante,idSoggettoDelegante,codiceFiscaleDelegante)
{
	
	document.getElementById("nominativo").value=nominativoDelegante ;
	document.getElementById("idSoggettoFisico").value=idSoggettoDelegante ;
	document.getElementById("codFiscaleSoggetto").value=codiceFiscaleDelegante ;
	document.getElementById("codFiscale").value=codiceFiscaleDelegante ;

	$( '#dialogDelega' ).dialog('close');
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
		 
		 
		 $( "#dialogDelega" ).dialog({
		    	autoOpen: false,
		        resizable: false,
		        closeOnEscape: false,
		       	title:"SELEZIONA DELEGA",
		        buttons:{
		        	
		        	 "Esci" : function() { $(this).dialog("close");}
		        	
		        },
		        width:850,
		        height:500,
		        draggable: false,
		        modal: true,
		        
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
    $( "#codice_azienda" ).combobox();
    
    
    
    $( "#searchcodeIdComune" ).combobox();
    
 
});



  
  

  
</script>

<form  name="addstabilimento" action="ApicolturaAttivita.do?command=Insert&auto-populate=true" method="POST" onsubmit="return checkForm()">
<%
if (request.getAttribute("Exist") != null && !("").equals(request.getAttribute("Exist"))) 
{
%> 
<font color="red"><%=(String) request.getAttribute("Exist")%></font>
<%
}%>

<input type="hidden" name="sovrascrivi" id="sovrascrivi" value="n.d">
<input type="hidden" name="idOperatore" id="idOperatore" value="n.d">
	
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
				<option value="<%=(OrgDetails.getSedeOperativa()!=null && OrgDetails.getSedeOperativa().getDescrizioneComune()!=null) ?OrgDetails.getSedeOperativa().getDescrizioneComune() :"" %>"></option>
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
			<input type="text" size="70" name="presso" maxlength="80" value="<%=(OrgDetails.getSedeOperativa()!=null) ? toHtml2(OrgDetails.getSedeOperativa().getCap()) : ""%>">
			<font color="red">*</font>
			<%=showError(request, "capSedeLegaleError") %>
		</td>
	</tr>
	
	<tr id ="searchcodeIdprovinciaTR">

		<td nowrap class="formLabel">
			Sigla Provincia
		</td>
		<td>
			
			<input type="text" readonly="readonly" required="required" name="searchcodeIdprovinciaTesto" id="searchcodeIdprovinciaTesto" />
			<input type="hidden"  required="required" name="searchcodeIdprovincia" id="searchcodeIdprovincia" />
			<font color="red">*</font>
						<%=showError(request, "provinciaSedeLegaleError") %>
			
			
			
		</td>
	</tr>
	
		
		<tr>
			<td nowrap class="formLabel">Denominazione</td>
			<td><input type="text" size="70"  id="ragioneSociale" class="required" name="ragioneSociale"><font color="red">*</font></td>
		</tr>
		
		
		
		
			
			<tr>
				<td class="formLabel" nowrap>Codice Fiscale Proprietario</td>
				<td><input type="text" name="codFiscaleSoggetto" readonly="readonly" placeholder="SELEZIONA DELEGA DAL LINK GESTIONE DELEGHE"
					id="codFiscaleSoggetto" class="required" value ="<%=(User.getSoggetto()!=null)?  User.getSoggetto().getCodFiscale() :"" %>" /> 
					<font color="red">*</font>
						<%=showError(request, "cfSoggettoFisicoError") %>
				</td>
			</tr>
			
			<tr>
				<td nowrap class="formLabel">Proprietario (cognome e nome)</td>
				<td><input type="text" readonly="readonly" size="70" id="nominativo" name="nome" class="required" value ="<%=(User.getSoggetto()!=null ) ? User.getSoggetto().getNome()+" "+User.getSoggetto().getCognome():""  %>">
				<input type = "hidden" name = "idSoggettoFisico" id="idSoggettoFisico" value = "<%=User.getSoggetto()!=null ? User.getSoggetto().getIdSoggetto() : "" %>" >
<!-- 				<input type ="button" onclick="javascript: $( '#dialogDelega' ).dialog('open')" value="Prendi da Delega"> -->
<!-- 				<input type ="button" onclick="javascript: $( '#dialogProprietario' ).dialog('open')" value="Inserisci Proprietario"> -->
				<font color="red">*</font>
				</td>

			</tr>
			
			
			<tr>
				<td class="formLabel" nowrap>Data Inizio Attivita</td>
				<td>
				<input type="text" size="70" name="dataInizio"
					id="dataInizioAttivita" readonly="readonly" class="required" placeholder="dd/MM/YYYY" <%=toDateasString(OrgDetails.getDataInizio()) %>>
					
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
			
			<input type="text" name="viaTesto" id="viaTesto" value="<%=(OrgDetails.getSedeOperativa()!=null) ? toHtml2(OrgDetails.getSedeOperativa().getVia()) : ""%>" />
			<font color="red">*</font>
			<%=showError(request, "viaSedeLegaleError") %>
		</td>
	</tr>
	
	
<!-- 	<tr> -->
<!-- 		<td nowrap class="formLabel"> -->
<!-- 			Localita Sede Legale -->
<!-- 		</td> -->
<!-- 		<td> -->
			
<!-- 			<input type="text" name="localitaSedeLegale" id="localitaSedeLegale" /> -->
<!-- 		</td> -->
<!-- 	</tr> -->
	
	<tr>
		<td nowrap class="formLabel">
			Tipo Attivita
		</td>
		<td>
		<%
		TipoAttivitaApi.setJsEvent("onchange='mostraCampoExtra(this.value);'");
		%>
			<%=TipoAttivitaApi.getHtmlSelect("idTipoAttivita", OrgDetails.getIdTipoAttivita()) %>
			
			<div id = "extraField" style="display: none">Annesso Laboratorio (Per Produzione Primaria) <input type = "checkbox" name="produzioneConLaboratorio"></div>
			<font color="red">*</font>
			      				<%=showError(request, "tipoAttivitaError") %>
			
			
		</td>
	</tr>
	
	<tr>
    		<td nowrap class="formLabel">
      			Domicilio Digitale<br>(Pec)
    		</td>
    		<td>
      			<input  type="email" size="100" name="domicilioDigitale" value="<%=toHtml2(OrgDetails.getDomicilioDigitale()) %>"  pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" required>    
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
      			<input type="text" size="70" name="fax" >    
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
			<input type="text" size="70" maxlength="16" id="codFiscale" class="required" name="codFiscale" value ="<%=(User.getSoggetto()!=null) ? User.getSoggetto().getCodFiscale() :"" %>">
		</td>
	</tr>
	</table>
	
	</fieldset>
	<br>
	<br>	
	
	
	
		<input type ="submit" value="SALVA">
</form>






<div id = "dialogDelega">
<div class="pager">
	Page: <select class="gotoPage"></select>		
	<img src="javascript/img/first.png" class="first" alt="First" title="First page" />
	<img src="javascript/img/prev.png" class="prev" alt="Prev" title="Previous page" />
	<img src="javascript/img//next.png" class="next" alt="Next" title="Next page" />
	<img src="javascript/img/last.png" class="last" alt="Last" title= "Last page" />
	<select class="pagesize">
		<option value="10">10</option>
		<option value="20">20</option>
		<option value="30">30</option>
		<option value="40">40</option>
	</select>
</div>


<table  class="tablesorter">

	<thead>
		<tr class="tablesorter-headerRow" role="row">

			<th aria-label="CODICE FISCALE DELEGATO No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRO PER DETENTORE" class="filter-false tablesorter-header"><div class="tablesorter-header-inner">CODICE FISCALE DELEGATO</div></th>
			<th aria-label="NOMINATIVO DELEGATO ( filter-match ): No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="1" data-placeholder="FILTRO PER NOMINATIVO" class="filter-false tablesorter-header"><div class="tablesorter-header-inner">NOMINATIVO DELEGATO</th>
			<th aria-label="CODICE FISCALE DELEGANTE ( filter-match ): No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="1" data-placeholder="FILTRO PER CODICE FISCALE DELEGANTE" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">CODICE FISCALE DELEGANTE</th>
			<th aria-label="NOMINATIVO DELEGANTE ( filter-match ): No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="1" data-placeholder="FILTRO PER NOMINATIVO DELEGANTE" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">NOMINATIVO DELEGANTE</th>
			<th aria-label="NOMINATIVO DELEGATO ( filter-match ): No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="1" data-placeholder="FILTRO PER NOMINATIVO" class="filter-false tablesorter-header"><div class="tablesorter-header-inner">DATA ASSEGNAZIONE DELEGA</th>
			<th aria-label="CODICE FISCALE DELEGATO No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRO PER DETENTORE" class="filter-false tablesorter-header"><div class="tablesorter-header-inner">&nbsp;</div></th>
			
		</tr>
	</thead>
	<tbody aria-relevant="all" aria-live="polite">
	
	<%
	if(ListaDeleghe.size()>0)
	{
		Iterator<Delega> itDelega = ListaDeleghe.iterator();
		while (itDelega.hasNext())
		{
			Delega thisDelega = itDelega.next();
			
			%>
			<tr>
			<td><%=toHtml2(thisDelega.getCodice_fiscale_delegato()) %></td>
			<td><%=toHtml2(User.getContact().getNameFirst() + " "+User.getContact().getNameLast())%></td>
			<td><%=toHtml2(thisDelega.getCodice_fiscale_delegante()) %></td>
			<td><%=toHtml2(thisDelega.getSoggetto_fisico_delegante().getNome()+" "+thisDelega.getSoggetto_fisico_delegante().getCognome()) %></td>
			<td><%=toDateasString(thisDelega.getData_assegnazione_delega()) %></td>
			<td><a href="#" onclick="javascript:selezionaDelega('<%=thisDelega.getSoggetto_fisico_delegante().getNome()+" "+thisDelega.getSoggetto_fisico_delegante().getCognome()%>',<%=thisDelega.getSoggetto_fisico_delegante().getIdSoggetto()%>,'<%=thisDelega.getSoggetto_fisico_delegante().getCodFiscale()%>');">Seleziona</a></td>
			</tr>
			<%
			
		}
	}else
	{
	%>
	<tr>
	<td colspan="6">Nessuna delega Presente</td>
	</tr>
	
	<%} %>
	</tbody>
	</table>
	
	<div class="pager">
	Page: <select class="gotoPage"></select>		
	<img src="javascript/img/first.png" class="first" alt="First" title="First page" />
	<img src="javascript/img/prev.png" class="prev" alt="Prev" title="Previous page" />
	<img src="javascript/img//next.png" class="next" alt="Next" title="Next page" />
	<img src="javascript/img/last.png" class="last" alt="Last" title= "Last page" />
	<select class="pagesize">
		<option value="10">10</option>
		<option value="20">20</option>
		<option value="30">30</option>
		<option value="40">40</option>
	</select>
</div>

</div>


<div id = "dialogProprietario">
<form name="addpersona" id="addpersona" action="ApicolturaAttivita.do?command=InsertPersona" method="POST">
<div id="messaggioErrore"></div>
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
				<td><input type="text" size="70" name="dataNascita"
					id="dataNascita" readonly="readonly" class="required" placeholder="dd/MM/YYYY">
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
					id="codFiscaleSoggettoAdd" class="required" value ="<%=(!CfUtil.extractCodiceFiscale(User.getContact().getCodiceFiscale()).equals("")) ? User.getContact().getCodiceFiscale():""  %>" />  	<div id="cfError"></div>
<!-- 					<input type="button" id="calcoloCF" class="newButtonClass" -->
<!-- 					value="Calcola Cod. Fiscale" -->
<!-- 					onclick="javascript:CalcolaCF(document.addpersona.sesso,document.addpersona.nome,document.addpersona.cognome,document.addpersona.comuneNascita,document.addpersona.dataNascita,'codFiscaleSoggettoAdd')"></input> -->
					
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
      			<input type="email" size="70" name="domicilioDigitale"  pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" required>    
    		</td>
  		</tr>

</table>
</form>
</div>
<script>
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
		        		document.getElementById("nominativo").value=data.nominativoSoggettoFisico ;
		        		document.getElementById("idSoggettoFisico").value=data.idSoggettoFisico ;
		        		alert(data.cfSoggettoFisico);
		        		document.getElementById("codFiscaleSoggettoinput").value=data.cfSoggettoFisico ;
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
