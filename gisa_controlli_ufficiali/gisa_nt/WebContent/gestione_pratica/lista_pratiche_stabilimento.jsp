<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="ListaPratiche" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope = "request"/>
<jsp:useBean id="ListaTipoRichieste" class="java.util.ArrayList" scope="request"/>

<%@ page import="org.aspcfs.modules.gestionepratiche.base.*" %>
<%@ page import="org.aspcfs.modules.gestionepratiche.actions.*" %>
  
  <%@ include file="../initPage.jsp" %>
  
  <script>
  function openPopup(url){
	  window.open(url,'popupSelect',
	         'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');

		}
  </script>
  
  <%
String nomeContainer = "gestioneanagrafica";
request.setAttribute("Operatore",StabilimentoDettaglio.getOperatore());
String param = "stabId="+StabilimentoDettaglio.getIdStabilimento();
%>

<dhv:container name="<%=nomeContainer %>"  selected="storicopratiche" object="Operatore" param="<%=param%>"  hideContainer="false">


<%-- Trails --%>
	<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="OpuStab.do?command=SearchForm">ANAGRAFICA STABILIMENTI</a> > 
			<a href="GestioneAnagraficaAction.do?command=Details&altId=<%=StabilimentoDettaglio.getAltId()%>"> SCHEDA</a> > 
			Storico Pratiche amministrative
		</td>
	</tr>
	</table>
<%-- Trails --%>


<br>

<center>
<b>Lista pratiche</b><br/>
<i>In questa pagina sono elencate le pratiche eseguite su questo stabilimento.</i>
<br><br>
<table class="table details" id="tabella_pratiche" style="border-collapse: collapse" width="100%" cellpadding="5"> 
<tr>
<th style="text-align:center"><p>Data inserimento</p></th>
<th style="text-align:center"><p>Numero pratica</p></th>
<th style="text-align:center"><p>Comune</p></th>
<th style="text-align:center"><p>DATA PEC / DATA SCIA</p></th>
<th style="text-align:center"><p>tipo pratica</p></th>
<th style="text-align:center"><p>Utente</p></th> 
<th style="text-align:center"><p>ALLEGATI</p></th> 
</tr>
<% int j=0; 
for ( int i = 0; i<ListaPratiche.size(); i++) {

	Pratica p = (Pratica) ListaPratiche.get(i);
	String tipoPratica = "";
	for (int z = 0; z < ListaTipoRichieste.size(); z++){
		Richiesta r = (Richiesta) ListaTipoRichieste.get(z);
		if(p.getIdTipologiaPratica() == r.getCode()) {
			tipoPratica = r.getLong_description();
		}
	}
	
	%>
<tr class="row<%=j%2%>">

<td align="center"><%=toDateasString(p.getDataInserimentoPratica()) %></td>

<td align="center"><p><%=p.getNumeroPratica() %></p></td>

<td align="center"><%=p.getComuneRichiedente() %></td>
<td align="center"><%=toDateasString(p.getDataOperazione()) %></td>
<td align="center"><%=tipoPratica %></td>
<td align="center"> <dhv:username id="<%= p.getIdUtente() %>" /></td> 
<td align="center">
	<%String desc_operatore = "";
	  desc_operatore = "<hr>DATI IMPRESA E STABILIMENTO<br>" +
				  "<br><b>RAGIONE SOCIALE</b>: " + p.getRagioneSociale() + 
				  "<br><b>PARTITA IVA/CODICE FISCALE</b>: " + p.getPartitaIva() + 
				  "<br><b>NUMERO REGISTRAZIONE</b>: " + p.getNumeroRegistrazione() +
				  "<br><b>INDIRIZZO</b>: " + p.getIndirizzo();
	  desc_operatore = desc_operatore.replaceAll("&", "%26");
	%>
	<a style="text-decoration:none" 
	href='GestioneAllegatiGins.do?command=ListaAllegati&numeroPratica=<%=p.getNumeroPratica()%>&desc_operatore=<%=desc_operatore.replaceAll("'", " ")%>&alt_id=<%=p.getAltId()%>&stab_id=<%=p.getIdStabilimento()%>&idComunePratica=<%=p.getIdComuneRichiedente()%>'>
	
		<img src="gestione_documenti/images/archivio_icon.png" width="35" title="visualizza allegati"/>
	</a>&nbsp;&nbsp;
	
</td>
</tr>

<% j++;
}
%>
</table>

  		
</dhv:container>
  		

  	
 