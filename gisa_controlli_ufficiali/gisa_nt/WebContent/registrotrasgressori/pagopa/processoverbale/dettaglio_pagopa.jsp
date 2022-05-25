<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ include file="../../initPage.jsp"%>
<jsp:useBean id="trasgr" class="org.aspcfs.modules.registrotrasgressori.base.Trasgressione" scope="request"/>
<jsp:useBean id="listaPagoPa" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="gruppiUtente" type="java.util.ArrayList" scope="request" />

<jsp:useBean id="messaggio" class="java.lang.String" scope="request"/>
<%@page import="org.aspcfs.modules.registrotrasgressori.base.*"%>

  <% UserBean user = (UserBean) session.getAttribute("User");%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %> 


<link rel="stylesheet" documentale_url="" href="registrotrasgressori/css/trasgressori_screen.css" type="text/css" media="screen" />
<link rel="stylesheet" documentale_url="" href="registrotrasgressori/css/trasgressori_print.css" type="text/css" media="print" />


<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>

<script src='javascript/modalWindow.js'></script>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>

<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>

<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<%@ include file="../js/registro_sanzioni_js.jsp"%>

<%! 
public static String getTagValue(String xml, String tagName){
	String esito = "";
    try {esito =  xml.split("<"+tagName+">")[1].split("</"+tagName+">")[0]; } catch (Exception e) {}
    return esito;
}

%>


<% if (messaggio!=null && !messaggio.equals("")){ %>
<%-- <script> alert('<%=messaggio%>');</script> --%>
<div style="background:yellow">
<pre><xmp>
<%=getTagValue(messaggio, "esito")%>
<%=getTagValue(messaggio, "faultCode")%>
<%=getTagValue(messaggio, "faultString")%>
</xmp></pre>
</div>
<%} %>



<form name="form1" id="form1" action="RegistroTrasgressori.do?command=InvioPagoPa" method="post">

<table class="details2" id = "tabletrasgressione" cellpadding="10" cellspacing="10">

<tr><th colspan="2">Invio sanzione a PagoPa</th></tr>

<tr><th>N° prog </th>  
<td><%= trasgr.getProgressivo() %>\<%=trasgr.getAnnoYY() %></td></tr>
<tr>
<th>Id controllo </th>  
<td><%=trasgr.getIdControllo()>0 ? trasgr.getIdControllo() : "" %></td></tr>
<tr>
<th>Id sanzione </th>  
<td><%=trasgr.getIdSanzione() %></td></tr>


<%for (int i = 0; i<listaPagoPa.size(); i++) {
	PagoPa pagopa = (PagoPa) listaPagoPa.get(i); %>

<tr><th colspan="2" style="background:yellow">PAGAMENTO <%=pagopa.getRataNumero() %></th></tr>

<% if (listaPagoPa.size()==1 && !pagopa.isInviato()){ %>
<tr><th>Numero rate</th> <td><select id="numeroRate_<%=pagopa.getId() %>" name="numeroRate_<%=pagopa.getId() %>" onChange="alert('ATTENZIONE! Selezionando un numero di rate maggiore di 1, verranno generati tanti invii ed IUV quante sono le rate. Anche il valore di importo versamento verrà diviso tra tutte le rate.')"><%for (int j = 1; j<=10; j++) { %> <option value="<%=j%>"><%=j %></option> <%} %></select></td></tr>
 <% } else { %>
<input type="hidden" id="numeroRate_<%=pagopa.getId() %>" name="numeroRate_<%=pagopa.getId() %>" value="1"/> 
  <% } %>
  
<tr><th colspan="2">Soggetto pagatore</th></tr>
<tr><th>Tipo pagatore</th> <td>
<input <%=pagopa.isInviato() ? "onClick=\"return false;\"" : "" %> type="radio" id="tipoIdentificativoUnivocoG_<%=pagopa.getId() %>" name="tipoIdentificativoUnivoco_<%=pagopa.getId() %>" value="G" <%=(pagopa.getTipoIdentificativoUnivoco().equals("G") ? "checked" : "") %>/> Persona giuridica 
<input <%=pagopa.isInviato() ? "onClick=\"return false;\"" : "" %> type="radio" id="tipoIdentificativoUnivocoF_<%=pagopa.getId() %>" name="tipoIdentificativoUnivoco_<%=pagopa.getId() %>" value="F" <%=(pagopa.getTipoIdentificativoUnivoco().equals("F") ? "checked" : "") %>/> Persona fisica 
</td>

<tr><th>Partita IVA / Codice fiscale</th> <td><input <%=pagopa.isInviato() ? "readonly" : "" %> type="text" id="codiceIdentificativoUnivoco_<%=pagopa.getId() %>" name="codiceIdentificativoUnivoco_<%=pagopa.getId() %>" value="<%=toHtml(pagopa.getCodiceIdentificativoUnivoco())%>"/></td></tr>
<tr><th>Ragione sociale / Nominativo</th> <td><input <%=pagopa.isInviato() ? "readonly" : "" %> type="text"id="anagraficaPagatore_<%=pagopa.getId() %>" name="anagraficaPagatore_<%=pagopa.getId() %>" value="<%=toHtml(pagopa.getAnagraficaPagatore())%>"/></td></td></tr>
<tr><th>Indirizzo</th> <td><input <%=pagopa.isInviato() ? "readonly" : "" %> type="text" id="indirizzoPagatore_<%=pagopa.getId() %>" name="indirizzoPagatore_<%=pagopa.getId() %>" value="<%=toHtml(pagopa.getIndirizzoPagatore())%>"/> </td></tr>
<tr><th>Civico</th> <td><input <%=pagopa.isInviato() ? "readonly" : "" %> type="text" id="civicoPagatore_<%=pagopa.getId() %>" name="civicoPagatore_<%=pagopa.getId() %>" value="<%=toHtml(pagopa.getCivicoPagatore())%>"/></td></tr>
<tr><th>CAP</th> <td><input <%=pagopa.isInviato() ? "readonly" : "" %> type="text" id="capPagatore_<%=pagopa.getId() %>" name="capPagatore_<%=pagopa.getId() %>" value="<%=toHtml(pagopa.getCapPagatore())%>"/></td></tr>
<tr><th>Comune</th> <td><input <%=pagopa.isInviato() ? "readonly" : "" %> type="text" id="localitaPagatore_<%=pagopa.getId() %>" name="localitaPagatore_<%=pagopa.getId() %>" value="<%=toHtml(pagopa.getLocalitaPagatore())%>"/></td></tr>
<tr><th>Cod provincia</th> <td><input <%=pagopa.isInviato() ? "readonly" : "" %> type="text" id="provinciaPagatore_<%=pagopa.getId() %>" name="provinciaPagatore_<%=pagopa.getId() %>" maxlength="2" value="<%=toHtml(pagopa.getProvinciaPagatore())%>"/></td></tr>
<tr><th>Nazione</th> <td><input <%=pagopa.isInviato() ? "readonly" : "" %> type="text" id="nazionePagatore_<%=pagopa.getId() %>" name="nazionePagatore_<%=pagopa.getId() %>" value="<%=toHtml(pagopa.getNazionePagatore())%>"/></td></tr>
<tr><th>Domicilio digitale</th> <td><input <%=pagopa.isInviato() ? "readonly" : "" %> type="text" id="emailPagatore_<%=pagopa.getId() %>" name="emailPagatore_<%=pagopa.getId() %>" value="<%=toHtml(pagopa.getEmailPagatore())%>"/></td></tr>

<tr><th colspan="2">Dati versamento</th></tr>
<tr><th>Tipo pagamento</th> <td>
<input <%=pagopa.isInviato() ? "onClick=\"return false;\"" : "" %> type="radio" id="tipoPagamentoPV_<%=pagopa.getId() %>" name="tipoPagamento_<%=pagopa.getId() %>" value="PV" <%=("PV".equals(pagopa.getTipoPagamento()) || "".equals(pagopa.getTipoPagamento()) || pagopa.getTipoPagamento()==null ? "checked" : "") %>/> Per Processo Verbale 
<input <%=pagopa.isInviato() ? "onClick=\"return false;\"" : "" %> type="radio" id="tipoPagamentoNO_<%=pagopa.getId() %>" name="tipoPagamento_<%=pagopa.getId() %>" value="NO" <%=("NO".equals(pagopa.getTipoPagamento()) ? "checked" : "") %>/> Per Numero Ordinanza 
</td>
<tr><th>Data esecuzione pagamento</th> <td><input type="date" id="dataPagamento_<%=pagopa.getId() %>" name="dataPagamento_<%=pagopa.getId() %>" value="<%=toHtml(pagopa.getDataPagamento())%>"/></td></tr>
<tr><th>Identificativo univoco dovuto</th> <td><input type="text" readonly id="identificativoUnivocoDovuto_<%=pagopa.getId() %>" name="identificativoUnivocoDovuto_<%=pagopa.getId() %>" value="<%=pagopa.getIdentificativoUnivocoDovuto()%>"/> <br/> (Generato automaticamente)</td></tr>
<tr><th>Importo totale versamento</th> <td><input <%=pagopa.isInviato() ? "readonly" : "" %> type="number" id="importoSingoloVersamento_<%=pagopa.getId() %>" name="importoSingoloVersamento_<%=pagopa.getId() %>" value="<%=pagopa.getImportoSingoloVersamento()%>"/></td></tr>
<tr><th>Causale versamento</th> <td><input type="text" readonly id="causaleVersamento_<%=pagopa.getId() %>" name="causaleVersamento_<%=pagopa.getId() %>" value="<%=pagopa.getCausaleVersamento()%>"/> <br/>(Generato automaticamente)</td></tr>
<tr><th>Tipo versamento</th> <td><input type="text" readonly id="tipoVersamento_<%=pagopa.getId() %>" name="tipoVersamento_<%=pagopa.getId() %>" value="<%=pagopa.getTipoVersamento()%>"/> <br/>(Valore di default)</td></tr>
<tr><th>Identificativo tipo dovuto</th> <td><input type="text" readonly id="identificativoTipoDovuto_<%=pagopa.getId() %>" name="identificativoTipoDovuto_<%=pagopa.getId() %>" value="<%=pagopa.getIdentificativoTipoDovuto()%>"/> <br/>(Valore di default)</td></tr>
<tr><th>Dati specifici riscossione</th> <td><input type="text" readonly id="datiSpecificiRiscossione_<%=pagopa.getId() %>" name="datiSpecificiRiscossione_<%=pagopa.getId() %>" value="<%=pagopa.getDatiSpecificiRiscossione()%>"/> <br/>(Valore di default)</td></tr>
<input type="hidden" readonly id="rataNumero_<%=pagopa.getId() %>" name="rataNumero_<%=pagopa.getId() %>" value="<%=pagopa.getRataNumero()%>"/>


<% if (pagopa.isInviato()){ %>
<tr><th>Data invio</th> <td><%=toDateasString(pagopa.getDataInvio()) %></td></tr>
<tr><th>Identificativo Univoco Versamento</th> <td><%=pagopa.getIdentificativoUnivocoVersamento() %></td></tr>
<tr><th>PDF Avviso</th> <td><a href="<%=pagopa.getUrlFileAvviso() %>">Download</a></td></tr>
<tr><th>Stato pagamento</th> <td><a href="RegistroTrasgressori.do?command=StatoPagoPa&idPagoPa=<%=pagopa.getId()%>">Verifica</a></td></tr>
<tr>
<th><input type="button" id="annulla" value="ANNULLAMENTO" onClick="annullamentoPagoPa(this.form, '<%=pagopa.getId()%>')"/></th>
<th><input type="button" id="aggiorna" value="AGGIORNA E INVIA" onClick="aggiornamentoPagoPa(this.form, '<%=pagopa.getId()%>')"/></th>
</tr>

</tr>

<% } else { %>
<tr><th colspan="2"><input type="button" id="invia" value="SALVA E INVIA" onClick="inserimentoPagoPa(this.form, '<%=pagopa.getId()%>')"/></th></tr>
<% } %>

<% } %>



<tr>
<th colspan="2">
<input type="button" id="torna" value="TORNA AL REGISTRO" onClick="tornaAlRegistro('<%=trasgr.getAnno()%>', '<%=trasgr.getTrimestre()%>')"/>
</th>
</tr> 

<input type="hidden" readonly id="idControlloUfficiale" name="idControlloUfficiale" value="<%=trasgr.getIdControllo() %>"/>
<input type="hidden" readonly id="id_sanzione" name="id_sanzione" value="<%=trasgr.getIdSanzione() %>"/>
<input type="hidden" readonly id="idTrasgressione" name="idTrasgressione" value="<%=trasgr.getId() %>"/>
<input type="hidden" readonly id="idPagoPa" name="idPagoPa" value=""/>
<input type="hidden" id="origine" name="origine" value="ProcessoVerbale"/>
	
</form>		



