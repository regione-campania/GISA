<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ include file="../initPage.jsp" %>
<%@page import="org.aspcfs.utils.web.LookupList"%>


<%@page import="org.aspcfs.modules.canipadronali.base.Cane"%><jsp:useBean id="fileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<jsp:useBean id="EsitoControllo" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="IspezioneMacrocategorie" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="DistribuzionePartita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="DestinazioneDistribuzione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ArticoliAzioni" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AzioniAdottate" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Provvedimenti2" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Provvedimenti3" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipiControlliCani" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.canipadronali.base.Proprietario" scope="request"/>
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
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popServiceContracts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAssets.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProducts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/controlliUfficiali.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/controlli_ufficiali_canipadronali.js"></script>
<script type="text/javascript" src="dwr/interface/ControlliUfficiali.js"> </script>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<!-- RELATIVO AL NUOVO CALENDARIO CON MESE E ANNO FACILMENTE MODIFICABILI -->
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>




<%
TipoIspezione.setJsEvent("onChange=javascript:mostraMenuTipoIspezione('details')");
TipoCampione.setJsEvent("onChange=javascript:provaFunzione('details')");
TipoAudit.setJsEvent("onChange=javascript:mostraMenu2('details')");
AuditTipo.setJsEvent("onChange=javascript:mostraMenu4('details')");
PianoMonitoraggio1.setJsEvent("onChange=javascript:piani('details')");
PianoMonitoraggio2.setJsEvent("onChange=javascript:piani('details')");
PianoMonitoraggio3.setJsEvent("onChange=javascript:piani('details')");



%>
<script>
function opensearchCaneBdr(){
	var res;
	var result;

	if (document.all) {
		window.open('CaniPadronali.do?command=SearchFormCane',null,
		'height=400px,width=780px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');
		
		
		} else {
		
			res = window.showModalDialog('CaniPadronali.do?command=SearchFormCane',null,
			'dialogWidth:780px;dialogHeight:400px;center: 1; scroll: 0; help: 1; status: 0');
		
		}
		}

function opensearchAzienda(codAzienda){
	var res;
	var result;

	if (codAzienda=='')
		alert('inserire il codice azienda')
		else
		{
	if (document.all) {
		window.open('CaniPadronali.do?command=SearchAzienda&codice_azienda='+codAzienda,null,
		'height=400px,width=780px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');
		
		
		} else {
		
			res = window.showModalDialog('CaniPadronali.do?command=SearchAzienda&codice_azienda='+codAzienda,null,
			'dialogWidth:780px;dialogHeight:400px;center: 1; scroll: 0; help: 1; status: 0');
		
		}
		}
		}
function showCodiceAzienda()
{
if(document.details.tipologia_cu.value=="5")
{
	document.getElementById("row_azienda").style.display="" ;
}
else
{
	document.getElementById("row_azienda").style.display="none" ;
}
}
function  copiaProprietario()
{
	
if (document.details.copia.checked == true )
{
	document.details.nome_conducente.value		=document.details.nominativo_proprietario.value ;
	document.details.cognome_conducente.value	=document.details.cf_proprietario.value ;
	document.details.documento_conducente.value	=document.details.documento_proprietario.value ;
	document.details.luogo_nascita_conducente.value =document.details.luogo_nascita_proprietario.value ;
	document.details.data_nascita_conducente.value =document.details.luogo_nascita_proprietario.value ;

	document.details.citta_conducente.value =document.details.citta_proprietario.value ;
	document.details.cap_conducente.value =document.details.cap_proprietario.value ;
	document.details.indirizzo_conducente.value =document.details.indirizzo_proprietario.value ;
	document.details.provincia_conducente.value =document.details.provincia_proprietario.value ;

	
}
else
{

	document.details.nome_conducente.value		="" ;
	document.details.cognome_conducente.value	="" ;
	document.details.documento_conducente.value	="" ;
	document.details.luogo_nascita_conducente.value ="" ;
	document.details.data_nascita_conducente.value ="";

	document.details.citta_conducente.value ="";
	document.details.cap_conducente.value ="" ;
	document.details.indirizzo_conducente.value ="" ;
	document.details.provincia_conducente.value ="" ;

}
	
}

</script>

<body onLoad="showCodiceAzienda();abilitaCodiceAllerta('details');initprovaFunzione('details');abilitaSistemaAllarmeRabido('details');resetElementiNucleoIspettivo('<%=TicketDetails.getNucleoasList().size() %>');">


<form name="details" action="CaniPadronaliVigilanza.do?command=UpdateTicket&auto-populate=true<%= addLinkParams(request, "popup|popupType|actionId") %>"  method="post">

<%
int numero_cani = OrgDetails.getListaCani().size() ;


%>

<input type = "hidden" id = "elementi_p" name = "elementi_p" value = "<%=numero_cani %>"/>
<input type = "hidden" id = "size_p" name = "size_p" value="<%=numero_cani %>"/>

<dhv:evaluate if="<%= !isPopup(request) %>">
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="CaniPadronali.do?command=SearchForm">Anagrafica Cani di proprieta</a> > 
   <a href="CaniPadronaliVigilanza.do?command=TicketDetails&id=<%=TicketDetails.getId() %>&orgId=<%=OrgDetails.getOrgId() %>"> Scheda  Controllo Ufficiale</a>>
   Modifica Controllo Ufficiale
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="canipadronali" selected="vigilanza" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId()%>' >
   <%--@ include file="accounts_ticket_header_include_vigilanza.jsp" --%>
     <dhv:evaluate if="<%= !TicketDetails.isTrashed() %>" >
      <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>" >
          <dhv:permission name="canipadronali-vigilanza-edit">
            <input type="submit" value="<dhv:label name="button.reopen">Reopen</dhv:label>" onClick="javascript:this.form.action='CaniPadronaliVigilanza.do?command=ReopenTicket&id=<%=TicketDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'">
           </dhv:permission>
           <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='CaniPadronali.do?command=ViewVigilanza&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
           <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='CaniPadronaliVigilanza.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
      </dhv:evaluate>
      <dhv:evaluate if="<%= TicketDetails.getClosed() == null %>" >
          <dhv:permission name="canipadronali-vigilanza-edit">
            <input type="button" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="return controlloCuSorveglianza()">
          </dhv:permission>
           <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='CaniPadronali.do?command=ViewVigilanza&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
           <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='CaniPadronaliVigilanza.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
          <%= showAttribute(request, "closedError") %>
       </dhv:evaluate>
      </dhv:evaluate>
    <br />
    <dhv:formMessage />
    
<table cellpadding="4" cellspacing="0" width="100%" class="details" style="display:none">
	
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Informazioni Proprietario</dhv:label></strong>
    </th>
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel"> Nominativo
		</td>
		<td><input type = "text" name = "nominativo_proprietario" size="50" width="50" value = "<%=OrgDetails.getRagioneSociale() %>"><font color="red">*</font> 
		<input type = "hidden" name = "nome_proprietario" value="<%=OrgDetails.getNome() %>">
		<input type = "hidden" name = "cognome_proprietario" value="<%=OrgDetails.getCognome() %>">
		<input type = "hidden" name = "asl_proprietario" value="<%=OrgDetails.getIdAsl() %>">
		</td> 
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel"> Codice Fiscale
		</td>
		<td><input type = "text"  size="30" maxlength="16" name = "cf_proprietario" value="<%=OrgDetails.getCodiceFiscale() %>"><font color="red">*</font> 
		</td>
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel"> Tipo(i)
		</td>
		<td>
				<input type = "text" name = "tipo_proprietario" value="<%=(OrgDetails!=null)? toHtml(OrgDetails.getTipoProprietarioDetentore()) : "" %>">
		
		</td> 
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel"> Comune di nascita
		</td>
		<td><input type = "text" name = "luogo_nascita_proprietario" value="<%=OrgDetails.getLuogoNascita() %>">
		</td>
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel"> Data Nascita
		</td>
		<td>
		
		<input readonly type="text" id="data_nascita_proprietario" name="data_nascita_proprietario" size="10" />
		<a href="#" onClick="cal19.select(document.forms[0].data_nascita_proprietario,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
		
		
		</td>
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel"> Estremi Documento
		</td>
		<td><input type = "text" name = "documento_proprietario" value="<%=OrgDetails.getDocumentoIdentita() %>">
		</td>
	</tr>


</table>
<table cellpadding="4" cellspacing="0" width="100%" class="details" style="display:none">
	
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Indirizzi Proprietario</dhv:label></strong>
    </th>
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">Citta
		</td>
		<td>
		<input type ="text" name = "citta_proprietario" value = "<%=toHtml(OrgDetails.getLista_indirizzi().get(0).getCitta())%>">
		</td> 
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">Indirizzo
		</td>
		<td>
		<input type ="text" name = "indirizzo_proprietario" value = "<%=toHtml(OrgDetails.getLista_indirizzi().get(0).getVia())%>">
		</td> 
	</tr>
	<tr>
		<td nowrap class="formLabel">Provincia
		</td>
		<td>
		<input type ="text" name = "provincia_proprietario" value = "<%=toHtml(OrgDetails.getLista_indirizzi().get(0).getProvincia())%>">
		</td> 
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">Cap
		</td>
		<td>
		<input type ="text" name = "cap_proprietario" value = "<%=toHtml(OrgDetails.getLista_indirizzi().get(0).getCap())%>">
		</td> 
	</tr>
	<input type ="hidden" name = "address_id" value = "<%=OrgDetails.getLista_indirizzi().get(0).getId()%>">
</table>
<br>
<table cellpadding="4" cellspacing="0" width="100%" class="details" style="display:none">
	
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Informazioni Conduttore</dhv:label></strong>
    </th>
	</tr>
	
	<tr class="containerBody">	
		<td nowrap class="formLabel"> Spuntare se il conduttore<br> coincide con il proprietario
		</td>
		<td><input type = "checkbox" name="copia"  onclick="javascript: copiaProprietario()">
		</td>
	</tr>
	
	<tr class="containerBody">
		<td nowrap class="formLabel"> Nominativo
		</td>
		<td><input type = "text" size="50" width="50" name = "nome_conducente"  value="<%=toHtml(TicketDetails.getNome_conducente()) %>"><font color="red">*</font> 
		</td>
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel"> Codice Fiscale
		</td>
		<td><input type = "text" name = "cognome_conducente"   size="30" maxlength="16" value="<%=toHtml(TicketDetails.getCognome_conducente()) %>">
		</td>
	</tr>
		<tr class="containerBody">
		<td nowrap class="formLabel">Comune di nascita
		</td>
		<td><input type = "text" name = "luogo_nascita_conducente"  value="<%=toHtml(TicketDetails.getLuogo_nascita_conducente()) %>">
		</td>
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">Data Nascita
		</td>
		<td>
		
		<input readonly type="text" id="data_nascita_conducente" name="data_nascita_conducente" size="10" />
		<a href="#" onClick="cal19.select(document.forms[0].data_nascita_conducente,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
		
	
       
		</td>
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel"> Estremi Documento
		</td>
		<td><input type = "text" name = "documento_conducente" value="<%=toHtml(TicketDetails.getDocumento_conducente()) %>"/>
		</td>
	</tr>


</table>
<br>
<table cellpadding="4" cellspacing="0" width="100%" class="details" style="display:none">
	
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Indirizzi Conduttore</dhv:label></strong>
    </th>
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">Citta
		</td>
		<td>
		<input type ="text" name = "citta_conducente" value = "<%=toHtml(TicketDetails.getCitta_conducente()) %>"/>
		</td> 
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">Indirizzo
		</td>
		<td>
		<input type ="text" name = "indirizzo_conducente" value = "<%=toHtml(TicketDetails.getVia_connducente()) %>"/>
		</td> 
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">Provincia
		</td>
		<td>
		<input type ="text" name = "provincia_conducente" value = "<%=toHtml(TicketDetails.getProv_conducente()) %>"/>
		</td> 
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">Cap
		</td>
		<td>

		<input type ="text" name = "cap_conducente" value = "<%=toHtml(TicketDetails.getCap_conducente()) %>"/>

		</td> 
	</tr>
</table>
<br>
    <table cellpadding="4" cellspacing="0" width="100%" class="details" style="display:none">
	
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
	for (Cane cane_vigilanza : OrgDetails.getListaCani()) { %>
	
	<input type = "hidden" name = "assetId_<%=num_cane %>" value = "<%=cane_vigilanza.getId() %>"/>
	<tr class="containerBody">
		<td><b><%=num_cane %></b></td>
		<td><input type = "text" id = "mc_<%=num_cane %>" name = "mc_<%=num_cane %>" value = "<%=toHtml(cane_vigilanza.getMc())%>"/>
		</td>
		<td><input type = "text" id = "razza_<%=num_cane %>" name = "razza_<%=num_cane %>" value = "<%= toHtml(cane_vigilanza.getRazza()) %>"/>
		</td>
	<td><input type = "text" id = "sesso_<%=num_cane %>" name = "sesso_<%=num_cane %>" value = "<%=toHtml(cane_vigilanza.getSesso())  %>"/>
		</td>
		<td><input type = "text" id = "taglia_<%=num_cane %>" name = "taglia_<%=num_cane %>" value = "<%= toHtml(cane_vigilanza.getTaglia())  %>"/>
		</td>
		<td><input type = "text" id = "mantello_<%=num_cane %>"  name = "mantello_<%=num_cane %>" value = "<%=toHtml(cane_vigilanza.getMantello())  %>"/>
		</td>
		<%

		String data_cane = "" ;
		if (cane_vigilanza!=null && cane_vigilanza.getDataNascita()!=null)
			data_cane = sdf2.format(cane_vigilanza.getDataNascita());
		%>
		<td>
		<input type = "text" id = "data_nascita_cane_<%=num_cane %>" name = "data_nascita_cane_<%=num_cane %>" readonly="readonly" value = "<%=data_cane %>">
		<a href="javascript:popCalendar('details','data_nascita_cane_<%=num_cane %>','it','IT','Europe/Berlin');">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle">
		
		</td>
	</tr>
	
	<%num_cane ++ ;
	} %>
	
</table>


</br>
    
<br> 
    
     <table cellpadding="4" cellspacing="0" width="100%" class="details">
<%@ include file="../controlliufficiali/controlli_ufficiali_modify.jsp" %>
  
<tr class="containerBody">
		<td nowrap class="formLabel"> Situazione Controllata 
		</td>
		<%
		
		TipiControlliCani.setJsEvent("onchange=showCodiceAzienda()");
		%>
		<td><%=TipiControlliCani.getHtmlSelect("tipologia_cu",TicketDetails.getTipologia_controllo_cani()) %>
		</td>
	</tr>
<tr style="display: none" id = "row_azienda">
<td nowrap class="formLabel"> Codice Azienda
		</td>
		<td><input type = "text" name = "cod_azienda" id = "cod_azienda" value = "<%=toHtml(TicketDetails.getCodice_azienda()) %>"> &nbsp; 
		<input type ="hidden" id="id_allevamento" name = "id_allevamento" value = "<%=TicketDetails.getId_allevamento() %>">
		<input type ="hidden" id = "ragione_sociale_allevamento" name = "ragione_sociale_allevamento" value = "<%=toHtml(TicketDetails.getRagione_sociale_allevamento()) %>">
		<div id ="azienda">
		<%=toHtml(TicketDetails.getRagione_sociale_allevamento()) %>
		</div>
		<br>
<!-- 		<input type = "button" onclick="opensearchAzienda(document.details.cod_azienda.value)" value="Verifica Esistenza in BDN"> -->
		</td>
		</tr>
</table>
<br>


<br><br>

<%@ include file="../controlliufficiali/controlli_ufficiali_allarmerapido_modify.jsp" %>	
	
 <%@ include file="../controlliufficiali/controlli_ufficiali_laboratori_haccp_modify.jsp" %>	
<br>
<%@ include file="../controlliufficiali/controlli_ufficiali_laboratori_haccp_non_in_regione_modify.jsp" %>

 
        &nbsp;<br>
   <dhv:evaluate if="<%= !TicketDetails.isTrashed() %>" >
    <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>" >
        <dhv:permission name="canipadronali-vigilanza-edit">
          <input type="submit" value="<dhv:label name="button.reopen">Reopen</dhv:label>" onClick="javascript:this.form.action='CaniPadronaliVigilanza.do?command=ReopenTicket&id=<%=TicketDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'">
         </dhv:permission>
         <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='CaniPadronali.do?command=ViewVigilanza&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
         <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='CaniPadronaliVigilanza.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
    </dhv:evaluate>
    <dhv:evaluate if="<%= TicketDetails.getClosed() == null %>" >
        <dhv:permission name="canipadronali-vigilanza-edit">
            <input type="button" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="return controlloCuSorveglianza()">
        </dhv:permission>
         <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='CaniPadronali.do?command=ViewVigilanza&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
         <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='CaniPadronaliVigilanza.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
        <%= showAttribute(request, "closedError") %>
     </dhv:evaluate>
    </dhv:evaluate>
    <input type="hidden" name="modified" value="<%= TicketDetails.getModified() %>">
      <input type="hidden" name="orgId" value="<%=OrgDetails.getOrgId()%>">
      
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
