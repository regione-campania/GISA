<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.suap.base.Stabilimento"%>
<%@page import="org.aspcfs.modules.suap.base.CodiciRisultatoFrontEnd"%>
<%@page import="org.aspcfs.modules.suap.base.RisultatoValidazioneRichiesta"%>
<%@page import="java.util.HashMap"%>
<jsp:useBean id="EsitoValidazione" class="org.aspcfs.modules.suap.base.RisultatoValidazioneRichiesta" scope="request"/>

<%
if(EsitoValidazione.getIdRisultato()==CodiciRisultatoFrontEnd.SCIA_VALIDAZIONE_SCELTA_CANDIDATO ||
EsitoValidazione.getIdRisultato()==CodiciRisultatoFrontEnd.SCIA_VALIDAZIONE_CARICA_IMPORT_MULTIPO)
{
	%>
	<table cellpadding="8" cellspacing="0" border="0" width="100%" class="pagedList">
	<tr>
					<%if(EsitoValidazione.getIdRisultato()==CodiciRisultatoFrontEnd.SCIA_VALIDAZIONE_SCELTA_CANDIDATO ){ %>
					<th>&nbsp;</th>
					<%} %>
					<th>ID STAB.</th>
					<th>RAGIONE SOCIALE</th>
					<th>P.IVA</th>
					<th>CF</th>
					<th>N. REG</th>
					<th>COMUNE LEGALE</th>
					<th>IND. LEGALE</th>
					<th>COMUNE SEDE OP.</th>
					<th>IND. SEDE OP.</th>
				</tr>
	
	<%
	
	HashMap<Integer,Stabilimento> candidati = EsitoValidazione.getListaAnagraficheCandidate(); 
				  boolean primo = true;
				  for(Integer idStab : candidati.keySet() )
				  {
					    Stabilimento candidato = candidati.get(idStab);
					    
				  		String ragioneSociale = candidato.getRagioneSociale();
				  		String partitaIva = candidato.getPartitaIva();
				  		String cfRappresentante = candidato.getCfRappresentante();
				  		String numeroRegistrazione = candidato.getNumeroRegistrazione();
				  		String comuneSedeLegale = candidato.getComuneSedeLegale();
				  		String indirizzoSedeLegale = candidato.getIndirizzoSedeLegale();
				  		String comuneSedeOperativa = candidato.getComuneSedeOperativa();
				  		String indirizzoSedeOperativa = candidato.getIndirizzoSedeOperativa();
%>

<tr>

<%if(EsitoValidazione.getIdRisultato()==CodiciRisultatoFrontEnd.SCIA_VALIDAZIONE_SCELTA_CANDIDATO ){ %>

<td>
<input type="radio" name="candidato_scelto" value="<%=idStab%>" descrizione="<%=ragioneSociale %>" <%=primo ? "checked=\"checked\"" : ""%>>
</td>
<%} %>
<td><%=idStab%></td>
<td><%=ragioneSociale%></td>
<td><%=partitaIva%></td>
<td><%=cfRappresentante%></td>
<td><%=numeroRegistrazione%></td>
<td><%=comuneSedeLegale%></td>
<td><%=indirizzoSedeLegale%></td>
<td><%=comuneSedeOperativa%></td>
<td><%=indirizzoSedeOperativa%></td>
</tr>
<%
primo=false;
}
				  %>
				  </table>
				  <% 
}
%>









