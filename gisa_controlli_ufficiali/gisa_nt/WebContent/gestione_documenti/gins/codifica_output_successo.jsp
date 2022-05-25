<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        
    <%@ include file="../../initPage.jsp"%>
    <jsp:useBean id="messaggioPost" class="java.lang.String" scope="request"/>
    <jsp:useBean id="orgId" class="java.lang.String" scope="request"/>
      <jsp:useBean id="label" class="java.lang.String" scope="request"/>
       <jsp:useBean id="codDocumento" class="java.lang.String" scope="request"/>
        <jsp:useBean id="titolo" class="java.lang.String" scope="request"/>
     <jsp:useBean id="codiceAllegato" class="java.lang.String" scope="request"/>
        

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.opu.base.*, org.aspcfs.modules.base.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

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
  
<script>
function ritornaAllegato (cod, codAll, tit){
		window.close();
}
function setAllegato (cod, codAll, tit){
	
	window.opener.document.getElementById('header_'+codAll).value=cod;
	tit = tit.replace(cod+"_", "");
	var download = ' <b>File caricato: </b><a href=\"GestioneAllegatiGins.do?command=DownloadPDF&codDocumento='+cod+'&nomeDocumento='+tit+' \">'+tit+ '</a>';
	window.opener.document.getElementById('titolo_'+codAll).innerHTML=download;
	}
	
function setAllegatoSchedaSupplementare (cod, codAll, tit){
	
	tit = tit.replace(cod+"_", "");
	
	var trfield = document.createElement('tr');
	trfield.setAttribute('id','tr_' + codAll);
	window.opener.document.getElementById('tabella_schede_supplementari').appendChild(trfield);
	
	var tdfield1 = document.createElement('td');
    tdfield1.setAttribute('style', 'width : 25%; background-color: #EDEDED');
	tdfield1.innerHTML = '<p align="center"><b>SCHEDA SUPPLEMENTARE</b></p>';
	window.opener.document.getElementById('tr_' + codAll).appendChild(tdfield1);
	
	var tdfield2 = document.createElement('td');
	tdfield2.setAttribute('colspan', '3');
	var texthtml = "";
	texthtml += "<input type='hidden' readonly='readonly' id='header_"+ codAll +"' name='header_"+ codAll +"' value='"+ cod +"'/>";
	texthtml += "<label><b>File caricato: </b><a href=\"GestioneAllegatiGins.do?command=DownloadPDF&codDocumento="+cod+"&nomeDocumento="+tit+" \">"+tit+"</a></label>";
	texthtml += "<input type='button' style='float: right;' value='rimuovi allegato' onclick='rimuovi_linea(\"tr_" + codAll + "\")'/>";
	tdfield2.innerHTML = texthtml;
	window.opener.document.getElementById('tr_' + codAll).appendChild(tdfield2);
	
	
	var numero_schede_sup = window.opener.document.getElementById('numero_schede_sup').value;
	window.opener.document.getElementById('numero_schede_sup').value = parseInt(numero_schede_sup) + 1;
	
}
</script>

	<% String param1 = "orgId=" + orgId;   
%>
<% if(codiceAllegato.contains("schedasup")){ %>
	<body onload="setAllegatoSchedaSupplementare('<%=codDocumento %>', '<%=codiceAllegato%>', '<%=fixStringa(titolo) %>')">
<% } else { %>
	<body onload="setAllegato('<%=codDocumento %>', '<%=codiceAllegato%>', '<%=fixStringa(titolo) %>')">
<% } %>
<center><b><p><span style="color:green"><%=titolo.replace(codDocumento+"_", "") %></span> </p></b>
<dhv:evaluate if="<%=(messaggioPost!=null) %>"> 
<label><font size="5"><%=messaggioPost %></font></label>
</dhv:evaluate>
<br/>


<input type="button" class="buttonClass" style="width:200px;height:50px" value="CHIUDI E CONTINUA" onclick="ritornaAllegato('<%=codDocumento %>', '<%=codiceAllegato%>','<%=fixStringa(titolo) %>')" />


</center>
</body>
