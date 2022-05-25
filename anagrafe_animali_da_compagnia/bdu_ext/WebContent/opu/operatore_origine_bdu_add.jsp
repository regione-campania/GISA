<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.opu.base.Stabilimento"%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttivaList"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.aspcfs.modules.opu.base.SoggettoFisico"%>
<jsp:useBean id="newStabilimento" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request" />
<!-- sede inserita -->
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ListaLineeProduttive" class="org.aspcfs.modules.opu.base.LineaProduttivaList" scope="request" />
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/gestoreCodiceFiscale.js"></script>

<script> 

var tipologia_privato = '<%=LineaProduttiva.idAggregazionePrivato%>' ;
var tipologia_sindaco = '<%=LineaProduttiva.idAggregazioneSindaco%>' ;
var tipologia_sindaco_fr = '<%=LineaProduttiva.idAggregazioneSindacoFR%>' ;
var tipologia_colonia = '<%=LineaProduttiva.idAggregazioneColonia%>' ;

function Associa(){
	var selectIndirizzo= document.getElementById('via');
	alert("id: "+selectIndirizzo.selectedIndex);
	var indText = selectIndirizzo.options[selectIndirizzo.selectedIndex].innerHTML;
	alert("via: "+indText);
	document.getElementById('via').value=selectIndirizzo.selectedIndex;
	alert("Nuova via: "+document.getElementById('via').value);
	document.getElementById('viaTesto	').value=indText;
	alert("Nuova viaTesto: "+document.getElementById('viaTesto').value);
	alert("ok");
}

function mostra_pagina(){
	if(document.getElementById('idLineaProduttiva').value == tipologia_privato ){
		document.getElementById('info_impresa_rs').style.display="none";
		document.getElementById('info_impresa_pi').style.display="none";
		document.getElementById('info_impresa_cf').style.display="none";
		document.getElementById('bdu').value = "no";
	}else{
		document.getElementById('bdu').value = "si";
		document.getElementById('info_soggetto_nm').style.display="none";
		document.getElementById('info_soggetto_cg').style.display="none";
		document.getElementById('info_soggetto_sex').style.display="none";
		document.getElementById('info_soggetto_cf').style.display="none";
	}
}

function checkLineaProduttiva(){
//	alert('check');
	document.forms[0].doContinueLp.value = 'false';
//	alert(document.forms[0].doContinueLp.value);
	document.forms[0].submit();
}
</script>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	<tr>
		<th colspan="2"><strong>TIPOLOGIA PROPRIETARIO</strong></th>
	</tr>
	<tr>
		<td nowrap class="formLabel"><dhv:label	name="opu.stabilimento.linea_produttiva"></dhv:label></td>
		<td><select name="idLineaProduttiva" id="idLineaProduttiva"
			onchange="checkLineaProduttiva()" <%=(User.getRoleId() == new Integer(ApplicationProperties.getProperty("ID_RUOLO_LLP")) || User.getRoleId() == new Integer(ApplicationProperties.getProperty("UNINA")))? "disabled=\"disabled\" " : "" %>  >
			<option value="-1"><dhv:label name="opu.stabilimento.linea_produttiva"></dhv:label></option>
			<%
				Iterator itLinee = ListaLineeProduttive.iterator();
				while (itLinee.hasNext()) {
					LineaProduttiva lp = (LineaProduttiva) itLinee.next();
			%>
				<option value="<%=lp.getId()%>" <%=(LineaProduttivaScelta.getIdRelazioneAttivita() == lp.getId()) ? "selected" : ""%>><%=lp.getAttivita()%>
				</option>
				<%
					}
				%>
		</select></td>
	</tr>
</table>
<br>
<input type="hidden" name="doContinueLp" id="doContinueLp" value="" />
<input type="hidden" name="soloPrivato" id="soloPrivato" value="<%=request.getAttribute("soloPrivato")!=null && ((Boolean)request.getAttribute("soloPrivato"))%>" />
<%
	if (LineaProduttivaScelta.getIdRelazioneAttivita() > 0) {
		int i = LineaProduttivaScelta.getIdRelazioneAttivita();
		String label_operatore = "opu.operatore_" + i;
		String label_operatore_rag_sociale = "opu.operatore.ragione_sociale_"+ i;

String label_soggetto_fisico = "opu.soggetto_fisico_" + i;
if (request.getAttribute("Errore")!=null){
	SoggettoFisico soggetto = (SoggettoFisico)request.getAttribute("SoggettoEsistente");
	%>
	<font color = "red">Attenzione : il soggetto che si sta censendo risulta anagrafato per un'altra asl. Rivolgersi al Servisio di HelpDesk</font>
	<%
}
%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" >
	<tr>
		<th colspan="2"><strong><dhv:label name="<%=label_operatore%>"></dhv:label></strong></th>
	</tr>
	
	<dhv:evaluate if="<%=(LineaProduttivaScelta.getIdRelazioneAttivita() != LineaProduttiva.idAggregazioneSindaco && LineaProduttivaScelta.getIdRelazioneAttivita() != LineaProduttiva.idAggregazioneSindacoFR) %>">
	<%if (request.getAttribute("in_regione")==null){ %>
	<tr>
		<td class="formLabel"><dhv:label name="opu.sede_legale.inregione"></dhv:label>
		</td>
		<td><select  name="inregione" onchange="checkLineaProduttiva()" id="inregione">
			<option value="si"
				<%=(!stabilimento.isFlagFuoriRegione()) ? "selected"
								: ""%>>SI</option>
			<option value="no"
				<%=(stabilimento.isFlagFuoriRegione()) ? "selected"
						: ""%>>NO</option>
		</select></td>
	</tr>
	<% }else{ %>
<tr>
		<td class="formLabel"><dhv:label name="opu.sede_legale.inregione"></dhv:label>
		</td>
		<td><input type="text" size="3" name="inregione" onchange="checkLineaProduttiva()" id="inregione" value='<%=((String)request.getAttribute("in_regione")).equalsIgnoreCase("si") ? "SI": "NO"%>' disabled/>
        	<input type="hidden" size="3" name="in_regione" id="in_regione" value='<%=((String)request.getAttribute("in_regione")).equalsIgnoreCase("si") ? "SI": "NO"%>' />
        </td>
	</tr>


	<% } %>
</dhv:evaluate>

	<!-- PARTE IMPRESA -->
	<tr id="info_impresa_rs">
		<td nowrap class="formLabel"><dhv:label	name="<%=label_operatore_rag_sociale%>"></dhv:label></td>
		<td><input type="text" size="20" maxlength="200" id="ragioneSociale" name="ragioneSociale"
			value="<%=(Operatore.getRagioneSociale() != null) ? Operatore.getRagioneSociale() : ""%>"><font color="red">&nbsp;(*)</font></td>
	</tr>
	<tr id="info_impresa_pi">
		<td nowrap class="formLabel"><dhv:label name="opu.operatore.piva"></dhv:label>
		</td>
		<td><input type="text" size="20" maxlength="11" id="partitaIva" name="partitaIva"
			value="<%=(Operatore.getPartitaIva() != null) ? Operatore.getPartitaIva() : ""%>">
		</td>
	</tr>
	<tr id="info_impresa_cf">
		<td nowrap class="formLabel"><dhv:label name="opu.operatore.cf"></dhv:label>
		</td>
		<td><input type="text" size="20" maxlength="16" id="codFiscale"
			name="codFiscale"
			value="<%=(Operatore.getCodFiscale() != null) ? Operatore.getCodFiscale() : ""%>">
		</td>
	</tr>
	</div>
	<!-- FINE PARTE IMPRESA -->
	
	<!-- PARTE PRIVATO -->
	<tr id="info_soggetto_nm">
		<td class="formLabel" nowrap><dhv:label	name="opu.soggetto_fisico.nome"></dhv:label></td>
		<td><input type="text" size="30" maxlength="50" id="nome" name = "nome">
	</tr>
	<tr id="info_soggetto_cg">
		<td class="formLabel" nowrap><dhv:label name="opu.soggetto_fisico.cognome"></dhv:label></td>
		<td><input class="todisable" type="text" size="30" maxlength="50" id="cognome" name="cognome" value="">&nbsp;<font color="red">(*)</font></td>
	</tr>
	<tr id="info_soggetto_cf">
		<td class="formLabel" nowrap><dhv:label name="opu.soggetto_fisico.cf"></dhv:label></td>
		<td>
		<input  type="text" name="codFiscaleSoggetto" id="codFiscaleSoggetto" />
			<input type = "checkbox" name = "estero" id = "estero" value="NO" onclick="if(this.checked){this.value='true';document.getElementById('calcoloCF').style.visibility='hidden';document.getElementById('codFiscaleSoggetto').readOnly=false;} else {this.value='false';document.getElementById('calcoloCF').style.visibility='visible';document.getElementById('codFiscaleSoggetto').readOnly=true;document.getElementById('codFiscaleSoggetto').value='';}" >Provenienza Estera
	</tr>
	<tr id="info_soggetto_sex">
		<td class="formLabel" nowrap><dhv:label name="opu.soggetto_fisico.sesso"></dhv:label></td>
		<td><input class="todisable" type="radio" name="sesso" id="sesso1" value="M" checked="checked">M <input type="radio" name="sesso" id="sesso2" value="F">F</td>
	</tr>
	<!-- FINE PARTE PRIVATO -->
	<tr>
		<td class="formLabel" nowrap><dhv:label name="opu.soggetto_fisico.provincia_residenza"></dhv:label></td>
		<td>
			<script> $(function() { $( "#addressLegaleCountry" ).combobox(); }); </script>
			<select class="todisable" name="addressLegaleCountry" id="addressLegaleCountry">
				<option value="-1">Inserire le prime 4 lettere</option>
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(dopo aver digitato le prime 5 lettere apparirà in automatico la lista delle province che iniziano con le lettere digitate)
			<font color="red">(*)</font> <%-- ComuniList.getHtmlSelect("searchcodeIdComune", -1) --%>
			<input type="hidden" name="addressLegaleCountryTesto" id="addressLegaleCountryTesto" /> 
		</td>
	</tr>
	<tr>
		<td class="formLabel" nowrap><dhv:label
			name="opu.soggetto_fisico.comune_residenza"></dhv:label></td>
		<td><select class="todisable" name="addressLegaleCity" id="addressLegaleCity">
			<option value="-1">Inserire le prime 4 lettere</option>
		</select>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(dopo aver digitato le prime 5 lettere apparirà in automatico la lista dei comuni che iniziano con le lettere digitate)
		<font color="red">(*)</font> <%-- ComuniList.getHtmlSelect("searchcodeIdComune", -1) --%>
		<input type="hidden" name="addressLegaleCityTesto"
			id="addressLegaleCityTesto" /> <!--      <input type="text" size="30" maxlength="50" id = "" name="addressLegaleCountry" value="">-->
		</td>
	</tr>
</table>
<br />
<br>

<script type="text/javascript">
//alert(document.forms[0].doContinueLp.value);
mostra_pagina();
</script>
<% } 
boolean soloPrivato = request.getAttribute("soloPrivato")!=null && ((Boolean)request.getAttribute("soloPrivato"));
if(soloPrivato){
%>
<script type="text/javascript">
		//Se già settata la tipologia di proprietario
		if(document.getElementById('idLineaProduttiva').value=='-1')
		{
			document.getElementById('idLineaProduttiva').value = '<%=LineaProduttiva.idAggregazionePrivato%>';
			checkLineaProduttiva();
		}
		document.getElementById('idLineaProduttiva').disabled = true;
</script>
<% } %>