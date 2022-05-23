<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.*"%>
<%@page import="it.us.web.util.guc.*"%>
<%@page import="it.us.web.bean.guc.*"%>


<%@page import="java.io.File"%><div id="content" align="center">

<div align="center">
	<a href="Home.us" style="margin: 0px 0px 0px 50px"><img src="images/lista.png" height="18px" width="18px" />Lista Utenti</a>
</div>
	<h4 class="titolopagina">Importa Utenti</h4>
	<form enctype="multipart/form-data" name="addUser" method="post" action="guc.Import.us">
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
		<tr>
			<th colspan="2">Im porta File</th>
		</tr>
		<tr>
			<td class="formLabel">Scegli File da Importare</td>
			<td><input type="file" name="file"></td>
		</tr>
	</table>
	<br>
	<input type="submit" value="Processa"></form>
	
	<br><br>
	
	
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	<tr>
			<th colspan="2">Lista Import Eseguiti</th>
		</tr>
		
	<%
	
	HashMap<String, File[]> listaUpload  = (HashMap<String, File[]>) request.getAttribute("DirList");
	Iterator<String> itKey = listaUpload.keySet().iterator();
	if (itKey.hasNext())
	{
	while ( itKey.hasNext())
	{
		String key = itKey.next();
		%>
			<tr><th colspan="2"><%=key %></th></tr>
			<tr>
			<th colspan="1">File Importato</th>
			<th colspan="1">File di Log</th>
		</tr>
			
		<%
		File fileLog = null ; 
		File fileUpload = null ; ; 
		File [] fileInDir = listaUpload.get(key);
		for(int i = 0 ; i< fileInDir.length;i++)
		{
			if (fileInDir[i].getName().startsWith("LOG"))
			{
				fileLog = fileInDir[i];
			}
			else
			{
				fileUpload = fileInDir[i] ;
			}
			
		}%>
		<tr>
		<td><center><a href = "guc.ViewFile.us?fileName=<%=key+"/"+fileUpload.getName() %>"> <%=fileUpload.getName() %></a></center></td><td><center><a href = "guc.ViewFile.us?fileName=<%=key+"/"+fileLog.getName() %>"><%=fileLog.getName() %></a></center></td>
		</tr>
		<%
		
	}
	}
	else
	{
		%>
		<tr><td colspan="2">Non Sono stati Eseguiti Import di Utenti</td></tr>
		
		<%
		
	}
	
	%>
	</table>
	
</div>