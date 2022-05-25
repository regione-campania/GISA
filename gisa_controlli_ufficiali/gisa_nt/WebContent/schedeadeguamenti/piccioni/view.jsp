<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="DettaglioPiccioni" class="org.aspcfs.modules.schedeadeguamenti.base.SchedaPiccioni" scope="request"/>
<jsp:useBean id="MessaggioSchedaPiccioni" class="java.lang.String" scope="request"/>
<jsp:useBean id="orgId" class="java.lang.String" scope="request"/>

<% if (MessaggioSchedaPiccioni!=null && !MessaggioSchedaPiccioni.equals("")) {%>
<script>
alert("<%=MessaggioSchedaPiccioni%>");
</script>
<% } %>

<br/><br/>
<center>
<table class="details">
<tr><th colspan="2">STIMA DEL PATRIMONIO DEL COLOMBO DI CITTA' NELLE AZIENDE AGRICOLE E/O ZOOTECNICHE </th></tr>
<tr><td>STIMA POPOLAZIONE COLOMBI</td><td> 
<input type="radio" disabled id="stimaPopolazioneD" name="stimaPopolazione" value="D" <%=(DettaglioPiccioni!=null && DettaglioPiccioni.getStimaPopolazione()!=null && DettaglioPiccioni.getStimaPopolazione().equals("D")) ? "checked" : "" %>>0/100<br/>
<input type="radio" disabled id="stimaPopolazioneA" name="stimaPopolazione" value="A" <%=(DettaglioPiccioni!=null && DettaglioPiccioni.getStimaPopolazione()!=null && DettaglioPiccioni.getStimaPopolazione().equals("A")) ? "checked" : "" %>>100/200<br/>
<input type="radio" disabled id="stimaPopolazioneB" name="stimaPopolazione" value="B" <%=(DettaglioPiccioni!=null && DettaglioPiccioni.getStimaPopolazione()!=null && DettaglioPiccioni.getStimaPopolazione().equals("B")) ? "checked" : "" %>>250/500<br/>
<input type="radio" disabled id="stimaPopolazioneC" name="stimaPopolazione" value="C" <%=(DettaglioPiccioni!=null && DettaglioPiccioni.getStimaPopolazione()!=null && DettaglioPiccioni.getStimaPopolazione().equals("C")) ? "checked" : "" %>>Oltre 500
</td></tr>

<tr><td>UTILIZZO SISTEMI ECOLOGICI DI DISSASUASIONE O PREVENZIONE</td><td>
<input type="radio" disabled id="utilizzoSistemiS" name="utilizzoSistemi" value="S" <%=(DettaglioPiccioni!=null && DettaglioPiccioni.getUtilizzoSistemi()!=null && DettaglioPiccioni.getUtilizzoSistemi().equals("S")) ? "checked" : "" %>> SI<br/>
<input type="radio" disabled id="utilizzoSistemiN" name="utilizzoSistemi" value="N" <%=(DettaglioPiccioni!=null && DettaglioPiccioni.getUtilizzoSistemi()!=null && DettaglioPiccioni.getUtilizzoSistemi().equals("N")) ? "checked" : "" %>> NO
</td></tr>

<tr><td>SE SI, QUALI</td><td>

<input type="checkbox" disabled id="retiProtezione" name="retiProtezione" value="S" <%=(DettaglioPiccioni!=null && DettaglioPiccioni.getRetiProtezione()!=null && DettaglioPiccioni.getRetiProtezione().equals("S")) ? "checked" : "" %>> Reti di protezione<br/>
<input type="checkbox" disabled id="cannonciniDissuasori" name="cannonciniDissuasori" value="S" <%=(DettaglioPiccioni!=null && DettaglioPiccioni.getCannonciniDissuasori()!=null && DettaglioPiccioni.getCannonciniDissuasori().equals("S")) ? "checked" : "" %>> Cannoncini dissuasori<br/>
<input type="checkbox" disabled id="dissuasoriAghi" name="dissuasoriAghi" value="S" <%=(DettaglioPiccioni!=null && DettaglioPiccioni.getDissuasoriAghi()!=null && DettaglioPiccioni.getDissuasoriAghi().equals("S")) ? "checked" : "" %>> Dissuasori ad aghi<br/>
<input type="checkbox" disabled id="dissuasoriSonori" name="dissuasoriSonori" value="S" <%=(DettaglioPiccioni!=null && DettaglioPiccioni.getDissuasoriSonori()!=null && DettaglioPiccioni.getDissuasoriSonori().equals("S")) ? "checked" : "" %>> Dissuasori sonori/visivi<br/>
<input type="checkbox" disabled id="altro" name="altro" value="S" <%=(DettaglioPiccioni!=null && DettaglioPiccioni.getAltro()!=null && DettaglioPiccioni.getAltro().equals("S")) ? "checked" : "" %>> Altro

</td></tr>

<dhv:permission name="allevamenti-allevamenti-schedapiccioni-add">
<%if (DettaglioPiccioni==null || DettaglioPiccioni.getId()<=0) { %>
<tr><td colspan="2" align="center">
<input type="button" onClick="window.location.href='SchedeAdeguamentiAction.do?command=AddSchedaPiccioni&orgId=<%=orgId%>'" value="COMPILA"/>
</td></tr>
<% } %>
</dhv:permission>

</table>

</center>