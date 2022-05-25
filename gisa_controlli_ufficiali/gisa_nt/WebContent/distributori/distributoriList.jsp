<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>


<table class="trails" cellspacing="0">
<tr>
<td>
<!-- <a href="AltriOperatori.do?command=DashboardScelta">Operatori</a> > -->
<a href="Distributori.do?command=ScegliD"> Scelta Operatore </a> >

Gestione Distributori Automatici

</td>
</tr>
</table>
	<head>
		<link rel="stylesheet" type="text/css" href="css/jmesa.css"></link>
		
		<script type="text/javascript" src="javascript/jquery.bgiframe.pack.js"></script>
		<script type="text/javascript" src="javascript/jquery.jmesa.js"></script>
		<script type="text/javascript" src="javascript/jmesa.js"></script>
	</head>

<center>
		<p>
			Utilizzare le caselle vuote sopra l'intestazione per filtrare per anno, richiedente, ragione sociale, capo e/o rapporto
		</p>
		<form name="aiequidiForm" action="DistributoriList.do">
	       <%=request.getAttribute( "tabella" )%>
	    </form>

</center>
	<script type="text/javascript">
            function onInvokeAction(id) {
                $.jmesa.setExportToLimit(id, '');
                $.jmesa.createHiddenInputFieldsForLimitAndSubmit(id);
            }
            function onInvokeExportAction(id) {
                var parameterString = $.jmesa.createParameterStringForLimit(id);
                location.href = 'DistributoriList.do?&' + parameterString;
            }
    </script>
