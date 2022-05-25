<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*" %>

<%@page import="org.aspcfs.modules.allerte_new.allegatof.AllegatoF"%>
<%@page import="org.aspcfs.modules.allerte_new.allegatof.ListaDistribuzione"%>

<jsp:useBean id="AllegatoFDetails" class="org.aspcfs.modules.allerte_new.allegatof.AllegatoF" scope="request"/>

<%@ include file="../../initPage.jsp" %>


<link rel="stylesheet" type="text/css" media="screen" documentale_url="" href="allerte_new/allertedocumenti/css/allerte_screen.css" />
<link rel="stylesheet" type="text/css" media="print" documentale_url="" href="allerte_new/allertedocumenti/css/allerte_print.css" />

<style>
@page {
 margin-top: 1cm !important;
 margin-bottom: 2cm !important;
  @bottom-center {
    content: counter(page) " su " counter(pages) !important;
    }
   
}


 </style> 
 
<script src='javascript/modalWindow.js'></script>
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>


<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div>

<br/><br/>

<table class="details" cellpadding="5" style="border-collapse: collapse;table-layout:fixed;" width="100%"> 
<col width="40%">
<col width="40%">
<col width="20%">


<thead>
<tr style="background-color: #ffff7f">
<td style="border:1px solid black;"><img style="text-decoration: none;" height="80" width="80" documentale_url="" src="gestione_documenti/schede/images/regionecampania.jpg" /> </td>
<td style="border:1px solid black;"> <div class="titolo">Allegato F - esiti accertamenti</div></td>
<td style="border:1px solid black;"> Notifica <br/> <label class="layout"><%=(AllegatoFDetails.getNumeroNotifica()!=null) ? AllegatoFDetails.getNumeroNotifica() : ""  %></label></td>
</tr>
<tr><td></td></tr>

</thead>

<tbody>

<tr>
<td style="border:1px solid black;">Notifica</td>
<td style="border:1px solid black;" colspan="2"><%=(AllegatoFDetails.getTipoNotifica()!=null) ? AllegatoFDetails.getTipoNotifica() : "" %></td>
</tr>

<tr>
<td style="border:1px solid black;">Rischio</td>
<td style="border:1px solid black;" colspan="2"><%=toHtml(AllegatoFDetails.getRischio()) %></td>
</tr>

<tr>
<td style="border:1px solid black;">Prodotto</td>
<td style="border:1px solid black;" colspan="2"><%=toHtml(AllegatoFDetails.getProdotto()) %></td>
</tr>

<tr>
<td style="border:1px solid black;">Denominazione Prodotto</td>
<td style="border:1px solid black;" colspan="2"><%=AllegatoFDetails.getDenominazioneProdotto() %></td>
</tr>

<tr>
<td style="border:1px solid black;">Produttore</td>
<td style="border:1px solid black;" colspan="2"><%=AllegatoFDetails.getProduttore() %></td>
</tr>

<tr>
<td style="border:1px solid black;">Lotto</td>
<td style="border:1px solid black;" colspan="2"><%=AllegatoFDetails.getLotto() %></td>
</tr>

<tr>
<td style="border:1px solid black;">Data Scadenza / Termine minimo di conservazione</td>
<td style="border:1px solid black;" colspan="2"><%=toDateasString(AllegatoFDetails.getDataScadenza()) %></td>
</tr>


<% 
ArrayList<ListaDistribuzione> liste  = AllegatoFDetails.getListe();
for (int i = 0; i< liste.size(); i++){
	ListaDistribuzione lista = (ListaDistribuzione) liste.get(i);%>
    
 <tr><td></td></tr> 
<% if (AllegatoFDetails.isHasListe()){ %>
<tr> <td style="border:1px solid black; background-color: #ccff99;" colspan="3"> <div class="testoMedio"> Lista di distribuzione <%=i+1 %></div></td></tr> 

<tr>
<td style="border:1px solid black;">Distributore</td> 
<td style="border:1px solid black;" colspan="2"><%=toHtml(lista.getDistributore()) %></td>
</tr>  

<tr>
<td style="border:1px solid black;">Data inserimento lista</td> 
<td style="border:1px solid black;" colspan="2"><%=toDateasString(lista.getData()) %></td>
</tr>	

<% } else { %>
<tr> <td style="border:1px solid black; background-color: #ccff99;" colspan="3"> <div class="testoMedio"> Senza lista</div></td></tr> 

<%} %>

<tr> <td style="border:1px solid black;" colspan="3">

Sono stati effettuati sul territorio della Regione Campania num. <label class="layout"><%=lista.getNumeroCuEseguiti() %></label> controlli ufficiali presso Operatori indicati nella lista di distribuzione con i seguenti esiti: <br/><br/>

[<%=lista.getProdottoGiaRitirato()>0 ? "X" : "" %>] Prodotto già ritirato dal fornitore - quantità:  <label class="layout"><%=lista.getProdottoGiaRitirato() %> <%=AllegatoFDetails.getUnitaMisura() %></label> <br/>
[<%=lista.getProdottoAccantonato()>0 ? "X" : "" %>] Prodotto accantonato in attesa di ritiro - quantità: <label class="layout"><%=lista.getProdottoAccantonato() %> <%=AllegatoFDetails.getUnitaMisura() %></label> <br/>
[<%=lista.getProdottoVincoloSanitario()>0 ? "X" : "" %>] Prodotto rinvenuto in vendita e sottoposto a vincolo sanitario in attesa di ritiro - quantità: <label class="layout"><%=lista.getProdottoVincoloSanitario() %> <%=AllegatoFDetails.getUnitaMisura() %></label> <br/>
[<%=lista.getProdottoVendutoConsumatore()>0 ? "X" : "" %>] Prodotto venduto al consumatore/utilizzatore  finale <br/>
[<%=lista.getProdottoDistribuito()>0 ? "X" : "" %>] Prodotto ulteriormente distribuito dalla ditta) - quantità: <label class="layout"><%=lista.getProdottoDistribuito() %> <%=AllegatoFDetails.getUnitaMisura() %></label> <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[<%=lista.getProdottoDistribuitoAsl()>0 ? "X" : "" %>] esclusivamente sul territorio ASL  <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[<%=lista.getProdottoDistribuitoRegione()>0 ? "X" : "" %>] esclusivamente nel territorio della Regione  <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[<%=lista.getProdottoDistribuitoNazionale()>0 ? "X" : "" %>] esclusivamente sul territorio nazionale <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[<%=lista.getProdottoDistribuitoEstero()>0 ? "X" : "" %>] commercializzato al di fuori del territorio nazionale  <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[<%=lista.getProdottoDistribuitoRitiro()>0 ? "X" : "" %>] Sono in atto le procedure di ritiro  <br/>
[<%=lista.getOperatoriNonComunicazioneRischio()>0 ? "X" : "" %>] Num. <label class="layout"><%=lista.getOperatoriNonComunicazioneRischio()%></label> Operatori dichiarano di NON aver mai ricevuto comunicazione del rischio dalla ditta fornitrice<br/>
[<%=lista.getOperatoriNonRicevutoProdotto()>0 ? "X" : "" %>]  Num. <label class="layout"><%=lista.getOperatoriNonRicevutoProdotto()%></label> Operatori dichiarano di NON aver mai ricevuto il prodotto oggetto di allerta dalla ditta fornitrice<br/>
[<%=lista.getOperatoriSoloSedeLegale()>0 ? "X" : "" %>]  Num. <label class="layout"><%=lista.getOperatoriSoloSedeLegale()%></label> Operatori risultano essere sola sede legale (non è riportata nella lista di distribuzione la sede di destinazione della merce) <br/>
[<%=lista.getOperatoriNonEsistenti()>0 ? "X" : "" %>] Num. <label class="layout"><%=lista.getOperatoriNonEsistenti()%></label> Operatori risultano non esisenti all'indirizzo riportato nella lista di distribuzione<br/>

</td>
</tr>
	
<%
}
%>


<tr>
<td style="border:0px">Data <br/><br/><br/></td>
<td colspan="2" style="border:0px">Il Referente Regionale<br/><br/><br/></td>
</tr>


</table>
<br><br><br>
<%@ include file="/gestione_documenti/gdpr_footer.jsp" %>
<br/>