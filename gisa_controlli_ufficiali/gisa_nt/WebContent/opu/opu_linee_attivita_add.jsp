<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>
<%@page import="java.util.Iterator"%>
<jsp:useBean id="ServizioCompetente" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="LookupTipoAttivita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Carattere" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="Operatore" class="org.aspcfs.modules.opu.base.Operatore" scope="request" />
<jsp:useBean id="newStabilimento" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request" />

<script>
function deleteLineaProduttiva(indice,fieldPrincipale)
{
	
	if (fieldPrincipale.checked)
		{
		alert('Linea Attivita\' Primaria: non e\' possibile effettare cambiamenti'.toUpperCase())
		}
	else
		{
	
	var element = document.getElementById("idLineaProduttiva"+indice);
	element.value='-1';
	
	document.forms[0].doContinueStab.value = 'false';
	document.forms[0].submit();
		}
}

</script>

<a href="javascript:popUp('<%=newStabilimento.getAction() %>.do?command=SearchLineaProduttiva&popup=true&tipoSelezione=multipla');">
Aggiungi Linea Produttiva</a>
<table cellpadding="4" cellspacing="0" border="0" width="100%"	class="details">
	<tr>
		<th colspan="7">
			<strong>Linee Produttive</strong>
		</th>
	</tr>
	
	<tr class="details">
	
		<th ><strong>Attivita</strong>
		</th>
		
		
		<th ><strong>Data Inizio</strong>
		</th>
		<th ><strong>Data Fine</strong>
		</th>
		<th  ><strong>Principale</strong></th>
		<th  ><strong>Operazione</strong></th>
	</tr>
	
	<dhv:evaluate if="<%= (newStabilimento.getListaLineeProduttive().size()>0) %>">
	
	<% 
		Iterator<LineaProduttiva> itLplist = newStabilimento.getListaLineeProduttive().iterator() ;
		int indice = 1 ;
		while(itLplist.hasNext())
		{
			LineaProduttiva lp = itLplist.next();
			if (lp.getId()>0)
			{
	%>
		<input type="hidden" name="idLineaProduttiva"
			id="idLineaProduttiva<%=indice %>"
			value="<%=lp.getId() %>">
	<tr>
	
	<td><%=lp.getMacrocategoria()+" "+lp.getAttivita() %></td>
	
	<td >
		<input readonly type="text" id="dataInizio<%=lp.getId() %>" name="dataInizio<%=lp.getId() %>" size="10" value = "<%=lp.getDataInizioasString() %>" />
		<a href="#" onClick="cal19.select(document.forms[0].dataInizio<%=lp.getId() %>,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
        
	</td>
	<td >
	<input readonly type="text" id="dataFine<%=lp.getId() %>" name="dataFine<%=lp.getId() %>" size="10" value = "<%=lp.getDataFineasString() %>"/>
		<a href="#" onClick="cal19.select(document.forms[0].dataFine<%=lp.getId()%>,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
	</td>
	<td style="display:none">Attivo<input type = "hidden" name = "stato<%=lp.getId() %>" value = "0"></td>
	
	<td style="display:none"><%=Carattere.getHtmlSelect("tipo_attivita_produttiva"+lp.getId(),lp.getTipoAttivitaProduttiva()) %></td>
	<td><input type = "radio" id = "principale<%=lp.getId() %>" name = "principale" value = "<%=lp.getId() %>" <%=(indice==1) ? "checked" : "" %>/></td>
	<td><input type = "button" value = "Elimina" onclick="deleteLineaProduttiva(<%=indice%>,document.getElementById('principale<%=lp.getId()%>'))"/></td>
	
		</tr>
			<%
		indice ++ ;
			}
		} %>
		
	</dhv:evaluate>
	<input type = "hidden" name = "numLineeProduttive" value = "<%=newStabilimento.getListaLineeProduttive().size()%>">
		<input type="hidden" name="dataInizio" id="dataInizio" value="">
		<input type="hidden" name="dataFine" id="dataFine" value="">
		<input type="hidden" name="stato" id="stato" value="">
		<input type="hidden" name="idLineaProduttiva" id="idLineaProduttiva" value="">
		<input type = hidden name = "flagDia" value = "<%=newStabilimento.isFlagDia() %>"/>
	
</table>




