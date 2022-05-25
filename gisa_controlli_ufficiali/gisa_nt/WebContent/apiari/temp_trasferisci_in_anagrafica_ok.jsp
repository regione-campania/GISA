<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<center>

<form method="post" id="form_hidden_trasferimento" action="InterfValidazioneRichieste.do?command=ValidaEConvergi">
	
	<input type="hidden" name="codice_regionale" value="${codice_regionale}"> 
	<input type="hidden" name="pIvaImpresa" value="${pIvaImpresa}"/>
	<input type="hidden" name="codiceFiscaleImpresa" value="${codiceFiscaleImpresa}"> 
	<input type="hidden" name="idRichiesta" value="${idRichiesta}"/>
	<input type="hidden" name="idTipoRichiesta" value="${idTipoRichiesta}"> 
	<input type="hidden" name="statoValidazione" value="${statoValidazione}"/>
	<input type="hidden" name="idLinea" value="${idLinea}"/>
	<input type="hidden" name="idTipoLinea" value="${idTipoLinea}"/>
	<input type="hidden" name="codiciNazionali" value="${codiciNazionali}"/>
	
</form>
	
</center>

<script>

	loadModalWindowCustom('ATTENDERE PREGO');
	document.getElementById("form_hidden_trasferimento").submit();

</script>