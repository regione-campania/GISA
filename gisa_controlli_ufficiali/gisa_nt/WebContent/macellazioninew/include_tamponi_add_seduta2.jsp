<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.macellazioninew.base.Partita"%>
<%@page import="org.aspcfs.modules.macellazioninew.base.Tampone"%>
<%@page import="org.aspcfs.modules.macellazioninew.base.TipoRicerca"%>
<%@page import="org.aspcfs.utils.web.LookupElement"%>
<%@page import="org.aspcfs.utils.web.LookupList"%><jsp:useBean id="PianiMonitoraggio" class="org.aspcfs.utils.web.LookupList" scope = "request"/>

<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<script type="text/javascript">
function setTampone(idMacello,sedutaMacellazione)
{

		dataMacellazione = document.getElementById('vpm_data').value ;
		PopolaCombo.getTampone(idMacello,dataMacellazione,sedutaMacellazione,setTamponeCallBack);
}

 function setTamponeCallBack(returnValue)
 {

	 if (document.getElementById('checkTampone').checked)
	 {
	 if (returnValue.id > 0)
	 {	
			document.getElementById('id_tampone').value = returnValue.id;

			document.getElementById('id_tipo_carcassa').value = returnValue.id_tipo_carcassa;
			document.getElementById('id_tipo_ricerca').value = returnValue.id_tipo_ricerca;
			if (returnValue.metodo =='true')
			{
				document.getElementById("metodo_distruttivo").checked = "true";
				document.getElementById("metodo_non_distruttivo").checked = "false";
			}
			else
			{
				document.getElementById("metodo_distruttivo").checked = "false";
				document.getElementById("metodo_non_distruttivo").checked = "true";
			}
			
			
	  }
	 document.getElementById('tampon1').style.display = "";
	
	 }
	 else
	 {
		 document.getElementById('tampon1').style.display = "none";
		 document.getElementById('id_tampone').value ="-1";
	 }
	


 }
</script>

<jsp:useBean id="Tampone"			class="org.aspcfs.modules.macellazioninew.base.Tampone"		scope="request" />
<jsp:useBean id="MatriciTamponi"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="AnalisiTamponi"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="OrgDetails"		class="org.aspcfs.modules.stabilimenti.base.Organization"	scope="request" />
<jsp:useBean id="Partita"				class="org.aspcfs.modules.macellazioninew.base.Partita"			scope="request" />

<%

int idPianoMonitoraggioTamponi = 469 ;
%>
<table class="details" width="100%" border="0" cellpadding="5" cellspacing="0">
    			<tr>
    				<th colspan="4">Effettuato Tampone <input type = "checkbox" id="checkTampone" name = "checkTampone" onclick="setTampone(<%=OrgDetails.getOrgId() %>,<%=Partita.getCd_seduta_macellazione() %>)" > </th>
    			</tr>
    			
    			<tr>
    				<th>Piano</th><th>Matrice</th> <th>Tipo Analisi</th>  <th colspan="2">Metodo</th>
    				 
    			</tr>
    					
    			<tr class="containerBody" id ="tampon1" value = "-1" style="display:none;" >
    				<input type="hidden" name="id_tampone" id = "id_tampone" value="<%=Tampone.getId() %>" />
    				<td>
    					<input type="hidden" name="piano_monitoraggio" id = "piano_monitoraggio" value="<%=idPianoMonitoraggioTamponi %>" />
    					<div id="descrizionePiano"></div>
    					
    					</td>
          
          
          
    				<td> 
    					
    					<%=MatriciTamponi.getSelectedValue( 4 ) %>
    					<input type = "hidden" name = "id_tipo_carcassa" id = "id_tipo_carcassa" value = "4">
    					</td>
          
    				<td>
<%
    				AnalisiTamponi.setMultiple(true);
    				AnalisiTamponi.setSelectSize(6);
    				LookupList listaElementiSel = new LookupList();
    				for (TipoRicerca r : Tampone.getTipo_ricerca())
    				{
    					LookupElement el = new LookupElement();
    					el.setCode(r.getId());
    					el.setDescription(r.getDescrizione());
    					listaElementiSel.add(el);
    					
    				}
    				if (listaElementiSel.size()==0)
    				{
    					LookupElement elm = new LookupElement();
    					elm.setCode(-1);
    					elm.setDescription("Seleziona Analita");
    					listaElementiSel.add(elm);
    					
    				}
%>
    					<%=AnalisiTamponi.getHtmlSelect( "id_tipo_ricerca" , -1 ) %>
    				</td>
    				
    				<td>
    					Distruttivo <input type = "radio" name = "metodo" id = "metodo_distruttivo" value = "si">
    					Non Distruttivo <input type = "radio" id = "metodo_non_distruttivo" name = "metodo" checked="checked"  value = "no">
    					
    				</td>
    				
    				
    				
    				
    				
    			</tr>
    			
    		
    			
</table>
<script>getCodiceTipoPianoFromCodiceInterno(<%=idPianoMonitoraggioTamponi%>)</script>
