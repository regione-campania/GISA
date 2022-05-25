<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="TipoImpresaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipoSocietaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipoAttivita" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipoCarattere" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipoMobili" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="NazioniList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ToponimiList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="newStabilimento" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request" />
<jsp:useBean id="fissa" class="java.lang.String" scope="request" />

<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/opu.css" />
<link rel="stylesheet" type="text/css" href="css/jquery.calendars.picker.css">
<link href="javascript/datepicker/jquery.datepick.css" rel="stylesheet">
<script src="javascript/jquery.steps.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/gestoreCodiceFiscale.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.plus.js"></script>
<script type="text/javascript" src="javascript/jquery.plugin.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.picker.js"></script>
<script src="javascript/parsedate.js"></script>
<script src="javascript/jquery.steps.js"></script>

<script src="javascript/jquery-ui.js"></script>
<SCRIPT src="javascript/opu.js"></SCRIPT>
<SCRIPT src="javascript/upload.js"></SCRIPT>
<SCRIPT src="javascript/suapUtil.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="gestione_documenti/generazioneDocumentale.js"></script>
<script src="javascript/jquery.form.js"></script>
<script type="text/javascript" src="dwr/interface/SuapDwr.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>-->
<script src="javascript/datepicker/jquery.plugin.js"></script>
<script src="javascript/datepicker/jquery.datepick.js"></script>

<style>
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

<script>
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
</script>


<%
        int maxFileSize = -1;
        int mb1size = 1048576;
        maxFileSize = Integer.parseInt(org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("MAX_SIZE_ALLEGATI"));
        String maxSizeString = String.format("%.2f", (double) maxFileSize / (double) mb1size);
%>

<script>
function checkFormFile(i){
        var form = document.getElementById("form"+i);
        var fileCaricato = form.file1;
        var oggetto = form.subject.value;
        var errorString = '';
        if (fileCaricato.value==''){// || (!fileCaricato.value.endsWith(".pdf") && !fileCaricato.value.endsWith(".csv"))){
                errorString+='Errore! Selezionare un file!';
                form.file1.value='';
        }
        if (oggetto==''){
                errorString+='\nErrore! L\'oggetto è obbligatorio.';
        }
        if (!GetFileSize(form.file1))
                errorString+='\nErrore! Selezionare un file con dimensione inferiore a <%=maxSizeString%> MB';
        if (errorString!= '')
                alert(errorString)
        else{
                //form.filename.value = fileCaricato.value;        
                form.uploadButton.hidden="hidden";
                form.file1.hidden="hidden";
                document.getElementById("image_loading").hidden="";
                document.getElementById("text_loading").hidden="";
                form.submit();
        }
}

function GetFileSize(fileid){
        var input = document.getElementById('file1');
    file = input.files[0];
    if (file.size> <%=Integer.parseInt(org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("MAX_SIZE_ALLEGATI"))%>)
                return false;
    return true;
}

$(function(){
        var that;
    $('input[name=partitaIva]').change( function(){
                // Get the other input
        that = $('input[name=partitaIva],input[name=codFiscale]').not($(this));
        // If the value of this input is not empty, set the required property of the other input to false
        if($(this).val().length){
            that.prop('required', false);
        }else{
            that.prop('required', true);
        }
    });
});

function getSelectedText(elementId){
        var elt = document.getElementById(elementId);
    if (elt.selectedIndex == -1)
        return null;
    return elt.options[elt.selectedIndex].text;
}

function visualizzaData(campo){
        if (campo.value=='1'){
                document.getElementById("trDataInizio").style.display="none";
                document.getElementById("trDataFine").style.display="none";
        }else{
                document.getElementById("trDataInizio").style.display="";
                document.getElementById("trDataFine").style.display="";
        }
}
        
/*VALIDAZIONE DEL CAMPO DATA*/
$.validator.addMethod("dataFormat",function(value, element) {
        // put your own logic here, this is just a (crappy) example
        return value.match(/^\d\d?\/\d\d?\/\d\d\d\d$/);
        },
        "Inserire una data nel formato GG/MM/AAAA"
);
        
$(function (){
        $('form[name=addstabilimento]').submit(function(e){
                loadModalWindowCustom("Salvataggio In Corso. Attendere");
                e.preventDefault();
                $.ajax({
                        type: 'POST',
                    dataType: "json",
                    cache: false,
                    url: 'OpuSuapStab.do?command=InsertSuap&auto-populate=true',
                    data: ''+$(this).serialize(), 
                    success: function(msg) {
                    alert(msg.erroreSuap);
                    document.getElementById("idStabilimento").value = msg.idStabilimento;
                    
                    
                    document.getElementById("viewRic").innerHTML = "<button onclick=\"javascript:window.open('./schede_centralizzate/iframe.jsp?objectId="+msg.idStabilimento+"&objectIdName=stab_id&tipo_dettaglio=28')\"  value=\"Visualizza Richiesta Inserita\" />"

                    document.getElementById("stampa").onclick=function(){openRichiestaPDFOpu(msg.idStabilimento, '-1', '-1', '-1', '15');};

                    if(msg.codiceErroreSuap =="1"){
                    }else{
                            if(msg.codiceErroreSuap =="2"){
                                var htmlText=msg.erroreSuap;
                            htmlText+='<br><br><table><tr id=""><th>Ragione Sociale/Denominazione/Ditta</th><th>Partita IVa</th><th>Rappresentante Legale</th><th>Sede Legale</th><th></th></tr>'
                            htmlText+="</table>";
                            htmlText+="<input type='button' value='Inserisci Nuova' onclick='sovrascriviImpresa()'> ";
                            $( "#dialogsuap" ).html(htmlText);
                            showPopUp();
                             }else{
                            $('input[name="idOperatore"]').val("n.d");
                            $('input[name="sovrascrivi"]').val("n.d");
                            document.getElementById("pratica_completa").value="1";
                        }
                    }
                    loadModalWindowUnlock();
                },
                error: function (err){
                        alert('ko '+err.responseText);
                }
        });
});     

$( "#dialogsuapSceltaOperazione" ).dialog({
        autoOpen: false,
        resizable: false,
        closeOnEscape: false,
        width:420,
        height:300,
        draggable: false,
        modal: true,
        }).prev(".ui-dialog-titlebar").css("background","#bdcfff");
        $( "#dialogsuap" ).dialog({
                autoOpen: false,
                resizable: false,
                closeOnEscape: false,
                width:850,
                height:500,
                draggable: false,
                modal: false,
        }).prev(".ui-dialog-titlebar").css("background","#bdcfff");
        $( "#dialogsuaplistastab" ).dialog({
                autoOpen: false,
                resizable: false,
                closeOnEscape: false,
                width:850,
                height:500,
                draggable: false,
                modal: false,
        }).prev(".ui-dialog-titlebar").css("background","#bdcfff");
});         
          
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
        stepsOrientation: "horizontal",
        onStepChanging: function (event, currentIndex, newIndex){
                // Allways allow previous action even if the current form is not valid!
            if (currentIndex > newIndex){
                    return true;
            }
            if (currentIndex ==0){
                           if(document.getElementById('operazioneScelta').value=='' ){
                        alert('Controllare di aver selezionato il tipo di operazione che si desidera effettuare');
                        return false ;
                }
            }
            // Forbid next action on "Warning" step if the user is to young
            if (currentIndex ==4 ){                   
                                //if($("#attprincipale").find('select').attr('name')==null)
                                //mostraAttivitaProduttive('attprincipale',1,-1, false);
                if(document.getElementById('validatelp').value=='false' &&  ( $( "#methodRequest" ).val()=="new" || $( "#methodRequest" ).val()=="ampliamento" )){
                        alert('Controllare di aver selezionato almeno una linea di attivita\' dalla master list'.toUpperCase());
                        return false ;
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
            if (currentIndex ==4 ){
            	
                                if($("#attprincipale").find('select').attr('name')==null)
                        mostraAttivitaProduttive('attprincipale',1,-1, false,<%=newStabilimento.getTipoInserimentoScia()%>);
                                if ($( "#methodRequest" ).val()=='ampliamento')
                                          document.getElementById('validatelp').value="true";
            }
            if (currentIndex ==5 ){
                                mostraListaDocumentiAttivitaProduttive(<%=newStabilimento.getIdStabilimento()%>);
                                if ($( "#methodRequest" ).val()=='ampliamento')
                                          document.getElementById('validatelp').value="true";
            }
            if (currentIndex ==6 ){
                                window.frames[0].location.reload(true);
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
            },
                  domicilioDigitale:{
                    required: true,
                email: true
            }
        }
    });
})
        
function sovrascriviImpresa(){
        $('input[name="sovrascrivi"]').val("si");
        $('#dialogsuap').dialog('close');
        $('form[name=addstabilimento]').submit();
}

var result ;

function selezionaStabilimentoByNumeroRegistrazione(i){
	var msgHD="ATTENZIONE! Uno o più campi obbligatori dello stabilimento non sono presenti, contattare l'helpdesk.";
	
	var stab = resultStab[i];
	
	   $('input[name="idStabilimento"]').val(stab.idStabilimento);
       $('input[name="idStabilimentoAdd"]').val(stab.idStabilimento);
	
	
	if( $( "#methodRequest" ).val()!='cambioTitolarita')
		{
	
		
	document.getElementById("test2").src = "./schede_centralizzate/iframe.jsp?objectId="+stab.idStabilimento+"&objectIdName=stab_id&tipo_dettaglio=15"
	document.getElementById("stampa").onclick=function(){openRichiestaPDFOpu(stab.idStabilimento, '-1', '-1', '-1', '15');};
		}
			
		
	$('input[name="searchcodeIdprovinciaStabinput"]').val(stab.sedeOperativa.descrizione_provincia);
	$("#searchcodeIdprovinciaStab").append("<option value='"+stab.sedeOperativa.idProvincia+ "' selected='selected'>"+stab.sedeOperativa.descrizione_provincia+"</option>');");
	if($('input[name="searchcodeIdprovinciaStabinput"]').val()!="")
		$('input[name="searchcodeIdprovinciaStabinput"]').prop("readonly", true);
  	else
  		if( $('input[name="searchcodeIdprovinciaStabinput"]').prop('required')){
  				
  		}


	$('input[name="searchcodeIdComuneStabinput"]').val(stab.sedeOperativa.descrizioneComune);
	$("#searchcodeIdComuneStab").append("<option value='"+stab.sedeOperativa.comune+ "' selected='selected'>"+stab.sedeOperativa.comune+"</option>');");
	if($('input[name="searchcodeIdComuneStabinput"]').val()!="")
		$('input[name="searchcodeIdComuneStabinput"]').prop("readonly", true);
  	else
  		if( $('input[name="searchcodeIdComuneStabinput"]').prop('required')){
  			
  		}

	
	$('input[name="viaStabinput"]').val(stab.sedeOperativa.via);
	$("#viaStab").append("<option value='"+stab.sedeOperativa.idIndirizzo+ "' selected='selected'>"+stab.sedeOperativa.via+"</option>');");
	if($('input[name="viaStabinput"]').val()!="")
		$('input[name="viaStabinput"]').prop("readonly", true);
  	else
  		if( $('input[name="viaStabinput"]').prop('required')){
  		
  		}


	$('input[name="toponimoSedeOperativa"]').val(stab.sedeOperativa.toponimo);
	if($('input[name="toponimoSedeOperativa"]').val()!="")
		$('input[name="toponimoSedeOperativa"]').prop("readonly", true);
	

	$('input[name="civicoSedeOperativa"]').val(stab.sedeOperativa.civico);
	if($('input[name="civicoSedeOperativa"]').val()!="")
		$('input[name="civicoSedeOperativa"]').prop("readonly", true);
  	else
  		if( $('input[name="civicoSedeOperativa"]').prop('required')){
  			
  		}


	$('input[name="capStab"]').val(stab.sedeOperativa.cap);	
	if($('input[name="capStab"]').val()!="")
		$('input[name="capStab"]').prop("readonly", true);
  	else
  		if( $('input[name="capStab"]').prop('required')){
  			
  		}


	$('#tipoAttivita').val(stab.tipoAttivita);
	if($('#tipoAttivita').val()!="")
		$('#tipoAttivita').prop("readonly", true);
	

	$('#tipoCarattere').val(stab.tipoCarattere);
	if($('#tipoCarattere').val()!="")
		$('#tipoCarattere').prop("readonly", true);
	

	$('#dataInizio').val(stab.dataInizioAttivitaString);
	if($('#dataInizio').val()!="")
		$('#dataInizio').prop("readonly", true);
  	else
  		if( $('#dataInizio').prop('required')){
  				
  		}


	$('#dataFine').val(stab.dataFineAttivitaString);
	if($('#dataFine').val()!="")
		$('#dataFine').prop("readonly", true);
  	else
  		if( $('#dataFine').prop('required')){
  			
  		}

	if(stab.tipoAttivita==1)
		{

	$('#latStab').val(stab.sedeOperativa.latitudine);
	if($('#latStab').val()!="")
		$('#latStab').prop("readonly", true);
	

	$('#longStab').val(stab.sedeOperativa.longitudine);
	if($('#longStab').val()!="")
		$('#longStab').prop("readonly", true);
	

	$('#civicoSedeOperativa').val(stab.sedeOperativa.civico);
	if($('#civicoSedeOperativa').val()!="")
		$('#civicoSedeOperativa').prop("readonly", true);
  	else
  		if( $('#civicoSedeOperativa').prop('required')){
  			 	
  		}


	$('#capStab').val(stab.sedeOperativa.civico);
	if($('#capStab').val()!=""){
		$('#capStab').prop("readonly", true);
		$('#calcolaCAPbtn_stab').hide();
	}
  	else
  		if( $('#capStab').prop('required')){
  				
  		}
	
	$('#datiIndirizzoStab').show();
		}
	
	
	visualizzaData(document.getElementById("tipoCarattere"));

	$("#lineeEsistenti").html("");
	for(i=0;i<stab.listaLineeProduttive.length;i++){
		numSecondarie+=1;
	 	$("#lineeEsistenti").append("<table  style=\"width: 100%;\"><tr><td width=\"60%\"><input type =\"hidden\" name=\"idLineaProduttiva\" value = \""+stab.listaLineeProduttive[i].idRelazioneAttivita+"\">"+stab.listaLineeProduttive[i].descrizione_linea_attivita+"</td><td><input type=\"hidden\" size=\"15\" name=\"dataInizioLinea"+numSecondarie+"\" id=\"dataInizioLinea"+numSecondarie+"\" value=\""+stab.listaLineeProduttive[i].dataInizioString+"\"  class=\"required\" placeholder=\"dd/MM/YYYY\"></td><td><input type=\"hidden\" size=\"15\" name=\"dataFineLinea"+numSecondarie+"\" id=\"dataFineLinea"+numSecondarie+"\" value=\""+stab.listaLineeProduttive[i].dataFineString+"\" placeholder=\"dd/MM/YYYY\"></td></tr></table><hr>");
	}
		
	
	
	
	if (stab.operatore.partitaIva==$('input[name="partitaIva"]').val() &&  $( "#methodRequest" ).val()=='cambioTitolarita')
	{
	
	$('input[name="ragioneSociale"]').prop("readonly", false);
  	$('input[name="domicilioDigitale"]').prop("readonly", false);
  	$('input[name="comuneNascitainput"]').prop("readonly", false);
  	$('input[name="nome"]').prop("readonly", false);
  	$('input[name="cognome"]').prop("readonly", false);
  	$('input[name="addressLegaleCountryinput"]').prop("readonly", false);
  	$('input[name="addressLegaleCityinput"]').prop("readonly", false);
  	$('input[name="addressLegaleLine1input"]').prop("readonly", false);
  	$('input[name="toponimoResidenza"]').prop("readonly", false);
  	$('input[name="civicoResidenza"]').prop("readonly", false);
  	$('input[name="capResidenza"]').prop("readonly", false);
	$('#calcolaCAPbtn_res').show();
	$('input[name="searchcodeIdprovinciainput"]').prop("readonly", false);
	$('input[name="searchcodeIdComuneinput"]').prop("readonly", false);
	$('input[name="viainput"]').prop("readonly", false);
	$('input[name="toponimoSedeLegale"]').prop("readonly", false);
	$('input[name="civicoSedeLegale"]').prop("readonly", false);
	$('#calcolaCAPbtn_leg').show();
	document.getElementById("idOperatore").value="-1";
	$('#calcoloCF').show();	
	alert("Indicaare che tipi di Variazione si vuole apportare all'impresa");
	 var options = getOptions(wizard);
     var  state = getState(wizard);
     goToPreviousStep(wizard, options, state);
	
	
	}
	
	
}
	
function selezionaStabilimento(i){
	var msgHD="ATTENZIONE! Uno o più campi obbligatori dello stabilimento non sono presenti, contattare l'helpdesk.";
	
	var stab = resultStab[i];
	document.getElementById("test2").src = "./schede_centralizzate/iframe.jsp?objectId="+stab.idStabilimento+"&objectIdName=stab_id&tipo_dettaglio=15"
	$('input[name="searchcodeIdprovinciaStabinput"]').val(stab.sedeOperativa.descrizione_provincia);
	$("#searchcodeIdprovinciaStab").append("<option value='"+stab.sedeOperativa.idProvincia+ "' selected='selected'>"+stab.sedeOperativa.descrizione_provincia+"</option>');");
	if($('input[name="searchcodeIdprovinciaStabinput"]').val()!="")
		$('input[name="searchcodeIdprovinciaStabinput"]').prop("readonly", true);
  	else
  		if( $('input[name="searchcodeIdprovinciaStabinput"]').prop('required')){
  			alert(msgHD); location.href='OpuStab.do?command=Default';		
  		}

	$('input[name="searchcodeIdComuneStabinput"]').val(stab.sedeOperativa.descrizioneComune);
	$("#searchcodeIdComuneStab").append("<option value='"+stab.sedeOperativa.comune+ "' selected='selected'>"+stab.sedeOperativa.comune+"</option>');");
	if($('input[name="searchcodeIdComuneStabinput"]').val()!="")
		$('input[name="searchcodeIdComuneStabinput"]').prop("readonly", true);
  	else
  		if( $('input[name="searchcodeIdComuneStabinput"]').prop('required')){
  			alert(msgHD); location.href='OpuStab.do?command=Default';		
  		}


	$('input[name="viaStabinput"]').val(stab.sedeOperativa.via);
	$("#viaStab").append("<option value='"+stab.sedeOperativa.idIndirizzo+ "' selected='selected'>"+stab.sedeOperativa.via+"</option>');");
	if($('input[name="viaStabinput"]')!="")
		$('input[name="viaStabinput"]').prop("readonly", true);
  	else
  		if( $('input[name="viaStabinput"]').prop('required')){
  			alert(msgHD); location.href='OpuStab.do?command=Default';		
  		}


	$('input[name="toponimoSedeOperativa"]').val(stab.sedeOperativa.toponimo);
	$('input[name="civicoSedeOperativa"]').val(stab.sedeOperativa.civico);
	if($('input[name="toponimoSedeOperativa"]').val()!="")
		$('input[name="toponimoSedeOperativa"]').prop("readonly", true);
	

	$('input[name="capStab"]').val(stab.sedeOperativa.cap);	
	if($('input[name="capStab"]').val()!=""){
		$('input[name="capStab"]').prop("readonly", true);
		$('#calcolaCAPbtn_stab').hide();
	}
  	else
  		if( $('input[name="capStab"]').prop('required')){
  			alert(msgHD); location.href='OpuStab.do?command=Default';		
  		}

	
	//alert(stab.tipoAttivita);

	$('select[name="tipoAttivita"]').val(stab.tipoAttivita);
	if($('#tipoAttivita').val()!="")
		$('#tipoAttivita').prop("readonly", true);
	

	
	
	//alert(stab.tipoCarattere);
		$('select[name="tipoCarattere"]').val(stab.tipoCarattere);
		if($('#tipoCarattere').val()!="")
			$('#tipoCarattere').prop("readonly", true);
	

	$('#dataInizio').val(stab.dataInizioAttivitaString);
	if($('#dataInizio').val()!="")
		$('#dataInizio').prop("readonly", true);
	

	$('#dataFine').val(stab.dataFineAttivitaString);
	if($('#dataFine').val()!="")
		$('#dataFine').prop("readonly", true);
  	else
  		if( $('#dataFine').prop('required')){
  			alert(msgHD); location.href='OpuStab.do?command=Default';		
  		}
	

	$('#latStab').val(stab.sedeOperativa.latitudine);
	if($('#latStab').val()!="")
		$('#latStab').prop("readonly", true);
	

	$('#longStab').val(stab.sedeOperativa.longitudine);
	if($('#longStab').val()!="")
		$('#longStab').prop("readonly", true);
	

	$('#civicoSedeOperativa').val(stab.sedeOperativa.civico);
	if($('#civicoSedeOperativa').val()!="")
		$('#civicoSedeOperativa').prop("readonly", true);
  	else
  		if( $('input[name="ragioneSociale"]').prop('required')){
  			alert(msgHD); location.href='OpuStab.do?command=Default';		
  		}
	
	

	$("#lineeEsistenti").html("");
	for(i=0;i<stab.listaLineeProduttive.length;i++){
		numSecondarie+=1;
	 	$("#lineeEsistenti").append("<table  style=\"width: 100%;\"><tr><td width=\"60%\"><input type =\"hidden\" name=\"idLineaProduttiva\" value = \""+stab.listaLineeProduttive[i].idRelazioneAttivita+"\">"+stab.listaLineeProduttive[i].descrizione_linea_attivita+"</td><td><input type=\"text\" size=\"15\" name=\"dataInizioLinea"+numSecondarie+"\" id=\"dataInizioLinea"+numSecondarie+"\" value=\""+stab.listaLineeProduttive[i].dataInizioString+"\"  class=\"required\" placeholder=\"dd/MM/YYYY\"></td><td><input type=\"text\" size=\"15\" name=\"dataFineLinea"+numSecondarie+"\" id=\"dataFineLinea"+numSecondarie+"\" value=\""+stab.listaLineeProduttive[i].dataFineString+"\" placeholder=\"dd/MM/YYYY\"></td></tr></table><hr>");
	 	$('#dataInizioLinea'+numSecondarie).datepick({dateFormat: 'dd/mm/yyyy',   maxDate: 0, showOnFocus: false, showTrigger: '#calImg'}); 
	 	$('#dataFineLinea'+numSecondarie).datepick({dateFormat: 'dd/mm/yyyy', showOnFocus: false, showTrigger: '#calImg',onClose: controlloDate}); 			
	}
	$("#idStabilimento").val(stab.idStabilimento);
	$("#idStabilimentoAdd").val(stab.idStabilimento);
	$('#dialogsuaplistastab').dialog('close');
	
	
  	if($('#dataInizioLinea').val()!=""){
  		$('#dataInizioLinea').datepick('destroy'); 
  		$('#dataInizioLinea').prop("readonly", true);
  	}
  	var i=1;
    while(document.getElementById('dataInizioLinea'+i)!=null){
    		if($('#dataInizioLinea'+i).val()!=""){
    			$('#dataInizioLinea'+i).datepick('destroy'); 
    			$('#dataInizioLinea'+i).prop("readonly", true);
      	}
    	i++;
    }
    
}
	
function selezionaImpresa(i){
	var msgHD="ATTENZIONE! Uno o più campi obbligatori dell'impresa non sono presenti, contattare l'helpdesk.";
	
	var operatori = result.listaOperatori;
  	$('select[name="tipo_impresa"]').val(operatori[i].tipo_impresa);
  	onChangeTipoImpresa();
  	//alert(operatori[i].tipo_societa);
  	
	$('input[name="idOperatore"]').val(operatori[i].idOperatore);
  	$('input[name="ragioneSociale"]').val(operatori[i].ragioneSociale);
  	if($('input[name="ragioneSociale"]').val()!="")
		$('input[name="ragioneSociale"]').prop("readonly", true);
//   	else
//   		if( $('input[name="ragioneSociale"]').prop('required')){
//   			alert(msgHD); location.href='OpuStab.do?command=Default';		
//   		}
	
	
	$('input[name="partitaIva"]').val(operatori[i].partitaIva);
  	if($('input[name="partitaIva"]').val()!="")
		$('input[name="partitaIva"]').prop("readonly", true);
//   	else
//   		if( $('input[name="partitaIva"]').prop('required')){
//   			alert(msgHD); location.href='OpuStab.do?command=Default';
//   		}
	
	$('input[name="codFiscale"]').val(operatori[i].codFiscale);
  	if($('input[name="codFiscale"]').val()!="")
		$('input[name="codFiscale"]').prop("readonly", true);
//   	else
//   		if( $('input[name="codFiscale"]').prop('required')){
//   			alert(msgHD); location.href='OpuStab.do?command=Default';
//   		}
  	
  	
  	
  	
	
	$('input[name="domicilioDigitale"]').val(operatori[i].domicilioDigitale);
  	if($('input[name="domicilioDigitale"]').val()!="")
		$('input[name="domicilioDigitale"]').prop("readonly", true);
//   	else
//   		if( $('input[name="domicilioDigitale"]').prop('required')){
//   			alert(msgHD); location.href='OpuStab.do?command=Default';
//   		}
  	

	$('input[name="comuneNascitainput"]').val(operatori[i].rappLegale.comuneNascita);
	$("#comuneNascita").append("<option value='"+operatori[i].rappLegale.idComuneNascita+ "' selected='selected'>"+operatori[i].rappLegale.comuneNascita+"</option>');");
  	if($('input[name="comuneNascitainput"]').val()!="")
		$('input[name="comuneNascitainput"]').prop("readonly", true);
//   	else
//   		if( $('input[name="comuneNascitainput"]').prop('required')){
//   			alert(msgHD); location.href='OpuStab.do?command=Default';
//   		}
  	

	$('input[name="nome"]').val(operatori[i].rappLegale.nome);
  	if($('input[name="nome"]').val()!="")
		$('input[name="nome"]').prop("readonly", true);
//   	else
//   		if( $('input[name="nome"]').prop('required')){
//   			alert(msgHD); location.href='OpuStab.do?command=Default';
//   		}

  	$('input[name="cognome"]').val(operatori[i].rappLegale.cognome);
  	if($('input[name="cognome"]').val()!="")
		$('input[name="cognome"]').prop("readonly", true);
//   	else
//   		if( $('input[name="cognome"]').prop('required')){
//   			alert(msgHD); location.href='OpuStab.do?command=Default';
//   		}

  

  	$('input[name="codFiscaleSoggetto"]').val(operatori[i].rappLegale.codFiscale);
  	$('input[name="dataNascita"]').val(operatori[i].rappLegale.dataNascitaString);  	
  	if($('input[name="codFiscaleSoggetto"]').val()!=""){
		$('input[name="codFiscaleSoggetto"]').prop("readonly", true);
		$('#calcoloCF').hide();	
		
  		$('#nazioneNascita').attr("disabled", true); 
  		$('#nazioneNascita_td').html("<input type='text' readonly='readonly' size='50' value='Italia'><input type='hidden' name='nazioneNascita' id='nazioneResidenza' value='106'>"); 

  	}
//   	else
//   		if( $('input[name="codFiscaleSoggetto"]').prop('required')){
//   			alert(msgHD); location.href='OpuStab.do?command=Default';
//   		}


  	$('input[name="nazioneResidenza"]').val(operatori[i].rappLegale.indirizzo.nazione);
  	if(operatori[i].rappLegale.indirizzo.nazione!=""){
  		var val_id=$('#nazioneResidenza').val(); 
  		var val=$('#nazioneResidenza').text(); 
  		//$('#nazioneResidenza').attr("disabled", true); 
  		//$('#nazioneResidenza_td').html("<input type='text' readonly='readonly' size='50' value='"+val_id+"'><input type='hidden' name='nazioneResidenza' id='nazioneResidenza' value='"+val+"'>"); 
  	}


  	$('input[name="addressLegaleCountryinput"]').val(operatori[i].rappLegale.indirizzo.descrizione_provincia);
  	$("#addressLegaleCountry").append("<option value='"+operatori[i].rappLegale.indirizzo.idProvincia+ "' selected='selected'>"+operatori[i].rappLegale.indirizzo.descrizione_provincia+"</option>');");
  	if($('input[name="addressLegaleCountryinput"]').val()!="")
		$('input[name="addressLegaleCountryinput"]').prop("readonly", true);
//   	else
//   		if( $('input[name="addressLegaleCountryinput"]').prop('required')){
//   			alert(msgHD); location.href='OpuStab.do?command=Default';
//   		}


  	
  	
  	$('input[name="addressLegaleCityinput"]').val(operatori[i].rappLegale.indirizzo.descrizioneComune);
	$("#addressLegaleCity").append("<option value='"+operatori[i].rappLegale.indirizzo.comune+ "' selected='selected'>"+operatori[i].rappLegale.indirizzo.descrizioneComune+"</option>');");
  	if($('input[name="addressLegaleCityinput"]').val()!="")
		$('input[name="addressLegaleCityinput"]').prop("readonly", true);
//   	else
//   		if( $('input[name="addressLegaleCityinput"]').prop('required')){
//   			alert(msgHD); location.href='OpuStab.do?command=Default';
//   		}


	$('input[name="addressLegaleLine1input"]').val(operatori[i].rappLegale.indirizzo.via);
  	$("#addressLegaleLine1").append("<option value='"+operatori[i].rappLegale.indirizzo.idIndirizzo+ "' selected='selected'>"+operatori[i].rappLegale.indirizzo.via+"</option>');");
  	if($('input[name="addressLegaleLine1input"]').val()!="")
		$('input[name="addressLegaleLine1input"]').prop("readonly", true);
//   	else
//   		if( $('input[name="addressLegaleLine1input"]').prop('required')){
//   			alert(msgHD); location.href='OpuStab.do?command=Default';
//   		}


	$('input[name="toponimoResidenza"]').val(operatori[i].rappLegale.indirizzo.toponimo);
  	if($('input[name="toponimoResidenza"]').val()!="")
		$('input[name="toponimoResidenza"]').prop("readonly", true);



 	$('input[name="civicoResidenza"]').val(operatori[i].rappLegale.indirizzo.civico);
  	if($('input[name="civicoResidenza"]').val()!="")
		$('input[name="civicoResidenza"]').prop("readonly", true);
//   	else
//   		if( $('input[name="civicoResidenza"]').prop('required')){
//   			alert(msgHD); location.href='OpuStab.do?command=Default';
//   		}


 	$('input[name="capResidenza"]').val(operatori[i].rappLegale.indirizzo.cap);
  	if($('input[name="capResidenza"]').val()!=""){
		$('input[name="capResidenza"]').prop("readonly", true);
		$('#calcolaCAPbtn_res').hide();
  	}
//   	else
//   		if( $('input[name="capResidenza"]').prop('required')){
//   			alert(msgHD); location.href='OpuStab.do?command=Default';
//   		}


	$('input[name="nazioneSedeLegale"]').val(operatori[i].sedeLegaleImpresa.nazione);
  	if(operatori[i].sedeLegaleImpresa.nazione!=""){
  		var val_id=$('#nazioneSedeLegale').val(); 
  		var val=$('#nazioneSedeLegale').text(); 
  		//$('#nazioneSedeLegale').attr("disabled", true); 
  		//$('#nazioneSedeLegale_td').html("<input type='text' readonly='readonly' size='50' value='"+val_id+"'><input type='hidden' name='nazioneSedeLegale' id='nazioneSedeLegale' value='"+val+"'>"); 
  		}



  	$('input[name="searchcodeIdprovinciainput"]').val(operatori[i].sedeLegaleImpresa.descrizione_provincia);
  	$("#searchcodeIdprovincia").append("<option value='"+operatori[i].sedeLegaleImpresa.idProvincia+ "' selected='selected'>"+operatori[i].sedeLegaleImpresa.descrizione_provincia+"</option>');");
  	if($('input[name="searchcodeIdprovinciainput"]').val()!="")
		$('input[name="searchcodeIdprovinciainput"]').prop("readonly", true);
//   	else
//   		if( $('input[name="searchcodeIdprovinciainput"]').prop('required')){
//   			alert(msgHD); location.href='OpuStab.do?command=Default';
//   		}

  
	
	
	$('input[name="searchcodeIdComuneinput"]').val(operatori[i].sedeLegaleImpresa.descrizioneComune);
  	$("#searchcodeIdComune").append("<option value='"+operatori[i].sedeLegaleImpresa.comune+ "' selected='selected'>"+operatori[i].sedeLegaleImpresa.comune+"</option>');");
  	if($('input[name="searchcodeIdComuneinput"]').val()!="")
		$('input[name="searchcodeIdComuneinput"]').prop("readonly", true);
//   	else
//   		if( $('input[name="searchcodeIdComuneinput"]').prop('required')){
//   			alert(msgHD); location.href='OpuStab.do?command=Default';
//   		}


	$('input[name="viainput"]').val(operatori[i].sedeLegaleImpresa.via);
	$("#via").append("<option value='"+operatori[i].sedeLegaleImpresa.idIndirizzo+ "' selected='selected'>"+operatori[i].sedeLegaleImpresa.via+"</option>');");
  	if($('input[name="viainput"]').val()!="")
		$('input[name="viainput"]').prop("readonly", true);
//   	else
//   		if( $('input[name="viainput"]').prop('required')){
//   			alert(msgHD); location.href='OpuStab.do?command=Default';
//   		}


	$('input[name="toponimoSedeLegale"]').val(operatori[i].sedeLegaleImpresa.toponimo);
  	if($('input[name="toponimoSedeLegale"]').val()!="")
		$('input[name="toponimoSedeLegale"]').prop("readonly", true);



 	$('input[name="civicoSedeLegale"]').val(operatori[i].sedeLegaleImpresa.civico);
  	if($('input[name="civicoSedeLegale"]').val()!="")
		$('input[name="civicoSedeLegale"]').prop("readonly", true);
//   	else
//   		if( $('input[name="civicoSedeLegale"]').prop('required')){
//   			alert(msgHD); location.href='OpuStab.do?command=Default';
//   		}


 	$('input[name="presso"]').val(operatori[i].sedeLegaleImpresa.cap);	
  	if($('input[name="presso"]').val()!=""){
		$('input[name="presso"]').prop("readonly", true);
		$('#calcolaCAPbtn_leg').hide();
  	}
//   	else
//   		if( $('input[name="presso"]').prop('required')){
//   			alert(msgHD); location.href='OpuStab.do?command=Default';
//   		}
  	
  	$('select[name="tipo_societa"]').val(operatori[i].tipo_societa);


  	$('input[name="sovrascrivi"]').val("no");
  	$('#dialogsuap').dialog('close');
}
  
function selezionaOperazione(tipo){
        $( "#methodRequest" ).val(tipo);
        $("#operazioneScelta").val(tipo);
        if (tipo=='cambioTitolarita'){
                $('#operazione').html("<h1><center>CAMBIO TITOLARITA</center></h1>");
                $('#secondarie').hide();
                $('#setAttivitaPrincipale').hide();
                $('#lineeEsistenti').show();
                $("#numReg").show();
                $('#datiIndirizzoStab').hide();
                document.getElementById('validatelp').value="true";
                var options = getOptions(wizard);
                var  state = getState(wizard);
                goToNextStep(wizard, options, state);
                
        }else{
                if (tipo=='cessazione'){
                        $('#operazione').html("<h1><center>CESSAZIONE STABILIMENTO O LINEE PRODUTTIVE : DATA CESSAZIONE STABILIMENTO <input type =\"text\" name = \"dataFineAttivitaCessazione\" id = \"dataFineAttivitaCessazione\"></center></h1>");
                          $('#dataFineAttivitaCessazione').datepick({dateFormat: 'dd/mm/yyyy', showOnFocus: false, showTrigger: '#calImg'}); 
                        $('#secondarie').hide();
                          $('#setAttivitaPrincipale').hide();
                          $('#lineeEsistenti').show();
                        $("#numReg").show();
                          $('#datiIndirizzoStab').hide();
                         var options = getOptions(wizard);
                    var  state = getState(wizard);
                    $("#dataInizioLinea").prop("readonly",true);
                          var indice=1;
                          while(document.getElementById("dataInizioLinea"+indice)!=null){
                                $("#dataInizioLinea"+indice).prop("readonly",true);
                                  indice++;
                           }
                          document.getElementById('validatelp').value="true";
                    goToNextStep(wizard, options, state);
                    
                  }else{
                        if (tipo=='ampliamento'){
                                $('#operazione').html("<h1><center>AMPLIAMENTO LINEE PRODUTTIVE</center></h1>");
                                $('#secondarie').show();
                                  $('#setAttivitaPrincipale').hide();
                                  $('#lineeEsistenti').show();
                                  //$("#numReg").hide();
                                    $("#numReg").show();
                                  //$('#datiIndirizzoStab').show();
                                   $('#datiIndirizzoStab').hide();
                                var options = getOptions(wizard);
                            var  state = getState(wizard);
                            document.getElementById('validatelp').value="true";
                            goToNextStep(wizard, options, state);
                            
                            
                          }else{
                                if (tipo=='new'){
                                          $("#numReg").hide();
                                        $('#datiIndirizzoStab').show();
                                          $('#operazione').html("<h1><center>NUOVA Richiesta</center></h1>");
                                          $('#secondarie').show();
                                          $('#setAttivitaPrincipale').show();
                                          $('#lineeEsistenti').hide();
                                          var options = getOptions(wizard);
                                    var  state = getState(wizard);
                                    document.getElementById('validatelp').value="false";
                                    goToNextStep(wizard, options, state);
                                  }
                        }
                }
        }
}
          
/*AUTOCOMPLETAMENTO PER GLI INDIRIZZI*/          
$(function(){
        //$( "#searchcodeIdComune" ).combobox();
    //  $( "#partitaIva" ).combobox();
    $( "#addressLegaleCountry" ).combobox();
    $( "#addressLegaleCity" ).combobox();
    $( "#comuneNascita" ).combobox();

    $( "#searchcodeIdComune" ).combobox();
    $( "#searchcodeIdprovincia" ).combobox();

    $( "#searchcodeIdComuneStab" ).combobox();
    $( "#searchcodeIdprovinciaStab" ).combobox();
    
 	$( "#addressLegaleLine1" ).combobox();
    $( "#viaStab" ).combobox();
    $( "#via" ).combobox();
});

function resizeIframe(iframe){
        alert(iframe.contentWindow.document.body.scrollHeight);
    iframe.height = iframe.contentWindow.document.body.scrollHeight + "px";
}
 
function cancellaFile(element,url,indice){
        $.ajax({
            url:url,
        async: false,
        success :  function(data){
                // Your Code here
                        alert("Allegato Eliminato Correttamente!")
            document.getElementById("fileAllegato"+indice).innerHTML="";
                        document.getElementById("linkallegato"+indice).style.display="";
                        indice=1;
                        var allegatoFile=false;
                        while(document.getElementById("fileAllegato"+indice)!=null){
                                if (document.getElementById("fileAllegato"+indice).innerHTML!=''){
                                           allegatoFile=true ;
                                           break;
                                   }
                                   indice++;
                        }
                        if (allegatoFile==false){
                                document.getElementById("documentazione_parziale").value="0";
                        }
                }
    })
}

var campoLat;
var campoLong;

function showCoordinate(address,city,prov,cap,campo_lat,campo_long){
        campoLat = campo_lat;
        campoLong = campo_long;
        Geocodifica.getCoordinate(address,city,prov,cap,'','','',setGeocodedLatLonCoordinate);
}
        
function setGeocodedLatLonCoordinate(value){
        campoLat.value = value[1];;
        campoLong.value =value[0];
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

function gestisciCFUguale(cb){
        var iv = document.getElementById("partitaIva").value;
        var cf = document.getElementById("codFiscale");
        if (cb.checked)
                cf.value = iv;
        else
                cf.value = '';
}

function controlloPiva(){
        var nazione = getSelectedText('nazioneSedeLegale');
        var iva = document.getElementById("partitaIva");
        if (nazione == "Italia"){
                iva.setAttribute("min", 11);
                iva.setAttribute("maxlength", 11);
        }else{
                iva.setAttribute("min", 4);
                iva.setAttribute("maxlength", 50);
        }
}

function checkResidenza(sel){
	var prov = document.getElementById("addressLegaleCountry").value;
	
	if (prov!='61' && prov!='62' && prov!='63' && prov!='64' && prov!='65'){
		alert("Impossibile selezionare il tipo att.Mobile con una residenza rappresentante legale fuori regione.")
		sel.value = -1;
	}
}
</script>


<table class="trails" cellspacing="0">
<tr>
<td width="100%">
<a href="OpuStab.do?command=SearchForm"><dhv:label name="">Ricerca Anagrafica stabilimenti</dhv:label></a> >
<a href="OpuStab.do?command=Default"><dhv:label name="">Anagrafica stabilimenti</dhv:label></a> >
</td>
</tr>
</table>

<div class="info">
In questa sezione puoi effettuare più operazioni a livello SUAP. Per ogni azione, il sistema ti 
propone un bottone richiamando tra parentesi tonda il paragrafo della
<a href="#" onClick="window.open('http://www.gisacampania.it/manualisuap/Allegato%20D.G.R.C.%20318.2015.pdf')"> Delibera 318/15<img src="gestione_documenti/images/pdf_icon.png" width="25px"/>
</a>
</div>


<h3><%=newStabilimento.getTipoInserimentoScia() == newStabilimento.TIPO_SCIA_RICONOSCIUTI
                                        ? "Riconoscimento"
                                        : newStabilimento.getStato() == 0 ? "Inserimento Stabilimento Non Registrato" : "Inserimento"%></h3>
<br>
<br>
<form id="example-advanced-form" name="addstabilimento" action="#" enctype="multipart/form-data">
        <input type="hidden" name="stato" id="stato" value="<%=newStabilimento.getStato()%>"> 
        <input type="hidden" name="tipoInserimentoScia" id="tipoInserimentoScia" value="<%=newStabilimento.getTipoInserimentoScia()%>"> 
        <input type="hidden" name="sovrascrivi" id="sovrascrivi" value="n.d">
        <input type="hidden" name="idOperatore" id="idOperatore" value="n.d">
        <input type="hidden" name="idStabilimento" id="idStabilimento" value="<%=newStabilimento.getIdStabilimento()%>"> 
        <input type="hidden" name="idStabilimentoAdd" id="idStabilimentoAdd" value="<%=newStabilimento.getIdStabilimento()%>">
        <input type="hidden" name="operazioneScelta" id="operazioneScelta" value="">
        <input type="hidden" name="pratica_completa" id="pratica_completa" value="0">
        <input type="hidden" name="documentazione_parziale" id="documentazione_parziale" value="0"> 
        <input type="hidden" name="methodRequest" id="methodRequest" value="new">
        <div style="display: none;"> 
            &nbsp;&nbsp;<img id="calImg" src="images/cal.gif" alt="Popup" class="trigger"> 
        </div>
        <h3>OPERAZIONE</h3>
       <!--  <fieldset> -->
                <!-- <legend>INDICARE IL TIPO DI OPERAZIONE CHE SI VUOLE ESEGUIRE</legend> -->
              
				<%if((fissa!= null && fissa.equals("true") || newStabilimento.getTipoInserimentoScia() == newStabilimento.TIPO_SCIA_RICONOSCIUTI)) { %>              
             		<%@ include file="bottoni_scia_fissa.jsp"%>
				<% } else {%>
					<%@ include file="bottoni_scia_mobili.jsp"%>
				<% } %>
              
                <!-- </fieldset> -->
        <h3>TIPO IMPRESA</h3>
        <fieldset>
                <legend>INDICARE IL TIPO DI IMPRESA CHE SI VUOLE INSERIRE</legend>
                <%
                        TipoImpresaList.setJsEvent("onchange=onChangeTipoImpresa();");
                %>
                <%=TipoImpresaList.getHtmlSelect("tipo_impresa", -1)%>
                <hr>
        </fieldset>
        <h3>IMPRESA</h3>
        <fieldset>
                <fieldset>
                        <legend>DATI IMPRESA</legend>
                        <table style="height: 100%; width: 100%">
                                <tr id="tipo_societaTR">
                                        <td nowrap id="trTipoSocieta">TIPO SOCIETA'</td>
                                        <td><%=TipoSocietaList.getHtmlSelect("tipo_societa", -1)%></td>
                                </tr>
                                <tr>
                                        <td nowrap>
                                                <p id="labelRagSoc">
                                                        DITTA/<br>DENOMINAZIONE/<br>RAGIONE SOCIALE
                                                </p>
                                        </td>
                                        <td><input type="text" size="70" id="ragioneSociale"
                                                class="required" name="ragioneSociale"></td>
                                </tr>
                                <tr>
                                        <td nowrap>PARTITA IVA</td>
                                        <td><input type="text" size="70" min="11" maxlength="11"
                                                id="partitaIva" name="partitaIva"></td>
                                </tr>
                                <tr id="codFiscaleTR">
                                        <td nowrap>CODICE FISCALE<br>IMPRESA
                                        </td>
                                        <td><input type="text" size="70" maxlength="16"
                                                id="codFiscale" name="codFiscale" class="required"> <input
                                                type="checkbox" id="codFiscaleUguale"
                                                onClick="gestisciCFUguale(this)" /> Uguale alla P.IVA</td>
                                </tr>
                                <tr id="codFiscaleTR">
                                        <td nowrap>DOMICILIO DIGITALE(PEC)</td>
                                        <td><input type="text" size="100" maxlength="300"
                                                id="domicilioDigitale" class="required" name="domicilioDigitale">
                                        </td>
                                </tr>
                                <tr>
                                        <td>NOTE</td>
                                        <td><textarea name="noteImpresa" cols="50" rows="5"
                                                        id="noteImpresa" value=""></textarea></td>
                                </tr>
                        </table>
                </fieldset>
                <fieldset>
                        <legend>DATI TITOLARE/LEGALE RAPPRESENTANTE</legend>
                        <table style="height: 100%; width: 100%">
                                <tr>
                                        <td>NOME</td>
                                        <td><input type="text" size="70" id="nome" name="nome"
                                                class="required"></td>
                                </tr>
                                <tr>
                                        <td><label for="cognome-2">COGNOME </label></td>
                                        <td><input type="text" size="70" id="cognome" name="cognome"
                                                class="required"></td>
                                </tr>
                                <tr>
                                        <td><label for="sesso-2">SESSO </label></td>
                                        <td><div class="test">
                                                        <input type="radio" name="sesso" id="sesso1" value="M"
                                                                checked="checked" class="required css-radio"> <label
                                                                for="sesso1" class="css-radiolabel radGroup2">M</label> <input
                                                                type="radio" name="sesso" id="sesso2" value="F"
                                                                class="required css-radio"> <label for="sesso2"
                                                                class="css-radiolabel radGroup2">F</label>
                                                </div></td>
                                </tr>
                                <tr>
                                        <td><label for="dataN-2">DATA NASCITA </label></td>
                                        <td><input type="text" size="15" name="dataNascita"
                                                id="dataNascita2" class="required" placeholder="dd/MM/YYYY">&nbsp;&nbsp;
                                        </td>
<script>
$(function() {
        $('#dataNascita2').datepick({dateFormat: 'dd/mm/yyyy',  maxDate: 0,  showOnFocus: false, showTrigger: '#calImg'});// , showOnFocus: false, showTrigger: '#calImg'}); 
});
</script>
                                </tr>
                                <tr>
                                        <td><label for="nazioneN-2">NAZIONE NASCITA</label></td>
                                        <td>
                                                <%
                                                        NazioniList
                                                                        .setJsEvent("onchange=\"abilitaCodiceFiscale('nazioneNascita');sbloccoProvincia('nazioneNascita',null,'comuneNascita',null)\"");
                                                %>
                                                <%=NazioniList.getHtmlSelect("nazioneNascita", 106)%></td>
                                </tr>
                                <tr>
                                        <td nowrap>COMUNE NASCITA</td>
                                        <td><select name="comuneNascita" id="comuneNascita"
                                                class="required">
                                                        <option value="">SELEZIONA COMUNE</option>
                                        </select> <input type="hidden" name="comuneNascitaTesto"
                                                id="comuneNascitaTesto" /></td>
                                </tr>
                                <tr>
                                        <td nowrap>CODICE FISCALE</td>
                                        <td><input type="text" name="codFiscaleSoggetto"
                                                readonly="readonly" id="codFiscaleSoggetto" class="required" /></td>
                                </tr>
                                <tr>
                                        <td>&nbsp;</td>
                                        <td><input type="button" id="calcoloCF" class="newButtonClass"
                                                value="CALCOLA CODICE FISCALE"
                                                onclick="javascript:CalcolaCF(document.forms[0].sesso,document.forms[0].nome,document.forms[0].cognome,document.forms[0].comuneNascitainput,document.forms[0].dataNascita,'codFiscaleSoggetto')"></input>
                                        </td>
                                </tr>
                                <tr>
                                        <td><label for="nazioneN-2">NAZIONE RESIDENZA</label></td>
                                        <td>
                                                <%
                                                        NazioniList
                                                                        .setJsEvent("onchange=\"sbloccoProvincia('nazioneResidenza','addressLegaleCountry','addressLegaleCity','addressLegaleLine1')\"");
                                                %>
                                                <%=NazioniList.getHtmlSelect("nazioneResidenza", 106)%></td>
                                </tr>
                                <tr id="addressLegaleCountryTR">
                                        <td nowrap>PROVINCIA RESIDENZA</td>
                                        <td><select id="addressLegaleCountry" class="required"
                                                name="addressLegaleCountry">
                                                        <option value="">SELEZIONA PROVINCIA</option>
                                        </select> <input type="hidden" id="addressLegaleCountryTesto"
                                                name="addressLegaleCountryTesto" /></td>
                                </tr>
                                <tr>
                                        <td nowrap>COMUNE RESIDENZA</td>
                                        <td><select name="addressLegaleCity" id="addressLegaleCity"
                                                class="required">
                                                        <option value="">SELEZIONA COMUNE</option>
                                        </select> <input type="hidden" name="addressLegaleCityTesto"
                                                id="addressLegaleCityTesto" /></td>
                                </tr>
                                <tr>
                                        <td>INDIRIZZO RESIDENZA</td>
                                        <td>
                                                <table class="noborder">
                                                        <tr>
                                                                <td><%=ToponimiList.getHtmlSelect("toponimoResidenza", "VIA")%>
                                                                </td>
                                                                <td><input type="text" name="addressLegaleLine1"
                                                                        id="addressLegaleLine1" class="required">
                                                                            
                                                               </td>
                                                                <td><input type="text" name="civicoResidenza"
                                                                        id="civicoResidenza" size="5" placeholder="NUM." maxlength="15"
                                                                        required="required"></td>
                                                                <td><input type="text" name="capResidenza"
                                                                        id="capResidenza" size="4" placeholder="CAP" maxlength="5"
                                                                        required="required"
                                                                        onfocus="chkCap(document.getElementById('addressLegaleCity').value, 'capResidenza')"  title="DATI TITOLARE/LEGALE RAPPRESENTANTE: CAP INDIRIZZO RESIDENZA non valido. Tornare indietro e correggere il campo."
                                                                        > <input type="button"
                                                                        value="Calcola CAP"
                                                                        onclick="calcolaCap(document.getElementById('addressLegaleCity').value, document.getElementById('toponimoResidenza').value, document.getElementById('addressLegaleLine1').value, 'capResidenza');" />
                                                                </td>
                                                        </tr>
                                                </table>
                                        </td>
                                </tr>
                                <tr>
                                        <td>DOMICILIO DIGITALE<br>(PEC)
                                        </td>
                                        <td><input type="text" size="70" name="domicilioDigitalePecSF">
                                        </td>
                                </tr>
                        </table>
                </fieldset>
                <fieldset id="setSedeLegale">
                        <legend>DATI SEDE LEGALE</legend>
                        <table style="height: 100%; width: 100%">
                                <tr>
                                        <td></td>
                                        <td><input type="checkbox" onclick="copiaDaResidenza()"
                                                id="checkSeddeLegale"> SPUNTARE SE L'INDIRIZZO DI
                                                RESIDENZA COINCIDE CON LA SEDE LEGALE</td>
                                </tr>
                                <tr>
                                        <td><label for="nazioneN-2">NAZIONE</label></td>
                                        <td>
                                                <%
                                                        NazioniList
                                                                        .setJsEvent("onchange=\"sbloccoProvincia('nazioneSedeLegale','searchcodeIdprovincia','searchcodeIdComune','via')\"");
                                                %>
                                                <%=NazioniList.getHtmlSelect("nazioneSedeLegale", 106)%></td>
                                </tr>
                                <tr id="searchcodeIdprovinciaTR">
                                        <td>PROVINCIA</td>
                                        <td><select name="searchcodeIdprovincia"
                                                id="searchcodeIdprovincia" class="required">
                                                        <option value=""></option>
                                        </select> <input type="hidden" name="searchcodeIdprovinciaTesto"
                                                id="searchcodeIdprovinciaTesto" /></td>
                                </tr>
                                <tr>
                                        <td>COMUNE</td>
                                        <td><select name="searchcodeIdComune" id="searchcodeIdComune"
                                                class="required">
                                                        <option value=""></option>
                                        </select> <input type="hidden" name="searchcodeIdComuneTesto"
                                                id="searchcodeIdComuneTesto" /></td>
                                </tr>
                                <tr>
                                        <td>INDIRIZZO</td>
                                        <td>
                                                <table class="noborder">
                                                        <tr>
                                                                <td><%=ToponimiList.getHtmlSelect("toponimoSedeLegale", "VIA")%>
                                                                </td>
                                                                <td><input type="text" name="via" id="via" class="required" size="80">
                                                                               
                                                                </td>
                                                                <td><input type="text" name="civicoSedeLegale"
                                                                        id="civicoSedeLegale" size="5" placeholder="NUM."
                                                                        maxlength="15" required="required"></td>
                                                                <td><input type="text" name="presso" id="presso" size="4"
                                                                        placeholder="CAP" maxlength="5" required="required"
                                                                        onfocus="chkCap(document.getElementById('searchcodeIdComune').value, 'presso')"  title="DATI SEDE LEGALE: CAP INDIRIZZO non valido. Tornare indietro e correggere il campo."
                                                                        > <input
                                                                        type="button" value="Calcola CAP"
                                                                        onclick="calcolaCap(document.getElementById('searchcodeIdComune').value, document.getElementById('toponimoSedeLegale').value, document.getElementById('via').value, 'presso');" />
                                                                </td>
                                                        </tr>
                                                </table>
                                        </td>
                                </tr>
                        </table>
                </fieldset>
        </fieldset>
        <h3>STABILIMENTO</h3>
        <fieldset>
                <legend>DATI STABILIMENTO</legend>
                <table id="numReg" style="display: none">
                        <tr>
                                <td>NUMERO REGISTRAZIONE STABILIMENTO</td>
                                <td>
                                        <input type = "text" name = "numeroRegistrazione" id = "numeroRegistrazione" onchange="checkStabNumReg()" required="required">
                                </td>
                        </tr>
                        </table>
                        <table>
                        <tr>
                                <td>TIPO ATTIVITA</td>
                                <td>
                                        <%TipoAttivita.setRequired(true);
                                                TipoAttivita.setJsEvent("onchange='mostraDatiStabilimento(this.value);mostraAttivitaProduttive(\"attprincipale\",1,-1, false,"+newStabilimento.getTipoInserimentoScia()+"); checkResidenza(this);'");
                                        %> <%=TipoAttivita.getHtmlSelect("tipoAttivita", -1)%>
                                </td>
                        </tr>
                        <tr>
                                <td>CARATTERE</td>
                                <td>
                                        <%
                                        TipoCarattere.setRequired(true);
                                                TipoCarattere.setJsEvent("onchange=visualizzaData(this);");
                                        %> <%=TipoCarattere.getHtmlSelect("tipoCarattere", -1)%>
                                </td>
                        </tr>
                        
                         <tr style="display: none" id="trDataInizio">
                                <td>DATA INIZIO</td>
                                <td><input type="text" size="15" name="dataInizioAttivita"
                                        id="dataInizio" class="required" placeholder="dd/MM/YYYY" readonly>
                                </td>
                        </tr>
                        <tr style="display: none" id="trDataFine">
                                <td>DATA FINE</td>
                                <td><input type="text" size="15" name="dataFineAttivita"
                                        id="dataFine" class="required" value="" placeholder="dd/MM/YYYY" readonly></td>
                        </tr>
                        
                </table>
                <br> <br>
                <table id="datiIndirizzoStab" style="display: none">
                        <tr>
                                <td></td>
                                <td><input type="checkbox" onclick="copiaDaLegale()"
                                        id="checkSeddeOperativa"> SPUNTARE SE LA SEDE PRODUTTIVA
                                        COINCIDE CON LA SEDE LEGALE</td>
                        </tr>
                        <tr>
                                <td>PROVINCIA</td>
                                <td><select name="searchcodeIdprovinciaStab"
                                        id="searchcodeIdprovinciaStab" class="required">
                                                <option value=""></option>
                                </select> <input type="hidden" name="searchcodeIdprovinciaTestoStab"
                                        id="searchcodeIdprovinciaTestoStab" /></td>
                        </tr>
                        <tr>
                                <td>COMUNE</td>
                                <td><select name="searchcodeIdComuneStab"
                                        id="searchcodeIdComuneStab" class="required">
                                                <option value=""></option>
                                </select> <input type="hidden" name="searchcodeIdComuneTestoStab"
                                        id="searchcodeIdComuneTestoStab" /></td>
                        </tr>
                        <tr>
                                <td>INDIRIZZO</td>
                                <td>
                                        <table class="noborder">
                                                <tr>
                                                        <td><%=ToponimiList.getHtmlSelect("toponimoSedeOperativa", "VIA")%>
                                                        </td>
                                                        <td><input type="text" name="viaStab" id="viaStab" class="required">
                                                                       </td>

                                                        <td><input type="text" name="civicoSedeOperativa"
                                                                id="civicoSedeOperativa" required="required" placeholder="NUM."
                                                                size="4" maxlength="7"></td>
                                                        <td><input type="text" size="4" id="capStab" name="capStab"
                                                                maxlength="5" value="" required="required" placeholder="CAP"
                                                                onfocus="chkCap(document.getElementById('searchcodeIdComuneStab').value, 'capStat')"  title="DATI STABILIMENTO: CAP INDIRIZZO non valido. Tornare indietro e correggere il campo."
                                                                >
                                                                <input type="button" value="Calcola CAP"
                                                                onclick="calcolaCap(document.getElementById('searchcodeIdComuneStab').value, document.getElementById('toponimoSedeOperativa').value, document.getElementById('viaStab').value, 'capStab');" />
                                                        </td>
                                                        <td>
                                                                <!-- <input id="coord1button" type="button" value="Calcola Coordinate"
                                                            onclick="javascript:showCoordinate(getSelectedText('toponimoSedeOperativa')+' '+getSelectedText('viaStab')+', '+ getSelectedText('searchcodeIdComuneStab') + ', '+ getSelectedText('searchcodeIdprovinciaStab') + ' '+ document.forms['example-advanced-form'].capStab.value);" /> 
                                                            --> <input id="coord1button" type="button"
                                                                value="Calcola Coordinate"
                                                                onclick="javascript:showCoordinate(getSelectedText('toponimoSedeOperativa')+' '+document.getElementById('viaStab').value+', '+document.getElementById('civicoSedeOperativa').value, getSelectedText('searchcodeIdComuneStab'), getSelectedText('searchcodeIdprovinciaStab'), document.getElementById('capStab').value, document.getElementById('latStab'), document.getElementById('longStab'));" />
                                                        </td>
                                                </tr>
                                        </table>
                                </td>
                        </tr>
                        <tr>
                                <td>COORDINATE</td>
                                <td>LAT <input type="text" name="latStab" id="latStab" value=""
                                        class="required" onChange="controllaCoordinate(this, 'lat')" />
                                        LONG <input type="text" name="longStab" id="longStab" value=""
                                        class="required" onChange="controllaCoordinate(this, 'long')" />
                                </td>
                        </tr>
                        <tr>
                                <td>TELEFONO</td>
                                <td><input type="text" name="telefono"></td>
                        </tr>
                       
<script>
$(function() {
        $('#dataInizio').datepick({dateFormat: 'dd/mm/yyyy',  maxDate: 0,  showOnFocus: false, showTrigger: '#calImg'}); 
        $('#dataFine').datepick({dateFormat: 'dd/mm/yyyy', showOnFocus: false, showTrigger: '#calImg',onClose: controlloDate}); 
});
</script>
                        <tr>
                                <td>NOTE</td>
                                <td><textarea name="noteStab" cols="50" rows="5" id="noteStab"
                                                value=""></textarea></td>
                        </tr>
                </table>
             
             <!--  DATI SEDE MOBILE -->
	<!-- <%//@ include file="campi_mobile_add.jsp"%> -->

        </fieldset>
        <h3>LINEE DI ATTIVITA'</h3>
        <fieldset>
                <input type="hidden" value="false" id="validatelp" >
                <fieldset id="operazione">
                        <h1><center>NUOVA Richiesta</center></h1>
                </fieldset>
                <fieldset id="lineeEsistenti" style="display: none">
                        <legend>ATTIVITA PRESENTI NELLO STABILIMENTO</legend>
                </fieldset>
                <fieldset id="setAttivitaPrincipale">
                        <legend>INDICARE IL TIPO DI ATTIVITA</legend>
                        <table id="attprincipale" style="width: 100%;">
                        </table>
                        <table style="width: 100%;">
                                <tr id="trDataInizioLinea">
                                        <td>DATA INIZIO</td>
                                        <td><input type="text" size="15" name="dataInizioLinea"
                                                id="dataInizioLinea" class="required" placeholder="dd/MM/YYYY">
                                        </td>
                                </tr>
                                <tr id="trDataFineLinea">
                                        <td>DATA FINE</td>
                                        <td><input type="text" size="15" name="dataFineLinea"
                                                id="dataFineLinea" value="" placeholder="dd/MM/YYYY"></td>
                                </tr>
                        </table>
<script>
$(function() {
        $('#dataInizioLinea').datepick({dateFormat: 'dd/mm/yyyy',  maxDate: 0,  showOnFocus: false, showTrigger: '#calImg'}); 
        $('#dataFineLinea').datepick({dateFormat: 'dd/mm/yyyy', showOnFocus: false, showTrigger: '#calImg',  onClose: controlloDate         });

		

});
</script>
                </fieldset>
                <br>
                <br>
                <fieldset id="secondarie">
                        <legend>INDICARE IL TIPO DI ATTIVITA AGGIUNTIVE</legend>
                        <table style="width: 100%;">
                                <tr>
                                        <td width="50%" align="left">Esistono altre Attivita ?</td>
                                        <td align="left"><input type="button" value="Aggiungi"
                                                onclick="aggiungiRiferimentoTabella(<%=newStabilimento.getTipoInserimentoScia()%>)"></td>
                                </tr>
                        </table>
                        <br>
                        <br>
                </fieldset>
        </fieldset>
        <h3>ALLEGATI</h3>
        <fieldset>
                <legend>DOCUMENTAZIONE DA ALLEGARE</legend>
                <div id="documenti" class="documenti"></div>
        </fieldset>
        <h3>FORM</h3>
        <fieldset>
        
                <legend>SCHEDA STABILIMENTO</legend>
                <input type = "button" value="Ricarica Scheda" onclick="ricarica();" >
                
                <iframe height="650px" id="test2"
                        src="./schede_centralizzate/iframe.jsp?objectId=<%=newStabilimento.getIdStabilimento()%>&objectIdName=stab_id&tipo_dettaglio=15"
                        name="test"> </iframe>
                <div align="right">
                        <img src="images/icons/stock_print-16.gif" border="0"
                                align="absmiddle" height="16" width="16" /> <input type="button"
                                title="Stampa Ricevuta" value="Stampa Ricevuta" id ="stampa"
                                onClick="openRichiestaPDFOpu('<%=newStabilimento.getIdStabilimento()%>', '-1', '-1', '-1', '15');">
                </div>
        </fieldset>
</form>
<div id="dialogsuap"></div>
<div id="dialogsuapSceltaOperazione">
        <table>
                <tr>
                        <td><input type="button" class="yellowBigButton"
                                value="AMPLIAMENTO LINEE DI ATTIVITA"
                                onclick="selezionaOperazione('ampliamento')" width="100%"></td>
                </tr>
        </table>
        <hr>
        <table>
                <tr>
                        <td><input type="button" class="yellowBigButton"
                                value="CESSAZIONE STABILIMENTO / ATTIVITA"
                                onclick="selezionaOperazione('cessazione')" width="100%"></td>
                </tr>
        </table>
        <hr>
        <table>
                <tr>
                        <td><input type="button" class="yellowBigButton"
                                value="CESSAZIONE PER VARIAZIONE TITOLARITA"
                                onclick="alert('Attenzione! La variazione di titolarita consiste in: \n 1) Cessazione attuale stabilimento \n 2) Nuova Richiesta.');selezionaOperazione('cambioTitolarita')" width="100%">
                        </td>
                </tr>
        </table>
</div>
<div id="dialogsuaplistastab"></div>

<script>
function showPopUp(){
        $('#dialogsuap').dialog('open');
}
        
function closePopupStab(){
        $('#dialogsuaplistastab').dialog('close');
}
function closePopupCancellaIva(){
	$('#partitaIva').val("");
	$('#dialogsuap').dialog('close');
}
var resultStab ;

$(function(){
        $('input[name=searchcodeIdComuneStabinput]').change( function(){checkStab();});
        var that;
    $('input[name=partitaIva],input[name=codFiscale]').change( function() {
                   tipoImpresa= document.getElementById("tipo_impresa").value;
            if (tipoImpresa=='5' ||  tipoImpresa=='6'){
                // Get the other input
                that = $('input[name=partitaIva],input[name=codFiscale]').not($(this));
                // If the value of this input is not empty, set the required property of the other input to false
                if($(this).val().length) {
                    that.removeAttr("class");
                } else {
                    that.attr("class","required");
                    $(this).removeAttr("class");
                }
            }
            if ( $('input[name=partitaIva]').val()!='' || $('input[name=codFiscale]').val()!=''){
                           loadModalWindowCustom("Verifica Esistenza Impresa. Attendere");
                           $.ajax({
                                type: 'POST',
                              dataType: "json",
                              cache: false,
                             url: 'OpuSuapStab.do?command=VerificaEsistenza',
                                  data: ''+$('form[name=addstabilimento]').serialize(), 
                             success: function(msg) {
                                      result = msg ;
                                      if(msg.codiceErroreSuap =="1"){
                                              showPopUp();
                                      }else{
                                              if(msg.codiceErroreSuap =="2"){
                                                      var htmlText="" ; //msg[0].erroreSuap;
                                                      htmlText+='<table border="1"><tr><th>Denominazione</th><th>Partita IVa</th><th>Rappresentante Legale</th><th>Sede Legale</th><th></th></tr>'
                                                      var jsontext = JSON.stringify(msg);
                                                      if (msg.listaOperatori!=null && msg.listaOperatori.length>0){
                                                              for(i=0;i<msg.listaOperatori.length;i++){
                                                                     htmlText+="<tr><td>"+msg.listaOperatori[i].ragioneSociale+"</td><td>"+msg.listaOperatori[i].partitaIva+"</td><td>"+msg.listaOperatori[i].rappLegale.nome+" "+msg.listaOperatori[i].rappLegale.cognome+" "+msg.listaOperatori[i].rappLegale.codFiscale+ " "+msg.listaOperatori[i].rappLegale.indirizzo.descrizioneComune+" "+msg.listaOperatori[i].rappLegale.indirizzo.descrizione_provincia+" "+msg.listaOperatori[i].rappLegale.indirizzo.via+"</td><td>"+msg.listaOperatori[i].sedeLegaleImpresa.descrizioneComune+" "+msg.listaOperatori[i].sedeLegaleImpresa.descrizione_provincia+" "+msg.listaOperatori[i].sedeLegaleImpresa.via+"</td><td><input type='button' value='Seleziona' onclick='selezionaImpresa("+i+")'></td></tr>";
                                                              }
                                  		        			htmlText+="<tr><td><input type='button' value='ANNULLA' onclick='closePopupCancellaIva()'></td></tr></table>";
                                                            htmlText+="</table>";
                                                              $( "#dialogsuap" ).html(htmlText);
                                                               showPopUp();
                                                      }else{
                                                              alert(msg.erroreSuap);
                                                              document.getElementById("partitaIva").value="";
                                                      }
                                }
                                        }
                                              loadModalWindowUnlock();    
                               },
                            error: function (err) {
                                      alert('ko '+err.responseText);
                         }
                        });
                  }
        });
});

function controlloDate(){
        if(document.getElementById(this.id.replace("Fine","Inizio")).value==""){
                alert("ATTENZIONE! Inserire prima la data inizio linea.");        
                this.value="";
        }else{
                if(!confrontoDate(document.getElementById(this.id.replace("Fine","Inizio")).value,document.getElementById(this.id).value)){
                        alert("ATTENZIONE! La data fine deve essere maggiore della data inizio.");
                        this.value="";
                }
        }
}

function confrontoDate(data_iniziale ,data_finale){
        var arr1 = data_iniziale.split("/");
        var arr2 = data_finale.split("/");
        var d1 = new Date(arr1[2],arr1[1]-1,arr1[0]);
        var d2 = new Date(arr2[2],arr2[1]-1,arr2[0]);
        var r1 = d1.getTime();
        var r2 = d2.getTime();
        if (r1<=r2) 
                return true;
        else 
                return false;
        }
        
$(function(){       
$('#tipoCarattere').change( function(){
	if($('#tipoCarattere').val()=="1"){
		$('#trDataFineLinea').hide();
	}else{
		$('#trDataFineLinea').show();
	}
})
});


function ricarica()
{
document.getElementById("test2").src = "./schede_centralizzate/iframe.jsp?objectId="+document.getElementById("idStabilimento").value+"&objectIdName=stab_id&tipo_dettaglio=15";
;window.frames[0].location.reload(true);
}
</script>