<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<html>
<head>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="scroll.js"></script>

<script>
function scroll() {
	$('#accTable').tScroll({
			h_box: '500px'
	});

}
</script>

<style type="text/css">
table{
	table-layout:fixed;
}
.css_tab{
    font-family: verdana;
    font-size: 10px;
    background-color: blue;
    color:white;
    border-left:1px solid #CCCCCC;
    border-top:1px solid #CCCCCC; 
}
.css_tab th{
    padding:2px; 
    border-bottom:1px solid #CCCCCC;
    border-right: 1px solid #CCCCCC;
}
.css_tab td{
    text-align: right;
    background-color: aqua;
    color:black;
    padding:4px; 
    border-bottom:1px solid #CCCCCC;
    border-right: 1px solid #CCCCCC;
}
.css_tab tfoot td{
    background-color: blue;
    color:white;
}
pre{
    background-color:#CCCCCC;
    width:400px;
}
</style>
</head>

<body onload='scroll()'>
<table border="0" cellpadding="0" cellspacing="0" id='accTable' class="css_tab">
<thead>
<th >username</th><th >last_ip</th><th >last_login</th><th >last_interaction_time</th><th >actioyyyyyyyyyyyyyyn</th><th >command</th><th >table_name</th>
<th >username</th><th >last_ip</th><th >last_login</th><th >last_interaction_time</th><th >action</th><th >command</th><th >table_name</th>

</thead>
<tbody onload="scroll()">
<tr ><td >adellamanaco</td><td >151.70.60.176</td><td >2015-02-25 23:33:58.96</td><td >2015-02-25 23:39:59.958</td><td >AccountCampioni</td><td >TicketDetails</td><td >Organization</td>
<td >adellamanaco</td><td >151.70.60.176</td><td >2015-02-25 23:33:58.96</td><td >2015-02-25 23:39:59.958</td><td >AccountCampioni</td><td >TicketDetails</td><td >Organization</td></tr>

</tbody>
</table>
</body>

</html> 