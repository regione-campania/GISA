<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>

<jsp:useBean id="idInvioMassivo" class="java.lang.String" scope="request"/>
<jsp:useBean id="totInvii" class="java.lang.String" scope="request"/>
<jsp:useBean id="totInviiOK" class="java.lang.String" scope="request"/>
<jsp:useBean id="totInviiKO" class="java.lang.String" scope="request"/>
<jsp:useBean id="listaInvii" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="dataDa" class="java.lang.String" scope="request"/>
<jsp:useBean id="dataA" class="java.lang.String" scope="request"/>
<jsp:useBean id="tipoBaSa" class="java.lang.String" scope="request"/>


 <dhv:container name="inviocuba" selected="Report" object="">

<P style="text-align: center; color: red; font-size:14px">
Di seguito verranno mostrati a video i risultati dell'invio delle Checklist tramite WS.<br>
</P>

  <script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>
	
<div align="right">	 <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Genera PDF" value="Genera PDF"		onClick="openRichiestaPDF_LogInviiBASA('<%=idInvioMassivo%>', '<%=dataDa%>', '<%=dataA%>', '<%=tipoBaSa%>');">
        </div>
<br/>

<table class="details" cellpadding="10" cellspacing="10" width="100%">
<tr><th colspan="6">ESITO INVII IN BDN PER  <%= tipoBaSa.equals("ba") ? "BENESSERE ANIMALE" : "SICUREZZA ALIMENTARE" %> DAL <%=dataDa %> al <%=dataA %></th></tr>

<tr>
<td class="formLabel">Totale invii</td> <td><%=totInvii %></td>
<td class="formLabel">Totale invii OK</td> <td><%=totInviiOK %></td>
<td class="formLabel">Totale invii KO</td> <td><%=totInviiKO %></td>
</tr>
</table>

<br/>

<table class="details" cellpadding="10" cellspacing="10" width="100%">

<tr><th>DATA OPERAZIONE</th> <th>ID CONTROLLO</th> <th>DATA CONTROLLO</th> <th>CODICE AZIENDA</th> <th>TIPO CHECKLIST</th> <th>ESITO</th> <th>DESCRIZIONE ERRORE</th> <th>INVIATO DA</th> </tr>

<%for (int i = 0; i<listaInvii.size(); i++) {
	String esitoInvio = (String) listaInvii.get(i);
	String split[] = esitoInvio.split(";;");
	String dataOperazione = split[0];
	String idControllo = split[1];
	String dataControllo = split[2];
	String codiceAzienda = split[3];
	String tipoChecklist = split[4];
	String esito = split[5];
	String descrizioneErrore = split[6];
	String inviatoDa = split [7];

	%>
	
<tr><td><%=dataOperazione %></td>
 <td><%=idControllo %></td> 
  <td><%=dataControllo %> 
  <td><%=codiceAzienda %></td> 
  <td><%= tipoChecklist%></td>
   <td><%=esito %></td> 
   <td><%=descrizioneErrore %></td> 
   <td><dhv:username id="<%= inviatoDa %>" /></td> </tr>
	
<% } %>

</table>


</dhv:container>
