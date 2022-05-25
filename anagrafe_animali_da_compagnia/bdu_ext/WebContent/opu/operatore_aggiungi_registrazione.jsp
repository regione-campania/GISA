<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page
	import="java.io.*,java.util.*,org.aspcfs.utils.web.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.registrazioniAnimali.base.*"%>
	

<%@page import="org.apache.derby.impl.sql.compile.NewInvocationNode"%><script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="operatore" class="org.aspcfs.modules.opu.base.Operatore"
	scope="request" />
<jsp:useBean id="canAddModificaResidenza" class="java.lang.String" scope="request"/>
<jsp:useBean id="modulo" class="java.lang.String" scope="request"/>
<jsp:useBean id="newIndirizzo" class="org.aspcfs.modules.opu.base.Indirizzo"
	scope="request" />
<jsp:useBean
	id="listaRegistrazioni"
	class="org.aspcfs.modules.opu.base.RegistrazioniOperatoreList"
	scope="request" />
<jsp:useBean id="registrazioniOperatoreList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
	
	
	
	
	

<%Stabilimento stab = (Stabilimento) operatore.getListaStabilimenti().get(0); 
  LineaProduttiva lp = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
  Indirizzo indOperativo = stab.getSedeOperativa();
  
  String param1 = "idLinea=" + lp.getId();
%>

<%@ include file="../initPage.jsp"%>
<dhv:evaluate if="<%= !isPopup(request) %>">
	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
		<dhv:evaluate if="<%=modulo != null && ("").equals(modulo) %>">
			<td width="100%"><a href="OperatoreAction.do?command=Details&opId=<%=lp.getId() %>"><dhv:label
				name="">Dettaglio operatore</dhv:label></a> > <dhv:label
				name="">Aggiungi registrazioni</dhv:label>
			</td>
		</dhv:evaluate>
		<dhv:evaluate if="<%=modulo != null && ("accettazione").equals(modulo) %>">
			<td width="100%"><a href="OperatoreAction.do?command=ListaModificheIndirizzoIngressoAsl"><dhv:label
				name="">Lista richieste in ingresso</dhv:label></a> > <dhv:label
				name="">Aggiungi registrazione</dhv:label>
			</td>
		</dhv:evaluate>
		</tr>
	</table>
	<%-- End Trails --%>
</dhv:evaluate>
<body>


<dhv:container name="anagrafica" selected="details"
	object="operatore" param="<%=param1%>"
	appendToUrl='<%=addLinkParams(request, "popup|popupType|actionId")%>'>
</br>
</br>
<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="details">
	<tr>
		<th colspan="2"><strong><dhv:label name="">Registrazioni possibili</dhv:label></strong>
		</th>
	</tr>
	
	<tr><td>
	<dhv:evaluate if="<%=(canAddModificaResidenza != null && !("").equals(canAddModificaResidenza) && ("canAddModificaResidenza").equals(canAddModificaResidenza))%>">
	<dhv:evaluate if="<%=(operatore.checkModificaResidenzaStabilimento() && (newIndirizzo.getIdAsl() == User.getSiteId()||newIndirizzo.getIdAsl()==14)) %>"> 
	<a href="OperatoreAction.do?command=PreparaAccettazioneModificaIndirizzoOperatore&opId=<%=lp.getId() %>" >
	Accettazione modifica residenza </a>	
	</dhv:evaluate>
	<dhv:evaluate if="<%=(!operatore.checkModificaResidenzaStabilimento()) %>"> 
	<a href="OperatoreAction.do?command=PreparaModificaIndirizzoOperatore&opId=<%=lp.getId() %>" >
	Modifica residenza fuori asl / fuori regione</a>	
	</dhv:evaluate>
	</dhv:evaluate>
	</td></tr>
</table>
</br></br>

<table class="details" cellpadding="4" cellspacing="0" border="0"
	width="100%">
	<thead>
		<tr>
		<th colspan="8"><strong><dhv:label name="">Cronologia registrazioni</dhv:label></strong>
		</th>
	</tr>
		<tr>
			<%-- th width="8">
	      &nbsp;
	    </th --%>
	    <th width="16%" nowrap><strong><dhv:label name="">Identificativo</dhv:label></strong>
			</th>
			<th width="16%" nowrap><strong>Tipologia</strong></th>

			<th width="16%" nowrap><strong><dhv:label name="">Data inserimento in BDU</dhv:label></strong>
			</th>
			<th width="16%" nowrap><strong><dhv:label name="">Data della registrazione</dhv:label></strong>
			</th>
			<th width="16%" nowrap><strong><dhv:label name="">Origine</dhv:label></strong>
			</th>
			<th width="16%" nowrap><strong><dhv:label name="">Destinazione</dhv:label></strong>
			</th>
		    <th width="16%" nowrap><strong><dhv:label name="">Asl inserimento</dhv:label></strong>
			</th>
		    <th width="16%" nowrap><strong><dhv:label name="">In sospeso</dhv:label></strong>
			</th>
		</tr>
	</thead>
	<tbody>
		<%
			Iterator itr = listaRegistrazioni.iterator();
			if (itr.hasNext()) {
				int rowid = 0;
				int i = 0;
				while (itr.hasNext()) {
					i++;
					rowid = (rowid != 1 ? 1 : 2);
					RegistrazioneOperatore thisReg = (RegistrazioneOperatore) itr.next();
		%>
		<tr class="row<%=rowid%>">
			<td width="15%" nowrap><%=(thisReg.getIdRegistrazione()) %></td>
			<td width="15%" nowrap><%=registrazioniOperatoreList.getSelectedValue(thisReg.getIdTipologiaRegistrazioneOperatore())%></td>
			<td width="15%" nowrap><%=toDateasString(thisReg.getEntered()) %></td>
			<td width="15%" nowrap><%=toDateasString(thisReg.getDataRegistrazione())%></td>
			<td width="15%" nowrap><%=toHtml(thisReg.getOrigine())%></td>
			<td width="15%" nowrap><%=toHtml(thisReg.getDestinazione())%></td>
			<td width="15%" nowrap><%=toHtml(AslList.getSelectedValue(thisReg.getIdAslInserimentoRegistrazione()))%></td>
			<td width="15%" nowrap><%=toHtml(thisReg.getStato())%></td>
		</tr>
		<%
			}
			} else {
				%>
				
						
			   <tr class="containerBody">
		<td colspan="9"><dhv:label name="">Nessuna registrazione individuata</dhv:label>
		</td>
	</tr>
			    <%
			    
		    
	

		}%>
	</tbody>
</table>

</br>
</br>






</dhv:container>