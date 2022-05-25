<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>
<%@page import="org.aspcfs.modules.variazionestati.base.*"%>
<%@page import="java.util.*"%>
<jsp:useBean id="ListaStati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ListaOperazioni" class="org.aspcfs.utils.web.LookupList" scope="request"/>


<jsp:useBean id="id" class="java.lang.String" scope="request"/>
<jsp:useBean id="tipologia" class="java.lang.String" scope="request"/>

<% 	
ArrayList<LineaVariazione> listaLinee = (ArrayList<LineaVariazione>) request.getAttribute("listaLinee");
%>



<font color="red">Si ricorda che la funzione di variazione stati e' disponibile solo per linee di attivita' registrate ai sensi del Reg.852.</font>

<%if (listaLinee.size()==0) {%>
<BR/><BR/>
<font size="5">
NESSUNA LINEA MODIFICABILE TRAMITE VARIAZIONE STATI
</font>
<%} else { %>

<form id = "variazione" name="variazione" action="VariazioneStato.do?command=Insert&auto-populate=true" method="post">

<table class="details" cellpadding="4" width="100%">
<col width="15%"/>
<tr>
<th colspan="6">Variazione su linee produttive</th>
</tr>

<%
Iterator it = listaLinee.iterator();
int i=-1;
while (it.hasNext()){
i++;
LineaVariazione linea =(LineaVariazione) it.next();
LineaProduttiva lineaOriginale = linea.getLinea();
%>

<tr class="row<%=i%2%>"><td colspan="6"><u><%=lineaOriginale.getDescrizione_linea_attivita() %></u></td></tr> 
<tr class="row<%=i%2%>">
<td><b>Stato</b></td><td><%=ListaStati.getSelectedValue(lineaOriginale.getStato()) %> <br/> (<i><%=ListaOperazioni.getSelectedValue(linea.getIdOperazioneOrigine()) %></i>)</td>  
<td><b>Variazione</b></td>
<td>
<select id="operazione_<%=i%>" name="operazione_<%=i%>">
<option value="-1">Nessuna</option>
<% for (int k=0; k<linea.getListaOperazioni().size(); k++){ 
Operazione op = (Operazione) linea.getListaOperazioni().get(k);%>
<option value="<%=op.getCode()%>"><%=op.getDescription() %></option>
<%} %>
</select>
<input type="hidden" id="linea_<%=i%>" name="linea_<%=i%>" value="<%=lineaOriginale.getId_rel_stab_lp()%>"/>
<input type="hidden" id="idStato_<%=i%>" name="idStato_<%=i%>" value="<%=lineaOriginale.getStato()%>"/>
<input type="hidden" id="idOperazioneOrigine_<%=i%>" name="idOperazioneOrigine_<%=i%>" value="<%=linea.getIdOperazioneOrigine()%>"/>

</td>

<td> <b> Data Variazione</b> </td>

<td>
<input readonly type="text" id="dataVariazione_<%=i %>" name="dataVariazione_<%=i %>" size="10" />
<a href="#" onClick="cal19.select(document.getElementById('variazione').dataVariazione_<%=i %>,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
</td>

</tr>
<tr class="row<%=i%2%>">
<td><b>Controllo ufficiale</b></td>
<td colspan="5"><input type="text" id="idCu_<%=i%>" name="idCu_<%=i%>" readonly/> <a href="#" onClick="cancellaCu('<%=i %>'); return false">[Cancella]</a>

<%for (int j=0; j<linea.getListaCU().size(); j++){
	int idcu = linea.getListaCU().get(j);%>
	<a href="#" onClick="selezionaCu('<%=i%>', '<%=idcu %>'); return false"><%=idcu %></a> 
<% } %>


</td>
</tr>
<%} %>

<tr><td colspan="6"><input type="button" id="conferma" value="conferma" onClick="checkForm(this.form, '<%=listaLinee.size()%>')"/></td></tr>
</table>

<input type="hidden" id="tipologia" name="tipologia" value="<%=tipologia%>"/>
<input type="hidden" id="id" name="id" value="<%=id%>"/>


</form>
<%}%>

