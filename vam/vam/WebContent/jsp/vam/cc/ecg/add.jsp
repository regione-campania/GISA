<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="java.util.Date"%>
<script language="JavaScript" type="text/javascript" src="js/vam/cc/ecg/addEdit.js"></script>

<form action="vam.cc.ecg.Add.us" id="form" name="form" method="post" class="marginezero">
           
    
    <jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
    <h4 class="titolopagina">
     		Nuovo ECG
    </h4>
    <table class="tabella">
    	<tr>
    		<td style="width:50%">
    			 Data Richiesta<font color="red"> *</font>
    			 <fmt:formatDate type="date" value="<%=new Date() %>" pattern="dd/MM/yyyy" var="dataOdierna"/> 		
    			 <input type="text" id="dataRichiesta" name="dataRichiesta" maxlength="32" size="50" readonly="readonly" style="width:246px;" value="${dataOdierna}"/>
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
    		<td style="width:30%">
    			 Data Esito
    			 <input type="text" id="dataEsito" name="dataEsito" maxlength="32" size="50" readonly="readonly" style="width:246px;"/>
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
        </tr>
          
    	<tr class="odd">
    		<td colspan="2">
    			DURATA
				Velocità carta:
				<ul>
					<li>
						50 mm/sec > 1 quadratino = 0.02 mm/sec
					</li>
					<li>
						25 mm/sec > 1 quadratino = 0.04 mm/sec
					</li>
				</ul>
				
				AMPIEZZA
				<ul>
					<li>
						1cm = 1mv > 1 quadratino = 0.1 mv
					</li>
					<li>
						2cm = 1mv
					</li>
				</ul>
    		</td>
    	</tr>   
    	     
        <th colspan="2">
    		Misurazioni effettuate in II derivazione (50mm/sec)
    	</th>
    	
         <tr>
    		<td style="width:30%">
    			 Ritmo<font color="red"> *</font>
    		</td>
    		<td style="width:70%">  
    			<select name="ritmo" id="ritmo" style="width:301px;">
						<option value=""><- Selezionare ritmo -></option>
            			<option value="S">Sinusale</option>
            			<option value="A">Aritmico</option>
    			</select>  		
    		</td>
        </tr>
        

		<tr class="odd">
    		<td style="width:30%">
    			 Frequenza (in bpm)
    		</td>
    		<td style="width:70%">  
    			 <input type="text" name="frequenza" id="frequenza"  size="5" onchange="isIntPositivo(this.value,'frequenza',this);" maxlength="7"/>
    		</td>
        </tr>     
        
        <tr>
    		<td style="width:30%">
    			 Ampiezza onda P (in quadratini)
    		</td>
    		<td style="width:70%">  
    			 <input type="text" name="ampiezzaOndaP" id="ampiezzaOndaP"  size="5"  onchange="isIntPositivo(this.value,'Ampiezza onda P',this);" maxlength="7"/>
    		</td>
        </tr>   
        
        <tr class="odd">
    		<td style="width:30%">
    			 Durata onda P (in quadratini)
    		</td>
    		<td style="width:70%">  
    			 <input type="text" name="durataOndaP" id="durataOndaP"  size="5"  onchange="isIntPositivo(this.value,'Durata onda P',this);" maxlength="7"/>
    		</td>
        </tr>    
        
        <tr>
    		<td style="width:30%">
    			 Ampiezza onda R (in quadratini)
    		</td>
    		<td style="width:70%">  
    			 <input type="text" name="ampiezzaOndaR" id="ampiezzaOndaR"  size="5"  onchange="isIntPositivo(this.value,'Ampiezza onda R',this);" maxlength="7"/>
    		</td>
        </tr>    
        
        <tr class="odd">
    		<td style="width:30%">
    			 Durata PR (in quadratini)
    		</td>
    		<td style="width:70%">  
    			 <input type="text" name="durataPR" id="durataPR"  size="5"  onchange="isIntPositivo(this.value,'Durata PR',this);" maxlength="7"/>
    		</td>
        </tr>  
        
        <tr>
    		<td style="width:30%">
    			 Durata complesso QRS (in quadratini)
    		</td>
    		<td style="width:70%">  
    			 <input type="text" name="durataComplessoQRS" id="durataComplessoQRS"  size="5"  onchange="isIntPositivo(this.value,'Durata complesso QRS',this);" maxlength="7"/>
    		</td>
        </tr> 
        
        <tr class="odd">
    		<td style="width:30%">
    			 Durata ST (in quadratini)
    		</td>
    		<td style="width:70%"> 
    			<input type="text" name="durataSTInQuadratini" id="durataSTInQuadratini" size="5" onchange="isIntPositivo(this.value,'Durata ST',this);" maxlength="7"/>
    			<select name="durataST" id="durataST" style="width:301px;">
						<option value=""><- Selezionare durata ST -></option>
            			<option value="T">Sottoslivellato</option>
            			<option value="P">Sopraslivellato </option>
    			</select>
    		</td>
        </tr> 
        
        <tr>
    		<td style="width:30%">
    			 Ampiezza onda T (in quadratini)
    		</td>
    		<td style="width:70%">  
    			 <input type="text" name="ampiezzaOndaT" id="ampiezzaOndaT"  size="5"  onchange="isIntPositivo(this.value,'Ampiezza onda T',this);" maxlength="7"/>
    		</td>
        </tr> 
        
        <tr class="odd">
    		<td style="width:30%">
    			 Intervallo QT (in quadratini) 
    		</td>
    		<td style="width:70%">  
    			 <input type="text" name="intervalloQT" id="intervalloQT" onchange="isIntPositivo(this.value,'Intervallo QT',this);" size="5" onblur="calcolaQTCorretto();" maxlength="7"/>
    		</td>
        </tr> 
        
        <tr>
    		<td style="width:30%">
    			 Intervallo RR (in quadratini) 
    		</td>
    		<td style="width:70%">  
    			 <input type="text" name="intervalloRR" id="intervalloRR" onchange="isIntPositivo(this.value,'Intervallo RR',this);" size="5" onblur="calcolaQTCorretto();" maxlength="7"/>
    		</td>
        </tr>
        
        <tr class="odd">
    		<td style="width:30%">
    			 QT corretto
    		</td>
    		<td style="width:70%">  
    			 <input type="text" name="QTCorretto" id="QTCorretto" onchange="isIntPositivo(this.value,'QT corretto',this);" maxlength="64" size="5" readonly="readonly"/>
    		</td>
        </tr>
        
        <tr>
        	<td colspan="2">
    			&nbsp;
    		</td>
        </tr>
        
        <tr>
        	<th colspan="2">
    			Diagnosi
    		</th>
        </tr>
        
        <tr class="odd">
    		<td style="width:100%" colspan="2"> 
    		
    		<c:set var="i" value='1'/>
    		<table>
    			<tr>
    				<td>
    					<input type="checkbox" id="diagnosi" name="diagnosi" onclick="javascript: disabilitaAnomalie();"/> <label for="diagnosi">Esame ECG nella norma</label><br/>
    				</td>
					<c:forEach var="aritmia" items="${aritmie}">
						<c:set var="i" value='${i+1}'/>
						<c:if test="${i%2>0}">
            				<tr>
            			</c:if>
						<td>
    						<input type="checkbox" id="aritmia_${aritmia.id}" name="aritmia_${aritmia.id}"/> <label for="aritmia_${aritmia.id}">${aritmia.nome}</label><br/>
            			</td>
            			
            			<c:if test="${i%2==0}">
            				</tr>
            			</c:if>
            		</c:forEach>
            	</tr>
    		</table>
    		</td>
        </tr> 
        
        <tr class='even'>
    		<td colspan="2">    
    			<font color="red">* </font> Campi obbligatori
				<br/>			
    			<input type="button" value="Salva" onclick="checkform()"/>
    			<input type="button" value="Annulla" onclick="location.href='vam.cc.ecg.List.us'">
    		</td>
        </tr>
	</table>
</form>