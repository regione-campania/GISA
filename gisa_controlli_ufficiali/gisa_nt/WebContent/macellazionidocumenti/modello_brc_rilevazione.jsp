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
<%@page import="org.aspcfs.modules.macellazioni.base.Capo"%>
<%@page import="org.aspcfs.modules.macellazioni.base.Casl_Non_Conformita_Rilevata"%>
<%@page import="org.aspcfs.modules.macellazioni.base.ProvvedimentiCASL"%>
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
		String loc = "..........................."; String via="..............................................";
		
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
	 				if( speditoreAddress.getStreetAddressLine1() != null && !speditoreAddress.getStreetAddressLine1().equals("") ){
	 					via = speditoreAddress.getStreetAddressLine1();
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
<td class="piccolo"> Prot.  <%=listaModelliElemento.getProgressivo() + "/" + SiteList.getSelectedValue(speditoreOrg.getSiteId()).toUpperCase() + "/" + listaModelliElemento.getAnnoDataModulo()%>
	<% if(listaModelliElemento.getOldProgressivo() > 0){%>
				<br/> (sostituisce il doc. con prot.  <%=listaModelliElemento.getOldProgressivo() + "/" + SiteList.getSelectedValue(speditoreOrg.getSiteId()).toUpperCase() + "/" + listaModelliElemento.getAnnoDataModulo()%> )
				<%} %> 
 </td>
<td> </td>
<td align="right"><b>ALLEGATO D</b></td>
</tr>
</table>
	 <br/><br/>
<center><div class="titolo">BRUCELLOSI  BOVINA /BUFALINA<br/>
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
<td> <b>Proprietario</b> <%=speditoreOrg.getNomeRappresentante() %></td> </tr>
<tr> <td><b><u></u></b>
<td> <b>Via</b> <%=via %>  <b>N°</b> ............ </td> </tr>
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
	<tr><td><%=categorieBufaline.getSelectedValue(elemento.getKey()) %></td> <td>N. <%=elemento.getValue() %></td></tr>
<% } %>
</table>

 
<center><b>REPERTO ISPETTIVO</b></center>
Descrizione delle lesioni anatomo-patologiche riscontrate:<br/>
__________________________________________________________________________________________________<br/><br/>
__________________________________________________________________________________________________<br/><br/>
__________________________________________________________________________________________________<br/>
(1) dallo svezzamento al primo intervento fecondativo<br/><br/>

 <div style="page-break-before:always">&nbsp; </div>  
<table border="1px"><tr><th>Contrassegno <br/>Identificazione <br/>marca auricolare)<br/>data di nascita</th>
<th>CATEGORIA</th>
<th colspan="3">Organi prelevati</th></tr>
 <% 
 ArrayList<Capo> listaCapi = null;
listaCapi = listaModelliElemento.getListaCapi();
int b = 0;
for(Capo capo : listaCapi){
	%>
	<tr>
<td align="center"><%=capo.getCd_matricola() %><br/><%=toDateasString(capo.getCd_data_nascita()) %></td>
<td>
<%for(Map.Entry<Integer, Integer> elemento : listaCategorieBovine.entrySet()){
	%>
	[ <%if (capo.isBovino() && capo.getCd_categoria_bovina()==elemento.getKey()){ %>X<%} %> ] <%=categorieBovine.getSelectedValue(elemento.getKey()) %><br/>
<% } %>
<%for(Map.Entry<Integer, Integer> elemento : listaCategorieBufaline.entrySet()){
	%>
	[ <%if (capo.isBufalino() && capo.getCd_categoria_bufalina()==elemento.getKey()){ %>X<%} %> ] <%=categorieBufaline.getSelectedValue(elemento.getKey()) %><br/>
<% } %>
<td>[ ] Ln.sopramammari (F)<br/>
[ ] Ln. Inguinali superficiali (M)<br/>
[ ] Ln. mandibolari<br/>
[ ] Ln. retrofaringei<br/>
[ ] Ln. Iliaci interni </td>
<td>[ ] milza<br/>
[ ]utero gravido<br/>
[ ]placenta<br/>
[ ]mammella<br/>
[ ]vescicole<br/>
seminali e<br/>
testicoli </td>
<td> [ ] altro </td>
</tr>
<% }%>


<%for (int j=b;j<10;j++) {%>
<tr>
<td>&nbsp;</td>
<td><%for(Map.Entry<Integer, Integer> elemento : listaCategorieBovine.entrySet()){
	%>
	[ ] <%=categorieBovine.getSelectedValue(elemento.getKey()) %><br/>
<% } %>
<%for(Map.Entry<Integer, Integer> elemento : listaCategorieBufaline.entrySet()){
	%>
	[ ] <%=categorieBufaline.getSelectedValue(elemento.getKey()) %><br/>
<% } %></td>
<td>[ ] Ln.sopramammari (F)<br/>
[ ] Ln. Inguinali superficiali (M)<br/>
[ ] Ln. mandibolari<br/>
[ ] Ln. retrofaringei<br/>
[ ] Ln. Iliaci interni </td>
<td>[ ] milza<br/>
[ ]utero gravido<br/>
[ ]placenta<br/>
[ ]mammella<br/>
[ ]vescicole<br/>
seminali e<br/>
testicoli </td>
<td> [ ] altro </td>
</tr>

<%} %>
</table>

<div style="page-break-before:always">&nbsp; </div>  
<center><b>SPAZIO RISERVATO ALLA SEZIONE DIAGNOSTICA -I.Z.S.</b></center>
<center>Numero di registro sezione: ...................................</center>
<br/><br/><br/><br/>
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
<div align="right">I VETERINARI UFFICIALI<br/>
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

<%--<body>

<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div> 
		 
<table>
<tr><td><i>7-12-2006</i></td>
<td><i>supplemento ordinario</i> alla <b>GAZZETTA UFFICIALE</b></td>
<td><i>serie generale</i>-n. 285</td></tr>
</table>		 
		 
<table width="100%">
<col width="33%"><col width="33%"><col width="20%">
<tr>
<td> Prot.  <%=mod.getProgressivo() + "/" + SiteList.getSelectedValue(mod.getAslMacello()).toUpperCase() + "/" + mod.getAnnoDataModulo()%>
	<% if(mod.getOldProgressivo() > 0){%>
				<br/> (sostituisce il doc. con prot.  <%=mod.getOldProgressivo() + "/" + SiteList.getSelectedValue(mod.getAslMacello()).toUpperCase() + "/" + mod.getAnnoDataModulo()%> )
				<%} %>
 </td>
<td> </td>
<td align="right"><b>ALLEGATO D</b></td>
</tr>
</table>
	 <br/><br/>
<center><div class="titolo">BRUCELLOSI  BOVINA /BUFALINA<br/>
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

<% 
// ArrayList<Capo> lista = null;
// lista = mod.getListaCapi();
// Capo c = (Capo)lista.get(0);

String speditore = "..........................."; String cod_speditore = "..........................."; String comune = "...........................";
	String loc = "...........................";
		org.aspcfs.modules.speditori.base.Organization speditoreOrg = null;
		OrganizationAddress speditoreAddress = null;
					
// 			if(c.getCd_id_speditore() > 0){
// 				speditoreOrg = PopolaCombo.buildSpeditore(c.getCd_id_speditore());
// 			}
// 			if(speditoreOrg != null){
// 				if( speditoreOrg.getName() != null && !speditoreOrg.getName().equals("") ){
// 					speditore = speditoreOrg.getName();
// 				}
// 				if( speditoreOrg.getAccountNumber() != null && !speditoreOrg.getAccountNumber().equals("") ){
// 					cod_speditore = speditoreOrg.getAccountNumber();
// 				}
// 				if(speditoreOrg.getAddressList().size() > 0){
// 					speditoreAddress = (OrganizationAddress) speditoreOrg.getAddressList().get(0);
// 				}
// 			}
// 			if(speditoreAddress != null){
// 				if( speditoreAddress.getCity() != null && !speditoreAddress.getCity().equals("") ){
// 					comune = speditoreAddress.getCity();
// 				}
// 				if( speditoreAddress.getState() != null && !speditoreAddress.getState().equals("") ){
// 					loc = speditoreAddress.getState();
// 				}
// 			}
				%>

<table>
<col width="20%">
<tr> <td><b><u>Allevamento: </u></b>
<td> <b>Codice identificazione azienda (DPR 317/96)</b> <%=cod_speditore %></td> </tr>
<tr> <td><b><u></u></b>
<td> <b>Denominazione azienda</b>  <%=speditore %></td></tr>
<tr> <td><b><u></u></b>
<td> <b>Proprietario</b> <%="N.D." %></td> </tr>
<tr> <td><b><u></u></b>
<td> <b>Via</b> <%=speditoreAddress %>  <b>N°</b> ............ </td> </tr>
<tr> <td><b><u></u></b>
<td> <b>Comune</b> <%=comune %> <b>Prov</b> <%=loc %></td> </tr>
<tr> <td><b><u></u></b>
<td> <b>A.S.L.</b> <%=""%></td> </tr>
</table>
<br/><br/>

 <center><b>Numero e tipo di animali esaminati</b></center>
<table cellpadding="5" style="border-collapse: collapse; float: left; width:50%" >
<col width="50%">
<% 
HashMap<Integer,Integer> listaCategorieBovine = mod.getHashCategorieBovine();
for(Map.Entry<Integer, Integer> elemento : listaCategorieBovine.entrySet()){
	%>
	<tr><td><%=categorieBovine.getSelectedValue(elemento.getKey()) %></td> <td>N. <%=elemento.getValue() %></td></tr>
<% } %>
</table>

<table cellpadding="5" style="border-collapse: collapse; float: left; width:50%" >
<col width="50%"> 
<% 
HashMap<Integer,Integer> listaCategorieBufaline = mod.getHashCategorieBufaline();
for(Map.Entry<Integer, Integer> elemento : listaCategorieBufaline.entrySet()){
	%>
	<tr><td><%=categorieBufaline.getSelectedValue(elemento.getKey()) %></td> <td>N. <%=elemento.getValue() %></td></tr>
<% } %>
</table>

 
 <center><b>REPERTO ISPETTIVO</b></center>
Descrizione delle lesioni anatomo-patologiche riscontrate:<br/>
__________________________________________________________________________________________________<br/><br/>
__________________________________________________________________________________________________<br/><br/>
__________________________________________________________________________________________________<br/>
(1) dallo svezzamento al primo intervento fecondativo<br/><br/>

<table border="1px"><tr><th>Contrassegno <br/>Identificazione <br/>marca auricolare)<br/>data di nascita</th>
<th>CATEGORIA</th>
<th colspan="3">Organi prelevati</th></tr>
 <% 
 ArrayList<Capo> listaCapi = null;
listaCapi = mod.getListaCapi();
int i = 0;
int sizeLista = listaCapi.size();
for(Capo capo : listaCapi){
	%>
	<tr>
<td align="center"><%=capo.getCd_matricola() %><br/><%=toDateasString(capo.getCd_data_nascita()) %></td>
<td>
<%for(Map.Entry<Integer, Integer> elemento : listaCategorieBovine.entrySet()){
	%>
	[ <%if (capo.getCd_categoria_bovina()>0 && capo.getCd_categoria_bovina()==elemento.getKey()){ %>X<%} %> ] <%=categorieBovine.getSelectedValue(elemento.getKey()) %><br/>
<% } %>
<%for(Map.Entry<Integer, Integer> elemento : listaCategorieBufaline.entrySet()){
	%>
	[ <%if (capo.getCd_categoria_bufalina()>0 && capo.getCd_categoria_bufalina()==elemento.getKey()){ %>X<%} %> ] <%=categorieBufaline.getSelectedValue(elemento.getKey()) %><br/>
<% } %>
<td>[ ] Ln.sopramammari (F)<br/>
[ ] Ln. Inguinali superficiali (M)<br/>
[ ] Ln. mandibolari<br/>
[ ] Ln. retrofaringei<br/>
[ ] Ln. Iliaci interni </td>
<td>[ ] milza<br/>
[ ]utero gravido<br/>
[ ]placenta<br/>
[ ]mammella<br/>
[ ]vescicole<br/>
seminali e<br/>
testicoli </td>
<td> [ ] altro </td>
</tr>
<% }%>


<%for (int j=i;j<10;j++) {%>
<tr>
<td>&nbsp;</td>
<td><%for(Map.Entry<Integer, Integer> elemento : listaCategorieBovine.entrySet()){
	%>
	[ ] <%=categorieBovine.getSelectedValue(elemento.getKey()) %><br/>
<% } %>
<%for(Map.Entry<Integer, Integer> elemento : listaCategorieBufaline.entrySet()){
	%>
	[ ] <%=categorieBufaline.getSelectedValue(elemento.getKey()) %><br/>
<% } %></td>
<td>[ ] Ln.sopramammari (F)<br/>
[ ] Ln. Inguinali superficiali (M)<br/>
[ ] Ln. mandibolari<br/>
[ ] Ln. retrofaringei<br/>
[ ] Ln. Iliaci interni </td>
<td>[ ] milza<br/>
[ ]utero gravido<br/>
[ ]placenta<br/>
[ ]mammella<br/>
[ ]vescicole<br/>
seminali e<br/>
testicoli </td>
<td> [ ] altro </td>
</tr>

<%} %>
</table>

<center><b>SPAZIO RISERVATO ALLA SEZIONE DIAGNOSTICA -I.Z.S.</b></center>
<center>Numero di registro sezione: ...................................</center>
<table>
<tr><td>Contrassegno <br/>Identificazione<br/>(marca auricolare)</td>
<td>Esami di laboratorio<br/>effettuati</td>
<td>Osservazioni</td>
</tr>
<%for (int j=0;j<10;j++) {%>
<tr><td>........................................................................</td>
<td>............................................................................</td>
<td>............................................................................</td>
</tr>
<% } %>
</table>
<br/><br/>
<div align="left">DATA DI INVIO ALL'ISTITUTO ZOOPROFILATTICO .......................................... </div>
<div align="right">I VETERINARI UFFICIALI<br/>
_________________________________________________________<br/><br/>
_________________________________________________________<br/><br/>
_________________________________________________________<br/></div>
	</div>
	
	
	
</body> --%>
</html>