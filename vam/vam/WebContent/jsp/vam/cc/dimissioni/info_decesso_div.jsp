<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<% 
String data = (String)request.getParameter("dataEvento");
String dataDecessoPresunta = (String)request.getParameter("dataDecessoPresunta");
String decessoValue = (String)request.getParameter("decessoValue");
String idNescroscopico = (String)request.getParameter("idNecroscopico");
String idSmaltimento = (String)request.getParameter("idSmaltimento");
%>

<c:set var="dataEvento" value="<%=data%>"/>
<c:set var="dataDecessoPresunta" value="<%=dataDecessoPresunta%>"/>
<c:set var="decessoValue" value="<%=decessoValue%>"/>


<table class="tabella">
<tr>
        	<th colspan="3">
        		Informazioni Decesso
        	</th>
        </tr>
        <tr class='even'>
    		<td>
    			Data<font color="red"> *</font>
    		</td>
    		<td>
    			<c:choose>
	    			<c:when test="${cc.accettazione.animale.decedutoNonAnagrafe}">
	    				<fmt:formatDate value="${cc.accettazione.animale.dataMorte}" pattern="dd/MM/yyyy" var="data"/> 
	    			</c:when>
	    			<c:otherwise>
	    				<fmt:formatDate value="${dataEvento}" pattern="dd/MM/yyyy" var="data"/> 
	    			</c:otherwise>
	    		</c:choose>
				<input type="text" id="dataMorte" name="dataMorte" maxlength="32" size="50" readonly="readonly" style="width:40%;" value="${data}"/>
		    		 <img src="images/b_calendar.gif" alt="calendario" id="id_img_" />
		 				<script type="text/javascript">
		      				 Calendar.setup({
		        				inputField     :    "dataMorte",     // id of the input field
		        				ifFormat       :    "%d/%m/%Y",      // format of the input field
		       					button         :    "id_img_",  // trigger for the calendar (button ID)
		       					// align          :    "Tl",           // alignment (defaults to "Bl")
		        				singleClick    :    true,
		        				timeFormat		:   "24",
		        				showsTime		:   false
		   						 });					    
		  				 </script>   
			</td>
			<td>						
				<c:choose>
	    			<c:when test="${cc.accettazione.animale.decedutoNonAnagrafe==true}">
	    				<c:if test="${cc.accettazione.animale.dataMortePresunta!=null}">
	    					<c:choose>	    			
								<c:when test="${cc.accettazione.animale.dataMortePresunta == true}">					
									<input type="radio" name="dataMorteCerta" id="dataMorteCertaT" value="false" checked/><label for="dataMorteCertaT">Presunta</label>	
									<br>
									<input type="radio" name="dataMorteCerta" id="dataMorteCertaF" value="true" /> <label for="dataMorteCertaF">Certa</label>				
								</c:when>
								<c:otherwise>
									<input type="radio" name="dataMorteCerta" id="dataMorteCertaT" value="false" /><label for="dataMorteCertaT">Presunta</label>	
									<br>
									<input type="radio" name="dataMorteCerta" id="dataMorteCertaF" value="true" checked/> <label for="dataMorteCertaF">Certa</label>					   		 
								</c:otherwise>	
							</c:choose>
						</c:if>
						<c:if test="${cc.accettazione.animale.dataMortePresunta==null}">
							<input type="radio" name="dataMorteCerta" id="dataMorteCertaT" value="false" checked/><label for="dataMorteCertaT">Presunta</label>	
							<br>
							<input type="radio" name="dataMorteCerta" id="dataMorteCertaF" value="true" /> <label for="dataMorteCertaF">Certa</label>				
						</c:if>
	    			</c:when>
	    			<c:otherwise>
	    				<c:choose>	    			
							<c:when test="${dataDecessoPresunta == true}">					
								<input type="radio" name="dataMorteCerta" id="dataMorteCertaT" value="false" checked/><label for="dataMorteCertaT">Presunta</label>	
								<br>
								<input type="radio" name="dataMorteCerta" id="dataMorteCertaF" value="true" /> <label for="dataMorteCertaF">Certa</label>				
							</c:when>
							<c:otherwise>
								<input type="radio" name="dataMorteCerta" id="dataMorteCertaT" value="false" /><label for="dataMorteCertaT">Presunta</label>	
								<br>
								<input type="radio" name="dataMorteCerta" id="dataMorteCertaF" value="true" checked/> <label for="dataMorteCertaF">Certa</label>					   		 
							</c:otherwise>	
						</c:choose>
	    			</c:otherwise>
	    		</c:choose>
	    		
	    			
			
			
	        </td>
    	</tr>
    	
    	<tr class='odd'>
	        <td>
	    		Probabile causa<font color="red"> *</font>
    		</td>
    		<td> 
    			<select style="width:40%" name="causaMorteIniziale">
    				<option value="">&lt;--Selezionare un valore--&gt;</option>
		        	 <c:forEach items="${listCMI}" var="cmi" >					        	 	
						<c:choose>
	    					<c:when test="${cc.accettazione.animale.decedutoNonAnagrafe}">
	    						<c:choose>
	  								<c:when test="${cmi.description == cc.accettazione.animale.causaMorte.description}">			
		        	 					<option value="${cmi.id }" selected>${cmi.description }</option>	        	 				
									</c:when>
									<c:otherwise>
										<option value="${cmi.id }">${cmi.description }</option>
									</c:otherwise>
								</c:choose>	
	    					</c:when>
	    					<c:otherwise>
	    						<c:choose>
	  								<c:when test="${cmi.description == decessoValue}">			
		        	 					<option value="${cmi.id }" selected>${cmi.description }</option>	        	 				
									</c:when>
									<c:otherwise>
										<option value="${cmi.id }">${cmi.description }</option>
									</c:otherwise>
								</c:choose>	
	    					</c:otherwise>
	    				</c:choose>
					</c:forEach>
		      	</select>	    		    
	        </td>
	        <td>
	        </td>
        </tr>



        <tr>

		        <td>
		       		 Provincia e Comune
		        </td>
		        <td>
		        	<select style="width:40%" id="provincia"  name="provincia" onChange="javascript: chooseProvincia(this.value)">
						<option value=""  SELECTED>&lt;--- Selezionare la provincia ---&gt;</option>
	    				<option value="AV" >  Avellino  	</option>
	    				<option value="BN" >  Benevento  </option>
	            		<option value="CE" >  Caserta 	</option>
	            		<option value="NA" >  Napoli 	</option>  
	            		<option value="SA" >  Salerno 	</option>  
		    		</select> 
		    		<select style="width:50%;display:none;" name="comuneBN" id="comuneChooserBN">
						<option value="">&lt;--- Selezionare il comune BN  ---&gt;</option>
	    				<c:forEach var="bn" items="${listComuniBN}">
	            			<option value="<c:out value="${bn.codiceIstat}"/>">            				
	            				${bn.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        	<select style="width:50%;display:none;" name="comuneNA" id="comuneChooserNA">
						<option value="">&lt;--- Selezionare il comune NA  ---&gt;</option>
	    				<c:forEach var="na" items="${listComuniNA}">
	            			<option value="<c:out value="${na.codiceIstat}"/>">            				
	            				${na.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        	<select style="width:50%;display:none;" name="comuneSA" id="comuneChooserSA">
						<option value="">&lt;--- Selezionare il comune SA  ---&gt;</option>
	    				<c:forEach var="sa" items="${listComuniSA}">
	            			<option value="<c:out value="${sa.codiceIstat}"/>">            				
	            				${sa.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        	<select style="width:50%;display:none;" name="comuneCE" id="comuneChooserCE">
						<option value="">&lt;--- Selezionare il comune CE  ---&gt;</option>
	    				<c:forEach var="ce" items="${listComuniCE}">
	            			<option value="<c:out value="${ce.codiceIstat}"/>">            				
	            				${ce.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        	<select style="width:50%;display:none;" name="comuneAV" id="comuneChooserAV">
						<option value="">&lt;--- Selezionare il comune AV  ---&gt;</option>
	    				<c:forEach var="av" items="${listComuniAV}">
	            			<option value="<c:out value="${av.codiceIstat}"/>">            				
	            				${av.description}
	    					</option>
	            		</c:forEach>
	    			</select>   
		    	</td>
		    	<td>
		        </td>
        	</tr> 
        
        
	       	<tr >
	        	<td>
	        		Indirizzo
	        	</td>
	        	<td>
	        		<input type="text" name="indirizzo" id="indirizzo" maxlength="25" size="25" style="width:40%" />
	        	</td>
	        	<td>
	        	</td>
	       	</tr>	
       	
	       	<tr>
	        	<td>
	        		Note 
	        	</td>
	        	<td>
	        		<TEXTAREA NAME="note" COLS=40 ROWS=3></TEXTAREA>         		
	        	</td>
	        	<td>
	        	</td>
	       	</tr>  
        
        <tr class='even'>
	        <td>
	    		Operazioni richieste<font color="red"> *</font>   		
	    	</td>
    		<td>
    			<!-- <input type="checkbox" name="op_<%=idNescroscopico%>"  id="esameNecroscopico" /> Esame Necroscopico</br>  -->
	    		<input type="checkbox" name="op_<%=idSmaltimento%>" id="smaltimentoCarogna"/> Trasporto Spoglie
	        </td>
	        <td>
	        </td>
        </tr>
</table>