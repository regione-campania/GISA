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
    
    
 

    $(function() {
        $( "#searchcodeIdprovincia" ).combobox();
        $( "#searchcodeIdComune" ).combobox();
        $( "#via" ).combobox();
        
     
       
    });
  </script>

<table cellpadding="4" id = "sedestabilimento" cellspacing="0" border="0" width="100%" class="details" >

	<tr>
			<th colspan="2">
				<label id = "label_sede">Sede Attivita</label>
			</th>
		
		
	</tr>
	

	<tr>
		<td class="formLabel">
			<dhv:label name="opu.stabilimento.asl">Asl</dhv:label>
		</td>
		<td>
			<dhv:evaluate if="<%=(User.getSiteId() <0)%>">
				<p id="descrizioneasl">
					<%=AslList.getSelectedValue(newStabilimento.getSedeOperativa().getIdAsl())%>
				</p>
			<input type="hidden" name="idAsl" id="idAsl" value="<%=newStabilimento.getSedeOperativa().getIdAsl()%>">
			</dhv:evaluate> 
			<dhv:evaluate if="<%=(User.getSiteId() >0)%>">
				<p id="descrizioneasl"><%=AslList.getSelectedValue(User.getSiteId())%></p>
				<input type="hidden" name="idAsl" id="idAsl" value="<%=User.getSiteId()%>">
			</dhv:evaluate>
		</td>
	</tr>
	<tr style ="display:none">
		<td class="formLabel">
			<dhv:label name="opu.sede_legale.inregione"></dhv:label>
		</td>
		<td>
			<select name="inregione" onchange="checkLineaProduttiva()" id="inregione">
				<option value="si" <%=(!newStabilimento.isFlagFuoriRegione()) ? "selected" : ""%>>SI</option>
				<option value="no" <%=(newStabilimento.isFlagFuoriRegione()) ? "selected" : ""%>>NO</option>
			</select>
		</td>
		<input type="hidden" name="doContinueStab" id="doContinueStab" value="" />
	</tr>
	
	<tr>
		<td nowrap class="formLabel">
			<dhv:label	name="opu.stabilimento.provincia"></dhv:label>
		</td>
		<td>
			<dhv:evaluate if="<%=(!(User.getSiteId() > -1) || newStabilimento.isFlagFuoriRegione())%>">
				<select name="searchcodeIdprovincia" id="searchcodeIdprovincia">
					<option value="<%=newStabilimento.getSedeOperativa().getIdProvincia()%>"><%=(newStabilimento.getSedeOperativa().getIdProvincia()>0)? newStabilimento.getSedeOperativa().getDescrizione_provincia() : "Inserire le prime 4 lettere" %></option>
				</select>
				<font color="red">(*)</font>
				<input type="hidden" name="searchcodeIdprovinciaTesto" id="searchcodeIdprovinciaTesto" />
			</dhv:evaluate> 
		
			<dhv:evaluate if="<%=(User.getSiteId() > -1 && !newStabilimento.isFlagFuoriRegione())%>">
				<%=provinciaAsl.getDescrizione()%>
				<input type="hidden" name="searchcodeIdprovinciaAsl" id="searchcodeIdprovinciaAsl" value="<%=provinciaAsl.getCodice()%>" />
			</dhv:evaluate>
		</td>
	</tr>
	<tr>
		<td nowrap class="formLabel" name="province" id="province">
			<dhv:label name="opu.stabilimento.comune"></dhv:label>
		</td>
		<td>
			<select name="searchcodeIdComune" id="searchcodeIdComune">
				<option value="<%=newStabilimento.getSedeOperativa().getComune()%>" selected="selected"><%=(newStabilimento.getSedeOperativa().getComune()>0)? newStabilimento.getSedeOperativa().getDescrizioneComune() : "Inserire le prime 4 lettere" %></option>
			</select> 
			<font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; *</font> 
			<input	type="hidden" name="searchcodeIdComuneTesto" id="searchcodeIdComuneTesto" />
		</td>
	</tr>
	<tr>
		<td nowrap class="formLabel">
			<dhv:label	name="opu.stabilimento.indirizzo"></dhv:label>
		</td>
		<td>
			<select name="via" id="via">
				<option value="<%=newStabilimento.getSedeOperativa().getIdIndirizzo()%>" selected="selected"><%=newStabilimento.getSedeOperativa().getVia()%></option>
			</select> 
			<input type="hidden" name="viaTesto" id="viaTesto" />
			<font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; *</font>
		</td>
	</tr>
	<tr style="display: none">
		<td nowrap class="formLabel">
			<dhv:label name="opu.stabilimento.co"></dhv:label>
		</td>
		<td>
			<input type="text" size="40" name="presso" maxlength="80" value="<%=""%>">
		</td>
	</tr>
	<tr>
		<td nowrap class="formLabel">
			<dhv:label name="opu.stabilimento.cap"></dhv:label>
		</td>
		<td>
			<input  type="text" size="28" id="cap" name="cap" maxlength="5" value="<%=newStabilimento.getSedeOperativa().getCap()%>">
		</td>
	</tr>
	<tr class="containerBody">
		<td class="formLabel" nowrap>
			<dhv:label name="opu.stabilimento.latitudine"></dhv:label>
		</td>
		<td>
			<input type="text" id="latitudine" name="latitudine" size="30" value="<%=((Double) newStabilimento.getSedeOperativa().getLatitudine() != null) ? newStabilimento.getSedeOperativa().getLatitudine()+ "" : ""%>">
		</td>
	</tr>
	<tr class="containerBody">
		<td class="formLabel">
			<dhv:label name="opu.stabilimento.longitudine">Longitude</dhv:label>
		</td>
		<td>
			<input type="text" id="longitudine" name="longitudine" size="30" value="<%=((Double) newStabilimento.getSedeOperativa().getLongitudine() != null) ? newStabilimento.getSedeOperativa().getLongitudine()+ "" : ""%>">
		</td>
	</tr>
	 <tr style="display: block">
    <td colspan="2">
    	<input id="coordbutton" type="button" value="Calcola Coordinate" 
    	onclick="javascript:showCoordinate(document.getElementById('viainput').value, document.getElementById('input').value,document.getElementById('searchcodeIdprovinciainput').value, document.getElementById('cap').value, document.getElementById('latitudine'), document.getElementById('longitudine'));" /> 
    </td>
  </tr> 
</table>
