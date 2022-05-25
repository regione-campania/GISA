<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.utils.web.LookupElement"%>
<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<jsp:useBean id="ToponimiList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="NazioniList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipoImpresaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipoSocietaList" class="org.aspcfs.utils.web.LookupList" scope="request" />


<script language="JavaScript" TYPE="text/javascript" SRC="javascript/gestoreCodiceFiscale.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.plus.js"></script>
<script type="text/javascript" src="javascript/jquery.plugin.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.picker.js"></script>
<script src="javascript/parsedate.js"></script>

<script  src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<script src="javascript/jquery-ui.js"></script>
<SCRIPT src="javascript/opuErrataCorrige.js"></SCRIPT>
<SCRIPT src="javascript/upload.js"></SCRIPT>
<SCRIPT src="javascript/suapUtil.js"></SCRIPT>

<script src="javascript/aggiuntaCampiEstesiScia.js"></script>
<link href="javascript/datepicker/jquery.datepick.css" rel="stylesheet">
<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/opu.css" />
<link rel="stylesheet" type="text/css" href="css/jquery.calendars.picker.css">

<script>

function sbloccoProvincia(nazioneFieldID,provinciaFieldID,comuneFieldID,indrizzoFieldID)
{
	if($("#"+nazioneFieldID+" option:selected").text()!='Italia')
	{
		if(($("#"+nazioneFieldID+" option:selected").val()=="-1"))
		{

		$('#nazioneSedeLegale option:selected').val("");
		}
		if (provinciaFieldID!=null)
			{
			
			
			document.getElementById("btnCap").setAttribute('disabled', 'disabled');
		$("#"+provinciaFieldID+"TR").attr("style","display:none")
		$("#"+provinciaFieldID).val('-1');
		$("#"+provinciaFieldID+"input").val('');
		
		$("#"+provinciaFieldID+"input").removeAttr("required");
		$("#"+provinciaFieldID+"input").removeAttr("class");

		$("#"+provinciaFieldID+"").removeAttr("required");
		$("#"+provinciaFieldID+"").removeAttr("class");

		
		$("#"+provinciaFieldID+"input").attr("readonly","readonly");
		
			}
		
		
	}else
	{
		
		document.getElementById("btnCap").removeAttribute('disabled');
		if(provinciaFieldID!=null)
			{
		$("#"+provinciaFieldID+"TR").removeAttr("style");
		$("#"+provinciaFieldID+"input").attr("required","required");
		$("#"+provinciaFieldID+"input").removeAttr("readonly");
	}}
}

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
function controllaModificheComune()
{
	idtipoAttivita=$("#tipoAttivita").val();
	idtipoImpresa = $("#tipo_impresa").val();
	
	
	if(idtipoAttivita=="2")
	{
		if(idtipoImpresa!="1")
			{
			
				PopolaCombo.verificaComuneAsl(document.getElementById("searchcodeIdComune").value,<%=StabilimentoDettaglio.getIdAsl()%>,verificaComuneAslCallback);
			}
		else
			{
			
			PopolaCombo.verificaComuneAsl(<%=StabilimentoDettaglio.getOperatore().getRappLegale().getIndirizzo().getComune()%>,<%=StabilimentoDettaglio.getOperatore().getRappLegale().getIdAsl()%>,verificaComuneAslResidenzaCallback);
			
			}
			
	}
	
}



function verificaComuneAslResidenzaCallback(valRet)
{
	if(valRet==false)
		{
		alert("Comune Residenza non Corrispondente con l'asl di appartenenza dello stabilimento");
		document.getElementById("tipo_impresa").value="<%=StabilimentoDettaglio.getOperatore().getTipo_impresa()%>";
		onChangeTipoImpresaEcorrige();
		}
	}


function verificaComuneAslCallback(valRet)
{
	if(valRet==false)
		{
		alert("Comune non Corrispondente con l'asl di appartenenza dello stabilimento");
		document.getElementById("searchcodeIdComuneinput").value="";

		}
	}
	
	

var checkField ;
function controllaModificheComunePerStabilimento(idAsl,idtipoAttivita,comuneResidenza,idAslResidenza,field,idtipoImpresaInput)
{
	
	idtipoImpresa=$("#tipo_impresa").val();
	checkField=field;
	if(field.checked==true)
		{

		if(idtipoAttivita=="2")
		{
			if(idtipoImpresa!="1")
				{
				
					PopolaCombo.verificaComuneAsl(document.getElementById("searchcodeIdComune").value,idAsl,verificaComuneAslCallbackPerStabilimento);
				}
			else
				{
				alert(comuneResidenza);
				
				PopolaCombo.verificaComuneAsl(comuneResidenza,idAslResidenza,verificaComuneAslCallbackPerStabilimentoResidenza);
				
				}
				
		}
		
	}
	
}


function verificaComuneAslCallbackPerStabilimentoResidenza(valRet)
{
	if(valRet==false)
		{
		alert("Comune Residenza non Corrispondente con l'asl di appartenenza dello stabilimento");
		checkField.checked=false;
		}
	}


function verificaComuneAslCallbackPerStabilimento(valRet)
{
	
	if(valRet==false)
		{
		alert("Comune non Corrispondente con l'asl di appartenenza dello stabilimento");
		checkField.checked=false;

		}
	}
	

/*AUTOCOMPLETAMENTO PER GLI INDIRIZZI*/          
$(function() {
	
	
	
    $( "#searchcodeIdComune" ).combobox();
    $( "#searchcodeIdprovincia" ).combobox();
    $( "#via" ).combobox();
    
    $("#searchcodeIdComuneinput").on("change",function(){

    	controllaModificheComune();
    	});
    
    
    onChangeTipoImpresaEcorrige();
    sbloccoProvincia('nazioneSedeLegale','searchcodeIdprovincia','searchcodeIdComune','via');
    
    $( "#modalListaStabilimenti" ).dialog({
		autoOpen: false,
	    resizable: false,
	    closeOnEscape: false,
	   	title:"LISTA STABILIMENTI",
	    width:1350,
	    height:400,
	    draggable: false,
	    modal: true,
	    buttons: [
	              {
	                  text: 'ESCI',
	                  click: function() { 
	                	  $( "#modalListaStabilimenti" ).dialog('close');
	                	  loadModalWindowUnlock();
	                	  }
	              },
	 {
	              text: 'APPLICA MODIFICHE IMPRESA',
                  click: function() { 
                	  verifica=false;
                	  var check = document.getElementsByName("checkStab");
                	  for(i=0;i<check.length;i++) 
                		  {
                		  if(check[i].checked)
                			  {
                			  verifica=true;
                			  break;
                			  }
                		  }
                	  if(verifica==false) 
                		  {
                		  alert("Selezionare almeno uno stabilimento su cui applicare la modifica");
                		  }
                	  else
                		  {
                	  $('#formCheckStab').validate({ // initialize the plugin
                	        rules: {
                	            'checkStab': {
                	                required: true
                	                
                	            }
                	        },
                	        messages: {
                	            'checkStab': {
                	                required: "Seleziona Almeno Uno Stabilimento"
                	            }
                	        }
                	    });
                	  
                	  
                	  $('#formCheckStab').attr('action', 'Gec.do?command=ErrataCorrigeDatiImpresa'); 
                	 
                	  
                	  
                	  $('#ecsf1').find('input').each(function(){

                		  $(this).appendTo("#hiddenFieldForm");
                	  });
                	  
                	  $('#ecsf1').find('select').each(function(){

                		  $(this).appendTo("#hiddenFieldForm");
                	  });


                
                	  $( "#formCheckStab" ).submit();
                	  }}
    }
	              
	              

	              ]
	  
	   
	}).prev(".ui-dialog-titlebar");
});

$(function (){
    $('form[name=ecsf]').submit(function(e){
   	
            loadModalWindowCustom("Attendere Prego");
            e.preventDefault();
          
            
            $.ajax({
           	 
           		url: 'Gec.do?command=VerificaEsistenzaAltriStabilimentiImpresa',
                type: 'post',
                data:  $("#ecsf1").serialize(),
           		datatype: 'json',
                success: function(msg) {
                document.getElementById("modalListaStabilimenti").innerHTML=msg;
                $( "#modalListaStabilimenti" ).dialog('open');
            },
            error: function (err){
                    alert('ko '+err.responseText);
            }
    });
});
});

function ControllaPIVA(pi)
{
	var formtest=true;
	var msg = 'Attenzione!\n'.toUpperCase();
	
	piField=document.getElementById(pi);
	
	pi= piField.value;	
	
	if( pi == '' ){ 
		msg +='Campo Partita iva vuoto!\n'.toUpperCase();
		formtest=false;
	} 
	if( pi.length != 11 ){
		msg+='Non corretta: la partita IVA dovrebbe essere lunga esattamente 11 caratteri.\n'.toUpperCase();
		formtest=false;
	}
validi = "0123456789";
for( i = 0; i < 11; i++ ){
    if( validi.indexOf( pi.charAt(i) ) == -1 ){
        msg+='Contiene un carattere non valido .\nI caratteri validi sono le cifre.\n'.toUpperCase();
        formtest=false;
    }
}
s = 0;
for( i = 0; i <= 9; i += 2 )
    s += pi.charCodeAt(i) - '0'.charCodeAt(0);
for( i = 1; i <= 9; i += 2 ){
    c = 2*( pi.charCodeAt(i) - '0'.charCodeAt(0) );
    if( c > 9 )  c = c - 9;
    s += c;
}
if( ( 10 - s%10 )%10 != pi.charCodeAt(10) - '0'.charCodeAt(0) ){
	msg+= 'Partita Iva non Valida secondo lo Standard.\n'.toUpperCase();
	formtest=false;
}


if(formtest==false){
	msg+='Vuoi continuare comunque ?'.toUpperCase();
	if(confirm(msg)==false)
		piField.value='';
}
	
}


function checkPartitaIva(idcampo){
    var that;
    
        tipoImpresa= document.getElementById("tipo_impresa").value;
 if (tipoImpresa=='5' ||  tipoImpresa=='6'){
     // Get the other input
     that = $('input[name=partitaIva],input[name=codFiscale]').not($('#'+idcampo));
     // If the value of this input is not empty, set the required property of the other input to false
     if($('#'+idcampo).val().length) {
         that.removeAttr("class");
     } else {
         that.attr("class","required");
         $('#'+idcampo).removeAttr("class");
     }
 }
 
 
 if ( $('input[name=partitaIva]').val()!='' || $('input[name=codFiscale]').val()!=''){
                loadModalWindowCustom("Verifica Esistenza Impresa. Attendere");
                $.ajax({
                     type: 'POST',
                   dataType: "json",
                   cache: false,
                  url: 'SuapStab.do?command=VerificaEsistenza',
                       data: ''+$('form[name=addstabilimento]').serialize(), 
                  success: function(msg) {
                           result = msg ;
                           if(msg.codiceErroreSuap =="1"){
                                   showPopUp();
                           }else{
                                   if(msg.codiceErroreSuap =="2"){
                                           var htmlText="<div><input type=\"button\" value = \"ESCI\" onclick=\"$('#dialogsuap').dialog('close');\"></div><br><br>" ; //msg[0].erroreSuap;
                                           htmlText+='<table border="1" class="pagedList"><tr><th>Denominazione</th><th>Partita IVa</th><th>Rappresentante Legale</th><th>Sede Legale</th><th></th></tr>'
                                           var jsontext = JSON.stringify(msg);
                                           if (msg.listaOperatori!=null && msg.listaOperatori.length>0){
                                                   for(i=0;i<msg.listaOperatori.length;i++){
                                                          htmlText+="<tr><td>"+msg.listaOperatori[i].ragioneSociale+"</td><td>"+msg.listaOperatori[i].partitaIva+"</td><td>"+msg.listaOperatori[i].rappLegale.nome+" "+msg.listaOperatori[i].rappLegale.cognome+" "+msg.listaOperatori[i].rappLegale.codFiscale+ " "+msg.listaOperatori[i].rappLegale.indirizzo.descrizioneComune+" "+msg.listaOperatori[i].rappLegale.indirizzo.descrizione_provincia+" "+msg.listaOperatori[i].rappLegale.indirizzo.via+"</td><td>"+msg.listaOperatori[i].sedeLegaleImpresa.descrizioneComune+" "+msg.listaOperatori[i].sedeLegaleImpresa.descrizione_provincia+" "+msg.listaOperatori[i].sedeLegaleImpresa.via+"</td><td><input type='button' value='Seleziona' onclick='selezionaImpresa("+i+")'></td></tr>";
                                                   }
                                                   htmlText+="</table>";
                                                   $( "#dialogsuap" ).html(htmlText);
                                                   
                                                   
                                                   
                                                    showPopUp();
                                           }else{
                                                   alert(msg.erroreSuap);
//                                                   document.getElementById("partitaIva").value="";
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

}

function nascondiCap(codnaz, idcap, idbottone)
{
			if( codnaz != "106" ) 
			{  
				document.getElementById(idbottone).type = "hidden";
				document.getElementById(idcap).type = "hidden";
				} else {
					document.getElementById(idbottone).type = "button";
					document.getElementById(idcap).type = "text";
					}
}

</script>

<%@ include file="../initPage.jsp"%>

<table class="trails" cellspacing="0">
<tr>
<td width="100%">
<a href=""><dhv:label name="">Anagrafica stabilimenti</dhv:label></a> >
<a href="OpuStab.do?command=SearchForm"><dhv:label name="">Ricerca</dhv:label></a> >
<a href="RicercaUnica.do?command=Search"><dhv:label name="">Risultato ricerca</dhv:label></a> >
<a href="OpuStab.do?command=Details&stabId=<%=StabilimentoDettaglio.getIdStabilimento()%>">Scheda Stabilimento</a> >
Errata Corrige Dati Impresa
</td>
</tr>
</table>



<%
String nomeContainer = StabilimentoDettaglio.getContainer();

String param = "stabId="+StabilimentoDettaglio.getIdStabilimento()+"&opId=" + StabilimentoDettaglio.getIdOperatore()+"altId="+StabilimentoDettaglio.getAltId();
request.setAttribute("Operatore",StabilimentoDettaglio.getOperatore());

%>

<br>


<dhv:container name="suap"  selected="details" object="Operatore" param="<%=param%>">

<form id = "ecsf1" name = "ecsf" method="post" action="Gec.do?command=VerificaEsistenzaAltriStabilimenti">
<input type = "hidden" name = "idSoggettoFisico" value = "<%=StabilimentoDettaglio.getOperatore().getRappLegale().getIdSoggetto() %>">
<input type = "hidden" name = "idStabilimento" value = "<%=StabilimentoDettaglio.getIdStabilimento() %>">


<fieldset>
		<legend><b>DATI IMPRESA</b></legend>
		
	
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
				
		<tr>
					<td class="formLabel" nowrap>
						<p id="labelRagSoc">
							DITTA/<br>DENOMINAZIONE/<br>RAGIONE SOCIALE
						</p>
					</td>
					<td>
					<input type ="hidden" name = "tipoAttivita" id = "tipoAttivita" value = "<%=StabilimentoDettaglio.getTipoAttivita() %>">
					<input type="text" size="70" id="ragioneSociale"
						required="required" name="ragioneSociale" value="<%=toHtml2(StabilimentoDettaglio.getOperatore().getRagioneSociale()) %>">
						
						</td>
				</tr>
				<tr>
					<td class="formLabel" nowrap>PARTITA IVA</td>
					<td><input type="text" size="70" min="11" maxlength="11" 
						id="partitaIva" name="partitaIva" required="required" onchange="ControllaPIVA('partitaIva')"  value="<%=toHtml2( StabilimentoDettaglio.getOperatore().getPartitaIva()) %>"></td>
				</tr>
				<tr id="codFiscaleTR">
					<td class="formLabel" nowrap>CODICE FISCALE<br>IMPRESA
					</td>
					<td><input type="text" size="70" maxlength="16"
						id="codFiscale" name="codFiscale"  value="<%=toHtml2(StabilimentoDettaglio.getOperatore().getCodFiscale()) %>">  
					 </td>
				</tr>
				
				<tr>
					<td class="formLabel" nowrap>TIPO IMPRESA</td>
					<td>
					<%
			TipoImpresaList.setJsEvent("onchange=onChangeTipoImpresaEcorrige();");
					TipoImpresaList.setRequired(true);
		%>
		
		   <%
                TipoImpresaList.setRequired(true);
                	   
                %>
                 <%=TipoImpresaList.getHtmlSelect("tipo_impresa", StabilimentoDettaglio.getOperatore().getTipo_impresa()) %>
            
            
			
		  
						</td>
				</tr>
				<tr id="tipo_societaTR">
					<td class="formLabel" nowrap id="trTipoSocieta">TIPO SOCIETA'</td>
					<td>
					<%=TipoSocietaList.getHtmlSelect("tipo_societa", StabilimentoDettaglio.getOperatore().getTipo_societa() )%>
						</td>
				</tr>
				
				<tr id="domicilio_digitaleTR">
					<td class="formLabel" nowrap id="trdomicilio_digitale">DOMICILIO DIGITALE</td>	
					<td><input type="text" size="70" maxlength="250"
					 id="domicilioDigitale" name=domicilioDigitale value="<%=toHtml2(StabilimentoDettaglio.getOperatore().getDomicilioDigitale()) %>">  
					</td>
				</tr>
				
				</table>
				</fieldset>
				
				<fieldset id = "setSedeLegale">
		<legend><b>DATI SEDE LEGALE</b></legend>
				
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
				
				
				
				<tr>
					<td class="formLabel" nowrap><label for="nazioneN-2">NAZIONE</label></td>
					<td>
						<%
							NazioniList
									.setJsEvent("onchange=\"sbloccoProvincia('nazioneSedeLegale','searchcodeIdprovincia','searchcodeIdComune','via'); \"");
						%>
						<% NazioniList.setJsEvent("onChange=\"nascondiCap(this.value,'presso','btnCap')\""); %> 
						<%=NazioniList.getHtmlSelect("nazioneSedeLegale", -1)%></td>
				</tr>
				
				<tr id="searchcodeIdprovinciaTR">
					<td class="formLabel" nowrap>PROVINCIA</td>
					<td><select name="searchcodeIdprovincia"
						id="searchcodeIdprovincia" 
						>
							<option value="<%=StabilimentoDettaglio.getOperatore().getSedeLegaleImpresa().getIdProvincia()%>" selected="selected"><%=StabilimentoDettaglio.getOperatore().getSedeLegaleImpresa().getDescrizione_provincia() %></option>
					</select> <input type="hidden" name="searchcodeIdprovinciaTesto"
						id="searchcodeIdprovinciaTesto" /></td>
				</tr>
				<tr>
					<td class="formLabel" nowrap>COMUNE</td>
					<td>
					<select name="searchcodeIdComune" id="searchcodeIdComune"
						>
							
							
							<%if(StabilimentoDettaglio.getOperatore().getTipo_impresa()!=1){
								
								if(StabilimentoDettaglio.getOperatore().getSedeLegaleImpresa().getComune()>0)
								{
								%>
							
							<option value="<%=StabilimentoDettaglio.getOperatore().getSedeLegaleImpresa().getComune()%>" selected="selected"><%= StabilimentoDettaglio.getOperatore().getSedeLegaleImpresa().getDescrizioneComune() %></option>
							
							<%}
								else
								{
									%>
									
									<option value="-1" selected="selected"><%= StabilimentoDettaglio.getOperatore().getSedeLegaleImpresa().getComuneTesto()%></option>
									<%
								}
								
							
							} %>
					</select> <input type="hidden" name="searchcodeIdComuneTesto"
						id="searchcodeIdComuneTesto" /></td>
				</tr>
				<tr>
					<td class="formLabel" nowrap>INDIRIZZO</td>
					<td>
						<table class="noborder">
							<tr>
								<td><%=ToponimiList.getHtmlSelect("toponimoSedeLegale", StabilimentoDettaglio.getOperatore().getSedeLegaleImpresa().getDescrizioneToponimo())%>
								</td>
								<td>
							
								<select name="via" id="via"  size="80">
										<option value="<%=StabilimentoDettaglio.getOperatore().getSedeLegaleImpresa().getIdIndirizzo() %>" selected="selected"><%=StabilimentoDettaglio.getOperatore().getSedeLegaleImpresa().getVia() %></option>
								</select> <%-- <input type="hidden" name="viaTesto" id="viaTesto" value="<%=StabilimentoDettaglio.getOperatore().getSedeLegaleImpresa().getVia()%>"/> --%>
								
								</td>
								<td>
								<input type="text" name="civicoSedeLegale"
									id="civicoSedeLegale" size="5" placeholder="NUM." maxlength="15" value ="<%=StabilimentoDettaglio.getOperatore().getSedeLegaleImpresa().getCivico() %>">
									</td>
								<td>
								<input type="text" name="presso" id="presso" size="4" placeholder="CAP" maxlength="5" value ="<%=StabilimentoDettaglio.getOperatore().getSedeLegaleImpresa().getCap() %>" >
									 <input type="button" id="btnCap" value="Calcola CAP"  onclick="calcolaCap(document.getElementById('searchcodeIdComune').value, document.getElementById('toponimoSedeLegale').value, document.getElementById('viainput').value, 'presso');" />
									</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			
			</fieldset>
			
			
			<%if(StabilimentoDettaglio.getTipoAttivita()==StabilimentoDettaglio.ATTIVITA_FISSA){ %>
			<fieldset id = "setSedeLegale">
		<legend><b>DATI SEDE PRODUTTIVA</b></legend>
				
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
				
				
				<tr id="searchcodeIdprovinciaTR">
					<td class="formLabel" nowrap>PROVINCIA</td>
					<td><%=StabilimentoDettaglio.getSedeOperativa().getDescrizione_provincia() %></td>
				</tr>
				<tr>
					<td class="formLabel" nowrap>COMUNE</td>
					<td><%=StabilimentoDettaglio.getSedeOperativa().getDescrizioneComune() %></td>
				</tr>
				<tr>
					<td class="formLabel" nowrap>INDIRIZZO</td>
					<td>
					<%=StabilimentoDettaglio.getSedeOperativa().getDescrizioneToponimo() + " "+StabilimentoDettaglio.getSedeOperativa().getVia()+" "+StabilimentoDettaglio.getSedeOperativa().getCivico() %>
					<%=StabilimentoDettaglio.getSedeOperativa().getCap() %>	
						</td>
						</tr>
						<tr>
					<td class="formLabel" nowrap>NUMERO REGISTRAZIONE</td>
					<td>
						<%=StabilimentoDettaglio.getNumero_registrazione()%>
					</td>
				</tr>	
						</table>
						
			</fieldset>
			<%} %>
			<input type = "submit" value="Continua">
			<input type = "button" value="Annulla" onclick="location.href='OpuStab.do?command=Details&stabId=<%=StabilimentoDettaglio.getIdStabilimento()%>'">
			</form>
			
</dhv:container>
			
<div id="modalListaStabilimenti">
</div>			
		