<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ include file="../initPage.jsp" %>
<%@ page import="java.util.*,org.aspcfs.modules.vigilanza.base.*" %>
<%@ page import="java.text.DateFormat, org.aspcfs.modules.actionplans.base.*"%>
<%@page import="org.aspcfs.utils.web.LookupList"%>

<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.canili.base.Organization" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="TipoIspezione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<script type="text/javascript" src="dwr/interface/ControlliUfficiali.js?n=2"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<%@ page import="java.text.DateFormat, org.aspcfs.modules.actionplans.base.*"%>


<jsp:useBean id="IspezioneMacrocategorie" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="EsitoCampione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipoCampione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipoAudit" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="AuditTipo" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Bpi" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Haccp" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Ispezione" class="java.util.HashMap" scope="request" />
<jsp:useBean id="PianoMonitoraggio1" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="PianoMonitoraggio2" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="PianoMonitoraggio3" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="resolvedByDeptList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleo" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleoDue" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleoTre" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleoQuattro" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleoCinque" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleoSei" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleoSette" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleoOtto" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleoNove" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleoDieci" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request" />
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="SanzioniAmministrative" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="SeverityList" class="org.aspcfs.utils.web.LookupList" 	scope="request" />
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ticketStateList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Recipient" class="org.aspcfs.modules.contacts.base.Contact" scope="request" />
<jsp:useBean id="DestinatarioCampione" 	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="causeList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="fileItem" class="com.zeroio.iteam.base.FileItem" scope="request" />
<jsp:useBean id="EsitoControllo" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="DistribuzionePartita" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="DestinazioneDistribuzione" 	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ArticoliAzioni" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="AzioniAdottate" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="resolutionList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="EscalationList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="OrgList" class="org.aspcfs.modules.canili.base.OrganizationList" scope="request" />
<jsp:useBean id="SubList1" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request" />
<jsp:useBean id="SubList2" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request" />
<jsp:useBean id="SubList3" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request" />
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request" />
<jsp:useBean id="resolvedUserList" class="org.aspcfs.modules.admin.base.UserList" scope="request" />
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request" />
<jsp:useBean id="actionPlans" class="org.aspcfs.modules.actionplans.base.ActionPlanList" scope="request" />
<jsp:useBean id="insertActionPlan" class="java.lang.String" scope="request" />
<jsp:useBean id="defectSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request" />
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request" />
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request" />
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popServiceContracts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAssets.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProducts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></SCRIPT>
<script language="JavaScript" type="text/javascript" src="javascript/confrontaDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/controlliUfficiali.js?n=2"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/controlli_ufficiali_imprese.js"></script>


<script>
//var r = confirm("ATTENZIONE! Per aggiungere un controllo ufficiale è necessario importare prima lo stabilimento nella NUOVA ANAGRAFICA.\nProseguire con la procedura di importazione?");		
alert("ATTENZIONE! Prima di aggiungere un nuovo controllo, è consigliabile importare prima lo stabilimento nella NUOVA ANAGRAFICA tramite il servizio di HELPDESK.");
/*var r = confirm("ATTENZIONE! E' consigliabile importare prima lo stabilimento nella NUOVA ANAGRAFICA.\n\n -Cliccare OK per proseguire con la procedura di importazione\n -Cliccare ANNLLA per aggiungere il controllo");		
if (r == true) { 
	window.location.href='OpuStab.do?command=CaricaImport&orgId=<%= OrgDetails.getOrgId() %>';
} else {
	//window.location.href='Allevamenti.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';
	;
}  */		
</script>

<body onload = "provaFunzione('addticket'); onloadAllerta('addticket'); resetElementiNucleoIspettivo('<%=TicketDetails.getNucleoasList().size() %>');
costruisci_rel_ateco_attivita('<%= OrgDetails.getOrgId() %>', 'id_linea_sottoposta_a_controllo');">



 <%

	TipoIspezione.setJsEvent("onChange=javascript:mostraMenuTipoIspezione('addticket');gestioneVisibilitaCodiceAteco('addticket')");
	TitoloNucleoDue.setJsEvent("onChange=mostraCampo2('addticket')");
	PianoMonitoraggio1.setJsEvent("onChange=javascript:piani('addticket')");
    PianoMonitoraggio2.setJsEvent("onChange=javascript:piani('addticket')");
    PianoMonitoraggio3.setJsEvent("onChange=javascript:piani('addticket')");
	AuditTipo.setJsEvent("onChange=javascript:mostraMenu4('addticket')");
    TipoAudit.setJsEvent("onChange=javascript:mostraMenu2('addticket')");
    TipoCampione.setJsEvent("onChange=javascript:reloadAddCU('CaniliVigilanza.do?command=Add&orgId="+OrgDetails.getOrgId()+"&tipoCampione='+this.value)"); 


%>

<% if (request.getAttribute("Error") != null) { %>
  <%="Attenzione. Si è verificato un errore: " + showError(request, "Error") %>
<%}%>

<form method="post" name="addticket" action="CaniliVigilanza.do?command=Insert&auto-populate=true"  >
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="Canili.do">Canili</a> > 
  <a href="Canili.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
  <a href="Canili.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Scheda Canile</a> >
  <a href="Canili.do?command=ViewVigilanza&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="vigilanza">Tickets</dhv:label></a> >
  <dhv:label name="campioni.aggiungi">Aggiungi Controllo Ufficiale</dhv:label>
</td>
</tr>
</table>


<script type="text/javascript">


function opensearchCaneBdr(){
	var res;
	var result;


		window.open('CaniPadronali.do?command=Search&cani_canile=true&id_canina=<%=OrgDetails.getOrgIdC()%>',null,
		'height=600px,width=900px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');
		
		
	
		}
		
function addCane(){
	  
  	var elementi;
  	var elementoClone;
  	var tableClonata;
  	var tabella;
  	var selezionato;
  	var x;
  	elementi = document.getElementById('size_p');
  	//elementi.value=parseInt(elementi.value)+1;
  	size = document.getElementById('size_p');
  	size.value=parseInt(size.value)+1;
  	
  	var indice = parseInt(elementi.value) - 1;

  	
  	var clonanbsp = document.getElementById('cane_1');
  	/*clona riga vuota*/
  	clone=clonanbsp.cloneNode(true);

	clone.getElementsByTagName('TD')[0].innerHTML 	= '<b>'+size.value+'</b>' ;
	 	
	clone.getElementsByTagName('INPUT')[0].name 	= "mc_"+elementi.value;
  	clone.getElementsByTagName('INPUT')[0].id 		= "mc_"+elementi.value;
	clone.getElementsByTagName('INPUT')[0].value 	= "" ;
	
	var ii = elementi.value;
	clone.getElementsByTagName('INPUT')[1].id="check_"+ii;
	clone.getElementsByTagName('INPUT')[1].onclick= function(){ var j = ii;  removeRequired(j,document.getElementById("check_"+j));};
	
	
	
  	
  	clone.getElementsByTagName('INPUT')[2].name = "razza_"+elementi.value;
  	clone.getElementsByTagName('INPUT')[2].id = "razza_"+elementi.value;
	clone.getElementsByTagName('INPUT')[2].value 	= "" ;

  	
  	clone.getElementsByTagName('INPUT')[3].name = "taglia_"+elementi.value;
  	clone.getElementsByTagName('INPUT')[3].id = "taglia_"+elementi.value;
	clone.getElementsByTagName('INPUT')[3].value 	= "" ;

	
  	clone.getElementsByTagName('INPUT')[4].name = "mantello_"+elementi.value;
  	clone.getElementsByTagName('INPUT')[4].id = "mantello_"+elementi.value;
	clone.getElementsByTagName('INPUT')[4].value 	= "" ;

	
  	clone.getElementsByTagName('INPUT')[5].name = "sesso_"+elementi.value;
  	clone.getElementsByTagName('INPUT')[5].id = "sesso_"+elementi.value;
	clone.getElementsByTagName('INPUT')[5].value 	= "" ;

  	clone.getElementsByTagName('INPUT')[6].name = "data_nascita_cane_"+elementi.value;
  	clone.getElementsByTagName('INPUT')[6].id = "data_nascita_cane_"+elementi.value;
	clone.getElementsByTagName('INPUT')[6].value = 	"" ;
	
	
	
	

  	clone.getElementsByTagName('A')[0].href = "javascript:popCalendar('addticket','data_nascita_cane_"+elementi.value+"','it','IT','Europe/Berlin')";
	
	//clone.getElementsByTagName('A')[1].href = "javascript:rimuoviCane("+elementi.value+")";
	
  	/*Aggancio il nodo*/
  	clonanbsp.parentNode.appendChild(clone);
	clone.id = "cane_" + size.value;

  }


function rimuoviCane(index){

		
		size = document.getElementById("size_p").value ;
		if (size == "1" && index==1)
		{
			document.getElementById("mc_1").value = "" ;
			document.getElementById("mantello_1").value = "" ;
			document.getElementById("razza_1").value = "" ;
			document.getElementById("sesso_1").value = "" ;
			document.getElementById("taglia_1").value = "" ;
			document.getElementById("data_nascita_cane_1").value = "" ;
		}
		
		if (document.getElementById('cane_'+index)!=null && index!=1)
		{
	  		var clonato = document.getElementById('cane_'+index);
  	  		clonato.parentNode.removeChild(clonato);
  	  		sizeC = document.getElementById('size_p');
  	  		sizeC.value=parseInt(sizeC.value)-1;
		}
	
}
</script>

<input type="button" value="<dhv:label name="button.inserta">Inserisci</dhv:label>" name="Save" onClick="return controlloCuSorveglianza()">
<input type="submit" value="Annulla" onClick="javascript:this.form.action='Canili.do?command=ViewVigilanza&orgId=<%=OrgDetails.getOrgId()%>'">
<br>

<table cellpadding="4" cellspacing="0" width="100%" class="details">
	
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Aggiungi Controllo Ufficiale</dhv:label></strong>
    </th>
	</tr>


<%@ include file="../controlliufficiali/controlli_ufficiali_add.jsp" %>


</table>
<br><br>

<%@ include file="../controlliufficiali/controlli_ufficiali_allarmerapido_add.jsp" %>
	

<%@ include file="../controlliufficiali/controlli_ufficiali_laboratori_haccp.jsp" %>

<br>
<br>
<br>

<%@ include file="../controlliufficiali/controlli_ufficiali_laboratori_haccp_non_in_regione.jsp" %>
	
 
 	<!-- commento al 214-->
<input type = "hidden" id = "size_p" name = "size_p" value = "1">

<table id="canicontrollatitr" cellpadding="4" cellspacing="0" width="100%" class="details" style="display: none">
	
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
     Data Nascita /Data Nascita Presunta
    </th>
   
	</tr>
	<tr id="cane_1">
	<td><b>1</b></td>
		<td><input type = "text" required="required"  id = "mc_1" name = "mc_1" size="30" maxlength="15" readonly="readonly"><font color="red">*</font> 
		</td>
		<td><input type = "text" required="required" id = "razza_1" name = "razza_1" readonly="readonly"><font color="red">*</font> 
		</td>
		
		<td><input type = "text" required="required" id = "taglia_1" name = "taglia_1" readonly="readonly"><font color="red">*</font> 
		</td>
		<td><input type = "text" required="required" id = "mantello_1" name = "mantello_1" readonly="readonly"><font color="red">*</font> 
		</td>
		<td><input type = "text" required="required" id = "sesso_1" name = "sesso_1" readonly="readonly"><font color="red">*</font> 
		</td>
		<td><input type = "text" required="required" id = "data_nascita_cane_1" name = "data_nascita_cane_1" readonly="readonly" value=""><font color="red">*</font> 
		<input type ="hidden" name ="data_decesso_1"  id = "data_decesso_1" value="">
		
		</td>
		
	</tr>
	<tr>
	<td>
	<input type = "button" value="Cerca in BDR" onclick="javascript:opensearchCaneBdr()">
	</td>
	</tr>
</table>  

 
<input type="hidden" name="close" value="">
<input type="hidden" name="refresh" value="-1">
<input type="hidden" name="modified" value="<%=  TicketDetails.getModified() %>" />
<input type="hidden" name="currentDate" value="<%=  request.getAttribute("currentDate") %>" />
<input type="hidden" name="statusId" value="<%=  TicketDetails.getStatusId() %>" />
 

<input type="hidden" name="isAllegato" value="<%=TicketDetails.isListaDistribuzioneAllegata() %>">
<input type="hidden" name="trashedDate" value="<%=  TicketDetails.getTrashedDate() %>" />
<%= addHiddenParams(request, "popup|popupType|actionId") %>


<br>
<input type="button" value="<dhv:label name="button.inserta">Inserisci </dhv:label>" name="Save" onClick="<%--javascript:this.form.action='AccountVigilanza.do?command=InsertContinua&auto-populate=true';--%>return controlloCuSorveglianza()">
<input type="submit" value="Annulla" onClick="javascript:this.form.action='Canili.do?command=ViewVigilanza&orgId=<%=OrgDetails.getOrgId()%>'">
</form>
</body>
