<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<jsp:useBean id="msg" class="java.lang.String" scope="request"/>

<%@ include file="../initPage.jsp"%>

<script>
function checkForm(form){
	if (form.nomeBreve.value==''){
		alert('Indicare un nome breve!')
		return false;
	}
	
	if (confirm("Confermare l'operazione? I dati non potranno essere modificati.")){
		form.submit();
}
}

</script>



<%if (msg!=null && !msg.equals("")){ %>
<center>

<br/><br/>
<font size="10px" color="green"><%=msg %></font>
<br/><br/>

<input type="button" value="CHIUDI" onClick="window.close()"/>

</center>
<%} else { %>


<center>
ATTENZIONE. Si procedera' alla propagazione della clinica in VAM con questi dati:
</center>
<br/><br/>

<form id = "addAccount" name="addAccount" action="OpuStab.do?command=SincronizzaVam&auto-populate=true" method="post">
<table class="details">
<tr><td class="formLabel">Nome</td> <td><input type="text" id="nome" name="nome" readonly style="background-color: #DEDEDE" value="<%=StabilimentoDettaglio.getOperatore().getRagioneSociale() %>"/></td></tr>
<tr><td class="formLabel">Nome breve</td> <td><input type="text" id="nomeBreve" name="nomeBreve" maxlength="10" value="<%=StabilimentoDettaglio.getOperatore().getRagioneSociale().substring(0, 10) %>"/></td></tr>
<tr><td class="formLabel">ASL</td> <td><input type="text" id="asl" name="asl" readonly  style="background-color: #DEDEDE" value="<%=StabilimentoDettaglio.getSedeOperativa().getDescrizioneAsl() %>"/></td></tr>
<tr><td class="formLabel">Comune</td> <td><input type="text" id="comune" name="comune" readonly  style="background-color: #DEDEDE" value="<%=StabilimentoDettaglio.getSedeOperativa().getDescrizioneComune()%>"/></td></tr>
<tr><td class="formLabel">Indirizzo</td> <td><input type="text" id="indirizzo" name="indirizzo" readonly  style="background-color: #DEDEDE"  value="<%=StabilimentoDettaglio.getSedeOperativa().getDescrizioneToponimo() %> <%=StabilimentoDettaglio.getSedeOperativa().getVia() %> <%=StabilimentoDettaglio.getSedeOperativa().getCivico() %>"/></td></tr>
<tr><td class="formLabel">Email</td> <td><input type="text" id="email" name="email" readonly style="background-color: #DEDEDE"  value="<%=toHtml(StabilimentoDettaglio.getOperatore().getEmail()) %>"/></td></tr>
<tr><td class="formLabel">Telefono</td> <td><input type="text" id="telefono" name="telefono" readonly  style="background-color: #DEDEDE" value="<%=toHtml(StabilimentoDettaglio.getOperatore().getTelefono1()) %>"/></td></tr>
<tr><td colspan="2">
<input type="hidden" id="stabId" name="stabId" value="<%=StabilimentoDettaglio.getIdStabilimento()%>"/>
<input type="button" onClick="checkForm(this.form)" value="CONFERMA"/></td></tr>
</table>
</form>

<%}%>