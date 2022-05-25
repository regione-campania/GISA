<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
 <script type="text/javascript">
function openPopupChecklistPdf(idAudit){
	var res;
	var result;
	
	window.open('GestioneDocumenti.do?command=GeneraPDFChecklist&id='+idAudit+'&tipo=Checklist','popupSelect',
	'toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');
} 


function openRichiestaPDF_Checklist(idAudit){
	var res;
	var result=
		window.open('GestioneDocumenti.do?command=GeneraPDFChecklist&id='+idAudit+'&tipo=Checklist','popupSelect',
		'height=200px,width=842px,left=200px, top=200px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
		var text = document.createTextNode('GENERAZIONE PDF IN CORSO.');
		span = document.createElement('span');
		span.style.fontSize = "30px";
		span.style.fontWeight = "bold";
		span.style.color ="#ff0000";
		span.appendChild(text);
		var br = document.createElement("br");
		var text2 = document.createTextNode('Attendere la generazione del documento... (potrebbe richiedere qualche secondo)');
		span2 = document.createElement('span');
		span2.style.fontSize = "20px";
		span2.style.fontStyle = "italic";
		span2.style.color ="#000000";
		span2.appendChild(text2);
		result.document.body.appendChild(span);
		result.document.body.appendChild(br);
		result.document.body.appendChild(span2);
		result.focus();
	}
</script>

 <input type="button" value="Stampa Scheda Check List"	onClick='javascript:openRichiestaPDF_Checklist(<%=Audit.getId() %>)'>