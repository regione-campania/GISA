<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants" %>



<%@page import="java.sql.Timestamp"%><jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>


<table class="trails" cellspacing="0">
<tr>
<td>
<a href="OperatoreAction.do"><dhv:label name="opu.operatore"></dhv:label></a> > 

<a href="OperatoreAction.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="OperatoreAction.do?command=Details&opId=<%=StabilimentoDettaglio.getIdOperatore() %>"><dhv:label name="opu.operatore.scheda"></dhv:label></a> >
<dhv:label name="opu.stabilimento.scheda"></dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>

<%
	String param = "idOp=" + StabilimentoDettaglio.getIdOperatore();
%>
<dhv:container name="anagrafica"  selected="" object="StabilimentoDettaglio" param="<%=param%>"  hideContainer="false">

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="opu.stabilimento.sede"></dhv:label></strong>
	  </th>
 </tr> 
 <dhv:evaluate if="<%=(AslList.size() > 0)%>">
	<tr>
			<td class="formLabel" nowrap><dhv:label name="opu.stabilimento.asl"></dhv:label>
			</td>
			<td><%=AslList.getSelectedValue(StabilimentoDettaglio
									.getIdAsl())%></td>
	</tr>
		</dhv:evaluate>
 <dhv:evaluate if="<%=(StabilimentoDettaglio.getSedeOperativa() != null)%>">
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.stabilimento.sede"></dhv:label>
    </td>
    <td>
    
    <%
        	String comune = ComuniList.getSelectedValue(StabilimentoDettaglio.getSedeOperativa().getComune());
        					
        			String via = (StabilimentoDettaglio.getSedeOperativa()
        					.getVia() != null) ? StabilimentoDettaglio
        					.getSedeOperativa().getVia() : "";
        %>

      <%=comune + ", " + via%>
    </td>
  </tr>
 </dhv:evaluate>

 </table>

<br><br>
<dhv:evaluate if="<%= (StabilimentoDettaglio.getRappLegale() != null && StabilimentoDettaglio.getRappLegale().getCodFiscale() != null)  %>">
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">

  <tr>
    <th colspan="2">
      <strong><dhv:label name="opu.stabilimento.soggetto_fisico"></dhv:label></strong>
      
    </th> 
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.stabilimento.soggetto_fisico.ragione_sociale"></dhv:label>
    </td>
    <td>
      <%=StabilimentoDettaglio.getRappLegale().getCognome() + " " + StabilimentoDettaglio.getRappLegale().getNome() %>
    </td>
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.stabilimento.soggetto_fisico.cf"></dhv:label>
    </td>
    <td><%=StabilimentoDettaglio.getRappLegale().getCodFiscale() %></td>
    </tr>
</table>
 </dhv:evaluate>
<br/>

 
<!-- LINEE PRODUTTIVE -->
<br/><br/>
<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="details">
	<tr>
		<th colspan="6"><strong><dhv:label name="opu.stabilimento.linea_produttiva"></dhv:label></strong>
		</th>
	</tr>
	<tr>
		<th style="background-color: rgb(204, 255, 153);"><strong><dhv:label name="opu.stabilimento.linea_produttiva.categoria"></dhv:label></strong>
		</th>
		<th style="background-color: rgb(204, 255, 153);"><strong><dhv:label name="opu.stabilimento.linea_produttiva.sottocategoria"></dhv:label></strong>
		</th>
		<th style="background-color: rgb(204, 255, 153);"><strong><dhv:label name="opu.stabilimento.linea_produttiva.tipologia"></dhv:label></strong>
		</th>
		<th style="background-color: rgb(204, 255, 153);"><strong><dhv:label name="opu.stabilimento.linea_produttiva.dal"></dhv:label></strong>
		</th>
		<th style="background-color: rgb(204, 255, 153);"><strong><dhv:label name="opu.stabilimento.linea_produttiva.al"></dhv:label></strong>
		</th>
		<th style="background-color: rgb(204, 255, 153);"><strong><dhv:label name="opu.stabilimento.linea_produttiva.stato"></dhv:label></strong>
		</th>
	</tr>
	
	<dhv:evaluate if="<%=(StabilimentoDettaglio.getListaLineeProduttive()
									.size() > 0)%>">
	<%
		Iterator<LineaProduttiva> itLplist = StabilimentoDettaglio
						.getListaLineeProduttive().iterator();
				int indice = 1;
				while (itLplist.hasNext()) {
					LineaProduttiva lp = itLplist.next();
	%>
		<input type="hidden" name="idLineaProduttivaAdded_Stabilimento"
			id="idLineaProduttivaAdded_Stabilimento<%=indice%>"
			value="<%=lp.getId()%>">
			<tr>
				<td ><%=lp.getMacrocategoria() %></td>
				<td><%=lp.getCategoria()%></td>
				<td><%=lp.getAttivita()%></td>
				<td><%=(lp.getDataInizioasString()!=null && ! lp.getDataInizioasString().equals("")) ? lp.getDataInizioasString() : "&nbsp;"%></td>
				<td><%=(lp.getDataFineasString()!=null && ! lp.getDataFineasString().equals("")) ? lp.getDataFineasString() : "&nbsp;"%></td>
				
			
				<td>
					<%if(("".equals(lp.getDataFineasString()) || lp.getDataFineasString() == null) || (lp.getDataFine() != null && lp.getDataFine().after(new Timestamp(System.currentTimeMillis()))))
					{%>
						<img src="images/icons/pallino_verde.JPG" border="0"  title="LINEA DI PRODUZIONE ATTIVA DAL  <%=lp.getDataInizioasString() %>"">
					<%}
					else
					{
						%>
					<img src="images/icons/pallino_rosso.JPG" border="0"  alt="LINEA DI PRODUZIONE CESSATA IN DATA <%=lp.getDataFineasString() %>"">
						
						<%						
					}%>
				</td>
			</tr>
			<%
				}
			%>
	</dhv:evaluate>
	
	
</table>
</dhv:container>
 