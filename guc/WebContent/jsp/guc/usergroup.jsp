<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>
<%@page import="it.us.web.util.guc.GUCEndpoint"%>
<%@page import="java.util.List"%>
<%@page import="it.us.web.bean.guc.Ruolo"%>
<%@page import="java.util.HashMap"%>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>


<%@page import="java.util.ArrayList"%><script type="text/javascript" src="/js/jquery/jquery-1.3.2.min.js" ></script>

<style type="text/css">
#Copia ed adattamento del css contenuto in css > vam > homePage
#content
{
margin:0 auto;
width:899px;
}
#content p
{
font:normal 12px/18px Arial, Helvetica, sans-serif;
padding:10px;
color:#333333;
}
#content_right
{
margin:0 auto;
width:860px;
padding:5px;
}
#content h3
{
font:bold 12px/20px Arial, Helvetica, sans-serif;
color:#607B35;
}
#content_row1
{
margin:0 auto;
width:670px;
height:175px;
background:url('images/homePage/pets_clinic_08.gif') no-repeat 0 0;
padding-left:250px
}
#content_row2
{
margin:0 auto;
width:625px;
}



</style>

<script type="text/javascript">

$(
		function(){
			abilitaComboRuoli();
		}
);

function abilitaComboRuoli(){

	var combo = $("#endpoint");
	
	$("select[name='ruolo']").hide();
	$("select[name='ruolo']").attr('disabled','disabled');
	
	if(combo.val() != ''){
		$("#" + combo.val() ).attr('disabled','');
		$("#" + combo.val() ).show();
	}
	else{
		$("#tuttiRuoli").attr('disabled','');
		$("#tuttiRuoli").show();
	}
			
}

</script>

<%
boolean utentiAttivi = Boolean.parseBoolean(request.getAttribute("utentiAttivi").toString());
String endpointSelezionato = request.getAttribute("endpoint") != null ? request.getAttribute("endpoint").toString() : "";
int ruoloSelezionato = request.getAttribute("ruolo") != null ? Integer.parseInt(request.getAttribute("ruolo").toString()) : -1;
%>



<div id="content">
<center>
  
<h4 class="titolopagina">Lista Utenti <%= utentiAttivi ? "Attivi":"NON Attivi" %> </h4>

<div class="area-contenuti-2">
	<form name="group" action="ToGroup.us" method="post">
			
		<table width="40%" style="text-align: center;">
		
		<tr>
		<td colspan="3">
			<input type="button" onclick="document.forms[0].submit();" value="Seleziona"/>
		</td>
		</tr>
		
		<tr>
			<th width="25%">Utenti</th>
			<th width="25%">Endpoint</th>
			<th width="50%">Ruoli</th>
		</tr>
		<tr>
		<td>
			<select name="utentiAttivi" id="utentiAttivi" style="width: 100%;" >
				<option value="true" <%if(utentiAttivi){ %>selected="selected"<%} %> >Attivi</option>
			</select>
		</td>
		
		<td>
			<select name="endpoint" id="endpoint" onchange="javascript:abilitaComboRuoli();" style="width: 100%;">
				<option value="">-- Tutti --</option>
				<c:forEach items="${endpoints}" var="e">
					<option value="${e}" <c:if test="${e == endpoint}">selected="selected"</c:if> >${e}</option>
				</c:forEach>
			</select>
		</td>
		
		<td>

		<%for(GUCEndpoint e : GUCEndpoint.values()){ 
			ArrayList<Ruolo> listaRuoli = (ArrayList<Ruolo>)request.getAttribute("listaRuoli" + e); 
		%>
			<select name="ruolo" id="<%= e %>" style="width: 100%;">
				<optgroup label="<%= e %>">
				<option value="">-- Tutti --</option>
				<%
				if(listaRuoli.size() > 0){
					for(Ruolo r : listaRuoli){ 
				%>
						<option value="<%= r.getRuoloInteger() %>" <%if(r.getRuoloInteger() == ruoloSelezionato){ %>selected="selected"<%} %> ><%= r.getRuoloString() %></option>
				<%	
					}
				} 
				%>
				</optgroup>
			</select>
			<%
		} %>
		
		</td>
	
		</tr>
	
		</table>
		<br>
		<input type="button" onclick="checkSelect();" value="Raggruppa Utenti Selezionati"/>
		
		<jmesa:tableModel items="${utentiList}" id="utentiList" var="u" filterMatcherMap="it.us.web.util.jmesa.MyFilterMatcherMap" columnSort="it.us.web.util.jmesa.CustomColumnSort">
			<jmesa:htmlTable styleClass="tabella">
				<jmesa:htmlRow>

					<jmesa:htmlColumn property="username" title="Username" />
					<jmesa:htmlColumn property="nominativo" title="Cognome , Nome" />
					<jmesa:htmlColumn property="asl.nome" title="Asl" filterEditor="it.us.web.util.jmesa.AslDroplistFilterEditor" />
					
					<c:forEach items="${endpoints}" var="e">
					<jmesa:htmlColumn  property="${e}" title="Ruolo ${e}" cellEditor="it.us.web.util.jmesa.HashMapCellEditor" filterable="false">
					</jmesa:htmlColumn>
					</c:forEach> 
					<jmesa:htmlColumn sortable="false" filterable="false" title="Seleziona" width="68px">			
						<input type="checkbox" id="groups" name="groups" value="${u.id}"/>
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
			location.href = 'ToGroup?' + parameterString;
		}
		
		function checkSelect()
		{
			var formtest = false;
			var count = 0;
		     //For each checkbox see if it has been checked, record the value.
			  for (i = 0; i < document.forms[0].groups.length; i++)
				if (document.forms[0].groups[i].checked){
				   count = count +1;	
				   formtest = true;
				   if(count == 2)
					   break;
				}
		     
			if(formtest && count >=2)
				document.forms[0].submit();
			else
				alert('Controllare di aver selezionato almeno due utenti per il raggruppamento');
		}

		
	</script>
</div>
</center>
  
</div>


