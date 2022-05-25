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
  <%@page import="org.aspcfs.utils.web.LookupList" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.DateFormat" %>

<%@page import="org.aspcfs.utils.web.LookupElement"%>
<%@page import="org.aspcfs.modules.allerte.base.AslCoinvolte"%><jsp:useBean id="Acque" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.allerte.base.Ticket" scope="request"/>
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
<%@ include file="../initPage.jsp" %>
<body onload="abilitaData('<%=TicketDetails.getIsPregresso() %>'); mostraSottoCategoria();mostraCU();mostraorigineAllerta();abilitaTestoAlimentoComposto(); abilitaLista_tipoAnalisi(); init(); abilitaLista_tipoChimico();">
<form name="details" enctype="multipart/form-data" action="TroubleTicketsAllerte.do?command=Update&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim()) ?"&defectCheck="+defectCheck:"") %><%= isPopup(request)?"&popup=true":"" %>" method="post">
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="TroubleTicketsAllerte.do"><dhv:label name="sanzioniddd">Allerte</dhv:label></a> > 
<%if(defectCheck != null && !"".equals(defectCheck.trim())) {%>
  <a href="TroubleTicketDefects.do?command=View"><dhv:label name="tickets.defects.viewDefects">View Defects</dhv:label></a> >
  <a href="TroubleTicketDefects.do?command=Details&defectId=<%= defectCheck %>"><dhv:label name="tickets.defects.defectDetails">Defect Details</dhv:label></a> >
  <a href="TroubleTicketsAllerte.do?command=Details&id=<%= TicketDetails.getId() %>&defectCheck=<%= defectCheck %>"><dhv:label name="sanzioni.dettaglisss">Scheda Allerta</dhv:label></a> >
<%}else{%>
<% if (("list".equals((String)request.getParameter("return"))) ||
      ("searchResults".equals((String)request.getParameter("return")))) {%>
    <% if ("yes".equals((String)session.getAttribute("searchTickets"))) {%>
      <a href="TroubleTicketsAllerte.do?command=SearchTicketsForm"><dhv:label name="tickets.searchForm">Search Form</dhv:label></a> >
      <a href="TroubleTicketsAllerte.do?command=SearchTickets"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
    <%}else{%> 
      <a href="TroubleTicketsAllerte.do?command=Home"><dhv:label name="sanzioni.visualizzaddd">Visualizza Allerte</dhv:label></a> >
    <%}%>
<%} else {%>
  <% if ("yes".equals((String)session.getAttribute("searchTickets"))) {%>
    <a href="TroubleTicketsAllerte.do?command=SearchTickets"><dhv:label name="tickets.searchss">Ricerca Allerte</dhv:label></a> >
  <%}else{%> 
    <a href="TroubleTicketsAllerte.do?command=Home"><dhv:label name="sanzioni.visualizzaaa">Visualizza Allerte</dhv:label></a> >
  <%}%>
    <a href="TroubleTicketsAllerte.do?command=Details&id=<%= TicketDetails.getId() %>"><dhv:label name="sanzioni.dettagliss">Scheda Allerta</dhv:label></a> >
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
      <%--<input type="button" value="<dhv:label name="button.reopen">Reopen</dhv:label>" onClick="javascript:this.form.action='TroubleTicketsAllerte.do?command=Reopen&ticketId=<%= TicketDetails.getId()%><%= (defectCheck != null && !"".equals(defectCheck.trim()) ?"&defectCheck="+defectCheck:"") %>';submit();">
      <input type="submit" value="Annulla" onClick='<%= (!isPopup(request)?"javascript:this.form.action='TroubleTicketsAllerte.do?command=Details&id="+ TicketDetails.getId() +"'":"javascript:window.close();") %>' />--%>
    <%} else {%>
      <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="return checkForm(this.form)">
      <% if ("list".equals(request.getParameter("return"))) {%>
        <input type="submit" value="Annulla" onClick='<%= (!isPopup(request)?"javascript:this.form.action='TroubleTicketsAllerte.do?command=Home'":"javascript:window.close();") %>' />
      <%} else if ("searchResults".equals(request.getParameter("return"))){%>
        <input type="submit" value="Annulla" onClick='<%= (!isPopup(request)?"javascript:this.form.action='TroubleTicketsAllerte.do?command=SearchTickets'":"javascript:window.close();") %>' />
      <% }else {%>
        <input type="submit" value="Annulla" onClick='<%= (!isPopup(request)?"javascript:this.form.action='TroubleTicketsAllerte.do?command=Details&id="+ TicketDetails.getId() +"'":"javascript:window.close();") %>' />
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



function abilitaData(value)
{

	if(value == "si")
	{
		document.getElementById("colonnaChiusura").style.display="";
	}
	else{
		document.details.dataChiusura.value = "";
		document.getElementById("colonnaChiusura").style.display="none";
		}

	
}

function clearAll(){

animali=document.details.alimentiOrigineAnimale;
vegetali=document.details.alimentiOrigineVegetale;
compost=document.details.alimentiComposti;
acqua=document.details.alimentiAcqua;
additivi=document.details.alimentiAdditivi;
bevande=document.details.alimentiBevande;
mangimi = document.details.alimentiMangimi;
mat=document.details.materialiAlimenti;

	


	if(animali.checked==false){
document.details.notealimenti.value="";
	}

	if(vegetali.checked==false){
document.details.notealimenti2.value="";
	}

	if(compost.checked==false){
document.details.testoAlimentoComposto.value="";
	}
	if(acqua.checked==false){
document.details.noteacqua.value="";
	}
	if(additivi.checked==false){
document.details.notebevande.value="";
	}
	

	if(mat.checked==false){
document.details.notematerialialimenti.value="";
	}
	
}



function mostraTrasformati(){

	if(document.details.tipoAlimento.value=="0"){
	document.details.alimentiOrigineVegetaleValoriNonTrasformati.style.display="block";
	document.details.alimentiOrigineVegetaleValoriTrasformati.style.display="none";
	document.details.fruttaFresca.style.display="none";
	document.details.fruttaSecca.style.display="none";
	document.details.ortaggi.style.display="none";
	document.details.funghi.style.display="none";
	document.details.derivati.style.display="none";
	document.details.conservati.style.display="none";
	document.details.grassi.style.display="none";
	document.details.vino.style.display="none";
	document.details.zuppe.style.display="none";
	}else{
		if(document.details.tipoAlimento.value=="1"){
		document.details.alimentiOrigineVegetaleValoriNonTrasformati.style.display="none";
		document.details.alimentiOrigineVegetaleValoriTrasformati.style.display="block";
		document.details.fruttaFresca.style.display="none";
		document.details.fruttaSecca.style.display="none";
		document.details.ortaggi.style.display="none";
		document.details.funghi.style.display="none";
		document.details.derivati.style.display="none";
		document.details.conservati.style.display="none";
		document.details.grassi.style.display="none";
		document.details.vino.style.display="none";
		document.details.zuppe.style.display="none";
		}
		else{

			if(document.details.tipoAlimento.value=="-1"){
			document.details.alimentiOrigineVegetaleValoriNonTrasformati.style.display="none";
			document.details.alimentiOrigineVegetaleValoriTrasformati.style.display="none";
			document.details.fruttaFresca.style.display="none";
			document.details.fruttaSecca.style.display="none";
			document.details.ortaggi.style.display="none";
			document.details.funghi.style.display="none";
			document.details.derivati.style.display="none";
			document.details.conservati.style.display="none";
			document.details.grassi.style.display="none";
			document.details.vino.style.display="none";
			document.details.zuppe.style.display="none";
			}	}
	}
		  }



function disabilitaAltriAlimenti(){

	document.getElementById("alimentinonAnimali").disabled=true;
		
	}


function abilitaAltriAlimenti(){

	document.getElementById("alimentinonAnimali").disabled=false;
		
	}

function mostraSottoCategoria(){

	mostraTrasformati();

	righe="-1";
if(document.details.tipoAlimento.value=="0")
	
 righe=document.details.alimentiOrigineVegetaleValoriNonTrasformati.value;
else{
	if(document.details.tipoAlimento.value=="1")
	 righe=document.details.alimentiOrigineVegetaleValoriTrasformati.value;
}
 
	
	if(righe=="1" ){
		document.details.fruttaFresca.style.display="block";
		document.details.fruttaSecca.style.display="none";
		document.details.ortaggi.style.display="none";
		document.details.funghi.style.display="none";
		document.details.derivati.style.display="none";
		document.details.conservati.style.display="none";
		document.details.grassi.style.display="none";
		document.details.vino.style.display="none";
		document.details.zuppe.style.display="none";

		}else{
			if(righe=="2" ){

				document.details.fruttaFresca.style.display="none";
				document.details.fruttaSecca.style.display="block";
				document.details.ortaggi.style.display="none";
				document.details.funghi.style.display="none";
				document.details.derivati.style.display="none";
				document.details.conservati.style.display="none";
				document.details.grassi.style.display="none";
				document.details.vino.style.display="none";
				document.details.zuppe.style.display="none";


			}else{
				if(righe=="4" ){
					document.details.fruttaFresca.style.display="none";
					document.details.fruttaSecca.style.display="none";
					document.details.ortaggi.style.display="block";
					document.details.funghi.style.display="none";
					document.details.derivati.style.display="none";
					document.details.conservati.style.display="none";
					document.details.grassi.style.display="none";
					document.details.vino.style.display="none";
					document.details.zuppe.style.display="none";

				}
				else{
					if(righe=="5" ){
						document.details.fruttaFresca.style.display="none";
						document.details.fruttaSecca.style.display="none";
						document.details.ortaggi.style.display="none";
						document.details.funghi.style.display="block";
						document.details.derivati.style.display="none";
						document.details.conservati.style.display="none";
						document.details.grassi.style.display="none";
						document.details.vino.style.display="none";
						document.details.zuppe.style.display="none";

					}else{
						if(righe=="6" ){
							document.details.fruttaFresca.style.display="none";
							document.details.fruttaSecca.style.display="none";
							document.details.ortaggi.style.display="none";
							document.details.funghi.style.display="none";
							document.details.conservati.style.display="block";
							document.details.derivati.style.display="none";
							document.details.grassi.style.display="none";
							document.details.vino.style.display="none";
							document.details.zuppe.style.display="none";

						}else{
							if(righe=="7" ){
								document.details.fruttaFresca.style.display="none";
								document.details.fruttaSecca.style.display="none";
								document.details.ortaggi.style.display="none";
								document.details.funghi.style.display="none";
								document.details.derivati.style.display="block";
								document.details.conservati.style.display="none";
								document.details.grassi.style.display="none";
								document.details.vino.style.display="none";
								document.details.zuppe.style.display="none";

							}else{

								if(righe=="8" ){
									document.details.fruttaFresca.style.display="none";
									document.details.fruttaSecca.style.display="none";
									document.details.ortaggi.style.display="none";
									document.details.funghi.style.display="none";
									document.details.derivati.style.display="none";
									document.details.conservati.style.display="none";
									document.details.grassi.style.display="block";
									document.details.vino.style.display="none";
									document.details.zuppe.style.display="none";

								}else{
									if(righe=="9" ){
										document.details.fruttaFresca.style.display="none";
										document.details.fruttaSecca.style.display="none";
										document.details.ortaggi.style.display="none";
										document.details.funghi.style.display="none";
										document.details.derivati.style.display="none";
										document.details.conservati.style.display="none";
										document.details.grassi.style.display="none";
										document.details.vino.style.display="block";
										document.details.zuppe.style.display="none";

									}else{

										if(righe=="11" ){
											document.details.fruttaFresca.style.display="none";
											document.details.fruttaSecca.style.display="none";
											document.details.ortaggi.style.display="none";
											document.details.funghi.style.display="none";
											document.details.derivati.style.display="none";
											document.details.conservati.style.display="none";
											document.details.grassi.style.display="none";
											document.details.vino.style.display="none";
											document.details.zuppe.style.display="block";

										}else{

											document.details.grassi.style.display="none";
											document.details.vino.style.display="none";
											document.details.zuppe.style.display="none";
											document.details.fruttaFresca.style.display="none";
											document.details.fruttaSecca.style.display="none";
											document.details.ortaggi.style.display="none";
											document.details.funghi.style.display="none";
											document.details.derivati.style.display="none";
											document.details.conservati.style.display="none";

											}



										}


									}
									




								
								

								
								}

							}

						}

					}


				}

			}





	

	
}

function abilitaAdditivi(){

	document.getElementById("additivi").disabled=false;
		
	}


function abilitaMaterialiAlimenti(){

	document.getElementById("materialialimenti").disabled=false;
		
	}

function disabilitaAdditivi(){

	document.getElementById("additivi").disabled=true;
		
	}

function mostraListaComm(){

numeroasl=document.details.numasl.value;
	
lis=document.details.ListaCommercializzazione.value;

if(lis=="1"){

	for(i=0;i<numeroasl;i++){

	if(i==0){

		}

			alert(document.details.i.value);

		}


	
}else{


	
}


	
}


function disabilitaBevande(){

	document.getElementById("bevande").disabled=true;
		
	}

function disabilitaMangimi(){

	document.getElementById("mangimi").disabled=true;
		
	}

function disabilitaMaterialiAlimenti(){

	document.getElementById("materialialimenti").disabled=true;
		
	}


function disabilitaAcque(){
	document.getElementById("acqua").disabled=true;
	
}
function abilitaAcqueCheck(){
	document.getElementById("acqua").disabled=false; 
	
}

function abilitaBevandeCheck(){
	document.getElementById("bevande").disabled=false; 
	
}

function abilitaMangimiCheck(){
	document.getElementById("mangimi").disabled=false; 
	
}


function abilitaalimentinonAnimali(){


	 if (document.getElementById("alimentinonAnimali").checked==true)
	 {

		 
		 document.getElementById("alimentinonanimalicella").style.display = "block";
		
		 document.details.altrialimenti.style.display = "block";
			
	  disabilitaCompostiAnimale();
		disabilitaBevande();
		disabilitaMangimi();
	  disabilitaCompostiVegetale();
	  disabilitaAcque();
	  disabilitaAdditivi();
	  disabilitaMaterialiAlimenti();
	  
	    disabilitaComposti();
	  //disabilitaDolciumi();
	  //disabilitaGelati();

	  
	 }
	 else
	 {

		 abilitaCompostiCheck();
		 abilitaBevandeCheck();
		 abilitaMangimiCheck();
		 abilitaMaterialiAlimenti();
		 abilitaAdditivi();
		 abilitaAcqueCheck()
		 document.getElementById("alimentinonanimalicella").style.display = "none";
		 document.details.altrialimenti.style.display = "none";

	  abilitaCompostiVegetaleCheck();
	//abilitaDolciumiCheck();
	//abilitaGelatiCheck();
	   abilitaAnimaliCheck();
	 } 
	
	
}

function abilitaAcque()
{
    alimentiOrigine = document.getElementById("acqua");
    //sel2 = document.getElementById("lookupVegetale");
    if(alimentiOrigine.checked==true)
    {
       

    	 document.getElementById("lookupVegetale").style.visibility = "hidden";

         document.getElementById("notealimenti2").style.display="none";

    	 
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
   
 disabilitaMaterialiAlimenti();
 
    disabilitaAdditivi();
    disabilitaBevande();
    disabilitaMangimi();
    //disabilitaGelati();
    //disabilitaDolciumi();

   
    }
    else
    { 

    	clearAll();
      //  abilitaGelatiCheck();
        //abilitaDolciumiCheck();

 abilitaAltriAlimenti();
    	abilitaCompostiVegetaleCheck()
    	abilitaAnimaliCheck();
        abilitaCompostiCheck();
		abilitaAdditivi();
    	   abilitaMaterialiAlimenti();
    	   abilitaBevandeCheck();
    	   abilitaMangimiCheck();
        
    	document.getElementById("acquaSelect").style.display="none";
        document.getElementById("noteacqua").style.display="none";

        document.getElementById("acquaSelect").value="-1"
        document.getElementById("noteacqua").value="";
        
     //abilitaAnimaliCheck();
     //abilitaCompostiCheck();
          
    }  
}

function abilitaBevande()
{
    alimentiOrigine = document.getElementById("bevande");
    //sel2 = document.getElementById("lookupVegetale");
    if(alimentiOrigine.checked)
    { //sel2.style.visibility = "visible";
    //disabilitaCompostiAnimale();
    //disabilitaComposti();
     document.getElementById("lookupVegetale").style.visibility = "hidden";

     document.getElementById("notealimenti2").style.display="none";

	 
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
	disabilitaCompostiAnimale();
    disabilitaComposti();
    disabilitaAcque();
 disabilitaMaterialiAlimenti();
disabilitaMangimi();
 //disabilitaDolciumi();
 //disabilitaGelati();
  disabilitaAltriAlimenti();
    disabilitaAdditivi();
    
    
    
    document.getElementById("notebevande").style.display="block";
    }
    else
    { clearAll();
    	abilitaCompostiVegetaleCheck()
    	abilitaAnimaliCheck();
        abilitaCompostiCheck();
		abilitaMangimiCheck();
        abilitaAcqueCheck();

      	
       // abilitaGelatiCheck();
      //  abilitaDolciumiCheck();

      	
 abilitaAltriAlimenti();
      	
    		abilitaAdditivi();
    	   abilitaMaterialiAlimenti();
     //sel2.style.visibility = "hidden";

     document.getElementById("notebevande").style.display="none";
     document.getElementById("notebevande").value="";

     //abilitaAnimaliCheck();
     //abilitaCompostiCheck();
          
    }  
}


function abilitaMangimi()
{
    alimentiOrigine = document.getElementById("mangimi");
    //sel2 = document.getElementById("lookupVegetale");
    if(alimentiOrigine.checked)
    { //sel2.style.visibility = "visible";
    //disabilitaCompostiAnimale();
    //disabilitaComposti();
     document.getElementById("lookupVegetale").style.visibility = "hidden";

     document.getElementById("notealimenti2").style.display="none";

	 
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
	disabilitaCompostiAnimale();
    disabilitaComposti();
    disabilitaAcque();
    disabilitaBevande();
 disabilitaMaterialiAlimenti();

 //disabilitaDolciumi();
 //disabilitaGelati();
  disabilitaAltriAlimenti();
    disabilitaAdditivi();
    
    
    
    document.getElementById("notealimentimangimi").style.display="block";
    }
    else
    { clearAll();
    	abilitaCompostiVegetaleCheck()
    	abilitaAnimaliCheck();
        abilitaCompostiCheck();

        abilitaAcqueCheck();
		abilitaBevandeCheck();
      	
       // abilitaGelatiCheck();
      //  abilitaDolciumiCheck();

      	
 abilitaAltriAlimenti();
      	
    		abilitaAdditivi();
    	   abilitaMaterialiAlimenti();
     //sel2.style.visibility = "hidden";

     document.getElementById("notealimentimangimi").style.display="none";
     document.getElementById("notealimenti").value="";

     //abilitaAnimaliCheck();
     //abilitaCompostiCheck();
          
    }  
}

function abilitatipoAdditivi(){
	var check=document.getElementById("additivi");

if(check.checked){

	 document.getElementById("lookupVegetale").style.visibility = "hidden";

     document.getElementById("notealimenti2").style.display="none";

	 
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
    
    disabilitaMaterialiAlimenti();
   
document.getElementById("noteadditivi").style.display="block";
	
}else{
	clearAll();
	 abilitaAnimaliCheck();
	abilitaCompostiVegetaleCheck();
	  abilitaCompostiCheck();
	  abilitaAcqueCheck();
	abilitaBevandeCheck();	

//	abilitaDolciumiCheck();
	//abilitaGelatiCheck();
 abilitaMaterialiAlimenti();
 abilitaMangimiCheck();

 document.getElementById("noteadditivi").style.display="none";	
 document.getElementById("noteadditivi").value="";
}
	

	
}


function abilitatipomaterialiAlimenti(){

	var check=document.getElementById("materialialimenti");
if(check.checked) {

	 document.getElementById("lookupVegetale").style.visibility = "hidden";

     document.getElementById("notealimenti2").style.display="none";

	 
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
    //disabilitaDolciumi();
    //disabilitaGelati();
    
  
    disabilitaAcque();
   
     disabilitaBevande();
     disabilitaMangimi();
   
     disabilitaCompostiAnimale();
     disabilitaAltriAlimenti();
   disabilitaAdditivi();
   document.getElementById("notematerialialimenti").style.display="block";
}else{
	clearAll();

	 abilitaAnimaliCheck();
		abilitaCompostiVegetaleCheck();
		  abilitaCompostiCheck();
		  abilitaAcqueCheck();
		//  abilitaDolciumiCheck();
		 // abilitaGelatiCheck();
		  
		abilitaBevandeCheck();	
		abilitaMangimiCheck();
abilitaAdditivi();
abilitaAltriAlimenti();
document.getElementById("notematerialialimenti").style.display="none";
document.getElementById("notematerialialimenti").value="";
	
}
	
}




/* function disabilitaDolciumi(){
	document.getElementById("dolciumi").disabled=true;
	
}function disabilitaGelati(){
	document.getElementById("gelati").disabled=true;
	
}

function abilitaDolciumiCheck(){
	document.getElementById("dolciumi").disabled=false; 
	
}

function abilitaGelatiCheck(){
	document.getElementById("gelati").disabled=false; 
	
}


function abilitatipoDolciumi(){

	var check=document.getElementById("dolciumi");
if(check.checked) {

	 document.getElementById("lookupVegetale").style.visibility = "hidden";

     document.getElementById("notealimenti2").style.display="none";

	 
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
    disabilitaGelati();
    disabilitaMaterialiAlimenti();
    disabilitaAcque();
   
     disabilitaBevande();
   
     disabilitaCompostiAnimale();
    
   disabilitaAdditivi();
   document.getElementById("notematerialialimenti").style.display="none";
   document.getElementById("notedolciumi").style.display="block";
}else{

	 abilitaAnimaliCheck();
		abilitaCompostiVegetaleCheck();
		  abilitaCompostiCheck();
		  abilitaAcqueCheck();
		abilitaBevandeCheck();	
abilitaAdditivi();
abilitaGelatiCheck();
abilitaMaterialiAlimenti();
document.getElementById("notedolciumi").style.display="none";
document.getElementById("notedolciumi").value="";
	
}
	
}



function abilitatipoGelati(){

	var check=document.getElementById("gelati");
if(check.checked) {

	 document.getElementById("lookupVegetale").style.visibility = "hidden";

     document.getElementById("notealimenti2").style.display="none";

	 
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
    disabilitaDolciumi();
    disabilitaMaterialiAlimenti();
    disabilitaAcque();
   
     disabilitaBevande();
   
     disabilitaCompostiAnimale();
    
   disabilitaAdditivi();
   document.getElementById("notedolciumi").style.display="none";
      document.getElementById("notematerialialimenti").style.display="none";
   document.getElementById("notegelati").style.display="block";
}else{

	 abilitaAnimaliCheck();
		abilitaCompostiVegetaleCheck();
		  abilitaCompostiCheck();
		  abilitaAcqueCheck();
		abilitaBevandeCheck();	
abilitaAdditivi();
abilitaMaterialiAlimenti();
document.getElementById("notegelati").style.display="none";
document.getElementById("notegelati").value="";
abilitaDolciumiCheck();
	
}
	
}*/

function init(){
	var flag=0;
	var check=document.getElementById("materialialimenti");
	if(check.checked) {
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

	if(check.checked){
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

		if(check.checked){
		
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
	    { //sel2.style.visibility = "visible";
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
	    { //sel2.style.visibility = "visible";
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
	    
	    
	    document.getElementById("notealimentimangimi").style.display="block";
	    }

	   

	if(document.details.alimentiOrigineAnimale.checked==true){
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
		    document.getElementById("notealimentimangimi").style.display="none";
			
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
	if(tipo == "1"){
		abilitaLookupOrigineVegetale();
		  
		
		document.forms['details'].alimentiOrigineAnimaleTrasformati.value="-1";

		document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
	
	    	  document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
	
	document.forms['details'].alimentiOrigineAnimaleNonTrasformati.style.display="block";
	
	document.forms['details'].alimentiOrigineAnimaleTrasformati.style.display="none"
		

	      disabilitaCompostiVegetale();
	      
	      disabilitaComposti();
	abilitaSpecie(document.forms['details']);
	    }else{
	if(tipo == "2"){
		  document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
	   
		  document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
		document.forms['details'].alimentiOrigineAnimaleNonTrasformati.style.display="none";
		document.forms['details'].alimentiOrigineAnimaleNonTrasformati.value = "-1";
		document.forms['details'].alimentiOrigineAnimaleTrasformati.style.display="block"
			 disabilitaCompostiVegetale();
		abilitaLookupOrigineVegetale();
		
	    disabilitaComposti();
	disabilitaNonTrasformati('details');
		
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
	


	if(document.details.alimentiOrigineVegetale.checked==true){
		flag = 1
	abilitaLookupOrigineVegetale();
	 disabilitaAltriAlimenti();
	}

	


	if(document.details.alimentiComposti.checked==true){
		flag = 1
		//disabilitaDolciumi();
        //disabilitaGelati();
       document.details.fruttaFresca.style.display="none";
    	 document.details.fruttaSecca.style.display="none";
    	 document.details.ortaggi.style.display="none";
    	 document.details.funghi.style.display="none";
    	 document.details.derivati.style.display="none";
    	 document.details.conservati.style.display="none";
    	 document.details.vino.style.display="none";
    	 document.details.grassi.style.display="none";
    	 document.details.zuppe.style.display="none";
	abilitaTestoAlimentoComposto();

	}

	if(flag == 0)
	{
		

		document.getElementById("acquaSelect").style.display="none";
	    document.getElementById("noteacqua").style.display="none";
	    document.getElementById("notebevande").style.display="none";
	    document.getElementById("notealimentimangimi").style.display="none";
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
	}

	/* var check=document.getElementById("dolciumi");
	if(check.checked) {

		 document.getElementById("lookupVegetale").style.visibility = "hidden";

	     document.getElementById("notealimenti2").style.display="none";

		 
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
	    disabilitaGelati();
	    disabilitaMaterialiAlimenti();
	    disabilitaAcque();
	   
	     disabilitaBevande();
	   
	     disabilitaCompostiAnimale();
	    
	   disabilitaAdditivi();
	   document.getElementById("notematerialialimenti").style.display="none";
	   document.getElementById("notedolciumi").style.display="block";
	}

	var check=document.getElementById("gelati");
	if(check.checked) {

		 document.getElementById("lookupVegetale").style.visibility = "hidden";

	     document.getElementById("notealimenti2").style.display="none";

		 
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
	    disabilitaDolciumi();
	    disabilitaMaterialiAlimenti();
	    disabilitaAcque();
	   
	     disabilitaBevande();
	   
	     disabilitaCompostiAnimale();
	    
	   disabilitaAdditivi();
	   document.getElementById("notedolciumi").style.display="none";
	      document.getElementById("notematerialialimenti").style.display="none";
	   document.getElementById("notegelati").style.display="block";}*/

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

    alimentiOrigine = document.forms['details'].tipoAlimentiAnimali.value;

   


    

    
    
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
   
     
}





function abilitaSpecie(form)
{
	
    if((form.alimentiOrigineAnimaleNonTrasformati.value >= 1) && (form.alimentiOrigineAnimaleNonTrasformati.value <= 4))
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

    	if(form.alimentiOrigineAnimaleNonTrasformati.value==8){
    	
    		form.alimentiOrigineAnimaleTrasformati.value=-1;
    		form.TipoSpecie_uova.value="-1";
    		document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
    	  
    	    	  document.details.alimentiOrigineAnimaleNonTrasformatiValori.value= -1;
    		form.TipoSpecie_latte.style.display="block";
    		form.TipoSpecie_uova.style.display="none";
    		form.TipoSpecie_uova.value=-1;
    	 	document.getElementById("notealimenti").style.display="block";
    		
        	}else{
            
        		if(form.alimentiOrigineAnimaleNonTrasformati.value==9){
            	
        			form.alimentiOrigineAnimaleTrasformati.value=-1;
            		form.TipoSpecie_latte.value="-1";
            		document.details.alimentiOrigineAnimaleNonTrasformatiValori.value=-1;
        		 document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
        		  
            		form.TipoSpecie_uova.style.display="block";
            		form.TipoSpecie_latte.style.display="none";
            	 	document.getElementById("notealimenti").style.display="block";
            		
                	}else{
                		document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
                		form.alimentiOrigineAnimaleTrasformati.value="-1";
                	 	document.getElementById("notealimenti").style.display="block";
        		form.TipoSpecie_uova.style.display="none";
        	  	form.TipoSpecie_latte.style.display="none";
        	  	form.TipoSpecie_uova.value="-1";
        	  	form.TipoSpecie_latte.value="-1";
    document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
      form.alimentiOrigineAnimaleNonTrasformatiValori.value= -1;
  
     } }}
     
    


     
}

function abilitaTestoAlimentoComposto()
{
	

 if (document.getElementById("alimentiComposti").checked==true)
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
	    document.getElementById("notealimentimangimi").style.display="none";
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
		
	 document.getElementById("testoAlimentoComposto").style.visibility = "visible";
		
  disabilitaCompostiAnimale();
	
  disabilitaCompostiVegetale();
  //disabilitaGelati();
  //disabilitaDolciumi();
  

  
 }
 else
 {clearAll();
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
 } 
}


function mostraCU(){

	 sel2=document.details.asl_coinvolte;

		for( i = 0 ; i < sel2.length; i++){

	if(sel2[i].checked==true){

			 document.getElementById("cu_"+sel2[i].value).style.display="";
			 document.getElementById("int_"+sel2[i].value).style.display="";
			 if(sel2[i].value =="16"){
					
					
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
	mostraAllegato();
	
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
				 

				
				 if(siteId =="16"){
						
					
						 document.getElementById("colonnaFuoriRegione").style.display="block";
						document.getElementById("noteFuoriRegione").style.display="block";
						
						}

					document.getElementById('select_'+siteId).style.display="";

					document.getElementById('selectstabilimenti_'+siteId).style.display="";
				
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
					 document.getElementById("org_"+siteid+"_"+i).style.display="none";
					 
				 if(siteId =="16"){
						
						
					 document.getElementById("colonnaFuoriRegione").style.display="none";
					document.getElementById("noteFuoriRegione").style.display="none";
					
					}
					
				 
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
	
    alimentiOrigine = document.getElementById("alimentiOrigineVegetale");
    
    if(alimentiOrigine.checked==true)
    { mostraSottoCategoria();
    	disabilitaAcque();
    	disabilitaAdditivi();
    	disabilitaBevande();
    	disabilitaMangimi();
    	disabilitaAltriAlimenti();
    	disabilitaMaterialiAlimenti();
    	 document.getElementById("acquaSelect").style.display="none";
		    document.getElementById("noteacqua").style.display="none";
		    document.getElementById("notebevande").style.display="none";
		    document.getElementById("notealimentimangimi").style.display="none";
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
    	document.forms['details'].alimentiOrigineAnimaleTrasformati.value="-1";
    	document.forms['details'].alimentiOrigineAnimaleTrasformati.style.display="none"
    		document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
    	document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
    //	 disabilitaDolciumi();
      //   disabilitaGelati();
        
    	document.getElementById("lookupVegetale").style.visibility = "visible";
    disabilitaCompostiAnimale();
    disabilitaComposti();
    if(document.details.tipoAlimento=="0"){
        document.details.alimentiOrigineVegetaleValoriNonTrasformati.style.display="block";
        document.details.alimentiOrigineVegetaleValoriTrasformati.style.display="none";

        }else{
            if(document.details.tipoAlimento=="1"){
                document.details.alimentiOrigineVegetaleValoriTrasformati.style.display="block";
            document.details.alimentiOrigineVegetaleValoriNonTrasformati.style.display="none";
            }}
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
          
    }  
}




function abilitaCompostiVegetaleCheck(){
	
	 alimentiOrigine1 = document.getElementById("alimentiOrigineVegetale");
	alimentiOrigine1.disabled=false;
	
	
}
function abilitaCompostiCheck(){
	
	 alimentiOrigine2  = document.getElementById("alimentiComposti");
		alimentiOrigine2.disabled=false;
	
}

function abilitaAnimaliCheck(){

	  alimentiOrigine3 = document.getElementById("alimentiOrigineAnimale");
		alimentiOrigine3.disabled=false;
	
}
function disabilitaCompostiVegetale(){
	
	 alimentiOrigine = document.getElementById("alimentiOrigineVegetale");
	alimentiOrigine.disabled="true";
}

function disabilitaCompostiAnimale(){
	
	 alimentiOrigine = document.getElementById("alimentiOrigineAnimale");
	alimentiOrigine.disabled="true";
}

function disabilitaComposti(){
	
	 alimentiOrigine = document.getElementById("alimentiComposti");
	alimentiOrigine.disabled="true";
}

function disabilitaNonTrasformati(form)
{
	
   if(document.details.alimentiOrigineAnimaleTrasformati.value != -1)
     {
     
      document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
      document.details.TipoSpecie_latte.style.display="none";
      document.details.TipoSpecie_uova.style.display="none";
      document.details.TipoSpecie_latte.value="-1";
      document.details.TipoSpecie_uova.value="-1";
      
      
      document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
   	document.getElementById("notealimenti").style.display="block";
     }  

}

function abilitaTipoAlimentoAnimale(){


 check=document.getElementById("alimentiOrigineAnimale");

if(check.checked==true){
	disabilitaAcque();
	disabilitaAdditivi();
	disabilitaBevande();
	disabilitaMangimi();
	disabilitaAltriAlimenti();
	disabilitaMaterialiAlimenti();
	 document.getElementById("acquaSelect").style.display="none";
	    document.getElementById("noteacqua").style.display="none";
	    document.getElementById("notebevande").style.display="none";
	    document.getElementById("notealimentimangimi").style.display="none";
	    document.getElementById("noteadditivi").style.display="none";
	    document.getElementById("notematerialialimenti").style.display="none";
	document.getElementById("tipoAlimentiAnimali").style.display="block";
	disabilitaCompostiVegetale();
	//disabilitaDolciumi();
    //disabilitaGelati();
   
    disabilitaComposti();
	
}
else{
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
	

}
	
}


function mostraFollowUP(){
	
if(document.details.EsitoCampione.value==1){

	document.getElementById("followup").style.display="block";
	
}else{

	document.getElementById("followup").style.display="none";
	
}

	
}





function abilitaCheckSegnalazione()
{

   allerta = document.getElementById("allerta");
   nonConformita = document.getElementById("nonConformita");
   if(nonConformita.checked==true)
    { allerta.checked = false;
    }
}


function abilitaCheckAllerta()
{
	
   allerta = document.getElementById("allerta");
   nonConformita = document.getElementById("nonConformita");
   if(allerta.checked==true)
    { nonConformita.checked = false;
    }
}




function abilitaLista_tipoAnalisi(){
	

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

	}
	
}








function disabilitaTipochimico(){
	
	document.forms['details'].TipoCampione_sottochimico.style.display="none";
	document.forms['details'].TipoCampione_sottochimico2.style.display="none";
	document.forms['details'].TipoCampione_sottochimico3.style.display="none";
	document.forms['details'].TipoCampione_sottochimico4.style.display="none";
	document.forms['details'].TipoCampione_sottochimico5.style.display="none";
}



function abilitaLista_tipoChimico(){
	
	 tipo=document.forms['details'].TipoCampione_chimico.value;

	 
	if(tipo==1){
		document.forms['details'].TipoCampione_sottochimico.style.display="block";

		
		document.forms['details'].TipoCampione_sottochimico2.style.display="none";
	
		document.forms['details'].TipoCampione_sottochimico3.style.display="none";
	
		document.forms['details'].TipoCampione_sottochimico4.style.display="none";

		document.forms['details'].TipoCampione_sottochimico5.style.display="none";
	
return;

		}
	if(tipo==2){
		document.forms['details'].TipoCampione_sottochimico.style.display="none";
		document.forms['details'].TipoCampione_sottochimico2.style.display="block";
		document.forms['details'].TipoCampione_sottochimico3.style.display="none";
		document.forms['details'].TipoCampione_sottochimico4.style.display="none";
		document.forms['details'].TipoCampione_sottochimico5.style.display="none";
		return;


		}

	if(tipo==3){
		document.forms['details'].TipoCampione_sottochimico.style.display="none";
		document.forms['details'].TipoCampione_sottochimico2.style.display="none";
		document.forms['details'].TipoCampione_sottochimico3.style.display="block";
		document.forms['details'].TipoCampione_sottochimico4.style.display="none";
		document.forms['details'].TipoCampione_sottochimico5.style.display="none";
	
		return;

		}

	if(tipo==4){
		document.forms['details'].TipoCampione_sottochimico.style.display="none";
		document.forms['details'].TipoCampione_sottochimico2.style.display="none";
		document.forms['details'].TipoCampione_sottochimico3.style.display="none";
		document.forms['details'].TipoCampione_sottochimico4.style.display="block";
		document.forms['details'].TipoCampione_sottochimico5.style.display="none";
		return;

		}
	if(tipo==5){
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
	

}



function updateSubList1() {
  var orgId = document.forms['details'].orgId.value;
  if(orgId != '-1'){
    var sel = document.forms['details'].elements['catCode'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTicketsAllerte.do?command=CategoryJSList&form=details&catCode=" + escape(value)+'&orgId='+orgId;
    window.frames['server_commands'].location.href=url;
  } else {
    var sel = document.forms['details'].elements['catCode'];
    sel.options.selectedIndex = 0;
    alert(label("select.account.first",'You have to select an Account first'));
    return;
  }
}
function updateSubList2() {
  var orgId = document.forms['details'].orgId.value;
  var sel = document.forms['details'].elements['subCat1'];
  var value = sel.options[sel.selectedIndex].value;
  var url = "TroubleTicketsAllerte.do?command=CategoryJSList&form=details&subCat1=" + escape(value)+'&orgId='+orgId;
  window.frames['server_commands'].location.href=url;
}
<dhv:include name="ticket.subCat2" none="true">
function updateSubList3() {
  var orgId = document.forms['details'].orgId.value;
  var sel = document.forms['details'].elements['subCat2'];
  var value = sel.options[sel.selectedIndex].value;
  var url = "TroubleTicketsAllerte.do?command=CategoryJSList&form=details&subCat2=" + escape(value)+'&orgId='+orgId;
  window.frames['server_commands'].location.href=url;
}
</dhv:include>
<dhv:include name="ticket.subCat3" none="true">
  function updateSubList4() {
    var orgId = document.forms['details'].orgId.value;
    var sel = document.forms['details'].elements['subCat3'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTicketsAllerte.do?command=CategoryJSList&form=details&subCat3=" + escape(value)+'&orgId='+orgId;
    window.frames['server_commands'].location.href=url;
  }
</dhv:include>
function updateUserList() {
  var sel = document.forms['details'].elements['departmentCode'];
  var value = sel.options[sel.selectedIndex].value;
  var orgSite = document.forms['details'].elements['orgSiteId'].value;
  var url = "TroubleTicketsAllerte.do?command=DepartmentJSList&form=details&dept=Assigned&orgSiteId="+ orgSite +"&populateResourceAssigned=true&resourceAssignedDepartmentCode=" + escape(value);
  window.frames['server_commands'].location.href=url;
}
function updateResolvedByUserList() {
  var sel = document.forms['details'].elements['resolvedByDeptCode'];
  var value = sel.options[sel.selectedIndex].value;
  var orgSite = document.forms['details'].elements['orgSiteId'].value;
  var url = "TroubleTicketsAllerte.do?command=DepartmentJSList&form=details&dept=Resolved&orgSiteId="+ orgSite + "&populateResolvedBy=true&resolvedByDepartmentCode=" + escape(value);
  window.frames['server_commands'].location.href=url;
}
  function changeDivContent(divName, divContents) {
  if(document.layers){
    // Netscape 4 or equiv.
    divToChange = document.layers[divName];
    divToChange.document.open();
    divToChange.document.write(divContents);
    divToChange.document.close();
  } else if(document.all){
    // MS IE or equiv.
    divToChange = document.all[divName];
    divToChange.innerHTML = divContents;
  } else if(document.getElementById){
    // Netscape 6 or equiv.
    divToChange = document.getElementById(divName);
    divToChange.innerHTML = divContents;
  }
}
function resetNumericFieldValue(fieldId){
  document.getElementById(fieldId).value = -1;
}
function checkForm(form) {
  formTest = true;
  message = "";
 /* <dhv:include name="ticket.contact" none="true">
  if (form.TipoAlimento.value == "-1") {
    message += label("check.campioni.richiedente.selezionatosssd","- Controllare che \"Tipo alimento\" sia stato popolato\r\n");
    formTest = false;
  }
  </dhv:include>*/
  if (form.dataApertura.value == "") {
      message += label("check..data_richiesta.selezionato","- Controllare che il campo \"Data Apertura\" sia stato popolato\r\n");
      formTest = false;
    }

  if(form.ListaCommercializzazione.value=="1"){

	  if(form.oggetto!=null){
		if(form.oggetto.value==""){
			 message += label("check..richiedente.selezionato","- Controllare di aver inserito l'oggetto per il file da allegare\r\n");
		      formTest = false;
			}

			if(form.file.value==""){
				 message += label("check..richiedente.selezionato","- Controllare di aver selezionato il file \r\n");
			      formTest = false;
				}

	  }
		}
  
  if (form.idAllerta.value == "") {
      message += label("check.campioni.richiedente.selezionatosssd","- Controllare che \"Identificativo Allerta\" sia stato popolato\r\n");
      formTest = false;
    }
  <dhv:include name="ticket.contact" none="true">
  if (form.Origine.value == "-1") {
    message += label("check.campioni.richiedente.selezionatosss","- Controllare che \"Origine\" sia stato popolato\r\n");
    formTest = false;
  }
  </dhv:include>


  aslcoinv=document.details.asl_coinvolte;

  flag1 = 0;
	for( i = 0 ; i < aslcoinv.length; i++){
		if(aslcoinv[i].checked == true)
		{
			flag1 = 1;	
			cu = document.getElementById("cu_"+aslcoinv[i].value).value;
			sizeOrg = document.getElementById("size_"+aslcoinv[i].value).value;
		
		if(cu < sizeOrg)
		{
			message += label("check..richiedente.selezionato","- Controllare il numero di controlli inseriti sia proporzionato alle imprese selezionate per le asl coinvolte nell'allerta \r\n");
		     formTest = false;
			break;
		}
		

		 }
	}

	if(flag1 ==0)
	{
		message += label("check..richiedente.selezionato","- Controllare di aver selezionato almeno un asl per l'allerta \r\n");
	     formTest = false;
	}

  
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
  <table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
      <th colspan="2">
        <strong><dhv:label name="sanzioni.informationff">Scheda Allerte</dhv:label></strong>
      </th>
	</tr>
	<dhv:include name="" none="true">
	
	
	 <tr class="containerBody">
      <td name="altr" id="altr" nowrap class="formLabel">
        <dhv:label name="">Lista di Commercializzazione</dhv:label>
      </td>
      <td>
      <input type="hidden" name="id" value="<%=TicketDetails.getId() %>">
      <%if(TicketDetails.getListaCommercializzazione()==2){ %>
      <%ListaCommercializzazione.setJsEvent("onChange=\"javascript:selectLista();\""); %>
         <%= ListaCommercializzazione.getHtmlSelect("ListaCommercializzazione",TicketDetails.getListaCommercializzazione()) %>
      <%}else{ %>
      
      Con
      <input type="hidden" name= "ListaCommercializzazione" value="1">
      <%} %>
      </td>   		
    </tr>
	
	<%if(TicketDetails.getListaCommercializzazione()==2){ %>
	<tr class="containerBody" id="oggetto" style="display: none">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_contacts_calls_details_include.Subject">Subject</dhv:label>
    </td>
    <td>
      <input type="hidden" name="folderId" value="<%= "-1"%>">
      <input type="text" name="subject" size="59" maxlength="255" value="<%= toHtmlValue((String)request.getAttribute("subject")) %>"><font color="red">*</font>
      <%= showAttribute(request, "subjectError") %>
    </td>
  </tr>
  <tr class="containerBody" id="file"  style="display: none">
    <td class="formLabel">
      <dhv:label name="contacts.companydirectory_confirm_importupload.File">File</dhv:label>
    </td>
    <td>
      <input type="file" name="file" size="45">
    </td>
  </tr>
 
	<%} %>
	
	
	
	
  <dhv:evaluate if="<%= SiteIdList.size() > 1 %>">
  <input type="hidden" name="numasl" value="<%=SiteIdList.size() %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      A.S.L. Coinvolte
    </td>
    <td>
        
        <table class="noborder">
        <%Iterator<Integer> it=SiteIdListUtil.keySet().iterator();
        Hashtable<String, AslCoinvolte> ListaBpi6=TicketDetails.getAsl_coinvolte();
		Iterator<String> iteraKiavi6= TicketDetails.getAsl_coinvolte().keySet().iterator();
        while(it.hasNext()){
        	int code=it.next();
       		String descr=(String)SiteIdListUtil.get(code);
        	AslCoinvolte asl=ListaBpi6.get(descr);
        	if(asl!=null){
        		if(code==16){
        						%>
        		<tr>
        			<td><%=descr %>&nbsp;</td><td><input type="checkbox" name="asl_coinvolte" checked="checked" onclick="abilitaCU(<%=code %>)" value="<%=code %>"></td><td id="int_<%=code %>" style="display:none">&nbsp;Numero C.U.&nbsp;</td><td id="cu_<%=code %>" style="display:none"><input type="text" name="cu_<%=code %>" value="<%=asl.getControlliUfficialiRegionaliPianificati() %>"></td>
        			<td id="colonnaFuoriRegione" style="display:none">
        				<textarea rows="8" cols="50" id = "noteFuoriRegione"  name="noteFuoriRegione"  value = "<%=asl.getNoteFuoriRegione() %>"> 
        				<%=toHtml(asl.getNoteFuoriRegione()) %>
        				</textarea> 
        
        				</td>
        				  <td id="orgid_<%=code %>">
        <input type = "hidden" id = "elementi_<%=code %>" name = "elementi_<%=code %>" <%if(TicketDetails.getImpresaCoinvolta(code)!=null){ if (TicketDetails.getImpresaCoinvolta(code).getImpreseCoinvolte()!=null){ %> value = "<%=TicketDetails.getImpresaCoinvolta(code).getImpreseCoinvolte().size() %>" <%}} %>>
   		<input type = "hidden" id = "size_<%=code %>" name = "size_<%=code %>" <%if(TicketDetails.getImpresaCoinvolta(code)!=null){ if (TicketDetails.getImpresaCoinvolta(code).getImpreseCoinvolte()!=null){ %> value = "<%=TicketDetails.getImpresaCoinvolta(code).getImpreseCoinvolte().size() %>" <%}} %> >
   
        <table id="tab_<%=code %>">
        <tr id="row_<%=code %>" style = "display : none"><td><input type = "text" readonly="readonly" name = "pippo_<%=code %>"></td> <td><input type = "text" readonly="readonly" name = "indirizzo_<%=code %>"></td><td>[<a href="javascript:popLookupSelectorAllerteImpreseElimina()" id="elimina_<%=code %>">[elimina]</a></td></tr>
        
        <%
        int indice =1;
        if(TicketDetails.getImpresaCoinvolta(code)!=null)
        {
        	
        	 if(TicketDetails.getImpresaCoinvolta(code).getIndirizziImpreseCoinvolte()!=null)
             {
        ArrayList<String > indirizzi =TicketDetails.getImpresaCoinvolta(code).getIndirizziImpreseCoinvolte();
        for (String s : TicketDetails.getImpresaCoinvolta(code).getImpreseCoinvolte())
        {
        	if(!s.equals("")){
        %>
         <tr id="row_<%=code %>_<%=indice %>"><td><input type = "text" readonly="readonly" name = "pippo_<%=code %>" value = "<%=s %>"></td> <td><input type = "text" readonly="readonly" name = "indirizzo_<%=code %>" value = "<%=indirizzi.get(indice) %>"></td><td>[<a href="javascript:popLookupSelectorAllerteImpreseElimina()" id="elimina_<%=code %>">[elimina]</a></td></tr>
        indice++;
        <%} }
             }}
        %>
        
        </table>
        
        </td>
        
        <td id = "select_<%=code %>" >
          &nbsp;[<a href="javascript:popLookupSelectorAllerteImprese('codiceFiscaleCorrentista','alertText','organization','',<%=code %>);">Aggiungi Impresa</a>]
        
        </td>
        
        
        <td id = "selectstabilimenti_<%=code %>" >
          &nbsp;[<a href="javascript:popLookupSelectorAllerteStabilimenti('codiceFiscaleCorrentista','alertText','organization','',<%=code %>);"><dhv:label name="">Aggiungi Stabilimento</dhv:label></a>]
        
        </td>
      
        
        </tr>
        <%}else{ %>
        <tr><td><%=descr %>&nbsp;</td><td><input type="checkbox" name="asl_coinvolte" checked="checked" onclick="abilitaCU(<%=code %>)" value="<%=code %>"></td><td id="int_<%=code %>" style="display:none">&nbsp;Numero C.U.&nbsp;</td><td id="cu_<%=code %>" style="display:none"><input type="text" name="cu_<%=code %>" value="<%=asl.getControlliUfficialiRegionaliPianificati() %>"></td>
        <td>&nbsp;</td>
        
          <td id="orgid_<%=code %>">
       <input type = "hidden" id = "elementi_<%=code %>" name = "elementi_<%=code %>" <%if(TicketDetails.getImpresaCoinvolta(code)!=null){ if(TicketDetails.getImpresaCoinvolta(code).getImpreseCoinvolte()!=null){ %> value = "<%=TicketDetails.getImpresaCoinvolta(code).getImpreseCoinvolte().size() %>" <%}} %>>
   		<input type = "hidden" id = "size_<%=code %>" name = "size_<%=code %>" <%if(TicketDetails.getImpresaCoinvolta(code)!=null){ if(TicketDetails.getImpresaCoinvolta(code).getImpreseCoinvolte()!=null){ %> value = "<%=TicketDetails.getImpresaCoinvolta(code).getImpreseCoinvolte().size() %>" <%}} %>>
   
        <table id="tab_<%=code %>">
        <tr id="row_<%=code %>" style = "display : none"><td><input type = "text" readonly="readonly" name = "pippo_<%=code %>"></td> <td><input type = "text" readonly="readonly" name = "indirizzo_<%=code %>"></td><td>[<a href="javascript:popLookupSelectorAllerteImpreseElimina()" id="elimina_<%=code %>">[elimina]</a></td></tr>
         <%
        int indice =1;
       
        if(TicketDetails.getImpresaCoinvolta(code)!=null)
        {
        	
        if(TicketDetails.getImpresaCoinvolta(code).getIndirizziImpreseCoinvolte()!=null)
        {
        ArrayList<String > indirizzi =TicketDetails.getImpresaCoinvolta(code).getIndirizziImpreseCoinvolte();
        for (String s : TicketDetails.getImpresaCoinvolta(code).getImpreseCoinvolte())
        {
        	if(!s.equals("")){
        %>
         <tr id="row_<%=code %>_<%=indice %>"><td><input type = "text" readonly="readonly" name = "pippo_<%=code %>" value = "<%=s %>"></td> <td><input type = "text" readonly="readonly" name = "indirizzo_<%=code %>" value = "<%=indirizzi.get(indice) %>"></td><td>[<a href="javascript:popLookupSelectorAllerteImpreseElimina('<%=code %>','<%=indice%>')" id="elimina_<%=code %>">[elimina]</a></td></tr>
        
        <%
        	}
        }
        }}
        
         indice++;%>
        
        </table>
        
        </td>
        
        <td id = "select_<%=code %>" >
          &nbsp;[<a href="javascript:popLookupSelectorAllerteImprese('codiceFiscaleCorrentista','alertText','organization','',<%=code %>);"><dhv:label name="">Aggiungi Impresa</dhv:label></a>]
        
        </td>
        
        <td id = "selectstabilimenti_<%=code %>" >
          &nbsp;[<a href="javascript:popLookupSelectorAllerteStabilimenti('codiceFiscaleCorrentista','alertText','organization','',<%=code %>);"><dhv:label name="">Aggiungi Stabilimento</dhv:label></a>]
        
        </td>
        </tr>
        <%} %>
        
        
        
        <%} else{
        	
        	if(code == 16) {
        	%>
        	
        	
        	 <tr><td><%=descr %>&nbsp;</td><td><input type="checkbox" name="asl_coinvolte"  onclick="abilitaCU(<%=code %>)" value="<%=code %>"></td><td id="int_<%=code %>" style="display:none">&nbsp;Numero C.U.&nbsp;</td><td id="cu_<%=code %>" style="display:none"><input type="text" name="cu_<%=code %>" ></td>
        	 
        	 <td id="colonnaFuoriRegione" style="display:none">
       
          <textarea rows="8" cols="50" id = "noteFuoriRegione"  name="noteFuoriRegione" > 
       
        		
        </textarea> 
        
        </td>
        	 
        	   <td id="orgid_<%=code %>">
        <input type = "hidden" id = "elementi_<%=code %>" name = "elementi_<%=code %>" value = "0">
   		<input type = "hidden" id = "size_<%=code %>" name = "size_<%=code %>" value = "0">
   
        <table id="tab_<%=code %>">
        <tr id="row_<%=code %>" style = "display : none"><td><input type = "text" readonly="readonly" name = "pippo_<%=code %>"></td> <td><input type = "text" readonly="readonly" name = "indirizzo_<%=code %>"></td><td>[<a href="javascript:popLookupSelectorAllerteImpreseElimina()" id="elimina_<%=code %>">[elimina]</a></td></tr>
        </table>
        
        </td>
        
        <td id = "select_<%=code %>" style="display: none">
          &nbsp;[<a href="javascript:popLookupSelectorAllerteImprese('codiceFiscaleCorrentista','alertText','organization','',<%=code %>);"><dhv:label name="">Aggiungi Impresa</dhv:label></a>]
        
        </td>
        
        <td id = "selectstabilimenti_<%=code %>" style="display: none">
          &nbsp;[<a href="javascript:popLookupSelectorAllerteStabilimenti('codiceFiscaleCorrentista','alertText','organization','',<%=code %>);"><dhv:label name="">Aggiungi Stabilimento</dhv:label></a>]
        
        </td>
        	 
        	 </tr>
       
        	
        	<%
        }else{
        	
        	%>
        	<tr><td><%=descr %>&nbsp;</td><td><input type="checkbox" name="asl_coinvolte"  onclick="abilitaCU(<%=code %>)" value="<%=code %>"></td><td id="int_<%=code %>" style="display:none">&nbsp;Numero C.U.&nbsp;</td><td id="cu_<%=code %>" style="display:none"><input type="text" name="cu_<%=code %>" ></td>  <td>&nbsp;</td>
        	
        	  <td id="orgid_<%=code %>">
       <input type = "hidden" id = "elementi_<%=code %>" name = "elementi_<%=code %>" value = "0">
   		<input type = "hidden" id = "size_<%=code %>" name = "size_<%=code %>" value = "0">
   
        <table id="tab_<%=code %>">
        <tr id="row_<%=code %>" style = "display : none"><td><input type = "text" readonly="readonly" name = "pippo_<%=code %>"></td> <td><input type = "text" readonly="readonly" name = "indirizzo_<%=code %>"></td><td>[<a href="javascript:popLookupSelectorAllerteImpreseElimina()" id="elimina_<%=code %>">[elimina]</a></td></tr>
        </table>
        
        </td>
        
        <td id = "select_<%=code %>" style="display: none">
          &nbsp;[<a href="javascript:popLookupSelectorAllerteImprese('codiceFiscaleCorrentista','alertText','organization','',<%=code %>);"><dhv:label name="">Aggiungi Impresa</dhv:label></a>]
        
        </td>
        
        <td id = "selectstabilimenti_<%=code %>" style="display: none">
          &nbsp;[<a href="javascript:popLookupSelectorAllerteStabilimenti('codiceFiscaleCorrentista','alertText','organization','',<%=code %>);"><dhv:label name="">Aggiungi Stabilimento</dhv:label></a>]
        
        </td>
        	</tr>
        	
        	<%
        	
        }
        	
        }
        
        
        }%>
        
        
        </table>
        
        </td>
      		
      		
      		
      		
    
  </tr>
  </dhv:evaluate> 
  <dhv:evaluate if="<%= SiteIdList.size() <= 1 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
  </dhv:evaluate>
</dhv:include>
<input type="hidden" name="id" value="<%=TicketDetails.getId()%>" >
	
	
	<input type="hidden" name="orgId" id="orgId" value="<%=  TicketDetails.getOrgId() %>" />
	
    
        <tr class="containerBody">
      <td  id="nonConf" nowrap class="formLabel">
        <dhv:label name="">Azione Non Conforme Per </dhv:label>
        
      </td>
     <td>
    <table class="noborder">
    <tr>
    <td >
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
 %>
 
<%
// setto i valori de defauls èper le liste multiple
   HashMap<Integer,String> ListaBpi6= TicketDetails.getTipiAlimentiVegetali();
      Iterator<Integer>iteraKiavi6=ListaBpi6.keySet().iterator();
      LookupList multipleSelects6=new LookupList();
  TipoCampione_sottochimico.setSelectStyle("display:none");
  TipoCampione_sottochimico2.setSelectStyle("display:none");
  TipoCampione_sottochimico3.setSelectStyle("display:none");
  TipoCampione_sottochimico4.setSelectStyle("display:none");
  TipoCampione_sottochimico5.setSelectStyle("display:none");
  LookupList batteriDefautItem=new LookupList();
	 multipleSelects6=new LookupList();
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
    
    
    <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="">Identificativo Allerta</dhv:label>
    </td>
    <td>
         <input type="text" name="idAllerta"  id="idAllerta" size="10" maxlength="50" value = "<%= TicketDetails.getIdAllerta() %>"/><font color="red">*</font> <%= showAttribute(request, "assignedDateError") %>
    </td> 
    </tr>
	<tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="sanzioni.data_richiestasss">Data Apertura</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="details" field="dataApertura" timestamp="<%= TicketDetails.getDataApertura() %>"  timeZone="<%= TicketDetails.getDataAperturaTimeZone() %>" showTimeZone="false" />
        <font color="red">*</font> <%= showAttribute(request, "assignedDateError") %>
      </td>
    </tr>
    
   
    <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name=".data_richiestas">Gestione Pregresso</dhv:label>
    </td>
    <td>
    <table class = "noborder">
    <tr>
    <td>NO <input type ="radio" name = "pregresso" value ="no" onclick="abilitaData('no')" <%if(TicketDetails.getIsPregresso().equals("no")){ %>checked="checked" <%} %>>  Si <input type ="radio" name = "pregresso" value ="si" onclick="abilitaData('si')" <%if(TicketDetails.getIsPregresso().equals("si")){ %>checked="checked" <%} %>></td>
    
    <td style="display: none" id = "colonnaChiusura">&nbsp; Data Chiusura &nbsp; <input type = "text" name="dataChiusura"   ></td>
    
    </tr>
    
    </table>
    
      
     
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
	 
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.importo">Alimenti di origine animale</dhv:label>
    </td>
   
     <td>    
    <table class="noborder">
    <tr>
     <td>
     <%
     
     if( TicketDetails.getAlimentiOrigineAnimale() == true ){%>
       <input type="checkbox" name="alimentiOrigineAnimale" id="alimentiOrigineAnimale" size="20" maxlength="256" checked="checked" id="alimentiOrigineAnimale" onclick="abilitaTipoAlimentoAnimale()"/>
       <%} else {%>
       <input type="checkbox" name="alimentiOrigineAnimale" id="alimentiOrigineAnimale" size="20" maxlength="256" onclick="abilitaTipoAlimentoAnimale()"/>
       <%} %>
     </td>
     
     
     <td>
     <select name="tipoAlimentiAnimali" id="tipoAlimentiAnimali" onchange="abilitaLookupOrigineAnimale(this.form)" >
     <option value="-1" >--SELEZIONA UNA VOCE</option>
     <option value="1" <%if(TicketDetails.getAlimentiOrigineAnimaleNonTrasformati()!=-1){ %>selected="selected" <%} %>>Alimenti Non Trasformati</option>
     <option value="2" <%if(TicketDetails.getAlimentiOrigineAnimaleTrasformati()!=-1){ %>selected="selected" <%} %>>Alimenti Trasformati</option>
     </select>
     
     
     </td>
     
       <td id="lookupNonTrasformati" style="padding: 5">
       <% AlimentiNonTrasformati.setJsEvent("onchange=\"javascript:abilitaSpecie(this.form);\"");%>
      <%= AlimentiNonTrasformati.getHtmlSelect("alimentiOrigineAnimaleNonTrasformati",TicketDetails.getAlimentiOrigineAnimaleNonTrasformati()) %>
   
     <%  AlimentiTrasformati.setJsEvent("onchange=\"javascript:disabilitaNonTrasformati(this.form);\"");%>
     <%= AlimentiTrasformati.getHtmlSelect("alimentiOrigineAnimaleTrasformati",TicketDetails.getAlimentiOrigineAnimaleTrasformati()) %>
   
      </td > 
     <td >
       <%= TipoSpecie_latte.getHtmlSelect("TipoSpecie_latte",TicketDetails.getTipSpecie_latte()) %>
      <%= TipoSpecie_uova.getHtmlSelect("TipoSpecie_uova",TicketDetails.getTipSpecie_uova()) %>
     
      <%= AlimentiNonTrasformatiValori.getHtmlSelect("alimentiOrigineAnimaleNonTrasformatiValori",TicketDetails.getAlimentiOrigineAnimaleNonTrasformatiValori()) %>
     </td>
    <td style="display:none" id="notealimenti">
      Note :
     <textarea rows="8" cols="40" name="notealimenti" value=""<%=TicketDetails.getNoteAlimenti() %>><%=TicketDetails.getNoteAlimenti() %></textarea>
     
     </td>
    </tr>
    </table>
    </td> 
  </tr><!-- chiusura tabella interna -->
  
  <!-- alimenti origine vegetale -->
   <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.importo">Alimenti di origine vegetale</dhv:label>
    </td>
     <td>    
    <table class="noborder">
    <tr>
     <td>
     <%
    
     
     
     if( TicketDetails.getAlimentiOrigineVegetale() == true ){%>
       <input type="checkbox" name="alimentiOrigineVegetale" id="alimentiOrigineVegetale" size="20" maxlength="256" checked="checked"  onclick="abilitaLookupOrigineVegetale()"/>
       <%} else {%>
              <input type="checkbox" name="alimentiOrigineVegetale" id="alimentiOrigineVegetale" onclick="abilitaLookupOrigineVegetale()" size="20" maxlength="256" />
       
       <%} %>
     </td>
      <td id="lookupVegetale">
      <select name="tipoAlimento" onchange="mostraTrasformati()">
       <option value="-1" <%if(TicketDetails.getIsvegetaletrasformato()==-1)  {%>selected="selected" <%} %>>--Seleziona Voce--</option>
      <option value="0" <%if(TicketDetails.getIsvegetaletrasformato()==-0)  {%>selected="selected" <%} %> >Non Trasformati</option>
      <option value="1"<%if(TicketDetails.getIsvegetaletrasformato()==1)  {%>selected="selected" <%} %> >Trasformati</option>
      </select>
      </td>
      <td id="lookupVegetale1">
      <%
      AlimentiVegetaliNonTraformati.setSelectStyle("display:none");
      AlimentiVegetaliNonTraformati .setJsEvent("onchange=\"javascript: mostraSottoCategoria();\""); %>
      
      <%= AlimentiVegetaliNonTraformati.getHtmlSelect("alimentiOrigineVegetaleValoriNonTrasformati",TicketDetails.getAlimentiOrigineVegetaleValori()) %>
      
       <%
       AlimentiVegetaliTraformati.setSelectStyle("display:none");
       AlimentiVegetaliTraformati .setJsEvent("onchange=\"javascript: mostraSottoCategoria();\""); %>
      
      <%= AlimentiVegetaliTraformati.getHtmlSelect("alimentiOrigineVegetaleValoriTrasformati",TicketDetails.getAlimentiOrigineVegetaleValori()) %>
      
      </td>
      <td>
      <%
      
    
      while(iteraKiavi6.hasNext()){
			int kiave1=iteraKiavi6.next();
			String valore1=ListaBpi6.get(kiave1);
			multipleSelects6.addItem(kiave1,valore1);
		}
      
      if(TicketDetails.getAlimentiOrigineVegetaleValori()==1){
    	  FruttaFresca.setMultipleSelects(multipleSelects6);
    	  
      }else{
    	  if(TicketDetails.getAlimentiOrigineVegetaleValori()==2){
        	  FruttaSecca.setMultipleSelects(multipleSelects6);
        	  
          } else{
        	  if(TicketDetails.getAlimentiOrigineVegetaleValori()==4){
        		  Ortaggi.setMultipleSelects(multipleSelects6);
            	  
              }else{
            	  if(TicketDetails.getAlimentiOrigineVegetaleValori()==5){
            		  Funghi.setMultipleSelects(multipleSelects6);
                	  
                  }else{
                	  if(TicketDetails.getAlimentiOrigineVegetaleValori()==6){
                		  Derivati.setMultipleSelects(multipleSelects6);
                    	  
                      }else{
                    	  if(TicketDetails.getAlimentiOrigineVegetaleValori()==7){
                        	  
                    		  Conservati.setMultipleSelects(multipleSelects6);
                          }else{
                        	  if(TicketDetails.getAlimentiOrigineVegetaleValori()==8){
                            	  
                        		  Grassi.setMultipleSelects(multipleSelects6);
                              }else{
                            	  if(TicketDetails.getAlimentiOrigineVegetaleValori()==9){
                                	  
                            		  Vino.setMultipleSelects(multipleSelects6);
                                  }else{
                                	  if(TicketDetails.getAlimentiOrigineVegetaleValori()==11){
                                    	  
                                		  Zuppe.setMultipleSelects(multipleSelects6);
                                      }
                                  }
                            	  
                              }
                        	  
                        	  
                        	  
                        	  
                          }
                      }
                	  
                  }
            	  
              }
        	  
          } 
    	  
      }
      
      
      
      
      Ortaggi.setSelectStyle("display:none");
      FruttaFresca.setSelectStyle("display:none");
      FruttaSecca.setSelectStyle("display:none");
      Funghi.setSelectStyle("display:none");
      Derivati.setSelectStyle("display:none");
      Conservati.setSelectStyle("display:none");
      Grassi.setSelectStyle("display:none");
      Vino.setSelectStyle("display:none");
      Zuppe.setSelectStyle("display:none");
      %>
      
     <%= FruttaFresca.getHtmlSelect("fruttaFresca",-1) %>
       <%= FruttaSecca.getHtmlSelect("fruttaSecca",-1) %>
         <%= Ortaggi.getHtmlSelect("ortaggi",-1) %>
         <%= Funghi.getHtmlSelect("funghi",-1) %>
           <%= Derivati.getHtmlSelect("derivati",-1) %>
           <%= Conservati.getHtmlSelect("conservati",-1) %>
      <%= Grassi.getHtmlSelect("grassi",-1) %>
       <%= Vino.getHtmlSelect("vino",-1) %>
        <%= Zuppe.getHtmlSelect("zuppe",-1) %>
   
   
      </td>
      
      <td style="display:none" id="notealimenti2">
      Note :
     <textarea rows="8" cols="40" name="notealimenti2" value="<%=TicketDetails.getNoteAlimenti() %>"><%=TicketDetails.getNoteAlimenti() %></textarea>
     
     </td>
      </tr>
      </table> <!--  chiusura tabella alimenti vegetali -->
     </td>
     </tr>
  
  <tr class="containerBody">
        <td valign="top" class="formLabel">
          <dhv:label name="sanzionia.importo">Altri Alimenti di origine non animale</dhv:label>
       </td>
       <td>
         <table class="noborder">
          <tr>
          
           <td>
             <% if( TicketDetails.isAltriAlimenti() == true ){%>
        <input type="checkbox" name="alimentinonAnomali"  id="alimentinonAnimali" onclick="abilitaalimentinonAnimali()"  size="20" maxlength="256" checked="checked" /></td>
       <%} else {%>
           <input type="checkbox" name="alimentinonAnomali"  id="alimentinonAnimali" onclick="abilitaalimentinonAnimali()"  size="20" maxlength="256" /></td>
     
       <%} %>
           
        
         <td style="display:none" id="alimentinonanimalicella">
         <%=AltriAlimenti.getHtmlSelect("altrialimenti",TicketDetails.getAltrialimenti()) %>
      <center>Descrizione</center>
     <textarea rows="8" cols="40" name="descrizionenonAnimali" value="<%=TicketDetails.getNoteAlimenti() %>" id="testoalimentinonanimali"></textarea>
     </td>

         </tr>
       </table>
       </td>
   </tr>
  
   <!-- alimenti composti -->
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.importo">Alimenti composti</dhv:label>
    </td>
    <td>
      <% if( TicketDetails.getAlimentiComposti() == true ){%>
       <input type="checkbox" name="alimentiComposti" id="alimentiComposti" size="20" maxlength="256" onclick="abilitaTestoAlimentoComposto()" checked="checked"/>
       <input type="text" name="testoAlimentoComposto" id="testoAlimentoComposto"  value="<%= toHtmlValue(TicketDetails.getNoteAlimenti()) %>" size="20" maxlength="256" />
       <%} else {%>
          <input type="checkbox" name="alimentiComposti" id="alimentiComposti" size="20" maxlength="256" onclick="abilitaTestoAlimentoComposto()" />    
          <input type="text" name="testoAlimentoComposto" id="testoAlimentoComposto"  value="<%= toHtmlValue(TicketDetails.getNoteAlimenti()) %>" size="20" maxlength="256" />
     
       <%} %>
     </td>
     </tr>
     
     
     
    <tr class="containerBody">
        <td valign="top" class="formLabel">
          <dhv:label name="sanzionia.importo">Acqua</dhv:label>
       </td>
       <td>
         <table class="noborder">
          <tr>
           
            <% if( TicketDetails.getAlimentiAcqua() == true ){%>
           <td>
           <input type="checkbox" name="alimentiAcqua"  id="acqua" onclick="abilitaAcque()" checked="checked"  size="20" maxlength="256" />
     </td>
     
     <td style="display:none" id="acquaSelect">
         <%= Acque.getHtmlSelect("acque",TicketDetails.getTipoAcque()) %>
     
     </td>
           <td style="display:none" id="noteacqua">
     <center>Descrizione:</center>
     <textarea rows="8" cols="40" name="noteacqua"  value="<%=TicketDetails.getNoteAlimenti() %>"><%=TicketDetails.getNoteAlimenti() %></textarea>
     
     </td>
         </tr>
       </table>
       </td>
     
     
      <%}else{ %>
       <td><input type="checkbox" name="alimentiAcqua"  id="acqua" onclick="abilitaAcque()"  size="20" maxlength="256" />
      </td>
      <td style="display:none" id="acquaSelect">
         <%= Acque.getHtmlSelect("acque",TicketDetails.getTipoAcque()) %>
     
     </td>
           <td style="display:none" id="noteacqua">
     <center>Descrizione:</center>
     <textarea rows="8" cols="40" name="noteacqua"></textarea>
     
     </td>
         </tr>
       </table>
       </td>
      <%} %>
               
   
     
   </tr>
   
   <tr class="containerBody">
        <td valign="top" class="formLabel">
          <dhv:label name="sanzionia.importo">Bevande</dhv:label>
       </td>
       <td>
         <table class="noborder">
          <tr>
           
             <% if( TicketDetails.getAlimentiBevande() == true ){%>
          <td>
           <input type="checkbox" name="alimentiBevande"  id="bevande" onclick="abilitaBevande()" checked="checked"  size="20" maxlength="256" />
            </td>
             <td style="display:none" id="notebevande">
      Descrizione: <br>
     <textarea rows="8" cols="40" name="notebevande" id="notealimentibevande" value="<%=TicketDetails.getNoteAlimenti() %>"><%=TicketDetails.getNoteAlimenti() %></textarea>
     
     </td>
         </tr>
       </table>
       </td>
            
           <%}else{ %>
            <td>
            <input type="checkbox" name="alimentiBevande"  id="bevande" onclick="abilitaBevande()"  size="20" maxlength="256" />
           </td>
            <td style="display:none" id="notebevande">
      Descrizione:
     <textarea rows="8" cols="40" name="notebevande" id="notealimentibevande"></textarea>
     
     </td>
         </tr>
       </table>
       </td>
           <%} %>
          
          
   
     
   </tr>
  
   
 <tr class="containerBody">
        <td valign="top" class="formLabel">
          <dhv:label name="sanzionia.importo">Alimenti per uso Zootecnico</dhv:label>
       </td>
       <td>
         <table class="noborder">
          <tr>
           
             <% if( TicketDetails.isMangimi() == true ){%>
          <td>
           <input type="checkbox" name="alimentiMangimi"  id="mangimi" onclick="abilitaMangimi()" checked="checked"  size="20" maxlength="256" />
            </td>
             <td style="display:none" id="notealimentimangimi">
      Descrizione: <br>
     <textarea rows="8" cols="40" name="notebevande" id="notealimenti" value="<%=TicketDetails.getNoteAlimenti() %>"><%=TicketDetails.getNoteAlimenti() %></textarea>
     
     </td>
         </tr>
       </table>
       </td>
            
           <%}else{ %>
            <td>
            <input type="checkbox" name="alimentiMangimi"  id="mangimi" onclick="abilitaMangimi()"  size="20" maxlength="256" />
           </td>
            <td style="display:none" id="notealimentimangimi">
      Descrizione:
     <textarea rows="8" cols="40" name="notealimenti" id="notealimenti"></textarea>
     
     </td>
         </tr>
       </table>
       </td>
           <%} %>
          
          
   
     
   </tr>
   
   
   <tr class="containerBody">
        <td valign="top" class="formLabel">
          <dhv:label name="sanzionia.importo">Additivi</dhv:label>
       </td>
       <td>
         <table class="noborder">
          <tr>
           
              <% if( TicketDetails.getAlimentiAdditivi() == true ){%>
           <td>
           <input type="checkbox" name="alimentiAdditivi" onclick="abilitatipoAdditivi()" checked="checked" id="additivi"  size="20" maxlength="256" />
          </td>
           
           <td style="display:none" id="noteadditivi">
      Descrizione:<br>
     <textarea rows="8" cols="40" name="noteadditivi" value="<%=TicketDetails.getNoteAlimenti() %>"><%=TicketDetails.getNoteAlimenti() %></textarea>
     
     </td>
         </tr>
       </table>
       </td>
          
           <%}else{ %>
           <td> <input type="checkbox" name="alimentiAdditivi" onclick="abilitatipoAdditivi()" id="additivi"  size="20" maxlength="256" />
          </td>
           
           <td style="display:none" id="noteadditivi">
      Descrizione:
     <textarea rows="8" cols="40" name="noteadditivi" ></textarea>
     
     </td>
         </tr>
       </table>
       </td>
           <%} %>
           
          
   
     
   </tr>
   
   
   <tr class="containerBody">
        <td valign="top" class="formLabel">
          <dhv:label name="sanzionia.importo">Materiali a Contatto con Alimenti</dhv:label>
       </td>
       <td>
         <table  class="noborder">
          <tr>
           <td>
                <% if( TicketDetails.getMaterialiAlimenti() == true ){%>
           <input type="checkbox" name="materialiAlimenti" onclick="abilitatipomaterialiAlimenti()" id="materialialimenti" checked="checked" size="20" maxlength="256" />
         
         </td>
         
         <td style="display:none" id="notematerialialimenti">
      Descrizione:<br>
     <textarea rows="8" cols="40" name="notematerialialimenti" value="<%=TicketDetails.getNoteAlimenti() %>"><%=TicketDetails.getNoteAlimenti() %></textarea>
     
     </td>
         </tr>
       </table>
       </td>
         
           <%}else{ %>
         <td>   <input type="checkbox" name="materialiAlimenti" onclick="abilitatipomaterialiAlimenti()" id="materialialimenti"   size="20" maxlength="256" />
           
            </td>
            <td style="display:none" id="notematerialialimenti">
      Descrizione:
     <textarea rows="8" cols="40" name="notematerialialimenti" ></textarea>
     
     </td>
         </tr>
       </table>
       </td>
            
           <%} %>
           
          
           
   
     
   </tr>
    
	
	</table>
	
&nbsp;<br>
<input type="hidden" name="orgSiteId" value="<%=  TicketDetails.getOrgSiteId() %>" />
<input type="hidden" name="currentDate" value="<%=  request.getAttribute("currentDate") %>" />
<input type="hidden" name="statusId" value="<%=  TicketDetails.getStatusId() %>" />
<input type="hidden" name="trashedDate" value="<%=  TicketDetails.getTrashedDate() %>" />

  
    <% if (TicketDetails.getClosed() != null) { %>
     <%-- %> <input type="button" value="<dhv:label name="button.reopen">Reopen</dhv:label>" onClick="javascript:this.form.action='TroubleTicketsAllerte.do?command=Reopen&ticketId=<%= TicketDetails.getId()%><%= defectCheck != null && !"".equals(defectCheck.trim()) ?"&defectCheck="+defectCheck:"" %>';submit();">
      <input type="submit" value="Annulla" onClick='<%= (!isPopup(request)?"javascript:this.form.action='TroubleTicketsAllerte.do?command=Details&id="+ TicketDetails.getId() +(defectCheck != null && !"".equals(defectCheck.trim()) ?"&defectCheck="+defectCheck:"")+"'":"javascript:window.close();") %>' />--%>
    <%} else {%>
      <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="return checkForm(this.form)">
      <% if ("list".equals(request.getParameter("return"))) {%>
        <input type="submit" value="Annulla" onClick='<%= (!isPopup(request)?"javascript:this.form.action='TroubleTicketsAllerte.do?command=Home'":"javascript:window.close();") %>' />
      <%} else if ("searchResults".equals(request.getParameter("return"))){%>
        <input type="submit" value="Annulla" onClick='<%= (!isPopup(request)?"javascript:this.form.action='TroubleTicketsAllerte.do?command=SearchTickets'":"javascript:window.close();") %>' />
      <%} else {%>
        <input type="submit" value="Annulla" onClick='<%= (!isPopup(request)?"javascript:this.form.action='TroubleTicketsAllerte.do?command=Details&id="+ TicketDetails.getId()+(defectCheck != null && !"".equals(defectCheck.trim()) ?"&defectCheck="+defectCheck:"") +"'":"javascript:window.close();") %>' />
      <%}%>
  <%}%>
</dhv:container>
</form>
</body>