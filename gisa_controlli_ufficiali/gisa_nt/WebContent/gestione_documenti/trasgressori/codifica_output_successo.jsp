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
        <jsp:useBean id="oggetto" class="java.lang.String" scope="request"/>
        <jsp:useBean id="nomeClient" class="java.lang.String" scope="request"/>
        

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
function ritornaAllegato (cod, tit, ogg, nom){
		window.close();
}
function setAllegato (cod, tit, ogg, nom){
	
	var size = parseInt(window.opener.document.getElementById('allegato_rt_size').value);
	
	var nuovoIndice = size;
	var nuovoSize = size+1;
	
	window.opener.document.getElementById('allegato_rt_size').value = nuovoSize;
	
	var nuovoDiv = document.createElement('div');
	
	var link_temp = "<a id = '"+cod+"_download' href='GestioneAllegatiTrasgressori.do?command=DownloadPDF&codDocumento="+cod+"&nomeDocumento="+nom+"'>"+ogg+"</a><br/><br/>";
	var cod_temp = "<input type='hidden' id='allegato_rt_"+nuovoIndice+"' name='allegato_rt_"+nuovoIndice+"' value='"+cod+"'/>";
	var ogg_temp = "<input type='hidden' id='allegato_rt_oggetto_"+nuovoIndice+"' name='allegato_rt_oggetto_"+nuovoIndice+"' value='"+ogg+"'/>";
	var nom_temp = "<input type='hidden' id='allegato_rt_nomeclient_"+nuovoIndice+"' name='allegato_rt_nomeclient_"+nuovoIndice+"' value='"+nom+"'/>";

	nuovoDiv.innerHTML =  link_temp + cod_temp + ogg_temp + nom_temp;
	window.opener.document.getElementById('linkAllegati').appendChild(nuovoDiv);
	}
</script>

	<% String param1 = "orgId=" + orgId;   
%>
<body onload="setAllegato('<%=codDocumento %>', '<%=fixStringa(titolo) %>', '<%=fixStringa(oggetto) %>', '<%=fixStringa(nomeClient)%>')">
<center><b><p><span style="color:green"><%=codDocumento %> - <%=titolo %> - <%=oggetto %> - <%=nomeClient %></span> </p></b>
<dhv:evaluate if="<%=(messaggioPost!=null) %>"> 
<label><font size="5"><%=messaggioPost %></font></label>
</dhv:evaluate>
<br/>

<input type="button" class="buttonClass" style="width:200px;height:50px" value="CHIUDI E CONTINUA" onclick="ritornaAllegato('<%=codDocumento %>', '<%=fixStringa(titolo) %>', '<%=fixStringa(oggetto) %>', '<%=fixStringa(nomeClient)%>')" />

</center>
</body>
