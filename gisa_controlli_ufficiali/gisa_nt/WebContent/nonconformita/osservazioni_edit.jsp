<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Iterator"%>
<link href="css/nonconformita.css" rel="stylesheet" type="text/css" />

<jsp:useBean id="Macrocategorie" class="org.aspcfs.utils.web.LookupList" scope="request"/>



<div class="tabber">
<div class="tabbertab">
 <%@ include file="osservazioni_formali_edit.jsp" %>

</div>
<div class="tabbertab">
 <%@ include file="osservazioni_significative_edit.jsp" %>

</div>
 
 <div class="tabbertab">
 <%@ include file="osservazioni_gravi_edit.jsp" %>

 </div>
 </div>


<input type = "hidden" id ="abilita_formali" name = "abilitanc_formali" value = "<%=request.getAttribute("abilita_formali") %>">
<input type = "hidden" id = "abilita_significative" name = "abilitanc_significative"  value = "<%=request.getAttribute("abilita_significative") %>">
<input type = "hidden" id = "abilita_gravi" name = "abilitanc_gravi"  value = "<%=request.getAttribute("abilita_gravi") %>">
<input type = "hidden" id ="formali" name = "formali" value = "<%=request.getAttribute("Formali") %>">
<input type = "hidden" name = "idIspezione" value = "<%=CU.getTipoIspezione() %>">

<script>
resetElementi(0,0,0,1,1,1);
</script>


