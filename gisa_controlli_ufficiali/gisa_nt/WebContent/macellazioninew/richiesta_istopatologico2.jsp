<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>



<%@page import="org.aspcfs.modules.macellazioninew.utils.ConfigTipo"%>

<%@page
	import="org.aspcfs.modules.macellazioninew.base.Organi"%>
<jsp:useBean id="OrgDetails"
	class="org.aspcfs.modules.stabilimenti.base.Organization"
	scope="request" />

<jsp:useBean id="Capo" class="org.aspcfs.modules.macellazioninew.base.Partita"
	scope="request" />

<jsp:useBean id="OrganiList" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="OrganiListNew" class="java.util.TreeMap"
	scope="request" />



<jsp:useBean id="ASL" class="org.aspcfs.utils.web.LookupList"
	scope="request" />


<jsp:useBean id="CategorieBufaline"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Organi" class="org.aspcfs.utils.web.LookupList"
	scope="request" />


<jsp:useBean id="Patologie" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="PatologieOrgani"
	class="org.aspcfs.utils.web.LookupList" scope="request" />



<!-- Lookup istopatologico -->
<jsp:useBean id="listaDestinatariRichiestaIstopatologico"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_alimentazione"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_habitat"
	class="org.aspcfs.utils.web.LookupList" scope="request" />

<%@ include file="../initPage.jsp"%>

<link type="text/css" href="css/ui.all.css" rel="stylesheet" />
<link type="text/css" href="css/demos.css" rel="stylesheet" />
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/popCalendar.js"></script>
<script type="text/javascript" src="javascript/ui.core.js"></script>
<script type="text/javascript" src="javascript/ui.tabs.js"></script>

<!-- RELATIVO AL NUOVO CALENDARIO CON MESE E ANNO FACILMENTE MODIFICABILI -->
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
	document.write(getCalendarStyles());
</SCRIPT>

<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();

	function svuotaData(input) {
		input.value = '';
	}
</SCRIPT>
<link type="text/css" href="css/ui.all.css" rel="stylesheet" />
<link type="text/css" href="css/demos.css" rel="stylesheet" />
<script type="text/javascript">
<!--
function controllaForm(  )
{
	
	try{
		var form = document.main;
		message ="";
		ret = true;	
		
		
		//alert(document.getElementById("dataPrelievo").value);
		if (document.getElementById("dataPrelievo") == null ||
				document.getElementById("dataPrelievo").value == ""){
			message += label(""," La data di prelievo campioni non può essere vuota\r\n" );
			ret = false;
		}
		
		if (!(document.getElementById("idDestinatarioRichiesta").value > 0 )){
			message += label(""," Seleziona un destinatario per la richiesta\r\n" );
			ret = false;
		}
		
		
		 if (ret == false) {
			
      		alert(label("", "La form non puo' essere salvata, verifica i seguenti errori:\r\n\r\n") + message);
    	}
		 else{
			 document.main.submit();
		}

		
	  }
	catch(err)
	  {
		alert(err);
	  }

	return ret;
}
//-->

 $(document).ready(function() {
	
		$("#tabs").tabs().addClass('ui-tabs-vertical ui-helper-clearfix');
		$("#tabs li").removeClass('ui-corner-top').addClass('ui-corner-left');

}); 
</script>

<%
	String param1 = "orgId=" + OrgDetails.getOrgId();
	String param2 = "capoId=" + Capo.getId();
%>

<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="Stabilimenti.do"><dhv:label name="stabilimenti.stabilimenti">Accounts</dhv:label></a> >
		
					<a href="Stabilimenti.do?command=Search"><dhv:label
					name="stabilimenti.SearchResults">Search Results</dhv:label></a> >
					<a href="Stabilimenti.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Scheda Stabilimento</a> >
	
			
			<a href="MacellazioniNew.do?command=List&orgId=<%=OrgDetails.getOrgId()%>">Macellazioni Ovicaprini</a> > 
			<a href="MacellazioniNew.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>&id=<%=Capo.getId()%>">Dettaglio partita</a>			
		</td>
	</tr>
</table>

<dhv:container 
	name="<%=(OrgDetails.isMacelloUngulati()) ? ("stabilimenti_macellazioni_ungulati")
								: ("stabilimenti")%>"
	selected="macellazioni" 
	object="OrgDetails" 
	param="<%=param1%>" 
	appendToUrl='<%=addLinkParams(request, "popup|popupType|actionId")%>' 
>

	<br />


	<br />
	<br />
<div class="demo">
<div id="tabs">
<ul>
		<li id="li-1" ><a href="#tabs-1">Richiesta istopatologico</a></li>
	</ul>
<div id="tabs-1">


<form name="main" action="MacellazioniNew.do?command=SaveIstopatologico&auto-populate=true<%=addLinkParams(request, "popup|popupType|actionId")%>"
		method="post" onsubmit="return controllaForm();">
		
			<br></br>COMPILAZIONE SCHEDA ANAMNESTICA PER CAMPIONI PROVENIENTI
			DA ANIMALI DI REDDITO DA SOTTOPORRE AD ESAME ISTOPATOLOGICO
		<br></br>
		<div>
			Seleziona destinatario richiesta
			<%=listaDestinatariRichiestaIstopatologico.getHtmlSelect("idDestinatarioRichiesta", -1)%>
		</div>
		<div>
		<input type="hidden" name="idPartita" id="idPartita" value="<%=Capo.getId() %>" />
			Data prelievo: <input readonly type="text"
				name="dataPrelievo" size="10"
				value="<%=DateUtils.timestamp2string(Capo.getVpm_data())%>" id="dataPrelievo" />&nbsp;
			<a href="#"
				onClick="cal19.select(document.forms[0].dataPrelievo,'anchor19','dd/MM/yyyy'); return false;"
				NAME="anchor19" ID="anchor19"> <img
				src="images/icons/stock_form-date-field-16.gif" border="0"
				align="absmiddle"></a> <a style="cursor: pointer;"
				onclick="svuotaData(document.forms[0].dataPrelievo);"><img
				src="images/delete.gif" align="absmiddle" /></a> <font color="red">*</font>

			<div>Veterinario prelevatore:</div>
			<div>
				Cognome:
				<%=Capo.getVpm_veterinario()%>
				<input type= "hidden" name="nominativoVeterinarioPrelevatore" id="nominativoVeterinarioPrelevatore" value="<%=Capo.getVpm_veterinario() %>"/>
				</div>

			<br></br>
			<div class="sezione_macello">
				Luogo del prelievo:
				<table border="1" width="50%">
					<tr>
						<td>Macello<br> <%=OrgDetails.getName()%></td>
						<td>Numero di riconoscimento<br><%=OrgDetails.getApprovalNumber()%>
						</td>
						<td>Telefono<br><%=(OrgDetails.getPrimaryPhoneNumber() != null && !("").equals(OrgDetails
						.getPrimaryPhoneNumber())) ? OrgDetails.getPrimaryPhoneNumber() : "---"%></td>
					</tr>
				</table>

			</div>


			<br></br>
			
	<%-- <dhv:evaluate if ="<%=(idTipo == 1) %>">
		<div class="sezione_provenienza">
				Provenienza del capo:
				<table border="1" width="50%">
					<tr>
						<td>Specie<br> <%=Specie.getSelectedValue(Capo.getCd_specie())%><br>Razza<br><%=Razze.getSelectedValue(Capo.getCd_id_razza())%></td>
						<td>Ragione sociale allevamento<br><%=Capo.getCd_info_azienda_provenienza()%>
						<input type="hidden" value="<%=Capo.getCd_info_azienda_provenienza()%>" name="ragioneSocialeAllevamentoProvenienzaCapo" id="ragioneSocialeAllevamentoProvenienzaCapo" /></td></td>
						<td>Codice allevamento<br><%=Capo.getCd_codice_azienda_provenienza()%>
						<input type="hidden" value="<%=Capo.getCd_codice_azienda_provenienza()%>" name="codiceAllevamentoProvenienza" id="codiceAllevamentoProvenienza" /></td>
						<td>ASL provenienza Capo <br><%=(Capo.getIdAsl() > 0) ? ASL.getSelectedValue(Capo.getIdAsl()) : ASL.getHtmlSelect("IdAsl", -1)%>
						<input type="hidden" name="idAslProvenienzaCapo" id="idAslProvenienzaCapo" value="Capo.getIdAsl()" /> </td>
					</tr>
				</table>

			</div> 
			
	</dhv:evaluate> --%>
	

			<div class="sezione_provenienza">
				Dettagli partita:
				<table border="1" width="50%">
					<tr>
										<%String asl = "";
		/* 			 if (Capo.getCod_asl_azienda_prov() != null || !("").equals(Capo.getCod_asl_azienda_prov()))
						// asl = ASL.getSelectedValue(Capo.getCod_asl_azienda_prov()); */
					// else 
						if ((Capo.getCod_asl_azienda_prov() == null || ("").equals(Capo.getCod_asl_azienda_prov()) )&& Capo.getDenominazione_asl_azienda_prov() != null && !("").equals(Capo.getDenominazione_asl_azienda_prov()))
						 asl = Capo.getDenominazione_asl_azienda_prov();
					 
					%>
					    <td>Identificativo partita <br> <%=Capo.getCd_partita() %></td>
						<td>Numero caprini<br> <%=Capo.getCd_num_capi_caprini() %><br>Numero ovini<br><%=Capo.getCd_num_capi_ovini()%></td>
						<td>Ragione sociale allevamento<br><%=Capo.getRag_soc_azienda_prov()%>
						<input type="hidden" value="<%=Capo.getRag_soc_azienda_prov()%>" name="ragioneSocialeAllevamentoProvenienzaCapo" id="ragioneSocialeAllevamentoProvenienzaCapo" /></td></td>
						<td>Codice allevamento<br><%=Capo.getCd_codice_azienda_provenienza()%>
						<input type="hidden" value="<%=Capo.getCd_codice_azienda_provenienza()%>" name="codiceAllevamentoProvenienza" id="codiceAllevamentoProvenienza" /></td>
						<td>ASL provenienza Partita <br><%=(!("").equals(asl)) ? asl : ASL.getSelectedValue(Capo.getCod_asl_azienda_prov()) %>
						<input type="hidden" value="<%=(Capo.getCod_asl_azienda_prov() == null || ("").equals(Capo.getCod_asl_azienda_prov()) ) ? asl : "" %>" name="denominazioneAsl" id="denominazioneAsl"/>
						<input type="hidden" value="<%=Capo.getCod_asl_azienda_prov() %>" name="idAslProvenienzaCapo" id="idAslProvenienzaCapo"/>
					 </td>
					</tr>
				</table>

			</div>


		<%-- 	<br></br>
			<dhv:evaluate if="<%=(idTipo == 1) %>">
			<div class="sezione_animale">
				Segnalamento dell'animale:
				<table border="1" width="50%">
					<tr>
						<td>Categoria<br> <%=CategorieBovine.getSelectedValue(Capo.getCd_categoria_bovina())%></td>
						<td>Data di nascita<br><%=DateUtils.timestamp2string(Capo.getCd_data_nascita())%></td>
						<td>Sesso<br><%=(Capo.isCd_maschio()) ? "M" : "F"%></td>
						<td>Marca auricolare <br><%=Capo.getCd_matricola()%></td>
					</tr>
				</table>

			</div>
</dhv:evaluate>		 --%>	
<%-- 						<br></br> Commentato come da verbale 27/11/2014
			<div class="sezione_dati_generici_animale">
				Dati generici sulla partita:
				<table border="1" width="50%">
					<tr>
						<td>Habitat<br><%=lookup_habitat.getHtmlSelect("idHabitatAnimale", -1)%></td>
						<td>Alimentazione<br><%=lookup_alimentazione.getHtmlSelect("idAlimentazioneAnimale", -1)%>
						<!-- <td>Peso:<br><input type="text" value="" name="pesoAnimale" id="pesoAnimale"> -->
					</tr>
				</table>

			</div>
 --%>

			<br></br>
			<div class="sezione_organi_prelevati">
				Organi prelevati:
				<table border="1" width="50%">
					<tr>
						<th>Organo</th>
						<th>Topografia</th>
						<th>Interessamento altri organi</th>
					</tr>
					<%
						Iterator i = OrganiList.iterator();
							while (i.hasNext()) {
								Organi thisOrgano = (Organi) i.next();
					%>

					<tr>
						<td style="text-align: center;"><%=Organi.getSelectedValue(thisOrgano.getLcso_organo())%><input type="hidden" value="<%=thisOrgano.getId()%>" name="organi_id_<%=thisOrgano.getId()%>" id="organi_id_<%=thisOrgano.getId()%>"/></td>
						<td style="text-align: center;"><input type="text" name="istopatologico_topografia_<%=thisOrgano.getId()%>" id="istopatologico_topografia_<%=thisOrgano.getId()%>" /></td>
						<td style="text-align: center;"><input type="text" name="istopatologico_interessamento_altri_organi_<%=thisOrgano.getId()%>" id="istopatologico_interessamento_altri_organi_<%=thisOrgano.getId()%>" /></td>
						

					</tr>
					<%
						}
					%>
				</table>

			</div>
<br></br>
			<div class="sezione_osservazioni_veterinario">
				Osservazioni del veterinario:<br>
<textarea rows="8" cols="80" name="noteRichiesta" id="noteRichiesta"></textarea>
			</div>




		</div>
		
<input type="submit" value="Salva" >
	</form></div></div></div>
</dhv:container>



