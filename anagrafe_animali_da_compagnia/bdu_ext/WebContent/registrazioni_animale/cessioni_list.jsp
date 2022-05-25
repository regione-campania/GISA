<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ page
	import="java.util.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.base.*,org.aspcfs.modules.registrazioniAnimali.base.*"%>
<jsp:useBean id="listaEventi"
	class="org.aspcfs.modules.registrazioniAnimali.base.EventoList"
	scope="request" />
<jsp:useBean id="registrazioniList"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="animaleDettaglio"
	class="org.aspcfs.modules.anagrafe_animali.base.Animale"
	scope="request" />
<jsp:useBean id="EventiListInfo"
	class="org.aspcfs.utils.web.PagedListInfo" scope="session" />

<%@ include file="../initPage.jsp"%>
<%@ include file="../initPopupMenu.jsp"%>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="javascript/popURL.js"></SCRIPT>

<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>

<script type="text/javascript">
//Per filtrare cessioni da asl di ingresso
function changeAsl(){
	
	$("#addAnimale").attr("action", "RegistrazioniAnimaleCessioni.do?command=Search" );
	$("#addAnimale").submit();
	
}
</script>

<%
	String param1 = "idAnimale=" + animaleDettaglio.getIdAnimale()
			+ "&idSpecie=" + animaleDettaglio.getIdSpecie();
%>
<dhv:evaluate if="<%=!isPopup(request)%>">
	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
			<td><dhv:evaluate
				if="<%="out".equals(EventiListInfo.getSavedCriteria()
									.get("searchexacttype"))%>">
				<dhv:label name="">Cessioni in uscita</dhv:label>
			</dhv:evaluate> <dhv:evaluate
				if="<%="in".equals(EventiListInfo.getSavedCriteria()
									.get("searchexacttype"))%>">
				<dhv:label name="">Cessioni in ingresso</dhv:label>
			</dhv:evaluate></td>
		</tr>
	</table>
	<%-- End Trails --%>
</dhv:evaluate>
<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="EventiListInfo"/>

<form name="addAnimale" id="addAnimale"
	action="RegistrazioniAnimaleCessioni.do?command=PresaCessioneAutomatica&auto-populate=true<%=addLinkParams(request, "popup|popupType|actionId")%>"
	method="post">


<input type="hidden" value="7" name="searchcodeidTipologiaEvento" id="searchcodeidTipologiaEvento"/>
<input type="hidden" value="in" name="searchexacttype" id="searchexacttype">
<input type="hidden" value="opened" name="searchexactstato" id="searchexactstato">
<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="pagedList">
	<tr>
		<!--  th>
        &nbsp;
      </th--> <%int numeroCessioniAutomatiche = 0; %>
      				<%
      				AslList
								.setJsEvent("onChange=\"javascript:changeAsl();\"");
				%>
  <dhv:evaluate
			if="<%="in".equals(EventiListInfo.getSavedCriteria().get(
								"searchexacttype"))%>">    				
   Filtra per asl origine:   <%=AslList.getHtmlSelect("searchcodeidAslInserimentoEventoBDU", listaEventi.getIdAslInserimentoEventoBDU())  %>
   </dhv:evaluate>
		<dhv:evaluate
			if="<%="in".equals(EventiListInfo.getSavedCriteria().get(
								"searchexacttype"))%>">
			<th width="5%" valign="center" align="left"><strong>Accettazione
			automatica</strong></th>
		</dhv:evaluate>
		<th width="5%" valign="center" align="left"><strong>Identificativo</strong>
		</th>
		<th><b><dhv:label name="">Categoria</dhv:label></b></th>
		<th><b><dhv:label name="">Data inserimento in BDU</dhv:label></b></th>
		<th><b><dhv:label name="">Data della registrazione</dhv:label></b></th>
		<th width="15%"><b><dhv:label name="">Da --> A</dhv:label></b>
		</th>
		<th width="20%"><b><dhv:label name="">Animale</dhv:label></b></th>
		<th><b><dhv:label name="">Asl origine</dhv:label></b></th>
	</tr>
	<%
		Iterator j = listaEventi.iterator();
		if (j.hasNext()) {
			// int rowid = 0;
			int i = 0;
			while (j.hasNext()) {
				i++;
				//  rowid = (rowid != 1?1:2);
				Evento thisEvento = (Evento) j.next();
	%>
	<tr class="">
		<!-- td  nowrap>
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
        <%-- To display the menu, pass the actionId, accountId and the contactId--%>
        <a href="javascript:displayMenu('select<%=i%>','menuTic','<%=animaleDettaglio.getIdAnimale()%>','<%=thisEvento.getIdEvento()%>', '<%=false%>');" onMouseOver="over(0, <%=i%>)" onmouseout="out(0, <%=i%>); hideMenu('menuTic');"><img src="images/select.gif" name="select<%=i%>" id="select<%=i%>" align="absmiddle" border="0"></a>
      </td-->
     
		<dhv:evaluate
			if="<%="in".equals(EventiListInfo.getSavedCriteria()
									.get("searchexacttype"))%>">
									
			<td><dhv:evaluate
				if="<%=(((EventoCessione) thisEvento)
													.isCanAccept())%>">
				<input type="checkbox" name="toImport" id="toImport"
					value="<%=thisEvento.getIdEvento()%>" />
					<%numeroCessioniAutomatiche++; %>
			</dhv:evaluate> &nbsp;</td>
		</dhv:evaluate>

		<td nowrap>
		<dhv:evaluate
			if="<%="in".equals(EventiListInfo.getSavedCriteria()
									.get("searchexacttype"))%>">
		<a
			href="RegistrazioniAnimaleCessioni.do?command=Add&idAnimale=<%=thisEvento.getIdAnimale()%><%=addLinkParams(request,
											"popup|popupType|actionId")%>"></dhv:evaluate><%=thisEvento.getIdEvento()%>
		

		<!-- <a
			href="RegistrazioniAnimale.do?command=Details&id=<%=thisEvento.getIdEvento()%>&tipologiaEvento=<%=thisEvento.getIdTipologiaEvento()%><%=addLinkParams(request,
											"popup|popupType|actionId")%>"><%=thisEvento.getIdEvento()%></a>-->
		</td>
		<td nowrap><%=toHtml(registrazioniList
									.getSelectedValue(thisEvento
											.getIdTipologiaEvento()))%></td>
		<td><%=toDateasString(thisEvento.getEntered())%>&nbsp;</td>
		<td><%=toDateasString(thisEvento
											.getDataRegistrazione())%>&nbsp;</td>
		<td>
		<%
			Operatore proprietario = thisEvento
							.getIdProprietarioOriginarioRegistrazione();
					if (proprietario != null) {
						Stabilimento stab = (Stabilimento) (proprietario
								.getListaStabilimenti().get(0));
						LineaProduttiva linea = (LineaProduttiva) (stab
								.getListaLineeProduttive().get(0));
		%> <a
			href="javascript:popURL('OperatoreAction.do?command=Details&opId=<%=linea.getId()%>&popup=true&viewOnly=true','AccountDetails','650','500','yes','yes');"><%=toHtml(proprietario
												.getRagioneSociale())%>&nbsp;</a> <!-- a href="LineaProduttivaAction.do?command=Details&lineaId=<%=linea.getId()%>"><%=toHtml(proprietario
												.getRagioneSociale())%></a--> <%
 	} else {
 %> -- <%
 	}
 %> --> <%
 	Operatore nuovoProprietario = thisEvento
 					.getIdProprietarioDestinatarioRegistrazione();
 			if (nuovoProprietario != null && nuovoProprietario.getIdOperatore() > 0) {
 				Stabilimento stab1 = (Stabilimento) (nuovoProprietario
 						.getListaStabilimenti().get(0));
 				LineaProduttiva linea1 = (LineaProduttiva) (stab1
 						.getListaLineeProduttive().get(0));
 %> <a
			href="javascript:popURL('OperatoreAction.do?command=Details&opId=<%=linea1.getId()%>&popup=true&viewOnly=true','AccountDetails','650','500','yes','yes');"><%=toHtml(nuovoProprietario.getRagioneSociale())%>&nbsp;</a>
		<!-- a href="LineaProduttivaAction.do?command=Details&lineaId=<%=linea1.getId()%>"><%=toHtml(nuovoProprietario.getRagioneSociale())%>&nbsp;</a-->
		<%
			} else {
		%> <%=thisEvento.getDestinazione() %> <%
			}
		%>
		</td>
		<td><a
			href="RegistrazioniAnimaleCessioni.do?command=DetailsAnimale&animaleId=<%=thisEvento.getIdAnimale()%>&idSpecie=<%=thisEvento.getSpecieAnimaleId()%>">
		<%=thisEvento.getMicrochip()%></a></td>
		<td><%=AslList.getSelectedValue(thisEvento
									.getIdAslRiferimento())%></td>
	</tr>

	<%
		}
	%>
	<%
		} else {
	%>
	<tr class="containerBody">
		<td colspan="9"><dhv:label name="">Nessuna registrazione individuata.</dhv:label>
		</td>
	</tr>
	<%
		}
	%>
</table>
</br>
</br>
<dhv:evaluate
	if="<%=("in".equals(EventiListInfo.getSavedCriteria().get(
								"searchexacttype")) && numeroCessioniAutomatiche > 0)%>">
	<input type="submit"
		value="Accettazione automatica cessioni selezionate" />
</dhv:evaluate></form>
<br>

<dhv:pagedListControl object="EventiListInfo" />
