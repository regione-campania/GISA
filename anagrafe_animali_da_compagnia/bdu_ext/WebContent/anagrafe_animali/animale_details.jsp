<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.schedaMorsicatura.base.SchedaMorsicatura"%>
<%@page import="org.aspcfs.modules.ws.WsPost"%>
<%@page import="org.aspcfs.modules.schedaAdozioneCani.base.SchedaAdozione"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@page	import="org.aspcfs.modules.admin.base.User,org.aspcfs.modules.anagrafe_animali.base.LeishList"%>
<%@page	import="org.aspcfs.modules.registrazioniAnimali.base.EventoRegistrazioneBDU,org.aspcfs.modules.opu.base.*"%>
<%@page import="org.aspcfs.modules.anagrafe_animali.base.Cane"%>
<%@page import="org.aspcfs.modules.anagrafe_animali.base.Furetto"%>
<%@page import="org.aspcfs.modules.anagrafe_animali.base.Gatto,org.aspcfs.modules.opu.base.*"%>
<%@page import="org.aspcfs.modules.anagrafe_animali.base.Animale"%>
<%@page import="org.aspcfs.modules.base.Constants"%>
<%@page	import="org.aspcfs.modules.partitecommerciali.base.PartitaCommerciale"%>

<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>

<jsp:useBean id="actionFrom" class="java.lang.String" scope="request" />
<jsp:useBean id="idLinea" class="java.lang.String" scope="request" />
<jsp:useBean id="origine" class="java.lang.String" scope="request" />
<jsp:useBean id="caneDettaglio" class="org.aspcfs.modules.anagrafe_animali.base.Cane" scope="request" />
<jsp:useBean id="gattoDettaglio" class="org.aspcfs.modules.anagrafe_animali.base.Gatto" scope="request" />
<jsp:useBean id="furettoDettaglio" class="org.aspcfs.modules.anagrafe_animali.base.Furetto" scope="request" />
<jsp:useBean id="animaleDettaglio" class="org.aspcfs.modules.anagrafe_animali.base.Animale"	scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="regioniList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="specieList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="razzaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="tagliaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="mantelloList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="statoList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="comuniList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="provinceList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="nazioniList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="esitoControlloList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="esitoControlloLabList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="listaPratiche" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="veterinariList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="partita" class="org.aspcfs.modules.partitecommerciali.base.PartitaCommerciale"	scope="request" />
<jsp:useBean id="wkf" class="org.aspcfs.modules.registrazioniAnimali.base.RegistrazioniWKF"	scope="request" />
<jsp:useBean id="dati_antirabbica" class="org.aspcfs.modules.registrazioniAnimali.base.EventoInserimentoVaccinazioni" scope="request" />
<jsp:useBean id="prelievoLeish" class="org.aspcfs.modules.registrazioniAnimali.base.EventoPrelievoLeishmania" scope="request" />	
<jsp:useBean id="dati_cessione" class="org.aspcfs.modules.registrazioniAnimali.base.EventoCessione"	scope="request" />
<jsp:useBean id="rilascioPassaporto" class="org.aspcfs.modules.registrazioniAnimali.base.EventoRilascioPassaporto" scope="request" />
<jsp:useBean id="evento" class="org.aspcfs.modules.registrazioniAnimali.base.EventoRegistrazioneBDU" scope="request" />
<jsp:useBean id="schede"      class="java.util.ArrayList" scope="request" />
<jsp:useBean id="schedeMorsi" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="valutazione"      class="org.aspcfs.modules.schedaAdozioneCani.base.Valutazione" scope="request" />
<jsp:useBean id="valutazioneMorso" class="org.aspcfs.modules.schedaMorsicatura.base.Valutazione" scope="request" />

<%
	WsPost ws = request.getAttribute("ws")!=null ? ((WsPost)request.getAttribute("ws")) : (null);
%>



<%
String param1 = "idAnimale=" + animaleDettaglio.getIdAnimale() + "&idSpecie=" + animaleDettaglio.getIdSpecie();
EventoRegistrazioneBDU eventoF = (EventoRegistrazioneBDU) evento;
%>

<script type="text/javascript">
function sendIdEventoToVam(){
	parent.postMessage("<%=animaleDettaglio.getIdAnimale()%>","*");
}

<%if (session.getAttribute("caller") != null && !("").equals(session.getAttribute("caller"))
					&& session.getAttribute("idTipologiaEvento") != null
					&& !("").equals(session.getAttribute("idTipologiaEvento"))) {
				//Devo controllare anche se ho idTipologiaEvento xkè potrei venire da accesso bdr%>
$('a').removeAttr('href');
$('table').on( "click", "a", function(e) {
	//alert('aaa');
	
    e.preventDefault();
    //do other stuff when a click happens
});

//sendIdEventoToVam();

var idEvento = <%=animaleDettaglio.getIdAnimale()%>;
$.getScript("javascript/comunicazioniToVamChangeDomain.js")

<%}%>
</script>


<%@ include file="../initPage.jsp"%>


<form name="detailsAnimale"
	action="AnimaleAction.do?command=Modify&id=<%=animaleDettaglio.getIdAnimale()%>&return=details<%=addLinkParams(request, "popup|popupType|actionId")%>"
	method="post">
	<dhv:evaluate if="<%=!isPopup(request)%>">
		<%-- Trails --%>
		<table class="trails" cellspacing="0">
			<tr>
				<td width="100%"><dhv:evaluate
						if="<%=("AnimaleAction").equals(actionFrom)%>">
						<dhv:evaluate if="<%=(origine != null && !("").equals(origine))%>">
							<a
								href="PartiteCommerciali.do?command=DettagliPartita&idPartita=<%=partita.getIdPartitaCommerciale()%>"><dhv:label
									name="">Dettagli partita commerciale</dhv:label></a> >				
				<a
								href="PartiteCommerciali.do?command=ListaAnimali&idPartita=<%=partita.getIdPartitaCommerciale()%>&idSpecie=<%=partita.getIdTipoPartita()%>"><dhv:label
									name="">Lista animali</dhv:label></a> > <dhv:label name="">Dettagli animale</dhv:label>
						</dhv:evaluate>
						<dhv:evaluate
							if="<%=!(origine != null && !("").equals(origine))%>">
							<a href="AnimaleAction.do"><dhv:label name="">Animale</dhv:label></a> > 
					<dhv:evaluate
								if="<%=User.getRoleId() != new Integer(ApplicationProperties.getProperty("ID_RUOLO_LLP"))%>">
								<a href="AnimaleAction.do?command=Search"><dhv:label
										name="accounts.SearchResults">Search Results</dhv:label></a> > </dhv:evaluate>
							<dhv:label name="">Dettagli animale</dhv:label>
						</dhv:evaluate>
					</dhv:evaluate> <dhv:evaluate
						if="<%=("RegistrazioniAnimaleCessioni").equals(actionFrom)%>">
						<a
							href="RegistrazioniAnimaleCessioni.do?command=Search&searchcodeidTipologiaEvento=7&searchexacttype=in&searchexactstato=opened">
							<dhv:label name="anagrafica.animale">Cessioni incompiute</dhv:label>
						</a> > <dhv:label name="">Dettagli animale</dhv:label>
					</dhv:evaluate>
					 <dhv:evaluate
						if="<%=("LineaProduttivaAction").equals(actionFrom)%>">
						<a href="OperatoreAction.do?command=Search"><dhv:label name="">Risultati ricerca</dhv:label></a> > <a
					href="OperatoreAction.do?command=Details&opId=<%=idLinea%>"> <dhv:label
						name="">Dettaglio operatore</dhv:label></a> >
						<a
					href="LineaProduttivaAction.do?command=ListaAnimali&idLinea=<%=idLinea%>&popup=false"><dhv:label name="">Lista animali</dhv:label></a>  >
					<dhv:label name="">Dettagli animale</dhv:label>
					</dhv:evaluate>
					</td>
							
			</tr>
		</table>
		<%-- End Trails --%>
	</dhv:evaluate>
	<script language="javascript">
function popupViewEsitiLeishmania(id) {
	  title  = '_types';
	  width  =  '500';
	  height =  '450';
	  resize =  'yes';
	  bars   =  'no';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('AnimaleAction.do?popup=true&command=ViewEsitiLeishmania&animaleId='+id, title, windowParams);
	  //newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}




</script>


	<script language="JavaScript" TYPE="text/javascript"
		SRC="gestione_documenti/generazioneDocumentale.js"></script>

<script type="text/javascript">
function openRichiesta(idAnimale, idSpecie){
	var res;
	var result;
		window.open('AnimaleAction.do?command=PrintRichiestaIscrizione&idAnimale='+idAnimale+'&idSpecie='+idSpecie,'popupSelect',
		'height=595px,width=842px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}

function openCertificato(idAnimale, idSpecie){
	var res;
	var result;
		window.open('AnimaleAction.do?command=PrintCertificatoIscrizione&idAnimale='+idAnimale+'&idSpecie='+idSpecie,'popupSelect',
		'height=595px,width=842px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}


function openObblighi(idAnimale){
	var res;
	var result;
		window.open('AnimaleAction.do?command=PrintObblighiProprietario&idAnimale='+idAnimale,'popupSelect',
		'height=595px,width=842px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}

function openCampioni(idAnimale, idSpecie){
	var res;
	var result;
		window.open('AnimaleAction.do?command=PrintRichiestaCampioni&idAnimale='+idAnimale+'&idSpecie='+idSpecie,'popupSelect',
		'height=595px,width=842px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}

function openCampioniRabbia(idAnimale, idSpecie){
	var res;
	var result;
		window.open('AnimaleAction.do?command=PrintRichiestaCampioniRabbia&idAnimale='+idAnimale+'&idSpecie='+idSpecie,'popupSelect',
		'height=595px,width=842px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}
</script>
	<dhv:container name="animale" selected="details"
		object="animaleDettaglio" param="<%=param1%>"
		appendToUrl='<%=addLinkParams(request, "popup|popupType|actionId")%>'>
		<dhv:evaluate if="<%=(animaleDettaglio.getStato() > 1 || (animaleDettaglio.getStato()<=0 && (User.getRoleId() == new Integer(ApplicationProperties.getProperty("ID_RUOLO_HD1")) || User.getRoleId() == new Integer(ApplicationProperties.getProperty("ID_RUOLO_HD2"))))) %>">
		<dhv:permission name="anagrafe_canina_registrazioni-add">
			<dhv:evaluate
				if="<%=(animaleDettaglio.getProprietario() != null
								&& animaleDettaglio.getProprietario().getIdOperatore() > 0 && animaleDettaglio
								.getProprietario().checkModificaResidenzaStabilimento())%>">
				<font color="red">CAMBIO RESIDENZA OPERATORE IN CORSO, PER
					PROSEGUIRE CORTESEMENTE REGOLARIZZA LA POSIZIONE DELL'OPERATORE</font>
			</dhv:evaluate>
			<dhv:evaluate
				if="<%=animaleDettaglio.checkAslAnimaleUtenteOFuoriRegione(User)%>">
				<dhv:evaluate if="<%=wkf.isFlagPossibilitaRegistazioni()%>">
					<a
						href="RegistrazioniAnimale.do?command=Add&idAnimale=<%=animaleDettaglio.getIdAnimale()%>">Aggiungi
						registrazione</a>&nbsp;
		</dhv:evaluate>
			</dhv:evaluate>
		</dhv:permission>
		<dhv:evaluate
			if="<%=animaleDettaglio.checkContributoPerDetentoreCanile(User)%>">
			<!-- Se il cane è detenutoo in un canile dell'asl dell'operatore e 
	 																				 per tale canile esiste una pratica contributi aperta, l'operatore
																						 può inserire una registrazione di sterilizzazione -->
			<a
				href="RegistrazioniAnimale.do?command=Add&idAnimale=<%=animaleDettaglio.getIdAnimale()%>&idTipologiaEvento=2&contributo=true">Aggiungere
				una registrazione di Sterilizzazione con contributo</a>

		</dhv:evaluate>
		<dhv:permission name="anagrafe_canina-anagrafe_canina-edit">
			<dhv:evaluate
				if="<%=(User.getRoleId() != new Integer(ApplicationProperties.getProperty("ID_RUOLO_LLP")) && animaleDettaglio
								.checkAslAnimaleUtenteOrRoleHd(User))%>">
				<a
					href="AnimaleAction.do?command=PrepareUpdate&animaleId=<%=animaleDettaglio.getIdAnimale()%>">Modifica</a>
			</dhv:evaluate>
			<dhv:evaluate
				if="<%=(User.getRoleId() == new Integer(ApplicationProperties.getProperty("ID_RUOLO_LLP")) || User
								.getRoleId() == new Integer(ApplicationProperties.getProperty("UNINA")))%>">
				<%--Vet privato --%>
				<dhv:evaluate if="<%=animaleDettaglio.checkVetModifica(User)%>">
					<%--Propri cani --%>
					<a
						href="AnimaleAction.do?command=PrepareUpdate&animaleId=<%=animaleDettaglio.getIdAnimale()%>">Modifica</a>
				</dhv:evaluate>
			</dhv:evaluate>

		</dhv:permission>

		<dhv:permission name="anagrafe_canina-anagrafe_canina-delete">
		
		<dhv:evaluate if="<%=(User.getRoleId() != new Integer(ApplicationProperties.getProperty("ID_RUOLO_LLP")) && animaleDettaglio.checkAslAnimaleUtenteOrRoleHd(User))%>">
			<a href="#"
				onclick="javascript:window.open('AnimaleAction.do?command=PrepareDelete&animaleId=<%=animaleDettaglio.getIdAnimale()%>&popup=true','popupSelect','height=595px,width=842px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');">Elimina</a>
		
		
		</dhv:evaluate>
		</dhv:permission>


		<dhv:permission name="anagrafe_canina-documenti-view">

			<img src="images/icons/stock_print-16.gif" border="0"
				align="absmiddle" height="16" width="16" />
			<a href="#"
				onclick="openRichiestaPDF('PrintRichiestaIscrizione','<%=animaleDettaglio.getIdAnimale()%>','<%=animaleDettaglio.getIdSpecie()%>', '-1', '-1', '-1');"
				id="" target="_self">Richiesta prima iscrizione</a>

			<dhv:evaluate
				if="<%=(animaleDettaglio.getIdAslRiferimento() != Constants.ID_ASL_FUORI_REGIONE)%>">



				<!-- img src="images/icons/stock_print-16.gif" border="0"
			align="absmiddle" height="16" width="16" />
		<a href="#"
			onclick="openRichiesta('<%=animaleDettaglio.getIdAnimale()%>','<%=animaleDettaglio.getIdSpecie()%>');"
			id="" target="_self">Richiesta prima iscrizione</a-->

				<!-- SE CESSIONE APERTA NON VISUALIZZARE CERTIFICATO DI ISCRIZIONE -->
				<dhv:evaluate
					if="<%=(dati_cessione == null || dati_cessione.getIdEvento() < 0)%>">

					<img src="images/icons/stock_print-16.gif" border="0"
						align="absmiddle" height="16" width="16" />
					<a href="#"
						onclick="openRichiestaPDF('PrintCertificatoIscrizione','<%=animaleDettaglio.getIdAnimale()%>','<%=animaleDettaglio.getIdSpecie()%>', '-1', '-1', '-1');"
						id="" target="_self">Certificato di Iscrizione</a>


					<!-- img src="images/icons/stock_print-16.gif" border="0"
				align="absmiddle" height="16" width="16" />
			<a href="#"
				onclick="openCertificato('<%=animaleDettaglio.getIdAnimale()%>','<%=animaleDettaglio.getIdSpecie()%>');"
				id="" target="_self">Certificato di Iscrizione</a-->
				</dhv:evaluate>

				<img src="images/icons/stock_print-16.gif" border="0"
					align="absmiddle" height="16" width="16" />
				<a href="#"
					onclick="openObblighi('<%=animaleDettaglio.getIdAnimale()%>');"
					id="" target="_self">Obblighi di legge del proprietario</a>
					
<%
				//Flusso 251
				if(User.getRoleId() == new Integer(ApplicationProperties.getProperty("ID_RUOLO_LLP")) || User.getRoleId() == new Integer(ApplicationProperties.getProperty("ID_RUOLO_HD1")) || User.getRoleId() == new Integer(ApplicationProperties.getProperty("ID_RUOLO_HD2")))
				{
%>
					<a href="#" onclick="openRichiestaPDF('PrintSterilizzazione', '<%=animaleDettaglio.getIdAnimale()%>','<%=animaleDettaglio.getIdSpecie()%>', '-1', '-1', '-1');"
				id="" target="_self">Stampa certificato di Sterilizzazione</a> <%=showWarning(request, "avvisoCertificati", false)%>

<%
				}
%>

				<dhv:evaluate
					if="<%=(animaleDettaglio.getIdSpecie() == Cane.idSpecie)%>">
					<dhv:evaluate
					if="<%=(prelievoLeish.getIdEvento() > 0)%>">

								<img src="images/icons/stock_print-16.gif" border="0"
					align="absmiddle" height="16" width="16" />
				<a href="#"
					onclick="openRichiestaPDF('PrintRichiestaCampioni', '<%=animaleDettaglio.getIdAnimale()%>','<%=animaleDettaglio.getIdSpecie()%>', '-1', '-1', '<%=prelievoLeish.getIdEvento()%>');"
					id="" target="_self">Scheda invio campioni Leishmania</a> 


			

					<!-- mg src="images/icons/stock_print-16.gif" border="0"
					align="absmiddle" height="16" width="16" />
				<a href="#"
					onclick="openCampioni('<%=animaleDettaglio.getIdAnimale()%>','<%=animaleDettaglio.getIdSpecie()%>');"
					id="" target="_self">Scheda invio campioni Leishmania</a-->
				</dhv:evaluate>
				<!-- ELIMINATO DOPO INTRODUZIONE MODULO PROFILASSI RABBIA
			<img src="images/icons/stock_print-16.gif" border="0"
				align="absmiddle" height="16" width="16" />
			<a href="#"
				onclick="openCampioniRabbia('<%=animaleDettaglio.getIdAnimale()%>','<%=animaleDettaglio.getIdSpecie()%>');"
				id="" target="_self">Scheda invio campioni anticorpi rabbia</a>
 --></dhv:evaluate>
			</dhv:evaluate>
		</dhv:permission>
		
		<% //if (request.getAttribute("mancataOrigine")!=null && Boolean.parseBoolean((String)request.getAttribute("mancataOrigine"))){ 
		%>
		<!-- 
		<img src="images/icons/stock_print-16.gif" border="0"
				align="absmiddle" height="16" width="16" />
			<a href="#"
				onclick="openRichiestaPDF('PrintAutocertificazioneMancataOrigineAnimale','<%=animaleDettaglio.getIdAnimale()%>','<%=animaleDettaglio.getIdSpecie()%>', '-1', '-1', '-1');"
				id="" target="_self">Autocertificazione mancata origine animale</a>
		-->
		<% //} 
		%>
		
			<dhv:permission name="anagrafe_canina_upload_photo-view">
						<a href="#" onclick="javascript: window.open('AnimaleAction.do?command=PrepareAddPhoto&idAnimale=<%=animaleDettaglio.getIdAnimale() %>&popup=true','popupSelect','height=595px,width=842px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');">UPLOAD FOTO</a>
			</dhv:permission>
			
			<dhv:permission name="schedaadozionecani-add">
			<dhv:evaluate if="<%=( animaleDettaglio.getStato()==3 || animaleDettaglio.getStato()==9 || (schede!=null && !schede.isEmpty()))%>">
				<a href="SchedaAdozioneCaniAction.do?command=Detail&idAnimale=<%=animaleDettaglio.getIdAnimale()%>">Scheda Adozione Cani</a>&nbsp;
			</dhv:evaluate>
			</dhv:permission>
			<dhv:permission name="schedamorsicatura-add">
<%
			if(ApplicationProperties.getProperty("scheda_morsicatura").equals("true"))
			{
%>
				<a href="SchedaMorsicaturaAction.do?command=Detail&idAnimale=<%=animaleDettaglio.getIdAnimale()%>">Scheda di Valutazione Rischio Morsicatore</a>&nbsp;
<%
			}
%>
			</dhv:permission>
			<dhv:evaluate
				if="<%=animaleDettaglio.checkAslAnimaleUtenteOFuoriRegione(User)%>">
				<dhv:evaluate if="<%=wkf.isFlagPossibilitaRegistazioni()%>">
					
		</dhv:evaluate>
			</dhv:evaluate>
		
		
</dhv:evaluate>
<%-- <dhv:evaluate if="<%=animaleDettaglio.getStato() == 1 %>">
Valida
</dhv:evaluate> --%>
		<br />



<%
if(request.getAttribute("Error")!=null)
{
%>
<%=showError(request, "Error")%>
<%
}
if(request.getAttribute("messaggio")!=null)
{
%>
<%=showMessage(request, "messaggio")%>
<%
}
%>
			
		<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">

<%
			if(!schede.isEmpty())
			{
				SchedaAdozione scheda = (SchedaAdozione)schede.get(0);
%>
				<tr>
					<th colspan="2"><strong>Indice Adottabilità</strong>
					</th>
				</tr>
				<tr class="containerBody">
					<td class="formLabel">Valutazione</td>
					<td><%=valutazione.getValutazione().toUpperCase()%></td>
				</tr>
				<tr class="containerBody">
					<td class="formLabel">Data</td>
					<td><%=toHtmlValue(toDateasString(scheda.getModified()))%></td>
				</tr>
				<tr class="containerBody">
					<td class="formLabel">Modificato da</td>
					<td><dhv:username id="<%=scheda.getModifiedBy()%>" /></td>
				</tr>
	<%
			}
			%>
 
		
			<tr>
				<th colspan="2"><strong><dhv:label
							name="accounts.accountasset_include.SpecificInformation"></dhv:label></strong>
				</th>
			</tr>
			
			
			<%
  		if(ws!=null && ws.propagazioneSinaaf)
  		{
  	%>
   <tr class="containerBody">
   <td class="formLabel">Stato SINAAF</td>
      <td >
        	
  		<%=ws.sincronizzato ? "<img src=\"images/verde.gif\">" : "<img src=\"images/rosso.gif\">" %>  <%=ws.sincronizzato ? "" : "NON" %> SINCRONIZZATO<br/>		
		<input type="button" value="Sincronizza" onclick="location.href='SinaafAction.do?command=Invia&idSinaaf=<%=ws.idSinaaf%>&entita=evento&id=<%=eventoF.getIdEvento()%>&urlToRedirect=AnimaleAction.do?command=Details_____animaleId=<%=animaleDettaglio.getIdAnimale()%>_____tipologiaEvento=<%=eventoF.getIdEvento()%>'" />
		<input type="button" value="Vedi body put/post" onclick="popURL('SinaafAction.do?command=VediRequest&idSinaaf=<%=ws.idSinaaf%>&entita=evento&id=<%=eventoF.getIdEvento()%>&urlToRedirect=AnimaleAction.do?command=Details_____id=<%=eventoF.getIdEvento()%>_____tipologiaEvento=<%=eventoF.getIdEvento()%>','Sinaaf','650','500','yes','yes')" />
		<input type="button" value="Vedi in sinaaf" onclick="if(<%=ws.idSinaaf==null || ws.idSinaaf.equals("") || ws.idSinaaf.equals("null")%>){alert('Animale non presente in sinaaf');} else { popURL('SinaafAction.do?command=Vedi&idSinaaf=<%=ws.idSinaaf%>&entita=evento&id=<%=eventoF.getIdEvento()%>','Sinaaf','650','500','yes','yes');} " />
	
      </td>
     </tr>
     <%
  		}
  	%>

			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Specie</dhv:label></td>
				<td><%=specieList.getSelectedValue(animaleDettaglio.getIdSpecie())%></td>
			</tr>

			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Data Registrazione</dhv:label>
				</td>


				<td><%=toDateString(animaleDettaglio.getDataRegistrazione())%></td>
			</tr>
			
			
			

			<%
				if (animaleDettaglio.getIdAslRiferimento() > 0) {
			%>
			<tr class="containerBody">
				<td class="formLabel" valign="top"><dhv:label name="">Asl di Riferimento</dhv:label></td>
				<td><%=toHtml(SiteList.getSelectedValue(animaleDettaglio.getIdAslRiferimento()))%></td>
			</tr>
			<%
				}
			%>

			<dhv:evaluate
				if="<%=(animaleDettaglio.getIdPartitaCircuitoCommerciale() > 0)%>">

				<tr class="containerBody">
					<td class="formLabel" valign="top"><dhv:label name="">Partita di origine</dhv:label></td>
					<td><%=partita.getNrCertificato()%></td>
				</tr>

			</dhv:evaluate>

			<%
				if (animaleDettaglio.getProprietario() != null
							&& animaleDettaglio.getProprietario().getIdOperatore() > 0) {
			%>

			<%
				LineaProduttiva lpProp = (LineaProduttiva) ((Stabilimento) animaleDettaglio.getProprietario()
								.getListaStabilimenti().get(0)).getListaLineeProduttive().get(0);

						if (lpProp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneColonia) {
			%>
			<tr class="containerBody">
				<td class="formLabel" valign="top"><dhv:label name="">Colonia Proprietaria</dhv:label></td>
				<td>Appartenente a colonia nr. <%=((ColoniaInformazioni) lpProp).getNrProtocollo()%></td>
			<tr class="containerBody">
				<td class="formLabel" valign="top"><dhv:label name="">Responsabile colonia</dhv:label></td>

				<%
					} else {
				%>
			
			<tr class="containerBody">
				<td class="formLabel" valign="top"><dhv:label name="">Proprietario</dhv:label></td>
				<%
					}
				%>
				<td><a
					href="javascript:popURL('OperatoreAction.do?command=Details&opId=<%=((LineaProduttiva) ((Stabilimento) animaleDettaglio.getProprietario()
							.getListaStabilimenti().get(0)).getListaLineeProduttive().get(0)).getId()%>&popup=true&viewOnly=true','AccountDetails','650','500','yes','yes');">
						<%=toHtml(animaleDettaglio.getProprietario().getRagioneSociale())%></a></td>
			</tr>


			<%
				}
			%>
			<%
				if (animaleDettaglio.getDetentore() != null && animaleDettaglio.getDetentore().getIdOperatore() > 0) {
			%>
			<%
				LineaProduttiva lpDet = (LineaProduttiva) ((Stabilimento) animaleDettaglio.getDetentore()
								.getListaStabilimenti().get(0)).getListaLineeProduttive().get(0);

						if (lpDet.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneColonia) {
			%>
			<tr class="containerBody">
				<td class="formLabel" valign="top"><dhv:label name="">Colonia Detentrice</dhv:label></td>
				<td>Appartenente a colonia nr. <%=((ColoniaInformazioni) lpDet).getNrProtocollo()%></td>
			<tr class="containerBody">
				<td class="formLabel" valign="top"><dhv:label name="">Responsabile colonia</dhv:label></td>

				<%
					} else {
				%>
			
			<tr class="containerBody">
				<td class="formLabel" valign="top"><dhv:label name="">Detentore</dhv:label>
				</td>
				<%
					}
				%>
				<td><a
					href="javascript:popURL('OperatoreAction.do?command=Details&opId=<%=((LineaProduttiva) ((Stabilimento) animaleDettaglio.getDetentore().getListaStabilimenti()
							.get(0)).getListaLineeProduttive().get(0)).getId()%>&popup=true&viewOnly=true','AccountDetails','650','500','yes','yes');"><%=toHtml(animaleDettaglio.getDetentore().getRagioneSociale())%></a></td>
			</tr>
			<%
				}
			%>

			<%-- RAZZA --%>

			<dhv:evaluate if="<%=animaleDettaglio.getIdRazza() > 0%>">
				<tr class="containerBody">
					<td class="formLabel"><dhv:label name="">Razza</dhv:label></td>
					<td><%=toHtml(razzaList.getSelectedValue(animaleDettaglio.getIdRazza()))%>
						&nbsp;</td>
				</tr>
			</dhv:evaluate>
			<%-- SESSO --%>
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Sesso</dhv:label></td>
				<td><%=(animaleDettaglio.getSesso() != null && !("").equals(animaleDettaglio.getSesso())) ? toHtml(animaleDettaglio
						.getSesso()) : "--"%></td>
			</tr>
			<%-- TAGLIA --%>

			<dhv:evaluate if="<%=(animaleDettaglio.getIdTaglia() > 0)%>">

				<tr class="containerBody">
					<td class="formLabel"><dhv:label name="">Taglia</dhv:label></td>
					<%
					if (animaleDettaglio.getProprietario() != null && animaleDettaglio.getProprietario().getIdOperatore() > 0)
					{
					%>
					<td><%=toHtml(tagliaList.getSelectedValueEstesa(animaleDettaglio.getIdTaglia(), ((LineaProduttiva) (((Stabilimento) animaleDettaglio.getProprietario().getListaStabilimenti().get(0)).getListaLineeProduttive().get(0))).getIdRelazioneAttivita()))%>
						&nbsp;</td>
					<%
					}
					else
					{
					%>
					<td><%=toHtml(tagliaList.getSelectedValue(animaleDettaglio.getIdTaglia()))%>
						&nbsp;</td>
					<%	
					}
					%>
				</tr>
			</dhv:evaluate>
			<!-- 	<dhv:evaluate if="<%=(animaleDettaglio.getIdSpecie() == 1)%>">
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Taglia</dhv:label></td>
				<td><%=toHtml(tagliaList.getSelectedValue(caneDettaglio.getIdTaglia()))%> &nbsp;</td>
			</tr>
		</dhv:evaluate>
		<dhv:evaluate if="<%=(animaleDettaglio.getIdSpecie() == 3)%>">
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Taglia</dhv:label></td>
				<td><%=toHtml(tagliaList.getSelectedValue(furettoDettaglio.getIdTaglia()))%> &nbsp;</td>
			</tr>
		</dhv:evaluate> -->
			<%-- DATA DI NASCITA --%>
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Data di nascita</dhv:label>
				</td>
				<td><%=toHtmlValue(toDateasString(animaleDettaglio.getDataNascita()))%>&nbsp;</td>
			</tr>
			<%-- DATA PRESUNTA --%>
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Data nascita presunta</dhv:label>
				</td>
				<dhv:evaluate if="<%=animaleDettaglio.isFlagDataNascitaPresunta()%>">
					<td><dhv:label name="">SI</dhv:label></td>
			</tr>
			</dhv:evaluate>
			<dhv:evaluate if="<%=!animaleDettaglio.isFlagDataNascitaPresunta()%>">
				<td><dhv:label name="">NO</dhv:label></td>
				</tr>
			</dhv:evaluate>


			<tr class="containerBody">

				<td class="formLabel"><dhv:label name="">Mantello</dhv:label></td>
				<td><%=mantelloList.getSelectedValue(animaleDettaglio.getIdTipoMantello())%>&nbsp;</td>
			</tr>

			<dhv:evaluate if="<%=caneDettaglio != null%>">
				<%
					if (animaleDettaglio.getIdSpecie() == caneDettaglio.getIdSpecie()) {
				%>
				<tr class="containerBody">
					<td class="formLabel"><dhv:label name="">Morsicatore</dhv:label>
					</td>
					<td>
						<%
							if (caneDettaglio.isFlagMorsicatore()) {
						%> SI &nbsp; Data ultimo evento morsicatura: <%=toHtmlValue(toDateasString(caneDettaglio.getDataMorso()))%>
						<%
							} else {
						%> NO <%
							}
						%>
					</td>

				</tr>
				<%
					}
				%>

				<%-- 			<%
				if (animaleDettaglio.getIdSpecie() == Cane.idSpecie) {
			%>
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Data Vaccino</dhv:label>
				</td>
				<td><%=(caneDettaglio.getDataVaccino() != null) ? toHtmlValue(toDateasString(caneDettaglio
										.getDataVaccino()))
										: "--"%></td>
			</tr>
			<%
				}
			%> --%>

				<%-- 			<%
				if (animaleDettaglio.getIdSpecie() == Cane.idSpecie) {
			%>
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Numero Lotto Vaccino</dhv:label>
				</td>
				<td><%=(caneDettaglio
												.getNumeroLottoVaccino() != null) ? toHtmlValue(caneDettaglio
										.getNumeroLottoVaccino())
										: "--"%></td>
			</tr>
			<%
				}
			%> --%>



				<%-- 
			<%
				if (animaleDettaglio.getIdSpecie() == Gatto.idSpecie) {
			%>
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Data Vaccino</dhv:label>
				</td>
				<td><%=(gattoDettaglio.getDataVaccino() != null) ? toHtmlValue(toDateasString(gattoDettaglio
										.getDataVaccino()))
										: "--"%></td>
			</tr>
			<%
				}
			%>

			<%
				if (animaleDettaglio.getIdSpecie() == Gatto.idSpecie) {
			%>
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Numero Lotto Vaccino</dhv:label>
				</td>
				<td><%=(gattoDettaglio
												.getNumeroLottoVaccino() != null) ? toHtmlValue(gattoDettaglio
										.getNumeroLottoVaccino())
										: "--"%></td>
			</tr>
			<%
				}
			%>
 --%>



				<%
					if (animaleDettaglio.getIdSpecie() == caneDettaglio.getIdSpecie()) {
				%>
				<tr class="containerBody">
					<td class="formLabel"><dhv:label name="">Aggressivo</dhv:label>
					</td>
					<td><%=caneDettaglio.isFlagAggressivo() ? "SI" : "NO"%></td>
				</tr>
				<%
					}
				%>
			</dhv:evaluate>

			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Nome</dhv:label></td>
				<td><%=(animaleDettaglio.getNome() != null && !"".equals((animaleDettaglio.getIdSpecie() == caneDettaglio
						.getIdSpecie()))) ? toHtml(animaleDettaglio.getNome()) : "--"%></td>
			</tr>
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Microchip</dhv:label></td>
				<td><%=toHtml(animaleDettaglio.getMicrochip())%></td>
			</tr>

			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Veterinario chippatura</dhv:label>
				</td>
				<td><%=(animaleDettaglio.getIdVeterinarioMicrochip() > -1) ? toHtml(veterinariList
						.getSelectedValue(animaleDettaglio.getIdVeterinarioMicrochip())) : "--"%></td>
			</tr>

			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Data chippatura</dhv:label>
				</td>
				<td><%=toDateasString(animaleDettaglio.getDataMicrochip())%></td>
			</tr>

			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Numero passaporto: </dhv:label>
				</td>
				<td><%=((animaleDettaglio.getNumeroPassaporto() != null && !("").equals(animaleDettaglio
						.getNumeroPassaporto())) ? (animaleDettaglio.getNumeroPassaporto() + " rilasciato il "
						+ toHtmlValue(toDateasString(animaleDettaglio.getDataRilascioPassaporto())) + " (scadenza il "
						+ ((animaleDettaglio.getDataScadenzaPassaporto() != null) ?  toHtmlValue(toDateasString(animaleDettaglio.getDataScadenzaPassaporto())) + ")" :
							toHtmlValue(toDateasString(rilascioPassaporto.getDataScadenzaPassaporto())) + ")")) : "--" )%>&nbsp;</td>
			</tr>


			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Tatuaggio/2°Microchip</dhv:label>
				</td>
				<td><%=(animaleDettaglio.getTatuaggio() != null) ? toHtml(animaleDettaglio.getTatuaggio()) : "--"%></td>
			</tr>



			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Data inserimento tatuaggio/2°Microchip</dhv:label>
				</td>
				<td><%=(animaleDettaglio.getDataTatuaggio() != null) ? toHtmlValue(toDateasString(animaleDettaglio
						.getDataTatuaggio())) : "--"%>&nbsp;</td>
			</tr>




			<!-- tr class="containerBody">
			<td class="formLabel"><dhv:label
				name="">Stato</dhv:label>
			</td>
			<td>
				<%--=toHtml(animaleDettaglio.getDescrizioneStato())--%>
						Data Stato 
						 <%--=toHtmlValue(toDateasString(animaleDettaglio.getDataAggiornamentoStato()))--%>&nbsp;
			</td>
		</tr-->

			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Sterilizzato</dhv:label>
				</td>
				<td>
<%
					if(animaleDettaglio.isFlagSterilizzazione() == true)
					{
						out.println("SI");
						if(animaleDettaglio.getDataSterilizzazione()!=null)
							out.println(" IN DATA " + toHtmlValue(toDateasString(animaleDettaglio.getDataSterilizzazione())));		
					}
%>
				</td>
			</tr>

			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Contributo Regionale</dhv:label>
				</td>
				<td><%=(animaleDettaglio.isFlagSterilizzazione() == false) ? " " : ((animaleDettaglio
						.isFlagContributoRegionale() == true) ? "SI pratica contributi decreto nr "
						+ listaPratiche.getSelectedValue(animaleDettaglio.getIdPraticaContributi()) : "NO")%></td>
			</tr>

			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Data vaccinazione antirabbica</dhv:label>
				</td>
				<td><%=(dati_antirabbica.getDataVaccinazione() != null) ? (toDateasString(dati_antirabbica
						.getDataVaccinazione())) :  (animaleDettaglio.getDataVaccino() != null) ? toDateasString(animaleDettaglio.getDataVaccino()) : "--"%></td>
			</tr>

			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Numero lotto vaccino (antirabbica)</dhv:label>
				</td>
				<td><%= ((dati_antirabbica.getNumeroLottoVaccino() != null) ? dati_antirabbica
						.getNumeroLottoVaccino() : (animaleDettaglio.getNumeroLottoVaccino() != null) ? animaleDettaglio.getNumeroLottoVaccino() : "--")%></td>
			</tr>

			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Nome vaccino (antirabbica)</dhv:label>
				</td>
				<td><%=(dati_antirabbica.getNomeVaccino() != null && !("").equals(dati_antirabbica.getNomeVaccino())) ? dati_antirabbica
						.getNomeVaccino() : (animaleDettaglio.getNomeVaccino() != null) ? animaleDettaglio.getNomeVaccino() : "--"%></td>
			</tr>
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Produttore vaccino (antirabbica)</dhv:label>
				</td>
				<td><%=(dati_antirabbica.getProduttoreVaccino() != null && !("").equals(dati_antirabbica
						.getProduttoreVaccino())) ? dati_antirabbica.getProduttoreVaccino() : (animaleDettaglio.getProduttoreVaccino() != null) ? animaleDettaglio.getProduttoreVaccino() : "--"%></td>
			</tr>
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Data scadenza vaccino (antirabbica)</dhv:label>
				</td>
				<td><%=(dati_antirabbica.getDataScadenzaVaccino() != null && !("").equals(dati_antirabbica
						.getDataScadenzaVaccino())) ? toDateasString(dati_antirabbica.getDataScadenzaVaccino()) : (animaleDettaglio.getDataScadenzaVaccino() != null) ? toDateasString(animaleDettaglio.getDataScadenzaVaccino()) : "--"%></td>
			</tr>

			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Stato del <%=animaleDettaglio.getNomeSpecieAnimale()%>
					</dhv:label></td>
<%
	String statoDaVisualizzare = toHtml(statoList.getSelectedValue(animaleDettaglio.getStato()));
%>
				<td><%=statoDaVisualizzare%>
					<dhv:evaluate
						if="<%=(animaleDettaglio.getStato() == 30 || animaleDettaglio.getStato() == 31
							|| animaleDettaglio.getStato() == 32 || animaleDettaglio.getStato() == 33
							|| animaleDettaglio.getStato() == 34 || animaleDettaglio.getStato() == 35)%>">
<%
if(animaleDettaglio.getIdRegione()>0)
{
%>
			- <%=regioniList.getSelectedValue(animaleDettaglio.getIdRegione())%>
<%
}
%>
					</dhv:evaluate> &nbsp;</td>
			</tr>
		</table>
		<br>
		
				<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">
			
			<!-- GESTIONE ORIGINE ANIMALE -->
			<tr>
				<th colspan="2"><strong>Dati provenienza animale mai anagrafato</strong>
				</th>
			
			</tr>
			<% 
			boolean or=false;
			if (animaleDettaglio.isFlagMancataOrigine()){ or=true; %>
				<tr class="containerBody">
					<td colspan="2">Informazioni mancanti sull'origine dell'animale</td>
				</tr>
			<% } %> 
		
			<% if (eventoF.getProvenienza_origine()!=null && !eventoF.getProvenienza_origine().equals("")){  or=true; %>
				<tr class="containerBody">
					<td class="formLabel"><dhv:label name="">Origine animale</dhv:label></td>
					<td><%=(eventoF.getProvenienza_origine().contains("in") ? "In regione" : "Fuori regione")%></td>
				</tr>
			<% } %>
			
			
			<% if(eventoF.getIdProprietarioProvenienza()>0){  or=true; %>
				<tr class="containerBody">
					<td class="formLabel"><dhv:label name="">Provienienza</dhv:label></td>
					<td>Da proprietario</td>
				</tr>
				<tr class="containerBody">
					<td class="formLabel"><dhv:label name="">Proprietario</dhv:label></td>
					<td>
					<% Operatore proprietarioProvenienza = eventoF.getIdProprietarioProvenienzaOp();
					if (proprietarioProvenienza != null) {
						if (proprietarioProvenienza.getIdOperatore()<10000000){
							Stabilimento stab = (Stabilimento) (proprietarioProvenienza.getListaStabilimenti().get(0));
							LineaProduttiva linea = (LineaProduttiva) (stab.getListaLineeProduttive().get(0));
							%> 
							<a href="OperatoreAction.do?command=Details&opId=<%=linea.getId()%>"><%=toHtml(proprietarioProvenienza.getRagioneSociale())%></a><br>
							<%
						}else{ %>
							<%=toHtml(proprietarioProvenienza.getRagioneSociale())%><br>
					<%	}
					} %></td></tr>
			<% } %>

			<% if (eventoF.getData_ritrovamento()!= null && !eventoF.getData_ritrovamento().equals("")){  or=true; %>
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Provienienza</dhv:label></td>
				<td>Da ritrovamento</td>
			</tr>
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Dettaglio ritrovamento</dhv:label></td>
				<td>
<%	
					out.println("Ritrovato in "+eventoF.getIndirizzo_ritrovamento());
					try
					{
						
						out.println(", "+comuniList.getSelectedValue(Integer.parseInt(eventoF.getComune_ritrovamento())));
					}
					catch(Exception e)
					{
						
					}
					try
					{
						
						out.println(" - "+provinceList.getSelectedValue(Integer.parseInt(eventoF.getProvincia_ritrovamento())));
					}
					catch(Exception e)
					{
						
					}
						
					out.println(" in data " + eventoF.getData_ritrovamento()); 					
					out.println(eventoF.getData_ritrovamento()); 
%>
			</td>
			</tr>
			<% } %>

			<% if(eventoF.isFlag_anagrafe_estera()){ or=true; %>
				<tr class="containerBody">
					<td class="formLabel"><dhv:label name="">Origine animale</dhv:label></td>
					<td>Provenienza da nazione estera</td>
				</tr>
				<tr class="containerBody">
					<td class="formLabel"><dhv:label name="">Nazione di provenienza</dhv:label></td>
					<td><%	out.print(nazioniList.getSelectedValue(Integer.parseInt(eventoF.getNazione_estera()))+"<br>");
							if(eventoF.getNazione_estera_note()!=null && !eventoF.getNazione_estera_note().equals(""))
					 	     out.print("Note: "+eventoF.getNazione_estera_note());%>
					</td>
				</tr>
			<% } %>
			
			<% if(eventoF.isFlag_anagrafe_fr()){ or=true;  %>
				<tr class="containerBody">
					<td class="formLabel"><dhv:label name="">Proveniente da anagrafe altra regione</dhv:label></td>
					<td><% out.print("Regione "+regioniList.getSelectedValue(Integer.parseInt(eventoF.getRegione_anagrafe_fr()))+"<br>");
						   if(eventoF.getRegione_anagrafe_fr_note()!=null && !eventoF.getRegione_anagrafe_fr_note().equals(""))
					 	     out.print("Note: "+eventoF.getRegione_anagrafe_fr_note());%>
					</td>
				</tr>
			<% } %>

			<% if(eventoF.isFlag_acquisto_online()){ or=true;  %>
				<tr class="containerBody">
					<td class="formLabel"><dhv:label name="">Acquisto online tramite</dhv:label></td>
					<td><% out.print(eventoF.getSito_web_acquisto()+"<br>");
						   if(eventoF.getSito_web_acquisto_note()!=null && !eventoF.getSito_web_acquisto_note().equals(""))
					 	     out.print("Note: "+eventoF.getSito_web_acquisto_note());	%>
					</td>
				</tr>
			<% } %>
			
			<!-- GESTIONE VECCHIA PROVENIENZA -->
			<%
			if (!or){
			if(animaleDettaglio.getIdSpecie() == Cane.idSpecie){   
				if(caneDettaglio.isFlagFuoriRegione() && caneDettaglio.getIdRegioneProvenienza() > -1){	or=true; %>
				<tr class="containerBody">
					<td class="formLabel"><dhv:label name="">Proveniente da anagrafe altra regione</dhv:label></td>
					<td><% out.print("Regione "+regioniList.getSelectedValue(caneDettaglio.getIdRegioneProvenienza())+"<br>");
						   if(caneDettaglio.isFlagSindacoFuoriRegione())
				 	         out.print("Note: Proprietario Sindaco");%>
					</td>
				</tr>
			<%	 }
			} %>

			<% if(animaleDettaglio.getIdSpecie() == Gatto.idSpecie){   
				if(gattoDettaglio.isFlagFuoriRegione() && gattoDettaglio.getIdRegioneProvenienza() > -1){	or=true; %>
				<tr class="containerBody">
					<td class="formLabel"><dhv:label name="">Proveniente da anagrafe altra regione</dhv:label></td>
					<td><% out.print("Regione "+regioniList.getSelectedValue(gattoDettaglio.getIdRegioneProvenienza())+"<br>");
     					   if(gattoDettaglio.isFlagSindacoFuoriRegione())
				 	         out.print("Note: Proprietario Sindaco"); %>
					</td>
				</tr>
			<%	 }
			} 
			}%>

			<% 	if (!or){ %>
			<tr class="containerBody">
				<td colspan="2">Informazioni mancanti sull'origine dell'animale</td>
			</tr>
			<% } %> 			
			<!-- FINE ORIGINE -->			
			</table><br/>

		<!-- Controlli -->
		<dhv:evaluate
			if="<%=(animaleDettaglio.isFlagCircuitoCommerciale() == false && animaleDettaglio.getIdSpecie() == Cane.idSpecie)%>">
			<table cellpadding="4" cellspacing="0" border="0" width="100%"
				class="details">
				<tr>
					<th colspan="4"><strong><dhv:label name="">Controlli Richiesti</dhv:label></strong>
					</th>
				</tr>


				<%-- parte modificata --%>


				<tr class="containerBody">
					<td width="25%" valign="top" class="formLabel2"><dhv:label
							name="">Leishmaniosi</dhv:label></td>
					<td width="25%" colspan="2"><a href="#"
						onclick="javascript : popupViewEsitiLeishmania (<%=animaleDettaglio.getIdAnimale()%>)">Visualizza
							Esiti Leishmaniosi</a></td>

					<%
						if (caneDettaglio.getLeishmaniosiNumeroOrdinanzaSindacale() != null
										&& !"".equals(caneDettaglio.getLeishmaniosiNumeroOrdinanzaSindacale())) {
					%>
					<td>Ord.sind. <%=" " + caneDettaglio.getLeishmaniosiNumeroOrdinanzaSindacale() + " "%>del
						<%=" "
								+ toHtmlValue(toDateasString(caneDettaglio.getLeishmaniosiDataOrdinanzaSindacale()))
								+ " "%></td>
					<%
						} else {
					%>
					<td>&nbsp;</td>
					<%
						}
					%>

				</tr>


				<tr class="containerBody">
					<td valign="top" class="formLabel2"><dhv:label name="">Ehrlichiosi</dhv:label>
					</td>
					<td>
						<%
							if (caneDettaglio.isFlagControlloEhrlichiosi()) {
						%> <img src="images/check/check.JPG"> <%
 	} else {
 %> <img src="images/check/nocheck.JPG"> <%
 	}
 %>
					</td>
					<td>Data <%
						if (caneDettaglio.getDataControlloEhrlichiosi() != null) {
					%> <%=toHtmlValue(toDateasString(caneDettaglio.getDataControlloEhrlichiosi()))%>
						<%
							} else {
						%> <%
 	}
 %>
					</td>
					<td>Esito <%=esitoControlloList.getSelectedValue(caneDettaglio.getEsitoControlloEhrlichiosi())%></td>
				</tr>

				<tr class="containerBody">
					<td valign="top" class="formLabel2"><dhv:label name="">Rickettiosi</dhv:label>
					</td>
					<td>
						<%
							if (caneDettaglio.isFlagControlloRickettsiosi()) {
						%> <img src="images/check/check.JPG"> <%
 	} else {
 %> <img src="images/check/nocheck.JPG"> <%
 	}
 %>
					</td>
					<td>Data <%
						if (caneDettaglio.getDataControlloRickettsiosi() != null) {
					%> <%=toHtmlValue(toDateasString(caneDettaglio.getDataControlloRickettsiosi()))%>
						<%
							} else {
						%> <%
 	}
 %>
					</td>
					<td>Esito <%=esitoControlloList.getSelectedValue(caneDettaglio.getEsitoControlloRickettsiosi())%></td>
				</tr>




			</table>
		</dhv:evaluate>



<%

//if(!schedeMorsi.isEmpty() )
	if(false)
			{
	%>
			<tr>
				<th colspan="2"><strong>VALUTAZIONE PERICOLOSITA' DEL CANE</strong>
				</th>
			</tr>
			
			<%
			int i =0;
			while(i<=schedeMorsi.size())
			{
				SchedaMorsicatura schedaMorso = (SchedaMorsicatura)schedeMorsi.get(i);
			%>
				<tr class="containerBody">
					<td class="formLabel">Rischio Pericolosità</td>
					<td><%=valutazioneMorso.getRischio()%></td>
				</tr>
				<tr class="containerBody">
					<td class="formLabel">Consiglio</td>
					<td><%=valutazioneMorso.getConsiglio()%></td>
				</tr>
				<tr class="containerBody">
					<td class="formLabel">Data</td>
					<td><%=toHtmlValue(toDateasString(schedaMorso.getModified()))%></td>
				</tr>
				<tr class="containerBody">
					<td class="formLabel">Modificato da</td>
					<td><!-- dhv:username id="<%=schedaMorso.getModifiedBy()%>" / --></td>
				</tr>
			<%
			i++;
			}
			}
			%>
			
			
			
		<!-- Controlli COMMERCIALE (se il cane appartiene al circuito commerciale) -->
		<dhv:evaluate if="<%=(animaleDettaglio.isFlagCircuitoCommerciale())%>">
			<table cellpadding="5" cellspacing="0" border="0" width="100%"
				class="details">
				<tr>
					<th colspan="5"><strong><dhv:label name="">Controlli Richiesti</dhv:label></strong>
					</th>
				</tr>


				<%-- parte modificata --%>


				<tr class="containerBody">
					<td width="25%" valign="top" class="formLabel2"><dhv:label
							name="">Controllo Documentale</dhv:label></td>
					<td width="25%" colspan="2"><input type="checkbox" value=""
						name="" id=""
						<%=(animaleDettaglio.getFlagPresenzaEsitoControlloDocumentale()) ? "checked" : ""%>
						disabled="disabled" /></td>
					<td>Data <%=(animaleDettaglio.getDataEsitoControlloDocumentale() != null) ? toDateasString(animaleDettaglio
							.getDataEsitoControlloDocumentale()) : ""%>
					<td>Esito <%=esitoControlloList.getSelectedValue(animaleDettaglio.getIdEsitoControlloDocumentale())%></td>


				</tr>

				<tr class="containerBody">
					<td width="25%" valign="top" class="formLabel2"><dhv:label
							name="">Controllo d'identità</dhv:label></td>
					<td width="25%" colspan="2"><input type="checkbox" value=""
						name="" id=""
						<%=(animaleDettaglio.getFlagPresenzaEsitoControlloIdentita()) ? "checked" : ""%>
						disabled="disabled" /></td>
					<td>Data <%=(animaleDettaglio.getDataEsitoControlloIdentita() != null) ? toDateasString(animaleDettaglio
							.getDataEsitoControlloIdentita()) : ""%>
					<td>Esito <%=esitoControlloList.getSelectedValue(animaleDettaglio.getIdEsitoControlloIdentita())%></td>


				</tr>

				<tr class="containerBody">
					<td width="25%" valign="top" class="formLabel2"><dhv:label
							name="">Controllo Fisico</dhv:label></td>
					<td width="25%" colspan="2"><input type="checkbox" value=""
						name="" id=""
						<%=(animaleDettaglio.getFlagPresenzaEsitoControlloFisico()) ? "checked" : ""%>
						disabled="disabled" /></td>
					<td>Data <%=(animaleDettaglio.getDataEsitoControlloFisico() != null) ? toDateasString(animaleDettaglio
							.getDataEsitoControlloFisico()) : ""%>
					<td>Esito <%=esitoControlloList.getSelectedValue(animaleDettaglio.getIdEsitoControlloFisico())%></td>


				</tr>

				<tr class="containerBody">
					<td width="25%" valign="top" class="formLabel2"><dhv:label
							name="">Controllo Laboratorio (anticorpale rabbia)</dhv:label></td>
					<td width="25%" colspan="2"><input type="checkbox" value=""
						name="" id=""
						<%=(animaleDettaglio.getFlagPresenzaEsitoControlloLaboratorio()) ? "checked" : ""%>
						disabled="disabled" /></td>
					<td>Data <%=(animaleDettaglio.getDataEsitoControlloLaboratorio() != null) ? toDateasString(animaleDettaglio
							.getDataEsitoControlloLaboratorio()) : ""%>
					<td>Esito <%=esitoControlloLabList.getSelectedValue(animaleDettaglio.getIdEsitoControlloLaboratorio())%></td>


				</tr>


			</table>
		</dhv:evaluate>


		<br>


		<dhv:evaluate
			if="<%=(animaleDettaglio.getIdSpecie() == Cane.idSpecie)%>">

			<table cellpadding="4" cellspacing="0" border="0" width="100%"
				class="details">
				<tr>
					<th colspan="2"><strong><dhv:label name="">Cattura</dhv:label></strong>
					</th>
				</tr>



				<tr class="containerBody">
					<td class="formLabel"><dhv:label name="">Cattura</dhv:label></td>
					<td><%=(caneDettaglio.isFlagCattura()) ? "SI" : "NO"%></td>
				</tr>
				<tr class="containerBody">
					<td valign="top" class="formLabel"><dhv:label name="">Luogo Cattura</dhv:label></td>
					<td><%=toHtml(caneDettaglio.getLuogoCattura())%></td>
				</tr>
				<tr class="containerBody">
					<td valign="top" class="formLabel"><dhv:label name="">Comune Cattura</dhv:label></td>
					<td><%=comuniList.getSelectedValue(caneDettaglio.getIdComuneCattura())%></td>
				</tr>

				<tr class="containerBody">
					<td class="formLabel"><dhv:label name="">Data cattura</dhv:label></td>
					<td><%=toHtmlValue(toDateasString(caneDettaglio.getDataCattura()))%>&nbsp;</td>
				</tr>
				<tr class="containerBody">
					<td valign="top" class="formLabel"><dhv:label name="">Verbale Cattura</dhv:label></td>
					<td><%=toHtml(caneDettaglio.getVerbaleCattura())%></td>
				</tr>


			</table>
		</dhv:evaluate>

		<dhv:evaluate
			if="<%=(animaleDettaglio.getIdSpecie() == Gatto.idSpecie)%>">

			<table cellpadding="4" cellspacing="0" border="0" width="100%"
				class="details">
				<tr>
					<th colspan="2"><strong><dhv:label name="">Cattura</dhv:label></strong>
					</th>
				</tr>



				<tr class="containerBody">
					<td class="formLabel"><dhv:label name="">Cattura</dhv:label></td>
					<td><%=(gattoDettaglio.isFlagCattura()) ? "SI" : "NO"%></td>
				</tr>
				<tr class="containerBody">
					<td valign="top" class="formLabel"><dhv:label name="">Luogo Cattura</dhv:label></td>
					<td><%=toHtml(gattoDettaglio.getLuogoCattura())%></td>
				</tr>
				<tr class="containerBody">
					<td valign="top" class="formLabel"><dhv:label name="">Comune Cattura</dhv:label></td>
					<td><%=comuniList.getSelectedValue(gattoDettaglio.getIdComuneCattura())%></td>
				</tr>

				<tr class="containerBody">
					<td class="formLabel"><dhv:label name="">Data cattura</dhv:label></td>
					<td><%=toHtmlValue(toDateasString(gattoDettaglio.getDataCattura()))%>&nbsp;</td>
				</tr>
				<tr class="containerBody">
					<td valign="top" class="formLabel"><dhv:label name="">Verbale Cattura</dhv:label></td>
					<td><%=toHtml(gattoDettaglio.getVerbaleCattura())%></td>
				</tr>


			</table>
		</dhv:evaluate>

		<br>
		<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">
			<tr>
				<th colspan="2"><strong><dhv:label name="">Dettagli addizionali</dhv:label></strong>
				</th>
			</tr>

			<%
				if ((animaleDettaglio.getSegniParticolari() != null && animaleDettaglio.getSegniParticolari().length() > 0)
							|| (animaleDettaglio.getNote() != null && animaleDettaglio.getNote().length() > 0)) {
			%>

			<dhv:evaluate
				if="<%=(animaleDettaglio.getSegniParticolari() != null && animaleDettaglio
								.getSegniParticolari().length() > 0)%>">
				<tr class="containerBody">
					<td valign="top" class="formLabel"><dhv:label name="">Segni Particolari</dhv:label>
					</td>
					<td><%=toHtml(animaleDettaglio.getSegniParticolari())%></td>
				</tr>
			</dhv:evaluate>
			<dhv:evaluate
				if="<%=(animaleDettaglio.getNote() != null && animaleDettaglio.getNote().length() > 0)%>">
				<tr class="containerBody">
					<td valign="top" class="formLabel"><dhv:label name="">Note</dhv:label></td>
					<td><%=toHtml(animaleDettaglio.getNote())%></td>
				</tr>
			</dhv:evaluate>
			<%
				} else {
			%>

			<tr class="containerBody">
				<td colspan="2"><font color="#9E9E9E"><dhv:label name="">Dettagli addizionali non inseriti</dhv:label></font>
				</td>
			</tr>
			<%
				}
			%>



		</table>
		<br>


		<dhv:permission name="anagrafe_canina-note_internal_use_only-add">
			<table cellpadding="4" cellspacing="0" border="0" width="100%"
				class="details">
				<tr>
					<th colspan="2"><strong>NOTE USO INTERNO</strong></th>
				</tr>
				<tr class="containerBody">
					<td valign="top" class="formLabel"><dhv:label name="">Note</dhv:label>
					</td>
					<td><textarea name="noteInternalUseOnly" rows="3" cols="50"
							disabled="disabled"><%=toString(animaleDettaglio.getNoteInternalUseOnly())%></textarea></td>
				</tr>
			</table>
		</dhv:permission>


		<%-- if ( .getCommerciale() ) { %>
	<%@ include file="s_details_controlli_comm.jsp" %>
<% }else{ %>
	<%@ include file="s_details_controlli_priv_new.jsp" %>
<% }  %>

<br--%>
		<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">
			<tr>
				<th colspan="2"><strong><dhv:label
							name="accounts.accounts_contacts_calls_details.RecordInformation">Record Information</dhv:label></strong>
				</th>
			</tr>
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label
						name="accounts.accounts_calls_list.Entered">Entered</dhv:label></td>
				<td><%=animaleDettaglio.getInformazioniInserimentoRecord()%>&nbsp;</td>
			</tr>
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label
						name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label>
				</td>
				<td><dhv:username
						id="<%=animaleDettaglio.getIdUtenteModifica()%>" /> <%=toHtmlValue(toDateasString(animaleDettaglio.getDataModifica()))%>&nbsp;</td>
			</tr>
		</table>
		<br>
		<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">

			<tr>
				<th colspan="2"><strong><dhv:label name="">Informazioni attivit&agrave; di anagrafe</dhv:label></strong>
				</th>
			</tr>


			<tr>
				<td nowrap class="formLabel"><b><dhv:label name="">Attivit&agrave; in anagrafe itinerante</dhv:label></b></td>
				<td><%=(animaleDettaglio.isFlagAttivitaItinerante()) ? "SI" : "NO"%></td>
				<dhv:evaluate if="<%=animaleDettaglio.isFlagAttivitaItinerante()%>">
					<tr>
						<td nowrap class="formLabel"><dhv:label name="">Data svolgimento attivit&agrave;</dhv:label></td>
						<td><%=toDateasString(animaleDettaglio.getDataAttivitaItinerante())%>&nbsp;
						</td>
					</tr>
					<tr>
						<td nowrap class="formLabel"><dhv:label name="">Comune svolgimento attivit&agrave;</dhv:label></td>
						<td><%=comuniList.getSelectedValue(animaleDettaglio.getIdComuneAttivitaItinerante())%>
						</td>
					</tr>
					<tr>
						<td nowrap class="formLabel"><dhv:label name="">Luogo svolgimento attivit&agrave;</dhv:label></td>
						<td><%=animaleDettaglio.getLuogoAttivitaItinerante()%></td>
					</tr>
				</dhv:evaluate>
			</tr>

		</table>
		<%=addHiddenParams(request, "popup|popupType|actionId")%>

		<br />




	</dhv:container>
	<br>
</form>



<script type="text/javascript">

<%
if(request.getAttribute("registrazionecontenutaInRegistroCaniAggressivi")!=null)
{
%>
	alert("Attenzione! Il cane verrà inserito nel REGISTRO UNICO CANI A RISCHIO ELEVATO DI AGGRESSIVITA' O.M. 06/08/2003 e s.m.i. art 3 comma 3");
	
<%
}
%>


</script>