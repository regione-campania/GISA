<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<script>

function checkForm() {
	
	 formTest = true;
	 message = "";
	 if (document.getElementById('dataChiusuraDef').value == '') {
	     message += label("","- Controllare che la data sia stata settata\r\n");
	      formTest = false;
	 }
	 
	 if (formTest == false) {
	      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
	      return false;
	    } else {
	      return true;
	    }
	 
}
</script>

<table class="trails" cellspacing="0">
<tr>
<td>
Attenzione! Specificare la data prima di chiudere l'allerta
</td>
</tr>
</table>

<form method="post" action="TroubleTicketsAllerteNew.do?command=SaveData&id=<%=request.getParameter("id") %>" name="viewData">
<input type="hidden" name = "chiusuraUfficio" id = "chiusuraUfficio" value="">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
   <tr>
    <th colspan="2">
      <strong><dhv:label name="">Data definitiva di trasmissione dati</dhv:label></strong> 
    </th>
  </tr>
   	<tr class="containerBody">
	 <td nowrap class="formLabel">
      	<dhv:label name="">Data</dhv:label>
	 </td>
	 <td>
		<input readonly type="text" id="dataChiusuraDef" name="dataChiusuraDef" value = "" size="10" />
		<a href="#" onClick="cal19.select(document.viewData.dataChiusuraDef,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
	 </td>
	</tr>
	
</table>		
<input type = "submit" onclick="return checkForm();" value = "Salva">
	</form>