<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.aspcfs.modules.troubletickets.base.Ticket"%>
<%@page import="org.aspcfs.modules.praticacontributi.base.Pratica"%>
<!--  %@page import="org.aspcfs.modules.praticaContributiGestioneSeparata.base.PraticaNew"%-->

<html>
<head>
<jsp:useBean id="OrgDetails" class="com.darkhorseventures.framework.beans.GenericBean" scope="request"/>
<jsp:useBean id="TicketDetails" class="com.darkhorseventures.framework.beans.GenericBean" scope="request"/>
<jsp:useBean id="pratica" class="com.darkhorseventures.framework.beans.GenericBean" scope="request"/>
<jsp:useBean id="pratica2" class="com.darkhorseventures.framework.beans.GenericBean" scope="request"/>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert</title>
</head>
<body>
<!-- Questa jsp deve essere per
tutti i metodi di inserimento al fine di 
evitare che cliccando sui tasti di refresh 
vengano inseriti più volte gli stessi record. 
Le variabili commandD e org_code devono essere 
valorizzate nel command insert. -->

<script type="text/javascript">
var command;
var org_cod;
<%
String contesto[] = new String[6];
int id=-1;

if(TicketDetails!=null){
	String ct = TicketDetails.getClass().toString();
	contesto = ct.split("[.]");
	ct = contesto[3];
	if(ct.equals("troubletickets")){
		org.aspcfs.modules.troubletickets.base.Ticket tnew ;
		tnew = (org.aspcfs.modules.troubletickets.base.Ticket)TicketDetails;
		int oid= tnew.getOrgId();
		int tid = tnew.getId();
		%>
		command = "?command=TicketDetails";
		org_cod = "&id="+"<%= tid %>"+"&orgId="+"<%= oid %>";
	
		<%
		}
}
if (pratica!=null){
	String ct=pratica.getClass().toString();
	contesto = ct.split("[.]");
	ct = contesto[3];
	if(ct.equals("praticacontributi")){
		org.aspcfs.modules.praticacontributi.base.Pratica tnew;
		tnew = (org.aspcfs.modules.praticacontributi.base.Pratica)pratica;
		int oid= tnew.getId();
		%>
		command = "?command=Details";
		org_cod = "&id="+"<%= oid %>";
		<%
		}

}





%>

var url_= location.href;
url_nuovo = new Array();
url_nuovo=url_.split("?command");
url_ = url_nuovo[0];

window.location.href = url_+command+org_cod;
</script>
</body>
</html>
