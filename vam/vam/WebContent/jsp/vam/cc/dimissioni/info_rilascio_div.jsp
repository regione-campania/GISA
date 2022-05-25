<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<% 
String codiceIspra = (String)request.getParameter("codiceIspra");
%>
<table class="tabella">    
        <tr class="even">
	        <td>
	        	<label id="tdRilascio1">
	       		 Provincia e Comune Rilascio<font color="red"> *</font>
	       		 </label>
	        </td>
	        <td >
	        	<select width:40%" name="provinciaReimmissione"  id="provinciaReimmissione" onChange="javascript: chooseProvinciaReimmissione(this.value)">>
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
	            			<option value="<c:out value="${bn.id}"/>">            				
	            				${bn.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        <div id="comuneReimmissioneNA" style="display:none;">
		        	<select style="width:60%" name="comuneReimmissioneNA" id="comuneReimmissioneNA">
						<option value="0">&lt;--- Selezionare il comune NA  ---&gt;</option>
	    				<c:forEach var="na" items="${listComuniNA}">
	            			<option value="<c:out value="${na.id}"/>">            				
	            				${na.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        <div id="comuneReimmissioneSA" style="display:none;">
		        	<select style="width:60%" name="comuneReimmissioneSA" id="comuneReimmissioneSA">
						<option value="0">&lt;--- Selezionare il comune SA  ---&gt;</option>
	    				<c:forEach var="sa" items="${listComuniSA}">
	            			<option value="<c:out value="${sa.id}"/>">            				
	            				${sa.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        <div id="comuneReimmissioneCE" style="display:none;">
		        	<select style="width:60%" name="comuneReimmissioneCE" id="comuneReimmissioneCE">
						<option value="0">&lt;--- Selezionare il comune CE  ---&gt;</option>
	    				<c:forEach var="ce" items="${listComuniCE}">
	            			<option value="<c:out value="${ce.id}"/>">            				
	            				${ce.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        <div id="comuneReimmissioneAV" style="display:none;">
		        	<select style="width:60%" name="comuneReimmissioneAV" id="comuneReimmissioneAV">
						<option value="0">&lt;--- Selezionare il comune AV  ---&gt;</option>
	    				<c:forEach var="av" items="${listComuniAV}">
	            			<option value="<c:out value="${av.id}"/>">            				
	            				${av.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
	        </td>
        </tr>
                        
        
        <tr class="odd">
	        <td>
	        	<label id="tdRilascio3" >
	       		 	Luogo Rilascio<font color="red"> *</font>
	       		 </label>
	        </td>
	        <td>
	        	<label id="tdRilascio4" >
	        		<input type="text" name="luogoReimmissione" id="luogoReimmissione" maxlength="50" size="50"/>	
	        	</label>	             
	        </td>
	        <td>
	        </td>
        </tr>
        
         <tr class="odd">
	        <td>
	        	<label id="tdRilascio6">
	       		 	Codice ISPRA
	       		 </label>
	        </td>
	        <td>
	        	<input type="text" name="codiceIspra" id="codiceIspra" value="<%=codiceIspra%>" maxlength="50" size="50"/>		             
	        </td>
	        <td>
	        </td>
        </tr>
</table>