<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ page import="java.util.ArrayList" %>
<%@page import="java.util.Date"%>

<script language="JavaScript" type="text/javascript" src="js/vam/cc/diagnosticaImmagini/ecoAddome/add.js"></script>

<form action="vam.cc.diagnosticaImmagini.ecoAddome.Add.us" method="post" name="form" id="form">

	
	<jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
	<h4 class="titolopagina">
	   	Nuovo Eco-Addome
	</h4>
	
	<table class="tabella">
		<tr>
    		<td>
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
    		<td>
    			 Data Esito
    			 <input type="text" id="dataEsito" name="dataEsito" maxlength="32" size="50" readonly="readonly" style="width:246px;" />
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
	</table>
	
	<table class="tabella">
		
		<tr>
			<th colspan="3">
	       		Eco-Addome
	       	</th> 	       	     
		</tr>		
		
		<tr>
			<th>
	       		Tipo
	       	</th>
	       	<th>
	       		Esito
	       	</th>
	       	<th>
	       		Referti
	       	</th> 	       	     
		</tr>
	
		<c:set var="i" value='1'/>	
		<c:forEach items="${ecoAddomeTipi}" var="ecoAddomeTipo" >	
		
		<tr class="${i % 2 == 0 ? 'even' : 'odd'}">
		
			<td style="width:20%">				
				${ecoAddomeTipo.nome}   
			</td>
	    
			<td style="width:20%">
				<input type="radio" name="_${i}" id="_N${i}" value="Normale" onclick="javascript: toggleGroup('_normale${i}'),  hiddenDiv('_anormale${i}') ;" > <label for="_N${i}">Normale</label><br>
				<input type="radio" name="_${i}" id="_A${i}" value="Anormale" onclick="javascript: toggleGroup('_anormale${i}'),  hiddenDiv('_normale${i}');" > <label for="_A${i}">Anormale</label><br>
				<input type="radio" name="_${i}" id="_NE${i}" value="NE" checked="checked" onclick="javascript: hiddenDiv('_normale${i}', '_anormale${i}');" > <label for="_NE${i}">Non esaminato</label><br>
			</td>			
			<td style="width:60%">
				<div id="_anormale${i }" style="display:none;">	
					<c:forEach items="${ecoAddomeTipo.lookupEcoAddomeReferti}" var="referto" >
						<c:if test="${referto.tipo == 'A'}">
							<input type="checkbox" id="refertoAnormale_${referto.id}" name="refertoAnormale_${referto.id}" /> <label for="refertoAnormale_${referto.id}">${referto.nome}</label>	
							<br>
						</c:if>				
					</c:forEach>
				</div>
				
				
				<div id="_normale${i }" style="display:none;">	
					<c:forEach items="${ecoAddomeTipo.lookupEcoAddomeReferti}" var="referto" >
						<c:if test="${referto.tipo == 'N'}">
							<input type="checkbox" id="refertoNormale_${referto.id}" name="refertoNormale_${referto.id}" /> <label for="refertoNormale_${referto.id}">${referto.nome}</label>	
							<br>
						</c:if>				
					</c:forEach>
					<c:if test="${ecoAddomeTipo.nome=='Gravidanza'}">
						<br/>
						Dimensione camera ovulare intrauterina (mm)
						<input type="text" name="gravidanza1" id="gravidanza1" size="5" maxlength="7" onblur="isNumber(this.value,'Dimensione camera ovulare intrauterina',this)"/>						
						<input type="text" name="gravidanza2" id="gravidanza2" size="5" maxlength="7" onblur="isNumber(this.value,'Dimensione camera ovulare intrauterina',this)"/>
						<input type="text" name="gravidanza3" id="gravidanza3" size="5" maxlength="7" onblur="isNumber(this.value,'Dimensione camera ovulare intrauterina',this)"/>
						<br/>
						CRL (mm)
						<input type="text" name="gravidanza4" id="gravidanza4" size="5" maxlength="7" onblur="isNumber(this.value,'CRL',this)"/>						
					</c:if>  
				</div>
			</td>			
		</tr>
		
		<c:set var="i" value='${i+1}'/>

	    </c:forEach>
	    
	    <tr class='even'>
    		<td colspan="3">   
    			<font color="red">* </font> Campi obbligatori
				<br/> 			
    			<input type="button" value="Salva"   onclick="checkform(document.getElementById('form'))"/>
    			<input type="button" value="Annulla" onclick="location.href='vam.cc.diagnosticaImmagini.ecoAddome.List.us'">
    		</td>
        </tr>
	
	</table>
</form>


