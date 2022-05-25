<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.utils.web.LookupElement"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>

<%@page import="org.aspcfs.modules.dpat2019.base.oia.OiaNodo"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatStrumentoCalcoloNominativi"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatStrumentoCalcoloNominativiList"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatStrumentoCalcoloStruttureList"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatStrumentoCalcoloStruttura"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatStruttura"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>

<%@ include file="../../initPage.jsp" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="DpatSDC" class="org.aspcfs.modules.dpat2019.base.DpatStrumentoCalcolo" scope="request"/>
<jsp:useBean id="Qualifica" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookupTipologia" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookupTipologia2" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>


<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/theme.css"></link>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.widgets.js"></script>
<script src="javascript/jquerypluginTableSorter/widget-scroller.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/tableJqueryFilter.js"></script>



<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>

<body onload="resizeGlobalItemsPane('hide')">
<script src="javasript/demo.js"></script>

<script>





RegExp.escape = function(text) {
    return text.replace(/[-[\]{}()*+?.,\\^$|#\s]/g, "\\$&");
}
String.prototype.replaceAll = function(search, replace) {
    return this.replace(new RegExp(RegExp.escape(search),'g'), replace);
};
var  caricoLavoroAnnualeNominativo = "0";
 
function deleteRow(nominativo,anno,idAsl,nomeStruttura)
{
	
	nominativo=nominativo.replaceAll('_','"');
	
	 var json = JSON.parse(nominativo);
	if(confirm('Sei Sicuro di voler eliminare la persona : '+json.anagrafica.nome+' '+json.anagrafica.cognome+' Per la struttura : '+nomeStruttura+"?"))
	 	location.href="DpatSDCConfig.do?command=DeleteNominativo&anno="+anno+"&idAsl="+idAsl+"&id="+json.id;
}


function deleteStruttura(id,anno,idAsl,nomeStruttura)
{
	
	if(confirm('Sei Sicuro di voler eliminare la Struttura :'+nomeStruttura+"?"))
	 	location.href="DpatSDCConfig.do?command=DeleteStruttura&anno="+anno+"&idAsl="+idAsl+"&id="+id;
}

function addStruttura()
{
	document.addstruttura.id.value ="0";
	document.addstruttura.tipologia_struttura.value = "-1"
	document.addstruttura.descrizione.value = "" ;
	window.location.href="#link";
	
		$( "#box2" ).dialog({
		    modal: true,
		    title: "Aggiungi Nuova Struttura ",
		    height: '400',
		    zIndex: 400,
         width: '800',
         show: { effect: 'drop', direction: "down" },
		    buttons: {
		    	
		       
		    }
		});
    
	
} 

function modifyStruttura(id,tipologiaStruttura,descrizione,idPadre,livello,num_figli)
{
	document.addstruttura.id.value=id ;
	document.addstruttura.descrizione.value=descrizione ;
	
	document.addstruttura.livello.value=livello;
	document.addstruttura.n_figli.value=num_figli;
	document.addstruttura.tipologia.value=tipologiaStruttura;

	if (tipologiaStruttura=='15'){ //SOLO PER VIGILI
		document.getElementById('n_livello').value='2';
		/*	var select = document.getElementById('tipologia_struttura');
		for (var k = select.length - 1; k>=0; k--) {
		      select.remove(k);
		      
		 } */
		 var op = document.getElementById("tipologia_struttura").getElementsByTagName("option");
		 for (var i = 0; i < op.length; i++) {
		     op[i].disabled = true;
		     op[i].style.display="none";
		 }
		 var select = document.getElementById('tipologia_struttura');
		 var opt = document.createElement('option');
		 opt.value=15;
		 opt.innerHTML = "AREA VIGILI";
		 select.appendChild(opt);
	}
	else {
		var op = document.getElementById("tipologia_struttura").getElementsByTagName("option");
		 for (var i = 0; i < op.length; i++) {
		     op[i].disabled = false;
		     op[i].style.display="block";
		 }
		 
		 var flag=0;
		 for (var i = 0; i < op.length; i++) {
			 if(op[i].value=='15'){
				 flag=1;
			 }
		 }
		 if (flag==1){
			 var select = document.getElementById('tipologia_struttura');
			 $("#tipologia_struttura option[value='15']").remove();
		 }
	}

	
	document.getElementById("tipologia_struttura").value=tipologiaStruttura;
//	document.addstruttura.tipologia_struttura.value=""+tipologiaStruttura;
	getStruttureComplesse(document.getElementById('idAsl').value ,document.getElementById('anno').value,document.getElementById('id').value,tipologiaStruttura);	
		
	window.location.href="#link";
	document.getElementById('id_padre').value = idPadre;
	
	$( "#box2" ).dialog({
	    modal: true,
	    title: "Modifica Struttura ",
	  
	    height: '400',
	    zIndex: 400,
     width: '800',
     show: { effect: 'drop', direction: "down" },
	    buttons: {
	        
	       
	    }
	});

	
}


function addRow(idStruttua,descrizioneStruttura){
	

	var elementi = document.getElementsByName("utente");
	
	for (i=0;i<elementi.length ; i++)
		{
		elementi[i].checked=false;
		}
	 window.location.href="#link";
	 document.getElementById("struttura").value =idStruttua;
	 $( "#box" ).dialog({
		    modal: true,
		    title: "Aggiungi Utenti Alla Struttura "+descrizioneStruttura,
		    height: '500',
		    autoOpen: true,
		   
		    zIndex: 400,
		    width :'750px',
		    show: { effect: 'drop', direction: "down" },
		    buttons: {
		        
		        "Salva": function() {
		        	var submit = false ; 
		        	var elementi = document.getElementsByName("utente");
		        	
		        		for (i=0;i<elementi.length ; i++)
		        			{
		        			
		        			if (elementi[i].checked==true)
		        				{
		        				
		        				submit = true ;
		        				break ;
		        				}
		        			}
		        	if (submit == true)
		        		document.addnominativo.submit();
		        	else
		        		alert("Attenzione!Per Salvare occorre selezionare almeno un utente da associare alla struttura");
		           // $( this ).dialog( "close" );
		        },
		        "Esci": function() {
		        	
			           $( this ).dialog( "close" );
			        }
		    }
		});
		
}

function checkNumber(numero)
{

	if (isNaN(numero))
	{
	alert("Errore! Campo non Numerico");
	return false ;
	}
	else if (String(numero).indexOf(".") != (-1))
	{
	alert("Errore! Campo non Intero");
	return false ;
	}
	else
	{
		return true ;
	}
}


</script>

<script>
    var msg ="";
	function controllaEliminaStruttura(livello,tipologia,n_figli,idStruttura){
		msg="";
		if (tipologia==15){ //VIGILI
			msg=msg+"Eliminazione non consentita per questo tipo di stuttura";
		}
		else if (livello==2){
			PopolaCombo.getNumeroFigliTemp(idStruttura,{callback:controllaEliminaStrutturaCallback,async:false});
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
		var tipologia_from_jsp = e.options[e.selectedIndex].value;
		
		if (livello==2 && tipologia_from_jsp!=tipologia){  //STRUTTURE COMPLESSE CON FIGLI
			PopolaCombo.getNumeroFigliTemp(idStruttura,{callback:controllaModificaStrutturaCallback,async:false});
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

<%
String edit = DpatSDC.isCompleto() ? "view" : "edit" ;

%>
<table class="trails" cellspacing="0" >
		<tr>	
			<td width="100%"><div align="left"><a href="DpatSDCConfig.do">2015</a> Configurazione Semplificata Strumento di Calcolo <%=DpatSDC.getAnno() %></div></td>
		</tr>
	</table>



<br><br>
<a name= "link" ></a>
<%if (DpatSDC.isCompleto()==false){%>
	<dhv:permission name="dpatSDCSalvaDefinitivo-view">
		<input type="button" 
		value="Salva Definitivo" 
		style="background-color:#FF4D00; font-weight: bold;"
		onclick="javascript : if (confirm('ATTENZIONE!!!Chiudere definitivamente lo Strumento di Calcolo? se clicchi OK non sarà MAI PIU\' possibile modificarlo.Se invece prevedi di apportare ancora modifiche clicca su ANNULLA')){window.location='DpatSDCConfig.do?command=SalvaDefinitivo&id=<%=DpatSDC.getId()%>&idAsl=<%=DpatSDC.getIdAsl()%>&anno=<%=DpatSDC.getAnno()%>'}"/>
	</dhv:permission>
<%} %>

<table width="100%" border="1" id = "myTable05">
<thead>
<tr>
	<th colspan="14"><input type = "button" class = "pulsante" value = "Nuova Struttura" onclick="addStruttura();" style="background-color:#FF4D00; font-weight: bold;"/></th>
</tr>
<tr style="background-color: red;height: 40px;" >
	<th colspan="6">ORGANIGRAMMA E STRUMENTI DI CALCOLO</th>
	
</tr>

<tr style="background-color: rgb(204,193,218) ">

<th>&nbsp;</th>
	<th>STRUTTURA DI APPARTENENZA</th>
	<th width="100px;">&nbsp;</th>
	<th width="300px;" >NOMINATIVO</th>
	<th width="200px;">QUALIFICA</th>
</tr>

</thead>

<%
DpatStrumentoCalcoloStruttureList listStrutture =  DpatSDC.getListaStrutture();
int rowid = 0 ;
Qualifica.setSelectStyle("style=\"width: 100%;heigh:100%;\"");
if (listStrutture.size()>0)
{
for (int i = 0 ; i < listStrutture.size(); i++)
{
	
	
	rowid = (rowid != 1 ? 1 : 2);
	DpatStrumentoCalcoloStruttura struttura = (DpatStrumentoCalcoloStruttura)listStrutture.get(i);
	
	
	DpatStrumentoCalcoloNominativiList listaNominativiStruttura = struttura.getListaNominativi();
	String color = "";
	if (struttura.getNodoStruttura().getN_livello()==2){
		if (struttura.getNodoStruttura().getTipologia_struttura()==15){
			color="#A6FBB2";
		} else{
			color="#FFFF00";
		}
	}
	else{
		color="#FFFFFF";
	}
	
	%>
		<tbody id="div_struttura_<%=struttura.getId()%>"  class="row<%= rowid %>">
			<tr id = "uo_<%=struttura.getId()%>">
			<td style="width: 115px;height: 65px;" rowspan="<%=(listaNominativiStruttura.size()>0)?""+listaNominativiStruttura.size()+1 :"2"%>">
		
		
					
			
			<input type = "button" <%=("view".equalsIgnoreCase(edit)) ? "disabled" : "" %> title="Modifica Struttura per <%=struttura.getDescrizioneStruttura().toUpperCase().replaceAll("'", "").replaceAll("\"", "") %>" value ="Modifica Struttura" 
			onclick="modifyStruttura(<%=struttura.getNodoStruttura().getId()%>,<%=struttura.getNodoStruttura().getTipologia_struttura() %>,'<%=struttura.getDescrizioneStruttura().replaceAll("'", "").replaceAll("\"", "").replaceAll("\n", "").trim()%>',<%=struttura.getNodoStruttura().getId_padre()%>,<%=struttura.getNodoStruttura().getN_livello()%>,<%=struttura.getNodoStruttura().getLista_nodi().size()%>)"
			style="background-color:#FF4D00; font-weight: bold;"/>

			<br/>
			<input type = "button" <%=("view".equalsIgnoreCase(edit)) ? "disabled" : "" %> title="Elimina Struttura <%=struttura.getDescrizioneStruttura().toUpperCase().replaceAll("'", "").replaceAll("\"", "") %>" value ="Elimina Struttura" 
				onclick="javascript : var ret =controllaEliminaStruttura(<%=struttura.getNodoStruttura().getN_livello()%>,<%=struttura.getNodoStruttura().getTipologia_struttura()%>,<%=struttura.getNodoStruttura().getLista_nodi().size()%>,<%=struttura.getNodoStruttura().getId()%>);
					if (ret==''){
					deleteStruttura(<%=struttura.getId()%>,<%=DpatSDC.getAnno()%>,<%=DpatSDC.getIdAsl()%>,'<%=struttura.getDescrizioneStruttura().replaceAll("'", "").replaceAll("\"", "").replaceAll("\n", "").trim()%>')
					} else {alert (ret);}"
				style="background-color:#FF4D00; font-weight: bold; width: 120px;"/>
				
		</td>

		<td  bgcolor="<%=color%>" align="center" style="width: 65px;height: 65px;" rowspan="<%=(listaNominativiStruttura != null && listaNominativiStruttura.size()>0)?""+listaNominativiStruttura.size()+1 :"2"%>">
		<%="<b>" + ((struttura.getDescrizioneStruttura()!=null) ? struttura.getDescrizioneStruttura().toUpperCase() : "") +"</b><BR><BR><BR><b>TIPOLOGIA</b> "+lookupTipologia2.getSelectedValue(struttura.getNodoStruttura().getTipologia_struttura())+"<br><br>"+ ((struttura.getNodoStruttura().getTipologia_struttura()==12) ? "<b>STRUTTURA DI APPARTENENZA</b>"+ struttura.getNodoStruttura().getDescrizionePadre() : "" )%>
		
		</td>



		<td rowspan="<%=(listaNominativiStruttura.size()>0)?""+listaNominativiStruttura.size()+1 :"2"%>">
			<input type = "button" <%=("view".equalsIgnoreCase(edit)) ? "disabled" : "" %> title="Aggiungi Nuovo Nominativo per <%=struttura.getDescrizioneStruttura().toUpperCase() %>" value ="+" onclick="addRow(<%=struttura.getId()%>,'<%=struttura.getDescrizioneStruttura().replaceAll("'", "").replaceAll("\"", "").replaceAll("\n", "")%>')"
			style="background-color:#FF4D00; font-weight: bold;"/>
			
		</td>
		<td colspan="2"></td>
		
	</tr>
		
		
		<% 
		if(listaNominativiStruttura.size()>0)
		{
			%>
			
			<%
			for (int j = 0 ; j < listaNominativiStruttura.size(); j++)
			{
				DpatStrumentoCalcoloNominativi nominativo = (DpatStrumentoCalcoloNominativi)listaNominativiStruttura.get(j);
		
				if (nominativo != null)
				{
		%>
				
				 	
					<tr id = "nominativo_<%=(j+1) %>_<%=struttura.getId()%>" style="margin-top: 0px;" >
					
					<td style="font-weight:bold;" align="center">
					<%=( nominativo.getNominativo()!= null ) ? nominativo.getNominativo().getContact().getNameFirst()+" "+nominativo.getNominativo().getContact().getNameLast() : "" %>
					
					</td>
					<td style="font-weight:bold;" align="center"><%=Qualifica.getSelectedValue(nominativo.getIdLookupQualifica()).toUpperCase() %></td>
					<td style="width: 130px;">
					
					<%if (!struttura.getNodoStruttura().isConfermato()){  %>
						
					<%} %>
						<input type ="button" style="width: 130px; background-color:#FF4D00; font-weight: bold;" <%=("view".equalsIgnoreCase(edit)) ? "disabled" : "" %>  width="100%" value = "elimina Nominativo" onclick="deleteRow('<%=nominativo.toString().replaceAll("'", "")%>',<%=DpatSDC.getAnno()%>,<%=DpatSDC.getIdAsl()%>,'<%=struttura.getDescrizioneStruttura().replaceAll("'", "").replaceAll("\"", "")%>')"/>

					</td>
				</tr>
		<%
			}}
		}
		else{
			%>
			<tr style="margin-top: 0px;" >
			
			
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
			
		<%
		}
}
		
		%>
		</tbody>
		

	
	<%
}

%>



</table>

<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<script>

 
	
	
	
	function getCaricoLavoroAnnuale()
	{
		var idQualifica = document.getElementById("qualifica").value;
		 
		if (idQualifica != '-1' )
		{
			PopolaCombo.getCaricoLavoroAnnuale(idQualifica,{callback:getCaricoLavoroAnnualeCallback,async:false});
			
		}
		
		if (document.addnominativo.qualifica.value =='99' || document.addnominativo.qualifica.value =='96')
		 {
		 document.getElementById("carico_annuo").readOnly=false ;
		 document.getElementById("carico_annuo").value = '0';
		 }
	 else
		 {
		 document.getElementById("carico_annuo").readOnly=true ;
		 }
	}
		function getCaricoLavoroAnnualeCallback(val)
		{
			caricoLavoroAnnualeNominativo = val ;
			document.getElementById("carico_annuo").value = val ;
			getCaricoLavoroEffettivo();
		}
		
		function trimStr(stringa){
			if (stringa.length >0)
		    while (stringa.substring(0,1) == ' '){
		        stringa = stringa.substring(1, stringa.length);
		    }
			if (stringa.length >0)	
		    while (stringa.substring(stringa.length-1, stringa.length) == ' '){
		        stringa = stringa.substring(0,stringa.length-1);
		    }
		    return stringa;
		}
		
		
		
	function getCaricoLavoroEffettivo()
	{
		var dx = trimStr(document.getElementById("carico_annuo").value) ;
		var fx = trimStr(document.getElementById("percentuale").value) ;
		if (dx=='')
			dx ='0';
		
		if (fx=='')
			fx='0';
		if(checkNumber(dx) && checkNumber(fx))
		{
		if (parseInt(fx)!=0)
			var gx = parseInt(dx)-((parseInt(dx)/100)*parseInt(fx));
		else
			var gx = parseInt(dx);
		
		
		document.getElementById("carico_effettivo").value = Math.round(gx) ;
		}
		
	}		
	
	var arraynominativi =new Array();
	

	</script>


<div class="overlay" id="overlay" style="display:none;"></div>


<div id="box2" style="display: none">

<form method="post" action = "DpatSDCConfig.do?command=SaveOiaStruttura" name = "addstruttura">

<script>
function getStruttureComplesse(idAsl,anno,id,tipo)
{
	if (tipo=='12'){
		PopolaCombo.getStruttureComplesseTemp(idAsl,anno,{callback: getStruttureComplesseCallBack,async:false});
		document.getElementById('padre').style.display="" ;
		document.getElementById('n_livello').value = '3';
		if(id!=0){
			$("#id_padre option[value='"+id+"']").remove();
		}
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
				opt.value = "<%=request.getAttribute("id_nonno")%>";
			    select.appendChild(opt);
	}
	
}

function getStruttureComplesseCallBack(arrayNodi)
{
	
	var select = document.getElementById('id_padre');
	 
	for (i = select.length - 1; i>=0; i--) {
	    
	      select.remove(i);
	    }
	
	
	 var opt = document.createElement('option');
	    opt.value = '-1';
	  
	    opt.innerHTML = '-SELEZIONA STRUTTURA-';
	    select.appendChild(opt);
	    
	for ( i = 0; i<arrayNodi.length; i++){
		if (arrayNodi[i]!=null)
			{
	    var opt = document.createElement('option');
	    opt.value = arrayNodi[i].id;
	  
	    opt.innerHTML = arrayNodi[i].descrizione_lunga;
	    select.appendChild(opt);
			}
	}
}
function checkFormStruttura(form)
{
	var msg = '' ;
	var test = true ;
	if (form.tipologia_struttura.value == '-1')
		{
			test = false ;
			msg += '-Controllare di aver selezionato la Tipologia Struttura\n' ;
		}
	
	if (form.id_padre.value == '-1' && form.tipologia_struttura.value=='12')
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

</script>

<input type = "hidden" name = "id" id = "id" value = "0" />	<!-- id della struttura se esistente -->
<input type = "hidden" name = "idStrumentoCalcolo" id = "idStrumentoCalcolo" value = "<%=DpatSDC.getId()%>" />
<input type ="hidden" id = "anno" name="anno" value = "<%=DpatSDC.getAnno()%>">
<input type ="hidden" id = "idAsl" name="idAsl" value = "<%=DpatSDC.getIdAsl()%>">
<%-- <input type ="hidden" id = "id_nodo_dipartimento" name="id_nodo_dipartimento" value = "<%=(listaperAsl!= null && listaperAsl.size()>0) ? listaperAsl.get(0).getId()  : "-1"%>">--%> 
<input type ="hidden" id = "n_livello" name="n_livello" value = "2">

<input type ="hidden" id = "livello" name="livello" value = "0"/>
<input type ="hidden" id = "n_figli" name="n_figli" value = "0"/>
<input type ="hidden" id = "tipologia" name="tipologia" value = "0"/>

<h1> <span id = "header"> Dati Struttura</span></h1>


       <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
       
       
         <tr>
					<td nowrap class="formLabel">DENOMINAZIONE STRUTTURA</td>
					<td>
						<textarea style="font-weight:bold"  rows="5" cols="32" name ="descrizione"></textarea>
					</td>
		        </tr>
		        
        <tr>
        
        <%
        lookupTipologia.setJsEvent("onchange=\"getStruttureComplesse(document.getElementById('idAsl').value ,document.getElementById('anno').value,document.getElementById('id').value,this.value);\"");
        %>
					<td nowrap class="formLabel">TIPOLOGIA STRUTTURA</td>
					<td>

						<%=lookupTipologia.getHtmlSelect("tipologia_struttura", -1) %>
					</td>
		        </tr>	
		        
		        
		         <tr style="display: none" id = "padre">
					<td nowrap class="formLabel">STRUTTURA COMPLESSA DI APPARTENENZA</td>
					<td>
					<select name = "id_padre" id = "id_padre">
					
					</select>
					
					</td>
		        </tr>	
		        
     
		         
			        
       </table>
       <input type = "button" id = "bottone2" value = "Inserisci" 
       onclick="javascript : var ret=controllaModificaStruttura(document.addstruttura.livello.value,document.addstruttura.tipologia.value,document.addstruttura.n_figli.value,document.addstruttura.id.value); if(ret==''){checkFormStruttura(document.addstruttura);} else {alert(ret);}" style="background-color:#FF4D00; font-weight: bold;"/>
       <input type = "button" value = "Annulla" onclick="$( '#box2' ).dialog( 'close' );" style="background-color:#FF4D00; font-weight: bold;"/>

       </form>
      
</div> 




<div id="box">
<form method="post" action = "DpatSDCConfig.do?command=SaveNominativo" name = "addnominativo">
<table  class="tablesorter">
	<thead>
		<tr class="tablesorter-headerRow" role="row">
			<!-- you can also add a placeholder using script; $('.tablesorter th:eq(0)').data('placeholder', 'hello') -->
			<th aria-label="Ruolo: No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRO PER ASL" class="first-name filter-select"><div class="tablesorter-header-inner">Asl</div></th>
			<th aria-label="Ruolo: No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRO PER RUOLO" class="first-name filter-select"><div class="tablesorter-header-inner">Ruolo</div></th>
			<th aria-label="Nome ( filter-match ): No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="1" data-placeholder="FILTRO PER NOME" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">Nome</th>
			<th aria-label="Cognome: No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" class="tablesorter-header tablesorter-headerUnSorted" data-column="2" data-placeholder="FILTRO PER COGNOME"><div class="tablesorter-header-inner">Cognome</div></th>
			<th aria-label="Codice Fiscale: No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" class="tablesorter-header" data-column="3" data-placeholder="FILTRO PER CODICE FISCALE"><div class="tablesorter-header-inner">Codice Fiscale</div></th>
			<th  role="columnheader" scope="col" tabindex="0" class="filter-false tablesorter-header" data-column="3" ><div class="tablesorter-header-inner">&nbsp;</div></th>
			
		</tr>
	</thead>
	<tbody aria-relevant="all" aria-live="polite">
	<%
	ArrayList<User> listaUtenti = (ArrayList<User>) request.getAttribute("ListaUtenti");
	for (User utente :listaUtenti)
	{
	%>
		<tr style="display: table-row;" class="odd" role="row">
		<td><%=toHtml2(utente.getSiteIdName())%></td>
			<td><%=toHtml2(utente.getRole())%></td>
			<td><%=toHtml2(utente.getContact().getNameFirst()) %></td>
			<td><%=toHtml2(utente.getContact().getNameLast()) %></td>
			<td><%=toHtml2(utente.getContact().getCodiceFiscale()) %> </td>
			<td><input type="checkbox" name="utente" value="<%=utente.getId()%>"></td>
			</tr>
		
		<%} %>
						
	</tbody>
</table>
<input type = "hidden" name = "struttura" value="" id = "struttura">
<input type ="hidden" id = "anno" name="anno" value = "<%=DpatSDC.getAnno()%>">
<input type = "hidden" name = "id" id = "id" value = "0" />
<input type ="hidden" id = "idAsl" name="idAsl" value = "<%=DpatSDC.getIdAsl()%>">
       </form>
      </div>
      
      

</body>

