<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script language="JavaScript" TYPE="text/javascript" SRC="opu/to_add_stabilimento.js"></script>



<table>

<tr>


<td colspan="2" align= "center">
<input type="button" class="darkGreyBigButton" value="Gestione SCIA" onClick="showHide('reg1')">
</td>

<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>

<td colspan="2" align="center">
<input type="button" class="darkGreyBigButton" value="Gestione Riconoscimento" onClick="goTo('OpuStab.do?command=Add&tipoInserimento=2&stato=3')">
</td>

</tr>

<tr>

<td name="reg1" style="visibility:hidden">
<input type="button" class="lightGreyBigButton" value="Gestione attività fissa"  onClick="goTo('OpuStab.do?command=Add&tipoInserimento=1&stato=7&fissa=true')">
</td>

<td name="reg1" style="visibility:hidden">
<input type="button" class="lightGreyBigButton" value="Gestione attività mobile" onClick="goTo('OpuStab.do?command=Add&tipoInserimento=1&stato=7&fissa=false')">
</td>

</tr></table>









