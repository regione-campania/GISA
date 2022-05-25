<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.schedaAdozioneCani.base.Indice"%>
<%@page import="org.aspcfs.modules.schedaAdozioneCani.base.Criterio"%>
<%@page import="org.aspcfs.modules.schedaAdozioneCani.base.SchedaAdozione"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@page import="org.aspcfs.modules.anagrafe_animali.base.Animale"%>

<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="criteri" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="indici" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="animaleDettaglio" class="org.aspcfs.modules.anagrafe_animali.base.Animale" scope="request" />

<%
String param1 = "idAnimale=" + animaleDettaglio.getIdAnimale() + "&idSpecie=" + animaleDettaglio.getIdSpecie();
%>


<form method="post" name="form" id="form" action="SchedaAdozioneCaniAction.do?command=Add">

<input type="hidden" name="idAnimale" value="<%=animaleDettaglio.getIdAnimale()%>" />


<%@ include file="../initPage.jsp"%>
<dhv:container name="animale" selected="details"
		object="animaleDettaglio" param="<%=param1%>"
		appendToUrl='<%=addLinkParams(request, "popup|popupType|actionId")%>'>
		
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
		<tr class="containerBody">
				<th>CRITERIO</th>
				<th>INDICE</th>
				<th>PUNTEGGIO</th>
			</tr>
<%
		int i=0;
		while(i<criteri.size())
		{
			Criterio criterio = (Criterio)criteri.get(i);
%>
			<tr class="containerBody">
				<td><dhv:label name=""><%=criterio.getNome().toUpperCase()%></dhv:label></td>
				<td>
<%
					int j=0;
					while(j<indici.size())
					{
						Indice indice = (Indice)indici.get(j);
%>
							<input value="<%=indice.getId()%>" id="indice<%=criterio.getId()%>" name="indice<%=criterio.getId()%>" onclick="document.getElementById('punteggio<%=criterio.getId()%>').value='<%=indice.getPunteggio()%>';" type="radio"   /><%=indice.getNome().toUpperCase()%>
							<br/>
<%
						j++;
					}
%>
				</td>
				<td><input disabled="true" id="punteggio<%=criterio.getId()%>" type="text" name="punteggio" /></td>
			</tr>
<%
			i++;
		}
%>
			
		<tr class="containerBody">
			<td colspan="3">
				<input type="button" onclick="if(checkForm()){document.form.submit();}" value="Salva" />
			</td>
		</tr>
			

		</table>
		</dhv:container>
		</form>
		
		
		
		<script type="text/javascript">
			function checkForm()
			{
<%
				int j=0;
				while(j<criteri.size())
				{
					Criterio criterio = (Criterio)criteri.get(j);
%>
					if(document.getElementById('punteggio<%=criterio.getId()%>').value=='')
					{
						alert('Selezionare tutti gli indici');
						return false;
					}
					
<%
					j++;
				}
%>
				return true;
			}
		</script>