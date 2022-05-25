<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page import="java.io.*,java.util.*,org.aspcfs.utils.web.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.registrazioniAnimali.base.*"%>
<meta charset="utf-8" />

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />

<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>


<script language="javascript" SRC="javascript/CalendarPopup.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/dateControl.js"></script>
<script language="javascript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();
</SCRIPT>

<script type="text/javascript" src="dwr/interface/DwrUtil.js"> </script>
<script TYPE="text/javascript" SRC="dwr/interface/PraticaList.js"></script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<script language="JavaScript" TYPE="text/javascript" SRC="dwr/interface/Animale.js"> </script>

<script type="text/javascript">

var array_pratiche = new Array();

function visualizzaPratiche()
{
	
		// Flusso 251: modifiche del 03/08 - INIZIO -->
		if(true)
		//if(document.getElementById('dataSterilizzazione').value != "" && document.getElementById('sesso').value != "" && document.getElementById('comuneId').value != "")
		// Flusso 251: modifiche del 03/08 - FINE -->
		{
			PraticaList.getListDataLP(document.getElementById('dataSterilizzazione').value , document.getElementById('sesso').value, document.getElementById('comuneId').value, '<%=User.getUserId()%>', valorizzaLista);
		}
		// Flusso 251: modifiche del 03/08 - INIZIO -->
		//else
		//{
			//removeOptions(document.getElementById('progetto'));
		//}
		// Flusso 251: modifiche del 03/08 - FINE -->
		
}


function valorizzaLista(listaPratiche)
{
  var select = document.getElementById('progetto'); 
  var valorePrecedente = select.value; 
  var valueCeAncora = false;

  i = 0;
  k = 0;

  for (var j = select.length - 1; j >= 0; j--)
  	  	select.remove(j);

	 var NewOpttmp = document.createElement('option');
	 NewOpttmp.value=-1;
	 NewOpttmp.text="<-- Selezionare una pratica -->";
	 try{
	 select.add(NewOpttmp, null); //Metodo Standard, non funziona con IE
	 }
	 catch(e){
		 select.add(NewOpttmp); 
	 }	
  while(i < listaPratiche.length){
		
			 array_pratiche[listaPratiche[i].id]=listaPratiche[i];

			 var NewOpt = document.createElement('option');
			 NewOpt.value = listaPratiche[i].id;
			 if(listaPratiche[i].id==valorePrecedente)
					valueCeAncora = true;
			 // Flusso 251: modifiche del 03/08 - INIZIO: aggiunto il totale di cani su ogni progetto -->
		 	 NewOpt.text =  "Decreto numero "+listaPratiche[i].numero_decreto_pratica + " del "+ listaPratiche[i].data_decreto_stringa +" - Cani maschi restanti "+ listaPratiche[i].cani_restanti_maschi + "/" + listaPratiche[i].totale_cani_maschi +" - Cani femmina restanti "+listaPratiche[i].cani_restanti_femmina + "/" + listaPratiche[i].totale_cani_femmina + " - "+ listaPratiche[i].elenco_comuni ;
			
			if(listaPratiche[i].elenco_comuni.length != 0 ){

				for (var k=0; k<listaPratiche[i].elenco_comuni.length; k++) {
				 	NewOpt.text =  "Decreto numero "+listaPratiche[i].numero_decreto_pratica + " del "+ listaPratiche[i].data_decreto_stringa +" - Cani maschi restanti "+ listaPratiche[i].cani_restanti_maschi + "/" + listaPratiche[i].totale_cani_maschi +" - Cani femmina restanti "+listaPratiche[i].cani_restanti_femmina + "/" + listaPratiche[i].totale_cani_femmina  +" - "+ listaPratiche[i].elenco_comuni[k];	
				}
			}
			
			
			
			    try
			    {
			  	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
			    }catch(e){
			  	  select.add(NewOpt); // Funziona solo con IE
			    }
			    i++;						   
	}
  				if(valorePrecedente!="" && valueCeAncora)
                	document.getElementById('progetto').value=valorePrecedente;
    
    }
    
    
    
function removeOptions(selectElement) {
	   var i, L = selectElement.options.length - 1;
	   for(i = L; i >= 0; i--) {
	      selectElement.remove(i);
	   }
	}
    
    
function verificaMicrochipEsistenza(microchip)
{
	var formTest = true;
	var message = "";

	 dwr.util.useLoadingImage('images_bdu/ajax-loader.gif');
	if( !( (microchip.value.length == 15) && ( /^([0-9]+)$/.test( microchip.value )) ) )
     {
    	  message += label("", "- La lunghezza del microchip deve essere di 15 caratteri numerici\r\n");
       	formTest = false;
       	$('#comune').val("");
		$('#comuneId').val("");
		$('#sesso').val("");
     }
	var ok = false;
	if (formTest){
	Animale.verificaEsistenzaMCNonSterilizzato(microchip.value, {
		callback:function(data) {
		if (data.idEsito == '-1'){
			$(microchip).focus();
			$(microchip).css('background-color', 'red');
			$('#dataSterilizzazione').attr("disabled", "true");
			$('#registrazione').val("");
			$('#comune').val("");
			$('#comuneId').val("");
			$('#sesso').val("");
			alert(data.descrizione);
			return false;
		}else{
			$(microchip).css('background-color', 'white');
			$('#dataSterilizzazione').removeAttr('disabled');
			$('#registrazione').val(data.dataRegistrazioneAnimale);
			$('#comune').val(data.comune);
			$('#comuneId').val(data.comuneId);
			$('#sesso').val(data.sesso);
			return true;
		}
		},
		timeout:8000,
		async:false

		});

	//return ok;
	}else{

		 alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
	     return false;
	}
}




function checkForm(form){
	 formTest = true;
	 message = "";
	lanciaControlloDate();
	
	 
	if (formTest){
		
				
	Animale.verificaEsistenzaMCNonSterilizzato(microchip.value, {
		callback:function(data) {
		if (data.idEsito == '-1'){
			$(microchip).focus();
			$(microchip).css('background-color', 'red');
			$('#dataSterilizzazione').attr("disabled", "true");
			$('#registrazione').val("");
			$('#comune').val("");
			$('#comuneId').val("");
			$('#sesso').val("");
			message = data.descrizione;
			formTest =  false;
		}else{
			$(microchip).css('background-color', 'white');
			//alert('WHITE');
			$('#dataSterilizzazione').removeAttr('disabled');
			$('#registrazione').val(data.dataRegistrazioneAnimale);
			$('#comune').val(data.comune);
			$('#comuneId').val(data.comuneId);
			$('#sesso').val(data.sesso);
			formTest =  true;
		}
		},
		timeout:8000,
		async:false

		});
	}
	
	
	if(document.getElementById('progetto').value==null || document.getElementById('progetto').value=="" || document.getElementById('progetto').value=="-1")
	{
		message += "- Selezionare un progetto di sterilizzazione";
		formTest =  false;
	}

   if (formTest == false) {
   	 alert(label("check.form", "Non puoi proseguire:\r\n\r\n") + message);
     return false;
   }else{
     //  alert('else return true');
	return true;
       }
}



dwr.util.useLoadingImage = function useLoadingImage(imageSrc) {
	//alert(imageSrc);
	  var loadingImage;
	  if (imageSrc) loadingImage = imageSrc;
	  else loadingImage = "images/c9_ajax-loader.gif";
	  dwr.engine.setPreHook(function() {
	    var disabledImageZone = dwr.util.byId('disabledImageZone');
	  //  alert(disabledImageZone);
	    if (!disabledImageZone) {
		//    alert('not');
	      disabledImageZone = document.createElement('div');
	      disabledImageZone.setAttribute('id', 'disabledImageZone');
	      disabledImageZone.style.position = "absolute";
	      disabledImageZone.style.zIndex = "1000";
	      disabledImageZone.style.left = "0px";
	      disabledImageZone.style.top = "0px";
	      disabledImageZone.style.width = "100%";
	      disabledImageZone.style.height = "100%";
	      var imageZone = document.createElement('img');
	      imageZone.setAttribute('id','imageZone');
	      imageZone.setAttribute('src','c9_ajax-loader.gif');
	      imageZone.src='c9_ajax-loader.gif';
	      imageZone.style.position = "absolute";
	      imageZone.style.top = "0px";
	      imageZone.style.right = "0px";
	      disabledImageZone.appendChild(imageZone);
	      document.body.appendChild(disabledImageZone);
	    }
	    else {
	     $('#imageZone').attr('src',imageSrc);	    
	      disabledImageZone.style.visibility = 'visible';

	    }
	  });
	  dwr.engine.setPostHook(function() {
		  disabledImageZone.style.visibility = 'hidden';
	  });
	}
</script>


<%@ include file="../initPage.jsp"%>
<dhv:evaluate if="<%=!isPopup(request)%>">
	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
			<td width="100%"><a href="SterilizzazioneLP.do"><dhv:label
				name="">Sterilizzazioni</dhv:label></a> > <dhv:label name="">Aggiungi registrazione</dhv:label>
				
			</td>
		</tr>
	</table>
	<%-- End Trails --%>
</dhv:evaluate>
<body>


<form name="add" action="SterilizzazioneLP.do?command=Insert&auto-populate=true" method="post" id="add"></br>

<input type="hidden" name="idTipologiaSoggettoSterilizzante" id="idTipologiaSoggettoSterilizzante" value="2"/>
<input type="hidden" name="idSoggettoSterilizzante" id="idSoggettoSterilizzante" value="<%=User.getUserId()%>"/>

<span id="datireg" class="datireg">
<input type="hidden" id="registrazione" name="registrazione" nomecampo="registrazione" labelcampo="Data Registrazione" value="" />
<table cellpadding="2" cellspacing="0" border="0" width="100%" class="details">
	<tr>
		<th colspan="2"><strong><dhv:label name="">Dati da inserire</dhv:label></strong>
		</th>
	</tr>
	
	<tr>
		<td class="formLabel"><dhv:label name="">Microchip</dhv:label></td>
		<td><input type="text" name="microchip" id="microchip" value="" onchange="javascript:verificaMicrochipEsistenza(document.forms[0].microchip);visualizzaPratiche();" maxlength="15" />
			<div id="disabledImageZone" style="visibility: hidden; color:red;">Verifica microchip in corso <br><img id="imageZone" src="" name="imageZone" /></div>
			<input type="hidden" name="sesso" id="sesso"/>
		</td>
	</tr>
	
	<tr>
		<td class="formLabel"><dhv:label name="">Data sterilizzazione</dhv:label></td>
		<td><input disabled="disabled" readonly onchange="visualizzaPratiche();" type="text" name="dataSterilizzazione" id="dataSterilizzazione" size="10" value="" nomecampo="dataSterilizzazione" tipocontrollo="T2,T11" labelcampo="Data Sterilizzazione" />&nbsp; 
		<a href="#"
			onClick="cal19.select(document.forms[0].dataSterilizzazione,'anchor19','dd/MM/yyyy'); return false;"
			NAME="anchor19" ID="anchor19"><img
			src="images/icons/stock_form-date-field-16.gif" border="0"
			align="absmiddle"></a></td>
	</tr>
	
	<tr>
		<td class="formLabel">
			Comune proprietario
		</td>
		<td>
			<input type="text" readonly="readonly" name="comune" id="comune"/>
			<input type="hidden" name="comuneId" id="comuneId"/>
		</td>
	</tr>

    <!-- Flusso 251: modifiche del 03/08 - INIZIO -->
	<!--tr>
		<td class="formLabel">
			Progetto di sterilizzazione
		</td>
		<td>
			<select id="progetto" name="progetto">
			</select>
		</td>
	</tr-->
	<!-- Flusso 251: modifiche del 03/08 - FINE -->
	
	<!-- Flusso 251: modifiche del 03/08 - INIZIO -->
	<tr>
		<td class="formLabel">
			Progetto di sterilizzazione
		</td>
		<td>
			<select id="progetto" name="progetto">
			</select>
			<i>La lista viene filtrata in base al mc inserito e alla data di sterilizzazione</i>
		</td>
	</tr>
	<!-- Flusso 251: modifiche del 03/08 - FINE -->
	

</table>
</span> </br>
</br>
<input type="hidden" name="idVeterinarioLLPP" id="idVeterinarioLLPP" value="<%=User.getUserId() %>" />
<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="details">
	<tr>
		<th colspan="2"><strong>NOTE</strong></th>
	</tr>
	<tr class="containerBody">
		<td valign="top" class="formLabel"><dhv:label name="">Note</dhv:label>
		</td>
		<td><textarea name="note" rows="3" cols="50"></textarea>
		</td>
	</tr>
</table>

</br>

 <input type="button" value="Invia dati e stampa modulo" onclick="javascript:loadModalWindow();if(checkForm(document.getElementById('add'))){document.getElementById('add').submit();}else{loadModalWindowUnlock();}" id="invia" />
 </form>
 
 
 <script type="text/javascript">
 <!-- Flusso 251: modifiche del 03/08 - INIZIO -->
 	visualizzaPratiche();
<!-- Flusso 251: modifiche del 03/08 - FINE -->
 </script>