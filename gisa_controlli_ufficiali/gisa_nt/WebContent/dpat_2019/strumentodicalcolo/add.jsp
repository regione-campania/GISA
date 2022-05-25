<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.Date"%>
<%@page import="org.aspcfs.utils.web.LookupElement"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>

<%@page import="org.aspcfs.modules.dpat2019.base.oia.OiaNodo"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatStrumentoCalcoloNominativi"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatStrumentoCalcoloNominativiList"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatStrumentoCalcoloStruttureList"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatStruttura"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>

<%@ include file="../../initPage.jsp" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="DpatSDC" class="org.aspcfs.modules.dpat2019.base.DpatStrumentoCalcolo" scope="request"/>
<jsp:useBean id="Qualifica" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookupTipologia" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookupTipologia2" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="dpat" class="org.aspcfs.modules.dpat2019.base.Dpat" scope="request"/>
<jsp:useBean id="ListaAsl" class="org.aspcfs.utils.web.LookupList" scope="request"/>





<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>


<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/theme.css"></link>
<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/jquery.tablesorter.pages.css"></link>

<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.pager.js"></script>

<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.widgets.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/tableJqueryFilter.js"></script>

<link rel="stylesheet" href="css/jquery-ui.css" />
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>

<body onload="resizeGlobalItemsPane('hide')">
<script src="javascript/jquery.fixedheadertable.js"></script>
<script src="javasript/demo.js"></script>

<script>

RegExp.escape = function(text) {
    return text.replace(/[-[\]{}()*+?.,\\^$|#\s]/g, "\\$&");
}
String.prototype.replaceAll = function(search, replace) {
    return this.replace(new RegExp(RegExp.escape(search),'g'), replace);
};
var  caricoLavoroAnnualeNominativo = "0";
function modifyRow(idStruttua,descrizioneStruttura,nominativo,descrQualifica)
{
	
// 	getListaNominativi();
nominativo=nominativo.replaceAll('_','"');	
	 var json = JSON.parse(nominativo);
	 document.getElementById("header").innerHTML="" ;
	 document.getElementById("bottone").value = "Modifica" ;
	 document.getElementById("struttura").value =idStruttua;
	 document.getElementById("descrizioneStruttura").innerHTML=descrizioneStruttura ;
	 document.getElementById("nome").value = json.anagrafica.nome ;
	 document.getElementById("cognome").value = json.anagrafica.cognome ;
	 document.getElementById("qualifica").value = json.idLookupQualifica ;
	 document.getElementById("qualificaDiv").innerHTML =descrQualifica ;
	 caricoLavoroAnnualeNominativo= json.caricoLavoroAnnuale ;
	 document.getElementById("fattori_incidenti").value = json.fattoriIncidentiSuCarico ;
	 document.getElementById("percentuale").value = json.percentualeDaSottrarre ;
	 document.getElementById("carico_effettivo").value = json.caricoEffettivoAnnuale ;
	 
	 document.getElementById("cf").value = json.anagrafica.cf;
	 document.getElementById("id").value = json.id;
	 document.getElementById("utente").value = json.anagrafica.id;

	 var idSpecialistaVeterinario = 222 ;
	 if (json.idLookupQualifica==idSpecialistaVeterinario)
		 {
		 document.getElementById("fattori_incidenti").value="";
		 	document.getElementById("fattori_incidenti").disabled=true;
		 	document.forms['modifynominativo'].percentuale.readOnly = true;
		 	
		 }
	 else
		 {
		 document.getElementById("fattori_incidenti").disabled=false;
		 document.forms['modifynominativo'].percentuale.readOnly =false;
		 }
	 
	 
	
	 
	 window.location.href="#link";
	
	 
	 $( "#box" ).dialog({
		    modal: true,
		    title: "Modifica Carico Lavoro Nominativo",
		    height: '400',
		    zIndex: 400,
	     width: '800',
	     open :getCaricoLavoroAnnuale(),
	     show: { effect: 'drop', direction: "down" },
		    buttons: {
		        
		       
		    }
		});
	 document.getElementById("carico_annuo").value = json.caricoLavoroAnnuale ;

    
}
 
function deleteRow(nominativo,anno,idAsl,nomeStruttura,idAreaSel)
{
	
	nominativo=nominativo.replaceAll('_','"');	
	 var json = JSON.parse(nominativo);
	if(confirm('Sei Sicuro di voler eliminare la persona : '+json.anagrafica.nome+' '+json.anagrafica.cognome+' Per la struttura : '+nomeStruttura+"?"))
	 	location.href="DpatSDC2019.do?command=DeleteNominativo&anno="+anno+"&id_area_sel="+idAreaSel+"&idAsl="+idAsl+"&id="+json.id;
}


function deleteStruttura(id,anno,idAsl,nomeStruttura)
{
	
	if(confirm('Sei Sicuro di voler eliminare la Struttura :'+nomeStruttura+"?"))
	 	location.href="DpatSDC2019.do?command=DeleteStruttura&anno="+anno+"&idAsl="+idAsl+"&id="+id;
}

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
		    title: "Aggiungi Nuova Struttura ",
		    height: '400',
		    zIndex: 400,
         width: '800',
         show: { effect: 'drop', direction: "down" },
		    buttons: {
		    	
		       
		    }
		});
    
	
} 


/**
 * tipoOperazione = 1 -> Modifica
   tipoOperazione = 2 -> Disabilita
 */
function modifyStruttura(idStruttura,id,tipologiaStruttura,descrizione,idPadre,livello,num_figli,tipoOperazione,areaDescrizione,idStrutturaSelezionataSdc)
{
	   document.getElementById("scadenza").style.display="";

	   document.addstruttura.descrizione.disabled=false ;
		document.addstruttura.tipologia_struttura.disabled=false ;
		document.addstruttura.id_padre.disabled=false;
	   document.addstruttura.id.value=id ;
	document.addstruttura.descrizione.value=descrizione ;
	document.addstruttura.tipoOperazione.value =tipoOperazione ;
	document.addstruttura.livello.value=livello;
	document.addstruttura.n_figli.value=num_figli;
	document.addstruttura.tipologia.value=tipologiaStruttura;
	document.addstruttura.idStruttura.value=idStruttura;
	document.addstruttura.descrizione_area_struttura_complessa.value=areaDescrizione;
	
	document.addstruttura.tipologia_struttura.value=tipologiaStruttura;
	


	
	document.getElementById("tipologia_struttura").value=tipologiaStruttura;
//	document.addstruttura.tipologia_struttura.value=""+tipologiaStruttura;
	getStruttureComplesse(document.getElementById('idAsl').value ,document.getElementById('anno').value,document.getElementById('id').value,tipologiaStruttura,idPadre);	
		
	window.location.href="#link";
	document.getElementById('id_padre').value = idPadre;
	
	document.getElementById("trTipoStruttura").style.display="none";
	
	if (tipoOperazione==1){
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
	else
		{
		
		document.getElementById("trTipoStruttura").style.display="";
		document.addstruttura.descrizione.disabled=true ;
		document.addstruttura.tipologia_struttura.disabled=true ;
		document.addstruttura.id_padre.disabled=false;
		
		$( "#box2" ).dialog({
		    modal: true,
		    title: "Disabilita Struttura ",
		  
		    height: '400',
		    zIndex: 400,
	     width: '800',
	     show: { effect: 'drop', direction: "down" },
		    buttons: {
		        
		       
		    }
		});
		}

	
}



function addRow(idStruttua,descrizioneStruttura){
	

	var elementi = document.getElementsByName("utente");
	
	for (i=0;i<elementi.length ; i++)
		{
		elementi[i].checked=false;
		}
	 window.location.href="#link";
	 document.getElementById("struttura_addrow").value =idStruttua;
	 $( "#box3" ).dialog({
		    modal: true,
		    title: "Aggiungi Utenti Alla Struttura "+descrizioneStruttura,
		    height: '500',
		    autoOpen: true,
		   
		    zIndex: 400,
		    width :'1150px',
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


function openMessaggioForm()
{
	$( "#esportazioneSdc").dialog({
	    modal: true,
	    title: "ESTRAZIONE ORGANIGRAMMA",
	    height: '400',
	    autoOpen: true,
	    zIndex: 400,
	    width :'750px',
	    show: { effect: 'drop', direction: "down" },
	    buttons: {
	        
	        "CONTINUA": function() {
	        	$("#esportazioneSdcForm").submit();
	        	 $( this ).dialog( "close" );
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

<%
String edit = "edit" ;

%>

<iframe scrolling="no" src="DpatSDC2019.do?command=MessaggioVisualizza" id="messaggio" style="top:0;left: 0;width:60%;height: 200%; border: none;" ></iframe>



<BR>
<font color="red">
ATTENZIONE! PER AGGIUNGERE PERSONE A UNA STRUTTURA OCCORRE CLICCARE SUL PULSANTE [+]. SELEZIONARE LA PERSONA DALL'ELENCO CHE APPARE QUINDI CLICCARE SUL PULSANTE SALVA.<BR>
SE LE PERSONE DA INSERIRE NON SI TROVANO NELL'ELENCO, CONTATTARE L'HELP-DESK (800 810 137).</font>


<BR>
<table class="trails" cellspacing="0" >
		<tr>	
<td width="100%"><a href="dpat2019.do">DPAT</a> &gt <a href="dpat2019.do?command=Home&combo_area=<%=DpatSDC.getIdStrutturaAreaSelezionata() %>&idAsl=<%=DpatSDC.getIdAsl()%>&anno=<%=DpatSDC.getAnno()%>">Allegati DPAT - ASL : <%=ListaAsl.getSelectedValue(DpatSDC.getIdAsl()) %> > <%=DpatSDC.getStrutturaAmbito() !=null && DpatSDC.getStrutturaAmbito().getId()>0 ?DpatSDC.getStrutturaAmbito().getDescrizione_lunga() :"" %></a> &gt Organigramma <%=DpatSDC.getAnno() %> ASL <%=ListaAsl.getSelectedValue(DpatSDC.getIdAsl()) %></div></td>
		</tr>
	</table>




<br><br>
<a name= "link" ></a>


<p align="left">
	 <script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>
	
	  <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="PDF" style="background-color:#FF4D00; font-weight: bold;" value="PDF Strumento di Calcolo"	
        onClick="openRichiestaPDF_DPAT('<%=DpatSDC.getIdAsl()%>', '<%=DpatSDC.getAnno()%>', 'Organigramma',<%=DpatSDC.getStrutturaAmbito().getId()%>);"/>
      
</p>
<%

if (DpatSDC.getStrutturaAmbito().getStato()==OiaNodo.STATO_DEFINITIVO)
{

%>
<div align="right">
<input type="button" id="CL" value="Esporta in Excel" style="background-color:#FF4D00; font-weight: bold;" onClick="openMessaggioForm()" style="background-color:#FF4D00; font-weight: bold;"  />
</div>

<%
}%>


		<dhv:permission name="dpat-view">
		<script>
// 		if (document.getElementById("CL")!=null)
// 			{
// 			document.getElementById("CL").style.display="block";
<%-- 			document.getElementById("CL").onclick=function(){window.location='DpatSDC2019.do?command=GeneraXls&idAsl=<%=DpatSDC.getIdAsl()%>&anno=<%=DpatSDC.getAnno()%>&combo_area=<%=DpatSDC.getIdStrutturaAreaSelezionata()%>'; } --%>
// 	}
			</script>

		</dhv:permission>
	
	



<br><br>

			 
			
			<%if(DpatSDC.getAnno()>2015){ %>
			<h1>LAVORAZIONE STRUMENTO DI CALCOLO PER LA STRUTTURA : <B><%=DpatSDC.getStrutturaAmbito().getDescrizione_lunga().toUpperCase() %></B></h1>
			<%} %>
			<%
			if (DpatSDC.getStrutturaAmbito().getStato()!=OiaNodo.STATO_DEFINITIVO)
			{
			%>
			<dhv:permission name="dpat-edit">
			<input type="button"
			value="Salva e Chiudi" 
			style="background-color:#FF4D00; font-weight: bold;"
			onclick="javascript : if (confirm('ATTENZIONE!!!le singole informazioni inserite sono gia state salvate. A parte questo si desidera chiudere anche lo strumento di calcolo  ?')){window.location='DpatSDC2019.do?command=SalvaDefinitivo&combo_area=<%=DpatSDC.getIdStrutturaAreaSelezionata() %>&id=<%=DpatSDC.getId()%>&idAsl=<%=DpatSDC.getIdAsl()%>&anno=<%=DpatSDC.getAnno()%>'}">
		</dhv:permission>
		<%} %>


<table width="100%" border="1" id = "myTable05">
<thead>
<tr>
	<th colspan="4">
	<dhv:permission name="dpat-edit">
	<input type="button" class="pulsante" value = "Nuova Struttura" onclick="addStruttura();" style="background-color:#FF4D00; font-weight: bold;"/>
	</dhv:permission>
	</th>
	
</tr>
<tr style="background-color: red;height: 40px;" >
	<th colspan="6">ORGANIGRAMMA E STRUMENTI DI CALCOLO</th>
</tr>

<tr style="background-color: rgb(204,193,218) ">

<th>&nbsp;</th>

	<th>STRUTTURA DI APPARTENENZA</th>
	<th>&nbsp;</th>
	<th>NOMINATIVO</th>
	<th>QUALIFICA</th>
	<th>&nbsp;</th>
	
</tr>

</thead>

<%
DpatStrumentoCalcoloStruttureList listStrutture =  DpatSDC.getListaStrutture();

OiaNodo strutturaAmbito = null ;
int rowspan = listStrutture.size()-1;
for (int k = 0 ; k <listStrutture.size();k++)
{
	OiaNodo strutturaTmp = (OiaNodo)listStrutture.get(k);
	if (strutturaTmp.getTipologia_struttura()!=13 && strutturaTmp.getTipologia_struttura()!=14)
	{
		rowspan+=strutturaTmp.getListaNominativi().size()+1;
	}
	else
	{
		strutturaAmbito = strutturaTmp;
	}
}



int rowid = 0 ;
Qualifica.setSelectStyle("style=\"width: 100%;heigh:100%;\"");

if (listStrutture.size()>0)
{
for (int i = 0 ; i < listStrutture.size(); i++)
{
	
	
	rowid = (rowid != 1 ? 1 : 2);
	OiaNodo struttura = (OiaNodo)listStrutture.get(i);
	
	
	DpatStrumentoCalcoloNominativiList listaNominativiStruttura = struttura.getListaNominativi();
	
	
	
	String color = "";
	if (struttura.getN_livello()==2){
		if (struttura.getTipologia_struttura()==15  ){
			color="#A6FBB2";
		} else{
			color="#FFFF00";
		}
	}
	else{
		color="#FFFFFF";
	}
	
	%>
		
			
		
			<tr class="row<%= rowid %>" id = "uo_<%=struttura.getCodiceInternoFK()%>" <%if((struttura.getTipologia_struttura()==13 || struttura.getTipologia_struttura()==14) && struttura.getAnno()>=2016 ){ %> style="display:none" <%} %>>
			<td style="width: 115px;height: 65px;" rowspan="<%=(listaNominativiStruttura!=null && listaNominativiStruttura.size()>0)?""+((listaNominativiStruttura.size()+1)) :"2"%>">
		
		
					
			<%
			if (struttura.getTipologia_struttura()!=13 && struttura.getTipologia_struttura()!=39)
			{	
			%>
			<dhv:permission name="dpat-edit">
			<input type = "button" <%=("view".equalsIgnoreCase(edit)) ? "disabled" : "" %> title="Modifica Struttura per <%=struttura.getDescrizione_lunga().toUpperCase().replaceAll("'", "").replaceAll("\"", "") %>" value ="Modifica Struttura" 
			onclick="modifyStruttura(<%=struttura.getId()%>,<%=struttura.getCodiceInternoFK()%>,<%=struttura.getTipologia_struttura() %>,'<%=struttura.getDescrizione_lunga().replaceAll("'", "").replaceAll("\"", "").replaceAll("\n", "").trim()%>',<%=struttura.getId_padre() %>,<%=struttura.getN_livello()%>,<%=struttura.getLista_nodi().size()%>,1,'<%= toHtml2(struttura.getDescrizioneAreaStruttureComplesse()) %>')"
			style="background-color:#FF4D00; font-weight: bold;"/>

			<br/>
			<%if (struttura.getTipologia_struttura()!=39) {%>
			<input type = "button" <%=("view".equalsIgnoreCase(edit)) ? "disabled" : "" %> title="Disabilita Struttura per <%=struttura.getDescrizione_lunga().toUpperCase().replaceAll("'", "").replaceAll("\"", "") %>" value ="Disabilita Struttura" 
			onclick="modifyStruttura(<%=struttura.getId()%>,<%=struttura.getCodiceInternoFK()%>,<%=struttura.getTipologia_struttura() %>,'<%=struttura.getDescrizione_lunga().replaceAll("'", "").replaceAll("\"", "").replaceAll("\n", "").trim()%>',<%=struttura.getId_padre()%>,<%=struttura.getN_livello()%>,<%=struttura.getLista_nodi().size()%>,2,'<%= toHtml2(struttura.getDescrizioneAreaStruttureComplesse()) %>')"
			style="background-color:#FF4D00; font-weight: bold;"/>
			<%} %>
			</dhv:permission>
			<%} %>
		</td>
		



		<td    align="center" style="width: 65px;height: 65px;" rowspan="<%=(listaNominativiStruttura != null && listaNominativiStruttura.size()>0)?""+((listaNominativiStruttura.size()+1)) :"2"%>">
		<%="<b>" + ((struttura.getDescrizione_lunga()!=null) ? struttura.getDescrizione_lunga().toUpperCase() : "") + ((struttura.getDataScadenza()!=null) ? "ATTIVA AL "+toDateasString(struttura.getDataScadenza()) : "")  +"</b><BR><BR><BR><b>TIPOLOGIA</b> "+lookupTipologia2.getSelectedValue(struttura.getTipologia_struttura())+"<br><br>"+ ((struttura.getTipologia_struttura()==12) ? "<b>STRUTTURA DI APPARTENENZA</b>"+ struttura.getDescrizionePadre().toUpperCase() : "" )%>
		
		</td>


		<td rowspan="<%=(listaNominativiStruttura!=null && listaNominativiStruttura.size()>0)?""+((listaNominativiStruttura.size()+1)) :"2"%>">
			
			
			<dhv:permission name="dpat-edit">
			<input type = "button" <%=("view".equalsIgnoreCase(edit)) ? "disabled" : "" %> title="Aggiungi Nuovo Nominativo per <%=struttura.getDescrizione_lunga().toUpperCase() %>" value ="+" onclick="addRow(<%=struttura.getCodiceInternoFK()%>,'<%=struttura.getDescrizione_lunga().replaceAll("'", "").replaceAll("\"", "").replaceAll("\n", "")%>')"
			style="background-color:#FF4D00; font-weight: bold;"/>
			</dhv:permission>
		
			
		</td>
		
		<td colspan="6"></td>
	</tr>
		
		
		<% 
		if(listaNominativiStruttura!=null && listaNominativiStruttura.size()>0)
		{
			
			
			%>
			
			<%
			for (int j = 0 ; j < listaNominativiStruttura.size(); j++)
			{
				DpatStrumentoCalcoloNominativi nominativo = (DpatStrumentoCalcoloNominativi)listaNominativiStruttura.get(j);
		
				if (nominativo != null)
				{
		%>
				
				 	
					<tr class="row<%= rowid %>" id = "nominativo_<%=(j+1) %>_<%=struttura.getCodiceInternoFK()%>"  <%if (nominativo.getNominativo().getDataScadenza()!=null && nominativo.getNominativo().getDataScadenza().before(new Timestamp(System.currentTimeMillis()))){ %> style="margin-top: 0px;background-color: red"  <%} %> <%if((struttura.getTipologia_struttura()==13 || struttura.getTipologia_struttura()==14) && struttura.getAnno()>=2016 ){ %> style="display:none" <%} %> >
					
					<td style="font-weight:bold;" align="center">
					<%= toHtml2(nominativo.getNominativo().getContact().getNameLast()) +" " + toHtml2(nominativo.getNominativo().getContact().getNameFirst()) + (nominativo.getNominativo().getDataScadenza()!=null && nominativo.getNominativo().getDataScadenza().before(new Timestamp(System.currentTimeMillis())) ? "Variato in Data "+nominativo.getNominativo().getDataScadenza() :"")%>
					
					</td>
					<td style="font-weight:bold;" align="center"><%=Qualifica.getSelectedValue(nominativo.getIdLookupQualifica()).toUpperCase() %></td>
					<td style="width: 190px;">
					
					<dhv:permission name="dpat-edit">
<%-- 						<input type ="button" style="width: 190px; background-color:#FF4D00; font-weight: bold;" <%=("view".equalsIgnoreCase(edit)) ? "disabled" : "" %>  width="100%" value = "Modifica Carico di Lavoro" onclick="modifyRow(<%=struttura.getCodiceInternoFK()%>,'<%=struttura.getDescrizione_lunga().replaceAll("'", "").replaceAll("\"", "")%>','<%=nominativo.toString().replaceAll("\"", "").replaceAll("'", " ")%>','<%=Qualifica.getSelectedValue(nominativo.getIdLookupQualifica()) %>')" /> --%>
					<input type ="button" style="width: 190px; background-color:#FF4D00; font-weight: bold;" <%=("view".equalsIgnoreCase(edit)) ? "disabled" : "" %>  width="100%" value = "elimina Nominativo" onclick="deleteRow('<%=nominativo.toString().replaceAll("'", "")%>',<%=DpatSDC.getAnno()%>,<%=DpatSDC.getIdAsl()%>,'<%=struttura.getDescrizione_lunga().replaceAll("\"", "").replaceAll("'", "")%>',<%=DpatSDC.getIdStrutturaAreaSelezionata()%>)"/>
</dhv:permission>
					</td>
				</tr>
		<%}
			}}
		
		else{
			%>
			
			<tr  class="row<%= rowid %>" style="margin-top: 0px;" <%if((struttura.getTipologia_struttura()==13 || struttura.getTipologia_struttura()==14) && struttura.getAnno()>=2016 ){ %> style="display:none" <%} %> >
			
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
			
		<%
		}

}
		%>
		
		

	
	<%
}

%>



<!-- <tr> -->
<!-- 	<th colspan="13"> -->
<!-- 		SOMMA ATTUALE SU STRUTTURE -->
<%-- 	</th><th id = "uiAreaCorrente"><%=strutturaAmbito.getSommaUiArea() %></th> --%>
	
<!-- </tr> -->


</table>

<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/interface/Dpat.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<script>

 

	
	function checkForm(form)
	{
		ret = true ;
		var msg ='';
		
		
		if(form.qualifica.value == '-1')
		{
			ret = false ;
			msg += "Il campo Qualifica deve Essere compilato\n";
		}
		if (trimStr(form.carico_annuo.value)!= '' && checkNumber(trimStr(form.carico_annuo.value)) 
				&& form.qualifica.value !='44' && form.qualifica.value !='222' && form.qualifica.value !='221'  && form.qualifica.value !='96')
		{
			var caricoAnnuo = parseInt(trimStr(form.carico_annuo.value));
			if (caricoAnnuo<0 || caricoAnnuo>caricoLavoroAnnualeNominativo)
			{
				ret = false ;
				msg += "Il carico Annuo deve essere compreso tra 0 e "+caricoLavoroAnnualeNominativo+"\n" ;
			}	
		}
		else
		{
			 if (trimStr(form.carico_annuo.value)!= '' && checkNumber(trimStr(form.carico_annuo.value)) && 
					 (form.qualifica.value =='44' || form.qualifica.value =='222'  || form.qualifica.value =='221'  || form.qualifica.value =='96'))
			{
				 var caricoAnnuo = parseInt(trimStr(form.carico_annuo.value));
				 if (caricoAnnuo<0 || caricoAnnuo>368)
					{
						ret = false ;
						msg += "Il carico Annuo deve essere compreso tra 0 e 368 \n" ;
					}	
			}
			 else
			 {
				 	ret = false ;
					msg += "Il valore del carico Annuo non e corretto\n" ;
			 }
		}
		
		if(trimStr(form.percentuale.value)!='' && checkNumber(form.percentuale.value))
		{
			percent = parseInt(trimStr(form.percentuale.value));
			if (percent<0 || percent >100)
			{
				ret = false ;
				msg += "Il valore percentuale deve essere compreso tra 0 e 100 \n" ;
			}
		}
		else
		{
			ret = false ;
			msg += "Il valore percentuale non e corretto \n" ;
		}
		if (ret==false)
		{
			alert(msg);
		}
		else
		{
			form.submit();
		}
		
		
		
	}
	
	
	function getCaricoLavoroAnnuale()
	{
		var idQualifica = document.getElementById("qualifica").value;
		 
		if (idQualifica != '-1' )
		{
			PopolaCombo.getCaricoLavoroAnnuale(idQualifica,{callback:getCaricoLavoroAnnualeCallback,async:false});
			
		}
		
		
		if (document.modifynominativo.qualifica.value =='99' ||document.modifynominativo.qualifica.value =='221' || document.modifynominativo.qualifica.value =='96' || document.modifynominativo.qualifica.value =='222')
		 {
		 document.getElementById("carico_annuo").readOnly=false ;
		 document.getElementById("carico_annuo").value = '';
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
	
	var fieldCheckBoxGlobale;
	function controllaEsistenzaInStruttura(fieldCheckBox,idUtente)
	{
		fieldCheckBoxGlobale = fieldCheckBox;
		if (fieldCheckBox.checked)
			{
		var idStrutturaNominativo = document.getElementById("struttura_addrow").value;
		PopolaCombo.controllaEsistenzaInStruttura(idStrutturaNominativo,idUtente,{callback:controllaEsistenzaInStrutturaCallback,async:false});
			}
		
	}
	
	function controllaEsistenzaInStrutturaCallback(risposta)
	{
		if (risposta==true && fieldCheckBoxGlobale!=null)
			{
			fieldCheckBoxGlobale.checked=false;
			alert("Questo nominativo risulta gia associato alla struttura");
			}
			
	}

	</script>


 





      
<div id="box" style="display: none">

<form method="post" action = "DpatSDC2019.do?command=SaveNominativo" name = "modifynominativo">

<input type = "hidden" name = "id" id = "id" value = "0" />
<input type = "hidden" name = "utente" id = "utente" value = "0" />
<input type = "hidden" name = "id_area_sel"  value = "<%=DpatSDC.getIdStrutturaAreaSelezionata() %>" />


<h1> <span id = "header"> Dati Nominativo</span></h1>
       <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
       
       <tr style="font-weight:bold" >
					<td nowrap class="formLabel">STRUTTURA DI APPARTENENZA</td>
					<td style="font-weight:bold" >
						<input type ="hidden" id = "struttura" name="struttura">
						<input type ="hidden" id = "anno" name="anno" value = "<%=DpatSDC.getAnno()%>">
						<span id = "descrizioneStruttura"></span>
					</td>
		        </tr>
		        
	 <tr style="font-weight:bold" >
					<td nowrap class="formLabel">NOME</td>
					<td style="font-weight:bold" >
						<input type ="hidden" id = "idAsl" name="idAsl" value = "<%=DpatSDC.getIdAsl()%>">
						<input style="font-weight:bold"  readonly="readonly" type ="text" id = "nome" name = "nome" >
					</td>
		        </tr>	
		        
		         <tr style="font-weight:bold" >
					<td nowrap class="formLabel">COGNOME</td>
					<td style="font-weight:bold" >
						<input style="font-weight:bold" readonly="readonly" type ="text" id = "cognome" name = "cognome">
					</td>
		        </tr>
		         <tr style="font-weight:bold" >
					<td nowrap class="formLabel">CODICE FISCALE</td>
					<td style="font-weight:bold" >
						<input style="font-weight:bold" readonly="readonly"  type ="text" id = "cf" name = "cf">
					</td>
		        </tr>
		        
		         <tr style="font-weight:bold" >
					<td nowrap class="formLabel">QUALIFICA</td>
					<td>
					<%
					Qualifica.setJsEvent("onchange=\"getCaricoLavoroAnnuale()\"");
					%>
						<input type="hidden" name="qualifica" id="qualifica">
						<div id="qualificaDiv"></div>
					</td>
		        </tr>	
<!-- 		         <tr style="font-weight:bold" > -->
<!-- 					<td nowrap class="formLabel">CARICO DI LAVORO TEORICO ANNUALE MINIMO AD PERSONAM IN U.I.</td> -->
<!-- 					<td style="font-weight:bold" > -->
					
<!-- 						<input style="font-weight:bold"  type ="text"  id = "carico_annuo" name = "carico_annuo" value="0" onchange="getCaricoLavoroEffettivo()"> -->
<!-- 					</td> -->
<!-- 		        </tr> -->
<!-- 		         <tr style="font-weight:bold" > -->
<!-- 					<td style="font-weight:bold"  nowrap class="formLabel">FATTORI CHE INCIDONO SUL CARICO DI LAVORO MINIMO AD PERSONAM</td> -->
<!-- 					<td> -->
<!-- 						<textarea style="font-weight:bold;"  rows="5" cols="32" id="fattori_incidenti" name="fattori_incidenti" style="text-transform: capitalize;"></textarea> -->
<!-- 					</td> -->
<!-- 		        </tr>	 -->
		        
<!-- 		        <tr style="font-weight:bold" > -->
<!-- 					<td nowrap style="font-weight:bold"  class="formLabel">PERCENTUALE DI U.I. DA SOTTRARRE (%)</td> -->
<!-- 					<td> -->
<!-- 						<input style="font-weight:bold"  type ="text" id = "percentuale" name = "percentuale" value="0"  onchange="getCaricoLavoroEffettivo()"/> -->
<!-- 					</td> -->
<!-- 		        </tr>	 -->
<!-- 		        <tr style="font-weight:bold" > -->
<!-- 					<td nowrap class="formLabel">CARICO DI LAVORO EFFETTIVO ANNUALE MINIMO AD PERSONAM IN U.I.</td> -->
<!-- 					<td> -->
<!-- 						<input type ="text" style="font-weight:bold"  id = "carico_effettivo"  readonly="readonly"  name = "carico_effettivo" value="0"> -->
<!-- 					</td> -->
<!-- 		        </tr>	 -->
			        
       </table>
       <input type = "button" id = "bottone" value = "Inserisci" onclick="checkForm(document.modifynominativo)" style="background-color:#FF4D00; font-weight: bold;"/>
       <input type = "button" value = "Annulla" onclick="$( '#box' ).dialog( 'close' );" style="background-color:#FF4D00; font-weight: bold;"/>
       </form>
      
</div>  <!--fine box-->



<div id="box3" style="display: none">
<form method="post" action = "DpatSDC2019.do?command=SaveNominativo" name = "addnominativo">
<input type = "hidden" name = "percentuale" value = "0"/>
<input type = "hidden" name = "carico_effettivo" value = "0"/>
<input type = "hidden" name = "carico_annuo" value = "0"/>
<input type = "hidden" name = "fattori_incidenti" value = ""/>
<input type = "hidden" name = "id_area_sel" value = "<%=DpatSDC.getIdStrutturaAreaSelezionata()%>"/>

	<div class="pager">
	Page: <select class="gotoPage"></select>		
	<img src="javascript/img/first.png" class="first" alt="First" title="First page" />
	<img src="javascript/img/prev.png" class="prev" alt="Prev" title="Previous page" />
	<img src="javascript/img//next.png" class="next" alt="Next" title="Next page" />
	<img src="javascript/img/last.png" class="last" alt="Last" title= "Last page" />
	<select class="pagesize">
		<option value="10">10</option>
		<option value="20">20</option>
		<option value="30">30</option>
		<option value="40">40</option>
	</select>
</div>

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
		<tr style="display: table-row;" class="odd" role="row" <%if (utente.getDataScadenza()!=null && utente.getDataScadenza().before(new Timestamp(System.currentTimeMillis()))){ %> background="red" <%} %>>
		<td><%=toHtml2(utente.getSiteIdName())%></td>
			<td><%=toHtml2(utente.getRole())%></td>
			<td><%=toHtml2(utente.getContact().getNameFirst()) %></td>
			<td><%=toHtml2(utente.getContact().getNameLast()) %></td>
			<td><%=toHtml2(utente.getContact().getCodiceFiscale()) %> </td>
			<input type="hidden" name="carico_annuo_<%=utente.getId() %>" value="<%=utente.getCaricolo_lavoro_annuale()%>">
			<td><input type="checkbox" name="utente" value="<%=utente.getId()%>" onclick="controllaEsistenzaInStruttura(this,<%=utente.getId()%>)"></td>
		</tr>
		<%} %>
						
	</tbody>
</table>

	<div class="pager">
	Page: <select class="gotoPage"></select>		
	<img src="javascript/img/first.png" class="first" alt="First" title="First page" />
	<img src="javascript/img/prev.png" class="prev" alt="Prev" title="Previous page" />
	<img src="javascript/img//next.png" class="next" alt="Next" title="Next page" />
	<img src="javascript/img/last.png" class="last" alt="Last" title= "Last page" />
	<select class="pagesize">
		<option value="10">10</option>
		<option value="20">20</option>
		<option value="30">30</option>
		<option value="40">40</option>
	</select>
</div>
<input type = "hidden" name = "struttura" value="" id = "struttura_addrow">
<input type ="hidden" id = "anno_addrow" name="anno" value = "<%=DpatSDC.getAnno()%>">
<input type = "hidden" name = "id" id = "id_addrow" value = "0" />
<input type ="hidden" id = "idAsl_addrow" name="idAsl" value = "<%=DpatSDC.getIdAsl()%>">
       </form>
      </div>
      
      
      
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
	    
	    
	    if (idArea>0)
	    	{
	for ( i = 0; i<arrayNodi.length; i++){
		if (arrayNodi[i]!=null)
			{
	    var opt = document.createElement('option');
	    opt.value = arrayNodi[i].codiceInternoFK;
	  
	    opt.innerHTML = arrayNodi[i].descrizione_lunga;
	    if(idArea==arrayNodi[i].codiceInternoFK)
	   		 select.appendChild(opt);
			}
	}
	}
	    else
	    	{
	    	for ( i = 0; i<arrayNodi.length; i++){
	    		if (arrayNodi[i]!=null)
	    			{
	    	    var opt = document.createElement('option');
	    	    opt.value = arrayNodi[i].codiceInternoFK;
	    	  
	    	    opt.innerHTML = arrayNodi[i].descrizione_lunga;
	    	    select.appendChild(opt);
	    			}
	    	}
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

</script>

<input type = "hidden" name = "tipoOperazione" id = "tipoOperazione" value = "0" />
<input type = "hidden" name = "id" id = "id" value = "0" />	<!-- id della struttura se esistente -->
<input type = "hidden" name = "idStrumentoCalcolo" id = "idStrumentoCalcolo" value = "<%=DpatSDC.getId()%>" />
<input type ="hidden" id = "anno" name="anno" value = "<%=DpatSDC.getAnno()%>">
<input type ="hidden" id = "idAsl" name="idAsl" value = "<%=DpatSDC.getIdAsl()%>">
<%-- <input type ="hidden" id = "id_nodo_dipartimento" name="id_nodo_dipartimento" value = "<%=(listaperAsl!= null && listaperAsl.size()>0) ? listaperAsl.get(0).getId()  : "-1"%>">--%> 
<input type ="hidden" id = "n_livello" name="n_livello" value = "2">
<input type = "hidden" name = "idStruttura" id="idStruttura" value="0">
<input type ="hidden" id = "livello" name="livello" value = "0"/>
<input type ="hidden" id = "n_figli" name="n_figli" value = "0"/>
<input type ="hidden" id = "tipologia" name="tipologia" value = "0"/>
<input type ="hidden" id = "descrizione_area_struttura_complessa" name="descrizione_area_struttura_complessa" value = "0"/>
<input type ="hidden" id = "id_area_sel" name="id_area_sel" value = "<%=DpatSDC.getIdStrutturaAreaSelezionata()%>"/>


<h1> <span id = "header"> Dati Struttura</span></h1>


       <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
       
       
         <tr>
					<td nowrap class="formLabel">DENOMINAZIONE STRUTTURA</td>
					<td>
						<!-- textarea style="font-weight:bold"  rows="5" cols="32" name ="descrizione"></textarea -->
						<input type="text" name ="descrizione" />
					</td>
		        </tr>
		        
        <tr id = "trTipoStruttura">
        
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

   

</body>
<div id="esportazioneSdc" style="display: none">
<form method="post" id="esportazioneSdcForm" action="DpatSDC2019.do?command=GeneraXls&idAsl=<%=DpatSDC.getIdAsl()%>&anno=<%=DpatSDC.getAnno()%>&combo_area=<%=DpatSDC.getIdStrutturaAreaSelezionata()%>">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
       
<tr><th colspan="2"><strong>Questa Estrazione sarà storicizzata all'interno del sistema con le seguenti informazioni</strong></th></tr>       
         <tr>
					<td nowrap class="formLabel">Tipo Estrazione</td>
					
					<td >ORGANIGRAMMA</td>
			</tr>
			<tr>
					<td nowrap class="formLabel">Data Estrazione</td>
					
					<td ><%
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					%>
					<%=sdf.format(new Date(System.currentTimeMillis())) %>
					</td>
			</tr>
			<tr>
					<td nowrap class="formLabel">Estratto da</td>
					
					<td ><%=User.getContact().getNameFirst()+" "+User.getContact().getNameLast() %></td>
			</tr>
			<tr>
					<td nowrap class="formLabel">Estrazione Per Tutte le Strutture Complesse</td>
					
					<td ><input  type = "checkbox" name="checkStrutture" value = "1"></td>
			</tr>
			<tr>
					<td nowrap class="formLabel">Struttura Complessa</td>
					
					<td ><%=DpatSDC.getStrutturaAmbito().getDescrizione_lunga() %></td>
			</tr>
			<tr>
					<td nowrap class="formLabel">Asl</td>
					
					<td ><%= DpatSDC.getStrutturaAmbito().getIdAsl() %></td>
			</tr>
			<tr>
					<td nowrap class="formLabel">Note</td>
					
					<td ><textarea rows="6" cols="30" name="note"></textarea> </td>
			</tr>
</table>
</form>
</div>
