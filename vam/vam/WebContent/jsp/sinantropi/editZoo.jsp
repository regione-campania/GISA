<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<script language="JavaScript" type="text/javascript" src="js/sinantropi/editZoo.js"></script>


<form action="sinantropi.EditZoo.us" name="form" method="post" onsubmit="javascript:return checkform(this);">

	<input type="hidden" name="idSinantropo" value="${s.id}"/>


	<h4 class="titolopagina">
	   	Modifica dell'animale dello zoo/circo ${s.numeroAutomatico }
	</h4>
	<jsp:include page="/jsp/sinantropi/menuSin.jsp"/>  
	<script type="text/javascript">
				ddtabmenu.definemenu("ddtabs2",0)		
	</script> 
		
	<table class="tabella">
		
		<!--  
		<tr>
        	<th colspan="3">
        		Dati generali
        	</th>
        </tr>
        
        
		
		<c:if test="${s.numeroUfficiale!=''}">
			<tr class="even">
	        	<td>
	       			 Numero ufficiale
	        	</td>	        
	        	<td>   	        	
		        	<input type="text" name="numeroUfficiale" maxlength="25" size="25" value="<c:out value="${s.numeroUfficiale}"/>"/>        
	        	</td>
        	</tr>
		</c:if>
		
		<c:if test="${s.mc!=''}">
			<tr class="even">
	        	<td>
	       			 Microchip
	        	</td>	        
	        	<td>   	        	
		        	<input type="text" name="mc" maxlength="15" size="25" value="<c:out value="${s.mc}"/>"/>        
	        	</td>
        	</tr>
		</c:if>
		
		-->
		
		<tr>
        	<th colspan="3">
        		Dati dell'animale
        	</th>
        </tr>
	
		<tr class="even">
	        <td>
	       		 Classe<font color="red"> *</font>
	        </td>
	        <td>
		        <select name="specieSinantropo" onChange="javascript: chooseSpecie(this.value)">
					<option value="0" 				
					>&lt;--- Selezionare la classe del Sinantropo ---&gt;</option>
    				
    				<option value="1" 
    					<c:if test="${s.lookupSpecieSinantropi.uccello == true}">
        					<c:out value="selected=selected"></c:out> 
						</c:if>   
    				>  Uccello  </option>
            		
            		<option value="2" 
            			<c:if test="${s.lookupSpecieSinantropi.mammifero == true}">
							<c:out value="selected=selected"></c:out> 
						</c:if> 
            		>  Mammifero </option>
            		
            		<option value="3" 
            			<c:if test="${s.lookupSpecieSinantropi.rettileAnfibio == true}">
							<c:out value="selected=selected"></c:out> 
						</c:if> 
            		>  Rettile/Anfibio    </option>    
	    		</select>      
	        </td>
	        <td>  
	        
		        <c:choose>
					<c:when test="${s.lookupSpecieSinantropi.uccello == true}">
						<div id="uccelli" >
					</c:when>							
					<c:otherwise>
						<div id="uccelli" style="display:none;">
					</c:otherwise>
				</c:choose>		        
		         <!-- Questo viene sostituito con la condizione di sopra  
		         <div id="uccelli" style="display:none;">-->
		        	<select name="tipologiaSinantropoU">
						<option value="0">&lt;--- Selezionare Genere e Specie ---&gt;</option>
	    				<c:forEach var="ss" items="${listUccelli}">
	            			<option value="<c:out value="${ss.id}"/>"	            			
	            			<c:if test="${s.lookupSpecieSinantropi.id == ss.id }">
        						<c:out value="selected=selected"></c:out> 
							</c:if>  	            			
	            			>            				
	            				${ss.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		       
		       
		       
		       <c:choose>
					<c:when test="${s.lookupSpecieSinantropi.mammifero == true}">
						<div id="mammiferi" >
					</c:when>							
					<c:otherwise>
						<div id="mammiferi" style="display:none;">
					</c:otherwise>
				</c:choose>				       
		        <!-- Questo viene sostituito con la condizione di sopra    
		        <div id="mammiferi" style="display:none;">-->
		        	<select name="tipologiaSinantropoM">
						<option value="0">&lt;--- Selezionare Genere e Specie ---&gt;</option>
	    				<c:forEach var="ss" items="${listMammiferi}">
	            			<option value="<c:out value="${ss.id}"/>"	            			
		            			<c:if test="${s.lookupSpecieSinantropi.id == ss.id }">
	        						<c:out value="selected=selected"></c:out> 
								</c:if>  	            			
	            			>            				
	            				${ss.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        
		        
		        
		        
		        
		        <c:choose>
					<c:when test="${s.lookupSpecieSinantropi.rettileAnfibio == true}">
						<div id="rettiliAnfibi" >
					</c:when>							
					<c:otherwise>
						<div id="rettiliAnfibi" style="display:none;">
					</c:otherwise>
				</c:choose>	        
		        <!-- Questo viene sostituito con la condizione di sopra    
		        <div id="rettiliAnfibi" style="display:none;">-->
		        	<select name="tipologiaSinantropoRA">
						<option value="0">&lt;--- Selezionare Genere e Specie ---&gt;</option>
	    				<c:forEach var="ss" items="${listRettiliAnfibi}">
	            			<option value="<c:out value="${ss.id}"/>"
		            			<c:if test="${s.lookupSpecieSinantropi.id == ss.id }">
	        						<c:out value="selected=selected"></c:out> 
								</c:if>  
	            			>            				
	            				${ss.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
	        </td>
        </tr>
                
		<!--tr class="odd">
	        <td>
	       		 Specie
	        </td>
	        <td>
	        	<input type="text" name="razza" maxlength="50" size="50" value="<c:out value="${s.razza}"/>"/>    		             
	        </td>
	        <td>
	        </td>
        </tr-->
		
		<tr class="even">
	        <td>
	       		 Sesso<font color="red"> *</font>
	        </td>
	        <td>
	        	<select name="sesso">
					<option value="X" 
							<c:if test="${s.sesso == 'X'}">											
  		          				<c:out value="selected=selected"></c:out> 
    						</c:if>       
					>&lt;--- Selezionare il sesso ---&gt;</option>
    				
    				<option value="M"     				
    						<c:if test="${s.sesso == 'M'}">											
  		          				<c:out value="selected=selected"></c:out> 
    						</c:if>   				
    				>  Maschio</option>
            		
            		<option value="F"             		
            				<c:if test="${s.sesso == 'F'}">											
  		          				<c:out value="selected=selected"></c:out> 
    						</c:if>            		
            		>  Femmina</option>
            		
            		<option value="ND"
            				<c:if test="${s.sesso == 'ND'}">											
  		          				<c:out value="selected=selected"></c:out> 
    						</c:if>         
            		>Non Definito</option>    
	    		</select>      	             
	        </td>
	        <td>	      	  
	        </td>
        </tr>
	
		<tr class="odd">
    		<td>
    			 Et&agrave;<font color="red"> *</font>
    		</td>
    		<td>   
    			 <select name="idEta">
					<option value="-1">-- Seleziona --</option>
						<c:forEach items="${listEta}" var="temp" >
							<option value="${temp.id }"
								<c:if test="${temp.id==s.eta.id}">
									<c:out value="selected=selected"/>
								</c:if>
							>${temp.description }</option>
						</c:forEach>
				</select>
    		</td>
    		<td>
	        </td>
        </tr>
        
        <tr class="even">
	        <td>
	       		 Note
	        </td>
	        <td>
		        <TEXTAREA NAME="note" COLS="40" ROWS="6"><c:out value="${s.note}"></c:out></TEXTAREA>        
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
			<input type="submit" value="Modifica" "/>
			<input type="button" value="Annulla" onclick="attendere(), location.href='Home.us'">
	 	</td>
	 	<td>
	 	</td>
 	</tr>

</table>
</form>