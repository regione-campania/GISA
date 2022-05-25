<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.action.GenericAction"%>
<%@page import="it.us.web.bean.vam.lookup.LookupOperazioniAccettazione"%>
<%@page import="it.us.web.util.vam.RegistrazioniUtil"%>
<%@page import="it.us.web.bean.vam.CartellaClinica"%>
<%@page import="it.us.web.bean.BUtente"%>
<%@page import="it.us.web.util.vam.CaninaRemoteUtil"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.naming.InitialContext"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<%
	CartellaClinica cc 	= (CartellaClinica)session.getAttribute("cc");
	Integer idRegBdr 	= (Integer)request.getAttribute("idRegBdr");
%>

<form name="form" method="post" class="marginezero" >
           
    
    <jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
    <h4 class="titolopagina">
     		Chirurgia
    </h4>
	
    <table class="tabella">
    	
    	
    	<c:if test="${animaleAnagrafica.dataSterilizzazione!=null}">
	    	<tr>
	        	<th colspan="2">
	        		Sterilizzazione
	        	</th>
	        </tr> 
	        <tr class='odd'>
	    		<td>
	    			Data
	    		</td>
	    		<td> 
	    			<fmt:formatDate type="date" value="${animaleAnagrafica.dataSterilizzazione}" pattern="dd/MM/yyyy" var="dataSterilizzazione" /> ${dataSterilizzazione}
	    		</td>
	    	</tr>
	    	<tr class='even'>
	    		<td>
	    			Operatore
	    		</td>
	    		<td> 
	    			${animaleAnagrafica.operatoreSterilizzazione}
	    		</td>
	    	</tr>
	    	<tr class='odd'>
	    		<td>
	    			<a href="javascript:dettaglioReg('myFrame');">Maggiori informazioni</a>
	    		</td>
	    		<td> 
	    			
	    		</td>
	    	</tr>
	    	
	    	
	    	
    	</c:if>
    	
    	<tr>
        	<th colspan="2">
        		Scelta Tipo Intervento
        	</th>
        </tr>              
        
    	<tr class='odd'>
    		<td>
    			Tipo
    		</td>
    		<td>
    			<select id="tipoIntervento">
					<option value="">&lt;--- Selezionare il tipo ---&gt;</option>
					<c:if test="${cc.accettazione.animale.lookupSpecie.id!=specie.sinantropo && regEffettuabili.sterilizzazione}">
						<option value="ToAdd.us">Inserisci Sterilizzazione</option>
					</c:if>
					<option value="tipoIntervento.List.us">Gestione Altri Interventi</option>
    			</select>
    		</td>
        </tr>
        	
        <tr class='even'>
        	<td>
        	</td>
    		<td>    			
    			<input type="button" value="Prosegui" onclick="if(document.getElementById('tipoIntervento').value==''){alert('Selezionare un tipo');}else{location.href='vam.cc.chirurgie.'+document.getElementById('tipoIntervento').value;}"/>
    		</td>
        </tr>
	</table>
</form>





<div id="dettaglio_reg" title="Dettaglio Registrazione di sterilizzazione">
	<iframe id="myFrame" name="myFrame" frameborder="0" vspace="0" hspace="0" marginwidth="0" marginheight="0"
			width="99%"  		   scrolling=yes  			height="98%"
			style="BORDER-RIGHT: black 1px solid; 			BORDER-TOP: black 1px solid; 		  Z-INDEX: 999; 
			BORDER-LEFT: black 1px solid; 
			BORDER-BOTTOM: black 1px solid;"
			src="">
	</iframe>
</div>
		
<%
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
		Connection connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(request);
		%>
		
<script type="text/javascript">
	function logoutFromCanina( idFrame )
	{
		document.getElementById(idFrame).src='<%=CaninaRemoteUtil.getLogoutUrl( (BUtente)session.getAttribute( "utente" ),request ) %>';
	};
	
	function dettaglioReg(idFrame)
	{		
		document.getElementById(idFrame).src='<%=CaninaRemoteUtil.getLoginUrl( (BUtente)session.getAttribute( "utente" ), null, cc.getAccettazione().getAnimale(), (LookupOperazioniAccettazione)request.getAttribute("operazione"), ((CartellaClinica)session.getAttribute("cc")).getAccettazione(), idRegBdr, connection,request,"2" ) %>';
		$( '#dettaglio_reg' ).dialog( 'open' );
	};
	
	$(function() 
			{
				$( "#dettaglio_reg" ).dialog({
					height: screen.height/1.5,
					modal: true,
					autoOpen: false,
					closeOnEscape: true,
					show: 'blind',
					resizable: true,
					draggable: true,
					width: screen.width/1.2,
					buttons: {
						"Chiudi": function() {
							logoutFromCanina('myFrame');
							$( this ).dialog( "close" );
						}
					}
			});
		});
</script>

<%
connection.close();
GenericAction.aggiornaConnessioneChiusaSessione(request);
%>