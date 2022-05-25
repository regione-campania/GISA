<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@page import="org.aspcfs.modules.gestoriacquenew.base.*"%>
<%@ include file="../initPage.jsp" %>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="gestori" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="GestoreAcque" class="org.aspcfs.modules.gestoriacquenew.base.GestoreAcque" scope="session" /> <!-- vuoto se non è stato trovato per l'user id quel gestore -->

<body>
<script src="gestoriacquedirete/script.js"></script>

<%if(User.getRoleId()!=Role.role_admin_ext && User.getRoleId()!=Role.RUOLO_REGIONE && User.getRoleId()!=Role.RUOLO_ORSA && User.getRoleId()!=Role.HD_1LIVELLO && User.getRoleId()!=Role.HD_2LIVELLO) {
	 if(GestoreAcque.getId()<=0)
	 {
%>
	<!-- non e' stato trovato gestore acque per l'user id dell'utente loggato -->
	<br>
	<br>
	<center>
	<font color="red"> Attenzione, per l'utente <%=User.getUsername() %> non e' stato trovato un Gestore Acque Di Rete Associato</font>
	</center>

<%}
}
else
	{

%>	
Selezionare il gestore acque con cui lavorare:<br/>
<form method="post" action="StabilimentoGestoriAcqueReteNewAction.do?command=SelezionaGestore" onSubmit="if(document.getElementById('idGestore').value==''){alert('Selezionare il gestore');return false;}else{this.submit();loadModalWindow();return true;};">
	<select name="idGestore" id="idGestore">
		<option value="">&lt;--- Selezionare gestore ---&gt;</option>
<%
		Iterator<GestoreAcque> gestoriIter = gestori.iterator();
		while(gestoriIter.hasNext())
		{
			GestoreAcque gestore = gestoriIter.next();
%>
			<option value="<%=gestore.getId()%>"
<%
			if(gestore.getId()==GestoreAcque.getId())
			{
%>			
				selected="selected"
<%
			}
%>
			><%=gestore.getNome()%></option>
<%
		}
%>
		<option value="-999"
<%
			if(-999==GestoreAcque.getId())
			{
%>
				selected="selected"	
<%
			}
%>
		>TUTTI</option>
	</select>
	<input type="submit" value="Seleziona">
</form>	
<%

	}
  if(GestoreAcque.getId()>0 || GestoreAcque.getId()==-999)  
  {%>
	
	<br>
	<br>
	<!-- e' stato trovato gestore acque per l'user id dell'utente loggato -->
	<center>
	<div style="max-width:1500px;">
	 <dhv:container name="datigestoreacque_detail" selected="Scheda Gestore Acque" object=""  param="">
	<fieldset>
<%
	if(GestoreAcque.getId()==-999)
	{
%>
	 	<center><b>NESSUN GESTORE ACQUE SPECIFICO SELEZIONATO</b></center>
<%
	}
	else
	{
%>
		<center><b>DENOMINAZIONE GESTORE UNIVOCA:</b> &nbsp;&nbsp;<%=GestoreAcque.getNome() %></center> 
<%
	}
%>
	 
	</fieldset>
		
		<%for(int i = 0; i< GestoreAcque.getAnagraficheDelGestore().size(); i++) {
			AnagraficaGestore anag = GestoreAcque.getAnagraficheDelGestore().get(i);
		%>
			<fieldset>
			<legend>Dati Anagrafici</legend>
				<table class="details">
					  <tr>
						<td style="width: 250px;"><b>ASL</b></td>
						<td align="left">&nbsp;&nbsp;<%=printSafe(anag.getDescrizioneAsl())  %></td>
					</tr>
					<tr>
						<td style="width: 250px;"><b>COMUNE</b></td>
						<td align="left">&nbsp;&nbsp;<%=printSafe(anag.getDescrizioneComune())  %></td>
					</tr>
					<tr>
						<td style="width: 250px;"><b>PROVINCIA</b></td>
						<td align="left">&nbsp;&nbsp;<%=printSafe(anag.getProvincia())  %></td>
					</tr>
					<tr>
						<td style="width: 250px;"><b>TELEFONO</b></td>
						<td align="left">&nbsp;&nbsp;<%=printSafe(anag.getTelefono())  %></td>
					</tr>
					<tr>
						<td style="width: 250px;"><b>INDIRIZZO</b></td>
						<td align="left">&nbsp;&nbsp;<%=printSafe(anag.getIndirizzo())  %></td>
					</tr> 
					<tr>
						<td style="width: 250px;"><b>DOMICILIO DIGITALE</b></td>
						<td align="left">&nbsp;&nbsp;<%=printSafe(anag.getDomicilioDigitale())  %></td>
					</tr>
					<%-- <tr>
						<td style="width: 250px;"><b>RAPPRESENTANTE</b></td>
						<td align="left">&nbsp;&nbsp;<%=printSafe(anag.getNominativoRappLegale())  %></td>
					</tr> --%>
				</table>
			</fieldset>
			
			<br>
			<div>
				<font size="2" color="red">
				<center>Attenzione! Per il completamento e/o l'aggiornamento dei dati anagrafici del gestore occorre inviare i dati mediante pec al seguente indirizzo gisasuap@cert.izsmportici.it
				</center>
				</font>
			</div>
			
		<%} %>
		
		<%-- <fieldset>
		<legend>Comuni di Competenza Punti di Prelievo</legend>
			 
							<%
							  String tempStr = "&nbsp;&nbsp;";
							  int iR = 0; /*per far andare su newline dopo un tot */
							  for(String descComuneCompetenza : GestoreAcque.getComuniAccettatiPerPP().keySet())
							  {  
								iR++;
								tempStr+= (descComuneCompetenza +", "+(iR%4 == 0 ? "</br>" : "" ));
								
							  }
							  out.println(tempStr.substring(0,tempStr.length()-2));
							  
							  %>
				 
		</fieldset> --%>
		
	
	</dhv:container>
	</div>
	</center>
	

<%} %>

 
 
  <%! public String printSafe(String toPrint)
      {
      	if(toPrint == null )
      	{
      		toPrint = "-";
      	}
      	return toPrint;
      	
      }%>
 
   
	
<br/><br/>

 

		 

</body>
</html>