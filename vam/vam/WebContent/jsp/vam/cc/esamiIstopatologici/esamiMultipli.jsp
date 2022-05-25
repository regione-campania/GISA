<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.HashMap"%>
<link rel="stylesheet" documentale_url="" href="jsp/vam/cc/print.css" type="text/css" media="print" />

<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<%@page import="it.us.web.bean.vam.TerapiaDegenza"%>
<%@page import="it.us.web.bean.vam.CartellaClinica"%>
<%@page import="it.us.web.bean.vam.Diagnosi"%>
<%@page import="it.us.web.bean.vam.Trasferimento"%>
<%@page import="it.us.web.bean.vam.TipoIntervento"%>
<%@page import="it.us.web.bean.vam.Accettazione"%>
<%@page import="it.us.web.bean.vam.EsameIstopatologico"%>
<%@page import="it.us.web.bean.vam.lookup.LookupOperazioniAccettazione"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Set" %>


<%@page import="it.us.web.constants.IdOperazioniBdr"%>
<%@page import="it.us.web.constants.IdRichiesteVarie"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.net.InetAddress"%>
<%@page import="it.us.web.util.properties.Application"%>


<script language="JavaScript" type="text/javascript" src="js/vam/cc/stampaCC/popupDetails.js"></script>

<style>
@media screen {
.coloreIntestazione {
border:none !important;
	font-style: italic;
	display:inline;
	color:#000033;
	font-size: 19px; 
}
.fondopagina {
	bottom:0;
}
}
@media print {
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
.coloreIntestazione {
border:none !important;
	font-style: italic;
	display:inline;
	color:#000033;
	font-size: 19px; 
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
table.nobordo {
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

<%@ include file="../../../../js/barcode.jsp"%>
<%

ArrayList<TerapiaDegenza> tdList = (ArrayList<TerapiaDegenza>)request.getAttribute("tdList");
ArrayList<Diagnosi> diagnosi = (ArrayList<Diagnosi>) request.getAttribute("diagnosi");
ArrayList<Trasferimento> trasferimenti = (ArrayList<Trasferimento>) request.getAttribute("trasferimenti");
CartellaClinica cc = (CartellaClinica)session.getAttribute("cc");
%>

<!-- <h4 class="titolopagina">
     Cartella Clinica
</h4>  -->
<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../../hostName.jsp" %></div>
<!-- <h4 class="titolopagina">
     Cartella Clinica
</h4>  -->
<br/>

<br>
<c:set var="tipo" scope="request" value="stampaIstoMultiplo" />
<c:import url="../../jsp/documentale/home.jsp"/>
<br>

</div>      
   
<%
	HashMap<EsameIstopatologico, ArrayList<EsameIstopatologico>> gruppiIsto = (HashMap<EsameIstopatologico, ArrayList<EsameIstopatologico>>)request.getAttribute("gruppiIsto");
	Iterator<EsameIstopatologico> iterIsto = gruppiIsto.keySet().iterator();
	EsameIstopatologico esito = iterIsto.next();
%>   
<div id="print_div" >

	<div id="intestazioneCC" style="display: block;" >
	<table class="griglia" style="margin:0 auto;" >
		<tr>
			
		<td>
			<c:choose>
				<c:when test="<%=esito.getTipoAccettazione()!=null && esito.getTipoAccettazione().equals("Criuv")%>">
					<img documentale_url="" src="images/criuv.jpg" style="height:100px"/>
				</c:when>
				<c:otherwise>
					<img documentale_url="" src="images/asl/${utente.clinica.lookupAsl}.jpg" style="height:100px"/>
				</c:otherwise>
			</c:choose>
		</td>
		
		<td>
			<c:choose>
				<c:when test="<%=esito.getTipoAccettazione()!=null && esito.getTipoAccettazione().equals("Criuv")%>">
					<h3>CRIUV</h3><br/>
					Via M.R. di Torrepadula - P.O. Frullone - Plesso Ulisse<br/>
					80143 Napoli<br/>
					Tel.  0812549555/52/56/58 - Fax 0812548740<br/>
					email: criuv@regione.campania.it
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${utente.clinica.lookupAsl.id==204}">
							<h3>ASL NAPOLI 1 CENTRO - SSD EPIDEMIOLOGIA VETERINARIA</h3><br/>
							Via M.R. di Torrepadula - P.O. Frullone - Plesso Ulisse<br/>
							80143 Napoli<br/>
							Tel.  081-2549555 - Fax 081- 2548740<br/>
							email: epidevet@aslnapoli1centro.it                       
						</c:when>
						<c:otherwise>
							<h3>${utente.clinica.nome }</h3><br/>
							${utente.clinica.indirizzo }<br/>
							${utente.clinica.lookupComuni.description }
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</td>
		<td>
			<c:choose>
				<c:when test="<%=esito.getTipoAccettazione()!=null && esito.getTipoAccettazione().equals("Criuv")%>">
					<b><i>Cod. SIGLA 282961</i></b>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${utente.clinica.lookupAsl.id==204}">
							<b><i>Cod. SIGLA 283779</i></b>
						</c:when>
						<c:otherwise>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</td>			
		</tr>
	
	</table>
	<br/><br/>
		<table width="100%" style="border-collapse: collapse">
		  <col width="50%">  <col width="50%">
    	<tr>
    		<td style="border: 1px solid;padding:10px" align="right">
    			Microchip
    		</td>
    		<td style="border: 1px solid;padding:10px" align="center">
    		<img src="<%=createBarcodeImage(cc.getAccettazione().getAnimale().getIdentificativo())%>" />
    		</td>  
    		<td>
    		</td>  		
        </tr>
        </table>

	
<br/>
<center><label class="coloreIntestazione">Richiesta Esami Istopatologici</label></center>

<fmt:setLocale value="en_US" /> <!-- ALTRIMENTI LA STAMPA NON FUNZIONA -->
<center><h2>Dati del ${cc.accettazione.animale.lookupSpecie.description}</h2></center>
	<table class="bordo"> <col width="25%"><col width="25%"><col width="25%"><col width="25%">
<tr>
<td><i>Identificativo</i></td>
<td>${cc.accettazione.animale.identificativo}</td>

<td><i>
    		<c:if test="${cc.accettazione.animale.lookupSpecie.id!=3}">
    			Razza
    			</c:if>
    		<c:if test="${cc.accettazione.animale.lookupSpecie.id==3}">
    			Famiglia/Genere
    			</c:if>
    			
    			</i></td><td><c:if test="${(cc.accettazione.animale.lookupSpecie.id == specie.cane or cc.accettazione.animale.lookupSpecie.id == specie.gatto) && cc.accettazione.animale.decedutoNonAnagrafe==false}">${anagraficaAnimale.razza}</c:if>
				<c:if test="${cc.accettazione.animale.decedutoNonAnagrafe==true}">${anagraficaAnimale.razza}</c:if>
				<c:if test="${cc.accettazione.animale.lookupSpecie.id == specie.sinantropo && cc.accettazione.animale.decedutoNonAnagrafe==false}">${cc.accettazione.animale.specieSinantropoString} - ${cc.accettazione.animale.razzaSinantropo}</c:if>
    		   </td>  		
</tr>
<tr>
<td><i>Sesso</i></td>
<td>${anagraficaAnimale.sesso}</td>

<c:choose>
<c:when test="${cc.accettazione.animale.lookupSpecie.id==3}">
<td><i>Età</i></td>
<td><fmt:formatDate type="date" value="${cc.accettazione.animale.dataNascita }" pattern="dd/MM/yyyy" var="dataNascita"/>
${cc.accettazione.animale.eta} <c:if test="${dataNascita}">(${dataNascita})</c:if>
</td>
</c:when>
<c:otherwise>
<td><i>Data nascita</i></td>
<td><fmt:formatDate type="date" value="${cc.accettazione.animale.dataNascita }" pattern="dd/MM/yyyy" var="dataNascita"/>
${dataNascita}</td>
</c:otherwise></c:choose> 

</tr>
<c:if test="${cc.accettazione.animale.lookupSpecie.id!=3}">
	<tr>
		<td>
			Mantello
		</td>
		<td colspan="3">
			${anagraficaAnimale.mantello}
		</td>
	</tr>
</c:if>
<tr>
<td><i>Peso (in Kg)</i></td>
<td> ${cc.peso} </td>
<td><i> Habitat </i></td>
<td>${cc.lookupHabitats}</td>
</tr>
<tr>
<td><i>Alimentazione</i></td>
<td>${cc.lookupAlimentazionis}</td>
</tr>

<c:if test="${cc.accettazione.animale.decedutoNonAnagrafe==true && cc.accettazione.randagio==true}">	
	<tr>
	<td>
	<h2>Ritrovamento carcassa</h2>
	</td>
	</tr>
	<tr>
	<td><i>Comune</i></td>
	<td>${cc.accettazione.animale.comuneRitrovamento.description}</td>
	<td><i>Provincia</i></td>
	<td>${cc.accettazione.animale.provinciaRitrovamento}</td>
	</tr>
	<tr>
	<td><i>Indirizzo</i></td>
	<td>${cc.accettazione.animale.indirizzoRitrovamento}</td>
	<td><i>Note</i></td>
	<td>${cc.accettazione.animale.noteRitrovamento}</td>
	</tr>
</c:if>

<tr><td><h2>Dati del proprietario 
<c:if test="${cc.accettazione.proprietarioTipo!='null'}">
${cc.accettazione.proprietarioTipo}
</c:if>
</h2></td></tr>
	<c:if test="${cc.accettazione.proprietarioTipo=='Privato' || cc.accettazione.proprietarioTipo=='Sindaco'}">
<tr>
<td><i>Cognome</i></td>
<td>${cc.accettazione.proprietarioCognome}</td>
<td><i>Nome</i></td>
<td>${cc.accettazione.proprietarioNome}</td>
</tr>
<tr>
<td><i>Codice fiscale</i></td>
<td>${cc.accettazione.proprietarioCodiceFiscale}</td>
<td><i>Documento</i></td>
<td>${cc.accettazione.proprietarioDocumento}</td>
</tr>
<tr>
<td><i>Indirizzo</i></td>
<td>${cc.accettazione.proprietarioIndirizzo}</td>
<td>CAP</td>
<td>${cc.accettazione.proprietarioCap}</td>
</tr>
<tr>
<td><i>Comune</i></td>
<td>${cc.accettazione.proprietarioComune}</td>
<td><i>Provincia</i></td>
<td>${cc.accettazione.proprietarioProvincia}</td>
</tr>
</c:if>
	<c:if test="${cc.accettazione.proprietarioTipo=='Canile'}">
<tr>
<td><i>Ragione sociale</i></td>
<td>${cc.accettazione.proprietarioNome}</td>
<td></td>
<td></td>
</tr>
<tr>
<td><i>Partita IVA</i></td>
<td>${cc.accettazione.proprietarioCognome}</td>
<td><i>Rappr. Legale</i></td>
<td>${cc.accettazione.proprietarioCodiceFiscale}</td>
</tr>
<tr>
<td><i>Indirizzo</i></td>
<td>${cc.accettazione.proprietarioIndirizzo}</td>
<td><i>CAP</i></td>
<td>${cc.accettazione.proprietarioCap}</td>
</tr>
<tr>
<td><i>Comune</i></td>
<td>${cc.accettazione.proprietarioComune}</td>
<td><i>Provincia</i></td>
<td>${cc.accettazione.proprietarioProvincia}</td>
</tr>
</c:if>

<c:if test="${cc.accettazione.proprietarioTipo=='Colonia'}">
<tr>
<td><i>Nome colonia</i></td>
<td>${cc.accettazione.nomeColonia}</td>
<td></td>
<td></td>
</tr>
<tr>
<td><i>Nominativo referente</i></td>
<td>${cc.accettazione.proprietarioNome}</td>
<td><i>Codice fiscale referente</i></td>
<td>${cc.accettazione.proprietarioCodiceFiscale}</td>
</tr>
<tr>
<td><i>Indirizzo</i></td>
<td>${cc.accettazione.proprietarioIndirizzo}</td>
<td><i>CAP</i></td>
<td>${cc.accettazione.proprietarioCap}</td>
</tr>
<tr>
<td><i>Comune</i></td>
<td>${cc.accettazione.proprietarioComune}</td>
<td><i>Provincia</i></td>
<td>${cc.accettazione.proprietarioProvincia}</td>
</tr>
</c:if>


<c:if test="${cc.accettazione.proprietarioTipo=='Importatore' || cc.accettazione.proprietarioTipo=='Operatore Commerciale'}">
<tr>
<td><i>Ragione sociale</i></td>
<td>${cc.accettazione.proprietarioNome}</td>
<td><i>Partita IVA</i></td>
<td>${cc.accettazione.proprietarioCognome}</td>
</tr>
<tr>
<td><i>Indirizzo sede operativa</i></td>
<td>${cc.accettazione.proprietarioIndirizzo}</td>
<td><i>CAP sede operativa</i></td>
<td>${cc.accettazione.proprietarioCap}</td>
</tr>
<tr>
<td><i>Comune sede operativa</i></td>
<td>${cc.accettazione.proprietarioComune}</td>
<td><i>Provincia sede operativa</i></td>
<td>${cc.accettazione.proprietarioProvincia}</td>
</tr>
</c:if>

<%
	gruppiIsto = (HashMap<EsameIstopatologico, ArrayList<EsameIstopatologico>>)request.getAttribute("gruppiIsto");
	iterIsto = gruppiIsto.keySet().iterator();
	while(iterIsto.hasNext())
	{
		EsameIstopatologico eisto = iterIsto.next();
%>

  <tr><td><h2>Dati dell'esame</h2></td></tr>
<tr>
<td><i>Data della richiesta</i></td>
<fmt:formatDate type="date" value="<%=eisto.getDataRichiesta()%>" pattern="dd/MM/yyyy" var="dataRichiesta"/>
<td>${dataRichiesta}</td>
</tr>
<tr>
<td><i>Laboratorio di destinazione</i></td>
<td><%=(eisto.getLass()!=null)?(eisto.getLass().getDescription()):("")%></td>
</tr>
<tr>
<td><i>Numero rif. mittente</i></td>
<td><%=eisto.getNumeroAccettazioneSigla()%></td>
</tr>
<tr>
<td><i>Tipo prelievo</i></td>
<td><%=((eisto.getTipoPrelievo()!=null)?(eisto.getTipoPrelievo().getDescription()):(""))%></td>
</tr>
<tr>
<td><i>Tumori precedenti</i></td>
<td><%=((eisto.getTumoriPrecedenti()!=null)?(eisto.getTumoriPrecedenti().getDescription()):(""))%></td>
</tr>
<c:if test="<%=eisto.getTumoriPrecedenti()!=null && eisto.getTumoriPrecedenti().getId()==2%>">
        <tr>
    		<td style="width:30%">
    			 T
    		</td>
    		<td style="width:70%"> 
    			<%=eisto.getT()%>
    		</td>
        </tr>    
        
        <tr>
    		<td style="width:30%">
    			 N
    		</td>
    		<td style="width:70%"> 
    			<%=eisto.getN()%> 
    		</td>
        </tr>  
        
        <tr>
    		<td style="width:30%">
    			 M
    		</td>
    		<td style="width:70%">  
    			<%=eisto.getM()%>
    		</td>
        </tr> 
    </c:if>
<tr>
<td><i>Dimensione (in cm)</i></td>
<td><%=((eisto.getDimensione()!=null)?(eisto.getDimensione()):(""))%></td>
</tr>
<tr>
<td><i>Interessamento linfondale</i></td>
<td><%=((eisto.getInteressamentoLinfonodale()!=null)?(eisto.getInteressamentoLinfonodale().getDescription()):(""))%></td>
</tr>
</table>
<br/><br/>

<%
Iterator<EsameIstopatologico> iters = 	gruppiIsto.get(eisto).iterator();
while(iters.hasNext())
{
	EsameIstopatologico temp = iters.next();
%>

  <table width="100%" style="border-collapse: collapse;page-break-inside: avoid">
  <col width="60%"> <col width="40%">
<tr>
<td style="border: 1px solid;padding:10px"><i>Sede lesione e sottospecifica</i></td>
<td style="border: 1px solid;padding:10px"><i>Numero verbale</i></td>
</tr>
<tr>
<td style="border: 1px solid;padding:10px"><%=temp.getSedeLesione()%></td>
<td style="border: 1px solid;padding:10px"><img src="<%=createBarcodeImage(temp.getNumero())%>" /> <!-- ${eisto.numero}--></td>
</tr>
</table>

<%
	}
}
%>





</div>

<br/>

	<br/>
	
  	
  	