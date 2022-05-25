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
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - ANY DAMAGES, INCLUDIFNG ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: accounts_details.jsp 19045 2007-02-07 18:06:22Z matt $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.operatorifuoriregione.base.*,org.aspcfs.modules.contacts.base.*, org.aspcfs.modules.base.Constants" %>
<%@page import="org.aspcfs.modules.audit.base.Audit"%>
<%@page import="com.darkhorseventures.framework.actions.ActionContext"%>
<%@page import="java.sql.*"%>
<link rel="stylesheet" type="text/css" href="css/jmesa.css"></link>		
<script type="text/javascript" src="javascript/jquery.bgiframe.pack.js"></script>
<jsp:useBean id="OrgDetailsAttori" class="org.aspcfs.modules.operatorifuoriregione.base.AttoriOpfuoriasl" scope="request"/>
<script type="text/javascript" src="javascript/jquery.jmesa.js"></script>
<script type="text/javascript" src="javascript/jmesa.js"></script>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="IstatList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoLocale" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.operatorifuoriregione.base.Organization" scope="request"/>
<jsp:useBean id="Conducente" class="org.aspcfs.modules.operatorifuoriregione.base.AttoriOpfuoriasl" scope="request"/>
<jsp:useBean id="Mittente" class="org.aspcfs.modules.operatorifuoriregione.base.AttoriOpfuoriasl" scope="request"/>
<jsp:useBean id="Voltura" class="org.aspcfs.modules.cessazionevariazione.base.Ticket" scope="request"/>
<jsp:useBean id="Audit" class="org.aspcfs.modules.audit.base.Audit" scope="request"/>
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SICCodeList" class="org.aspcfs.modules.admin.base.SICCodeList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="refreshUrl" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>

<script>
function chiamaAction(stringa1){
	var scroll = document.body.scrollTop;
	location.href=stringa1+scroll;
}

</script>	

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<%if (refreshUrl!=null && !"".equals(refreshUrl)){ %>
<script language="JavaScript" TYPE="text/javascript">
parent.opener.window.location.href='<%=refreshUrl%><%= request.getAttribute("actionError") != null ? "&actionError=" + request.getAttribute("actionError") :""%>';
</script>
<%}%>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>

<table class="trails" cellspacing="0">
<tr>
<td>
<% String tipoDi = OrgDetails.getTipoDest(); %>



<!--<a href="AltriOperatori.do?command=DashboardScelta"><dhv:label name="">Altri Operatori</dhv:label></a> >--> 
<%--<a href="<%=((tipoDi.equals("Autoveicolo"))?("AltriOperatori.do?command=DashboardScelta"):("Distributori.do?command=ScegliD"))%>"><dhv:label name="">Attività Mobile Fuori Ambito ASL</dhv:label></a> >--%> 
<% if (request.getParameter("return") == null) { %>

<a href="OperatoriFuoriRegione.do?command=Search"><dhv:label name="accounts.SearchResults">Search </dhv:label></a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<a href="OperatoriFuoriRegione.do?command=SearchForm&tipoD=Distributori">Cruscotto</a> >
<%}%>
<dhv:label name="">Dettaglio Attività Fuori Ambito ASL</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<% java.util.Date datamio = new java.util.Date(System.currentTimeMillis());
Timestamp d = new Timestamp (datamio.getTime()); %>
<dhv:permission name="operatoriregione-operatoriregione-report-view">
  <table width="100%" border="0">
    <tr>
      <td nowrap align="right">
 <%if((OrgDetails.getCessato()==1)||((OrgDetails.getSource()==1)&&(OrgDetails.getDateF().after(d)))){%>
  <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Certificato Cessazione" value="Certificato Cessazione"	onClick="javascript:window.location.href='OperatoriFuoriRegione.do?command=PrintReport&file=modelloCessazione.xml&id=<%= OrgDetails.getId() %>';">
 		
  <%}%>
        <br>
        <%if((OrgDetails.getTipoDest()!=null)&& (OrgDetails.getTipoDest().equals("Autoveicolo"))){ %>
    	 <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Stampa scheda" value="Stampa scheda"	onClick="javascript:window.location.href='OperatoriFuoriRegione.do?command=PrintReport&file=account_attivitaMobili.xml&id=<%= OrgDetails.getId() %>';">
 		<%}else{ %>
 		<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Stampa scheda" value="Stampa scheda"	onClick="javascript:window.location.href='OperatoriFuoriRegione.do?command=PrintReport&file=account.xml&id=<%= OrgDetails.getId() %>';">
 		<%} %>
      </td>
    </tr>
  </table>
</dhv:permission>


<br>
<br>
<%@ include file="../../controlliufficiali/diffida_list.jsp" %>

<% String param1 = "orgId=" + OrgDetails.getOrgId();
   %>
<dhv:container name="<%= OrgDetails.getTipoDest().equals("Autoveicolo")?("operatoriregioneT"):("operatoriregione")%>" selected="details" object="OrgDetails" param="<%= param1 %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' hideContainer="<%= !OrgDetails.getEnabled() || OrgDetails.isTrashed() %>">
<input type="hidden" name="orgId" value="<%= OrgDetails.getOrgId() %>"> 
<dhv:evaluate if="<%=OrgDetails.isTrashed()%>">
    <!--<dhv:permission name="operatoriregione-operatoriregione-edit">
      <input type="button" value="Ripristina"	onClick="javascript:window.location.href='OperatoriFuoriRegione.do?command=Restore&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>-->
</dhv:evaluate>

<dhv:evaluate if="<%=!OrgDetails.isTrashed()%>">
<dhv:evaluate if="<%=((OrgDetails.getCessato()!=1)||((OrgDetails.getSource()==1)&&(OrgDetails.getDateF().after(d))))%>">

  <dhv:evaluate if="<%=(OrgDetails.getEnabled())%>">
    <dhv:permission name="operatoriregione-operatoriregione-edit"><input type="button"  value="Modifica"	onClick="javascript:window.location.href='OperatoriFuoriRegione.do?command=Modify&orgId=<%= OrgDetails.getOrgId()%>&tipoD=<%=OrgDetails.getTipoDest()%><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission>
  </dhv:evaluate>
  
  <dhv:evaluate if="<%=!(OrgDetails.getEnabled())%>">
    <dhv:permission name="operatoriregione-operatoriregione-edit">
      <input type="button" value="<dhv:label name="global.button.Enable">Enable</dhv:label>" 	onClick="javascript:window.location.href='OperatoriFuoriRegione.do?command=Enable&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>
  </dhv:evaluate>
  <dhv:evaluate if='<%= (request.getParameter("actionplan") == null) %>'>
    <dhv:permission name="operatoriregione-operatoriregione-delete"><input type="button" value="<dhv:label name="">Elimina Operatore</dhv:label>" onClick="javascript:popURLReturn('OperatoriFuoriRegione.do?command=ConfirmDelete&id=<%=OrgDetails.getId()%>&popup=true','OperatoriFuoriRegione.do?command=Search', 'Delete_account','320','200','yes','no');"></dhv:permission>
  </dhv:evaluate>
  </dhv:evaluate>
  <dhv:evaluate if='<%= (OrgDetails.getTipoDest().equalsIgnoreCase("distributori")) %>'>
    <input type="button" value="<dhv:label name="">Importa Distributori da File</dhv:label>" onClick="javascript:window.location.href='OperatoriFuoriRegione.do?command=InsertDistributori&id=<%=OrgDetails.getId()%>&tipoD=<%=OrgDetails.getTipoDest()%>'">
   
  </dhv:evaluate>
</dhv:evaluate>
<dhv:permission name="operatoriregione-operatoriregione-edit,operatoriregione-operatoriregione-delete"><br>&nbsp;</dhv:permission>



<%-- 	 <dhv:evaluate if='<%=OrgDetails.getStatoLab()!=4 %>'> --%>
<%-- 	 <dhv:permission name="osa-cessazione-pregressa-view"> --%>
<!-- 	 <div class="ovale"align="center"> -->
<!-- 	 <br> -->
<!-- 	 <p> -->
<!-- 	 <center> -->
<!-- 	 <input type="button" value="CESSAZIONE OSA"  onclick="openPopUpCessazioneAttivita();" width="120px;" > -->
<!-- 	 </center></p> -->
<!-- 	 <br><br> -->
<!-- 	 </div> -->
<%-- 	 </dhv:permission> --%>
	 
<%-- 	 <jsp:include page="../dialog_cessazione_attivita.jsp"> --%>
<%-- 	 <jsp:param value="<%=OrgDetails.getOrgId() %>" name="idAnagrafica"/> --%>
<%-- 	 <jsp:param value="OperatoriFuoriRegione.do?command=CessazioneAttivita" name="urlSubmitCessazione"/> --%>
<%-- 	 <jsp:param value="<%=OrgDetails.getDate1() != null ? new SimpleDateFormat("ss:mm:hh dd/MM/yyyy").format(new java.util.Date(OrgDetails.getDate1().getTime())) : "" %>" name="data_inizio" /> --%>
<%-- 	 </jsp:include> --%>
<%--      </dhv:evaluate> --%>
  
  <dhv:permission name="note_hd-view">
<jsp:include page="../note_hd/link_note_hd.jsp">
<jsp:param name="riferimentoId" value="<%=OrgDetails.getOrgId() %>" />
<jsp:param name="riferimentoIdNomeTab" value="organization" />
</jsp:include> <br><br>
</dhv:permission>

<jsp:include page="../preaccettazionesigla/button_preaccettazione.jsp">
    <jsp:param name="riferimentoIdPreaccettazione" value="<%=OrgDetails.getOrgId() %>" />
    <jsp:param name="riferimentoIdNomePreaccettazione" value="orgId" />
    <jsp:param name="riferimentoIdNomeTabPreaccettazione" value="organization" />
    <jsp:param name="userIdPreaccettazione" value="<%=User.getUserId() %>" />
</jsp:include>

<%if (1==1) { %>
<jsp:include page="../schede_centralizzate/iframe.jsp">
    <jsp:param name="objectId" value="<%=OrgDetails.getOrgId() %>" />
     <jsp:param name="tipo_dettaglio" value="37" />
     </jsp:include>
<% } else { %>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Proprietario del Veicolo</dhv:label></strong>
    </th>
  </tr>
  <dhv:evaluate if='<%= (OrgDetails.getFuori_regione()) %>'>
 <tr class="containerBody">
 	<td nowrap class="formLabel"><dhv:label
						name="">Operatore</dhv:label></td>
 	<td>
        <%= SiteList.getValueFromId(OrgDetails.getSiteId()) %>&nbsp;
    </td>
 </tr>
 </dhv:evaluate>
 <dhv:evaluate if='<%= (!OrgDetails.getFuori_regione()) %>'>
 <tr class="containerBody">
 	<td nowrap class="formLabel"><dhv:label
						name="">Operatore</dhv:label></td>
 	<td>
 		<%if (OrgDetails.getSiteId() != -1){ %>
        <%= toHtml("di Altre ASL della Campania : "+SiteList.getValueFromId(OrgDetails.getSiteId())) %>&nbsp;
        <%}
 		else
 		{
 			%>
 			 <%= toHtml("di Altre ASL della Campania : Nessuna Asl di Appartenenza Selezionata" )%>&nbsp;
 			<%
 		}
 		%>
    </td>
 </tr>
 </dhv:evaluate>
<dhv:include name="accounts-name" none="true">
      <tr class="containerBody">
        <td nowrap class="formLabel" name="orgname1" id="orgname1">
          <dhv:label name="accounts.accounts_add.OrganizationName">Organization Name</dhv:label>
        </td>
        <td>
          <%= toHtmlValue(OrgDetails.getName()) %>&nbsp;
       </td>
      </tr>
</dhv:include>  
  
<dhv:evaluate if="<%= hasText(OrgDetails.getBanca()) %>">
    	<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Denominazione</dhv:label>
			</td>
			<td>
         		<%= toHtml(OrgDetails.getBanca()) %>&nbsp;
			</td>
		</tr>
</dhv:evaluate>
<dhv:evaluate if="<%= hasText(OrgDetails.getCodiceImpresaInterno()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Numero Registrazione</dhv:label>
    </td>
    <td>
       <%= toHtml(OrgDetails.getCodiceImpresaInterno()) %>&nbsp;
    </td>
  </tr>
</dhv:evaluate>
  <dhv:evaluate if="<%= hasText(OrgDetails.getPartitaIva()) %>">
    <tr class="containerBody">
			<td nowrap class="formLabel">
      Partita IVA
			</td>
			<td>
         <%= toHtml(OrgDetails.getPartitaIva()) %>&nbsp;
			</td>
		</tr>
  </dhv:evaluate>
 <dhv:evaluate if="<%= hasText(OrgDetails.getCodiceFiscale()) %>">
    <tr class="containerBody">
			<td nowrap class="formLabel">
      Codice Fiscale
			</td>
			<td>
         <%= toHtml(OrgDetails.getCodiceFiscale()) %>&nbsp;
			</td>
		</tr>
  </dhv:evaluate>
  
    <dhv:evaluate if="<%= hasText(OrgDetails.getCodiceFiscaleCorrentista()) %>">
    	<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Codice Istat Principale</dhv:label>
			</td>
			<td>
        		<%= toHtml(OrgDetails.getCodiceFiscaleCorrentista()) %>&nbsp;
			</td>
		</tr>
  	</dhv:evaluate>  
<dhv:include name="organization.alert" none="true">
    <dhv:evaluate if="<%= hasText(OrgDetails.getAlertText()) %>">
      <tr class="containerBody">
       <td nowrap class="formLabel">
         <dhv:label name="accounts.accounts_add.AlertDescription">Alert Description</dhv:label>
       </td>
       <td>
         <%= toHtml(OrgDetails.getAlertText()) %>
       </td>
     </tr>
   </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.sicDescription" none="true">
    <dhv:evaluate if="<%= hasText(OrgDetails.getSicDescription()) %>">
      <tr class="containerBody">
	    <td nowrap class="formLabel">
          <dhv:label name="accounts.accounts_add.sicDescription">SIC Description</dhv:label>
		</td>
		<td>
         <%= toHtml(OrgDetails.getSicDescription()) %>&nbsp;
		</td>
	  </tr>
    </dhv:evaluate>
</dhv:include>
<%if(hasText(OrgDetails.getCodice1())) {%> 
    	<tr class="containerBody">
			 <td nowrap class="formLabel">
      <dhv:label name="">Codici Istat Secondari</dhv:label>  
   </td>   
    <td>
      		 <%if((OrgDetails.getCodice1()!= null) && (!OrgDetails.getCodice1().equals(""))){ %>   
      		 Codice 1&nbsp;&nbsp;
      		 <%= toHtmlValue(OrgDetails.getCodice1()) %>
      		 <%= request.getAttribute("codice1") %></br>
      		 <%}if((OrgDetails.getCodice2()!= null) && (!OrgDetails.getCodice2().equals(""))){ %>
      		 Codice 2&nbsp;&nbsp;
      		 <%= toHtmlValue(OrgDetails.getCodice2()) %>
      		 <%= request.getAttribute("codice2") %></br>
      		  <%}if((OrgDetails.getCodice3()!= null) && (!OrgDetails.getCodice3().equals(""))){ %>
      		 Codice 3&nbsp;&nbsp;
      		 <%= toHtmlValue(OrgDetails.getCodice3()) %>
      		 <%= request.getAttribute("codice3") %></br>
      		  <%}if((OrgDetails.getCodice4()!= null) && (!OrgDetails.getCodice4().equals(""))){ %>
      		 Codice 4&nbsp;&nbsp;
      		  <%= toHtmlValue(OrgDetails.getCodice4()) %>
      		  <%= request.getAttribute("codice4") %></br>
      		  <%}if((OrgDetails.getCodice5()!= null) && (!OrgDetails.getCodice5().equals(""))){ %>
      		 Codice 5&nbsp;&nbsp; 
      		 <%= toHtmlValue(OrgDetails.getCodice5()) %>
      		 <%= request.getAttribute("codice5") %></br>
      		  <%}if((OrgDetails.getCodice6()!= null) && (!OrgDetails.getCodice6().equals(""))){ %>
			 Codice 6&nbsp;&nbsp;
			 <%= toHtmlValue(OrgDetails.getCodice6()) %>
			 <%= request.getAttribute("codice6") %></br>
      		  <%}if((OrgDetails.getCodice7()!= null) && (!OrgDetails.getCodice7().equals(""))){ %>
			 Codice 7&nbsp;&nbsp;
			 <%= toHtmlValue(OrgDetails.getCodice7()) %>
			 <%= request.getAttribute("codice7") %></br>
      		  <%}if((OrgDetails.getCodice8()!= null) && (!OrgDetails.getCodice8().equals(""))){ %>
			 Codice 8&nbsp;&nbsp;
			 <%= toHtmlValue(OrgDetails.getCodice8()) %>
			 <%= request.getAttribute("codice8") %></br>
      		  <%}if((OrgDetails.getCodice9()!= null) && (!OrgDetails.getCodice9().equals(""))){ %>
			 Codice 9&nbsp;&nbsp;
			 <%= toHtmlValue(OrgDetails.getCodice9()) %>
			 <%= request.getAttribute("codice9") %></br>
      		  <%}if((OrgDetails.getCodice10()!= null) && (!OrgDetails.getCodice10().equals(""))){ %>
	         Codice 10
      		 <%= toHtmlValue(OrgDetails.getCodice10()) %>
      		 <%= request.getAttribute("codice10") %></br>
      		 <%} %>
     </td>
		</tr>
		<%}else{} %>
    <dhv:evaluate if="<%= hasText(OrgDetails.getCab()) %>">
      <tr class="containerBody">
	    <td nowrap class="formLabel">
          <dhv:label name="accounts.accounts_add.cab">CAB</dhv:label>
		</td>
		<td>
         <%= toHtml(OrgDetails.getCab()) %>&nbsp;
		</td>
	  </tr>
    </dhv:evaluate>
<dhv:include name="organization.url" none="true">
  <dhv:evaluate if="<%= hasText(OrgDetails.getUrl()) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.WebSiteURL">Web Site URL</dhv:label>
      </td>
      <td>
        <a href="<%= toHtml(OrgDetails.getUrlString()) %>" target="_new"><%= toHtml(OrgDetails.getUrl()) %></a>&nbsp;
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.industry" none="true">
  <dhv:evaluate if="<%= hasText(OrgDetails.getIndustryName()) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.Industry">Industry</dhv:label>
      </td>
      <td>
         <%= toHtml(OrgDetails.getIndustryName()) %>&nbsp;
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.dunsType" none="true">
  <dhv:evaluate if="<%= hasText(OrgDetails.getDunsType()) %>">
    <tr class="containerBody"><td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.duns_type">DUNS Type</dhv:label>
    </td><td>
       <%= toHtml(OrgDetails.getDunsType()) %>&nbsp;
    </td></tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.employees" none="true">
  <dhv:evaluate if="<%= (OrgDetails.getEmployees() > 0) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="organization.employees">No. of Employees</dhv:label>
      </td>
      <td>
         <%= OrgDetails.getEmployees() %>
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.potential" none="true">
  <dhv:evaluate if="<%= (OrgDetails.getPotential() > 0) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.Potential">Potential</dhv:label>
    </td>
    <td>
       <zeroio:currency value="<%= OrgDetails.getPotential() %>" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;"/>
    </td>
  </tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.revenue" none="true">
  <dhv:evaluate if="<%= (OrgDetails.getRevenue() > 0) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.Revenue">Revenue</dhv:label>
      </td>
      <td>
         <zeroio:currency value="<%= OrgDetails.getRevenue() %>" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;"/>
      </td>
    </tr>
  </dhv:evaluate>
  </dhv:include>
  <dhv:include name="organization.ticker" none="true">
  <dhv:evaluate if="<%= hasText(OrgDetails.getTicker()) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.TickerSymbol">Ticker Symbol</dhv:label>
      </td>
      <td>
         <%= toHtml(OrgDetails.getTicker()) %>&nbsp;
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.dunsNumber" none="true">
  <dhv:evaluate if="<%= hasText(OrgDetails.getDunsNumber()) %>">
    <tr class="containerBody"><td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.duns_number">DUNS Number</dhv:label>
    </td><td>
       <%= toHtml(OrgDetails.getDunsNumber()) %>&nbsp;
    </td></tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.businessNameTwo" none="true">
  <dhv:evaluate if="<%= hasText(OrgDetails.getBusinessNameTwo()) %>">
    <tr class="containerBody"><td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.business_name_two">Business Name 2</dhv:label>
    </td><td>
       <%= toHtml(OrgDetails.getBusinessNameTwo()) %>&nbsp;
    </td></tr>
  </dhv:evaluate>
</dhv:include>
<%--
<dhv:include name="organization.sicCode" none="true">
  <dhv:evaluate if="<%= (OrgDetails.getSicCode() > -1) %>">
    <tr class="containerBody"><td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.sic_code">SIC</dhv:label>
    </td><td>
       <%= SICCodeList.getDescriptionByCode(OrgDetails.getSicCode()) %>&nbsp;
    </td></tr>
  </dhv:evaluate>
</dhv:include>
--%>
<dhv:include name="accounts-size" none="true">
  <dhv:evaluate if="<%= hasText(OrgDetails.getAccountSizeName()) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.accountSize">Account Size</dhv:label>
      </td>
      <td>
      
      <%= toHtml(OrgDetails.getAccountSizeName()) %>&nbsp;
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.segment" none="true">
  <dhv:evaluate if="<%= (OrgDetails.getSegmentId() > 0) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.segment">Segment</dhv:label>
      </td>
      <td>
         <%= SegmentList.getSelectedValue(OrgDetails.getSegmentId()) %>
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.directBill" none="true">
  <dhv:evaluate if="<%= OrgDetails.getDirectBill() %>">
    <tr>
      <tr class="containerBody">
        <td nowrap class="formLabel">
          Direct Bill
        </td>
      <td>
      <input type="checkbox" name="directBill" CHECKED DISABLED />
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>

    <dhv:evaluate if="<%= hasText(OrgDetails.getContoCorrente()) %>">
  	<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Tipo Veicolo</dhv:label>
			</td>
			<td>
         		<%= toHtml(OrgDetails.getContoCorrente()) %>&nbsp;
			</td>
		</tr>
  	</dhv:evaluate>  
    <dhv:evaluate if="<%= hasText(OrgDetails.getNomeCorrentista()) %>">
    	<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Identificativo Veicolo</dhv:label>
			</td>
			<td>
         		<%= toHtml(OrgDetails.getNomeCorrentista()) %>&nbsp;
			</td>
		</tr>
  	</dhv:evaluate> 
  	<%if(hasText(OrgDetails.getNomeCorrentista())) {%> 
  	<%-- %><tr class="containerBody"><td nowrap class="formLabel">
      <dhv:label name="">Codice Contenitore</dhv:label>
    </td><td>
       <%= toHtmlValue(OrgDetails.getCodiceCont()) %>&nbsp;
    </td></tr>--%>
  	<%}
  	else{}%>
  	
  	<dhv:include name="organization.stage" none="true">
  <dhv:evaluate if="<%= OrgDetails.getStageId() != -1 %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="account.stage">Stage</dhv:label>
      </td>
      <td>
        <%= StageList.getSelectedValue(OrgDetails.getStageId()) %> 
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>
<%--if(hasText(OrgDetails.getTipoDest())) {%>
	<tr class="containerBody">
  		<td nowrap class="formLabel">
     		 <dhv:label name="">Attività</dhv:label>
    	</td>
    	<td>
    	 <dhv:evaluate if="<%= OrgDetails.getTipoDest().equals("Autoveicolo")%>">
        Mobile
        </dhv:evaluate>
       
        <dhv:evaluate if="<%= OrgDetails.getTipoDest().equals("Es. Commerciale")%>">
        Fissa
        </dhv:evaluate>
      		 <%= toHtmlValue(OrgDetails.getTipoDest()) %>&nbsp;
   		</td>
  	</tr>
  	<%} --%>
  	<%if(hasText(OrgDetails.getCodiceCont())) {%>
   <tr class="containerBody"><td nowrap class="formLabel">
      <dhv:label name="">Codice Contenitore</dhv:label>
    </td><td>
       <%= toHtmlValue(OrgDetails.getCodiceCont()) %>&nbsp;
    </td></tr>
  	<%}else{} %>
  	<dhv:include name="organization.source" none="true">
  <dhv:evaluate if="<%= OrgDetails.getSource() != -1 %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="contact.source">Source</dhv:label>
      </td>
      <td>
        <%= SourceList.getSelectedValue(OrgDetails.getSource()) %> 
        <dhv:evaluate if="<%= OrgDetails.getSource()==1 %>">
        &nbsp; dal <zeroio:tz timestamp="<%= OrgDetails.getDateI() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/><%--= (request.getParameter("dateI") ) --%> <zeroio:tz timestamp="<%= request.getParameter("dateI") %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/> al&nbsp; <zeroio:tz timestamp="<%= OrgDetails.getDateF() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
         <dhv:evaluate if="<%= OrgDetails.getCessazione()%>">
        Cessazione automatica
       </dhv:evaluate>
        </dhv:evaluate>
        
       
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>  

        

<dhv:include name="organization.rating" none="true">
  <dhv:evaluate if="<%= OrgDetails.getRating() != -1 %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="sales.rating">Rating</dhv:label>
      </td>
      <td>
        <%= RatingList.getSelectedValue(OrgDetails.getRating()) %> 
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>
 <dhv:include name="organization.date1" none="true">
    <dhv:evaluate if="<%= (OrgDetails.getDate1() != null) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Data Presentazione D.I.A./Inizio Attività</dhv:label>
      </td>
      <td>
        <zeroio:tz timestamp="<%= OrgDetails.getDate1() %>" dateOnly="true" showTimeZone="false" default="&nbsp;" />
      </td>
    </tr>
    </dhv:evaluate>
 </dhv:include> 
 <dhv:evaluate if="<%= (OrgDetails.getDate2() != null) %>">
   <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Data inizio attività</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= OrgDetails.getDate2() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
    </td>
  </tr>
</dhv:evaluate>
<%--  
  <tr class="containerBody">
	<td nowrap class="formLabel">
      	<dhv:label name="">Stato Impresa</dhv:label>
	</td>
	<td>
	<%
		if(OrgDetails.getSource()== 1){%>
         <%= ((OrgDetails.getDateF()!= null && OrgDetails.getDateF().before(d))  ? ("Cessato in data ") : ("In Attività")) %>&nbsp;
		<%if((OrgDetails.getDateF()!= null) && (OrgDetails.getDateF().before(d))){%>
		<zeroio:tz timestamp="<%= OrgDetails.getDateF() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/> ,di conseguenza  non più gestibile in modifica.	        
	  		<%} %>
		<%}else{
	%>
         <%= ((OrgDetails.getCessato()== 1 ) ? ("Cessato in data ") : ((OrgDetails.getCessato()==0)?("In Attività"):("Sospeso in data "))) %>&nbsp;
	


<% if((OrgDetails.getCessato()==1) || (OrgDetails.getCessato()==2)) {%>
  
    
      <zeroio:tz timestamp="<%= OrgDetails.getContractEndDate() %>" dateOnly="true" timeZone="<%= OrgDetails.getContractEndDateTimeZone() %>" showTimeZone="false" default="&nbsp;"/>  <%=((OrgDetails.getCessato()==1) ? (",di conseguenza  non più gestibile in modifica.") : (""))%>
    
   
<%} }%>
</td>
</tr>--%>
</table>
<br />

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
   <tr>
    <th colspan="2">
      <strong><dhv:label name="">Titolare o Legale Rappresentante</dhv:label></strong>
     
    </th>
  </tr>
  <dhv:evaluate if="<%= (hasText(OrgDetails.getCodiceFiscaleRappresentante()))||(hasText(Voltura.getCodiceFiscaleRappresentante())) %>">			
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			Codice Fiscale
			</td>
			<td>
         	<%= ((OrgDetails.getVoltura())?(Voltura.getCodiceFiscaleRappresentante()):(OrgDetails.getCodiceFiscaleRappresentante())) %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
		<dhv:evaluate if="<%= (hasText(OrgDetails.getNomeRappresentante())) ||(hasText(Voltura.getNomeRappresentante())) %>">		
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			Nome
			</td>
			<td>
         	<%= ((OrgDetails.getVoltura())?(Voltura.getNomeRappresentante()):(OrgDetails.getNomeRappresentante())) %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
  	 <dhv:evaluate if="<%= (hasText(OrgDetails.getCognomeRappresentante()))||(hasText(Voltura.getCognomeRappresentante())) %>">
<tr class="containerBody">
			<td nowrap class="formLabel">
      			Cognome
			</td>
			<td>
         	<%= ((OrgDetails.getVoltura())?(Voltura.getCognomeRappresentante()):(OrgDetails.getCognomeRappresentante())) %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>

<dhv:evaluate if="<%= (OrgDetails.getDataNascitaRappresentante() != null) || (Voltura.getDataNascitaRappresentante() != null) %>">
   <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Data Nascita</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= ((OrgDetails.getVoltura())?(Voltura.getDataNascitaRappresentante()):(OrgDetails.getDataNascitaRappresentante())) %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
    </td>
  </tr>
</dhv:evaluate>
  	 
		<dhv:evaluate if="<%= (hasText(OrgDetails.getLuogoNascitaRappresentante()))||(hasText(Voltura.getLuogoNascitaRappresentante())) %>">			
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Comune di nascita</dhv:label>
			</td>
			<td>
         	<%= ((OrgDetails.getVoltura())?(Voltura.getLuogoNascitaRappresentante()):(OrgDetails.getLuogoNascitaRappresentante()))%>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
	
	<dhv:evaluate if="<%= (hasText(OrgDetails.getEmailRappresentante()))||(hasText(Voltura.getEmailRappresentante())) %>">						
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Email</dhv:label>
			</td>
			<td>
         	<%= ((OrgDetails.getVoltura())?(Voltura.getEmailRappresentante()):(OrgDetails.getEmailRappresentante())) %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
		
	<dhv:evaluate if="<%= (hasText(OrgDetails.getTelefonoRappresentante())) %>">							
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Telefono</dhv:label>
			</td>
			<td>
         	<%= ((OrgDetails.getTelefonoRappresentante())) %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
		
		<dhv:evaluate if="<%= (hasText(OrgDetails.getFax())) %>">							
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Fax</dhv:label>
			</td>
			<td>
         	<%= ((OrgDetails.getTelefonoRappresentante())) %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
		
		<!--  fine delle modifiche -->
		
</table>
<br>
<% if(Conducente.getCognome()!=null || Conducente.getNome()!=null){ %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Conducente</dhv:label></strong>
      <input type="hidden" name="tipologia" value="1">
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Cognome
    </td>
    <td>
      <%= toHtmlValue(Conducente.getCognome()) %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Nome
    </td>
    <td>
      <%= toHtmlValue(Conducente.getNome()) %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Data Nascita</dhv:label>
      </td>
      <td>
        <zeroio:tz timestamp="<%= Conducente.getData_nascita()%>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
      </td>
    </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="">Comune di nascita</dhv:label>
    </td>
    <td>
      <%= toHtmlValue(Conducente.getLuogo_nascita()) %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="">Residenza</dhv:label>
    </td>
    <td>
      <%= toHtmlValue(Conducente.getComune()) %><br>&nbsp;
      <%= toHtmlValue(Conducente.getIndirizzo()) %><br>
      <%= toHtmlValue(Conducente.getProvincia()) %>
    </td>
    
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="">Documento</dhv:label>
    </td>
    <td>
      <%= toHtmlValue(Conducente.getDocumento()) %>&nbsp;
    </td>
    
  </tr>

</table>
<br>
<%}
if(Mittente.getRagione_sociale()!=null){
%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Mittente Merce</dhv:label></strong>
 	<input type="hidden" name="tipologia" value="2">
     </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="">Ragione Sociale</dhv:label>
    </td>
    <td>
      <%= toHtmlValue(Mittente.getRagione_sociale()) %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Sede Legale
    </td>
    <td>
      <%= toHtmlValue(Mittente.getComune()) %><br>&nbsp;
      <%= toHtmlValue(Mittente.getIndirizzo()) %><br>
      <%= toHtmlValue(Mittente.getProvincia()) %>
    </td>
    
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="">Merce Trasportata</dhv:label>
    </td>
    <td>
      <%= toHtmlValue(Mittente.getMerce()) %>&nbsp;
    </td>
    
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="">Note</dhv:label>
    </td>
    <td>
      <%= toHtmlValue(Mittente.getNote()) %>&nbsp;
    </td>
    
  </tr>

</table>
<%} %>
<br>
<dhv:include name="organization.addresses" none="true">
<%
  Iterator iaddress = OrgDetails.getAddressList().iterator();
  Object address[] = null;
  int i = 0;
  int locali=0;
  if (iaddress.hasNext()) {
    while (iaddress.hasNext()) {
      OrganizationAddress thisAddress = (OrganizationAddress)iaddress.next();
%>  
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
        <strong><dhv:label name="accounts.accounts_add.Addressess">Sede legale</dhv:label></strong>
	</th>
  <%if(OrgDetails.getFuori_regione()){ %>
   <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Nazione d'origine</dhv:label>
    </td>
    <td>
       <%= toHtmlValue(thisAddress.getStreetAddressLine2()) %>
    </td>
  </tr>  
  <%} else{%>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">ASL Appartenenza</dhv:label>
    </td>
    <td>
    <%= SiteList.getSelectedValue(thisAddress.getStreetAddressLine3()) %>&nbsp;
      <input type="hidden" size="40" name="address1line3" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine3()) %>">
    </td>
  </tr>  
  <% } %>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
        indirizzo
      </td>
      <td>
        <%= toHtml(thisAddress.toString()) %>&nbsp;
        <dhv:evaluate if="<%=thisAddress.getPrimaryAddress()%>">        
          <dhv:label name="requestor.primary.brackets">(Primary)</dhv:label>
        </dhv:evaluate>
      </td>
   
    </tr>
    
    
    </table><br>
<%
    }
  } else {
%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="requestor.requestor_add.Addresses">Addresses</dhv:label></strong>
	  </th>
  </tr>
    <tr class="containerBody">
      <td colspan="2">
        <font color="#9E9E9E"><dhv:label name="contacts.NoAddresses">No addresses entered.</dhv:label></font>
      </td>
    </tr>
    </table><br>
<%}%>
</dhv:include>

 <%if(hasText(OrgDetails.getNotes())){ %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="requestor.requestor_add.AdditionalDetails">Additional Details</dhv:label></strong>
	  </th>
  </tr>
 
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Luogo di controllo/Note</dhv:label>
    </td>
    <td>
      <%=toHtml(OrgDetails.getNotes()) %>&nbsp;
    </td>
  </tr>
  
</table>
<br />
<%} %>

<% } %>

<%if(OrgDetails.getTipoDest().equalsIgnoreCase("distributori")){ %>

<p>
			Utilizzare le caselle vuote sopra l'intestazione per filtrare per anno, richiedente, ragione sociale, capo e/o rapporto
		</p>
		<%
		request.setAttribute("Organization",OrgDetails);
		%>
		
		
		<%
		if(request.getAttribute("errore")!=null){
			
			%>
			<font color="red">
			<%=request.getAttribute("errore")+" Utilizzare il seguente formato (gg/mm/yyyy) <br>" %>
			
			</font>
			
			<% 
			
		}
		
		%>
		
		
		
		
	<%
	String aggiunto="false";
	if(request.getAttribute("aggiunto")!=null)
	 aggiunto=(String)request.getAttribute("aggiunto");
	
	%>

		
	 <%
  Integer numeroDistributori =(Integer) request.getAttribute("numeroDistributori");
  
  %>
  

		<a href="javascript:chiamaAction('DistributoriListFuoriRegione.do?command=Add&orgId=<%=OrgDetails.getOrgId() %>&maxRows=15&15_sw_=true&15_tr_=true&15_p_=<%=numeroDistributori %>&15_mr_=15&scroll=')">Inserisci Distributore se non importato da file</a>
		
		<form name="aiequidiForm" action="DistributoriListFuoriRegione.do?orgId=<%=OrgDetails.getOrgId() %>&aggiunto=<%=aggiunto %>">
		<input type="hidden" name="orgId" value="<%=OrgDetails.getOrgId() %>">
	     
	     <%if(request.getAttribute("add")!=null) {%>
	     <input type="hidden" name="add" value="add">
	     
	     <%} %>
	     
	     
	      <jmesa:htmlColumn property="chkbox" title=" " worksheetEditor="org.jmesa.worksheet.editor.CheckboxWorksheetEditor" filterable="false" sortable="false"/>
	     
	     <%if(request.getAttribute( "tabella" )!=null) {%>
	       <%=request.getAttribute( "tabella" )%>
	       <%} %>
	    <jmesa:tableFacade editable="true" >   <jmesa:htmlRow uniqueProperty="matricola">   </jmesa:htmlRow></jmesa:tableFacade>
	    
	    <script type="text/javascript">
            function onInvokeAction(id) {
                $.jmesa.setExportToLimit(id, '');
                $.jmesa.createHiddenInputFieldsForLimitAndSubmit(id);
            }
            function onInvokeExportAction(id) {
                var parameterString = $.jmesa.createParameterStringForLimit(id);
				
                 location.href = 'DistributoriListFuoriRegione.do?&' + parameterString;
            }
    </script>
	    </form>
	   


<%} %>


<dhv:permission name="operatoriregione-operatoriregione-report-view">
  <table width="100%" border="0">
    <tr>
      <td nowrap align="right">
 <%if((OrgDetails.getCessato()==1)||((OrgDetails.getSource()==1)&&(OrgDetails.getDateF().after(d)))){%>
  <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Certificato Cessazione" value="Certificato Cessazione"	onClick="javascript:window.location.href='OperatoriFuoriRegione.do?command=PrintReport&file=modelloCessazione.xml&id=<%= OrgDetails.getId() %>';">
 		
  <%}%>
        <br>
        <%if((OrgDetails.getTipoDest()!=null)&& (OrgDetails.getTipoDest().equals("Autoveicolo"))){ %>
    	 <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Stampa scheda" value="Stampa scheda"	onClick="javascript:window.location.href='OperatoriFuoriRegione.do?command=PrintReport&file=account_attivitaMobili.xml&id=<%= OrgDetails.getId() %>';">
 		<%}else{ %>
 		<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Stampa scheda" value="Stampa scheda"	onClick="javascript:window.location.href='OperatoriFuoriRegione.do?command=PrintReport&file=account.xml&id=<%= OrgDetails.getId() %>';">
 		<%} %>
      </td>
    </tr>
  </table>
</dhv:permission>
</dhv:container>
<%= addHiddenParams(request, "popup|popupType|actionId") %>
<% if (request.getParameter("return") != null) { %>
<input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>
<% if (request.getParameter("actionplan") != null) { %>
<input type="hidden" name="actionplan" value="<%=request.getParameter("actionplan")%>">
<%}%>

<script>
document.body.scrollTop=<%= request.getAttribute("scroll") %>;
</script>
