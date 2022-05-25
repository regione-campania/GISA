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
<jsp:useBean id="DisplayFieldId3" class="java.lang.String" scope="request"/>
<jsp:useBean id="DisplayFieldId2" class="java.lang.String" scope="request"/>
<jsp:useBean id="Table" class="java.lang.String" scope="request"/>
<jsp:useBean id="FiltroDesc" class="java.lang.String" scope="request"/>
<jsp:useBean id="LookupSelectorInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script>

function checkFiltro() {
  //cancello eventuali spazi all'inizio e alla fine del testo
  var desc = leftTrim(rightTrim(document.elementListView.filtroDesc.value));
  document.elementListView.filtroDesc.value = desc;
  if (desc != ""){
    elementListView.submit();  
  } else {
	  alert('Inserire la descrizione dell attività da ricercare');
    document.elementListView.filtroDesc.focus();  
  }
}

function clearRicerca() {
	
	  //cancello eventuali spazi all'inizio e alla fine del testo
	  document.elementListView.filtroDesc.value="";
	  
	  document.elementListView.submit();  
	 }

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
<% 
String prev="00";
String att = "01";

%>
<%@ include file="initPage.jsp" %>
<body onload="javascript:document.elementListView.filtroDesc.focus()">

<form name="elementListView" method="post" action="LookupSelector.do?command=PopupSelectorCustomImprese">

<%	

if (request.getAttribute("orgId")!=null)
	
{
%>
<input type= "hidden" name = "orgId" value = "<%=request.getAttribute("orgId") %>">

<%	
}
%>
<br />
<table width="100%" border="0">
  <tr>
    <td>
      <b>Codice Istat</b> <input type="text" size="20" name="filtroDesc" value="<%= FiltroDesc %>"/> 
      <input type="button" value="<dhv:label name="button.search">Search</dhv:label>" onClick="checkFiltro();">
      <input type="button" value="Azzera Ricerca" onClick="javascript:clearRicerca();">
      
      
    </td>
  </tr>
</table>
<div style="height: 350px; overflow: auto;">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
 
  <tr style="backgroundcolor:gray">
      <th width="25%">
      <dhv:label name="">Codice</dhv:label>
    </th>
    <th width="25%">
      <dhv:label name="contact.Descrizione">Descrizione</dhv:label>
    </th>
    <th width="25%">
      <dhv:label name="contact.Descrizione">Macroarea</dhv:label>
    </th>
    <th width="25%">
      <dhv:label name="contact.Descrizione">Aggregazione</dhv:label>
    </th>
    <th width="25%">
      <dhv:label name="contact.Descrizione">Attivita'</dhv:label>
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
      //String description = thisElt.getValue("description");
      String description = thisElt.getValue("codice_ateco");
      //String shortDescription = thisElt.getValue("short_description");
      String shortDescription = thisElt.getValue("descrizione_ateco");
      //String codeString = thisElt.getValue("code");
      
      //R.M
      String macroarea = thisElt.getValue("macroarea");
      String aggregazione = thisElt.getValue("aggregazione");
      String attivita = thisElt.getValue("attivita");
      String id_attivita = thisElt.getValue("id_attivita");
      //R.M
      
      String codeString = thisElt.getValue("id_ateco");
      int code = codeString.startsWith("--") ? -1 : Integer.parseInt(codeString);
  %>
  <%
  att = description.substring(0,2);
  if(!prev.equals(att)){
	  if(att.equals("01")){
  %>
  <tr>
	 <th id="tb_label">
	   <dhv:label name=""> A - B</dhv:label>
 	</th>
  	<th id="tb_label">
      <dhv:label name="">AGRICOLTURA, SILVICOLTURA E PESCA </dhv:label>
    </th>
   	<th id="tb_label">
      <dhv:label name="">MACROAREA</dhv:label>
    </th>
    <th id="tb_label">
      <dhv:label name="">AGGREGAZIONE</dhv:label>
    </th>
    <th id="tb_label">
      <dhv:label name="">ATTIVITA'</dhv:label>
    </th>
    </tr>
    <tr>
	 <th id="tb_label2">
	   <dhv:label name=""> 01</dhv:label>
 	</th>
  	<th id="tb_label2">
      <dhv:label name="">COLTIVAZIONI AGRICOLE E PRODUZIONE DI PRODOTTI ANIMALI, CACCIA E SERVIZI CONNESSI</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">MACROAREA</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">AGGREGAZIONE</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">ATTIVITA'</dhv:label>
    </th>
    </tr>
 <%}else if(att.equals("02")){ %>
 <tr>
	 <th id="tb_label2">
	   <dhv:label name=""> 02</dhv:label>
 	</th>
  	<th id="tb_label2">
      <dhv:label name="">SILVICOLTURA ED UTILIZZO DI AREE FORESTALI</dhv:label>
    </th>
   <th id="tb_label2">
      <dhv:label name="">MACROAREA</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">AGGREGAZIONE</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">ATTIVITA'</dhv:label>
    </th>
    </tr>
 <%}else if(att.equals("03")){%>
 <tr>
	 <th id="tb_label2">
	   <dhv:label name=""> 03</dhv:label>
 	</th>
  	<th id="tb_label2">
      <dhv:label name=""></dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">MACROAREA</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">AGGREGAZIONE</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">ATTIVITA'</dhv:label>
    </th>
    </tr>
  <%}else if(att.equals("10")){%>
	  <tr>
	 <th id="tb_label">
	   <dhv:label name=""> C</dhv:label>
 	</th>
  	<th id="tb_label">
      <dhv:label name="">ATTIVITÀ MANIFATTURIERE</dhv:label>
    </th>
    <th id="tb_label">
      <dhv:label name="">MACROAREA</dhv:label>
    </th>
    <th id="tb_label">
      <dhv:label name="">AGGREGAZIONE</dhv:label>
    </th>
    <th id="tb_label">
      <dhv:label name="">ATTIVITA'</dhv:label>
    </th>
    </tr>
    <tr>
	 <th id="tb_label2">
	   <dhv:label name=""> 10</dhv:label>
 	</th>
  	<th id="tb_label2">
      <dhv:label name="">INDUSTRIE ALIMENTARI</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">MACROAREA</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">AGGREGAZIONE</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">ATTIVITA'</dhv:label>
    </th>
    </tr>
	 <%}else if(att.equals("11")){%>
	 <tr>
	 <th id="tb_label2">
	   <dhv:label name=""> 11</dhv:label>
 	</th>
  	<th id="tb_label2">
      <dhv:label name="">INDUSTRIA DELLE BEVANDE</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">MACROAREA</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">AGGREGAZIONE</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">ATTIVITA'</dhv:label>
    </th>
    </tr>
 <%}else if(att.equals("36")){%>
	<tr>
	 <th id="tb_label">
	   <dhv:label name="">E</dhv:label>
 	</th>
  	<th id="tb_label">
      <dhv:label name="">FORNITURA DI ACQUA, RETI FOGNARIE, ATTIVITÀ DI GESTIONE DEI RIFIUTI E RISANAMENTO</dhv:label>
    </th>
    <th id="tb_label">
      <dhv:label name="">MACROAREA</dhv:label>
    </th>
    <th id="tb_label">
      <dhv:label name="">AGGREGAZIONE</dhv:label>
    </th>
    <th id="tb_label">
      <dhv:label name="">ATTIVITA'</dhv:label>
    </th>
    </tr>
    <tr>
	 <th id="tb_label2">
	   <dhv:label name=""> 36</dhv:label>
 	</th>
  	<th id="tb_label2">
      <dhv:label name="">RACCOLTA, TRATTAMENTO E FORNITURA DI ACQUA</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">MACROAREA</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">AGGREGAZIONE</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">ATTIVITA'</dhv:label>
    </th>
    </tr> 
		 <%} else if(att.equals("46")){%>
			 <tr>
	 <th id="tb_label">
	   <dhv:label name="">46</dhv:label>
 	</th>
  	<th id="tb_label">
      <dhv:label name="">COMMERCIO ALL'INGROSSO (ESCLUSO QUELLO DI AUTOVEICOLI E DI MOTOCICLI)</dhv:label>
    </th>
    <th id="tb_label">
      <dhv:label name="">MACROAREA</dhv:label>
    </th>
    <th id="tb_label">
      <dhv:label name="">AGGREGAZIONE</dhv:label>
    </th>
    <th id="tb_label">
      <dhv:label name="">ATTIVITA'</dhv:label>
    </th>
    </tr>
    
		<%}else if(att.equals("47")){%>
			<tr>
	 <th id="tb_label">
	   <dhv:label name=""> 47</dhv:label>
 	</th>
  	<th id="tb_label">
      <dhv:label name="">COMMERCIO AL DETTAGLIO (ESCLUSO QUELLO DI AUTOVEICOLI E DI MOTOCICLI)</dhv:label>
    </th>
    <th id="tb_label">
      <dhv:label name="">MACROAREA</dhv:label>
    </th>
    <th id="tb_label">
      <dhv:label name="">AGGREGAZIONE</dhv:label>
    </th>
    <th id="tb_label">
      <dhv:label name="">ATTIVITA'</dhv:label>
    </th>
    </tr> 
		<%}else if(att.equals("49")){%>
			<tr>
	 <th id="tb_label">
	   <dhv:label name="">H</dhv:label>
 	</th>
  	<th id="tb_label">
      <dhv:label name="">TRASPORTO E MAGAZZINAGGIO</dhv:label>
    </th>
    <th id="tb_label">
      <dhv:label name="">MACROAREA</dhv:label>
    </th>
    <th id="tb_label">
      <dhv:label name="">AGGREGAZIONE</dhv:label>
    </th>
    <th id="tb_label">
      <dhv:label name="">ATTIVITA'</dhv:label>
    </th>
    </tr>
    <tr>
	 <th id="tb_label2">
	   <dhv:label name=""> 49</dhv:label>
 	</th>
  	<th id="tb_label2">
      <dhv:label name="">TRASPORTO TERRESTRE E TRASPORTO MEDIANTE CONDOTTE</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">MACROAREA</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">AGGREGAZIONE</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">ATTIVITA'</dhv:label>
    </th>
    </tr> 
		<%}else if(att.equals("50")){%>
			<tr>
	 <th id="tb_label2">
	   <dhv:label name=""> 50</dhv:label>
 	</th>
  	<th id="tb_label2">
      <dhv:label name="">TRASPORTO MARITTIMO E PER VIE D'ACQUA</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">MACROAREA</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">AGGREGAZIONE</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">ATTIVITA'</dhv:label>
    </th>
    </tr> 
		<%}else if(att.equals("51")){%>
			<tr>
	 <th id="tb_label2">
	   <dhv:label name=""> 51</dhv:label>
 	</th>
  	<th id="tb_label2">
      <dhv:label name="">TRASPORTO AEREO</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">MACROAREA</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">AGGREGAZIONE</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">ATTIVITA'</dhv:label>
    </th>
    </tr> 
		<%}else if(att.equals("52")){%>
			<tr>
	 <th id="tb_label2">
	   <dhv:label name=""> 52</dhv:label>
 	</th>
  	<th id="tb_label2">
      <dhv:label name="">MAGAZZINAGGIO E ATTIVITÀ DI SUPPORTO AI TRASPORTI</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">MACROAREA</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">AGGREGAZIONE</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">ATTIVITA'</dhv:label>
    </th>
    </tr> 
		<%}else if(att.equals("55")){%>
		<tr>
	 <th id="tb_label">
	   <dhv:label name="">I</dhv:label>
 	</th>
  	<th id="tb_label">
      <dhv:label name="">ATTIVITÀ DEI SERVIZI DI ALLOGGIO E DI RISTORAZIONE
</dhv:label>
    </th>
    <th id="tb_label">
      <dhv:label name="">MACROAREA</dhv:label>
    </th>
    <th id="tb_label">
      <dhv:label name="">AGGREGAZIONE</dhv:label>
    </th>
    <th id="tb_label">
      <dhv:label name="">ATTIVITA'</dhv:label>
    </th>
    </tr>
		<tr>
		 <th id="tb_label2">
		   <dhv:label name=""> 55</dhv:label>
	 	</th>
	  	<th id="tb_label2">
	      <dhv:label name=""></dhv:label>
	    </th>
	    <th id="tb_label2">
      <dhv:label name="">MACROAREA</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">AGGREGAZIONE</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">ATTIVITA'</dhv:label>
    </th>
	    </tr> 
			<%}else if(att.equals("56")){%>
			
				<tr>
				 <th id="tb_label2">
				   <dhv:label name=""> 56</dhv:label>
			 	</th>
			  	<th id="tb_label2">
			      <dhv:label name="">ATTIVITÀ DEI SERVIZI DI RISTORAZIONE</dhv:label>
			    </th>
			    <th id="tb_label2">
      <dhv:label name="">MACROAREA</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">AGGREGAZIONE</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">ATTIVITA'</dhv:label>
    </th>
			    </tr> 
			    <%}else if(att.equals("82")){%>
					<tr>
				 <th id="tb_label2">
				   <dhv:label name=""> 82</dhv:label>
			 	</th>
			  	<th id="tb_label2">
			      <dhv:label name=""></dhv:label>
			    </th>
			    <th id="tb_label2">
      <dhv:label name="">MACROAREA</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">AGGREGAZIONE</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">ATTIVITA'</dhv:label>
    </th>
			    </tr> 
					<%}else{%>
						<tr>
				 <th id="tb_label2">
				   <dhv:label name=""> 93</dhv:label>
			 	</th>
			  	<th id="tb_label2">
			      <dhv:label name=""></dhv:label>
			    </th>
			    <th id="tb_label2">
      <dhv:label name="">MACROAREA</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">AGGREGAZIONE</dhv:label>
    </th>
    <th id="tb_label2">
      <dhv:label name="">ATTIVITA'</dhv:label>
    </th>
			    </tr> 
					<%}
	  
	  prev = att;}%>
	 
 
  <tr class="row<%= rowid %>">
    <td valign="center">
      <a href="javascript:setParentValue2('<%= DisplayFieldId %>','<%= StringUtils.jsStringEscape(description) %>','<%= DisplayFieldId2 %>','<%= StringUtils.jsStringEscape(shortDescription) %>',
      '<%= DisplayFieldId3 %>','<%= StringUtils.jsStringEscape(id_attivita) %>');">
        <%= toHtml(description) %>
      </a>
    </td>
    <td valign="center">
      <%= toHtml(shortDescription) %>
    </td>
     <td valign="center">
      <%= toHtml(macroarea) %>
    </td>
    </td>
     <td valign="center">
      <%= toHtml(aggregazione) %>
    </td>
    </td>
    <td valign="center">
      <%= toHtml(attivita) %>
   </td>
  </tr>
<%} } else {%>
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
<input type="hidden" name="displayFieldId3" value="<%= DisplayFieldId3 %>">
<input type="hidden" name="table" value="<%= Table %>">
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.close()">
<br />
</form>
</body>
