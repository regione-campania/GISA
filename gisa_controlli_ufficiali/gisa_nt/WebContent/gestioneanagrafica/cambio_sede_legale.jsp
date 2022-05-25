<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope = "request"/>


<script src="javascript/vendor/moment.min.js"></script>
<script src="javascript/noscia/addNoScia.js"></script>
<script type="text/javascript" src="dwr/interface/DWRnoscia.js"> </script>
<script type="text/javascript" src="dwr/interface/SuapDwr.js"> </script>

<script  src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<script src="javascript/gestioneanagrafica/add.js"></script>

<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="OpuStab.do?command=SearchForm">ANAGRAFICA STABILIMENTI</a> > 
			<a href="GestioneAnagraficaAction.do?command=Details&altId=${altId}">SCHEDA</a> > VARIAZIONE SEDE LEGALE
		</td>
	</tr>
</table>

<form class="form-horizontal" role="form" method="post" action="GestioneAnagraficaAction.do?command=CambioSedeLegale" onsubmit="return validateForm();">
<b>MODIFICA SCHEDA: VARIAZIONE SEDE LEGALE</b><br>
<input type="hidden" id="stabId" name="stabId" value="<%=StabilimentoDettaglio.getIdStabilimento()%>"/>
<input type="hidden" id="altId" name="altId" value="<%=StabilimentoDettaglio.getAltId()%>"/>
<input type="hidden" id="id_asl_imp" value="<%=StabilimentoDettaglio.getIdAsl() %>" /> 
<input type="hidden" id="tipo_impresa" value="<%=StabilimentoDettaglio.getOperatore().getTipo_societa() %>" />


<dhv:permission name="cambio_sede_operatore-view"> 
	<body onload ="document.getElementById('id_asl_imp').value = '-1'">   
	</body> 
</dhv:permission>

<div id="dati_pratica" style="border: 1px solid black; background: #BDCFFF">
<br>
&nbsp;&nbsp;&nbsp;&nbsp;NUMERO PRATICA: ${numeroPratica} <br>
&nbsp;&nbsp;&nbsp;&nbsp;DATA PEC / DATA SCIA: ${dataPratica} <br>
<br>
<input type="hidden" id="numeroPratica" name="numeroPratica" value="${numeroPratica}"/>
<input type="hidden" id="idTipologiaPratica" name="idTipologiaPratica" value="${tipoPratica}"/>
<input type="hidden" id="idComunePratica" name="idComunePratica" value="${comunePratica}"/>
<input type="hidden" id="causaleRicevuta" value="${id_causale}">
</div>
<br/>

<jsp:include page="include/sezione_gestione_causale.jsp"/>


<div id="operazione_scheda" style="display: none">
<table class="details" cellspacing="0" border="0" width="100%" cellpadding="4">
<tbody>
	<tr>
		<th colspan="2">STABILIMENTO</th>
	</tr>
	<tr>
		<td class="formLabel">Ragione Sociale Impresa</td>
		<td><%=StabilimentoDettaglio.getOperatore().getRagioneSociale()%></td>
	</tr>
	<tr>
		<td class="formLabel">Numero registrazione</td>
		<td><%=StabilimentoDettaglio.getNumero_registrazione()%></td>
	</tr>
	
	<tr>
		<td class="formLabel">ASL</td>
		<td><%=StabilimentoDettaglio.getSedeOperativa().getDescrizioneAsl() %> </td>
		
	</tr>
	
	
	<tr>
		<th colspan="2">DATI ATTUALE SEDE LEGALE</th>
	</tr>
	<tr>
		<td class="formLabel">INDIRIZZO</td>
		<td>
			${provincia_sede_legale}&nbsp;&nbsp;
			${comune_sede_legale}&nbsp;&nbsp;
			${toponimo_sede_legale}&nbsp;&nbsp;
			${via_sede_legale}&nbsp;&nbsp;
			${civico_sede_legale}&nbsp;&nbsp;
			${cap_sede_legale}
		 	
		 	
		 </td>
	</tr>
	
	<tr>
		<td class="formLabel">COORDINATE</td>
		<td>
			${latitudine_sede_legale} &nbsp;&nbsp;
		 	${longitudine_sede_legale}
		</td>
	</tr>
		
	
	<tr>
		<th colspan="2">DATI NUOVA SEDE LEGALE</th>
	</tr>
	
	<tr>
		<td class="formLabel">INDIRIZZO</td>
		<td> 
			<%if(StabilimentoDettaglio.getTipoAttivita() == 1) { %> 
				<input type="button" id="ins_indirizzo_sede_legale" value="INSERISCI INDIRIZZO" 
				onclick="inserisciNuovoIndirizzo()">
				<input type="text" id="provincia_sede_legale" name="provincia_leg" placeholder="PROVINCIA" value="" size="18" readonly="" style="display: none">
				<input type="text" id="comune_sede_legale" name="comune_leg" placeholder="COMUNE" value="" size="30" readonly="" style="display: none">
			<% } else { %>
				<input type="button" id="ins_indirizzo_sede_legale" value="INSERISCI INDIRIZZO" 
				onclick="inserisciNuovoIndirizzoRidotto('${comune_id_sede_legale}', '${provincia_id_sede_legale}')">
				<input type="text" id="provincia_sede_legale" name="provincia_leg" placeholder="PROVINCIA" value="${provincia_sede_legale}" size="18" readonly="" style="display: none">
				<input type="text" id="comune_sede_legale" name="comune_leg" placeholder="COMUNE" value="${comune_sede_legale}" size="30" readonly="" style="display: none">
			<%} %>
			
			<input type="text" id="toponimo_sede_legale" name="toponimo_sede_legale" placeholder="TOPONIMO" value="" size="10" readonly="" style="display: none">
			<input type="text" id="via_sede_legale" name="_b_via_leg" placeholder="VIA" value="" size="38" readonly="" style="display: none">
			<input type="text" id="civico_sede_legale" name="_b_civico_leg" placeholder="CIVICO" value="" size="10" readonly="" style="display: none">
			<input type="text" id="cap_sede_legale" name="_b_cap_leg" placeholder="CAP" value="" size="5" maxlength="5" onkeydown="return false;" required="" autocomplete="off" style="display: none">

			<input type="hidden" id="comuneIdSedeLegale" name="_b_cod_comune_leg">
			<input type="hidden" id="provinciaIdSedeLegale" name="_b_cod_provincia_leg">
			<input type="hidden" id="topIdSedeLegale" name="_b_toponimo_leg">
		</td>
	</tr>
	
	<tr>
		<td class="formLabel">COORDINATE</td>
		<td>
			<input type="text" id="lat_sede_legale" name="_b_latitudine_leg" placeholder="LATITUDINE" value="" onkeydown="return false;" required="" autocomplete="off">
			<input type="text" id="long_sede_legale" name="_b_longitudine_leg" placeholder="LONGITUDINE" value="" onkeydown="return false;" required="" autocomplete="off">
			<input type="button" id="calcola_coord_sede_legale" value="CALCOLA COORDINATE" 
				onclick="if(document.getElementById('cap_sede_legale').value != ''){ 
						getCoordinate('toponimo_sede_legale','via_sede_legale','comune_sede_legale','provincia_sede_legale','cap_sede_legale','lat_sede_legale','long_sede_legale');
					} else { alert('Attenzione! inserire prima l\'indirizzo');}">
		</td>
	</tr>
	
</tbody>
</table>
<br><br>
<center>

<button type="submit" class="yellowBigButton" style="width: 250px;">Salva</button>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

<button type="button" id="torna" class="yellowBigButton" style="width: 250px;"
	onClick="loadModalWindowCustom('Attendere Prego...'); window.location.href='GestionePraticheAction.do?command=ListaPraticheStabilimenti'">Annulla</button>

</center>
</div>
</form>
<br><br>

<script src="javascript/noscia/widget.js"></script>
<script>

function validateForm(){
	
	loadModalWindowCustom('Attendere Prego...');
	return true;
}

resetStar();

if(document.getElementById('tipo_impresa').value == '11'){
	alert('Attenzione: operazione non consentita per stabilimenti con impresa individuale!');
	loadModalWindowCustom('Attendere Prego...');
	window.location.href='GestioneAnagraficaAction.do?command=Details&altId='+ document.getElementById('altId').value;
}

if(document.getElementById('causaleRicevuta').value != '1'){
	document.getElementById('dati_pratica').style.display = 'none';
	document.getElementById('torna').onclick = function(){
		loadModalWindowCustom('Attendere Prego...');
		window.location.href='GestioneAnagraficaAction.do?command=Details&altId='+ document.getElementById('altId').value;
	};
}else{
	document.getElementById('id_causale').value = "1";
	document.getElementById('specifica_causale').style='display: none';
	document.getElementById('operazione_scheda').style='display:'
}

function inserisciNuovoIndirizzoRidotto(idcomuneattule, idprovinciaattuale){
	openCapWidgetRidotta('toponimo_sede_legale', 'topIdSedeLegale', 'via_sede_legale', 'civico_sede_legale', 'comune_sede_legale', 
				  'comuneIdSedeLegale', 'cap_sede_legale', 'provincia_sede_legale', 'provinciaIdSedeLegale',
				  'campania', document.getElementById('id_asl_imp').value, idcomuneattule, idprovinciaattuale); 
	document.getElementById('lat_sede_legale').value = '';
	document.getElementById('long_sede_legale').value = '';
}

function inserisciNuovoIndirizzo(){
	openCapWidget('toponimo_sede_legale', 'topIdSedeLegale', 'via_sede_legale', 'civico_sede_legale', 'comune_sede_legale', 
				  'comuneIdSedeLegale', 'cap_sede_legale', 'provincia_sede_legale', 'provinciaIdSedeLegale'); 
	document.getElementById('lat_sede_legale').value = '';
	document.getElementById('long_sede_legale').value = '';
}



</script>