<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: accounts_details.jsp 19045 2007-02-07 18:06:22Z matt $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.trasportoanimali.base.*,org.aspcfs.modules.contacts.base.*, org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="IstatList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SpecieA" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CategoriaTrasportata" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="statoLab" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="role" class="org.aspcfs.modules.admin.base.Role" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.trasportoanimali.base.Organization" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoLocale" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SICCodeList" class="org.aspcfs.modules.admin.base.SICCodeList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="refreshUrl" class="java.lang.String" scope="request"/>

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

<%@ include file="../initPage.jsp" %>

<link rel="stylesheet" type="text/css" href="css/jmesa.css"></link>
		
		<script type="text/javascript" src="javascript/jquery.bgiframe.pack.js"></script>
		<script type="text/javascript" src="javascript/jquery.jmesa.js"></script>
		<script type="text/javascript" src="javascript/jmesa.js"></script>
		<script type="text/javascript" src="javascript/gestioneStatoTrasportoAnimali.js"></script>
<script>
function chiamaActionVeicoli(stringa1,stringa2){
	var scroll = document.body.scrollTop;
	location.href=stringa1+scroll+stringa2;
}

</script>	
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<%if (refreshUrl!=null && !"".equals(refreshUrl)){ %>
<script language="JavaScript" TYPE="text/javascript">
parent.opener.window.location.href='<%=refreshUrl%><%= request.getAttribute("actionError") != null ? "&actionError=" + request.getAttribute("actionError") :""%>';
</script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>



<%}%>

<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="TrasportoAnimali.do"><dhv:label name="">Trasporto animali</dhv:label></a> > 
<% if (request.getParameter("return") == null) { %>
<a href="TrasportoAnimali.do?command=Search"><dhv:label name="requestor.SearchResults">Search Results</dhv:label></a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<a href="TrasportoAnimali.do?command=Dashboard">Cruscotto</a> >
<%}%>

<dhv:label name="trasportoanimali.details">Scheda Richiesta Autorizzazione Tipo 2</dhv:label>

</td>
</tr>
</table>
<% %>

<%-- End Trails --%>
</dhv:evaluate>
    
<br>
<br>
<%@ include file="../../controlliufficiali/diffida_list.jsp" %>
       <script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>
	
	<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Stampa Scheda" value="Stampa Scheda"		onClick="openRichiestaPDF2('<%= OrgDetails.getId() %>', '-1', '-1', '-1', '47');">

  <table width="100%" border="0">
    <tr>
      <td nowrap align="right">
      	<% if (!OrgDetails.getAccountNumber().equals("") ){ %> 
      	     	 <br>
        
    	<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <!-- input type="button" title="Allegato D (AUTORIZZAZIONE)" value="Allegato D (AUTORIZZAZIONE)"	onClick="javascript:window.location.href='TrasportoAnimali.do?command=PrintReport&file=AllegatoD.pdf&id=<%= OrgDetails.getId() %>';"-->
 		
 		<input type="button" title="Allegato D (AUTORIZZAZIONE)" value="Allegato D (AUTORIZZAZIONE)" onClick="openRichiestaPDF('<%= OrgDetails.getId() %>', '-1', '-1', '-1', 'AllegatoD.pdf', 'AllegatoTrasporti');">	
 				
 		
       <% } else { %>
    	
       <br>
    	 <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button"  disabled = "disabled" title="Allegato D (AUTORIZZAZIONE)" value="Allegato D (AUTORIZZAZIONE)"	onClick="javascript:window.location.href='TrasportoAnimali.do?command=PrintReport&file=AllegatoD.pdf&id=<%= OrgDetails.getId() %>';">
 		
       	<%} %>
        
       </td>
    </tr>
  </table>
<%--/dhv:permission--%>

<% String param1 = "orgId=" + OrgDetails.getOrgId(); %>
<dhv:container name="trasportoanimali" selected="details" object="OrgDetails" param="<%= param1 %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' hideContainer="<%= !OrgDetails.getEnabled() || OrgDetails.isTrashed() %>">
<input type="hidden" name="orgId" value="<%= OrgDetails.getOrgId() %>">
<dhv:evaluate if="<%=OrgDetails.isTrashed()%>">
    <dhv:permission name="trasportoanimali-trasportoanimali-edit">
      <input type="button" value="Ripristina"	onClick="javascript:window.location.href='TrasportoAnimali.do?command=Restore&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>
</dhv:evaluate>



<dhv:evaluate if="<%=(!OrgDetails.isTrashed() && ( OrgDetails.getStato().equals("Attivo") || OrgDetails.getStato().equals("Rinnovo")) )%>">
   
     <dhv:permission name="trasportoanimali-trasportoanimali-edit">
    <input type="button" value="Modifica"	onClick="javascript:window.location.href='TrasportoAnimali.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';">
    </dhv:permission>
  
  <dhv:evaluate if="<%=!(OrgDetails.getEnabled())%>">
    <dhv:permission name="trasportoanimali-trasportoanimali-edit">
      <input type="button" value="<dhv:label name="global.button.Enable">Enable</dhv:label>" 	onClick="javascript:window.location.href='TrasportoAnimali.do?command=Enable&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>
  </dhv:evaluate>
  
    <dhv:permission name="trasportoanimali-trasportoanimali-delete">
    <input type="button" value="<dhv:label name="">Elimina</dhv:label>" onClick="javascript:popURLReturn('TrasportoAnimali.do?command=ConfirmDelete&id=<%=OrgDetails.getId()%>&popup=true','TrasportoAnimali.do?command=Search', 'Delete_account','320','200','yes','no');">
    </dhv:permission>
  
  
  
 <script language="JavaScript" TYPE="text/javascript">
function enable() {
var b = document.getElementById("modB");
var c = document.getElementById("modC");
b.disabled=false;
c.disabled=false;
}
</script>

<!--dhv:permission name="trasportoanimali-trasportoanimali-edit"-->
    <% if (!OrgDetails.getAccountNumber().equals("")){ %>
 
		<%-- && OrgDetails.getAccountSize()!=-1 --%>
    <%} else  { %>
		<dhv:permission name="trasportoanimali-trasportoanimali-genera-view">

			<input type="button" title= "Genera il Numero Registrazione" value="Genera Numero Registrazione"	onClick="javascript:window.location.href='TrasportoAnimali.do?command=GeneraCodiceOsa&orgId=<%= OrgDetails.getOrgId()%>';">

 		</dhv:permission>
    
	<%} %>
<%--<dhv:permission name="trasportoanimali-trasportoanimali-genera-view">--%>
		<input type="button" title= "Carica gli autoveicoli" name="Carica Autoveicoli" value="Carica Autoveicoli" onClick="javascript:window.location.href='TrasportoAnimali.do?command=InsertVeicoli&carica=veicoli&orgId=<%=OrgDetails.getOrgId()%>'"></a>
	<%--/dhv:permission--%>
	
	<%if(OrgDetails.isSediCaricate()==true){ %>
	<dhv:permission name="trasportoanimali-trasportoanimali-genera-view">
		<input type="button" disabled="disabled" title= "Carica le sedi operative degli autoveicoli" name="Carica Sedi Operative" value="Carica Sedi Operative" onClick="javascript:window.location.href='TrasportoAnimali.do?command=InsertSedi&carica=sedi&orgId=<%=OrgDetails.getOrgId()%>'"></a>
	</dhv:permission>
	
	<%}else{ %>
	<dhv:permission name="trasportoanimali-trasportoanimali-genera-view">
		<input type="button" title= "Carica le sedi operative degli autoveicoli" name="Carica Sedi Operative" value="Carica Sedi Operative" onClick="javascript:window.location.href='TrasportoAnimali.do?command=InsertSedi&carica=sedi&orgId=<%=OrgDetails.getOrgId()%>'"></a>
	</dhv:permission>
	
	<%} %>
	<dhv:permission name="trasportoanimali-trasportoanimali-genera-view">
		<input type="button" title= "Carica Personale" name="Carica Personalee" value="Carica Personale" onClick="javascript:window.location.href='TrasportoAnimali.do?command=InsertPersonale&carica=personale&orgId=<%=OrgDetails.getOrgId()%>'"></a>
	</dhv:permission>
<!-- /dhv:permission-->
<dhv:permission name="trasportoanimali-trasportoanimali-stato-view">
  <br/><br/>
<table cellpadding="2" width="40%" bgcolor="#FF9933">
  <tr bgcolor="#FFFFFF">
    <th colspan="1" align="center" >
	    <strong><dhv:label name="">Gestione Stato: </dhv:label></strong>
	    <input type="button" value="<dhv:label name="">Sospensione</dhv:label>"	onClick="gestisciStatoTrasportoAnimali('Sospeso','<%= OrgDetails.getOrgId() %>','<%= addLinkParams(request, "popup|actionplan") %>');">
	    <input type="button" value="<dhv:label name="">Revoca</dhv:label>"	onClick="gestisciStatoTrasportoAnimali('Revocato','<%= OrgDetails.getOrgId() %>','<%= addLinkParams(request, "popup|actionplan") %>');">
	    <input type="button" value="<dhv:label name="">Cessazione</dhv:label>"	onClick="gestisciStatoTrasportoAnimali('Cessato','<%= OrgDetails.getOrgId() %>','<%= addLinkParams(request, "popup|actionplan") %>');">
	    <input type="button" value="<dhv:label name="">Rinnovo</dhv:label>"	onClick="gestisciStatoTrasportoAnimali('Rinnovo','<%= OrgDetails.getOrgId() %>','<%= addLinkParams(request, "popup|actionplan") %>');">
	    
    </th>
  </tr>
</table>
</dhv:permission>
</dhv:evaluate>
<dhv:evaluate if="<%=(!OrgDetails.isTrashed() && OrgDetails.getStato().equals("Sospeso"))%>">
<dhv:permission name="trasportoanimali-trasportoanimali-stato-view">
<table cellpadding="2" width="40%" bgcolor="#FF9933">
  <tr bgcolor="#FFFFFF">
    <th colspan="1" align="center" >
	    <strong><dhv:label name="">Gestione Stato: </dhv:label></strong>
	    <input type="button" value="<dhv:label name="">Riattivazione</dhv:label>"	onClick="gestisciStatoTrasportoAnimali('Attivo','<%= OrgDetails.getOrgId() %>','<%= addLinkParams(request, "popup|actionplan") %>');">
	    <input type="button" value="<dhv:label name="">Revoca</dhv:label>"	onClick="gestisciStatoTrasportoAnimali('Revocato','<%= OrgDetails.getOrgId() %>','<%= addLinkParams(request, "popup|actionplan") %>');">
	    <input type="button" value="<dhv:label name="">Cessazione</dhv:label>"	onClick="gestisciStatoTrasportoAnimali('Cessato','<%= OrgDetails.getOrgId() %>','<%= addLinkParams(request, "popup|actionplan") %>');">
    </th>
  </tr>
</table>
</dhv:permission>
</dhv:evaluate>
  <dhv:evaluate if="<%=(!OrgDetails.isTrashed() && (OrgDetails.getStato().equals("Cessato") || OrgDetails.getStato().equals("Revocato") ))%>">
  <dhv:evaluate if="<%=!(OrgDetails.getEnabled())%>">
    <dhv:permission name="trasportoanimali-trasportoanimali-edit">
      <input type="button" value="<dhv:label name="global.button.Enable">Enable</dhv:label>" 	onClick="javascript:window.location.href='TrasportoAnimali.do?command=Enable&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>
  </dhv:evaluate>
  
    <dhv:permission name="trasportoanimali-trasportoanimali-delete">
    <input type="button" value="<dhv:label name="">Elimina</dhv:label>" onClick="javascript:popURLReturn('TrasportoAnimali.do?command=ConfirmDelete&id=<%=OrgDetails.getId()%>&popup=true','TrasportoAnimali.do?command=Search', 'Delete_account','320','200','yes','no');">
    </dhv:permission>
  </dhv:evaluate>
</br>
</br>

<dhv:permission name="note_hd-view">
<jsp:include page="../note_hd/link_note_hd.jsp">
<jsp:param name="riferimentoId" value="<%=OrgDetails.getOrgId() %>" />
<jsp:param name="riferimentoIdNomeTab" value="organization" />
</jsp:include> <br><br>
</dhv:permission>

<%-- <jsp:include page="../schede_centralizzate/iframe.jsp"> --%>
<%--     <jsp:param name="objectId" value="<%=OrgDetails.getOrgId() %>" /> --%>
<%--      <jsp:param name="tipo_dettaglio" value="18" /> --%>
<%--      </jsp:include> --%>

<%-- <jsp:include page="../preaccettazionesigla/button_preaccettazione.jsp">
    <jsp:param name="riferimentoIdPreaccettazione" value="<%=OrgDetails.getOrgId() %>" />
    <jsp:param name="riferimentoIdNomePreaccettazione" value="orgId" />
    <jsp:param name="riferimentoIdNomeTabPreaccettazione" value="organization" />
    <jsp:param name="userIdPreaccettazione" value="<%=User.getUserId() %>" />
</jsp:include>  --%>

   <% if (1==1) { %>  

<jsp:include page="../schede_centralizzate/iframe.jsp">
    <jsp:param name="objectId" value="<%=OrgDetails.getOrgId() %>" />
     <jsp:param name="tipo_dettaglio" value="47" />
     </jsp:include>     
<% } else { %>     
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Scheda Impresa Trasporto Animali</dhv:label></strong>
    </th>
  </tr>
 <input type="hidden" name="id" value="<%=OrgDetails.getOrgId()%>" > 
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.site">A.S.L.</dhv:label>
    </td>
    <td>
      <%= SiteList.getSelectedValue(OrgDetails.getSiteId()) %>
      
      
      <input type="hidden" name="siteId" value="<%=OrgDetails.getSiteId()%>" >
    </td>
  </tr>
  
    <tr class="containerBody">
      			<td nowrap class="formLabel">
        			<dhv:label name="">Data Presentazione Richiesta</dhv:label>
      			</td>
      			<td>
        			<zeroio:tz timestamp="<%= OrgDetails.getDate1() %>" dateOnly="true" showTimeZone="false" default="&nbsp;" />
      			</td>
    		</tr>
 <dhv:include name="accounts-name" none="true">
  			<tr class="containerBody">
    			<td nowrap class="formLabel" name="orgname1" id="orgname1">
      				<dhv:label name="">Denominazione</dhv:label>
    			</td>
    			<td>
      				<%= toHtmlValue(OrgDetails.getName()) %>
    			</td>
  			</tr>
  			</dhv:include>
    
    
    
    <dhv:evaluate if="<%= hasText(OrgDetails.getAccountNumber()) %>">
    <tr class="containerBody"><td nowrap class="formLabel">
      <dhv:label name="">Numero Registrazione</dhv:label>
    </td><td>
       <%= toHtml(OrgDetails.getAccountNumber()) %>&nbsp;
    </td></tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= hasText(OrgDetails.getNumAut()) %>">
    <tr class="containerBody"><td nowrap class="formLabel">
      <dhv:label name="">Registrazione Precedente</dhv:label>
    </td><td>
       <%= toHtml(OrgDetails.getNumAut()) %>&nbsp;
    </td></tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= hasText(OrgDetails.getPartitaIva()) %>">
  <tr class="containerBody">
    			<td class="formLabel" nowrap>
      				Partita IVA
    			</td>
    			<td>
      				<%= toHtmlValue( OrgDetails.getPartitaIva()) %>
      				
    			</td>
  			</tr>
  			</dhv:evaluate>
  			<dhv:evaluate if="<%= hasText(OrgDetails.getCodiceFiscale()) %>">
  			<tr class="containerBody">
    			<td class="formLabel" nowrap>
      				Codice Fiscale
    			</td>
    			<td>
      				<%= toHtmlValue(OrgDetails.getCodiceFiscale()) %>  
    			</td>
  			</tr>
  			</dhv:evaluate>
  			<dhv:include name="organization.specieAllev" none="true">
  			    <tr class="containerBody">
			      <td nowrap class="formLabel">
			        <dhv:label name="">Animali Trasportati</dhv:label>
			      </td>
			      <td>
			     	 <% String  selezione2="";
			    		HashMap<Integer,String> lista2=OrgDetails.getListaCategoria();
			    		Iterator<Integer> valori2=OrgDetails.getListaCategoria().keySet().iterator();
			    		
			    		while(valori2.hasNext()){
			    			String Sel2=lista2.get(valori2.next());			    			
			    				selezione2+=Sel2+" - ";
			    			}			    		
			    		out.print(selezione2); %>
			    	  <input type="hidden" name="categoriaTrasportata" value="<%=OrgDetails.getListaCategoria()%>" >
			       </td>			      
			    </tr>
			</dhv:include>
			
  			<dhv:include name="organization.specieAllev" none="true">
			    <tr class="containerBody">
			      <td nowrap class="formLabel">
			        <dhv:label name="">Specie Animali Trasportati</dhv:label>
			      </td>
			      <td>
			     	 <%String  selezione="";

			    		HashMap<Integer,String> lista=OrgDetails.getListaAnimali();
			    		Iterator<Integer> valori=OrgDetails.getListaAnimali().keySet().iterator();
			    		
			    		while(valori.hasNext()){
			    			String Sel=lista.get(valori.next());
			    			
			    			if(Sel.equals("Altre Specie")){
			    				
			    				selezione+=" </br> ";
			    				
			    			}
			    			selezione+=Sel+" - ";
			    			}
			    		out.print(selezione); %>
			    		
						<%if(OrgDetails.getCodice10()!=null && !OrgDetails.getCodice10().equals("")){ %>
						<%="<b>Descrizione: </b>"+ OrgDetails.getCodice10() %>
						<input type="hidden" name="codice10" value="<%=OrgDetails.getCodice10()%>" >
						<%} %>
      				<input type="hidden" name="specieA" value="<%=OrgDetails.getListaAnimali()%>" >
			       </td>
			    </tr>
  			</dhv:include>
  			<tr class="containerBody">
  				<td  class="formLabel" nowrap>
  					<dhv:label name="">Stato</dhv:label>
  				</td>
  				<td>
  					       <%= toHtml(OrgDetails.getStato()) %>
  					       <%if(OrgDetails.getData_cambio_stato() != null && OrgDetails.getUtente_cambio_stato() != -1){ %>
  					        - Data operazione: <zeroio:tz timestamp="<%= OrgDetails.getData_cambio_stato() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" />
  					        - Operazione effettuata da: <dhv:username id="<%= OrgDetails.getUtente_cambio_stato() %>"/>
  					       <%} %>
  			   </td>  				
  			</tr>
  			
		</table>  
		
		<BR/>
		
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
		<TR><TH colspan="2">LISTA LINEE PRODUTTIVE</TH></TR>
  		<tr class="containerBody"><td  class="formLabel" nowrap>Altra Normativa</td><td class="containerBody">TRASPORTO ANIMALI</td></tr>
  		</table>
				<br>
		
		
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
   <tr>
    <th colspan="2">
      <strong><dhv:label name="">Titolare o Legale Rappresentante</dhv:label></strong>
     
    </th>
  </tr>
  <dhv:evaluate if="<%= (OrgDetails.getTitoloRappresentante()>0) %>">
    <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Titolo</dhv:label>
    </td>
    <td class="containerBody"> 
       <%= TitoloList.getSelectedValue(OrgDetails.getTitoloRappresentante()) %></td>
  </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= hasText(OrgDetails.getCodiceFiscaleRappresentante()) %>">			
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Codice fiscale Rappresentante</dhv:label>
			</td>
			<td>
         	<%= OrgDetails.getCodiceFiscaleRappresentante() %>&nbsp; 
			</td>
		</tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= hasText(OrgDetails.getNomeRappresentante()) %>">		
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Nome Rappresentante</dhv:label>
			</td>
			<td>
         	<%= OrgDetails.getNomeRappresentante() %>&nbsp; 
			</td>
		</tr>
     </dhv:evaluate>
  
	  	<!-- aggiunto da d.dauria -->
  <dhv:evaluate if="<%= hasText(OrgDetails.getCognomeRappresentante()) %>">
     <tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Cognome Rappresentante</dhv:label>
			</td>
			<td>
         	<%= OrgDetails.getCognomeRappresentante() %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
<dhv:evaluate if="<%= (OrgDetails.getDataNascitaRappresentante() != null) %>">
   <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Data Nascita</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= OrgDetails.getDataNascitaRappresentante() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
    </td>
  </tr>
</dhv:evaluate>
  	 
		<dhv:evaluate if="<%= hasText(OrgDetails.getLuogoNascitaRappresentante()) %>">			
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Comune di nascita</dhv:label>
			</td>
			<td>
         	<%= OrgDetails.getLuogoNascitaRappresentante()%>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
	
	<dhv:evaluate if="<%= hasText(OrgDetails.getEmailRappresentante()) %>">						
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Email</dhv:label>
			</td>
			<td>
         	<%= OrgDetails.getEmailRappresentante() %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
		
	<dhv:evaluate if="<%= hasText(OrgDetails.getTelefonoRappresentante()) %>">							
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Telefono</dhv:label>
			</td>
			<td>
         	<%= OrgDetails.getTelefonoRappresentante() %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
		
		<dhv:evaluate if="<%= hasText(OrgDetails.getFax()) %>">							
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Fax</dhv:label>
			</td>
			<td>
         	<%= OrgDetails.getFax() %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
		
	</table>
<br>

<dhv:include name="organization.phoneNumbers" none="false">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="accounts.accounts_add.PhoneNumbers">Phone Numbers</dhv:label></strong>
	  </th>
  </tr>

<%  
  Iterator inumber = OrgDetails.getPhoneNumberList().iterator();
  if (inumber.hasNext()) {
    while (inumber.hasNext()) {
      OrganizationPhoneNumber thisPhoneNumber = (OrganizationPhoneNumber)inumber.next();
%>    
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <%= toHtml(thisPhoneNumber.getTypeName()) %>
      </td>
      <td>
        <%= toHtml(thisPhoneNumber.getPhoneNumber()) %>
        <dhv:evaluate if="<%=thisPhoneNumber.getPrimaryNumber()%>">        
          <dhv:label name="trasportoanimali.primary.brackets">(Primary)</dhv:label>
        </dhv:evaluate>&nbsp;
      </td>
    </tr>
<%    
    }
  } else {
%>
    <tr class="containerBody">
      <td colspan="2">
        <font color="#9E9E9E"><dhv:label name="contacts.NoPhoneNumbers">No phone numbers entered.</dhv:label></font>
      </td>
    </tr>
<%}%>

</table>
<br />
</dhv:include>

<dhv:include name="organization.addresses" none="true">

<%  
  Iterator iaddress = OrgDetails.getAddressList().iterator();
  Object address[] = null;
  int i = 0;
  if (iaddress.hasNext()) {
    while (iaddress.hasNext()) {
      OrganizationAddress thisAddress = (OrganizationAddress)iaddress.next();
%>  
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <dhv:evaluate if="<%= thisAddress.getType() == 1 %>">
	    <strong><dhv:label name="accounts.accounts_add.Addressess">Sede legale</dhv:label></strong>
	  </dhv:evaluate>
	  <dhv:evaluate if="<%= thisAddress.getType() == 5 %>">
	    <strong><dhv:label name="accounts.accounts_add.Addressess">Sede operativa</dhv:label></strong>
	  </dhv:evaluate>  
	  <dhv:evaluate if="<%= thisAddress.getType() == 7 %>">
	    <strong><dhv:label name="accounts.accounts_add.Addressess">Lavaggio Autorizzato</dhv:label></strong>
	  </dhv:evaluate>  
	   <%-- %> <strong><dhv:label name="trasportoanimali.trasportoanimali_add.Addressess"><%= toHtml(thisAddress.getTypeName()) %></dhv:label></strong>--%>
	  </th>
  </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
        <%= ((thisAddress.getType()==7)?("Lavaggio"):(toHtml(thisAddress.getTypeName()))) %>
      </td>
      <td>
        <%= ( thisAddress.getType() == 1 && thisAddress.getCity().equals("-1"))?(toHtml(thisAddress.getCountry())):(toHtml(thisAddress.toString())) %>&nbsp;
        <dhv:evaluate if="<%=thisAddress.getPrimaryAddress()%>">        
          <dhv:label name="trasportoanimali.primary.brackets">(Primary)</dhv:label>
        </dhv:evaluate>
      </td>
    </tr>
  <%--<dhv:evaluate if="<%= thisAddress.getStreetAddressLine2()%>">
    <dhv:evaluate if="<%=thisAddress.getType() == 1 %>">
      <tr class="containerBody">
       <td nowrap class="formLabel">
          <dhv:label name="">C/O</dhv:label>
       </td>
       <td>
        <input type="text" size="40" name="address1line2" maxlength="80" value="<%= thisAddress.getStreetAddressLine2() %>">
      </td>
  </tr>
  </dhv:evaluate>
 </dhv:evaluate>--%>
    </table><br>
<%
    }
  } else {
%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="requestor.requestor_add.Addresses">Addresses</dhv:label></strong>
	  </th>
  </tr>
    <tr class="containerBody">
      <td colspan="2">
        <font color="#9E9E9E"><dhv:label name="contacts.NoAddresses">No addresses entered.</dhv:label></font>
      </td>
    </tr>
    </table><br>
<%}%>


</dhv:include>
<dhv:include name="organization.emailAddresses" none="false">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="trasportoanimali.trasportoanimali_add.EmailAddresses">Email Addresses</dhv:label></strong>
	  </th>
  </tr>
<%
  Iterator iemail = OrgDetails.getEmailAddressList().iterator();
  if (iemail.hasNext()) {
    while (iemail.hasNext()) {
      OrganizationEmailAddress thisEmailAddress = (OrganizationEmailAddress)iemail.next();
%>    
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <%= toHtml(thisEmailAddress.getTypeName()) %>
      </td>
      <td>
        <a href="mailto:<%= toHtml(thisEmailAddress.getEmail()) %>"><%= toHtml(thisEmailAddress.getEmail()) %></a>&nbsp;
        <dhv:evaluate if="<%=thisEmailAddress.getPrimaryEmail()%>">        
          <dhv:label name="trasportoanimali.primary.brackets">(Primary)</dhv:label>
        </dhv:evaluate>
      </td>
    </tr>
<%    
    }
  } else {
%>
    <tr class="containerBody">
      <td colspan="2">
        <font color="#9E9E9E"><dhv:label name="contacts.NoEmailAdresses">No email addresses entered.</dhv:label></font>
      </td>
    </tr>
<%}%>

</table>
</dhv:include>
<%if(OrgDetails.getCodiceFiscaleCorrentista()!=null && OrgDetails.getNomeCorrentista()!=null && OrgDetails.getContoCorrente()!=null){ %>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" id="responsabile">
			<tr>
				<th colspan="2">
					<strong>
						<dhv:label name="">Responsabile Trasporto</dhv:label>
					</strong>
				</th>
			</tr>
			
			<!-- Cognome -->
			<dhv:evaluate if="<%= hasText(OrgDetails.getCodiceFiscaleCorrentista()) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel">
				  Cognome
				</td>
				<td>
				  <%=OrgDetails.getCodiceFiscaleCorrentista() %>
				</td>
			</tr>
			</dhv:evaluate>
			<!-- Nome -->
			<dhv:evaluate if="<%= hasText(OrgDetails.getNomeCorrentista()) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel">
				  Nome
				</td>
				<td>
				  <%=OrgDetails.getNomeCorrentista() %>
				</td>
			</tr>
			</dhv:evaluate>	
			<dhv:evaluate if="<%= hasText(OrgDetails.getContoCorrente()) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel">
				  <dhv:label name="">Telefono</dhv:label>
				</td>
				<td>
				  <%=OrgDetails.getContoCorrente() %>
				</td>
			</tr>
			</dhv:evaluate>			
		</table>
		<%} %>
		<% } %>
		</br>
		
		
		<%
		Integer numeroPersonale=(Integer)request.getAttribute("numeroPersonale");
		Integer numeroSedi=(Integer)request.getAttribute("numeroSedi");
		Integer numeroAutoveicoli=(Integer)request.getAttribute("numeroAutoveicoli");
		
		%>
		
		
		<%if(request.getAttribute( "tabella" )!=null && request.getAttribute( "tabella2" )!=null && request.getAttribute( "tabella3" )!=null)  {%>
		
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" id="responsabile">
			<tr>
				<th colspan="2">
					<strong>
						<dhv:label name="">Autoveicoli e Sedi Operative Autoveicoli</dhv:label>
					</strong>
				</th>
			</tr>
			
			
		<tr>
		<p>
			Utilizzare le caselle vuote sopra l'intestazione per filtrare.
		</p>
		</tr>
		
	
		<tr>
		<td>
		<br>
		<dhv:permission name="carica-autoveicoli-add">
		<a href="javascript:chiamaActionVeicoli('VeicoliList.do?command=Add&tipoaggiunto=veicolo&scroll=','&orgId=<%=OrgDetails.getId() %>&maxRows=15&veicoli_sw_=true&veicoli_tr_=true&veicoli_p_=<%=numeroAutoveicoli %>&veicoli_mr_=15');">Inserisci Singolo Veicolo</a>
		</dhv:permission>
				<br>
		<%if(request.getAttribute( "tabella" )!=null)  {%>
		
		

		<%
		request.setAttribute("Organization",OrgDetails);
		%>
		
		
		<form name="aiequidiForm" action="VeicoliList.do?orgId=<%=OrgDetails.getOrgId() %>">
		<input type="hidden" name="orgId" value="<%=OrgDetails.getOrgId() %>">
	       <%=request.getAttribute( "tabella" )%>
	    <jmesa:tableFacade editable="true" >   <jmesa:htmlRow uniqueProperty="id" >   </jmesa:htmlRow></jmesa:tableFacade>
	    
	     
	    </form>



	<script type="text/javascript">
            function onInvokeAction(id) {
                $.jmesa.setExportToLimit(id, '');
                $.jmesa.createHiddenInputFieldsForLimitAndSubmit(id);
            }
            function onInvokeExportAction(id) {
                var parameterString = $.jmesa.createParameterStringForLimit(id);
				
                 location.href = 'VeicoliList.do?&' + parameterString;
            }
    </script>



<%} %>
</td>
<td>
<br><dhv:permission name="carica-autoveicoli-add">
			<a href="javascript:chiamaActionVeicoli('VeicoliList.do?command=Add&tipoaggiunto=sede&scroll=','&orgId=<%=OrgDetails.getId() %>&sedi_sw_=true&sedi_tr_=true&sedi_p_=<%=numeroSedi %>&sedi_mr_=15');">Inserisci Singola Sede Operativa</a>
</dhv:permission>
<br>
		
		<%if(request.getAttribute( "tabella2" )!=null)  {%>
		<%
		request.setAttribute("Organization",OrgDetails);
		%>
		
		
		<form name="aiequidiForm" action="VeicoliList.do?orgId=<%=OrgDetails.getOrgId() %>">
		<input type="hidden" name="orgId" value="<%=OrgDetails.getOrgId() %>">
	       <%=request.getAttribute( "tabella2" )%>
	    <jmesa:tableFacade editable="true" >   <jmesa:htmlRow uniqueProperty="id">   </jmesa:htmlRow></jmesa:tableFacade>
	    
	     
	    </form>



	<script type="text/javascript">
            function onInvokeAction(id) {
                $.jmesa.setExportToLimit(id, '');
                $.jmesa.createHiddenInputFieldsForLimitAndSubmit(id);
            }
            function onInvokeExportAction(id) {
                var parameterString = $.jmesa.createParameterStringForLimit(id);
				
                 location.href = 'VeicoliList.do?&' + parameterString;
            }
    </script>



<%} %>
</td>
</tr>
</table>

<%} %>

</br>

<p>
			Utilizzare le caselle vuote sopra l'intestazione per filtrare.
		</p>

	<%if(request.getAttribute( "tabella3" )!=null)  { %>
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" id="responsabile">
			<tr>
				<th colspan="2">
					<strong>
						<dhv:label name="">Personale</dhv:label>
					</strong>
				</th>
			</tr>
			
			
		<tr>
		<td>
		<br>
		<dhv:permission name="carica-autoveicoli-add">
		<a href="javascript:chiamaActionVeicoli('VeicoliList.do?command=Add&tipoaggiunto=personale&scroll=','&orgId=<%=OrgDetails.getId() %>&maxRows=15&personale_tr_=true&personale_p_=<%=numeroPersonale %>&personale_mr_=15');">Inserisci Singola Persona</a>
		</dhv:permission>
		<br>

		<%
		request.setAttribute("Organization",OrgDetails);
		%>
		
		
		<form name="aiequidiForm" action="VeicoliList.do?orgId=<%=OrgDetails.getOrgId() %>">
		<input type="hidden" name="orgId" value="<%=OrgDetails.getOrgId() %>">
	       <%=request.getAttribute( "tabella3" )%>
	    <jmesa:tableFacade editable="true" >   <jmesa:htmlRow uniqueProperty="id">   </jmesa:htmlRow></jmesa:tableFacade>
	    
	     
	    </form>



	<script type="text/javascript">
            function onInvokeAction(id) {
                $.jmesa.setExportToLimit(id, '');
                $.jmesa.createHiddenInputFieldsForLimitAndSubmit(id);
            }
            function onInvokeExportAction(id) {
                var parameterString = $.jmesa.createParameterStringForLimit(id);
				
                 location.href = 'VeicoliList.do?&' + parameterString;
            }
    </script>




</td>
</tr>
</table>

<%} %>

</br>
		<dhv:evaluate if="<%= hasText(OrgDetails.getNotes()) %>">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="requestor.requestor_add.AdditionalDetails">Additional Details</dhv:label></strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accountasset_include.Notes">Notes</dhv:label>
    </td>
    <td>
      <%=toHtml(OrgDetails.getNotes()) %>&nbsp;
    </td>
  </tr>
</table>
<br />
</dhv:evaluate>
<%-- %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="requestor.requestor_contacts_calls_details.RecordInformation">Record Information</dhv:label></strong>
    </th>     
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_calls_list.Entered">Entered</dhv:label>
    </td>
    <td>
      <dhv:username id="<%= OrgDetails.getEnteredBy() %>" />
      <zeroio:tz timestamp="<%= OrgDetails.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" />
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_contacts_calls_details.Modified">Modified</dhv:label>
    </td>
    <td>
      <dhv:username id="<%= OrgDetails.getModifiedBy() %>" />
      <zeroio:tz timestamp="<%= OrgDetails.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" />
    </td>
  </tr>
</table>--%>
</br>
<dhv:evaluate if="<%=(!OrgDetails.isTrashed() && OrgDetails.getStato().equals("Attivo"))%>">

<dhv:evaluate if="<%=OrgDetails.isTrashed()%>">
    <dhv:permission name="trasportoanimali-trasportoanimali-edit">
      <input type="button" value="Ripristina"	onClick="javascript:window.location.href='TrasportoAnimali.do?command=Restore&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>
</dhv:evaluate>
<dhv:evaluate if="<%=!OrgDetails.isTrashed()%>">
  <!--  
  <dhv:evaluate if="<%=(OrgDetails.getEnabled())%>">
    <dhv:permission name="trasportoanimali-trasportoanimali-edit"><input type="button" value="Modifica"	onClick="javascript:window.location.href='TrasportoAnimali.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission>
  </dhv:evaluate>
  -->
  
 <% if ((!OrgDetails.getAccountNumber().equals(""))&& (User.getActualUserId() != 1)){ %>
  <dhv:permission name="trasportoanimali-trasportoanimali-edit"><input type="button" disabled="disabled" title="Non è possibile effettuare la modifica in quanto è già stato generato il Numero Registrazione" value="Modifica"	onClick="javascript:window.location.href='TrasportoAnimali.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission>
  <%} else { %>
  
  
  <dhv:evaluate if="<%=(OrgDetails.getEnabled())%>">
    <dhv:permission name="trasportoanimali-trasportoanimali-edit"><input type="button" value="Modifica"	onClick="javascript:window.location.href='TrasportoAnimali.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission>
  </dhv:evaluate>
  <%} %>
  
  <dhv:evaluate if="<%=!(OrgDetails.getEnabled())%>">
    <dhv:permission name="trasportoanimali-trasportoanimali-edit">
      <input type="button" value="<dhv:label name="global.button.Enable">Enable</dhv:label>" 	onClick="javascript:window.location.href='TrasportoAnimali.do?command=Enable&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>
  </dhv:evaluate>
  <dhv:evaluate if='<%= (request.getParameter("actionplan") == null) %>'>
    <dhv:permission name="trasportoanimali-trasportoanimali-delete"><input type="button" value="<dhv:label name="">Elimina</dhv:label>" onClick="javascript:popURLReturn('TrasportoAnimali.do?command=ConfirmDelete&id=<%=OrgDetails.getId()%>&popup=true','TrasportoAnimali.do?command=Search', 'Delete_account','320','200','yes','no');"></dhv:permission>
  </dhv:evaluate>
</dhv:evaluate>
</dhv:evaluate>
</dhv:container>
<%= addHiddenParams(request, "popup|popupType|actionId") %>
<% if (request.getParameter("return") != null) { %>
<input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>
<% if (request.getParameter("actionplan") != null) { %>
<input type="hidden" name="actionplan" value="<%=request.getParameter("actionplan")%>">
<%}%>
<script>
document.body.scrollTop=<%= request.getAttribute("scroll") %>;
</script>

<div id="boxes">

<%-- IL CAMPO SRC è DA AGGIUSTARE --%>
<div id="dialog4" class="window" width="600" height="380">
  <jsp:include page="gestione_stato.jsp"/>
</div>
<input type = "text" name="data_presentazione_richiesta" id="data_presentazione_richiesta" value="<%= OrgDetails.getDate1() %>">


<!-- Mask to cover the whole screen -->
  <div id="mask"></div>

</div>