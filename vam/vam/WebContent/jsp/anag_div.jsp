<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.bean.vam.Animale"%>
<%@page import="it.us.web.constants.SpecieAnimali"%>
<%@page import="it.us.web.constants.Specie"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="it.us.web.bean.vam.lookup.LookupMantelli"%>
<%@page import="it.us.web.bean.vam.lookup.LookupRazze"%>
<%@page import="it.us.web.bean.vam.lookup.LookupTaglie"%>
<%@page import="it.us.web.bean.vam.AnimaleAnagrafica"%>
<%@page import="it.us.web.bean.vam.Accettazione"%>




<%
int idAcc = (Integer.parseInt(request.getParameter("idAcc")));
int idA = (Integer.parseInt(request.getParameter("idA")));
int idS = (Integer.parseInt(request.getParameter("idS")));
String redirectTo = request.getParameter("redirectTo");
String act = request.getParameter("act");

int idR = (Integer.parseInt(request.getParameter("idR")));
int idT = (Integer.parseInt(request.getParameter("idT")));
int idM = (Integer.parseInt(request.getParameter("idM")));
String sesso = request.getParameter("sesso");
boolean dec_no_anag = (Boolean.parseBoolean(request.getParameter("dec_no_anag")));

Iterator<LookupRazze> iterRazzeCane = ((List<LookupRazze> )request.getServletContext().getAttribute("razzeCane")).iterator(); 
Iterator<LookupRazze> iterRazzeGatto = ((List<LookupRazze> )request.getServletContext().getAttribute("razzeGatto")).iterator(); 
Iterator<LookupMantelli> iterMantelliCane = ((List<LookupMantelli> )request.getServletContext().getAttribute("mantelliCane")).iterator(); 
Iterator<LookupMantelli> iterMantelliGatto = ((List<LookupMantelli> )request.getServletContext().getAttribute("mantelliGatto")).iterator(); 
Iterator<LookupTaglie> iterTaglie =  ((List<LookupTaglie>)request.getServletContext().getAttribute("taglie")).iterator();

%>

<form id="formAnagraficaAnimale" name="formAnagraficaAnimale" action="<%=act%>" method="post">	
	<input type="hidden" name="idAnimale" value="<%=idA%>" />
	<input type="hidden" name="redirectTo" value="<%=redirectTo%>" />
	<input type="hidden" name="idSpecie" id="idSpecie"  value="<%=idS%>" />
	<input type="hidden" name="idAccettazione" value="<%=idAcc%>" />
	
	 <table class="tabella">		
		
		<tr>
        	<th colspan="3">
        		Anagrafica Animale
        	</th>
        </tr>
    
	    <%if ((idS==1 || idS==2) && !dec_no_anag){%>   
    		<tr>
        		<td colspan="3">
        			<font color="red">Attenzione!!! Le modifiche ai dati saranno apportate anche in BDU</font><br/>
    			</td>
    		</tr>
    	<%} %>
    	
    	<% if(idS!=3){ %>
    	<tr class='even'>
    		<td>
				Razza
    		</td>
    		<td>
				<select id="razza" name="razza">
					<option value="-1">-- Seleziona --</option>
<%
					if(idS==1)
					{
					while(iterRazzeCane.hasNext())
					{
						LookupRazze temp = iterRazzeCane.next();
%>
							<option value="<%=temp.getId()%>"
							><%=temp.getDescription()%></option>
<%
					}
					}


					if(idS==2)
					{
					while(iterRazzeGatto.hasNext())
					{
						LookupRazze temp = iterRazzeGatto.next();
%>
							<option value="<%=temp.getId()%>"
							><%=temp.getDescription()%></option>
<%
					}
					}
%>
				</select>
    		</td>
    		<td>
    		</td>
        </tr>
        <%} %>
    
   
        <tr class='odd'>
    		<td>
    			Sesso 
    		</td>
    		<td>
    			<select name="sesso">
					<option value="M"
						<% if(sesso.equals("M")) {%>
							selected="selected" <%} %>
					>Maschio</option>
					<option value="F"
						<% if(sesso.equals("F")) {%>
							selected="selected" <%} %>
					>Femmina</option>
					<% if(idS==3){%>
					<option value="ND"
						<%if (sesso.equals("ND")){ %>
							selected="selected"
						<%} %>
					>Non Definito</option>
					<%} %>
				</select>
    		</td>
    		<td>
    		</td>
        </tr>
        
        <% if(idS==1){%>
        <tr class='even'>
    		<td>
    			Taglia 
    		</td>
    		<td>
    			<select name="idTaglia" id="idTaglia">
					<option value="-1">-- Seleziona --</option>
					<%
						while (iterTaglie.hasNext()){
							LookupTaglie tmp = iterTaglie.next();%>
							<option value="<%=tmp.getId()%>"
					<%	if  (tmp.getId()==idT){ %>
								selected="selected"	
					<% 		} %>
							><%=tmp.getDescription() %></option>
					<%	} %>
				</select>
    		</td>
    		<td>
    		</td>
        </tr>
        <%} %>

    
   	<% if(idS==1){%>
        <tr class='even'>
    		<td>
    			Mantello 
    		</td>
    		<td>
    		<div id="mantelli_cane" name="mantelli_cane">
    			<select name="mantelloCane" id="mantelloCane">
					<option value="-1">-- Seleziona --</option>
					<%
						while (iterMantelliCane.hasNext()){
							LookupMantelli tmp = iterMantelliCane.next();%>
							<option value="<%=tmp.getId()%>"
							><%=tmp.getDescription() %></option>
					<%	} %>
				</select>
			</div>
    		</td>
    		<td>
    		</td>
        </tr>
        <%} %>			
        
        <% if(idS==2){%>
        <tr class='even'>
    		<td>
    			Mantello 
    		</td>
    		<td>
    		   <div id="mantelli_gatto" name="mantelli_gatto">
    			<select name="mantelloGatto" id="mantelloGatto">
					<option value="-1">-- Seleziona --</option>
					<%
						while (iterMantelliGatto.hasNext()){
							LookupMantelli tmp = iterMantelliGatto.next();%>
							<option value="<%=tmp.getId()%>"
							><%=tmp.getDescription() %></option>
					<%	} %>
				</select>
				</div>
    		</td>
    		<td>
    		</td>
        </tr>
        <%} %>	

      </table>
      <script type="text/javascript">
      
<%
if(idS==1)
{
%>
document.getElementById('mantelloCane').value='<%=idM%>';
<%
}
else
{
%>
document.getElementById('mantelloGatto').value='<%=idM%>';
<%
}
%>
document.getElementById('razza').value='<%=idR%>';
</script>
      
      
    </form>	
    
    
    
    
    
