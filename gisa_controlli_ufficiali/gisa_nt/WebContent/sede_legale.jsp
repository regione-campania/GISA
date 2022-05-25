<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script>
function setta_sede_legale(sl_city,sl_line1,sl_zip,sl_state,sl_latitude,sl_longitude,so_city,so_line1,so_zip,so_state,so_latitude,so_longitude){
 	var chk = document.getElementById("chk_setta_sede_legale");
 	if (chk.checked==true){
		document.getElementById(sl_city).value = so_city.value; 
		document.getElementById(sl_city).disabled=""; 
		document.getElementById(sl_city).readOnly="readOnly";
		
		document.getElementById(sl_zip).value = so_zip.value;   
		document.getElementById(sl_zip).disabled=""; 
		document.getElementById(sl_zip).readOnly="readOnly";
		
		document.getElementById(sl_state).value = so_state.value; 
		document.getElementById(sl_state).disabled=""; 
		document.getElementById(sl_state).readOnly="readOnly";
		
		document.getElementById(sl_latitude).value = so_latitude.value;
		document.getElementById(sl_longitude).value = so_longitude.value;
		document.getElementById(sl_line1).value = so_line1.value;
 	} else {
 		document.getElementById(sl_city).value = ""; 
 		document.getElementById(sl_city).disabled="";
 		document.getElementById(sl_city).readOnly="";
 		
		document.getElementById(sl_zip).value = "";   
		document.getElementById(sl_zip).disabled="";
		document.getElementById(sl_zip).readOnly="";
		
		document.getElementById(sl_state).value = ""; 
		document.getElementById(sl_state).disabled="";
		document.getElementById(sl_state).readOnly="";

		document.getElementById(sl_latitude).value = "";
		document.getElementById(sl_longitude).value = "";
		document.getElementById(sl_line1).value = "";
 	}
}
</script>

<script>
function disabilita_chk(){
	document.getElementById("chk_setta_sede_legale").checked="";
	document.getElementById("chk_setta_sede_legale").disabled="disabled";
}

function abilita_chk(){
	document.getElementById("chk_setta_sede_legale").checked="";
	document.getElementById("chk_setta_sede_legale").disabled="";
}
</script>

<script>
function setLabelSedeLegale(prov){
	if (prov=='ESTERO'){
		document.getElementById("label_city").innerHTML="NAZIONE";
		document.getElementById("label_prov").innerHTML="CITTA'";
		disabilita_chk();
	} else if (prov=='ITALIA'){
		document.getElementById("label_city").innerHTML="COMUNE";
		document.getElementById("label_prov").innerHTML="PROVINCIA";
		abilita_chk();
	}
}
</script>

<input type="checkbox" 
	    		id="chk_setta_sede_legale" 
	    		name="chk_setta_sede_legale" 
	    		value="" 
	    		onchange="javascript:setta_sede_legale('address1city','address1line1','address1zip','address1state','address1latitude','address1longitude',
	    		document.forms[0].address2city,document.forms[0].address2line1,document.forms[0].address2zip,document.forms[0].address2state,document.forms[0].address2latitude,document.forms[0].address2longitude);">
	    		<font color="RED">Copia valori da Sede Operativa</font></input>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>Sede Legale</strong>
	    <input type="hidden" name="address1type" value="1">
	  </th>
  <tr>
	<td nowrap class="formLabel" name="province" id="province">
      <label id="label_city">COMUNE</label>
    </td> 
    <td > 
    <table class = "noborder">
    <td>
    
    <select  name="address1city" id="provs" style="display: none" disabled="disabled">
	<option value="-1">Nessuna Selezione</option>
            
	 <%
                Vector v = OrgDetails.getComuni2();
	 			Enumeration e=v.elements();
                while (e.hasMoreElements()) {
                	String prov4=e.nextElement().toString();
                	
        %>
                <option value="<%=prov4%>" <%if(prov4.equalsIgnoreCase(AddressSedeOperativa.getCity())) {%> selected="selected" <%} %>><%= prov4 %></option>	
              <%}%>
		
	</select> 
	
    <input type="text" name="address1city" id="address1city" value = "<%=toHtmlValue(AddressSedeOperativa.getCity()) %>" style="display: block;">
	</td><td><div id = "sl"></div> </td></table>
	
	</td>
  	</tr>	
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
      <input type="text" size="40" id="address1line1" name="address1line1" maxlength="80" value="<%= toHtmlValue(AddressSedeOperativa.getStreetAddressLine1()) %>"><font color="red">*</font>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="">C/O</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address1line2" maxlength="80" value = "<%=toHtmlValue(AddressSedeOperativa.getStreetAddressLine2()) %>">
    </td>
  </tr>

  
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="28" id="address1zip" name="address1zip" maxlength="5" value = "<%=toHtmlValue(AddressSedeOperativa.getZip()) %>">
    </td>
  </tr>
  
  	<tr>
    <td nowrap class="formLabel">
      <label id="label_prov">PROVINCIA</label>
    </td>
    <td>
    	  <input type="text" size="28" id="address1state" name="address1state" maxlength="80" value="<%= toHtmlValue(AddressSedeOperativa.getCityState()) %>">          
      <div id="div_provincia" style="display: none;">  
      <input type="text"  size="28" name="address1state1" maxlength="80" >  
          
       </div>      
    </td>
  </tr>
 
  
  
   <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="requestor.address.latitude">Latitude</dhv:label></td>
    <td>
    	<input type="text" readonly="readonly" id="address1latitude" name="address1latitude" size="30" value="<%=AddressSedeOperativa.getLatitude() %>" >
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="requestor.address.longitude">Longitude</dhv:label></td>
    <td><input type="text" readonly="readonly" id="address1longitude" name="address1longitude" size="30" value="<%=AddressSedeOperativa.getLongitude() %>" ></td>
  </tr>
  <tr style="display: block">
    <td colspan="2">
    	<input id="coordbutton" type="button" value="Calcola Coordinate"
    	 onclick="javascript:showCoordinate(document.getElementById('address1line1').value, document.getElementById('address1city').value,document.forms['addAccount'].address1state.value, document.forms['addAccount'].address1zip.value, document.forms['addAccount'].address1latitude, document.forms['addAccount'].address1longitude);"  
    	/> 
    </td>
  </tr> 
</table>
<br>