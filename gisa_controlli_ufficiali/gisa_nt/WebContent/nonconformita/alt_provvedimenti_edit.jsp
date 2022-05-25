<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Iterator"%>
<link href="css/nonconformita.css" rel="stylesheet" type="text/css" />

<jsp:useBean id="Macrocategorie" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="listaLineeCU" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="listaOperatoriMercatoCU" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="ProvvedimentiBenessereMacellazione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ProvvedimentiBenessereTrasporto" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@page import="org.aspcfs.modules.lineeattivita.base.LineeAttivita"%>


<script>


var unloadEvent = function (e) {
	
	var salvato = document.getElementById('dosubmit');
	if (salvato!=null && salvato.value!="1")
		{
    var confirmationMessage = "Attenzione! ricordarsi di cliccare sul bottone SALVA per evitare di perdere i follow up inseriti.";

    (e || window.event).returnValue = confirmationMessage; //Gecko + IE
    loadModalWindowUnlock();
    return confirmationMessage; //Webkit, Safari, Chrome etc.
		}
};
window.addEventListener("beforeunload", unloadEvent);


function openNotePopupFollowup(altId,idC,dataC,tipo_nc){
	var res;
	var result;
	var tipologiaNc = document.getElementById("Provvedimenti"+tipo_nc+"_1").value;

	
		window.open('FollowupNC.do?command=Add&idC='+idC+'&dataC='+dataC+'&altId='+altId+'&tipoNc='+tipo_nc+'&tipologiaNc='+tipologiaNc,null,
		'height=800px,width=680px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=si ,modal=yes');
		
		
		
		} 
	

function openNotePopupDiffida(altId,idC,dataC,tipo_nc){
	var res;
	var result;

	
		window.open('DiffidaNC.do?command=Add&idC='+idC+'&dataC='+dataC+'&altId='+altId+'&tipoNc='+tipo_nc,null,
		'height=800px,width=680px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=no ,modal=yes');
		
		
		
		} 
	


function openNotePopupSanzione(altId,idC,dataC,tipo_nc){
	var res;
	var result;

	
		window.open('SanzioniNC.do?command=Add&idC='+idC+'&dataC='+dataC+'&altId='+altId+'&tipoNc='+tipo_nc,null,
		'height=800px,width=680px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=no ,modal=yes');
		
		
		
		} 
	
function openNotePopupSanzioneTerzi(impresa,altId,idC,dataC,tipo_nc, tipo_op){
	var res;
	var result;

	
		window.open('SanzioniNC.do?command=Add&idC='+idC+'&dataC='+dataC+'&altId='+altId+'&orgIdSanzionata='+impresa+'&tipoNc='+tipo_nc+'&tipologiaOp='+tipo_op,null,
		'height=800px,width=680px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=no ,modal=yes');
		
		
		} 	

function openNotePopupSequestro(altId,idC,dataC,tipo_nc){
	var res;
	var result;

	
		window.open('SequestriNC.do?command=Add&idC='+idC+'&dataC='+dataC+'&altId='+altId+'&tipoNc='+tipo_nc,null,
		'height=800px,width=680px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=no ,modal=yes');
		
		
		
		} 

function openNotePopupReato(altId,idC,dataC,tipo_nc){
	var res;
	var result;

	
		window.open('ReatiNC.do?command=Add&idC='+idC+'&dataC='+dataC+'&altId='+altId+'&tipoNc='+tipo_nc,null,
		'height=800px,width=680px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=no ,modal=yes');
		
		
	
	
}
</script>

<input type="hidden" name="tipoControlloUfficiale" id="tipoControlloUfficiale"" value="<%=CU.getTipoCampione()%>">
<input type="hidden" name="dataInizioControlloUfficiale" id="dataInizioControlloUfficiale" value="<%=CU.getAssignedDate()%>">

<script>

function openRicercaGlobale(){
	var res;
	var result;

	
		window.open('GlobalSearch.do?command=SearchFormImprese',null,
		'height=500px,width=7000px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=no ,modal=yes');
		
		
		
		} 

</script>

<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">

      <strong><dhv:label name="">Aggiungi Soggetto (diverso da quello ispezionato) a cui attribuire la non conformita'</dhv:label></strong>

    </th>
	</tr>
 <tr>
    <td valign="top" class="formLabel">
      Non Conformita A carico Di
    </td>
    <td>
    
     <input type = "hidden" name = "id_impresa_sanzionata" id = "id_impresa_sanzionata" value="<%=TicketDetails.getIdImpresaSanzionata()%>">
     <input type = "hidden" name = "id_tipologia_operatore" id = "id_tipologia_operatore" value="<%=TicketDetails.getIdTipologiaImpresaSanzionata()%>">
    
    
    <input type = "text" readonly name = "descrizione_impresa_sanzionata"  id = "descrizione_impresa_sanzionata"  size="100" value="<%=TicketDetails.getRagioneSocialeImpresaSanzionata()%>">
    <br>
<!--          <a href="javascript:openRicercaGlobale()">Seleziona da Gisa</a> -->
    <input style="display:none" type="radio" <%=(!TicketDetails.isFuoriRegioneImpresaSanzionata()) ? "checked" : ""%> id="operatoreFuoriRegione_NO" name="operatoreFuoriRegione" value="0"/>
    <input style="display:none" type="radio"  <%=(TicketDetails.isFuoriRegioneImpresaSanzionata()) ? "checked" : ""%> id="operatoreFuoriRegione_SI" name="operatoreFuoriRegione" value="1"/> 
    
    
    <br>
<!--          <a href="javascript:openRicercaGlobale()">Seleziona da Gisa</a> -->
    
    
    </td>
	</tr>
	</table>
	<br>
	
<div class="tabber">
<div style="display: none">
<div class="tabbertab">
 <%@ include file="alt_non_conformita_formali.jsp" %>

</div>
<div class="tabbertab">
 <%@ include file="alt_non_conformita_significative.jsp" %>

</div>
 </div>
 <div class="tabbertab">
 <%@ include file="alt_provvedimenti_gravi_edit.jsp" %>

 </div>
 </div>


<input type = "hidden" id ="abilita_formali" name = "abilitanc_formali" value = "<%=request.getAttribute("abilita_formali") %>">
<input type = "hidden" id = "abilita_significative" name = "abilitanc_significative"  value = "<%=request.getAttribute("abilita_significative") %>">
<input type = "hidden" id = "abilita_gravi" name = "abilitanc_gravi"  value = "<%=request.getAttribute("abilita_gravi") %>">
<input type = "hidden" id ="formali" name = "formali" value = "<%=request.getAttribute("Formali") %>">
<input type = "hidden" name = "idIspezione" value = "<%=CU.getTipoIspezione() %>">

<script>
resetElementi(0,0,0,1,1,1);
</script>


