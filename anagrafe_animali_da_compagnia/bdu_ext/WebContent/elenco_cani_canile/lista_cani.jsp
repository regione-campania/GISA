<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>

<%@page import="org.aspcfs.modules.anagrafe_animali.base.Animale,org.aspcfs.modules.opu.base.*"%>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>	
<jsp:useBean id="listaAnimali" class="org.aspcfs.modules.anagrafe_animali.base.AnimaleList"	scope="request" />
<jsp:useBean id="comuniList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<%@ include file="../initPage.jsp"%>

<script type="text/javascript">
function openListaHTML(idLinea){
	var res;
	var result;
		window.open('ElencoCaniInCanile.do?command=PrintDocumentoListaCani&id='+idLinea,'popupSelect',
		'height=595px,width=842px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}

/*function openRichiestaPDF(idLinea, idTipo){
	var res;
	var result;
		window.open('GestioneDocumenti.do?command=ListaDocumentiByTipo&tipo='+idTipo+'&idLinea='+idLinea,'popupSelect',
		'height=200px,width=842px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}*/
</script>


<script type="text/javascript">





$(document).ready(function() {
    $('#prova').submit(function() {
       // alert('ss');
        window.open('', 'formpopup', 'height=595px,width=842px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
        this.target = 'formpopup';
    });
});

</script>


<% 

	int numCani=listaAnimali.size();				
	if (listaAnimali.size()==0) {

%>
			<font color="red">
				Non ci sono cani nel canile selezionato	
			</font>

<% }  else  {%>
		<form method="post" id="prova" name="prova" action="BarcodeCanili.do?command=PrintRichiestaCampioni&sizeCani=<%=listaAnimali.size()%>&lineaId=<%=listaAnimali.getId_detentore() %>" />
			<table class="trails" cellspacing="0">
			<tr>
				<td width="100%">
					  <a href="Barcode_canili.do?command=Default"><dhv:label name="barcodecanili"></dhv:label></a> >
					  <dhv:label name="">Elenco Cani in Canile</dhv:label>
				</td>
			</tr>
		</table>
				<script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>
			<input type="button" onclick="openRichiestaPDF('PrintDocumentoListaCani', '-1', '-1', '-1', '<%=listaAnimali.getId_detentore() %>', '-1');" value="Stampa PDF" />
			<input type="button" onclick="location.href='ElencoCaniInCanile.do?command=stampaCSV&id=<%=listaAnimali.getId_detentore() %>'" value="stampa CSV" />
				

			
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
					<th ><font color="blue">PROPRIETARIO</font></th>
					<th ><font color="blue">DETENTORE</font></th>
					<th ><font color="blue">COMUNE PROPRIETARIO</font></th>
					<th width="10%"><font color="blue">ASL</font></th>
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
					<td>&nbsp;
				<dhv:evaluate
						if="<%=c.getNomeCognomeProprietario() != null%>">
						<%=toHtml(c.getNomeCognomeProprietario())%>
					</dhv:evaluate>
					</td>
					<td>&nbsp;
					 <dhv:evaluate
				if="<%=c.getNomeCognomeDetentore() != null%>">

				<%=toHtml(c.getNomeCognomeDetentore())%>

			</dhv:evaluate>
					</td>
					
					<dhv:evaluate if="<%= c.getIdComuneProprietario() < 0 %>">
								<td><%="  --- " %></td>
					</dhv:evaluate>
					<dhv:evaluate if="<%=(c.getIdComuneProprietario()  > 0 )  %>">
						<td>&nbsp; <%= comuniList.getSelectedValue(c.getIdComuneProprietario()) %></td>
					</dhv:evaluate>
					<td><%=AslList.getSelectedValue(c.getIdAslRiferimento() )  %></td>
			
				</tr>
				<%} %>
			</table>
		</form>

<%} %>