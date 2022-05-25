<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="ext.aspcfs.modules.apiari.base.Stabilimento"%>
<%@page import="ext.aspcfs.modules.apiari.base.StabilimentoList"%>
<jsp:useBean id="Operatore" class="ext.aspcfs.modules.apiari.base.Operatore" scope="request" />
<jsp:useBean id="ApiarioOrigine" class="ext.aspcfs.modules.apiari.base.Stabilimento" scope="request"/>
<jsp:useBean id="ModelloC" class="ext.aspcfs.modules.apiari.base.ModelloC"  scope="request"/>

<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/opu.css" />

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





<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/theme.css"></link>
<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/jquery.tablesorter.pages.css"></link>

<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.pager.js"></script>

<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.widgets.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/tableJqueryFilterApi.js"></script>


<script src="javascript/jquery.searchable-1.0.0.min.js"></script>
<!--   <link rel="stylesheet" type="text/css" media="screen" href="http://combogrid.justmybit.com/resources/css/smoothness/jquery-ui-1.10.1.custom.css"/> -->

  <link rel="stylesheet" type="text/css" media="screen" href="css/jquery.ui.combogrid.css"/>
  <script type="text/javascript" src="javascript/jquery.ui.combogrid.js"></script>
  <script>
  
  var messaggioControlloData = "Se la movimentazione richiede l'attestazione sanitaria, la data movimentazione deve essere successiva di almeno due giorni rispetto alla data modello";
  
  function checkForm()
  {
	  
	  formTest = true;
	    message = "";
	    alertMessage = "";
	   
	    form = document.formcessazione ;
	
	    if (document.editmovimentazione.dataInizioAttivita!=null && document.editmovimentazione.dataInizioAttivita.value=='' )
		  {
		  message += "- Data Inizio Attivita richiesto\r\n";
	        formTest = false;
		  }
	    if(document.editmovimentazione.recuperoMaterialeBiologico.value=="-1")
	    {
	    	message += "- Selezionare recupero materiale biologico\r\n";
	        formTest = false;
	    }
	    
	    var numAlveari = document.editmovimentazione.numAlveariDaSpostare.value=='' || document.editmovimentazione.numAlveariDaSpostare.value=='0';
	    var numSciami = document.editmovimentazione.numSciamiDaSpostare.value=='' || document.editmovimentazione.numSciamiDaSpostare.value=='0';
	    if(numAlveari && numSciami)
	    {
	    	 message += "- Digitare il numero di materiale da spostare\r\n";
		        formTest = false;
	    }
	    if (trim(document.editmovimentazione.cfPartitaIvaAziendaDestinazione.value).length != 16  && trim(document.editmovimentazione.cfPartitaIvaAziendaDestinazione.value).length != 11) {
	    	message += "- Il codice fiscale deve essere di 16 caratteri o di 11 in caso di partita iva.\r\n";
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
  
  

  $( document ).ready(function() {
	  
	  $( "#cfPartitaIvaAziendaDestinazione" ).combogrid({
		  debug:true,
		  async: false,
		  minLength: 8,
		  colModel: [{'columnName':'cfPartitaIva','width':'20','label':'CF Impresa'},{'columnName':'codiceFiscaleProprietario','width':'25','label':'CF Proprietario'},{'columnName':'ragioneSociale','width':'20','label':'Denominazione'},{'columnName':'indirizzoSedeLegale','width':'20','label':'Indirizzo'},{'columnName':'comuneSedeLegale','width':'15','label':'Comune'}],
		  url: 'ApicolturaAttivita.do?command=SearchAziendaCfPartitaIva&output=json',
		  select: function( event, ui ) 
		  {
			  if (ui!=null && ui.item!=null && ui.item.cfPartitaIva!=null && ui.item.cfPartitaIva!='')
			  {
				  selectAttivitaDestinazione(ui.item);
			  }
			  else
			  {
			  	alert("Azienda Non Presente in BDA");
			  }
			  return false;
		  }
	  });
  });
	  

  function controlloDataMovimentazione(dataMovimentazione,validazioneAsl,stampaAlert)
  {
	  var dataOdierna = '<%=(String)request.getAttribute("dataOdierna")%>';
	  if(validazioneAsl && dataMovimentazione!='')
	  {
		    if(giorni_differenza(dataOdierna,dataMovimentazione)<2) 
		    {
		    	document.getElementById('dataMovimentazione').value="";
		    	document.getElementById('richiestaValidazione').checked=false;
		    	if(stampaAlert)
		    		alert(messaggioControlloData);	
		    	return false;
		    }
	  }
	  return true;
  }
  
  function svuotaDestinazione()
  {
	  document.getElementById("cfPartitaIvaAziendaDestinazione").value="";
	  document.getElementById("ragioneSocialeIn").value="";
	  document.getElementById("indirizzoUbicazioneIn").value="";
	  document.getElementById("codiceApiarioDestinazione").value="";
	  document.getElementById("provinciaUbicazioneIn").value="";
  }
  
  function selectAttivitaDestinazione(item)
  {
  	document.getElementById("ragioneSocialeIn").value=item.ragioneSociale;
  	document.getElementById("cfPartitaIvaAziendaDestinazione").value=item.cfPartitaIva;
  	document.getElementById("indirizzoUbicazioneIn").value=item.indirizzoSedeLegale;
  	document.getElementById("codiceApiarioDestinazione").value=item.comuneSedeLegale;
  	document.getElementById("provinciaUbicazioneIn").value=item.provinciaSedeLegale;
  	
  	$(".table-wrapper").hide();
  }
  
</script>

<style>
input[readonly="readonly"]
{
    border:0;
}


</style>




<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ApicolturaAttivita.do?command=Home">ANAGRAFICA ATTIVITA</a> > 
<a href="ApicolturaMovimentazioni.do?command=List">MOVIMENTAZIONI </a> >
<a href="ApicolturaMovimentazioni.do?command=ToAddRichiesta">NUOVA MOVIMENTAZIONE</a> >
IMPOLLINAZIONE
</td>
</tr>
</table>
<br>

<%
if (request.getAttribute("Error")!=null && ((String)request.getAttribute("Error")).indexOf("duplicati_movimentazioni")>0)
{
	%>
	<font color="red">Errore: la movimentazione che si vuole inserire esiste già</font>
	<%
}

%>

<form  name="editmovimentazione" action="ApicolturaMovimentazioni.do?command=Modifica&auto-populate=true" method="POST" onsubmit="return checkForm()">

<input type="hidden" name="idOperatore" id="idOperatore" value="<%=Operatore.getIdOperatore()%>">
<input type="hidden" name="id" id="id" value="<%=ModelloC.getId()%>">
<input type="hidden" name="numero_modello" id="numero_modello" value="<%=ModelloC.getNumero_modello()%>">
	
	<fieldset>
		<legend><b>MODIFICA MOVIMENTAZIONE</b></legend>
		<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		 <tr>
    <th colspan="2">
      <strong>AZIENDA</strong>
    </th>
  </tr>
			<tr>
				<td nowrap class="formLabel">Tipo di Movimentazione</td>
				<td>
					IMPOLLINAZIONE <input type="hidden" required="required"  name="idTipoMovimentazione" id = "tipoMovimentazion3" value="3">
				</td>
			</tr>
			<tr>
			<td nowrap class="formLabel">Data Movimentazione</td>
			<td>
			<input type= "text" name = "dataMovimentazione" value="<%=toDateasString(ModelloC.getDataMovimentazione())%>" required/>
			<a href="#"  onClick="cal19.select(document.forms['editmovimentazione'].dataMovimentazione,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
							<%=showError(request, "dataMovimentazioneError") %>
			
			</td>
		</tr>
		
		<tr>
			<td nowrap class="formLabel">Data Modello</td>
			<td>
				<%=toDateasString(ModelloC.getData_modello())%>
			<input type= "hidden" name = "data_modello"  value="<%=toDateasString(ModelloC.getData_modello())%>"/>
			</td>
		</tr>
		
		<tr>
			<td nowrap class="formLabel">Codice</td>
			<td>
			<%=Operatore.getCodiceAzienda() %>
			<input type = "hidden" name = "codiceAziendaOrigine" value = "<%=Operatore.getCodiceAzienda() %>">
			</td>
		</tr>
		
		<tr>
			<td nowrap class="formLabel">Denominazione</td>
			<td>
			<input type="hidden" name = "idAzienda" value="<%=Operatore.getIdOperatore() %>">
			<%=Operatore.getRagioneSociale() %>
			
			</td>
		</tr>
		
		
			<tr>
				<td class="formLabel" nowrap>Codice Fiscale Proprietario</td>
				<td>
				<%=Operatore.getRappLegale().getCodFiscale() %>
					
				</td>
			</tr>
			
			<tr>
				<td nowrap class="formLabel">Proprietario (cognome e nome) </td>
				<td>
				<%=Operatore.getRappLegale().getCognome() +" "+ Operatore.getRappLegale().getNome() %>
				</td>

			</tr>
			
			<!-- tr>
			<td nowrap class="formLabel">Richiesta validazione ASL</td>
			<td>
				<input type="checkbox" name="richiestaValidazione" id="richiestaValidazione" onclick="controlloDataMovimentazione(document.getElementById('dataMovimentazione').value,document.getElementById('richiestaValidazione').checked, true)" ><i>Per le destinazioni in regione la validazione non sarà in nessun caso necessaria</i>
			</td>
		</tr-->
		
		<tr>
			<td class="formLabel" nowrap>
				Recupero materiale biologico
			</td>
			<td>
				<select id="recuperoMaterialeBiologico" name="recuperoMaterialeBiologico">
					<option value="-1">Selezionare una voce</option>
					<option value="true"
					<%
					if(ModelloC.getRecuperoMaterialeBiologico())
					{
					%>
						selected="selected"
					<%
					}
					%>
					>SI</option>
					<option value="false"
					<%
					if(!ModelloC.getRecuperoMaterialeBiologico())
					{
					%>
						selected="selected"
					<%
					}
					%>
					>NO</option>
				</select>
				<font color = "red">*</font>
			</td>
		</tr>
			
			
	</table>
	
	<BR>
	<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details" id = "dati_apiario">
		 <tr>
   
   <tr><th colspan="2"><strong>APIARIO DA MOVIMENTARE</strong></th></tr>
				  <tr><td nowrap class="formLabel">Comune</td><td>
				 
				
				 <input type="text"  size="50" readonly="readonly" name="codiceApiario" required id="apiario" value="<%=ApiarioOrigine.getSedeOperativa().getDescrizioneComune()%>" >
				 
				 
				 </td></tr>

				 <tr><td nowrap class="formLabel">Provincia</td><td>
				 <input type="hidden" id="idApiario" value="<%=ApiarioOrigine.getIdBda() %>" name="idBdaApiarioOrigine" required >
				 <input type="text" value="<%=ApiarioOrigine.getSedeOperativa().getSiglaProvincia() %>" size="50"  readonly="readonly" name="provinciaUbicazione" id="provinciaUbicazione"></td></tr>
				 <tr><td nowrap class="formLabel">Indirizzo</td><td><input type="text"  size="50"  value="<%=ApiarioOrigine.getSedeOperativa().getVia() %>" readonly="readonly" name="indirizzoUbicazione" required id="indirizzoUbicazione"></td></tr>	
				 <tr><td nowrap class="formLabel">Detentore</td><td><input type="text"  size="50"  value="<%=ApiarioOrigine.getDetentore().getNome() + " "+ApiarioOrigine.getDetentore().getCognome() %>" readonly="readonly" name="detentore" required id="detentore"></td></tr>	
				 <tr><td nowrap class="formLabel">Codice Fiscale Detentore</td><td><input type="text"  size="50"  value="<%=ApiarioOrigine.getDetentore().getCodFiscale() %>" readonly="readonly" name="cfDetentore" id="cfDetentore"></td></tr>	
				 <tr><td nowrap class="formLabel">PROGRESSIVO</td><td><input type="text"  size="50"  value="<%=ApiarioOrigine.getProgressivoBDA() %>" readonly="readonly" name="progressivoApiarioOrigine" required id="progressivo"></td></tr>	
    <tr><td nowrap class="formLabel">Numero Alveari Presenti</td><td><input type="text"  size="50"  value="<%=ApiarioOrigine.getNumAlveari() %>" readonly="readonly" name="numApiariOrigine" required id="numAlveari"></td></tr>
    <tr><td nowrap class="formLabel">Numero Sciami/Nuclei Presenti</td><td><input type="text"  size="50"  value="<%=ApiarioOrigine.getNumSciami() %>" readonly="readonly" name="numSciamiOrigine" required id="numSciami"></td></tr>
    <tr><td nowrap class="formLabel">Numero Pacchi Presenti</td><td><input type="text"  size="50"  value="<%=ApiarioOrigine.getNumPacchi() %>" readonly="readonly" name="numPacchiOrigine" required id="numPacchi"></td></tr>
    <tr><td nowrap class="formLabel">Numero Api Regine Presenti</td><td><input type="text"  size="50"  value="<%=ApiarioOrigine.getNumRegine() %>" readonly="readonly" name="numRegineOrigine" required id="numRegine"></td></tr>
    <tr><td nowrap class="formLabel">Numero Alveari Da Spostare</td><td><input type="number"  size="50"         name="numAlveariDaSpostare"  id="numAlveariDaSpostare" value="<%=ModelloC.getNumAlveariDaSpostare()%>"></td></tr>
    <tr><td nowrap class="formLabel">Numero Sciami/Nuclei Da Spostare</td><td><input type="number"  size="50" name="numSciamiDaSpostare"  id="numSciamiDaSpostare" value="<%=ModelloC.getNumSciamiDaSpostare()%>"></td></tr>
 
  </table>
  
  <br>
  
  <table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details" >
		 <tr>
   
   <tr><th colspan="2"><strong>ATTIVITA DI DESTINAZIONE</strong></th></tr>
   
   
  <tr>
  	<td nowrap class="formLabel">
  		CODICE FISCALE/PARTITA IVA
  	</td>
  	<td>
  		<input type="text" value="<%=ModelloC.getCfPartitaApicoltore()%>"  size="50" min="5" name="cfPartitaIvaAziendaDestinazione" required id="cfPartitaIvaAziendaDestinazione" placeholder="DIGITARE IL CODICE FISCALE/PARTITA IVA">
  	</td>
  </tr>
 <tr>
 	<td nowrap class="formLabel">
 		DENOMINAZIONE
 	</td>
 	<td>
 		<input type="text" value="<%=ModelloC.getDenominazioneApicoltore()%>"  size="50" id="ragioneSocialeIn" name="ragioneSocialeIn" >
 	</td>
 </tr>
 <tr>
 	<td nowrap class="formLabel">
 		INDIRIZZO SEDE LEGALE
 	</td>
 	<td>
 		<input type="text"  size="50"  value="<%=ModelloC.getIndirizzo_dest()%>" name="indirizzoUbicazioneIn" id="indirizzoUbicazioneIn">
 	</td>
 </tr>	
 <tr>
 	<td nowrap class="formLabel">
 		COMUNE SEDE LEGALE
 	</td>
 	<td>
 		<input type="text"    size="50"  value="<%=ModelloC.getComune_dest()%>" name="codiceApiarioDestinazione" required id="codiceApiarioDestinazione">
 		<input type="hidden"  size="50"  name="provinciaUbicazioneIn" required id="provinciaUbicazioneIn" value="<%=ModelloC.getSigla_prov_comune_dest()%>">
 	</td>
 </tr>	
  
  </table>
  <br>
   
	</fieldset>
	
	
	
		<input type ="submit" value="MODIFICA E INVIA IN BDN" >
</form>


