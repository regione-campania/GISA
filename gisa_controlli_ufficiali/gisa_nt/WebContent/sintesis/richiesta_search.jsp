<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: accounts_search.jsp 18543 2007-01-17 02:55:07Z matt $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.base.Constants,org.aspcfs.utils.web.*" %>

<%@ include file="../initPage.jsp" %>

  <dhv:container name="sintesisimport" selected="Pratiche" object=""  param="">

<form name="searchAccount" action="StabilimentoSintesisAction.do?command=SearchPratica" method="post">



      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong><dhv:label name="">Ricerca Rapida Pratica</dhv:label></strong> 
          </th>
        </tr>
        
        <tr>
          <td class="formLabel">
            <dhv:label name="">Ragione sociale</dhv:label>
          </td>
          <td>
            <input type="text" maxlength="70" size="50" name="ragioneSociale" value="">
          </td>
        </tr>
        
        <tr>
          <td class="formLabel">
            Partita IVA
          </td>
          <td>
            <input type="text" maxlength="70" size="50" name="partitaIva" value="">
          </td>
        </tr>
        
        <tr>
          <td class="formLabel">
            <dhv:label name="">Approval number</dhv:label>
          </td>
          <td>
            <input type="text" maxlength="70" size="50" name="approvalNumber" value="">
          </td>
        </tr>
               <tr><td> <input type="submit" value="Cerca"></td></tr>
      
      </table>  
</form>

</dhv:container>

