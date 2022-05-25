<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<%
	Vector<BImpresePregresso> risultati = (Vector<BImpresePregresso>)request.getAttribute( "risultati" );
%>

<%@page import="org.aspcfs.modules.imprese_pregresso.base.BImpresePregresso"%><table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="ImpresePregresso.do"><dhv:label name="">Imprese Pregresso</dhv:label></a> > 
			<dhv:label name="">Risultati Ricerca</dhv:label>
		</td>
	</tr>
</table>

<table cellpadding="2" cellspacing="2" border="0" width="100%">
  <tr>
    <td width="50%" valign="top">

      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="6">
            <strong><dhv:label name="camera_commercio.risultati">Risultati</dhv:label></strong>
          </th>
        </tr>
        <%
        	if( risultati.size() < 1 )
        	{
        %>
        <tr>
          <td colspan="6" class="formLabel" align="left">
            Nessun Risultato
          </td>
        </tr>
        <%
        	}
        	else
        	{
        %>
        <tr>
          <th class="formLabel">
            <dhv:label name="">Impresa</dhv:label>
          </th>
          <th class="formLabel">
            <dhv:label name="camera_commercio.partita_iva">Partita IVA</dhv:label>
          </th>
          <th class="formLabel">
            <dhv:label name="camera_commercio.codice_fiscale">Codice Fiscale</dhv:label>
          </th>
          <th class="formLabel">
            <dhv:label name="">Comune Sede Operativa</dhv:label>
          </th>
          <th class="formLabel">
            <dhv:label name="camera_commercio.data_importazione">Data Importazione</dhv:label>
          </th>
          <th class="formLabel" width="5">&nbsp;
          </th>
        </tr>
        <%
        		for( BImpresePregresso bc: risultati )
        		{
        %>
        
        <tr>
          <td class="formLabel">
       	  	<%=toHtml( ((bc.getRagione_sociale()!=null && !bc.getRagione_sociale().equals("null")) ? (bc.getRagione_sociale()) : (""))+" - "+bc.getDenominazione() ) %>
          </td>
          <td class="formLabel">
          	<%=toHtml( bc.getPartita_iva() ) %>
          </td>
          <td class="formLabel">
          	<%=toHtml( bc.getCodice_fiscale() ) %>
          </td>
          <td class="formLabel">
          	<%=toHtml( bc.getComune_sede_operativa() ) %>&nbsp;
          </td>
          <td class="formLabel">
          		&nbsp;<zeroio:tz timestamp="<%=bc.getData_import() %>" />
          </td>
          <td class="formLabel">
          	<a href="ImpresePregresso.do?command=Dettaglio&id=<%=bc.getId() %>">Scheda</a>
          </td>
        </tr>
        <%
        		}
        	}
        %>
        
      </table>
      
      
    </td>
    
  </tr>
  
</table>
