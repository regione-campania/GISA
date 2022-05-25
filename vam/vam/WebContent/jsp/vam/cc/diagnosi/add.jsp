<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="java.util.Date"%>
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

<form action="vam.cc.diagnosi.Add.us" method="post" onsubmit="javascript:return checkform(this);">

	<input type="hidden" name="numeroDiagnosi"    value="<c:out value="${numeroDiagnosi}"/>"/>

	
	<jsp:include page="/jsp/vam/cc/menuCC.jsp?riepilogoCc=ancheDatiRicovero"/>
	<h4 class="titolopagina">
	   	Nuova diagnosi
	</h4>
	
	<table class="tabella">
		
		<tr>
        	<th colspan="2">
        		Dati della diagnosi
        	</th>
        </tr>
	
		<tr>
    		<td>
    			 Data della diagnosi
    		</td>
    		<td>    		
    			 <fmt:formatDate type="date" value="<%=new Date() %>" pattern="dd/MM/yyyy" var="dataOdierna"/> 		
    			 <input type="text" id="dataDiagnosi" name="dataDiagnosi" maxlength="32" size="50" readonly="readonly" style="width:246px;" value="${dataOdierna}"/>
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
	        <TEXTAREA NAME="note" COLS="40" ROWS="6"></TEXTAREA>        
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
							<td>																							
								<input type="checkbox" name="op_${temp.id }" id="op_${temp.id }" /> <label for="op_${temp.id }">${temp.description }</label>
								</td>
								<td>
								<input type="radio" name="tipoDiagnosi_${temp.id}" id="tipoDiagnosi_${temp.id}P" value="Provata"  checked> <label for="tipoDiagnosi_${temp.id}P">Provata</label>
								<input type="radio" name="tipoDiagnosi_${temp.id}" id="tipoDiagnosi_${temp.id}S" value="Sospetta"  > <label for="tipoDiagnosi_${temp.id}S">Sospetta</label>
							</td>											
									
						</tr>
						<c:set var="i" value='${i+1}'/>						
					</c:forEach>					
				</table>
			</div>
	
		
			<h3><a href="#">Diagnosi Chirurgiche</a></h3>
			<div>
				<table class="tabella">	
					<tr class="${i % 2 == 0 ? 'odd' : 'even'}">
						<td>														
							<TEXTAREA NAME="diagnosi" COLS="40" ROWS="4"></TEXTAREA>   
						</td>
						<td>
							<input type="radio" name="tipoDiagnosi" id="tipoDiagnosi" value="Provata"  checked> Provata	
							<input type="radio" name="tipoDiagnosi" id="tipoDiagnosi" value="Sospetta"        > Sospetta					
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
							<td>									
								<input type="checkbox" name="op_${temp.id }" id="op_${temp.id }"/> <label for="op_${temp.id }">${temp.description }</label>
							</td>
							<td>
								<input type="radio" name="tipoDiagnosi_${temp.id}" id="tipoDiagnosi_${temp.id}P" value="Provata"  checked> <label for="tipoDiagnosi_${temp.id}P">Provata</label>	
								<input type="radio" name="tipoDiagnosi_${temp.id}" id="tipoDiagnosi_${temp.id}S" value="Sospetta"  >  <label for="tipoDiagnosi_${temp.id}S">Sospetta</label>							
							</td>
						</tr>	
						<c:set var="i" value='${i+1}'/>												
					</c:forEach>						
				</table>
			</div>
	
			<h3><a href="#">Controllo Periodico</a></h3>
			<div>
				<table class="tabella">
					<tr>
						<td>	
							<c:forEach items="${diagnosiCP}" var="temp" >							
										<input type="checkbox" name="op_${temp.id }" id="op_${temp.id }"/> <label for="op_${temp.id }">${temp.description }</label>						
										<br>				
							</c:forEach>
						</td>
					</tr>
				</table>
			</div>
	</div>
	
	
	<input type="submit" value="Salva" "/>
	<input type="button" value="Annulla" onclick="attendere(), location.href='vam.cc.Detail.us'">
 

</form>
