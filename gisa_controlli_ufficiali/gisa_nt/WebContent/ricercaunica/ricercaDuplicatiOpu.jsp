<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>


	<%@page import="org.aspcfs.modules.opu.base.*"%>
	<%@page import="org.aspcfs.modules.ricercaunica.base.OpuDuplicatiWrapper"%>
	<%@page import = "java.util.ArrayList" %>
	<%@page import="org.json.JSONObject" %>

	<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
	<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
	<%!int numeroEntries = 0; %>
	 
	
	
		
	<style>
		
		.rigaDettaglio th
		{
			font-size: 9px;
		}
		
		.rigaDettaglio td
		{
			font-size: 8px;
		}
		
		

	
	</style>	
		
	
	  <div hidden="hidden" id="divPIva"><%=(String)request.getAttribute("pIvaToSearch") %></div><br>
	  <table width="100%" class="details">
	  	<col width="280px" />
    
	  <colgroup><col width="0%">
	  </colgroup>
	       <tbody>
	   		<tr>
	                <th ><strong><dhv:label name="">P.Iva</dhv:label></strong></th>
	                <th ><strong><dhv:label name="">Da convergere</dhv:label></strong></th>
	                <th ><strong><dhv:label name="">Cod Fiscale</dhv:label></strong></th>
	                <th><strong><dhv:label name="">Id</dhv:label></strong></th>
	                <th ><strong><dhv:label name="">Ragione Sociale</dhv:label></strong></th>
	                <th ><strong><dhv:label name="">Domicilio Digitale</dhv:label></strong></th>
	                <th ><strong><dhv:label name="">Tipo Societa</dhv:label></strong></th>
	    	</tr>
	    		
		    	<%if(request.getAttribute("duplicatiOpuTrovati") != null )
		    	{
		    		
		    		//cerco di estrarre l'info relativa al sottogruppo di operatori checked (se esiste) che arriva come stringa json
		    		String jsonStringIdOpChecked = (String)request.getAttribute("opIdDaConvergere");
		    		
		    		JSONObject jsonObjSottogruppoOps = null;
		    		if(jsonStringIdOpChecked != null)
		    		{
		    			jsonObjSottogruppoOps = new JSONObject(jsonStringIdOpChecked);
		    		}
		    		
		    		ArrayList<OperatorePerDuplicati> trovati = (ArrayList<OperatorePerDuplicati>) request.getAttribute("duplicatiOpuTrovati");
		    		if(trovati.size() == 0)
		    			System.out.println("VUOTO!");
		    		else
		    		{
		    			this.numeroEntries = trovati.size();
		    			System.out.println("----trovati "+(trovati.size()-1)+" risultati"); 
		    			for(int i=0;i<trovati.size();i++)
		    			{
		    				String pIva = trovati.get(i).getPartitaIva();
		    				String codF = trovati.get(i).getCodFiscale();
		    				int idOp = trovati.get(i).getIdOperatore();
		    				String ragSoc = trovati.get(i).getRagioneSociale();
		    				String dom = trovati.get(i).getDomicilioDigitale();
		    				int tipoSoc  = trovati.get(i).getTipo_societa();
		    				boolean isChecked = false;
		    				try
		    				{
			    				if(jsonStringIdOpChecked != null && jsonObjSottogruppoOps.get(idOp+"") != null && (boolean)jsonObjSottogruppoOps.getBoolean(idOp+"") == true )
			    				{
			    					isChecked = true;
			    				}
		    				}
		    				catch(Exception ex)
		    				{
		    					//NIENTE
		    				}
		    				
		    				
		    				if(i == 0) //quello in prima posizione è sempre quello per ilq uale abbiamo eseguito risultato (originale)
		    				{%>
		    					<tr id ="<%="rigaEntry"+i %>" style=" background-color: lightblue; " ondblclick="animazioneRigaDettaglio(<%=i %>,<%=numeroEntries %>)" >
		    						<td ><%=pIva%></td>
		    						<td>-</td>
		    						<td ><%=codF%></td>
		    						<td id="idOpOriginalex"><%=idOp%></td>
		    						<td ><%=ragSoc%></td>
		    						<td ><%=dom%></td>
		    						<td ><%=tipoSoc%></td>
		    					<tr>
		    				<%}
		    				else
		    				{%>
		    					<tr id ="<%="rigaEntry"+i %>" ondblclick="animazioneRigaDettaglio(<%=i %>,<%=numeroEntries %>)"  >
		    						<td ><%=pIva%></td>
		    						<td><input type="checkbox" id="<%="checkbox"+i %>" onchange="clickSuCheckBoxPerConvergenza(this,<%=idOp%>)" <%=isChecked ? "checked" : ""  %> /></td>
		    						<td ><%=codF%></td>
		    						<td ><%=idOp%></td>
		    						<td ><%=ragSoc%></td>
		    						<td ><%=dom%></td>
		    						<td ><%=tipoSoc%></td>
		    					<tr>
		    				<%}
		    				%>
		    				
		    				<!-- questa è la riga relativa al dettaglio per un'entry -->
		    				<tr class="rigaDettaglio" id="<%="rigaDettaglio"+i %>" style=" display: none;  " >
		    					<td>
		    						<table>
		    							<tr>
						    					<td>
						    					<table id="tabellaDettaglio1">
						    						
						    						<tr>
						    							<th >NOME RAPPR.</th><th>COGNOME RAPPR.</th><th>COD. FISCALE RAPPR.</th>
						    						</tr>
						    						<tr>
						    							<td><%=(trovati.get(i).getRappLegalePerDup().getNome()!= null ? trovati.get(i).getRappLegalePerDup().getNome() : "") %></td><td><%=(trovati.get(i).getRappLegalePerDup().getCognome()!= null ? trovati.get(i).getRappLegalePerDup().getCognome() : "" )%></td><td><%=(trovati.get(i).getRappLegalePerDup().getCodiceFiscale()!= null ? trovati.get(i).getRappLegalePerDup().getCodiceFiscale() : "" ) %></td>
						    						</tr>
						    						
						    					 </table>
						    					 </td>
						    				</tr>
						    				<tr>
						    					 <td>
						    					<table id="tabellaDettaglio2">
						    						
						    						<tr>
						    							<th>COMUNE SEDE LEGALE</th><th>INDIRIZZO SEDE LEGALE</th>
						    						</tr>
						    						<tr>
						    							<td><%=(trovati.get(i).getSedePerDup().getComune()!= null ? trovati.get(i).getSedePerDup().getComune() : "") %></td><td><%=(trovati.get(i).getSedePerDup().getVia()!= null ? trovati.get(i).getSedePerDup().getVia() : "" ) %></td>
						    						</tr>
						    						
						    					 </table>
						    					 </td>
						    			    </tr>
						    			    <tr>
						    					 <td>
						    					<table id="tabellaDettaglio3">
						    						
						    						<tr>
						    							<th>TIPO ATTIVITA</th><th>CARATTERE</th><th>DATA INIZIO</th><th>COMUNE</th><th>INDIRIZZO</th>
						    						</tr>
						    						<tr>
						    							<td>NomeDummy</td><td>CognomeDummy</td><td>M</td><td>28/4/88</td><td>CARATTERE</td>
						    						</tr>
						    						
						    					 </table>
						    					 </td>
						    			    </tr>
						    		</table>
				    			</td>
		    				</tr>
		    				
		    				
		    			
		    				<%
		    			}
		    		}
		    	}
		    	  %>  	
	    	
	      </tbody>   
	  </table>          
	      
	 <script>
	 
	 	$(document).ready(function()
	 	{
	 		if($("#formConvergenzaManuale").length > 0) //vuol dire che esiste questo form, quindi allora era stato generato form conv manuale poichè era stato richiesto dal srv
	 		{
	 			disabilitaCheckTabellaEConvergiButton();
	 		}
	 	});
	 	
	 	
	 	
	 	function abilitaCheckTabellaEConvergiButton()
	 	{
	 		for(var i=0;i<parseInt('<%=numeroEntries %>');i++ )
	 		{
	 			$("#checkbox"+i).removeAttr("disabled");
	 		}
	 		$("#convergiButton").removeAttr("disabled");
	 		$("#convergiButton").css("background-color", "green");
	 		
	 		$("#indietroButton").removeAttr("disabled");
	 		$("#indietroButton").css("background-color", "blue");
	 		
	 	}
	 	
	 	function disabilitaCheckTabellaEConvergiButton()
	 	{
	 		for(var i=0;i<parseInt('<%=numeroEntries %>');i++ )
	 		{
	 			$("#checkbox"+i).attr("disabled",true);
	 		}
	 		$("#convergiButton").attr("disabled",true);
	 		$("#convergiButton").css("background-color", "grey");
	 		
	 		$("#indietroButton").attr("disabled",true);
	 		$("#indietroButton").css("background-color", "grey");
	 	}
	 
	 	var pIveToConverge = {};
	 	
	 	
	 	function clickSuCheckBoxPerConvergenza(source,idOp)
	 	{
	 		if(source.checked == true)
	 			pIveToConverge[idOp] = true;
	 		else
	 			pIveToConverge[idOp] = false;
	 		
	 		
	 	}
	 
	 	function intercettaConvergiButton()
	 	{
	 		
	 		var pIva = $("#divPIva").text();
	 		//alert(pIva);
	 		if(pIva == "undefined" || pIva == "")
	 		{
	 			alert("Inserire un valore per partita iva");
	 		}
	 		var inputA = document.createElement("input");
	 		inputA.type ="hidden";
	 		inputA.name = "pIvaToConverge";
	 		inputA.value = pIva;
	 		//setto anche la partita iva come pivatosearch
	 		//in modo tale che se dal comando di convergenza richiamo quello di search
	 		//ottengo anche quest'ultimo risultato
	 		var inputB = document.createElement("input");
	 		inputB.type="hidden";
	 		inputB.name="pIvaToSearch";
	 		inputB.value= pIva;
	 		
	 		var inputC = document.createElement("input");
	 		inputC.type="hidden";
	 		inputC.name="opIdDaConvergere";
	 		pIveToConverge[ $("#idOpOriginalex").html() ] = true; //aggiungo la piva dell'originale (per il quale il checkbox non esiste)
	 		inputC.value = JSON.stringify(pIveToConverge);
	 		
	 		var form = document.getElementById("formConvergenza");
	 		form.appendChild(inputA);
	 		form.appendChild(inputB);
	 		form.appendChild(inputC);
	 		form.submit();
	 	    
	 	    
	 	}
	 	
	 	function intercettaConvergiManualmenteButton()
	 	{
	 		var pIva = $("#divPIva").text();
	 		if(pIva == "undefined" || pIva == "")
	 		{
	 			alert("Inserire un valore per partita iva");
	 		}
	 		var inputA = document.createElement("input");
	 		inputA.type ="hidden";
	 		inputA.name = "pIvaToConverge";
	 		inputA.value = pIva;
	 		//setto anche la partita iva come pivatosearch
	 		//in modo tale che se dal comando di convergenza richiamo quello di search
	 		//ottengo anche quest'ultimo risultato
	 		var inputB = document.createElement("input");
	 		inputB.type="hidden";
	 		inputB.name="pIvaToSearch";
	 		inputB.value= pIva;
	 		
	 		var inputC = document.createElement("input");
	 		inputC.type="hidden";
	 		inputC.name="risultato_convergenza";
	 		inputC.value= "manuale";
	 		
	 		var form = document.getElementById("formConvergenzaManuale");
	 		form.appendChild(inputA);
	 		form.appendChild(inputB);
	 		form.appendChild(inputC);
	 		form.submit();
	 	}
	 	
	 	function animazioneRigaDettaglio(indiceRiga,totEntries)
	 	{
	 		//rendo tutte le righe bianche e poi evidenzio in verde solo la cliccata
	 		//for(var i=0;i<totEntries;i++)
	 			//$("#rigaEntry"+i).css("background-color","white");
	 		//$("#rigaEntry"+indiceRiga).css("background-color","lightgreen");
	 		
	 		//mostro riga di dettaglio associata alla riga entry cliccata (di default è sempre verde la riga di dettaglio)
	 		$("#rigaDettaglio"+indiceRiga).toggle();
	 	}
	 	
	 	
	 	
	 </script>     
	 
	 <table class="details">
	 	<tbody>
	 		<tr>
	 			<td>
	 				<form id ="formConvergenza" action="InterfAnalisiDuplicatiOpu.do?command=ConvergiDuplicati" method="POST">
	 					<input type="submit" id="convergiButton" value="CONVERGI" onClick="intercettaConvergiButton();" style="background-color: green;"/>
	 				</form>
	 			</td>
	 			<td>
	 				<form action="InterfAnalisiDuplicatiOpu.do?command=ConvergiDuplicatiMostraTuttePIvaConDuplicati" method="post">
	 					<input id ="indietroButton" type="submit" value="INDIETRO" />
	 				</form>
	 			</td>
	 		
	 		</tr>
	 	</tbody>
	 </table>     
	 <!-- se risultato convergenza è ok allora è stata effettuata automaticamente, altrimenti se è manuale, occorre mostrare maschera per 
	 effettuarla manualmente -->
	 <%if( request.getAttribute("risultato_convergenza") != null && ( (String)request.getAttribute("risultato_convergenza")).equals("ok") ) 
	 {%>
	 					Convergenza effettuata con successo !
	 					
	 <%}
	 else if(request.getAttribute("risultato_convergenza") != null && ( (String)request.getAttribute("risultato_convergenza")).equals("fallita"))
	 {%>
		 			   Convergenza fallita !
	 <%}
	 else if(request.getAttribute("risultato_convergenza") != null && ( (String)request.getAttribute("risultato_convergenza")).equals("manuale")) {%>
	 				    Necessaria convergenza manuale
	 				    
	 				    <!-- le informazioni residue del sottogruppo di operatori scelti relativamente ai quali fare convergenza sono ricevute 
	 				    in attr request come stringa json -->
	 				    <!-- se è necessaria la convergenza manuale, disattivo temporaneamente la tabella superiore (disattivando i checkbox e il bottone convergi)-->
	 				    
	 				    
	 				    <br>
	 				  	<form id="formConvergenzaManuale" action="InterfAnalisiDuplicatiOpu.do?command=ConvergiDuplicati" method="POST">
	 				  		<input type="submit" value="CONV.MANUALe" onClick="intercettaConvergiManualmenteButton();"/>
	 				  		<input type="hidden" name="opIdDaConvergere" value="<%=request.getAttribute("opIdDaConvergere") %>" />
	 				  	</form>
	 				    <form method="post" action="InterfAnalisiDuplicatiOpu.do?command=OttieniListaDuplicati&pIvaToSearch=<%=request.getParameter("pIvaToSearch") %>" > <!-- ritorniamo a questa stessa pagina, non in modalitaà convergenza manuale, con la stessa piva richiesta -->
	 				    	<input type="submit" value="ANNULLA"/>
	 				    </form>
	 <%} %>
	      
	   
