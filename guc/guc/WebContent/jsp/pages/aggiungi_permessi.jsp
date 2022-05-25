<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Aggiungi Permessi</title>
<script language="JavaScript">
function toggle(source) {
      var aInputs = document.getElementsByTagName('input');
      for (var i=0;i<aInputs.length;i++) {
          if (aInputs[i] != source && aInputs[i].className == source.className) {
              aInputs[i].checked = source.checked;
          }
      }
	}
</script>
</head>
<%@ page import="java.util.ArrayList"%>
<jsp:useBean id="RoleBean" class="it.us.web.action.guc.rpm.RoleBean"
	scope="request"></jsp:useBean>
<jsp:useBean id="permessi" class="java.util.ArrayList" scope="request"></jsp:useBean>
<body>
<br>
<center>
<div style="width: 1000px; background-color: #FFA500">
<h2>Lista Permessi Disponibili per <%=RoleBean.getRole()%></h2>
</div>
<form id="add" action="guc.rpm.AddPermission.us" name="add"
	method="post">
<table style="width: 900px;" border="0" align="center">
	<tbody>
		<tr>
			<td align="center"><font color="#0000FF" size="3"><b>SELECT</b></font><input
				type="checkbox" class='SEL' name="all" onClick="toggle(this)"
				value="all"></input></td>
			<td align="center"><font color="#0000FF" size="3"><b>CATEGORIA</b></font></td>
			<td align="center"><font color="#0000FF" size="3"><b>DESCRIZIONE</b></font></td>
			<td align="center"><font color="#0000FF" size="3"><b>VIEW</b></font></td>
			<td align="center"><font color="#0000FF" size="3"><b>ADD</b></font></td>
			<td align="center"><font color="#0000FF" size="3"><b>EDIT</b></font></td>
			<td align="center"><font color="#0000FF" size="3"><b>DELETE</b></font></td>
		</tr>
		<%
			String p[];
			String pr[];
			Boolean trovato;
			ArrayList<String> p_disponibili = new ArrayList<String>();
			for (int i = 0; i < permessi.size(); i++) {
				trovato = false;
				p = permessi.get(i).toString().split(" -- ");
				for (int j = 0; j < RoleBean.getPerm().size(); j = j + 4) {
					pr = RoleBean.getPerm().get(j).split(";");
					if (p[2].equals(pr[5])) {
						trovato = true;
						break;
					}
				}
				if (trovato == false)
					p_disponibili.add(permessi.get(i).toString());
			}
			int riga = 0;
			int cont = 0;
			String colore = "#FFFFFF";
			String v[];
			for (int i = 0; i < p_disponibili.size(); i++) {
				v = p_disponibili.get(i).split(" -- ");
		%>
		<tr bgcolor=<%=colore%> onmouseover="this.style.color = 'red';" onmouseout="this.style.color = 'black';">
			<td align="center"><input type="checkbox" class='SEL'
				name="S<%=cont%>" value="S<%=cont%>"></input></td>
			<td><%=v[0]%></td>
			<td><%=v[1]%></td>
			<input type="hidden" name="cat<%=cont%>" value="<%=v[0]%>"></input>  
			<input type="hidden" name="des<%=cont%>" value="<%=v[1]%>"></input>
			<td align="center"><input type="checkbox" name="t<%=cont%>"
				value="t<%=cont%>"></input></td>
			<td align="center"><input type="checkbox" name="u<%=cont%>"
				value="u<%=cont%>"></input></td>
			<td align="center"><input type="checkbox" name="v<%=cont%>"
				value="v<%=cont%>"></input></td>
			<td align="center"><input type="checkbox" name="z<%=cont%>"
				value="z<%=cont%>"></input></td>
		</tr>
		<%
			if (riga == 0) {
					colore = "#E5E7EA";
					riga = 1;
				} else {
					colore = "#FFFFFF";
					riga = 0;
				}
				cont++;
			}
		%>
	</tbody>
</table>
<p align="center"><input name="save" type="submit"
	value="SALVA MODIFICHE" style="width: 150px" /></p>
	<input type="hidden"
	name="DB" value='<%=request.getParameter("DB")%>'> <input type="hidden" name="rn"
	value='<%=RoleBean.getRole()%>'><input type="hidden" name="N"
	value='<%=p_disponibili.size()%>'> <input type="hidden" name="or"
	value='<%=request.getParameter("or")%>'> <input type="hidden" name="tm"
	value='<%=request.getParameter("tm")%>'> <input type="hidden" name="DB1"
	value='<%=request.getParameter("DB1")%>'><input type="hidden" name="DB2"
	value='<%=request.getParameter("DB2")%>'>
<br><br>
<input type="submit" name="home" style="background-image:url('images/homepage.png');
background-repeat:no-repeat;background-position:center center;
width:32px;height:32px;" value=" ">
</form>
</center>
</body>
</html>