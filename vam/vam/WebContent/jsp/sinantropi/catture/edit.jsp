<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<script language="JavaScript" type="text/javascript" src="js/sinantropi/catture/edit.js"></script>


<form action="sinantropi.catture.Edit.us" name="form" method="post" onsubmit="javascript:return checkform(this);">

	<input type="hidden" name="idCattura" 		value="${c.id}"/>
	<input type="hidden" name="idSinantropo" 	value="${s.id}"/>


	<h4 class="titolopagina">
	   	Modifica Rinvenimento
	</h4>
	<jsp:include page="/jsp/sinantropi/menuCatture.jsp"/>  
	<script type="text/javascript">
				ddtabmenu.definemenu("ddtabs2",1);
	</script> 
		
	<table class="tabella">
		
		<tr class="even">
    		<td>
    			 Data<font color="red"> *</font>
    		</td>
    		<td>   
    			<fmt:formatDate type="date" value="${c.dataCattura}" pattern="dd/MM/yyyy" var="dataCattura"/> 		 		
    			 <input type="text" id="dataCattura" name="dataCattura" maxlength="32" size="50" readonly="readonly" style="width:246px;" value="${dataCattura}"/>
    			 <img src="images/b_calendar.gif" alt="calendario" id="id_img_1" />
 					<script type="text/javascript">
      					 Calendar.setup({
        					inputField     :    "dataCattura",     // id of the input field
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
        	Provincia e Comune Rinvenimento<font color="red"> *</font>
	        </td>
	        <td>
	        	<select name="provinciaCattura" onChange="javascript: chooseProvinciaCattura(this.value)">
					<option value="X"  >&lt;--- Selezionare la provincia ---&gt;</option>
    				
    				<option value="AV"     				
    					<c:if test="${c.comuneCattura.av == true}">
							<c:out value="selected=selected"></c:out> 
						</c:if>    				
    				>  Avellino  	</option>
    				    				
    				<option value="BN" 
    					<c:if test="${c.comuneCattura.bn == true}">
							<c:out value="selected=selected"></c:out> 
						</c:if> 		    				
    				>  Benevento  </option>
            		            		
            		<option value="CE" 
            			<c:if test="${c.comuneCattura.ce == true}">
							<c:out value="selected=selected"></c:out> 
						</c:if> 
            		>  Caserta 	</option>
            		            		
            		<option value="NA" 
            			<c:if test="${c.comuneCattura.na == true}">
							<c:out value="selected=selected"></c:out> 
						</c:if> 
            		>  Napoli 	</option>  
            		            		
            		<option value="SA" 
            			<c:if test="${c.comuneCattura.sa == true}">
							<c:out value="selected=selected"></c:out> 
						</c:if>                 		
            		>  Salerno 	</option>  
	    		</select>      		             
	        </td>
	        <td>
	                
	        
	       		 	<c:choose>
						<c:when test="${c.comuneCattura.bn == true}">
							<div id="comuneCatturaBN">
						</c:when>							
						<c:otherwise>
							<div id="comuneCatturaBN" style="display:none;">
						</c:otherwise>
					</c:choose>		        
	        	            
<!--	        	<div id="comuneCatturaBN" style="display:none;">-->
		        	<select name="comuneCatturaBN">
						<option value="0">&lt;--- Selezionare il comune BN  ---&gt;</option>
	    				<c:forEach var="bn" items="${listComuniBN}">
	            			<option value="<c:out value="${bn.id}"/>"
	            			
		            			<c:if test="${bn.id == c.comuneCattura.id}">
	  		          				<c:out value="selected=selected"></c:out> 
	    						</c:if>  
	            			
	            			>            				
	            				${bn.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        
		        
		        	<c:choose>
						<c:when test="${c.comuneCattura.na == true}">
							<div id="comuneCatturaNA">
						</c:when>							
						<c:otherwise>
							<div id="comuneCatturaNA" style="display:none;">
						</c:otherwise>
					</c:choose>		
<!--		        <div id="comuneCatturaNA" style="display:none;">-->
		        	<select name="comuneCatturaNA">
						<option value="0">&lt;--- Selezionare il comune NA  ---&gt;</option>
	    				<c:forEach var="na" items="${listComuniNA}">
	            			<option value="<c:out value="${na.id}"/>"
	            			
	            				<c:if test="${na.id == c.comuneCattura.id}">
	  		          				<c:out value="selected=selected"></c:out> 
	    						</c:if>  
	            			
	            			>            				
	            				${na.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        
		       	 	<c:choose>
						<c:when test="${c.comuneCattura.sa == true}">
							<div id="comuneCatturaSA">
						</c:when>							
						<c:otherwise>
							<div id="comuneCatturaSA" style="display:none;">
						</c:otherwise>
					</c:choose>			        
<!--		        <div id="comuneCatturaSA" style="display:none;">-->
		        	<select name="comuneCatturaSA">
						<option value="0">&lt;--- Selezionare il comune SA  ---&gt;</option>
	    				<c:forEach var="sa" items="${listComuniSA}">
	            			<option value="<c:out value="${sa.id}"/>"
	            			
	            				<c:if test="${sa.id == c.comuneCattura.id}">
	  		          				<c:out value="selected=selected"></c:out> 
	    						</c:if>  
	            			
	            			>            				
	            				${sa.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		       
		       		<c:choose>
						<c:when test="${c.comuneCattura.ce == true}">
							<div id="comuneCatturaCE">
						</c:when>							
						<c:otherwise>
							<div id="comuneCatturaCE" style="display:none;">
						</c:otherwise>
					</c:choose>			        
<!--		        <div id="comuneCatturaCE" style="display:none;">-->
		        	<select name="comuneCatturaCE">
						<option value="0">&lt;--- Selezionare il comune CE  ---&gt;</option>
	    				<c:forEach var="ce" items="${listComuniCE}">
	            			<option value="<c:out value="${ce.id}"/>"
	            			
	            				<c:if test="${ce.id == c.comuneCattura.id}">
	  		          				<c:out value="selected=selected"></c:out> 
	    						</c:if>  
	            			
	            			>            				
	            				${ce.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        
		       	 	<c:choose>
						<c:when test="${c.comuneCattura.av == true}">
							<div id="comuneCatturaAV">
						</c:when>							
						<c:otherwise>
							<div id="comuneCatturaAV" style="display:none;">
						</c:otherwise>
					</c:choose>			        
<!--		        <div id="comuneCatturaAV" style="display:none;">-->
		        	<select name="comuneCatturaAV">
						<option value="0">&lt;--- Selezionare il comune AV  ---&gt;</option>
	    				<c:forEach var="av" items="${listComuniAV}">
	            			<option value="<c:out value="${av.id}"/>"
	            			
	            				<c:if test="${av.id == c.comuneCattura.id}">
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
	       		 Luogo Rinvenimento<font color="red"> *</font>
	        </td>
	        <td>
		        <input type="text" name="luogoCattura" maxlength="50" size="50" value="<c:out value="${c.luogoCattura}"></c:out>"/>      
	        </td>
	        <td>
	        </td>
        </tr>                      
        
        <tr class="odd">
	        <td>
	       		 Note Rinvenimento
	        </td>
	        <td>
		        <TEXTAREA NAME="noteCattura" COLS="40" ROWS="6"><c:out value="${c.noteCattura}"></c:out></TEXTAREA>        
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
			<input type="button" value="Annulla" onclick="attendere(), location.href='sinantropi.Detail.us?idSinantropo=${s.id}'">		
	 	</td>
	 	<td>
	 	</td>
 	</tr>

</table>
</form>