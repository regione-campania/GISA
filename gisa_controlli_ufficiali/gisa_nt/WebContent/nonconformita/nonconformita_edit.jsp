<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Iterator"%>
<jsp:useBean id="AcqueDiRete" class="java.lang.String" scope="request"/>
<jsp:useBean id="View" class="java.lang.String" scope="request"/>

<link href="css/nonconformita.css" rel="stylesheet" type="text/css" />

<jsp:useBean id="Macrocategorie" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="listaLineeCU" class="java.util.ArrayList" scope="request"/>
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



function openNotePopupDiffida(orgId,idC,dataC,tipo_nc){
	var res;
	var result;

	
		window.open('DiffidaNC.do?command=Add&idC='+idC+'&dataC='+dataC+'&orgId='+orgId+'&tipoNc='+tipo_nc,null,
		'height=800px,width=680px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');
		
		
		
		} 
		
function openNotePopupFollowup(orgId,idC,dataC,tipo_nc){
	var res;
	var result;
	var tipologiaNc = document.getElementById("Provvedimenti"+tipo_nc+"_1").value;

	
		window.open('FollowupNC.do?command=Add&idC='+idC+'&dataC='+dataC+'&orgId='+orgId+'&tipoNc='+tipo_nc+'&tipologiaNc='+tipologiaNc,null,
		'height=800px,width=680px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=si ,modal=yes');
		
		
		
		} 
	

	
	


function openNotePopupSanzione(orgId,idC,dataC,tipo_nc){
	var res;
	var result;

	
		window.open('SanzioniNC.do?command=Add&idC='+idC+'&dataC='+dataC+'&orgId='+orgId+'&tipoNc='+tipo_nc,null,
		'height=800px,width=680px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=no ,modal=yes');
		
	
		} 
	
function openNotePopupSanzioneTerzi(impresa,orgId,idC,dataC,tipo_nc){
	var res;
	var result;

	
		window.open('SanzioniNC.do?command=Add&idC='+idC+'&dataC='+dataC+'&orgId='+orgId+'&orgIdSanzionata='+impresa+'&tipoNc='+tipo_nc,null,
		'height=800px,width=680px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=no ,modal=yes');
		
		
		} 


function openNotePopupSequestro(orgId,idC,dataC,tipo_nc){
	var res;
	var result;


		window.open('SequestriNC.do?command=Add&idC='+idC+'&dataC='+dataC+'&orgId='+orgId+'&tipoNc='+tipo_nc,null,
		'height=800px,width=680px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=no ,modal=yes');
		
		
	
		} 

function openNotePopupReato(orgId,idC,dataC,tipo_nc){
	var res;
	var result;

	
		window.open('ReatiNC.do?command=Add&idC='+idC+'&dataC='+dataC+'&orgId='+orgId+'&tipoNc='+tipo_nc,null,
		'height=800px,width=680px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=no ,modal=yes');
		
		
		
	
}
</script>


<input type="hidden" name="tipoControlloUfficiale" id="tipoControlloUfficiale"" value="<%=CU.getTipoCampione() %>">
<input type="hidden" name="dataInizioControlloUfficiale" id="dataInizioControlloUfficiale" value="<%=CU.getAssignedDate() %>">

<div class="tabber">

<% if ( (View!=null && View.equals("AutoritaNonCompetenti") ) || (AcqueDiRete!=null && AcqueDiRete.equals("AcqueDiRete") )) {%>
 <div style="display: none">
<% } %>
<div class="tabbertab">
<%@ include file="non_conformita_formali_edit.jsp" %>

</div>
<div class="tabbertab">
 <%@ include file="non_conformita_significative_edit.jsp" %>

</div>
<% if ( (View!=null && View.equals("AutoritaNonCompetenti") ) || (AcqueDiRete!=null && AcqueDiRete.equals("AcqueDiRete") )) {%>
 </div>
<% } %>

 <div class="tabbertab">
 <%@ include file="non_conformita_gravi_edit.jsp" %>

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


