<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.molluschibivalvi.base.*"%>

<%@page import="java.util.Date"%><jsp:useBean id="OrgDetails" class="org.aspcfs.modules.molluschibivalvi.base.Organization" scope="request" />
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="StatiClassificazione" class="org.aspcfs.utils.web.LookupList" scope="request" />

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
	
	if(document.addAccount.dataProvvedimento.value =='' && (document.addAccount.statiClassificazione.value == '3' || document.addAccount.statiClassificazione.value == '4') )
	{
		formtest = false ;
		msg += 'Il campo data provvedimento deve essere popolato\n'
	}
	if(document.addAccount.dataRevoca.value =='' && document.addAccount.statiClassificazione.value == '4' )
	{
		formtest = false ;
		msg += 'Il campo data revoca deve essere popolato\n'
	}
	if(document.addAccount.dataSospensione.value =='' && document.addAccount.statiClassificazione.value == '3' )
	{
		formtest = false ;
		msg += 'Il campo data sospensione deve essere popolato\n'
	}
	if(document.addAccount.tipoMolluschi.value =='-1' )
	{
		formtest = false ;
		msg += 'Il campo Tipologia Zona deve essere popolato\n'
	}
	
	var select = document.getElementById('molluschi');
	for (i = 0 ; i < select.length ; i ++)
	{
		if(select.options[i].value ==  '-1' &&  select.options[i].selected==true)
		{
			formtest = false ;
			msg += 'Il campo Molluschi non deve contenere seleziona voce\n' ;
			break ;
		}
	}
	
	var alfabeto = ['0', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'L', 'M', 'N', 'O'];
	
	for (var k=1; k<=10;k++){
		var lat = document.getElementById("poligonoLatitude"+k).value;
		var lon = document.getElementById("poligonoLongitude"+k).value;
		
		if (lon!='')
		{
			if (isNaN(lon) ||  (lon < 13.7563172) || (lon > 15.8032837)){
		    	msg += "- Valore errato per il campo Poligono "+ alfabeto[k] +" - Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837.\r\n";
		       	formtest = false;
		      }	
		}
		
		if (lat!=''){
			if (isNaN(lat) ||  (lat < 39.988475) || (lat > 41.503754)){
			       msg += "- Valore errato per il campo Poligono "+ alfabeto[k] +" - Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754. \r\n";
			       formtest = false;
			}		
		}	 
			 
	   	}
	
	for (var k=1; k<=10;k++){
		var lat = document.getElementById("prelievoLatitude"+k).value;
		var lon = document.getElementById("prelievoLongitude"+k).value;
		
		if (lon!='')
		{
			if (isNaN(lon) ||  (lon < 13.7563172) || (lon > 15.8032837)){
		    	msg += "- Valore errato per il campo Punti di Prelievo "+ alfabeto[k] +" - Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837.\r\n";
		       	formtest = false;
		      }	
		}
		
		if (lat!=''){
			if (isNaN(lat) ||  (lat < 39.988475) || (lat > 41.503754)){
			       msg += "- Valore errato per il campo Punti di Prelievo "+ alfabeto[k] +" - Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754. \r\n";
			       formtest = false;
			}		
		}	 	 
	   	}   
	
	
	if(formtest==true){
	
		document.addAccount.submit();
		}
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
function openTreeMatrici()
{

	window.open('Tree.do?command=DettaglioTree&nomeTabella=matrici&nodo=289&campoId=matrice_id&campoPadre=id_padre&campoDesc=nome&campoLivello=livello&campoEnabled=nuova_gestione&sel=true');

}

function gestisciStati(sel){
	var val = sel.value;
	
	if (val!=3){
		document.getElementById("trDataSospensione").style.display='none';
		document.getElementById("dataSospensione").value='';
		document.getElementById("trDataProvvedimento").style.display='none';
	
	}
	if (val!= 4){
		document.getElementById("trDataRevoca").style.display='none';
		document.getElementById("dataRevoca").value='';
		document.getElementById("trDataProvvedimento").style.display='none';

	}
	if (val==3){
		document.getElementById("trDataSospensione").style.display='';
		document.getElementById("trDataProvvedimento").style.display='';
	}
	if (val==4){
		document.getElementById("trDataRevoca").style.display='';
		document.getElementById("trDataProvvedimento").style.display='';
	}
}
</script>

<%
if(request.getAttribute("updateOk")!=null)
{
%>
<script>
opener.location.reload();
window.close();

</script>

<%	

}
%>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>
<%@ include file="../initPage.jsp"%>

<form id="addAccount" name="addAccount"
	action="MolluschiBivalvi.do?command=UpdateClassificazione&auto-populate=true&gestioneClassificazione=1&id=<%=OrgDetails.getId() %>"
	method="post">

<table class="trails" cellspacing="0">
	<tr>
		<td width="100%">Molluschi	Bivalvi > Gestione Classificazione <%=OrgDetails.getName() %></td>
	</tr>
</table>

<input type="button"
	value="Aggiorna"
	name="button" onClick="checkForm()"> 
	
	<input type="button"
	value="Annulla"
	onClick="javascript:window.close();">


<%
SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
%>	
 <br>
<table cellpadding="4" cellspacing="0" border="0" width="100%"class="details" id = "zc">
	<tr>
		<th colspan="2"><strong>Gestione classificazione</strong></th></tr>
	<tr id = "classeId">
		<td nowrap class="formLabel" name="orgname1" id="orgname1"> Stato classificazione</td>
		<td>
  		<% StatiClassificazione.setJsEvent("onchange=\"gestisciStati(this)\""); %>
  		<%=StatiClassificazione.getHtmlSelect("statiClassificazione",OrgDetails.getStatoClassificazione()) %><font color="red">*</font>

    	
		<input type ="hidden" name = "classe" value = "<%=OrgDetails.getClasse() %>">
		</td>
		<input type="hidden" id="tipoSospensione" name="tipoSospensione" value=""/>
		<input type = "hidden" name ="oldClasse" value = "<%=OrgDetails.getClasse() %>">
	</tr>
	
	<tr id = "trDataSospensione" <%if (OrgDetails.getStatoClassificazione()!=Organization.STATO_CLASSIFICAZIONE_SOSPESO){ %>style="display: none"<%} %>>
		<td nowrap class="formLabel" >Data sospensione</td>
		<td><input readonly type="text" id="dataSospensione" name="dataSospensione" value = "<%=toDateasString(OrgDetails.getDataSospensione()) %>" size="10" />
		<a href="#" onClick="cal19.select(document.forms[0].dataSospensione,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
		<font color="red">*</font></td>
	</tr> 
	
	<tr id = "trDataRevoca" <%if (OrgDetails.getStatoClassificazione()!=Organization.STATO_CLASSIFICAZIONE_REVOCATO){ %>style="display: none"<%} %>>
		<td nowrap class="formLabel" >Data revoca</td>
		<td><input readonly type="text" id="dataRevoca" name="dataRevoca" value = "<%=toDateasString(OrgDetails.getDataRevoca()) %>" size="10" />
		<a href="#" onClick="cal19.select(document.forms[0].dataRevoca,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
		<font color="red">*</font></td>
	</tr>
	
	
	<tr id = "zc1" style="display: none">
		<td nowrap class="formLabel" ><%=(OrgDetails.getTipoMolluschi()==5) ? "Numero Decreto" : "Numero Decreto Riclassificazione" %></td>
		<td><input type = "text" name = "numClassificazione" value = "<%=toHtml2(OrgDetails.getNumClassificazione()) %>"><font color="red">*</font></td>
	</tr>
	
	
<input type = "hidden" name = "dataClassificazione" value = "<%=( (OrgDetails.getDataClassificazione() != null && !OrgDetails.getDataClassificazione().equals("")) ? sdf.format(new Date (OrgDetails.getDataClassificazione().getTime())) : "")  %>">
	
	<tr id = "trDataProvvedimento" >
		<td nowrap class="formLabel" >Data Provvedimento</td>
		<td>
		<input readonly type="text" id="dataProvvedimento" name="dataProvvedimento" value = "<%=toDateasString(OrgDetails.getDataProvvedimento())%>" size="10" />
		<a href="#" onClick="cal19.select(document.forms[0].dataProvvedimento,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
  
       </td>
	</tr>
	
	
	<tr >
		<td nowrap class="formLabel" > Tipologia Zona</td>
		<td>
		<%if (OrgDetails.getTipoMolluschi()==5){ %>
		<%=ZoneProduzione.getHtmlSelect("tipoMolluschi",-1) %>
		<%}
	else
	{
		%>
		<%=ZoneProduzione.getSelectedValue(OrgDetails.getTipoMolluschi()) %>
		<input type = "hidden" name = "tipoMolluschi" value = "<%=OrgDetails.getTipoMolluschi() %>" >
		<%
		
	}%>
       <font color="red">*</font> 
       </td>
	</tr>
	
	
	  <tr>
      <td name="tipoCampione1" id="tipoCampione1" nowrap class="formLabel">
        <dhv:label name="">Specie Molluschi</dhv:label>
      </td>
      <td>
  		<%HashMap<Integer,String> molluschi = (HashMap<Integer,String>)request.getAttribute("Molluschi");
  		HashMap<Integer,String> molluschiPresenti =  OrgDetails.getTipoMolluschiInZone();
  		
  		%>
  		<select name = "molluschi" id = "molluschi" multiple="multiple">
  		<%
  		
  		Iterator<Integer> itMoll = molluschi.keySet().iterator();
  		while (itMoll.hasNext())
  		{
  			int idMoll = itMoll.next();
  			String path = molluschi.get(idMoll);
  			%>
  			<option value = "<%=idMoll %>" <%if(idMoll >0 && molluschiPresenti.containsKey(idMoll)){%>selected="selected" <%} %>><%=path %></option>
  			<%
  		}
  		%>
  		
  		</select>
    	</td>
     	</tr>
	
	  <%char alfabeto[] = {'0', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'L', 'M', 'N', 'O'}; %>
	  
	  
	  <%
OrganizationAddress oa = new OrganizationAddress();
if(OrgDetails.getAddressList().size()>0)
	oa = (OrganizationAddress) OrgDetails.getAddressList().get(0);
%>
	
	<tr class="containerBody">
	<td nowrap="nowrap" class="formLabel">Modifica/Inserisci <br/>Coordinate <br/> Poligono</td>
	<td>
	<%int k = 0; %>
	<% for( k = 0; k <oa.getListaCoordinatePoligono().size() ; k++ )
			{
				CoordinateMolluschiBivalvi coord = oa.getListaCoordinatePoligono().get(k);	%>
				<input type = "hidden" name = "poligonoAddressId<%=(k+1) %>" value = "<%=coord.getAddressId() %>">
				<input type = "hidden" name = "poligonoId<%=(k+1) %>" value = "<%=coord.getId() %>">
				<b><%=coord.getIdentificativo() %></b> .
				Lat. <input type="text" id="poligonoLatitude<%=(k+1) %>"name="poligonoLatitude<%=(k+1) %>" value="<%=(coord.getLatitude()>0 ) ? Math.floor(coord.getLatitude() * 100000) / 100000  : ""%>" maxlength="8" size="8">
				Long. <input type="text" id="poligonoLongitude<%=(k+1) %>" name="poligonoLongitude<%=(k+1) %>" value="<%=(coord.getLongitude()>0) ? Math.floor(coord.getLongitude() * 100000) / 100000 : "" %>" maxlength="8" size="8">
				<br/>
				<%
			} 
	
	while (k<10){%>
		<b><%=alfabeto[k+1] %></b> -
		Lat. <input type="text" id="poligonoLatitude<%=(k+1) %>"name="poligonoLatitude<%=(k+1) %>" value="" maxlength="8" size="8">
		Long. <input type="text" id="poligonoLongitude<%=(k+1) %>" name="poligonoLongitude<%=(k+1) %>" value="" maxlength="8" size="8">
		<br/>
				
<%	k++;
}
		%>
	</td></tr>	
	
	  
	
	
	<tr class="containerBody">
	<td nowrap="nowrap" class="formLabel">Modifica/Inserisci <br/>Coordinate <br/> Punti di prelievo</td>
	<td>
	<%int j = 0; %>
	<% for( j = 0; j <oa.getListaCoordinatePuntiDiPrelievo().size() ; j++ )
			{
				CoordinateMolluschiBivalvi coord = oa.getListaCoordinatePuntiDiPrelievo().get(j);	%>
				<input type = "hidden" name = "prelievoAddressId<%=(j+1) %>" value = "<%=coord.getAddressId() %>">
				<input type = "hidden" name = "prelievoId<%=(j+1) %>" value = "<%=coord.getId() %>">
				<b><%=coord.getIdentificativo() %></b> .
				Lat. <input type="text" id="prelievoLatitude<%=(j+1) %>"name="prelievoLatitude<%=(j+1) %>" value="<%=(coord.getLatitude()>0) ? Math.floor(coord.getLatitude() * 100000) / 100000 : "" %>" maxlength="8" size="8">
				Long. <input type="text" id="prelievoLongitude<%=(j+1) %>" name="prelievoLongitude<%=(j+1) %>" value="<%=(coord.getLongitude()>0) ? Math.floor(coord.getLongitude() * 100000) / 100000 : "" %>" maxlength="8" size="8">
				<br/>
				<%
			} 
	
	while (j<10){%>
		<b><%=alfabeto[j+1] %></b> -
		Lat. <input type="text" id="prelievoLatitude<%=(j+1) %>"name="prelievoLatitude<%=(j+1) %>" value="" maxlength="8" size="8">
		Long. <input type="text" id="prelievoLongitude<%=(j+1) %>" name="prelievoLongitude<%=(j+1) %>" value="" maxlength="8" size="8">
		<br/>
				
<%	j++;
}
		%>
	</td></tr>	
	
</table>



<br>
<input type="button"
	value="Aggiorna"
	name="Save" onClick="checkForm()"> 
	<input type="button"
	value="Annulla"
	onClick="javascript:window.close();">
</form>