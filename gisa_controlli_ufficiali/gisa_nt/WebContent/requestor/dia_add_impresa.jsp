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
        $( "#searchcodeIdprovinciaSL" ).combobox();
        $( "#searchcodeIdComuneSL" ).combobox();
        $( "#viaSL" ).combobox();
        
     
       
    });
  </script>


<table  border="0" width="100%" class="details">
<tr>
		<th colspan="2">
		<strong>Dati Impresa</strong></th>
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
			<input type="text" size="20" maxlength="200" id="ragioneSociale" name="ragioneSociale" value="<%=""%>">
			<font color="red">*</font></td>
	</tr>

	<tr>
		<td nowrap class="formLabel">
			<dhv:label name="opu.operatore.piva"></dhv:label>
		</td>
		<td>
			<input type="text" size="20" maxlength="11" id="partitaIva" name="partitaIva" value="<%=(Operatore.getPartitaIva() != null) ? Operatore.getPartitaIva() : ""%>">
			<font color="red">*</font></td>
	</tr>

	<tr>
		<td nowrap class="formLabel">
			<dhv:label name="opu.operatore.cf"></dhv:label>
		</td>
		<td>
			<input type="text" size="20" maxlength="16" id="codFiscale" name="codFiscale" value="<%=(Operatore.getCodFiscale() != null) ? Operatore.getCodFiscale() : ""%>">
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



<div id="info_sede">

<table  border="0" width="100%" class="details">
	<tr>
		<th colspan="2">
			<strong>Sede Legale</strong>
		</th>
	</tr>

	<tr>
		<td class="formLabel">
			<dhv:label name="opu.sede_legale.inregione"></dhv:label>
		</td>
		<td>
			<select name="inregioneSedeLegale" id="inregioneSedeLegale">
				<option value="si">SI</option>
				<option value="no">NO</option>
			</select>
		</td>
	</tr>
	<tr>

		<td nowrap class="formLabel">
			<dhv:label name="opu.sede_legale.provincia"></dhv:label>
		</td>
		<td>
			<select name="searchcodeIdprovinciaSL" id="searchcodeIdprovinciaSL">
				<option value="<%=(Operatore.getSedeLegale() != null) ? Operatore.getSedeLegale().getIdProvincia() : ""%>"><%=(Operatore.getSedeLegale() != null && Operatore.getSedeLegale().getIdProvincia()>0)? Operatore.getSedeLegale().getDescrizione_provincia() : "Inserire le prime 4 lettere" %></option>
			</select>
			
			<input type="hidden" name="searchcodeIdprovinciaSLTesto" id="searchcodeIdprovinciaSLTesto" />
			<font color="red">(*)</font> 
		</td>
	</tr>
	
	<tr>
		<td nowrap class="formLabel" name="province" id="province">
			<dhv:label name="opu.sede_legale.comune"></dhv:label>
		</td>
		<td>
			<select name="searchcodeIdComuneSL" id="searchcodeIdComuneSL">
				<option value="<%=(Operatore.getSedeLegale() != null ) ? Operatore.getSedeLegale().getComune() : ""%>" selected="selected"><%=(Operatore.getSedeLegale() != null && Operatore.getSedeLegale().getComune()>0)? Operatore.getSedeLegale().getDescrizioneComune() : "Inserire le prime 4 lettere" %></option>
			</select>
			
			<input type="hidden" name="searchcodeIdComuneSLTesto" id="searchcodeIdComuneSLTesto" />
			<font color="red">(*)</font>
		</td>
	</tr>
	
	<tr>
		<td nowrap class="formLabel">
			<dhv:label name="opu.sede_legale.indirizzo"></dhv:label>
		</td>
		<td>
			<select name="viaSL" id="viaSL"> 
				<option value="<%=(Operatore.getSedeLegale() != null ) ? Operatore.getSedeLegale().getIdIndirizzo() : "-1"%>" selected="selected"><%=(Operatore.getSedeLegale() != null) ? Operatore.getSedeLegale().getVia() : ""%></option>
			</select>
			
			<font color="red">(*)</font> 
			<input type="hidden" name="viaSLTesto" id="viaSLTesto" />
		</td>
	</tr>
	
	<tr>
		<td nowrap class="formLabel">
			<dhv:label name="opu.sede_legale.co"></dhv:label>
		</td>
		<td>
			<input type="text" size="40" name="presso"  value="<%=""%>">
		</td>
	</tr>


	<tr class="containerBody">
		<td class="formLabel" nowrap>
			<dhv:label name="opu.sede_legale.latitudine"></dhv:label>
		</td>
		<td>
			<input type="text" id="latitudineSL" name="latitudineSL" size="30" value="<%=(Operatore.getSedeLegale() != null )? Operatore.getSedeLegale().getLatitudine() :""%>">
		</td>
	</tr>
	
	<tr class="containerBody">
		<td class="formLabel">
			<dhv:label name="opu.sede_legale.longitudine"></dhv:label>
		</td>
		<td>
			<input type="text" id="longitudineSL" name="longitudineSL" size="30" value="<%=(Operatore.getSedeLegale() != null )? Operatore.getSedeLegale().getLongitudine():""%>">
		</td>
	</tr>

	
</table>
</div>