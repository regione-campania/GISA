<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.base.Constants"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page
	import="java.io.*,java.util.*,org.aspcfs.utils.web.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.registrazioniAnimali.base.*"%>
	
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="operatoreToModify" class="org.aspcfs.modules.opu.base.Operatore"
	scope="request" />
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
	
<jsp:useBean id="regioni" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
	
<jsp:useBean id="province" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
	
<jsp:useBean id="comuni" class="org.aspcfs.utils.web.LookupList"
	scope="request" />

<script language="javascript" SRC="javascript/CalendarPopup.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/dateControl.js"></script>
<script language="javascript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();
</SCRIPT>
<%Stabilimento stab = (Stabilimento) operatoreToModify.getListaStabilimenti().get(0); 
  LineaProduttiva lp = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
  Indirizzo indOperativo = stab.getSedeOperativa();
%>
<script language="JavaScript">

function doCheck(form){
	formTest = true;
	message = "";
//	alert($('#nuovaDimensione').val());
//	if ($('#nuovaDimensione').val() == null || $('#nuovaDimensione').val() == ''){
		//alert('ddd');
//		formTest = false;
//		message += "Specifica una nuova dimensione della colonia \r\n";
//	}

	//alert($('#dataModificaResidenza').val());
if ($('#dataModificaResidenza').val() == null || $('#dataModificaResidenza').val() == ''){
		//alert('ddd');
		formTest = false;
		message += "Specifica una nuova data per la registrazione di modifica \r\n";
	}
	
if ( $('#idAslNuovoProprietarioSelect').val()=='-2')
{
	formTest = false;
	message += "Specificare Indirizzo di destinazione\r\n";
}

if ($('#inRegione').val()=='si' && document.getElementById('cap').value=='80100' )
{
	formTest = false;
	message += "Non è possibile inserire il cap 80100\r\n";
}

if ($('#idAslNuovoProprietario').val() == <%=stab.getIdAsl()%>){
	formTest = false;
	message += "Stai selezionando un'asl di destinazione uguale all'asl attuale, se devi modificare accedi al modulo di modifica operatore \r\n";

}

	 
    if (formTest == false) {
      alert(label("check.form", "Impossibile proseguire, per favore verifica i seguenti problemi:\r\n\r\n") + message);
      return false;
    }
    else
    {
    	
    	
    	return true;
    }
}

$(document).ready(function(){

$('#idProvinciaModificaResidenzaSelect').change(function(){
	$("#idNuovoIndirizzo").val('');
	  var elem = $(this).val();
	 //  alert('aa');
	  $.ajax({
	   type: 'POST',
	   url:'ServletRegioniComuniProvince',
	   dataType: 'json',
	   data: {'idProvinciaModificaResidenzaSelect':elem, 'tipoRichiesta':2},
	   success: function(res){
	    $('#idComuneModificaResidenzaSelect option').each(function(){$(this).remove()});
	    //$("#via").val('');
	   // $("#idNuovoIndirizzo").val('-1');
	    $('#idComuneModificaResidenzaSelect').append('<option selected="selected">Seleziona...</option>');
	    $.each(res, function(i, e){
	     $('#idComuneModificaResidenzaSelect').append('<option value="' + e.codice + '">' + e.descrizione + '</option>');
	    });
	   }
	  });
	 });


	 $('#idComuneModificaResidenzaSelect').change(function(){


		    $("#via").val('');
		    $("#idNuovoIndirizzo").val('');
			  var elem = $(this).val();
		//	   alert('aa');
			  $.ajax({
			   type: 'POST',
			   url:'ServletRegioniComuniProvince',
			   dataType: 'json',
			   data: {'idComune':elem, 'tipoRichiesta':3},
			   success: function(res){
			 //   $('#idComune option').each(function(){$(this).remove()});
			    //$("#via").val('');
			   // $("#idNuovoIndirizzo").val('-1');
			 
			    $.each(res, function(i, e){
					  // alert(e.codice);
					   $('#idAslNuovoProprietarioSelect').val(e.codice);
					   $('#idAslNuovoProprietario').val(e.codice);
					   $('#idAslNuovoProprietarioSelect').prop('disabled', true);
					//   alert($('#idAslNuovoProprietario').val());
			    });
			   }
			  });


		 });


	 var via = { 
	 		source: function (request, response) {
	     $.post("ServletRegioniComuniProvince?tipoRichiesta=4_text&idprovincia="+$("#idProvinciaModificaResidenzaSelect").val()+"&idcomune="+$("#idComuneModificaResidenzaSelect").val(), request, response);
	   }, 


	 change: function(event, ui){ 	
//	 	alert((ui.item ? ui.item.idindirizzo : 'sdf'));	

	 	$("#idNuovoIndirizzo").val(ui.item ? ui.item.idindirizzo : '-1' );  }, 
	 	minLength:3 }; 


	 $("#via").autocomplete(via);

	 checkInRegione();
	 
	$('#idComuneModificaResidenzaSelect').prop('disabled', true);
	$('#idProvinciaModificaResidenzaSelect').prop('disabled', true);
	$('#idAslNuovoProprietarioSelect').prop('disabled', true);
	 
}); 


function checkInRegione(){
	//alert($("#inregione").val());
    $('#idRegione').prop('disabled', true);
	
    if ($("#inRegione").val() == 'no'){
		 $('#idAslNuovoProprietarioSelect').prop('disabled', true);
		 $('#idAslNuovoProprietarioSelect').prop('value', '-2');
		 $('#idAslNuovoProprietario').prop('value', '');
		 $('#idProvinciaModificaResidenzaSelect').prop('disabled', true);
		 $('#idProvinciaModificaResidenzaSelect').prop('value', '-2');
		 $('#idProvinciaModificaResidenza').prop('value', '');
		 $('#idComuneModificaResidenzaSelect').prop('disabled', true);
		 $('#idComuneModificaResidenzaSelect').prop('value', '-1');
		 $('#idComuneModificaResidenza').prop('value', '');
		 $('#via').prop('value', '');
		 $('#cap').prop('value', '');
		 //$('#idRegione').prop('disabled', false);
		 //document.getElementById('selezionaIndirizzoLink').style.display="none";
		
	}else{
		 //$('#idAslNuovoProprietarioSelect').prop('disabled', false);
		 //$('#idProvinciaModificaResidenzaSelect').prop('disabled', false);
		 //$('#idComuneModificaResidenzaSelect').prop('disabled', false);
		 //$('#via').prop('disabled', false);
		 //$('#idRegione').prop('disabled', true);
		 $('#idRegione').prop('value', '-2');
		 $('#selezionaIndirizzoLink').prop('disabled', false);
		 //document.getElementById('selezionaIndirizzoLink').style.display="block";
		 
	}
	}




</script>




<%@ include file="../initPage.jsp"%>
<dhv:evaluate if="<%= !isPopup(request) %>">
	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
			<td width="100%"><a href="OperatoreAction.do?command=details&opId=<%=lp.getId() %>"><dhv:label
				name="">Operatore</dhv:label></a> > <dhv:label
				name="">Modifica indirizzo</dhv:label>
			</td>
		</tr>
	</table>
	<%-- End Trails --%>
</dhv:evaluate>
<body>

<form name="modificaIndirizzo"
	action="OperatoreAction.do?command=ModificaIndirizzoOperatore&auto-populate=true"
	method="post" onsubmit="javascript:return checkForm();">


<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="details">
	<tr>
		<th colspan="2"><strong><dhv:label name="">Informazioni operatore</dhv:label></strong>
		</th>
	</tr>

	<tr>
		<td class="formLabel" nowrap> Asl di riferimento </td>
		<td><%=AslList.getSelectedValue(stab.getIdAsl()) %></td>
		<input type="hidden" name="idAslRiferimento" id="idAslRiferimento"
			value="<%=User.getSiteId() %>" />
	</tr>
	<tr>
		<td class="formLabel" nowrap>Provincia</td>
		<td><%=province.getSelectedValue(indOperativo.getProvincia()) %></td>
	</tr>
	<input type="hidden" name="idIndirizzoOperativoOld" id="idIndirizzoOperativoOld"
		value=" <%=indOperativo.getIdIndirizzo() %>" />
	<tr>
		<td class="formLabel" nowrap>Comune</td>
		<td><%=comuni.getSelectedValue(indOperativo.getComune())%></td>
	</tr>
	<tr>
		<td class="formLabel" nowrap>Via</td>
		<td><%=(indOperativo.getVia())%>
		<input type="hidden" name="idRelazioneStabilimentoLineaProduttiva" id="idRelazioneStabilimentoLineaProduttiva" value="<%=lp.getId() %>"/>
		
		</td>
	</tr>
</table>
</br>
</br>
<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="details">
	<tr>
		<th colspan="2"><strong><dhv:label name="">Dati nuovo indirizzo</dhv:label></strong>
		</th>
	</tr>
	<tr>
		<td class="formLabel" nowrap>Data modifica indirizzo</td>
		<td><input readonly type="text" name="dataModificaResidenza" size="10" id="dataModificaResidenza"
				value=""
				nomecampo="dataModificaResidenza" tipocontrollo="T2" labelcampo="Data modifica indirizzo" />&nbsp;
			<a href="#"
				onClick="cal19.select(document.forms[0].dataModificaResidenza,'anchor19','dd/MM/yyyy'); return false;"
				NAME="anchor19" ID="anchor19"><img
				src="images/icons/stock_form-date-field-16.gif" border="0"
				align="absmiddle"></a><font color="red">*</font></td>
	</tr>
	
		<tr>
		<td class="formLabel"><dhv:label name="opu.sede_legale.inregione"></dhv:label>
		</td>
		<td><select  name="inRegione" onchange="javascript:checkInRegione();" id="inRegione">
			<option value="si" selected="selected">SI</option>
<%
		if(!stab.isFlagFuoriRegione() && stab.getIdAsl()!=Constants.ID_ASL_FUORI_REGIONE)
		{
%>			
			<option value="no">NO</option>
<%
		}
%>
		</select></td>
	</tr>
	
<tr><td class="formLabel" nowrap>Regione</td><td>
	<%=regioni.getHtmlSelect("idRegione", -1) %>
	</td></tr>
	
	<tr>
		<td class="formLabel" nowrap>
			Indirizzo
		</td>
		<td>
			<a id="selezionaIndirizzoLink" style="text-decoration:underline; color:#006b9c;" onmouseover="this.style.textDecoration='none'; this.style.color='black';" onmouseout="this.style.textDecoration='underline'; this.style.color='#006b9c';" onclick="var regione = 'CAMPANIA'; if(document.getElementById('inRegione').value=='no'){regione='FUORI REGIONE'};selezionaIndirizzo('106','callbackResidenzaProprietarioModificaIndirizzo','',regione)">Seleziona</a>
		</td>
	</tr>
	
	<tr><td class="formLabel" nowrap>Provincia</td><td>
	<%=province.getHtmlSelect("idProvinciaModificaResidenzaSelect", -1) %>
	</td></tr>
	
    <tr><td class="formLabel" nowrap>Comune</td><td>
	<%=comuni.getHtmlSelect("idComuneModificaResidenzaSelect", -1) %>
	</td></tr>
	<tr><td class="formLabel" nowrap>Via / Piazza</td><td>
	<input type="text" name="via" id="via" readonly="readonly"/>
	</td></tr>
	
	<tr><td class="formLabel" nowrap>Cap</td><td>
	<input type="text" name="cap" id="cap" readonly="readonly"/>
	</td></tr>
	
		
	<input type="hidden" name="idNuovoIndirizzo" id="idNuovoIndirizzo"/>
	</td></tr>
	
	
	    <tr id ="inregionedati"><td class="formLabel" nowrap>Asl</td><td>
	<%=AslList.getHtmlSelect("idAslNuovoProprietarioSelect", -1) %>
	</td></tr>
	
	<input type="hidden" name="idAslNuovoProprietario" id="idAslNuovoProprietario"  value="" />
	<input type="hidden" name="idComuneModificaResidenza" id="idComuneModificaResidenza"  value="" />
	<input type="hidden" name="idProvinciaModificaResidenza" id="idProvinciaModificaResidenza"  value="" />


<tr><td>&nbsp;</td><td>
	<input type="button" value="invia" id="invia"
		name="invia" onclick="if(doCheck(this.form)){this.form.submit()}" /></td></tr>
</table>

</br>
</br>


</form>