<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<script type="text/javascript">
function openCampioni(){
	var res;
	var result;
		window.open('AnimaleAction.do?command=PrintRichiestaCampioni&microchip='+microchip+'&idSpecie='+idSpecie,'popupSelect',
		'height=595px,width=842px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}

function test(){
	
	if(((document.getElementById("microchip").value.length)<15)&&((document.getElementById("microchip").value!=null)||(document.getElementById("microchip").value==null)))
	{
		alert("Il microchip deve essere di 15 caratteri");
		window.location.reload();
	}
	
	if (document.getElementById("tipo0").checked){
		
		//alert('popup');
		document.form1.target = "POPUPW" ;
		 POPUPW = window.open(
				   'about:blank','POPUPW','width=600,height=400');
		document.form1.submit();
	}
		
}


</script>

<body>

  <font size="2" face="Verdana, Arial, Helvetica, sans-serif">

  <form name="form1"  action="GeneraBarCode.do?command=SearchForm" method="get"
    onsubmit="javascript:test();" >
   
  <table>
  <tr><td><b>Genera scheda</b></td></tr>
  <tr>
  <td>Scheda per l'invio dei campioni</td>
  <td><input name="tipo" type="radio" checked="checked" value="0" id="tipo0"></td>
  </tr>
  
  <tr>
  <td>Scarica solo immagine barcode</td>
  <td><input name="tipo" type="radio" checked="checked" value="1" id="tipo1"></td>
  </tr>
  </table>
 
  <br>
  <br>
  <br>
    Inserire il Microchip
  <br>
  <input name="microchip" type="text" id="microchip" maxlength="15">
  <input name="isEmpty" type="hidden" id="isEmpty" value="a">
  
  <input type="submit" name="Submit" value="Invia">
  
  </form>
  </font>
  
</body>
</html>