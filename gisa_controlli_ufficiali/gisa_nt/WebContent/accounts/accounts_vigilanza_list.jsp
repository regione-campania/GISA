<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: accounts_tickets_list.jsp 18488 2007-01-15 20:12:32Z matt $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.vigilanza.base.Ticket,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="TipoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AuditTipo" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="EsitoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoAudit" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoIspezione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TicList" class="org.aspcfs.modules.vigilanza.base.TicketList" scope="request"/>
<jsp:useBean id="AccountTicketInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="flag" type="java.lang.Boolean" scope="request"/>
<jsp:useBean id="container" class="java.lang.String" scope="request"/>

<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_vigilanza_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" type="text/javascript">

  <%-- Preload image rollovers for drop-down menu --%>
  
</script>

<% String containerR  = ""; %>
<% if(container == null || container.equals("") ) { containerR = "accounts";} else { containerR = container; } %>

<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<% if(containerR != null && containerR.equals("accounts")){ %> 
	<a href="Accounts.do"><dhv:label name="">Stabilimenti 852</dhv:label></a> > 
	<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
	<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Scheda Stabilimento 852</dhv:label></a> >
<% }else { %>
	<a href="RicercaArchiviati.do"><dhv:label name="">Stabilimenti archiviati</dhv:label></a> > 
	<a href="RicercaArchiviati.do?command=SearchForm"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
	<a href="Accounts.do?command=Details&container=archiviati&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Scheda Stabilimento 852</dhv:label></a> >
<% } %>
<dhv:label name="vigilanza">Controlli Ufficiali</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>




<dhv:container name="<%=containerR%>"  selected="vigilanza" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
  <dhv:evaluate if="<%= !OrgDetails.isTrashed() %>">
    
 <% String info_mancanti = ""; 
    boolean b = false;
    
    //MANCA P_IVA E CF
    if (((OrgDetails.getCodiceFiscale()==null || OrgDetails.getCodiceFiscale().trim().equals("")) && 
     	    ((OrgDetails.getPartitaIva()==null || OrgDetails.getPartitaIva().trim().equals(""))) )) {
    		info_mancanti = info_mancanti+"Codice fiscale impresa o partita iva richieste<br>"; 
    		b=true;
    }
    
    //MANCA CF ED E' UNA ASSOCIAZIONE
 	if ((OrgDetails.getCodiceFiscale()==null || OrgDetails.getCodiceFiscale().trim().equals("")) && OrgDetails.getNo_piva()==true ) {
		info_mancanti = info_mancanti+"Codice fiscale impresa richiesto<br>"; 
		b=true;
	}  	

 	if (OrgDetails.getAccountNumber()==null || OrgDetails.getAccountNumber().trim().equals("")){
 		info_mancanti = info_mancanti+"Numero di registrazione richiesto <br>"; 
 		b=true;
 	}
    
 	if (OrgDetails.getSiteId()<=0) {
 		info_mancanti = info_mancanti+"ASL richiesta<br>"; 
 		b=true;
 	}
    
 	if (OrgDetails.getCodiceFiscaleCorrentista()==null || OrgDetails.getCodiceFiscaleCorrentista().trim().equals("") || OrgDetails.getCodiceFiscaleCorrentista().trim().equals("00.00.00") ) {
 		info_mancanti=info_mancanti+"Codice ISTAT principale non valido<br>"; 
 		b=true;
 	}
 	
    if (flag==true){
    	info_mancanti = info_mancanti+"Codice ISTAT secondario non valido<br>"; 
    	b=true;
    }
    
    if (OrgDetails.getTipoDest()!=null && OrgDetails.getTipoDest().equalsIgnoreCase("Es. Commerciale") && (OrgDetails.getAddressList().getAddress(5).getCity()==null || OrgDetails.getAddressList().getAddress(5).getCity().trim().equals(""))){ 
    	info_mancanti=info_mancanti+"Info Sede operativa incomplete<br>"; 
    	b=true;
    } else if (OrgDetails.getTipoDest()!=null && OrgDetails.getTipoDest().equalsIgnoreCase("distributori") && (OrgDetails.getAddressList().getAddress(1).getCity()==null || OrgDetails.getAddressList().getAddress(1).getCity().trim().equals(""))){ 
    	info_mancanti=info_mancanti+"Info Sede legale incomplete<br>"; 
    	b=true;
    } else if (OrgDetails.getTipoDest()!=null && OrgDetails.getTipoDest().equalsIgnoreCase("Autoveicolo") && (OrgDetails.getAddressList().getAddress(7).getCity()==null || OrgDetails.getAddressList().getAddress(7).getCity().trim().equals(""))){
    	info_mancanti=info_mancanti+"Info Sede attività mobile incomplete<br>"; 
    	b=true;
    }


if(containerR != null && containerR.equals("accounts")){ 

   if ( b==false ) { %> 
    <dhv:permission name="accounts-accounts-vigilanza-add"><a href="AccountVigilanza.do?command=Add&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.richiesta.add">Aggiungi Nuovo Controllo Ufficiale</dhv:label></a></dhv:permission>
  <%} else { %>
  <font color="red">NON E' POSSIBILE INSERIRE NUOVI CONTROLLI UFFICIALI PER L'OPERATORE PER MANCANZA DI ALCUNI CAMPI OBBLIGATORI IN ANAGRAFICA.</font><br>
<%--   <font color="red"><%=info_mancanti%></font><br>  
  <font color="red">INSERIRE I DATI RICHIESTI OPPURE CONTATTARE L'HELP DESK</font> --%>
   <dhv:evaluate if="<%=(OrgDetails.getEnabled())%>">
	    <dhv:permission name="accounts-accounts-edit">
	    	<a href="javascript:window.location.href='Accounts.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';">Inserire i dati richiesti</a>
	    </dhv:permission>
   </dhv:evaluate>
  <%} 
  
  }//fine parentesi container%>
  
  </dhv:evaluate>
    <input type=hidden name="orgId" value="<%= OrgDetails.getOrgId() %>">
    <br>
  <dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="AccountTicketInfo"/>
  

 <script>
function filtraCu(radio){
	var value = radio.value;
	loadModalWindow();
	window.location.href = 	"<%="Accounts"  %>.do?command=ViewVigilanza&orgId=<%= request.getParameter("orgId")%>&statusId="+value;
}

</script>


<dhv:permission name="cu-filtra-view">
<table style="border: 1px solid black">
<tr>
<td><input type="radio" id="tutti" name="status" value="-1" <%if (request.getParameter("statusId")==null || request.getParameter("statusId").equals("-1")){ %>checked<%} %> onChange="filtraCu(this)"/>Tutti</td>
<td><input type="radio" id="aperti" name="status" value="1" <%if (request.getParameter("statusId")!=null && request.getParameter("statusId").equals("1")){ %>checked<%} %> onChange="filtraCu(this)"/>Aperti/Riaperti</td>
<%-- <td><input type="radio" id="riaperti" name="status" value="3" <%if (request.getParameter("statusId")!=null && request.getParameter("statusId").equals("3")){ %>checked<%} %> onChange="filtraCu(this)"/>Riaperti</td> --%>
<td><input type="radio" id="chiusi" name="status" value="2" <%if (request.getParameter("statusId")!=null && request.getParameter("statusId").equals("2")){ %>checked<%} %> onChange="filtraCu(this)"/>Chiusi</td>
</tr>
</table>
<font color="red">Attenzione:  Per i filtri Aperti e Chiusi, tutti i risultati saranno mostrati nella prima pagina.</font>
</dhv:permission>

<%@ include file="../controlliufficiali/lista_controlli_ufficiali.jsp" %>
  <dhv:pagedListControl object="AccountTicketInfo"/>

	<br>

</dhv:container>