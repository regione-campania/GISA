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
    
    function checkForm()
    {
    	document.forms[0].doContinue.value="true";
    	msg = "Attenzione Controllare di aver compilato i seguenti campi\n" ;

    	controllaCampi = true ;
    	if (document.getElementById("tipo_attivita")!=null && document.getElementById("tipo_attivita").value=='3')
    	{
    		controllaCampi=false ;
    	}
    		
    	if($("#searchcodeIdprovinciaSL").length > 0 && controllaCampi == true)
        {
        	if( document.forms[0].searchcodeIdprovinciaSL.value=='-1')
    		{
        		alert(document.forms[0].searchcodeIdprovinciaSL.value);
    			document.forms[0].doContinue.value="false";
    			msg+= "- Campo provincia sede legale richiesto \n" ;
    		}
    	}
//     	if (parseInt(document.getElementById('id_operatore').value)<=0  )
//     		{
//     		document.forms[0].doContinue.value="false";
//     		msg+= "- Controllare di aver Selezionato L'impresa \n" ;
    		
//     		}
    	if(document.forms[0].searchcodeIdComune.value=='-1' && controllaCampi == true)
    	{
    		document.forms[0].doContinue.value="false";
    		msg+= "- Campo comune  richiesto \n" ;
    	}
    	
    	if((document.forms[0].viainput.value=='' || document.forms[0].viainput.value=='Seleziona Indirizzo') && controllaCampi == true)
    	{
    		document.forms[0].doContinue.value="false";
    		msg+= "- Campo indirizzo  richiesto \n" ;
    	}
    	

    	if($("#codFiscaleSoggetto").length =0 && controllaCampi == true)
    	{
    		document.forms[0].doContinue.value="false";
    		msg+= "- Campo codice fiscale soggetto richiesto \n" ;
    	}

    	if($("#nome").length > 0 && document.forms[0].nome.value=='' && controllaCampi == true)
    	{
    		document.forms[0].doContinue.value="false";
    		msg+= "- Campo nome soggetto fisico richiesto \n" ;
    	}

    	if($("#cognome").length > 0 &&document.forms[0].cognome.value=='' && controllaCampi == true)
    	{
    		document.forms[0].doContinue.value="false";
    		msg+= "- Campo cognome soggetto fisico richiesto \n" ;
    	}
    	
    	if(document.forms[0].numLineeProduttive.value=="0")
    		{
    		document.forms[0].doContinue.value="false";
    		msg+= "- Controllare di Aver Selezionato almeno una Linea Produttiva \n" ;
    		}

		
    
    	if(document.forms[0].doContinue.value=="false")
        {
    		$('#mask').hide();
			$('.window').hide();
    		alert(msg);
    		
    	}
    	else
    	{
    		document.forms[0].doContinue.value="true";
    		document.forms[0].submit();
    	}
    }    
    
  
  </script>

<dhv:evaluate if="<%=!isPopup(request)%>">
	<table class="trails" cellspacing="0">
		<tr>
			<td>DIA -> Aggiungi </td>
		</tr>
	</table>
</dhv:evaluate>

<%
if (request.getAttribute("Exist") != null && !("").equals(request.getAttribute("Exist"))) 
{
%> 
<font color="red"><%=(String) request.getAttribute("Exist")%></font>
<%
}
if (request.getAttribute("Error") != null && !("").equals(request.getAttribute("Error"))) 
{
%> 
<font color="red"><%=(String) request.getAttribute("Error")%></font>
<%
}
%> 

<form id="addStabilimento" name="addStabilimento" action="<%=newStabilimento.getPrefissoAction(1) %>.do?command=InsertOpu&auto-populate=true" method="post" onsubmit="javascript:return loadModalWindow();">
<%
	boolean popUp = false;
  	if(request.getParameter("popup")!=null)
  	{
    	popUp = true;
  	}
%> 
<input type="button" value="Inserisci" name="Save" onClick="javascript:verificaSoggetto(document.getElementById('codFiscaleSoggetto'))"> <dhv:formMessage showSpace="false" />



<dhv:evaluate if="<%= popUp %>">
	<input type="button" value="Annulla" onClick="javascript:self.close();">
</dhv:evaluate> 

<br/>

<input type = "hidden" id = "sovrascrivi" name = "sovrascrivi" value = "si"/>
<input type = "hidden" id = "idAsl" name = idAsl value = ""/>
<input type = "hidden" id = "descrAsl" name = "descrAsl" value = ""/>
<input type = "hidden" id = "idNorma" name = "idNorma" value = "<%=request.getAttribute("idNorma") %>">
<input type = "hidden" name = "tipologiaSoggetto" value = "<%=(request.getAttribute("tipologiaSoggetto")!=null)? (String)request.getAttribute("tipologiaSoggetto"):"" %>">





<jsp:include page="dia_add_soggetto_fisico.jsp"/>
<br>
<jsp:include page="dia_add_impresa.jsp"/>
<br>
<jsp:include page="dia_add_stabilimento.jsp"/>
<br>
<jsp:include page="opu_informazioni_852.jsp"/>
<br/>
<jsp:include page="opu_linee_attivita_add.jsp"/>




<input type="hidden" name="doContinue" id="doContinue" value="">
<input type="button" value="Inserisci" name="Save" onClick="verificaSoggetto(document.getElementById('codFiscaleSoggetto'))"> 

</form>


<!-- POP UP MODALE  -->
<div id="boxes">
	<div id="dialog4" class="window" >
   		<a href="#" class="close"/><b>Chiudi</b></a>
  		<table  border="0" width="100%" class="details">
			<tr>
				<th colspan="2"><strong><div id = "intestazione"></div></strong></th>
			</tr>
			<tr>
				<td class="formLabel" nowrap>Nome</td>
				<td><div id = "nomeSoggetto"></div></td>
			</tr>
			<tr>
				<td class="formLabel" nowrap>Cognome</td>
				<td><div id = "cognomeSoggetto"></div></td>
			</tr>
			<tr>
				<td class="formLabel" nowrap>Sesso</td>
				<td><div id = "sessoSoggetto"></div></td>
			</tr>
			<tr>
				<td class="formLabel" nowrap>Data Nascita</td>
				<td><div id = "dataNascitaSoggetto"></div></td>
			</tr>
			<tr>
				<td class="formLabel" nowrap>Comune Nascita</td>
				<td><div id = "comuneNascitaSoggetto"></div></td>
			</tr>
			<tr>
				<td class="formLabel" nowrap>Provincia Nascita</td>
				<td><div id = "provinciaNascitaSoggetto"></div></td>
			</tr>
			<tr>
				<td class="formLabel" nowrap>Comune Residenza</td>
				<td><div id = "comuneResidenzaSoggetto"></div></td>
			</tr>
			<tr>
				<td class="formLabel" nowrap>Provincia Residenza</td>
				<td><div id = "provinciaResidenzaSoggetto"></div></td>
			</tr>
			<tr>
				<td class="formLabel" nowrap>Indirizzo Residenza</td>
				<td><div id = "indirizzoResidenzaSoggetto"></div></td>
			</tr>
			<tr>
				<td class="formLabel" nowrap>Documento</td>
				<td><div id = "documentoSoggetto"></div></td>
			</tr>
			<tr>
				<td class="formLabel" nowrap>e-mail</td>
				<td><div id = "mailSoggetto"></div></td>
			</tr>
			<tr>
				<td class="formLabel" nowrap>telefono principale</td>
				<td><div id = "telefono1Soggetto"></div></td>
			</tr>
			<tr>
				<td class="formLabel" nowrap>cellulare/tel. secondario</td>
				<td><div id = "telefono2Soggetto"></div></td>
			</tr>
			<tr>
				<td class="formLabel" nowrap>Fax</td>
				<td><div id = "faxSoggetto"></div></td>
			</tr>
			<tr id="azione">
				<td><input type = "button" value = "Sovrascrivi" onclick="document.getElementById('sovrascrivi').value='si';checkForm();"/></td>
				<td><input type = "button" value = "Non Sovrascrivere" onclick="document.getElementById('sovrascrivi').value='no';checkForm();" /></td>
			</tr>
		</table>
</div>



<div id="dialog" title="Stabilimento Esistente! vuoi andare al dettaglio ?" style="height: 80px;">
</div>

<!-- Mask to cover the whole screen -->
  <div id="mask"></div>

</div>

