<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="it.us.web.util.vam.CaninaRemoteUtil"%>
<%@page import="it.us.web.bean.BUtente"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.naming.Context"%>
<%@page import="it.us.web.bean.vam.lookup.LookupOperazioniAccettazione"%>
<%@page import="it.us.web.action.GenericAction"%>
<%@page import="it.us.web.bean.vam.CartellaClinica"%>
<%@page import="java.util.ArrayList"%>
<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage=""%>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<script language="JavaScript" type="text/javascript" src="js/azionijavascript.js"></script>

<%
	CartellaClinica cc 	= (CartellaClinica)session.getAttribute("cc");
%>


<jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
<h4 class="titolopagina">
     		Lista Leishmaniosi
</h4>


<!-- input type="button" value="Aggiungi Leishmaniosi" onclick="if(${cc.dataChiusura!=null}){ 
	if(myConfirm('Cartella Clinica chiusa. Vuoi procedere?')){location.href='vam.cc.leishmaniosi.ToAdd.us'}
	}else{location.href='vam.cc.leishmaniosi.ToAdd.us'}" -->


<div class="area-contenuti-2">
	<form action="vam.cc.leishmaniosi.List.us" method="post">
		
		<jmesa:tableModel items="${l}" id="l" var="l" filterMatcherMap="it.us.web.util.jmesa.MyFilterMatcherMap" columnSort="it.us.web.util.jmesa.CustomColumnSort">
			<jmesa:htmlTable styleClass="tabella">
				<jmesa:htmlRow>

					<jmesa:htmlColumn property="dataPrelievoLeishmaniosi" title="Data Prelievo" cellEditor="it.us.web.util.jmesa.DateCellEditor" filterEditor="it.us.web.util.jmesa.DateFilterEditor"/>
					
					<jmesa:htmlColumn property="dataEsitoLeishmaniosi" title="Data Esito" cellEditor="it.us.web.util.jmesa.DateCellEditor" filterEditor="it.us.web.util.jmesa.DateFilterEditor"/>
										
					<jmesa:htmlColumn property="lle.description" title="Esito" cellEditor="it.us.web.util.jmesa.vam.EsitoLeishmaniosiCellEditor" filterEditor="it.us.web.util.jmesa.vam.EsitoLeishmaniosiDroplistFilterEditor"/>
																			
					<jmesa:htmlColumn sortable="false" filterable="false" title="Operazioni">
							<a href="vam.cc.leishmaniosi.Detail.us?idLeishmaniosi=${l.id}">Dettaglio</a>
							<!-- a href="vam.cc.leishmaniosi.ToEdit.us?idLeishmaniosi=${l.id}" onclick="if(${cc.dataChiusura!=null}){ 
			    				return myConfirm('Cartella Clinica chiusa. Vuoi procedere?');
	    						}else{return true;}">Modifica</a-->
					</jmesa:htmlColumn>
					
				</jmesa:htmlRow>
			</jmesa:htmlTable>
		</jmesa:tableModel>
		
	</form>
	
	<script type="text/javascript">
		function onInvokeAction(id)
		{
			setExportToLimit(id, '');
			createHiddenInputFieldsForLimitAndSubmit(id);
		}
		function onInvokeExportAction(id)
		{
			var parameterString = createParameterStringForLimit(id);
			location.href = 'vam.cc.leishmaniosi.List.us?' + parameterString;
		}
	</script>
</div>


<br/><br/>


<h4 class="titolopagina">
     		Esiti Leishmaniosi importati da Sigla
</h4>


<div class="area-contenuti-2">
	<form action="vam.cc.leishmaniosi.List.us" method="post">
		<jmesa:tableModel items="${lBdu}" id="lBdu" var="lBdu" filterMatcherMap="it.us.web.util.jmesa.MyFilterMatcherMap" columnSort="it.us.web.util.jmesa.CustomColumnSort">
			<jmesa:htmlTable styleClass="tabella">
				<jmesa:htmlRow>

					<jmesa:htmlColumn property="dataPrelievoLeishmaniosi" title="Data Prelievo" cellEditor="it.us.web.util.jmesa.DateCellEditor" filterEditor="it.us.web.util.jmesa.DateFilterEditor"/>
					
					<jmesa:htmlColumn property="dataAccertamento" title="Data Accertamento" cellEditor="it.us.web.util.jmesa.DateCellEditor" filterEditor="it.us.web.util.jmesa.DateFilterEditor"/>
					
					<jmesa:htmlColumn property="dataEsitoLeishmaniosi" title="Data Esito" cellEditor="it.us.web.util.jmesa.DateCellEditor" filterEditor="it.us.web.util.jmesa.DateFilterEditor"/>
										
					<jmesa:htmlColumn title="Esito" sortable="false" filterable="false">
						${lBdu.esito}, ${lBdu.esitoCar}
					</jmesa:htmlColumn>
																			
				</jmesa:htmlRow>
			</jmesa:htmlTable>
		</jmesa:tableModel>
	</form>
	<br/><br/>
	
	<c:if test="${cc.accettazione.animale.lookupSpecie.id==1 && cc.accettazione.animale.decedutoNonAnagrafe==false && regEffettuabili.prelievoLeishmania}">
		<input type="button" onclick="location.href='vam.cc.leishmaniosi.ToAddPrelCampLeishBdu.us'" value="Inserisci Prelievo Campioni Leishmania"/>
	</c:if>
	
	<form action="vam.cc.leishmaniosi.List.us" method="post">
		<jmesa:tableModel items="${prelievoCampioniLeishmaniaBdu}" id="prelievoCampioniLeishmaniaBdu" var="leish" filterMatcherMap="it.us.web.util.jmesa.MyFilterMatcherMap" columnSort="it.us.web.util.jmesa.CustomColumnSort">
			<jmesa:htmlTable styleClass="tabella">
				<jmesa:htmlRow>

					<jmesa:htmlColumn property="dataPrelievoLeishmaniosi" title="Data Prelievo" cellEditor="it.us.web.util.jmesa.DateCellEditor"  sortable="false" filterable="false" />
					
					<jmesa:htmlColumn property="veterinario" title="Veterinario" sortable="false" filterable="false"/>
					
					<jmesa:htmlColumn sortable="false" filterable="false" title="Operazioni">
						<c:choose>
						<c:when test="${leish.id>0}">
							<a id="dettaglio" href="javascript:dettaglioReg('myFrame',${leish.id});">Dettaglio</a>
						</c:when>
						<c:otherwise>
							<a id="dettaglio" href="">Esegui</a>
						</c:otherwise>
						</c:choose>
						
					</jmesa:htmlColumn>
																			
				</jmesa:htmlRow>
			</jmesa:htmlTable>
		</jmesa:tableModel>
	</form>
	
	<br/>
<%
	ArrayList lBdu2 = (ArrayList)request.getAttribute("lBdu2");	
	if(lBdu2!=null && !lBdu2.isEmpty() && (cc.getDataChiusura()==null || (cc.getDataChiusura()!=null && lBdu2!=null && !lBdu2.isEmpty())))
	{
%>
		<form action="vam.cc.leishmaniosi.AddEditMod5.us" method="post" id="formMod5">
		<table width="95%">
			<tr>
				<th>
   					Modello 5/A
				</th>
			</tr>
			<tr class="even">
				<td>
					Numero 
					<c:if test="${cc.dataChiusura!=null}">
						${cc.numMod5}
					</c:if>
					<c:if test="${cc.dataChiusura==null}">
						<input type="text" value="${cc.numMod5}" name="numMod5" id="numMod5"> 
						<font color="red">*</font>
					</c:if>
					del 
					<c:if test="${cc.dataChiusura!=null}">
						<fmt:formatDate type="date" value="${cc.dataMod5}" pattern="dd/MM/yyyy"/>
					</c:if>
					<c:if test="${cc.dataChiusura==null}">
						<input type="text" id="dataMod5" name="dataMod5" maxlength="32" readonly="readonly" style="width:100px;" value="<fmt:formatDate type="date" value="${cc.dataMod5}" pattern="dd/MM/yyyy"/>"/>
			    		<img src="images/b_calendar.gif" alt="calendario" id="id_img_1" />
			    		<font color="red">*</font>
			 			<script type="text/javascript">
			      			Calendar.setup({
			        			inputField     :    "dataMod5",     // id of the input field
			        			ifFormat       :    "%d/%m/%Y",      // format of the input field
			       				button         :    "id_img_1",  // trigger for the calendar (button ID)
			       				// align          :    "Tl",           // alignment (defaults to "Bl")
			        			singleClick    :    true,
			        			timeFormat		:   "24",
			        			showsTime		:   false
			   				});					    
			  			</script>
			  		</c:if>
			  		<br/>
					<c:if test="${cc.dataChiusura==null}">
						<font color="red">Campi obbligatori</font>
			  			<br/>
						<input type="button" onclick="if(checkMod5()){document.getElementById('formMod5').submit();}" value='<c:choose><c:when test="${cc.dataMod5!=null}">Modifica</c:when><c:otherwise>Inserisci</c:otherwise> </c:choose>'>
					</c:if>
				</td>
			</tr>
		</table>
		</form>
<%
	}
%>
</div>




<div id="dettaglio_reg" title="Dettaglio Registrazione di Prelievo Leishmania">
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
	function checkMod5()
	{
		if(document.getElementById('dataMod5').value=='' || document.getElementById('numMod5').value=='')
		{
			alert("Inserire numero e data del Modello 5/A");
			return false;
		}
		else
			return true;
	}
	
	
	function logoutFromCanina( idFrame )
	{
		document.getElementById(idFrame).src='<%=CaninaRemoteUtil.getLogoutUrl( (BUtente)session.getAttribute( "utente" ),request ) %>';
	};
	
	
	function dettaglioReg(idFrame,idRegBdr)
	{		
		var loginUrl = '<%=CaninaRemoteUtil.getLoginUrl( (BUtente)session.getAttribute( "utente" ), null, cc.getAccettazione().getAnimale(), (LookupOperazioniAccettazione)request.getAttribute("operazione"), ((CartellaClinica)session.getAttribute("cc")).getAccettazione(), 1, connection,request,"2" ) %>';
		loginUrl = loginUrl + '&id='+idRegBdr;
		document.getElementById(idFrame).src=loginUrl;
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

