<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<%@page import="org.aspcfs.modules.imprese_pregresso.base.BImpresePregresso"%>

<form action="ImpresePregresso.do?command=TrasformaOsa" method="post">
<%-- Trails --%>
<%
	BImpresePregresso cc = (BImpresePregresso)request.getAttribute( "dettaglio" );
%>

<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="ImpresePregresso.do">
			<dhv:label name="imprese_pregresso">
			
				Imprese Pregresso
				
								
			</dhv:label></a> > 
			<dhv:label name="imprese_pregresso.cerca">Scheda Attivit&agrave;</dhv:label>
		</td>
	</tr>
</table>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        
        <tr>
          <th colspan="2">
            <strong><dhv:label name="imprese_pregresso.filtri"><%=toHtml( cc.getRagione_sociale()+" "+cc.getDenominazione() ) %>
            <% SimpleDateFormat dataPC = new SimpleDateFormat("dd/MM/yyyy");
       %>
            <%=
					(cc.getData_import() == null) ? 
							("") : 
								( " (Attivit&agrave; importata in data: " + dataPC.format(cc.getData_import()) + ") " ) %>
             </dhv:label></strong>
          </th>
        </tr>
        <dhv:include name="accounts-sites" none="true">
  <dhv:evaluate if="<%= SiteList.size() > 1 %>">
  <%
  String codiceasl = cc.getCodice_istat_asl();
  int asl = -1;
  if(codiceasl.equals("101")){
		asl = 1;
						
	}else if(codiceasl.equals("102")){
		asl = 2;
						
	}else if(codiceasl.equals("103")){
		asl = 3;
					
	}else if(codiceasl.equals("104")){
		asl = 4;
		
	}
	else if(codiceasl.equals("105")){
		asl = 5;
		
	}
	else if(codiceasl.equals("106")){
		asl = 6;
		
	}
	else if(codiceasl.equals("107")){
		asl = 7;
		
	}
	else if(codiceasl.equals("108")){
		asl = 8;
		
	}
	else if(codiceasl.equals("109")){
		asl = 9;
		
	}
	else if(codiceasl.equals("110")){
		asl = 10;
		
	}else if(codiceasl.equals("111")){
		asl = 11;
		
	}
	else if(codiceasl.equals("112")){
		asl = 12;
		
	}
	else if(codiceasl.equals("113")){
		asl = 13;
		
	}
  %>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.site">Site</dhv:label>
    </td>
    <td>
      <%= SiteList.getSelectedValue(asl) %>
      <input type="hidden" name="siteId" value="<%=asl%>" >
    </td>
  </tr>
  </dhv:evaluate> 
  <dhv:evaluate if="<%= SiteList.size() <= 1 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
  </dhv:evaluate>
</dhv:include>
        <tr>
          <td class="formLabel">
            <dhv:label name="">Impresa</dhv:label>
          </td>
          <td>
            <%=toHtml( cc.getDenominazione() ) %>&nbsp;
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="imprese_pregresso.partita_iva">Partita IVA</dhv:label>
          </td>
          <td>
            <%=toHtml( cc.getPartita_iva() ) %>&nbsp;
          </td>
        </tr>
        
        <tr>
          <td class="formLabel">
            <dhv:label name="imprese_pregresso.codice_fiscale">Codice Fiscale</dhv:label>
          </td>
          <td>
            <%=toHtml( cc.getCodice_fiscale() ) %>&nbsp;
          </td>
        </tr>
        
        <tr>
          <td class="formLabel">
            <dhv:label name="imprese_pregresso.cod_natura_giuridica">Codice ISTAT</dhv:label>
          </td>
          <td>
            <%=toHtml( cc.getCodice_ateco() ) %>&nbsp;
          </td>
        </tr> 
            
        <tr>
          <td class="formLabel">
            <dhv:label name="imprese_pregresso.cod_attivita_istat_91_primario">Descrizione Attività</dhv:label>
          </td>
          <td>
            <%=toHtml( cc.getTipologia_attivita() ) %>&nbsp;
          </td>
        </tr> 
        
        <tr>
          <td class="formLabel">
            <dhv:label name="imprese_pregresso.oggetto_sociale">Titolare o Legale Rappresentante</dhv:label>
          </td>
          <td>
            <%=toHtml( cc.getRappresentante_legale() ) %>&nbsp;
          </td>
        </tr> 
        </table>
        <br>
        <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong>Sede Legale</strong>
          </th>
        </tr>
        
        <tr>
          <td class="formLabel">
            <dhv:label name="">Comune</dhv:label>
          </td>
          <td>
            <%=toHtml( cc.getComune_sede_legale() ) %>
          </td>
        </tr>
        
        <tr>
          <td class="formLabel">
            <dhv:label name="">Indirizzo</dhv:label>
          </td>
          <td>
            <%=toHtml( cc.getVia_sede_legale() ) %>
          </td>
        </tr>
        </table>
           
         <br>
        <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong><dhv:label name="">Sede Operativa</dhv:label></strong>
          </th>
        </tr>
        
        <tr>
          <td class="formLabel">
            <dhv:label name="">Comune</dhv:label>
          </td>
          <td>
            <%=toHtml( cc.getComune_sede_legale() ) %>
          </td>
        </tr>
        
        <tr>
          <td class="formLabel">
            <dhv:label name="">Indirizzo</dhv:label>
          </td>
          <td>
            <%=toHtml( cc.getIndirizzo_sede_operativa() ) %>
          </td>
        </tr>
        </table>
 
	<input type="hidden" name="id" value="<%=cc.getId() %>" />
	<% if( cc.getData_importazione_osa() == null ){ %>
	<dhv:permission name="accounts-impresepregresso-edit">
		<dhv:permission name="accounts-accounts-add">
			<input type="submit" value="<dhv:label name="button.importa">Importa</dhv:label>" />
		</dhv:permission>
	</dhv:permission>
	<%} %>
</form>

