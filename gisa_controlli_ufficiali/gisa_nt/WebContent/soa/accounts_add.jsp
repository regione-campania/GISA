<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="org.aspcfs.utils.web.LookupElement"%><jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" 	scope="request" />
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.soa.base.Organization" scope="request" />
<jsp:useBean id="impianto" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="statoLab" class="org.aspcfs.utils.web.LookupList"	scope="request" />
<jsp:useBean id="statoLabImp" class="org.aspcfs.utils.web.LookupList"	scope="request" />

<jsp:useBean id="tipoAutorizzazioneList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="LookupClassificazione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="LookupProdotti" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="categoriaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="categoria" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="tipoAutorizzazzione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>

<script type="text/javascript" src="dwr/util.js"></script>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>
<script>
var campoLat;
	var campoLong;
	var field_id_impianto ;
	function setComboImpianti(idCategoria,idField)
	{
			field_id_impianto = idField;
			
		   	PopolaCombo.getValoriComboImpiantiSoa(idCategoria,setValoriComboCallBack);
		   
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
				
			   	PopolaCombo.getValoriComboProdottiSoa(idCategoria,setComboProdottiCallBack);
			   
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
	  
	function checkForm(form)
	{
		formTest=true ;
		msg='';
		if(form.siteId.value=='-1')
		{
			msg+=" - Controllare di aver inserito ASL\n";
			formTest = false ;
		}
		
		if(form.name.value=='')
		{
			msg+=' - Controllare di aver inserito il nome Impresa\n';
			formTest = false ;
		}

		
		if(form.cf_stabilimento.value=='' && form.piva.value=='')
		{
			msg+=' - Controllare di aver inserito Codice Fiscale Stabilimento o Partita Iva\n';
			formTest = false ;
		}

		

		if(form.codiceFiscaleRappresentante.value=='')
		{
			msg+=' - Controllare di aver inserito il Cf Rappresentante\n';
			formTest = false ;
		}
		if(form.nomeRappresentante.value=='')
		{
			msg+=' - Controllare di aver inserito il Nome Rappresentante\n';
			formTest = false ;
		}
		if(form.cognomeRappresentante.value=='')
		{
			msg+=' - Controllare di aver inserito il Cognome Rappresentante\n';
			formTest = false ;
		}
		if(form.dataNascitaRappresentante.value=='')
		{
			msg+=' - Controllare di aver inserito la data Nascita Rappresentante\n';
			formTest = false ;
		}
		
		
		
		  
		  
		  
		if(form.dateI.value=='')
		{
			msg+=' - Controllare di aver inserito la data presentazione istanza\n';
			formTest = false ;
		}

		if(form.address2city.value=='-1')
		{
			msg+=' - Controllare di aver inserito il Comune per la sede operativa\n';
			formTest = false ;
		}
		if(form.address1city.value=='')
		{
			msg+=' - Controllare di aver inserito il Comune per la sede Legale\n';
			formTest = false ;
		}
		if(form.address1line1.value=='')
		{
			msg +=' - Controllare di aver inserito un indirizzo per la sede Legale\n';
			formTest = false ;
		}

		if(form.address2line1.value=='')
		{
			msg +=' - Controllare di aver inserito un indirizzo per la sede operativa\n';
			formTest = false ;
		}

		if(form.address2latitude.value=='')
		{
			msg+=' - Controllare di aver inserito la latitudine per la sede operativa\n';
			formTest = false ;
		}
		if(form.address2longitude.value=='')
		{
			msg +=' - Controllare di aver inserito la longitudine per la sede operativa\n';
			formTest = false ;
		}

		if(form.address2zip.value=='')
		{
			msg +=' - Controllare di aver inserito il cap per la sede operativa\n';
			formTest = false ;
		}
		
		numSottoAttivita = form.size.value ;


		if (parseInt(numSottoAttivita)==0)
		{
			msg +=' - Controllare di aver Inserito almeno una sottoattivita\n';
			formTest = false ;
		}
		else
		{
		for(i=1;i<=parseInt(numSottoAttivita);i++)
		{
			if(document.getElementById('impianto_'+i).value=='-1')
			{
				msg +=' - Controllare di aver Selezionato un impianto per la sottoattivita '+i+'\n';
				formTest = false ;
			}

			
			
			if(document.getElementById('categoria_'+i).value=='')
			{
				msg +=' - Controllare di aver Selezionato una categoria per la sottoattivita '+i+'\n';
				formTest = false ;
			}
	
		}
	}	

		if(formTest==false)
		{
			alert(msg);
		}
		return formTest;
		
	
	}
	function azzeraElementi()
	{
		document.getElementById("elementi").value = "0" ;
		document.getElementById("size").value = "0" ;
	}
</script>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ include file="../initPage.jsp"%>

<body onload="azzeraElementi()">

<table class="trails" cellspacing="0">
<tr>
<td width="100%">
<a href="Soa.do">Stabilimenti</a> >Aggiungi Stabilimento 
</td>
</tr>
</table>
<br/>

<form name = "addAccount" method="post" action="Soa.do?command=Insert">
<input type = "button" value="Inserisci" onclick="doCheck(document.addAccount)">

<input type = "hidden" name = "tipoSoa" value = "<%=OrgDetails.getTipoSoa() %>">
<br/>
<input type = "hidden" name = "tipo_istruttoria" value = "1">
<%
if(request.getAttribute("Inserito")!=null)
{
	out.println("<font color = 'red'>Attenzione! il sistema ha trovato uno stabilimento con la partita iva che si sta cercando di inserire. Contattare l'help Desk</font>");	
}
%>
<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
			<th colspan="2"><strong>Informazioni Principali</strong> </th>
		</tr>

		
	
		
			<dhv:evaluate if="<%= User.getSiteId() > 0 %>">
				<tr class="containerBody">
					<td nowrap class="formLabel">ASL</td>
					<td><%=SiteList.getSelectedValue(User.getSiteId())%> 
					<input type="hidden" name="siteId" value="<%=User.getSiteId()%>">
					</td>
				</tr>
			</dhv:evaluate>
			<dhv:evaluate if="<%= User.getSiteId() <= 0 %>">
			
			<tr class="containerBody">
					<td nowrap class="formLabel">ASL</td>
					<td><%=SiteList.getHtmlSelect("siteId",-1)%> 
					<font color = "red">*</font>
					</td>
				</tr>
				
			
		</dhv:evaluate>
	
		
			<tr class="containerBody">
				<td nowrap class="formLabel" name="orgname1" id="orgname1"> Impresa
				</td>
				<td>
				<input type = "text" name = "name" id = "name" maxlength="85" size="50" style="background: none repeat scroll 0% 0% rgb(255, 255, 255)";>
				<font color = "red">*</font>
				</td>
			</tr>
		
			<tr class="containerBody">
				<td nowrap class="formLabel">Partita IVA
				</td>
				<td><input type = "text" name = "piva" id = "piva" maxlength="11" style="background: none repeat scroll 0% 0% rgb(255, 255, 255)";>
				<font color = "red">*</font>
				</td>
			</tr>
			
			<tr class="containerBody">
				<td nowrap class="formLabel">Codice Fiscale
				</td>
				<td><input type = "text" name = "cf_stabilimento" id = "cf_stabilimento" style="background: none repeat scroll 0% 0% rgb(255, 255, 255)";>
				<font color = "red">*</font></td>
			</tr>
			
		

			
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Data presentazione istanza</dhv:label>
				</td>
				<td>
				
<input readonly type="text" id="dateI" name="dateI" size="10" />
		<a href="#" onClick="cal19.select(document.forms[0].dateI,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle">
		</a>
<font color="red">*</font>
	  		
				</td>
			</tr>
			<%
				int statoInDomanda = 3 ;
			%>
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Stato Stabilimento</dhv:label>
				</td>
				<td>
					<%=statoLab.getSelectedValue(statoInDomanda)%>
					<input type = "hidden" name = "statoLab" value = "<%=statoInDomanda %>"	>	
				</td>
			</tr>
			
			  <%
  
  if (OrgDetails.getTipoSoa()==2)
  {
	  %>
	  
	    <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Documentazione Controllata</dhv:label>
    </td>
    <td>
		<input type="checkbox"  name= "doc_controllata" checked="checked" readonly="readonly"   >    </td>
  </tr>
	  
	  
	  <%
	  
  }
  
  %>
			
		</table>
	<br/>
	<br/>
	
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
		</a><font color = "red">*</font>

      
        <%= showAttribute(request, "alertDateError") %>
        <%= showWarningAttribute(request, "alertDateWarning") %>
        
      </td>
    </tr>
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Comune di Nascita</dhv:label>
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
      <dhv:label name="">Domicilio digitale</dhv:label>
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
<br/>
<br/>
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
    
    <input type ="text" name ="address1city" id = "prov12"><font color = "red">*</font>

	
	</td>
  	</tr>	
  	  	
  <tr>
    <td nowrap class="formLabel" id="indirizzo1">
      Indirizzo
    </td>
    <td>
      <input type="text" size="40" name="address1line1" maxlength="80" id="indirizzo12" value=""><font color = "red">*</font>
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
      <input type="text" size="28" name="address1zip" maxlength="5" value = "" id="cap">
    </td>
  </tr>  
  	 
  	<tr>
    <td nowrap class="formLabel" id="stateProv2" >
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
          <% if (User.getSiteId() == 202) { %>
          <input type="text" size="28" name="address1state" maxlength="80" value="BN">          
          <%}%>
          <% if (User.getSiteId() == 201) { %>
          <input type="text" size="28" name="address1state" maxlength="80" value="AV">
          <%}%>
          <% if (User.getSiteId() == 203) { %>
          <input type="text" size="28" name="address1state" maxlength="80" value="CE">
          <%}%>
          <% if (User.getSiteId() == 204 || User.getSiteId() == 205 || User.getSiteId() == 206 ) { %>
          <input type="text" size="28" name="address1state" maxlength="80" value="NA">
          <%}%>
          <% if (User.getSiteId() == 207) { %>
          <input type="text" size="28" name="address1state" maxlength="80" value="SA">
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
    	<input type="text" id="address1latitude" readonly="readonly" name="address1latitude" size="30" value="">
 	
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap id="longitude2"><dhv:label name="requestor.address.longitude">Longitude</dhv:label></td>
    <td>
    	<%-- <input type="text" id="address2longitude" name="address2longitude" size="30" value="<%=AddressSedeOperativa.getLongitude() %>" id="longitude12">--%>
    	<input type="text" id="address1longitude"  readonly="readonly" name="address1longitude" size="30" value="">
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
	  <input type="hidden" name="address2type" value="5">
	  </th>
  </tr>
 <tr>
	<td nowrap class="formLabel"  id="prov12">
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td> 
    <td > 
	<select  name="address2city" id="prov2" onchange="popolaComuni()">
	<option value="-1">Nessuna Selezione</option>
            
	 <%
                Vector v4 = OrgDetails.getComuni2();
	 			Enumeration e4=v4.elements();
                while (e4.hasMoreElements()) {
                	String prov4=e4.nextElement().toString();
                	
        %>
                <option value="<%=prov4%>" ><%= prov4 %></option>	
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
      <input type="text" size="40" name="address2line1" maxlength="80" id="indirizzo22" value="">
      	<font color="red">*</font>
    </td>
  </tr>
  
  
  <tr>
    <td nowrap class="formLabel" id="cap1">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
      
    </td>
    <td>
      <input type="text" size="28" name="address2zip" maxlength="5" value = "" id="cap">
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
    	<input type="text" id="address2latitude"   name="address2latitude" size="30" value=""  readonly="readonly">
 		<font color="red">*</font>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap id="longitude2"><dhv:label name="requestor.address.longitude">Longitude</dhv:label></td>
    <td>
    	<%-- <input type="text" id="address2longitude" name="address2longitude" size="30" value="<%=AddressSedeOperativa.getLongitude() %>" id="longitude12">--%>
    	<input type="text" id="address2longitude"   name="address2longitude" size="30" value="" readonly="readonly">
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
	
		
		<br><br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" style="display: none">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="">Controllo della Notifica</dhv:label></strong>
	  </th>
  </tr>
  <tr>
      <td nowrap class="formLabel">
        Data Completamento
      </td>
      <td>
      
      	<input readonly type="text" id="date1" name="date1" size="10" />
        <a href="#" onClick="cal19.select(document.forms[0].date1,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>

      </td>
    </tr>
  
  <tr>
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
  	
  	<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Stato Istruttoria</dhv:label>
				</td>
				<td>
					<select name="statoIstruttoria">
						<option value = "1" >Preliminare</option>
						
					</select>	  		
				</td>
			</tr>
  	
   <tr>
      <td name="cin" id="cin" nowrap class="formLabel" valign="top">
        <dhv:label name="">Note relative alle non conformità</dhv:label>
      </td>
      <td>
       <TEXTAREA name="cin" ROWS="3" COLS="50"> </TEXTAREA> 
   	<%--	<input type="text" size="50" maxlength="80" name="nameSuffix"> --%>  
      </td>
    </tr>
    
  	<tr>
      <td name="date3" id="date3" nowrap class="formLabel">
        <dhv:label name="">Data di risoluzione della non conformita'</dhv:label>
      </td>
      <td>
      	<div id="data3">
        	<input readonly type="text" id="date3" name="date3" size="10" />
        <a href="#" onClick="cal19.select(document.forms[0].date3,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
        </div>
      </td>
    </tr>
  
  
  </table>

	
	<br/>
	<br/>
	<a href = "javascript:clonaNelPadre()" > Aggiungi Attività</a>
	<br/>
	<br/>
	<input type = "hidden" id = "size" name = "size" value = "0">
	<input type = "hidden" id = "elementi" name = "elementi" value = "0">
	
	
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" id = "attivita">
  			<tr>
    			<th colspan="8"><strong>Linee Produttive</strong></th>
  			</tr>
			
			<tr class="formLabel">
				<td width ="15%" align="left">Categoria/Sezione</td>
				<td width ="10%" align="left">Impianto/Attivit&agrave;</td>
				<td width ="15%" align="left">Prodotti</td>
				<td width ="10%" align="left">Descrizione Stato Attività</td>
				<td width ="3%" align="left" >Produzione con Riti Religiosi</td>	
				<td width ="3%" align="left" >Prodotti Imballati</td>	
				<td width ="3%" align="left" >Prodotti non Imballati</td>		
				
			</tr>
			
    		<tr class="containerBody" id = "row" style="display: none">
    		
    		<td>
	  		<select name = "categoria_0" id = "categoria_0" onchange="setComboImpiantiProdotti(this.value,'impianto_0','prodotti_0')">
					<%
					Iterator	it = categoria.iterator();
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
				
				<td>
				<%=statoLabImp.getSelectedValue(statoInDomanda)%>
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
			<!-- <td>
      				<input type="text"  size="10" id="dateI_0" name="dateI_0" >
      			    <a href = "javascript:popCalendar('addAccount','dateI_0','it','IT','Europe/Berlin');">
      			    <img align="absmiddle" border="0" src="images/icons/stock_form-date-field-16.gif"></a>
      			    
      				</td> -->
      			
    		</tr>
		
		</table>
		
		<br><br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" style="display: none">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="">Controllo della Notifica</dhv:label></strong>
	  </th>
  </tr>
  <tr>
      <td nowrap class="formLabel">
        Data Completamento
      </td>
      <td>
      
      	<input readonly type="text" id="date1" name="date1" size="10" />
        <a href="#" onClick="cal19.select(document.forms[0].date1,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>

      </td>
    </tr>
  
  <tr>
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
  	
  	<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Stato Istruttoria</dhv:label>
				</td>
				<td>
					<select name="statoIstruttoria">
						<option value = "2" >Preliminare</option>
						
					</select>	  		
				</td>
			</tr>
  	
   <tr>
      <td name="cin" id="cin" nowrap class="formLabel" valign="top">
        <dhv:label name="">Note relative alle non conformità</dhv:label>
      </td>
      <td>
       <TEXTAREA name="cin" ROWS="3" COLS="50"> </TEXTAREA> 
   	<%--	<input type="text" size="50" maxlength="80" name="nameSuffix"> --%>  
      </td>
    </tr>
    
  	<tr>
      <td name="date3" id="date3" nowrap class="formLabel">
        <dhv:label name="">Data di risoluzione della non conformita'</dhv:label>
      </td>
      <td>
      	<div id="data3">
        	<input readonly type="text" id="date3" name="date3" size="10" />
        <a href="#" onClick="cal19.select(document.forms[0].date3,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
        </div>
      </td>
    </tr>
  
  </table>
  
  <input type = "button" value="Inserisci" onclick="doCheck(document.addAccount)">
  
</form>