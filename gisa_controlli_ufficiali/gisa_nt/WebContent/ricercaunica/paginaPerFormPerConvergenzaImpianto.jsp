<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "org.json.JSONArray, org.json.JSONObject" %>
<%@ page import="java.util.ArrayList, java.util.Iterator" %>
<%@page import="org.aspcfs.modules.opu.base.*, java.util.HashMap"%>
<jsp:useBean id="lookupListTipoAttivita" class="org.aspcfs.utils.web.LookupList"  scope ="request" />
<jsp:useBean id="lookupListTipoCarattere" class="org.aspcfs.utils.web.LookupList"  scope ="request" />
<jsp:useBean id="lookupToponimi" class="org.aspcfs.utils.web.LookupList"  scope ="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<SCRIPT src="javascript/combostatiche.js"></SCRIPT>
<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/opu.css" />
<link rel="stylesheet" type="text/css" href="css/jquery.calendars.picker.css">
<link href="javascript/datepicker/jquery.datepick.css" rel="stylesheet">
<script src="javascript/suap.jquery.steps.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/gestoreCodiceFiscale.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.plus.js"></script>
<script type="text/javascript" src="javascript/jquery.plugin.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.picker.js"></script>
<script src="javascript/parsedate.js"></script>
<script src="javascript/jquery-ui.js"></script>
<SCRIPT src="javascript/combostatiche.js"></SCRIPT>
<SCRIPT src="javascript/upload.js"></SCRIPT>
<SCRIPT src="javascript/suapCittadinoUtil.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="gestione_documenti/generazioneDocumentale.js"></script>
<script src="javascript/jquery.form.js"></script>
<script type="text/javascript" src="dwr/interface/SuapDwr.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
 
<script src="javascript/datepicker/jquery.plugin.js"></script>
<script src="javascript/datepicker/jquery.datepick.js"></script>




	<style type="text/css">
        select:not([disabled]) { border-color: lightblue; background-color: #EEFFEE; }
 </style>

	
	
	<!-- per ogni proprieta del bean che rappresenta operatore duplicato, devo salvare tutti i possibili valori che esistono -->
	<% 
		Integer idOperatoreInComune = null;
		//LookupList lookupSelectTipoImpresa = request.getAttribute("lookupTipoImpresa");
	
		ArrayList<OperatorePerDuplicati> entries = (ArrayList<OperatorePerDuplicati>)request.getAttribute("entriesChecked");
		ArrayList<Integer> idsImpianti = new ArrayList<Integer>();
		HashMap<String,ArrayList<String[]>> allValues = new HashMap<String,ArrayList<String[]>>();
		//scarico tutte le proprieta dal bean della singola entry
		for(OperatorePerDuplicati t : entries)
		{
			if(idOperatoreInComune == null) //se non l'avevo già fatto, me lo salvo da parte
				idOperatoreInComune = t.getIdOperatore();
			
			idsImpianti.add(t.getIdStabilimento());
			HashMap<String,String[]> propValues = t.getValuesPerForm();
			
			for(String nomeProp : propValues.keySet())
			{
				 
				if(propValues.get(nomeProp)[0] == null)
					continue;
				if(!allValues.containsKey(nomeProp)) //prima entry sicuramente
				{
					allValues.put(nomeProp,new ArrayList<String[]>());
				}
				else
				{
					boolean haveToAdd = true;
					for(String[] alreadyAdded : allValues.get(nomeProp))
					{
						/*String already = alreadyAdded[0];
						String toComp = propValues.get(nomeProp)[0];
					*/
						
						if( alreadyAdded[0].equals(propValues.get(nomeProp)[0]) )
						{
							haveToAdd = false;
							break;
						}
					}
					if(!haveToAdd)
						continue;
					//if( allValues.get(nomeProp).contains( propValues.get(nomeProp)  ) )
						//continue;
				}
				
				allValues.get(nomeProp).add(propValues.get(nomeProp));
				//System.out.println(allValues.get(nomeProp).get(0)[0]);
			}
		}
		
		%> 
		<script>
		window.idImpianti = {};
		
		</script>
		<%
		
		for(Integer id : idsImpianti)
		{
			%>
			<script> console.log('<%=id%>'); window.idImpianti['<%=id%>'] = true  </script>
			<%
		}
	%> 


	 <form id="example-advanced-form" name="convergiImpianti"  action="#" enctype="multipart/form-data">
	        
	       
	        <h3>DATI STABILIMENTO</h3>
	        <fieldset>
	                <legend style="background-color:lightblue;">TIPOLOGIA IMPIANTO</legend>
	               <table style="width:100%;">
						<col width="16%"/>
		                <tr>
			                <td colspan="3" nowrap id="trTipoAttivita">TIPO ATTIVITA'</td>
			                <td>
							<%if(allValues.get("stabIdAttivita") != null && allValues.get("stabIdAttivita")!= null && allValues.get("stabIdAttivita").size() != 0  && !(allValues.get("stabIdAttivita").size()==1 && allValues.get("stabIdAttivita").get(0)[0].matches(" *") ) )
							{%>
								<!-- inoltre se è solo 1 non è attivata la scelta della select -->
				                <select  id="selTipoAttivita">
				                <%
				                 // out.print(allValues.get("stabIdAttivita")[1]);
				                 if(allValues.get("stabIdAttivita") != null)
				                 {
						                  for(String[] tipoAttivita : allValues.get("stabIdAttivita")) 
						                  {
						                  	if(tipoAttivita[0].matches(" *") && allValues.get("stabIdAttivita").size() > 1)
						                  		continue; 
						                  %><!-- in realtà stampo il tipo impresa ma il valore associato è l'd -->
						                	<option <%= !tipoAttivita[0].matches(" *") ? "selected=\"selected\"" : "" %> value=<%=tipoAttivita[1] %>><%=tipoAttivita[0] %></option> 
						                <%}
				                 }
				                   %>
				                </select>
				                
				                <script>
				                	($("#selTipoAttivita option").size()<=1)
				                	{
				                		$("#selTipoAttivita").attr("disabled","disabled");
				                	}
				                </script>
				                
				           <%} 
							else //uso la lookup
							{
								out.print(lookupListTipoAttivita.getHtmlSelect("selTipoAttivita",-1)); 
							}
				           %>
				          </td>
						 </tr>   
						 
						 
						 
						   
						<tr>
			                <td colspan="3" nowrap id="trTipoCarattere">CARATTERE ATTIVITA</td>
			                <td>
							<%if(allValues.get("stabIdCarattere") != null && allValues.get("stabIdCarattere") != null && allValues.get("stabIdCarattere").size() != 0  && !(allValues.get("stabIdCarattere").size()==1 && allValues.get("stabIdCarattere").get(0)[0].matches(" *") ) )
							{%>
								<!-- inoltre se è solo 1 non è attivata la scelta della select -->
				                <select id="selTipoCarattere">
				                <%
				              
				               	   //out.print(allValues.get("stabIdCarattere")[1]);
				                  for(String[] tipoCarattere : allValues.get("stabIdCarattere")) 
				                  {
				                  	if(tipoCarattere[0].matches(" *")  && allValues.get("stabIdCarattere").size() > 1)
				                  		continue; 
				                  %><!-- in realtà stampo il tipo impresa ma il valore associato è l'd -->
				                	<option <%= !tipoCarattere[0].matches(" *") ? "selected=\"selected\"" : "" %> value=<%=tipoCarattere[1] %>><%=tipoCarattere[0] %></option> 
				                <%} 
				                   %>
				                </select>
				                
				                <script>
				                	($("#selTipoCarattere option").size()<=1)
				                	{
				                		$("#selTipoCarattere").attr("disabled","disabled");
				                	}
				                </script>
				                
				           <%} 
							else //uso la lookup
							{
								out.print(lookupListTipoAttivita.getHtmlSelect("selTipoCarattere",-1)); 
								
							}
				           %>
				          </td>
						 </tr>   
						 
						 
						 <tr>
							<td colspan="3" nowrap>NAZIONE</td>
							<td>
							<select id="selNazioneSedeOperativa">
							  
							<% 
								if(allValues.get("nazioneSedeOperativa") != null)
								{
									for(String[] nazioneSedeOperativa : allValues.get("nazioneSedeOperativa")) 
					                  {
					                  	if(nazioneSedeOperativa[0].matches(" *")  && allValues.get("nazioneSedeOperativa").size() > 1) continue; %>
					                	<option <%=!nazioneSedeOperativa[0].matches(" *") ? "selected=\"selected\"" : ""%> value=<%=nazioneSedeOperativa[0].replaceAll(" ","&nbsp;") %>><%=nazioneSedeOperativa[0] %></option>
					                <% 
				                	  }
								} 
								else
								{
									%>
									<option value=" ">&nbsp;</option>
									<%
								}
				                   %>
				             </select>
				              		<script>
					                	/* if($("#selProvincia option").size()<=1)
					                	{
					                		$("#selProvincia").attr("disabled","disabled");
					                	} */
					                	$("#selNazioneSedeOperativa").combobox();
					                </script>
				               <br>
							</td>
						</tr>
						 <tr>
							<td colspan="3" nowrap>PROVINCIA</td>
							<td>
							<select id="selProvincia">
							  
							<% 
								if(allValues.get("siglaProvOperativa") != null)
								{
									for(String[] provincia : allValues.get("siglaProvOperativa")) 
					                  {
					                  	if(provincia[0].matches(" *")  && allValues.get("siglaProvOperativa").size() > 1) continue; %>
					                	<option <%=!provincia[0].matches(" *") ? "selected=\"selected\"" : ""%> value=<%=provincia[0].replaceAll(" ","&nbsp;") %>><%=provincia[0] %></option>
					                <% 
				                	  }
								} 
								else
								{
									%>
									<option value=" ">&nbsp;</option>
									<%
								}
				                   %>
				             </select>
				              		<script>
					                	/* if($("#selProvincia option").size()<=1)
					                	{
					                		$("#selProvincia").attr("disabled","disabled");
					                	} */
					                	$("#selProvincia").combobox();
					                </script>
				               <br>
							</td>
						</tr>
						
						
						
						
						<tr>
							<td colspan="3" nowrap>COMUNE</td>
							<td>
							<select id="selComune">
							
							<%if(allValues.get("comuneStab") != null)
							  {
								for(String[] com : allValues.get("comuneStab")) 
				                  {
				                  	if(com[0].matches(" *") && allValues.get("comuneStab").size() > 1) continue; %>
				                	<option <%=!com[0].matches(" *") ? "selected=\"selected\"" : ""%> value=<%=com[0].replaceAll(" ","&nbsp;") %>><%=com[0] %></option>
				                <% }
							  }
								else
								{
									%>
									<option value=" ">&nbsp;</option>
									<%
								}
				                   %>
				             </select>
				              		<script>
					                	/* if($("#selComune option").size()<=1)
					                	{
					                		$("#selComune").attr("disabled","disabled");
					                	} */
					                	$("#selComune").combobox();
					                </script>
				               <br>
							</td>
						</tr>
						<tr>
							<td colspan="3" nowrap>CAP</td>
							<td>
							<select id="selCapSedeOperativa">
							
							<%if(allValues.get("capSedeOperativa") != null)
							  {
								for(String[] capSedeOperativa : allValues.get("capSedeOperativa")) 
				                  {
				                  	if(capSedeOperativa[0].matches(" *") && allValues.get("capSedeOperativa").size() > 1) continue; %>
				                	<option <%=!capSedeOperativa[0].matches(" *") ? "selected=\"selected\"" : ""%> value=<%=capSedeOperativa[0].replaceAll(" ","&nbsp;") %>><%=capSedeOperativa[0] %></option>
				                <% }
							  }
								else
								{
									%>
									<option value=" ">&nbsp;</option>
									<%
								}
				                   %>
				             </select>
				              		<script>
					                	/* if($("#selComune option").size()<=1)
					                	{
					                		$("#selComune").attr("disabled","disabled");
					                	} */
					                	$("#selCapSedeOperativa").combobox();
					                </script>
				               <br>
							</td>
						</tr>
						
		 
				<tr>
				<td colspan="3" nowrap>INDIRIZZO STABILIMENTO</td>
				<td>
					<table class="noborder">
						<tr>
							<td>
							<%if(allValues.get("toponimoSedeStab").size() != 0 && !(allValues.get("toponimoSedeStab").size()==1 && allValues.get("toponimoSedeStab").get(0)[0].matches(" *") ) )
							{%>
								<select  id="selToponimoStab">
								
									<%for(String[] toponimo : allValues.get("toponimoSedeStab")) 
						                  {if(toponimo[0].matches(" *")  && allValues.get("toponimoSedeStab").size() > 1)
						                  	continue;
						                  %>
						                	<option <%=!toponimo[0].matches(" *") ? "selected=\"selected\"" : ""%> value=<%=toponimo[1] %>><%=toponimo[0] %></option>
						                <%} 
						                   %>
						             
					             </select>
					             <script>
				                	/* if($("#selToponimoStab option").size()<=1)
				                	{
				                		$("#selToponimoStab").attr("disabled","disabled");
				                	} */
				                	$("#selToponimoStab").combobox();
				                </script>
					         <%}
							else{
								out.print(lookupToponimi.getHtmlSelect("selToponimoStab",-1));
							}%>
							
							
				             <select id="selIndirizzoStab">
				             
								<%
								if(allValues.get("indirizzoStab")!= null)
								{	for(String[] indirizzoStab : allValues.get("indirizzoStab")) 
					                  { if(indirizzoStab[0].matches(" *")  && allValues.get("indirizzoStab").size() > 1)
					                  	continue;
					                  %>
					                	<option <%=!indirizzoStab[0].matches(" *") ? "selected=\"selected\"" : ""%> value=<%=indirizzoStab[0].replaceAll(" ","&nbsp;") %>><%=indirizzoStab[0] %></option>
					                <%} 
								}
								else
								{
									%>
									<option value=" ">&nbsp;</option>
									<%
								}
					                   %>
				             </select>
				              <script>
				                	/* if($("#selIndirizzoStab option").size()<=1)
				                	{
				                		$("#selIndirizzoStab").attr("disabled","disabled");
				                	} */
				                	$("#selIndirizzoStab").combobox();
				              </script>
				              
				              
				             <select  id="selCivicoStab">
				            
								<%if(allValues.get("civicoSedeStab") != null)
									{
										for(String[] civicoSedeStab : allValues.get("civicoSedeStab")) 
						                  {
						                  	if(civicoSedeStab[0].matches(" *")  && allValues.get("civicoSedeStab").size() > 1)
						                  		continue;
						                  %>
						                	<option <%=!civicoSedeStab[0].matches(" *") ? "selected=\"selected\"" : ""%> value=<%=civicoSedeStab[0].replaceAll(" ","&nbsp;") %>><%=civicoSedeStab[0] %></option>
						                <%}
									}
									else
									{
										%>
										<option value=" ">&nbsp;</option>
										<%
									}
					                   %>
				             </select>
				             	<script>
				                	/* if($("#selCivicoStab option").size()<=1)
				                	{
				                		$("#selCivicoStab").attr("disabled","disabled");
				                	} */
				                	$("#selCivicoStab").combobox();
				              </script>
				               <br>
							</td>
						</tr>
					</table>
						
						<tr>
							<td colspan="3" nowrap><label for="dataInizioAttivita">DATA INIZIO ATTIVITA</label></td>
							<td>
								<select id="selDataInizioAttivita">
								
									<%
										if(allValues.get("dataInizioAttivita")!=null)
										{
											for(String[] dataInizioAttivita : allValues.get("dataInizioAttivita")) 
							                  {
							                  	if(dataInizioAttivita[0].matches(" *")  && allValues.get("dataInizioAttivita").size() > 1)
							                  		continue;
							                    %>
							                	<option <%=!dataInizioAttivita[0].matches(" *") ? "selected=\"selected\"" : ""%> value=<%=dataInizioAttivita[0].replaceAll(" ","&nbsp;") %>><%=dataInizioAttivita[0] %></option>
							                <%}
										}
										else
										{
											%>
											<option value=" ">&nbsp;</option>
											<%
										}
						                   %>
					             </select>
					              <script>
					                	/* if($("#selDataInizioAttivita option").size()<=1)
					                	{
					                		$("#selDataInizioAttivita").attr("disabled","disabled");
					                	} */
					                	$("#selDataInizioAttivita").combobox();
					               </script>
								  <br>
							</td>
						</tr>
						
						
						
						<tr>
							<td colspan="3" nowrap><label for="dataFineAttivita">DATA FINE ATTIVITA</label></td>
							<td>
								<select id="selDataFineAttivita">
								
									<%
										if(allValues.get("dataFineAttivita") != null)
										{
											for(String[] dataFineAttivita : allValues.get("dataFineAttivita")) 
							                  {
							                  	if(dataFineAttivita[0].matches(" *")  && allValues.get("dataFineAttivita").size() > 1)
							                  		continue;
							                    %>
							                	<option <%=!dataFineAttivita[0].matches(" *") ? "selected=\"selected\"" : ""%> value=<%=dataFineAttivita[0].replaceAll(" ","&nbsp;") %>><%=dataFineAttivita[0] %></option>
							                <%}
										}
										else
										{
											%>
											<option value=" ">&nbsp;</option>
											<%
										}
							                   %>
					             </select>
					              <script>
					                	/* if($("#selDataFineAttivita option").size()<=1)
					                	{
					                		$("#selDataFineAttivita").attr("disabled","disabled");
					                	} */
					                	$("#selDataFineAttivita").combobox();
					               </script>
								  <br>
							</td>
						</tr>
						
					    <tr><td colspan="11"><div align="right"><input type="button" value="INDIETRO" onclick="intercettaBtnIndietro('<%=(String)request.getAttribute("pIvaRichiesta")%>'	)" /><input type="button" value="SALVA" onclick="intercettaSalvaBtn('<%=(String)request.getAttribute("pIvaRichiesta")%>','<%=User.getUserRecord().getId()+""%>','<%=idOperatoreInComune%>')"/> </div> </td> </tr>		
					</table>
			<!-- 	</td>
			</tr> -->
						
						
						
						
					
				 
				       
	        </fieldset>
	        
	        
	        
	</form>
	
	
	
	<script>
	
	
		
	function intercettaBtnIndietro(pIvaRichiesta)
	{
		window.location.href = "InterfAnalisiDuplicatiOpu.do?command=RicercaTuttiDuplicatiPerPIva&pIvaCercata="+pIvaRichiesta;
	}

	function intercettaSalvaBtn(pIvaRichiesta,idUtente,idOperatore)
	{
		 /* selTipoAttivita,selTipoCarattere,selProvincia,selComune,selToponimo,selIndirizzoStab,selCivicoStab */
		
		
		
		var idTipoAttivita = $("#selTipoAttivita").val();
		var idTipoCarattere = $("#selTipoCarattere").val();
		var siglaProvincia = $("#selProvincia").val();
		var comune = $("#selComune").val();
		var idToponimo = $("#selToponimoStab").val();
		var indirizzo =$("#selIndirizzoStab").val();
		var civico = $("#selCivicoStab").val();
		var dataInizioAttivita = $("#selDataInizioAttivita").val();
		var dataFineAttivita = $("#selDataFineAttivita").val();
		var capSedeOperativa = $("selCapSedeOperativa").val();
		var nazioneSedeOperativa = $("selNazioneSedeOperativa").val();
		var jsonRepr = {};
		jsonRepr.idTipoAttivita = idTipoAttivita;
		jsonRepr.idTipoCarattere = idTipoCarattere;
		jsonRepr.siglaProvincia = siglaProvincia;
		jsonRepr.comune = comune;
		jsonRepr.idToponimo = idToponimo;
		jsonRepr.indirizzo = indirizzo;
		jsonRepr.civico = civico;
		jsonRepr.idUtente = idUtente;
		jsonRepr.idImpianti = window.idImpianti;
		console.log(idImpianti);
		jsonRepr.dataFineAttivita = dataFineAttivita;
		jsonRepr.dataInizioAttivita = dataInizioAttivita;
		jsonRepr.idOperatore = idOperatore;
		jsonRepr.capSedeOperativa = capSedeOperativa;
		jsonRepr.nazioneSedeOperativa = nazioneSedeOperativa;
		
		
		window.location.href = "InterfAnalisiDuplicatiOpu.do?command=ConvergiDuplicati&perImpianti=true&pIvaCercata="+pIvaRichiesta+"&datiDaForm="+JSON.stringify(jsonRepr).replace(/\s/g,'$');
		//faccio replace dei caratteri vuoti per problemi di encoding
		//alert(JSON.stringify(jsonRepr).replace(/\s/g,'+'));
	}

</script>
	
	


</body>
</html>