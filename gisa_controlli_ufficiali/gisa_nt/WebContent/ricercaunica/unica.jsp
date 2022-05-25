<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%String parametri = "";

String ragioneSociale = request.getParameter("ragioneSociale");
if (ragioneSociale!=null && !ragioneSociale.trim().equals(""))
	parametri+="&searchragioneSociale="+ragioneSociale;

String partitaIva = request.getParameter("partitaIva");
if (partitaIva!=null && !partitaIva.trim().equals(""))
	parametri+="&searchpartitaIva="+partitaIva;

String codFiscaleRappresentante = request.getParameter("codFiscaleRappresentante");
if (codFiscaleRappresentante!=null && !codFiscaleRappresentante.trim().equals(""))
	parametri+="&searchcodiceFiscaleSoggettoFisico="+codFiscaleRappresentante;

String comuneStabilimento = request.getParameter("comuneStabilimento");
if (comuneStabilimento!=null && !comuneStabilimento.trim().equals(""))
	parametri+="&searchcomuneSedeOperativa="+comuneStabilimento;

String indirizzoStabilimento = request.getParameter("indirizzoStabilimento");
if (indirizzoStabilimento!=null && !indirizzoStabilimento.trim().equals(""))
	parametri+="&searchindirizzoSedeOperativa="+indirizzoStabilimento;

String asl = request.getParameter("asl");
if (asl!=null && !asl.trim().equals(""))
	parametri+="&searchcodeOrgSiteId="+asl;

String numeroRegistrazione = request.getParameter("numeroRegistrazione");
if (numeroRegistrazione!=null && !numeroRegistrazione.trim().equals(""))
	parametri+="&searchnumeroRegistrazione="+numeroRegistrazione;

%>



<script>
function cercaDirettamente(form){
	form.action ='RicercaUnica.do?command=Search<%=parametri%>';
	loadModalWindow();
	form.submit();
}

function cambioParametri(form){
	form.action ='RicercaUnica.do?command=Dashboard';
	loadModalWindow();
	form.submit();
}
</script>


<form name="ricercaUnica" action="" method="post">
<center>
<table style="border:1px solid black">
<tr><td>
<!-- <input class="redBigButton" type="button" value="CAMBIA PARAMETRI E RICERCA IN TUTTI I CAVALIERI" onClick="cambioParametri(this.form)"/> -->
</td><td>
<input class="greyBigButton" type="button" value="RICERCA IN TUTTI I CAVALIERI" onClick="cercaDirettamente(this.form)"/>
</td></tr>
</table>
</center>
</form>