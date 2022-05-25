<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
   
<jsp:useBean id="listaDocumenti" class="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleDocumentoList" scope="request"/>
<%@page import="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleDocumento"%>
<jsp:useBean id="downloadURL" class="java.lang.String" scope="request"/>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<jsp:useBean id="tipo" class="java.lang.String" scope="request"/>
<jsp:useBean id="idCU" class="java.lang.String" scope="request"/>
<jsp:useBean id="orgId" class="java.lang.String" scope="request"/>
<jsp:useBean id="stabId" class="java.lang.String" scope="request"/>
<jsp:useBean id="ticketId" class="java.lang.String" scope="request"/>
<jsp:useBean id="url" class="java.lang.String" scope="request"/>
<jsp:useBean id="extra" class="java.lang.String" scope="request"/>
<jsp:useBean id="htmlcode" class="java.lang.String" scope="request"/>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>

<script>function checkForm(form) {
	form.generaPDF.disabled=true;
	form.generaPDF.style.background="#919191";
	//form.generaPDF.value='GENERAZIONE PDF IN CORSO. ATTENDERE.';
	form.glifo.style.visibility='hidden';
	document.getElementById("glifoLabel").style.visibility='hidden';
	if (document.getElementById("downloadUltimo")!=null)
		document.getElementById("downloadUltimo").style.visibility='hidden';
	if (document.getElementById("listaDoc")!=null)
		document.getElementById("listaDoc").style.visibility='hidden';
	return true;
}</script>

<script>function reload(form) {
	form.generaPDF.disabled=false;
	form.glifo.checked=false;
	return true;
}</script>
<script>
function goBack() {
    window.history.go(-2);
}
</script>


 <%! public static String fixData(String timestring)
  {
	  String toRet = "";
	  if (timestring == null)
		  return toRet;
	  String anno = timestring.substring(0,4);
	  String mese = timestring.substring(5,7);
	  String giorno = timestring.substring(8,10);
	  String ora = timestring.substring(11,13);
	  String minuto = timestring.substring(14,16);
	  String secondi = timestring.substring(17,19);
	  toRet =giorno+"/"+mese+"/"+anno+" "+ora+":"+minuto+":"+secondi;
	  return toRet;
	  
  }%>


<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<dhv:label name="">Documenti</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>

<br>

<%String timbra = "";
timbra = "GestioneDocumenti.do?command=GeneraPDF&tipo="+tipo+"&orgId="+orgId+"&idCU="+idCU+"ticketId="+ticketId;
	
%>

<form name="timbra" action="GestioneDocumenti.do?command=GeneraPDF" method="POST">
<center><input type="button" value="Aggiorna" onClick="reload(this.form); window.location.reload()"/></center>
<input type="button" id ="generaPDF" value="Genera PDF" 	onClick="if(checkForm(this.form)){this.form.submit();}" />
<input type="hidden" name="orgId" id="orgId" value="<%=orgId %>"></input>
<input type="hidden" name="stabId" id="stabId" value="<%=stabId %>"></input>
<input type="hidden" name="ticketId" id="ticketId" value="<%=ticketId %>"></input>
<input type="hidden" name="tipo" id="tipo" value="<%=tipo %>"></input>
<input type="hidden" name="idCU" id="idCU" value="<%=idCU %>"></input>
<input type="hidden" name="url" id="url" value="<%=url %>"></input>
<input type="hidden" name="extra" id="extra" value="<%=extra %>"></input>
<%session.setAttribute("htmlcode", htmlcode); %>
<input type="hidden" name="glifo" id="glifo" value="" onclick="if(this.checked){this.value='glifo';} else {this.value='';}" > <label id="glifoLabel"><!-- Timbra con glifo--></label><br>
</form>
<%if (listaDocumenti.size()>0) {
	DocumentaleDocumento docUltimo = (DocumentaleDocumento) listaDocumenti.get(0);
	
%>
<%-- <a href="GestioneDocumenti.do?command=DownloadPDF&codDocumento=<%=docUltimo.getIdHeader()%>&idDocumento=<%=docUltimo.getIdDocumento() %>"  style="text-decoration:none;"><input type="button" id="downloadUltimo" name="downloadUltimo" value="Download ultima versione (<%=fixData(docUltimo.getDataCreazione()) %>)"></input></a> --%>
<%} %>

<br></br>
  <!-- table id = "listaDoc" name ="listaDoc" cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
		<tr>
				<th><strong>Data creazione</strong></th>
				<th><strong>Generato da</strong></th>
				<th><strong>Recupera</strong></th>
		</tr>
	
			
		<%
	if (listaDocumenti.size()>0)
		for (int i=0;i<listaDocumenti.size(); i++){
			DocumentaleDocumento doc = (DocumentaleDocumento) listaDocumenti.get(i);
			
			
			%>
			
		
		<%} %>
		
		</table -->
	<br>

<%-- <% if (tipo!=null && tipo.equals("19")){ %> --%>
<!-- 	<input type="button"  onclick="goBack()" value="Indietro"/> -->
<%-- 	<%} %>	 --%>

  <!-- dhv:pagedListControl object="AssetTicketInfo"/-->



</body>
</html>