<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<link rel="stylesheet" documentale_url="" href="jsp/vam/cc/print.css" type="text/css" media="print" />

<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<%@page import="it.us.web.bean.vam.TerapiaDegenza"%>
<%@page import="it.us.web.bean.vam.CartellaClinica"%>
<%@page import="it.us.web.bean.vam.Trasferimento"%>
<%@page import="it.us.web.bean.vam.TipoIntervento"%>
<%@page import="it.us.web.bean.vam.Accettazione"%>
<%@page import="it.us.web.bean.vam.lookup.LookupOperazioniAccettazione"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>


<%@page import="it.us.web.constants.IdOperazioniBdr"%>
<%@page import="it.us.web.constants.IdRichiesteVarie"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.net.InetAddress"%>
<%@page import="it.us.web.util.properties.Application"%>

<script language="JavaScript" type="text/javascript" src="js/vam/cc/stampaCC/popupDetails.js"></script>

<style>

@media all {

.title1 {
	font-size: 14px;
	text-decoration: underline;
	text-align: center;
	margin-top: 70px;
}
.nodott {
	margin-top: 0px;
	margin-right: 4px;
	float: left;
}

.Section1 {
	color: black;
	position: relative;
	border: 0px solid red;
}

.dott {
	width: 180px;
	margin-top: 50px;
	margin-left: 0px;
	border-bottom: 1px dashed #000;
	float: left;
}
.nodott_margin_low {
	margin-top: 10px;
	margin-right: 4px;
	float: left;
	font-size: 14px;
}

.dott_margin_low {
	width: 180px;
	margin-top: 10px;
	margin-left: 0px;
	border-bottom: 1px dashed #000;
	float: left;
	font-size: 12px;
	font-weight: lighter;
}

.dott_long_margin_low {
	width: 380px;
	margin-top: 10px;
	margin-left: 0px;
	border-bottom: 1px dashed #000;
	float: left;
	font-size: 12px;
	font-weight: lighter;
}
.dott_short_margin_low {
	width: 20px;
	margin-top: 10px;
	margin-left: 0px;
	border-bottom: 1px dashed #000;
	float: left;
	font-size: 12px;
	font-weight: lighter;
}
.nodott {
	margin-top: 50px;
	margin-right: 4px;
	font-size: 14px;
}

.clear1 {
	clear: both;
}

.firma {
	position: absolute;
	right: 25px;
	font-size: 12px;
}

.firmavalore {
	position: absolute;
	right: 25px;
	border-bottom: 1px dashed #000;
	margin-top: 30px;
	width: 150px;
}
.data {
	position: absolute;
	left: 25px;
	font-size: 12px;
}

.datavalore {
	position: absolute;
	left: 25px;
	border-bottom: 1px dashed #000;
	margin-top: 30px;
	width: 150px;
}

.imgAsl {
	position: absolute;
	right: 10px;
	border: 0px solid black;
	width: 80px;
	height: 80px;
	margin-bottom: 200px;
}

.imgRegione {
	position: absolute;
	left: 10px;
	border: 0px solid black;
	width: 80px;
	height: 80px;
	margin-bottom: 200px;
}


.boxIdDocumento {
	position: absolute;
	left: 100px;
	border: 0.5px solid black;
	width: 60px;
	height: 20px;
	margin-top: 20px;
	text-align: center;
	padding-top: 5px;
	font-size: 8px;
}

.boxOrigineDocumento {
	position: absolute;
	left: 100px;
	width: 160px;
	height: 20px;
	margin-top: 45px;
	text-align: left;
	padding-top: 10px;
	font-size: 9px;
}

}
	
</style>

<%

Accettazione accettazione = (Accettazione)request.getAttribute("accettazione");
%>

<%-- <body onload="javascript:closeAndRefresh('<%= request.getAttribute("chiudi")%>','<%= request.getAttribute("redirect")%>')">--%>
<body>
<fmt:setLocale value="en_US" /> <!-- ALTRIMENTI LA STAMPA NON FUNZIONA -->
 <br>
<c:set var="tipo" scope="request" value="stampaDecesso" />
<c:import url="../../jsp/documentale/home.jsp"/>
<br>
<br>
<div class="imgRegione">
<img style="text-decoration: none;" width="80" height="80" documentale_url="" src="images/asl/regionecampania.jpg" />
</div>
<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div>
	
<div class="imgAsl">
<img style="text-decoration: none;" width="80" height="80" documentale_url="" src="images/asl/${utente.clinica.lookupAsl}.jpg" />
</div>

<div class="Section1">
 </br>
<div class="title1">CERTIFICATO DECESSO</div>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;

<div class="nodott_margin_low"><b><u>Dati del proprietario</u></b></div>
<div class="clear1"></div>

<div class="nodott_margin_low">Cognome e nome: </div>
<div class="dott_long_margin_low">&nbsp;${accettazione.proprietarioCognome} ${accettazione.proprietarioNome}</div>
<div class="clear1"></div>
<div class="nodott_margin_low">Codice Fiscale:</div>
<div class="dott_margin_low">&nbsp; ${accettazione.proprietarioCodiceFiscale}</div>
<div class="clear1"></div>
<div class="nodott_margin_low" >Comune di residenza:</div>
<div class="dott_margin_low" >&nbsp;  ${accettazione.proprietarioComune}</div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Indirizzo:</div>
<div class="dott_long_margin_low" >&nbsp; ${accettazione.proprietarioIndirizzo}</div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Telefono:</div>
<div class="dott_margin_low" >&nbsp;  ${accettazione.proprietarioTelefono}</div>

<div class="clear1"></div>

<div class="nodott_margin_low" ><b><u>Dati del  ${accettazione.animale.lookupSpecie.description}</u></b></div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Identificativo: </div>
<div class="dott_margin_low" >&nbsp;  ${accettazione.animale.identificativo} </div>
<div class="nodott_margin_low" >Tatuaggio/II MC: </div>
<div class="dott_margin_low" >&nbsp;  ${accettazione.animale.tatuaggio}&nbsp; </div>
<div class="nodott_margin_low" >Taglia:</div>
<div class="dott_margin_low" >&nbsp; ${anagraficaAnimale.taglia} </div>


<div class="nodott_margin_low" >Sesso:</div>
<div class="dott_short_margin_low" >&nbsp; ${accettazione.animale.sesso}</div>
<div class="clear1"></div>

<c:if test="${accettazione.animale.lookupSpecie.id!=3}">
<div class="nodott_margin_low" >Razza:</div>
</c:if>
<c:if test="${accettazione.animale.lookupSpecie.id==3}">
<div class="nodott_margin_low" >Famiglia/Genere:</div>
</c:if>

<c:if test="${(accettazione.animale.lookupSpecie.id == specie.cane or accettazione.animale.lookupSpecie.id == specie.gatto) && accettazione.animale.decedutoNonAnagrafe==false}">
<div class="dott_long_margin_low" >&nbsp; ${anagraficaAnimale.razza}</div></c:if>
<c:if test="${accettazione.animale.decedutoNonAnagrafe==true}">
<div class="dott_long_margin_low" >&nbsp; ${anagraficaAnimale.razza}</div></c:if>
<c:if test="${accettazione.animale.lookupSpecie.id == specie.sinantropo && accettazione.animale.decedutoNonAnagrafe==false}">
<div class="dott_long_margin_low" >&nbsp; ${accettazione.animale.specieSinantropoString} - ${accettazione.animale.razzaSinantropo}</div></c:if>
    		   
<div class="clear1"></div>

<div class="nodott_margin_low" >Mantello:</div>
<div class="dott_margin_low" >&nbsp; ${anagraficaAnimale.mantello}</div>
<div class="clear1"></div>

<fmt:formatDate type="date" value="${accettazione.animale.dataNascita }" pattern="dd/MM/yyyy" var="dataNascita"/>
<c:choose>
<c:when test="${accettazione.animale.lookupSpecie.id==3}">
<div class="nodott_margin_low" >Età:</div>
<div class="dott_margin_low" >&nbsp; 
<%
	if(accettazione.getAnimale().getEta().getDescription()!=null)
	{
%>
		${accettazione.animale.eta} 
<%
	}
%> 
${dataNascita}</div>
</c:when>
<c:otherwise>
<div class="nodott_margin_low" >Data nascita:</div>
<div class="dott_margin_low" >&nbsp;${dataNascita}</div>
</c:otherwise></c:choose>
<div class="clear1"></div>
<div class="nodott_margin_low" >Data decesso:</div>
<c:choose>
	<c:when test="<%=accettazione.getAnimale().getDecedutoNonAnagrafe()==true%>">						
		<fmt:formatDate type="date" value="<%=accettazione.getAnimale().getDataMorte()%>" pattern="dd/MM/yyyy" var="dataMorte"/>
	</c:when>
	<c:otherwise>
		<fmt:formatDate type="date" value="${res.dataEvento}" pattern="dd/MM/yyyy" var="dataMorte"/>
	</c:otherwise>	
</c:choose>
<div class="dott_margin_low" >&nbsp; ${dataMorte}</div>
<div class="nodott_margin_low" >Presunta:</div>
<c:if test="${accettazione.animale.dataMortePresunta==true}">
<div class="dott_margin_low" > &nbsp; SI</div></c:if>
<c:if test="${accettazione.animale.dataMortePresunta!=true}">
<div class="dott_margin_low" > &nbsp; NO</div></c:if>
<div class="clear1"></div>

<div class="nodott_margin_low" ><b><u>Registrazione smaltimento spoglie</u></b></div>
<div class="clear1"></div>
<div class="nodott_margin_low" >Data: </div>
<fmt:formatDate type="date" value="${accettazione.animale.dataSmaltimentoCarogna}" pattern="dd/MM/yyyy" var="dataSmaltimento"/>  	
<div class="dott_margin_low" >&nbsp;  ${dataSmaltimento}</div>
<div class="nodott_margin_low" >DDT:</div>
<div class="dott_margin_low" >&nbsp; ${accettazione.animale.ddt}</div>
<div class="clear1"></div>
<div class="nodott_margin_low" >Ditta autorizzata: </div>
<div class="dott_margin_low" >&nbsp; ${accettazione.animale.dittaAutorizzata}</div>
<div class="clear1"></div>

<c:if test="${accettazione.animale.decedutoNonAnagrafe==true && accettazione.randagio==true}">
	<div class="nodott_margin_low" ><b><u>Ritrovamento carcassa</u></b></div>
	<div class="clear1"></div>
	<div class="nodott_margin_low" >Comune: </div>
	<div class="dott_margin_low" >&nbsp;  ${accettazione.animale.comuneRitrovamento.description} </div>
	<div class="nodott_margin_low" >Provincia:</div>
	<div class="dott_margin_low" >&nbsp; ${accettazione.animale.provinciaRitrovamento} </div>
	<div class="clear1"></div>
	<div class="nodott_margin_low" >Indirizzo: </div>
	<div class="dott_margin_low" >&nbsp; ${accettazione.animale.indirizzoRitrovamento} </div>
	<div class="clear1"></div>
	<div class="nodott_margin_low" >Note: </div>
	<div class="dott_margin_low" >&nbsp; ${accettazione.animale.noteRitrovamento} </div>
	<div class="clear1"></div>
</c:if>

<br/><br/>
<div class="data">DATA</div>
<jsp:useBean id="now" class="java.util.Date"/>    
<fmt:formatDate value="${now}" pattern="dd/MM/yyyy" var="dataOra"/>
<div class="datavalore">&nbsp;${dataOra}</div>
<div class="firma">TIMBRO E FIRMA DEL VETERINARIO</div>

<div class="firmavalore">&nbsp;</div>

</br>

</div>
<br/>
</body>

