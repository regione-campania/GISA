<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.tamponi.base.*" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.osm.base.Organization" scope="request"/>
<body >
<form name="addticket" action="OsmTamponi.do?command=Insert&auto-populate=true" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0" >
<tr>
<td>
  <a href="Osm.do"><dhv:label name="osm.osm">Osm</dhv:label></a> > 
  <a href="Osm.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
  <a href="Osm.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="osm.details">Account Details</dhv:label></a> >
    <a href="Osm.do?command=ViewVigilanza&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Controlli Ufficiali</dhv:label></a> >
    <a href="OsmVigilanza.do?command=TicketDetails&id=<%= request.getAttribute("idC")%>&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Controllo Ufficiale</dhv:label></a> >
    
  <%--a href="Osm.do?command=ViewTamponi&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="tamponi">Tamponi</dhv:label></a> --%>
  <dhv:label name="tamponi.aggiungi">Aggiungi Tampone</dhv:label>
</td>
</tr>
</table >
<%-- End Trails --%>
<input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save" onClick="return checkForm(this.form)">
<input type="submit" value="Annulla" onClick="javascript:this.form.action='OsmVigilanza.do?command=TicketDetails&id=<%= request.getAttribute("idC")%>&orgId=<%=OrgDetails.getOrgId()%>'">
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
<jsp:useBean id="EsitoTampone" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="RicercaTamponi_1" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi_2" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi_3" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi_4" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi_5" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi_6" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi_7" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi_8" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi_9" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi_10" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Tamponi" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi2_1" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi2_2" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi2_3" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi2_4" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi2_5" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi2_6" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi2_7" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi2_8" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi2_9" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi2_10" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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
<jsp:useBean id="AlimentiNonTrasformati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiNonTrasformatiValori" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ConseguenzePositivita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ResponsabilitaPositivita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiTrasformati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiVegetali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoTampone" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolvedByDeptList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.tamponi.base.Ticket" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SanzioniAmministrative" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SeverityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ticketStateList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Recipient" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="DestinatarioTampone" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="causeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolutionList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EscalationList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgList" class="org.aspcfs.modules.osm.base.OrganizationList" scope="request"/>
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
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popOsm.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popServiceContracts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAssets.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProducts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></SCRIPT>
<script language="JavaScript">
//abilita il componente ed il nucleo





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

//aggiunto da d.dauria
/*function abilitaTestoAlimentoComposto()
{
 //alimenti = document.getElementById("alimentiComposti");
 testo = document.getElementById("testoAlimentoComposto"); 
 if (alimenti.checked)
 {
  testo.style.visibility = "visible";
 }
 else
 {
  testo.style.visibility = "hidden";
 } 
}*/



function showRow1_tampone1(){



	if(document.getElementById("check1_1").checked==true){
	document.getElementById("row1tampone1").style.display="block";
	document.getElementById("check2_1").disabled=true;
	}else{
		document.getElementById("check2_1").disabled=false;
		document.getElementById("row1tampone1").style.display="none";
		if(document.getElementById("check2_1").checked==true){
			document.getElementById("check1_1").disabled=true;
			document.getElementById("row2tampone1").style.display="block";
		}
		else{
			document.getElementById("check1_1").disabled=false;
			document.getElementById("row2tampone1").style.display="none";
		}
		}
	
	
	


	
}

function showRow1_tampone2(){

	if(document.getElementById("check1_2").checked==true){
		document.getElementById("row1tampone2").style.display="block";
		document.getElementById("check2_2").disabled=true;
		}else{
			document.getElementById("check2_2").disabled=false;
			document.getElementById("row1tampone2").style.display="none";
			if(document.getElementById("check2_2").checked==true){
				document.getElementById("check1_2").disabled=true;
				document.getElementById("row2tampone2").style.display="block";
			}
			else{
				document.getElementById("check1_2").disabled=false;
				document.getElementById("row2tampone2").style.display="none";
			}
			}
	
}

function showRow1_tampone3(){

	if(document.getElementById("check1_3").checked==true){
		document.getElementById("row1tampone3").style.display="block";
		document.getElementById("check2_3").disabled=true;
		}else{
			document.getElementById("check2_3").disabled=false;
			document.getElementById("row1tampone3").style.display="none";
			if(document.getElementById("check2_3").checked==true){
				document.getElementById("check1_3").disabled=true;
				document.getElementById("row2tampone3").style.display="block";
			}
			else{
				document.getElementById("check1_3").disabled=false;
				document.getElementById("row2tampone3").style.display="none";
			}
			}
	
}


function showRow1_tampone4(){

	if(document.getElementById("check1_4").checked==true){
		document.getElementById("row1tampone4").style.display="block";
		document.getElementById("check2_4").disabled=true;
		}else{
			document.getElementById("check2_4").disabled=false;
			document.getElementById("row1tampone4").style.display="none";
			if(document.getElementById("check2_4").checked==true){
				document.getElementById("check1_4").disabled=true;
				document.getElementById("row2tampone4").style.display="block";
			}
			else{
				document.getElementById("check1_4").disabled=false;
				document.getElementById("row2tampone4").style.display="none";
			}
			}
	
}


function showRow1_tampone5(){

	if(document.getElementById("check1_5").checked==true){
		document.getElementById("row1tampone5").style.display="block";
		document.getElementById("check2_5").disabled=true;
		}else{
			document.getElementById("check2_5").disabled=false;
			document.getElementById("row1tampone5").style.display="none";
			if(document.getElementById("check2_5").checked==true){
				document.getElementById("check1_5").disabled=true;
				document.getElementById("row2tampone5").style.display="block";
			}
			else{
				document.getElementById("check1_5").disabled=false;
				document.getElementById("row2tampone5").style.display="none";
			}
			}
	
}


function showRow1_tampone6(){

	if(document.getElementById("check1_6").checked==true){
		document.getElementById("row1tampone6").style.display="block";
		document.getElementById("check2_6").disabled=true;
		}else{
			document.getElementById("check2_6").disabled=false;
			document.getElementById("row1tampone6").style.display="none";
			if(document.getElementById("check2_6").checked==true){
				document.getElementById("check1_6").disabled=true;
				document.getElementById("row2tampone6").style.display="block";
			}
			else{
				document.getElementById("check1_6").disabled=false;
				document.getElementById("row2tampone6").style.display="none";
			}
			}
	
}


function showRow1_tampone7(){

	if(document.getElementById("check1_7").checked==true){
		document.getElementById("row1tampone7").style.display="block";
		document.getElementById("check2_7").disabled=true;
		}else{
			document.getElementById("check2_7").disabled=false;
			document.getElementById("row1tampone7").style.display="none";
			if(document.getElementById("check2_7").checked==true){
				document.getElementById("check1_7").disabled=true;
				document.getElementById("row2tampone7").style.display="block";
			}
			else{
				document.getElementById("check1_7").disabled=false;
				document.getElementById("row2tampone7").style.display="none";
			}
			}
	
}


function showRow1_tampone8(){

	if(document.getElementById("check1_8").checked==true){
		document.getElementById("row1tampone8").style.display="block";
		document.getElementById("check2_8").disabled=true;
		}else{
			document.getElementById("check2_8").disabled=false;
			document.getElementById("row1tampone8").style.display="none";
			if(document.getElementById("check2_8").checked==true){
				document.getElementById("check1_8").disabled=true;
				document.getElementById("row2tampone8").style.display="block";
			}
			else{
				document.getElementById("check1_8").disabled=false;
				document.getElementById("row2tampone8").style.display="none";
			}
			}
	
}


function showRow1_tampone9(){

	if(document.getElementById("check1_9").checked==true){
		document.getElementById("row1tampone9").style.display="block";
		document.getElementById("check2_9").disabled=true;
		}else{
			document.getElementById("check2_9").disabled=false;
			document.getElementById("row1tampone9").style.display="none";
			if(document.getElementById("check2_9").checked==true){
				document.getElementById("check1_9").disabled=true;
				document.getElementById("row2tampone9").style.display="block";
			}
			else{
				document.getElementById("check1_9").disabled=false;
				document.getElementById("row2tampone9").style.display="none";
			}
			}
	
}


function showRow1_tampone10(){

	if(document.getElementById("check1_10").checked==true){
		document.getElementById("row1tampone10").style.display="block";
		document.getElementById("check2_10").disabled=true;
		}else{
			document.getElementById("check2_10").disabled=false;
			document.getElementById("row1tampone10").style.display="none";
			if(document.getElementById("check2_10").checked==true){
				document.getElementById("check1_10").disabled=true;
				document.getElementById("row2tampone10").style.display="block";
			}
			else{
				document.getElementById("check1_10").disabled=false;
				document.getElementById("row2tampone10").style.display="none";
			}
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
/*
function controlloLookup()
{
     //aggiunto per positività
   // note=document.getElementById("note");
    //note.style.visibility="hidden";
    note2=document.getElementById("note_etichetta");
    note2.style.visibility="hidden";
    //aggiunto da d.dauria
    sel = document.getElementById("lookupNonTrasformati");
    sel.style.visibility = "hidden"; 
    sel2 = document.getElementById("lookupNonTrasformatiValori");
    sel2.style.visibility = "hidden";
    sel3 = document.getElementById("lookupTrasformati");
    sel3.style.visibility = "hidden"; 
    sel4 = document.getElementById("lookupVegetale");
    sel4.style.visibility = "hidden";
    
    //aggiunto da d.dauria per far scomparire il testo degli alimenti composti
    //sel5 = document.getElementById("testoAlimentoComposto");
    //sel5.style.visibility = "hidden";
}*/
//aggiunto per positività
/*function abilitaNote(form)
{
   if(form.conseguenzePositivita.value == 4)
   {
    note=document.getElementById("note");
    note.style.visibility="visible";
    note2=document.getElementById("note_etichetta");
    note2.style.visibility="visible";
   } 
   else
   {
    note=document.getElementById("note");
    note.style.visibility="hidden";
    note2=document.getElementById("note_etichetta");
    note2.style.visibility="hidden";
   }
   
}
*/
/*function abilitaLookupOrigineAnimale()
{
    alimentiOrigine = document.getElementById("alimentiOrigineAnimale");
    sel = document.getElementById("lookupNonTrasformati");
    sel2 = document.getElementById("lookupNonTrasformatiValori");
    sel3 = document.getElementById("lookupTrasformati");
    if(alimentiOrigine.checked)
    { sel.style.visibility = "visible";
      sel3.style.visibility = "visible";
    }
    else
    { sel.style.visibility = "hidden";
      sel2.style.visibility = "hidden";
      sel3.style.visibility = "hidden";
    }  
}
*/

//magic
/*function abilitaSpecie(form)
{
    if((form.alimentiOrigineAnimaleNonTrasformati.value >= 1) && (form.alimentiOrigineAnimaleNonTrasformati.value <= 4))
     {
      sel2 = document.getElementById("lookupNonTrasformatiValori");
      sel2.style.visibility = "visible";
     } 
    else
     {
      sel2 = document.getElementById("lookupNonTrasformatiValori");
      form.alimentiOrigineAnimaleNonTrasformatiValori.value= -1;
      sel2.style.visibility = "hidden";
     } 
     if(form.alimentiOrigineAnimaleNonTrasformati.value == -1)
     {
        sel3 = document.getElementById("lookupTrasformati");
        sel3.style.visibility = "visible";
        sel2 = document.getElementById("lookupNonTrasformatiValori");
        sel2.style.visibility = "hidden";
        form.alimentiOrigineAnimaleNonTrasformatiValori.value= -1;
     }
     else
     {
      sel3 = document.getElementById("lookupTrasformati");
      sel3.style.visibility = "hidden";
     }  
}*/

//aggiunto per magic
/*function disabilitaNonTrasformati(form)
{
   if(form.alimentiOrigineAnimaleTrasformati.value == -1)
     {
        sel3 = document.getElementById("lookupNonTrasformati");
        sel3.style.visibility = "visible";
     }
     else
     {
      sel3 = document.getElementById("lookupNonTrasformati");
      sel3.style.visibility = "hidden";
      sel4 = document.getElementById("lookupNonTrasformatiValori");
      sel4.style.visibility = "hidden";
     }  

}
*/

/*function abilitaLookupOrigineVegetale()
{
    alimentiOrigine = document.getElementById("alimentiOrigineVegetale");
    sel2 = document.getElementById("lookupVegetale");
    if(alimentiOrigine.checked)
    { sel2.style.visibility = "visible";
    }
    else
    { 
     sel2.style.visibility = "hidden";
    }  
}*/



//fine delle modifiche

function showEsitiTampone1(){
	
	var aa=document.forms['addticket'].RicercaTamponi_1;

	for(i=0;i<aa.length;i++){
	var selected=	aa[i].selected;
	document.getElementById("etichetta1").style.display="block";

if(aa[i].value!="-1"){
	if(selected==true){
		
		a=i+1;
		document.getElementById("esitoTampone1_"+aa[i].value).style.display="block";
	}else{
			a=i+1;
			//document.getElementById("etichetta1").style.display="none";
			document.getElementById("esitoTampone1_"+aa[i].value).style.display="none";
			
			}
}
}
	mostraNextTampone2();
	
}


function showEsitiTampone2(){
	
	var aa=document.forms['addticket'].RicercaTamponi_2;

	for(i=0;i<aa.length;i++){
	var selected=	aa[i].selected;

	if(aa[i].value!="-1"){

	document.getElementById("etichetta2").style.display="block";

	if(selected==true){
		
		a=i+1;
		document.getElementById("esitoTampone2_"+aa[i].value).style.display="block";
		}else{
			a=i+1;
			//document.getElementById("etichetta2").style.display="none";
			document.getElementById("esitoTampone2_"+aa[i].value).style.display="none";
			
			}
		
}}
	mostraNextTampone3();
}

function showEsitiTampone3(){
	a=0;
	var aa=document.forms['addticket'].RicercaTamponi_3;

	for(i=0;i<aa.length;i++){
	var selected=	aa[i].selected;

	if(aa[i].value!="-1"){

	document.getElementById("etichetta3").style.display="block";

	if(selected==true){
		
		a=i+1;
		document.getElementById("esitoTampone3_"+aa[i].value).style.display="block";
		}else{
			a=i+1;
			//document.getElementById("etichetta3").style.display="none";
			document.getElementById("esitoTampone3_"+aa[i].value).style.display="none";
			
			}
		
}}
	mostraNextTampone4();
}

function showEsitiTampone4(){
	
	var aa=document.forms['addticket'].RicercaTamponi_4;

	for(i=0;i<aa.length;i++){
	var selected=	aa[i].selected;

	if(aa[i].value!="-1"){

	document.getElementById("etichetta4").style.display="block";

	if(selected==true){
		
		a=i+1;
		document.getElementById("esitoTampone4_"+aa[i].value).style.display="block";
		}else{
			a=i+1;
			//document.getElementById("etichetta4").style.display="none";
			document.getElementById("esitoTampone4_"+aa[i].value).style.display="none";
			
			}
		
}}
	mostraNextTampone5();
}

function showEsitiTampone5(){
	
	var aa=document.forms['addticket'].RicercaTamponi_5;

	for(i=0;i<aa.length;i++){
	var selected=	aa[i].selected;

	if(aa[i].value!="-1"){

	document.getElementById("etichetta5").style.display="block";

	if(selected==true){
		
		a=i+1;
		document.getElementById("esitoTampone5_"+aa[i].value).style.display="block";
		}else{
			a=i+1;
			//document.getElementById("etichetta5").style.display="none";
			document.getElementById("esitoTampone5_"+aa[i].value).style.display="none";
			
			}
	}
}
	mostraNextTampone6();
}

function showEsitiTampone6(){
	
	var aa=document.forms['addticket'].RicercaTamponi_6;

	for(i=0;i<aa.length;i++){
	var selected=	aa[i].selected;

	if(aa[i].value!="-1"){

	document.getElementById("etichetta6").style.display="block";

	if(selected==true){
		
		a=i+1;
		document.getElementById("esitoTampone6_"+aa[i].value).style.display="block";
		}else{
			a=i+1;
			//document.getElementById("etichetta6").style.display="none";
			document.getElementById("esitoTampone6_"+aa[i].value).style.display="none";
			
			}
	}
}
	mostraNextTampone7();
}

function showEsitiTampone7(){
	
	var aa=document.forms['addticket'].RicercaTamponi_7;

	for(i=0;i<aa.length;i++){
	var selected=	aa[i].selected;

	if(aa[i].value!="-1"){

	document.getElementById("etichetta7").style.display="block";

	if(selected==true){
		
		a=i+1;
		document.getElementById("esitoTampone7_"+aa[i].value).style.display="block";
		}else{
			a=i+1;
			//document.getElementById("etichetta7").style.display="none";
			document.getElementById("esitoTampone7_"+aa[i].value).style.display="none";
			
			}
		
}}
	mostraNextTampone8();
}

function showEsitiTampone8(){
	
	var aa=document.forms['addticket'].RicercaTamponi_8;

	for(i=0;i<aa.length;i++){
	var selected=	aa[i].selected;

	if(aa[i].value!="-1"){

	document.getElementById("etichetta7").style.display="block";

	if(selected==true){
		
		a=i+1;
		document.getElementById("esitoTampone8_"+aa[i].value).style.display="block";
		}else{
			a=i+1;
			//document.getElementById("etichetta8").style.display="none";
			document.getElementById("esitoTampone8_"+aa[i].value).style.display="none";
			
			}
		
}}
	mostraNextTampone9();
}

function showEsitiTampone9(){
	
	var aa=document.forms['addticket'].RicercaTamponi_9;

	for(i=0;i<aa.length;i++){
	var selected=	aa[i].selected;

	if(aa[i].value!="-1"){

	document.getElementById("etichetta9").style.display="block";

	if(selected==true){
		
		a=i+1;
		document.getElementById("esitoTampone9_"+aa[i].value).style.display="block";
		}else{
			a=i+1;
			//document.getElementById("etichetta9").style.display="none";
			document.getElementById("esitoTampone9_"+aa[i].value).style.display="none";
			
			}
		
}}
	mostraNextTampone10();
}

function showEsitiTampone10(){
	
	var aa=document.forms['addticket'].RicercaTamponi_10;

	for(i=0;i<aa.length;i++){
	var selected=	aa[i].selected;

	if(aa[i].value!="-1"){

	document.getElementById("etichetta10").style.display="block";

	if(selected==true){
		
		a=i+1;
		document.getElementById("esitoTampone10_"+aa[i].value).style.display="block";
		}else{
			a=i+1;
			//document.getElementById("etichetta10").style.display="none";
			document.getElementById("esitoTampone10_"+aa[i].value).style.display="none";
		
			}
		
}}
}


function showEsitiTipo2_tampone1(){
	
	var bb=document.forms['addticket'].RicercaTamponi2_1;
	
	for(c=0;c<bb.length;c++){
	document.getElementById("etichetta12").style.display="block";
	var selected1=	bb[c].selected;

	if(bb[c].value!="-1"){
	if(selected1==true){
		
		b=c+1;
		
		document.getElementById("esito_"+bb[c].value+"_tampone1").style.display="block";
		}else{
			b=c+1;
			document.getElementById("esito_"+bb[c].value+"_tampone1").style.display="none";
			//document.getElementById("etichetta12").style.display="none";
			}
		
}}
	mostraNextTampone2();
}
function showEsitiTipo2_tampone2(){
	
	var bb=document.forms['addticket'].RicercaTamponi2_2;

	for(c=0;c<bb.length;c++){

	var selected1=	bb[c].selected;

	if(bb[c].value!="-1"){

	document.getElementById("etichetta21").style.display="block";

	if(selected1==true){
		
		b=c+1;
		
		document.getElementById("esito_"+bb[c].value+"_tampone2").style.display="block";
		}else{
			b=c+1;
			//document.getElementById("etichetta21").style.display="none";
			document.getElementById("esito_"+bb[c].value+"_tampone2").style.display="none";
			
			}
		
}}
	mostraNextTampone3();
}
function showEsitiTipo2_tampone3(){
	
	var bb=document.forms['addticket'].RicercaTamponi2_3;

	for(c=0;c<bb.length;c++){

	var selected1=	bb[c].selected;

	if(bb[c].value!="-1"){

	document.getElementById("etichetta31").style.display="block";

	if(selected1==true){
		
		b=c+1;
		
		document.getElementById("esito_"+bb[c].value+"_tampone3").style.display="block";
		}else{
			b=c+1;
			//document.getElementById("etichetta31").style.display="none";
			document.getElementById("esito_"+bb[c].value+"_tampone3").style.display="none";
			
			}
		
}}
	mostraNextTampone4();
}

function showEsitiTipo2_tampone4(){
	
	var bb=document.forms['addticket'].RicercaTamponi2_4;

	for(c=0;c<bb.length;c++){

	var selected1=	bb[c].selected;

	if(bb[c].value!="-1"){

	document.getElementById("etichetta41").style.display="block";

	if(selected1==true){
		
		b=c+1;
		
		document.getElementById("esito_"+bb[c].value+"_tampone4").style.display="block";
		}else{
			b=c+1;
			//document.getElementById("etichetta41").style.display="none";
			document.getElementById("esito_"+bb[c].value+"_tampone4").style.display="none";
			
			}
	}
}
	mostraNextTampone5();
}

function showEsitiTipo2_tampone5(){
	
	var bb=document.forms['addticket'].RicercaTamponi2_5;

	for(c=0;c<bb.length;c++){

	var selected1=	bb[c].selected;

	if(bb[c].value!="-1"){

	document.getElementById("etichetta51").style.display="block";

	if(selected1==true){
		
		b=c+1;
		
		document.getElementById("esito_"+bb[c].value+"_tampone5").style.display="block";
		}else{
			b=c+1;
			//document.getElementById("etichetta51").style.display="none";
			document.getElementById("esito_"+bb[c].value+"_tampone5").style.display="none";
			
			}
		
}}
	mostraNextTampone6();
}

function showEsitiTipo2_tampone6(){
	
	var bb=document.forms['addticket'].RicercaTamponi2_6;

	for(c=0;c<bb.length;c++){

	var selected1=	bb[c].selected;

	if(bb[c].value!="-1"){

	document.getElementById("etichetta61").style.display="block";

	if(selected1==true){
		
		b=c+1;
		
		document.getElementById("esito_"+bb[c].value+"_tampone6").style.display="block";
		}else{
			b=c+1;
			//document.getElementById("etichetta61").style.display="none";
			document.getElementById("esito_"+bb[c].value+"_tampone6").style.display="none";
			
			}
		
}}
	mostraNextTampone7();
}

function showEsitiTipo2_tampone7(){
	
	var bb=document.forms['addticket'].RicercaTamponi2_7;

	for(c=0;c<bb.length;c++){

	var selected1=	bb[c].selected;

	if(bb[c].value!="-1"){

	document.getElementById("etichetta71").style.display="block";

	if(selected1==true){
		
		b=c+1;
		
		document.getElementById("esito_"+bb[c].value+"_tampone7").style.display="block";
		}else{
			b=c+1;
			//document.getElementById("etichetta71").style.display="none";
			document.getElementById("esito_"+bb[c].value+"_tampone7").style.display="none";
			
			}
		
}}
	mostraNextTampone8();
}

function showEsitiTipo2_tampone8(){
	
	var bb=document.forms['addticket'].RicercaTamponi2_8;

	for(c=0;c<bb.length;c++){

		var selected1=	bb[c].selected;
		if(bb[c].value!="-1"){
	document.getElementById("etichetta81").style.display="block";
	if(selected1==true){
		
		b=c+1;
		
		document.getElementById("esito_"+bb[c].value+"_tampone8").style.display="block";
		}else{
			b=c+1;
			//document.getElementById("etichetta81").style.display="none";
			document.getElementById("esito_"+bb[c].value+"_tampone8").style.display="none";
			
			}
		}
}
	mostraNextTampone9();
}

function showEsitiTipo2_tampone9(){
	
	var bb=document.forms['addticket'].RicercaTamponi2_9;

	for(c=0;c<bb.length;c++){

	var selected1=	bb[c].selected;

	if(bb[c].value!="-1"){

	document.getElementById("etichetta91").style.display="block";

	if(selected1==true){
		
		b=c+1;
		
		document.getElementById("esito_"+bb[c].value+"_tampone9").style.display="block";
		}else{
			b=c+1;
			//document.getElementById("etichetta91").style.display="none";
			document.getElementById("esito_"+bb[c].value+"_tampone9").style.display="none";
			
			}
		
}}
	mostraNextTampone10();
}

function showEsitiTipo2_tampone10(){
	
	var bb=document.forms['addticket'].RicercaTamponi2_10;

	for(c=0;c<bb.length;c++){

	var selected1=	bb[c].selected;

	if(bb[c].value!="-1"){

	document.getElementById("etichetta101").style.display="block";

	if(selected1==true){
		
		b=c+1;
		
		document.getElementById("esito_"+bb[c].value+"_tampone10").style.display="block";
		}else{
			b=c+1;
			//document.getElementById("etichetta101").style.display="none";
			document.getElementById("esito_"+bb[c].value+"_tampone10").style.display="none";
			
			}
		
}}
}



  function updateCategoryList() {
    var orgId = document.forms['addticket'].orgId.value;
    var url = 'TroubleTicketsTamponi.do?command=CategoryJSList&form=addticket&reset=true&orgId='+orgId;
    window.frames['server_commands'].location.href=url;
  }
<dhv:include name="ticket.catCode" none="false">
  function updateSubList1() {
    var orgId = document.forms['addticket'].orgId.value;
    if(orgId != '-1'){
      var sel = document.forms['addticket'].elements['catCode'];
      var value = sel.options[sel.selectedIndex].value;
      var url = "TroubleTicketsTamponi.do?command=CategoryJSList&form=addticket&catCode=" + escape(value)+'&orgId='+orgId;
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
    var url = "TroubleTicketsTamponi.do?command=CategoryJSList&form=addticket&subCat1=" + escape(value)+'&orgId='+orgId;
    window.frames['server_commands'].location.href=url;
  }
</dhv:include>
<dhv:include name="ticket.subCat2" none="true">
  function updateSubList3() {
    var orgId = document.forms['addticket'].orgId.value;
    var sel = document.forms['addticket'].elements['subCat2'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTicketsTamponi.do?command=CategoryJSList&form=addticket&subCat2=" + escape(value)+'&orgId='+orgId;
    window.frames['server_commands'].location.href=url;
  }
</dhv:include>
<dhv:include name="ticket.subCat3" none="true">
  function updateSubList4() {
    var orgId = document.forms['addticket'].orgId.value;
    var sel = document.forms['addticket'].elements['subCat3'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTicketsTamponi.do?command=CategoryJSList&form=addticket&subCat3=" + escape(value)+'&orgId='+orgId;
    window.frames['server_commands'].location.href=url;
  }
</dhv:include>
  function updateUserList() {
    var sel = document.forms['addticket'].elements['departmentCode'];
    var value = sel.options[sel.selectedIndex].value;
    var orgSite = document.forms['addticket'].elements['orgSiteId'].value;
    var url = "TroubleTicketsTamponi.do?command=DepartmentJSList&form=addticket&dept=Assigned&orgSiteId="+ orgSite +"&populateResourceAssigned=true&resourceAssignedDepartmentCode=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
  function updateResolvedByUserList() {
    var sel = document.forms['addticket'].elements['resolvedByDeptCode'];
    var value = sel.options[sel.selectedIndex].value;
    var orgSite = document.forms['addticket'].elements['orgSiteId'].value;
    var url = "TroubleTicketsTamponi.do?command=DepartmentJSList&form=addticket&dept=Resolved&orgSiteId="+ orgSite + "&populateResolvedBy=true&resolvedByDepartmentCode=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
  
  function updateAllUserLists() {
    var sel = document.forms['addticket'].elements['departmentCode'];
    var value = sel.options[sel.selectedIndex].value;
    var orgSite = document.forms['addticket'].elements['orgSiteId'].value;
    var sel2 = document.forms['addticket'].elements['resolvedByDeptCode'];
    var value2 = sel2.options[sel2.selectedIndex].value;
    var url = "TroubleTicketsTamponi.do?command=DepartmentJSList&form=addticket&orgSiteId="+ orgSite +"&populateResourceAssigned=true&populateResolvedBy=true&resourceAssignedDepartmentCode=" + escape(value)+'&resolveByDepartmentCode='+ escape(value2);
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

    var url = "TroubleTicketsTamponi.do?command=OrganizationJSList" + params; 
    window.frames['server_commands'].location.href=url;
  </dhv:include>
  }
  function checkForm(form){
    formTest = true;
    message = "";


    if(form.check1_1.checked==false && form.check2_1.checked==false){

    	  message += label("check.tamponi.richiedente.selezionato","- Controllare che almeno un tampone sia stato inserito\r\n");
          formTest = false;
    	
    }else{
            	
    if(form.check1_1.checked==true){

   
    	if(form.Tamponi1.value=="-1"){
    		 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver selezionato una voce per Carcassa per il Tampone 1\r\n");
    	      formTest = false;

    		}else{

    		array=form.RicercaTamponi_1;

    		var flag=0;
    		for( k=0 ; k<array.length ; k++){

    			

    			
    			if(array[k].value=="-1" && array[k].selected==true){
    				flag=1;
    				 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 1\r\n");
    			      formTest = false;

    				}
    		}

    		
    	
    	

    		
    			}

    	

    	
    }else{


    	if(form.check2_1.checked==true){


    		if(form.superfice1.value==""){
    			 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 1\r\n");
    		      formTest = false;

    			}else{

    			array=form.RicercaTamponi2_1;

    			var flag=0;
    			for( k=0 ; k<array.length ; k++){

    				

    				
    				if(array[k].value=="-1" && array[k].selected==true){
    					flag=1;
    					 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 1\r\n");
    				      formTest = false;

    					}
    		
    				}
    					

    			
    		
    		

    			
    				}

    		

    		
    	}







    	
    }


    	
    }
    
    

   

    if(form.location.value==""){
    	message += label("check.tamponi.richiedente.selezionato","- Controllare cher il \"Numero Verbale\" sia stato riempito\r\n");
        formTest = false;
    	    }
    if(form.DestinatarioTampone.value=="-1"){
    	message += label("check.tamponi.richiedente.selezionato","- Controllare cher il campo \"Laboratorio di Destinazione\" sia stato selezionato\r\n");
        formTest = false;
    	    }






if(form.check1_2.checked==true){


	if(form.Tamponi2.value=="-1"){
		 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 2\r\n");
	      formTest = false;

		}else{

		array=form.RicercaTamponi_2;

		var flag=0;
		for( k=0 ; k<array.length ; k++){

			

			
			if(array[k].value=="-1" && array[k].selected==true){
				flag=1;
				 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 2\r\n");
			      formTest = false;

				}
	
			}
				

		
	
	

		
			}

	

	
}else{


	if(form.check2_2.checked==true){


		if(form.superfice2.value==""){
			 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 2\r\n");
		      formTest = false;

			}else{

			array=form.RicercaTamponi2_2;

			var flag=0;
			for( k=0 ; k<array.length ; k++){

				

				
				if(array[k].value=="-1" && array[k].selected==true){
					flag=1;
					 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 2\r\n");
				      formTest = false;

					}
		
				}
					

			
		
		

			
				}

		

		
	}







	
}








if(form.check1_3.checked==true){


	if(form.Tamponi3.value=="-1"){
		 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 3\r\n");
	      formTest = false;

		}else{

		array=form.RicercaTamponi_3;

		var flag=0;
		for( k=0 ; k<array.length ; k++){

			

			
			if(array[k].value=="-1" && array[k].selected==true){
				flag=1;
				 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 3\r\n");
			      formTest = false;

				}
	
			}
				

		
	
	

		
			}

	

	
}else{


	if(form.check2_3.checked==true){


		if(form.superfice3.value==""){
			 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 3\r\n");
		      formTest = false;

			}else{

			array=form.RicercaTamponi2_3;

			var flag=0;
			for( k=0 ; k<array.length ; k++){

				

				
				if(array[k].value=="-1" && array[k].selected==true){
					flag=1;
					 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 3\r\n");
				      formTest = false;

					}
		
				}
					

			
		
		

			
				}

		

		
	}







	
}
    








if(form.check1_4.checked==true){


	if(form.Tamponi4.value=="-1"){
		 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 4\r\n");
	      formTest = false;

		}else{

		array=form.RicercaTamponi_4;

		var flag=0;
		for( k=0 ; k<array.length ; k++){

			

			
			if(array[k].value=="-1" && array[k].selected==true){
				flag=1;
				 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 4\r\n");
			      formTest = false;

				}
	
			}
				

		
	
	

		
			}

	

	
}else{


	if(form.check2_4.checked==true){


		if(form.superfice4.value==""){
			 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 4\r\n");
		      formTest = false;

			}else{

			array=form.RicercaTamponi2_4;

			var flag=0;
			for( k=0 ; k<array.length ; k++){

				

				
				if(array[k].value=="-1" && array[k].selected==true){
					flag=1;
					 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 4\r\n");
				      formTest = false;

					}
		
				}
					

			
		
		

			
				}

		

		
	}







	
}







if(form.check1_5.checked==true){


	if(form.Tamponi5.value=="-1"){
		 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 5\r\n");
	      formTest = false;

		}else{

		array=form.RicercaTamponi_5;

		var flag=0;
		for( k=0 ; k<array.length ; k++){

			

			
			if(array[k].value=="-1" && array[k].selected==true){
				flag=1;
				 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 5\r\n");
			      formTest = false;

				}
	
			}
				

		
	
	

		
			}

	

	
}else{


	if(form.check2_5.checked==true){


		if(form.superfice5.value==""){
			 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 5\r\n");
		      formTest = false;

			}else{

			array=form.RicercaTamponi2_5;

			var flag=0;
			for( k=0 ; k<array.length ; k++){

				

				
				if(array[k].value=="-1" && array[k].selected==true){
					flag=1;
					 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 5\r\n");
				      formTest = false;

					}
		
				}
					

			
		
		

			
				}

		

		
	}







	
}








if(form.check1_6.checked==true){


	if(form.Tamponi6.value=="-1"){
		 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 6\r\n");
	      formTest = false;

		}else{

		array=form.RicercaTamponi_6;

		var flag=0;
		for( k=0 ; k<array.length ; k++){

			

			
			if(array[k].value=="-1" && array[k].selected==true){
				flag=1;
				 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 6\r\n");
			      formTest = false;

				}
	
			}
				

		
	
	

		
			}

	

	
}else{


	if(form.check2_6.checked==true){


		if(form.superfice6.value==""){
			 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 6\r\n");
		      formTest = false;

			}else{

			array=form.RicercaTamponi2_6;

			var flag=0;
			for( k=0 ; k<array.length ; k++){

				

				
				if(array[k].value=="-1" && array[k].selected==true){
					flag=1;
					 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 6\r\n");
				      formTest = false;

					}
		
				}
					

			
		
		

			
				}

		

		
	}







	
}











if(form.check1_7.checked==true){


	if(form.Tamponi7.value=="-1"){
		 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 7\r\n");
	      formTest = false;

		}else{

		array=form.RicercaTamponi_7;

		var flag=0;
		for( k=0 ; k<array.length ; k++){

			

			
			if(array[k].value=="-1" && array[k].selected==true){
				flag=1;
				 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 7\r\n");
			      formTest = false;

				}
	
			}
				

		
	
	

		
			}

	

	
}else{


	if(form.check2_7.checked==true){


		if(form.superfice7.value==""){
			 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 7\r\n");
		      formTest = false;

			}else{

			array=form.RicercaTamponi2_7;

			var flag=0;
			for( k=0 ; k<array.length ; k++){

				

				
				if(array[k].value=="-1" && array[k].selected==true){
					flag=1;
					 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 7\r\n");
				      formTest = false;

					}
				}
					

			
		
		

			
				}

		

		
	}



	
}





if(form.check1_8.checked==true){


	if(form.Tamponi8.value=="-1"){
		 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 8\r\n");
	      formTest = false;

		}else{

		array=form.RicercaTamponi_8;

		var flag=0;
		for( k=0 ; k<array.length ; k++){

			

			
			if(array[k].value=="-1" && array[k].selected==true){
				flag=1;
				 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 8\r\n");
			      formTest = false;

				}
	
			}
				

		
	
	

		
			}

	

	
}else{


	if(form.check2_8.checked==true){


		if(form.superfice8.value==""){
			 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 8\r\n");
		      formTest = false;

			}else{

			array=form.RicercaTamponi2_8;

			var flag=0;
			for( k=0 ; k<array.length ; k++){

				

				
				if(array[k].value=="-1" && array[k].selected==true){
					flag=1;
					 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 8\r\n");
				      formTest = false;

					}
		
				}
					

			
		
		

			
				}

		

		
	}



	
}





if(form.check1_9.checked==true){


	if(form.Tamponi9.value=="-1"){
		 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 9\r\n");
	      formTest = false;

		}else{

		array=form.RicercaTamponi_9;

		var flag=0;
		for( k=0 ; k<array.length ; k++){

			

			
			if(array[k].value=="-1" && array[k].selected==true){
				flag=1;
				 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 9\r\n");
			      formTest = false;

				}
	
			}
				

		
	
	

		
			}

	

	
}else{


	if(form.check2_9.checked==true){


		if(form.superfice9.value==""){
			 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 9\r\n");
		      formTest = false;

			}else{

			array=form.RicercaTamponi2_9;

			var flag=0;
			for( k=0 ; k<array.length ; k++){

				

				
				if(array[k].value=="-1" && array[k].selected==true){
					flag=1;
					 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 9\r\n");
				      formTest = false;

					}
		
				}
					

			
		
		

			
				}

		

		
	}



	
}









if(form.check1_10.checked==true){


	if(form.Tamponi10.value=="-1"){
		 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 10\r\n");
	      formTest = false;

		}else{

		array=form.RicercaTamponi_10;

		var flag=0;
		for( k=0 ; k<array.length ; k++){

			

			
			if(array[k].value=="-1" && array[k].selected==true){
				flag=1;
				 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 10\r\n");
			      formTest = false;

				}
	
			}
				

		
	
	

		
			}

	

	
}else{


	if(form.check2_10.checked==true){


		if(form.superfice10.value==""){
			 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 10\r\n");
		      formTest = false;

			}else{

			array=form.RicercaTamponi2_10;

			var flag=0;
			for( k=0 ; k<array.length ; k++){

				

				
				if(array[k].value=="-1" && array[k].selected==true){
					flag=1;
					 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 10\r\n");
				      formTest = false;

					}
		
				}
					

			
		
		

			
				}

		

		
	}



	
}

    //aggiunto da d.dauria
    //alimentiOrigine = document.getElementById("alimentiOrigineAnimale");
    //sel = document.getElementById("lookupNonTrasformati");
    //sel2 = document.getElementById("lookupNonTrasformatiValori");
    //sel3 = document.getElementById("lookupTrasformati");
    //if(((alimentiOrigine.checked) && (form.alimentiOrigineAnimaleNonTrasformati.value == "-1")) && ((alimentiOrigine.checked) && (form.alimentiOrigineAnimaleTrasformati.value == "-1")))
    //{ message +=label("TESTO1","Controllare che il campo Alimenti Origine Animale sia stato riempito\n"); formTest = false; }
  /*  if((alimentiOrigine.checked) &&(form.alimentiOrigineAnimaleTrasformati.value == "-1"))
    { message +=label("TESTO2","Controllare che il campo Alimenti Origine Animale Trasformati sia stato selezionato\n"); formTest = false; }
    */
    
    <dhv:include name="ticket.contact" none="true">
    if (form.siteId.value == "-1") {
      message += label("check.tamponi.richiedente.selezionato","- Controllare che il campo \"A.S.L.\" sia stato popolato\r\n");
      formTest = false;
    }
    </dhv:include>
    <dhv:include name="ticket.contact" none="true">
    if (form.orgId.value == "-1") {
      message += label("check.tamponi.richiedente.selezionato","- Controllare che \"OSM\" sia stato selezionato\r\n");
      formTest = false;
    }
    </dhv:include>
    if (form.assignedDate.value == "") {
      message += label("check.tamponi.data_richiesta.selezionato","- Controllare che il campo \"Data Prelievo\" sia stato popolato\r\n");
      formTest = false;
    }
    //<dhv:include name="ticket.contact" none="true">
    <%--if (form.TipoTampone.value == "-1") {
      message += label("check.tamponi.richiedente.selezionato","- Controllare che \"Tipo di Analisi\" sia stato selezionato\r\n");
      formTest = false;
    }--%>
    //</dhv:include>
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

  function addNewContact(){
    <dhv:permission name="osm-osm-contacts-add">
      var acctPermission = true;
    </dhv:permission>
    <dhv:permission name="osm-osm-contacts-add" none="true">
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



function mostraNextTampone2(){

document.getElementById("tampone2").style.display="";
}

function mostraNextTampone3(){

	document.getElementById("tampone3").style.display="";
	}


function mostraNextTampone4(){

	document.getElementById("tampone4").style.display="";
	}

function mostraNextTampone5(){

	document.getElementById("tampone5").style.display="";
	}


function mostraNextTampone6(){

	document.getElementById("tampone6").style.display="";
	}


function mostraNextTampone7(){

	document.getElementById("tampone7").style.display="";
	}


function mostraNextTampone8(){

	document.getElementById("tampone8").style.display="";
	}


function mostraNextTampone9(){

	document.getElementById("tampone9").style.display="";
	}


function mostraNextTampone10(){

	document.getElementById("tampone10").style.display="";
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
</script>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Aggiungi Tampone</dhv:label></strong>
    </th>
	</tr>
  
   <dhv:include name="osm-sites" none="true">
 <%--  <dhv:evaluate if="<%= SiteIdList.size() > 1 %>"> --%>
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="stabilimenti.site">Site</dhv:label>
      </td>
      <td>
       <%=SiteIdList.getSelectedValue(OrgDetails
										.getSiteId())%>
          <input type="hidden" name="siteId" value="<%=OrgDetails.getSiteId()%>" >
      
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
      <dhv:label name="sanzioni.richiedente">OSM</dhv:label>
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

   <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="">Identificativo C.U.</dhv:label>
    <td>
      <%= (String)request.getAttribute("idControllo") %>
      <input type="hidden" name="idControlloUfficiale" id="idControlloUfficiale" value="<%= (String)request.getParameter("idControllo") %>">
      <input type="hidden" name="idC" id="idC" value="<%= (String)request.getParameter("idC") %>">
    </td>
  </tr>
  
  
  
  
     <tr class="containerBody" >
    <td valign="top" class="formLabel">
      Tamponi<br><br>
      
      (* In caso di selezione multipla tenere premuto il tasto Ctrl durante la Selezione)
      
    </td>
    
    
    <td>
     <table class="noborder">
    <tr>
    <td><b>Tampone 1 &nbsp;&nbsp;&nbsp;&nbsp;</b></td>
     <td>
     <table>
	 <tr><td align="middle" ><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     <tr><td align="middle" ><input type="checkbox" value="1" name="check1_1" id="check1_1" onclick="showRow1_tampone1()" ></td></tr>
     <tr><td align="middle" ><label>Altro</label></td></tr>
     <tr><td align="middle" ><input type="checkbox" value="2" name="check2_1" id="check2_1" onclick="showRow1_tampone1()" ></td></tr>

     </table>
   </td>
     
    <%RicercaTamponi_1.setJsEvent("onChange=showEsitiTampone1()"); %>
    <td>
    <table cellpadding="15">
    <tr valign="top" id="row1tampone1" style="display: none">
    
    <td>Carcassa</br><%=Tamponi.getHtmlSelect("Tamponi1",TicketDetails.getTamponi()) %></td>
    <td>Tipo di Ricerca </br><%=RicercaTamponi_1.getHtmlSelect("RicercaTamponi_1",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta1"  style="display: none">Esiti</label>
    <table id="esitiTampone1">
    <tr id="esitoTampone1_1"  style="display: none"><td>1. <input type="text" name="esitoTampone1_1"></td></tr>
    <tr id="esitoTampone1_2"  style="display: none"><td>2. <input type="text" name="esitoTampone1_2"></td></tr>
    <tr id="esitoTampone1_3"  style="display: none"><td>3. <input type="text" name="esitoTampone1_3"></td></tr>
    <tr id="esitoTampone1_4"  style="display: none"><td>4. <input type="text" name="esitoTampone1_4"></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone1" style="display: none">
    <%RicercaTamponi2_1.setJsEvent("onChange=showEsitiTipo2_tampone1()"); %>
   
    <td> Superficie Testata</br><textarea rows="5" cols="30" name="superfice1"></textarea> </td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi2_1.getHtmlSelect("RicercaTamponi2_1",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta12"  style="display: none">Esiti</label>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone1" style="display: none"><td>1. <input type="text" name="esito_1_tampone1"></td></tr>
    <tr id="esito_2_tampone1" style="display: none"><td>2. <input type="text" name="esito_2_tampone1"></td></tr>
    <tr id="esito_3_tampone1" style="display: none"><td>3. <input type="text" name="esito_3_tampone1"></td></tr>
    <tr id="esito_4_tampone1" style="display: none"><td>4. <input type="text" name="esito_4_tampone1"></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
    
    <tr id="tampone2" style="display: none">
    <td><b>Tampone 2 &nbsp;&nbsp;&nbsp;&nbsp;</b></td>
     <td>
     <table>
     <tr><td align="middle"><label><d>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     <tr><td align="middle"><input type="checkbox" value="1" name="check1_2" id="check1_2" onclick="showRow1_tampone2()"></td></tr>
     <tr><td align="middle"><label>Altro</label></td></tr>
     <tr><td align="middle"><input type="checkbox" value="2" name="check2_2" id="check2_2" onclick="showRow1_tampone2()"></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_2.setJsEvent("onChange=showEsitiTampone2()"); %>
    <td>
    <table cellpadding="15">
    <tr valign="top" id="row1tampone2" style="display: none">
   
    <td>Carcassa</br><%=Tamponi.getHtmlSelect("Tamponi2",TicketDetails.getTamponi()) %></td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi_2.getHtmlSelect("RicercaTamponi_2",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta2"  style="display: none">Esiti</label>
    <table id="esitiTampone1">
    <tr id="esitoTampone2_1" style="display: none"><td>1. <input type="text" name="esitoTampone2_1"></td></tr>
    <tr id="esitoTampone2_2" style="display: none"><td>2. <input type="text" name="esitoTampone2_2"></td></tr>
    <tr id="esitoTampone2_3" style="display: none"><td>3. <input type="text" name="esitoTampone2_3"></td></tr>
    <tr id="esitoTampone2_4" style="display: none"><td>4. <input type="text" name="esitoTampone2_4"></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone2" style="display: none">
    <%RicercaTamponi2_2.setJsEvent("onChange=showEsitiTipo2_tampone2()"); %>
   
    <td>Superficie Testata</br><textarea rows="5" cols="30" name="superfice2"></textarea></td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi2_2.getHtmlSelect("RicercaTamponi2_2",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta21"  style="display: none">Esiti</label>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone2" style="display: none"><td>1. <input type="text" name="esito_1_tampone2"></td></tr>
    <tr id="esito_2_tampone2" style="display: none"><td>2. <input type="text" name="esito_2_tampone2"></td></tr>
    <tr id="esito_3_tampone2" style="display: none"><td>3. <input type="text" name="esito_3_tampone2"></td></tr>
    <tr id="esito_4_tampone2" style="display: none"><td>4. <input type="text" name="esito_4_tampone2"></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
    
    
    
     <tr id="tampone3" style="display: none">
    <td><b>Tampone 3 &nbsp;&nbsp;&nbsp;&nbsp;</b></td>
     <td>
     <table>
     <tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     <tr><td align="middle"><input type="checkbox" value="1" name="check1_3" id="check1_3" onclick="showRow1_tampone3()"></td></tr>
     <tr><td align="middle"><label>Altro</label></td></tr>
     <tr><td align="middle"><input type="checkbox" value="2" name="check2_3" id="check2_3" onclick="showRow1_tampone3()"></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_3.setJsEvent("onChange=showEsitiTampone3()"); %>
    <td>
    <table cellpadding="15">
    <tr valign="top" id="row1tampone3" style="display: none">
   
    <td>Carcassa</br><%=Tamponi.getHtmlSelect("Tamponi3",TicketDetails.getTamponi()) %></td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi_3.getHtmlSelect("RicercaTamponi_3",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta3"  style="display: none">Esiti</label>
    <table id="esitiTampone1">
    <tr id="esitoTampone3_1" style="display: none"><td>1. <input type="text" name="esitoTampone3_1"></td></tr>
    <tr id="esitoTampone3_2" style="display: none"><td>2. <input type="text" name="esitoTampone3_2"></td></tr>
    <tr id="esitoTampone3_3" style="display: none"><td>3. <input type="text" name="esitoTampone3_3"></td></tr>
    <tr id="esitoTampone3_4" style="display: none"><td>4. <input type="text" name="esitoTampone3_4"></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone3" style="display: none">
    <%RicercaTamponi2_3.setJsEvent("onChange=showEsitiTipo2_tampone3()"); %>
   
    <td>Superficie Testata</br><textarea rows="5" cols="30" name="superfice3"></textarea></td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi2_3.getHtmlSelect("RicercaTamponi2_3",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta31"  style="display: none">Esiti</label>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone3" style="display: none"><td>1. <input type="text" name="esito_1_tampone3"></td></tr>
    <tr id="esito_2_tampone3" style="display: none"><td>2. <input type="text" name="esito_2_tampone3"></td></tr>
    <tr id="esito_3_tampone3" style="display: none"><td>3. <input type="text" name="esito_3_tampone3"></td></tr>
    <tr id="esito_4_tampone3" style="display: none"><td>4. <input type="text" name="esito_4_tampone3"></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
    
    
    
     <tr id="tampone4" style="display: none">
    <td><b>Tampone 4 &nbsp;&nbsp;&nbsp;&nbsp;</b></td>
     <td>
     <table>
     <tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     <tr><td align="middle"><input type="checkbox" name="check1_4" value="1" id="check1_4" onclick="showRow1_tampone4()"></td></tr>
     <tr><td align="middle"><label>Altro</label></td></tr>
     <tr><td align="middle"><input type="checkbox" name="check2_4" value="2" id="check2_4" onclick="showRow1_tampone4()"></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_4.setJsEvent("onChange=showEsitiTampone4()"); %>
    <td>
    <table cellpadding="15">
    <tr valign="top" id="row1tampone4" style="display: none">
   
    <td>Carcassa</br><%=Tamponi.getHtmlSelect("Tamponi4",TicketDetails.getTamponi()) %></td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi_4.getHtmlSelect("RicercaTamponi_4",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta4"  style="display: none">Esiti</label>
    <table id="esitiTampone1">
    <tr id="esitoTampone4_1" style="display: none"><td>1. <input type="text" name="esitoTampone4_1"></td></tr>
    <tr id="esitoTampone4_2" style="display: none"><td>2. <input type="text" name="esitoTampone4_2"></td></tr>
    <tr id="esitoTampone4_3" style="display: none"><td>3. <input type="text" name="esitoTampone4_3"></td></tr>
    <tr id="esitoTampone4_4" style="display: none"><td>4. <input type="text" name="esitoTampone4_4"></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone4" style="display: none">
    <%RicercaTamponi2_4.setJsEvent("onChange=showEsitiTipo2_tampone4()"); %>
   
    <td>Superficie Testata</br><textarea rows="5" cols="30" name="superfice4"></textarea></td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi2_4.getHtmlSelect("RicercaTamponi2_4",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta41"  style="display: none">Esiti</label>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone4" style="display: none"><td>1. <input type="text" name="esito_1_tampone4"> </td></tr>
    <tr id="esito_2_tampone4" style="display: none"><td>2. <input type="text" name="esito_2_tampone4"></td></tr>
    <tr id="esito_3_tampone4" style="display: none"><td>3. <input type="text" name="esito_3_tampone4"></td></tr>
    <tr id="esito_4_tampone4" style="display: none"><td>4. <input type="text" name="esito_4_tampone4"></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
    
    
     <tr id="tampone5" style="display: none">
    <td><b>Tampone 5 &nbsp;&nbsp;&nbsp;&nbsp;</b></td>
     <td>
     <table>
     <tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     <tr><td align="middle"><input type="checkbox" name="check1_5" value="1" id="check1_5" onclick="showRow1_tampone5()"></td></tr>
     <tr><td align="middle"><label>Altro</label></td></tr>
     <tr><td align="middle"><input type="checkbox" name="check2_5" value="2" id="check2_5" onclick="showRow1_tampone5()"></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_5.setJsEvent("onChange=showEsitiTampone5()"); %>
    <td>
    <table cellpadding="15">
    <tr valign="top" id="row1tampone5" style="display: none">
   
    <td>Carcassa</br><%=Tamponi.getHtmlSelect("Tamponi5",TicketDetails.getTamponi()) %></td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi_5.getHtmlSelect("RicercaTamponi_5",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta5"  style="display: none">Esiti</label>
    <table id="esitiTampone1">
    <tr id="esitoTampone5_1" style="display: none"><td>1. <input type="text" name="esitoTampone5_1"></td></tr>
    <tr id="esitoTampone5_2" style="display: none"><td>2. <input type="text" name="esitoTampone5_2"></td></tr>
    <tr id="esitoTampone5_3" style="display: none"><td>3. <input type="text" name="esitoTampone5_3"></td></tr>
    <tr id="esitoTampone5_4" style="display: none"><td>4. <input type="text" name="esitoTampone5_4"></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone5" style="display: none">
    <%RicercaTamponi2_5.setJsEvent("onChange=showEsitiTipo2_tampone5()"); %>
   
    <td>Superficie Testata</br><textarea rows="5" cols="30" name="superfice5"></textarea> </td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi2_5.getHtmlSelect("RicercaTamponi2_5",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta51"  style="display: none">Esiti</label>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone5" style="display: none"><td>1. <input type="text" name="esito_1_tampone5"></td></tr>
    <tr id="esito_2_tampone5" style="display: none"><td>2. <input type="text" name="esito_2_tampone5"></td></tr>
    <tr id="esito_3_tampone5" style="display: none"><td>3. <input type="text" name="esito_3_tampone5"></td></tr>
    <tr id="esito_4_tampone5" style="display: none"><td>4. <input type="text" name="esito_4_tampone5"></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
    
     <tr id="tampone6" style="display: none">
    <td><b>Tampone 6 &nbsp;&nbsp;&nbsp;&nbsp;</b></td>
     <td>
     <table>
     <tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     <tr><td align="middle"><input type="checkbox" name="check1_6" value="1" id="check1_6" onclick="showRow1_tampone6()"></td></tr>
     <tr><td align="middle"><label>Altro</label></td></tr>
     <tr><td align="middle"><input type="checkbox" name="check2_6" value="2" id="check2_6" onclick="showRow1_tampone6()"></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_6.setJsEvent("onChange=showEsitiTampone6()"); %>
    <td>
    <table cellpadding="15">
    <tr valign="top" id="row1tampone6" style="display: none">
   
    <td>Carcassa</br><%=Tamponi.getHtmlSelect("Tamponi6",TicketDetails.getTamponi()) %></td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi_6.getHtmlSelect("RicercaTamponi_6",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta6"  style="display: none">Esiti</label>
    <table id="esitiTampone1">
    <tr id="esitoTampone6_1" style="display: none"><td>1. <input type="text" name="esitoTampone6_1"></td></tr>
    <tr id="esitoTampone6_2" style="display: none"><td>2. <input type="text" name="esitoTampone6_2"></td></tr>
    <tr id="esitoTampone6_3" style="display: none"><td>3. <input type="text" name="esitoTampone6_3"></td></tr>
    <tr id="esitoTampone6_4" style="display: none"><td>4. <input type="text" name="esitoTampone6_4"></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone6" style="display: none">
    <%RicercaTamponi2_6.setJsEvent("onChange=showEsitiTipo2_tampone6()"); %>
   
    <td>Superficie Testata</br><textarea rows="5" cols="30" name="superfice6"></textarea></td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi2_6.getHtmlSelect("RicercaTamponi2_6",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta61"  style="display: none">Esiti</label>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone6" style="display: none"><td>1. <input type="text" name="esito_1_tampone6"></td></tr>
    <tr id="esito_2_tampone6" style="display: none"><td>2. <input type="text" name="esito_2_tampone6">></td></tr>
    <tr id="esito_3_tampone6" style="display: none"><td>3. <input type="text" name="esito_3_tampone6">></td></tr>
    <tr id="esito_4_tampone6" style="display: none"><td>4. <input type="text" name="esito_4_tampone6">></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
    
    
    
    
     <tr id="tampone7" style="display: none">
    <td><b>Tampone 7 &nbsp;&nbsp;&nbsp;&nbsp;</b></td>
     <td>
     <table>
     <tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     <tr><td align="middle"><input type="checkbox" name="check1_7" value="1" id="check1_7" onclick="showRow1_tampone7()"></td></tr>
     <tr><td align="middle"><label>Altro</label></td></tr>
     <tr><td align="middle"><input type="checkbox" name="check2_7" value="2" id="check2_7" onclick="showRow1_tampone7()"></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_7.setJsEvent("onChange=showEsitiTampone7()"); %>
    <td>
    <table cellpadding="15">
    <tr valign="top" id="row1tampone7" style="display: none">
   
    <td>Carcassa</br><%=Tamponi.getHtmlSelect("Tamponi7",TicketDetails.getTamponi()) %></td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi_7.getHtmlSelect("RicercaTamponi_7",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta7"  style="display: none">Esiti</label>
    <table id="esitiTampone1">
    <tr id="esitoTampone7_1" style="display: none"><td>1. <input type="text" name="esitoTampone7_1"></td></tr>
    <tr id="esitoTampone7_2" style="display: none"><td>2. <input type="text" name="esitoTampone7_2"></td></tr>
    <tr id="esitoTampone7_3" style="display: none"><td>3. <input type="text" name="esitoTampone7_3"></td></tr>
    <tr id="esitoTampone7_4" style="display: none"><td>4. <input type="text" name="esitoTampone7_4"></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone7" style="display: none">
    <%RicercaTamponi2_7.setJsEvent("onChange=showEsitiTipo2_tampone7()"); %>
   
    <td>Superficie Testata</br><textarea rows="5" cols="30" name="superfice7"></textarea></td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi2_7.getHtmlSelect("RicercaTamponi2_7",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta71"  style="display: none">Esiti</label>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone7" style="display: none"><td>1. <input type="text" name="esito_1_tampone7"></td></tr>
    <tr id="esito_2_tampone7" style="display: none"><td>2. <input type="text" name="esito_2_tampone7"></td></tr>
    <tr id="esito_3_tampone7" style="display: none"><td>3. <input type="text" name="esito_3_tampone7"></td></tr>
    <tr id="esito_4_tampone7" style="display: none"><td>4. <input type="text" name="esito_4_tampone7"></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
    
    
    
    
     <tr id="tampone8" style="display: none">
    <td><b>Tampone 8 &nbsp;&nbsp;&nbsp;&nbsp;</b></td>
     <td>
     <table>
     <tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     <tr><td align="middle"><input type="checkbox" name="check1_8" value="1" id="check1_8" onclick="showRow1_tampone8()"></td></tr>
     <tr><td align="middle"><label>Altro</label></td></tr>
     <tr><td align="middle"><input type="checkbox" name="check2_8" value="2" id="check2_8" onclick="showRow1_tampone8()"></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_8.setJsEvent("onChange=showEsitiTampone8()"); %>
    <td>
    <table cellpadding="15">
    <tr valign="top" id="row1tampone8" style="display: none">
   
    <td>Carcassa</br><%=Tamponi.getHtmlSelect("Tamponi8",TicketDetails.getTamponi()) %></td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi_8.getHtmlSelect("RicercaTamponi_8",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta8"  style="display: none">Esiti</label>
    <table id="esitiTampone1">
    <tr id="esitoTampone8_1" style="display: none"><td>1. <input type="text" name="esitoTampone8_1"></td></tr>
    <tr id="esitoTampone8_2" style="display: none"><td>2. <input type="text" name="esitoTampone8_2"></td></tr>
    <tr id="esitoTampone8_3" style="display: none"><td>3. <input type="text" name="esitoTampone8_3"></td></tr>
    <tr id="esitoTampone8_4" style="display: none"><td>4. <input type="text" name="esitoTampone8_4"></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone8" style="display: none">
    <%RicercaTamponi2_8.setJsEvent("onChange=showEsitiTipo2_tampone8()"); %>
   
    <td>Superficie Testata</br><textarea rows="5" cols="30" name="superfice8"></textarea></td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi2_8.getHtmlSelect("RicercaTamponi2_8",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta81"  style="display: none">Esiti</label>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone8" style="display: none"><td>1. <input type="text" name="esito_1_tampone8"></td></tr>
    <tr id="esito_2_tampone8" style="display: none"><td>2. <input type="text" name="esito_2_tampone8"></td></tr>
    <tr id="esito_3_tampone8" style="display: none"><td>3. <input type="text" name="esito_3_tampone8"></td></tr>
    <tr id="esito_4_tampone8" style="display: none"><td>4. <input type="text" name="esito_4_tampone8"></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
    
    
    
    
     <tr id="tampone9" style="display: none">
    <td><b>Tampone 9 &nbsp;&nbsp;&nbsp;&nbsp;</b></td>
     <td>
     <table>
     <tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05 </label></td></tr>
     <tr><td align="middle"><input type="checkbox" name="check1_9" value="1" id="check1_9" onclick="showRow1_tampone9()"></td></tr>
     <tr><td align="middle"><label>Altro </label></td></tr>
     <tr><td align="middle"><input type="checkbox" name="check2_9" value="2" id="check2_9" onclick="showRow1_tampone9()"></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_9.setJsEvent("onChange=showEsitiTampone9()"); %>
    <td>
    <table cellpadding="15">
    <tr valign="top" id="row1tampone9" style="display: none">
   
    <td>Carcassa</br><%=Tamponi.getHtmlSelect("Tamponi9",TicketDetails.getTamponi()) %></td>
    <td>Tipo di Ricerca </br><%=RicercaTamponi_9.getHtmlSelect("RicercaTamponi_9",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta9"  style="display: none">Esiti</label>
    <table id="esitiTampone1">
    <tr id="esitoTampone9_1" style="display: none"><td>1. <input type="text" name="esitoTampone9_1"></td></tr>
    <tr id="esitoTampone9_2" style="display: none"><td>2. <input type="text" name="esitoTampone9_2"></td></tr>
    <tr id="esitoTampone9_3" style="display: none"><td>3. <input type="text" name="esitoTampone9_3"></td></tr>
    <tr id="esitoTampone9_4" style="display: none"><td>4. <input type="text" name="esitoTampone9_4"></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone9" style="display: none">
    <%RicercaTamponi2_9.setJsEvent("onChange=showEsitiTipo2_tampone9()"); %>
   
    <td>Superficie Testata</br><textarea rows="5" cols="30" name="superfice9"></textarea></td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi2_9.getHtmlSelect("RicercaTamponi2_9",TicketDetails.getRicercaTamponi()) %>;</td>
    <td>
    <label id="etichetta91"  style="display: none">Esiti</label>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone9" style="display: none"><td>1. <input type="text" name="esito_1_tampone9"></td></tr>
    <tr id="esito_2_tampone9" style="display: none"><td>2. <input type="text" name="esito_2_tampone9"></td></tr>
    <tr id="esito_3_tampone9" style="display: none"><td>3. <input type="text" name="esito_3_tampone9"></td></tr>
    <tr id="esito_4_tampone9" style="display: none"><td>4. <input type="text" name="esito_4_tampone9"></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
    
    
    
    
    
     <tr id="tampone10" style="display: none">
    <td><b>Tampone 10 &nbsp;&nbsp;&nbsp;&nbsp;</b></td>
     <td>
     <table>
     <tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     <tr><td align="middle"><input type="checkbox" name="check1_10" value="1" id="check1_10" onclick="showRow1_tampone10()"></td></tr>
     <tr><td align="middle"><label>Altro</label></td></tr>
     <tr><td align="middle"><input type="checkbox" name="check2_10" value="2" id="check2_10" onclick="showRow1_tampone10()"></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_10.setJsEvent("onChange=showEsitiTampone10()"); %>
    <td>
    <table cellpadding="15">
    <tr valign="top" id="row1tampone10" style="display: none">
   
    <td>Carcassa</br><%=Tamponi.getHtmlSelect("Tamponi10",TicketDetails.getTamponi()) %></td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi_10.getHtmlSelect("RicercaTamponi_10",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta10"  style="display: none">Esiti</label>
    <table id="esitiTampone1">
    <tr id="esitoTampone10_1" style="display: none"><td>1. <input type="text" name="esitoTampone10_1"></td></tr>
    <tr id="esitoTampone10_2" style="display: none"><td>2. <input type="text" name="esitoTampone10_2"></td></tr>
    <tr id="esitoTampone10_3" style="display: none"><td>3. <input type="text" name="esitoTampone10_3"></td></tr>
    <tr id="esitoTampone10_4" style="display: none"><td>4. <input type="text" name="esitoTampone10_4"></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone10" style="display: none">
    <%RicercaTamponi2_10.setJsEvent("onChange=showEsitiTipo2_tampone10()"); %>
   
    <td>Superficie Testata</br><textarea rows="5" cols="30" name="superfice10"></textarea></td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi2_10.getHtmlSelect("RicercaTamponi2_10",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta101"  style="display: none">Esiti</label>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone10" style="display: none"><td>1. <input type="text" name="esito_1_tampone10"></td></tr>
    <tr id="esito_2_tampone10" style="display: none"><td>2. <input type="text" name="esito_2_tampone10"></td></tr>
    <tr id="esito_3_tampone10" style="display: none"><td>3. <input type="text" name="esito_3_tampone10"></td></tr>
    <tr id="esito_4_tampone10" style="display: none"><td>4. <input type="text" name="esito_4_tampone10"></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    

    
    </table>
    
    </td>
   </tr>
 
    
  
    
  
  
  
 
  
  
  <tr class="containerBody" >
    <td valign="top" class="formLabel">
      <dhv:label name="">Numero Verbale</dhv:label>
    </td>
    <td>
    <%if(TicketDetails.getLocation() != "" && TicketDetails.getLocation() != null){ %>
      <input type="text" name="location" id="location" value="<%= toHtmlValue(TicketDetails.getLocation()) %>" size="20" maxlength="256" /><font color="red">*</font>
    <%}else{%>
          <input type="text" name="location" id="location" value="" size="20" maxlength="256" /><font color="red">*</font>
    <%} %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="sanzionia.data_richiesta">Data Prelievo</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addticket" field="assignedDate" timestamp="<%= TicketDetails.getAssignedDate() %>"  timeZone="<%= TicketDetails.getAssignedDateTimeZone() %>" showTimeZone="false" />
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
  
<dhv:include name="organization.source" none="true">
   <tr>
      <td name="destinatarioTampone1" id="destinatarioTampone1" nowrap class="formLabel">
        <dhv:label name="">Laboratorio di Destinazione</dhv:label>
      </td>
    <td>
      <%= DestinatarioTampone.getHtmlSelect("DestinatarioTampone",TicketDetails.getDestinatarioTampone()) %>
   <input type="hidden" name="destinatarioTampone" value="<%=TicketDetails.getDestinatarioTampone()%>" > <font color="red">*</font>
    </td>
  </tr>
</dhv:include>
<tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Data Accettazione</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addticket" field="dataAccettazione" timestamp="<%= TicketDetails.getDataAccettazione() %>"  timeZone="<%= TicketDetails.getDataAccettazioneTimeZone() %>" showTimeZone="false" />
    </td>
  </tr>
  
    <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.importo">Codice Accettazione</dhv:label>
    </td>
    <td>
      <input type="text" name="cause" id="cause" value="<%= toHtmlValue(TicketDetails.getCause()) %>" size="20" maxlength="256" />
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
            <select name="punteggio">
            <option value="0">   0 </option>
            <option value="2">   2 </option>
            <option value="4">   4 </option>
            <option value="6">   6 </option>
            <option value="8">   8 </option>
            <option value="10"> 10 </option> 
            </select>
            (Punteggio a scelta dell'Ispettore in funzione della gravità della n.c. rilevata)
          </td>
         
        </tr>
   </table></td></tr>
    
    
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
	



 
    
     
   <br/>
   <!-- fine positività -->
   
 <!-- <table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Esito Tampone</dhv:label></strong>
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
      <%= EsitoTampone.getHtmlSelect("EsitoTampone",TicketDetails.getEsitoTampone()) %>
      <input type="hidden" name="esitoTampone" value="<%=TicketDetails.getEsitoTampone()%>" >
    </td>
  </tr>
 
        
        
        
    </table>
    </td>
    </tr></dhv:include>

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
    </tr>-->
      
    
 </table>
    </br>
    <br><br>
<input type="hidden" name="close" value="">
<input type="hidden" name="refresh" value="-1">
<input type="hidden" name="modified" value="<%=  TicketDetails.getModified() %>" />
<input type="hidden" name="currentDate" value="<%=  request.getAttribute("currentDate") %>" />
<input type="hidden" name="statusId" value="<%=  TicketDetails.getStatusId() %>" />
<input type="hidden" name="trashedDate" value="<%=  TicketDetails.getTrashedDate() %>" />
<%= addHiddenParams(request, "popup|popupType|actionId") %>


<br>
<input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save" onClick="return checkForm(this.form)">
<input type="submit" value="Annulla" onClick="javascript:this.form.action='OsmVigilanza.do?command=TicketDetails&id=<%= request.getAttribute("idC")%>&orgId=<%=OrgDetails.getOrgId()%>'">
</form>
</body>
