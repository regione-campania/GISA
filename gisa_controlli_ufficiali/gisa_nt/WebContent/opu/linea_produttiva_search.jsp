<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
		<link rel="stylesheet" type="text/css" href="css/jmesa.css"></link>
		
		<script type="text/javascript" src="javascript/jquery.bgiframe.pack.js"></script>
		<script type="text/javascript" src="javascript/jquery.jmesa.js"></script>
		<script type="text/javascript" src="javascript/jmesa.js"></script>
		
		<script>
		function setidLineaProduttivaRequest(fieldCheckbox)
		{
			fieldText= document.getElementById('idLineaProduttivaRequest');
			
			if (fieldCheckbox.checked==true)
			{
				fieldText.value = addText(fieldText.value,fieldCheckbox.value);
			}
			else
			{
				fieldText.value = removeText(fieldText.value,fieldCheckbox.value);
			}
			
		}
		
		function containsString(text1,text2)
		{
			  return (text1.indexOf(text2)) ;
				
		}
		function removeText(text1,text2)
		{
			index = containsString(text1,text2) ;
			if (index!=-1) {
				index2 = parseInt(index)+text2.length+1 ;
				text1=text1.substring(0,index) + text1.substring(index2,text1.length);
			}
			return text1 ;
			
		}
		function addText(text1,text2)
		{
			
			if (containsString(text1,text2)==-1) {
				
				text1+=text2+";";
			}
			return text1 ;
			
			
		}
		
		function checkLineeProduttive(idlinea,field)
		{
			index=1;
		while (opener.document.getElementById('idLineaProduttiva' + index) != null) {
			
			if (idlinea==opener.document.getElementById('idLineaProduttiva' + index).value)
				{
				alert('Linea Attivita gia Presente');
				field.checked=false;
				break;
				}
		
			index++;
		}
		}
		
		</script>




<!--<dhv:pagedListStatus  object="SearchLineaProduttiva"/>-->
<input type = "hidden" name = "lineaProduttivaSelezionata" value = "">
<form action = "LineaProduttivaAction.do?command=Search&popup=true&tipoSelezione=multipla" method="post">


   <%=request.getAttribute( "tabella" )%>
	<script type="text/javascript">
            function onInvokeAction(id) {
                $.jmesa.setExportToLimit(id, '');
                $.jmesa.createHiddenInputFieldsForLimitAndSubmit(id);
            }
            function onInvokeExportAction(id) {
                var parameterString = $.jmesa.createParameterStringForLimit(id);
                location.href = 'LineaProduttivaAction.do?command=Search&popup=true&tipoSelezione=multipla&' + parameterString;
            }
    </script>
<input type = "button" value = "Fatto" onclick="document.forms[0].action='LineaProduttivaAction.do?command=ScegliLineaProduttiva';document.forms[0].submit()">
<input type = "hidden" name = "idLineaProduttivaRequest" id = "idLineaProduttivaRequest" value = "<%=(request.getAttribute("idLineaProduttivaRequest")!= null) ? request.getAttribute("idLineaProduttivaRequest")+"" : "" %>" >
</form>
