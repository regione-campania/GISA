<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/theme.css"></link>
<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/jquery.tablesorter.pages.css"></link>
	
	<style>
		input[disabled]{
			opacity: 0.4;
			filter: alpha(opacity = 40);
		}
	</style>

<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.pager.js"></script>

<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.widgets.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/tableJqueryFilterDialogRichiesteDaValidare.js"></script>




		<script>
		
			var entriesChecked = {}
		
			function checkPremuto(idOperatore, idStabilimento) //uso stabilimento come chiave
			{
				
				disattivaBottoni();
				
				var checked = document.getElementById("checkBoxDuplicato"+idStabilimento).checked;
				if(checked)
				{
					//alert("E' CHECKED");
					entriesChecked[idStabilimento] = idOperatore;
				}
				else
				{
					//alert("E' NON  CHECKED");
					delete entriesChecked[idStabilimento];
				}
				
				console.log(entriesChecked);
				
				//CONTROLLO QUALE BOTTONE ATTIVARE:
				
				//se ho selezionato una sola entry, comunque non posso attivare bottoni
				if(Object.keys(entriesChecked).length <= 1)
					return;
				
				
				//scorro nella mappa con tutte le entries checked, e vedo se attivare il bottone per convergenza impianto
				//o per convergenza impresa
		  		//se tutte le entry hanno stesso id impresa, allora era già stata fatta convergenza per impresa, e disattivo il bottone conv impresa e attivo quello di stabilimento
			  	//altrimenti il contrario se esiste almeno un'id impresa diverso 
				var tuttiUguali = true;
				var primoIdOp = entriesChecked[Object.keys(entriesChecked)[0]];
				
			  	for(idStabs in entriesChecked)
				{
					if(entriesChecked[idStabs] !== primoIdOp)
					{
						
						tuttiUguali = false;
						break;
					}
				}
			  	if(tuttiUguali) 
			  	{
			  		attivaBottonePerImpianto();
			  	}
			  	else
			  	{
			  		attivaBottonePerImpresa();
			  	}
			  	
			}
			
			function intercettaBtnConvImpresa(pIva)
			{
				//alert("CONV IMPRESA");
				//richiamo la action mandando la rapp json delle entries premute
				window.location.href = "InterfAnalisiDuplicatiOpu.do?command=PreparaFormPerConvergenzaDuplicatiOperatore&entriesChecked="+JSON.stringify(entriesChecked)+"&pIvaRichiesta="+pIva;
			}
			
			function intercettaBtnConvImpianto(pIva)
			{
				window.location.href = "InterfAnalisiDuplicatiOpu.do?command=PreparaFormPerConvergenzaDuplicatiImpianti&entriesChecked="+JSON.stringify(entriesChecked)+"&pIvaRichiesta="+pIva;
			}
			
			function disattivaBottoni()
			{
				$("#btnConvImpianto").attr("disabled","true");
				$("#btnConvImpresa").attr("disabled","true");
			}
 			function attivaBottonePerImpresa()
 			{
 					$("#btnConvImpianto").attr("disabled","true");
					$("#btnConvImpresa").removeAttr("disabled");
					$("#btnConvImpresa").css("visibility","visible");
					$("#btnConvImpianto").css("visibility","visible");
 			} 
 			function attivaBottonePerImpianto()
 			{
 					$("#btnConvImpresa").attr("disabled","true");
					$("#btnConvImpianto").removeAttr("disabled");
					$("#btnConvImpresa").css("visibility","visible");
					$("#btnConvImpianto").css("visibility","visible");
 			}
			
		
		</script>
		
	
	
	 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	    pageEncoding="ISO-8859-1"%>
	    
	<%@ page import="java.util.ArrayList" %>
	<%@page import="org.aspcfs.modules.opu.base.*"%>
	<%-- <%@ include file="../initPage.jsp" %> --%>



	 <%
	    String pIvaCercata = (String)request.getAttribute("pIvaCercata"); 
	    ArrayList<OperatorePerDuplicati> duplicati =(ArrayList<OperatorePerDuplicati>) request.getAttribute("duplicatiPerPIva");
	   
	  %>
	
    	<table width="100%">
		  <colgroup><col width="50%">
		  </colgroup>
		  <tbody>
		  <tr>
		   
		    <td valign="top">  
		    <form name="searchDuplicatiOpu" action="InterfAnalisiDuplicatiOpu.do?command=RicercaTuttiDuplicatiPerPIva" method="post">
		            <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
		                <tr>
		                	<th colspan="2"><strong><dhv:label name="">Ricerca Per PIVA</dhv:label></strong></th>
		                </tr>
		                <tr>
			                <td class="formLabel">
			                  Partita IVA
			                </td>
			                <td>
			                  <input id ="pIvaText" type="text" size="23" name="pIvaCercata" value="<%=pIvaCercata == null ? "" : pIvaCercata %>" >
			                  <input type="submit" value="Ricerca">
			                </td>
		                </tr> 
		              </table>
		      </form> 
		      </td> 
		      </tr>  
		      </tbody>   
		  </table><br>
		  <div id="divMsgEsito" style="background-color: lightgreen;" >
		  	${msgEsito}
		  </div>
			<table class="details" width="100%">
			 	
	  			<%
	  				if(duplicati != null && duplicati.size() == 0)
	  				{%>
  					
	  					<tr>
	  						<td  colspan="11">
	  							NON ESISTONO DUPLICATI PER LA PARTITA IVA RICERCATA
	  						</td>
	  					</tr>
  						</table>
  					<%}
	  				else if(duplicati != null) //è pieno
	  				{%>
	  					
	  					
	  					<tr>
			  				<th style="font-size:12px; text-align: center">ID STABILIMENTO</th>
			  				<th style="font-size:12px; text-align: center">ID OPERATORE</th>
			  				<th style="font-size:12px; text-align: center">TIPO IMPRESA</th>
			  				<th style="font-size:12px; text-align: center">TIPO SOCIETA</th>
			  				<th style="font-size:12px; text-align: center">RAGIONE SOCIALE</th>
			  				<th style="font-size:12px; text-align: center">PARTITA IVA</th>
			  				<th style="font-size:12px; text-align: center">CODICE FISCALE</th>
			  				<th style="font-size:12px; text-align: center">NOME RAPPR.</th>
			  				<th style="font-size:12px; text-align: center">COGNOME RAPPR.</th>
			  				<th style="font-size:12px; text-align: center">PROV LEGALE</th>
			  				<th style="font-size:12px; text-align: center">COMUNE SEDE LEGALE</th>
			  				<th  style="font-size:12px; text-align: center"> &nbsp;</th>
			  			</tr>
	  					
	  				<%	
	  					for(int i=0;i<duplicati.size();i++)
	  					{
	  						OperatorePerDuplicati t = duplicati.get(i);
	  						%>
	  						
	  						<tr>
		  						<td  colspan="1"><%=t.getIdStabilimento()%></td>
		  						<td  colspan="1"><%=t.getIdOperatore()%></td>
		  						<td  colspan="1"><%=t.getTipoImpresa()%></td>
		  						<td  colspan="1"><%=t.getTipoSocieta()%></td>
		  						<td  colspan="1"><%=t.getRagioneSociale()%></td>
		  						<td  colspan="1"><%=t.getPartitaIva()%></td>
		  						<td  colspan="1"><%=t.getCodFiscale()%></td>
		  						<td  colspan="1"><%=t.getNomeRappSedeLegale()%></td>
		  						<td  colspan="1"><%=t.getCognomeRappSedeLegale()%></td>
		  						<td  colspan="1"><%=t.getSiglaProvLegale()%></td>
		  						<td  colspan="1"><%=t.getComuneSedeLegale()%></td>
		  						<td colspan="1"> <input type="checkbox" id=<%="checkBoxDuplicato"+t.getIdStabilimento()%>  onclick="checkPremuto('<%=t.getIdOperatore() %>','<%=t.getIdStabilimento() %>')" /> </td>
		  					</tr>
	  						
	  					<%}
	  					%>
	 					 </table>
	 					 <input disabled id="btnConvImpresa" type ="button" value = "CONVERGI IMPRESA" onclick = "intercettaBtnConvImpresa('<%=pIvaCercata%>')"/>
	 					 <input disabled id="btnConvImpianto" type ="button" value = "CONVERGI IMPIANTO" onclick = "intercettaBtnConvImpianto('<%=pIvaCercata%>')" />
	  					<%		
	  					
			  			
	  				}
	  				else //allora abbiamo aperto la pagina per la prima volta, quindi non ci siamo arrivati per la ricerca
	  				{%>
	  					</table>
	  					
	  				<%}
	  			%>
	  			
	
	 
	
		 
		





	<%-- <table width="100%" class="details">
	  	<col width="280px" />
    
	  <colgroup><col width="0%">
	  </colgroup>
	       <tbody>
	   		<tr>
	                <th ><strong><dhv:label name="">P.Iva</dhv:label></strong></th>
	                <th ><strong><dhv:label name="">Operazione</dhv:label></strong></th>
	                <th ><strong><dhv:label name="">Validazione</dhv:label></strong></th>
	    	</tr>
	    	<%
	    	for(int i=0;i<res.size();i++)
	    	{%>
		    	<tr>
		    		<td id="<%="riga"+i %>"><%=res.get(i).getPartitaIva() %></td>
		    		<td> OPERAZIONEDUMMY </td>
		    		<td><input type="submit" value="convergi" onClick="intercettaValidaButton('<%=i%>')"/></td>
		    	</tr>
	    	<%} %>
	    	
	      </tbody>   
	  </table>        	 --%>

	  
