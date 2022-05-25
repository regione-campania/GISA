<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="org.aspcfs.modules.vigilanza.base.Ticket,java.util.*"%>
<%@page import="org.aspcfs.modules.vigilanza.base.NucleoIspettivo"%>
<%@page import="org.aspcfs.modules.contacts.base.Contact"%>


<%@page import="org.aspcfs.modules.oia.base.OiaNodo"%><input type ="hidden" name = "elementi" id = "elementi" value = "<%=TicketDetails.getNucleoasList().size()+1 %>">
<input type ="hidden" name = "elemento" id = "elemento" value = "1">
<input type ="hidden" name = "size" id = "size" value = "<%=TicketDetails.getNucleoasList().size()+1 %>"">

<tr class="containerBody" >

	<td valign="top" class="formLabel"><label id = "nucleo" >Nucleo Ispettivo </label></td>
	<td>
	<table class="empty">
		<%
		ArrayList<NucleoIspettivo> lista = TicketDetails.getNucleoasList();
			for (int i = 0; i < lista.size(); i++)
			{
				boolean vet_visibilita = false 	;
				boolean med_visibilita = false 	;
				boolean tpal_visibilita = false ;
				boolean altr_visibilita = false ;
				boolean ref_visibilita = false ;
				boolean amm_visibilita = false ;
				int nucleoCurr = lista.get(i).getNucleo();
				String componente = lista.get(i).getComponente();
				if (nucleoCurr == 1)
				{
					vet_visibilita = true;
				}
				else
				if (nucleoCurr == 2)
				{
					med_visibilita = true;
				}
				else
				if (nucleoCurr == 23)
				{
					tpal_visibilita = true;
				}
				else
				if (nucleoCurr == 24)
				{
					ref_visibilita = true;
				}
				else
				if (nucleoCurr == 25)
				{
					amm_visibilita = true;
				}
				else
					altr_visibilita = true;
				%>
		<tr id = "nucleoispettivo_<%=(i+1) %>">
			<td>
				<%
				String onchange = "onChange=prova("+(i+1)+")";
				TitoloNucleo.setJsEvent(onchange); %>
				<%= TitoloNucleo.getHtmlSelect("nucleo_ispettivo_"+(i+1),nucleoCurr) %>
			</td>
			<td>
				<%
   					HashMap<String,ArrayList<String>> listaSian=(HashMap<String,ArrayList<String>>)request.getAttribute("listaUtentiSian");
        			HashMap<String,ArrayList<Contact>> listaVet=(HashMap<String,ArrayList<Contact>>)request.getAttribute("listaUtenti");        
        			HashMap<String,ArrayList<String>> listaTpal=(HashMap<String,ArrayList<String>>)request.getAttribute("listaUtentiTpal");
        			HashMap<String,ArrayList<String>> listaRefe=(HashMap<String,ArrayList<String>>)request.getAttribute("listaUtentiReferenteAllerte");
        			HashMap<String,ArrayList<String>> listaAmm=(HashMap<String,ArrayList<String>>)request.getAttribute("listaUtentiAmministrativi");
            	%>
             	<select name="Veterinari_<%=(i+1) %>" id="Veterinari_<%=(i+1) %>" <%if (vet_visibilita==false) {%>style="display: none"<%} %> onchange="clona();  costruisci_uo(document.getElementById('uo_<%=i+1 %>'),this.value);">
					<option value="-1">Seleziona</option>
					<%
						Iterator<String> itVeterinari = listaVet.keySet().iterator();
					while (itVeterinari.hasNext())
					{
						String label = itVeterinari.next();
						%>
						<optgroup label="<%=label %>"></optgroup>
						<%
						ArrayList<Contact> listaUtenti = listaVet.get(label);
						for (Contact utente : listaUtenti)
						{
							%>
							<option value = "<%=utente.getNameLast()%>" <%if(utente.getNameLast().equals(componente)){ %>selected="selected"<%} %> ><%=utente.getNameLast() %></option>
							<%
						}
					}
					%>
				</select>
				<select name="Medici_<%=(i+1) %>" id="Medici_<%=(i+1) %>" <%if (med_visibilita==false) {%>style="display: none"<%} %> onchange="clona(); costruisci_uo(document.getElementById('uo_<%=i+1 %>'),this.value);">
					<option value="-1">Seleziona</option>
					<%
						Iterator<String> idMedici = listaSian.keySet().iterator();
					while (idMedici.hasNext())
					{
						String label = idMedici.next();
						%>
						<optgroup label="<%=label %>"></optgroup>
						<%
						ArrayList<String> listaUtenti = listaSian.get(label);
						for (String utente : listaUtenti)
						{
							%>
							<option value = "<%=utente %>" <%if(utente.equals(componente)){ %>selected="selected"<%} %>  ><%=utente %></option>
							<%
						}
					}
					%>
				</select>
				<select name="Tpal_<%=(i+1) %>" id="Tpal_<%=(i+1) %>" <%if (tpal_visibilita==false) {%>style="display: none"<%} %> onchange="clona(); costruisci_uo(document.getElementById('uo_<%=i+1 %>'),this.value);">
				
					<option value="-1">Seleziona</option>
					<%
					
						Iterator<String> itTpal = listaTpal.keySet().iterator();
					while (itTpal.hasNext())
					{
						String label = itTpal.next();
						%>
						<optgroup label="<%=label %>"></optgroup>
						<%
						ArrayList<String> listaUtenti = listaTpal.get(label);
						for (String utente : listaUtenti)
						{
							%>
							<option value = "<%=utente %>" <%if(utente.equals(componente)){ %>selected="selected"<%} %>  ><%=utente %></option>
							<%
						}
					}
					%>
				</select>
				
					<select name="Ref_<%=(i+1) %>" id="Ref_<%=(i+1) %>" <%if (ref_visibilita==false) {%>style="display: none"<%} %> onchange="clona(); costruisci_uo(document.getElementById('uo_<%=i+1 %>'),this.value);">
				
					<option value="-1">Seleziona</option>
					<%
						Iterator<String> itRef = listaRefe.keySet().iterator();
					while (itRef.hasNext())
					{
						String label = itRef.next();
						%>
						<optgroup label="<%=label %>"></optgroup>
						<%
						ArrayList<String> listaUtenti = listaRefe.get(label);
						for (String utente : listaUtenti)
						{
							%>
							<option value = "<%=utente %>" <%if(utente.equals(componente)){ %>selected="selected"<%} %>  ><%=utente %></option>
							<%
						}
					}
					%>
				</select>
				
					<select name="Amm_<%=(i+1) %>" id="Amm_<%=(i+1) %>" <%if (amm_visibilita==false) {%>style="display: none"<%} %> onchange="clona(); costruisci_uo(document.getElementById('uo_<%=i+1 %>'),this.value);">
				
					<option value="-1">Seleziona</option>
					<%
						Iterator<String> itAmm = listaAmm.keySet().iterator();
					while (itAmm.hasNext())
					{
						String label = itAmm.next();
						%>
						<optgroup label="<%=label %>"></optgroup>
						<%
						ArrayList<String> listaUtenti = listaAmm.get(label);
						for (String utente : listaUtenti)
						{
							%>
							<option value = "<%=utente %>" <%if(utente.equals(componente)){ %>selected="selected"<%} %>  ><%=utente %></option>
							<%
						}
					}
					%>
				</select>
				
				<select name = "uo_<%=i+1 %>" id = "uo_<%=i+1 %>">
				
				</select>
				<input type="text" name="Utente_<%=(i+1) %>" id="Utente_<%=(i+1) %>" size="20" maxlength="256" <%if(altr_visibilita){ %> value = "<%=componente %>"<%} %>  <%if (altr_visibilita==false) {%>style="display: none"<%} %> onchange="clona()" />
				<font color = "red"> * </font> 
			</td>
		</tr>
		<%} %>
		
<%if (lista.size()<10){ %>
<tr id = "nucleo_ispettivo" >
			<td>
			
				<%
				String onchange = "onChange=prova("+(lista.size()+1)+")";
				TitoloNucleo.setJsEvent(onchange); %>
				<%= TitoloNucleo.getHtmlSelect("nucleo_ispettivo_"+(lista.size()+1),-1) %>
			</td>
			<td>
				<%
   					HashMap<String,ArrayList<String>> listaSian=(HashMap<String,ArrayList<String>>)request.getAttribute("listaUtentiSian");
        			HashMap<String,ArrayList<Contact>> listaVet=(HashMap<String,ArrayList<Contact>>)request.getAttribute("listaUtenti");        
        			HashMap<String,ArrayList<String>> listaTpal=(HashMap<String,ArrayList<String>>)request.getAttribute("listaUtentiTpal");
        			HashMap<String,ArrayList<String>> listaRefe=(HashMap<String,ArrayList<String>>)request.getAttribute("listaUtentiReferenteAllerte");
        			HashMap<String,ArrayList<String>> listaAmm=(HashMap<String,ArrayList<String>>)request.getAttribute("listaUtentiAmministrativi");
            	%>
             	<select name="Veterinari_<%=lista.size()+1 %>" id="Veterinari_<%=lista.size()+1 %>" style="display: none" onchange="clona();mostraUo(<%=lista.size()+1 %>)">
					<option value="-1">Seleziona</option>
					<%
						Iterator<String> itVeterinari = listaVet.keySet().iterator();
					while (itVeterinari.hasNext())
					{
						String label = itVeterinari.next();
						%>
						<optgroup label="<%=label %>"></optgroup>
						<%
						ArrayList<Contact> listaUtenti = listaVet.get(label);
						for (Contact utente : listaUtenti)
						{
							%>
							<option value = "<%=utente.getNameLast() %>"  ><%=utente.getNameLast() %></option>
							<%
						}
					}
					%>
				</select>
				
				<select name="Medici_<%=lista.size()+1 %>" id="Medici_<%=lista.size()+1 %>" style="display: none" onchange="clona();mostraUo(<%=lista.size()+1 %>)">
					<option value="-1">Seleziona</option>
					<%
						Iterator<String> idMedici = listaSian.keySet().iterator();
					while (idMedici.hasNext())
					{
						String label = idMedici.next();
						%>
						<optgroup label="<%=label %>"></optgroup>
						<%
						ArrayList<String> listaUtenti = listaSian.get(label);
						for (String utente : listaUtenti)
						{
							%>
							<option value = "<%=utente %>"><%=utente %></option>
							<%
						}
					}
					%>
				</select>
				<select name="Tpal_<%=lista.size()+1 %>" id="Tpal_<%=lista.size()+1 %>" style="display: none" onchange="clona();mostraUo(<%=lista.size()+1 %>)">
				
					<option value="-1">Seleziona</option>
					<%
						Iterator<String> itTpal = listaTpal.keySet().iterator();
					while (itTpal.hasNext())
					{
						String label = itTpal.next();
						%>
						<optgroup label="<%=label %>"></optgroup>
						<%
						ArrayList<String> listaUtenti = listaTpal.get(label);
						for (String utente : listaUtenti)
						{
							%>
							<option value = "<%=utente %>"><%=utente %></option>
							<%
						}
					}
					%>
				</select>
				
				<select name="Ref_<%=lista.size()+1 %>" id="Ref_<%=lista.size()+1 %>" style="display: none" onchange="clona();mostraUo(<%=lista.size()+1 %>)">
				
					<option value="-1">Seleziona</option>
					<%
						Iterator<String> itRef = listaRefe.keySet().iterator();
					while (itRef.hasNext())
					{
						String label = itRef.next();
						%>
						<optgroup label="<%=label %>"></optgroup>
						<%
						ArrayList<String> listaUtenti = listaRefe.get(label);
						for (String utente : listaUtenti)
						{
							%>
							<option value = "<%=utente %>"><%=utente %></option>
							<%
						}
					}
					%>
				</select>
				
				<select name="Amm_<%=lista.size()+1 %>" id="Amm_<%=lista.size()+1 %>" style="display: none" onchange="clona();mostraUo(<%=lista.size()+1 %>)">
				
					<option value="-1">Seleziona</option>
					<%
						Iterator<String> itAmm = listaAmm.keySet().iterator();
					while (itVeterinari.hasNext())
					{
						String label = itAmm.next();
						%>
						<optgroup label="<%=label %>"></optgroup>
						<%
						ArrayList<String> listaUtenti = listaAmm.get(label);
						for (String utente : listaUtenti)
						{
							%>
							<option value = "<%=utente %>"><%=utente %></option>
							<%
						}
					}
					%>
				</select>
				<input type="text" name="Utente_<%=lista.size()+1 %>" id="Utente_<%=lista.size()+1 %>" size="20" maxlength="256"  style="display: none" onchange="clona();mostraUo(<%=lista.size()+1 %>)" />
				</td>
				<td>
				<font color = "red"> * </font> 
						<select name = "uo_<%=lista.size()+1 %>" id = "uo_<%=lista.size()+1 %>" style="display: none" >
					<option value = "-1">Seleziona Unit&agrave; Operativa</option>
					<%
					ArrayList<OiaNodo> lista_uo = (ArrayList<OiaNodo>)request.getAttribute("lista_uo");
					for(OiaNodo nodo : lista_uo)
					{
						%>
						<option value = "<%=nodo.getId() %>" ><%= nodo.toString()%></option>
						<%
						
					}
					%>
					
				</select>
				
			</td>
		</tr>
<%} %>
	</table>
	</td>
</tr>


