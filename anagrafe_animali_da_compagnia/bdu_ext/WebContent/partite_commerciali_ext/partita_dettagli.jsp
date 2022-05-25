<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>

<%@ page import="java.io.*,java.util.*,org.aspcfs.utils.web.*" %>

<%@page import="org.aspcfs.modules.opu_ext.base.Stabilimento"%>
<%@page import="org.aspcfs.modules.opu_ext.base.LineaProduttiva"%>
<%@page import="org.aspcfs.modules.anagrafe_animali_ext.base.*"%>


<%@page import="org.aspcfs.modules.partitecommerciali_ext.base.PartitaCommerciale"%>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="specieList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="aslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="nazioniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="StatiList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="partita" class="org.aspcfs.modules.partitecommerciali_ext.base.PartitaCommerciale" scope="request"/>
<jsp:useBean id="list" class="java.util.ArrayList" scope="request"/>

	
	<!-- inclusioni per jmesa -->
		<link rel="stylesheet" type="text/css" href="css/jmesa/jmesa.css" />
		<script type="text/javascript" src="javascript/jmesa/jquery-1.3.min.js"></script>
		<script type="text/javascript" src="javascript/jmesa/jquery.bgiframe.pack.js"></script>
		<script type="text/javascript" src="javascript/jmesa/jmesa.js"></script>
		<script type="text/javascript" src="javascript/jmesa/jmesa.min.js"></script>
		<script type="text/javascript" src="javascript/jmesa/jquery.jmesa.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>

<SCRIPT LANGUAGE="JavaScript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();

</script>
<style type="text/css">
#Copia ed adattamento del css contenuto in css > vam > homePage
#content
{
margin:0 auto;
width:899px;
}
#content p
{
font:normal 12px/18px Arial, Helvetica, sans-serif;
padding:10px;
color:#333333;
}
#content_right
{
margin:0 auto;
width:860px;
padding:5px;
}
#content h3
{
font:bold 12px/20px Arial, Helvetica, sans-serif;
color:#607B35;
}
#content_row1
{
margin:0 auto;
width:670px;
height:175px;
background:url('images/pets_clinic_08.gif') no-repeat 0 0;
padding-left:250px
}
#content_row2
{
margin:0 auto;
width:625px;
}



</style>


<%@ include file="../initPage.jsp" %>
<% String param1 = "idPartita=" + partita.getIdPartitaCommerciale() +"&idSpecie=" + partita.getIdTipoPartita();
   %>
   
   
   
   
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0"> 
<tr>
<td width="100%">
  <a href="PartiteCommerciali.do"><dhv:label name="circuito.commerciale">Prenotifiche</dhv:label></a> >
  <dhv:label name="circuito.commerciale.dettagli">Dettagli prenotifica</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<form method = "post" name = "dettaglio" action = "PartiteCommerciali.do?command=ChiudiPartita" >
<br><br>
   <%if (list.size() > 0 && partita.getDataArrivoEffettiva() != null) {
	   if(partita.getIdStatoImportatore()==PartitaCommerciale.ID_STATO_APERTO)
   
	   {
	   %>
   <input type = "button" onclick="javascript:document.dettaglio.action='PartiteCommerciali.do?command=ChiudiPratica&idPartita=<%=partita.getIdPartitaCommerciale() %>';if (form.dataArrivoEffettiva.value != ''){submit()}else{alert('Per proseguire inserisci un valore per la data di arrivo effettiva')};" value = "CHIUDI PRATICA"> 
   <%
	   }
   if(partita.getIdStatoImportatore()==PartitaCommerciale.ID_STATO_CHIUSO)
	   {
	   %>
	   <input type = "button" onclick="this.form.action='PartiteCommerciali.do?command=InviaPratica&idPartita=<%=partita.getIdPartitaCommerciale() %>';submit();" value = "INVIA AD ASL DI APPARTENENZA"> 
   
	   <%
	   }}
	   %>
   
   <br><br>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" >
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="">Dettagli partita (Prenotifica)</dhv:label></strong>
	  </th>
  </tr>
<tr>
    <td class="formLabel" nowrap>
      <dhv:label name="">Asl di riferimento</dhv:label>
    </td>
	<td>
	<%=aslList.getSelectedValue(partita.getIdAslRiferimento()) %>
	</td>
</tr>
<input type="hidden" id="idTipoPartita" name="idTipoPartita" value="<%=partita.getIdTipoPartita() %>"/>

    <tr>
      <td class="formLabel" nowrap>
      <dhv:label name="">Numero certificato</dhv:label>
    </td>
    <td>
   <%=partita.getNrCertificato() %>
    </td>

  </tr>
<%
String label = "Numero animali";
if (partita.getIdTipoPartita() == Gatto.idSpecie) {
	label = "Numero Gatti";
}else if (partita.getIdTipoPartita() == Cane.idSpecie){
	label = "Numero Cani";
}

%>
    <tr>
      <td class="formLabel" nowrap>
      <dhv:label name=""><%=label%></dhv:label>
    </td>
    <td>
   <%=partita.getNrAnimaliPartita() %>
    </td>

  </tr>
  
      <tr>
      <td class="formLabel" nowrap>
      <dhv:label name="">Nazione di provenienza</dhv:label>
    </td>
    
    <td>
	<%=nazioniList.getSelectedValue(partita.getIdNazioneProvenienza()) %>
    </td>

  </tr>
  
  
  <tr class="containerBody" >
    <td class="formLabel">
      <dhv:label name="">Data di arrivo previsto</dhv:label>
    </td>

    
    <td>      
		<%=toDateasString(partita.getDataArrivoPrevista()) %>
    </td> 
  </tr>
  <tr class="containerBody" >
    <td class="formLabel">
      <dhv:label name="">Stato Pratica</dhv:label>
    </td>

    
    <td>      
		<%=StatiList.getSelectedValue(partita.getIdStatoImportatore())%>
    </td> 
  </tr>
 
    <tr class="containerBody" >
    <td class="formLabel">
      <dhv:label name="">Data di arrivo effettiva</dhv:label>
    </td>
    
    <td>
 <%if(partita.getIdStatoImportatore()==PartitaCommerciale.ID_STATO_APERTO){ %>		
  		<input type = "hidden" value="<%=partita.getIdPartitaCommerciale()%>" name="idpartita" id="idpartita"/>
      	<input  readonly type="text" name="dataArrivoEffettiva" size="10" value="<%=toDateString(partita.getDataArrivoEffettiva()) %>"  nomecampo="dataArrivoEffettiva" labelcampo="Data arrivo effettiva" />&nbsp;
        <a href="#" onClick="cal19.select(document.forms[0].dataArrivoEffettiva,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>

      
<input type="button" value="Salva data" name="salva" id="salva" onclick="if (this.form.dataArrivoEffettiva.value != null && !(this.form.dataArrivoEffettiva.value == '')) {this.form.action='PartiteCommerciali.do?command=AggiornaDataArrivoEffettivo';submit();} else {alert('Inserire un valore per la data di arrivo');}"/>
<%} else{%> 
<input  readonly type="text" name="dataArrivoEffettiva" size="10" value="<%=toDateString(partita.getDataArrivoEffettiva()) %>"/>   
<%} %>
    </td>
  </tr>

</table>
<br/>
</form>
<div id="content">
<center>
  

<h4>Animali Partita Commerciale</h4>

<%if(partita.getIdStatoImportatore()==PartitaCommerciale.ID_STATO_APERTO){ %>	
<a href="PartiteCommerciali.do?command=Add&idPartita=<%=partita.getIdPartitaCommerciale() %>&idLinea=<%=partita.getIdImportatore() %>"><img src="images/add.png" height="24px" width="24px" title="Aggiungi Animale Alla Partita" alt="Aggiungi Animale" /></a>
		<%} %>

<form action="PartiteCommerciali.do?command=DettagliPartita&amp;idPartita=<%=partita.getIdPartitaCommerciale() %>" method="post">
				
<jmesa:tableModel items="<%=list %>" id="list" var="u" >
			<jmesa:htmlTable styleClass="tabella">
				<jmesa:htmlRow>
					<jmesa:htmlColumn property="descrSpecie" title="specie" width="208px" />
					<jmesa:htmlColumn property="sesso" title="sesso" width="68px"/>
					<jmesa:htmlColumn property="nome" title="nome" width="208px"/>
					<jmesa:htmlColumn property="microchip" title="microchip"  width="108px"/>
					<jmesa:htmlColumn property="descrRazza" title="Razza" width="208px" />
					<jmesa:htmlColumn property="descrMantello" title="Mantello" width="108px" />
					<jmesa:htmlColumn property="descrTaglia" title="Taglia"  width="200px"/>
					<jmesa:htmlColumn property="descrizioneStatoImport" title="Stato"  width="50px"/>
					<jmesa:htmlColumn property="noteImport" title="Note"  width="50px"/>
					
					
					
					
				</jmesa:htmlRow>
			</jmesa:htmlTable>
		</jmesa:tableModel>
	
		<script type="text/javascript">
		function onInvokeAction(id)
		{
			setExportToLimit(id, '');
			createHiddenInputFieldsForLimitAndSubmit(id);
		}
		function onInvokeExportAction(id)
		{
			var parameterString = createParameterStringForLimit(id);
			location.href = 'PartiteCommerciali.do?command=DettagliPartita&amp;idPartita=<%=partita.getIdPartitaCommerciale() %>' + parameterString;
		}
	</script>
</form>
</center>
  
</div>
  
  
  

<br/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" >
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="">Informazioni di inserimento/modifica</dhv:label></strong>
	  </th>
  </tr>
<%--   <tr class="containerBody" >
    <td class="formLabel">
      <dhv:label name="">Inserito</dhv:label>
    </td>
    <td>
      <dhv:username id="<%= partita.getIdUtenteInserimento() %>" />
      <zeroio:tz timestamp="<%= partita.getDataInserimento()%>" />
    </td>
  </tr> --%>
    <tr class="containerBody" >
    <td class="formLabel">
      <dhv:label name="">Modificato</dhv:label>
    </td>
    <td>
      <dhv:username id="<%= partita.getIdUtenteModifica() %>" />
      <zeroio:tz timestamp="<%= partita.getDataModifica()%>" />
    </td>
  </tr>
</table>
<br>




