<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ taglib uri="/WEB-INF/taglib/systemcontext-taglib.tld" prefix="sc"%>

<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*"%>
<%@page import="org.aspcfs.modules.opu.base.Stabilimento"%>
<%@page import="org.aspcfs.modules.opu.base.Operatore"%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.aspcfs.modules.opu.base.Stabilimento"%>

<jsp:useBean id="Operatore" class="org.aspcfs.modules.opu.base.Operatore" scope="request" />
<jsp:useBean id="newStabilimento" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request" />
<jsp:useBean id="indirizzoAdded" class="org.aspcfs.modules.opu.base.Indirizzo" scope="request" />
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="IterList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="Address" class="org.aspcfs.modules.accounts.base.OrganizationAddress" scope="request" />
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="provinciaAsl"  class="org.aspcfs.modules.accounts.base.Provincia" scope="request" />




<script language="JavaScript" TYPE="text/javascript"SRC="javascript/gestoreCodiceFiscale.js"></script>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/opu.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>
<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/opu.css" />
 
 
<script>
	function checkLineaProduttiva()
	{
		document.forms[0].doContinueStab.value = 'false';
		document.forms[0].submit();
	}
	
	var campoLat;
	var campoLong;
  	function showCoordinate(address,city,prov,cap,campo_lat,campo_long)
  	{
	   campoLat = campo_lat;
	   campoLong = campo_long;
	   Geocodifica.getCoordinate(address,city,prov,cap,'','','',setGeocodedLatLonCoordinate);
	   
	   
	}
	function setGeocodedLatLonCoordinate(value)
	{
		campoLat.value = value[1];;
		campoLong.value =value[0];
		
	}
</script>



<%@ include file="../initPage.jsp"%>


<script>
    
       
    
    function popUp(url) 
    {
    	  title  = '_types';
    	  width  =  '500';
    	  height =  '600';
    	  resize =  'yes';
    	  bars   =  'yes';
    	  var posx = (screen.width - width)/2;
    	  var posy = (screen.height - height)/2;
    	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
    	  var newwin=window.open(url, title, windowParams);
    	  newwin.focus();
		  if (newwin != null) 
		  {
    	    	if (newwin.opener == null)
    	      		newwin.opener = self;
    	  }
    	}

    $(function() {
      
        $( "#addressLegaleCity" ).combobox();
        $( "#addressLegaleCountry" ).combobox();
        $( "#addressLegaleLine1" ).combobox();
       
    });
  </script>


<table border="0" width="100%" 	class="details" id = "rappstabilimento" >
		<tr>
			<th colspan="2">
				<strong>Legale Rappresentante</strong>
			</th>
		</tr>
		<tr>
		<th>&nbsp;</th>
		<th>
			<input type = "text" name="cfSearch"  value = "Ricerca codice Fiscale" onclick="this.value=''" id = "cfSearch" style="background-color: blue;color: white;">
			<a href="#" onclick="ricercaSoggettoFisico(document.getElementById('cfSearch').value)">
				<img src="images/filter.gif">
			</a>
			<a href= "#" onclick="document.getElementById('cfSearch').value=''">
				<img src="images/clear.gif">
			</a>
		</th>
	</tr>
		<tr style="display:none">
			<td class="formLabel">
				<dhv:label name="opu.sede_legale.inregione"></dhv:label>
			</td>
			<td>
				<select class="todisable" name="inregioneRappOperativo" id="inregioneRappOperativo">
					<option value="si">SI</option>
					<option value="no">NO</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="formLabel" nowrap>
				<dhv:label name="opu.soggetto_fisico.nome"></dhv:label>
			</td>
			<td>
				<input class="todisable" type="text" size="30" maxlength="50" id="nome" name="nome" value="<%=newStabilimento.getRappLegale().getNome() %>">
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td class="formLabel" nowrap>
				<dhv:label name="opu.soggetto_fisico.cognome"></dhv:label>
			</td>
			<td>
				<input class="todisable" type="text" size="30" maxlength="50" id="cognome" name="cognome" value="<%=newStabilimento.getRappLegale().getCognome() %>">
				<font color="red">*</font>
			</td>
		</tr>
		<tr id="">
			<td class="formLabel" nowrap>
				<dhv:label name="opu.soggetto_fisico.sesso"></dhv:label>
			</td>
			<td>
				<input class="todisable" type="radio" name="sesso" id="sesso1" value="M" checked="checked">M <input type="radio" name="sesso" id="sesso2" value="F">F
			</td>
		</tr>
		<tr>
			<td nowrap class="formLabel">
				<dhv:label name="opu.soggetto_fisico.data_nascita"></dhv:label>
			</td>
			<td>
				<input class="todisable" readonly type="text" id="dataNascita" name="dataNascita" size="10" value = "<%=toDateasString(newStabilimento.getRappLegale().getDataNascita()) %>" /> 
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
				<input class="todisable" type="text" size="30" maxlength="50" id="provinciaNascita" name="provinciaNascita" value="<%=newStabilimento.getRappLegale().getProvinciaNascita() %>">
			</td>
		</tr>
		<tr>
			<td class="formLabel" nowrap>
				<dhv:label name="opu.soggetto_fisico.comune_nascita"></dhv:label>
			</td>
			<td>
				<input class="todisable" type="text" size="30" maxlength="50" id="comuneNascita" name="comuneNascita" value="<%=newStabilimento.getRappLegale().getComuneNascita() %>">
			</td>
		</tr>
	
		<tr>
			<td class="formLabel" nowrap>
				<dhv:label name="opu.soggetto_fisico.provincia_residenza"></dhv:label>
			</td>
			<td>
			
				<select class="todisable" name="addressLegaleCountry" id="addressLegaleCountry">
					<dhv:evaluate if="<%=(newStabilimento.getRappLegale().getIndirizzo().getIdProvincia()>0)%>">
						<option value="<%=newStabilimento.getRappLegale().getIndirizzo().getIdProvincia()%>" selected="selected"><%= newStabilimento.getRappLegale().getIndirizzo().getDescrizione_provincia() %></option>
					</dhv:evaluate>
					<dhv:evaluate if="<%=(newStabilimento.getRappLegale().getIndirizzo().getIdProvincia()<=0)%>">
							<option value="-1" selected="selected">Inserire le prime 4 lettere</option>
					</dhv:evaluate>
					
					
				</select>
				<font color="red">(*)</font> 
				<input type="hidden" name="addressLegaleCountryTesto" id="addressLegaleCountryTesto" /> 
			</td>
		</tr>
		<tr>
			<td class="formLabel" nowrap>
				<dhv:label name="opu.soggetto_fisico.comune_residenza"></dhv:label>
			</td>
			<td>
		
				<select class="todisable" name="addressLegaleCity" id="addressLegaleCity">
					<dhv:evaluate if="<%=(newStabilimento.getRappLegale().getIndirizzo().getComune()>0)%>">
						<option value="<%=newStabilimento.getRappLegale().getIndirizzo().getComune()%>" selected="selected"><%=(newStabilimento.getRappLegale().getIndirizzo().getComune()>0)? newStabilimento.getRappLegale().getIndirizzo().getDescrizioneComune().trim() : "Inserire le prime 4 lettere".trim()%></option>
						</dhv:evaluate>
						<dhv:evaluate if="<%=(newStabilimento.getRappLegale().getIndirizzo().getComune()<=0)%>">
							<option value="-1" selected="selected">Inserire le prime 4 lettere</option>
						</dhv:evaluate>
						
				</select>
				<font color="red">(*)</font> 
				<input type="hidden" name="addressLegaleCityTesto" id="addressLegaleCityTesto" /> 
			</td>
		</tr>
		<tr>
			<td class="formLabel" nowrap>
				<dhv:label name="opu.soggetto_fisico.indirizzo"></dhv:label>
			</td>
			<td>
			
				<select  name="addressLegaleLine1" id="addressLegaleLine1">
					<dhv:evaluate if="<%=(newStabilimento.getRappLegale().getIndirizzo().getIdIndirizzo()>0)%>">
						<option value="<%=newStabilimento.getRappLegale().getIndirizzo().getIdIndirizzo()%>" selected="selected"><%=newStabilimento.getRappLegale().getIndirizzo().getVia()%></option>
					</dhv:evaluate>
					<dhv:evaluate if="<%=(newStabilimento.getRappLegale().getIndirizzo().getIdIndirizzo()<=0)%>">
						<option value="-1" selected="selected"><%=(!"".equals(newStabilimento.getRappLegale().getIndirizzo().getVia())) ? newStabilimento.getRappLegale().getIndirizzo().getVia() : "Inserire le prime 4 lettere"%></option>
					</dhv:evaluate>
				</select>
				<font color="red">(*)</font> 
				<input type="hidden" name="addressLegaleLine1Testo" id="addressLegaleLine1Testo" /> 
			</td>
		</tr>
		<tr>
			<td class="formLabel" nowrap>
				<dhv:label	name="opu.soggetto_fisico.cf"></dhv:label>
			</td>
			<td>
				<input type="text" name="codFiscaleSoggetto" id="codFiscaleSoggetto" value = "<%=newStabilimento.getRappLegale().getCodFiscale()%>" />  
				<font color="red">(*)</font>
				<%if(newStabilimento.isFlagFuoriRegione()){ %>
					<input type = "checkbox" name = "estero" id = "estero"  onclick="if(this.checked){document.getElementById('calcoloCF').style.visibility='hidden';} else {document.getElementById('calcoloCF').style.visibility='visible';}" >Provenienza Estera
				<%} %>
				<input type="button" id = "calcoloCF" value="Calcola Codice Fiscale" onclick="javascript:CalcolaCF(document.forms[0].sesso,document.forms[0].nome,document.forms[0].cognome,document.forms[0].comuneNascita,document.forms[0].dataNascita,'codFiscaleSoggetto')"></input></td>
		</tr>
		<tr>
			<td class="formLabel" nowrap>
				<dhv:label name="opu.soggetto_fisico.didentita"></dhv:label>
			</td>
			<td>
		 		<input class="todisable" type="text" name="documentoIdentita" id="documentoIdentita" value = "<%=newStabilimento.getRappLegale().getDocumentoIdentita()%>"/> 
		 	</td>
		</tr>
		<tr style="display:none">
			<td class="formLabel" nowrap>
				<dhv:label name="opu.soggetto_fisico.mail"></dhv:label>
			</td>
			<td>
				<input class="todisable" type="text" size="30" maxlength="50" id="email" name="email" value="">
			</td>
		</tr>
		<tr style="display:none">
			<td class="formLabel" nowrap>
				<dhv:label name="opu.soggetto_fisico.telefono"></dhv:label>
			</td>
			<td>
				<input class="todisable" type="text" size="30" maxlength="50" id="telefono1" name="telefono1" value = "<%=""%>">
				<font color="red">*</font>
			</td>
		</tr>
		<tr style="display:none">
			<td class="formLabel" nowrap>
				<dhv:label name="opu.soggetto_fisico.telefono2"></dhv:label>
			</td>
			<td>
				<input class="todisable" type="text" size="30" maxlength="50" id="telefono2" name="telefono2" value="">
			</td>
		</tr>
		<tr style="display:none">
			<td class="formLabel" nowrap>
				<dhv:label	name="opu.soggetto_fisico.fax"></dhv:label>
			</td>
			<td>
				<input class="todisable" type="text" size="30" maxlength="50" id="fax" name="fax" value="">
			</td>
		</tr>
	</table>
	