<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Iterator"%>
<jsp:useBean id="Macrocategorie" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<link href="css/osservazioni.css" rel="stylesheet" type="text/css" />
<!-- FINE CODICE PER POPUP MODALE JQUERY -->
<link href="css/nonconformita.css" rel="stylesheet" type="text/css" />



<div class="tabber">
<div class="tabbertab">
 <%@ include file="osservazioni_formali_add.jsp" %>

</div>
<div class="tabbertab">

 <%@ include file="osservazioni_significative.jsp" %>

</div>
 
 <div class="tabbertab">
 <%@ include file="osservazioni_gravi.jsp" %>

 </div>
 </div>


<input type = "hidden" name = "idIspezione" value = "<%=CU.getTipoIspezione() %>">




<script>
resetElementi(0,0,0,1,1,1);
</script>





