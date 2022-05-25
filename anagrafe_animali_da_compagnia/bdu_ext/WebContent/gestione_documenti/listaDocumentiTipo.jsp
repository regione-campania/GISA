<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ include file="../../initPage.jsp"%>
<jsp:useBean id="listaDocumenti" class="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleDocumentoList" scope="request"/>
<%@page import="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleDocumento"%>

<jsp:useBean id="downloadURL" class="java.lang.String" scope="request"/>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.opu.base.*, org.aspcfs.modules.base.*, org.aspcfs.modules.registrazioniAnimali.base.*" %>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="Animale" class="org.aspcfs.modules.anagrafe_animali.base.Animale" scope="request"/>
<jsp:useBean id="tipo" class="java.lang.String" scope="request"/>


<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>

  <%! public static String fixData(String timestring)
  {
	  String toRet = "";
	  if (timestring == null || timestring.equals("null"))
		  return toRet;
	  String anno = timestring.substring(0,4);
	  String mese = timestring.substring(5,7);
	  String giorno = timestring.substring(8,10);
	  String ora = timestring.substring(11,13);
	  String minuto = timestring.substring(14,16);
	  String secondi = timestring.substring(17,19);
	  toRet =giorno+"/"+mese+"/"+anno+" "+ora+":"+minuto;
	  return toRet;
	  
  }%>
  
<html>
<script>function checkForm(form) {
	form.generaPDF.disabled=true;
	form.generaPDF.style.background="#919191";
	form.generaPDF.value='GENERAZIONE PDF IN CORSO. ATTENDERE.';
	//form.glifo.style.visibility='hidden';
	//document.getElementById("glifoLabel").style.visibility='hidden';
	//form.generazionePulita.style.visibility='hidden';
	//document.getElementById("generazionePulitaLabel").style.visibility='hidden';
	if (document.getElementById("downloadUltimo")!=null)
		document.getElementById("downloadUltimo").style.visibility='hidden';
	if (document.getElementById("listaDoc")!=null)
		document.getElementById("listaDoc").style.visibility='hidden';
	return true;
}</script>
<script> function closeWindow(){
	window.close();
	window.opener.location.reload(); 
}

</script>
<script>function reload(form) {
	form.generaPDF.disabled=false;
	//form.generazionePulita.checked=false;
	//form.glifo.checked=false;
	return true;
}</script>

<% String param1 = "idAnimale=" + Animale.getIdAnimale() +"&idSpecie=" + Animale.getIdSpecie();
   %>
  <body>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<dhv:label name="">Documenti</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<br>

<form name="timbra" action="GestioneDocumenti.do?command=GeneraPDF" method="POST">
<center><input type="button" id="aggiorna" name="aggiorna" value="Aggiorna" onClick="reload(this.form); window.location.reload()"/></center>
<input type="button" id ="generaPDF" value="Genera NUOVA versione del certificato" 	onClick="if(checkForm(this.form)){this.form.submit();}" />
<input type="hidden" name="IdAnimale" id="IdAnimale" value="<%=Animale.getIdAnimale() %>"></input>
<input type="hidden" name="IdSpecie" id="IdSpecie" value="<%=Animale.getIdSpecie() %>"></input>
<input type="hidden" name="idMicrochip" id="idMicrochip" value="<%=request.getParameter("idMicrochip") %>"></input>
<input type="hidden" name="tipo" id="tipo" value="<%=tipo %>"></input>
<input type="hidden" name="idLinea" id="idLinea" value="<%=request.getParameter("idLinea") %>"></input>
<input type="hidden" name="idEvento" id="idEvento" value="<%=request.getParameter("idEvento") %>"></input>
<!-- input type="checkbox" name="glifo" id="glifo" value="glifo" onclick="if(this.checked){this.value='glifo';} else {this.value='';}" > <label id="glifoLabel">Timbra con glifo</label-->
<input type="hidden" name="generazionePulita" id="generazionePulita" value="si" onclick="if(this.checked){this.value='si';} else {this.value='';}" > <label id="generazionePulitaLabel"> (Saranno ignorati tutti i controlli temporali e di unicità)</label><br>
<input type="hidden" name="generaNonLista" id="generaNonLista" value="ok"></input>
</form>


<%if (listaDocumenti.size()>0) {
	DocumentaleDocumento docUltimo = (DocumentaleDocumento) listaDocumenti.get(0);
	
%>
<a href="GestioneDocumenti.do?command=DownloadPDF&codDocumento=<%=docUltimo.getIdHeader()%>&idDocumento=<%=docUltimo.getIdDocumento() %>"  style="text-decoration:none;"><input type="button" id="downloadUltimo" name="downloadUltimo" value="Download ultima versione (<%=fixData(docUltimo.getDataCreazione()) %>)"></input></a>
<%} %>


<!-- p align="right">
<img style="text-decoration: none;" width="150" src="gestione_documenti/images/sdlogo.png" />
</p-->
<br/><br/>
<dhv:evaluate if="<%=(User.getRoleId()== new Integer(ApplicationProperties.getProperty("ID_RUOLO_HD1"))
|| User.getRoleId()== new Integer(ApplicationProperties.getProperty("ID_RUOLO_HD2"))) || tipo.equals("PrintSchedaAdozioneCani") || tipo.equals("PrintSchedaMorsicatura") %>">
   <table id="listaDoc" cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
		<tr>
				<th><strong>ID</strong></th>
				<th><strong>Data creazione</strong></th>
				<th><strong>Generato da</strong></th>
				<th><strong>Recupera</strong></th>
		</tr>
	
			
			<%
	
	if (listaDocumenti.size()>0)
		for (int i=0;i<listaDocumenti.size(); i++){
			DocumentaleDocumento doc = (DocumentaleDocumento) listaDocumenti.get(i);
			
			
			%>
			
			<tr class="row<%=i%2%>">
			<td><%= (doc.getIdHeader()!=null && !doc.getIdHeader().equals("null")) ? doc.getIdHeader() : doc.getIdDocumento() %></td> 
			<td><%= fixData(doc.getDataCreazione()) %></td>
			<td><dhv:username id="<%=doc.getUserId() %>"></dhv:username>
						 (<%= doc.getUserIp() %>)
			</td> 
			<td>
			
			<a href="GestioneDocumenti.do?command=DownloadPDF&codDocumento=<%=doc.getIdHeader()%>&idDocumento=<%=doc.getIdDocumento() %>"><input type="button" value="DOWNLOAD"></input></a>
			
			</td> 
		</tr>
		<%} %>
		
		</table>
	<br>
</dhv:evaluate>
		

  <!-- dhv:pagedListControl object="AssetTicketInfo"/-->



</body>
</html>