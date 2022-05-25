<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.admin.base.User"%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>
 <script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/gestoreCodiceFiscale.js"></script>
 
<script>
function CalcolaCF() {
	
  		var nomeCalc=""; var cognomeCalc=""; var comuneCalc=""; var nascitaCalc ="";
  		var giorno=""; var mese=""; var anno=""; var sesso="";var comuneResidenza= "" ;
  
  		if ( document.forms[0].sesso[0].checked )
  			sesso = "M";
  		else
  			sesso = "F";
			
  		if ( document.forms[0].nome.value != "" ) {
  			nomeCalc =  document.forms[0].nome.value;
			nomeCalc=nomeCalc.replace(/^\s+|\s+$/g,"").replace(/'/g,"");
  		}
  	
  		if ( document.forms[0].cognome.value  != "" ) {
  			cognomeCalc = document.forms[0].cognome.value;
  			cognomeCalc=cognomeCalc.replace(/^\s+|\s+$/g,"").replace(/'/g,"");
  		}    
  
  		if ( document.forms[0].comuneNascita.value != "" ) {
  			comuneCalc = document.forms[0].comuneNascita.value;
  		}  

  		

  		
  	
  		if ( document.forms[0].dataNascita.value != "" ) {
  			nascitaCalc = document.forms[0].dataNascita.value;
  			giorno = nascitaCalc.substring(0,2);
  			mese = nascitaCalc.substring(3,5);
  			anno = nascitaCalc.substring(6,10);
  		}  
		

  	if (cognomeCalc!="" && nomeCalc!="" && giorno!= "" && mese!="" && anno!= "" && sesso!= "" && comuneCalc!=""){
  	  	codCF= CalcolaCodiceFiscaleCompleto(cognomeCalc, nomeCalc, giorno, mese, anno, sesso, comuneCalc);
  	  	if (codCF=='[Comune non presente in banca dati]')
  	  	  	alert(codCF);
  	  	else
		document.getElementById('codFiscaleSoggetto').value=codCF ;
  		}
  	else
  	  	alert('Inserire tutti i campi necessari per il calcolo del codice fiscale ed il comune di residenza');
  		
  	}
    

  
</script>
 
<script>
function checkLineaProduttiva(){
//	alert('check');
	document.forms[0].doContinueStab.value = 'false';
//	alert(document.forms[0].doContinueLp.value);
	document.forms[0].submit();
}
</script>


<input type="hidden" id ="idRelazione" name="idRelazione" value="<%=id_relazione %>"></input>
<dhv:evaluate if="<%=(id_relazione == LineaProduttiva.idAggregazioneImportatore || id_relazione == LineaProduttiva.idAggregazioneCanile || id_relazione == LineaProduttiva.IdAggregazioneOperatoreCommerciale)%>">
</br>
<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="details">
	<tr>
		<th colspan="2"><strong><dhv:label name="">Informazioni aggiuntive</dhv:label></strong></th>
	</tr>
	<dhv:evaluate if="<%=(id_relazione == LineaProduttiva.idAggregazioneImportatore || id_relazione == LineaProduttiva.IdAggregazioneOperatoreCommerciale)%>">
		<tr>
			<td class="formLabel" nowrap>AUTORIZZAZIONE</td>
			<td><input class="todisable"  type="text" size="30" maxlength="50" id="autorizzazione"
				name="autorizzazione"
				value=""></td>
		</tr>
		
		<tr>
			<td class="formLabel" nowrap>SUPERFICE DESTINATA AL RICOVERO ANIMALI</td>
			<td><input  type="text" size="30" maxlength="50" id="mqDisponibili"
				name="mqDisponibili"
				value=""></td>
		</tr>
	</dhv:evaluate>
	
	<dhv:evaluate if="<%=(id_relazione == LineaProduttiva.idAggregazioneImportatore)%>">
	<tr>
	
	<td class="formLabel"><dhv:label
				name="">Codice uvac</dhv:label></td>
		<td><input type="text" name="codiceUvac"
			id="codiceUvac" value="" /></td>
	</tr>
	</dhv:evaluate>
<dhv:evaluate if="<%=id_relazione == LineaProduttiva.idAggregazioneCanile %>">

   <tr>
		<td nowrap class="formLabel"><dhv:label name="">CANILE MUNICIPALE </dhv:label>
		</td>
		<td><input type="checkbox" id="municipale"
			name="municipale"
			>


		</td>
	</tr>
	
	
	<tr>
		<td nowrap class="formLabel"><dhv:label name="">CENTRO DI STERILIZZAZIONE </dhv:label>
		</td>
		<td><input type="checkbox"  id="centroSterilizzazione"
			name="centroSterilizzazione">


		</td>
	</tr>
	<tr>	
	<td nowrap class="formLabel"><dhv:label name="">CLINICA / OSPEDALE</dhv:label>
		</td>
		<td><input type="checkbox"  id="flagClinicaOspedale"
			name="flagClinicaOspedale">


		</td>

	</tr>
   <tr>
		<td nowrap class="formLabel"><dhv:label name="">ABUSIVO </dhv:label>
		</td>
		<td><input type="checkbox" id="abusivo"
			name="abusivo"
			>


		</td>
	</tr>
	
		<tr>
			<td class="formLabel" nowrap>SUPERFICE DESTINATA AL RICOVERO ANIMALI</td>
			<td><input  type="text" size="30" maxlength="50" id="mqDisponibili"
				name="mqDisponibili"
				value=""></td>
		</tr>
	
	<tr>
			<td class="formLabel" nowrap>AUTORIZZAZIONE</td>
			<td><input class="todisable"  type="text" size="30" maxlength="50" id="autorizzazione"
				name="autorizzazione"
				value=""></td>
		</tr>
		<tr>
			<td nowrap class="formLabel">DATA AUTORIZZAZIONE</td>
			<td><input class="todisable"  readonly type="text" id="dataAutorizzazione"
				name="dataAutorizzazione" size="10"
				value="" />
			<a href="#"
				onClick="cal19.select(document.forms[0].dataAutorizzazione,'anchor19','dd/MM/yyyy'); return false;"
				NAME="anchor19" ID="anchor19"> <img
				src="images/icons/stock_form-date-field-16.gif" border="0"
				align="absmiddle"></a></td>
		</tr>
	
</dhv:evaluate>

<tr>
		<td class="formLabel" nowrap><dhv:label
			name="opu.stabilimento.mail"></dhv:label></td>
		<td><input class="" type="text" size="30" maxlength="50" id="email_lp"
			name="email_lp" value=""></td>

	</tr>

	<tr>
		<td class="formLabel" nowrap><dhv:label
			name="opu.stabilimento.telefono"></dhv:label></td>
		<td><input class="todisable" type="text" size="30" maxlength="50" id="telefono1_lp"
			name="telefono1_lp" value=""></td>

	</tr>

	<tr>
		<td class="formLabel" nowrap><dhv:label
			name="opu.stabilimento.telefono2"></dhv:label></td>
		<td><input class="todisable" type="text" size="30" maxlength="50" id="telefono2_lp"
			name="telefono2_lp" value=""></td>

	</tr>

	<tr>
		<td class="formLabel" nowrap><dhv:label
			name="opu.stabilimento.fax"></dhv:label></td>
		<td><input class="todisable" type="text" size="30" maxlength="50" id="fax_lp"
			name="fax_lp" value=""></td>

	</tr>

</table>
</dhv:evaluate>
</br>
</br>
<%
	String label_stabilimento_sede = "";

	if ((id_relazione > -1)) {
		label_stabilimento_sede = "opu.stabilimento.sede_"
				+ id_relazione;
	} else {
		label_stabilimento_sede = "opu.stabilimento.sede";
	}
%>
<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="details">
	<tr>
		<th colspan="2"><strong><dhv:label
			name="<%=label_stabilimento_sede%>"></dhv:label></strong></th>
	</tr>

	<tr>
		<td class="formLabel"><dhv:label name="opu.stabilimento.asl">Asl</dhv:label>
		</td>
		<td><dhv:evaluate if="<%=!(User.getSiteId() > -1)%>">
			<p id="descrizioneasl"><%=newStabilimento.getSedeOperativa().getDescrizioneAsl()%></p>
<%-- 			<input type="hidden" name="idAsl" id="idAsl"
				value="<%=newStabilimento.getSedeOperativa().getIdAsl()%>"> --%>
		</dhv:evaluate> <dhv:evaluate if="<%=(User.getSiteId() > -1)%>">
			<p id="descrizioneasl"><%=AslList.getSelectedValue(User.getSiteId())%></p>
			<input type="hidden" name="idAsl" id="idAsl"
				value="<%=User.getSiteId()%>">
		</dhv:evaluate></td>
	</tr>
	
	<tr>
		<td nowrap class="formLabel">
			
		</td>
		<td>
<%
	if(id_relazione == LineaProduttiva.idAggregazioneCanile)
	{
%>		
		<a style="text-decoration:underline; color:#006b9c;" onmouseover="this.style.textDecoration='none'; this.style.color='black';" onmouseout="this.style.textDecoration='underline'; this.style.color='#006b9c';" onclick="selezionaIndirizzo('106','callbackResidenzaProprietarioInserimentoColonia','','FUORI REGIONE')">Seleziona</a>
		
	
<%		
	}
	else
	{
		 %>
	<a style="text-decoration:underline; color:#006b9c;" onmouseover="this.style.textDecoration='none'; this.style.color='black';" onmouseout="this.style.textDecoration='underline'; this.style.color='#006b9c';" onclick="selezionaIndirizzo('106','callbackResidenzaProprietarioInserimentoColonia','','<%=(newStabilimento.isFlagFuoriRegione())?("FUORI REGIONE"):("CAMPANIA")%>','<%=(User.getSiteId()>0 && !newStabilimento.isFlagFuoriRegione())?(User.getSiteId()):("")%>')">Seleziona</a>
		 
		 
		 <%
		
	}
	
%>
					</td>
	</tr>
	<tr>
		<td nowrap class="formLabel"><dhv:label
			name="opu.stabilimento.provincia"></dhv:label></td>
		<td><dhv:evaluate
			if="<%=(!(User.getSiteId() > -1) || newStabilimento
							.isFlagFuoriRegione())%>">
			<input type="text" name="searchcodeIdprovinciaSelect" id="searchcodeIdprovinciaSelect" readonly="readonly">
			<font color="red"> *</font>
			<input type="hidden" name="searchcodeIdprovinciaTesto"
				id="searchcodeIdprovinciaTesto" />
			<input type="hidden" name="searchcodeIdprovincia" id="searchcodeIdprovincia" />
		</dhv:evaluate> <dhv:evaluate
			if="<%=(User.getSiteId() > -1 && !newStabilimento
							.isFlagFuoriRegione())%>">
			<%=provinciaAsl.getDescrizione()%>
			<input type="hidden" name="searchcodeIdprovinciaAsl"
				id="searchcodeIdprovinciaAsl" value="<%=provinciaAsl.getCodice()%>" />
		</dhv:evaluate></td>
	</tr>
	<tr>
		<td nowrap class="formLabel" name="province" id="province"><dhv:label
			name="opu.stabilimento.comune"></dhv:label></td>
		<td>
		<%
			System.out.println(newStabilimento.getSedeOperativa().getComune());
		%><input type="text" name="searchcodeIdComuneSelect" id="searchcodeIdComuneSelect" readonly="readonly">
		  <font color="red"> *</font> <input
			type="hidden" name="searchcodeIdComuneTesto"
			id="searchcodeIdComuneTesto" />
			<input type="hidden" name="searchcodeIdComune" id="searchcodeIdComune" />
		</td>
	</tr>
	<tr>
		<td nowrap class="formLabel"><dhv:label
			name="opu.stabilimento.indirizzo"></dhv:label></td>
		<td>
			<input type="text" name="via" id="via" readonly="readonly"/>
			<input type="hidden" name="viaTesto" id="viaTesto" /><font color="red"> *</font>
		</td>
	</tr>
	<tr>
		<td nowrap class="formLabel">
			<dhv:label name="opu.stabilimento.cap"></dhv:label>
		</td>
		<td>
			<input type="text" size="28" id="cap" name="cap" maxlength="5" readonly="readonly" value="<%=newStabilimento.getSedeOperativa().getCap()%>"></td>
	</tr>
	<tr>
		<td nowrap class="formLabel"><dhv:label
			name="opu.stabilimento.co"></dhv:label></td>
		<td><input type="text" size="40" name="presso" maxlength="80"
			value="<%=""%>"></td>
	</tr>



<tr class="containerBody">
		<td class="formLabel" nowrap><dhv:label
			name="opu.stabilimento.latitudine"></dhv:label></td>
		<td><input type="text" id="latitudine" name="latitudine"
			size="30"
			value="<%=((Double) newStabilimento.getSedeOperativa()
							.getLatitudine() != null) ? newStabilimento
					.getSedeOperativa().getLatitudine()
					+ "" : ""%>">
		</td>
	</tr>
	<tr class="containerBody">
		<td class="formLabel"><dhv:label
			name="opu.stabilimento.longitudine">Longitude</dhv:label></td>
		<td><input type="text" id="longitudine" name="longitudine"
			size="30"
			value="<%=((Double) newStabilimento.getSedeOperativa()
							.getLongitudine() != null) ? newStabilimento
					.getSedeOperativa().getLongitudine()
					+ "" : ""%>"></td>
	</tr>
	<!-- <tr style="display: block">
		<td colspan="2"><input id="coordbutton" type="button"
			value="Calcola Coordinate"
			onclick="javascript:showCoordinate(document.getElementById('via').value, document.forms['addSede'].comune.value,document.forms['addSede'].provincia.value, document.forms['addSede'].cap.value, document.forms['addSede'].latitudine, document.forms['addSede'].longitudine);" />
		</td>
	</tr> -->	
</table>

</br>
</br>

<dhv:evaluate if="<%=false%>">
	<%
		String label_responsabile = "";
		if ((id_relazione > -1)) {
			label_responsabile = "opu.stabilimento.soggetto_fisico_"
					+ id_relazione;
		} else {
			label_responsabile = "opu.stabilimento.soggetto_fisico_operativo";
		}
	%>

	<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
			<th colspan="2"><strong><dhv:label
				name="<%=label_responsabile%>"></dhv:label></strong></th>
		</tr>
		<tr>
			<td class="formLabel"><dhv:label
				name="opu.sede_legale.inregione"></dhv:label></td>
			<td><select class="todisable" name="inregioneRappOperativo"
				id="inregioneRappOperativo">
				<option value="si">SI</option>
				<option value="no">NO</option>
			</select></td>
		</tr>
		<tr>
		<td class="formLabel" nowrap><dhv:label
			name="opu.soggetto_fisico.nome"></dhv:label></td>
		<td><input class="todisable" type="text" size="30" maxlength="50" id="nome"
			name="nome" value=""><font color="red">*</font></td>
	</tr>
	<tr>
		<td class="formLabel" nowrap><dhv:label
			name="opu.soggetto_fisico.cognome"></dhv:label></td>
		<td><input class="todisable" type="text" size="30" maxlength="50" id="cognome"
			name="cognome" value=""><font color="red">*</font></td>
	</tr>
	

	<tr id="">
		<td class="formLabel" nowrap><dhv:label
			name="opu.soggetto_fisico.sesso"></dhv:label></td>
		<td><input class="todisable" type="radio" name="sesso" id="sesso1" value="M"
			checked="checked">M <input type="radio" name="sesso"
			id="sesso2" value="F">F</td>
	</tr>


	
	<tr>
		<td nowrap class="formLabel"><dhv:label
			name="opu.soggetto_fisico.data_nascita"></dhv:label></td>
		<td><input class="todisable" readonly type="text" id="dataNascita"
			name="dataNascita" size="10" /> <a href="#"
			onClick="cal19.select(document.forms[0].dataNascita,'anchor19','dd/MM/yyyy'); return false;"
			NAME="anchor19" ID="anchor19"> <img
			src="images/icons/stock_form-date-field-16.gif" border="0"
			align="absmiddle"></a></td>
	</tr>
	<tr>
		<td class="formLabel" nowrap><dhv:label
			name="opu.soggetto_fisico.comune_nascita"></dhv:label></td>
		<td><input class="todisable" type="text" size="30" maxlength="50"
			id="comuneNascita" name="comuneNascita" value=""> <%@ include file="openListaComuniNascita.jsp"%> </td>
	</tr>
	<tr>
		<td class="formLabel" nowrap><dhv:label
			name="opu.soggetto_fisico.provincia_nascita"></dhv:label></td>
		<td><input class="todisable" type="text" size="30" maxlength="50"
			id="provinciaNascita" name="provinciaNascita" value=""></td>
	</tr>
	
	<tr>
		<td class="formLabel" nowrap><dhv:label
			name="opu.soggetto_fisico.provincia_residenza"></dhv:label></td>
			
				<td><select class="todisable" name="addressLegaleCountry" id="addressLegaleCountry">
			<option value="-1">Inserire le prime 4 lettere</option>
			
		</select>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(dopo aver digitato le prime 5 lettere apparirà in automatico la lista delle province che iniziano con le lettere digitate)
		<font color="red">(*)</font> <%-- ComuniList.getHtmlSelect("searchcodeIdComune", -1) --%>
		<input type="hidden" name="addressLegaleCountryTesto"
			id="addressLegaleCountryTesto" /> <!--      <input type="text" size="30" maxlength="50" id = "" name="addressLegaleCountry" value="">-->
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


	
		
	
	<tr>
		<td class="formLabel" nowrap><dhv:label
			name="opu.soggetto_fisico.indirizzo"></dhv:label></td>
		<td><select class="todisable" name="addressLegaleLine1" id="addressLegaleLine1">
			<option value="-1" selected="selected">Inserire le prime 4 lettere</option>
			
		</select>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(dopo aver digitato le prime 5 lettere apparirà in automatico la lista degli indirizzi che iniziano con le lettere digitate)
		<font color="red">(*)</font> <input type="hidden"
			name="addressLegaleLine1Testo" id="addressLegaleLine1Testo" /> <!--      <input type="text" size="30" maxlength="50" id = "addressLegaleLine1" name="addressLegaleLine1" value="">-->
		</td>
	</tr>

	
	<tr>
		<td class="formLabel" nowrap><dhv:label
			name="opu.soggetto_fisico.cf"></dhv:label></td>
		<td>
		<input readonly="readonly" type="text" name="codFiscaleSoggetto"
			id="codFiscaleSoggetto" />  <font color="red">(*)</font>
			<!-- %if(newStabilimento.isFlagFuoriRegione()){ %-->
			<input type = "checkbox" name = "estero" id = "estero"  onclick="if(this.checked){document.getElementById('calcoloCF').style.visibility='hidden';document.getElementById('codFiscaleSoggetto').readOnly=false;} else {document.getElementById('calcoloCF').style.visibility='visible';document.getElementById('codFiscaleSoggetto').readOnly=true;document.getElementById('codFiscaleSoggetto').value='';}" >Provenienza Estera
			<!-- %} %-->
			<input type="button" id = "calcoloCF" value="Calcola Codice Fiscale" onclick="javascript:CalcolaCF()"></input></td>
	</tr>
	<tr>
		<td class="formLabel" nowrap><dhv:label
			name="opu.soggetto_fisico.didentita"></dhv:label></td>
		<td>
		 <input class="todisable" type="text" name="documentoIdentita"
			id="documentoIdentita" value=""/> </td>
	</tr>

	<tr>
		<td class="formLabel" nowrap><dhv:label
			name="opu.soggetto_fisico.mail"></dhv:label></td>
		<td><input class="todisable" type="text" size="30" maxlength="50" id="email"
			name="email" value=""></td>

	</tr>

	<tr>
		<td class="formLabel" nowrap><dhv:label
			name="opu.soggetto_fisico.telefono"></dhv:label></td>
		<td><input class="todisable" type="text" size="30" maxlength="50" id="telefono1"
			name="telefono1" value=""></td>

	</tr>

	<tr>
		<td class="formLabel" nowrap><dhv:label
			name="opu.soggetto_fisico.telefono2"></dhv:label></td>
		<td><input class="todisable" type="text" size="30" maxlength="50" id="telefono2"
			name="telefono2" value=""></td>

	</tr>

	<tr>
		<td class="formLabel" nowrap><dhv:label
			name="opu.soggetto_fisico.fax"></dhv:label></td>
		<td><input class="todisable" type="text" size="30" maxlength="50" id="fax"
			name="fax" value=""></td>

	</tr>

	</table>
</div>
</dhv:evaluate>

<dhv:evaluate if="<%=(id_relazione == LineaProduttiva.idAggregazioneColonia)%>">
	<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
			<th colspan="2"><strong><dhv:label name="">Informazioni colonia</dhv:label></strong>

			</th>
		</tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label name="">Numero protocollo</dhv:label>
			</td>
			<td><input type="text" name="nrProtocollo" id="nrProtocollo"
				value="" /></td>
		</tr>

		<tr>
			<td class="formLabel" nowrap="nowrap">Data registrazione della
			colonia</td>
			<td><input readonly type="text" name="dataRegistrazioneColonia"
				size="10" value="" nomecampo="registrazione"
				tipocontrollo="T2,T6,T7" labelcampo="Data Registrazione" />&nbsp; <a
				href="#"
				onClick="cal19.select(document.forms[0].dataRegistrazioneColonia,'anchor19','dd/MM/yyyy'); return false;"
				NAME="anchor19" ID="anchor19"><img
				src="images/icons/stock_form-date-field-16.gif" border="0"
				align="absmiddle"></a></td>
		</tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label name="">Numero totale gatti</dhv:label>
			</td>
			<td><input type="text" name="nrGattiTotale" id="nrGattiTotale"
				value="" /> <input type="checkbox" name="totalePresunto"
				id="totalePresunto" />&nbsp; Tot. Presunto <!-- Aggiungere data del censimento dei gatti -->
			</td>
		</tr>
		
		<tr>
			<td class="formLabel" nowrap="nowrap">Data del censimento</td>
			<td><input readonly type="text" name="dataCensimentoTotale"
				size="10" value="" nomecampo="censimento"
				tipocontrollo="T2,T6,T7" labelcampo="Data Censimento" />&nbsp; <a
				href="#"
				onClick="cal19.select(document.forms[0].dataCensimentoTotale,'anchor19','dd/MM/yyyy'); return false;"
				NAME="anchor19" ID="anchor19"><img
				src="images/icons/stock_form-date-field-16.gif" border="0"
				align="absmiddle"></a></td>
		</tr>

		<tr>
			<td class="formLabel" nowrap><dhv:label name="">Numero totale femmine</dhv:label>
			</td>
			<td><input type="text" name="nrGattiFTotale" id="nrGattiFTotale"
				value="" /> <input type="checkbox" name="totaleFPresunto"
				id="totaleFPresunto" />&nbsp; Tot. Presunto</td>
		</tr>

		<tr>
			<td class="formLabel" nowrap><dhv:label name="">Numero totale maschi</dhv:label>
			</td>
			<td><input type="text" name="nrGattiMTotale" id="nrGattiMTotale"
				value="" /> <input type="checkbox" name="totaleMPresunto"
				id="totaleMPresunto" />&nbsp; Tot. Presunto</td>
		</tr>


		<tr>
			<td class="formLabel" nowrap><dhv:label name="">Nominativo veterinario</dhv:label></td>
			<td><input type="text" size="30" maxlength="50"
				id="nomeVeterinario" name="nomeVeterinario" value=""></td>
		</tr>

	</table>

	</br>
	</br>
</dhv:evaluate>