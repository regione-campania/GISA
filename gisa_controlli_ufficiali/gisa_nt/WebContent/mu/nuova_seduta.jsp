<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<!-- RELATIVO AL NUOVO CALENDARIO CON MESE E ANNO FACILMENTE MODIFICABILI -->
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();
cal19.showNavigationDropdowns();
</SCRIPT>
<!-- <script language="JavaScript" TYPE="text/javascript" SRC="mu/mu.js"></script> -->


<%@ page import="java.util.*" %>
<%@page import="org.aspcfs.utils.web.LookupList"%>
<%@page import="org.aspcfs.utils.web.LookupElement"%>
<%@page import="org.aspcfs.modules.contacts.base.Contact"%>

 <jsp:useBean id="OrgDetails" class="org.aspcfs.modules.stabilimenti.base.Organization" scope="request" />	
<jsp:useBean id="listaPartite" class="java.util.Vector" scope="request"/>
<jsp:useBean id="specieList"			class="org.aspcfs.utils.web.LookupList" scope="request" />
 <jsp:useBean id="specieBovine"			class="org.aspcfs.utils.web.LookupList" scope="request" />
 <jsp:useBean id="razzeBovine"			class="org.aspcfs.utils.web.LookupList" scope="request" />
 <jsp:useBean id="categorieBovine"			class="org.aspcfs.utils.web.LookupList" scope="request" />
 <jsp:useBean id="categorieBufaline"			class="org.aspcfs.utils.web.LookupList" scope="request" />
 <jsp:useBean id="catRischio"			class="org.aspcfs.utils.web.LookupList" scope="request" />
 <jsp:useBean id="PianiRisanamento"			class="org.aspcfs.utils.web.LookupList" scope="request" />
 <jsp:useBean id="seduta"			class="org.aspcfs.modules.mu.base.SedutaUnivoca" scope="request" />
 <jsp:useBean id="idPartitaDaAggiungere" class="java.lang.String" scope="request"/>
 
<%@page import="org.aspcfs.modules.mu.base.PartitaUnivoca"%>
 <%@page import="org.aspcfs.modules.mu.base.CapoUnivoco"%>
 
  <%@ include file="mu_js.jsp"%>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script   src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/jquery-ui-1.9.2.custom.css" />  
<script type="text/javascript" src="dwr/interface/DwrCapiPartita.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
  
 <script>
 function disabilitaAbilitaBottone(bottone1, bottone2){
	 bottone1.className="disabled";
	 bottone1.disabled = true;
	 bottone2.className="";
	 bottone2.disabled = false;
 }
 
 function caricaCapiDaPartita(idpartita){
	 DwrCapiPartita.listaCapiMacellabiliDaPartita(idpartita,{callback:caricaCapiDaPartitaCallBack,async:false});
	 
	 var aggiungi = document.getElementById("aggiungiCapi_"+idpartita);
	 var rimuovi = document.getElementById("rimuoviCapi_"+idpartita);
	 disabilitaAbilitaBottone(aggiungi, rimuovi);
}
 
 function caricaCapiDaPartitaCallBack(val){
	
	 for (var i = 0; i < val.length; i++) {
		 generaDatiCapi(val[i].numeroPartita, val[i].idPartita, val[i].id, val[i].specieCapoNome, val[i].specieCapo, val[i].matricola);
	  }
	}
 
 function rimuoviCapiDaPartita(idpartita){
	 DwrCapiPartita.listaCapiMacellabiliDaPartita(idpartita,{callback:rimuoviCapiDaPartitaCallBack,async:false});
	 var aggiungi = document.getElementById("aggiungiCapi_"+idpartita);
	 var rimuovi = document.getElementById("rimuoviCapi_"+idpartita);
	 disabilitaAbilitaBottone(rimuovi, aggiungi);
}
 
 function rimuoviCapiDaPartitaCallBack(val){
	
	 for (var i = 0; i < val.length; i++) {
		 rimuoviDatiCapi(val[i].id);
	  }
	}
 
 function mostraCapi(idpartita){
	 var tabella = document.getElementById("tabella_"+idpartita);
	 if (tabella!=null){
		 if (tabella.style.display=='block')
			 tabella.style.display='none';
		 else
			 tabella.style.display='block';
	 }
 }
 
 function gestisciAggiuntaTotale(campo, specie, partita){
	var x = document.getElementsByName("capo_"+partita+"_"+specie);	
	 if (campo.checked){
		for (var i =0; i<x.length; i++){
			x[i].checked = true;
			x[i].parentNode.parentNode.setAttribute('class', 'row1');
		}
	 }
	 else {
		for (var i =0; i<x.length; i++){
		 x[i].checked = false;
		x[i].parentNode.parentNode.setAttribute('class', '');
		}
	 }
 }
 
 function gestisciAggiuntaSingola(campo, specie, partita){
		 if (campo.checked){
			 campo.parentNode.parentNode.setAttribute('class', 'row1');
		 }
		 else {
			 document.getElementById("specie_"+specie+"_"+partita).checked = false;
			 campo.parentNode.parentNode.setAttribute('class', '');
		 }
		 }
 
 function checkForm(form){
	 var tabella = document.getElementById("table3");
	 var listaCapi = new Array();
	 
	 formTest = true;
	 message = "";
	
	 for(var i=3;i<tabella.rows.length;i++)
	 {
	   	  listaCapi.push(tabella.rows[i].id);
	 }
	 
	 document.getElementById("listaCapiSeduta").value = listaCapi;

	 if (listaCapi.length <= 0){
		 formTest = false;
		 message= "- Inserirsci almeno un capo \r\n";
	 }

		/* checkFormComunicazioni();
		checkFormAM(); */
		$(document).find('input').each(
				function() {
					//alert($(this).val());
					if ($(this).prop('required') == true && !($(this).css('display') == 'none') ) {
				
						if (($(this).is(':text') && ($(this).val() == null || $(this).val() == '' )) ){ 
						//	alert('ddd');
						message += label("", "- " + $(this).attr('label')
								+ " richiesta\r\n");
						formTest = false;
					}
					} /* else {
					    	alert('not required ' + $(this).attr('label'));
					    } */
				});
if (formTest)
	 form.submit();
else
	alert(message);
 }
	 
 </script>
 <%@ include file="../initPage.jsp" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
 <table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="MacellazioneUnica.do?command=List&orgId=<%=request.getParameter("orgId")%>">Home macellazioni </a> > 
			<dhv:evaluate if="<%=seduta.getId() < 0 %>">
				Aggiungi seduta
			</dhv:evaluate>
			
			<dhv:evaluate if="<%=seduta.getId() > 0 %>">
				Aggiungi capi a seduta
			</dhv:evaluate>
		</td>
	</tr>
</table>

<%
String param1 = "orgId=" + OrgDetails.getOrgId();
%>
<dhv:container name="stabilimenti_macellazioni_ungulati" selected="macellazioniuniche" object="OrgDetails" param="<%= param1 %>"  hideContainer='<%= isPopup(request)  %>' >


 <form name="nuovaSeduta" id="nuovaSeduta" action="MacellazioneUnica.do?command=InserisciSeduta" method="post" >
 
 <input type="hidden" id="idMacello" name="idMacello" value="<%=request.getParameter("orgId")%>"/> 
  <input type="hidden" id="idSeduta" name="idSeduta" value="<%=seduta.getId()%>"/> 
<dhv:evaluate if="<%=seduta.getId() < 0 %>">
<table class="details" layout="fixed" width="50%">
<col width="180px">
<th colspan="2">Creazione nuova seduta</th> 
<tr><td class="formLabel">Data seduta</td> <td> <input readonly type="text" name="dataSeduta" id="dataSeduta=" size="10" value="" required="required" label="Data seduta"/>&nbsp;  
    <a href="#" onClick="cal19.select(document.forms[0].dataSeduta,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
		<a href="#" style="cursor: pointer;" onclick="svuotaData(document.forms[0].dataSeduta);return false;"><img src="images/delete.gif" align="absmiddle"/></a>    
		</td></tr>

</table>
</dhv:evaluate>

<table class="details" layout="fixed" width="50%">
<tr> <th colspan="2">Capi</th>
<% Iterator iter = specieList.iterator();
	while (iter.hasNext()) 
	{
	LookupElement thisElement = (LookupElement) iter.next();
	String specie = thisElement.getDescription();
	int idSpecie = thisElement.getCode();%>
	<td class="formLabel"><%= specie%></td> <td> <input readonly style="border: none" type="text" id="num_<%=idSpecie %>" name="num_<%=idSpecie %>" size="3" value="0"/> </td>
 <%} %>
 </tr>
</table>
<br/><br/>

<%@ include file="griglia_aggiunta_capi.jsp" %>


<%-- <% if (idPartitaDaAggiungere!=null){ %> --%>
<!-- 	<script> -->
<!-- 	var r = confirm("Vuoi aggiungere alla seduta i capi della partita appena inserita?");
 	if (r == true) -->
<%-- 		openPopupCapiPartita('<%=idPartitaDaAggiungere%>'); --%>
<!-- 	</script> -->
<%-- <% } %> --%>
<dhv:evaluate if="<%=seduta.getId() < 0 %>">
<input type="button" value="INSERISCI SEDUTA" onClick="checkForm(this.form)"/> 
</dhv:evaluate>
<dhv:evaluate if="<%=seduta.getId() > 0 %>">
<input type="button" value="CONFERMA AGGIUNTA CAPI" onClick="checkForm(this.form)"/> 
</dhv:evaluate>
<input type="hidden" value="" id="listaCapiSeduta" name="listaCapiSeduta"/>

</form>

</dhv:container>
