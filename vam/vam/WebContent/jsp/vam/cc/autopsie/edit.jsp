<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>
<%@page import="it.us.web.bean.vam.Autopsia"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="it.us.web.bean.vam.lookup.LookupAutopsiaEsitiEsami"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="it.us.web.bean.vam.lookup.LookupAutopsiaOrgani"%>
<%@page import="it.us.web.bean.vam.lookup.LookupAutopsiaTipiEsami"%>
<%@page import="it.us.web.bean.vam.lookup.LookupAutopsiaOrganiTipiEsamiEsiti"%>

<script language="JavaScript" type="text/javascript" src="js/vam/cc/autopsie/edit.js"></script>
<script language="JavaScript" type="text/javascript" src="js/azionijavascript.js"></script>
<script>
function test(){
	var mess = "";
	if (document.getElementById("dataEsito").value!=null && document.getElementById("dataEsito").value!=""){
		if (controllaDataFutura(document.getElementById("dataEsito").value)==1){ 
			mess="Non è consentito inserire una data futura";
		}
		else if (confrontaDate(document.getElementById("dataEsito").value, document.getElementById("dataAutopsia").value)<0 ){
			mess="Non è consentito inserire una Data Necroscopia antecedente alla Data Richiesta";
		}
	}
	return mess;
}
</script>
<%
	Autopsia a 									                      = (Autopsia)request.getAttribute("a");
	ArrayList<LookupAutopsiaOrgani> organi	              = (ArrayList<LookupAutopsiaOrgani>)request.getAttribute("organi");
	ArrayList<LookupAutopsiaOrgani> organiSde	          = (ArrayList<LookupAutopsiaOrgani>)request.getAttribute("organiSde");
	ArrayList<LookupAutopsiaTipiEsami> listTipiAutopsia   = (ArrayList<LookupAutopsiaTipiEsami>)request.getAttribute("listTipiAutopsia");
	HashMap<String, Set<LookupAutopsiaEsitiEsami>> allOrganiTipiEsiti = (HashMap<String, Set<LookupAutopsiaEsitiEsami>>)request.getAttribute("allOrganiTipiEsiti");
%>

<form action="vam.cc.autopsie.Edit.us" name="form" method="post" id="form" class="marginezero" onsubmit="javascript:return checkform(this);">
           
    <input type="hidden" name="idAutopsia" value="<c:out value="${a.id}"/>"/>
    <fmt:formatDate value="${cc.accettazione.animale.dataSmaltimentoCarogna}" pattern="dd/MM/yyyy" var="dataSmaltimento"/>
    <input type="hidden" name="dataSmaltimento" id="dataSmaltimento" value="${dataSmaltimento}" />  
    
    <jsp:include page="/jsp/vam/cc/menuCC.jsp"/>   
     <h4 class="titolopagina">
     		Esame Necroscopico
    </h4>  
    
    <table class="tabella">
    	        
        <tr class='even'>
    		<td>
    			 Data richiesta
    		</td>
    		<td>    
    			 <fmt:formatDate type="date" value="${a.dataAutopsia}" pattern="dd/MM/yyyy" var="data"/> 		
    			 <input type="hidden" id="dataAutopsia" name="dataAutopsia" maxlength="32" size="50" readonly="readonly" style="width:246px;" value="${data}"/>
    			 ${data}
    		</td>
    		<td>
    		</td>
        </tr>
        
        
         <tr class='even'>
    		<td>
    			 Sala settoria destinazione
    		</td>
    		<td>
    			${a.lass.description}
    			<input type="hidden" name="lookupAutopsiaSalaSettoria" id="lookupAutopsiaSalaSettoria" value="${a.lass.id}"/> 
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			 Numero rif. Mittente</br>
    			 <input type="hidden" name="tipoAccettazione" id="tipoAccettazione" value="${a.tipoAccettazione}" />
    		</td>
    		<td>
				<input readonly="readonly" type="hidden" id="numeroAccettazioneSigla" name="numeroAccettazioneSigla" size="50" style="width:246px;" value="${a.numeroAccettazioneSigla}"/>	
				${a.numeroAccettazioneSigla}
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Modalità di conservazione
    		</td>
    		<td>
    			${a.lmcRichiesta.description}   		
    		</td>
    		<td> 
    			
    			<c:choose>
  						<c:when test="${a.lmcRichiesta.id == 5}">			
	        	 			<div id="temperaturaRichiesta">	
		        	 			Temperatura di conservazione   		
					    		 <input type="text" name="temperaturaConservazioneRichiesta" maxlength="5" size="5" value="<c:out value="${a.temperaturaConservazioneRichiesta}"/>"/>	    		 
					    	</div>			    			
						</c:when>
						<c:otherwise>
							<div id="temperaturaRichiesta" style="display:none;">
								Temperatura di conservazione   		
					    		 <input type="text" name="temperaturaConservazioneRichiesta" maxlength="5" size="5"/>	    		 
					    	</div>		
						</c:otherwise>
				</c:choose>	    		
			    			
    		</td>
        </tr>
                
        <tr>
        	<th colspan="3">
        		Dati anagrafici
        	</th>        	
        </tr>
        
        <tr class='even'>
    		<td>
    			Identificativo Animale
    		</td>
    		<td>
    			 <c:out value="${cc.accettazione.animale.identificativo}"/>
    		</td>
    		<td>
    		</td>
        </tr>
        
        
        
        <tr class='odd'>
    		<td>
    			Tipologia
    		</td>
    		<td>
    			 <c:out value="${cc.accettazione.animale.lookupSpecie.description}"/>
    		</td>
    		<td>
    		</td>
        </tr>
        
         <c:set scope="request" var="anagraficaAnimale" value="${anagraficaAnimale}"/>
        <c:import url="../vam/anagraficaAnimale.jsp"/> 

         <tr class='even'>
    		<c:choose>
				<c:when test="${cc.accettazione.animale.lookupSpecie.id==3}">
					<td>
						Et&agrave;
					</td>
					<td>
						<fmt:formatDate type="date" value="${cc.accettazione.animale.dataNascita }" pattern="dd/MM/yyyy" />
						${cc.accettazione.animale.eta} <c:if test="${dataNascita}">(${dataNascita})</c:if>
					</td>
				</c:when>
				<c:otherwise>
					<td>
						Data nascita 
					</td>
					<td>
						<fmt:formatDate type="date" value="${cc.accettazione.animale.dataNascita }" pattern="dd/MM/yyyy" />
  						${dataNascita}
					</td>
				</c:otherwise>
			</c:choose>
    		<td width="23%">    			
				${cc.accettazione.animale.dataNascitaCertezza}					 		
    		</td>
        </tr>
                
       <tr class='odd'>
    		<td>
    			Data del decesso
    		</td>
    		<td>
				<c:choose>
					<c:when test="${cc.accettazione.animale.decedutoNonAnagrafe == true}">						
						<fmt:formatDate type="date" value="${cc.accettazione.animale.dataMorte}" pattern="dd/MM/yyyy" var="dataMorte"/>
						<c:out value="${dataMorte}"/>
						<input type="hidden" id="dataMorte" name="dataMorte" value="<c:out value="${dataMorte}"/>"/>
					</c:when>
					<c:otherwise>
						<fmt:formatDate type="date" value="${res.dataEvento}" pattern="dd/MM/yyyy" var="dataMorte"/>
						<c:out value="${dataMorte}"/>
						<input type="hidden" id="dataMorte" name="dataMorte" value="<c:out value="${dataMorte}"/>"/>
					</c:otherwise>	
				</c:choose>	    	
			</td>
			<td>			
				<c:choose>
					<c:when test="${cc.accettazione.animale.decedutoNonAnagrafe == true}">						
						${cc.accettazione.animale.dataMorteCertezza}
					</c:when>
					<c:otherwise>
						${res.dataMorteCertezza}
					</c:otherwise>	
				</c:choose>		
	        </td>
    	</tr>
    	
    	<tr class='even'>
	        <td>
	    		Probabile causa del decesso
    		</td>
    		<td>    
    			<c:choose>
	    			<c:when test="${cc.accettazione.animale.decedutoNonAnagrafe}">
	    				${cc.accettazione.animale.causaMorte.description}
	    			</c:when>
	    			<c:otherwise>
	    				${res.decessoValue}
	    			</c:otherwise>
	    		</c:choose>	        	        
	        </td>
	        <td>
	        </td>
        </tr>
        
        
        
        
        <!--  DATI RITROVAMENTO  -->
 		<c:if test="${cc.accettazione.animale.decedutoNonAnagrafe == true && cc.accettazione.randagio}">
	       <tr>
	        	<th colspan="3">
	        		Dati inerenti il ritrovamento
	        	</th>        	
	        </tr>
        	
        	
        	<tr class='even'>
	        	<td>
	        		Comune Ritrovamento
	        	</td>
	        	<td>
	        		<c:out value="${cc.accettazione.animale.comuneRitrovamento.description }"/>		        		
	        	</td>
	        	<td>
	        	</td>
        	</tr>
        	
        	<tr class='odd'>
	        	<td>
	        		Provincia Ritrovamento
	        	</td>
	        	<td>
	        		<c:out value="${cc.accettazione.animale.provinciaRitrovamento }"/>	        		
	        	</td>
	        	<td>
	        	</td>
        	</tr>
        		   	
        	<tr class='even'>
	        	<td>
	        		Indirizzo Ritrovamento
	        	</td>
	        	<td>
	        		<c:out value="${cc.accettazione.animale.indirizzoRitrovamento }"/>        		
	        	</td>
	        	<td>
	        	</td>
        	</tr>	
        	
        	<tr class='odd'>
	        	<td>
	        		Note Ritrovamento
	        	</td>
	        	<td>
	        		<c:out value="${cc.accettazione.animale.noteRitrovamento }"/>		        		    		
	        	</td>
	        	<td>
	        	</td>
        	</tr>			   		 	   		 
        </c:if>
		
		
		
		
       
                 
        <tr>
        	<th colspan="3">
        		Dati generali
        	</th>        	
        </tr>
        
        
        
        <tr class='even'>
    		<td>
    			 Data Necroscopia<font color="red"> *</font>
    		</td>
    		<td>    
    			 <fmt:formatDate type="date" value="${a.dataEsito}" pattern="dd/MM/yyyy" var="data"/> 		
    			 <input type="text" id="dataEsito" name="dataEsito" maxlength="32" size="50" readonly="readonly" style="width:246px;" value="${data}"/>
    			 <img src="images/b_calendar.gif" alt="calendario" id="id_img_3" />
 					<script type="text/javascript">
      					 Calendar.setup({
        					inputField     :    "dataEsito",     // id of the input field
        					ifFormat       :    "%d/%m/%Y",      // format of the input field
       						button         :    "id_img_3",  // trigger for the calendar (button ID)
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
        
        <tr class='odd'>
    		<td id="asterisco">
    			 Operatori<font color="red"> *</font></br>
    			 <a id="linkChooseOp1"  style="cursor: pointer; text-decoration: underline; color: blue;" onclick="selezionaOp();">Seleziona</a>
					<input type="hidden" name="idOp" id="idOp" value="${a.operatoriId}"/>
					<input type="hidden" name="idOpTemp" id="idOpTemp" value="${a.operatoriId}"/>
    		</td>
    		<td id="facoltativo">Operatori</br>
    			 <a id="linkChooseOp1"  style="cursor: pointer; text-decoration: underline; color: blue;" onclick="selezionaOp();">Seleziona</a>
					<input type="hidden" name="idOp" id="idOp" value="${a.operatoriId}"/>
					<input type="hidden" name="idOpTemp" id="idOpTemp" value="${a.operatoriId}"/></td>
    		<td colspan="2">
				<table id="operatoriSelezionati">
					<c:forEach items="${a.operatori}" var="temp"><tr><td>${temp}</td></tr></c:forEach>
				</table>
				<table id="operatoriSelezionatiTemp" style="display:none;">
					<c:forEach items="${a.operatori}" var="temp"><tr><td>${temp}</td></tr></c:forEach>
				</table>
	    	</td>
        </tr>
                 <input type='hidden' id='senza_operatore' value='0'/>  
        
        <tr>
        	<th colspan="3">
        		Fenomeni tanatologici e diagnosi dell'epoca del decesso
        	</th>        	
        </tr>
        
        <tr class='even'>
    		<td>
    			Modalità di conservazione
    		</td>
    		<td>
    		
    			<c:forEach items="${listModalitaConservazione}" var="temp" >									
					<c:choose>
  						<c:when test="${temp.id == a.lmc.id}">			
	        	 			<input type="radio" id="mc_${temp.id}" name="modalitaConservazione"  value="${temp.id}" onClick="javascript: showTemperatura('${temp.id}'); " checked /> <label for="${temp.id}">${temp.description}</label>	
						</c:when>
						<c:otherwise>
							<input type="radio" id="mc_${temp.id}" name="modalitaConservazione" value="${temp.id}" onClick="javascript: showTemperatura('${temp.id}');" /> <label for="${temp.id}">${temp.description}</label>	
						</c:otherwise>
					</c:choose>		
					<br>				
				</c:forEach>    		
    		</td>
    		<td> 
    			
    			<c:choose>
  						<c:when test="${a.lmc.id == 5}">			
	        	 			<div id="temperatura">	
		        	 			Temperatura di conservazione   		
					    		 <input type="text" name="temperaturaConservazione" maxlength="5" size="5" value="<c:out value="${a.temperaturaConservazione}"/>"/>	    		 
					    	</div>			    			
						</c:when>
						<c:otherwise>
							<div id="temperatura" style="display:none;">
								Temperatura di conservazione   		
					    		 <input type="text" name="temperaturaConservazione" maxlength="5" size="5"/>	    		 
					    	</div>		
						</c:otherwise>
				</c:choose>	    		
			    			
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Fenomeni cadaverici
    		</td>
    		<td>
    			<c:forEach items="${listFenomeniCadaverici}" var="fc" >		
    				<c:set var="fcTemp"  scope="request" value="${fc}" />
    				<c:set var="livello" scope="request" value="0" />
    				<c:import url="../vam/cc/autopsie/ricorsioneFcEdit.jsp"/>						
				</c:forEach>    
    		</td>
    		<td>
    		</td>
        </tr>
        
        
<!--        <tr class='even'>-->
<!--    		<td>-->
<!--    			Esame esterno del cadavere-->
<!--    		</td>-->
<!--    		<td>-->
<!--    			<TEXTAREA NAME="esameEsterno" COLS=40 ROWS=4><c:out value="${a.esameEsterno}"/></TEXTAREA>	    			   					-->
<!--    		</td>-->
<!--    		<td>-->
<!--    		</td>-->
<!--        </tr>      -->
                       
<!--        <tr class='odd'>-->
<!--    		<td>-->
<!--    			Cavità Addominale-->
<!--    		</td>-->
<!--    		<td>-->
<!--    			<TEXTAREA NAME="cavitaAddominale" COLS=40 ROWS=4><c:out value="${a.cavitaAddominale}"/></TEXTAREA>	    			   					-->
<!--    		</td>-->
<!--    		<td>-->
<!--    		</td>-->
<!--        </tr>-->
<!--        -->
<!--        <tr class='even'>-->
<!--    		<td>-->
<!--    			Cavità Toracica-->
<!--    		</td>-->
<!--    		<td>-->
<!--    			<TEXTAREA NAME="cavitaToracica" COLS=40 ROWS=4><c:out value="${a.cavitaToracica}"/></TEXTAREA>	    			   					-->
<!--    		</td>-->
<!--    		<td>-->
<!--    		</td>-->
<!--        </tr> -->
<!--               -->
<!--        <tr class='odd'>-->
<!--    		<td>-->
<!--    			Cavità Pelvica-->
<!--    		</td>-->
<!--    		<td>-->
<!--    			<TEXTAREA NAME="cavitaPelvica" COLS=40 ROWS=4><c:out value="${a.cavitaPelvica}"/></TEXTAREA>	    			   					-->
<!--    		</td>-->
<!--    		<td>-->
<!--    		</td>-->
<!--        </tr>  -->
<!--              -->
<!--        <tr class='even'>-->
<!--    		<td>-->
<!--    			Cavità Orale-->
<!--    		</td>-->
<!--    		<td>-->
<!--    			<TEXTAREA NAME="cavitaOrale" COLS=40 ROWS=4><c:out value="${a.cavitaOrale}"/></TEXTAREA>	    			   					-->
<!--    		</td>-->
<!--    		<td>-->
<!--    		</td>-->
<!--        </tr>                -->
        
        
        
        <tr>
        	<th colspan="3">
        		Esame morfologico degli organi 
        		&nbsp;&nbsp;
        		<a id="nascondi" style="cursor: pointer; color: blue; display:none;" onclick="javascript:document.getElementById('patologie').style.display='none';this.style.display='none';document.getElementById('mostra').style.display='inline';">Nascondi&nbsp;<img alt="su" src="images/su.PNG"/></a>
        		<a id="mostra"   style="cursor: pointer; color: blue;" onclick="javascript:document.getElementById('patologie').style.display='block';this.style.display='none';document.getElementById('nascondi').style.display='inline';">Mostra&nbsp;<img alt="giu" src="images/giu.PNG"/></a>
        	</th>        	      	
        </tr>         
     </table>
     
     
     	
     <div id="patologie" style="display:none;">
     
     <table class="tabella">  
        
	        <c:set var="j" value='1'/>	
	        <c:forEach items="${listTessutiAutopsia}" var="tessuto" >

				<c:set var="isIn" value='NO'/>	
	        	<c:set var="note" value=''/>
	        	<c:forEach items="${a.aops}" var="organoS" >	        		
					<c:if test="${ tessuto.id == organoS.lookupOrganiAutopsia.id}">																		
						<c:set var="isIn" value='YES'/>	
						<c:set var="note" value='${organoS.altro}'/>
							<c:choose>
								<c:when test="${ organoS.esaminato == true}">																		
									<c:set var="esaminato" value='YES'/>
								</c:when>
								<c:when test="${ organoS.esaminato == false}">																		
									<c:set var="esaminato" value='NO'/>
								</c:when>											
								
							</c:choose> 
					</c:if>		
	        	</c:forEach>	


			        
		        <tr class="${j % 2 == 0 ? 'odd' : 'even'}">	 
		    		<td>	    		    											
						<c:out value="${tessuto.description}"/>	
						<input type="hidden" name="tessuto_${j}" value="${tessuto.description}"/>
		    		</td>
		    		<td>
						<c:choose>
								<c:when test="${ isIn == 'YES'}">																	
									<c:choose>
										<c:when test="${ esaminato == 'YES'}">																		
											<input type="radio" name="esaminatoTessuto_${j}" id="esaminatoTessuto_${j}N" value="Non esaminato"     onClick="javascript: hiddenDiv('tessuto_${j}');" > <label for="esaminatoTessuto_${j}N">Non esaminato</label><br>
											<input type="radio" name="esaminatoTessuto_${j}" id="esaminatoTessuto_${j}E" value="Esaminato" 	checked="checked" onClick="javascript: toggleGroup('tessuto_${j}');" > <label for="esaminatoTessuto_${j}E">Esaminato</label><br>
											<input type="radio" name="esaminatoTessuto_${j}" id="esaminatoTessuto_${j}O" value="Normale"   	onClick="javascript: hiddenDiv('tessuto_${j}');" > <label for="esaminatoTessuto_${j}N">Normale</label><br>
										</c:when>
										<c:when test="${ esaminato == 'NO'}">																		
											<input type="radio" name="esaminatoTessuto_${j}" id="esaminatoTessuto_${j}N" value="Non esaminato"        onClick="javascript: hiddenDiv('tessuto_${j}');" > <label for="esaminatoTessuto_${j}N">Non esaminato</label><br>
											<input type="radio" name="esaminatoTessuto_${j}" id="esaminatoTessuto_${j}E" value="Esaminato" 	   onClick="javascript: toggleGroup('tessuto_${j}');" > <label for="esaminatoTessuto_${j}E">Esaminato</label><br>
											<input type="radio" name="esaminatoTessuto_${j}" id="esaminatoTessuto_${j}O" value="Normale"   	   checked="checked" onClick="javascript: hiddenDiv('tessuto_${j}');" > <label for="esaminatoTessuto_${j}N">Normale</label><br>
										</c:when>										
									</c:choose> 
									
								</c:when>											
								<c:otherwise>								
									<input type="radio" name="esaminatoTessuto_${j}" id="esaminatoTessuto_${j}N" value="Non esaminato"  checked="checked" onClick="javascript: hiddenDiv('tessuto_${j}');" > <label for="esaminatoTessuto_${j}N">Non esaminato</label><br>
									<input type="radio" name="esaminatoTessuto_${j}" id="esaminatoTessuto_${j}E" value="Esaminato" 	 onClick="javascript: toggleGroup('tessuto_${j}');" > <label for="esaminatoTessuto_${j}E">Esaminato</label><br>
									<input type="radio" name="esaminatoTessuto_${j}" id="esaminatoTessuto_${j}O" value="Normale"  	     onClick="javascript: hiddenDiv('tessuto_${j}');" > <label for="esaminatoTessuto_${j}O">Normale</label><br>
									
								</c:otherwise>
						</c:choose> 		    					
		    		</td>
		    		<td>
			    		
						<c:choose>
							<c:when test="${ isIn == 'YES'&& esaminato == 'YES'}">																		
								<div id="tessuto_${j }">		
							</c:when>											
							<c:otherwise>								
								<div id="tessuto_${j }" style="display:none;">	
							</c:otherwise>
						</c:choose> 		



			    		
			    		 <c:forEach items="${tessuto.lookupPatologiePrevalentiAutopsias}" var="patologia" >
			    		 	

							<c:set var="checkElement" value=''/>
				        	<c:forEach items="${a.aops}" var="aop" >
								<c:if test="${aop.lookupOrganiAutopsia.id == tessuto.id}">								
									<c:forEach items="${aop.lookupPatologiePrevalentiAutopsias}" var="patologiaS" >							        		
										<c:if test="${patologia.id == patologiaS.id}">																		
											<c:set var="checkElement" value='YES'/>									
										</c:if>		
				        			</c:forEach>
								</c:if>
							</c:forEach>	

							

							<c:choose>
								<c:when test="${ checkElement == 'YES'}">	
									<c:choose>
										<c:when test="${patologia.esclusivo}">
											<input type="radio" name="patologieTessuti_${j}" checked="checked" id="${patologia.id }" value="${patologia.id }"/> ${patologia.description}
										</c:when>
										<c:otherwise>
											<input type="checkbox" name="patologieTessuti_${j}" checked="checked" id="${patologia.id }" value="${patologia.id }"/> ${patologia.description}
										</c:otherwise>
									</c:choose>
								</c:when>											
								<c:otherwise>								
								<c:choose>
									<c:when test="${patologia.esclusivo}">
										<input type="radio" name="patologieTessuti_${j}" id="${patologia.id }" value="${patologia.id }"/> ${patologia.description}
									</c:when>
									<c:otherwise>
										<input type="checkbox" name="patologieTessuti_${j}" id="${patologia.id }" value="${patologia.id }"/> ${patologia.description}
									</c:otherwise>
								</c:choose>
							</c:otherwise>
							</c:choose> 	
			    		 	<br>
			    		 	<c:set var="checkElement" value=''/>    		 	
			    		</c:forEach>

			    		 Note<br><TEXTAREA NAME="altroTessuto_${j}" COLS=25 ROWS=3><c:out value="${note}"/></TEXTAREA>
			    		</div>
		    		</td>		    		
		    		
		        </tr>		       
		        <c:set var="j" value='${j+1}'/>
	        </c:forEach>   
	        <input type="hidden" name="numeroElementiTessuti" value="${j}"/>
          
       </table>
     
     
     <table class="tabella">     	
        	
        	<tr>
	        	<th class="sub" colspan="4">
	        		Organi
	        	</th>        	
        	</tr>
        	
	       <c:set var="i" value='1'/>	
	        <c:forEach items="${organi}" var="organo" >
	        	
	        	
	        	<c:set var="isIn" value='NO'/>	
	        	<c:set var="note" value=''/>
	        	<c:forEach items="${a.aops}" var="organoS" >	        		
					<c:if test="${ organo.id == organoS.lookupOrganiAutopsia.id}">																		
						<c:set var="isIn" value='YES'/>	
						<c:set var="note" value='${organoS.altro}'/>
						<c:choose>
								<c:when test="${ organoS.esaminato == true}">																		
									<c:set var="esaminato" value='YES'/>
								</c:when>
								<c:when test="${ organoS.esaminato == false}">																		
									<c:set var="esaminato" value='NO'/>
								</c:when>											
								
						</c:choose> 	
					</c:if>		
	        	</c:forEach>	
		        
		        <tr class="${i % 2 == 0 ? 'odd' : 'even'}">	    		
		    		<td>	    		    											
						<c:out value="${organo.description}"/>	
						<input type="hidden" name="organo_${i}" value="${organo.description}"/>
		    		</td>
		    		<td>  	
		    			<c:choose>
								<c:when test="${ isIn == 'YES'}">																		
									
									<c:choose>
										<c:when test="${ esaminato == 'YES'}">																		
											<input type="radio" name="esaminato_${i}" id="esaminato_${i}N" value="Non esaminato"     onClick="javascript: hiddenDiv('_${i}');" > <label for="esaminato_${i}N">Non esaminato</label><br>
											<input type="radio" name="esaminato_${i}" id="esaminato_${i}E" value="Esaminato" 	checked="checked" onClick="javascript: toggleGroup('_${i}');" > <label for="esaminato_${i}E">Esaminato</label><br>
											<input type="radio" name="esaminato_${i}" id="esaminato_${i}O" value="Normale"   	onClick="javascript: hiddenDiv('_${i}');" > <label for="esaminato_${i}O">Normale</label><br>
										</c:when>
										<c:when test="${ esaminato == 'NO'}">																		
											<input type="radio" name="esaminato_${i}" id="esaminato_${i}N" value="Non esaminato"        onClick="javascript: hiddenDiv('_${i}');" > <label for="esaminato_${i}N">Non esaminato</label><br>
											<input type="radio" name="esaminato_${i}" id="esaminato_${i}E" value="Esaminato" 	   onClick="javascript: toggleGroup('_${i}');" > <label for="esaminato_${i}E">Esaminato</label><br>
											<input type="radio" name="esaminato_${i}" id="esaminato_${i}O" value="Normale"   	   checked="checked" onClick="javascript: hiddenDiv('_${i}');" > <label for="esaminato_${i}O">Normale</label><br>
										</c:when>										
									</c:choose> 								
									
								</c:when>											
								<c:otherwise>								
									<input type="radio" name="esaminato_${i}" id="esaminato_${i}N" value="Non esaminato"  checked="checked" onClick="javascript: hiddenDiv('_${i}');" > <label for="esaminato_${i}N">Non esaminato</label><br>
									<input type="radio" name="esaminato_${i}" id="esaminato_${i}E" value="Esaminato" onClick="javascript: toggleGroup('_${i}');" > <label for="esaminato_${i}E">Esaminato</label><br>
									<input type="radio" name="esaminato_${i}" id="esaminato_${i}O" value="Normale"   onClick="javascript: hiddenDiv('_${i}');" > <label for="esaminato_${i}O">Normale</label><br>
									
								</c:otherwise>
						</c:choose> 		    			    
		    		</td>
		    		<td>
			    		
			    		<c:choose>
							<c:when test="${ isIn == 'YES' && esaminato == 'YES'}">																		
								<div id="_${i }">		
							</c:when>											
							<c:otherwise>								
								<div id="_${i }" style="display:none;">	
							</c:otherwise>
						</c:choose> 		
			    		 				    		
			    		<c:forEach items="${organo.lookupPatologiePrevalentiAutopsias}" var="patologia" >
			    		 	
			    		 	<c:set var="checkElement" value=''/>
				        	<c:forEach items="${a.aops}" var="aop" >
								<c:if test="${aop.lookupOrganiAutopsia.id == organo.id}">								
									<c:forEach items="${aop.lookupPatologiePrevalentiAutopsias}" var="patologiaS" >							        		
										<c:if test="${patologia.id == patologiaS.id}">																		
											<c:set var="checkElement" value='YES'/>									
										</c:if>		
				        			</c:forEach>
								</c:if>

	
							</c:forEach>	
			    		 	
			    		 	<c:choose>
								<c:when test="${ checkElement == 'YES'}">																		
									<input type="checkbox" name="op${i}_${patologia.id }"  checked id="op${i}_${patologia.id }"/> <label for="op${i}_${patologia.id }">${patologia.description}</label>	
								</c:when>											
								<c:otherwise>								
									<input type="checkbox" name="op${i}_${patologia.id }"  id="op${i}_${patologia.id }"/> <label for="op${i}_${patologia.id }">${patologia.description}</label>	
								</c:otherwise>
							</c:choose> 	
			    		 	<br>
			    		 	<c:set var="checkElement" value=''/>    		 	
			    		</c:forEach>
			    		 
			    		 Note<br><TEXTAREA NAME="altro_${i}" COLS=25 ROWS=3><c:out value="${note}"/></TEXTAREA>
			    		 
			    		
			    		</div>
		    		</td>			    	
		        </tr>	
		        
				<c:set var="checkElement" value=''/>   	       
		        <c:set var="i" value='${i+1}'/>
	        </c:forEach>   
	        <input type="hidden" name="numeroElementi" value="${i}"/>
          
       </table>
              
       
       </div>
       
       
        <table class="tabella">
        <tr>
        	<th colspan="4">
        		Sezione Dettaglio esami
        	</th>        	
        </tr>
        
       <tr>
       <td colspan="4">
       
       
        <div id="dettaglioEsamiDiv"  style="display:block;">
	        <table class="tabella">
	        	        
	        <tr>
	    		<td colspan="2">
	    		<table style="width:100%" id="dettaglioEsami">
	    			<tr>
	    				<th style="width:30%">
	    					Organo da analizzare
	    				</th>
	    				<th style="width:20%">
	    					Tipologia Esame
	    				</th>
	    				<th style="width:20%">
	    					Dettaglio Richiesta
	    				</th>
	    				<th style="width:20%">
	    					Esito
	    				</th>
	    				<th style="width:10%">Aggiungi/Rimuovi</th>
	    			</tr>
	    			
	    			
	    			<c:set var="i" value="1"/>
	    			<c:set var="nessunEsito" value="true"/>
	    			
	    			<%
	    				Iterator<Entry<String, Set<LookupAutopsiaOrganiTipiEsamiEsiti>>> iter = a.getDettaglioEsamiForJspEdit().entrySet().iterator();
	    			    Iterator<Entry<String, String>> iter2 = a.getValoriDettaglioEsamiForJspEdit().entrySet().iterator();
	    				while(iter.hasNext())
	    				{
	    					Entry<String, Set<LookupAutopsiaOrganiTipiEsamiEsiti>> e = iter.next();
	    					Entry<String, String> e2 = iter2.next();
	    			%>
	    				<c:set var="nessunEsito" value="false"/>
	    				<tr>
	    					<td>
	    						<% String organo = e.getKey().split(";")[0].split("---")[0]; %>
	    						<c:set var="organoCurr" value="<%= organo %>"/>
	    						
	    						<select style="width:98%" name="organo${i}" id="organo${i}" onchange="controllaOrganoRipetuto(this,document.getElementById('tipo${i}'));azzeraEsiti('esiti${i}');">
									<option value="">&lt;----------&gt;</option>
					        		<c:forEach items="${organiSde}" var="organo" >	
					        	 		<option value="${organo.id}"
					        	 		<c:if test="${organoCurr==organo.id}">
					        	 			<c:out value="selected=selected" />
					        	 		</c:if>
					        	 		>${organo.description}
					        	 		</option>
									</c:forEach>
    							</select>
    						</td>
    						<td>
    							<% String tipoEsame = e.getKey().split(";")[1].split("---")[0]; %>
	    						<c:set var="tipoCurr" value="<%= tipoEsame %>"/>
	    						
	    						<select style="width:98%" name="tipo${i}" id="tipo${i}" onchange="controllaTipoRipetuto(this,document.getElementById('organo${i}'));azzeraEsiti('esiti${i}');">
									<option value="">&lt;----------&gt;</option>
					        		<c:forEach items="${listTipiAutopsia}" var="tipo" >	
					        	 		<option value="${tipo.id}"
					        	 		<c:if test="${tipoCurr==tipo.id}">
					        	 			selected=selected
					        	 		</c:if>
					        	 		>${tipo.description}
					        	 		</option>
									</c:forEach>
    							</select>
    						</td>
    						<td>
    							<input type="text" name="note${i}" id="note${i}" value="<%=((e.getKey().split(";").length>2)?(e.getKey().split(";")[2]):(""))%>" maxlength="255"/>
    						</td>
    						<td>
    							<input type="hidden" name="esitiTemp${i}" id="esitiTemp${i}" value="<%=e.getValue()%>"/>
    							<input type="hidden" name="esiti${i}" id="esiti${i}" value="<%=e.getValue()%>"/>
    							<input type="hidden" name="esitoDaAnagrafare${i}" id="esitoDaAnagrafare${i}" value=""/>
    							<input type="hidden" name="esitoTempDaAnagrafare${i}" id="esitoTempDaAnagrafare${i}" value=""/>
    							<input type="hidden" name="esitoDaAnagrafareValore${i}" id="esitoDaAnagrafareValore${i}" value=""/>
    							<input type="hidden" name="esitoTempDaAnagrafareValore${i}" id="esitoTempDaAnagrafareValore${i}" value=""/>
    							<input type="hidden" name="esitiTempValore${i}" id="esitiTempValore${i}" value="<%=e2.getValue()%>"/>
								<input type="hidden" name="esitiValore${i}"     id="esitiValore${i}" value="<%=e2.getValue()%>"/>
    							<a id="linkChooseEsiti${i}"  style="cursor: pointer; text-decoration: underline; color: blue;" onclick="selezionaEsiti(${i},document.getElementById('organo${i}').options[document.getElementById('organo${i}').selectedIndex].innerHTML,document.getElementById('tipo${i}').options[document.getElementById('tipo${i}').selectedIndex].innerHTML);">Seleziona esiti</a> 
    						</td>
    						<td>
    							<c:if test="${i==1}">
    								<input type="button" value="+" onclick="campiAltroDettaglio();" id="pulsanteAggiungiDettaglio"/>
    								<input type="button" value="-" onclick="rimuoviUltimoDettaglio();"  id="pulsanteRimuoviUltimoDettaglio"/>
    							</c:if>
    						</td>
    					</tr>
    					<c:set var="i" value="${i+1}"/>
	    			<%
	    			}
	    			%>
	    			
	    			<tr id="tr_${i}">
	    				<td>
	    					<select style="width:98%" name="organo${i}" id="organo${i}" onchange="controllaOrganoRipetuto(this,document.getElementById('tipo${i}'));azzeraEsiti('esiti${i}');">
							<option value="">&lt;----------&gt;</option>
					        	<c:forEach items="${organiSde}" var="organo" >	
					        	 	<option value="${organo.id}">${organo.description}</option>
								</c:forEach>
    						</select>
    					</td>
    					<td>
    						<select style="width:98%" name="tipo${i}" id="tipo${i}" onchange="controllaTipoRipetuto(this,document.getElementById('organo${i}'));azzeraEsiti('esiti${i}');">
							<option value="">&lt;----------&gt;</option>
					        	 <c:forEach items="${listAutopsiaTipiEsami}" var="esame" >	
					        	 	<option value="${esame.id}">${esame.description }</option>	        	 				
								</c:forEach>
    						</select>
    					</td>
    					<td>
    						<input type="text" value="" name="note${i}" id="note${i}" maxlength="255"/>
    					</td>
    					<td>
    						<input type="hidden" name="esitiTemp${i}" id="esitiTemp${i}" value=""/>
    						<input type="hidden" name="esiti${i}" id="esiti${i}" value=""/>
    						<input type="hidden" name="esitoDaAnagrafare${i}" id="esitoDaAnagrafare${i}" value=""/>
    						<input type="hidden" name="esitoTempDaAnagrafare${i}" id="esitoTempDaAnagrafare${i}" value=""/>
   							<input type="hidden" name="esitoDaAnagrafareValore${i}" id="esitoDaAnagrafareValore${i}" value=""/>
   							<input type="hidden" name="esitoTempDaAnagrafareValore${i}" id="esitoTempDaAnagrafareValore${i}" value=""/>
   							<input type="hidden" name="esitiTempValore${i}" id="esitiTempValore${i}" value=""/>
							<input type="hidden" name="esitiValore${i}"     id="esitiValore${i}" value=""/>
    						
    						
    						<a id="linkChooseEsiti${i}"  style="cursor: pointer; text-decoration: underline; color: blue;" onclick="selezionaEsiti(${i},document.getElementById('organo${i}').options[document.getElementById('organo${i}').selectedIndex].innerHTML);">Seleziona esiti</a> 
    					</td>
    					<td>
    					${almenoUnEsito}
    						<c:if test="${nessunEsito}">
   								<input type="button" value="+" onclick="campiAltroDettaglio();" id="pulsanteAggiungiDettaglio"/>
   								<input type="button" value="-" onclick="rimuoviUltimoDettaglio();"  id="pulsanteRimuoviUltimoDettaglio"/>
   							</c:if>
    					</td>
    				</tr>
    			</table>
    		</td>
	        </tr>	  
	  </table>  
	  </div>
       
        </td>
        </tr>
        
        
        
       </table>
                
		<table class="tabella"> 
	        <tr>
	        	<th class="sub" colspan="5">
	        		Sezione Esami Istopatologici
	        	</th>        	
	        </tr>
	        
	        <tr>
	        	<td>
	        		<b>Data Richiesta</b>
	        	</td>
	        	<td>
	        		<b>Data Esito</b>
	        	</td>
	        	<td>
	        		<b>Numero</b>
	        	</td>
	        	<td>
	        		<b>Sede Lesione</b>
	        	</td>
	        	<td>
	        		<b>Diagnosi</b>
	        	</td>
	        </tr>
	        
	        <c:set var="i" value='1'/>	
 			 <c:forEach items="${cc.esameIstopatologicos}" var="ei" >    	   		
    	   		
    	   		<tr class="${i % 2 == 0 ? 'odd' : 'even'}">
					<td>
						<fmt:formatDate type="date" value="${ei.dataRichiesta}" pattern="dd/MM/yyyy" var="dataRichiesta"/>
    			 		<c:out value="${dataRichiesta}"/>							
					</td>
					<td>
						<fmt:formatDate type="date" value="${ei.dataEsito}" pattern="dd/MM/yyyy" var="dataEsito"/>
    			 		<c:out value="${dataEsito}"/>	
					</td>
					<td>
    			 		<c:out value="${ei.numero}"/>	
					</td>
					<td>
						<c:out value="${ei.sedeLesione }"/>	
					</td>	
					<td>
						${ei.whoUmana } ${ei.diagnosiNonTumorale }
					</td>									
				</tr>    	   		
     	   		
   	   	<c:set var="i" value='${i+1}'/>	
   	   	</c:forEach>	
	       
        </table>
				                
        
        <table class="tabella">
        
        <tr>
        	<th colspan="2">
        		Cause del decesso Finale
        	</th>        	
        </tr>
       
       	<tr class="${i % 2 == 0 ? 'odd' : 'even'}">
	   		<td width="50%">
       			<c:set var="i" value='1'/>	
      			<c:forEach items="${listCMF}" var="temp" >									
					<input type="radio" name="causaMorteFinale" id="op_${temp.id }" value="${temp.id}" 
					<c:forEach items="${a.cmf}" var="cmf">								
						<c:if test="${temp.id == cmf.lookupCMF.id }">
							<c:out value="checked=checked"></c:out>	
							<c:choose>
								<c:when test="${ cmf.provata == true}">																		
									<c:set var="provata" value="YES"/>	
								</c:when>							
								<c:when test="${ cmf.provata == false}">		
									<c:set var="provata" value="NO"/>
								</c:when>							
								<c:otherwise>								
									<c:set var="provata" value="-"/>
								</c:otherwise>
							</c:choose> 
						</c:if>
					</c:forEach> 
					/> <label for="op_${temp.id}">${temp.description }</label>	
					</br>
		 			<c:set var="i" value='${i+1}'/>
				</c:forEach>
			</td>
			<td>
				<input type="radio" name="tipoCMF" id="tipoCMF_${temp.id}P" value="Provata"
					<c:if test="${ provata == 'YES'}">
						checked
					</c:if>
				/> 
				<label for="tipoCMF_${temp.id}P">Provata</label>
				<br>
				<input type="radio" name="tipoCMF" id="tipoCMF_${temp.id}S" value="Sospetta"
					<c:if test="${ provata == 'NO'}">
						checked
					</c:if>
				/> 
				<label for="tipoCMF_${temp.id}S">Sospetta</label>											
			</td>
		</tr>
       
       <tr>
        	<th colspan="2">
        		Diagnosi
        	</th>        	
       </tr>
        
       <tr class='odd'>
    		<td>
    			Diagnosi Anatomo Patologica
    		</td>
    		<td>
    			<TEXTAREA NAME="diagnosiAnatomoPatologica" COLS=40 ROWS=4><c:out value="${a.diagnosiAnatomoPatologica}"/></TEXTAREA>	    			   					
    		</td>    		
        </tr>  
              
        <tr class='even'>
    		<td>
    			Diagnosi Definitiva
    		</td>
    		<td>
    			<TEXTAREA NAME="diagnosiDefinitiva" COLS=40 ROWS=4><c:out value="${a.diagnosiDefinitiva}"/></TEXTAREA>	    			   					
    		</td>    		
        </tr>   
        
        <tr class='odd'>
    		<td>
    			Quadro patologico prevalente<font color="red"> *</font>
    		</td>
    		<td>
    			 <c:forEach items="${patologieDefinitive}" var="temp" >									
					<input type="radio" id="patologiaDefinitiva_${temp.id}" name="patologiaDefinitivaId" value="${temp.id}"
						<c:if test="${temp.id==a.patologiaDefinitiva.id}">
							checked="checked"
						</c:if>						
					/> ${temp.description}	
					<br>				
				</c:forEach>	    			   					
    		</td>    		
        </tr>           
       
                
	</table>
		
	<table class="tabella">
		<tr class='odd'> 
			<td>
				<font color="red">* </font> Campi obbligatori
			</td>
			<td align="center">     		   			
				<input type="submit" value="Salva" onclick="var mess=test(); if(mess!=''){alert(mess); return false;}"/>
				<c:set var="readonlyImmagini" value="true" />
			<c:if test="${cc.accettazione.animale.necroscopiaNonEffettuabile==false}">
				<c:if test="${utenteStrutturaNecroscopia || utente.ruoloByTalos=='HD1' || utente.ruoloByTalos=='HD2'}">
					<c:set var="readonlyImmagini" value="false" />
				</c:if>
			</c:if>
				<input
				type="button" value="Immagini"
				onclick="javascript:avviaPopup( 'vam.cc.autopsie.GestioneImmagini.us?id=${a.id }&readonly=${readonlyImmagini}' );" />
	    		<input type="button" value="Annulla" onclick="attendere(), location.href='vam.cc.autopsie.Detail.us'">
	    	</td>				
		</tr>
	</table>
		
</form>

 
<%
	Iterator<LookupAutopsiaOrgani> o = organiSde.iterator();
	while(o.hasNext())
	{
		LookupAutopsiaOrgani organo = o.next();
		Iterator<LookupAutopsiaTipiEsami> tipi = listTipiAutopsia.iterator();
		while(tipi.hasNext())
		{
			LookupAutopsiaTipiEsami tipo = tipi.next();
%>
			<input type="hidden" id="esitiOrganoTipo<%=organo.getId()%>---<%=tipo.getId()%>"	value="<%=allOrganiTipiEsiti.get(organo.getId()+"---"+organo.getDescription()+";"+tipo.getId()+"---"+tipo.getDescription())%>" />
<%
		}
	}
%>

<div style="display:none;" id="chooseTuttiEsiti" title="Selezione Esiti">
	<table class="tabella" id="tabellaTuttiEsiti">
		<tr>
	       	<th colspan="3">
			</th>
		</tr>	
		
		<c:set var="i" value='1'/>
		<c:forEach items="${listOrganiTipiEsiti}" var="organoTipoEsito" >	
			<tr class="${i % 2 == 0 ? 'odd' : 'even'}" id="trTuttiEsiti${i}_${organoTipoEsito.id}">
				<td colspan="3">
					<input type="checkbox" value="${organoTipoEsito.id}" id="checkTuttiEsiti${i}_${organoTipoEsito.id}" onclick="popolaEsitiSelezionati('1','${organoTipoEsito.id}');"
					/>
					<label id="labelTuttiEsiti${i}_${organoTipoEsito.id}" for="checkTuttiEsiti${i}_${organoTipoEsito.id}">${organoTipoEsito.esito.description}</label>
					<input type="text" value="" id="checkTuttiEsitiValore${i}_${organoTipoEsito.id}" onchange="popolaEsitiSelezionatiValore('1','${organoTipoEsito.id}',this.value);" />
				</td>
			</tr>
			<c:set var="i" value='${i+1}'/>
		</c:forEach>
		
		
		<tr>
	       	<td colspan="3">
	       		&nbsp;
			</td>
		</tr>
		
		<tr>
	       	<th colspan="3">
			</th>
		</tr>
		
		<tr class="even" id="trEsitoDaAnagrafare1">
			<td colspan="3">
				<input type="text" value="" id="esitoDaAnagrafare" onchange="popolaEsitiDaAnagrafare('1','1',this.value);" maxlength="64"/>
				<input type="text" value="" id="esitoDaAnagrafareValore" onchange="popolaEsitiDaAnagrafareValore('1','1',this.value);" maxlength="64"/>
			</td>
		</tr>
	</table>
</div>

<script type="text/javascript">
	function mostraPopupChooseEsiti(riga){

		$( "#chooseEsiti"+riga ).dialog({
			height: screen.height/2,
			modal: true,
			autoOpen: true,
			closeOnEscape: true,
			show: 'blind',
			resizable: true,
			draggable: true,
			width: screen.width/2,
			buttons: {
				"Annulla": function() {
					confermaModifiche(false,riga);
					$( this ).dialog( "close" );
				},
				"Salva": function() {
					confermaModifiche(true,riga);
					$( this ).dialog( "close" );
				}
			}
		});
	}

	var numeroInizialeDettagli = document.getElementById("dettaglioEsami").getElementsByTagName("select").length/2-1;
	
	
	$("input[name='modalitaConservazione'], input[name='fc_1'], input[name='fc_3'], input[name='fc_4'], input[name='causaMorteFinale']  ").click(function ()
			{
			  var previousValue = $(this).attr('previousValue');
			  var name = $(this).attr('name');

			  if (previousValue == 'checked')
			  {
			    $(this).removeAttr('checked');
			    $(this).attr('previousValue', false);
			    //Nascondo la temperatura di conservazione
			    if ($(this).attr('id') == 'mc_5')
			    	document.getElementById("temperatura").style.display = "none";	
			  }
			  else
			  {
			    $("input[name="+name+"]:radio").attr('previousValue', false);
			    $(this).attr('previousValue', 'checked');
			  }
			});
			
			
			
	function mostraPopupChooseOp(){

		$( "#chooseOp" ).dialog({
			height: screen.height/2,
			modal: true,
			autoOpen: true,
			closeOnEscape: true,
			show: 'blind',
			resizable: true,
			draggable: true,
			title: 'Seleziona Operatori',
			width: screen.width/2,
			buttons: {
				"Annulla": function() {
					confermaModificheOperatori(false);
					$( this ).dialog( "close" );
				},
				"Ok": function() {
					confermaModificheOperatori(true);
					$( this ).dialog( "close" );
				}
			}
		});
	}
	
	
	document.getElementById('idOp').value = document.getElementById('idOp').value.replace("[","");
	document.getElementById('idOp').value = document.getElementById('idOp').value.replace("]","");
	while(document.getElementById('idOp').value.indexOf(" ")>0)
	{
		document.getElementById('idOp').value = document.getElementById('idOp').value.replace(" ","");
	}
	document.getElementById('idOpTemp').value = document.getElementById('idOpTemp').value.replace("[","");
	document.getElementById('idOpTemp').value = document.getElementById('idOpTemp').value.replace("]","");
	while(document.getElementById('idOpTemp').value.indexOf(" ")>0)
	{
		document.getElementById('idOpTemp').value = document.getElementById('idOpTemp').value.replace(" ","");
	}
	var val1=$('#lookupAutopsiaSalaSettoria').val(); 
	if (val1==4 || val1==5 || val1==8 || val1==9 || val1==10 || val1==11){
		$('#asterisco').hide();
		$('#facoltativo').show();
	}else{
		$('#facoltativo').hide();	
		$('#asterisco').show();
	}
	
	
	$('#lookupAutopsiaSalaSettoria').change( function() {
		var val = $(this).val();
		if (val==4 || val==5 || val==8 || val==9 || val==10 || val==11){  
			$('#asterisco').hide();
			$('#facoltativo').show();
			$('#senza_operatore').val('1');
	}else{
			$('#facoltativo').hide();
			$('#asterisco').show();
			$('#senza_operatore').val('0');

	}
		});
	
</script>



<div id="div_operatori" style="display: none;">
	<table class="tabella" id="tabellaOp">
		<tr>
						<th colspan="3">
			</th>
		</tr>

		<c:forEach items="${operatori}" var="operatore">
			<c:if test="${operatore.enabled == true}">
				<tr class="${i % 2 == 0 ? 'odd' : 'even'}" id="trTuttiEsiti${i}_${organoTipoEsito.id}">
					<td colspan="3" style="text-transform: uppercase;">
						<input type="checkbox" value="${operatore.id}"  
							   	id="op${operatore.id}"  
						   		name="op${operatore}"
				   		   		onclick="popolaOpSelezionati('${operatore.id}','${operatore}')"
							<us:contain collection="${a.operatori}" item="${operatore}">
								checked="checked"
							</us:contain>	
						/>
						${operatore}
					</td>
				</tr>
				<c:set var="i" value='${i+1}'/>
			</c:if>
		</c:forEach>
	</table>
</div>