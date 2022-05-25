<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page import="java.util.*,java.text.DateFormat"%>
<%@page import="org.aspcfs.modules.dpat2019.base.oia.OiaNodo"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatStruttura"%>
<%@page import="com.darkhorseventures.framework.actions.ActionContext"%>
<jsp:useBean id="lookup_comuni" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<%@page import="org.aspcfs.modules.oia.base.ResponsabileNodo"%>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="nodi_livello" class="java.util.ArrayList" scope="request"/>
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

<style>
.pulsante {
 outline: none;
 cursor: pointer;
 text-align: center;
 text-decoration: none;
 font: bold 12px Arial, Helvetica, sans-serif;
 color: black;
 padding: 0px 0px;
 border: solid 1px #0076a3;
 background: blue;
 background: -webkit-gradient(linear, left top, left bottom, from(#00adee), to(#BDCFFF));
 background: -webkit-linear-gradient(top,  #BDCFFF,  #BDCFFF);
 background: -moz-linear-gradient(top,  #BDCFFF,  #BDCFFF);
 background: -ms-linear-gradient(top,  #BDCFFF,  #BDCFFF);
 background: -o-linear-gradient(top,  #BDCFFF,  #BDCFFF);
 background: linear-gradient(top,  #BDCFFF,  #BDCFFF);
 -moz-border-radius: 8px;
 -webkit-border-radius: 8px;
 border-radius: 8px;
 -moz-box-shadow: 0 1px 3px rgba(0,0,0,0.5);
 -webkit-box-shadow: 0 1px 3px rgba(0,0,0,0.5);
 box-shadow: 0 1px 3px rgba(0,0,0,0.5);
}
.pulsante:hover {
 background: #0095cd;
 background: -webkit-gradient(linear, left top, left bottom, from(#0078a5), to(#00adee));
 background: -webkit-linear-gradient(top,  #0078a5,  #00adee);
 background: -moz-linear-gradient(top,  #0078a5,  #00adee);
 background: -ms-linear-gradient(top,  #0078a5,  #00adee);
 background: -o-linear-gradient(top,  #0078a5,  #00adee);
 background: linear-gradient(top,  #0078a5,  #00adee);
}
</style>
<%@ include file="../initPage.jsp"%>

	
	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
			<td width="100%"><a href="Oia.do">Modellatore Organizzazione ASL</a>  
			</td>
		</tr>
	</table>
	<%-- End Trails --%>
	<br><br>
	<%--dhv:permission name="oia-espandi-add">
		<input class= "pulsante" type="button" value="Inserisci Dipartimento di Prevenzione" onClick="javascript:popLookupSelectorNuovoNodoDpat('tipologia=1&livello=1&id_padre=-1');">  
	</dhv:permission--%>
	<br><br>
<%	

if ( nodi_livello.size() > 0 ) { %>
	
	<%-- DEMO DA QUI IN POI --%>
	
		
<%	
				for (DpatStruttura nodo_L1 : (ArrayList<DpatStruttura> ) nodi_livello) {															
					if ( nodo_L1.getLista_nodiDpat().size()>0 ) {		// GESTIONE NODO CON FIGLI (DI LIVELLO 2)				%>
						
						<table cellpadding="9" cellspacing="0" width="100%" class="details" style="display:none" name="tab" id="tab_<%=nodo_L1.getId_asl()%>">
	
		
	<%-- Intestazione --%>
		<tr align="center" style="background-color: blue" >
			<th colspan="6"><center><%=nodo_L1.getAsl_stringa() %></center></th>
		
		</tr>
		<%-- Intestazione --%>
		<tr align="center" width="">
			 <dhv:permission name="oia-espandi-view">
			<th></th>
			 </dhv:permission>
			<th width="30%">Struttura</th>
			<th width="30%">Responsabile/Direttore</th>
			<th width="20%">Indirizzo</th>
			<th width="20%"></th>
		</tr>
						
						
						<tr align="center" id='riga_<%= nodo_L1.getId()  %>' onmouseover="inRow(this)" onmouseout="outRow(<%= nodo_L1.getId() %>,this)" st> 
							 <dhv:permission name="oia-espandi-view">
							<td rowspan="3" onclick="switch_elemento_albero('img_riga_<%= nodo_L1.getId()  %>','sotto_riga_<%= nodo_L1.getId() %>')" >
								<img id="img_riga_<%= nodo_L1.getId()  %>" src="images/tree0.gif" border=0>
							</td>
							</dhv:permission>
							
							<td rowspan="3"><%= nodo_L1.getDescrizione_lunga() %></td>
							<td rowspan="3"><%
									for(ResponsabileNodo resp : nodo_L1.getListaResponsabili())
									{
										
										out.println(resp.getNome_responsabile()+" "+resp.getCognome_responsabile()+"<br>");
									}
							%></td>
							<td rowspan="3"><%=lookup_comuni.getSelectedValue(nodo_L1.getComune())+"<br>"+nodo_L1.getIndirizzo() +"<br>"+nodo_L1.getMail() %></td>
							<td>
							
							<%
							if(nodo_L1.getN_livello()==1)
							{
							%>
							 <dhv:permission name="oia-modellazione_level1-view">
							 
							  <dhv:permission name="oia-modellazione_level1-edit">
								<input class= "pulsante" type="button" value="Modifica" style="width:200px;" onClick="javascript:popLookupSelectorModificaNodoDpat('id=<%= nodo_L1.getId() %>');" />
								</dhv:permission><br><br>
								 <dhv:permission name="oia-modellazione_level1-delete">
								<input class= "pulsante" type="button" value="Elimina" style="width:200px;" onClick="javascript:nonPossibileDelete()" />
								 </dhv:permission>
								 
								</dhv:permission><br><br>
								<input class= "pulsante" type="button" value="dettaglio dpat" style="width:200px;" onClick="window.location='dpat2019.do?command=DpatDetailGenerale&idAsl=<%=nodo_L1.getId_asl()%>&idPadre=<%=nodo_L1.getId()%>&anno=corrente'" />
								
								<%} 
								
								if(nodo_L1.getN_livello()==2)
							{
							%>
							<dhv:permission name="oia-modellazione_level2-view">
											
												<dhv:permission name="oia-modellazione_level2-edit">
												<input class= "pulsante" type="button" value="Modifica" style="width: 200px"  onClick="javascript:popLookupSelectorModificaNodoDpat('id=<%= nodo_L1.getId() %>');" />
												</dhv:permission>
												<br><br>
												<dhv:permission name="oia-modellazione_level2-delete">
												<input class= "pulsante" type="button" value="Elimina" style="width: 200px"  onClick="javascript:nonPossibileDelete()" />
												</dhv:permission>
												
											</dhv:permission>
											
							<%} 	if(nodo_L1.getN_livello()==3)
							{
							%>
							<dhv:permission name="oia-modellazione_level3-view">
											<dhv:permission name="oia-modellazione_level3-edit">
															<input class= "pulsante" type="button" value="Modifica" style="width: 200px;" onClick="javascript:popLookupSelectorModificaNodoDpat('id=<%= nodo_L1.getId() %>');" />
														</dhv:permission><br><br>	
																<dhv:permission name="oia-modellazione_level3-delete">
																<input class= "pulsante" type="button" value="Elimina" style="width: 200px;" onClick="javascript:confermaDelete('dpat2019.do?command=Cancella&id=<%= nodo_L1.getId() %>')" />
																</dhv:permission>
															</dhv:permission>
							<%} %>
							
							
								
								
								
							</td>
						</tr>
						
						<tr align="center" id='riga_<%= nodo_L1.getId()  %>' onmouseover="inRow(this)" onmouseout="outRow(<%= nodo_L1.getId() %>,this)" st> 
							
							<td>
							
							<%
							if(nodo_L1.getN_livello()==1)
							{
							%>
							 <dhv:permission name="oia-modellazione_level1-view">
							 	 
							 <dhv:permission name="oia-modellazione_level1-add">
								<input class= "pulsante" type="button" value="AGGIUNGI STRUTTURA" style="width:200px;" onClick="javascript:popLookupSelectorNuovoNodoDpat('tipologia=3&livello=2&id_padre=<%= nodo_L1.getId() %>');">
								</dhv:permission>
								
								
								
								
								</dhv:permission>
								<%} 
								
								if(nodo_L1.getN_livello()==2)
							{
							%>
							<dhv:permission name="oia-modellazione_level2-view">
											
												<dhv:permission name="oia-modellazione_level2-edit">
												<tr><td><input class= "pulsante" type="button" value="Modifica" style="width: 200px"  onClick="javascript:popLookupSelectorModificaNodoDpat('id=<%= nodo_L1.getId() %>');" /></td></tr>
												</dhv:permission><br><br>
												<dhv:permission name="oia-modellazione_level2-delete">
												<input class= "pulsante" type="button" value="Elimina" style="width: 200px"  onClick="javascript:nonPossibileDelete()" />
												</dhv:permission>
											</dhv:permission>
							<%} 	if(nodo_L1.getN_livello()==3)
							{
							%>
							<dhv:permission name="oia-modellazione_level3-view">
											<dhv:permission name="oia-modellazione_level3-edit">
																<tr><td><input class= "pulsante" type="button" value="Modifica" style="width: 200px;" onClick="javascript:popLookupSelectorModificaNodoDpat('id=<%= nodo_L1.getId() %>');" /></td></tr>
														</dhv:permission>	
														<br><br>	
																<dhv:permission name="oia-modellazione_level3-delete">
																<input class= "pulsante" type="button" value="Elimina" style="width: 200px;" onClick="javascript:confermaDelete('dpat2019.do?command=Cancella&id=<%= nodo_L1.getId() %>')" />
																</dhv:permission>
															</dhv:permission>
							<%} %>
						
								
								
								
							</td>
						</tr>
						
						<tr align="center"> 
							
							<td>
							
							<%
							if(nodo_L1.getN_livello()==1)
							{
							%>
							
								<%} 
								
								if(nodo_L1.getN_livello()==2)
							{
							%>
							<dhv:permission name="oia-modellazione_level2-view">
											<dhv:permission name="oia-modellazione_level2-add">
												<input  class= "pulsante" type="button" value="Aggiungi Unita Operativa" style="width: 200px" onClick="javascript:popLookupSelectorNuovoNodoDpat('tipologia=<%=(nodo_L1.getTipologia_struttura()==3)? "4" :(nodo_L1.getTipologia_struttura()==2)? "5" : "-1" %>&livello=3&id_padre=<%= nodo_L1.getId() %>');">
												</dhv:permission>
											
											</dhv:permission>
							<%} 	if(nodo_L1.getN_livello()==3)
							{
							%>
							<dhv:permission name="oia-modellazione_level3-view">
											<dhv:permission name="oia-modellazione_level3-edit">
																<input class= "pulsante" type="button" value="Modifica" style="width: 200px;" onClick="javascript:popLookupSelectorModificaNodoDpat('id=<%= nodo_L1.getId() %>');" />
														</dhv:permission>
														<br><br>		
																<dhv:permission name="oia-modellazione_level3-delete">
																<input class= "pulsante" type="button" value="Elimina" style="width: 200px;" onClick="javascript:confermaDelete('dpat2019.do?command=Cancella&id=<%= nodo_L1.getId() %>')" />
																</dhv:permission>
															</dhv:permission>
							<%} %>
						
							
								
								
								
							</td>
						</tr>
						
						<!--figli  -->
						<tr align="center"  id ="sotto_riga_<%= nodo_L1.getId() %>" style="display: none">
							<td colspan="7">
								<table cellpadding="9" cellspacing="0" width="100%" style="padding-left: 50px" >
								
								<%-- Intestazione --%>
								<tr align="center"  style="background-color: #FFE4C4;">
									 <dhv:permission name="oia-espandi-view">
									<th></th>
									</dhv:permission>
									<th>Tipo Struttura</th>
			
			<th>Responsabile/Direttore</th>
			<th>Indirizzo</th>
			
									<th></th>
								</tr>
<%						
								ArrayList<DpatStruttura> nodi_livello_2_per_id_nodo_livello_1 =  nodo_L1.getLista_nodiDpat();
								for (DpatStruttura nodo_L2 : (ArrayList<DpatStruttura> ) nodi_livello_2_per_id_nodo_livello_1) {
									if ( nodo_L2.getLista_nodiDpat().size()>0 ) {												%>
										<tr  align="center"  id ="riga_<%= nodo_L1.getId() %>_<%= nodo_L2.getId() %>" style="background-color: #FFCB98;" >
											
											 <dhv:permission name="oia-espandi-view">
											 <td rowspan="2" onclick="switch_elemento_albero('img_riga_<%= nodo_L1.getId() %>_<%= nodo_L2.getId() %>','sotto_riga_<%= nodo_L1.getId() %>_<%= nodo_L2.getId() %>')" >
												<img id="img_riga_<%= nodo_L1.getId() %>_<%= nodo_L2.getId() %>" src="images/tree0.gif" border=0>
											</td>
											</dhv:permission>
											<td rowspan="2"><%= nodo_L2.getDescrizione_lunga() %></td>
											<td rowspan="2"><%
									for(ResponsabileNodo resp : nodo_L2.getListaResponsabili())
									{
										
										out.println(resp.getNome_responsabile()+" "+resp.getCognome_responsabile()+"<br>");
									}
							%></td>
							<td rowspan="2"><%=lookup_comuni.getSelectedValue(nodo_L2.getComune())+"<br>"+nodo_L2.getIndirizzo() +"<br>"+nodo_L2.getMail() %></td>
											<td>
											
											<%
							if(nodo_L2.getN_livello()==2)
							{
							%>
											<dhv:permission name="oia-modellazione_level2-view">
											
												<dhv:permission name="oia-modellazione_level2-edit">
												<input class= "pulsante" type="button" value="Modifica" style="width: 200px"  onClick="javascript:popLookupSelectorModificaNodoDpat('id=<%= nodo_L2.getId() %>');" />
												</dhv:permission><br><br>
												<dhv:permission name="oia-modellazione_level2-delete">
												<input class= "pulsante" type="button" value="Elimina" style="width: 200px"  onClick="javascript:nonPossibileDelete()" />
												</dhv:permission>
											</dhv:permission>
											
											<%}
							else
							{
											%>
											
											<dhv:permission name="oia-modellazione_level3-view">
											<dhv:permission name="oia-modellazione_level3-edit">
																<input class= "pulsante" type="button" value="Modifica" style="width: 200px;" onClick="javascript:popLookupSelectorModificaNodoDpat('id=<%= nodo_L2.getId() %>');" />
														</dhv:permission><br><br>		
																<dhv:permission name="oia-modellazione_level3-delete">
																<input class= "pulsante" type="button" value="Elimina" style="width: 200px;" onClick="javascript:confermaDelete('dpat2019.do?command=Cancella&id=<%= nodo_L2.getId() %>')" />
																</dhv:permission>
															</dhv:permission>
															
							<%} %>				
											
											</td>
										</tr>
										
										
										<tr align="center"  id ="riga_<%= nodo_L1.getId() %>_<%= nodo_L2.getId() %>" style="background-color: #FFCB98;" >
											
									
											<td>
										
											<%
							if(nodo_L2.getN_livello()==2)
							{
							%>
											<dhv:permission name="oia-modellazione_level2-view">
											<dhv:permission name="oia-modellazione_level2-add">
												<input  class= "pulsante" type="button" value="Aggiungi Struttura Semplice" style="width: 200px" onClick="javascript:popLookupSelectorNuovoNodoDpat('tipologia=4&livello=3&id_padre=<%= nodo_L2.getId() %>');">
												</dhv:permission>
												
											</dhv:permission><br><br>
											<input class="pulsante" type="button"
						value="Modifica DPAT"  style="width: 200px" onClick="window.location='dpat2019.do?command=AddDatiPerStruttura&idAsl=<%=nodo_L2.getId_asl()%>&idStrutt=<%=nodo_L2.getId()%>'" />
											<%}
						
											%>
											
										
										
											</td>
										</tr>
										
																				
										
										<!--  -->
										<tr align="center"  id ="sotto_riga_<%= nodo_L1.getId() %>_<%= nodo_L2.getId() %>" style="display: none">
											<td colspan="7">
												<table cellpadding="9" cellspacing="0" width="100%" style="padding-left: 100px" >
													<tr align="center"  style="background-color: #99FF00;">
														<th></th>
														<th>Tipo Struttura</th>
			
			<th>Responsabile/Direttore</th>
			<th>Indirizzo</th>
			
														<th></th>
													</tr>
													
<% 													ArrayList<DpatStruttura> nodi_livello_3_per_id_nodo_livello_2 =  nodo_L2.getLista_nodiDpat();
													for (DpatStruttura nodo_L3 : (ArrayList<DpatStruttura> ) nodi_livello_3_per_id_nodo_livello_2) {					%>
														<tr align="center"  id ="riga_<%= nodo_L1.getId() %>_<%= nodo_L2.getId() %>_<%= nodo_L3.getId() %>" style="background-color: #FFE5CC">
															<td rowspan="1"><img src="images/box.gif" border=0></td>
															<td rowspan="1"><%= nodo_L3.getDescrizione_lunga() %></td>
															<td rowspan="1"><%
									for(ResponsabileNodo resp : nodo_L3.getListaResponsabili())
									{
										
										out.println(resp.getNome_responsabile()+" "+resp.getCognome_responsabile()+"<br>");
									}
							%></td>
							<td rowspan="1"><%=lookup_comuni.getSelectedValue(nodo_L3.getComune())+"<br>"+nodo_L3.getIndirizzo() +"<br>"+nodo_L3.getMail() %></td>
															<td>
															<table>
															
															<dhv:permission name="oia-modellazione_level3-view">
																<tr><td><input class= "pulsante" type="button" value="Modifica" style="width: 200px;" onClick="javascript:popLookupSelectorModificaNodoDpat('id=<%= nodo_L3.getId() %>');" /></td></tr>
																<tr><td><input class= "pulsante" type="button" value="Elimina" style="width: 200px;" onClick="javascript:confermaDelete('dpat2019.do?command=Cancella&id=<%= nodo_L3.getId() %>')" /></td></tr>
																<tr><td><input class="pulsante" type="button"  value="Modifica DPAT"  style="width: 200px" onClick="window.location='dpat2019.do?command=AddDatiPerStruttura&idAsl=<%=nodo_L3.getId_asl()%>&idStrutt=<%=nodo_L3.getId()%>'" /></td></tr>
															</dhv:permission>
															</table>
															</td>
														</tr>																								
<% 													}																										%>
												</table>
											</td>
										</tr>
<%									} else {																												%>
										<tr align="center"  id ="riga_<%= nodo_L1.getId() %>_<%= nodo_L2.getId() %>" style="background-color: #FFCB98;">
											<td rowspan="2"><img src="images/box.gif" border=0></td>
											<td rowspan="2"><%= nodo_L2.getDescrizione_lunga() %></td>
											<td rowspan="2"><%
									for(ResponsabileNodo resp : nodo_L2.getListaResponsabili())
									{
										
										out.println(resp.getNome_responsabile()+" "+resp.getCognome_responsabile()+"<br>");
									}
							%></td>
											
											<td rowspan="2"><%=lookup_comuni.getSelectedValue(nodo_L2.getComune())+"<br>"+nodo_L2.getIndirizzo() +"<br>"+nodo_L2.getMail() %></td>
											<td>
											
											<%if (nodo_L2.getN_livello()==2){ %> 	
											<dhv:permission name="oia-modellazione_level2-view">
											
												<dhv:permission name="oia-modellazione_level2-edit">
												<input class= "pulsante" type="button" value="Modifica" style="width: 200px"  onClick="javascript:popLookupSelectorModificaNodoDpat('id=<%= nodo_L2.getId() %>');" />
												</dhv:permission><br><br>
												<dhv:permission name="oia-modellazione_level2-delete">
												<input class= "pulsante" type="button" value="Elimina" style="width: 200px"  onClick="javascript:nonPossibileDelete()" />
												</dhv:permission>
												
											</dhv:permission>
											
											<%}else { %>
											<dhv:permission name="oia-modellazione_level3-view">
											<dhv:permission name="oia-modellazione_level3-edit">
																<input class= "pulsante" type="button" value="Modifica" style="width: 200px;" onClick="javascript:popLookupSelectorModificaNodoDpat('id=<%= nodo_L2.getId() %>');" />
														</dhv:permission>
														<br><br>		
																<dhv:permission name="oia-modellazione_level3-delete">
																<input class= "pulsante" type="button" value="Elimina" style="width: 200px;" onClick="javascript:confermaDelete('dpat2019.do?command=Cancella&id=<%= nodo_L2.getId() %>')" />
																</dhv:permission>
															</dhv:permission>
											<%} %>
											
											</td>
										</tr>
										
										
										<tr align="center"  id ="riga_<%= nodo_L1.getId() %>_<%= nodo_L2.getId() %>" style="background-color: #FFCB98;">
											<td>
											
											
											<%if (nodo_L2.getN_livello()==2){ %> 	
											<dhv:permission name="oia-modellazione_level2-view">
											<dhv:permission name="oia-modellazione_level2-add">
												<input  class= "pulsante" type="button" value="AGGIUNGI STRUTTURA SEMPLICE" style="width: 200px" onClick="javascript:popLookupSelectorNuovoNodoDpat('tipologia=4&livello=3&id_padre=<%= nodo_L2.getId() %>');">
												</dhv:permission>
												
											</dhv:permission><br><br>
											<input class="pulsante" type="button"
						value="Modifica DPAT"  style="width: 200px" onClick="window.location='dpat2019.do?command=AddDatiPerStruttura&idAsl=<%=nodo_L2.getId_asl()%>&idStrutt=<%=nodo_L2.getId()%>'" />
											<%} %>
											
											
											</td>
										</tr>
										
<%									}																														
								}																															%>
								</table>
							</td>
						</tr>
						</table>
						<br><br>
<%					} else {
	
	%>
	<table cellpadding="9" cellspacing="0" width="100%" class="details">
				<tr align="center" syle="background-color: blue" >
			<th colspan="6"><center><%=nodo_L1.getAsl_stringa() %></center></th>
		
		</tr>
		<%-- Intestazione --%>
		<tr align="center" >
			 <dhv:permission name="oia-espandi-view">
			<th></th>
			 </dhv:permission>
			<th>Tipo Struttura</th>
			
			<th>Responsabile/Direttore</th>
			<th>Indirizzo</th>
			
			
			<th></th>
		</tr>
						<tr align="center" id='riga_<%= nodo_L1.getId()  %>' onmouseover="inRow(this)" onmouseout="outRow(<%= nodo_L1.getId() %>,this)" > 
							<td rowspan="3"><img src="images/box.gif" border=0></td>

							<td rowspan="3"><%= nodo_L1.getDescrizione_lunga() %></td>
							<td rowspan="3"><%
									for(ResponsabileNodo resp : nodo_L1.getListaResponsabili())
									{
										
										out.println(resp.getNome_responsabile()+" "+resp.getCognome_responsabile()+"<br>");
									}
							%></td>
							 
							 <td rowspan="3"><%=lookup_comuni.getSelectedValue(nodo_L1.getComune())+"<br>"+nodo_L1.getIndirizzo() +"<br>"+nodo_L1.getMail() %></td>
							 <td>
							 
								<%if (nodo_L1.getN_livello()==1){ %> 	
							 <dhv:permission name="oia-modellazione_level1-view">
							
								 <dhv:permission name="oia-modellazione_level1-edit">
								<input class= "pulsante" type="button" value="Modifica" style="width:200px;" onClick="javascript:popLookupSelectorModificaNodoDpat('id=<%= nodo_L1.getId() %>');" />
								</dhv:permission>
								<br><br>
								 <dhv:permission name="oia-modellazione_level1-delete">
									<input class= "pulsante" type="button" value="Elimina" style="width:200px;" onClick="javascript:nonPossibileDelete()" />
								 </dhv:permission>
								
								</dhv:permission>
								<%}else %>
								<%if (nodo_L1.getN_livello()==2){ %> 	
											<dhv:permission name="oia-modellazione_level2-view">
											<dhv:permission name="oia-modellazione_level2-add">
												<tr><td><input  class= "pulsante" type="button" value="AGGIUNGI STRUTTURA SEMPLICE" style="width: 200px" onClick="javascript:popLookupSelectorNuovoNodoDpat('tipologia=4&livello=3&id_padre=<%= nodo_L1.getId() %>');"></td></tr>
												</dhv:permission>
												<dhv:permission name="oia-modellazione_level2-edit">
												<tr><td><input class= "pulsante" type="button" value="Modifica" style="width: 200px"  onClick="javascript:popLookupSelectorModificaNodoDpat('id=<%= nodo_L1.getId() %>');" /></td></tr>
												</dhv:permission>
												<dhv:permission name="oia-modellazione_level2-delete">
												<tr><td><input class= "pulsante" type="button" value="Elimina" style="width: 200px"  onClick="javascript:nonPossibileDelete()" /></td></tr>
												</dhv:permission>
											</dhv:permission>
											<%}else {
												if (nodo_L1.getN_livello()==3){ 
												%>
											<dhv:permission name="oia-modellazione_level3-view">
											<dhv:permission name="oia-modellazione_level3-edit">
																<tr><td><input class= "pulsante" type="button" value="Modifica" style="width: 200px;" onClick="javascript:popLookupSelectorModificaNodoDpat('id=<%= nodo_L1.getId() %>');" /></td></tr>
														</dhv:permission>		
																<dhv:permission name="oia-modellazione_level3-delete">
																<tr><td><input class= "pulsante" type="button" value="Elimina" style="width: 200px;" onClick="javascript:confermaDelete('dpat2019.do?command=Cancella&id=<%= nodo_L1.getId() %>')" /></td></tr>
																</dhv:permission>
															</dhv:permission>
											<%}} %>
							
							</td>
						</tr>
						<tr align="center"  > 
							
							 <td onmouseover="inRow(this)" onmouseout="outRow(<%= nodo_L1.getId() %>,this)" >
								<%if (nodo_L1.getN_livello()==1){ %> 	
							 <dhv:permission name="oia-modellazione_level1-view">
							 <dhv:permission name="oia-modellazione_level1-add">
								<input class= "pulsante" type="button" value="AGGIUNGI STRUTTURA" style="width:200px;" onClick="javascript:popLookupSelectorNuovoNodoDpat('tipologia=3&livello=2&id_padre=<%= nodo_L1.getId() %>');">
								</dhv:permission>
									 
								</dhv:permission>
								<%}else %>
								<%if (nodo_L1.getN_livello()==2){ %> 	
											<dhv:permission name="oia-modellazione_level2-view">
											<dhv:permission name="oia-modellazione_level2-edit">
												<input class= "pulsante" type="button" value="Modifica" style="width: 200px"  onClick="javascript:popLookupSelectorModificaNodoDpat('id=<%= nodo_L1.getId() %>');" />
												</dhv:permission>
												<br><br>
												<dhv:permission name="oia-modellazione_level2-delete">
												<input class= "pulsante" type="button" value="Elimina" style="width: 200px"  onClick="javascript:nonPossibileDelete()" />
												</dhv:permission>
											</dhv:permission>
											<%}else {
												if (nodo_L1.getN_livello()==3){ 
												%>
											<dhv:permission name="oia-modellazione_level3-view">
											<dhv:permission name="oia-modellazione_level3-edit">
																<tr><td><input class= "pulsante" type="button" value="Modifica" style="width: 200px;" onClick="javascript:popLookupSelectorModificaNodoDpat('id=<%= nodo_L1.getId() %>');" /></td></tr>
														</dhv:permission>		
																<dhv:permission name="oia-modellazione_level3-delete">
																<input class= "pulsante" type="button" value="Elimina" style="width: 200px;" onClick="javascript:confermaDelete('dpat2019.do?command=Cancella&id=<%= nodo_L1.getId() %>')" />
																</dhv:permission>
															</dhv:permission>
											<%}} %>
							
							</td>
						</tr>
						
						<tr align="center"> 
							
							 <td onmouseover="inRow(this)" onmouseout="outRow(<%= nodo_L1.getId() %>,this)" >
								<%if (nodo_L1.getN_livello()==1){ %> 	
							
								<%}else %>
								<%if (nodo_L1.getN_livello()==2){ %> 	
											<dhv:permission name="oia-modellazione_level2-view">
											<dhv:permission name="oia-modellazione_level2-add">
												<input  class= "pulsante" type="button" value="AGGIUNGI STRUTTURA SEMPLICE" style="width: 200px" onClick="javascript:popLookupSelectorNuovoNodoDpat('tipologia=4&livello=3&id_padre=<%= nodo_L1.getId() %>');">
												</dhv:permission>
											
											</dhv:permission>
											<%}else {
												if (nodo_L1.getN_livello()==3){ 
												%>
											<dhv:permission name="oia-modellazione_level3-view">
											<dhv:permission name="oia-modellazione_level3-edit">
																<tr><td><input class= "pulsante" type="button" value="Modifica" style="width: 200px;" onClick="javascript:popLookupSelectorModificaNodoDpat('id=<%= nodo_L1.getId() %>');" /></td></tr>
														</dhv:permission>		
																<dhv:permission name="oia-modellazione_level3-delete">
																<input class= "pulsante" type="button" value="Elimina" style="width: 200px;" onClick="javascript:confermaDelete('dpat2019.do?command=Cancella&id=<%= nodo_L1.getId() %>')" />
																</dhv:permission>
															</dhv:permission><br><br>
															<input class="pulsante" type="button"
						value="Modifica DPAT"  style="width: 200px" onClick="window.location='dpat2019.do?command=AddDatiPerStruttura&idAsl=<%=nodo_L1.getId_asl()%>&idStrutt=<%=nodo_L1.getId()%>'" />
											<%}} %>
							
							</td>
						</tr>
						
						</table>
						<br><br>
<% 					}
				}
				
%>
<script>
	var idAsl = '<%=User.getUserRecord().getSiteId()%>';
	if(idAsl!=-1){ //VISUALIZZA SOLO ASL DELL'UTENTE
		document.getElementById('tab_<%=User.getUserRecord().getSiteId()%>').style.display='block';
	}
	else{  //SE REGIONE VISUALIZZA TUTTE LE ASL
		var t = document.getElementsByName('tab');
		for(var i = 0; i < t.length; i++) {
			t[i].style.display='block';
		}
	}
</script>
		
	
<%	} else {	%>
	<label style="padding-left: 30px" >Non sono presenti elementi.</label>
	
<%  }			%>

	