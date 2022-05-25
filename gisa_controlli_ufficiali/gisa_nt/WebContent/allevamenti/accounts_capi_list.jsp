<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@page import="org.aspcfs.taglib.PagedListStatusHandler"%><jsp:useBean id="CapiListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>

 <link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css" />


<%@page import="java.util.ArrayList"%>
<%@page import="org.aspcfs.modules.allevamenti.base.CapoAllevamento"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Date"%><script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="extjs/ext-all.js"></script>
<script type="text/javascript" src="extjs/examples/ux/TableGrid.js"></script>
<script type="text/javascript" >
Ext.onReady(function(){
        
        // create the grid
        var grid = new Ext.ux.grid.TableGrid("tabella_lista_capi", {
            stripeRows: true // stripe alternate rows
        });
        grid.render();
});
</script>
<%
ArrayList<CapoAllevamento> listaCapi = (ArrayList<CapoAllevamento>)request.getAttribute("ListaCapi");
String idAllevamento = (String) request.getAttribute("idAllevamento");
String codice_azienda = (String)request.getAttribute("codiceAzienda");
%>

<%-- <a href = "LookupSelector.do?command=ElencoCapi&richiesto_report=SI&codice_azienda=<%=codice_azienda%>&id_allevamento=<%=idAllevamento%>"> --%>
<!-- <img src="images/icona-excel.gif" > Esporta in Excel</a> -->

<center><b>Elenco capi in Allevamento </b></center>
<table id="tabella_lista_capi" cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList" />
	<thead>
	  <tr>
	   
	    <th width="16%" nowrap>
	      <strong>Matricola</strong>
	    </th>
	    <th width="16%" nowrap>
	      <strong>Specie</strong>
	    </th>
	    <th width="16%" nowrap>
	      <strong>Razza</strong>
	    </th>
	    <th width="16%" nowrap>
	      <strong>Sesso</strong>
	    </th>
	    <th width="16%" nowrap>
	      <strong>Data Nascita</strong>
	    </th>
	  </tr>
  </thead>
    <tbody>
    
    <%
    

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
  
    for (CapoAllevamento capo : listaCapi)
    {
    	%>
    <tr>
    <td>
    <%=capo.getMatricola() %>
    </td>
    <td>
    <%=capo.getSpecie() %>
    </td>
    <td>
    <%=toHtml(capo.getRazza()) %>
    </td>
    <td>
    <%=capo.getSesso()%>
    </td>
    <td>
    <%
    if (capo.getDataNascita()!=null)
    {
    	out.print(sdf.format(new Date(capo.getDataNascita().getTime())));
    }
    else
    {
    	out.print("");
    }
    %>
    </td>
    </tr>
    
    <%	
    }
    
    
    %>
      
    </tbody>
    
    </table>
    
   
    
    <dhv:pagedListControl object="CapiListInfo" tdClass="row1"/>
 