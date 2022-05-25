<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<div style="border: 1px solid;">
<br>
<!-- Per modello 1 -->

Codice Azienda <input type="text" class="layout" size = "15" readonly value="<%=(Allevamento.getN_reg() != null ? Allevamento.getN_reg() :"") %>"><br>
Ragione Sociale<input type="text" class="layout" size="100" readonly value="<%= (Allevamento.getName() != null ? Allevamento.getName() :"") %>"><br>
Sede Operativa<input type="text" class="layout" size="100" readonly value="<%= (Allevamento.getComune_operativo() != null ? Allevamento.getComune_operativo()+" "+ Allevamento.getIndirizzo_operativo() : Allevamento.getComune()+" "+Allevamento.getIndirizzo())  %>"><br>
Sede legale<input type="text" class="layout" size="100" readonly value="<%=(Allevamento.getIndirizzo_legale() != null && !Allevamento.getIndirizzo_legale().equals("")) ? Allevamento.getIndirizzo_legale() : ""%>"><br>
Responsabile legale<input type="text" class="layout" size="100" readonly value="<%= (Allevamento.getProp() != null ? Allevamento.getProp() :"")  %>"><br>
Proprietario degli Animali<input type="text" class="layout" size="100" readonly value="<%= Allevamento.getProp()  %>"> <br>
Codice fiscale<input type="text" class="layout" size="100" readonly value="<%= (Allevamento.getCodice_fiscale() != null ? Allevamento.getCodice_fiscale() :"") %>"><br>
Conduttore Detentore degli animali<input type="text" class="layout" size="100" readonly value="<%= (Allevamento.getDet() != null ? Allevamento.getDet() :"") %>"><br>
Codice fiscale<input type="text" class="layout" size="100" readonly value="<%= Allevamento.getCodice_fiscale() %>"><br>

 

</div>