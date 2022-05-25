<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<script language="JavaScript" type="text/javascript" src="js/sinantropi/detenzioni/edit.js"></script>



<form action="sinantropi.detenzioni.Edit.us" name="form" method="post" onsubmit="javascript:return checkform(this);">

	<input type="hidden" name="idSinantropo" value="${s.id}"/>
	<input type="hidden" name="idCattura" 	 value="${c.id}"/>
	<input type="hidden" name="idDetenzione" value="${d.id}"/>

	<h4 class="titolopagina">
	   	Modifica della Detenzione
	</h4>
	<jsp:include page="/jsp/sinantropi/menuCatture.jsp"/>
	<script type="text/javascript">
			ddtabmenu.definemenu("ddtabs2",2);	
	</script> 
		
	<table class="tabella">
		        
        
        <tr>
        	<th colspan="3">
        		Informazioni su Detenzione
        	</th>
        </tr>
        
        
        
        <tr class="even">
    		<td>
    			 Data di assegnazione in detenzione<font color="red"> *</font>
    		</td>
    		<td>  
    			 <fmt:formatDate type="date" value="${d.dataDetenzioneDa}" pattern="dd/MM/yyyy" var="dataDetenzioneDa"/>		
    			 <input type="text" id="dataDetenzioneDa" name="dataDetenzioneDa" maxlength="32" size="50" readonly="readonly" style="width:246px;" value="${dataDetenzioneDa}"/>
    			 <img src="images/b_calendar.gif" alt="calendario" id="id_img_4" />
 					<script type="text/javascript">
      					 Calendar.setup({
        					inputField     :    "dataDetenzioneDa",     // id of the input field
        					ifFormat       :    "%d/%m/%Y",      // format of the input field
       						button         :    "id_img_4",  // trigger for the calendar (button ID)
       						// align          :    "Tl",           // alignment (defaults to "Bl")
        					singleClick    :    true,
        					timeFormat		:   "24",
        					showsTime		:   false
   							 });					    
  					 </script>   
    		</td>
        </tr>
        
        <tr class="odd">
    		<td>
    			 Data di fine detenzione
    		</td>
    		<td>    		
    			 <fmt:formatDate type="date" value="${d.dataDetenzioneA}" pattern="dd/MM/yyyy" var="dataDetenzioneA"/>		
    			 <input type="text" id="dataDetenzioneA" name="dataDetenzioneA" maxlength="32" size="50" readonly="readonly" style="width:246px;" value="${dataDetenzioneA}"/>
    			 <img src="images/b_calendar.gif" alt="calendario" id="id_img_6" />
 					<script type="text/javascript">
      					 Calendar.setup({
        					inputField     :    "dataDetenzioneA",     // id of the input field
        					ifFormat       :    "%d/%m/%Y",      // format of the input field
       						button         :    "id_img_6",  // trigger for the calendar (button ID)
       						// align          :    "Tl",           // alignment (defaults to "Bl")
        					singleClick    :    true,
        					timeFormat		:   "24",
        					showsTime		:   false
   							 });					    
  					 </script>   
    		</td>
        </tr>
        
        
        <tr class="even">
	        <td>
	       		 Tipologia di detentore<font color="red"> *</font>
	        </td>
	        <td>
        		<select name="tipologiaDetentore" onChange="javascript: chooseDetentore(this.value)">
					<option value="0" >&lt;--- Selezionare la tipologia di detentore  ---&gt;</option>
    				<c:forEach var="det" items="${listDetentori}">
            			<option value="<c:out value="${det.id}"/>" 
            			
            				<c:if test="${det.id == d.lookupDetentori.id}">
  		          				<c:out value="selected=selected"></c:out> 
    						</c:if>   
            			            			
            			>            				
            				${det.description}
    					</option>
            		</c:forEach>
    			</select>      		             
	        </td>
	        <td>		       
	        </td>
        </tr>
        
    
        
        
        
       </table>
                
        
       	<c:choose>
			<c:when test="${d.lookupDetentori.id == 1}">
				<div id="detentorePrivato">
			</c:when>							
			<c:otherwise>
				<div id="detentorePrivato" style="display:none;">
			</c:otherwise>
		</c:choose>		
        		 
		 <table class="tabella">
		 <tr>
        	<th colspan="3">
        		Informazioni sul Detentore privato
        	</th>
         </tr>
        		    
		    <tr class="even">
		        <td>
		       		 Nome<font color="red"> *</font>
		        </td>
		        <td>
		        	<input type="text" name="detentorePrivatoNome" maxlength="50" size="50" value="<c:out value="${d.detentorePrivatoNome}"></c:out>"/> 		             
		        </td>
		        <td>
		        </td>
            </tr>        
        	<tr class="odd">
		        <td>
		       		 Cognome<font color="red"> *</font>
		        </td>
		        <td>
		        	<input type="text" name="detentorePrivatoCognome" maxlength="50" size="50" value="<c:out value="${d.detentorePrivatoCognome}"></c:out>"/> 		             		             
		        </td>
		        <td>
		        </td>
       		 </tr>        
	        <tr class="even">
		        <td>
		       		 Codice Fiscale<font color="red"> *</font>
		        </td>
		        <td>
		        	<input type="text" name="detentorePrivatoCodiceFiscale" maxlength="16" size="50" value="<c:out value="${d.detentorePrivatoCodiceFiscale}"></c:out>"/> 		             
		        </td>
		        <td>
		        </td>
	        </tr>        
	        <tr class="odd">
		        <td>
		       		 Tipologia Documento
		        </td>
		        <td>
		        	<select name="tipologiaDocumento">
						<option value="0" >&lt;--- Selezionare la tipologia di documento ---&gt;</option>
	    				<c:forEach var="td" items="${listTipologiaDocumenti}">
	            			<option value="<c:out value="${td.id}"/>" 
	            			
	            				<c:if test="${td.id == d.lookupTipologiaDocumento.id}">
		          					<c:out value="selected=selected"></c:out> 
								</c:if>   
	            			
	            			>            				
	            				${td.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </td>	       
		        <td>
			         			         			         
					
					<div id="numeroDocumento"">
								         
			        	Numero Documento <input type="text" name="detentorePrivatoNumeroDocumento" maxlength="20" size="20" value="<c:out value="${d.detentorePrivatoNumeroDocumento}"></c:out>"/> 			             
			        </div>

		        </td>	        
        	</tr> 
        	<tr class="even">
		        <td>
		       		 Email
		        </td>
		        <td>
		        	<input type="text" name="detentorePrivatoEmail" maxlength="16" size="50" value="<c:out value="${d.detentorePrivatoEmail}"></c:out>"/> 		             		             
		        </td>
		        <td>
		        </td>
	        </tr>        
	        <tr class="odd">
		        <td>
		       		 Telefono
		        </td>
		        <td>
		        	<input type="text" name="detentorePrivatoTelefono" maxlength="16" size="50" value="<c:out value="${d.detentorePrivatoTelefono}"></c:out>"/> 			             
		        </td>
		        <td>
		        </td>
	        </tr> 
	        
	        <tr class="even">
	        <td>
	       		 Provincia e Comune di detenzione<font color="red"> *</font>
	        </td>
	        <td>
	        	<select name="provinciaDetenzione" onChange="javascript: chooseProvinciaDetenzione(this.value)">
					<option value="X"  >&lt;--- Selezionare la provincia ---&gt;</option>
    				
    				<option value="AV" 
    					<c:if test="${d.comuneDetenzione.av == true}">
							<c:out value="selected=selected"></c:out> 
						</c:if>   				
    				>  Avellino  	</option>
    				
    				<option value="BN" 
    					<c:if test="${d.comuneDetenzione.bn == true}">
							<c:out value="selected=selected"></c:out> 
						</c:if> 
    				>  Benevento  </option>
            		
            		<option value="CE" 
            			<c:if test="${d.comuneDetenzione.ce == true}">
							<c:out value="selected=selected"></c:out> 
						</c:if> 
            		>   Caserta 	</option>
            		
            		<option value="NA" 
            			<c:if test="${d.comuneDetenzione.na == true}">
							<c:out value="selected=selected"></c:out> 
						</c:if> 
            		>  Napoli 	</option>  
            		
            		<option value="SA" 
            			<c:if test="${d.comuneDetenzione.sa == true}">
							<c:out value="selected=selected"></c:out> 
						</c:if> 
            		>  Salerno 	</option>  
	    		</select>      		             
	        </td>
	        <td>    
	        	
	        	
	        	<c:choose>
					<c:when test="${d.comuneDetenzione.bn == true}">
						<div id="comuneDetenzioneBN">
					</c:when>							
					<c:otherwise>
						<div id="comuneDetenzioneBN" style="display:none;">
					</c:otherwise>
				</c:choose>	        	
		        	<select name="comuneDetenzioneBN">
						<option value="0">&lt;--- Selezionare il comune BN  ---&gt;</option>
	    				<c:forEach var="bn" items="${listComuniBN}">
	            			<option value="<c:out value="${bn.id}"/>"
	            			       			
	            			
	            				<c:if test="${bn.id == d.comuneDetenzione.id}">
	  		          				<c:out value="selected=selected"></c:out> 
	    						</c:if>  
	            			
	            			>            				
	            				${bn.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        
		        <c:choose>
					<c:when test="${d.comuneDetenzione.na == true}">
						<div id="comuneDetenzioneNA">
					</c:when>							
					<c:otherwise>
						<div id="comuneDetenzioneNA" style="display:none;">
					</c:otherwise>
				</c:choose>	 
		        	<select name="comuneDetenzioneNA">
						<option value="0">&lt;--- Selezionare il comune NA  ---&gt;</option>
	    				<c:forEach var="na" items="${listComuniNA}">
	            			<option value="<c:out value="${na.id}"/>"
	            			
		            			<c:if test="${na.id == d.comuneDetenzione.id}">
		  		          				<c:out value="selected=selected"></c:out> 
		    					</c:if>  
	            			
	            			>            				
	            				${na.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        
		        <c:choose>
					<c:when test="${d.comuneDetenzione.sa == true}">
						<div id="comuneDetenzioneSA">
					</c:when>							
					<c:otherwise>
						<div id="comuneDetenzioneSA" style="display:none;">
					</c:otherwise>
				</c:choose>			        
		        	<select name="comuneDetenzioneSA">
						<option value="0">&lt;--- Selezionare il comune SA  ---&gt;</option>
	    				<c:forEach var="sa" items="${listComuniSA}">
	            			<option value="<c:out value="${sa.id}"/>"
	            				
	            				<c:if test="${sa.id == d.comuneDetenzione.id}">
	  		          				<c:out value="selected=selected"></c:out> 
	    						</c:if>
	    						  
	            			>            				
	            				${sa.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        
		       <c:choose>
					<c:when test="${d.comuneDetenzione.ce == true}">
						<div id="comuneDetenzioneCE">
					</c:when>							
					<c:otherwise>
						<div id="comuneDetenzioneCE" style="display:none;">
					</c:otherwise>
				</c:choose>			
		        	<select name="comuneDetenzioneCE">
						<option value="0">&lt;--- Selezionare il comune CE  ---&gt;</option>
	    				<c:forEach var="ce" items="${listComuniCE}">
	            			<option value="<c:out value="${ce.id}"/>"
	            			
	            				<c:if test="${ce.id == d.comuneDetenzione.id}">
	  		          				<c:out value="selected=selected"></c:out> 
	    						</c:if>  
	            			
	            			>            				
	            				${ce.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        
		        <c:choose>
					<c:when test="${d.comuneDetenzione.av == true}">
						<div id="comuneDetenzioneAV">
					</c:when>							
					<c:otherwise>
						<div id="comuneDetenzioneAV" style="display:none;">
					</c:otherwise>
				</c:choose>			
		        	<select name="comuneDetenzioneAV">
						<option value="0">&lt;--- Selezionare il comune AV  ---&gt;</option>
	    				<c:forEach var="av" items="${listComuniAV}">
	            			<option value="<c:out value="${av.id}"/>"
	            			
	            				<c:if test="${av.id == d.comuneDetenzione.id}">
	  		          				<c:out value="selected=selected"></c:out> 
	    						</c:if>  
	            			
	            			>            				
	            				${av.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
	        </td>
        </tr>
                        
        
        <tr class="odd">
	        <td>
	       		 Luogo di detenzione<font color="red"> *</font>
	        </td>
	        <td>
	        	<input type="text" name="luogoDetenzione" maxlength="50" size="50" value="<c:out value="${d.luogoDetenzione}"></c:out>"/> 			             		             
	        </td>
	        <td>
	        </td>
        </tr>
	                      
		</table>
	</div>  
        
        
    
	<tr class='odd'>   
		<td>
		</td>
		<td>  
			<font color="red">* </font> Campi obbligatori
			<br> 	
			<input type="submit" value="Modifica" />
					
	 	</td>
	 	<td>
	 	</td>
 	</tr>


</form>