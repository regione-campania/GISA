<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../initPage.jsp" %>

<%@page import="org.aspcfs.modules.blackberry.base.SanzioneBlackBerry"%>

<form action="BlackBerryImport.do?command=TrasformaSanzione" method="post">
<%-- Trails --%>
<%
SanzioneBlackBerry cc = (SanzioneBlackBerry)request.getAttribute( "dettaglio" );
%>
<input type = "hidden" name = "dataC" value = "<%=request.getAttribute("dataC") %>">

<input type = "hidden" name = identificativo value = "<%=request.getAttribute("identificativo") %>">
<input type = "hidden" name = "idS" value = "<%=cc.getId() %>">
<input type = "hidden" name = "idC" value = "<%=request.getAttribute("idControllo") %>">
<input type = "hidden" name = "idNC" value = "<%=request.getAttribute("idNonConformita") %>">
<input type = "hidden" name = "orgId" value = "<%=request.getAttribute("orgId") %>">
<table class="trails" cellspacing="0">
	<tr>
		<td>
			
			<dhv:label name="camera_commercio">
			
				Scheda Sanzione Inserita da Black Berry
				
								
			</dhv:label>> 
			<dhv:label name="camera_commercio.cerca">Scheda Attivit&agrave;</dhv:label>
		</td>
	</tr>
</table>

<table cellpadding="2" cellspacing="2" border="0" width="100%">
  <tr>
    <td width="50%" valign="top">

      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        
        <tr>
          <th colspan="2">
            <strong>Scheda Sanzione Inserita da Black Berry
             </strong>
          </th>
        </tr>
        
        <tr>
          <td class="formLabel">
            <dhv:label name="camera_commercio.partita_iva">Verbale</dhv:label>
          </td>
          <td>
            <%=cc.getVerbale()  %>&nbsp;
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="camera_commercio.partita_iva">Data </dhv:label>
          </td>
          <td>
            <%=cc.getData() %>&nbsp;
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="camera_commercio.partita_iva">Trasgressore</dhv:label>
          </td>
          <td>
            <%=toHtml( cc.getTrasgressore()) %>&nbsp;
          </td>
        </tr>
        
        <tr>
          <td class="formLabel">
            <dhv:label name="">Borma violata</dhv:label>
          </td>
          <td>
            <%=toHtml( cc.getnorma_violata() ) %>
          </td>
        </tr>
        
        <tr>
          <td class="formLabel">
            <dhv:label name="camera_commercio.cod_natura_giuridica">Note</dhv:label>
          </td>
          <td>
            <%=toHtml( cc.getNote()) %>
          </td>
        </tr> 
            
       
        
       
        
        
        
        
      </table>
      
      
    </td>
    
  </tr>
  
</table>

	<input type="hidden" name="id" value="<%=cc.getId() %>" />
	
	
		<dhv:permission name="accounts-accounts-nonconformita-add">
			<input type="submit" value="<dhv:label name="button.importa">Importa</dhv:label>" />
		</dhv:permission>
	

</form>

