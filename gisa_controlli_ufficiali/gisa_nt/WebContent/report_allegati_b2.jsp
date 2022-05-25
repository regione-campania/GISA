<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<style>
table a:link {
	color: #666;
	font-weight: bold;
	text-decoration:none;
}
table a:visited {
	color: #999999;
	font-weight:bold;
	text-decoration:none;
}
table a:active,
table a:hover {
	color: #bd5a35;
	text-decoration:underline;
}
table {
	font-family:Arial, Helvetica, sans-serif;
	color:#666;
	font-size:12px;
	text-shadow: 1px 1px 0px #fff;
	margin:20px;
	border:#ccc 1px solid;
	-moz-border-radius:3px;
	-webkit-border-radius:3px;
	border-radius:3px;

	-moz-box-shadow: 0 1px 2px #d1d1d1;
	-webkit-box-shadow: 0 1px 2px #d1d1d1;
	box-shadow: 0 1px 2px #d1d1d1;
}
table th {
	padding:21px 25px 22px 25px;
	border-top:1px solid #fafafa;
	border-bottom:1px solid #e0e0e0;

	background: #ededed;
	background: -webkit-gradient(linear, left top, left bottom, from(#ededed), to(#ebebeb));
	background: -moz-linear-gradient(top,  #ededed,  #ebebeb);
}
table th:first-child {
	text-align: left;
	padding-left:20px;
}
table tr:first-child th:first-child {
	-moz-border-radius-topleft:3px;
	-webkit-border-top-left-radius:3px;
	border-top-left-radius:3px;
}
table tr:first-child th:last-child {
	-moz-border-radius-topright:3px;
	-webkit-border-top-right-radius:3px;
	border-top-right-radius:3px;
}
table tr {
	text-align: center;
	padding-left:20px;
}
table td:first-child {
	text-align: left;
	padding-left:20px;
	border-left: 0;
}
table td {
	padding:18px;
	border-top: 1px solid #ffffff;
	border-bottom:1px solid #e0e0e0;
	border-left: 1px solid #e0e0e0;

}

table tr.presente td {
	background: #98FB98;
		color:#000;

}

table tr:last-child td {
	border-bottom:0;
}
table tr:last-child td:first-child {
	-moz-border-radius-bottomleft:3px;
	-webkit-border-bottom-left-radius:3px;
	border-bottom-left-radius:3px;
}
table tr:last-child td:last-child {
	-moz-border-radius-bottomright:3px;
	-webkit-border-bottom-right-radius:3px;
	border-bottom-right-radius:3px;
}
html * {
    font-family: "Trebuchet MS", Helvetica, sans-serif;
}
</style>

<%@page import="org.aspcfs.utils.GestoreConnessioni"%><%@ page language="java" import="java.sql.*" %>
<% response.setContentType("text/html");%>

<%! public static String fixStringa(String nome)
  {
	  String toRet = nome;
	  if (nome == null || nome.equals("null"))
		  return toRet;
	  toRet = nome.replaceAll("'", "");
	  toRet = toRet.replaceAll(" ", "_");
	  toRet = toRet.replaceAll("\\?","");
	
	  return toRet;
	  
  }%>


<center>

<table>
<tr>
<td><img src="images/concourseSuiteCommunitySplashOLD.png" width="120px"/></td>
<td>
<b>Report integrativo Piano B2 - Monitoraggio requisiti biosicurezza allevamenti suini</b><br/>
Prot.2016.0486361 15/07/2016
</td></tr>
</table>
</center>
<br/>
<font size="2px">
<div align="right">
<i>Si ricorda che non esiste alcun meccanismo di controllo della relazione tra nome file e controllo ufficiale per cui ci sono delle vulnerabilità legate ai nomi dei file. <br/>
1)Non c'è alcun controllo sul nome dei file che l'utente allega per cui non è assolutamente garantito che il nome del file inserito in GISA corrisponda all'id di un controllo esistente.<br/>
2)il nome file potrebbe contenere spazi oppure caratteri o cifre diverse in modo da inficiare la corrispondenza precisa con l'id controllo associato.<br/>
Inoltre<br/>
3)i file potrebbero essere associati ad OSA diversi da allevamenti suini<br/>
4)i file potrebbero avere un contenuto diverso dalla checklist</i>
</div>

Questa pagina contiene l'elenco dei file caricati con le seguenti condizioni:
<ul>
<li>L'impresa su cui è stato caricato il file presenta almeno un controllo ufficiale sul piano B2</li>
<li>I file evidenziati in verde vengono restituiti anche dalla reportistica Di.Ge.Mon.</li>
<li>I file non evidenziati in verde non vengono restituiti dalla reportistica. Potrebbe trattarsi di file che non devono rientrare nel flusso o file allegati per il piano B2 senza rispettare lo standard concordato (ID del controllo nel nome del file).</li>
</ul>
</font>


<table>

<tr>
<th>ID CONTROLLO</th>
<th>DATA CONTROLLO</th>
<th>MOTIVO ISPEZIONE</th>
<th>STATO CONTROLLO</th>
<th>NOME FILE</th>
<th>DATA FILE</th>
<th>CODICE FILE</th>
<th>TIPO OPERATORE</th>
<th>NORMA OPERATORE</th>
<th>RAGIONE SOCIALE</th>
<th>ASL CU</th>
<th>ID ASL CU</th>
<th>DOWNLOAD</th>
</tr>

<%
int tot = 0;
int totSI = 0;
int totNO = 0;

String str=request.getParameter("queryString");
Connection con =  null ;
try {

con = GestoreConnessioni.getConnection();

String sql = "SELECT * FROM  public_functions.dbi_get_allegati_b2_totali();";
Statement stm = con.createStatement();
ResultSet rs= stm.executeQuery(sql);
while (rs.next ()){
tot++;
if (rs.getString("presente").equals("SI"))
	totSI++;
else
	totNO++;
%>
<tr <%if (rs.getString("presente").equals("SI")){%> class="presente" <%}%>>
<td><%=rs.getInt("id_controllo") %></td>
<td><%=rs.getTimestamp("data_controllo") %></td>
<td><font size="1px"><%=rs.getString("motivo_ispezione") %></font></td>
<td><%=rs.getString("stato_controllo") %></td>
<td><%=rs.getString("file_nome") %></td>
<td><%=rs.getTimestamp("file_data_inserimento") %></td>
<td><%=rs.getString("file_codice") %></td>
<td><%=rs.getString("operatore_tipo") %></td>
<td><%=rs.getString("operatore_norma") %></td>
<td><%=rs.getString("operatore_ragione_sociale") %></td>
<td><%=rs.getString("asl_controllo") %></td>
<td><%=rs.getInt("id_asl_controllo") %></td>
<td>
<a href="GestioneAllegatiUpload.do?command=DownloadPDF&codDocumento=<%=rs.getString("file_codice") %>&nomeDocumento=<%=fixStringa(rs.getString("file_nome")) %>">Download</a>
</td>
</tr>
<%
}}catch(Exception e){
out.println("Exception is ;"+e);
}
finally
{
	GestoreConnessioni.freeConnection(con) ;
}
%>


</table>

<br/>
<center>
Tot. <%=tot %><br/>
Presenti in Di.Ge.Mon.: <%=totSI %><br/>
Non presenti in Di.Ge.Mon.: <%=totNO %>
</center>

