<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

 <link rel="stylesheet" type="text/css" href="css/colore_demo.css"></link>	
<link rel="stylesheet" type="text/css" href="css/demo.css"></link>		
<link rel="stylesheet" type="text/css" href="css/custom.css"></link>	
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>
<link rel="stylesheet" type="text/css" href="css/capitalize.css"></link>		

<script src='javascript/modalWindow.js'></script>
<script src='javascript/jquerymini.js'></script>	
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
 
<%
String ruolo = User.getRole();
int idDca = -1;
switch (ruolo) {
case "DCA 43 ASL AVELLINO": idDca = 1;  break;
case "DCA 43 ASL BENEVENTO": idDca = 2;  break;
case "DCA 43 ASL CASERTA": idDca = 3;  break;
case "DCA 43 ASL NAPOLI 1 CENTRO": idDca = 4;  break;
case "DCA 43 ASL NAPOLI 2 NORD": idDca = 5;  break;
case "DCA 43 ASL NAPOLI 3 SUD": idDca = 6;  break;
case "DCA 43 ASL SALERNO": idDca = 7;  break;
case "DCA 43 AO CARDARELLI": idDca = 8;  break;
case "DCA 43 AO SANTOBONO": idDca = 9;  break;
case "DCA 43 AO DEI COLLI": idDca = 10;  break;
case "DCA 43 AOU RUGGI": idDca = 11;  break;
case "DCA 43 AO MOSCATI": idDca = 12;  break;
case "DCA 43 AO RUMMO": idDca = 13;  break;
case "DCA 43 AO SAN SEBASTIANO": idDca = 14;  break;
case "DCA 43 AOU SUN": idDca = 15;  break;
case "DCA 43 AOU FEDERICO II": idDca = 16;  break;
case "DCA 43 IRCCS PASCALE": idDca = 17;  break;
case "DCA 43 Regione": idDca = -1;  break;
}
%>

<script>
function wait()
{
	
	
//loadModalWindowCustom("ATTENZIONE! LA REPORTISTICA DI.GE.MON NON E' IN TEMPO REALE");

//href='Reportistica.do?command=LoginDigemon';
//if('demo.gisacampania.it' == document.referrer.split('/')[2])
//        location.href='http://col2.gisacampania.it/DiGeMon';
//else

setTimeout(function(){document.getElementById("goDca").submit();}, 1000);

}
</script>
<body onload="wait()">
<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>

<form name="goDca" id="goDca" action="http://<%= java.net.InetAddress.getByName("DCA43HOST").getHostAddress()%>" method="post">
<input type="hidden" id="role" name="role" value="<%=idDca%>"/>
</form>

</body>


