<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

 <%
 String contesto = (String) application.getAttribute("SUFFISSO_TAB_ACCESSI");
	if (contesto!=null && contesto.equals("_ext"))
		contesto="Suap";
	else
		contesto = "Gisa";
 %>


<%-- Trails --%>
	<table class="trails" cellspacing="0">
	<tr>
		<td>PRATICHE SUAP 2.0</td>
	</tr>
	</table>
<%-- Trails --%>

<div align="right" style="padding-right: 1%">
<img class="masterTooltip" onclick="apri_mini_guida();" src="images/questionmark.png" title="GUIDA FUNZIONALITA DI QUESTA PAGINA"  width="20">
</div>

<br>
<div align="center">
<dhv:permission name="opu-add"> 

	<input type="button" class="yellowBigButton" style="width: 450px;" 
			onclick="loadModalWindowCustom('Attendere Prego...'); window.location.href='GestionePraticheAction.do?command=ListaPraticheStabilimenti';" 
			value="GESTIONE ATT. FISSE, MOBILI E RICONOSCIMENTO" />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	
	<%--
	<input type="button" class="yellowBigButton" style="width: 300px;" 
		onclick="loadModalWindowCustom('Attendere Prego...'); window.location.href='InterfValidazioneRichieste.do?command=MostraRichiesteDaValutare';" 
		value="APICOLTURA" />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	--%>
	<input type="button" class="yellowBigButton" style="width: 300px;" 
		onclick="loadModalWindowCustom('Attendere Prego...'); window.location.href='GestionePraticheAction.do?command=ListaPraticheApicoltura';" 
		value="APICOLTURA" />

	<!-- 
	<fieldset style="display : inline-block;">
	<legend><b>PRATICA</b></legend>
	<table>
	<input type="button" class="yellowBigButton" style="width: 300px;" 
		onclick="sceltaOperazione('<%=contesto %>','SuapStab.do?command=Scelta&tipoInserimento=6&stato=3&fissa=true');" 
		value="RICONOSCIMENTO" />
	</table>
	</fieldset>
	 -->

</dhv:permission>

<!-- inizio parte popup per miniguida -->
<%@include file="/gestione_pratica/guide_html/mini_guida_home_gins.html" %>
<jsp:include page="/gestione_pratica/mini_guida.jsp" />
<body onload="apri_mini_guida();"></body>
<!-- fine parte popup per miniguida -->

</div>
<br>
	<%if (contesto=="Suap") { %>
		  <br>
		  <div align="center">
		  <a href="#" onclick="window.open('suap/download/ModAccredSuap.pdf')">Modulo richiesta accreditamento suap
		  <img src="gestione_documenti/images/pdf_icon.png" width="20"/>
		  </a>
		  </div>
	<%} %>
<center>
<br>

<jsp:include page="/suap/suap_scia_add_asl.jsp" />

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

function sceltaOperazione(contesto,url) //tiposcelta 1 per registrati, 2 per riconosciuti
{
	 if(contesto=='Suap')
		 {
		 goTo(url);
		 }
	 else
		 {
		 document.forms['scelta'].action=url;
		 
		 $( "#dialogSceltaComune" ).dialog('open');
		 }
}

</script>