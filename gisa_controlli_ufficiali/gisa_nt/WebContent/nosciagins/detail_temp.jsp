<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
function detailNoSciaInserito(value) {
		loadModalWindow();
		var link = 'GestioneAnagraficaAction.do?command=Details&stabId='+value;
		//var link = 'OpuStab.do?command=Details&stabId='+value;
		   window.location.href=link;
   	}
</script>


<h2>stato operazione</h2>
<tr>
	<td>  
		<label id="inserimento_linea" name="inserimento_linea" >operazione in corso</label>    
		<input type="hidden" id="idStab" name="idStab" value="${id_stabilimento}"> 
	</td>
</tr>


<script>
  var idStabilimento = document.getElementById("idStab").value; 
  detailNoSciaInserito(idStabilimento);
</script>
