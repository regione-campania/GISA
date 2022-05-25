<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ taglib uri="/WEB-INF/taglib/systemcontext-taglib.tld" prefix="sc"%>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*"%>

<jsp:useBean id="Titolo" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Operatore" class="org.aspcfs.modules.opu.base.Operatore" scope="request" />
<jsp:useBean id="soggettoAdded" class="org.aspcfs.modules.opu.base.SoggettoFisico" scope="request" />
<jsp:useBean id="indirizzoAdded" class="org.aspcfs.modules.opu.base.Indirizzo" scope="request" />
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="provinciaAsl" class="org.aspcfs.modules.accounts.base.Provincia" scope="request" />
<jsp:useBean id="stabilimento" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request" />

<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<!-- RELATIVO AL NUOVO CALENDARIO CON MESE E ANNO FACILMENTE MODIFICABILI -->
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();
</SCRIPT>

<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/opu.css" />

<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/opu.js"></SCRIPT>

<script type="text/javascript">

$(function() {
    //$( "#searchcodeIdComune" ).combobox();
      //  $( "#partitaIva" ).combobox();

    $( "#searchcodeIdprovincia" ).combobox();
    $( "#searchcodeIdComune" ).combobox();
    $( "#via" ).combobox();
    $( "#addressLegaleCity" ).combobox();
    $( "#addressLegaleLine1" ).combobox();

});
function checkForm()
{
	document.forms[0].doContinue.value="true";
	msg = "Attenzione Controllare di aver compilato i seguenti campi\n" ;

	
    if (document.forms[0].addressLegaleCountry.value == -1) 
    {
    		document.forms[0].doContinue.value="false";
			msg+= "- Seleziona la provincia di residenza del soggetto \n" ;
    }
    if (document.forms[0].addressLegaleCity.value == -1) 
	{
   	 		document.forms[0].doContinue.value="false";
			msg+= "- Seleziona il comune di residenza del soggetto \n" ;
    }
    if (	document.forms[0].addressLegaleLine1input.value == "Inserire le prime 4 lettere" &&
    		document.forms[0].addressLegaleLine1Testo.value == '') 
		{
	   	 	document.forms[0].doContinue.value="false";
   			msg+= "- Seleziona l'indirizzo di residenza del soggetto \n" ;
		}


    
	if ($('#inregione').length > 0 && document.getElementById('inregione').value == 'no')
	{
		if (document.getElementById('addressLegaleCountry').value != null && 
    	      (	document.getElementById('addressLegaleCountry').value == 61 || 
				document.getElementById('addressLegaleCountry').value == 62 ||
				document.getElementById('addressLegaleCountry').value == 63 || 
				document.getElementById('addressLegaleCountry').value == 64 ||
				document.getElementById('addressLegaleCountry').value == 65
				)
			)
			{

    	  document.forms[0].doContinue.value="false";
			msg+= "- Hai selezionato tipo di proprietario fuori regione e scelto una provincia in regione, per favore controlla le informazioni incongruenti \n" ;
      }
	}
	

		if(document.forms[0].codFiscale.value.length > 0 && document.forms[0].codFiscale.value.length!=16)
		{
				document.forms[0].doContinue.value="false";
				msg+= "- Campo codice fiscale azienda non corretto (Lunghezza 16 caratteri) \n" ;
		}
		if(document.forms[0].ragioneSociale.value=='')
		{
			document.forms[0].doContinue.value="false";
			msg+= "- Campo ragione sociale richiesto \n" ;
		}

		if(document.forms[0].partitaIva.value=='' ||  document.forms[0].partitaIva.value.length!=11)
		{
			document.forms[0].doContinue.value="false";
			msg+= "- Campo partitaIva richiesto o non corretto (lunghezza 11 caratteri) \n" ;
	}
	
	

	

	

		if(document.forms[0].searchcodeIdprovincia.value=='-1')
		{
			document.forms[0].doContinue.value="false";
			msg+= "- Campo provincia sede legale richiesto \n" ;
		}	
	if(document.forms[0].via.value=='-1' && document.forms[0].viaTesto.value == '')
	{
		document.forms[0].doContinue.value="false";
		msg+= "- Campo indirizzo sede legale richiesto \n" ;
	}
	if(document.forms[0].searchcodeIdComune.value=='-1')
	{
		document.forms[0].doContinue.value="false";
		msg+= "- Campo comune sede legale richiesto \n" ;
	}
	

	
	if((document.forms[0].codFiscaleSoggetto.value==''  && document.forms[0].codFiscaleSoggetto.value.length!=16) && ((document.getElementById('estero')!=null && document.getElementById('estero').checked == false ) || document.getElementById('estero')==null))
	{
		document.forms[0].doContinue.value="false";
		msg+= "- Campo codice fiscale soggetto richiesto non corretto (lunghezza 16 caratteri) \n" ;
	}
	else
	{
	if (document.getElementById('estero') != null && document.getElementById('estero').checked == true && document.forms[0].codFiscaleSoggetto.value=='' ) 
	{
		document.forms[0].doContinue.value="false";
		msg+= "- Campo codice fiscale soggetto richiesto\n" ;
	}
	}
	if(document.forms[0].nome.value=='')
	{
		document.forms[0].doContinue.value="false";
		msg+= "- Campo nome soggetto fisico richiesto \n" ;
	}

	if(document.forms[0].cognome.value=='')
	{
		document.forms[0].doContinue.value="false";
		msg+= "- Campo cognome soggetto fisico richiesto \n" ;
	}
	

	
	

	if(document.forms[0].doContinue.value=="false")
	{
		$('#mask').hide();
		$('.window').hide();
			alert(msg);
	}
	else
	{
		document.forms[0].doContinue.value="true";
		document.forms[0].submit();
	}
		

}

</script>

<%@ include file="../initPage.jsp"%>

<dhv:evaluate if="<%=!isPopup(request)%>">
	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
			<td width="100%">Impresa -> Aggiungi</td>
		</tr>
	</table>
	<%-- End Trails --%>
</dhv:evaluate>

<%
	boolean popUp = false;
	if (request.getParameter("popup") != null) {
		popUp = true;
	}
%> <%=addHiddenParams(request, "actionSource|popup")%> 




<dhv:container name="anagraficaaimpresadd" selected="operatore" object="OperatoreDettagli" >

<form id="addOperatore" name="addOperatore" action="OperatoreAction.do?command=Insert&auto-populate=true" method="post">

<%if (request.getAttribute("PopUp")!=null)
	{
	%>
	<input type = "hidden" name = "popup" value = "<%=request.getAttribute("PopUp") %>">
	<%
	
	}%>

<input type="button" value="Inserisci" name="Save" onClick="javascript:verificaSoggetto(document.getElementById('codFiscaleSoggetto'))"> <dhv:formMessage showSpace="false" /> 


<dhv:evaluate if="<%=popUp%>">
	<input type="button" value="Annulla" onClick="javascript:self.close();">
</dhv:evaluate> 
<br />
<br />

<input type="hidden" id="sovrascrivi" name="sovrascrivi" value="si" /> 
<input type="hidden" id="idAsl" name=idAsl value="<%=User.getSiteId() %>" /> 
<input type="hidden" id="descrAsl" name="descrAsl" value="<%=SiteList.getSelectedValue(User.getSiteId()) %>" /> 

<%
if (request.getAttribute("Exist") != null && !("").equals(request.getAttribute("Exist"))) 
{
%> 
<font color="red"><%=(String) request.getAttribute("Exist")%></font>
<%
}
%> 

<sc:namecontext></sc:namecontext> 
<sc:context id="opu;gisa_nt">
	<%@ include file="operatore_generic_add.jsp"%>

</sc:context> 

<input type="hidden" name="doContinue" id="doContinue" value="">
<input type="hidden" name="tipologiaSoggetto" value="<%=(request.getAttribute("TipologiaSoggetto") != null) ? (String) request.getAttribute("TipologiaSoggetto"): ""%>">

<input type="button" value="Inserisci" name="Save" onClick="javascript:verificaSoggetto(document.getElementById('codFiscaleSoggetto'))">



<dhv:evaluate if="<%=popUp%>">
	<input type="button" value="Annulla" onClick="javascript:self.close();">
</dhv:evaluate></form>



<div id="boxes"><%-- IL CAMPO SRC è DA AGGIUSTARE --%>
<div id="dialog4" class="window">
<a href="#" class="close" /><b>Chiudi</b></a>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
<tr>
		<th colspan="2"><strong>
		<div id="intestazione"></div>
		</strong></th>
	</tr>

	<tr>
		<td class="formLabel" nowrap>Nome</td>
		<td>
		<div id="nomeSoggetto"></div>
		</td>
	</tr>

	<tr>
		<td class="formLabel" nowrap>Cognome</td>
		<td>
		<div id="cognomeSoggetto"></div>
		</td>
	</tr>

	<tr>
		<td class="formLabel" nowrap>Sesso</td>
		<td>
		<div id="sessoSoggetto"></div>
		</td>
	</tr>

	<tr>
		<td class="formLabel" nowrap>Data Nascita</td>
		<td>
		<div id="dataNascitaSoggetto"></div>
		</td>
	</tr>
	<tr>
		<td class="formLabel" nowrap>Comune Nascita</td>
		<td>
		<div id="comuneNascitaSoggetto"></div>
		</td>
	</tr>
	<tr>
		<td class="formLabel" nowrap>Provincia Nascita</td>
		<td>
		<div id="provinciaNascitaSoggetto"></div>
		</td>
	</tr>

	<tr>
		<td class="formLabel" nowrap>Comune Residenza</td>
		<td>
		<div id="comuneResidenzaSoggetto"></div>
		</td>
	</tr>
	<tr>
		<td class="formLabel" nowrap>Provincia Residenza</td>
		<td>
		<div id="provinciaResidenzaSoggetto"></div>
		</td>
	</tr>
	<tr>
		<td class="formLabel" nowrap>Indirizzo Residenza</td>
		<td>
		<div id="indirizzoResidenzaSoggetto"></div>
		</td>
	</tr>

	<tr>
		<td class="formLabel" nowrap>Documento</td>
		<td>
		<div id="documentoSoggetto"></div>
		</td>
	</tr>
	<tr>
		<td class="formLabel" nowrap>e-mail</td>
		<td>
		<div id="mailSoggetto"></div>
		</td>
	</tr>
	<tr>
		<td class="formLabel" nowrap>telefono principale</td>
		<td>
		<div id="telefono1Soggetto"></div>
		</td>
	</tr>
	<tr>
		<td class="formLabel" nowrap>cellulare/tel. secondario</td>
		<td>
		<div id="telefono2Soggetto"></div>
		</td>
	</tr>

	<tr>
		<td class="formLabel" nowrap>Fax</td>
		<td>
		<div id="faxSoggetto"></div>
		</td>
	</tr>
	<tr id="azione">
		<td><input type="button" value="Sovrascrivi" onclick="document.getElementById('sovrascrivi').value='si';checkForm();" /></td>
		<td><input type="button" value="Non Sovrascrivere" onclick="document.getElementById('sovrascrivi').value='no';checkForm();" /></td>
	</tr>

</table>


</div>

<div id="dialog" title="Impresa Esistente! vuoi andare al dettaglio ?" style="height: 80px;">
</div>

<!-- Mask to cover the whole screen -->
<div id="mask"></div>

</div>
</dhv:container>

