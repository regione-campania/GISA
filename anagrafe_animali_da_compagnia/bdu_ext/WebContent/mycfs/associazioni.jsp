<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.json.JSONObject"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page
	import="java.util.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.assets.base.*,org.aspcfs.modules.servicecontracts.base.*,org.aspcfs.modules.anagrafe_animali.base.*"%>


<jsp:useBean id="associazioni" class="org.json.JSONObject" scope="request" />
<jsp:useBean id="associazioniListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />


<%@ include file="../initPage.jsp"%>

<dhv:evaluate if="<%=!isPopup(request)%>">
	<%-- Trails --%>
	<table class="trails" cellspacing="0" width="100%">
		<tr>
		
			<td>Lista Associazioni</a></td>

		</tr>
	</table>
	<%-- End Trails --%>
</dhv:evaluate>



<dhv:pagedListStatus title='<%=showError(request, "actionError")%>' object="associazioniListInfo" />
<br />

<input type="button" value="Aggiungi" onclick="javascript:location.href='MyCFS.do?command=ToInsertAssociazione'">
<br/><br/>		

<table class="details" cellpadding="4" cellspacing="0" border="0" width="100%">
	<thead>
		<tr>
			<th width="11%" ><strong>Progressivo</strong></th>
			<th width="11%" ><strong>Denominazione</strong></th>
			<th width="11%" ><strong>Codice fiscale</strong>
			</th>
			<th width="11%" ><strong>Sede</strong>
			</th>
			<th width="11%" ><strong>Indirizzo</strong>
			</th>
			<th width="11%"><strong>Rappresentante legale</strong>
			</th>
			<th width="11%" ><strong>Contatti</strong>
			</th>
			<th width="11%" ><strong>Mail</strong>
			</th>
			<th width="11%"><strong>Operazioni</strong>
			</th>
		</tr>
	</thead>
	<tbody>
	
<%
			Iterator<String> itr = associazioni.keys();
			
			
			if (itr.hasNext()) 
			{
				int rowid = 0;
				int i = 0;
				while (itr.hasNext()) 
				{
					i++;
					rowid = (rowid != 1 ? 1 : 2);
					String key = itr.next();
%>
					<tr class="row<%=rowid%>">
						<td width="13%" nowrap><%=((JSONObject)associazioni.get(key)).get("progressivo")%></td>
						<td width="13%" nowrap><%=((JSONObject)associazioni.get(key)).get("denominazione")%></td>
						<td width="13%" nowrap><%=((JSONObject)associazioni.get(key)).get("codice_fiscale")%></td>
						<td width="13%" nowrap><%=((JSONObject)associazioni.get(key)).get("sede")%></td>
						<td width="13%" nowrap><%=((JSONObject)associazioni.get(key)).get("indirizzo")%></td>
						<td width="13%" nowrap><%=((JSONObject)associazioni.get(key)).get("rappresentante_legale")%></td>
						<td width="13%" nowrap>Fisso: <%=((JSONObject)associazioni.get(key)).get("telefono_fisso")%><br/>
											   Mobile: <%=((JSONObject)associazioni.get(key)).get("telefono_cellulare")%></td>
						<td width="13%" nowrap>Mail: <%=((JSONObject)associazioni.get(key)).get("mail")%><br/>
											   PEC: <%=((JSONObject)associazioni.get(key)).get("pec")%></td>
						<td width="13%" nowrap>
							<input type="button" value="Modifica" onclick="javascript:location.href='MyCFS.do?command=ToEditAssociazione&id=<%=key%>'"><br/>
							<input type="button" value="Elimina" onclick="javascript:if(confirm('Sicuro di voler eliminare?')){location.href='MyCFS.do?command=DeleteAssociazione&id=<%=key%>'}">
						</td>
					</tr>
<%
				}
			} 
			else 
			{
%>
			   <tr class="containerBody">
					<td colspan="9">Non sono state trovate associazioni
			  		</td>
			   </tr>
<%
			}
%>
	</tbody>
</table>
<dhv:pagedListControl object="associazioniListInfo" />