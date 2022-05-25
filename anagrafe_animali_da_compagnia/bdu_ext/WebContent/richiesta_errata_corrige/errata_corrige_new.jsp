<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ include file="../../initPage.jsp" %> 

<jsp:useBean id="Animale" class="org.aspcfs.modules.anagrafe_animali.base.Animale" scope="request"/> 
<jsp:useBean id="LookupTipoInformazione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="LookupMotivoCorrezione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

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

function annulla(riferimentoId, riferimentoIdNomeTab){
	if (confirm("Annullare l'operazione?")){
		loadModalWindow();
		window.location.href="GestioneRichiesteErrataCorrige.do?command=GestioneRichiesteErrataCorrige&riferimentoId="+riferimentoId+"&riferimentoIdNomeTab="+riferimentoIdNomeTab;
	}
}

</script>




    <br>
   
   
 <%
String param1 = "idAnimale=" + Animale.getIdAnimale() + "&idSpecie=" + Animale.getIdSpecie();
%>	

<dhv:container name="animale" selected="Gestione Richieste Errata Corrige" object="Animale" param="<%=param1%>">
  
<b>Richiesta di errata corrige a nome di: </b> <u><%=User.getContact().getNameFirst() %> <%=User.getContact().getNameLast() %> (<%=User.getUsername() %>)</u><br/>
<b>Animale: </b> <u> <%=Animale.getMicrochip() %></u><br/><br/>

<form id = "addAccount" name="addAccount" action="GestioneRichiesteErrataCorrige.do?command=SalvaErrataCorrige&auto-populate=true" method="post" >

<input type="hidden" id="riferimentoId" name="riferimentoId" value="<%=Animale.getIdAnimale()%>"/>
<input type="hidden" id="riferimentoIdNomeTab" name="riferimentoIdNomeTab" value="animale"/>

<center>
<table class="details" width="50%">
<tr><td align="center"><input type="button" value="ANNULLA" onClick="annulla('<%=Animale.getIdAnimale()%>', 'animale')"/></td>
<td align="center"><input type="button" value="SALVA" onClick="salva(this.form)"/></td></tr>
<tr><td colspan="2" align="center"><font color="red"><b>Attenzione. Si ricorda che per completare l'invio dell'Errata Corrige e' necessario confermare <br/>l'invio all'Help Desk dopo averla compilata.</b></font></td></tr>
</table></center>
<br/><br/>




<table class="details" width="100%">
<tr><th colspan="2">Dettaglio errata corrige</th></tr>
<tr><td>Motivo della correzione</td><td><%=LookupMotivoCorrezione.getHtmlSelect("motivoCorrezione", -1) %></td></tr>
<tr><td>Num. telefono</td><td><input type="text" id="telefono" name="telefono"/></td></tr>
<tr><td>E-Mail</td><td><input type="text" id="mail" name="mail"/></td></tr>
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