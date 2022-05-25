<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.constants.Specie"%>
<%@page import="it.us.web.bean.vam.lookup.LookupPersonaleInterno"%>
<%@page import="it.us.web.bean.SuperUtente"%>
<%@page import="it.us.web.bean.vam.lookup.LookupTipiRichiedente"%>
<%@page import="it.us.web.bean.BUtente"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn"%>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us"%>
<%@ taglib uri="/WEB-INF/vam.tld" prefix="vam"%>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa"%>
<%@page import="java.util.Date"%>

<script language="JavaScript" type="text/javascript" src="js/vam/accettazioneMultipla/addAcc.js"></script>

<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/Test.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>


<%@page import="it.us.web.constants.SpecieAnimali"%>
<%@page import="it.us.web.constants.IdOperazioniBdr"%>
<%@page import="it.us.web.constants.IdRichiesteVarie"%>

<%@page import="it.us.web.constants.IdOperazioniDM"%>
<%@page import="it.us.web.bean.vam.Accettazione"%>
<%@page import="it.us.web.bean.vam.AnimaleAnagrafica"%>
<%@page import="it.us.web.bean.vam.lookup.LookupAsl"%>
<%@page import="it.us.web.bean.vam.lookup.LookupAssociazioni"%>
<%@page import="it.us.web.bean.vam.lookup.LookupOperazioniAccettazione"%>
<%@page import="it.us.web.bean.vam.lookup.LookupTipoTrasferimento"%>
<%@page import="it.us.web.bean.vam.lookup.LookupAccettazioneAttivitaEsterna"%>
<%@page import="it.us.web.bean.vam.lookup.LookupComuni"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%
    ArrayList<Accettazione> accs = (ArrayList<Accettazione>) request.getAttribute("accettazioni");
    Accettazione acc = accs.get(0);
	SpecieAnimali specie = (SpecieAnimali) request.getAttribute("specie");

	ArrayList<AnimaleAnagrafica> anagraficheAnimali = (ArrayList<AnimaleAnagrafica>)request.getAttribute("anagraficheAnimali");
	
	ArrayList<LookupTipiRichiedente> richiedenti = (ArrayList<LookupTipiRichiedente>) request.getAttribute("richiedenti");
	ArrayList<LookupTipiRichiedente> richiedentiForzaPubblica = (ArrayList<LookupTipiRichiedente>) request.getAttribute("richiedentiForzaPubblica");
	ArrayList<LookupAsl> asl = (ArrayList<LookupAsl>) request.getAttribute("asl");
	ArrayList<LookupAsl> listaAltreAsl = (ArrayList<LookupAsl>) request	.getAttribute("listaAltreAsl");
	ArrayList<LookupAssociazioni> associazioni = (ArrayList<LookupAssociazioni>) request.getAttribute("associazioni");
	ArrayList<LookupOperazioniAccettazione> operazioniInBdr = (ArrayList<LookupOperazioniAccettazione>)request.getAttribute("operazioniInBdr");
	ArrayList<LookupComuni> lookupComuni  = (ArrayList<LookupComuni>) request.getServletContext().getAttribute("listComuni");
	BUtente utente = (BUtente) request.getSession().getAttribute("utente");
	LookupOperazioniAccettazione operazioneIscrizione = (LookupOperazioniAccettazione)request.getAttribute("operazioneIscrizione");
%>

<script language="JavaScript" type="text/javascript" src="js/azionijavascript.js"></script>
<script>
function test(){
	var mess="";
	
	var datiRic = controllaInfo();
	if (datiRic==1) {
		mess=mess+"\n -Inserire Nome e Cognome del richiedente";
	}
	
	var dataFutura = controllaDataFutura(document.getElementById("data").value);
	if (dataFutura==1){
		mess=mess+"\n -Impossibile inserire una data futura";
	}
	return mess;
}
</script>

	<form name="form" action="vam.accettazioneMultipla.Add.us?flagAnagrafe=true"
		method="post">
		<input type="submit" value="Salva"
			onclick="var mess=test(); if(mess==''){if(myConfirm('Si conferma la data accettazione ' + document.getElementById('data').value + '?')){return checkform('<%=utente.getId()%>','<%=acc.getAnimale().getId()%>','<%=utente.getClinica().getId() %>', <%=utente.getRuolo().equals("Referente Asl") || utente.getRuolo().equals("14")%>,false)}else{ return false;}}else{alert(mess); return false;}" />

<fieldset>
	<legend>
		Accettazione Multipla del <input type="text" 
		    value="<fmt:formatDate type="date" value="<%=new Date()%>" pattern="dd/MM/yyyy" />" 
			id="data" name="data" maxlength="10" size="10" readonly="readonly" />
		<img src="images/b_calendar.gif" alt="calendario" id="id_img_1" />
		<script type="text/javascript">
					Calendar.setup({
						inputField  :    "data",      // id of the input field
						ifFormat    :    "%d/%m/%Y",  // format of the input field
						button      :    "id_img_1",  // trigger for the calendar (button ID)
						// align    :    "rl,      // alignment (defaults to "Bl")
						singleClick :    true,
						timeFormat	:   "24",
						showsTime	:   false
					});					    

				</script>
	</legend>
	<table class="tabella" style="width: 99%">
<%
	Iterator<Accettazione> accsIter = accs.iterator();
    int i=1;
	while(accsIter.hasNext())
	{
		acc = accsIter.next();		
%>
		<input type="hidden" name="microchip<%=i%>" id="microchip<%=i%>" value="<%=acc.getAnimale().getIdentificativo()%>"/>
		<tr>
			<th><%=acc.getAnimale().getLookupSpecie().getDescription()%></th>
					<c:set var="proprietarioCognome"
						value="<%=acc.getProprietarioCognome()%>" />
					<c:choose>
						<c:when test="<%=acc.getProprietarioCognome()!=null && acc.getProprietarioCognome().startsWith(\"<b>\") %>">
							<th>Colonia</th>
						</c:when>
						<c:otherwise>
							<th>Proprietario <c:if test="<%=acc.getProprietarioTipo()!=null && !acc.getProprietarioTipo().equals(\"null\")%>"><%=acc.getProprietarioTipo()%></c:if></th>
						</c:otherwise>
					</c:choose>
		</tr>
		<tr>
			<td style="vertical-align: top;">
				<ul>
					<li><b>Identificativo:</b> <%=acc.getAnimale().getIdentificativo() %></li>
					<li><b>Tatuaggio/II MC:</b> <%=(acc.getAnimale().getTatuaggio() != null) ? acc.getAnimale().getTatuaggio() : "---" %></li>
					<c:choose>
						<c:when test="<%=(acc.getAnimale().getLookupSpecie().getId()==3) %>">
							<li><b>Et&agrave;:</b> <fmt:formatDate type="date"
									value="<%=acc.getAnimale().getDataNascita()%>" pattern="dd/MM/yyyy" />
  								<%if (acc.getAnimale().getEta()!=null){%><%=acc.getAnimale().getEta()%><%}%></li>
						</c:when>
						<c:otherwise>
							<li><b>Data nascita:</b> <fmt:formatDate type="date"
									value="<%=acc.getAnimale().getDataNascita()%>" pattern="dd/MM/yyyy" /> 
							</li>
				</c:otherwise>
					</c:choose>
			 		
<%
	AnimaleAnagrafica anag = (AnimaleAnagrafica)request.getAttribute("anagraficaAnimale"+i);
%>

<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<li>
	<b>
			Razza:
	</b> 
<%
		out.println(anag.getRazza());
%>
</li>
<li>
	<b>
		Sesso:
	</b>
<%
		out.println(anag.getSesso());
%>		
</li>
		
<c:if test="<%anag.getAnimale().getLookupSpecie().getId()==Specie.CANE%>">
	<li>
		<b>
			Taglia:
		</b> 
<%
		out.println(anag.getTaglia());
%>
	</li>
</c:if>
			
	<li>
		<b>
			Mantello:
		</b> 
<%
		out.println(anag.getMantello());
%>
	</li>

<li>
	<b>
		Stato attuale:
	</b> 
<%
		out.println(anag.getStatoAttuale());
%>
</li>

	<li>
		<b>
			Sterilizzazione:
		</b> 
<%
		out.println(anag.getSterilizzato());
%>
	</li>

					<c:if test="<%=acc.getAnimale().getClinicaChippatura()!=null %>">
						<li><b>Microchippato nella clinica
								<%=acc.getAnimale().getClinicaChippatura().getNome() %></b></li>
					</c:if>
				</ul>
			</td>
			<td style="vertical-align: top;">
				<ul>
					<c:choose>
						<c:when test="<%=(acc.getProprietarioCognome()!=null && acc.getProprietarioCognome().startsWith(\"<b>\"))%>">
							<li><%=acc.getProprietarioCognome()%></li>
							<li><b>Indirizzo:</b> <%=acc.getProprietarioIndirizzo()%>, <%=acc.getProprietarioComune()%>
								<c:if test="<%=acc.getProprietarioProvincia()!=null && !acc.getProprietarioProvincia().equals(\"\")%>">
												(<%=acc.getProprietarioProvincia()%>)						
											</c:if> <c:if test="<%=acc.getProprietarioCap()!=null && !acc.getProprietarioCap().equals(\"\")%>">
												- <%=acc.getProprietarioCap()%>
								</c:if></li>
							<li><b>Nominativo Referente:</b> <%=acc.getProprietarioNome()%></li>
							<li><b>Codice fiscale Referente:</b><% if (acc.getProprietarioCodiceFiscale()!=null){%><%=acc.getProprietarioCodiceFiscale()%><%} %></li>
							<li><b>Documento Identità Referente:</b><% if (acc.getProprietarioDocumento()!=null){%><%=acc.getProprietarioDocumento()%><%} %></li>
							<li><b>Telefono Referente:</b><% if (acc.getProprietarioTelefono()!=null){%><%=acc.getProprietarioTelefono()%><%} %></li>
						</c:when>
						
						<c:when test="<%=acc.getProprietarioCognome()!=null && acc.getProprietarioTipo()!=null && ( acc.getProprietarioTipo().equals(\"Importatore\") || acc.getProprietarioTipo().equals(\"Operatore Commerciale\"))%>">
							<li><b>Ragione Sociale: </b><% if (acc.getProprietarioNome()!=null){%><%=acc.getProprietarioNome()%><%} %></li>
							<li><b>Partita Iva: </b><% if (acc.getProprietarioCognome()!=null){%><%=acc.getProprietarioCognome()%><%} %></li>
							<li><b>Rappr. Legale: </b><% if (acc.getProprietarioCodiceFiscale()!=null){%><%=acc.getProprietarioCodiceFiscale()%><%} %></li>
							<li><b>Telefono struttura(principale): </b><% if (acc.getProprietarioTelefono()!=null){%><%=acc.getProprietarioTelefono()%><%} %></li>
							<li><b>Telefono struttura(secondario): </b><% if (acc.getProprietarioDocumento()!=null){%><%=acc.getProprietarioDocumento()%><%} %></li>
							<u>Sede operativa</u>
							<li><b>Indirizzo: </b><% if (acc.getProprietarioIndirizzo()!=null){%><%=acc.getProprietarioIndirizzo()%><%} %></li>
							<li><b>CAP: </b><% if (acc.getProprietarioCap()!=null){%><%=acc.getProprietarioCap()%><%} %></li>
							<li><b>Comune: </b><% if (acc.getProprietarioComune()!=null){%><%=acc.getProprietarioComune()%><%} %></li>
							<li><b>Provincia: </b><% if (acc.getProprietarioProvincia()!=null){%><%=acc.getProprietarioProvincia()%><%} %></li>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="<%=acc.getProprietarioTipo()!=null && acc.getProprietarioTipo().equals(\"Canile\") %>">
									<li><b>Ragione Sociale: </b><%if (acc.getProprietarioNome()!=null){%><%=acc.getProprietarioNome() %><%} %></li>
									<li><b>Partita Iva: </b><% if (acc.getProprietarioCognome()!=null){%><%=acc.getProprietarioCognome()%><%} %></li>
									<li><b>Rappr. Legale: </b><% if (acc.getProprietarioCodiceFiscale()!=null){%><%=acc.getProprietarioCodiceFiscale()%><%} %></li>
								</c:when>
								<c:when test="<%=acc.getProprietarioTipo()!=null && !acc.getProprietarioTipo().equals(\"Canile\") %>">
										<c:if test="<%=!acc.getRandagio()%>">
											<li><b>Cognome: </b><%if (acc.getProprietarioCognome()!=null){%><%=acc.getProprietarioCognome()%><%} %></li>
										</c:if>
										<li><b>Nome: </b><%if (acc.getProprietarioNome()!=null){%><%=acc.getProprietarioNome() %><%} %></li>
								</c:when>	
								<c:when test="<%=acc.getProprietarioTipo()==null && acc.getRandagio()%>">
									<li><b>Nome: </b><%=request.getParameter("proprietarioNome")%></li>
								</c:when>
								<c:otherwise>
									<li><b>Cognome: </b><%if (acc.getProprietarioCognome()!=null){%><%=acc.getProprietarioCognome()%><%} %></li>
									<li><b>Nome: </b><%if (acc.getProprietarioNome()!=null){%><%=acc.getProprietarioNome() %><%} %></li>
								</c:otherwise>
							</c:choose>
							<c:if test="<%=!acc.getRandagio()%>">
							<c:if test="<%=acc.getProprietarioTipo()!=null && !acc.getProprietarioTipo().equals(\"Canile\") %>">
								<li><b>Codice Fiscale:</b> <% if (acc.getProprietarioCodiceFiscale()!=null){%><%=acc.getProprietarioCodiceFiscale()%><%} %></li></c:if>
								<li><b>Documento:</b> <% if (acc.getProprietarioDocumento()!=null){%><%=acc.getProprietarioDocumento()%><%} %></li>
								<li><b>Indirizzo:</b> <% if (acc.getProprietarioIndirizzo()!=null){%><%=acc.getProprietarioIndirizzo()%><%} %></li>
								<li><b>CAP:</b> <% if (acc.getProprietarioCap()!=null){%><%=acc.getProprietarioCap()%><%} %></li>
								<li><b>Comune:</b> <% if (acc.getProprietarioComune()!=null){%><%=acc.getProprietarioComune()%><%} %></li>
								<li><b>Provincia:</b><% if (acc.getProprietarioProvincia()!=null){%><%=acc.getProprietarioProvincia()%><%} %></li>
								<li><b>Telefono:</b> <% if (acc.getProprietarioTelefono()!=null){%><%=acc.getProprietarioTelefono()%><%} %></li>
							</c:if>
						</c:otherwise>
					</c:choose>
				</ul>
			</td>
			<!--  /c:if-->
		</tr>

	<input type="hidden" name="idAnimale<%=i%>" value="<%=acc.getAnimale().getId()%>" /> 
	<input type="hidden"
		name="proprietarioCognome<%=i%>" value="<%=acc.getProprietarioCognome()%>" />
	<input type="hidden" name="proprietarioTipo<%=i%>"
		<c:if test="<%=acc.getProprietarioTipo()!=null%>"> value="<%=acc.getProprietarioTipo()%>"</c:if>
		<c:if test="<%=acc.getProprietarioTipo()==null%>"> value=<%=request.getParameter("proprietarioTipo")%></c:if> /> <input type="hidden"
		name="proprietarioNome<%=i%>" value="<%=acc.getProprietarioNome()%>" /> <input
		type="hidden" name="proprietarioCodiceFiscale<%=i%>"
		value="<%=acc.getProprietarioCodiceFiscale()%>" /> <input
		type="hidden" name="proprietarioDocumento<%=i%>"
		value="<%=acc.getProprietarioDocumento()%>" /> <input type="hidden"
		name="proprietarioIndirizzo<%=i%>"
		value="<%=acc.getProprietarioIndirizzo()%>" /> <input type="hidden"
		name="proprietarioCap<%=i%>" value="<%=acc.getProprietarioCap()%>" /> <input
		type="hidden" name="proprietarioComune<%=i%>"
		value="<%=acc.getProprietarioComune()%>" /> <input type="hidden"
		name="proprietarioProvincia<%=i%>"
		value="<%=acc.getProprietarioProvincia()%>" /> <input type="hidden"
		name="proprietarioTelefono<%=i%>" value="<%=acc.getProprietarioTelefono()%>" />
	<input type="hidden" name="randagio<%=i%>" value="<%=acc.getRandagio()%>" />
	<input type="hidden" name="sterilizzato<%=i%>"
		value="<%=acc.getSterilizzato()%>" />
<%
    i++;
	}
%>
	</table>
</fieldset>

<fieldset>
	<legend>Dati Richiedente</legend>

	<fieldset>
		<legend>
			Tipo richiedente <select id="tipoRichiedente" name="tipoRichiedente"
				onchange="document.getElementById('idDipendenteAsl').value='';document.getElementById('idDipendenteAslTemp').value='';
			  document.getElementById('personaleAslSelezionato').innerHTML='';document.getElementById('personaleAslSelezionatoTemp').innerHTML='';
			  selezionaDivRichiedente(this.value)">

				<%
			LookupTipiRichiedente ltr = new LookupTipiRichiedente();
			Iterator i2 = richiedenti.iterator();
			while (i2.hasNext()) {
				ltr = (LookupTipiRichiedente) i2.next();
		%>
				<option value="<%=ltr.getId()%>"
					<c:if test="<%=(ltr == acc.getLookupTipiRichiedente())%>">selected="selected"</c:if>><%=ltr.getDescription()%>
				</option>
				<%
		}
	%>


				<optgroup label="Altre Autorità">

					<%
				ltr = new LookupTipiRichiedente();
				i2 = richiedentiForzaPubblica.iterator();
				while (i2.hasNext()) {
					ltr = (LookupTipiRichiedente) i2.next();
			%>
					<option value="<%=ltr.getId()%>"
						<c:if test="<%=(ltr == acc.getLookupTipiRichiedente())%>">selected="selected"</c:if>><%=ltr.getDescription()%>
					</option>
					<%
			}
		%>
				</optgroup>
			</select>
		</legend>

		<div id="div_richiedente_privato" style="display: block">
				
			<div id="div_richiedente_privato_non_proprietario">
				
<table class="tabella" style="width: 99%">
	<tr align="right">
		<td width="33%">Nome <font color="red">*</font><input value="<%=acc.getProprietarioNome() %>" type="text" id="richiedenteNome" name="richiedenteNome" maxlength="64"> </td>
		<td width="33%">Cognome <font color="red">*</font><input value="<%=acc.getProprietarioCognome() %>" type="text" id="richiedenteCognome" name="richiedenteCognome" maxlength="64"> </td>
		<td width="33%">Telefono <input value="<%=acc.getProprietarioTelefono() %>" type="text" name="richiedenteTelefono" maxlength="16"> </td>
		<td width="33%">&nbsp;</td>
	</tr>
	<tr align="right">
		<td width="33%">Codice Fiscale <input value="<%=acc.getProprietarioCodiceFiscale() %>" type="text" name="richiedenteCodiceFiscale" maxlength="16" maxlength="64"> </td>
		<td width="33%">Documento <input value="<%=acc.getProprietarioDocumento()%>" type="text" name="richiedenteDocumento" maxlength="64"> </td>
		<td width="33%">Residenza <input value="<%=acc.getProprietarioIndirizzo() + " " + acc.getProprietarioComune() + " (" + acc.getProprietarioProvincia() + ")"%>" type="text" name="richiedenteResidenza" maxlength="64"> </td>
		<td width="33%">&nbsp;</td>
	</tr>
</table>

<script>
	function controllaInfo(){
		var ck = document.getElementById("richiedenteProprietario"); //ckbox coincide con il proprietario
		var e = document.getElementById("tipoRichiedente"); 		 //select tipi di proprietario
		var strUser = e.options[e.selectedIndex].value; 
		if(strUser==1){		//privato
			if (ck!=null){
				if(ck.checked==false){
					if (document.getElementById("richiedenteNome").value=="") return 1;
					if (document.getElementById("richiedenteCognome").value=="") return 1;
				}
				else return 0;
			}
			else {
				if (document.getElementById("richiedenteNome").value=="") return 1;
				if (document.getElementById("richiedenteCognome").value=="") return 1;
			}
		}
	}
</script>
				
			
			
			</div>

		</div>

		<div id="div_richiedente_personale_asl" style="display: none;">
			Asl <font color="red">*</font> <select id="idRichiedenteAsl"
				name="idRichiedenteAsl" disabled="disabled"
				onchange="document.getElementById('idDipendenteAsl').value='';document.getElementById('idDipendenteAslTemp').value='';
			  document.getElementById('personaleAslSelezionato').innerHTML='';document.getElementById('personaleAslSelezionatoTemp').innerHTML='';">
				<option value="-1">- Seleziona -</option>
				<%
		LookupAsl la = new LookupAsl();
		i2 = asl.iterator();
		while (i2.hasNext()) {
			la = (LookupAsl) i2.next();
	%>
				<option value="<%=la.getId()%>"
					<c:if test="<%=(acc.getRichiedenteAsl()!=null && (la.getId() == acc.getRichiedenteAsl().getId()))%>">selected="selected"</c:if>><%=la.getDescription()%>
				</option>
				<%
		}
	%>
			</select> <a id="linkChoosePersAsl"
				style="cursor: pointer; text-decoration: underline; color: blue;"
				onclick="selezionaPersAsl();">Seleziona</a> <input type="hidden"
				name="idDipendenteAsl" id="idDipendenteAsl"
				value="<%=acc.getPersonaleAslId()%>" /> <input type="hidden"
				name="idDipendenteAslTemp" id="idDipendenteAslTemp"
				value="<%=acc.getPersonaleAslId()%>" />

<!-- VEDERE -->
<%
	Iterator<LookupAsl> asl2 = ((ArrayList<LookupAsl>)request.getAttribute("asl")).iterator();
	while(asl2.hasNext())
	{
		LookupAsl temp = asl2.next();
%>
	<div id="operatori_asl_<%=temp.getId()%>" style="display: none;">
		<table class="tabella" id="tabellaPersAsl">
		<tr>
	   		<th colspan="3">
			</th>
		</tr>
			<c:set var="aslId" value="<%=temp.getId()%>"/>
			<c:forEach items="<%=(ArrayList<BUtente>)request.getAttribute("personaleAsl"+temp.getId()) %>" var="utente">
				<tr class="${i % 2 == 0 ? 'odd' : 'even'}">
					<td colspan="3">
						<input type="checkbox" value="${utente.id}"  
											   id="persAsl${utente.id}"  
											   name="persAsl${utente}"
									   		   onclick="popolaPersAslSelezionati('${utente.id}','${utente}');"
							<us:contain collection="${accettazione.personaleAsl}" item="<%=(ArrayList<BUtente>)request.getAttribute("personaleAsl"+temp.getId()) %>">
								checked="checked"
							</us:contain>	
						/>
						${utente}
					</td>
				</tr>
				<c:set var="i" value='${i+1}'/>
			</c:forEach>
		</table>
	</div>
<%
	}
%>
<!--  -->

		</div>

		<table id="personaleAslSelezionato">
			<%
		i2 = acc.getPersonaleAsl().iterator();
		SuperUtente s = new SuperUtente();
		while (i2.hasNext()) {
			s = (SuperUtente) i2.next();
	%>
			<tr>
				<td><%=s%></td>
			</tr>
			<%
		}
	%>
		</table>

		<table id="personaleAslSelezionatoTemp" style="display: none;">
			<%
		i2 = acc.getPersonaleAsl().iterator();
		s = new SuperUtente();
		while (i2.hasNext()) {
			s = (SuperUtente) i2.next();
	%>
			<tr>
				<td><%=s%></td>
			</tr>
			<%
		}
	%>
		</table>

		<div id="div_richiedente_associazione" style="display: none;">
			Nome Associazione <font color="red">*</font> <select
				name="idAssociazione" id="idAssociazione" disabled="disabled">
				<option value="-1">- Seleziona -</option>
				<%
		LookupAssociazioni lass = new LookupAssociazioni();
		i2 = associazioni.iterator();
		while (i2.hasNext()) {
			lass = (LookupAssociazioni) i2.next();
	%>
				<option value="<%=lass.getId()%>"
					<c:if test="<%=(acc.getRichiedenteAssociazione()!=null && (lass.getId() == acc.getRichiedenteAssociazione().getId()) )%>">selected="selected"</c:if>><%=lass.getDescription()%>
				</option>
				<%
		}
	%>

			</select>
		</div>

		<div id="div_richiedente_forza_pubblica" style="display: none;">
			<table class="tabella" style="width: 99%">
				<tr>
					<th colspan="3">Dati Comando</th>
				</tr>
				<tr align="right">
					<td width="33%">Comune <input
					<% if (acc.getRichiedenteForzaPubblicaComune()!=null) {%>
						value="<%=acc.getRichiedenteForzaPubblicaComune()%>"<%} else {%>value=""<%} %>
						type="text" name="richiedenteForzaPubblicaComune" 
						maxlength="64" disabled="disabled"></td>
					<td width="33%">Provincia <input
						<% if (acc.getRichiedenteForzaPubblicaProvincia()!=null) {%>
						value="<%=acc.getRichiedenteForzaPubblicaProvincia()%>"<%} else {%>value=""<%} %>
						type="text" name="richiedenteForzaPubblicaProvincia"
						maxlength="64" disabled="disabled"></td>
					<td width="33%">&nbsp;</td>
				</tr>
				<tr align="right">
					<td width="33%">Comando <input
					<% if (acc.getRichiedenteForzaPubblicaComando()!=null) {%>
						value="<%=acc.getRichiedenteForzaPubblicaComando()%>"<%} else {%>value=""<%} %>
						type="text"	name="richiedenteForzaPubblicaComando" 
						maxlength="64"	disabled="disabled"></td>
					<td width="33%"></td>
					<td width="33%">&nbsp;</td>
				</tr>
			</table>
		</div>

		<div id="div_richiedente_altro" style="display: none;">
			Specificare tipologia richiedente <input 
			<% if (acc.getRichiedenteAltro()!=null) {%>
				value="<%=acc.getRichiedenteAltro()%>"<%} else {%>value=""<%} %> type="text"
				name="richiedenteAltro" maxlength="64" />
		</div>

	</fieldset>

	<fieldset>
		<legend>
			<input type="checkbox" name="interventoPersonaleInterno"
				id="interventoPersonaleInterno"
				onchange="document.getElementById('personaleInternoSelezionato').innerHTML='';document.getElementById('personaleInternoSelezionatoTemp').innerHTML='';
		   			 document.getElementById('idRichiedenteInterno').value='';document.getElementById('idRichiedenteInternoTemp').value='';"
				>
			Intervento Personale Interno
		</legend>
		<a id="linkChoosePersInt1"
			style="cursor: pointer; text-decoration: underline; color: blue;"
			onclick="selezionaPersInt();">Seleziona</a> <input type="hidden"
			name="idRichiedenteInterno" id="idRichiedenteInterno"
			value="<%=acc.getPersonaleInterno()%>" /> <input type="hidden"
			name="idRichiedenteInternoTemp" id="idRichiedenteInternoTemp"
			value="<%=acc.getPersonaleInterno()%>" />

		<div id="div_richiedente_personale_interno" style="display: none;">
			<!--  select id="idPersonaleInterno" multiple="multiple" size="10" name="idRichiedenteInterno"-->
			<table class="tabella" id="tabellaPersInt">
				<tr>
					<th colspan="3"></th>
				</tr>

				<%
		LookupPersonaleInterno lpi = new LookupPersonaleInterno();
		ArrayList<LookupPersonaleInterno> personaleInterno = (ArrayList<LookupPersonaleInterno>)request.getAttribute("personaleInterno");
		i2 = personaleInterno.iterator();
		while (i2.hasNext()) {
			lpi = (LookupPersonaleInterno) i2.next();%>
				<c:if test="<%=lpi.getEnabled() == true%>">
					<tr class="${i % 2 == 0 ? 'odd' : 'even'}"
						id="trTuttiEsiti${i}_${organoTipoEsito.id}">
						<td colspan="3"><input type="checkbox"
							value="<%=lpi.getId()%>" id="persInt<%=lpi.getId()%>"
							name="persInt<%=lpi.getNominativo()%>"
							onclick="popolaPersIntSelezionati('<%=lpi.getId()%>','<%=lpi.getNominativo()%>')"
							<us:contain collection="${accettazione.personaleInterno}" item="<%=lpi%>">
								checked="checked"
							</us:contain> />
							<%=lpi.getNominativo()%></td>
					</tr>
					<c:set var="i" value='${i+1}' />
				</c:if>

				<%  }	  %>
			</table>

			<!--/select-->
		</div>


		<table id="personaleInternoSelezionato">
			<%
		i2 = acc.getPersonaleInterno().iterator();
	 	lpi = new LookupPersonaleInterno();
		while (i2.hasNext()) {
			lpi = (LookupPersonaleInterno) i2.next();
	%>
			<tr>
				<td><%=lpi.getNominativo()%></td>
			</tr>
			<%
		}
	%>
		</table>
		<table id="personaleInternoSelezionatoTemp" style="display: none;">
			<%
		i2 = acc.getPersonaleInterno().iterator();
	 	lpi = new LookupPersonaleInterno();
		while (i2.hasNext()) {
			lpi = (LookupPersonaleInterno) i2.next();
	%>
			<tr>
				<td><%=lpi.getNominativo()%></td>
			</tr>
			<%
		}
	%>
		</table>
	</fieldset>

		<script type="text/javascript">
		document.getElementById('idRichiedenteInterno').value = "";
		document.getElementById('idRichiedenteInternoTemp').value = "";
		document.getElementById('idDipendenteAsl').value = "";
		document.getElementById('idDipendenteAslTemp').value = "";
	</script>

</fieldset>

<fieldset>
	<legend>
		Motivazioni/Operazioni Richieste <font color="red">*</font>
	</legend>

		<table class="tabella" style="width: 99%">
			<tr valign="top">
				<td>
					<table width="100%">
						<tr>
							<th>Anagrafi Regionali</th>
						</tr>
						<%
						    i2 = operazioniInBdr.iterator();
							LookupOperazioniAccettazione loa = new LookupOperazioniAccettazione();
							while (i2.hasNext()){
								loa=(LookupOperazioniAccettazione)i2.next(); %>
								<tr>
								<td>
									<input type="hidden" id="op_<%=loa.getId() %>" name="op_<%=loa.getId() %>" value="true"/>
									<input type="checkbox" disabled=false checked="checked" />
									<%=loa.getDescription() %>		
						<%	} %>
						</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

</fieldset>
<br />

<font color="red">* Campi obbligatori</font>
<br />
	<input type="submit" value="Salva"
			onclick="var mess=test(); if(mess==''){if(myConfirm('Si conferma la data accettazione ' + document.getElementById('data').value + '?')){return checkform('<%=utente.getId()%>','<%=acc.getAnimale().getId()%>','<%=utente.getClinica().getId() %>', <%=utente.getRuolo().equals("Referente Asl") || utente.getRuolo().equals("14")%>,false)}else{ return false;}}else{alert(mess); return false;}" />
</form>

<script type="text/javascript">

//questa variabile serve a determinare quale div va nascosto quando si seleziona un nuovo tipo di richiedente
			var richiedentePrecedente = 1;
			function selezionaDivRichiedente( idTipoRichiedente )
			{
			    //alert( 'Selezionato il valore: ' + idTipoRichiedente );
			    if(idTipoRichiedente!='personaleInterno')
			    {
					var divHash = new Array
						(
							"-none-",
							"div_richiedente_privato",
							"",
							"div_richiedente_personale_asl",
							"div_richiedente_associazione",
							"div_richiedente_forza_pubblica",
							"div_richiedente_forza_pubblica",
							"div_richiedente_forza_pubblica",
							"div_richiedente_forza_pubblica",
							"div_richiedente_forza_pubblica",
							"div_richiedente_forza_pubblica",
							"div_richiedente_forza_pubblica",
							"div_richiedente_altro",
							"div_richiedente_forza_pubblica",
							"div_richiedente_forza_pubblica",
							"div_richiedente_forza_pubblica"
						);
	
					toggleDiv( divHash[ richiedentePrecedente ] );
					toggleDiv( divHash[ idTipoRichiedente ] );
					
					document.getElementById('div_richiedente_privato_non_proprietario').style.display='block';
					richiedentePrecedente = idTipoRichiedente;
				}
				else
				{
					toggleDiv( "div_richiedente_personale_interno" );
				}
			};
	
			var idAslPrecedente = -1;
			function selezionaOrganigrammaAsl( idAsl )
			{
				if( idAslPrecedente > 0 )
				{
					toggleDiv( "operatori_asl_" + idAslPrecedente );
				}
				
				if( idAsl > 0 )
				{
					toggleDiv( "operatori_asl_" + idAsl );
					idAslPrecedente = idAsl;
				}
				else
				{
					idAslPrecedente = -1;
				}
	
			};

			function selezionaDipendenteAsl( idDipendente )
			{
				document.getElementById("idDipendenteAsl").value = idDipendente;
			}

function mySubmit( messaggio )
{
	var test = true;
	var tipoRich = $( '#tipoRichiedente' )[0].value;
	var errore;
	
	if( tipoRich == 2 ) //personale interno
	{
		if( $( "#idPersonaleInterno" )[0].value < 0 )
		{
			test = false;
			errore = "Selezionare il personale richiedente";
		}
	}
	else if( tipoRich == 3 ) //personale asl
	{
		var asl = $( '#idRichiedenteAsl' )[0].value;
		if( asl < 0 )
		{
			test = false;
			errore = "Selezionare l'asl del richiedente";
		}
		else if( $( '#idPersonaleAsl' + asl )[0].value < 0 )
		{
			test = false;
			errore = "Selezionare il personale richiedente";
		}
	}
	else if( tipoRich ==  4) //associazione
	{
		var associazione = $( '#idAssociazione' )[0].value;
		if( associazione < 0 )
		{
			test = false;
			errore = "Selezionare l'associazione";
		}
	}

	if( test )
	{
		return myConfirm( messaggio );
	}
	else
	{
		alert( errore );
		return false;
	}
}

var opSelezionate = new Array();

function mostraPopupChoosePersInt(){

		$( "#choosePersInt" ).dialog({
			height: screen.height/2,
			modal: true,
			autoOpen: true,
			closeOnEscape: true,
			show: 'blind',
			resizable: true,
			draggable: true,
			title: 'Seleziona Personale Interno',
			width: screen.width/2,
			buttons: {
				"Annulla": function() {
					confermaModifiche(false);
					$( this ).dialog( "close" );
				},
				"Ok": function() {
					confermaModifiche(true);
					$( this ).dialog( "close" );
				}
			}
		});
	}
	

function mostraPopupChoosePersAsl(){

		$( "#choosePersAsl" ).dialog({
			height: screen.height/2,
			modal: true,
			autoOpen: true,
			closeOnEscape: true,
			show: 'blind',
			resizable: true,
			draggable: true,
			title: 'Seleziona Personale Asl',
			width: screen.width/2,
			buttons: {
				"Annulla": function() {
					confermaModificheAsl(false);
					$( this ).dialog( "close" );
				},
				"Ok": function() {
					confermaModificheAsl(true);
					$( this ).dialog( "close" );
				}
			}
		});
	}
	
abilitaDisabilitaAttivitaEsterne(document.getElementById('op_'+${idRichiesteVarie.attivitaEsterne}));
</script>

<script type="text/javascript">
		popolaOpSelezionate('1');
</script>


<script type="text/javascript">

<% if (acc.getProprietarioTipo()!=null && acc.getProprietarioTipo().equalsIgnoreCase("colonia")){%>
	toggleDiv('div_richiedente_privato_non_proprietario');
<%}%>

</script>


<script>
dwr.engine.setErrorHandler(errorHandler);
dwr.engine.setTextHtmlHandler(errorHandler);

function errorHandler(message, exception){
    //Session timedout/invalidated

    if(exception && exception.javaClassName== 'org.directwebremoting.impl.LoginRequiredException'){
        alert(message);
        //Reload or display an error etc.
        window.location.href=window.location.href;
    }
    else
        alert('Errore Nella Chiamata Remota : '+exception.javaClassName);
 }
</script>









