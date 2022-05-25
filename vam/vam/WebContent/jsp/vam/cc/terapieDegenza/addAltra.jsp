<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="java.util.Date"%>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>


<script language="JavaScript" type="text/javascript" src="js/vam/cc/terapieDegenza/addAltra.js"></script>

         
  
    <jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
   	<h4 class="titolopagina">
     		Nuova Terapia
    </h4> 
   
    <table class="tabella">
    	
    	<tr>
        	<th colspan="2">
        		Dettagli dell'assegnazione della terapia effettuata dal Dott. ${td.enteredBy.nome} ${td.enteredBy.cognome} 
    			in data <fmt:formatDate type="date" value="${td.data}" pattern="dd/MM/yyyy" var="data"/>
    			 <c:out value="${data}"/>
        		
        		
        	</th>
        </tr>
    </table>	
    	
    
<INPUT type="button" value="Aggiungi Terapia" onclick="addAssegnazione();" />	
		
<div id="assegnazione_div" title="Assegnazione alla terapia">
<form id="assForm" name="assForm" action="vam.cc.terapieDegenza.AddAltra.us" method="post">
	<input type="hidden" name="idDc" value="" />
	<input type="hidden" name="idTerapiaDegenza" value="${td.id}" />
	
	 <table class="tabella">
		<tr>
			<td>
				Modalità di somministrazione<font color="red"> *</font>
			</td>
			<td>
				<select name="modalitaSomministrazione">
		        	 <c:forEach items="${modalitaSomministrazione}" var="ms" >	
		        	 	<option value="${ms.id }">${ms.description }</option>	        	 				
					</c:forEach>
	        	</select>
			</td>
		</tr>	
	
		<tr>
			<td>
				Tempi (in giorni)<font color="red"> *</font>
			</td>
			<td>
				<input type="text" name="tempi" maxlength="5" size="5"/>
			</td>
		</tr>		
	
		<tr>
			<td>
				Note
			</td>
			<td>
				<TEXTAREA NAME="note" COLS=45 ROWS=7></TEXTAREA>
			</td>
		</tr>															
		
		 
      </table>
    </form>	
 </div>
	

<script type="text/javascript">
	$(function() 
	{
		$( "#assegnazione_div" ).dialog({
			height: screen.height/1.5,
			modal: true,
			autoOpen: false,
			closeOnEscape: true,
			show: 'blind',
			resizable: true,
			draggable: true,
			width: screen.width/1.5,
			buttons: {
				"Annulla": function() {
					$( this ).dialog( "close" );
				},
				"Salva": function() {
					document.assForm.submit();
					//location.href=("vam.cc.diarioClinico.EditOsservazioni.us?idDc=${dc.id }&osservazioni=" + osservazioni);
					//confermaModifiche(true,riga);
					//$( this ).dialog( "close" );
				}
			}
		});
	});

	function addAssegnazione(  )
	{
		//document.ossForm.osservazioni.value = unescape(String( testo ).replace( /\+/g, " " ));//urlDecode( testo );
		$( '#assegnazione_div' ).dialog( 'open' );
	};	
	
	function editAssegnazione( id )
	{
		document.assForm.idDc.value = id;
		//document.ossForm.osservazioni.value = unescape(String( testo ).replace( /\+/g, " " ));//urlDecode( testo );
		$( '#assegnazione_div' ).dialog( 'open' );
	};	
</script>

	
	


	
 
