<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%--Pagina JSP creata da Francesco --%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.base.*,org.aspcfs.utils.web.*" %>
<%@ page import="org.aspcfs.utils.*" %>
<jsp:useBean id="BaseList" class="org.aspcfs.utils.web.CustomLookupList" scope="request"/>
<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="LookupSelectorInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<link type="text/css" href="css/ui.all.css" rel="stylesheet" />
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script type="text/javascript">
function checkFiltro() {

    elementListView.submit();  

}
function clearR(){
	document.elementListView.searchcodeidAsl.value = "-1";
	document.elementListView.ragioneSociale.value = "";
}

</script>
<%@ include file="initPage.jsp" %>


<body style="overflow-y: scroll;" >
<%-- Trails --%>

<% 

String inRegione = (String)request.getAttribute("inRegione"); 
String tipo = (String)request.getParameter("tipo");
String indice = (String)request.getParameter("indiceDestinatario");

if(inRegione.equalsIgnoreCase("si"))
{
%>
<center><strong>SELEZIONA UNA IMPRESA COME DESTINATARIA DELLE CARNI</strong></center>
<%}
else
{
%>
<center><strong>SELEZIONA UN ESERCENTE COME DESTINATARIO DELLE CARNI</strong></center>
<%} %>
<%-- End Trails --%>
<br/>
<br/>
<form name="elementListView" method="post" action="LookupSelector.do?command=PopupSelectorDestinazioneCarni&indiceDestinatario=<%= indice%>" >
<input type = "hidden" name = "inRegione" value = "<%=inRegione %>">
<input type = "hidden" name = "tipo" value = "<%= tipo %>">
<input type = "hidden" name = "indice" value = "<%= indice %>">

<br />
<table width="100%" border="0">
  <tr >
    <td>
<b>ASL&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  : </b> 
      <%
if(inRegione.equalsIgnoreCase("si"))
{
	int idAsl =-1;
	if(request.getAttribute("idAsl")!=null)
	{
		idAsl = Integer.parseInt((String)(request.getAttribute("idAsl")));
	}
	
	
%>
<%=SiteIdList.getHtmlSelect("searchcodeidAsl",BaseList.getIdAsl()) %>
<%}else
{
	%>
	<%=SiteIdList.getSelectedValue(16)%>
	<input type = "hidden" name ="searchcodeidAsl" value = "16">
 <%} %>     
    </td>
    
  </tr>
  <tr >
  <td>
  <b>Ragione Sociale : </b> 
  <input type = "text" name = "ragioneSociale" value = "<%=BaseList.getRagioneSociale() %>">
  </td>
  </tr>
  
  <tr >
    <td><input type="button" value="<dhv:label name="button.search">Search</dhv:label>" onClick="checkFiltro();">
    <input type="button" value="Pulisci" onClick="clearR()">
   
  </td></tr>
</table>

<br><br><br>

<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="SearchOrgListInfo"/>
<% int columnCount = 0; %>


<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th width="20%">
     Ragione Sociale
    </th>
    <th width="10">
      Partita Iva
    </th>
  
    <th width="30%">
      Indirizzo Sede Operativa
    </th>
    <th width="10%">
      Asl
    </th>
  </tr>

	 
<%
org.aspcfs.modules.macellazioninew.utils.ConfigTipo configTipo = null;
org.aspcfs.modules.macellazioninewopu.utils.ConfigTipo configTipoOpu = null;
org.aspcfs.modules.macellazioninewsintesis.utils.ConfigTipo configTipoSintesis = null;

  Iterator j = BaseList.iterator();
  if ( j.hasNext() ) {
    int rowid = 0;
    while (j.hasNext()) {
      rowid = (rowid != 1?1:2);
      CustomLookupElement thisElt = (CustomLookupElement)j.next();
     
    
      String ragioneSociale = thisElt.getValue("name");
      String citta = thisElt.getValue("city");
      String indirizzo = thisElt.getValue("addrline1");
      String prov = thisElt.getValue("state");
      String zip = thisElt.getValue("postalcode");
      String orgId = thisElt.getValue("org_id");
      String partitaIva = thisElt.getValue("partita_iva");

      String aslDescr = thisElt.getValue("asldescr");
      
      
      //int code = codeString.startsWith("--") ? -1 : Integer.parseInt(codeString);
  %>
  <tr class="row<%= rowid %>">
    <td valign="center">
<%
configTipo = null;
configTipoOpu = null;
	try {
		configTipo = (org.aspcfs.modules.macellazioninew.utils.ConfigTipo)request.getSession().getAttribute("configTipo");
	}
	catch (Exception e){
		
		try {
			configTipoOpu = (org.aspcfs.modules.macellazioninewopu.utils.ConfigTipo)request.getSession().getAttribute("configTipo");
		}
		catch (Exception e2){
			configTipoSintesis = (org.aspcfs.modules.macellazioninewsintesis.utils.ConfigTipo)request.getSession().getAttribute("configTipo");
	}
		
		
}
	boolean hasNumCapi = false;	
	if((configTipo!=null && configTipo.hasDestinatariNumCapi()) || (configTipoOpu!=null && configTipoOpu.hasDestinatariNumCapi() )  || (configTipoSintesis!=null && configTipoSintesis.hasDestinatariNumCapi() ))
		hasNumCapi = true;	
%>
      <a href="javascript:setDestinatarioField('<%=request.getParameter("indiceDestinatario") %>', '<%=orgId %>','<%= toHtml(ragioneSociale).replaceAll("'"," ").toUpperCase() %>' , '<%= tipo %>',<%=hasNumCapi%>)">
        <%= toHtml(ragioneSociale) %>
      </a>
    </td>
    <td valign="center">
     <%= toHtml(partitaIva) %>
    </td>
    
    <td valign="center">
     <%= toHtml(citta)+"<br>"+toHtml(indirizzo)+"<br>"+toHtml(prov)+","+toHtml(zip) %>
    </td>
    <td valign="center">
     <%= toHtml(aslDescr)%>
    </td>
    
  </tr>
<%} }
  
 
else {%>
  <tr class="containerBody">
    <td colspan="<%= SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId").equals(String.valueOf(Constants.INVALID_SITE))?columnCount+1:columnCount %>">
      <dhv:label name="requestor.search.notFound">No accounts found with the specified search parameters.</dhv:label><br />
    </td>
  </tr>
<%}%>
</table>
<br />
<dhv:pagedListControl object="SearchOrgListInfo" tdClass="row1"/>
</form></body>
<script type="text/javascript" >
function clear()
{
	alert('pulisci form');
//	document.elementListView.searchcodeidAsl.value = "-1";-->
//	document.elementListView.ragioneSociale.value = "";
}
</script>

