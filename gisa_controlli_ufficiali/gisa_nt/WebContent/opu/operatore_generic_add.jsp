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

<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/gestoreCodiceFiscale.js"></script>

<script>
function checkLineaProduttiva(){
	document.forms[0].doContinueLp.value = 'false';
	document.forms[0].submit();
}
</script>
<input type="hidden" name="doContinueLp" id="doContinueLp" value="" />
<%
int i = 0 ;

	
%>
<div id="info_impresa">	
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
<tr>
		<th colspan="2"><strong><dhv:label name="<%="opu.operatore_gisa"%>"></dhv:label></strong></th>
	</tr>
	<tr>
		<th>&nbsp;</th>
		<th>
			<input type = "text" maxlength="11" name="partitaIvaSearch" value = "ricerca partita iva" onclick="this.value=''" id = "partitaIvaSearch" style="background-color: blue;color: white;">
			<a href="#" onclick="ricercaImpresa(document.getElementById('partitaIvaSearch').value)">
				<img src="images/filter.gif">
			</a>
			<a href= "#" onclick="document.getElementById('partitaIvaSearch').value=''">
				<img src="images/clear.gif">
			</a>
		</th>
	</tr>
	

	<tr>
		<td nowrap class="formLabel"><dhv:label name="<%="opu.operatore.intestazione"%>"></dhv:label></td>
		<td>
			<input type="text" size="20" maxlength="200" id="ragioneSociale" required="required" name="ragioneSociale" value="<%=(Operatore.getRagioneSociale() != null) ? Operatore.getRagioneSociale(): ""%>">
			<font color="red">*</font></td>
	</tr>

	<tr>
		<td nowrap class="formLabel">
			<dhv:label name="opu.operatore.piva"></dhv:label>
		</td>
		<td>
			<input type="text" size="20" maxlength="11" id="partitaIva" required="required" name="partitaIva" value="<%=(Operatore.getPartitaIva() != null) ? Operatore.getPartitaIva() : ""%>">
			<font color="red">*</font></td>
	</tr>

	<tr>
		<td nowrap class="formLabel">
			<dhv:label name="opu.operatore.cf"></dhv:label>
		</td>
		<td>
			<input type="text" size="20" maxlength="16" id="codFiscale" required="required" name="codFiscale" value="<%=(Operatore.getCodFiscale() != null) ? Operatore.getCodFiscale() : ""%>">
		</td>
	</tr>

	<tr>
		<td valign="top" nowrap class="formLabel">
			<dhv:label name="opu.operatore.note"></dhv:label></td>
		<td>
			<textarea NAME="note" ROWS="3" COLS="50"><%=toString(Operatore.getNote())%></textarea>
		</td>
	</tr>
		<tr>
    		<td class="formLabel" nowrap>
      			<dhv:label name="">Domicilio Digitale</dhv:label>
    		</td>
    		<td>
      			<input type="text" size="20" maxlength="" name="domicilioDigitale" >    
    		</td>
  		</tr>
  			<tr style ="display:none"> 
    		<td class="formLabel" nowrap>
      			<dhv:label name="">Flag Ric. Ce</dhv:label>
    		</td>
    		<td>
      			<input type="checkbox" name = "flagRicCe" value = "1" onclick="if(this.checked) {document.getElementById('numRicCe').style.display=''} else{document.getElementById('numRicCe').style.display='none'}">    
      			<input type = "text" id = "numRicCe" name = "numRicCe" style = "display:none">
    		</td>
  		</tr>

</table>
</div>

<br/>
<div id="info_sede">
<%


%>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	<tr>
		<th colspan="2">
			<strong><dhv:label name="<%="opu.sede_legale_gisa"%>"></dhv:label></strong>
		</th>
	</tr>


	<tr>

		<td nowrap class="formLabel">
			<dhv:label name="opu.sede_legale.provincia"></dhv:label>
		</td>
		<td>
			<select name="searchcodeIdprovincia" id="searchcodeIdprovincia">
				<option value="-1">Inserire le prime 4 lettere</option>
			</select>
			
			<input type="hidden" name="searchcodeIdprovinciaTesto" id="searchcodeIdprovinciaTesto" value="<%=provinciaAsl.getCodice()%>" />
			<font color="red">(*)</font> 
		</td>
	</tr>
	
	<tr>
		<td nowrap class="formLabel" name="province" id="province">
			<dhv:label name="opu.sede_legale.comune"></dhv:label>
		</td>
		<td>
			<select name="searchcodeIdComune" id="searchcodeIdComune">

			</select>
			
			<input type="hidden" name="searchcodeIdComuneTesto" id="searchcodeIdComuneTesto" />
			<font color="red">(*)</font>
		</td>
	</tr>
	
	<tr>
		<td nowrap class="formLabel">
			<dhv:label name="opu.sede_legale.indirizzo"></dhv:label>
		</td>
		<td>
			<select name="via" id="via"> 
				<option value="-1" selected="selected">Inserire le prime 4 lettere</option>
			</select>
			
			<font color="red">(*)</font> 
			<input type="hidden" name="viaTesto" id="viaTesto" />
		</td>
	</tr>
	
	<tr>
		<td nowrap class="formLabel">
			<dhv:label name="opu.sede_legale.co"></dhv:label>
		</td>
		<td>
			<input type="text" size="40" name="presso" maxlength="80" value="<%=""%>">
		</td>
	</tr>


	<tr class="containerBody">
		<td class="formLabel" nowrap>
			<dhv:label name="opu.sede_legale.latitudine"></dhv:label>
		</td>
		<td>
			<input type="text" id="latitudine" name="latitudine" size="30" value="">
		</td>
	</tr>
	
	<tr class="containerBody">
		<td class="formLabel">
			<dhv:label name="opu.sede_legale.longitudine"></dhv:label>
		</td>
		<td>
			<input type="text" id="longitudine" name="longitudine" size="30" value="">
		</td>
	</tr>

	
</table>
</div>

<br>


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

	<tr style="display: none">
		<td class="formLabel">
			<dhv:label name="opu.sede_legale.inregione"></dhv:label>
		</td>
		<td>
		<input type = "hidden" name = "inregione"  id="inregione" value = "no">
<!-- 			<select  name="inregione" onchange="checkLineaProduttiva()" id="inregione"> -->
<%-- 				<option value="si" <%=(Operatore.isFlagFuoriRegione()) ? "selected" : ""%>>SI</option> --%>
<%-- 				<option value="no" <%=(Operatore.isFlagFuoriRegione()) ? "selected" : ""%>>NO</option> --%>
<!-- 			</select> -->
		</td>
	</tr>

	<tr>
		<td class="formLabel" nowrap>
			<dhv:label name="opu.soggetto_fisico.nome"></dhv:label>
		</td>
		<td> 
			<input type="text" size="30" maxlength="50" id="nome" name = "nome" required="required">
		</td>
		
	</tr>
	<tr>
		<td class="formLabel" nowrap>
			<dhv:label name="opu.soggetto_fisico.cognome"></dhv:label>
		</td>
		<td>
			<input class="todisable" type="text" size="30" maxlength="50" id="cognome" name="cognome" required="required" value="">
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
			<input class="todisable" readonly type="text" id="dataNascita" name="dataNascita" size="10" /> 
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
			<input readonly="readonly"  type="text" name="codFiscaleSoggetto" id="codFiscaleSoggetto" required="required" />  <font color="red">(*)</font>
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

</table>

<br/>








