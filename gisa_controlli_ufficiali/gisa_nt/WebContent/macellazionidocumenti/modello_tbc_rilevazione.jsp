<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="macello" class="org.aspcfs.modules.stabilimenti.base.Organization" scope="request"/>
<jsp:useBean id="mod" class="org.aspcfs.modules.macellazionidocumenti.base.ModelloGenerico" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="listaSpecie" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ProvvedimentiList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="NonConformitaList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="listaLuoghiVerifica" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="listaRazze" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="categorieBovine" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="categorieBufaline" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="nomeMacello" class="java.lang.String" scope="request"/>
<jsp:useBean id="approvalNumber" class="java.lang.String" scope="request"/>
<jsp:useBean id="comuneMacello" class="java.lang.String" scope="request"/>

<%@page import="org.aspcfs.modules.speditori.base.OrganizationAddress"%>
<%@page import="org.aspcfs.modules.macellazioni.base.*"%>
<%@page import="org.aspcfs.modules.macellazionidocumenti.base.ModelloGenerico"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../../initPage.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Scheda</title>
</head>
<link rel="stylesheet" type="text/css" media="screen" documentale_url="" href="macellazionidocumenti/css/macelli_moduli_screen.css" />
<link rel="stylesheet" type="text/css" media="print" documentale_url="" href="macellazionidocumenti/css/macelli_moduli_print.css" />

<body> 
 <% 
 ArrayList<ModelloGenerico> listaModelli = null;
 listaModelli = mod.getListaModelli();
	int sizeLista = listaModelli.size();
	int i = 0;
	for (ModelloGenerico listaModelliElemento : listaModelli) {
	 //for(int i=0; i<listaModelli.size();i++){
	//	 ModelloGenerico listaModelliElemento = (ModelloGenerico) listaModelli.get(i);
	 
	 //for(Map.Entry<Integer,org.aspcfs.modules.speditori.base.Organization> speditoreList : listaSpeditori.entrySet()){
		 int idSpeditore = listaModelliElemento.getIdSpeditore();
		 org.aspcfs.modules.speditori.base.Organization speditoreOrg = null;
		 OrganizationAddress speditoreAddress = null;
			speditoreOrg = PopolaCombo.buildSpeditore(idSpeditore);
		 
		 
	 String speditore = "..........................."; String cod_speditore = "..........................."; String comune = "..........................."; String asl="...........................";
		String loc = "...........................";
		
	 			if(speditoreOrg != null){
	 				if( speditoreOrg.getName() != null && !speditoreOrg.getName().equals("") ){
	 					speditore = speditoreOrg.getName();
	 				}
	 				if( speditoreOrg.getAccountNumber() != null && !speditoreOrg.getAccountNumber().equals("") ){
	 					cod_speditore = speditoreOrg.getAccountNumber();
	 				}
	 				if(speditoreOrg.getAddressList().size() > 0){
	 					speditoreAddress = (OrganizationAddress) speditoreOrg.getAddressList().get(0);
	 				}
	 				if(speditoreOrg.getSiteId() > 0){
	 					asl = SiteList.getSelectedValue(speditoreOrg.getSiteId()).toUpperCase();
	 					
	 				}
	 			}
	 			if(speditoreAddress != null){
	 				if( speditoreAddress.getCity() != null && !speditoreAddress.getCity().equals("") ){
	 					comune = speditoreAddress.getCity();
	 				}
	 				if( speditoreAddress.getState() != null && !speditoreAddress.getState().equals("") ){
	 					loc = speditoreAddress.getState();
	 				}
	 			}
	 
	%>
	
<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div> 

	 
<table>
<tr><td class="piccolo"><i>7-12-2006</i></td>
<td class="piccolo"><i>supplemento ordinario</i> alla <b>GAZZETTA UFFICIALE</b></td>
<td class="piccolo"><i>serie generale</i>-n. 285</td></tr>
</table>		 
		 
<table width="100%">
<col width="33%"><col width="33%"><col width="20%">
<tr>
<td class="piccolo">Prot.  <%=listaModelliElemento.getProgressivo() + "/" + SiteList.getSelectedValue(speditoreOrg.getSiteId()).toUpperCase() + "/" + listaModelliElemento.getAnnoDataModulo()%>
	<% if(listaModelliElemento.getOldProgressivo() > 0){%>
				<br/> (sostituisce il doc. con prot.  <%=listaModelliElemento.getOldProgressivo() + "/" + SiteList.getSelectedValue(speditoreOrg.getSiteId()).toUpperCase() + "/" + listaModelliElemento.getAnnoDataModulo()%> )
				<%} %> 
 </td>
<td> </td>
<td align="right"><b>ALLEGATO C</b></td>
</tr>
</table>
	 <br/><br/>
<center><div class="titolo">TUBERCOLOSI BOVINA /BUFALINA<br/>
FOCOLAIO<br/>
SCHEDA DI RILEVAMENTO DATI AL MACELLO</div></center>
	<br/><br/>	 
<table>
<col width="50%">
<tr><td><b>DR.</b> .......................................... </td><td> <b>Recapito telefonico</b> .......................................... </td></tr>
<tr><td><b>ASL:</b> ..........................................  </td><td> <b>Data:</b> .......................................... </td></tr>
<tr><td><b>MACELLO:</b> ..........................................  </td><td> <b>Tel.</b> .......................................... ; <b>Fax</b> ........................................... </td></tr>
<tr><td><b>VIA:</b> ..........................................  </td><td> <b>Comune</b> ............................. <b>Prov</b> ...............</td></tr>
</table>
<br/><br/>
<center><b>ANIMALI PROVENIENTI DA ALLEVAMENTO INFETTO</b></center>
<center>Animali negativi alla TBC, positivi alla BRC</center>
<br/><br/>

<table>
<col width="20%">
<tr> <td><b><u>Allevamento: </u></b>
<td> <b>Codice identificazione azienda (DPR 317/96)</b> <%=cod_speditore %></td> </tr>
<tr> <td><b><u></u></b>
<td> <b>Denominazione azienda</b>  <%=speditore %></td></tr>
<tr> <td><b><u></u></b>
<td> <b>Proprietario</b> <%="N.D." %></td> </tr>
<tr> <td><b><u></u></b>
<td> <b>Via</b> <%=(speditoreAddress!=null) ? speditoreAddress : "....................................................." %>  <b>N°</b> ............ </td> </tr>
<tr> <td><b><u></u></b>
<td> <b>Comune</b> <%=comune %> <b>Prov</b> <%=loc %></td> </tr>
<tr> <td><b><u></u></b>
<td> <b>A.S.L.</b> <%=asl%></td> </tr>
</table>
<br/><br/>

 <center><b>Numero e tipo di animali esaminati</b></center>
<table cellpadding="5" style="border-collapse: collapse; float: left; width:50%" >
<col width="50%">
<% 
HashMap<Integer,Integer> listaCategorieBovine = listaModelliElemento.getHashCategorieBovine();
for(Map.Entry<Integer, Integer> elemento : listaCategorieBovine.entrySet()){
	%>
	<tr><td><%=categorieBovine.getSelectedValue(elemento.getKey()) %></td> <td>N. <%=elemento.getValue() %></td></tr>
<% } %>
</table>
<table cellpadding="5" style="border-collapse: collapse; float: left; width:50%" >
<col width="50%"> 
<% 
HashMap<Integer,Integer> listaCategorieBufaline = listaModelliElemento.getHashCategorieBufaline();
for(Map.Entry<Integer, Integer> elemento : listaCategorieBufaline.entrySet()){
	%>
	<tr><td><%=categorieBufaline.getSelectedValue(elemento.getKey()) %>
	<%if (categorieBufaline.getSelectedValue(elemento.getKey()).equals("ANNUTOLO")){ %> <font size="2px">(2)</font><%} %>
	<%if (categorieBufaline.getSelectedValue(elemento.getKey()).equals("ANNUTOLA")){ %> <font size="2px"> (3) </font><%} %>
	<%if (categorieBufaline.getSelectedValue(elemento.getKey()).equals("VITELLO BUFALINO")){ %> <font size="2px"> (1) </font><%} %>
	</td> 
	
	<td>N. <%=elemento.getValue() %> </td></tr>
<% } %>

<tr><td class="piccolo"> (1) Dalla nascita allo svezzamento<br/>
(2) Dallo svezzamento a 24 mesi<br/>
(3) Dallo svezzamento al primo intervento fecondativo.</td></tr>
</table>

    <div style="page-break-before:always">&nbsp; </div>  
 <table>
<tr><td class="piccolo"><i>7-12-2006</i></td>
<td class="piccolo"><i>supplemento ordinario</i> alla <b>GAZZETTA UFFICIALE</b></td>
<td class="piccolo"><i>serie generale</i>-n. 285</td></tr>
</table>	
 
 
 <center><b>REPERTO ISPETTIVO</b></center> 
 
 <table cellpadding="1" border="1px" style="border-collapse: collapse;" width="30%" >
 <col width="10%"><col width="10%"><col width="10%">
 <tr><th></th><th> N° di animali esaminati</th><th>N° animali con lesioni tubercolari</th></tr>
<% 
HashMap<Integer,Integer> listaCategorieInfettiBovini = listaModelliElemento.getHashCategoriaNumeroInfettiBovini();
for(Map.Entry<Integer, Integer> elemento : listaCategorieBovine.entrySet()){
	%>
	<tr><td style="border:0px"><%=categorieBovine.getSelectedValue(elemento.getKey()) %></td> <td style="border:0px">N. <%=elemento.getValue() %></td> <td style="border:0px">N. <%=listaCategorieInfettiBovini.get(elemento.getKey()) %></td></tr>
<% } %> 
<tr><td style="border:0px">-------</td><td style="border:0px">----------------</td><td style="border:0px">--------------------</td></tr>
<% 
HashMap<Integer,Integer> listaCategorieInfettiBufalini = listaModelliElemento.getHashCategoriaNumeroInfettiBufalini();
for(Map.Entry<Integer, Integer> elemento : listaCategorieBufaline.entrySet()){
	%>
	<tr><td style="border:0px"><%=categorieBufaline.getSelectedValue(elemento.getKey()) %></td> <td style="border:0px">N. <%=elemento.getValue() %></td><td style="border:0px">N. <%=listaCategorieInfettiBufalini.get(elemento.getKey()) %></td></tr>
<% } %>
</table>
<br/><br/>
 
 <table>
 <tr><th><b>MATERIALE PATOLOGICO DA PRELEVARE</b> (1)</th></tr>
 <tr><td>(anche in caso di reperto anatomo-patologico negativo)</td></tr>
 <tr><td><u>IN PRESENZA DI LESIONI SOSPETTE:</u></td></tr>
 <tr><td><b>a) ORGANI SEDE DI LESIONE</b> (2)</td></tr>
 <tr><td>Evitare di incidere a fondo i siti di lesione per non compromettere l'esito dell'esame colturale ( possibile inquinamento del campione)</td></tr>
 <tr><td><u>IN APPARENTE ASSENZA DI LESIONI RIFERIBILI A TBC:</u></td></tr>
 <tr><td><b>a) TONSILLE</b></td></tr>
 <tr><td><b>b) LINFONODI:</b><br/>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; RETROFARINGEI<br/>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; MANDIBOLARI<br/>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; TRACHEOBRONCHIALI<br/>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; MEDIASTINICI<br/>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; MESENTERICI<br/>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; EPATICO-MESENTERICI (NEI VITELLI)<br/>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; SUB-ILIACI</td></tr>
 <tr><td><b>c) POLMONE</b></td></tr>
 <tr><td>(1):I campioni confezionati singolarmente in contenitore sterile a tenuta ed identificati con etichetta 
riportante il n° di matricola dell'animale e natura dell'organo contenuto, devono essere inviati nel 
più breve tempo possibile alla vicina Sezione dell'Istituto Zooprofilattico, avendo cura di 
mantenerli atemperatura di refrigerazione. Se l'invio non è effettuabile entro le 12 ore dal prelievo, 
è necessario suddividere il campione in due aliquote , una delle quali da sottoporre a congelamento 
(esame colturale), e la seconda da congelare o fissare in formalina al 10% (esame istologico). 
(2): Inviare al laboratorio, in ogni caso, le lesioni tubercolari o similtubercolari riscontrate in sede 
ispettiva; in assenza di queste prelevare un numero adeguato di campioni, dagli animali risultati 
positivi alla tubercolina, previo accordo con l'IZS competente.</td></tr>
 </table>
 
    <div style="page-break-before:always">&nbsp; </div>  
 <table>
<tr><td class="piccolo"><i>7-12-2006</i></td>
<td class="piccolo"><i>supplemento ordinario</i> alla <b>GAZZETTA UFFICIALE</b></td>
<td class="piccolo"><i>serie generale</i>-n. 285</td></tr>
</table>	
 
 
 <table border="1px"><tr><th>Contrassegno <br/>Identificazione <br/>marca auricolare)<br/>data di nascita</th>
<th colspan="2">Organi colpiti</th>
<th>Tipo di lesione (3)</th>
<th>Lesioni aspecifiche (4)</th>
<th colspan="2">Organi /sangueprelevati</th></tr>
 
<%for (int j=0;j<10;j++) {%>
<tr>
<td>&nbsp;</td>
<td>[ ] tonsille<br/>
[ ] Ln. retrofaringei<br/>
[ ] Ln. Mandibolari<br/>
[ ] Ln. tracheobronchiali<br/>
[ ] Ln. mediastinici</td>
<td>[ ] Ln. mesenterici<br/>
[ ] Ln. epatici<br/>
[ ] Ln. Sub-iliaci<br/>
[ ] Ln. ....................... </td>
<td> &nbsp; </td>
<td>  &nbsp; </td>
<td> [ ] tonsille<br/>
[ ] Ln. retrofaringei<br/>
[ ] Ln. Mandibolari<br/>
[ ] Ln. tracheobronchiali<br/>
[ ] Ln. mediastinici<br/></td>
<td>[ ] Ln. mesenterici<br/>
[ ] Ln. epatici<br/>
[ ] Ln. Sub-iliaci<br/>
[ ] sangue </td>
</tr>

<%} %>
</table>

(3) Tipo di lesione: 1- complesso primario, 2- generalizzazione acuta miliare. 3- generalizzazione protratta, 4- forma 
organica cronica evolutiva, 5- collasso delle resistenze generali, 6- nessuna lesione apparente (NVL). 
(4) Descrivere le eventuali lesioni non riconducibili all'infezione tubercolare ma a patologie in grado di generare false 
positività : 1- Paratubercolosi, 2- Distomatosi, 3- Actinogranulomatosi, 4- Elmintiasi gastro-int., 5-Lesioni da corpo 
estraneo, 6 - Cisticercosi/idatidosi, 7- Granuloma di Roeckl, 8- ectoparassitosi, 9-Nocardiosi,10- Dermatite nodosa, 11- 
Altro (specificare).<br/><br/>

<table border="1px">
<tr><td colspan="3" style="border:0px"><center><b>SPAZIO RISERVATO ALLA SEZIONE DIAGNOSTICA -I.Z.S.</b></center></td></tr>
<tr><td colspan="3" style="border:0px"><center>Numero di registro sezione: ...................................</center></td></tr>
<tr><td style="border:0px">Contrassegno <br/>Identificazione<br/>(marca auricolare)</td>
<td style="border:0px">Esami di laboratorio<br/>effettuati</td>
<td style="border:0px">Osservazioni</td>
</tr>
<%for (int j=0;j<10;j++) {%>
<tr><td style="border:0px">................................................................</td> 
<td style="border:0px">....................................................................</td>
<td style="border:0px">....................................................................</td>
</tr>
<% } %>
</table>
<br/><br/>
<div align="left">DATA DI INVIO ALL'ISTITUTO ZOOPROFILATTICO .......................................... </div>
<div align="right">
SEDE CENTRALE [ ] <br/> 
SEZIONE DI _____________________ [ ] <br/><br/>

I VETERINARI UFFICIALI<br/>
_________________________________________________________<br/><br/>
_________________________________________________________<br/><br/>
_________________________________________________________<br/></div>
	</div>
<br><br><br>
<%@ include file="/gestione_documenti/gdpr_footer.jsp" %>
<br/>
<% 
i++;
	if (i!=sizeLista) { %>
	<div style="page-break-before:always">&nbsp; </div>  
<% }  }%>
	
<%if (listaModelli.size()==0) { %>
<font color="red"> Nessun capo con piano di risanamento tubercolosi macellato il giorno <%=toDateasString(mod.getData()) %>  </font>
<%} %>	
</body>
</html>