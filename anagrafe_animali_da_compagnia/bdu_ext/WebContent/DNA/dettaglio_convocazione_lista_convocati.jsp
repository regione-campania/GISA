<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>

<%@ page import="java.util.*" %>
    <%@ page
	import="java.util.*, org.aspcfs.modules.DNA.base.*"%>
    
  <script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>  
    
 <jsp:useBean id="lista" class="org.aspcfs.modules.DNA.base.ListaConvocazione" scope="request"/>
 <jsp:useBean id="statiList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />

<script type="text/javascript" src="dwr/interface/DwrUtil.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/modalWindow.js"> </script>

	<%@ include file="../initPage.jsp" %>
	
<script type="text/javascript" src="dwr/util.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();
</SCRIPT>


<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/dateControl.js"></script>	
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script type="text/javascript">
function doCheck(form) {
	formtest=true;
	  message = "";
	  
	  if (form.denominazione.value==''){
	        message+="- Denominazione richiesta.\n";
			formtest= false;}
	 
	  
	  lanciaControlloDate();
	 // formTest = false;
   
	
 
 if (!formtest){
		  alert(message);
		  }

 return formtest;
  }

function checkForm(form) {

if (confirm("ATTENZIONE. Stai per aggiornare lo stato dei convocati recuperando i dati da BDU.")){
	loadModalWindow();
	form.submit();
	}
else
	return false;
  }

function refreshLista(form) {
	  
	  $("#inputForm").attr("action", "ListaConvocazioneAction.do?command=DettaglioListaConvocazione");
	  $("#inputForm").attr("onSubmit", "");
	  $("#inputForm").submit();

  }




</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lista convocazioni</title>
</head>
<body>

<!--  AGGIORNA BDU -->

<center>


<form name="bdurefresh5" action="ListaConvocazioneAction.do?command=AggiornaListaDaBDU" method="POST">
<input type="hidden" id="idLista" name="idLista" value="<%=lista.getIdListaConvocazione()%>"></input>
<input type="image" src="images/buttons/bdurefresh.png" width="120" height="70" onClick="checkForm(this.form);return false;"></input>

</form>
			</center>
<!-- Statistiche -->	
<div><span>Numero totale soggetti caricati: <%= lista.getNumeroTotali()%></span><br>
<span>di cui: <br> 
<ul>
<li style="text-align: left;">Convocati: <%=(lista.getNumeroConvocati())%></li>
<li style="text-align: left;">Da Convocare: <%=lista.getNumeroDaConvocare() %></li>
<li style="text-align: left;"> Esclusi: <%=lista.getNumeroEsclusiPerRegolarizzazioneSuccessiva() %></li> 
<li style="text-align: left;">Presentati: <%=lista.getNumeroPresentati() %></li></ul>
</span>
<span>Percentuale di completamento: <%=lista.getPercentualeDiCompletamento() %> %</span>
</div>
			
<!-- INIZIO -->
<br/>

<center><b><%=lista.getDenominazione() %></b></center>
<a href="ListaConvocazioneAction.do?command=StampaXLSAggiornatoConvocazione&idListaConvocazione=<%=lista.getIdListaConvocazione()%>" >Stampa XLS aggiornato</a>
</br></br>
<form method="post" id="inputForm" name="inputForm" action="ListaConvocazioneAction.do?command=CreaNuovoGruppoConvocazione&auto-populate=true" onSubmit="return doCheck(this);"><dhv:permission name="lista_convocazione-edit">
<table class="details">
<tr><td>Denominazione <input type="text" name="denominazione" id="denominazione" value=""> </td><td>&nbsp; </td>
<tr><td>Data scadenza convocazione <input readonly type="text" name="dataFine"
				size="10" value=""
				nomecampo="dataFine" tipocontrollo="T2, T20"
				labelcampo="Data Fine" />&nbsp; <a href="#"
				onClick="cal19.select(document.inputForm.dataFine,'anchor19','dd/MM/yyyy'); return false;"
				NAME="anchor19" ID="anchor19"><img
				src="images/icons/stock_form-date-field-16.gif" border="0"
				align="absmiddle"></a></td>
				<td><input type="submit" value="<dhv:label name="">Crea nuova lista di convocazione</dhv:label>" name="upload"></td>
				</tr>
				</table></br>
</dhv:permission>
<a href="ListaConvocazioneAction.do?command=DettaglioListaConvocazione&idLista=<%=lista.getIdListaConvocazione()%>&idTipoVisualizzazione=1"> Visualizza liste di convocazioni esistenti</a> - 
<%statiList.setJsEvent("onChange=\"javascript:refreshLista();\""); %>
Filtro stato soggetti <%=statiList.getHtmlSelect("idStatoConvocati", lista.getIdStatoConvocati()) %>
<input type="hidden" name="idLista" id="idLista" value="<%=lista.getIdListaConvocazione()%>">
<table class="details" cellpadding="4" cellspacing="0" border="0"
	width="100%">
	<thead>
		<tr>
			<%-- th width="8">
	      &nbsp;
	    </th --%>
	        <th width="5%" ><strong>Seleziona per convocazione</strong></th>
			<th width="10%" ><strong>Nome</strong></th>
			<th width="10%" ><strong>Cognome</strong>
			</th>
			<th width="10%"><strong>Codice fiscale</strong>
			</th>
			<th width="5%" ><strong>Data nascita</strong>
			</th>
			<th width="16%" ><strong>Indirizzo</strong>
			</th>
			<th width="16%" ><strong>Microchip</strong>
			</th>
			<th width="16%" ><strong>Stato convocazione</strong>	
			</th>
			<th width="16%" ><strong>Data del prelievo</strong>	
			</th>
			
		</tr>
	</thead>
	<tbody>
		<% 
		    ArrayList<Convocato> convocazioniList = (ArrayList<Convocato>) lista.getConvocazioni();
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
		    <td width="5%" nowrap>
		    <%if (thisConvocato.getIdStatoPresentazione() != Convocato.convocato_ma_escluso_per_regolarizzazione && thisConvocato.getIdStatoPresentazione() != Convocato.presentato ){ %>
			<input type="checkbox" id="checkConvocato_<%=thisConvocato.getId()%>" name="checkConvocato_<%=thisConvocato.getId()%>">			
			<%}else{ %>			
			&nbsp;
			<%} %>		
			</td>
			<td width="10%" nowrap><%= toHtml(thisConvocato.getNome()) %></td>
			<td width="10%" ><%=toHtml(thisConvocato.getCognome())%></td>
			<td width="10%" ><%=toHtml(thisConvocato.getCodiceFiscale())%></td>
			<td width="5%" ><%=toDateasString((thisConvocato.getDataNascita()))%></td>
			<td width="15%" ><%=toHtml(thisConvocato.getIndirizzo())%></td>
			<td width="15%" ><%=toHtml(thisConvocato.getMicrochip())%></td>
			<td width="15%" ><%=toHtml(statiList.getSelectedValue(thisConvocato.getIdStatoPresentazione()))%>
			<%if (thisConvocato.getIdStatoPresentazione() == Convocato.convocato_non_presentato 
					|| thisConvocato.getIdStatoPresentazione() == Convocato.presentato){
					ConvocazioneTemporale last = thisConvocato.getUltimaConvocazioneTemporaleSoggetto();
					if (last.getId() > 0 && last.getDataFine() != null){%>
					 (convocazione del  <%=toDateasString(last.getDataFine()) %> )
					<%} }%></td>
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


</form>



</body>
</html>