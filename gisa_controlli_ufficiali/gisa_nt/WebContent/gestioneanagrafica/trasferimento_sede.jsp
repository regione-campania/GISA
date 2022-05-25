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
			<a href="GestioneAnagraficaAction.do?command=Details&altId=${altId}">SCHEDA</a> > TRASFERIMENTO SEDE OPERATIVA
		</td>
	</tr>
</table>

<form class="form-horizontal" role="form" method="post" action="GestioneAnagraficaAction.do?command=TrasferimentoSedeOperativa" onsubmit="return validateForm();">
<b>MODIFICA SCHEDA: TRASFERIMENTO SEDE OPERATIVA</b><br>
<input type="hidden" id="stabId" name="stabId" value="<%=StabilimentoDettaglio.getIdStabilimento()%>"/>
<input type="hidden" id="altId" name="altId" value="<%=StabilimentoDettaglio.getAltId()%>"/>
<input type="hidden" id="id_asl_stab" value="<%=StabilimentoDettaglio.getSedeOperativa().getIdAsl()%>">
<input type="hidden" id="tipo_linee_attivita" value="<%=StabilimentoDettaglio.getTipoAttivita()%>" />
<input type="hidden" id="stato_stabilimento" value="<%=StabilimentoDettaglio.getStato()%>" />

<dhv:permission name="trasferimento_sede_stabilimento_tutte_asl-view"> 
	<body onload ="document.getElementById('id_asl_stab').value = '-1'">   
	</body>
</dhv:permission>

<div id="dati_pratica" style="border: 1px solid black; background: #BDCFFF">
<br>
&nbsp;&nbsp;&nbsp;&nbsp;NUMERO PRATICA: ${numeroPratica} <br>
&nbsp;&nbsp;&nbsp;&nbsp;DATA PEC: ${dataPratica} <br>
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
		<td><%=StabilimentoDettaglio.getSedeOperativa().getDescrizioneAsl()%> </td>
	</tr>
	
	
	<tr>
		<th colspan="2">DATI ATTUALE SEDE OPERATIVA</th>
	</tr>
	<tr>
		<td class="formLabel">INDIRIZZO</td>
		<td>
			${provincia_stabilimento}&nbsp;&nbsp;
			${comune_stabilimento}&nbsp;&nbsp;
			<%=StabilimentoDettaglio.getSedeOperativa().getDescrizioneToponimo()%>&nbsp;&nbsp;
			<%=StabilimentoDettaglio.getSedeOperativa().getVia() %>&nbsp;&nbsp;
			<%=StabilimentoDettaglio.getSedeOperativa().getCivico() %>&nbsp;&nbsp;
			<%=StabilimentoDettaglio.getSedeOperativa().getCap() %>
		 </td>
	</tr>
	
	<tr>
		<td class="formLabel">COORDINATE</td>
		<td>
			<%=StabilimentoDettaglio.getSedeOperativa().getLatitudine() %> &nbsp;&nbsp;
		 	<%=StabilimentoDettaglio.getSedeOperativa().getLongitudine() %>
		</td>
	</tr>
		
	
	<tr>
		<th colspan="2">DATI NUOVA SEDE OPERATIVA</th>
	</tr>
	
	<tr>
		<td class="formLabel">INDIRIZZO</td>
		<td> 
			<input type="button" id="ins_indirizzo_stabilimento" value="INSERISCI INDIRIZZO" 
				onclick="inserisciNuovoIndirizzo('${comune_id_stabilimento}', '${provincia_id_stabilimento}')">
			<input type="text" id="provincia_stabilimento" name="provincia_stabilimento" placeholder="PROVINCIA" value="${provincia_stabilimento}" size="18" readonly="" style="display: none">
			<input type="text" id="comune_stabilimento" name="comune_stabilimento" placeholder="COMUNE" value="${comune_stabilimento}" size="30" readonly="" style="display: none">	
			<input type="text" id="toponimo_stabilimento" name="toponimo_stabilimento" placeholder="TOPONIMO" value="" size="10" readonly="" style="display: none">
			<input type="text" id="via_stabilimento" name="_b_via_stab" placeholder="VIA" value="" size="38" readonly="" style="display: none">
			<input type="text" id="civico_stabilimento" name="_b_civico_stab" placeholder="CIVICO" value="" size="10" readonly="" style="display: none">
			<input type="text" id="cap_stabilimento" name="_b_cap_stab" placeholder="CAP" value="" size="5" maxlength="5" onkeydown="return false;" required="" autocomplete="off" style="display: none">
			<input type="hidden" id="comuneIdStabilimento" name="_b_cod_comune_stab">
			<input type="hidden" id="provinciaIdStabilimento" name="_b_cod_provincia_stab">
			<input type="hidden" id="topIdStabilimento" name="_b_toponimo_stab">
		</td>
	</tr>
	
	<tr>
		<td class="formLabel">COORDINATE</td>
		<td>
			<input type="text" id="lat_stabilimento" name="_b_latitudine_stab" placeholder="LATITUDINE" value="" onkeydown="return false;" required="" autocomplete="off">
			<input type="text" id="long_stabilimento" name="_b_longitudine_stab" placeholder="LONGITUDINE" value="" onkeydown="return false;" required="" autocomplete="off">
			<input type="button" id="calcola_coord_stabilimento" value="CALCOLA COORDINATE" 
				onclick="if(document.getElementById('cap_stabilimento').value != ''){ 
							getCoordinate('toponimo_stabilimento','via_stabilimento','comune_stabilimento','provincia_stabilimento','cap_stabilimento','lat_stabilimento','long_stabilimento');
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

controlla();

function validateForm(){
	
	loadModalWindowCustom('Attendere Prego...');
	return true;
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

function inserisciNuovoIndirizzo(idcomuneattule, idprovinciaattuale){
	openCapWidgetRidotta('toponimo_stabilimento','topIdStabilimento','via_stabilimento','civico_stabilimento','comune_stabilimento',
						 'comuneIdStabilimento', 'cap_stabilimento','provincia_stabilimento','provinciaIdStabilimento',
						 'campania', document.getElementById('id_asl_stab').value, idcomuneattule, idprovinciaattuale); 
	document.getElementById('lat_stabilimento').value = '';
	document.getElementById('long_stabilimento').value = '';
}

resetStar();

function controlla(){
	if(document.getElementById('tipo_linee_attivita').value == '2'){
		document.getElementById('dati_pratica').style.display = 'none';
		document.getElementById('specifica_causale').style='display: none';
		document.getElementById('operazione_scheda').style='display: none';
		alert('Attenzione: gli stabilimenti senza sede fissa non hanno sede operativa, per tali stabilimenti è concesso solo il cambio sede legale!');
		loadModalWindowCustom('Attendere Prego...');
		window.location.href='GestioneAnagraficaAction.do?command=Details&altId='+ document.getElementById('altId').value;
	}
	
	if(document.getElementById('stato_stabilimento').value == '4'){
		document.getElementById('dati_pratica').style.display = 'none';
		document.getElementById('specifica_causale').style='display: none';
		document.getElementById('operazione_scheda').style='display: none';
		alert('Attenzione: trasferimento sede non consentito in quanto questo stabilimento risulta cessato!');
		loadModalWindowCustom('Attendere Prego...');
		window.location.href='GestioneAnagraficaAction.do?command=Details&altId='+ document.getElementById('altId').value;
	}
}

</script>