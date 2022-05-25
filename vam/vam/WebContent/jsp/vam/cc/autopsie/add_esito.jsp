<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>
<%@page import="java.util.Date"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="it.us.web.bean.vam.lookup.LookupAutopsiaEsitiEsami"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="it.us.web.bean.vam.lookup.LookupAutopsiaOrgani"%>
<%@page import="it.us.web.bean.vam.lookup.LookupAutopsiaTipiEsami"%>
<script language="JavaScript" type="text/javascript" src="js/vam/cc/autopsie/add.js"></script>
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
	/*ArrayList<LookupAutopsiaOrgani> organi	             	  = (ArrayList<LookupAutopsiaOrgani>)request.getAttribute("organi");
	ArrayList<LookupAutopsiaOrgani> organiSde	              = (ArrayList<LookupAutopsiaOrgani>)request.getAttribute("organiSde");*/
	ArrayList<LookupAutopsiaTipiEsami> listTipiAutopsia 			  = (ArrayList<LookupAutopsiaTipiEsami>)request.getAttribute("listTipiAutopsia");
	//HashMap<String, Set<LookupAutopsiaEsitiEsami>> allOrganiTipiEsiti = (HashMap<String, Set<LookupAutopsiaEsitiEsami>>)request.getAttribute("allOrganiTipiEsiti");
	DecimalFormat decimalFormat = new DecimalFormat( "000" );

%>
<form action="vam.cc.autopsie.Add.us" name="form" method="post" id="form" class="marginezero" onsubmit="javascript:return checkform(this);">
       
    <fmt:formatDate value="${cc.accettazione.animale.dataSmaltimentoCarogna}" pattern="dd/MM/yyyy" var="dataSmaltimento"/>
    <input type="hidden" name="dataSmaltimento" id="dataSmaltimento" value="${dataSmaltimento}" />   
    <input type="hidden" name="progressivo" id="progressivo" value="${progressivo}" />  
     
    <jsp:include page="/jsp/vam/cc/menuCC.jsp"/>   
    <h4 class="titolopagina">
     		Richiesta Esame Necroscopico
    </h4>
     
    
    <table class="tabella">
    	        
        <tr class='even'>
    		<td>
    			 Data richiesta<font color="red"> *</font>
    		</td>
    		<td>
    			 <fmt:formatDate type="date" value="<%=new Date() %>" pattern="dd/MM/yyyy" var="dataOdierna"/> 		
    			 <input type="text" id="dataAutopsia" name="dataAutopsia" maxlength="32" size="50" readonly="readonly" style="width:246px;" value="${dataOdierna}"/>
    			 <img src="images/b_calendar.gif" alt="calendario" id="id_img_2" />
 					<script type="text/javascript">
      					 Calendar.setup({
        					inputField     :    "dataAutopsia",     // id of the input field
        					ifFormat       :    "%d/%m/%Y",      // format of the input field
       						button         :    "id_img_2",  // trigger for the calendar (button ID)
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
        <!-- tr class='even'>
    		<td>
    			 Data Necroscopia
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
        </tr-->
        
        <!-- tr class='odd'>
    		<td id="asterisco">Operatori<font color="red"> *</font></br>
    			 <a id="linkChooseOp1"  style="cursor: pointer; text-decoration: underline; color: blue;" onclick="selezionaOp();">Seleziona</a>
					<input type="hidden" name="idOp" id="idOp" value="${a.operatori}"/>
					<input type="hidden" name="idOpTemp" id="idOpTemp" value="${a.operatori}"/>
    		</td>
    		<td id="facoltativo">Operatori (FACOLTATIVO)</br>
    			 <a id="linkChooseOp1"  style="cursor: pointer; text-decoration: underline; color: blue;" onclick="selezionaOp();">Seleziona</a>
					<input type="hidden" name="idOp" id="idOp" value="${a.operatori}"/>
					<input type="hidden" name="idOpTemp" id="idOpTemp" value="${a.operatori}"/></td>
    		<td colspan="2">
				<table id="operatoriSelezionati">
					<c:forEach items="${a.operatori}" var="temp"><tr><td>${temp}</td></tr></c:forEach>
				</table>
				<table id="operatoriSelezionatiTemp" style="display:none;">
					<c:forEach items="${a.operatori}" var="temp"><tr><td>${temp}</td></tr></c:forEach>
				</table>
	    	</td>
        </tr-->
         <!-- input type='hidden' id='senza_operatore' value='0'/-->    
        <tr class='even'>
    		<td>
    			 Sala settoria destinazione
    		</td>
    		<td>
    			 <c:set value="true" var="viewOptEsterna"/>
    			 <c:set value="true" var="viewOptInterna"/>
    			 <select id="lookupAutopsiaSalaSettoria" name="lookupAutopsiaSalaSettoria">
    			 <option value="">&lt;---Selezionare---&gt;</option>
				 	<c:forEach items="${listAutopsiaSalaSettoria}" var="q" >	
				 		<c:if test="${q.esterna && viewOptEsterna=='true'}">
				 			<optgroup label="<------- Esterna ------->" style="font-style: italic">
							<c:set value="false" var="viewOptEsterna"/>
				 		</c:if>
				 		<c:if test="${!q.esterna && viewOptInterna=='true'}">
				 			<optgroup label="<------- Interna ------->" style="font-style: italic">
				 			<c:set value="false" var="viewOptInterna"/>
				 		</c:if>
				 		<c:if test="${q.id==5}">
				 			<optgroup label="Universit&agrave;">
				 		</c:if>
				 		<c:if test="${q.id==4}">
				 			</optgroup>
				 			<optgroup label="IZSM">
				 		</c:if>
						<option value="${q.id }">${q.description }</option>
					</c:forEach>
					</optgroup>
		      	</select>
    		</td>
        </tr>     
             
             
        <tr class='even'>
    		<td>
    			 Numero rif. Mittente</br>
    			 <input onclick="popolaNumRifMittente('<%=decimalFormat.format(request.getAttribute("progressivo"))%>','${utente.clinica.lookupAsl.id}')" type="radio" name="tipoAccettazione" id="idTipoAccettazione1" value="IZSM"  checked/> <label onclick="popolaNumRifMittente('<%=decimalFormat.format(request.getAttribute("progressivo"))%>','${utente.clinica.lookupAsl.id}')" for="idTipoAccettazione1">IZSM</label>
    			 <input onclick="popolaNumRifMittente('<%=decimalFormat.format(request.getAttribute("progressivo"))%>','${utente.clinica.lookupAsl.id}')" type="radio" name="tipoAccettazione" id="idTipoAccettazione2" value="Unina"/>         <label onclick="popolaNumRifMittente('<%=decimalFormat.format(request.getAttribute("progressivo"))%>','${utente.clinica.lookupAsl.id}')" for="idTipoAccettazione2">Unina</label>
    			 <input onclick="popolaNumRifMittente('<%=decimalFormat.format(request.getAttribute("progressivo"))%>','${utente.clinica.lookupAsl.id}')" type="radio" name="tipoAccettazione" id="idTipoAccettazione3" value="Asl"/>           <label onclick="popolaNumRifMittente('<%=decimalFormat.format(request.getAttribute("progressivo"))%>','${utente.clinica.lookupAsl.id}')" for="idTipoAccettazione3">Asl</label>
    	         <input onclick="popolaNumRifMittente('<%=decimalFormat.format(request.getAttribute("progressivo"))%>','${utente.clinica.lookupAsl.id}')" type="radio" name="tipoAccettazione" id="idTipoAccettazione4" value="Criuv"/>         <label onclick="popolaNumRifMittente('<%=decimalFormat.format(request.getAttribute("progressivo"))%>','${utente.clinica.lookupAsl.id}')" for="idTipoAccettazione4">Criuv</label>
    		</td>
    		<td>
				<input type="text" id="numeroAccettazioneSigla" name="numeroAccettazioneSigla" size="50" style="width:246px;" value="${cc.accettazione.animale.numAccNecroscopicoIstoPrecedente}"/>
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Modalità di conservazione
    		</td>
    		<td>
    		
    			<c:forEach items="${listModalitaConservazione}" var="temp" >									
							<input type="radio" id="mcRichiesta_${temp.id}" name="modalitaConservazioneRichiesta" value="${temp.id}" onClick="javascript: showTemperaturaRichiesta('${temp.id}');" /> ${temp.description}	
							<br>				
				</c:forEach>    		
    		</td>
    		<td> 
    			
	    		<div id="temperaturaRichiesta" style="display:none;">
	    			Temperatura di conservazione   		
		    		 <input type="text" name="temperaturaConservazioneRichiesta" maxlength="5" size="5"/>	    		 
		    	</div>
    		</td>
        </tr>
             
        <tr>
        	<th colspan="4">
        		Dati anagrafici
        	</th>        	
        </tr>
        
        <tr class='even'>
    		<td >
    			Identificativo Animale
    		</td>
    		<td >
    			 <c:out value="${cc.accettazione.animale.identificativo}"/>
    		</td>
    		<td >
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
              
       
        
        <!-- tr>
        	<th colspan="3">
        		Fenomeni tanatologici e diagnosi dell'epoca del decesso
        	</th>        	
        </tr-->
        
        
        <!-- tr class='even'>
    		<td>
    			Modalità di conservazione
    		</td>
    		<td>
    		
    			<c:forEach items="${listModalitaConservazione}" var="temp" >									
							<input type="radio" id="mc_${temp.id}" name="modalitaConservazione" value="${temp.id}" onClick="javascript: showTemperatura('${temp.id}');" /> ${temp.description}	
							<br>				
				</c:forEach>    		
    		</td>
    		<td> 
    			
	    		<div id="temperatura" style="display:none;">
	    			Temperatura di conservazione   		
		    		 <input type="text" name="temperaturaConservazione" maxlength="5" size="5"/>	    		 
		    	</div>
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
    				<c:import url="../vam/cc/autopsie/ricorsioneFcAdd.jsp"/>						
				</c:forEach>    		
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr>
        	<th colspan="3">
        		Esame morfologico degli organi
        		&nbsp;&nbsp;
        		<a id="nascondi" style="cursor: pointer; color: blue; display:none;" onclick="javascript:document.getElementById('patologie').style.display='none';this.style.display='none';document.getElementById('mostra').style.display='inline';">Nascondi&nbsp;<img alt="su" src="images/su.PNG"/></a>
        		<a id="mostra"   style="cursor: pointer; color: blue;" onclick="javascript:document.getElementById('patologie').style.display='block';this.style.display='none';document.getElementById('nascondi').style.display='inline';">Mostra&nbsp;<img alt="giu" src="images/giu.PNG"/></a>
        	</th>        	      	
        </tr-->         
     </table>
     
     
     	
     <div id="patologie" style="display:none;">
     
     <table class="tabella">  
               
	        <!-- c:set var="j" value='1'/>	
	        <c:forEach items="${listTessutiAutopsia}" var="tessuto" >			        
		        <tr class="${j % 2 == 0 ? 'odd' : 'even'}">	    		
		    		<td>	    		    											
						<c:out value="${tessuto.description}"/>	
						<input type="hidden" name="tessuto_${j}" value="${tessuto.description}"/>
		    		</td>
		    		<td>	    		
		    			<input type="radio" name="esaminatoTessuto_${j}" id="esaminatoTessuto_${j}N" value="Non esaminato"  checked="checked" onClick="javascript: hiddenDiv('tessuto_${j}');" > <label for="esaminatoTessuto_${j}N">Non esaminato</label><br>
						<input type="radio" name="esaminatoTessuto_${j}" id="esaminatoTessuto_${j}E" value="Esaminato" onClick="javascript: toggleGroup('tessuto_${j}');" > <label for="esaminatoTessuto_${j}E">Esaminato</label><br>
						<input type="radio" name="esaminatoTessuto_${j}" id="esaminatoTessuto_${j}O" value="Normale"   onClick="javascript: hiddenDiv('tessuto_${j}');" > <label for="esaminatoTessuto_${j}O">Normale</label><br>
								
		    		</td>
		    		<td>
			    		<div id="tessuto_${j }" style="display:none;">	
			    		
			    		 <c:forEach items="${tessuto.lookupPatologiePrevalentiAutopsias}" var="patologia" >
			    		 
			    		 <c:choose>
							<c:when test="${patologia.esclusivo}">
								<input type="radio" name="patologieTessuti_${j}" id="${patologia.id }" value="${patologia.id }"/> ${patologia.description}
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="patologieTessuti_${j}" id="${patologia.id }" value="${patologia.id }"/> ${patologia.description}
							</c:otherwise>
						</c:choose>
			    		 	
						<br>    		 	
			    		</c:forEach>
			    		 Note<br><TEXTAREA NAME="altroTessuto_${j}" COLS=25 ROWS=3></TEXTAREA>
			    		</div>
		    		</td>		    		
		    		
		        </tr>		       
		        <c:set var="j" value='${j+1}'/>
	        </c:forEach>   
	        <input type="hidden" name="numeroElementiTessuti" value="${j}"/> 
	        -->
          
       </table>
     
     
     
     <table class="tabella">     	
        	
        	<!-- tr>
	        	<th class="sub" colspan="4">
	        		Organi
	        	</th>        	
        	</tr>
        	
	        <c:set var="i" value='1'/>	
	        <c:forEach items="${organi}" var="organo" >			        
		        <tr class="${i % 2 == 0 ? 'odd' : 'even'}">	    		
		    		<td>	    		    											
						<c:out value="${organo.description}"/>	
						<input type="hidden" name="organo_${i}" value="${organo.description}"/>
		    		</td>
		    		<td>	    		
		    			<input type="radio" name="esaminato_${i}" id="esaminato_${i}N" value="Non esaminato"  checked="checked" onClick="javascript: hiddenDiv('_${i}');" > <label for="esaminato_${i}N">Non esaminato</label><br>
						<input type="radio" name="esaminato_${i}" id="esaminato_${i}E" value="Esaminato" onClick="javascript: toggleGroup('_${i}');" > <label for="esaminato_${i}E">Esaminato</label><br>
						<input type="radio" name="esaminato_${i}" id="esaminato_${i}O" value="Normale"   onClick="javascript: hiddenDiv('_${i}');" > <label for="esaminato_${i}O">Normale</label><br>
								
		    		</td>
		    		<td>
			    		<div id="_${i }" style="display:none;">	
			    		
			    		 <c:forEach items="${organo.lookupPatologiePrevalentiAutopsias}" var="patologia" >
			    		 	<input type="checkbox" name="op${i}_${patologia.id }"  id="op${i}_${patologia.id }"/> <label for="op${i}_${patologia.id }">${patologia.description}</label>	
							<br>    		 	
			    		 </c:forEach>
			    		 Note<br><TEXTAREA NAME="altro_${i}" COLS=25 ROWS=3></TEXTAREA>
			    		</div>
		    		</td>		    		
		    		
		        </tr>		       
		        <c:set var="i" value='${i+1}'/>
	        </c:forEach>   
	        <input type="hidden" name="numeroElementi" value="${i}"/ -->
          
       </table>
       
       
       
       
       </div>
       
       
       <table class="tabella"> 
        <!-- tr>
        	<th colspan="4">
        		Sezione Dettaglio esami
        	</th>        	
        </tr-->
        
       <tr>
       <td colspan="4">
       
       
        <div id="dettaglioEsamiDiv"  style="display:block;">
	        <table class="tabella">
	        	        
	        <!--tr>
	    		<td colspan="3">
	    		<table style="width:100%" id="dettaglioEsami">
	    			<tr>
	    				<th class="sub" style="width:30%">
	    					Organo da analizzare
	    				</th>
	    				<th class="sub" style="width:20%">
	    					Tipologia Esame
	    				</th>
	    				<th class="sub" style="width:20%">
	    					Dettaglio Richiesta
	    				</th>
	    				<th class="sub" style="width:20%">
	    					Esito
	    				</th>
	    				<th class="sub" style="width:10%">Aggiungi/Rimuovi</th>
	    			</tr>
	    			<tr id="tr_1">
	    				<td>
    						<select style="width:98%" name="organo1" id="organo1" onchange="controllaOrganoRipetuto(this,document.getElementById('tipo1'));azzeraEsiti('esiti1');">
							<option value="">&lt;----------&gt;</option>
					        	<c:forEach items="${organiSde}" var="organo" >	
					        	 	<option value="${organo.id}">${organo.description}</option>
								</c:forEach>
    						</select>
    					</td>
    					<td>
    						<select style="width:98%" name="tipo1" id="tipo1" onchange="controllaTipoRipetuto(this,document.getElementById('organo1'));azzeraEsiti('esiti1');">
							<option value="">&lt;----------&gt;</option>
					        	 <c:forEach items="${listAutopsiaTipiEsami}" var="esame" >	
					        	 	<option value="${esame.id}">${esame.description }</option>	        	 				
								</c:forEach>
    						</select>
    					</td>
    					<td>
    						<input type="text" value="" name="note1" id="note1" maxlength="255"/>
    					</td>
    					<td>
    						<input type="hidden" name="esitiTemp1" id="esitiTemp1" value=""/>
    						
    						<input type="hidden" name="esiti1" id="esiti1" value=""/>
    						<input type="hidden" name="esitoDaAnagrafare1" id="esitoDaAnagrafare1" value=""/>
    						<input type="hidden" name="esitoDaAnagrafareValore1" id="esitoDaAnagrafareValore1" value=""/>
    						<input type="hidden" name="esitoTempDaAnagrafare1" id="esitoTempDaAnagrafare1" value=""/>
    						<input type="hidden" name="esitoTempDaAnagrafareValore1" id="esitoTempDaAnagrafareValore1" value=""/>
    						<input type="hidden" name="esitiTempValore1" id="esitiTempValore1" value=""/>
							<input type="hidden" name="esitiValore1"     id="esitiValore1" value=""/>
    						<a id="linkChooseEsiti1"  style="cursor: pointer; text-decoration: underline; color: blue;" onclick="selezionaEsiti(1,document.getElementById('organo1').options[document.getElementById('organo1').selectedIndex].innerHTML,document.getElementById('tipo1').options[document.getElementById('tipo1').selectedIndex].innerHTML);">Seleziona esiti</a> 
    					</td>
    					<td>
    						<input type="button" value="+" onclick="campiAltroDettaglio();" id="pulsanteAggiungiDettaglio"/>
    						<input type="button" value="-" onclick="rimuoviUltimoDettaglio();"  id="pulsanteRimuoviUltimoDettaglio" disabled="disabled"/>
    					</td>
    				</tr>
    			</table>
    		</td>
	        </tr-->  
	  </table>  
	  </div>	
       
      </td>
      </tr>
      </table>
                
		<table class="tabella"> 
	        <tr>
	        	<th class="sub" colspan="4">
	        		Sezione Esami Istopatologici
	        	</th>        	
	        </tr>
	        
	        <tr>
	        	<td>
	        		<b>Data Richiesta</b>
	        	</td>
	        	<td>
	        		<b>Data Necroscopia</b>
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
        
        <!-- tr>
        	<th colspan="2">
        		Cause del decesso Finale
        	</th>        	
        </tr>
       
        <tr class="${i % 2 == 0 ? 'odd' : 'even'}">
			<td width="50%">
       			<c:set var="i" value='1'/>
      			<c:forEach items="${listCMF}" var="temp" >									
					<input type="radio" name="causaMorteFinale" id="op_${temp.id }" value="${temp.id}" /> <label for="op_${temp.id}">${temp.description }</label>	
		 			</br>
		 			<c:set var="i" value='${i+1}'/>														
				</c:forEach> 
			</td>
			<td>
				<input type="radio" name="tipoCMF" id="tipoCMF_${temp.id }P" value="Provata"  checked> <label for="tipoCMF_${temp.id}P">Provata</label>
				<br>
				<input type="radio" name="tipoCMF" id="tipoCMF_${temp.id }S" value="Sospetta"  > <label for="tipoCMF_${temp.id}S">Sospetta</label>											
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
    			<TEXTAREA NAME="diagnosiAnatomoPatologica" COLS=40 ROWS=4></TEXTAREA>	    			   					
    		</td>    		
        </tr> 
         
        <tr class='even'>
    		<td>
    			Diagnosi Definitiva
    		</td>
    		<td>
    			<TEXTAREA NAME="diagnosiDefinitiva" COLS=40 ROWS=4></TEXTAREA>	    			   					
    		</td>    		
        </tr> 
        
        <tr class='odd'>
    		<td>
    			Quadro patologico prevalente
    		</td>
    		<td>
    			 <c:forEach items="${patologieDefinitive}" var="temp" >									
					<input type="radio" id="patologiaDefinitiva_${temp.id}" name="patologiaDefinitivaId" value="${temp.id}"/> ${temp.description}	
					<br>				
				</c:forEach>	    			   					
    		</td>    		
        </tr--> 
        
        
        
        <!-- tr>
        	<th colspan="2">
        		Immagini
        	</th>        	
        </tr>
             
        
        <tr class='odd'>
    		<td colspan="2">
    			<input type="file" value="" name="pathFile1"/>	    			   					
    		</td>    		
        </tr> 
         
        <tr class='even'>
    		<td colspan="2">
    			<input type="file" name="pathFile2"/>	    			   					
    		</td>    		
        </tr> 
        
        <tr class='odd'>
    		<td colspan="2">
    			<input type="file" name="pathFile3"/>	    			   					
    		</td>    		
        </tr> 
        
        <tr class='even'>
    		<td colspan="2">
    			<input type="file" name="pathFile4"/>	    			   					
    		</td>    		
        </tr> 
        
        <tr class='odd'>
    		<td colspan="2">
    			<input type="file" name="pathFile5"/>	    			   					
    		</td>    		
        </tr>    -->   
        
        
        
        
        
        <tr class='odd'>
        	<td>
        		<font color="red">* </font> Campi obbligatori
        	</td>
        	<td align="center">
        		<input type="submit" value="Salva" onclick="var mess=test(); if(mess!=''){alert(mess); return false;}"
        		/>
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
    		<td>    			
    		</td>     				
        </tr>
	</table>
</form>


 
<%
	/*Iterator<LookupAutopsiaOrgani> o = organiSde.iterator();
	while(o.hasNext())
	{
		LookupAutopsiaOrgani organo = o.next();
		Iterator<LookupAutopsiaTipiEsami> tipi = listTipiAutopsia.iterator();
		while(tipi.hasNext())
		{
			LookupAutopsiaTipiEsami tipo = tipi.next();*/
%>
			<!-- input type="hidden" id="esitiOrganoTipo<!--%=organo.getId()%>---<!--%=tipo.getId()%>"	value="<!--%=allOrganiTipiEsiti.get(organo.getId()+"---"+organo.getDescription()+";"+tipo.getId()+"---"+tipo.getDescription())%>" /-->
<%
		//}
	//}
%>


<!-- div style="display:none;" id="chooseTuttiEsiti" title="Selezione Esiti">
	<table class="tabella" id="tabellaTuttiEsiti">
		<tr>
	       	<th colspan="3">
			</th>
		</tr>	
		
		<c:set var="i" value='1'/>
		<c:forEach items="${listOrganiTipiEsiti}" var="organoTipoEsito" >	
			<tr class="${i % 2 == 0 ? 'odd' : 'even'}" id="trTuttiEsiti${i}_${organoTipoEsito.id}">
				<td colspan="3">
					<input type="checkbox" value="${organoTipoEsito.id}" id="checkTuttiEsiti${i}_${organoTipoEsito.id}" onclick="popolaEsitiSelezionati('1','${organoTipoEsito.id}');"/>
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
				<input type="text" value="" id="esitoDaAnagrafare"       onchange="popolaEsitiDaAnagrafare('1','1',this.value);" maxlength="64"/>
				<input type="text" value="" id="esitoDaAnagrafareValore" onchange="popolaEsitiDaAnagrafareValore('1','1',this.value);" maxlength="64"/>
			</td>
		</tr>
	</table>
</div-->

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
	
	
	$('#facoltativo').hide();
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




<!--  div id="div_operatori" style="display: none;">
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
</div-->

<script type="text/javascript">
	popolaNumRifMittente('<%=decimalFormat.format(request.getAttribute("progressivo"))%>','${utente.clinica.lookupAsl.id}');
</script>