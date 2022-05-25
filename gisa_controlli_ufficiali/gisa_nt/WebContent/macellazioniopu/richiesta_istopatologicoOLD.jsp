<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>


<%@page
	import="org.aspcfs.modules.macellazioniopu.base.Casl_Non_Conformita_Rilevata"%>
<%@page import="org.aspcfs.modules.macellazioniopu.base.ProvvedimentiCASL"%>
<%@page import="org.aspcfs.modules.macellazioniopu.base.NonConformita"%>
<%@page import="org.aspcfs.modules.macellazioniopu.base.Campione"%><%@page
	import="org.aspcfs.modules.macellazioniopu.base.Organi"%>
<%@page import="org.aspcfs.modules.macellazioniopu.base.PatologiaRilevata"%>

<%@page import="org.aspcfs.modules.util.imports.ApplicationProperties"%>

<%@page import="org.aspcfs.modules.macellazioniopu.base.TipoRicerca"%><jsp:useBean
	id="Tampone" class="org.aspcfs.modules.macellazioniopu.base.Tampone"
	scope="request" />

<jsp:useBean id="OrgDetails"
	class="org.aspcfs.modules.opu.base.Stabilimento"
	scope="request" />
<jsp:useBean id="Speditore"
	class="org.aspcfs.modules.speditori.base.Organization" scope="request" />
<jsp:useBean id="SpeditoreAddress"
	class="org.aspcfs.modules.speditori.base.OrganizationAddress"
	scope="request" />
<jsp:useBean id="Capo" class="org.aspcfs.modules.macellazioniopu.base.Capo"
	scope="request" />
<jsp:useBean id="NCVAM" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="Campioni" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="OrganiList" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="OrganiListNew" class="java.util.TreeMap"
	scope="request" />
<jsp:useBean id="PatologieRilevate" class="java.util.ArrayList"
	scope="request" />
<jsp:useBean id="casl_NCRilevate" class="java.util.ArrayList"
	scope="request" />
<jsp:useBean id="MatriciTamponi" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="AnalisiTamponi" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="PianiMonitoraggio"
	class="org.aspcfs.utils.web.LookupList" scope="request" />

<jsp:useBean id="casl_Provvedimenti_effettuati"
	class="java.util.ArrayList" scope="request" />

<jsp:useBean id="Nazioni" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="Matrici" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="ASL" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="LuoghiVerifica" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="Regioni" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="Razze" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="Specie" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="CategorieBovine"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="CategorieBufaline"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="PianiRisanamento"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="BseList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="TipiMacellazione"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="EsitiVpm" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="Patologie" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="PatologieOrgani"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Azioni" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="Stadi" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="Organi" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="TipiAnalisi" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="Molecole" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="TipiNonConformita"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="MotiviASL" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="ProvvedimentiVAM"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="look_ProvvedimentiCASL"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="EsitiCampioni" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="MotiviCampioni" class="org.aspcfs.utils.web.LookupList"
	scope="request" />


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

<script type="text/javascript">
<!--
function controllaForm(  )
{
	
	try{
		var form = document.main;
		message ="";
		ret = true;	
		
		
		alert(document.getElementById("cdDataPrelievoCampioni").value);
		if (document.getElementById("cdDataPrelievoCampioni") == null ||
				document.getElementById("cdDataPrelievoCampioni").value == ""){
			message += label(""," La data di prelievo campioni non può essere vuota\r\n" );
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
</script>

<%
	String param1 = "altId=" + OrgDetails.getAltId()+"&stabId=" + OrgDetails.getIdStabilimento(); request.setAttribute("Operatore",OrgDetails.getOperatore());
	String param2 = "capoId=" + Capo.getId();
%>
<dhv:container
	name="<%=(OrgDetails.isMacelloUngulati()) ? ("stabilimenti_macellazioni_ungulati") : ("stabilimenti")%>"
	selected="macellazioni" object="Operatore" param="<%=param1%>"
	appendToUrl='<%=addLinkParams(request, "popup|popupType|actionId")%>'
	hideContainer="<%=!OrgDetails.getEnabled() || OrgDetails.isTrashed()%>">

	<br />


	<br />
	<br />




	<form name="main" action="MacellazioniOpu.do?command=SaveIstopatologico&auto-populate=true<%=addLinkParams(request, "popup|popupType|actionId")%>"
		method="post" onsubmit="return true">
		<div class="demo">
			REGISTRO TUMORI<br></br> SCHEDA ANAMNESTICA PER CAMPIONI PROVENIENTI
			DA ANIMALI DI REDDITO DA SOTTOPORRE AD ESAME ISTOPATOLOGICO
		</div>
		<br></br>
		<div>
			Seleziona destinatario richiesta
			<%=listaDestinatariRichiestaIstopatologico.getHtmlSelect("idDestinatarioRichiesta", -1)%>
		</div>
		<div>
		<input type="hidden" name="idCapo" id="idCapo" value="<%=Capo.getId() %>" />
			Data prelievo: <input readonly type="text"
				name="dataPrelievo" size="10"
				value="<%=DateUtils.timestamp2string(Capo.getVpm_data())%>" id="dataPrelievo" />&nbsp;
			<a href="#"
				onClick="cal19.select(document.forms[0].cd_data_prelievo_campioni,'anchor19','dd/MM/yyyy'); return false;"
				NAME="anchor19" ID="anchor19"> <img
				src="images/icons/stock_form-date-field-16.gif" border="0"
				align="absmiddle"></a> <a style="cursor: pointer;"
				onclick="svuotaData(document.forms[0].cd_data_prelievo_campioni);"><img
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
			<div class="sezione_provenienza">
				Provenienza del capo:
				<table border="1" width="50%">
					<tr>
						<td>Specie<br> <%=Specie.getSelectedValue(Capo.getCd_specie())%><br>Razza<br><%=Razze.getSelectedValue(Capo.getCd_id_razza())%></td>
						<td>Ragione sociale allevamento<br><%=Capo.getCd_info_azienda_provenienza()%>
						<input type="hidden" value="<%=Capo.getCd_info_azienda_provenienza()%>" name="ragioneSocialeAllevamentoProvenienzaCapo" id="ragioneSocialeAllevamentoProvenienzaCapo" /></td></td>
						<td>Codice allevamento<br><%=Capo.getCd_codice_azienda_provenienza()%>
						<input type="hidden" value="<%=Capo.getCd_codice_azienda_provenienza()%>" name="codiceAllevamentoProvenienza" id="codiceAllevamentoProvenienza" /></td>
						<td>ASL provenienza Capo <br><%=(Capo.getIdAsl() > 0) ? ASL.getSelectedValue(Capo.getIdAsl()) : "_______"%>
						<input type="hidden" name="idAslProvenienzaCapo" id="idAslProvenienzaCapo" value="Capo.getIdAsl()" /> </td>
					</tr>
				</table>

			</div>

			<br></br>
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
			
						<br></br>
			<div class="sezione_dati_generici_animale">
				Dati generici sul capo:
				<table border="1" width="50%">
					<tr>
						<td>Habitat<br><%=lookup_habitat.getHtmlSelect("idHabitatAnimale", -1)%></td>
						<td>Alimentazione<br><%=lookup_alimentazione.getHtmlSelect("idAlimentazioneAnimale", -1)%>
						<td>Peso:<br><input type="text" value="" name="pesoAnimale" id="pesoAnimale">
					</tr>
				</table>

			</div>


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
	</form>
</dhv:container>

