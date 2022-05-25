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
	Vector<BOperatori> risultati = (Vector<BOperatori>)request.getAttribute( "risultati" );
%>

<%@page import="org.aspcfs.modules.operatorifuoriregione.base.BOperatori"%><table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="OperatoriFuoriRegione.do?command=DefaultASL"><dhv:label name="">Imprese Fuori Ambito ASL</dhv:label></a> > 
			<dhv:label name="">Risultati Ricerca Imprese Reg. Altre ASL della Campania da importare</dhv:label>
		</td>
	</tr>
</table>

<table cellpadding="2" cellspacing="2" border="0" width="100%">
  <tr>
    <td width="50%" valign="top">

      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="6">
            <strong><dhv:label name="risultati">Risultati</dhv:label></strong>
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
            <dhv:label name="camera_commercio.ragione_sociale">Ragione Sociale</dhv:label>
          </th>
          <th class="formLabel">
            <dhv:label name="camera_commercio.partita_iva">Identificativo Veicolo</dhv:label>
          </th>
          <th class="formLabel">
            <dhv:label name="camera_commercio.codice_fiscale">Partita IVA</dhv:label>
          </th>
          <th class="formLabel" width="5">&nbsp;
          </th>
        </tr>
        <%
        		for( BOperatori bc: risultati )
        		{
        %>
        
        <tr>
          <td class="formLabel">
       	  	<%=toHtml( bc.getName() ) %>
          </td>
          <td class="formLabel">
          	<%=toHtml( bc.getNome_correntista()) %>
          </td>
          <td class="formLabel">
          	<%=toHtml( bc.getPartita_iva() ) %>
          </td>
           <td class="formLabel">
          	<a href="OperatoriFuoriRegione.do?command=Add&org_id=<%=bc.getOrg_id() %>&tipoD=Autoveicolo">Scheda</a>
          </td>
        </tr>
       
        <%
        		}
        	}
        %>
         
        
</table>
      
    </td>
    
  </tr>
  
 <%
        	if( risultati.size() < 1 )
        	{
        %>
        <tr>
        
        <dhv:permission name="operatoriregione-operatoriregione-add"><a href="OperatoriFuoriRegione.do?command=Add"><dhv:label name="">Aggiungi Impresa Fuori Ambito ASL</dhv:label></a></dhv:permission>
        
         </tr>
        <%}%>
</table>
