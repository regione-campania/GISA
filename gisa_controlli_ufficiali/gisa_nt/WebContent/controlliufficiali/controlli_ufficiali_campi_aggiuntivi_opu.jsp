<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/ControlliUfficialiCampiAggiuntiviOpu.js"></script>

<!-- <tr id="Allev01"  style="display:none"> -->
<tr id="Allev01" style="display:none" class="campiAggiuntiviLinea">
	<td colspan="2" style="padding: 0px;">
		<table cellpadding="4" cellspacing="0" width="100%" class="details">
			<tr id="preavviso" class="containerBody">
				<td class="formLabel" nowrap="nowrap">
					&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Effettuato Preavviso
				</td>
				<td>
					<select id= "flag_preavviso" name = "flag_preavviso" onchange="if(document.getElementById('flag_preavviso').value != '-1'&& document.getElementById('flag_preavviso').value!='N'){document.getElementById('data_preavviso_ba_tr').style.display=''}else{document.getElementById('data_preavviso_ba_tr').style.display='none';document.getElementById('data_preavviso_ba').value='';}">
						<option value = "-1" selected="selected" >Seleziona Voce</option>
						<option value = "N">Nessun Preavviso</option>
						<option value = "P">Telefono</option>
						<option value = "T">Telegramma</option>
						<option value = "A">Altro</option>		
					</select>
				</td>
			</tr>		
			<tr id="data_preavviso_ba_tr" class="containerBody" style="display: none">
				<td class="formLabel" nowrap="nowrap">
					Data Preavviso
				</td>
				<td>
					<input readonly type="text" id="data_preavviso_ba" name="data_preavviso_ba" size="10" />
					<a href="#" onClick="cal19.select(document.forms[0].data_preavviso_ba,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
					<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
				</td>
			</tr>
		</table>
	</td>
</tr>