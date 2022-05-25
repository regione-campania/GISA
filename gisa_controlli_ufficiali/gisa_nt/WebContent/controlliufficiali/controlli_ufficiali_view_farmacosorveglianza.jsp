<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
  
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.aspcfs.checklist.base.Audit"%><script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
        <script type="text/javascript" src="dwr/engine.js"> </script>
        <script type="text/javascript" src="dwr/util.js"></script>
        <script type="text/javascript">

       
			function controlloChecklist(idCu)
			{
				orgId = document.details.orgId.value;
				idCu = document.details.id.value;
				
					PopolaCombo.controlloAperturaChecklist( orgId ,idCu,viewMessageCallback) ;
				
			}
	
			function viewMessageCallback (returnValue)
			{
				
				if (returnValue == "")
				{
					compilaCheckList('<%="Sei sicuro che la CheckList Selezionata sia quella principale  ? "%>',<%=TicketDetails.getOrgId() %>,<%=TicketDetails.getId() %>,<%=TicketDetails.getPaddedId()%>,'1','details')
				}
				else
				{
					alert ('ATTENZIONE : per poter inserire la checklist occorre provvedere alla chiusura dei seguenti controlli \n'+returnValue);
				}
				
			}			

			</script>


		<tr>
			<th colspan="2"><strong><dhv:label
				name="sanzionia.information">Scheda Controllo Ufficiale</dhv:label></strong></th>
				<input type = "hidden" name = "orgId" value = "<%=TicketDetails.getOrgId() %>">
		</tr>
		
	<tr class="containerBody">
		<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.site">Site</dhv:label></td>
		<td><%=SiteIdList.getSelectedValue(TicketDetails.getSiteId())%>
					<%
					%> 
					<input type="hidden"
						name="siteId" value="<%=TicketDetails.getSiteId()%>"></td>
	</tr>
			
  <input type="hidden" name="id" id="id"
			value="<%=  TicketDetails.getId() %>" />
			
		
 <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="">Identificativo C.U.</dhv:label>
    </td>
   
     
      <td>
        <%= toHtml(TicketDetails.getPaddedId()) %>
        <input type="hidden" name="idControlloUfficiale" id="idControlloUfficiale"
			value="<%=  TicketDetails.getPaddedId() %>" />
	    <input type="hidden" name="idC" id="idC"
			value="<%=  TicketDetails.getPaddedId() %>" />
      </td>
    
  </tr>
 <tr class="containerBody">
      <td name="tipoCampione1" id="tipoCampione1" nowrap class="formLabel">
        <dhv:label name="">Tipo di controllo</dhv:label>
      </td>
    <td>
    <%
    
    if(TicketDetails.getTipoCampione()==3){
    	if(TicketDetails.getAuditTipo()==1){
    	if(TicketDetails.getTipoAudit()==2){
    		String  bpi="- Controlli Effettuati: ";

    		HashMap<Integer,String> listaBpi= TicketDetails.getLisaElementibpiOhaccp();
    		Iterator<Integer> valoriBpiSel=TicketDetails.getLisaElementibpiOhaccp().keySet().iterator();
    		
    		while(valoriBpiSel.hasNext()){
    			String bpiSel=listaBpi.get(valoriBpiSel.next());
    			
    			bpi=bpi+" "+bpiSel+" - ";
    		}
    		
    		out.print("- Audit: "+TicketDetails.getDescriptionTipoAudit()+" - "+AuditTipo.getSelectedValue(TicketDetails.getAuditTipo())+".<br>"+bpi);
    	}
    	
     
    else{
    	
    	if(TicketDetails.getTipoAudit()==3){
    		HashMap<Integer,String> listaHaccp= TicketDetails.getLisaElementibpiOhaccp();
    		Iterator<Integer> valoriHaccpSel=TicketDetails.getLisaElementibpiOhaccp().keySet().iterator();
    		String  haccp="- Controlli Effettuati: ";
    		while(valoriHaccpSel.hasNext()){
    			String haccpSel=listaHaccp.get(valoriHaccpSel.next());
    			haccp=haccp+" "+haccpSel+" - ";
    		}
    		
    		out.print("- Audit: "+TicketDetails.getDescriptionTipoAudit()+" - "+AuditTipo.getSelectedValue(TicketDetails.getAuditTipo())+"<br>"+haccp);
    		
    		
    	}else{
    		
    			out.print("- Audit: "+TicketDetails.getDescriptionTipoAudit()+" - "+AuditTipo.getSelectedValue(TicketDetails.getAuditTipo())+".<br>");
    		
    		
    	}
    }
    }else{
    	out.print("- Audit: "+AuditTipo.getSelectedValue(TicketDetails.getAuditTipo())+".<br>");
    	if(TicketDetails.getAuditTipo()==6){
    		out.print("Descrizione : "+TicketDetails.getNoteAltrodiSistema());
    		
    	}
    }	
    	
    }else{
    	if(TicketDetails.getTipoCampione()==4){
    			
    		if(TicketDetails.getTipoIspezioneCodiceInterno().contains("2a")) // in piano di monitoraggio
    		{
    			String  piano="- Piano di monitoraggio: ";
    			Iterator<Integer> kiave=TicketDetails.getLisaElementipianoMonitoraggio_ispezioni().keySet().iterator();
    			String pianoScelto="";
    			while(kiave.hasNext()){
    				
    				pianoScelto=TicketDetails.getLisaElementipianoMonitoraggio_ispezioni().get(kiave.next());
    			}
      			
        		
        		out.print("- Ispezione: <br> <b>Motivo dell'Ispezione</b> "+TicketDetails.getDescriptionTipoIspezione()+".<br>"+piano+pianoScelto);
    			
    		}
    			
    			else{
    				if(TicketDetails.getTipoCampione()==5) // in piano di monitoraggio
            		{
    					out.print("- Ispezione: <br> <b>Motivo dell'Ispezione</b> "+TicketDetails.getDescriptionTipoIspezione()+".<br>");
            		}
    				
    				else{
    					
    					if (TicketDetails.getTipoIspezioneCodiceInterno().contains("16a"))
    					{
    						out.print("- Ispezione: <br> <b> Motivo dell'Ispezione </b> "+TicketDetails.getDescriptionTipoIspezione()+".<br>");
    						
    						if (TicketDetails.getSoggettiCoinvolti()!=null)
    						{
    							out.print("Soggetti Coinvolti : "+TicketDetails.getSoggettiCoinvolti()+"<br>");
    							
    						}
    						if (TicketDetails.getRicoverati()!=null)
    						{
    							out.print("di cui Ricoverati : "+TicketDetails.getRicoverati()+"<br>");
    							
    						}
    						if (TicketDetails.getAlimentiSospetti()!=null)
    						{
    							out.print("Alimenti Sospetti : "+TicketDetails.getAlimentiSospetti()+"<br>");
    							
    						}
    						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    						if (TicketDetails.getDataSintomi()!=null)
    						{
    							out.print("Data Sintomi : "+sdf.format(new java.sql.Date(TicketDetails.getDataSintomi().getTime())));%>
    							  
    							<br>
    							<%
    							
    						}
    						if (TicketDetails.getDataPasto()!=null)
    						{
    							out.print("Data Pasto : "+sdf.format(new java.sql.Date(TicketDetails.getDataPasto().getTime())));%>
    							 
    							<%
    						}
    						
    						
    					}
    					else
    					{
    					
            			out.print("- Ispezione: <br> <b> Motivo dell'Ispezione </b> "+TicketDetails.getDescriptionTipoIspezione());
            			if(TicketDetails.getTipoIspezioneCodiceInterno().contains("4a") && TicketDetails.getIspezioneAltro() != null){
            				out.print(": " + TicketDetails.getIspezioneAltro());
						}
            			out.print(".<br/>");
            			if(TicketDetails.getTipoIspezioneCodiceInterno().contains("5a") || 
            					TicketDetails.getTipoIspezioneCodiceInterno().contains("7a") ||
            					TicketDetails.getTipoIspezioneCodiceInterno().contains("8a") ){
            				
            				out.print("<br>- Contributi in Euro (nei casi in cui è previsto dal D.Lgs 194/2008): "+TicketDetails.getContributi());
            				
            			}
    					}
    				
    				
    			}
    			
    			
    		}
    		
    		
    }
    	
    }
    	
    %>
 
    	<input type="hidden" name="tipoCampione" value="<%=TicketDetails.getTipoCampione() %>">
					
	</td>		
    
    
	<%if(TicketDetails.getTipoIspezioneCodiceInterno().contains("7a")){ %>
	
	<tr class="containerBody">
    	<td nowrap class="formLabel">
      		Codice Allerta
    	</td>
    	
    	<td>
    	<%=TicketDetails.getCodiceAllerta() %>
    	</td>
    	</tr>
	
	<%} %>		
	
	<%if(TicketDetails.getTipoIspezioneCodiceInterno().contains("8a")){ %>
	
	<tr class="containerBody">
    	<td nowrap class="formLabel">
      		Le azioni correttive risultano adeguate ed efficaci ?"
    	</td>
    	
    	<td>
    	<table class="noborder">
			<tr><td>SI <input type = "radio" name = "azione" disabled="disabled" <%if (TicketDetails.isAzione()){ %>checked="checked"<%} %> > </td><td>NO <input type = "radio" <%if (!TicketDetails.isAzione()){ %>checked="checked"<%} %> disabled="disabled"></td> </tr>
			
			<%if (TicketDetails.isAzione()) { %>
			<tr id = "desc1" ><td>Descrizione : </td><td><%=TicketDetails.getAzione_descrizione() %></td><td>&nbsp;</td></tr>
			
			<%} %>
			</table>
    	</td>
    	</tr>
	
	<%} %>		
	
	
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="campioni.data_richiesta">Data Inizio Controllo</dhv:label>
    </td>
    <td>
      <zeroio:tz
				timestamp="<%= TicketDetails.getAssignedDate() %>" dateOnly="true"
				timeZone="<%= TicketDetails.getAssignedDateTimeZone() %>"
				showTimeZone="false" default="&nbsp;" /> 
     
    </td>
  </tr>


  

  
  <%if(TicketDetails.getDataFineControllo()!=null){ %>

  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Data Fine Controllo</dhv:label>
    </td>
    <td>
      <%= (new SimpleDateFormat( "dd/MM/yyyy" )).format(TicketDetails.getDataFineControllo().getTime()) %>     
    </td>
  </tr><%} %>
  
  <%if(TicketDetails.getTipoCampione()==4){ %>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzioni.note">Oggetto del Controllo</dhv:label>
    </td>
    <td>
     
     <table class = "noborder">
     <tr>
     <td>
   <%  String  ispezioni="<b></b> <br>";
    				Iterator<Integer> kiave=TicketDetails.getLisaElementi_Ispezioni().keySet().iterator();
    				String ispezioneSel="";
    				while(kiave.hasNext()){
    					
    					int key = kiave.next();
    					
    					out.print("<b><font color='blue'> "+IspezioneMacrocategorie.getValueFromId(key)+"</font></b><br> ") ;
    					
    					HashMap<Integer,String> lista = TicketDetails.getLisaElementi_Ispezioni().get(key);
    					
    					Iterator<Integer> kiave1= lista.keySet().iterator();
    					
    					while(kiave1.hasNext()){
    						
    						out.println(lista.get(kiave1.next())+"<br>");
    					}
    					
    					
    	    				
    	    		}%>
     
     </td>
     <td>
     &nbsp;
     </td>
     <td>
     <%
     if (!"".equals(TicketDetails.getIspezioni_desc1()) && TicketDetails.getIspezioni_desc1()!=null)
		{
			out.print("<br><b>Note Settore Alimenti per il consumo Umano : </b><br>"+TicketDetails.getIspezioni_desc1());
			
		}
		if (!"".equals(TicketDetails.getIspezioni_desc2()) && TicketDetails.getIspezioni_desc2()!=null)
		{
			out.print("<br><b>Note Settore alimenti Zootecnici : </b><br>"+TicketDetails.getIspezioni_desc2());
			
		}
		if (!"".equals(TicketDetails.getIspezioni_desc3()) && TicketDetails.getIspezioni_desc3()!=null)
		{
			out.print("<br><b>Note Settore Benessere Animale non durante il trasporto : </b><br> "+TicketDetails.getIspezioni_desc3());
			
		}
		if (!"".equals(TicketDetails.getIspezioni_desc4()) && TicketDetails.getIspezioni_desc4()!=null)
		{
			out.print("<br><b>Note Settore Sanita animale : </b><br>"+TicketDetails.getIspezioni_desc4());
			
		}
		if (!"".equals(TicketDetails.getIspezioni_desc5()) && TicketDetails.getIspezioni_desc5()!=null)
		{
			out.print("<br><b>Note Settore S.O.A. negli Impianti di trasformazione : </b><br>"+TicketDetails.getIspezioni_desc5());
			
		}
		if (!"".equals(TicketDetails.getIspezioni_desc6()) && TicketDetails.getIspezioni_desc6()!=null)
		{
			out.print("<br><b>Note Settore Rifiuti S.O.A. nelle altre imprese : </b><br>"+TicketDetails.getIspezioni_desc6());
			
		}
		if (!"".equals(TicketDetails.getIspezioni_desc7()) && TicketDetails.getIspezioni_desc7()!=null)
		{
			out.print("<br><b>Note Altro : </b><br>"+TicketDetails.getIspezioni_desc7());
			
		}
		if (!"".equals(TicketDetails.getIspezioni_desc8()) && TicketDetails.getIspezioni_desc8()!=null)
		{
			out.print("<br><b>Note Settore Benessere Animale durante il trasporto : </b><br>"+TicketDetails.getIspezioni_desc8());
			
		}
		%>
     
     </td>
     </tr>
     
     
     </table>
     
     
    </td>
	</tr>
	<%} %>
  <dhv:evaluate if="<%= hasText(TicketDetails.getProblem()) %>">
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzioni.note">Note</dhv:label>
    </td>
    <td>
      <%= toString(TicketDetails.getProblem()) %>
    </td>
	</tr>
</dhv:evaluate>
  
  
  <!-- aggiunto da d.dauria -->
  <% if (((TicketDetails.getNucleoIspettivo() > -1) && (TicketDetails.getComponenteNucleo() != "")   ) || ((TicketDetails.getNucleoIspettivoDue() > -1) && (TicketDetails.getComponenteNucleoDue() != "")) || ((TicketDetails.getNucleoIspettivoTre() > -1) && (TicketDetails.getComponenteNucleoTre() != "")) || ((TicketDetails.getNucleoIspettivoQuattro() > -1) && (TicketDetails.getComponenteNucleoQuattro() != "")) || ((TicketDetails.getNucleoIspettivoCinque() > -1) && (TicketDetails.getComponenteNucleoCinque() != "")) || ((TicketDetails.getNucleoIspettivoSei() > -1) && (TicketDetails.getComponenteNucleoSei() != "")) || ((TicketDetails.getNucleoIspettivoSette() > -1) && (TicketDetails.getComponenteNucleoSette() != "")) || ((TicketDetails.getNucleoIspettivoOtto() > -1) && (TicketDetails.getComponenteNucleoOtto() != "")) || ((TicketDetails.getNucleoIspettivoNove() > -1) && (TicketDetails.getComponenteNucleoNove() != "")) || ((TicketDetails.getNucleoIspettivoDieci() > -1) && (TicketDetails.getComponenteNucleoDieci() != "")) ){%>
    <tr class="containerBody" >
   <td name="" class="formLabel">
     <dhv:label name="">Nucleo Ispettivo</dhv:label>
   </td>
   <td>
   <% if((TicketDetails.getNucleoIspettivo() > -1) && (TicketDetails.getComponenteNucleo() != "")) {%>
    <b> <%=TitoloNucleo.getSelectedValue(TicketDetails.getNucleoIspettivo())%>:</b>
    <%=TicketDetails.getComponenteNucleo() %>
   <% } %>
   <% if(TicketDetails.getNucleoIspettivoDue() > -1) {%>
 	  <b><%=TitoloNucleoDue.getSelectedValue(TicketDetails.getNucleoIspettivoDue())%>:</b>
 	 <%=TicketDetails.getComponenteNucleoDue() %>
   <% } %> 
   <% if(TicketDetails.getNucleoIspettivoTre() > -1) {%>
 	 <b><%=TitoloNucleoTre.getSelectedValue(TicketDetails.getNucleoIspettivoTre())%>:</b>
 	 <%= TicketDetails.getComponenteNucleoTre() %>
   <% } %> 
   <% if(TicketDetails.getNucleoIspettivoQuattro() > -1) {%>
 	 <b><%=TitoloNucleoQuattro.getSelectedValue(TicketDetails.getNucleoIspettivoQuattro())%>:</b>
 	 <%= TicketDetails.getComponenteNucleoQuattro() %>
   <% } %>   					 
   <% if(TicketDetails.getNucleoIspettivoCinque() > -1) {%>
 	  <b><%=TitoloNucleoCinque.getSelectedValue(TicketDetails.getNucleoIspettivoCinque())%>:</b>
 	 <%= TicketDetails.getComponenteNucleoCinque() %>
   <% } %>  
      <% if(TicketDetails.getNucleoIspettivoSei() > -1) {%>
 	  <b><%=TitoloNucleoSei.getSelectedValue(TicketDetails.getNucleoIspettivoSei())%>:</b>
 	 <%= TicketDetails.getComponenteNucleoSei() %>
   <% } %>  
      <% if(TicketDetails.getNucleoIspettivoSette() > -1) {%>
 	  <b><%=TitoloNucleoSette.getSelectedValue(TicketDetails.getNucleoIspettivoSette())%>:</b>
 	 <%= TicketDetails.getComponenteNucleoSette() %>
   <% } %>  
      <% if(TicketDetails.getNucleoIspettivoOtto() > -1) {%>
 	  <b><%=TitoloNucleoOtto.getSelectedValue(TicketDetails.getNucleoIspettivoOtto())%>:</b>
 	 <%= TicketDetails.getComponenteNucleoOtto() %>
   <% } %>  
      <% if(TicketDetails.getNucleoIspettivoNove() > -1) {%>
 	  <b><%=TitoloNucleoNove.getSelectedValue(TicketDetails.getNucleoIspettivoNove())%>:</b>
 	 <%= TicketDetails.getComponenteNucleoNove() %>
   <% } %>  
      <% if(TicketDetails.getNucleoIspettivoDieci() > -1) {%>
 	  <b><%=TitoloNucleoDieci.getSelectedValue(TicketDetails.getNucleoIspettivoDieci())%>:</b>
 	 <%= TicketDetails.getComponenteNucleoDieci() %>
   <% } %>  
  </td>
  </tr>
<%} %> 
  
  
  
  

 


					
    				
    	    		
    	    		


  