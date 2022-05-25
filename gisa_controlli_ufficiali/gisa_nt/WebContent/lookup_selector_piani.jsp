<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.base.*,org.aspcfs.utils.web.*" %>
<%@ page import="org.aspcfs.utils.*" %>
<jsp:useBean id="BaseList" class="org.aspcfs.utils.web.CustomLookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="DisplayFieldId" class="java.lang.String" scope="request"/>
<jsp:useBean id="lookup_sezioni_piani" class = "org.aspcfs.utils.web.LookupList" scope = "request"></jsp:useBean>
<jsp:useBean id="ListaPianiLookup" class = "org.aspcfs.utils.web.LookupList" scope = "request"></jsp:useBean>

<jsp:useBean id="DisplayFieldId2" class="java.lang.String" scope="request"/>
<jsp:useBean id="Table" class="java.lang.String" scope="request"/>
<jsp:useBean id="FiltroDesc" class="java.lang.String" scope="request"/>
<jsp:useBean id="SearchPianiListCuInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script>
arrayValue = new Array();
arrayDesc = new Array();

function fillArrayonLoad(indice,id,descrizione,ischecked)
{
	
	if(ischecked == 'true')
	{
		arrayValue[indice] = id ;
		arrayDesc[indice] = descrizione ;
		
	}
	else
	{
		arrayValue[indice] = -1 ;
		arrayDesc[indice] = '' ;
		
		
	}


	
}
function fillArray(indice,campo,id,descrizione)
{
	if(campo.checked == true)
	{
		arrayValue[indice] = id ;
		arrayDesc[indice] = descrizione ;
		indice ++ ;
	}
	else
	{
		arrayValue[indice] = -1 ;
		arrayDesc[indice] = '' ;
		
		
	}


	
}


function checkFiltro() {
  //cancello eventuali spazi all'inizio e alla fine del testo
  var desc = leftTrim(rightTrim(document.elementListView.filtroDesc.value));
  document.elementListView.filtroDesc.value = desc;
  document.elementListView.submit();
    document.elementListView.filtroDesc.focus();  
  
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

boolean nazionali = false ;
boolean regionali = false ;
boolean territoriali = false ;

String  tipi_piani_monitoraggio = (String) request.getAttribute("TipiPiani");
int val_sel = -1 ;
boolean selezione = true ;

if (request.getAttribute("Selezione") != null)
{
	selezione = false ;
}
if (tipi_piani_monitoraggio != null)
{
	val_sel = Integer.parseInt(tipi_piani_monitoraggio);
}
%>

<%@ include file="initPage.jsp" %>
<body onload="javascript:document.elementListView.filtroDesc.focus()">
<%

 
%>

<form name="elementListView" method="post" <%if(selezione == true){ %>action="LookupSelector.do?command=PopupSelectorPianiMonitoraggioCU&table=<%=Table %>" <%}else{ %>action="LookupSelector.do?command=PopupSelectorCustomPianiMonitoraggio&table=<%=Table %>"<%} %>>
<input type = "hidden" name = "idAsl" value = "<%=request.getAttribute("idAsl") %>">
<br />



<table width="100%" border="0">
  <tr>
    <td>
    <%if(request.getAttribute("table")!=null)
    	{%>
    	<input type = "hidden" name = "table" value = "<%=request.getAttribute("table") %>">
    	<%} %>
     
    </td>
  </tr>
  
  <tr>
    <td>
    <table>
    <tr><td><b>Sezione</b></td><td><%=lookup_sezioni_piani.getHtmlSelect("tipo",val_sel)%></td></tr>
    <tr><td><b>Descrizione</b></td><td><input type="text" size="20" name="filtroDesc" value="<%= FiltroDesc %>"/> </td></tr>
	<input type="button" value="<dhv:label name="button.search">Search</dhv:label>" onClick="checkFiltro();">
</table>      
     </td>
  </tr>
  
</table>

<%if(selezione == true)
{ %>
<input type="button" value="<dhv:label name="button.select">Seleziona</dhv:label>" onClick="javascript:clonaNelPadrePiani(arrayValue,arrayDesc)">
<%} %>
<!--<div style="height: 650px; overflow: auto;">-->
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th width="100%" <%if(selezione==true) {%>colspan="3"<%} %>>
     Lista piani di monitoraggio  DPAR 2015
    </th>
  </tr>
  <%
  boolean campio_titolo = true  ;
  String old_gruppo  = "" ;
  String old_sezione  = "" ;

  Iterator j = BaseList.iterator();



  if ( j.hasNext() ) {
    int rowid = 0;
    int index = 0 ;
    String color = "" ;	
    while (j.hasNext()) {
      rowid = (rowid != 1?1:2);
      CustomLookupElement thisElt = (CustomLookupElement)j.next();
      boolean enabled = thisElt.getValue("abilitato") == "true" ? true : false;
     // boolean defaultItem = thisElt.getValue("default_item") == "true" ? true : false;
      String description = thisElt.getValue("descrizione_piano");
      //String shortDescription = thisElt.getValue("short_description");
      String codeString = thisElt.getValue("codice_piano");
      int code = codeString.startsWith("--") ? -1 : Integer.parseInt(codeString);
     
      String sezione = thisElt.getValue("sezione");
	 	
      
      if (!sezione.equals(old_sezione))
      {
    	  if(sezione.equalsIgnoreCase("sezione a"))
    	  {
    		  color = "background-color:#00FF33" ;
    	  }
    	  if(sezione.equalsIgnoreCase("sezione b"))
    	  {
    		  color = "background-color:#00FFFF" ;
    	  }
    	  if(sezione.equalsIgnoreCase("sezione c"))
    	  {
    		  color = "background-color:#CC33FF" ;
    	  }
    	  if(sezione.equalsIgnoreCase("sezione d"))
    	  {
    		  color = "background-color:#FFCC66" ;
    	  }
    	  if(sezione.equalsIgnoreCase("sezione e"))
    	  {
    		  color = "background-color:#993300" ;
    	  }
    	  
    	  
    	  %>
    	  <tr><td colspan="3" style="<%=color %>"> <b><%=sezione %></b></td></tr>
    	 
    	  <%
    	  old_sezione = sezione ;
      }
     
    	  %>
    	 
    	  <%
    	
      
    
      %>
  <tr>
  <%if(selezione == true)
{

%>


  <td >
  <%
  if(thisElt.getListasottopiani().size()==0)
  {
  %>
  <input type = "checkbox" value = "<%=code %>" name = "listaPianiSelezionati" <%if(thisElt.isSelezionato()==true){%>checked="checked"<%} %> onclick="fillArray(<%=index %>,this,<%=code %>,'<%= StringUtils.jsStringEscape(description.replaceAll("\"", "")).toUpperCase() %>');">
<script>
  fillArrayonLoad(<%=index%>,<%=code%>,'<%= StringUtils.jsStringEscape(description.replaceAll("\"", "")).toUpperCase() %>','<%=thisElt.isSelezionato()%>');
  </script>
  <%}%>
  </td>
  <%
  }%>
  
  <td colspan="2">
  		  <%if(selezione == false)
{ %>
<%
  if(thisElt.getListasottopiani().size()==0)
  {
  %>
  		<a href="javascript:setParentValue_piani(<%= code %>,'<%= StringUtils.jsStringEscape(description).toUpperCase() %>');">
       
        <%= toHtml(description.toUpperCase()) %>
        <%}
else{
	%>
	 <%= toHtml(description.toUpperCase()) %>
	<%
}%>
      </a>
      <%}else
      {
    	  %>
    	    <%= toHtml(description.toUpperCase()) %>
    	  <%
      }
  		  %>
     </td>
     
     </tr>
     
     <%
     
     Iterator jj = thisElt.getListasottopiani().iterator();
     while (jj.hasNext()) {
         rowid = (rowid != 1?1:2);
         CustomLookupElement thisElt2 = (CustomLookupElement)jj.next();
         boolean enabled2 = thisElt2.getValue("abilitato") == "true" ? true : false;
        // boolean defaultItem = thisElt.getValue("default_item") == "true" ? true : false;
         String description2 = thisElt2.getValue("descrizione_piano");
         //String shortDescription = thisElt.getValue("short_description");
         String codeString2 = thisElt2.getValue("codice_piano");
         int code2 = codeString2.startsWith("--") ? -1 : Integer.parseInt(codeString2);
        
       
    	 
    	 %>
    	  <tr>
  <%if(selezione == true)
{
 
%>

 <td > &nbsp; &nbsp;</td>
  <td >
  
  <input type = "checkbox" value = "<%=code2 %>" name = "listaPianiSelezionati" <%if(thisElt2.isSelezionato()==true){%>checked="checked"<%} %> onclick="fillArray(<%=index %>,this,<%=code2 %>,'<%= StringUtils.jsStringEscape(description2.replaceAll("\"", "")).toUpperCase() %>');">
<script>
  fillArrayonLoad(<%=index%>,<%=code2%>,'<%= StringUtils.jsStringEscape(description2.replaceAll("\"", "")).toUpperCase() %>','<%=thisElt2.isSelezionato()%>');
  </script>

  </td>
  <%
  }%>
  
  
  <td>
  
  		  <%if(selezione == false)
{ 
  			
  			  %>
  			  &nbsp;&nbsp;&nbsp;
  			  &nbsp;&nbsp;&nbsp;
  		<a href="javascript:setParentValue_piani(<%= code2 %>,'<%= StringUtils.jsStringEscape(description2).toUpperCase() %>');">
       
        <%= toHtml(description2.toUpperCase()) %>
      </a>
      <%
  			  }else
      {
    	  %>
    	    <%= toHtml(description2.toUpperCase()) %>
    	  <%
      }
  		  %>
     </td>
     
     </tr>
     
  
  
<%
	index++;
    
     }
     index++;
    }
    index = 0 ;
    String[] lista_piani =(String[]) request.getAttribute("listaPianiSel");
    
    if (lista_piani != null)
    	
    for (int i = 0 ; i < lista_piani.length ; i ++)
    {
    	
    	if (! BaseList.contains(Integer.parseInt(lista_piani[i])))
    	{
    		%>
    		  <input type = "hidden" id = "hidden_<%=index %>" value = "<%=lista_piani[i] %>" name = "listaPianiSelezionati" checked="checked"/>
    	<%} %>
    	
    		<%
    		index++ ;
    	
    	
    }
   
    
    
    
  
  } 
  
  else {%>
      <tr class="containerBody">
        <td >
          <dhv:label name="calendar.noOptionsAvailable.text">No options are available.</dhv:label>
        </td>
      </tr>
<%}%>
</table>
<!--</div>-->
<input type="hidden" name="rowcount" value="0">
<br/>
<br/>
<%if(selezione == true)
{ %>
<input type="button" value="<dhv:label name="button.select">Seleziona</dhv:label>" onClick="javascript:clonaNelPadrePiani(arrayValue,arrayDesc)">
<%} %>
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.close()">
</form>


</body>
