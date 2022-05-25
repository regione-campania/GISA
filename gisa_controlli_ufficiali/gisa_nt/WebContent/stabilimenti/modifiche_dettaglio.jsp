<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*,
	org.aspcfs.modules.stabilimenti.base.*,org.aspcfs.modules.stabilimenti.storico.CampoModificato"%>

<%@page
	import="org.aspcfs.modules.stabilimenti.storico.StoricoModifica"%><jsp:useBean
	id="listaModifiche"
	class="org.aspcfs.modules.stabilimenti.storico.StoricoModificaList"
	scope="request" />
<jsp:useBean id="modifica"
	class="org.aspcfs.modules.stabilimenti.storico.StoricoModifica"
	scope="request" />


<%@ include file="../initPage.jsp"%>
<%@ include file="../initPopupMenu.jsp"%>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="javascript/popURL.js"></SCRIPT>

<% String orgId = request.getParameter("orgId"); %>

<dhv:evaluate if="<%=!isPopup(request)%>">
	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
			<td>
			
			<a href="Stabilimenti.do"><dhv:label name="">Stabilimenti</dhv:label></a> >  <a href="Stabilimenti.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a>
   > <a href="Stabilimenti.do?command=Details&orgId=<%=orgId %>"><dhv:label name="">Scheda Stabilimento </dhv:label></a> >
   <a href="Stabilimenti.do?command=ListaStoricoModifiche&orgId=<%=orgId%>"><dhv:label
				name="Stabilimenti">Lista Storico Modifiche</dhv:label></a> > Dettagli Storico Modifiche 
			 
			</td>
		</tr>
	</table>
	<%-- End Trails --%>
</dhv:evaluate>


<br />

<table class="details" cellpadding="4" cellspacing="0" border="0"
	width="100%">
	<thead>
		<tr>
			<%-- th width="8">
	      &nbsp;
	    </th --%>
			<th width="16%" nowrap><strong>Data Modifica</strong></th>
			<th width="5%" nowrap><strong><dhv:label name="">Nr campi modificati</dhv:label></strong>
			</th>
			<th width="5%" nowrap><strong><dhv:label name="">Utente</dhv:label></strong>
			</th>
		</tr>
	</thead>
	<tbody>

		<tr class="">
			<td width="15%" nowrap><%=modifica.getDataModifica()%>
			</td>
			<td width="15%" nowrap><%=modifica.getNrCampiModificati()%></td>
			<td width="15%" nowrap><dhv:username
				id="<%=modifica.getIdUtente()%>" /></td>
		</tr>
	</tbody>
</table>


<!-- Campi modificati -->
</br></br>
<table class="details" cellpadding="4" cellspacing="0" border="0"
	width="100%">
	<thead>
		<tr>
			<%-- th width="8">
	      &nbsp;
	    </th --%>
			<th width="5%" nowrap><strong><dhv:label name="">Valore iniziale</dhv:label></strong>
			</th>
			<th width="16%" nowrap><strong><dhv:label name="">Valore modificato</dhv:label></strong>
			</th>
			<th width="16%" nowrap><strong><dhv:label name="">Nome Campo</dhv:label></strong>
			</th>
			<th width="16%" nowrap><strong><dhv:label name="">Nome Classe</dhv:label></strong>
			</th>
		</tr>
	</thead>
	<tbody>
		<%
			ArrayList campiModificati = modifica.getListaCampiModificati();
			for (int i = 0; i < campiModificati.size(); i++) {
				CampoModificato campo = (CampoModificato) campiModificati
						.get(i);
		%>
		<tr class="">
			<td width="15%" nowrap><%=campo.getValorePrecedenteStringa()%></td>
			<td width="15%" nowrap><%=campo.getValoreModificatoStringa()%></td>
			<td width="15%" nowrap><%=campo.getNomeCampo()%></td>
			<td width="15%" nowrap><%=campo.getNomeClasse()%></td>
		</tr>
		<%
			}
		%>
	</tbody>
</table>

