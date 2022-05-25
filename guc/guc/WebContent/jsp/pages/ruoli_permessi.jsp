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
<title>Ruoli e Permessi</title>
<%@ page import="it.us.web.action.guc.rpm.Db"%>
</head>
<jsp:useBean id="RolesBean" class="it.us.web.action.guc.rpm.RolesBean"
	scope="request"></jsp:useBean>
<jsp:useBean id="RoleBean" class="it.us.web.action.guc.rpm.RoleBean"
	scope="request"></jsp:useBean>
<body>
<script type="text/javascript">
function aggiornaHidden1(sel){
	  var f = document.form1;		
	  f.DB1.value = sel.options[sel.selectedIndex].value;
}

function aggiornaHidden2(sel){
	var g = document.form1;	
	g.DB2.value = sel.options[sel.selectedIndex].value;
}
</script>

<%
	Db lista = new Db();
	lista.buildLista();
%>
<!-- SELEZIONE DB -->
<br>
<form id="form1" action="guc.rpm.MyConnectAction.us" name="form1"
	method="get">
<center>
<div style="width: 1200px; background-color: #FFA500">
<h2>Selezione Dei DB</h2>
</div>
<select name="db1" onchange="aggiornaHidden1(this)">
	<option value="sel1">SELEZIONA DB1</option>
	<%
		for (int i = 0; i < lista.getLista().size(); i++)
			out.println("<option value=\"" + lista.getLista().get(i)
					+ "\">" + lista.getLista().get(i) + "");
	%>
</select> <input type="hidden" name="DB1"> <select name="db2"
	onchange="aggiornaHidden2(this)">
	<option value="sel2">SELEZIONA DB2</option>
	<%
		for (int i = 0; i < lista.getLista().size(); i++)
			out.println("<option value=\"" + lista.getLista().get(i)
					+ "\">" + lista.getLista().get(i) + "");
	%>
</select> <input type="hidden" name="DB2"> <input name="check"
	type="submit" value="CHECK" /></center>
</form>
<br>

<!-- SEZIONE RUOLI -->
<form id="form2" action="guc.rpm.ScambioAction.us" name="form2"
	method="get">
<center>
<div style="width: 1200px; background-color: #FFA500">
<h2>Ruoli</h2>
</div>
<input type="hidden" name="D1" value=<%=request.getParameter("DB1")%>>
<input type="hidden" name="D2" value=<%=request.getParameter("DB2")%>>
<script type="text/javascript">
	var msg = '<%=RoleBean.getMsg()%>';
	var D1 = '<%=request.getParameter("D1")%>';
	var D2 = '<%=request.getParameter("D2")%>';
	if (msg!='val'){
		if (window.confirm(msg)) {
	        window.location.href='guc.rpm.MyConnectAction.us?db1='+D1+'&DB1='+D1+'&db2='+D2+'&DB2='+D2+'&check=CHECK';
	    }
		else window.location.href='guc.rpm.MyConnectAction.us?db1='+D1+'&DB1='+D1+'&db2='+D2+'&DB2='+D2+'&check=CHECK';
	   <%RoleBean.setMsg("val");%>
	}
</script>

<table style="width: 1100px;" border="0" align="center">
	<tbody>
		<tr>
			<td>
			<p align=center>
			<%
				if (request.getParameter("DB1") != null)
					out.println("<font color=\"#0000FF\" size=\"3\"><b>"
							+ request.getParameter("DB1").toUpperCase()
							+ "</b></font><br>");
			%> <select name="RoleDB1" style="width: 350px;" size="15" >
				<%
					if (RolesBean != null && RolesBean.getRoleDB1().size() > 0) {
						for (int i = 0; i < RolesBean.getRoleDB1().size(); i++)
							out.println("<option onmouseover=\"this.style.color = 'red';\" onmouseout=\"this.style.color = 'black';\" value='"
									+ RolesBean.getRoleDB1().get(i) + "'>"
									+ RolesBean.getRoleDB1().get(i) + "</option>");
					}
				%>
			</select><br>
			<input name="mod1" type="submit" value="MODIFICA" /></p>
			</td>
			<td>&nbsp;</td>
			<td>
			<p align=center><input name="sx" type="submit" value=&lt&lt /><br>
			<input name="dx" type="submit" value=&gt&gt /><br>
			<input name="ASSOCIA_R" type="submit" value="ASSOCIA"
				style="width: 80px" /><br>
			<input name="SEPARA_R" type="submit" value="SEPARA"
				style="width: 80px" /><br>
			<br>
			<font color="#0000FF" size="3"><b>RUOLI ASSOCIATI</b></font><br>
			<select name="r_ass" size="7" style="width: 450px;"
				disabled="disabled">
				<%
					if (RolesBean != null && RolesBean.getCommonRole().size() > 0) {
						for (int i = 0; i < RolesBean.getCommonRole().size(); i++)
							out.println("<option value='" + i + "'>"
									+ RolesBean.getCommonRole().get(i) + "</option>");
					}
				%>
			</select></p>
			</td>
			<td>&nbsp;</td>
			<td>
			<p align=center>
			<%
				if (request.getParameter("DB2") != null)
					out.println("<font color=\"#0000FF\" size=\"3\"><b>"
							+ request.getParameter("DB2").toUpperCase()
							+ "</b></font><br>");
			%> <select name="RoleDB2" style="width: 350px;" size="15">
				<%
					if (RolesBean != null && RolesBean.getRoleDB2().size() > 0) {
						for (int i = 0; i < RolesBean.getRoleDB2().size(); i++)
							out.println("<option onmouseover=\"this.style.color = 'red';\" onmouseout=\"this.style.color = 'black';\" value='"
									+ RolesBean.getRoleDB2().get(i) + "'>"
									+ RolesBean.getRoleDB2().get(i) + "</option>");
					}
				%>
			</select> <input name="mod2" type="submit" value="MODIFICA" /></p>
			</td>
		</tr>
	</tbody>
</table>
</center>

<!-- SEZIONE PERMESSI -->
<center>
<div style="width: 1200px; background-color: #FFA500">
<h2>Permessi</h2>
</div>
<table style="width: 1100px;" border="0" align="center">
	<tbody>
		<tr>
			<td>
			<p align=center><select name="PDB1" style="width: 550px"
				size="15">
				<%
					if (RolesBean != null && RolesBean.getPermDB1().size() > 0) {
						for (int i = 0; i < RolesBean.getPermDB1().size(); i++)
							out.println("<option onmouseover=\"this.style.color = 'red';\" onmouseout=\"this.style.color = 'black';\" value='"
									+ RolesBean.getPermDB1().get(i) + "'>"
									+ RolesBean.getPermDB1().get(i) + "</option>");
					}
				%>
			</select></p>
			</td>
			<td>&nbsp;</td>
			<td><br>
			<br>
			<p align=center><input name="ASSOCIA_P" type="submit"
				value="ASSOCIA" style="width: 80px" /><br>
			<input name="SEPARA_P" type="submit" value="SEPARA"
				style="width: 80px" /></p>
			</td>
			<td>&nbsp;</td>

			<td>
			<p align=center><select name="PDB2" style="width: 550px"
				size="15">
				<%
					if (RolesBean != null && RolesBean.getPermDB2().size() > 0) {
						for (int i = 0; i < RolesBean.getPermDB2().size(); i++)
							out.println("<option onmouseover=\"this.style.color = 'red';\" onmouseout=\"this.style.color = 'black';\" value='"
									+ RolesBean.getPermDB2().get(i) + "'>"
									+ RolesBean.getPermDB2().get(i) + "</option>");
					}
				%>
			</select></p>
			</td>
		</tr>
	</tbody>
</table>
<table style="width: 600px;" border="0" align="center">
	<tbody>
		<tr>
			<td>
			<p align=center><font color="#0000FF" size="3"><b>PERMESSI
			ASSOCIATI</b></font> <select name="p_ass" style="width: 1000px" size="10"
				disabled="disabled">
				<%
					if (RolesBean != null && RolesBean.getCommonPermission().size() > 0) {
						for (int i = 0; i < RolesBean.getCommonPermission().size(); i++)
							out.println("<option value='" + i + "'>"
									+ RolesBean.getCommonPermission().get(i)
									+ "</option>");
					}
				%>
			</select></p>
			</td>
			<td>&nbsp;</td>
		</tr>
	</tbody>
</table>
</center>
</form>
</body>
</html>