<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 
<jsp:useBean id="listaSchede" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="Stabilimento" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<jsp:useBean id="Relazione" class="org.aspcfs.modules.opu.base.LineaProduttiva" scope="request"/>

<jsp:useBean id="esito" class="java.lang.String" scope="request"/>

<% HashMap<Integer, SintesisProdotto> hashProdotti = (HashMap<Integer, SintesisProdotto>) request.getAttribute("hashProdotti"); %>

<%@ page import="org.aspcfs.modules.sintesis.base.*" %>
<%@ page import="org.aspcfs.modules.opu.base.*" %>
<%@ page import="org.aspcfs.modules.gestioneml.base.*" %>
<%@ page import="java.util.HashMap" %>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>


<script src='javascript/modalWindow.js'></script>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>

<script>
function checkForm(form){
	loadModalWindow();
	form.submit();
}

function checkCheck(cb){
	if (cb.checked){
		cb.parentNode.parentNode.setAttribute('class', 'rowGreen');
	}
	else {
		cb.parentNode.parentNode.setAttribute('class', '');
	}
}


function selezionaTutti(idScheda){
	
	var table = document.getElementById("table_"+idScheda);
	
	 checkboxes = table.getElementsByTagName('input');
	  for(var i=0, n=checkboxes.length;i<n;i++) {
		 if (!checkboxes[i].checked){
	    	checkboxes[i].click();
	    	//checkCheck(checkboxes[i]);
		 }
	  }
}
</script>

<div class="documentaleNonStampare">
<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>
</div>

<center>
<i>Prodotti per: <br/>
<b><%=Stabilimento.getOperatore().getRagioneSociale() %></b></center>

<br/><br/>

<%if (esito!=null && esito.equalsIgnoreCase("ok")){ %>
<center><font color="green">Prodotti salvati con successo.</font></center>
<%} %>


<%if (listaSchede.size()==0){ %>
<script>
alert('AL MOMENTO NON SONO DISPONIBILI SCHEDE SUPPLEMENTARI PER QUESTA LINEA PRODUTTIVA');
window.close();
</script>
<%}  %>

<form name="searchAccount" action="OpuStab.do?command=SalvaProdotti" method="post">

<center>
	<table>
	
<dhv:permission name="sintesis-prodotti-edit">	
<tr><td colspan="4"><input type="button" onClick="checkForm(this.form)" value="Salva"/></td></tr>
</dhv:permission>
<input type="hidden" id="totSchede" name="totSchede" value="<%=listaSchede.size() %>"/>
<input type="hidden" id="idStabilimento" name="idStabilimento" value="<%=Stabilimento.getIdStabilimento() %>"/>
<input type="hidden" id="idRelazione" name="idRelazione" value="<%=Relazione.getId_rel_stab_lp() %>"/>

</table>
	</center>
	
	<br/><br/>
 	
			
			<%
			
			int idSchedaOld = -1;
			
			for (int i=0;i<listaSchede.size(); i++){
				SuapMasterListSchedaAggiuntiva scheda = (SuapMasterListSchedaAggiuntiva) listaSchede.get(i);
				SintesisProdotto prod = (SintesisProdotto) hashProdotti.get(scheda.getId());
				
			%>
			
			<% if (scheda.getIdScheda()!=idSchedaOld) { %>
			
			<% if (idSchedaOld > -1){%>
				
				</table>
					<br/><br/>
				
			<% } %>
			
			<table cellpadding="2" cellspacing="2"  border="1" class="details" id ="table_<%=scheda.getIdScheda() %>">
			<tr><th colspan="2"><%=scheda.getTitScheda() %> <input type="button" onClick="selezionaTutti('<%=scheda.getIdScheda() %>')" value="SELEZIONA TUTTI"/></th></tr>
			<%
				idSchedaOld = scheda.getIdScheda();
			} %>
			
			<tr>
			<input type="hidden" id="idProdotto_<%=i %>" name="idProdotto_<%=i %>" value="<%=scheda.getId()%>"/>
			</td>
			<td><%=scheda.getNome() %>
			
			<%if (scheda.isTestoAggiuntivo()) { %>
			<input type="text" id="valore_prod_<%=i%>" name="valore_prod_<%=i%>" placeholder="Inserire qui" value="<%=(prod!=null && prod.getValoreProdotto()!=null) ? prod.getValoreProdotto() : ""%>"/>
			<%} %>
			
			</td> 
			<td><input type="checkbox" id="cb_prod_<%=i%>" name="cb_prod_<%=i%>" <%=(prod!=null && prod.isChecked()) ? "checked" : ""  %> onClick="checkCheck(this)" />
			<% if (prod!=null && prod.isChecked()){ %> <script>checkCheck(document.getElementById("cb_prod_<%=i%>"))</script><%} %>
			</td>
			
			</tr>
		<%} %>

</table>


</form>
