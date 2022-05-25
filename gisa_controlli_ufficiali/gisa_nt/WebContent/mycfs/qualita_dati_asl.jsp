<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<jsp:useBean id="listaQualita" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="msg" class="java.lang.String" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>


<script>
function apriLista(tipo, idAsl){
	if (confirm ('ATTENZIONE. La lista potrebbe impiegare molto tempo ad essere caricata.')){
		window.open('MyCFS.do?command=QualitaDatiAslLista&tipo='+tipo+'&idAsl='+idAsl, 'popupSelect',
	    'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
	}
	
}


</script>


<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>    
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>


<% if (msg!=null && !msg.equals("")) {%>

<font color="red"><%=msg %></font><br/><br/>

<form id = "addAccount" name="addAccount" action="MyCFS.do?command=QualitaDatiAsl&auto-populate=true" method="post" onSubmit="loadModalWindow();">
<b>ASL di cui analizzare la qualità dei dati</b>:
<%=SiteList.getHtmlSelect("idAsl", -1) %>
<input type="submit" value="CONFERMA"/>
</form>

<% } else {  

%>

<% for (int i = 0; i<listaQualita.size(); i++) { 
String elem = (String) listaQualita.get(i);
String res[] = elem.split(";;");

String idAsl = res[0];
String asl = res[1];

String numCuAperti = res[2];
String numCuTotali = res[3];

String numSorveglianzeSenzaChecklist = res[4];
String numSorveglianzeTotali = res[5];

String numErrataCorrigeMesePrecedente = res[6];
String numErrataCorrigeMeseMesePrecedente = res[7];
String numErrataCorrigeMeseMeseMesePrecedente = res[8];

String numErrataCorrigeArt17MesePrecedente = res[9];
String numErrataCorrigeArt17MeseMesePrecedente = res[10];
String numErrataCorrigeArt17MeseMeseMesePrecedente = res[11];


/*
String numErrataCorrigeMesePrecedente = res[6];
String numErrataCorrigeMeseMesePrecedente = String.valueOf((int)(Math.random()*50));
String numErrataCorrigeMeseMeseMesePrecedente = String.valueOf((int)(Math.random()*50));

String numErrataCorrigeArt17MesePrecedente = res[9];
String numErrataCorrigeArt17MeseMesePrecedente = String.valueOf((int)(Math.random()*50));
String numErrataCorrigeArt17MeseMeseMesePrecedente = String.valueOf((int)(Math.random()*50));
*/

%>
<table class="details" width="100%" cellspacing="10" cellpadding="10">
<col width="50%">
<tr><th colspan="2" style="text-align:center !important"><a style="text-decoration: none; color:#000000" href="MyCFS.do?command=QualitaDatiAsl&idAsl=<%=idAsl%>"><font size="5px">QUALITA' DEI DATI PER L'ASL <font color="red"><%=asl %></font></font></a><br/>
<i>Cliccare per accedere al grafico</i><br/>
 <img style="border:2px solid black" height="50px" width="100px" src="gestione_documenti/schede/images/<%=asl.toLowerCase() %>.jpg" /> </th></tr>

<tr>
<th style="text-align:center !important"> Controlli Ufficiali Attualmente Aperti / CU Totali</th>
<th style="text-align:center !important"> Controlli Ufficiali in Sorveglianza senza Checklist / CU in sorveglianza totali</th>
</tr>

<tr>
<td align="center"> <a href="#" onClick="apriLista(1, '<%=idAsl%>')"><font size="3px"><%=numCuAperti %></font></a> /<%=numCuTotali %><br/>

<% if (listaQualita.size()==1) {%>
<div id="piechartCu<%=idAsl %>" style="border: 1px solid violet ; width: 350px; height: 350px;"></div>
<% } %>

</td>
<td align="center">   <a href="#" onClick="apriLista(2, '<%=idAsl%>')"><font size="3px"><%=numSorveglianzeSenzaChecklist %></font></a> /<%=numSorveglianzeTotali %><br/>

<% if (listaQualita.size()==1) {%>
<div id="piechartCuSorveglianza<%=idAsl %>" style="border: 1px solid violet ; width: 350px; height: 350px;"></div>
<% } %>

</td>
</tr>

<tr>
<th style="text-align:center !important"> Errata Corrige inviate nei mesi precedenti</th>
<th style="text-align:center !important"> Errata Corrige Articolo 17 inviate nei mesi precedenti</th>
</tr>

<tr>
<td align="center">   <%=numErrataCorrigeMeseMeseMesePrecedente %> / <%=numErrataCorrigeMeseMesePrecedente %> / <a href="#" onClick="apriLista(3, '<%=idAsl%>')"><font size="3px"><%=numErrataCorrigeMesePrecedente %></font></a> <br/>
<% if (listaQualita.size()==1) {%>
<div id="curve_chartEc<%=idAsl %>" style="border: 1px solid violet ; width: 350px; height: 350px;"></div>
<% } %>
</td>
<td align="center">  <%=numErrataCorrigeArt17MeseMeseMesePrecedente %> /  <%=numErrataCorrigeArt17MeseMesePrecedente %> / <a href="#" onClick="apriLista(4, '<%=idAsl%>')"><font size="3px"><%=numErrataCorrigeArt17MesePrecedente %></font></a> <br/>
<% if (listaQualita.size()==1) {%>
<div id="curve_chartEc17<%=idAsl %>" style="border: 1px solid violet ; width: 350px; height: 350px;"></div>
<% } %>
</td>
</tr>
</table>
<br/><br/>

<% if (listaQualita.size()==1) {%>
<script type="text/javascript">
    var chartCuData=[
          ['CU', 'Stato'],
          ['Chiusi',  <%=Integer.parseInt(numCuTotali)-Integer.parseInt(numCuAperti)%>],
          ['Aperti',  <%=numCuAperti%>]
    ];
  google.charts.load('current', {'packages':['corechart']});
  google.charts.setOnLoadCallback(drawCuChart);
 function drawCuChart() {
  var data = google.visualization.arrayToDataTable(chartCuData);
  var options = {
          title: 'Situazione Controlli Ufficiali'
        };
  var chartCu = new google.visualization.PieChart(document.getElementById('piechartCu<%=idAsl%>'));
  chartCu.draw(data, options);
    }
 
 var chartCuSorveglianzaData=[
                  ['CU', 'Stato'],
                  ['SI',  <%=Integer.parseInt(numSorveglianzeTotali) - Integer.parseInt(numSorveglianzeSenzaChecklist)%>],
                  ['NO',  <%=numSorveglianzeSenzaChecklist%>]
            ];
          google.charts.load('current', {'packages':['corechart']});
          google.charts.setOnLoadCallback(drawCuSorveglianzaChart);
         function drawCuSorveglianzaChart() {
          var data = google.visualization.arrayToDataTable(chartCuSorveglianzaData);
          var options = {
                  title: 'Situazione Presenza Checklist in Sorveglianza'
                };
          var chartCuSorveglianza = new google.visualization.PieChart(document.getElementById('piechartCuSorveglianza<%=idAsl%>'));
          chartCuSorveglianza.draw(data, options);
            }
         
         
 var chartEcData=[
                  	  ['periodo', 'EC Asl'],
                  	  ['TRE MESI FA',  <%=numErrataCorrigeMeseMeseMesePrecedente%>],
                  	  ['DUE MESI FA',  <%=numErrataCorrigeMeseMesePrecedente%>],
                  	  ['UN MESE FA',  <%=numErrataCorrigeMesePrecedente%>],
                  	];
                        google.charts.load('current', {'packages':['corechart']});
                        google.charts.setOnLoadCallback(drawChartEc);

                        function drawChartEc() {
                          var data = google.visualization.arrayToDataTable(chartEcData);

                          var options = {
                            title: 'Richieste di Errata Corrige',
                            curveType: 'function',
                            legend: { position: 'bottom' }
                          };

                          var chartEc = new google.visualization.LineChart(document.getElementById('curve_chartEc<%=idAsl%>'));

                          chartEc.draw(data, options);
                        }

                        
                        var chartEc17Data=[
                                     	  ['periodo', 'EC Asl'],
                                     	  ['TRE MESI FA',  <%=numErrataCorrigeArt17MeseMeseMesePrecedente%>],
                                     	  ['DUE MESI FA',  <%=numErrataCorrigeArt17MeseMesePrecedente%>],
                                     	  ['UN MESE FA',  <%=numErrataCorrigeArt17MesePrecedente%>],
                                     	];
                                           google.charts.load('current', {'packages':['corechart']});
                                           google.charts.setOnLoadCallback(drawChartEc17);

                                           function drawChartEc17() {
                                             var data = google.visualization.arrayToDataTable(chartEc17Data);

                                             var options = {
                                               title: 'Richieste di Errata Corrige Art17',
                                               curveType: 'function',
                                               legend: { position: 'bottom' }
                                             };

                                             var chartEc17 = new google.visualization.LineChart(document.getElementById('curve_chartEc17<%=idAsl%>'));

                                             chartEc17.draw(data, options);
                                           }
                                           
                                           
</script>
<% } %>





<% } %>

<% } %>