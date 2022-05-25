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
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script   src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/jquery-ui-1.9.2.custom.css" />
<script type="text/javascript">
function checkForm(form) {

	formTest = true;
	message = "";
	


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
	
	
	$(document).find('select').each(
			
			function() {
			//	alert(alert($(this).prop('name')+ '  '+$(this).val()));
				if ($(this).prop('required') ) {
					
				//	alert($(this).prop('name')+ '  '+$(this).val());
		
				//	alert($(this).is(':text'));
					if ( $(this).val() == null || $(this).val() =='' ||  $(this).val() == 'null' || $(this).val() <  0)  { 
						message += label("", "- " + $(this).attr('label')
							+ " richiesta\r\n");
						formTest = false;
				}
				} /* else {
				    	alert('not required ' + $(this).attr('label'));
				    } */
			});
	
	
	$(document).find('textarea').each(
			
			function() {
			//	alert(alert($(this).prop('name')+ '  '+$(this).val()));
				if ($(this).prop('required') && !($(this).css('display') == 'none') ) {
					
				//	alert($(this).prop('name')+ '  '+$(this).val());
		
				//	alert($(this).is(':text'));
					if ( $(this).val() == null || $(this).val() =='' ||  $(this).val() == 'null' || $(this).val() <  0)  { 
						message += label("", "- " + $(this).attr('label')
							+ " richiesta\r\n");
						formTest = false;
				}
				} /* else {
				    	alert('not required ' + $(this).attr('label'));
				    } */
			});
	
	
	if (formTest){
		message = label("", "- " +  " Seleziona almeno un animale\r\n");
		formTest = false;
		
		$("input[id^=num]").each(
				
				function() {

				//alert($(this).attr("id"));	
			if ($(this).attr("id") != "numero_partita" && $(this).val() > 0){
				//alert('ok');
				//alert($(this).val());
				formTest = true;
				message = "";
			}
				});
	}

	if (formTest) {
		form.submit();
	} else {
		alert(message);
	}
}
</script>
<!-- <script language="JavaScript" TYPE="text/javascript" SRC="mu/mu.js"></script> -->

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ page import="java.util.*" %>
<%@page import="org.aspcfs.utils.web.LookupList"%>
<%@page import="org.aspcfs.utils.web.LookupElement"%>
<%@page import="org.aspcfs.modules.contacts.base.Contact"%>

 <jsp:useBean id="specieList"			class="org.aspcfs.utils.web.LookupList" scope="request" />
 
   <jsp:useBean id="specieBovine"			class="org.aspcfs.utils.web.LookupList" scope="request" />
  <jsp:useBean id="razzeBovine"			class="org.aspcfs.utils.web.LookupList" scope="request" />
  <jsp:useBean id="categorieBovine"			class="org.aspcfs.utils.web.LookupList" scope="request" />
  <jsp:useBean id="categorieBufaline"			class="org.aspcfs.utils.web.LookupList" scope="request" />
    <jsp:useBean id="catRischio"			class="org.aspcfs.utils.web.LookupList" scope="request" />
        <jsp:useBean id="PianiRisanamento"			class="org.aspcfs.utils.web.LookupList" scope="request" />
  <jsp:useBean id="popup"			class="java.lang.String" scope="request" />
 
 <%@ include file="mu_js.jsp"%>
 <%@ include file="../initPage.jsp" %>
 <SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
 <table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="MacellazioneUnica.do?command=List&orgId=<%=request.getParameter("orgId")%>">Home macellazioni </a> > Aggiungi partita
		</td>
	</tr>
</table>
<%
String param1 = "orgId=" +request.getParameter("orgId");
%>
<dhv:container name="stabilimenti_macellazioni_ungulati" selected="macellazioniuniche" object="OrgDetails" param="<%= param1 %>" hideContainer="<%= isPopup(request)  %>" >
 <form name="nuovaPartita" id="nuovaPartita" action="MacellazioneUnica.do?command=InserisciPartita" method="post" >
 
 <input type="hidden" id="idMacello" name="idMacello" value="<%=request.getParameter("orgId")%>"/> 
  <input type="hidden" id="popup" name="popup" value="<%=popup%>"/> 
<table class="details" layout="fixed" width="50%">
<col width="180px">


<th colspan="2">Informazioni partita / Controllo documentale</th> 
<tr><td class="formLabel">Codice azienda di provenienza</td> 
<td><input type="text" id="codiceAziendaProvenienza"  onchange="searchBdn(this.value,'1')" name="codiceAziendaProvenienza" required="required" label="Codice azienda provenienza"/>  <font color="red">*</font> </td></tr>
<tr><td></td><td><textarea rows="5" cols="33"  name = "cd_info_azienda_provenienza" id = "infoAzienda" ></textarea>
										
					<input type = "hidden" name = "rag_soc_azienda_provenienza" id = "rag_soc_azienda_provenienza" value =""/>
					<input type = "hidden" name = "denominazione_asl_azienda_prov" id = "denominazione_asl_azienda_prov" value =""/>
					<input type = "hidden" name = "id_asl_azienda_prov" id = "id_codice_asl_azienda_prov" value =""/>
					<input type = "hidden" name = "cod_asl_azienda_prov" id = "cod_asl_azienda_prov" value =""/>

					<div id="rowInfoAzienda" align="right" style="padding-right: 20px;"></div>	</td></tr>
<tr><td bgcolor="yellow" style="text-align:right">Numero partita</td> <td><input type="text" id="numero_partita"  name="numero_partita" readonly required="required" label="Numero partita"/>   	<input type="button" id="genera_partita" name="genera_partita" value="Numero automatico" onclick="javascript:generaPartita();" />   <font color="red">*</font> </td></tr>
<tr><td class="formLabel">Commerciante/proprietario degli animali</td> <td><input type="text" id="proprietarioAnimali" name="proprietarioAnimali"/></td></tr>
<tr><td class="formLabel">Codice azienda di nascita</td> <td><input type="text" id="codiceAziendaNascita" name="codiceAziendaNascita"/></td></tr>
<tr><td class="formLabel">Vincolo sanitario</td> <td> <input type="checkbox" id="vincoloSanitario"  name="vincoloSanitario"/> MOTIVO <input type="text" id="vincoloSanitarioMotivo" name="vincoloSanitarioMotivo"/></td></tr>
<tr><td class="formLabel">Mod 4</td> <td><input type="text" id="mod4" name="mod4" required="required" label="Modello 4" /> <font color="red">*</font></td></tr>
<tr><td class="formLabel">Data mod 4</td> <td> <input readonly type="text" name="dataMod4" id="dataMod4" required="required" label="Data Mod.4" size="10" value="" />  <font color="red">*</font> &nbsp;  
    <a href="#" onClick="cal19.select(document.forms[0].dataMod4,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
		<a href="#" style="cursor: pointer;" onclick="svuotaData(document.forms[0].dataMod4);return false;"><img src="images/delete.gif" align="absmiddle"/></a>    
		</td></tr>
<tr><td class="formLabel">Macellazione differita</td> <td> 	<%=PianiRisanamento.getHtmlSelect( "macellazioneDifferita", -1 ) %>  </td></tr> 
<tr><td class="formLabel">Disponibili info catena alimentare</td> <td> <input type="checkbox" id="infoCatenaAlimentare" name="infoCatenaAlimentare"/></td></tr>
<tr><td class="formLabel">Data di arrivo al macello</td> <td> <input readonly type="text" name="dataArrivoMacello" id="dataArrivoMacello" size="10" value="" required="required" label="Data arrivo al macello" />  <font color="red">*</font> &nbsp;  
  <a href="#" onClick="cal19.select(document.forms[0].dataArrivoMacello,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
		<a href="#" style="cursor: pointer;" onclick="svuotaData(document.forms[0].data_arrivo_macello);return false;"><img src="images/delete.gif" align="absmiddle"/></a>      
<input type="checkbox" id="dataArrivoMacelloDichiarata" name="dataArrivoMacelloDichiarata"/> Data dichiarata dal gestore</td></tr>
<th colspan="2">Identificazione mezzo di trasporto</th> 
<tr><td class="formLabel">Tipo</td> <td><input type="text" id="mezzoTipo" name="mezzoTipo"/></td></tr>
<tr><td class="formLabel">Targa</td> <td><input type="text" id="mezzoTarga" name="mezzoTarga"/></td></tr>
<tr><td class="formLabel">Trasporto superiore a 8 ore</td> <td> <input type="checkbox" id="trasportoSuperiore" name="trasportoSuperiore"/></td></tr>
<th colspan="2">Veterinari addetti al controllo</th> 

<% HashMap<String,ArrayList<Contact>> listaVeterinari = (HashMap<String,ArrayList<Contact>>)request.getAttribute("listaVeterinari"); %>
<tr>
<td colspan="2">1.
<input value="" size="35" id="veterinario1" name="veterinario1"  type="hidden" />
<select id="veterinari1" name="veterinari1" onchange="set_vet( this, 'veterinario1'); " required="required" label="Veterinario">
<option value="-1">Seleziona</option>
<%for (String gruppo : listaVeterinari.keySet()){ %>
<optgroup label="<%=gruppo %>"></optgroup>
<%for(Contact vet : listaVeterinari.get(gruppo)){ %>
<option value = "<%=vet.getUserId()%>" ><%=vet.getNameLast() %></option>
<%} %><%} %>
</select> <font color="red" >*</font>
</td>
</tr>

<tr>
<td colspan="2">2.<input value="" size="35" id="veterinario2" name="veterinario2"  type="hidden" />
<select id="veterinari2" name="veterinari2" onchange="set_vet( this, 'veterinario2'); ">
<option value="-1">Seleziona</option>
<%for (String gruppo : listaVeterinari.keySet()){ %>
<optgroup label="<%=gruppo %>"></optgroup>
<%for(Contact vet : listaVeterinari.get(gruppo)){ %>
<option value = "<%=vet.getUserId()%>" ><%=vet.getNameLast() %></option>
<%} %><%} %>
</select>
</td>
</tr>

<tr> 
<td colspan="2">3.<input value="" size="35" id="veterinario3" name="veterinario3"  type="hidden" />
<select id="veterinari3" name="veterinari3" onchange="set_vet( this, 'veterinario3');">
<option value="-1">Seleziona</option>
<%for (String gruppo : listaVeterinari.keySet()){ %>
<optgroup label="<%=gruppo %>"></optgroup>
<%for(Contact vet : listaVeterinari.get(gruppo)){ %>
<option value = "<%=vet.getUserId()%>" ><%=vet.getNameLast() %></option>
<%} %><%} %>
</select>
</td>
</tr>


</table>

<br/><br/>

<table class="details" style="border-collapse: collapse;table-layout:fixed;" width="50%">
<th colspan="2">Informazioni capi</th>
<tr><td class="formLabel">Specie capi</td> 
<td>
<table width="100%">
<tr><th> Specie </th> <th> Num. capi vivi </th> <th> Num. capi deceduti </th></tr>

<% Iterator iter = specieList.iterator();
	int i=0;
	while (iter.hasNext()) 
	{
	LookupElement thisElement = (LookupElement) iter.next();
	String specie = thisElement.getDescription(); %>
	
<tr><td><%= specie%></td> <td> <input <%=(specie.equals("Bovini")) ? "readonly" : "" %> type="text" id="num<%=specie %>" name="num<%=specie %>" size="3" value="0"/>  <input <%=(specie.equals("Bovini")) ? "disabled" : "" %> type="button" value="-" onClick="gestisciNumCapi('num<%=specie%>', '-')"/> <input type="button" value="+" onClick="gestisciNumCapi('num<%=specie%>', '+')"/> </td>
<dhv:evaluate if="<%=!(specie.equals("Bovini") )%>">
 <td> <input <%=(specie.equals("Bovini")) ? "readonly" : "" %> type="text" id="num_deceduti<%=specie %>" name="num_deceduti<%=specie %>" size="3" value="0"/>  <input <%=(specie.equals("Bovini")) ? "disabled" : "" %> type="button" value="-" onClick="gestisciNumCapi('num_deceduti<%=specie%>', '-')"/> <input type="button" value="+" onClick="gestisciNumCapi('num_deceduti<%=specie%>', '+')"/> </td>
 </dhv:evaluate>
</tr>
 
 <%} %>

</table></td></tr> </table>

<br/><br/>

<table class="details" style="border-collapse: collapse;table-layout:fixed;" id="tabellaDatiBovini" >
<tr><th colspan="14">Dettaglio capi</th></tr>
</table>
<input type="button" value="+" onClick="gestisciNumCapi('numBovini', '+')"/> 


<br/><br/>

<input type="button" value="INSERISCI PARTITA OK" onClick="checkForm(document.forms[0]);"/> 

</form>

</dhv:container>