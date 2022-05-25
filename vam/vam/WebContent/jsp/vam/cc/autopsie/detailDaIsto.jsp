<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa"%>
<%@page import="it.us.web.bean.vam.Autopsia"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="it.us.web.bean.vam.lookup.LookupAutopsiaEsitiEsami"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="it.us.web.bean.vam.lookup.LookupAutopsiaOrgani"%>
<%@page import="it.us.web.bean.vam.lookup.LookupAutopsiaTipiEsami"%>

<script language="JavaScript" type="text/javascript"
	src="js/vam/cc/autopsie/detail.js"></script>

<%
	Autopsia a 									                      = (Autopsia)request.getAttribute("a");
	ArrayList<LookupAutopsiaOrgani> listOrganiAutopsia	              = (ArrayList<LookupAutopsiaOrgani>)request.getAttribute("listOrganiAutopsia");
	ArrayList<LookupAutopsiaTipiEsami> listTipiAutopsia 			  = (ArrayList<LookupAutopsiaTipiEsami>)request.getAttribute("listTipiAutopsia");
	HashMap<String, Set<LookupAutopsiaEsitiEsami>> allOrganiTipiEsiti = (HashMap<String, Set<LookupAutopsiaEsitiEsami>>)request.getAttribute("allOrganiTipiEsiti");
%>

<c:set var="readonlyImmagini" value="true" />
			<c:if test="${cc.accettazione.animale.necroscopiaNonEffettuabile==false}">
				<c:if test="${utenteStrutturaNecroscopia || utente.ruoloByTalos=='HD1' || utente.ruoloByTalos=='HD2'}">
					<c:if test="${a.dataEsito!=null}">
						<input type="button" value="Modifica Necroscopia" onclick="attendere(), location.href='vam.cc.autopsie.ToEdit.us'">
					</c:if>
					<c:if test="${a.dataEsito==null}">
						<input type="button" value="Inserisci Necroscopia" onclick="attendere(), location.href='vam.cc.autopsie.ToEdit.us'">
					</c:if>
				</c:if>
			</c:if>
<input type="button" value="Immagini" onclick="javascript:avviaPopup( 'vam.cc.autopsie.GestioneImmagini.us?id=${a.id }&readonly=true' );" />
<input type="button" value="Chiudi" onclick="window.close();" />

<h4 class="titolopagina">Dettaglio Richiesta Esame Necroscopico</h4>
<table class="tabella">
	<tr class='even'>
		<td>Data richiesta</td>
		<td><fmt:formatDate type="date" value="${a.dataAutopsia}"
				pattern="dd/MM/yyyy" var="dataAutopsia" /> ${dataAutopsia}</td>
		<td></td>
	</tr>
	<tr class='even'>
		<td>Data Necroscopia</td>
		<td><fmt:formatDate type="date" value="${a.dataEsito}"
				pattern="dd/MM/yyyy" var="dataEsito" /> ${dataEsito}</td>
		<td></td>
	</tr>

	<tr class='odd'>
		<td>Operatori</td>
		<td colspan="2">${a.operatori}</td>
	</tr>

	<tr class='even'>
		<td>Sala settoria destinazione</td>
		<td>${a.lass.description}</td>
		<td></td>
	</tr>

	<tr class='odd'>
		<td>Numero rif. Mittente</td>
		<td>${a.numeroAccettazioneSigla}</td>
		<td></td>
	</tr>
	<tr class="even">
		<td>Richiedente</td>
		<td>${a.enteredBy} il <fmt:formatDate value="${a.entered}" pattern="dd/MM/yyyy"/></td>			
	</tr>
	<c:if test="${a.modifiedBy!=null}">
		<tr class="odd">
			<td>Modificato da</td><td>${a.modifiedBy} il <fmt:formatDate value="${a.modified}" pattern="dd/MM/yyyy"/></td><td></td>
		</tr>
	</c:if>
	
	<tr class='even'>
		<td>Modalità di conservazione</td>
		<td><c:out value="${a.lmcRichiesta.description }" /></td>
		<td><c:if test="${a.temperaturaConservazioneRichiesta != null}">
					Temperatura di conservazione
					<c:out value="${a.temperaturaConservazioneRichiesta}" />
			</c:if></td>
	</tr>
</table>

<br/>
<h4 class="titolopagina">Dettaglio Esame Necroscopico</h4>
<table class="tabella">
	<tr>
		<th colspan="3">Fenomeni tanatologici e diagnosi dell'epoca del
			decesso</th>
	</tr>

	<tr>
		<th class="sub" colspan="3">Dati generali</th>
	</tr>

	<tr class='even'>
		<td>Modalità di conservazione</td>
		<td><c:out value="${a.lmc.description }" /></td>
		<td><c:if test="${a.temperaturaConservazione != null}">
					Temperatura di conservazione
					<c:out value="${a.temperaturaConservazione}" />
			</c:if></td>
	</tr>

	<tr class='odd'>
		<td>Fenomeni cadaverici</td>
		<td><c:forEach items="${listFenomeniCadaverici}" var="fc">
				<c:set var="fcTemp" scope="request" value="${fc}" />
				<c:set var="livello" scope="request" value="0" />
				<c:import url="../vam/cc/autopsie/ricorsioneFcDetail.jsp" />
			</c:forEach></td>
		<td></td>
	</tr>


	<!--        <tr class='even'>-->
	<!--    		<td>-->
	<!--    			Esame esterno -->
	<!--    		</td>-->
	<!--    		<td>    			-->
	<!--	        	<c:out value="${a.esameEsterno }"/>	-->
	<!--    		</td>-->
	<!--    		<td>-->
	<!--    		</td>-->
	<!--        </tr>-->


	<!--        <tr class='odd'>-->
	<!--    		<td>-->
	<!--    			Cavità Addominale -->
	<!--    		</td>-->
	<!--    		<td>    			-->
	<!--	        	<c:out value="${a.cavitaAddominale }"/>	-->
	<!--    		</td>-->
	<!--    		<td>-->
	<!--    		</td>-->
	<!--        </tr>-->
	<!--        -->
	<!--        <tr class='even'>-->
	<!--    		<td>-->
	<!--    			Cavità Toracica -->
	<!--    		</td>-->
	<!--    		<td>    			-->
	<!--	        	<c:out value="${a.cavitaToracica }"/>	-->
	<!--    		</td>-->
	<!--    		<td>-->
	<!--    		</td>-->
	<!--        </tr>-->
	<!--        -->
	<!--        <tr class='odd'>-->
	<!--    		<td>-->
	<!--    			Cavità Pelvica -->
	<!--    		</td>-->
	<!--    		<td>    			-->
	<!--	        	<c:out value="${a.cavitaPelvica }"/>	-->
	<!--    		</td>-->
	<!--    		<td>-->
	<!--    		</td>-->
	<!--        </tr>-->
	<!--        -->
	<!--        <tr class='evan'>-->
	<!--    		<td>-->
	<!--    			Cavità Orale -->
	<!--    		</td>-->
	<!--    		<td>    			-->
	<!--	        	<c:out value="${a.cavitaOrale }"/>	-->
	<!--    		</td>-->
	<!--    		<td>-->
	<!--    		</td>-->
	<!--        </tr>-->

	<tr>
		<th class="sub" colspan="3">Esame morfologico degli organi</th>
	</tr>

	<c:set var="i" value='1' />
	<c:forEach items="${organi}" var="autopsia_op">

		<tr class="${i % 2 == 0 ? 'odd' : 'even'}">
			<td><c:out
					value="${autopsia_op.lookupOrganiAutopsia.description }" /></td>
			<td>${autopsia_op.descrizioneReferto}</td>
			<td>${autopsia_op.noteReferto}</td>


		</tr>
		<c:set var="i" value='${i+1}' />

	</c:forEach>




	<tr>
		<th colspan="3">Sezione Dettaglio esami</th>
	</tr>

	<tr>
		<td colspan="3">
			<table style="width: 100%" id="dettaglioEsami">
				<tr>
					<th class="sub" style="width: 15%">Organo da analizzare</th>
					<th class="sub" style="width: 15%">Tipologia Esame</th>
					<th class="sub" style="width: 40%">Dettaglio Richiesta</th>
					<th class="sub" style="width: 30%">Esiti</th>
				</tr>

				<c:set var="i" value="1" />

				<%
	    				Iterator<Entry<String, Set<String>>> iter = a.getDettaglioEsamiForJspDetail().entrySet().iterator();
	    				while(iter.hasNext())
	    				{
	    					Entry<String, Set<String>> e = iter.next();
	    			%>
				<tr class="${i % 2 == 0 ? 'odd' : 'even'}">
					<td><%=e.getKey().split(";")[0].split("---")[1]%> <input
						type="hidden" id="organo${i}"
						value="<%=e.getKey().split(";")[0].split("---")[0]%>" /> <input
						type="hidden" id="organoDesc${i}"
						value="<%=e.getKey().split(";")[0].split("---")[1]%>" /></td>
					<td><%=e.getKey().split(";")[1].split("---")[1]%> <input
						type="hidden" id="tipo${i}"
						value="<%=e.getKey().split(";")[1].split("---")[0]%>" /> <input
						type="hidden" id="tipoDesc${i}"
						value="<%=e.getKey().split(";")[1].split("---")[1]%>" /></td>
					<td><%=((e.getKey().split(";").length>2)?(e.getKey().split(";")[2]):(""))%>
						<!-- a style="align: center;"
						href="vam.cc.autopsie.VerbalePrelievo.us?idAutopsia=${a.id}&amp;keyDettaglioEsame=<%=e.getKey()%>"
						title="Verbale di Prelievo"> <img alt="Verbale di Prelievo"
							src="images/verbale.gif"> </img</a-->
					</td>
					<td><input type="hidden" name="esitiTemp${i}"
						id="esitiTemp${i}" value="<%=e.getValue()%>" /> <input
						type="hidden" name="esiti${i}" id="esiti${i}"
						value="<%=e.getValue()%>" /> <%=e.getValue()%></td>
				</tr>
				<c:set var="i" value="${i+1}" />
				<%
	    			}
	    			%>


			</table>
		</td>
	</tr>

	<table class="tabella">

		<tr>
			<th colspan="2">Cause del decesso Finale</th>
		</tr>


		<c:set var="i" value='1' />
		<c:forEach items="${a.cmf}" var="cmf">

			<tr class="${i % 2 == 0 ? 'odd' : 'even'}">
				<td width="80%"><c:out value="${cmf.lookupCMF.description }" />
				</td>
				<td><c:choose>
						<c:when test="${cmf.provata == true}">
							<c:out value="(Provata)" />
						</c:when>
						<c:otherwise>
							<c:out value="(Sospetta)" />
						</c:otherwise>
					</c:choose></td>
			</tr>

			<c:set var="i" value='${i+1}' />
		</c:forEach>




		<tr>
			<th colspan="2">Diagnosi</th>
		</tr>


		<tr class='odd'>
			<td style="width: 30%">Diagnosi Anatomo Patologica</td>
			<td style="width: 70%"><c:out
					value="${a.diagnosiAnatomoPatologica }" /></td>
		</tr>

		<tr class='even'>
			<td style="width: 30%">Diagnosi Definitiva</td>
			<td style="width: 70%"><c:out value="${a.diagnosiDefinitiva }" />
			</td>
		</tr>
		
		<tr class='odd'>
    		<td>
    			Quadro patologico prevalente
    		</td>
    		<td>
    			 ${a.patologiaDefinitiva.description}
    		</td>    		
        </tr>  


	</table>
</table>

<input type="button" value="Immagini" onclick="javascript:avviaPopup( 'vam.cc.autopsie.GestioneImmagini.us?id=${a.id }&readonly=true' );" />
<input type="button" value="Chiudi" onclick="window.close();" />



<%
	Iterator<LookupAutopsiaOrgani> organi = listOrganiAutopsia.iterator();
	while(organi.hasNext())
	{
		LookupAutopsiaOrgani organo = organi.next();
		Iterator<LookupAutopsiaTipiEsami> tipi = listTipiAutopsia.iterator();
		while(tipi.hasNext())
		{
			LookupAutopsiaTipiEsami tipo = tipi.next();
%>
<input type="hidden"
	id="esitiOrganoTipo<%=organo.getId()%>---<%=tipo.getId()%>"
	value="<%=allOrganiTipiEsiti.get(organo.getId()+"---"+organo.getDescription()+";"+tipo.getId()+"---"+tipo.getDescription())%>" />
<%
		}
	}
%>

<div style="display: none;" id="mostraTuttiEsiti"
	title="Selezione Esiti">
	<table class="tabella" id="tabellaTuttiEsiti">
		<tr>
			<th colspan="3"></th>
		</tr>

		<c:set var="i" value='1' />
		<c:forEach items="${listEsitiAutopsia}" var="esito">
			<tr class="${i % 2 == 0 ? 'odd' : 'even'}"
				id="trTuttiEsiti${i}_${esito.id}">
				<td colspan="3"><label id="labelTuttiEsiti${i}_${esito.id}">${esito.description}</label>
				</td>
			</tr>
			<c:set var="i" value='${i+1}' />
		</c:forEach>
	</table>
</div>

<script type="text/javascript">
	function mostraPopupEsiti(riga) {

		$("#mostraEsiti" + riga).dialog({
			height : screen.height / 2,
			modal : true,
			autoOpen : true,
			closeOnEscape : true,
			show : 'blind',
			resizable : true,
			draggable : true,
			width : screen.width / 2,
			buttons : {
				"Chiudi" : function() {
					$(this).dialog("close");
				}
			}
		});
	}
</script>