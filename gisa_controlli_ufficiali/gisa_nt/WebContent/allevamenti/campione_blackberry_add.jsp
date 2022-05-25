<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.campioni.base.*" %>
<%@ include file="../initPage.jsp" %>

<%@page import="java.sql.Date"%><jsp:useBean id="OrgDetails" class="org.aspcfs.modules.allevamenti.base.Organization" scope="request"/>
<body onload="controlloLookup();impostaResponsabilita();">
<form name="addticket" action="AllevamentiCampioni.do?command=Insert&auto-populate=true" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0" >
<tr>
<td>
  <a href="Allevamenti.do"><dhv:label name="">Allevamenti</dhv:label></a> > 
  <a href="Allevamenti.do?command=Search"><dhv:label name="">Risultati Ricerca</dhv:label></a> >
  <a href="Allevamenti.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Scheda Allevamento</dhv:label></a> >
  <a href="Allevamenti.do?command=ViewVigilanza&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Controlli Ufficiali</dhv:label></a> >
  <a href="AllevamentiVigilanza.do?command=TicketDetails&id=<%= request.getAttribute("idC")%>&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Controllo Ufficiale</dhv:label></a> >
 
  <%--a href="Accounts.do?command=ViewCampioni&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="campioni">Tickets</dhv:label></a> --%>
  <dhv:label name="campioni.aggiungi">Aggiungi Campione</dhv:label>
</td>
</tr>
</table >
<%-- End Trails --%>
<input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save" onClick="return checkForm(this.form)">
<input type="button" value="Annulla" onClick="window.location.href='AccountVigilanza.do?command=TicketDetails&id=<%= request.getAttribute("idC") %>&orgId=<%=OrgDetails.getOrgId()%>';this.form.dosubmit.value='false';" />
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
<jsp:useBean id="EsitoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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
<jsp:useBean id="FruttaFresca" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="FruttaSecca" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Funghi" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Ortaggi" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Derivati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Conservati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Grassi" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Vino" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Zuppe" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiNonTrasformati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiNonTrasformatiValori" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ConseguenzePositivita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ResponsabilitaPositivita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiTrasformati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CampioneBlackBerry" class="org.aspcfs.modules.blackberry.base.CampioniBlackBerry" scope="request"/>

<jsp:useBean id="AltriAlimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiVegetaliNonTraformati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiVegetaliTraformati" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="TipoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>


<jsp:useBean id="TipoCampione_batteri" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoCampione_virus" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoCampione_parassiti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoCampione_chimico" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="TipoCampione_sottochimico" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="TipoCampione_sottochimico2" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Acque" class="org.aspcfs.utils.web.LookupList" scope="request"/>


<jsp:useBean id="TipoCampione_sottochimico3" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="TipoCampione_sottochimico4" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="TipoCampione_sottochimico5" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoSpecie_latte" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoSpecie_uova" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="resolvedByDeptList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.campioni.base.Ticket" scope="request"/>
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
<jsp:useBean id="OrgList" class="org.aspcfs.modules.allevamenti.base.OrganizationList" scope="request"/>
<jsp:useBean id="SubList1" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList2" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList3" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="resolvedUserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="actionPlans" class="org.aspcfs.modules.actionplans.base.ActionPlanList" scope="request"/>
<jsp:useBean id="insertActionPlan" class="java.lang.String" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="defectSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popServiceContracts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAssets.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProducts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></SCRIPT>
<script language="JavaScript">
//abilita il componente ed il nucleo



function impostaResponsabilita(){


	punteggio=document.addticket.punteggio.value;
	if(punteggio==0){
	document.addticket.responsabilitaPositivita.value="1";
	}else
		document.addticket.responsabilitaPositivita.value="-1";

	
}


function mostraSottoCategoria(){

	if(document.addticket.tipoAlimento.value=="0")
 var righe=document.addticket.alimentiOrigineVegetaleValoriNonTrasformati.value;
	else
		var righe=document.addticket.alimentiOrigineVegetaleValoriTrasformati.value;

	
 
	
	if(righe=="1" ){
		document.addticket.fruttaFresca.style.display="block";
		document.addticket.fruttaSecca.style.display="none";
		document.addticket.ortaggi.style.display="none";
		document.addticket.funghi.style.display="none";
		document.addticket.derivati.style.display="none";
		document.addticket.conservati.style.display="none";
		document.addticket.grassi.style.display="none";
		document.addticket.vino.style.display="none";
		document.addticket.zuppe.style.display="none";

		}else{
			if(righe=="2" ){

				document.addticket.fruttaFresca.style.display="none";
				document.addticket.fruttaSecca.style.display="block";
				document.addticket.ortaggi.style.display="none";
				document.addticket.funghi.style.display="none";
				document.addticket.derivati.style.display="none";
				document.addticket.conservati.style.display="none";
				document.addticket.grassi.style.display="none";
				document.addticket.vino.style.display="none";
				document.addticket.zuppe.style.display="none";


			}else{
				if(righe=="4" ){
					document.addticket.fruttaFresca.style.display="none";
					document.addticket.fruttaSecca.style.display="none";
					document.addticket.ortaggi.style.display="block";
					document.addticket.funghi.style.display="none";
					document.addticket.derivati.style.display="none";
					document.addticket.conservati.style.display="none";
					document.addticket.grassi.style.display="none";
					document.addticket.vino.style.display="none";
					document.addticket.zuppe.style.display="none";

				}
				else{
					if(righe=="5" ){
						document.addticket.fruttaFresca.style.display="none";
						document.addticket.fruttaSecca.style.display="none";
						document.addticket.ortaggi.style.display="none";
						document.addticket.funghi.style.display="block";
						document.addticket.derivati.style.display="none";
						document.addticket.conservati.style.display="none";
						document.addticket.grassi.style.display="none";
						document.addticket.vino.style.display="none";
						document.addticket.zuppe.style.display="none";

					}else{
						if(righe=="6" ){
							document.addticket.fruttaFresca.style.display="none";
							document.addticket.fruttaSecca.style.display="none";
							document.addticket.ortaggi.style.display="none";
							document.addticket.funghi.style.display="none";
							document.addticket.conservati.style.display="block";
							document.addticket.derivati.style.display="none";
							document.addticket.grassi.style.display="none";
							document.addticket.vino.style.display="none";
							document.addticket.zuppe.style.display="none";

						}else{
							if(righe=="7" ){
								document.addticket.fruttaFresca.style.display="none";
								document.addticket.fruttaSecca.style.display="none";
								document.addticket.ortaggi.style.display="none";
								document.addticket.funghi.style.display="none";
								document.addticket.derivati.style.display="block";
								document.addticket.conservati.style.display="none";
								document.addticket.grassi.style.display="none";
								document.addticket.vino.style.display="none";
								document.addticket.zuppe.style.display="none";

							}else{

								if(righe=="8" ){
									document.addticket.fruttaFresca.style.display="none";
									document.addticket.fruttaSecca.style.display="none";
									document.addticket.ortaggi.style.display="none";
									document.addticket.funghi.style.display="none";
									document.addticket.derivati.style.display="none";
									document.addticket.conservati.style.display="none";
									document.addticket.grassi.style.display="block";
									document.addticket.vino.style.display="none";
									document.addticket.zuppe.style.display="none";

								}else{
									if(righe=="9" ){
										document.addticket.fruttaFresca.style.display="none";
										document.addticket.fruttaSecca.style.display="none";
										document.addticket.ortaggi.style.display="none";
										document.addticket.funghi.style.display="none";
										document.addticket.derivati.style.display="none";
										document.addticket.conservati.style.display="none";
										document.addticket.grassi.style.display="none";
										document.addticket.vino.style.display="block";
										document.addticket.zuppe.style.display="none";

									}else{

										if(righe=="11" ){
											document.addticket.fruttaFresca.style.display="none";
											document.addticket.fruttaSecca.style.display="none";
											document.addticket.ortaggi.style.display="none";
											document.addticket.funghi.style.display="none";
											document.addticket.derivati.style.display="none";
											document.addticket.conservati.style.display="none";
											document.addticket.grassi.style.display="none";
											document.addticket.vino.style.display="none";
											document.addticket.zuppe.style.display="block";

										}else{

											document.addticket.grassi.style.display="none";
											document.addticket.vino.style.display="none";
											document.addticket.zuppe.style.display="none";
											document.addticket.fruttaFresca.style.display="none";
											document.addticket.fruttaSecca.style.display="none";
											document.addticket.ortaggi.style.display="none";
											document.addticket.funghi.style.display="none";
											document.addticket.derivati.style.display="none";
											document.addticket.conservati.style.display="none";

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

function abilitaAltriAlimenti(){

	document.getElementById("alimentinonAnimali").disabled=false;
		
	}
function disabilitaAltriAlimenti(){

	document.getElementById("alimentinonAnimali").disabled=true;
		
	}

function abilitaMaterialiAlimenti(){

	document.getElementById("materialialimenti").disabled=false;
		
	}

function disabilitaAdditivi(){

	document.getElementById("additivi").disabled=true;
		
	}


function disabilitaBevande(){

	document.getElementById("bevande").disabled=true;
		
	}


function disabilitaMaterialiAlimenti(){

	document.getElementById("materialialimenti").disabled=true;
		
	}


function disabilitaAcque(){
	document.getElementById("acqua").disabled=true;
	
}


function mostraUlteririNote(){


value=document.addticket.motivazione.value;
if(value=='Per Altro'){

	document.getElementById("noteMotivazione").style.display="";
	
}else{
	document.getElementById("noteMotivazione").style.display="none";
}
	
	
}







/*function disabilitaDolciumi(){
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
*/
function abilitaAcqueCheck(){
	document.getElementById("acqua").disabled=false; 
	
}

function abilitaBevandeCheck(){
	document.getElementById("bevande").disabled=false; 
	
}

function abilitaFlag(){
	if(document.getElementById("allerta").checked==true){
		document.getElementById("segnalazione").disabled=true;
	}else{

		if(document.getElementById("segnalazione").checked==true){
			document.getElementById("allerta").disabled=true;
		}else{
			document.getElementById("segnalazione").disabled=false;
			document.getElementById("allerta").disabled=false;

			}




		}

	
}

function abilitatipoAdditivi(){
	var check=document.getElementById("additivi");

if(check.checked){
	disabilitaCompostiVegetale();
    disabilitaComposti();
  
    disabilitaAcque();
   
     disabilitaBevande();
   
     disabilitaCompostiAnimale();
    
    disabilitaMaterialiAlimenti();
    //disabilitaDolciumi();
    //disabilitaGelati();
document.getElementById("noteadditivi").style.display="block";
	
}else{
	 abilitaAnimaliCheck();
	abilitaCompostiVegetaleCheck();
	  abilitaCompostiCheck();
	  abilitaAcqueCheck();
	abilitaBevandeCheck();	
	//abilitaDolciumiCheck();	
	//abilitaGelatiCheck();
 abilitaMaterialiAlimenti();

 document.getElementById("noteadditivi").style.display="none";	
 document.getElementById("noteadditivi").value="";
}
	

	
}



/*function abilitatipoDolciumi(){

	var check=document.getElementById("dolciumi");
if(check.checked) {
	disabilitaCompostiVegetale();
    disabilitaComposti();
    disabilitaGelati();
  
    disabilitaAcque();
   
     disabilitaBevande();
     disabilitaMaterialiAlimenti();
     disabilitaCompostiAnimale();
    
   disabilitaAdditivi();
   document.getElementById("notedolciumi").style.display="block";
}else{

	 abilitaAnimaliCheck();
		abilitaCompostiVegetaleCheck();
		  abilitaCompostiCheck();
		  abilitaAcqueCheck();
		  abilitaGelatiCheck();
		  abilitaMaterialiAlimenti();
		  
		abilitaBevandeCheck();	
abilitaAdditivi();

document.getElementById("notedolciumi").style.display="none";
document.getElementById("notedolciumi").value="";
	
}
	
}




function abilitatipoGelati(){

	var check=document.getElementById("gelati");
if(check.checked) {
	disabilitaCompostiVegetale();
    disabilitaComposti();
    disabilitaDolciumi();
    disabilitaAcque();
    disabilitaMaterialiAlimenti();
     disabilitaBevande();
     
     disabilitaCompostiAnimale();
    
   disabilitaAdditivi();
   document.getElementById("notegelati").style.display="block";
}else{
	 abilitaMaterialiAlimenti();

	 abilitaAnimaliCheck();
		abilitaCompostiVegetaleCheck();
		  abilitaCompostiCheck();
		  abilitaAcqueCheck();
		  abilitaDolciumiCheck();
		abilitaBevandeCheck();	
		abilitaAdditivi();

document.getElementById("notegelati").style.display="none";
document.getElementById("notegelati").value="";
	
}
	
}
*/


function abilitatipomaterialiAlimenti(){

	var check=document.getElementById("materialialimenti");
if(check.checked) {
	disabilitaCompostiVegetale();
    disabilitaComposti();
  
    disabilitaAcque();
   
     disabilitaBevande();
     //disabilitaDolciumi();
     //disabilitaGelati();
     disabilitaCompostiAnimale();
     disabilitaAltriAlimenti();
   disabilitaAdditivi();
   document.getElementById("notematerialialimenti").style.display="block";
}else{

	 abilitaAnimaliCheck();
		abilitaCompostiVegetaleCheck();
		abilitaAltriAlimenti();
		  abilitaCompostiCheck();
		  abilitaAcqueCheck();
		abilitaBevandeCheck();	
		abilitaAdditivi();
		//abilitaGelatiCheck();	
		//abilitaDolciumiCheck();	

document.getElementById("notematerialialimenti").style.display="none";
document.getElementById("notematerialialimenti").value="";
	
}
	
}

function abilitaTipoAlimentoAnimale(){


var check=document.getElementById("alimentiOrigineAnimale");

if(check.checked==true){
	document.getElementById("tipoAlimentiAnimali").style.display="block";
	disabilitaCompostiVegetale();
	
	document.addticket.alimentiOrigineAnimaleTrasformati.value="-1";

	//disabilitaDolciumi();
	//disabilitaGelati();
    disabilitaComposti();
   
    disabilitaAcque();
    disabilitaAltriAlimenti();
     disabilitaBevande();
    
    disabilitaAdditivi();
    
    disabilitaMaterialiAlimenti();
    
}
else{

    abilitaAltriAlimenti();
	document.getElementById("notealimenti").style.display="none";
	document.getElementById("tipoAlimentiAnimali").style.display="none";
	abilitaCompostiVegetaleCheck();
	  abilitaCompostiCheck();
	  abilitaAcqueCheck();
	abilitaBevandeCheck();	
	abilitaAdditivi();
   abilitaMaterialiAlimenti();
   //abilitaGelatiCheck();	
	//abilitaDolciumiCheck();	
	  document.addticket.TipoSpecie_uova.style.display="none";
	  document.addticket.TipoSpecie_latte.style.display="none";
	  document.addticket.alimentiOrigineAnimaleNonTrasformati.style.display="none";
	  document.addticket.alimentiOrigineAnimaleNonTrasformati.value="-1";
	  document.addticket.alimentiOrigineAnimaleTrasformati.style.display="none"
	  document.addticket.alimentiOrigineAnimaleTrasformati.value="-1";
	  document.addticket.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none"; 

}
	
}







function disabilitaTipochimico(){
	
	//document.getElementById("nascosto2").style.display="none";
		document.forms['addticket'].TipoCampione_sottochimico.style.display="none";
		document.forms['addticket'].TipoCampione_sottochimico2.style.display="none";
		document.forms['addticket'].TipoCampione_sottochimico3.style.display="none";
		document.forms['addticket'].TipoCampione_sottochimico4.style.display="none";
		document.forms['addticket'].TipoCampione_sottochimico5.style.display="none";
	}

function abilitaLista_tipoAnalisi(){
	
	var tipo=document.forms['addticket'].TipoCampione.value;

	if(tipo==1){
		//document.getElementById("nascosto1").style.display="block";
		document.forms['addticket'].TipoCampione_batteri.style.display="block";
		document.forms['addticket'].TipoCampione_virus.style.display="none";
		document.forms['addticket'].TipoCampione_parassiti.style.display="none";
		document.forms['addticket'].TipoCampione_chimico.style.display="none";
		
		disabilitaTipochimico();
return;

		}
	if(tipo==2){
		//document.getElementById("nascosto1").style.display="block";
		document.forms['addticket'].TipoCampione_batteri.style.display="none";
		document.forms['addticket'].TipoCampione_virus.style.display="block";
		document.forms['addticket'].TipoCampione_parassiti.style.display="none";
		document.forms['addticket'].TipoCampione_chimico.style.display="none";
		
		disabilitaTipochimico();
		return;


		}

	if(tipo==4){
	//	document.getElementById("nascosto1").style.display="block";
		document.forms['addticket'].TipoCampione_batteri.style.display="none";
		document.forms['addticket'].TipoCampione_virus.style.display="none";
		document.forms['addticket'].TipoCampione_parassiti.style.display="block";
		document.forms['addticket'].TipoCampione_chimico.style.display="none";
		
		disabilitaTipochimico();
return;

		}

	if(tipo==5){
	//	document.getElementById("nascosto1").style.display="block";
		document.forms['addticket'].TipoCampione_batteri.style.display="none";
		document.forms['addticket'].TipoCampione_virus.style.display="none";
		document.forms['addticket'].TipoCampione_parassiti.style.display="none";
		document.forms['addticket'].TipoCampione_chimico.style.display="block";
return;

		}

	//document.getElementById("nascosto1").style.display="none";
	disabilitaTipochimico();
		document.forms['addticket'].TipoCampione_batteri.style.display="none";
		document.forms['addticket'].TipoCampione_virus.style.display="none";
		document.forms['addticket'].TipoCampione_parassiti.style.display="none";
		document.forms['addticket'].TipoCampione_chimico.style.display="none";
		
		return;

		
	
}

function mostraFollowUP(){

if(document.addticket.EsitoTampone.value=="1"){

	document.getElementById("followup").style.display="";
	
}else{

	document.getElementById("followup").style.display="none";
	
}

	
}


function abilitaLista_tipoChimico(){
	
	var tipo=document.forms['addticket'].TipoCampione_chimico.value;

	if(tipo==1){
	//	document.getElementById("nascosto2").style.display="block";
		document.forms['addticket'].TipoCampione_sottochimico.style.display="block";
		document.forms['addticket'].TipoCampione_sottochimico2.style.display="none";
		document.forms['addticket'].TipoCampione_sottochimico3.style.display="none";
		document.forms['addticket'].TipoCampione_sottochimico4.style.display="none";
		document.forms['addticket'].TipoCampione_sottochimico5.style.display="none";
	
		return;

		}
	if(tipo==2){
		//document.getElementById("nascosto2").style.display="block";
		document.forms['addticket'].TipoCampione_sottochimico.style.display="none";
		document.forms['addticket'].TipoCampione_sottochimico2.style.display="block";
		document.forms['addticket'].TipoCampione_sottochimico3.style.display="none";
		document.forms['addticket'].TipoCampione_sottochimico4.style.display="none";
		document.forms['addticket'].TipoCampione_sottochimico5.style.display="none";
		return;


		}

	if(tipo==3){
		//document.getElementById("nascosto2").style.display="block";
		document.forms['addticket'].TipoCampione_sottochimico.style.display="none";
		document.forms['addticket'].TipoCampione_sottochimico2.style.display="none";
		document.forms['addticket'].TipoCampione_sottochimico3.style.display="block";
		document.forms['addticket'].TipoCampione_sottochimico4.style.display="none";
		document.forms['addticket'].TipoCampione_sottochimico5.style.display="none";
		return;


		}

	if(tipo==4){
	//	document.getElementById("nascosto2").style.display="block";
		document.forms['addticket'].TipoCampione_sottochimico.style.display="none";
		document.forms['addticket'].TipoCampione_sottochimico2.style.display="none";
		document.forms['addticket'].TipoCampione_sottochimico3.style.display="none";
		document.forms['addticket'].TipoCampione_sottochimico4.style.display="block";
		document.forms['addticket'].TipoCampione_sottochimico5.style.display="none";
		return;

		}
	if(tipo==5){
		//document.getElementById("nascosto2").style.display="block";
		document.forms['addticket'].TipoCampione_sottochimico.style.display="none";
		document.forms['addticket'].TipoCampione_sottochimico2.style.display="none";
		document.forms['addticket'].TipoCampione_sottochimico3.style.display="none";
		document.forms['addticket'].TipoCampione_sottochimico4.style.display="none";
		document.forms['addticket'].TipoCampione_sottochimico5.style.display="block";
return;
		}

	
	if(tipo==-1){
	//	document.getElementById("nascosto2").style.display="none";
		document.forms['addticket'].TipoCampione_sottochimico.style.display="none";
		document.forms['addticket'].TipoCampione_sottochimico2.style.display="none";
		document.forms['addticket'].TipoCampione_sottochimico3.style.display="none";
		document.forms['addticket'].TipoCampione_sottochimico4.style.display="none";
		document.forms['addticket'].TipoCampione_sottochimico5.style.display="none";
		return;
		}
		
			return;
	
}




function abilita2(){
document.getElementById("nucleodue").style.display="";

}

function abilita3(){
document.getElementById("nucleotre").style.display="";
}

function abilita4(){
document.getElementById("nucleoquattro").style.display="";
}

function abilita5(){
document.getElementById("nucleocinque").style.display="";
}

function abilita6(){
document.getElementById("nucleosei").style.display="";
}

function abilita7(){
document.getElementById("nucleosette").style.display="";
}

function abilita8(){
document.getElementById("nucleootto").style.display="";
}

function abilita9(){
document.getElementById("nucleonove").style.display="";
}

function abilita10(){
document.getElementById("nucleodieci").style.display="";
}

function abilitaalimentinonAnimali(){


	 if (document.getElementById("alimentinonAnimali").checked==true)
	 {

		 
		 document.getElementById("alimentinonanimalicella").style.display = "block";
		
		 document.addticket.altrialimenti.style.display = "block";
			
	  disabilitaCompostiAnimale();
		disabilitaBevande();
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
		 abilitaMaterialiAlimenti();
		 abilitaAdditivi();
		 abilitaAcqueCheck()
		 document.getElementById("alimentinonanimalicella").style.display = "none";

	  abilitaCompostiVegetaleCheck();
	//abilitaDolciumiCheck();
	//abilitaGelatiCheck();
	   abilitaAnimaliCheck();
	 } 
	
	
}


//aggiunto da d.dauria
function abilitaTestoAlimentoComposto()
{
	 

 if (document.getElementById("alimentiComposti").checked==true)
 {

		
	 document.getElementById("testoAlimentoComposto").style.display = "block";
		
  disabilitaCompostiAnimale();
	disabilitaBevande();
  disabilitaCompostiVegetale();
  disabilitaAcque();
  disabilitaAdditivi();
  disabilitaMaterialiAlimenti();
  //disabilitaDolciumi();
  //disabilitaGelati();
  disabilitaAltriAlimenti();
  
 }
 else
 {

     abilitaAltriAlimenti();
	 abilitaCompostiVegetaleCheck();
	 abilitaBevandeCheck();
	 abilitaMaterialiAlimenti();
	 abilitaAdditivi();
	 abilitaAcqueCheck()
	 document.getElementById("testoAlimentoComposto").style.display = "none";
  abilitaCompostiVegetaleCheck();
//abilitaDolciumiCheck();
//abilitaGelatiCheck();
   abilitaAnimaliCheck();
 } 
}

function abilitaCheckSegnalazione()
{
   allerta = document.getElementById("allerta");
   nonConformita = document.getElementById("nonConformita");
   if(nonConformita.checked)
    { allerta.checked = false;
    }
}


function abilitaCheckAllerta()
{
   allerta = document.getElementById("allerta");
   nonConformita = document.getElementById("nonConformita");
   if(allerta.checked)
    { nonConformita.checked = false;
    }
}

function controlloLookup(){
     //aggiunto per positività
    document.getElementById("note").style.visibility="hidden";
  
   document.getElementById("note_etichetta").style.visibility="hidden";
  
    //aggiunto da d.dauria
     document.getElementById("lookupNonTrasformati").style.visibility = "hidden";
  
     document.addticket.alimentiOrigineAnimaleNonTrasformati.style.display="none";
     document.addticket.alimentiOrigineAnimaleNonTrasformati.value="-1";
     document.getElementById("tipoAlimentiAnimali").style.display="none";
     document.addticket.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";

     //document.getElementById("lookupTrasformati").style.visibility = "hidden"; 
     
      document.addticket.alimentiOrigineAnimaleTrasformati.style.display="none";
     
      document.getElementById("lookupVegetale").style.visibility = "hidden";
      
  
     
     //aggiunto da d.dauria per far scomparire il testo degli alimenti composti
    document.getElementById("testoAlimentoComposto").style.display = "none";

   
}
//aggiunto per positività
function abilitaNote(form)
{
   if(form.conseguenzePositivita.value == 4)
   {
    document.getElementById("note").style.visibility="visible";
   
    document.getElementById("note_etichetta").style.visibility="visible";
    
   } 
   else
   {
    document.getElementById("note").style.visibility="hidden";
    
    document.getElementById("note_etichetta").style.visibility="hidden";

   }
   
}

function abilitaLookupOrigineAnimale()
{
	
    alimentiOrigine = document.addticket.tipoAlimentiAnimali.value;

document.getElementById("lookupNonTrasformati").style.visibility="visible";
  //sel = document.getElementById("lookupNonTrasformati");
  
    //sel3 = document.getElementById("lookupTrasformati");
    if(alimentiOrigine==1)
    { 
        

   	 document.addticket.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none"
         
document.forms['addticket'].alimentiOrigineAnimaleNonTrasformati.style.display="block";
document.addticket.alimentiOrigineAnimaleTrasformati.style.display="none"
	document.addticket.alimentiOrigineAnimaleTrasformati.value="-1";

    
      disabilitaCompostiVegetale();
      
      disabilitaComposti();
    }

    else{
if(alimentiOrigine=="2"){
	 document.addticket.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
     
	document.addticket.alimentiOrigineAnimaleNonTrasformati.style.display="none";
	document.addticket.alimentiOrigineAnimaleNonTrasformati.value="-1";
	document.addticket.alimentiOrigineAnimaleTrasformati.style.display="block";
	  document.forms['addticket'].alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
      document.forms['addticket'].TipoSpecie_latte.style.display="none";
      document.forms['addticket'].TipoSpecie_uova.style.display="none";
      document.addticket.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
		 disabilitaCompostiVegetale();
    
    disabilitaComposti();
	
}
else
{ 
	document.addticket.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
	document.forms['addticket'].TipoSpecie_latte.style.display="none";
    document.forms['addticket'].TipoSpecie_uova.style.display="none";
	   document.forms['addticket'].TipoSpecie_latte.style.value="-1";
	      document.forms['addticket'].TipoSpecie_uova.value="-1";
	document.addticket.alimentiOrigineAnimaleNonTrasformati.style.display="none";
	document.addticket.alimentiOrigineAnimaleNonTrasformati.value="-1";
	document.addticket.alimentiOrigineAnimaleTrasformati.value="-1";
	document.addticket.alimentiOrigineAnimaleTrasformati.style.display="none"
		document.addticket.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
	 disabilitaCompostiVegetale();
     
     disabilitaComposti();

     document.getElementById("lookupNonTrasformati").style.visibility="hidden";
 
 
} 

        }
   
     
}


//magic
function abilitaSpecie(form)
{
    if((form.alimentiOrigineAnimaleNonTrasformati.value >= 1) && (form.alimentiOrigineAnimaleNonTrasformati.value <= 4))
     {
    	form.alimentiOrigineAnimaleTrasformati.value="-1";
      sel2 = document.addticket.alimentiOrigineAnimaleNonTrasformatiValori;//
      sel2.style.display="block"
  	form.TipoSpecie_uova.style.display="none";
      form.TipoSpecie_uova.value="-1";
      form.TipoSpecie_latte.value="-1";
  	form.TipoSpecie_latte.style.display="none";
  	document.getElementById("notealimenti").style.display="block";
     } 
    else
     {

    	if(form.alimentiOrigineAnimaleNonTrasformati.value==8){
    		form.alimentiOrigineAnimaleTrasformati.value="-1";
    		form.TipoSpecie_uova.value="-1";
    		sel2 = document.addticket.alimentiOrigineAnimaleNonTrasformatiValori;//
    	      sel2.style.display="none"
    	    	  document.addticket.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
    		form.TipoSpecie_latte.style.display="block";
    		form.TipoSpecie_uova.style.display="none";
    	 	document.getElementById("notealimenti").style.display="block";
    		
        	}else{
        		if(form.alimentiOrigineAnimaleNonTrasformati.value==9){
        			form.alimentiOrigineAnimaleTrasformati.value="-1";
            		form.TipoSpecie_latte.value="-1";
            		document.addticket.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
        			 document.addticket.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
        		
            		form.TipoSpecie_uova.style.display="block";
            		form.TipoSpecie_latte.style.display="none";
            	 	document.getElementById("notealimenti").style.display="block";
            		
                	}else{
                		document.addticket.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
                		form.alimentiOrigineAnimaleTrasformati.value="-1";
                	 	document.getElementById("notealimenti").style.display="block";
        		form.TipoSpecie_uova.style.display="none";
        	  	form.TipoSpecie_latte.style.display="none";
        	  	form.TipoSpecie_uova.value="-1";
        	  	form.TipoSpecie_latte.value="-1";
     document.addticket.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
      form.alimentiOrigineAnimaleNonTrasformatiValori.value= -1;
   
     } }}
     if(form.alimentiOrigineAnimaleNonTrasformati.value == -1)
     {
    	 document.addticket.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
    	 	document.getElementById("notealimenti").style.display="none";
    	 form.TipoSpecie_uova.style.display="none";
 	  	form.TipoSpecie_latte.style.display="none";
        //sel3 = document.getElementById("lookupTrasformati");
        //sel3.style.visibility = "visible";
        sel2 = document.addticket.alimentiOrigineAnimaleNonTrasformatiValori;//getElementById("lookupNonTrasformatiValori");
        sel2.style.display="none"
        form.alimentiOrigineAnimaleNonTrasformatiValori.value= -1;
     }
     


     
}

//aggiunto per magic
function disabilitaNonTrasformati(form)
{
   if(form.alimentiOrigineAnimaleTrasformati.value != -1)
     {
	   
        //sel3 = document.getElementById("lookupNonTrasformati");
        //sel3.style.visibility = "visible";
     //}
     //else
     //{
      //sel3 = document.getElementById("lookupNonTrasformati");
      //sel3.style.visibility = "hidden";
    
   document.getElementById("notealimenti").style.display="block";
     }  

}

function disabilitaCompostiAnimale(){

	document.getElementById("alimentiOrigineAnimale").disabled=true;
}
function disabilitaComposti(){

	document.getElementById("alimentiComposti").disabled=true;
}

function abilitaAnimaliCheck(){

	document.getElementById("alimentiOrigineAnimale").disabled=false;
}
function abilitaCompostiCheck(){

	document.getElementById("alimentiComposti").disabled=false;
	
}
function disabilitaCompostiVegetale(){
	document.getElementById("alimentiOrigineVegetale").disabled=true;
	
}
function abilitaCompostiVegetaleCheck(){
	document.getElementById("alimentiOrigineVegetale").disabled=false;
	
}





function abilitaLookupOrigineVegetale()
{
    alimentiOrigine = document.getElementById("alimentiOrigineVegetale");
    sel2 = document.getElementById("lookupVegetale");
    sel3 = document.getElementById("lookupVegetale1");
    if(alimentiOrigine.checked)
    { sel2.style.visibility = "visible";
    sel3.style.visibility = "visible";
    disabilitaCompostiAnimale();
    disabilitaComposti();
    disabilitaAcque();
    disabilitaBevande();
    disabilitaAltriAlimenti();
    disabilitaAdditivi();

    //disabilitaDolciumi();
    //disabilitaGelati();
	   disabilitaMaterialiAlimenti();
    document.getElementById("notealimenti2").style.display="block";
    }
    else
    { 
        abilitaAltriAlimenti();
    	document.addticket.grassi.style.display="none";
		document.addticket.vino.style.display="none";
		document.addticket.zuppe.style.display="none";
		document.addticket.fruttaFresca.style.display="none";
		document.addticket.fruttaSecca.style.display="none";
		document.addticket.ortaggi.style.display="none";
		document.addticket.funghi.style.display="none";
		document.addticket.derivati.style.display="none";
		document.addticket.conservati.style.display="none";
    	 abilitaAnimaliCheck();
         abilitaCompostiCheck();
        // abilitaDolciumiCheck();
         //abilitaGelatiCheck();
         abilitaAcqueCheck();
         abilitaBevandeCheck();	
    	 abilitaAdditivi();
    	 abilitaMaterialiAlimenti();
    	 
    document.addticket.fruttaFresca.style.display="none";
    document.addticket.fruttaSecca.style.display="none";
    document.addticket.ortaggi.style.display="none";
    document.addticket.funghi.style.display="none";
    document.addticket.derivati.style.display="none";
    document.addticket.conservati.style.display="none";
    
     sel2.style.visibility = "hidden";
     sel3.style.visibility = "hidden";

     document.getElementById("notealimenti2").style.display="none";
    
    
	   
		  
           
    }  
}

function abilitaAcque()
{
    alimentiOrigine = document.getElementById("acqua");
    //sel2 = document.getElementById("lookupVegetale");
    if(alimentiOrigine.checked==true)
    { 
    document.getElementById("acquaSelect").style.display="block";
    document.getElementById("noteacqua").style.display="block";

    disabilitaCompostiVegetale();
	disabilitaCompostiAnimale();
    disabilitaComposti();
   
 	disabilitaMaterialiAlimenti();
 	//disabilitaDolciumi();
 	//disabilitaGelati();
    disabilitaAdditivi();
    disabilitaAltriAlimenti();
    disabilitaBevande();
    }
    else
    { 
        abilitaAltriAlimenti();

    	abilitaCompostiVegetaleCheck()
    	abilitaAnimaliCheck();
        abilitaCompostiCheck();
		abilitaAdditivi();
    	abilitaMaterialiAlimenti();
    	abilitaBevandeCheck();
    	//abilitaDolciumiCheck();
        //abilitaGelatiCheck();
        
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
    
    
    disabilitaCompostiVegetale();
	disabilitaCompostiAnimale();
    disabilitaComposti();
    disabilitaAcque();
 	disabilitaMaterialiAlimenti();
 	//disabilitaDolciumi();
	//disabilitaGelati();
    disabilitaAdditivi();
    
    disabilitaAltriAlimenti();
    
    document.getElementById("notebevande").style.display="block";
    }
    else
    { 
        abilitaAltriAlimenti();
    	abilitaCompostiVegetaleCheck()
    	abilitaAnimaliCheck();
        abilitaCompostiCheck();
        //abilitaGelatiCheck();
       // abilitaDolciumiCheck();
        abilitaAcqueCheck();
        		
    		abilitaAdditivi();
    	   abilitaMaterialiAlimenti();
     //sel2.style.visibility = "hidden";

     document.getElementById("notebevande").style.display="none";
     document.getElementById("notebevande").value="";

     //abilitaAnimaliCheck();
     //abilitaCompostiCheck();
          
    }  
}


//fine delle modifiche

  function updateCategoryList() {
    var orgId = document.forms['addticket'].orgId.value;
    var url = 'TroubleTicketsCampioni.do?command=CategoryJSList&form=addticket&reset=true&orgId='+orgId;
    window.frames['server_commands'].location.href=url;
  }
<dhv:include name="ticket.catCode" none="false">
  function updateSubList1() {
    var orgId = document.forms['addticket'].orgId.value;
    if(orgId != '-1'){
      var sel = document.forms['addticket'].elements['catCode'];
      var value = sel.options[sel.selectedIndex].value;
      var url = "TroubleTicketsCampioni.do?command=CategoryJSList&form=addticket&catCode=" + escape(value)+'&orgId='+orgId;
      window.frames['server_commands'].location.href=url;
    } else {
      var sel = document.forms['addticket'].elements['catCode'];
      sel.options.selectedIndex = 0;
      alert(label("select.account.first",'You have to select an Account first'));
      return;
    }
  }
</dhv:include>
<dhv:include name="ticket.subCat1" none="true">
  function updateSubList2() {
    var orgId = document.forms['addticket'].orgId.value;
    var sel = document.forms['addticket'].elements['subCat1'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTicketsCampioni.do?command=CategoryJSList&form=addticket&subCat1=" + escape(value)+'&orgId='+orgId;
    window.frames['server_commands'].location.href=url;
  }
</dhv:include>
<dhv:include name="ticket.subCat2" none="true">
  function updateSubList3() {
    var orgId = document.forms['addticket'].orgId.value;
    var sel = document.forms['addticket'].elements['subCat2'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTicketsCampioni.do?command=CategoryJSList&form=addticket&subCat2=" + escape(value)+'&orgId='+orgId;
    window.frames['server_commands'].location.href=url;
  }
</dhv:include>
<dhv:include name="ticket.subCat3" none="true">
  function updateSubList4() {
    var orgId = document.forms['addticket'].orgId.value;
    var sel = document.forms['addticket'].elements['subCat3'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTicketsCampioni.do?command=CategoryJSList&form=addticket&subCat3=" + escape(value)+'&orgId='+orgId;
    window.frames['server_commands'].location.href=url;
  }
</dhv:include>
  function updateUserList() {
    var sel = document.forms['addticket'].elements['departmentCode'];
    var value = sel.options[sel.selectedIndex].value;
    var orgSite = document.forms['addticket'].elements['orgSiteId'].value;
    var url = "TroubleTicketsCampioni.do?command=DepartmentJSList&form=addticket&dept=Assigned&orgSiteId="+ orgSite +"&populateResourceAssigned=true&resourceAssignedDepartmentCode=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
  function updateResolvedByUserList() {
    var sel = document.forms['addticket'].elements['resolvedByDeptCode'];
    var value = sel.options[sel.selectedIndex].value;
    var orgSite = document.forms['addticket'].elements['orgSiteId'].value;
    var url = "TroubleTicketsCampioni.do?command=DepartmentJSList&form=addticket&dept=Resolved&orgSiteId="+ orgSite + "&populateResolvedBy=true&resolvedByDepartmentCode=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
  
  function updateAllUserLists() {
    var sel = document.forms['addticket'].elements['departmentCode'];
    var value = sel.options[sel.selectedIndex].value;
    var orgSite = document.forms['addticket'].elements['orgSiteId'].value;
    var sel2 = document.forms['addticket'].elements['resolvedByDeptCode'];
    var value2 = sel2.options[sel2.selectedIndex].value;
    var url = "TroubleTicketsCampioni.do?command=DepartmentJSList&form=addticket&orgSiteId="+ orgSite +"&populateResourceAssigned=true&populateResolvedBy=true&resourceAssignedDepartmentCode=" + escape(value)+'&resolveByDepartmentCode='+ escape(value2);
    window.frames['server_commands'].location.href=url;
  }

  function updateLists() {
  <dhv:include name="ticket.contact" none="true">
    var orgWidget = document.forms['addticket'].elements['orgId'];
    var orgValue = document.forms['addticket'].orgId.value;

    //var resourceAssignedDepartmentWidget = document.forms['addticket'].elements['departmentCode'];
    //var resourceAssignedDepartmentValue = resourceAssignedDepartmentWidget.options[resourceAssignedDepartmentWidget.selectedIndex].value;

    //var resolvedByDepartmentWidget = document.forms['addticket'].elements['resolvedByDeptCode'];
    //var resolvedByDepartmentValue = resolvedByDepartmentWidget.options[resolvedByDepartmentWidget.selectedIndex].value;

    var params = "&orgId=" + escape(orgValue);
    //params = params + "&populateResourceAssigned=true&resourceAssignedDepartmentCode=" + escape(resourceAssignedDepartmentValue);
    //params = params + "&populateResolvedBy=true&resolvedByDepartmentCode=" + escape(resolvedByDepartmentValue);
    params = params + "&populateDefects=true";

    var url = "TroubleTicketsCampioni.do?command=OrganizationJSList" + params; 
    window.frames['server_commands'].location.href=url;
  </dhv:include>
  }
  function checkForm(form){
    formTest = true;
    message = "";
    
  
    alimentiNonAnimali=document.getElementById("alimentinonAnimali");

    
    alimentiOrigineAnimale= document.getElementById("alimentiOrigineAnimale");
    alimentiOrigineVegetale= document.getElementById("alimentiOrigineVegetale");
   alimentiComposti= document.getElementById("alimentiComposti");
    acqua= document.getElementById("acqua");
    bevande= document.getElementById("bevande");
    mangimi= document.getElementById("mangimi");
    additivi= document.getElementById("additivi");
    materialialimenti= document.getElementById("materialialimenti");
    


    
if(form.location.value==""){
	 message += label("check.sanzioni.richiedente.selezionato","- Controllare che il campo \"Numero Verbale\" sia stato popolato\r\n");
    formTest = false;

	 }
if (form.assignedDate.value == "") {
    message += label("check.campioni.data_richiesta.selezionato","- Controllare che il campo \"Data Prelievo\" sia stato popolato\r\n");
    formTest = false;
  }

if (form.TipoCampione.value == "-1") {
  message += label("check.campioni.richiedente.selezionato","- Controllare che \"Tipo di Analisi\" sia stato selezionato\r\n");
  formTest = false;
}else{

	if (form.TipoCampione.value == "1") {

		
		arr=form.TipoCampione_batteri;

		for( z=0; z<arr.length; z++) {

				if(arr[z].value=="-1" && arr[z].selected==true){
					message += label("check.campioni.richiedente.selezionato","- Controllare che \"Tipo di Analisi\" sia stato selezionato\r\n");
				      formTest = false;
					}


			}


		

    	}else{

    		if (form.TipoCampione.value == "2") {
    			arr=form.TipoCampione_virus;
    			for( z=0; z<arr.length; z++) {

					if(arr[z].value=="-1" && arr[z].selected==true){
						message += label("check.campioni.richiedente.selezionato","- Controllare che \"Tipo di Analisi\" sia stato selezionato\r\n");
					      formTest = false;
						}


				}

    			

        		}else{

        			if (form.TipoCampione.value == "4") {
        				arr=form.TipoCampione_parassiti;
        				for( z=0; z<arr.length; z++) {

        					if(arr[z].value=="-1" && arr[z].selected==true){
        						message += label("check.campioni.richiedente.selezionato","- Controllare che \"Tipo di Analisi\" sia stato selezionato\r\n");
        					      formTest = false;
        						}


        				}
                    				

            			}else{

            				if (form.TipoCampione.value == "5") {

            					valchimico=form.TipoCampione_chimico.value;

		if(valchimico=="-1"){
			essage += label("check.campioni.richiedente.selezionato","- Controllare che \"Tipo di Analisi\" sia stato selezionato\r\n");
		      formTest = false;




			}else{

				if(valchimico=="1"){
					

					arr=form.TipoCampione_sottochimico;
    				for( z=0; z<arr.length; z++) {

    					if(arr[z].value=="-1" && arr[z].selected==true){
    						message += label("check.campioni.richiedente.selezionato","- Controllare che \"Tipo di Analisi\" sia stato selezionato\r\n");
    					      formTest = false;
    						}


    				}

					}else{
						if(valchimico=="2"){
							arr=form.TipoCampione_sottochimico2;
	        				for( z=0; z<arr.length; z++) {

	        					if(arr[z].value=="-1" && arr[z].selected==true){
	        						message += label("check.campioni.richiedente.selezionato","- Controllare che \"Tipo di Analisi\" sia stato selezionato\r\n");
	        					      formTest = false;
	        						}


	        				}

							}else{
								if(valchimico=="3"){
									arr=form.TipoCampione_sottochimico3;
			        				for( z=0; z<arr.length; z++) {

			        					if(arr[z].value=="-1" && arr[z].selected==true){
			        						message += label("check.campioni.richiedente.selezionato","- Controllare che \"Tipo di Analisi\" sia stato selezionato\r\n");
			        					      formTest = false;
			        						}


			        				}

									}else{
										if(valchimico=="4"){
											arr=form.TipoCampione_sottochimico4;
					        				for( z=0; z<arr.length; z++) {

					        					if(arr[z].value=="-1" && arr[z].selected==true){
					        						message += label("check.campioni.richiedente.selezionato","- Controllare che \"Tipo di Analisi\" sia stato selezionato\r\n");
					        					      formTest = false;
					        						}


					        				}

											}else{
												if(valchimico=="5"){
													arr=form.TipoCampione_sottochimico5;
							        				for( z=0; z<arr.length; z++) {

							        					if(arr[z].value=="-1" && arr[z].selected==true){
							        						message += label("check.campioni.richiedente.selezionato","- Controllare che \"Tipo di Analisi\" sia stato selezionato\r\n");
							        					      formTest = false;
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

            		}



        	}




    }
if(form.DestinatarioCampione.value=="-1"){
	 message += label("check.sanzioni.richiedente.selezionato","- Controllare che il campo \"Laboratorio Destinazione\" sia stato popolato\r\n");
   formTest = false;

	 }

if(alimentiOrigineAnimale.checked==false && alimentiOrigineVegetale.checked==false && alimentiComposti.checked==false && acqua.checked==false && bevande.checked==false && additivi.checked==false && materialialimenti.checked==false && alimentiNonAnimali.checked==false ){
	message +=label("TESTO1","- Controllare di aver selezionato un tipo di alimento \n"); formTest = false;
}else{

	
if(alimentiOrigineVegetale.checked==true){

	if(form.tipoAlimento.value=="0"){
	if(form.alimentiOrigineVegetaleValoriNonTrasformati.value=="-1"){

		message +=label("TESTO1","- Controllare che il campo \"Alimenti di Origine Vegetale\" sia stato selezionato \n"); formTest = false;
		}
	}else{
		if(form.tipoAlimento.value=="1"){
			if(form.alimentiOrigineVegetaleValoriTrasformati.value=="-1"){

				message +=label("TESTO1","- Controllare che il campo \"Alimenti di Origine Vegetale\" sia stato selezionato \n"); formTest = false;
				}
			}

		}
	
}else{
			
				if(alimentiComposti.checked==true){

if(form.testoAlimentoComposto.value==""){
	message +=label("TESTO1","- Controllare che il campo di testo per \"Alimenti Composti\" sia stato popolato \n"); formTest = false;
	
	
}



				}else{

					if(acqua.checked==true){

						//if(form.Acque.value==-1)
						//message +=label("TESTO1","- Controllare che il campo \"Acqua\" sia stato popolato \n"); formTest = false;


					}else if(bevande.checked==true){
						//message +=label("TESTO1","- Controllare che il campo di testo per  \"Bevande\" sia stato popolato \n"); formTest = false;
						}

						else{

							if(additivi.checked==true){


//								message +=label("TESTO1","- Controllare che il campo di testo per \"Additivi\" sia stato popolato \n"); formTest = false;


							}else{

								if(materialialimenti.checked==true){

	//								message +=label("TESTO1","- Controllare che il campo di testo per \"Materiali a Contatto con Alimenti\" sia stato popolato \n"); formTest = false;
								}}


								



			}



				}
}}
    //aggiunto da d.dauria
    alimentiOrigine = document.getElementById("alimentiOrigineAnimale");
    sel = document.getElementById("lookupNonTrasformati");
    sel2 = document.getElementById("lookupNonTrasformatiValori");
    sel3 = document.getElementById("lookupTrasformati");
   /* if(((alimentiOrigine.checked) && (form.alimentiOrigineAnimaleNonTrasformati.value == "-1")) && ((alimentiOrigine.checked) && (form.alimentiOrigineAnimaleTrasformati.value == "-1")))*/
    /*{ message +=label("TESTO1","Controllare che il campo Alimenti Origine Animale sia stato riempito\n"); formTest = false; }*/
  /*  if((alimentiOrigine.checked) &&(form.alimentiOrigineAnimaleTrasformati.value == "-1"))
    { message +=label("TESTO2","Controllare che il campo Alimenti Origine Animale Trasformati sia stato selezionato\n"); formTest = false; }
    */





    

    if(alimentiOrigine.checked==true  )
    { 

    	if(form.tipoAlimentiAnimali.value == "-1"){
    		message +=label("TESTO1","Controllare che il campo Alimenti Origine Animale sia stato riempito\n"); formTest = false;

        	}
    	else{

        	if(form.tipoAlimentiAnimali.value == "1"){ 
    	

	if(form.alimentiOrigineAnimaleNonTrasformati.value == "-1"){
		 message +=label("TESTO1","Controllare che il campo Alimenti Origine Animale sia stato riempito\n"); formTest = false;

	}else{
    	
    	if(form.alimentiOrigineAnimaleNonTrasformati.value == "1" || form.alimentiOrigineAnimaleNonTrasformati.value == "2" || form.alimentiOrigineAnimaleNonTrasformati.value == "3" || form.alimentiOrigineAnimaleNonTrasformati.value == "4" ){ 

    		if(form.alimentiOrigineAnimaleNonTrasformatiValori.value == "-1"){
    		
        message +=label("TESTO1","Controllare che il campo Alimenti Origine Animale sia stato riempito\n"); formTest = false; }
    	}
    	else{
    		if(form.alimentiOrigineAnimaleNonTrasformati.value == "8"  ){ 

    			if(form.TipoSpecie_latte.value == "-1"  ){
    				 message +=label("TESTO1","Controllare che il campo Alimenti Origine Animale sia stato riempito\n"); formTest = false;

    			}
    			
    		}
    		else{
    			if(form.alimentiOrigineAnimaleNonTrasformati.value == "9"  ) {


    				if(form.TipoSpecie_uova.value == "-1"  ){
    					 message +=label("TESTO1","Controllare che il campo Alimenti Origine Animale sia stato riempito\n"); formTest = false;

        			}
        			}

        		

        	}

    }}}else{
    	if(form.tipoAlimentiAnimali.value == "2"){ 

		if(form.alimentiOrigineAnimaleTrasformati.value=="-1"){
			message +=label("TESTO1","Controllare che il campo Alimenti Origine Animale sia stato riempito\n"); formTest = false;


			}

        	}


        }


            }}
  
    <dhv:include name="ticket.contact" none="true">
    if (form.siteId.value == "-1") {
      message += label("check.campioni.richiedente.selezionato","- Controllare che il campo \"A.S.L.\" sia stato popolato\r\n");
      formTest = false;
    }
    </dhv:include>
    <dhv:include name="ticket.contact" none="true">
    if (form.orgId.value == "-1") {
      message += label("check.campioni.richiedente.selezionato","- Controllare che \"Impresa\" sia stato selezionato\r\n");
      formTest = false;
    }
    </dhv:include>
    
   


    
    <dhv:include name="ticket.resolution" none="false">
    if (form.closeNow){
      if (form.closeNow.checked && form.solution.value == "") {
        message += label("check.ticket.resolution.atclose","- Resolution needs to be filled in when closing a ticket\r\n");
        formTest = false;
      }
    }
    </dhv:include>
    
    
    
    
    <dhv:include name="ticket.actionPlans" none="false">
      if (form.insertActionPlan.checked && form.assignedTo.value <= 0) {
        message += label("check.ticket.assignToUser","- Please assign the ticket to create the related action plan.\r\n");
        formTest = false;
      }
      if (form.insertActionPlan.checked && form.actionPlanId.value <= 0) {
        message += label("check.actionplan","- Please select an action plan to be inserted.\r\n");
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
  //used when a new contact is added
  function insertOption(text,value,optionListId){
   var obj = document.forms['addticket'].contactId;
   insertIndex= obj.options.length;
   obj.options[insertIndex] = new Option(text,value);
   obj.selectedIndex = insertIndex;
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
    //when the content of any of the select items changes, do something here
    //reset the sc and asset
    if (divName == 'changeaccount') {
      <dhv:include name="ticket.contact" none="false">
      if(document.forms['addticket'].orgId.value != '-1'){
        updateLists();
      }
      </dhv:include>
      <dhv:include name="ticket.contractNumber" none="false">
      changeDivContent('addServiceContract',label('none.selected','None Selected'));
      resetNumericFieldValue('contractId');
      </dhv:include>
      <dhv:include name="ticket.contractNumber" none="false">
      changeDivContent('addAsset',label('none.selected','None Selected'));
      resetNumericFieldValue('assetId');
      </dhv:include>
      <%-- dhv:include name="ticket.laborCategory" none="false">
      changeDivContent('addLaborCategory',label('none.selected','None Selected'));
      resetNumericFieldValue('productId');
      </dhv:include --%>
    }
  }

  function mostraTrasformati(){

if(document.addticket.tipoAlimento.value=="0"){
document.addticket.alimentiOrigineVegetaleValoriNonTrasformati.style.display="block";
document.addticket.alimentiOrigineVegetaleValoriTrasformati.style.display="none";
document.addticket.fruttaFresca.style.display="none";
document.addticket.fruttaSecca.style.display="none";
document.addticket.ortaggi.style.display="none";
document.addticket.funghi.style.display="none";
document.addticket.derivati.style.display="none";
document.addticket.conservati.style.display="none";
document.addticket.grassi.style.display="none";
document.addticket.vino.style.display="none";
document.addticket.zuppe.style.display="none";
}else{
	if(document.addticket.tipoAlimento.value=="1"){
	document.addticket.alimentiOrigineVegetaleValoriNonTrasformati.style.display="none";
	document.addticket.alimentiOrigineVegetaleValoriTrasformati.style.display="block";
	document.addticket.fruttaFresca.style.display="none";
	document.addticket.fruttaSecca.style.display="none";
	document.addticket.ortaggi.style.display="none";
	document.addticket.funghi.style.display="none";
	document.addticket.derivati.style.display="none";
	document.addticket.conservati.style.display="none";
	document.addticket.grassi.style.display="none";
	document.addticket.vino.style.display="none";
	document.addticket.zuppe.style.display="none";
	}
	else{

		document.addticket.alimentiOrigineVegetaleValoriNonTrasformati.style.display="none";
		document.addticket.alimentiOrigineVegetaleValoriTrasformati.style.display="none";
		document.addticket.fruttaFresca.style.display="none";
		document.addticket.fruttaSecca.style.display="none";
		document.addticket.ortaggi.style.display="none";
		document.addticket.funghi.style.display="none";
		document.addticket.derivati.style.display="none";
		document.addticket.conservati.style.display="none";
		document.addticket.grassi.style.display="none";
		document.addticket.vino.style.display="none";
		document.addticket.zuppe.style.display="none";
		}	
}
	  }

  function addNewContact(){
    <dhv:permission name="allevamenti-allevamenti-contacts-add">
      var acctPermission = true;
    </dhv:permission>
    <dhv:permission name="allevamenti-allevamenti-contacts-add" none="true">
      var acctPermission = false;
    </dhv:permission>
    <dhv:permission name="contacts-internal_contacts-add">
      var empPermission = true;
    </dhv:permission>
    <dhv:permission name="contacts-internal_contacts-add" none="true">
      var empPermission = false;
    </dhv:permission>
    var orgId = document.forms['addticket'].orgId.value;
    if(orgId == -1){
      alert(label("select.account.first",'You have to select an Account first'));
      return;
    }
    if(orgId == '0'){
      if (empPermission) {
        popURL('CompanyDirectory.do?command=Prepare&container=false&popup=true&source=troubletickets', 'New_Employee','600','550','yes','yes');
      } else {
        alert(label('no.permission.addemployees','You do not have permission to add employees'));
        return;
      }
    }else{
      if (acctPermission) {
        popURL('Contacts.do?command=Prepare&container=false&popup=true&source=troubletickets&hiddensource=troubletickets&orgId=' + document.forms['addticket'].orgId.value, 'New_Contact','600','550','yes','yes');
      } else {
        alert(label("no.permission.addcontacts","You do not have permission to add contacts"));
        return;
      }
    }
  }

 function resetNumericFieldValue(fieldId){
  document.getElementById(fieldId).value = -1;
 }

 function setAssignedDate(){
  resetAssignedDate();
  if (document.forms['addticket'].assignedTo.value > 0){
    document.forms['addticket'].assignedDate.value = document.forms['addticket'].currentDate.value;
  }
 }

 function resetAssignedDate(){
    document.forms['addticket'].assignedDate.value = '';
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
    var orgId = document.forms['addticket'].orgId.value;
    var siteId = document.forms['addticket'].orgSiteId.value;
    if (orgId != '-1') {
      popUserGroupsListSingle('userGroupId','changeUserGroup', '&userId=<%= User.getUserRecord().getId() %>&siteId='+siteId);
    } else {
      alert(label("select.account.first",'You have to select an Account first'));
      return;
    }
  }
  
  function popKbEntries() {
    var siteId = document.forms['addticket'].orgSiteId.value;
    var form = document.forms['addticket'];
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
  <dhv:include name="ticket.subCat3" none="true">
    url = url + '&searchcodeSubCat3='+ subCat3Value;
  </dhv:include>
    popURL(url, 'KnowledgeBase','600','550','yes','yes');
  }

  function noteAnalisiF(){
	  var tcamp = document.addticket.TipoCampione.value;
	  
	  if(tcamp != -1){
		  
	  document.getElementById("noteAnalisi").style.display="block";
	  
	  }else if(tcamp == -1){
		  
		  document.getElementById("noteAnalisi").style.display="none";
		  
		  }
 }

  function scritta(){
	  var tcamp = document.addticket.TipoCampione.value;
	  
	  if(tcamp == 1 || tcamp == 2 || tcamp == 4){
		  
	  document.getElementById("scritta").style.display="block";
	  
	  }else{
		  
		  document.getElementById("scritta").style.display="none";
		  
		  }
 }
</script>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Aggiungi Campione</dhv:label></strong>
    </th>
	</tr>
  
   <dhv:include name="stabilimenti-sites" none="true">
 <%--  <dhv:evaluate if="<%= SiteIdList.size() > 1 %>"> --%>
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="stabilimenti.site">Site</dhv:label>
      </td>
      <td>
       <%=SiteIdList.getSelectedValue(OrgDetails
										.getSiteId())%>
          <input type="hidden" name="siteId" value="<%=OrgDetails.getSiteId()%>" >
      		  <input type="hidden" name="bb" value="<%=CampioneBlackBerry.getId() %>" >
      
      
      </td>
    </tr>
<%--</dhv:evaluate>  --%>
  <dhv:evaluate if="<%= SiteIdList.size() <= 1 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
  </dhv:evaluate>
 </dhv:include>
	<% if (!"true".equals(request.getParameter("contactSet"))) { %>
  <tr>
    <td class="formLabel">
      <dhv:label name="sanzioni.richiedente">Impresa</dhv:label>
    </td>
   
     
      <td>
        <%= toHtml(OrgDetails.getName()) %>
        <input type="hidden" name="orgId" value="<%=OrgDetails.getOrgId()%>">
        <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  OrgDetails.getSiteId() %>" />
      </td>
    
  </tr>
  
  <% }else{ %>
    <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  TicketDetails.getId() > 0 ? TicketDetails.getOrgSiteId() : User.getSiteId()%>" />
    <input type="hidden" name="orgId" value="<%= toHtmlValue(request.getParameter("orgId")) %>">
    <input type="hidden" name="contactId" value="<%= request.getParameter("contactId") %>">
  <% } %>
  
  <%-- %><tr>
    <td class="formLabel">
      <dhv:label name="sanzioni.richiedente">Numero C.U..</dhv:label>
    </td>
   
     
      <td>
        
        <input type="text" name="numeroCU">
        
      </td>
    
  </tr>--%>
  
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="">Identificativo C.U.</dhv:label>
    </td>
    <td>
      <%= (String)request.getAttribute("idControllo") %>
      <input type="hidden" name="idControlloUfficiale" id="idControlloUfficiale" value="<%= request.getAttribute("identificativo") %>">
      <input type="hidden" name="idC" id="idC" value="<%= (String)request.getAttribute("idControlloUff") %>">
    </td>
  </tr>
  
  
   <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="">Motivazione</dhv:label>
    </td>
    <td>
     
     <%if(request.getAttribute("Piano")!=null) {
    	 out.print("Piano di Monitoraggio : "+request.getAttribute("Piano"));
    	 %>
    	 <input type ="hidden" name="motivazione" value="<%="Piano di Monitoraggio : "+request.getAttribute("Piano") %>">
    	 
    	 <%
     }else{
     
     %>
     <table class="noborder">
     <tr>
     <td>
      <select name="motivazione" onchange="mostraUlteririNote()">
     
     <option value="Su Sospetto" >Su Sospetto</option>
     <option value="Per Altro" >Per Altro</option>
     </select>
    </td><td id ="noteMotivazione" style="display: none">
    &nbsp;
    Note <br> <textarea rows="5" cols="30" name = "noteMotivazione"></textarea>
    
    
    </td>
    
    
    </tr>

</table>     
     <% }%>
    </td>
  </tr>
  
  
  
  
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="">Numero Verbale</dhv:label>
    </td>
    <td>
    <%if(TicketDetails.getLocation() != "" && TicketDetails.getLocation() != null){ %>
      <input type="text" name="location" id="location" value = "<%=CampioneBlackBerry.getNumeroVerbale() %>" size="20" maxlength="256" /><font color="red">*</font>
    <%}else{%>
          <input type="text" name="location" value = "<%=CampioneBlackBerry.getNumeroVerbale() %>" id="location" value="" size="20" maxlength="256" /><font color="red">*</font>
    <%} %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="sanzionia.data_richiesta">Data Prelievo</dhv:label>
    </td>
    <td>
    <%
    java.sql.Date data=new Date( CampioneBlackBerry.getDataPrelievo().getTime());
    Timestamp timestamp = new Timestamp(data.getTime());
    
    %>
      <zeroio:dateSelect form="addticket"  field="assignedDate" timestamp="<%=timestamp %>"  timeZone="<%=""  %>" showTimeZone="false" />
      <font color="red">*</font> <%= showAttribute(request, "assignedDateError") %>
    </td>
  </tr>
  <%--
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="sanzioni.data_ispezione">Data Macellazione</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addticket" field="estimatedResolutionDate" timestamp="<%= TicketDetails.getEstimatedResolutionDate() %>" timeZone="<%= TicketDetails.getEstimatedResolutionDateTimeZone() %>"  showTimeZone="false" />
      <%= showAttribute(request, "estimatedResolutionDateError") %>
    </td>
  </tr>--%>
  
   <tr>
      <td name="tipoCampione1" id="tipoCampione1" nowrap class="formLabel">
        <dhv:label name="">Tipo di Analisi</dhv:label>
      </td>
      
      
      <%
      
      TipoCampione.setJsEvent("onChange=abilitaLista_tipoAnalisi();noteAnalisiF();scritta()");
      
      %>
    <td>
    <table class="noborder">
    <tr>
    <td >
      <%= TipoCampione.getHtmlSelect("TipoCampione",TicketDetails.getTipoCampione()) %><font color="red">*</font> <%= showAttribute(request, "assignedDateError") %>
 </td>
 
 <td id="nascosto1" >
  <%
  TipoCampione_chimico.setJsEvent("onChange=abilitaLista_tipoChimico()");
  
 
 TipoCampione_batteri.setSelectStyle("display:none");
 TipoCampione_virus.setSelectStyle("display:none");
 TipoCampione_parassiti.setSelectStyle("display:none");
 TipoCampione_chimico.setSelectStyle("display:none");

 
 %>
 
 
 <%= TipoCampione_batteri.getHtmlSelect("TipoCampione_batteri",TicketDetails.getTipoCampione()) %> <%= showAttribute(request, "assignedDateError") %>
 <%= TipoCampione_virus.getHtmlSelect("TipoCampione_virus",TicketDetails.getTipoCampione()) %> <%= showAttribute(request, "assignedDateError") %>
   <%= TipoCampione_parassiti.getHtmlSelect("TipoCampione_parassiti",TicketDetails.getTipoCampione()) %><%= showAttribute(request, "assignedDateError") %>
   <%= TipoCampione_chimico.getHtmlSelect("TipoCampione_chimico",TicketDetails.getTipoCampione()) %> <%= showAttribute(request, "assignedDateError") %>
    
    
 <label style="display:none" id="scritta">(* In caso di selezione multipla tenere </br>premuto il tasto Ctrl durante la Selezione)</label>

    

 </td>
 
 <td id="nascosto2" >
  <%
  
  TipoCampione_sottochimico.setSelectStyle("display:none");
  TipoCampione_sottochimico2.setSelectStyle("display:none");
  TipoCampione_sottochimico3.setSelectStyle("display:none");
  TipoCampione_sottochimico4.setSelectStyle("display:none");
  TipoCampione_sottochimico5.setSelectStyle("display:none");

 %>
 
   <%=  TipoCampione_sottochimico.getHtmlSelect("TipoCampione_sottochimico",TicketDetails.getTipoCampione()) %> <%= showAttribute(request, "assignedDateError") %>
    <%=  TipoCampione_sottochimico2.getHtmlSelect("TipoCampione_sottochimico2",TicketDetails.getTipoCampione()) %> <%= showAttribute(request, "assignedDateError") %>
   <%=  TipoCampione_sottochimico3.getHtmlSelect("TipoCampione_sottochimico3",TicketDetails.getTipoCampione()) %> <%= showAttribute(request, "assignedDateError") %>
   <%=  TipoCampione_sottochimico4.getHtmlSelect("TipoCampione_sottochimico4",TicketDetails.getTipoCampione()) %> <%= showAttribute(request, "assignedDateError") %>
   <%=  TipoCampione_sottochimico5.getHtmlSelect("TipoCampione_sottochimico5",TicketDetails.getTipoCampione()) %> <%= showAttribute(request, "assignedDateError") %>
   
   
   

 </td>
 
 <td style="display:none" id="noteAnalisi">
    <center>Descrizione:</center>
     <textarea rows="8" cols="40" name="noteAnalisi"></textarea></br>
     
  </td>
  <td>
 &nbsp;
 <textarea rows="5" cols="20" disabled="disabled"><%=CampioneBlackBerry.getNoteTipoAnalisi() %></textarea>
 
 </td>
 
 </table>
 

 
 </td>

 
 
  </tr>

<dhv:include name="organization.source" none="true">
   <tr>
      <td name="destinatarioCampione1" id="destinatarioCampione1" nowrap class="formLabel">
        <dhv:label name="">Laboratorio di Destinazione </dhv:label>
      </td>
    <td>
    <table class = "noborder">
      <tr>
      <td>
      <%= DestinatarioCampione.getHtmlSelect("DestinatarioCampione",TicketDetails.getDestinatarioCampione()) %><font color="red">*</font>
  <%-- <input type="hidden" name="destinatarioCampione" value="<%=TicketDetails.getDestinatarioCampione()%>" > --%>
   </td>
   <td>
 &nbsp;
 <input type = "text" disabled="disabled" value ="<%=CampioneBlackBerry.getLaboratorioDestinazione()%>">
 
 </td>
   
    </tr>
    </table>
    </td>
  </tr>
</dhv:include>
<tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Data Accettazione</dhv:label>
    </td>
    <td>
    <%
    Timestamp timestamp1 =null;
    String tolocalString = "";
    if(CampioneBlackBerry.getDataAccettazione()!=null){
    java.sql.Date data1=new Date( CampioneBlackBerry.getDataAccettazione().getTime());
     timestamp1 = new Timestamp(data1.getTime());
    tolocalString=timestamp1.toLocaleString();
    }
    %>
    
      <zeroio:dateSelect form="addticket" field="dataAccettazione"   timestamp="<%= timestamp1 %>"  timeZone="<%= tolocalString %>"  showTimeZone="false" />
    </td>
  </tr>
  

  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.importo">Codice Accettazione</dhv:label>
    </td>
    <td>
      <input type="text" name="cause" id="cause" value = "<%=CampioneBlackBerry.getCodiceAccettazione() %>" size="20" maxlength="256" />
    </td>
  </tr>

 <!-- modifiche aggiunto da d.dauria  -->
 
 <!--  alimenti di origine  animale -->
 <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.importo">Alimenti di Origine Animale</dhv:label>
    </td>
    <td>    
    <table class="noborder">
    <tr>
     <td>
       <input type="checkbox" name="alimentiOrigineAnimale" id="alimentiOrigineAnimale" size="20" maxlength="256" onclick="abilitaTipoAlimentoAnimale()"/>
     </td> 
     <td>
     <select name="tipoAlimentiAnimali" id="tipoAlimentiAnimali" onchange="abilitaLookupOrigineAnimale()">
     <option value="-1">--SELEZIONA UNA VOCE</option>
     <option value="1">Alimenti Non Trasformati</option>
     <option value="2">Alimenti Trasformati</option>
     </select>
     
     
     </td>
     
      <td id="lookupNonTrasformati" style="padding: 5">
       <% AlimentiNonTrasformati.setJsEvent("onchange=\"javascript:abilitaSpecie(this.form);\"");%>
      <%= AlimentiNonTrasformati.getHtmlSelect("alimentiOrigineAnimaleNonTrasformati",TicketDetails.getAlimentiOrigineAnimaleNonTrasformati()) %>
   
     <%  AlimentiTrasformati.setJsEvent("onchange=\"javascript:disabilitaNonTrasformati(this.form);\"");%>
     <%= AlimentiTrasformati.getHtmlSelect("alimentiOrigineAnimaleTrasformati",TicketDetails.getAlimentiOrigineAnimaleTrasformati()) %>
   
      </td > 
     <td id="lookupNonTrasformatiValori" >
      <%= AlimentiNonTrasformatiValori.getHtmlSelect("alimentiOrigineAnimaleNonTrasformatiValori",TicketDetails.getAlimentiOrigineAnimaleNonTrasformatiValori()) %>
    
     <%
     TipoSpecie_latte.setSelectStyle("display:none");
     TipoSpecie_uova.setSelectStyle("display:none");
     
     
     %>
     <%=  TipoSpecie_latte.getHtmlSelect("TipoSpecie_latte",TicketDetails.getAlimentiOrigineAnimaleNonTrasformatiValori()) %>
      <%= TipoSpecie_uova.getHtmlSelect("TipoSpecie_uova",TicketDetails.getAlimentiOrigineAnimaleNonTrasformatiValori()) %>
     
     
    
     </td>
     <td style="display:none" id="notealimenti">
      <center>Descrizione:</center>
     <textarea rows="8" cols="40" name="notealimenti"></textarea>
     
     </td>
  
    </tr>
    </table>
    </td> 
  </tr><!-- chiusura tabella interna -->
  
  <!-- alimenti origine vegetale -->
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.importo">Alimenti di Origine Vegetale</dhv:label>
    </td>
     <td>    
    <table class="noborder" id="padre">
    <tr>
     <td>
       <input type="checkbox" name="alimentiOrigineVegetale" id="alimentiOrigineVegetale" size="20" maxlength="256"  onclick="abilitaLookupOrigineVegetale()" />
     </td>
      <td id="lookupVegetale">
      <select name="tipoAlimento" onchange="mostraTrasformati()">
       <option value="-1">--Seleziona Voce--</option>
      <option value="0">Non Trasformati</option>
      <option value="1">Trasformati</option>
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
      Ortaggi.setSelectStyle("display:none");
      FruttaFresca.setSelectStyle("display:none");
      FruttaSecca.setSelectStyle("display:none");
      Funghi.setSelectStyle("display:none");
      Derivati.setSelectStyle("display:none");
      Conservati.setSelectStyle("display:none");
      Zuppe.setSelectStyle("display:none");
      Vino.setSelectStyle("display:none");
      Grassi.setSelectStyle("display:none");
      
      %>
      
     <%= FruttaFresca.getHtmlSelect("fruttaFresca",-1) %>
       <%= FruttaSecca.getHtmlSelect("fruttaSecca",-1) %>
         <%= Ortaggi.getHtmlSelect("ortaggi",-1) %>
         <%= Funghi.getHtmlSelect("funghi",-1) %>
           <%= Derivati.getHtmlSelect("derivati",-1) %>
           <%= Conservati.getHtmlSelect("conservati",-1) %>
                 <%= Zuppe.getHtmlSelect("zuppe",-1) %>
                       <%= Vino.getHtmlSelect("vino",-1) %>
                             <%= Grassi.getHtmlSelect("grassi",-1) %>
     
   
   
   
   
      </td>
     
      
      
      
       <td style="display:none" id="notealimenti2">
     <center>Descrizione</center> 
     <textarea rows="8" cols="40" name="notealimenti2"></textarea>
     
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
           <td><input type="checkbox" name="alimentinonAnomali"  id="alimentinonAnimali" onclick="abilitaalimentinonAnimali()"  size="20" maxlength="256" /></td>
   
         <td style="display:none" id="alimentinonanimalicella">
         <%=AltriAlimenti.getHtmlSelect("altrialimenti",TicketDetails.getAltrialimenti()) %>
      <center>Descrizione</center>
     <textarea rows="8" cols="40" name="descrizionenonAnimali" id="testoalimentinonanimali"></textarea>
     </td>
         </tr>
       </table>
       </td>
   </tr>
  
     <tr class="containerBody">
        <td valign="top" class="formLabel">
          <dhv:label name="sanzionia.importo">Alimenti Composti</dhv:label>
       </td>
       <td>
         <table class="noborder">
          <tr>
           <td><input type="checkbox" name="alimentiComposti"  id="alimentiComposti" onclick="abilitaTestoAlimentoComposto()"  size="20" maxlength="256" /></td>
           <td style="display:none" id="testoAlimentoComposto">
      <center>Descrizione</center>
     <textarea rows="8" cols="40" name="testoAlimentoComposto" id="testoAlimentoComposto1"></textarea>
     </td>
         </tr>
       </table>
       </td>
   </tr>
  
   
   <!-- alimenti composti -->
  <%--<tr class="containerBody">
        <td valign="top" class="formLabel">
          <dhv:label name="sanzionia.importo">Alimenti Composti</dhv:label>
       </td>
       <td>
         <table class="noborder">
          <tr>
           <td><input type="checkbox" name="alimentiComposti"  id="alimentiComposti" onclick="abilitaTestoAlimentoComposto()" size="20" maxlength="256" /></td>
           <td id="testoAlimentoComposto"><textarea name="testoAlimentoCompost"></textarea></td>
         </tr>
       </table>
       </td> 
   </tr>--%>

    <tr class="containerBody">
       <td valign="top" class="formLabel">
          <dhv:label name="sanzionia.importo">Acqua</dhv:label>
       </td>
       <td>
         <table class="noborder">
          <tr>
           <td>
           		<input type="checkbox" name="alimentiAcqua"  id="acqua" onclick="abilitaAcque()"  size="20" maxlength="256" />
           </td>
           <td style="display:none" id="acquaSelect">
         		<%= Acque.getHtmlSelect("acque",-1) %>
     	   </td>
       		<td style="display:none" id="noteacqua">
       		<center>Descrizione</center>
     			<textarea rows="8" cols="40" name="noteacqua"></textarea>
     		</td>   
         </tr>
       </table>
     </td>
   </tr>
   
   <tr class="containerBody">
        <td valign="top" class="formLabel">
          <dhv:label name="sanzionia.importo">Bevande</dhv:label>
       </td>
       <td>
         <table class="noborder">
          <tr>
           <td><input type="checkbox" name="alimentiBevande"  id="bevande" onclick="abilitaBevande()"  size="20" maxlength="256" /></td>
           <td style="display:none" id="notebevande">
      <center>Descrizione</center>
     <textarea rows="8" cols="40" name="notebevande" id="notealimentibevande"></textarea>
     
     </td>
         </tr>
       </table>
       </td>
   
     
   </tr>
  
   
   <%-- <tr class="containerBody">
        <td valign="top" class="formLabel">
          <dhv:label name="sanzionia.importo">Alimenti per uso Zootecnico</dhv:label>
       </td>
       <td>
         <table>
          <tr>
           <td><input type="checkbox" name="mangimi"  id="mangimi" onclick="attendiamodefinizione()" size="20" maxlength="256" /></td>
           <td style="display:none" id="notealimenti">
      Note :
     <textarea rows="8" cols="40" name="notealimenti"></textarea>
     
     </td>
         </tr>
       </table>
       </td>
   
     
   </tr> --%>
   
   
   <tr class="containerBody">
        <td valign="top" class="formLabel">
          <dhv:label name="sanzionia.importo">Additivi</dhv:label>
       </td>
       <td>
         <table class="noborder">
          <tr>
           <td><input type="checkbox" name="alimentiAdditivi" onclick="abilitatipoAdditivi()" id="additivi"  size="20" maxlength="256" /></td>
           <td style="display:none" id="noteadditivi">
      <center>Descrizione</center>
     <textarea rows="8" cols="40" name="noteadditivi"></textarea>
     
     </td>
         </tr>
       </table>
       </td>
   
     
   </tr>
   
   
   <tr class="containerBody">
        <td valign="top" class="formLabel">
          <dhv:label name="sanzionia.importo">Materiali a Contatto con Alimenti</dhv:label>
       </td>
       <td>
         <table  class="noborder">
          <tr>
           <td><input type="checkbox" name="materialiAlimenti" onclick="abilitatipomaterialiAlimenti()" id="materialialimenti"  size="20" maxlength="256" /></td>
           <td style="display:none" id="notematerialialimenti">
     <center>Descrizione</center>
     <textarea rows="8" cols="40" name="notematerialialimenti"></textarea>
     
     </td>
         </tr>
       </table>
       </td>
   
     
   </tr>
    
    
    
      <%-- <tr class="containerBody">
        <td valign="top" class="formLabel">
          <dhv:label name="sanzionia.importo">Dolciumi</dhv:label>
       </td>
       <td>
         <table  class="noborder">
          <tr>
           <td><input type="checkbox" name="dolciumi" onclick="abilitatipoDolciumi()" id="dolciumi"  size="20" maxlength="256" /></td>
           <td style="display:none" id="notedolciumi">
     <center>Descrizione</center>
     <textarea rows="8" cols="40" name="notedolciumi"></textarea>
     
     </td>
         </tr>
       </table>
       </td>
   
     
   </tr>--%>

 <%--  <tr class="containerBody">
       <td valign="top" class="formLabel">
          <dhv:label name="sanzionia.importo">Gelati e Dessert</dhv:label>
       </td>
       <td>
         <table  class="noborder">
          	<tr>
           <td><input type="checkbox" name="gelati" onclick="abilitatipoGelati()" id="gelati"  size="20" maxlength="256"/></td>
           <td style="display:none" id="notegelati">
     			<center>Descrizione</center>
     			<textarea rows="8" cols="40" name="notegelati"></textarea>
     	   </td>
         </tr>
       </table>
       </td>
   </tr>--%>

  <tr>
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
  
<!--  fine modifiche -->
   </table> <!--  chiusura tabella generale -->
   </br>

  <table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Esito Campione</dhv:label></strong>
    </th>
	</tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="sanzionia.data_ispezione">Data</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addticket" field="estimatedResolutionDate" timestamp="<%= TicketDetails.getEstimatedResolutionDate() %>" timeZone="<%= TicketDetails.getEstimatedResolutionDateTimeZone() %>"  showTimeZone="false" />
      <%= showAttribute(request, "estimatedResolutionDateError") %>
    </td>
  </tr>
  <dhv:include name="organization.source" none="true">
   <tr>
      <td name="esitoTampone1" id="esitoTampone1" nowrap class="formLabel">
        <dhv:label name="">Esito</dhv:label>
      </td>
    <td>
    <%EsitoCampione.setJsEvent("onChange=mostraFollowUP()"); %>
      <%= EsitoCampione.getHtmlSelect("EsitoTampone",TicketDetails.getEsitoCampione()) %>
      <input type="hidden" name="esitoTampone" value="<%=TicketDetails.getEsitoCampione()%>" >
    </td>
  </tr>
</dhv:include>

<tr id="followup" style="display:none" class="containerBody">
        <td valign="top" class="formLabel">
         Follow Up Positività
    	</td>
     <td >    
       
        <table >
         <tr>
           <td>
              <% ConseguenzePositivita.setJsEvent("onchange=\"javascript:abilitaNote(this.form);\"");%>
             <%= ConseguenzePositivita.getHtmlSelect("conseguenzePositivita",TicketDetails.getConseguenzePositivita()) %>
           </td>
           <td id="note_etichetta">Note per altro </td>
           <td id="note"><input type="text" name="noteEsito" id="noteEsito" value="<%= toHtmlValue(TicketDetails.getNoteEsito()) %>" size="60" maxlength="256" /></td>
         </tr>
         </table>
      </td>
     </tr>      
    

<tr>
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.azioni">Punteggio</dhv:label>
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
   <select name="punteggio" onchange="impostaResponsabilita()">
   
   <option value="0">   0</option>
   <option value="1">   1 </option>
   <option value="7">  7 </option>
   <option value="25">  25 </option>
   
   
   </select>
 
          </td>
         <td>
         &nbsp;
          <%= ResponsabilitaPositivita.getHtmlSelect("responsabilitaPositivita",TicketDetails.getResponsabilitaPositivita()) %> <%= showAttribute(request, "assignedDateError") %>
   
         </td>
         
        </tr>
        
        
        
    </table>
    </td>
    </tr>
     
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.importo">Accensione Sistema Allarme rapido</dhv:label>
    </td>
    <td>
       <input type="checkbox" name="allerta" id="allerta"  size="20" maxlength="256" onchange="abilitaCheckAllerta() " onclick="abilitaFlag()"/>
     </td>
  </tr>
  
  
   <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.importo">Segnalazione per Informazioni</dhv:label>
    </td>
    <td>
       <input type="checkbox" name="segnalazione" id="segnalazione"  size="20" maxlength="256" onchange="abilitaCheckAllerta()" onclick="abilitaFlag()"/>
     </td>
  </tr>


  
<tr>
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.azioni">Ulteriori Note</dhv:label>
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <textarea name="solution" cols="55" rows="8"><%= toString(TicketDetails.getSolution()) %></textarea>
          </td>
          <td valign="top">
            <%= showAttribute(request, "problemError") %>
          </td>
          </tr>
</tr>
 </table>
    </td>
</tr>
     
    
 </table>
  
    </br>
<input type="hidden" name="close" value="">
<input type="hidden" name="refresh" value="-1">
<input type="hidden" name="modified" value="<%=  TicketDetails.getModified() %>" />
<input type="hidden" name="currentDate" value="<%=  request.getAttribute("currentDate") %>" />
<input type="hidden" name="statusId" value="<%=  TicketDetails.getStatusId() %>" />
<input type="hidden" name="trashedDate" value="<%=  TicketDetails.getTrashedDate() %>" />
<%= addHiddenParams(request, "popup|popupType|actionId") %>


<br>
<input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save" onClick="return checkForm(this.form)">
<input type="button" value="Annulla" onClick="window.location.href='AllevamentiVigilanza.do?command=TicketDetails&id=<%= request.getAttribute("idC") %>&orgId=<%=OrgDetails.getOrgId()%>';this.form.dosubmit.value='false';" />
</form>
</body>