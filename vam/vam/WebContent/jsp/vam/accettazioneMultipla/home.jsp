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

<script language="JavaScript" type="text/javascript" src="js/vam/accettazioneMultipla/add.js"></script>
<script language="JavaScript" type="text/javascript" >
	var mcSelezionati = 2;
</script>

<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/Test.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>

<script>
function openPopup(url){
	
	  var res;
      var result;
      
      	  window.open(url,'popupSelect',
            'height=1280px,width=600px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');

	}
</script>

<img src="images/pdf_icon.png" width="20"/>  <a href="#" onClick="openPopup('documentazione/HELP Accettazione Multipla.pdf')"> <font size="3px">Manuale utente</font></a>

<fieldset>
	<legend>Elenco Microchip da Anagrafare</legend>
	<form name="form" id="form" action="vam.accettazioneMultipla.ToAddAnimale.us" method="post">
	<table id="tableAggiungiMc">
		<tr id="trMc1">
			<td>
				<label id="label1" name="label1">1.</label>
				<input type="text" name="microchip1" id="microchip1" maxlength="15" /> 	
			</td>
			<td>
				<input type="button" name="aggiungiMc1" id="aggiungiMc1" value="Aggiungi altro microchip" onclick="javascript:aggiungiMc('2');" />
			</td>
		</tr>
		<tr id="trAggiungiMc2">
			<td id="td1AggiungiMc2">
				<label id="label2" name="label2">2.</label>
				<input type="text" name="microchip2" id="microchip2" maxlength="15" /> 	
			</td>
			<td id="td2AggiungiMc2">
			</td>
		</tr>
	</table>
		
	</form>
</fieldset>
<br/>
<input type="button" value="Prosegui" onclick="checkForm();" />
		
		
<br/>





<script>
dwr.engine.setErrorHandler(errorHandler);
dwr.engine.setTextHtmlHandler(errorHandler);

function errorHandler(message, exception){
    //Session timedout/invalidated

    if(exception && exception.javaClassName== 'org.directwebremoting.impl.LoginRequiredException'){
        alert(message);
        //Reload or display an error etc.
        window.location.href=window.location.href;
    }
    else
        alert('Errore Nella Chiamata Remota : '+exception.javaClassName);
 }
</script>
