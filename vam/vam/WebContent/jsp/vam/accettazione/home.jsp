<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@page import="org.jmesa.view.html.editor.DroplistFilterEditor"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="org.jmesa.core.filter.FilterMatcher"%>

<fieldset>
	<legend>Nuova Accettazione</legend>
	<form id="newAccettazione" action="vam.accettazione.FindAnimale.us" method="post">
		<font size="+1">Identificativo Animale:</font> 
		<input type="text" name="identificativo" id="identificativo" maxlength="15" /> 
		<input type="submit" value="Prosegui" onclick="if(document.getElementById('identificativo').value.length>0 && !Controlla(document.getElementById('identificativo').value)){attendere();return true;}else{alert('Inserire un identificativo alfanumerico');return false;}" />
		<input type="button" value="Animale Deceduto senza Identificativo" onclick="attendere(),location.href='vam.accettazione.ToAddDecedutoNonAnagrafe.us'" />
	</form>
</fieldset>

<fieldset>
	<legend>Ricerca Accettazione</legend>
	<form id="searchAccettazione" action="vam.accettazione.FindAccettazione.us" method="post">
		<font size="+1">Numero Accettazione:</font> 
		<input type="text" name="numeroAccettazione" id="numeroAccettazione" /> 
		<input type="submit" value="Prosegui" onclick="if($('#numeroAccettazione')[0].value.length > 0){attendere();return true;}else{alert('Inserire il numero dell\'accettazione');return false;}" />
	</form>	
	<form id="searchAccettazioneByMc" action="vam.accettazione.FindAccettazioneByMc.us" method="post">
		<font size="+1">Microchip:</font> 
		<input type="text" name="mc" id="mc" /> 
		Cerca in tutte le cliniche
		<select id="ricercaAllCliniche" name="ricercaAllCliniche">
			<option value="No">No</option>
			<option value="Si">Si</option>
		</select>
		<input type="submit" value="Prosegui" onclick="if($('#mc')[0].value.length > 0){attendere();return true;}else{alert('Inserire il microchip');return false;}" />
	</form>
	<input type="button" value="Lista di tutte le Accettazioni della clinica" onclick="attendere(),location.href='vam.accettazione.ListClinica.us'" />
</fieldset>
<br/>



<script type="text/javascript">
function Controlla(stringa) 
{
	var myregexp = /^[a-zA-Z0-9]+$/;
	if (myregexp.test(stringa) == false)
	{
		return true;
	}
	else
	{
		return false;
	}
}
</script>