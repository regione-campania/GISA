<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page import="java.util.*,org.aspcfs.utils.*"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ include file="../initPage.jsp"%>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="LookupSpecie" class="org.aspcfs.utils.web.LookupList"	scope="request" />


<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
        <script type="text/javascript" src="dwr/engine.js"> </script>
        <script type="text/javascript" src="dwr/util.js"></script>
        <script type="text/javascript">

			
        function openChk_bns(){
        	var res;
        	var result;
        	var specie = document.forms[0].specie.value;
        	var anno = document.forms[0].anno.value;
        	window.open('Allevamenti.do?command=RendicontazioneBa&anno='+anno+'&specie='+specie,'popupSelect',
        	'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
        }   
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">


	<tr>
		<td width="100%"><a href="Stabilimenti.do"><dhv:label
			name="">Benessere Animale</dhv:label></a> > <dhv:label
			name="">Rendicontazione Schede Benessere Animale</dhv:label>
		</td>
	</tr>
</table>
<%-- End Trails --%>

<br>
<form method="post" onsubmit="">

	
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong>Filtra</strong>
          </th>
        </tr>
	
	
        
        	<tr>
          <td nowrap class="formLabel">
           	Specie
          </td>
          <td>
           	<select name = "specie">
           <option value = "-1">--Seleziona--</option>
           	<option value = "131">Gallus/Galline Ovaiole</option>
           	<option value = "122">Suini</option>
           	<option value = "1211">Vitelli</option>
           	<option value = "139">Fagiani</option>
           	<option value = "125">Caprini</option>
           	<option value = "146">Avicoli Misti</option>
           	<option value = "129">Bufalini</option>
           	<option value = "121">Bovini</option>
           	<option value = "126">Cavalli</option>
           	<option value = "124">Ovini</option>
           	<option value = "134">Quaglie</option>
           	<option value = "160">Pesci</option>
           	<option value = "1461">Polli da carne</option>
           	<option value = "128">Conigli</option>
           	</select>
          </td>
        </tr>
        <tr>
          <td nowrap class="formLabel">
           	Anno
          </td>
          <td>
           	<select name = "anno">
           	<option value = "-1">--Seleziona--</option>
           	<option value = "2011">2011</option>
           	<option value = "2012">2012</option>
           	<option value = "2013">2013</option>
           	</select>
          </td>
        </tr>
        
        
</table>
<input type = "submit" value = "VISUALIZZA" onclick="javascript:openChk_bns();">
</form>



	