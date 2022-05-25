<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.troubletickets.base.*,com.zeroio.iteam.base.*, org.aspcfs.modules.quotes.base.*,org.aspcfs.modules.base.EmailAddress" %>
<%@page import="org.aspcfs.modules.allerte.base.AslCoinvolte"%>
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
AslCoinvolte	ac3	= TicketDetails.getAslCoinvolta( User.getSiteId() );
	int cuEseguiti = 0;
	int cupianificati = 0;
	if (ac3 !=null )
	{
		cuEseguiti = ac3.getCu_eseguiti();
		cupianificati = ac3.getCu_pianificati();
	}
%>
<form name="details" action="TroubleTicketsAllerte.do?command=Modify&auto-populate=true" method="post">
<%-- Trails --%>
<input type="hidden" name = "risposta" id = "risposta">
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="TroubleTicketsAllerte.do"><dhv:label name="sanzioniss">Allerta</dhv:label></a> >
<%
	if (defectCheck != null && !"".equals(defectCheck.trim())) {
%>
  <a href="TroubleTicketDefects.do?command=View"><dhv:label name="tickets.defects.viewDefects">View Defects</dhv:label></a> >
  <a href="TroubleTicketDefects.do?command=Details&defectId=<%= defectCheck %>"><dhv:label name="tickets.defects.defectDetails">Defect Details</dhv:label></a> >
<%
  	} else {
  %>
<%
	if ("yes"
				.equals((String) session.getAttribute("searchTickets"))) {
%>
  <a href="TroubleTicketsAllerte.do?command=SearchTicketsForm"><dhv:label name="tickets.searchForm">Search Form</dhv:label></a> >
  <a href="TroubleTicketsAllerte.do?command=SearchTickets"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<%
  	} else {
  %> 
  <a href="TroubleTicketsAllerte.do?command=Home"><dhv:label name="sanzioni.visualizzaee">Visualizza Allerte</dhv:label></a> >
<%
   	}
   %>
<%
	}
%>


<dhv:label name="sanzioni.dettagliss">Scheda Allerte</dhv:label>
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
	<%--@ include file="ticket_header_include.jsp"--%>
	<%
		if (TicketDetails.isTrashed()) {
	%>
	<dhv:permission name="allerte-allerte-delete">
		<input type="button"
			value="Ripristina"
			onClick="javascript:this.form.action='TroubleTicketsAllerte.do?command=Restore&id=<%= TicketDetails.getId()%>';submit();">
	</dhv:permission>
	<%
		} else if (TicketDetails.getDataChiusura() != null) {
	%>
	
	
	
	<%
		} else {
	%>
	<dhv:permission name="allerte-allerte-edit">
		<input type="button"
			value="Modifica"
			onClick="javascript:this.form.action='TroubleTicketsAllerte.do?command=Modify&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	
	<dhv:permission name="allerte-allerte-edit">
		<input type="button"
			value="<dhv:label name="g">Ripianifica</dhv:label>"
			onClick="javascript:this.form.action='TroubleTicketsAllerte.do?command=Modify&ripianifica=true&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	
	<dhv:permission name="allerte-allerte-delete">
		<%
			if ("searchResults".equals(request
								.getParameter("return"))) {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('TroubleTicketsAllerte.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&return=searchResults&popup=true', 'Delete_ticket','520','200','yes','no');">
		<%
			} else {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('TroubleTicketsAllerte.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&popup=true', 'Delete_ticket','520','200','yes','no');">
		<%
			}
		%>
	</dhv:permission>
	
	<dhv:permission name="allerte-allerte-chiusura-view">
	
	
	<%if(User.getSiteId() == -1){ %>
	<%if( TicketDetails.isChiudibile( User.getSiteId() ) ){ 
		//TroubleTicketsAllerte.do?command=ChiudiAllerta&id=<%=TicketDetails.getId() %>
<%--Chiudi<%=(User.getSiteId() > 0) ? ( " per " + SiteIdList.getSelectedValue( User.getSiteId() ) ) : ( "" ) %>--%>
		<input 
			type="button" 
			value="Chiusura Allerta per Tutte le Asl"
		onclick="openchiusuraAllerta(<%=cuEseguiti %>, <%=TicketDetails.getId() %>,0,<%=User.getSiteId() %>,0,0)"/>
		 
		 
	<%}else{
		
		if(TicketDetails.getDataChiusura()==null){
		//TroubleTicketsAllerte.do?command=ChiudiAllerta&chiusuraUfficio=1&id=<%=TicketDetails.getId() %>
		
		<input 
			type="button" 
			value="Chiusura Forzata Allerta Per Tutte le Asl"
			onclick="openchiusuraAllerta(<%=cuEseguiti %>, <%=TicketDetails.getId() %>,1,<%=User.getSiteId() %>,0,0)"/>
			
		<%
		}
	}
	}else{

	%>
	<%if( TicketDetails.isChiudibile( User.getSiteId() ) ){ 
		//TroubleTicketsAllerte.do?command=ChiudiAllerta&id=<%=TicketDetails.getId() %>

		<input 
			type="button" 
			value="Chiusura Allerta e Invio Allegato F"
					
		onclick="openchiusuraAllerta(<%=cuEseguiti %>, <%=TicketDetails.getId() %>,0,<%=User.getSiteId() %>,0,<%=cupianificati %>)"/>
		 
	<%}else{
		
		if(TicketDetails.getAslCoinvolta(User.getSiteId()).getData_chiusura() ==null){
		//TroubleTicketsAllerte.do?command=ChiudiAllerta&chiusuraUfficio=1&id=<%=TicketDetails.getId() %>
		
		<input 
			type="button" 
			value="Chiusura Forzata Allerta e Invio Allegato F"
		onclick="openchiusuraAllerta(<%=cuEseguiti %>, <%=TicketDetails.getId() %>,1,<%=User.getSiteId() %>,<%=ac3.getCu_eseguiti()%>,<%=cupianificati %>)"/>
						
		
		
		
		<%
		}
	}
	
	} %>
	</dhv:permission>
	
	<%
		}
	%>
	

<%
if( User.getSiteId() > 0 ) {

%>

<dhv:permission name="allerte-allerte-chiusura-view">
<input type = "button" value = "Scarica Allegato F" onclick="javascript : document.details.action ='TroubleTicketsAllerte.do?command=DownloadAllegatoF&tipo_file=pdf&ticketid=<%=TicketDetails.getId() %>'; document.details.submit() ">
</dhv:permission>
&nbsp;&nbsp;
				<a  href="#dialog4" name = "modal" onclick="document.getElementById('dialog').src=''+document.getElementById('dialog').src+'&tipoAlimenti='+document.getElementById('tipoAlimenti').value+'&specie_alimenti='+document.getElementById('specie_alimenti').value">Legenda</a>
		

<%
}
else
{
	
		%>

		
		<dhv:permission name="allerte-allerte-chiusura-view">

				<a  href="#dialog" name = "modal" onclick="document.getElementById('dialog').src=''+document.getElementById('dialog').src+'&tipoAlimenti='+document.getElementById('tipoAlimenti').value+'&specie_alimenti='+document.getElementById('specie_alimenti').value">Scarica Allegato F</a>
		
		</dhv:permission>
		&nbsp;&nbsp;
				<a  href="#dialog4" name = "modal" onclick="document.getElementById('dialog').src=''+document.getElementById('dialog').src+'&tipoAlimenti='+document.getElementById('tipoAlimenti').value+'&specie_alimenti='+document.getElementById('specie_alimenti').value">Legenda</a>
		
		
		<%
	
}
%>

		


<%

	int size = ( (User.getSiteId() > 0) ? (1) : (SiteIdList.size()) );
%>


	<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr><%if(session.getAttribute("problemaInvioMail")!=null && session.getAttribute("problemaInvioMail").equals("si")){
%>
<p><font color="red">Attenzione! L'allerta è stata inserita/modificata correttamente ma non è stato possibile inviare i messaggi di PEC.</font></p>
<%} %>
	</tr>
		<tr>
			<th colspan="<%=(User.getSiteId()!=-1)?("2"):(size)  %>"><strong><dhv:label
				name="sanzioni.informationxx">Scheda Allerta</dhv:label></strong></th>
		</tr>
		
		
		<%if(ListaCommercializzazione.size() > 1) {%>
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.sitefff">Lista di Commercializzazione</dhv:label></td>
					<td colspan="<%=size %>"><%=ListaCommercializzazione.getSelectedValue(TicketDetails
										.getListaCommercializzazione())%>
					 <input type="hidden"
						name="siteId" value="<%=TicketDetails.getListaCommercializzazione()%>"></td>
		</tr>
        <%} %>
		<% if(TicketDetails.getDataChiusura() != null && TicketDetails.isChiusuraUfficio()) {%>
			<tr class="containerBody">
					<td nowrap class="formLabel">Esito accertamenti finali</td>
					<td colspan="<%=size %>"><%= "Allerta Chiusa in quanto Rientrata" %>
					</td>
				</tr>
		
		<%} %>
		
		
		
		<dhv:include name="" none="true">
			<dhv:evaluate if="<%= SiteIdList.size() > 1 %>">
				<tr class="containerBody">
					<td nowrap class="formLabel">A.S.L. Coinvolte</td>
					<td colspan="<%=size %>"><%= TicketDetails.getAsl_coinvolteAsString() %>
					 <input type="hidden"
						name="siteId" value="<%=TicketDetails.getSiteId()%>"></td>
				</tr>
			</dhv:evaluate>
			<dhv:evaluate if="<%= SiteIdList.size() <= 1 %>">
				<input type="hidden" name="siteId" id="siteId" value="-1" />
			</dhv:evaluate>
		</dhv:include>
	
	
  		<input type="hidden" name="id" id="id" value="<%=  TicketDetails.getId() %>" />
		<input type="hidden" name="orgId" id="orgId"
			value="<%=  TicketDetails.getOrgId() %>" />
 <%if(TicketDetails.getDataApertura() != null) {
 
 %>
		<tr class="containerBody">
			<td nowrap class="formLabel"><dhv:label
				name="sanzioni.data_richiestass">Data Apertura</dhv:label></td>
			<td colspan="<%=size %>">
			  <zeroio:tz timestamp="<%= TicketDetails.getDataApertura() %>" dateOnly="true" timeZone="<%= TicketDetails.getAssignedDateTimeZone() %>" showTimeZone="false" default="&nbsp;"/>
	
            </td>
		</tr>
 <%} %>
 
 <%if(TicketDetails.getDataChiusura()!=null) {%>
 
 <tr class="containerBody">
			<td nowrap class="formLabel"><dhv:label
				name="sanzioni.data_richiestass">Data Chiusura</dhv:label></td>
			<td colspan="<%=size %>">
			<zeroio:tz timestamp="<%= TicketDetails.getDataChiusura() %>" dateOnly="true" timeZone="<%= TicketDetails.getAssignedDateTimeZone() %>" showTimeZone="false" default="&nbsp;"/>
	
            </td>
		</tr>
 
 <%} %>
 
 <%if(TicketDetails.getIdAllerta() != null) {%>
 <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="">Identificativo Allerta</dhv:label>
    </td>
    <td colspan="<%=size %>">
         <%= TicketDetails.getIdAllerta() %>
    </td> 
    </tr>
 <%} %>
 
 


<tr class="containerBody">
      <td name="orig"  nowrap class="formLabel">
        <dhv:label name="">Comunitaria</dhv:label>
      </td>
     <td colspan="<%=size %>">
      <input type = "checkbox" disabled="disabled" <%if(TicketDetails.isFlagTipoAllerta()==true){%>checked="checked"<%}%> name = "flag_pubblicazione_allerte"/>
      </td>
      
      
 </tr>
 
 
 <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="">Unità di Misura per Quantita</dhv:label>
    </td>
    <td colspan="<%=size %>">
         <%=UnitaMisura.getSelectedValue(TicketDetails.getUnitaMisura()) %>
    </td> 
    </tr>
 
 

	    	
	    	<%String cu_pp="";
	    		
				AslCoinvolte	ac	= TicketDetails.getAslCoinvolta( User.getSiteId() );
		    	if( User.getSiteId() <= 0 )
		    	{
		    		
	    	%>
	    			<tr class="containerBody">
	    				<td  valign="top" class="formLabel">
	    					A.S.L.
	    				</td>
	    							<%
	    							for( int i = 1; i < size; i++)
	    							{
	    								if(SiteIdList.getIdFromLevel(i)!=-1){
	    								String asl = SiteIdList.getSelectedValue( SiteIdList.getIdFromLevel(i));
	    							%>
	    							
	    								<td>
	    								
	    									<%=toHtmlValue( asl ) %>
	    								</td>	
	    							<%
	    							}}
	    							
	    							%>
	    			</tr>
	    			<tr class="containerBody">
	    				<td  valign="top" class="formLabel">
	    					C.U. Pianificati dalla Regione
	    				</td>
	    				
	    							<%
	    							for( int i = 1; i < size; i++)
	    							{
	    								int id_asl = SiteIdList.getIdFromLevel( i );
	    								if(id_asl!=-1){
	    								AslCoinvolte temp = TicketDetails.getAslCoinvolta( id_asl );
	    							String	 cu_p = ( (temp == null) ? ( TicketDetails.ASL_NON_COINVOLTA ) : ( (temp.getControlliUfficialiRegionaliPianificati() > -1) ? (temp.getControlliUfficialiRegionaliPianificati()) : (TicketDetails.CU_NON_PIANIFICATI) ) ) + "";
	    							cu_pp=cu_p;
	    							%>
	    								<td>
	    						
	    									<%=toHtmlValue( cu_p ) %>
	    								</td>	
	    							<%
	    							}}
	    							%>
					</tr>
	    			
	    			<tr class="containerBody">
	    				<br/><br/><td  valign="top" class="formLabel">
	    					C.U. Pianificati da Asl
	    				</td>
	    				
	    							<%
	    							for( int i = 1; i < size; i++)
	    							{
	    								int id_asl = SiteIdList.getIdFromLevel( i );
	    								if(id_asl!=-1){
	    								AslCoinvolte temp = TicketDetails.getAslCoinvolta( id_asl );
	    								String cu_p = ( (temp == null) ? ( TicketDetails.ASL_NON_COINVOLTA ) : ( (temp.getCu_pianificati() > -1) ? (temp.getCu_pianificati()) : (TicketDetails.CU_NON_PIANIFICATI) ) ) + "";
	    							%>
	    								<td>
	    									<%=toHtmlValue( cu_p ) %>
	    								</td>	
	    							<%
	    							}}
	    							%>
					</tr>
					
					<tr class="containerBody">
	    				<td  valign="top" class="formLabel">
	    					C.U. Eseguiti (Chiusi)
	    				</td>
	    				
	    							<%
	    							int col1=0;
	    							for( int i = 1; i < size; i++)
	    							{
	    								col1++;
	    								int id_asl = SiteIdList.getIdFromLevel( i );
	    								if(id_asl!=-1){
	    								AslCoinvolte temp = TicketDetails.getAslCoinvolta( id_asl );
	    								String cu_e = ( (temp == null) ? ( TicketDetails.ASL_NON_COINVOLTA ) : ( temp.getCu_eseguiti()) ) + "";
	    							%>
	    								<td>
	    									<%=toHtmlValue( cu_e ) %>
	    								</td>	
	    							<%
	    							}}
	    							%>
 					</tr>
					
					
					<tr class="containerBody">
	    				<td  valign="top" class="formLabel">
	    					C.U. Residui da Eseguire
	    				</td>
	    				
	    							<%
	    							int col=0;
	    							for( int i = 1; i < size; i++)
	    							{
	    								col++;
	    								int id_asl = SiteIdList.getIdFromLevel( i );
	    								if(id_asl!=-1){
	    								AslCoinvolte temp = TicketDetails.getAslCoinvolta( id_asl );
	    								String cu_e = ( (temp == null) ? ( TicketDetails.ASL_NON_COINVOLTA ) : ( temp.getCUResidui() ) ) + "";
	    							%>
	    								<td>
	    									<%=toHtmlValue( cu_e ) %>
	    								</td>	
	    							<%
	    							}}
	    							%>
 					</tr>
 					
 					<tr class="containerBody">
	    				<td  valign="top" class="formLabel">
	    					C.U. Eseguiti (Aperti)
	    				</td>
	    				
	    							<%
	    							col=0;
	    							for( int i = 1; i < size; i++)
	    							{
	    								col++;
	    								int id_asl = SiteIdList.getIdFromLevel( i );
	    								if(id_asl!=-1){
	    								AslCoinvolte temp = TicketDetails.getAslCoinvolta( id_asl );
	    								String cu_e = ( (temp == null) ? ( TicketDetails.ASL_NON_COINVOLTA ) : ( temp.getNumCuEseguiti_aperti() ) ) + "";
	    							%>
	    								<td>
	    									<%=toHtmlValue( cu_e ) %>
	    								</td>	
	    							<%
	    							}}
	    							%>
 					</tr>
 					
					
					<tr class="containerBody">
	    				<td  valign="top" class="formLabel">
	    					Imprese e stabilimenti coinvolti
	    				</td>
	    				
	    				
	    							<%
	    							for( int i = 1; i < size; i++)
	    							{
	    								int id_asl = SiteIdList.getIdFromLevel( i );
	    								if(id_asl!=-1){
	    								ImpreseCoinvolte temp = TicketDetails.getImpresaCoinvolta( id_asl );
	    								
	    								if(id_asl==16)
	    								{
	    								%>
	    								<td>
	    								<%
	    									out.println(TicketDetails.ASL_NON_COINVOLTA);
	    									
	    									
	    									%>
	    								</td>
	    								
	    								<%
	    								}
	    								else{
	    								if(temp==null)
	    									{
	    									%><td>
	    									<%
	    									out.println(TicketDetails.ASL_NON_COINVOLTA);
	    									
	    									
	    									%>
	    									</td>
	    								<%} 
	    								else{
	    								%>
	    								
	    								<td>
	    							<%
	    							
	    							int ii =0;
	    							for(String s : temp.getImpreseCoinvolte())
	    							{
	    								String indirizzo = temp.getIndirizziImpreseCoinvolte().get(ii);
	    								if(!s.equals(""))
	    								out.println("<b>Ragione Sociale :</b>"+s+" . <b>Indirizzo :</b> "+indirizzo+"<br>");
	    								ii++;
	    							}
	    							
	    							%>
	    						
	    									
	    								</td>	
	    							<%
	    							}}}}
	    							%>
					</tr>
					
					<tr class="containerBody">
	    				<br/><br/><td  valign="top" class="formLabel">
	    					Note su Variazione dei C.U. Pianificati da Asl
	    				</td>
	    				
	    							<%
	    							
	    							for( int i = 1; i < size; i++)
	    							{
	    								int id_asl = SiteIdList.getIdFromLevel(i);
	    								AslCoinvolte temp = TicketDetails.getAslCoinvolta( id_asl );
	    								String motivazione="";
	    								if(temp==null || temp.equals(""))
	    									motivazione="";
	    									else
	    									{
	    										
	    									
	    										motivazione=temp.getMotivazione();
	    										
	    									}
	    										%>
	    								<td>
	    									<%=toHtml(motivazione)%>
	    								</td>	
	    							<%
	    							}
	    							%>
					</tr>
					
					
					<tr class="containerBody">
	    				<br/><br/><td  valign="top" class="formLabel">
	    					Descrizione per Asl Fuori Regione
	    				</td>
	    				
	    							<%
	    							
	    							for( int i = 1; i < size; i++)
	    							{
	    								
	    								int id_asl = SiteIdList.getIdFromLevel(i);
	    								String descrizione="";
	    								if(id_asl == 16){
	    								AslCoinvolte temp = TicketDetails.getAslCoinvolta( id_asl );
	    								
	    								if(temp==null || temp.equals(""))
	    									descrizione="";
	    									else
	    									{
	    										
	    									descrizione = temp.getNoteFuoriRegione();
	    										
	    										
	    									}}
	    										%>
	    								<td>
	    									<%=toHtml(descrizione)%>
	    								</td>	
	    							<%
	    							}
	    							%>
					</tr>
					
					
 					
 					
 					<tr class="containerBody">
	    				<td  valign="top" class="formLabel">
	    					Stato Allerta
	    				</td>
	    				
	    							<%
	    							for( int i = 1; i < size; i++)
	    							{
	    								int id_asl = SiteIdList.getIdFromLevel( i );
	    							%>
	    								<td>
	    									<%=( TicketDetails.getStato( id_asl ) ) %>
	    								</td>	
	    							<%
	    							}
	    							%>
	    			
	    			
	    			<tr class="containerBody">
	    				<td  valign="top" class="formLabel">
	    					Data Ricezione Allegato F
	    				</td>
	    				
	    							<%
	    							for( int i = 1; i < size; i++)
	    							{
	    								int id_asl = SiteIdList.getIdFromLevel( i );
	    								AslCoinvolte ac1 = TicketDetails.getAslCoinvolta( id_asl );
	    							%>
	    								<td >
	    								<%if (ac1!=null)
	    									{
	    									if(ac1.isStato_allegatof()==true )
	    									{
	    										if(ac1.getData_invio_allegato()!=null)
	    											out.print(""+(new SimpleDateFormat("dd/MM/yyyy:hh:mm").format(new Date (ac1.getData_invio_allegato().getTime()))));
	    									}
	    									else
	    									{
	    										
	    										if(ac1.getData_chiusura()!=null)
	    										{
	    											out.print(""+(new SimpleDateFormat("dd/MM/yyyy:hh:mm").format(new Date (ac1.getData_chiusura().getTime()))));
	    										}
	    										else
	    										{
	    											out.print("Non Ricevuto");
	    										}
	    										
	    									}
	    								%>
	    								
	    									
	    									
	    								<%}else
	    									{
	    									out.print(Ticket.ASL_NON_COINVOLTA);
	    									}%>
					    
										</td> 
								<%} %>
				</tr>	
	
			
	    			
	    			<tr class="containerBody">
	    				<td  valign="top" class="formLabel">
	    					Motivazione Mancato completamento controlli
	    				</td>
	    				
	    							<%
	    							
	    							for( int i = 1; i < size; i++)
	    							{
	    								col++;
	    								int id_asl = SiteIdList.getIdFromLevel( i );
	    								if(id_asl!=-1){
	    								AslCoinvolte temp = TicketDetails.getAslCoinvolta( id_asl );
	    								String cu_e = ( (temp == null) ? ( TicketDetails.ASL_NON_COINVOLTA ) : ( temp.getMotivo_chiusura() ) ) + "";
	    							%>
	    								<td>
	    									<%=toHtml( cu_e ) %>
	    								</td>	
	    							<%
	    							}}
	    							%>
 					</tr>
	    			
	    	<%
		    	}
		    	else
		    	{
	    	%>
	    	  <tr class="containerBody">
		    <td valign="top" class="formLabel">
				C.U. Pianificati dalla Regione
		    </td>
		    <td colspan="<%=size %>">
	    	<%=ac.getControlliUfficialiRegionaliPianificati() %>
	    	<input type="hidden" id="cuRegione" name="cuRegione" value="<%=ac.getControlliUfficialiRegionaliPianificati() %>">
	    	</td>
	    	
	    	</tr>
	    	
	    	
		  <tr class="containerBody">
		    <td valign="top" class="formLabel">
				C.U. Pianificati <%=SiteIdList.getValueFromId( User.getSiteId() ) %>
		    </td>
		    <td colspan="<%=size %>">
			    		
			    		
			    		<%
			    			String cup = ( (ac == null) ? ( TicketDetails.ASL_NON_COINVOLTA ) : ( (ac.getCu_pianificati() > -1) ? (ac.getCu_pianificati()) : (TicketDetails.CU_NON_PIANIFICATI) ) ) + "";
			    		
			    		
			    		%>
					
					<dhv:permission name="allerte-allerte-cu-view">
						<%
							if( (ac != null) && (ac.getCu_pianificati() < 0) )
							{
								cup = "";
			    		%>
			    		<table class="noborder">
			    		<tr>
			    		<td>
			    		
			    			<input size="5" maxlength="4" type="text" onchange="mostraMotivo()" id="cu_pianificati" name="cu"  />
			    			<input type="hidden" name="id_allerta"  value="<%=TicketDetails.getId() %>" />
			    		</td>
			    		<td id="descr" style="display:none">
			    	
			    		
			    		
			    		<textarea rows="5" name="motivazione" cols="30" id="motivazione1"></textarea>
			    	</td>
			    		<td>	
			    		<% if(TicketDetails.getAslCoinvolta(User.getSiteId() ).getData_chiusura()==null){%>
			    		<input type="button"
								value="Assegna"
								onClick="javascript:if(checkInt(this.form)){ this.form.action='TroubleTicketsAllerte.do?command=AssegnaNumeroCU'; submit(); }">
							<%} %>
	</td></tr></table>
			    		<%
							}else{
								%>
								<table class="noborder">
			    		<tr>
			    		<td>
								<input size="5" maxlength="4" id="cu_pianificati" onchange="mostraMotivo()" type="text" value="<%=cup %>" name="cu" />
				    			<input type="hidden" name="id_allerta" value="<%=TicketDetails.getId() %>" />
				    			</td>
			    		<td id="descr" style="display:none">
				    			&nbsp;&nbsp;
				    		<textarea rows="5" name="motivazione" value="<%=ac.getMotivazione() %>" cols="30"><%=ac.getMotivazione() %></textarea>
			    		</td>
			    		<td>
				    				<% if(TicketDetails.getAslCoinvolta(User.getSiteId() ).getData_chiusura()==null){%>
				    			<input type="button"
									value="Modifica"
									onClick="javascript:if(checkInt(this.form)){ this.form.action='TroubleTicketsAllerte.do?command=AssegnaNumeroCU'; submit(); }">
							<%} %>
		</td></tr></table>
								<% 
								
								
								
								
							}
			    		%>
					</dhv:permission>
			    		
			    	
			    </td> 
		    </tr>
 
			<%if( (ac != null) && (ac.getCu_pianificati() > -1) ){%>
				<tr class="containerBody">
					<td valign="top" class="formLabel">
						C.U. Residui da Eseguire <%=SiteIdList.getValueFromId( User.getSiteId() ) %>
					</td>
					<td colspan="<%=size %>">
					     <%= toHtmlValue( ac.getCUResidui() ) %>
					</td> 
				</tr>
			<% }%>
			
			<%if( (ac != null) ){%>
				<tr class="containerBody">
					<td valign="top" class="formLabel">
						C.U. Eseguiti (Aperti) <%=SiteIdList.getValueFromId( User.getSiteId() ) %>
					</td>
					<td colspan="<%=size %>">
					     <%= toHtmlValue(""+ ac.getNumCuEseguiti_aperti() ) %>
					</td> 
				</tr>
			<% }%>
	
			<tr class="containerBody">
				<td valign="top" class="formLabel">
					Stato Allerta <%=SiteIdList.getValueFromId( User.getSiteId() ) %>
				</td>
				<td colspan="<%=size %>">
				     <%= ( TicketDetails.getStato( User.getSiteId() ) ) %>
				</td> 
			</tr>
			
			<%if(ac.getMotivo_chiusura()!=null  && ! ac.getMotivo_chiusura().equals("null")&& ! ac.getMotivo_chiusura().equals("")){ %>
				<tr class="containerBody">
					<td valign="top" class="formLabel">
						Motivo Mancato Completamento Controlli
					</td>
					<td colspan="<%=size %>">
					     <%= toHtmlValue( ac.getMotivo_chiusura()) %>
					</td> 
				</tr>
				
				<%} %>
			
				<tr class="containerBody">
					<td valign="top" class="formLabel">
						Stato Allegato F
					</td>
					<td colspan="<%=size %>">
					    <%if(ac.isStato_allegatof()== true  ){ 
					   		 if (ac.getData_invio_allegato()!=null)
					   		 {
					    %>
					    Inviato in Data : <%=new SimpleDateFormat("dd/MM/yyyy:hh:mm").format(new Date (ac.getData_invio_allegato().getTime())) %>
					    <%
					   	}
					    }
					    else
					    {
					    	if (ac.getData_chiusura()!=null)
					   		 {
					    		%>
					     Inviato in Data : <%=new SimpleDateFormat("dd/MM/yyyy:hh:mm").format(new Date (ac.getData_chiusura().getTime())) %>		
					    		<%
					   		 }
					    	else
					    	{
					    %>
					    Non Inviato
					    <%}} %>
					</td> 
				</tr>	
	
			<%
				
		    	}
			%>
 

		 <%if(TicketDetails.getProgressivoAllerta() > -1) { %>
       <tr class="containerBody">
			<td nowrap class="formLabel"><dhv:label
				name="sanzioni.data_richiestass">Progressivo Allerta</dhv:label></td>
			<td colspan="<%=size %>">
				<%= toHtmlValue(TicketDetails.getProgressivoAllerta()) %>
            </td>
		</tr> 
       <%} %>

	
		<%if(Origine.size() > 1) {%>
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.sitefff">Origine</dhv:label></td>
					<td colspan="<%=size %>"><%=Origine.getSelectedValue(TicketDetails
										.getOrigine())%>
					 <input type="hidden"
						name="siteId" value="<%=TicketDetails.getOrigine()%>">
						&nbsp;&nbsp;&nbsp;
						<%if(TicketDetails.getOrigineAllerta()>-1)
						{
							if(TicketDetails.getOrigine()==1 || TicketDetails.getOrigine()==2){
						%>
						
						Origine da Asl: <%=SiteIdList.getValueFromId(TicketDetails.getOrigineAllerta()) %>
						<%}else{
						
						%>
							Origine da Regione: <%=Regioni.getValueFromId(TicketDetails.getOrigineAllerta()) %>
						
						<%} 
						
						}
						%>
						
						</td>
		</tr>
        <%} %>
 <%if(TicketDetails.isFlag_produzione()==true){%>


<tr class="containerBody">
      <td name="orig"  nowrap class="formLabel">
        <dhv:label name="">Pubblicazione</dhv:label>
      </td>
     <td colspan="<%=size %>">
      <input type = "checkbox" disabled="disabled" <%if(TicketDetails.isFlag_produzione()==true){%>checked="checked"<%} %> name = "flag_pubblicazione_allerte" onclick="abilitapubblicazione(this)"/>
      </td>
      
      
 </tr>
  <tr class="containerBody">
      <td name="orig"  nowrap class="formLabel">
        <dhv:label name="">Data Inizio pubblicazione</dhv:label>
      </td>
    <td colspan="<%=size %>">
      
      <%=TicketDetails.getData_inizio_pubblicazioneString() %>
      </td>
      
      
 </tr>
 <tr class="containerBody">
      <td name="orig"  nowrap class="formLabel">
        <dhv:label name="">Data Fine pubblicazione</dhv:label>
      </td>
    <td colspan="<%=size %>">
      
      <%=TicketDetails.getData_fine_pubblicazioneString() %>
      </td>
      
      
 </tr>
 
 <tr class="containerBody">
      <td name="orig"  nowrap class="formLabel">
        <dhv:label name="">Tipo di Rischio</dhv:label>
      </td>
   
      
      <td colspan="<%=size %>">  
       <textarea rows="5" cols="30" name = "tipo_rischio_allerte" readonly="readonly"><%=TicketDetails.getTipo_rischio() %></textarea>
      </td>
      
      
 </tr>
 
 <tr class="containerBody">
      <td name="orig"  nowrap class="formLabel">
        <dhv:label name="">Provvedimenti/Esito Accertamenti</dhv:label>
      </td>
    
      
      <td colspan="<%=size %>">  
     <textarea rows="5" cols="30" name = "provvedimenti_esito_allerte" readonly="readonly"><%=TicketDetails.getProvvedimento_esito() %></textarea>
      </td>
      
      
 </tr>
 


          
   <%} %>
	
	<%ArrayList<Analita> tipi_a= TicketDetails.getTipi_Campioni(); %>
	
	<% if(tipi_a.isEmpty()) {	%>   
	<tr class="containerBody">
	<td nowrap class="formLabel"><dhv:label
		name="stabilimenti.sitefff">Azione non conforme Per</dhv:label></td>
					 <td colspan="<%=size %>">
    <table class="noborder">
    <tr>
    <td>
      <%="<b>- Tipo di Esame:</b> "+TipoCampione.getSelectedValue(TicketDetails
    		  .getTipoCampione())%>
					<input type="hidden" name="provvedimenti"
						value="<%=TicketDetails.getTipoCampione() %>">
						
						
					
						
						
						<%
						HashMap<Integer,String> tipi= TicketDetails.getTipiCampioni();
						
						if(TicketDetails.getTipoCampione()==5){
							
							Iterator<Integer> set=tipi.keySet().iterator();
							int kiave=0;
							out.print("<br><b> - Ricerca di: </b>");
							while(set.hasNext()){
							 kiave=set.next();
								out.print(tipi.get(kiave)+", ");
						
							}
						
							HashMap<Integer,String> tipiChimici=TicketDetails.getTipiChimiciSelezionati();
							Iterator<Integer> set1=tipiChimici.keySet().iterator();
							int kiave1=0;
							out.print("<br><b>- Per:</b> ");
							while(set1.hasNext()){
								 kiave1=set1.next();
								out.print(""+tipiChimici.get(kiave1)+",");
						
							}
							
						}else{
						
						Iterator<Integer> set=tipi.keySet().iterator();
						while(set.hasNext()){
							int kiave=set.next();
							out.print("<br><b> - Ricerca di:</b> "+tipi.get(kiave)+",");
							
							}
							}
						
						%></br>
						<%="<b>- Descrizione: </b>"+ TicketDetails.getNoteAnalisi() %>
						
						
						
						</td>
					</tr>
			</table>
    </td>
  </tr>
		
	<% } else { %>
		 <tr class="containerBody">
		    <td valign="top" class="formLabel">
		      <dhv:label name="sanzioni.note">Azione non conforme per</dhv:label>
		    </td>
		    <td>
		    <table class="noborder">
		    <%	    
	
						int i=0 ;
						for(Analita a : tipi_a)
						{
							i++ ;
							int chiave = a.getIdAnalita();
							String descrizione = a.getDescrizione();
							out.print("<tr><td> "+i+") "+descrizione+"</td>");
						
						}
						%>
			<input type="hidden" id="numeroAnaliti" name="numeroAnaliti" value="<%=i%>">
						
			<tr><td><% if(TicketDetails.getNoteAnalisi() != null && !TicketDetails.getNoteAnalisi().equals("")) { %>
				<%="Note : "+TicketDetails.getNoteAnalisi() %>
				<% } else { %>
					<%="Note : N.D"%>
					<% } %>
			</td></tr>
		</table>
    </td>
  </tr>
  <% } %>
        

		<dhv:evaluate if="<%= hasText(TicketDetails.getProblem()) %>">
			<tr class="containerBody">
				<td class="formLabel" valign="top"><dhv:label
					name="sanzioni.note">Note</dhv:label></td>
				<td colspan="<%=size %>" valign="top">
				<%
					//Show audio files so that they can be streamed
							Iterator files = TicketDetails.getFiles().iterator();
							while (files.hasNext()) {
								FileItem thisFile = (FileItem) files.next();
								if (".wav".equalsIgnoreCase(thisFile.getExtension())) {
				%> <a
					href="TroubleTicketsDocuments_asl.do?command=Download&stream=true&tId=<%= TicketDetails.getId() %>&fid=<%= thisFile.getId() %>"><img
					src="images/file-audio.gif" border="0" align="absbottom"><dhv:label
					name="tickets.playAudioMessage">Play Audio Message</dhv:label></a><br />
				<%
					}
							}
				%> <%=toHtml(TicketDetails.getProblem())%> <input type="hidden"
					name="problem" value="<%=toHtml(TicketDetails.getProblem())%>">
				</td>
			</tr>
		</dhv:evaluate>
		<dhv:evaluate if="<%= hasText(TicketDetails.getCause()) %>">
			<tr class="containerBody">
				<td valign="top" class="formLabel"><dhv:label
					name="sanzioni.importo">Importo da Pagare (euro)</dhv:label></td>
				<td colspan="<%=size %>"><%=toHtmlValue(TicketDetails.getCause())%> <input
					type="hidden" name="importo" id="orgId"
					value="<%=  TicketDetails.getCause() %>" /></td>
			</tr>
		</dhv:evaluate>
		<dhv:evaluate if="<%= hasText(TicketDetails.getSolution()) %>">
			<tr class="containerBody">
				<td valign="top" class="formLabel"><dhv:label
					name="sanzioni.azioni">Ulteriori Azioni</dhv:label></td>
				<td colspan="<%=size %>"><%=toString(TicketDetails.getSolution())%><%-- </textarea>--%></td>
			</tr>
		</dhv:evaluate>
		
		<dhv:include name="" none="true">
			<dhv:evaluate
				if="<%= TicketDetails.getSanzioniAmministrative() > -1 %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label name="">Sanzioni Amministrative</dhv:label>
					</td>
					<td colspan="<%=size %>"><%=SanzioniAmministrative
										.getSelectedValue(TicketDetails
												.getSanzioniAmministrative())%>
					<dhv:evaluate if="<%= TicketDetails.getSanzioniAmministrative() == 9%>">
						&nbsp; Descrizione:&nbsp;<%= TicketDetails.getDescrizione2()%>
					</dhv:evaluate>
					<input type="hidden" name="sanzioniamm"
						value="<%=TicketDetails.getSanzioniAmministrative() %>">
					</td>
				</tr>
			</dhv:evaluate>
		</dhv:include>
		<dhv:include name="" none="true">
			<dhv:evaluate if="<%= TicketDetails.getSanzioniPenali() > -1 %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label name="">Sanzioni Penali</dhv:label>
					</td>
					<td colspan="<%=size %>"><%=SanzioniPenali.getSelectedValue(TicketDetails
										.getSanzioniPenali())%>
					<dhv:evaluate if="<%= TicketDetails.getSanzioniPenali() == 6 %>">
										&nbsp; Descrizione:&nbsp;<%= TicketDetails.getDescrizione3()%>
					</dhv:evaluate>
					<input type="hidden" name="sanzionipen"
						value="<%=TicketDetails.getSanzioniPenali() %>"></td>
				</tr>
			</dhv:evaluate>
		</dhv:include>
		<dhv:include name="" none="true">
			<dhv:evaluate if="<%= (TicketDetails.getTipoSequestro() != false ) || (TicketDetails.getTipoSequestroDue() != false) || (TicketDetails.getTipoSequestroTre() != false) || (TicketDetails.getTipoSequestroQuattro() != false) %>">
		     <tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label name="">Sequestri</dhv:label>
					</td>
					<td colspan="<%=size %>">
					 <% if(TicketDetails.getTipoSequestro() == true) {%>
					  Alimenti <input type="checkbox" checked disabled> <%} %>
					  <% if(TicketDetails.getTipoSequestroDue() == true) {%>
					  Attrezzature <input type="checkbox" checked disabled> <%} %>
					  <% if(TicketDetails.getTipoSequestroTre() == true) {%>
					  Locale <input type="checkbox" checked disabled> <%} %>
					  <% if(TicketDetails.getTipoSequestroQuattro() == true) {%>
					  Stabilimento <input type="checkbox" checked disabled> <%} %>
					</td>
				</tr>
			</dhv:evaluate>
		</dhv:include>			  
		
		
		<dhv:include name="ticket.defect" none="false">
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="tickets.defects.defect">Defect</dhv:label>
				</td>
				<td colspan="<%=size %>"><%=toHtml(defect.getTitle())%> <dhv:evaluate
					if="<%= hasText(defect.getTitle()) && defect.isDisabled() %>">(X)</dhv:evaluate>
				</td>
			</tr>
		</dhv:include>


		
		<tr class="containerBody">
			<td class="formLabel"><dhv:label
				name="accounts.accounts_calls_list.Entered">Entered</dhv:label></td>
			<td colspan="<%=size %>"><dhv:username id="<%= TicketDetails.getEnteredBy() %>" /> <zeroio:tz
				timestamp="<%= TicketDetails.getEntered() %>"
				timeZone="<%= User.getTimeZone() %>" showTimeZone="false" /></td>
		</tr>
		<tr class="containerBody">
			<td class="formLabel"><dhv:label
				name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label>
			</td>
			<td colspan="<%=size %>"><dhv:username id="<%= TicketDetails.getModifiedBy() %>" /> <zeroio:tz
				timestamp="<%= TicketDetails.getModified() %>"
				timeZone="<%= User.getTimeZone() %>" showTimeZone="false" /></td>
		</tr>
		<%if(TicketDetails.getResolutionDate() != null) { %>
		<tr class="containerBody">
			<td class="formLabel"><dhv:label
				name="">Data definitiva di trasmissione dati</dhv:label>
			</td>
			<td colspan="<%=size %>"><dhv:username id="<%= TicketDetails.getModifiedBy()%>" /> <zeroio:tz
				timestamp="<%= TicketDetails.getResolutionDate() %>"
				timeZone="<%= User.getTimeZone() %>" showTimeZone="false" /></td>
		</tr>
		<% } %>
	</table>
	
	<br><br>
	
	
	<table cellpadding="4" cellspacing="0" width="100%" class="details">
	
	<tr>
				<th colspan="2"><strong><dhv:label name="">Oggetto della Allerta</dhv:label></strong>
				</th>
			</tr>
	
	<tr class="containerBody">
      <td id="" nowrap class="formLabel">
        <dhv:label name="">Descrizione Breve</dhv:label>
      </td>
  <td>
  <%=toHtml(TicketDetails.getDescrizioneBreve()) %>
   </td>
	</tr>
	
	
	<% HashMap<Integer,String> matrici= TicketDetails.getMatrici();
		String tipoAlimenti = "" ;
	    String specie_alimenti = "" ;
		if(matrici.isEmpty()) {
	%>   
		<%@ include file="../tipi_alimenti_details.jsp" %>
		
	<% } else { %>
		 <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzioni.note">Matrice</dhv:label>
    </td>
    <td>
    <table class="noborder">
    
						<%
						
						Iterator<Integer> itMatrici = matrici.keySet().iterator();
						int i = 0 ;
						while(itMatrici.hasNext())
						{
							i++ ;
							int chiave = itMatrici.next();
							String descrizione = TicketDetails.getMatrici().get(chiave);
							out.print("<tr><td> "+i+") "+descrizione+"</td></tr>");
							tipoAlimenti += "Matrice ";
							specie_alimenti += descrizione;
						}
						%>
						<% if(i==0){%>
							N.D
						<% } %>
			<tr>
			<td>
				<% if(TicketDetails.getNoteAlimenti()!=null && ! "".equals(TicketDetails.getNoteAlimenti()) ){%>
						NOTE : <%=TicketDetails.getNoteAlimenti() %> 
				<% } else { %>
					<b>Note: </b>N.D
				<% } %>
			</td></tr>
		</table>
</td></tr>
	<% } %>
	
	<input type = "hidden" name = "tipoAlimenti" id = "tipoAlimenti" value = "<%=tipoAlimenti %>">
	<input type = "hidden" name = "specie_alimenti" id = "specie_alimenti" value = "<%=specie_alimenti %>">
       
       <tr class="containerBody">
    <td valign="top" class="formLabel">
      Denominazione Prodotto
    </td>
    <td>    
    <%=toHtml(TicketDetails.getDenominazione_prodotto()) %>
    </td>
    
    </tr>
    <tr class="containerBody">
    <td valign="top" class="formLabel">
     Numero del Lotto
    </td>
    <td>    
    <%=toHtml(TicketDetails.getNumero_lotto()) %>
    </td>
    
    </tr>
    <tr class="containerBody">
    <td valign="top" class="formLabel">
      Fabbricante o Produttore
    </td>
    <td>    
    <%=toHtml(TicketDetails.getFabbricante_produttore()) %>
    </td>
    
    </tr>
    <%if (TicketDetails.getData_scadenza_allerta()!=null)
    	{
    	String data_scadenza = "" ;
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	data_scadenza = sdf.format((new java.util.Date(TicketDetails.getData_scadenza_allerta().getTime())));
    	%>
    <tr class="containerBody">
    <td valign="top" class="formLabel">
      Data Scadenza/termine minimo di conservazione
    </td>
    <td>    
       <%= data_scadenza %>
    </td>
    
    </tr>
   <%} %>
	
	</table>
	
	
	
	
	<br />
&nbsp;
<br />
	<%
		if (TicketDetails.isTrashed()) {
		
	%>
	<dhv:permission name="allerte-allerte-delete">
		<input type="button"
			value="Ripristina"
			onClick="javascript:this.form.action='TroubleTicketsAllerte.do?command=Restore&id=<%= TicketDetails.getId()%>';submit();">
	</dhv:permission>
	<%
		} else if (TicketDetails.getDataChiusura() != null) {
	%>
	

	
	<%
		} else {
	%>
	<dhv:permission name="allerte-allerte-edit">
		<input type="button"
			value="Modifica"
			onClick="javascript:this.form.action='TroubleTicketsAllerte.do?command=Modify&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	
	<dhv:permission name="allerte-allerte-delete">
		<%
			if ("searchResults".equals(request
								.getParameter("return"))) {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('TroubleTicketsAllerte.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&return=searchResults&popup=true', 'Delete_ticket','520','200','yes','no');">
		<%
			} else {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('TroubleTicketsAllerte.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&popup=true', 'Delete_ticket','520','200','yes','no');">
		<%
			}
		%>
	</dhv:permission>
	
		<dhv:permission name="allerte-allerte-chiusura-view">
		
		<%if(User.getSiteId() == -1){ %>
		<%if( TicketDetails.isChiudibile( User.getSiteId() ) ){ 
		//TroubleTicketsAllerte.do?command=ChiudiAllerta&id=<%=TicketDetails.getId() %>

		<input 
			type="button" 
			value="Chiusura Allerta Per Tutte Le Asl"
		onclick="openchiusuraAllerta(<%=cuEseguiti %>, <%=TicketDetails.getId() %>,0,<%=User.getSiteId() %>)"/>
		 
		 
	<%}else{
		if(TicketDetails.getDataChiusura()==null){
		//TroubleTicketsAllerte.do?command=ChiudiAllerta&chiusuraUfficio=1&id=<%=TicketDetails.getId() %>
		
		<input 
			type="button" 
			value="Chiusura Forzata Per Tutte Le Asl"
		onclick="openchiusuraAllerta(<%=cuEseguiti %>, <%=TicketDetails.getId() %>,1,<%=User.getSiteId() %>)"/>
		
		
		<%
		
	}}
		}else{
	
	%>
	
	<%if( TicketDetails.isChiudibile( User.getSiteId() ) ){ 
		//TroubleTicketsAllerte.do?command=ChiudiAllerta&id=<%=TicketDetails.getId() %>

		<input 
			type="button" 
			value="Chiusura Allerta e Invio Allegato F"
			onclick="openchiusuraAllerta(<%=cuEseguiti %>, <%=TicketDetails.getId() %>,0,<%=User.getSiteId() %>)"/>
		 
		 
	<%}else{
		
		if(TicketDetails.getAslCoinvolta(User.getSiteId()).getData_chiusura() ==null){
		//TroubleTicketsAllerte.do?command=ChiudiAllerta&chiusuraUfficio=1&id=<%=TicketDetails.getId() %>
		
		<input 
			type="button" 
			value="Chiusura Forzata Allerta e Invio Allegato F"
			onclick="openchiusuraAllerta(<%=cuEseguiti %>, <%=TicketDetails.getId() %>,1,<%=User.getSiteId() %>)"/>
		
		
		<%
		}
	}
	
	} %>
	</dhv:permission>
	
	<%
		}
	%>
</dhv:container>
</form>
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


<iframe id="dialog4" class="window" src="TroubleTicketsAllerte.do?command=ViewLegenda&cuRegionali" width="600" height="380">
  
  <a href="#"class="close"/>Close it</a>
</iframe>

<!-- Mask to cover the whole screen -->
  <div id="mask"></div>

</div>





<%
if(User.getSiteId()>0)
{
%>
</body>
<%}%>



