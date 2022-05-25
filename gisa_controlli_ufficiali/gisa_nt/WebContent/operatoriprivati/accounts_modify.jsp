<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
  <jsp:useBean id="AnagraficaDetails" class="org.aspcfs.modules.gestioneanagrafica.base.Anagrafica" scope="request"/>
  <jsp:useBean id="StabilimentoDetails" class="org.aspcfs.modules.gestioneanagrafica.base.Stabilimento" scope="request"/>
  <jsp:useBean id="StatiList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
  
  <%@ page import="org.aspcfs.modules.gestioneanagrafica.base.*" %>
  
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
  
  <%@ include file="../initPage.jsp" %>
  
  <!-- RELATIVO AL NUOVO CALENDARIO CON MESE E ANNO FACILMENTE MODIFICABILI -->
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>


<script>
function checkForm(form){
		 
	var message = '';
	
	  <% for (int i = 0 ; i<StabilimentoDetails.getLinee().size(); i++){ 
	  	Istanza ist = (Istanza) StabilimentoDetails.getLinee().get(i);
	  	int id = ist.getId();%>
	  	
	  	var stato = form.stato<%=id%>.value;
	  	var datafine = form.dataFine<%=id%>.value;
	  	
	  	if (stato==<%=Istanza.STATO_CESSATO%> && datafine == '')
	  		message+=" Indicare una DATA FINE.\n";
	  	<% } %>

	  	if (message!=''){
	  		alert(message);
	  		return false;
	  	}
	  	
	if (confirm('Confermare?')){
		loadModalWindow();
		form.submit();	
	}
}


</script>


<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr><td>
Operatori privati > 
<a href="Operatoriprivati.do?command=Details&altId=<%=AnagraficaDetails.getAltId()%>">Operatori privati</a> > 
Modifica stato linea
</td>
</tr>
</table>
<br>
<br>


<%-- End Trails --%>


<%
String nomeContainer = "operatoriprivati";
String param = "altId="+AnagraficaDetails.getAltId();

%>


  <dhv:container name="<%=nomeContainer %>" selected="Scheda" object="AnagraficaDetails" param="<%=param%>">


<table class="details" width="100%" cellpadding="10">
<col width="10%">
  <tr>  <th colspan="2"> Modifica stato linea </th></tr>
  <tr><th>Nome</th><td> <%=StabilimentoDetails.getImpresa().getRagione_sociale() %></td>
  <tr><th>Partita IVA</th> <td> <%=StabilimentoDetails.getImpresa().getPiva() %></td></tr>
  <tr><th>Numero registrazione</th><td> <%=StabilimentoDetails.getNumero_registrazione()%></td></tr>
  </table><br/><br/>
  

<form id = "addAccount" name="addAccount" action="Operatoriprivati.do?command=Update&auto-populate=true" method="post"">
  
    <input type="hidden" id="stabId" name="stabId" value="<%=StabilimentoDetails.getId()%>"/>
  
  <% 
  boolean modificabile = false;
  for (int i = 0 ; i<StabilimentoDetails.getLinee().size(); i++){ 
  	Istanza ist = (Istanza) StabilimentoDetails.getLinee().get(i);
  	int id = ist.getId();
  %>
  
  <table class="details">
  <tr>  <th colspan="2"> <%= ist.getPathCompleto()%> </th></tr>
  <% if (ist.getCodiceUnivoco() !=null) { %><tr><th>Codice Univoco</th><td><%=ist.getCodiceUnivoco() %></td></tr><%} %>
  <tr><th>Stato</th> <td> <%if (ist.getIdStato()==Istanza.STATO_CESSATO){ StatiList.removeElementByCode(Istanza.STATO_ATTIVO); StatiList.removeElementByCode(Istanza.STATO_SOSPESO); } %> <%=StatiList.getHtmlSelect("stato"+id, ist.getIdStato()) %></td></tr>
  <tr><th>Data inizio</th><td> <input readonly type="text" id="dataInizio<%=id %>" name="dataInizio<%=id %>" size="10" value="<%=toDateasString(ist.getDataInizio()) %>"/><%--<a href="#" onClick="cal19.select(document.forms[0].dataInizio<%=id %>,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>--%></td></tr>
  <tr><th>Data fine</th><td> <input readonly type="text" id="dataFine<%=id %>" name="dataFine<%=id %>" size="10" value="<%=toDateasString(ist.getDataFine()) %>" /><a href="#" onClick="cal19.select(document.forms[0].dataFine<%=id %>,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a></td></tr>
  </table><br/>
  <%
  if (ist.getIdStato()!=Istanza.STATO_CESSATO)
	  modificabile = true;
  } %>
  
  <%if (modificabile){ %>
  <input type="button" value="SALVA" onClick="checkForm(this.form)"/>
  <% } else { %>
  <font color="red">Tutte le linee risultano cessate. Modifica non possibile.</font>
  <%} %>
  </form>
  
     </dhv:container>
  
  