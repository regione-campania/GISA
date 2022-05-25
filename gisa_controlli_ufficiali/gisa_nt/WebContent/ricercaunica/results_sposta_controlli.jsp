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
<%@page import="com.itextpdf.text.log.SysoLogger"%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>
<%@page import="org.aspcfs.modules.opu.base.Stabilimento"%>
<%@page import="org.aspcfs.modules.troubletickets.base.Ticket"%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.ricercaunica.base.*, org.aspcfs.modules.base.*" %>
<jsp:useBean id="StabilimentiList" class="org.aspcfs.modules.ricercaunica.base.RicercaList" scope="request"/>
<jsp:useBean id="SearchOpuListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="tipoOperatore" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="AnagraficaPartenza" class="org.aspcfs.modules.ricercaunica.base.RicercaOpu" scope = "request"></jsp:useBean>


<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>

<script>



</script>



<%
String action = "";
Ticket urlAnagrafica = new Ticket();
if(StabilimentiList.size()>0)
{
	urlAnagrafica.setTipologia_operatore(((RicercaOpu)StabilimentiList.get(0)).getTipologia());
 action = urlAnagrafica.getURlDettaglioanagrafica()+".do?command=Details&"+((RicercaOpu)StabilimentiList.get(0)).getRiferimentoIdNome()+"="+((RicercaOpu)StabilimentiList.get(0)).getRiferimentoId();
}
boolean presenteInOpu = false;
Iterator<RicercaOpu> itAnagrafiche = StabilimentiList.iterator();
while (itAnagrafiche.hasNext())
{
	RicercaOpu thisAnagrafica = itAnagrafiche.next();
	if(thisAnagrafica.getTipologia()==999)
	{
		presenteInOpu=true;
	break;
	}
}

%>


<br />
<%
if(presenteInOpu==true)
{
	%>
	<div align="left">
		<b>Mostra Altri Risultati di Ricerca</b> <input type="checkbox" onclick="mostraRisultatiInVecchiaAnagrafica(this)">
	</div>
	
	<%
}

%>

<table cellpadding="8" cellspacing="0" border="0" width="100%" class="pagedList">
 <tr>
   <th colspan="8">
          <strong><dhv:label name="">RISULTATI RICERCA DESTINAZIONE CONVERGENZA</dhv:label></strong>
        </th> 
	</tr>
	<tr>
	
	<th>DITTA/DENOMINAZIONE/RAGIONE SOCIALE</th>
	<th>PARTITA IVA</th>
	<th>COMUNE SEDE PRODUTTIVA</th>
	<th>INDIRIZZO SEDE PRODUTTIVA</th>
	<th>CODICE FISCALE PROPRIETARIO</th>
	<th>N. REG</th>
	<th>STATO</th>
	<th>#</th>
	
	</tr>
	
        <%
        int rowid = 0;
         itAnagrafiche = StabilimentiList.iterator();
        while (itAnagrafiche.hasNext())
        {
        	RicercaOpu thisAnagrafica = itAnagrafiche.next();
        	
        	
        	
        	if(thisAnagrafica.getRiferimentoId()!=AnagraficaPartenza.getRiferimentoId() || !thisAnagrafica.getRiferimentoIdNomeCol().equals(AnagraficaPartenza.getRiferimentoIdNomeCol())){
        		%>
        	<tr <% if(presenteInOpu==true && thisAnagrafica.getTipologia()!=999) {%> style="display: none" <%} %> <%if(thisAnagrafica.getTipologia()!=999) {%>class="noOpu"<%} %> >
        	<td><%=thisAnagrafica.getRagioneSociale() %></td>
        	<td><%=thisAnagrafica.getPartitaIva() %></td>
        	<td><%=thisAnagrafica.getComuneSedeProduttiva()%></td>
        	<td><%=thisAnagrafica.getIndirizzoSedeProduttiva() %></td>
        	<td><%=thisAnagrafica.getCodiceFiscaleSoggettoFisico() %></td>
        	<td><%=thisAnagrafica.getNumeroRegistrazione() %></td>
        	<td><%=thisAnagrafica.getStato() %></td>
        	<td>
        	<input type="button" id="" name=""  value="SELEZIONA" onclick="sceltaDestinazioneConvergenza(<%=AnagraficaPartenza.getRiferimentoId()%>,'<%=AnagraficaPartenza.getRiferimentoIdNomeCol()%>',<%=thisAnagrafica.getRiferimentoId()%>,'<%=thisAnagrafica.getRiferimentoIdNomeCol()%>',<%=AnagraficaPartenza.getId_tipo_attivita()%> , <%=thisAnagrafica.getId_tipo_attivita()%>)"/>
        	
        	</td>
        	
        	</tr>
        	
        	<%
        }}
        
        %>
        
        </table>
        
        

<input type = "button" value="ESCI" onclick="$('#dialogRICERCA').dialog('close');">

<div id="LoadingImage" style="display: none">
	Attendere Operazione in corso ..
<img src="images/ajax-loader.gif" />
</div>
