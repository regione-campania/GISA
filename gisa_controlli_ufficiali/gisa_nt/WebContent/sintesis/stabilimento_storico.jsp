<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.sintesis.base.SintesisRelazioneLineaProduttiva"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>



<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

<jsp:useBean id="listaStoricoStabilimento" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="listaStoricoRelazione" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="listaStoricoOperatore" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="listaStoricoSoggettoFisico" class="java.util.ArrayList" scope="request"/>

<jsp:useBean id="Stabilimento" class="org.aspcfs.modules.sintesis.base.SintesisStabilimento" scope="request"/>


<jsp:useBean id="TipoImpresaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipoSocietaList" class="org.aspcfs.utils.web.LookupList" scope="request" />

<jsp:useBean id="ToponimiList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ProvinceList" class="org.aspcfs.utils.web.LookupList" scope="request" />

<jsp:useBean id="StatiStabilimento" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="StatiLinea" class="org.aspcfs.utils.web.LookupList" scope="request" />


<%@page import="org.aspcfs.modules.sintesis.base.*"%>

<%@ include file="../initPage.jsp" %>


<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>

<style>
.rosso { background-color:#ffcccc; }
.bianco { color:#ffffff; }

</style>
		
 <%! public static String fixData(String timestring)
  {
	  String toRet = "";
	  if (timestring == null)
		  return toRet;
	  String anno = timestring.substring(0,4);
	  String mese = timestring.substring(5,7);
	  String giorno = timestring.substring(8,10);
	  String ora = timestring.substring(11,13);
	  String minuto = timestring.substring(14,16);
	  String secondi = timestring.substring(17,19);
	  toRet =giorno+"/"+mese+"/"+anno+" "+ora+":"+minuto+":"+secondi;
	  return toRet;
	  
  }
  
  public static boolean confrontaStringhe (String stringa1, String stringa2){
	  
	  if (stringa1==null)
		  stringa1="";
	  if (stringa2==null)
		  stringa2="";
	  
	  if (stringa1.equalsIgnoreCase(stringa2))
		  return true;
	  else
		  return false;
  }
  
  
  
  
  
  %>

<script>
function openPopupRichiesta(id){
window.open('StabilimentoSintesisAction.do?command=DettaglioCompletaRichiesta&popup=true&id='+id,'popupSelect',
'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}
</script>


    <br>
   
    <%
String nomeContainer = "sintesis";
request.setAttribute("Operatore",Stabilimento.getOperatore());
String param = "altId="+Stabilimento.getAltId();
%>

  <dhv:container name="<%=nomeContainer %>" selected="Storico" object="Operatore" param="<%=param%>">
  	
  	<center><b>Lista modifiche sullo stabilimento (effettuate da SINTESIS)</b></center>
  		


<%
if (listaStoricoStabilimento.size()>0) {%>
<table  class="details" width="100%">
<tr><th>Richiesta</th> <th>Data modifica</th> <th>Utente modifica</th> 	
<th colspan="2">Denominazione sede operativa</th><th colspan="2">Stato sede operativa</th><th colspan="2">Indirizzo</th><th colspan="2">Ragione sociale impresa</th></tr>
<th colspan="3"></th><th>PRIMA</th><th>DOPO</th><th>PRIMA</th><th>DOPO</th><th>PRIMA</th><th>DOPO</th><th>PRIMA</th><th>DOPO</th></tr>

<%
		for (int i=0;i<listaStoricoStabilimento.size(); i++){
			SintesisStoricoStabilimento stab = (SintesisStoricoStabilimento) listaStoricoStabilimento.get(i);
			
			%>

		
		<tr>
		<td> <a href="#" onClick="openPopupRichiesta('<%=stab.getIdRecord() %>')">Vedi</a></td> 
		<td> <%=toDateasStringWitTime(stab.getDataModifica()) %></td>
		<td><dhv:username id="<%= stab.getIdUtente() %>"></dhv:username></td>
	
		<td <%= (!confrontaStringhe(stab.getDenominazioneOld(),stab.getDenominazioneNew())) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%=toHtml(stab.getDenominazioneOld()) %></td>
		<td <%= (!confrontaStringhe(stab.getDenominazioneOld(), stab.getDenominazioneNew())) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%=stab.getDenominazioneNew() %></td>
		
		<td <%= (stab.getStatoOld()!=stab.getStatoNew()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%= StatiStabilimento.getSelectedValue(stab.getStatoOld())%></td>
		<td <%= (stab.getStatoOld()!=stab.getStatoNew()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%= StatiStabilimento.getSelectedValue(stab.getStatoNew())%></td>
		
		<td <%= (stab.getIndirizzoOld().getIdIndirizzo()!=stab.getIndirizzoNew().getIdIndirizzo()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<% if (stab.getIndirizzoOld().getIdIndirizzo()==-1){ %> <%} else { %>
		<%=ToponimiList.getSelectedValue(stab.getIndirizzoOld().getToponimo())%> <%=stab.getIndirizzoOld().getVia() %>  <%=toHtml(stab.getIndirizzoOld().getCivico()) %>, <%=ComuniList.getSelectedValue(stab.getIndirizzoOld().getComune())%>, <%=ProvinceList.getSelectedValue(stab.getIndirizzoOld().getIdProvincia())%>
		<%} %>
		</td>
		<td <%= (stab.getIndirizzoOld().getIdIndirizzo()!=stab.getIndirizzoNew().getIdIndirizzo()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%=ToponimiList.getSelectedValue(stab.getIndirizzoNew().getToponimo())%> <%=stab.getIndirizzoNew().getVia() %>  <%=toHtml(stab.getIndirizzoNew().getCivico()) %>, <%=ComuniList.getSelectedValue(stab.getIndirizzoNew().getComune())%>, <%=ProvinceList.getSelectedValue(stab.getIndirizzoNew().getIdProvincia())%></td>
		
		<td <%= (stab.getOperatoreOld().getIdOperatore()!=stab.getOperatoreNew().getIdOperatore()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%=toHtml(stab.getOperatoreOld().getRagioneSociale()) %> </td> 
		<td <%= (stab.getOperatoreOld().getIdOperatore()!=stab.getOperatoreNew().getIdOperatore()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%=stab.getOperatoreNew().getRagioneSociale() %> </td> 
		</tr>
		
		<%} %>
		</table>
		<%} else {%>
		Nessuna variazione sullo stabilimento.
		<% } %>
		
	
	
	
<br/><br/>

<center><b>Lista modifiche sulle linee (effettuate da SINTESIS)</b></center>
  		


<%
if (listaStoricoRelazione.size()>0) {%>
<table  class="details" width="100%">
<tr>
		<th>Richiesta</th> <th>Data modifica</th> <th>Utente modifica</th><th>Linea</th>
		<th colspan="2">Stato</th><th colspan="2">Data Inizio</th><th colspan="2">Data Fine</th></tr>
		<th colspan="4"></th>		<th>PRIMA</th><th>DOPO</th>		<th>PRIMA</th><th>DOPO</th>		<th>PRIMA</th><th>DOPO</th>
</tr>
		
		
<%
		for (int i=0;i<listaStoricoRelazione.size(); i++){
			SintesisStoricoRelazioneLineaProduttiva rel = (SintesisStoricoRelazioneLineaProduttiva) listaStoricoRelazione.get(i);
			
			%>
		<tr>
		<td><a href="#" onClick="window.open('StabilimentoSintesisAction.do?command=DettaglioCompletaRichiesta&id=<%=rel.getIdRecord() %>')">Vedi</a></td> 
		<td><%=toDateasStringWitTime(rel.getDataModifica()) %></td>
		 <td> <dhv:username id="<%= rel.getIdUtente() %>"></dhv:username></td>
		 <td><%=rel.getPathCompleto().replaceAll("->", "<br/>->") %></td>
		<td <%= (rel.getStatoOld()!=rel.getStatoNew()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%=StatiLinea.getSelectedValue(rel.getStatoOld()) %></td>
		<td <%= (rel.getStatoOld()!=rel.getStatoNew()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%=StatiLinea.getSelectedValue(rel.getStatoNew()) %></td>
		
		<td <%= (rel.getDataInizioOld()!=rel.getDataInizioNew()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%=toDateasString(rel.getDataInizioOld()) %></td>
		<td <%= (rel.getDataInizioOld()!=rel.getDataInizioNew()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%=toDateasString(rel.getDataInizioNew()) %></td>
		
		<td <%= (rel.getDataFineOld()!=rel.getDataFineNew()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%=toDateasString(rel.getDataFineOld()) %></td>
		<td <%= (rel.getDataFineOld()!=rel.getDataFineNew()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%=toDateasString(rel.getDataFineNew()) %></td>

		</tr>
		<%} %>
		</table>
		<%} else {%>
		Nessuna variazione sulle linee.
		<% } %>
		
<br/><br/>


<center><b>Lista modifiche sull'impresa  (effettuate da GISA)</b></center>
  		


<%
if (listaStoricoOperatore.size()>0) {%>
<table  class="details" width="100%">
<tr><th>Modifica</th><th colspan="2">Tipo Impresa</th><th colspan="2">Tipo societa</th><th colspan="2">Domicilio digitale</th><th colspan="2">Indirizzo</th></tr>
<tr><th></th><th>PRIMA</th><th>DOPO</th><th>PRIMA</th><th>DOPO</th><th>PRIMA</th><th>DOPO</th><th>PRIMA</th><th>DOPO</th></tr>
<%

		for (int i=0;i<listaStoricoOperatore.size(); i++){
			SintesisStoricoOperatore op = (SintesisStoricoOperatore) listaStoricoOperatore.get(i);
			
			%>
<tr>
		<td> <dhv:username id="<%= op.getIdUtente() %>"></dhv:username> <br/>(<%=toDateasString(op.getDataModifica()) %>)</td>
		
		
		<td <%= (op.getTipoImpresaOld()!=op.getTipoImpresaNew() && !(op.getTipoImpresaOld()<=0 && op.getTipoImpresaNew()<=0)) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%=(op.getTipoImpresaOld()>0) ? TipoImpresaList.getSelectedValue(op.getTipoImpresaOld()) : ""%></td>
		<td <%= (op.getTipoImpresaOld()!=op.getTipoImpresaNew() && !(op.getTipoImpresaOld()<=0 && op.getTipoImpresaNew()<=0)) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%=(op.getTipoImpresaNew()>0) ? TipoImpresaList.getSelectedValue(op.getTipoImpresaNew()) : ""%></td>
		
		<td <%= (op.getTipoSocietaOld()!=op.getTipoSocietaNew()  && !(op.getTipoSocietaOld()<=0 && op.getTipoSocietaNew()<=0)) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%=(op.getTipoSocietaOld()>0) ? TipoSocietaList.getSelectedValue(op.getTipoSocietaOld()) : ""%></td>
		<td <%= (op.getTipoSocietaOld()!=op.getTipoSocietaNew()  && !(op.getTipoSocietaOld()<=0 && op.getTipoSocietaNew()<=0)) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%=(op.getTipoSocietaNew()>0) ? TipoSocietaList.getSelectedValue(op.getTipoSocietaNew()) : ""%></td>
		
		<td <%= (!confrontaStringhe(op.getDomicilioDigitaleOld(),op.getDomicilioDigitaleNew())) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%=toHtml(op.getDomicilioDigitaleOld()) %> </td> 
		<td <%= (!confrontaStringhe(op.getDomicilioDigitaleOld(),op.getDomicilioDigitaleNew())) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%=toHtml(op.getDomicilioDigitaleNew()) %> </td> 
		
		<td <%= (op.getIndirizzoOld().getIdIndirizzo()!=op.getIndirizzoNew().getIdIndirizzo()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%=ToponimiList.getSelectedValue(op.getIndirizzoOld().getToponimo())%> <%=toHtml(op.getIndirizzoOld().getVia()) %>  <%=toHtml(op.getIndirizzoOld().getCivico()) %>, <%=ComuniList.getSelectedValue(op.getIndirizzoOld().getComune())%>, <%=ProvinceList.getSelectedValue(op.getIndirizzoOld().getIdProvincia())%></td>
		<td <%= (op.getIndirizzoOld().getIdIndirizzo()!=op.getIndirizzoNew().getIdIndirizzo()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%=ToponimiList.getSelectedValue(op.getIndirizzoNew().getToponimo())%> <%=toHtml(op.getIndirizzoNew().getVia()) %>  <%=toHtml(op.getIndirizzoNew().getCivico()) %>, <%=ComuniList.getSelectedValue(op.getIndirizzoNew().getComune())%>, <%=ProvinceList.getSelectedValue(op.getIndirizzoNew().getIdProvincia())%></td>
		
		
		</tr>
		
		<%} %>
		</table>
		<%} else {%>
		Nessuna variazione sull'impresa.
		<% } %>
		
	
	
	
<br/><br/>

<center><b>Lista modifiche sul rappresentante legale (effettuate da GISA)</b></center>
  		


<%
if (listaStoricoSoggettoFisico.size()>0) {%>
<table  class="details" width="100%">
<tr><th>Modifica</th><th colspan="2">Codice Fiscale</th><th colspan="2">Nome</th><th colspan="2">Cognome</th><th colspan="2">Sesso</th><th colspan="2">Data Nascita</th><th colspan="2">Comune Nascita</th> <th colspan="2">Domicilio digitale</th><th colspan="2">Indirizzo</th></tr>
	<tr><th></th><th>PRIMA</th><th>DOPO</th><th>PRIMA</th><th>DOPO</th><th>PRIMA</th><th>DOPO</th><th>PRIMA</th><th>DOPO</th><th>PRIMA</th><th>DOPO</th><th>PRIMA</th><th>DOPO</th><th>PRIMA</th><th>DOPO</th><th>PRIMA</th><th>DOPO</th></tr>
		
<%
		for (int i=0;i<listaStoricoSoggettoFisico.size(); i++){
			SintesisStoricoSoggettoFisico sogg = (SintesisStoricoSoggettoFisico) listaStoricoSoggettoFisico.get(i);
			
			%>
<tr>
		<td> <dhv:username id="<%= sogg.getIdUtente() %>"></dhv:username> <br/>(<%=toDateasString(sogg.getDataModifica()) %>)</td>
		
		
		<td <%= (!confrontaStringhe(sogg.getCodiceFiscaleOld(),sogg.getCodiceFiscaleNew())) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%=toHtml(sogg.getCodiceFiscaleOld()) %></td>
		<td <%= (!confrontaStringhe(sogg.getCodiceFiscaleOld(),sogg.getCodiceFiscaleNew())) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%=toHtml(sogg.getCodiceFiscaleNew()) %></td>
		
		<td <%= (!confrontaStringhe(sogg.getNomeOld(),sogg.getNomeNew())) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%=toHtml(sogg.getNomeOld()) %></td>
		<td <%= (!confrontaStringhe(sogg.getNomeOld(),sogg.getNomeNew())) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%=toHtml(sogg.getNomeNew()) %></td>
		
		<td <%= (!confrontaStringhe(sogg.getCognomeOld(),sogg.getCognomeNew())) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%=toHtml(sogg.getCognomeOld()) %></td>
		<td <%= (!confrontaStringhe(sogg.getCognomeOld(),sogg.getCognomeNew())) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%=toHtml(sogg.getCognomeNew()) %></td>
		
		<td <%= (!confrontaStringhe(sogg.getSessoOld(),sogg.getSessoNew())) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%=toHtml(sogg.getSessoOld()) %></td>
		<td <%= (!confrontaStringhe(sogg.getSessoOld(),sogg.getSessoNew())) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%=toHtml(sogg.getSessoNew()) %></td>
		
		<td <%= (!confrontaStringhe(toDateasString(sogg.getDataNascitaOld()),toDateasString(sogg.getDataNascitaNew()))) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%=toDateasString(sogg.getDataNascitaOld())%></td>
		<td <%= (!confrontaStringhe(toDateasString(sogg.getDataNascitaOld()),toDateasString(sogg.getDataNascitaNew()))) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%=toDateasString(sogg.getDataNascitaNew())%></td>
		
		<td <%= (!confrontaStringhe(sogg.getComuneNascitaOld(),sogg.getComuneNascitaNew())) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%=toHtml(sogg.getComuneNascitaOld())%></td>
		<td <%= (!confrontaStringhe(sogg.getComuneNascitaOld(),sogg.getComuneNascitaNew())) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%=toHtml(sogg.getComuneNascitaNew())%></td>		

		<td <%= (!confrontaStringhe(sogg.getEmailOld(),sogg.getEmailNew())) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%=toHtml(sogg.getEmailOld())%></td>
		<td <%= (!confrontaStringhe(sogg.getEmailOld(),sogg.getEmailNew())) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%=toHtml(sogg.getEmailNew())%></td>	
 
 		<td <%= (sogg.getIndirizzoOld().getIdIndirizzo()!=sogg.getIndirizzoNew().getIdIndirizzo()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%=ToponimiList.getSelectedValue(sogg.getIndirizzoOld().getToponimo())%> <%=toHtml(sogg.getIndirizzoOld().getVia()) %>  <%=toHtml(sogg.getIndirizzoOld().getCivico()) %>, <%=ComuniList.getSelectedValue(sogg.getIndirizzoOld().getComune())%>, <%=ProvinceList.getSelectedValue(sogg.getIndirizzoOld().getIdProvincia())%></td>
		<td <%= (sogg.getIndirizzoOld().getIdIndirizzo()!=sogg.getIndirizzoNew().getIdIndirizzo()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
		<%=ToponimiList.getSelectedValue(sogg.getIndirizzoNew().getToponimo())%> <%=toHtml(sogg.getIndirizzoNew().getVia()) %>  <%=toHtml(sogg.getIndirizzoNew().getCivico()) %>, <%=ComuniList.getSelectedValue(sogg.getIndirizzoNew().getComune())%>, <%=ProvinceList.getSelectedValue(sogg.getIndirizzoNew().getIdProvincia())%></td>
		
		</tr>
		
		<%} %>
		</table>
		<%} else {%>
		Nessuna variazione sul rappresentante legale.
		<% } %>
		
	
	
<br/><br/>
</dhv:container>
</body>
</html>