<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="NazioniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/gestoreCodiceFiscale.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/suapUtil.js"></script>
<SCRIPT src="javascript/apiari.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>
<script>
function validateEmail(email) {
	  const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	  return re.test(email);
	}

/*AUTOCOMPLETAMENTO PER GLI INDIRIZZI*/          
$(function() {
  
    $( "#addressLegaleCity" ).combobox();
    $( "#searchcodeIdComune" ).combobox();
    $( "#codFiscaleSoggetto" ).combobox();
 
});

$(function () {
	    
	 
	
	 
	 $( "#dialogDelega" ).dialog({
	    	autoOpen: false,
	        resizable: false,
	        closeOnEscape: false,
	       	title:"INSERIMENTO NUOVA DELEGA",
	        width:850,
	        height:800,
	        draggable: false,
	        modal: true,
	        buttons:{
	        	 "Salva": function() {$("#delegaadd").submit(); } ,
	        	"Esci" : function() { loadModalWindowUnlock(); $(this).dialog("close");}
	        	
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



</script>


<div id = "dialogDelega">

<form name="delegaadd" id="delegaadd" action="ApicolturaAttivita.do?command=InsertPersona" method="POST">
<div id="messaggioErrore"></div>

<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
	<th colspan="2">Dati Del Delegato</th>
	</tr>
	
	<tr>
	<td nowrap class="formLabel">Denominazione </td>
	<td>
		<input type="text" size="70" readonly="readonly" value = "<%=User.getContact().getNameLast()+" - "+User.getContact().getNameFirst() %>">
	</td>
	</tr>
	
	<tr>
	<td nowrap class="formLabel">Codice Fiscale </td>
	<td>
		<input type="text" size="70" readonly="readonly" required="required" id = "codice_fiscale_delegato_" name="codice_fiscale_delegato_" value = "<%=User.getContact().getVisibilitaDelega()%>">
		<input type = "hidden" name = "id_utente_access_ext_delegato_" value = "<%=User.getUserId() %>"/>
	</td>
	</tr>
	
	<tr>
	<td nowrap class="formLabel">Data Assegnazione Delega</td>
	<td>
		<input type="text" size="70"  id = "data_assegnazione_delega_" name="data_assegnazione_delega_" readonly="readonly">
		
			<a href="#" onClick="cal19.select(document.forms['delegaadd'].data_assegnazione_delega_,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
				<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
					
					
	</td>
	</tr>
	</table>
<br>
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
						<a href="#" onClick="cal19.select(document.forms['delegaadd'].dataNascita,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
				<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
					
					<input type = "hidden" name = "inReg" id = "inReg" value = "si">
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
					onclick="javascript:CalcolaCF(document.delegaadd.sesso,document.delegaadd.nome,document.delegaadd.cognome,document.delegaadd.comuneNascita,document.delegaadd.dataNascita,'codFiscaleSoggettoAdd')"></input>
					
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
				<td>
				<select name="addressLegaleCity" id="addressLegaleCity" class="required" >
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
		<td nowrap class="formLabel">CAP Residenza</td>
		<td>
			<input type="text" maxlength="5" name="presso" id="presso" />
		</td>
	</tr>
	<tr>
    		<td nowrap class="formLabel">
      			Domicilio Digitale<br>(Pec)
    		</td>
    		<td>
      			<input type="email" size="70" name="domicilioDigitale" id="domicilioDigitale" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$">    
    		</td>
  		</tr>
  		<tr>
    		<td nowrap class="formLabel">
      			Telefono fisso
    		</td>
    		<td>
      			<input type="text" size="70" name="telefono1" id="telefono1">    
    		</td>
  		</tr>
  		<tr>
    		<td nowrap class="formLabel">
      			Telefono cellulare
    		</td>
    		<td>
      			<input type="text" size="70" name="telefono2"  id="telefono2">    
    		</td>
  		</tr>

</table>
</form>


<form method="POST" name="delegaadd2" id="delegaadd2" action="DelegaAction.do?command=Insert&auto-populate=true"  enctype="multipart/form-data">
<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
	<th colspan="2">ALLEGA DELEGA</th>
	</tr>
			
			<tr>
				<td >Allega Delega</td><td><input type ="file" name = "allegatoDelega" required="required"></td>
				
			</tr>
</table>
<input type ="hidden" name = "conferma" id = "conferma" value="">
<input type = "hidden" name = "codice_fiscale_delegante" id = "codice_fiscale_delegante" >
<input type = "hidden" name = "id_soggetto_fisico_delegante" id = "id_soggetto_fisico_delegante" >
<input type="hidden" size="70" readonly="readonly" id = "codice_fiscale_delegato" name="codice_fiscale_delegato" value = "<%=User.getContact().getVisibilitaDelega()%>">
<input type = "hidden" name = "id_utente_access_ext_delegato" value = "<%=User.getUserId() %>"/>
<input type="hidden" size="70" readonly="readonly" id = "data_assegnazione_delega" name="data_assegnazione_delega">
		
</form>
</div>

<script>

$(".tipo_delegante").change(function () {                  
	if ($("#persona").attr("checked")) {
    	$("#label_cf").html("CODICE FISCALE");
    	$("#calcoloCF").show();
    }else{
		$("#label_cf").html("PARTITA IVA");
    	$("#calcoloCF").hide();
		}
});
	

$("#delegaadd2").submit(function(e)
		{
		    var formURL = $(this).attr("action");
		    var data = new FormData(jQuery('form')[1]);
		    var formURL = $(this).attr("action");
		    $.ajax(
		    {
		        url :formURL,
		        type: "POST",
		        data : data,
		        dataType : "json",
		        async: false,
                cache: false,
                contentType: false,
                processData: false,
		        success:function(data, textStatus, jqXHR)
		        {
		        	
		        	if (data.EsitoInserimentoSoggettoFisico=='KOCONFIRM')
		        	{
		        		if (confirm(data.ErroreInserimento)==true)
		        			{
		        				document.getElementById('conferma').value='si';
		        				$("#delegaadd2").submit();
		        			
		        			}
		        		else
		        			{
		        			document.getElementById('conferma').value='no';
		        			}
		        		
		        	}
		        	else
		        		{
		        		
		        	
		        	
		        	alert("Delega Inserita Correttamente");
		        	loadModalWindowUnlock();

		        	$("#dialogDelega").dialog("close");
		        	location.href="DelegaAction.do?command=List";
		        	
		        		}

		        },
		        error: function(jqXHR, textStatus, errorThrown)
		        {
		        	alert("errore");
		        	loadModalWindowUnlock();
		            //if fails     
		        }
		    });
		    e.preventDefault(); //STOP default action
		  
		});

			$("#delegaadd").submit(function(e)
			{
			if(document.getElementById('presso').value=='80100')
			{
				alert("Il cap della residenza deve essere diverso da 80100");	
			}
			else if(trim(document.getElementById('presso').value)=='')
			{
				alert("Valorizzare il cap della residenza");	
			}
			else if(trim(document.getElementById('domicilioDigitale').value)=='')
			{
				alert("Valorizzare il domicilio digitale");	
			}
			else if(!validateEmail(document.getElementById('domicilioDigitale').value))
			{
				alert("Inserire il domicilio digitale nel formato corretto");	
			}
			else if(trim(document.getElementById('telefono1').value)=='')
			{
				alert("Valorizzare il numero di telefono fisso");	
			}
			else if(trim(document.getElementById('telefono2').value)=='')
			{
				alert("Valorizzare il numero di telefono cellulare");	
			}
			else
			{
			loadModalWindowCustom("Attendere Prego Salvataggio delega in corso..");
		    var postData = $(this).serializeArray();
		    var formURL = $(this).attr("action");
		    $.ajax(
		    {
		        url : "ApicolturaAttivita.do?command=InsertPersona",
		        type: "POST",
		        async: false,
		        data : postData,
		        dataType : "json",
		        success:function(data, textStatus, jqXHR)
		        {
		        	if (data.EsitoInserimentoSoggettoFisico=='KO' && data.ErroreInserimento!='Persona gia anagrafata')
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
		        		
		        		if (data.capError!=null)
	        			{
	        			document.getElementById("capError").innerHTML="<font color='red' font-size:small;>"+data.capError+"</font>";
	        			}
	        		else
	        			{
	        			document.getElementById("capError").innerHTML="";
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
		        		
		        		document.getElementById('codice_fiscale_delegante').value=data.cfSoggettoFisico;
		        		document.getElementById('id_soggetto_fisico_delegante').value=data.idSoggettoFisico;
		        		document.getElementById('data_assegnazione_delega').value=document.getElementById('data_assegnazione_delega_').value;
		        		$("#delegaadd2").submit();
		        		}
		        	
		            //data: return data from server
		        },
		        error: function(jqXHR, textStatus, errorThrown)
		        {
		        	alert("errore");
		        	loadModalWindowUnlock();
		            //if fails     
		        }
		    });
			}
		    e.preventDefault(); //STOP default action
		  
		});
		</script>