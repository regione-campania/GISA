<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>

<div align="left">
	<img src="images/tree0.gif" border="0" align="absmiddle" height="16" width="16" />
	<a href="javascript:popLookupSelectorCustomPuntiSbarco('17,4','<%=OrgDetails.getSiteId()%>');"><dhv:label name="">Seleziona Operatore</dhv:label></a>
</div>
<body onload="javascript:document.getElementById('dim').value=0;">

<table cellpadding="7" cellspacing="0" width="70%" class="details" id="tblClone_ps">
  <tr>
    <th colspan="6">
      <strong><dhv:label name="">Lista Operatori controllati</dhv:label></strong>
    </th>
  </tr>
  <tr>
	<th >Tipologia Operatore</th>
    <th>Nome Impresa/OSA</th>
	<th>Numero<br>registrazione</th>
	
    <th>Numero<br>identificazione<br>barca</th>
	<th>E' attrezzata<br>per conservare<br>prodotti<br>per 24 ore</th>
     
    <th>Azione</th>
	</tr>  
	<tr style="display: none">
	 	<td><p></p>
	 		<input type="hidden" name="org_id_op" id="org_id_op" value="" />
	 	</td>
	 	
	 	<td><p></p>
	 	</td>
	 	
	 	<td><p></p>
	 	</td>
	 	
	 	<td><p></p>
	 	</td>
	 	
	 	<td><p>
	 		<input type="radio" id="conservazione" name="conservazione" value="Si" /> Si<br/>
	 		<input type="radio" id="conservazione" name="conservazione" value="No" checked="true" /> No<br/>
	 	</p>
	 	</td>
	 	
	 	<td>
	 	<p><a href="javascript:eliminaOperatore()" id="elimina">Elimina</a></p>
	 	</td>
	 	
	 </tr>
</table>	
<input type="hidden" name="dim" id="dim" value ="0">
</body>
  