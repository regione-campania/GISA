<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatIstanza"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="java.util.Calendar, java.util.Iterator"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@page import="org.aspcfs.utils.web.CustomLookupElement"%>


<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="siteList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="anniList" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="annoCorrente" class="java.lang.String" scope="request" />
<jsp:useBean id="lookupTipologia" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<script type="text/javascript" src="dwr/interface/Dpat.js"> </script>



<script>
function addStruttura()
{
	document.addstruttura.descrizione.disabled=false ;
	document.addstruttura.tipologia_struttura.disabled=false ;
	document.getElementById("scadenza").style.display="none";
	document.getElementById("dataScadenza").value="";
	document.addstruttura.id_padre.disabled=false;
	document.getElementById("trTipoStruttura").style.display="";

	document.addstruttura.id.value ="0";
	document.addstruttura.tipologia_struttura.value = "-1"
	document.addstruttura.descrizione.value = "" ;
	window.location.href="#link";
	
		$( "#box2" ).dialog({
		    modal: true,
		    title: "Aggiungi Nuova Struttura Complessa",
		    height: '400',
		    zIndex: 400,
         width: '800',
         show: { effect: 'drop', direction: "down" },
		    buttons: {
		    	
		       
		    }
		});
    
	
} 

function getAreaStruttureComplesse()
{
	
	
	idAsl = document.getElementById("ASL").value ; 
	anno = document.getElementById("ANNO").value ; 
	
	if (idAsl>0 && anno >0)
		Dpat.getAreeStruttureComplesse(idAsl,anno,-1,{callback: getStruttureComplesseCallBack,async:false});
		

}

function getStruttureComplesseCallBack(arrayNodi)
{
	
	var select = document.getElementById('combo_area');
	select.value="";
	anno = document.getElementById("ANNO").value ; 
	for (i = select.length - 1; i>=0; i--) {
	    
	      select.remove(i);
	    }
	if (anno>=2016)
		document.getElementById("tr_area").style.display="";
	else
		{
		document.getElementById("tr_area").style.display="none";
		var opt = document.createElement('option');
 	    opt.value = '';
 	   opt.selected = true ;
 	    opt.innerHTML = '-SELEZIONA STRUTTURA-';
 	    select.appendChild(opt);
 	   select.value="";
		
		}
	
// 	 	var opt = document.createElement('option');
// 	    opt.value = '';
	  
// 	    opt.innerHTML = '-SELEZIONA STRUTTURA-';
// 	    select.appendChild(opt);
	    
	for ( i = 0; i<arrayNodi.length; i++){
		if (arrayNodi[i]!=null)
			{
	    var opt = document.createElement('option');
	    opt.value = arrayNodi[i].codiceInternoFK;
	  
	    opt.innerHTML = arrayNodi[i].descrizioneAreaStruttureComplesse.toUpperCase();
	    select.appendChild(opt);
			}
	}
}


function aggiornaMatrix()
{
	
	idAsl = document.getElementById("ASL").value ; 
	anno = document.getElementById("ANNO").value ; 
		
	if (idAsl>0 && anno>0){
    //Dpat.aggiornaMatrix(idAsl,anno,-1,{callback: aggiornaMatrixCallBack,async:false});
		loadModalWindow();
		Dpat.aggiornaMatrix(idAsl,aggiornaMatrixCallBack);
	}
		else
			alert('Selezionare ASL e anno');
}

function aggiornaMatrixCallBack(valRet)
{
	if(valRet==false)
	{
		alert("Aggiornamento in MATRIX non effettuato");
	}
	else {
		alert("Aggiornamento in MATRIX effettuato con successo.");
	}
	loadModalWindowUnlock();
}



$(function () {
    
	
	
	
	 $( "#congelaStruttureReportistica" ).dialog({
	    	autoOpen: false,
	        resizable: false,
	        closeOnEscape: false,
	       	title:"CONGELA STRUTTURE PER REPORTISTICA",
	        width:420,
	        height:200,
	        draggable: false,
	        modal: true,
	        buttons:{
	        	 "CONGELA": function() {  $( "#reportistica" ).submit(); $(this).dialog("close"); } ,
	        	 "ESCI" : function() { $(this).dialog("close");}
	        	
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



	
	<script>function inviaFileExcel(form){
		var errorString = '';
		var s = document.getElementById("ASL");
		var asl = s.options[s.selectedIndex].value;
		var t = document.getElementById("ANNO");
		var anno = t.options[t.selectedIndex].value;
		var comboArea =  document.getElementById('combo_area');
		var idArea = comboArea.options[comboArea.selectedIndex].value;
		
		if(asl == -1)
		{
			errorString+='Selezionare ASL di riferimento';
		}
		
		
		
		var areaIdInput = document.createElement('input');
		areaIdInput.type = 'hidden';
		areaIdInput.name = 'combo_area';
		areaIdInput.value = idArea;
		
		
		var aslInput = document.createElement('input');
		aslInput.type = 'hidden';
		aslInput.name = 'asl';
		aslInput.value = asl;
		
		var annoInput = document.createElement('input');
		annoInput.type = "hidden";
		annoInput.name = 'anno';
		annoInput.value = anno;
		
		form.appendChild(aslInput);
		form.appendChild(annoInput);
		form.appendChild(areaIdInput);
		
		//alert(idArea);
		var fileCaricato = form.file1;
		//var oggetto = form.subject.value;
		
		if (fileCaricato.value==''){// || (!fileCaricato.value.endsWith(".pdf") && !fileCaricato.value.endsWith(".csv"))){
			errorString+=' Errore! Selezionare un file!';
			form.file1.value='';
		}
		/*if (oggetto==''){
			errorString+='\nErrore! L\'oggetto è obbligatorio.';
			}
		if (!GetFileSize(form.file1))
			errorString+='\nErrore! Selezionare un file con dimensione inferiore a 3,00 MB'; */
		if (errorString!= '')
			alert(errorString);
		else
		{
		//form.filename.value = fileCaricato.value;	
		
		
		form.asl= asl;
		form.anno = anno;
		
		loadModalWindow();
		form.submit();
		}
	}</script>
	
	
	
	<div align="right">


</div>
<%
String annoCorrenteStatoAvanzamento = annoCorrente;
if (anniList.size()>0)
{
	DpatIstanza ist = ((ArrayList<DpatIstanza>)anniList).get(0);
	annoCorrenteStatoAvanzamento = ist.getAnno()+"";
} 
%>

	<dhv:permission name="dpat-sdc-stato-view">
	<a  href = "#" onclick="javascript:window.open('DpatSDC2019.do?command=GetStatoAvanzamento&annoCorrente=<%=annoCorrenteStatoAvanzamento %>','_blank', 'location=yes,scrollbars=yes,status=yes')">Stato Avanzamento Organigramma</a>
		</dhv:permission>
			 <table class="details" border="0" cellpadding="4" cellspacing="0" width="100%">
			    <tbody>
			    <tr>
			      <th colspan="2">
			        <img src="images/file.gif" align="absmiddle" border="0"><b>Gestione DPAT</b>
			      </th>
			    </tr>
			     
			     <tr class="containerBody">
				      <td id="selAslTd">
				       SELEZIONARE L'ASL
				       <%
				       	siteList.setJsEvent("onchange='getAreaStruttureComplesse()'");
				       	%>
						<%=siteList.getHtmlSelect("ASL", User.getSiteId())%>
				      </td>
			    </tr>
			    <tr class="containerBody">
			    	<td id="selAnnoTd">
			    		SELEZIONARE L'ANNO
						<select id="ANNO" onchange="getAreaStruttureComplesse()">
							<option value = "">--SELEZIONARE--</option>
							<% if (anniList.size()>0){
									for (DpatIstanza ist : (ArrayList<DpatIstanza>)anniList){
										%>
										<option value="<%=ist.getAnno()%>"><%=ist.getAnno()%></option>
								<%  }
							} %>
						</select>
			    	</td>
			    </tr>
			    
			      <tr class="containerBody" id = "tr_area">
			    	<td >
			    		SELEZIONARE L'AREA DI STRUTTURA COMPLESSA
						<select id="combo_area" name = "combo_area" onchange="" required="required">
						<option value = "">SELEZIONARE PRIMA ASL E ANNO</option>
						</select>
			    	</td>
			    </tr>
			    <tr class="containerBody">
			    	
			    	<td >
			    		<input id="btnVai" type="button" onclick="javascript: getOption('tipoHtml');" value="VAI"/>
			    		<dhv:permission name="dpat_export-view">
			    		<!-- input type="button" onclick="javascript:  getOption('tipoExcel');" value="Esporta come foglio Excel"/ -->
			    		 <!-- select id="selectTrasposto" name="trasposto" -->
			    			<!--option value="false">ORIZZONTALE</option -->
			    			<!--option value="true" >VERTICALE</option -->
			    		<!--/select -->
			    		<!--script -->
			    			<!-- $("#selectTrasposto").hide(); -->
			    		<!--/script -->
			    	</dhv:permission >
			    	<!-- </td> -->
			    </tr> 
			  <!-- </tbody>
			  </table> -->
		
	
			<TR>
			<TD>
		<dhv:permission name="dpat_import-view">
			<form id="form2" action="GenerazioneExcel.do?command=PutExcel" method="post" name="form2" enctype="multipart/form-data">
		
			    <!--    <input id="file1" name="file1" size="45" type="file" title="import excel per asl selezionata">  -->
			    <!--  </tr> -->
			     <!-- <tr class ="containerBody"> -->
			       <!--   	<input id="uploadButton" name="uploadButton" value="UPLOAD" onclick="inviaFileExcel(document.forms['form2'])" type="button"> -->
			      		<%if(request.getAttribute("msg_import") != null) {%>
							
							<div style="background-color: lightgreen;" >${msg_import}</div> 
						<%} %>
			      <!-- </tr> -->
			          <img id="image_loading" src="gestione_documenti/images/loading.gif" height="15" hidden="hidden">
			          <input disabled="" id="text_loading" name="text_loading" value="Caricamento in corso..." style="border: none" hidden="hidden" type="text">
			    <!-- </tr> -->
			    <input name="tipo_richiesta" id="tipo_richiesta" type="hidden" value="dpat"/>
			    
			    <dhv:permission name="dpat-initNuovoAnno-view">
			    	<input style="background-color: green;" id="initNuovoAnnoOrganigramma" name="initNuovoAnnoOrganigramma" value="Inizializza nuovo anno" onclick="if(confirm('Sicuro di proseguire?')){location.href='DpatSDC2019.do?command=InitNuovoAnno&annoCorrente=<%=annoCorrente %>';}" type="button">
			    </dhv:permission>
			    <dhv:permission name="dpat-nuovaStrutturaComplessa-view">
   	              <input type="button" class="pulsante" value="Inserisci nuova struttura complessa" onclick="addStruttura();" >
                </dhv:permission>
                <!-- R.m: aggiunta bottone di propagazione in Matrix -->
                 <dhv:permission name="dpat-propagamatrix-view">
   	              <input type="button" class="pulsante" value="Aggiorna Matrix" onclick="aggiornaMatrix();" >
                </dhv:permission>
			 <!--  </tbody></table> -->
			   </form>
			</dhv:permission>
			
			</td>
			</TR>
	</tr>
	 </tbody>
			  </table>
	
	
	
	
<script>

	var ancoraDaScegliereSeTrasposto = true;

	function getOption(tipoRichiesta){
		var s = document.getElementById("ASL");
		var asl = s.options[s.selectedIndex].value;
		
		var t = document.getElementById("ANNO");
		var anno = t.options[t.selectedIndex].value;
		area = document.getElementById("combo_area").value ; 
		
		var flag = -1;
		var idAslUtente = <%=User.getSiteId()%>;
		if (idAslUtente>0){  // UTENTE ASL
			if (idAslUtente!=asl){
				flag=0;
				alert("Impossibile accedere ai dati di un'altra asl");
			}
			if (flag!=0 && anno=='')
			{
				flag=0;	
				alert("Selezionare anno");
			}

		} else { //UTENTE REGIONE
			if (asl==-1){
				flag=0;
				alert("Selezionare un'asl");
			}
		if (flag!=0 && anno=='')
		{
			flag=0;	
			alert("Selezionare anno");
		}
		if (area=='' && anno >=2016)
			{
				alert("Selezionare un'area");
				flag=0;
			}
		}
		
		if (flag==-1 && asl!=-1 ){
			//DAVIDE
			//window.location="dpat2019.do?command=Home&idAsl="+asl+"&anno="+anno;
			
			if(tipoRichiesta == 'tipoExcel')
			{
				
				
			
				if(ancoraDaScegliereSeTrasposto == true)
				{
					$("#selectTrasposto").show(500);
					ancoraDaScegliereSeTrasposto = false;
					//disabilito tutti gli altri campi della ofrm (tranne il bottone esporta)....
					$("#ASL").prop("disabled",true);
					$("#ASL").fadeTo('slow',0.2);
					$("#ANNO").prop("disabled",true);
					$("#ANNO").fadeTo("slow",0.2);
					$("#selAnnoTd").fadeTo('slow',0.2);
					$("#selAslTd").fadeTo("slow",0.2);
					if(document.getElementById("tr_area").style.display!="none") //la select delle sole strutture complesse viene disabilitata solo se era già visibile
					{
						$("#tr_area").fadeTo("slow",0.2);
						$("#combo_area").prop("disabled",true);
						$("#combo_area").fadeTo("slow",0.2);
					}
					$("#btnVai").prop("disabled",true);
					$("#btnVai").fadeTo("slow",0.2);
					$("#uploadButton").prop("disabled",true);
					$("#uploadButton").fadeTo('slow',0.2);
					$("#file1").prop("disabled",true);
					$("#file1").fadeTo('slow',0.2);
					//---------------------------------------------------------------------------
					
				}
				else
				{
					$("#selectTrasposto").hide();
					//riabilito tutti gli altri campi della ofrm (tranne il bottone esporta)....
					$("#ASL").prop("disabled",false);
					$("#ASL").fadeTo('slow',1.0);
					$("#ANNO").prop("disabled",false);
					$("#ANNO").fadeTo("slow",1.0);
					$("#selAnnoTd").fadeTo('slow',1.0);
					$("#selAslTd").fadeTo("slow",1.0);
					if(document.getElementById("tr_area").style.display!="none") //la select delle sole strutture complesse viene riabilitata solo se era già visibile
					{
						$("#tr_area").fadeTo("slow",1.0);
						$("#combo_area").prop("disabled",false);
						$("#combo_area").fadeTo("slow",1.0);
					}
					
					$("#btnVai").prop("disabled",false);
					$("#btnVai").fadeTo("slow",1.0);
					$("#uploadButton").prop("disabled",false);
					$("#uploadButton").fadeTo('slow',1.0);
					$("#file1").prop("disabled",false);
					$("#file1").fadeTo('slow',1.0);
					
					ancoraDaScegliereSeTrasposto = true;
					
					//---------------------------------------------------------------------------
					
					
				alert('ATTENZIONE ! IL FOGLIO DI ATTIVITA CHE STAI PER ESTRARRE RIPORTA IL CARICO DI LAVORO DELLA STRUTTURA COMPLESSA SU CUI è STATO ESEGUITO L OPERAZIONE DI SALVATAGGIO-CHIUSURA DELLO STRUMENTO DI CALCOLO');	
				  var link = "GenerazioneExcel.do?command=GetExcel&tipo_richiesta=dpat&idAsl="+asl+"&anno="+anno+"&combo_area="+area+"&trasposto="+$("#selectTrasposto").val(); 
				  var result = window.open(link,'popupSelect',
					'height=400px,width=400px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
				  var text = document.createTextNode('GENERAZIONE EXCEL IN CORSO.');
					span = document.createElement('span');
					span.style.fontSize = "30px";
					span.style.fontWeight = "bold";
					span.style.color ="#ff0000";
					span.appendChild(text);
					var br = document.createElement("br");
					var text2 = document.createTextNode('Attendere la generazione del documento entro qualche secondo...');
					span2 = document.createElement('span');
					span2.style.fontSize = "20px";
					span2.style.fontStyle = "italic";
					span2.style.color ="#000000";
					span2.appendChild(text2);
					result.document.body.appendChild(span);
					result.document.body.appendChild(br);
					result.document.body.appendChild(span2);
					result.focus();  
					
				}
			}
			else
			{
					window.location="dpat2019.do?command=Home&idAsl="+asl+"&anno="+anno+"&combo_area="+area;
					
			}
			
			
		}
	
}
</script>
<br><br>
<br><br>
<br><br>


<div id="box2" style="display: none">

<form method="post" action = "DpatSDC2019.do?command=SaveOiaStruttura" name = "addstruttura">

<script>
var idArea = -1 ;
function getStruttureComplesse(idAsl,anno,id,tipo,idPadre)
{
	
	
	idArea = document.getElementById("id_area_sel").value ;
	if (tipo=='12'){
		Dpat.getStruttureComplesse(idAsl,anno,{callback: getStruttureComplesseCallBack,async:false});
		document.getElementById('padre').style.display="" ;
		document.getElementById('n_livello').value = '3';
		if(id!=0){
			$("#id_padre option[value='"+id+"']").remove();
		}
		
		document.getElementById("id_padre").value = idPadre;
	}
	else{
			document.getElementById('padre').style.display="none" ;
			var select = document.getElementById('id_padre');
			document.getElementById('n_livello').value = '2';
			for (i = select.length - 1; i>=0; i--) {
			      select.remove(i);
			    }
			 var opt = document.createElement('option');
			//    opt.value = document.getElementById('id_nodo_dipartimento').value;
				opt.value =<%=request.getAttribute("id_nonno")%>;
				
			    select.appendChild(opt);
	}
	
}

var test = true ;
var msg = '' ;
function verificaControlliSuStrutturaCallBack(valore)
{

	if (valore==true)	
	{
	
	 msg += 'Attenzione! Esistono controlli inseriti in data antecedente o uguale alla data di inizio validità :'+document.addstruttura.dataScadenza.value+' sulla struttura da modificare. Selezionare una data successiva' ;
	 test = false ;
	}
}

function checkFormStruttura(form)
{
	 msg = '' ;
	 test = true ;
	 if (form.idAsl.value == '-1')
		{
			test = false ;
			msg += "-Controllare di aver selezionato l'asl\n" ;
		}
	 if (form.anno.value == '')
		{
			test = false ;
			msg += "-Controllare di aver selezionato l'anno\n" ;
		}
	if (form.tipologia_struttura.value == '-1')
		{
			test = false ;
			msg += '-Controllare di aver selezionato la Tipologia Struttura\n' ;
		}
	
	if (form.id.value != '-1' && form.id.value != '' && form.id.value != '0' && form.dataScadenza.value=="")
	{
		
		test = false ;
		msg += '-Controllare di aver selezionato La data di Inizio Validita Della Struttura \n' ;
	}
	
	
	if (form.id.value != '-1' && form.id.value != '' && form.id.value != '0' && form.dataScadenza.value!="")
	{
		
		PopolaCombo.verificaControlliSuStruttura(form.idStruttura.value,form.dataScadenza.value,{callback: verificaControlliSuStrutturaCallBack,async:false});
		
	}
	
	
	
	
	
	
	if ((form.id_padre.value == '-1' || form.id_padre.value=='') && form.tipologia_struttura.value=='12')
	{
		test = false ;
		msg += '-Controllare di aver selezionato la struttura complessa di appartenenza\n' ;
	}
	if (form.descrizione.value == '')
	{
		test = false ;
		msg += '-Controllare di aver selezionato denominazione struttura\n' ;
	}
	
	
	
	if (test == true)
		form.submit();
	else
		alert(msg);

}

var msg ="";
function controllaEliminaStruttura(livello,tipologia,n_figli,idStruttura){
	msg="";
	if (tipologia==15){ //VIGILI
		msg=msg+"Eliminazione non consentita per questo tipo di stuttura";
	}
	else if (livello==2){
		PopolaCombo.getNumeroFigli(idStruttura,{callback:controllaEliminaStrutturaCallback,async:false});
	}
	return msg;
}
function controllaEliminaStrutturaCallback(val){
	msg="";
	if (val>0){
		msg=msg+"Eliminazione Struttura Complessa non consentita - Ci sono Strutture Semplici collegate.";
	}
}


function controllaModificaStruttura(livello,tipologia,num_figli,idStruttura){
	msg ="";
	var e = document.getElementById("tipologia_struttura");
	
	
	if(tipologia!="39")
		{
	if(e.options != null)
		tipologia_from_jsp = e.options[e.selectedIndex].value;
	else
		tipologia_from_jsp = e.value;
		}
	else
		{
		tipologia_from_jsp="";
		}
	if (livello==2 && tipologia_from_jsp!=tipologia){  //STRUTTURE COMPLESSE CON FIGLI
		PopolaCombo.getNumeroFigli(idStruttura,{callback:controllaModificaStrutturaCallback,async:false});
	}
	return msg;
}
function controllaModificaStrutturaCallback(val){
	msg="";
	if (val>0){
		msg=msg+"Modifica Struttura Complessa non consentita - Ci sono Strutture Semplici collegate.";
	}
}
</script>

<input type = "hidden" name = "tipoOperazione" id = "tipoOperazione" value = "0" />
<input type = "hidden" name = "id" id = "id" value = "0" />	<!-- id della struttura se esistente -->
<%-- <input type ="hidden" id = "id_nodo_dipartimento" name="id_nodo_dipartimento" value = "<%=(listaperAsl!= null && listaperAsl.size()>0) ? listaperAsl.get(0).getId()  : "-1"%>">--%> 
<input type ="hidden" id = "n_livello" name="n_livello" value = "2">
<input type = "hidden" name = "idStruttura" id="idStruttura">
<input type ="hidden" id = "livello" name="livello" value = "0"/>
<input type ="hidden" id = "n_figli" name="n_figli" value = "0"/>
<input type ="hidden" id = "tipologia" name="tipologia" value = "0"/>
<input type ="hidden" id = "descrizione_area_struttura_complessa" name="descrizione_area_struttura_complessa" value = "0"/>


<h1> <span id = "header"> Dati Struttura</span></h1>


       <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
       
       
         <tr>
					<td nowrap class="formLabel">DENOMINAZIONE STRUTTURA</td>
					<td>
						<!-- textarea style="font-weight:bold"  rows="5" cols="32" name ="descrizione"></textarea -->
						<input type="text" name="descrizione" />
					</td>
		        </tr>
		        
		<tr>
				      <td nowrap class="formLabel">
				       SELEZIONARE L'ASL
				       </td>
				       <td>
				       <%
				       	siteList.setJsEvent("onchange='getAreaStruttureComplesse()'");
				       	%>
						<%=siteList.getHtmlSelect("idAsl", User.getSiteId())%>
				      </td>
	    </tr>
	    <tr>
	    	<td nowrap class="formLabel">
	    		SELEZIONARE L'ANNO
	    		</td>
				<td>
				<select id="anno" name="anno" onchange="getAreaStruttureComplesse()">
					<option value = "">--SELEZIONARE--</option>
					<% if (anniList.size()>0){
							for (DpatIstanza ist : (ArrayList<DpatIstanza>)anniList){
								%>
								<option value="<%=ist.getAnno()%>"><%=ist.getAnno()%></option>
						<%  }
					} %>
				</select>
	    	</td>
	    </tr>
		        
        <tr id = "trTipoStruttura">
					<td nowrap class="formLabel">TIPOLOGIA STRUTTURA</td>
					<td>
						
						<%=lookupTipologia.getHtmlSelect("tipologia_struttura", -1) %>
					</td>
		        </tr>	
		        
		        
		         <tr style="display: none" id = "padre">
					<td nowrap class="formLabel">STRUTTURA COMPLESSA DI APPARTENENZA</td>
					<td>
					<select name = "id_padre" id = "id_padre" required="required">
					
					</select>
					
					</td>
		        </tr>	
		        
		         <tr id = "scadenza">
					<td nowrap class="formLabel">DATA INIZIO VALIDITA OPERAZIONE</td>
					<td>
					
						<input readonly type="text"  id="dataScadenza" name="dataScadenza" size="10" />
			<a href="#" onClick="cal19.select(document.forms['addstruttura'].dataScadenza,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
			<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
					</td>
		        </tr>	
		        
     
		         
			        
       </table>
       <input type = "button" id = "bottone2" value = "SALVA" 
       onclick="javascript:var ret=controllaModificaStruttura(document.addstruttura.livello.value,document.addstruttura.tipologia.value,document.addstruttura.n_figli.value,document.addstruttura.id.value); if(ret==''){checkFormStruttura(document.addstruttura);} else {alert(ret);}" style="background-color:#FF4D00; font-weight: bold;"/>
       <input type = "button" value = "Annulla" onclick="$( '#box2' ).dialog( 'close' );" style="background-color:#FF4D00; font-weight: bold;"/>

       </form>
      
</div> 



<div id ="congelaStruttureReportistica">
<form method="post" id = "reportistica" action="DpatSDC2019.do?command=CongelaStruttureReportistica">
<table>

<tr class="containerBody">
			    	<td  class="formLabel">
			    		SELEZIONARE L'ANNO
						<select id="ANNO" onchange="getAreaStruttureComplesse()">
							<% if (anniList.size()>0){
									for (DpatIstanza ist : (ArrayList<DpatIstanza>)anniList){
										%>
										<option value="<%=ist.getAnno()%>"><%=ist.getAnno()%></option>
								<%  }
							} %>
						</select>
						
				
		
			    	</td>
			    </tr>
</table>
	</form>
	
	
		</div>
		
		
				<!-- input type="button" value="Esporta Modello 5 - Attribuzione competenze PER TUTTE LE STRUTTURE"
		onClick="scaricaExcelMod5Totale()" style="background-color:#FF4D00; font-weight: bold;"/ -->
		
		<script>
		function scaricaExcelMod5Totale(){
				var idAsl = document.getElementById("ASL").value;
				var anno = document.getElementById("ANNO").value;
				
				if (idAsl=="-1" || anno == ""){
					alert('Selezionare ASL ed ANNO');
					return false;
					}
				alert('Il report impiegherà qualche minuto ad essere generato.');
		
				var result=
				window.open('dpat2019.do?command=DpatGeneraXlsModifyGeneraleCompetenze&combo_area=-999&idAsl='+idAsl+'&anno='+anno);
				var text = document.createTextNode('GENERAZIONE EXCEL IN CORSO.');
				span = document.createElement('span');
				span.style.fontSize = "30px";
				span.style.fontWeight = "bold";
				span.style.color ="#ff0000";
				span.appendChild(text);
				var br = document.createElement("br");
				var text2 = document.createTextNode('Attendere la generazione del documento entro qualche minuto...');
				span2 = document.createElement('span');
				span2.style.fontSize = "20px";
				span2.style.fontStyle = "italic";
				span2.style.color ="#000000";
				span2.appendChild(text2);
				result.document.body.appendChild(span);
				result.document.body.appendChild(br);
				result.document.body.appendChild(span2);
				result.focus();
		}
		</script>
