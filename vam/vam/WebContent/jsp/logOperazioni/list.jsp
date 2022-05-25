<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage=""%>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<h4 class="titolopagina">
		    		Lista delle Operazioni Utente   		
</h4>    

<form action="LogOperazioniList.us" method="post">
<jmesa:tableModel items="${op_list}" id="opList" var="op">
		<jmesa:htmlTable styleClass="tabella">
				<jmesa:htmlRow>
					<jmesa:htmlColumn sortable="false" filterable="false" property="id"  title="Id">${op.id}</jmesa:htmlColumn>				
					<jmesa:htmlColumn sortable="false" filterable="false" property="data"  title="Data">${op.data}</jmesa:htmlColumn>					
					<jmesa:htmlColumn sortable="false" filterable="false" property="username"  title="Username">${op.username}</jmesa:htmlColumn>																						
					<jmesa:htmlColumn sortable="false" filterable="false" property="url" title="Url" >${op.url}</jmesa:htmlColumn>
					<jmesa:htmlColumn sortable="false" filterable="false" title="Operazione" >
						<a href="LogOperazioniDetail.us?idOp=${op.id}" onclick="attendere()">Dettaglio</a>
					</jmesa:htmlColumn>		
				</jmesa:htmlRow>
		</jmesa:htmlTable>
</jmesa:tableModel>
</form>
	   
<script type="text/javascript">
		function onInvokeAction(id)
		{
			setExportToLimit(id, '');
			createHiddenInputFieldsForLimitAndSubmit(id);
		}
</script>