<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.text.DecimalFormat"%>
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
var mess = "";

function test(idAutopsia){
	mess = "";
	TestAutopsie.existNumRifMittente(idAutopsia,document.getElementById('numeroAccettazioneSigla').value, 
			{
					callback:function(msg) 
					{ 
						if(msg!=null && msg!='')
						{
							alert(msg);
							mess=msg;
							//mess="";
							$( "#dialog-modal" ).dialog( "close" );
							return mess;
						}
						else
							return mess;
					},
					async: false,
					timeout:5000,
					errorHandler:function(message, exception)
					{
					    //Session timedout/invalidated
					    if(exception && exception.javaClassName=='org.directwebremoting.impl.LoginRequiredException')
					    {
					        alert(message);
					        //Reload or display an error etc.
					        window.location.href=window.location.href;
					    }
					    else
					        alert('Errore Nella Chiamata Remota : '+exception.javaClassName);
					 }
 			});
}
</script>

<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/TestAutopsie.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>


<%
	Autopsia a 									                      = (Autopsia)request.getAttribute("a");
	ArrayList<LookupAutopsiaTipiEsami> listTipiAutopsia   = (ArrayList<LookupAutopsiaTipiEsami>)request.getAttribute("listTipiAutopsia");
	DecimalFormat decimalFormat = new DecimalFormat( "000" );
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
    			 Data richiesta<font color="red"> *</font>
    		</td>
    		<td>    
    			 <fmt:formatDate type="date" value="${a.dataAutopsia}" pattern="dd/MM/yyyy" var="data"/> 		
    			 <input type="text" id="dataAutopsia" name="dataAutopsia" maxlength="32" size="50" readonly="readonly" style="width:246px;" value="${data}"/>
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
        
         <tr class='even'>
    		<td>
    			 Sala settoria destinazione<font color="red"> *</font>
    		</td>
    		<td>
    			 <c:set value="true" var="viewOptEsterna"/>
    			 <c:set value="true" var="viewOptInterna"/>
    			 <select name="lookupAutopsiaSalaSettoria" id="lookupAutopsiaSalaSettoria">
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
				 		<c:choose>
	    					<c:when test="${a.lass.id == q.id}">			
								<option value="${q.id }" selected>${q.description }</option>									
							</c:when>
							<c:otherwise>
								<option value="${q.id }">${q.description }</option>
							</c:otherwise>
								</c:choose>		
					</c:forEach>
					</optgroup>
		      	</select>
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			 Numero rif. Mittente</br>
    			 <input onclick="popolaNumRifMittente('<%=decimalFormat.format((Integer)request.getAttribute("progressivo"))%>','${utente.clinica.lookupAsl.id}')" type="radio" name="tipoAccettazione" id="idTipoAccettazione1" value="IZSM" <c:if test="${a.tipoAccettazione=='IZSM'}">checked="checked"</c:if>/>
			     <label onclick="popolaNumRifMittente('<%=decimalFormat.format((Integer)request.getAttribute("progressivo"))%>','${utente.clinica.lookupAsl.id}')" for="idTipoAccettazione1">IZSM</label>
				 <input onclick="popolaNumRifMittente('<%=decimalFormat.format((Integer)request.getAttribute("progressivo"))%>','${utente.clinica.lookupAsl.id}')" type="radio" name="tipoAccettazione" id="idTipoAccettazione2" value="Unina" <c:if test="${a.tipoAccettazione=='Unina'}">checked="checked"</c:if>/>
				 <label onclick="popolaNumRifMittente('<%=decimalFormat.format((Integer)request.getAttribute("progressivo"))%>','${utente.clinica.lookupAsl.id}')" for="idTipoAccettazione2">Unina</label>
				 <input onclick="popolaNumRifMittente('<%=decimalFormat.format((Integer)request.getAttribute("progressivo"))%>','${utente.clinica.lookupAsl.id}')" type="radio" name="tipoAccettazione" id="idTipoAccettazione3" value="Asl" <c:if test="${a.tipoAccettazione=='Asl'}">checked="checked"</c:if>/>
			     <label onclick="popolaNumRifMittente('<%=decimalFormat.format((Integer)request.getAttribute("progressivo"))%>','${utente.clinica.lookupAsl.id}')" for="idTipoAccettazione3">Asl</label>
			     <input onclick="popolaNumRifMittente('<%=decimalFormat.format((Integer)request.getAttribute("progressivo"))%>','${utente.clinica.lookupAsl.id}')" type="radio" name="tipoAccettazione" id="idTipoAccettazione4" value="Criuv" <c:if test="${a.tipoAccettazione=='Criuv'}">checked="checked"</c:if>/>
			     <label onclick="popolaNumRifMittente('<%=decimalFormat.format((Integer)request.getAttribute("progressivo"))%>','${utente.clinica.lookupAsl.id}')" for="idTipoAccettazione4">Criuv</label>
    		</td>
    		<td>
				<input type="text" id="numeroAccettazioneSigla" name="numeroAccettazioneSigla" size="50" style="width:246px;" value="${a.numeroAccettazioneSigla}"/>	
    			<!-- 
    			<input type="button" id="generaNuovoNumero"       name="generaNuovoNumero"       onclick="generaNuovoCodice('${utente.clinica.lookupAsl.id}')" value="Genera Nuovo Numero"/>
    			-->
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Modalità di conservazione
    		</td>
    		<td>
    		
    			<c:forEach items="${listModalitaConservazione}" var="temp" >									
					<c:choose>
  						<c:when test="${temp.id == a.lmcRichiesta.id}">			
	        	 			<input type="radio" id="mcRichiesta_${temp.id}" name="modalitaConservazioneRichiesta"  value="${temp.id}" onClick="javascript: showTemperaturaRichiesta('${temp.id}'); " checked /> <label for="${temp.id}">${temp.description}</label>	
						</c:when>
						<c:otherwise>
							<input type="radio" id="mcRichiesta_${temp.id}" name="modalitaConservazioneRichiesta" value="${temp.id}" onClick="javascript: showTemperaturaRichiesta('${temp.id}');" /> <label for="${temp.id}">${temp.description}</label>	
						</c:otherwise>
					</c:choose>		
					<br>				
				</c:forEach>    		
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
		
     </table>
         
	 
       
        </td>
        </tr>
        
		<tr class='odd'> 
			<td>
				<font color="red">* </font> Campi obbligatori
			</td>
			<td align="center">     		   			
				<input type="submit" value="Modifica" onclick="test(${a.id}); if(mess!=''){ return false;}"/>
				<!--input
				type="button" value="Immagini"
				onclick="javascript:avviaPopup( 'vam.cc.autopsie.GestioneImmagini.us?id=${a.id }' );" /-->
	    		<input type="button" value="Annulla" onclick="attendere(), location.href='vam.cc.autopsie.Detail.us'">
	    	</td>				
		</tr>
	</table>
		
</form>

<script type="text/javascript">
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