<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.mu.base.PartitaUnivoca"%>
<%@page import="sun.reflect.generics.reflectiveObjects.NotImplementedException"%>
<%@page import="org.aspcfs.modules.mu.operazioni.base.MorteANM"%>
<%@page import="org.aspcfs.modules.mu.operazioni.base.VisitaPM"%>
<%@page import="org.aspcfs.modules.mu.operazioni.base.Macellazione"%>
<%@page import="org.aspcfs.modules.mu.base.CapoUnivoco"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="macello" class="org.aspcfs.modules.stabilimenti.base.Organization" scope="request"/>
<jsp:useBean id="reg" class="org.aspcfs.modules.macellazionidocumenti.base.RegistroMacellazioni" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="esitiPm" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="listaCapiSeduta" class="org.aspcfs.modules.mu.base.CapoUnivocoList" scope="request" />
<jsp:useBean id="statiCapo" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<%@page import="org.aspcfs.modules.accounts.base.OrganizationAddress"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../../initPage.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<style>
@page { size: landscape !important;
 margin-top: 1cm !important;
 margin-bottom: 2cm !important;
  @bottom-center {
    content: counter(page) " su " counter(pages) !important;
    }
      @bottom-right {
      content: "Firma del veterinario _______________________________"  !important;
      }
}


 </style> 
 

<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Scheda</title>
</head>
<link rel="stylesheet" type="text/css" media="screen" documentale_url="" href="macellazionidocumenti/css/macelli_screen.css" />
<link rel="stylesheet" type="text/css" media="print" documentale_url="" href="macellazionidocumenti/css/macelli_print.css" />

<body>

<div class="boxIdDocumento"></div>
<%-- <div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div> --%>

<br/><br/>

<table class="details" cellpadding="5" style="border-collapse: collapse;table-layout:fixed;" width="100%"> 


<thead>
<tr><td colspan="12"><div class="titolo2">REGISTRO MACELLAZIONI "<%=macello.getName() %>" del <%=toDateasString(reg.getData()) %></div></td></tr>
<tr>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Prg<br/>Bov</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Mod. 4</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Data<br/> arrivo</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Specie</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Cod. Allev.</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Matricola</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Data<br/> nascita</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Data<br/> Macellazione</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Esito<br/> Visita PM</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Destinatario<br/> Carni</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Dest.<br/> Carcassa</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Cat. rischio BSE</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Stato finale capo</th>
</tr>
</thead>
<tbody>
<%

Iterator i = listaCapiSeduta.iterator();
VisitaPM pm = null;
MorteANM mam = null;
while (i.hasNext()){
CapoUnivoco thisCapo = (CapoUnivoco) i.next();
				Macellazione dettaglioMacellazione = thisCapo.getDettagliMacellazione();
				
			
				try{
					pm = dettaglioMacellazione.getVisitaPm();
				}catch(NotImplementedException e){
					
				}
				
				try{
				mam = dettaglioMacellazione.getMorteAM();
				}catch(NotImplementedException e){
					
				}
%>
<tr class="row1">
<td style="text-overflow: ellipsis; overflow: hidden;" align="center"><%=(pm != null) ?  pm.getProgressivoMacellazionePm() : " --" %></td>
<td style="text-overflow: ellipsis; overflow: hidden;" align="center"><%=thisCapo.getPartita().getMod4() %></td>
<td style="text-overflow: ellipsis; overflow: hidden;" align="center"><%=toDateasString(thisCapo.getPartita().getDataArrivoMacello()) %></td>
<td style="text-overflow: ellipsis; overflow: hidden;" align="center"><%=thisCapo.getSpecieCapoNome() %></td>
<td style="text-overflow: ellipsis; overflow: hidden;" align="center"><%= thisCapo.getPartita().getCodiceAziendaProvenienza() %></td>
<td style="text-overflow: ellipsis; overflow: hidden;" align="center"><%= thisCapo.getMatricola() %></td>
<td style="text-overflow: ellipsis; overflow: hidden;" align="center"><%= toDateasString(thisCapo.getDataNascita()) %></td>
<td style="text-overflow: ellipsis; overflow: hidden;" align="center"><%=(pm != null && pm.getDataVisitaPm() != null) ?  toDateasString(pm.getDataVisitaPm()) : "" %></td>
<td style="text-overflow: ellipsis; overflow: hidden;" align="center"><%=(pm != null && pm.getIdEsitoPm() > 0) ? esitiPm.getSelectedValue(pm.getIdEsitoPm()) : "" %></td>
<td style="text-overflow: ellipsis; overflow: hidden;" align="center"><%="dest carni" %></td>
<td style="text-overflow: ellipsis; overflow: hidden;" align="center"><%=((mam != null) ?  mam.getDestinazioneCarcassaMorteAm() : "") %></td>
<td style="text-overflow: ellipsis; overflow: hidden;" align="center"><%=thisCapo.getCategoriaRischio() %></td>
<td style="text-overflow: ellipsis; overflow: hidden;" align="center"><%=statiCapo.getSelectedValue(thisCapo.getIdStato()) %></td>



</tr>
<% } %>
</tbody>
</table>

<!-- <br/><br/><br/><br/><br/> -->
<!--  <table> -->
<!--  <col width="33%"><col width="33%"> -->
<!--  <tr><td></td> -->
<!--  <td></td> -->
<!--  <td><i>Firma del veterinario</i> ___________________________________________</td> </tr> -->
<!-- </table> -->

    
  

</body>
</html>