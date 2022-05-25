<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<!-- <script type="text/javascript" src="suap/javascriptsuap/suap_imprese.js"></script> -->

<jsp:useBean id="TipoSocietaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="NazioniList"     class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ToponimiList"    class="org.aspcfs.utils.web.LookupList" scope="request" />
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
<!-- <SCRIPT src="javascript/upload.js"></SCRIPT>
<SCRIPT src="javascript/suapCittadinoUtil.js"></SCRIPT> -->
<script language="JavaScript" TYPE="text/javascript" SRC="gestione_documenti/generazioneDocumentale.js"></script>
 
 

<%@ page import = "org.json.JSONArray, org.json.JSONObject" %>
<%@ page import="java.util.ArrayList, java.util.Iterator" %>
<%@page import="org.aspcfs.modules.opu.base.*, java.util.HashMap"%>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="lookupToponimi" class="org.aspcfs.utils.web.LookupList"  scope="request" />
<jsp:useBean id="lookupTipoSocieta" class="org.aspcfs.utils.web.LookupList" scope="request" />



	

<%-- <%@ include file="../initPage.jsp" %> --%>

 <!-- per ogni proprieta del bean che rappresenta operatore duplicato, devo salvare tutti i possibili valori che esistono -->
	<% 
		ArrayList<Integer> idImpreseSelezionate =new ArrayList<Integer>(); //anche duplicati
		
		ArrayList<OperatorePerDuplicati> entries = (ArrayList<OperatorePerDuplicati>)request.getAttribute("entriesChecked");
		HashMap<String,ArrayList<String[]>> allValues = new HashMap<String,ArrayList<String[]>>();
		//scarico tutte le proprieta dal bean della singola entry
		for(OperatorePerDuplicati t : entries)
		{
			idImpreseSelezionate.add(t.getIdOperatore());
			HashMap<String,String[]> propValues = t.getValuesPerForm();
			
			for(String nomeProp : propValues.keySet())
			{
				if(nomeProp.equals("campiConcatenati")) //il record dei campi concatenati lo prendiamo anche con duplicati
				{
					if(!allValues.containsKey("campiConcatenati")) //prima entry sicuramente
					{
						allValues.put("campiConcatenati",new ArrayList<String[]>());
						
					}
					allValues.get("campiConcatenati").add(propValues.get("campiConcatenati"));
					continue;
				}
				if(propValues.get(nomeProp)[0] == null)
					continue;
				/*if(propValues.get(nomeProp)[0].matches(" *")) //se l'entry è un valore fatto solo di spazi vuoti
					continue;*/
				if(!allValues.containsKey(nomeProp)) //prima entry sicuramente
				{
					allValues.put(nomeProp,new ArrayList<String[]>());
				}
				else
				{
					boolean haveToAdd = true;
					for(String[] alreadyAdded : allValues.get(nomeProp))
					{
						
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
			}
		}
		
		//salvo gli id operatori in un oggetto javascript
		%>
			<script>
				window.idImprese = {};
				<%for(Integer idImpresa : idImpreseSelezionate) 
				{%>
					idImprese['<%=idImpresa%>'] = true; 
				
				<%} %>
			</script>
		<%
	%> 



	<fieldset>
		<legend style="background-color:lightblue;">DATI IMPRESA</legend>
		<table style="width:100%;">
			<col width="18%"/>
			<tr id="tipo_societaTR">
				<td colspan="3" nowrap id="trTipoSocieta">TIPO SOCIETA'</td>
				<td>
					<!-- se vengono mostrate tutte le possibili tipologie impresa, vengoon mostrate anche tutte le possibili tipologie di societa (col filtraggio fatto in js) -->
					<%if(allValues.get("tipoImpresa").size() != 0  && !(allValues.get("tipoImpresa").size()==1 && allValues.get("tipoImpresa").get(0)[0].matches(" *") ) )
					{%>
					
						<select id="selTipoSocieta">
		                <%for(String[] tipoSocieta : allValues.get("tipoSocieta")) 
		                  {
		                	/*if(tipoSocieta[0].matches(" *"))
		                	{	//nel caso in cui il valore per la società è nullo, 
		                		
		                  		continue;
		                	}*/		                  	
		                  %><!-- in realtà stampo il tipo impresa ma il valore associato è l'd --> 
		                	<option value=<%=tipoSocieta[1] %>><%= tipoSocieta[0].matches(" *") ? "NESSUNA" : tipoSocieta[0]%></option>
		                <%} 
		                   %>
		                </select>
		                
		               
					
		                 <script>
		                	if($("#selTipoSocieta option").size()<=1)
		                	{
		                		$("#selTipoSocieta").attr("disabled","disabled");
		                	}
		                </script>
		                
	                <%} 
	                else
	                {
	                	out.print(lookupTipoSocieta.getHtmlSelect("selTipoSocieta",-1).replace("IMPRESA INDIVIDUALE","NESSUNA"));
	                }%>
	               <!--  <script>
		              //disattivo prima tuttie l e possibili scelte tipi societa
	  					$("#selTipoSocieta option").attr("disabled",true);
	                </script> -->
	                <br>
				</td>
			</tr>
			<tr>
				<td colspan="3" nowrap>
						DITTA/<br>DENOMINAZIONE/<br>RAGIONE SOCIALE
				</td>
				<td>
					 
					<select id="selRagioneSociale" name="test">
					
	                <%
	                 if(allValues.get("ragioneSociale") != null)
	                 {
		                 for(String[] ragioneSociale : allValues.get("ragioneSociale")) 
		                  {
		                	
		                    if(ragioneSociale[0].matches(" *") && allValues.get("ragioneSociale").size()>1){  continue; };
		                   %>
		                	 <option <%=!ragioneSociale[0].matches(" *") ? "selected=\"selected\"" : ""%> value=<%=ragioneSociale[0].replaceAll(" ","&nbsp;") %>><%=ragioneSociale[0] %></option>
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
	                	 
	                	 /* 
 		                	if($("#selRagioneSociale option").size()<=1)
		                	{
 		                		$("#selRagioneSociale").attr("disabled","disabled");
 		                	} */ 
 		                	$( "#selRagioneSociale" ).combobox();
 		                	/* $(function(){
						        $( "#selRagioneSociale" ).combobox();
						   
 								});  */
		               </script> 
		               
	                  <br>
				</td>
			</tr>
			<tr>
				<td colspan="3" nowrap>PARTITA IVA</td>
				<td>
				<select id="selPartitaIva">
				
				<% 
				  if(allValues.get("partitaIva")!=null)
				  {
					  for(String[] partitaIva : allValues.get("partitaIva")) 
	                  {
	                  	if(partitaIva[0].matches(" *") && allValues.get("partitaIva").size()>1 ) continue; %>
	                	<option <%=!partitaIva[0].matches(" *") ? "selected=\"selected\"" : ""%> value=<%=partitaIva[0].replaceAll(" ","&nbsp;") %>><%=partitaIva[0] %></option>
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
		                	/* if($("#selPartitaIva option").size()<=1)
		                	{
		                		$("#selPartitaIva").attr("disabled","disabled");
		                	} */
		                	$( "#selPartitaIva" ).combobox();
		                </script>
	               <br>
				</td>
			</tr>
			<tr id="codFiscaleTR">
				<td colspan="3" nowrap>CODICE FISCALE<br>IMPRESA
				</td>
				<td>
				<select id=selCodFiscale>
				
				<%if(allValues.get("codFiscale")!=null)
					{
					for(String[] codFiscale : allValues.get("codFiscale")) 
	                  {
	                    if(codFiscale[0].matches(" *") && allValues.get("codFiscale").size()>1 ) continue;
	                    %>
	                	<option <%=!codFiscale[0].matches(" *") ? "selected=\"selected\"" : ""%> value=<%=codFiscale[0].replaceAll(" ","&nbsp;") %>><%=codFiscale[0] %></option>
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
		                	/* if($("#selCodFiscale option").size()<=1)
		                	{
		                		$("#selCodFiscale").attr("disabled","disabled");
		                	} */
		                	$( "#selCodFiscale" ).combobox();
		                </script>
	             	
				  <br>
				</td>
			</tr>
			
			<!-- il controllo per la mail è bypassato, e non è lo stesso degli altri campi -->
			
			
			<tr id="domicilioDigitale">
				<td  colspan="3" nowrap>DOMICILIO <br>DIGITALE(PEC)</td>
				<td>
					<select  id=selDomicilioDigitale>
					
					<%if(allValues.get("domicilioDigitale")!=null)
					  {for(String[] domicilioDigitale : allValues.get("domicilioDigitale")) 
		                  {
		                    if(domicilioDigitale[0].matches(" *") && allValues.get("domicilioDigitale").size()>1) continue;
		                    %>
		                	<option <%=!domicilioDigitale[0].matches(" *") ? "selected=\"selected\"" : ""%> value=<%=domicilioDigitale[0].replaceAll(" ","&nbsp;") %>><%=domicilioDigitale[0] %></option>
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
		                	/* if($("#selDomicilioDigitale option").size()<=1)
		                	{
		                		$("#selDomicilioDigitale").attr("disabled","disabled");
		                	} */
		                	$( "#selDomicilioDigitale" ).combobox();
		                </script>
		               <br>
				</td>
			</tr>
		</table>
		
	</fieldset>
	<fieldset>
		<legend style="background-color:lightblue;">DATI TITOLARE/LEGALE RAPPRESENTANTE</legend>
		<table style="width:100%;">
			<col width="18%"/>
			
			<tr>
				<td colspan="3" nowrap>NOME|COGNOME|SESSO|CF</td>
				<td>
					<select id="selConcatenazionePerRappLegale">
					
					<%if(allValues.get("campiConcatenati")!=null)
						{
						 for(String[] campiConcatenati : allValues.get("campiConcatenati")) 
		                  {
							if(campiConcatenati == null)
								break;
							boolean atLeastOneField = false;
		                  	String concatenazione = "";
		                  	for(int i=0;i<campiConcatenati.length;i++)
		                  	{
		                  		//separatore è | e per valori nulli mettiamo - (mentre se appare spazio, vuiol dire che l'utente aveva inserito campo vuoto)
		                  		concatenazione += (campiConcatenati[i] != null ? campiConcatenati[i] : " ") +"|"; 
		                  		
		                  		if(campiConcatenati[i] != null) 
		                  			atLeastOneField = true;
		                  	}
		                  	if(!atLeastOneField)
		                  		continue;
		                  	concatenazione = concatenazione.substring(0,concatenazione.length()-1);
		                  %>
		                	<option value=<%=concatenazione.replaceAll(" ","&nbsp;") %>><%=concatenazione %></option>
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
		                	/* if($("#selConcatenazionePerRappLegale option").size()<=1)
		                	{
		                		$("#selConcatenazionePerRappLegale").attr("disabled","disabled");
		                	} */
		                	$( "#selConcatenazionePerRappLegale" ).combobox();
		                	
		                	
		                	
		                	
		               </script>
		               <br>
				</td>
			</tr>
			
			
			<%-- <tr>
				<td colspan="3" nowrap>NOME</td>
				<td>
					<select id="selNomeRappLegale">
					
					<%for(String[] nomeRappSedeLegale : allValues.get("nomeRappSedeLegale")) 
		                  {%>
		                	<option value=<%=nomeRappSedeLegale[0].replaceAll(" ","&nbsp;") %>><%=nomeRappSedeLegale[0] %></option>
		                <%} 
		                   %>
		             </select>
				</td>
			</tr>
			<tr>
				<td colspan="3" nowrap><label for="cognome-2">COGNOME </label></td>
				<td>
				<select id="selCognomeRappLegale">
				
					<%for(String[] cognomeRappSedeLegale : allValues.get("cognomeRappSedeLegale")) 
		                  {%>
		                	<option value=<%=cognomeRappSedeLegale[0].replaceAll(" ","&nbsp;") %>><%=cognomeRappSedeLegale[0] %></option>
		                <%} 
		                   %>
		             </select>
				</td>
			</tr>
			<tr>
				<td colspan="3" nowrap><label for="sesso-2">SESSO </label></td>
				<td>
						<select id="selSessoRappSedeLegale">
						
						<%for(String[] sessoRappSedeLegale : allValues.get("sessoRappSedeLegale")) 
			                  {%>
			                	<option value=<%=sessoRappSedeLegale[0].replaceAll(" ","&nbsp;") %>><%=sessoRappSedeLegale[0] %></option>
			                <%} 
			                   %>
		             </select>
					</td>
			</tr>
			<tr> --%>
			<tr>
				<td colspan="3" nowrap><label for="dataN-2">DATA NASCITA </label></td>
				<td>
					<select id="selDataNascitaRappSedeLegale">
					
						<%
							if(allValues.get("dataNascitaRappSedeLegale") != null)
							{
							   for(String[] dataNascitaRappSedeLegale : allValues.get("dataNascitaRappSedeLegale")) 
				                  {
				                  	if(dataNascitaRappSedeLegale[0].matches(" *") && allValues.get("dataNascitaRappSedeLegale").size()>1)
				                  		continue;
				                    %>
				                	<option <%=!dataNascitaRappSedeLegale[0].matches(" *") ? "selected=\"selected\"" : ""%> value=<%=dataNascitaRappSedeLegale[0].replaceAll(" ","&nbsp;") %>><%=dataNascitaRappSedeLegale[0] %></option>
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
		                	/* if($("#selDataNascitaRappSedeLegale option").size()<=1)
		                	{
		                		$("#selDataNascitaRappSedeLegale").attr("disabled","disabled");
		                	} */
		                	$( "#selDataNascitaRappSedeLegale" ).combobox();
		               </script>
					  <br>
				</td>
				
			</tr>
			<tr>
				<td colspan="3" nowrap>COMUNE NASCITA</td>
				<td>
					<select id="selComuneNascitaRappSedeLegale">
					
						<%
						 if(allValues.get("comuneNascitaRappSedeLegale")!=null)
						 {for(String[] comuneNascitaRappSedeLegale : allValues.get("comuneNascitaRappSedeLegale")) 
			                  { if(comuneNascitaRappSedeLegale[0].matches(" *") && allValues.get("comuneNascitaRappSedeLegale").size()>1 )
			                  		continue;
			                  	%>
			                	<option <%=!comuneNascitaRappSedeLegale[0].matches(" *") ? "selected=\"selected\"" : ""%> value=<%=comuneNascitaRappSedeLegale[0].replaceAll(" ","&nbsp;") %>><%=comuneNascitaRappSedeLegale[0] %></option>
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
		                	/* if($("#selComuneNascitaRappSedeLegale option").size()<=1)
		                	{
		                		$("#selComuneNascitaRappSedeLegale").attr("disabled","disabled");
		                	} */
		                	$( "#selComuneNascitaRappSedeLegale" ).combobox();
		               </script>
		               <br>
				</td>
			</tr>
			<%-- <tr>
				<td colspan="3" nowrap>CODICE FISCALE</td>
				<td>
					<select id="selCfRappSedeLegale">
					
						<%for(String[] cfRappSedeLegale : allValues.get("cfRappSedeLegale")) 
			                  {%>
			                	<option value=<%=cfRappSedeLegale[0].replaceAll(" ","&nbsp;") %>><%=cfRappSedeLegale[0] %></option>
			                <%} 
			                   %>
		             </select>
				</td>
			</tr> --%>
			
			<tr  >
				<td colspan="3" nowrap>NAZIONE RESIDENZA</td>
				<td>
					<select  id="selNazioneResidenza">
					
						<%if(allValues.get("nazioneResidenza")!=null)
							{for(String nazioneResidenza[] : allValues.get("nazioneResidenza")) 
			                  {
			                  	if(nazioneResidenza[0].matches(" *") && allValues.get("nazioneResidenza").size()>1)
			                  		continue;
			                  %>
			                	<option <%=!nazioneResidenza[0].matches(" *") ? "selected=\"selected\"" : ""%> value=<%=nazioneResidenza[0].replaceAll(" ","&nbsp;") %>><%=nazioneResidenza[0] %></option>
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
		                	/* if($("#selSiglaProvSoggFisico option").size()<=1)
		                	{
		                		$("#selSiglaProvSoggFisico").attr("disabled","disabled");
		                	} */
		                	$( "#selNazioneResidenza" ).combobox();
		               </script>
					  <br>
				</td>
			</tr>
			
			<tr  >
				<td colspan="3" nowrap>PROVINCIA RESIDENZA</td>
				<td>
					<select  id="selSiglaProvSoggFisico">
					
						<%if(allValues.get("siglaProvSoggFisico")!=null)
							{for(String siglaProvSoggFisico[] : allValues.get("siglaProvSoggFisico")) 
			                  {
			                  	if(siglaProvSoggFisico[0].matches(" *") && allValues.get("siglaProvSoggFisico").size()>1)
			                  		continue;
			                  %>
			                	<option <%=!siglaProvSoggFisico[0].matches(" *") ? "selected=\"selected\"" : ""%> value=<%=siglaProvSoggFisico[0].replaceAll(" ","&nbsp;") %>><%=siglaProvSoggFisico[0] %></option>
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
		                	/* if($("#selSiglaProvSoggFisico option").size()<=1)
		                	{
		                		$("#selSiglaProvSoggFisico").attr("disabled","disabled");
		                	} */
		                	$( "#selSiglaProvSoggFisico" ).combobox();
		               </script>
					  <br>
				</td>
			</tr>
			<tr>
				<td  colspan="3" nowrap>COMUNE RESIDENZA</td>
				<td>
					<select  id="selComuneResidenza">
					
						<%
						 if(allValues.get("comuneResidenza")!=null)
						 {for(String[] comuneResidenza : allValues.get("comuneResidenza")) 
			                  {if(comuneResidenza[0].matches(" *") && allValues.get("comuneResidenza").size()>1 )
			                  	continue;
			                  %>
			                	<option <%=!comuneResidenza[0].matches(" *") ? "selected=\"selected\"" : ""%> value=<%=comuneResidenza[0].replaceAll(" ","&nbsp;") %>><%=comuneResidenza[0] %></option>
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
		                	/* if($("#selComuneResidenza option").size()<=1)
		                	{
		                		$("#selComuneResidenza").attr("disabled","disabled");
		                	} */
		                	$( "#selComuneResidenza" ).combobox();
		               </script>
		               <br>
				</td>
			</tr>
			<tr  >
				<td colspan="3" nowrap>CAP RESIDENZA</td>
				<td>
					<select  id="selCapResidenza">
					
						<%if(allValues.get("capResidenza")!=null)
							{for(String capResidenza[] : allValues.get("capResidenza")) 
			                  {
			                  	if(capResidenza[0].matches(" *") && allValues.get("capResidenza").size()>1)
			                  		continue;
			                  %>
			                	<option <%=!capResidenza[0].matches(" *") ? "selected=\"selected\"" : ""%> value=<%=capResidenza[0].replaceAll(" ","&nbsp;") %>><%=capResidenza[0] %></option>
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
		                	/* if($("#selSiglaProvSoggFisico option").size()<=1)
		                	{
		                		$("#selSiglaProvSoggFisico").attr("disabled","disabled");
		                	} */
		                	$( "#selCapResidenza" ).combobox();
		               </script>
					  <br>
				</td>
			</tr>
			<tr>
				<td colspan="3" nowrap>INDIRIZZO RESIDENZA</td>
				<td>
					<table class="noborder">
						<tr>
							<td>
							<!-- NON mostro tutti i possibili toponimi se è verificato che:
							il size dei toponimi ricevuti è > 0 e non accade che ce n'e' soltanto uno che è fatto da uno o piu' spazi vuoti soltanto -->
							<%if(allValues.get("toponimoResidenza").size() != 0 && !(allValues.get("toponimoResidenza").size()==1 && allValues.get("toponimoResidenza").get(0)[0].matches(" *") ) )
							{%>
								<select  id="selToponimoResidenza">
								
									<%for(String[] toponimoResidenza : allValues.get("toponimoResidenza")) 
						                  {if(toponimoResidenza[0].matches(" *") && allValues.get("toponimoResidenza").size()>1 )
						                  	continue;
						                  %>
						                	<option <%=!toponimoResidenza[0].matches(" *") ? "selected=\"selected\"" : ""%> value=<%=toponimoResidenza[1] %>><%=toponimoResidenza[0] %></option>
						                <%} 
						                   %>
						             
					             </select>
					             <script>
				                	 if($("#selToponimoResidenza option").size()<=1)
				                	{
				                		$("#selToponimoResidenza").attr("disabled","disabled");
				                	} 
				                	//$( "#selToponimoResidenza" ).combobox();
				                </script>
					         <%}
							else{
								out.print(lookupToponimi.getHtmlSelect("selToponimoResidenza",-1));
							}%>
							
							
				             <select id="selIndirizzoRappSedeLegale">
				             
								<%
								if(allValues.get("indirizzoRappSedeLegale") != null)
								{for(String[] indirizzoRappSedeLegale : allValues.get("indirizzoRappSedeLegale")) 
					                  { if(indirizzoRappSedeLegale[0].matches(" *") && allValues.get("indirizzoRappSedeLegale").size()>1)
					                  	continue;
					                  %>
					                	<option <%=!indirizzoRappSedeLegale[0].matches(" *") ? "selected=\"selected\"" : ""%> value=<%=indirizzoRappSedeLegale[0].replaceAll(" ","&nbsp;") %>><%=indirizzoRappSedeLegale[0] %></option>
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
				                	/* if($("#selIndirizzoRappSedeLegale option").size()<=1)
				                	{
				                		$("#selIndirizzoRappSedeLegale").attr("disabled","disabled");
				                	} */
				                	$( "#selIndirizzoRappSedeLegale" ).combobox();
				              </script>
				              
				              
				             <select  id="selCivicoResidenza">
				             
								<%if(allValues.get("civicoResidenza")!=null)
								 {for(String[] civicoResidenza : allValues.get("civicoResidenza")) 
					                  {
					                  	if(civicoResidenza[0].matches(" *") && allValues.get("civicoResidenza").size()>1)
					                  		continue;
					                  %>
					                	<option <%=!civicoResidenza[0].matches(" *") ? "selected=\"selected\"" : ""%> value=<%=civicoResidenza[0].replaceAll(" ","&nbsp;") %>><%=civicoResidenza[0] %></option>
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
				                	/* if($("#selCivicoResidenza option").size()<=1)
				                	{
				                		$("#selCivicoResidenza").attr("disabled","disabled");
				                	} */
				                	
				                	$( "#selCivicoResidenza" ).combobox();
				                	//alert(oldStyle);
				                	//$( "#selCivicoResidenza" ).attr("style",oldStyle+" "+"width: 10px important!;");
				                	
				              </script>
				               <br>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</fieldset>
	<fieldset id="setSedeLegale">
		<legend style="background-color:lightblue;">DATI SEDE LEGALE</legend>
		<table style="width:100%;">
			<col width="18%"/>
			
			<tr  >
				<td colspan="3" nowrap>NAZIONE SEDE LEGALE</td>
				<td>
					<select  id="selNazioneSedeLegale">
					
						<%if(allValues.get("nazioneSedeLegale")!=null)
							{for(String nazioneSedeLegale[] : allValues.get("nazioneSedeLegale")) 
			                  {
			                  	if(nazioneSedeLegale[0].matches(" *") && allValues.get("nazioneSedeLegale").size()>1)
			                  		continue;
			                  %>
			                	<option <%=!nazioneSedeLegale[0].matches(" *") ? "selected=\"selected\"" : ""%> value=<%=nazioneSedeLegale[0].replaceAll(" ","&nbsp;") %>><%=nazioneSedeLegale[0] %></option>
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
		                	/* if($("#selSiglaProvSoggFisico option").size()<=1)
		                	{
		                		$("#selSiglaProvSoggFisico").attr("disabled","disabled");
		                	} */
		                	$( "#selNazioneSedeLegale" ).combobox();
		               </script>
					  <br>
				</td>
			</tr>
			
			<tr id="searchcodeIdprovinciaTR">
				<td colspan="3" nowrap>PROVINCIA</td>
				<td>
							<select  id="selSiglaProvLegale">
							
								<%
								 if(allValues.get("siglaProvLegale")!=null)
								 {for(String[] siglaProvLegale : allValues.get("siglaProvLegale")) 
					                  { if(siglaProvLegale[0].matches(" *") && allValues.get("siglaProvLegale").size()>1)
					                  	continue;
					                 %>
					                	<option <%=!siglaProvLegale[0].matches(" *") ? "selected=\"selected\"" : ""%> value=<%=siglaProvLegale[0].replaceAll(" ","&nbsp;") %>><%=siglaProvLegale[0] %></option>
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
				                	/* if($("#selSiglaProvLegale option").size()<=1)
				                	{
				                		$("#selSiglaProvLegale").attr("disabled","disabled");
				                	} */
				                	$( "#selSiglaProvLegale" ).combobox();
				              </script>
				               <br>
				</td>
			</tr>
			<tr>
				<td colspan="3" nowrap >COMUNE</td>
				<td>
							<select id="selComuneSedeLegale">
							
								<%if(allValues.get("comuneSedeLegale")!=null)
								 {for(String[] comuneSedeLegale : allValues.get("comuneSedeLegale")) 
					                  {
										if(comuneSedeLegale[0].matches(" *") && allValues.get("comuneSedeLegale").size()>1)
					                  		continue;
					                  %>
					                	<option <%=!comuneSedeLegale[0].matches(" *") ? "selected=\"selected\"" : ""%> value=<%=comuneSedeLegale[0].replaceAll(" ","&nbsp;") %>><%=comuneSedeLegale[0] %></option>
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
				                	/* if($("#selComuneSedeLegale option").size()<=1)
				                	{
				                		$("#selComuneSedeLegale").attr("disabled","disabled");
				                	} */
				                	$( "#selComuneSedeLegale" ).combobox();
				              </script>
				               <br>
				</td>
			</tr>
			<tr>
				<td colspan="3" nowrap >CAP SEDE LEGALE</td>
				<td>
							<select id="selCapSedeLegale">
							
								<%if(allValues.get("capSedeLegale")!=null)
								 {for(String[] capSedeLegale : allValues.get("capSedeLegale")) 
					                  {
										if(capSedeLegale[0].matches(" *") && allValues.get("capSedeLegale").size()>1)
					                  		continue;
					                  %>
					                	<option <%=!capSedeLegale[0].matches(" *") ? "selected=\"selected\"" : ""%> value=<%=capSedeLegale[0].replaceAll(" ","&nbsp;") %>><%=capSedeLegale[0] %></option>
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
				                	/* if($("#selComuneSedeLegale option").size()<=1)
				                	{
				                		$("#selComuneSedeLegale").attr("disabled","disabled");
				                	} */
				                	$( "#selCapSedeLegale" ).combobox();
				              </script>
				               <br>
				</td>
			</tr>
			<tr>
				<td colspan="3" >INDIRIZZO</td>
				<td>
					<table class="noborder">
						<td>
							<!-- NON mostro tutti i possibili toponimi se è verificato che:
							il size dei toponimi ricevuti è > 0 e non accade che ce n'e' soltanto uno che è fatto da uno o piu' spazi vuoti soltanto -->
							<%if(allValues.get("toponimoSedeLegale") != null && allValues.get("toponimoSedeLegale").size() != 0 && !(allValues.get("toponimoSedeLegale").size()==1 && allValues.get("toponimoSedeLegale").get(0)[0].matches(" *") ) )
							{%>
								<select  id="selToponimoSedeLegale">
									<%for(String[] toponimoSedeLegale : allValues.get("toponimoSedeLegale")) 
						                  {
						                  if(toponimoSedeLegale[0].matches(" *") && allValues.get("toponimoSedeLegale").size()>1)
						                	  continue;
						                  %>
						                	<option <%=!toponimoSedeLegale[0].matches(" *") ? "selected=\"selected\"" : ""%> value=<%=toponimoSedeLegale[1] %>><%=toponimoSedeLegale[0] %></option>
						                <%} 
						                   %>
					             </select>
					             <script>
				                	 if($("#selToponimoSedeLegale option").size()<=1)
				                	{
				                		$("#selToponimoSedeLegale").attr("disabled","disabled");
				                	} 
				                	//$( "#selToponimoSedeLegale" ).combobox();
				             	 </script>
					         <%}
							else
							{
								out.print(lookupToponimi.getHtmlSelect("selIndirizzoSedeLegale",-1));
							}%>
							
							
							
				             <select  id="selIndirizzoSedeLegale">
				             
								<%if(allValues.get("indirizzoSedeLegale")!=null)
									{for(String[] indirizzoSedeLegale : allValues.get("indirizzoSedeLegale")) 
					                  {if(indirizzoSedeLegale[0].matches(" *") && allValues.get("indirizzoSedeLegale").size()>1) 
					                  	continue;
					                  %>
					                	<option <%=!indirizzoSedeLegale[0].matches(" *") ? "selected=\"selected\"" : ""%> value=<%=indirizzoSedeLegale[0].replaceAll(" ","&nbsp;") %>><%=indirizzoSedeLegale[0] %></option>
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
				                	/* if($("#selIndirizzoSedeLegale option").size()<=1)
				                	{
				                		$("#selIndirizzoSedeLegale").attr("disabled","disabled");
				                	} */
				                	$( "#selIndirizzoSedeLegale" ).combobox();
				             	 </script>
				             	 
				             	 
				             <select  id="selCivicoSedeLegale">
				             
								<%if(allValues.get("civicoSedeLegale")!=null)
								 {for(String[] civicoSedeLegale : allValues.get("civicoSedeLegale")) 
					                  {if(civicoSedeLegale[0].matches(" *") && allValues.get("civicoSedeLegale").size()>1)
					                  	continue;%>
					                	<option <%=!civicoSedeLegale[0].matches(" *") ? "selected=\"selected\"" : ""%> value=<%=civicoSedeLegale[0].replaceAll(" ","&nbsp;") %>><%=civicoSedeLegale[0] %></option>
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
				                	/* if($("#selCivicoSedeLegale option").size()<=1)
				                	{
				                		$("#selCivicoSedeLegale").attr("disabled","disabled");
				                	} */
				                	$( "#selCivicoSedeLegale" ).combobox();
				             	 </script>
				               <br>
							</td>
					</table>
				</td>
			</tr>
			<tr><td colspan="11"><div align="right"><input type="button" value="INDIETRO" onclick="intercettaBtnIndietro('<%=(String)request.getAttribute("pIvaRichiesta")%>'	)" /><input type="button" value="SALVA" onclick="intercettaSalvaBtn('<%=(String)request.getAttribute("pIvaRichiesta")%>','<%=User.getUserRecord().getId()+""%>')"/> </div> </td> </tr>
		</table>
	</fieldset>
	


<script>
	
	
		
	function intercettaBtnIndietro(pIvaRichiesta)
	{
		window.location.href = "InterfAnalisiDuplicatiOpu.do?command=RicercaTuttiDuplicatiPerPIva&pIvaCercata="+pIvaRichiesta;
	}

	function intercettaSalvaBtn(pIvaRichiesta,idUtente)
	{
		
		var idTipoImpresa = $("#selTipoImpresa").val();
		var idTipoSocieta = $("#selTipoSocieta").val();
		//se è attivata l'opzione per scegliere qualnque tipo impresa/societa 
		//
		
			
			if(idTipoImpresa == -1)
			{
				alert("Scegliere TIPO IMPRESA");
				return;
			}
			if(idTipoSocieta == -1)
			{
				alert("Scegliere TIPO SOCIETA");
				return;
			}
			
		
		
		
		
		var ragioneSociale = $("#selRagioneSociale").val();
		var partitaIva = $("#selPartitaIva").val();
		var codFiscale = $("#selCodFiscale").val();
		var domicilioDigitale = $("#selDomicilioDigitale").val();
		//var nomeRappLegale = $("#selNomeRappLegale").val();
		//var cognomeRappLegale = $("#selCognomeRappLegale").val();
		//var sessoRappSedeLegale = $("#selSessoRappSedeLegale").val();
		//var cfRappSedeLegale = $("#selCfRappSedeLegale").val();
		var concatenazionePerRappLegale = $("#selConcatenazionePerRappLegale").val();
		//alert(concatenazionePerRappLegale);
		
		var dataNascitaRappSedeLegale = $("#selDataNascitaRappSedeLegale").val();
		var comuneNascitaRappSedeLegale = $("#selComuneNascitaRappSedeLegale").val();
		
		var siglaProvSoggFisico = $("#selSiglaProvSoggFisico").val();
		var comuneResidenza = $("#selComuneResidenza").val();
		var nazioneResidenza = $("#selNazioneResidenza").val();
		var capResidenza = $("#selCapResidenza").val();
		var idToponimoResidenza = $("#selToponimoResidenza").val();
		var indirizzoRappSedeLegale = $("#selIndirizzoRappSedeLegale").val();
		var civicoResidenza = $("#selCivicoResidenza").val();
		var siglaProvLegale = $("#selSiglaProvLegale").val();
		var comuneSedeLegale = $("#selComuneSedeLegale").val();
		var nazioneSedeLegale = $("#selNazioneSedeLegale").val();
		var capSedeLegale = $("#selCapSedeLegale").val();
		var idToponimoSedeLegale = $("#selToponimoSedeLegale").val();
		var indirizzoSedeLegale = $("#selIndirizzoSedeLegale").val();
		var civicoSedeLegale = $("#selCivicoSedeLegale").val();
	
		
		/*console.log(idTipoImpresa);
		console.log(itTipoSocieta);
		console.log(ragioneSociale);
		console.log(partitaIva);
		console.log(codiceFiscale);
		console.log(domicilioDigitale);
		console.log(nomeRappLegale);
		console.log(cognomeRappLegale);
		console.log(sessoRappSedeLegale);
		console.log(dataNascitaRappSedeLegale);
		console.log(comuneNascitaRappSedeLegale);
		console.log(cfRappSedeLegale);
		console.log(siglaProvSoggFisico);
		console.log(comuneResidenza);
		console.log(idToponimoResidenza);
		console.log(indirizzoRappSedeLegale);
		console.log(civicoResidenza);
		console.log(siglaProvLegale);
		console.log(comuneSedeLegale);
		console.log(idToponimoSedeLegale);
		console.log(indirizzoRappSedeLegale);
		console.log(civicoSedeLegale);
		console.log(window.idImprese);*/
		
		var jsonRepr = {};
		jsonRepr.idTipoImpresa = idTipoImpresa;
		jsonRepr.idTipoSocieta = idTipoSocieta;
		jsonRepr.ragioneSociale = ragioneSociale;
		jsonRepr.partitaIva = partitaIva;
		jsonRepr.codFiscale = codFiscale;
		jsonRepr.domicilioDigitale = domicilioDigitale;
		//jsonRepr.nomeRappLegale = nomeRappLegale;
		//jsonRepr.cfRappSedeLegale = cfRappSedeLegale;
		//jsonRepr.cognomeRappLegale = cognomeRappLegale;
		//jsonRepr.sessoRappSedeLegale = sessoRappSedeLegale;
		jsonRepr.dataNascitaRappSedeLegale = dataNascitaRappSedeLegale;
		jsonRepr.comuneNascitaRappSedeLegale = comuneNascitaRappSedeLegale;
		jsonRepr.nazioneSedeLegale = nazioneSedeLegale;
		jsonRepr.capSedeLegale = capSedeLegale;
		jsonRepr.concatenazionePerRappLegale =  concatenazionePerRappLegale;
		jsonRepr.siglaProvSoggFisico = siglaProvSoggFisico;
		jsonRepr.comuneResidenza = comuneResidenza;
		jsonRepr.nazioneResidenza = nazioneResidenza;
		jsonRepr.capResidenza = capResidenza;
		jsonRepr.idToponimoResidenza = idToponimoResidenza;
		jsonRepr.indirizzoRappSedeLegale = indirizzoRappSedeLegale;
		jsonRepr.civicoResidenza = civicoResidenza;
		jsonRepr.siglaProvLegale = siglaProvLegale;
		jsonRepr.comuneSedeLegale = comuneSedeLegale;
		jsonRepr.idToponimoSedeLegale = idToponimoSedeLegale;
		jsonRepr.indirizzoSedeLegale = indirizzoSedeLegale;
		jsonRepr.civicoSedeLegale = civicoSedeLegale;
		jsonRepr.idOperatori = window.idImprese;
		jsonRepr.idUtente = idUtente;
		window.location.href = "InterfAnalisiDuplicatiOpu.do?command=ConvergiDuplicati&pIvaCercata="+pIvaRichiesta+"&datiDaForm="+JSON.stringify(jsonRepr).replace(/\s/g,'$');
		
		//faccio replace dei caratteri vuoti per problemi di encoding
		//alert(JSON.stringify(jsonRepr).replace(/\s/g,'+'));
	}

</script>
