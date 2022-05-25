<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="org.aspcfs.utils.web.LookupList"%>

<script language="javascript">
function openPopupModules(orgId, ticketId, idCU, url, tipo){
        var res;
        var result;
  
        	  window.open('PrintModulesHTML.do?command=ViewModules&orgId='+orgId+'&ticketId='+ticketId+'&idCU='+idCU+'&url='+url+'&tipo='+tipo,'popupSelect',
              'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
    		
    		
    		
        
               
        
} 

</script>

 <script type="text/javascript">
 function openModificaModulo(orgId,idCampione,idC, url, mod){
	//var mes = document.getElementById('messaggio_pnaa').value;
	var mes = '';
	
	if( mes != '' && mes != 'null'){
		alert(mes);
	}else{
		
		  window.open('CampioniReport.do?command=ModificaSchedaModulo&idCampione='+idCampione+'&idCU='+idC+'&orgId='+orgId+'&url='+url+'&tipoModulo='+mod,'popupSelect',
				'height=600px,width=800px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
   }				
}
 
 function openModuloPnaa(idCampione){
		//var mes = document.getElementById('messaggio_pnaa').value;
		var mes = '';
		
		if( mes != '' && mes != 'null'){
			alert(mes);
		}else{
			
			  window.open('GestionePnaa.do?command=View&idCampione='+idCampione,'popupSelect',
					'height=600px,width=800px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
	   }				
	}
</script>

 <script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>
	
		
	<% 
	String tipoModulo = null;
	String nomeModulo = null;

      if (PopolaCombo.isPrevistoVerbaleCampione(TicketDetails.getId(), "modello_1")){
    	  tipoModulo = "1";
    	  nomeModulo = "Mod. 1 Molluschi";
      }
      else if (PopolaCombo.hasEventoMotivoCU("isPnaa", TicketDetails.getMotivazione_piano_campione(), -1)){
	    	  tipoModulo = "19";
    		  nomeModulo = "Verbale Prelievo Pnaa";
      }
      else if (PopolaCombo.hasEventoMotivoCU("isTrichinella", TicketDetails.getMotivazione_piano_campione(), -1)){
    	  tipoModulo = "14";
    	  nomeModulo = "Mod. Trichinella";
      }
      else if (PopolaCombo.isPrevistoVerbaleCampione(TicketDetails.getId(), "modello_2")){
    	  tipoModulo = "2";
    	  nomeModulo = "Mod. 2";
      }
      else if (PopolaCombo.isPrevistoVerbaleCampione(TicketDetails.getId(), "modello_3")){
    	  tipoModulo = "3";
    	  nomeModulo = "Mod. 3";
      }
      else if (PopolaCombo.isPrevistoVerbaleCampione(TicketDetails.getId(), "modello_4")){
    	  tipoModulo = "4";
    	  nomeModulo = "Mod. Fisico";
      }
	%>
	
<% if (tipoModulo != null && (tipoModulo=="1" || tipoModulo=="2" || tipoModulo=="3" || tipoModulo=="4" || tipoModulo=="14" )) { %>	
<div align="right" style="padding-left: 210px; margin-bottom: 45px">
<% if (TicketDetails.getClosed()==null) { %>
<br><a href="javascript:openModificaModulo('<%= OrgDetails.getOrgId() %>', '<%= TicketDetails.getId() %>', '<%= TicketDetails.getIdControlloUfficiale() %>', '<%= TicketDetails.getURlDettaglio() %>', '<%=tipoModulo%>' );">
<font size="3px" color="#006699" style="font-weight: bold;">
	Modifica Dati <%=nomeModulo %>
</font>
</a>
<% } %>
<br><a style="text-decoration: none; color:black;" href="javascript:openRichiestaPDF_ModuliCampione('<%= TicketDetails.getId() %>','<%= OrgDetails.getOrgId() %>', '<%= TicketDetails.getIdControlloUfficiale() %>', '<%= TicketDetails.getURlDettaglio() %>', '<%=tipoModulo%>' );">
<input type="button" 
	value="Stampa <%=nomeModulo%>"/> 
</a>
</div>
<%  } else  if (tipoModulo != null && (tipoModulo=="19")) { %>	
<script>alert("Questo è un campione con motivazione PNAA. Si ricorda di compilare l'apposito verbale.");</script>
<div align="right" style="padding-left: 210px; margin-bottom: 45px">
<br><a style="text-decoration: none; color:black;" href="javascript:openModuloPnaa('<%= TicketDetails.getId() %>');">
<input type="button" value="Compila <%=nomeModulo%>"/> 
</a>
</div>
<%  } %>

<%-- <input type="hidden" name="tipoAnalita" id="tipoAnalita" value="<%=tipoAnalita%>" /> --%>