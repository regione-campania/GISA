<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

	
<%@page import="org.aspcfs.utils.web.LookupList"%>
<%@page import="org.aspcfs.modules.login.beans.UserBean"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.aspcfs.modules.parafarmacie.base.RigaAllegatoI"%>
		
<%@page import="java.util.Iterator"%><link rel="stylesheet" type="text/css" href="css/jmesa.css"></link>
		
		<script type="text/javascript" src="javascript/jquery.bgiframe.pack.js"></script>
		<script type="text/javascript" src="javascript/jquery.jmesa.js"></script>
		<script type="text/javascript" src="javascript/jmesa.js"></script>
	
	
<%

LookupList asl = (LookupList)request.getAttribute("aslList");
HashMap<Integer,String> aslListMap = (HashMap<Integer,String>)request.getAttribute("aslListMap");
LookupList righeAllegato = (LookupList)request.getAttribute("righeAllegato");
%>	
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Parafarmacie.do?command=SearchFormFcie"><dhv:label name="">Farmacie / Grossisti / Parafarmacie</dhv:label></a> > 
<a href="Parafarmacie.do?command=SelectAllegatoI"><dhv:label name="">Allegato I</dhv:label></a> >
Scheda Rilevazione Dati Attivita' di Farmacie / Grossisti / Parafarmacie 

</td>
</tr>
</table>
<br>

		<%
		UserBean user =(UserBean)session.getAttribute("User");
		%>
	
		<%if (user.getSiteId()!=-1)
		{
		%>
		
		
		<table>
		<tr>
		<td>
		<table align="left">
			<tr><td><a href = "Parafarmacie.do?command=StampaAllegatoI&asl=<%=user.getSiteId() %>&anno=<%=request.getAttribute("anno") %>">Stampa Report</a></td></tr>
			</table>
			
			
			<br/>
			<center><table>
		<tr><td colspan="2">
		<h2><center>Allegato I</center></h2> <br>
		</td></tr>
		</table></center>
		<table align="left">
		<tr style= "background-color: #E3E3E3">
		<td colspan="118">
		
		<table align = "left" width="100%" border="1px;">
		<!-- tr><td colspan="2"><center>
		<h2>Allegato I</h2> <br>
		
		</center></td></tr-->
		<tr><td colspan="2">
		<h3><center>Scheda Rilevazione Dati Attivita' di Parafarmacie </center></h3>
		</td></tr>
		<tr><td colspan="1"><b>REGIONE: CAMPANIA</br>
		ANNO: <%=request.getAttribute("anno") %></br>
		ENTE: <%=asl.getSelectedValue(Integer.parseInt((String)request.getAttribute("idAsl"))) %></b></td>
		<td>(*)ai sensi del Dlvo 193/2006 e 158/2006
		</br>(**)riportare a parte i dettagli dei riferimenti penali o amministrativi
		</br>(***)riportare a parte le tipologie di non conformità riscontrate
		</br>(****)riportare a parte l'elenco dei prodotti sequestrati(tipologia e quantità)
		</td></tr>		
		</table>
		<br>
		<center>
		
		<%
		if(request.getAttribute("errore")!=null){
			
			%>
			<font color="red" >
			<%=request.getAttribute("errore")+"  <br>" %>
			</font>
			
			
			<% 
			
		}
		
		%>
		
		
		</center>
		</td>
		</tr>
		<tr>
			<td colspan="2">
			<form name="aiequidiForm" action="Parafarmacie.do?command=AllegatoI">
	      <input type = "hidden" name = "asl" value = "<%=request.getAttribute("idAsl")%>">
	      <input type = "hidden" name = "anno" value = "<%=request.getAttribute("anno")%>">
	        <input type = "hidden" name = "comando" value = "salva">
	       <%=request.getAttribute( "tabella" )%>
	    </form>


	<script type="text/javascript">
            function onInvokeAction(id) {
                $.jmesa.setExportToLimit(id, '');
                $.jmesa.createHiddenInputFieldsForLimitAndSubmit(id);
            }
            function onInvokeExportAction(id) {
                var parameterString = $.jmesa.createParameterStringForLimit(id);
                location.href = 'Parafarmacie.do?command=AllegatoI' + parameterString;
            }
    </script>
			
			</td>
			
			</tr>
		
		</table>
		</td></tr></table>
		<br>
		<%
		Integer stato = (Integer) request.getAttribute("stato");
		if(stato==0)
		{
		%>
		<a href="Parafarmacie.do?command=ChiudiAllegatoI&asl=<%=user.getSiteId() %>&anno=<%=request.getAttribute("anno")%>" title="Attenzione chiudendo la pratica non sarà piu possibile modificare i valori."> Chiudi Pratica</a> 
		<%} %>
		<%
		if(request.getAttribute("errore")!=null){
			
			%>
			
			<script>
			
			alert("Attenzione!! I tipi di dati inseriti non sono corretti.");
			</script>
			
			<% 
			
		}
		
		%>
		
		
		<%}
		else
		{
HashMap<Integer,ArrayList<RigaAllegatoI>> collezione = (HashMap<Integer,ArrayList<RigaAllegatoI>>) request.getAttribute("AllegatoI");
			
			%>
			<table align="left" >
			<tr><td><a href = "Parafarmacie.do?command=StampaAllegatoI&anno=<%=request.getAttribute("anno") %>">Stampa Report</a></td></tr>
			</table>
			<br/>
			<center><table>
		<tr><td colspan="2">
		<h2><center>Allegato I</center></h2> <br>
		</td></tr>
		</table></center>
		<table align="left">
		<tr style= "background-color: #DBE6F2">
		<td colspan="118">
		
		<table align = "left" width="100%" border="1px;">
		<!-- tr><td colspan="2"><center>
		<h2>Allegato I</h2> <br>
		
		</center></td></tr-->
		<tr><td colspan="2">
		<h3><center>Scheda Rilevazione Dati Attivita' di Parafarmacie </center></h3>
		</td></tr>
		<tr><td colspan="1"><b>REGIONE: CAMPANIA</br>
		ANNO: <%=request.getAttribute("anno") %></br>
		ENTE: AASSLL</b></td>
		<td>(*)ai sensi del Dlvo 193/2006 e 158/2006
		</br>(**)riportare a parte i dettagli dei riferimenti penali o amministrativi
		</br>(***)riportare a parte le tipologie di non conformità riscontrate
		</br>(****)riportare a parte l'elenco dei prodotti sequestrati(tipologia e quantità)
		</td></tr>		
		</table>
		</td>
		</tr >
		<tr >
			<td colspan="2">
				<!-- COSTRUZIONE TABELLA PER TUTTE LE ASL -->
			<table border ="1px" >
			<tr style= "background-color: #729FCF">
			<td>&nbsp;</td>
			<td width="39" colspan="7" ><center><b>N. Operatori</b></center></td>
			<td width="39" colspan="7" ><center><b>N. Ispezioni</b></center></td>
			<td width="39" colspan="7" ><center><b>(*)(**)Violazioni Amm.</b></center></td>
			<td width="39" colspan="7" ><center><b>(*)(**)Denunce Giudiz.</b></center></td>
			<td width="39" colspan="7" ><center><b>(*)(****)Sequestri Amm.</b></center></td>
			<td width="39" colspan="7" ><center><b>(*)(****)Sequestri Giud.</b></center></td>
			<td width="39" colspan="7" ><center><b>(***)Non Conformita Camp.</b></center></td>
			<td width="39" colspan="7" ><center><b>N. Oper. sott. a cu(Cont.>1)</b></center></td>
			<td width="39" colspan="7" ><center><b>N. Oper. sott. a cu(Cont.>2)</b></center></td>
			</tr>
			<tr style= "background-color: #C0D5E9">
			<td>&nbsp;</td>
			<%for (int i = 1 ; i<10; i++)
			{
				for (int j = 201 ; j<=207 ; j++)
				{
					String aslCorrente = aslListMap.get(j);
					String temp = "" ;
					for (int k = 0 ; k<aslCorrente.length(); k++){
						
						temp = temp + "" +aslCorrente.charAt(k)+"<br>";
					}
					%>
					<td width="10" align="center" ><%=temp %></td>
					<%
				}
			}
				
				%>
			
			
			</tr>
			
			<!-- STAMPA DATI TABELLONE -->
			
			<%for (int i = 1 ; i<21; i++) // scorro le righe prese dalla lookup allegatoi_righe
			{
				%>
				<tr >
				<td ><%=righeAllegato.getSelectedValue(i) %></td>
				
				<%
				for (int k = 1 ; k< 10; k++) // scorro le 9 colonne 
				{
				
				for (int j = 201 ; j<=207 ; j++) //per ogni colonna prendo i valori delle 13 asl
				{
					ArrayList<RigaAllegatoI> lista =  collezione.get(j);
					RigaAllegatoI rigaTemp = new RigaAllegatoI();
					if(!lista.isEmpty())
					{
						rigaTemp = lista.get(i-1);
						
						
					}
					%>
				<td <%=((k%2==0)?("style= 'background-color: #EEF3F9'"):("")) %>>
				<%
				switch (k)
				{
				
				case 1 :{
					
					out.print(rigaTemp.getN_operatori());
					break;
				}
				case 2 :{
					
					out.print(rigaTemp.getN_ispezioni_effettuate());
					break;
				}
				case 3 :{
					
					out.print(rigaTemp.getViolazioni_amministrative());
					break;
				}
				case 4 :{
					
					out.print(rigaTemp.getDenunce_aut_giusiziarie());
					break;
				}
				case 5 :{
					
					out.print(rigaTemp.getSequestri_amministrativi());
					break;
				}
				case 6 :{
					
					out.print(rigaTemp.getSequestri_giudiziari());
					break;
				}
				case 7 :{
					
					out.print(rigaTemp.getNon_conformita_campionamento());
					break;
				}
				case 8 :{
					
					out.print(rigaTemp.getN_operatori_piu_uno_controlli());
					break;
				}
				case 9 :{
					
					out.print(rigaTemp.getN_operatori_piu_due_controlli());
					break;
				}
				
				}
				
				}
				%>
				
				</td>
				
					
					<%}
				%>
				</tr>
				<%
				}%>
			
			</table>
			
			
			
			
			</td>
			</tr></table>
			<br><br>
			<table align="center" border="1">
			<tr class="details"><td colspan="2"><b>Stato Allegato I per Asl</b></td></tr>
			<%
				
				for(int i = 201 ; i<=207 ; i++)
				{
					
					String nextAsl = asl.getValueFromId(i);
					
			%>
			<tr><td><%=nextAsl %></td>
			<td><%
					if(collezione.get(i) !=null &&  ! collezione.get(i).isEmpty())
					{
						if(collezione.get(i).get(1).getStato()!=0)
						{
							out.print("<font color = 'red'>DEFINITIVO</font>");
						}
						else
						{
							out.print("<font color = 'green'>TEMPORANEO</font>");
						}
					}
					else
					{
						out.print("<font color = 'green'>TEMPORANEO</font>");
					}
			
			
					
				%>
				</td></tr>
			<%
				}
			%>
			
			</table>
			
			<%
			
		}%>
		<br>
		
		