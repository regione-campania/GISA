<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@page import="it.us.web.util.properties.Application"%>
<div id="sidebar-left">

	<div class="mymoduletable">		
		
		<p id="p3_my"> Benvenuto 
			<br>						
				<c:out value="${utente.nome}"/> <c:out value="${utente.cognome}"/>
			<br>						
				${utente.clinica.nome } - ${utente.clinica.lookupComuni.description } 
			<br>			
		</p>



<div id="utentiConnessi" >
		<span style="text-align: center;display:block; background-color:#F0F9FC ; width:176px;border:1px solid black;font-weight:normal;">Utenti connessi: <b><%@ include file="../../../utentiConnessi.jsp"%></b> &nbsp; &nbsp; </span>
		</div>
		
	<ul id="qm0" class="qmmc">

	<li>
		<a onclick="attendere()" href="login.Logout.us" accesskey="2"><span>Esci da V.A.M.</span></a>
	</li>

				
	<us:can f="AMMINISTRAZIONE" sf="MAIN" og="MAIN" r="r" >
		<li><a href="#">Amministrazione Sistema</a>
			<ul id="qm1" class="qmmc">
				
				<us:can f="AMMINISTRAZIONE" sf="UTENTI" og="MAIN" r="w" >
					<li>
						<a onclick="attendere()" href="utenti.List.us" accesskey="5"><span>Utenti</span></a>
					</li>
				</us:can>
				
				<us:can f="AMMINISTRAZIONE" sf="RUOLI" og="MAIN" r="r" >
					<li>
						<a onclick="attendere()" href="ruoli.List.us" accesskey="3"><span>Ruoli</span></a>
					</li>
				</us:can>
				
				<us:can f="AMMINISTRAZIONE" sf="FUNZIONI" og="MAIN" r="r" >
					<li>
						<a onclick="attendere()" href="funzioni.ToPermissionEdit.us" accesskey="4"><span>Funzioni</span></a>
					</li>
				</us:can>
				
				<us:can f="AMMINISTRAZIONE" sf="UTENTI" og="ASSOCIAZIONE CLINICA" r="w" >
					<li>
						<a onclick="attendere()" href="vam.cliniche.ToAssociatesUser.us" accesskey="5"><span>Associazione Utente/Clinica</span></a>
					</li>
				</us:can>
				
			</ul>
		</li>
	</us:can>
	
	<us:can f="AMMINISTRAZIONE" sf="MAIN" og="MAIN" r="r" >
		<li>
			<a onclick="attendere()"  href="messaggi.ToModifica.us" accesskey="4"><span>Messaggio Home Page</span></a>
		</li>
	</us:can>
	
	<li>
		<a onclick="attendere()" href="Home.us" accesskey=""><span>Home</span></a>
	</li>
	
	<us:can f="ACCETTAZIONE" sf="MAIN" og="MAIN" r="r" >
		<li>
			<a onclick="attendere()"  href="vam.accettazione.Home.us" accesskey=""><span>Accettazione</span></a>
		</li>
	</us:can>
	
	<us:can f="ACCETTAZIONE" sf="MAIN" og="MAIN" r="r" >
	<li>
			<a onclick="attendere()"  href="vam.accettazioneMultipla.Home.us" accesskey=""><span>Accettazione Multipla</span></a>
		</li>
	</us:can>
	
	<us:can f="CC" sf="MAIN" og="MAIN" r="r" >
		<li>
			<a onclick="attendere()"  href="vam.cc.ToFind.us" accesskey="4"><span>Cartella Clinica</span></a>
		</li>
	</us:can>
	
	<us:can f="FASCICOLO_SANITARIO" sf="MAIN" og="MAIN" r="r" >
		<li>
			<a onclick="attendere()"  href="vam.fascicoloSanitario.List.us" accesskey="4"><span>Fascicolo Sanitario</span></a>
		</li>
	</us:can>
	
	<us:can f="TRASFERIMENTI" sf="MAIN" og="MAIN" r="r" >
		<li>
			<a onclick="attendere()"  href="vam.cc.trasferimenti.Home.us" accesskey="4"><span>Trasferimenti</span></a>
		</li>
	</us:can>
	
	<us:can f="CC" sf="MAIN" og="MAIN" r="r" >
		<li>
			<a onclick="attendere()"  href="vam.registroTumori.List.us" accesskey="4"><span>Registro Tumori</span></a>
		</li>
	</us:can>	
		
		
	<us:can f="RICHIESTA_ISTOPATOLOGICO" sf="ADD" og="MAIN" r="r" >
		<li>
			<a onclick="attendere()"  href="vam.richiesteIstopatologici.ToFindAnimale.us" accesskey=""><span>Richiesta Istopatologico</span></a>
		</li>
	</us:can>
	
	<us:can f="RICHIESTA_ISTOPATOLOGICO" sf="LIST_LLPP" og="MAIN" r="r" >
		<li>
			<a href="#"><span>Richiesta Istopatologico</span></a>
				<ul id="qm1" class="qmmc">	
					<us:can f="RICHIESTA_ISTOPATOLOGICO" sf="ADD_LLPP" og="MAIN" r="r" >		
						<li>
							<a onclick="attendere()"  href="vam.richiesteIstopatologici.ToFindAnimaleLLPP.us" accesskey="5"><span>Inserisci Richiesta</span></a>
						</li>
					</us:can>
					<us:can f="RICHIESTA_ISTOPATOLOGICO" sf="LIST_LLPP" og="MAIN" r="r" >
						<li>
							<a onclick="attendere()"  href="vam.richiesteIstopatologici.ListLLPP.us" accesskey="3"><span>Lista</span></a>
						</li>
					</us:can>
				</ul>
		</li>
	</us:can>
	
	
	
	<us:can f="MAGAZZINO" sf="MAIN" og="MAIN" r="r" >
		<li>
			<a href="#" accesskey=""><span>Gestione Magazzino</span></a>
				<ul id="qm1" class="qmmc">	
						<us:can f="MAGAZZINO" sf="FARMACI" og="MAIN" r="r" >			
							<li>
								<a onclick="attendere()"  href="vam.magazzino.farmaci.Detail.us" accesskey="5"><span>Farmaci</span></a>
							</li>
						</us:can>
						<us:can f="MAGAZZINO" sf="MANGIMI" og="MAIN" r="r" >
							<li>
								<a onclick="attendere()"  href="vam.magazzino.mangimi.Detail.us" accesskey="3"><span>Mangimi</span></a>
							</li>
						</us:can>
						<us:can f="MAGAZZINO" sf="AS" og="MAIN" r="r" >
							<li>
								<a onclick="attendere()"  href="vam.magazzino.articoliSanitari.Detail.us" accesskey="3"><span>Articoli sanitari</span></a>
							</li>
						</us:can>
				</ul>
		</li>
	</us:can>
	
	<us:can f="RICHIESTA_ISTOPATOLOGICO" sf="DETAIL" og="MAIN" r="r" >
		<li>
			<a onclick="attendere()"  href="vam.richiesteIstopatologici.ToFind.us" accesskey=""><span>Cerca Istopatologico</span></a>
		</li>
	</us:can>
	
	<us:can f="GESTIONE_ISTOPATOLOGICO" sf="ADD" og="MAIN" r="r" >
		<li>
			<a onclick="attendere()"  href="vam.izsm.esamiIstopatologici.ToFind.us" accesskey=""><span>Gestione Istopatologici</span></a>
		</li>
	</us:can>	
	
	<us:can f="AGENDA" sf="MAIN" og="MAIN" r="r" >
		<li>
			<a onclick="attendere()"  href="vam.agenda.ToChooseStruttura.us" accesskey=""><span>Agenda</span></a>
		</li>
	</us:can>
	
	<us:can f="MAGAZZINO" sf="MAIN" og="MAIN" r="r" >
		<li>
			<a href="#" accesskey=""><span>Gestione Magazzino</span></a>
				<ul id="qm1" class="qmmc">	
						<us:can f="MAGAZZINO" sf="FARMACI" og="MAIN" r="r" >			
							<li>
								<a onclick="attendere()"  href="vam.magazzino.farmaci.Detail.us" accesskey="5"><span>Farmaci</span></a>
							</li>
						</us:can>
						<us:can f="MAGAZZINO" sf="MANGIMI" og="MAIN" r="r" >
							<li>
								<a onclick="attendere()"  href="vam.magazzino.mangimi.Detail.us" accesskey="3"><span>Mangimi</span></a>
							</li>
						</us:can>
						<us:can f="MAGAZZINO" sf="AS" og="MAIN" r="r" >
							<li>
								<a onclick="attendere()"  href="vam.magazzino.articoliSanitari.Detail.us" accesskey="3"><span>Articoli sanitari</span></a>
							</li>
						</us:can>
				</ul>
		</li>
	</us:can>
	
	<us:can f="STATISTICHE" sf="MAIN" og="MAIN" r="r" >
		<li>
			<a onclick="attendere()"  href="vam.statistiche.Home.us" accesskey=""><span>Statistiche</span></a>
		</li>
	</us:can>
	
<!--	<li>-->
<!--		<a onclick="attendere()"  href="#" accesskey=""><span>Gestione Personale</span></a>-->
<!--	</li>-->
<!--	-->	
<!--	<li>-->
<!--		<a onclick="attendere()"  href="#" accesskey=""><span>Report</span></a>-->
<!--	</li>-->
<!--	-->
	
	<us:can f="HD" sf="MAIN" og="MAIN" r="r" >
		<li>
			<a href="#" accesskey=""><span>Segnalazioni</span></a>
				<ul id="qm1" class="qmmc">	
						<us:can f="HD" sf="ADD" og="MAIN" r="r" >			
							<li>
								<a onclick="attendere()"  href="vam.helpDesk.ToAdd.us" accesskey="5"><span>Nuova segnalazione</span></a>
							</li>
						</us:can>
						<us:can f="HD" sf="LIST" og="MAIN" r="r" >
							<li>
								<a onclick="attendere()"  href="vam.helpDesk.List.us" accesskey="3"><span>Gestisci segnalazioni</span></a>
							</li>
						</us:can>
				</ul>
		</li>
	</us:can>
	
			<us:can f="BDR" sf="MAIN" og="MAIN" r="r" >
			<li>
			<a href="#" accesskey=""><span>Accesso BDR</span></a>
				<ul id="qm1" class="qmmc">	
						<us:can f="ACCESSO_BDU" sf="ADD" og="MAIN" r="w">
						<li>
							<a href="vam.bdr.canina.SwitchToAnagrafe.us" accesskey="5" target="_new"><span>Cani/Gatti</span></a>
						</li>
						</us:can>
						<li>
							<a href="indexS.jsp?entrypointSinantropi=vam" accesskey="1" target="_new"><span>Sinantropi/Marini/Zoo</span></a>
						</li>
				</ul>
		</li>
		</us:can>
		<!-- li>
			<a href="vam.SwitchToDigemon.us" accesskey="" target="_new"><span>Reportistica</span></a>
		</li -->
		
		<us:can f="STORICO" sf="MAIN" og="MAIN" r="r" >
			<li>
				<a onclick="attendere()" href="StoricoModifiche.us" accesskey=""><span>Storico Modifiche</span></a>
			</li>
		</us:can>
		
		<us:can f="LOG_OPERAZIONI" sf="FUNZIONI" og="MAIN" r="r" >
			<li>
				<a onclick="attendere()" href="LogOperazioni.us" accesskey=""><span>Log Operazioni</span></a>
			</li>
		</us:can>
	
	<li>
		<a href="vam.util.ShowDoc.us" accesskey="" target="_new"><span>Help</span></a>
	</li>
	
	
   </ul>
   
   <p id="p3_my"> <br/>Help Desk:<br/><%=Application.get("TELEFONO_HD")%><br/> <br/> 
   </p>
</div>
<img id="finemenu" src="images/finemenu.jpg"/>	

</div>
	
				
			
	
			