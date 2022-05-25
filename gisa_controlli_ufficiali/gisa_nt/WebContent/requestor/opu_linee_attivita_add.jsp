<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>


<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ taglib uri="/WEB-INF/taglib/systemcontext-taglib.tld" prefix="sc"%>

<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*"%>
<%@page import="org.aspcfs.modules.opu.base.Stabilimento"%>
<%@page import="org.aspcfs.modules.opu.base.Operatore"%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.aspcfs.modules.opu.base.Stabilimento"%>

<jsp:useBean id="Operatore" class="org.aspcfs.modules.opu.base.Operatore" scope="request" />
<jsp:useBean id="newStabilimento" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request" />
<jsp:useBean id="indirizzoAdded" class="org.aspcfs.modules.opu.base.Indirizzo" scope="request" />
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="IterList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="Address" class="org.aspcfs.modules.accounts.base.OrganizationAddress" scope="request" />
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="provinciaAsl"  class="org.aspcfs.modules.accounts.base.Provincia" scope="request" />
<jsp:useBean id="ServizioCompetente" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="LookupTipoAttivita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Carattere" class="org.aspcfs.utils.web.LookupList" scope="request"/>




<script language="JavaScript" TYPE="text/javascript"SRC="javascript/gestoreCodiceFiscale.js"></script>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/opu.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</script>
<a href="javascript:popUp('LineaProduttivaAction.do?command=Search&popup=true&tipoSelezione=multipla&idNorma='+document.getElementById('idNorma').value);">
<dhv:label name="opu.stabilimento.linea_produttiva.selezione"></dhv:label>
</a>
<table cellpadding="4" cellspacing="0" border="0" width="100%"	class="details">
	<tr>
		<th colspan="6">
			<strong><dhv:label name="opu.stabilimento.linea_produttiva"></dhv:label></strong>
		</th>
	</tr>
	
	<tr>
	<th style="background-color: rgb(204, 255, 153);"><strong>Norma</strong>
		</th>
		<th style="background-color: rgb(204, 255, 153);"><strong>Macroarea</strong>
		</th>
		<th style="background-color: rgb(204, 255, 153);"><strong>Aggregazione</strong>
		</th>
		<th style="background-color: rgb(204, 255, 153);"><strong>Attivita</strong>
		</th>
		
		<th  style="background-color: rgb(204, 255, 153);"><strong>Principale</strong>
	</tr>
	
	<dhv:evaluate if="<%= (newStabilimento.getListaLineeProduttive().size()>0) %>">
	
	<% 
		Iterator<LineaProduttiva> itLplist = newStabilimento.getListaLineeProduttive().iterator() ;
		int indice = 1 ;
		while(itLplist.hasNext())
		{
			LineaProduttiva lp = itLplist.next();
			if (lp.getId()>0)
			{
	%>
		<input type="hidden" name="idLineaProduttiva"
			id="idLineaProduttiva<%=indice %>"
			value="<%=lp.getId() %>">
	<tr>
	<td><%=lp.getNorma() %></td>
	<td><%=lp.getMacrocategoria() %></td>
	<td><%=lp.getCategoria() %></td>
	<td><%=lp.getAttivita() %></td>
	
	<td style="display:none">
		<input readonly type="text" id="dataInizio<%=lp.getId() %>" name="dataInizio<%=lp.getId() %>" size="10" value = "<%=lp.getDataInizioasString() %>" />
		<a href="#" onClick="cal19.select(document.forms[0].dataInizio<%=lp.getId() %>,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
        
	</td>
	<td style="display:none">
	<input readonly type="text" id="dataFine<%=lp.getId() %>" name="dataFine<%=lp.getId() %>" size="10" value = "<%=lp.getDataFineasString() %>"/>
		<a href="#" onClick="cal19.select(document.forms[0].dataFine<%=lp.getId()%>,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
	</td>
	<td style="display:none">Attivo<input type = "hidden" name = "stato<%=lp.getId() %>" value = "0"></td>
	
	<td style="display:none"><%=Carattere.getHtmlSelect("tipo_attivita_produttiva"+lp.getId(),lp.getTipoAttivitaProduttiva()) %></td>
	<td><input type = "radio" name = "principale" value = "<%=lp.getId() %>" <%=(indice==1) ? "checked" : "" %>/></td>
	</tr>
			<%
		indice ++ ;
			}
		} %>
		
	</dhv:evaluate>
	<input type = "hidden" name = "numLineeProduttive" value = "<%=newStabilimento.getListaLineeProduttive().size()%>">
		<input type="hidden" name="dataInizio" id="dataInizio" value="">
		<input type="hidden" name="dataFine" id="dataFine" value="">
		<input type="hidden" name="stato" id="stato" value="">
		<input type="hidden" name="idLineaProduttiva" id="idLineaProduttiva" value="">
		<input type = hidden name = "flagDia" value = "<%=newStabilimento.isFlagDia() %>"/>
	
</table>