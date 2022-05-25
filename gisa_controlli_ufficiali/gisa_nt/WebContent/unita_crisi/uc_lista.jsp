<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page import="java.util.*,
				 java.text.DateFormat"%>


<%@page import="org.aspcfs.modules.oia.base.OiaNodo"%>
<%@page import="com.darkhorseventures.framework.actions.ActionContext"%>
<%@page import="org.aspcfs.modules.unitacrisi.base.UnitaCrisiBean"%>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="lookup_ambito_unita_di_crisi" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lista_unita" class="java.util.ArrayList" scope="request"/>

	<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
	<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" >

		function confermaDelete(url) {
			  if ( confirm('ATTENZIONE! Sicuro di voler proseguire con l\'eliminazione dell\'unita\' di crisi?') ) {
			    window.location = url;
			  }
		}

		function inRow(riga)
		{
			riga.style.background='#EDEDED';
		}
		
		function outRow(riga)
		{
				riga.style.background='#FFFFFF';
		}
	</SCRIPT>


<%@ include file="../initPage.jsp"%>

<dhv:permission name="unitacrisi-view">
	<body>
		
		<%-- Trails --%>
		<table class="trails" cellspacing="0">
			<tr>
				<td width="100%"><a href="UnitaCrisi.do">Unità di Crisi - Regione Campania</a>  
				</td>
			</tr>
		</table>
		<%-- End Trails --%>
		
	<%	if ( lista_unita.size() > 0 ) { 																															%>
		
		<table cellpadding="8" cellspacing="0" width="100%" class="details">
		
			<tr align="center" >
				<th colspan="8" style="background-color: #FFE4B9;"><strong><center>Unità di Crisi</center></strong></th>
			</tr>
		
			<%-- Intestazione --%>
			<tr align="center" >
				<th>Ambito di Competenza</th>
				<th>Responsabile</th>
				<th>E-mail</th>
				<th>Telefono</th>
				<th>Fax</th>
				<th>Operazione</th>
			</tr>
			
	<%		int i_1 = 0;
			int i_2 = 0;
			int i_3 = 0;
			int i_4 = 0;
			int i_5 = 0;
			int i_6 = 0;
			int i_7 = 0;
			int i_8 = 0;
			int i_9 = 0;
			int i_10 = 0;
			int i_11 = 0;
			int i_12 = 0;
			int i_13 = 0;
			int i_14 = 0;
			int i_15 = 0;
			int i_16 = 0;
			int i_17 = 0;

	
			for (UnitaCrisiBean nodo : (ArrayList<UnitaCrisiBean> ) lista_unita) {	
					if (nodo.getId_ambito()==1 && i_1 == 0) {							i_1++;																%>	
						<td><b><%= nodo.getAmbito_stringa() %></b></td><td></td><td></td><td></td><td></td>
						<td>
						
							<dhv:permission name="unitacrisi-1-add">	<input type="button" value="Inserisci nuovo referente" onClick="javascript:popLookupSelectorNuovoUnitaCrisi('id_ambito=<%= nodo.getId_ambito() %>');">  </dhv:permission>
						
						</td>
<%					}
					
					
					if (nodo.getId_ambito()==2 && i_2 == 0) {							i_2++;																%>	
					<td><b><%= nodo.getAmbito_stringa() %></b></td><td></td><td></td><td></td><td></td>
					<td>
						<dhv:permission name="unitacrisi-2-add">	<input type="button" value="Inserisci nuovo referente" onClick="javascript:popLookupSelectorNuovoUnitaCrisi('id_ambito=<%= nodo.getId_ambito() %>');">  </dhv:permission>
					</td>
<%					}
					
					if (nodo.getId_ambito()==3 && i_3 == 0) {							i_3++;																%>	
					<td><b><%= nodo.getAmbito_stringa() %></b></td><td></td><td></td><td></td><td></td>
					<td>
						<dhv:permission name="unitacrisi-3-add">	<input type="button" value="Inserisci nuovo referente" onClick="javascript:popLookupSelectorNuovoUnitaCrisi('id_ambito=<%= nodo.getId_ambito() %>');">  </dhv:permission>
					</td>
<%					}
					
					if (nodo.getId_ambito()==4 && i_4 == 0) {							i_4++;																%>	
					
					<td><b><%= nodo.getAmbito_stringa() %></b></td><td></td><td></td><td></td><td></td>
					<td>
					<dhv:evaluate if="<%= User.getSiteId()<=0  || User.getSiteId()==nodo.getIdAsl() %>">
						<dhv:permission name="unitacrisi-4-add">	<input type="button" value="Inserisci nuovo referente" onClick="javascript:popLookupSelectorNuovoUnitaCrisi('id_ambito=<%= nodo.getId_ambito() %>');">  </dhv:permission>
					</dhv:evaluate>
					</td>
<%					}
					
					if (nodo.getId_ambito()==5 && i_5 == 0) {							i_5++;																%>	
					
					<td><b><%= nodo.getAmbito_stringa() %></b></td><td></td><td></td><td></td><td></td>
					<td>
					<dhv:evaluate if="<%= User.getSiteId()<=0 || User.getSiteId()==nodo.getIdAsl() %>">
						<dhv:permission name="unitacrisi-5-add">	<input type="button" value="Inserisci nuovo referente" onClick="javascript:popLookupSelectorNuovoUnitaCrisi('id_ambito=<%= nodo.getId_ambito() %>');">  </dhv:permission>
					</dhv:evaluate>
					</td>
<%					}
					
					if (nodo.getId_ambito()==6 && i_6 == 0) {							i_6++;																%>	
					
					<td><b><%= nodo.getAmbito_stringa() %></b></td><td></td><td></td><td></td><td></td>
					<td>
					<dhv:evaluate if="<%= User.getSiteId()<=0 || User.getSiteId()==nodo.getIdAsl() %>">
						<dhv:permission name="unitacrisi-6-add">	<input type="button" value="Inserisci nuovo referente" onClick="javascript:popLookupSelectorNuovoUnitaCrisi('id_ambito=<%= nodo.getId_ambito() %>');">  </dhv:permission>
					</dhv:evaluate>
					</td>
<%					}
					
					if (nodo.getId_ambito()==7 && i_7 == 0) {							i_7++;																%>	
					
					<td><b><%= nodo.getAmbito_stringa() %></b></td><td></td><td></td><td></td><td></td>
					<td>
					<dhv:evaluate if="<%= User.getSiteId()<=0 || User.getSiteId()==nodo.getIdAsl() %>">
						<dhv:permission name="unitacrisi-7-add">	<input type="button" value="Inserisci nuovo referente" onClick="javascript:popLookupSelectorNuovoUnitaCrisi('id_ambito=<%= nodo.getId_ambito() %>');">  </dhv:permission>
					</dhv:evaluate>
					</td>
<%					}
					
					if (nodo.getId_ambito()==8 && i_8 == 0) {							i_8++;																%>	
					<td><b><%= nodo.getAmbito_stringa() %></b></td><td></td><td></td><td></td><td></td>
					<td>
					<dhv:evaluate if="<%= User.getSiteId()<=0 || User.getSiteId()==nodo.getIdAsl() %>">
						<dhv:permission name="unitacrisi-8-add">	<input type="button" value="Inserisci nuovo referente" onClick="javascript:popLookupSelectorNuovoUnitaCrisi('id_ambito=<%= nodo.getId_ambito() %>');">  </dhv:permission>
					</dhv:evaluate>
					</td>
<%					}
					
					if (nodo.getId_ambito()==9 && i_9 == 0) {							i_9++;																%>	
					<td><b><%= nodo.getAmbito_stringa() %></b></td><td></td><td></td><td></td><td></td>
					<td>
					
					<dhv:evaluate if="<%= User.getSiteId()<=0 || User.getSiteId()==nodo.getIdAsl() %>">
						<dhv:permission name="unitacrisi-9-add">	<input type="button" value="Inserisci nuovo referente" onClick="javascript:popLookupSelectorNuovoUnitaCrisi('id_ambito=<%= nodo.getId_ambito() %>');">  </dhv:permission>
					</dhv:evaluate>
					</td>
<%					}
					
					if (nodo.getId_ambito()==10 && i_10 == 0) {							i_10++;																%>	
					<td><b><%= nodo.getAmbito_stringa() %></b></td><td></td><td></td><td></td><td></td>
					<td>
					<dhv:evaluate if="<%= User.getSiteId()<=0 || User.getSiteId()==nodo.getIdAsl() %>">
						<dhv:permission name="unitacrisi-10-add">	<input type="button" value="Inserisci nuovo referente" onClick="javascript:popLookupSelectorNuovoUnitaCrisi('id_ambito=<%= nodo.getId_ambito() %>');">  </dhv:permission>
					</dhv:evaluate>
					</td>
<%					}
					
					if (nodo.getId_ambito()==11 && i_11 == 0) {							i_11++;																%>	
					<td><b><%= nodo.getAmbito_stringa() %></b></td><td></td><td></td><td></td><td></td>
					<td>
					<dhv:evaluate if="<%= User.getSiteId()<=0 || User.getSiteId()==nodo.getIdAsl() %>">
						<dhv:permission name="unitacrisi-11-add">	<input type="button" value="Inserisci nuovo referente" onClick="javascript:popLookupSelectorNuovoUnitaCrisi('id_ambito=<%= nodo.getId_ambito() %>');">  </dhv:permission>
					</dhv:evaluate>
					</td>
<%					}
					
					if (nodo.getId_ambito()==12 && i_12 == 0) {							i_12++;																%>	
					<td><b><%= nodo.getAmbito_stringa() %></b></td><td></td><td></td><td></td><td></td>
					<td>
					<dhv:evaluate if="<%= User.getSiteId()<=0 || User.getSiteId()==nodo.getIdAsl() %>">
						<dhv:permission name="unitacrisi-12-add">	<input type="button" value="Inserisci nuovo referente" onClick="javascript:popLookupSelectorNuovoUnitaCrisi('id_ambito=<%= nodo.getId_ambito() %>');">  </dhv:permission>
					</dhv:evaluate>
					</td>
<%					}
					
					if (nodo.getId_ambito()==13 && i_13 == 0) {							i_13++;																%>	
					<td><b><%= nodo.getAmbito_stringa() %></b></td><td></td><td></td><td></td><td></td>
					<td>
					<dhv:evaluate if="<%= User.getSiteId()<=0 || User.getSiteId()==nodo.getIdAsl() %>">
						<dhv:permission name="unitacrisi-13-add">	<input type="button" value="Inserisci nuovo referente" onClick="javascript:popLookupSelectorNuovoUnitaCrisi('id_ambito=<%= nodo.getId_ambito() %>');">  </dhv:permission>
					</dhv:evaluate>
					</td>
<%					}
					
					if (nodo.getId_ambito()==14 && i_14 == 0) {							i_14++;																%>	
					<td><b><%= nodo.getAmbito_stringa() %></b></td><td></td><td></td><td></td><td></td>
					<td>
					<dhv:evaluate if="<%= User.getSiteId()<=0 || User.getSiteId()==nodo.getIdAsl() %>">
						<dhv:permission name="unitacrisi-14-add">	<input type="button" value="Inserisci nuovo referente" onClick="javascript:popLookupSelectorNuovoUnitaCrisi('id_ambito=<%= nodo.getId_ambito() %>');">  </dhv:permission>
					</dhv:evaluate>
					</td>
<%					}
					
					if (nodo.getId_ambito()==15 && i_15 == 0) {							i_15++;																%>	
					<td><b><%= nodo.getAmbito_stringa() %></b></td><td></td><td></td><td></td><td></td>
					<td>
					<dhv:evaluate if="<%= User.getSiteId()<=0 || User.getSiteId()==nodo.getIdAsl() %>">
						<dhv:permission name="unitacrisi-15-add">	<input type="button" value="Inserisci nuovo referente" onClick="javascript:popLookupSelectorNuovoUnitaCrisi('id_ambito=<%= nodo.getId_ambito() %>');">  </dhv:permission>
					</dhv:evaluate>
					</td>
<%					}
					
					if (nodo.getId_ambito()==16 && i_16 == 0) {							i_16++;																%>	
					<td><b><%= nodo.getAmbito_stringa() %></b></td><td></td><td></td><td></td><td></td>
					<td>
					<dhv:evaluate if="<%= User.getSiteId()<=0 || User.getSiteId()==nodo.getIdAsl() %>">
						<dhv:permission name="unitacrisi-16-add">	<input type="button" value="Inserisci nuovo referente" onClick="javascript:popLookupSelectorNuovoUnitaCrisi('id_ambito=<%= nodo.getId_ambito() %>');">  </dhv:permission>
					</dhv:evaluate>
					</td>
<%					}
					
					if (nodo.getId_ambito()==17 && i_17 == 0) {							i_17++;																%>	
					<td><b><%= nodo.getAmbito_stringa() %></b></td><td></td><td></td><td></td><td></td>
					<td>
					<dhv:evaluate if="<%= User.getSiteId()<=0 || User.getSiteId()==nodo.getIdAsl() %>">
						<dhv:permission name="unitacrisi-17-add">	<input type="button" value="Inserisci nuovo referente" onClick="javascript:popLookupSelectorNuovoUnitaCrisi('id_ambito=<%= nodo.getId_ambito() %>');">  </dhv:permission>
					</dhv:evaluate>
					</td>
<%					}
					

%>										
				<tr align="center" id='riga_<%= nodo.getId() %>' <%=(nodo.getLevel()==0) ? "bgcolor=\"yellow\"" : "" %> > 
					<td><%= " " %></td>
					<td><%= nodo.getResponsabile() %></td>
					<td><a href="mailto:<%= nodo.getMail() %>"><%= nodo.getMail() %></a></td>
					<td><%= nodo.getTelefono() %></td>
					<td><%= nodo.getFax() %></td>
					<td>
	<% 					String op_edit = "unitacrisi-" + nodo.getId_ambito() + "-edit" ;
						String op_del  = "unitacrisi-" + nodo.getId_ambito() + "-delete" ;
	%>
		<dhv:evaluate if="<%= User.getSiteId()<=0 || User.getSiteId()==nodo.getIdAsl() %>">
						<dhv:permission name="<%=op_edit%>">
							<input type="button" value="Modifica" onClick="javascript:popLookupSelectorModificaUnitaCrisi('id=<%= nodo.getId() %>');" />
						</dhv:permission>
						
						<dhv:permission name="<%=op_del%>">
							<input type="button" value="Elimina"  onClick="javascript:confermaDelete('UnitaCrisi.do?command=Cancella&id=<%= nodo.getId() %>')" />
						</dhv:permission>
						</dhv:evaluate>
					</td>
				</tr>
	<% 		}		%>
		
		
		
<%				if (i_1 == 0) {							i_1++;																%>	
				<tr align="center" id='extra_1' onmouseover="inRow(this)" onmouseout="outRow(this)" > 
						<td><b><%= lookup_ambito_unita_di_crisi.getValueFromId(1) %></b></td><td></td><td></td><td></td><td></td>
						<td>
							<dhv:permission name="unitacrisi-1-add">	<input type="button" value="Inserisci nuovo referente" onClick="javascript:popLookupSelectorNuovoUnitaCrisi('id_ambito=1');">  </dhv:permission>
						</td>
				</tr>
<%				}
					
					
				if (i_2 == 0) {							i_2++;																%>	
				<tr align="center" id='extra_2' onmouseover="inRow(this)" onmouseout="outRow(this)" > 
						<td><b><%= lookup_ambito_unita_di_crisi.getValueFromId(2) %></b></td><td></td><td></td><td></td><td></td>
						<td>
							<dhv:permission name="unitacrisi-2-add">	<input type="button" value="Inserisci nuovo referente" onClick="javascript:popLookupSelectorNuovoUnitaCrisi('id_ambito=2');">  </dhv:permission>
						</td>
				</tr>
<%				}
				
				if (i_3 == 0) {							i_3++;																%>	
				<tr align="center" id='extra_3' onmouseover="inRow(this)" onmouseout="outRow(this)" > 
						<td><b><%= lookup_ambito_unita_di_crisi.getValueFromId(3) %></b></td><td></td><td></td><td></td><td></td>
						<td>
							<dhv:permission name="unitacrisi-3-add">	<input type="button" value="Inserisci nuovo referente" onClick="javascript:popLookupSelectorNuovoUnitaCrisi('id_ambito=3');">  </dhv:permission>
						</td>
				</tr>
<%				}
					
				if (i_4 == 0) {							i_4++;																%>	
				<tr align="center" id='extra_4' onmouseover="inRow(this)" onmouseout="outRow(this)" > 
						<td><b><%= lookup_ambito_unita_di_crisi.getValueFromId(4) %></b></td><td></td><td></td><td></td><td></td>
						<td>
							<dhv:permission name="unitacrisi-4-add">	<input type="button" value="Inserisci nuovo referente" onClick="javascript:popLookupSelectorNuovoUnitaCrisi('id_ambito=4');">  </dhv:permission>
						</td>
				</tr>
<%				}
					
				if (i_5 == 0) {							i_5++;																%>	
				<tr align="center" id='extra_5' onmouseover="inRow(this)" onmouseout="outRow(this)" > 
						<td><b><%= lookup_ambito_unita_di_crisi.getValueFromId(5) %></b></td><td></td><td></td><td></td><td></td>
						<td>
							<dhv:permission name="unitacrisi-5-add">	<input type="button" value="Inserisci nuovo referente" onClick="javascript:popLookupSelectorNuovoUnitaCrisi('id_ambito=5');">  </dhv:permission>
						</td>
				</tr>
<%				}
					
				if (i_6 == 0) {							i_6++;																%>	
				<tr align="center" id='extra_6' onmouseover="inRow(this)" onmouseout="outRow(this)" > 
						<td><b><%= lookup_ambito_unita_di_crisi.getValueFromId(6) %></b></td><td></td><td></td><td></td><td></td>
						<td>
							<dhv:permission name="unitacrisi-6-add">	<input type="button" value="Inserisci nuovo referente" onClick="javascript:popLookupSelectorNuovoUnitaCrisi('id_ambito=6');">  </dhv:permission>
						</td>
				</tr>
<%				}
				//Disabilito perchè questo ambito è disabilitato e non ci sono persone associate
				if (i_7 == 0 && false) {							i_7++;																%>	
				<tr align="center" id='extra_7' onmouseover="inRow(this)" onmouseout="outRow(this)" > 
						<td><b><%= lookup_ambito_unita_di_crisi.getValueFromId(7) %></b></td><td></td><td></td><td></td><td></td>
						<td>
							<dhv:permission name="unitacrisi-7-add">	<input type="button" value="Inserisci nuovo referente" onClick="javascript:popLookupSelectorNuovoUnitaCrisi('id_ambito=7');">  </dhv:permission>
						</td>
				</tr>
<%				}
					
				if (i_8 == 0) {							i_8++;																%>	
				<tr align="center" id='extra_8' onmouseover="inRow(this)" onmouseout="outRow(this)" > 
						<td><b><%= lookup_ambito_unita_di_crisi.getValueFromId(8) %></b></td><td></td><td></td><td></td><td></td>
						<%if(User.getSiteId()!=204){ %>
						<td>
							<dhv:permission name="unitacrisi-8-add">	<input type="button" value="Inserisci nuovo referente" onClick="javascript:popLookupSelectorNuovoUnitaCrisi('id_ambito=8');">  </dhv:permission>
						</td>
						<%} %>
				</tr>
<%				}
					
				if (i_9 == 0) {							i_9++;																%>	
				<tr align="center" id='extra_9' onmouseover="inRow(this)" onmouseout="outRow(this)" > 
						<td><b><%= lookup_ambito_unita_di_crisi.getValueFromId(9) %></b></td><td></td><td></td><td></td><td></td>
						<td>
							<dhv:permission name="unitacrisi-9-add">	<input type="button" value="Inserisci nuovo referente" onClick="javascript:popLookupSelectorNuovoUnitaCrisi('id_ambito=9');">  </dhv:permission>
						</td>
				</tr>
<%				}
				//Disabilito perchè questo ambito è disabilitato e non ci sono persone associate	
				if (i_10 == 0 && false) {							i_10++;																%>	
				<tr align="center" id='extra_10' onmouseover="inRow(this)" onmouseout="outRow(this)" > 
						<td><b><%= lookup_ambito_unita_di_crisi.getValueFromId(10) %></b></td><td></td><td></td><td></td><td></td>
						<td>
							<dhv:permission name="unitacrisi-10-add">	<input type="button" value="Inserisci nuovo referente" onClick="javascript:popLookupSelectorNuovoUnitaCrisi('id_ambito=10');">  </dhv:permission>
						</td>
				</tr>
<%				}
					
				if (i_11 == 0) {							i_11++;																%>	
				<tr align="center" id='extra_11' onmouseover="inRow(this)" onmouseout="outRow(this)" > 
						<td><b><%= lookup_ambito_unita_di_crisi.getValueFromId(11) %></b></td><td></td><td></td><td></td><td></td>
						<td>
							<dhv:permission name="unitacrisi-11-add">	<input type="button" value="Inserisci nuovo referente" onClick="javascript:popLookupSelectorNuovoUnitaCrisi('id_ambito=11');">  </dhv:permission>
						</td>
				</tr>
<%				}
				//Disabilito perchè questo ambito è disabilitato e non ci sono persone associate	
				if (i_12 == 0 && false) {							i_12++;																%>	
				<tr align="center" id='extra_12' onmouseover="inRow(this)" onmouseout="outRow(this)" > 
						<td><b><%= lookup_ambito_unita_di_crisi.getValueFromId(12) %></b></td><td></td><td></td><td></td><td></td>
						<td>
							<dhv:permission name="unitacrisi-12-add">	<input type="button" value="Inserisci nuovo referente" onClick="javascript:popLookupSelectorNuovoUnitaCrisi('id_ambito=12');">  </dhv:permission>
						</td>
				</tr>
<%				}
					
				if (i_13 == 0) {							i_13++;																%>	
				<tr align="center" id='extra_13' onmouseover="inRow(this)" onmouseout="outRow(this)" > 
						<td><b><%= lookup_ambito_unita_di_crisi.getValueFromId(13) %></b></td><td></td><td></td><td></td><td></td>
						<td>
							<dhv:permission name="unitacrisi-13-add">	<input type="button" value="Inserisci nuovo referente" onClick="javascript:popLookupSelectorNuovoUnitaCrisi('id_ambito=13');">  </dhv:permission>
						</td>
				</tr>
<%				}
					
				if (i_14 == 0) {							i_14++;																%>	
				<tr align="center" id='extra_14' onmouseover="inRow(this)" onmouseout="outRow(this)" > 
						<td><b><%= lookup_ambito_unita_di_crisi.getValueFromId(14) %></b></td><td></td><td></td><td></td><td></td>
						<td>
							<dhv:permission name="unitacrisi-14-add">	<input type="button" value="Inserisci nuovo referente" onClick="javascript:popLookupSelectorNuovoUnitaCrisi('id_ambito=14');">  </dhv:permission>
						</td>
				</tr>
<%				}
					
				if (i_15 == 0) {							i_15++;																%>	
				<tr align="center" id='extra_15' onmouseover="inRow(this)" onmouseout="outRow(this)" > 
						<td><b><%= lookup_ambito_unita_di_crisi.getValueFromId(15) %></b></td><td></td><td></td><td></td><td></td>
						<td>
							<dhv:permission name="unitacrisi-15-add">	<input type="button" value="Inserisci nuovo referente" onClick="javascript:popLookupSelectorNuovoUnitaCrisi('id_ambito=15');">  </dhv:permission>
						</td>
				</tr>
<%				}
					
				if (i_16 == 0) {							i_16++;																%>	
				<tr align="center" id='extra_16' onmouseover="inRow(this)" onmouseout="outRow(this)" > 
						<td><b><%= lookup_ambito_unita_di_crisi.getValueFromId(16) %></b></td><td></td><td></td><td></td><td></td>
						<td>
							<dhv:permission name="unitacrisi-16-add">	<input type="button" value="Inserisci nuovo referente" onClick="javascript:popLookupSelectorNuovoUnitaCrisi('id_ambito=16');">  </dhv:permission>
						</td>
				</tr>
<%				}
					
				if (i_17 == 0) {							i_17++;																%>	
				<tr align="center" id='extra_17' onmouseover="inRow(this)" onmouseout="outRow(this)" > 
						<td><b><%= lookup_ambito_unita_di_crisi.getValueFromId(17) %></b></td><td></td><td></td><td></td><td></td>
						<td>
							<dhv:permission name="unitacrisi-17-add">	<input type="button" value="Inserisci nuovo referente" onClick="javascript:popLookupSelectorNuovoUnitaCrisi('id_ambito=17');">  </dhv:permission>
						</td>
				</tr>				
				
<%				}	%>
	
	
		</table>
		
	<%	} else {																																					%>
		<label style="padding-left: 30px" >Non sono presenti elementi.</label>
		
	<%  }																																							%>
	</body>
</dhv:permission>