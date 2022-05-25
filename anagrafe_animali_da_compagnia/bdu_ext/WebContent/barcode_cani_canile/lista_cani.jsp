<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>

<%@page import="org.aspcfs.modules.anagrafe_animali.base.Animale,org.aspcfs.modules.opu.base.*"%>

<jsp:useBean id="specieList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>	
<jsp:useBean id="listaAnimali" class="org.aspcfs.modules.anagrafe_animali.base.AnimaleList"	scope="request" />
<jsp:useBean id="leish" class="java.lang.String" scope="request"/>	
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<%@ include file="../initPage.jsp"%>

<script type="text/javascript">
function settaNo(size)
{
	for (var j=0; j<size;j++){
		var no='checkCane_no'+j;
		var si='checkCane_si'+j;
		document.getElementById(no).checked=true;
		document.getElementById(si).checked=false;
		}

	
}

function settaSi(size)
{
	for (var j=0; j<size;j++){
		var si='checkCane_si'+j;
		var no='checkCane_no'+j;
		document.getElementById(si).checked=true;
		document.getElementById(no).checked=false;
		}

	
}


$(document).ready(function() {
    $('#prova').submit(function() {
       // alert('ss');
        window.open('', 'formpopup', 'height=595px,width=842px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
        this.target = 'formpopup';
    });
});


// function generaExcel(idCanile){
	
// 	var res;
// 	var result=
// 		window.open('BarcodeCanili.do?command=GeneraExcel&idCanile='+idCanile,'popupSelect',
// 		'height=200px,width=842px,left=200px, top=200px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
// 		var text = document.createTextNode('GENERAZIONE EXCEL IN CORSO.');
// 		span = document.createElement('span');
// 		span.style.fontSize = "30px";
// 		span.style.fontWeight = "bold";
// 		span.style.color ="#ff0000";
// 		span.appendChild(text);
// 		var br = document.createElement("br");
// 		var text2 = document.createTextNode('Attendere la chiusura di questa finestra entro qualche secondo...');
// 		span2 = document.createElement('span');
// 		span2.style.fontSize = "20px";
// 		span2.style.fontStyle = "italic";
// 		span2.style.color ="#000000";
// 		span2.appendChild(text2);
// 		result.document.body.appendChild(span);
// 		result.document.body.appendChild(br);
// 		result.document.body.appendChild(span2);
// 		result.focus();
		
// }

function generaExcel2(idCanile, leish){
	
	var res;
	var result=
		window.open('BarcodeCanili.do?command=StampaXLS2&idCanile='+idCanile+'&leish='+leish,'popupSelect',
		'height=200px,width=842px,left=200px, top=200px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
		var text = document.createTextNode('GENERAZIONE EXCEL IN CORSO.');
		span = document.createElement('span');
		span.style.fontSize = "30px";
		span.style.fontWeight = "bold";
		span.style.color ="#ff0000";
		span.appendChild(text);
		var br = document.createElement("br");
		var text2 = document.createTextNode('Attendere la chiusura di questa finestra entro qualche secondo...');
		span2 = document.createElement('span');
		span2.style.fontSize = "20px";
		span2.style.fontStyle = "italic";
		span2.style.color ="#000000";
		span2.appendChild(text2);
		result.document.body.appendChild(span);
		result.document.body.appendChild(br);
		result.document.body.appendChild(span2);
		result.focus();
		
	
		
}
</script>

<table class="trails" cellspacing="0">
		<tr>
			<td width="100%">
				  <a href="BarcodeCanili.do?command=Default"><dhv:label name="barcodecanili">Canili</dhv:label></a> >
				  <dhv:label name="">Elenco Cani in Canile</dhv:label>
			</td>
		</tr>
</table>


<% if (leish!=null && leish.equals("true")){ %>
<center><span style="background-color: #98FB98">ESTRAZIONE PER PIANO LEISHMANIA</span></center>
<%} %>


<% 

	int numCani=listaAnimali.size();				
	if (listaAnimali.size()==0) {

%>
			<font color="red">
				Non ci sono cani nel canile selezionato	
			</font>

<% }  else  {%>
		<form method="post" id="prova" name="prova" action="BarcodeCanili.do?command=PrintRichiestaCampioni&sizeCani=<%=listaAnimali.size()%>&lineaId=<%=listaAnimali.getId_detentore() %>" />

			
			
			<input type="submit" value="Conferma la richiesta"/>
			<input type="button" value="Annulla" onclick="location.href='BarcodeCanili.do?command=Default'" />
			<input type="button" value="Deseleziona tutti" onclick="javascript:settaNo(<%=listaAnimali.size() %>)" />
			<input type="button" value="Seleziona tutti" onclick="javascript:settaSi(<%=listaAnimali.size() %>)" />
<%-- 			<a href="BarcodeCanili.do?command=StampaXLS&idCanile=<%=listaAnimali.getId_detentore()%>" >Stampa XLS aggiornato</a> --%>
			
			<a href="#" onClick="generaExcel2('<%=listaAnimali.getId_detentore()%>', '<%=leish %>');return false;">Stampa XLS</a>
			
<%-- 			<a href="#" onClick="generaExcel('<%=listaAnimali.getId_detentore()%>');return false;">Stampa XLSX nuova versione</a> --%>
			
			
			<br />
			<br />
			<table  class="details"  cellspacing="0" cellpadding="4" border="0"  width="80%">
				<tr>
					<th colspan="14"> 
					 <dhv:label name="" ><font color="blue" size="2" ><i>Nel canile sono presenti <%=numCani %> cani vivi </i></font></dhv:label>
					</th>
				</tr>
				<tr>
					<th ><font color="blue">MICROCHIP</font></th>
					<th ><font color="blue">TATUAGGIO</font> </th>
					<th ><font color="blue">SPECIE</font> </th>
					<th ><font color="blue">PROPRIETARIO</font></th>
					<th ><font color="blue">DETENTORE</font></th>
					<th ><font color="blue">COMUNE PROPRIETARIO</font></th>
					<th ><font color="blue">SITUAZIONE ESAME LEISHMANIOSI</font></th>
					<th width="10%"><font color="blue">ASL</font></th>
					<th width="15%"><font color="blue"> RICHIESTA?</font> </th>
				</tr>
				
				
				<%  Animale c;
					for (int i = 0;i < listaAnimali.size(); i++) {
					c = (Animale) listaAnimali.get(i);
					if ((i % 2)==0){ 
				%>					
								
					
				<tr>
				<%} else  {%>
				<tr bgcolor="white">
				<%} %>
					<td>
						<dhv:evaluate if="<%= ("".equals(c.getMicrochip()) || c.getMicrochip() == null) %>">
								<%=" --- " %> 
						</dhv:evaluate>	
						<dhv:evaluate if="<%= !("".equals(c.getMicrochip()) || c.getMicrochip() == null) %>">
								<%=c.getMicrochip() %> 
						</dhv:evaluate>
					</td>						
					<td>
						<dhv:evaluate if="<%= ("".equals(c.getTatuaggio()) || c.getTatuaggio() == null) %>">
								<%="  --- " %> 
						</dhv:evaluate>	
						<dhv:evaluate if="<%= !("".equals(c.getTatuaggio()) || c.getTatuaggio() == null) %>">
								<%=c.getTatuaggio() %> 
						</dhv:evaluate>
					</td>
					<td>
						<%=specieList.getSelectedValue(c.getIdSpecie())%> 
					</td>
					<td>&nbsp;
					<dhv:evaluate if="<%=c.getNomeCognomeProprietario() != null%>">
						<%=c.getNomeCognomeProprietario() %>
					</dhv:evaluate>
					</td>
					<td>&nbsp;
					<dhv:evaluate if="<%=c.getNomeCognomeDetentore() != null%>">
					<%=c.getNomeCognomeDetentore() %>
					</dhv:evaluate>
					</td>
					
					<dhv:evaluate if="<%= c.getProprietario()==null %>">
								<td><%="  --- " %></td>
					</dhv:evaluate>
					<dhv:evaluate if="<%=( c.getProprietario()!=null && c.getProprietario().getIdOperatore() > -1)  %>">
					<%Stabilimento stab = (Stabilimento) c.getProprietario().getListaStabilimenti().get(0); %>
								<td>&nbsp;<%=ComuniList.getSelectedValue(stab.getSedeOperativa().getComune())  %></td>
					</dhv:evaluate>
					<td><dhv:evaluate if="<%=c.isEsitoLeishAnnoCorrente()%>">
						Sottoposto già ad un esame entro anno corrente
					</dhv:evaluate>
					
					<dhv:evaluate if="<%=!(c.isEsitoLeishAnnoCorrente())%>">
						Nessun esame anno corrente
					</dhv:evaluate>
					
					</td>
					<td><%=AslList.getSelectedValue(c.getIdAslRiferimento() )  %></td>
					<td>
						
						<input type="radio" id="checkCane_si<%=i%>" name="checkCane<%=c.getMicrochip()%>" value="true" checked> Si
						<input type="radio" id="checkCane_no<%=i%>"  name="checkCane<%=c.getMicrochip()%>" value="false" > No<br>
											
					</td>	
				</tr>
				<%} %>
			</table>
			
						<input type="hidden" id="leish" name="leish" value="<%=leish %>" />
			
		</form>

<%} %>