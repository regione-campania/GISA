<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<br/><br/>

<% if (numAllegato.equals("2")) { %>
	
  <table width="100%" style="border-collapse: collapse"><tr><td style="border:1px solid black; text-align:center">Check-list per il controllo della muta non forzata negli allevamenti avicoli di galline ovaiole DLgs. 267/03 - DLgs. 146/01 - Nota Ministero salute prot. n. 23052 del 03/12/2013)</td></tr> </table>
 
 <table width="100%" style="border:1px solid black; text-align:center; border-collapse: collapse">
 <col width="35%">
 <tr><th>Tipologia verifiche</th> <th>Conforme</th> <th>Non conforme </th> <th> Note e/o osservazioni</th></tr>
<tr>
<td style="border:1px solid black;">
Sono presenti documenti attestanti la comunicazione al servizio veterinario di inizio muta non forzata, che specifica: numero, eta', peso medio degli animali, 
programma luminoso ed alimentare adottati
</td>
<td style="border:1px solid black; text-align:center"><label class="layout"> <% if (fixValore(Modulo.getListaCampiModulo().get("documenti_attestanti").get("documenti_attestanti")).equals("ON")) {%> X<%} %> </label>
</td>
<td style="border:1px solid black; text-align:center"><label class="layout"> <% if (!fixValore(Modulo.getListaCampiModulo().get("documenti_attestanti").get("documenti_attestanti")).equals("ON")) {%> X<%} %> </label>
</td>
<td style="border:1px solid black; text-align:center">  <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("documenti_attestanti_note").get("documenti_attestanti_note"))%></label> 
</td>
</tr>
<tr>
<td style="border:1px solid black;">Il programma di luce prevede almeno 8h luce/di</td>
<td style="border:1px solid black; text-align:center"><label class="layout"> <% if (fixValore(Modulo.getListaCampiModulo().get("programma_luce").get("programma_luce")).equals("ON")) {%> X<%} %> </label>
</td>
<td style="border:1px solid black; text-align:center"><label class="layout"> <% if (!fixValore(Modulo.getListaCampiModulo().get("programma_luce").get("programma_luce")).equals("ON")) {%> X<%} %> </label>
</td>
<td style="border:1px solid black; text-align:center"> <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("programma_luce_note").get("programma_luce_note"))%></label> 
</td>
</tr>
<tr><td style="border:1px solid black;">Viene somministrato mangime nelle 24 ore</td>
<td style="border:1px solid black; text-align:center"><label class="layout"> <% if (fixValore(Modulo.getListaCampiModulo().get("somministrato_mangime").get("somministrato_mangime")).equals("ON")) {%> X<%} %> </label>
</td>
<td style="border:1px solid black; text-align:center"><label class="layout"> <% if (!fixValore(Modulo.getListaCampiModulo().get("somministrato_mangime").get("somministrato_mangime")).equals("ON")) {%> X<%} %> </label>
</td>
<td style="border:1px solid black; text-align:center"> <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("somministrato_mangime_note").get("somministrato_mangime_note"))%></label> 
</td>
</tr>
<tr><td style="border:1px solid black;">Viene somministrata acqua a volonta'</td>
<td style="border:1px solid black; text-align:center"><label class="layout"> <% if (fixValore(Modulo.getListaCampiModulo().get("somministrata_acqua").get("somministrata_acqua")).equals("ON")) {%> X<%} %> </label>
</td>
<td style="border:1px solid black; text-align:center"><label class="layout"> <% if (!fixValore(Modulo.getListaCampiModulo().get("somministrata_acqua").get("somministrata_acqua")).equals("ON")) {%> X<%} %> </label>
</td>
<td style="border:1px solid black; text-align:center"> <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("somministrata_acqua_note").get("somministrata_acqua_note"))%></label> 
</td>
</tr>
<tr><td style="border:1px solid black;">La mortalita' tra inizio e fine muta non forzata non supera il 5%</td>
<td style="border:1px solid black; text-align:center"><label class="layout"> <% if (fixValore(Modulo.getListaCampiModulo().get("mortalita").get("mortalita")).equals("ON")) {%> X<%} %> </label>
</td>
<td style="border:1px solid black; text-align:center"><label class="layout"> <% if (!fixValore(Modulo.getListaCampiModulo().get("mortalita").get("mortalita")).equals("ON")) {%> X<%} %> </label>
</td>
<td style="border:1px solid black; text-align:center"> <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("mortalita_note").get("mortalita_note"))%></label> 
</td>
</tr>
<tr><td style="border:1px solid black;">
Se il sopralluogo ufficiale avviene alla fine del periodo di muta non forzata, verificare che il peso non sia diminuito oltre il 30% di quello iniziale</td>
<td style="border:1px solid black; text-align:center"><label class="layout"> <% if (fixValore(Modulo.getListaCampiModulo().get("sopralluogo").get("sopralluogo")).equals("ON")) {%> X<%} %> </label>
</td>
<td style="border:1px solid black; text-align:center"><label class="layout"> <% if (!fixValore(Modulo.getListaCampiModulo().get("sopralluogo").get("sopralluogo")).equals("ON")) {%> X<%} %> </label>
</td>
<td style="border:1px solid black; text-align:center"> <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("sopralluogo_note").get("sopralluogo_note"))%></label> 
</td>
</tr>

 </table>	
	
	
	<% }%>
	
<% if (numAllegato.equals("5")) { %>	

VALUTAZIONE DEL PIANO DI AUTOCONTROLLO/BUONE PRATICHE (se presente) <br/>
....................................................................................<br/>
....................................................................................<br/>
....................................................................................<br/>
....................................................................................<br/>
....................................................................................<br/>
....................................................................................<br/>
....................................................................................<br/>
....................................................................................<br/>
....................................................................................<br/>

GIUDIZIO FINALE <br/>
....................................................................................<br/>
....................................................................................<br/>
....................................................................................<br/>
....................................................................................<br/>
....................................................................................<br/>
....................................................................................<br/>
....................................................................................<br/>
....................................................................................<br/>
....................................................................................<br/>
<br/><br/><br/>

___________________________, li _____________________________________

<% } %>
	