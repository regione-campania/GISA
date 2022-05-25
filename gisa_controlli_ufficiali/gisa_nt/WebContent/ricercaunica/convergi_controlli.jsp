<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: accounts_list.jsp 18543 2007-01-17 02:55:07Z matt $
  - Description:
  --%>
<%@page import="org.aspcf.modules.controlliufficiali.base.Ticket"%>
<%@page import="org.aspcfs.modules.lineeattivita.base.LineeAttivita"%>
<%@page import="com.itextpdf.text.log.SysoLogger"%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>
<%@page import="org.aspcfs.modules.opu.base.Stabilimento"%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.ricercaunica.base.*, org.aspcfs.modules.base.*" %>

<jsp:useBean id="SearchOpuListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="tipoOperatore" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="AnagraficaPartenza" class="org.aspcfs.modules.ricercaunica.base.RicercaOpu" scope = "request"/>
<jsp:useBean id="AnagraficaDestinazione" class="org.aspcfs.modules.ricercaunica.base.RicercaOpu" scope = "request"/>



<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>

<script>



</script>



<%
Ticket urlAnagrafica = new Ticket();
urlAnagrafica.setTipologia_operatore(AnagraficaDestinazione.getTipologia());
String action = urlAnagrafica.getURlDettaglioanagrafica()+".do?command=Details&"+AnagraficaDestinazione.getRiferimentoIdNome()+"="+AnagraficaDestinazione.getRiferimentoId();
%>

<form method="post" action="RicercaUnica.do?command=CovergenziAnagrafiche" id = "convergenza">
			<input type = "hidden" name = "rifIdOrigine" value = "<%=AnagraficaPartenza.getRiferimentoId()%>">
			<input type = "hidden" name = "rifIdDestinazione" value = "<%=AnagraficaDestinazione.getRiferimentoId()%>">
			<input type = "hidden" name = "rifNomeColonnaOrigine" value = "<%=AnagraficaPartenza.getRiferimentoIdNomeCol()%>">
			<input type = "hidden" name = "rifNomeColonnaDestinazione" value = "<%=AnagraficaDestinazione.getRiferimentoIdNomeCol()%>">

<input type ="hidden" name = "tipoAttivita" id = "tipoAttivita" value = "<%=AnagraficaPartenza.getId_tipo_attivita() %>">
<br />
<table cellpadding="8" cellspacing="0" border="0" width="100%" class="pagedList">
 <tr>
   <th colspan="4">
          <strong><dhv:label name="">CONVERGENZA ANAGRAFICHE</dhv:label></strong>
        </th>  
        </tr>
        
        <tr>
        <th>
          &nbsp;
        </th>
         <th>
         ANAGRAFICA ORIGINE
        </th>
         <th>
         ANAGRAFICA DESTINAZIONE
        </th>  
         <th>
         RISULTATO CONVERGENZA
        </th>   
        </tr>
        
        
         <TR>
        <td class="formLabel">Ragione Sociale</td>
        <td><%=AnagraficaPartenza.getRagioneSociale() %></td>
         <td><%=AnagraficaDestinazione.getRagioneSociale() %></td>
           <td><%=AnagraficaDestinazione.getRagioneSociale() %></td>
        </TR>
         <TR>
        <td class="formLabel">Asl</td>
        <td><%=AnagraficaPartenza.getAsl() %></td>
         <td><%=AnagraficaDestinazione.getAsl() %></td>
                <td><%=AnagraficaDestinazione.getAsl() %></td>
        </TR>
          <TR>
        <td class="formLabel">Partita Iva</td>
        <td><%=AnagraficaPartenza.getPartitaIva() %></td>
         <td><%=AnagraficaDestinazione.getPartitaIva() %></td>
          <td><%=AnagraficaDestinazione.getPartitaIva() %></td>
        </TR>
        <TR>
        <td class="formLabel">Numero Registrazione</td>
        <td><%=AnagraficaPartenza.getNumeroRegistrazione() %></td>
         <td><%=AnagraficaDestinazione.getNumeroRegistrazione() %></td>
          <td><%=AnagraficaDestinazione.getNumeroRegistrazione() %></td>
        </TR>
        <TR>
        <td class="formLabel">Stato</td>
        <td><%=AnagraficaPartenza.getStato() %></td>
        <td><%=AnagraficaDestinazione.getStato() %></td>
         <td><%=AnagraficaDestinazione.getStato() %></td>
        </TR>
        <TR>
        <td class="formLabel">Indirizzo</td>
        <td><%=AnagraficaPartenza.getIndirizzoSedeProduttiva() %></td>
        <td><%=AnagraficaDestinazione.getIndirizzoSedeProduttiva() %></td>
         <td><%=AnagraficaDestinazione.getIndirizzoSedeProduttiva() %></td>
        </TR>
          <TR>
        <td class="formLabel">Numero Controlli</td>
        <td><%=AnagraficaPartenza.getNumeroControlli() %></td>
        <td><%=AnagraficaDestinazione.getNumeroControlli() %></td>
          <td><%= ( AnagraficaPartenza.getNumeroControlli()+AnagraficaDestinazione.getNumeroControlli()) %></td>
        </TR>
        <TR>
        <td class="formLabel">Categoria di Rischio</td>
        <td>
        <%=AnagraficaPartenza.getCategoriaRischio()  + ((AnagraficaPartenza.getDataProssimoControllo()!=null)? "PROSSIMA SORVEGLIANZA"+ toDateasString(AnagraficaPartenza.getDataProssimoControllo()) :"") %>
        </td>
         <td>
        <%=AnagraficaDestinazione.getCategoriaRischio()  + ((AnagraficaDestinazione.getDataProssimoControllo()!=null)? "PROSSIMA SORVEGLIANZA"+ toDateasString(AnagraficaDestinazione.getDataProssimoControllo()) :"") %>
        </td>
        
         <td>
         <%
         if((AnagraficaDestinazione.getCategoriaRischio()>0) && AnagraficaDestinazione.getCategoriaRischio()<AnagraficaPartenza.getCategoriaRischio())
         {
         %>
        <%=AnagraficaPartenza.getCategoriaRischio()  + ((AnagraficaPartenza.getDataProssimoControllo()!=null)? "PROSSIMA SORVEGLIANZA"+ toDateasString(AnagraficaDestinazione.getDataProssimoControllo()) :"") %>
        <%}
         else
         {
        	 %>
        	         <%=AnagraficaDestinazione.getCategoriaRischio()  + ((AnagraficaDestinazione.getDataProssimoControllo()!=null)? "PROSSIMA SORVEGLIANZA"+ toDateasString(AnagraficaPartenza.getDataProssimoControllo()) :"") %>
        	 
        	 <%
        	 
         }
        	 
         %>
        </td>
        </TR>
       
       <TR>
        <td class="formLabel">Ultimo Controllo in Sorveglianza</td>
        <td>
        
        <%= toDateasString(AnagraficaPartenza.getDataUltimoControlloSorveglianza()) %>
        </td>
        <td>
        
        <%= toDateasString(AnagraficaDestinazione.getDataUltimoControlloSorveglianza()) %>
        </td>
         <td>
        
        <%= toDateasString(AnagraficaDestinazione.getDataUltimoControlloSorveglianza()) %>
        </td>
        
        </TR>
       
        <%
        if( AnagraficaPartenza.getListaControlliPerLinea().size()>0)
        {
        %>
        <TR style="display: none">
        <td class="formLabel">Numero Controlli Per ogni Linea</td>
        <td><%
       			for(LineaProduttiva linea1: AnagraficaPartenza.getListaControlliPerLinea())
       			{
       				out.print("Linea : "+linea1.getDescrizione_linea_attivita()+ " Num Cu : "+linea1.getNumeroControlliUfficialiEseguiti()+"<br>");
       			}
        
        %></td>
        </TR>
        <%} %>
       
         
        </table>
        
        <br>
        
        <table cellpadding="8" cellspacing="0" border="0" width="100%" class="pagedList">
 <tr>
   <th colspan="3">
          <strong><dhv:label name="">ATTIVITA SVOLTE IN ANAGRAFICA DI ORIGINE</dhv:label></strong>
        </th>  
        </tr>
          <tr>
        
         <th>
         DESCRIZIONE LINEA
        </th>
         <%
        if(AnagraficaPartenza.getTipologia()!=999 && AnagraficaDestinazione.getTipologia()==999)
        {
        	%>
        	<th>
        	TRADUCI IN MASTER LIST
        	</th>
        	
        	<%
        	
        }
        %>
        
        <th>
         NUM. CU
        </th>
        
        </tr>
        
        <%
        ArrayList<LineaProduttiva> listaLineeOrigine = AnagraficaPartenza.getListaControlliPerLinea();
        for(LineaProduttiva lpOrigine : listaLineeOrigine)
        {
        	%>
        	<tr>
        	
        	<td><%=lpOrigine.getDescrizione_linea_attivita() %>
        	
        	<input type ="hidden" name = "lineaOrigine" value = "<%=lpOrigine.getId_rel_stab_lp()%>">
        	</td>
        	  <%
        if(AnagraficaPartenza.getTipologia()!=999 && AnagraficaDestinazione.getTipologia()==999)
        {
        %>
        
        	<td>
        	
        	<%
        	ArrayList<LineeAttivita> listaCandiati = AnagraficaPartenza.getListaCandatiPerLineaVecchiaAnagrafica().get(lpOrigine.getId_rel_stab_lp());
        	if(listaCandiati!=null&& listaCandiati.size()>0)
        	{
        		%>
        		<table id = "attprincipale_ranked<%=lpOrigine.getId_rel_stab_lp() %>" class="noborder" cellspacing = "10" style ="width:100%">
		
        		<%
        	for(LineeAttivita laCandiata : listaCandiati)
        	{
        		
        		%>
        		
    
	<tr class="trEntryCandidato" id="trPerEntryCandidato" style="opacity:0.5" >
		<td><b>MACROAREA</b>: <%=laCandiata.getMacroarea() %></td>
		<td><b>AGGREGAZIONE</b>: <%=laCandiata.getAggregazione() %></td>
	<td><b>ATTIVITA</b>: <%=laCandiata.getAttivita() %></td>
		<td>&nbsp;</td>
		<td><input id="<%=lpOrigine.getId_rel_stab_lp()+"-"+laCandiata.getIdAttivita() %>" type="radio" name="candidato-<%=laCandiata.getIdLineaVecchiaOriginale() %>" value ="<%=laCandiata.getIdAttivita() %>" 
		onclick="candidatoScelto('attprincipale_ranked<%=lpOrigine.getId_rel_stab_lp() %>',<%=laCandiata.getIdLineaVecchiaOriginale() %>,<%=laCandiata.getIdAttivita() %>,1,'candidato-<%=laCandiata.getIdLineaVecchiaOriginale() %>')"/></td>
	
	
	</tr>
	<tr><td>&nbsp;</td></tr>
							<tr name="tr_path_aggiuntivo" ></tr>
							<tr name="tr_select_aggiuntivi" ></tr>
							<tr><td>&nbsp;</td></tr>
	
        		
        		
        		<%
        	}%>
        	</table>
        	<%
        	}
        	else
        	{
        		%>
        		<font color="red">ASSOCIAZIONE NON TROVATA</font>
        		<%
        	}
        	%>
        	
        	
        	</td>
        
        <%} %>
        	
        	<td><%=lpOrigine.getNumeroControlliUfficialiEseguiti() %></td>
        	
        	</tr>
        	<%
        	
        }
        
        %>
        
        </table>
        
        <table cellpadding="8" cellspacing="0" border="0" width="100%" class="pagedList">
 <tr>
   <th colspan="3">
          <strong><dhv:label name="">ATTIVITA SVOLTE IN ANAGRAFICA DI DESTINAZIONE</dhv:label></strong>
        </th>  
        </tr>
          <tr>
        
         <th>
         DESCRIZIONE LINEA
        </th>
        
        <th>
         NUM. CU
        </th>
        
        </tr>
        
        <%
        listaLineeOrigine = AnagraficaDestinazione.getListaControlliPerLinea();
        for(LineaProduttiva lpOrigine : listaLineeOrigine)
        {
        	%>
        	<tr>
        	
        	<td><%=lpOrigine.getDescrizione_linea_attivita() %></td>
        	
        	<td><%=lpOrigine.getNumeroControlliUfficialiEseguiti() %></td>
        	
        	</tr>
        	<%
        	
        }
        
        %>
        
        </table>
    
     
<input type = "button" value="CONVERGI" onclick="eseguiConvergenzaAnagrafiche('<%=action%>')">
<input type = "button" value="ESCI" onclick="$('#dialogRICERCA').dialog('close');">

<div id="LoadingImage" style="display: none">
	Attendere Operazione in corso ..
<img src="images/ajax-loader.gif" />
</div>
</form>
