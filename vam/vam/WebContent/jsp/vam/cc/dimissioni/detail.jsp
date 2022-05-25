<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.bean.vam.CartellaClinicaNoH"%>
<%@page import="it.us.web.action.GenericAction"%>
<%@page import="it.us.web.constants.IdOperazioniBdr"%>
<%@page import="it.us.web.bean.vam.lookup.LookupDestinazioneAnimale"%>
<%@page import="it.us.web.util.vam.RegistrazioniUtil"%>
<%@page import="it.us.web.bean.vam.lookup.LookupOperazioniAccettazione"%>
<%@page import="it.us.web.util.vam.CaninaRemoteUtil"%>
<%@page import="it.us.web.bean.vam.CartellaClinica"%>
<%@page import="it.us.web.bean.BUtente"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.naming.InitialContext"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>
<%@page import="java.util.Date"%>
<%@page import="it.us.web.bean.BUtente"%>


<%
    CartellaClinicaNoH cc 	= (CartellaClinicaNoH)request.getAttribute("cc");
	int idRegBdr 		= RegistrazioniUtil.getIdRegBdr(cc, (LookupOperazioniAccettazione)request.getAttribute("operazione"));
	BUtente utente = (BUtente)request.getSession().getAttribute("utente");
%>
  
<script language="JavaScript" type="text/javascript" src="js/vam/cc/dimissioni/add.js"></script>
<c:choose>
	<c:when test="${cc.accettazione.animale.decedutoNonAnagrafe}">
		<fmt:formatDate value="${cc.accettazione.animale.dataMorte}" pattern="dd/MM/yyyy" var="dataMorte"/> 
	</c:when>
	<c:otherwise>
		<fmt:formatDate value="${res.dataEvento}" pattern="dd/MM/yyyy" var="dataMorte"/> 
	</c:otherwise>
</c:choose>

<form action="vam.cc.dimissioni.Add.us" name="form" method="post" id="form" class="marginezero" onsubmit="javascript:return checkform(this);">
           
    <input type="hidden" name="idAutopsia" value="<c:out value="${a.id}"/>"/>
    
    <jsp:include page="/jsp/vam/cc/menuCC.jsp"/>   
    <h4 class="titolopagina">
     		Dimissioni
    </h4>
    <table class="tabella">
    
    <tr class='even'>
    		<td>
    			 Data Dimissione
    		</td>
    		<td> 
    			 <fmt:formatDate type="date" value="${cc.dataChiusura}" pattern="dd/MM/yyyy" var="data"/>
    			 ${data} 
    			 	<c:if test="${utente.id==cc.enteredBy.id || utente.ruoloByTalos=='HD1' || utente.ruoloByTalos=='HD2'}">      			 
    			 	  <input type="button" value="Riapri" onclick="if( myConfirm('Sei sicuro di voler riaprire la cartella clinica?') ){location.href='vam.cc.Riapertura.us?id=<%=cc.getId()%>'}" />
					</c:if>
    		</td>    		
        </tr>
        <tr class='odd'>
	        <td>
	    		Destinazione dell'animale
    		</td>
    		<td>
    			<c:choose>
    				<c:when test="${cc.destinazioneAnimale!=null}">
    					<c:if test="${idTipoAttivitaBdrCompletata>0}">
    					  <%if (idRegBdr != 0){ %>
							<a id="href">
						  <% } %>
						</c:if>
							<c:if test="${cc.accettazione.animale.lookupSpecie.id==3}">
    							${cc.destinazioneAnimale.descriptionSinantropo }
    						</c:if>
    						<c:if test="${cc.accettazione.animale.lookupSpecie.id==1 or cc.accettazione.animale.lookupSpecie.id==2}">
    							${cc.destinazioneAnimale.description}
    						</c:if>
    						<c:if test="<%=cc.getDestinazioneAnimale().getId()==3 && cc.getAdozioneFuoriAsl()!=null && cc.getAdozioneFuoriAsl()%>">
								fuori Asl
							</c:if>
							<c:if test="<%=cc.getDestinazioneAnimale().getId()==3 && cc.getAdozioneVersoAssocCanili()!=null && cc.getAdozioneVersoAssocCanili()%>">
								verso Associazioni/Canili
							</c:if>
    						
    						
						<c:if test="${idTipoAttivitaBdrCompletata>0}">
						<%if (idRegBdr != 0){ %>
							</a>
						<%} %>
						</c:if>
    				</c:when>
    				<c:otherwise>
    					<c:choose>
    						<c:when  test="${not empty cc.trasferimenti}">
    							Trasferito presso la clinica <c:forEach var="trasf" items="${cc.trasferimenti}" end="0">'${trasf.clinicaDestinazione.nome}'</c:forEach> 
		    				</c:when>
		    				<c:otherwise>
    							<c:if test="${not empty cc.trasferimentiByCcPostTrasf}">
    								Riconsegnato alla clinica <c:forEach var="trasf" items="${cc.trasferimentiByCcPostTrasf}" end="0">'${trasf.clinicaOrigine.nome}'</c:forEach> 
    							</c:if>
    						</c:otherwise>
    					</c:choose>
    				</c:otherwise>
    			</c:choose>
	        </td>
	        <td>
	        </td>
        </tr>
        <tr class='even'>
	        <fmt:formatDate type="date" value="${cc.dimissioniEntered}" pattern="dd/MM/yyyy" var="data"/>    			     			 
        	<td>Dimissione inserita da </td>
        	<td>${cc.dimissioniEnteredBy} il ${data}</td><td></td>
        </tr>
<%--         <tr class='odd'>
        	<td>Data dimissione</td>
        	<td><fmt:formatDate pattern="dd/MM/yyyy" value="${cc.dimissioniEntered}"/></td><td></td>
        <tr> --%>
        
        <c:if test="${idTipoAttivitaBdrCompletata==null && ((cc.destinazioneAnimale.id==1 && ritornoDaComunicare) || cc.destinazioneAnimale.id==4 || cc.destinazioneAnimale.id==2 || cc.destinazioneAnimale.id==3 || cc.destinazioneAnimale.id==5  ) && cc.accettazione.animale.lookupSpecie.id!=3}">
			<tr>
				<td>Operazioni da Eseguire in BDR:</td>
				<td>
					${operazione} 
					<us:can f="BDR" sf="ADD" og="MAIN" r="w">
						<input type="button" value="Esegui" onclick="attendere(),location.href='vam.cc.dimissioni.TestRegistrazioni.us?idCc=${cc.id}'" />
					</us:can>
				</td>
			</tr>
		</c:if>
        
        <c:if test="${cc.destinazioneAnimale.id==2}">
        <tr class='even'>
    		<td>
    			Data del decesso
    		</td>
    		<td>
    			<c:choose>
	    			<c:when test="${cc.accettazione.animale.decedutoNonAnagrafe}">
	    				<fmt:formatDate value="${cc.accettazione.animale.dataMorte}" pattern="dd/MM/yyyy" var="dataMorte"/>
	    				${dataMorte}
	    			</c:when>
	    			<c:otherwise>
	    				<fmt:formatDate value="${res.dataEvento}" pattern="dd/MM/yyyy" var="dataMorte"/>
	    				${dataMorte}
	    			</c:otherwise>
	    		</c:choose>
			</td>
			<td>						
				<c:choose>
	    			<c:when test="${cc.accettazione.animale.decedutoNonAnagrafe}">
	    				<c:if test="${cc.accettazione.animale.dataMortePresunta!=null}">
	    					<c:choose>	    			
								<c:when test="${cc.accettazione.animale.dataMortePresunta == true}">					
									Presunta
								</c:when>
								<c:otherwise>
									Certa
								</c:otherwise>	
							</c:choose>
						</c:if>
	    			</c:when>
	    			<c:otherwise>
	    				<c:choose>	    			
							<c:when test="${res.dataDecessoPresunta == true}">					
								Presunta
							</c:when>
							<c:otherwise>
								Certa
							</c:otherwise>	
						</c:choose>
	    			</c:otherwise>
	    		</c:choose>
	        </td>
    	</tr>
    	
    	<tr class='odd'>
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
        </c:if>
	</table>
</form>


<div id="dettaglio_reg" title="Dettaglio Registrazione di ${cc.destinazioneAnimale.description}">
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

	function dettaglioReg( idAccettazione, idOperazione, idFrame)
	{		
		document.getElementById(idFrame).src='<%=CaninaRemoteUtil.getLoginUrl( (BUtente)session.getAttribute( "utente" ), null, cc.getAccettazione().getAnimale(), (LookupOperazioniAccettazione)request.getAttribute("operazione"), ((CartellaClinicaNoH)request.getAttribute("cc")).getAccettazione(), idRegBdr, connection,request ,"2") %>';
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
	Integer idTipoAttivitaBdrCompletata = (Integer)request.getAttribute("idTipoAttivitaBdrCompletata");	
	Integer idTipoAttivitaBdrRichiesta = (Integer)request.getAttribute("idTipoAttivitaBdrRichiesta");	
	LookupOperazioniAccettazione operazione = (LookupOperazioniAccettazione)request.getAttribute("operazione");
	
	LookupDestinazioneAnimale dest = cc.getDestinazioneAnimale();
	if(idTipoAttivitaBdrCompletata==idTipoAttivitaBdrRichiesta && (idTipoAttivitaBdrCompletata!=null && idTipoAttivitaBdrRichiesta!=null))
	{
		idRegBdr = RegistrazioniUtil.getIdRegBdr(cc, operazione);
%>
		<div id="dettaglio_reg" title="Dettaglio Registrazione di <%=dest.getDescription()%>">
			<iframe id="myFrame" name="myFrame" frameborder="0" vspace="0" hspace="0" marginwidth="0" marginheight="0"
					width="99%"  		   scrolling=yes  			height="98%"
					style="BORDER-RIGHT: black 1px solid; 			BORDER-TOP: black 1px solid; 		  Z-INDEX: 999; 
					BORDER-LEFT: black 1px solid; 
					BORDER-BOTTOM: black 1px solid;"
					src="">
			</iframe>
		</div>
		
		<script type="text/javascript">
		
			//Se c'è il link, quindi registrazione avvenuta in BDR
			if(document.getElementById("href")!=null)
				document.getElementById("href").href="javascript:dettaglioReg(<%=cc.getId()%>,<%=operazione.getId()%>,'myFrame','<%=CaninaRemoteUtil.getLoginUrl( (BUtente)session.getAttribute( "utente" ), null, cc.getAccettazione().getAnimale(), operazione, cc.getAccettazione(), idRegBdr, connection,request ,"2") %>');";
		
		
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
								logoutFromCanina("myFrame");
								$( this ).dialog( "close" );
							}
						}
				});
			});
		</script>
		
		
<%
	}
%>


<%
connection.close();
GenericAction.aggiornaConnessioneChiusaSessione(request);
%>

