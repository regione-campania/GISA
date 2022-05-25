<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page
	import="java.io.*,java.util.*,org.aspcfs.utils.web.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.registrazioniAnimali.base.*"%>
<meta charset="utf-8" />

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="EventoPrelievoLeishmania"
	class="org.aspcfs.modules.registrazioniAnimali.base.EventoPrelievoLeishmania"
	scope="session" />
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />

<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>


<script language="javascript" SRC="javascript/CalendarPopup.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/dateControl.js"></script>
<script language="javascript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();
</SCRIPT>

<script type="text/javascript" src="dwr/interface/DwrUtil.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<script language="JavaScript" TYPE="text/javascript"
	SRC="dwr/interface/Animale.js"> </script>

<script type="text/javascript">

function verificaMicrochipEsistenza(microchip){
	var formTest = true;
	var message = "";

//	dwr.util.useLoadingMessage('testttt');
	 dwr.util.useLoadingImage('images_bdu/ajax-loader.gif');
	if( !( (microchip.value.length == 15) && ( /^([0-9]+)$/.test( microchip.value )) ) )
     {
    	  message += label("", "- La lunghezza del microchip deve essere di 15 caratteri numerici\r\n");
       	formTest = false;
     }
	var ok = false;
	//alert($('#flagCattura').is(':checked'));
	if (formTest){
	//	alert("controllo animale");
	Animale.verificaEsistenzaMC(microchip.value, {
		callback:function(data) {
		if (data.idEsito == '-1'){
			$(microchip).focus();
			$(microchip).css('background-color', 'red');
			$('#dataPrelievoLeishamania').attr("disabled", "true");
			alert(data.descrizione);
			return false;
		}else{
			$(microchip).css('background-color', 'white');
			$('#dataPrelievoLeishamania').removeAttr('disabled');
			$('#registrazione').val(data.dataRegistrazioneAnimale);
			
		//	alert('WHITE');
			return true;
		}
		},
		timeout:8000,
		async:false

		});

	//return ok;
	}else{

		 alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
	     return false;
	}
}

function checkForm(form){
	 formTest = true;
	 message = "";
	lanciaControlloDate();
	
	 
	if (formTest){
	Animale.verificaEsistenzaMC(microchip.value, {
		callback:function(data) {
		if (data.idEsito == '-1'){
			$(microchip).focus();
			$(microchip).css('background-color', 'red');
			message = data.descrizione;
			formTest =  false;
		}else{
			$(microchip).css('background-color', 'white');
			//alert('WHITE');
			formTest =  true;
		}
		},
		timeout:8000,
		async:false

		});
	}

    if (formTest == false) {
    	 alert(label("check.form", "Non puoi proseguire:\r\n\r\n") + message);
      return false;
    }else{
      //  alert('else return true');
	return true;
        }
}



dwr.util.useLoadingImage = function useLoadingImage(imageSrc) {
	//alert(imageSrc);
	  var loadingImage;
	  if (imageSrc) loadingImage = imageSrc;
	  else loadingImage = "images/c9_ajax-loader.gif";
	  dwr.engine.setPreHook(function() {
	    var disabledImageZone = dwr.util.byId('disabledImageZone');
	  //  alert(disabledImageZone);
	    if (!disabledImageZone) {
		//    alert('not');
	      disabledImageZone = document.createElement('div');
	      disabledImageZone.setAttribute('id', 'disabledImageZone');
	      disabledImageZone.style.position = "absolute";
	      disabledImageZone.style.zIndex = "1000";
	      disabledImageZone.style.left = "0px";
	      disabledImageZone.style.top = "0px";
	      disabledImageZone.style.width = "100%";
	      disabledImageZone.style.height = "100%";
	      var imageZone = document.createElement('img');
	      imageZone.setAttribute('id','imageZone');
	      imageZone.setAttribute('src','c9_ajax-loader.gif');
	      imageZone.src='c9_ajax-loader.gif';
	      imageZone.style.position = "absolute";
	      imageZone.style.top = "0px";
	      imageZone.style.right = "0px";
	      disabledImageZone.appendChild(imageZone);
	      document.body.appendChild(disabledImageZone);
	    }
	    else {
	     $('#imageZone').attr('src',imageSrc);	    
	      disabledImageZone.style.visibility = 'visible';

	    }
	  });
	  dwr.engine.setPostHook(function() {
		  disabledImageZone.style.visibility = 'hidden';
	  });
	}
</script>


<%@ include file="../initPage.jsp"%>
<dhv:evaluate if="<%=!isPopup(request)%>">
	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
			<td width="100%"><a href="ProfilassiLeishmania.do"><dhv:label
				name="">Profilassi Leishmania</dhv:label></a> > <dhv:label name="">Aggiungi registrazione di prelievo</dhv:label>
				
			</td>
		</tr>
	</table>
	<%-- End Trails --%>
</dhv:evaluate>
<body>


<form name="add"
	action="ProfilassiLeishmania.do?command=Insert&auto-populate=true"
	method="post" onsubmit="javascript:return checkForm(this);"></br>

<span id="datireg" class="datireg">
<input
	type="hidden" id="registrazione" name="registrazione" nomecampo="registrazione"
	labelcampo="Data Registrazione"
	value="" />
<table cellpadding="2" cellspacing="0" border="0" width="100%"
	class="details">
	<tr>
		<th colspan="2"><strong><dhv:label name="">Dati da inserire</dhv:label></strong>
		</th>
	</tr>
	<tr>
		<td class="formLabel"><dhv:label name="">Microchip</dhv:label></td>
		<td><input type="text" name="microchip" id="microchip" value=""
			onchange="javascript:verificaMicrochipEsistenza(document.forms[0].microchip);" maxlength="15" />
			<div id="disabledImageZone" style="visibility: hidden; color:red;">Verifica microchip in corso <br><img id="imageZone" src="" name="imageZone" /></div>
		</td>
	</tr>
	
	<tr>
		<td class="formLabel"><dhv:label name="">Data prelievo</dhv:label></td>
		<td><input disabled="disabled" readonly type="text" name="dataPrelievoLeishamania" id="dataPrelievoLeishamania" size="10"
			value="" nomecampo="dataPrelievoLeishamania" tipocontrollo="T2,T11"
			labelcampo="Data Prelievo" />&nbsp; <a href="#"
			onClick="cal19.select(document.forms[0].dataPrelievoLeishamania,'anchor19','dd/MM/yyyy'); return false;"
			NAME="anchor19" ID="anchor19"><img
			src="images/icons/stock_form-date-field-16.gif" border="0"
			align="absmiddle"></a></td>
	</tr>

</table>
</span> </br>
</br>
<input type="hidden" name="idVeterinarioLLPP" id="idVeterinarioLLPP" value="<%=User.getUserId() %>" />
<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="details">
	<tr>
		<th colspan="2"><strong>NOTE</strong></th>
	</tr>
	<tr class="containerBody">
		<td valign="top" class="formLabel"><dhv:label name="">Note</dhv:label>
		</td>
		<td><textarea name="note" rows="3" cols="50"><%=toString(EventoPrelievoLeishmania.getNote())%></textarea>
		</td>
	</tr>
</table>

</br>

<dhv:permission name="anagrafe_canina-note_internal_use_only-add">
	<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
			<th colspan="2"><strong>NOTE USO INTERNO</strong></th>
		</tr>
		<tr class="containerBody">
			<td valign="top" class="formLabel"><dhv:label name="">Note</dhv:label>
			</td>
			<td><textarea name="noteInternalUseOnly" rows="3" cols="50"><%=toString(EventoPrelievoLeishmania
						.getNoteInternalUseOnly())%></textarea></td>
		</tr>
	</table>
</dhv:permission> <input type="submit" value="Invia dati e stampa modulo per invio campioni" id="invia" /></form>