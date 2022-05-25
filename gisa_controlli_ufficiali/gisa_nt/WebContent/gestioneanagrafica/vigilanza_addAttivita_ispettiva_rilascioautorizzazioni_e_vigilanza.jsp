<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.vigilanza.base.*" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="OrgDetails" class="org.aspcf.modules.controlliufficiali.base.Organization" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>

<form name="addticket" id="gestioneanagrafica" action="GestioneAnagraficaVigilanza.do?command=Insert&auto-populate=true" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="GestioneAnagraficaAction.do"><dhv:label name="">Gestione Anagrafica</dhv:label></a> > 
<%--   <a href="GestioneAnagraficaAction.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> > --%>
  <a href="GestioneAnagraficaAction.do?command=Details&altId=<%=OrgDetails.getAltId()%>"><dhv:label name="">scheda</dhv:label></a> >
  <a href="GestioneAnagraficaAction.do?command=ViewVigilanza&altId=<%=OrgDetails.getAltId()%>"><dhv:label name="vigilanza">Tickets</dhv:label></a> >
  <dhv:label name="campioni.aggiungi">Aggiungi Controllo Ufficiale</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<input type="button" value="<dhv:label name="button.inserta">Inserisci </dhv:label>" name="Save" onClick="return controlloCuSorveglianzaAlt()">
<input type="button" value="Annulla" onClick="loadModalWindow(); window.location.href='GestioneAnagraficaAction.do?command=ViewVigilanza&altId=<%=OrgDetails.getAltId()%>'">
<br>
<dhv:formMessage />
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<% if (request.getAttribute("closedError") != null) { %>
  <%= showAttribute(request, "closedError") %>
<%}%>
<%-- include basic troubleticket add form --%>

<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.text.DateFormat, org.aspcfs.modules.actionplans.base.*" %>

<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Ispezione" class="java.util.HashMap" scope="request" />
<jsp:useBean id="IspezioneMacrocategorie" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="EsitoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoAudit" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Bpi" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Haccp" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoIspezione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="PianoMonitoraggio1" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="PianoMonitoraggio2" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="PianoMonitoraggio3" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolvedByDeptList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SanzioniAmministrative" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SeverityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ticketStateList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Recipient" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="DestinatarioCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="causeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolutionList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EscalationList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="resolvedUserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="actionPlans" class="org.aspcfs.modules.actionplans.base.ActionPlanList" scope="request"/>
<jsp:useBean id="insertActionPlan" class="java.lang.String" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="defectSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<jsp:useBean id="fileItem" class="com.zeroio.iteam.base.FileItem" scope="request" />
<jsp:useBean id="EsitoControllo" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="DistribuzionePartita" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="DestinazioneDistribuzione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ArticoliAzioni" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="AzioniAdottate" class="org.aspcfs.utils.web.LookupList" scope="request" />
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popGestioneAnagrafica.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popServiceContracts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAssets.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProducts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/controlli_ufficiali_anagrafica.js"></script>

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></SCRIPT>
<script language="JavaScript" type="text/javascript" src="javascript/confrontaDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script type="text/javascript" src="dwr/interface/ControlliUfficiali.js?n=2"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/controlliUfficiali.js"></script>
<jsp:useBean id="AuditTipo" class="org.aspcfs.utils.web.LookupList" scope="request" />


<script language="JavaScript">
var res;
	function load_linee_attivita_per_alt_id_stabilimento_callback(returnValue) {
		  campo_combo_da_costruire = returnValue [2];
		  var select = document.getElementById(campo_combo_da_costruire); //Recupero la SELECT
	      
	      //Azzero il contenuto della seconda select
	      for (var i = select.length - 1; i >= 0; i--)
	    	  select.remove(i);

	      var NewOpt = document.createElement('option');
	      NewOpt.value = -1; // Imposto il valore
	      NewOpt.text = "-- SELEZIONARE UNA LINEA DI ATTIVITA --" // Imposto il testo

	     // if (returnValue [3]==indici[j])
	    	//  NewOpt.selected = true;
			
	      //Aggiungo l'elemento option
	      try
	      {
	    	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
	      } catch(e){
	    	  select.add(NewOpt); // Funziona solo con IE
	      }
	
	      indici = returnValue [0];
	      valori = returnValue [1];
	      //Popolo la seconda Select
	      for(j =0 ; j<indici.length; j++){
		      //Creo il nuovo elemento OPTION da aggiungere nella seconda SELECT
		      var NewOpt = document.createElement('option');
		      NewOpt.value = indici[j]; // Imposto il valore
		      NewOpt.text = valori[j]; // Imposto il testo

		     // if (returnValue [3]==indici[j])
		    	//  NewOpt.selected = true;
				
		      //Aggiungo l'elemento option
		      try
		      {
		    	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
		      } catch(e){
		    	  select.add(NewOpt); // Funziona solo con IE
		      }
		      
	      }

	   
	}
	
	  function costruisci_rel_ateco_attivita( alt_id, campo_combo_da_costruire ) {
		  //
		 // alert(campo_combo_da_costruire);
		  PopolaCombo.load_linee_attivita_per_alt_id_stabilimento(alt_id , campo_combo_da_costruire, null, load_linee_attivita_per_alt_id_stabilimento_callback)
	  }
	
</script>


<body onload = "provaFunzione('addticket'); onloadAllerta('addticket'); resetElementiNucleoIspettivo('<%=TicketDetails.getNucleoasList().size() %>'); costruisci_rel_ateco_attivita('<%= OrgDetails.getAltId() %>', 'id_linea_sottoposta_a_controllo'); "">

 <%
	TipoIspezione.setJsEvent("onChange=javascript:mostraMenuTipoIspezione('addticket');");
	TitoloNucleoDue.setJsEvent("onChange=mostraCampo2('addticket')");
	
    PianoMonitoraggio2.setJsEvent("onChange=javascript:piani('addticket')");
    PianoMonitoraggio3.setJsEvent("onChange=javascript:piani('addticket')");
	AuditTipo.setJsEvent("onChange=javascript:mostraMenu4('addticket')");
    TipoAudit.setJsEvent("onChange=javascript:mostraMenu2('addticket')");
    TipoCampione.setJsEvent("onChange=javascript:reloadAddCU('GestioneAnagraficaVigilanza.do?command=Add&orgId="+OrgDetails.getOrgId()+"&tipoCampione='+this.value)"); 
	TitoloNucleo.setJsEvent("onChange=mostraCampooo('addticket')"); 
%>

<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Aggiungi Controllo Ufficiale</dhv:label></strong>
    </th>
	</tr>
  
  <%@ include file="../controlliufficiali/alt_controlli_ufficiali_add.jsp" %>
 
  
 </table>

<br><br>

<%@ include file="../controlliufficiali/controlli_ufficiali_allarmerapido_add.jsp" %>
	

<%@ include file="../controlliufficiali/controlli_ufficiali_laboratori_haccp.jsp" %>

<br>
<br>
<br>

<%@ include file="../controlliufficiali/controlli_ufficiali_laboratori_haccp_non_in_regione.jsp" %>
	
 
 
<input type="hidden" name="isAllegato" value="<%=TicketDetails.isListaDistribuzioneAllegata() %>"> 
<input type="hidden" name="close" value="">
<input type="hidden" name="refresh" value="-1">
<input type="hidden" name="modified" value="<%=  TicketDetails.getModified() %>" />
<input type="hidden" name="currentDate" value="<%=  request.getAttribute("currentDate") %>" />
<input type="hidden" name="statusId" value="<%=  TicketDetails.getStatusId() %>" />
<input type="hidden" name="trashedDate" value="<%=  TicketDetails.getTrashedDate() %>" />
<%= addHiddenParams(request, "popup|popupType|actionId") %>


<br>
<input type="button" value="<dhv:label name="button.inserta">Inserisci </dhv:label>" name="Save" onClick="return controlloCuSorveglianza()">
<input type="button" value="Annulla" onClick="loadModalWindow(); window.location.href='GestioneAnagraficaAction.do?command=ViewVigilanza&altId=<%=OrgDetails.getAltId()%>'">
</form>
</body>