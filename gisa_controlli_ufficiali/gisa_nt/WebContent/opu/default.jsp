<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants" %>

<%@ include file="../initPage.jsp"%>

<script src='javascript/modalWindow.js'></script>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>
<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>

<script>function redirect(val){
	loadModalWindow();
	if (val==1)
		window.location.href ='OpuStab.do?command=SearchForm';
	else 
		window.location.href ='OpuStab.do?command=SearchFormNonFissa';
}

function openPopup(url){
	
	  var res;
      var result;
      
      	  window.open(url,'popupSelect',
            'height=1280px,width=600px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
	
	}
	
	
function showHide(name){
	  var elem = document.getElementById(name);
			if (elem.style.display=='none')
			    	elem.style.display='block';
			    else
			    	elem.style.display='none';
return false;
}

function hide(name){
	  var elem = document.getElementById(name);
		elem.style.display='none';
return false;
}

function goTo(link){
	
	if (link=='')
		alert('da implementare');
	else{
		loadModalWindow();
		window.location.href=link;
	}
	
	return false;
}

function scrollPage() {
	window.scrollBy(0,400); // horizontal and vertical scroll increments

}


</script>


<table  cellpadding="10">

<tr><td colspan="4" align="center">
<img src="gestione_documenti/images/pdf_icon.png" width="20"/>  <a href="#" onClick="openPopup('http://www.gisacampania.it/manuali/manualeopu.pdf');	return false; "> 
<font size="3px">Manuale utente</font></a> 
</td></tr>


<tr>

<td valign="top"> 
<center><b></b> </center> <br/> <br/>  <br/>
<input type="button" class="aniceBigButton" style="height:110px !important; width:120px !important;" value="RICERCA" onClick="goTo('OpuStab.do?command=SearchForm')"/> 
</td>

<td valign="top"> 
 <center><b><font size="3px">SCIA</font></b> </center><br/> 
 
 <%@ include file="../suap/suap_scia_add.jsp"%>
 
 </td>

<td valign="top"> 
 <center><b><font size="3px">NO SCIA</font></b> </center><br/>
 
 <dhv:permission name="opu-add">
 <input type="button" class="stitchedGreyBigButton" style="height:50px !important; width:350px !important;" value="Aggiungi stabilimento pregresso"  onClick="intercettaPregresso()">  <br/><br/>
 <div id="vecchioNumero" name="vecchioNumero">
 <%@ include file="controlloPregresso.jsp"%><br>
 </div>
 </dhv:permission>
 
 <input type="button" class="aniceBigButton" style="height:50px !important; width:350px !important;" value="Gestione Stabilimenti senza SCIA" onClick="showHide('noscia'); scrollPage()"/> <br/><br/> 

<div id="noscia" style="display:none">
 <input type="button" class="lightGreyBigButton" style="height:50px !important; width:175px !important;" value="Aggiungi" onClick="showHide('stabilimenti_nonscia_add'); hide('stabilimenti_nonscia_search'); scrollPage();"/>
 <input type="button" class="lightGreyBigButton" style="height:50px !important; width:175px !important;" value="Ricerca" onClick="showHide('stabilimenti_nonscia_search'); hide('stabilimenti_nonscia_add'); scrollPage();"/>
  
<%@ include file="stabilimenti_non_scia_add.jsp"%>

  </div>

</td>

<td valign="top"> 
<center><b><font size="3px"> &nbsp </font></b> </center><br/>

 <%@ include file="../suap/suap_riconosciuti_add.jsp"%>

<br>	<br>

<!-- DA PROGETTAZIONE, CHI METTE I PERMESSI O ABILITA IL PRIMO O IL SECONDO -->
 <dhv:permission name="opu_validazione-view">
<input type="button" class="aniceBigButton" style="height:50px !important; width:200px !important;" value="SCIA da validare" onClick="goTo('InterfValidazioneRichieste.do?command=MostraRichiesteDaValutare')">
</dhv:permission>

<dhv:permission name="opu_validazione_riconoscimento-view">
<input type="button" class="aniceBigButton" style="height:50px !important; font-size: 10 !important; width:200px !important;" value="VALIDA RICONOSCIUTI" onClick="goTo('InterfValidazioneRichieste.do?command=MostraRichiesteDaValutare')">
</dhv:permission>

</td>

</tr>

</table>
   <jsp:include page="/suap/suap_scia_add_asl.jsp" />
 
<script>
$('#vecchioNumero').hide();

function intercettaPregresso()
{
	$('#vecchioNumero').show();
}
</script>
