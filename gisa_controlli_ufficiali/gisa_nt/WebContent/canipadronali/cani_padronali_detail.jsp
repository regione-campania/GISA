<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="CaneDetails" class ="org.aspcfs.modules.canipadronali.base.Cane" scope ="request" />
<jsp:useBean id="ListaAsl" class ="org.aspcfs.utils.web.LookupList" scope ="request" />

<%@ include file="../initPage.jsp" %>
<% String param1 = "orgId=" + CaneDetails.getProprietario().getIdProprietario()+"&assetId="+CaneDetails.getId() ; %>
<dhv:container name="canipadronali" selected="details" object="CaneDetails" param="<%= param1 %>" >
<table class="trails" cellspacing="0">
<tr>
<td>Cani Padronali> Anagrafica Cani di proprieta</td>
</tr>
</table>
<br>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
     <strong>Informazioni cane</strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
     Asl
    </td>
    <td>
      <%=ListaAsl.getSelectedValue(CaneDetails.getProprietario().getIdAsl()) %>
    </td>
  </tr>
    <tr class="containerBody">
    <td nowrap class="formLabel">
     Razza
    </td>
    <td>
      <%=CaneDetails.getRazza() %>
    </td>
  </tr>
    <tr class="containerBody">
    <td nowrap class="formLabel">
     Sesso
    </td>
    <td>
      <%=CaneDetails.getSesso() %>
    </td>
  </tr>
    <tr class="containerBody">
    <td nowrap class="formLabel">
     Data Nascita
    </td>
    <td>
      <%=CaneDetails.getDataNascita() %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
     Microchip
    </td>
    <td>
      <%=CaneDetails.getMc() %>
    </td>
  </tr>
  
  </table>
<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
     <strong>Informazioni Proprietario</strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
     Nome
    </td>
    <td>
      <%=toHtml(CaneDetails.getProprietario().getNome()) %>
    </td>
  </tr>
    <tr class="containerBody">
    <td nowrap class="formLabel">
     Cognome
    </td>
    <td>
      <%=toHtml(CaneDetails.getProprietario().getCognome()) %>
    </td>
  </tr>
    <tr class="containerBody">
    <td nowrap class="formLabel">
     Cf
    </td>
    <td>
      <%=toHtml(CaneDetails.getProprietario().getCodiceFiscale()) %>
    </td>
  </tr>
    <tr class="containerBody">
    <td nowrap class="formLabel">
     Data Nascita
    </td>
    <td>
      <%=toHtml(""+CaneDetails.getProprietario().getDataNascita()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
     Comune di nascita
    </td>
    <td>
      <%=toHtml(CaneDetails.getProprietario().getLuogoNascita()) %>
    </td>
  </tr>
  
  </table>

<br>

<%
for (IndirizzoProprietario ind :CaneDetails.getProprietario().getLista_indirizzi())
{
	if(ind != null && ind.getCitta()!=null)
	{
	%>
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
     <strong>Indirizzo Proprietario</strong>
    </th>
  </tr>
	 <tr class="containerBody">
    <td nowrap class="formLabel">
     Indirizzo Residenza
    </td>
    <td>
      <%=ind.getVia() %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
     Citta Residenza
    </td>
    <td>
      <%=ind.getCitta() %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
    Provincia
    </td>
    <td>
      <%=ind.getProvincia() %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
     Cap
    </td>
    <td>
      <%=ind.getCap() %>
    </td>
  </tr>
  </table>
	
	<%
	}
}
%>

</dhv:container>