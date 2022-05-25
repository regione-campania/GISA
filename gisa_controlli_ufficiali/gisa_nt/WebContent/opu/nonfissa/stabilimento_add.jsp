<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants" %>

<%@ include file="../../initPage.jsp"%>

<script src='javascript/modalWindow.js'></script>
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>
<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>

<script>
function add(form, val){
	if (val=='imbarcazione')
		form.action='OpuStab.do?command=AddImbarcazione';	
	else if (val=='operatore')
		form.action='OpuStab.do?command=AddOperatoreFuoriRegione';
	else if (val=='automezzo')
		form.action='OpuStab.do?command=PrepareAddAutomezzo';
	loadModalWindow();
	form.submit();
}
</script>



<form name="addForm" id = "addForm" action="" method="post">


<input type="button" value="Aggiungi Imbarcazione" onClick="add(this.form, 'imbarcazione')"/>

<input type="button" value="Aggiungi Op. Fuori regione" onClick="add(this.form, 'operatore')"/>

<input type="button" value="Aggiungi Att. mobile/ Automezzo" onClick="add(this.form, 'automezzo')"/>


</form>