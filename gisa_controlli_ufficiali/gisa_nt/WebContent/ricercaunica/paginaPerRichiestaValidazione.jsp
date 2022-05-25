<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@page import="org.aspcfs.modules.suap.utils.CodiciRisultatiRichiesta"%>
<%@page import="org.aspcfs.modules.opu.base.OperatorePerDuplicati"%>
<%@page import="java.util.ArrayList, java.util.HashMap"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.aspcfs.modules.suap.actions.StabilimentoAction"%>
<%@page import="org.aspcfs.modules.suap.base.LineaProduttiva"%>
<%@page import="org.aspcfs.modules.suap.base.LineaProduttivaList"%>
<%@page import="org.aspcfs.modules.suap.base.Stabilimento"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<jsp:useBean id="EsitoValidazione" class="org.aspcfs.modules.suap.base.RisultatoValidazioneRichiesta" scope="request" />
<jsp:useBean id="Richiesta" class="org.aspcfs.modules.suap.base.Stabilimento" scope="request" />

<script type="text/javascript" src="javascript/jquery.miny_1.7.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<script type="text/javascript" src="ricercaunica/javascript/suapValidazioneScia.js"></script>

<script type="text/javascript" src="dwr/interface/SuapDwr.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<link rel="stylesheet" type="text/css" href="css/jquery-ui-1.9.2.custom.css" />

<%@ include file="../initPage.jsp"%>

<script>
function checkFormValidazione(button, form){
	
	 for(var i=0; i < form.elements.length; i++){
	      if(form.elements[i].value === '' && form.elements[i].hasAttribute('required')){
	        alert('Ci sono dei campi obbligatori non valorizzati!');
	        return false;
	      }
	 }
	
	button.disabled = "disabled";
	button.style.backgroundColor ="grey";
	button.value="ATTENDERE. VALIDAZIONE IN CORSO.";
	form.submit();
}

</script>

<DIV ID='modalWindow' CLASS='unlocked'>
		<P CLASS='wait'>Attendere il completamento dell'operazione...</P>
	</DIV>
	
<script>
loadModalWindowCustom("ATTENDERE IL CARICAMENTO DELLA PAGINA");
</script>




<!-- CONFIGURAZIONE E VISUALIZZAZIONE DEI MESSAGGI DA MOSTRARE ALL'UTENTE DOPO LA VALIDAZIONE -->
<jsp:include page="suap_messaggi_esiti_validazione.jsp" />


<!-- DETTAGLIO MINIMALE DELLA RICHIESTA DA VALIDARE -->
<jsp:include page="suap_validazione_info_impresa.jsp" />



<!-- LINEA DI ATTIVITA DA VALIDARE IN BASE AL TIPO DI OPERAZIONE -->
<%
if(Richiesta.getOperatore().getIdOperazione() ==StabilimentoAction.SCIA_NUOVO_STABILIMENTO )
{
	%>
	<jsp:include page="paginaPerRichiesta.jsp" />
	
	<%
	
}
else
	if(Richiesta.getOperatore().getIdOperazione() == StabilimentoAction.SCIA_AMPLIAMENTO )
{
	%>
		<jsp:include page="paginaPerRichiestaAmpliamento.jsp" />
	
	<%
	
}
	else
		if(Richiesta.getOperatore().getIdOperazione() ==  StabilimentoAction.SCIA_CESSAZIONE )
{
	%>
		<jsp:include page="paginaPerRichiestaCessazione.jsp" />
	
	<%
	
}
		else
			if(Richiesta.getOperatore().getIdOperazione() == StabilimentoAction.SCIA_SOSPENSIONE )
{
	%>
		<jsp:include page="paginaPerRichiestaSospensione.jsp" />
	<%
	
}
	else
				if(Richiesta.getOperatore().getIdOperazione() == StabilimentoAction.SCIA_MODIFICA_STATO_LUOGHI )
{
	%>
		<jsp:include page="paginaPerRichiestaModificaStatoLuoghiPopup.jsp" />
	<%
}

else
	if(Richiesta.getOperatore().getIdOperazione() == StabilimentoAction.SCIA_VARIAZIONE_TITOLARITA )
{
	%>
		<jsp:include page="paginaPerRichiestaVariazione.jsp" />
	<%
	

}
%>
<br><br>

<table>
	<tr>
		<td><%=showError(request, "MailSendError") %><br> <%=showError(request, "GenericError") %>
		</td>
	</tr>

	<tr>
		<td><input type="button" value="chiudi e ricarica scheda pratica"
			onClick="chiudiERicarica()" /></td>
	</tr>
</table>



<!-- DIALOG  PER LA VALIDAZIONE DI UNA LINEA -->

<div id="validazioneDialog">
	<form method="post" action="InterfValidazioneRichieste.do?command=ValidaEConvergi">
		<input type="hidden" name="pIvaImpresa" value="<%=Richiesta.getOperatore().getPartitaIva()%>"> 
		<input type="hidden" name="codiceFiscaleImpresa" value="<%=Richiesta.getOperatore().getCodiceInternoImpresa()%>">
		<input type="hidden" name="idRichiesta" value="<%=Richiesta.getIdOperatore()%>"> 
		<input type="hidden" name="idTipoRichiesta" value="<%=Richiesta.getOperatore().getIdOperazione() %>"> 
		<input type="hidden" name="codice_regionale" value="<%=Richiesta.getNumeroRegistrazione()%>">
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" id="tabValidazioneDialog">
			<tr>
				<th colspan="2"><strong><dhv:label name="">VALIDAZIONE RICHIESTA</dhv:label></strong></th>
			</tr>
		</table>	
	</form>
</div>


<!-- DIALOG  PER LA LA NON VALIDABILITA DI UNA LINEA -->


<div id="nonValidabileDialog">
	<form method="post" action="InterfValidazioneRichieste.do?command=ValidaEConvergi">
		<input type="hidden" name="pIvaImpresa" value="<%=Richiesta.getOperatore().getPartitaIva()%>"> 
		<input type="hidden" name="codiceFiscaleImpresa" value="<%=Richiesta.getOperatore().getCodiceInternoImpresa()%>">
		<input type="hidden" name="idRichiesta" value="<%=Richiesta.getIdOperatore()%>"> 
		<input type="hidden" name="idTipoRichiesta" value="<%=Richiesta.getOperatore().getIdOperazione() %>"> 
		<input type="hidden" name="codice_regionale" value="<%=Richiesta.getNumeroRegistrazione()%>">
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" id="tabNonValidabileDialog">
			<tr>
				<th colspan="2"><strong><dhv:label name="">RICHIESTA NON VALIDABILE</dhv:label></strong></th>
			</tr>
		</table>	
	</form>
</div>

<script>
loadModalWindowUnlock();
</script>
