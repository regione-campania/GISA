<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<jsp:useBean id="Specie" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Allevamenti.do"><dhv:label name="allevamenti.allevamenti">Allevamenti</dhv:label></a> > 
<dhv:label name="allevamenti.search">Ricerca In BDN</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<form method="post" action = "Allevamenti.do?command=CompareWS">
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong>Ricerca in BDN</strong>
          </th>
        </tr>
        <tr>
          <td class="formLabel">
            Codice Azienda
          </td>
          <td>
            <input type="text" size="23" name="codiceAzienda" value="">
          </td>
        </tr>
        
        <tr>
          <td class="formLabel">
            Partita Iva
          </td>
          <td>
            <input type="text" size="23" name="pIva" value="">
          </td>
        </tr>
        
        <tr>
          <td class="formLabel">
            Specie
          </td>
          <td>
          <input type = "text" name = "codSpecie" >
           <%--=Specie.getHtmlSelect("codSpecie",-1) --%>
          </td>
        </tr>
        
        </table>
        <input type = "submit" value = "Invia Richiesta">
 
        </form>
        
       