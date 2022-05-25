/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

var ricerca = false;
var tipoAttivita_FISSA=1;
var tipoAttivita_MOBILE=2;
var tipoAttivita_APICOLTURA=3;

/*
var importedScript = document.createElement("script");
importedScript.src = "aggiuntaCampiEstesiScia.js";
document.head.appendChild(src);
*/



function mostraDatiStabilimento(valueTipoAttivita){
	if (valueTipoAttivita==tipoAttivita_MOBILE){
		// oscuriamo l'indirizzo dello stabilimento
		document.getElementById("datiIndirizzoStab").style.display="none";
		$("#viaStab").val("");
		if (document.getElementById("datiAttivitaMobile")!=null)
			document.getElementById("datiAttivitaMobile").style.display="";
	}else{ 
		
		if (valueTipoAttivita==tipoAttivita_APICOLTURA){
			// oscuriamo l'indirizzo dello stabilimento
			document.getElementById("datiIndirizzoStab").style.display="none";
			$("#viaStab").val("");
			if (document.getElementById("datiAttivitaMobile")!=null)
				document.getElementById("datiAttivitaMobile").style.display="";
			
		}
		else
			{
		// di default o per tipo attivita FISSA rendiamo visibile l'indirizzo dello stabilimento
		document.getElementById("datiIndirizzoStab").style.display="";
		if (document.getElementById("datiAttivitaMobile")!=null)
			document.getElementById("datiAttivitaMobile").style.display="none";
			}
	}
	
	
}

/*COPIA INDIRIZZO DA  RESIDENZA A SEDE LEGALE*/
function copiaDaResidenza(){
	if (document.getElementById("checkSeddeLegale").checked){
		
		
  		// $("#searchcodeIdprovincia").val($("#addressLegaleCountry").val());
  		
  		if ($("#codeIdComune").prop('readonly') )
  			{
  			
  				if ($("#codeIdComune").val() == $("#addressLegaleCitta").val())
  					{
  					$("#nazioneSedeLegale").val($("#nazioneResidenza").val());
  					$("#viainput").val($("#addressLegaleLine1input").val());
  			  		$("#via").val($("#addressLegaleLine1input").val());
  			  		$
  			  		$("#toponimoSedeLegale").val($("#toponimoResidenza").val());
  			  		$("#civicoSedeLegale").val($("#civicoResidenza").val());
  			  		$("#presso").val($("#capResidenza").val());
  			  		$("#searchcodeIdprovinciaSigla").val($("#addressLegaleCountrySigla").val());
  			  		$("#toponimoSedeLegaleId").val($("#toponimoResidenzaId").val());
  			  		$("#searchcodeIdprovinciainput").val($("#addressLegaleCountryinput").val());	
  			  		$("#searchcodeIdComuneId").val($("#addressLegaleCityId").val());
  					}
  				else
  					{
  					alert("NON E POSSIBILE SOVRASCRIVERE I DATI");

  					}
  			
  			}
  		else
  			{
  			$("#toponimoSedeLegaleId").val($("#toponimoResidenzaId").val());
  			$("#nazioneSedeLegale").val($("#nazioneResidenza").val());
  	  		$("#searchcodeIdprovinciainput").val($("#addressLegaleCountryinput").val());
  			
  			
	  		$("#codeIdComune").val($("#addressLegaleCitta").val());
	  		$("#searchcodeIdComuneId").val($("#addressLegaleCityId").val());
	  		$("#codeIdComune").val($("#addressLegaleCitta").val());
	  		
	  		$("#viainput").val($("#addressLegaleLine1input").val());
	  		$("#via").val($("#addressLegaleLine1input").val());
	  		$("#toponimoSedeLegale").val($("#toponimoResidenza").val());
	  		$("#civicoSedeLegale").val($("#civicoResidenza").val());
	  		$("#presso").val($("#capResidenza").val());
		    document.getElementById('presso').readOnly=true;
		    document.getElementById('civicoSedeLegale').readOnly=true;
		    document.getElementById('viainput').readOnly=true;
			document.getElementById('toponimoSedeLegale').disabled=true;
			document.getElementById('butCapSedeLegale').disabled=true;	
			document.getElementById('searchcodeIdprovinciaSigla').value=document.getElementById('addressLegaleCountrySigla').value;
			
			
  		
  			}
  	}else{
  		if ($("#codeIdComune").prop('readonly') ){}
  		else
  			{
  		$("#nazioneSedeLegale").val('106');
  		sbloccoProvincia('nazioneSedeLegale','searchcodeIdprovincia','searchcodeIdComune','via');
  		$("#searchcodeIdprovinciainput").val("");
  		$("#searchcodeIdprovincia").val("");
  		$("#codeIdComune").val("");
  		$("#searchcodeIdComune").val("");
  		$("#viainput").val("");
  		$("#via").val("");
  		$("#viainput").val("");
  			}
  	}
}
  


function checkStab(ricercapernumero) {

//$("#idStabilimento").val("-1");

	if ($("#partitaIvaVariazione")!=null && $("#partitaIvaVariazione").value!=undefined && $("#partitaIvaVariazione").value!='')
		{
		pIva =  $("#partitaIvaVariazione").val();
		
		
		}
	else
		{
		pIva =  $("#partitaIva").val();
		
		
		}

var numReg = "" ;
if ($("#numeroRegistrazioneVariazione") !=null)
	numReg = $("#numeroRegistrazioneVariazione").val();

var comune = $("#searchcodeIdComuneStabinput").val();
var indirizzo = -1;





if (ricercapernumero==true && numReg!="" )
$("#searchStab").html("<input type = \"hidden\" name = \"numeroRegistrazioneVariazione\" value = \""+numReg+"\"><input type = \"text\" name = \"partitaIva\" value = \""+pIva+"\">");
else	
	{
	if (numReg=="")
		{
		ricercapernumero = false;
		}
	
$("#searchStab").html("<input type = \"hidden\" name = \"searchcodeIdComuneStabinput\" value = \""+comune+"\"><input type = \"hidden\" name = \"partitaIva\" value = \""+pIva+"\">");

	}


if (comune!="")
{
	loadModalWindowCustom("Verifica Esistenza Sede Produttiva. Attendere");
	$.ajax({
		type: 'POST',
		dataType: "json",
		cache: false,
		 url: 'SuapStab.do?command=VerificaEsistenzaStabilimento',
		data: ''+$('form[name=searchStab]').serialize(), 
		success: function(msg) {
			var htmlText="" ;
			  if (msg!=null && msg!='' && msg.length >0)
    		  {
    		  resultStab = msg ; 
  			htmlText+="<div align=\"center\"><input type='button' value='Non voglio inserire nessuno di questi stabilimenti' onclick='closePopupStab()'></div><br><br>";

    		  htmlText+='<table border="1" class="pagedList"><tr><th>Denominazione</th><th>Partita IVa</th><th>Rappresentante Legale</th><th>Sede Legale</th><th>Sede Produttiva</th><th>Numero Registrazione</th><th></th></tr>'
        			var jsontext = JSON.stringify(msg);
        				for(i=0;i<msg.length;i++)
        				{
        					htmlText+="<tr><td>"+msg[i].operatore.ragioneSociale+"</td><td>"+msg[i].operatore.partitaIva+"</td><td>"+msg[i].operatore.rappLegale.nome+" "+msg[i].operatore.rappLegale.cognome+" "+msg[i].operatore.rappLegale.codFiscale+"</td><td>"+msg[i].operatore.sedeLegaleImpresa.descrizioneComune+" "+msg[i].operatore.sedeLegaleImpresa.descrizione_provincia+" "+msg[i].operatore.sedeLegaleImpresa.via+"</td><td>"+msg[i].sedeOperativa.descrizioneComune+"<br>"+msg[i].sedeOperativa.via +"</td><td>"+msg[i].numeroRegistrazione+"</td><td><input type='button' value='Seleziona' onclick='selezionaStabilimento("+i+")'></td></tr>";
        				}
        			
//        			htmlText+="<input type='button' value='Inserisci Nuova' onclick='sovrascriviImpresa()'> ";

        			$( "#dialogsuaplistastab" ).html(htmlText);    		 
        			$('#dialogsuaplistastab').dialog('open'); 
        			loadModalWindowUnlock();
    		  }
			else
			{
				if (msg.length==0 && ricercapernumero ==true)
					{
						if (confirm(("ATTENZIONE, NON E' STATO TROVATO UNO STABILIMENTO CON IL NUMERO DI REGISTRAZIONE  "+numReg+" ASSOCIATO ALLA PARTITA IVA "+pIva+". VUOI RICERCARLO TRAMITE PARTITA IVA E COMUNE ?")) )
							{
							loadModalWindowUnlock();
							checkStab(false);
							}
						else
							{
							loadModalWindowUnlock();
							}
					}
				else
					{
				alert("Stabilimento non trovato".toUpperCase());
				loadModalWindowUnlock();
					}
			}
			
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
  		
  			if ($("#searchcodeIdprovinciaStabinput").val()==$("#codeIdComune").val())
  				{
  		$("#searchcodeIdprovinciaStabinput").val($("#searchcodeIdprovinciainput").val());
  		$("#searchcodeIdprovinciaStab").append("<option value='"+$("#searchcodeIdprovincia").val()+ "' selected='selected'>"+$("#searchcodeIdprovinciainput").val()+"</option>');");
  		$("#searchcodeIdComuneStabinput").val($("#codeIdComune").val());
  		$("#searchcodeIdComuneStab").append("<option value='"+$("#searchcodeIdComune").val()+ "' selected='selected'>"+$("#codeIdComune").val()+"</option>');");
  		$("#searchcodeIdComuneStab").val($("#searchcodeIdComune").val());
  		$("#viaStab").val($("#via").val());
  		$("#viaStabinput").val($("#viainput").val());

  		$("#toponimoSedeOperativa").val($("#toponimoSedeLegale").val());
  		$("#civicoSedeOperativa").val($("#civicoSedeLegale").val());
  		$("#capStab").val($("#presso").val());
  				}
  			else
  				{
  				alert("L'INDIRIZZO DELLA SEDE PRODUTTIVA DEVE CORRISPONDERE CON IL COMUNE DEL SUAP");
  				}
  			}
  			else
  			{
  				if ($("#addressLegaleCountryinput").val()!='Napoli' && $("#addressLegaleCountryinput").val()!='Salerno' && $("#addressLegaleCountryinput").val()!='Caserta' && $("#addressLegaleCountryinput").val()!='Benevento' && $("#addressLegaleCountryinput").val()!='Avellino' )
  			{
  			alert('Impossibile copiare un indirizzo fuori Campania!'.toUpperCase());
  			document.getElementById("checkSeddeOperativa").checked=false;
  			return false;
  			}
  				
  				if ($("#searchcodeIdprovinciaStabinput").val()==$("#addressLegaleCitta").val())
  				{
  			$("#searchcodeIdprovinciaStabinput").val($("#addressLegaleCountryinput").val());
  	  		$("#searchcodeIdprovinciaStab").append("<option value='"+$("#addressLegaleCountry").val()+ "' selected='selected'>"+$("#addressLegaleCountryinput").val()+"</option>');");
  	  		$("#searchcodeIdComuneStabinput").val($("#addressLegaleCitta").val());
  	  		$("#searchcodeIdComuneStab").append("<option value='"+$("#addressLegaleCity").val()+ "' selected='selected'>"+$("#addressLegaleCitta").val()+"</option>');");
  	  		$("#searchcodeIdComuneStab").val($("#addressLegaleCity").val());
  	  	$("#viaStab").val($("#addressLegaleLine1").val());
  	  		$("#viaStabinput").val($("#addressLegaleLine1input").val());
  	  		$("#toponimoSedeOperativa").val($("#toponimoResidenza").val());
  	  		$("#civicoSedeOperativa").val($("#civicoResidenza").val());
  	  		$("#capStab").val($("#capResidenza").val());
  			}
  			
  			else
  				{
  				alert("L'INDIRIZZO DELLA SEDE PRODUTTIVA DEVE CORRISPONDERE CON IL COMUNE DEL SUAP");
  				}
  			checkStab();
  			
  		}}
  	else
  		{
  		
  		if ($("#searchcodeIdComuneStabinput").prop('readonly') )
  			{}
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

/*FUNZIONI PER IL TAB DELLE ATTIVIT PRODUTTIVE. COSTRUISCE LA LISTA DELLE ATTIVITA PER STEP*/
function checkNext(tab)
{
	if($("#"+tab+"").find('select').val()=='')
		{
		alert( ('Controllare di aver selezionato una voce per '+$("#"+tab+"").find('select').attr('label')).toUpperCase() );
		return false ;
		}
	return 'true' ;
}

var idTab;
var livelloIn;
var tipoRegistrazione ;


function mostraAttivitaProduttive(idTable,livello,idSelezionato, cerca,tipoInserimento)
  {
	

	// alert(livello);
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
	 
	 tipoImpresa = document.getElementById("tipo_impresa").value;
	  
  	  SuapDwr.mostraAttivitaProduttive(livello,idSelezionato,tipoInserimento,idTipoAttivita,tipoImpresa,{async:false,callback:mostraAttivitaProduttiveCallBack});
		
	
  }

	

var richiestaEsistente = false;
var listaParametriRicerca = null ;
var tipoOp = null;
function verificaEsistenzaRichiesta()
{
	
pIva 			= 	 document.getElementById("partitaIva").value ;
comuneRichiesta	=  	 document.getElementById("comuneSuap").value ;
viaStab	 		= 	 document.getElementById("viaStabinput").value ;	
civicoStab 		=    document.getElementById("civicoSedeOperativa").value ;
toponimoStab	= 	 document.getElementById("toponimoSedeOperativa").value ;
tipoOp	=	 document.getElementById("operazioneScelta").value ;
var idTipoRichiesta = -1;
if (tipoOp=='new')
	idTipoRichiesta =1;

		if (tipoOp=='amliamento')
			idTipoRichiesta =	2;	
				
				if (tipoOp=='cessazione')
					idTipoRichiesta =	3;
						
						if (tipoOp=='cambioTitolarita')
							idTipoRichiesta =		4;						
	
numReg	= '';
if (document.getElementById("numeroRegistrazioneVariazione")!=null)
	numReg=document.getElementById("numeroRegistrazioneVariazione").value ;


SuapDwr.verificaEsistenzaRichiesta(pIva, comuneRichiesta, idTipoRichiesta, viaStab, civicoStab,toponimoStab,numReg,{callback:allarm,async:false});

return richiestaEsistente ;
	


}

function allarm(value)
{
	listaParametriRicerca = value;
	if (value[0] =='true')
	richiestaEsistente = true ;
	else
		richiestaEsistente = false ;
}

function eliminaLineaCorrente(idTabDel)
{

	
//	var el  = document.getElementById(idTabDel.id+"_ext");
//	el.parentNode.removeChild(el);
	
	idTabDel.parentNode.removeChild(idTabDel);	
	
	document.getElementById('validatelp').value = "true";
	/*
	if($("input[name=idLineaProduttiva2]").length==0)
		document.getElementById('validatelp').value="false";
		else
			{
			if(($("input[name=idLineaProduttiva2]").length==$("input[name=idLineaProduttiva]").length) ||$("input[name=idLineaProduttiva2]").length==0)
			{
				document.getElementById('validatelp').value="true";
			}
			}
	*/
	
//	$('#'+idTabDel).remove();
//$("#menu"+idTabDel).remove();
}
  
  function mostraAttivitaProduttiveCallBack(attivita)
  {
	  
	  if(typeof livelloIn != 'undefined')
	  {
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
	  		if($("#"+idTab+"").find("div").html()!=null)
	  		{
	  			pathSelezionato = $("#"+idTab+"").find("div").html()+'\n\n <p style="color:'+attivita.bgcolorPrec+'">'+$("#"+idTab+"").find('select option:selected').text()+'</p>';
	  			
	  			if (ricerca==true && document.getElementById("searchattivita"+(livelloIn-1))!=null)
	  			{
	  				var ric = document.getElementById("searchattivita"+(livelloIn-1));
	  				ric.value = $("#"+idTab+"").find('select option:selected').text();
	  			}
	  		}	
	  		$("#"+idTab+" tr").remove();
	  		$("#documento table").remove();
	  		$("#"+idTab+"").append('<tr><td align="left" colspan="2" width="100%"><div style="width:100%;">'+pathSelezionato+'</div></td><td id="valida'+idTab+'" aligh="right"></td><td></td></tr>');
	  		var combo = "<select onchange='if(checkNext(\""+idTab+"\")==\"true\"){mostraAttivitaProduttive(\""+idTab+"\",\""+attivita.nextLivello+"\",this.value, "+ricerca+","+tipoRegistrazione+");}' style='color:"+attivita.bgcolor+";width:100%;' label='"+attivita.label+"' foglia='false' name='idLineaProduttiva2' id='"+attivita.label+"'>" ;
	  		combo+="<option style='color:"+attivita.bgcolor+"' value='' selected='selected'>Seleziona "+attivita.label+"</option>";
	  		for (i=0;i<attivita.listaItem.length;i++)
	  		{
	  			combo+="<option style='color:"+attivita.bgcolor+"' value='"+attivita.listaItem[i].id+"'>"+attivita.listaItem[i].descizione+"</option>";
	  		}
	  		combo+="</select>";
	  		$("#"+idTab+"").append('<tr><td align="left" style="width:10%"><p style="color:'+attivita.bgcolor+'">'+attivita.label+'</p></td><td align="left" style="width:60%">'+combo+'</td><td  id="menu'+idTab+'" style="width:15%" align="left"><input type="button" style="width:35%" value="Elimina" onclick="mostraAttivitaProduttive(\''+idTab+'\',1,-1, '+ricerca+',\''+tipoRegistrazione+'\'></td></tr>');




	  		$("#menu"+idTab).append('<input type="button" value="Elimina" onclick="mostraAttivitaProduttive(\''+idTab+'\',1,-1, '+ricerca+',\''+tipoRegistrazione+'\'); azzeraCampiLinea('+idTab+');">');

	  		if (idTab!='attprincipale' && window.location.href.indexOf("LineePregresse")<=0)
	  		{
	  			//alert(idTab);
	  			$("#menu"+idTab).append('<input type="button" value="Annulla" onclick="eliminaLineaCorrente('+idTab+');">');
	  		}
	  		
	  		document.getElementById('validatelp').value="false";
  		}
  	else
  		{	
	  		if( $("#"+idTab+"").find("div")!=null)
	  		{
	  			
				pathSelezionato= $("#"+idTab+"").find("div").html()+'\n\n <p style="color:'+attivita.bgcolorPrec+'">'+$("#"+idTab+"").find('select option:selected').text()+'</p>';
			}
	  		$("#"+idTab+"").find("div").html(pathSelezionato+"<input type='hidden' name='idLineaProduttiva' value='"+ $("#"+idTab+"").find("select option:selected").val()+"'>");
	  		$("#menu"+idTab).html('<input type="button" value="Elimina" onclick="mostraAttivitaProduttive(\''+idTab+'\',1,-1, '+ricerca+',\''+tipoRegistrazione+'\'); azzeraCampiLinea('+idTab+');">');
	  		$("#valida"+idTab).html('&nbsp;&nbsp;<img width="50px" height="50px" src="css/suap/images/ok3.png">');
	  		
	  		if(!window.isAggiornaLineePregresse || $("input[name=numeroLinee]").val() ==$("input[name=idLineaProduttiva]").length )
	  			document.getElementById('validatelp').value="true";
	  		
	  		$("#"+idTab+"").find('select').attr("label",$("#"+idTab+"").find('select option:selected').text());
	  		$("#"+idTab+"").find('select').val($("#"+idTab+"").find('select option:selected').val());
	  		$("#"+idTab+"").find('select').attr("disabled","disabled"); 
	  		
	  		/*****************************************************************************************************************/
//	  		if( tipoOp != "cessazione" )
//	  			aggiungiCampiEstesi(attivita,idTab);	
	  		
	  		
	  		
	  		/*****************************************************************************************************************/
	  		
	  		
  		}
  	if(livelloIn=='1')
		$("#"+idTab+"").find("div").html('');
   
  	
  	
  	
  	
  }
  var numSecondarie = 0;
  
  function aggiungiCampiLinea(numSecondarie){
	  $("#secondarie").html($("#secondarie").html()+'<table style="width: 100%;" id="attsecondarie'+numSecondarie+'_ext"><tr id = "trDataInizioLinea"><td>DATA INIZIO</td><td><input type="text" size="15" class="required" name="dataInizioLinea'+numSecondarie+'" id="dataInizioLinea'+numSecondarie+'"  placeholder="dd/MM/YYYY"></td></tr><tr id = "trDataFineLinea"><td>DATA FINE</td><td><input type="text" size="15" name="dataFineLinea'+numSecondarie+'" id="dataFineLinea'+numSecondarie+'" placeholder="dd/MM/YYYY"></td></tr></table><br><br>');	
		$("#secondarie").html($("#secondarie").html()+"<script> $(function() {	$('#dataInizioLinea"+numSecondarie+"').datepick({dateFormat: 'dd/mm/yyyy',  maxDate: 0, showOnFocus: false, showTrigger: '#calImg'}); $('#dataFineLinea"+numSecondarie+"').datepick({dateFormat: 'dd/mm/yyyy', showOnFocus: false, showTrigger: '#calImg', onClose: controlloDate }); }); </script>");
  }

  function aggiungiRiferimentoTabella(tipoinserimento)
{
	aggiornaValoriDellaForm('addstabilimento');
	if(document.getElementById('validatelp').value=='true' ||  ($( "#methodRequest" ).val()=='ampliamento' || $( "#methodRequest" ).val()=='cessazione' ||  $( "#methodRequest" ).val()=='sospensione') )//|| $( "#methodRequest" ).val()=="ampliamento" )
	{
				numSecondarie+=1;
		
		
			//affinchè vengano mantenuti gli stati checked di tutti i campi (estesi) delle tabelle precedenti
			//prendo tutti i checkbox checked in tutte le tabelle attsecondarieID
			var toRicheck = [];
			for(var p = 1; p <= numSecondarie; p++)
			{
				var ts = $("table#attsecondarie"+p+" input[type='checkbox']:checked");
				for(var q = 0; q<ts.length;q++)
				{
					toRicheck.push(ts[q]);	
				}
			}//e li risetterò checked dopo l'injection della nuova tabella (che li faceva saltare)....
			
			
				
			//$("#secondarie").html($("#secondarie").html()+'<table id="attsecondarie'+numSecondarie+'" style="width: 100%;"<tr><td></td></tr></table>')
			var idNuovaTab = 'attsecondarie'+numSecondarie;
			$("#secondarie").append(
					$("<table></table>"
					,{
						id : idNuovaTab
						,style : 'width : 100%'
						
					})
					.append(
							$("<tr></tr>")
							.append("<td></td>")
					)
			);
			
			mostraAttivitaProduttive(idNuovaTab ,1,-1, ricerca,tipoinserimento);
			
			 
			//risetto i checked che erano saltati
				for(var p = 0; p< toRicheck.length; p++)
				{
					console.log(toRicheck[p].name + " e ' "+toRicheck[p].checked);
					toRicheck[p].checked = true;
				}
			 
			//aggiungiCampiLinea(numSecondarie);
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
	  if(document.getElementById("methodRequest").value!='cessazione')
		  {
	  for (i=0; i <array.length; i++)
		  idLinee[i]=array[i].value ;
	  SuapDwr.mostraDocumentazioneAttivitaProduttive(idLinee,mostraListaDocumentiAttivitaProduttiveCallBack);
		  }
  	//SuapDwr.mostraAttivitaProduttive(1,mostraListaDocumentiAttivitaProduttiveCallBack);
  }


function mostraListaDocumentiAttivitaProduttiveCallBack(listaDocumenti)
  {
	  var htmltoappend = '' ;
	  $(".documenti").html("");
	  if(listaDocumenti.length>0 || document.getElementById("operazioneScelta").value == "modificaStatoLuoghi" )
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
			     "<tr><td colspan='2'>"+listaDocumenti[i].allegatoDescrizione+"</td></tr>"
			     /*+
			     "<tr class='containerBody'>" +
			     "<td colspan='2'>" +
			     	"<input type=\"text\" name=\"subject"+i+"\" size=\"59\" maxlength=\"255\" readonly value=\""+listaDocumenti[i].allegatoNome+"\">" +
			     				"</td></tr><tr>"+
			       "<td id = 'fileAllegato"+i+"'>" +
			       		"<input type=\"file\" required id=\"file"+i+"\" name=\"file"+i+"\" size=\"45\" onChange=\"checkFile(this)\" > " +
			       			
			       		"</td>"+
			     "</tr>"*/+
			   "</table>"+
		"</fieldset>").appendTo(".documenti");
			  }
		  }
	  
	  if( document.getElementById("operazioneScelta").value == "modificaStatoLuoghi" )
	  {
		  $("<fieldset>"
				  +"<legend>"+"ALLEGATO NUOVA PLANIMETRIA"+"</legend>"
				  +"<table cellpadding='4' cellspacing='0' border='0' width='100%' class='details'>"+
				     "<tr><th colspan='2'><img border='0' src='images/file.gif' align='absmiddle'><b>Descrizione Allegato</b></th>"+
				     "</tr>"+
				     "<tr><td colspan='2'>"+"ALLEGATO CONTENENTE LA NUOVA PLANIMETRIA"+"</td></tr>"+
				     /*"<tr class='containerBody'>" +
				     "<td colspan='2'>" +
				     	"<input type=\"text\" name=\"subject"+i+"\" size=\"59\" maxlength=\"255\" readonly value=\""+"allegato planimetria"+"\">" +
				     				"</td></tr><tr>"+
				       "<td id = 'fileAllegato"+i+"'>" +
				       		"<input type=\"file\" required id=\"file"+i+"\" name=\"file"+i+"\" size=\"45\" onChange=\"checkFile(this)\"> " +
				       			
				       		"</td>"+
				     "</tr>"+*/
				   "</table>"+
				  +"</fieldset>").appendTo(".documenti");
	  }
		  
	  $(".documenti").append($("<br></br>"));
  	  $(".documenti").append($("<br></br>"));
	  		$(".documenti").append($("<input></input>").attr("name","asl_in_possesso_documenti").attr("type","checkbox") );
	  	  
		  $(".documenti").append($("<font></font>").css( { color: 'red','font-weight' : 'bold' } ).text("L'ASL e' in possesso dei documenti.") );  
		
		  }
	  else
		  {
		  $("<H1>DOCUMENTAZIONE NON NECESSARIA</H1>").appendTo(".documenti");
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
 
 


 $(function (){
     $('form[name=addstabilimento]').submit(function(e){
    	 
    	
    	 var data = new FormData(jQuery('form')[0]);
//    	 var data =   $(this).serialize();
             loadModalWindowCustom("Salvataggio In Corso. Attendere");
             e.preventDefault();
             $.ajax({
            	 
            	 url: 'SuapStab.do?command=InsertSuap&auto-populate=true',
                 type: 'POST',
                 data: data ,//''+$(this).serialize(),
                 async: false,
                 cache: false,
                 contentType: false,
                 processData: false,
            	
                 success: function(msg) {
                 alert(msg.erroreSuap);
//                 alert(msg.codiceErroreSuap);
                 
                 if (msg.codiceErroreSuap =="0"){
                	 
                	 var save = wizard.find(".actions a[href$='#saveTemp']").parent();
                	 save._showAria(false);
                     
                     var previous = wizard.find(".actions a[href$='#previous']").parent();
                     previous._showAria(false);
                     
                //     document.getElementById("test2").src = "./schede_centralizzate/iframe.jsp?objectId="+msg.altId+"&objectIdName=alt_id&tipo_dettaglio=28"
                     
                     document.getElementById("test2").innerHTML = "OPERAZIONE COMPLETATA"
                     
                     document.getElementById("pratica_completa").value="1";
                	 
                 //    document.getElementById("divStampa").innerHTML="<input type = \"button\" value=\"Stampa Riepilogo Scheda\" onclick=\"openRichiestaPDFOpuRichiestaAnagrafica('"+ msg.altId+"', '28',null)\"/>";
                     
                     
                   
                	
                	 window.listaDatiRichiesta = {} //questo oggetto verrà utilizzato quando si premera'
                	 //il bottone torna al suap di suap_form.jsp
                	 
                	if( msg != null && msg.beanPerXml!=null)
                		{
                	 window.listaDatiRichiesta["tot_entries"] = msg.beanPerXml.listaEntries.length;
                	 window.listaDatiRichiesta["entries"] = new Array();
                	 //console.log(msg);
                	 //console.log(JSON.stringify(msg));
                	 
                	 //estraggo le entry associate alle informazioni della richiesta
                	 for(var i = 0; i< msg.beanPerXml.listaEntries.length; i++)
                	 {
                	      var entry = msg.beanPerXml.listaEntries[i];
             
                	      window.listaDatiRichiesta["entries"][i] = entry;
                	 }
                		}
                     
                 window.location.href='GisaSuapStab.do?command=Default';
                 }
                 else if (msg.codiceErroreSuap =="3"){
                	 if (confirm (msg.erroreSuap+'\n Si vuole proseguire lo stesso in questa operazione?')){
                		  document.getElementById("controlloEsistenzaStabilimento").value="ok";
                		 $('form[name=addstabilimento]').submit();
                	 }
                		 
                	 else
                		 alert('Operazione annullata.');
                 }
                 else
                	 {
                	 
//                	 alert(msg.codiceErroreSuap);
                	 }
                
                 loadModalWindowUnlock();
             },
             error: function (err){
                     alert('ko '+err.responseText);
             }
     });
 });
 });

 
 
 function cancellaFile(element,url,indice){
     $.ajax({
         url:url,
     async: false,
     success :  function(data){
             // Your Code here
                     alert("Allegato Eliminato Correttamente!".toUpperCase())
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

 function rimuoviSpazi(campo){

	 var nsText = campo.value;
	 nsText = nsText.replace(/(\n\r|\n|\r)/gm,"<1br />");
	 nsText = nsText.replace(/\t/g,"");

	 re1 = /\s+/g;
	 nsText = nsText.replace(re1," ");

	 re2 = /\<1br \/>/gi;
	 nsText = nsText.replace(re2, "\n");

	 campo.value = nsText;
	 }
 
 
 
 
 
 
