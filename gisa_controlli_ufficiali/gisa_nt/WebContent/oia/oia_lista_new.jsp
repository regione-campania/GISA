<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page import="java.util.*,java.text.DateFormat"%>
<%@page import="org.aspcfs.modules.oia.base.OiaNodo"%>
<%@page import="com.darkhorseventures.framework.actions.ActionContext"%>

<%@page import="com.itextpdf.text.log.SysoLogger"%><jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="nodi_livello_1" class="java.util.ArrayList" scope="request"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" >

		function confermaDelete(url) {
			  if ( confirm('ATTENZIONE! Sicuro di voler proseguire con l\'eliminazione del nodo?') ) {
			    window.location = url;
			  }
			}

		function nonPossibileDelete() {
			  alert('ATTENZIONE! Non è possibile continuare in quanto il nodo corrente ha figli. Cancellare prima i figli per proseguire...');
			}
	
		function switch_elemento_albero(id_immagine_elemento, id_sotto_elemento) {
			if (document.getElementById(id_sotto_elemento).style.display=='') {
				document.getElementById(id_immagine_elemento).src = "images/tree0.gif";
				document.getElementById(id_sotto_elemento).style.display='none';
			} else {
				document.getElementById(id_immagine_elemento).src = "images/tree1.gif";
				document.getElementById(id_sotto_elemento).style.display='';
			}
		}

		function switch_elemento(id)
		{
			if (document.getElementById(id).style.display=='')
				document.getElementById(id).style.display='none';
			else
				document.getElementById(id).style.display='';
		}
		
		function inRow(riga)
		{
			riga.style.background='#FFF5EE';
		}
		
		function outRow(i,riga)
		{
			if (i==1)
			{
				riga.style.background='#EDEDED';
			}
			else
			{
				riga.style.background='#FFFFFF';
			}
		}
</SCRIPT>


<%@ include file="../initPage.jsp"%>

	<dhv:permission name="oia-espandi-add">
		<input type="button" value="Inserisci Dipartimento di Prevenzione" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=Dipartimento%20di%20Prevenzione&livello=1&id_padre=-1');">  
	</dhv:permission>
	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
			<td width="100%"><a href="Oia.do">Modellatore Organizzazione ASL</a>  
			</td>
		</tr>
	</table>
	<%-- End Trails --%>
	
<%	if ( nodi_livello_1.size() > 0 ) { %>
	
	<%-- DEMO DA QUI IN POI --%>
	<table cellpadding="9" cellspacing="0" width="100%" class="details">
	
		<tr align="center" >
			<th colspan="9" style="background-color: #FFE4B9;"><strong><center>Regione Campania</center></strong></th>
		</tr>
	
		<%-- Intestazione --%>
		<tr align="center" >
			 <dhv:permission name="oia-espandi-view">
			<th></th>
			 </dhv:permission>
			<th>A.S.L.</th>
			<th>Nome</th>
			<th>C.U. Assegnati</th>
			<th>C.U. Campioni</th>
			<th>Descrizione</th>
			<th>Responsabile</th>
			<th>Ruolo Responsabile</th>
			<th></th>
		</tr>
		
<%	
				for (OiaNodo nodo_L1 : (ArrayList<OiaNodo> ) nodi_livello_1) {															
					if ( OiaNodo.have_child_oia_nodo( Integer.toString(nodo_L1.getId()) ) ) {		// GESTIONE NODO CON FIGLI (DI LIVELLO 2)				%>
						<tr align="center" id='riga_<%= nodo_L1.getId()  %>' onmouseover="inRow(this)" onmouseout="outRow(<%= nodo_L1.getId() %>,this)" > 
							 <dhv:permission name="oia-espandi-view">
							<td onclick="switch_elemento_albero('img_riga_<%= nodo_L1.getId()  %>','sotto_riga_<%= nodo_L1.getId() %>')" >
								<img id="img_riga_<%= nodo_L1.getId()  %>" src="images/tree0.gif" border=0>
							</td>
							</dhv:permission>
							
							<td><%= toHtml2(nodo_L1.getAsl_stringa()) %></td>
							<td><%= toHtml2(nodo_L1.getNome()) %></td>
							<td><%= toHtml2(nodo_L1.getN_controlli_ufficiali()) %></td>
							<td><%= toHtml2(nodo_L1.getN_cu_campioni()) %></td>
							<td><%= toHtml2(nodo_L1.getDescrizione_lunga()) %></td>
							<td><%= toHtml2(nodo_L1.getNome_responsabile()) + " " + nodo_L1.getCognome_responsabile() %></td>
							<td><%= toHtml2(nodo_L1.getRuolo_responsabile()) %></td>
							<td>
							
							 <dhv:permission name="<%=nodo_L1.getPermesso_modellatore() %>">
								<%if(nodo_L1.getTipologia_nodo().equalsIgnoreCase("sian"))
													{%>
								<input type="button" value="Inserisci struttura SIAN" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=SIAN&livello=<%=nodo_L1.getN_livello()+1 %>&id_padre=<%= nodo_L1.getId() %>');">
								<%}else
									{
									if(nodo_L1.getTipologia_nodo().equalsIgnoreCase("veterinari"))
									{
									
									%>
								<input type="button" value="Inserisci struttura Veterinaria" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=Veterinari&livello=<%=nodo_L1.getN_livello()+1 %>&id_padre=<%= nodo_L1.getId() %>');">
								<%}
									else
									{
										%>
										<input type="button" value="Inserisci struttura SIAN" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=SIAN&livello=<%=nodo_L1.getN_livello()+1 %>&id_padre=<%= nodo_L1.getId() %>');">
										<input type="button" value="Inserisci struttura Veterinaria" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=Veterinari&livello=<%=nodo_L1.getN_livello()+1 %>&id_padre=<%= nodo_L1.getId() %>');">
										<%
									}
							}%>
								</dhv:permission>
								
								<dhv:permission name="<%=nodo_L1.getPermesso_modellatore().substring(0,nodo_L1.getPermesso_modellatore().length()-4)+"edit" %>">
									<input type="button" value="Elimina" onClick="javascript:nonPossibileDelete()" />
									<input type="button" value="Modifica" onClick="javascript:popLookupSelectorModificaNodoOia('id=<%= nodo_L1.getId() %>');" />
								</dhv:permission>
								
								 <dhv:permission name="oia-oia-vigilanza-view">
									<a href ="OiaVigilanza.do?command=ViewVigilanza&orgId=<%=nodo_L1.getOrgId()%>" >Audit</a>
								</dhv:permission>
								
							</td>
						</tr>
						
						<tr align="center"  id ="sotto_riga_<%= nodo_L1.getId() %>" style="display: none">
							<td colspan="9">
								<table cellpadding="9" cellspacing="0" width="100%" style="padding-left: 50px" >
								
								<%-- Intestazione --%>
								<tr align="center"  style="background-color: #FFE4C4;">
									 <dhv:permission name="oia-espandi-view">
									<th></th>
									</dhv:permission>
									<th>A.S.L.</th>
									<th>Nome</th>
									<th>C.U. Assegnati</th>
									<th>C.U. Campioni</th>
									<th>Descrizione</th>
									<th>Responsabile</th>
									<th>Ruolo Responsabile</th>
									<th></th>
								</tr>
<%						
								ArrayList<OiaNodo> nodi_livello_2_per_id_nodo_livello_1 =  OiaNodo.load_oia_nodo_per_id_padre( Integer.toString( nodo_L1.getId() ) );
								for (OiaNodo nodo_L2 : (ArrayList<OiaNodo> ) nodi_livello_2_per_id_nodo_livello_1) {
									if ( OiaNodo.have_child_oia_nodo( Integer.toString(nodo_L2.getId()) ) ) {												%>
										<tr align="center"  id ="riga_<%= nodo_L1.getId() %>_<%= nodo_L2.getId() %>" style="background-color: #FFE4C4;" >
											
											 <dhv:permission name="oia-espandi-view">
											 <td onclick="switch_elemento_albero('img_riga_<%= nodo_L1.getId() %>_<%= nodo_L2.getId() %>','sotto_riga_<%= nodo_L1.getId() %>_<%= nodo_L2.getId() %>')" >
												<img id="img_riga_<%= nodo_L1.getId() %>_<%= nodo_L2.getId() %>" src="images/tree0.gif" border=0>
											</td>
											</dhv:permission>
											<td><%= nodo_L2.getPermesso_modellatore() %></td>
											<td><%= nodo_L2.getNome() %></td>
											<td><%= nodo_L2.getN_controlli_ufficiali() %></td>
											<td><%= nodo_L2.getN_cu_campioni() %></td>
											<td><%= nodo_L2.getDescrizione_lunga() %></td>
											<td><%= nodo_L2.getNome_responsabile() + " " + nodo_L2.getCognome_responsabile() %></td>
											<td><%= nodo_L2.getRuolo_responsabile() %></td>
											<td>
											<dhv:permission name="<%=nodo_L2.getPermesso_modellatore() %>">
												<%if(nodo_L2.getTipologia_nodo().equalsIgnoreCase("sian"))
													{%>
												<input type="button" value="Inserisci struttura SIAN" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=SIAN&livello=3&id_padre=<%= nodo_L2.getId() %>');">
												<%}
												else{
													{
														if(nodo_L2.getTipologia_nodo().equalsIgnoreCase("veterinari"))
														{
														
													
												%>
												<input type="button" value="Inserisci struttura Veterinaria" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=Veterinari&livello=3&id_padre=<%= nodo_L2.getId() %>');">
												<%}
														else
														{
															%>
																											<input type="button" value="Inserisci struttura SIAN" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=SIAN&livello=3&id_padre=<%= nodo_L2.getId() %>');">
																											<input type="button" value="Inserisci struttura Veterinaria" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=Veterinari&livello=3&id_padre=<%= nodo_L2.getId() %>');">
															
															<%
														}
														}
													}
														
														%>
												
											</dhv:permission>
											<dhv:permission name="<%=nodo_L2.getPermesso_modellatore().substring(0,nodo_L1.getPermesso_modellatore().length()-4)+"edit" %>">
												<input type="button" value="Elimina" onClick="javascript:nonPossibileDelete()" />
												<input type="button" value="Modifica" onClick="javascript:popLookupSelectorModificaNodoOia('id=<%= nodo_L2.getId() %>');" />
											</dhv:permission>
											</td>
										</tr>
										<tr align="center"  id ="sotto_riga_<%= nodo_L1.getId() %>_<%= nodo_L2.getId() %>" style="display: none">
											<td colspan="9">
												<table cellpadding="9" cellspacing="0" width="100%" style="padding-left: 100px" >
													<tr align="center"  style="background-color: #99FF00;">
														<th></th>
														<th>A.S.L.</th>
														<th>Nome</th>
														<th>C.U. Assegnati</th>
														<th>C.U. Campioni</th>
														<th>Descrizione</th>
														<th>Responsabile</th>
														<th>Ruolo Responsabile</th>
														<th></th>
													</tr>
													
<% 													ArrayList<OiaNodo> nodi_livello_3_per_id_nodo_livello_2 =  OiaNodo.load_oia_nodo_per_id_padre( Integer.toString( nodo_L2.getId() ) );
													for (OiaNodo nodo_L3 : (ArrayList<OiaNodo> ) nodi_livello_3_per_id_nodo_livello_2) {
														if(OiaNodo.have_child_oia_nodo_terzo_livello(Integer.toString(nodo_L3.getId())))
														{
														%>
														<tr align="center"  id ="riga_<%= nodo_L1.getId() %>_<%= nodo_L2.getId() %>_<%= nodo_L3.getId() %>" style="background-color: #99FF00;">
<td onclick="switch_elemento_albero('img_riga_<%= nodo_L3.getId()  %>','sotto_riga_<%= nodo_L3.getId() %>')" >
								<img id="img_riga_<%= nodo_L3.getId()  %>" src="images/tree0.gif" border=0>
							</td>															<td><%= nodo_L3.getAsl_stringa() %></td>
															<td><%= nodo_L3.getNome() %></td>
															<td><%= nodo_L3.getN_controlli_ufficiali() %></td>
															<td><%= nodo_L3.getN_cu_campioni() %></td>
															<td><%= nodo_L3.getDescrizione_lunga() %></td>
															<td><%= nodo_L3.getNome_responsabile() + " " + nodo_L3.getCognome_responsabile() %></td>
															<td><%= nodo_L3.getRuolo_responsabile() %></td>
															<td>
											<dhv:permission name="<%=nodo_L2.getPermesso_modellatore() %>">
																<input type="button" value="Elimina" onClick="javascript:confermaDelete('Oia.do?command=Cancella&id=<%= nodo_L3.getId() %>')" />
															</dhv:permission>
														<dhv:permission name="<%=nodo_L2.getPermesso_modellatore().substring(0,nodo_L1.getPermesso_modellatore().length()-4)+"edit" %>">	
														<input type="button" value="Modifica" onClick="javascript:popLookupSelectorModificaNodoOia('id=<%= nodo_L3.getId() %>');" />
														</dhv:permission>
															
															</td>
														</tr>
														<tr align="center"  id ="sotto_riga_<%= nodo_L3.getId()%>" style="display: none">
											<td colspan="9">
												<table cellpadding="9" cellspacing="0" width="100%" style="padding-left: 100px" >
													<tr align="center"  style="background-color: #99FF00;">
														<th></th>
														
														
														<th>C.U. Assegnati</th>
														<th>C.U. Campioni</th>
														<th>Responsabile</th>
														<th>Ruolo Responsabile</th>
														<th></th>
													</tr>
											
											<%

											ArrayList<OiaNodo> nodi_livello_4_per_id_nodo_livello_2 =  OiaNodo.load_oia_nodo_per_terzo_livello( Integer.toString( nodo_L3.getId() ) );
											for (OiaNodo nodo_L4 : (ArrayList<OiaNodo> ) nodi_livello_4_per_id_nodo_livello_2) {			
												
												%>
											
									
														<tr align="center"  style="background-color:yellow;">
															<td><img src="images/box.gif" border=0></td>
															<td><%= nodo_L4.getN_controlli_ufficiali() %></td>
															<td><%= nodo_L4.getN_cu_campioni() %></td>
															<td><%= nodo_L4.getNome_responsabile() + " " + nodo_L4.getCognome_responsabile() %></td>
															<td><%= nodo_L4.getRuolo_responsabile() %></td>
															<td></td>
															</tr>
															
											
									<%}
											%>
											</table>
											</td>
											</tr>
															
														
														<%}
														else
														{
															%>
															<tr align="center"  id ="riga_<%= nodo_L1.getId() %>_<%= nodo_L2.getId() %>_<%= nodo_L3.getId() %>" style="background-color: #99FF00;">
															<td><img src="images/box.gif" border=0></td>
															<td><%= nodo_L3.getAsl_stringa() %></td>
															<td><%= nodo_L3.getNome() %></td>
															<td><%= nodo_L3.getN_controlli_ufficiali() %></td>
															<td><%= nodo_L3.getN_cu_campioni() %></td>
															<td><%= nodo_L3.getDescrizione_lunga() %></td>
															<td><%= nodo_L3.getNome_responsabile() + " " + nodo_L3.getCognome_responsabile() %></td>
															<td><%= nodo_L3.getRuolo_responsabile() %></td>
															<td>
											<dhv:permission name="<%=nodo_L2.getPermesso_modellatore() %>">
																<input type="button" value="Elimina" onClick="javascript:confermaDelete('Oia.do?command=Cancella&id=<%= nodo_L3.getId() %>')" />
															</dhv:permission>
														<dhv:permission name="<%=nodo_L2.getPermesso_modellatore().substring(0,nodo_L1.getPermesso_modellatore().length()-4)+"edit" %>">	
														<input type="button" value="Modifica" onClick="javascript:popLookupSelectorModificaNodoOia('id=<%= nodo_L3.getId() %>');" />
														</dhv:permission>
															
															</td>
														</tr>
															<%
															
														}
														%>																							
<% 													}																										%>
												</table>
											</td>
										</tr>
<%									} else {																												%>
										<tr align="center"  id ="riga_<%= nodo_L1.getId() %>_<%= nodo_L2.getId() %>" style="background-color: #FFE4C4;">
											<td><img src="images/box.gif" border=0></td>
											<td><%= nodo_L2.getAsl_stringa() %></td>
											<td><%= nodo_L2.getNome() %></td>
											<td><%= nodo_L2.getN_controlli_ufficiali() %></td>
											<td><%= nodo_L2.getN_cu_campioni() %></td>
											<td><%= nodo_L2.getDescrizione_lunga() %></td>
											<td><%= nodo_L2.getNome_responsabile() + " " + nodo_L2.getCognome_responsabile() %></td>
											<td><%= nodo_L2.getRuolo_responsabile() %></td>
											<td>
											<%if(nodo_L2.getN_livello()==3)
											{
												%>
												<dhv:permission name="<%=nodo_L2.getPermesso_modellatore() %>">
																<input type="button" value="Modifica" onClick="javascript:popLookupSelectorModificaNodoOia('id=<%= nodo_L2.getId() %>');" />
																<input type="button" value="Elimina" onClick="javascript:confermaDelete('Oia.do?command=Cancella&id=<%= nodo_L2.getId() %>')" />
															</dhv:permission>
															
											
											<%}else
												{
												
												%>
												<dhv:permission name="<%=nodo_L2.getPermesso_modellatore() %>">
												<%if(nodo_L2.getTipologia_nodo().equalsIgnoreCase("sian"))
													{%>
												<input type="button" value="Inserisci struttura SIAN" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=SIAN&livello=3&id_padre=<%= nodo_L2.getId() %>');">
												<%}
												else
												{
													if(nodo_L2.getTipologia_nodo().equalsIgnoreCase("veterinari"))
													{
												%>
												
												<input type="button" value="Inserisci struttura Veterinaria" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=Veterinari&livello=3&id_padre=<%= nodo_L2.getId() %>');">
												
												<%}
													else
													{
														
														%>
														<input type="button" value="Inserisci struttura SIAN" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=SIAN&livello=3&id_padre=<%= nodo_L2.getId() %>');">
														<input type="button" value="Inserisci struttura Veterinaria" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=Veterinari&livello=3&id_padre=<%= nodo_L2.getId() %>');">
													<%
														
													}
													}
												
												%>
											
												
											</dhv:permission>
											<dhv:permission name="<%=nodo_L2.getPermesso_modellatore().substring(0,nodo_L1.getPermesso_modellatore().length()-4)+"edit" %>">	
												<input type="button" value="Elimina" onClick="javascript:confermaDelete('Oia.do?command=Cancella&id=<%= nodo_L2.getId() %>')" />
												<input type="button" value="Modifica" onClick="javascript:popLookupSelectorModificaNodoOia('id=<%= nodo_L2.getId() %>');" />
											</dhv:permission>
												<%
												}
												%>
											
															
											</td>
										</tr>
										
										
							<%
										
								}																														
								}																															%>
								</table>
							</td>
						</tr>
						
<%					} else { 																																%>
						<tr align="center" id='riga_<%= nodo_L1.getId()  %>' onmouseover="inRow(this)" onmouseout="outRow(<%= nodo_L1.getId() %>,this)" > 
							<%if(nodo_L1.getN_livello()==3 && nodo_L1.have_child_oia_nodo_terzo_livello(Integer.toString( nodo_L1.getId()))) 
							{
							%>
								<td onclick="switch_elemento_albero('img_riga_<%= nodo_L1.getId()  %>','sotto_riga_<%= nodo_L1.getId() %>')" >
								<img id="img_riga_<%= nodo_L1.getId()  %>" src="images/tree0.gif" border="0">
							</td>
							<%}
							else
							{
							%>
							<td><img src="images/box.gif" border=0></td>
							<%} %>
							<td><%= nodo_L1.getAsl_stringa() %></td>
							<td><%= nodo_L1.getNome() %></td>
							<td><%= nodo_L1.getN_controlli_ufficiali() %></td>
							<td><%= nodo_L1.getN_cu_campioni() %></td>
							<td><%= nodo_L1.getDescrizione_lunga() %></td>
							<td><%= nodo_L1.getNome_responsabile() + " " + nodo_L1.getCognome_responsabile() %></td>
							<td><%= nodo_L1.getRuolo_responsabile() %></td>
							<td>
							<%if(nodo_L1.getN_livello()==3) 
							{
							%>
							<input type="button" value="Vedi Sian E Vet" onClick="javascript:popLookupSelectorAssegnazioneCuOia('id=<%= nodo_L1.getId() %>');" />
							
							<%} %>
							
								<dhv:permission name="<%=nodo_L1.getPermesso_modellatore() %>">
								
								<%if(nodo_L1.getTipologia_nodo().equalsIgnoreCase("sian"))
								{
								
								%>
								<input type="button" value="Inserisci struttura SIAN" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=SIAN&livello=<%=nodo_L1.getN_livello()+1 %>&id_padre=<%= nodo_L1.getId() %>');">
								<%}
								else
								{
									if(nodo_L1.getTipologia_nodo().equalsIgnoreCase("veterinari"))
									{
								%>
								<input type="button" value="Inserisci struttura Veterinaria" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=Veterinari&livello=<%=nodo_L1.getN_livello()+1 %>&id_padre=<%= nodo_L1.getId() %>');">
								<%}
									else
									{
										
										%>
																		<input type="button" value="Inserisci struttura SIAN" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=SIAN&livello=<%=nodo_L1.getN_livello()+1 %>&id_padre=<%= nodo_L1.getId() %>');">
										
																		<input type="button" value="Inserisci struttura Veterinaria" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=Veterinari&livello=<%=nodo_L1.getN_livello()+1 %>&id_padre=<%= nodo_L1.getId() %>');">
										
										<%
									}
								}
								%>
								
								
							</dhv:permission>
							<dhv:permission name="<%=nodo_L1.getPermesso_modellatore().substring(0,nodo_L1.getPermesso_modellatore().length()-4)+"edit" %>">
							<input type="button" value="Modifica" onClick="javascript:popLookupSelectorModificaNodoOia('id=<%= nodo_L1.getId() %>');" />
								<input type="button" value="Elimina" onClick="javascript:confermaDelete('Oia.do?command=Cancella&id=<%= nodo_L1.getId() %>')" />
						</dhv:permission>
							
							 <dhv:permission name="oia-oia-vigilanza-view">
									<a href ="OiaVigilanza.do?command=ViewVigilanza&orgId=<%=nodo_L1.getOrgId()%>" >Audit</a>
								</dhv:permission>
							</td>
						</tr>
									<%
										if(nodo_L1.getN_livello()==3 && nodo_L1.have_child_oia_nodo_terzo_livello(Integer.toString( nodo_L1.getId())))
										{
											%>
											<tr align="center"  id ="sotto_riga_<%= nodo_L1.getId()%>" style="display: none">
											<td colspan="9">
												<table cellpadding="9" cellspacing="0" width="100%" style="padding-left: 100px" >
													<tr align="center"  style="background-color: #99FF00;">
														<th></th>
														<th>C.U. Assegnati</th>
														<th>C.U. Campioni</th>
														<th>Responsabile</th>
														<th>Ruolo Responsabile</th>
														<th></th>
													</tr>
											
											<%

											ArrayList<OiaNodo> nodi_livello_4_per_id_nodo_livello_2 =  OiaNodo.load_oia_nodo_per_terzo_livello( Integer.toString( nodo_L1.getId() ) );
											for (OiaNodo nodo_L4 : (ArrayList<OiaNodo> ) nodi_livello_4_per_id_nodo_livello_2) {			
												
												%>
											
									
														<tr align="center"  style="background-color:yellow;">
															<td><img src="images/box.gif" border=0></td>
															<td><%= nodo_L4.getN_controlli_ufficiali() %></td>
															<td><%= nodo_L4.getN_cu_campioni() %></td>
															<td><%= nodo_L4.getNome_responsabile() + " " + nodo_L4.getCognome_responsabile() %></td>
															<td><%= nodo_L4.getRuolo_responsabile() %></td>
															<td></td>
															</tr>
															
											
									<%}
											%>
											</table>
											</td>
											</tr>
											<%
										}
						
						
						
 					}
				}
%>
	</table>	
	
<%	} else {	%>
	<label style="padding-left: 30px" >Non sono presenti elementi.</label>
	
<%  }			%>
	
	
	