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
<title>Modifica Ruolo</title>
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
<jsp:useBean id="RoleBean" class="it.us.web.action.guc.rpm.RoleBean"
	scope="request"></jsp:useBean>
<body>
<br>
<center>
<div style="width: 1000px; background-color: #FFA500">
<h2>Modifica Info Del Ruolo : <%=RoleBean.getRole()%></h2>
</div>
</center>
<form id="update" action="guc.rpm.UpdateRoleAction.us" name="update"
	method="get">
<center>
<table style="width: 400px;" border="0" align="center">
	<tbody>
		<tr>
			<td align="center"><font color="#0000FF" size="3"><b>NOME</b></font></td>
			<td align="center"><font color="#0000FF" size="3"><b>DESCRIZIONE</b></font></td>
			<td align="center"><font color="#0000FF" size="3"><b>ENABLED</b></font></td>
			<td align="center"><font color="#0000FF" size="3"><b>TYPE</b></font></td>
			<td align="center"><font color="#0000FF" size="3"><b>NOTE</b></font></td>
		</tr>
		<tr>
			<td><input type="text" name="role"
				value="<%=RoleBean.getRole()%>"></td>
			<td><input type="text" name="descr"
				value="<%=RoleBean.getDescr()%>"></td>
			<td><input readonly type="text" name="enabled"
				value="<%=RoleBean.getEnabled()%>"></td>
			<td><input type="text" name="role_type"
				value="<%=RoleBean.getRole_type()%>"></td>
			<td><input type="text" name="note"
				value="<%=RoleBean.getNote()%>"></td>
		</tr>
	</tbody>
</table>
</center>
<br>
<center>
<div style="width: 1000px; background-color: #FFA500">
<h2>Modifica Permessi Del Ruolo : <%=RoleBean.getRole()%></h2>
</div>
</center>
<center>
<table style="width: 800px;" border="0" align="center">
	<tbody>
		<tr>
			<td align="center"><font color="#0000FF" size="3"><b>TRASH</b></font><input
				type="checkbox" class='TR' name="all" onClick="toggle(this)"
				value="all"></input></td>
			<td align="center"><font color="#0000FF" size="3"><b>CATEGORIA</b></font></td>
			<td align="center"><font color="#0000FF" size="3"><b>DESCRIZIONE</b></font></td>
			<td align="center"><font color="#0000FF" size="3"><b>VIEW</b></font></td>
			<td align="center"><font color="#0000FF" size="3"><b>ADD</b></font></td>
			<td align="center"><font color="#0000FF" size="3"><b>EDIT</b></font></td>
			<td align="center"><font color="#0000FF" size="3"><b>DELETE</b></font></td>
		</tr>
		<%
			String s[];
			String m[];
			String value[] = new String[4];
			String opt[] = new String[4];
			int index = 0;
			int cont = 0;
			String colore = "#FFFFFF";
			int riga = 0;
			for (int j = 0; j < RoleBean.getPerm().size(); j = j + 4) {
				s = RoleBean.getPerm().get(j).split(";");
				for (int i = j; i < j + 4; i++) {
					m = RoleBean.getPerm().get(i).split(";");
					value[index] = m[3];
					index++;
					if (index == 4)
						index = 0;
				}
		%>
		<tr bgcolor=<%=colore%> onmouseover="this.style.color = 'red';" onmouseout="this.style.color = 'black';">
			<td align="center"><input type="checkbox" class='TR'
				name="trash<%=cont%>" value="trash<%=cont%>"></input></td>
			<td><%=s[0]%></td>
			<td><%=s[1]%></td>
			<td align="center"><input type="checkbox" name="t<%=cont%>"
				value="<%=value[0]%>" <%if (value[0].equals("true")) {%>
				checked="checked" <%}%>></input></td>
			<td align="center"><input type="checkbox" name="u<%=cont%>"
				value="<%=value[1]%>" <%if (value[1].equals("true")) {%>
				checked="checked" <%}%>></input></td>
			<td align="center"><input type="checkbox" name="v<%=cont%>"
				value="<%=value[2]%>" <%if (value[2].equals("true")) {%>
				checked="checked" <%}%>></input></td>
			<td align="center"><input type="checkbox" name="z<%=cont%>"
				value="<%=value[3]%>" <%if (value[3].equals("true")) {%>
				checked="checked" <%}%>></input></td>
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

			String d1 = request.getParameter("D1");
			String d2 = request.getParameter("D2");
			String mod1 = request.getParameter("mod1");
			String mod2 = request.getParameter("mod2");
			String or=null;
			String tm=null;
			String db = null;
			if (mod1 != null){
				db = d1; tm="mod1"; or=request.getParameter("RoleDB2");
			}
			if (mod2 != null){
				db = d2; tm="mod2"; or=request.getParameter("RoleDB1");
			}
		%>
	</tbody>
</table>
</center>
<p align="center"><input name="save" type="submit"
	value="SALVA MODIFICHE" style="width: 150px" /> <input name="delete"
	type="submit" value="ELIMINA RUOLO" style="width: 150px" /> <input
	name="add" type="submit" value="AGGIUNGI PERMESSI" style="width: 150px" /><br>
<br>
<input type="checkbox" name="cb" value="sync">Sincronizza
Permessi dei Ruoli Associati<br>
<input type="hidden" name="DB1" value='<%=d1%>'> <input
	type="hidden" name="DB2" value='<%=d2%>'> <input type="hidden"
	name="DB" value='<%=db%>'> <input type="hidden" name="rn"
	value='<%=RoleBean.getRole()%>'> <input type="hidden" name="tm"
	value='<%=tm%>'> <input type="hidden" name="or"
	value='<%=or%>'> 
<br><br>
<input type="submit" name="home" style="background-image:url('images/homepage.png');
background-repeat:no-repeat;background-position:center center;
width:32px;height:32px;" value=" ">
</form>
</body>
</html>