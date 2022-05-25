<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ page
	import="java.util.*,java.text.DateFormat,org.aspcfs.modules.molluschibivalvi.base.*"%>

<%@page import="java.util.Date"%><jsp:useBean id="OrgDetails"
	class="org.aspcfs.modules.molluschibivalvi.base.Organization"
	scope="request" />
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="ZoneProduzione" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="Classificazione"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="applicationPrefs"
	class="org.aspcfs.controller.ApplicationPrefs" scope="application" />
<jsp:useBean id="systemStatus"
	class="org.aspcfs.controller.SystemStatus" scope="request" />
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
		msg += 'Il campo Localita deve essere popolato\n'
	}
	if(document.addAccount.address1city.value =='' || document.addAccount.address1city.value =='-1')
	{
		formtest = false ;
		msg  += 'Il campo comune deve essere popolato\n'
	}
// 	if(document.addAccount.siteId.value =='' || document.addAccount.siteId.value =='-1')
// 	{
// 		formtest = false ;
// 		msg += 'Il campo asl deve essere popolato\n'
// 	}
	
// 	if(document.addAccount.tipoMolluschi.value =='-1')
// 	{
// 		formtest = false ;
// 		msg += 'Il campo zona di produzione deve essere popolato\n'
// 	}

	if(document.addAccount.address1latitude.value =='' || document.addAccount.address1longitude.value =='')
	{
		formtest = false ;
		msg += 'Inserire almeno una coppia di coordinate \n'
	}
	else
	{

		form = document.addAccount ;
		indice = 1 ;
		while (document.getElementById('address'+indice+'latitude')!=null)
		{
		   if (document.getElementById('address'+indice+'latitude') && document.getElementById('address'+indice+'latitude').value!=""){
		       	 //alert(!isNaN(form.address2latitude.value));
		       		
		       			if (isNaN(document.getElementById('address'+indice+'latitude').value) ||  (document.getElementById('address'+indice+'latitude').value < 39.988475) || (document.getElementById('address'+indice+'latitude').value > 41.503754)){
		       				msg += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754 (Sede Operativa)\r\n";
		       				formtest = false;
		        			}		 
		       		
		    	 }   


		   	 
		   	 if (document.getElementById('address'+indice+'longitude') && document.getElementById('address'+indice+'longitude').value!=""){
		      	 //alert(!isNaN(form.address2longitude.value));
		      		
		      			if (isNaN(document.getElementById('address'+indice+'longitude').value) ||  (document.getElementById('address'+indice+'longitude').value < 13.7563172) || (document.getElementById('address'+indice+'longitude').value > 15.8032837)){
		      				msg += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837 (Sede Operativa)\r\n";
		       			formtest = false;
		       			}		 
		      		
		   	 }  
		   	 indice ++ ;
		}
	}
	
	
	

// 	var select = document.getElementById('molluschi');
// 	for (i = 0 ; i < select.length ; i ++)
// 	{
// 		if(select.options[i].value == '-1' && select.options[i].selected==true)
// 		{
// 			formtest = false ;
// 			msg += 'Il campo Molluschi non deve contenere seleziona voce\n' ;
// 			break ;
// 		}
// 	}

	if(formtest==true)
		document.addAccount.submit();
	else
	{
		alert(msg);
		return false ;
	}
}

function showCampi()
{
value = document.getElementById('tipoMolluschi').value;
if(value == '1') // specchio acquo
{
	document.getElementById('classeId').style.display= "";
	document.getElementById('abusivi').style.display= "none";
}
else
{
	
	if(value == '4') // specchio acquo
	{
		document.getElementById('abusivi').style.display= "";
		document.getElementById('classeId').style.display= "none";
		
	}
	else
	{
		document.getElementById('abusivi').style.display= "none";
		document.getElementById('classeId').style.display= "none";
	}
	
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
	campoLat.value = value[1];;
	campoLong.value =value[0];
	
}
function openTree(campoid1,campoid2,table,id,idPadre,livello,multiplo,divPath,idRiga)
{

	window.open('Tree.do?command=DettaglioTree&nodo=289&multiplo='+multiplo+'&divPath='+divPath+'&idRiga='+idRiga+'&campoId1='+campoid1+'&campoId2='+campoid2+'&nomeTabella='+table+'&campoId='+id+'&campoPadre='+idPadre+'&campoDesc=nome&campoLivello=livello&campoEnabled=nuova_gestione&sel=true');

}
function eliminaTutti(id)
{
	for (i=1 ; i <=parseInt(document.getElementById('size').value); i++)
	{
		   var node = document.getElementById(id+'_'+i);
		   var removed = node.parentNode.removeChild(node); 
		   
	}
	document.getElementById('size').value = 0 ;
	document.getElementById('elementi').value = 0 ;
	 

}
</script>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>
<%@ include file="../initPage.jsp"%>

<body>
<form id="addAccount" name="addAccount"
	action="MolluschiBivalvi.do?command=Update&auto-populate=true&id=<%=OrgDetails.getId() %>"
	method="post">

<table class="trails" cellspacing="0">
	<tr>
		<td width="100%"><a href="MolluschiBivalvi.do">Molluschi
		Bivalvi</a> > Modifica Scheda</td>
	</tr>
</table>

<input type="button" value="Aggiorna" name="button"
	onClick="checkForm()">
	
	<input type="submit"
	value="Annulla"
	onClick="javascript:this.form.action='MolluschiBivalvi.do?command=Details&orgId=<%=OrgDetails.getId() %>';">
<br />
<br>
<%
OrganizationAddress oa = new OrganizationAddress();
if(OrgDetails.getAddressList().size()>0)
	oa = (OrganizationAddress) OrgDetails.getAddressList().get(0);
%>
<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="details">
	<tr>
		<th colspan="2"><strong><dhv:label name="">Localita</dhv:label></strong>
		<input type="hidden" name="address1id" value="<%=oa.getId() %>">
		<input type="hidden" name="address1type" value="5"></th></tr>
	<tr>
		<td nowrap="nowrap" class="formLabel" name="province" id="province">Comune</td>
		<td>
		<select  name="address1city" id="address1city">
	<option value="-1">Nessuna Selezione</option>
            
	 <%
                Vector v4 = OrgDetails.getComuni2();
	 			Enumeration e4=v4.elements();
                while (e4.hasMoreElements()) {
                	String prov4=e4.nextElement().toString();
                	
        %>
                <option value="<%=prov4%>" <%if(prov4.equalsIgnoreCase(oa.getCity())) {%> selected="selected" <%} %>><%= prov4 %></option>	
              <%}%>
		
	</select> 	<font color="red">*</font>
		</td>
	</tr>
	<tr>
		<td nowrap class="formLabel" name="orgname1" id="orgname1"> Localita'</td>
		<td><input type = "text" name = "name" value="<%=toHtml2(OrgDetails.getName()) %>">	<font color="red">*</font>
			<input type="hidden" size="40" id="address1line1" name="address1line1" maxlength="80" value = "<%=toHtml2(oa.getStreetAddressLine1()) %>">
	<input type="hidden" size="28" name="address1zip" maxlength="5" value = "<%=toHtml2(oa.getZip()) %>">
	
		<input type="hidden" size="28" name="address1state" maxlength="80" value = "<%=toHtml2(oa.getState()) %>">
		</td>
	</tr>


		
	<tr class="containerBody">
	<td nowrap="nowrap" class="formLabel">Coordinate</td>
	<td>
	<!--  <a href= "javascript:clonaCoordinateMolluschi()" >Aggiungi Coppia di Coordinate</a>-->
	<br>
	<br>
	<input type = "hidden" name = "elementi" id = "elementi" value = "<%=oa.getListaCoordinate().size() %>">
	<input type = "hidden" name = "size" id = "size"  value = "<%=oa.getListaCoordinate().size() %>">
	<table>
	<tr id = "riga" style="display: none">
	<td>Latitudine&nbsp;</td><td><input type="text"  size="30" ></td>
		<td>Longitudine&nbsp;</td><td><input type="text" size="30"></td></tr>
		<%
		if (oa.getListaCoordinate()!=null && !oa.getListaCoordinate().isEmpty() && oa.getListaCoordinate().size()>0){
			for( int i = 0; i <oa.getListaCoordinate().size() ; i++ )
			{
				CoordinateMolluschiBivalvi coord = oa.getListaCoordinate().get(i);
				%>
				<tr id = "riga<%=(i+1) %>" >
				<input type = "hidden" name = "address<%=(i+1) %>id" value = "<%=coord.getAddressId() %>">
				<input type = "hidden" name = "id<%=(i+1) %>" value = "<%=coord.getId() %>">
			<td>Latitudine&nbsp;</td><td><input type="text" id="address<%=(i+1) %>latitude"name="address<%=(i+1) %>latitude" size="30" value="<%=coord.getLatitude() %>"></td>
			<td>Longitudine&nbsp;</td><td><input type="text" id="address<%=(i+1) %>longitude" name="address<%=(i+1) %>longitude" size="30" value="<%=coord.getLongitude() %>"></td></tr>
				<%
			}
		} else { %>
			<tr id = "riga1" >
			<input type = "hidden" name = "address1id" value = "0">
			<input type = "hidden" name = "id1" value = "0">
		<td>Latitudine&nbsp;</td><td><input type="text" id="address1latitude" name="address1latitude" size="30" value="0"></td>
		<td>Longitudine&nbsp;</td><td><input type="text" id="address1longitude" name="address1longitude" size="30" value="0"></td></tr>
	<%	}
		%>
		
	</table> 
	</td>
	</tr>

	
</table>

<br>
<input type="button"
	value="Aggiorna"
	name="Save" onClick="checkForm()"> <input type="submit"
	value="Annulla"
	onClick="javascript:this.form.action='MolluschiBivalvi.do?command=Details&orgId=<%=OrgDetails.getId() %>';">
	
</form>

