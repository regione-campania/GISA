<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.molluschibivalvi.base.*"%>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.molluschibivalvi.base.Organization" scope="request" />
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
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/popLookupSelect.js"></SCRIPT>

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
	if(document.addAccount.siteId.value =='' || document.addAccount.siteId.value =='-1')
	{
		formtest = false ;
		msg += 'Il campo asl deve essere popolato\n'
	}
	
	if(document.addAccount.tipoMolluschi.value =='-1')
	{
		formtest = false ;
		msg += 'Il campo zona di produzione deve essere popolato\n'
	}

// 	if(document.addAccount.address1latitude.value =='' || document.addAccount.address1longitude.value =='')
// 	{
// 		formtest = false ;
// 		msg += 'Inserire almeno una coppia di coordinate \n'
// 	}
// 	else
// 	{

// 		form = document.addAccount ;
// 		indice = 1 ;
// 		while (document.getElementById('address'+indice+'latitude')!=null)
// 		{
// 		   if (document.getElementById('address'+indice+'latitude') && document.getElementById('address'+indice+'latitude').value!=""){
// 		       	 //alert(!isNaN(form.address2latitude.value));
		       		
// 		       			if (isNaN(document.getElementById('address'+indice+'latitude').value) ||  (document.getElementById('address'+indice+'latitude').value < 39.988475) || (document.getElementById('address'+indice+'latitude').value > 41.503754)){
// 		       				msg += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754 (Sede Operativa)\r\n";
// 		       				formtest = false;
// 		        			}		 
		       		
// 		    	 }   


		   	 
// 		   	 if (document.getElementById('address'+indice+'longitude') && document.getElementById('address'+indice+'longitude').value!=""){
// 		      	 //alert(!isNaN(form.address2longitude.value));
		      		
// 		      			if (isNaN(document.getElementById('address'+indice+'longitude').value) ||  (document.getElementById('address'+indice+'longitude').value < 13.7563172) || (document.getElementById('address'+indice+'longitude').value > 15.8032837)){
// 		      				msg += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837 (Sede Operativa)\r\n";
// 		       			formtest = false;
// 		       			}		 
		      		
// 		   	 }  
// 		   	 indice ++ ;
// 		}
// 	}
	
	
	

	var select = document.getElementById('molluschi');
	for (i = 0 ; i < select.length ; i ++)
	{
		if(select.options[i].value == '-1' && select.options[i].selected==true)
		{
			formtest = false ;
			msg += 'Il campo Molluschi non deve contenere seleziona voce\n' ;
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
	document.getElementById('classeId').style.display= "none";
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

<form id="addAccount" name="addAccount"
	action="MolluschiBivalvi.do?command=Insert&auto-populate=true"
	method="post">

<table class="trails" cellspacing="0">
	<tr>
		<td width="100%"><a href="MolluschiBivalvi.do">Molluschi
		Bivalvi</a> > Aggiungi</td>
	</tr>
</table>

<input type="button"
	value="Inserisci"
	name="Save" onClick="checkForm()"> <input type="button"
	value="Annulla"
	onClick="javascript:this.form.action='MolluschiBivalvi.do';this.form.submit()">

<br />
<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="details">
	<tr>
		<th colspan="2"><strong>Aggiungi Zona di Produzione</strong></th>
	</tr>

	<tr>
		<td nowrap="nowrap" class="formLabel">ASL</td>
		<td><dhv:evaluate if="<%= User.getSiteId() == -1 %>">
			<%= SiteIdList.getHtmlSelect("siteId",OrgDetails.getSiteId()) %>
			<font color="red">*</font>
		</dhv:evaluate> <dhv:evaluate if="<%= User.getSiteId() != -1 %>">
			<%= SiteIdList.getSelectedValue(User.getSiteId()) %>
			<input type="hidden" name="siteId" value="<%=User.getSiteId()%>">
		</dhv:evaluate></td>
	</tr>

	<tr>
		<td nowrap class="formLabel" > Zona di produzione</td>
		<td>
		<%//ZoneProduzione.setJsEvent("onchange=showCampi()"); %>
		<%//ZoneProduzione.getHtmlSelect("tipoMolluschi",OrgDetails.getTipoMolluschi()) %>
		<select name = "tipoMolluschi" id = "tipoMolluschi" onchange="showCampi()">
		<option value = "-1">Seleziona Tipo di Zona</option>
		<dhv:permission name="molluschibivalvi-addbanco-view">
		<option value = "1">Banchi naturali</option>
		</dhv:permission>
		<dhv:permission name="molluschibivalvi-addspecchio-view">
		<option value = "5">Specchio acqueo in fase di prima classificazione</option>
		</dhv:permission>
		<dhv:permission name="molluschibivalvi-addabusivi-view">
		<option value = "4">Zone non in concessione / Impianti abusivi</option>
		</dhv:permission>
<!--		<dhv:permission name="molluschibivalvi-addstabulazione-view">-->
<!--		<option value = "3">"Zona di stabulazione"</option>-->
<!--		</dhv:permission>-->
		</select>
			<font color="red">*</font>
		 </td>
	</tr> 
	
<!-- 	<tr> -->
<!-- 		<td nowrap class="formLabel"> Codice Univoco Nazionale(CUN)'</td> -->
<%-- 		<td><input type = "text" name = "cun" maxlength="7" value="<%=toHtml2(OrgDetails.getCun()) %>">	 --%>
		
<!-- 		</td> -->
<!-- 	</tr> -->
	
	   <tr>
      <td name="tipoCampione1" id="tipoCampione1" nowrap class="formLabel">
        <dhv:label name="">Specie Molluschi</dhv:label>
      </td>
      <td>
  		<%HashMap<Integer,String> molluschi = (HashMap<Integer,String>)request.getAttribute("Molluschi");
  		
  		
  		%>
  		<select name = "molluschi" id = "molluschi" multiple="multiple">
  		<option value = "-1" selected="selected">Seleziona </option>
  		<%
  		
  		Iterator<Integer> it = molluschi.keySet().iterator();
  		while (it.hasNext())
  		{
  			int idMoll = it.next();
  			String path = molluschi.get(idMoll);
  			%>
  			<option value = "<%=idMoll %>"><%=path %></option>
  			<%
  		}
  		%>
  		
  		</select><font color="red">*</font>
    	</td>
     	</tr>
	
	
		<tr id = "classeId" style="display: none">
		<td nowrap class="formLabel" name="orgname1" id="orgname1"> Classificazione</td>
		<td><%=Classificazione.getHtmlSelect("classe",-1) %></td>
	</tr>
</table>
<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" id = "abusivi" style="display:none"
	class="details" id = "zc">
	<tr>
		<th colspan="2"><strong><dhv:label name="">Zone non in concessione / Impianti abusivi</dhv:label></strong>
		</th></tr>

<tr>
		<td nowrap class="formLabel" name="orgname1" id="orgname1"> Nome</td>
		<td><input type = "text" name = "nome" value = "<%=toHtml2(OrgDetails.getNome()) %>"></td>
	</tr>
	
	<tr>
		<td nowrap class="formLabel" name="orgname1" id="orgname1"> Cognome</td>
		<td><input type = "text" name = "cognome" value = "<%=toHtml2(OrgDetails.getCognome()) %>"></td>
	</tr>
	
	<tr>
		<td nowrap class="formLabel" name="orgname1" id="orgname1"> Note</td>
		<td><textarea name = "note"><%=toHtml2(OrgDetails.getNote()) %></textarea></td>
	</tr>
	
	
	</table>
<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="details">
	<tr>
		<th colspan="2"><strong><dhv:label name="">Sito</dhv:label></strong>
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
                <option value="<%=prov4%>" ><%= prov4 %></option>	
              <%}%>
		
	</select> 	<font color="red">*</font>
		</td>
	</tr>
	
	
	
	<tr>
		<td nowrap class="formLabel" name="orgname1" id="orgname1"> Localita'</td>
		<td><input type = "text" name = "name" value="<%=toHtml2(OrgDetails.getName()) %>">	<font color="red">*</font>
		<input type="hidden" size="40" id="address1line1" name="address1line1" maxlength="80" >
	<input type="hidden" size="28" name="address1zip" maxlength="5">
	<input type="hidden" size="28" name="address1state" maxlength="80">
		</td>
	</tr>
	
	


<!-- 	<tr class="containerBody"> -->
<!-- 	<td nowrap="nowrap" class="formLabel">Coordinate</td> -->
<!-- 	<td> -->
<!-- 	<input type = "hidden" name = "elementi" id = "elementi" value = "1"> -->
<!-- 	<input type = "hidden" name = "size" id = "size" value = "1"> -->
<!-- 	<table> -->
<!-- 	<tr id = "riga"> -->
<!-- 	<td>Latitudine&nbsp;</td><td><input type="text" id="address1latitude"name="address1latitude" size="30" readonly="readonly"><font color="red">*</font></td> -->
<!-- 		<td>Longitudine&nbsp;</td><td><input type="text" id="address1longitude" name="address1longitude" size="30" readonly="readonly"><font color="red">*</font></td></tr> -->
		
<!-- 	<tr style="display: block"> -->
<!-- 		<td colspan="2"><input id="coordbutton" type="button" -->
<!-- 			value="Calcola Coordinate" -->
<!-- 			onclick="javascript:showCoordinate(document.getElementById('address1line1').value, document.forms['addAccount'].address1city.value,document.forms['addAccount'].address1state.value, document.forms['addAccount'].address1zip.value, document.forms['addAccount'].address1latitude, document.forms['addAccount'].address1longitude);" /> -->
<!-- 		</td> -->
<!-- 	</tr>	 -->
	
<!-- 	</table>  -->
<!-- 	</td> -->
<!-- 	</tr> -->
	
	
	
</table>

<br>
<input type="button"
	value="Inserisci"
	name="Save" onClick="checkForm()"> <input type="button"
	value="Annulla"
	onClick="javascript:this.form.action='MolluschiBivalvi.do';this.form.submit()">


</form>

