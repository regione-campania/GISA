<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="lookupTipologiaAlimento" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookupSpecieAlimento" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookupmolluschi" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AnimaliNonAlimentari" class="org.aspcfs.utils.web.LookupList" scope="request"/>



<dhv:evaluate if="<%= TicketDetails.getAlimentiOrigineAnimale() == true %>">
 <%--  <dhv:evaluate if="<%= TicketDetails.getAlimentiOrigineAnimaleNonTrasformati() > -1  %>"> --%> 
    <% if (TicketDetails.getAlimentiOrigineAnimaleNonTrasformati() > -1){  %>   
   <tr class="containerBody">
      <td name="" id="" nowrap class="formLabel">
        <dhv:label name="">Alimenti di origine animale</dhv:label>
      </td>
    <td colspan="">
     Tipo : Non Trasformati . <br> <%="Tipo di Alimento :"+AlimentiNonTrasformati.getSelectedValue(TicketDetails.getAlimentiOrigineAnimaleNonTrasformati())%>
    
      <%
      specie_alimenti = "Alimenti di Origine Animale Non Trasformati";
      	tipoAlimenti += AlimentiNonTrasformati.getSelectedValue(TicketDetails.getAlimentiOrigineAnimaleNonTrasformati());
      if(TicketDetails.getAlimentiOrigineAnimaleNonTrasformatiValori()>-1 && TicketDetails.getAlimentiOrigineAnimaleNonTrasformati()!=6 ){
    	  
    	out.print("<br> Specie : "+AlimentiNonTrasformatiValori.getSelectedValue(TicketDetails.getAlimentiOrigineAnimaleNonTrasformatiValori())); 
    	tipoAlimenti += " Specie : " +AlimentiNonTrasformatiValori.getSelectedValue(TicketDetails.getAlimentiOrigineAnimaleNonTrasformatiValori()+"\n");
      }
      else
      {
    	  if(TicketDetails.getAlimentiOrigineAnimaleNonTrasformatiValori()>-1 && TicketDetails.getAlimentiOrigineAnimaleNonTrasformati()==6 ){
    	  	out.print("<br> Tipo Mollusco : "+lookupmolluschi.getSelectedValue(TicketDetails.getAlimentiOrigineAnimaleNonTrasformatiValori())); 
      		tipoAlimenti += " Tipo Mollusco : " +lookupmolluschi.getSelectedValue(TicketDetails.getAlimentiOrigineAnimaleNonTrasformatiValori()+"\n");
      		}
      }
      
      
      if(TicketDetails.getAlimentiOrigineAnimaleNonTrasformati()==8){
			out.print("<br> Latte Provenitente da : "+TicketDetails.getTipoSpecieLatte_descrizione());
			tipoAlimenti += "Latte Provenitente da : "+TicketDetails.getTipoSpecieLatte_descrizione();
		}
		else{

			if(TicketDetails.getAlimentiOrigineAnimaleNonTrasformati()==9){
				out.print("<br> Uova Provenitenti da : "+TicketDetails.getTipoSpecieUova_descrizione());
				tipoAlimenti += "Uova Provenitenti da : "+TicketDetails.getTipoSpecieUova_descrizione();
			}
			
			
			out.println("<br> Note : "+TicketDetails.getNoteAlimenti());
			
		}
      
      
      
      %>
      
      
        
    </td> 
    
    
  </tr>
   <% } %> 
    </dhv:evaluate> 

<dhv:evaluate if="<%= TicketDetails.getAlimentiOrigineAnimaleTrasformati() > -1  %>">
    <tr class="containerBody" >
   <td name="" class="formLabel">
     <dhv:label name="">Alimenti di origine animale</dhv:label>
   </td>
   <td colspan="">
   
   <%
   specie_alimenti = "Alimenti di Origine Animale Trasformati";
   tipoAlimenti += ""+AlimentiTrasformati.getSelectedValue(TicketDetails.getAlimentiOrigineAnimaleTrasformati());
   %>
   
   Trasformati:<%=AlimentiTrasformati.getSelectedValue(TicketDetails.getAlimentiOrigineAnimaleTrasformati())%>		
   
   <%out.println("<br> Note : "+TicketDetails.getNoteAlimenti()); %>			 
  </td>
  </tr>
</dhv:evaluate> 

<dhv:evaluate if="<%= TicketDetails.getAlimentiOrigineVegetale() == true %>">
   <tr class="containerBody">
      <td name="" id="" nowrap class="formLabel">
        <dhv:label name="">Alimenti di origine vegetale</dhv:label>
      </td>
    <td>
       <%="<b>"+ AlimentiVegetali.getSelectedValue(TicketDetails.getAlimentiOrigineVegetaleValori())+ "</b>"%>  
     
     <%
       tipoAlimenti += AlimentiVegetali.getSelectedValue(TicketDetails.getAlimentiOrigineVegetaleValori());
     specie_alimenti = "Alimenti di Origine Vegetale";
     %>
     
      
       <br>
       
       <%
      
       HashMap<Integer,String> alimentiVegetali=TicketDetails.getTipiAlimentiVegetali();
       Iterator<Integer> iter=alimentiVegetali.keySet().iterator();
       while(iter.hasNext()){
    	   int k=iter.next();
    	   out.println(alimentiVegetali.get(k)+"<br>");
    	   
       }
       %>
     
       <%out.println("<br> Note : "+TicketDetails.getNoteAlimenti()); %>
    </td>
  </tr>
</dhv:evaluate> 

<dhv:evaluate if="<%= TicketDetails.isAltriAlimenti() == true %>">
   <tr class="containerBody">
      <td name="" id="" nowrap class="formLabel">
        <dhv:label name="">Altri Alimenti origine non Animale</dhv:label>
      </td>
    <td>
   
    
      <%=TicketDetails.getNoteAlimenti()+"<br><br>" +"Tipo:" %>
      <%=AltriAlimenti.getSelectedValue(TicketDetails.getAltrialimenti())%>   
     
     <%  tipoAlimenti += AltriAlimenti.getSelectedValue(TicketDetails.getAltrialimenti());
     specie_alimenti = "Alimenti di Origine non Animale";
    
     %>
    </td>
  </tr>
</dhv:evaluate> 

<dhv:evaluate if="<%= TicketDetails.getAlimentiComposti() == true %>">
   <tr class="containerBody">
      <td name="" id="" nowrap class="formLabel">
        <dhv:label name="">Alimenti composti</dhv:label>
      </td>
    <td colspan="">
      <%=TicketDetails.getTestoAlimentoComposto() %>  
      <% tipoAlimenti += TicketDetails.getTestoAlimentoComposto() ;
      specie_alimenti = "Alimenti Composti";
      %>
    
    </td>
  </tr>
</dhv:evaluate> 

<dhv:evaluate if="<%= TicketDetails.isAlimentiAcqua() == true %>">
   <tr class="containerBody">
      <td name="" id="" nowrap class="formLabel">
        <dhv:label name="">Acqua</dhv:label>
      </td>
    <td colspan="">
    <%out.print("Tipo : "+TicketDetails.getDescrizionetipoAcqua()+"<br>"); %>
      <%=TicketDetails.getNoteAlimenti()%>   
      <% tipoAlimenti += TicketDetails.getNoteAlimenti(); 
      specie_alimenti = "Acqua";
      %>
      
    </td>
  </tr>
</dhv:evaluate>

<dhv:evaluate if="<%= TicketDetails.isCheckMatriciCanili() == true %>">
   <tr class="containerBody">
      <td name="" id="" nowrap class="formLabel">
        <dhv:label name="">Cane</dhv:label>
      </td>
    <td colspan="">
    <%out.print("Tipo : "+TicketDetails.getDescrizioneTipoMatriciCanili()+"<br>"); %>
      <%=TicketDetails.getNoteAlimenti()+"<br>" %>   
      <%="Microchip: "+TicketDetails.getMicrochip() %>
      <% tipoAlimenti += TicketDetails.getNoteAlimenti(); 
      specie_alimenti = "Matrici Canili";
      %>
      <input type = "hidden" name = "microchipMatriciCanili" value = "<%=TicketDetails.getMicrochip() %>">
      <%--if(TicketDetails.getMicrochip() != null && !TicketDetails.getMicrochip().equals("")){ %>
      <br/>Microchip: <%= TicketDetails.getMicrochip() %>
      <%} --%>
      
    </td>
  </tr>
</dhv:evaluate>
 
<dhv:evaluate if="<%= TicketDetails.isMangimi() == true %>">
   <tr class="containerBody">
      <td name="" id="" nowrap class="formLabel">
        <dhv:label name="">Alimenti per uso Zootecnico</dhv:label>
      </td>
    <td>
    <table>
    <tr>
    <td>
    <b>Specie </b>:<%=lookupSpecieAlimento.getSelectedValue(TicketDetails.getSpecieAlimentoZootecnico()) %>
    
    </td></tr>
    <tr>
    <td><b>Tipologia </b>: <%=lookupTipologiaAlimento.getSelectedValue(TicketDetails.getTipologiaAlimentoZootecnico()) %>
    <%  tipoAlimenti += lookupTipologiaAlimento.getSelectedValue(TicketDetails.getTipologiaAlimentoZootecnico()) ; 
    	specie_alimenti = "Alimenti per uso Zootecnico";
    %>
    </td>
    
    </tr>
    <tr>
    <td colspan="">
     <b>Note :</b> <%=TicketDetails.getNoteAlimenti() %>  
    </td></tr>
 </table></td></tr>
</dhv:evaluate> 


<dhv:evaluate if="<%= TicketDetails.isAlimentiBevande() == true %>">
   <tr class="containerBody">
      <td name="" id="" nowrap class="formLabel">
        <dhv:label name="">Bevande</dhv:label>
      </td>
    <td colspan="">
      <%=TicketDetails.getNoteAlimenti() %>  
      <%  tipoAlimenti += TicketDetails.getNoteAlimenti();
      specie_alimenti = "Bevande";
      %>
    </td>
  </tr>
</dhv:evaluate> 


<dhv:evaluate if="<%= TicketDetails.isAlimentiAdditivi() == true %>">
   <tr class="containerBody">
      <td name="" id="" nowrap class="formLabel">
        <dhv:label name="">Additivi</dhv:label>
      </td>
    <td colspan="">
      <%=TicketDetails.getNoteAlimenti() %>  
      <%  tipoAlimenti += TicketDetails.getNoteAlimenti();
      specie_alimenti = "Additivi";
      %>
    </td>
  </tr>
</dhv:evaluate> 

<dhv:evaluate if="<%= TicketDetails.isMaterialiAlimenti()== true %>">
   <tr class="containerBody">
      <td name="" id="" nowrap class="formLabel">
        <dhv:label name="">Materiali a contatto con Alimenti</dhv:label>
      </td>
    <td colspan="">
      <%=TicketDetails.getNoteAlimenti()%> 
      <%  tipoAlimenti += TicketDetails.getNoteAlimenti();
      specie_alimenti = "Materiali a contatto con Alimenti";
      %> 
    </td>
  </tr>
</dhv:evaluate> 
<dhv:evaluate if="<%= TicketDetails.isAnimaliNonAlimentari()== true %>">
   <tr class="containerBody">
      <td name="" id="" nowrap class="formLabel">
        <dhv:label name="">Matrice Animali non Alimn</dhv:label>
      </td>
    <td colspan="">
      <input type = "hidden" name = "animalinonalimentarivalue" value = "<%=TicketDetails.getAnimaliNonAlimentariCombo()%>">
      <%  tipoAlimenti += AnimaliNonAlimentari.getSelectedValue(TicketDetails.getAnimaliNonAlimentariCombo());
     
      %>
      <%=tipoAlimenti %> 
    </td>
  </tr>
</dhv:evaluate> 


