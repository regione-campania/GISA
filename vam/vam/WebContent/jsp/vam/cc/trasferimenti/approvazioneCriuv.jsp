<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="java.util.Date"%>
<script language="JavaScript" type="text/javascript" src="js/vam/cc/rickettsiosi/add.js"></script>


<form action="vam.cc.trasferimenti.ApprovazioneCriuv.us" method="post" name="myForm" onsubmit="javascript:return checkForm(this);">
	
	<input type="hidden" name="idTrasferimento" value="${trasferimento.id }" />
	
	<h4 class="titolopagina">
	   	Approvazione Trasferimento
	</h4>
	
	<table class="tabella">
		
		<tr>
        	<th colspan="2">
        		Dati Approvazione Trasferimento
        	</th>
        </tr>
			
		<tr class="even">
    		<td style="text-align: right; width: 40%">
    			 <strong>Data</strong>
    		</td>
    		<td style="text-align: left;">
    			<fmt:formatDate type="date" value="<%=new Date() %>" pattern="dd/MM/yyyy" var="dataOdierna"/>
    			${dataOdierna }
    			<input type="hidden" name="dataApprovazioneCriuv" value="${dataOdierna }" />
    		</td>
        </tr>
		
		<tr>
			<td style="text-align: right;"><strong>Nota Criuv</strong> <font color="red">* </font></td>
			<td style="text-align: left;">
				<textarea rows="10" cols="60" name="notaCriuv"></textarea>
			</td>
		</tr>
		
		<tr>
			<td style="text-align: right;"><strong>Clinica di Destinazione</strong></td>
			<td style="text-align: left;">
				<select name="clinicaDestinazioneId">
					<c:set var="idAslVecchia" value="-1" />
					<c:forEach items="${cliniche }" var="clinica">
						<c:if test="${clinica.lookupAsl.id !=  idAslVecchia}">
							<c:set var="idAslVecchia" value="${clinica.lookupAsl.id }" />
							<c:if test="${idAslVecchia != -1 }">
								</optgroup>
							</c:if>
							<optgroup label="${clinica.lookupAsl }">
						</c:if>
						<option <c:if test="${clinica == trasferimento.clinicaDestinazione }">selected="seleccted"</c:if> value="${clinica.id }">${clinica.nome }</option>
					</c:forEach>
					<c:if test="${idAslVecchia != -1 }">
						</optgroup>
					</c:if>
				</select>
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
	if( document.myForm.notaCriuv.value != "" )
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

