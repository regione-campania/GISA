<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<link rel="stylesheet" type="text/css" href="css/jmesa.css"></link>
<script type="text/javascript" src="javascript/jquery.bgiframe.pack.js"></script>
<script type="text/javascript" src="javascript/jquery.jmesa.js"></script>
<script type="text/javascript" src="javascript/jmesa.js"></script>
<%@page import="org.aspcfs.modules.sintesis.base.SintesisRelazioneLineaProduttiva"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@page import="org.aspcfs.modules.gestoriacquenew.base.*"%>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="gestori" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="idAsl" class="java.lang.String" scope="request"/>
<jsp:useBean id="idGestore" class="java.lang.String" scope="request"/>
<jsp:useBean id="lookup_asl" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="GestoreAcque" class="org.aspcfs.modules.gestoriacquenew.base.GestoreAcque" scope="session" /> <!-- vuoto se non è stato trovato per l'user id quel gestore -->


<%Integer indiceChunkPuntiPrelievo =  request.getAttribute("indiceChunkPuntiPrelievo") == null ? 0 : (Integer)request.getAttribute("indiceChunkPuntiPrelievo"); %>


<%@ include file="../initPage.jsp" %>

<head>
	
	 <style>
	 	
	 	tr.tr_pp:hover
	 	{
	 		background-color: rgba(125,126,130,0.3);
	 	}
	 
	 </style>

</head> 
<body>
<script src="gestoriacquedirete/script.js"></script>
  <br>
   
<%
   	if(User.getRoleId()!=Role.role_admin_ext && User.getRoleId()!=Role.RUOLO_REGIONE && User.getRoleId()!=Role.RUOLO_ORSA && User.getRoleId()!=Role.HD_2LIVELLO  && User.getRoleId()!=Role.HD_1LIVELLO && GestoreAcque.getId()<=0 && GestoreAcque.getId()!=-999) 
   	{
%>
	<!-- non e' stato trovato gestore acque per l'user id dell'utente loggato -->
	<br>
	<br>
	<center>
<%
%>
		<font color="red"> Attenzione, per l'utente <%=User.getUsername() %> non e' stato trovato un Gestore Acque Di Rete Associato</font>

	</center>
<%
    }
	if((User.getRoleId()==Role.RUOLO_REGIONE || User.getRoleId()==Role.role_admin_ext || User.getRoleId()==Role.RUOLO_ORSA || User.getRoleId()==Role.HD_1LIVELLO || User.getRoleId()==Role.HD_2LIVELLO) && GestoreAcque.getId()<=0 && GestoreAcque.getId()!=-999) 
	{
%>
	<br>
	<br>
	<center>
	<%
	%>
		<font color="red"> Attenzione, selezionare un gestore acque per visualizzare i punti di prelievo</font>
	
	</center>
	<%
	}
   if(GestoreAcque.getId()>0 || GestoreAcque.getId()==-999) 
   {
   %>
  	
	  	<center><b>Lista Punti Prelievo 
<%
				if(User.getRoleId()!=Role.RUOLO_REGIONE && User.getRoleId()!=Role.role_admin_ext && User.getRoleId()!=Role.RUOLO_ORSA && User.getRoleId()!=Role.HD_1LIVELLO && User.getRoleId()!=Role.HD_2LIVELLO)
				{
%>
					per gestore: <%=GestoreAcque.getNome() %>
<%
				}
%>
</b></center><br>
	  	<center>
	  	
		<form method="post" action="StabilimentoGestoriAcqueReteNewAction.do?command=EstrazionePuntiPrelievo">
			<input type="submit" value="Estrai in Excel">
		</form>	
					
		<table  class="details" width="90%;">
		<form method="post" action="StabilimentoGestoriAcqueReteNewAction.do?command=PaginaPerListaPuntiPrelievo" >
			<tr>
				<th>ASL
					
						<%= lookup_asl.getHtmlSelect("idAsl", idAsl) %>
						<input type="submit" value="Seleziona">
				</th>
				<th>COMUNE</th>
				<th>LATITUDINE</th>
				<th>LONGITUDINE</th>
				<th>INDIRIZZO</th>
				<th>DENOMINAZIONE</th>
				<th>TIPOLOGIA</th>
				<th>STATO</th>
				<th>CODICE GISA</th>
				<th>CODICE</th>
				<th>DATA INSERIMENTO</th>
<%
				if(User.getRoleId()==Role.RUOLO_REGIONE || User.getRoleId()==Role.role_admin_ext || User.getRoleId()==Role.RUOLO_ORSA || User.getRoleId()==Role.HD_1LIVELLO || User.getRoleId()==Role.HD_2LIVELLO)
				{
%>
					<th>
						GESTORE
							<select name="idGestore" id="idGestore">
								<option value="-999"
<%
								if(-999==GestoreAcque.getId())
								{
%>
									selected="selected"	
<%
								}
%>
								>
									TUTTI
								</option>
<%
								Iterator<GestoreAcque> gestoriIter = gestori.iterator();
								while(gestoriIter.hasNext())
								{
									GestoreAcque gestore = gestoriIter.next();
%>
									<option value="<%=gestore.getId()%>"
<%
									if(idGestore!=null && !idGestore.equals("") && gestore.getId()==Integer.parseInt(idGestore))
									{
%>			
										selected="selected"
<%
									}
%>
									>
										<%=gestore.getNome()%>
									</option>
<%
								}
%>
								
							</select>
							<input type="submit" value="Seleziona">
					</th>
<%
				}
%>
			</tr>
		</form>	
			
		 <%for(PuntoPrelievo pp : GestoreAcque.getPuntiPrelievo())
		   {
		   if((idAsl==null || idAsl.equals("") || idAsl.equals("-1")  || idAsl.equals(pp.getIdAsl()+"")  ) && (idGestore==null || idGestore.equals("") || idGestore.equals("-999") || idGestore.equals("-1")  || idGestore.equals(pp.getIdGestore()+"")   ))
		   {
		   %>
				
				<tr class="tr_pp" style="cursor:pointer;" 
				 
				onclick="javascript: document.location.href = 'StabilimentoGestoriAcqueReteNewAction.do?command=PuntoPrelievoDetail&idGestore=<%=pp.getIdGestore()%>&idPuntoPrelievo=<%=pp.getId()%>';">
					<td><%=nullablePrint(pp.getDescrizioneAsl()) %></td>
					<td><%=nullablePrint(pp.getIndirizzo().getDescrizioneComune())%></td>
					<td><%=pp.getIndirizzo().getLatitudine()%></td>
					<td><%=pp.getIndirizzo().getLongitudine()%></td>
					<td><%=nullablePrint(pp.getIndirizzo().toString()) %></td>
					<td><%=nullablePrint(pp.getDenominazione()) %></td>
					<td><%=nullablePrint(pp.getDescrizioneTipologia()) %></td>
					<td><%=nullablePrint(pp.getStato()) %></td>
					<td><%=nullablePrint(pp.getCodiceGisa()) %></td>
					<td><%=nullablePrint(pp.getCodice()) %></td>
					<td><%=toDateWithTimeasString(pp.getDataInserimento()) %></td>
<%
					if(User.getRoleId()==Role.RUOLO_REGIONE || User.getRoleId()==Role.role_admin_ext || User.getRoleId()==Role.RUOLO_ORSA || User.getRoleId()==Role.HD_1LIVELLO || User.getRoleId()==Role.HD_2LIVELLO)
					{
%>
						<td><%=pp.getNomeGestore()%></td>
<%
					}
%>
				</tr>
			 
		  <%
		   }
		   }%>
		  
		 </table>
			
	 	<br>
	 	<div>
	 		<br>
	 		
	 		
	 	</div>
	 	
	 	</center>	
	 <% }%>
		
	
	
		
<br/><br/>

	<script>
	
		function aggiornaTab(form0,op,dom)
	    {
			 
			
			var inp0= document.createElement("input");
			inp0.type ="hidden";
			inp0.name="indiceChunkPuntiPrelievo";
			if(op == 'next' || op == 'prev')
			{
				/*il valore dell'indice che appendo al form e' l'indice chunk presente +1 o -1 (a seconda del tasto) */
				var step = (op == 'next') ? +1 : -1;
				inp0.value = (<%=indiceChunkPuntiPrelievo%>+  step    )+'';
			}
			else if(op == 'selectIndex')
			{
				var selectedIndex = +(dom.value);
				inp0.value = selectedIndex + '';
			}
			
			form0.appendChild(inp0);
			form0.submit();
	    }
	
	
	</script>
 
	
	

</body>
</html>