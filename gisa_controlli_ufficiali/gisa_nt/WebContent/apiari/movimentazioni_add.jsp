<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="ext.aspcfs.modules.apiari.base.Stabilimento"%>
<%@page import="ext.aspcfs.modules.apiari.base.StabilimentoList"%>
<jsp:useBean id="ApicolturaSottospecie" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ApicolturaModalita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ApicolturaClassificazione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoAttivitaApi" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="NazioniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Operatore" class="ext.aspcfs.modules.apiari.base.Operatore" scope="request" />





<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/opu.css" />

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





<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/theme.css"></link>
<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/jquery.tablesorter.pages.css"></link>

<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.pager.js"></script>

<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.widgets.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/tableJqueryFilterApi.js"></script>


<script src="javascript/jquery.searchable-1.0.0.min.js"></script>
<!--   <link rel="stylesheet" type="text/css" media="screen" href="http://combogrid.justmybit.com/resources/css/smoothness/jquery-ui-1.10.1.custom.css"/> -->

  <link rel="stylesheet" type="text/css" media="screen" href="css/jquery.ui.combogrid.css"/>
  <script type="text/javascript" src="javascript/jquery.ui.combogrid.js"></script>
  <script>
  
  
  function checkForm()
  {
	  
	  formTest = true;
	    message = "";
	    alertMessage = "";
	   
	    form = document.formcessazione ;
	
	
	   
	    if (document.addmovimentazione.dataInizioAttivita!=null && document.addmovimentazione.dataInizioAttivita.value=='' )
		  {
		  message += "- Data Inizio Attivita richiesto\r\n";
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
  function mostraIndirizziinUscita(fieldbox)
	{
	  
	  if (fieldbox.checked)
		  {
		loadModalWindowCustom("Attendere Prego..");
		 $.fx.speeds._default = 1000;
		  var resort = true;
		  $( "#listaview tr" ).remove();
        jQuery("#listanomadismouscita").trigger('update');
       
		idAzienda=document.getElementById("idOperatore").value;
		idApiario=document.getElementById("idApiario").value;
		
		if (idApiario== null || idApiario=='')
			{
            loadModalWindowUnlock();
            fieldbox.checked=false;
			alert("Selezionare l'apiario da spostare");
			}
		else
			{
		link = "ApicolturaMovimentazioni.do?command=SearchIndirizziUscitaGson&idAzienda="+idAzienda+'&idApiario='+idApiario;
		/*Recupero la lista degli utenti per la qualifica selezionata tramite una chiamata ajax*/
		 //start ajax request
              $.ajax({
                  url: link,
                  //force to handle it as text
                  dataType: "json",
                  success: function(data) {
                      
                	 
                  	
                      //data downloaded so we call parseJSON function 
                      //and pass downloaded data
                    if (data!=null)
                    	{
                      var json = data;
                      
                      if (json.length>1 || (json.length==1 && json[0].id>0))
                      	{
                      $.each(json, function(i, item) {
                      	 
                      	 if(item.id>0)
                      		 {
                      		
                      		 alert(item.idcomune);
                      		 $("#searchcodeIdComuneinput").val(item.comune);
                      	    $('#searchcodeIdComune').append('<option value="' + item.idcomune + '" selected="selected">' + item.comune + '</option>');
                      	  
                      	$("#searchcodeIdprovinciaTesto").val(item.provincia);
                       			  $("#searchcodeIdprovincia").val(item.idprovincia);
                       			$("#presso").val(item.cap);
                       			$("#viaTesto").val(item.indirizzo);
                       			$("#latitudine").val(item.latitudine);
                       			$("#longitudine").val(item.longitudine);
                      		
                      		 }
                      	 
                      });
                      	}
                     
                      	}
                    
                      
                      loadModalWindowUnlock();
                      
         
                  }});
		
			}
		  }
	  else
		  {

   		 $("#searchcodeIdComuneinput").val("");
    	  $("#searchcodeIdComune").val("");
    			  $("#searchcodeIdprovinciaTesto").val("");
    			$("#presso").val("");
    			$("#viaTesto").val("");
    			$("#latitudine").val("");
    			$("#longitudine").val("");
		  }
              
		
              
            
			
	}
  
  jQuery(document).ready(function(){
	  
	  
	
	 
	  
	  

		 
	  
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
	  
		  

	   
	  $( "#project" ).combogrid({
		  debug:true,
		  
		  colModel: [{'columnName':'codiceAzienda','width':'20','label':'Codice'},{'columnName':'comune','width':'45','label':'Comune'},{'columnName':'proprietario','width':'30','label':'Proprietario'}],
		  url: 'ApicolturaAttivita.do?command=SearchAzienda&output=json',
		  select: function( event, ui ) {
			  $( "#project" ).val( ui.item.codiceAzienda );
			  $( "#idOperatoreDest" ).val( ui.item.id );
			  $( "#comuneImpresa" ).val( ui.item.comune );
			  $( "#proprietario" ).val( ui.item.proprietario );
			  
			  return false;
		  }
	  });
	  
	  $( "#apiario" ).combogrid({
		  debug:true,
		  
		  colModel: [ {'columnName':'comune','width':'20','label':'Comune'},{'columnName':'indirizzo','width':'20','label':'Indirizzo'},{'columnName':'detentore','width':'45','label':'Detentore'}],
		  url: 'ApicolturaApiari.do?command=SearchApiari&opId=<%=Operatore.getIdOperatore() %>&output=json',
		  select: function( event, ui ) {
			  selectApiario(ui.item.id,ui.item.comune,ui.item.provincia,ui.item.indirizzo,ui.item.detentore,ui.item.cfDetentore);
			  
			  return false;
		  }
	  });
  });
  
 
  </script>
<script>


function selectApiario(progressivo,idApiario,comuneUbicazione,provinciaUbicazione,indirizzoApiario,detentore,cfDetentore)
{
	document.getElementById("idApiario").value=idApiario;
	document.getElementById("indirizzoUbicazione").value=indirizzoApiario;
	document.getElementById("provinciaUbicazione").value=provinciaUbicazione;
	document.getElementById("detentore").value=detentore;
	document.getElementById("cfDetentore").value=cfDetentore;
	document.getElementById("apiario").value=comuneUbicazione;
	document.getElementById("progressivo").value=progressivo;

	
	$(".table-wrapper").hide();
	
}
</script>

<style>
input[readonly="readonly"]
{
    border:0;
}


</style>

<script>



function showDatiTipoAzienda(value)
{
	
	valTipoMovimentazione = document.addmovimentazione.idTipoMovimentazione.value ;
	
	 $("#dati_azienda").find("tr:gt(1)").remove();
	
	if (value=='1')
		{
	$('#dati_azienda').append('<tr><td class="formLabel">Codice</td><td><input size="50" placeholder="DIGITA IL CODICE PER CERCARE" id="project"/></td></tr>');
	
	if(valTipoMovimentazione== '2' )
		$('#dati_azienda').append('<tr><td class="formLabel">Comune</td><td><input type="text" size="50" value="" readonly="readonly" name="comuneImpresa" id="comuneImpresa"><input type="hidden" value="" name="idAziendaInRegioneOut" id="idOperatoreDest"></td></tr>');
	else
		if(valTipoMovimentazione== '1' )
			$('#dati_azienda').append('<tr><td class="formLabel">Comune</td><td><input type="text" size="50" value="" readonly="readonly" name="comuneImpresa" id="comuneImpresa"><input type="hidden" value="" name="idAziendaInRegioneOut" id="idOperatoreDest"></td></tr>');
		
	
	$('#dati_azienda').append('<tr><td class="formLabel">Proprietario</td><td><input type="text" size="50" value="" readonly="readonly" name="proprietario" id="proprietario"></td></tr>');
	$( "#project" ).attr("required","required");
	$( "#project" ).combogrid({
		  debug:true,
		  
		  colModel: [{'columnName':'codiceAzienda','width':'20','label':'Codice'},{'columnName':'comune','width':'45','label':'Comune'},{'columnName':'proprietario','width':'30','label':'Proprietario'}],
		  url: 'ApicolturaAttivita.do?command=SearchAzienda&output=json',
		  select: function( event, ui ) {
			  $( "#project" ).val( ui.item.codiceAzienda );
			  $( "#idOperatoreDest" ).val( ui.item.id );
			  $( "#comuneImpresa" ).val( ui.item.comune );
			  $( "#proprietario" ).val( ui.item.proprietario );
			  
			  return false;
		  }
	  });
	
	
		}
	else
		{
		
		if (value=='2')
		{
			$('#dati_azienda').append('<tr><td class="formLabel">Codice Azienda</td><td><input size="30" required name = "codiceAzienda" id="project" /><input type = "button" onclick="alert(\'Servizio non Disponibile\');" value="Verifica BDN"></td></tr>');
			$('#dati_azienda').append('<tr><td class="formLabel" nowrap>Paese</td><td><%=NazioniList.getSelectedValue(106) %><input type="hidden" name="idPaese" value = "106"></tr>');

			
		}
		else
			{
			$('#dati_azienda').append('<tr><td class="formLabel">Codice Azienda</td><td><input size="30" required name = "codiceAzienda" id="project"/></td></tr>');
			$('#dati_azienda').append('<tr><td class="formLabel" nowrap>Paese</td><td><%=NazioniList.getHtmlSelect("idPaese", -1).replaceAll("\'", "\"") %></tr>');

			}
		 
		 $('#dati_azienda').append('<tr><td class="formLabel">Comune</td><td><input type="text" value="" required  name="comune" id="comuneImpresa"><input type="hidden" value="" name="idAziendaInRegioneOut" id="idOperatoreDest"></td></tr>');
		 $('#dati_azienda').append('<tr><td class="formLabel">Proprietario</td><td><input type="text" required value="" name="nominativoProprietario" id="proprietario"></td></tr>');
		 $('#dati_azienda').append('<tr><td class="formLabel">Codice Fiscale Proprietario</td><td><input type="text" required value="" name="cfProprietario" id="cfProprietario"></td></tr>');

		}
	
}

function showDatiTipoMovimentazione(value)
{
	
	 $("#dati_apiario").find("tr").remove();
	 $("#dati_azienda").find("tr").remove();
	if (value=='2' )
		{
		
		 $('#dati_apiario').append('<tr><th colspan="2"><strong>DATI APIARIO</strong></th></tr>');
		 $('#dati_apiario').append('<tr><td nowrap class="formLabel">CERCA PER Comune</td><td><input type="text"   size="50"  value="" required name="codiceApiario" id = "apiario" placeholder="DIGITARE IL COMUNE PER CERCARE"></td></tr>');

		 $('#dati_apiario').append('<tr><td nowrap class="formLabel">Provincia</td><td><input type="hidden"  id="idApiario"  name="idApiarioIn"  requried value=""><input type="text" value=""  size="50"  readonly="readonly" name="provinciaUbicazione" id="provinciaUbicazione"></td></tr>');
		 $('#dati_apiario').append('<tr><td nowrap class="formLabel">Indirizzo</td><td><input type="text" value="" readonly="readonly"  size="50"  name="indirizzoUbicazione" requried id="indirizzoUbicazione"></td></tr>');	
		 $('#dati_apiario').append('<tr><td nowrap class="formLabel">Detentore</td><td><input type="text" value="" readonly="readonly"  size="50"  name="detentore" requried id="detentore"></td></tr>');	
		 $('#dati_apiario').append('<tr><td nowrap class="formLabel">Codice Fiscale Detentore</td><td><input type="text" value=""  size="50"  readonly="readonly" name="cfDetentore" requried id="cfDetentore"></td></tr>');	
		 $('#dati_apiario').append('<tr><td nowrap class="formLabel">PROGRESSIVO</td><td><input type="text"  size="50"  value="" readonly="readonly" name="progressivo" required id="progressivo"></td></tr>');	

		 
		 
		 $('#dati_azienda').append('<tr><th colspan="2"><strong>DATI COMPRATORE</strong></th></tr>');
		 
		 $('#dati_azienda').append('<tr><td nowrap class="formLabel">Sede Legale Azienda</td><td>In Regione <input type = "radio" name="inNazione" required value="1" onclick="showDatiTipoAzienda(this.value)"> Territorio Nazionale <input type = "radio" required onclick="showDatiTipoAzienda(this.value)" name="inNazione" value="2"> Fuori Territorio Nazionale <input type = "radio" onclick="showDatiTipoAzienda(this.value)" required name="inNazione" value="3"> </td></tr>');
		 
		 
		  
		  $( "#apiario" ).combogrid({
			  debug:true,
			  
			  colModel: [ {'columnName':'comune','width':'30','label':'Comune'},{'columnName':'indirizzo','width':'20','label':'Indirizzo'},{'columnName':'detentore','width':'45','label':'Detentore'} ],
			  url: 'ApicolturaApiari.do?command=SearchApiari&opId=<%=Operatore.getIdOperatore() %>&output=json',
			  select: function( event, ui ) {
				  selectApiario(ui.item.progressivo,ui.item.id,ui.item.comune,ui.item.provincia,ui.item.indirizzo,ui.item.detentore,ui.item.cfDetentore);
				  
				  return false;
			  }
		  });

		}
	else
		{
		
		if (value=='1')
		{
		$('#dati_apiario').append('<tr><th colspan="2"><strong>DATI APIARIO</strong></th></tr>');
		$('#dati_apiario').append('<tr><td class="formLabel" nowrap>Codice Fiscale Detentore</td><td><input type="text" name="codFiscaleSoggetto" readonly="readonly" placeholder="RICERCA DIGITANDO ILCODICE FISCALE" id="codFiscaleSoggetto" class="required" /></td></tr>');
	
		$('#dati_apiario').append('<tr><td nowrap class="formLabel">Detentore (cognome e nome) </td><td><input type="text" size="70" id="nominativo" name="nome" class="required"><input type = "hidden" name = "idSoggettoFisico" id="idSoggettoFisico" value = "" ><input type ="button" onclick="javascript: $( \'#dialogProprietario\' ).dialog(\'open\')" value="Inserisci Persona"></td></tr>');
		$('#dati_apiario').append('<tr><td class="formLabel" nowrap>Classificazione</td><td><%=ApicolturaClassificazione.getHtmlSelect("idApicolturaClassificazione", -1).replaceAll("\'", "\"") %></tr>');
		$('#dati_apiario').append('<tr><td class="formLabel" nowrap>Sottospecie</td><td><%=ApicolturaSottospecie.getHtmlSelect("idApicolturaSottospecie", -1).replaceAll("\'", "\"") %><font color = "red">*</font><%=showError(request, "sottospecieError") %></td></tr>');
		$('#dati_apiario').append('<tr><td class="formLabel" nowrap>Modalita Allevamento</td><td><%=ApicolturaModalita.getHtmlSelect("idApicolturaModalita", -1).replaceAll("\'", "\"") %><font color = "red">*</font><%=showError(request, "modalitaError") %></tr>');
		$('#dati_apiario').append('<tr><td class="formLabel" nowrap>Data Apertura</td><td><input type="text" size="70" name="dataApertura"id="dataInizioAttivita" class="required" placeholder="dd/MM/YYYY"><a href="#" onClick="cal19.select(document.forms[0].dataInizioAttivita,\'anchor19\',\'dd/MM/yyyy\'); return false;" NAME="anchor19" ID="anchor19"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a><font color = "red">*</font><%=showError(request, "dataAperturaError") %></tr>');
		$('#dati_apiario').append('<tr><td nowrap class="formLabel">Numero Alveari</td><td><input type = "text" required name = "numAlveari" id = "numAlveari" style="width: 50px;"><font color = "red">*</font></td></tr>');
		$('#dati_apiario').append('<tr><td nowrap class="formLabel">Numero Sciami/Nuclei</td><td><input type = "text" required name = "numSciami" id = "numSciami" style="width: 50px;"><font color = "red">*</font></td></tr>');
		
		
		$('#dati_apiario').append('<tr><td nowrap class="formLabel">Numero Pacchi Api</td><td><input type = "text"  name = "numPacchi" id = "numPacchi" style="width: 50px;"></td></tr>');
		$('#dati_apiario').append('<tr><td nowrap class="formLabel">Numero Api Regine</td><td><input type = "text"  name = "numRegine" id = "numRegine" style="width: 50px;"></td></tr>');

		
		$('#dati_apiario').append('<tr><td nowrap class="formLabel">Comune</td><td><select name="searchcodeIdComune" required id="searchcodeIdComune" class="required"><option value=""></option></select><font color = "red">*</font><%=showError(request, "comuneError") %><input type="hidden" name="searchcodeIdComuneTesto" id="searchcodeIdComuneTesto" /></td></tr>');
		$('#dati_apiario').append('<tr><td nowrap class="formLabel">Cap</td><td><input type="text" size="70" name="presso" id ="presso"  style="width: 50px;"></td></tr>');
		$('#dati_apiario').append('<tr id ="searchcodeIdprovinciaTR"><td nowrap class="formLabel">Sigla Provincia</td><td><input type="text" readonly="readonly" required="required" name="searchcodeIdprovinciaTesto" id="searchcodeIdprovinciaTesto" /><input type="hidden"  required="required" name="searchcodeIdprovincia" id="searchcodeIdprovincia" /></td></tr>');
		$('#dati_apiario').append('<tr><td nowrap class="formLabel">Indirizzo</td><td><input type="text" name="viaTesto" required id="viaTesto" /><font color = "red">*</font><%=showError(request, "indirizzoError") %></td></tr>');
// 		$('#dati_apiario').append('<tr><td nowrap class="formLabel">Localita</td><td><input type="text" name="localitaSedeLegale" id="localitaSedeLegale" /></td></tr>');
		$('#dati_apiario').append('<tr><td nowrap class="formLabel">Latitudine</td><td><input type="text" name="latitudine" required id="latitudine" /><font color = "red">*</font><%=showError(request, "latitudineError") %></td></tr>');
		$('#dati_apiario').append('<tr><td nowrap class="formLabel">Longitudine</td><td><input type="text" name="longitudine" required id="longituine" /><font color = "red">*</font><%=showError(request, "longitudineError") %></td></tr>');
		$('#dati_apiario').append('<tr style="display:block"><td colspan="2"><input id="coord2button" type="button" value="Calcola Coordinate" onclick="javascript:showCoordinate(document.getElementById(\'viaTesto\').value, document.forms[\'addmovimentazione\'].searchcodeIdComuneinput.value,document.forms[\'addmovimentazione\'].searchcodeIdprovinciaTesto.value, document.forms[\'addmovimentazione\'].presso.value, document.forms[\'addmovimentazione\'].latitudine, document.forms[\'addmovimentazione\'].longitudine);" /></td></tr>');
		 
		$( "#addressLegaleCity" ).combobox();
	    $( "#searchcodeIdComune" ).combobox();
	    $( "#codFiscaleSoggetto" ).combobox();
		   
	    $('#searchcodeIdComuneinput').prop('required', 'required');
	    $('#codFiscaleSoggettoinput').prop('required', 'required');
		    

		    $('#dati_azienda').append('<tr><th colspan="2"><strong>DATI VENDITORE</strong></th></tr>');
			 
			 $('#dati_azienda').append('<tr><td nowrap class="formLabel">Sede Legale Azienda</td><td>In Regione <input type = "radio" name="inNazione" required value="1" onclick="showDatiTipoAzienda(this.value)"> Territorio Nazionale <input type = "radio" required onclick="showDatiTipoAzienda(this.value)" name="inNazione" value="2"> Fuori Territorio Nazionale <input type = "radio" onclick="showDatiTipoAzienda(this.value)" required name="inNazione" value="3"> </td></tr>');
			 
			  
		}
		else
			{
			if (value=='3')
			{
				 $('#dati_apiario').append('<tr><th colspan="2"><strong>APIARIO DA MOVIMENTARE</strong></th></tr>');
				 $('#dati_apiario').append('<tr><td nowrap class="formLabel">CERCA PER Comune</td><td><input type="text" value=""  size="50" name="codiceApiario" required id="apiario" placeholder="DIGITARE IL COMUNE PER CERCARE"></td></tr>');

				 $('#dati_apiario').append('<tr><td nowrap class="formLabel">Provincia</td><td><input type="hidden" id="idApiario" name="idApiarioOut" required value=""><input type="text" value=""  size="50"  readonly="readonly" name="provinciaUbicazione" id="provinciaUbicazione"></td></tr>');
				 $('#dati_apiario').append('<tr><td nowrap class="formLabel">Indirizzo</td><td><input type="text"  size="50"  value="" readonly="readonly" name="indirizzoUbicazione" required id="indirizzoUbicazione"></td></tr>');	
				 $('#dati_apiario').append('<tr><td nowrap class="formLabel">Detentore</td><td><input type="text"  size="50"  value="" readonly="readonly" name="detentore" required id="detentore"></td></tr>');	
				 $('#dati_apiario').append('<tr><td nowrap class="formLabel">Codice Fiscale Detentore</td><td><input type="text"  size="50"  value="" readonly="readonly" name="cfDetentore" id="cfDetentore"></td></tr>');	
				 $('#dati_apiario').append('<tr><td nowrap class="formLabel">PROGRESSIVO</td><td><input type="text"  size="50"  value="" readonly="readonly" name="progressivo" required id="progressivo"></td></tr>');	

				  $( "#apiario" ).combogrid({
					  debug:true,
					  
					  colModel: [  {'columnName':'comune','width':'20','label':'Comune'},{'columnName':'indirizzo','width':'20','label':'Indirizzo'},{'columnName':'detentore','width':'45','label':'Detentore'}],
					  url: 'ApicolturaApiari.do?command=SearchApiari&opId=<%=Operatore.getIdOperatore() %>&output=json',
					  select: function( event, ui ) {
						  selectApiario(ui.item.progressivo,ui.item.id,ui.item.comune,ui.item.provincia,ui.item.indirizzo,ui.item.detentore,ui.item.cfDetentore);
						  
						  return false;
					  }
				  });

				 	$('#dati_azienda').append('<tr><th colspan="2"><strong>NUOVA DESTINAZIONE</strong></th></tr>');
				 	$('#dati_azienda').append('<tr><td nowrap class="formLabel">RIPORTA IN SEDE</td><td><input type = "checkbox" name="insede" value="1" onclick="javascript:mostraIndirizziinUscita(this)"></td></tr>');

				 	$('#dati_azienda').append('<tr><td nowrap class="formLabel">Comune</td><td><select name="searchcodeIdComune" id="searchcodeIdComune" class="required"><option value=""></option></select><font color = "red">*</font><%=showError(request, "comuneError") %><input type="hidden" name="searchcodeIdComuneTesto" id="searchcodeIdComuneTesto" /></td></tr>');
					$('#dati_azienda').append('<tr><td nowrap class="formLabel">Cap</td><td><input type="text" size="70" name="presso" id ="presso"  style="width: 50px;"></td></tr>');
					$('#dati_azienda').append('<tr id ="searchcodeIdprovinciaTR"><td nowrap class="formLabel">Sigla Provincia</td><td><input type="text" readonly="readonly" required="required" name="searchcodeIdprovinciaTesto" id="searchcodeIdprovinciaTesto" /><input type="hidden"  required="required" name="searchcodeIdprovincia" id="searchcodeIdprovincia" /></td></tr>');
					$('#dati_azienda').append('<tr><td nowrap class="formLabel">Indirizzo</td><td><input type="text" name="viaTesto" required id="viaTesto" /><font color = "red">*</font><%=showError(request, "indirizzoError") %></td></tr>');
// 					$('#dati_azienda').append('<tr><td nowrap class="formLabel">Localita</td><td><input type="text" name="localitaSedeLegale" id="localitaSedeLegale" /></td></tr>');
					$('#dati_azienda').append('<tr><td nowrap class="formLabel">Latitudine</td><td><input type="text" name="latitudine" required id="latitudine" /><font color = "red">*</font><%=showError(request, "latitudineError") %></td></tr>');
					$('#dati_azienda').append('<tr><td nowrap class="formLabel">Longitudine</td><td><input type="text" name="longitudine" required id="longitudine" /><font color = "red">*</font><%=showError(request, "longitudineError") %></td></tr>');
					$('#dati_azienda').append('<tr style="display:block"><td colspan="2"><input id="coord2button" type="button" value="Calcola Coordinate" onclick="javascript:showCoordinate(document.getElementById(\'viaTesto\').value, document.forms[\'addmovimentazione\'].searchcodeIdComuneinput.value,document.forms[\'addmovimentazione\'].searchcodeIdprovinciaTesto.value, document.forms[\'addmovimentazione\'].presso.value, document.forms[\'addmovimentazione\'].latitudine, document.forms[\'addmovimentazione\'].longitudine);" /></td></tr>');
					  $( "#addressLegaleCity" ).combobox();
					    
					    $( "#searchcodeIdComune" ).combobox();
					    
					    $('#addressLegaleCityinput').prop('required', 'required');
					    $('#searchcodeIdComuneinput').prop('required', 'required');
					  
				
			}
			
			
			
			}
		 
	   
	      

		
		}
}
  
	


  
</script>


<table class="trails" cellspacing="0">
<tr>
<td>
Apicoltura > Movimentazioni 
</td>
</tr>
</table>

<form  name="addmovimentazione" action="ApicolturaMovimentazioni.do?command=Insert&auto-populate=true" method="POST" onsubmit="return checkForm()">

<input type="hidden" name="idOperatore" id="idOperatore" value="<%=Operatore.getIdOperatore()%>">
	
	<fieldset>
		<legend><b>NUOVA MOVIMENTAZIONE</b></legend>
		<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		</tr>
		 <tr>
    <th colspan="2">
      <strong>AZIENDA</strong>
    </th>
  </tr>

		
		<tr>
			<td nowrap class="formLabel">Tipo di Movimentazione</td>
			<td>
			ACQUISTO <input type="radio" required="required"  name="idTipoMovimentazione" id = "tipoMovimentazione1" value="1" onclick="showDatiTipoMovimentazione(this.value)">  
			VENDITA <input type="radio" required="required"  name="idTipoMovimentazione" id = "tipoMovimentazione2" value="2" onclick="showDatiTipoMovimentazione(this.value)">  
			SPOSTAMENTI/NOMADISMO <input type="radio" required="required"  name="idTipoMovimentazione" id = "tipoMovimentazion3" value="3" onclick="showDatiTipoMovimentazione(this.value)">
			
			
			
			
			</td>
		</tr>
		
		
		
			<tr>
			<td nowrap class="formLabel">Data Movimentazione</td>
			<td>
			<input type= "text" name = "dataMovimentazione" required/>
			<a href="#" onClick="cal19.select(document.forms['addmovimentazione'].dataMovimentazione,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
							<%=showError(request, "dataMovimentazioneError") %>
			
			</td>
		</tr>
		
		<tr>
			<td nowrap class="formLabel">Codice</td>
			<td>
			<%=Operatore.getCodiceAzienda() %>
			
			</td>
		</tr>
		
		
	
		
		<tr>
			<td nowrap class="formLabel">Denominazione</td>
			<td>
			<input type="hidden" name = "idAzienda" value="<%=Operatore.getIdOperatore() %>">
			<%=Operatore.getRagioneSociale() %>
			
			</td>
		</tr>
		
		
			<tr>
				<td class="formLabel" nowrap>Codice Fiscale Proprietario</td>
				<td>
				<%=Operatore.getRappLegale().getCodFiscale() %>
					
				</td>
			</tr>
			
			<tr>
				<td nowrap class="formLabel">Proprietario (cognome e nome) </td>
				<td>
				<%=Operatore.getRappLegale().getCognome() +" "+ Operatore.getRappLegale().getNome() %>
				</td>

			</tr>
			
			
	</table>
	
	<BR>
	<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details" id = "dati_apiario">
		 <tr>
    <th colspan="2">
      <strong>DATI APIARIO</strong>
    </th>
  </tr>
  
    
  </table>
  <br>
  
  
  <table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details" id = "dati_azienda">
		 <tr>
    <th colspan="2">
      <strong>DATI AZIENDA DESTINAZIONE</strong>
    </th>
    
    
			
  
  </table>
  
	</fieldset>
	
	
	
		<input type ="submit" value="SALVA" >
</form>










<script>

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






<!-- ADD PERSONA DETENTORE DIALOG -->

<div id = "dialogProprietario">
<form name="addpersona" id="addpersona" action="ApicolturaAttivita.do?command=InsertPersona" method="POST">
<div id="messaggioErrore"></div>
<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
	
	
	
			
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
					
					<a href="#" onClick="cal19.select(document.forms['addpersona'].dataNascita,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
					
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
				<td class="formLabel" nowrap>Codice Fiscale</td>
				<td><input type="text" name="codFiscaleSoggetto" readonly="readonly"
					id="codFiscaleSoggettoAdd" class="required" />  	<div id="cfError"></div>
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
      			<input type="text" size="70" name="domicilioDigitale" >    
    		</td>
  		</tr>

</table>
</form>
</div>
