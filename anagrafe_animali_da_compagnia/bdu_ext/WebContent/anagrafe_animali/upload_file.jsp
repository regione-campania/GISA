<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ include file="../initPage.jsp" %>
<br/>
<form  enctype="multipart/form-data" name="inputForm" action="AnimaleAction.do?command=UploadListaMCVerificaEsistenza" method="post" >
	<table>
		<tr>
			<td>File: </td>
			<td>
				<input type="file" name="microchips" size="45">
			</td>
			<td><input  type="submit" value="Prosegui" /></td>
		</tr>
	</table>
</form>
<span style="color: red"><%=toHtmlValue( (String)request.getAttribute( "errore" ) ) %></span>

<table>
<tr>
		<td>
		Per ogni Microchip inserito nel file di input, è riportato se è presente o meno in banca dati.</br>  
		Qualora dovesse essere presente, sono indicate le seguenti informazioni: </br>
		<li>Asl</li>
		<li>Se il cane è presente regolarmente o è stato cancellato logicamente dalla banca dati </li>
		<li>Eventuale data di sterilizzazione</li>
		<li>Eventuale Contributo regionale</li>
		</td>
		</tr>
		<BR/>
		<tr>
		<td><label><font color="red" size ="3">NON SONO AMMESSI FILE CON PIU' DI 250 MICROCHIP</font></label></td>
		</tr>
</table>

