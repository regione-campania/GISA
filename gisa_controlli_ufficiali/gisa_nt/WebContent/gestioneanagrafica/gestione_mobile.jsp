<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.distributori.base.Distrubutore"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants" %>
<%@page import="java.sql.Timestamp"%>
<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="Operatore" class="org.aspcfs.modules.opu.base.Operatore" scope="request"/>
<jsp:useBean id="op" class="java.lang.String" scope="request"/>
<jsp:useBean id="InvioMassivo" class="org.aspcfs.utils.InvioMassivoDistributori" scope="request"/>

<jsp:useBean id="messaggioOk" class="java.lang.String" scope="request"/>

<% 
if (request.getAttribute("Error") != null) 
{ 
%>
  <%= showError(request, "Error") %>
<%
}
%>

<%@ include file="../initPage.jsp"%>

<jsp:useBean id="OperazioniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<!--  jsp:useBean id="listaLineeProduttiveAutomezzi" class="org.aspcfs.modules.opu.base.LineaProduttivaList" scope="request"/-->
<!--  jsp:useBean id="listaLineeProduttiveDistributori" class="org.aspcfs.modules.opu.base.LineaProduttivaList" scope="request"/-->
<!--  jsp:useBean id="listaLineeProduttiveProdotti" class="org.aspcfs.modules.opu.base.LineaProduttivaList" scope="request"/-->
<jsp:useBean id="listaLineeProduttive" class="org.aspcfs.modules.opu.base.LineaProduttivaList" scope="request"/>


<jsp:useBean id="listaStorico" class="java.util.Vector" scope="request"/>
<%@page import="org.aspcfs.modules.opu.base.Storico"%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>


<script src='javascript/suapUtil.js'></script>
<script src='javascript/modalWindow.js'></script>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>



<script type="text/javascript" src="dwr/interface/SuapDwr.js"> </script>
<script language="JavaScript" TYPE="text/javascript" SRC="dwr/interface/PopolaCombo.js"> </script>



<!-- RELATIVO AL NUOVO CALENDARIO CON MESE E ANNO FACILMENTE MODIFICABILI -->
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>


<script>
$(function() {
	  
    $( "#comune" ).combobox();
  
 
});

function onChangeComuneDistributore(value)
{
	PopolaCombo.getProvinciaeAslDaComune(value,onChangeComuneDistributoreCallback);
}

function onChangeComuneDistributoreCallback(ret)
{
	if(ret[0].length==0)
		{
		alert("Comune Non Valido");
		}
	else
		{
	document.getElementById("provincia").value = ret[0];
	document.getElementById("asl_distributore").value = ret[1];
		}

}

</script>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
	
	function checkFormPopUp(form){
		//loadModalWindow();
		var ok=true;
		var messaggio="Campi obbligatori mancanti:";
		if (document.getElementById('targa')!=null){
			if(document.getElementById('targa').value==''){
				ok=false;
				messaggio+="\n- Targa";
			}
			if(document.getElementById('data_acquisto').value==''){
				ok=false;
				messaggio+="\n- Data acquisto";
			}
			//if(document.getElementById('carta_circolazione').value==''){
			//	ok=false;
			//	messaggio+="\n- Carta di circolazione";
			//}
			if (ok){
				if(document.getElementById('carta_circolazione') != null &&  document.getElementById('carta_circolazione').value==''){
						if(confirm('ATTENZIONE!!! Si sta inserendo un automezzo SENZA carta di circolazione. Continuare?'))
							return true;
						else 
							return false;				
			}else{
							return true;
		}
		}else{
			alert(messaggio);
			return false;
		}
			
			
			
			
		}else{
			if(document.getElementById('matricola') != null && document.getElementById('matricola').value==''){
				ok=false;
				messaggio+="\n- Matricola";
			}
			if(document.getElementById('data_installazione') != null &&  document.getElementById('data_installazione').value==''){
				ok=false;
				messaggio+="\n- Data installazione";
			}
			if(document.getElementById('ubicazione') != null &&  document.getElementById('ubicazione').value==''){
				ok=false;
				messaggio+="\n- Ubicazione";
			}
			if(document.getElementById('indirizzo') != null &&  document.getElementById('indirizzo').value==''){
				ok=false;
				messaggio+="\n- Indirizzo";
			}
			if(document.getElementById('comune') != null &&  document.getElementById('comune').value==''){
				ok=false;
				messaggio+="\n- comune";
			}
			
		if (ok)
			return true;
		else{
			alert(messaggio);
			return false;
		}	
		
		}
		
		
		
		}
	
	
	function inizializzaForm () {
		var inputs, index;
		inputs = document.getElementById("modificaModulo").elements;
		for (index = 0; index < inputs.length; ++index) {
			var id = inputs[index].id;
			
			if (inputs[index].onchange!=null)
				document.getElementById(id).onchange();
			
		}
		}

		function setDataScadenza (name)
		{
			
			//Vuoto (inutile in modifica)

		}
	
</SCRIPT>




 <script>$(document).ready(function() {
      // Initialize Smart Wizard
      loadModalWindowUnlock();
      
  }); 
</script>

<style>
.row3 {
	background-color : #b2d8b2 !important;
}
.row4 {
	background-color : #ffb2b2 !important;
}

</style>

<%@page import="java.util.ArrayList"%>


<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>

	<%if (messaggioOk!=null && !messaggioOk.equals("")){ %>
	<script>alert('<%=messaggioOk%>');</script>
	<% } %>

<%
	boolean flagLineeScia = false;
	for(int i = 0; i < StabilimentoDettaglio.getListaLineeProduttive().size(); i++ ){
		LineaProduttiva l = (LineaProduttiva) StabilimentoDettaglio.getListaLineeProduttive().get(i);
		if(!l.getFlags().isNoScia())
		{
			flagLineeScia = true;
			break;
		}
	}
%>

<%
String param = "stabId="+StabilimentoDettaglio.getIdStabilimento();
String container = "gestioneanagrafica";

if(!flagLineeScia) { 
	container = "gestioneanagraficanoscia";
}

%>
<dhv:container name="<%=container %>"  selected="Gestione mobile" object="Operatore" param="<%=param%>"  hideContainer="false">

<input type='hidden' id='stabId' value="<%=StabilimentoDettaglio.getIdStabilimento() %>"/>

<%
//if (listaLineeProduttiveAutomezzi.size()==0 && listaLineeProduttiveDistributori.size()==0  && listaLineeProduttiveProdotti.size()==0){
	%>
	<!--  script>
	var url="OpuStab.do?command=Details&stabId="+document.getElementById('stabId').value;
	alert("ATTENZIONE! Menu non disponibile per l'impresa selezionata.");
	location.href=url;
	</script-->
	<% 
//}else{
  if(listaLineeProduttive.size()>0){
%>
	<br><br><table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	<col width="5%"><col width="60%"><col width="35%">
	<tr>
		<th>#</th>
		<th>Linee di attivita'</th>
		<th>Azione</th>
	</tr>

<%
	Iterator it = listaLineeProduttive.iterator();
	int i=0;
	while (it.hasNext()){
		i++;
		LineaProduttiva lp =(LineaProduttiva) it.next();
%>
	<tr class="row<%=i%>">
	<td> <%=i %> </td>
	<td> <%=lp.getAttivita()%> </td>
	<td>
<%
	if(lp.getIdAttivita()>0 && lp.existCampiEstesi())
	{
%>	
	<input type="button" value="Gestisci dati aggiuntivi" 
 		onClick="clickAggiunta('<%=StabilimentoDettaglio.getIdStabilimento() %>','<%=lp.getId_rel_stab_lp() %>','<%=lp.getId() %>')"
 		/>
<%
	}
	else if(!lp.existCampiEstesi())
	{
		out.println("Non esistono campi estesi per questa linea");
	}
	else
	{
		out.println("Linea di attività non mappata completamente");
	}
%> 		
 		</td>
	</tr>

<%
	}
%>
	</table><br><br>
<%
  }
  
  if(false && listaLineeProduttive.size()>0 && StabilimentoDettaglio.getTipoAttivita()==Stabilimento.ATTIVITA_MOBILE){
%>
	<br><br><table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	<col width="1%"><col width="30%"><col width="20%"><col width="8%">
	<tr>
		<th>#</th>
		<th>Linee di attivita' con presenza di distributori</th>
		<th>Azione</th>
	</tr>

<%
	Iterator it = listaLineeProduttive.iterator();
	int i=0;
	while (it.hasNext()){
		i++;
		LineaProduttiva lp =(LineaProduttiva) it.next();
%>
	<tr class="row<%=i%>">
	<td> <%=i %> </td>
	<td> <%=lp.getAttivita()%> </td>
	<td><input type="button" value="Gestisci distributori" 
 		onClick="clickAggiunta('<%=StabilimentoDettaglio.getIdStabilimento() %>','<%=lp.getId_rel_stab_lp() %>','<%=lp.getId() %>')"
 		/>
 		
 		<input type="button" value="Lista File Caricati" 
 		onClick="clickListaImportDistributori('<%=lp.getId_rel_stab_lp() %>')"
 		/>
 		
 			
 		
 		</td>
	</tr>


	</table>
	
	
<%
  }
	String color = "red";
if(InvioMassivo.getId()>0)
{
	
	if(InvioMassivo.getEsito()!=null && InvioMassivo.getEsito().equalsIgnoreCase("ok"))
	{
		color="green";
	}
	%>
	<br>
	<font color="<%=color%>">
	<b>Esito  Import File Ditributori : <%=InvioMassivo.getEsito() %></b><br>
	</font>
	<%
}
else
{
	if(InvioMassivo.getEsito()!=null)
	{
	%>
	<br>
	<font color="<%=color%>">
	<b>Esito  Import File Ditributori : <%=InvioMassivo.getEsito() %></b><br>
	</font>
	<%
	}
}

  }
  //}
%>



<div id="dialogImport">


</div>
<div id="dialogMOBILE"></div>

</dhv:container>

<script>
function openPopupLarge(url){
	
	  var res;
    var result;
    	  window.open(url,'popupSelect',
          'height=600px,width=1000px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
	}





$( "#dialogMOBILE" ).dialog({
	autoOpen: false,
    resizable: false,
    closeOnEscape: false,
   	title:"DETTAGLIO LINEA D'ATTIVITA'",
    width:850,
    height:500,
    draggable: false,
    modal: true
   
}).prev(".ui-dialog-titlebar");


$( "#dialogImport" ).dialog({
	autoOpen: false,
    resizable: false,
    closeOnEscape: false,
   	title:"LISTA IMPORT ESEGUITI'",
    width:850,
    height:500,
    draggable: false,
    modal: true
   
}).prev(".ui-dialog-titlebar");




function clickListaImportDistributori(ldaStabId){
	
	
	loadModalWindow();
	
	$.ajax({
    	type: 'POST',
   		dataType: "html",
   		cache: false,
  		url: 'GestioneAnagraficaAction.do?command=ListaImportDistributoriPerLinea',
        data: { "ldaStabId" : ldaStabId} , 
    	success: function(msg) {
    		loadModalWindowUnlock();
       		document.getElementById('dialogImport').innerHTML=msg ; 
       		caricaCampiDistributore();
       		$('#dialogImport').dialog('open');
   		},
   		error: function (err, errore) {
   			alert('ko '+errore);
        }
		});
	
}

function clickAggiunta(stabId, ldaStabId, ldaMacroId){
	
	
	loadModalWindow();
	
	$.ajax({
    	type: 'POST',
   		dataType: "html",
   		cache: false,
  		url: 'GestioneAnagraficaAction.do?command=PreparazioneLineaMobile',
        data: { "stabId": stabId, "ldaStabId" : ldaStabId, "ldaMacroId" : ldaMacroId} , 
    	success: function(msg) {
    		loadModalWindowUnlock();
       		document.getElementById('dialogMOBILE').innerHTML=msg ; 
       		caricaCampiDistributore();
       		$('#dialogMOBILE').dialog('open');
   		},
   		error: function (err, errore) {
   			alert('ko '+errore);
        }
		});
	
}

function caricaCampiDistributore(){

if (document.getElementById("cap")!=null){
	//document.getElementById("cap").value=document.getElementById("cap_hid").value;
	//$('#cap').prop('readonly',true);
}

if (document.getElementById("provincia")!=null){
	//document.getElementById("provincia").value=document.getElementById("provincia_hid").value;
	//$('#provincia').prop('readonly',true);
}
			
if (document.getElementById("comune")!=null){
	//document.getElementById("comune").value=document.getElementById("comune_hid").value;
	//$('#comune').prop('readonly',true);
	}
					
if (document.getElementById("indirizzo")!=null){
	//document.getElementById("indirizzo").value=document.getElementById("indirizzo_hid").value;
	//$('#indirizzo').prop('readonly',true);
	}
							
if (document.getElementById("ubicazione")!=null){
	//document.getElementById("ubicazione").value=document.getElementById("impresa_hid").value;
	//$('#ubicazione').prop('readonly',true);	
	}
	
if (document.getElementById("asl_distributore")!=null){
	//document.getElementById("asl_distributore").value=document.getElementById("asl_hid").value;
	//$('#asl_distributore').prop('readonly',true);	
	}
	
if ( document.getElementById('targa')!=null){
	$('#targa').change(function() {
		checkEsistenzaKey(document.getElementById('targa').value,'targa', '<%=StabilimentoDettaglio.getIdStabilimento() %>', '<%=StabilimentoDettaglio.getIdOperatore() %>');
	});
}
if ( document.getElementById('matricola')!=null){
	$('#matricola').change(function() {
		checkEsistenzaKey(document.getElementById('matricola').value,'matricola', '<%=StabilimentoDettaglio.getIdStabilimento() %>', '<%=StabilimentoDettaglio.getIdOperatore() %>');
	});
}
}


function pulisciCampi(){

	if (document.getElementById("cap")!=null){
		document.getElementById("cap").value='';
	}

	if (document.getElementById("provincia")!=null){
		document.getElementById("provincia").value='';
	}
				
	if (document.getElementById("comune")!=null){
		document.getElementById("comune").value='';
		}
						
	if (document.getElementById("indirizzo")!=null){
		document.getElementById("indirizzo").value='';
		}
								
	if (document.getElementById("ubicazione")!=null){
		document.getElementById("ubicazione").value='';
		}
		
	if (document.getElementById("asl_distributore")!=null){
		document.getElementById("asl_distributore").value='';
		}
	if (document.getElementById("superficie")!=null){
		document.getElementById("superficie").value='';
		}
	if (document.getElementById("referente_cognome")!=null){
		document.getElementById("referente_cognome").value='';
		}
	if (document.getElementById("referente_nome")!=null){
		document.getElementById("referente_nome").value='';
		}
	if (document.getElementById("desc_autorizzazione")!=null){
		document.getElementById("desc_autorizzazione").value='';
		}
	if (document.getElementById("referente_cf")!=null){
		document.getElementById("referente_cf").value='';
		}
	
	if (document.getElementById("data_autorizzazione")!=null){
		document.getElementById("data_autorizzazione").value='';
		}
	if (document.getElementById("autorizzazione")!=null){
		document.getElementById("autorizzazione").checked=false;
		}
	
	
	
	
	
	
	
	
		
	}



var campo_field="";

function checkEsistenzaKey(key,campo, idStabilimento, idOperatore){
	campo_field=campo;
	if (key!="")
            SuapDwr.esisteKey(key.trim(),campo,idStabilimento, idOperatore,{callback:checkEsistenzaKeyCallBack,async:false});
}
function checkEsistenzaKeyCallBack(val){
    if (val==true){
           alert("Attenzione! La "+campo_field+" inserita è già presente nel sistema");
           document.getElementById(campo_field).value="";
    }
    return val;
}

</script>


 		
























