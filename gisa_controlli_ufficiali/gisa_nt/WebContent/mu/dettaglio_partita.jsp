<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
 <%@page import="org.aspcfs.modules.mu.base.CapoUnivocoList"%>
<jsp:useBean id="specieList"			class="org.aspcfs.utils.web.LookupList" scope="request" />
 <jsp:useBean id="specieBovine"			class="org.aspcfs.utils.web.LookupList" scope="request" />
 <jsp:useBean id="razzeBovine"			class="org.aspcfs.utils.web.LookupList" scope="request" />
 <jsp:useBean id="categorieBovine"			class="org.aspcfs.utils.web.LookupList" scope="request" />
 <jsp:useBean id="categorieBufaline"			class="org.aspcfs.utils.web.LookupList" scope="request" />
 <jsp:useBean id="catRischio"			class="org.aspcfs.utils.web.LookupList" scope="request" />
 <jsp:useBean id="PianiRisanamento"			class="org.aspcfs.utils.web.LookupList" scope="request" />
  <jsp:useBean id="partita" class="org.aspcfs.modules.mu.base.PartitaUnivoca" scope="request"/>
  <jsp:useBean id="statiCapi"			class="org.aspcfs.utils.web.LookupList" scope="request" />
 <%@page import="org.aspcfs.modules.mu.base.CapoUnivoco"%>
<%@ page import="java.util.*" %>  

<%@ include file="../../initPage.jsp" %>

<script>
function aggiungiDaPartita(form){
	form.submit();
}
</script>


<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="MacellazioneUnica.do?command=List&orgId=<%=partita.getIdMacello()%>">Home macellazioni
		</td>
	</tr>
</table>

<form name="nuovaSeduta" id="nuovaSeduta" action="MacellazioneUnica.do?command=NuovaSeduta" method="post" >

<!-- <input type="button" value="Aggiungi a seduta" onClick="aggiungiDaPartita(this.form)"/> -->
  
 <input type="hidden" id="orgId" name="orgId" value="<%=partita.getIdMacello()%>"/> 
  <input type="hidden" id="idPartita" name="idPartita" value="<%=partita.getId()%>"/> 
<table class="details" layout="fixed" width="50%">
<col width="180px">
<th colspan="2">Dettaglio partita</th> 
<tr><td class="formLabel">Codice azienda di provenienza</td> <td><%=partita.getCodiceAziendaProvenienza() %></td></tr>
<tr><td bgcolor="yellow" style="text-align:right">Numero partita</td> <td><%=partita.getNumeroPartita() %> </td></tr>
<tr><td class="formLabel">Commerciante/proprietario degli animali</td> <td><%=partita.getProprietarioAnimali() %></td></tr>
<tr><td class="formLabel">Codice azienda di nascita</td> <td><%=partita.getCodiceAziendaNascita() %></td></tr>
<tr><td class="formLabel">Vincolo sanitario</td> <td> <input type="checkbox" disabled id="vincoloSanitario"  name="vincoloSanitario" <%=(partita.isVincoloSanitario()) ? "checked" : "" %>/> MOTIVO: <%=partita.getMotivoVincoloSanitario() %></td></tr>
<tr><td class="formLabel">Mod 4</td> <td><%=partita.getMod4() %></td></tr>
<tr><td class="formLabel">Data mod 4</td> <td> <%=toDateasString(partita.getDataMod4()) %></td></tr>
<tr><td class="formLabel">Macellazione differita</td> <td> 	<%=PianiRisanamento.getSelectedValue(partita.getMacellazioneDifferita()) %>  </td></tr> 
<tr><td class="formLabel">Disponibili info catena alimentare</td> <td> <input type="checkbox" disabled id="infoCatenaAlimentare" name="infoCatenaAlimentare" <%=(partita.isInfoCatenaAlimentare()) ? "checked" : "" %>/></td></tr>
<tr><td class="formLabel">Data di arrivo al macello</td> <td> <%=toDateasString(partita.getDataArrivoMacello()) %>       
<input type="checkbox" disabled id="dataArrivoMacelloDicharata" name="dataArrivoMacelloDicharata" <%=(partita.isDataArrivoMacelloDichiarata()) ? "checked" : "" %>/> Data dichiarata dal gestore</td></tr>
<th colspan="2">Identificazione mezzo di trasporto</th> 
<tr><td class="formLabel">Tipo</td> <td><%=partita.getMezzoTipo() %></td></tr>
<tr><td class="formLabel">Targa</td> <td><%=partita.getMezzoTarga() %></td></tr>
<tr><td class="formLabel">Trasporto superiore a 8 ore</td> <td> <input type="checkbox" disabled id="trasportoSuperiore" name="trasportoSuperiore" <%=(partita.isTrasportoSuperiore()) ? "checked" : "" %>/></td></tr>
<th colspan="2">Veterinari addetti al controllo</th> 
<tr>
<td colspan="2">1. <%=partita.getVeterinario1() %>
</td>
</tr>
<tr>
<td colspan="2">2. <%=partita.getVeterinario2() %>
</td>
</tr>
<tr>
<td colspan="2">3. <%=partita.getVeterinario3() %>
</td>
</tr>


</table>


<br/><br/>

<%-- <a href="javascript:;"  onClick="window.open('MacellazioneUnica.do?command=GestisciEsercenti&idPartita=<%=partita.getId()%>', 'titolo', 'width=400, height=200, resizable, status, scrollbars=1, location');">GESTIONE ESERCENTI</a> --%>
<table class="details" layout="fixed"  width="50%">
<th colspan="2">Informazioni capi</th>
<tr><td class="formLabel">Specie capi</td> 
<td>
<table width="100%">
<tr><th> Specie </th> <th> Num. capi vivi </th> <th> Num. capi deceduti</th></tr>
<%
HashMap<String, Integer> grigliaSpecie= partita.getListaCapiNumeri();
		Iterator<String> itSpecie = grigliaSpecie.keySet().iterator();
		while(itSpecie.hasNext())
		{
			String specie = itSpecie.next(); 
			int num = 0;
			int numDeceduti = 0;
			if (!specie.contains("_deceduti")) {
				 num =  (grigliaSpecie.containsKey(specie)) ?  grigliaSpecie.get(specie) : 0; 
				 numDeceduti = (grigliaSpecie.containsKey(specie+"_deceduti")) ?  grigliaSpecie.get(specie+"_deceduti") : 0; %>
				 <tr><td><%=specie %></td> <td><%=num %> </td><td> <%=numDeceduti %></td></tr>		
				 
			<%}else if (specie.contains("_deceduti") && !(grigliaSpecie.containsKey( specie.substring(0, specie.indexOf("_"))))){
				numDeceduti = grigliaSpecie.get(specie);
				%>
				 <tr><td><%=specie.substring(0, specie.indexOf("_")) %></td> <td><%=num %></td><td> <%=numDeceduti %></td></tr>		
		<%	}
				}%>
		
	
			


</table></td></tr> </table>

<br/><br/>

<%HashMap <Integer, Integer> map = CapoUnivocoList.calcolaStatistiche(partita.getListaCapi()); %>

<table class="details" layout="fixed"  width="50%">
<th colspan="2">Dettaglio sintetico seduta</th>
<tr><td class="formLabel">Statistiche</td> 
<td>
<table width="100%">
<tr><th>Stato capo  </th> <th> Totale </th></tr>
<%Iterator it = map.entrySet().iterator();
    while (it.hasNext()) {
        Map.Entry pair = (Map.Entry)it.next();
        
   
   %>
<tr><td><%=statiCapi.getSelectedValue((Integer)pair.getKey()) %>  </td> <td> <%=pair.getValue() %> </td></tr>
 <% }%>

</table></td></tr> </table>


</br></br>

<table class="details" style="border-collapse: collapse;table-layout:fixed;" width="100%">
<tr><td colspan="2">
<% for (int i = 0; i< partita.getListaCapi().size(); i++){
	CapoUnivoco capo = (CapoUnivoco) partita.getListaCapi().get(i);
	if (capo.getSpecieCapo()==1) {%>
<tr>
<th>Capo <%=specieList.getSelectedValue(capo.getSpecieCapo())%></th> 
<td bgcolor="#d3d3d3">Matricola</td> 
<td><%=capo.getMatricola() %></td>
<td bgcolor="#d3d3d3">Categoria</td>
<td><%=specieBovine.getSelectedValue(capo.getCategoriaCapo()) %> 
<%=categorieBovine.getSelectedValue(capo.getCategoriaBovina()) %> 
<%=categorieBufaline.getSelectedValue(capo.getCategoriaBufalina()) %> 
<%=razzeBovine.getSelectedValue(capo.getRazzaBovina()) %>
</td>
<td bgcolor="#d3d3d3">Sesso</td>
<td><%=capo.getSesso() %>	</td>
<td bgcolor="#d3d3d3">Data di nascita</td>
<td><%=toDateasString(capo.getDataNascita()) %></td>
<td bgcolor="#d3d3d3">Condizioni Particolari del capo</td>
<td><%=catRischio.getSelectedValue(capo.getCategoriaRischio()) %></td>
<td bgcolor="#d3d3d3">Arrivato Deceduto</td>
<td><%=(capo.isFlagArrivatoDeceduto()) ? "SI" : "NO" %></td>
</tr>
	
	
<% }
}
%>
</td></tr>  
</table>