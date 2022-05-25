<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="Allevamento" class="org.aspcf.modules.controlliufficiali.base.Organization" scope="request"/>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<P style="text-align: right">
	<b>Allegato 5</b>	   
</P>
<P text-align="center" style="font-size:18pt; text-align: center">
	<b>PROTEZIONE DEGLI ANIMALI NEGLI ALLEVAMENTI <input style="font-size: 18pt;" type="text" class="layout" value="POLLI DA CARNE"><br></b>
	   RISULTATI DEI CONTROLLI EFFETTUATI PRESSO LE AZIENDE
</P>
<BR>
<p style="font-size: 15pt;">
 REGIONE <input type="text" class="layout" value="CAMPANIA"/>
 ASL <input type="text" class="layout" readonly value="<%= Allevamento.getAsl()%>"/>
<br> 
EXTRAPIANO: <span class="NocheckedItem">SI&nbsp;&nbsp;</span>
<span class="NocheckedItem">NO&nbsp;&nbsp;</span>
DATA DEL CONTROLLO:<input type="text" class="layout" readonly value="<%= Allevamento.getGiornoReferto()+ " " + Allevamento.getMeseReferto() + " "+ Allevamento.getAnnoReferto()%>" /> 
</p>
<div style="text-align:justify;border: 1px solid;">
<br>
Codice Azienda <input type="text" class="layout" readonly value="<%=Allevamento.getN_reg() %>">Denominazione <input size="110" type="text" class="layout" readonly value="<%=Allevamento.getName()%>"><br>
Indirizzo azienda <input size="150" type="text" class="layout" readonly value="<%=Allevamento.getIndirizzo()+ " " + Allevamento.getCap() + " " +Allevamento.getComune() +" ("+Allevamento.getProv()+")" %>"><br>
Indirizzo sede legale <input size="145" type="text" class="layout" readonly value=""><br>
Proprietario struttura <input size="100" type="text" class="layout" readonly value=""> codice fiscale <input size="20" type="text" class="layout" readonly value=""><br>
Proprietario capi <input size="145" type="text" class="layout" readonly value="<%= Allevamento.getProp()%>"><br>
Codice fiscale <input size="20" type="text" class="layout" readonly value="<%= Allevamento.getCf_prop() %>"> Tel. <input type="text" class="layout" readonly value=""><br>
Detentore <input size="160" type="text" class="layout" readonly value="<%=Allevamento.getDet()%>"><br>
Codice fiscale <input size="20" type="text" class="layout" readonly value="<%=Allevamento.getCf_det() %>"> Tel. <input type="text" class="layout" readonly value=""><br>
Data inizio attività <input type="text" class="layout" readonly value="<%=Allevamento.getGiornoNascita()+ " " +Allevamento.getMeseNascita()+" " +Allevamento.getAnnoNascita() %>"><br>
N. totale capannoni <input type="text" class="layout" readonly value=""> n. totali capannoni attivi all'atto dell'ispezione<br>
N. totale animali presenti <input type="text" class="layout" readonly value=""> 
Capacità massima di allevamento autorizzata dalla ASL competente per il territorio:<br> 
<span class="NocheckedItem"> 33 kg/mq&nbsp;&nbsp;&nbsp;&nbsp;</span><span class="NocheckedItem">39 kg/mq&nbsp;&nbsp;&nbsp;&nbsp;</span><span class="NocheckedItem" > 42kg/mq&nbsp;&nbsp;&nbsp;&nbsp;</span> 
</div>    
<br>
<br>
<br>
<br>
<B>LEGENDA</B>
<table border="1">
	<tr>
		<td style="text-align:center">
			<b>Categoria delle non conformità</b>
		</td>
		<td>
			<b>AZIONI INTRAPRESE DALL'AUTORITA' COMPETENTE</b>
		</td>
	</tr>
	<tr>
		<td style="text-align:center">
			<b>A</b>
		</td>
		<td>
			Richiesta di rimediare alle non conformità entro un termine inferiore a 3 mesi. <br>
			Nessuna sanzione amministrativa o penale immediata.
		</td>
	</tr>
	<tr>
		<td style="text-align:center">
			<b>B</b>
		</td>
		<td>
			Richiesta di rimediare alle non conformità entro un termine superiore a 3 mesi.<br>
			 Nessuna sanzione amministrativa o penale immediata.
		</td>
	</tr>
	<tr>
		<td style="text-align:center">
			<b>C</b>
		</td>
		<td>
			Sanzione amministrativa o penale immediata.
		</td>
	</tr>
</table>

<div class="fine" style="height: 150px;">&nbsp;</div>

