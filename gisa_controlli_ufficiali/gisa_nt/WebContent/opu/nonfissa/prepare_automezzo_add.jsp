<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants" %>


<%@ include file="../../initPage.jsp"%>


<form name="searchAccount" id = "searchAccount" action="OpuStab.do?command=SearchNonFissa" method="post">
    <table style="border:1px solid black">
<tr><td>    
 <b>Partita IVA/ Codice fiscale</b> <br/>
 Inserire la partita iva o il cf impresa cui agganciare l'automezzo.
 </td></tr>
 <tr><td>
<input type="text" maxlength="11" size="50" name="searchpartitaIva" value=""> <input type="submit" value="CERCA"/>
</td></tr>
</table>
</form>