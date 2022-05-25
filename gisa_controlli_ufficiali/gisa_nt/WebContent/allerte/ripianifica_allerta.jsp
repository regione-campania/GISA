<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.troubletickets.base.*,com.zeroio.iteam.base.*, org.aspcfs.modules.quotes.base.*,org.aspcfs.modules.base.EmailAddress" %>
<%@page import="org.aspcfs.modules.allerte.base.AslCoinvolte"%>
<%@page import="org.aspcfs.modules.allerte.base.ListaDistribuzione"%>
<%@page import="org.aspcfs.modules.allerte.base.ImpreseCoinvolte"%>
<%@page import="org.aspcfs.modules.campioni.base.Analita"%>
<%@page import="java.util.Date"%>
<%@page import="org.aspcfs.modules.allerte.base.Ticket"%><jsp:useBean id="TicketDetails" class="org.aspcfs.modules.allerte.base.Ticket" scope="request"/>
<jsp:useBean id="ticketCategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="product" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="customerProduct" class="org.aspcfs.modules.products.base.CustomerProduct" scope="request"/>
<jsp:useBean id="quoteList" class="org.aspcfs.modules.quotes.base.QuoteList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoAlimento" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Origine" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentoInteressato" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="NonConformita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ListaCommercializzazione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AltriAlimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiNonTrasformati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiNonTrasformatiValori" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiTrasformati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiVegetali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="UnitaMisura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SanzioniAmministrative" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SanzioniPenali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Sequestri" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="causeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ticketStateList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Regioni" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolutionList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="defectCheck" class="java.lang.String" scope="request"/>
<jsp:useBean id="defect" class="org.aspcfs.modules.troubletickets.base.TicketDefect" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>


<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>


<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>





<script>

function openPopupAggiungiLista(id){
  window.open('TroubleTicketsAllerte.do?command=AddListaDistribuzione&idAllerta='+id,'popupSelect',
      'height=400px,width=1080px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
	}	

</script>

<script type="text/javascript">

function checkInt( form )
{
	var input		= form.cu.value;
	input 			= input.replace(/\s/g,'');
	input 			= (input.length == 0) ? ( "-1" ) : ( input ) ;
	var ret			= false;
	var int_value	= -1;
	messaggio1 = "" ;
	
	try
	{
		int_value = parseInt( input );
		
		if( int_value > -1 )
		{
			ret = true;
		}
		else
		{
			ret = false;
		}
	}
	catch (e)
	{
		ret = false;
	}


	if( !ret )
	{
		messaggio1+=label("", " - Inserire un valore intero non negativo \n ");
		
		
		
	}
	else
	{

		cuRegione=document.getElementById("cuRegione").value;	
		cuasl=document.getElementById("cu_pianificati").value;
		

		if(cuasl < cuRegione){
			
		if(document.details.motivazione.value==""){

			messaggio1 +=label("", " - Si è scelto di ridurre il numero di controlli pianificati dal nodo regionale.Indicare i motivi");
			ret = false ;

		}
			
		}

		if(ret == false)
		{
			alert( messaggio1 );
		}
		else
		{
			ret = confirm( "Sicuro di Voler Pianificare " + int_value + " C.U.?" );
		}
		

		
		
		
	}
	
	return ret;
}
function mostraMotivo(){

	

	
	cuRegione=document.getElementById("cuRegione").value;	
cuasl=document.getElementById("cu_pianificati").value;


if(cuasl!=""){
if(cuasl==cuRegione){
	
document.getElementById("descr").style.display="none";
	
}else{
	document.getElementById("descr").style.display=""
}

	
}
	
}


function openconfirm(msg,cueseguiti ,idAllerta,chiusuraUfficio,idAslUtente,numero_cu_seguiti,cu_pianificati,tipo_alimenti,specie_alimenti)
{

	window.open('TroubleTicketsAllerte.do?command=PupUpConfirmChiusura&msg='+msg+'&cueseguiti='+cueseguiti+'&idAllerta='+idAllerta+'&chiusuraUfficio='+chiusuraUfficio+'&idAslUtente='+idAslUtente+'&numero_cu_seguiti='+numero_cu_seguiti+'&cu_pianificati='+cu_pianificati+'&tipo_alimenti='+tipo_alimenti+'&specie_alimenti='+specie_alimenti,null,
	'height=350px,width=650px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');
	
}

function openchiusuraAllerta(cueseguiti , idAllerta,chiusuraUfficio,idAslUtente,numero_cu_seguiti,cu_pianificati)
{

	
	tipo_alimenti =document.getElementById("tipoAlimenti").value;
	specie_alimenti = document.getElementById("specie_alimenti").value;
	 
	if (idAslUtente!=-1)
	{
	if (cueseguiti>=cu_pianificati)
	{


	openconfirm('Attenzione! Il sistema chiuderà l\'allerta per la propria ASL ed invierà in automatico l\'Allegato F al referente NU.RE.CU, Alla mail che sta per essere inviata vanno allegati altri file ?',cueseguiti ,idAllerta,chiusuraUfficio,idAslUtente,numero_cu_seguiti,cu_pianificati,tipo_alimenti,specie_alimenti);
	//document.details.submit();
	

	}
	else
	{
		if (numero_cu_seguiti>=0 )
		{
			msg = '';
			if (numero_cu_seguiti!=cu_pianificati)
			{
				msg = 'Attenzione non sono stati copletati i controlli ufficiali, Sei Sicuro di voler chiudere l\'allerta ?';
			}
			else
			{
				msg = 'Attenzione  risultano controlli ufficiali ancora aperti, Sei Sicuro di voler chiudere l\'allerta ?';
				
			}
			if(confirm(msg) )
			{

				openconfirm('Attenzione! Il sistema chiuderà l\'allerta per la propria ASL ed invierà in automatico l\'Allegato F al referente NU.RE.CU, Alla mail che sta per essere inviata vanno allegati altri file ?',cueseguiti ,idAllerta,chiusuraUfficio,idAslUtente,numero_cu_seguiti,cu_pianificati,tipo_alimenti,specie_alimenti);
			
			}
			}
			
			
		}
		
		
	
	}else
	{
		if (chiusuraUfficio == 1)
		{
			if(confirm('Attenzione l \'allerta verrà chiusa per tutte le asl. Sicuro di voler procedere ?') )
			{
				//R.M: chiusura definitiva con popup modale
				var maskHeight = $(document).height();
				var maskWidth = $(window).width();
				//Set heigth and width to mask to fill up the whole screen
				$('#mask').css({'width':maskWidth,'height':maskHeight});
				$('#mask').fadeIn(1000);        
			    $('#mask').fadeTo("slow",0.8);        
				$('#mask').show();
				//Get the window height and width
				var winH = $(window).height();
				var winW = $(window).width();
				$('#dialog4').css('top',  winH/2-$('#dialog4').height()/2);
			    $('#dialog4').css('left', winW/2-$('#dialog4').width()/2);
				$('#dialog4').fadeIn(2000); 
				
				//document.details.action='TroubleTicketsAllerte.do?command=ChiudiAllerta&chiusuraUfficio=1&id='+idAllerta;
				//document.details.submit();
				document.getElementById('chiusuraUfficio').value=1;
			}
		}
		else
		{
			//R.M: chiusura definitiva con popup modale
			var maskHeight = $(document).height();
			var maskWidth = $(window).width();
			//Set heigth and width to mask to fill up the whole screen
			$('#mask').css({'width':maskWidth,'height':maskHeight});
			$('#mask').fadeIn(1000);        
		    $('#mask').fadeTo("slow",0.8);        
			$('#mask').show();
			//Get the window height and width
			var winH = $(window).height();
			var winW = $(window).width();
			$('#dialog4').css('top',  winH/2-$('#dialog4').height()/2);
		    $('#dialog4').css('left', winW/2-$('#dialog4').width()/2);
			$('#dialog4').fadeIn(2000); 
			
			//document.details.action='TroubleTicketsAllerte.do?command=ChiudiAllerta&chiusuraUfficio=0&id='+idAllerta;
			//document.details.submit();
			document.getElementById('chiusuraUfficio').value=0;
		}

	}
	
}

</script>

<%@ include file="../initPage.jsp" %>

<%if( User.getSiteId() > 0 ) {%>

<body onload="mostraMotivo()">

<%} 

%>
<form name="details" action="TroubleTicketsAllerte.do?command=Modify&auto-populate=true" method="post">
<%-- Trails --%>
<input type="hidden" name = "risposta" id = "risposta">
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="TroubleTicketsAllerte.do"><dhv:label name="sanzioniss">Allerta</dhv:label></a> >

  <a href="TroubleTicketsAllerte.do?command=Details&id=<%=TicketDetails.getId()%>"><dhv:label name="sanzioni.visualizzaee">Scheda Allerta</dhv:label></a> >


<dhv:label name="sanzioni.dettagliss">Ripianifica Allerta</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>

<%
	String param1 = "id=" + TicketDetails.getId();
%>
<dhv:container name="allerte" selected="details" object="TicketDetails"
	param="<%= param1 %>"
	hideContainer='<%= isPopup(request) || (defectCheck != null && !"".equals(defectCheck.trim())) %>'>


	<dhv:permission name="allerte-allerte-edit">
		<input type="button"
			value="<dhv:label name="">Aggiungi nuova lista di distribuzione</dhv:label>"
	onClick="openPopupAggiungiLista('<%=TicketDetails.getId()%>')">		
	</dhv:permission>
	
		


<%

	int size = ( (User.getSiteId() > 0) ? (1) : (SiteIdList.size()) );
%>
<% if (TicketDetails.getListaCommercializzazione()==2){%>

<% Hashtable<Integer,ListaDistribuzione> liste= TicketDetails.getListe_distribuzione();
	Iterator<Integer> it=liste.keySet().iterator();
	int ldd=0;
	while(it.hasNext()){
	ldd=it.next();
	ListaDistribuzione lista = (ListaDistribuzione) liste.get(ldd);
		
	%>
	<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr><th colspan="2">Info allerta</th></tr>
	<tr class="containerBody"><td nowrap class="formLabel"> Lista di distribuzione	</td>
	<td>SENZA</td>
	</tr>
	<tr class="containerBody"><td nowrap class="formLabel"> ASL Coinvolte	</td>
	<td><%=lista.getAsl_coinvolteAsString() %></td>
	</tr>
	</table>
	
	<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr><th colspan="8">INFO CU</th></tr>
	<%@ include file="tabella_info_cu.jsp" %>
	</table>
	<br/>
	<%}%>

<%} else { %>

	<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr><th colspan="2">Info allerta</th></tr>
	<tr class="containerBody"><td nowrap class="formLabel"> Lista di distribuzione	</td>
	<td>CON</td>
	</tr>
	</table>
<br/>
<% Hashtable<Integer,ListaDistribuzione> liste= TicketDetails.getListe_distribuzione();
	Iterator<Integer> it=liste.keySet().iterator();
	int ldd=0;
	while(it.hasNext()){
	ldd=it.next();
	ListaDistribuzione lista = (ListaDistribuzione) liste.get(ldd);
	%>
	
	<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr><th colspan="2">LISTA DI DISTRIBUZIONE <%=ldd %> 
	
	<%if (lista.getData_trasmissione()==null) { %>
	<dhv:permission name="allerte-allerte-edit">
		<input type="button"
			value="Modifica"
			onClick="javascript:this.form.action='TroubleTicketsAllerte.do?command=ModifyListaDistribuzione&idLdd=<%=lista.getId() %>';submit();">
	</dhv:permission>
	<%} %>
	
	</th></tr>
	
	<tr class="containerBody"><td nowrap class="formLabel"> Data lista	</td>
	<td><%=toDateasString(lista.getData_lista()) %></td>
	</tr>
	<tr class="containerBody"><td nowrap class="formLabel"> Data chiusura	</td>
	<td><%=toDateasString(lista.getData_chiusura()) %></td>
	</tr>
	<tr class="containerBody"><td nowrap class="formLabel"> Nome fornitore	</td>
	<td><%=lista.getNome_fornitore() %></td>
	</tr>
	<tr class="containerBody"><td nowrap class="formLabel"> ASL Coinvolte	</td>
	<td><%=lista.getAsl_coinvolteAsString() %></td>
	</tr>
	
	</table>
	
	
	
<%@ include file="tabella_info_cu.jsp" %>
	
	

		<br/>				
<%}%>

<%} %>






	
	
	

</dhv:container>
</form>



<style>
body {
font-family:verdana;
font-size:15px;
}

a {color:#333; text-decoration:none}
a:hover {color:#ccc; text-decoration:none}

#mask {
  position:absolute;
  left:0;
  top:0;
  z-index:9000;
  background-color:#000;
  display:none;
}
  
#boxes .window {
  position:absolute;
  left:0;
  top:0;
  width:675px;
  height:358;
  display:none;
  z-index:9999;
  padding:20px;
}

#boxes #dialog # {
  width:675px; 
  height:380;
  padding:10px;
  background-color:#ffffff;
}

 #dialog4 {
  width:675px; 
  height:380;
  padding:10px;
  background-color:#ffffff;
}

#boxes #dialog1 {
  width:375px; 
  height:203px;
}

#dialog1 .d-header {
  background:url(images/login-header.png) no-repeat 0 0 transparent; 
  width:375px; 
  height:150px;
}

#dialog1 .d-header input {
  position:relative;
  top:60px;
  left:100px;
  border:3px solid #cccccc;
  height:22px;
  width:200px;
  font-size:15px;
  padding:5px;
  margin-top:4px;
}

#dialog1 .d-blank {
  float:left;
  background:url(images/login-blank.png) no-repeat 0 0 transparent; 
  width:267px; 
  height:53px;
}

#dialog1 .d-login {
  float:left;
  width:108px; 
  height:53px;
}

#boxes #dialog2 {
  background:url(images/notice.png) no-repeat 0 0 transparent; 
  width:326px; 
  height:229px;
  padding:50px 0 20px 25px;
}
</style>

<div id="boxes">

<%-- IL CAMPO SRC è DA AGGIUSTARE --%>
<div id="dialog4" class="window" width="600" height="380">
  <jsp:include page="view_data.jsp"/>
</div>

<iframe id="dialog" class="window" src="TroubleTicketsAllerte.do?command=DownloadAllegatoFRegione&id=<%=TicketDetails.getId() %>" width="600" height="380">
  
  <a href="#"class="close"/>Close it</a>
</iframe>


<!-- <iframe id="dialog4" class="window" src="TroubleTicketsAllerte.do?command=ViewLegenda&cuRegionali" width="600" height="380"> -->
  
<!--   <a href="#"class="close"/>Close it</a> -->
<!-- </iframe> -->

<!-- Mask to cover the whole screen -->
  <div id="mask"></div>

</div>





<%
if(User.getSiteId()>0)
{
%>
</body>
<%}%>

