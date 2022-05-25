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
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="DisplayFieldId" class="java.lang.String" scope="request"/>
<jsp:useBean id="DisplayFieldId2" class="java.lang.String" scope="request"/>
<jsp:useBean id="Table" class="java.lang.String" scope="request"/>
<jsp:useBean id="FiltroDesc" class="java.lang.String" scope="request"/>

<jsp:useBean id="IstatList" class="org.aspcfs.utils.web.LookupListList" scope="request"/>



<jsp:useBean id="LookupSelectorInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script>


function leftTrim(stringa) {
  while (stringa.substring(0,1) == ' ') {
    stringa = stringa.substring(1, stringa.length);
  }
  return stringa;
}

function rightTrim(stringa) {
  while (stringa.substring(stringa.length-1, stringa.length) == ' ') {
    stringa = stringa.substring(0,stringa.length-1);
  }
  return stringa;
}

</script>
<%@ include file="initPage.jsp" %>
<body >
<form name="elementListView" method="post" action="#">
<br />
<table width="100%" border="0">
  
</table>
<div style="height: 350px; overflow: auto;">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th width="20%">
      <dhv:label name="contact.option">Option</dhv:label>
    </th>
    <th width="80%">
      <dhv:label name="contact.Descrizione">Descrizione</dhv:label>
    </th>
  </tr>
  <%
  Iterator j = BaseList.iterator();
  if ( j.hasNext() ) {
    int rowid = 0;
    while (j.hasNext()) {
      rowid = (rowid != 1?1:2);
      CustomLookupElement thisElt = (CustomLookupElement)j.next();
      boolean enabled = thisElt.getValue("enabled") == "true" ? true : false;
      boolean defaultItem = thisElt.getValue("default_item") == "true" ? true : false;
      String description = thisElt.getValue("name");
      String shortDescription = thisElt.getValue("alert");
      String codeString = thisElt.getValue("code");
      //int code = codeString.startsWith("--") ? -1 : Integer.parseInt(codeString);
      
  %>
  <tr class="row<%= rowid %>">
    <td valign="center">
    
    
    
    
<%
String cod1="";
String cod2="";
String cod3="";
String cod4="";
String cod5="";
String cod6="";
String cod7="";
String cod8="";
String cod9="";
String cod10="";

if(thisElt.getValue("cod1")!=null)
	cod1=thisElt.getValue("cod1").replaceAll("'"," ");


if(thisElt.getValue("cod2")!=null)
	cod2=thisElt.getValue("cod2").replaceAll("'"," ");

if(thisElt.getValue("cod3")!=null)
	cod3=thisElt.getValue("cod3").replaceAll("'"," ");

if(thisElt.getValue("cod4")!=null)
	cod4=thisElt.getValue("cod4").replaceAll("'"," ");

if(thisElt.getValue("cod5")!=null)
	cod5=thisElt.getValue("cod5").replaceAll("'"," ");

if(thisElt.getValue("cod6")!=null)
	cod6=thisElt.getValue("cod6").replaceAll("'"," ");

if(thisElt.getValue("cod7")!=null)
	cod7=thisElt.getValue("cod7").replaceAll("'"," ");

if(thisElt.getValue("cod8")!=null)
	cod8=thisElt.getValue("cod8").replaceAll("'"," ");

if(thisElt.getValue("cod9")!=null)
	cod9=thisElt.getValue("cod9").replaceAll("'"," ");

if(thisElt.getValue("cod10")!=null)
	cod10=thisElt.getValue("cod10").replaceAll("'"," ");


%>
    
      <a href="javascript:setParentValue2_dia('<%=thisElt.getValue("name").replaceAll("'","") %>','<%=thisElt.getValue("duns_type") %>','<%=thisElt.getValue("site_id") %>','<%=thisElt.getValue("partita_iva") %>','<%=thisElt.getValue("codice_fiscale") %>','<%=thisElt.getValue("cf_correntista") %>','<%=thisElt.getValue("alert") %>','<%=thisElt.getValue("codice1") %>','<%=cod1 %>','<%=thisElt.getValue("codice2") %>','<%=cod2 %>','<%=thisElt.getValue("codice3") %>','<%=cod3 %>','<%=thisElt.getValue("codice4") %>','<%=cod4 %>','<%=(thisElt.getValue("codice5")!=null)?thisElt.getValue("codice5").replaceAll("'","\\'"):"" %>','<%=cod5 %>','<%=thisElt.getValue("codice6") %>','<%=cod6 %>','<%=thisElt.getValue("codice7") %>','<%=cod7 %>','<%=thisElt.getValue("codice8") %>','<%=cod8 %>','<%=thisElt.getValue("codice9") %>','<%=cod9 %>','<%=thisElt.getValue("codice10") %>','<%=cod10 %>','<%=thisElt.getValue("tipo_dest") %>','<%=thisElt.getValue("date1") %>','<%=thisElt.getValue("stage_id") %>','<%=thisElt.getValue("titolo_rappresentante") %>','<%=thisElt.getValue("codice_fiscale_rappresentante") %>','<%=thisElt.getValue("nome_rappresentante") %>','<%=thisElt.getValue("cognome_rappresentante") %>','<%=thisElt.getValue("data_nascita_rappresentante") %>','<%=thisElt.getValue("luogo_nascita_rappresentante") %>','<%=thisElt.getValue("email_rappresentante") %>','<%=thisElt.getValue("telefono_rappresentante") %>','<%=thisElt.getValue("fax") %>' ,'<%=thisElt.getValue("sedeLegalecity") %>','<%=thisElt.getValue("sedeLegaleaddress1") %>','<%=thisElt.getValue("sedeLegaleaddress2") %>','<%=thisElt.getValue("sedeLegalepostalcode") %>','<%=thisElt.getValue("sedeLegalestate") %>','<%=thisElt.getValue("sedeOperativacity") %>','<%=thisElt.getValue("sedeOperativaaddress1") %>','<%=thisElt.getValue("sedeOperativapostalcode") %>','<%=thisElt.getValue("sedeOperativastate") %>','<%=thisElt.getValue("sedeOperativalatitudine") %>','<%=thisElt.getValue("sedeOperativalongitudine") %>','<%=thisElt.getValue("mobilecity") %>','<%=thisElt.getValue("mobileaddress1") %>','<%=thisElt.getValue("mobilepostalcode") %>','<%=thisElt.getValue("mobilestate") %>','<%=thisElt.getValue("mobilelatitudine") %>','<%=thisElt.getValue("mobilelongitudine") %>','<%=thisElt.getValue("localicity") %>','<%=thisElt.getValue("localiaddress1") %>','<%=thisElt.getValue("localipostalcode") %>','<%=thisElt.getValue("localistate") %>','<%=thisElt.getValue("localilatitudine") %>','<%=thisElt.getValue("localilongitudine") %>','<%=thisElt.getValue("tipologia_strutt") %>','<%=thisElt.getValue("nome_correntista") %>','<%=thisElt.getValue("conto_corrente") %>'  );">
        <%= toHtml(description) %>
      </a>
    </td>
    <td valign="center">
     <%= toHtml(shortDescription) %>
    </td>
  </tr>
<%} }
  
  else {%>
      <tr class="containerBody">
        <td colspan="2">
          <dhv:label name="calendar.noOptionsAvailable.text">No options are available.</dhv:label>
        </td>
      </tr>
<%}%>
</table>
</div>
<input type="hidden" name="rowcount" value="0">
<input type="hidden" name="displayFieldId" value="<%= DisplayFieldId %>">
<input type="hidden" name="displayFieldId2" value="<%= DisplayFieldId2 %>">
<input type="hidden" name="table" value="<%= Table %>">
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.close()">
<br />
</form>
</body>
