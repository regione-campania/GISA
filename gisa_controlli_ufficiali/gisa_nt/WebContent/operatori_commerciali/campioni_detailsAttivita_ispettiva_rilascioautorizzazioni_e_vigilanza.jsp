<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.campioni.base.*,com.zeroio.iteam.base.*, org.aspcfs.modules.quotes.base.*,org.aspcfs.modules.base.EmailAddress" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.campioni.base.Ticket" scope="request"/>
<jsp:useBean id="ticketCategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="product" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="customerProduct" class="org.aspcfs.modules.products.base.CustomerProduct" scope="request"/>
<jsp:useBean id="quoteList" class="org.aspcfs.modules.quotes.base.QuoteList" scope="request"/>
<jsp:useBean id="TitoloNucleo" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoDue" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoTre" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoQuattro" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoCinque" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoSei" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoSette" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoOtto" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoNove" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoDieci" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AltriAlimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ConseguenzePositivita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ResponsabilitaPositivita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiNonTrasformati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiNonTrasformatiValori" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiTrasformati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiVegetali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.operatori_commerciali.base.Organization" scope="request"/>
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SanzioniAmministrative" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EsitoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="DestinatarioCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SanzioniPenali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Sequestri" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="causeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ticketStateList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolutionList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="defectCheck" class="java.lang.String" scope="request"/>
<jsp:useBean id="defect" class="org.aspcfs.modules.troubletickets.base.TicketDefect" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="id" class="java.lang.String" scope="request"/>
<jsp:useBean id="ck_mot" class="java.lang.String" scope="request"/>
<jsp:useBean id="ck_nv" class="java.lang.String" scope="request"/>
<jsp:useBean id="ck_dp" class="java.lang.String" scope="request"/>
<jsp:useBean id="ck_mat" class="java.lang.String" scope="request"/>
<jsp:useBean id="ck_an" class="java.lang.String" scope="request"/>
<jsp:useBean id="input" class="java.lang.String" scope="request"/>
<jsp:useBean id="elencoMotivazioni" class="java.util.ArrayList" scope="session"/>

<script language="Javascript" TYPE="text/javascript" SRC="javascript/checkAnaliti.js"></script>

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script type="text/javascript">

function apriVerbale(idCampione,tipoAnalisi)
{
	tipoAlimenti = document.getElementById("tipoAlimenti").value;
	document.location.href="OperatoriCommercialiCampioni.do?command=StampaScheda&tipoAnalisi="+tipoAnalisi+"&idCampione="+idCampione+"&tipoAlimenti="+tipoAlimenti;
		
}
</script>

<%@ include file="../initPage.jsp" %>
<form id="details" name="details" action="OperatoriCommercialiCampioni.do?command=ModifyTicket&auto-populate=true" method="post">
<input type = "hidden" name ="idC" value = "<%=TicketDetails.getIdControlloUfficiale() %>">

<!-- <input type="hidden" name = "idControlloUfficiale" value ="<%= request.getAttribute("idC")%>">-->
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<td>
  <a href="OperatoriCommerciali.do">Operatori Commerciali</a> > 
  <a href="OperatoriCommerciali.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
  <a href="OperatoriCommerciali.do?command=Details&orgId=<%=TicketDetails.getOrgId()%>">Scheda Operatore Commerciale</a> >
  <a href="OperatoriCommerciali.do?command=ViewVigilanza&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="">Controlli Ufficiali</dhv:label></a> >
    <a href="OperatoriCommercialiVigilanza.do?command=TicketDetails&id=<%= request.getAttribute("idC")%>&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="">Controllo Ufficiale</dhv:label></a> >
  <%--a href="Accounts.do?command=ViewCampioni&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="campioni">Tickets</dhv:label></a> --%>
<dhv:label name="campione.dettagli">Scheda Campione</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>

<%
	String param1 = "idCampione="+TicketDetails.getId()+"&orgId=" + OrgDetails.getOrgId();
%>


<%
	String nomeContainer = "" ;
int pianoBenessereAnimale = Ticket.PIANO_BENESSERE_ANIMALE ;
if(TicketDetails.getCodiceInternPiano()!=null && TicketDetails.getCodiceInternPiano().equals(String.valueOf(pianoBenessereAnimale)))
{
	param1 = "idCampione="+TicketDetails.getId()+"&orgId=" + OrgDetails.getOrgId();
	nomeContainer = "canipadronalicampioni" ;
}
else
{
	param1 = "orgId=" + OrgDetails.getOrgId(); 
	nomeContainer = "operatori_commerciali" ;
}
%>
<dhv:container name="<%=nomeContainer %>" selected="vigilanza" object="OrgDetails" param='<%= param1%>' >
<%@ include file="ticket_header_include_campioni.jsp"%>
	<%String numero_include="1";
 	  String perm_op_delete = TicketDetails.getPermission_ticket()+"-campioni-delete";
 	  String perm_op_edit = TicketDetails.getPermission_ticket()+"-campioni-edit";
 	 %>
	<%@ include file="/campioni/header_campioni.jsp" %>
	
	
	<table cellpadding="4" cellspacing="0" width="100%" class="details">
		<tr>
			<th colspan="2"><strong><dhv:label
				name="sanzionia.information">Scheda Campione</dhv:label></strong></th>
		</tr>
	
		  <%@ include file="/campioni/campioni_view.jsp" %>
		  
		<tr class="containerBody">
      		<td name="" id="" nowrap class="formLabel">
        	<dhv:label name="">Cane</dhv:label>
      		</td>
    		<td colspan="">
    		<%="Microchip: "+TicketDetails.getMicrochip() %>
     		<input type = "hidden" name = "microchipMatriciCanili" value = "<%=TicketDetails.getMicrochip() %>">
    		</td>
		</tr>  
				
   </table>
     
   
   </br>
 
	
	  <%@ include file="/campioni/campioni_esito_view.jsp" %>
 
    </table>
&nbsp;
<br />
	<% numero_include="2";%>
	<%@ include file="/campioni/header_campioni.jsp" %>
</dhv:container>
</form>
<%
String msg = (String)request.getAttribute("Messaggio");

if(request.getAttribute("Messaggio")!=null)
{
	%>
	<script>
	
	alert('<%=((String)request.getAttribute("Messaggio")).replaceAll("'","")%>');
	</script>
	<%
}

%>
<%
String msg2 = (String)request.getAttribute("Messaggio2");
if(request.getAttribute("Messaggio2")!=null)
{
	%>
	<script>


	var answer = confirm("Tutte le Attivita Collegate al controllo sono state chiuse . \n Desideri Chiudere il Controllo Ufficiale ?\n Attenzione! La pratica verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore")

	if (answer){
		
		doSubmit(<%=TicketDetails.getId() %>);
	}


	function doSubmit(idTicket){

		document.details.action="OperatoriCommercialiCampioni.do?command=Chiudi&id="+idTicket+"&chiudiCu=true'"
		document.details.submit();

		}
	
	</script>
	<%
}
%>
