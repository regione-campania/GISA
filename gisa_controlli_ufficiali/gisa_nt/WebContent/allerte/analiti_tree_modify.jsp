<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script language="JavaScript">
function openTree(campoid1,campoid2,table,id,idPadre,livello,multiplo,divPath,idRiga)
{
	idPiano='-1';
	controllo = true ;
	if(document.getElementById('motivazione_campione')!=null  && document.getElementById('motivazione_campione').value=='2')
	{
		idPiano=document.getElementById('motivazione_piano_campione').value;
		if (idPiano=='-1')
		{
			alert('- Controllare di aver selezionato il piano come motivazione del campione');
			controllo = false ;
		}
		else
		{
			window.open('Tree.do?command=DettaglioTree&idPiano='+idPiano+'&multiplo='+multiplo+'&divPath='+divPath+'&idRiga='+idRiga+'&campoId1='+campoid1+'&campoId2='+campoid2+'&nomeTabella='+table+'&campoId='+id+'&campoPadre='+idPadre+'&campoDesc=nome&campoLivello=livello&campoEnabled=nuova_gestione&sel=true');

		}
	}
	else
	{
		window.open('Tree.do?command=DettaglioTree&multiplo='+multiplo+'&divPath='+divPath+'&idRiga='+idRiga+'&campoId1='+campoid1+'&campoId2='+campoid2+'&nomeTabella='+table+'&campoId='+id+'&campoPadre='+idPadre+'&campoDesc=nome&campoLivello=livello&campoEnabled=nuova_gestione&sel=true');
		

		}

}

</script>

<script>
function bloccaSelezioneAnalitiMod(){
	/*var num_analiti_selezionati = document.getElementById("elementi").value;
	if (num_analiti_selezionati!=0){
		openTree('analitiId','pathAnaliti','analiti','analiti_id','id_padre','livello','si','divPathAnaliti','clona');
	}
	else {
		openTree('analitiId','pathAnaliti','analiti','analiti_id','id_padre','livello','si','divPathAnaliti','clona');
	}
	*/
	alert('iuuuuuuu');
	indice = 1;
	while (document.getElementById('divPathAnaliti_'+indice)!=null && document.getElementById('divPath_'+indice)== null)
	{
		document.getElementById('divPathAnaliti_'+indice).parentNode.removeChild(document.getElementById('divPathAnaliti_'+indice)); 
		document.getElementById('analitiId_'+indice).parentNode.removeChild(document.getElementById('analitiId_'+indice)); 
		document.getElementById('pathAnaliti_'+indice).parentNode.removeChild(document.getElementById('pathAnaliti_'+indice)); 
		indice ++ ;
	}
	elementi = document.getElementById('elementi').value = 0;

	openTree('analitiId','pathAnaliti','analiti','analiti_id','id_padre','livello','si','divPathAnaliti','clona');

	
}
</script>
	
  <tr>
      <td name="tipoCampione1" id="tipoCampione1" nowrap class="formLabel">
        <dhv:label name="">Azione non conforme per</dhv:label>
      </td>
      <td>
    <table class = "noborder"><tr><td>
    <input type = "hidden" name = "elementi" id = "elementi" value = "0">
    <input type = "hidden" name = "size" id = "size" value = "0">
    <tr id = "clona" style="display: none">
    <td>
    <input type = "hidden" name = "analitiId">
    <input type = "hidden" name = "pathAnaliti">
    
    <div id = "divPathAnaliti"></div>
     
   </td>
   </tr>
   <tr>
   <td>
     <table class="noborder">
		    <%	    
		    
						int j=0 ;
						for(Analita a : tipi_a)
						{
							j++ ;
							int chiave = a.getIdAnalita();
							String descrizione = a.getDescrizione();
							out.print("<tr id='divPathAnaliti_"+j+"' style=\"\" class=\"containerBody\"><td valign=\"top\"> "+j+") "+descrizione+"</td></tr>");
							out.print("<input type='hidden' id='analitiId_"+j+ "' value='"+ chiave +"'/>");
							out.print("<input type='hidden' id='pathAnaliti_"+j+ "' value=''/>");
						}
						%>
			<input type="hidden" id="numeroAnaliti" name="numeroAnaliti" value="<%=j%>">			
			<tr><td><% if(TicketDetails.getNoteAnalisi() != null && !TicketDetails.getNoteAnalisi().equals("")) { %>
				<%="Note : "+TicketDetails.getNoteAnalisi() %>
				<% } else { %>
					<br><td id="id_note_old" style="" valign="top"><b>Note:</b>N.D</td>
					<% } %>
			</td></tr>
		</table>				
   </td>
   
   <td>
   <a href = "javascript:bloccaSelezioneAnalitiMod();">(Selezionare Tipo Analisi)</center></a><font color="red">*</font>
   <br> <br>
    <textarea rows="8" cols="40" name="noteAnalisi"></textarea></br>
   </td>
   </tr>
   </table>
    </td>
     </tr>

