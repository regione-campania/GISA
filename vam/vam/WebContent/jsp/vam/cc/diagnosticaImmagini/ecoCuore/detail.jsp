<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>


    
    <jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
    <h4 class="titolopagina">
     		Dettaglio Eco-Cuore
    </h4>
    
    <table class="tabella">
    	
    	<tr>
        	<th colspan="3">
        		Informazioni preliminari
        	</th>
        </tr>
    	<tr>
    		<td style="width:50%">
    			 Data Richiesta:&nbsp;
    			 <fmt:formatDate type="date" value="${ecoCuore.dataRichiesta}" pattern="dd/MM/yyyy" var="dataRichiesta"/> 		
    			 ${dataRichiesta}
    		</td>
    		<td style="width:50%">
    			 Data Esito:&nbsp;
    			 <fmt:formatDate type="date" value="${ecoCuore.dataEsito}" pattern="dd/MM/yyyy" var="dataEsito"/> 		
    			 ${dataEsito}
    		</td>
        </tr>
        
        <tr>
        	<th colspan="3">
        		Impostazione unit&agrave; di misura
        	</th>
        </tr>
        <tr>
        	<td>
        		Esame B-Mode/M-Mode 
        	</td>
        	<td colspan="2">
        		${ecoCuore.unitaMisuraBMode}
        	</td>
        </tr>
        <tr class="odd">
        	<td>
        		Esame Doppler 
        	</td>
        	<td colspan="2">
        		${ecoCuore.unitaMisuraDoppler}
        	</td>
        </tr>
        
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
			
			<td style="width:33%">				
				${tipo.descrizione}
			</td>
	    
			<td style="width:33%">
					<c:if test="${esitoNormale=='YES'}">
  		          		Normale
    				</c:if>
					<c:if test="${anomaliePresenti=='YES'}">
  		          		Anormale 
    				</c:if>
					<c:if test="${esitoNormale=='NO' && anomaliePresenti=='NO'}">
  		          		Non esaminato
					</c:if>
			</td>			
			<td style="width:34%">
				<div id="_${i}" 
								<c:if test="${anomaliePresenti=='NO'}">
									style="display:none;"	
								</c:if>
				>	
					<c:forEach items="${tipo.lookupEcoCuoreAnomalias}" var="anomaliaTipo" >
						
						<c:set var="superesitoDaStampato" value="NO"/>
							
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
											<c:if test="${checked=='SI'}">
												${anomaliaTipo.descrizione} <br/>
											</c:if>
									</c:when>							
									<c:otherwise>								
										<c:forEach items="${tipo.lookupEcoCuoreAnomalias}" var="tempAnomaliaTipo" >	
										
											<c:if test="${tempAnomaliaTipo.lookupEcoCuoreAnomalia.id == anomaliaTipo.id}">
												<c:set var="isIn" value="YES"/>	
												
												<c:forEach items="${ecoCuoreEsitos}" var="esito">
													<c:if test="${esito.lookupEcoCuoreAnomalia.id == tempAnomaliaTipo.id}">
														<c:set var="checked" value="SI"></c:set>
													</c:if>
												</c:forEach>
												
													<c:if test="${checked=='SI' && superesitoDaStampato == 'NO'}">
														<c:set var="superesitoDaStampato" value="SI"/>
														<label><b><i>${anomaliaTipo.descrizione}</i></b></label><br/>&nbsp;&nbsp;&nbsp;&nbsp;${tempAnomaliaTipo.descrizione}
														<br/>
														<c:set var="checked" value="NO"></c:set>
														</c:if>
													<c:if test="${checked == 'SI' && superesitoDaStampato == 'SI'}">
														&nbsp;&nbsp;&nbsp;&nbsp;${tempAnomaliaTipo.descrizione}	
														<br/>												
														<c:set var="checked" value="NO"/>
													</c:if>
											</c:if>				
										</c:forEach>
										
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
        
        <tr>
        	<th colspan="3">
        		Altre informazioni
        	</th>
        </tr>
        
        <tr>
        	<td>
        		Neoformazione base cardiaca
        	</td>
        	<td colspan="2">
        			<c:if test="${ecoCuore.neoformazioneBaseCardiaca==true}">
  		          		Si
    				</c:if>
    				<c:if test="${ecoCuore.neoformazioneBaseCardiaca==false}">
  		          		No
    				</c:if>
        	</td>
        </tr>
        
        <tr class="odd">
        	<td>
        		Neoformazione auricola DX
        	</td>
        	<td colspan="2">
        			<c:if test="${ecoCuore.neoformazioneAuricolaDx==true}">
  		          		Si
    				</c:if>
    				<c:if test="${ecoCuore.neoformazioneAuricolaDx==false}">
  		          		No
    				</c:if>
        	</td>
        </tr>
        
        <tr>
        	<td>
        		Difetto interventricolare
        	</td>
        	<td colspan="2">
        			<c:if test="${ecoCuore.difettoInterventricolare==true}">
	          			Si, Flusso: ${ecoCuore.flussoDifettoInterventricolare}
					</c:if>
					<c:if test="${ecoCuore.difettoInterventricolare==false}">
	          			No
					</c:if>
        	</td>
        </tr>
        
        <tr class="odd">
        	<td>
        		Difetto interatriale
        	</td>
        	<td colspan="2">
        			<c:if test="${ecoCuore.difettoInteratriale==true}">
	          			Si, Flusso: ${ecoCuore.flussoDifettoInteratriale}
					</c:if>
					<c:if test="${ecoCuore.difettoInteratriale==false}">
	          			No
					</c:if>
        	</td>
        </tr>
        
        <tr>
        	<td>
        		Dotto arterioso pervio
        	</td>
        	<td colspan="2">
        			<c:if test="${ecoCuore.dottoArteriosoPervio==true}">
	          			Si
					</c:if>
					<c:if test="${ecoCuore.dottoArteriosoPervio==false}">
	          			No
					</c:if>
        	</td>
        </tr>
        
        <tr class="odd">
        	<td>
        		Versamento pericardico
        	</td>
        	<td colspan="2">
        			<c:if test="${ecoCuore.versamentoPericardico==true}">
	          			Si
					</c:if>
					<c:if test="${ecoCuore.versamentoPericardico==false}">
	          			No
					</c:if>
        	</td>
        </tr>
        
        <tr>
        	<td>
        		Versamento toracico
        	</td>
        	<td colspan="2">
        			<c:if test="${ecoCuore.versamentoToracico==true}">
	          			Si
					</c:if>
					<c:if test="${ecoCuore.versamentoToracico==false}">
	          			No
					</c:if>
        	</td>
        </tr>
        
        <tr>
        	<th colspan="3">
        		L'esame ecocardiografico bidimensionale ed M-mode ha permesso la rilevazione dei seguenti parametri
        	</th>
        </tr>
        
        <tr>
        	<td>
        		IVSd	
        	</td>
        	<td colspan="2">
        		<c:if test="${ecoCuore.IVSd!=null && ecoCuore.IVSd!=''}">
        			${ecoCuore.IVSd} ${ecoCuore.unitaMisuraBMode}
        		</c:if>
        	</td>
        </tr>
        
        <tr class="odd">
        	<td>
        		IVSs	
        	</td>
        	<td colspan="2">
        		<c:if test="${ecoCuore.IVSs!=null && ecoCuore.IVSs!=''}">
        			${ecoCuore.IVSs} ${ecoCuore.unitaMisuraBMode}
        		</c:if>	
        	</td>
        </tr>
 
        <tr>
        	<td>
        		LVIDd	
        	</td>
        	<td colspan="2">
        		<c:if test="${ecoCuore.LVIDd!=null && ecoCuore.LVIDd!=''}">
        			${ecoCuore.LVIDd} ${ecoCuore.unitaMisuraBMode}
        		</c:if>
        	</td>
        </tr>     
     
        <tr class="odd">
        	<td>
        		LVIDs	
        	</td>
        	<td colspan="2">
        		<c:if test="${ecoCuore.LVIDs!=null && ecoCuore.LVIDs!=''}">
        			${ecoCuore.LVIDs} ${ecoCuore.unitaMisuraBMode}
        		</c:if>
        	</td>
        </tr>    

        <tr>
        	<td>
        		LVWd	
        	</td>
        	<td colspan="2">
        		<c:if test="${ecoCuore.LVWd!=null && ecoCuore.LVWd!=''}">
        			${ecoCuore.LVWd} ${ecoCuore.unitaMisuraBMode}
        		</c:if>
        	</td>
        </tr> 

		<tr class="odd">
        	<td>
        		LVWs	
        	</td>
        	<td colspan="2">
        		<c:if test="${ecoCuore.LVWs!=null && ecoCuore.LVWs!=''}">
        			${ecoCuore.LVWs} ${ecoCuore.unitaMisuraBMode}
        		</c:if>
        	</td>
        </tr>

		<tr>
        	<td>
        		La/Ao	
        	</td>
        	<td colspan="2">
        		<c:if test="${ecoCuore.laAo!=null && ecoCuore.laAo!=''}">
        			 ${ecoCuore.laAo} ${ecoCuore.unitaMisuraBMode}
        		</c:if>
        	</td>
        </tr>
        
        <tr class="odd">
        	<td>
        		LA	
        	</td>
        	<td colspan="2">
        		<c:if test="${ecoCuore.LA!=null && ecoCuore.LA!=''}">
        			${ecoCuore.LA} ${ecoCuore.unitaMisuraBMode}
        		</c:if>
        	</td>
        </tr>

		<tr>
        	<td>
        		FS%	
        	</td>
        	<td colspan="2">
        		<c:if test="${ecoCuore.FS!=null && ecoCuore.FS!=''}">
        			${ecoCuore.FS} ${ecoCuore.unitaMisuraBMode}
        		</c:if>
        	</td>
        </tr>
        
        <tr class="odd">
        	<td>
        		EF%
        	</td>
        	<td colspan="2">
        		<c:if test="${ecoCuore.EF!=null && ecoCuore.EF!=''}">
        			${ecoCuore.EF} ${ecoCuore.unitaMisuraBMode}
        		</c:if>
        	</td>
        </tr>
        
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
					<c:if test="${ecoCuore.aortaFlusso=='L'}">
	          			Laminare in fase sistolica
					</c:if>
					<c:if test="${ecoCuore.aortaFlusso=='T'}">
	          			Turbolento
					</c:if>
			</td>
		</tr>
		
		<tr class="odd">
			<td>
				Velocit&agrave; massima 
			</td>
			<td colspan="2">
				<c:if test="${ecoCuore.aortaVelocitaMax!=null && ecoCuore.aortaVelocitaMax!=''}">
					${ecoCuore.aortaVelocitaMax} ${ecoCuore.unitaMisuraDoppler}
				</c:if>
			</td>
		</tr>
		
		<tr>
			<td>
				Gradiente pressorio massimo (mmHg) 
			</td>
			<td colspan="2">
				${ecoCuore.aortaGradiente}
			</td>
		</tr>
		
		<tr class="odd">
			<td>
				Stenosi 
			</td>
			<td colspan="2">
        			<c:if test="${ecoCuore.aortaStenosi1!=null || ecoCuore.aortaStenosi2!=null}">
					<c:if test="${ecoCuore.aortaStenosi1=='Su'}">
	          			Sub aortica 
					</c:if>
					<c:if test="${ecoCuore.aortaStenosi1=='Ao'}">
	          			Aortica
					</c:if>
					<c:if test="${ecoCuore.aortaStenosi1=='So'}">
	          			Sopraortica
					</c:if>
					<c:if test="${ecoCuore.aortaStenosi2=='L'}">
	          			Lieve 
					</c:if>
					<c:if test="${ecoCuore.aortaStenosi2=='M'}">
	          			Moderata
					</c:if>
					<c:if test="${ecoCuore.aortaStenosi2=='G'}">
	          			Grave 
					</c:if>
					</c:if>
					<c:if test="${ecoCuore.aortaStenosi1==null || ecoCuore.aortaStenosi2==null}">
	          			No
					</c:if>
			</td>
		</tr>
		
		<tr>
			<td>
				Insufficienza 
			</td>
			<td colspan="2">
					<c:if test="${ecoCuore.aortaInsufficienza=='L'}">
	          			Lieve 
					</c:if>
					<c:if test="${ecoCuore.aortaInsufficienza=='M'}">
	          			Moderata
					</c:if>
					<c:if test="${ecoCuore.aortaInsufficienza=='G'}">
	          			Grave 
					</c:if>
					<c:if test="${ecoCuore.aortaInsufficienza==null}">
	          			No
					</c:if>
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
        			<c:if test="${ecoCuore.polmonareFlusso=='L'}">
	          			Laminare in fase sistolica
					</c:if>
        			<c:if test="${ecoCuore.polmonareFlusso=='T'}">
	          			Turbolento 
					</c:if>
			</td>
		</tr>
		
		<tr class="odd">
			<td>
				Velocit&agrave; massima 
			</td>
			<td colspan="2">
				<c:if test="${ecoCuore.aortaVelocitaMax!=null && ecoCuore.aortaVelocitaMax!=''}">
					${ecoCuore.polmonareVelocitaMax} ${ecoCuore.unitaMisuraDoppler}
				</c:if>
			</td>
		</tr>
		
		<tr>
			<td>
				Gradiente pressorio massimo (mmHg) 
			</td>
			<td colspan="2">
				${ecoCuore.polmonareGradiente}
			</td>
		</tr>
		
		<tr class="odd">
			<td>
				Stenosi 
			</td>
			<td colspan="2">
        			<c:if test="${ecoCuore.polmonareStenosi=='L'}">
	          			Lieve 
					</c:if>
					<c:if test="${ecoCuore.polmonareStenosi=='M'}">
	          			Moderata
					</c:if>
					<c:if test="${ecoCuore.polmonareStenosi=='G'}">
	          			Grave 
					</c:if>
        			<c:if test="${ecoCuore.polmonareStenosi==null}">
	          			No
					</c:if>
			</td>
		</tr>
		
		<tr>
			<td>
				Insufficienza 
			</td>
			<td colspan="2">
					<c:if test="${ecoCuore.polmonareInsufficienza=='L'}">
	          			Lieve 
					</c:if>
					<c:if test="${ecoCuore.polmonareInsufficienza=='M'}">
	          			Moderata
					</c:if>
					<c:if test="${ecoCuore.polmonareInsufficienza=='G'}">
	          			Grave 
					</c:if>
					<c:if test="${ecoCuore.polmonareInsufficienza==null}">
	          			No
					</c:if>
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
        			<c:if test="${ecoCuore.lvotTipo=='N'}">
	          			Normale 
					</c:if>
        			<c:if test="${ecoCuore.lvotTipo=='O'}">
	          			Ostruito 
					</c:if>
			</td>
		</tr>
		
		<tr class="odd">
			<td>
				Velocit&agrave; massima 
			</td>
			<td colspan="2">
				<c:if test="${ecoCuore.lvotVelocitaMax!=null && ecoCuore.lvotVelocitaMax!=''}">
					${ecoCuore.lvotVelocitaMax} ${ecoCuore.unitaMisuraDoppler}
				</c:if>
			</td>
		</tr>
		
		<tr>
			<td>
				Gradiente pressorio massimo (mmHg) 
			</td>
			<td colspan="2">
				${ecoCuore.lvotGradiente}
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
        			<c:if test="${ecoCuore.lvotFlussoLaminare==true}">
	          			Si
					</c:if>
        			<c:if test="${ecoCuore.lvotFlussoLaminare==false}">
	          			No 
					</c:if>
			</td>
		</tr>
		
		<tr class="odd">
			<td>
				Velocit&agrave; onda E
			</td>
			<td colspan="2">
				<c:if test="${ecoCuore.lvotVelocitaOndaE!=null && ecoCuore.lvotVelocitaOndaE!=''}">
					${ecoCuore.lvotVelocitaOndaE} ${ecoCuore.unitaMisuraDoppler}
				</c:if>
			</td>
		</tr>
		
		<tr>
			<td>
				Velocit&agrave; onda A
			</td>
			<td colspan="2">
				<c:if test="${ecoCuore.lvotVelocitaOndaA!=null && ecoCuore.lvotVelocitaOndaA!=''}">
					${ecoCuore.lvotVelocitaOndaA} ${ecoCuore.unitaMisuraDoppler}
				</c:if>
			</td>
		</tr>
		
		<tr class="odd">
			<td>
				Rapporto E/A
			</td>
			<td colspan="2">
				${ecoCuore.lvotRapportoEA}
			</td>
		</tr>

		<tr>
			<td>
				Velocit&agrave; massima di rigurgito
			</td>
			<td colspan="2">
				<c:if test="${ecoCuore.lvotVelocitaMaxRigurgito!=null && ecoCuore.lvotVelocitaMaxRigurgito!=''}">
					${ecoCuore.lvotVelocitaMaxRigurgito} ${ecoCuore.unitaMisuraDoppler}
				</c:if>
			</td>
		</tr>
		
		<tr class="odd">
			<td>
				Gradiente pressorio massimo (mmHg) 
			</td>
			<td colspan="2">
				${ecoCuore.lvotMitraleGradiente}
			</td>
		</tr>
		
		<tr>
			<td>
				Stenosi 
			</td>
			<td colspan="2">
        			<c:if test="${ecoCuore.lvotStenosi=='L'}">
	          			Lieve 
					</c:if>
					<c:if test="${ecoCuore.lvotStenosi=='M'}">
	          			Moderata
					</c:if>
					<c:if test="${ecoCuore.lvotStenosi=='G'}">
	          			Grave 
					</c:if>
        			<c:if test="${ecoCuore.lvotStenosi==null}">
	          			No 
					</c:if>
			</td>
		</tr>
		
		<tr class="odd">
			<td>
				Insufficienza 
			</td>
			<td colspan="2">
					<c:if test="${ecoCuore.lvotInsufficienza=='L'}">
	          			Lieve 
					</c:if>
					<c:if test="${ecoCuore.lvotInsufficienza=='M'}">
	          			Moderata
					</c:if>
					<c:if test="${ecoCuore.lvotInsufficienza=='G'}">
	          			Grave 
					</c:if>
					<c:if test="${ecoCuore.lvotInsufficienza==null}">
	          			No
					</c:if>
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
        			<c:if test="${ecoCuore.rvotTipo=='N'}">
	          			Normale 
					</c:if>
        			<c:if test="${ecoCuore.rvotTipo=='O'}">
	          			Ostruito 
					</c:if>
			</td>
		</tr>
		
		<tr class="odd">
			<td>
				Velocit&agrave; massima 
			</td>
			<td colspan="2">
				<c:if test="${ecoCuore.rvotVelocitaMax!=null && ecoCuore.rvotVelocitaMax!=''}">
					${ecoCuore.rvotVelocitaMax} ${ecoCuore.unitaMisuraDoppler}
				</c:if>
			</td>
		</tr>
		
		<tr>
			<td>
				Gradiente pressorio massimo (mmHg) 
			</td>
			<td colspan="2">
				${ecoCuore.rvotGradiente}
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
        			<c:if test="${ecoCuore.rvotFlussoLaminare==true}">
	          			Si
					</c:if>
        			<c:if test="${ecoCuore.rvotFlussoLaminare==false}">
	          			No
					</c:if>
			</td>
		</tr>
		
		<tr class="odd">
			<td>
				Velocit&agrave; massima di rigurgito
			</td>
			<td colspan="2">
				<c:if test="${ecoCuore.rvotVelocitaMaxRigurgito!=null && ecoCuore.rvotVelocitaMaxRigurgito!=''}">
					${ecoCuore.rvotVelocitaMaxRigurgito} ${ecoCuore.unitaMisuraDoppler}
				</c:if>
			</td>
		</tr>
		
		<tr>
			<td>
				Gradiente pressorio massimo (mmHg) 
			</td>
			<td colspan="2">
				${ecoCuore.rvotTricuspideGradiente}
			</td>
		</tr>
		
		<tr class="odd">
			<td>
				Stenosi 
			</td>
			<td colspan="2">
        			<c:if test="${ecoCuore.rvotStenosi=='L'}">
	          			Lieve 
					</c:if>
					<c:if test="${ecoCuore.rvotStenosi=='M'}">
	          			Moderata
					</c:if>
					<c:if test="${ecoCuore.rvotStenosi=='G'}">
	          			Grave 
					</c:if>
        			<c:if test="${ecoCuore.rvotStenosi==null}">
	          			No
					</c:if>
			</td>
		</tr>
		
		<tr>
			<td>
				Insufficienza 
			</td>
			<td colspan="2">
					<c:if test="${ecoCuore.rvotInsufficienza=='L'}">
	          			Lieve 
					</c:if>
					<c:if test="${ecoCuore.rvotInsufficienza=='M'}">
	          			Moderata
					</c:if>
					<c:if test="${ecoCuore.rvotInsufficienza=='G'}">
	          			Grave 
					</c:if>
					<c:if test="${ecoCuore.rvotInsufficienza==null}">
	          			No
					</c:if>
			</td>
		</tr>
		
		<tr>
        	<th colspan="3">
        		Conclusioni(diagnosi)
        	</th>
		</tr>
		
    	<c:set var="i" value='1'/>
    	<c:forEach var="d" items="${ecoCuore.lookupEcoCuoreDiagnosis}">
    		<tr class="${i % 2 == 0 ? 'odd' : 'even'}">
    			<td colspan="3" >
    				${d.descrizione}
    			</td>
    		</tr>
    		<c:set var="i" value='${i+1}'/>
		</c:forEach>
          
        <tr class="${i % 2 == 0 ? 'odd' : 'even'}">
        	<td>
        		&nbsp;
        	</td>
        </tr>
        </table>
           		
        <br>
		<table class="tabella">
			<tr>
				<th colspan="3">
				       		Altre Informazioni
				 </th>
			 </tr> 	  
			<tr class="odd">
				<td>Inserito da</td>
				<td>${ecoCuore.enteredBy} il <fmt:formatDate value="${ecoCuore.entered}" pattern="dd/MM/yyyy"/></td>
			</tr>
			<c:if test="${ecoCuore.modifiedBy!=null}">
			<tr class="even">
				<td>Modificato da</td><td>${ecoCuore.modifiedBy} il <fmt:formatDate value="${ecoCuore.modified}" pattern="dd/MM/yyyy"/></td>
			</tr>
			</c:if>
		</table> 			
    			<input type="button" value="Modifica" onclick="if(${cc.dataChiusura!=null}){ 
    				if(myConfirm('Cartella Clinica chiusa. Vuoi procedere?')){location.href='vam.cc.diagnosticaImmagini.ecoCuore.ToEdit.us?idEcoCuore=${ecoCuore.id}'}
    				}else{location.href='vam.cc.diagnosticaImmagini.ecoCuore.ToEdit.us?idEcoCuore=${ecoCuore.id}'}">
    			<input type="button" value="Lista Eco-Cuore" onclick="location.href='vam.cc.diagnosticaImmagini.ecoCuore.List.us'">