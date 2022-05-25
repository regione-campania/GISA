<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="newStabilimento" class="org.aspcfs.modules.suap.base.Stabilimento" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />


<legend><b>GESTIONE ANAGRAFICA STABILIMENTO</b></legend>
<div align="center"><FONT COLOR="RED">Cliccare su "Salva pratica" per confermare l'inserimento.</FONT></div>	


<div id="test2"></div>
 	

<%--
<legend><b>SCHEDA STABILIMENTO</b></legend>
<div align="center"><FONT COLOR="RED">LA RICHIESTA E' STATA INSERITA CORRETTAMENTE E DOVRA' ESSERE ORA VALIDATA DALL'ASL O DALL'ENTE REGIONALE.</FONT></div>	
	
	
	<div align="right">
	<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle"
		height="16" width="16" /> 
		
		<div id = "divStampa"></div>

</div>
	
	<%
	if ("_ext".equalsIgnoreCase( (String)application.getAttribute("SUFFISSO_TAB_ACCESSI")))
	{
	%>
	<input type = "button" value="Torna Al Suap" onclick="logOutSuap()" >
	<%} %>
<iframe  id="test2"
	src="./schede_centralizzate/iframe.jsp?objectId=<%=newStabilimento.getAltId()%>&objectIdName=alt_id&tipo_dettaglio=28"
	name="test" height="auto">
	
 </iframe>



<div id ="formHidden">


</div>




<script>

	function intercettaBottoneTornaAlSuap(urlDestinazione)
	{
		
		//urlDestinazione="http://www.google.it";
		var formToUse = document.createElement("form");
		formToUse.method = "POST";
		formToUse.action =urlDestinazione;
		
		
		
		for(var i = 0; i < window.listaDatiRichiesta["entries"].length; i++)
		{	  
			
			
			for(var nomeCampo in window.listaDatiRichiesta["entries"][i])
			{
				var newInput = document.createElement("input");
				newInput.type="hidden";
				newInput.name=nomeCampo;
				newInput.value = window.listaDatiRichiesta["entries"][i][nomeCampo];
				
				formToUse.appendChild(newInput);
			}
		}
		
		document.getElementById("formHidden").appendChild(formToUse);
		formToUse.appendChild(newInput);
		document.body.appendChild(formToUse);
		formToUse.submit();
		
	}
	
	

		 
function logOutSuap()
{
		
	             $.ajax({
	                 type: 'GET',
	                 dataType: "json",
	                 cache: false,
	                 url: 'Login.do?command=LogoutSuap',
	                 data: ''+$(this).serialize(), 
	                 success: function(msg) {
	                
	                	 if(msg.esito=='OK')
	                	 	intercettaBottoneTornaAlSuap(msg.callbackSuap);
	                	 else
	                		 intercettaBottoneTornaAlSuap(msg.callbackSuapKo);
	                 
	             },
	             error: function (err){
	                     alert('ko '+err.responseText);
	             }
	     });
}
	 
</script>

--%>