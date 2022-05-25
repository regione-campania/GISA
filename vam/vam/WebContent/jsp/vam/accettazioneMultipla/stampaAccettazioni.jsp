<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.bean.vam.AnimaleAnagrafica"%>
<%@page import="it.us.web.bean.SuperUtente"%>
<%@page import="it.us.web.bean.vam.lookup.LookupPersonaleInterno"%>
<link rel="stylesheet" documentale_url= "" href="jsp/vam/cc/print.css" type="text/css" media="print" />

<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<%@page import="it.us.web.bean.vam.TerapiaDegenza"%>
<%@page import="it.us.web.bean.vam.CartellaClinica"%>
<%@page import="it.us.web.bean.vam.Diagnosi"%>
<%@page import="it.us.web.bean.vam.Trasferimento"%>
<%@page import="it.us.web.bean.vam.TipoIntervento"%>
<%@page import="it.us.web.bean.vam.Accettazione"%>
<%@page import="it.us.web.bean.vam.lookup.LookupOperazioniAccettazione"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page
	import="java.util.Date"%>


<%@page import="it.us.web.constants.IdOperazioniBdr"%>
<%@page import="it.us.web.constants.IdRichiesteVarie"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.net.InetAddress"%>
<%@page import="it.us.web.util.properties.Application"%>

<script language="JavaScript" type="text/javascript" src="js/vam/cc/stampaCC/popupDetails.js"></script>


<style>
@media all {
	table.fondo {
position: absolute;
	font-size: 12px;
	margin-top: 650px;
	width:100%;
}
div.header {
	display:none;
}
.stampaSezione {
display:none;
}
.coloreIdentificativo {
	color:#000000;
}
.boxIdDocumento {  
       border: 1px solid black;
       width: 60px;
       height: 20px;
       margin-top: 30px;
       text-align: center;
       padding-top: 5px;
       font-size: 8px; 
}
.boxOrigineDocumento {
	position: absolute;
	width: 160px;
	height: 20px;
	top: 15px;
	left: 70px;
	text-align: left;
	padding-top: 10px;
	font-size: 8px;
}
table.tabella
{
	-webkit-print-color-adjust:exact;
	background-color:#E5EAE7;
	font-size:16px;
	margin:0.5%;
	width:94%;	
	border-collapse: collapse;
	
}

th
{
	background:#A8C4DC;
	color:#000044;
	font-weight:bold;
	text-align:center;
	padding: 0px;
	border: none;	
}

th.sub
{
	background:#CCCCCC;
	color:#000044;
	font-weight:bold;
	text-align:center;
	padding: 0px;
	border: none;	
}
#divTerap, #divChir {
page-break-before: always;
}


tr.odd {background-color: #FFFFFF}
tr.even {background-color: #E5EAE7;}

table.griglia  {
width:100%;
background-color:#000;
}
table.griglia td {
background-color:#FFF;

}

table.grigliaEsami  {
background-color:#000;
}
table.grigliaEsami td {
background-color:#FFF;

}

table {
line-height:1.8em;
}

.underline {
border-bottom:1px solid;
}
.esamiSangue tr td{
	font-size: 0.8em;
}
}

body{
	-webkit-print-color-adjust:exact;
    font-family: Trebuchet MS,Verdana,Helvetica,Arial,san-serif;	
}

p.intestazione{
	background: none repeat scroll 0 0 #A8C4DC;
    border: medium none;
    color: #000044;
    font-weight: bold;
    padding: 0;
    text-align: center;
    width: 50%;
    text-align:left;
    
}
.pagebreak, #divChir {
page-break-after:always;

}

#intestazione table td img {
	heigth:100px;
}
h2 {
	border:none !important;
	text-decoration: underline;
	display:inline;
}
table td h3 {
	display:inline
	}
table.griglia  {
width:100%;
background-color:#000;
}
table.griglia td {
background-color:#FFF;
text-align:center;
}

table.grigliaEsami  {
background-color:#000;
}
table.grigliaEsami td {
background-color:#FFF;
text-align:center;
}

table.bordo {
border:1px solid;
width:100%;
}
.esamiSangue {
	font-size: 10px;
	}
	table.fondo {

position: absolute;
	font-size: 12px;
	width:98%;
}

	
</style>

<%

Accettazione accettazione = (Accettazione)request.getAttribute("accettazione");
%>

<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div>
<!-- <h4 class="titolopagina">
     Cartella Clinica
</h4>  -->

<br>
<c:set var="tipo" scope="request" value="stampaAcc" />
<c:import url="../../jsp/documentale/home.jsp"/>
   
<div id="print_div" >

	<div id="intestazioneCC" style="display: block;" >
	<table class="griglia" style="margin:0 auto;" >
		<tr>
			
		<td>
			<img documentale_url="" src="images/asl/${utente.clinica.lookupAsl}.jpg" style="height:100px"/>
		</td>
		
		<td>
			<h3>${utente.clinica.nome }</h3><br/>
			${utente.clinica.indirizzo }<br/>
			${utente.clinica.lookupComuni.description }
		</td>
			
		</tr>
	
	</table></div>

<%
	int j = 1;
%>
<c:forEach items="${accettazioni}" var="acc">
    			<fmt:setLocale value="en_US" /> <!-- ALTRIMENTI LA STAMPA NON FUNZIONA -->
    			<fmt:formatDate type="date" value="${acc.data}" pattern="dd/MM/yyyy" var="dataApertura"/>
	<center><h2>Accettazione n. <c:out value="${acc.progressivoFormattato}"/> del 	<c:out value="${dataApertura}"/> </h2></center>
	
		<br/>


<%

	AnimaleAnagrafica anag = (AnimaleAnagrafica)request.getAttribute("anagraficaAnimale"+j);
%>		
		<center><h2>Dati del ${acc.animale.lookupSpecie.description}</h2></center>
	<table class="bordo"> <col width="25%"><col width="25%"><col width="10%"><col width="40%">
<tr>
<td><i>Identificativo<i><br/><i>Tatuaggio/II MC<i></td>
<td>${acc.animale.identificativo}<br/>${acc.animale.tatuaggio}&nbsp;</td>
<td><i>Stato attuale<i></td>
<td><%=anag.getStatoAttuale()%></td>  		
</tr>
<tr>
<c:choose>
<c:when test="${acc.animale.lookupSpecie.id==3}">
<td><i>Età</i></td>
<td><fmt:formatDate type="date" value="${acc.animale.dataNascita }" pattern="dd/MM/yyyy" var="dataNascita"/>
${acc.animale.eta} <c:if test="${dataNascita}">(${dataNascita})</c:if>
</td>
</c:when>
<c:otherwise>
<td><i>Data nascita</i></td>
<td><fmt:formatDate type="date" value="${acc.animale.dataNascita }" pattern="dd/MM/yyyy" var="dataNascita"/>
${dataNascita}</td>
</c:otherwise></c:choose>  
<td>
    			<i>Razza</i>
    			</td><td><%=anag.getRazza()%>
    		   </td>  		
</tr>
<tr>
<td><i>Taglia</i></td>
<td><%=anag.getTaglia()%></td>
<td><i>Mantello</i></td>
<td><%=anag.getMantello()%></td>  		
</tr>
<tr>
<td><i>Data decesso</i></td>
<fmt:formatDate type="date" value="${res.dataEvento}" pattern="dd/MM/yyyy" var="dataMorte"/>
<td>${dataMorte}</td>
<td><i>Sesso</i></td>
<td><%=anag.getSesso()%></td>  		
</tr>
<tr>
<td><i>Probabile causa del decesso</i></td>
<td>
  	${res.decessoValue}
</td>
<td><i>Sterilizzato</i></td>
<td><%=anag.getSterilizzato()%>
</td>  		
</tr>

</table>	
		</br></br>
		

	
<center><h2>Proprietario</h2></center>
<table class="bordo"> <col width="10%"><col width="40%"><col width="10%"><col width="40%">
	<c:if test="${acc.proprietarioTipo=='Privato' || acc.proprietarioTipo=='Sindaco'}">
	<tr>
<td><i>Cognome</i></td>
<td>${acc.proprietarioCognome}</td>
<td><i>Nome</i></td>
<td>${acc.proprietarioNome}</td>
</tr>
<tr>
<td><i>Codice fiscale</i></td>
<td>${acc.proprietarioCodiceFiscale}</td>
<td><i>Documento</i></td>
<td>${acc.proprietarioDocumento}</td>
</tr>
<tr>
<td><i>Indirizzo</i></td>
<td>${acc.proprietarioIndirizzo}</td>
<td><i>CAP</i></td>
<td>${acc.proprietarioCap}</td>
</tr>
<tr>
<td><i>Comune</i></td>
<td>${acc.proprietarioComune}</td>
<td><i>Provincia</i></td>
<td>${acc.proprietarioProvincia}</td>
</tr>
</c:if>
	<c:if test="${acc.proprietarioTipo=='Canile'}">
<tr>
<td><i>Ragione sociale</i></td>
<td>${acc.proprietarioNome}</td>
<td></td>
<td></td>
</tr>
<tr>
<td><i>Partita IVA</i></td>
<td>${acc.proprietarioCognome}</td>
<td><i>Rappr. Legale</i></td>
<td>${acc.proprietarioCodiceFiscale}</td>
</tr>
<tr>
<td><i>Indirizzo</i></td>
<td>${acc.proprietarioIndirizzo}</td>
<td><i>CAP</i></td>
<td>${acc.proprietarioCap}</td>
</tr>
<tr>
<td><i>Comune</i></td>
<td>${acc.proprietarioComune}</td>
<td><i>Provincia</i></td>
<td>${acc.proprietarioProvincia}</td>
</tr>
</c:if>

<c:if test="${acc.proprietarioTipo=='Colonia'}">
<tr>
<td><i>Nome colonia</i></td>
<td>${acc.nomeColonia}</td>
<td></td>
<td></td>
</tr>
<tr>
<td><i>Nominativo referente</i></td>
<td>${acc.proprietarioNome}</td>
<td><i>Codice fiscale referente</i></td>
<td>${acc.proprietarioCodiceFiscale}</td>
</tr>
<tr>
<td><i>Indirizzo</i></td>
<td>${acc.proprietarioIndirizzo}</td>
<td><i>CAP</i></td>
<td>${acc.proprietarioCap}</td>
</tr>
<tr>
<td><i>Comune</i></td>
<td>${acc.proprietarioComune}</td>
<td><i>Provincia</i></td>
<td>${acc.proprietarioProvincia}</td>
</tr>
</c:if>


<c:if test="${acc.proprietarioTipo=='Importatore' || acc.proprietarioTipo=='Operatore Commerciale'}">
<tr>
<td><i>Ragione sociale</i></td>
<td>${acc.proprietarioNome}</td>
<td><i>Partita IVA</i></td>
<td>${acc.proprietarioCognome}</td>
</tr>
<tr>
<td><i>Indirizzo sede operativa</i></td>
<td>${acc.proprietarioIndirizzo}</td>
<td><i>CAP sede operativa</i></td>
<td>${acc.proprietarioCap}</td>
</tr>
<tr>
<td><i>Comune sede operativa</i></td>
<td>${acc.proprietarioComune}</td>
<td><i>Provincia sede operativa</i></td>
<td>${acc.proprietarioProvincia}</td>
</tr>
</c:if>

</table>	
		</br></br>
		<%j++; %>
</c:forEach>

	<center><h2>Dati accettazione</h2></center>
	<table class="bordo"> <col width="25%"><col width="25%"><col width="25%"><col width="25%">
<tr>
<td><i>Data<i></td>
<td><c:out value="${dataApertura}"/> </td>
<td><i><i></td>
<td></td>	
</tr>
<tr>
<td><i>Inserita da<i></td>
	<td colspan="3"> ${accettazione.enteredBy.cognome } ${accettazione.enteredBy.nome }</td>
</tr>
<tr>	
<td><i>Richiedente</i></td>
<td colspan="3"> <b><%=accettazione.getLookupTipiRichiedente().getDescription()%>:</b>
			<c:choose>
				<c:when test="<%=accettazione.getRichiedenteTipoProprietario()!=null && accettazione.getRichiedenteTipoProprietario().equals(\"Importatore\") %>">
					Importatore ragione sociale: <%=accettazione.getRichiedenteNome()%>
				</c:when>
		
				<c:when test="<%=accettazione.getRichiedenteTipoProprietario()!=null && !accettazione.getRichiedenteTipoProprietario().equals(\"Importatore\") %>"> 
<%-- 				<%if(accettazione.getRichiedenteAsl()!=null && accettazione.getRichiedenteAsl().getDescription()!=null){%> <%=accettazione.getRichiedenteAsl().getDescription()%> <%}%> 
					<%if(accettazione.getRichiedenteAssociazione()!=null && accettazione.getRichiedenteAssociazione().getDescription()!=null){%> <%=accettazione.getRichiedenteAssociazione().getDescription()%> <%}%> --%>
					<c:if test="<%=accettazione.getRichiedenteCognome()!=null && accettazione.getRichiedenteCognome().startsWith(\"<b>\") %>">
						<%=accettazione.getRichiedenteCognome()%> 
					</c:if>
					<%if(accettazione.getRichiedenteNome()!=null){%><%=accettazione.getRichiedenteNome()%><%}%>
					<%if(accettazione.getRichiedenteCodiceFiscale()!=null){%><%=accettazione.getRichiedenteCodiceFiscale()%><%}%>
					<%if(accettazione.getRichiedenteDocumento()!=null){%><%=accettazione.getRichiedenteDocumento()%><%}%>
					<c:if test="<%=accettazione.getRichiedenteTelefono()!=null && !accettazione.getRichiedenteTelefono().equals(\"\")%>">
						<br/>Telefono: <%=accettazione.getRichiedenteTelefono()%>
					</c:if>
					<c:if test="<%=accettazione.getRichiedenteResidenza()!=null && !accettazione.getRichiedenteResidenza().equals(\"\") %>">
						<br/>Residenza: <%=accettazione.getRichiedenteResidenza()%>
					</c:if>
<%--				<%if(accettazione.getRichiedenteForzaPubblicaComune()!=null){><%=accettazione.getRichiedenteForzaPubblicaComune() %><%}%>
					<%if(accettazione.getRichiedenteForzaPubblicaProvincia()!=null){%><%=accettazione.getRichiedenteForzaPubblicaProvincia() %><%}%>
					<%if(accettazione.getRichiedenteForzaPubblicaComando()!=null){%><%=accettazione.getRichiedenteForzaPubblicaComando() %><%}%>
					<%if(accettazione.getRichiedenteAltro()!=null && !accettazione.getRichiedenteAltro().equals("")){%><%=accettazione.getRichiedenteAltro() %><%}%>
		 			<c:if test="<%=accettazione.getPersonaleAsl()!=null && accettazione.getPersonaleAsl().size()>0%>">
						<br/>Richiedente Asl:
						<% 	Iterator i = accettazione.getPersonaleAsl().iterator();
							SuperUtente su;
							while (i.hasNext()){
								su = (SuperUtente)i.next(); %>
								<%=su.toString()%>,	
						<%  } %>
					</c:if> --%>
					<c:if test="<%=accettazione.getPersonaleInterno()!=null && accettazione.getPersonaleInterno().size()>0 %>">
						<br/>Intervento personale interno:
						<% 	Iterator i = accettazione.getPersonaleInterno().iterator();
							LookupPersonaleInterno lpi;
							while (i.hasNext()){
								lpi = (LookupPersonaleInterno)i.next(); %>
								<%=lpi.getNominativo()%>,	
						<%  } %>
					</c:if>
				</c:when>

				<c:when test="<%=accettazione.getRichiedenteTipoProprietario()==null%>">
					<c:if test="<%=accettazione.getRichiedenteCognome()!=null && !accettazione.getRichiedenteCognome().startsWith(\"<b>\") %>">
						<%=accettazione.getRichiedenteCognome()%> 
					</c:if>
					<%if(accettazione.getRichiedenteNome()!=null){%><%=accettazione.getRichiedenteNome()%><%}%>
					<%if(accettazione.getRichiedenteCodiceFiscale()!=null){%><%=accettazione.getRichiedenteCodiceFiscale()%><%}%>
					<%if(accettazione.getRichiedenteDocumento()!=null){%><%=accettazione.getRichiedenteDocumento()%><%}%>
					<c:if test="<%=accettazione.getRichiedenteTelefono()!=null && !accettazione.getRichiedenteTelefono().equals(\"\")%>"><%=accettazione.getRichiedenteTelefono()%></c:if>
					<c:if test="<%=accettazione.getRichiedenteResidenza()!=null && !accettazione.getRichiedenteResidenza().equals(\"\") %>"><%=accettazione.getRichiedenteResidenza()%>
					</c:if>
					<c:if test="<%=accettazione.getPersonaleAsl()!=null && accettazione.getPersonaleAsl().size()>0%>">
							<% 	Iterator i = accettazione.getPersonaleAsl().iterator();
								SuperUtente su;
								while (i.hasNext()){
									su = (SuperUtente)i.next(); %>
									<%=su.toString()%>,	
							<%  } %>
					</c:if>	
					<c:if test="<%=accettazione.getRichiedenteAssociazione()!=null%>"><%=accettazione.getRichiedenteAssociazione().getDescription()%></c:if>
					<c:if test="<%=accettazione.getRichiedenteForzaPubblicaComune()!=null%>"><%=accettazione.getRichiedenteForzaPubblicaComune()%>	</c:if>
					<c:if test="<%=accettazione.getRichiedenteForzaPubblicaProvincia()!=null%>"><%=accettazione.getRichiedenteForzaPubblicaProvincia()%></c:if>
					<c:if test="<%=accettazione.getRichiedenteForzaPubblicaComando()!=null%>"><%=accettazione.getRichiedenteForzaPubblicaComando()%></c:if>
					<c:if test="<%=accettazione.getRichiedenteAltro()!=null && !accettazione.getRichiedenteAltro().equals(\"\")%>"><%=accettazione.getRichiedenteAltro()%></c:if>
				</c:when>
			</c:choose>
	</td>
</tr>
					
<c:if test="<%=accettazione.getPersonaleInterno()!=null && accettazione.getPersonaleInterno().size()>0%>">
	<tr>
		<td>
			<i>Intervento personale interno</i>
		</td>
		<td colspan="3">
<% 	
			Iterator i = accettazione.getPersonaleInterno().iterator();
			LookupPersonaleInterno lpi;
			while (i.hasNext())
			{
				lpi = (LookupPersonaleInterno)i.next(); 
%>
				<%=lpi.getNominativo()%>,	
<%  
			} 
%>
		</td>
	</tr>
</c:if>
<tr>
<td><i>Motivazioni/Operazioni Richieste</i></td>
	<td colspan="3"> 
<% 	
        	 IdOperazioniBdr idOpsBdr = (IdOperazioniBdr)request.getAttribute("idOpsBdr");
        	 IdRichiesteVarie idRichiesteVarie = (IdRichiesteVarie)request.getAttribute("idRichiesteVarie");
        		
        	 Boolean scriviVirgola=false;
				Iterator i = accettazione.getOperazioniRichieste().iterator();
				LookupOperazioniAccettazione loa;
				while (i.hasNext())
				{
					loa = (LookupOperazioniAccettazione)i.next();
					if (scriviVirgola)
					{
						out.println(",");
					}
					out.println(loa.getDescription());
%>
					<%scriviVirgola=true;%>
<% 
				} 
%>
	</td>	
</tr>
</table>	
	<jsp:useBean id="now" class="java.util.Date"/>    
	<fmt:formatDate value="${now}" pattern="dd/MM/yyyy" var="dataOra"/>
	
<table class="nobordo">
<col width="25%"><col width="25%"><col width="25%"><col width="25%">
<tr>
	<td>
		${dataOra}
	</td>
	<td>
	</td>
	<td>
		Firma dell'operatore</br>
		${accettazione.enteredBy.cognome } ${accettazione.enteredBy.nome }
	</td>
	<td>
		Firma del richiedente</br>
		___________________________________
	</td>
</tr>
</table>

<br><br><br>
<%@ include file="/jsp/documentale/gdpr_footer.jsp" %>
<br/>

	</div>

	
  	