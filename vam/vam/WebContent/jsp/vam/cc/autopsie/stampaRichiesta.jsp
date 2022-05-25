<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.HashSet"%>
<%@page import="java.sql.SQLException"%>
<%@page import="it.us.web.bean.BUtenteAll"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="java.util.Date"%>
<%@page import="it.us.web.bean.remoteBean.RegistrazioniSinantropiResponse"%>
<%@page import="it.us.web.bean.remoteBean.RegistrazioniFelinaResponse"%>
<%@page import="it.us.web.util.sinantropi.SinantropoUtil"%>
<%@page import="it.us.web.util.vam.FelinaRemoteUtil"%>
<%@page import="it.us.web.util.vam.CaninaRemoteUtil"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.naming.Context"%>
<%@page import="it.us.web.action.GenericAction"%>
<%@page import="it.us.web.dao.hibernate.PersistenceFactory"%>
<%@page import="it.us.web.dao.hibernate.Persistence"%>
<%@page import="it.us.web.bean.ServicesStatus"%>
<%@page import="it.us.web.bean.BUtente"%>
<%@page import="it.us.web.bean.remoteBean.RegistrazioniCaninaResponse"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa"%>
<%@page import="it.us.web.bean.vam.Autopsia"%>
<%@page import="it.us.web.bean.vam.CartellaClinica"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="it.us.web.bean.vam.lookup.LookupAutopsiaEsitiEsami"%>
<%@page import="it.us.web.bean.vam.lookup.LookupAutopsiaTipiEsami"%>
<%@page import="it.us.web.bean.vam.lookup.LookupAutopsiaOrgani"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.net.InetAddress"%>
<%@page import="it.us.web.util.properties.Application"%>

<script language="JavaScript" type="text/javascript"
	src="js/vam/cc/autopsie/detail.js"></script>

<%	

Persistence persistence = PersistenceFactory.getPersistence();
GenericAction.aggiornaConnessioneApertaSessione(request);
Context ctx = new InitialContext();
javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
Connection connection = ds.getConnection();
GenericAction.aggiornaConnessioneApertaSessione(request);
Autopsia a 	         = (Autopsia)request.getAttribute("a");
CartellaClinica cc = a.getCartellaClinica();
HashMap<String, Set<LookupAutopsiaEsitiEsami>> allOrganiTipiEsiti = (HashMap<String, Set<LookupAutopsiaEsitiEsami>>)request.getAttribute("allOrganiTipiEsiti");
BUtente utente = ((BUtente)request.getSession().getAttribute("utente"));
ServicesStatus status =new ServicesStatus();

RegistrazioniCaninaResponse res = null;
RegistrazioniFelinaResponse ref = null;
RegistrazioniSinantropiResponse ress = null;

if (cc.getAccettazione().getAnimale().getLookupSpecie().getId() == 1 && !cc.getAccettazione().getAnimale().getDecedutoNonAnagrafe()) 
	res	= CaninaRemoteUtil.getInfoDecesso( cc.getAccettazione().getAnimale().getIdentificativo(), utente, status, connection, request);
else if (cc.getAccettazione().getAnimale().getLookupSpecie().getId() == 2 && !cc.getAccettazione().getAnimale().getDecedutoNonAnagrafe()) 
	ref = FelinaRemoteUtil.getInfoDecesso( cc.getAccettazione().getAnimale().getIdentificativo(), utente, status, connection, request);
else if (cc.getAccettazione().getAnimale().getLookupSpecie().getId() == 3 && !cc.getAccettazione().getAnimale().getDecedutoNonAnagrafe()) 
	ress = SinantropoUtil.getInfoDecesso(persistence, cc.getAccettazione().getAnimale());

Date dataEvento = null;
String dataMorteCertezza = "";
String decessoValue = "";
if(res!=null)
{
	decessoValue = res.getDecessoValue();
	dataEvento = res.getDataEvento();
	dataMorteCertezza = res.getDataMorteCertezza();
}
else if(ref!=null)
{
	decessoValue = ref.getDecessoValue();
	dataEvento = ref.getDataEvento();
	dataMorteCertezza = ref.getDataMorteCertezza();
}
if(ress!=null)
{
	decessoValue = ress.getDecessoValue();
	dataEvento = ress.getDataEvento();
	dataMorteCertezza = ress.getDataMorteCertezza();
}

Context ctx2 = new InitialContext();
javax.sql.DataSource ds2 = (javax.sql.DataSource)ctx2.lookup("java:comp/env/jdbc/vamM");
Connection connectionVam = ds2.getConnection();
GenericAction.aggiornaConnessioneApertaSessione(request);
PreparedStatement pSt = connectionVam.prepareStatement("select operatore from autopsia_operatori where autopsia = " + a.getId() );
ResultSet rs = pSt.executeQuery();

Set<BUtenteAll> operatori = new HashSet<BUtenteAll>();
while(rs.next())
{
	
		BUtenteAll				utente2	= null;
		PreparedStatement	stat	= null;
		ResultSet			rs2		= null;
		
		try 
		{
			stat = connectionVam.prepareStatement("SELECT * FROM utenti_ WHERE utenti_.superutente =  " + rs.getInt("operatore"));
			rs2 = stat.executeQuery();
			
			if( rs2.next() )
			{		
				utente2 = new BUtenteAll();
				utente2.setCognome(rs2.getString("cognome"));
				utente2.setNome(rs2.getString("nome"));
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		operatori.add(utente2);	
}
connectionVam.close();
GenericAction.aggiornaConnessioneChiusaSessione(request);

PersistenceFactory.closePersistence(persistence, false);
GenericAction.aggiornaConnessioneChiusaSessione(request);
connection.close();
GenericAction.aggiornaConnessioneChiusaSessione(request);

%>




<script language="JavaScript" type="text/javascript" src="js/vam/cc/stampaCC/popupDetails.js"></script>
<%@ include file="../../../../js/barcode.jsp"%>


<style>

@media all {
@page { margin-top: 5px}
.blue {
	 background-color:#e6f3ff;
	  border: 1px solid black;
	}
	.layout {
		  border: 1px solid black;
	}

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
       margin-top: 15px;
       text-align: center;
       padding-top: 5px;
       font-size: 8px; 
}
.boxOrigineDocumento {
	position: absolute;
	width: 160px;
	height: 20px;
	top: 15px;
	left: 80px;
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
line-height:1.2em;
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
border-collapse: collapse;
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

<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../../hostName.jsp" %></div>
<br/>
      
<div id="print_div" >

	<div id="intestazioneCC" style="display: block;" >
	<table class="griglia" style="margin:0 auto;" >
		<tr>
			
		<td>
			<c:choose>
				<c:when test="${a.tipoAccettazione=='Criuv'}">
					<img documentale_url="" src="images/criuv.jpg" style="height:100px"/>
				</c:when>
				<c:otherwise>
					<img documentale_url="" src="images/asl/${utente.clinica.lookupAsl}.jpg" style="height:100px"/>
				</c:otherwise>
			</c:choose>
		</td>
		
		<td>
			<c:choose>
				<c:when test="${a.tipoAccettazione=='Criuv'}">
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
				<c:when test="${a.tipoAccettazione=='Criuv'}">
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
	
	</table></div>
	
	
	<table width="100%">
		<col width="50%">
		<tr><td>Numero rif. mittente <b>${a.numeroAccettazioneSigla}</b></td>
		<% String barcode = (String) cc.getAccettazione().getAnimale().getIdentificativo();  %>
		<td><img src="<%=createBarcodeImage(barcode)%>" /></td>
		</tr>
		
		
		<c:if test="${(a.lass.id == 4 || a.lass.id == 8 || a.lass.id == 9 || a.lass.id == 10 || a.lass.id == 11) && (cc.accettazione.animale.lookupSpecie.id==3)}">
			<tr>
				<td>Quesito diagnostico PGMFS - Piano B7</td>
			</tr>
		</c:if>
		
		</table>
		
		
	
		<fmt:setLocale value="en_US" /> <!-- ALTRIMENTI LA STAMPA NON FUNZIONA -->
		<br/>
    		<center><h2>Richiesta Esame Necroscopico</h2></center>
	
	<table width="100%" class="bordo">
	<col width="50%"><col width="50%">
	<tr><td><i>Data richiesta</i></td>
			<td><fmt:formatDate type="date" value="${a.dataAutopsia}"
				pattern="dd/MM/yyyy" var="dataAutopsia" /> ${dataAutopsia}</td>
	</tr>
		
	
		
	
	<tr>
		<td><i>Sala settoria di destinazione</i></td>
		<td>${a.lass.description}</td>
		</tr>

	<tr>
		<td><i>Numero rif. Mittente</i></td>
		<td>${a.numeroAccettazioneSigla}</td>
		</tr>
	<tr>
		<td><i>Richiedente</i></td>
		<td>${a.enteredBy} il <fmt:formatDate value="${a.entered}" pattern="dd/MM/yyyy"/></td>			
	</tr>
	
	<tr>
		<td>Modalità di conservazione</td>
		<td>
			${a.lmcRichiesta.description }
			<c:if test="${a.temperaturaConservazioneRichiesta != null}">
				- temperatura di conservazione: ${a.temperaturaConservazioneRichiesta}
			</c:if>
		</td>
	</tr>
	
	</table>
	
	<br/>
<%
	String specie = cc.getAccettazione().getAnimale().getLookupSpecie().getDescription();
%>
		<center><h2>Dati del <%=specie%></h2></center>
	<table width="100%" class="bordo">
		<col width="25%"><col width="25%">
	<tr>
	<td><i>Identificativo</i></td>
	<td>${cc.accettazione.animale.identificativo }</td>
	<td><i>Specie</i></td>
	<td><%=specie%></td>
	</tr>
	
		<tr>
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
	<td><i>Sesso</i></td>
	<td>${cc.accettazione.animale.sesso }</td>
	</tr>
	
	<tr>
	<td><i>Taglia</i></td>
	<td>${anagraficaAnimale.taglia}</td>
	<td><i>Mantello</i></td>
	<td>${anagraficaAnimale.mantello}</td>
	</tr> 
	
		<tr>
	<td><i>Sterilizzato</i></td>
	<td>${anagraficaAnimale.sterilizzato}</td>
	<td><i>Stato attuale</i></td>
	<td>${anagraficaAnimale.statoAttuale}</td>
	</tr> 
	
	
	<tr>
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

<c:choose>
        	<c:when test="${cc.accettazione.animale.decedutoNonAnagrafe == true}">						
				<fmt:formatDate type="date" value="${cc.accettazione.animale.dataMorte}" pattern="dd/MM/yyyy" var="dataMorte"/>
			</c:when>
			<c:otherwise>
				<fmt:formatDate type="date" value="<%=dataEvento%>" pattern="dd/MM/yyyy" var="dataMorte"/>
			</c:otherwise>	
		</c:choose>
		
<td><i>Data del decesso</i></td>
<td>${dataMorte} - <c:choose>
					<c:when test="${cc.accettazione.animale.decedutoNonAnagrafe == true}">						
						${cc.accettazione.animale.dataMorteCertezza}
					</c:when>
					<c:otherwise>
						<%=dataMorteCertezza%>
					</c:otherwise>	
				</c:choose>	</td>
</tr>

<tr><td></td>
<td></td>
<td><i>Probabile causa del decesso</i></td>
<td>
	<c:choose>
		<c:when test="${cc.accettazione.animale.decedutoNonAnagrafe}">
	    	${cc.accettazione.animale.causaMorte.description}
	    </c:when>
	    <c:otherwise>
	    	<%=decessoValue%>
	    </c:otherwise>
	</c:choose></td>
</tr>
	
	</table>
	
	<br/>
<c:if test="${cc.accettazione.animale.decedutoNonAnagrafe==true && (cc.accettazione.randagio==true || cc.accettazione.animale.lookupSpecie.id==3)}">	
	<center><h2>Ritrovamento carcassa</h2></center>
	<table width="100%" class="bordo">
		<col width="25%"><col width="25%">
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
</table>
<br/><br/>
</c:if>

<br/>

	<c:if test="${not empty a.cmf}">
<center><h2>Cause del decesso Finale</h2></center>
	<table width="100%" class="bordo">
<tr><td style="width: 80'%" class="blue"><b>Descrizione</b></td>
<td style="width: 20%" class="blue"><b>Provata/Sospetta</b></td></tr>

		<c:set var="i" value='1' />
		<c:forEach items="${a.cmf}" var="cmf">
			<tr>
				<td class="layout"><i><c:out value="${cmf.lookupCMF.description }" /></i>
				</td>
				<td class="layout"><c:choose>
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
	</table>
	</c:if>

<br/>
<center><h2>Dati del Proprietario</h2></center>
	<table width="100%" class="bordo">
		<tr>
			<td><i>Cognome e nome:</i></td>
			<td>${cc.accettazione.proprietarioCognome} ${cc.accettazione.proprietarioNome}</td>
		</tr>
		<tr>
			<td><i>Codice Fiscale:</i></td>
			<td>${cc.accettazione.proprietarioCodiceFiscale}</td>
		</tr>
		<tr>
			<td><i>Comune di residenza:</i></td>
			<td>${cc.accettazione.proprietarioComune}</td>
		</tr>
		<tr>
			<td><i>Indirizzo:</i></td>
			<td>${cc.accettazione.proprietarioIndirizzo}</td>
		</tr>
		<tr>
			<td><i>Telefono:</i></td>
			<td>${cc.accettazione.proprietarioTelefono}</td>
		</tr>
	</table>
		
	<br/><br/><br/>
		
		<table width="100%">
		<col width="50%">
		<tr><td><center>Data</center></td>
		<td><center>Firma</center></td></tr>
		<tr><td><center><fmt:formatDate type="date" value="<%= new java.util.Date() %>" pattern="dd/MM/yyyy"/></center></td>
	 <td><center>_______________________</center></td></tr>
        	
	
	
	</table>
	
	<br/>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	