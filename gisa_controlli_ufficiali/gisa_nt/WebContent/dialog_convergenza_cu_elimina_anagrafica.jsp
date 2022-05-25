<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%--
<jsp:useBean id="AnagraficaStabilimento" class="org.aspcfs.modules.ricercaunica.base.RicercaOpu" scope = "request">
</jsp:useBean>
 --%>
<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope = "request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>


<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>
<script>

var infosAggiuntiviCandidatoLineaVecchia =
{ //la chiave è id linea vecchia, il valore associato è un oggetto { idlineaattivita_IIIlivello_candidato : idcandidatoIIIlivello,
		 														//    dati: { lvlAggiuntivoRaggiunto : , pathAccumulato : } }	
};
</script>



<div id="dialogRICERCA">
</div>




<script>

$( "#dialogRICERCA" ).dialog({
	autoOpen: false,
    resizable: false,
    closeOnEscape: false,
   	title:"CONVERGENZA - RICERCA ANAGRAFICA VERSO CUI CONVERGERE",
    width:1250,
    height:1000,
    draggable: false,
    modal: true
   
}).prev(".ui-dialog-titlebar");





var actionGlobal;
	
	
	
	var toUrl='';
function apriRisultatiRicercaOperatore()
{
	toUrl='RicercaUnica.do?command=Search';
	form =document.getElementById("searchAccount");
	if (form.tipoOperazione!=null)
	{
		$("#LoadingImage").show();
		$.ajax({
	    	type: 'POST',
	   		dataType: "html",
	   		async: false,
	   		cache: false,
	  		url: toUrl,
	        data: $('#searchAccount').serialize(), 
	    	success: function(msg) {
	    		
	    		document.getElementById('dialogRICERCA').innerHTML=msg ; 
	       	 	$("#LoadingImage").hide();      	 		
	       		$('#dialogRICERCA').dialog('open');
	       		
	   		},
	   		error: function (err, errore) {
	   			alert('ko '+errore);
	   		 	$("#LoadingImage").hide();	   		 	
	        }
			});
	}
	else
		form.submit();
	
}

function eseguiConvergenzaAnagrafiche(action)
{
	toUrl='RicercaUnica.do?command=CovergenziAnagrafiche';
	form =document.getElementById("convergenza");
	$("#LoadingImage").show();
	$.ajax({
    	type: 'POST',
   		dataType: "json",
   		async: false,
   		cache: false,
  		url: toUrl,
        data: $('#convergenza').serialize(), 
    	success: function(msg) {
    		
    		
    		alert(msg.msgConvergenza);
    		$("#LoadingImage").hide()
    		if(msg.esitoConvergenza==1)
    		{
    			loadModalWindowCustom("Attendere Prego...");
    			$('#dialogRICERCA').dialog('close');
    			location.href=action ;
    		}
       		
   		},
   		error: function (err, errore) {
   			alert('ko '+errore);
   		 	$("#LoadingImage").hide();
        }
		});}
		
	
	


function mostraRisultatiInVecchiaAnagrafica(fieldCheck)
{
	if(fieldCheck.checked)
		{
		$('.noOpu').css('display','');
		}
	else
		{
		$('.noOpu').css('display','none');
		}
	}
	


	
function apriRicercaOperatore(rifId,rifIdNome)
{
	toUrl='RicercaUnica.do?command=SearchForm';
	loadModalWindowCustom("Attendere Prego...");
	$.ajax({
    	type: 'POST',
   		dataType: "html",
   		async: false,
   		cache: false,
  		url: toUrl,
        data: { "Popup": true,"tipoOperazione":1,"rifId":rifId,"rifIdNome":rifIdNome} , 
    	success: function(msg) {
    		
    		
       		document.getElementById('dialogRICERCA').innerHTML=msg ; 
       		loadModalWindowUnlock();
       		$('#dialogRICERCA').dialog('open');
   		},
   		error: function (err, errore) {
   			alert('ko '+errore);
   			loadModalWindowUnlock();
        }
		});
	
}



function sceltaDestinazioneConvergenza(rifIdOrigine,rifIdNomeOrigine,rifIdDestinazione,rifIdNomeDestinazione,form,tipoAttivita1,tipoAttivita2)
{
	
	if(tipoAttivita1==2 || tipoAttivita2== 2)
	{
		alert("ATTENZIONE! LO STABILIMENTO DI ORIGINE O DESTINAZIONE HANNO COME  TIPO DI ATTIVITA MOBILE. ATTUALMENTE QUESTI TIPI DI STABILIMENTI SONO GESTITI DALL'HELP DESK DI SECONDO LIVELLO");
	
	}
	else
		{
	
	toUrl = 'RicercaUnica.do?command=SceltaDestinazioneCovergenza';
	$("#LoadingImage").show();
	$.ajax({
    	type: 'POST',
   		dataType: "html",
   		async: false,
   		cache: false,
   		url: toUrl,
        data: { "Popup": true,"tipoOperazione":1,"rifIdOrigine":rifIdOrigine,"rifIdDestinazione":rifIdDestinazione,"rifNomeColonnaOrigine":rifIdNomeOrigine,"rifNomeColonnaDestinazione":rifIdNomeDestinazione} , 
    	success: function(msgSel) {
    		
    		
       		document.getElementById('dialogRICERCA').innerHTML=msgSel ; 
       		$("#LoadingImage").hide();
       		$('#dialogRICERCA').dialog('open');
       		
       		$( document ).ready(function() {
       		  
       			indice = 1 ;
       			while (document.getElementById("attprincipale"+indice)!=null)
       				{
       				mostraAttivitaProduttive('attprincipale'+indice,1,-1, false,-1);
       				indice++;
       				}
       			
       		});
       		
       		
       		
   		},
   		error: function (err, errore) {
   			alert('ko '+errore);
   			$("#LoadingImage").hide();
        }
		});
		}
	
}

function annullaLineeProduttive(tab)
{
	
	mostraAttivitaProduttive(tab,1,-1, false,-1);
	}


function clickBtnAnnulla(evento)
{
	var nomeTab = evento.data.nometab;
	//setto a false la possibilità di proseguire
	

	//elimino l'entry nella struttura dati
	delete infosAggiuntiviCandidatoLineaVecchia[evento.data.idvecchialinea];
	//risimulo il check su quello che era il candidato già attualmente selezionato, questo lo farà resettare
	var idcandIII = evento.data.idcandidatoIIIlivello;
	console.log(nomeTab+" "+idcandIII); 
	 
	$("table#"+nomeTab+" input#"+evento.data.idvecchialinea+"-"+idcandIII+"[type='radio']:checked").trigger("click");
	//risetto come value del radio button il valore del candidato del III livello (in realtà non serve a nulla poichè verrà di nuovo risovrascritto, ma per un fatto di pulizia...)
	$("table#"+nomeTab+" input#"+evento.data.idvecchialinea+"-"+idcandIII+"[type='radio']:checked").val(idcandIII);
	 
	
	 
	
}

function preparaPerRiempimentoCampiSceltaLivelliAggiuntiviRanked(nometab_ranks,idvecchialineaI,idcandidatoIIIliv)
{
	$("table#"+nometab_ranks+" tr[name='tr_path_aggiuntivo']").html("<td colspan=\"3\"><span style=\"background-color: rgba(20,255,20,0.5)\" name=\"span_path_aggiuntivo\"><i>LIVELLI AGGIUNTIVI RICHIESTI...</i></span></td>"); 
	$("table#"+nometab_ranks+" tr[name='tr_select_aggiuntivi']").html("<td colspan = \"3\" ><select name=\"select_agg\"></select><button type=\"button\" name=\"btn_annulla_agg\">ANNULLA</button></td>");
	
	//posso mettere l'handler sul bottone annulla già da qui
	$("table#"+nometab_ranks+" button[name='btn_annulla_agg']").click({ nometab : nometab_ranks , idvecchialinea : idvecchialineaI , idcandidatoIIIlivello : idcandidatoIIIliv },clickBtnAnnulla);

}



var idTab;
var livelloIn;
var tipoRegistrazione ;
var numeroLinee = 0 ;

function checkNext(tab)
{
	if($("#"+tab+"").find('select').val()=='')
		{
		alert(('Controllare di aver selezionato una voce per '+$("#"+tab+"").find('select').attr('label')).toUpperCase());
		return false ;
		}
	return 'true' ;
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
	  
	  tipoImpresa=document.getElementById("tipo_impresa").value;
  	SuapDwr.mostraAttivitaProduttive(livello,idSelezionato,tipoInserimento,idTipoAttivita,tipoImpresa,{async:false,callback:mostraAttivitaProduttiveCallBack});
  }
  

//livello è il livello per il quale fare la ricerca
//id combo è dove mettere i risultati
//idselezionato è l'id attività del livello precedente livello



 





var previstoCodiceNazionale =false;
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
  	var combo = "<select onchange='if(checkNext(\""+idTab+"\")==\"true\"){mostraAttivitaProduttive(\""+idTab+"\",\""+attivita.nextLivello+"\",this.value, "+ricerca+","+tipoRegistrazione+");}' style='color:"+attivita.bgcolor+";width:100%;'  label='"+attivita.label+"' foglia='false' name='idLineaProduttiva2' id='"+attivita.label+"'>" ;
  	combo+="<option style='color:"+attivita.bgcolor+"' value=''  selected='selected'>Seleziona "+attivita.label+"</option>";
  	for (i=0;i<attivita.listaItem.length;i++)
  		{
  		combo+="<option style='color:"+attivita.bgcolor+"' value='"+attivita.listaItem[i].id+"'>"+attivita.listaItem[i].descizione+"</option>";
  		}
  	combo+="</select>";
  	
  	$("#"+idTab+"").append('<tr><td align="left" style="width:10%"><p style="color:'+attivita.bgcolor+'">'+attivita.label+'</p></td><td align="left" style="width:60%">'+combo+'</td><td  id="menu'+idTab+'" style="width:15%" align="left"><input type="button" style="width:35%" value="Annulla" onclick="mostraAttivitaProduttive(\''+idTab+'\',1,-1, '+ricerca+',\''+tipoRegistrazione+'\'></td></tr>');

	
		$("#menu"+idTab).html('<input type="button" value="Annulla" onclick="annullaLineeProduttive('+idTab+'); azzeraCampiLinea('+idTab+');">');

  		document.getElementById('validatelp').value="false";
  		if(previstoCodiceNazionale!=true && previstoCodiceNazionale !='true')
  			previstoCodiceNazionale = attivita.previstoCodiceNazionale;
  		
  		}
  	else
  		{
  		if( $("#"+idTab+"").find("div")!=null){
			pathSelezionato= $("#"+idTab+"").find("div").html()+'\n\n <p style="color:'+attivita.bgcolorPrec+'">'+$("#"+idTab+"").find('select option:selected').text()+'</p>';
			}
  		
  		$("#"+idTab+"").find("div").html(pathSelezionato+"<input type='hidden' name='idLineaProduttiva' value='"+ $("#"+idTab+"").find("select option:selected").val()+"'>");
  		
  		
  		$("#menu"+idTab).html('<input type="button" value="Annulla" onclick="annullaLineeProduttive(); azzeraCampiLinea('+idTab+');">');
  		$("#valida"+idTab).html('&nbsp;&nbsp;<img width="50px" height="50px" src="css/suap/images/ok3.png">');
  			
  		
  		
  		if($("input[name=importOp]") !=null && $("input[name=importOp]").val()=='si')
  			{
  			
  		if($("input[name=numeroLinee]").val() ==$("input[name=idLineaProduttiva]").length )
  			document.getElementById('validatelp').value="true";
  			}
  		else
  			document.getElementById('validatelp').value="true";
  		
  			$("#"+idTab+"").find('select').attr("label",$("#"+idTab+"").find('select option:selected').text());
  			$("#"+idTab+"").find('select').val($("#"+idTab+"").find('select option:selected').val());
  		$("#"+idTab+"").find('select').attr("disabled","disabled");
  		
  		
  	
  		}
	  	
 
  	
  	
  	
  	
  	
  	
  	
  }
  
  
function candidatoScelto(nometab_ranks,idLineaOrg,idLineaIIILivelloCandidato,tipoInserimento,nomeradioinput)
{
	 
	 console.log(infosAggiuntiviCandidatoLineaVecchia);
	 if(infosAggiuntiviCandidatoLineaVecchia[idLineaOrg] != undefined )
	 {
		 
		 var idCandidatoPrecedente = infosAggiuntiviCandidatoLineaVecchia[idLineaOrg].idlineaattivita_IIIlivello_candidato;
		 if( idLineaIIILivelloCandidato === idCandidatoPrecedente)
		 {
			 console.log("hai riscelto stesso candidato per stessa linea vecchia, non faccio nulla !");
			 return;
		 }
		
	 }
	  
	console.log("candidato nuovo !");
	
	
	$("table#"+nometab_ranks+" input[type='hidden'][name='idLineaProduttiva']").remove();
	
	infosAggiuntiviCandidatoLineaVecchia[idLineaOrg] = {
			
			idlineaattivita_IIIlivello_candidato : idLineaIIILivelloCandidato,
			dati : {lvlAggiuntivoRaggiunto : 3 , pathRaggiunto : ""}
		
	}; 
	
	console.log(infosAggiuntiviCandidatoLineaVecchia[idLineaOrg].idlineaattivita_IIIlivello_candidato);
	//resetto il contenuto delle righe per scelta livelli aggiuntivi e display path
	//preparaPerRiempimentoCampiSceltaLivelliAggiuntiviRanked(nometab_ranks,idLineaOrg,idLineaIIILivelloCandidato);
	
	
	//mando richiesta per ottenere linee oltre il terzo livello
	ottieniSoloAttivitaProduttiveOltreTerzoLivello(nometab_ranks,4,idLineaOrg,idLineaIIILivelloCandidato,idLineaIIILivelloCandidato,tipoInserimento,nomeradioinput);
	
	//cambio opacity a seconda che sia quella selezionata o meno
	$("table#"+nometab_ranks+" tr.trEntryCandidato").css("opacity",0.5);
	$("table#"+nometab_ranks+" tr.trEntryCandidato").css("background-color","#ffffff");
	$("table#"+nometab_ranks+" tr#trPerEntryCandidato"+idLineaOrg+"-"+idLineaIIILivelloCandidato).css("opacity","1.0");
	$("table#"+nometab_ranks+" tr#trPerEntryCandidato"+idLineaOrg+"-"+idLineaIIILivelloCandidato).css("background-color","rgba(20,255,20,0.5");
	 
	
	  
}


function ottieniSoloAttivitaProduttiveOltreTerzoLivello(nomeTabella,livello,idvecchialinea,idcandidatoIIILiv,idSelezionato,tipoInserimento,nomeradioinput)
{
	var idTipoAttivita = -1 ;
	if (document.getElementById("tipoAttivita")!=null)
		 idTipoAttivita = document.getElementById("tipoAttivita").value;
	
	SuapDwr.mostraAttivitaProduttive(livello,idSelezionato,tipoInserimento,idTipoAttivita,-1,{async:false,callback:
		
		function(attivita)
		{
			 
			/*if(livello > 4 && livello < 7 )
				attivita.listaItem.push( {id : '1111', descrizione :'dummy'+livello } );*/
			
			if(attivita.listaItem.length > 1) //1 perchè c'e' sempre una dummy label inserita dal server
			{
				
				preparaPerRiempimentoCampiSceltaLivelliAggiuntiviRanked(nomeTabella,idvecchialinea,idcandidatoIIILiv);
				//svuoto la select
				$("table#"+nomeTabella+" select[name='select_agg']").html("");
				//la riempio
				//metto etichetta "sceglimi"
				$("table#"+nomeTabella+" select[name='select_agg']").append(
						$("<option>",{
	 							value : -1,
	 							text : 'scegli valore'
								}
						)
				);
				
				
				
				for (i=0;i<attivita.listaItem.length;i++)
		  		{
					if(attivita.listaItem[i].descizione == undefined || attivita.listaItem[i].descizione.trim() == '') 
						continue; //perchè questa è una dummy del tipo "scegliere valore" inserita dal server, mentre io usero' la mia dummy label, quindi questa la salto
					
					$("table#"+nomeTabella+" select[name='select_agg']").
							append(
							$('<option>',{
										value : attivita.listaItem[i].id,
										text : attivita.listaItem[i].descizione.toUpperCase()
										  }
							));
		  		}
				
				
				//metto handler sulla selezione 
				$("table#"+nomeTabella+" select[name='select_agg']").change(
						function()
						{
							
							var idNuovoScelto = $(this).val();
							var textLab = $(this).find("option:selected").text();
							console.log(idNuovoScelto + " " + textLab);
							
							if(idNuovoScelto == '-1')
								return;
							
							//aggiorno i dati nella struttura
							infosAggiuntiviCandidatoLineaVecchia[idvecchialinea].dati.lvlAggiuntivoRaggiunto = livello;
							var vecchioPath = infosAggiuntiviCandidatoLineaVecchia[idvecchialinea].dati.pathRaggiunto;
							
							infosAggiuntiviCandidatoLineaVecchia[idvecchialinea].dati.pathRaggiunto = vecchioPath + ( (vecchioPath != "-") ? "-" : "") + textLab;
							//aggiorno la gui
							$("table#"+nomeTabella+" span[name='span_path_aggiuntivo']").html("<B>LIVELLI AGGIUNTIVI: </B>"+infosAggiuntiviCandidatoLineaVecchia[idvecchialinea].dati.pathRaggiunto);
							
							
							//lancio richiesta per aggiornamento livello successivo
							ottieniSoloAttivitaProduttiveOltreTerzoLivello(nomeTabella,livello+1,idvecchialinea,idcandidatoIIILiv,idNuovoScelto,tipoInserimento,nomeradioinput);
						}
				
				);
				
				
			}
			else
			{	
				//ho terminato il ramo
				$("table#"+nomeTabella).append("<tr>"+
				"<input type=\"hidden\" name= \"idLineaProduttiva\" value= \"-1\" />"+
				"</tr>"); // come id della linea scelta -1 (in questo modo il server sa che non va inserita questa linea) poichè viene usato un candidato
				
				
				if(livello > 4) //livello è quello per cui ho fatto la query, quindi se livello è = 4, allora il ramo si esaurisce al terzo ilvelilo quindi non devo farci niente
				{//altrimenti se livello > 4 --aggiorno il valore dell'input del ranked scelto che arriverà (metto id foglia invece del terzo livello)
					
					$("table#"+nomeTabella+" select[name='select_agg']").remove(); //rimuovo la select
					 
					 
					
					//aggiorno l'id che arriverà al server
					$("table#"+nomeTabella+" input[name='"+nomeradioinput+"']:checked").val(idSelezionato); 
					//se sono arrivato alla fine del ramo, ed ero almeno un livello oltre il terzo, nascondo solo la select per la scelta dei livelli aggiuntivi
					//ma non il bottone annulla nè quello che mostra il path accumulato
					
				}
				else
				{
					
					//se sono arrivato alla fine del ramo, senza neanche un livello aggiuntivo
					//metto un messaggio al path
					$("table#"+nomeTabella+" tr[name='tr_path_aggiuntivo']").html("<td colspan=\"3\"><span name=\"span_path_aggiuntivo\"><i>NON SONO RICHIESTI LIVELLI AGGIUNTIVI</i></span></td>");
					//allora nascondo i bottoni annulla e select per il candidato
					$("table#"+nomeTabella+" tr[name='tr_select_aggiuntivi']").html(""); //levo la riga contenente la select e l'annulla 
				}
				
				
			}
		
		}});
}

</script>

	
 	<%-- <input style="width:250px" type = "button" width="120px;" value = "CONVERGENZA" onclick="apriRicercaOperatore(<%=AnagraficaStabilimento.getRiferimentoId()%>,'<%=AnagraficaStabilimento.getRiferimentoIdNomeCol()%>')"/> --%> 
 	<input style="width:250px" type = "button" width="120px;" value = "CONVERGENZA" onclick="apriRicercaOperatore(<%=StabilimentoDettaglio.getIdStabilimento()%>,'id_stabilimento')"/><br> 
 	<b style="color: #006699; font-weight:bold;">Per maggiori dettagli cliccare </b><b style="color: #000000; font-weight:bold;"> <a href="#" onclick="window.open('guida_2016/guida.jsp#convergenza')">QUI</a></b>
 </div>
 
 