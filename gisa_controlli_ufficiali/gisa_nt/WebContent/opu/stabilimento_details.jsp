<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.taglib.PermissionHandler"%>
<%@page import="org.aspcfs.modules.stabilimenti.base.OperatoriAssociatiMercatoIttico"%>
<%@page import="org.aspcfs.modules.opu.actions.StabilimentoAction"%>
<%@page import="org.aspcfs.modules.lineeattivita.base.LineeCampiEstesi"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants" %>
<%@page import="java.util.Map.Entry"%>
<jsp:useBean id="AnagraficaStabilimento" class="org.aspcfs.modules.ricercaunica.base.RicercaOpu" scope = "request"/>
<jsp:useBean id="stabilimentiAssociateMercatoIttico" class="java.util.ArrayList" scope="request" />


<script language="JavaScript" TYPE="text/javascript"
	SRC="dwr/interface/PopolaCombo.js"> </script>
	
<%@page import="java.sql.Timestamp"%>

<link rel="stylesheet" type="text/css" href="css/jquery.calendars.picker.css">
<link href="javascript/datepicker/jquery.datepick.css" rel="stylesheet">
<script type="text/javascript" src="javascript/jquery.calendars.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.plus.js"></script>
<script type="text/javascript" src="javascript/jquery.plugin.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.picker.js"></script>
<script src="javascript/parsedate.js"></script>

<script src="javascript/datepicker/jquery.plugin.js"></script>
<script src="javascript/datepicker/jquery.datepick.js"></script>

<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>
<style>

.ovale { border-style:solid; border-color:#405c81; border-width:1px; }



</style>

<style>
	
	.dropbtn {
	}

	.dropdown {
	  position: relative;
	  display: inline-block;
	}
	
	.dropdown-content {
	  display: none;
	  position: absolute;
	  border-style: solid;
  	  border-width: 1px;
	  background-color: #E8E8E8;
	  overflow: auto;
	  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
	  z-index: 1;
	}
	
	.dropdown-content a {
	  display: block;
	  padding: 6px 16px;
	  
	}
	
	
	.show {display: block;}
	
	/* Change color of dropdown links on hover */
	.dropdown-content a:hover {background-color: #D0D0D0}

</style>

<script>


function mostraListaOperazioni(listadamostrare){
	
	var dropdowns = document.getElementsByClassName("dropdown-content");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
	
	document.getElementById(listadamostrare).classList.toggle("show");
}

//Close the dropdown if the user clicks outside of it
window.onclick = function(event) {
  if (!event.target.matches('.dropbtn')) {
    var dropdowns = document.getElementsByClassName("dropdown-content");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
  }
}

</script>


<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ListaStati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="LineeCampiEstesi" class="java.util.LinkedHashMap" scope="request"/>
<jsp:useBean id="Audit" class="org.aspcfs.modules.audit.base.Audit" scope="request"/>


<jsp:useBean id="Operatore" class="org.aspcfs.modules.opu.base.Operatore" scope="request"/>

<%@ include file="../initPage.jsp"%>


<% if (StabilimentoDettaglio.getCodiceErroreSuap()!=null && !StabilimentoDettaglio.getCodiceErroreSuap().equals("")){%>
<script>
alert('<%=StabilimentoDettaglio.getCodiceErroreSuap()%>');
</script>	
<% } %> 




<script>

var toUrl = "" ;
function verificaPropagazionBdu(url)
{
	toUrl = url;
	PopolaCombo.propagazioneInBdu(<%=StabilimentoDettaglio.getIdStabilimento()%>,verificaPropagazionBduCallback);
	}
	
	function verificaPropagazionBduCallback(val)
	{	
		if(val==false)
			{
			location.href=toUrl+"&stabId=<%=StabilimentoDettaglio.getIdStabilimento()%>";
			}
		else
			{
			alert("Attenzione ! per gli stabilimenti con linee soggette a propagazione in altri sistemi non è possibile eseguire una richiesta di errata corrige.")
			}
		
	}
function openPopup(url){
	
		  var res;
	        var result;
	        	  window.open(url,'popupSelect',
	              'height=300px,width=600px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
		}
function openPopupLarge(url){
	
	  var res;
      var result;
      	  window.open(url,'popupSelect',
            'height=600px,width=1000px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
	}		
	


function checkFieldRiattivazione()
{
	var msg ="Attenzione controllare che i seguenti campi siano valorizzati : \n" ;
	formTest= true ;
	if($("#dataRiattivazione2").val()=="")
		{
		formTest = false;
		msg+="- Controllare il Campo Data Riattivazione\n";
		}
	
	if($("#decretoRiattivazione").val()=="")
	{
	formTest = false;
	msg+="- Controllare il Campo Decreto Riattivazione\n";
	}
	
	
	
	var checkedVal = $('input[name=idLineaProduttivaRiattivazione]:checked');
	
	if(checkedVal.length==0)
		{
		formTest = false;
		msg+="- Controllare di aver Spuntato almeno una linea \n";
		}
	
	
	if(formTest==false)
		alert(msg);
	
	return formTest;
		
	
	
	
	
}

$(function () {
	    
	


	    
		
		
		// NUOVA GESTIONE BOTTONI
		$( "#dialogModifica" ).dialog({
	    	autoOpen: false,
	        resizable: false,
	        closeOnEscape: false,
	       	title:"Operazioni di modifica",
	        width:850,
	        height:250,
	        draggable: false,
	        modal: true,
	        buttons:{
	        	 
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
	 
  
$( "#dialogSincronizzazioneAltriSistemi" ).dialog({
	autoOpen: false,
    resizable: false,
    closeOnEscape: false,
   	title:"Operazioni di sincronizzazione",
    width:850,
    height:250,
    draggable: false,
    modal: true,
    buttons:{
    	 
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

$( "#dialogPreaccettazione" ).dialog({
	autoOpen: false,
    resizable: false,
    closeOnEscape: false,
   	title:"Operazioni su preaccettazione",
    width:850,
    height:250,
    draggable: false,
    modal: true,
    buttons:{
    	 
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

// FINE NUOVA GESTIONE BOTTONI -->
	
		$( "#dialogGestioneScia" ).dialog({
		    	autoOpen: false,
		        resizable: false,
		        closeOnEscape: false,
		       	title:"Operazioni di modifica stabilimento",
		        width:850,
		        height:500,
		        draggable: false,
		        modal: true,
		        buttons:{
		        	 
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
		 
	  
		
		
		$( "#dialogStatoBdu" ).dialog({
	    	autoOpen: false,
	        resizable: false,
	        closeOnEscape: false,
	       	title:"Stato Canile in Bdu",
	        width:850,
	        height:500,
	        draggable: false,
	        modal: true,
	        buttons:{
	        	 
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
	
	
	 $( "#dialogRiattivazione" ).dialog({
	    	autoOpen: false,
	        resizable: false,
	        closeOnEscape: false,
	       	title:"RIATTIVAZIONE PER LINEE DI ATTIVITA",
	        width:850,
	        height:500,
	        draggable: false,
	        modal: true,
	        buttons:{
	        	
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

function mostraGestioneScia(idStabilimento,tipoInserimentoScia)
{

	
	loadModalWindow();
	$.ajax({
    	type: 'POST',
    	async: false,
   		dataType: "html",
   		cache: false,
  		url: 'SuapStab.do?command=GestioneScia',
        data: { "idStabilimento": idStabilimento,"idTipoInserimentoScia":tipoInserimentoScia} , 
    	success: function(msg) {
    		loadModalWindowUnlock();
       		document.getElementById('dialogGestioneScia').innerHTML=msg ; 
       		
       		
   		},
   		error: function (err, errore) {
   			alert('ko '+errore);
        }
		});
	$('#dialogGestioneScia').dialog('open');
	
	
}

function mostraStatoBdu(idStabilimento)
{

	
	loadModalWindow();
	$.ajax({
    	type: 'POST',
    	async: false,
   		dataType: "html",
   		cache: false,
  		url: 'SuapStab.do?command=StatoBdu',
        data: { "idStabilimento": idStabilimento} , 
    	success: function(msg) {
    		loadModalWindowUnlock();
       		document.getElementById('dialogStatoBdu').innerHTML=msg ; 
       		
       		
   		},
   		error: function (err, errore) {
   			alert('ko '+errore);
        }
		});
	$('#dialogStatoBdu').dialog('open');
	
	
}

</script>


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
	
<dhv:evaluate if="<%=!isPopup(request)&& User.getRoleId()!=Role.RUOLO_TRASPORTATORI_DISTRIBUTORI && User.getRoleId()!=Role.RUOLO_GESTORE_PRODOTTI_SOA%>">

<table class="trails" cellspacing="0">
<tr>
<td width="100%">

<%if (flagLineeScia) { %>
<a href=""><dhv:label name="">Anagrafica stabilimenti</dhv:label></a> >
<a href="OpuStab.do?command=SearchForm"><dhv:label name="">Ricerca</dhv:label></a> >
<a href="RicercaUnica.do?command=Search"><dhv:label name="">Risultato ricerca</dhv:label></a> >
Scheda Anagrafica Impresa
<% } else {  %>
<a href=""><dhv:label name="">Anagrafica stabilimenti</dhv:label></a> >
<a href="GisaNoScia.do?command=SearchForm"><dhv:label name="">Ricerca</dhv:label></a> >
<a href="RicercaUnica.do?command=Search"><dhv:label name="">Risultato ricerca</dhv:label></a> >
Scheda Anagrafica Impresa
<% } %>
</td>
</tr>
</table>

<%@ include file="../../controlliufficiali/diffida_list.jsp" %>

</dhv:evaluate>
<dhv:permission name="opu-storico-view">
<a href="OperatoreAction.do?command=ViewStoricoSoggettoFisicoImpresa&idOperatore=<%=StabilimentoDettaglio.getIdOperatore()%>&idStabilimento=<%=StabilimentoDettaglio.getIdStabilimento()%>">Storico Impresa</a>
</dhv:permission>

<%
String param = "stabId="+StabilimentoDettaglio.getIdStabilimento()+"&opId=" + StabilimentoDettaglio.getIdOperatore()+"&altId="+StabilimentoDettaglio.getAltId();
%>

<%
// boolean hasPrimaria = false;
// boolean primariaValorizzata = false;
boolean lineePregresse = false;
lineePregresse = StabilimentoDettaglio.isLineePregresse();
String messagePrimaria = "<ul>";
// 
// 		Iterator<LineaProduttiva> itLplist2 = StabilimentoDettaglio.getListaLineeProduttive().iterator();
// 				int indice2 = 1;
// 				while (itLplist2.hasNext()) {
// 					LineaProduttiva lp2 = itLplist2.next();
// 						if (lp2.isPrincipale()){
// 							hasPrimaria = true;
// 							if (lp2.getIdAttivita()>0)
// 								primariaValorizzata = true;
// 							break;
// 							}
// 						}
	%>
	

	
<%
String nomeContainer = StabilimentoDettaglio.getContainer();
request.setAttribute("Operatore",StabilimentoDettaglio.getOperatore());
if("aziendeagricoleopu".equals(nomeContainer) )
{
	if(User.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA)
	{
	nomeContainer="aziendeagricoleopu";
	}
}
else
{	
 nomeContainer = "suap";
request.setAttribute("Operatore",StabilimentoDettaglio.getOperatore());


if (StabilimentoDettaglio.getStato()==Stabilimento.STATO_IN_DOMANDA){
	nomeContainer = "suapminimale";
	messagePrimaria+= "<li>Lo stato dello stabilimento risulta in domanda.</li>";
}
//if (!hasPrimaria || !primariaValorizzata){
	if (lineePregresse){
	nomeContainer = "suapminimale";
	messagePrimaria+= "<li>Lo stabilimento ha linee non aggiornate. </li>";
// 	if (!hasPrimaria)
// 		messagePrimaria+= "Lo stabilimento manca di linea produttiva primaria.<br/>";
// 	else if (!primariaValorizzata)
// 		messagePrimaria+= "Lo stabilimento ha linee produttive non aggiornate.<br/>";
}
	messagePrimaria += "</ul>";

}


%>

<% 
int tipoDettaglioScheda = 15;
int tipoDettaglioAttestato = 23;

if (StabilimentoDettaglio.getTipoAttivita()==Stabilimento.ATTIVITA_FISSA){
	tipoDettaglioScheda = 24;
	tipoDettaglioAttestato = 26;
}
else if (StabilimentoDettaglio.getTipoAttivita()==Stabilimento.ATTIVITA_MOBILE){
	tipoDettaglioScheda = 25;
	tipoDettaglioAttestato = 27;
}

%>

<% System.out.println("tipo attività:"+StabilimentoDettaglio.getTipoAttivita()); 
System.out.println("nomeContainer:"+nomeContainer); 

%>


 <script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>
	
<!-- 	CONTROLLO SCIA NO SCIA -->
<%-- <%if (!flagLineeScia){ %> --%>
<!-- <script> -->
<%-- window.location.href="GestioneAnagraficaAction.do?command=Details&altId=<%=StabilimentoDettaglio.getAltId()%>"; --%>
<!-- </script> -->
<%-- <% } %> --%>
	
	<!-- NUOVA GESTIONE BOTTONI -->
<%
int numeroTdTableBottoni = 0;
boolean viewSincronizzazione = false ;
boolean viewOperatoriMercatoIttico= false ;
boolean isCanile = false;
boolean isOsm = false;
int idRelCanile = -1;

StabilimentoDettaglio.getListaLineeProduttive();
for (LineaProduttiva lp : (Vector<LineaProduttiva>)StabilimentoDettaglio.getListaLineeProduttive())
{
	if(lp.getDescrizione_linea_attivita().toLowerCase().contains("canile") || lp.getDescrizione_linea_attivita().toLowerCase().contains("animali da compagnia")  || lp.getCodice().equalsIgnoreCase("VET-AMBV-PU") || lp.getCodice().equalsIgnoreCase("349-93-ALPET-PRIV") || lp.getCodice().equalsIgnoreCase("349-93-ALCAT-PRIV") || lp.getCodice().equalsIgnoreCase("349-93-ALPET-ALTR") || lp.getCodice().equalsIgnoreCase("349-93-ALCAT-ALTR")  )
	{
		viewSincronizzazione=true ;
	}
	if(lp.getDescrizione_linea_attivita().toUpperCase().contains("MERCATO ITTICO - WM") )
	{
		viewOperatoriMercatoIttico=true ;
	}

	//if(lp.getCodice().equals("IUV-CAN-CAN") ) //gestione valida solo per ML8
	if(lp.getCodice().contains("IUV-CAN-") ) //gestione valida per ML8 e ML10
	{
		isCanile=true ;
		idRelCanile = lp.getId_rel_stab_lp();
	}
	if(lp.getCodice().startsWith("MG") || lp.getCodice().startsWith("MR") )
	{
		isOsm=true ;
	}
}

int idLineaCanile = -1;
int idLineaOperatoreComm = -1;
int idLineaClinica = -1;
int idLineaCommercio = -1;
int idLineaDistributori = -1;

Iterator<LineaProduttiva> itLinee= StabilimentoDettaglio.getListaLineeProduttive().iterator();
String codAzienda = "" ;
String codSpecie = "" ;
String macroarea = "";
PermissionHandler p1 = new PermissionHandler();
PermissionHandler p2 = new PermissionHandler();
while(itLinee.hasNext())
{
	LineaProduttiva lp= itLinee.next();
	macroarea=lp.getMacrocategoria();
	System.out.println("attivita: "+lp.getMacrocategoria());

	if(lp.getCodice().equalsIgnoreCase("MS.060-MS.060.200-852IT7A101"))
		idLineaCommercio=lp.getId_rel_stab_lp();

	if(lp.getDescrizione_linea_attivita().toLowerCase().contains("canile"))
		idLineaCanile=lp.getId_rel_stab_lp();

	if(lp.getMacrocategoria().toLowerCase().contains("strutture veterinarie"))
		idLineaClinica=lp.getId_rel_stab_lp();

	if(lp.getDescrizione_linea_attivita().toLowerCase().contains("vendita al dettaglio animali da compagnia") || lp.getDescrizione_linea_attivita().toLowerCase().contains("ingrosso animali da compagnia"))
		idLineaOperatoreComm=lp.getId_rel_stab_lp();

	if(lp.getCodice().equalsIgnoreCase("MS.060-MS.060.400-852IT7A301"))
		idLineaDistributori=lp.getId_rel_stab_lp();
}
%>

	<div style="float:left;">
	<table>
		<tbody>
			<tr>
			<%
	
	if (!lineePregresse && flagLineeScia)
	 { 
	if (!lineePregresse && StabilimentoDettaglio.getStato()== Stabilimento.STATO_AUTORIZZATO && StabilimentoDettaglio.getTipoAttivita()== Stabilimento.ATTIVITA_FISSA) 
	{
%>  
		<dhv:permission name="aggiungi_linea_pregressa-view">  
		<%numeroTdTableBottoni++;%>
			<td valign="top" > 
				<center>	
					<input style="width:250px" type="button" onClick="loadModalWindow(); location.href='AggiungiLineaPregressa.do?command=Add&id=<%=StabilimentoDettaglio.getIdStabilimento() %>'; return false;" value="Aggiungi linea pregressa"/>
				</center>   
			</td>
		</dhv:permission>      	
<%
	} 
	 if (!lineePregresse && StabilimentoDettaglio.getOperatore().getTipo_impresa()!= 1 && StabilimentoDettaglio.getStato()== Stabilimento.STATO_AUTORIZZATO && StabilimentoDettaglio.getTipoAttivita()== Stabilimento.ATTIVITA_MOBILE && StabilimentoDettaglio.getIdAsl()>200 && (User.getSiteId()==-1 || User.getSiteId() == StabilimentoDettaglio.getIdAsl())) 
	 {
%>  
		<dhv:permission name="cambio_sede_operatore-view">
		<%numeroTdTableBottoni++;%> 
			<td valign="top" > 
				<center>     	
					<input style="width:250px" type="button" onClick="loadModalWindow();window.location.href='CambioSedeOperatore.do?command=Add&id=<%=StabilimentoDettaglio.getIdStabilimento() %>'; return false;" value="Cambio sede legale"/>
				</center>
			</td> 
		</dhv:permission>      	
<%
	 }
	 }
	if (lineePregresse) 
	{
%>		
		<dhv:permission name="opu-edit">
<%		
		numeroTdTableBottoni++;
%>
		<td valign="top" > 
			<center> 
				<input style="width:250px" type="button"  value="AGGIORNA LINEE DI ATTIVITA'" onClick="openPopupLarge('OpuStab.do?command=PrepareUpdateLineePregresse&stabId=<%=StabilimentoDettaglio.getIdStabilimento() %>')"/>
	 		</center>
		</td>  
		</dhv:permission>
<%  
	}
%>
<%
if (!lineePregresse && flagLineeScia)
{
%>
	<dhv:permission name="opu-edit">	
<%
	numeroTdTableBottoni++;
%>
 		<td valign="top" >
			<center>
				<input style="width:250px" type="button" value="Livelli aggiuntivi masterlist" onClick="openPopupLarge('OpuStab.do?command=ListaLivelliAggiuntivi&stabId=<%=StabilimentoDettaglio.getIdStabilimento()%>')"/>
			</center>
		</td>
	
<%
	out.println((numeroTdTableBottoni%4==0)?("</tr><tr>"):(""));
	if (idLineaCommercio>0) 
	{
		numeroTdTableBottoni++;
%>
		<td valign="top" > 
			<center> 
				<input style="width:250px" type="button" value="Gestione prodotti" onClick="openPopupLarge('OpuStab.do?command=ListaProdottiLinea&idRelazione=<%=idLineaCommercio%>')"/>
			</center>
		</td>
<%
		out.println((numeroTdTableBottoni%4==0)?("</tr><tr>"):(""));
	}
%>
	</dhv:permission>


<% 
	if (!lineePregresse && StabilimentoDettaglio.getStato()== Stabilimento.STATO_AUTORIZZATO && StabilimentoDettaglio.getTipoAttivita()== Stabilimento.ATTIVITA_FISSA) 
	{
%>  
		<dhv:permission name="trasferimento_sede_stabilimento-view"> 
			<%numeroTdTableBottoni++;%>
			<td valign="top" > 
			<center>
			<input style="width:250px" type="button" onClick="loadModalWindow();window.location.href='TrasferimentoSedeStabilimento.do?command=Add&id=<%=StabilimentoDettaglio.getIdStabilimento() %>'; return false;" value="Trasferimento sede operativa"/>
			</center>
			</td>
			<%=(numeroTdTableBottoni%4==0)?("</tr><tr>"):("") %>
		</dhv:permission>      	
<%
	} 
	 }
%>  
<dhv:permission name="osm-invio-view"> 
<%
	if(isOsm)
	{
		numeroTdTableBottoni++;	
%>
			<td valign="top" > 
				<center>
			<input style="width:250px" type="button" value="GESTIONE INVIO OSM" onclick="openPopupLarge('GestioneOSM.do?command=Details&riferimentoId=<%=StabilimentoDettaglio.getIdStabilimento()%>&riferimentoIdNomeTab=opu_stabilimento')"/>
 			</center>
 			</td>
<%
		out.println((numeroTdTableBottoni%4==0)?("</tr><tr>"):(""));
	} 
%>	
</dhv:permission>

<dhv:permission name = "opu-sposta-controlli-view">
<%
if(AnagraficaStabilimento.getNumeroControlli ()==0 && AnagraficaStabilimento.getTipologia()==999)
{
	numeroTdTableBottoni++;		
	%>
	<td valign="top" > 
				<center>
	<input style="width:250px" type = "button" width="120px;" value = "ELIMINA" onclick="if(confirm('Sei sicuro di voler eliminare la scheda di anagrafica corrente ?')){location.href='OpuStab.do?command=TrashStabilimento&idAnagrafica=<%=AnagraficaStabilimento.getRiferimentoId()%>';}"/>
	</center>
	</td>
	<%
	out.println((numeroTdTableBottoni%4==0)?("</tr><tr>"):(""));
}
%>
</dhv:permission>



<%
if (flagLineeScia)
{ 
	p1.setName("sincronizza-bdu-view");
	p2.setName("sincronizza-vam-view");
	p1.setPageContext(pageContext);
	p2.setPageContext(pageContext);
	if((viewSincronizzazione==true || isCanile || idLineaClinica>0)&&(p1.doStartTag()==1 || p2.doStartTag()==1))
	{ 
%>
		<dhv:permission name = "sincronizza-bdu-view">
<%
	if(viewSincronizzazione==true)
	{ 
		numeroTdTableBottoni++;		
%>
		<td valign="top" > 
				<center>
			<input style="width:250px" type="button" value="SINCRONIZZA BDU (ANAGRAFICA)" onclick="loadModalWindow(); location.href='OpuStab.do?command=Details&sincronizzaBdu=true&stabId=<%=StabilimentoDettaglio.getIdStabilimento()%>'"/>
 			</center>
 			</td>
<%
		out.println((numeroTdTableBottoni%4==0)?("</tr><tr>"):(""));
	} 

	if(isCanile)
	{
		numeroTdTableBottoni++;	
%>
			<td valign="top" > 
				<center>
			<input style="width:250px" type="button" value="SINCRONIZZA BDU (SUPERFICIE MQ)" onclick="loadModalWindow(); location.href='OpuStab.do?command=Details&sincronizzaBduMq=true&stabId=<%=StabilimentoDettaglio.getIdStabilimento()%>&idRelCanile=<%=idRelCanile%>'"/>
 			</center>
 			</td>
<%
		out.println((numeroTdTableBottoni%4==0)?("</tr><tr>"):(""));

		numeroTdTableBottoni++;	
%>
			<td valign="top"> 
				<center>
			<input style="width:250px" type="button" value="Visualizza stato in Bdu" onclick="mostraStatoBdu(<%=StabilimentoDettaglio.getIdStabilimento()%>)"/>
 			</center>
 			</td>
<%
		out.println((numeroTdTableBottoni%4==0)?("</tr><tr>"):(""));
	} 
%>
</dhv:permission> 
<%	
	if (idLineaClinica>0) 
	{
%>
		<dhv:permission name = "sincronizza-vam-view">
			<%numeroTdTableBottoni++;%>
			<td valign="top" > 
				<center>
			<input style="width:250px" type="button" value="SINCRONIZZA IN VAM" onClick="openPopup('OpuStab.do?command=PrepareSincronizzaVam&id=<%=StabilimentoDettaglio.getIdStabilimento()%>')"/>
			</center>
			</td>
			<%=(numeroTdTableBottoni%4==0)?("</tr><tr>"):("")%>
		</dhv:permission>
<%
	}
%>
<%
	}
%>
  
<dhv:permission name="note_hd-view">
<%numeroTdTableBottoni++;%>
<td valign="top" > 
				<center>
<jsp:include page="../note_hd/link_note_hd.jsp">
<jsp:param name="riferimentoId" value="<%=StabilimentoDettaglio.getIdStabilimento() %>" />
<jsp:param name="riferimentoIdNomeTab" value="opu_stabilimento" />
<jsp:param name="typeView" value="button" />
</jsp:include>
</center>
</td>
<%=(numeroTdTableBottoni%4==0)?("</tr><tr>"):("") %>
</dhv:permission>

<%
}
	p1.setName("campioni-campioni-preaccettazionesenzacampione-view");
	p2.setName("campioni-campioni-listacodicipreaccettazione-view");
	p1.setPageContext(pageContext);
	p2.setPageContext(pageContext);
	if(p1.doStartTag()==1 || false )
	{
		if(p1.doStartTag()==1  )
			numeroTdTableBottoni++;
%>		
		<jsp:include page="../preaccettazionesigla/button_preaccettazione.jsp">
			<jsp:param name="numeroTdTableBottoni" value="<%=numeroTdTableBottoni%>" />
    		<jsp:param name="riferimentoIdPreaccettazione" value="<%=StabilimentoDettaglio.getIdStabilimento() %>" />
    		<jsp:param name="riferimentoIdNomePreaccettazione" value="stabId" />
    		<jsp:param name="riferimentoIdNomeTabPreaccettazione" value="opu_stabilimento" />
   	 		<jsp:param name="userIdPreaccettazione" value="<%=User.getUserId() %>" />
		</jsp:include>
		
		<% if(!flagLineeScia){ %>
		
				<dhv:permission name="note_hd-view">
				<%numeroTdTableBottoni++;%>
				<td valign="top" > 
								<center>
				<jsp:include page="../note_hd/link_note_hd.jsp">
				<jsp:param name="riferimentoId" value="<%=StabilimentoDettaglio.getIdStabilimento() %>" />
				<jsp:param name="riferimentoIdNomeTab" value="opu_stabilimento" />
				<jsp:param name="typeView" value="button" />
				</jsp:include>
				</center>
				</td>
				<%=(numeroTdTableBottoni%4==0)?("</tr><tr>"):("") %>
				</dhv:permission>
		 		
		 		<dhv:permission name="gestione_noscia_modifica_dati_scheda-view">
		 		<%if(StabilimentoDettaglio.getStato() != 4) { %>
				<%numeroTdTableBottoni++;%>
				<td valign="top" > 
				
				<div class="dropdown">
					<input type="button" onclick="mostraListaOperazioni('dropdownModifica')" value="Modifica dati scheda" class="dropbtn" style="width:250px"/>
						<div id="dropdownModifica" class="dropdown-content" style="width:250px">
					<br>
					&nbsp;&nbsp;<label><b><i>modifiche dati</i></b></label>
				
				
					<a href="GestioneAnagraficaAction.do?command=ModifyGeneric&altId=<%=StabilimentoDettaglio.getAltId() %>&operazione=cessazione">CESSAZIONE/REVOCA</a>
				
								
					<%if (!StabilimentoDettaglio.isLineePregresse()) {%>  
						<dhv:permission name="variazione_stato_stabilimento-view"> 
							<a href="GestioneAnagraficaAction.do?command=AddGestioneRiattivaLinee&altId=<%=StabilimentoDettaglio.getAltId() %>">RIATTIVA LINEE SOSPESE</a>
							<a href="GestioneAnagraficaAction.do?command=AddGestioneSospendiLinee&altId=<%=StabilimentoDettaglio.getAltId() %>">SOSPENDI LINEE</a>
						</dhv:permission>
					<%} %>
					<%if(StabilimentoDettaglio.getTipoAttivita() == 1){ %>
						<% boolean compatibilita_ampliamento_noscia = false;
							for(int i = 0; i < StabilimentoDettaglio.getListaLineeProduttive().size(); i++ ){
								LineaProduttiva l = (LineaProduttiva) StabilimentoDettaglio.getListaLineeProduttive().get(i);
								if(l.getFlags().getCompatibilita_noscia() > 0)
								{
									compatibilita_ampliamento_noscia = true;
									break;
								}
							}
							%>
						<%if(compatibilita_ampliamento_noscia){ %>
							<a href="GestioneAnagraficaAction.do?command=ModifyGeneric&altId=<%=StabilimentoDettaglio.getAltId() %>&operazione=ampliamento">AGGIUNGI LINEA</a>
					 	<%} %>
					<%} %>
					<dhv:permission name="gestioneanagrafica-errata-corrige-view">
						<a href="GestioneAnagraficaAction.do?command=Modify&altId=<%=StabilimentoDettaglio.getAltId() %>">ERRATA CORRIGE MODIFICA ANAGRAFICA</a>
					</dhv:permission>
					
		
					<br><br>
					 
				
					</div>
					
				</td>
				
				<%=(numeroTdTableBottoni%4==0)?("</tr><tr>"):("") %>
				<%} %>
				</dhv:permission>
		<%} %>

<% out.println((numeroTdTableBottoni%4==0)?("</tr><tr>"):(""));
	} %>

<dhv:permission name="osa-cessazione-pregressa-view">
<%if(StabilimentoDettaglio.getStato() != 4 && flagLineeScia){ %>
		<%numeroTdTableBottoni++;%>
				<td valign="top" > 
					<center>
					<input style="width:250px" type="button" value="cessazione d'ufficio" onclick="loadModalWindow(); 
					location.href='GisaNoScia.do?command=Schedacessazione&altId=<%=StabilimentoDettaglio.getAltId() %>' "/>
					</center>
				</td>
		<%=(numeroTdTableBottoni%4==0)?("</tr><tr>"):("") %>
	<%} %>
</dhv:permission>

<%	if (!lineePregresse && flagLineeScia)
	{
	if (!StabilimentoDettaglio.isLineePregresse()) 
	{
%>      	
	<dhv:permission name="variazione_stato_stabilimento-view"> 
		<%numeroTdTableBottoni++;%>
			<td valign="top" > 
			<center>
			<jsp:include page="../variazionestati/variazione.jsp">
				<jsp:param name="id" value="<%=StabilimentoDettaglio.getIdStabilimento() %>" />
				<jsp:param name="tipologia" value="999" />
			</jsp:include>
			</center>
			</td>
			<%=(numeroTdTableBottoni%4==0)?("</tr><tr>"):("") %>
	</dhv:permission>
<%  
	} 
%>
	<dhv:permission name = "opu-sposta-controlli-view">
<%numeroTdTableBottoni++;%>
<td valign="top" > 
				<center>
<jsp:include page="../dialog_convergenza_cu_elimina_anagrafica.jsp"/>
</center>
</td>
<%=(numeroTdTableBottoni%4==0)?("</tr><tr>"):("") %>
</dhv:permission>

		<dhv:permission name="opu-add">
<%  
     	if (User.getRoleId()!=40)
     	{
     		boolean trovato_852=false;
     		for(int i = 0; i < StabilimentoDettaglio.getListaLineeProduttive().size(); i++ )
     		{
     			LineaProduttiva l = (LineaProduttiva) StabilimentoDettaglio.getListaLineeProduttive().get(i); 
     			// Il ciclo gestisce la visualizzazione o meno del tasto di SCIA SEMPLIFICATA PER REGISTRATI. Nello specifico, se c'è almeno 1 linea diversa da RICONOSCIUTO,
     			// è visualizzato il bottone per le REGISTRATE
     			if (l.getIdTipoGestioneScia()!=1)
     			{
     				trovato_852=true;
     				break;
     			}
     		}
     		if (trovato_852)
     		{ 
     			numeroTdTableBottoni++;      			
%>		
				<td valign="top" > 
				<center>
     			<input style="width:250px" type="button" value="OPERAZIONI DI MODIFICA STABILIMENTO"  onclick="mostraGestioneScia(<%=StabilimentoDettaglio.getIdStabilimento()%>,4)"><br/>
				<b style="color: #006699; font-weight:bold;">Per maggiori dettagli cliccare </b><b style="color: #000000; font-weight:bold;"> <a href="#" onclick="window.open('http://www.gisacampania.it/manuali/guida2016.html?link=mycfs#scia_semplificate')">QUI</b></a>
				</center>
				</td>
<% 
			} 
     	}
     	else
     	{
     		numeroTdTableBottoni++;      		
%>
			<td valign="top" > 
				<center>
     		<input style="width:250px" type="button" value="OPERAZIONI DI MODIFICA STABILIMENTO"  onclick="mostraGestioneScia(<%=StabilimentoDettaglio.getIdStabilimento()%>,4)"><br/>
			<b style="color: #006699; font-weight:bold;">Per maggiori dettagli cliccare <a href="#" onclick="window.open('http://www.gisacampania.it/manuali/guida2016.html?link=mycfs#scia_semplificate')"> QUI </a>
     		</center>
     		</td>
<% 
		} 
%>
		</dhv:permission>
<%
	} 
%>

<%	if (flagLineeScia) { %>
	<dhv:permission name="opu-erratacorrige-view">
	<%=(numeroTdTableBottoni%4==0)?("</tr><tr>"):("") %>
	
	<td valign="top" > 
				
		<div class="dropdown">
			<input type="button" onclick="mostraListaOperazioni('dropdownModificaErrataCorrige')" value="Gestione errata corrige" class="dropbtn" style="width:250px;"/>
			<div id="dropdownModificaErrataCorrige" class="dropdown-content" style="width:250px">
			<br>
				<a href="javascript:verificaPropagazionBdu('Gec.do?command=ErrataCorrige&amp;tipoErrataCorrige=1')">SOGGETTO FISICO</a>
				<a href="javascript:verificaPropagazionBdu('Gec.do?command=ErrataCorrige&amp;tipoErrataCorrige=2')">IMPRESA</a>
				<a href="javascript:verificaPropagazionBdu('Gec.do?command=ErrataCorrige&amp;tipoErrataCorrige=3')">LINEE DI ATTIVITA</a>
			<br>
			</div>
				
		</div>
					
	</td>
	
	<%numeroTdTableBottoni++; %>
	<%=(numeroTdTableBottoni%4==0)?("</tr><tr>"):("") %>
	</dhv:permission>
<%} %>

</tr>
</tbody>
</table>
</div>
<!-- FINE NUOVA GESTIONE BOTTONI -->



	<div style="float:right;">
	<% if (StabilimentoDettaglio.getStato()==4) { %>
	<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
	     <input type="button" title="Scheda cessazione" value="Scheda cessazione" onClick="openRichiestaPDFOpuAnagrafica('<%= StabilimentoDettaglio.getIdStabilimento() %>', '29');">
	
<%--         <input type="button" title="Certificato Cessazione" value="Certificato Cessazione"	onClick="openRichiestaPDFOpu2('<%= StabilimentoDettaglio.getIdStabilimento() %>', '-1', '-1', '-1', 'modelloCessazioneOpu.xml', 'SchedaCessazione');"> --%>
 	 <br/>
 	 <% } %>
 	 
 	 <!-- attestato di registrazione visibile solo per le linee SCIA -->
 	 <% if (StabilimentoDettaglio.getNumero_registrazione()!=null && flagLineeScia == true) { %>
     <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>  
     <input type="button" title="Attestato registrazione" value="Attestato registrazione" onClick="openRichiestaPDFOpuAnagrafica('<%= StabilimentoDettaglio.getIdStabilimento() %>', '<%=tipoDettaglioAttestato%>');">
     <br/>
     <%} %>
     
     <%if(flagLineeScia == false){ 
      //if(1 == 0){%>
     	<div align="right">
		<jsp:include page="../gestione_documenti/boxDocumentaleIframe.jsp">
		<jsp:param name="iframeId" value="dettaglioTemplate" />
		<jsp:param name="altId" value="<%=StabilimentoDettaglio.getAltId() %>" />
		<jsp:param name="tipo" value="SchedaAnagrafica" />
		</jsp:include>
		</div>
		<br/>     	
     <%} else { %>
     	<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
		<input type="button" title="Stampa Scheda" value="Stampa Scheda" onClick="openRichiestaPDFOpuAnagrafica('<%= StabilimentoDettaglio.getIdStabilimento() %>', '<%=tipoDettaglioScheda%>');">
		<br/>
     <%} %>
        
	

	<%
	boolean flagInformazioniAggiuntive = false;
	for(int i = 0; i < StabilimentoDettaglio.getListaLineeProduttive().size(); i++ ){
		LineaProduttiva lverificace = (LineaProduttiva) StabilimentoDettaglio.getListaLineeProduttive().get(i);
		lverificace.existCampiEstesi();
		if(lverificace.existCampiEstesi())
		{
			flagInformazioniAggiuntive = true;
			break;
		}
	}
	%>
 
 <% if (StabilimentoDettaglio.getDatiMobile().size()>0) { %>
  <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>  
  <select id="idCampoEsteso" name="idCampoEsteso">
 <% for (int i = 0; i<StabilimentoDettaglio.getDatiMobile().size(); i++) {%>
 <option value="<%=StabilimentoDettaglio.getDatiMobile().get(i).getId()%>"><%=StabilimentoDettaglio.getDatiMobile().get(i).getLabel().toUpperCase() %>: <%=StabilimentoDettaglio.getDatiMobile().get(i).getTarga() %></option> 
 <%} %>
 </select>
     <input type="button" title="Stampa Scheda Specifica" value="Stampa Scheda Specifica" onClick="openRichiestaPDFOpuAnagraficaCampoEsteso('<%= StabilimentoDettaglio.getIdStabilimento() %>', document.getElementById('idCampoEsteso').value, '41');">
     <%} else if(flagInformazioniAggiuntive){ %>
     <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
     <input type="button" title="Stampa Scheda Completa" value="Stampa Scheda Completa" onClick="openRichiestaPDFOpuAnagrafica('<%= StabilimentoDettaglio.getIdStabilimento() %>', '51');">
     <br/>
     <%} %>

<%
if(StabilimentoDettaglio.isFlagLineaSospesa())
{
	%>
<%-- 	<dhv:permission name="suap-riattivazione-opu-view"> --%>
<input type="button" title="RIATTIVAZIONE" value="RIATTIVAZIONE" onClick="javascript:$( '#dialogRiattivazione' ).dialog('open');">

<%-- </dhv:permission> --%>
	<%
}
%>
 </div>	
 	
<dhv:evaluate if="<%=viewOperatoriMercatoIttico==true%>">
	<dhv:permission name="stabilimenti-operatori-ittici-view">
		<table width="100%" border="0">
			<tr>
				<td nowrap align="right"><img
					src="images/tree0.gif" border="0" align="absmiddle"
					height="16" width="16" /> <a
					href="OpuStab.do?command=ListaOperatoriMercatoIttico&orgId=<%= StabilimentoDettaglio.getIdStabilimento() %>">Aggiungi Operatore</a>
				</td>
			</tr>
		</table>
	</dhv:permission>
</dhv:evaluate>
<dhv:container name="<%=nomeContainer %>"  selected="details" object="Operatore" param="<%=param%>"  hideContainer="false">
<center><font class="highlight"><font style="font-weight : bold;" size="2.5"><%=messagePrimaria.toUpperCase() %> </font></center>
<br>
<center>

		
 
</center>

<script> 
function refreshDimensioniIframe(){
	var iframe = document.getElementById("dettaglioTemplate");
	$(iframe).height($(iframe).contents().find('html').height());
}
 </script>

<%if (flagLineeScia == false){ 
//if (1 == 0){%>
	<iframe scrolling="no" src="GestioneAnagraficaAction.do?command=TemplateDetails&altId=<%=StabilimentoDettaglio.getAltId() %>" id="dettaglioTemplate" style="top:0;left: 0;width:100%;height: 200%; border: none; " onload="refreshDimensioniIframe()"></iframe>
<%}else if (1==1) { %>
<jsp:include page="../schede_centralizzate/iframe.jsp">
    <jsp:param name="objectId" value="<%=StabilimentoDettaglio.getIdStabilimento() %>" />
       <jsp:param name="objectIdName" value="stab_id" />
     <jsp:param name="tipo_dettaglio" value="<%=tipoDettaglioScheda %>" />
     </jsp:include>
<%} else { %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	<tr>
			<th colspan="2">
				<dhv:label name="opu.operatore_gisa"></dhv:label>
			</th>
	</tr>
	<tr>
			<td class="formLabel" nowrap>
				<dhv:label name="">Ragione Sociale</dhv:label>
			</td>
			<td>
				<%= StabilimentoDettaglio.getOperatore().getRagioneSociale() %>
			</td>
		</tr>
		<tr>
			<td class="formLabel" nowrap>
				Partita IVA
			</td>
			<td>
				<%= toHtml2( StabilimentoDettaglio.getOperatore().getPartitaIva()) %>
			</td>
		</tr>
		<tr>
			<td class="formLabel" nowrap>
				Codice Fiscale
			</td>
			<td>
				<%= toHtml2( StabilimentoDettaglio.getOperatore().getCodFiscale()) %>
			</td>
		</tr>
		
	
<dhv:evaluate if="<%= (StabilimentoDettaglio.getOperatore().getRappLegale() != null) %>">
		<tr>
			<td class="formLabel" nowrap>
				<dhv:label name="opu.soggetto_fisico_gisa"></dhv:label>
			</td>
			<td>
				<%= StabilimentoDettaglio.getOperatore().getRappLegale().getNome()+ " "+StabilimentoDettaglio.getOperatore().getRappLegale().getCognome()+ " ("+StabilimentoDettaglio.getOperatore().getRappLegale().getCodFiscale()+")" %>
			<%= (!StabilimentoDettaglio.getOperatore().getRappLegale().getIndirizzo().toString().equals("")) ? "<br>Residente in : "+ StabilimentoDettaglio.getOperatore().getRappLegale().getIndirizzo().toString() :"" %>
			</td>
		</tr>
	</dhv:evaluate>
	<dhv:evaluate if="<%= (StabilimentoDettaglio.getOperatore().getSedeLegale() != null) %>">
		<tr>
			<td class="formLabel" nowrap>
				<dhv:label name="opu.sede_legale.indirizzo"></dhv:label>
			</td>
			<td>
				<%
					String comune =  ComuniList.getSelectedValue(StabilimentoDettaglio.getOperatore().getSedeLegale().getComune());
    				String via = (StabilimentoDettaglio.getOperatore().getSedeLegale().getVia()!= null)? StabilimentoDettaglio.getOperatore().getSedeLegale().getVia():"";  %>
				<%= StabilimentoDettaglio.getOperatore().getSedeLegale().toString() %>
				
				</td>
		</tr>
	</dhv:evaluate>
	</table>
<br/>

<br/>
<dhv:evaluate if="<%=(StabilimentoDettaglio.getSedeOperativa() != null &&   
StabilimentoDettaglio.getListaLineeProduttive().size()>0 && ((LineaProduttiva)StabilimentoDettaglio.getListaLineeProduttive().get(0)).getInfoStab().getTipoAttivita()!=3) %>">

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    	
			<th colspan="2"><strong>Sede Produttiva</strong></th>
		
 </tr> 
 <dhv:evaluate if="<%=(AslList.size() > 0)%>">
	<tr>
			<td class="formLabel" nowrap><dhv:label name="opu.stabilimento.asl"></dhv:label>
			</td>
			<td><%=AslList.getSelectedValue(StabilimentoDettaglio.getIdAsl())%></td>
	</tr>
		</dhv:evaluate>
		
		 <dhv:evaluate if="<%=(StabilimentoDettaglio.getDenominazione()!= null && !"".equals(StabilimentoDettaglio.getDenominazione()) )%>">
	<tr>
			<td class="formLabel" nowrap>Denominazione
			</td>
			<td><%=StabilimentoDettaglio.getDenominazione()%></td>
	</tr>
		</dhv:evaluate>
		
		<dhv:evaluate if="<%=(StabilimentoDettaglio.getNumero_registrazione()!= null)%>">
											 
					<tr>
			<td class="formLabel" nowrap>Numero Registrazione
			</td>
			<td><%=StabilimentoDettaglio.getNumero_registrazione()%></td>
	</tr>						 
											 
	</dhv:evaluate>
	<dhv:evaluate if="<%=(StabilimentoDettaglio.getCodiceInterno()!= null)%>">
											 
					<tr>
			<td class="formLabel" nowrap>Codice Interno
			</td>
			<td><%=StabilimentoDettaglio.getCodiceInterno()%></td>
	</tr>						 
											 
	</dhv:evaluate>
	
		<dhv:evaluate if="<%=(StabilimentoDettaglio.getSedeOperativa()!= null)%>">
			<tr>
				<td class="formLabel" nowrap><dhv:label
					name="opu.sede_legale.indirizzo"></dhv:label></td>
				<td><%
				String comune = ComuniList.getSelectedValue(StabilimentoDettaglio.getSedeOperativa().getComune());
				String via = (StabilimentoDettaglio.getSedeOperativa().getVia() != null) ? StabilimentoDettaglio.getSedeOperativa().getVia(): "";
			%> 
			<%=StabilimentoDettaglio.getSedeOperativa().toString()%> </td>
			</tr>
			  <% if(Audit!=null){ %>
  
  <tr class="containerBody" >
      <td nowrap class="formLabel">
        <dhv:label name="osaa.livelloRischio" >Categoria di Rischio</dhv:label>
      </td>
      <td>
         <%= StabilimentoDettaglio.getCategoriaRischio()%>
      </td>
    </tr>
    <tr class="containerBody" >
      <td nowrap class="formLabel">
        <dhv:label name="osaa.livelloRischio" >Prossimo Controllo</br>con la tecnica della Sorveglianza</dhv:label>
      </td>
      <td>
      <% SimpleDateFormat dataPC = new SimpleDateFormat("dd/MM/yyyy");
      java.util.Date datamio = new java.util.Date(System.currentTimeMillis());
      Timestamp d = new Timestamp (datamio.getTime()); %>
  
         <%= (((StabilimentoDettaglio.getDataProssimoControllo()!=null))?(dataPC.format(StabilimentoDettaglio.getDataProssimoControllo())):(dataPC.format(d)))%>
      </td>
    </tr>
  <%}%>
</dhv:evaluate>
	<tr>
			<td class="formLabel" nowrap>Stato
			</td>
			<td><%=ListaStati.getSelectedValue(StabilimentoDettaglio.getStato())%></td>
	</tr>
 </table>

 </dhv:evaluate>

<br/>


<!-- LINEE PRODUTTIVE -->


<%


String nomePaginaInclusa =StabilimentoDettaglio.getPageInclude() ;

if (!nomePaginaInclusa.equals(""))
{
	
%>
<jsp:include page="<%= "./"+nomePaginaInclusa+"_details.jsp"%>" flush="true" />
<%} %>


<br/>	
<jsp:include page="opu_linee_attivita_details.jsp" />
<br/>	
<jsp:include page="opu_lista_distributori.jsp" />


 <%} %>
 


<jsp:include page="campi_estesi_lda.jsp"/>



<dhv:evaluate if="<%=(stabilimentiAssociateMercatoIttico.size()>0 )%>">
		<%{
			%>
			<div id="tab4" class="tab">
		
			<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	  			<tr>
	    			<th colspan="8"><strong><dhv:label name="">Operatori Associati</dhv:label></strong></th>
	  			</tr>
	  			
	  			<tr class="formLabel">
					<td width ="5%" align="left">Num. Box</td>
					<td width ="20%" align="left">Ragione Sociale</td>
					<td width ="10%" align="left">Comune</td>
					<td width ="10%" align="left">Categoria di rischio</td>
					<td width ="10%" align="left">&nbsp;</td>		
				</tr>



				<%
		  			for (OperatoriAssociatiMercatoIttico stabilimento : (ArrayList<OperatoriAssociatiMercatoIttico>) stabilimentiAssociateMercatoIttico ) 
	  				{
	  					%>    
	  						<tr>
				      			<td>
				      				<%=toHtmlValue( stabilimento.getStabilimento().getOperatoreItticoNumeroBox()  )%>&nbsp;
				      			</td>

				      			<td>
				      				<%=toHtml( stabilimento.getStabilimento().getName() )%>&nbsp;
				      			</td>

				      			<td>
				      				<%=toHtml( stabilimento.getStabilimento().getCity() )%>&nbsp;
				      			</td>
				      			
				      			<td>
				      				<%= (((stabilimento.getStabilimento().getCategoriaRischio()>0))?(stabilimento.getStabilimento().getCategoriaRischio()):("3"))%>
				      			</td>
				      			
				      			<td>
				      				<a href="OpuStab.do?command=DetailsOperatoriMercatiIttici&stabId=<%=StabilimentoDettaglio.getIdStabilimento() %>&orgId=<%=stabilimento.getStabilimento().getId()%>">Dettagli</a>
				      				<dhv:permission name="stabilimenti-operatori-ittici-view">
				      					<a onclick="if( confirm('Sei sicuro di voler eliminare l\'operatore?')){ location.href='OpuStab.do?command=EliminaOperatoreMercatoIttico&id=<%=stabilimento.getId()%>&orgId=<%=StabilimentoDettaglio.getIdStabilimento()%>';}" 
				      				   		href="#">Elimina</a>
				      				</dhv:permission>
				      			</td>
				      			
				      		</tr>
  		      			<%		  		
	  				}
	  			
	  			%>
	  			
	  			
	  		</table>
	  		</div>
	  		<%} %>
  		</dhv:evaluate>



<dhv:permission name="opu-edit">
<% if (true){ %>
<!-- % if (StabilimentoDettaglio.getTipoAttivita()!=Stabilimento.ATTIVITA_MOBILE && idLineaDistributori>0){ % -->
<% } else { %>
<center>
<table class="details" width="50%">
<tr><th colspan="2" align="center">Gestione campi estesi</th></tr>
<tr><td align="center"><a href="#" onClick="openPopupLarge('OpuStab.do?command=PrepareModificaLineeCampiEstesi2&stabId=<%=StabilimentoDettaglio.getIdStabilimento()%>')">Campi estesi</a></td></tr>
</table>
</center>
<% } %>
</dhv:permission>

<%--

--%>

</dhv:container>








<script>

function checkFieldRiattivazione()
{
	var msg ="Attenzione controllare che i seguenti campi siano valorizzati : \n" ;
	formTest= true ;
	if($("#dataRiattivazione2").val()=="")
		{
		formTest = false;
		msg+="- Controllare il Campo Data Riattivazione\n";
		}
	
	if($("#decretoRiattivazione").val()=="")
	{
	formTest = false;
	msg+="- Controllare il Campo Decreto Riattivazione\n";
	}
	
	
	
	var checkedVal = $('input[name=idLineaProduttivaRiattivazione]:checked');
	
	if(checkedVal.length==0)
		{
		formTest = false;
		msg+="- Controllare di aver Spuntato almeno una linea \n";
		}
	
	
	if(formTest==false)
		alert(msg);
	
	return formTest;
		
	
	
	
	
}
</script>

<div id = "dialogRiattivazione">
<form method="Post" action="OpuStab.do?command=RiattivazioneSospensione" onsubmit="return checkFieldRiattivazione()">
<input type="hidden" name="stabId" value="<%=StabilimentoDettaglio.getIdStabilimento() %>">
<input type="hidden" name="altId" value="<%=StabilimentoDettaglio.getAltId() %>">
<table cellpadding="4" cellspacing="0" width="100%" class="details">
	
	<tr>
    <th colspan="2">
      <strong>RIATTIVAZIONE</strong>
    </th>
	</tr>
 <tr class="containerBody">
      <td nowrap class="formLabel">
       Data Riattivazione
      </td>
      <td><input type = "text" name="dataRiattivazione" readonly="readonly" id = "dataRiattivazione2"  >
      	<a href="#" onClick="cal19.select(document.getElementById('dataRiattivazione2'),'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
      
      </td>
    </tr>
 <tr class="containerBody">
      <td nowrap class="formLabel">
       Note
      </td>
      <td><textarea rows="5" cols="30" name="noteRiattivazione"></textarea></td>
    </tr>
    
     <tr class="containerBody">
      <td nowrap class="formLabel">
       Numero Decreto
      </td>
      <td><input type = "text"  name="decretoRiattivazione" id="decretoRiattivazione" ></td>
    </tr>
    
     <tr class="containerBody">
      <td nowrap class="formLabel">
       Linee Da Riattivare
      </td>
      <td>
      <table class="noborder">
      <tr>
      
      <td>DESCRIZIONE LINEA DI ATTIVITA</td>
      <td>DATA SOSPENSIONE</td>
      <td>Seleziona</td>
      <%
      Iterator<LineaProduttiva> itLp = StabilimentoDettaglio.getListaLineeProduttive().iterator();
      while (itLp.hasNext())
      {
    	  LineaProduttiva lp = itLp.next();
    	  if(lp.getStato()==Stabilimento.STATO_SOSPESO)
    	  {
    	  %>
    	  <tr>
    	  <td><%=lp.getDescrizione_linea_attivita() %></td>
    	   <td><%=toDateString(lp.getDataSospensionevolontaria()) %></td>
    	   <td><input type = "checkbox" value="<%=lp.getId()%>" name = "idLineaProduttivaRiattivazione"checked="checked"></td>
    	  
    	  <%
    	  }
      }
      %>
      
      </table>
      
      </td>
    </tr>
    <tr>
    <td colspan="2">
    <input type = "submit" value = "Riattiva">
    </td></tr>
    
</table>
</form>

</div>

<div id = "dialogGestioneScia">

</div>

<div id = "dialogStatoBdu">

</div>



