<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="org.aspcfs.modules.mu.base.Articolo17"%>
<%@page import="org.aspcfs.modules.mu.base.SedutaUnivoca"%>
<%@page import="org.aspcfs.modules.mu.base.CapoUnivocoList"%>
<jsp:useBean id="specieList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="statiCapi" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="specieBovine" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="razzeBovine" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="categorieBovine"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="categorieBufaline"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="catRischio" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="PianiRisanamento"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="seduta"
	class="org.aspcfs.modules.mu.base.SedutaUnivoca" scope="request" />
<jsp:useBean id="statoSeduta" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="listaArticoli17"
	class="org.aspcfs.modules.mu.base.Articolo17List" scope="request" />
<%@page import="org.aspcfs.modules.mu.base.CapoUnivoco"%>
<%@ page import="java.util.*"%>

<%@ include file="../../initPage.jsp"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
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
</head>
<script type="text/javascript">
	function openPopupRegistroMacellazioniPdf(idSeduta) {

		window
				.open(
						'GestioneDocumenti.do?command=GeneraPDFMacelliUnici&tipo=Macelli_Registro&idSeduta='
								+ idSeduta,
						'popupSelect',
						'toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');

	}
</script>




<table class="trails" cellspacing="0">
	<tr>
		<td><a
			href="MacellazioneUnica.do?command=List&orgId=<%=seduta.getIdMacello()%>">Home
				macellazioni </a> > Dettaglio seduta</td>
	</tr>
</table>
<%
String param1 = "orgId=" + seduta.getIdMacello();
%>
<dhv:container name="stabilimenti_macellazioni_ungulati" selected="macellazioniuniche" object="OrgDetails" param="<%= param1 %>" >
<dhv:evaluate
	if="<%=(seduta.getIdStato() == SedutaUnivoca.idStatoMacellato)%>">
	<input type="button" name="macella" id="macella"
		value="STAMPA REGISTRO MACELLAZIONE SEDUTA"
		onclick="javascript:openPopupRegistroMacellazioniPdf('<%=seduta.getId()%>');" />
</dhv:evaluate>
<dhv:evaluate
	if="<%=(seduta.getIdStato() == SedutaUnivoca.idStatoMacellazioneInCorso || seduta.getIdStato() == SedutaUnivoca.idStatoDaMacellare )%>">
	<input type="button" name="macella" id="macella" value="PROCEDI"
		onclick="location.href='MacellazioneUnica.do?command=MacellaSeduta&idSeduta=<%=seduta.getId()%>';" />
</dhv:evaluate>

<dhv:evaluate if="<%=(seduta.getIdStato() != SedutaUnivoca.idStatoMacellato && seduta.getGiorniAperturaSeduta() > 5) %>">

<p style="background-color: yellow;"> Attenzione, questa seduta risulta incompleta da <%=seduta.getGiorniAperturaSeduta()%> giorni </p>
</dhv:evaluate>
<table class="details" layout="fixed" width="50%">
	<col width="180px">
	<th colspan="2">Dettaglio seduta</th>
	<tr>
		<td class="formLabel">Data della seduta</td>
		<td><%=toDateasString(seduta.getData())%></td>
	</tr>
	<tr>
		<td class="formLabel">Numero della seduta</td>
		<td><%=seduta.getNumeroSeduta()%></td>
	</tr>
	<tr>
		<td class="formLabel">Stato della seduta</td>
		<td><%=statoSeduta.getSelectedValue(seduta.getIdStato())%></td>
	</tr>
</table>
</br>
</br>

<%Iterator itt = listaArticoli17.iterator();
int kk = 0;
while (itt.hasNext()){
	Articolo17 art = (Articolo17) itt.next();
	if (art.getIdStato() == Articolo17.idStatoAperto && art.getGiorniAperturaArticolo() > 1){
		kk++;
	}
}
%>

<table class="details" layout="fixed" width="50%">
	<col width="180px">
	<th colspan="2">Allerte</th>
	<tr>
		<td class="formLabel">Articoli 17 sospesi</td>
		<td><%=kk %></td>
	</tr>
	
</table>

<br />
<br />

<table class="details" layout="fixed" width="50%">
	<th colspan="2">Informazioni capi</th>
	<tr>
		<td class="formLabel">Specie capi</td>
		<td>
			<table width="100%">
				<tr>
					<th>Specie</th>
					<th>Num. capi</th>
				</tr>
				<%
					HashMap <Integer, HashMap<Integer, Integer>> mapConteggi = CapoUnivocoList.calcolaStatisticheSpecie(seduta.getListaCapi()); 
						HashMap<Integer, Integer> grigliaSpecie= seduta.getListaCapiNumeri();
								Iterator<Integer> itSpecie = grigliaSpecie.keySet().iterator();
								while(itSpecie.hasNext())
								{
							int specie = itSpecie.next(); 
							int num = grigliaSpecie.get(specie);
							HashMap<Integer, Integer> mapThisConteggio = mapConteggi.get(specie);
				%>

				<tr>
					<td><%=specieList.getSelectedValue(specie)%></td>
					<td><%=num%> (di cui: <%
						Iterator it = mapThisConteggio.entrySet().iterator();
									int k = 0;
								    while (it.hasNext()) {
								        Map.Entry pair = (Map.Entry)it.next();
								        
					%> <%=(k > 0) ?  "," : ""%> <%=pair.getValue()%> <%=statiCapi.getSelectedValue((Integer)pair.getKey())%>
						<%
							k++;
										}
						%> )</td>
				</tr>

				<%
					}
				%>

			</table>
		</td>
	</tr>
</table>

<br />
<br />



<table class="details" layout="fixed" width="50%">
<dhv:evaluate if="<%=seduta.getNumeroCapiMacellati() > 0 %>">
	<tr>
		<td class=""><a href="javascript:;"
			onClick="window.open('MacellazioneUnica.do?command=GestisciEsercenti&idSeduta=<%=seduta.getId()%>&idStato=<%=Articolo17.idStatoAperto%>', 'titolo', 'width=400, height=200, resizable, status, scrollbars=1, location');">
				Gestione esercenti (Art. 17)</a></td>

		<td class=""><a href="javascript:;"
			onClick="window.open('MacellazioneUnica.do?command=GestisciEsercenti&idSeduta=<%=seduta.getId()%>&idStato=<%=Articolo17.idStatoChiuso%>', 'titolo', 'width=400, height=200, resizable, status, scrollbars=1, location');">
				Visualizza Articoli 17</a></td></dhv:evaluate>
		<dhv:evaluate if="<%=seduta.getIdStato() != SedutaUnivoca.idStatoMacellato %>">
			<td class=""><a href="javascript:;"
				onClick="window.open('MacellazioneUnica.do?command=NuovaSeduta&idSeduta=<%=seduta.getId()%>&orgId=<%=seduta.getIdMacello() %>&popup=true', 'titolo', 'width=400, height=200, resizable, status, scrollbars=1, location');">
					Aggiungi capi</a></td>
		</dhv:evaluate>
	</tr>
</table>

<br />
<br />

<table class="details"
	style="border-collapse: collapse; table-layout: fixed;" width="100%"
	id="table2">

	<tr>
		<th>Specie</th>
		<th>Matricola</th>
		<th>Numero partita</th>
		<th>Stato</th>
	</tr>
	<%
		for (int i = 0; i< seduta.getListaCapi().size(); i++){
			CapoUnivoco capo = (CapoUnivoco) seduta.getListaCapi().get(i);
	%>

	<tr id="<%=capo.getId()%>" <%=(capo.getIdStato() == CapoUnivoco.idStatoDocumentale)? "style=\"background-color:rgba(255,255,0,0.5);\"" : ""%>>
		<td><dhv:evaluate
				if="<%=!(capo.getIdStato() == CapoUnivoco.idStatoDocumentale)%>">
				<a
					href="MacellazioneUnica.do?command=DettaglioMacellazione&idCapo=<%=capo.getId()%>">
					<%=specieList.getSelectedValue(capo.getSpecieCapo())%></a>
			</dhv:evaluate> <dhv:evaluate
				if="<%=(capo.getIdStato() == CapoUnivoco.idStatoDocumentale)%>">
				<%=specieList.getSelectedValue(capo.getSpecieCapo())%>
			</dhv:evaluate></td>
		<td><%=capo.getMatricola()%></td>
		<td><%=capo.getNumeroPartita()%></td>
		<td><%=statiCapi.getSelectedValue(capo.getIdStato())%>
	</tr>
	<%
		}
	%>
</table>
</dhv:container>
<script type='text/javascript'>
	//<![CDATA[ 

	var table2_Props = {
		col_0 : "select",
		col_2 : "select",
		col_3 : "none",
		display_all_text : " [ Mostra tutte ] ",
		sort_select : true
	};
	var tf2 = setFilterGrid("table2", table2_Props);
	//]]>
</script>