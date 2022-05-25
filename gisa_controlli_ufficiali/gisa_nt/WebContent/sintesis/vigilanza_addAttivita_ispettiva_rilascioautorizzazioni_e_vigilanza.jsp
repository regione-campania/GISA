<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ include file="../initPage.jsp" %>
<%@ page import="java.util.*,org.aspcfs.modules.vigilanza.base.*" %>
<%@ page import="java.text.DateFormat, org.aspcfs.modules.actionplans.base.*"%>
<%@ page import="java.text.DateFormat, org.aspcfs.modules.actionplans.base.*"%>
<%@page import="org.aspcfs.utils.web.LookupList"%>
<jsp:useBean  id="OrgDetails" class="org.aspcfs.modules.sintesis.base.SintesisStabilimento" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="TipoIspezione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="IspezioneMacrocategorie" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipoCampione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipoAudit" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="AuditTipo" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Bpi" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Haccp" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Ispezione" class="java.util.HashMap" scope="request" />
<jsp:useBean id="PianoMonitoraggio1" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="PianoMonitoraggio2" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="PianoMonitoraggio3" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleo" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleoDue" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleoTre" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleoQuattro" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleoCinque" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleoSei" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleoSette" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleoOtto" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleoNove" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleoDieci" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="SanzioniAmministrative" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="fileItem" class="com.zeroio.iteam.base.FileItem" scope="request" />
<jsp:useBean id="EsitoControllo" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="DistribuzionePartita" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="DestinazioneDistribuzione" 	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ArticoliAzioni" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="AzioniAdottate" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request" />
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request" />
<jsp:useBean id="popup" class="java.lang.String" scope="request" />	
<jsp:useBean id="TipoMobili" class="org.aspcfs.utils.web.LookupList" scope="request" />

<script type="text/javascript" src="dwr/interface/ControlliUfficiali.js?n=2"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popServiceContracts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAssets.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProducts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></SCRIPT>
<script language="JavaScript" type="text/javascript" src="javascript/confrontaDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/controlliUfficiali.js?n=2"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/controlli_ufficiali_imprese.js"></script>

<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script language="JavaScript">
var res;
	function load_linee_attivita_per_alt_id_sintesis_callback(returnValue) {
		  campo_combo_da_costruire = returnValue [2];
		  var select = document.getElementById(campo_combo_da_costruire); //Recupero la SELECT
	      
	      //Azzero il contenuto della seconda select
	      for (var i = select.length - 1; i >= 0; i--)
	    	  select.remove(i);

	      var NewOpt = document.createElement('option');
	      NewOpt.value = -1; // Imposto il valore
	      NewOpt.text = "-- SELEZIONARE UNA LINEA DI ATTIVITA --" // Imposto il testo

	     // if (returnValue [3]==indici[j])
	    	//  NewOpt.selected = true;
			
	      //Aggiungo l'elemento option
	      try
	      {
	    	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
	      } catch(e){
	    	  select.add(NewOpt); // Funziona solo con IE
	      }
	
	      indici = returnValue [0];
	      valori = returnValue [1];
	      //Popolo la seconda Select
	      for(j =0 ; j<indici.length; j++){
		      //Creo il nuovo elemento OPTION da aggiungere nella seconda SELECT
		      var NewOpt = document.createElement('option');
		      NewOpt.value = indici[j]; // Imposto il valore
		      NewOpt.text = valori[j]; // Imposto il testo

		     // if (returnValue [3]==indici[j])
		    	//  NewOpt.selected = true;
				
		      //Aggiungo l'elemento option
		      try
		      {
		    	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
		      } catch(e){
		    	  select.add(NewOpt); // Funziona solo con IE
		      }
		      
	      }

	   
	}
	
	  function costruisci_rel_ateco_attivita( alt_id, campo_combo_da_costruire ) {
		  //
		 // alert(campo_combo_da_costruire);
		  PopolaCombo.load_linee_attivita_per_alt_id_sintesis(alt_id , campo_combo_da_costruire, null, load_linee_attivita_per_alt_id_sintesis_callback)
	  }
	
</script>

 

<body onload = "provaFunzione('addticket'); gestioneVisibilitaCodiceAteco('addticket');onloadAllerta('addticket'); resetElementiNucleoIspettivo('<%=TicketDetails.getNucleoasList().size() %>'); costruisci_rel_ateco_attivita('<%= OrgDetails.getAltId() %>', 'id_linea_sottoposta_a_controllo'); ">



 <%

	TipoIspezione.setJsEvent("onChange=javascript:mostraMenuTipoIspezione('addticket');");
	TitoloNucleoDue.setJsEvent("onChange=mostraCampo2('addticket')");
	PianoMonitoraggio1.setJsEvent("onChange=javascript:piani('addticket')");
    PianoMonitoraggio2.setJsEvent("onChange=javascript:piani('addticket')");
    PianoMonitoraggio3.setJsEvent("onChange=javascript:piani('addticket')");
	AuditTipo.setJsEvent("onChange=javascript:mostraMenu4('addticket')");
    TipoAudit.setJsEvent("onChange=javascript:mostraMenu2('addticket')");
    TipoCampione.setJsEvent("onChange=javascript:reloadAddCU('StabilimentoSintesisActionVigilanza.do?command=Add&altId="+OrgDetails.getAltId()+"&tipoCampione='+this.value)"); 
	
	
    
	
%>

<% 
boolean popUp = false;
if (request.getParameter("popup") != null && !request.getParameter("popup").equals("")) {
	popUp = true;
}
%>

<form method="post" name="addticket" action="<%=OrgDetails.getAction() %>Vigilanza.do?command=Insert&auto-populate=true">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<% 
	if (!popUp){
%>
<td>
  <dhv:label name=""><a href="<%=OrgDetails.getAction()+".do" %>" >Gestione Anagrafica Impresa </a>-><a  href="<%=OrgDetails.getAction()+".do?command=Details&altId="+OrgDetails.getAltId()%>">Scheda Impresa</a> -><a href="<%=OrgDetails.getAction()+".do?command=ViewVigilanza&altId="+OrgDetails.getAltId()%>"> Controlli Ufficiali </a>-> Aggiungi</dhv:label>
  
</td>
<% } %>
</tr>
</table>

<input type="button" id = "btn_salva" value="<dhv:label name="button.inserta">Inserisci</dhv:label>"  name="Save" onClick="return controlloCuSorveglianza();" >
<%
	if(!popUp){
%>
<input type="submit" value="Annulla" onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>.do?command=ViewVigilanza&opId=<%=OrgDetails.getIdOperatore() %>&stabId=<%=OrgDetails.getIdStabilimento()%>'">
<% } else { %>
<input type="submit" value="Annulla" onClick="javascript:window.close();">
<% } %>
<br>

<table cellpadding="4" cellspacing="0" width="100%" class="details">
	
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Aggiungi Controllo Ufficiale</dhv:label></strong>
    </th>
	</tr>


	
<%@ include file="../controlliufficiali/alt_controlli_ufficiali_add.jsp" %>




</table>
<br><br>


<%@ include file="../controlliufficiali/opu_controlli_ufficiali_allarmerapido_add.jsp" %>

<%@ include file="../controlliufficiali/opu_controlli_ufficiali_laboratori_haccp.jsp" %>

<br>
<br>
<br>

<%@ include file="../controlliufficiali/opu_controlli_ufficiali_laboratori_haccp_non_in_regione.jsp" %>
	


 <input type = "hidden" name = "idMacchinetta" value = "<%=request.getAttribute("idMacchinetta") %>">
 
<input type="hidden" name="close" value="">
<input type="hidden" name="refresh" value="-1">
<input type="hidden" name="modified" value="<%=  TicketDetails.getModified() %>" />
<input type="hidden" name="currentDate" value="<%=  request.getAttribute("currentDate") %>" />
<input type="hidden" name="statusId" value="<%=  TicketDetails.getStatusId() %>" />
 

<input type="hidden" name="isAllegato" value="<%=TicketDetails.isListaDistribuzioneAllegata() %>">
<input type="hidden" name="trashedDate" value="<%=  TicketDetails.getTrashedDate() %>" />
<%= addHiddenParams(request, "popup|popupType|actionId") %>


<br>
<input type="button" id ="btn_salva2" value="<dhv:label name="button.inserta">Inserisci </dhv:label>" name="Save" onClick="return controlloCuSorveglianza()">
<%
	if(!popUp){
%>
<input type="submit" value="Annulla" onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>.do?command=ViewVigilanza&opId=<%=OrgDetails.getIdOperatore() %>&stabId=<%=OrgDetails.getIdStabilimento()%>'">
<% } else { %>
<input type="submit" value="Annulla" onClick="javascript:window.close()">
<% } %>
</form>
</body>
