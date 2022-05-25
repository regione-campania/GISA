<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../initPage.jsp" %>

<script type="text/javascript">
function clearForm() {
    <%-- Account Filters --%>
    document.forms['searchOperatori'].targa.value="";
    document.forms['searchOperatori'].duplicati.checked="";
    
  }


	function trim(stringa){
	    while (stringa.substring(0,1) == ' '){
	        stringa = stringa.substring(1, stringa.length);
	    }
	    while (stringa.substring(stringa.length-1, stringa.length) == ' '){
	        stringa = stringa.substring(0,stringa.length-1);
	    }
	    return stringa;
	};
	
	function checkForm()
	{
		targa = document.getElementById( 'targa' ).value;
			
		all = ( "" + targa );
	
		if( trim( all ).length > 0 )
		{
			return true;
		}
		else
		{
			alert( "Selezionare almeno un filtro" );
			return false;
		}
	};
	
</script>

<form name="searchOperatori" action="OperatoriFuoriRegione.do?command=Cerca" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="OperatoriFuoriRegione.do?command=Dashboard&tipoD=Distributori"><dhv:label name="">Imprese Fuori Ambito ASL</dhv:label></a> > 
			<dhv:label name="imprese_pregresso.cerca">Cerca Impresa Erogatrici Reg. Altre ASL della Campania da importare</dhv:label>
		</td>
	</tr>
</table>

<table cellpadding="2" cellspacing="2" border="0" width="100%">
  <tr>
    <td width="50%" valign="top">

      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong><dhv:label name="imprese_pregresso.filtri">Filtri</dhv:label></strong>
          </th>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="">Nome Impresa</dhv:label>
          </td>
          <td>
            <input id="targa" type="text" maxlength="70" size="50" name="name" />
          </td>
        </tr>
        
        <tr>
          <td class="formLabel">
            <dhv:label name="imprese_pregresso.duplicati">Evita Duplicati</dhv:label>
          </td>
          <td>
            <input id="duplicati" type="checkbox" name="duplicati" />
          </td>
        </tr>
        
      </table>
      
      
    </td>
    
  </tr>
  
</table>

<input onclick="return checkForm();" type="submit" value="<dhv:label name="button.search">Search</dhv:label>">
<input type="button" value="<dhv:label name="button.clear">Clear</dhv:label>" onClick="javascript:clearForm();">

</form>


