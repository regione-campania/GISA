<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.partitecommerciali_ext.base.PartitaCommerciale"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page
	import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.assets.base.*,org.aspcfs.modules.anagrafe_animali.base.*,org.aspcfs.modules.partitecommerciali.base.*"%>
<jsp:useBean id="animaleList"
	class="org.aspcfs.modules.anagrafe_animali.base.AnimaleList"
	scope="request" />
<jsp:useBean id="animaliListInfo"
	class="org.aspcfs.utils.web.PagedListInfo" scope="session" />
<jsp:useBean id="razzaList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="LookupSpecie" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="statoList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="partita"
	class="org.aspcfs.modules.partitecommerciali.base.PartitaCommerciale"
	scope="request" />
<jsp:useBean id="mantelloList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />

<%@ include file="../initPage.jsp"%>
<%@ include file="../initPopupMenu.jsp"%>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
function gestisci(){
	
	//CONTROLLA SELEZIONE ALMENO UN ANIMALE
	formTest = true;
	message = "";
	 
		message = label("", "" +  " Seleziona almeno un animale per la validazione\r\n");
		formTest = false;
		
		$("input[id^=validate_]").each(
				
				function() {

				//alert($(this).attr("id"));	
			if ($(this).is(':checked')){
				//alert('ok');
				//alert($(this).val());
				formTest = true;
				message = "";
			}
				});
		
	if (formTest){	
		document.forms[0].submit();
	}else{
		alert(message);
	}
}
</script>
<dhv:evaluate if="<%=!isPopup(request)%>">
	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
			<td><a href="PartiteCommerciali.do?command=ListaPartiteImportatori">Lista partite commerciali importatori</a> > <a
				href="PartiteCommerciali.do?command=DettagliPartita&idPartita=<%=animaleList.getIdPartita()%>"><dhv:label
						name="">Dettagli partita</dhv:label></a> > <dhv:label name="">Lista animali</dhv:label>
			</td>
		</tr>
	</table>
	<%-- End Trails --%>
</dhv:evaluate>

<dhv:pagedListStatus title='<%=showError(request, "actionError")%>'
	object="animaliListInfo" />

<br />





<form name="validate"
	action="PartiteCommerciali.do?command=Validate&auto-populate=true<%=addLinkParams(request, "popup|popupType|actionId")%>"
	method="post">
	
	<input type="hidden" name="idPartita" id="idPartita" value="<%=animaleList.getIdPartita() %>" />
	<input type="hidden" name="importa" id="importa" value="no" />
	<table class="details" cellpadding="4" cellspacing="0" border="0"
		width="100%">
		<thead>
			<tr>
				<%-- th width="8">
	      &nbsp;
	    </th --%>
				<th width="16%" nowrap><strong>Microchip</strong></th>
				<th width="16%" nowrap><strong><dhv:label name="">Passaporto</dhv:label></strong>
				</th>
				<th width="16%"><strong><dhv:label name="">Razza</dhv:label></strong>
				</th>
				<th width="16%"><strong><dhv:label name="">Proprietario</dhv:label></strong>
				</th>
				<th width="16%" nowrap><strong><dhv:label name="">Nome</dhv:label></strong>
				</th>
				<th width="16%" nowrap><strong><dhv:label name="">Mantello</dhv:label></strong>
				</th>
				<th width="16%" nowrap><strong><dhv:label name="">Stato</dhv:label></strong>
				</th>
				<%if (partita.getIdStatoImportatore() == PartitaCommerciale.ID_STATO_INVIATO ){ %>
					<th width="16%" nowrap><strong><dhv:label name="">Validazione</dhv:label></strong>
					</th>
				<%} %>
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
				<td width="15%" nowrap><dhv:evaluate
						if="<%=!isPopup(request)%>">
						<a orderInfo="<%=toHtml(thisAnimale.getMicrochip())%>"
							href="AnimaleAction.do?command=Details&animaleId=<%=thisAnimale.getIdAnimale()%>&idSpecie=<%=thisAnimale.getIdSpecie()%>&origine=partite_commerciali<%=addLinkParams(request, "popup|popupType|actionId")%>"><%=toHtml(thisAnimale.getMicrochip())%></a>
					</dhv:evaluate> <dhv:evaluate if="<%=isPopup(request)%>">
						<input type="hidden" name="idPartita" id="idPartita"
							value="<%=partita.getIdPartitaCommerciale()%>" />
						<input type="checkbox"
							value="<%=toHtml(thisAnimale.getMicrochip())%>"
							name="microchips[]" id="microchips[]"
							<%=(partita.getListMicrochipAnimaliConVincolo().contains(thisAnimale.getMicrochip())) ? "checked"
								: ""%> />
						<%=toHtml(thisAnimale.getMicrochip())%>
					</dhv:evaluate></td>
				<td width="15%" nowrap><%=toHtml(thisAnimale.getNumeroPassaporto())%>
				</td>
				<td width="15%" nowrap><dhv:evaluate
						if="<%=thisAnimale.getIdRazza() > -1%>">
						<%=toHtml(razzaList.getSelectedValue(thisAnimale.getIdRazza()))%>
					</dhv:evaluate></td>
				<td width="15%" nowrap><%=toHtml(thisAnimale.getNomeCognomeProprietario())%>

				</td>
				<td width="15%" nowrap><%=toHtml(thisAnimale.getNome())%></td>
				<td width="15%" nowrap><dhv:evaluate
						if="<%=thisAnimale.getIdTipoMantello() > -1%>">
						<%=toHtml(mantelloList.getSelectedValue(thisAnimale.getIdTipoMantello()))%>
					</dhv:evaluate></td>
				<td width="15%" nowrap><%=(thisAnimale.getStato() > 1 ? toHtml(statoList.getSelectedValue(thisAnimale.getStato()))
							: "--")%>&nbsp;
					<%
						if (thisAnimale.isFlagVincoloCommerciale()) {
					%> <font color="red">Presenza
						vincolo commerciale</font> <%
 	}
 %></td>
 
<%if (partita.getIdStatoImportatore() == PartitaCommerciale.ID_STATO_INVIATO && !thisAnimale.isFlagValidato() ){ %>
	<td width="15%" nowrap> <input type="checkbox" name="validate_<%=thisAnimale.getMicrochip() %>" id="validate_<%=thisAnimale.getMicrochip() %>" /> </td>
 <%}else if (thisAnimale.isFlagValidato()){ %>
 
 <td width="15%" nowrap>--<%--  <input type="checkbox" name="validate_<%=thisAnimale.getMicrochip() %>" id="validate_<%=thisAnimale.getMicrochip() %>" /> --%> </td>
 
 <%} %>

			</tr>


			<%
				}
			%>
			<!-- IF ITR.HASNEXT MOSTRA IL BOTTONE -->
			<dhv:evaluate if="<%=isPopup(request)%>">
				<!-- Devo aggiungere a lista animali vincolati -->
				<input type="submit" value="Inserisci" />
			</dhv:evaluate>
			<%
				} else {
			%>
			<tr class="containerBody">
				<td colspan="9"><dhv:label name="">Non sono stati trovati animali</dhv:label>
				</td>
			</tr>
			<%
				}
			%>
		</tbody>

	</table>
<input type="button" value="Salva validazione" onclick="javascript: document.getElementById('importa').value='no'; gestisci();" />
<input type="button" value="Salva validazione e approva partita" onclick="javascript: document.getElementById('importa').value='si'; gestisci();" />
</form>
<dhv:pagedListControl object="animaliListInfo" />