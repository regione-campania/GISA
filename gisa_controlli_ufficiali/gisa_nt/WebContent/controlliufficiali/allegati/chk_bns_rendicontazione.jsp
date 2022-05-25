<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="java.util.Iterator"%>
<%@page import="org.aspcf.modules.checklist_benessere.base.Rendicontazione"%>
<%@page import="java.util.ArrayList"%>
<jsp:useBean id="Rendicontazione" class="org.aspcf.modules.checklist_benessere.base.Rendicontazione" scope="request"/>
<jsp:useBean id="CapitoliList" class="java.util.ArrayList" scope="request"/>
<style type="text/css">
@print {
	table, td, th, li, p, ul, ol, textarea, input, select, a {
	font-size:10pt;
	}
}
</style>

<body>
<form method="post" name="myform" action="">
<P text-align="center" style="font-size:12pt; text-align: center">
	<b>Piano nazionale benessere animale<br>
	MODELLO RENDICONTATIVO<br>
	PROTEZIONE DEGLI ANIMALI NEGLI ALLEVAMENTI<br>
	DATI ANNUALI SPECIE <%= request.getAttribute("tipoSpecie") %><br>
	RISULTATI DEI CONTROLLI EFFETTUATI PRESSO LE AZIENDE<br>
	(D.Lgs. 146/2001 e succ. modifiche)<br>
	DECISIONE DELLA COMMISSIONE 2006/778/CE del 14 novembre 2006
	</b>
</P>
<table border="1" >
	<tr >
		<td colspan="2" style="text-align:center"> REGIONE CAMPANIA</td>
		<td style="text-align:center">ANNO<br><%= request.getAttribute("anno") %></td>
	</tr>
	<tr>
		<td>N. totale aziende di <%=request.getAttribute("tipoSpecie")%> soggette ad ispezione n.</td>
		<td>N. totale aziende di <%=request.getAttribute("tipoSpecie")%> controllate n.</td>
		<td>N. totale aziende di <%=request.getAttribute("tipoSpecie")%><br> ispezionate per le quali NON sono state<br>rilevate non conformità n. </td>
	</tr>
</table>
<br>
<B>LEGENDA</B>
<table border="1" >
	<tr>
		<td style="text-align:center">
			<b>Categoria delle non conformità</b>
		</td>
		<td>
			<b>AZIONI INTRAPRESE DALL'AUTORITA' COMPETENTE</b>
		</td>
	</tr>
	<tr>
		<td style="text-align:center">
			<b>A</b>
		</td>
		<td>
			Richiesta di rimediare alle non conformità entro un termine inferiore a 3 mesi. <br>
			Nessuna sanzione amministrativa o penale immediata.
		</td>
	</tr>
	<tr>
		<td style="text-align:center">
			<b>B</b>
		</td>
		<td>
			Richiesta di rimediare alle non conformità entro un termine superiore a 3 mesi.<br>
			 Nessuna sanzione amministrativa o penale immediata.
		</td>
	</tr>
	<tr>
		<td style="text-align:center">
			<b>C</b>
		</td>
		<td>
			Sanzione amministrativa o penale immediata.
		</td>
	</tr>
</table>
<br/>
<table cellpadding="9" cellspacing="0" border="1" width="100%" class="details">
  <tr class="containerBody">
    <th colspan="1" style="background-color: gray;">Requisito</th>
    <th colspan="1" style="background-color: gray;">Numero<br> totale irregolarità</th>
    <th colspan="3" style="background-color: gray;">N.dei provvedimenti<br>adottati di conseguenza<br>(per categoria di non conformità)</th>   
  </tr>
  <tr class="containerBody">
    <th></th>
    <th></th>
    <th>A</th>
    <th>B</th>
    <th>C</th>
  </tr>
   
   <% 
   	  int totalePuntA = 0;
   	  int totalePuntB = 0;
   	  int totalePuntC = 0;
   	  
   	  int totaleTot = 0;
   	
   	  int cap=1;
   	  Iterator<Rendicontazione> it = CapitoliList.iterator();
      while(it.hasNext()){
    	  Rendicontazione rend = it.next();
  	  		
  %>
  <tr class="containerBody">
	<td><%= rend.getDescCap() %></td>
	<td><%= rend.getPunteggioTotale() %></td>
	<td><%= rend.getPunteggioA() %></td>
	<td><%= rend.getPunteggioB() %></td>
	<td><%= rend.getPunteggioC() %></td>	
  </tr>
  
<%	
	++cap;
	 totalePuntA += rend.getPunteggioA();
	 totalePuntB += rend.getPunteggioB();
	 totalePuntC += rend.getPunteggioC();
	 
	 totaleTot += rend.getPunteggioTotale();
     }//fine while
     
  	%>  
  <tr>
  	<td style="font-weight: bold;background-color: gray;">TOTALE</td>
  	<td style="font-weight: bold;background-color: gray;"><%= totaleTot %></td>
  	<td style="font-weight: bold;background-color: gray;"><%= totalePuntA %></td>
  	<td style="font-weight: bold;background-color: gray;"><%= totalePuntB %></td>
  	<td style="font-weight: bold;background-color: gray;"><%= totalePuntC %></td>
  </tr>	
  </table>


	<input type="submit" name="stampa" value="Stampa" onclick="window.print();"/>
 </form> 
</body>