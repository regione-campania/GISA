<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.bean.BUtente"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>
<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page import="it.us.web.bean.vam.lookup.LookupCMI"%>

<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/TestDimissioni.js'></script>
<script language="JavaScript" type="text/javascript" src="js/vam/cc/dimissioni/add.js"></script>

<%
BUtente utente = (BUtente) request.getSession().getAttribute("utente");
%>

<c:choose>
	<c:when test="${cc.accettazione.animale.decedutoNonAnagrafe}">
		<fmt:formatDate value="${cc.accettazione.animale.dataMorte}" pattern="dd/MM/yyyy" var="dataMorte"/> 
	</c:when>
	<c:otherwise>
		<fmt:formatDate value="${res.dataEvento}" pattern="dd/MM/yyyy" var="dataMorte"/> 
	</c:otherwise>
</c:choose>
<form action="vam.cc.dimissioni.Add.us" name="form" method="post" id="form" class="marginezero" onsubmit="javascript:return checkform(this,'${cc.id}');">
         
    <fmt:formatDate type="date" value="${cc.dataApertura}" pattern="dd/MM/yyyy" var="dataAperturaCc"/> 
    <input type="hidden" name="dataApertura" id="dataApertura" value="${dataAperturaCc}">  
    <input type="hidden" name="idAutopsia" value="<c:out value="${a.id}"/>"/>
    <input type="hidden" name="oldCodiceIspra" id="oldCodiceIspra" value="${codiceIspra}"/>
    <input type="hidden" name="idUtente" id="idUtente" value="${utente.id}"/>
    <input type="hidden" name="idAnimale" id="idAnimale" value="${cc.accettazione.animale.id}"/>
    <input type="hidden" name="idDA" id="idDA" value="1"/>
    
    
    <jsp:include page="/jsp/vam/cc/menuCC.jsp"/>   
    <h4 class="titolopagina">
     		Registrazione Dimissioni
    </h4>
    <table class="tabella">
    
    <tr class='even'>
    		<td>
    			 Data Dimissione
    		</td>
    		<td>     			     			 
    			 <c:choose>
	    			<c:when test="${cc.dataChiusura != null}">					
						<fmt:formatDate type="date" value="${cc.dataChiusura}" pattern="dd/MM/yyyy" var="data"/> 		
    			 		<input type="text" id="dataChiusura" name="dataChiusura" maxlength="32" size="50" readonly="readonly" style="width:40%;" value="${data}"/>
    			 	</c:when>
					<c:otherwise>
						 <c:choose>
	    					<c:when test="${cc.dayHospital}">					
								<fmt:formatDate type="date" value="${cc.dataApertura}" pattern="dd/MM/yyyy" var="dataPrecompilata"/> 		
    			 			</c:when>
							<c:otherwise>
								<fmt:formatDate type="date" value="<%=new Date() %>" pattern="dd/MM/yyyy" var="dataPrecompilata"/> 		
    			 			</c:otherwise>
    			 		</c:choose>
    			 		<input type="text" id="dataChiusura" name="dataChiusura" maxlength="32" size="50" readonly="readonly" style="width:40%;" value="${dataPrecompilata}"/>
    			 	</c:otherwise>	
				</c:choose>	
    			<c:if test="${cc.dayHospital==false}">	  			 
    			 <img src="images/b_calendar.gif" alt="calendario" id="id_img_1" />
 					<script type="text/javascript">
      					 Calendar.setup({
        					inputField     :    "dataChiusura",     // id of the input field
        					ifFormat       :    "%d/%m/%Y",      	// format of the input field
       						button         :    "id_img_1",  		// trigger for the calendar (button ID)
       						// align          :    "Tl",           	// alignment (defaults to "Bl")
        					singleClick    :    true,
        					timeFormat		:   "24",
        					showsTime		:   false
   							 });					    
  					 </script>  
  				</c:if> 
    		</td>    		
        </tr>
                
             
        <tr class='odd'>
	        <td>
	    		Destinazione dell'animale
    		</td>
    		<td>
		        <select style="width:40%" id="destinazioneAninale" name="destinazioneAninale" onchange="javascript:abilita_div();abilitaDisabilitaIntraFuoriAsl(this);abilitaDisabilitaVersoAssocCanili(this);">
		        	 <c:forEach items="${destinazioneAnimale}" var="da" >	
		        	 	<c:if test="${dataMorte==null || da.id==2}">	
		        	 		<option value="${da.id }"
		        	 		<c:if test="${da.id == cc.destinazioneAnimale.id }">
								<c:out value="selected=selected"></c:out>
							</c:if> 
		        	 		>
		        	 		
	        	 			<c:if test="${cc.accettazione.animale.lookupSpecie.id==1 or cc.accettazione.animale.lookupSpecie.id==2}">
	        	 				${da.description }
	        	 			</c:if>
	        	 			<c:if test="${cc.accettazione.animale.lookupSpecie.id==3}">
	        	 				${da.descriptionSinantropo }
	        	 			</c:if>
		        	 		</option>	        	 				
						</c:if>
					</c:forEach>				
		        </select>
				<input type="checkbox" id="intraFuoriAsl" name="intraFuoriAsl" disabled="disabled"/> <label for="intraFuoriAsl" id="labelIntraFuoriAsl" >fuori Asl</label>
<%
				if(utente.getRuoloByTalos().equals("HD1") || utente.getRuoloByTalos().equals("HD2") || utente.getRuoloByTalos().equals("17") || utente.getRuoloByTalos().equals("16")  )
				{
%>
					<input type="checkbox" id="versoAssocCanili" name="versoAssocCanili" disabled="disabled"/> <label for="versoAssocCanili" id="labelVersoAssocCanili" >verso Associazioni/Canili</label>
<%
				}
%>
	        </td>
	        <td>
	        </td>
        </tr>
        
        
 <%--        
        <tr class="even">
	        <td>
	        	<label id="tdRilascio1" style="display:none;">
	       		 Provincia e Comune Rilascio<font color="red"> *</font>
	       		 </label>
	        </td>
	        <td >
	        	<select style="display:none;width:40%" name="provinciaReimmissione"  id="provinciaReimmissione" onChange="javascript: chooseProvinciaReimmissione(this.value)">>
					<option value="X"   SELECTED>&lt;--- Selezionare la provincia ---&gt;</option>
    				<option value="AV" >  Avellino  	</option>
    				<option value="BN" >  Benevento  </option>
            		<option value="CE" >  Caserta 	</option>
            		<option value="NA" >  Napoli 	</option>  
            		<option value="SA" >  Salerno 	</option>  
	    		</select>      		             
	        </td>
	        <td>    
	        	<div id="comuneReimmissioneBN" style="display:none;">
		        	<select style="width:60%" name="comuneReimmissioneBN" id="comuneReimmissioneBN">
						<option value="0">&lt;--- Selezionare il comune BN  ---&gt;</option>
	    				<c:forEach var="bn" items="${listComuniBN}">
	            			<option value="<c:out value="${bn.codiceIstat}"/>">            				
	            				${bn.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        <div id="comuneReimmissioneNA" style="display:none;">
		        	<select style="width:60%" name="comuneReimmissioneNA" id="comuneReimmissioneNA">
						<option value="0">&lt;--- Selezionare il comune NA  ---&gt;</option>
	    				<c:forEach var="na" items="${listComuniNA}">
	            			<option value="<c:out value="${na.codiceIstat}"/>">            				
	            				${na.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        <div id="comuneReimmissioneSA" style="display:none;">
		        	<select style="width:60%" name="comuneReimmissioneSA" id="comuneReimmissioneSA">
						<option value="0">&lt;--- Selezionare il comune SA  ---&gt;</option>
	    				<c:forEach var="sa" items="${listComuniSA}">
	            			<option value="<c:out value="${sa.codiceIstat}"/>">            				
	            				${sa.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        <div id="comuneReimmissioneCE" style="display:none;">
		        	<select style="width:60%" name="comuneReimmissioneCE" id="comuneReimmissioneCE">
						<option value="0">&lt;--- Selezionare il comune CE  ---&gt;</option>
	    				<c:forEach var="ce" items="${listComuniCE}">
	            			<option value="<c:out value="${ce.codiceIstat}"/>">            				
	            				${ce.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        <div id="comuneReimmissioneAV" style="display:none;">
		        	<select style="width:60%" name="comuneReimmissioneAV" id="comuneReimmissioneAV">
						<option value="0">&lt;--- Selezionare il comune AV  ---&gt;</option>
	    				<c:forEach var="av" items="${listComuniAV}">
	            			<option value="<c:out value="${av.codiceIstat}"/>">            				
	            				${av.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
	        </td>
        </tr>
                        
        
        <tr class="odd">
	        <td>
	        	<label id="tdRilascio3" style="display:none;">
	       		 	Luogo Rilascio<font color="red"> *</font>
	       		 </label>
	        </td>
	        <td>
	        	<label id="tdRilascio4" style="display:none;">
	        		<input type="text" name="luogoReimmissione" id="luogoReimmissione" maxlength="50" size="50"/>	
	        	</label>	             
	        </td>
	        <td>
	        </td>
        </tr>
        
         <tr class="odd">
	        <td>
	        	<label id="tdRilascio6" style="display:none;">
	       		 	Codice ISPRA
	       		 </label>
	        </td>
	        <td>
	        	<input type="text" name="codiceIspra" id="codiceIspra" style="display:none;"value="${codiceIspra}" maxlength="50" size="50"/>		             
	        </td>
	        <td>
	        </td>
        </tr> --%>
        
        
 <%--       
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
	    				<fmt:formatDate value="${res.dataEvento}" pattern="dd/MM/yyyy" var="data"/> 
	    			</c:otherwise>
	    		</c:choose>
				<input type="text" id="dataMorte" name="dataMorte" maxlength="32" size="50" readonly="readonly" disabled="disabled" style="width:40%;" value="<c:out value="${data}"></c:out>"/>
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
	    			<c:when test="${cc.accettazione.animale.decedutoNonAnagrafe}">
	    				<c:if test="${cc.accettazione.animale.dataMortePresunta!=null}">
	    					<c:choose>	    			
								<c:when test="${cc.accettazione.animale.dataMortePresunta == true}">					
									<input type="radio" name="dataMorteCerta" id="dataMorteCertaT" value="false" disabled="disabled" checked/><label for="dataMorteCertaT">Presunta</label>	
									<br>
									<input type="radio" name="dataMorteCerta" id="dataMorteCertaF" value="true" disabled="disabled"/> <label for="dataMorteCertaF">Certa</label>				
								</c:when>
								<c:otherwise>
									<input type="radio" name="dataMorteCerta" id="dataMorteCertaT" value="false" disabled="disabled"/><label for="dataMorteCertaT">Presunta</label>	
									<br>
									<input type="radio" name="dataMorteCerta" id="dataMorteCertaF" value="true" disabled="disabled" checked/> <label for="dataMorteCertaF">Certa</label>					   		 
								</c:otherwise>	
							</c:choose>
						</c:if>
						<c:if test="${cc.accettazione.animale.dataMortePresunta==null}">
							<input type="radio" name="dataMorteCerta" id="dataMorteCertaT" value="false" disabled="disabled" checked/><label for="dataMorteCertaT">Presunta</label>	
							<br>
							<input type="radio" name="dataMorteCerta" id="dataMorteCertaF" value="true" disabled="disabled"/> <label for="dataMorteCertaF">Certa</label>				
						</c:if>
	    			</c:when>
	    			<c:otherwise>
	    				<c:choose>	    			
							<c:when test="${res.dataDecessoPresunta == true}">					
								<input type="radio" name="dataMorteCerta" id="dataMorteCertaT" value="false" disabled="disabled" checked/><label for="dataMorteCertaT">Presunta</label>	
								<br>
								<input type="radio" name="dataMorteCerta" id="dataMorteCertaF" value="true" disabled="disabled"/> <label for="dataMorteCertaF">Certa</label>				
							</c:when>
							<c:otherwise>
								<input type="radio" name="dataMorteCerta" id="dataMorteCertaT" value="false" disabled="disabled"/><label for="dataMorteCertaT">Presunta</label>	
								<br>
								<input type="radio" name="dataMorteCerta" id="dataMorteCertaF" value="true" disabled="disabled" checked/> <label for="dataMorteCertaF">Certa</label>					   		 
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
    			<select style="width:40%" name="causaMorteIniziale" disabled="disabled">
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
	  								<c:when test="${cmi.description == res.decessoValue}">			
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
		       		 Provincia e Comune<font color="red"> *</font>
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
	        		Indirizzo<font color="red"> *</font>
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
	    		Operazioni richieste	   		
	    	</td>
    		<td>
    			<input type="checkbox" name="op_${opRichieste.esameNecroscopico}"  id="esameNecroscopico"  disabled="disabled"/> Esame Necroscopico</br>
	    		<input type="checkbox" name="op_${opRichieste.smaltimentoCarogna}" id="smaltimentoCarogna" disabled="disabled"/> Smaltimento Carcassa
	        </td>
	        <td>
	        </td>
        </tr> --%>
        <c:if test="${dataMorte!=null}">
			<tr class="even">
				<td>Data Decesso</td>
				<td><fmt:formatDate type="date" value="${res.dataEvento}" pattern="dd/MM/yyyy"/></td>
			</tr>
			<tr class="odd">
				<td>Decesso Presunto</td>
				<td><c:if test="${res.dataDecessoPresunta==false}">NO</c:if>
					<c:if test="${res.dataDecessoPresunta==true}">SI</c:if>
				</td>
			</tr>
			<tr class="even">
				<td>Causa del decesso</td>
				<td>${res.decessoValue}</td>
			</tr>
		</c:if>	
</table>	

<div id="info_decesso_div" title="info_decesso" style="display :none">      
</div>   

<div id="info_rilascio_div" title="info_rilascio" style="display :none">      
</div>     

<c:if test="${dataMorte!=null}">
	<!--  input type="checkbox" name="op_12"  id="esameNecroscopico" /> Esame Necroscopico</br>-->
	<input type="checkbox" name="op_13" id="smaltimentoCarogna"/> Trasporto Spoglie
	<script>document.forms[0].action = "vam.cc.dimissioni.AddDecessoSenzaComunicazioneBDU.us";</script>
</c:if>
<br>
       <input type="submit" value="Salva" />
       <input type="button" value="Annulla" onclick="attendere(), location.href='vam.cc.Detail.us'">

</form>



<script>
	function abilita_div(){
		var myselect = document.getElementById("destinazioneAninale");
		var opt = myselect.options[myselect.selectedIndex].value;
		
		if (opt==2){ //decesso
			document.getElementById("info_rilascio_div").style.display='none';
			document.getElementById("info_decesso_div").style.display='';
			$("#info_decesso_div").load("jsp/vam/cc/dimissioni/info_decesso_div.jsp?dataEvento=${res.dataEvento}&dataDecessoPresunta=${res.dataDecessoPresunta}&decessoValue=${res.decessoValue}&idNecroscopico=${opRichieste.esameNecroscopico}&idSmaltimento=${opRichieste.smaltimentoCarogna}");
		}
		else if (opt==5){//rilascio per i sinantropi
			var s = '${cc.accettazione.animale.lookupSpecie.id}';
			document.getElementById("info_decesso_div").style.display='none';
			if (s==3){
				document.getElementById("info_rilascio_div").style.display='';
				$("#info_rilascio_div").load("jsp/vam/cc/dimissioni/info_rilascio_div.jsp?codiceIspra=${codiceIspra}");
			}
		}
		else {
			document.getElementById("info_decesso_div").style.display='none';
			document.getElementById("info_rilascio_div").style.display='none';
		}
		document.getElementById("idDA").value=opt;
	}
</script>


<script type="text/javascript">
	if(form.destinazioneAninale.value=2)
	{
		abilita_div();
	}
</script>

