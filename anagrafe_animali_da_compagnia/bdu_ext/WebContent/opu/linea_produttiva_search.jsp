<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.text.DateFormat,org.aspcfs.modules.accounts.base.*" %>
<%@ page import="java.util.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.base.*" %>
<jsp:useBean id="ListaLineaProduttiva" class="org.aspcfs.modules.opu.base.LineaProduttivaList" scope="request"/>
<jsp:useBean id="SearchLineaProduttiva" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="listaCategoria" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ListaMacroCategoria" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="ListaOldIter" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<%@ include file="../initPage.jsp" %>
<%@ include file="../initPopupMenu.jsp" %>

<table class="trails" cellspacing="0">
<tr>
<td>
<dhv:label name="opu.stabilimento.linea_produttiva"></dhv:label>
</td>
</tr>
</table>
<br/><br/>
<!-- 
<form method="post" action = "LineaProduttivaAction.do?command=Search">
<table cellpadding="8" cellspacing="0" border="0" width="100%" class="details">
<tr>
<th colspan="2">RICERCA</th>
</tr>
<tr>
<td  style="background-color: red"><dhv:label name = "opu.stabilimento.linea_produttiva.categoria"></dhv:label></td>
<td><%=ListaMacroCategoria.getHtmlSelect("searchcodeidMacrocategoria",-1)  %></td>
</tr>
<tr>
<td  style="background-color: rgb(204, 255, 153);"><dhv:label name = "opu.stabilimento.linea_produttiva.sottocategoria"></dhv:label></td>
<td><%=listaCategoria.getHtmlSelect("searchcodeidCategoria",-1) %></td>
</tr>
<tr>
<td colspan="2"><input type = "submit" value = "Ricerca"></td>
</tr>
</table>
<input type = "hidden" name = "tipoSelezione" value = "<%=ListaLineaProduttiva.getTipoSelezione() %>">
</form> -->


<!--<dhv:pagedListStatus  object="SearchLineaProduttiva"/>-->
<input type = "hidden" name = "lineaProduttivaSelezionata" value = "">
<form action = "LineaProduttivaAction.do?command=ScegliLineaProduttiva" method="post">

<table cellpadding="8" cellspacing="0" border="0" width="100%" class="pagedList">
<tr>
<th colspan="2"><dhv:label name = "opu.stabilimento.linea_produttiva"></dhv:label></th>
</tr>

<%

if(ListaLineaProduttiva != null && ListaLineaProduttiva.size()>0)
{
	String macrocategoria = "" ;
	String categoria = "" ;
	int idTipoIter = -1 ;
	Iterator<LineaProduttiva> itLp = ListaLineaProduttiva.iterator();
	while (itLp.hasNext())
	{
		%>
		
		<%
		LineaProduttiva lp = itLp.next();
		
		if( ! macrocategoria.equals(lp.getMacrocategoria()) )
		{
			macrocategoria = lp.getMacrocategoria() ;
			
			%>
			<tr><td colspan="2" style="background-color: red"><%=lp.getMacrocategoria() %></td></tr>
			<%
		}
		
		
		if( ! categoria.equals(lp.getCategoria()) )
		{
			categoria = lp.getCategoria() ;
			
			%>
			<tr><td colspan="2" style="background-color: rgb(204, 255, 153);"><%=categoria %></td></tr>
			<%
		}
		%>
		<tr>
		
		<td>
		
		<%if( ListaLineaProduttiva.getTipoSelezione().equals("multipla"))
			{
			%>
		<input type="checkbox" name = "idLineaProduttiva" value = "<%=lp.getId() %>" > 
		<%}
		else
		{
			%>
			<a href = "LineaProduttivaAction.do?command=ScegliLineaProduttiva&idLineaProduttiva=<%=lp.getId() %>">Seleziona</a>
			<%
			
		}
		%>
		</td>
		<td><%=lp.getAttivita() %></td>
		</tr>
		<%
		
	}
}
else
{
%>
<tr><td colspan="2">NESSUN RECORD PRESENTE</td></tr>
<%	
}
%>
<input type="hidden" value="<%= isPopup(request) %>" name="popup" />
<input type="hidden" value="<%=request.getParameter("tipoRegistrazione") %>" name="tipoRegistrazione" id="tipoRegistrazione" />
</table>
<input type = "hidden" name = "tipoSelezione" value = "<%=ListaLineaProduttiva.getTipoSelezione() %>">
<input type = "submit" value = "Fatto">
</form>
<!--<dhv:pagedListControl object="SearchLineaProduttiva" tdClass="row1"/>-->
