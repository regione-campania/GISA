<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  --%>

<%@page import="org.aspcfs.modules.opu.base.Stabilimento"%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%><html>
<jsp:useBean id="OperatoreAdded" class="org.aspcfs.modules.opu.base.Operatore" scope="request" /> <!-- soggetto inserito -->
	<%
	String tipologiaSoggetto = (String)request.getAttribute("TipologiaSoggetto");
	int idTipologia = -1 ;
	if (tipologiaSoggetto != null && ! "".equals(tipologiaSoggetto))
		idTipologia = Integer.parseInt(tipologiaSoggetto);
	//System.out.println("Tipologia del soggetto "+idTipologia + " id operatore "+OperatoreAdded.getIdOperatore()+" - nome: "+((LineaProduttiva)(((Stabilimento)OperatoreAdded.getListaStabilimenti().get(0)).getListaLineeProduttive().get(0))).getId());
	System.out.println("************** "+request.getAttribute("IdOrigineOperatore"));
	if(idTipologia==8)
	{
		%>
		<script>
		function setSoggettoFisicoParent()
		{
			<% if (request.getAttribute("IdOrigineOperatore")!=null && ((int)request.getAttribute("IdOrigineOperatore")>=10000000)){ %>
			  window.opener.document.forms[0].idProprietarioProvenienza.value = '<%=request.getAttribute("IdOrigineOperatore")%>';
			<% } else { %>
			  window.opener.document.forms[0].idProprietarioProvenienza.value = '<%= ((LineaProduttiva)(((Stabilimento)OperatoreAdded.getListaStabilimenti().get(0)).getListaLineeProduttive().get(0))).getId()%>';
			<% } %>
		}
		</script>
		
		<%
	} else  if(idTipologia==1)
		{
			%>
			<script>
			function setSoggettoFisicoParent()
			{
				window.opener.document.forms[0].idProprietario.value = '<%= ((LineaProduttiva)(((Stabilimento)OperatoreAdded.getListaStabilimenti().get(0)).getListaLineeProduttive().get(0))).getId()%>';
				
			}
			</script>
			
			<%
		}
		else
		{
			if(idTipologia==2 || idTipologia==4)
			{
				%>
				<script>
			function setSoggettoFisicoParent()
			{
				window.opener.document.forms[0].idDetentore.value = '<%=((LineaProduttiva)(((Stabilimento)OperatoreAdded.getListaStabilimenti().get(0)).getListaLineeProduttive().get(0))).getId()%>';

			}
			</script>
				
				<%
			}
			else
			{
				if(idTipologia==3)
				{
					%>
						<script>
			function setSoggettoFisicoParent()
			{
				window.opener.document.forms[0].idProprietario.value = '<%=((LineaProduttiva)(((Stabilimento)OperatoreAdded.getListaStabilimenti().get(0)).getListaLineeProduttive().get(0))).getId()%>';
				
			}
			</script>
					<%
				}
				else
				{
					%>
						<script>
			function setSoggettoFisicoParent()
			{
				window.opener.document.forms[0].idOperatoreAdded.value = '<%=((LineaProduttiva)(((Stabilimento)OperatoreAdded.getListaStabilimenti().get(0)).getListaLineeProduttive().get(0))).getId()%>';
				
			}
			</script>
					<%
				}
			}
		}
	%>
<script type="text/javascript">


	function submitParent(){
		setSoggettoFisicoParent();
		window.opener.document.forms[0].doContinue.value = 'false';
		window.opener.document.forms[0].submit();
		window.close();
}
</script>
<body onload="javascript:submitParent();">
