<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.math.BigDecimal"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<jsp:useBean id="OrgList" class="org.aspcfs.modules.opu.base.OperatoreList" scope="request"/>
<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="tipologiaList" class="org.aspcfs.utils.web.LookupList" scope="request" />

<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>

<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="OperatoreAction.do"><dhv:label name="opu.operatore"></dhv:label></a> > 
<dhv:label name="accounts.SearchResults">Search Results</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:evaluate if="<%= (User.getRoleType() > 0) %>" >
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><dhv:label name="accounts.manage">Select an account to manage.</dhv:label></td>
  </tr>
</table>
</dhv:evaluate>

<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="SearchOrgListInfo"/>
<% int columnCount = 0; %>
<table cellpadding="8" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
  <dhv:evaluate if="<%= isPopup(request) %>">
    <th nowrap="nowrap"></th>
   </dhv:evaluate>
    <th nowrap <% ++columnCount; %>>
      <strong><a href="OpeatoreAction.do?command=Search&column=o.name"><dhv:label name="opu.operatore.ragione_sociale"></dhv:label></a></strong>
      <%= SearchOrgListInfo.getSortIcon("o.name") %> 
    </th>
    
    <th nowrap <% ++columnCount; %>>
     <dhv:label name="opu.operatore.tipologia_proprietario">Tipologia</dhv:label>
      <%= SearchOrgListInfo.getSortIcon("o.name") %>
    </th>
            <th nowrap <% ++columnCount; %>>
         <dhv:label name="opu.operatore.piva"></dhv:label>
		</th>
		 <th nowrap <% ++columnCount; %>>
         <dhv:label name="opu.operatore.cf"></dhv:label>
		</th>

		<th nowrap <% ++columnCount; %>>
          Sede operativa/residenza
        </th>
       
        <th nowrap <% ++columnCount; %>>
          <strong>Data Inserimento</strong>
		</th>
    
        <!-- Commento perchè inutile, fa solo sfalsare l'elenco -->
        <!-- th nowrap <% ++columnCount; %>>
          <strong>Inserito da</strong>
		</th>
		<th nowrap <% ++columnCount; %>>
          <strong>Modificato da</strong>
		</th -->
		<th nowrap>
          <strong>Indice di capienza</strong>
		</th>
		<th nowrap>
          <strong>Numero cani presenti</strong>
		</th>
  </tr>
<%
	Iterator j = OrgList.iterator();
	if (j.hasNext() ) {
	    int rowid = 0;
	    int i = 0;
	    while (j.hasNext()) {
		    i++;
		    //rowid = (rowid != 1 ? 1 : 2);
		    Operatore thisOrg = (Operatore)j.next();
		    String canile_bloccato=""; 
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String data_corrente_st = sdf.format(new Date());
			Date data_corrente = sdf.parse(data_corrente_st);
			if(thisOrg.isBloccato()){
				String data_operazione_st = sdf.format(new Date(thisOrg.getData_sospensione_blocco().getTime()));
				Date data_operazione = sdf.parse(data_operazione_st);
				if(data_operazione.compareTo(data_corrente)<=0){
					canile_bloccato="<font color='red'><b> - CHIUSO PER BLOCCO AMMINISTRATIVO </b></font>";
					canile_bloccato+="<img title=\"Il canile &egrave; sottoposto a blocco di cani in ingresso"+
					//dal "+data_operazione_st.substring(0,10)+
					" con motivo: "+thisOrg.getMotivo_blocco()+"\" class=\"masterTooltip\" src=\"images/questionmark.png\" width=\"15\"/>";
				}   
			}else if(thisOrg.getData_operazione_blocco()!=null){
				String data_operazione_st = sdf.format(new Date(thisOrg.getData_riattivazione_blocco().getTime()));
				Date data_operazione = sdf.parse(data_operazione_st);
				if(data_operazione.compareTo(data_corrente)>0){
					canile_bloccato="<font color='red'><b> - CHIUSO PER BLOCCO AMMINISTRATIVO </b></font>";
					canile_bloccato+="<img title=\"Il canile &egrave; sottoposto a blocco di cani in ingresso"+ 
					//dal "+data_operazione_st.substring(0,10)+
					" con motivo: "+thisOrg.getMotivo_blocco()+"\" class=\"masterTooltip\" src=\"images/questionmark.png\" width=\"15\"/>";				
				}
			}
		    //System.out.println("###### "+thisOrg.getIdOperatore() + " - name: "+thisOrg.getRagioneSociale()+" BLOCCATO: "+thisOrg.isBloccato()+" - "+thisOrg.getData_operazione_blocco());
			if(thisOrg.getIdOperatore()>=10000000){ 
				rowid = (rowid != 1 ? 1 : 2); %>
			  	<tr class="row<%= rowid %>">
		  		<dhv:evaluate if="<%= isPopup(request) %>">
					<td nowrap><a href="AnimaleAction.do?command=ScegliOperatore&tipologiaSoggetto=<%=(String)request.getAttribute("TipologiaSoggetto") %>&opId=<%=thisOrg.getIdOperatore()%>">Seleziona</a></td>
				</dhv:evaluate>
				<%
				String ragione_sociale = thisOrg.getRagioneSociale();
				%>
				<dhv:evaluate if="<%= isPopup(request) %>">
		    	<td nowrap><%= toHtml(ragione_sociale) %> 
		    	</dhv:evaluate>
		    	<dhv:evaluate if="<%= !isPopup(request) %>">
		    	<td nowrap><%= toHtml(ragione_sociale) %> 
		    	</dhv:evaluate>
		       	</td>
			    <td nowrap>  <%=toHtml(tipologiaList.getSelectedValue((int)thisOrg.getId_linea_produttiva()))%>
				</td>
				<td nowrap>
		    	<%= ( (((int)thisOrg.getId_linea_produttiva()) != LineaProduttiva.idAggregazionePrivato) ?  toHtml(thisOrg.getPartitaIva()) : " -- ") %>
		       </td>
		    	<td nowrap>
		       	<%= toHtml(thisOrg.getCodFiscale()) %>
				</td>
		  		<td nowrap>--
				<%//=thisStab.getSedeOperativa().toString() 
				%>
				</td> 
				<td nowrap>
		      	<%=toDateasString(thisOrg.getEntered())%>
				</td>
				<td nowrap>--
				</td>
		  	</tr>
			<% }else{
			    StabilimentoList listaStab =  thisOrg.getListaStabilimenti() ;
			  //  System.out.println(listaStab.size());
			    Iterator itStab = listaStab.iterator();
			    while (itStab.hasNext()){
			      	Stabilimento thisStab = (Stabilimento)itStab.next();
			    	Iterator itLinee = thisStab.getListaLineeProduttive().iterator();
			    	
			     	Connection conn = GestoreConnessioni.getConnection();
					
			    	while (itLinee.hasNext()){
			    		i++;
			    		rowid = (rowid != 1 ? 1 : 2);
			    		LineaProduttiva lp = (LineaProduttiva) itLinee.next();
			%>
			  	<tr class="row<%= rowid %>">
			  		<dhv:evaluate if="<%= isPopup(request) %>">
						<td nowrap><a href="AnimaleAction.do?command=ScegliOperatore&tipologiaSoggetto=<%=(String)request.getAttribute("TipologiaSoggetto") %>&opId=<%=lp.getId()%>">Seleziona</a></td>
				</dhv:evaluate>
					<%
					String chiuso="";
					String ragione_sociale = "";
					if (lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneCanile){
						ragione_sociale = thisOrg.getRagioneSociale();
						ragione_sociale += (((CanileInformazioni) lp).isAbusivo()) ? " -- Sogg. Abusivo" : "";
						//ragione_sociale += 
						if(lp.getDataFine() != null){ 
							chiuso="<font color='red'><b> - CANILE CHIUSO </b></font>" ;
							chiuso+="<img title=\"Il canile &egrave; CHIUSO/BLOCCATO dal "+lp.getDataFine().toString().substring(0,10)+"\" class=\"masterTooltip\" src=\"images/questionmark.png\" width=\"15\"/>";

						}
					}else if ((lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneColonia)){
						ragione_sociale = thisOrg.getRagioneSociale() + " (Protocollo nr: " +  (((ColoniaInformazioni) lp).getNrProtocollo()) +")" ;
					}else if ((lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneImportatore || lp.getIdRelazioneAttivita() == LineaProduttiva.IdAggregazioneOperatoreCommerciale) &&
						lp.getDataFine() != null){
						ragione_sociale =thisOrg.getRagioneSociale(); chiuso="<font color='red'><b> - CHIUSO</b></font>";
					}else{
						ragione_sociale = thisOrg.getRagioneSociale();
					}
					%>
					<dhv:evaluate if="<%=User.getRoleId() != new Integer(ApplicationProperties.getProperty("ID_RUOLO_LLP"))%>">
						<dhv:evaluate if="<%= isPopup(request) %>">
						<td nowrap> <a onclick="window.open('OperatoreAction.do?command=Details&opId=<%=lp.getId()%>&popup=<%= isPopup(request) %>','','width=800,height=500,left=400,top=200');return false;" href="OperatoreAction.do?command=Details&opId=<%=lp.getId()%>&popup=<%= isPopup(request) %>"><%= toHtml(ragione_sociale) %></a><%=chiuso%><%=canile_bloccato%>
				    	</dhv:evaluate>
				    	<dhv:evaluate if="<%= !isPopup(request) %>">
				    	<td nowrap> <a href="OperatoreAction.do?command=Details&opId=<%=lp.getId()%>&popup=<%= isPopup(request) %>"><%= toHtml(ragione_sociale) %></a><%=chiuso%><%=canile_bloccato%>
				    	</dhv:evaluate>
			    	</dhv:evaluate>
			    	<dhv:evaluate if="<%=User.getRoleId() == new Integer(ApplicationProperties.getProperty("ID_RUOLO_LLP"))%>">
			    		<td nowrap> <%= toHtml(ragione_sociale) %><%=chiuso%><%=canile_bloccato%>
			    	</dhv:evaluate>
			       	</td>
				    <td nowrap>  <%=toHtml(tipologiaList.getSelectedValue(lp.getIdAttivita()))%>
					</td>
					<td nowrap>
			    	<%= ( (lp.getIdRelazioneAttivita() != LineaProduttiva.idAggregazionePrivato) ?  toHtml(thisOrg.getPartitaIva()) : " -- ") %>
			       </td>
			    	<td nowrap>
			       	<%= toHtml(thisOrg.getCodFiscale()) %>
					</td>
			  		<td nowrap>
					<%=thisStab.getSedeOperativa().toString() %>
					</td> 
					<td nowrap>
			      	<%=toDateasString(thisOrg.getEntered())%>
					</td>
					<td nowrap>
					<%
					if(lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneCanile){
						String colorFont = "green";
						if(thisOrg.getIndice()>=80 && thisOrg.getIndice()<100)
							colorFont="orange";
						else if(thisOrg.getIndice()>=100){
							colorFont="red";
						}
						String indice ="" + new BigDecimal(thisOrg.getIndice()).setScale(2 , BigDecimal .ROUND_UP).doubleValue();
						colorFont = "green";
						double occupazioneTotale     = 0;		
						if(lp!=null)
							occupazioneTotale     = (((CanileInformazioni) lp).getMqDisponibili());
						double percentuale = 0;
						if(occupazioneTotale>0 && thisOrg.getOccupazioneAttuale()>0)
							percentuale = (thisOrg.getOccupazioneAttuale()/occupazioneTotale)*100;
						percentuale = new BigDecimal(percentuale).setScale(2 , BigDecimal .ROUND_UP).doubleValue();
						if(percentuale>=80 && percentuale<100)
							colorFont="orange";
						else if(percentuale>=100)
							colorFont="red";
					%>
					<% if(thisOrg.getOccupazioneAttuale()>0){ %>
						<b><font color="<%=colorFont%>"><%=thisOrg.getOccupazioneAttuale()%> mq <%if(percentuale >0){ out.println(" (" + percentuale + " %)");} %></font></b>
					<% }else{ %>
					<b><font color='red'>N.D.</font></b>
					<%} 
					}else{
						out.print("N.D.");
					} %>
					</td>
					
					<td nowrap>
<%
					if(lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneCanile)
					{
						String colorFont = "green";
						double limiteNumeroCaniVivi     = Integer.parseInt(ApplicationProperties.getProperty("limite_numero_cani_vivi"));		
						double percentuale = 0;
						if(limiteNumeroCaniVivi>0 && thisOrg.getNumeroCaniVivi()>0)
							percentuale = (thisOrg.getNumeroCaniVivi()/limiteNumeroCaniVivi)*100;
						percentuale = new BigDecimal(percentuale).setScale(2 , BigDecimal .ROUND_UP).doubleValue();
						if(percentuale>=80 && percentuale<100)
							colorFont="orange";
						else if(percentuale>=100)
							colorFont="red";
					%>
					<% if(thisOrg.getNumeroCaniVivi()>0){ %>
						<b><font color="<%=colorFont%>"><%=thisOrg.getNumeroCaniVivi()%> <%if(percentuale >0){ out.println(" (" + percentuale + " %)");} %></font></b>
					<% }else{ %>
					<b><font color='red'>N.D.</font></b>
					<%} 
					}else{
						out.print("N.D.");
					} %>
					</td>

			  	</tr>
			<%	}
			  GestoreConnessioni.freeConnection(conn);	
			  }
	    }
		}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="<%= SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId").equals(String.valueOf(Constants.INVALID_SITE))?columnCount+1:columnCount %>">
      <dhv:label name="accounts.search.notFound">No accounts found with the specified search parameters.</dhv:label><br />
      <!-- <a href="OperatoreAction.do?command=SearchForm&tipologiaSoggetto=1&popup=true&idLineaProduttiva1=<%=OrgList.getIdLineaProduttiva()[0] %>&idAsl=<%=OrgList.getIdAsl() %>&tipoRegistrazione=<%=(String)request.getAttribute("tipoRegistrazione") %>"><dhv:label name="accounts.accounts_list.ModifySearch">Modify Search</dhv:label></a>.-->
       <!--  edit: per permettere di cambiare linea produttiva in caso di ricerca fallita. Reso dinamico parametro Popup -->
       <% if(request.getAttribute("in_regione")!= null){ %>
       <a href="OperatoreAction.do?command=SearchForm&idLineaProduttiva1=<%=(String)request.getAttribute("idLineaProduttiva1") %>&tipologiaSoggetto=<%=(String)request.getAttribute("TipologiaSoggetto") %>&popup=<%=isPopup(request)%>&idAsl=<%=OrgList.getIdAsl() %>&tipoRegistrazione=<%=(String)request.getAttribute("tipoRegistrazione")%>&in_regione=<%=(String)request.getAttribute("in_regione")%>"><dhv:label name="accounts.accounts_list.ModifySearch">Modify Search</dhv:label></a>.       
       <% }else{ %>
       <a href="OperatoreAction.do?command=SearchForm&idLineaProduttiva1=<%=(String)request.getAttribute("idLineaProduttiva1") %>&tipologiaSoggetto=<%=(String)request.getAttribute("TipologiaSoggetto") %>&popup=<%=isPopup(request)%>&idAsl=<%=OrgList.getIdAsl() %>&tipoRegistrazione=<%=(String)request.getAttribute("tipoRegistrazione") %>"><dhv:label name="accounts.accounts_list.ModifySearch">Modify Search</dhv:label></a>.
	   <% } %>
    </td>
  </tr>
<%}%>
</table>
<br />
<dhv:pagedListControl object="SearchOrgListInfo" tdClass="row1"/>
