<#assign check = '${lista.html_type}'>
			<#if check == 'nome_sezione'>
				<tr  id="tr_${lista.html_name}">
					<th colspan="2">${lista.html_label_sezione}
					<#if lista.html_label??>
						<#if lista.html_label == 'input_text'>
							<input type="text" id="${lista.html_name}" name="${lista.mapping_field}" <#if lista.sql_campo??>${lista.sql_campo}<#else></#if>
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
			<#elseif check == 'text'>
			<tr  id="tr_${lista.html_name}" title="" <#if lista.html_style??>${lista.html_style}<#else></#if>>
				<td class="formLabel">${lista.html_label}</td>
				<td>
					<input type="text" id="${lista.html_name}" name="${lista.mapping_field}"  
						<#if lista.html_style??>${lista.html_style}<#else></#if>
						<#if lista.html_event??>${lista.html_event}<#else></#if>>
			</tr>
			<#elseif check == 'date'>
 			<tr>
        		<td class="formLabel">${lista.html_label}</td>
        		<td>
                	<input placeholder="Inserisci data" type="text" id="${lista.html_name}" name="${lista.mapping_field}" 
                	<#if lista.html_style??>${lista.html_style}<#else></#if> autocomplete="off" onkeydown="return false">                
                	<script>
                	$( '#${lista.html_name}' ).datepicker({
						  dateFormat: 'dd-mm-yy',
						  changeMonth: true,
						  changeYear: true,
						  yearRange: '-100:+3',					
						  <#if lista.html_event??>${lista.html_event}<#else></#if>
						  dayNamesMin : [ 'do', 'lu', 'ma', 'me', 'gi', 've', 'sa' ],
						  monthNamesShort :['Gennaio','Febbraio','Marzo','Aprile','Maggio','Giugno', 'Luglio','Agosto','Settembre','Ottobre','Novembre','Dicembre'],
						  beforeShow: function(input, inst) {
                                 setTimeout(function () {
                                            var offsets = $('#${lista.html_name}').offset();
                                            var top = offsets.top - 100;
                                            inst.dpDiv.css({ top: top, left: offsets.left});
                                            $(".ui-datepicker-next").hide();
											$(".ui-datepicker-prev").hide();
											$(".ui-state-default").css({'font-size': 15});
											$(".ui-datepicker-title").css({'text-align': 'center'});
											$(".ui-datepicker-calendar").css({'text-align': 'center'});
                                  });
                                },
                          onChangeMonthYear: function(year, month, inst) {
                                 setTimeout(function () {
                                            var offsets = $('#${lista.html_name}').offset();
                                            var top = offsets.top - 100;
                                            inst.dpDiv.css({ top: top, left: offsets.left});
                                            $(".ui-datepicker-next").hide();
											$(".ui-datepicker-prev").hide();
											$(".ui-state-default").css({'font-size': 15});
											$(".ui-datepicker-title").css({'text-align': 'center'});
											$(".ui-datepicker-calendar").css({'text-align': 'center'});
                                  });
                                }                                                  
						});
                	</script>
        		</td>
        	</tr>
        	
        	<#elseif check == 'gruppo_calcolato'>
 				<#assign elementigruppo = lista.listaLookup>
				<tr id="${lista.html_name}">
				<td class="formLabel">${lista.html_label}</td>	
				<td>
        					<#list elementigruppo as e> 
        					<#if '${e.html_type}' == 'text'>		
								<input type="text" id="${e.html_name}" name="${e.mapping_field}" placeholder="${e.html_label}"  value ="" 
								<#if e.html_style??>${e.html_style}<#else></#if>
								<#if e.html_event??>${e.html_event}<#else></#if>
								>
							<#elseif '${e.html_type}' == 'button'>
					 			<input type="button" id="${e.html_name}" value="${e.html_label}" ${e.html_event} <#if e.html_style??>${e.html_style}<#else></#if> />     
							</#if>
							</#list>
        		</td>
				</tr>
        	
			<#-- 		
			<#elseif check == 'select'>
 				<#assign lookup = lista.listaLookup>
 				<tr id="tr_${lista.html_name}" <#if lista.html_style??>${lista.html_style}<#else></#if>>
    				<td class="formLabel">${lista.html_label}</td>
        			<td>
            			<select name="${lista.mapping_field}" id="${lista.html_name}" <#if lista.html_event??>${lista.html_event}<#else></#if> <#if lista.html_style??>${lista.html_style}<#else></#if>>
                			<option></option>
                			<#list lookup as t>
                    			<option value="${t.code}" <#if ('${t.code}' == '106')&& '${t.description}'=='Italia'> selected="selected"</#if>>${t.description}</option>                 
                			</#list>
            			</select>
        			</td>
 				</tr>
			 -->
			
			<#elseif check == 'select' && lista.html_name != 'comune_nascita_rapp_legale'>
 				<#assign lookup = lista.listaLookup>
 				<tr id="tr_${lista.html_name}" <#if lista.html_style??>${lista.html_style}<#else></#if>>
    				<td class="formLabel">${lista.html_label}</td>
        			<td>
            			<select name="${lista.mapping_field}" id="${lista.html_name}" <#if lista.html_event??>${lista.html_event}<#else></#if> <#if lista.html_style??>${lista.html_style}<#else></#if>>
                			<option></option>
                			<#list lookup as t>
                    			<option value="<#if t.code??>${t.code}<#else></#if>" 
                    				<#if ('${t.code}' == '106') && '${t.description}'=='Italia'> selected="selected"</#if>>${t.description}</option>                 
                			</#list>
            			</select>
        			</td>
 				</tr>
 			 
 			<#elseif check == 'select' && lista.html_name == 'comune_nascita_rapp_legale'>
				<tr id="tr_${lista.html_name}" <#if lista.html_style??>${lista.html_style}<#else></#if>>
    				<td class="formLabel">${lista.html_label}</td>
        			<td>
        				 <form action="">
 							<div class="awesomplete">
								<input type="text" id="${lista.html_name}Label" name="${lista.mapping_field}Label" placeholder="Digitare i primi caratteri il Comune" size="50" 
								<#if lista.html_event??>${lista.html_event}<#else></#if> />
								<input type="hidden" id="${lista.html_name}" name="${lista.mapping_field}">
							</div>
						</form>
						<script>
							"use strict";
							var hidden = document.getElementById("${lista.html_name}");
							var input = document.getElementById("${lista.html_name}Label");
							new Awesomplete(input, {
    												list: cArray,
    												minChars: 2,
													maxItems: 20,
													autoFirst: true,
   													replace: function(item) {
       												input.value= item.label; 
        											hidden.value = item.value;
    												},
											filter: Awesomplete.FILTER_STARTSWITH
							});
						</script>
					</td>
 				</tr>
 				
 			<#elseif check == 'dati_linea_attivita'>
 				<#assign lookup = lista.listaLookup>
 				<tr id="tr_${lista.html_name}" <#if lista.html_style??>${lista.html_style}<#else></#if>>
    				<td class="formLabel">${lista.html_label}</td>
                		<#list lookup as t>
            				<td id="${lista.html_name}" name="${lista.mapping_field}" value="${t.code}">${t.description}</td>
            			</#list>        			
 				</tr>
 			<#elseif check == 'hidden'>
 				<#if '${lista.html_name}' == 'lineaattivita_1_codice_univoco_ml'>
 	 				<input type="hidden" id="${lista.html_name}" name="${lista.mapping_field}" value="${lista.codice_univoco_ml}"/>
 	 			<#elseif '${lista.html_name}' == 'numero_linee_effettivo'>
 	 				<input type="hidden" id="${lista.html_name}" name="${lista.mapping_field}" ${lista.html_style} />
 	 			<#elseif '${lista.html_name}' == 'lineaattivita_1_tipo_carattere_attivita'>
 	 				<input type="hidden" id="${lista.html_name}" name="${lista.mapping_field}" <#if lista.html_style??>${lista.html_style}<#else></#if> />
 	 			<#else>
 	 				<input type="hidden" id="${lista.html_name}" name="${lista.mapping_field}" />
 	 			</#if>
 	 		<#elseif check == 'textarea'>
 	 			<td class="formLabel"> ${lista.html_label}</td>
            	<td>
                    <${lista.html_type} id="${lista.html_name}" name="${lista.mapping_field}" ></${lista.html_type}>
            	</td>
			</#if>