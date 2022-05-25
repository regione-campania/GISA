<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="com.sun.webkit.ContextMenu.ShowContext"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<dhv:container name="inviocuibr" selected="Invia" object="">
<%@ include file="../initPage.jsp"%>



<form method="post" action="GestioneEsitoIbr.do?command=ImportIbr" enctype="multipart/form-data">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <img border="0" src="images/file.gif" align="absmiddle"><b>File da Inviare</b>
      </th>
    </tr>
   
      <tr class="containerBody">
      <td class="formLabel">
       File
      </td>
      <td>
        <input type="file" id="file1" name="file1" size="45"  required="required">  <a href="#" onclick="rimuoviFile(1); return false;"><img src="images/delete.gif"></a>
      <%=showError(request, "ImportKoError") %>
      </td>
    </tr>
    
  </table>
<input type ="submit" value="Invia File a BDN" onclick="loadModalWindowCustom('ATTENDERE IL COMPLETAMENTO DELL\' INVIO VERSO LA BDN.')">
</form>
</dhv:container>