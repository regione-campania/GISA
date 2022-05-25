<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page import="java.util.*,
				 java.text.DateFormat"%>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

	<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
	<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" >

		function attenzione(){
			return confirm('ATTENZIONE! Sicuro di voler proseguire con l\'eliminazione del nodo?');
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

<body>

	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
			<td width="100%"><a href="Oia.do">Modellatore Organizzazione ASL</a>  
			</td>
		</tr>
	</table>
	<%-- End Trails --%>
	
	
	<%-- DEMO DA QUI IN POI --%>
	<table cellpadding="7" cellspacing="0" width="100%" class="details">
	
		<tr align="center" >
			<th colspan="7" style="background-color: #FFE4B9;"><strong><center>Regione Campania</center></strong></th>
		</tr>
	
		<%-- Intestazione --%>
		<tr align="center" >
			<th></th>
			<th>A.S.L.</th>
			<th>Nome</th>
			<th>Descrizione</th>
			<th>Responsabile</th>
			<th>Ruolo Responsabile</th>
			<th></th>
		</tr>
	
		
		<tr align="center" id='riga_1' onmouseover="inRow(this)" onmouseout="outRow(1,this)" > 
			<td onclick="switch_elemento_albero('img_riga_1','sotto_riga_1')" ><img id="img_riga_1" src="images/tree0.gif" border=0></td>
			<td>NA exNA1</td>
			<td>Dipartimento di Prevenzione AV</td>
			<td>Descrizione Dipartimento di Prevenzione AV</td>
			<td>Alberto Campanile</td>
			<td>R01 - "Direttore Dipartimento Prevenzione"</td>
			<td>
				<input type="button" value="Inserisci" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=Veterinari&livello=1');">
				<input type="button" value="Modifica" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=Veterinari&livello=1');" />
				<input type="button" value="Elimina" onClick="javascript:attenzione()" />
			</td>
		</tr>
	
		
		<tr align="center"  id ="sotto_riga_1" style="display: none">
			<td colspan="7">
				<table cellpadding="7" cellspacing="0" width="100%" style="padding-left: 50px" >
				
				<%-- Intestazione --%>
				<tr align="center"  style="background-color: #FFE4C4;">
					<th></th>
					<th>A.S.L.</th>
					<th>Nome</th>
					<th>Descrizione</th>
					<th>Responsabile</th>
					<th>Ruolo Responsabile</th>
					<th></th>
				</tr>
	
				<tr align="center"  id ="riga_1_1" style="background-color: #FFE4C4;" >
					<td onclick="switch_elemento_albero('img_riga_1_1','sotto_riga_1_1')" ><img id="img_riga_1_1" src="images/tree0.gif" border=0></td>
					<td>NA exNA1</td>
					<td>SIAN</td>
					<td>Descrizione SIAN</td>
					<td>Adelaide Esposito</td>
					<td>R02 - "Direttore SIAN"</td>
					<td>
						<input type="button" value="Inserisci" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=SIAN&livello=2');">
						<input type="button" value="Modifica" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=SIAN&livello=2');" />
						<input type="button" value="Elimina" onClick="javascript:attenzione()" />
					</td>
				</tr>
				
				<tr align="center"  id ="sotto_riga_1_1" style="display: none">
					<td colspan="7">
						<table cellpadding="7" cellspacing="0" width="100%" style="padding-left: 100px" >
							<tr align="center"  style="background-color: #99FF00;">
								<th></th>
								<th>A.S.L.</th>
								<th>Nome</th>
								<th>Descrizione</th>
								<th>Responsabile</th>
								<th>Ruolo Responsabile</th>
								<th></th>
							</tr>
						
							<tr align="center"  id ="riga_1_1_1" style="background-color: #99FF00;">
								<td><img src="images/box.gif" border=0></td>
								<td>NA exNA1</td>
								<td>TPAL</td>
								<td>Descrizione SIAN</td>
								<td>Giuseppe Silvestri</td>
								<td>R08 - "TPAL"</td>
								<td>
									<input type="button" value="Inserisci" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=SIAN&livello=3');">
									<input type="button" value="Modifica" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=SIAN&livello=3');" />
									<input type="button" value="Elimina" onClick="javascript:attenzione()" />
								</td>
							</tr>
									
							<tr align="center"  id ="riga_1_1_2" style="background-color: #99FF00;">
								<td><img src="images/box.gif" border=0></td>
								<td>NA exNA1</td>
								<td>Medici</td>
								<td>Descrizione SIAN</td>
								<td>Rita Mele</td>
								<td>R06 - "Medici"</td>
								<td>
									<input type="button" value="Inserisci" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=SIAN&livello=3');">
									<input type="button" value="Modifica" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=SIAN&livello=3');" />
									<input type="button" value="Elimina" onClick="javascript:attenzione()" />
								</td>
							</tr>		
						</table>
					</td>
				</tr>
				
				<tr align="center"  id ="riga_1_2" style="background-color: #FFE4C4;">
					<td><img src="images/box.gif" border=0></td>
					<td>NA exNA1</td>
					<td>Servizi Veterinari</td>
					<td>Descrizione Servizi Veterinari</td>
					<td>Giuseppe Balzano</td>
					<td>R03 - "Direttore Servizi Veterinari"</td>
					<td>
						<input type="button" value="Inserisci" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=Veterinari&livello=2');">
						<input type="button" value="Modifica" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=Veterinari&livello=2');" />
						<input type="button" value="Elimina" onClick="javascript:attenzione()" />
					</td>
				</tr>
				
				<tr align="center"  id ="riga_1_3" style="background-color: #FFE4C4;">
					<td><img src="images/box.gif" border=0></td>
					<td>NA exNA1</td>
					<td>Servizi Veterinari</td>
					<td>Descrizione Servizi Veterinari</td>
					<td>Carmela Paolillo</td>
					<td>R03 - "Direttore Servizi Veterinari"</td>
					<td>
						<input type="button" value="Inserisci" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=Veterinari&livello=2');">
						<input type="button" value="Modifica" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=Veterinari&livello=2');" />
						<input type="button" value="Elimina" onClick="javascript:attenzione()" />
					</td>
				</tr>
				
				</table>
			</td>
		</tr>
		
		
		<tr align="center" id='riga_2' onmouseover="inRow(this)" onmouseout="outRow(2,this)" >
			<td onclick="switch_elemento_albero('img_riga_2','sotto_riga_2')" ><img id="img_riga_2" src="images/tree0.gif" border=0></td>	
			<td>BN exBN1</td>
			<td>Dipartimento di Prevenzione AV</td>
			<td>Descrizione Dipartimento di Prevenzione AV</td>
			<td>Gennaro Nave</td>
			<td>R01 - "Direttore Dipartimento Prevenzione"</td>
			<td>
				<input type="button" value="Inserisci" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=Veterinari&livello=1');">
				<input type="button" value="Modifica" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=Veterinari&livello=1');" />
				<input type="button" value="Elimina" onClick="javascript:attenzione()" />
			</td>
		</tr>
		
		<tr align="center"  id ="sotto_riga_2" style="display: none">
			<td colspan="7">
				<table cellpadding="7" cellspacing="0" width="100%" style="padding-left: 50px" >
				
				<tr align="center"  style="background-color: #FFE4C4;">
					<th></th>
					<th>A.S.L.</th>
					<th>Nome</th>
					<th>Descrizione</th>
					<th>Responsabile</th>
					<th>Ruolo Responsabile</th>
					<th></th>
				</tr>
	
				<tr align="center"  id ="riga_2_1" style="background-color: #FFE4C4;" >
					<td onclick="switch_elemento_albero('img_riga_2_1','sotto_riga_2_1')" ><img id="img_riga_2_1" src="images/tree0.gif" border=0></td>		
					<td>BN exBN1</td>
					<td>SIAN</td>
					<td>Descrizione SIAN</td>
					<td>Stefano Squitieri</td>
					<td>R02 - "Direttore SIAN"</td>
					<td>
						<input type="button" value="Inserisci" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=SIAN&livello=2');">
						<input type="button" value="Modifica" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=SIAN&livello=2');" />
						<input type="button" value="Elimina" onClick="javascript:attenzione()" />
					</td>
				</tr>
				
				<tr align="center"  id ="sotto_riga_2_1" style="display: none">
							<td colspan="7">
								<table cellpadding="7" cellspacing="0" width="100%" style="padding-left: 100px" >
								
								<tr align="center"  style="background-color: #99FF00;">
									<th></th>
									<th>A.S.L.</th>
									<th>Nome</th>
									<th>Descrizione</th>
									<th>Responsabile</th>
									<th>Ruolo Responsabile</th>
									<th></th>
								</tr>
					
								<tr align="center"  id ="riga_2_1_1" style="background-color: #99FF00;">
									<td><img src="images/box.gif" border=0></td>
									<td>BN exBN1</td>
									<td>TPAL</td>
									<td>Descrizione SIAN</td>
									<td>Alessandro De Stasio</td>
									<td>R08 - "TPAL"</td>
									<td>
										<input type="button" value="Inserisci" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=SIAN&livello=3');">
										<input type="button" value="Modifica" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=SIAN&livello=3');" />
										<input type="button" value="Elimina" onClick="javascript:attenzione()" />
									</td>
								</tr>
								
								<tr align="center"  id ="riga_2_1_2" style="background-color: #99FF00;">
									<td><img src="images/box.gif" border=0></td>
									<td>BN exBN1</td>
									<td>Medici</td>
									<td>Descrizione SIAN</td>
									<td>Antonio Donatiello</td>
									<td>R06 - "Medici"</td>
									<td>
										<input type="button" value="Inserisci" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=SIAN&livello=3');">
										<input type="button" value="Modifica" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=SIAN&livello=3');" />
										<input type="button" value="Elimina" onClick="javascript:attenzione()" />
									</td>
								</tr>		
										
								</table>
							</td>
					</tr>
				
				<tr align="center"  id ="riga_2_2" style="background-color: #FFE4C4;">
					<td><img src="images/box.gif" border=0></td>
					<td>BN exBN1</td>
					<td>Servizi Veterinari</td>
					<td>Descrizione Servizi Veterinari</td>
					<td>Daniele Pinto</td>
					<td>R03 - "Direttore Servizi Veterinari"</td>
					<td>
						<input type="button" value="Inserisci" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=Veterinari&livello=2');">
						<input type="button" value="Modifica" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=Veterinari&livello=2');" />
						<input type="button" value="Elimina" onClick="javascript:attenzione()" />
					</td>
				</tr>
				
				<tr align="center"  id ="riga_2_3" style="background-color: #FFE4C4;">
					<td><img src="images/box.gif" border=0></td>
					<td>BN exBN1</td>
					<td>Servizi Veterinari</td>
					<td>Descrizione Servizi Veterinari</td>
					<td>Michele D'Onofrio</td>
					<td>R03 - "Direttore Servizi Veterinari"</td>
					<td>
						<input type="button" value="Inserisci" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=Veterinari&livello=2');">
						<input type="button" value="Modifica" onClick="javascript:popLookupSelectorNuovoNodoOia('tipologia=Veterinari&livello=2');" />
						<input type="button" value="Elimina" onClick="javascript:attenzione()" />
					</td>
				</tr>
				
				</table>
			</td>
		</tr>

	</table>
	<input type = "hidden" id ="last_click" value = "-1"> 
	
	
	
</body>