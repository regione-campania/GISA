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
  - Version: $Id: troubletickets_modify.jsp 18488 2007-01-15 20:12:32Z matt $
  - Description:
  --%>
  <%@page import="com.itextpdf.text.log.SysoLogger"%>
<%@page import="org.aspcfs.utils.web.LookupList" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.DateFormat" %>
<%@page import="org.aspcfs.modules.campioni.base.Analita"%>
<%@page import="org.aspcfs.utils.web.LookupElement"%>
<%@page import="org.aspcfs.modules.allerte_new.base.AslCoinvolte"%><jsp:useBean id="Acque" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiNonTrasformati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiNonTrasformatiValori" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiTrasformati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiVegetali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Regioni" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="TipoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoCampione_batteri" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoCampione_virus" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteIdListUtil" class="java.util.HashMap" scope="request"/>
<jsp:useBean id="TipoCampione_fisico" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoCampione_parassiti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoCampione_chimico" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoSpecie_latte" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoSpecie_uova" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoCampione_sottochimico" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AltriAlimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiVegetaliNonTraformati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiVegetaliTraformati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoCampione_sottochimico2" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="TipoCampione_sottochimico3" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="TipoCampione_sottochimico4" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="TipoCampione_sottochimico5" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<%@ page import="org.aspcfs.modules.troubletickets.base.*,org.aspcfs.modules.communications.base.Campaign,org.aspcfs.modules.communications.base.CampaignList,org.aspcfs.utils.web.HtmlSelect" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.allerte_new.base.Ticket" scope="request"/>
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="Grassi" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Vino" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Zuppe" class="org.aspcfs.utils.web.LookupList" scope="request"/>


<jsp:useBean id="FruttaFresca" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="FruttaSecca" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Funghi" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Ortaggi" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Derivati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Conservati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="tipoNotifica" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="resolvedByDeptList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SeverityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="causeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ticketStateList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolutionList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EscalationList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="resolvedUserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SanzioniAmministrative" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SanzioniPenali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoAlimento" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="UnitaMisura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Origine" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentoInteressato" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="NonConformita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ListaCommercializzazione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Sequestri" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SubList1" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList2" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList3" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="actionPlans" class="org.aspcfs.modules.actionplans.base.ActionPlanList" scope="request"/>
<jsp:useBean id="insertActionPlan" class="java.lang.String" scope="request"/>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="defectSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="defectCheck" class="java.lang.String" scope="request"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>

<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>

<%@ include file="../initPage.jsp" %>
<body onload="mostraorigineAllerta(); abilitaLista_tipoAnalisi(); init(); abilitaLista_tipoChimico();abilitapubblicazione(document.getElementById('flag_pubblicazione_allerte'))">
<form name="details"  action="TroubleTicketsAllerteNew.do?command=Update&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim()) ?"&defectCheck="+defectCheck:"") %><%= isPopup(request)?"&popup=true":"" %>" method="post">
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="TroubleTicketsAllerteNew.do"><dhv:label name="sanzioniddd">Allerte</dhv:label></a> > 
<%if(defectCheck != null && !"".equals(defectCheck.trim())) {%>
  <a href="TroubleTicketDefects.do?command=View"><dhv:label name="tickets.defects.viewDefects">View Defects</dhv:label></a> >
  <a href="TroubleTicketDefects.do?command=Details&defectId=<%= defectCheck %>"><dhv:label name="tickets.defects.defectDetails">Defect Details</dhv:label></a> >
  <a href="TroubleTicketsAllerteNew.do?command=Details&id=<%= TicketDetails.getId() %>&defectCheck=<%= defectCheck %>"><dhv:label name="sanzioni.dettaglisss">Scheda Allerta</dhv:label></a> >
<%}else{%>
<% if (("list".equals((String)request.getParameter("return"))) ||
      ("searchResults".equals((String)request.getParameter("return")))) {%>
    <% if ("yes".equals((String)session.getAttribute("searchTickets"))) {%>
      <a href="TroubleTicketsAllerteNew.do?command=SearchTicketsForm"><dhv:label name="tickets.searchForm">Search Form</dhv:label></a> >
      <a href="TroubleTicketsAllerteNew.do?command=SearchTickets"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
    <%}else{%> 
      <a href="TroubleTicketsAllerteNew.do?command=Home"><dhv:label name="sanzioni.visualizzaddd">Visualizza Allerte</dhv:label></a> >
    <%}%>
<%} else {%>
  <% if ("yes".equals((String)session.getAttribute("searchTickets"))) {%>
    <a href="TroubleTicketsAllerteNew.do?command=SearchTickets"><dhv:label name="tickets.searchss">Ricerca Allerte</dhv:label></a> >
  <%}else{%> 
    <a href="TroubleTicketsAllerteNew.do?command=Home"><dhv:label name="sanzioni.visualizzaaa">Visualizza Allerte</dhv:label></a> >
  <%}%>
    <a href="TroubleTicketsAllerteNew.do?command=Details&id=<%= TicketDetails.getId() %>"><dhv:label name="sanzioni.dettagliss">Scheda Allerta</dhv:label></a> >
<%}%>
<%}%>
<dhv:label name="sanzioni.modifysss">Modifica Allerta</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<% String param1 = "id=" + TicketDetails.getId(); %>
<dhv:container name="sanzioni" selected="details" object="TicketDetails" param="<%= param1 %>" hideContainer='<%= (isPopup(request) || (defectCheck != null && !"".equals(defectCheck.trim()))) %>'>
  <%@ include file="ticket_header_include.jsp" %>
  <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>">
    <font color="red"><dhv:label name="tickets.alert.closed">This ticket has been closed:</dhv:label>
    <zeroio:tz timestamp="<%= TicketDetails.getClosed() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
    </font><br />
  </dhv:evaluate>
    <% if (TicketDetails.getClosed() != null) { %>
      <%--<input type="button" value="<dhv:label name="button.reopen">Reopen</dhv:label>" onClick="javascript:this.form.action='TroubleTicketsAllerteNew.do?command=Reopen&ticketId=<%= TicketDetails.getId()%><%= (defectCheck != null && !"".equals(defectCheck.trim()) ?"&defectCheck="+defectCheck:"") %>';submit();">
      <input type="submit" value="Annulla" onClick='<%= (!isPopup(request)?"javascript:this.form.action='TroubleTicketsAllerteNew.do?command=Details&id="+ TicketDetails.getId() +"'":"javascript:window.close();") %>' />--%>
    <%} else {%>
      <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="return checkForm(this.form)">
      <% if ("list".equals(request.getParameter("return"))) {%>
        <input type="submit" value="Annulla" onClick='<%= (!isPopup(request)?"javascript:this.form.action='TroubleTicketsAllerteNew.do?command=Home'":"javascript:window.close();") %>' />
      <%} else if ("searchResults".equals(request.getParameter("return"))){%>
        <input type="submit" value="Annulla" onClick='<%= (!isPopup(request)?"javascript:this.form.action='TroubleTicketsAllerteNew.do?command=SearchTickets'":"javascript:window.close();") %>' />
      <% }else {%>
        <input type="submit" value="Annulla" onClick="javascript:this.form.action='TroubleTicketsAllerteNew.do?command=Details&id=<%=TicketDetails.getId() %>'" />
      <%}%>
  <%}%>
  <br />
  <dhv:formMessage />
  <iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popServiceContracts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAssets.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProducts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript">



/*function abilitaData(value)
{

	if(value == "si")
	{
		document.getElementById("colonnaChiusura").style.display="";
	}
	else{
		document.details.dataChiusura.value = "";
		document.getElementById("colonnaChiusura").style.display="none";
		}

	
}*/

function disabilitaMatriciCanili(){
	if(document.getElementById("checkMatriciCanili")!=null)
	document.getElementById("checkMatriciCanili").disabled=true;
	
}

function abilitaMatriciCaniliCheck(){
	if(document.getElementById("checkMatriciCanili")!=null)
	document.getElementById("checkMatriciCanili").disabled=false; 
	
}
function abilitapubblicazione(campo)
{
	if (campo.checked==true)
	{
		document.getElementById('pubblicazione').style.display="";
	}
	else
	{
		document.getElementById('pubblicazione').style.display="none";
		}
}
function init(){
	
	/*var flag=0;
	var check=document.getElementById("materialialimenti");

	if(check!=null && check.checked) 
	{
		flag = 1;
		document.details.fruttaFresca.style.display="none";
   	 	document.details.fruttaSecca.style.display="none";
   	 	document.details.ortaggi.style.display="none";
   	 	document.details.funghi.style.display="none";
   	 	document.details.derivati.style.display="none";
   	 	document.details.conservati.style.display="none";
   	 	document.details.vino.style.display="none";
   	 	document.details.grassi.style.display="none";
   	 	document.details.zuppe.style.display="none";
		document.getElementById("lookupVegetale").style.visibility = "hidden";
	    document.getElementById("notealimenti2").style.display="none";
	    document.details.alimentiOrigineVegetaleValoriNonTrasformati.value="-1";
		document.details.alimentiOrigineVegetaleValoriNonTrasformati.style.display="none";
		document.details.alimentiOrigineVegetaleValoriTrasformati.value="-1";
		document.details.alimentiOrigineVegetaleValoriTrasformati.style.display="none"; 
		document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
	 	document.details.TipoSpecie_uova.style.display="none";
	 	document.details.TipoSpecie_latte.style.display="none";
	 	document.details.TipoSpecie_uova.value="-1";
	 	document.details.TipoSpecie_latte.value="-1";
	 	document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
	 	document.details.alimentiOrigineAnimaleNonTrasformatiValori.value= -1;
	 	document.getElementById("tipoAlimentiAnimali").style.display="none";
	 	document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
	 	document.forms['details'].alimentiOrigineAnimaleNonTrasformati.style.display="none";
	 	document.forms['details'].alimentiOrigineAnimaleNonTrasformati.value="-1";
	 	document.forms['details'].alimentiOrigineAnimaleTrasformati.value="-1";
	 	document.forms['details'].alimentiOrigineAnimaleTrasformati.style.display="none"
	 	document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
	 	document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
	 	document.getElementById("testoAlimentoComposto").style.visibility = "hidden";
		disabilitaCompostiVegetale();
	    disabilitaComposti();
	   // disabilitaDolciumi();
	    //disabilitaGelati();
	  
	    disabilitaAcque();
	   	disabilitaBevande();
	    disabilitaMangimi();
	    disabilitaCompostiAnimale();
	    disabilitaAltriAlimenti();
	   	disabilitaAdditivi();
	   	document.getElementById("notematerialialimenti").style.display="block";
	}

	var check=document.getElementById("additivi");
	if(check.checked)
	{
		flag = 1
		document.details.fruttaFresca.style.display="none";
   	 	document.details.fruttaSecca.style.display="none";
   	 	document.details.ortaggi.style.display="none";
   	 	document.details.funghi.style.display="none";
   	 	document.details.derivati.style.display="none";
   	 	document.details.conservati.style.display="none";
   	 	document.details.vino.style.display="none";
   	 	document.details.grassi.style.display="none";
   	 	document.details.zuppe.style.display="none";
		document.getElementById("lookupVegetale").style.visibility = "hidden";
     	document.getElementById("notealimenti2").style.display="none";
	    document.details.alimentiOrigineVegetaleValoriNonTrasformati.value="-1";
		document.details.alimentiOrigineVegetaleValoriNonTrasformati.style.display="none";
		document.details.alimentiOrigineVegetaleValoriTrasformati.value="-1";
		document.details.alimentiOrigineVegetaleValoriTrasformati.style.display="none";
		document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
	 	document.details.TipoSpecie_uova.style.display="none";
	 	document.details.TipoSpecie_latte.style.display="none";
	 	document.details.TipoSpecie_uova.value="-1";
	 	document.details.TipoSpecie_latte.value="-1";
		document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
	 	document.details.alimentiOrigineAnimaleNonTrasformatiValori.value= -1;
		document.getElementById("tipoAlimentiAnimali").style.display="none";
	 	document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
	 	document.forms['details'].alimentiOrigineAnimaleNonTrasformati.style.display="none";
	 	document.forms['details'].alimentiOrigineAnimaleNonTrasformati.value="-1";
	 	document.forms['details'].alimentiOrigineAnimaleTrasformati.value="-1";
	 	document.forms['details'].alimentiOrigineAnimaleTrasformati.style.display="none"
	 	document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
	 	document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
		document.getElementById("testoAlimentoComposto").style.visibility = "hidden";
		disabilitaCompostiVegetale();
	    disabilitaComposti();
	    disabilitaAltriAlimenti();
	    disabilitaAcque();
	    disabilitaBevande();
	    disabilitaMangimi();
	    //    disabilitaGelati();
	    //      disabilitaDolciumi();
		   
	    disabilitaCompostiAnimale();
	    disabilitaMaterialiAlimenti();
		document.getElementById("noteadditivi").style.display="block";
		
	}
	
	 alimentiOrigine = document.getElementById("acqua");
	    //sel2 = document.getElementById("lookupVegetale");
	    if(alimentiOrigine.checked==true)
	    {
	    	 flag = 1
	    	 document.details.alimentiOrigineVegetaleValoriNonTrasformati.value="-1";
			 document.details.alimentiOrigineVegetaleValoriNonTrasformati.style.display="none";
			 document.details.alimentiOrigineVegetaleValoriTrasformati.value="-1";
			 document.details.alimentiOrigineVegetaleValoriTrasformati.style.display="none";
	    	 document.details.fruttaFresca.style.display="none";
	    	 document.details.fruttaSecca.style.display="none";
	    	 document.details.ortaggi.style.display="none";
	    	 document.details.funghi.style.display="none";
	    	 document.details.derivati.style.display="none";
	    	 document.details.conservati.style.display="none";
	    	 document.details.vino.style.display="none";
	    	 document.details.grassi.style.display="none";
	    	 document.details.zuppe.style.display="none";
	    	 document.getElementById("lookupVegetale").style.visibility = "hidden";
			 document.getElementById("notealimenti2").style.display="none";
	    	//     disabilitaDolciumi();
	      	//   disabilitaGelati();
	        
	    	 document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
	     	 document.details.TipoSpecie_uova.style.display="none";
	     	 document.details.TipoSpecie_latte.style.display="none";
	     	 document.details.TipoSpecie_uova.value="-1";
	     	 document.details.TipoSpecie_latte.value="-1";
	       	 document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
	         document.details.alimentiOrigineAnimaleNonTrasformatiValori.value= -1;
	      	 document.getElementById("tipoAlimentiAnimali").style.display="none";
	     	 document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
	     	 document.forms['details'].alimentiOrigineAnimaleNonTrasformati.style.display="none";
	     	 document.forms['details'].alimentiOrigineAnimaleNonTrasformati.value="-1";
	     	 document.forms['details'].alimentiOrigineAnimaleTrasformati.value="-1";
	     	 document.forms['details'].alimentiOrigineAnimaleTrasformati.style.display="none"
	     	 document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
	     	 document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
	    	 document.getElementById("testoAlimentoComposto").style.visibility = "hidden";
	    	 document.getElementById("acquaSelect").style.display="block";
	     	 document.getElementById("noteacqua").style.display="block";
	     	 disabilitaAltriAlimenti();
	    	 disabilitaCompostiVegetale();
			 disabilitaCompostiAnimale();
	    	 disabilitaComposti();
	    	// disabilitaGelati();
      		//  disabilitaDolciumi();
	 		disabilitaMaterialiAlimenti();
	 	    disabilitaAdditivi();
	    	disabilitaBevande();
	    	disabilitaMangimi();

	   
	    }

	    var check=document.getElementById("alimentinonAnimali");

		if(check.checked)
		{
		
			flag = 1
			document.getElementById("lookupVegetale").style.visibility = "hidden";
		    document.getElementById("notealimenti2").style.display="none";
		    abilitaAltriAlimenti();
		   	document.details.alimentiOrigineVegetaleValoriNonTrasformati.value="-1";
			document.details.alimentiOrigineVegetaleValoriNonTrasformati.style.display="none";
			document.details.alimentiOrigineVegetaleValoriTrasformati.value="-1";
			document.details.alimentiOrigineVegetaleValoriTrasformati.style.display="none";
			document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
		 	document.details.TipoSpecie_uova.style.display="none";
		 	document.details.TipoSpecie_latte.style.display="none";
		 	document.details.TipoSpecie_uova.value="-1";
		 	document.details.TipoSpecie_latte.value="-1";
		 	document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
		 	document.details.alimentiOrigineAnimaleNonTrasformatiValori.value= -1;
		 	document.getElementById("tipoAlimentiAnimali").style.display="none";
		 	document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
		 	document.forms['details'].alimentiOrigineAnimaleNonTrasformati.style.display="none";
		 	document.forms['details'].alimentiOrigineAnimaleNonTrasformati.value="-1";
		 	document.forms['details'].alimentiOrigineAnimaleTrasformati.value="-1";
		 	document.forms['details'].alimentiOrigineAnimaleTrasformati.style.display="none"
		 	document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
		 	document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
			document.getElementById("testoAlimentoComposto").style.visibility = "hidden";
			document.getElementById("alimentinonanimalicella").style.display = "block";
			document.details.tipoAlimento.value="-1";
			document.details.alimentiOrigineVegetaleValoriNonTrasformati.value="-1";
			document.details.alimentiOrigineVegetaleValoriNonTrasformati.style.display="none";
			document.details.alimentiOrigineVegetaleValoriTrasformati.value="-1";
			document.details.alimentiOrigineVegetaleValoriTrasformati.style.display="none";
			disabilitaCompostiVegetale();
		    disabilitaComposti();
		  	disabilitaAdditivi();
		    disabilitaAcque();
		    disabilitaBevande();
		    disabilitaMangimi();
		     //  disabilitaGelati();
		     // disabilitaDolciumi();
			disabilitaCompostiAnimale();
		    disabilitaMaterialiAlimenti();
		   	document.getElementById("noteadditivi").style.display="none";
			
		}

	 alimentiOrigine = document.getElementById("bevande");
	    //sel2 = document.getElementById("lookupVegetale");
	    if(alimentiOrigine.checked)
	    { 
		    //sel2.style.visibility = "visible";
	    	//disabilitaCompostiAnimale();
	    	//disabilitaComposti();
	     	document.getElementById("lookupVegetale").style.visibility = "hidden";
	     	flag = 1
	     	document.getElementById("notealimenti2").style.display="none";
	     	document.details.alimentiOrigineVegetaleValoriNonTrasformati.value="-1";
		 	document.details.alimentiOrigineVegetaleValoriNonTrasformati.style.display="none";
		 	document.details.alimentiOrigineVegetaleValoriTrasformati.value="-1";
		 	document.details.alimentiOrigineVegetaleValoriTrasformati.style.display="none";
		 	document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
	 		document.details.TipoSpecie_uova.style.display="none";
	 		document.details.TipoSpecie_latte.style.display="none";
	 		document.details.TipoSpecie_uova.value="-1";
	 		document.details.TipoSpecie_latte.value="-1";
	 		document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
	 		document.details.alimentiOrigineAnimaleNonTrasformatiValori.value= -1;
	 		document.getElementById("tipoAlimentiAnimali").style.display="none";
	 		document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
	 		document.forms['details'].alimentiOrigineAnimaleNonTrasformati.style.display="none";
	 		document.forms['details'].alimentiOrigineAnimaleNonTrasformati.value="-1";
	 		document.forms['details'].alimentiOrigineAnimaleTrasformati.value="-1";
	 		document.forms['details'].alimentiOrigineAnimaleTrasformati.style.display="none"
	 		document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
	 		document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
	 		document.details.fruttaFresca.style.display="none";
   	 		document.details.fruttaSecca.style.display="none";
   	 		document.details.ortaggi.style.display="none";
   	 		document.details.funghi.style.display="none";
   	 		document.details.derivati.style.display="none";
   	 		document.details.conservati.style.display="none";
   	 		document.details.vino.style.display="none";
   	 		document.details.grassi.style.display="none";
   	 		document.details.zuppe.style.display="none";
		 	document.getElementById("testoAlimentoComposto").style.visibility = "hidden";
		 	disabilitaAltriAlimenti();
	    	disabilitaCompostiVegetale();
			disabilitaCompostiAnimale();
	    	disabilitaComposti();
	    	disabilitaAcque();
	 		disabilitaMaterialiAlimenti();
			// disabilitaGelati();
   			//  disabilitaDolciumi();
	    	disabilitaAdditivi();
	    	document.getElementById("notebevande").style.display="block";
	  }


	    alimentiOrigine = document.getElementById("mangimi");
	    //sel2 = document.getElementById("lookupVegetale");
	    if(alimentiOrigine.checked)
	    {
		     	//sel2.style.visibility = "visible";
	    		//disabilitaCompostiAnimale();
	    		//disabilitaComposti();
	     	document.getElementById("lookupVegetale").style.visibility = "hidden";
	     	flag = 1
	     	document.getElementById("notealimenti2").style.display="none";
	     	document.details.alimentiOrigineVegetaleValoriNonTrasformati.value="-1";
		 	document.details.alimentiOrigineVegetaleValoriNonTrasformati.style.display="none";
		 	document.details.alimentiOrigineVegetaleValoriTrasformati.value="-1";
		 	document.details.alimentiOrigineVegetaleValoriTrasformati.style.display="none";
			document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
	 		document.details.TipoSpecie_uova.style.display="none";
	 		document.details.TipoSpecie_latte.style.display="none";
	 		document.details.TipoSpecie_uova.value="-1";
	 		document.details.TipoSpecie_latte.value="-1";
	 		document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
	 		document.details.alimentiOrigineAnimaleNonTrasformatiValori.value= -1;
	 		document.getElementById("tipoAlimentiAnimali").style.display="none";
	 		document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
	 		document.forms['details'].alimentiOrigineAnimaleNonTrasformati.style.display="none";
	 		document.forms['details'].alimentiOrigineAnimaleNonTrasformati.value="-1";
	 		document.forms['details'].alimentiOrigineAnimaleTrasformati.value="-1";
	 		document.forms['details'].alimentiOrigineAnimaleTrasformati.style.display="none"
	 		document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
	 		document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
	 		document.details.fruttaFresca.style.display="none";
   	 		document.details.fruttaSecca.style.display="none";
   	 		document.details.ortaggi.style.display="none";
   	 		document.details.funghi.style.display="none";
   	 		document.details.derivati.style.display="none";
   	 		document.details.conservati.style.display="none";
   	 		document.details.vino.style.display="none";
   	 		document.details.grassi.style.display="none";
   	 		document.details.zuppe.style.display="none";
		 	document.getElementById("testoAlimentoComposto").style.visibility = "hidden";
		 	disabilitaAltriAlimenti();
	    	disabilitaCompostiVegetale();
			disabilitaCompostiAnimale();
	    	disabilitaComposti();
	    	disabilitaAcque();
	 		disabilitaMaterialiAlimenti();
			// disabilitaGelati();
   			//  disabilitaDolciumi();
	    	disabilitaAdditivi();
	    	disabilitaBevande();
	    	document.getElementById("noteAlimentiMangimi").style.display="block";
	    }

	   

	if(document.details.alimentiOrigineAnimale.checked)
	{

		
		
		flag = 1
		disabilitaAcque();
		disabilitaAdditivi();
		disabilitaBevande();
		disabilitaMangimi();
		disabilitaMaterialiAlimenti();
		disabilitaAltriAlimenti();
		document.getElementById("acquaSelect").style.display="none";
		document.getElementById("noteacqua").style.display="none";
		document.getElementById("notebevande").style.display="none";
		document.getElementById("noteAlimentiMangimi").style.display="none";
		document.getElementById("noteadditivi").style.display="none";
		document.details.fruttaFresca.style.display="none";
	    document.details.fruttaSecca.style.display="none";
	    document.details.ortaggi.style.display="none";
	    document.details.funghi.style.display="none";
	    document.details.derivati.style.display="none";
	    document.details.conservati.style.display="none";
	    document.details.vino.style.display="none";
	    document.details.grassi.style.display="none";
	    document.details.zuppe.style.display="none";
		document.getElementById("notematerialialimenti").style.display="none";
		tipo=document.details.tipoAlimentiAnimali.value;

		
		
		if(tipo == "1")
		{
			
			abilitaLookupOrigineVegetale();
			document.forms['details'].alimentiOrigineAnimaleTrasformati.value="-1";
			document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
			document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
			document.forms['details'].alimentiOrigineAnimaleNonTrasformati.style.display="block";
			document.forms['details'].alimentiOrigineAnimaleTrasformati.style.display="none"
			disabilitaCompostiVegetale();      
	      	disabilitaComposti();
		  	abilitaSpecie(document.forms['details']);

		  	
	    }
	    else
		{
			if(tipo == "2")
			{
				document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
	   			document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
				document.forms['details'].alimentiOrigineAnimaleNonTrasformati.style.display="none";
				document.forms['details'].alimentiOrigineAnimaleNonTrasformati.value = "-1";

				if(document.forms['details'].alimentiOrigineAnimaleTrasformati != null)
		    	{
					document.forms['details'].alimentiOrigineAnimaleTrasformati.style.display="block";
		    	}
				disabilitaCompostiVegetale();
				abilitaLookupOrigineVegetale();
				disabilitaComposti();
			
				disabilitaNon_Trasformati();
				
		
			}
			else
			{ 
				abilitaLookupOrigineVegetale();
	  			document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
	    		document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
				document.forms['details'].alimentiOrigineAnimaleNonTrasformati.style.display="none";
				document.forms['details'].alimentiOrigineAnimaleNonTrasformati.value="-1";
				document.forms['details'].alimentiOrigineAnimaleTrasformati.value="-1";
				document.forms['details'].alimentiOrigineAnimaleTrasformati.style.display="none"
				document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
				document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
		 		disabilitaCompostiVegetale();
		 		disabilitaAltriAlimenti();
	     		disabilitaComposti();
	     		disabilitaAltriAlimenti();

			} 
   }
//	disabilitaDolciumi();
  //  disabilitaGelati();
	disabilitaAcque();
	disabilitaAdditivi();
	disabilitaBevande();
	disabilitaMangimi();
	disabilitaMaterialiAlimenti();
	disabilitaAltriAlimenti();

	}
	


	if(document.details.alimentiOrigineVegetale.checked)
	{
		document.forms['details'].TipoSpecie_uova.style.display="none";
		document.forms['details'].TipoSpecie_latte.style.display="none";
		document.forms['details'].TipoSpecie_uova.value="-1";
		document.forms['details'].TipoSpecie_latte.value="-1";
		document.getElementById("tipoAlimentiAnimali").style.display="none";
	 	document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
	 	document.forms['details'].alimentiOrigineAnimaleNonTrasformati.style.display="none";
	 	document.forms['details'].alimentiOrigineAnimaleNonTrasformati.value="-1";
	 	document.forms['details'].alimentiOrigineAnimaleTrasformati.value="-1";
	 	document.forms['details'].alimentiOrigineAnimaleTrasformati.style.display="none"
	 	document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
	 	document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
		flag = 1
		abilitaLookupOrigineVegetale();
	 	disabilitaAltriAlimenti();
	}

	


	if(document.details.alimentiComposti.checked)
	{
		flag = 1
		//disabilitaDolciumi();
        //disabilitaGelati();
        disabilitaAcque();
	 	disabilitaAdditivi();
	 	disabilitaBevande();
	 	disabilitaAltriAlimenti();
	 	disabilitaMaterialiAlimenti();
		disabilitaMangimi();
		document.getElementById("acquaSelect").style.display="none";
	    document.getElementById("noteacqua").style.display="none";
	    document.getElementById("notebevande").style.display="none";
	    document.getElementById("noteAlimentiMangimi").style.display="none";
	    document.getElementById("noteadditivi").style.display="none";
	    document.getElementById("notematerialialimenti").style.display="none";
	 	document.getElementById("lookupVegetale").style.visibility = "hidden";
     	document.getElementById("notealimenti2").style.display="none";
     	document.details.alimentiOrigineVegetaleValoriNonTrasformati.value="-1";
	 	document.details.alimentiOrigineVegetaleValoriNonTrasformati.style.display="none";
		document.details.alimentiOrigineVegetaleValoriTrasformati.value="-1";
	 	document.details.alimentiOrigineVegetaleValoriTrasformati.style.display="none";
	 	document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
		document.details.TipoSpecie_uova.style.display="none";
 		document.details.TipoSpecie_latte.style.display="none";
 		document.details.TipoSpecie_uova.value="-1";
 		document.details.TipoSpecie_latte.value="-1";
 		document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
 		document.details.alimentiOrigineAnimaleNonTrasformatiValori.value= -1;
		document.getElementById("tipoAlimentiAnimali").style.display="none";
 		document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
 		document.forms['details'].alimentiOrigineAnimaleNonTrasformati.style.display="none";
 		document.forms['details'].alimentiOrigineAnimaleNonTrasformati.value="-1";
 		document.forms['details'].alimentiOrigineAnimaleTrasformati.value="-1";
 		document.forms['details'].alimentiOrigineAnimaleTrasformati.style.display="none"
 		document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
 		document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";		
	 	document.getElementById("testoAlimentoComposto").style.visibility = "";
		disabilitaCompostiAnimale();
		disabilitaCompostiVegetale();
       	document.details.fruttaFresca.style.display="none";
    	document.details.fruttaSecca.style.display="none";
    	document.details.ortaggi.style.display="none";
    	document.details.funghi.style.display="none";
    	document.details.derivati.style.display="none";
    	document.details.conservati.style.display="none";
    	document.details.vino.style.display="none";
    	document.details.grassi.style.display="none";
    	document.details.zuppe.style.display="none";
    	alert('composti composto')
		
		
	}

	if(flag == 0)
	{

		document.getElementById("acquaSelect").style.display="none";
	    document.getElementById("noteacqua").style.display="none";
	    document.getElementById("notebevande").style.display="none";
	    document.getElementById("noteAlimentiMangimi").style.display="none";
	    document.getElementById("noteadditivi").style.display="none";
	    document.getElementById("notematerialialimenti").style.display="none";
	 	document.getElementById("lookupVegetale").style.visibility = "hidden";
     	document.getElementById("notealimenti2").style.display="none";
     	document.details.alimentiOrigineVegetaleValoriNonTrasformati.value="-1";
	 	document.details.alimentiOrigineVegetaleValoriNonTrasformati.style.display="none";
		document.details.alimentiOrigineVegetaleValoriTrasformati.value="-1";
	 	document.details.alimentiOrigineVegetaleValoriTrasformati.style.display="none";
	 	document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
		document.details.TipoSpecie_uova.style.display="none";
 		document.details.TipoSpecie_latte.style.display="none";
 		document.details.TipoSpecie_uova.value="-1";
 		document.details.TipoSpecie_latte.value="-1";
 		document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
 		document.details.alimentiOrigineAnimaleNonTrasformatiValori.value= -1;
		document.getElementById("tipoAlimentiAnimali").style.display="none";
 		document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
 		document.forms['details'].alimentiOrigineAnimaleNonTrasformati.style.display="none";
 		document.forms['details'].alimentiOrigineAnimaleNonTrasformati.value="-1";
 		document.forms['details'].alimentiOrigineAnimaleTrasformati.value="-1";
 		document.forms['details'].alimentiOrigineAnimaleTrasformati.style.display="none"
 		document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
 		document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";		
	 	document.getElementById("testoAlimentoComposto").style.visibility = "hidden";
	}*/

	

	}


function controllaNumeroCuRipianificati(cuIniziali,campo)
{
	if (controlloNumber(campo)==false)
	{
		alert('Non è possibile inserire un valore non numerico!')
		campo.value = cuIniziali;
	}
	else
	if(campo.value <cuIniziali)
	{
		alert('Attenzione non è possibile ripianificare un numero di controlli inferiore a quelli esistenti!')
		campo.value = cuIniziali;
	}
	

}

function mostraorigineAllerta(){

	if(document.details.Origine !=null)
	{
	origine=document.details.Origine.value;
	
	if(origine==1 || origine==2 || origine==3){
	if(origine==1 || origine==2){
		document.details.aslOrigine.style.display="";
		document.details.regioneOrigine.style.display="none";

	}
	if(origine==3){
		document.details.aslOrigine.style.display="none";
		document.details.regioneOrigine.style.display="";
		}
	}
	else{
		document.details.aslOrigine.style.display="none";
		document.details.regioneOrigine.style.display="none";

		}}
	}







function abilitaLookupOrigineAnimale()
{

    /*alimentiOrigine = document.forms['details'].tipoAlimentiAnimali.value;

   


    

    
    
    sel2 = document.details.alimentiOrigineAnimaleNonTrasformatiValori;

    if(alimentiOrigine==1)
    { 
    	  document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
        
    	  document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
document.forms['details'].alimentiOrigineAnimaleNonTrasformati.style.display="block";
document.forms['details'].alimentiOrigineAnimaleTrasformati.style.display="none"
	document.forms['details'].alimentiOrigineAnimaleTrasformati.value="-1";

      disabilitaCompostiVegetale();
      
      disabilitaComposti();
    }else{
if(alimentiOrigine=="2"){
	  document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
   
	  document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
	document.forms['details'].alimentiOrigineAnimaleNonTrasformati.style.display="none";
	document.forms['details'].alimentiOrigineAnimaleNonTrasformati.value="-1";
	document.forms['details'].alimentiOrigineAnimaleTrasformati.style.display="block"
		 disabilitaCompostiVegetale();
	document.forms['details'].TipoSpecie_latte.style.display="none";
    document.forms['details'].TipoSpecie_uova.style.display="none";
	   document.forms['details'].TipoSpecie_latte.style.value="-1";
	      document.forms['details'].TipoSpecie_uova.value="-1";
    disabilitaComposti();
	
}
else
{ 
	document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
     
	document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
	document.forms['details'].alimentiOrigineAnimaleNonTrasformati.style.display="none";
	document.forms['details'].alimentiOrigineAnimaleNonTrasformati.value="-1";
	document.forms['details'].alimentiOrigineAnimaleTrasformati.value="-1";
	document.forms['details'].alimentiOrigineAnimaleTrasformati.style.display="none"
		document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
	document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
	 disabilitaCompostiVegetale();
		document.forms['details'].TipoSpecie_latte.style.display="none";
	    document.forms['details'].TipoSpecie_uova.style.display="none";
		   document.forms['details'].TipoSpecie_latte.style.value="-1";
		      document.forms['details'].TipoSpecie_uova.value="-1";
     disabilitaComposti();

	 
 
 
} 

        }
   */
     
}





function abilitaSpecie(form)
{
	
   /* if((form.alimentiOrigineAnimaleNonTrasformati.value >= 1) && (form.alimentiOrigineAnimaleNonTrasformati.value <= 4))
     {
    	form.alimentiOrigineAnimaleTrasformati.value="-1";
       document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="block";
      
  	form.TipoSpecie_uova.style.display="none";
      form.TipoSpecie_uova.value="-1";
      form.TipoSpecie_latte.value="-1";
  	form.TipoSpecie_latte.style.display="none";
  	document.getElementById("notealimenti").style.display="block";
     } 
    else
    {

    	if(form.alimentiOrigineAnimaleNonTrasformati.value==8)
       	{
			form.alimentiOrigineAnimaleTrasformati.value=-1;
    		form.TipoSpecie_uova.value="-1";
    		document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
    	   	document.details.alimentiOrigineAnimaleNonTrasformatiValori.value= -1;
    		form.TipoSpecie_latte.style.display="block";
    		form.TipoSpecie_uova.style.display="none";
    		form.TipoSpecie_uova.value=-1;
    	 	document.getElementById("notealimenti").style.display="block";
    		
        }
        else
        {
            if(form.alimentiOrigineAnimaleNonTrasformati.value==9)
            {
            	form.alimentiOrigineAnimaleTrasformati.value=-1;
            	form.TipoSpecie_latte.value="-1";
            	document.details.alimentiOrigineAnimaleNonTrasformatiValori.value=-1;
        		document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
        		form.TipoSpecie_uova.style.display="block";
            	form.TipoSpecie_latte.style.display="none";
            	document.getElementById("notealimenti").style.display="block";
            }
            else
            {
                document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
                form.alimentiOrigineAnimaleTrasformati.value="-1";
                document.getElementById("notealimenti").style.display="block";
        		form.TipoSpecie_uova.style.display="none";
        	  	form.TipoSpecie_latte.style.display="none";
        	  	form.TipoSpecie_uova.value="-1";
        	  	form.TipoSpecie_latte.value="-1";
    			document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
      			form.alimentiOrigineAnimaleNonTrasformatiValori.value= -1;
  
     		} 
     	}
     }
     
    */


     
}

function abilitaTestoAlimentoComposto()
{
	/*
 	if(document.details.alimentiComposti.checked )
 	{
 		
		disabilitaAcque();
	 	disabilitaAdditivi();
	 	disabilitaBevande();
	 	disabilitaAltriAlimenti();
	 	disabilitaMaterialiAlimenti();
		disabilitaMangimi();
		document.getElementById("acquaSelect").style.display="none";
	    document.getElementById("noteacqua").style.display="none";
	    document.getElementById("notebevande").style.display="none";
	    document.getElementById("noteAlimentiMangimi").style.display="none";
	    document.getElementById("noteadditivi").style.display="none";
	    document.getElementById("notematerialialimenti").style.display="none";
	 	document.getElementById("lookupVegetale").style.visibility = "hidden";
     	document.getElementById("notealimenti2").style.display="none";
     	document.details.alimentiOrigineVegetaleValoriNonTrasformati.value="-1";
	 	document.details.alimentiOrigineVegetaleValoriNonTrasformati.style.display="none";
		document.details.alimentiOrigineVegetaleValoriTrasformati.value="-1";
	 	document.details.alimentiOrigineVegetaleValoriTrasformati.style.display="none";
	 	document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
		document.details.TipoSpecie_uova.style.display="none";
 		document.details.TipoSpecie_latte.style.display="none";
 		document.details.TipoSpecie_uova.value="-1";
 		document.details.TipoSpecie_latte.value="-1";
 		document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
 		document.details.alimentiOrigineAnimaleNonTrasformatiValori.value= -1;
		document.getElementById("tipoAlimentiAnimali").style.display="none";
 		document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
 		document.forms['details'].alimentiOrigineAnimaleNonTrasformati.style.display="none";
 		document.forms['details'].alimentiOrigineAnimaleNonTrasformati.value="-1";
 		document.forms['details'].alimentiOrigineAnimaleTrasformati.value="-1";
 		document.forms['details'].alimentiOrigineAnimaleTrasformati.style.display="none"
 		document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
 		document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";		
	 	document.getElementById("testoAlimentoComposto").style.visibility = "";
		disabilitaCompostiAnimale();
		disabilitaCompostiVegetale();
  //disabilitaGelati();
  //disabilitaDolciumi();

 	}
 	else
 	{
 	 	clearAll();
	 	abilitaAdditivi();
	 	abilitaalimentinonAnimali();
     	abilitaBevandeCheck();
     	abilitaMangimiCheck();
     	abilitaAcqueCheck();
    	// abilitaDolciumiCheck();
    	// abilitaGelatiCheck();
     	abilitaMaterialiAlimenti();	
	 	document.getElementById("testoAlimentoComposto").style.visibility = "hidden";
  		abilitaCompostiVegetaleCheck();
  		abilitaAnimaliCheck();
 	} */
	}

function abilitaAltriAlimentiCheck(){

	//document.getElementById("alimentinonAnimali").disabled=false;

}

function mostraCU(){

	sel2=document.details.asl_coinvolte;
	for( i = 0 ; i < sel2.length; i++)
		{
	if(sel2[i].checked==true)
	{
		 document.getElementById("cu_"+sel2[i].value).style.display="";
		 document.getElementById("int_"+sel2[i].value).style.display="";
		 if(sel2[i].value =="16")
		 {
				document.getElementById("colonnaFuoriRegione").style.display="block";
				document.getElementById("noteFuoriRegione").style.display="block";
				
		 }
			 
	}
		}
	
}

function selectLista(){


	 sel=document.details.ListaCommercializzazione.value;

if(sel=="2"){
	 sel2=document.details.asl_coinvolte;

	for( i = 0 ; i < sel2.length; i++){

sel2[i].checked=true;

		 document.getElementById("cu_"+sel2[i].value).style.display="";
		 document.getElementById("int_"+sel2[i].value).style.display="";
		 }
	
}else{

	sel2=document.details.asl_coinvolte;

	for( i = 0 ; i < sel2.length; i++){
		document.getElementById("cu_"+sel2[i].value).style.display="none";	
		 document.getElementById("int_"+sel2[i].value).style.display="none";
sel2[i].checked=false;

 
		 }
	
	
}

	
	
}

function popLookupSelectorAllerteImpreseElimina(siteid,size)
{	


var clonato = document.getElementById('row_'+siteid+'_'+size);

	clonato.parentNode.removeChild(clonato);
	
	size = document.getElementById('size_'+siteid);
	size.value=parseInt(size.value)-1;
}

function abilitaCU(siteId)
	{
	
	sel2=document.details.asl_coinvolte;

	for( i = 0 ; i < sel2.length; i++){
	
			if(sel2[i].value==siteId && sel2[i].checked){
				
				 document.getElementById("cu_"+siteId).style.display="";
				 document.getElementById("int_"+siteId).style.display="";
				 document.getElementById("orgid_"+siteId).style.display="";
				 
				 if(siteId =="16")
					 {

						document.getElementById("colonnaFuoriRegione").style.display="block";
						document.getElementById("noteFuoriRegione").style.display="block";
						
					}

					document.getElementById('select_'+siteId).style.display="";

					document.getElementById('selectstabilimenti_'+siteId).style.display="";
				break;
				}
			else{
				if(sel2[i].value==siteId){
				
				document.getElementById("cu_"+siteId).style.display="none";
				 document.getElementById("int_"+siteId).style.display="none";
				 document.getElementById("orgid_"+siteId).style.display="none";
				 
				 document.getElementById('select_'+siteId).style.display="none";
				 document.getElementById('selectstabilimenti_'+siteId).style.display="none";
				 numelementi = document.getElementById("elementi_"+siteId);
				
					 for(i=1; i <numelementi;i++)
				 {
					 document.getElementById("org_"+siteid+"_"+i).style.display="none";
				 } 
				 if(siteId =="16"){
						
						
					 document.getElementById("colonnaFuoriRegione").style.display="none";
					document.getElementById("noteFuoriRegione").style.display="none";
					
					}
					
				 break;
				}
				}

		 
		 }
	




	
}

function mostraAllegato(){

  lista=document.details.ListaCommercializzazione.value;
  

  if(lista==1){
document.getElementById("oggetto").style.display="";



sel2=document.details.asl_coinvolte;

for( i = 0 ; i < sel2.length; i++){

sel2[i].checked=false;
document.getElementById("cu_"+sel2[i].value).style.display="none";
document.getElementById("int_"+sel2[i].value).style.display="none";
}
	  }else{
		  document.getElementById("oggetto").style.display="none";

		  

		  
		  }

 
		 
		 
	
}


function abilitaLookupOrigineVegetale()
{
	/*
    alimentiOrigine = document.getElementById("alimentiOrigineVegetale");
    
    if(alimentiOrigine.checked==true)
    { 
        mostraSottoCategoria();
    	disabilitaAcque();
    	disabilitaAdditivi();
    	disabilitaBevande();
    	disabilitaMangimi();
    	disabilitaAltriAlimenti();
    	disabilitaMaterialiAlimenti();
    	document.getElementById("acquaSelect").style.display="none";
		document.getElementById("noteacqua").style.display="none";
		document.getElementById("notebevande").style.display="none";
		document.getElementById("noteAlimentiMangimi").style.display="none";
		document.getElementById("noteadditivi").style.display="none";
		document.getElementById("notematerialialimenti").style.display="none";
    	document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
    	document.details.TipoSpecie_uova.style.display="none";
    	document.details.TipoSpecie_latte.style.display="none";
    	document.details.TipoSpecie_uova.value="-1";
    	document.details.TipoSpecie_latte.value="-1";
    	document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
    	document.details.alimentiOrigineAnimaleNonTrasformatiValori.value= -1;
		document.getElementById("tipoAlimentiAnimali").style.display="none";
    	document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
    	document.forms['details'].alimentiOrigineAnimaleNonTrasformati.style.display="none";
    	document.forms['details'].alimentiOrigineAnimaleNonTrasformati.value="-1";
    	if(document.forms['details'].alimentiOrigineAnimaleTrasformati != null)
    	{
    		document.forms['details'].alimentiOrigineAnimaleTrasformati.value="-1";
    		document.forms['details'].alimentiOrigineAnimaleTrasformati.style.display="none"
    	}
    	document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
    	document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
    	//	 disabilitaDolciumi();
      	//   disabilitaGelati();
       document.getElementById("lookupVegetale").style.visibility = "visible";
       disabilitaCompostiAnimale();
       disabilitaComposti();
       if(document.details.tipoAlimento=="0")
       {
        	document.details.alimentiOrigineVegetaleValoriNonTrasformati.style.display="block";
        	document.details.alimentiOrigineVegetaleValoriTrasformati.style.display="none";

       }else
           {
	           if(document.details.tipoAlimento=="1")
		        {
                	document.details.alimentiOrigineVegetaleValoriTrasformati.style.display="block";
            		document.details.alimentiOrigineVegetaleValoriNonTrasformati.style.display="none";
            	}
           	}
    	document.getElementById("notealimenti2").style.display="block";
    }
    else
    {
    	clearAll();
    	document.details.alimentiOrigineVegetaleValoriTrasformati.style.display="none";
        document.details.alimentiOrigineVegetaleValoriNonTrasformati.style.display="none";
        document.details.alimentiOrigineVegetaleValoriTrasformati.value="-1";
        document.details.alimentiOrigineVegetaleValoriNonTrasformati.value="-1";
    	//mostraSottoCategoria();
		 document.details.fruttaFresca.style.display="none";
    	 document.details.fruttaSecca.style.display="none";
    	 document.details.ortaggi.style.display="none";
    	 document.details.funghi.style.display="none";
    	 document.details.derivati.style.display="none";
    	 document.details.conservati.style.display="none";
    	 document.details.vino.style.display="none";
    	 document.details.grassi.style.display="none";
    	 document.details.zuppe.style.display="none";
    	abilitaAcqueCheck();
        abilitaBevandeCheck();
        abilitaMangimiCheck();
        //abilitaDolciumiCheck();
        //abilitaGelatiCheck();      
        abilitaAdditivi();
        abilitaMaterialiAlimenti();	 
    	document.getElementById("lookupVegetale").style.visibility = "hidden";
		abilitaAltriAlimenti();
     	document.getElementById("notealimenti2").style.display="none";
     	abilitaAnimaliCheck();
     	abilitaCompostiCheck();
          
    }  */
}




function abilitaCompostiVegetaleCheck(){
	
	 alimentiOrigine1 = document.getElementById("alimentiOrigineVegetale");
	//alimentiOrigine1.disabled=false;
	
	
}
function abilitaCompostiCheck(){
	
	 alimentiOrigine2  = document.getElementById("alimentiComposti");
	//	alimentiOrigine2.disabled=false;
	
}

function abilitaAnimaliCheck(){

	  alimentiOrigine3 = document.getElementById("alimentiOrigineAnimale");
		alimentiOrigine3.disabled=false;
	
}
function disabilitaCompostiVegetale(){
	
	 alimentiOrigine = document.getElementById("alimentiOrigineVegetale");
	//alimentiOrigine.disabled="true";
}

function disabilitaCompostiAnimale(){
	
	 alimentiOrigine = document.getElementById("alimentiOrigineAnimale");
	//alimentiOrigine.disabled="true";
}

function disabilitaComposti(){
	
	 alimentiOrigine = document.getElementById("alimentiComposti");
	//alimentiOrigine.disabled="true";
}

function disabilitaNon_Trasformati()
{
	
 /*  if(document.forms['details'].alimentiOrigineAnimaleTrasformati != null ) {
   if(document.forms['details'].alimentiOrigineAnimaleTrasformati.value != -1)
   {
	   
     
      document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
      document.details.TipoSpecie_latte.style.display="none";
      document.details.TipoSpecie_uova.style.display="none";
      document.details.TipoSpecie_latte.value="-1";
      document.details.TipoSpecie_uova.value="-1";
      document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
   	  document.getElementById("notealimenti").style.display="block";
   
   }  
   
   }*/
	
}


function abilitaTipoAlimentoAnimale()
{

/*
 	check=document.getElementById("alimentiOrigineAnimale");

	if(check.checked==true)
	{
		disabilitaAcque();
		disabilitaAdditivi();
		disabilitaBevande();
		disabilitaMangimi();
		disabilitaAltriAlimenti();
		disabilitaMaterialiAlimenti();
	 	document.getElementById("acquaSelect").style.display="none";
	    document.getElementById("noteacqua").style.display="none";
	    document.getElementById("notebevande").style.display="none";
	    document.getElementById("noteAlimentiMangimi").style.display="none";
	    document.getElementById("noteadditivi").style.display="none";
	    document.getElementById("notematerialialimenti").style.display="none";
		document.getElementById("tipoAlimentiAnimali").style.display="block";
		disabilitaCompostiVegetale();
		//disabilitaDolciumi();
    	//disabilitaGelati();
    	disabilitaComposti();
	
	}
	else
	{
		clearAll();
		document.getElementById("tipoAlimentiAnimali").style.display="none";
		document.getElementById("tipoAlimentiAnimali").value="-1";
		abilitaCompostiVegetaleCheck();
		abilitaAdditivi();
    	abilitaBevandeCheck();
    	abilitaMangimiCheck();
    	abilitaAcqueCheck();
    	abilitaMaterialiAlimenti();	
		abilitaLookupOrigineVegetale();
		//abilitaDolciumiCheck();
		//abilitaGelatiCheck();
		document.getElementById("notealimenti").style.display="none";
		document.getElementById("notealimenti").value="";
	  	abilitaCompostiCheck();
	  	document.forms['details'].TipoSpecie_uova.style.display="none";
	  	document.forms['details'].TipoSpecie_uova.value="-1";
	  	document.forms['details'].TipoSpecie_latte.value="-1";
	  	document.forms['details'].TipoSpecie_latte.style.display="none";
	  	document.forms['details'].alimentiOrigineAnimaleNonTrasformati.style.display="none";
	  	document.forms['details'].alimentiOrigineAnimaleTrasformati.style.display="none"
		document.forms['details'].alimentiOrigineAnimaleTrasformati.value="-1";
		document.forms['details'].alimentiOrigineAnimaleNonTrasformati.value="-1"
		document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
		document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display = "none";
	

	}*/
	
}


function mostraFollowUP()
{
	
	if(document.details.EsitoCampione.value==1)
	{
		document.getElementById("followup").style.display="block";
	
	}
	else
	{
		document.getElementById("followup").style.display="none";
	
	}
}





function abilitaCheckSegnalazione()
{

   allerta = document.getElementById("allerta");
   nonConformita = document.getElementById("nonConformita");
   if(nonConformita.checked==true)
   { 
	   allerta.checked = false;
   }
}


function abilitaCheckAllerta()
{
	
   allerta = document.getElementById("allerta");
   nonConformita = document.getElementById("nonConformita");
   if(allerta.checked==true)
    { 
	    nonConformita.checked = false;
    }
}




function abilitaLista_tipoAnalisi(){
	
/*
flag=0;
	if(document.forms['details'].TipoCampione!=null){
		flag=1;
	 tipo=document.forms['details'].TipoCampione.value;

	if(tipo==1){
		//document.getElementById("nascosto1").style.display="block";
		document.forms['details'].TipoCampione_batteri.style.display="block";
		document.forms['details'].TipoCampione_virus.style.display="none";
		document.forms['details'].TipoCampione_parassiti.style.display="none";
		document.forms['details'].TipoCampione_chimico.style.display="none";
		document.forms['details'].TipoCampione_fisico.style.display="none";
		disabilitaTipochimico();
		//disabilitaTipochimico();
return 0;

		}
	if(tipo==2){
		//document.getElementById("nascosto1").style.display="block";
		document.forms['details'].TipoCampione_batteri.style.display="none";
		document.forms['details'].TipoCampione_virus.style.display="block";
		document.forms['details'].TipoCampione_parassiti.style.display="none";
		document.forms['details'].TipoCampione_chimico.style.display="none";
		document.forms['details'].TipoCampione_fisico.style.display="none";
		disabilitaTipochimico();
		return 0;


		}

	if(tipo==4){
	//	document.getElementById("nascosto1").style.display="block";
		document.forms['details'].TipoCampione_batteri.style.display="none";
		document.forms['details'].TipoCampione_virus.style.display="none";
		document.forms['details'].TipoCampione_parassiti.style.display="block";
		document.forms['details'].TipoCampione_chimico.style.display="none";
		document.forms['details'].TipoCampione_fisico.style.display="none";
		disabilitaTipochimico();
return 0;

		}


	if(tipo==8){
		//	document.getElementById("nascosto1").style.display="block";
			document.forms['details'].TipoCampione_batteri.style.display="none";
			document.forms['details'].TipoCampione_virus.style.display="none";
			document.forms['details'].TipoCampione_parassiti.style.display="none";
			document.forms['details'].TipoCampione_chimico.style.display="none";

			document.forms['details'].TipoCampione_fisico.style.display="block";
			
			
			disabilitaTipochimico();
	return 0;

			}

	if(tipo==5){
		document.forms['details'].TipoCampione_fisico.style.display="none";
	//	document.getElementById("nascosto1").style.display="block";
		document.forms['details'].TipoCampione_batteri.style.display="none";
		document.forms['details'].TipoCampione_virus.style.display="none";
		document.forms['details'].TipoCampione_parassiti.style.display="none";
		document.forms['details'].TipoCampione_chimico.style.display="block";
return 0;

		}

	//document.getElementById("nascosto1").style.display="none";
	disabilitaTipochimico();
		document.forms['details'].TipoCampione_batteri.style.display="none";
		document.forms['details'].TipoCampione_virus.style.display="none";
		document.forms['details'].TipoCampione_parassiti.style.display="none";
		document.forms['details'].TipoCampione_chimico.style.display="none";
		document.forms['details'].TipoCampione_fisico.style.display="none";
		return 0;

	}*/
	
}








function disabilitaTipochimico(){
	
	/*document.forms['details'].TipoCampione_sottochimico.style.display="none";
	document.forms['details'].TipoCampione_sottochimico2.style.display="none";
	document.forms['details'].TipoCampione_sottochimico3.style.display="none";
	document.forms['details'].TipoCampione_sottochimico4.style.display="none";
	document.forms['details'].TipoCampione_sottochimico5.style.display="none";
	*/
}



function abilitaLista_tipoChimico(){
	
	/* tipo=document.forms['details'].TipoCampione_chimico.value;

	 
	if(tipo==1)
	{
		document.forms['details'].TipoCampione_sottochimico.style.display="block";
		document.forms['details'].TipoCampione_sottochimico2.style.display="none";
		document.forms['details'].TipoCampione_sottochimico3.style.display="none";
		document.forms['details'].TipoCampione_sottochimico4.style.display="none";
		document.forms['details'].TipoCampione_sottochimico5.style.display="none";
	
		return;

	}
	if(tipo==2)
	{
		document.forms['details'].TipoCampione_sottochimico.style.display="none";
		document.forms['details'].TipoCampione_sottochimico2.style.display="block";
		document.forms['details'].TipoCampione_sottochimico3.style.display="none";
		document.forms['details'].TipoCampione_sottochimico4.style.display="none";
		document.forms['details'].TipoCampione_sottochimico5.style.display="none";
		return;
	}

	if(tipo==3)
	{
		document.forms['details'].TipoCampione_sottochimico.style.display="none";
		document.forms['details'].TipoCampione_sottochimico2.style.display="none";
		document.forms['details'].TipoCampione_sottochimico3.style.display="block";
		document.forms['details'].TipoCampione_sottochimico4.style.display="none";
		document.forms['details'].TipoCampione_sottochimico5.style.display="none";
	
		return;

	}

	if(tipo==4)
	{
		document.forms['details'].TipoCampione_sottochimico.style.display="none";
		document.forms['details'].TipoCampione_sottochimico2.style.display="none";
		document.forms['details'].TipoCampione_sottochimico3.style.display="none";
		document.forms['details'].TipoCampione_sottochimico4.style.display="block";
		document.forms['details'].TipoCampione_sottochimico5.style.display="none";
		return;

	}
	if(tipo==5)
	{
		document.forms['details'].TipoCampione_sottochimico.style.display="none";
		document.forms['details'].TipoCampione_sottochimico2.style.display="none";
		document.forms['details'].TipoCampione_sottochimico3.style.display="none";
		document.forms['details'].TipoCampione_sottochimico4.style.display="none";
		document.forms['details'].TipoCampione_sottochimico5.style.display="block";
		return;
	}

		document.forms['details'].TipoCampione_sottochimico.style.display="none";
		document.forms['details'].TipoCampione_sottochimico2.style.display="none";
		document.forms['details'].TipoCampione_sottochimico3.style.display="none";
		document.forms['details'].TipoCampione_sottochimico4.style.display="none";
		document.forms['details'].TipoCampione_sottochimico5.style.display="none";
	
*/
}



function resetNumericFieldValue(fieldId){
  document.getElementById(fieldId).value = -1;
}
function trim(stringa){
	
	if(stringa!='')
	{
    while (stringa.substring(0,1) == ' '){
        stringa = stringa.substring(1, stringa.length);
    }
    while (stringa.substring(stringa.length-1, stringa.length) == ' '){
        stringa = stringa.substring(0,stringa.length-1);
    }
    while (stringa.substring(stringa.length-1, stringa.length) == '\n'){
        stringa = stringa.substring(0,stringa.length-1);
    }
	}
    return stringa;
}

function controlloNumber(campo)
{	

	if(campo!=null && campo.value!='' )
	{
	
	 if (isNaN(campo.value)){
			
			return false ;
		}
	}
		return true ;
}

function controllo_data(stringa){
    var espressione = /^[0-9]{2}\/[0-9]{2}\/[0-9]{4}$/;
    if (!espressione.test(stringa))
    {
        return false;
    }else{
        anno = parseInt(stringa.substr(6),10);
        mese = parseInt(stringa.substr(3, 2),10);
        giorno = parseInt(stringa.substr(0, 2),10);
        
        var data=new Date(anno, mese-1, giorno);
        if(data.getFullYear()==anno && data.getMonth()+1==mese && data.getDate()==giorno){
            return true;
        }else{
            return false;
        }
    }
}



function confronta_data(data1, data2){
	// controllo validità formato data
    if(controllo_data(data1) &&controllo_data(data2)){
		//trasformo le date nel formato aaaammgg (es. 20081103)
        data1str = data1.substr(6)+data1.substr(3, 2)+data1.substr(0, 2);
		data2str = data2.substr(6)+data2.substr(3, 2)+data2.substr(0, 2);
		//controllo se la seconda data è successiva alla prima
        if (data2str-data1str<0) {
           return false ;
        }else{
			return true ;
        }
    }
}



function checkForm(form) {
  formTest = true;
  message = "";

  if (form.flag_pubblicazione_allerte.checked==true)
  {
  	 
       
  	if (form.data_inizio_pubblicazione_allerte.value == "") {
          message += label("check.campioni.richiedente.selezionatosssd","- Controllare che \"data inizio pubblicazione\" sia stato popolato\r\n");
          formTest = false;
        }
      
  	if (form.data_fine_pubblicazione_allerte.value == "") {
          message += label("check.campioni.richiedente.selezionatosssd","- Controllare che \"data fine pubblicazione\" sia stato popolato\r\n");
          formTest = false;
        }

  	if (form.tipo_rischio_allerte.value == "") {
          message += label("check.campioni.richiedente.selezionatosssd","- Controllare che \"tipo rischio\" sia stato popolato\r\n");
          formTest = false;
        }

  	if (form.provvedimenti_esito_allerte.value == "") {
          message += label("check.campioni.richiedente.selezionatosssd","- Controllare che \"provvedimenti\esito\" sia stato popolato\r\n");
          formTest = false;
        }
        
        if (form.data_inizio_pubblicazione_allerte.value != "" && form.data_fine_pubblicazione_allerte.value != "")
        {
        		if (confronta_data(form.data_inizio_pubblicazione_allerte.value, form.data_fine_pubblicazione_allerte.value)==false)
        		{
        			 message += label("check.campioni.richiedente.selezionatosssd","- Controllare che \"data inizio pubblicazione\" sia precedente alla data di fine pubblicazione\r\n");
          		formTest = false;
        		}
        }
  }
  
//   for (i=1; i<=14;i++)
// 	{
// 		if (controlloNumber(document.getElementById("cucampo_"+i))==false)
// 		{
// 			formTest = false ;
// 			message += label("check..richiedente.selezionato","- Il Numero cu deve essere un valore numerico.\r\n");
// 			break;
// 		}
// 	}

  alimentiNonAnimali=document.getElementById("alimentinonAnimali");


	alimentiOrigineAnimale= document.getElementById("alimentiOrigineAnimale");
	mangimi= document.getElementById("mangimi");
	alimentiOrigineVegetale= document.getElementById("alimentiOrigineVegetale");
	alimentiComposti= document.getElementById("alimentiComposti");
	acqua= document.getElementById("acqua");

	bevande= document.getElementById("bevande");
	mangimi= document.getElementById("mangimi");
	additivi= document.getElementById("additivi");
	materialialimenti= document.getElementById("materialialimenti");

	if(alimentiOrigineAnimale != null && alimentiOrigineAnimale.checked==false && alimentiOrigineVegetale.checked==false && alimentiComposti.checked==false && acqua.checked==false && bevande.checked==false && additivi.checked==false && mangimi.checked==false && materialialimenti.checked==false && alimentiNonAnimali.checked==false ){
		message +=label("TESTO1","- Controllare di aver selezionato un tipo di alimento \n"); formTest = false;
	}else{

		if(alimentiOrigineVegetale != null && alimentiOrigineVegetale.checked==true){

			if(form.tipoAlimento.value == "-1" ){ // nessun valore scelto per Alimenti di Origine Vegetale
				message +=label("TESTO1","- Controllare che il campo \"Alimenti di Origine Vegetale\" sia stato selezionato \n"); formTest = false;
			}

			else if (form.tipoAlimento.value == "0"){ // Alimenti di Origine Vegetale Non Trasformati

				if(form.alimentiOrigineVegetaleValoriNonTrasformati.value == "-1"){ // nessun valore scelto per Alimenti di Origine Vegetale Non Trasformati
					message +=label("TESTO1","- Controllare che il campo \"Alimenti di Origine Vegetale\" sia stato selezionato \n"); formTest = false;
				}
				else if(form.alimentiOrigineVegetaleValoriNonTrasformati.value == "1"){ // frutta fresca
					if(form.fruttaFresca.value == "-1"){ // nessun valore scelto per frutta fresca
						message +=label("TESTO1","- Controllare che il campo \"Alimenti di Origine Vegetale\" sia stato selezionato \n"); formTest = false;
					}
				}
				else if(form.alimentiOrigineVegetaleValoriNonTrasformati.value == "4"){ // ortaggi e verdure
					if(form.ortaggi.value == "-1"){ // nessun valore scelto per ortaggi e verdure
						message +=label("TESTO1","- Controllare che il campo \"Alimenti di Origine Vegetale\" sia stato selezionato \n"); formTest = false;
					}
				}
				else if(form.alimentiOrigineVegetaleValoriNonTrasformati.value == "5"){ // funghi
					if(form.funghi.value == "-1"){ // nessun valore scelto per funghi
						message +=label("TESTO1","- Controllare che il campo \"Alimenti di Origine Vegetale\" sia stato selezionato \n"); formTest = false;
					}
				}
				else if(form.alimentiOrigineVegetaleValoriNonTrasformati.value == "6"){ // conserve e surgelati
					if(form.conservati.value == "-1"){ // nessun valore scelto per conserve e surgelati
						message +=label("TESTO1","- Controllare che il campo \"Alimenti di Origine Vegetale\" sia stato selezionato \n"); formTest = false;
					}
				}

			}

			else if (form.tipoAlimento.value == "1"){ // Alimenti di Origine Vegetale Trasformati

				if(form.alimentiOrigineVegetaleValoriTrasformati.value == "-1"){ // nessun valore scelto per Alimenti di Origine Vegetale Trasformati
					message +=label("TESTO1","- Controllare che il campo \"Alimenti di Origine Vegetale\" sia stato selezionato \n"); formTest = false;
				}
				else if(form.alimentiOrigineVegetaleValoriTrasformati.value == "2"){ // frutta secca
					if(form.fruttaSecca.value == "-1"){ // nessun valore scelto per frutta secca
						message +=label("TESTO1","- Controllare che il campo \"Alimenti di Origine Vegetale\" sia stato selezionato \n"); formTest = false;
					}
				}
				else if(form.alimentiOrigineVegetaleValoriTrasformati.value == "7"){ // cereali e prodotti della panetteria
					if(form.derivati.value == "-1"){ // nessun valore scelto per cereali e prodotti della panetteria
						message +=label("TESTO1","- Controllare che il campo \"Alimenti di Origine Vegetale\" sia stato selezionato \n"); formTest = false;
					}
				}
				else if(form.alimentiOrigineVegetaleValoriTrasformati.value == "11"){ // zuppe
					if(form.zuppe.value == "-1"){ // nessun valore scelto per zuppe
						message +=label("TESTO1","- Controllare che il campo \"Alimenti di Origine Vegetale\" sia stato selezionato \n"); formTest = false;
					}
				}

			}

		}

		else if(alimentiNonAnimali != null && alimentiNonAnimali.checked==true){

			if (form.altrialimenti.value == "-1"){ // nessun valore scelto per Alimenti Non Animali
				message +=label("TESTO1","- Controllare che il campo \"Altri Alimenti di origine non animale\" sia stato selezionato \n"); formTest = false;
			}

		}

		else{

			if( alimentiComposti != null &&  alimentiComposti.checked==true){

				if(form.testoAlimentoComposto.value==""){
					message +=label("TESTO1","- Controllare che il campo di testo per \"Alimenti Composti\" sia stato popolato \n"); formTest = false;	
				}



			}else{

				if(acqua != null && acqua.checked==true){

					if(form.acque.value == "-1"){
						message +=label("TESTO1","- Controllare che il campo \"Acqua\" sia stato popolato \n"); formTest = false;
					}
					//message +=label("TESTO1","- Controllare che il campo \"Acqua\" sia stato popolato \n"); formTest = false;


				}else if(mangimi != null && mangimi.checked==true){
					if(form.lookupSpecieAlimento.value == "-1" || form.lookupTipologiaAlimento.value == "-1"){
						message +=label("TESTO1","- Controllare che il campo \"Alimenti per uso Zootecnico\" sia stato popolato \n"); formTest = false;
					}
				}

				else{

					if(additivi != null && additivi.checked==true){


//						message +=label("TESTO1","- Controllare che il campo di testo per \"Additivi\" sia stato popolato \n"); formTest = false;


					}else{

						if(materialialimenti != null && materialialimenti.checked==true){

							//								message +=label("TESTO1","- Controllare che il campo di testo per \"Materiali a Contatto con Alimenti\" sia stato popolato \n"); formTest = false;
						}
					}
				}
			}
		}
	}
	
// 	 if (form.tipo_notifica_allerta.value == "-1") {
//          message += label("check..data_richiesta.selezionato","- Controllare che il campo \"Tipo notifica\" sia stato popolato\r\n");
//          formTest = false;
//        }
     if (form.numero_notifica_allerta.value == "") {
         message += label("check..data_richiesta.selezionato","- Controllare che il campo \"Numero notifica\" sia stato popolato\r\n");
         formTest = false;
       }
     
  if (form.dataApertura.value == "") {
      message += label("check..data_richiesta.selezionato","- Controllare che il campo \"Data Apertura\" sia stato popolato\r\n");
      formTest = false;
    }
  if (form.denominazione_prodotto.value == "") {
      message += label("check.campioni.richiedente.selezionatosssd","- Controllare che \"Denominazione Prodotto\" sia stato popolato\r\n");
      formTest = false;
    }
	if (form.numero_lotto.value == "") {
      message += label("check.campioni.richiedente.selezionatosssd","- Controllare che \"Numero Lotto\" sia stato popolato\r\n");
      formTest = false;
    }
	if (form.fabbricante_produttore.value == "") {
      message += label("check.campioni.richiedente.selezionatosssd","- Controllare che \"Fabbricante o produttore\" sia stato popolato\r\n");
      formTest = false;
    }
    if(trim(form.motivo_ripianificazione_modifica.value)=="")
    {
    	message += label("check.campioni.richiedente.selezionatosssd","- Controllare che \"Motivo Ripianificazione/ambiamento \" sia stato popolato\r\n");
        formTest = false;
    }


  
  /* if (form.idAllerta.value == "") {
      message += label("check.campioni.richiedente.selezionatosssd","- Controllare che \"Identificativo Allerta\" sia stato popolato\r\n");
      formTest = false;
    }*/
  <dhv:include name="ticket.contact" none="true">
  if (form.Origine.value == "-1") {
    message += label("check.campioni.richiedente.selezionatosss","- Controllare che \"Origine\" sia stato popolato\r\n");
    formTest = false;
  }
  </dhv:include>

    if (formTest == false) {
    alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
    return false;
  } else {
    return true;
  }

  
}

function setAssignedDate(){
  resetAssignedDate();
  if (document.forms['details'].assignedTo.value > 0){
    document.forms['details'].assignedDate.value = document.forms['details'].currentDate.value;
  }
}

function resetAssignedDate(){
  document.forms['details'].assignedDate.value = '';
}  

function setField(formField,thisValue,thisForm) {
  var frm = document.forms[thisForm];
  var len = document.forms[thisForm].elements.length;
  var i=0;
  for( i=0 ; i<len ; i++) {
    if (frm.elements[i].name.indexOf(formField)!=-1) {
      if(thisValue){
        frm.elements[i].value = "1";
      } else {
        frm.elements[i].value = "0";
      }
    }
  }
}

function selectUserGroups() {
  var siteId = document.forms['details'].orgSiteId.value;
  if ('<%= OrgDetails.getOrgId() %>' != '-1') {
    popUserGroupsListSingle('userGroupId','changeUserGroup', '&userId=<%= User.getUserRecord().getId() %>&siteId='+siteId);
  } else {
    alert(label("select.account.first",'You have to select an Account first'));
    return;
  }
}

function popKbEntries() {
  var siteId = document.forms['details'].orgSiteId.value;
  var form = document.forms['details'];
  var catCode = form.elements['catCode'];
  var catCodeValue = catCode.options[catCode.selectedIndex].value;
  if (catCodeValue == '0') {
    alert(label('','Please select a category first'));
    return;
  }
  var subCat1 = form.elements['subCat1'];
  var subCat1Value = subCat1.options[subCat1.options.selectedIndex].value;
<dhv:include name="ticket.subCat2" none="true">
  var subCat2 = form.elements['subCat2'];
  var subCat2Value = subCat2.options[subCat2.options.selectedIndex].value;
</dhv:include>
<dhv:include name="ticket.subCat2" none="true">
  var subCat3 = form.elements['subCat3'];
  var subCat3Value = subCat3.options[subCat3.options.selectedIndex].value;
</dhv:include>
  var url = 'KnowledgeBaseManager.do?command=Search&popup=true&searchcodeSiteId='+siteId+'&searchcodeCatCode='+catCodeValue;
  url = url + '&searchcodeSubCat1='+ subCat1Value;
<dhv:include name="ticket.subCat2" none="true">
  url = url + '&searchcodeSubCat2='+ subCat2Value;
</dhv:include>
<dhv:include name="ticket.subCat2" none="true">
  url = url + '&searchcodeSubCat3='+ subCat3Value;
</dhv:include>
  popURL(url, 'KnowledgeBase','600','550','yes','yes');
}

function selectCarattere(str, n, m, x){
  
 		elm1 = document.getElementById("dat"+n);
 		elm2 = document.getElementById("dat"+m);
 		
 		
 		if(str == "Provvedimenti"){
 			car = document.details.Provvedimenti.value;
 		}
 		if(str == "SanzioniAmministrative"){
 			car = document.details.SanzioniAmministrative.value;
 		}
 		if(str == "SanzioniPenali"){
 			car = document.details.SanzioniPenali.value;
 		}
 		
 		if(car == 9 || (car == 6 && str == "SanzioniPenali")){
 			elm1.style.visibility = "visible";
 			elm2.style.visibility = "visible";
 			
 		}
 		else {
 			elm1.style.visibility = "hidden";
 			elm2.style.visibility = "hidden";
 			if(x == 1){
 			document.forms['details'].descrizione1.value="";
 			}
 			if(x == 2){
 			document.forms['details'].descrizione2.value="";
 			}
 			if(x == 3){
 			document.forms['details'].descrizione3.value="";
 			}
 		}
 	  }
</script>
  
  	<%=showError(request, "numero_notifica_allertaError") %>
	
	<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
      <th colspan="2">
        <strong><dhv:label name="sanzioni.informationff">Scheda Allerta</dhv:label></strong>
      </th>
	</tr>
      <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="">Identificativo Allerta</dhv:label>
    </td>
    <td>
         <input type="text" readonly="readonly" name="idAllerta"  id="idAllerta" size="10" maxlength="50" value = "<%= TicketDetails.getIdAllerta() %>"/><font color="red">*</font> <%= showAttribute(request, "assignedDateError") %>
    </td> 
    </tr>
    
    <%
     LookupList batteriDefautItem=new LookupList();
 // setto i valori de defauls èper le liste multiple
    HashMap<Integer,String> ListaBpi6= TicketDetails.getTipiAlimentiVegetali();
    Iterator<Integer>iteraKiavi6=ListaBpi6.keySet().iterator();
    LookupList multipleSelects6=new LookupList();
    //multipleSelects6=new LookupList();
	LookupList multipleSelects7=new LookupList();
	LookupList multipleSelects8=new LookupList();
	LookupList multipleSelects9=new LookupList();
	LookupList multipleSelects10=new LookupList();
	LookupList virusDefaultItem=new LookupList();
	LookupList parassitiDefaultItem=new LookupList();
	LookupList fisicoDefaultItem=new LookupList();
	LookupList multipleSelects5=new LookupList();
	
	
						if(TicketDetails.getTipoCampione()==5){
							
						
							HashMap<Integer, String> ListaBpi5=TicketDetails.getTipiCampioni();
							Iterator<Integer> iteraKiavi5= TicketDetails.getTipiCampioni().keySet().iterator();
							while(iteraKiavi5.hasNext()){
								int kiave=iteraKiavi5.next();
								String valore=ListaBpi5.get(kiave);
								multipleSelects5.addItem(kiave,valore);


								 ListaBpi6=TicketDetails.getTipiChimiciSelezionati();
								 iteraKiavi6= TicketDetails.getTipiChimiciSelezionati().keySet().iterator();
								

								if(kiave==1){
									while(iteraKiavi6.hasNext()){
										int kiave1=iteraKiavi6.next();
										String valore1=ListaBpi6.get(kiave1);
										multipleSelects6.addItem(kiave1,valore1);
									}

								}
								else{

									if(kiave==2){
										while(iteraKiavi6.hasNext()){
											int kiave1=iteraKiavi6.next();
											String valore1=ListaBpi6.get(kiave1);
											multipleSelects7.addItem(kiave1,valore1);
										}

									}
									else{

										if(kiave==3){
											while(iteraKiavi6.hasNext()){
												int kiave1=iteraKiavi6.next();
												String valore1=ListaBpi6.get(kiave1);
												multipleSelects8.addItem(kiave1,valore1);
											}

										}
										else{
											if(kiave==4){
												while(iteraKiavi6.hasNext()){
													int kiave1=iteraKiavi6.next();
													String valore1=ListaBpi6.get(kiave1);
													multipleSelects9.addItem(kiave1,valore1);
												}

											}
											else{

												if(kiave==5){
													while(iteraKiavi6.hasNext()){
														int kiave1=iteraKiavi6.next();
														String valore1=ListaBpi6.get(kiave1);
														multipleSelects10.addItem(kiave1,valore1);
													}

												}


											}

										}
									}

								}


							}
						

						}else{
							
							if(TicketDetails.getTipoCampione()==1){
								
								HashMap<Integer, String> ListaBpi=TicketDetails.getTipiCampioni();
								Iterator<Integer> iteraKiavi= TicketDetails.getTipiCampioni().keySet().iterator();
								while(iteraKiavi.hasNext()){
									int kiave=iteraKiavi.next();
									String valore=ListaBpi.get(kiave);
									batteriDefautItem.addItem(kiave,valore);

								}

								
								
							}else{
								
								if(TicketDetails.getTipoCampione()==2){
									
									HashMap<Integer, String> ListaBpi2=TicketDetails.getTipiCampioni();
									Iterator<Integer> iteraKiavi2= TicketDetails.getTipiCampioni().keySet().iterator();
									while(iteraKiavi2.hasNext()){
										int kiave=iteraKiavi2.next();
										String valore=ListaBpi2.get(kiave);
										virusDefaultItem.addItem(kiave,valore);

									}

									

								}
								else{
									if(TicketDetails.getTipoCampione()==4){
										
										HashMap<Integer, String> ListaBpi3=TicketDetails.getTipiCampioni();
										Iterator<Integer> iteraKiavi3= TicketDetails.getTipiCampioni().keySet().iterator();
										while(iteraKiavi3.hasNext()){
											int kiave=iteraKiavi3.next();
											String valore=ListaBpi3.get(kiave);
											parassitiDefaultItem.addItem(kiave,valore);

										}

										
									}else{
										
										if(TicketDetails.getTipoCampione()==8){
											
											HashMap<Integer, String> ListaBpi3=TicketDetails.getTipiCampioni();
											Iterator<Integer> iteraKiavi3= TicketDetails.getTipiCampioni().keySet().iterator();
											while(iteraKiavi3.hasNext()){
												int kiave=iteraKiavi3.next();
												String valore=ListaBpi3.get(kiave);
												fisicoDefaultItem.addItem(kiave,valore);

											}

											
										}
										
										
									}
								
								
								
							}
							
							
							
						}
						}
						
						%>
 
    
     <% ArrayList<Analita> tipi_a= TicketDetails.getTipi_Campioni(); 
    	if(tipi_a.isEmpty()) { %>
   	 <tr class="containerBody">
      <td  id="nonConf" nowrap class="formLabel">
        <dhv:label name="">Azione Non Conforme Per </dhv:label>
        
      </td>
     <td>
    <table class="noborder">
    <tr>
  
    <td>
     <%
     TipoCampione_chimico.setJsEvent("onChange=abilitaLista_tipoChimico()");
      TipoCampione.setJsEvent("onChange=abilitaLista_tipoAnalisi()");
      
      %>
      <%= TipoCampione.getHtmlSelect("TipoCampione",TicketDetails.getTipoCampione()) %><font color="red">*</font> <%= showAttribute(request, "assignedDateError") %>
 </td>

 <td>
  <% 
 
 TipoCampione_batteri.setSelectStyle("display:none");
 TipoCampione_virus.setSelectStyle("display:none");
 TipoCampione_parassiti.setSelectStyle("display:none");
 TipoCampione_fisico.setSelectStyle("display:none");
 TipoCampione_chimico.setSelectStyle("display:none");
  TipoCampione_sottochimico.setSelectStyle("display:none");
  TipoCampione_sottochimico2.setSelectStyle("display:none");
  TipoCampione_sottochimico3.setSelectStyle("display:none");
  TipoCampione_sottochimico4.setSelectStyle("display:none");
  TipoCampione_sottochimico5.setSelectStyle("display:none");
 
 %>
 </td>
 <td>
 
 <%= TipoCampione_batteri.getHtmlSelect("TipoCampione_batteri",batteriDefautItem) %> <%= showAttribute(request, "assignedDateError") %>
 <%= TipoCampione_virus.getHtmlSelect("TipoCampione_virus",virusDefaultItem) %> <%= showAttribute(request, "assignedDateError") %>
   <%= TipoCampione_parassiti.getHtmlSelect("TipoCampione_parassiti",parassitiDefaultItem) %><%= showAttribute(request, "assignedDateError") %>
   <%= TipoCampione_chimico.getHtmlSelect("TipoCampione_chimico",multipleSelects5) %> <%= showAttribute(request, "assignedDateError") %>
     <%= TipoCampione_fisico.getHtmlSelect("TipoCampione_fisico",fisicoDefaultItem) %> <%= showAttribute(request, "assignedDateError") %>
 </td>
 
 <td>
  
 
 
 <%= TipoCampione_sottochimico.getHtmlSelect("TipoCampione_sottochimico",multipleSelects6) %>
  <%= TipoCampione_sottochimico2.getHtmlSelect("TipoCampione_sottochimico2",multipleSelects7) %>
   <%= TipoCampione_sottochimico3.getHtmlSelect("TipoCampione_sottochimico3",multipleSelects8) %>
    <%= TipoCampione_sottochimico4.getHtmlSelect("TipoCampione_sottochimico4",multipleSelects9) %>
     <%= TipoCampione_sottochimico5.getHtmlSelect("TipoCampione_sottochimico5",multipleSelects10) %>
 
  

 </td>
 

<td id="noteAnalisi">
    <center>Descrizione:</center>
     <textarea rows="8" cols="40" name="noteAnalisi" > <%=TicketDetails.getNoteAnalisi() %> </textarea>
     
</td>

 
 </tr>
 
 </table>
 
 </td>

  </tr>
  
   <% } else { %>
 <%@ include file="/allerte/analiti_tree_modify.jsp" %>
 <% } %>
  
  
   <tr>
      <td valign="top" class="formLabel">Allerta Comunitaria</td>
      <td>  
      <input type = "checkbox" id = "flag_tipo_allerta" name = "flag_tipo_allerta" <%if(TicketDetails.isFlagTipoAllerta()==true){%>checked="checked"<%} %>/>
      </td>      
      </tr>
      
      
     <tr>
    <td valign="top" class="formLabel">
      <dhv:label name="">Unità di Misura per Quantita</dhv:label>
    </td>
    <td>
         <%=UnitaMisura.getHtmlSelect("unitaMisura",TicketDetails.getUnitaMisura()) %>
    </td> 
    </tr>
    
      
 <tr>
      <td name="orig"  nowrap class="formLabel">
        <dhv:label name="">Notifica</dhv:label>
      </td>
         <td>  
    
      <%= tipoNotifica.getHtmlSelect("tipo_notifica_allerta", TicketDetails.getTipo_notifica_allerta()) %>
      <b>Num. notifica </b><input type="text" id="numero_notifica_allerta" name="numero_notifica_allerta" value="<%=(TicketDetails.getNumero_notifica_allerta()!=null) ? TicketDetails.getNumero_notifica_allerta() : ""%>"/> <font color="red">*</font>  
	
	</td>
 </tr>
 
 
	<tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="sanzioni.data_richiestasss">Data Apertura</dhv:label>
      </td>
      <td>
      <input readonly type="text" id="dataApertura" name="dataApertura" size="10" value="<%= toDateString(TicketDetails.getDataApertura()) %>"/>
		<a href="#" onClick="cal19.select(document.forms[0].dataApertura,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
        <font color="red">*</font> <%= showAttribute(request, "assignedDateError") %>
      </td>
    </tr>
    
   
  
  
  
 <tr class="containerBody">
      <td name="orig"  nowrap class="formLabel">
        <dhv:label name="">Origine</dhv:label>
      </td>
    <td>
      
      <table class="noborder">
      
      <tr>
      <td>  
    <%Origine.setJsEvent("onChange=mostraorigineAllerta()"); %>
      <%= Origine.getHtmlSelect("Origine",TicketDetails.getOrigine()) %>
         
         
          <font color="red">*</font> <%= showAttribute(request, "assignedDateError") %>
          </td>
          <td>
          <%
          SiteIdList.setSelectStyle("display:none");
          Regioni.setSelectStyle("display:none");
          %>
          <%= SiteIdList.getHtmlSelect("aslOrigine",TicketDetails.getOrigineAllerta()) %>
           <%= Regioni.getHtmlSelect("regioneOrigine",TicketDetails.getOrigineAllerta()) %>
          
          </td>
          
      </tr>
      
      </table>
      
    
       
	</td>
 </tr>
   
    <tr>
      <td name="orig"  nowrap class="formLabel">
        <dhv:label name="">Pubblicazione</dhv:label>
      </td>
    <td>
      
      <table class="noborder">
      
      <tr>
      <td>  
      <input type = "checkbox" id = "flag_pubblicazione_allerte" name = "flag_pubblicazione_allerte" <%if(TicketDetails.isFlag_produzione()==true){%>checked="checked"<%} %> onclick="abilitapubblicazione(this)"/>
      </td>
          
      </tr>
      <tr>
      <td>  
     <table id = "pubblicazione" style="display: none">
     
      <tr>
     <td>Data Inizio pubblicazione</td>
       <td>
       
       
             <input readonly type="text" id="data_inizio_pubblicazione_allerte" name="data_inizio_pubblicazione_allerte" value = "<%=toDateString(TicketDetails.getData_inizio_pubblicazione()) %>" size="10"/>
		<a href="#" onClick="cal19.select(document.forms[0].data_inizio_pubblicazione_allerte,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
       
       
         
      <font color="red">*</font>
       </td>
     </tr>
     
     <tr>
     <td>Data Fine pubblicazione</td>
       <td>
       
              
             <input readonly type="text" id="data_fine_pubblicazione_allerte" name="data_fine_pubblicazione_allerte" value = "<%=toDateString(TicketDetails.getData_fine_pubblicazione()) %>" size="10"/>
		<a href="#" onClick="cal19.select(document.forms[0].data_fine_pubblicazione_allerte,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
       
      <font color="red">*</font>
       </td>
     </tr>
     
      <tr>
     <td>Tipo di Rischio</td>
       <td>
       <textarea rows="5" cols="30" name = "tipo_rischio_allerte"><%=toHtml2(TicketDetails.getTipo_rischio()) %></textarea>
       <font color="red">*</font>
       </td>
     </tr>
     
      <tr>
     <td>Provvedimenti/Esito Accertamenti</td>
       <td>
       <textarea rows="5" cols="30" name = "provvedimenti_esito_allerte"><%=toHtml2(TicketDetails.getProvvedimento_esito()) %></textarea>
       <font color="red">*</font>
       </td>
     </tr>
     
     </table>
      </td>
          
      </tr>
      
      
      </table>
      
    
       
	</td>
 </tr>
 
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzioni.note">Note</dhv:label>
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <textarea name="problem" cols="55" rows="8"><%= toString(TicketDetails.getProblem()) %></textarea>
          </td>
          <td valign="top">
            <%= showAttribute(request, "problemError") %>
          </td>
        </tr>
      </table>
    </td>
	</tr>


	</table>
	<br>
	
<div <%if (request.getAttribute("ripianifica")!=null){ %>style="display: none"<%} %>>	
	 <table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
      <th colspan="2">
        <strong><dhv:label name="">Oggetto della Allerta</dhv:label></strong>
      </th>
	</tr>
	
	<tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.importo">Descrizione Breve</dhv:label>
    </td>
   
     <td>    
	 <textarea rows="5" cols="40" name="oggettoAllerta" value=="<%=TicketDetails.getDescrizioneBreve() %>"><%=TicketDetails.getDescrizioneBreve() %></textarea>
	</td>
	</tr>
	<% HashMap<Integer,String> matrici= TicketDetails.getMatrici();
	if(matrici.isEmpty()) {  %>
		<%@ include file="../tipi_alimenti_modify.jsp" %>
  <% } else { %>
  
	 <%@ include file="/allerte/matrici_tree_modify.jsp" %>
	<% } %>
	<tr class="containerBody">
    <td valign="top" class="formLabel">
      Denominazione Prodotto
    </td>
    <td>    
    <input type = "text" name="denominazione_prodotto"  value = "<%=TicketDetails.getDenominazione_prodotto() %>"><font color = "red">*</font>
    </td>
    
    </tr>
    <tr class="containerBody">
    <td valign="top" class="formLabel">
     Numero del Lotto
    </td>
    <td>    
    <input type = "text" name="numero_lotto" value = "<%=TicketDetails.getNumero_lotto() %>"><font color = "red">*</font>
    </td>
    
    </tr>
    <tr class="containerBody">
    <td valign="top" class="formLabel">
      Fabbricante o Produttore
    </td>
    <td>    
    <input type = "text" name="fabbricante_produttore"  value = "<%=TicketDetails.getFabbricante_produttore() %>"><font color = "red">*</font>
    </td>
    
    </tr>
    <%
    String data_scadenza = "" ;
    if (TicketDetails.getData_scadenza_allerta()!=null)
	{
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	data_scadenza = sdf.format((new java.util.Date(TicketDetails.getData_scadenza_allerta().getTime())));
	}
    %>
    <tr class="containerBody">
    <td valign="top" class="formLabel">
      Data Scadenza/termine minimo di conservazione
    </td>
    <td>  
    
    
                  
             <input readonly type="text" id="data_scadenza_allerta" name="data_scadenza_allerta" value = "<%=data_scadenza %>" size="10"/>
		<a href="#" onClick="cal19.select(document.forms[0].data_scadenza_allerta,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
    
    
    </td>
    
    </tr>
  
	</table>  </div>
	<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
      <th colspan="2">
        <strong><dhv:label name="">Motivo Ripianificazione/Modifica</dhv:label></strong>
      </th>
	</tr>
	  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzioni.note">Indicare il motivo del cambiamento</dhv:label>
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <textarea name="motivo_ripianificazione_modifica" cols="55" rows="8"></textarea><font color ="red">*</font>
          </td>
          <td valign="top">
            <%= showAttribute(request, "problemError") %>
          </td>
        </tr>
      </table>
    </td>
	</tr>


	</table>
	
	
&nbsp;<br>
<input type="hidden" name="orgSiteId" value="<%=  TicketDetails.getOrgSiteId() %>" />
<input type="hidden" name="currentDate" value="<%=  request.getAttribute("currentDate") %>" />
<input type="hidden" name="statusId" value="<%=  TicketDetails.getStatusId() %>" />
<input type="hidden" name="trashedDate" value="<%=  TicketDetails.getTrashedDate() %>" />
<input type="hidden" name="id" id="id" value="<%=  TicketDetails.getId() %>" />
   

  
    <% if (TicketDetails.getClosed() != null) { %>
     <%-- %> <input type="button" value="<dhv:label name="button.reopen">Reopen</dhv:label>" onClick="javascript:this.form.action='TroubleTicketsAllerteNew.do?command=Reopen&ticketId=<%= TicketDetails.getId()%><%= defectCheck != null && !"".equals(defectCheck.trim()) ?"&defectCheck="+defectCheck:"" %>';submit();">
      <input type="submit" value="Annulla" onClick='<%= (!isPopup(request)?"javascript:this.form.action='TroubleTicketsAllerteNew.do?command=Details&id="+ TicketDetails.getId() +(defectCheck != null && !"".equals(defectCheck.trim()) ?"&defectCheck="+defectCheck:"")+"'":"javascript:window.close();") %>' />--%>
    <%} else {%>
      <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="return checkForm(this.form)">
      <% if ("list".equals(request.getParameter("return"))) {%>
        <input type="submit" value="Annulla" onClick='<%= (!isPopup(request)?"javascript:this.form.action='TroubleTicketsAllerteNew.do?command=Home'":"javascript:window.close();") %>' />
      <%} else if ("searchResults".equals(request.getParameter("return"))){%>
        <input type="submit" value="Annulla" onClick='<%= (!isPopup(request)?"javascript:this.form.action='TroubleTicketsAllerteNew.do?command=SearchTickets'":"javascript:window.close();") %>' />
      <%} else {%>
        <input type="submit" value="Annulla" onClick="javascript:this.form.action='TroubleTicketsAllerteNew.do?command=Details&id=<%=TicketDetails.getId()%>'" />
      <%}%>
  <%}%>
  </form>
</dhv:container>

</body>