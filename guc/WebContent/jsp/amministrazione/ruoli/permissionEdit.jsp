<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage=""%>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="US" %>

<%@page import="java.util.*" %>

		
	<%
		Vector funzioni = (Vector)request.getAttribute("funzioni");
		Vector permessi = (Vector)request.getAttribute("permessi");
		BRuolo br = (BRuolo)request.getAttribute("ruolo");
		String funzione	= (String)request.getParameter("action");
		funzione = (funzione == null) ? ("") : (funzione);
		BUtente utente = (BUtente)session.getAttribute("utente");
	%>
    
  	<%
   		if(utente == null)
      	{
	%>
    			
<%@page import="it.us.web.bean.BRuolo"%>
<%@page import="it.us.web.bean.BUtente"%>
<%@page import="it.us.web.util.properties.Message"%>


<%@page import="it.us.web.bean.BFunzione"%>
<%@page import="it.us.web.util.properties.Label"%>
<%@page import="it.us.web.bean.BGuiView"%><div class="titolo"> <%=Message.getSmart("accesso_pagina_non_loggato")%> </div>

		<%
			}
   		%>
		
	<div class="titolo">Modifica Permessi per il ruolo "<%=br.getRuolo() %>"</div>
	 
	 <div class="area-contenuti-2">
	   	
		 <br/>
			<%
			if(funzioni != null)
			{
				Enumeration e = funzioni.elements();
			%>
			<form id="a2" action="xls.listaRuoli.do" >
				<p><input type="hidden" name="ruolo" value="<%=br.getRuolo() %>" /></p>
				<%-- <p><input type="submit" class="button" value="Export XLS Permessi Funzioni" /></p> --%>
			</form>
			
			<form id="a1" action="XXX" method="post">
				<p>
					<select name="FUNZIONI" size="1" onchange="selezionaFunzioneInModificaRuolo( this, '<%=br.getRuolo().replace( "'", "\\'" ).replace( "\"", "\\\"" )%>' )" >
						<option value="" selected="selected">Seleziona una funzione</option>
						<option value="tutte">TUTTE</option>
						<%
							while(e.hasMoreElements())
							{
								BFunzione bf = (BFunzione)e.nextElement();
						%>
								<option value="<%=bf.getId()+"" %>" <%=(bf.getId() + "").equalsIgnoreCase(funzione) ? ("selected=\"selected\"") : ("") %> >
									<%=Label.getSmart( bf.getNome() ) %>
								</option>
						<% 
							}
						%>
					</select>
				</p>
			</form>
			<br/>
			<%} %>
			<br/>
			 
			<% if( (permessi!=null) && (permessi.size()>0) )
			{
				Enumeration enp=permessi.elements();
			%>
			<form action="ruoli.PermissionEdit.us" method="post">
			
				<table width="95%" class="griglia">
				
				<tr>
					<th colspan="3" style="text-align:right;">Imposta tutti</th>
					<th width="20%" >
						<input type="button"  class="button" onclick="setAllRO();" value="RO"/>
						<input type="button"  class="button" onclick="setAllRW();" value="RW"/>
						<input type="button"  class="button" onclick="setAllNO();" value="NO"/>
					</th>
				</tr>
				
				<tr>
					<th>Funzione</th>
					<th>SubFunzione</th>
					<th>GUIObject</th>
					<th>Permessi</th>
				</tr>
				
				<%
				int index = 0;
				while( enp.hasMoreElements() )
				{ 
					BGuiView	bgv		= (BGuiView)enp.nextElement();
					int			id		= bgv.getId_gui();
					String		diritti	= bgv.getDiritti();
				%>
				
					<tr class="<%=(index%2 == 0) ? ("even") : ("odd") %>">
						<td><%= Label.getSmart( bgv.getNome_funzione() ) %> </td>
						<td><%= Label.getSmart( bgv.getNome_subfunzione() ) %> </td>
						<td><%= Label.getSmart( bgv.getNome_gui() ) %> </td>
						<td class="lunghezza" style="text-align: center" >
							<input id="ogRadio" type="radio" name="OG_<%=id%>" value="0" <%if((diritti!=null)&&(diritti.equals("r"))){%>checked="checked"<% }%> /><label for="ogRadioRO_<%=id%>">RO</label>
							<input id="ogRadio" type="radio" name="OG_<%=id%>" value="1" <%if((diritti!=null)&&(diritti.equals("w"))){%>checked="checked"<% }%> /><label for="ogRadioRW_<%=id%>">RW</label>
							<input id="ogRadio" type="radio" name="OG_<%=id%>" value="2" <%if((diritti==null)||(diritti.equals(""))){%>checked="checked"<% }%> /><label for="ogRadioNO_<%=id%>">NO</label>
						</td>
					</tr>
					
				<% index++; } %>
				
				<tr>
					<td colspan="4">
						<input class="button" type="submit" value="Aggiorna Permessi" onclick="attendere();"/>
						<input type="hidden" name="gruppo" value="<%=br.getRuolo().replace( "\"", "&quot;" )%>"/>
					</td>
				</tr>
				
			</table>
	</form>
			<% } %>
			
</div>
