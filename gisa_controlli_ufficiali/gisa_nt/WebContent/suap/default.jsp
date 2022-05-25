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

<!-- 
<div align="right" style="padding-right: 1%">
<img class="masterTooltip" onclick="apri_mini_guida();" src="images/questionmark.png" title="GUIDA FUNZIONALITA DI QUESTA PAGINA"  width="20">
</div>
-->
<body onload="loadModalWindowCustom('Attendere Prego...'); window.location.href='InterfValidazioneRichieste.do?command=MostraRichiesteDaValutare';" ></body>

<div style="display: none;">

<br>
<table  width="100%">
<tr>
	
	<td >
		&nbsp; 
	</td>
</tr>

<tr>
	<td colspan="4" align="right" style="padding-right: 180px ">
		
<!-- 		<a href="#" onclick ="window.open('http://www.gisacampania.it/manuali/manualeopu.pdf'); return false;"> -->
<!-- 			<font size="1.5px" class="highlight"><b>Consulta la guida operativa</b></font>   -->
<!-- 			<img src="gestione_documenti/images/icon_guida.png" width="25"/>  -->
<!-- 		</a> -->
	  
	</td>
</tr>
<tr>
	
	<td >
		&nbsp; 
	</td>
</tr>	
 

<tr>
	<td colspan="4" align="center"> 
	<!--  <center><b><font size="3px">SCIA</font></b> </center><br/> --> 
	  
	 <%@ include file="../suap/suap_scia_add.jsp"%>
	  
	 </td>
</tr>

<tr>
	<td valign="top"> 
	 	&nbsp;
	</td>
</tr>

<tr>
	<td  colspan="4" align="center"> 
	
	 <%@ include file="../suap/suap_riconosciuti_add.jsp"%>
	 
	<br>	<br>
	</td>

</tr>

</table>


<!-- inizio parte popup per miniguida -->
<%-- 
<%@include file="/gestione_pratica/guide_html/mini_guida_old_gestione_scia.html" %> 
<jsp:include page="/gestione_pratica/mini_guida.jsp" />  
--%>
<!-- fine parte popup per miniguida -->

<jsp:include page="/suap/suap_scia_add_asl.jsp" />

</div>
