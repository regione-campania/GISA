<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>


<jsp:useBean id="ApicolturaSottospecie" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ApicolturaModalita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ApicolturaClassificazione" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="TipoAttivitaApi" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="NazioniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Operatore" class="ext.aspcfs.modules.apiari.base.Operatore" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<%@page import="ext.aspcfs.modules.apicolture.actions.CfUtil"%>
<jsp:useBean id="ErroreCoordinate" class="java.lang.String" scope="request"/>
<jsp:useBean id="Stabilimento" class="ext.aspcfs.modules.apiari.base.Stabilimento" scope="request" />


<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/opu.css" />
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/suapUtil.js"></script>

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/gestoreCodiceFiscale.js"></script>
<SCRIPT src="javascript/apiari.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>
<%@ include file="../initPage.jsp"%>


<%if (ErroreCoordinate!=null && !ErroreCoordinate.equals("")) {%>
<script>
alert("<%=ErroreCoordinate%>");
</script>
<%} %>

<script>


function controllaCoordinate()
{
	
	var lat = form.latitudine.value ;
	var longi = form.longitudine.value;
	
	var comune = form.searchcodeIdComuneinput.value ;
	
	var esito = false ;
	
	 $.ajax({
	        type: 'GET',
	        dataType: "json",
	        async:false,
	        url: "http://maps.googleapis.com/maps/api/geocode/json?latlng="+lat+","+longi+"&sensor=false",
	        data: {},
	        success: function(data) {
	        	
	            $('#city').html(data);
	            $.each( data['results'],function(i, val) {
	                $.each( val['address_components'],function(i, val) {
	                	
	                    if (val['types'] == "locality, political") {
	                    	
	                        if (val['long_name']!="") {
	                        
	                        	if (val['long_name'] ==comune)
	                        		{
	                        		esito = true;
	                        		$("#coordinateError").html("");
	                        }
	                        	else
	                        		{
	                        		esito =  false ;
	                        	$("#coordinateError").html("<font color='red'>Attenzione ! Le coordinate dell'indirizzo immesso fanno riferimento al comune di : "+val['long_name']+"</font>");
	                        		}
	                        }
	                        else {
	                        	esito =  false ;
	                        }
	                       
	                    }
	                });
	            });
	           
	        },
	        error: function () { console.log('error'); } 
	    }); 
	return esito;
}

function checkForm()
{
	
	
	 	formTest = true;
	    message = "";
	    alertMessage = "";
	   
	    form = document.addstabilimento ;
	    
	    
	    
	    if (form.idApicolturaClassificazione.value == "-1"){
	        message += "- Classificazione richiesto\r\n";
	        formTest = false;
	     }
	    
	    if (form.codFiscaleSoggettoinput.value == ""){
	        message += "- Dati Detentore richiesti\r\n";
	        formTest = false;
	     }
	    
	    
	    if (form.idApicolturaSottospecie.value == "-1"){
	        message += "- Sottospecie richiesto\r\n";
	        formTest = false;
	     }
	    
	    if (form.idApicolturaModalita.value == "-1"){
	        message += "- Modalita richiesto\r\n";
	        formTest = false;
	     }
	    
	    if (form.dataApertura.value == ""){
	        message += "- Data Apertura richiesto\r\n";
	        formTest = false;
	     }
	    
	    if (form.numAlveari.value == ""){
	        message += "- Num alverai richiesto\r\n";
	        formTest = false;
	     }
	    if (form.numSciami.value == ""){
	        message += "- Num Sciami/Nuclei richiesto\r\n";
	        formTest = false;
	     }
	    
	    if (form.searchcodeIdComuneinput.value == ""){
	        message += "- Comune Ubicazione richiesto\r\n";
	        formTest = false;
	     }
	    if (!document.getElementById('aslRomaBdn').disabled && document.getElementById('aslRomaBdn').value==''){
	        message += "- Asl richiesta\r\n";
	        formTest = false;
	     }
	    if (form.viaTesto.value == ""){
	        message += "- Indirizzo Ubicazione richiesto\r\n";
	        formTest = false;
	     }
	    
	    if (form.latitudine.value == ""){
	        message += "- Latitudine richiesto\r\n";
	        formTest = false;
	     }
	    if (form.longitudine.value == ""){
	        message += "- Longitudine richiesto\r\n";
	        formTest = false;
	     }
	    if (form.telefono1!=null && form.telefono1.value == ""){
	        message += "- Telefono fisso richiesto\r\n";
	        formTest = false;
	     }
	    
	    var esitoControlloCoordinate = controllaCoordinate();
	    esitoControlloCoordinate = true;
	    
	    if (esitoControlloCoordinate==false)
	    {
	        message += "- Le coordinate dell'indirizzo inserito non corrispondono al comune immesso!\r\n";
	        formTest = false;
	     }
	    
	    
	    if (formTest == false) {
	        alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
	        return false;
	      } else {
	      
	        loadModalWindow();
	        return true;
	      }
		
}
	
	 $(function () {
		 $( "#dialogProprietario" ).dialog({
		    	autoOpen: false,
		        resizable: false,
		        closeOnEscape: false,
		       	title:"INSERIMENTO ANAGRAFICA PERSONA",
		        width:850,
		        height:500,
		        draggable: false,
		        modal: true,
		        buttons:{
		        	 "Salva": function() {$("#addpersona").submit(); } ,
		        	 "Esci" : function() { $(this).dialog("close");}
		        	
		        },
		        show: {
		            effect: "blind",
		            duration: 1000
		        },
		        hide: {
		            effect: "explode",
		            duration: 1000
		        }
		       
		    }).prev(".ui-dialog-titlebar").css("background","#bdcfff");
		 
	   
	});         
	
	
          
/*AUTOCOMPLETAMENTO PER GLI INDIRIZZI*/          
$(function() {
  
    $( "#addressLegaleCity" ).combobox();
    
    $( "#searchcodeIdComune" ).combobox();
    $( "#codFiscaleSoggetto" ).combobox();
    
     

});
 

  
</script>


<%=showError(request, "ErrorValidazioneError") %>



<form  name="addstabilimento" action="ApicolturaApiari.do?command=Insert&auto-populate=true" method="POST" onsubmit="return checkForm()">

<input type="hidden" name="idOperatore" id="idOperatore" value="<%=Operatore.getIdOperatore()%>">
<!-- Serve opId quando il command Insert chiama AssegnaNumero che si aspetta opId -->
<input type="hidden" name="opId" id="opId" value="<%=Operatore.getIdOperatore()%>">
	
	<fieldset>
		<legend><b>ANAGRAFICA APIARI</b></legend>
		<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		 <tr>
    <th colspan="2">
      <strong>INSERIMENTO UBICAZIONE APIARIO</strong>
    </th>
  </tr>

		
		<tr>
			<td nowrap class="formLabel">Denominazione</td>
			<td>
			<input type="hidden" name = "idOperatore" value="<%=Operatore.getIdOperatore() %>">
			<%=Operatore.getRagioneSociale() %></td>
		</tr>
		
		
			<tr>
				<td class="formLabel" nowrap>Codice Fiscale Proprietario</td>
				<td>
				<%=Operatore.getRappLegale().getCodFiscale() %>
					
				</td>
			<input type='hidden' id="cod_fiscale_propr" value="<%=Operatore.getRappLegale().getCodFiscale()%>"/>
			
			</tr>
			
			<tr>
				<td nowrap class="formLabel">Proprietario (cognome e nome) </td>
				<td>
				<%=Operatore.getRappLegale().getCognome() +" "+ Operatore.getRappLegale().getNome() %>
				</td>
				<input type='hidden' id='cognome_nome' value="<%=Operatore.getRappLegale().getCognome() +" "+ Operatore.getRappLegale().getNome() %>"/>
			</tr>
			
			
			<tr>
				<td class="formLabel" nowrap>Codice Fiscale Detentore</td>
				<td>
				
				<select name="codFiscaleSoggetto" id="codFiscaleSoggetto" class="required">
				
				<%if (Stabilimento.getDetentore()!=null){ %>
				<option value="<%=Stabilimento.getDetentore().getCodFiscale()%>"><%=Stabilimento.getDetentore().getCodFiscale() %></option>
				<%} else { %>
				<option value=""></option>
				<%} %>
			</select>
					&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="copia_proprietario"onclick="copiaProprietario()">&nbsp;Copia proprietario</input>
				
				</td>
			</tr>
			
			<tr>
				<td nowrap class="formLabel">Detentore (cognome e nome) </td>
				<td><input type="text" size="70" id="nominativo" name="nome" class="required" value ="<%=toHtml(Stabilimento.getDetentore().getCognome()) %> <%=toHtml(Stabilimento.getDetentore().getNome()) %>" >
				<input type = "hidden" name = "idSoggettoFisico" id="idSoggettoFisico"  value = "" >
				<input type ="button" id="inserisci_persona" onclick="javascript: $( '#dialogProprietario' ).dialog('open')" value="Inserisci Persona">
				
				</td>

			</tr>
			
			<tr>
				<td class="formLabel" nowrap>Classificazione</td>
				<td>
				<%=ApicolturaClassificazione.getHtmlSelect("idApicolturaClassificazione", Stabilimento.getIdApicolturaClassificazione()) %>
				<font color = "red">*</font>
				<%=showError(request, "classificazioneError") %>
				</td>
			</tr>
			
			<tr>
				<td class="formLabel" nowrap>Sottospecie</td>
				<td>
				<%=ApicolturaSottospecie.getHtmlSelect("idApicolturaSottospecie", Stabilimento.getIdApicolturaSottospecie()) %>
				<font color = "red">*</font>
				<%=showError(request, "sottospecieError") %>
				</td>
			</tr>
			
			<tr>
				<td class="formLabel" nowrap>Modalita Allevamento</td>
				<td>
				<%=ApicolturaModalita.getHtmlSelect("idApicolturaModalita", Stabilimento.getIdApicolturaModalita()) %>
				<font color = "red">*</font>
				<%=showError(request, "modalitaError") %>
				</td>
			</tr>
			
			<tr>
				<td class="formLabel" nowrap>Data Apertura</td>
				<td>
				<input type="text" size="70" name="dataApertura"
					id="dataInizioAttivita" class="required" placeholder="dd/MM/YYYY" readonly="readonly" value="<%=toDateasString(Stabilimento.getDataApertura())%>">
					
					<a href="#" onClick="cal19.select(document.forms[0].dataInizioAttivita,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
					<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
				
					<font color = "red">*</font>
					<%=showError(request, "dataAperturaError") %>
					<%=showError(request, "dataInizioError") %>
				</td>
			</tr>
			
			
	</table>
	
	</fieldset>
	<fieldset>
		<legend><b>DATI CENSIMENTO</b></legend>
		<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		
   
		<tr>
			<td nowrap class="formLabel">Numero Alveari</td>
			<td>
			<input type = "text" name = "numAlveari" id = "numAlveari" style="width: 50px;" value="<%=Stabilimento.getNumAlveari() %>">
			<font color = "red">*</font>
			</td>
		</tr>
		<tr>
			<td nowrap class="formLabel">Numero Sciami / Nuclei</td>
			
			<td>
			<input type = "text" name = "numSciami" id = "numSciami" style="width: 50px;" value="<%=Stabilimento.getNumSciami() %>">
			<font color = "red">*</font>
			</td>
		
		</tr>
		
		<tr>
			<td nowrap class="formLabel">Numero Pacchi d'Api</td>
			
			<td>
			<input type = "text" name = "numPacchi" id = "numPacchi" style="width: 50px;" value="<%=Stabilimento.getNumPacchi() %>">
			
			</td>
		
		</tr>
		<tr>
			<td nowrap class="formLabel">Numero Api Regine</td>
			
			<td>
			<input type = "text" name = "numRegine" id = "numRegine" style="width: 50px;" value="<%=Stabilimento.getNumRegine() %>">
			
			</td>
		
		</tr>
		</table>
	
	</fieldset>
	<fieldset>
		<legend><b>DATI UBICAZIONE</b></legend>
		<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		
   <tr>
		<td nowrap class="formLabel">Comune</td>
		<td> <select name="searchcodeIdComune" id="searchcodeIdComune" class="required">
				
				<%if (Stabilimento.getSedeOperativa()!=null){ %>
				<option value="<%=Stabilimento.getSedeOperativa().getComune()%>"><%=Stabilimento.getSedeOperativa().getDescrizioneComune() %></option>
				<%} else { %>
				<option value=""></option>
				<%} %>
			</select>
			<input type = "hidden" id = "inRegione" value= "no">
			<font color = "red">*</font>
					<%=showError(request, "comuneError") %>
			<input type="hidden" name="searchcodeIdComuneTesto" id="searchcodeIdComuneTesto" value=""/>
			
		</td>
	</tr>
	
	<tr>
		<td nowrap class="formLabel">ASL</td>
		<td>
			<select name="aslRomaBdn" id="aslRomaBdn" class="required" 
<%
				if(Stabilimento.getSedeOperativa()==null || !Stabilimento.getSedeOperativa().getDescrizioneComune().equalsIgnoreCase("roma"))
				{
%>
					disabled="disabled"
<%
				}
%>
			>
				<option value="">&lt;--Selezionare asl --&gt;</option>
				<option value="O201">ROMA 1</option>
				<option value="O202">ROMA 2</option>
				<option value="O203">ROMA 3</option>
			</select>
			<font color = "red">* (solo per comune Roma)</font>
		</td>
	</tr>
	
	<tr>
		<td nowrap class="formLabel">
			Cap
		</td>
		<td>
			<input type="text" size="70" name="presso" id ="presso"  style="width: 50px;" value="<%=Stabilimento.getSedeOperativa().getCap()%>">
		</td>
	</tr>
	
	<tr id ="searchcodeIdprovinciaTR">

		<td nowrap class="formLabel">
			Sigla Provincia
		</td>
		<td>
			
			<input type="text" readonly="readonly" required="required" name="searchcodeIdprovinciaTesto" id="searchcodeIdprovinciaTesto" value="<%=Stabilimento.getSedeOperativa().getDescrizione_provincia()%>"/>
			<input type="hidden"  required="required" name="searchcodeIdprovincia" id="searchcodeIdprovincia" value="<%=Stabilimento.getSedeOperativa().getIdProvincia()%>"/>
			
		</td>
	</tr>
	
	<tr>
		<td nowrap class="formLabel">
			Indirizzo
		</td>
		<td>
			
			<input type="text" name="viaTesto" id="viaTesto" value="<%=Stabilimento.getSedeOperativa().getVia()%>" maxlength="50"/>
				<font color = "red">*</font>
					<%=showError(request, "indirizzoError") %>
		</td>
	</tr>
	
	
	
	
	<tr>
		<td nowrap class="formLabel">
			Latitudine
		</td>
		<td>
			
			<input type="text" name="latitudine" id="localitaSedeLegale" pattern="-?\d{1,3}\.\d+" readonly="readonly" value="<%=Stabilimento.getSedeOperativa().getLatitudine()%>"/>
			<font color = "red">*</font>
					<%=showError(request, "latitudineError") %>
		</td>
	</tr>
	<tr>
		<td nowrap class="formLabel">
			Longitudine
		</td>
		<td>
			
			<input type="text" name="longitudine" id="localitaSedeLegale" pattern="-?\d{1,3}\.\d+" readonly="readonly" value="<%=Stabilimento.getSedeOperativa().getLongitudine()%>"  />
			<font color = "red">*</font>
					<%=showError(request, "longitudineError") %>
					
					<div id ="coordinateError"></div>
		</td>
	</tr>
	
	 <tr style="display:block">
    <td colspan="2">
    <input id="coord2button" type="button" value="Calcola Coordinate" 
    onclick="javascript:showCoordinate(document.getElementById('viaTesto').value, document.forms['addstabilimento'].searchcodeIdComuneinput.value,document.forms['addstabilimento'].searchcodeIdprovinciaTesto.value, document.forms['addstabilimento'].presso.value, document.forms['addstabilimento'].longitudine, document.forms['addstabilimento'].latitudine);" />
    <input id="coord2button2" type="button" value="Inserisci Coordinate manuali" 
    onclick="javascript:liberalizzaCoordinate(document.forms['addstabilimento'].longitudine, document.forms['addstabilimento'].latitudine);" />
     </td>
    </tr>
	
	
		
		</table>
	
	</fieldset>	
	
	
<%
	if(Operatore.getListaStabilimenti().isEmpty() && Operatore.getIdTipoAttivita()==1)
	{
%>
	<fieldset>
		<legend><b>DATI APICOLTORE DA AGGIORNARE</b></legend>
		<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		
   <tr>
		<td nowrap class="formLabel">Telefono fisso</td>
		<td>
			<input type = "text" id = "telefono1" name="telefono1" value="">
			<font color = "red">*</font> 
		</td>
	</tr>
	
		</table>
	
	</fieldset>		
<%
	}
%>	
	
	
		<input type ="submit" value="SALVA" >
</form>








<div id = "dialogProprietario">
<form name="addpersona" id="addpersona" action="ApicolturaAttivita.do?command=InsertPersona&tipo=detentore" method="POST">
<div id="messaggioErrore"></div>
<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
	<th colspan="2">Dati Del Delegante</th>
	</tr>
			
			<tr>
				<td colspan="2">
				<div align="center">
			  Persona fisica <input id='persona' checked type='radio' class='tipo_delegante' name="asdf"/>
				Impresa				 <input id='impresa' type='radio' class='tipo_delegante' name="asdf"/><br/>
   
  </div>
  </td>
			</tr>
			
			<tr>
				<td nowrap class="formLabel">Nome </td>
				<td><input type="text" size="70" id="nome" name="nome" class="required">
					<div id="nomeError"></div>
				</td>

			</tr>

			<tr>
				<td nowrap class="formLabel"><label for="cognome-2">Cognome </label></td>
				<td><input type="text" size="70" id="cognome" name="cognome"
					class="required">
						<div id="cognomeError"></div>
					</td>
			</tr>

			<tr>
				<td nowrap class="formLabel"><label for="sesso-2">Sesso </label></td>
				<td><div class="test">
						<input type="radio" name="sesso" id="sesso1" value="M"
							checked="checked" class="required css-radio">
							<label for="sesso1" class="css-radiolabel radGroup2">M</label>
						 <input type="radio"
							name="sesso" id="sesso2" value="F" class="required css-radio">
						<label for="sesso2" class="css-radiolabel radGroup2">F</label>
					</div></td>
			</tr>

			<tr>
				<td nowrap class="formLabel"><label for="dataN-2">Data Nascita </label></td>
				<td><input type="text" size="70" name="dataNascita" readonly="readonly"
					id="dataNascita" class="required" placeholder="dd/MM/YYYY">
						<a href="#" onClick="cal19.select(document.forms['addpersona'].dataNascita,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
				<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
					
					
					</td>
			</tr>

			<tr>
				<td nowrap class="formLabel"><label for="nazioneN-2">Nazione Nascita </label></td>
				<td>
				<%NazioniList.setJsEvent("onchange=\"abilitaCodiceFiscale('nazioneNascita')\"") ; %>
				<%=NazioniList.getHtmlSelect("nazioneNascita", 106) %></td>
			</tr>


			<tr>
				<td nowrap class="formLabel"><label for="comuneNascita-2">Comune Nascita </label></td>
				<td><input type="text" size="70" name="comuneNascita"
					id="comuneNascita" class="required">
					
					</td>
			</tr>

			

			<tr>
				<td id="label_cf" class="formLabel" nowrap>Codice Fiscale</td>
				<td><input type="text" name="codFiscaleSoggetto" 	maxlength="16" id="codFiscaleSoggettoAdd" class="required" />  	<div id="cfError"></div>
					<input type="button" id="calcoloCF" class="newButtonClass"
					value="Calcola Cod. Fiscale"
					onclick="javascript:CalcolaCF(document.addpersona.sesso,document.addpersona.nome,document.addpersona.cognome,document.addpersona.comuneNascita,document.addpersona.dataNascita,'codFiscaleSoggettoAdd')"></input>
					
				</td>
			</tr>
			
			

			<tr>
				<td nowrap class="formLabel"><label for="nazioneN-2">Nazione Residenza </label></td>
				<td>
				<%NazioniList.setJsEvent("onchange=\"sbloccoProvincia('nazioneResidenza','addressLegaleCountry','addressLegaleCity','addressLegaleLine1')\"") ; %>
				<%=NazioniList.getHtmlSelect("nazioneResidenza", 106) %>
				<div id="nazioneError"></div>
				</td>
			</tr>
			
			
			

			<tr>
				<td class="formLabel" nowrap>Comune Residenza</td>
				<td><select name="addressLegaleCity" id="addressLegaleCity" class="required" >
						<option value="">Seleziona Comune</option>
				</select> <input type="hidden" name="addressLegaleCityTesto"
					id="addressLegaleCityTesto" />
					<div id="comuneError"></div>
					</td>
			</tr>
			
			<tr id ="addressLegaleCountryTR">
				<td class="formLabel" nowrap>Provincia Residenza</td>
				<td><input type= "hidden" id="addressLegaleCountry" class="required"
					name="addressLegaleCountry">
									<input type="text" id="addressLegaleCountryTesto" readonly="readonly"
					name="addressLegaleCountryTesto" /></td>
			</tr>


		<tr>
		<td nowrap class="formLabel">Indirizzo Residenza</td>
		<td>
			
			
			<input type="text" name="addressLegaleLine1Testo" id="addressLegaleLine1Testo" />
			<div id="indirizzoError"></div>
				
		</td>
	</tr>
	
	<tr>
    		<td nowrap class="formLabel">
      			Domicilio Digitale<br>(Pec)
    		</td>
    		<td>
      			<input type="email" size="70" name="domicilioDigitale"  pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" required>    
    		</td>
  		</tr>

</table>
</form>
</div>

	
<script>



var campoLat;
var campoLong;
	function showCoordinate(address,city,prov,cap,campo_lat,campo_long)
	{
   campoLat = campo_lat;
   campoLong = campo_long;
   address = trim(address);
   if (city=='' || prov=='' || address==''){alert('Attenzione! riemprire i campi Comune, Provincia e Indirizzo!');}
   else{
   Geocodifica.getCoordinate(address,city,prov,cap,'','','',setGeocodedLatLonCoordinate);
   }
   }
	
	function liberalizzaCoordinate(campo_lat,campo_long)
	{
   campoLat = campo_lat;
   campoLong = campo_long;
   if (confirm('ATTENZIONE. Liberalizzando le coordinate sarà possibile inserire manualmente i valori assumendosi le responsabilità su eventuali errori di formato (XX.YYYY) o precisione.')){
	   campoLat.readOnly=false;
	   campoLong.readOnly=false;
	   campoLong.style.background="khaki";
	   campoLat.style.background="khaki";
   }
   }

	function setGeocodedLatLonCoordinate(value){
		campoLat.value = value[0];
		campoLong.value =value[1];
	}   
   


$("#addpersona").submit(function(e)
		{
		    var postData = $(this).serializeArray();
		    var formURL = $(this).attr("action");
		    $.ajax(
		    {
		        url : formURL,
		        type: "POST",
		        data : postData,
		        dataType : "json",
		        success:function(data, textStatus, jqXHR)
		        {
		        	if (data.EsitoInserimentoSoggettoFisico=='KO')
		        	{
		        		document.getElementById("messaggioErrore").innerHTML="<font color='red' font-size:small;>"+data.ErroreInserimento+"</font>";
		        		if (data.nomeError!=null)
		        			{
		        			document.getElementById("nomeError").innerHTML="<font color='red' font-size:small;>"+data.nomeError+"</font>";
		        			}
		        		else
		        			{
		        			document.getElementById("nomeError").innerHTML="";
		        			}
		        		
		        		
		        		if (data.cognomeError!=null)
	        			{
	        			document.getElementById("cognomeError").innerHTML="<font color='red' font-size:small;>"+data.cognomeError+"</font>";
	        			}
	        		else
	        			{
	        			document.getElementById("cognomeError").innerHTML="";
	        			}
		        		
		        		if (data.cfError!=null)
	        			{
	        			document.getElementById("cfError").innerHTML="<font color='red' font-size:small;>"+data.cfError+"</font>";
	        			}
	        		else
	        			{
	        			document.getElementById("cfError").innerHTML="";
	        			}
		        		
		        		if (data.ComuneError!=null)
	        			{
	        			document.getElementById("comuneError").innerHTML="<font color='red' font-size:small;>"+data.ComuneError+"</font>";
	        			}
	        		else
	        			{
	        			document.getElementById("comuneError").innerHTML="";
	        			}
		        		
		        		       		
		        		if (data.IndirizzoError!=null)
	        			{
	        			document.getElementById("indirizzoError").innerHTML="<font color='red' font-size:small;>"+data.IndirizzoError+"</font>";
	        			}
	        		else
	        			{
	        			document.getElementById("indirizzoError").innerHTML="";
	        			}
		        		
		        		if (data.nazioneError!=null)
	        			{
	        			document.getElementById("nazioneError").innerHTML="<font color='red' font-size:small;>"+data.nazioneError+"</font>";
	        			}
	        		else
	        			{
	        			document.getElementById("nazioneError").innerHTML="";
	        			}
		        		
		        		
		        		
		        	}
		        	else
		        		{
		        		alert('Persona Anagrafata con successo!');
		        		document.getElementById("nominativo").value=data.nominativoSoggettoFisico ;
		        		document.getElementById("idSoggettoFisico").value=data.idSoggettoFisico ;
		        		document.getElementById("codFiscaleSoggettoinput").value=data.cfSoggettoFisico ;
		        		$( '#dialogProprietario' ).dialog('close');
		        		}
		        	
		            //data: return data from server
		        },
		        error: function(jqXHR, textStatus, errorThrown)
		        {
		        	alert("errore");
		            //if fails     
		        }
		    });
		    e.preventDefault(); //STOP default action
		  
		});
		
		

$(".tipo_delegante").change(function () {                  
	if ($("#persona").attr("checked")) {
    	$("#label_cf").html("CODICE FISCALE");
    	$("#calcoloCF").show();
    }else{
		$("#label_cf").html("PARTITA IVA");
    	$("#calcoloCF").hide();
		}
});

function copiaProprietario(){
	if (document.getElementById("copia_proprietario").checked){
		$("#codFiscaleSoggettoinput").val(document.getElementById("cod_fiscale_propr").value);
		$("#nominativo").val(document.getElementById("cognome_nome").value);
		$("#codFiscaleSoggettoinput").prop('readonly',true);
		$("#nominativo").prop('readonly',true);
		$('#inserisci_persona').hide();
	}else{
		$("#codFiscaleSoggettoinput").val('');
		$("#nominativo").val('');
		$("#codFiscaleSoggettoinput").prop('readonly',false);
		$("#nominativo").prop('readonly',false);
		$('#inserisci_persona').show();
	}
}

</script>



<script type="text/javascript">
if(document.getElementById("inRegione")!=null)
	 inReg=document.getElementById("inRegione").value;
</script>
