<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="org.aspcfs.modules.util.imports.ApplicationProperties"%>
<%@page import="ext.aspcfs.modules.apiari.base.StabilimentoVariazioneCensimento"%>
<%@page import="ext.aspcfs.modules.apiari.base.StabilimentoVariazioneUbicazione"%>
<%@page import="org.apache.batik.css.engine.value.ListValue"%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>

<jsp:useBean id="ListaVariazioni" class="ext.aspcfs.modules.apiari.base.VariazioneCensimentoList" scope="request"/>
<jsp:useBean id="SearchVariazioniCensiListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="LookupTipoMovimentazione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StabilimentoDetails" class="ext.aspcfs.modules.apiari.base.Stabilimento" scope="request" />
<%@page import="java.util.Date" %> 

<%@ include file="../initPage.jsp" %>

   <script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>

<table class="trails" cellspacing="0">
<tr>
<td width="100%">
APICOLTURA >
VARIAZIONI CENSIMENTI
	

</td>
</tr>
</table>
<br>

<%

String param = "stabId="+StabilimentoDetails.getIdStabilimento()+"&opId=" 
		+ StabilimentoDetails.getIdOperatore()+"&searchcodeidApiario="+StabilimentoDetails.getIdStabilimento()+"&searchcodeidAzienda="+StabilimentoDetails.getIdOperatore() +"&searchcodeCodiceAziendaSearch="+StabilimentoDetails.getOperatore().getCodiceAzienda()+"&searchcodeProgressivoApiarioSearch="+StabilimentoDetails.getProgressivoBDA() ;


%>

<dhv:container name="apiari" selected="Scheda"
	object="Operatore" param="<%=param%>" hideContainer="false">
	<%
	if(ListaVariazioni.size() > 0)
		{
		%>	
			 <br>
			<input value="Report XLS Censimenti Apiario" type="button" id= "btnReportCensimenti" onclick="javascript: window.open('GenerazioneExcel.do?command=GetExcel&tipo_richiesta=censimentiApiari&idApiario=<%=((StabilimentoVariazioneCensimento) ListaVariazioni.get(0) ).getIdApicoltoreApiario() %>');" />
			<br>
		<%} 
		else {%>
			<br>
			<!-- se non ci sono censimenti associati, creo bottone ma con comportamento dummy -->
			<input value="Report XLS Censimenti" type="button" id= "btnReportCensimenti" onclick="javascript: alert('Attenzione, non esistono censimenti associati');" />
			<br>
		<% } %>
<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="SearchVariazioniCensiListInfo"/>
<% int columnCount = 0; %>
<table cellpadding="8" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    
   <th>Data Inserimento</th>
   <th>Data Censimento</th>
   <th>Numero Alveari</th>
   <th>Numero Sciami /Nuclei</th>
   <th>Numero Pacchi Api</th>
    <th>Numero Api Regine</th>
     <th>Stampa</th>
    <th>Operazioni</th>
  
   
  </tr>
<%
	Iterator j = ListaVariazioni.iterator();
	if ( j.hasNext() ) {
    int rowid = 0;
    int i = 0;
    while (j.hasNext()) {
    i++;
    rowid = (rowid != 1 ? 1 : 2);
    StabilimentoVariazioneCensimento thisMovimentazione = (StabilimentoVariazioneCensimento)j.next();
%>

  <tr class="row<%= rowid %>">
	<td><%=toDateasString(thisMovimentazione.getEntered())%></td>
	<td><%=toDateasString(thisMovimentazione.getDataAssegnazioneCensimento())%></td>
	<td><%=thisMovimentazione.getNumAlveari()%></td>
	<td><%=thisMovimentazione.getNumSciami()%></td>
	<td><%=thisMovimentazione.getNumPacchi()%></td>
	<td><%=thisMovimentazione.getNumRegine()%></td>
	<td> 
        <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Stampa" value="Stampa"	onClick="openRichiestaPDFCensimento('<%= thisMovimentazione.getId() %>', 'VariazioneCensimento');">
    </td>
    <td> 
<%
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");  

	//Limite inferiore
	Date dateFrom = null;
	if(thisMovimentazione.getDataAssegnazioneCensimento()!=null)
		dateFrom = new Date(thisMovimentazione.getDataAssegnazioneCensimento().getTime());
	
	//Data attuale
	Date dateCurr = (Date)request.getAttribute("dataOdierna");
	
	//Limite superiore
	Calendar cTo = null;
	Date dateTo = null;
	if(thisMovimentazione.getDataAssegnazioneCensimento()!=null)
	{
		cTo = Calendar.getInstance();
		cTo.setTime(dateFrom);
		cTo.add(Calendar.DATE, Integer.parseInt(ApplicationProperties.getProperty("tolleranza_elimina_censimenti")));
		dateTo = new Date(cTo.getTimeInMillis());
	}
	
	System.out.println(dateCurr +  " " + dateFrom + " " + dateTo);
	if(thisMovimentazione.getDataAssegnazioneCensimento()!=null && dateCurr.after(dateFrom) && dateCurr.before(dateTo))
	{
%>
        <!-- 
        <input type="button" title="Modifica" value="Modifica" onClick="javascript: modificacensimento('<%=StabilimentoDetails.getIdStabilimento() %>','<%=thisMovimentazione.getId()%>','<%=toDateasString(thisMovimentazione.getDataCensimento())%>',
        																							   '<%=thisMovimentazione.getNumAlveari()%>',
        																							   '<%=thisMovimentazione.getNumSciami()%>',
																									   '<%=thisMovimentazione.getNumPacchi()%>',
																									   '<%=thisMovimentazione.getNumRegine()%>')">
		 -->


		<input type="button" title="Elimina" value="Elimina" onClick="javascript: eliminacensimento('<%=StabilimentoDetails.getIdStabilimento() %>','<%=thisMovimentazione.getId()%>')">
<%
	}
%>    
    </td>
	
	
	
	
     
  </tr>
<%}%>
<%} else {
%>
  <tr class="containerBody">
    <td colspan="7" >
      Nessun Censimento trovato con i parametri di ricerca specificati<br />
    </td>
  </tr>
<%}%>
</table>
<br />

<div id = "dialogDeleteCensimento">



<form name="deletecensimento" id="deletecensimento" action="ApicolturaApiari.do?command=DeleteCensimento&autopopulate=true" method="POST">

<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">


<input type ="hidden" name = "idStabilimento" value = "" />
<input type ="hidden" name = "idCensimento" value = "" />
		
	<tr>
				<td nowrap class="formLabel">Ragione Sociale </td>
				<td>
				
				<div ><%=StabilimentoDetails.getOperatore().getRagioneSociale() %></div>
				</td>

			</tr>
			
			<tr>
				<td nowrap class="formLabel">Proprietario </td>
				<td>
				
				<div ><%=StabilimentoDetails.getOperatore().getRappLegale()!= null ? StabilimentoDetails.getOperatore().getRappLegale().getNome()+" "+StabilimentoDetails.getOperatore().getRappLegale().getCognome() :"" %></div>
				</td>

			</tr>
			<tr>
				<td nowrap class="formLabel">Note Eliminazione</td>
				<td>
				
				<textarea name = "note" required="required"></textarea>
				</td>

			</tr>
			</table>
			
			</form>
			
			</div>
			
<div id = "dialogCensimento">



<form name="editcensimento" id="editcensimento" action="ApicolturaApiari.do?command=ModificaCensimento&autopopulate=true" method="POST">

<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">


<input type ="hidden" name = "idStabilimento" value = "" />
<input type ="hidden" name = "idCensimento" value = "" />
		
	<tr>
				<td nowrap class="formLabel">Ragione Sociale </td>
				<td>
				
				<div ><%=StabilimentoDetails.getOperatore().getRagioneSociale() %></div>
				</td>

			</tr>
			
			<tr>
				<td nowrap class="formLabel">Proprietario </td>
				<td>
				
				<div ><%=StabilimentoDetails.getOperatore().getRappLegale()!= null ? StabilimentoDetails.getOperatore().getRappLegale().getNome()+" "+StabilimentoDetails.getOperatore().getRappLegale().getCognome() :"" %></div>
				</td>

			</tr>
			 <tr>
		<td nowrap class="formLabel">Data Censimento</td>
		<td>
			
					<label id="data_assegnazione_censimento_label"></label>
					<input type ="hidden" name="data_assegnazione_censimento" readonly="readonly" value="" required="required">
					
					<!--  a href="#" onClick="cal19.select(document.forms['addcensimento'].data_assegnazione_censimento,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19"-->
				<!--  img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a-->
				
					
			
		</td>
	</tr>
			<tr>
				<td nowrap class="formLabel">Numero Alveari </td>
				<td>
				
				<input type = "text" name = "numAlveari" required="required">
				</td>

			</tr>
			
			<tr>
				<td nowrap class="formLabel">Numero Sciami/Nuclei </td>
				<td>
				
<input type = "text" name = "numSciami" required="required">
				</td>

			</tr>
			<tr>
			<td nowrap class="formLabel">Numero Pacchi d'Api</td>
			
			<td>
			<input type = "text" name = "numPacchi" id = "numPacchi" style="width: 50px;">
			
			</td>
		
		</tr>
		<tr>
			<td nowrap class="formLabel">Numero Api Regine</td>
			
			<td>
			<input type = "text" name = "numRegine" id = "numRegine" style="width: 50px;">
			
			</td>
		
		</tr>
			
			</table>
			
			</form>
			
			</div>
			
			
<dhv:pagedListControl object="SearchVariazioniCensiListInfo" tdClass="row1"/>
</dhv:container>





<script type="text/javascript">



function checkFormCensimento()
{
	
	
	 	formTest = true;
	    message = "";
	    alertMessage = "";
	   
	    form = document.editcensimento ;
	
	   if (form.data_assegnazione_censimento.value == ""){
	        message += "- Data Censimento richiesto\r\n";
	        formTest = false;
	     }
	   
	    if (form.numAlveari.value == ""){
	        message += "- Num Alveari richiesto\r\n";
	        formTest = false;
	     }
		 if (form.numSciami.value == ""){
	 	        message += "- Num Sciami/Nuclei richiesto\r\n";
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

function checkFormDeleteCensimento()
{
	
	
	 	formTest = true;
	    message = "";
	    alertMessage = "";
	   
	    form = document.deletecensimento ;
	
	   if (form.note.value == ""){
	        message += "- Note richiesto\r\n";
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

$( "#dialogCensimento" ).dialog({
	autoOpen: false,
    resizable: false,
    closeOnEscape: false,
   	title:"MODIFICA",
    width:850,
    height:500,
    position: 'top',
    draggable: false,
    modal: true,
    buttons:{
    	 "Modifica": function() {if (checkFormCensimento()){$("#editcensimento").submit();} } ,
    	 "Esci" : function() { $(this).dialog("close");
    	 $("html,body").animate({scrollTop: 0}, 500, function(){});
    	 }
    	
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

$( "#dialogDeleteCensimento" ).dialog({
	autoOpen: false,
    resizable: false,
    closeOnEscape: false,
   	title:"ELIMINA CENSIMENTO",
    width:850,
    height:500,
    position: 'top',
    draggable: false,
    modal: true,
    buttons:{
    	 "Elimina": function() {if (checkFormDeleteCensimento()){$("#deletecensimento").submit();} } ,
    	 "Esci" : function() { $(this).dialog("close");
    	 $("html,body").animate({scrollTop: 0}, 500, function(){});
    	 }
    	
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



	function modificacensimento(idStabilimento,id,data,alveari,sciami,pacchi,regine)
	{
		document.editcensimento.idStabilimento.value=idStabilimento;
		document.editcensimento.idCensimento.value=id;
		document.editcensimento.data_assegnazione_censimento.value=data;
		document.editcensimento.data_assegnazione_censimento_label.value=data;
		document.editcensimento.numAlveari.value=alveari;
		document.editcensimento.numPacchi.value=pacchi;
		document.editcensimento.numSciami.value=sciami;
		document.editcensimento.numRegine.value=regine;
		
		$( '#dialogCensimento' ).dialog('open')
		
	}
	
	function eliminacensimento(idStabilimento,id)
	{
		document.deletecensimento.idStabilimento.value=idStabilimento;
		document.deletecensimento.idCensimento.value=id;		
		$( '#dialogDeleteCensimento' ).dialog('open')
		
	}
</script>









