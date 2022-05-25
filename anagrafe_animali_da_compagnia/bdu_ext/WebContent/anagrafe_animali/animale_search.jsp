<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.utils.web.LookupElement"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ page
	import="java.util.*,org.aspcfs.modules.assets.base.*,java.text.DateFormat"%>

<jsp:useBean id="animaliListInfo"
	class="org.aspcfs.utils.web.PagedListInfo" scope="session" />
<jsp:useBean id="razzaList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="tagliaList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="mantelloList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="specieList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="Animale"
	class="org.aspcfs.modules.anagrafe_animali.base.Animale"
	scope="request" />
<jsp:useBean id="annoList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="comuniList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="statoList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="utentiList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />

<%@ include file="../initPage.jsp"%>

<!-- REVEAL MODAL POPUP -->
<link rel="stylesheet" href="javascript/reveal/reveal.css">
<script src="javascript/jquery-1.8.2.js" type="text/javascript"></script>
<script src="javascript/reveal/jquery.reveal.js" type="text/javascript"></script>
<!-- FINE REVEAL MODAL POPUP -->

<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/popCalendar.js"></script>
<script language="JavaScript">
  function clearForm() {
	    
	    var frm_elements = searchAnimale.elements;
	    // frm_elements.searchcodeId.value="";
	    //frm_elements.searchcodeidTipologiaEvento.value="-1";
	    frm_elements.searchexactmicrochip.value="";
	    frm_elements.searchcodeidAnimale.value="";
	    frm_elements.searchexactnumeroPassaporto.value="";
	    frm_elements.searchexacttatuaggio.value="";
	    frm_elements.searchexactsesso.value="F"; //??
	    frm_elements.searchdenominazioneProprietario.value="";
	    frm_elements.searchexactcodiceFiscaleProprietario.value="";
	    frm_elements.searchexactpartitaIvaProprietario.value="";
	    frm_elements.searchnomeComuneProprietario.value="";
	    frm_elements.searchdenominazioneDetentore.value="";
	    frm_elements.searchexactcodiceFiscaleDetentore.value="";
	    frm_elements.searchexactpartitaIvaDetentore.value="";
	    frm_elements.searchnomeComuneDetentore.value="";
	    frm_elements.searchcodeidRazza.value="-1";
	    frm_elements.searchcodeidMantello.value="-1";
	    frm_elements.searchcodeidTaglia.value="-1";
	    frm_elements.searchcodeidStato.value="-1";
	    frm_elements.searchcodeidAsl.value="-1";
	    frm_elements.searchexactanno.value="-1";
	    frm_elements.searchcodeidSpecie.value="-1";
	    document.getElementById("searchcodeidUtenteInserimento").value=-1;
	  

	  //pulisce il form
	  	if (document.forms['searchAnimale'].vendorCode){
	    	document.forms['searchAnimale'].vendorCode.options.selectedIndex = 0;
	    }
	    if (document.forms['searchAnimale'].manufacturerCode){
	    	document.forms['searchAnimale'].manufacturerCode.options.selectedIndex = 0;
	    }
	    if (document.forms['searchAnimale'].serialNumber){
	    	document.forms['searchAnimale'].serialNumber.value='';
	    }
	    document.forms['searchAnimale'].location.value='';
	    document.forms['searchAnimale'].status.options.selectedIndex = 0;
	    document.forms['searchAnimale'].serialNumber.focus();
	    }
	
 
  	
  
  </script>
  <script language="JavaScript">
	function checkSpecieAnimale(obj){
		document.forms[0].doContinue.value = 'false';
		document.forms[0].submit();

		
	}
  
</script>

<script type="text/javascript">
function openRichiesta(idAnimale, idSpecie){
	var res;
	var result;
		window.open('AnimaleAction.do?command=PrintRichiestaIscrizione&idAnimale='+idAnimale+'&idSpecie='+idSpecie+'&isEmpty=true','popupSelect',
		'height=595px,width=842px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}
</script>

<script type="text/javascript">
function openCertificato(idAnimale, idSpecie){
	var res;
	var result;
		window.open('AnimaleAction.do?command=PrintCertificatoIscrizione&idAnimale='+idAnimale+'&idSpecie='+idSpecie+'&isEmpty=true','popupSelect',
		'height=595px,width=842px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}
</script>

<script type="text/javascript">
function openRichiestaAdozione(){
	var res;
	var result;
		window.open('AnimaleAction.do?command=PrintRichiestaAdozione&isEmpty=true','popupSelect',
		'height=595px,width=842px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}
</script>


<!-- RIMUOVERE APICI DA MICROCHIP INCOLLATO -->
<script>
$(document).ready(
function pulisciMicrochip() {
	var mc = document.getElementById('searchexactmicrochip'),
    cleanMicrochip;
	cleanMicrochip= function(e) {
    e.preventDefault();
    var pastedText = '';
    if (window.clipboardData && window.clipboardData.getData) { // IE
        pastedText = window.clipboardData.getData('Text');
      } else if (e.clipboardData && e.clipboardData.getData) {
        pastedText = e.clipboardData.getData('text/plain');
      }
    var mc_gestito = pastedText;
    mc_gestito = mc_gestito.split('"').join(''); //rimozione apici
    mc_gestito = mc_gestito.split(' ').join(''); //rimozione spazi
    mc.value = mc_gestito;
};

mc.onpaste = cleanMicrochip;
	});</script>
	<!-- RIMUOVERE APICI DA MICROCHIP INCOLLATO -->

<body onload="javascript:init();clearForm();">
<dhv:permission name="anagrafe_canina-documenti-view">
<dhv:evaluate if="<%=User.getRoleId() != 24%>">
	<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle"
		height="16" width="16" />
	<a href="#" onclick="openCertificato('<%=-1%>','<%=-1%>');" id=""
		target="_self">Certificato di Iscrizione da compilare</a>
</dhv:evaluate>
	<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle"
		height="16" width="16" />
	<a href="#" onclick="openRichiesta('<%=-1%>','<%=-1%>');" id=""
		target="_self">Richiesta di iscrizione da compilare</a>
	<dhv:evaluate if="<%=User.getRoleId() != 24%>">
		<img src="images/icons/stock_print-16.gif" border="0"
			align="absmiddle" height="16" width="16" />
		<a href="#" onclick="openRichiestaAdozione();" id="" target="_self">
		Richiesta di Adozione da rifugio da compilare</a>
	</dhv:evaluate>

</dhv:permission>
<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td width="100%"><a href="AnimaleAction.do"><dhv:label
			name="">Anagrafe animali</dhv:label></a> > <dhv:label name="">Cerca animale</dhv:label>
		</td>
	</tr>
</table>
<%-- End Trails --%>
<form name="searchAnimale"
	action="AnimaleAction.do?command=Search&auto-populate=true" onSubmit=""
	method="post"><input type="hidden" name="doContinue"
	id="doContinue" value="" />
<table cellpadding="2" cellspacing="2" border="0" width="100%">
	<tr>
		<td width="50%" valign="top">

		<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">
			<tr>
				<th colspan="2"><strong><dhv:label
					name="accounts.accountasset_include.SpecificInformation">Specific Information</dhv:label></strong>
				</th>
			</tr>
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Specie animale</dhv:label>
				</td>
				<td>
				<%
					specieList.setJsEvent("onChange=\"javascript:checkSpecieAnimale(this);\"");
				
				%> 
				<%=specieList.getHtmlSelect("searchcodeidSpecie",animaliListInfo.getCriteriaValue("searchcodeidSpecie"))%>
				<%=showAttribute(request, "idSpecieCodeError")%></td>
			</tr>
			<tr class="containerBody">
				<td class="formLabel">Anno</td>
				<td><%=annoList.getHtmlSelect("searchexactanno", new Integer(annoList.getDefaultElementCode()))%>
				</td>

			</tr>
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Microchip</dhv:label>
				</td>
				<td><input type="text" size="20" maxlength="15" id="searchexactmicrochip"
					name="searchexactmicrochip" maxlength="30"
					value="<%=toHtmlValue(Animale.getMicrochip())%>"> <%=showAttribute(request, "microchipError")%></td>
			</tr>

			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Identificativo animale</dhv:label>
				</td>
				<td><input type="text" size="20" maxlength="15"
					name="searchcodeidAnimale" maxlength="30"
					value="<%=(Animale.getIdAnimale() != 0) ? toHtmlValue(Animale
					.getIdAnimale()) : ""%>">
				</td>
			</tr>
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Passaporto</dhv:label></td>
				<td><input type="text" size="20" maxlength="30"
					name="searchexactnumeroPassaporto" maxlength="30" value="">
				</td>
			</tr>

			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Tatuaggio</dhv:label>
				</td>
				<td><input type="text" size="20" name="searchexacttatuaggio"
					maxlength="30" value=""> <%=showAttribute(request, "tatuaggioError")%></td>
			</tr>
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Razza</dhv:label></td>
				<td><%=razzaList.getHtmlSelect("searchcodeidRazza", Animale
							.getIdRazza())%>
				<%=showAttribute(request, "idRazzaCodeError")%></td>
			</tr>


			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Sesso</dhv:label></td>
				<td><%--	<%= sessoList.getHtmlSelect("location", asset.getSessoCode()) %> --%>
				<input type="radio" name="searchexactsesso" value="M"
					id="searchsesso"
					<%=("maschio".equals((String) request
									.getParameter("sesso"))) ? " checked" : ""%>>
				<dhv:label name="cani.sesso.maschio">M</dhv:label> <input
					type="radio" name="searchexactsesso" value="F" id="searchsesso"
					<%="femmina".equals((String) request
									.getParameter("sesso")) ? " checked" : ""%>>
				<dhv:label name="cani.sesso.femmina">F</dhv:label> <input
					type="radio" name="searchexactsesso" value="" id="searchsesso"
					<%=(request.getParameter("sesso") == null || ""
									.equals((String) request
											.getParameter("sesso"))) ? " checked"
							: ""%>>
				<dhv:label name="">Tutti</dhv:label></td>
			</tr>

			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Mantello</dhv:label></td>
				<td><%=mantelloList.getHtmlSelect("searchcodeidMantello",
							Animale.getIdTipoMantello())%>
				</td>
			</tr>
			
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Taglia</dhv:label></td>
				<td><%=tagliaList.getHtmlSelect("searchcodeidTaglia",
							-1)%>
				</td>
			</tr>

			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Animali Viventi/Deceduti</dhv:label></td>
				<td><select name="searchexactflagDecesso" id="searchexactflagDecesso">
					<option value="-1">Tutti</option>
					<option value="1">Viventi</option>
					<option value="2">Deceduti</option>
				</select>
				 </td>
			</tr>

			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Stato animale</dhv:label></td>
				<td><%=statoList.getHtmlSelect("searchcodeidStato", 2)%> &nbsp;
				<a href="#" data-reveal-id="guida_stati" data-animation="fade" data-animationspeed="300" 
				data-closeonbackgroundclick="true" 
				data-dismissmodalclass="close-reveal-modal"><img src="javascript/reveal/Question_Mark.png"></img></a>
</td>

			</tr>
			<tr>
				<td nowrap class="formLabel"><dhv:label name="">Asl di Riferimento</dhv:label>
				</td>
				
				<td>
				<dhv:evaluate if="<%=(User.getSiteId() > -1)%>">
					<%=AslList.getHtmlSelect("searchcodeidAsl", User
								.getSiteId())%>
				</dhv:evaluate> 
				<dhv:evaluate if="<%=(User.getSiteId() == -1)%>">
					<%=AslList.getHtmlSelect("searchcodeidAsl", -1)%>

				</dhv:evaluate>
				<!-- UTENTE COMUNE, ASL SETTATA -->
				
				
				 <b><font color="red"><label id="utProv" /></font></b> <input
					type="hidden" name="provincia" /> <input type="hidden"
					name="aslRifFReg" /></td>
			</tr>
	<dhv:evaluate if="<%=(User.getRoleId() != 24) %>" >
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Utente inserimento</dhv:label></td>
				<td><%=utentiList.getHtmlSelect("searchcodeidUtenteInserimento",
							Animale.getIdUtenteInserimento())%>
				</td>
			</tr>
	</dhv:evaluate>
	
	<dhv:evaluate if="<%=(User.getRoleId() == 24) %>" >
		<input type="hidden" value="<%=User.getUserId() %>" name="searchcodeidUtenteInserimento" id="searchcodeidUtenteInserimento"/>
	</dhv:evaluate>
		</table>



		</td>

		<td width="50%" valign="top"><!-- FILTRI DI RICERCA -->

		<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">
			<tr>
				<th colspan="2"><strong><dhv:label name="">Filtri Informazioni Proprietario</dhv:label></strong>
				</th>
			</tr>
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Denominazione</dhv:label>
				</td>

				<td><input type="text" size="20"
					name="searchdenominazioneProprietario" maxlength="30" value="">
				</td>
			</tr>
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Codice Fiscale</dhv:label>
				</td>
				<td><input type="text" size="20"
					name="searchexactcodiceFiscaleProprietario" maxlength="16" value="">
				</td>
			</tr>
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Partita Iva</dhv:label>
				</td>
				<td><input type="text" size="20"
					name="searchexactpartitaIvaProprietario" maxlength="30" value="">
				</td>
			</tr>
			<dhv:evaluate if= "<%=(User.getRoleId() != new Integer(ApplicationProperties.getProperty("ID_RUOLO_UTENTE_COMUNE"))) %>">
				<tr class="containerBody">
					<td class="formLabel"><dhv:label name="">Comune</dhv:label></td>
					<td>
						<dhv:evaluate if= "<%=(User.getRoleId() != new Integer(ApplicationProperties.getProperty("ID_RUOLO_UTENTE_COMUNE"))) %>">
							<input type="text" size="20" name="searchnomeComuneProprietario" maxlength="30" value="">
						</dhv:evaluate>
					</td>
				</tr>
			</dhv:evaluate>
			<dhv:evaluate if= "<%=(User.getRoleId() == new Integer(ApplicationProperties.getProperty("ID_RUOLO_UTENTE_COMUNE"))) %>">
				<input type="hidden" size="20" name="searchnomeComuneProprietario" value="<%=comuniList.getSelectedValue(User.getUserRecord().getIdComune()) %>">
			</dhv:evaluate>	
			<tr>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
			</tr>

			<tr>
				<th colspan="2"><strong><dhv:label name="">Filtri Informazioni Detentore</dhv:label></strong>
				</th>
			</tr>
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Denominazione</dhv:label>
				</td>
				<td><input type="text" size="20"
					name="searchdenominazioneDetentore" maxlength="30" value="">
				</td>
			</tr>
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Codice Fiscale</dhv:label>
				</td>
				<td><input type="text" size="20"
					name="searchexactcodiceFiscaleDetentore" maxlength="16" value="">
				</td>
			</tr>
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Partita Iva</dhv:label>
				</td>
				<td><input type="text" size="20"
					name="searchexactpartitaIvaDetentore" maxlength="30" value="">
				</td>
			</tr>
			
			<%-- 
			<dhv:evaluate if= "<%=(User.getRoleId() != new Integer(ApplicationProperties.getProperty("ID_RUOLO_UTENTE_COMUNE"))) %>">
				<tr class="containerBody">
					<td class="formLabel"><dhv:label name="">Comune</dhv:label></td>
					<td>
						<dhv:evaluate if= "<%=(User.getRoleId() != new Integer(ApplicationProperties.getProperty("ID_RUOLO_UTENTE_COMUNE"))) %>">
							<input type="text" size="20" name="searchnomeComuneDetentore" maxlength="30" value="">
						</dhv:evaluate>
					</td>
				</tr>
			</dhv:evaluate>
			<dhv:evaluate if= "<%=(User.getRoleId() == new Integer(ApplicationProperties.getProperty("ID_RUOLO_UTENTE_COMUNE"))) %>">
				<input type="hidden" size="20" name="searchnomeComuneDetentore" value="<%=comuniList.getSelectedValue(User.getUserRecord().getIdComune()) %>">
			</dhv:evaluate>	
			--%>
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Comune</dhv:label></td>
				<td>
					<input type="text" size="20" name="searchnomeComuneDetentore" maxlength="30" value="">
				</td>
			</tr>	
			
		</table>

		</td>
</table>
<br />
<input type="submit"
	value="<dhv:label name="button.search">Search</dhv:label>"> <input
	type="button" value="<dhv:label name="button.clear">Clear</dhv:label>"
	onClick="javascript:clearForm();"></form>
	
	<div id="guida_stati" class="reveal-modal">
     <h1>Guida agli stati degli animali</h1>
     <table border="1">
     <%
     statoList.remove(0);
     Iterator i = statoList.iterator();
     while (i.hasNext()){
    	 LookupElement el = (LookupElement) i.next();
    	 if (el.isGroup() && el.isEnabled()){
    	 %>
    	 <tr bgcolor="gray"> <th colspan="2"><%=el.getDescription() %></th> </tr>
    	<%}else if (el.isEnabled()){ %>
     <tr><td><p><strong><%=el.getDescription() %></strong></td><td> <%=el.getTitle() %></p>  </td></tr>
     
     <%}} %> 
     </table>         
     <a class="close-reveal-modal">&#215;</a>
</div>
</body>