<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
 
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="ListaPratiche" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="ListaTipoRichieste" class="java.util.ArrayList" scope="request"/>

<%@ page import="org.aspcfs.modules.gestionepratiche.base.*" %>
<%@ page import="org.aspcfs.modules.gestionepratiche.actions.*" %>
  
  <%@ include file="../initPage.jsp" %>
  
  

<%-- Trails --%>
	<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="GestionePraticheAction.do?command=HomeGins">PRATICHE SUAP 2.0</a> >
			GESTIONE ATT. FISSE, MOBILI E RICONOSCIMENTO
		</td>
	</tr>
	</table>
<%-- Trails --%>

<div align="right" style="padding-right: 1%">
<img class="masterTooltip" onclick="apri_mini_guida();" src="images/questionmark.png" title="GUIDA FUNZIONALITA DI QUESTA PAGINA"  width="20">
</div>

<br>
<div align="center">
<input type="button" class="yellowBigButton" style="width: 300px;" 
		onclick="loadModalWindowCustom('Attendere Prego...'); window.location.href='GestionePraticheAction.do?command=CreaPratica';" 
		value="NUOVA PRATICA" />
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type="button" class="yellowBigButton" style="width: 300px;" 
		onclick="loadModalWindowCustom('Attendere Prego...'); window.location.href='GestionePraticheAction.do?command=SearchFormPratica';" 
		value="RICERCA IN TUTTE LE PRATICHE" />
</div>
<br>
<center>
<h3>
<b>Lista ultime pratiche</b><br/>
<i>Di seguito sono elencate le ultime 100 pratiche inserite nel sistema.</i>
</h3>
<br>
<table class="table details" id="tabella_pratiche" style="border-collapse: collapse" border="1" width="100%" cellpadding="5"> 
<tr>
	<th style="text-align:center; width:7%;"><p>data inserimento</p></th>
	<th style="text-align:center; width:15%;"><p>Numero pratica</p></th>
	<th style="text-align:center; width:25%;">
		<p>Dati impresa e stabilimento</p>
		<p style="color: red; font-size: 70%" >* i dati impresa e stabilimento fanno riferimento allo 
		stabilimento vigente, se si vuole vedere la storia consultare il link 
		'storico operazioni stabilimento' dal dettaglio dello stesso
		</p>
	</th>
	<th style="text-align:center; width:10%;"><p>Comune</p></th>
	<th style="text-align:center; width:7%;"><p>data pec / data scia</p></th>
	<th style="text-align:center"><p>tipo pratica</p></th> 	
	<th style="text-align:center; width:10%;"><p>Utente</p></th> 
	<th style="text-align:center; width:5%;" ><p>ALLEGATI</p></th> 
	<th style="text-align:center; width:10%;"><p>STABILIMENTO COLLEGATO</p></th> 
</tr>
<tr>
	<th style="text-align:center;">
		<p><input type="text" id="data_ins_prat_col"  style="text-align:center; width:90%;" onkeyup="filtra_pratiche(this.id,0)" placeholder="cerca data" ></p>
	</th>
	<th style="text-align:center;">
		<p><input type="text" id="num_prat_col"  style="text-align:center; width:90%;" onkeyup="filtra_pratiche(this.id,1)" placeholder="cerca num. pratica" ></p>
	</th>
	<th style="text-align:center; ">
		<p><input type="text" id="dati_stab_col"  style="text-align:center; width:90%;" onkeyup="filtra_pratiche(this.id,2)" placeholder="cerca dati impresa/stabilimento" ></p>
	</th>
	<th style="text-align:center;">
		<p><input type="text" id="comune_ric_col"  style="text-align:center; width:90%;" onkeyup="filtra_pratiche(this.id,3)" placeholder="cerca comune" ></p>
	</th>
	<th style="text-align:center; ">
		<p><input type="text" id="data_ric_col"  style="text-align:center; width:90%;" onkeyup="filtra_pratiche(this.id,4)" placeholder="cerca data" ></p>
	</th>
	<th style="text-align:center">
		<p><input type="text" id="tipo_pratica_col"  style="text-align:center; width:90%;" onkeyup="filtra_pratiche(this.id,5)" placeholder="cerca tipo" ></p>
	</th> 
	
	<th style="text-align:center;">
		<p><input type="text" id="utente_col"  style="text-align:center; width:90%;" onkeyup="filtra_pratiche(this.id,6)" placeholder="cerca utente" ></p>
	</th> 
	<th style="text-align:center;" ></th> 
	<th style="text-align:center; ">
		<p>
			<select id="stato_col"  
				   style="text-align:center; font-size:120% ; width:90%;" 
				   onchange="filtra_pratiche(this.id,8)">
				<option value="">TUTTE</option>
				<option value="scheda da completare">SCHEDA DA COMPLETARE</option>
				<option value="scheda completa">SCHEDA COMPLETA</option>
		   </select>
		 </p>
	</th> 
</tr>

<% int j=0; 
for ( int i = 0; i<ListaPratiche.size(); i++) {
	
	Pratica p = (Pratica) ListaPratiche.get(i);
	String tipoPratica = "";
	if ( p.getSiteIdStab() == User.getSiteId() || User.getSiteId() == -1 ) {
		for (int z = 0; z < ListaTipoRichieste.size(); z++){
			Richiesta r = (Richiesta) ListaTipoRichieste.get(z);
			if(p.getIdTipologiaPratica() == r.getCode()) {
				tipoPratica = r.getLong_description();
			}
		}
	%>
	
<tr class="row<%=j%2%>">

<td align="center"><p><%=toDateasString(p.getDataInserimentoPratica()) %></p></td>

<td align="center"><p><%=p.getNumeroPratica() %></p></td>

<td align="center" style="word-break:break-all;">
	
	<%if(p.getStatoPratica() == 1){ %>
		<b><%=p.getRagioneSociale() %></b>
		<br>P. IVA/cod. fiscale: <%=p.getPartitaIva() %>
		<br>Num. Reg.: <%=p.getNumeroRegistrazione() %>
		<br>Indirizzo: <%=p.getIndirizzo() %>
	<%} %>
</td>

<td align="center"><%=p.getComuneRichiedente() %></td>
<td align="center"><%=toDateasString(p.getDataOperazione()) %></td>
<td align="center"><%=tipoPratica %></td>
<td align="center"> <dhv:username id="<%= p.getIdUtente() %>" /></td> 
<td align="center">
	<%String desc_operatore = "";
	if(p.getStatoPratica() == 1){
		desc_operatore = "<hr>DATI IMPRESA E STABILIMENTO<br>" +
				  "<br><b>RAGIONE SOCIALE</b>: " + p.getRagioneSociale() + 
				  "<br><b>PARTITA IVA/CODICE FISCALE</b>: " + p.getPartitaIva() + 
				  "<br><b>NUMERO REGISTRAZIONE</b>: " + p.getNumeroRegistrazione() +
				  "<br><b>INDIRIZZO</b>: " + p.getIndirizzo();
		desc_operatore = desc_operatore.replaceAll("&", "%26");
	}
	%>
	<a style="text-decoration:none"
		href='GestioneAllegatiGins.do?command=ListaAllegati&numeroPratica=<%=p.getNumeroPratica()%>&desc_operatore=<%=desc_operatore.replaceAll("'", " ")%>&alt_id=<%=p.getAltId()%>&stab_id=<%=p.getIdStabilimento()%>&idComunePratica=<%=p.getIdComuneRichiedente()%>'>
		<img src="gestione_documenti/images/archivio_icon.png" width="35" title="visualizza allegati"/>
	</a>&nbsp;&nbsp;

</td>
<td align="center">
	<%if(p.getStatoPratica() == 0 ){ %>
		
		<a style="" href="#" onclick="if(confirm('Passare alla gestione scheda?')==true)
				{
						loadModalWindowCustom('Attendere Prego...');
						window.location.href='GestionePraticheAction.do?command=CompletaPratica&stabId=<%=p.getIdStabilimento()%>&altId=<%=p.getAltId()%>&numeroPratica=<%=p.getNumeroPratica()%>&tipoPratica=<%=p.getIdTipologiaPratica()%>&dataPratica=<%=toDateasString(p.getDataOperazione())%>&comunePratica=<%=p.getIdComuneRichiedente()%>';		
				}">
		scheda da completare
		</a>
		<br><br>
		<a style="" href="#" onclick="if(confirm('Annullare pratica suap n° <%=p.getNumeroPratica() %> ?')==true) 
				{
					loadModalWindowCustom('Attendere Prego...');
					window.location.href='GestionePraticheAction.do?command=EliminaPratica&id_pratica=<%=p.getId_pratica() %>';
				}">
		annulla pratica
		</a>
	<%} else if (p.getStatoPratica() == 1) { %>
		<a style="" href="#" onclick="if(confirm('Visualizzare scheda?')==true)
				{
						loadModalWindowCustom('Attendere Prego...');
						window.location.href='GestioneAnagraficaAction.do?command=Details&stabId=<%=p.getIdStabilimento()%>';		
				}">
		scheda completa
		</a>
	<%} %>

</td>
</tr>

<%
  j++;
	}
}
%>
</table>
<br>
<br>

<!-- inizio parte popup per miniguida -->
<%@include file="/gestione_pratica/guide_html/mini_guida_lista_pratiche_suap.html" %>
<jsp:include page="/gestione_pratica/mini_guida.jsp" />
<!-- fine parte popup per miniguida -->

<script>
function filtra_pratiche(colid,ordine_col) {
  var input, filter, table, tr, td, i, txtValue;
  input = document.getElementById(colid);
  filter = input.value.toUpperCase();
  table = document.getElementById("tabella_pratiche");
  tr = table.getElementsByTagName("tr");
  for (i = 0; i < tr.length; i++) {
    td = tr[i].getElementsByTagName("td")[ordine_col];
    if (td) {
      txtValue = td.textContent || td.innerText;
      if (txtValue.toUpperCase().indexOf(filter.trim()) > -1) {
        tr[i].style.display = "";
      } else {
        tr[i].style.display = "none";
      }
    }       
  }
}

function filtra_pratiche_select(colid,ordine_col){
	
	var input, filter, table, tr, td, i, txtValue;
	input = document.getElementById(colid);
	filter = input.value.toUpperCase();
	table = document.getElementById("tabella_pratiche");
	tr = table.getElementsByTagName("tr");
	for (i = 0; i < tr.length; i++) {
	  td = tr[i].getElementsByTagName("td")[ordine_col];
	  if (td) {
	    txtValue = td.textContent || td.innerText;
	    if (txtValue.toUpperCase().indexOf(filter.trim()) > -1) {
	      tr[i].style.display = "";
	    } else {
	      tr[i].style.display = "none";
	    }
	  }       
	}
}

</script>  		

  	
 