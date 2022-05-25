/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

var ricerca = false;
var tipoAttivita_FISSA=1;
var tipoAttivita_MOBILE=2;

function mostraDatiStabilimento(valueTipoAttivita){
	if (valueTipoAttivita==tipoAttivita_MOBILE){
		// oscuriamo l'indirizzo dello stabilimento
		document.getElementById("datiIndirizzoStab").style.display="none";
		$("#viaStab").val("");
//		$("#viaStabTesto").val("");
//		$("#viaStab").append("<option value='-1' selected='selected'></option>');");
		if (document.getElementById("datiAttivitaMobile")!=null)
			document.getElementById("datiAttivitaMobile").style.display="";
	}else{ 
		// di default o per tipo attivita FISSA rendiamo visibile l'indirizzo dello stabilimento
		document.getElementById("datiIndirizzoStab").style.display="";
		if (document.getElementById("datiAttivitaMobile")!=null)
			document.getElementById("datiAttivitaMobile").style.display="none";
	}
	
}

/*COPIA INDIRIZZO DA  RESIDENZA A SEDE LEGALE*/
function copiaDaResidenza(){
	if (document.getElementById("checkSeddeLegale").checked){
		$("#nazioneSedeLegale").val($("#nazioneResidenza").val());
  		sbloccoProvincia('nazioneSedeLegale','searchcodeIdprovincia','searchcodeIdComune','via');
  		$("#searchcodeIdprovinciainput").val($("#addressLegaleCountryinput").val());
  		// $("#searchcodeIdprovincia").val($("#addressLegaleCountry").val());
  		$("#searchcodeIdprovincia").append("<option value='"+$("#addressLegaleCountry").val()+ "' selected='selected'>"+$("#addressLegaleCountryinput").val()+"</option>');");
  		$("#searchcodeIdComuneinput").val($("#addressLegaleCityinput").val());
  		$("#searchcodeIdComune").append("<option value='"+$("#addressLegaleCity").val()+ "' selected='selected'>"+$("#addressLegaleCityinput").val()+"</option>');");
  		$("#searchcodeIdComune").val($("#addressLegaleCity").val());
  		$("#viainput").val($("#addressLegaleLine1input").val());
  		$("#via").val($("#addressLegaleLine1input").val());
//  		$("#viaTesto").val($("#addressLegaleLine1Testo").val());
//  		$("#via").append("<option value='"+$("#addressLegaleLine1").val()+ "' selected='selected'>"+$("#addressLegaleLine1input").val()+"</option>');");
  		//$("#via :selected").val($("#addressLegaleLine1").val());
  		$
  		$("#toponimoSedeLegale").val($("#toponimoResidenza").val());
  		$("#civicoSedeLegale").val($("#civicoResidenza").val());
  		$("#presso").val($("#capResidenza").val());
  	}else{
  		$("#nazioneSedeLegale").val('106');
  		sbloccoProvincia('nazioneSedeLegale','searchcodeIdprovincia','searchcodeIdComune','via');
  		$("#searchcodeIdprovinciainput").val("");
  		$("#searchcodeIdprovincia").val("");
  		$("#searchcodeIdComuneinput").val("");
  		$("#searchcodeIdComune").val("");
  		$("#viainput").val("");
//  		$("#via :selected").val("");
  		$("#via").val("");
  		$("#viainput").val("");
  	}
}
  
function checkStabNumReg() {
	// $("#idStabilimento").val("-1");
	var numReg = $("#numeroRegistrazione").val();
	if (numReg!=""){
		loadModalWindowCustom("Verifica Esistenza Stabilimento. Attendere");
		$.ajax({
			type: 'POST',
		    dataType: "json",
		    cache: false,
		    url: 'OpuStab.do?command=VerificaEsistenzaStabilimento',
		    data: ''+$('form[name=addstabilimento]').serialize(), 
		    success: function(msg) {
		    	var htmlText="" ;
		    	if (msg!=null && msg!='' && msg.length >0){
		    		resultStab = msg ; 
		    		selezionaStabilimentoByNumeroRegistrazione(0);
		    	}else{
		    		$("#numeroRegistrazione").val("");
		    		alert("Stabilimento non trovato. Indicare Un numero valido per lo stabilimento".toUpperCase());
		    	}
		    	loadModalWindowUnlock();
		    },
		    error: function (err) {
		    alert('ko '+err.responseText);
		    }	
		});
	}
}
  
function checkStab() {
//	  $("#idStabilimento").val("-1");
		var comune = $("#searchcodeIdComuneStabinput").val();
		var indirizzo = -1;
		if (comune!="")
			{
			 loadModalWindowCustom("Verifica Esistenza Sede Produttiva. Attendere");
		 $.ajax({
		      type: 'POST',
		      dataType: "json",
		      cache: false,
		     url: 'OpuStab.do?command=VerificaEsistenzaStabilimento',
		      data: ''+$('form[name=addstabilimento]').serialize(), 
		      success: function(msg) {
		    	  var htmlText="" ;
		    	  if (msg!=null && msg!='' && msg.length >0)
		    		  {
		    		  resultStab = msg ; 
		    		  htmlText+='<table border="1"><tr><th>Denominazione</th><th>Partita IVa</th><th>Rappresentante Legale</th><th>Sede Legale</th><th>Sede Produttiva</th><th></th></tr>'
		        			var jsontext = JSON.stringify(msg);
		        				for(i=0;i<msg.length;i++)
		        				{
		        					htmlText+="<tr><td>"+msg[i].operatore.ragioneSociale+"</td><td>"+msg[i].operatore.partitaIva+"</td><td>"+msg[i].operatore.rappLegale.nome+" "+msg[i].operatore.rappLegale.cognome+" "+msg[i].operatore.rappLegale.codFiscale+"</td><td>"+msg[i].operatore.sedeLegaleImpresa.descrizioneComune+" "+msg[i].operatore.sedeLegaleImpresa.descrizione_provincia+" "+msg[i].operatore.sedeLegaleImpresa.via+"</td><td>"+msg[i].sedeOperativa.descrizioneComune+"<br>"+msg[i].sedeOperativa.via +"</td><td><input type='button' value='Seleziona' onclick='selezionaStabilimento("+i+")'></td></tr>";
		        				}
		        			htmlText+="<tr><td><input type='button' value='Non voglio inserire nessuno di questi stabilimenti' onclick='closePopupStab()'></td></tr></table>";
//		        			htmlText+="<input type='button' value='Inserisci Nuova' onclick='sovrascriviImpresa()'> ";
		        			$( "#dialogsuaplistastab" ).html(htmlText);
		        			$('#dialogsuaplistastab').dialog('open');
		    		  }
		    	  else
		    		  {
		    		  alert("Stabilimento non trovato".toUpperCase());
		    		  }
		    	  loadModalWindowUnlock();
		      },
		      error: function (err) {
		         alert('ko '+err.responseText);
		      }
		  });
			}
	}

  function copiaDaLegale()
  {
	  if ( $("#operazioneScelta").val()!='cambioTitolarita')
		  {
  	if (document.getElementById("checkSeddeOperativa").checked)
  		{
  		if ($("#tipo_impresa").val()!="1" && $("#searchcodeIdprovinciainput").val()!='Napoli' && $("#searchcodeIdprovinciainput").val()!='Salerno' && $("#searchcodeIdprovinciainput").val()!='Caserta' && $("#searchcodeIdprovinciainput").val()!='Benevento' && $("#searchcodeIdprovinciainput").val()!='Avellino' )
  			{
  			alert('Impossibile copiare un indirizzo fuori Campania!'.toUpperCase());
  			document.getElementById("checkSeddeOperativa").checked=false;
  			return false;
  			}
  		if ($("#tipo_impresa").val()!="1")
  			{
  		$("#searchcodeIdprovinciaStabinput").val($("#searchcodeIdprovinciainput").val());
  		$("#searchcodeIdprovinciaStab").append("<option value='"+$("#searchcodeIdprovincia").val()+ "' selected='selected'>"+$("#searchcodeIdprovinciainput").val()+"</option>');");
  		$("#searchcodeIdComuneStabinput").val($("#searchcodeIdComuneinput").val());
  		$("#searchcodeIdComuneStab").append("<option value='"+$("#searchcodeIdComune").val()+ "' selected='selected'>"+$("#searchcodeIdComuneinput").val()+"</option>');");
  		$("#searchcodeIdComuneStab").val($("#searchcodeIdComune").val());
  		$("#viaStab").val($("#via").val());
  		$("#viaStabinput").val($("#viainput").val());
//  		$("#viaStabTesto").val($("#viaTesto").val());
//  		$("#viaStab").append("<option value='"+$("#via").val()+ "' selected='selected'>"+$("#viainput").val()+"</option>');");
  		$("#toponimoSedeOperativa").val($("#toponimoSedeLegale").val());
  		$("#civicoSedeOperativa").val($("#civicoSedeLegale").val());
  		$("#capStab").val($("#presso").val());
  			}
  			else
  			{
  				if ($("#addressLegaleCountryinput").val()!='Napoli' && $("#addressLegaleCountryinput").val()!='Salerno' && $("#addressLegaleCountryinput").val()!='Caserta' && $("#addressLegaleCountryinput").val()!='Benevento' && $("#addressLegaleCountryinput").val()!='Avellino' )
  			{
  			alert('Impossibile copiare un indirizzo fuori Campania!'.toUpperCase());
  			document.getElementById("checkSeddeOperativa").checked=false;
  			return false;
  			}
  			$("#searchcodeIdprovinciaStabinput").val($("#addressLegaleCountryinput").val());
  	  		$("#searchcodeIdprovinciaStab").append("<option value='"+$("#addressLegaleCountry").val()+ "' selected='selected'>"+$("#addressLegaleCountryinput").val()+"</option>');");
  	  		$("#searchcodeIdComuneStabinput").val($("#addressLegaleCityinput").val());
  	  		$("#searchcodeIdComuneStab").append("<option value='"+$("#addressLegaleCity").val()+ "' selected='selected'>"+$("#addressLegaleCityinput").val()+"</option>');");
  	  		$("#searchcodeIdComuneStab").val($("#addressLegaleCity").val());
  	  	$("#viaStab").val($("#addressLegaleLine1").val());
  	  		$("#viaStabinput").val($("#addressLegaleLine1input").val());
//  	  		$("#viaStabTesto").val($("#addressLegaleLine1Testo").val());
//  	  		$("#viaStab").append("<option value='"+$("#addressLegaleLine1").val()+ "' selected='selected'>"+$("#addressLegaleLine1input").val()+"</option>');");
  	  		$("#toponimoSedeOperativa").val($("#toponimoResidenza").val());
  	  		$("#civicoSedeOperativa").val($("#civicoResidenza").val());
  	  		$("#capStab").val($("#capResidenza").val());
  			}
  			checkStab();
  		}
  	else
  		{
  		 $("#searchcodeIdprovinciaStabinput").val("");
  		 $("#searchcodeIdprovinciaStab").val("");
  		 $("#searchcodeIdComuneStabinput").val("");
  		 $("#searchcodeIdComuneStab").val("");
  		 $("#viaStabinput").val("");
  		$("#idStabilimento").val("-1");
  		 $("#viaStab").val("");
  		$("#viaStabinput").val("");
  		}
		  }
	  else
		  {
		  	alert("Non e' possibile selezionare la sede dello stabilimento per l'operazione scelta".toUpperCase());
		  }
  	}
  
function svuotaDatiStabilimento()
  {
	  $("#searchcodeIdprovinciaStabinput").val("");
		 $("#searchcodeIdprovinciaStab").val("");
		 $("#searchcodeIdComuneStabinput").val("");
		 $("#searchcodeIdComuneStab").val("");
		 $("#viaStab").val("");
			if ($("#idStabilimentoAdd").val()!='' )
				{
					$("#idStabilimento").val($("#idStabilimentoAdd").val());
				}
		 $("#viaStab").val("");
		 $("#viaStabinput").val("");
  }

/*GESTIONE CAMPO TIPO IMPRESA PER SOCIETA E IMPRESE INDIVIDUALI*/
 function onChangeTipoImpresa()
  {
	 $("#tipo_impresa option[value='']").hide();
  	var  tipoImpresa =document.getElementById("tipo_impresa").value;
  	if (tipoImpresa!="")
  		SuapDwr.onChangeTipoImpresa(tipoImpresa,onChangeTipoImpresaCallBack);
  	}

function onChangeTipoImpresaCallBack(tipoImpresa)
  	{ 
  		if (document.getElementById("tipo_impresa").value=='4')
  			{
  			document.getElementById("trTipoSocieta").innerHTML="TIPO COOPERATIVA";
  			}
  		else
  			{
  			document.getElementById("trTipoSocieta").innerHTML="TIPO SOCIETA'";
  			}
  		/*setto la label e obbligatorieta della ragione sociale in base al tipo impresa*/
  		document.getElementById("labelRagSoc").innerHTML=tipoImpresa[0].labelRagioneSociale;
  		if (tipoImpresa[0].requiredRagioneSociale==false)
  			{
  		 $("#ragioneSociale").removeAttr("class");
  		 $("#codFiscaleTR").attr("style","display:none");
  			}
  		else
  			{
  			if (tipoImpresa[0].requiredRagioneSociale==true)
  			{
  				 $("#ragioneSociale").attr("class","required");
  				 $("#codFiscaleTR").removeAttr("style");
  			}
  			}
		if(tipoImpresa[0].idTipoImpresa!=5 && tipoImpresa[0].idTipoImpresa!=6  && tipoImpresa[0].idTipoImpresa!=4)
			{
  		if (tipoImpresa[0].requiredPartitaIva==true)
  			{
  				$("#partitaIva").attr("class","required");
  			}
  		else
  			{
  				$("#partitaIva").removeAttr("class");
  			}
  		if (tipoImpresa[0].requiredCodiceFiscale==true)
			{
  				$("#codFiscale").attr("class","required");
			}
  		else
  			if (tipoImpresa[0].idTipoImpresa==2) // societa
  				{
  				 $("#codFiscaleTR").attr("style","display:none");
  				}
		else
			{
				$("#codFiscale").removeAttr("class");
			}
  	}
  		if (tipoImpresa[0].requiredSedeLegale==true)
		{
				$("#searchcodeIdprovinciainput").attr("class","required");
				$("#searchcodeIdComuneinput").attr("class","required");
				$("#tiponimosedelegaleinput").attr("class","required");
				$("#via").attr("class","required");
				$("#civicoSedeLegale").attr("class","required");
				$("#presso").attr("class","required");
				document.getElementById("setSedeLegale").style.display="";
		}
	else
		{
		$("#searchcodeIdprovinciainput").removeAttr("class");
		$("#searchcodeIdComuneinput").removeAttr("class");
		$("#tiponimosedelegaleinput").removeAttr("class");
		$("#via").removeAttr("class");
		$("#civicoSedeLegale").removeAttr("class");
		$("#presso").removeAttr("class");
		document.getElementById("setSedeLegale").style.display="none";
		}
  		/**required su partita iva e codice fisclae impresa  **/
  		/*costruisco la lista delle societa in base al tipo impresa*/
  		$('#tipo_societa').children('option:not(:first)').remove();
  		for (i=0;i<tipoImpresa.length;i++)
  			{
  			if (tipoImpresa[i].tipoImpresa==tipoImpresa[i].tipoSocieta)
  				{
  				$("#tipo_societa").removeAttr("class");
  				 $("#tipo_societaTR").attr("style","display:none");
  				break;
  				}
  			else
  				{
  			 $('#tipo_societa')
  			 .append($("<option></option>")
  			 .attr("value",tipoImpresa[i].codeTipoSocieta)
  			 .text(tipoImpresa[i].tipoSocieta)); 
  			 if (i==0){
  				 $("#tipo_societa").attr("class","required");
  				 $("#tipo_societaTR").removeAttr("style");
  				}
  				}
  			}  		
  	}
/*FUNZIONI PER IL TAB DELLE ATTIVIT PRODUTTIVE. COSTRUISCE LA LISTA DELLE ATTIVITA PER STEP*/
function checkNext(tab)
{
	if($("#"+tab+"").find('select').val()=='')
		{
		alert(('Controllare di aver selezionato una voce per '+$("#"+tab+"").find('select').attr('label')).toUpperCase());
		return false ;
		}
	return 'true' ;
}

var idTab;
var livelloIn;
var tipoRegistrazione ;

function mostraAttivitaProduttive(idTable,livello,idSelezionato, cerca,tipoInserimento)
  {
	  tipoRegistrazione = tipoInserimento;
	  if (cerca==true){
		  ricerca=true;
		if (idSelezionato==-1)
			svuotaCampiRicerca();
	  }
	  else
		  ricerca=false;
	  idTab = idTable;
	  livelloIn=livello;
	  var idTipoAttivita = -1 ;
	  if (document.getElementById("tipoAttivita")!=null)
		  idTipoAttivita = document.getElementById("tipoAttivita").value;
  	SuapDwr.mostraAttivitaProduttive(livello,idSelezionato,tipoInserimento,idTipoAttivita,{async:false,callback:mostraAttivitaProduttiveCallBack});
  }
  
  function mostraAttivitaProduttiveCallBack(attivita)
  {
	  if(typeof livelloIn != 'undefined'){
		  if (ricerca==true && livelloIn>3){
			  //ARRIVA FINO AL LIVELLO LIVELLOIN E POI FERMATI
		  var ric = document.getElementById("searchattivita"+(livelloIn-1));
		  ric.value =  $("#"+idTab+"").find('select option:selected').text();
		  return false;
		  }
	  }
	  var pathSelezionato = '' ;
	  	if(attivita.listaItem.length>0)
  		{
  		if($("#"+idTab+"").find("div").html()!=null){
			pathSelezionato = $("#"+idTab+"").find("div").html()+'\n\n <p style="color:'+attivita.bgcolorPrec+'">'+$("#"+idTab+"").find('select option:selected').text()+'</p>';
		  	if (ricerca==true && document.getElementById("searchattivita"+(livelloIn-1))!=null){
		  		var ric = document.getElementById("searchattivita"+(livelloIn-1));
		  		ric.value = $("#"+idTab+"").find('select option:selected').text();
		  	}
			}
  	$("#"+idTab+" tr").remove();
  	$("#documento table").remove();
  	$("#"+idTab+"").append('<tr><td align="left" colspan="2" width="100%"><div style="width:100%;">'+pathSelezionato+'</div></td><td id="valida'+idTab+'" aligh="right"></td></tr>');
  	var combo = "<select onchange='if(checkNext(\""+idTab+"\")==\"true\"){mostraAttivitaProduttive(\""+idTab+"\",\""+attivita.nextLivello+"\",this.value, "+ricerca+","+tipoRegistrazione+");}' style='color:"+attivita.bgcolor+";width:100%;' label='"+attivita.label+"' foglia='false' name='idLineaProduttiva2' id='"+attivita.label+"'>" ;
  	combo+="<option style='color:"+attivita.bgcolor+"' value='' selected='selected'>Seleziona "+attivita.label+"</option>";
  	for (i=0;i<attivita.listaItem.length;i++)
  		{
  		combo+="<option style='color:"+attivita.bgcolor+"' value='"+attivita.listaItem[i].id+"'>"+attivita.listaItem[i].descizione+"</option>";
  		}
  	combo+="</select>";
  	$("#"+idTab+"").append('<tr><td align="left" style="width:10%"><p style="color:'+attivita.bgcolor+'">'+attivita.label+'</p></td><td align="left" style="width:60%">'+combo+'</td><td  id="menu'+idTab+'" style="width:15%" align="r"><input type="button" style="width:35%" value="Annulla" onclick="mostraAttivitaProduttive(\''+idTab+'\',1,-1, '+ricerca+',\''+tipoRegistrazione+'\'></td></tr>');

	
		$("#menu"+idTab).html('<input type="button" value="Annulla" onclick="mostraAttivitaProduttive(\''+idTab+'\',1,-1, '+ricerca+',\''+tipoRegistrazione+'\'); azzeraCampiLinea('+idTab+');">');

  		document.getElementById('validatelp').value="false";
  		}
  	else
  		{
  		if( $("#"+idTab+"").find("div")!=null){
			pathSelezionato= $("#"+idTab+"").find("div").html()+'\n\n <p style="color:'+attivita.bgcolorPrec+'">'+$("#"+idTab+"").find('select option:selected').text()+'</p>';
			}
  		$("#"+idTab+"").find("div").html(pathSelezionato+"<input type='hidden' name='idLineaProduttiva' value='"+ $("#"+idTab+"").find("select option:selected").val()+"'>");
  		$("#menu"+idTab).html('<input type="button" value="Annulla" onclick="mostraAttivitaProduttive(\''+idTab+'\',1,-1, '+ricerca+',\''+tipoRegistrazione+'\'); azzeraCampiLinea('+idTab+');">');
  		$("#valida"+idTab).html('&nbsp;&nbsp;<img width="50px" height="50px" src="css/suap/images/ok3.png">');
  			document.getElementById('validatelp').value="true";
  			$("#"+idTab+"").find('select').attr("label",$("#"+idTab+"").find('select option:selected').text());
  			$("#"+idTab+"").find('select').val($("#"+idTab+"").find('select option:selected').val());
  		$("#"+idTab+"").find('select').attr("disabled","disabled");
  		}
  	if(livelloIn=='1')
		$("#"+idTab+"").find("div").html('');
  }
  var numSecondarie = 0;
  
  function aggiungiCampiLinea(numSecondarie){
	  $("#secondarie").html($("#secondarie").html()+'<table style="width: 100%;" id="attsecondarie'+numSecondarie+'_ext"><tr id = "trDataInizioLinea"><td>DATA INIZIO</td><td><input type="text" size="15" class="required" name="dataInizioLinea'+numSecondarie+'" id="dataInizioLinea'+numSecondarie+'"  placeholder="dd/MM/YYYY"></td></tr><tr id = "trDataFineLinea"><td>DATA FINE</td><td><input type="text" size="15" name="dataFineLinea'+numSecondarie+'" id="dataFineLinea'+numSecondarie+'" placeholder="dd/MM/YYYY"></td></tr></table><br><br><hr>');	
		$("#secondarie").html($("#secondarie").html()+"<script> $(function() {	$('#dataInizioLinea"+numSecondarie+"').datepick({dateFormat: 'dd/mm/yyyy',  maxDate: 0, showOnFocus: false, showTrigger: '#calImg'}); $('#dataFineLinea"+numSecondarie+"').datepick({dateFormat: 'dd/mm/yyyy', showOnFocus: false, showTrigger: '#calImg', onClose: controlloDate }); }); </script>");
  }

  function aggiungiRiferimentoTabella(tipoinserimento)
{
	aggiornaValoriDellaForm('addstabilimento');
	if(document.getElementById('validatelp').value=='true' )//|| $( "#methodRequest" ).val()=="ampliamento" )
		{
		numSecondarie+=1;

		
	$("#secondarie").html($("#secondarie").html()+'<table id="attsecondarie'+numSecondarie+'" style="width: 100%;"<tr><td></td></tr></table>')
	mostraAttivitaProduttive('attsecondarie'+numSecondarie ,1,-1, ricerca,tipoinserimento);
	aggiungiCampiLinea(numSecondarie);
			}
		else
			alert('Controllare di aver compilato le linee produttive esistenti'.toUpperCase());
	}

function abilitaCodiceFiscale(id)
{
	if($("#"+id+" option:selected").text()=='Italia')
		{
		this.value='false';document.getElementById('calcoloCF').style.visibility='visible';
		document.getElementById('codFiscaleSoggetto').readOnly=true;
		if (document.getElementById('codFiscaleSoggettoAdd')!=null)
			{
			document.getElementById('codFiscaleSoggettoAdd').readOnly=true;
			document.getElementById('codFiscaleSoggettoAdd').value="";
			}
		document.getElementById('codFiscaleSoggetto').value='';
	} else {
		document.getElementById('calcoloCF').style.visibility='hidden';
		document.getElementById('codFiscaleSoggetto').readOnly=false;
		if (document.getElementById('codFiscaleSoggettoAdd')!=null)
			{
			document.getElementById('codFiscaleSoggettoAdd').readOnly=false;
			document.getElementById('codFiscaleSoggettoAdd').value="";}
		}
	}

function sbloccoProvincia(nazioneFieldID,provinciaFieldID,comuneFieldID,indrizzoFieldID)
{
	if($("#"+nazioneFieldID+" option:selected").text()!='Italia')
	{
		if (provinciaFieldID!=null)
			{
		$("#"+provinciaFieldID+"TR").attr("style","display:none")
		$("#"+provinciaFieldID).val('-1');
		$("#"+provinciaFieldID+"input").val('');
		$("#"+provinciaFieldID+"input").removeAttr("class");
		$("#"+provinciaFieldID+"input").attr("readonly","readonly");
			}
		if (indrizzoFieldID!=null)
			{
		
		$("#"+indrizzoFieldID+"").val('');
			}
		$("#"+comuneFieldID).val('-1');
		$("#"+comuneFieldID+"input").val('');
	}else
	{
		if(provinciaFieldID!=null)
			{
		$("#"+provinciaFieldID+"TR").removeAttr("style");
		$("#"+provinciaFieldID+"input").attr("class","required");
		$("#"+provinciaFieldID+"input").removeAttr("readonly");
	}}
}

/*FUNZIONI PER LA COSTRUZIONE DELLA DOCUMENTAZIONE IN BASE ALLE LINEE PRODUTTIVE SELEZIONATE*/
var stabId;

function mostraListaDocumentiAttivitaProduttive(stabIdIn)
  {
// 	alert($("input[name=idLineaProduttiva]").val());
	stabId = stabIdIn;
	var idLinee = new Array();
	  var array = document.getElementsByName('idLineaProduttiva');
	  for (i=0; i <array.length; i++)
		  idLinee[i]=array[i].value ;
	  SuapDwr.mostraDocumentazioneAttivitaProduttive(idLinee,mostraListaDocumentiAttivitaProduttiveCallBack);
  	//SuapDwr.mostraAttivitaProduttive(1,mostraListaDocumentiAttivitaProduttiveCallBack);
  }

function mostraListaDocumentiAttivitaProduttiveCallBack(listaDocumenti)
  {
	  var htmltoappend = '' ;
	  $(".documenti").html("");
	  if(listaDocumenti.length>0)
		  {
	  for(i=0;i<listaDocumenti.length;i++)
		  {
		  if (document.getElementById("allegato"+i)==null || document.getElementById("allegato"+i).value!="1" )
			  {
		  $("<fieldset>"+
			"<legend>"+listaDocumenti[i].allegatoNome+"</legend>"+
			  "<table cellpadding='4' cellspacing='0' border='0' width='100%' class='details'>"+
			     "<tr><th colspan='2'><img border='0' src='images/file.gif' align='absmiddle'><b>Descrizione Allegato</b></th>"+
			     "</tr>"+
			     /*"<tr><td colspan='2'>"+listaDocumenti[i].allegatoDescrizione+"</td></tr>"+
			     "<tr class='containerBody'>" +
			     "<td class='formLabel' id='linkallegato"+i+"' style=''><input type = 'hidden' id = 'allegato"+i+"' value='0'><a href='#' onclick='window.open(\"GestioneAllegatiUploadSuap.do?command=ToAllegaFileSuap&subject="+listaDocumenti[i].allegatoNome+"&stabId="+stabId+"&indice="+i+"\")'><img src=\"gestione_documenti/images/new_file_icon.png\" width=\"20\"/><b>ALLEGA FILE</b></a></td>"+
			       "<td id = 'fileAllegato"+i+"'></td>"+
			     "</tr>"+*/
			   "</table>"+
		"</fieldset>").appendTo(".documenti");
			  }
		  }
			  $(".documenti").append($("<br></br>"));
		  	  $(".documenti").append($("<br></br>"));
			  $(".documenti").append($("<input></input>").attr("name","asl_in_possesso_documenti").attr("type","checkbox") );
			  $(".documenti").append($("<font></font>").css( { color: 'red','font-weight' : 'bold' } ).text("L'ASL e' in possesso dei documenti.") );  
		  }
	  else
		  {
		  $("<H1>DOCUMENTAZIONE NON NECESSARIA PER LE LINEE DI ATTIVITA SELEZIONATE</H1>").appendTo(".documenti");
		  }
  }
  
  function svuotaCampiRicerca(){
	  if (document.getElementById("searchattivita1")!=null)
		  document.getElementById("searchattivita1").value = '';
	  if (document.getElementById("searchattivita2")!=null)
		  document.getElementById("searchattivita2").value = '';
	  if (document.getElementById("searchattivita3")!=null)
		  document.getElementById("searchattivita3").value = '';
	  if (document.getElementById("searchattivita")!=null)
		  document.getElementById("searchattivita").value = '';
  }
  
  function aggiornaValoriDellaForm(formname){
	  var elems = document.getElementsByName(formname)[0].getElementsByTagName("input");
		for(var i = 0; i < elems.length; i++) {
		    // set attribute to property value
		    elems[i].setAttribute("value", elems[i].value);
		}

  }
 function azzeraCampiLinea(table){
	 var tabella = document.getElementById(table.id+"_ext");
	 //alert(tabella.id);
	 if (tabella!=null){
		var elems = tabella.getElementsByTagName("input");
		//alert("cccccc "+elems.length);
		for(var i = 0; i < elems.length; i++) {
			if(elems[i].type == "text"){
					elems[i].value='';
			}
		}
	 }
  }
 
 function openUploadAllegatoMobile(stabId,idElemento, nome){
	 		var sub = 'targa_'+ (parseInt(idElemento) + 1);
	 		if (nome!=null && !nome=='')
	 			sub = nome;
			window.open('GestioneAllegatiUploadSuap.do?command=ToAllegaFileSuapMobile&subject='+sub+'&stabId='+stabId+'&indice='+idElemento,null,
			'height=450px,width=480px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');
			} 
 

