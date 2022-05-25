<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<!-- RELATIVO AL NUOVO CALENDARIO CON MESE E ANNO FACILMENTE MODIFICABILI -->
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();
cal19.showNavigationDropdowns();
</SCRIPT>
<!-- <script language="JavaScript" TYPE="text/javascript" SRC="mu/mu.js"></script> -->


<%@ page import="java.util.*" %>
<%@page import="org.aspcfs.utils.web.LookupList"%>
<%@page import="org.aspcfs.utils.web.LookupElement"%>

 <jsp:useBean id="specieList"			class="org.aspcfs.utils.web.LookupList" scope="request" />
 
   <jsp:useBean id="specieBovine"			class="org.aspcfs.utils.web.LookupList" scope="request" />
  <jsp:useBean id="razzeBovine"			class="org.aspcfs.utils.web.LookupList" scope="request" />
  <jsp:useBean id="categorieBovine"			class="org.aspcfs.utils.web.LookupList" scope="request" />
  <jsp:useBean id="categorieBufaline"			class="org.aspcfs.utils.web.LookupList" scope="request" />
    <jsp:useBean id="catRischio"			class="org.aspcfs.utils.web.LookupList" scope="request" />
 
 
 <%@ include file="mu_js.jsp"%>
 
 <form name="nuovaPartita" id="nuovaPartita" action="">
<table class="details" layout="fixed" width="50%">
<th colspan="2">Aggiunta nuova partita</th> 
<tr><td class="formLabel">Codice azienda di provenienza</td> <td><input type="text"/></td></tr>
<tr><td bgcolor="yellow">Numero partita</td> <td bgcolor="yellow"><input type="text" id="numero_partita" readonly/>   	<input type="button" id="genera_partita" name="genera_partita" value="Numero automatico" onclick="javascript:generaPartita();" /> </td></tr>
<tr><td class="formLabel">Commerciante/proprietario degli animali</td> <td><input type="text"/></td></tr>
<tr><td class="formLabel">Codice azienda di nascita</td> <td><input type="text"/></td></tr>
<tr><td class="formLabel">Specie capi</td> 
<td>
<table width="100%">
<tr><th> Specie </th> <th> Num. capi </th></tr>

<% Iterator iter = specieList.iterator();
	int i=0;
	while (iter.hasNext()) 
	{
	LookupElement thisElement = (LookupElement) iter.next();
	String specie = thisElement.getDescription(); %>
	
<tr><td><%= specie%></td> <td> <input <%=(specie.equals("Bovini")) ? "readonly" : "" %> type="text" id="num<%=specie %>" size="3" value="0"/>  <input <%=(specie.equals("Bovini")) ? "disabled" : "" %> type="button" value="-" onClick="gestisciNumCapi('num<%=specie%>', '-')"/> <input type="button" value="+" onClick="gestisciNumCapi('num<%=specie%>', '+')"/> </td></tr>
 
 <%} %>

</table></td></tr> </table>

<table class="details" layout="fixed"  width="100%">
<tr><td colspan="2">
<div id="datiBovini"></div> 
</td></tr>  
</table>

<table class="details" layout="fixed"  width="50%">
<tr><td class="formLabel">Vincolo sanitario</td> <td> <input type="checkbox"/> MOTIVO <input type="text"/></td></tr>
<tr><td class="formLabel">Mod 4</td> <td><input type="text"/></td></tr>
<tr><td class="formLabel">Data mod 4</td> <td><input type="text"/></td></tr>
<tr><td class="formLabel">Macellazione differita</td> <td><select>  <option value="volvo">--------</option></select></td></tr> 
<tr><td class="formLabel">Disponibili info catena alimentare</td> <td> <input type="checkbox"/></td></tr>
<tr><td class="formLabel">Data di arrivo al macello</td> <td><input type="text"/>  <input type="checkbox"/> Data dichiarata dal gestore</td></tr>
<th colspan="2">Identificazione mezzo di trasporto</th> 
<tr><td class="formLabel">Tipo</td> <td><input type="text"/></td></tr>
<tr><td class="formLabel">Targa</td> <td><input type="text"/></td></tr>
<tr><td class="formLabel">Trasporto superiore a 8 ore</td> <td> <input type="checkbox"/></td></tr>
<th colspan="2">Veterinari addetti al controllo</th> 
<tr><td class="formLabel">1. </td> <td><select>  <option value="volvo">--------</option></select></td></tr>
<tr><td class="formLabel">2. </td> <td><select>  <option value="volvo">--------</option></select></td></tr>
<tr><td class="formLabel">3. </td> <td><select>  <option value="volvo">--------</option></select></td></tr>
</table> 
</form>