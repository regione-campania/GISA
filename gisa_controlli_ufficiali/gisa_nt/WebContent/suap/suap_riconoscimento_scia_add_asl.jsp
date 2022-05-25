<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request" />


<script src="javascript/datepicker/jquery.plugin.js"></script>
<script src="javascript/datepicker/jquery.datepick.js"></script>



<div id = "dialogDelega">

<table class="trails" cellspacing="0">
<tr>
<td width="100%">
INSERIMENTO NUOVA SCIA
</td>
</tr>
</table>

<br>
<form id="" name="" action="SuapStab.do?command=Scelta&tipoInserimento=2&stato=3" method="post">

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>COMPILARE I DATI PER ANDARE AVANTI</strong>
    </th>
  </tr>
 
    <tr>
      <td nowrap class="formLabel">
        Data Arrivo Pec da SUAP/Camera di Commercio
      </td>
      <td>
       <input type="text" size="15" name="dataRichiesta"
					id="dataRichiesta" required="required" placeholder="dd/MM/YYYY">
      </td>
    </tr>
    
     <tr>
      <td nowrap class="formLabel">
        Seleziona Comune
      </td>
      <td>
      <%
      ComuniList.setRequired(true);
      %>
       <%=ComuniList.getHtmlSelectText("comuneSuap", "-1") %>
      </td>
    </tr>
    
    </table>
    <div align="right">
        <input type="submit"  class="aniceBigButton" style="height:40px !important; width:250px !important;" value="AVANTI" />  <br/><br/>
       </div>
       
        
</form>

<script>
$('#dataRichiesta').datepick({dateFormat: 'dd/mm/yyyy',  maxDate: 0,  showOnFocus: false, showTrigger: '#calImg'});

</script>
</div>