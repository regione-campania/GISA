<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%> 
    <%@ page
	import="java.util.*, org.aspcfs.modules.DNA.base.*"%>
    
 <jsp:useBean id="convocazione" class="org.aspcfs.modules.DNA.base.ConvocazioneTemporale" scope="request"/>
 <jsp:useBean id="statiList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
	
	<%@ include file="../initPage.jsp" %>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>  
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>

<script type="text/javascript">
function refreshLista(form) {

	  $("#inputForm").submit();

}

</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lista convocazioni</title>
</head>
<body>
</br>
<center><b><%=convocazione.getDenominazione() %></b></center>
</br>

<!-- Statistiche -->	
<div><span>Numero totale soggetti caricati: <%= convocazione.getNumeroTotali()%></span><br>
<span>di cui: <br> 
<ul>
<li style="text-align: left;">Convocati: <%=(convocazione.getNumeroConvocati())%></li>
<li style="text-align: left;">Da Convocare: <%=convocazione.getNumeroDaConvocare() %></li>
<li style="text-align: left;"> Esclusi: <%=convocazione.getNumeroEsclusiPerRegolarizzazioneSuccessiva() %></li> 
<li style="text-align: left;">Presentati: <%=convocazione.getNumeroPresentati() %></li></ul>
</span>
<span>Percentuale di completamento: <%=convocazione.getPercentualeDiCompletamento() %> </span>
</div>




<!-- INIZIO -->
<br/>
<a href="ListaConvocazioneAction.do?command=StampaCsvAggiornatoConvocazioneTemporale&idListaConvocazioneTemporale=<%=convocazione.getId()%>" >Stampa CSV aggiornato</a> - 
<a href="ListaConvocazioneAction.do?command=StampaXLSAggiornatoConvocazioneTemporale&idListaConvocazioneTemporale=<%=convocazione.getId()%>" >Stampa XLS aggiornato</a>
</br></br>
<form method="post" id="inputForm" name="inputForm" action="ListaConvocazioneAction.do?command=DettaglioConvocazioneTemporale&auto-populate=true" onSubmit="">
<%statiList.setJsEvent("onChange=\"javascript:refreshLista();\""); %>
Filtro stato soggetti <%=statiList.getHtmlSelect("idStatoConvocati", convocazione.getIdStatoConvocati()) %>
<input type="hidden" name="idListaConvocazioneTemporale" id="idListaConvocazioneTemporale" value="<%=convocazione.getId() %>">
<table class="details" cellpadding="4" cellspacing="0" border="0"
	width="100%">
	<thead>
		<tr>
			<%-- th width="8">
	      &nbsp;
	    </th --%>
			<th width="16%" nowrap><strong>Nome</strong></th>
			<th width="16%" nowrap><strong>Cognome</strong>
			</th>
			<th width="16%"><strong>Codice fiscale</strong>
			</th>
			<th width="16%" nowrap><strong>Data nascita</strong>
			</th>
			<th width="16%" nowrap><strong>Indirizzo</strong>
			</th>
			<th width="16%" nowrap><strong>Microchip</strong>
			</th>
			<th width="16%" nowrap><strong>Stato convocazione</strong>	
			</th>
			<th width="16%" nowrap><strong>Data del prelievo</strong>	
			</th>
			
		</tr>
	</thead>
	<tbody>
		<% 
		    ArrayList<Convocato> convocazioniList = (ArrayList<Convocato>) convocazione.getConvocazioni();
			Iterator itr = convocazioniList.iterator();
			if (itr.hasNext()) {
				int rowid = 0;
				int i = 0;
				while (itr.hasNext()) {
					i++;
					rowid = (rowid != 1 ? 1 : 2);
					Convocato thisConvocato = (Convocato) itr.next();
		%>
		<tr style="background-color: rgba(<%=ApplicationProperties.getProperty("COLOR_"+thisConvocato.getIdStatoPresentazione()) %>,<%=ApplicationProperties.getProperty("TRASPARENCY")%>);" class="row<%=rowid%>">

			<td width="15%" nowrap>
			
			<%= toHtml(thisConvocato.getNome()) %>
			
			</td>
			<td width="15%" nowrap><%=toHtml(thisConvocato.getCognome())%></td>
			<td width="15%" nowrap><%=toHtml(thisConvocato.getCodiceFiscale())%></td>
			<td width="15%" nowrap><%=toDateasString((thisConvocato.getDataNascita()))%></td>
			<td width="15%" nowrap><%=toHtml(thisConvocato.getIndirizzo())%></td>
			<td width="15%" nowrap><%=toHtml(thisConvocato.getMicrochip())%></td>
			<td width="15%" nowrap><%=toHtml(statiList.getSelectedValue(thisConvocato.getIdStatoPresentazione()))%></td>
			<td width="15%" ><%=(thisConvocato.getDataPrelievo() != null) ? toDateasString(thisConvocato.getDataPrelievo()) : "NON EFFETTUATO"%></td>
		</tr>
		<%
			}
			} else {
				%>
				
						
			   <tr class="containerBody">
		<td colspan="9">Non sono state trovate convocazioni.
		</td>
	</tr>
			    <%
			    
		    
	

		}%>
	</tbody>
</table>



</body>
</html>