<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="ext.aspcfs.modules.apiari.base.Stabilimento"%>
<%@page import="ext.aspcfs.modules.apiari.base.StabilimentoList"%>
<jsp:useBean id="ApicolturaSottospecie" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ApicolturaModalita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ApicolturaClassificazione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoAttivitaApi" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ModelloC" class="ext.aspcfs.modules.apiari.base.ModelloC"  scope="request"/>

<jsp:useBean id="NazioniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Operatore" class="ext.aspcfs.modules.apiari.base.Operatore" scope="request" />
<jsp:useBean id="ApiarioOrigine" class="ext.aspcfs.modules.apiari.base.Stabilimento" scope="request"/>
<jsp:useBean id="OperatoreDestinazione" class="ext.aspcfs.modules.apiari.base.Operatore" scope="request" />





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
	    if (document.editmovimentazione.provinciaUbicazioneIn!=null && document.editmovimentazione.provinciaUbicazioneIn.value=='' )
		  {
		  message += "- Selezionare l'apiario di destinazione\r\n";
	        formTest = false;
		  }
	    
	    if (document.editmovimentazione.numRegineDaSpostare!=null && document.editmovimentazione.numRegineDaSpostare.value==''  )
		  {
		  message += "- Valorizzare il numero di api regine\r\n";
	        formTest = false;
		  }
	    
	    if (document.editmovimentazione.numRegineDaSpostare!=null && document.editmovimentazione.numRegineDaSpostare.value!='' && parseInt(document.editmovimentazione.numRegineDaSpostare.value)<=0 )
		  {
		  message += "- Valorizzare il numero di api regine con un numero positivo\r\n";
	        formTest = false;
		  }
	    
	    
	    
	    //controlloDataMovimentazione(document.getElementById('dataMovimentazione').value,document.getElementById('richiestaValidazione').checked);
	    
	    if (formTest == false) {
	        alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
	        return false;
	      } else {
	      
	        loadModalWindow();
	        return true;
	      }
	 
  }
  
  

  $( document ).ready(function() {
	  
	  $( "#codiceAziendaDestinazione" ).combogrid({
		  debug:true,
		  async: false,
		  minLength: 8,
		  colModel: [{'columnName':'codiceAzienda','width':'20','label':'Codice'},{'columnName':'comune','width':'45','label':'Comune'},{'columnName':'proprietario','width':'30','label':'Proprietario'}],
		  url: 'ApicolturaAttivita.do?command=SearchAzienda&output=json',
		  select: function( event, ui ) {
			  if (ui!=null && ui.item!=null && ui.item.id>0)
				  {
				  if('<%=Operatore.getCodiceAzienda().toUpperCase()%>'==ui.item.codiceAzienda.toUpperCase())
				  {
					   alert("Non è possibile selezionare come azienda di destinazione la stessa azienda di origine");
				  	   $( "#codiceAziendaDestinazione" ).val("");
				  }
				  else
				  {
				       selectAttivitaDestinazione(ui.item);
				  }
				  
				  }
			  else
			  {
			  alert("Azienda Non Presente in BDA");
			  $( "#codiceAziendaDestinazione" ).val("");
			  } 
			 
			  return false;
		  }
	  });
  });
	  

  



	  function selectApiarioDestinazione(progressivo,idApiario,comuneUbicazione,provinciaUbicazione,indirizzoApiario,detentore,cfDetentore,numAlveari,latitudine,longitudine)
	  {
	  	document.getElementById("idApiarioIn").value=idApiario;
	  	document.getElementById("detentoreIn").value=detentore;
	  	document.getElementById("cfDetentoreIn").value=cfDetentore;
	  	document.getElementById("codiceApiarioDestinazione").value=comuneUbicazione;
	  	document.getElementById("progressivoIn").value=progressivo;
	  	document.getElementById("numAlveariIn").value=numAlveari;
	  	
		document.getElementById("latitudine_dest").value=latitudine;
	  	document.getElementById("longitudine_dest").value=longitudine;
	  	
	  	$(".table-wrapper").hide();
	  	
	  }
	  
  
  function selectAttivitaDestinazione(item)
  {
  	document.getElementById("ragioneSocialeIn").value=item.ragioneSociale;
  	document.getElementById("nominativoIn").value=item.proprietario;
  	document.getElementById("codiceAziendaDestinazione").value=item.codiceAzienda;

  	
  	document.getElementById("cfPropIn").value=item.cfProprietario;
  	document.getElementById("indirizzoIn").value=item.indirizzo;
  	document.getElementById("comuneIn").value=item.comune;
  	document.getElementById("idImpresaIn").value=item.id;
  	
  	$(".table-wrapper").hide();
  	
  	idImpresaIn = document.getElementById("idImpresaIn").value ;
  	
  	var codAzienda = $( "#codiceAziendaDestinazione" ).val();
  	 $( "#codiceApiarioDestinazione" ).combogrid({
		  debug:true,
		  loadMsg: 'Searching...',
		  colModel: [  {'columnName':'comune','width':'20','label':'Comune'},{'columnName':'indirizzo','width':'20','label':'Indirizzo'},{'columnName':'detentore','width':'45','label':'Detentore'}],
		  url: 'ApicolturaApiari.do?command=SearchApiari&codiceAzienda='+codAzienda+'&output=json',
		  success :function( event, ui ) {alert(ui);},
				  select: function( event, ui ) {
					  
					
					  if(ui.item.id>0)
					  {
					  		selectApiarioDestinazione(ui.item.progressivo,ui.item.id,ui.item.comune,ui.item.provincia,ui.item.indirizzo,ui.item.detentore,ui.item.cfDetentore,ui.item.numAlveari,ui.item.latitudine,ui.item.longitudine);
			 		  }
					  else
					  {
					  	alert("Non Esistono Altri Apiari");
					  	$( "#codiceApiarioDestinazione" ).val("");
					  }
			   
			  return false;
		  }
	  });
  	
  	
  	
  	
  }


 
  
  function svuotaDestinazione()
  {
	  	document.getElementById("ragioneSocialeIn").value="";
	  	document.getElementById("nominativoIn").value="";
	  	document.getElementById("cfPropIn").value="";
	  	document.getElementById("indirizzoIn").value="";
	  	document.getElementById("comuneIn").value="";
	  	document.getElementById("idImpresaIn").value="";

  }
  
  
  function controlloDataMovimentazione(dataMovimentazione,validazioneAsl)
  {
	  var dataOdierna = '<%=(String)request.getAttribute("dataOdierna")%>';
	  if(validazioneAsl && dataMovimentazione!='')
	  {
		    if(giorni_differenza(dataOdierna,dataMovimentazione)<2) 
		    {
		    	document.getElementById('dataMovimentazione').value="";
		    	document.getElementById('richiestaValidazione').checked=false;
		    	alert("Se la movimentazione richiede l'attestazione sanitaria, la data movimentazione deve essere successiva di almeno due giorni rispetto alla data modello");	
		    	
		    }
	  }
  }

function selectApiarioOrigine(progressivo,idApiario,comuneUbicazione,provinciaUbicazione,indirizzoApiario,detentore,cfDetentore,numAlveari)
{
	document.getElementById("idApiario").value=idApiario;
	document.getElementById("indirizzoUbicazione").value=indirizzoApiario;
	document.getElementById("provinciaUbicazione").value=provinciaUbicazione;
	document.getElementById("detentore").value=detentore;
	document.getElementById("cfDetentore").value=cfDetentore;
	document.getElementById("apiario").value=comuneUbicazione;
	document.getElementById("progressivo").value=progressivo;
	document.getElementById("numAlveari").value=numAlveari;

	
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
			<a href="ApicolturaMovimentazioni.do?command=ToAddRichiesta">MODIFICA MOVIMENTAZIONE</a> >
			COMPRAVENDITA API REGINE
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
<input type="hidden" name="id"          id="id" value="<%=ModelloC.getId()%>">
	
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
				COMPRAVENDITA DI API REGINE<input type="hidden" required="required"  name="idTipoMovimentazione" id = "tipoMovimentazion3" value="4">
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
			
			<tr>
			<!-- td nowrap class="formLabel">Richiesta validazione ASL</td -->
			<!-- td>
				<input type="checkbox" name="richiestaValidazione" id="richiestaValidazione" onclick="controlloDataMovimentazione(document.getElementById('dataMovimentazione').value,document.getElementById('richiestaValidazione').checked)" ><i>Per le destinazioni in regione la validazione non sarà in nessun caso necessaria</i>
			</td-->
		</tr>
			
			
	</table>
	
	<BR>
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" id = "dati_apiario">
			<tr>
				<th colspan="2">
					<strong>MATERIALE DA MOVIMENTARE</strong>
				</th>
			</tr>
		    
            <tr>
             	<td nowrap class="formLabel">
             		Numero Api Regine Da Spostare
             	</td>
             	<td>
             		<input type="number"  size="50"      name="numRegineDaSpostare"  id="numRegineDaSpostare" value="<%=ModelloC.getNumRegineDaSpostare()%>">
             	</td>
            </tr>
  </table>
  
  <br>
  
  <table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details" >
		 <tr>
   
   <tr>
   		<th colspan="2">
   			<strong>ATTIVITA DI DESTINAZIONE</strong>
   		</th>
   	</tr>
   
  	<tr>
  		<td nowrap class="formLabel">
  			CERCA PER CODICE AZIENDA
  		</td>
  		<td>
  			<input type="text" value=""  size="50" min="5" onchange="svuotaDestinazione()" name="codiceAziendaDestinazione" required id="codiceAziendaDestinazione" placeholder="DIGITARE IL CODICE AZIENDA">
  		</td>
  	</tr>

	<tr>
		<td nowrap class="formLabel">
			RAGIONE SOCIALE
		</td>
		<td>
			<input type="hidden" id="idImpresaIn" name="idImpresaIn" required value="">
			<input type="text" value=""  size="50"  readonly="readonly"  id="ragioneSocialeIn" name="ragioneSocialeIn">
		</td>
	</tr>
	<tr>
		<td nowrap class="formLabel">
			PROPRIETARIO
		</td>
		<td>
			<input type="text"  size="50"  value="" readonly="readonly" name="nominativoIn" required id="nominativoIn">
		</td>
	</tr>
	<tr>
		<td nowrap class="formLabel">
			CODICE FISCALE PROPRIETARIO
		</td>
		<td>
			<input type="text"  size="50"  value="" readonly="readonly" name="cfPropIn" required id="cfPropIn">
		</td>
	</tr>	
	<tr>
		<td nowrap class="formLabel">
			INDIRIZZO
		</td>
		<td>
			<input type="text"  size="50"  value="<%=ModelloC.getIndirizzo_dest()%>" readonly="readonly" name="indirizzoIn" id="indirizzoIn">
		</td>
	</tr>	
	<tr>
		<td nowrap class="formLabel">
			COMUNE
		</td>
		<td>
			<input type="text"  size="50"  value="<%=ModelloC.getComune_dest()%>" readonly="readonly" name="comuneIn" required id="comuneIn">
		</td>
	</tr>	
  </table>
  
  
  
  
  <br>
  
  
 
	</fieldset>
	
	
	
		<input type ="submit" value="MODIFICA" >
</form>


<script type="text/javascript">
		document.getElementById("ragioneSocialeIn").value='<%=(OperatoreDestinazione!=null && OperatoreDestinazione.getRagioneSociale()!=null && !OperatoreDestinazione.getRagioneSociale().equals(""))?(OperatoreDestinazione.getRagioneSociale()):(ModelloC.getDenominazioneApicoltore())%>';
		document.getElementById("nominativoIn").value='<%=(OperatoreDestinazione!=null && OperatoreDestinazione.getRappLegale()!=null && !OperatoreDestinazione.getRappLegale().getNome().equals(""))?(OperatoreDestinazione.getRappLegale().getNome() + " " + OperatoreDestinazione.getRappLegale().getCognome()):(ModelloC.getProprietarioDestinazione())%>';
		document.getElementById("codiceAziendaDestinazione").value='<%=(OperatoreDestinazione!=null && OperatoreDestinazione.getCodiceAzienda()!=null && !OperatoreDestinazione.getCodiceAzienda().equals(""))?(OperatoreDestinazione.getCodiceAzienda()):(ModelloC.getCodiceAziendaDestinazione())%>';
		document.getElementById("cfPropIn").value='<%=(OperatoreDestinazione!=null && OperatoreDestinazione.getRappLegale()!=null)?(OperatoreDestinazione.getRappLegale().getCodFiscale()):(ModelloC.getCfProprietarioDestinazione())%>';
		document.getElementById("indirizzoIn").value='<%=(OperatoreDestinazione!=null && OperatoreDestinazione.getSedeLegale()!=null)?(OperatoreDestinazione.getSedeLegale().getVia()):(ModelloC.getIndirizzo_dest())%>';
		document.getElementById("comuneIn").value='<%=(OperatoreDestinazione!=null && OperatoreDestinazione.getSedeLegale()!=null)?(OperatoreDestinazione.getSedeLegale().getDescrizioneComune()):(ModelloC.getComune_dest())%>';
		document.getElementById("idImpresaIn").value='<%=OperatoreDestinazione.getIdOperatore()%>';
<%
	if(ModelloC.getApiarioDestinazione()!=null && ModelloC.getApiarioDestinazione().getIdApiario()>0)
	{
%>
		selectApiarioDestinazione('<%=ModelloC.getApiarioDestinazione().getProgressivoBDA()%>','<%=ModelloC.getApiarioDestinazione().getIdStabilimento()%>','<%=ModelloC.getApiarioDestinazione().getSedeOperativa().getComuneTesto()%>','<%=ModelloC.getApiarioDestinazione().getSedeOperativa().getDescrizione_provincia()%>','<%=ModelloC.getApiarioDestinazione().getSedeOperativa().getVia()%>','<%=ModelloC.getApiarioDestinazione().getDetentore().getNome()%>','<%=ModelloC.getApiarioDestinazione().getDetentore().getCodFiscale()%>','<%=ModelloC.getApiarioDestinazione().getNumAlveari()%>','<%=ModelloC.getApiarioDestinazione().getSedeOperativa().getLatitudine()%>','<%=ModelloC.getApiarioDestinazione().getSedeOperativa().getLongitudine()%>');

<%
	}		
%>	
</script>

