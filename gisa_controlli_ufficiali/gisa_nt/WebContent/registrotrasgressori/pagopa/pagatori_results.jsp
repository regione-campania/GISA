<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="listaPagatori" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="trasgressoreObbligato" class="java.lang.String" scope="request"/>


<script>
function setPagatore(trasgressoreObbligato, origine, tipo, nome, piva, indirizzo, civico, cap, comune, provincia, nazione, mail, telefono){
	nome = nome.replaceAll("#SINGLEQUOT#", "'").replaceAll("#DOUBLEQUOT#", "\"");
	comune = comune.replaceAll("#SINGLEQUOT#", "'").replaceAll("#DOUBLEQUOT#", "\"");;
	indirizzo = indirizzo.replaceAll("#SINGLEQUOT#", "'").replaceAll("#DOUBLEQUOT#", "\"");;
	provincia = provincia.replaceAll("#SINGLEQUOT#", "'").replaceAll("#DOUBLEQUOT#", "\"");;

	window.opener.document.getElementById("tipoPagatore"+trasgressoreObbligato).value = tipo;
	window.opener.document.getElementById("nome"+trasgressoreObbligato).value = nome;
	window.opener.document.getElementById("piva"+trasgressoreObbligato).value = piva;
	window.opener.document.getElementById("indirizzo"+trasgressoreObbligato).value = indirizzo;
	window.opener.document.getElementById("civico"+trasgressoreObbligato).value = civico;
	window.opener.document.getElementById("cap"+trasgressoreObbligato).value = cap;
	window.opener.document.getElementById("comune"+trasgressoreObbligato).value = comune;
	window.opener.document.getElementById("provincia"+trasgressoreObbligato).value = provincia;
	window.opener.document.getElementById("nazione"+trasgressoreObbligato).value = nazione;
	window.opener.document.getElementById("mail"+trasgressoreObbligato).value = mail;
	window.opener.document.getElementById("telefono"+trasgressoreObbligato).value = telefono;

	if (trasgressoreObbligato == 'T')
		window.opener.document.getElementById("trasgressorealtro").value = nome;
	else if (trasgressoreObbligato == 'O')
		window.opener.document.getElementById("obbligatoinSolidoAltro").value = nome;
	
	window.close();
}

</script>


<table class="details">
<tr><th>Origine</th><th>Tipo <br/>Pagatore</th><th>Ragione Sociale /<br/> Nominativo</th><th>Partita Iva /<br/> Codice Fiscale</th><th>Indirizzo</th><th>Civico</th><th>CAP</th><th>Comune</th><th>Cod<br/> Provincia</th><th>Nazione</th><th>Mail</th><th>Telefono</th><th></th></tr>

<%for (int i = 0; i<listaPagatori.size(); i++) {
	String rigaPagatore = (String) listaPagatori.get(i);
	String pagatore[] = rigaPagatore.split(";;");
	
	String origine = pagatore[0];
	String nome = pagatore[1];
	String piva = pagatore[2];
	String indirizzo = pagatore[3];
	String civico = pagatore[4];
	String cap = pagatore[5];
	String comune = pagatore[6];
	String provincia = pagatore[7];
	String nazione = pagatore[8];
	String mail = pagatore[9];
	String telefono = pagatore[10];
	String tipo = pagatore[11];

	%> 
<tr> 
<td><%=origine %></td>	
<td><%="F".equals(tipo) ? "PERSONA FISICA" : "G".equals(tipo) ? "PERSONA GIURIDICA/SOCIETA DI PERSONE/ASSOCIAZIONI" : "" %></td>	
<td><%=nome %></td>	
<td><%=piva %></td>	
<td><%=indirizzo %></td>	
<td><%=civico %></td>	
<td><%=cap %></td>	
<td><%=comune %></td>	
<td><%=provincia %></td>	
<td><%=nazione %></td>	
<td><%=mail %></td>	
<td><%=telefono %></td>	
<td><input type="button" value="SELEZIONA" onClick='setPagatore("<%=trasgressoreObbligato%>", "<%=origine %>", "<%=tipo %>", "<%=nome.replace("'", "#SINGLEQUOT#").replace("\"", "#DOUBLEQUOT#") %>", "<%=piva %>", "<%=indirizzo.replace("'", "#SINGLEQUOT#").replace("\"", "#DOUBLEQUOT#")  %>", "<%=civico %>", "<%=cap %>", "<%=comune.replace("'", "#SINGLEQUOT#").replace("\"", "#DOUBLEQUOT#")  %>", "<%=provincia.replace("'", "#SINGLEQUOT#").replace("\"", "#DOUBLEQUOT#")  %>", "<%=nazione %>", "<%=mail %>", "<%=telefono%>")'/>

</tr>
	
	<%
}

%>

</table>