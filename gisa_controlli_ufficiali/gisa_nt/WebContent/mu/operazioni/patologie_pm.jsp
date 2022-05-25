<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
  <table> <tr 
    	id="lcso" 
    	style="display:none;" >
    	<td>
    		<table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
    		<tr>
              <th colspan="2"><strong>Organi</strong></th>
            </tr>
<!--            <tr class="containerBody">-->
<!--              <td class="formLabel" >Data</td>-->
<!---->
<!--              <td><zeroio:dateSelect field="lcso_data" form="main" showTimeZone="false" /> </td>-->
<!--            </tr>-->
     
     <%
     	int numero_organi = 0;
     	int indice_globale= 0;
     	ArrayList<Integer> listaIdOrgani = new ArrayList<Integer>();
     	ArrayList<Organi> listaOrgani = null;
     	for(int key : (Set<Integer>)OrganiListNew.keySet()){
     		listaIdOrgani.add(key);
     	}
     	for( int index = 1; index == 1 || index <= listaIdOrgani.size(); index++ )
     	{
     		//out.println("Indice di Organi = "+ index);
     		//out.println("Numero di Organi = "+ OrganiList.size());
     		indice_globale=index;
     		if(listaIdOrgani.size() > 0){
     			listaOrgani = (ArrayList<Organi>)OrganiListNew.get( listaIdOrgani.get(index-1) );
     		}
     		Organi organo = (index <= listaIdOrgani.size()) ? (Organi)listaOrgani.get(0) : (new Organi());
     		PatologieOrgani.setJsEvent("onChange=\"javascript:displayStadio2(" + index + ");\"");
     %>
     
    
		    <tr style="background-color: #EDEDED" class="containerBody">
               <td colspan="1">&nbsp;<input type="hidden" name="lcso_id_<%=index %>" value="<%=organo.getId() %>" /> </td>
            </tr>
            
     		<tr id="organo" class="containerBody">
              <td class="formLabel" >Organo</td>
              <td>
              		<%	String id_organo = "document.getElementById('lcso_organo_"+index+"').value" ; 
              		
              			Organi.setJsEvent("onChange=\"javascript:vpm_resetta_lookup_patologia_organo("+ index +"," + id_organo +", -1);\"");
              			Organi.setIdName("lcso_organo_" + index);
              			
              		%>
              		<%=	Organi.getHtmlSelect("lcso_organo_" + index, organo.getLcso_organo())%>
              </td>
            </tr>

            <tr id="patologie" class="containerBody">
              <td class="formLabel" >Lesione Anatomopatologica</td>
              <td>
              <%
	      			lookup_lesione_milza.setSelectStyle("display:none");
              		lookup_lesione_milza.setMultiple(true);
              		lookup_lesione_milza.setSelectSize(5);
              		lookup_lesione_milza.setJsEvent("onChange=\"javascript:mostraLcsoPatologiaAltro(this," + index + ");\"");
              		
	       			lookup_lesione_cuore.setSelectStyle("display:none");
	       			lookup_lesione_cuore.setMultiple(true);
	       			lookup_lesione_cuore.setSelectSize(5);
	       			lookup_lesione_cuore.setJsEvent("onChange=\"javascript:mostraLcsoPatologiaAltro(this," + index + ");\"");
	       			
	        		lookup_lesione_polmoni.setSelectStyle("display:none");
	        		lookup_lesione_polmoni.setMultiple(true);
	        		lookup_lesione_polmoni.setSelectSize(5);
	        		lookup_lesione_polmoni.setJsEvent("onChange=\"javascript:mostraLcsoPatologiaAltro(this," + index + ");\"");
	        		
	        		lookup_lesione_fegato.setSelectStyle("display:none");
	        		lookup_lesione_fegato.setMultiple(true);
	        		lookup_lesione_fegato.setSelectSize(5);
	        		lookup_lesione_fegato.setJsEvent("onChange=\"javascript:mostraLcsoPatologiaAltro(this," + index + ");\"");
	        		
	        		lookup_lesione_rene.setSelectStyle("display:none");
	        		lookup_lesione_rene.setMultiple(true);
	        		lookup_lesione_rene.setSelectSize(5);
	        		lookup_lesione_rene.setJsEvent("onChange=\"javascript:mostraLcsoPatologiaAltro(this," + index + ");\"");
	        		
	        		lookup_lesione_mammella.setSelectStyle("display:none");
	        		lookup_lesione_mammella.setMultiple(true);
	        		lookup_lesione_mammella.setSelectSize(5);
	        		lookup_lesione_mammella.setJsEvent("onChange=\"javascript:mostraLcsoPatologiaAltro(this," + index + ");\"");
	        		
	        		lookup_lesione_apparato_genitale.setSelectStyle("display:none");
	        		lookup_lesione_apparato_genitale.setMultiple(true);
	        		lookup_lesione_apparato_genitale.setSelectSize(5);
	        		lookup_lesione_apparato_genitale.setJsEvent("onChange=\"javascript:mostraLcsoPatologiaAltro(this," + index + ");\"");
	        		
	        		lookup_lesione_stomaco.setSelectStyle("display:none");
	        		lookup_lesione_stomaco.setMultiple(true);
	        		lookup_lesione_stomaco.setSelectSize(5);
	        		lookup_lesione_stomaco.setJsEvent("onChange=\"javascript:mostraLcsoPatologiaAltro(this," + index + ");\"");
	        		
	        		lookup_lesione_intestino.setSelectStyle("display:none");
	        		lookup_lesione_intestino.setMultiple(true);
	        		lookup_lesione_intestino.setSelectSize(5);
	        		lookup_lesione_intestino.setJsEvent("onChange=\"javascript:mostraLcsoPatologiaAltro(this," + index + ");\"");
	        		
	        		lookup_lesione_osteomuscolari.setSelectStyle("display:none");
	        		lookup_lesione_osteomuscolari.setMultiple(true);
	        		lookup_lesione_osteomuscolari.setSelectSize(5);
	        		lookup_lesione_osteomuscolari.setJsEvent("onChange=\"javascript:mostraLcsoPatologiaAltro(this," + index + ");\"");
	        		
	        		lookup_lesione_generici.setSelectStyle("display:none");
	        		lookup_lesione_generici.setMultiple(true);
	        		lookup_lesione_generici.setSelectSize(5);
	        		lookup_lesione_generici.setJsEvent("onChange=\"javascript:mostraLcsoPatologiaAltro(this," + index + ");\"");
	        		
	        		lookup_lesione_altro.setSelectStyle("display:none");
	        		lookup_lesione_altro.setMultiple(true);
	        		lookup_lesione_altro.setSelectSize(5);
	        		lookup_lesione_altro.setJsEvent("onChange=\"javascript:mostraLcsoPatologiaAltro(this," + index + ");\"");
	        		
	        		
	        		//lookup_lesione_milza.setJsEvent("onChange=\"javascript:displayStadio2(" + index + ");\"");
	        		//lookup_lesione_cuore.setJsEvent("onChange=\"javascript:displayStadio2(" + index + ");\"");
	        		//lookup_lesione_polmoni.setJsEvent("onChange=\"javascript:displayStadio2(" + index + ");\"");
	        		//lookup_lesione_fegato.setJsEvent("onChange=\"javascript:displayStadio2(" + index + ");\"");
	        		//lookup_lesione_rene.setJsEvent("onChange=\"javascript:displayStadio2(" + index + ");\"");
	        		//lookup_lesione_mammella.setJsEvent("onChange=\"javascript:displayStadio2(" + index + ");\"");
	        		//lookup_lesione_apparato_genitale.setJsEvent("onChange=\"javascript:displayStadio2(" + index + ");\"");
	        		//lookup_lesione_stomaco.setJsEvent("onChange=\"javascript:displayStadio2(" + index + ");\"");
	        		//lookup_lesione_intestino.setJsEvent("onChange=\"javascript:displayStadio2(" + index + ");\"");
	        		//lookup_lesione_osteomuscolari.setJsEvent("onChange=\"javascript:displayStadio2(" + index + ");\"");
	        		
	        		
	        		//out.println("Valore ID lesione : "  + organo.getLcso_patologia() ) ;
	        		LookupList multipleSelect = new LookupList();
	        		if(listaIdOrgani.size() > 0){
		        		for ( Organi o : listaOrgani ){
		        			multipleSelect.addItem(o.getLcso_patologia(), "" );
		        		}
	        		}
	        		LookupList multipleSelectDefault = new LookupList();
	        		multipleSelectDefault.addItem(-1,"");
				               
              %>
                  <%=lookup_lesione_milza.getHtmlSelect("lesione_milza_" + index, organo.getLcso_organo() == 1 ? multipleSelect : multipleSelectDefault )%>
                  <%=lookup_lesione_cuore.getHtmlSelect("lesione_cuore_" + index, organo.getLcso_organo() == 2 ? multipleSelect : multipleSelectDefault)%>
	              <%=lookup_lesione_polmoni.getHtmlSelect("lesione_polmoni_" + index, organo.getLcso_organo() == 3 ? multipleSelect : multipleSelectDefault)%>
	              <%=lookup_lesione_fegato.getHtmlSelect("lesione_fegato_" + index, organo.getLcso_organo() == 4 ? multipleSelect : multipleSelectDefault)%>
	              <%=lookup_lesione_rene.getHtmlSelect("lesione_rene_" + index, organo.getLcso_organo() == 5 ? multipleSelect : multipleSelectDefault)%>
	              <%=lookup_lesione_mammella.getHtmlSelect("lesione_mammella_" + index, organo.getLcso_organo() == 6 ? multipleSelect : multipleSelectDefault)%>
	              <%=lookup_lesione_apparato_genitale.getHtmlSelect("lesione_apparato_genitale_" + index, organo.getLcso_organo() == 7 ? multipleSelect : multipleSelectDefault)%>
	              <%=lookup_lesione_stomaco.getHtmlSelect("lesione_stomaco_" + index, organo.getLcso_organo() == 8 ? multipleSelect : multipleSelectDefault)%>
	              <%=lookup_lesione_intestino.getHtmlSelect("lesione_intestino_" + index, organo.getLcso_organo() == 9 ? multipleSelect : multipleSelectDefault)%>
	              <%=lookup_lesione_osteomuscolari.getHtmlSelect("lesione_osteomuscolari_" + index, organo.getLcso_organo() == 10 ? multipleSelect : multipleSelectDefault)%>
	              <%=lookup_lesione_visceri.getHtmlSelect("lesione_visceri_" + index, organo.getLcso_organo() == 10 ? multipleSelect : multipleSelectDefault)%>
	              
	              
	              <%=lookup_lesione_generici.getHtmlSelect("lesione_generici_" + index, organo.getLcso_organo() >= 13 ? multipleSelect : multipleSelectDefault)%>
	              <%=lookup_lesione_altro.getHtmlSelect("lesione_altro_" + index, organo.getLcso_organo() == 11 || organo.getLcso_organo() == 12 ? multipleSelect : multipleSelectDefault)%>
	              <input style="display: <%= organo.getLcso_patologia() == 5 ? "" : "none" %>;" type="text" id="lcso_patologiaaltro_<%=index %>" name="lcso_patologiaaltro_<%=index %>" 
	              value="<%= organo.getLcso_patologia_altro() != null ? organo.getLcso_patologia_altro() : "" %>" />
	              
              </td>
            </tr>
           
           <script>
	    		vpm_seleziona_lookup_patologia_organo(<%=index%>,<%=organo.getLcso_organo()%>,<%=organo.getLcso_patologia()%>);
	   	   </script> 	
<!-- **************************************************** INSERIMENTO DI ALBERTO ***********************************************     -->
<%-- 	
	
            <tr id="patologie" class="containerBody">
              <td class="formLabel" >Lesione Anatomopatologica</td>
              <td>
	              <%=PatologieOrgani.getHtmlSelect("lcso_patologia_" + index, organo.getLcso_patologia())%>
	          </td>
            </tr>	

			<tr id="lesione_milza" class="containerBody">
              <td class="formLabel" >Lesione Anatomopatologica (milza)</td>
              <td>
	              <%=lookup_lesione_milza.getHtmlSelect("lesione_milza_" + index, organo.getLcso_patologia())%>
	          </td>
            </tr>


          
			<tr id="lesione_cuore" class="containerBody">
              <td class="formLabel" >Lesione Anatomopatologica (cuore)</td>
              <td>
	              <%=lookup_lesione_cuore.getHtmlSelect("lesione_cuore_" + index, organo.getLcso_patologia())%>
	          </td>
            </tr>
            			
            <tr id="lesione_polmoni" class="containerBody">
              <td class="formLabel" >Lesione Anatomopatologica (polmoni)</td>
              <td>
	              <%=lookup_lesione_polmoni.getHtmlSelect("lesione_polmoni_" + index, organo.getLcso_patologia())%>
	          </td>
            </tr>
            
			<tr id="lesione_fegato" class="containerBody">
              <td class="formLabel" >Lesione Anatomopatologica (fegato)</td>
              <td>
	              <%=lookup_lesione_fegato.getHtmlSelect("lesione_fegato_" + index, organo.getLcso_patologia())%>
	          </td>
            </tr>

            <tr id="lesione_rene" class="containerBody">
              <td class="formLabel" >Lesione Anatomopatologica (rene)</td>
              <td>
	              <%=lookup_lesione_rene.getHtmlSelect("lesione_rene_" + index, organo.getLcso_patologia())%>
	          </td>
            </tr>
            
			<tr id="lesione_mammella" class="containerBody">
              <td class="formLabel" >Lesione Anatomopatologica (mammella)</td>
              <td>
	              <%=lookup_lesione_mammella.getHtmlSelect("lesione_mammella_" + index, organo.getLcso_patologia())%>
	          </td>
            </tr>
            
            <tr id="lesione_apparato_genitale" class="containerBody">
              <td class="formLabel" >Lesione Anatomopatologica (genitale)</td>
              <td>
	              <%=lookup_lesione_apparato_genitale.getHtmlSelect("lesione_apparato_genitale_" + index, organo.getLcso_patologia())%>
	          </td>
            </tr>
            
			<tr id="lesione_stomaco" class="containerBody">
              <td class="formLabel" >Lesione Anatomopatologica (stomaco)</td>
              <td>
	              <%=lookup_lesione_stomaco.getHtmlSelect("lesione_stomaco_" + index, organo.getLcso_patologia())%>
	          </td>
            </tr>
            
            <tr id="lesione_intestino" class="containerBody">
              <td class="formLabel" >Lesione Anatomopatologica (intestino)</td>
              <td>
	              <%=lookup_lesione_intestino.getHtmlSelect("lesione_intestino_" + index, organo.getLcso_patologia())%>
	          </td>
            </tr>
            
			<tr id="lesione_osteomuscolari" class="containerBody">
              <td class="formLabel" >Lesione Anatomopatologica (osteomuscolari)</td>
              <td>
	              <%=lookup_lesione_osteomuscolari.getHtmlSelect("lesione_osteomuscolari_" + index, organo.getLcso_patologia())%>
	          </td>
            </tr>        
--%>
<!-- *********************************************** FINE INSERIMENTO DI ALBERTO ***********************************************     -->	


 <%-- ELIMINA stadio 	
        <tr 
            	id="stadio_<%=index %>" 
            	class="containerBody" 
            	style="<%=(organo.getLcso_patologia() == 6) ? ("") : ("display:none") %>" >
	          <td class="formLabel" >Stadio</td>
    	      <td>
    	      	<%=Stadi.getHtmlSelect("lcso_stadio_" + index, organo.getLcso_stadio())%>
    	      </td>
            </tr>
            --%>   
         
       <%
       		numero_organi++;
     	}
     	PatologieOrgani.setJsEvent("onChange=\"javascript:displayStadio(--placeholder--);\"");
       %>
            
     	   
            
     			<input type="hidden" id="elementi" name="elementi" value="<%=numero_organi %>">
   				<input type="hidden" id="size" name="size" value="<%=numero_organi %>">
   				
   			<tr id="nbsp" style = "visibility: collapse; background-color: #EDEDED" class="containerBody">
               <td  name="nbsptr1_" colspan="2">&nbsp;<input type="hidden" name="lcso_id_" value="-1" /></td>
            </tr>
   			<tr id="ww" style = "visibility: collapse" class="containerBody">
              <td class="formLabel" colspan="1">Organo</td>
              <td>
               <%--Organi.setJsEvent("onChange=alert(this.value"+ out.print(indice_globale)  + ")");--%>
              <%=Organi.getHtmlSelect( "lcso_organo_", "-1" )%>
              
              </td>
            </tr>    
   			
   			<tr id="row" style = "visibility: collapse" class="containerBody">
              <td class="formLabel" >Lesione Anatomopatologica</td>
              <td>
              <%
					lookup_lesione_milza.setSelectStyle("display:none");
              		lookup_lesione_milza.setMultiple(true);
        			lookup_lesione_milza.setSelectSize(5);
        			
             		lookup_lesione_cuore.setSelectStyle("display:none");
             		lookup_lesione_cuore.setMultiple(true);
             		lookup_lesione_cuore.setSelectSize(5);
        			
              		lookup_lesione_polmoni.setSelectStyle("display:none");
              		lookup_lesione_polmoni.setMultiple(true);
              		lookup_lesione_polmoni.setSelectSize(5);
        			
              		lookup_lesione_fegato.setSelectStyle("display:none");
              		lookup_lesione_fegato.setMultiple(true);
              		lookup_lesione_fegato.setSelectSize(5);
        			
              		lookup_lesione_rene.setSelectStyle("display:none");
              		lookup_lesione_rene.setMultiple(true);
              		lookup_lesione_rene.setSelectSize(5);
        			
              		lookup_lesione_mammella.setSelectStyle("display:none");
              		lookup_lesione_mammella.setMultiple(true);
              		lookup_lesione_mammella.setSelectSize(5);
        			
              		lookup_lesione_apparato_genitale.setSelectStyle("display:none");
              		lookup_lesione_apparato_genitale.setMultiple(true);
              		lookup_lesione_apparato_genitale.setSelectSize(5);
        			
              		lookup_lesione_stomaco.setSelectStyle("display:none");
              		lookup_lesione_stomaco.setMultiple(true);
              		lookup_lesione_stomaco.setSelectSize(5);
        			
              		lookup_lesione_intestino.setSelectStyle("display:none");
              		lookup_lesione_intestino.setMultiple(true);
              		lookup_lesione_intestino.setSelectSize(5);
        			
              		lookup_lesione_osteomuscolari.setSelectStyle("display:none");
              		lookup_lesione_osteomuscolari.setMultiple(true);
              		lookup_lesione_osteomuscolari.setSelectSize(5);

              		lookup_lesione_generici.setSelectStyle("display:none");
              		lookup_lesione_generici.setMultiple(true);
              		lookup_lesione_generici.setSelectSize(5);
        			
              		lookup_lesione_altro.setSelectStyle("display:none");
              		lookup_lesione_altro.setMultiple(true);
              		lookup_lesione_altro.setSelectSize(5);
              		//lookup_lesione_altro.setJsEvent("onchange=\"javascript:mostraLcsoPatologiaAltro();\"");
              		
              		lookup_lesione_visceri.setSelectStyle("display:none");
              		lookup_lesione_visceri.setMultiple(true);
              		lookup_lesione_visceri.setSelectSize(5);
              		
              %>
                  <%=lookup_lesione_milza.getHtmlSelect("lesione_milza_","-1")%>
	              <%=lookup_lesione_cuore.getHtmlSelect("lesione_cuore_" ,"-1")%>
	              <%=lookup_lesione_polmoni.getHtmlSelect("lesione_polmoni_" ,"-1")%>
	              <%=lookup_lesione_fegato.getHtmlSelect("lesione_fegato_","-1")%>
	              <%=lookup_lesione_rene.getHtmlSelect("lesione_rene_","-1")%>
	              <%=lookup_lesione_mammella.getHtmlSelect("lesione_mammella_","-1")%>
	              <%=lookup_lesione_apparato_genitale.getHtmlSelect("lesione_apparato_genitale_","-1")%>
	              <%=lookup_lesione_stomaco.getHtmlSelect("lesione_stomaco_","-1")%>
	              <%=lookup_lesione_intestino.getHtmlSelect("lesione_intestino_","-1")%>
	              <%=lookup_lesione_osteomuscolari.getHtmlSelect("lesione_osteomuscolari_","-1")%>
	              <%=lookup_lesione_visceri.getHtmlSelect("lesione_visceri_","-1")%>
	              
	              <%=lookup_lesione_generici.getHtmlSelect("lesione_generici_","-1")%>
	              <%=lookup_lesione_altro.getHtmlSelect("lesione_altro_","-1")%>
	              <input style="display: none;" type="text" id="lcso_patologiaaltro_" name="lcso_patologiaaltro_" class="lcso_patologia_altro_class" value="" />
	             
	              
	              
              </td>
            </tr>

		<%-- 
			<tr id="tr" style = "visibility: collapse" class="containerBody" >
	          <td class="formLabel" colspan="1">Stadio</td>
    	      <td><%=Stadi.getHtmlSelect( "lcso_stadio_", "-1" )%></td>
            </tr>
        --%>         
                 
       
          	<tr style="display: none;" id="lcsobutton">
	    	  <input type="button" onClick="javascript:aggiungiOrgano();" id="aggiungialtrobutton" value="Aggiungi Altro Organo" >
	      	</tr>
</table>
</td>
</tr>


<tr id="lcpr" style="display: none;" >
    	<td>
    		<table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
    		
            <tr>
              <th colspan="2"><strong>Libero Consumo Previo Risanamento</strong></th>
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" >Data prevista di liberalizzazione</td>
              <td>
              	<zeroio:dateSelect 
              		field="lcpr_data_prevista_liber" 
              		form="main" 
              		showTimeZone="false" 
              		timestamp="<%="" %>" />
              </td>
            </tr>     
             
            <tr class="containerBody">
              <td class="formLabel" >Data effettiva di liberalizzazione</td>
              <td>
              	<zeroio:dateSelect 
              		field="lcpr_data_effettiva_liber" 
              		form="main" 
              		showTimeZone="false" 
              		timestamp="<%="" %>" />
              </td>
            </tr>
   </table>
   </td>
</tr> </table>
