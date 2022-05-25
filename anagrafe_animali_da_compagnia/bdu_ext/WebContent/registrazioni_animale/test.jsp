<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
<meta charset="utf-8" />
<title>jQuery UI Autocomplete - Default functionality</title>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>


<script>



$(document).ready(function(){ 
	var regione = { 
			source: function (request, response) {
	    $.post("../ServletRegioniComuniProvince?tipoRichiesta=1_text", request, response);
	  }, 
			select: function(event, ui){ //alert(ui.item.idRegione);			
			$("#idregione").val(ui.item.idRegione);  }, 
			minLength:3 }; 

	var provincia = { 
			source: function (request, response) {
	    $.post("../ServletRegioniComuniProvince?tipoRichiesta=2_text&idregione="+$("#idregione").val(), request, response);
	  }, 
			select: function(event, ui){ 		
			$("#idprovincia").val(ui.item.idProvincia);  }, 
			minLength:3 }; 


	var comune = { 
			source: function (request, response) {
	    $.post("../ServletRegioniComuniProvince?tipoRichiesta=3_text&idprovincia="+$("#idprovincia").val(), request, response);
	  }, 
			select: function(event, ui){ 		
			$("#idcomune").val(ui.item.idComune);  }, 
			minLength:3 };

	var via = { 
			source: function (request, response) {
	    $.post("../ServletRegioniComuniProvince?tipoRichiesta=4_text&idprovincia="+$("#idprovincia").val()+"&idcomune="+$("#idcomune").val(), request, response);
	  }, 
			select: function(event, ui){ 		
			$("#idindirizzo").val(ui.item.idindirizzo);  }, 
			minLength:3 }; 
	
	$("#regione").autocomplete(regione); 
	$("#provincia").autocomplete(provincia); 
	$("#comune").autocomplete(comune); 
	$("#via").autocomplete(via); 
	}); 

</script>
</head>
<body>
<span id="datireg" class="datireg">
<table id="dati" cellpadding="2" cellspacing="0" border="0" width="100%"class="details"><tr><th colspan="2"><strong>Dati da inserire</strong></th><tr> 
 <td class="formLabel"> Data modifica residenza</td> <td><input  readonly type="text" id="dataModificaResidenza" name="dataModificaResidenza" size="10" value="" nomecampo="dataModificaResidenza" tipocontrollo="undefined" labelcampo="undefined" />&nbsp;<a href="#" onClick="cal19.select(document.forms[0].dataModificaResidenza,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a></td></tr><tr><td class="formLabel"> Destinazione trasferimento</td><td><select size='1' id='idTipologiaSoggettoDestinatarioModifica' name='idTipologiaSoggettoDestinatarioModifica' id='idTipologiaSoggettoDestinatarioModifica'  ><option selected value='-1' >--Seleziona--</option></select></td></tr><tr><td class="formLabel"> Proprietario/Detentore</td><td><select size='1' id='idTipologiaSoggettoDestinatarioModifica' name='idTipologiaSoggettoDestinatarioModifica' id='idTipologiaSoggettoDestinatarioModifica'  ><option selected value='-1' >--Seleziona--</option></select></td></tr><tr><td class="formLabel" nowrap>Regione</td><td><input type = "text" name="regione" id="regione" value="" maxlength="500"  /></td></tr><tr><td class="formLabel" nowrap>Provincia</td><td><input type = "text" name="provincia" id="provincia" value="" maxlength="500"  /></td></tr><tr><td class="formLabel" nowrap>Comune</td><td><input type = "text" name="comune" id="comune" value="" maxlength="500"  /></td></tr><tr><td class="formLabel" nowrap>Via</td><td><input type = "text" name="via" id="via" value="undefined" maxlength="500"  /></td></tr><input type = "hidden" name="idindirizzo" id="idindirizzo" value ="undefined"/><input type = "hidden" name="idcomune" id="idcomune" value ="undefined"/><input type = "hidden" name="idprovincia" id="idprovincia" value =""/><input type = "hidden" name="idregione" id="idregione" value =""/>
</span>
</body>
</html>