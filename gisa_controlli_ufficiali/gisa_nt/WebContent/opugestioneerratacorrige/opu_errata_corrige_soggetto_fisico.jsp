<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<jsp:useBean id="ToponimiList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="NazioniList" class="org.aspcfs.utils.web.LookupList" scope="request" />


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
<script src="javascript/datepicker/jquery.plugin.js"></script>
<script src="javascript/datepicker/jquery.datepick.js"></script>
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
function controllaModificheComune()
{
	idtipoAttivita=$("#tipoAttivita").val();
	idtipoImpresa = $("#tipoImpresa").val();
	
	
	if(idtipoAttivita=="2")
	{
		if(idtipoImpresa=="1")
			{
				PopolaCombo.verificaComuneAsl(document.getElementById("addressLegaleCity").value,<%=StabilimentoDettaglio.getIdAsl()%>,verificaComuneAslCallback);
				
			}
			
	}
	
}

function verificaComuneAslCallback(valRet)
{
	if(valRet==false)
		{
		alert("Comune non Corrispondente con l'asl di appartenenza dello stabilimento");
		document.getElementById("addressLegaleCityinput").value="";

		}
	}



var checkField ;

function controllaModificheComunePerStabilimento(idAsl,idtipoAttivita,comuneResidenza,idAslResidenza,field,idtipoImpresa)
{
	checkField=field;
	
	if(field.checked=='true')
		{
	if(idtipoAttivita=="2")
	{
		if(idtipoImpresa=="1")
			{
				PopolaCombo.verificaComuneAsl(document.getElementById("addressLegaleCity").value,idAsl,verificaComuneAslCallbackPerStabilimento);
				
			}
	}
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
	
	$('#dataNascita2').datepick({dateFormat: 'dd/mm/yyyy', maxDate: 0, showOnFocus: false, showTrigger: '#calImg'});
    //$( "#searchcodeIdComune" ).combobox();
    //  $( "#partitaIva" ).combobox();
    $( "#addressLegaleCountry" ).combobox();
    $( "#addressLegaleCity" ).combobox();
    $( "#comuneNascita" ).combobox();
    $( "#addressLegaleLine1" ).combobox();
    
    $( "#addressLegaleLine1input" ).attr("required","required");
    $( "#addressLegaleCountryinput" ).attr("required","required");
    $( "#addressLegaleCityinput" ).attr("required","required");
    
    
    if($("#comuneNascitainput").val()!="")
    	{
    	//$('#comuneNascitainput').attr('readonly', true);
    	}
    $("#comuneNascitainput").on("change",function(){

    	verificaCalcoloCF(document.forms[0].sesso,document.forms[0].nome,document.forms[0].cognome,document.getElementById('comuneNascitainput'),document.forms[0].dataNascita,'codFiscaleSoggetto',document.getElementById("comuneNascitainput"))
    	});
    
    $("#addressLegaleCityinput").on("change",function(){

    	controllaModificheComune();
    	});
    
    
    $('#formCheckStab').validate({ // initialize the plugin
        rules: {
            'sesso': {
                required: true
                
            }
        },
        messages: {
            'sesso': {
                required: "Seleziona Sesso"
            }
        }
    });
    
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
	                      loadModalWindowUnlock();

	                	  $( "#modalListaStabilimenti" ).dialog('close');
	                	  }
	              },
	 {
	              text: 'APPLICA MODIFICHE AL SOGGETTO FISICO',
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
                    	                required: true,
                    	                maxlength: 2
                    	            }
                    	        },
                    	        messages: {
                    	            'checkStab': {
                    	                required: "Seleziona Almeno Uno Stabilimento"
                    	            }
                    	        }
                    	    });
                    	  
                	  $('#formCheckStab').attr('action', 'Gec.do?command=ErrataCorrigeSoggettoFisico');
                	  
                	  $('#ecsf1').find('input').each(function(){

                		  $(this).appendTo("#hiddenFieldForm");
                	  });
                	  
                	  $('#ecsf1').find('select').each(function(){

                		  $(this).appendTo("#hiddenFieldForm");
                	  });


                
                	  $( "#formCheckStab" ).submit();
                		  }
                	  }
    }
	              
	              

	              ]
	  
	   
	}).prev(".ui-dialog-titlebar");
});

$(function (){
    $('form[name=ecsf]').submit(function(e){
   	
            loadModalWindowCustom("Attendere Prego");
            e.preventDefault();
            $.ajax({
           	 
           		url: 'Gec.do?command=VerificaEsistenzaAltriStabilimenti',
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


</script>

<%@ include file="../initPage.jsp"%>

<table class="trails" cellspacing="0">
<tr>
<td width="100%">
<a href=""><dhv:label name="">Anagrafica stabilimenti</dhv:label></a> >
<a href="OpuStab.do?command=SearchForm"><dhv:label name="">Ricerca</dhv:label></a> >
<a href="RicercaUnica.do?command=Search"><dhv:label name="">Risultato ricerca</dhv:label></a> >
<a href="OpuStab.do?command=Details&stabId=<%=StabilimentoDettaglio.getIdStabilimento()%>">Scheda Stabilimento</a> >
Errata Corrige Soggetto Fisico
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
<input type = "hidden" name = "tipoAttivita" id = "tipoAttivita" value = "<%=StabilimentoDettaglio.getTipoAttivita() %>">
<input type = "hidden" name = "tipoImpresa" id = "tipoImpresa" value = "<%=StabilimentoDettaglio.getOperatore().getTipo_impresa() %>">


<fieldset>
		<legend><b>SOGGETTO FISICO</b></legend>
		

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
				
				
				<tr>
					<td class="formLabel" nowrap>NOME</td>
					<td><input type="text" size="70" id="nome" name="nome"  onchange="verificaCalcoloCF(document.forms[0].sesso,document.forms[0].nome,document.forms[0].cognome,document.getElementById('comuneNascitainput'),document.forms[0].dataNascita,'codFiscaleSoggetto',this)"
						required="required" value="<%=StabilimentoDettaglio.getOperatore().getRappLegale().getNome()%>" <%if(!"".equalsIgnoreCase(StabilimentoDettaglio.getOperatore().getRappLegale().getNome())){%> <%}%>>
						
						</td>
				</tr>
				<tr>
					<td class="formLabel" nowrap><label for="cognome-2">COGNOME </label></td>
					<td><input type="text" size="70" id="cognome" name="cognome"  onchange="verificaCalcoloCF(document.forms[0].sesso,document.forms[0].nome,document.forms[0].cognome,document.getElementById('comuneNascitainput'),document.forms[0].dataNascita,'codFiscaleSoggetto',this)"
						required="required" value="<%=StabilimentoDettaglio.getOperatore().getRappLegale().getCognome()%>" <%if(!"".equalsIgnoreCase(StabilimentoDettaglio.getOperatore().getRappLegale().getCognome())){%> <%}%>>
						</td>
				</tr>
				<tr>
					<td class="formLabel" nowrap><label for="sesso-2">SESSO </label></td>
					<td><div class="test">
							<input type="radio" name="sesso" id="sesso1" value="M"  onclick="verificaCalcoloCF(document.forms[0].sesso,document.forms[0].nome,document.forms[0].cognome,document.getElementById('comuneNascitainput'),document.forms[0].dataNascita,'codFiscaleSoggetto',this)"
								class="required css-radio" <%if(StabilimentoDettaglio.getOperatore().getRappLegale().getSesso().equalsIgnoreCase("m")){%> checked="checked" <%} %>> <label
								for="sesso1" class="css-radiolabel radGroup2">M</label> 
								
								<input
								type="radio" name="sesso" id="sesso2" value="F"  onclick="verificaCalcoloCF(document.forms[0].sesso,document.forms[0].nome,document.forms[0].cognome,document.getElementById('comuneNascitainput'),document.forms[0].dataNascita,'codFiscaleSoggetto',this)"
								class="required css-radio" <%if(StabilimentoDettaglio.getOperatore().getRappLegale().getSesso().equalsIgnoreCase("f")){%> checked="checked" <%} %>> <label
								for="sesso2" class="css-radiolabel radGroup2">F</label>
						</div>
						</td>
				</tr>
				<tr>
					<td class="formLabel" nowrap><label for="dataN-2">DATA NASCITA </label></td>
					<td>
					<input type="text" size="15" name="dataNascita"  readonly="readonly" onchange="verificaCalcoloCF(document.forms[0].sesso,document.forms[0].nome,document.forms[0].cognome,document.getElementById('comuneNascitainput'),document.forms[0].dataNascita,'codFiscaleSoggetto',this)"
						id="dataNascita2" placeholder="dd/MM/YYYY" value="<%=toDateasString(StabilimentoDettaglio.getOperatore().getRappLegale().getDataNascita())%>" >
					
					<div style="display: none;"> 
    &nbsp;&nbsp;<img id="calImg" src="images/cal.gif" alt="Popup" class="trigger"> 
	</div>
					</td>
				</tr>
				<tr style="display: none">
					<td class="formLabel" nowrap><label for="nazioneN-2">NAZIONE NASCITA</label></td>
					<td>
					<INPUT TYPE="hidden" name = "nazioneNascita" id = "nazioneNascita" value = "150">
						</td>
				</tr>
				<tr>
					<td class="formLabel" nowrap>COMUNE NASCITA</td>
									<td>
									<select name="comuneNascita" id="comuneNascita" onchange="verificaCalcoloCF(document.forms[0].sesso,document.forms[0].nome,document.forms[0].cognome,document.getElementById('comuneNascitainput'),document.forms[0].dataNascita,'codFiscaleSoggetto',this)">
								<%if(StabilimentoDettaglio.getOperatore().getRappLegale().getComuneNascita()!=null && StabilimentoDettaglio.getOperatore().getRappLegale().getComuneNascita().trim().length()>2 && !"".equals(StabilimentoDettaglio.getOperatore().getRappLegale().getComuneNascita()) && !StabilimentoDettaglio.getOperatore().getRappLegale().getComuneNascita().equalsIgnoreCase("N.D")) {%>
								<option value="<%=StabilimentoDettaglio.getOperatore().getRappLegale().getComuneNascita()%>" selected="selected"><%=StabilimentoDettaglio.getOperatore().getRappLegale().getComuneNascita()%></option>
								<%} %>
								</select> 
					<input type="hidden" name="comuneNascitaTesto"
						id="comuneNascitaTesto" />
						
						</td>
						
				</tr>
				<tr>
					<td class="formLabel" nowrap>CODICE FISCALE</td>
					<td><input type="text" name="codFiscaleSoggetto"
						readonly="readonly" id="codFiscaleSoggetto" class="required"
						value ="<%=StabilimentoDettaglio.getOperatore().getRappLegale().getCodFiscale() %>" /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><input type="button" id="calcoloCF" class="newButtonClass" 
						value="CALCOLA CODICE FISCALE" 
						onclick="javascript:CalcolaCF(document.forms[0].sesso,document.forms[0].nome,document.forms[0].cognome,document.getElementById('comuneNascitainput'),document.forms[0].dataNascita,'codFiscaleSoggetto')"></input>
					</td>
				</tr>
				<tr style="display: none">
					<td><label for="nazioneN-2">NAZIONE RESIDENZA</label></td>
					<td>
					
										<INPUT TYPE="hidden" name = "nazioneNascita" id = "nazioneNascita" value = "150">
					</td>
				</tr>
				<tr id="addressLegaleCountryTR">
					<td class="formLabel" nowrap>PROVINCIA RESIDENZA</td>
					<td><select id="addressLegaleCountry" required="required"
						name="addressLegaleCountry">
						
							<option value="<%=StabilimentoDettaglio.getOperatore().getRappLegale().getIndirizzo().getIdProvincia()%>" selected="selected"><%=StabilimentoDettaglio.getOperatore().getRappLegale().getIndirizzo().getDescrizione_provincia()%></option>
					</select> <input type="hidden" id="addressLegaleCountryTesto"
						name="addressLegaleCountryTesto" /></td>
				</tr>
				<tr>
					<td class="formLabel" nowrap>COMUNE RESIDENZA</td>
					<td>
					
					
					
					<select name="addressLegaleCity" id="addressLegaleCity" required="required">
							
							<%if(StabilimentoDettaglio.getOperatore().getRappLegale().getIndirizzo().getComune()>0){ %>
							<option value="<%=StabilimentoDettaglio.getOperatore().getRappLegale().getIndirizzo().getComune()%>" selected="selected"><%=StabilimentoDettaglio.getOperatore().getRappLegale().getIndirizzo().getDescrizioneComune()%></option>
					
					<%}%>
					
					</select> <input type="hidden" name="addressLegaleCityTesto"
						id="addressLegaleCityTesto" /></td>
				</tr>
				<tr>
					<td class="formLabel" nowrap>INDIRIZZO RESIDENZA</td>
					<td>
						<table class="noborder">
							<tr>
								<td><%=ToponimiList.getHtmlSelect("toponimoResidenza", "VIA")%>
								</td>
								<td><select name="addressLegaleLine1" required="required"
									id="addressLegaleLine1" >
										
									<option value="<%=StabilimentoDettaglio.getOperatore().getRappLegale().getIndirizzo().getIdIndirizzo()%>" selected="selected"><%=StabilimentoDettaglio.getOperatore().getRappLegale().getIndirizzo().getVia()%></option>										
										
								</select>
								 <%-- <input type="hidden" name="addressLegaleLine1Testo"
									id="addressLegaleLine1Testo" value="<%=StabilimentoDettaglio.getOperatore().getRappLegale().getIndirizzo().getVia() %>" /> --%>
									
									
									
									</td>
								<td><input type="text" name="civicoResidenza" required="required"
									id="civicoResidenza" size="5" placeholder="NUM." maxlength="15"
									 value="<%=StabilimentoDettaglio.getOperatore().getRappLegale().getIndirizzo().getCivico()%>"></td>
								<td><input type="text" name="capResidenza" readonly
									id="capResidenza" size="4" placeholder="CAP" maxlength="5" required="required"
									 value="<%=StabilimentoDettaglio.getOperatore().getRappLegale().getIndirizzo().getCap()%>">
									 <input type="button" value="Calcola CAP"
   							 onclick="calcolaCap(document.getElementById('addressLegaleCity').value, document.getElementById('toponimoResidenza').value, document.getElementById('addressLegaleLine1input').value, 'capResidenza');" />
									 </td>
							</tr>
						</table>
					</td>
				</tr>
				
			</table>
			
			</fieldset>
			<input type = "submit" value="Continua">
			<input type = "button" value="Annulla" onclick="location.href='OpuStab.do?command=Details&stabId=<%=StabilimentoDettaglio.getIdStabilimento()%>'">
			</form>
			
</dhv:container>
			
<div id="modalListaStabilimenti">
</div>			
		