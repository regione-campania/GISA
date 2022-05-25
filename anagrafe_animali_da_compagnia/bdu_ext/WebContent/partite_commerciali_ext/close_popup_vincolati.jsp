<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="PartitaCommerciale" class="org.aspcfs.modules.partitecommerciali_ext.base.PartitaCommerciale" scope="request" />
	<%
	//	request.setAttribute("PartitaCommerciale", PartitaCommerciale);
	
	%>
	
	<%=PartitaCommerciale.getListMicrochipAnimaliConVincolo().get(0) %>
<script type="text/javascript">




	function submitParent(){
	var sel = window.opener.document.forms[0].listMicrochipAnimaliConVincolo;
	window.opener.document.forms[0].listMicrochipAnimaliConVincolo.options.length = 0;
	<%for (int i = 0; i < PartitaCommerciale.getListMicrochipAnimaliConVincolo().size(); i++){
		%>
		sel.options[sel.options.length]=new Option(<%=PartitaCommerciale.getListMicrochipAnimaliConVincolo().get(i)%>,<%=PartitaCommerciale.getListMicrochipAnimaliConVincolo().get(i)%>);
	<%}
	%>
	loop_select();
	window.close();
	
}


	function loop_select() {
		 for(i=0; i<=window.opener.document.forms[0].listMicrochipAnimaliConVincolo.length-1; i++) { 
			 window.opener.document.forms[0].listMicrochipAnimaliConVincolo.options[i].selected = true; } 
		 }
</script>
<body onload="javascript:submitParent();">
