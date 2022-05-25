<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ include file="../../initPage.jsp" %> 

<jsp:useBean id="Stabilimento" class="org.aspcfs.modules.ricercaunica.base.RicercaOpu" scope="request"/>
<jsp:useBean id="LookupTipoInformazione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="LookupMotivoCorrezione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="op" class="java.lang.String" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcf.modules.controlliufficiali.base.Organization" scope="request"/>
<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<jsp:useBean id="StabilimentoSintesisDettaglio" class="org.aspcfs.modules.sintesis.base.SintesisStabilimento" scope="request"/>

<jsp:useBean id="idControllo" class="java.lang.String" scope="request"/>
<jsp:useBean id="contesto" class="java.lang.String" scope="request"/>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>

<script>
function salva(form){
	
	var telefono = document.getElementById("telefono").value;
	var mail = document.getElementById("mail").value;

	if (telefono.trim()=='' || telefono.length<5){
		alert('Inserire un numero di telefono valido.');
		return false;
	}
	
	if (mail.trim()=='' || mail.length<5){
		alert('Inserire un indirizzo email valido.');
		return false;
	}
	
	if (confirm("Confermare l'operazione?")){
		loadModalWindow();
		form.submit();
		}
	}

function annulla(riferimentoId, riferimentoIdNomeTab, op){
	if (confirm("Annullare l'operazione?")){
		loadModalWindow();
		window.location.href="GestioneRichiesteErrataCorrige.do?command=GestioneRichiesteErrataCorrige&riferimentoId="+riferimentoId+"&riferimentoIdNomeTab="+riferimentoIdNomeTab+"&op="+op;
	}
}

</script>




    <br>
   
 <%
String nomeContainer = op;
String obj = "OrgDetails";
String param1 = "orgId=" + Stabilimento.getRiferimentoId(); 
String param2 = "stabId=" ; 
String param3 = "altId=" ; 

String param ="";
if (OrgDetails!=null && OrgDetails.getOrgId()>0)
	param = param1;
else if (StabilimentoDettaglio!=null && StabilimentoDettaglio.getIdStabilimento()>0){
	param = param2+StabilimentoDettaglio.getIdStabilimento()+"&opId="+StabilimentoDettaglio.getIdOperatore();
	obj = "Operatore";
    request.setAttribute("Operatore",StabilimentoDettaglio.getOperatore());
}
else if (StabilimentoSintesisDettaglio!=null && StabilimentoSintesisDettaglio.getAltId()>0){
	param = param3+StabilimentoSintesisDettaglio.getAltId()+"&opId="+StabilimentoSintesisDettaglio.getIdOperatore();
	obj = "Operatore";
} 
%>	
<dhv:container name="<%=nomeContainer %>"  selected="Aggiungi Richiesta Errata Corrige" object="<%=obj%>" param="<%=param%>"  hideContainer="false">
  
<b>Richiesta di errata corrige a nome di: </b> <u><%=User.getContact().getNameFirst() %> <%=User.getContact().getNameLast() %> (<%=User.getUsername() %>)</u><br/>
<b>Stabilimento: </b> <u> <%=Stabilimento.getRagioneSociale() %> (<%= (Stabilimento.getNumeroRegistrazione() != null && !Stabilimento.getNumeroRegistrazione().equals("")) ? Stabilimento.getNumeroRegistrazione() : (Stabilimento.getNumAut() != null && !Stabilimento.getNumAut().equals("")) ?  Stabilimento.getNumAut() : "" %> )</u><br/><br/>

<form id = "addAccount" name="addAccount" action="GestioneRichiesteErrataCorrige.do?command=SalvaErrataCorrige&auto-populate=true" method="post" >

<input type="hidden" id="riferimentoId" name="riferimentoId" value="<%=Stabilimento.getRiferimentoId()%>"/>
<input type="hidden" id="riferimentoIdNomeTab" name="riferimentoIdNomeTab" value="<%=Stabilimento.getRiferimentoIdNomeTab()%>"/>
<input type="hidden" id="op" name="op" value="<%=op%>"/>
<input type="hidden" id="idControllo" name="idControllo" value="<%=idControllo%>"/>
<input type="hidden" id="contesto" name="contesto" value="<%=contesto%>"/>

<center>
<table class="details" width="50%">
<tr><td align="center"><input type="button" value="ANNULLA" onClick="annulla('<%=Stabilimento.getRiferimentoId()%>', '<%=Stabilimento.getRiferimentoIdNomeTab()%>', '<%=op%>')"/></td>
<td align="center"><input type="button" value="SALVA" onClick="salva(this.form)"/></td></tr>
<tr><td colspan="2" align="center"><font color="red"><b>Attenzione. Si ricorda che per completare l'invio dell'Errata Corrige e' necessario confermare <br/>l'invio all'Help Desk dopo averla compilata.</b></font></td></tr>
</table></center>
<br/><br/>




<table class="details" width="100%">
<tr><th colspan="2">Dettaglio errata corrige</th></tr>
<tr><td>Motivo della correzione</td><td><%=LookupMotivoCorrezione.getHtmlSelect("motivoCorrezione", -1) %></td></tr>
<% if (idControllo!=null && !idControllo.equals("") && Integer.parseInt(idControllo)>0) {%>
<tr><td>ID CONTROLLO UFFICIALE</td><td><%=idControllo %></td></tr>
<% } %>
<tr><td>Num. telefono</td><td><input type="text" id="telefono" name="telefono"/> <font color="red">*</font></td></tr>
<tr><td>E-Mail</td><td><input type="text" id="mail" name="mail"/>  <font color="red">*</font></td></tr>
<tr><td>Note</td><td><textarea id="note" name="note" rows="3" cols="180"></textarea></td></tr>
</table>


<table class="details" width="100%">
<tr><th>Informazione da modificare</th><th>Dato errato</th><th>Dato Corretto</th></tr>
<%for (int i = 0; i<5; i++){ %>
<tr><td> <%=LookupTipoInformazione.getHtmlSelect("tipoInformazione"+i, -1) %> </td>
<td><textarea id="datoErrato<%=i %>" name="datoErrato<%=i %>" rows="3" cols="60"></textarea></td>
<td><textarea id="datoCorretto<%=i %>" name="datoCorretto<%=i %>" rows="3" cols="60"></textarea></td></tr>
<% } %>
</table>
</form>
		</dhv:container>

</body>
</html>