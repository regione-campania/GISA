<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page
	import="java.util.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.assets.base.*,org.aspcfs.modules.servicecontracts.base.*,org.aspcfs.modules.anagrafe_animali.base.*"%>

<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%><jsp:useBean
	id="animaleList"
	class="org.aspcfs.modules.anagrafe_animali.base.AnimaleList"
	scope="request" />
<jsp:useBean id="animaliListInfo"
	class="org.aspcfs.utils.web.PagedListInfo" scope="session" />
<jsp:useBean id="razzaList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
	<jsp:useBean id="tagliaList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="LookupSpecie" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="statoList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="operatore"
	class="org.aspcfs.modules.opu.base.Operatore" scope="request" />
<jsp:useBean id="comuniList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />


<jsp:useBean id="mantelloList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<!-- Action di provenienza -->
<jsp:useBean id="actionFrom" class="java.lang.String" scope="request" />

<%@ include file="../initPage.jsp"%>
<%@ include file="../initPopupMenu.jsp"%>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
	
<%-- Preload image rollovers for drop-down menu --%>
	/* loadImages('select'); */
</script>


<%
	int idLinea = 0;
	if (operatore != null && operatore.getIdOperatore() > 0) {
		Stabilimento stab = (Stabilimento) operatore.getListaStabilimenti().get(0);
		LineaProduttiva lp = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
		idLinea = lp.getId();
	}
%>

<%
	/* Stabilimento stab = (Stabilimento) operatore.getListaStabilimenti().get(0);
	LineaProduttiva linea = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
	int idLinea = linea.getId(); */

	
%>


<dhv:evaluate if="<%=!isPopup(request)%>">
	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>

			<td><dhv:evaluate
					if="<%=("AnimaleAction").equals(actionFrom)%>">
					<a href="AnimaleAction.do?command=SearchForm"><dhv:label
							name="">Ricerca animali</dhv:label></a> > <dhv:label
						name="accounts.SearchResults">Search Results</dhv:label></td>
			</dhv:evaluate>
			<dhv:evaluate if="<%=("LineaProduttivaAction").equals(actionFrom)%>">
				<a href="OperatoreAction.do?command=Search"><dhv:label name="">Risultati ricerca</dhv:label></a> > <a
					href="OperatoreAction.do?command=Details&opId=<%=idLinea%>"> <dhv:label
						name="">Dettaglio operatore</dhv:label></a> >
						<dhv:label name="">Lista animali</dhv:label>
				</td>
			</dhv:evaluate>
		</tr>
	</table>
	<%-- End Trails --%>
</dhv:evaluate>
<%
	Stabilimento stab = null;
	LineaProduttiva lineaP = null;
	String param1 = "-1";
	if (operatore.getIdOperatore() > 0) {
		stab = (Stabilimento) operatore.getListaStabilimenti().get(0);
		lineaP = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
		
		 param1 = "idLinea=" + lineaP.getId();
	}
%>
<dhv:container name="anagrafica" selected="details"
		object="operatore" param="<%=param1%>"
		appendToUrl='<%=addLinkParams(request, "popup|popupType|actionId")%>'>
<dhv:evaluate if="<%=!isPopup(request)%>">
	<dhv:permission name="anagrafe_canina-anagrafe_canina-add">
		<dhv:evaluate
			if="<%=(animaleList.getId_proprietario_o_detentore() > -1)%>">
			<dhv:evaluate if="<%=stab.checkAslStabilimentoUtente(User)%>">
				<dhv:evaluate
					if="<%=!operatore.checkModificaResidenzaStabilimento()%>">
					<%
						if (lineaP.getIdRelazioneAttivita() != LineaProduttiva.idAggregazioneColonia) {
					%>
					<input type="button"
						value="<dhv:label name="">Aggiungi Cane</dhv:label>"
						onClick="javascript:window.location.href='AnimaleAction.do?command=Add&idSpecie=1&idLinea=<%=animaleList.getId_proprietario_o_detentore()%><%=addLinkParams(request, "popup|actionplan")%>';">
					<%
						//}
					%>

					<%
						//if (lineaP.getIdRelazioneAttivita() != LineaProduttiva.idAggregazioneCanile) {
					%>
					<input type="button"
						value="<dhv:label name="">Aggiungi Gatto</dhv:label>"
						onClick="javascript:window.location.href='AnimaleAction.do?command=Add&idSpecie=2&idLinea=<%=animaleList.getId_proprietario_o_detentore()%><%=addLinkParams(request, "popup|actionplan")%>';">

					<%
						}
					%>

					<%
						if (lineaP.getIdRelazioneAttivita() == LineaProduttiva.idAggregazionePrivato) {
					%>
					<input type="button"
						value="<dhv:label name="">Aggiungi Furetto</dhv:label>"
						onClick="javascript:window.location.href='AnimaleAction.do?command=Add&idSpecie=3&idLinea=<%=animaleList.getId_proprietario_o_detentore()%><%=addLinkParams(request, "popup|actionplan")%>';">

					<%
						}
					%>
				</dhv:evaluate>
				<%
					if ((lineaP.getIdRelazioneAttivita() == LineaProduttiva.IdAggregazioneOperatoreCommerciale
											|| lineaP.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneImportatore) && animaleList.size() > 0){
				%>
					<input type="button"
					value="<dhv:label name="">Movimentazione di massa animali verso altro operatore commerciale </dhv:label>"
					onClick="javascript:window.location.href='LineaProduttivaAction.do?command=PrepareMovimentazioneDiMassa&idLinea=<%=animaleList.getId_proprietario_o_detentore()%><%=addLinkParams(request, "popup|actionplan")%>';">
					<%} %>
			</dhv:evaluate>
		</dhv:evaluate>
		<dhv:evaluate
			if="<%=!(animaleList.getId_proprietario_o_detentore() > -1)%>">
			<a
				href="AnimaleAction.do?command=Add<%=addLinkParams(request, "popup|popupType|actionId")%>"><dhv:label
					name="">Aggiungi un animale</dhv:label></a>
		</dhv:evaluate>


	</dhv:permission>

</dhv:evaluate>

<dhv:pagedListStatus title='<%=showError(request, "actionError")%>'
	object="animaliListInfo" />

<br />





<link rel="stylesheet" type="text/css"
	href="extjs/resources/css/ext-all.css" />

<%
	if (operatore.getIdOperatore() > 0) {
		stab = (Stabilimento) operatore.getListaStabilimenti().get(0);
		lineaP = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
		if (lineaP.getIdAttivita() == LineaProduttiva.idAttivitaColonia) {
%>


<%
	}
	}
%>
<table class="details" cellpadding="4" cellspacing="0" border="0"
	width="100%">
	<thead>
		<tr>
			<%-- th width="8">
	      &nbsp;
	    </th --%>
			<th width="16%" nowrap><strong>Tipologia</strong></th>
			<th width="16%" nowrap><strong><dhv:label name="">Microchip</dhv:label></strong>
			</th>
			<th width="16%" nowrap><strong><dhv:label name="">Tatuaggio/2nd microchip</dhv:label></strong>
			</th>
			<th width="16%" nowrap><strong><dhv:label name="">Nome</dhv:label></strong>
			</th>
			<th width="16%"><strong><dhv:label name="">Taglia</dhv:label></strong>
			</th>
			<th width="16%"><strong><dhv:label name="">Razza</dhv:label></strong>
			</th>
			<th width="16%" nowrap><strong><dhv:label name="">Mantello</dhv:label></strong>
			</th>
			<th width="16%" nowrap><strong><dhv:label name="">Stato</dhv:label></strong>
			</th>
			<th width="16%" nowrap><strong><dhv:label name="">Proprietario</dhv:label></strong>
			</th>
			<th width="16%" nowrap><strong><dhv:label name="">Detentore</dhv:label></strong>
			</th>
			<th width="16%" nowrap><strong><dhv:label name="">Asl di Riferimento</dhv:label></strong>
			</th>
			<th width="16%" nowrap><strong><dhv:label name="">Comune proprietario</dhv:label></strong>
			</th>
			<th width="16%" nowrap><strong><dhv:label name="">Data registrazione anagrafe</dhv:label></strong>
			</th>
		</tr>
	</thead>
	<tbody>
		<%
			Iterator itr = animaleList.iterator();
			if (itr.hasNext()) {
				int rowid = 0;
				int i = 0;
				while (itr.hasNext()) {
					i++;
					rowid = (rowid != 1 ? 1 : 2);
					Animale thisAnimale = (Animale) itr.next();
		%>
		<tr class="row<%=rowid%>">
			<td width="15%" nowrap><%=LookupSpecie.getSelectedValue(thisAnimale.getIdSpecie())%></td>
			<td width="15%" nowrap><dhv:evaluate if="<%=!isPopup(request)%>">
					<a orderInfo="<%=toHtml(thisAnimale.getMicrochip())%>"
						href="AnimaleAction.do?command=Details&idLinea=<%=animaleList.getId_proprietario_o_detentore()%>&animaleId=<%=thisAnimale.getIdAnimale()%>&idSpecie=<%=thisAnimale.getIdSpecie()%><%=addLinkParams(request, "popup|popupType|actionId")%>"><%=toHtml(thisAnimale.getMicrochip())%></a>
				</dhv:evaluate> <dhv:evaluate if="<%=isPopup(request)%>">
					<%=toHtml(thisAnimale.getMicrochip())%>
				</dhv:evaluate></td>
			<td width="15%" nowrap><%=toHtml(thisAnimale.getTatuaggio())%></td>
			<td width="15%" nowrap><%=(thisAnimale.getNome() != null) ? toHtml(thisAnimale.getNome()) : "  "%></td>
			<td width="15%" nowrap><dhv:evaluate
					if="<%=thisAnimale.getIdTaglia() > -1%>">
					<%=toHtml(tagliaList.getSelectedValue(thisAnimale.getIdTaglia()))%>
				</dhv:evaluate></td>
			<td width="15%" nowrap><dhv:evaluate
					if="<%=thisAnimale.getIdRazza() > -1%>">
					<%=toHtml(razzaList.getSelectedValue(thisAnimale.getIdRazza()))%>
				</dhv:evaluate></td>
			<td width="15%" nowrap><dhv:evaluate
					if="<%=thisAnimale.getIdTipoMantello() > -1%>">
					<%=toHtml(mantelloList.getSelectedValue(thisAnimale.getIdTipoMantello()))%>
				</dhv:evaluate></td>
			<td width="15%" nowrap><%=toHtml(statoList.getSelectedValue(thisAnimale.getStato()))%>&nbsp;</td>
			<td width="15%" nowrap>&nbsp;<dhv:evaluate
					if="<%=thisAnimale.getNomeCognomeProprietario() != null%>">

					<%=toHtml(thisAnimale.getNomeCognomeProprietario())%>

				</dhv:evaluate></td>
			<td width="15%" nowrap>&nbsp; <dhv:evaluate
					if="<%=thisAnimale.getNomeCognomeDetentore() != null%>">

					<%=toHtml(thisAnimale.getNomeCognomeDetentore())%>

				</dhv:evaluate></td>

			<td width="15%" nowrap><%=toHtml(AslList.getSelectedValue(thisAnimale.getIdAslRiferimento()))%></td>
			<td width="15%" nowrap><%=toHtml(comuniList.getSelectedValue(thisAnimale.getIdComuneProprietario()))%></td>
			<td width="15%" nowrap><%=toDateasString(thisAnimale.getDataRegistrazione())%></td>
		</tr>
		<%
			}
			} else {
		%>


		<tr class="containerBody">
			<td colspan="9"><dhv:label name="">Non sono stati trovati animali. <a
						href="McNonStandard.do">Cerca nei microchips non standard</a>
				</dhv:label></td>
		</tr>
		<%
			}
		%>
	</tbody>
</table>
<dhv:pagedListControl object="animaliListInfo" />
</dhv:container>