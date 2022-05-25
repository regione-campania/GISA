<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<script language="JavaScript" type="text/javascript" src="js/sinantropi/reimmissioni/edit.js"></script>


<form action="sinantropi.reimmissioni.Edit.us" name="form" method="post" onsubmit="javascript:return checkform(this);">

	<input type="hidden" name="idCattura" 		value="${c.id}"/>
	<input type="hidden" name="idSinantropo" 	value="${s.id}"/>
	<input type="hidden" name="oldCodiceIspra" id="oldCodiceIspra" value="${c.sinantropo.codiceIspra}"/>


	<h4 class="titolopagina">
	   	Modifica Rilascio
	</h4>
	<jsp:include page="/jsp/sinantropi/menuCatture.jsp"/>  
	<script type="text/javascript">
			ddtabmenu.definemenu("ddtabs2",3);
	</script> 
		
	<table class="tabella">
		
		<tr class="even">
    		<td>
    			 Data<font color="red"> *</font>
    		</td>
    		<td>   
    			<fmt:formatDate type="date" value="${c.reimmissioni.dataReimmissione}" pattern="dd/MM/yyyy" var="dataReimmissione"/> 		 		
    			 <input type="text" id="dataReimmissione" name="dataReimmissione" maxlength="32" size="50" readonly="readonly" style="width:246px;" value="${dataReimmissione}"/>
    			 <img src="images/b_calendar.gif" alt="calendario" id="id_img_1" />
 					<script type="text/javascript">
      					 Calendar.setup({
        					inputField     :    "dataReimmissione",     // id of the input field
        					ifFormat       :    "%d/%m/%Y",      // format of the input field
       						button         :    "id_img_1",  // trigger for the calendar (button ID)
       						// align          :    "Tl",           // alignment (defaults to "Bl")
        					singleClick    :    true,
        					timeFormat		:   "24",
        					showsTime		:   false
   							 });					    
  					 </script>   
    		</td>
    		<td>
	        </td>
        </tr>
        
        
        
         <tr class="odd">
        <td>
        	Provincia e Comune di Rilascio<font color="red"> *</font>
	        </td>
	        <td>
	        	<select name="provinciaReimmissione" onChange="javascript: chooseProvinciaReimmissione(this.value)">
					<option value="X"  >&lt;--- Selezionare la provincia ---&gt;</option>
    				
    				<option value="AV"     				
    					<c:if test="${c.reimmissioni.comuneReimmissione.av == true}">
							<c:out value="selected=selected"></c:out> 
						</c:if>    				
    				>  Avellino  	</option>
    				    				
    				<option value="BN" 
    					<c:if test="${c.reimmissioni.comuneReimmissione.bn == true}">
							<c:out value="selected=selected"></c:out> 
						</c:if> 		    				
    				>  Benevento  </option>
            		            		
            		<option value="CE" 
            			<c:if test="${c.reimmissioni.comuneReimmissione.ce == true}">
							<c:out value="selected=selected"></c:out> 
						</c:if> 
            		>  Caserta 	</option>
            		            		
            		<option value="NA" 
            			<c:if test="${c.reimmissioni.comuneReimmissione.na == true}">
							<c:out value="selected=selected"></c:out> 
						</c:if> 
            		>  Napoli 	</option>  
            		            		
            		<option value="SA" 
            			<c:if test="${c.reimmissioni.comuneReimmissione.sa == true}">
							<c:out value="selected=selected"></c:out> 
						</c:if>                 		
            		>  Salerno 	</option>  
	    		</select>      		             
	        </td>
	        <td>
	                
	        
	       		 	<c:choose>
						<c:when test="${c.reimmissioni.comuneReimmissione.bn == true}">
							<div id="comuneReimmissioneBN">
						</c:when>							
						<c:otherwise>
							<div id="comuneReimmissioneBN" style="display:none;">
						</c:otherwise>
					</c:choose>		        
	        	            
<!--	        	<div id="comuneCatturaBN" style="display:none;">-->
		        	<select name="comuneReimmissioneBN">
						<option value="0">&lt;--- Selezionare il comune BN  ---&gt;</option>
	    				<c:forEach var="bn" items="${listComuniBN}">
	            			<option value="<c:out value="${bn.id}"/>"
	            			
		            			<c:if test="${bn.id == c.reimmissioni.comuneReimmissione.id}">
	  		          				<c:out value="selected=selected"></c:out> 
	    						</c:if>  
	            			
	            			>            				
	            				${bn.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        
		        
		        	<c:choose>
						<c:when test="${c.reimmissioni.comuneReimmissione.na == true}">
							<div id="comuneReimmissioneNA">
						</c:when>							
						<c:otherwise>
							<div id="comuneReimmissioneNA" style="display:none;">
						</c:otherwise>
					</c:choose>		
<!--		        <div id="comuneCatturaNA" style="display:none;">-->
		        	<select name="comuneReimmissioneNA">
						<option value="0">&lt;--- Selezionare il comune NA  ---&gt;</option>
	    				<c:forEach var="na" items="${listComuniNA}">
	            			<option value="<c:out value="${na.id}"/>"
	            			
	            				<c:if test="${na.id == c.reimmissioni.comuneReimmissione.id}">
	  		          				<c:out value="selected=selected"></c:out> 
	    						</c:if>  
	            			
	            			>            				
	            				${na.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        
		       	 	<c:choose>
						<c:when test="${c.reimmissioni.comuneReimmissione.sa == true}">
							<div id="comuneReimmissioneSA">
						</c:when>							
						<c:otherwise>
							<div id="comuneReimmissioneSA" style="display:none;">
						</c:otherwise>
					</c:choose>			        
<!--		        <div id="comuneCatturaSA" style="display:none;">-->
		        	<select name="comuneReimmissioneSA">
						<option value="0">&lt;--- Selezionare il comune SA  ---&gt;</option>
	    				<c:forEach var="sa" items="${listComuniSA}">
	            			<option value="<c:out value="${sa.id}"/>"
	            			
	            				<c:if test="${sa.id == c.reimmissioni.comuneReimmissione.id}">
	  		          				<c:out value="selected=selected"></c:out> 
	    						</c:if>  
	            			
	            			>            				
	            				${sa.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		       
		       		<c:choose>
						<c:when test="${c.reimmissioni.comuneReimmissione.ce == true}">
							<div id="comuneReimmissioneCE">
						</c:when>							
						<c:otherwise>
							<div id="comuneReimmissioneCE" style="display:none;">
						</c:otherwise>
					</c:choose>			        
<!--		        <div id="comuneCatturaCE" style="display:none;">-->
		        	<select name="comuneReimmissioneCE">
						<option value="0">&lt;--- Selezionare il comune CE  ---&gt;</option>
	    				<c:forEach var="ce" items="${listComuniCE}">
	            			<option value="<c:out value="${ce.id}"/>"
	            			
	            				<c:if test="${ce.id == c.reimmissioni.comuneReimmissione.id}">
	  		          				<c:out value="selected=selected"></c:out> 
	    						</c:if>  
	            			
	            			>            				
	            				${ce.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        
		       	 	<c:choose>
						<c:when test="${c.reimmissioni.comuneReimmissione.av == true}">
							<div id="comuneReimmissioneAV">
						</c:when>							
						<c:otherwise>
							<div id="comuneReimmissioneAV" style="display:none;">
						</c:otherwise>
					</c:choose>			        
<!--		        <div id="comuneCatturaAV" style="display:none;">-->
		        	<select name="comuneReimmissioneAV">
						<option value="0">&lt;--- Selezionare il comune AV  ---&gt;</option>
	    				<c:forEach var="av" items="${listComuniAV}">
	            			<option value="<c:out value="${av.id}"/>"
	            			
	            				<c:if test="${av.id == c.reimmissioni.comuneReimmissione.id}">
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
               
        
        <tr class="even">
	        <td>
	       		 Luogo Rilascio<font color="red"> *</font>
	        </td>
	        <td>
		        <input type="text" name="luogoReimmissione" maxlength="50" size="50" value="<c:out value="${c.reimmissioni.luogoReimmissione}"></c:out>"/>      
	        </td>
	        <td>
	        </td>
        </tr> 
        
        <tr class="odd">
	        <td>
	       		 Codice ISPRA
	        </td>
	        <td>
	        	<input type="text" name="codiceIspra" id="codiceIspra" value="${c.sinantropo.codiceIspra}" maxlength="50" size="50"/>		             
	        </td>
	        <td>
	        </td>
        </tr>                     
        
		
	<tr class='odd'>   
		<td>
		</td>
		<td>  
			<font color="red">* </font> Campi obbligatori
			<br> 	
			<input type="submit" value="Modifica"/>		
	 	</td>
	 	<td>
	 	</td>
 	</tr>

</table>
</form>