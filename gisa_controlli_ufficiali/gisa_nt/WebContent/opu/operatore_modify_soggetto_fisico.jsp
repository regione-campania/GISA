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
<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>


<script language="JavaScript" TYPE="text/javascript" SRC="javascript/gestoreCodiceFiscale.js"></script>
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
	cal19.showNavigationDropdowns();
</SCRIPT>

<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/opu.css" />

<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/opu.js"></SCRIPT>

<script type="text/javascript">

$(function() {


    
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
			<td width="100%">
			
			<dhv:label name=""><a href="<%=StabilimentoDettaglio.getAction()+".do?command=SearchForm" %>" >Anagrafica Stabilimenti </a>-><a  href="<%=StabilimentoDettaglio.getAction()+".do?command=Details&stabId="+StabilimentoDettaglio.getIdStabilimento()%>">Scheda Impresa</a> ->Variazione Legale Rappresentante Impresa </dhv:label>
			</td>
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



<%
String param = "stabId="+StabilimentoDettaglio.getIdStabilimento()+"&opId=" + StabilimentoDettaglio.getIdOperatore();
%>
<%
String nomeContainer = StabilimentoDettaglio.getContainer();

request.setAttribute("Operatore",StabilimentoDettaglio.getOperatore());
%>
<dhv:container name="<%=nomeContainer %>"  selected="variazionesfimpresa" object="Operatore" param="<%=param%>"  hideContainer="false">



<form id="addOperatore" name="addOperatore" action="<%=StabilimentoDettaglio.getAction() %>.do?command=UpdateSoggettoImpresa&auto-populate=true" method="post">

<input type = "hidden" name = "idStabilimento" value = "<%=StabilimentoDettaglio.getIdStabilimento() %>"/>
<input type = "hidden" name = "idOperatore" value = "<%=StabilimentoDettaglio.getIdOperatore() %>"/>

<input type="button" value="Inserisci" name="Save" onClick="javascript:verificaSoggetto(document.getElementById('codFiscaleSoggetto'))"> <dhv:formMessage showSpace="false" /> 


<dhv:evaluate if="<%=popUp%>">
	<input type="button" value="Annulla" onClick="javascript:self.close();">
</dhv:evaluate> 
<br />
<br />

<input type="hidden" id="sovrascrivi" name="sovrascrivi" value="si" /> 
<input type="hidden" id="idAsl" name=idAsl value="<%=User.getSiteId() %>" /> 
<input type="hidden" id="descrAsl" name="descrAsl" value="<%=SiteList.getSelectedValue(User.getSiteId()) %>" /> 



<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" >
	
	<tr>
		<th colspan="2"><strong><dhv:label name="<%="opu.soggetto_fisico_gisa"%>"></dhv:label></strong></th>
	</tr>
	<tr>
		<th>&nbsp;</th>
		<th>
			<input type = "text" name="cfSearch" maxlength="16"  value = "Ricerca codice Fiscale" onclick="this.value=''" id = "cfSearch" style="background-color: blue;color: white;">
			<a href="#" onclick="ricercaSoggettoFisico(document.getElementById('cfSearch').value)">
				<img src="images/filter.gif">
			</a>
			<a href= "#" onclick="document.getElementById('cfSearch').value=''">
				<img src="images/clear.gif">
			</a>
		</th>
	</tr>

	<tr>
		<td class="formLabel">
			<dhv:label name="opu.sede_legale.inregione"></dhv:label>
		</td>
		<td>
			<select  name="inregione" onchange="checkLineaProduttiva()" id="inregione">
				<option value="si" <%=(Operatore.isFlagFuoriRegione()) ? "selected" : ""%>>SI</option>
				<option value="no" <%=(Operatore.isFlagFuoriRegione()) ? "selected" : ""%>>NO</option>
			</select>
		</td>
	</tr>

	<tr>
		<td class="formLabel" nowrap>
			<dhv:label name="opu.soggetto_fisico.nome"></dhv:label>
		</td>
		<td>
			<input type="text" size="30" maxlength="50" id="nome" name = "nome">
		</td>
		
	</tr>
	<tr>
		<td class="formLabel" nowrap>
			<dhv:label name="opu.soggetto_fisico.cognome"></dhv:label>
		</td>
		<td>
			<input class="todisable" type="text" size="30" maxlength="50" id="cognome" name="cognome" value="">
			<font color="red">*</font>
		</td>
	</tr>
	

	<tr id="">
		<td class="formLabel" nowrap>
			<dhv:label name="opu.soggetto_fisico.sesso"></dhv:label>
		</td>
		<td>
			<input class="todisable" type="radio" name="sesso" id="sesso1" value="M" checked="checked">M 
			<input type="radio" name="sesso" id="sesso2" value="F">F
		</td>
	</tr>

	<tr>
		<td nowrap class="formLabel">
			<dhv:label name="opu.soggetto_fisico.data_nascita"></dhv:label>
		</td>
		<td>
			<input class="todisable" readonly type="text" id="dataNascita" name="dataNascita" size="10" value = "" /> 
				<a href="#" onClick="cal19.select(document.forms[0].dataNascita,'anchor19','dd/MM/yyyy'); return false;" name="anchor19" ID="anchor19"> 
					<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle">
				</a>
		</td>
	</tr>
	
	<tr>
		<td class="formLabel" nowrap>
			<dhv:label name="opu.soggetto_fisico.provincia_nascita"></dhv:label>
		</td>
		<td>
			<input class="todisable" type="text" size="30" maxlength="50" id="provinciaNascita" name="provinciaNascita" value="">
		</td>
	</tr>
	<tr>
		<td class="formLabel" nowrap>
			<dhv:label name="opu.soggetto_fisico.comune_nascita"></dhv:label>
		</td>
		<td>
			<input class="todisable" type="text" size="30" maxlength="50" id="comuneNascita" name="comuneNascita" value="">
		</td>
	</tr>
	
	
	<tr>
		<td class="formLabel" nowrap>
			<dhv:label name="opu.soggetto_fisico.provincia_residenza"></dhv:label>
		</td>
		<td>
		
				<script>
		 				$(function() {
		       				$( "#addressLegaleCountry" ).combobox();
		    			});
		    	</script>
				<select class="todisable" name="addressLegaleCountry" id="addressLegaleCountry">
					<option value="-1">Inserire le prime 4 lettere</option>
				</select>
				
				<input type="hidden" name="addressLegaleCountryTesto" id="addressLegaleCountryTesto" /> 
				<font color="red">(*)</font> 
			
		</td>
	</tr>
	
	<tr>
		<td class="formLabel" nowrap>
			<dhv:label name="opu.soggetto_fisico.comune_residenza"></dhv:label>
		</td>
		<td>
			<select class="todisable" name="addressLegaleCity" id="addressLegaleCity">
				<option value="-1">Inserire le prime 4 lettere</option>
			</select>
			
			<input type="hidden" name="addressLegaleCityTesto" id="addressLegaleCityTesto" /> 
			<font color="red">(*)</font> 
		</td>
	</tr>


	<tr>
		<td class="formLabel" nowrap>
			<dhv:label name="opu.soggetto_fisico.indirizzo"></dhv:label>
		</td>
		<td>
			<select  name="addressLegaleLine1" id="addressLegaleLine1">
				<option value="-1" selected="selected">Inserire le prime 4 lettere</option>
			</select>
			
			<font color="red">(*)</font> 
			<input type="hidden" name="addressLegaleLine1Testo" id="addressLegaleLine1Testo" /> 
		</td>
	</tr>

	
	<tr>
		<td class="formLabel" nowrap>
			<dhv:label name="opu.soggetto_fisico.cf"></dhv:label>
		</td>
		<td>
			<input readonly="readonly"  type="text" name="codFiscaleSoggetto" id="codFiscaleSoggetto" />  <font color="red">(*)</font>
			<input type = "checkbox" name = "estero" id = "estero" value="NO" onclick="if(this.checked){this.value='true';document.getElementById('calcoloCF').style.visibility='hidden';document.getElementById('codFiscaleSoggetto').readOnly=false;} else {this.value='false';document.getElementById('calcoloCF').style.visibility='visible';document.getElementById('codFiscaleSoggetto').readOnly=true;document.getElementById('codFiscaleSoggetto').value='';}" >Provenienza Estera
			<input type="button" id = "calcoloCF" value="Calcola Codice Fiscale" onclick="javascript:CalcolaCF(document.forms[0].sesso,document.forms[0].nome,document.forms[0].cognome,document.forms[0].comuneNascita,document.forms[0].dataNascita,'codFiscaleSoggetto')"></input>
		</td>
	</tr>
	<tr>
		<td class="formLabel" nowrap>
			<dhv:label name="opu.soggetto_fisico.didentita"></dhv:label>
		</td>
		<td>
		 	<input class="todisable" type="text" name="documentoIdentita" id="documentoIdentita" value=""/> 
		 </td>
	</tr>

	<tr style="display: none">
		<td class="formLabel" nowrap>
			<dhv:label name="opu.soggetto_fisico.mail"></dhv:label>
		</td>
		<td>
			<input class="todisable" type="text" size="30" maxlength="50" id="email" name="email" value="">
		</td>
	</tr>

	<tr style="display: none">
		<td class="formLabel" nowrap>
			<dhv:label name="opu.soggetto_fisico.telefono"></dhv:label>
		</td>
		<td>
			<input class="todisable" type="text" size="30" maxlength="50" id="telefono1" name="telefono1" value="">
		</td>
	</tr>

	<tr style="display: none">
		<td class="formLabel" nowrap>
			<dhv:label name="opu.soggetto_fisico.telefono2"></dhv:label>
		</td>
		<td>
			<input class="todisable" type="text" size="30" maxlength="50" id="telefono2" name="telefono2" value="">
		</td>
	</tr>

	<tr style="display: none">
		<td class="formLabel" nowrap>
			<dhv:label name="opu.soggetto_fisico.fax"></dhv:label>
		</td>
		<td>
			<input class="todisable" type="text" size="30" maxlength="50" id="fax" name="fax" value="">
		</td>
	</tr>
</table>

<input type="hidden" name="doContinue" id="doContinue" value="">
<input type="hidden" name="tipologiaSoggetto" value="<%=(request.getAttribute("TipologiaSoggetto") != null) ? (String) request.getAttribute("TipologiaSoggetto"): ""%>">

<input type="button" value="Inserisci" name="Save" onClick="javascript:verificaSoggetto(document.getElementById('codFiscaleSoggetto'))">



</form>



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

