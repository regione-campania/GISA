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
<jsp:useBean id="listaOrgani" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="listaStadi" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="listaRazze" class="org.aspcfs.utils.web.LookupList" scope="request"/>

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
 ArrayList<Capo> lista = null;
 lista = mod.getListaCapi_1033_tbc();
 int i = 0;
 String specie ="";
	String razza="";
	String sesso = "";
	int sizeLista = lista.size();
	
	 for(Capo capo : lista){
		  ModelloGenerico listaModelliElemento = (ModelloGenerico) listaModelli.get(i);
		 %> 
		 <!--  QUA DENTRO VA MESSO IL SINGOLO DOCUMENTO  -->
		 <!--  capo CONTIENE IL CAPO ATTUALE -->
		 <!--  listaModelliElemento CONTIENE IL MODELLO ATTUALE (PER PROGRESSIVO ETC) -->
		<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div> 


<table border="1px">
<tr><td style="border:0px">REGIONE CAMPANIA</td>
<td style="border:0px"></td>
<td style="border:0px"><u>MOD. 10/33</u></td></tr>

<tr><td style="border:0px">AZIENDA SANITARIA LOCALE<br/> SERVIZIO VETERINARIO<br/><%=SiteList.getSelectedValue(mod.getAslMacello()) %><br/>
Recapiti...............................................</td>
<td style="border:0px"></td>
<td style="border:0px">Al Dipartimento di Prevenzione<br/>
Servizio Veterinario<br/>
dell'A.S.L. .........................................<br/>
Fax: ..........................................<br/>
mail: ..........................................<br/></td></tr>
</table>
<br/>		 
		 
<table width="100%">
<col width="33%"><col width="33%"><col width="20%">
<tr>
<td class="piccolo">Prot.  <%=listaModelliElemento.getProgressivo() + "/" + SiteList.getSelectedValue(mod.getAslMacello()).toUpperCase() + "/" + mod.getAnnoDataModulo()%>
	<% if(listaModelliElemento.getOldProgressivo() > 0){%>
				<br/> (sostituisce il doc. con prot.  <%=listaModelliElemento.getOldProgressivo() + "/" + SiteList.getSelectedValue(mod.getAslMacello()).toUpperCase() + "/" + mod.getAnnoDataModulo()%> )
				<%} %> 
 </td>
<td> </td>
<td align="right">Al Settore Veterinario Assessorato alla<br/>
Sanità<br/>
della Regione Campania<br/>
FAX: 0817865267</td>
</tr>
</table>	
	 
<br/><br/>
Data .......................................................... 
<br/><br/>	 

<center><b>Oggetto: BONIFICA SANITARIA DEGLI ALLEVAMENTI DALLA TUBERCOLOSI<br/>
Comunicazione capo infetto</b><br/><br/>
Oggetto: Ai sensi della legge 9/6/1964 N. 615 del D.M. 1/6/1968 e successive modifiche ed<br/>
integrazioni si comunica che nel capo di :
</center>
<br/><br/>

<%specie = listaSpecie.getValueFromId(capo.getCd_specie());
specie = (specie != null && !specie.equals("") ? specie : "...........................");
razza = listaRazze.getValueFromId(capo.getCd_id_razza());
razza = (razza != null && !razza.equals("") ? razza : "...........................");
sesso = capo.isCd_maschio() ? "M" : "F";
	%>	
		 
<table style="float:left; width:200px" border="1px"> 
<tr><td><b>SPECIE</b></td> <td> <%=specie %></td></tr>
<tr><td><b>RAZZA</b></td> <td><%=razza %></td></tr>
<tr><td><b>SESSO</b></td> <td> [ <%if(capo.isCd_maschio()){ %>X<%} %> ] M  [ <%if(!capo.isCd_maschio()){ %>X<%} %> ] F</td></tr>
</table>

<table style="float:left; width:200px" border="1px"> 
<tr><td colspan="2"><b>ETA'</b></td></tr>
<tr><td>Superiore a due anni</td> <td>Inferiore a due anni</td></tr>
<tr><td>[ <%if(capo.getVpm_data()!=null && capo.getCd_data_nascita()!=null &&  capo.getVpm_data().getTime() - capo.getCd_data_nascita().getTime() > 2*365*24*60*60*1000L ){%>X<%} %> ]</td> 
<td>[ <%if(capo.getVpm_data()!=null && capo.getCd_data_nascita()!=null &&  capo.getVpm_data().getTime() - capo.getCd_data_nascita().getTime() <= 2*365*24*60*60*1000L ){%>X<%} %> ]</td></tr>
</table> 
<br/><br/><br/><br/><br/><br/>

<table style="float:left; width:200px" border="1px"> 
<tr><td><b>Contrassegno</b></td> <td> <%=capo.getCd_matricola() %></td></tr>
</table>

<table style="float:left; width:200px" border="1px"> 
<tr><td><b>Macellato il</b> </td> <td><%=toDateasString(mod.getData()) %></td></tr>
</table> 
<br/><BR/>
<table border="1px">
<tr><td style="border:0px">Presso: macello <%=nomeMacello %></td></tr>
<tr><td style="border:0px">sito nel comune di: <%=comuneMacello %></td></tr>
</table>
	 
<br/><br/>
<table border="1px" width="100%">
<tr><td style="border:0px">Sono state rilevate alla visita veterinaria ispettiva post mortem le seguenti lesioni tubercolari:<br/><br/></td></tr>
<tr><td style="border:0px">
 <%
 
 TreeMap<Integer, ArrayList<Organi>> hashOrgani = null;
 hashOrgani = mod.getHashCapiOrgani();
 int k = 1;
 String organi="";
 String stadi="";
 for(Organi o : hashOrgani.get(capo.getId())){
 		organi = organi + " "+k+"." + listaOrgani.getSelectedValue(o.getLcso_organo());
		stadi = stadi + " "+k+"." + listaStadi.getSelectedValue(o.getLcso_patologia());
		k++;
 }
%>
<%=organi %>
<br/><br/><br/><br/><br/><br/><br/><br/>
</td></tr>
<tr><td style="border:0px">
<b>STADIO E CARATTERI MORFOSTRUTTURALI DELLA LESIONE</b><br/><br/>
<%=stadi %>
<br/><br/><br/><br/><br/><br/><br/><br/>
</td></tr></table>
<br/>

  <div style="page-break-before:always">&nbsp; </div>  
  
<table border="1px" width="100%">
<tr><td style="border:0px">
<b>ALLEGARE COPIA DEL CERTIFICATO DI ORIGINE</b><br/><br/>
Altre informazioni :<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
</td></tr></table>

<div align="left">Il veterinario ispettore<br/>
<%=capo.getCd_veterinario_1() %><br/>
<%=capo.getCd_veterinario_2() %><br/>
<%=capo.getCd_veterinario_3() %><br/>
</div> 
<div align="right">Il Direttore del Servizio Veterinario</div>
<br><br><br>
<%@ include file="/gestione_documenti/gdpr_footer.jsp" %>
<br/>
		 
<%i++; 
			if (i!=sizeLista) { %>
			<div style="page-break-before:always">&nbsp; </div>  
		<% }  }%>
	
	
	
</body>
</html>