<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

 
 <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" id = "tabVerbale">
    <tr>
      <th colspan="2">
        <img border="0" src="images/file.gif" align="absmiddle"><b>ALLEGA DOCUMENTO</b>
      </th>
    </tr>
  
      <tr class="containerBody">
      <td class="formLabel">
       File
       (Max. <%=String.format("%.2f", (double) 3145728/ (double) 1048576) %> MB)
       
      </td>
      <td>
        <input type="file" id="fileConsegna1" name="fileConsegna1" size="45">  <a href="#" onclick="rimuoviFileConsegna(1); return false;"><img src="images/delete.gif"></a>
 
      </td>
    </tr>
    
  </table>

  

