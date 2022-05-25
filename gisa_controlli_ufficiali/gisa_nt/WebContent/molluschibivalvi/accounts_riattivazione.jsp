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
<jsp:useBean id="ZoneProduzione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Classificazione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application" />
<jsp:useBean id="Provvedumento" class="org.aspcfs.modules.molluschibivalvi.base.HistoryClassificazione" scope="application" />


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
	
// 	if(document.addAccount.numClassificazione.value =='')
// 	{
// 		formtest = false ;
// 		msg += 'Il campo num. descreto deve essere popolato\n'
// 	}
	if(document.addAccount.dataProvvedimento.value =='' )
	{
		formtest = false ;
		msg += 'Il campo data Riattivazione deve essere popolato\n'
	}
	if(document.addAccount.tipoMolluschi.value =='-1' )
	{
		formtest = false ;
		msg += 'Il campo data Cassifica In deve essere popolato\n'
	}
	
	var select = document.getElementById('molluschi');
	for (i = 0 ; i < select.length ; i ++)
	{
		if(select.options[i].value ==  '-1' &&  select.options[i].selected==true)
		{
			formtest = false ;
			msg += 'Il campo Mollusci non deve contenere seleziona voce\n' ;
			break ;
		}
	}
	
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
function openTreeMatrici()
{

	window.open('Tree.do?command=DettaglioTree&nomeTabella=matrici&nodo=289&campoId=matrice_id&campoPadre=id_padre&campoDesc=nome&campoLivello=livello&campoEnabled=nuova_gestione&sel=true');

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
	action="MolluschiBivalvi.do?command=UpdateClassificazione&auto-populate=true&id=<%=OrgDetails.getId() %>&riattivazione=1"
	method="post">
<input type="hidden" name = "idProvvedimento" value = "<%=OrgDetails.getIdUltimoProvvedimento()%>">
<table class="trails" cellspacing="0">
	<tr>
		<td width="100%"><a href="MolluschiBivalvi.do">Molluschi
		Bivalvi</a> > Classificazione Scheda <%=OrgDetails.getName() %></td>
	</tr>
</table>

<input type="button"
	value="Aggiorna"
	name="button" onClick="checkForm()"> 
	
		<input type="button"
	value="Annulla"
	onClick="javascript:if(window.opener!=null){window.close();}">




<%
SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
%>	
 <br>
<table cellpadding="4" cellspacing="0" border="0" width="100%"class="details" id = "zc">
	<tr>
		<th colspan="2"><strong><dhv:label name="">Dettagli Riattivazione</dhv:label></strong></th></tr>
	
	
	
	<tr id = "zc2">
		<td nowrap class="formLabel" >Data Riattivazione</td>
		<td>
		<input type = "hidden" name = "numClassificazione" value = "<%=toHtml2(OrgDetails.getNumClassificazione()) %>">
		<%String dataClassificazione = "" ;%>
		<%if(OrgDetails.getDataClassificazione()!=null){
			dataClassificazione = sdf.format(new Date(OrgDetails.getDataClassificazione().getTime())) ;
		}
		%>
			<input readonly type="text" id="dataProvvedimento" name="dataProvvedimento" value = "<%--=dataClassificazione --%>" size="10" />
		<a href="#" onClick="cal19.select(document.forms[0].dataProvvedimento,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
       <font color="red">*</font>
       </td>
       <input type = "hidden" name ="dataClassificazione" value ="<%=toDateasString(OrgDetails.getDataClassificazione())%>"/>
        <input type = "hidden" name ="dataFineClassificazione" value ="<%=toDateasString(OrgDetails.getDataFineClassificazione())%>"/>
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
	
	
	  <tr style="display: none">
      <td name="tipoCampione1" id="tipoCampione1" nowrap class="formLabel">
        <dhv:label name="">Specie Molluschi</dhv:label>
      </td>
      <td>
  		<%HashMap<Integer,String> molluschi = (HashMap<Integer,String>)request.getAttribute("Molluschi");
  		HashMap<Integer,String> molluschiPresenti =  OrgDetails.getTipoMolluschiInZone();
  		
  		%>
  		<select name = "molluschi" id = "molluschi" multiple="multiple">
  		<%
  		
  		Iterator<Integer> it = molluschi.keySet().iterator();
  		while (it.hasNext())
  		{
  			int idMoll = it.next();
  			String path = molluschi.get(idMoll);
  			%>
  			<option value = "<%=idMoll %>" <%if(idMoll > 0 && molluschiPresenti.containsKey(idMoll)){%>selected="selected" <%} %>><%=path %></option>
  			<%
  		}
  		%>
  		
  		</select>
    	</td>
     	</tr>
	
	
</table>



<br>
<input type="button"
	value="Aggiorna"
	name="Save" onClick="checkForm()">
	
		
	<input type="button"
	value="Annulla"
	onClick="javascript:if(window.opener!=null){window.close();}">
	


</form>

