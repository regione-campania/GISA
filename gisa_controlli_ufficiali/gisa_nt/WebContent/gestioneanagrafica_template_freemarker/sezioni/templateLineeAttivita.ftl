<#assign check = '${lista.html_type}'>
			<#if check == 'nome_sezione'>
				<tr  id="tr_${lista.html_name}">
					<th colspan="2">${lista.html_label_sezione} &emsp;
					<#if lista.html_label??>
						<#if lista.html_label == 'input_text'>
							<input type="text" id="${lista.html_name}" name="${lista.mapping_field}" 
							<#if lista.html_style??>${lista.html_style}<#else></#if>
							<#if lista.html_event??>${lista.html_event}<#else></#if>>
						<#elseif lista.html_label == 'input_button'>
							<input type="button" id="${lista.html_name}" value="<#if lista.sql_campo??>${lista.sql_campo}<#else></#if>"  
							<#if lista.html_style??>${lista.html_style}<#else></#if>
							<#if lista.html_event??>${lista.html_event}<#else></#if> />
						</#if>
					<#else></#if>
					</th>				
				</tr>
				
			<#elseif check == 'hidden'>
 				<input type="hidden" id="${lista.html_name}" name="${lista.mapping_field}" <#if lista.html_style??>${lista.html_style}<#else></#if> />		
			</#if>
