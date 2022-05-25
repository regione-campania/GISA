<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<jsp:useBean id="seduta"
	class="org.aspcfs.modules.mu.base.SedutaUnivoca" scope="request" />
<jsp:useBean id="listaArticoli17"
	class="org.aspcfs.modules.mu.base.Articolo17List" scope="request" />
<jsp:useBean id="statiArticolo17"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<%@page import="org.aspcfs.modules.mu.base.*, org.aspcfs.modules.mu.base.Articolo17, java.util.*"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>HTML Table Filter Generator script | Examples - jsFiddle
	demo by koalyptus</title>
<script type='text/javascript' src='/js/lib/dummy.js'></script>

<link rel="stylesheet" type="text/css" href="/css/result-light.css">
<script type='text/javascript'
	src="http://tablefilter.free.fr/TableFilter/tablefilter_all_min.js"></script>
<link rel="stylesheet" type="text/css"
	href="http://tablefilter.free.fr/TableFilter/filtergrid.css">
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="javascript/gestione_esercenti.js"></SCRIPT>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>
<link rel="stylesheet" type="text/css" href="css/capitalize.css"></link>

<script src='javascript/modalWindow.js'></script>
<script src='javascript/jquerymini.js'></script>

<script type="text/javascript" src="dwr/interface/Articolo17.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
</head>

<jsp:useBean id="OrgDetails"
	class="org.aspcfs.modules.stabilimenti.base.Organization"
	scope="request" />
<jsp:useBean id="id" class="java.lang.String" scope="request" />
<jsp:useBean id="idLivello" class="java.lang.String" scope="request" />
<script type="text/javascript">


//PER ORA NON UTILIZZATO IN QUANTO LA CHIUSURA SI FA CON LA STAMPA
function chiudiArticolo(id) 
{
		Articolo17.close(id ,   {callback:verificaChiusura,async:false});

}

function verificaChiusura(value)
{

/* if (value){
	alert('Articolo 17 chiuso correttamente');
	//return false;
}else{
	alert('Si è verificato un problema nella chiusura');
} */
}

function openPopupArticolo17Pdf(idArticolo) {

	window
			.open(
					'GestioneDocumenti.do?command=GeneraPDFMacelliUnici&tipo=Macelli_17&idArticolo17='
							+ idArticolo,
					'popupSelect',
					'toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');

}

</script>

<p>
	<a href="javascript:;"
		onClick="window.open('MacellazioneUnica.do?command=AggiungiEsercente&id=<%=id%>&idLivello=<%=idLivello%>&idMacello=<%=OrgDetails.getOrgId()%>', 'titolo', 'width=400, height=200, resizable, status, scrollbars=1, location');">
		Aggiungi Esercente</a>
</p>

<p>
<a href="javascript:;"
			onClick="window.open('MacellazioneUnica.do?command=GestisciEsercenti&idSeduta=<%=id%>&idStato=<%=Articolo17.idStatoChiuso %>', 'titolo', 'width=400, height=200, resizable, status, scrollbars=1, location');">
				Visualizza Articoli 17</a>
				
		</p>		





<table id="tabellaArticolo17" cellpadding="0" cellspacing="0"
	width="100%" class="order-table table">
	<thead>
		<tr>
			<th>Identificativo Art. 17</th>
			<th>Stato Animale</th>
			<th>Operazioni</th>
	</thead>
	<tbdody> <%
 	Iterator k = listaArticoli17.iterator();
 	while (k.hasNext()) {
 		Articolo17 articolo = (Articolo17) k.next();
 %>

	<tr class="">
		<%-- <td><%=seduta.getNumeroSeduta() %></td> --%>
		<td><%=articolo.getDescrizione()%> &nbsp;</td>
		<td><%=statiArticolo17.getSelectedValue(articolo.getIdStato()) %>
			&nbsp;</td>
		<td><font size="2">
		<dhv:evaluate if="<%=(articolo.getIdStato() == Articolo17.idStatoAperto) %>">
		 <a href="javascript:;" onclick="javascript: openPopupArticolo17Pdf('<%=articolo.getId()%>');">Stampa/Chiudi</a> 
		<a		href="">Modifica</a> <a href="">Elimina</a></dhv:evaluate>
		<dhv:evaluate if="<%=(articolo.getIdStato() == Articolo17.idStatoChiuso) %>">
		<a		href="#" onclick="javascript: openPopupArticolo17Pdf('<%=articolo.getId()%>');">Stampa</a></dhv:evaluate>
		</font></td>
	</tr>
	<%
		}
	%> </tbdody>
</table>
</form>
<script type='text/javascript'>
	//<![CDATA[ 

	var tabellaArticolo17_Props = {
		col_0 : "none",
		col_1 : "select",
		col_3 : "none",
		col_4 : "none",
		display_all_text : " [ Mostra tutte ] ",
		sort_select : true
	};
	var tf3 = setFilterGrid("tabellaArticolo17", tabellaArticolo17_Props);
	//]]>
</script>