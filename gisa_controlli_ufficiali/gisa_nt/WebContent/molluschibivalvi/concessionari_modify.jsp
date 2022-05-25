<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.molluschibivalvi.base.*"%>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.molluschibivalvi.base.Concessionario" scope="request" />
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ZoneProduzione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Classificazione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application" />
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request" />
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<script>
function checkForm()
{
	formtest = true ;
	msg ='Controllare di aver selezionato i seguenti campi : \n';
	if(document.addAccount.name.value =='')
	{
		formtest = false ;
		msg += 'Il campo impresa deve essere popolato\n'
	}
	if(document.addAccount.address1city.value =='' || document.addAccount.address1city.value =='-1')
	{
		formtest = false ;
		msg += 'Il campo comune deve essere popolato\n'
	}
	if(document.addAccount.address1state.value =='' || document.addAccount.address1state.value =='-1')
	{
		formtest = false ;
		msg += 'Il campo provincia deve essere popolato\n'
	}
	if(document.addAccount.siteId.value =='' || document.addAccount.siteId.value =='-1')
	{
		formtest = false ;
		msg += 'Il campo asl deve essere popolato\n'
	}
	
	if(formtest==true)
		document.addAccount.submit();
	else
	{
		alert(msg);
		return false ;
	}
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
	campoLat.value = value[1];
	campoLong.value =value[0];
	
}

</script>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>
<%@ include file="../initPage.jsp"%>

<form id="addAccount" name="addAccount"
	action="Concessionari.do?command=Update&auto-populate=true"
	method="post">
<input type="hidden" name="id" value="<%=OrgDetails.getId()%>">
<table class="trails" cellspacing="0">
	<tr>
		<td width="100%"><a href="Concessionari.do">Concessionari</a> > Modifica Scheda Concessionario</td>
	</tr>
</table>

<input type="button"
	value="Aggiorna"
	name="Save" onClick="checkForm()"> 
	<input type="button"
	value="Annulla"
	onClick="javascript:document.addAccount.action='Concessionari.do?command=Details&orgId=<%=OrgDetails.getId()%>';document.addAccount.submit();">
<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="details">
	<tr>
		<th colspan="2"><strong>Modifica Concessionario</strong></th>
	</tr>

	<tr>
		<td nowrap="nowrap" class="formLabel">ASL</td>
		<td><dhv:evaluate if="<%=OrgDetails.getSiteId()<=0   %>">
			<%= SiteIdList.getHtmlSelect("siteId",OrgDetails.getSiteId()) %>	<font color="red">*</font>
		</dhv:evaluate> <dhv:evaluate if="<%= OrgDetails.getSiteId() >0  %>">
			<%= SiteIdList.getSelectedValue(OrgDetails.getSiteId()) %>
			<input type="hidden" name="siteId" value="<%=OrgDetails.getSiteId()%>">
		</dhv:evaluate></td>
	</tr>

	<tr>
		<td nowrap class="formLabel" > Ragione Sociale</td>
		<td>
		<input type = "text" name = "name" value="<%=toHtml2(OrgDetails.getName()) %>">	<font color="red">*</font>
		</td>
	</tr>
	

	
<tr id = "classeId">
		<td nowrap class="formLabel" name="orgname1" id="orgname1"> Cf Impresa</td>
		<td><input type = "text" name = "cfTitolare" maxlength="16"  value="<%=toHtml2(OrgDetails.getCfTitolare()) %>"></td>
	</tr>
	
	<tr>
		<td nowrap class="formLabel" name="orgname1" id="orgname1"> Legale Rappresentante</td>
		<td><input type = "text" name = "titolareNome" value="<%=toHtml2(OrgDetails.getTitolareNome()) %>"></td>
	</tr>
	
	
</table>
<br />
<%
OrganizationAddress oa = new OrganizationAddress();
if(OrgDetails.getAddressList().size()>0)
	oa = (OrganizationAddress) OrgDetails.getAddressList().get(0);
%>
<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="details">
	<tr>
		<th colspan="2"><strong>Sede Legale</strong>
		<input type="hidden" name="address1id" value="<%=oa.getId() %>">
		<input type="hidden" name="address1type" value="5"></th></tr>
	<tr>
		<td nowrap="nowrap" class="formLabel" name="province" id="province">Comune</td>
		<td>
		<input type = "text" name = "address1city" id = "address1city" value = "<%=oa.getCity() %>" >
		<font color="red">*</font>
		</td>
	</tr>
	<tr>
		<td nowrap="nowrap" class="formLabel">Indirizzo
		</td>
		<td><input type="text" size="40" id="address1line1" name="address1line1" maxlength="80" value = "<%=toHtml2(oa.getStreetAddressLine1()) %>">
		</td>
	</tr>

	<tr>
		<td nowrap="nowrap" class="formLabel">CAP</td>
		<td><input type="text" size="28" name="address1zip" maxlength="5" value = "<%=toHtml2(oa.getZip()) %>">
		</td>
	</tr>

	<tr>
		<td nowrap="nowrap" class="formLabel">Prov</td>
		<td>
		<input type="text" size="28" name="address1state" maxlength="80" value = "<%=toHtml2(oa.getState()) %>"><font color="red">*</font>

		</td>
	</tr>



	<tr class="containerBody">
		<td class="formLabel" nowrap="nowrap">Latitudine</td>
		<td><input type="text" id="address1latitude" value = "<%=oa.getLatitude() %>" readonly="readonly"
			name="address1latitude" size="30" ></td>
	</tr>
	
	<tr class="containerBody">
		<td class="formLabel" nowrap="nowrap">Longitudine</td>
		<td><input type="text" id="address1longitude"  value = "<%=oa.getLongitude() %>" readonly ="readonly"
			name="address1longitude" size="30"></td>
	</tr>
	<tr style="display: block">
		<td colspan="2"><input id="coordbutton" type="button"
			value="Calcola Coordinate"
			onclick="javascript:showCoordinate(document.getElementById('address1line1').value, document.forms['addAccount'].address1city.value,document.forms['addAccount'].address1state.value, document.forms['addAccount'].address1zip.value, document.forms['addAccount'].address1latitude, document.forms['addAccount'].address1longitude);" />
		</td>
	</tr>
</table>

<br>
<input type="button"
	value="Aggiorna"
	name="Save" onClick="checkForm()"> 
	
	<input type="button"
	value="Annulla"
	onClick="javascript:document.addAccount.action='Concessionari.do?command=Details&orgId=<%=OrgDetails.getId()%>';document.addAccount.submit();">


</form>

