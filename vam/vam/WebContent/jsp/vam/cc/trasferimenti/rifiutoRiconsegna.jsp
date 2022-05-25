<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="java.util.Date"%>
<script language="JavaScript" type="text/javascript" src="js/vam/cc/rickettsiosi/add.js"></script>


<form action="vam.cc.trasferimenti.RifiutoRiconsegna.us" method="post" name="myForm" onsubmit="javascript:return checkForm(this);">
	
	<input type="hidden" name="idTrasferimento" value="${trasferimento.id }" />
	
	<h4 class="titolopagina">
	   	Rifiuto Riconsegna
	</h4>
	
	<table class="tabella">
		
		<tr>
        	<th colspan="2">
        		Dati Rifiuto Riconsegna
        	</th>
        </tr>
			
		<tr class="even">
    		<td style="text-align: right; width: 40%">
    			 <strong>Data</strong>
    		</td>
    		<td style="text-align: left;">
    			<fmt:formatDate type="date" value="<%=new Date() %>" pattern="dd/MM/yyyy" var="dataOdierna"/>
    			${dataOdierna }
    			<input type="hidden" name="dataRifiutoRiconsegna" value="${dataOdierna }" />
    		</td>
        </tr>
		
		<tr>
			<td style="text-align: right;"><strong>Nota Criuv</strong> <font color="red">* </font></td>
			<td style="text-align: left;">
				<textarea rows="10" cols="60" name="notaApprovazioneRiconsegna"></textarea>
			</td>
		</tr>
		<tr>
			<td colspan="2" style="text-align: center;">	
				<input type="submit" value="Salva"/>
				<input type="button" value="Annulla" onclick="attendere(), location.href='vam.cc.trasferimenti.Home.us'">
			 </td>
		 </tr>
 	</table>

</form>

<script type="text/javascript">
function checkForm( form )
{
	if( document.myForm.notaApprovazioneRiconsegna.value != "" )
	{
		return true;
	}
	else
	{
		alert( "Inserire nota Criuv" );
		return false;
	}
}
</script>
