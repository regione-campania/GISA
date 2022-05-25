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
  - Version: $Id: accounts_list.jsp 18543 2007-01-17 02:55:07Z matt $
  - Description:
  --%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.allevamenti.base.*, org.aspcfs.modules.base.*" %>

<%@page import="it.izs.bdn.bean.InfoAllevamentoBean"%><jsp:useBean id="OrgList" class="org.aspcfs.modules.allevamenti.base.OrganizationList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SpecieList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>

<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Allevamenti.do"><dhv:label name="allevamenti.allevamenti">Accounts</dhv:label></a> > 
<dhv:label name="allevamenti.SearchResults">Search Results</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>

<dhv:evaluate if="<%= (User.getRoleType() > 0) %>" >
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><dhv:label name="allevamenti.manage">Select an account to manage.</dhv:label></td>
  </tr>
</table>
</dhv:evaluate>


<table cellpadding="8" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
      <th >
          <strong>Codice Azienda</strong>
		</th>
		 <th >
          <strong>Denominazione</strong>
		</th>
		 
		 <th >
          <strong>Specie</strong>
		</th>
		<th >
          <strong>Stato</strong>
		</th>
		<th >
          <strong>Proprietario</strong>
		</th>
		<th >
          <strong>Orientamento produttivo</strong>
		</th>
		<th >
          <strong></strong>
		</th>
		
  </tr>

<%
ArrayList<InfoAllevamentoBean> lista = (ArrayList<InfoAllevamentoBean>)request.getAttribute("ListaAllevamenti");
for (InfoAllevamentoBean all : lista)
{ 
	if(all.getErrore()!=null && all.getErrore().contains("Remote"))
	{
		%>
		<tr>
	    <td colspan="6"><font color="red"><%="I Servizi BDN al momento non sono disponibili" %></font></td></tr>
	    
	    <%
	}
	else
	{
		if(all.getErrore()!=null && !all.getErrore().equals(""))
		{
			%>
			<tr>
		    <td colspan="6"><font color="red"><%="Errore generico nella chiamata ai servizi BDN: " + all.getErrore() %></font></td></tr>
		    
		    <%
		}
		else
		{
			
	%>
	<tr >
    <td><%=all.getCodice_azienda() %></td>
	<td><%=all.getDenominazione() %></td>
	<td>
	<% int idSpecie = -1;
	try { idSpecie = Integer.parseInt(all.getSpecie_allevata()); } catch (Exception e) {}%>
	<%= (idSpecie > 0) ? SpecieList.getSelectedValue(idSpecie) : all.getSpecie_allevata() %></td>
	<td><%=(all.getData_fine_attivita()!=null ) ? "<font color = 'red'>CESSATO IN DATA</font> "+toDateasStringFromString(all.getData_fine_attivita()) :"<font color ='green'>ATTIVO</font>"  %></td>
	<td><%=all.getNome_proprietario() %></td>
	<td><%=all.getOrientamento_prod() %></td>	
<td>

<%
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
%>
<form method="post" onSubmit="loadModalWindow();" action = "Allevamenti.do?command=ImportGisa">
<input type = "hidden" name = "codAzienda" value = "<%=all.getCodice_azienda() %>"/>
<input type = "hidden" name = "idAllevamento" value = "<%=all.getId_allevamento() %>"/>
<input type = "hidden" name = "denominazione" value = "<%=all.getDenominazione().replace("\"", "&quot;") %>"/>
<input type = "hidden" name = "specie" value = "<%= (all.getCodice_specie()!=null && all.getCodice_specie().equals("PES")) ? "0160" : all.getCodice_specie()%>"/>
<input type = "hidden" name = "idFiscale" value = "<%= all.getCod_fiscale()%>"/> 
<input type = "hidden" name = "codiceOrientamentoProd" value = "<%= all.getFlag_carne_latte()%>"/>
<input type = "hidden" name = "descrizioneOrientamentoProd" value = "<%= all.getOrientamento_prod()%>"/> 
<input type = "hidden" name = "stato" value = "<%= (all.getData_fine_attivita()!=null ) ? "cessato" : "attivo"%>"/> 
<input type = "hidden" name = "dataInizioAttivita" value = "<%= sdf2.format(new Date(sdf.parse(all.getData_inizio_attivita()).getTime())) %>"/> 
<input type = "hidden" name = "dataFineAttivita" value = "<%=(all.getData_fine_attivita()!=null)?(sdf2.format(new Date(sdf.parse(all.getData_fine_attivita()).getTime()))):("") %>"/>
<input type = "hidden" name = "orientamento_prod" value = "<%=all.getOrientamento_prod()%>"/>
<input type = "hidden" name = "numeroCapi" value = "<%=all.getNum_capi_totali()%>"/>
<input type = "hidden" name = "indirizzoLegale" value = "<%=all.getIndirizzo()%>"/>
<input type = "hidden" name = "comuneLegale" value = "<%=all.getComune()%>"/>
<input type = "hidden" name = codiceFiscaleProp value = "<%=all.getCodFiscaleProp() %>"/>
<input type = "hidden" name = "codiceFiscaleDet" value = "<%=all.getCod_fiscale_det()%>"/>
<input type = "hidden" name = "tipologia_strutt" value = "<%=all.getTipoProduzione()%>"/>




<%
if(all.getCodice_specie()!=null && all.getCodice_specie().equals("0130")){ %>
<input type = "button" onclick="alert('Gli allevamenti di api non sono importabili tramite questa funzione. Gli inserimenti vanno fatti usando la banca dati apistica regionale.')" value = "Importa Gisa" > 

<%}
else { %>
<!-- <input type = "button" style="background:grey !important" disabled value = "Non importabile" >  -->
<input type = "submit" value = "Importa Gisa" > 

<%} %>

</form>

</td>
     </tr>
	
	
	<%
	}}
}


%>
</table>