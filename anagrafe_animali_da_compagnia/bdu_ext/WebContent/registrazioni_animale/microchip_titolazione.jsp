<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page
	import="java.io.*,java.util.*,org.aspcfs.utils.web.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.registrazioniAnimali.base.*"%>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />



<meta charset="utf-8" />
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />

<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>


<script language="javascript" SRC="javascript/CalendarPopup.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/dateControl.js"></script>


<script type="text/javascript" src="dwr/interface/DwrUtil.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<script language="JavaScript" TYPE="text/javascript"
	SRC="dwr/interface/Animale.js"> </script>


<!-- script type="text/javascript">function openRichiestaPDF(idTipo, microchip){
	var res;
	var result;
		window.open('GestioneDocumenti.do?command=ListaDocumentiByTipo&tipo='+idTipo+'&idMicrochip='+microchip.value,'popupSelect',
		'height=595px,width=842px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}</script-->

<script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>
	
<!--<script type="text/javascript">function openRichiestaPDF(idTipo, microchip){-->
<!--	var res;-->
<!--	var result=-->
<!--		window.open('GestioneDocumenti.do?command=GeneraPDF&tipo='+idTipo+'&idMicrochip='+microchip.value,'popupSelect',-->
<!--		'height=200px,width=842px,left=200px, top=200px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');-->
<!--		var text = document.createTextNode('GENERAZIONE PDF IN CORSO. ATTENDERE...');-->
<!--		span = document.createElement('span');-->
<!--		span.style.fontSize = "30px";-->
<!--		span.style.fontWeight = "bold";-->
<!--		span.style.color ="#ff0000";-->
<!--		span.appendChild(text);-->
<!--		result.document.body.appendChild(span);-->
<!--		}</script>-->

<script type="text/javascript">


function openCampioniRabbia(microchip){
	//alert(microchip.value);
	var res;
	var result;
		window.open('ProfilassiRabbia.do?command=PrintRichiestaCampioniRabbia&microchip='+microchip.value,'popupSelect',
		'height=595px,width=842px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}



function verificaMicrochipPerTitolazioneAntiRabbia(microchip, idUtente){
	var formTest = true;
	var message = "";

	if( !( (microchip.value.length == 15) && ( /^([0-9]+)$/.test( microchip.value )) ) )
     {
    	  message += label("", "- La lunghezza del microchip deve essere di 15 caratteri numerici\r\n");
       	formTest = false;
     }
	var ok = false;
	//alert($('#flagCattura').is(':checked'));
	if (formTest){
	//	alert("controllo animale");
	Animale.verificaMCperTitolazioneRabbia(microchip.value, idUtente, false, {
		callback:function(data) {
		if (data.idEsito == '-1'){
			$(microchip).focus();
			$(microchip).css('background-color', 'red');
			$('#invia').hide();
			alert(data.descrizione);
		}else{
			$(microchip).css('background-color', 'white');
			$('#invia').show();
		}
		},
		timeout:8000,
		async:false

		});

	return ok;
	}else{

		 alert(label("", "") + message);
	     return false;
	}
}
</script>


<%@ include file="../initPage.jsp"%>
<dhv:evaluate if="<%=!isPopup(request)%>">
	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
			<td width="100%"><a href="ProfilassiRabbia.do"><dhv:label
				name="">Profilassi Rabbia</dhv:label></a> > <dhv:label name="">Titolazione anticorpi</dhv:label>
			</td>
		</tr>
	</table>
	<%-- End Trails --%>
</dhv:evaluate>
<body>


<form name="add"
	action="ProfilassiRabbia.do?command=Insert&auto-populate=true"
	method="post" onsubmit="javascript:return checkForm();"></br>

<span id="datireg" class="datireg">
<table cellpadding="2" cellspacing="0" border="0" width="100%"
	class="details">
	<tr>
		<th colspan="2"><strong><dhv:label name="">Dati da inserire</dhv:label></strong>
		</th>
	</tr>
	<tr>
		<td class="formLabel"><dhv:label name="">Microchip</dhv:label></td>
		<td><input type="text" name="microchip" id="microchip" value=""
			onchange="javascript:verificaMicrochipPerTitolazioneAntiRabbia(document.forms[0].microchip, <%=User.getUserId() %>);" maxlength="15" />
		</td>
	</tr>
	

</table>
</span> </br>
</br>

 <!-- input type="button" value="Genera certificato" id="invia" style="display:none;" onclick="javascript:openCampioniRabbia(document.forms[0].microchip);"/!-->
 <input type="button" value="Genera certificato" id="invia" style="display:none;" onclick="javascript:openRichiestaPDF('PrintRichiestaCampioniRabbia', '-1', '-1', document.forms[0].microchip.value, '-1', '-1');"/></form>
 
 </form>