<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.canili.base.*,org.aspcfs.modules.vigilanza.base.Ticket,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.canili.base.Organization" scope="request"/>
<jsp:useBean id="TipoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AuditTipo" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="EsitoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoAudit" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoIspezione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TicList" class="org.aspcfs.modules.vigilanza.base.TicketList" scope="request"/>
<jsp:useBean id="AccountTicketInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@page import="org.aspcfs.modules.canipadronali.base.Cane"%>
<%@page import="org.aspcfs.modules.vigilanza.base.Ticket"%>
<%@page import="org.aspcfs.modules.canipadronali.base.Proprietario"%>
<%@page import="org.aspcfs.modules.util.imports.ApplicationProperties"%>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>


<script>
function openPopupAggiungiCane(link)
{

	window.open(link,null,
	'height=800px,width=680px,toolbar=no,directories=no,status=no,scrollbars=no,resizable=no');

}




</script>
<%@ include file="../initPage.jsp" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<script>
function openPopupAggiungiCane(link,username)
{


	window.open(link+'&username='+username,null,
	'height=800px,width=680px,toolbar=no,directories=no,status=no,scrollbars=no,resizable=no');
	
	

}



</script>
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="CaniPadronali.do?command=SearchForm">Anagrafica Cani di proprieta</a> > 
 Risultati Ricerca
   
</td>
</tr>
</table>

<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="AccountTicketInfo"/>

<table class="details" width="100%">
<tr>
<th>Id controllo</th>
<th>Data Controllo</th>
<th>Tipo Controllo</th>
<th>Proprietario</th>
<th>Conduttore</th>
<th>ASL</th>
</tr>

<%
Iterator j = TicList.iterator();
if ( j.hasNext() ) {
int rowid = 0;
int i = 0;
while (j.hasNext()) {
i++;
rowid = (rowid != 1 ? 1 : 2);
Ticket cu = (Ticket)j.next();
Proprietario prop = cu.getProprietario();

	%>
	 <tr class="row<%= rowid %>">
	<td><a href = "CaniPadronaliVigilanza.do?command=TicketDetails&id=<%=cu.getId()%>&orgId=<%=cu.getOrgId()%>&assetId=<%=cu.getAssetId() %>"><%=cu.getPaddedId() %></a></td>
	<td>
	<%SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	%>
	<%=(cu.getDataInizioControllo()!=null) ? sdf.format(cu.getDataInizioControllo()) : ""%></td>
	
		<td valign="top"><%= TipoCampione.getSelectedValue(cu.getTipoCampione()) %>
		
		</td>
	
	<td><%=(prop != null) ? toHtml(prop.getRagioneSociale()) : "" %></td>
	<td><%=toHtml(cu.getNome_conducente()) %></td>
	<td><%=SiteIdList.getSelectedValue(cu.getSiteId()) %></td>
	</tr>
	<tr class="row<%= rowid %>">
      <td colspan="7" valign="top">
        <%
          if (1==1) {
            Iterator files = cu.getFiles().iterator();
            while (files.hasNext()) {
              FileItem thisFile = (FileItem)files.next();
              if (".wav".equalsIgnoreCase(thisFile.getExtension())) {
        %>
          <a href="AccountVigilanzaDocuments.do?command=Download&stream=true&tId=<%= cu.getId() %>&fid=<%= thisFile.getId() %>"><img src="images/file-audio.gif" border="0" align="absbottom"></a>
        <%
              }
            }
          }
        %>
        <%= toHtml(cu.getProblemHeader()) %>&nbsp;
        <% if (cu.getClosed() == null) { %>
          [<font color="green"><dhv:label name="project.open.lowercase1">open</dhv:label></font>]
        <%} else {%>
          [<font color="red"><dhv:label name="project.closed.lowercase1">closed</dhv:label></font>]
        <%}%>
      </td>
    </tr>
	<%
}
}
else
{
UserBean utente = (UserBean)session.getAttribute("User");
%>

<br>
<tr>
<td colspan="7">
Nessun Controllo Presente</td></tr>
<%}
	
%>


</table>
<dhv:pagedListControl object="AccountTicketInfo" tdClass="row1"/>

