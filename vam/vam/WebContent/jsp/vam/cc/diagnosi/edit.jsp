<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<script language="JavaScript" type="text/javascript" src="js/vam/cc/diagnosi/add.js"></script>

<script>
	$(function() {
		$( "#accordion" ).accordion({
			active: false,
			collapsible: true,
			autoHeight: false
		});
	});
</script>

<form action="vam.cc.diagnosi.Edit.us" method="post" onsubmit="javascript:return checkform(this);">

	<input type="hidden" name="idDiagnosi" value="<c:out value="${d.id}"/>"/>

	
	<jsp:include page="/jsp/vam/cc/menuCC.jsp?riepilogoCc=ancheDatiRicovero"/>
	<h4 class="titolopagina">
	   	Modifica della diagnosi effettuata dal Dott. ${d.modifiedBy.nome} ${d.modifiedBy.cognome}
	</h4>
	<table class="tabella">
		<tr>
    		<td>
    			 Data della diagnosi
    		</td>
    		<td>
    			 <fmt:formatDate value="${d.dataDiagnosi}" pattern="dd/MM/yyyy" var="data"/>    		
    			 <input type="text" id="dataDiagnosi" name="dataDiagnosi" maxlength="32" size="50" readonly="readonly" style="width:246px;" value="<c:out value="${data}"></c:out>"/>
    			 <img src="images/b_calendar.gif" alt="calendario" id="id_img_1" />
 					<script type="text/javascript">
      					 Calendar.setup({
        					inputField     :    "dataDiagnosi",     // id of the input field
        					ifFormat       :    "%d/%m/%Y",      // format of the input field
       						button         :    "id_img_1",  // trigger for the calendar (button ID)
       						// align          :    "Tl",           // alignment (defaults to "Bl")
        					singleClick    :    true,
        					timeFormat		:   "24",
        					showsTime		:   false
   							 });					    
  					 </script>   
    		</td>
        </tr>
        <tr>
	        <td>
	       		 Note
	        </td>
        <td>
	        <TEXTAREA NAME="note" COLS="40" ROWS="6"><c:out value="${d.note}"/></TEXTAREA>        
        </td>
        </tr>
	</table>
	
	
	
	<div id="accordion" style="width:89%">
		
			<h3><a href="#">Diagnosi Mediche</a></h3>
			<div>
				<table class="tabella">					
					
					<c:set var="i" value='1'/>	
					<c:forEach items="${diagnosiMediche}" var="temp" >									
					
					<tr class="${i % 2 == 0 ? 'odd' : 'even'}">									
							
						<c:forEach items="${d.diagnosiEffettuate}" var="oldDiagnosi">
							
								<c:if test="${temp.description == oldDiagnosi.listaDiagnosi.description}">
		  		          				<c:set var="isIn" value="YES"/>	
		  		          				
		  		          				<c:choose>
											<c:when test="${ oldDiagnosi.provata == true}">																	
													<c:set var="provata" value="YES"/>	
											</c:when>							
											<c:when test="${ oldDiagnosi.provata == false}">	
												<c:set var="provata" value="NO"/>
											</c:when>							
											<c:otherwise>								
													<c:set var="provata" value="-"/>
											</c:otherwise>
										</c:choose>    				
		  		          				
		  		          					
		    					</c:if> 			
		    					      											
							</c:forEach>					
									
							<td>
							    					
		    					
		    				<c:choose>
									<c:when test="${ isIn == 'YES'}">																	
											<input type="checkbox" name="op_${temp.id }" id="op_${temp.id }"  checked="checked"/> <label for="op_${temp.id }">${temp.description }</label>								
									</c:when>							
									<c:otherwise>								
											<input type="checkbox" name="op_${temp.id }" id="op_${temp.id }"  /> <label for="op_${temp.id }">${temp.description }</label>								
									</c:otherwise>
							</c:choose>
							
							</td>
							
							<td>
							
							<c:choose>
									<c:when test="${ provata == 'YES'}">																	
											<input type="radio" name="tipoDiagnosi_${temp.id}" id="tipoDiagnosi_${temp.id}" value="Provata" checked="checked"/> <label for="tipoDiagnosi_${temp.id}">Provata</label>	
											<input type="radio" name="tipoDiagnosi_${temp.id}" id="tipoDiagnosi_${temp.id}" value="Sospetta" /> <label for="tipoDiagnosi_${temp.id}">Sospetta</label>							
									</c:when>	
									<c:when test="${ provata == 'NO'}">																	
											<input type="radio" name="tipoDiagnosi_${temp.id}" id="tipoDiagnosi_${temp.id}" value="Provata" /> <label for="tipoDiagnosi_${temp.id}">Provata</label>	
											<input type="radio" name="tipoDiagnosi_${temp.id}" id="tipoDiagnosi_${temp.id}" value="Sospetta" checked="checked"/> <label for="tipoDiagnosi_${temp.id}">Sospetta</label>							
									</c:when>							
									<c:otherwise>								
											<input type="radio" name="tipoDiagnosi_${temp.id}" id="tipoDiagnosi_${temp.id}" value="Provata" checked/> <label for="tipoDiagnosi_${temp.id}">Provata</label>	
											<input type="radio" name="tipoDiagnosi_${temp.id}" id="tipoDiagnosi_${temp.id}" value="Sospetta" /> <label for="tipoDiagnosi_${temp.id}">Sospetta</label>															
									</c:otherwise>
							</c:choose>
																
							</td>
						</tr>
						<c:set var="isIn" value="NO"/>	
						<c:set var="provata" value="-"/>
						<c:set var="i" value='${i+1}'/>						
				</c:forEach>
							
								
				</table>
			</div>
	
		
			<h3><a href="#">Diagnosi Chirurgiche</a></h3>
			<div>
				<!--  <table class="tabella">						
					<c:set var="i" value='1'/>	
					<c:forEach items="${diagnosiChirurgiche}" var="temp" >							
					<tr class="${i % 2 == 0 ? 'odd' : 'even'}">	
						<c:forEach items="${d.diagnosiEffettuate}" var="oldDiagnosi">
							
							<c:if test="${temp.description == oldDiagnosi.listaDiagnosi.description}">
	  		          				<c:set var="isIn" value="YES"/>
	  		          				<c:choose>
										<c:when test="${ oldDiagnosi.provata == true}">																	
												<c:set var="provata" value="YES"/>	
										</c:when>							
										<c:when test="${ oldDiagnosi.provata == false}">	
											<c:set var="provata" value="NO"/>
										</c:when>							
										<c:otherwise>								
												<c:set var="provata" value="-"/>
										</c:otherwise>
									</c:choose>
	  		          				
	    					</c:if> 						
						</c:forEach>					
									
						<td>						
						
							<c:choose>
									<c:when test="${ isIn == 'YES'}">																	
											<input type="checkbox" name="op_${temp.id }" id="op_${temp.id }"  checked="checked"/> <label for="op_${temp.id }">${temp.description }</label>								
									</c:when>							
									<c:otherwise>								
											<input type="checkbox" name="op_${temp.id }" id="op_${temp.id }"  /> <label for="op_${temp.id }">${temp.description }</label>								
									</c:otherwise>
							</c:choose>
							
						</td>
						<td>
							<c:choose>
									<c:when test="${ provata == 'YES'}">																	
											<input type="radio" name="tipoDiagnosi_${temp.id}" id="tipoDiagnosi_${temp.id}" value="Provata" checked="checked"/> <label for="tipoDiagnosi_${temp.id}">Provata</label>	
											<input type="radio" name="tipoDiagnosi_${temp.id}" id="tipoDiagnosi_${temp.id}" value="Sospetta"/> <label for="tipoDiagnosi_${temp.id}">Sospetta</label>							
									</c:when>	
									<c:when test="${ provata == 'NO'}">																	
											<input type="radio" name="tipoDiagnosi_${temp.id}" id="tipoDiagnosi_${temp.id}" value="Provata" /> <label for="tipoDiagnosi_${temp.id}">Provata</label>	
											<input type="radio" name="tipoDiagnosi_${temp.id}" id="tipoDiagnosi_${temp.id}" value="Sospetta" checked="checked"/> <label for="tipoDiagnosi_${temp.id}">Sospetta</label>							
									</c:when>							
									<c:otherwise>								
											<input type="radio" name="tipoDiagnosi_${temp.id}" id="tipoDiagnosi_${temp.id}" value="Provata" checked/> <label for="tipoDiagnosi_${temp.id}">Provata</label>	
											<input type="radio" name="tipoDiagnosi_${temp.id}" id="tipoDiagnosi_${temp.id}" value="Sospetta" /> <label for="tipoDiagnosi_${temp.id}">Sospetta</label>															
									</c:otherwise>
							</c:choose>
																	
							<c:set var="isIn" value="NO"/>
							<c:set var="provata" value="-"/>	
							<c:set var="i" value='${i+1}'/>									
						</td>
						</tr>								
				</c:forEach>
						
											
				</table> -->
				
				<table class="tabella">						
					<tr class="${i % 2 == 0 ? 'odd' : 'even'}">	
						<td>
							<TEXTAREA NAME="diagnosi" COLS="40" ROWS="4"><c:out value="${d.diagnosi}"/></TEXTAREA>   
						</td>
						<td>
							<c:choose>
									<c:when test="${ d.tipoDiagnosi == 'Provata'}">																	
											<input type="radio" name="tipoDiagnosi" id="tipoDiagnosi" value="Provata" checked="checked"/> Provata
											<input type="radio" name="tipoDiagnosi" id="tipoDiagnosi" value="Sospetta"                 /> Sospetta							
									</c:when>	
									<c:when test="${ d.tipoDiagnosi == 'Sospetta'}">																	
											<input type="radio" name="tipoDiagnosi" id="tipoDiagnosi" value="Provata"                   /> Provata	
											<input type="radio" name="tipoDiagnosi" id="tipoDiagnosi" value="Sospetta" checked="checked"/> Sospetta							
									</c:when>							
									<c:otherwise>								
											<input type="radio" name="tipoDiagnosi" id="tipoDiagnosi" value="Provata" checked/> Provata	
											<input type="radio" name="tipoDiagnosi" id="tipoDiagnosi" value="Sospetta"       /> Sospetta															
									</c:otherwise>
							</c:choose>
						</td>
					</tr>								
				</table>
			</div>
	
		
			<h3><a href="#">Diagnosi Infettive</a></h3>
			<div>
				<table class="tabella">						
					<c:set var="i" value='1'/>	
					<c:forEach items="${diagnosiInfettive}" var="temp" >									
					<tr class="${i % 2 == 0 ? 'odd' : 'even'}">	
						<c:forEach items="${d.diagnosiEffettuate}" var="oldDiagnosi">							
							<c:if test="${temp.description == oldDiagnosi.listaDiagnosi.description}">
	  		          				<c:set var="isIn" value="YES"/>	  		          				
	  		          				<c:choose>
										<c:when test="${ oldDiagnosi.provata == true}">																	
												<c:set var="provata" value="YES"/>	
										</c:when>							
										<c:when test="${ oldDiagnosi.provata == false}">	
											<c:set var="provata" value="NO"/>
										</c:when>							
										<c:otherwise>								
												<c:set var="provata" value="-"/>
										</c:otherwise>
									</c:choose>	  		          					  		          						
	    					</c:if> 							
						</c:forEach>					
						
						<td>
							<c:choose>
									<c:when test="${ isIn == 'YES'}">																
											<input type="checkbox" name="op_${temp.id }" id="op_${temp.id }"  checked="checked"/> <label for="op_${temp.id }">${temp.description }</label>								
									</c:when>							
									<c:otherwise>								
											<input type="checkbox" name="op_${temp.id }" id="op_${temp.id }"  /> <label for="op_${temp.id }">${temp.description }</label>								
									</c:otherwise>
							</c:choose>					
						</td>
						<td>
							<c:choose>
									<c:when test="${ provata == 'YES'}">																	
											<input type="radio" name="tipoDiagnosi_${temp.id}" value="Provata" checked="checked"/> Provata	
											<input type="radio" name="tipoDiagnosi_${temp.id}" value="Sospetta"/> Sospetta							
									</c:when>	
									<c:when test="${ provata == 'NO'}">																	
											<input type="radio" name="tipoDiagnosi_${temp.id}" value="Provata" /> Provata	
											<input type="radio" name="tipoDiagnosi_${temp.id}" value="Sospetta" checked="checked"/> Sospetta							
									</c:when>							
									<c:otherwise>								
											<input type="radio" name="tipoDiagnosi_${temp.id}" value="Provata" checked/> Provata	
											<input type="radio" name="tipoDiagnosi_${temp.id}" value="Sospetta" /> Sospetta															
									</c:otherwise>
							</c:choose>	
							<c:set var="i" value='${i+1}'/>					
							<c:set var="isIn" value="NO"/>	
							<c:set var="provata" value="-"/>
						</td>								
					</tr>						
				</c:forEach>			
				</table>
			</div>
	
			<h3><a href="#">Controllo Periodico</a></h3>
			<div>
				<table class="tabella">
						
						<c:forEach items="${diagnosiCP}" var="temp" >	
						<tr>					
							<c:forEach items="${d.diagnosiEffettuate}" var="oldDiagnosi">
								
								<c:if test="${temp.description == oldDiagnosi.listaDiagnosi.description}">
		  		          				<c:set var="isIn" value="YES"/>	
		    					</c:if> 
								
							</c:forEach>					
							<td>
								<c:choose>
										<c:when test="${ isIn == 'YES'}">																
												<input type="checkbox" name="op_${temp.id }" id="op_${temp.id }" checked="checked"/> <label for="op_${temp.id }">${temp.description }</label>								
										</c:when>							
										<c:otherwise>								
												<input type="checkbox" name="op_${temp.id }" id="op_${temp.id }"  /> <label for="op_${temp.id }">${temp.description }</label>								
										</c:otherwise>
								</c:choose>
							</td>
											
																
						<c:set var="isIn" value="NO"/>													
						</tr>						
						</c:forEach>
						
				</table>
			</div>
	</div>
	
	
	<input type="submit" value="Salva"/>
	<input type="button" value="Annulla" onclick="attendere(), location.href='vam.cc.Detail.us'">
  		

</form>
