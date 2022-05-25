<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*" %>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>

<%if(request.getAttribute("isAllegato")!=null){ %>



<%@page import="com.sun.org.apache.xml.internal.utils.UnImplNode"%>
<%@page import="java.util.Date"%><body onload="controlloLookup();selectLista()">
<%}else{ %>

<body onload="controlloLookup();">

<%} %>

<form name="addticket"  action="TroubleTicketsAllerteNew.do?command=Insert&auto-populate=true" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="TroubleTicketsAllerteNew.do"><dhv:label name="sanzioniss">Allerte</dhv:label></a> > 
<dhv:label name="allerte.aggiungi">Aggiungi Allerta</dhv:label>
</td>
</tr>
</table>

<%
if (request.getAttribute("allerte.insert.error")!=null)
{
	out.print("<font color = 'red'>"+request.getAttribute("allerte.insert.error")+"</font>");
}

%>
<br>
<%-- End Trails --%>
<input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save" onClick="return checkForm(this.form)">
<input type="submit" value="Annulla" onClick="javascript:this.form.action='TroubleTicketsAllerteNew.do?command=Home'">
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
<jsp:useBean id="FruttaFresca" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="FruttaSecca" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Funghi" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Ortaggi" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Derivati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Conservati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Grassi" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Vino" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Zuppe" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AltriAlimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiVegetaliNonTraformati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiVegetaliTraformati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookup_1" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookup_2" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookup_3" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookup_4" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookup_5" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookup_6" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookup_7" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookup_8" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookup_9" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookup_10" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookup_11" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookup_12" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookup_13" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="tipoNotifica" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolvedByDeptList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.allerte_new.base.Ticket" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoAlimento" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Origine" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentoInteressato" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="NonConformita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ListaCommercializzazione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="UnitaMisura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiNonTrasformati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiNonTrasformatiValori" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Amministrative" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Penali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Sequestri" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SeverityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ticketStateList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Recipient" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="causeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolutionList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EscalationList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgList" class="org.aspcfs.modules.accounts.base.OrganizationList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
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
<jsp:useBean id="AlimentiTrasformati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteIdListUtil" class="java.util.HashMap" scope="request"/>
<jsp:useBean id="Regioni" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoCampione_batteri" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoCampione_virus" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoCampione_parassiti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoCampione_fisico" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoCampione_chimico" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoCampione_sottochimico" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoCampione_sottochimico2" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Acque" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoCampione_sottochimico3" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoCampione_sottochimico4" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoCampione_sottochimico5" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoSpecie_latte" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoSpecie_uova" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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

function listaConSenza(){
	var lista = document.getElementById("ListaCommercializzazione");
	var riga_asl = document.getElementById("tr_asl");
	var riga_datalista = document.getElementById("tr_datalista");
// 	var riga_datachiusura = document.getElementById("tr_datachiusura");
	var riga_fornitore = document.getElementById("tr_nomefornitore");
	
	if (lista.value==1){
		riga_asl.style.display='';
		riga_datalista.style.display='';
// 		riga_datachiusura.style.display='';
		riga_fornitore.style.display='';
	}
	else if (lista.value==2){
		riga_asl.style.display='';
		riga_datalista.style.display='none';
// 		riga_datachiusura.style.display='none';
		riga_fornitore.style.display='none';
	}
	else {
		riga_asl.style.display='none';
		riga_datalista.style.display='none';
// 		riga_datachiusura.style.display='none';
		riga_fornitore.style.display='none';
	}
	
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

function disabilitaMatriciCanili(){
	if(document.getElementById("checkMatriciCanili")!=null)
	document.getElementById("checkMatriciCanili").disabled=true;
	
}

function abilitaMatriciCaniliCheck(){
	if(document.getElementById("checkMatriciCanili")!=null)
	document.getElementById("checkMatriciCanili").disabled=false; 
	
}

function selectLista(){


	 sel=document.addticket.ListaCommercializzazione.value;

if(sel=="2"){
	/* sel2=document.addticket.asl_coinvolte;

	for( i = 0 ; i < sel2.length; i++){

sel2[i].checked=true;

		 document.getElementById("cu_"+sel2[i].value).style.display="";
		 document.getElementById("int_"+sel2[i].value).style.display="";
		 }*/
	
}else{

	sel2=document.addticket.asl_coinvolte;

	for( i = 0 ; i < sel2.length; i++){
		document.getElementById("cu_"+sel2[i].value).style.display="none";	
		 document.getElementById("int_"+sel2[i].value).style.display="none";
sel2[i].checked=false;

 
		 }
	mostraAllegato();
	
}

	
	
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

function popLookupSelectorAllerteImpreseElimina(siteid,size)
{	

var clonato = document.getElementById('row_'+siteid+'_'+size);
	
	clonato.parentNode.removeChild(clonato);
	
	size = document.getElementById('size_'+siteid);
	size.value=parseInt(size.value)-1;
}

function abilitaCU(siteId)	{
	
	sel2=document.addticket.asl_coinvolte;
	var len = sel2.length;

	for( i = 0 ; i < len; i++){
		if(sel2[i].value==siteId && sel2[i].checked){
			 document.getElementById("cu_"+siteId).style.display="";
			 document.getElementById("int_"+siteId).style.display="";
			 document.getElementById("orgid_"+siteId).style.display="";
			 document.getElementById('select_'+siteId).style.display="";
			 document.getElementById('selectstabilimenti_'+siteId).style.display="";
			 if(siteId =="16"){
				document.getElementById("colonnaFuoriRegione").style.display="block";
				document.getElementById("noteFuoriRegione").style.display="block";
				}
		}
		else {
			 if(sel2[i].value==siteId){
				document.getElementById("cu_"+siteId).style.display="none";
			 	document.getElementById("int_"+siteId).style.display="none";
			 	document.getElementById("orgid_"+siteId).style.display="none";
			 	document.getElementById('select_'+siteId).style.display="none";
 				document.getElementById('selectstabilimenti_'+siteId).style.display="none";
 				if(siteId =="16"){
				 	document.getElementById("colonnaFuoriRegione").style.display="none";
					document.getElementById("noteFuoriRegione").style.display="none";
				}
//  				var numelementi = document.getElementById("elementi_"+siteId);
//  				alert(numelementi.value);
//  				for(i=1; i <numelementi.value;i++)
// 					 document.getElementById("org_"+siteid+"_"+i).style.display="none"; 
			}
		}
	}
}
	
	


function mostraorigineAllerta(){

	origine=document.addticket.Origine.value;

	if(origine==1 || origine==2 || origine==3){
	if(origine==1 || origine==2){
		document.addticket.aslOrigine.style.display="";
		document.addticket.regioneOrigine.style.display="none";

	}
	if(origine==3){
		document.addticket.aslOrigine.style.display="none";
		document.addticket.regioneOrigine.style.display="";
		}
	}
	else{
		document.addticket.aslOrigine.style.display="none";
		document.addticket.regioneOrigine.style.display="none";

		}
	}



function abilitaData(value)
{

	if(value == "si")
	{
		document.getElementById("colonnaChiusura").style.display="";
	}
	else{
		document.addticket.dataChiusura.value = "";
		document.getElementById("colonnaChiusura").style.display="none";
		}

	
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
		document.forms['addticket'].TipoCampione_fisico.style.display="none";
		disabilitaTipochimico();
return;

		}
	if(tipo==2){
		//document.getElementById("nascosto1").style.display="block";
		document.forms['addticket'].TipoCampione_batteri.style.display="none";
		document.forms['addticket'].TipoCampione_virus.style.display="block";
		document.forms['addticket'].TipoCampione_parassiti.style.display="none";
		document.forms['addticket'].TipoCampione_chimico.style.display="none";
		document.forms['addticket'].TipoCampione_fisico.style.display="none";
		disabilitaTipochimico();
		return;


		}



	if(tipo==8){
		//document.getElementById("nascosto1").style.display="block";
		document.forms['addticket'].TipoCampione_batteri.style.display="none";
		document.forms['addticket'].TipoCampione_virus.style.display="none";
		document.forms['addticket'].TipoCampione_parassiti.style.display="none";
		document.forms['addticket'].TipoCampione_chimico.style.display="none";
		document.forms['addticket'].TipoCampione_fisico.style.display="block";
		
		disabilitaTipochimico();
		return;


		}

	if(tipo==4){
	//	document.getElementById("nascosto1").style.display="block";
		document.forms['addticket'].TipoCampione_batteri.style.display="none";
		document.forms['addticket'].TipoCampione_virus.style.display="none";
		document.forms['addticket'].TipoCampione_parassiti.style.display="block";
		document.forms['addticket'].TipoCampione_chimico.style.display="none";
		document.forms['addticket'].TipoCampione_fisico.style.display="none";
		disabilitaTipochimico();
return;

		}

	if(tipo==5){
	//	document.getElementById("nascosto1").style.display="block";
		document.forms['addticket'].TipoCampione_batteri.style.display="none";
		document.forms['addticket'].TipoCampione_virus.style.display="none";
		document.forms['addticket'].TipoCampione_parassiti.style.display="none";
		document.forms['addticket'].TipoCampione_chimico.style.display="block";
		document.forms['addticket'].TipoCampione_fisico.style.display="none";
return;

		}

	//document.getElementById("nascosto1").style.display="none";
	disabilitaTipochimico();
		document.forms['addticket'].TipoCampione_batteri.style.display="none";
		document.forms['addticket'].TipoCampione_virus.style.display="none";
		document.forms['addticket'].TipoCampione_parassiti.style.display="none";
		document.forms['addticket'].TipoCampione_chimico.style.display="none";
		document.forms['addticket'].TipoCampione_fisico.style.display="none";
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




 
  function updateCategoryList() {
    var orgId = document.forms['addticket'].orgId.value;
    var url = 'TroubleTickets.do?command=CategoryJSList&form=addticket&reset=true&orgId='+orgId;
    window.frames['server_commands'].location.href=url;
  }
<dhv:include name="ticket.catCode" none="false">
  function updateSubList1() {
    var orgId = document.forms['addticket'].orgId.value;
    if(orgId != '-1'){
      var sel = document.forms['addticket'].elements['catCode'];
      var value = sel.options[sel.selectedIndex].value;
      var url = "TroubleTickets.do?command=CategoryJSList&form=addticket&catCode=" + escape(value)+'&orgId='+orgId;
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
    var url = "TroubleTickets.do?command=CategoryJSList&form=addticket&subCat1=" + escape(value)+'&orgId='+orgId;
    window.frames['server_commands'].location.href=url;
  }
</dhv:include>
<dhv:include name="ticket.subCat2" none="true">
  function updateSubList3() {
    var orgId = document.forms['addticket'].orgId.value;
    var sel = document.forms['addticket'].elements['subCat2'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTickets.do?command=CategoryJSList&form=addticket&subCat2=" + escape(value)+'&orgId='+orgId;
    window.frames['server_commands'].location.href=url;
  }
</dhv:include>
<dhv:include name="ticket.subCat3" none="true">
  function updateSubList4() {
    var orgId = document.forms['addticket'].orgId.value;
    var sel = document.forms['addticket'].elements['subCat3'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTickets.do?command=CategoryJSList&form=addticket&subCat3=" + escape(value)+'&orgId='+orgId;
    window.frames['server_commands'].location.href=url;
  }
</dhv:include>
  function updateUserList() {
    var sel = document.forms['addticket'].elements['departmentCode'];
    var value = sel.options[sel.selectedIndex].value;
    var orgSite = document.forms['addticket'].elements['orgSiteId'].value;
    var url = "TroubleTickets.do?command=DepartmentJSList&form=addticket&dept=Assigned&orgSiteId="+ orgSite +"&populateResourceAssigned=true&resourceAssignedDepartmentCode=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
  function updateResolvedByUserList() {
    var sel = document.forms['addticket'].elements['resolvedByDeptCode'];
    var value = sel.options[sel.selectedIndex].value;
    var orgSite = document.forms['addticket'].elements['orgSiteId'].value;
    var url = "TroubleTickets.do?command=DepartmentJSList&form=addticket&dept=Resolved&orgSiteId="+ orgSite + "&populateResolvedBy=true&resolvedByDepartmentCode=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
  
  function updateAllUserLists() {
    var sel = document.forms['addticket'].elements['departmentCode'];
    var value = sel.options[sel.selectedIndex].value;
    var orgSite = document.forms['addticket'].elements['orgSiteId'].value;
    var sel2 = document.forms['addticket'].elements['resolvedByDeptCode'];
    var value2 = sel2.options[sel2.selectedIndex].value;
    var url = "TroubleTickets.do?command=DepartmentJSList&form=addticket&orgSiteId="+ orgSite +"&populateResourceAssigned=true&populateResolvedBy=true&resourceAssignedDepartmentCode=" + escape(value)+'&resolveByDepartmentCode='+ escape(value2);
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

    var url = "TroubleTickets.do?command=OrganizationJSList" + params; 
    window.frames['server_commands'].location.href=url;
  </dhv:include>
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

	
	
  
  function checkForm(form){
  	  
    formTest = true;
    message = "";
    
    
    if(form.ListaCommercializzazione.value == "-1")
	{
		message += label("check.sanzioni.richiedente.selezionato","- Controllare che il campo \"Lista di distribuzione\" sia stato popolato\r\n");
		formTest = false;
		
	} else {
    	
		
		
		
		var aslVuote = 0;
		var cuVuoti = 0;
		
		for (var i=201; i<=207;i++){
			var asl =document.getElementById("asl_"+i);
			var cu = document.getElementById("cucampo_"+i);
			
			if (asl.checked){
				aslVuote = 1;
				if (cu.value == '')
					cuVuoti=1;
			}
		}
		
		if (aslVuote==0){
		message += label("check.sanzioni.richiedente.selezionato","- Selezionare almeno un'ASL coinvolta.\r\n");
		formTest = false;
		}
		if (cuVuoti==1){
			message += label("check.sanzioni.richiedente.selezionato","- Controllar di aver inserito il numero di CU per ogni asl coinvolta.\r\n");
			formTest = false;
			}
		
		
		
		
		
		
		
		
    }
    
    if(form.ListaCommercializzazione.value == "1")
	{
    	 if(form.data_lista.value == ""){
    		message += label("check.sanzioni.richiedente.selezionato","- Controllare che il campo \"Data lista\" sia stato popolato\r\n");
    		formTest = false;
    	 }
 		if(form.nome_fornitore.value == ""){
 			message += label("check.sanzioni.richiedente.selezionato","- Controllare che il campo \"Nome fornitore\" sia stato popolato\r\n");
 			formTest = false;
    	 }
//  		if(form.data_chiusura.value == ""){
//  			message += label("check.sanzioni.richiedente.selezionato","- Controllare che il campo \"Data chiusura\" sia stato popolato\r\n");
//  			formTest = false;
//  		}
	}
    
    
    
    <dhv:include name="ticket.contact" none="true">
    if ( form.asl_coinvolte.selectedIndex == -1 ) {
      message += label("check..richiedente.selezionato","- Controllare che le \"A.S.L. Coinvolte\" siano state selezionate\r\n");
      formTest = false;
    }
    </dhv:include>

	for (i=201; i<=207;i++)
	{
		if (controlloNumber(document.getElementById("cucampo_"+i))==false)
		{
			formTest = false ; 
			message += label("TESTO1","- Il \"Numero CU\" deve essere un valore numerico.\r\n");
			break;
		}
	}

// 	if(form.size1.value == "0")
// 	{
// 		message += label("check.sanzioni.richiedente.selezionato","- Controllare che il campo \"Matrice\" sia stato popolato\r\n");
// 		formTest = false;
		
// 	}

    
	/*alimentiNonAnimali=document.getElementById("alimentinonAnimali");

	animalinonalimentari=document.getElementById("animalinonalimentari");

	alimentiOrigineAnimale= document.getElementById("alimentiOrigineAnimale");
	mangimi= document.getElementById("mangimi");
	alimentiOrigineVegetale= document.getElementById("alimentiOrigineVegetale");
	alimentiComposti= document.getElementById("alimentiComposti");
	acqua= document.getElementById("acqua");

	bevande= document.getElementById("bevande");
	mangimi= document.getElementById("mangimi");
	additivi= document.getElementById("additivi");
	materialialimenti= document.getElementById("materialialimenti");

	if(animalinonalimentari.checked==false && alimentiOrigineAnimale.checked==false && alimentiOrigineVegetale.checked==false && alimentiComposti.checked==false && acqua.checked==false && bevande.checked==false && additivi.checked==false && mangimi.checked==false && materialialimenti.checked==false && alimentiNonAnimali.checked==false ){
		message +=label("TESTO1","- Controllare di aver selezionato un tipo di alimento \n"); formTest = false;
	}else{

		if(alimentiOrigineVegetale.checked==true){

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

		else if(alimentiNonAnimali.checked==true){

			if (form.altrialimenti.value == "-1"){ // nessun valore scelto per Alimenti Non Animali
				message +=label("TESTO1","- Controllare che il campo \"Altri Alimenti di origine non animale\" sia stato selezionato \n"); formTest = false;
			}

		}

		else{

			if(alimentiComposti.checked==true){

				if(form.testoAlimentoComposto.value==""){
					message +=label("TESTO1","- Controllare che il campo di testo per \"Alimenti Composti\" sia stato popolato \n"); formTest = false;	
				}



			}else{

				if(acqua.checked==true){

					if(form.acque.value == "-1"){
						message +=label("TESTO1","- Controllare che il campo \"Acqua\" sia stato popolato \n"); formTest = false;
					}
					//message +=label("TESTO1","- Controllare che il campo \"Acqua\" sia stato popolato \n"); formTest = false;


				}else if(mangimi.checked==true){
					if(form.lookupSpecieAlimento.value == "-1" || form.lookupTipologiaAlimento.value == "-1"){
						message +=label("TESTO1","- Controllare che il campo \"Alimenti per uso Zootecnico\" sia stato popolato \n"); formTest = false;
					}
				}

				else{

					if(additivi.checked==true){


//						message +=label("TESTO1","- Controllare che il campo di testo per \"Additivi\" sia stato popolato \n"); formTest = false;


					}else{

						if(materialialimenti.checked==true){

							//								message +=label("TESTO1","- Controllare che il campo di testo per \"Materiali a Contatto con Alimenti\" sia stato popolato \n"); formTest = false;
						}
					}
				}
			}
		}
	}*/

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
	
	if (form.oggettoAllerta.value == "") {
        message += label("check.campioni.richiedente.selezionatosssd","- Controllare che \"Descrizione Breve\" sia stato popolato\r\n");
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

// 		if(form.size.value == "0")
// 		{
// 			message += label("check.sanzioni.richiedente.selezionato","- Controllare che il campo \"Azione non conforme per\" sia stato popolato\r\n");
// 			formTest = false;
		
// 		}
	
		/*if (form.TipoCampione != null && form.TipoCampione.value == "-1") {
	        message += label("check.campioni.richiedente.selezionatosssd","- Controllare che \"Azione non conforme per\" sia stato selezionato\r\n");
	        formTest = false;
	      }
*/
		if (form.Origine.value == "1" || form.Origine.value == "2") {

			if(form.aslOrigine.value == ""-1)
			{
			 	message += label("check.campioni.richiedente.selezionatosssd","- Controllare che di aver selezionato tutte le voci per il campo\"Origine\"\r\n");
		        formTest = false;
			}
		}
		else
		{
			if (form.Origine.value == "3") {

				if(form.regioneOrigine.value == ""-1)
				{
				 	message += label("check.campioni.richiedente.selezionatosssd","- Controllare che di aver selezionato tutte le voci per il campo\"Origine\"\r\n");
			        formTest = false;
				}

		}
		}
		
		    
		

		 if (form.tipo_allerta.value == "-1") {
		        message += label("check..data_richiesta.selezionato","- Controllare che il campo \"Tipo Allerta\" sia stato Selezionato\r\n");
		        formTest = false;
		      }
		

	/*if (form.oggettoAllerta.value != "" && form.oggettoAllerta.value.length>20) {
        message += label("check.campioni.richiedente.selezionatosssd","- Controllare che \"Descrizione Breve\" non superi i 20 caratteri\r\n");
        formTest = false;
      }*/
    
//       if (form.tipo_notifica_allerta.value == "-1") {
//           message += label("check..data_richiesta.selezionato","- Controllare che il campo \"Tipo notifica\" sia stato popolato\r\n");
//           formTest = false;
//         }
      if (form.numero_notifica_allerta.value == "") {
          message += label("check..data_richiesta.selezionato","- Controllare che il campo \"Numero notifica\" sia stato popolato\r\n");
          formTest = false;
        }
    if (form.dataApertura.value == "") {
        message += label("check..data_richiesta.selezionato","- Controllare che il campo \"Data Apertura\" sia stato popolato\r\n");
        formTest = false;
      }
   <%-- <dhv:include name="ticket.contact" none="true">
    if (form.TipoAlimento.value == "-1") {
      message += label("check.campioni.richiedente.selezionatosssd","- Controllare che \"Tipo alimento\" sia stato popolato\r\n");
      formTest = false;
    }
    </dhv:include>--%>
    
    <dhv:include name="ticket.contact" none="true">
    if (form.Origine.value == "-1") {
      message += label("check.campioni.richiedente.selezionatosss","- Controllare che \"Origine\" sia stato popolato\r\n");
      formTest = false;
    }
    </dhv:include>
    
    aslcoinv=document.addticket.asl_coinvolte;
	 flag1 = 0;
	for( i = 0 ; i < aslcoinv.length; i++){
		if(aslcoinv[i].checked == true)
		{
			flag1 = 1;	
			cu = document.getElementById("cucampo_"+aslcoinv[i].value).value;
			sizeOrg = document.getElementById("size_"+aslcoinv[i].value).value;
		
		if(cu < sizeOrg)
		{
			message += label("check..richiedente.selezionato","- Controllare il numero di controlli inseriti sia proporzionato alle imprese selezionate per le asl coinvolte nell'allerta \r\n");
		     formTest = false;
			break;
		}
		 }
	}



    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    } else {
      loadModalWindow(); 
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
    <dhv:permission name="accounts-accounts-contacts-add">
      var acctPermission = true;
    </dhv:permission>
    <dhv:permission name="accounts-accounts-contacts-add" none="true">
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
  
   function selectCarattere(str, n, m){
  
 		elm1 = document.getElementById("dat"+n);
 		elm2 = document.getElementById("dat"+m);
 		
 		
 		if(str == "Provvedimenti"){
 			car = document.addticket.Provvedimenti.value;
 		}
 		if(str == "Amministrative"){
 			car = document.addticket.Amministrative.value;
 		}
 		if(str == "Penali"){
 			car = document.addticket.Penali.value;
 		}
 		
 		if(car == 9 || (car == 6 && str == "Penali")){
 			elm1.style.visibility = "visible";
 			elm2.style.visibility = "visible";
 			
 		}
 		else {
 			elm1.style.visibility = "hidden";
 			elm2.style.visibility = "hidden";
 			
 		}
 	  }

 

function mostraAllegato(){

  lista=document.addticket.ListaCommercializzazione.value;
  

  if(lista==1){
document.getElementById("oggetto").style.display="";



sel2=document.addticket.asl_coinvolte;

for( i = 0 ; i < sel2.length; i++){

sel2[i].checked=false;
document.getElementById("cu_"+sel2[i].value).style.display="none";
document.getElementById("int_"+sel2[i].value).style.display="none";
}
	  }else{
		  document.getElementById("oggetto").style.display="none";
	  
		  }
}




</script>


	<%=showError(request, "numero_notifica_allertaError") %>

<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Aggiungi Allerta</dhv:label></strong>
    </th>
	</tr>
	
	
	<tr>
      <td name="altrIrreg"  nowrap class="formLabel">
        <dhv:label name="">Lista di Distribuzione</dhv:label>
      </td>
    <td>
      <%	ListaCommercializzazione.setJsEvent("onChange=\"javascript:listaConSenza();\"");
        %> 
         <%= ListaCommercializzazione.getHtmlSelect("ListaCommercializzazione",TicketDetails.getListaCommercializzazione()) %> <font color="red">*</font>
	</td>
 </tr>
  
  
  
 <tr id="tr_datalista" style="display:none">
  <td nowrap class="formLabel">Data lista</td>
  <td>
  
<input class="editField" type="text" readonly id="data_lista" name="data_lista" size="10" value=""/>
<a href="#" onClick="cal19.select(document.forms[0].data_lista,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a> <font color="red">*</font>

  
  </td></tr>
 
  <tr id="tr_nomefornitore" style="display:none">
  <td nowrap class="formLabel">Nome fornitore</td>
 <td>
 <input type="text" id="nome_fornitore" name="nome_fornitore" value=""/> <font color="red">*</font>
 </td></tr>
  
  <dhv:include name="stabilimenti-sites" none="true">
  <dhv:evaluate if="<%= SiteIdList.size() > 1 %>">
    <tr id="tr_asl" style="display:none">
      <td nowrap class="formLabel">
        A.S.L. Coinvolte
      </td>
      <td>
        
        <table class="noborder">
        <%Iterator<Integer> it=SiteIdListUtil.keySet().iterator();
        
        while(it.hasNext()){
        	int code=it.next();
        String descr=(String)SiteIdListUtil.get(code);
        	
        %>
        <tr><td><%=descr %>&nbsp;</td><td>
        <input type="checkbox" name="asl_coinvolte" id = "asl_<%=code %>" onclick="abilitaCU(<%=code %>)" value="<%=code %>"></td><td id="int_<%=code %>" style="display:none">&nbsp;Numero C.U.&nbsp;</td><td id="cu_<%=code %>" style="display:none"><input type="text" name="cu_<%=code %>" id = "cucampo_<%=code %>"></td><% if(code == 16) { %> <td id="colonnaFuoriRegione" style="display:none"  > &nbsp; Descrizione <% if(code == 16) { %> <textarea rows="8" cols="50" id = "noteFuoriRegione" style="display: none" name="noteFuoriRegione"> </textarea> <%} %></td><%} %>
        
        <td id="orgid_<%=code %>">
        <input type = "hidden" id = "elementi_<%=code %>" name = "elementi_<%=code %>" value = "0">
   		<input type = "hidden" id = "size_<%=code %>" name = "size_<%=code %>" value = "0">
   
        <table id="tab_<%=code %>">
        <tr id="row_<%=code %>" style = "display : none"><td><input type = "text" readonly="readonly" name = "ragione_sociale<%=code %>"></td> <td><input type = "text" readonly="readonly" name = "indirizzo_<%=code %>"></td><td>[<a href="javascript:popLookupSelectorAllerteImpreseElimina(document.getElementById('asl_<%=code %>').value,document.getElementById('size_<%=code %>').value)" id="elimina_<%=code %>">[elimina]</a></td></tr>
        </table>
        
        </td>
        
        <td id = "select_<%=code %>" style="display: none">
          &nbsp;[<a href="javascript:popLookupSelectorAllerteImprese('codiceFiscaleCorrentista','alertText','organization','',<%=code %>);"><dhv:label name="">Aggiungi Impresa</dhv:label></a>]
        </td>
        
        <td id = "selectstabilimenti_<%=code %>" style="display: none">
          &nbsp;[<a href="javascript:popLookupSelectorAllerteStabilimenti('codiceFiscaleCorrentista','alertText','organization','',<%=code %>);"><dhv:label name="">Aggiungi Stabilimento</dhv:label></a>]
        
        </td>
        
        </tr>
        <%} %>
        
        
        </table>
     
      </td>
    </tr>
  </dhv:evaluate> 
  <dhv:evaluate if="<%= SiteIdList.size() <= 1 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
  </dhv:evaluate>
 </dhv:include>
 
  
  
  
  
<!--    <tr id="tr_datachiusura" style="display:none"> -->
<!--   <td nowrap class="formLabel">Data chiusura</td> -->
<!--  <td> -->
<!--  <input class="editField" type="text" readonly id="data_chiusura" name="data_chiusura" size="10" value=""/> -->
<!-- <a href="#" onClick="cal19.select(document.forms[0].data_chiusura,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19"> -->
<!-- <img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a> <font color="red">*</font> -->
<!--  </td></tr> -->
 
	
 
	<% if ( false ){//!"true".equals(request.getParameter("contactSet"))) { %>
  <tr>  
    <td class="formLabel">
      <dhv:label name=".richiedente">Ente</dhv:label>
    </td>
    <td>
      <table cellspacing="0" cellpadding="0" border="0" class="empty">
        <tr>
          <td>
            <div id="changeaccount">
              <% if(TicketDetails.getOrgId() != -1) {%>
                <%= toHtml(TicketDetails.getCompanyName()) %>
              <%} else {%>
                <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
              <%}%>
            </div>
          </td>
          <td>
            <input type="hidden" name="orgId" id="orgId" value="<%=  TicketDetails.getOrgId() %>" />
            <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  TicketDetails.getId() > 0 || TicketDetails.getOrgId() > -1 ? TicketDetails.getOrgSiteId() : User.getSiteId()%>" />
            &nbsp;<font color="red">*</font>
            <%= showAttribute(request, "orgIdError") %>
            [<a href="javascript:popAccountsListSingle('orgId','changeaccount', 'showMyCompany=true&filters=all|my|disabled');"><dhv:label name="accountsa.accounts_add.select">Seleziona O.S.A.</dhv:label></a>]
            [<a href="javascript:popAccountsListSingleNew('orgId','changeaccount', 'showMyCompany=true&filters=all|my|disabled');"><dhv:label name="accountsa.accounts_add.select">Seleziona Stabilimento</dhv:label></a>]
            [<a href="javascript:popAccountsListSingleNewAllev('orgId','changeaccount', 'showMyCompany=true&filters=all|my|disabled');"><dhv:label name="accountsa.accounts_add.select">Seleziona Allevamento</dhv:label></a>]
          </td>
        </tr>
      </table>
    </td>
  </tr> 

  <% }else{ %>
    <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  TicketDetails.getId() > 0 ? TicketDetails.getOrgSiteId() : User.getSiteId()%>" />
    <input type="hidden" name="orgId" value="<%= toHtmlValue(request.getParameter("orgId")) %>">
    <input type="hidden" name="contactId" value="<%= request.getParameter("contactId") %>">
  <% } %>

<tr>
    <td valign="top" class="formLabel">
      <dhv:label name="">Tipo di Allerta</dhv:label>
    </td>
    <td>
    <select name = "tipo_allerta">
    <option  value = "-1">--SELEZIONARE UNA VOCE--</option>
     <option  value = "Entrata">In Entrata</option>
     <option  value = "Uscita">In Uscita</option>
    </select>
        <font color="red">*</font> 
    </td> 
    </tr>
    
      <tr>
      <td valign="top" class="formLabel">Allerta Comunitaria</td>
      <td>  
       <input type = "checkbox" name = "flag_tipo_allerta" id="flag_tipo_allerta" />
      </td>      
      </tr>
    
    
    <dhv:permission name="allerte-allerte-sian-view">
    
    <input type = "hidden" name = "inserita_da_sian" value = "true">
    </dhv:permission>
     <dhv:permission name="allerte-allerte-veterinari-view">
     <input type = "hidden" name = "inserita_da_sian" value = "false">
    </dhv:permission>
   

<!--<tr>-->
<!--    <td valign="top" class="formLabel">-->
<!--      <dhv:label name="">Identificativo Allerta</dhv:label>-->
<!--    </td>-->
<!--    <td>-->
<!--         <input type="text" name="idAllerta"  id="idAllerta" size="10" maxlength="50" /><font color="red">*</font> <%= showAttribute(request, "assignedDateError") %>-->
<!--    </td> -->
<!--    </tr>-->

<tr>
    <td valign="top" class="formLabel">
      <dhv:label name="">Unità di Misura per Quantita</dhv:label>
    </td>
    <td>
         <%=UnitaMisura.getHtmlSelect("unitaMisura",TicketDetails.getUnitaMisura()) %>
    </td> 
    </tr>
   <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name=".data_richiestas">Data apertura</dhv:label>
    </td>
    <td>
    
        <input readonly type="text" id="dataApertura" name="dataApertura" size="10"/>
		<a href="#" onClick="cal19.select(document.forms[0].dataApertura,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
 
    
      <font color="red">*</font> 

    </td>
  </tr>
  
  
 <tr>
      <td name="orig"  nowrap class="formLabel">
        <dhv:label name="">Notifica</dhv:label>
      </td>
         <td>  
    
      <%= tipoNotifica.getHtmlSelect("tipo_notifica_allerta", -1) %>
      <b>Num. notifica </b><input type="text" id="numero_notifica_allerta" name="numero_notifica_allerta"/>   <font color="red">*</font>
	</td>
 </tr>
 
  
  <tr class="containerBody" style = "display:none">
    <td nowrap class="formLabel">
      <dhv:label name=".data_richiestas">Gestione Pregresso</dhv:label>
    </td>
    <td>
    <table class = "noborder">
    <tr>
    <td>NO <input type ="radio" name = "pregresso" value ="no" checked="checked" onclick="abilitaData('no')">  Si <input type ="radio" name = "pregresso" value ="si" onclick="abilitaData('si')"></td>
    
    <td style="display: none" id = "colonnaChiusura">&nbsp; Data Chiusura &nbsp; 
    <input readonly type="text" id="dataChiusura" name="dataChiusura" size="10"/>
		<a href="#" onClick="cal19.select(document.forms[0].dataChiusura,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
		
    </td>
    
    </tr>
    
    </table>
    
      
     
    </td>
  </tr>
   
   

 <tr>
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
      <input type = "checkbox" name = "flag_pubblicazione_allerte" onclick="abilitapubblicazione(this)"/>
      </td>
          
      </tr>
      <tr>
      <td>  
     <table id = "pubblicazione" style="display: none">
     <tr>
     <td>Data Inizio pubblicazione</td>
       <td>
       <%
       String data_attuale = "" ;
       SimpleDateFormat sdf_d = new SimpleDateFormat("dd/MM/yyyy");
       data_attuale = sdf_d.format(new Date(System.currentTimeMillis()));
       %>
       
         <input readonly type="text" id="data_inizio_pubblicazione_allerte" name="data_inizio_pubblicazione_allerte" size="10"/>
		<a href="#" onClick="cal19.select(document.forms[0].data_inizio_pubblicazione_allerte,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
         
      <font color="red">*</font>
       </td>
     </tr>
     
     <tr>
     <td>Data Fine pubblicazione</td>
       <td>
       
       <input readonly type="text" id="data_fine_pubblicazione_allerte" name="data_fine_pubblicazione_allerte" size="10"/>
		<a href="#" onClick="cal19.select(document.forms[0].data_fine_pubblicazione_allerte,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
       
         
      <font color="red">*</font>
       </td>
     </tr>
    
      <tr>
     <td>Tipo di Rischio</td>
       <td>
       <textarea rows="5" cols="30" name = "tipo_rischio_allerte"></textarea>
       <font color="red">*</font>
       </td>
     </tr>
     
      <tr>
     <td>Provvedimenti/Esito Accertamenti</td>
       <td>
       <textarea rows="5" cols="30" name = "provvedimenti_esito_allerte"></textarea>
       <font color="red">*</font>
       </td>
     </tr>
     
     </table>
      </td>
          
      </tr>
      
      
      </table>
      
    
       
	</td>
 </tr>
 
 <%@ include file="/allerte_new/matrici_tree.jsp" %>
 	 
<%@ include file="/allerte_new/analiti_tree.jsp" %>
<%-- 
<tr>
      <td name="nonconf"  nowrap class="formLabel">
        <dhv:label name="">Azione non conforme Per</dhv:label>
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
 TipoCampione_fisico.setSelectStyle("display:none");
 TipoCampione_chimico.setSelectStyle("display:none");

 
 %>
 
 
 <%= TipoCampione_batteri.getHtmlSelect("TipoCampione_batteri",TicketDetails.getTipoCampione()) %> <%= showAttribute(request, "assignedDateError") %>
 <%= TipoCampione_virus.getHtmlSelect("TipoCampione_virus",TicketDetails.getTipoCampione()) %> <%= showAttribute(request, "assignedDateError") %>
   <%= TipoCampione_parassiti.getHtmlSelect("TipoCampione_parassiti",TicketDetails.getTipoCampione()) %><%= showAttribute(request, "assignedDateError") %>
   <%= TipoCampione_chimico.getHtmlSelect("TipoCampione_chimico",TicketDetails.getTipoCampione()) %> <%= showAttribute(request, "assignedDateError") %>
   <%= TipoCampione_fisico.getHtmlSelect("TipoCampione_fisico",TicketDetails.getTipoCampione()) %> <%= showAttribute(request, "assignedDateError") %>
    
    
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
 
 </table>
 

 
 </td>

 
 
  </tr>

 --%>

  <tr>
    <td valign="top" class="formLabel">
      <dhv:label name=".note">Note</dhv:label>
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
<br /><br/>

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
    <textarea rows="5" cols="40" name="oggettoAllerta"></textarea><font color = "red">*</font>
    </td>
    
    </tr>
	
<%-- <%@ include file="../tipiAlimenti.jsp" %> --%>	


<tr class="containerBody">
    <td valign="top" class="formLabel">
      Denominazione Prodotto
    </td>
    <td>    
    <input type = "text" name="denominazione_prodotto"><font color = "red">*</font>
    </td>
    
    </tr>
    <tr class="containerBody">
    <td valign="top" class="formLabel">
     Numero del Lotto
    </td>
    <td>    
    <input type = "text" name="numero_lotto"><font color = "red">*</font>
    </td>
    
    </tr>
    <tr class="containerBody">
    <td valign="top" class="formLabel">
      Fabbricante o Produttore
    </td>
    <td>    
    <input type = "text" name="fabbricante_produttore"><font color = "red">*</font>
    </td>
    
    </tr>
    <tr class="containerBody">
    <td valign="top" class="formLabel">
      Data Scadenza/termine minimo di conservazione
    </td>
    <td>
    
    
      <input readonly type="text" id="data_scadenza_allerta" name="data_scadenza_allerta" size="10"/>
		<a href="#" onClick="cal19.select(document.forms[0].data_scadenza_allerta,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
 
      <!-- font color="red">*</font -->    
         
    </td>
    
    </tr>

</table>


<a name="categories"></a>
<input type="hidden" name="close" value="">
<input type="hidden" name="refresh" value="-1">
<input type="hidden" name="modified" value="<%=  TicketDetails.getModified() %>" />
<input type="hidden" name="currentDate" value="<%=  request.getAttribute("currentDate") %>" />
<input type="hidden" name="statusId" value="<%=  TicketDetails.getStatusId() %>" />
<input type="hidden" name="trashedDate" value="<%=  TicketDetails.getTrashedDate() %>" />
<%= addHiddenParams(request, "popup|popupType|actionId") %>


<br>
<input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save" onClick="return checkForm(this.form)">
<input type="submit" value="Annulla" onClick="javascript:this.form.action='TroubleTicketsAllerteNew.do?command=Home'">
</form>
</body>