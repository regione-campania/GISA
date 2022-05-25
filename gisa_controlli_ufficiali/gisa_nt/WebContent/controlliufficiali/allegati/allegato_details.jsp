<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="org.aspcf.modules.checklist_benessere.base.Capitolo"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.aspcf.modules.checklist_benessere.base.Domanda"%>

<%@page import="org.aspcf.modules.checklist_benessere.base.Risposta"%>
<jsp:useBean id="Allevamento" class="org.aspcf.modules.controlliufficiali.base.Organization" scope="request"/>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ChecklistIstanza" class="org.aspcf.modules.checklist_benessere.base.ChecklistIstanza" scope="request"/>
<jsp:useBean id="Risposta" class="org.aspcf.modules.checklist_benessere.base.Risposta" scope="request"/>
<jsp:useBean id="domandePerCapitolo" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="numAllegato" class="java.lang.String" scope="request"/>
<jsp:useBean id="nuova_gestione" class="java.lang.String" scope="request"/>
<jsp:useBean id="versioneChecklist" class="java.lang.String" scope="request"/>

<jsp:useBean id="numCapitoli" class="java.lang.String" scope="request"/>
<jsp:useBean id="totale_punteggio_A" class="java.lang.String" scope="request"/>
<jsp:useBean id="totale_punteggio_B" class="java.lang.String" scope="request"/>
<jsp:useBean id="totale_punteggio_C" class="java.lang.String" scope="request"/>
<jsp:useBean id="totale_punteggio" class="java.lang.String" scope="request"/>
<jsp:useBean id="AzFields" class="org.aspcf.modules.controlliufficiali.base.AziendeZootFields" scope="request"/>
<jsp:useBean id="Ticket" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>
<jsp:useBean id="Modulo" class="org.aspcfs.modules.allevamenti.base.ModuloControllo" scope="request"/>
<jsp:useBean id="AltreSpecie" class="java.lang.String" scope="request"/>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>

<%@ include file="../../../initPage.jsp" %>

<style>
@media print {
td {
	  page-break-inside: avoid !important ;
	}
      tr {
	  page-break-inside: avoid !important ;
	}
        
 }</style>

<script type="text/javascript">
window.opener.loadModalWindow();

// window.opener.document.location.reload();


function findTotal(idCapitolo, idDomanda){
    var arr = document.getElementsByName('punteggio_'+idCapitolo+'_'+idDomanda);
    var tot=0;
    for(var i=0;i<arr.length;i++){
        if(parseInt(arr[i].value))
            tot += parseInt(arr[i].value);
    }
    
    if(parseInt(tot) > 0){
    	 document.getElementById('d_no_'+idCapitolo+'_'+idDomanda).checked = true;
    	 document.getElementById('nfav_'+idCapitolo+'_'+idDomanda).style.display = 'block';
    	 document.getElementById('fav_'+idCapitolo+'_'+idDomanda).style.display = 'none';   
    	 document.getElementById('nafav_'+idCapitolo+'_'+idDomanda).style.display = 'none';   
    }
    
    document.getElementById('tot_'+idCapitolo+'_'+idDomanda).value = tot;
    document.getElementById('punteggioA_'+idCapitolo+'_'+idDomanda).value = document.getElementById('puntA_'+idCapitolo+'_'+idDomanda).value;
    document.getElementById('punteggioB_'+idCapitolo+'_'+idDomanda).value = document.getElementById('puntB_'+idCapitolo+'_'+idDomanda).value;
    document.getElementById('punteggioC_'+idCapitolo+'_'+idDomanda).value = document.getElementById('puntC_'+idCapitolo+'_'+idDomanda).value;
    sumTotalPunt();
    
}
function sumTotalPunt(){
	var numCapitoli = document.getElementById('dimCapitoli').value;
    var totA = 0;
    var totB = 0;
    var totC = 0;
    var totIrreg = 0;
    var j = 1;
    for(var i=1;i<=numCapitoli;i++){
    	var numDomande = document.getElementById('domCap_'+i).value;
    	for(var j=1;j<=numDomande;j++){
       		var puntA = document.getElementById('puntA_'+i+'_'+j);
    		var puntB = document.getElementById('puntB_'+i+'_'+j);
    		var puntC = document.getElementById('puntC_'+i+'_'+j);    	
    		var irreg = document.getElementById('tot_'+i+'_'+j);
    		if(puntA != null && parseInt(puntA.value))//qua....
        		totA += parseInt(puntA.value);
    		if(puntB != null && parseInt(puntB.value))
        		totB += parseInt(puntB.value);
    		if(puntC != null && parseInt(puntC.value))
        		totC += parseInt(puntC.value);
    		if(irreg != null && parseInt(irreg.value))
        		totIrreg += parseInt(irreg.value);
    		if(parseInt(puntA.value) > 0 || parseInt(puntB.value) > 0 || parseInt(puntC.value) > 0){
//    			document.getElementById('d_no_'+i+'_'+j).checked = true;
//     	      	document.getElementById('nfav_'+i+'_'+j).style.display = 'block';
//     	      	document.getElementById('fav_'+i+'_'+j).style.display = 'none'; 
    		}
    		else{
//     	    	document.getElementById('d_si_'+i+'_'+j).checked = false;
//     	      	document.getElementById('fav_'+i+'_'+j).style.display = 'block';
//     	      	document.getElementById('nfav_'+i+'_'+j).style.display = 'none';   
    	      	 
    	   }
    	}//fine for
    	
    }
    document.getElementById('totaleA').innerHTML = totA;
    document.getElementById('totaleB').innerHTML = totB;
    document.getElementById('totaleC').innerHTML = totC;
    document.getElementById('totIrreg').innerHTML = totIrreg;

	
}


function verificaStatoControllo(dataChiusuraCU,isBozza){
	
	if((dataChiusuraCU != null && dataChiusuraCU != '' && dataChiusuraCU != 'null') || isBozza == 'false'){
		var f = document.forms['myform'];
		for(var i=0,fLen=f.length;i<fLen;i++){

			if (f.elements[i].type == 'radio')
		    { 
		          f.elements[i].disabled = true;
		    } 
			else if (f.elements[i].type == 'submit')
		    { 
		          
		    } 
		    else {
			    
		  		f.elements[i].readOnly = true;
		  		f.elements[i].className = 'layout';
		    }
		}
		var g = document.forms['myform'].getElementsByTagName("textarea");
		for(var j=0; j < g.length; j++)
			 g.item(j).className = '';

		document.getElementById('idbtn').style.display = 'none';
		
		var b = document.getElementsByName("resetbtn");
		for(var q=0; q < b.length; q++){
			 b.item(q).disabled=true;
			 b.item(q).style.display = 'none';	
		}
		
	}
}

function closeAndRefresh(chiudi) {
	
	
	if(chiudi == "si")
	{
		<% String urlDettaglio ="";
		if (Allevamento.getIdStabilimento()>0)
			urlDettaglio = "OpuStab";
		else
			urlDettaglio="Allevamenti";
		%>
		
		window.opener.location.href='<%=urlDettaglio%>Vigilanza.do?command=TicketDetails&id=<%=Allevamento.getIdControllo()%>&orgId=<%=Allevamento.getOrgId()%>&stabId=<%=Allevamento.getIdStabilimento()%>';
		self.close();
		
	}	
}	

function gestisciCb(campo){
	var dsi = document.getElementById('d_si_'+campo);
	var dno = document.getElementById('d_no_'+campo);
	var dna = document.getElementById('d_na_'+campo);
	var fav = document.getElementById('fav_'+campo);
	var nfav = document.getElementById('nfav_'+campo);
	var nafav = document.getElementById('nafav_'+campo);
	var tot = document.getElementById('tot_'+campo);
	
	if (tot.value>0){
		dno.checked=true;}
	
	if (dsi.checked)
	{
	fav.style.display='block';
	nfav.style.display='none';
	nafav.style.display='none';
	}
else if (dno.checked){
	nfav.style.display='block';
	fav.style.display='none';
	nafav.style.display='none';
}
else {
	nafav.style.display='block';
	fav.style.display='none';
	nfav.style.display='none';
}
	
}

// function resetRisposta(cap, i){
// 	var campo = cap+'_'+i;
// 	var dsi = document.getElementById('d_si_'+campo);
// 	var dno = document.getElementById('d_no_'+campo);
// 	var fav = document.getElementById('fav_'+campo);
// 	var nfav = document.getElementById('nfav_'+campo);
// 	var tot = document.getElementById('tot_'+campo);
// 	var puntA = document.getElementById('puntA_'+campo);
// 	var puntB = document.getElementById('puntB_'+campo);
// 	var puntC = document.getElementById('puntC_'+campo);
// 	var oss = document.getElementById('oss_'+campo);
	
// 	dsi.checked = false;
// 	dno.checked = false;
// 	fav.style.display='none';
// 	nfav.style.display='none';
// 	oss.value='';
// 	puntA.value='0';
// 	puntB.value='0';
// 	puntC.value='0';
// 	tot.value='0';
// 	findTotal(cap,i);
// }


</script>

<% String specie = Allevamento.getSpecie_allev();
   String codice_specie = Allevamento.getCodice_specie();
   if(versioneChecklist.equals("2")){
	   if(numAllegato.equals("4")) {
		   specie = "VITELLI";
		   codice_specie = "1211";
	   }
	   else if(numAllegato.equals("2")) {
		   specie = "GALLINE OVAIOLE";
		   codice_specie = "131";
	   }
	   //suini
	   else if(numAllegato.equals("3")){
		   specie = "SUINI";
		   codice_specie = "122";
	   }
	   else if(numAllegato.equals("5")) {
		   specie = "POLLI DA CARNE";
		   codice_specie = "1461";
	   }
	   //altre specie
	   else {
		   if (AltreSpecie!=null && !AltreSpecie.equals("null") && !AltreSpecie.equals(""))
			   specie = AltreSpecie;
		   else
		   		specie = Allevamento.getSpecie_allev();
		   codice_specie = "-2";
	   }
   }//fine nuova gestione
   else {
	if(numAllegato.equals("3")) {
		specie = "VITELLI";
		codice_specie = "1211";
	} else if(numAllegato.equals("5")){
		specie = "POLLI DA CARNE";
		codice_specie = "1461";
	}else {
		specie = Allevamento.getSpecie_allev();
	}
   }



%>



<input type="hidden" name="orgId" id="orgId" value="<%=Allevamento.getOrgId()%>"/>
<input type="hidden" name="stabId" id="stabId" value="<%=Allevamento.getIdStabilimento()%>"/>

<body onload="javascript:verificaStatoControllo('<%=Allevamento.getData_chiusura_controllo()%>','<%=ChecklistIstanza.isBozza()%>');sumTotalPunt();closeAndRefresh('<%= request.getAttribute("chiudi")%>');">

<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div>
<br/><br/><br/> <!-- FACCIO POSTO PER BOX ID DOCUMENTO IN GENERA PDF -->

<form method="post" name="myform" action="PrintModulesHTML.do?command=UpdateChecklistBenessere&idControllo=<%=Allevamento.getIdControllo()%>&orgId=<%=Allevamento.getOrgId()%>&stabId=<%=Allevamento.getIdStabilimento()%>&specie=<%=codice_specie%>&auto-populate=true">

<P style="text-align: right">
	<b>Allegato <%=numAllegato %></b>	   
</P>
<%
if (!"15".equals(numAllegato))
{
%>
 	<%@ include file="liste_di_riscontro_header.jsp" %>
 	
<%} %>

<div style="border: 1px solid;">
<br>

<%  if(versioneChecklist.equals("2")) { %>

 <%@ include file="liste_di_riscontro_frontespizio_new.jsp" %>

<% } else {%>

<%@ include file="liste_di_riscontro_frontespizio_old.jsp" %>

<% } %>
	  
</div>
<br>
<br>
<br>
<B>LEGENDA</B>
<table class="tableClass" border="1">
	<tr>
		<td style="text-align:center">
			<b>Categoria delle non conformità</b>
		</td>
		<td>
			<b>AZIONI INTRAPRESE DALL'AUTORITA' COMPETENTE</b>
		</td>
	</tr>
	<tr>
		<td style="text-align:center">
			<b>A</b>
		</td>
		<td>
			Richiesta di rimediare alle non conformità entro un termine inferiore a 3 mesi. <br>
			Nessuna sanzione amministrativa o penale immediata.
		</td>
	</tr>
	<tr>
		<td style="text-align:center">
			<b>B</b>
		</td>
		<td>
			Richiesta di rimediare alle non conformità entro un termine superiore a 3 mesi.<br>
			 Nessuna sanzione amministrativa o penale immediata.
		</td>
	</tr>
	<tr>
		<td style="text-align:center">
			<b>C</b>
		</td>
		<td>
			Sanzione amministrativa o penale immediata.
		</td>
	</tr>
</table>

<div class="fine" style="height: 150px;">&nbsp;</div>
  <div style="page-break-before:always">&nbsp; </div>  
  
<table class="tableClass" cellpadding="9" cellspacing="0" border="1" width="100%" class="details">
  <input type="hidden" name="idCU" id="idCU" value="<%=Allevamento.getIdControllo()%>" />
  <tr class="containerBody">
    <th colspan="2" style="background-color: gray;">Tipo di irregolarità</th>
    <th colspan="4" style="background-color: gray;"></th> 
    <th colspan="1" style="background-color: gray;">Numero<br>irregolarità</th>
    <th colspan="3" style="background-color: gray;">N.dei provvedimenti<br>adottati di conseguenza</th>   
  </tr>
  <tr class="containerBody">
    <th>Requisito</th>
    <th>Definizione dei requisiti</th>
    <th>Si</th>
    <th>No</th>
    <th>N.A.</th>
    <th>Osservazioni</th>
    <th></th>
    <th>A</th>
    <th>B</th>
    <th>C</th>
  </tr>
  
    <input type="hidden" name="dimCapitoli" id="dimCapitoli" value="<%=numCapitoli%>" />
  
   <% 
   	  Iterator<Risposta> it = ChecklistIstanza.getRisposte().iterator();
	  int index = 0;
      while(it.hasNext()){
    	    
  	  		Risposta risposta = it.next();
      		
  %>
  	<input type="hidden" id="domCap_<%=risposta.getIdCap()%>" name="domCap_<%=risposta.getIdCap()%>" value="<%= domandePerCapitolo.get(index)%>">
  	
  	<% if(risposta.getIdDom() == 1) { 
  	%>
  		<tr class="containerBody">
		<td rowspan="<%=domandePerCapitolo.get(index)%>">
			<%=risposta.getDescCap()%>
		</td>
		<td><%=risposta.getDescDom()%></td>
		<td> <div class="documentaleNonStampare">
			<input type="radio" name="esito_<%=risposta.getIdCap()%>_<%=risposta.getIdDom()%>" id="d_si_<%=risposta.getIdCap()%>_<%=risposta.getIdDom()%>" <%if(Boolean.TRUE.equals(risposta.isEsito())){ %>checked="checked"<%} %> value="si" onClick="gestisciCb('<%=risposta.getIdCap()%>_<%=risposta.getIdDom()%>')"/>
			</div>
  			<input class="layout" type="text" readonly style="display: block;border: none;font-size:12pt;font-weight:bold;" size="1" id="fav_<%=risposta.getIdCap()%>_<%=risposta.getIdDom()%>" name="favorevole" value="V"/>
		</td>
  		<td>
  		<div class="documentaleNonStampare">
  		<input type="radio" name="esito_<%=risposta.getIdCap()%>_<%=risposta.getIdDom()%>" id="d_no_<%=risposta.getIdCap()%>_<%=risposta.getIdDom()%>" <%if(Boolean.FALSE.equals(risposta.isEsito())){ %>checked="checked"<%} %> value="no" onClick="gestisciCb('<%=risposta.getIdCap()%>_<%=risposta.getIdDom()%>')"/>
  		</div>
  		<input class="layout" type="text" readonly style="display: block;border: none;font-size:12pt;font-weight: bold;" size="1" id="nfav_<%=risposta.getIdCap()%>_<%=risposta.getIdDom()%>" name="Nfavorevole" value="X"/>
  		</td>
  		<td>
  		<div class="documentaleNonStampare">
  		<input type="radio" name="esito_<%=risposta.getIdCap()%>_<%=risposta.getIdDom()%>" id="d_na_<%=risposta.getIdCap()%>_<%=risposta.getIdDom()%>" <%if(risposta.isEsito()==null){ %>checked="checked"<%} %> value="na" onClick="gestisciCb('<%=risposta.getIdCap()%>_<%=risposta.getIdDom()%>')"/>
  		</div> 
  		<input class="layout" type="text" readonly style="display: block;border: none;font-size:12pt;font-weight: bold;" size="1" id="nafav_<%=risposta.getIdCap()%>_<%=risposta.getIdDom()%>" name="Nafavorevole" value="NA"/>
  		</td>
  		<td>
			<%-- <input type="text" name="oss_<%=risposta.getIdCap()%>_<%=risposta.getIdDom()%>" id="oss_<%=risposta.getIdCap()%>_<%=risposta.getIdDom()%>"  value="<%=(risposta.getOsservazioni() != null) ? risposta.getOsservazioni(): "" %>" />--%>
			<textarea name="oss_<%=risposta.getIdCap()%>_<%=risposta.getIdDom()%>" id="oss_<%=risposta.getIdCap()%>_<%=risposta.getIdDom()%>"><%=(risposta.getOsservazioni() != null) ? risposta.getOsservazioni(): "" %></textarea>
		</td>
		<td><input class="layout" type="text" readonly size="5" maxlength="5" value="<%= risposta.getPunteggioTotale()%>" name="tot_<%=risposta.getIdCap()%>_<%=risposta.getIdDom()%>" id="tot_<%=risposta.getIdCap()%>_<%=risposta.getIdDom()%>"/>
		
		</td>
		<td><input class="editField" type="text" size="5" maxlength="5" name="punteggio_<%=risposta.getIdCap()%>_<%=risposta.getIdDom()%>" id="puntA_<%=risposta.getIdCap()%>_<%=risposta.getIdDom()%>" value="<%=risposta.getPunteggioA()%>" onblur="javascript:if (isNaN(value)) { alert ('Errore il campo relativo al punteggio A può contenere solo valori numerici!'); document.getElementById('puntA_<%=risposta.getIdCap()%>_<%=1%>').value=''; } 
		else if (parseInt(value) < 0) { alert ('Errore il campo relativo al punteggio A non può essere negativo!'); document.getElementById('puntA_<%=risposta.getIdCap()%>_<%=1%>').value=''; }
		else findTotal('<%=risposta.getIdCap()%>','<%=1%>');" /></td>
		<td><input class="editField" type="text" size="5" maxlength="5" name="punteggio_<%=risposta.getIdCap()%>_<%=risposta.getIdDom()%>" id="puntB_<%=risposta.getIdCap()%>_<%=risposta.getIdDom()%>" value="<%=risposta.getPunteggioB()%>" onblur="javascript:if (isNaN(value)) { alert ('Errore il campo relativo al punteggio B può contenere solo valori numerici!'); document.getElementById('puntB_<%=risposta.getIdCap()%>_<%=1%>').value=''; } 
		else if (parseInt(value) < 0) { alert ('Errore il campo relativo al punteggio B non può essere negativo!'); document.getElementById('puntB_<%=risposta.getIdCap()%>_<%=1%>').value=''; }
		else findTotal('<%=risposta.getIdCap()%>','<%=1%>');" /></td>
		<td><input class="editField" type="text" size="5" maxlength="5" name="punteggio_<%=risposta.getIdCap()%>_<%=risposta.getIdDom()%>" id="puntC_<%=risposta.getIdCap()%>_<%=risposta.getIdDom()%>" value="<%=risposta.getPunteggioC()%>" onblur="javascript:if (isNaN(value)) { alert ('Errore il campo relativo al punteggio C può contenere solo valori numerici!'); document.getElementById('puntC_<%=risposta.getIdCap()%>_<%=1%>').value=''; } 
		else if (parseInt(value) < 0) { alert ('Errore il campo relativo al punteggio C non può essere negativo!'); document.getElementById('puntC_<%=risposta.getIdCap()%>_<%=1%>').value=''; }
		else findTotal('<%=risposta.getIdCap()%>','<%=1%>');" /></td>
		<input type="hidden" id="punteggioA_<%=risposta.getIdCap()%>_<%=risposta.getIdDom()%>" name="punteggioA_<%=risposta.getIdCap()%>_<%=risposta.getIdDom()%>" value="<%=risposta.getPunteggioA()%>"/>
		<input type="hidden" id="punteggioB_<%=risposta.getIdCap()%>_<%=risposta.getIdDom()%>" name="punteggioB_<%=risposta.getIdCap()%>_<%=risposta.getIdDom()%>" value="<%=risposta.getPunteggioB()%>"/>
		<input type="hidden" id="punteggioC_<%=risposta.getIdCap()%>_<%=risposta.getIdDom()%>" name="punteggioC_<%=risposta.getIdCap()%>_<%=risposta.getIdDom()%>" value="<%=risposta.getPunteggioC()%>"/>
		<input type="hidden" name="idRisposta_<%=risposta.getIdCap()%>_<%=risposta.getIdDom()%>" id="idRisposta_<%=risposta.getIdCap()%>_<%=risposta.getIdDom()%>" value="<%=risposta.getId()%>" />
	</tr> 
	<script>gestisciCb('<%=risposta.getIdCap()%>_<%=risposta.getIdDom()%>')</script>   
	<% }
   	 for(int j=2;j <= (Integer)(domandePerCapitolo.get(index));j++){
    		Risposta risposta_succ = it.next();
  	%>	
  	   	  
  	<tr class="containerBody">
    	<td><%=risposta_succ.getDescDom()%></td>
    	<td>
    		<div class="documentaleNonStampare">
    		<input type="radio" name="esito_<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>" id="d_si_<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>" <%if(Boolean.TRUE.equals(risposta_succ.isEsito())){ %>checked="checked"<%} %> value="si" onClick="gestisciCb('<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>')"/>
    		</div>
  		<input class="layout" type="text" readonly style="display: block;border: none;font-size:12pt;font-weight:bold;" size="1" id="fav_<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>" name="favorevole" value="V"/>
		</td>
  		<td>
  		<div class="documentaleNonStampare">
  		<input type="radio" name="esito_<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>" id="d_no_<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>" <%if(Boolean.FALSE.equals(risposta_succ.isEsito())){ %>checked="checked"<%} %> value="no" onClick="gestisciCb('<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>')"/>
  		</div>
  		<input class="layout" type="text" readonly style="display: block;border: none;font-size:12pt;font-weight: bold;" size="1" id="nfav_<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>" name="Nfavorevole" value="X"/>
  		</td>
  		<td>
  		<div class="documentaleNonStampare">
  		<input type="radio" name="esito_<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>" id="d_na_<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>" <%if(risposta_succ.isEsito()==null){ %>checked="checked"<%} %> value="na" onClick="gestisciCb('<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>')"/>
  		</div>
  		<input class="layout" type="text" readonly style="display: block;border: none;font-size:12pt;font-weight: bold;" size="1" id="nafav_<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>" name="Nafavorevole" value="NA"/>
  		</td>
  		<td>
  		<%--<input type="text" name="oss_<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>" id="oss_<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>"  value="<%=(risposta_succ.getOsservazioni() != null) ? risposta_succ.getOsservazioni(): "" %>"/>--%>
  			<textarea name="oss_<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>" id="oss_<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>"><%=(risposta_succ.getOsservazioni() != null) ? risposta_succ.getOsservazioni(): "" %></textarea>
  		</td>
  		<td><input class="layout" type="text" readonly size="5" maxlength="5" value="<%= risposta_succ.getPunteggioTotale()%>" name="tot_<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>" id="tot_<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>"/>
  		</td>
  		<td><input class="editField" type="text" size="5" maxlength="5" value="<%=risposta_succ.getPunteggioA()%>" name="punteggio_<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>" id="puntA_<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>" onblur="javascript:if (isNaN(value)) { alert ('Errore il campo relativo al punteggio A può contenere solo valori numerici!'); document.getElementById('puntA_<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>').value=''; } 
  		else if (parseInt(value) < 0) { alert ('Errore il campo relativo al punteggio A non può essere negativo!'); document.getElementById('puntA_<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>').value=''; }
  		else findTotal('<%=risposta_succ.getIdCap()%>','<%=risposta_succ.getIdDom()%>');" /></td>
  		<td><input class="editField" type="text"size="5" maxlength="5" value="<%= risposta_succ.getPunteggioB()%>" name="punteggio_<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>" id="puntB_<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>" onblur="javascript:if (isNaN(value)) { alert ('Errore il campo relativo al punteggio B può contenere solo valori numerici!'); document.getElementById('puntB_<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>').value=''; } 
  		else if (parseInt(value) < 0) { alert ('Errore il campo relativo al punteggio B non può essere negativo!'); document.getElementById('puntB_<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>').value=''; }
  		else findTotal('<%=risposta_succ.getIdCap()%>','<%=risposta_succ.getIdDom()%>');" /></td>
  		<td><input class="editField" type="text" size="5" maxlength="5" value="<%= risposta_succ.getPunteggioC()%>" name="punteggio_<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>" id="puntC_<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>" onblur="javascript:if (isNaN(value)) { alert ('Errore il campo relativo al punteggio C può contenere solo valori numerici!'); document.getElementById('puntC_<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>').value=''; } 
  		else if (parseInt(value) < 0) { alert ('Errore il campo relativo al punteggio C non può essere negativo!'); document.getElementById('puntC_<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>').value=''; }
  		else findTotal('<%=risposta_succ.getIdCap()%>','<%=risposta_succ.getIdDom()%>');" /></td>
  		<input type="hidden" id="punteggioA_<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>" name="punteggioA_<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>" value="<%=risposta_succ.getPunteggioA()%>" />
  		<input type="hidden" id="punteggioB_<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>" name="punteggioB_<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>" value="<%=risposta_succ.getPunteggioB()%>"/>
  		<input type="hidden" id="punteggioC_<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>" name="punteggioC_<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>" value="<%=risposta_succ.getPunteggioC()%>"/>
  	 	<input type="hidden" name="idRisposta_<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>" id="idRisposta_<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>" value="<%=risposta_succ.getId()%>" />
    </tr>
   
 	   	<script>gestisciCb('<%=risposta_succ.getIdCap()%>_<%=risposta_succ.getIdDom()%>')</script>
	<% } //Fine del for 
	
  		//inizialmente è 0
  			++index;
      } //fine while
  
	
  	%>  
  	 <tr>
  	<td style="font-weight: bold;background-color: gray;">TOTALE</td>
  	<td style="font-weight: bold;background-color: gray;"></td>
  	<td style="font-weight: bold;background-color: gray;"></td>
  	<td style="font-weight: bold;background-color: gray;"></td>
  	<td style="font-weight: bold;background-color: gray;"></td>
  	<td style="font-weight: bold;background-color: gray;"></td>
  	<td style="font-weight: bold;background-color: gray;"><label class="layout" type="text" readonly size="5" maxlength="5" name="totIrreg" id="totIrreg"><%=(totale_punteggio!=null) ? totale_punteggio : "" %></label></td>
  	<td style="font-weight: bold;background-color: gray;"><label class="layout" type="text" readonly size="5" maxlength="5" name="totaleA" id="totaleA"><%=(totale_punteggio_A!=null) ? totale_punteggio_A : "" %></label></td>
  	<td style="font-weight: bold;background-color: gray;"><label class="layout" type="text" readonly size="5" maxlength="5" name="totaleB" id="totaleB"><%=(totale_punteggio_B!=null) ? totale_punteggio_B : "" %></label></td>
  	<td style="font-weight: bold;background-color: gray;"><label class="layout" type="text" readonly size="5" maxlength="5" name="totaleC" id="totaleC"><%=(totale_punteggio_C!=null) ? totale_punteggio_C : "" %></label></td>
  </tr>	 
  </table >
  <div class="documentaleNonStampare">
  <P style="color: red;font-weight: bold;" align="right"> ***ATTENZIONE! PER AGGIORNARE I PUNTEGGI COMPLESSIVI <br> CLICCARE SUL TOTALE IN CORRISPONDENZA DEL NUMERO IRREGOLARITA'<br> (PRIMO CAMPO SULLA RIGA "TOTALE")</P>
  </div>
 
  	<div id="idbtn" style="display:block;">
       	<input type="button" class="buttonClass" name="salva" value="Aggiorna Temporaneo" onclick="javascript:if( confirm('La scheda sarà aggiornata come richiesto. Vuoi procedere con il salvataggio?')){document.myform.bozza.value = true; return document.myform.submit();}else return false;"/> &nbsp;
  		<input type="button"  class="buttonClass" name="salva" value="Aggiorna Definitivo" onclick="javascript:if( confirm('La scheda sarà aggiornata come richiesto ma i dati non saranno più modificabili. Vuoi procedere con il salvataggio definitivo?')){document.myform.bozza.value = false; return document.myform.submit();}else return false;"/> &nbsp;
  	</div><br> 
 	 	<%-- <input type="submit"  class="buttonClass" name="stampa" value="Stampa" onclick="document.myform.bozza.value = '<%=ChecklistIstanza.isBozza()%>';window.print();"/>--%>
  		<input type="hidden" name="bozza" value="" />
  		
  		<dhv:permission name="server_documentale-view">
  				<!--  BOX DOCUMENTALE -->
	  <jsp:include page="../../gestione_documenti/boxDocumentale.jsp">
    <jsp:param name="orgId" value="<%=request.getParameter("orgId") %>" />
        <jsp:param name="stabId" value="<%=request.getParameter("idStabilimento") %>" />
         <jsp:param name="ticketId" value="<%=request.getParameter("idControllo") %>" />
      <jsp:param name="tipo" value="allegato" />
       <jsp:param name="idCU" value="<%=request.getParameter("specie") %>" />
        <jsp:param name="url" value="<%=request.getParameter("url") %>" />
</jsp:include>
<!--  BOX DOCUMENTALE -->
    </dhv:permission>
    <br>
  <br>
  <br>
<!--   <table class="tableClass" border="1" width="100%"> -->
<!--   		<tr> -->
<!--   			<td style="font-weight: bold;background-color: gray;"> -->
<!--   				VERIFICA ESECUZIONE PRESCRIZIONI/AZIONI CORRETTIVE -->
<!--   			</td> -->
<!--   		</tr> -->
<!--   		<tr> -->
<!--   			<td> -->
<!--   				da effettuare dopo la scadenza del tempo assegnato e prima di rendere definitivo il risultato del controllo<br><br> -->
<!--   				Prescrizioni/azioni correttive eseguite in data<br> -->
<!--   				<span class="NocheckedItem">SI&nbsp;&nbsp;</span> -->
<!--   				<span class="NocheckedItem">NO&nbsp;&nbsp;</span> -->
<!--   				<input type="radio" name="prescrizioni" id="prescrizioni" value="si"/>SI &nbsp;
<!--   				<input type="radio" name="prescrizioni" id="prescrizioni" value="no"/>NO  --> 
<!--   			</td> -->
<!--   		</tr> -->
<!--   </table> -->

 <table width="100%" style="border: 1px solid black">
 <col width="55%">
 <tr><td>E' stato dato PREAVVISO (max 48 ore) del presente controllo</td>
 <td> NO [&nbsp;<%if (Ticket.getFlag_preavviso()==null || ( Ticket.getFlag_preavviso()!=null && Ticket.getFlag_preavviso().equalsIgnoreCase("n"))){%>X<%} %>&nbsp;] SI [&nbsp;<% if (Ticket.getFlag_preavviso()!=null && !Ticket.getFlag_preavviso().equalsIgnoreCase("n")){%>X<%} %>&nbsp;] </td>
 </tr>
 <tr><td></td>
 <td> Se SI in data <label class="layout"><%=toDateasString(Ticket.getData_preavviso_ba()) %></label> tramite: </td></tr>
 <tr><td></td>
 <td> [&nbsp;<% if (Ticket.getFlag_preavviso()!=null && Ticket.getFlag_preavviso().equals("P")){%>X<%} %>&nbsp;] Telefono</td></tr>
 <tr><td></td>
 <td>[&nbsp;<% if (Ticket.getFlag_preavviso()!=null && Ticket.getFlag_preavviso().equals("T")){%>X<%} %>&nbsp;] Telegramma/lettera/fax</td></tr>
 <tr><td></td>
 <td>[&nbsp;<% if (Ticket.getFlag_preavviso()!=null && Ticket.getFlag_preavviso().equals("A")){%>X<%} %>&nbsp;] Altra forma ............... </td></tr>
 <tr><td colspan="2">L'esito del presente controllo sarà considerato anche per la verifica del rispetto degli impegni di Condizionalità.</td></tr>
  </table>

 
  <%  if(versioneChecklist.equals("2")) { %>
 <%@ include file="liste_di_riscontro_footer.jsp" %>
 <% } %>
 
<br/><br/>
  <div>Firma Proprietario/Detentore/Conduttore &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Firma e Timbro Veterinario Ufficiale</div>
  
  <script>sumTotalPunt()</script>
  
 
 </form> 
 </body> 