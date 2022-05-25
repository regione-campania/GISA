<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<!--  INIZIO HEADER -->
<div class="header">
<TABLE  cellpadding="10" width="100%" style="border-collapse: collapse">
 <col width="10%">
<col width="20%">
<col width="30%">
<col width="10%">
<col width="30%"> 
<TR>
<Td style="border: 1px solid black;"><div class="boxIdDocumento"></div><br/> <b><center>REGIONE<br> CAMPANIA</center></b></Td>
<TD style="border: 1px solid black;"><center><b>DIP. DI PREVENZIONE</b></center><BR>
SERVIZIO <input class="editField" type="text"  name="servizio" id="servizio"  value="<%=valoriScelti.get(z++) %>" size="20" maxlength=""/><br>
U.O. <input class="editField" type="text" name="uo" id="uo"  value="<%=valoriScelti.get(z++) %>" size="20" maxlength="" /><BR>
SEDE <input class="editField" type="text" name="via_amm" id="via_amm"  value="<%=valoriScelti.get(z++) %>"  size="20" maxlength="" /></br>
MAIL <input class="editField" type="text" name="mail" id="mail"  value="<%=valoriScelti.get(z++) %>" size="20" maxlength="" /></TD>
<TD style="border: 1px solid black;"><b>CAMPIONE EFFETTUATO PER:</b><br> 
<input class="layout" type="text" size="30" value="<%= (OrgTampone.getPiano() != null) ? OrgTampone.getPiano().toUpperCase() : "" %>" />
</TD>									
<TD style="border: 1px solid black;"><b>&nbsp; MOD <%=request.getParameter("tipo")%> &nbsp;</b><BR>
&nbsp; Rev. 6 del &nbsp; <BR>
&nbsp; 25/03/13&nbsp;
</TD>
<TD  style="border: 1px solid black;" ><center>
VERBALE<br>PRELIEVO<br>CAMPIONE DI SUPERFICIE AMBIENTALE N.
<br>
<br>&nbsp;&nbsp;&nbsp;&nbsp;
<img src="<%=createBarcodeImage(OrgTampone.getBarcodePrelievo())%>" />
&nbsp;&nbsp;&nbsp;&nbsp;<br>
</center>
</TD>
</TR>
</table>
</div>
<!-- FINE HEADER -->