<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../initPage.jsp" %>

<%@page import="org.aspcfs.modules.camera_commercio.base.BCameraCommercio"%>

<form action="CameraCommercio.do?command=TrasformaOsa" method="post">
<%-- Trails --%>
<%
	BCameraCommercio cc = (BCameraCommercio)request.getAttribute( "dettaglio" );
%>

<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="CameraCommercio.do">
			<dhv:label name="camera_commercio">
			
				Camera di Commercio 
				
								
			</dhv:label></a> > 
			<dhv:label name="camera_commercio.cerca">Scheda Attivit&agrave;</dhv:label>
		</td>
	</tr>
</table>

<table cellpadding="2" cellspacing="2" border="0" width="100%">
  <tr>
    <td width="50%" valign="top">

      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        
        <tr>
          <th colspan="2">
            <strong><dhv:label name="camera_commercio.filtri"><%=toHtml( cc.getRagione_sociale() ) %>
            <%=
					(cc.getData_importazione_osa() == null) ? 
							("") : 
								( " (Attivit&agrave; importata in data: " + cc.getData_importazione_osa() + ") " ) %>
             </dhv:label></strong>
          </th>
        </tr>
        
        <tr>
          <td class="formLabel">
            <dhv:label name="camera_commercio.partita_iva">Partita IVA</dhv:label>
          </td>
          <td>
            <%=toHtml( cc.getPartita_iva() ) %>&nbsp;
          </td>
        </tr>
        
        <tr>
          <td class="formLabel">
            <dhv:label name="camera_commercio.codice_fiscale">Codice Fiscale</dhv:label>
          </td>
          <td>
            <%=toHtml( cc.getCf_impresa() ) %>
          </td>
        </tr>
        
        <tr>
          <td class="formLabel">
            <dhv:label name="camera_commercio.cod_natura_giuridica">Codice Natura Giuridica</dhv:label>
          </td>
          <td>
            <%=toHtml( cc.getCod_natura_giuridica() ) %>
          </td>
        </tr> 
            
        <tr>
          <td class="formLabel">
            <dhv:label name="camera_commercio.cod_attivita_istat_91_primario">Codice ISTAT attivit&agrave; 91 Primario</dhv:label>
          </td>
          <td>
            <%=toHtml( cc.getCod_attivita_istat_91_primario() ) %>
          </td>
        </tr> 
        
        <tr>
          <td class="formLabel">
            <dhv:label name="camera_commercio.oggetto_sociale">Oggetto Sociale</dhv:label>
          </td>
          <td>
            <%=toHtml( cc.getOggetto_sociale_1() ) 
            	+ toHtml( cc.getOggetto_sociale_2() ) 
            	+ toHtml( cc.getOggetto_sociale_3() ) 
            	+ toHtml( cc.getOggetto_sociale_4() ) 
            	+ ( (cc.isFlag_omissis_oggetto_sociale()) ? ("...") : ("") )%>
          </td>
        </tr> 
        
        <tr>
          <th colspan="2">
            <strong><dhv:label name="camera_commercio.sede_legale">Sede Legale</dhv:label></strong>
          </th>
        </tr>

        <tr>
          <td class="formLabel">
            <dhv:label name="camera_commercio.provincia_sede_legale">Provincia</dhv:label>
          </td>
          <td>
            <%=toHtml( cc.getProvincia_sede_legale() ) %>
          </td>
        </tr>
        
        <tr>
          <td class="formLabel">
            <dhv:label name="camera_commercio.comune_sede_legale">Descrizione Comune</dhv:label>
          </td>
          <td>
            <%=toHtml( cc.getDescrizione_comune_sede_legale() ) %>
          </td>
        </tr>
        
        <tr>
          <td class="formLabel">
            <dhv:label name="camera_commercio.istat_comune_sede_legale">Codice ISTAT Comune</dhv:label>
          </td>
          <td>
            <%=toHtml( cc.getCodice_istat_comune_sede_legale() ) %>
          </td>
        </tr>
        
        <tr>
          <td class="formLabel">
            <dhv:label name="camera_commercio.via_sede_legale">Via</dhv:label>
          </td>
          <td>
            <%=toHtml( cc.getCod_toponimo_sede_legale() ) + " " + toHtml( cc.getVia_sede_legale() ) %>
          </td>
        </tr>
        
        <tr>
          <td class="formLabel">
            <dhv:label name="camera_commercio.civico_sede_legale">Civico</dhv:label>
          </td>
          <td>
            <%=toHtml( cc.getCivico_sede_legale() ) %>
          </td>
        </tr>         
        
        <tr>
          <td class="formLabel">
            <dhv:label name="camera_commercio.cap_sede_legale">CAP</dhv:label>
          </td>
          <td>
            <%=toHtml( cc.getCap_sede_legale() ) %>
          </td>
        </tr> 
        
         <tr>
          <th colspan="2">
            <strong><dhv:label name="camera_commercio.ul">UL</dhv:label></strong>
          </th>
        </tr>
                
        <tr>
          <td class="formLabel">
            <dhv:label name="camera_commercio.denomizione_ul">Denominazione</dhv:label>
          </td>
          <td>
            <%=toHtml( cc.getDenominazione_ul() ) %>
          </td>
        </tr>
        
        <tr>
          <td class="formLabel">
            <dhv:label name="camera_commercio.provincia_ul">Provincia</dhv:label>
          </td>
          <td>
            <%=toHtml( cc.getProvincia_ul() ) %>
          </td>
        </tr>
        
        <tr>
          <td class="formLabel">
            <dhv:label name="camera_commercio.comune_sede_legale">Descrizione Comune</dhv:label>
          </td>
          <td>
            <%=toHtml( cc.getDescrizione_comune_ul() ) %>
          </td>
        </tr>
        
        <tr>
          <td class="formLabel">
            <dhv:label name="camera_commercio.istat_comune_sede_legale">Codice ISTAT Comune</dhv:label>
          </td>
          <td>
            <%=toHtml( cc.getCodice_istat_ul() ) %>
          </td>
        </tr>
        
        <tr>
          <td class="formLabel">
            <dhv:label name="camera_commercio.via_sede_legale">Via</dhv:label>
          </td>
          <td>
            <%=toHtml( cc.getCod_toponimo_ul() ) + " " + toHtml( cc.getVia_ul() ) %>
          </td>
        </tr>
        
        <tr>
          <td class="formLabel">
            <dhv:label name="camera_commercio.civico_ul">Civico</dhv:label>
          </td>
          <td>
            <%=toHtml( cc.getCivico_ul() ) %>
          </td>
        </tr>         
        
        <tr>
          <td class="formLabel">
            <dhv:label name="camera_commercio.cap_ul">CAP</dhv:label>
          </td>
          <td>
            <%=toHtml( cc.getCap_ul() ) %>
          </td>
        </tr> 
         
        <tr>
          <td class="formLabel">
            <dhv:label name="camera_commercio.telefono_ul">Telefono</dhv:label>
          </td>
          <td>
            <%=toHtml( cc.getTelefono_ul() ) %>
          </td>
        </tr> 
            
        <tr>
          <td class="formLabel">
            <dhv:label name="camera_commercio.anno_denuncia_addetti_ul">Anno Denuncia Addetti UL</dhv:label>
          </td>
          <td>
            <%=toHtml( cc.getAnno_denuncia_addetti_ul() ) %>
          </td>
        </tr> 
         
		<tr>
          <td class="formLabel">
            <dhv:label name="camera_commercio.descrizione_attivita_ul">Descrizione Attivit&agrave; UL</dhv:label>
          </td>
          <td>
            <%=toHtml( cc.getDescrizione_attivita_ul_1() ) 
            	+ toHtml( cc.getDescrizione_attivita_ul_2() ) 
            	+ toHtml( cc.getDescrizione_attivita_ul_3() ) 
            	+ toHtml( cc.getDescrizione_attivita_ul_4() ) 
            	+ ( (cc.isFlag_omissis_descrizione_attivita_ul()) ? ("...") : ("") ) %>
          </td>
        </tr> 
        
        
      </table>
      
      
    </td>
    
  </tr>
  
</table>

	<input type="hidden" name="id" value="<%=cc.getId() %>" />
	<% if( cc.getData_importazione_osa() == null ){ %>
	<dhv:permission name="accounts-cameracommercio-edit">
		<dhv:permission name="accounts-accounts-add">
			<input type="submit" value="<dhv:label name="button.importa">Importa</dhv:label>" />
		</dhv:permission>
	</dhv:permission>
	<%} %>
</form>

