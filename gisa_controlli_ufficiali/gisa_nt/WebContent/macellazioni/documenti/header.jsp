<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<!--  INIZIO HEADER -->
<div class="header">
<TABLE cellpadding="2" style="border-collapse: collapse;table-layout:fixed;" width="100%">
 <col width="25%">
<col width="35%">
<col width="40%"> 
<TR>
<Td style="border:1px solid black;"><table cellpadding="5" ><tr><td><div class="boxIdDocumento"></div><br/><center><b>REGIONE<br> CAMPANIA</b></center> </td><td><img style="text-decoration: none;" height="80" width="80" documentale_url="" src="gestione_documenti/schede/images/<%=(OrgOperatore.getAsl()!=null) ? OrgOperatore.getAsl().toLowerCase() : ""%>.jpg" /></td></tr></table>
</Td>
<TD style="border:1px solid black;"><b>TAMPONE EFFETTUATO PER:</b><br> 
<label class="layout"><%= (OrgTamponeMacelli.getDescrizionePianoMonitoraggio() != null) ? OrgTamponeMacelli.getDescrizionePianoMonitoraggio().toUpperCase() : "" %></label>
</TD>									
<TD  style="border:1px solid black;" >
<center>
VERBALE<br>PRELIEVO<br>CAMP. DI SUPERFICIE<br>DA CARCASSA N.
<br>
<br>&nbsp;&nbsp;<img src="<%=createBarcodeImage(OrgTamponeMacelli.getBarcodePrelievo())%>" />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>
</center>
</TD>
</TR> </table>
<TABLE cellpadding="2" style="border-collapse: collapse;table-layout:fixed;" width="100%">
 <col width="90%">
<col width="10%">
<tr><TD style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;"><center><b>DIP. DI PREVENZIONE</b></center>
SERVIZIO <input class="editField" type="text" name="servizio" id="servizio"  value="<%=valoriScelti.get(z++) %>" size="20" maxlength=""/><br>
U.O. <input class="editField" type="text" name="uo" id="uo"  value="<%=valoriScelti.get(z++) %>" size="20" maxlength="" /><BR>
SEDE <input class="editField" type="text" name="via_amm" id="via_amm"  value="<%=valoriScelti.get(z++) %>"  size="20" maxlength="" /></br>
MAIL <input class="editField" type="text" name="mail" id="mail"  value="<%=valoriScelti.get(z++) %>" size="20" maxlength="" /></TD>


<TD style="border:1px solid black;"><center><b>&nbsp; MOD <%=request.getParameter("tipo")%> &nbsp;</b><BR>
&nbsp; Rev. 6 del &nbsp; <BR>
&nbsp; 25/03/13&nbsp;
</TD>
</tr>
</table>
</div>

<!-- FINE HEADER -->