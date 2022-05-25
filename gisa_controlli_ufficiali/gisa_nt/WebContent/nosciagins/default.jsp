<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<table class="trails" cellspacing="0">
<tr>
	<td>
		<a href="AltriStabilimenti.do?command=Default">ALTRI STABILIMENTI </a> 
		> GESTIONE ANAGRAFICHE NO-SCIA 
	
	</td>
</tr>
</table>


<h2>Selezionare la linea di attività</h2>
<form>
	<select id="linee" name="linee" style="max-width: 40%;">
		<c:forEach items="${listLinee}" var="linee">
			<option value="${linee.codice_univoco_ml}">${linee.desc_linea}</option>
		</c:forEach>
	</select> <input type="button" value="Avanti" onclick="getTemplate()">

</form>


<script>
function getTemplate() {
	loadModalWindow();
	   var selector = document.getElementById('linee');
	   var value = selector[selector.selectedIndex].value;

       var link = 'GisaNoSciaGINS.do?command=Choose&codice_univoco_ml='+value;
       window.location.href=link;
         

	   }
</script>


