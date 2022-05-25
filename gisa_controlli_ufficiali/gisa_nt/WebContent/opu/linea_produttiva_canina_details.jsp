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
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - ANY DAMAGES, INCLUDIFNG ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: accounts_details.jsp 19045 2007-02-07 18:06:22Z matt $
  - Description: 
  --%> 
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*, org.aspcfs.modules.base.Constants" %>
<%@page import="com.darkhorseventures.framework.actions.ActionContext"%>
<%@page import="java.sql.*"%>
<%@page import="org.aspcfs.modules.opu.base.ComuniAnagrafica"%>

<%@page import="org.aspcfs.modules.system.base.SiteList"%>

<jsp:useBean id="OperatoreDettagli" class="org.aspcfs.modules.opu.base.Operatore" scope="request"/>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<%@ include file="../initPage.jsp" %>
 		
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>

<table class="trails" cellspacing="0">
<tr>
<td>
<a href="OperatoreAction.do"><dhv:label name="">Operatore</dhv:label></a> > 
<% if (request.getParameter("return") == null) { %>
<a href="OperatoreAction.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<a href="OperatoreAction.do?command=Dashboard">Cruscotto</a> >
<%}%>
<dhv:label name="">Dettagli operatore</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>


<%
Stabilimento stabilimentoDellaLineaProduttiva = (Stabilimento) OperatoreDettagli.getListaStabilimenti().get(0);   //Informazioni principalmente sulla sede
SoggettoFisico rappLegale =  null ;
if(OperatoreDettagli != null ) { rappLegale = OperatoreDettagli.getRappLegale(); }	 // Inforomazioni sul rappresentante Legale
LineaProduttiva lineaProduttivaProprietariaODetentrice =  (LineaProduttiva) stabilimentoDellaLineaProduttiva.getListaLineeProduttive().get(0);	
%>

<% String param1 = "idLinea=" + lineaProduttivaProprietariaODetentrice.getId();
   %>
<dhv:container name="anagrafica" selected="details"  object="OperatoreDettagli" param="<%= param1 %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Informazioni Proprietario/Detentore</strong>
    </th>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Asl di Riferimento
    </td>
    <td>
      <%= AslList.getSelectedValue(stabilimentoDellaLineaProduttiva.getIdAsl()) %>    
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Tipo(i)
    </td>
    <td>
       <%= lineaProduttivaProprietariaODetentrice.getAttivita() %>
    </td>
  </tr>

 	<tr class="containerBody">
		<td nowrap class="formLabel">
      		Nome
		</td>
		<td>
		
        	<%=OperatoreDettagli.getRagioneSociale() %>
        	
		</td>
	</tr>
  
  
  
  <% 
  String label = "";
  String value = "";
  if (OperatoreDettagli.getPartitaIva() != null && !("").equals(OperatoreDettagli.getPartitaIva()) ) {
  	 	label = "Partita iva";
  		value = OperatoreDettagli.getPartitaIva();
  }else if (OperatoreDettagli.getCodFiscale() != null && !("").equals(OperatoreDettagli.getCodFiscale())) { 
	  	 label = "Codice fiscale";
	  	 value = OperatoreDettagli.getCodFiscale();
  }else{
	  label = "Partita iva";
	  value = "N.D";
  }
  %>
  
    <tr class="containerBody">
			<td nowrap class="formLabel">
      <%=label %>
			</td>
			<td>

        <%=value%>

			</td>
		</tr>
  
  
    <tr class="containerBody">
		<td nowrap class="formLabel">
    	  Autorizzazione
		</td>
		<td>
		<%if (lineaProduttivaProprietariaODetentrice.getAutorizzazione() != null){ %>
        	<%=lineaProduttivaProprietariaODetentrice.getAutorizzazione() %>
        <%} %>
		</td>
	</tr>
	
	  <tr class="containerBody">
		<td nowrap class="formLabel">
    	  Sesso
		</td>
		<td>
			<%=(rappLegale!=null) ? rappLegale.getSesso() : "N.D" %>
		</td>
	</tr>
	
	<tr class="containerBody">
		<td nowrap class="formLabel">
    	  Comune Nascita
		</td>
		<td>
			<%=(rappLegale!=null  ) ?  toHtml(rappLegale.getComuneNascita()) : "N.D" %>
		</td>
	</tr>
	
	<tr class="containerBody">
		<td nowrap class="formLabel">
    	  Data Nascita
		</td>
		<td>
			<%=(rappLegale!=null  ) ?  toDateasString(rappLegale.getDataNascita()) : "N.D" %>
		</td>
	</tr>
	
	<!-- tr class="containerBody">
		<td nowrap class="formLabel">
    	  Documento Identita
		</td>
		<td>
			<!--%=(rappLegale!=null  ) ?  toHtml(rappLegale.get) : "N.D" %-->
		<!-- /td>
	</tr -->
	

 </table>
 
 </br></br>
 
 <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>Indirizzi</strong>
	  </th>
  </tr>

    
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
        Residenza
      </td>
      <td>
       <%=stabilimentoDellaLineaProduttiva.getSedeOperativa().getVia() %> <br>
       <%=ComuniList.getSelectedValue( stabilimentoDellaLineaProduttiva.getSedeOperativa().getComune()) %> - <%=stabilimentoDellaLineaProduttiva.getSedeOperativa().getProvincia() %>
       <br>( <%=stabilimentoDellaLineaProduttiva.getSedeOperativa().getNazione() %> )<br>
       <% if (stabilimentoDellaLineaProduttiva.getSedeOperativa().getLatitudine() != 0.0){ %>
       		<%=stabilimentoDellaLineaProduttiva.getSedeOperativa().getLatitudine() %>
       <%} %>
       <% if (stabilimentoDellaLineaProduttiva.getSedeOperativa().getLongitudine() != 0.0 ){ %>
       		<%=stabilimentoDellaLineaProduttiva.getSedeOperativa().getLongitudine() %>
       <%} %>
      </td>
    </tr>
    
     <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
        Telefono
      </td>
      <td>
       <%if (rappLegale != null )
       {%>
       <%=rappLegale.getTelefono1() + " --- " + rappLegale.getTelefono2() %>
      <%  }
    	   %> <br>
       
      </td>
    </tr>
    
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
        E mail
      </td>
      <td>
       <%if (rappLegale != null )
       {%>
    	   <%=rappLegale.getEmail() %>
      <% }
    	   %> <br>
       
      </td>
    </tr>
    




</table>
<br />


<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Informazioni sul record</strong>
    </th>     
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Inserito
    </td>
    <td>
    
    <!-- Ha senso l'entered dell'operatore o è necessario definire entered modified enteredby modifiedby sulla linea produttiva -->
       <dhv:username id="<%=OperatoreDettagli.getEnteredBy()%>" />
      <%=toHtmlValue(toDateasString(OperatoreDettagli.getEntered()))%>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Modificato
    </td>
    <td>
       <dhv:username id="<%=OperatoreDettagli.getModifiedBy()%>" />
      <%=toHtmlValue(toDateasString(OperatoreDettagli.getModified()))%>
    </td>
  </tr>
</table>
<br>
</dhv:container>