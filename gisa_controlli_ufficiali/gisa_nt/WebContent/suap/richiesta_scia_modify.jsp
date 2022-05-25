<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>
<%@page import="org.aspcfs.utils.web.LookupElement"%>
<jsp:useBean id="TipoImpresaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipoSocietaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipoAttivita" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipoCarattere" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipoMobili" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="NazioniList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ToponimiList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="newStabilimento" class="org.aspcfs.modules.suap.base.Stabilimento" scope="request" />
<jsp:useBean id="fissa" class="java.lang.String" scope="request" />
<jsp:useBean id="operazioneScelta" class="java.lang.String" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="StabilimentoOpu" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>


<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/opu.css" />
<link rel="stylesheet" type="text/css" href="css/jquery.calendars.picker.css">
<link href="javascript/datepicker/jquery.datepick.css" rel="stylesheet">
<script src="javascript/suap.jquery.steps.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/gestoreCodiceFiscale.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.plus.js"></script>
<script type="text/javascript" src="javascript/jquery.plugin.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.picker.js"></script>
<script src="javascript/parsedate.js"></script>
<script src="javascript/jquery-ui.js"></script>
<SCRIPT src="javascript/opu.js"></SCRIPT>
<SCRIPT src="javascript/upload.js"></SCRIPT>
<SCRIPT src="javascript/suapCittadinoUtil.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="gestione_documenti/generazioneDocumentale.js"></script>
<script src="javascript/jquery.form1.js"></script>
<script type="text/javascript" src="dwr/interface/SuapDwr.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<script src="javascript/datepicker/jquery.plugin.js"></script>
<script src="javascript/datepicker/jquery.datepick.js"></script>
<script src ="javascript/aggiuntaCampiEstesiScia.js"></script>
<script src="javascript/gestoreCodiceFiscale.js"></script>

<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal99 = new CalendarPopup();
	cal99.showYearNavigation();
	cal99.showYearNavigationInput();
	cal99.showNavigationDropdowns();
</SCRIPT> 




<!-- <script src="javascript/bootstrap.min.js"></script> -->
<script src="javascript/BootSideMenu.js"></script>
<!-- <link rel="stylesheet" href="css/bootstrap.min.css"> -->
  <link rel="stylesheet" href="css/BootSideMenu.css">
  
  <script type="text/javascript">
  $(document).ready(function(){
	  
      $('#panelDetailOpu').BootSideMenu({side:"right",autoClose:true});
  });
  </script>
<style>

.glyphicon-chevron-right
{
border-color: transparent #e3c024 transparent transparent;

}

.glyphicon-chevron-left
{
border-color: transparent #e3c024 transparent transparent;
}
.toggler
{
background-color: transparent;
}
.embed + img { position: relative; left: -24px; top: 0px; }
.td {
        border-right: 1px solid #C1DAD7;
        border-bottom: 1px solid #C1DAD7;
        background: #fff;
        padding: 6px 6px 6px 12px;
        color: #6D929B;
}

#progress {
        position: relative;
        width: 400px;
        border: 1px solid #ddd;
        padding: 1px;
        border-radius: 3px;
}

#bar {
        background-color: #B4F5B4;
        width: 0%;
        height: 20px;
        border-radius: 3px;
}

#percent {
        position: absolute;
        display: inline-block;
        top: 3px;
        left: 48%;
}

.info  {
color:red;
font-size: 25px;
}
.info a:link { color: blue; font-size: 25px;}
.info a:visited { color: navy; font-size: 25px;}
.info a:hover { color: grey; font-size: 25px;}
.info a:active { color: blue; font-size: 25px;}

input:invalid {
  background-image:url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAwAAAAMCAYAAABWdVznAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAT1JREFUeNpi/P//PwMpgImBRMACY/x7/uDX39sXt/67cMoDyOVgMjBjYFbV/8kkqcCBrIER5KS/967s+rmkXxzI5wJiRSBm/v8P7NTfHHFFl5mVdIzhGv4+u///x+xmuAlcdXPB9KeqeLgYd3bDU2ZpRRmwH4DOeAI07QXIRKipYPD35184/nn17CO4p/+cOfjl76+/X4GYAYThGn7/g+Mfh/ZZwjUA/aABpJVhpv6+dQUjZP78Z0YEK7OezS2gwltg64GmfTu6i+HL+mUMP34wgvGvL78ZOEysf8M1sGgZvQIqfA1SDAL8iUUMPIFRQLf+AmMQ4DQ0vYYSrL9vXDz2sq9LFsiX4dLRA0t8OX0SHKzi5bXf2HUMBVA0gN356N7p7xdOS3w5fAgcfNxWtn+BJi9gVVBOQfYPQIABABvRq3BwGT3OAAAAAElFTkSuQmCC);
  background-position:right top;
  background-repeat:no-repeat;
  border: 2px solid red;
}

</style>

<%@ include file="../initPage.jsp" %>


<script>



var resultStab ;
function calcolaCap(idComune, topon, indir, campo){
        if (idComune!="")
                SuapDwr.getCap(idComune, topon, indir, campo,{callback:calcolaCapCallBack,async:false});
        else{
                alert("Controllare di Aver Selezionato il Comune");
        }
}
function calcolaCapCallBack(val){
        if (val!=null && val[1]=="-1"){
                alert(val[0]);
        }else{
                var campo = val[1];
                var value = val[0];
                document.getElementById(campo).value = value;
        }
}

function chkCap(idComune,campo_cap) {
	if (idComune == '5279') {

	document.getElementById(campo_cap).pattern="801[1-9][0-9]";
	} else {
	     document.getElementById(campo_cap).pattern="[0-9]{5}";
	}
}


function getSelectedText(elementId){
        var elt = document.getElementById(elementId);
    if (elt.selectedIndex == -1)
        return null;
    return elt.options[elt.selectedIndex].text;
}

        
/*VALIDAZIONE DEL CAMPO DATA*/
$.validator.addMethod("dataFormat",function(value, element) {
        // put your own logic here, this is just a (crappy) example
        return value.match(/^\d\d?\/\d\d?\/\d\d\d\d$/);
        },
        "Inserire una data nel formato GG/MM/AAAA"
);
  
          
/*GESTIONE STEP AGGIUNGI STABILIMENTO*/
$(function (){
	
	
        $("#spinner").bind("ajaxSend", function() {
                $(this).show();
        }).bind("ajaxStop", function() {
            $(this).hide();
        }).bind("ajaxError", function() {
            $(this).hide();
        });
        var form =$("#example-advanced-form").ajaxForm(options).show();
    form.steps({
            headerTag: "h3",
        bodyTag: "fieldset",
        transitionEffect: "slideLeft",
        stepsOrientreadation: "horizontal",
        onStepChanging: function (event, currentIndex, newIndex){
                // Allways allow previous action even if the current form is not valid!
            if (currentIndex > newIndex){
                    return true;
            }
            // Forbid next action on "Warning" step if the user is to young
            if (currentIndex ==3 ){              
            	
            	  if( ( 
                  		//($( "#methodRequest" ).val()=="cambioTitolarita" && document.getElementById('validatelp').value=='false' ) ||
                  		($( "#methodRequest" ).val()=="ampliamento" && document.getElementById('validatelp').value=='false' ) || 
                  		(
                  		$( "#methodRequest" ).val()=="cessazione" && document.getElementById('validatelp').value=='false' && (
                  		( (document.getElementById("cessazioneStabilimento")!=null && document.getElementById("cessazioneStabilimento").checked==false) || document.getElementById("cessazioneStabilimento")==null )		
                  		))
                  		||
                  		(
                          		$( "#methodRequest" ).val()=="sospensione" && document.getElementById('validatelp').value=='false' && (
                          		( (document.getElementById("sospensioneStabilimento")!=null && document.getElementById("sospensioneStabilimento").checked==false) || document.getElementById("sospensioneStabilimento")==null )		
                          		))
                  		
//                   		(
//                   		( (document.getElementById("sospensioneStabilimento")!=null && document.getElementById("sospensioneStabilimento").checked==false) || document.getElementById("sospensioneStabilimento")==null )		
//                   		)
                  		
                  		 ) 
                  		
                  ){
                        alert('Controllare di aver selezionato almeno una linea di attivita\' dalla master list'.toUpperCase());
                        return false ;
                }
            	
            	if ($( "#methodRequest" ).val()=="ampliamento"){
            		<% for(int i = 0; i < StabilimentoOpu.getListaLineeProduttive().size(); i++ ){ %>
				  		<%LineaProduttiva lp = (LineaProduttiva) StabilimentoOpu.getListaLineeProduttive().get(i); %>
				  		for (var j = 0; j < document.getElementsByName('idLineaProduttiva').length; j++ ){
				  			if(document.getElementsByName('idLineaProduttiva')[j].value == <%=String.valueOf(lp.getId())%> && <%=lp.getStato()%> == 0 ){
					  			alert('LA ' + (j+1) + 'ª LINEA SELEZIONATA RISULTA GIA\' ATTIVA NELLO STABILIMENTO');
			                    return false ;
					  		}
				  		}

			  	<% }%>
            	} 
            	
            }
           
            // Needed in some cases if the user went back (clean up)
            if (currentIndex < newIndex){
                // To remove error styles
                form.find(".body:eq(" + newIndex + ") label.error").remove();
                form.find(".body:eq(" + newIndex + ") .error").removeClass("error");
            }
            form.validate().settings.ignore = ":disabled,:hidden";
            return form.valid();
        },
        onStepChanged: function (event, currentIndex, priorIndex){
                // Used to skip the "Warning" step if the user is old enough.
                
        	 if (currentIndex ==3 && $( "#methodRequest" ).val()=='cambioTitolarita' ){
                 if($("#attprincipale").find('select').attr('name')==null)
         				mostraAttivitaProduttive('attprincipale',1,-1, false,<%=newStabilimento.getTipoInserimentoScia()%>);
                 
                 if($("#idStabilimentoOpu").val()>0 || $("#idStabilimentoOpu").val()!='0')
                	 {
                	 $("#validatelp").val("true")
                	 }
                
		}
           
            if (currentIndex ==4 ){ 
                                mostraListaDocumentiAttivitaProduttive(<%=newStabilimento.getIdStabilimento()%>);
                               
            }
            
            if (currentIndex ==5 ){
            	var my_css_class = { float : 'inherit', position : 'relative', width:'auto',height:"900",padding:"2.5%" };
            	            	
            	                window.frames[0].location.reload(true);
            	                $(".wizard > .content > .body").css(my_css_class);
            	            }
                },
        onFinishing: function (event, currentIndex){
                form.validate().settings.ignore = ":disabled";
            return form.valid();
        },
        onFinished: function (event, currentIndex){
                alert("Submitted!");
        },
        onSaveTemp: function (event, currentIndex){
                alert("Submitted!");
        }
    }).validate({
            errorPlacement: function errorPlacement(error, element) { element.before(error); },
        rules: {
                dataNascita: {
                    dataFormat :true
            }
        }
    });
})
       

  

          
/*AUTOCOMPLETAMENTO PER GLI INDIRIZZI*/          
$(function(){
        //$( "#searchcodeIdComune" ).combobox();
    //  $( "#partitaIva" ).combobox();
    //$( "#addressLegaleCountry" ).combobox();
    //$( "#addressLegaleCity" ).combobox();
    $( "#comuneNascita" ).combobox();

    //$( "#searchcodeIdComune" ).combobox();
    //$( "#searchcodeIdprovincia" ).combobox();

    $( "#searchcodeIdComuneStab" ).combobox();
    $( "#searchcodeIdprovinciaStab" ).combobox();
    
 	$( "#addressLegaleLine1" ).combobox();
    $( "#viaStab" ).combobox();
    $( "#via" ).combobox();
    
    
    	$('input[name=viaStabinput]').change( function(){checkStab(true);});

    	
            $('#dataInizio').datepick({dateFormat: 'dd/mm/yyyy',    showOnFocus: false, showTrigger: '#calImg', onClose: controlloDate}); 
            $('#dataFine').datepick({dateFormat: 'dd/mm/yyyy', showOnFocus: false, showTrigger: '#calImg',onClose: controlloDate});
            
            $( "#dialogsuaplistastab" ).dialog({
            	title:"SELEZIONARE LO STABILIMENTO SE CORRISPONDE CON QUELLO PER CUI SI STA PRESENTANDO LA SCIA",
                autoOpen: false,
                resizable: false,
                closeOnEscape: false,
                width:1350,
                height:500,
                draggable: false,
                modal: false,
        }).prev(".ui-dialog-titlebar").css("background","#bdcfff");
            
//             $('input[name=searchcodeIdComuneStabinput]').change( function(){checkStab();});
    
});

function resizeIframe(iframe){
        
    iframe.height = iframe.contentWindow.document.body.scrollHeight + "px";
}
 


var campoLat;
var campoLong;

function showCoordinate(address,city,prov,cap,campo_lat,campo_long){
        campoLat = campo_lat;
        campoLong = campo_long;
        Geocodifica.getCoordinate(address,city,prov,cap,'','','',setGeocodedLatLonCoordinate);
}
        
function setGeocodedLatLonCoordinate(value){
	
	if(value[0]=="0.0")
    {
    	alert('Non è stato possibile calcolare le coordinate. Inserirle manualmente.');
    	campoLat.value = "";
    	campoLong.value = "";
    }
	else
	{
    	campoLat.value = value[1];
    	campoLong.value =value[0];
	}
}


function controllaCoordinate(coord, tipo){
        var val = coord.value;
        if(isNaN(val)){
                alert('Inserire le coordinate nel formato numerico');
                coord.value = '';
                return false;
        }
        if(val.charAt(2)!='.'){
                alert('Inserire le coordinate nel formato corretto (XX.XXXXXX)');
                coord.value = '';
                return false;
        }
        if (tipo=='lat' && (val < 39.988475 || val > 41.503754)){
                alert('Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754');
                coord.value = '';
                return false;
        }   
        if (tipo=='long' && (val < 13.7563172 || val > 15.8032837)){
              alert('Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837');
              coord.value = '';
                return false;
          }   
        return true;
}

function inizializzaCoordinate(campo, tipo){
	var coord = document.getElementById(campo);
	var val = coord.value;
    if(isNaN(val)){
            coord.value = '';
    }
    if(val.charAt(2)!='.'){
            coord.value = '';
    }
    if (tipo=='lat' && (val < 39.988475 || val > 41.503754)){
            coord.value = '';
    }   
    if (tipo=='long' && (val < 13.7563172 || val > 15.8032837)){
          coord.value = '';
     }   
}

function svuotaCf(){
	var cf = document.getElementById("codFiscaleSoggetto");
	cf.value="";
	
	
}

function gestioneSciaBreveGoToStep()
{
	idStabOpu = $("#idStabilimentoOpu").val();
	operazione = $("#operazioneScelta").val();
	var posizionePartenza = 0 ;
	
	if(idStabOpu>0)
		{
		if(operazione=='ampliamento' || operazione=='cessazione' || operazione=='sospensione')
			{
			posizionePartenza = 3;
			goToStep(wizard, getOptions(wizard), getState(wizard),posizionePartenza);

			}
		if(operazione=='modificaStatoLuoghi')
			{
			posizionePartenza = 4;
			goToStep(wizard, getOptions(wizard), getState(wizard),posizionePartenza);

			}
		
		
		
		}
	
	}

$( document ).ready(function() {
	$('#tipo_impresa').trigger('change');
	
	$('.glyphicon-chevron-right').html("<b>>></b>");
	$('.glyphicon-chevron-left').html("<b><<</b>");
	
	/*NEL CASO DI GESTIONE SCIA BREVE PARTO DALLA PRIMA MASCHERA UTILE PER IL TIPO DI OPERAZIONE SCELTO*/
	gestioneSciaBreveGoToStep();
	
	
	
});





</script>

<% 
String contesto = (String) application.getAttribute("SUFFISSO_TAB_ACCESSI");
	if (contesto!=null && !contesto.equals("") && contesto.equals("_ext")){
			
	}else{
		
		boolean riconosciuto = newStabilimento.getTipoInserimentoScia() ==   newStabilimento.TIPO_SCIA_RICONOSCIUTI;
%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">
<a href="GisaSuapStab.do?command=SearchForm"><dhv:label name="">Ricerca Pratiche SUAP</dhv:label></a> >
<a href="GisaSuapStab.do?command=Default"><dhv:label name="">Pratiche SUAP</dhv:label></a> >
<a href="SuapStab.do?command=Scelta&tipoInserimento=<%=newStabilimento.getTipoInserimentoScia() %>&stato=<%=newStabilimento.getStato() %>&fissa=<%=fissa%>&dataRichiesta=<%= toDateasString(newStabilimento.getDataRichiestaSciaAsl()) %>"><dhv:label name=""><%="Tipologia di "+(newStabilimento.getTipoInserimentoScia() == newStabilimento.TIPO_SCIA_RICONOSCIUTI ? "pratica" : "SCIA") %></dhv:label></a>
 > <%=("ampliamento".equalsIgnoreCase(operazioneScelta)) ? "SCIA PER AMPLIAMENTO LINEE".replace(riconosciuto ? "SCIA" : "", "") : ("cambiotitolarita".equalsIgnoreCase(operazioneScelta) ? "SCIA PER VARIAZIONE TITOLARITA'".replace(riconosciuto ? "SCIA" : "", "") : ("cessazione".equalsIgnoreCase(operazioneScelta) ? "SCIA PER CESSAZIONE".replace(riconosciuto ? "SCIA" : "", "") : ("sospenzione".equalsIgnoreCase(operazioneScelta) ? "RICHIESTA SOSPENSIONE".replace(riconosciuto ? "SCIA" : "", "") : "SCIA PER MODIFICA STATO DEI LUIGHI".replace(riconosciuto ? "SCIA" : "", ""))))  %>
</td>
</tr>
</table>
<%}%>

<br>
<%--<h3 align="center"><%="COMUNE DI: "+User.getUserRecord().getSuap().getDescrizioneComune()%></h3> --%>
<div><center><fieldset style="display: inline-block"><legend>Comune di:</legend><font size="3px" style="font-weight : bold"><%=User.getUserRecord().getSuap().getDescrizioneComune()%></font></fieldset></center></div>
<br>
<br>

<form id="example-advanced-form" name="addstabilimento" enctype="multipart/form-data">
        <input type="hidden" name="stato" id="stato" value="<%=newStabilimento.getStato()%>"> 
        <input type="hidden" name="tipoInserimentoScia" id="tipoInserimentoScia" value="<%=newStabilimento.getTipoInserimentoScia()%>"> 
        <input type="hidden" name="idOperatore" id="idOperatore" value="n.d">
        <input type="hidden" name="idStabilimento" id="idStabilimento" value="<%=newStabilimento.getIdStabilimento()%>">  
        <input type="hidden" name="idStabilimentoAdd" id="idStabilimentoAdd" value="<%=newStabilimento.getIdStabilimento()%>">
        <input type="hidden" name="operazioneScelta" id="operazioneScelta" value="<%=operazioneScelta%>">
        <input type="hidden" name="pratica_completa" id="pratica_completa" value="0">
        <input type="hidden" name="documentazione_parziale" id="documentazione_parziale" value="0"> 
        <input type="hidden" name="methodRequest" id="methodRequest" value="<%=operazioneScelta%>">
        <input type="hidden" name="idStabilimentoOpu" id="idStabilimentoOpu" value="<%=StabilimentoOpu.getIdStabilimento()%>">



 <input type = "hidden" name="dataRichiesta" value = "<%= toDateasString(newStabilimento.getDataRichiestaSciaAsl()) %>">
		<input type="hidden" name="comuneSuap" id="comuneSuap" value="<%=User.getUserRecord().getSuap().getDescrizioneComune() %>">
		<input type="hidden" name="idComuneSuap" id="idComuneSuap" value="<%=User.getUserRecord().getSuap().getIdComuneSuap() %>">
		
		<input type="hidden" name="descrizioneProvincia" id="descrizioneProvincia" value="<%=User.getUserRecord().getSuap().getDescrizioneProvincia() %>">
		<input type="hidden" name="idProvinciaSuap" id="idProvinciaSuap" value="<%=User.getUserRecord().getSuap().getIdProvinciaSuap() %>">
        		<input type="hidden" name="fissa" id="fissa" value="<%=fissa%>">
        
        
        <div style="display: none;"> 
            &nbsp;&nbsp;<img id="calImg" src="images/cal.gif" alt="Popup" class="trigger"> 
        </div>
       
        <h3>TIPO IMPRESA</h3>
        <fieldset>
                <legend><b>INDICARE IL TIPO DI IMPRESA CHE SI VUOLE INSERIRE</b></legend>
                <%
                String onchange = "" ;
                if(StabilimentoOpu.getIdStabilimento()>0)
                	onchange="onChangeTipoImpresaGestioneBreve("+StabilimentoOpu.getOperatore().getTipo_societa()+");";
                else
                	onchange="onChangeTipoImpresa();";                	
                %>
   <%
                        TipoImpresaList.setJsEvent("onchange=onChangeTipoImpresa();");
                TipoImpresaList.setRequired(true);
                	   
                %>
               
              <%=TipoImpresaList.getHtmlSelect("tipo_impresa",StabilimentoOpu.getOperatore().getTipo_impresa()) %>
		     
        </fieldset>
         
        
        <h3>IMPRESA</h3>
        <fieldset>
               <jsp:include page="suap_imprese.jsp"/>
        </fieldset>
        
        <h3>STABILIMENTO</h3>
        <fieldset>
         <fieldset>
                <legend><b>STABILIMENTO DA VARIARE</b></legend>
                  <jsp:include page="suap_stabilimento_numreg.jsp"/>                 
        </fieldset>
            
                
        
        <fieldset>
                <legend><b>DATI STABILIMENTO</b></legend>
                
                 <% 
       
        if (operazioneScelta.equals("cambioTitolarita") ) {%>
        <jsp:include page="suap_stabilimento_variazione.jsp"/>
		<%}
		else {  %>
   		<jsp:include page="suap_stabilimento.jsp"/>                 
        <%} %>
                 
                 
                   
        </fieldset>
        
        </fieldset>
        
        <h3>LINEE DI ATTIVITA'</h3>
        <fieldset>
        <% 
       
        if (operazioneScelta.equals("cessazione") ) {%>
             <jsp:include page="suap_linee_attivita_cessazione.jsp"/>
             <script>window.devoNascondereAllegati = true;</script>
		<% } else if (operazioneScelta.equals("ampliamento")){ %>	
		    <jsp:include page="suap_linee_attivita_ampliamento.jsp"/>
		    <%} else if (operazioneScelta.equals("sospensione")){ %>
		    <jsp:include page="suap_linee_attivita_sospensione.jsp"/>
		    <%}
		    else if (operazioneScelta.equals("modificaStatoLuoghi")) {%>
		    	<jsp:include page="suap_linee_attivita_modificaStatoLuoghi.jsp" />
		    <%}
		else {  %>
		    <jsp:include page="suap_linee_attivita.jsp"/>                 
                 <%} %>
        </fieldset>
        
        <%--h3>ALLEGATI</h3>
        <fieldset  >
                <legend><b>DOCUMENTAZIONE DA ALLEGARE</b></legend>
                 <jsp:include page="allegati_scia_js.jsp"/>       
                 <% boolean firmaObbligatoria = (Boolean) request.getAttribute("firmaDigitaleObbligatoria"); %>
                 <center>
                 <div id="div_allegati">
                 	 <font color="red"><b>Si ricorda che per le linee di attivita' scelte sono richiesti i seguenti documenti</b></font>
                 </div> 
                  <div id="msg_aggiuntivo"></div>
                  </center>
                  <br/>
                  
                <div id="documenti" class="documenti"></div>
                
                <script>
                	try
                	{
                		devoNascondereAllegati;
                		console.log("devo nascondere tab allegati");
                		$("div#div_allegati").css("opacity",0.1).css("color","rgba: (125,125,125,0.1)");
                		$("div#msg_aggiuntivo").html( $("<font>",{
                				size : '2'
                		}).css("font-weight","bold").text("ALLEGATI NON NECESSARI PER L'OPERAZIONE DI CESSAZIONE. PROSEGUI PER COMPLETARE LA PROCEDURA.") );
                		
                	}
                	catch(e)
                	{
                		
                	}
                </script>
               
                
                
                
                
                
<%
         if (operazioneScelta.equals("modificaStatoLuoghi")) 
         {
        	 boolean superficeDaMostrare = false;
        	 Iterator<LineaProduttiva> itLp = StabilimentoOpu.getListaLineeProduttive().iterator();
        	 while (itLp.hasNext())
        	 {
        		LineaProduttiva lp = itLp.next();
        		if(lp.getId()==635 || lp.getId()==636)
        		{
        			superficeDaMostrare=true;
        			break;
        		}
			}
%>
		
		
<%
				if(superficeDaMostrare)
				{
%>		
              		Superficie destinata al ricovero di animali (specificare i metri quadri totali):<br/>
              		<input type ="number" name="superficie" id="superficie" value=""  required="required"></input>
<%               
				}   
                  
         }
%>
                
                
   </fieldset--%>              
                
                
                
                
                
                
        
        
        <h3>CONFERMA</h3>
        <fieldset>
               <jsp:include page="suap_form.jsp"/>
        </fieldset>
</form>


<form style="visibility:hidden;" name="searchStab" id = "searchStab">

</form>

<script>
mostraDatiStabilimento(document.getElementById("tipoAttivita").value);
</script>


<div id="dialogsuap"></div>
<div id="dialogsuaplistastab"></div>

<%if(StabilimentoOpu.getIdStabilimento()>0){ 
String tipoScheda = "24" ;
if(fissa!=null && fissa.equals("false"))
{
	tipoScheda = "25";
}
String tipoScia ="";
if (operazioneScelta!=null&& operazioneScelta.equalsIgnoreCase("cambiotitolarita"))
{
	tipoScia="Variazione di Titolarita";
}
else
{
	if (operazioneScelta!=null&& operazioneScelta.equalsIgnoreCase("cessazione"))
	{
		tipoScia="Cessazione";
	}
	else
	{
		if (operazioneScelta!=null&& operazioneScelta.equalsIgnoreCase("ampliamento"))
		{
			tipoScia="Ampliamento";
		}
		else
		{
			if (operazioneScelta!=null&& operazioneScelta.contains("luoghi"))
			{
				tipoScia="Modifica Stato Dei Luoghi";
			}
		}
	}
}
%>
<div id="panelDetailOpu" style="width: 50%" >


<h2><center>Scia di <%=tipoScia %> : Riepilogo Dati Stabilimento </center></h2>
    <jsp:include page="../schede_centralizzate/iframe.jsp">
    <jsp:param name="objectId" value="<%=StabilimentoOpu.getIdStabilimento() %>" />
       <jsp:param name="objectIdName" value="stab_id" />
     <jsp:param name="tipo_dettaglio" value="<%=tipoScheda %>" />
     </jsp:include>
    
  </div>
  <%} %>

