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

<%@page import="java.util.Iterator"%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%><html>
<jsp:useBean id="LineeProduttiveList"
	class="org.aspcfs.modules.opu.base.LineaProduttivaList" scope="request" />
<!-- sede inserita -->
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<!-- Gestire campi diversi per sedi diverse in base a tipologia sede in SedeAdded -->
<%@ include file="../initPage.jsp"%>
<%@ include file="../initPopupMenu.jsp"%>

<script type="text/javascript">
	var tipoSelezioneGlobale;

	function submitParent(lp) {
		var clonato = opener.document.getElementById('idLineaProduttiva');

		if (clonato != null) {

			clone = clonato.cloneNode(true);
			clone.value = lp;
			clonato.parentNode.appendChild(clone);
		}

	}
	var index = 1;
	
</script>
<%
if (LineeProduttiveList != null)
{
Iterator<LineaProduttiva> itlpList = LineeProduttiveList.iterator();
int indice = 0 ;
String lineeproduttive = "" ;
while (itlpList.hasNext())
{
	LineaProduttiva lp = itlpList.next();
	%>
<script type="text/javascript">
	submitParent(
<%=lp.getId()%>
	);
</script>

<%
}
}

%>
<script type="text/javascript">
	if (window.opener.document.forms[0].doContinueStab != null)
		window.opener.document.forms[0].doContinueStab.value = 'false';



	window.opener.document.forms[0].doContinueStab.value = 'false';
	window.opener.loadModalWindow();
	
	window.opener.document.forms[0].submit();

	window.close();
</script>
<body>