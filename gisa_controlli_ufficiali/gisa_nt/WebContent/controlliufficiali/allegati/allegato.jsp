<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@page import="org.aspcf.modules.checklist_benessere.base.Capitolo"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.aspcf.modules.checklist_benessere.base.Domanda"%><jsp:useBean id="Allevamento" class="org.aspcf.modules.controlliufficiali.base.Organization" scope="request"/>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Capitolo" class="org.aspcf.modules.checklist_benessere.base.Capitolo" scope="request"/>
<jsp:useBean id="Domanda" class="org.aspcf.modules.checklist_benessere.base.Domanda" scope="request"/>
<jsp:useBean id="CapitoliList" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="idAlleg" class="java.lang.String" scope="request"/>
<jsp:useBean id="esito" class="java.lang.String" scope="request"/>
<jsp:useBean id="numAllegato" class="java.lang.String" scope="request"/>
<jsp:useBean id="nuova_gestione" class="java.lang.String" scope="request"/>
<jsp:useBean id="versioneChecklist" class="java.lang.String" scope="request"/>
<jsp:useBean id="AzFields" class="org.aspcf.modules.controlliufficiali.base.AziendeZootFields" scope="request"/>
<jsp:useBean id="Ticket" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>
<jsp:useBean id="Modulo" class="org.aspcfs.modules.allevamenti.base.ModuloControllo" scope="request"/>
<jsp:useBean id="AltreSpecie" class="java.lang.String" scope="request"/>

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
//      else{
//      	 document.getElementById('d_si_'+idCapitolo+'_'+idDomanda).checked = false;
//      	 document.getElementById('fav_'+idCapitolo+'_'+idDomanda).style.display = 'block';
//      	 document.getElementById('nfav_'+idCapitolo+'_'+idDomanda).style.display = 'none';   
     	 
//     }
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
    		if(puntA != null && parseInt(puntA.value))
        		totA += parseInt(puntA.value);
    		if(puntB != null && parseInt(puntB.value))
        		totB += parseInt(puntB.value);
    		if(puntC != null && parseInt(puntC.value))
        		totC += parseInt(puntC.value);
    		if(irreg != null && parseInt(irreg.value))
        		totIrreg += parseInt(irreg.value);
    	}
    }//fine for
    document.getElementById('totaleA').value = totA;
    document.getElementById('totaleB').value = totB;
    document.getElementById('totaleC').value = totC;
    document.getElementById('totIrreg').value = totIrreg;

	
}

function checkEsitoNC(esito){

	var numCapitoli = document.getElementById('dimCapitoli').value;
    var totA = 0;
    var totB = 0;
    var totC = 0;
    var totIrreg = 0;
    var j = 1;
    for(var i=1;i<=numCapitoli;i++){
    	var numDomande = document.getElementById('domCap_'+i).value;
    	for(var j=1;j<=numDomande;j++){
    		if(esito == 'NonFavorevole'){
    			document.getElementById('d_no_'+i+'_'+j).checked = true;
    	      	document.getElementById('nfav_'+i+'_'+j).style.display = 'block';
    	      	document.getElementById('fav_'+i+'_'+j).style.display = 'none'; 
    	    	document.getElementById('nafav_'+i+'_'+j).style.display = 'none'; 
    		}
    		else{
    	    	document.getElementById('d_si_'+i+'_'+j).checked = false;
    	      	document.getElementById('fav_'+i+'_'+j).style.display = 'block';
    	      	document.getElementById('nfav_'+i+'_'+j).style.display = 'none';   
    	    	document.getElementById('nafav_'+i+'_'+j).style.display = 'none'; 

    	   }
    	}
    }//fine for	
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

<body onload="javascript:verificaStatoControllo('<%=Allevamento.getData_chiusura_controllo()%>','true');<%--checkEsitoNC('<%=esito%>');--%>" >
<form method="post" name="myform" action="PrintModulesHTML.do?command=InsertChecklistBenessere&idControllo=<%=Allevamento.getIdControllo()%>&orgId=<%=Allevamento.getOrgId()%>&stabId=<%=Allevamento.getIdStabilimento()%>&specie=<%=codice_specie%>&auto-populate=true">

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
<%
if (!"15".equals(numAllegato))
{
%>
<B>LEGENDA</B>
<table border="1">
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
<%} %>
<div class="fine" style="height: 150px;">&nbsp;</div>
<table cellpadding="9" cellspacing="0" border="1" width="100%" class="details">

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
 
  <input type="hidden" name="idAlleg" id="idAlleg" value="<%=idAlleg%>" />
  <input type="hidden" name="idCU" id="idCU" value="<%=Allevamento.getIdControllo()%>" />
  <input type="hidden" name="dimCapitoli" id="dimCapitoli" value="<%=CapitoliList.size()%>" />
  
   <% 
   
   	  int cap=1;
   	  Iterator<Capitolo> it = CapitoliList.iterator();
      while(it.hasNext()){
  	  		Capitolo capitolo = it.next();
  	  		ArrayList<Domanda> domande = capitolo.getDomandeList();
  	  		int numDomande = domande.size();
  	  
  %>
  <tr class="containerBody">
  	<input type="hidden" id="domCap_<%=cap%>" name="domCap_<%=cap%>" value="<%= numDomande%>">
	<td rowspan="<%=numDomande%>"><%=capitolo.getDescription()%></td>
	<td><%=domande.get(0).getDescription()%></td>
  		<td>
  			<input type="radio" name="esito_<%=cap%>_1" id="d_si_<%=cap%>_1" value="si" onClick="gestisciCb('<%=cap%>_1')"/>
			<input type="text" readonly style="display: none;border: none;font-size:12pt;font-weight:bold;" size="1" id="fav_<%=cap%>_<%=1%>" name="favorevole" value="V"/>
		</td>
  		<td>
  			<input type="radio" name="esito_<%=cap%>_1" id="d_no_<%=cap%>_1" value="no" onClick="gestisciCb('<%=cap%>_1')"/>
  		  		<input type="text" readonly style="display: none;border: none;font-size:12pt;font-weight: bold;" size="1" id="nfav_<%=cap%>_<%=1%>" name="Nfavorevole" value="X"/>
  		</td>
  		<td>
  			<input type="radio" name="esito_<%=cap%>_1" id="d_na_<%=cap%>_1" value="na" onClick="gestisciCb('<%=cap%>_1')"/>
  		  		<input type="text" readonly style="display: none;border: none;font-size:12pt;font-weight: bold;" size="1" id="nafav_<%=cap%>_<%=1%>" name="Nafavorevole" value="NA"/>
  		</td>
		<td>
			<%--  <input type="text" name="oss_<%=cap%>_1" id="oss_<%=cap%>_1"/> --%>
			<textarea name="oss_<%=cap%>_1" id="oss_<%=cap%>_1"></textarea>
		</td>
		<td><input type="text" class="layout" readonly size="5" maxlength="5" value="0" name="tot_<%=cap%>_1" id="tot_<%=cap%>_1"/> 
	
		 </td>
		<td><input type="text" class="editField" size="5" value="0" maxlength="5" name="punteggio_<%=cap%>_1" id="puntA_<%=cap%>_1" onblur="javascript:if (isNaN(value)) { alert ('Errore il campo relativo al punteggio A può contenere solo valori numerici!'); document.getElementById('puntA_<%=cap%>_<%=1%>').value=''; } 
		else if (parseInt(value) < 0) { alert ('Errore il campo relativo al punteggio A non può essere negativo!'); document.getElementById('puntA_<%=cap%>_<%=1%>').value=''; }		
		else findTotal('<%=cap%>','<%=1%>');" /></td>
		<td><input type="text" class="editField" size="5" value="0" maxlength="5" name="punteggio_<%=cap%>_1" id="puntB_<%=cap%>_1" onblur="javascript:if (isNaN(value)) { alert ('Errore il campo relativo al punteggio B può contenere solo valori numerici!'); document.getElementById('puntB_<%=cap%>_<%=1%>').value=''; } 
		else if (parseInt(value) < 0) { alert ('Errore il campo relativo al punteggio B non può essere negativo!'); document.getElementById('puntB_<%=cap%>_<%=1%>').value=''; }
		else findTotal('<%=cap%>','<%=1%>');" /></td>
		<td><input type="text" class="editField" size="5" value="0" maxlength="5" name="punteggio_<%=cap%>_1" id="puntC_<%=cap%>_1" onblur="javascript:if (isNaN(value)) { alert ('Errore il campo relativo al punteggio C può contenere solo valori numerici!'); document.getElementById('puntC_<%=cap%>_<%=1%>').value=''; } 
		else if (parseInt(value) < 0) { alert ('Errore il campo relativo al punteggio C non può essere negativo!'); document.getElementById('puntC_<%=cap%>_<%=1%>').value=''; }
		else findTotal('<%=cap%>','<%=1%>');" /></td>
		<input type="hidden" id="punteggioA_<%=cap%>_1" name="punteggioA_<%=cap%>_1" value="0"/>
		<input type="hidden" id="punteggioB_<%=cap%>_1" name="punteggioB_<%=cap%>_1" value="0"/>
		<input type="hidden" id="punteggioC_<%=cap%>_1" name="punteggioC_<%=cap%>_1" value="0"/>
	    <input type="hidden" id="descCap_<%=cap%>" name="descCap_<%=cap%>" value="<%=capitolo.getDescription()%>"/>
	    <input type="hidden" id="descDom_<%=cap%>_<%=1%>" name="descDom_<%=cap%>_<%=1%>" value="<%=domande.get(0).getDescription()%>"/>
	    
  </tr>
  <%--script>gestisciCb('<%=cap%>_1')</script--%>   
  <% 
  for(int i=2;i<=numDomande;i++){ 
	%>	
	<tr class="containerBody">
  		<td><%=domande.get(i-1).getDescription()%></td>
  		<td>
			<input type="radio" name="esito_<%=cap%>_<%=i%>" id="d_si_<%=cap%>_<%=i%>" value="si" onClick="gestisciCb('<%=cap%>_<%=i%>')"/>
	  		<input type="text" readonly style="display: none;border: none;font-size:12pt;font-weight: bold;" size="1" id="fav_<%=cap%>_<%=i%>" name="favorevole" value="V"/>
  		</td>
  		<td>
  		<input type="radio" name="esito_<%=cap%>_<%=i%>" id="d_no_<%=cap%>_<%=i%>"  value="no" onClick="gestisciCb('<%=cap%>_<%=i%>')"/>
  		<input type="text" readonly style="display: none;border: none;font-size: 12pt;font-weight: bold;" size="1" id="nfav_<%=cap%>_<%=i%>" name="nonFavorevole" value="X"/>
  		</td>
  		<td>
  		<input type="radio" name="esito_<%=cap%>_<%=i%>" id="d_na_<%=cap%>_<%=i%>"  value="na" onClick="gestisciCb('<%=cap%>_<%=i%>')"/>
  		<input type="text" readonly style="display: none;border: none;font-size: 12pt;font-weight: bold;" size="1" id="nafav_<%=cap%>_<%=i%>" name="naFavorevole" value="NA"/>
  		</td>
		<td>
			<%-- <input type="text" name="oss_<%=cap%>_<%=i%>" id="oss_<%=cap%>_<%=i%>"/>--%>
			<textarea name="oss_<%=cap%>_<%=i%>" id="oss_<%=cap%>_<%=i%>"></textarea>
		</td>
		<td><input type="text" class="layout" readonly size="5" maxlength="5" value="0" name="tot_<%=cap%>_<%=i%>" id="tot_<%=cap%>_<%=i%>"/>
		</td>
		<td><input type="text" class="editField" size="5"  maxlength="5" value="0" name="punteggio_<%=cap%>_<%=i%>" id="puntA_<%=cap%>_<%=i%>" onblur="javascript:if (isNaN(value)) { alert ('Errore il campo relativo al punteggio A può contenere solo valori numerici!'); document.getElementById('puntA_<%=cap%>_<%=i%>').value=''; } 
		else if (parseInt(value) < 0) { alert ('Errore il campo relativo al punteggio A non può essere negativo!'); document.getElementById('puntA_<%=cap%>_<%=i%>').value=''; }
		else findTotal('<%=cap%>','<%=i%>');" /></td>
		<td><input type="text" class="editField" size="5" maxlength="5" value="0" name="punteggio_<%=cap%>_<%=i%>" id="puntB_<%=cap%>_<%=i%>" onblur="javascript:if (isNaN(value)) { alert ('Errore il campo relativo al punteggio B può contenere solo valori numerici!'); document.getElementById('puntB_<%=cap%>_<%=i%>').value=''; } 
		else if (parseInt(value) < 0) { alert ('Errore il campo relativo al punteggio B non può essere negativo!'); document.getElementById('puntB_<%=cap%>_<%=i%>').value=''; }
		else findTotal('<%=cap%>','<%=i%>');" /></td>
		<td><input type="text" class="editField" size="5" maxlength="5" value="0" name="punteggio_<%=cap%>_<%=i%>" id="puntC_<%=cap%>_<%=i%>" onblur="javascript:if (isNaN(value)) { alert ('Errore il campo relativo al punteggio C può contenere solo valori numerici!'); document.getElementById('puntC_<%=cap%>_<%=i%>').value=''; } 
		else if (parseInt(value) < 0) { alert ('Errore il campo relativo al punteggio C non può essere negativo!'); document.getElementById('puntC_<%=cap%>_<%=i%>').value=''; }
		else findTotal('<%=cap%>','<%=i%>');" /></td>
		<input type="hidden" id="punteggioA_<%=cap%>_<%=i%>" name="punteggioA_<%=cap%>_<%=i%>" value="0"/>
		<input type="hidden" id="punteggioB_<%=cap%>_<%=i%>" name="punteggioB_<%=cap%>_<%=i%>" value="0"/>
		<input type="hidden" id="punteggioC_<%=cap%>_<%=i%>" name="punteggioC_<%=cap%>_<%=i%>" value="0"/>
	 	<input type="hidden" id="descCap_<%=cap%>_<%=i%>" name="descCap_<%=cap%>_<%=i%>" value="<%=capitolo.getDescription()%>"/>
	    <input type="hidden" id="descDom_<%=cap%>_<%=i%>" name="descDom_<%=cap%>_<%=i%>" value="<%=domande.get(i-1).getDescription()%>"/>
		
  </tr>
  <%--script>gestisciCb('<%=cap%>_<%=i%>')</script--%>   
<% } //fine for sulle domande per capitolo
  
	++cap;
  	}//fine while
     
  	%>  
  <tr>
  	<td style="font-weight: bold;background-color: gray;">TOTALE</td>
  	<td style="font-weight: bold;background-color: gray;"></td>
  	<td style="font-weight: bold;background-color: gray;"></td>
  	<td style="font-weight: bold;background-color: gray;"></td>
  	<td style="font-weight: bold;background-color: gray;"></td>
  	<td style="font-weight: bold;background-color: gray;"></td>
  	<td style="font-weight: bold;background-color: gray;"><input type="text" class="layout" readonly size="5" maxlength="5" name="totIrreg" id="totIrreg" value="" onblur="javascript: sumTotalPunt();"/></td>
  	<td style="font-weight: bold;background-color: gray;"><input type="text" class="layout" readonly size="5" maxlength="5" name="totaleA" id="totaleA" value=""/></td>
  	<td style="font-weight: bold;background-color: gray;"><input type="text" class="layout" readonly size="5" maxlength="5" name="totaleB" id="totaleB" value=""/></td>
  	<td style="font-weight: bold;background-color: gray;"><input type="text" class="layout" readonly size="5" maxlength="5" name="totaleC" id="totaleC" value=""/></td>
  </tr>	
  </table>
  <P style="color: red;font-weight: bold;" align="justify"> ***ATTENZIONE! PER AGGIORNARE I PUNTEGGI COMPLESSIVI <br> CLICCARE SUL TOTALE IN CORRISPONDENZA DEL NUMERO IRREGOLARITA'<br> (PRIMO CAMPO SULLA RIGA "TOTALE")</P>
  <div id="idbtn" style="display:block;">
       	<input type="button" name="salva" class="buttonClass" value="Salva Temporaneo" onclick="javascript:if( confirm('La scheda sarà aggiornata come richiesto. Vuoi procedere con il salvataggio?')){document.myform.bozza.value = true; return document.myform.submit();}else return false;"/> &nbsp;
  		<input type="button" name="salva" class="buttonClass" value="Salva Definitivo" onclick="javascript:if( confirm('La scheda sarà aggiornata come richiesto ma i dati non saranno più modificabili. Vuoi procedere con il salvataggio definitivo?')){document.myform.bozza.value = false; return document.myform.submit();}else return false;"/> &nbsp;
  	</div>
  		<input type="hidden" name="bozza" value="" />
 
  <br>
  <br>
  <br>
<!--   <table border="1" width="100%"> -->
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
<%--   				<input type="radio" name="prescrizioni" id="prescrizioni" value="si"/>SI &nbsp;
<%--   				<input type="radio" name="prescrizioni" id="prescrizioni" value="no"/>NO --%>
<%--   				--%> 
<!--   			</td> -->
<!--   		</tr> -->
<!--   </table> -->

 <table width="100%">
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


 </form> 
</body>