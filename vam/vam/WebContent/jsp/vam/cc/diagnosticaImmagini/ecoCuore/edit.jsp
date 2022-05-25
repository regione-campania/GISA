<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<script language="JavaScript" type="text/javascript" src="js/vam/cc/diagnosticaImmagini/ecoCuore/edit.js"></script>
<script language="JavaScript" type="text/javascript" src="js/azionijavascript.js"></script>


<form action="vam.cc.diagnosticaImmagini.ecoCuore.Edit.us?idEcoCuore=${ecoCuore.id}" name="form" id="form" method="post" class="marginezero">
           
    
    <jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
    <h4 class="titolopagina">
     		Modifica Eco-Cuore
    </h4>
	
    <table class="tabella">
    	
    	<tr>
        	<th colspan="3">
        		Informazioni preliminari
        	</th>
        </tr>
    	<tr>
    		<td style="width:50%">
    			 Data Richiesta<font color="red"> *</font>
    			 <fmt:formatDate type="date" value="${ecoCuore.dataRichiesta}" pattern="dd/MM/yyyy" var="dataRichiesta"/> 		
    			 <input type="text" id="dataRichiesta" name="dataRichiesta" maxlength="32" size="50" readonly="readonly" style="width:246px;" value="${dataRichiesta}" />
    			 <img src="images/b_calendar.gif" alt="calendario" id="id_img_1" />
 					<script type="text/javascript">
      					 Calendar.setup({
        					inputField     :    "dataRichiesta",     // id of the input field
        					ifFormat       :    "%d/%m/%Y",      // format of the input field
       						button         :    "id_img_1",  // trigger for the calendar (button ID)
       						// align          :    "Tl",           // alignment (defaults to "Bl")
        					singleClick    :    true,
        					timeFormat		:   "24",
        					showsTime		:   false
   							 });					    
  					 </script>   
    		</td>
    		<td style="width:50%">
    			 Data Esito
    			 <fmt:formatDate type="date" value="${ecoCuore.dataEsito}" pattern="dd/MM/yyyy" var="dataEsito"/> 		
    			 <input type="text" id="dataEsito" name="dataEsito" maxlength="32" size="50" readonly="readonly" style="width:246px;" value="${dataEsito}" />
    			 <img src="images/b_calendar.gif" alt="calendario" id="id_img_2" />
 					<script type="text/javascript">
      					 Calendar.setup({
        					inputField     :    "dataEsito",     // id of the input field
        					ifFormat       :    "%d/%m/%Y",      // format of the input field
       						button         :    "id_img_2",  // trigger for the calendar (button ID)
       						// align          :    "Tl",           // alignment (defaults to "Bl")
        					singleClick    :    true,
        					timeFormat		:   "24",
        					showsTime		:   false
   							 });					    
  					 </script>   
    		</td>
    		<td style="width:34%">
        		&nbsp;
        	</td>
        </tr>
        
        <tr>
        	<th colspan="3">
        		Impostazione unit&agrave; di misura
        	</th>
        </tr>
        <tr>
        	<td>
        		Esame B-Mode/M-Mode<font color="red"> *</font>
        	</td>
        	<td colspan="2">
        		<select name="unitaMisuraBMode" id="unitaMisuraBMode" onchange="popolaUnitaMisura(this.value)">
						<option value="" 
										   <c:if test="${ecoCuore.unitaMisuraBMode==''}">
  		          								selected=selected 
    									   </c:if> 
										><- Selezionare unità di misura -></option>
            			<option value="cm" 
										   <c:if test="${ecoCuore.unitaMisuraBMode=='cm'}">
  		          								selected=selected 
    									   </c:if> 
										>cm</option>
            			<option value="mm" 
										   <c:if test="${ecoCuore.unitaMisuraBMode=='mm'}">
  		          								selected=selected 
    									   </c:if> 
										>mm</option>
    			</select>
        	</td>
        </tr>
        <tr class="odd">
        	<td>
        		Esame Doppler<font color="red"> *</font>
        	</td>
        	<td colspan="2">
        		<select name="unitaMisuraDoppler" id="unitaMisuraDoppler"  onchange="popolaUnitaMisuraEsameDoppler(this.value)">
						<option value="" 
										   <c:if test="${ecoCuore.unitaMisuraDoppler==''}">
  		          								selected=selected 
    									   </c:if> 
										><- Selezionare unità di misura -></option>
            			<option value="cm/s" 
										   <c:if test="${ecoCuore.unitaMisuraDoppler=='cm/s'}">
  		          								selected=selected 
    									   </c:if> 
										>cm/s</option>
            			<option value="m/s" 
										   <c:if test="${ecoCuore.unitaMisuraDoppler=='m/s'}">
  		          								selected=selected 
    									   </c:if> 
										>m/s</option>
    			</select>
        	</td>
        </tr>
        </table>
        
        <table class="tabella">
        <tr>
        	<th colspan="3">
        		Esame ecocardiografico bidimensionale ed M-mode 
        	</th>
        </tr>
        
        <c:set var="i" value='1'/>	
		<c:forEach items="${tipi}" var="tipo" >	
		
		<c:set var="esitoNormale" value="NO"></c:set>
		<c:set var="anomaliePresenti" value="NO"></c:set>
		
		<tr class="${i % 2 == 0 ? 'odd' : 'even'}">
		
			<%-- Cicli preventivi per controllare se il tipo per questo Eco-Cuore ha esito normale, anormale o non esaminato --%>
			<c:forEach items="${ecoCuoreEsitos}" var="esito">
				<c:if test="${esito.lookupEcoCuoreTipo.id==tipo.id && esito.normale==true}">
					<c:set var="esitoNormale" value="YES"></c:set>
				</c:if>
				<c:if test="${esito.lookupEcoCuoreTipo.id==tipo.id && esito.lookupEcoCuoreAnomalia!=null}">
					<c:set var="anomaliePresenti" value="YES"></c:set>
				</c:if>
			</c:forEach>
			
			<td style="width:20%">				
				${tipo.descrizione} 
			</td>
	    
			<td style="width:20%">
				<input type="radio" name="_${tipo.id}" id="_N${tipo.id}" value="Normale" onclick="javascript: hiddenDiv('_${tipo.id}');" 
					<c:if test="${esitoNormale=='YES'}">
  		          		checked=checked 
    				</c:if>
				> <label for="_N${tipo.id}">Normale</label><br>
				<input type="radio" name="_${tipo.id}" id="_A${tipo.id}" value="Anormale" onclick="javascript: toggleGroup('_${tipo.id}');" 
					<c:if test="${anomaliePresenti=='YES'}">
  		          		checked=checked 
    				</c:if>
				> <label for="_A${tipo.id}">Anormale</label><br>
				<input type="radio" name="_${tipo.id}" id="_NE${tipo.id}" value="NE" onclick="javascript: hiddenDiv('_${tipo.id}');" 
					<c:if test="${esitoNormale=='NO' && anomaliePresenti=='NO'}">
  		          		checked=checked 
    				</c:if>
				> <label for="_NE${tipo.id}">Non esaminato</label><br>
			</td>			
			<td style="width:60%">
				<div id="_${i}" 
					<c:if test="${anomaliePresenti!='YES'}">
  		          		style="display:none;"
    				</c:if>
    				>	
					<c:forEach items="${tipo.lookupEcoCuoreAnomalias}" var="anomaliaTipo" >
						
						<c:set var="checked" value="NO"></c:set>
							<c:forEach items="${ecoCuoreEsitos}" var="esito">
								<c:if test="${esito.lookupEcoCuoreAnomalia.id == anomaliaTipo.id}">
									<c:set var="checked" value="SI"></c:set>
								</c:if>
							</c:forEach>
												
						<%-- Variabile per l'individuazione dei figli di una anomalia --%>
						<c:set var="isIn" value="NO"/>	
						
						<%-- Ciclo preventivo per controllare se una anomalia ha figli --%>
						<c:forEach items="${anomalie}" var="anomalia" >							
							<c:if test="${anomalia.lookupEcoCuoreAnomalia.id == anomaliaTipo.id}">
								<c:set var="isIn" value="YES"/>	
							</c:if>																
						</c:forEach>
						
						<%-- Se una anomalia non ha figli allora checkbox normale,
						se invece possiede figli il padre viene messo non checkabile
						in rosso e immediatamente sotto viene presentata la lista dei figli --%>
						<c:choose>
							<c:when test="${anomaliaTipo.lookupEcoCuoreAnomalia==null}">
								<c:choose>
									<c:when test="${ isIn == 'NO'}">																	
										<input type="checkbox" 
											<c:if test="${checked=='SI'}">
												checked=checked 
											</c:if>
										id="anomalia_${anomaliaTipo.id }" name="anomalia_${anomaliaTipo.id }" onclick="anomalieMutuamenteEsclusive('${anomaliaTipo.anomalieMutuamenteEsclusive}')"/> <label for="anomalia_${anomaliaTipo.id }">${anomaliaTipo.descrizione }</label>
										<br/>	
									</c:when>							
									<c:otherwise>
										<label><b><i>${anomaliaTipo.descrizione}</i></b></label><br/>								
										<c:forEach items="${tipo.lookupEcoCuoreAnomalias}" var="tempAnomaliaTipo" >	
										
											<c:if test="${tempAnomaliaTipo.lookupEcoCuoreAnomalia.id == anomaliaTipo.id}">
												<c:set var="isIn" value="YES"/>	
												
												<c:forEach items="${ecoCuoreEsitos}" var="esito">
													<c:if test="${esito.lookupEcoCuoreAnomalia.id == tempAnomaliaTipo.id}">
														<c:set var="checked" value="SI"></c:set>
													</c:if>
												</c:forEach>
																						
													&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" 
													<c:if test="${checked=='SI'}">
														checked=checked
														<c:set var="checked" value="NO"></c:set> 
													</c:if>
												id="anomalia_${tempAnomaliaTipo.id}" name="anomalia_${tempAnomaliaTipo.id }" onclick="anomalieMutuamenteEsclusive('${tempAnomaliaTipo.anomalieMutuamenteEsclusive}')" /> <label for="anomalia_${tempAnomaliaTipo.id}">${tempAnomaliaTipo.descrizione }</label>	
												<br>
											</c:if>				
										</c:forEach>
										
										<br/>
										
									</c:otherwise>
								</c:choose>
							</c:when>
						</c:choose>
					</c:forEach>
				</div>
			</td>			
		</tr>
		
		<c:set var="i" value='${i+1}'/>

	    </c:forEach>
        </table>
        
		<table class="tabella">
        <tr>
        	<th colspan="3">
        		Altre informazioni
        	</th>
        </tr>
        
        <tr>
        	<td style="width:20%">
        		Neoformazione base cardiaca
        	</td>
        	<td colspan="2">
        		<input type="radio" name="neoformazioneBaseCardiaca" id="neoformazioneBaseCardiacaS" value="true"  
        			<c:if test="${ecoCuore.neoformazioneBaseCardiaca==true}">
  		          		checked=checked
    				</c:if>
    			> <label for="neoformazioneBaseCardiacaS">Si</label><br>
        		<input type="radio" name="neoformazioneBaseCardiaca" id="neoformazioneBaseCardiacaN" value="false" 
        			<c:if test="${ecoCuore.neoformazioneBaseCardiaca==false}">
  		          		checked=checked
    				</c:if>
    			 > <label for="neoformazioneBaseCardiacaN">No</label><br>
        	</td>
        </tr>
        
        <tr class="odd">
        	<td style="width:20%">
        		Neoformazione auricola DX
        	</td>
        	<td colspan="2">
        		<input type="radio" name="neoformazioneAuricolaDx" id="neoformazioneAuricolaDxS" value="true"  
        			<c:if test="${ecoCuore.neoformazioneAuricolaDx==true}">
  		          		checked=checked 
    				</c:if>
    			> <label for="neoformazioneAuricolaDxS">Si</label><br>
        		<input type="radio" name="neoformazioneAuricolaDx" id="neoformazioneAuricolaDxN" value="false"  
        			<c:if test="${ecoCuore.neoformazioneAuricolaDx==false}">
  		          		checked=checked 
    				</c:if>
    				> <label for="neoformazioneAuricolaDxN">No</label><br>
        	</td>
        </tr>
        
        <tr>
        	<td style="width:20%">
        		Difetto interventricolare
        	</td>
        	<td style="width:10%">
        		<input type="radio" name="difettoInterventricolare" id="difettoInterventricolareSI" value="true" onclick="document.getElementById('divFlussoDifettoInterventricolare').style.display='block';document.getElementById('flussoDifettoInterventricolareSXDX').disabled=false;document.getElementById('flussoDifettoInterventricolareDXSX').disabled=false;document.getElementById('flussoDifettoInterventricolareSXDX').checked=false;document.getElementById('flussoDifettoInterventricolareDXSX').checked=false;" 
        			<c:if test="${ecoCuore.difettoInterventricolare==true}">
	          			checked=checked 
					</c:if>
        		> <label for="difettoInterventricolareSI">Si</label><br>
        		<input type="radio" name="difettoInterventricolare" id="difettoInterventricolareNO" value="false" onclick="document.getElementById('divFlussoDifettoInterventricolare').style.display='none';document.getElementById('flussoDifettoInterventricolareSXDX').disabled=true;document.getElementById('flussoDifettoInterventricolareDXSX').disabled=true;document.getElementById('flussoDifettoInterventricolareSXDX').checked=false;document.getElementById('flussoDifettoInterventricolareDXSX').checked=false;" 
        			<c:if test="${ecoCuore.difettoInterventricolare==false}">
	          			checked=checked 
					</c:if>
				> <label for="difettoInterventricolareNO">No</label><br>
        	</td>
        	<td style="width:70%">
        		<div id="divFlussoDifettoInterventricolare" 
        			<c:if test="${ecoCuore.difettoInterventricolare==false}">
        				style="display:none;" 
        			</c:if>
        		>
        		<input type="radio" name="flussoDifettoInterventricolare" id="flussoDifettoInterventricolareSXDX" value="Sx/Dx" 
        			<c:if test="${ecoCuore.difettoInterventricolare==false}">
        				disabled=disabled 
        			</c:if>
        			
        			<c:if test="${ecoCuore.flussoDifettoInterventricolare==true}">
	          			checked=checked 
					</c:if>
        		> <label for="flussoDifettoInterventricolareSXDX">Flusso Sx/Dx</label> <br/>
        		<input type="radio" name="flussoDifettoInterventricolare" id="flussoDifettoInterventricolareDXSX" value="Dx/Sx" 
        			<c:if test="${ecoCuore.difettoInterventricolare==false}">
        				disabled=disabled 
        			</c:if>
        			 
        			<c:if test="${ecoCuore.flussoDifettoInterventricolare==false}">
	          			checked=checked 
					</c:if>
					> <label for="flussoDifettoInterventricolareDXSX">Flusso Dx/Sx</label> <br/>
					</div>
        	</td>
        </tr>
        
        <tr class="odd">
        	<td style="width:20%">
        		Difetto interatriale
        	</td>
        	<td style="width:10%">
        		<input type="radio" name="difettoInteratriale" id="difettoInteratrialeSI" value="true" onclick="document.getElementById('divflussoDifettoInteratriale').style.display='block';document.getElementById('flussoDifettoInteratrialeSXDX').disabled=false;document.getElementById('flussoDifettoInteratrialeDXSX').disabled=false;document.getElementById('flussoDifettoInteratrialeSXDX').checked=false;document.getElementById('flussoDifettoInteratrialeDXSX').checked=false;" 
        			<c:if test="${ecoCuore.difettoInteratriale==true}">
	          			checked=checked 
					</c:if>
					> <label for="difettoInteratrialeSI">Si</label><br>
        		<input type="radio" name="difettoInteratriale" id="difettoInteratrialeNO" value="false" onclick="document.getElementById('divflussoDifettoInteratriale').style.display='none';document.getElementById('flussoDifettoInteratrialeSXDX').disabled=true;document.getElementById('flussoDifettoInteratrialeDXSX').disabled=true;document.getElementById('flussoDifettoInteratrialeSXDX').checked=false;document.getElementById('flussoDifettoInteratrialeDXSX').checked=false;" 
        			<c:if test="${ecoCuore.difettoInteratriale==false}">
	          			checked=checked 
					</c:if>
					> <label for="difettoInteratrialeNO">No</label><br>
        	</td>
        	<td style="width:70%">
        		<div id="divflussoDifettoInteratriale"
        			<c:if test="${ecoCuore.difettoInteratriale==false}">
        				style="display:none;"
        			</c:if>
        		>
        		
        		<input type="radio" name="flussoDifettoInteratriale" id="flussoDifettoInteratrialeSXDX" value="Sx/Dx"
        			<c:if test="${ecoCuore.difettoInteratriale==false}">
        				disabled=disabled 
        			</c:if>
        			
        			<c:if test="${ecoCuore.flussoDifettoInteratriale==true}">
	          			checked=checked
					</c:if>
					> <label for="flussoDifettoInteratrialeSXDX">Flusso Sx/Dx</label> <br/>
        		<input type="radio" name="flussoDifettoInteratriale" id="flussoDifettoInteratrialeDXSX" value="Dx/Sx"
        			<c:if test="${ecoCuore.difettoInteratriale==false}">
        				disabled=disabled 
        			</c:if>
        			<c:if test="${ecoCuore.flussoDifettoInteratriale==false}">
	          			checked=checked
					</c:if>
        		> <label for="flussoDifettoInteratrialeDXSX">Flusso Dx/Sx</label> <br/>
        		</div>
        	</td>
        </tr>
        
        <tr>
        	<td style="width:20%">
        		Dotto arterioso pervio
        	</td>
        	<td colspan="2">
        		<input type="radio" name="dottoArteriosoPervio" value="true"  id="dottoArteriosoPervioS" 
        			<c:if test="${ecoCuore.dottoArteriosoPervio==true}">
	          			checked=checked
					</c:if>
				/> <label for="dottoArteriosoPervioS">Si</label><br>
        		<input type="radio" name="dottoArteriosoPervio" value="false"  id="dottoArteriosoPervioN" 
	        		<c:if test="${ecoCuore.dottoArteriosoPervio==false}">
	          			checked=checked
					</c:if>
        		/> <label for="dottoArteriosoPervioN">No</label><br>
        	</td>
        </tr>
        
        <tr class="odd">
        	<td style="width:20%">
        		Versamento pericardico
        	</td>
        	<td colspan="2">
        		<input type="radio" name="versamentoPericardico" value="true"  id="versamentoPericardicoS" 
        			<c:if test="${ecoCuore.versamentoPericardico==true}">
	          			checked=checked
					</c:if>
        		/> <label for="versamentoPericardicoS">Si</label><br>
        		<input type="radio" name="versamentoPericardico" value="false"  id="versamentoPericardicoN" 
        			<c:if test="${ecoCuore.versamentoPericardico==false}">
	          			checked=checked
					</c:if>
        		/> <label for="versamentoPericardicoN">No</label><br>
        	</td>
        </tr>
        
        <tr>
        	<td style="width:20%">
        		Versamento toracico
        	</td>
        	<td colspan="2">
        		<input type="radio" name="versamentoToracico" value="true"  id="versamentoToracicoS" 
        			<c:if test="${ecoCuore.versamentoToracico==true}">
	          			checked=checked
					</c:if>
        		/> <label for="versamentoToracicoS">Si</label><br>
        		<input type="radio" name="versamentoToracico" value="false"  id="versamentoToracicoN" 
        			<c:if test="${ecoCuore.versamentoToracico==false}">
	          			checked=checked
					</c:if>
        		/> <label for="versamentoToracicoN">No</label><br>
        	</td>
        </tr>
        
        <tr>
        	<th colspan="3">
        		L'esame ecocardiografico bidimensionale ed M-mode ha permesso la rilevazione dei seguenti parametri
        	</th>
        </tr>
        
        <tr>
        	<td style="width:20%">
        		IVSd	
        	</td>
        	<td colspan="2">
        		<input type="text" maxlength="7" name="IVSd" id="IVSd" size="5" onblur="isNumber(this.value,'IVSd',this)" value="${ecoCuore.IVSd}"/> 
        		<input type="text" name="IVSdUnita" id="IVSdUnita" size="5" readonly="readonly" value="${ecoCuore.unitaMisuraBMode}" />
        	</td>
        </tr>
        
        <tr class="odd">
        	<td style="width:20%">
        		IVSs	
        	</td>
        	<td colspan="2">
        		<input type="text" maxlength="7" name="IVSs" id="IVSs" size="5" onblur="isNumber(this.value,'IVSs',this)" value="${ecoCuore.IVSs}"/>
        		<input type="text" name="IVSsUnita" id="IVSsUnita" size="5" readonly="readonly" value="${ecoCuore.unitaMisuraBMode}"/>
        	</td>
        </tr>
 
        <tr>
        	<td style="width:20%">
        		LVIDd	
        	</td>
        	<td colspan="2">
        		<input type="text" maxlength="7" name="LVIDd" id="LVIDd" size="5" onblur="isNumber(this.value,'LVIDd',this)" value="${ecoCuore.LVIDd}"/>
        		<input type="text" name="LVIDdUnita" id="LVIDdUnita" size="5" readonly="readonly" value="${ecoCuore.unitaMisuraBMode}"/>
        	</td>
        </tr>     
     
        <tr class="odd">
        	<td style="width:20%">
        		LVIDs	
        	</td>
        	<td colspan="2">
        		<input type="text" maxlength="7" name="LVIDs" id="LVIDs" size="5" onblur="isNumber(this.value,'LVIDs',this)" value="${ecoCuore.LVIDs}"/>
        		<input type="text" name="LVIDsUnita" id="LVIDsUnita" size="5" readonly="readonly" value="${ecoCuore.unitaMisuraBMode}"/>
        	</td>
        </tr>    

        <tr>
        	<td style="width:20%">
        		LVWd	
        	</td>
        	<td colspan="2">
        		<input type="text" maxlength="7" name="LVWd" id="LVWd" size="5" onblur="isNumber(this.value,'LVWd',this)" value="${ecoCuore.LVWd}"/>
        		<input type="text" name="LVWdUnita" id="LVWdUnita" size="5" readonly="readonly" value="${ecoCuore.unitaMisuraBMode}"/>
        	</td>
        </tr> 

		<tr class="odd">
        	<td style="width:20%">
        		LVWs	
        	</td>
        	<td colspan="2">
        		<input type="text" maxlength="7" name="LVWs" id="LVWs" size="5" onblur="isNumber(this.value,'LVWs',this)" value="${ecoCuore.LVWs}"/>
        		<input type="text" name="LVWsUnita" id="LVWsUnita" size="5" readonly="readonly" value="${ecoCuore.unitaMisuraBMode}"/>
        	</td>
        </tr>

		<tr>
        	<td style="width:20%">
        		La/Ao	
        	</td>
        	<td colspan="2">
        		<input type="text" maxlength="7" name="laAo" id="LaAo" size="5" onblur="isNumber(this.value,'La/Ao',this)" value="${ecoCuore.laAo}" />
        		<input type="text" name="LaAoUnita" id="LaAoUnita" size="5" readonly="readonly" value="${ecoCuore.unitaMisuraBMode}"/>
        	</td>
        </tr>
        
        <tr class="odd">
        	<td style="width:20%">
        		LA	
        	</td>
        	<td colspan="2">
        		<input type="text" maxlength="7" name="LA" id="LA" size="5" onblur="isNumber(this.value,'LA',this)" value="${ecoCuore.LA}"/>
        		<input type="text" name="LAUnita" id="LAUnita" size="5" readonly="readonly" value="${ecoCuore.unitaMisuraBMode}"/>
        	</td>
        </tr>

		<tr>
        	<td style="width:20%">
        		FS%	
        	</td>
        	<td colspan="2">
        		<input type="text" maxlength="7" name="FS" id="FS" size="5" onblur="isNumber(this.value,'FS%',this)" value="${ecoCuore.FS}"/>
        		<input type="text" name="FSdUnita" id="FSUnita" size="5" readonly="readonly" value="${ecoCuore.unitaMisuraBMode}"/>
        	</td>
        </tr>
        
        <tr class="odd">
        	<td style="width:20%">
        		EF%
        	</td>
        	<td colspan="2">
        		<input type="text" maxlength="7" name="EF" id="EF" size="5" onblur="isNumber(this.value,'EF%',this)" value="${ecoCuore.EF}"/>
        		<input type="text" name="EFUnita" id="EFUnita" size="5" readonly="readonly" value="${ecoCuore.unitaMisuraBMode}"/>
        	</td>
        </tr>
        </table>
        
        <table class="tabella">
        <tr>
        	<th colspan="3">
        		Esame doppler
        	</th>  
		</tr>
		
		<tr>
        	<th colspan="3">
        		Aorta
        	</th>  
		</tr>
		
		<tr>
			<td>
				Flusso
			</td>
			<td colspan="2">
				<input type="radio" name="aortaFlusso" value="L"  id="aortaFlussoL" 
					<c:if test="${ecoCuore.aortaFlusso=='L'}">
	          			checked=checked 
					</c:if>
					> <label for="aortaFlussoL">Laminare in fase sistolica</label><br>
        		<input type="radio" name="aortaFlusso" value="T"   id="aortaFlussoT"  
					<c:if test="${ecoCuore.aortaFlusso=='T'}">
	          			checked=checked 
					</c:if>
					> <label for="aortaFlussoT">Turbolento</label><br>
			</td>
		</tr>
		
		<tr class="odd">
			<td>
				Velocit&agrave; massima 
			</td>
			<td colspan="2">
				<input type="text" maxlength="7" name="aortaVelocitaMax" id="aortaVelocitaMax" size="5" onblur="isNumber(this.value,'Velocità massima',this)" value="${ecoCuore.aortaVelocitaMax}"/>
				<input type="text" name="aortaVelocitaMaxUnita" id="aortaVelocitaMaxUnita" size="5" readonly="readonly" value="${ecoCuore.unitaMisuraDoppler}"/>
			</td>
		</tr>
		
		<tr>
			<td>
				Gradiente pressorio massimo (mmHg) 
			</td>
			<td colspan="2">
				<input type="text" maxlength="7" name="aortaGradiente" id="aortaGradiente" size="5" onblur="isNumber(this.value,'Gradiente pressorio massimo',this)" value="${ecoCuore.aortaGradiente}"/>
			</td>
		</tr>
		
		<tr class="odd">
			<td>
				Stenosi 
			</td>
			<td>
				<input type="radio" name="aortaStenosiScelta" id="aortaStenosiSceltaSI" value="true"  
        			<c:if test="${ecoCuore.aortaStenosi1!=null}">
	          			checked=checked 
					</c:if>
        		 onclick="document.getElementById('divaortaStenosi').style.display='block';document.getElementById('aortaStenosiSu').disabled=false;document.getElementById('aortaStenosiSu').checked=false;document.getElementById('aortaStenosiAo').disabled=false;document.getElementById('aortaStenosiAo').checked=false;document.getElementById('aortaStenosiSo').disabled=false;document.getElementById('aortaStenosiSo').checked=false;document.getElementById('aortaStenosiL').disabled=false;document.getElementById('aortaStenosiL').checked=false;document.getElementById('aortaStenosiM').disabled=false;document.getElementById('aortaStenosiM').checked=false;document.getElementById('aortaStenosiG').disabled=false;document.getElementById('aortaStenosiG').checked=false;" > <label for="aortaStenosiSceltaSI">Si</label><br>
        		<input type="radio" name="aortaStenosiScelta" value="false" id="aortaStenosiSceltaNO" 
        			<c:if test="${ecoCuore.aortaStenosi1==null}">
	          			checked=checked 
					</c:if>
        		onclick="document.getElementById('divaortaStenosi').style.display='none';document.getElementById('aortaStenosiSu').disabled=true;document.getElementById('aortaStenosiSu').checked=false;document.getElementById('aortaStenosiAo').disabled=true;document.getElementById('aortaStenosiAo').checked=false;document.getElementById('aortaStenosiSo').disabled=true;document.getElementById('aortaStenosiSo').checked=false;document.getElementById('aortaStenosiL').disabled=true;document.getElementById('aortaStenosiL').checked=false;document.getElementById('aortaStenosiM').disabled=true;document.getElementById('aortaStenosiM').checked=false;document.getElementById('aortaStenosiG').disabled=true;document.getElementById('aortaStenosiG').checked=false;" > <label for="aortaStenosiSceltaNO">No</label><br>
			</td>
			<td>
				<div id="divaortaStenosi"
					<c:if test="${ecoCuore.aortaStenosi1==null}">
						style="display:none;"
					</c:if>			
				>
				<input type="radio" name="aortaStenosi1" id="aortaStenosiSu" value="Su" id="aortaStenosiSu" 
					<c:if test="${ecoCuore.aortaStenosi1=='Su'}">
	          			checked=checked 
					</c:if>
					<c:if test="${ecoCuore.aortaStenosi1==null}">
	          			disabled=disabled
					</c:if>
				 > <label for="aortaStenosiSu">Sub aortica</label> <br/>
        		<input type="radio" name="aortaStenosi1" id="aortaStenosiAo" value="Ao" id="aortaStenosiAo" 
        			<c:if test="${ecoCuore.aortaStenosi1=='Ao'}">
	          			checked=checked 
					</c:if>
					<c:if test="${ecoCuore.aortaStenosi1==null}">
	          			disabled=disabled
					</c:if>
        		 > <label for="aortaStenosiAo">Aortica</label> <br/>
        		<input type="radio" name="aortaStenosi1" id="aortaStenosiSo" value="So"  id="aortaStenosiSo" 
        			<c:if test="${ecoCuore.aortaStenosi1=='So'}">
	          			checked=checked 
					</c:if>
					<c:if test="${ecoCuore.aortaStenosi1==null}">
	          			disabled=disabled
					</c:if>
        		  > <label for="aortaStenosiSo">Sopraortica</label> <br/><br/>
        		<input type="radio" name="aortaStenosi2" id="aortaStenosiL" value="L"  id="aortaStenosiL" 
        			<c:if test="${ecoCuore.aortaStenosi2=='L'}">
	          			checked=checked 
					</c:if>
					<c:if test="${ecoCuore.aortaStenosi2==null}">
	          			disabled=disabled
					</c:if>
        		   > <label for="aortaStenosiL">Lieve</label> <br/>
        		<input type="radio" name="aortaStenosi2" id="aortaStenosiM" value="M"  id="aortaStenosiM" 
        			<c:if test="${ecoCuore.aortaStenosi2=='M'}">
	          			checked=checked 
					</c:if>
					<c:if test="${ecoCuore.aortaStenosi2==null}">
	          			disabled=disabled
					</c:if>
        		   > <label for="aortaStenosiM">Moderata</label> <br/>
        		<input type="radio" name="aortaStenosi2" id="aortaStenosiG" value="G"  id="aortaStenosiG" 
        			<c:if test="${ecoCuore.aortaStenosi2=='G'}">
	          			checked=checked 
					</c:if>
					<c:if test="${ecoCuore.aortaStenosi2==null}">
	          			disabled=disabled
					</c:if>
        		   > <label for="aortaStenosiG">Grave</label> <br/>
        		   
        		   </div>
			</td>
		</tr>
		
		<tr>
			<td>
				Insufficienza 
			</td>
			<td>
				<input type="radio" name="aortaInsufficienzaScelta" id="aortaInsufficienzaSceltaSI" value="true" 
					<c:if test="${ecoCuore.aortaInsufficienza!=null}">
	          			checked=checked 
					</c:if>
					onclick="document.getElementById('divaortaInsufficienza').style.display='block';document.getElementById('aortaInsufficienzaL').disabled=false;document.getElementById('aortaInsufficienzaL').checked=false;document.getElementById('aortaInsufficienzaM').disabled=false;document.getElementById('aortaInsufficienzaM').checked=false;document.getElementById('aortaInsufficienzaG').disabled=false;document.getElementById('aortaInsufficienzaG').checked=false;" > <label for="aortaInsufficienzaSceltaSI">Si</label><br>
        		<input type="radio" name="aortaInsufficienzaScelta" value="false"  id="aortaInsufficienzaSceltaNO" 
					<c:if test="${ecoCuore.aortaInsufficienza==null}">
	          			checked=checked 
					</c:if>
					onclick="document.getElementById('divaortaInsufficienza').style.display='none';document.getElementById('divaortaInsufficienza').disabled=false;document.getElementById('aortaInsufficienzaL').disabled=true;document.getElementById('aortaInsufficienzaL').checked=false;document.getElementById('aortaInsufficienzaM').disabled=true;document.getElementById('aortaInsufficienzaM').checked=false;document.getElementById('aortaInsufficienzaG').disabled=true;document.getElementById('aortaInsufficienzaG').checked=false;" > <label for="aortaInsufficienzaSceltaNO">No</label><br>
			</td>
			<td>
				<div  id="divaortaInsufficienza"
					<c:if test="${ecoCuore.aortaInsufficienza==null}">
						style="display:none;"
					</c:if>			
				>
        		<input type="radio" name="aortaInsufficienza" id="aortaInsufficienzaL" value="L"  
        			<c:if test="${ecoCuore.aortaInsufficienza=='L'}">
	          			checked=checked 
					</c:if>
					<c:if test="${ecoCuore.aortaInsufficienza==null}">
	          			disabled=disabled
					</c:if>
        		     > <label for="aortaInsufficienzaL">Lieve</label> <br/>
        		<input type="radio" name="aortaInsufficienza" id="aortaInsufficienzaM" value="M"    
        			<c:if test="${ecoCuore.aortaInsufficienza=='M'}">
	          			checked=checked 
					</c:if>
					<c:if test="${ecoCuore.aortaInsufficienza==null}">
	          			disabled=disabled
					</c:if>
        		       > <label for="aortaInsufficienzaM">Moderata</label> <br/>
        		<input type="radio" name="aortaInsufficienza" id="aortaInsufficienzaG" value="G"    
        			<c:if test="${ecoCuore.aortaInsufficienza=='G'}">
	          			checked=checked 
					</c:if>
					<c:if test="${ecoCuore.aortaInsufficienza==null}">
	          			disabled=disabled
					</c:if>
        		       > <label for="aortaInsufficienzaG">Grave</label><br/>
        		</div>
			</td>
		</tr>
		
		<tr>
        	<th colspan="3">
        		Polmonare
        	</th>  
		</tr>
		
		<tr>
			<td>
				Flusso
			</td>
			<td colspan="2">
				<input type="radio" name="polmonareFlusso" value="L"   id="polmonareFlussoL" 
        			<c:if test="${ecoCuore.polmonareFlusso=='L'}">
	          			checked=checked 
					</c:if>
        		       > <label for="polmonareFlussoL">Laminare in fase sistolica</label><br>
        		<input type="radio" name="polmonareFlusso" value="T"  id="polmonareFlussoT"   
        			<c:if test="${ecoCuore.polmonareFlusso=='T'}">
	          			checked=checked 
					</c:if>
        		        > <label for="polmonareFlussoT">Turbolento</label><br>
			</td>
		</tr>
		
		<tr class="odd">
			<td>
				Velocit&agrave; massima 
			</td>
			<td colspan="2">
				<input type="text" maxlength="7" name="polmonareVelocitaMax" id="polmonareVelocitaMax" size="5" onblur="isNumber(this.value,'Velocità massima',this)" value="${ecoCuore.polmonareVelocitaMax}"/>
				<input type="text" name="polmonareVelocitaMaxUnita" id="polmonareVelocitaMaxUnita" size="5" readonly="readonly" value="${ecoCuore.unitaMisuraDoppler}"/>
			</td>
		</tr>
		
		<tr>
			<td>
				Gradiente pressorio massimo (mmHg) 
			</td>
			<td colspan="2">
				<input type="text" maxlength="7" name="polmonareGradiente" id="polmonareGradiente" size="5" onblur="isNumber(this.value,'Gradiente pressorio massimo',this)" value="${ecoCuore.polmonareGradiente}"/>
			</td>
		</tr>
		
		<tr class="odd">
			<td>
				Stenosi 
			</td>
			<td>
				<input type="radio" name="polmonareStenosiScelta" id="polmonareStenosiSceltaSI" value="true"  
        			<c:if test="${ecoCuore.polmonareStenosi!=null}">
	          			checked=checked 
					</c:if>
        		 onclick="document.getElementById('divpolmonareStenosi').style.display='block';document.getElementById('polmonareStenosiL').disabled=false;document.getElementById('polmonareStenosiL').checked=false;document.getElementById('polmonareStenosiM').disabled=false;document.getElementById('polmonareStenosiM').checked=false;document.getElementById('polmonareStenosiG').disabled=false;document.getElementById('polmonareStenosiG').checked=false;" > <label for="polmonareStenosiSceltaSI">Si</label><br>
        		<input type="radio" name="polmonareStenosiScelta" value="false" id="polmonareStenosiSceltaNO" 
        			<c:if test="${ecoCuore.polmonareStenosi==null}">
	          			checked=checked 
					</c:if>
        		onclick="document.getElementById('divpolmonareStenosi').style.display='none';document.getElementById('polmonareStenosiL').disabled=true;document.getElementById('polmonareStenosiL').checked=false;document.getElementById('polmonareStenosiM').disabled=true;document.getElementById('polmonareStenosiM').checked=false;document.getElementById('polmonareStenosiG').disabled=true;document.getElementById('polmonareStenosiG').checked=false;" > <label for="polmonareStenosiSceltaNO">No</label><br>
			</td>
			<td>
				<div id="divpolmonareStenosi"
					<c:if test="${ecoCuore.polmonareStenosi==null}">
						style="display:none;"
					</c:if>			
				>
        		<input type="radio" name="polmonareStenosi" id="polmonareStenosiL" value="L"  i
        			<c:if test="${ecoCuore.polmonareStenosi=='L'}">
	          			checked=checked 
					</c:if>
					<c:if test="${ecoCuore.polmonareStenosi==null}">
	          			disabled=disabled
					</c:if>
        		   > <label for="polmonareStenosiL">Lieve</label> <br/>
        		<input type="radio" name="polmonareStenosi" id="polmonareStenosiM" value="M" 
        			<c:if test="${ecoCuore.polmonareStenosi=='M'}">
	          			checked=checked 
					</c:if>
					<c:if test="${ecoCuore.polmonareStenosi==null}">
	          			disabled=disabled
					</c:if>
        		   > <label for="polmonareStenosiM">Moderata</label> <br/>
        		<input type="radio" name="polmonareStenosi" id="polmonareStenosiG" value="G"   
        			<c:if test="${ecoCuore.polmonareStenosi=='G'}">
	          			checked=checked 
					</c:if>
					<c:if test="${ecoCuore.polmonareStenosi==null}">
	          			disabled=disabled
					</c:if>
        		   > <label for="polmonareStenosiG">Grave</label> <br/>
        		   
        		   </div>
			</td>
		</tr>
		
		
		<tr>
			<td>
				Insufficienza 
			</td>
			<td>
				<input type="radio" name="polmonareInsufficienzaScelta" id="polmonareInsufficienzaSceltaSI" value="true" 
					<c:if test="${ecoCuore.polmonareInsufficienza!=null}">
	          			checked=checked 
					</c:if>
					onclick="document.getElementById('divpolmonareInsufficienza').style.display='block';document.getElementById('polmonareInsufficienzaL').disabled=false;document.getElementById('polmonareInsufficienzaL').checked=false;document.getElementById('polmonareInsufficienzaM').disabled=false;document.getElementById('polmonareInsufficienzaM').checked=false;document.getElementById('polmonareInsufficienzaG').disabled=false;document.getElementById('polmonareInsufficienzaG').checked=false;" > <label for="polmonareInsufficienzaSceltaSI">Si</label><br>
        		<input type="radio" name="polmonareInsufficienzaScelta" value="false"  id="polmonareInsufficienzaSceltaNO" 
					<c:if test="${ecoCuore.polmonareInsufficienza==null}">
	          			checked=checked 
					</c:if>
					onclick="document.getElementById('divpolmonareInsufficienza').style.display='none';document.getElementById('divpolmonareInsufficienza').disabled=false;document.getElementById('polmonareInsufficienzaL').disabled=true;document.getElementById('polmonareInsufficienzaL').checked=false;document.getElementById('polmonareInsufficienzaM').disabled=true;document.getElementById('polmonareInsufficienzaM').checked=false;document.getElementById('polmonareInsufficienzaG').disabled=true;document.getElementById('polmonareInsufficienzaG').checked=false;" > <label for="polmonareInsufficienzaSceltaNO">No</label><br>
			</td>
			<td>
				<div  id="divpolmonareInsufficienza"
					<c:if test="${ecoCuore.polmonareInsufficienza==null}">
						style="display:none;"
					</c:if>			
				>
        		<input type="radio" name="polmonareInsufficienza" id="polmonareInsufficienzaL" value="L"  
        			<c:if test="${ecoCuore.polmonareInsufficienza=='L'}">
	          			checked=checked 
					</c:if>
					<c:if test="${ecoCuore.polmonareInsufficienza==null}">
	          			disabled=disabled
					</c:if>
        		     > <label for="polmonareInsufficienzaL">Lieve</label> <br/>
        		<input type="radio" name="polmonareInsufficienza" id="polmonareInsufficienzaM" value="M"    
        			<c:if test="${ecoCuore.polmonareInsufficienza=='M'}">
	          			checked=checked 
					</c:if>
					<c:if test="${ecoCuore.polmonareInsufficienza==null}">
	          			disabled=disabled
					</c:if>
        		       > <label for="polmonareInsufficienzaM">Moderata</label> <br/>
        		<input type="radio" name="polmonareInsufficienza" id="polmonareInsufficienzaG" value="G"    
        			<c:if test="${ecoCuore.polmonareInsufficienza=='G'}">
	          			checked=checked 
					</c:if>
					<c:if test="${ecoCuore.polmonareInsufficienza==null}">
	          			disabled=disabled
					</c:if>
        		       > <label for="polmonareInsufficienzaG">Grave</label> <br/>
        		</div>
			</td>
		</tr>
		
		<tr>
        	<th colspan="3">
        		LVOT
        	</th>  
		</tr>
		
		<tr>
			<td>
				Tipo
			</td>
			<td colspan="2">
				<input type="radio" name="lvotTipo" value="N"  id="lvotTipoN" 
        			<c:if test="${ecoCuore.lvotTipo=='N'}">
	          			checked=checked 
					</c:if>
					 > <label for="lvotTipoN">Normale</label><br>
        		<input type="radio" name="lvotTipo" value="O"  id="lvotTipoO" 
        			<c:if test="${ecoCuore.lvotTipo=='O'}">
	          			checked=checked 
					</c:if>
					   > <label for="lvotTipoO">Ostruito</label><br>
			</td>
		</tr>
		
		<tr class="odd">
			<td>
				Velocit&agrave; massima 
			</td>
			<td colspan="2">
				<input type="text" maxlength="7" name="lvotVelocitaMax" id="lvotVelocitaMax" size="5" onblur="isNumber(this.value,'Velocità massima',this)" value="${ecoCuore.lvotVelocitaMax}"/>
				<input type="text" name="lvotVelocitaMaxUnita" id="lvotVelocitaMaxUnita" size="5" readonly="readonly" value="${ecoCuore.unitaMisuraDoppler}"/>
			</td>
		</tr>
		
		<tr>
			<td>
				Gradiente pressorio massimo (mmHg) 
			</td>
			<td colspan="2">
				<input type="text" maxlength="7" name="lvotGradiente" id="lvotGradiente" size="5" onblur="isNumber(this.value,'Gradiente pressorio massimo',this)" value="${ecoCuore.lvotGradiente}"/>
			</td>
		</tr>
		
		<tr>
        	<th colspan="3">
        		Mitrale
        	</th>  
		</tr>
		
		<tr>
			<td>
				Flusso Laminare in fase sistolica 
			</td>
			<td colspan="2">
				<input type="radio" name="lvotFlussoLaminare" value="true" id="lvotFlussoLaminareS" 
        			<c:if test="${ecoCuore.lvotFlussoLaminare==true}">
	          			checked=checked 
					</c:if>
					   > <label for="lvotFlussoLaminareS">Si</label><br>
        		<input type="radio" name="lvotFlussoLaminare" value="false" id="lvotFlussoLaminareN" 
        			<c:if test="${ecoCuore.lvotFlussoLaminare==false}">
	          			checked=checked 
					</c:if>
					     > <label for="lvotFlussoLaminareN">No</label><br>
			</td>
		</tr>
		
		<tr class="odd">
			<td>
				Velocit&agrave; onda E
			</td>
			<td colspan="2">
				<input type="text" maxlength="7" name="lvotVelocitaOndaE" id="lvotVelocitaOndaE" size="5" onblur="isNumber(this.value,'Velocità onda E',this);calcolaRapporto();" value="${ecoCuore.lvotVelocitaOndaE}"/>
				<input type="text" name="lvotVelocitaOndaEUnita" id="lvotVelocitaOndaEUnita" size="5" readonly="readonly" value="${ecoCuore.unitaMisuraDoppler}"/>
			</td>
		</tr>
		
		<tr>
			<td>
				Velocit&agrave; onda A
			</td>
			<td colspan="2">
				<input type="text" maxlength="7" name="lvotVelocitaOndaA" id="lvotVelocitaOndaA" size="5" onblur="isNumber(this.value,'Velocità onda A',this);calcolaRapporto()" value="${ecoCuore.lvotVelocitaOndaA}"/>
				<input type="text" name="lvotVelocitaOndaAUnita" id="lvotVelocitaOndaAUnita" size="5" readonly="readonly" value="${ecoCuore.unitaMisuraDoppler}"/>
			</td>
		</tr>
		
		<tr class="odd">
			<td>
				Rapporto E/A
			</td>
			<td colspan="2">
				<input type="text" name="lvotRapportoEA" id="lvotRapportoEA" readonly="readonly" size="16"  value="${ecoCuore.lvotRapportoEA}"/>
			</td>
		</tr>

		<tr>
			<td>
				Velocit&agrave; massima di rigurgito
			</td>
			<td colspan="2">
				<input type="text" maxlength="7" name="lvotVelocitaMaxRigurgito" id="lvotVelocitaMaxRigurgito" size="5" onblur="isNumber(this.value,'Velocità massima di rigurgito',this)" value="${ecoCuore.lvotVelocitaMaxRigurgito}"/>
				<input type="text" name="lvotVelocitaMaxRigurgitoUnita" id="lvotVelocitaMaxRigurgitoUnita" size="5" readonly="readonly" value="${ecoCuore.unitaMisuraDoppler}"/>
			</td>
		</tr>
		
		<tr class="odd">
			<td>
				Gradiente pressorio massimo (mmHg) 
			</td>
			<td colspan="2">
				<input type="text" maxlength="7" name="lvotMitraleGradiente" id="lvotMitraleGradiente" size="5" onblur="isNumber(this.value,'Gradiente pressorio massimo',this)" value="${ecoCuore.lvotMitraleGradiente}"/>
			</td>
		</tr>
		
		<tr>
			<td>
				Stenosi 
			</td>
			<td>
				<input type="radio" name="lvotStenosiScelta"  id="lvotStenosiSceltaSI" value="true"  
        			<c:if test="${ecoCuore.lvotStenosi!=null}">
	          			checked=checked 
					</c:if>
        		 onclick="document.getElementById('divlvotStenosi').style.display='block';document.getElementById('lvotStenosiL').disabled=false;document.getElementById('lvotStenosiL').checked=false;document.getElementById('lvotStenosiM').disabled=false;document.getElementById('lvotStenosiM').checked=false;document.getElementById('lvotStenosiG').disabled=false;document.getElementById('lvotStenosiG').checked=false;" > <label for="lvotStenosiSceltaSI">Si</label><br>
        		<input type="radio" name="lvotStenosiScelta" value="false" id="lvotStenosiSceltaNO" 
        			<c:if test="${ecoCuore.lvotStenosi==null}">
	          			checked=checked 
					</c:if>
        		onclick="document.getElementById('divlvotStenosi').style.display='none';document.getElementById('lvotStenosiL').disabled=true;document.getElementById('lvotStenosiL').checked=false;document.getElementById('lvotStenosiM').disabled=true;document.getElementById('lvotStenosiM').checked=false;document.getElementById('lvotStenosiG').disabled=true;document.getElementById('lvotStenosiG').checked=false;" > <label for="lvotStenosiSceltaNO">No</label><br>
			</td>
			<td>
				<div id="divlvotStenosi"
					<c:if test="${ecoCuore.lvotStenosi==null}">
						style="display:none;"
					</c:if>			
				>
        		<input type="radio" name="lvotStenosi" id="lvotStenosiL" value="L"  
        			<c:if test="${ecoCuore.lvotStenosi=='L'}">
	          			checked=checked 
					</c:if>
					<c:if test="${ecoCuore.lvotStenosi==null}">
	          			disabled=disabled
					</c:if>
        		   > <label for="lvotStenosiL">Lieve</label> <br/>
        		<input type="radio" name="lvotStenosi" id="lvotStenosiM" value="M" 
        			<c:if test="${ecoCuore.lvotStenosi=='M'}">
	          			checked=checked 
					</c:if>
					<c:if test="${ecoCuore.lvotStenosi==null}">
	          			disabled=disabled
					</c:if>
        		   > <label for="lvotStenosiM">Moderata</label> <br/>
        		<input type="radio" name="lvotStenosi" id="lvotStenosiG" value="G" 
        			<c:if test="${ecoCuore.lvotStenosi=='G'}">
	          			checked=checked 
					</c:if>
					<c:if test="${ecoCuore.lvotStenosi==null}">
	          			disabled=disabled
					</c:if>
        		   > <label for="lvotStenosiG">Grave</label> <br/>
        		   
        		   </div>
			</td>
		</tr>
		
		<tr class="odd">
			<td>
				Insufficienza 
			</td>
			<td>
				<input type="radio" name="lvotInsufficienzaScelta" id="lvotInsufficienzaSceltaSI" value="true" 
					<c:if test="${ecoCuore.lvotInsufficienza!=null}">
	          			checked=checked 
					</c:if>
					onclick="document.getElementById('divlvotInsufficienza').style.display='block';document.getElementById('lvotInsufficienzaL').disabled=false;document.getElementById('lvotInsufficienzaL').checked=false;document.getElementById('lvotInsufficienzaM').disabled=false;document.getElementById('lvotInsufficienzaM').checked=false;document.getElementById('lvotInsufficienzaG').disabled=false;document.getElementById('lvotInsufficienzaG').checked=false;" > <label for="lvotInsufficienzaSceltaSI">Si</label><br>
        		<input type="radio" name="lvotInsufficienzaScelta" value="false" id="lvotInsufficienzaSceltaNO"  
					<c:if test="${ecoCuore.lvotInsufficienza==null}">
	          			checked=checked 
					</c:if>
					onclick="document.getElementById('divlvotInsufficienza').style.display='none';document.getElementById('divlvotInsufficienza').disabled=false;document.getElementById('lvotInsufficienzaL').disabled=true;document.getElementById('lvotInsufficienzaL').checked=false;document.getElementById('lvotInsufficienzaM').disabled=true;document.getElementById('lvotInsufficienzaM').checked=false;document.getElementById('lvotInsufficienzaG').disabled=true;document.getElementById('lvotInsufficienzaG').checked=false;" > <label for="lvotInsufficienzaSceltaNO">No</label><br>
			</td>
			<td>
				<div  id="divlvotInsufficienza"
					<c:if test="${ecoCuore.lvotInsufficienza==null}">
						style="display:none;"
					</c:if>			
				>
        		<input type="radio" name="lvotInsufficienza" id="lvotInsufficienzaL" value="L" 
        			<c:if test="${ecoCuore.lvotInsufficienza=='L'}">
	          			checked=checked 
					</c:if>
					<c:if test="${ecoCuore.lvotInsufficienza==null}">
	          			disabled=disabled
					</c:if>
        		     > <label for="lvotInsufficienzaL">Lieve</label> <br/>
        		<input type="radio" name="lvotInsufficienza" id="lvotInsufficienzaM" value="M"   
        			<c:if test="${ecoCuore.lvotInsufficienza=='M'}">
	          			checked=checked 
					</c:if>
					<c:if test="${ecoCuore.lvotInsufficienza==null}">
	          			disabled=disabled
					</c:if>
        		       > <label for="lvotInsufficienzaM">Moderata</label> <br/>
        		<input type="radio" name="lvotInsufficienza" id="lvotInsufficienzaG" value="G"  
        			<c:if test="${ecoCuore.lvotInsufficienza=='G'}">
	          			checked=checked 
					</c:if>
					<c:if test="${ecoCuore.lvotInsufficienza==null}">
	          			disabled=disabled
					</c:if>
        		       > <label for="lvotInsufficienzaG">Grave</label> <br/>
        		</div>
			</td>
		</tr>

		<tr>
        	<th colspan="3">
        		RVOT
        	</th>  
		</tr>
		
		<tr>
			<td>
				Tipo
			</td>
			<td colspan="2">
				<input type="radio" name="rvotTipo" value="N"  id="rvotTipoN" 
        			<c:if test="${ecoCuore.rvotTipo=='N'}">
	          			checked=checked 
					</c:if>
					   > <label for="rvotTipoN">Normale</label><br>
        		<input type="radio" name="rvotTipo" value="O"  id="rvotTipoO" 
        			<c:if test="${ecoCuore.rvotTipo=='O'}">
	          			checked=checked 
					</c:if>
					   > <label for="rvotTipoO">Ostruito</label><br>
			</td>
		</tr>
		
		<tr class="odd">
			<td>
				Velocit&agrave; massima 
			</td>
			<td colspan="2">
				<input type="text" maxlength="7" name="rvotVelocitaMax" id="rvotVelocitaMax" size="5" onblur="isNumber(this.value,'Velocità massima',this)" value="${ecoCuore.rvotVelocitaMax}"/>
				<input type="text" name="rvotVelocitaMaxUnita" id="rvotVelocitaMaxUnita" size="5" readonly="readonly" value="${ecoCuore.unitaMisuraDoppler}"/>
			</td>
		</tr>
		
		<tr>
			<td>
				Gradiente pressorio massimo (mmHg) 
			</td>
			<td colspan="2">
				<input type="text" maxlength="7" name="rvotGradiente" id="rvotGradiente" size="5" onblur="isNumber(this.value,'Gradiente pressorio massimo',this)" value="${ecoCuore.rvotGradiente}"/>
			</td>
		</tr>
		
		<tr>
        	<th colspan="3">
        		Tricuspide
        	</th>  
		</tr>
		
		<tr>
			<td>
				Flusso Laminare in fase sistolica 
			</td>
			<td colspan="2">
				<input type="radio" name="rvotFlussoLaminare" value="true" id="rvotFlussoLaminareS" 
        			<c:if test="${ecoCuore.rvotFlussoLaminare==true}">
	          			checked=checked 
					</c:if>
					     > <label for="rvotFlussoLaminareS">Si</label><br>
        		<input type="radio" name="rvotFlussoLaminare" value="false" id="rvotFlussoLaminareN" 
        			<c:if test="${ecoCuore.rvotFlussoLaminare==false}">
	          			checked=checked 
					</c:if>
					       > <label for="rvotFlussoLaminareN">No</label><br>
			</td>
		</tr>
		
		<tr class="odd">
			<td>
				Velocit&agrave; massima di rigurgito
			</td>
			<td colspan="2">
				<input type="text" maxlength="7" name="rvotVelocitaMaxRigurgito" id="rvotVelocitaMaxRigurgito" size="5" onblur="isNumber(this.value,'Velocità massima di rigurgito',this)" value="${ecoCuore.rvotVelocitaMaxRigurgito}"/>
				<input type="text" name="rvotVelocitaMaxRigurgitoUnita" id="rvotVelocitaMaxRigurgitoUnita" size="5" readonly="readonly" value="${ecoCuore.unitaMisuraDoppler}"/>
			</td>
		</tr>
		
		<tr>
			<td>
				Gradiente pressorio massimo (mmHg) 
			</td>
			<td colspan="2">
				<input type="text" maxlength="7" name="rvotTricuspideGradiente" id="rvotTricuspideGradiente" size="5" onblur="isNumber(this.value,'Gradiente pressorio massimo',this)" value="${ecoCuore.rvotTricuspideGradiente}"/>
			</td>
		</tr>
		
		<tr class="odd">
			<td>
				Stenosi 
			</td>
			<td>
				<input type="radio" name="rvotStenosiScelta" id="rvotStenosiSceltaSI" value="true"  
        			<c:if test="${ecoCuore.rvotStenosi!=null}">
	          			checked=checked 
					</c:if>
        		 onclick="document.getElementById('divrvotStenosi').style.display='block';document.getElementById('rvotStenosiL').disabled=false;document.getElementById('rvotStenosiL').checked=false;document.getElementById('rvotStenosiM').disabled=false;document.getElementById('rvotStenosiM').checked=false;document.getElementById('rvotStenosiG').disabled=false;document.getElementById('rvotStenosiG').checked=false;" > <label for="rvotStenosiSceltaSI">Si</label><br>
        		<input type="radio" name="rvotStenosiScelta" value="false" id="rvotStenosiSceltaNO" 
        			<c:if test="${ecoCuore.rvotStenosi==null}">
	          			checked=checked 
					</c:if>
        		onclick="document.getElementById('divrvotStenosi').style.display='none';document.getElementById('rvotStenosiL').disabled=true;document.getElementById('rvotStenosiL').checked=false;document.getElementById('rvotStenosiM').disabled=true;document.getElementById('rvotStenosiM').checked=false;document.getElementById('rvotStenosiG').disabled=true;document.getElementById('rvotStenosiG').checked=false;" > <label for="rvotStenosiSceltaNO">No</label><br>
			</td>
			<td>
				<div id="divrvotStenosi"
					<c:if test="${ecoCuore.rvotStenosi==null}">
						style="display:none;"
					</c:if>			
				>
        		<input type="radio" name="rvotStenosi" id="rvotStenosiL" value="L"  
        			<c:if test="${ecoCuore.rvotStenosi=='L'}">
	          			checked=checked 
					</c:if>
					<c:if test="${ecoCuore.rvotStenosi==null}">
	          			disabled=disabled
					</c:if>
        		   > <label for="rvotStenosiL">Lieve</label> <br/>
        		<input type="radio" name="rvotStenosi" id="rvotStenosiM" value="M"  
        			<c:if test="${ecoCuore.rvotStenosi=='M'}">
	          			checked=checked 
					</c:if>
					<c:if test="${ecoCuore.rvotStenosi==null}">
	          			disabled=disabled
					</c:if>
        		   > <label for="rvotStenosiM">Moderata</label> <br/>
        		<input type="radio" name="rvotStenosi" id="rvotStenosiG" value="G" 
        			<c:if test="${ecoCuore.rvotStenosi=='G'}">
	          			checked=checked 
					</c:if>
					<c:if test="${ecoCuore.rvotStenosi==null}">
	          			disabled=disabled
					</c:if>
        		   > <label for="rvotStenosiG">Grave</label> <br/>
        		   
        		   </div>
			</td>
		</tr>
		
		<tr>
			<td>
				Insufficienza 
			</td>
			<td>
				<input type="radio" name="rvotInsufficienzaScelta" id="rvotInsufficienzaSceltaSI" value="true" 
					<c:if test="${ecoCuore.rvotInsufficienza!=null}">
	          			checked=checked 
					</c:if>
					onclick="document.getElementById('divrvotInsufficienza').style.display='block';document.getElementById('rvotInsufficienzaL').disabled=false;document.getElementById('rvotInsufficienzaL').checked=false;document.getElementById('rvotInsufficienzaM').disabled=false;document.getElementById('rvotInsufficienzaM').checked=false;document.getElementById('rvotInsufficienzaG').disabled=false;document.getElementById('rvotInsufficienzaG').checked=false;" > <label for="rvotInsufficienzaSceltaSI">Si</label><br>
        		<input type="radio" name="rvotInsufficienzaScelta" value="false"  id="rvotInsufficienzaSceltaNO" 
					<c:if test="${ecoCuore.rvotInsufficienza==null}">
	          			checked=checked 
					</c:if>
					onclick="document.getElementById('divrvotInsufficienza').style.display='none';document.getElementById('divrvotInsufficienza').disabled=false;document.getElementById('rvotInsufficienzaL').disabled=true;document.getElementById('rvotInsufficienzaL').checked=false;document.getElementById('rvotInsufficienzaM').disabled=true;document.getElementById('rvotInsufficienzaM').checked=false;document.getElementById('rvotInsufficienzaG').disabled=true;document.getElementById('rvotInsufficienzaG').checked=false;" > <label for="rvotInsufficienzaSceltaNO">No</label><br>
			</td>
			<td>
				<div  id="divrvotInsufficienza"
					<c:if test="${ecoCuore.rvotInsufficienza==null}">
						style="display:none;"
					</c:if>			
				>
        		<input type="radio" name="rvotInsufficienza" id="rvotInsufficienzaL" value="L"
        			<c:if test="${ecoCuore.rvotInsufficienza=='L'}">
	          			checked=checked 
					</c:if>
					<c:if test="${ecoCuore.rvotInsufficienza==null}">
	          			disabled=disabled
					</c:if>
        		     > <label for="rvotInsufficienzaL">Lieve</label> <br/>
        		<input type="radio" name="rvotInsufficienza" id="rvotInsufficienzaM" value="M"
        			<c:if test="${ecoCuore.rvotInsufficienza=='M'}">
	          			checked=checked 
					</c:if>
					<c:if test="${ecoCuore.rvotInsufficienza==null}">
	          			disabled=disabled
					</c:if>
        		       > <label for="rvotInsufficienzaM">Moderata</label> <br/>
        		<input type="radio" name="rvotInsufficienza" id="rvotInsufficienzaG" value="G"
        			<c:if test="${ecoCuore.rvotInsufficienza=='G'}">
	          			checked=checked 
					</c:if>
					<c:if test="${ecoCuore.rvotInsufficienza==null}">
	          			disabled=disabled
					</c:if>
        		       > <label for="rvotInsufficienzaG">Grave</label> <br/>
        		</div>
			</td>
		</tr>
		
		<tr>
        	<th colspan="3">
        		Conclusioni(diagnosi)
        	</th>
		</tr>
		
    		
    	<c:set var="i" value='4'/>
    	<tr>
			<c:forEach var="d" items="${diagnosi}">
				<td>
    				<input type="checkbox" id="diagnosi_${d.id}" name="diagnosi_${d.id}"
    				<c:forEach var="dTemp" items="${ecoCuore.lookupEcoCuoreDiagnosis}">
    					<c:if test="${d.id==dTemp.id}">
    						checked=checked
    					</c:if>
					</c:forEach>
    				
    				/> <label for="diagnosi_${d.id}">${d.descrizione}</label> <br/>
            	</td>
            	<c:if test="${i%3==0}">
            		</tr>
            		<c:if test="${i%6==0}">
            			<tr class="odd">
            		</c:if>
            		<c:if test="${i%6>0}">
            			<tr>
            		</c:if>
            	</c:if>
            	<c:set var="i" value='${i+1}'/>
            </c:forEach>
            
        </tr>
    	
        <tr class='even'>
    		<td colspan="3"> 
    			<font color="red">* </font> Campi obbligatori
				<br/>   			
    			<input type="button" value="Modifica" onclick="checkform(document.getElementById('form'))"/>
    			<input type="button" value="Annulla" onclick="location.href='vam.cc.diagnosticaImmagini.ecoCuore.List.us'">
    		</td>
        </tr>
	</table>
</form>