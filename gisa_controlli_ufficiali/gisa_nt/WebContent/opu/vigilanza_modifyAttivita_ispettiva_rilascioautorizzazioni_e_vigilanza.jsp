<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ include file="../initPage.jsp" %>
<%@page import="org.aspcfs.utils.web.LookupList"%>
<jsp:useBean id="fileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<jsp:useBean id="EsitoControllo" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="IspezioneMacrocategorie" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="DistribuzionePartita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="DestinazioneDistribuzione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ArticoliAzioni" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AzioniAdottate" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Provvedimenti2" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Provvedimenti3" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>
<jsp:useBean id="EsitoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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
<jsp:useBean id="TipoAudit" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AuditTipo" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Bpi" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Haccp" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Ispezione" class="java.util.HashMap"	scope="request" />	
<jsp:useBean id="TipoIspezione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="PianoMonitoraggio1" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="PianoMonitoraggio2" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="PianoMonitoraggio3" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolvedByDeptList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SeverityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="DestinatarioCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="causeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolutionList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ticketStateList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EscalationList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="resolvedUserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="SubList1" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList2" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList3" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="actionPlans" class="org.aspcfs.modules.actionplans.base.ActionPlanList" scope="request"/>
<jsp:useBean id="insertActionPlan" class="java.lang.String" scope="request"/>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="defectSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="defectCheck" class="java.lang.String" scope="request"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<jsp:useBean id="TipoMobili" class="org.aspcfs.utils.web.LookupList" scope="request" />

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popServiceContracts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAssets.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProducts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/controlliUfficiali.js?n=1"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/controlli_ufficiali_imprese.js"></script>
<script type="text/javascript" src="dwr/interface/ControlliUfficiali.js"> </script>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<script src="javascript/noscia/widget.js"></script>


<script language="JavaScript">

	function load_linee_attivita_per_org_id_callback(returnValue) {
		
		  campo_combo_da_costruire = returnValue [2];
		  selected_value = returnValue [3];
		  //alert(selected_value);
		  var select = document.getElementById(campo_combo_da_costruire); //Recupero la SELECT
	      //Azzero il contenuto della seconda select
	      if (select!=null){
	      for (var i = select.length - 1; i >= 0; i--)
	    	  select.remove(i);
	     
	      indici = returnValue [0];
	      valori = returnValue [1];
	      //Popolo la seconda Select
	      for(j =0 ; j<indici.length; j++){
		      //Creo il nuovo elemento OPTION da aggiungere nella seconda SELECT
		      var NewOpt = document.createElement('option');
		      NewOpt.value = indici[j]; // Imposto il valore
		      NewOpt.text = valori[j]; // Imposto il testo

		      if (NewOpt.value==selected_value)
		    	  NewOpt.selected = true 
		
		      //Aggiungo l'elemento option
		      try
		      {
		    	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
		      } catch(e){
		    	  select.add(NewOpt); // Funziona solo con IE
		      }
  
		      
	      }
	      }
		  if (select!=null)
	  		 select.onchange();
	   
	   <% if (TicketDetails.getTipoCampione()==3) {  //AUDIT IMPEDISCO LA MODIFICA%>
	   select.style.display="none";
	   var newText = document.createElement( 'label' ); // create new textarea
	   var linee = "";
	   var element; 
	   for(k = 0; k < select.length; k++) {
	       element = select[k];
	       if (element.selected) {
	           linee = linee+select.options[k].text+"<br/>";
	       }
	     }
	   newText.innerHTML="<font color=\"red\"> Linee sottoposte a controllo non modificabili</font>";
	   select.parentNode.insertBefore( newText, select.nextSibling );
	  <% } %>
	  
	}
	  function costruisci_rel_ateco_attivita( org_id, campo_combo_da_costruire, default_value ) {
		  //alert(org_id);
		  //alert(campo_combo_da_costruire);
		  //alert(default_value);
		
		  PopolaCombo.load_linee_attivita_per_id_stabilimento(org_id , campo_combo_da_costruire, default_value, {callback:load_linee_attivita_per_org_id_callback,async:false}  )
		
	  }

</script>



<%
TipoIspezione.setJsEvent("onChange=javascript:mostraMenuTipoIspezione('details');");
TipoCampione.setJsEvent("onChange=javascript:gestioneVisibilitaCodiceAteco('details');provaFunzione('details');");
TipoAudit.setJsEvent("onChange=javascript:mostraMenu2('details')");
AuditTipo.setJsEvent("onChange=javascript:mostraMenu4('details')");
PianoMonitoraggio1.setJsEvent("onChange=javascript:piani('details')");
PianoMonitoraggio2.setJsEvent("onChange=javascript:piani('details')");
PianoMonitoraggio3.setJsEvent("onChange=javascript:piani('details')");



%>

<body onLoad="abilitaCodiceAllerta('details');initprovaFunzione('details');abilitaSistemaAllarmeRabido('details');gestioneVisibilitaCodiceAteco('details');resetElementiNucleoIspettivo('<%=TicketDetails.getNucleoasList().size() %>');costruisci_rel_ateco_attivita('<%= OrgDetails.getIdStabilimento() %>', 'id_linea_sottoposta_a_controllo', '<%= TicketDetails.getId_imprese_linee_attivita() %>' );">
<%
//commento al 214
ArrayList<org.aspcfs.modules.canipadronali.base.Cane> lista_cani = null;
lista_cani = ((ArrayList<org.aspcfs.modules.canipadronali.base.Cane>)request.getAttribute("lista_cani"));

String param = "stabId="+OrgDetails.getIdStabilimento()+"&opId=" + OrgDetails.getIdOperatore()+"&id="+TicketDetails.getIdMacchinetta();

String container = OrgDetails.getContainer();
if (User.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA && (container.startsWith("aziendeagricoleopu")|| container.startsWith("opu")))
	container="aziendeagricoleopu";
TicketDetails.setPermission();
request.setAttribute("Operatore",OrgDetails.getOperatore());


%>


<form name="details" action="<%=OrgDetails.getAction() %>Vigilanza.do?command=UpdateTicket&auto-populate=true<%= addLinkParams(request, "popup|popupType|actionId") %>"  method="post">
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
  <dhv:label name=""><a href="<%=OrgDetails.getAction()+".do?command=SearchForm" %>" >Anagrafica Stabilimenti </a>-><a  href="<%=OrgDetails.getAction()+".do?command=Details&stabId="+OrgDetails.getIdStabilimento()%>">Scheda Impresa</a> -><a href="<%=OrgDetails.getAction()+".do?command=ViewVigilanza&stabId="+OrgDetails.getIdStabilimento()%>"> Controlli Ufficiali </a>-> <a href="<%=OrgDetails.getAction()+"Vigilanza.do?command=TicketDetails&id="+TicketDetails.getId()+"&idStabilimentoopu="+OrgDetails.getIdStabilimento()%>">Scheda controllo</a>-> Modifica Controllo</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<dhv:container name="<%=container %>" selected="vigilanza" object="Operatore" param='<%= param%>' >
     <dhv:evaluate if="<%= !TicketDetails.isTrashed() %>" >
      <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>" >
          <dhv:permission name="accounts-accounts-vigilanza-edit">
            <input type="submit" value="<dhv:label name="button.reopen">Reopen</dhv:label>" onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>Vigilanza.do?command=ReopenTicket&id=<%=TicketDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'">
           </dhv:permission>
           <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>.do?command=ViewVigilanza&idStabilimentoopu=<%=OrgDetails.getIdStabilimento()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
           <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>Vigilanza.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
      </dhv:evaluate>
      <dhv:evaluate if="<%= TicketDetails.getClosed() == null %>" >
          <dhv:permission name="accounts-accounts-vigilanza-edit">
            <input type="button" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="return controlloCuSorveglianza()">
          </dhv:permission>
           <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>.do?command=ViewVigilanza&orgId=<%=OrgDetails.getIdStabilimento()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
           <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>Vigilanza.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
          <%= showAttribute(request, "closedError") %>
       </dhv:evaluate>
      </dhv:evaluate>
    <br />
    <dhv:formMessage />
    
    <table cellpadding="4" cellspacing="0" width="100%" class="details">
    	
<%@ include file="../controlliufficiali/opu_controlli_ufficiali_modify.jsp" %>

<%@ include file="../controlliufficiali/controlli_ufficiali_campi_aggiuntivi_opu_modify.jsp" %>

	
</table>
<br><br>

<% if (OrgDetails.getTipoAttivita()==2 && OrgDetails.getDatiMobile().size()>0){ %>
<%@ include file="../controlliufficiali/opu_controlli_ufficiali_mobili_modify.jsp" %>
<br><br>
<%} %>

<%@ include file="../controlliufficiali/opu_controlli_ufficiali_allarmerapido_modify.jsp" %>	
	
<%@ include file="../controlliufficiali/opu_controlli_ufficiali_laboratori_haccp_modify.jsp" %>	
<br>
<%@ include file="../controlliufficiali/opu_controlli_ufficiali_laboratori_haccp_non_in_regione_modify.jsp" %>

 <!-- commento al 214 -->
 
 <input type = "hidden" id = "size_p" name = "size_p" value = "<%=(lista_cani==null || lista_cani.isEmpty())?("1"):(lista_cani.size())%>">
 <br>
    <table id="canicontrollatitr" cellpadding="4" cellspacing="0" width="100%" class="details" style="display:none">
	
		<tr>
    <th colspan="7">
      <strong><dhv:label name="">Lista Cani Controllati</dhv:label></strong>
    </th>
	</tr>
	<tr>
    <th >
     Num Cane
    </th>
	<th >
     Microchip
    </th>
    <th >
     Razza
    </th>
    <th >
     Taglia
    </th>
    <th >
     Mantello
    </th>
    <th >
    Sesso
    </th>
    <th >
     Data Nascita Cane
    </th>
	</tr>
	 
	<%
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
	
	int num_cane = 1 ;
	//commento al 214
	//if(false)
	if(lista_cani!=null)
	{
	for (org.aspcfs.modules.canipadronali.base.Cane cane_vigilanza : lista_cani) { %>
	
	<input type = "hidden" name = "assetId_<%=num_cane %>" value = "<%=cane_vigilanza.getId() %>"/>
	<tr class="containerBody">
		<td><b><%=num_cane %></b></td>
		<td><input type = "text" id = "mc_<%=num_cane %>" name = "mc_<%=num_cane %>" value = "<%=toHtml(cane_vigilanza.getMc())%>" readonly="readonly"/>
		</td>
		<td><input type = "text" id = "razza_<%=num_cane %>" name = "razza_<%=num_cane %>" value = "<%= toHtml(cane_vigilanza.getRazza()) %>" readonly="readonly"/>
		</td>
	<td><input type = "text" id = "sesso_<%=num_cane %>" name = "sesso_<%=num_cane %>" value = "<%=toHtml(cane_vigilanza.getSesso())  %>" readonly="readonly"/>
		</td>
		<td><input type = "text" id = "taglia_<%=num_cane %>" name = "taglia_<%=num_cane %>" value = "<%= toHtml(cane_vigilanza.getTaglia())  %>" readonly="readonly"/>
		</td>
		<td><input type = "text" id = "mantello_<%=num_cane %>"  name = "mantello_<%=num_cane %>" value = "<%=toHtml(cane_vigilanza.getMantello())  %>" readonly="readonly"/>
		</td>
		<%

		String data_cane = "" ;
		if (cane_vigilanza!=null && cane_vigilanza.getDataNascita()!=null)
			data_cane = sdf2.format(cane_vigilanza.getDataNascita());
		%>
		<td>
		<input type = "text" id = "data_nascita_cane_<%=num_cane %>" name = "data_nascita_cane_<%=num_cane %>" readonly="readonly" value = "<%=data_cane %>">
		</td>
	</tr>
	
	<%num_cane ++ ;
	} }%>
	
</table>

        &nbsp;<br>
   <dhv:evaluate if="<%= !TicketDetails.isTrashed() %>" >
    <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>" >
        <dhv:permission name="accounts-accounts-vigilanza-edit">
          <input type="submit" value="<dhv:label name="button.reopen">Reopen</dhv:label>" onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>Vigilanza.do?command=ReopenTicket&id=<%=TicketDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'">
         </dhv:permission>
         <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>.do?command=ViewVigilanza&orgId=<%=OrgDetails.getIdStabilimento()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
         <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>Vigilanza.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
    </dhv:evaluate>
    <dhv:evaluate if="<%= TicketDetails.getClosed() == null %>" >
        <dhv:permission name="accounts-accounts-vigilanza-edit">
          <input type="button" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="return controlloCuSorveglianza()">
        </dhv:permission>
         <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>.do?command=ViewVigilanza&orgId=<%=OrgDetails.getIdStabilimento()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
         <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>Vigilanza.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
        <%= showAttribute(request, "closedError") %>
     </dhv:evaluate>
    </dhv:evaluate>
    <input type="hidden" name="modified" value="<%= TicketDetails.getModified() %>">
      
      
    <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  TicketDetails.getOrgSiteId() %>" />
    <input type="hidden" name="id" value="<%= TicketDetails.getId() %>">
  
    <input type="hidden" name="isAllegato" value="<%=TicketDetails.isListaDistribuzioneAllegata() %>">
    <input type="hidden" name="companyName" value="<%= toHtml(TicketDetails.getCompanyName()) %>">
    <input type="hidden" name="statusId" value="<%=  TicketDetails.getStatusId() %>" />
    <input type="hidden" name="trashedDate" value="<%=  TicketDetails.getTrashedDate() %>" />
    <input type="hidden" name="close" value="">
     <input type="hidden" id="ticketid" value="0" name="ticketidd">
    <input type="hidden" name="refresh" value="-1">
     <input type="hidden" name="isAllegato" value="<%=TicketDetails.isListaDistribuzioneAllegata() %>">
    <input type="hidden" name="currentDate" value="<%=  request.getAttribute("currentDate") %>" />
</dhv:container>

</form>
</body>
