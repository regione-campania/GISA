<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="org.aspcfs.modules.stabilimenti.base.OrganizationAddress"%>
<%@page import="org.aspcfs.modules.stabilimenti.base.SottoAttivita"%>
<%@page import="org.aspcfs.utils.web.LookupElement"%>
<%@page import="org.aspcfs.utils.web.*"%>



<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" 	scope="request" />
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.stabilimenti.base.Organization" scope="request" />
<jsp:useBean id="impianto" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="statoLab" class="org.aspcfs.utils.web.LookupList"	scope="request" />
<jsp:useBean id="tipoAutorizzazioneList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="categoriaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="elencoSottoAttivita" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="LookupClassificazione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="LookupProdotti" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="categoria" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="tipoAutorizzazzione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>
<script type="text/javascript" src="dwr/util.js"></script>
<script>
var campoLat;
	var campoLong;

	var field_id_impianto ;
	function setComboImpianti(idCategoria,idField)
	{
			field_id_impianto = idField;
			
		   	PopolaCombo.getValoriComboImpiantiStabilimenti(idCategoria,setValoriComboCallBack);
		   
	}

	 function setValoriComboCallBack(returnValue)
     {
	    	var select = document.getElementById(field_id_impianto); //Recupero la SELECT
         

         //Azzero il contenuto della seconda select
         for (var i = select.length - 1; i >= 0; i--)
       	  select.remove(i);

         indici = returnValue [0];
         valori = returnValue [1];
         //Popolo la seconda Select
         if (indici.length==0)
         {
        	 var NewOpt = document.createElement('option');
             NewOpt.value = -1; // Imposto il valore
        	 	NewOpt.text = 'Seleziona Categoria'; // Imposto il testo
             	NewOpt.title = valori[j];
             //Aggiungo l'elemento option
             try
             {
           	  	select.add(NewOpt, null); //Metodo Standard, non funziona con IE
             }catch(e){
           	  select.add(NewOpt); // Funziona solo con IE
             }
          }
         else
         {
         
         for(j =0 ; j<indici.length; j++){
         //Creo il nuovo elemento OPTION da aggiungere nella seconda SELECT
         var NewOpt = document.createElement('option');
         NewOpt.value = indici[j]; // Imposto il valore
         if(valori[j] != null)
         	NewOpt.text = valori[j]; // Imposto il testo
         	NewOpt.title = valori[j];
         //Aggiungo l'elemento option
         try
         {
       	  	select.add(NewOpt, null); //Metodo Standard, non funziona con IE
         }catch(e){
       	  select.add(NewOpt); // Funziona solo con IE
         }
         }
         }


     }
		var field_id_prodotti ;

	 function setComboProdotti(idCategoria,idField)
		{
		 	field_id_prodotti = idField;
				
			   	PopolaCombo.getValoriComboProdottiStabilimenti(idCategoria,setComboProdottiCallBack);
			   
		}

		 function setComboProdottiCallBack(returnValue)
	     {
		    	var select = document.getElementById(field_id_prodotti); //Recupero la SELECT
	         

	         //Azzero il contenuto della seconda select
	         for (var i = select.length - 1; i >= 0; i--)
	       	  select.remove(i);

	         indici = returnValue [0];
	         valori = returnValue [1];
	         //Popolo la seconda Select
	         if (indici.length==0)
	         {
	        	 var NewOpt = document.createElement('option');
	             NewOpt.value = -1; // Imposto il valore
	        	 	NewOpt.text = 'Seleziona Categoria'; // Imposto il testo
	             	NewOpt.title = valori[j];
	             //Aggiungo l'elemento option
	             try
	             {
	           	  	select.add(NewOpt, null); //Metodo Standard, non funziona con IE
	             }catch(e){
	           	  select.add(NewOpt); // Funziona solo con IE
	             }
	          }
	         else
	         {
	        	 var NewOpt = document.createElement('option');
	             NewOpt.value = -1; // Imposto il valore
	        	 	NewOpt.text = 'Seleziona uno o piu prodotti'; // Imposto il testo
	             	NewOpt.title = valori['Seleziona uno o piu prodotti'];
	             	NewOpt.selected = true ;
	             //Aggiungo l'elemento option
	             try
	             {
	           	  	select.add(NewOpt, null); //Metodo Standard, non funziona con IE
	             }catch(e){
	           	  select.add(NewOpt); // Funziona solo con IE
	             }
	         for(j =0 ; j<indici.length; j++){
	         //Creo il nuovo elemento OPTION da aggiungere nella seconda SELECT
	         var NewOpt = document.createElement('option');
	         NewOpt.value = indici[j]; // Imposto il valore
	         if(valori[j] != null)
	         	NewOpt.text = valori[j]; // Imposto il testo
	         	NewOpt.title = valori[j];
	         //Aggiungo l'elemento option
	         try
	         {
	       	  	select.add(NewOpt, null); //Metodo Standard, non funziona con IE
	         }catch(e){
	       	  select.add(NewOpt); // Funziona solo con IE
	         }
	         }
	         }


	     }

		 function setComboImpiantiProdotti(idCategoria,idField1,idField2)
		 {
			setComboImpianti(idCategoria,idField1);
			setComboProdotti(idCategoria,idField2);
		 }
	     
	
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

	 function doCheck(form){
		 
			  if(checkForm(form)) {
				  form.submit();
				  return true;
			  }
			  else
				  return false;
			  
		  }

	 var controllo_approval_number = true ;


	function controllo_esistenza_approval_number()
	{
		  
		  if(document.getElementById('numAut')!= null && document.getElementById('numAut').value!='')
		  	PopolaCombo.controlloEsuistenzaApprovalNumber(document.getElementById('numAut').value,controllo_esistenza_approval_number_callback);
	}
	function controllo_esistenza_approval_number_callback(val)
	{
		
		if(val==true)
		{
			alert('Attenzione! Il numero di riconoscimento inserito è assegnato a un altro stabilimento');
			controllo_approval_number = false;
		}
		else
		{
			controllo_approval_number = true ;
		}

	}
	  
	
function viewBodyModify(statoAttuale)
{
	  
	/*if (statoAttuale == 2)
	{
		document.getElementById('esito_controlli').style.display="" ;
		document.getElementById('informazioni_principali').style.display="none" ;
		document.getElementById('lista_impianti').style.display="none" ;
	}*/
	if (statoAttuale == 6 || statoAttuale == 5)
	{
		document.getElementById('esito_controlli').style.display="none" ;
		document.getElementById('informazioni_principali').style.display="" ;
		document.getElementById('lista_impianti').style.display="none" ;
	}
	else
	if (statoAttuale == 1)
	{
		document.getElementById('esito_controlli').style.display="none" ;
		document.getElementById('informazioni_principali').style.display="none" ;
		document.getElementById('lista_impianti').style.display="" ;
	}
	else
	if (statoAttuale == 2)
	{
		document.getElementById('esito_controlli').style.display="none" ;
		document.getElementById('informazioni_principali').style.display="none" ;
		document.getElementById('lista_impianti').style.display="" ;
	}
	else
	if (statoAttuale == 14)
	{
		document.getElementById('esito_controlli').style.display="none" ;
		document.getElementById('informazioni_principali').style.display="none" ;
		document.getElementById('lista_impianti').style.display="" ;
	}
	else
		if (statoAttuale == 0)
		{
			document.getElementById('esito_controlli').style.display="none" ;
			document.getElementById('informazioni_principali').style.display="none" ;
			document.getElementById('lista_impianti').style.display="" ;
		}
	else
	{
		document.getElementById('esito_controlli').style.display="" ;
		document.getElementById('informazioni_principali').style.display="" ;
		document.getElementById('lista_impianti').style.display="" ;
	}
	
	
	
}

function checkForm()
{

	formTest = true ;
	msg = "Controllare di aver selezionato le seguenti informazioni : \n";
	if (document.getElementById('informazioni_principali').style.display=="")
	{
		if (document.addAccount.numAut.value == '')
		{
			msg += " - approval number" ;
			formTest = false ;
		}
		if (document.addAccount.data_assegnazione_approval_number.value == '')
		{
			msg += " - Data Assegnazione approval number in sintesi \n" ;
			formTest = false ;
		}

	}
	if(controllo_approval_number==false)
	{
		msg += "Attenzione il numero di riconoscimento inserito è stato assegnato a un altro stabilimento" ;
		formTest = false ;
	}
	

	if (document.getElementById('lista_impianti').style.display=='' && document.getElementById('statoCorrente').value=='0')
	{

		size = parseInt(document.addAccount.size.value)	;

		if(document.getElementById("n_s").value=="0")
		{
		for (i=1;i<=size;i++)
		{

			
			
			
			if (document.getElementById('dateI_'+i).value == '')
			{
				msg += " - data inizio sottoattivita per linee produttive" ;
				formTest = false ;
				break ;
			}
			if (document.getElementById('tipoAutorizzazzione_'+i).value == '-1')
			{
				msg += " - Tipo autorizzazzione per linee produttive" ;
				formTest = false ;
				break ;
			}
		}
		}
	}

	if (formTest==false)
	{
		alert(msg);
		return false ;
	}
	document.addAccount.submit();
}

	
</script>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ include file="../initPage.jsp"%>

<%
Integer tipoModifica = (Integer)request.getAttribute("tipoModifica"); %>

<body >

<table class="trails" cellspacing="0">
<tr>
<td width="100%">
<a href="Stabilimenti.do">Stabilimenti</a> >Aggiungi Stabilimento 
</td>
</tr>
</table>
<br/>

<form name = "addAccount" method="post" action="Stabilimenti.do?command=Update">
<input type = "hidden" name = "condizionato" value = "<%=request.getAttribute("Condizionato") %>"/>
<input type = "hidden" name = "condizionato" value = "<%=request.getAttribute("Revoca") %>"/>
<input type = "hidden" name = "orgId" value = "<%=OrgDetails.getOrgId() %>">
<input type = "submit" value="Salva" onclick="return checkForm()">
<input type = "hidden" name = "tipoModifica" value = "<%=tipoModifica %>">
<div id = "informazioni_principali" >
<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
			<th colspan="2"><strong>Informazioni Principali</strong> </th>
		</tr>

				<tr class="containerBody">
					<td nowrap class="formLabel">ASL</td>
					<td>
					<%if(OrgDetails.getSiteId()<=0) { %>
					<%=SiteList.getHtmlSelect("siteId",OrgDetails.getSiteId())%> 
					
					<%} else {
						%>
							<%=SiteList.getSelectedValue(OrgDetails.getSiteId())%> 
						<input type="hidden" name="siteId" value="<%=OrgDetails.getSiteId()%>">
						<%
						
					} %>
					
					</td>
				</tr>
				<tr class="containerBody">
				<td nowrap class="formLabel" name="orgname1" id="orgname1"> Ragione Sociale
				</td>
				<td>
				
				<input type = "text" name = "name"  value="<%=OrgDetails.getName() %>" id = "name" maxlength="85" size="50" style="background: none repeat scroll 0% 0% rgb(255, 255, 255)";>
				</td>
			</tr>
		
			<tr class="containerBody">
				<td nowrap class="formLabel">Partita IVA
				</td>
				
				<td><input type = "text" name = "piva"   value="<%=OrgDetails.getPartitaIva() %>" id = "piva" maxlength="11" style="background: none repeat scroll 0% 0% rgb(255, 255, 255)";></td>
			</tr>

			<tr class="containerBody">
				<td nowrap class="formLabel">Codice Fiscale
				</td>
				<td><input type = "text" name = "cf_stabilimento" id = "cf_stabilimento" style="background: none repeat scroll 0% 0% rgb(255, 255, 255)";></td>
			</tr>
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Data presentazione istanza</dhv:label>
				</td>
				<td>
						
						<input type="text" name="dateI" size="10" value="<%=OrgDetails.getDate2String() %>" />&nbsp;
	  			</td>
			</tr>
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Stato Stabilimento</dhv:label>
				</td>
				<td>
					<%=statoLab.getHtmlSelect("statoLab",OrgDetails.getStatoLab())%>&nbsp;	  		
				</td>
			</tr>
		
			
			
			
		</table>
		
	<br/>
	<br/>
	<br>
		<br>
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Titolare o Legale Rappresentante</dhv:label></strong>
      
    </th>
  </tr>
  
  <tr >
    <td class="formLabel" nowrap>
      Codice Fiscale
    </td>
    <td>
      <input type="text" size="30" maxlength="16" name="codiceFiscaleRappresentante" value="<%= toHtmlValue(OrgDetails.getCodiceFiscaleRappresentante()) %>"><font color="red">*</font>
    </td>
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      Nome
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="nomeRappresentante" value="<%= toHtmlValue(OrgDetails.getNomeRappresentante()) %>"><font color="red">*</font>
    </td>
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      Cognome
    </td>
    <td>
   
      <input type="text" size="30" maxlength="50" name="cognomeRappresentante" value="<%= toHtmlValue(OrgDetails.getCognomeRappresentante()) %>"><font color="red">*</font>
    </td>
  </tr>
  <tr>
      <td nowrap class="formLabel">
        <dhv:label name="">Data Nascita</dhv:label>
      </td>
      <td>
     
     
     <input readonly type="text" id="dataNascitaRappresentante" name="dataNascitaRappresentante" size="10" />
		<a href="#" onClick="cal19.select(document.forms[0].dataNascitaRappresentante,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle">
		</a>

      
        <%= showAttribute(request, "alertDateError") %>
        <%= showWarningAttribute(request, "alertDateWarning") %>
        
      </td>
    </tr>
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Comune di nascita</dhv:label>
    </td>
    <td>
      
      <input type="text" size="30" maxlength="50" name="luogoNascitaRappresentante" value="<%= toHtmlValue(OrgDetails.getLuogoNascitaRappresentante()) %>">
    </td>
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Comune di Residenza</dhv:label>
    </td>
    <td>
    
      <input type="text" size="30" maxlength="50" name="city_legale_rapp" value="<%= toHtmlValue(OrgDetails.getCity_legale_rapp()) %>">
    </td>
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Provincia</dhv:label>
    </td>
    <td>
   
      <input type="text" size="30" maxlength="50" name="prov_legale_rapp" value="<%= toHtmlValue(OrgDetails.getProv_legale_rapp()) %>">
    </td>
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Indirizzo</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="address_legale_rapp" value="<%= toHtmlValue(OrgDetails.getAddress_legale_rapp()) %>">
    </td>
  </tr>
  
  
  
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Email</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="emailRappresentante" value="<%= toHtmlValue(OrgDetails.getEmailRappresentante()) %>">
    </td>
    
  </tr>
  
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Telefono</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="telefonoRappresentante" value="<%= toHtmlValue(OrgDetails.getTelefonoRappresentante()) %>">
    </td>
    
  </tr>
  
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Fax</dhv:label>
    </td>
    <td>
    
      <input type="text" size="30" maxlength="50" name="fax" value="<%= toHtmlValue(OrgDetails.getFax()) %>">
    </td>
    
  </tr>
  
</table>
<br>
		
		<br><br>
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="">Sede Legale/Domicilio Fiscale</dhv:label></strong>
	  <input type="hidden" name="address1type" value="1">
	  </th>
  </tr>
 <tr>
	<td nowrap class="formLabel" name="province1" id="prov2">
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td> 
    <td> 
	  <input type="hidden" name="address1id" value="<%= OrgDetails.getAddress("1").getId()%>">
    <input type ="text" name ="address1city" id = "prov12" value = "<%=OrgDetails.getAddress("1").getCity() %>"><font color = "red">*</font>

	
	</td>
  	</tr>	
  	  	
  <tr>
    <td nowrap class="formLabel" id="indirizzo1">
      Indirizzo
    </td>
    <td>
      <input type="text" size="40" name="address1line1" maxlength="80" id="indirizzo12" value="<%=OrgDetails.getAddress("1").getStreetAddressLine1() %>"><font color = "red">*</font>
    </td>
  </tr>
  
   <tr>
    <td nowrap class="formLabel" id="nazione">
     Nazione
    </td>
    <td>
      <input type="text" size="40" name="nazione" maxlength="80" id="nazione" value="ITALIA">
    </td>
  </tr>
  
  
  <tr>
    <td nowrap class="formLabel" id="cap1">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="address1zip" maxlength="5" value = "<%=OrgDetails.getAddress("1").getZip() %>" id="cap">
    </td>
  </tr>  
  	 
  	<tr>
    <td nowrap class="formLabel" id="stateProv2" >
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
          <% if (User.getSiteId() == 202) { %>
          <input type="text" readonly="readonly" size="28" name="address1state" maxlength="80" value="BN">          
          <%}%>
          <% if (User.getSiteId() == 201) { %>
          <input type="text" readonly="readonly" size="28" name="address1state" maxlength="80" value="AV">
          <%}%>
          <% if (User.getSiteId() == 203) { %>
          <input type="text" readonly="readonly" size="28" name="address1state" maxlength="80" value="CE">
          <%}%>
          <% if (User.getSiteId() == 204 || User.getSiteId() == 205 || User.getSiteId() == 206 ) { %>
          <input type="text" readonly="readonly" size="28" name="address1state" maxlength="80" value="NA">
          <%}%>
          <% if (User.getSiteId() == 207) { %>
          <input type="text" readonly="readonly" size="28" name="address1state" maxlength="80" value="SA">
          <%}%>
          <% if (User.getSiteId() == -1) { %>
          <input type="text"  size="28" name="address1state" maxlength="80" value="">
          <%}%>
    </td>
  </tr>

  
  <tr class="containerBody">
    <td class="formLabel" nowrap id="latitude2"><dhv:label name="requestor.address.latitude">Latitude</dhv:label></td>
    <td>
       	<%-- <input type="text" id="address2longitude" name="address2longitude" size="30" value="<%=AddressSedeOperativa.getLongitude() %>" id="longitude12">--%>
    	<input type="text" id="address1latitude" readonly="readonly" name="address1latitude" size="30" value="<%=OrgDetails.getAddress("1").getLatitude() %>">
 	
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap id="longitude2"><dhv:label name="requestor.address.longitude">Longitude</dhv:label></td>
    <td>
    	<%-- <input type="text" id="address2longitude" name="address2longitude" size="30" value="<%=AddressSedeOperativa.getLongitude() %>" id="longitude12">--%>
    	<input type="text" id="address1longitude"  readonly="readonly" name="address1longitude" size="30" value="<%=OrgDetails.getAddress("1").getLongitude() %>">
    </td>
    </tr>
    <tr style="display: block">
    <td colspan="2">
    <input id="coord1button" type="button" value="Calcola Coordinate"
    onclick="javascript:showCoordinate(document.getElementById('indirizzo12').value, document.forms['addAccount'].address1city.value,document.forms['addAccount'].address1state.value, document.forms['addAccount'].address1zip.value, document.forms['addAccount'].address1latitude, document.forms['addAccount'].address1longitude);" /> 
    </td>
    </tr>
   
</table>
	
	<br/>
	<br/>
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="">Sede Operativa</dhv:label></strong>
	  <input type="hidden" name="address2id" value="<%= OrgDetails.getAddress("5").getId()%>">
	  </th>
  </tr>
 <tr>
	<td nowrap class="formLabel"  id="prov12">
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td> 
    <td > 
     <input type = "hidden" name = "address2type" value = "<%=OrgDetails.getAddress("5").getType() %>">
	<select  name="address2city" id="prov2" onchange="popolaComuni()">
	<option value="-1">Nessuna Selezione</option>
            
	 <%
	 
	 
                Vector v4 = OrgDetails.getComuni2();
	 			Enumeration e4=v4.elements();
                while (e4.hasMoreElements()) {
                	String prov4=e4.nextElement().toString();
                	
        %>
                <option value="<%=prov4%>" <%if(prov4.equals(OrgDetails.getAddress("5").getCity())){%> selected="selected"<%} %> ><%= prov4 %></option>	
              <%}%>
		
	</select> 
	<font color="red">*</font>
	</td>
  	</tr>	
  	  	
  <tr>
    <td nowrap class="formLabel" id="indirizzo1">
      Indirizzo
    </td>
    <td>
      <input type="text" size="40" name="address2line1" maxlength="80" id="indirizzo22" value="<%=OrgDetails.getAddress("5").getStreetAddressLine1() %>">
      	<font color="red">*</font>
    </td>
  </tr>
  
  
  <tr>
    <td nowrap class="formLabel" id="cap1">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
      
    </td>
    <td>
      <input type="text" size="28" name="address2zip" maxlength="5" value = "<%=OrgDetails.getAddress("5").getZip() %>" id="cap">
      	<font color="red">*</font>
    </td>
  </tr>  
  	 
  	<tr>
    <td nowrap class="formLabel" id="stateProv2" >
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
          <% if (User.getSiteId() == 202) { %>
          <input type="text" readonly="readonly" size="28" name="address2state" maxlength="80" value="BN">          
          <%}%>
          <% if (User.getSiteId() == 201) { %>
          <input type="text" readonly="readonly" size="28" name="address2state" maxlength="80" value="AV">
          <%}%>
          <% if (User.getSiteId() == 203) { %>
          <input type="text" readonly="readonly" size="28" name="address2state" maxlength="80" value="CE">
          <%}%>
          <% if (User.getSiteId() == 204 || User.getSiteId() == 205 || User.getSiteId() == 206 ) { %>
          <input type="text" readonly="readonly" size="28" name="address2state" maxlength="80" value="NA">
          <%}%>
          <% if (User.getSiteId() == 207) { %>
          <input type="text" readonly="readonly" size="28" name="address2state" maxlength="80" value="SA">
          <%}%>
          <% if (User.getSiteId() == -1) { %>
          <input type="text"  size="28" name="address2state" maxlength="80" value="">
          <%}%>
    </td>
  </tr>

  
  <tr class="containerBody">
    <td class="formLabel" nowrap id="latitude2"><dhv:label name="requestor.address.latitude">Latitude</dhv:label></td>
   
    <td>
       	<%-- <input type="text" id="address2longitude" name="address2longitude" size="30" value="<%=AddressSedeOperativa.getLongitude() %>" id="longitude12">--%>
    	<input type="text" id="address2latitude"   name="address2latitude" size="30" value="<%=OrgDetails.getAddress("5").getLatitude() %>">
 		<font color="red">*</font>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap id="longitude2"><dhv:label name="requestor.address.longitude">Longitude</dhv:label></td>
    <td>
    	<%-- <input type="text" id="address2longitude" name="address2longitude" size="30" value="<%=AddressSedeOperativa.getLongitude() %>" id="longitude12">--%>
    	<input type="text" id="address2longitude"   name="address2longitude" size="30" value="<%=OrgDetails.getAddress("5").getLongitude() %>">
    		<font color="red">*</font>
    </td>
    </tr>
    <tr style="display: block">
    <td colspan="2">
    <input id="coord1button" type="button" value="Calcola Coordinate"
    onclick="javascript:showCoordinate(document.getElementById('indirizzo22').value, document.forms['addAccount'].address2city.value,document.forms['addAccount'].address2state.value, document.forms['addAccount'].address2zip.value, document.forms['addAccount'].address2latitude, document.forms['addAccount'].address2longitude);" /> 
    </td>
    </tr>
   
</table>
	
	<br/>
	<br/>
	</div>
	
	<br/>
	<br/>
	<div id = "lista_impianti" >
	<a href = "javascript:clonaNelPadre()" > Aggiungi Attività</a>
	<input type = "hidden" id = "size" name = "size" value = "<%=elencoSottoAttivita.size() %>">
	<input type = "hidden" id = "elementi" name = "elementi" value = "<%=elencoSottoAttivita.size() %>">
	<%
				int statoInDomanda = 3 ;
			%>
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" id = "attivita">
  			<tr>
    			<th colspan="8"><strong>Linee Produttive</strong></th>
  			</tr>
			
			<tr class="formLabel">
				<td width ="15%" align="left">Categoria/Sezione</td>
				<td width ="10%" align="left">Impianto/Attivit&agrave;</td>
				<td width ="15%" align="left">Prodotti</td>
				<td width ="3%" align="left" >Produzione con Riti Religiosi</td>	
				<td width ="3%" align="left" >Prodotti Imballati</td>	
				<td width ="3%" align="left" >Prodotti non Imballati</td>		
			</tr>
			
				<%
				int i = 1 ;
				Iterator iElencoAttivita = elencoSottoAttivita.iterator();
				
					while (iElencoAttivita.hasNext()) {
						SottoAttivita thisAttivita = (SottoAttivita) iElencoAttivita.next();
			%>    
			 
		
			<input type = "hidden" name = "statoLab_<%=i %>" value = "<%=thisAttivita.getStato_attivita() %>">
			
			<input type = "hidden" name = "id_<%=i %>" value = "<%=thisAttivita.getId() %>">
    		<tr class="containerBody">
      			<td>
      				
      				<select name = "categoria_<%=i %>" id = "categoria_<%=i %>" onchange="setComboImpiantiProdotti(this.value,'impianto_<%=i %>','prodotti_<%=i %>')">
					<%
					Iterator	it = categoriaList.iterator();
					while(it.hasNext())
					{
						LookupElement el = (LookupElement)it.next();
						%>
						
						<option value = "<%=el.getCode() %>" <%if(el.getCode()==thisAttivita.getCodice_sezione()){%>selected="selected" <%} %> title="<%=el.getDescription().toUpperCase() %>"><%=el.getDescription() %></option>
						<%
					}
					%></select> 
      			</td>
      			
      			<td>
      			
      			<select name = "impianto_<%=i %>"  id = "impianto_<%=i %>" >
					<%
					 it = impianto.iterator();
					while(it.hasNext())
					{
						LookupElement el = (LookupElement)it.next();
						%>
						
						<option value = "<%=el.getCode() %>" <%if (el.getCode()==thisAttivita.getCodice_impianto()){%> selected="selected"<%} %> title="<%=el.getDescription().toUpperCase() %>"><%=el.getDescription() %></option>
						<%
					}
					%></select> &nbsp;
      				
      				
      			</td>
      			<td>
      				<%
      				LookupList valSel = new LookupList();
      				LookupProdotti.setMultiple(true);
      				LookupProdotti.setSelectSize(7);
      					for(Integer idProdotto : thisAttivita.getListaProdotti())
      					{
      						LookupElement el = new LookupElement();
      						el.setCode(idProdotto);
      						el.setDescription(LookupProdotti.getSelectedValue( idProdotto));
      						valSel.add(el);
      					
      					
      					}
      					
      					%>
      					<%=LookupProdotti.getHtmlSelect("prodotti_"+i,valSel) %>
      			</td>
      			
      		
      				
      			<td>
      				<input type = "checkbox" name = "riti_religiosi_<%=i %>" <%if(thisAttivita.isRiti_religiosi()) {%>checked="checked"<% }%> > &nbsp;
      			
      			</td>
      			
      			<td>
      				<input type = "checkbox" name = "imballata_<%=i %>" <%if(thisAttivita.getImballata()==1) {%>checked="checked"<% }%> > &nbsp;
      			</td>
      			
      				<td>
      				<input type = "checkbox" name = "non_imballata_<%=i %>" <%if(thisAttivita.getNon_imballata()==1) {%>checked="checked"<% }%> > &nbsp;
      			</td>
      			<td>
      				<input type="text"  size="10" id="dateI_<%=i %>" name="dateI_<%=i %>" value = "<%=thisAttivita.getData_inizio_attivitaAsString()%>" >
      			    <a href = "javascript:popCalendar('addAccount','dateI_<%=i %>','it','IT','Europe/Berlin');">
      			    <img align="absmiddle" border="0" src="images/icons/stock_form-date-field-16.gif"></a>
      			     <font color = 'red'>*</font>
      				</td>
      			
    		</tr>
			<%
			i++;
					}
				
			%>
			
			
    		<tr class="containerBody" id = "row" style="display: none">
    		
    		<td>
	  		<select name = "categoria_0" id = "categoria_0" onchange="setComboImpiantiProdotti(this.value,'impianto_0','prodotti_0')">
					<%
					Iterator	it = categoriaList.iterator();
					while(it.hasNext())
					{
						LookupElement el = (LookupElement)it.next();
						%>
						
						<option value = "<%=el.getCode() %>" title="<%=el.getDescription().toUpperCase() %>"><%=el.getDescription() %></option>
						<%
					}
					%></select> &nbsp;
				</td>
      			
				<td>
				<select name = "impianto_0"  id = "impianto_0" >
					<%
					 it = impianto.iterator();
					while(it.hasNext())
					{
						LookupElement el = (LookupElement)it.next();
						%>
						
						<option value = "<%=el.getCode() %>" title="<%=el.getDescription().toUpperCase() %>"><%=el.getDescription() %></option>
						<%
					}
					%></select> &nbsp;
				</td>
		   		
				<td>
				
	  			<select name = "prodotti_0"  id = "impianto_0" multiple="multiple" >
					<%
					it = LookupProdotti.iterator();
					while(it.hasNext())
					{
						LookupElement el = (LookupElement)it.next();
						%>
						
						<option value = "<%=el.getCode() %>" <%if(el.getCode()==-1){ %>selected="selected"<%} %> title="<%=el.getDescription().toUpperCase() %>"><%=el.getDescription() %></option>
						<%
					}
					%></select> &nbsp;
				</td>
				<td style="display: none">
				<%=statoLab.getSelectedValue(statoInDomanda)%>
					<input type = "hidden" name = "statoLab_0" value = "<%=statoInDomanda %>"	>
	  			
				</td>
			
				<td >
	  				<input type="checkbox" name = "riti_religiosi_0" value = "SI">
				</td>
				<td >
	  				<input type="checkbox" name = "imballata_0" value = "SI">
				</td>
				<td >
	  				<input type="checkbox" name = "non_imballata_0" value = "SI">
				</td>
				
			
			 
    		</tr>
		
		</table>
	
	
		</div>
		<br><br>
		
		<div id = "esito_controlli" style="display: none">
		
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="">Completamento Istruttoria</dhv:label></strong>
	  </th>
  </tr>
  <tr >
      <td nowrap class="formLabel">
        Data Completamento
      </td>
      <td>
              	<zeroio:dateSelect form="addAccount" field="data1" timestamp="<%= "" %>" showTimeZone="false" /><font color="red">*</font>


      </td>
    </tr>
  
  <tr style="display: none">
	<td nowrap class="formLabel" name="nameMiddle" id="nameMiddle">
      <dhv:label name="requestor.requestor_add.Classificatio">Esito</dhv:label>
    </td> 
    <td> 
	<select name="nameMiddle">
			<option value=" "><dhv:label name="requestor.requestor_add.NoneSelected">None Selected</dhv:label></option>
            <option value="Favorevole">Favorevole</option>
			<option value="Non favorevole">Non favorevole</option>
			<option value="Favorevole con lievi non conformita'">Favorevole con lievi non conformita'</option>
    </select> 
	</td>
  	</tr>
  	 	<tr>
      <td name="date3" id="date3" nowrap class="formLabel">
        <dhv:label name="">Note</dhv:label>
      </td>
      <td>
      	<textarea rows="6" cols="40" name = "note"></textarea>
      </td>
    </tr>
						
	
    
  	<tr style="display: none">
      <td name="date3" id="date3" nowrap class="formLabel">
        <dhv:label name="">Data Esito</dhv:label>
      </td>
      <td>
      	<div id="data3">
        	<zeroio:dateSelect form="addAccount" field="data3" timestamp="<%= "" %>" showTimeZone="false" /><font color="red">*</font>
        </div>
      </td>
    </tr>
    <input type = "hidden" id = "statoCorrente" value = "<%=OrgDetails.getStatoIstruttoria() %>">
  <%
						int statoIstruttoria = OrgDetails.getStatoIstruttoria();
						
						
							%>
							<input type ="hidden" name = "nuovoStato" id = "n_s" value = "<%=statoIstruttoria %>"/>
							
							
						  	
  
  </table>
  </div>
</form></body>