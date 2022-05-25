<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<link href="css/nonconformita.css" rel="stylesheet" type="text/css" />

<script>var myRequest = null;
var idTypeList = new Array();

function CreateXmlHttpReq(handler) {
  var xmlhttp = null;
  if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
  
  xmlhttp.onreadystatechange = handler;
  return xmlhttp;
}

function myHandler() {
	//alert('my handler')
    if (myRequest.readyState == 4 && myRequest.status == 200) {

		document.details.action=link_ret ;//    	location.reload(true);
		document.details.submit();
    }
}
var link_ret;
function riapriChiusuraTemp(link,link_di_ritorno)
{
	link_ret = link_di_ritorno ;
	 myRequest = CreateXmlHttpReq(myHandler);
	myRequest.open("GET",link);
	myRequest.send(null);
}

function openAlertAMR(){
	var msg = "Attenzione! Per la compilazione e l'invio automatico a SINVSA dei dati relativi ai campioni AMR 2018 fatti nei MACELLI per la matrice INTESTINO è OBBLIGATORIO avere a disposizione le seguenti informazioni (da recuperare da SINVSA):\n 1) 'NUM REGISTRAZIONE PROVENIENZA': in questo campo occorre specificare il CUN SINVSA dell'allevamento di provenienza (es. 120AV079-00642520233-30-1280789-PGLPLE71P28A509I-POL-AL-8)\n 2)'LOCALE/RECINTO CAMPIONATO' e 'DATA DI ACCASAMENTO': questi due campi devono contenere l'identificativo del capannone e la data ultimo accasamento relativi all'allevamento di provenienza (es. per l'allevamento con CUN '001BS004-PLTPPP75T02A116O-30-2068190-PLTPPP75T02A116O-POL-AL-8' identificativo capannone:2 e data ultimo accasamento: 15/05/2018).\n\n Si ricorda inoltre che:\n\n * il campo 'NUMERO VERBALE AMR' deve contenere altri caratteri/numeri oltre alla lettera 'I' finale (es. 1150772I) \n ** una copia del verbale scaricabile cliccando sul bottone 'STAMPA DOCUMENTO ACCOMPAGNAMENTO CAMPIONE AMR' deve essere sempre inviata per posta elettronica all'ORSA";
	alert(msg);
}
</script>


<%if (TicketDetails.isflagBloccoCu()==true){
		%>
		
		<font color = "red" ><%=TicketDetails.getNoteBlocco() %></font><br><br>
	
	<% if (TicketDetails.getClosed() == null && User.getUserRecord().getGruppo_ruolo()==UserUtils.getUserFormId(request, TicketDetails.getEnteredBy()).getGruppo_ruolo()) { %>
	   
	    <dhv:permission name="operazioni-hd-view">
			<input type="button"
			value="Chiusura Cu Pregresso" onClick="javascript:this.form.action='<%=OrgDetails.getAction()  %>Vigilanza.do?command=ChiudiTutto&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere il Controllo Ufficiale? \n Attenzione! La pratica verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore') ){loadModalWindow();submit()};">
			</dhv:permission>
		<%} %>
		
		 
	<%
	
	}

TicketDetails.setActionChecklist();
TicketDetails.setActionChecklist();

if(TicketDetails.isTrashed())
{
	%>
	<dhv:permission name="reopen-reopen-delete">
	<input type="button"
			value="Ripristina"
			onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>Vigilanza.do?command=Restore&id=<%= TicketDetails.getId()%><%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	<%
	
}else
	 if (TicketDetails.getClosed() != null || TicketDetails.isChiusura_attesa_esito()==true) {
			if (TicketDetails.getClosed() != null && TicketDetails.getTipoCampione()!=5 && TicketDetails.isflagBloccoCu()==false && User.getUserRecord().getGruppo_ruolo()==UserUtils.getUserFormId(request, TicketDetails.getEnteredBy()).getGruppo_ruolo())
			{
	%>
	<dhv:permission name="reopen-reopen-view">
		<input type="button"
			value="APRI DI NUOVO"
			onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>Vigilanza.do?command=ReopenTicket&id=<%= TicketDetails.getId()%><%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	<%
			}
			else
			{
				if ( TicketDetails.isChiusura_attesa_esito()==true  && TicketDetails.isflagBloccoCu()==false && User.getUserRecord().getGruppo_ruolo()==UserUtils.getUserFormId(request, TicketDetails.getEnteredBy()).getGruppo_ruolo())
				{
					%>
					<%if(TicketDetails.getAltId() > 0) { %>
<!-- 					<input type="button" -->
<!-- 					value="Annulla Chiusura Momentanea" -->
<%-- 					onClick="javascript:riapriChiusuraTemp('PrintReportVigilanza.do?command=AnnullaChiusuraTemp&id=<%= TicketDetails.getId() %>','<%= TicketDetails.getURlDettaglio() %>Vigilanza.do?command=TicketDetails&id=<%=TicketDetails.getId() %>&altId=<%=TicketDetails.getAltId() %>')"> --%>
					<% } %>
					
				<%
					
				}
				
			}
	
		} else {
			if (TicketDetails.isflagBloccoCu()==false &&  User.getUserRecord().getGruppo_ruolo()==UserUtils.getUserFormId(request, TicketDetails.getEnteredBy()).getGruppo_ruolo())
			{
			
	%>
	<dhv:permission name="<%=permission_op_edit%>">
		<input type="button"
			value="Modifica"
			onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>Vigilanza.do?command=ModifyTicket&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	<%} %>
	
	<% if(TicketDetails.isCategoriaisAggiornata() == false  && User.getUserRecord().getGruppo_ruolo()==UserUtils.getUserFormId(request, TicketDetails.getEnteredBy()).getGruppo_ruolo()) {%>
	<dhv:permission name="<%=permission_op_del%>">
		<%
			if ("searchResults".equals(request
								.getParameter("return"))) {
		%>
		
		<% if (TicketDetails.isVincoloRegistro()) {%>
		<%if(TicketDetails.getAltId() > 0) { %>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('<%=OrgDetails.getAction() %>Vigilanza.do?command=ConfirmDelete&assetId=<%=TicketDetails.getAssetId() %>&codiceallerta=<%= TicketDetails.getCodiceAllerta() %>&id=<%= TicketDetails.getId() %>&altId=<%=TicketDetails.getAltId()%>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');">
			<% }  %>
		<%
			} else {
				 if(TicketDetails.isflagBloccoCu() == false  && User.getUserRecord().getGruppo_ruolo()==UserUtils.getUserFormId(request, TicketDetails.getEnteredBy()).getGruppo_ruolo()) {
		%>
		<% if(TicketDetails.getAltId() > 0) {%>
		 <input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('<%=OrgDetails.getAction() %>Vigilanza.do?command=ConfirmDelete&assetId=<%=TicketDetails.getAssetId() %>&codiceallerta=<%= TicketDetails.getCodiceAllerta() %>&id=<%= TicketDetails.getId() %>&altId=<%=TicketDetails.getAltId()%>&popup=true', 'Delete_ticket','320','200','yes','no');">
			<% } %> 
		<%
			}}
		
			} else if(TicketDetails.isflagBloccoCu() == false) {
		%>
		<%if(TicketDetails.getAltId() > 0) { %>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('<%=OrgDetails.getAction() %>Vigilanza.do?command=ConfirmDelete&assetId=<%=TicketDetails.getAssetId() %>&codiceallerta=<%= TicketDetails.getCodiceAllerta() %>&id=<%= TicketDetails.getId() %>&altId=<%=TicketDetails.getAltId()%>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');">
			<% } %>
			<%
			}
		%>
		
	</dhv:permission>
	
	<%} %>
	
	
		<dhv:permission name="<%=permission_op_edit%>">
	<%
	if (TicketDetails.getTipoCampione()!=5) {
	if(TicketDetails.isflagBloccoCu() == false && User.getUserRecord().getGruppo_ruolo()==UserUtils.getUserFormId(request, TicketDetails.getEnteredBy()).getGruppo_ruolo()) {
	if (TicketDetails.isControllo_chiudibile()==true )
	{
	%>
		<input type="button"
			value="chiudi tutto il controllo"
			<%if(TicketDetails.getTipologiaOperatore()==1){ %>
			onClick="this.form.action='<%=OrgDetails.getAction() %>Vigilanza.do?command=ChiudiTutto&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere il Controllo Ufficiale? \n Attenzione! La pratica verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore') ){submit()};">
			<%}
		else{%>	onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>Vigilanza.do?command=ChiudiTutto&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere il Controllo Ufficiale? \n Attenzione! La pratica verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore') ){submit()};">
		<%} %>
	<%}
	else
	{
		if(User.getUserRecord().getGruppo_ruolo()==UserUtils.getUserFormId(request, TicketDetails.getEnteredBy()).getGruppo_ruolo())
		{
		
		%>
			<%if(TicketDetails.getAltId() > 0) {%>
			<input type="button"
			value="chiudi tutto il controllo"
			title="Chiude il Controllo in maniera momentanea in attesa di esito di campioni e tamponi"
			onClick="javascript:alert('ATTENZIONE! Stai per chiudere il controllo in maniera momentanea in attesa di esito di campioni e tamponi.');this.form.action='<%=OrgDetails.getAction() %>Vigilanza.do?command=ChiudiTutto&altId=<%=TicketDetails.getAltId() %>&id=<%= TicketDetails.getId() %>';submit();">
			
			
			<%
		}}
		
	}
	} }
	%>

	</dhv:permission>
	
	
	
	<dhv:permission name="<%="old_permission_op_edit"%>">
	<%
	if(TicketDetails.isflagBloccoCu() == false && User.getUserRecord().getGruppo_ruolo()==UserUtils.getUserFormId(request, TicketDetails.getEnteredBy()).getGruppo_ruolo()) {
	if (TicketDetails.isControllo_chiudibile()==true )
	{
	%>
		<input type="button"
			value="chiudi tutto il controllo"
			<%if(TicketDetails.getTipologiaOperatore()==1){ %>
			onClick="this.form.action='<%=OrgDetails.getAction() %>Vigilanza.do?command=ChiudiTutto&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere il Controllo Ufficiale? \n Attenzione! La pratica verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore') ){submit()};">
			<%}
		else{%>	onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>Vigilanza.do?command=ChiudiTutto&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere il Controllo Ufficiale? \n Attenzione! La pratica verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore') ){submit()};">
		<%} %>
	<%}
	else
	{
		if(User.getUserRecord().getGruppo_ruolo()==UserUtils.getUserFormId(request, TicketDetails.getEnteredBy()).getGruppo_ruolo())
		{
		
		%>
			<%if(TicketDetails.getAltId() > 0) {%>
			<input type="button"
			value="chiudi tutto il controllo"
			title="Chiude il Controllo in maniera momentanea in attesa di esito di campioni e tamponi"
			onClick="javascript:alert('ATTENZIONE! Stai per chiudere il controllo in maniera momentanea in attesa di esito di campioni e tamponi.');this.form.action='<%=OrgDetails.getAction() %>Vigilanza.do?command=ChiudiTutto&altId=<%=TicketDetails.getAltId() %>&id=<%= TicketDetails.getId() %>';submit();">
			<% }  %>
			
	
		<%
		}
		
	}
	}
	%>
	
	
		<input type="button"
			value="chiudi tutto il controllo" 
			onClick="this.form.action='<%=TicketDetails.getURlDettaglio() %>Vigilanza.do?command=ChiudiTutto&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere il Controllo Ufficiale? \n Attenzione! La pratica verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore') ){loadModalWindow();submit()};"
			/>
	
	</dhv:permission>
	
	<%
	
	if((TicketDetails.getNumeroAudit()>0 || (TicketDetails.isChecklistLibera() && TicketDetails.getPunteggio()>0)) && TicketDetails.isCategoriaisAggiornata() == false && TicketDetails.isCategoriaAggiornabile()==true && User.getUserRecord().getGruppo_ruolo()==UserUtils.getUserFormId(request, TicketDetails.getEnteredBy()).getGruppo_ruolo())
		{
		if(TicketDetails.getAssetId()>0)
		{
		%>
		
		<%if(TicketDetails.getAltId() > 0) {%>
		<input type="button"
			value="Aggiorna Categoria"
			onClick="aggiornaCategoria('<%=OrgDetails.getAction()%>CheckList',<%=TicketDetails.getId() %>,<%=TicketDetails.getAltId() %>,<%=TicketDetails.getAssetId() %>)">
	<% } %>
	<%
		}else
		{
			
			if(TicketDetails.getAltId() > 0){
			%>
				<input type="button"
			value="Aggiorna Categoria E CHIUDI TUTTO IL CONTROLLO"
			onClick="aggiornaCategoria('<%=OrgDetails.getAction()%>CheckList',<%=TicketDetails.getId() %>,<%=TicketDetails.getAltId() %>)">
			<%
			}
		}
		}
		}

if(TicketDetails.getSupervisionato_in_data()==null && TicketDetails.isflagBloccoCu()==false && User.getUserRecord().getGruppo_ruolo()==UserUtils.getUserFormId(request, TicketDetails.getEnteredBy()).getGruppo_ruolo())
{
	%> 
		<dhv:permission name="vigilanza-vigilanza-supervisiona-view">
	
		&nbsp;&nbsp;&nbsp;&nbsp;<a class="ovalbutton" href ="#dialog10" name = "modal"><span>Supervisiona</span></a>
	</dhv:permission>

	
	<%
	
}
	%>
	
	
	<dhv:permission name="global-search-amr-add">
	<% if (TicketDetails.isAMR()) {%>
	<br/>
	<div align="right">
	<b><a href="#" style="-moz-appearance: button;" onClick="openAlertAMR(); openPopupBox('PrintReportVigilanza.do?command=GestioneAMR&idControllo=<%=TicketDetails.getId()%>')">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Gestione dati per invio AMR a SINVSA &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></b>
	<br/><b><a href="#" style="background-color:red; color: white;" onClick="openAlertAMR(); openPopupBox('PrintReportVigilanza.do?command=GestioneAMR&idControllo=<%=TicketDetails.getId()%>')"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Compila il verbale di prelievo da inviare all'IZSM Lazio per campioni AMR &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></b>
	</div>
	<br/>
	<% } %>
	</dhv:permission>	
	
	
	
<script>


$(document).ready(function() {	

	
	//select all the a tag with name equal to modal
	$('a[name=modal]').click(function(e) {
		//Cancel the link behavior
		e.preventDefault();
		
		//Get the A tag
		var id = $(this).attr('href');
	
		//Get the screen height and width
		var maskHeight = $(document).height();
		var maskWidth = $(window).width();
	
		//Set heigth and width to mask to fill up the whole screen
		$('#mask').css({'width':maskWidth,'height':maskHeight});
		
		//transition effect		
		$('#mask').fadeIn(1000);	
		$('#mask').fadeTo("slow",0.8);	
	
		//Get the window height and width
		var winH = $(window).height();
		var winW = $(window).width();
              
		//Set the popup window to center
		$(id).css('top',  winH/2-$(id).height()/2);
		$(id).css('left', winW/2-$(id).width()/2);
		
		//transition effect
		$(id).fadeIn(2000); 
	
	});
	
	//if close button is clicked
	$('.window .close').click(function (e) {
		//Cancel the link behavior
		e.preventDefault();
		
		$('#mask').hide();
		$('.window').hide();
	});		
	
	//if mask is clicked
	$('#mask').click(function () {
		$(this).hide();
		$('.window').hide();
	});			
	
});

</script>
<style>
body {
	font-family: verdana;
	font-size: 15px;
}

a {
	color: #333;
	text-decoration: none
}

a:hover {
	color: #ccc;
	text-decoration: none
}

#mask {
	position: absolute;
	left: 0;
	top: 0;
	z-index: 9000;
	background-color: #000;
	display: none;
}

#boxes .window {
	position: absolute;
	left: 0;
	top: 0;
	width: 675px;
	height: 658;
	display: none;
	z-index: 9999;
	padding: 20px;
}

#boxes
#dialog
#
{
width:675px;
height:680;
padding:10px;
background-color:#ffffff
;}
#dialog4 {
	width: 100%;
	height: 100%;
	padding: 10px;
	background-color: #ffffff;
	overflow: scroll;
}

#dialog10 {
	width: 100%;
	height: 100%;
	padding: 10px;
	background-color: #ffffff;
	overflow: scroll;
}


#boxes #dialog1 {
	width: 375px;
	height: 203px;
}

#dialog1 .d-header {
	background: url(images/login-header.png) no-repeat 0 0 transparent;
	width: 375px;
	height: 150px;
}

#dialog1 .d-header input {
	position: relative;
	top: 60px;
	left: 100px;
	border: 3px solid #cccccc;
	height: 22px;
	width: 200px;
	font-size: 15px;
	padding: 5px;
	margin-top: 4px;
}

#dialog1 .d-blank {
	float: left;
	background: url(images/login-blank.png) no-repeat 0 0 transparent;
	width: 267px;
	height: 53px;
}

#dialog1 .d-login {
	float: left;
	width: 108px;
	height: 53px;
}

#boxes #dialog2 {
	background: url(images/notice.png) no-repeat 0 0 transparent;
	width: 326px;
	height: 229px;
	padding: 50px 0 20px 25px;
}
</style>




<input type="hidden" id="idChecklist" name="idChecklist"
	value="<%=request.getAttribute("idChecklist_corrente")%>" />


<div id="boxes">


<div id="dialog4" class="window"><a href="#" class="close" /><font
	color="red">CHIUDI</font></a> <br>
<%@ include file="guida_compila_checklist.txt"%>
</div>


<div id="dialog10" class="window"><a href="#" class="close" /><font
	color="red">CHIUDI</font></a> <br>
<script>
function checkFormSupervisione()
{
	if (document.getElementById('flag_supervisione_efficace_si').checked==true)
		document.supervisionaform.flag_supervisione_efficace.value = 'si';
	else
	{
		document.supervisionaform.flag_supervisione_efficace.value = 'no';
	}
	document.supervisionaform.supervisione_note.value = document.getElementById('supervisione_note').value
	document.supervisionaform.submit();
}
	
</script>

<%if(TicketDetails.getAltId() > 0) { %>
<form method="post" name = "supervisionaform" action = "<%=OrgDetails.getAction() %>Vigilanza.do?command=Supervisiona&altId=<%=TicketDetails.getAltId()%>&id=<%= TicketDetails.getId() %>&return=searchResults&popup=true">
<% }  %>
 <table cellpadding="4" cellspacing="0" border="0" width="100%"
				class="details">

				<tr>
					<th colspan="2">Dettagli Supervisione</th>
				</tr>
				
				<tr>
					<td class="formLabel" nowrap>Il Controllo e' risultato efficace e congruo</td>
					<td>
					
					 SI <input type = "radio" name = "flag" id = "flag_supervisione_efficace_si" value = "si" onclick="document.getElementById('note_supervisione').style.display='none'"   checked="checked" >
					 NO <input type = "radio" name = "flag"  id = "flag_supervisione_efficace_no" value = "no" onclick="document.getElementById('note_supervisione').style.display=''" >
					
					</td>
				</tr>
				
				<tr id = "note_supervisione" style="display: none">
					<td class="formLabel" nowrap>Indicare il motivo</td>
					<td><textarea rows="6" cols="30" id = "supervisione_note" name = "supervisione_note"></textarea> </td>
				</tr>
				<input type = "hiddens"  name= "flag_supervisione_efficace" />
				<tr><td colspan="2"><input type = "button" onclick="checkFormSupervisione()" value = "Salva Supervisione"></td></tr>

</table>
</form>

</div>


 <script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>

<dhv:permission name="cu_stampa-view">
<div align="right">
<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
<input type="button" title="Stampa Riepilogativa del Controllo Ufficiale" value="Stampa Riepilogativa del Controllo Ufficiale"	onClick="openRichiestaPDFControlli('<%= TicketDetails.getId() %>');">
</div>
</dhv:permission>

<!-- Mask to cover the whole screen -->
<div id="mask"></div>

</div>

<% if (TicketDetails.getTipoCampione() == 4 && TicketDetails.getClosed() == null && (TicketDetails.getStatusId() == 1 || TicketDetails.getStatusId() == 3)){ %>
<dhv:permission name="cu_aggiunta_motivi-view">
<br/> <input type="button" value="aggiungi piano/attivita" onClick="openPopupFull('Vigilanza.do?command=AddMotivoCU&idControlloUfficiale=<%=TicketDetails.getId()%>')"/><br/><br/>
</dhv:permission>
<% } %>

<% //GESTIONE MESSAGGIO ERRORE CHIUSURA CU
if (request.getAttribute("Messaggio")!=null && !request.getAttribute("Messaggio").equals("")){%>
<script>
if (typeof messaggioAlert !== 'undefined') { } else { var messaggioAlert = false;} //Istanzio la variabile se non gia presente (questa pagina viene inclusa due volte)

if (messaggioAlert==false){ //se ho gia mostrato l'alert
	alert('<%=request.getAttribute("Messaggio") %>');
	messaggioAlert = true; //metti a true in modo da non mostrarlo di nuovo (questa pagina viene inclusa due volte)
}
</script>
<%} %>