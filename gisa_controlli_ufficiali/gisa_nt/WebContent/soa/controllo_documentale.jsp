<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="java.util.Iterator"%>
<%@page import="org.aspcfs.utils.web.CustomLookupElement"%>
<%@page import="org.aspcfs.utils.web.CustomLookupList"%>
<%@page import="org.aspcfs.modules.login.beans.UserBean"%>

<%@page import="org.aspcfs.modules.stabilimenti.base.StatiStabilimenti"%><jsp:useBean id="ListaQuesiti" class="org.aspcfs.modules.stabilimenti.base.ControlloDocumentale" scope = "request"></jsp:useBean>

<%
UserBean user = (UserBean) session.getAttribute("User");
CustomLookupList lista_quesiti = ListaQuesiti.getCll();
%>

<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.soa.base.Organization" scope="request"/>

<script>
function doSubmit(roleId,stato)
{
checkformAsl = true ;
checkformStap = true ;
ritornoPratica = false ;
form = document.forms[0] ;
for (i=0;i<document.getElementById('numQuesiti').value ; i++)
{
	if(document.getElementById('id_quesito_'+i).value!='13' && document.getElementById('id_quesito_'+i).value!='9')
	{
	if(document.getElementById('risposta_asl_'+i).checked==false)
	{
		checkformAsl = false ;
	}
	if(document.getElementById('risposta_stap_'+i).checked==false && document.getElementById('note_stap_'+i).value=='' )
	{
		
		checkformStap = false ;
	}
	}

	if(document.getElementById('risposta_stap_'+i).checked==false && document.getElementById('note_stap_'+i).value!='' )
	{
		
		ritornoPratica = true ;
	}
	
}


if(roleId ==40  ) // stap
{
	if (checkformStap==false)
		document.getElementById('stato_stap').value = '0' ;
	else
		document.getElementById('stato_stap').value = '1' ;
	if (document.getElementById('stato_stap').value=='0')
	{
		alert('Per salvare il controllo in maniera definitiva occorre aver spuntato tutti i documenti o aver inserito un motivo ') ;
	}
	else
	{
		if (ritornoPratica == true && document.getElementById('stato_stap').value =='1')
		{
			alert('Attenzione! poiche l\'esito dell\'esame di qualche documento non e\' favorevole la pratica e\' salvata e rinviata per la rivaluzazione da parte dell\'asl');
			form.submit();
			}
		else
		{
			form.submit();
		}
	}
	
}
else
{
	
	if (checkformAsl==false)
		document.getElementById('stato_asl').value = '0' ;
	else
		document.getElementById('stato_asl').value = '1' ;

	if (document.getElementById('stato_asl').value=='0')
	{
		alert('Per salvare il controllo in maniera definitiva occorre aver spuntato tutti i documenti') ;
	}
	else
	{

		form.submit();
	}
}


}

</script>

<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Soa.do"><dhv:label name="">Soa</dhv:label></a> > 
<a href="Soa.do?command=Search">Risultati Ricerca</a> >
<a href="Soa.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Scheda Soa</dhv:label></a> >
Controllo Documentale
</td>
</tr>
</table>

<br>
<%
String msg = (String) request.getAttribute("EsitoUpdate");
if (msg != null)
{
	if (msg.startsWith("Errore"))
	{
		%>
		<font color ="red" ><%=msg %></font>
		<%
	}
	else
	{
		%>
		<font color = "green" ><%=msg %></font>
		<%
		
	}
}

%>

<%@ include file="../initPage.jsp"%>

<%
if(OrgDetails.getTipoSoa()==2)
{
%>
<font color = "green" >La verifica dei Documenti è stata eseguita.</font>
<%	
}
else
{
%>
<form method="post" action="Soa.do?command=UpdateControlloDocumentale">
<input type = "hidden" name = "idStabilimento" value = "<%=OrgDetails.getId() %>">
<input type = "hidden" id = "numQuesiti" name = "numQuesiti" value = "<%=lista_quesiti.size()%>">
<input type = "hidden" name = "idControlloDocumentale" value = "<%=OrgDetails.getIdControlloDocumentale()%>">
<input type = "hidden" name = "stato_asl" id = "stato_asl" value = "<%=ListaQuesiti.getStatoAsl()%>">
<input type = "hidden" name = "stato_stap" id = "stato_stap" value = "<%=ListaQuesiti.getStatoStap()%>">

<table cellpadding="4" cellspacing="0" border="0" width="85%"
		class="details">
<tr>
<th colspan="4" align="left">Controllo Documentale su <%=OrgDetails.getName() %></th>
</tr>
<tr>
<th align="left" width="50%">Quesito</th>
<th width="10%">Esito Favorevole ASL</th>
<th width="10%">Esito Favorevole STAP</th>
<th width="15%">Note STAP</th>
</tr>
<%

boolean disabled_stap = false ;
boolean disabled_asl = false  ;



if (user.getRoleId()==40)
{
	disabled_asl = true ;
	if ((OrgDetails.getStatoIstruttoria()!=StatiStabilimenti.RICHIESTA_RICONSOCIMENTO_CONDIZIONATO && 
			OrgDetails.getStatoIstruttoria()!=StatiStabilimenti.RICHIESTA_RICONSOCIMENTO_DEFINITIVO
	) || ListaQuesiti.getStatoStap()==1  )
	{
		disabled_stap = true ;
		

	}
	
}

if (user.getRoleId()!=40)
{
	disabled_stap = true ;
	if ((OrgDetails.getStatoIstruttoria() !=StatiStabilimenti.ISTRUTTORIA_PRELIMINARE 
			&& OrgDetails.getStatoIstruttoria()!=StatiStabilimenti.DOCUMENTAZIONE_COMPLETATA && OrgDetails.getStatoIstruttoria()!=StatiStabilimenti.ISTRUTTORIA_ESISTENTE  ) || (ListaQuesiti.getStatoAsl()==1   )  )
	{
		disabled_asl = true ;

	}
	
}


Iterator it = lista_quesiti.iterator();
int i = 0 ;
String modificato_da_asl= "" ;
String modificato_da_stap= "" ;
String note_stap = "" ;
Boolean competenzaAsl= true ;
while (it.hasNext())
{
	CustomLookupElement cle = (CustomLookupElement) it.next();
	modificato_da_asl = cle.getValue("modificato_da_asl");
	modificato_da_stap= cle.getValue("modificato_da_stap");
	note_stap = cle.getValue("note_stap");
	competenzaAsl = Boolean.parseBoolean(cle.getValue("competenza_asl"));

%>
<input type = "hidden" name = "competenzaAsl_<%=i %>" value = "<%=competenzaAsl %>"/>

<tr  class="containerBody">
<td  class="formLabel" width="50%">
<input type = "hidden" id = "id_quesito_<%=i %>" name = "id_quesito_<%=i %>" value = "<%=cle.getValue("id_quesito") %>">
<%=cle.getValue("quesito") %>

</td>
<td width="10%" align="center">
<input type="checkbox" id = "risposta_asl_<%=i %>" name = "risposta_asl_<%=i %>" <%if(disabled_asl==true || competenzaAsl==false ) {%>disabled="disabled" <% }%> value="1" <%if (Boolean.parseBoolean( cle.getValue("risposta_asl"))==true || competenzaAsl==false) {%>checked="checked"<%} %>> 

</td>
<td width="10%" align="center">

<input type="checkbox" id = "risposta_stap_<%=i %>" name = "risposta_stap_<%=i %>"  <%if(disabled_stap == true ) {%>disabled="disabled"<% }%>  value="1" <%if (Boolean.parseBoolean( cle.getValue("risposta_stap"))==true) {%>checked="checked"<%} %>>
 </td>
 <td width="15%" align="center">

<textarea rows="5" cols="30" id = "note_stap_<%=i %>"  name = "note_stap_<%=i %>" <%if(disabled_stap == true ) {%>disabled="disabled"<% }%> value = "<%=toHtml2(note_stap) %>"><%=toHtml2(note_stap) %></textarea>
 </td>
 
</tr>


<%
i++ ;
}

%>
</table>
<br>
<%=(modificato_da_asl!=null) ? "Modificato da Utente ASl :"+modificato_da_asl : ""%>



<%
/*SE LO STATO è PRELIMINARE è POSSIBILE FARE MODIFICHE E SALVARLE*/
if (disabled_asl==false  && user.getRoleId()!=40)
{
	%>
	<input type = submit value = "Salva Temporanea"/>
	
	<input type = "button" value = "Salva Definitiva" onclick="doSubmit(<%=user.getRoleId() %>)"/>
	
	<%
}
if (disabled_stap==false && user.getRoleId()==40)
{
	%>
<input type = submit value = "Salva Temporanea"/>
	
	<input type = "button" value = "Salva Definitiva" onclick="doSubmit(<%=user.getRoleId() %>)"/>
	<%
}

%>

</form>
<br>
<%if(ListaQuesiti.getUtenteAsl()!= null && ! "".equals(ListaQuesiti.getUtenteAsl()))
{
%>
<b><%="Modificato da ASL : "+ListaQuesiti.getUtenteAsl() %></b>
<%} %>
<%if(ListaQuesiti.getUtenteAsl()!= null && ! "".equals(ListaQuesiti.getUtenteStap()))
{
%>
<b><%="Modificato da STAP : "+ListaQuesiti.getUtenteStap() %></b>
<%} }%>

