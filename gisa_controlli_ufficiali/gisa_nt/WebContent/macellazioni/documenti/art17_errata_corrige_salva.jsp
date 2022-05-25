<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <jsp:useBean id="OrgDetails" class="org.aspcfs.modules.stabilimenti.base.Organization" scope="request"/>
 <jsp:useBean id="Capo" class="org.aspcfs.modules.macellazioni.base.Capo" scope="request"/>
 <jsp:useBean id="ErrataCorrige" class="org.aspcfs.modules.macellazioni.base.Art17ErrataCorrige" scope="request"/>

  <jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../../initPage.jsp" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<link rel="stylesheet" type="text/css" media="screen" documentale_url="" href="css/moduli_screen.css">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script language="javascript">
function openPopupListaErrataCorrigeArt17(idCapo, orgId, idErrataCorrige){
window.location='GestioneDocumenti.do?command=GeneraPDFMacelli&tipo=Macelli_17_ErrataCorrige&orgId='+orgId+'&idCapo='+idCapo+'&idErrataCorrige='+idErrataCorrige;
}
</script>

<script language="javascript">
function load(){
	var bottone = document.getElementById('bottone17');
	bottone.click();
}
</script>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body onload="load()">

<center><strong><u>Modello Errata Corrige generato</u></strong></center>
<br/><br/>
 <img id="image_loading" src="gestione_documenti/images/loading.gif" height="25"/><br/>
<i>Attendere qualche secondo. Generazione documento in corso...</i>
<center><input type="button" id="bottone17" style="display:none" onClick="openPopupListaErrataCorrigeArt17('<%=ErrataCorrige.getIdCapo() %>', '<%=ErrataCorrige.getIdMacello() %>', '<%=ErrataCorrige.getId()%>'); this.disabled=true;" value="GENERA PDF"/> 
<input type="button" id="bottoneChiudi" onClick="window.close()" value="CHIUDI"/>
</center>

</body>
</html>