<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="tot" class="java.lang.String" scope="request"/>
<jsp:useBean id="totOK" class="java.lang.String" scope="request"/>
<jsp:useBean id="totKO" class="java.lang.String" scope="request"/>
<jsp:useBean id="num" class="java.lang.String" scope="request"/>
<jsp:useBean id="messaggio" class="java.lang.String" scope="request"/>
<jsp:useBean id="idImportMassivo" class="java.lang.String" scope="request"/>

<style>
.tableOsm {
table-layout: fixed;
word-wrap:break-word; 
border: 1px solid black;
border-collapse: collapse;
}
.tableOsm td,th{
border: 1px solid black;
font-size: 18px;
}
.tableOsm th{
background-color: 00ffee;
}
.esito {
font-size: 12px !important;
}

</style>

<table class="tableOsm" width="60%">
<col width="40%"><col width="10%"><col width="40%"><col width="10%">
<tr><th colspan="4">Risultati invio massivo OSM #<%=idImportMassivo %></th></tr>
<tr><td>OSM selezionati </td><td><%=num %></td> <td>OSM totali</td><td><%=tot %></td></tr>
<tr><td>OSM inviati OK (questo invio)</td><td><%=totOK %></td> <td>OSM inviati KO (questo invio)</td><td><%=totKO %></td></tr>
<tr><td colspan="4" align="center"><b>Log: </b><br/><br/><%=messaggio %></td></tr>
</table>

<center><a href="GestioneOSM.do?command=PrepareInvioMassivoOSM">Nuovo invio massivo</a></center>