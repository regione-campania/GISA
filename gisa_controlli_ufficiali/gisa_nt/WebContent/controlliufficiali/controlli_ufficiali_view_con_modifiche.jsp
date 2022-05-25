<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.aspcfs.checklist.base.Audit"%>

<%@page import="java.util.ArrayList"%>
<%@page import="org.aspcfs.modules.login.beans.UserBean"%>
<%@page import="org.aspcfs.modules.oia.base.OiaNodo"%><script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>


<script>
			
			//----------------------------------------- CONTROLLA SE IL SERVER E' UP
			
			function ifUp(url,onUp,onDown) 
			{
				var RANDOM_DIGITS = 7; 
				var pow = Math.pow(10,RANDOM_DIGITS);
				var randStr = String(Math.floor(Math.random()*pow)+pow).substr(1 );
				var img = new Image();
				img.onload = onUp;
				img.onerror = onDown;
				img.src = url+"?"+randStr;
			
			}
			

			//----------------------------------------- DATABASE MANAGEMENT
		
			var indexedDB = window.indexedDB || window.webkitIndexedDB || window.mozIndexedDB || window.msIndexedDB;
			var IDBTransaction = window.IDBTransaction || window.webkitIDBTransaction;
			var db;
			(
			
			function () 
			{     
           
				//---------------------------------- INIT DB
				function initDb() 
				{
					var request = indexedDB.open("GISA_OFFLINE",1);  
					
					// apertura db: on success
					request.onsuccess = function (evt)
					{
						console.log('DB opened');
						db = request.result;                                                            
					};
	 
					// apertura db: on error
					request.onerror = function (evt) 
					{
						console.log('Error opening DB '+ evt.target.errorCode);
					};
	 
					// apertura db: on upgradeneeded
					request.onupgradeneeded = function (evt) 
					{                   
						var objectStore = evt.currentTarget.result.createObjectStore("databean", { keyPath: "timeStamp", autoIncrement: false });
						objectStore.createIndex("databean", "databean", { unique: false });
					};
				}
 
 
				//---------------------------------- CONTENT LOADING
				function contentLoaded()
				{
		 
					initDb();              


							
							$('#pendenti').click(function()
									{
								
								
								var tab ;
								jQuery('#label').html("<font color = 'red'>CARICAMENTO CHECKLIST IN CORSO. ATTENDERE . . . </font>");	
								
										var idControlloUfficiale = $('#idControlloUfficiale').val();
										tab = "<TABLE cellpadding='4' id ='checklist'  cellspacing='0' border='0' width='100%' class='details'><TR><TH>Tipologia</TH> <TH>Vai alla checkslist</TH></TR>" ;
										var lastpath = 'null';
										var transaction = db.transaction("databean", IDBTransaction.READ_WRITE);
										var objectStore = transaction.objectStore("databean");
										var request = objectStore.openCursor();
										var cursRow = 0 ;
										request.onsuccess = function(evt) 
										{

											var cursor = evt.target.result;
													
											if (cursor) 
											{ 
												
												var databean = cursor.value;

												var idChecklist_memorizzato = databean.idChecklist;

												
												console.log('idChecklist_memorizzato '+idChecklist_memorizzato);
												console.log(lastpath);
												console.log(databean.path);
												
												if((lastpath=='null' || databean.path!=lastpath) && idControlloUfficiale == databean.idControllo)
												{
													cursRow++ ;
													lastpath = databean.path;
													tab+="<TR><TD>"+databean.checklistname+"</TD> <TD><A HREF=\'"+lastpath+"\'>VAI</A></TD></TR>" ;
													//jQuery('#checklist').html("<TR><TD>"+databean.timeStamp+"</TD> <TD>"+databean.checklistname+"</TD> <TD><A HREF=\'"+lastpath+"\'>VAI</A></TD></TR>");
												}
														
												cursor.continue();  
											}
											else
											{
												tab += "</TABLE>" ;
												if(cursRow>0)
												{
													jQuery('#label').html("<font color = 'green'>Caricamento Completato</font>");
													jQuery('#elenco').html(tab);
												}
												else
												{
													jQuery('#label').html("<font color = 'green'>Caricamento Completato.Nessuna checklist presente</font>");
												}
											}

											
											
											
										} // fine request onsuccess
										
										}); // fine evento load-on-click
				
					
				 
				}	// fine contentLoaded
 
            window.addEventListener("DOMContentLoaded", contentLoaded, false); 
			})();  // fine funzione principale DB 
			
</script>

<script type="text/javascript">

       	var msg_1 = '';
       	var principale_glob ;
			function controlloChecklist(msg,principale,userId)
			{

				msg_1 = msg;
				orgId = document.details.orgId.value;
				
				idCu = document.details.id.value;
				principale_glob = principale ;
				PopolaCombo.controlloAperturaChecklist(orgId,idCu,userId,viewMessageCallback) ;
			}
	
			function viewMessageCallback (returnValue)
			{

				
				if (returnValue == "")
				{
					if(document.details.assetId!=null)
					{
						compilaCheckList(msg_1,<%=TicketDetails.getOrgId() %>,<%=TicketDetails.getAssetId()%>,<%=TicketDetails.getId() %>,<%=TicketDetails.getPaddedId()%>,principale_glob,'details')

					}
					else
					{
						compilaCheckList(msg_1,<%=TicketDetails.getOrgId() %>,<%=TicketDetails.getId() %>,<%=TicketDetails.getPaddedId()%>,principale_glob,'details')
						
					}
				}
				else
				{
					alert ('ATTENZIONE! Per poter avere un punteggio checklist attendibile bisognerebbe provvedere alla chiusura dei seguenti controlli (Dalla seguente lista sono esclusi i controlli ufficiali con campioni/tamponi in attesa di esito)\n'+returnValue);
					//if(confirm('ATTENZIONE: per poter inserire la checklist occorre provvedere alla chiusura dei seguenti controlli \n'+returnValue+". Continuare?")==true)
					//compilaCheckList(msg_1,<%=TicketDetails.getOrgId() %>,<%=TicketDetails.getId() %>,<%=TicketDetails.getPaddedId()%>,principale_glob,'details')
				}
				
			}			

			</script>



<script>


$(document).ready(function() {	

	
	//select all the a tag with name equal to modal
	$('a[name=modal]').click(function(e) {
		//Cancel the link behavior
		e.preventDefault();
		
		//Get the A tag
		var id = $(this).attr('href');
	
		//Get the screen height and width
		var maskHeight = $(document).height();
		var maskWidth = $(window).width();
	
		//Set heigth and width to mask to fill up the whole screen
		$('#mask').css({'width':maskWidth,'height':maskHeight});
		
		//transition effect		
		$('#mask').fadeIn(1000);	
		$('#mask').fadeTo("slow",0.8);	
	
		//Get the window height and width
		var winH = $(window).height();
		var winW = $(window).width();
              
		//Set the popup window to center
		$(id).css('top',  winH/2-$(id).height()/2);
		$(id).css('left', winW/2-$(id).width()/2);
		
		//transition effect
		$(id).fadeIn(2000); 
	
	});
	
	//if close button is clicked
	$('.window .close').click(function (e) {
		//Cancel the link behavior
		e.preventDefault();
		
		$('#mask').hide();
		$('.window').hide();
	});		
	
	//if mask is clicked
	$('#mask').click(function () {
		$(this).hide();
		$('.window').hide();
	});			
	
});

</script>
<style>
body {
	font-family: verdana;
	font-size: 15px;
}

a {
	color: #333;
	text-decoration: none
}

a:hover {
	color: #ccc;
	text-decoration: none
}

#mask {
	position: absolute;
	left: 0;
	top: 0;
	z-index: 9000;
	background-color: #000;
	display: none;
}

#boxes .window {
	position: absolute;
	left: 0;
	top: 0;
	width: 675px;
	height: 658;
	display: none;
	z-index: 9999;
	padding: 20px;
}

#boxes
 
#dialog
 
#
{
width
:
675px;
 
  
height
:
680;

  
padding
:
10px;

  
background-color
:
#ffffff
;


}
#dialog4 {
	width: 100%;
	height: 100%;
	padding: 10px;
	background-color: #ffffff;
	overflow: scroll;
}

#boxes #dialog1 {
	width: 375px;
	height: 203px;
}

#dialog1 .d-header {
	background: url(images/login-header.png) no-repeat 0 0 transparent;
	width: 375px;
	height: 150px;
}

#dialog1 .d-header input {
	position: relative;
	top: 60px;
	left: 100px;
	border: 3px solid #cccccc;
	height: 22px;
	width: 200px;
	font-size: 15px;
	padding: 5px;
	margin-top: 4px;
}

#dialog1 .d-blank {
	float: left;
	background: url(images/login-blank.png) no-repeat 0 0 transparent;
	width: 267px;
	height: 53px;
}

#dialog1 .d-login {
	float: left;
	width: 108px;
	height: 53px;
}

#boxes #dialog2 {
	background: url(images/notice.png) no-repeat 0 0 transparent;
	width: 326px;
	height: 229px;
	padding: 50px 0 20px 25px;
}
</style>

<%
if (request.getAttribute("SalvataggioChecklist")!=null)
{
	%>
	

	<input type = "hidden" id = "idChecklist" name = "idChecklist" value = "<%=request.getAttribute("idChecklist_corrente") %>"/>
	
		<script>

			
			var indexedDB = window.indexedDB || window.webkitIndexedDB || window.mozIndexedDB || window.msIndexedDB;
			var IDBTransaction = window.IDBTransaction || window.webkitIDBTransaction;
			var request = indexedDB.open("GISA_OFFLINE",1);  
			// OPENING: on success
			request.onsuccess = function (evt)
			{
				console.log('DB opened');
				db = request.result;  
				console.log(db);
				
				var lastpath = 'null';
				var transaction = db.transaction("databean", IDBTransaction.READ_WRITE);
				var objectStore = transaction.objectStore("databean");
	
				var idChecklist_corrente = $('#idChecklist').val();
				var idControllo_corrente = $('#idControllo').val();
				

				var requestb = objectStore.openCursor();
				requestb.onsuccess = function(evt) 
				{  
					var cursor = evt.target.result;  
					if (cursor) 
					{  
						
						var databean = cursor.value;
						var idChecklist_memorizzato = databean.idChecklist;
						var idControllo_memorizzato = databean.idControllo;

						

						console.log('idChecklist_corrente '+idChecklist_corrente);
						if(idChecklist_memorizzato==idChecklist_corrente && idChecklist_memorizzato==idChecklist_corrente)
						{
					
						
						cursor.delete(); 
						console.log('Checklist Cancellata da locale ');
						}
						cursor.continue();  
					}
					else   
						console.log('Cursor: no more entries to delete'); 
				};

			};
			
			</script>

<%	
}

%>

<div id="boxes">



<div id="dialog4" class="window"><a href="#" class="close" /><font
	color="red">CHIUDI</font></a> <br>
<%@ include file="guida_compila_checklist.txt"%>

</div>

<!-- Mask to cover the whole screen -->
<div id="mask"></div>

</div>
<input type="hidden" name="orgId" value="<%=TicketDetails.getOrgId() %>">

<% if(TicketDetails.getTipoCampione()==5){%>
<center><a href="#dialog4" name="modal"><b>Guida alla
compilazione CheckList</b></a></center>
<br>
<br>
<%} %>
<tr>
	<th colspan="2">Scheda Controllo Ufficiale</th>
</tr>

<tr class="containerBody">
	<td nowrap class="formLabel"><dhv:label name="stabilimenti.site">Site</dhv:label></td>
	<td><%=SiteIdList.getSelectedValue(TicketDetails.getSiteId())%> <%
					%> <input type="hidden" name="siteId"
		value="<%=TicketDetails.getSiteId()%>"></td>
</tr>

<input type="hidden" name="id" id="id"
	value="<%=  TicketDetails.getId() %>" />


<tr class="containerBody">
	<td class="formLabel"><dhv:label name="">Identificativo C.U.</dhv:label>
	</td>


	<td><%= toHtml(TicketDetails.getPaddedId()) %> <input
		type="hidden" name="idControlloUfficiale" id="idControlloUfficiale"
		value="<%=  TicketDetails.getPaddedId() %>" /> <input type="hidden"
		name="idC" id="idC" value="<%=  TicketDetails.getPaddedId() %>" /></td>

</tr>
<tr class="containerBody">
	<td name="tipoCampione1" id="tipoCampione1" nowrap class="formLabel">
	<dhv:label name="">Tipo di controllo</dhv:label></td>
	<td>
	<%
    String tipoControllo1 = "" ;
    if(TicketDetails.getTipoCampione()==3){
    	
		tipoControllo1 = "Audit - "+AuditTipo.getSelectedValue(TicketDetails.getAuditTipo());
    	
		if(TicketDetails.getAuditTipo()==1){
    		String bpi = "" ;
    		String haccp = "" ;
    		String tipiaudit = "<b>Oggetto dell audit</b><br>" ;
    		
    		Iterator<Integer> itTipoAudit = TicketDetails.getTipoAudit().keySet().iterator();
    		while (itTipoAudit.hasNext())
    		{
    			int tipoAudit = itTipoAudit.next();
    			String descrizioneTipoAudit = TicketDetails.getTipoAudit().get(tipoAudit);
    			
    			tipiaudit +=descrizioneTipoAudit+"<br/>" ;
    			
    		}
    		
    		if(TicketDetails.getTipoAudit().containsKey(2)){
	    		

	    		HashMap<Integer,String> listaBpi= TicketDetails.getLisaElementibpi();
	    		Iterator<Integer> valoriBpiSel=TicketDetails.getLisaElementibpi().keySet().iterator();
	    		
	    		while(valoriBpiSel.hasNext()){
	    			String bpiSel=listaBpi.get(valoriBpiSel.next());
	    			
	    			bpi=bpi+" "+bpiSel+" - ";
	    		}
	    		
	    		
	    	}
    		
       		if(TicketDetails.getTipoAudit().containsKey(3)){
	    		
	    		HashMap<Integer,String> listahaccp= TicketDetails.getLisaElementihaccp();
	    		Iterator<Integer> valoriHaccpSel=TicketDetails.getLisaElementihaccp().keySet().iterator();
	    		
	    		while(valoriHaccpSel.hasNext()){
	    			String haccpSel=listahaccp.get(valoriHaccpSel.next());
	    			
	    			haccp=haccp+" "+haccpSel+" - ";
	    		}
	    		
	    		
	    	}
    		
       		out.print(tipoControllo1+"<br>");
       		
       		out.println(tipiaudit);
       		
       		if(!bpi.equals(""))
       			out.println("<br><b>BPI</b><br>"+bpi+"<br>");
       		if(!haccp.equals(""))
       			out.println("<br><b>HACCP</b><br> : "+haccp+"<br>");
    	}
    	else
    	{
    		out.print(tipoControllo1+"<br>");
    	}

    	
    }else
    	if(TicketDetails.getTipoCampione()==4){
    		
    		tipoControllo1 = "Ispezione Semplice <br>";
    		String tipiispezione = "<br><b>Motivo ispezione</b><br>" ;
    		String piani = "" ;
    		Iterator<Integer> itTipoIspezione = TicketDetails.getTipoIspezione().keySet().iterator();
    		while (itTipoIspezione.hasNext())
    		{
    			int tipoIspezione = itTipoIspezione.next();
    			String descrizioneTipoIspezione = TicketDetails.getTipoIspezione().get(tipoIspezione);
    			tipiispezione +=descrizioneTipoIspezione+"<br/>" ;
    			
    		}
    		
    		
    		out.print(tipoControllo1);
    		out.print(tipiispezione);
    		
    		if(TicketDetails.getTipoIspezione().containsKey(4) && TicketDetails.getIspezioneAltro() != null){
            				out.print("Note : " + TicketDetails.getIspezioneAltro());
			}
            			
            			
    					
    		
    		
    }
    	else
    	{
    		if(TicketDetails.getTipoCampione()==5){
    			
    			out.print("Ispezione con la tecnica della sorveglianza");
    			
    		}
    	}
    	
    
    	
    %> <input type="hidden" name="tipoCampione"
		value="<%=TicketDetails.getTipoCampione() %>"></td></tr>
		
<%  // Se il CU è "ISPEZIONE/IN SORVEGLIANZA" il codice Ateco non deve essere visualizzato
		if ( (TicketDetails.getTipoCampione() == 5) ) {} else {
			if (request.getAttribute("linea_attivita")!=null)
			{
			
				ArrayList<String> linee = (ArrayList<String>)request.getAttribute("linea_attivita");
		%>
		<tr class="containerBody" >
		    <td valign="top" class="formLabel">
		     Linea Attività Sottoposta a Controllo
		    </td>
		    
		    <td>
				<table class = "noborder">
				<%
				for (String linea_di_attivita : linee)
				{
					%>
					<tr><td><%= toHtml(linea_di_attivita) %></td></tr>
					<%
					
				}
				%>
				</table>	    
			    	
		    </td>
		</tr>
	<% }
			else
			{
				if (request.getAttribute("linee_attivita_stabilimenti")!=null)
				{
					ArrayList<String> linee = (ArrayList<String>)request.getAttribute("linee_attivita_stabilimenti");
					
			%>
			<tr class="containerBody" >
			    <td valign="top" class="formLabel">
			     Linea Attività Sottoposta a Controllo
			    </td>
			    
			    <td>
					<table class = "noborder">
					<%
					for (String linea_di_attivita : linee)
					{
						%>
						<tr><td><%= toHtml(linea_di_attivita) %></td></tr>
						<%
						
					}
					%>
					</table>	    
				    	
			    </td>
			</tr>
		<% }
				else
					if (request.getAttribute("linee_attivita_stabilimenti")!=null)
					{
						ArrayList<String> linee = (ArrayList<String>)request.getAttribute("linee_attivita_stabilimenti");
						
				%>
				<tr class="containerBody" >
				    <td valign="top" class="formLabel">
				     Linea Attività Sottoposta a Controllo
				    </td>
				    
				    <td>
						<table class = "noborder">
						<%
						for (String linea_di_attivita : linee)
						{
							%>
							<tr><td><%= toHtml(linea_di_attivita) %></td></tr>
							<%
							
						}
						%>
						</table>	    
					    	
				    </td>
				</tr>
			<% }	
					
				
				
			}
		
		}%>
				
		
<%
if(TicketDetails.getTipoIspezione().containsKey(5))
{
%>
<tr >
		<td nowrap class="formLabel">
			Rilascio Certificazione
		<td>
		<table class = "noborder">
				<tr>
				<td>&nbsp;&nbsp;Contributi in Euro <br>(solo nei casi in cui è previsto <br> dal D.Lgs 194/2008)</td>
					<td>&nbsp;&nbsp;<%=TicketDetails.getContributi_rilascio_certificazione()%></td>
				</tr>
		</table>
</td>
</tr>

<%	
	
	
}



if (TicketDetails.getTipoIspezione().containsKey(16))
{
	%>
	<tr class="containerBody">
	<td nowrap class="formLabel">Motivi Tossinfezione</td>

	<td>
	<table class = "noborder">
	<%
	if (TicketDetails.getSoggettiCoinvolti()!=null)
	{
		%>
		<tr><td>Soggetti Coinvolti</td><td><%=TicketDetails.getSoggettiCoinvolti() %></td></tr>
	
		<%
	}
	if (TicketDetails.getRicoverati()!=null)
	{
		%>
		<tr><td>di cui Ricoverati</td><td><%=TicketDetails.getRicoverati() %></td></tr>
	
		<%
		
	}
	if (TicketDetails.getAlimentiSospetti()!=null)
	{
		%>
		<tr><td>Alimenti Sospetti</td><td><%=TicketDetails.getAlimentiSospetti() %></td></tr>
	
		<%
		
	}
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	if (TicketDetails.getDataSintomi()!=null)
	{
		
		%>
		<tr><td>Data Sintomi</td><td><%=sdf.format(new java.sql.Date(TicketDetails.getDataSintomi().getTime())) %></td></tr>
	
		<%
		
	}
	if (TicketDetails.getDataPasto()!=null)
	{
		
		%>
		<tr><td>Data Ingestione Pasto Sospetto</td><td><%=sdf.format(new java.sql.Date(TicketDetails.getDataPasto().getTime())) %></td></tr>
	
		<%
		
	}
	%>
	</table>
	</td>
	</tr>
	<%
}


if (TicketDetails.getTipoIspezione().containsKey(20))
{
	%>
	<tr id="svincolisanitari"  >
		<td class = "formLabel" >
			Svincoli Sanitari
		<td>
		<table class = "noborder">
				<tr>
				<td>Data Preavviso</td>
					<td>
					<%=TicketDetails.getData_preavvisoasString() %>
					</td>
					<td>
					Protocollo Preavviso
					</td>
					<td >
					<%=TicketDetails.getProtocollo_preavviso() %>
					</td>
					
				</tr>
				<tr>
				<td>Data Comunicazione Svincolo</td>
					<td>
						<%=TicketDetails.getComuni2cazione_svincoloasString() %>
					</td>
					<td>
					Protocollo Svincolo
					</td>
					<td >
					<%=TicketDetails.getProtocollo_svincolo() %>
					</td>
					
				</tr>
					<tr>
				<td>Tipologia Sottoprodotto</td>
					<td>
						<%=TicketDetails.getTipologia_sottoprodotto() %>
					</td>
					<td>
					Peso
					</td>
					<td >
						<%=TicketDetails.getPeso() %>
					</td>
					
				</tr>
		</table>
</td>
</tr>
	
	<%
	
}
		
%>
		
		
<%
if(TicketDetails.getTipoIspezione().containsKey(2)) // in piano di monitoraggio
  {
    			
    Iterator<Integer> kiave=TicketDetails.getLisaElementipianoMonitoraggio_ispezioni().keySet().iterator();
    			
    			
    			%>
    			
 <tr class="containerBody">
	<td nowrap class="formLabel">Lista Piani</td>

	<td>
	<table class = "noborder">
	<%
	int i = 1 ;
	while(kiave.hasNext())
	{
		
		
		%>
		<tr><td><%=i+") "+TicketDetails.getLisaElementipianoMonitoraggio_ispezioni().get(kiave.next()) %></td></tr>
		<%
		i++;
	}
	
	%>
	
	
	</table>
	
	</td>
</tr>
<%
}
%>

	<%if(TicketDetails.getTipoIspezione().containsKey(7)){ %>

<tr class="containerBody">
	<td nowrap class="formLabel">Sistema Allarme Rapido</td>
		<td>
		<table class= "noborder">
		<tr>
		<td>Codice Allerta</td>
		<td><%=TicketDetails.getCodiceAllerta() %>&nbsp;&nbsp;</td>
		<td>&nbsp;&nbsp;
		Contributi in Euro <br>(solo nei casi in cui è previsto <br> dal D.Lgs 194/2008) 
		</td>
		<td >
		<%=TicketDetails.getContributi_allarme_rapido()%>
		</td>
		
		</tr>
		
		</table>
		
		</td>
		
		
</tr>

<%} %>

	<%if(TicketDetails.getTipoIspezione().containsKey(10)){ %>

<tr class="containerBody">
	<td nowrap class="formLabel">Controllo Importazione Scambio</td>
		<td>
		<table class = "noborder">
				<tr>
				<td>&nbsp;&nbsp;Contributi in Euro <br>(solo nei casi in cui è previsto <br> dal D.Lgs 194/2008)</td>
					<td>&nbsp;&nbsp;<%=TicketDetails.getContributi_importazione_scambio()%></td>
				</tr>
		</table>
		</td>
		
</tr>

<%} %>

<%if(TicketDetails.getTipoIspezione().containsKey(8)){ %>

<tr class="containerBody">
	<td nowrap class="formLabel">
			Verifica Risoluzione N.C Precedenti 
		<td>
		<table class = "noborder">
				<tr>
				<td>Le azioni correttive <br> risultano adeguate ed efficaci ?</td>
			<td>SI <input type="radio" name="azione" disabled="disabled"
				<%if (TicketDetails.isAzione()){ %> checked="checked" <%} %>>
			</td>
			<td>NO <input type="radio" <%if (!TicketDetails.isAzione()){ %>
				checked="checked" <%} %> disabled="disabled">
			</td>
			<td >
			Contributi in
			Euro <br> </td>
			<td >&nbsp;&nbsp;
			<%=TicketDetails.getContributi_verifica_risoluzione_nc()%>
			</td>	
				
		</tr>

		<%if (TicketDetails.isAzione()) { %>
		<tr id="desc1">
			<td>Descrizione :</td>
			<td><%=TicketDetails.getAzione_descrizione() %></td>
			<td>&nbsp;</td>
		</tr>

		<%} %>
	</table>
	</td>
</tr>

<%} %>


<%if(TicketDetails.getTipoIspezione().containsKey(14)){ %>

<tr id="macellazione">
		<td nowrap class="formLabel">
			Macellazione Privata
		<td>
		<table class = "noborder">
				<tr>
				<td>Contributi in Euro <br></td>
					<td>&nbsp;&nbsp;<%=TicketDetails.getContributi_macellazione()%></td>
				</tr>
		</table>
</td>
</tr>




<%
}
%>

<%if(TicketDetails.getTipoIspezione().containsKey(25)){ %>

<tr id="macellazione_urgenza">
		<td nowrap class="formLabel">
			Macellazione D'Urgenza
		<td>
		<table class = "noborder">
				<tr>
				<td>Contributi in Euro <br></td>
					<td>&nbsp;&nbsp;<%=TicketDetails.getContributi_macellazione_urgenza()%></td>
				</tr>
		</table>
</td>
</tr>




<%
}
%>

<%if(TicketDetails.getTipoIspezione().containsKey(1)){ %>
<tr id="seguitodicampionamento" >
		<td nowrap class="formLabel">
			A seguito di Campioni/tamponi nc
		<td>
		<table class = "noborder">
				<tr>
				<td>&nbsp;&nbsp;Contributi in Euro <br>(solo nei casi in cui è previsto <br> dal D.Lgs 194/2008)</td>
					<td>&nbsp;&nbsp;<%=TicketDetails.getContributi_seguito_campioni_tamponi()%></td>
				</tr>
		</table>
</td>
</tr>


<%
}	 

	 if(TicketDetails.isCategoriaisAggiornata()==false){
	 if(TicketDetails.getClosed()==null){
	 %>

<%if(TicketDetails.getTipoCampione()==5){ 
	 		
            		%>
<tr class="containerBody">
	<td name="accountSize1" id="accountSize1" nowrap class="formLabel">
	<dhv:label name="osa.categoriaRischioo" />Scegli Tipo Check List</td>
	<td><%= OrgCategoriaRischioList2.getHtmlSelect("accountSize",-1) %>
	<%
    UserBean entered = (UserBean)session.getAttribute("User");
        if(TicketDetails.getNumeroAudit() == 0)
        	{%> <input type="button" value="Compila Checklist Principale"
		name="CompilaChecklistPrincipale"
		onClick="if(document.getElementById('accountSize').value=='-1'){alert('Selezionare il tipo di checklist')}else{controlloChecklist('<%="Sei sicuro che la CheckList Selezionata sia quella principale  ? "%>','1',<%=entered.getUserId() %>)}" />
	<%}
        else
        {
        %> <input type="button" value="Compila Checklist Secondaria"
		name="Save"
		onClick="if(document.getElementById('accountSize').value=='-1'){alert('Selezionare il tipo di checklist')}else{controlloChecklist('Stai per compilare una checklist successiva alla prima. Continuare ?','2',<%=entered.getUserId() %>)}" />

	<%} %>
	</td>
</tr>
<%
	 String checklistInserite = "";
	 Iterator<Audit> it = Audit.iterator();
	 while (it.hasNext()){
	 	Audit a = it.next();
	 	checklistInserite+=a.getTipoChecklist()+";";
	 			
		}
	 %>
<input type="hidden" name="checklist_inserite" id="checklist_inserite"
	value="<%=checklistInserite %>">
<%
	 
	 } %>

<%} }%>
<tr class="containerBody">
	<td nowrap class="formLabel"><dhv:label
		name="campioni.data_richiesta">Data Inizio Controllo</dhv:label></td>
	<td><zeroio:tz timestamp="<%= TicketDetails.getAssignedDate() %>"
		dateOnly="true"
		timeZone="<%= TicketDetails.getAssignedDateTimeZone() %>"
		showTimeZone="false" default="&nbsp;" /></td>
</tr>





<%if(TicketDetails.getDataFineControllo()!=null){ %>

<tr class="containerBody">
	<td nowrap class="formLabel"><dhv:label name="">Data Fine Controllo</dhv:label>
	</td>
	<td><%= (new SimpleDateFormat( "dd/MM/yyyy" )).format(TicketDetails.getDataFineControllo().getTime()) %>
	</td>
</tr>
<%} %>

<%if(TicketDetails.getTipoCampione()==4){ %>
<tr class="containerBody">
	<td valign="top" class="formLabel"><dhv:label name="sanzioni.note">Oggetto del Controllo</dhv:label>
	</td>
	<td>

	<table class="noborder">
		<tr>
			<td>
			<%  String  ispezioni="<b></b> <br>";
    				Iterator<Integer> kiave=TicketDetails.getLisaElementi_Ispezioni().keySet().iterator();
    				String ispezioneSel="";
    				while(kiave.hasNext()){
    					
    					int key = kiave.next();
    					
    					out.print("<b><font color='blue'> "+IspezioneMacrocategorie.getValueFromId(key)+"</font></b><br> ") ;
    					
    					HashMap<Integer,String> lista = TicketDetails.getLisaElementi_Ispezioni().get(key);
    					
    					Iterator<Integer> kiave1= lista.keySet().iterator();
    					
    					while(kiave1.hasNext()){
    						
    						out.println(lista.get(kiave1.next())+"<br>");
    					}
    					
    					
    	    				
    	    		}%>
			</td>
			<td>&nbsp;</td>
			<td>
			<%
     if (!"".equals(TicketDetails.getIspezioni_desc1()) && TicketDetails.getIspezioni_desc1()!=null)
		{
			out.print("<br><b>Settore Alimenti per il consumo Umano (Descrizione): </b><br>"+TicketDetails.getIspezioni_desc1());
			
		}
		if (!"".equals(TicketDetails.getIspezioni_desc2()) && TicketDetails.getIspezioni_desc2()!=null)
		{
			out.print("<br><b>Settore alimenti Zootecnici (Descrizione): </b><br>"+TicketDetails.getIspezioni_desc2());
			
		}
		if (!"".equals(TicketDetails.getIspezioni_desc3()) && TicketDetails.getIspezioni_desc3()!=null)
		{

			out.print("<br><b>Note Settore Benessere Animale non durante il trasporto : </b><br> "+TicketDetails.getIspezioni_desc3());

			out.print("<br><b>Settore Benessere Animale (Descrizione): </b><br> "+TicketDetails.getIspezioni_desc3());

			
		}
		if (!"".equals(TicketDetails.getIspezioni_desc4()) && TicketDetails.getIspezioni_desc4()!=null)
		{
			out.print("<br><b>Settore Sanita animale (Descrizione): </b><br>"+TicketDetails.getIspezioni_desc4());
			
		}
		if (!"".equals(TicketDetails.getIspezioni_desc5()) && TicketDetails.getIspezioni_desc5()!=null)
		{
			out.print("<br><b>Settore S.O.A. negli Impianti di trasformazione (Descrizione): </b><br>"+TicketDetails.getIspezioni_desc5());
			
		}
		if (!"".equals(TicketDetails.getIspezioni_desc6()) && TicketDetails.getIspezioni_desc6()!=null)
		{
			out.print("<br><b>Settore Rifiuti S.O.A. nelle altre imprese (Descrizione): </b><br>"+TicketDetails.getIspezioni_desc6());
			
		}
		if (!"".equals(TicketDetails.getIspezioni_desc7()) && TicketDetails.getIspezioni_desc7()!=null)
		{
			out.print("<br><b>Altro (Descrizione): </b><br>"+TicketDetails.getIspezioni_desc7());
			
		}
		if (!"".equals(TicketDetails.getIspezioni_desc8()) && TicketDetails.getIspezioni_desc8()!=null)
		{
			out.print("<br><b>Note Settore Benessere Animale durante il trasporto : </b><br>"+TicketDetails.getIspezioni_desc8());
			
		}
		%>
			</td>
		</tr>


	</table>


	</td>
</tr>
<%} %>

<!-- TRASPORTI NUOVA GESTIONE -->
<% HashMap <Integer,String> specie = TicketDetails.getListaAnimali_Ispezioni(); %>
<% if (specie.size() != 0) { %>
<tr class="containerBody">
	<td valign="top" class="formLabel"><dhv:label name="sanzioni.note">Specie Animali Trasportati</dhv:label>
	</td>
	<td>
	<table class="noborder">
		<tr>
			<td>
			<%
    //  HashMap <Integer,String> specie = TicketDetails.getListaAnimali_Ispezioni();
      Iterator<Integer> itSpecie = specie.keySet().iterator();
      while(itSpecie.hasNext())
      {
    		int key = itSpecie.next();
    		String value = TicketDetails.getListaAnimali_Ispezioni().get(key);
   			out.println(" - "+ value +"<br/>");
       }
      %>
			</td>
			<td>&nbsp;</td>
			<td>
			<% if (TicketDetails.getNum_specie1() != -1)
		{
			out.print("<br><b> Num. bovini: </b>"+TicketDetails.getNum_specie1());
			
		}
		if (TicketDetails.getNum_specie2() != -1)
		{
			out.print("<br><b> Num. suini: </b>" + TicketDetails.getNum_specie2());
			
		}
		if ( TicketDetails.getNum_specie3() != -1)
		{
			out.print("<br><b> Num. equidi: </b>"+TicketDetails.getNum_specie3());
			
		}
		if (TicketDetails.getNum_specie4() != -1)
		{
			out.print("<br><b> Num. Altre specie: </b>"+TicketDetails.getNum_specie4());
			
		}
		if (TicketDetails.getNum_specie5() != -1)
		{
			out.print("<br><b> Num. Bufali : </b>"+TicketDetails.getNum_specie5());
			
		}
		if (TicketDetails.getNum_specie6() != -1)
		{
			out.print("<br><b> Num. Pesci di acqua dolce: </b>"+TicketDetails.getNum_specie6());
			
		}
		if (TicketDetails.getNum_specie7() != -1)
		{
			out.print("<br><b> Num. Pesci ornamentali : </b>"+TicketDetails.getNum_specie7());
			
		}
		if (TicketDetails.getNum_specie8() != -1)
		{
			out.print("<br><b> Num. Oche: </b>"+TicketDetails.getNum_specie8());
			
		}
		if (TicketDetails.getNum_specie9() != -1)
		{
			out.print("<br><b> Num. Conigli: </b>"+TicketDetails.getNum_specie9());
			
		}
		if (TicketDetails.getNum_specie10() != -1)
		{
			out.print("<br><b> Num. Ovaiole: </b>"+TicketDetails.getNum_specie10());
			
		}
		if (TicketDetails.getNum_specie11() != -1)
		{
			out.print("<br><b> Num. Broiler: </b>"+TicketDetails.getNum_specie11());
			
		}
		if (TicketDetails.getNum_specie12() != -1)
		{
			out.print("<br><b> Num. Vitelli: </b>"+TicketDetails.getNum_specie12());
			
		}
		if (TicketDetails.getNum_specie13() != -1)
		{
			out.print("<br><b> Num. Struzzi: </b>"+TicketDetails.getNum_specie13());
			
		}
		if (TicketDetails.getNum_specie14() != -1)
		{
			out.print("<br><b> Num. Cani: </b>"+TicketDetails.getNum_specie14());
			
		}
		if (TicketDetails.getNum_specie15() != -1)
		{
			out.print("<br><b> Num. Ovicaprini: </b>"+TicketDetails.getNum_specie15());
			
		}
	
     %>
			</td>
		</tr>

	</table>

	</td>
</tr>
<% } %>

<dhv:evaluate if="<%= hasText(TicketDetails.getProblem()) %>">
	<tr class="containerBody">
		<td valign="top" class="formLabel"><dhv:label
			name="sanzioni.note">Note</dhv:label></td>
		<td><%= toString(TicketDetails.getProblem()) %></td>
	</tr>
</dhv:evaluate>

  
  <!-- aggiunto da d.dauria -->
  <% if (((TicketDetails.getNucleoIspettivo() > -1) && (TicketDetails.getComponenteNucleo() != "")   ) || ((TicketDetails.getNucleoIspettivoDue() > -1) && (TicketDetails.getComponenteNucleoDue() != "")) || ((TicketDetails.getNucleoIspettivoTre() > -1) && (TicketDetails.getComponenteNucleoTre() != "")) || ((TicketDetails.getNucleoIspettivoQuattro() > -1) && (TicketDetails.getComponenteNucleoQuattro() != "")) || ((TicketDetails.getNucleoIspettivoCinque() > -1) && (TicketDetails.getComponenteNucleoCinque() != "")) || ((TicketDetails.getNucleoIspettivoSei() > -1) && (TicketDetails.getComponenteNucleoSei() != "")) || ((TicketDetails.getNucleoIspettivoSette() > -1) && (TicketDetails.getComponenteNucleoSette() != "")) || ((TicketDetails.getNucleoIspettivoOtto() > -1) && (TicketDetails.getComponenteNucleoOtto() != "")) || ((TicketDetails.getNucleoIspettivoNove() > -1) && (TicketDetails.getComponenteNucleoNove() != "")) || ((TicketDetails.getNucleoIspettivoDieci() > -1) && (TicketDetails.getComponenteNucleoDieci() != "")) ){%>
    <tr class="containerBody" >
   <td name="" class="formLabel">
     <dhv:label name="">Nucleo Ispettivo</dhv:label>
   </td>
   <td>
   <% if((TicketDetails.getNucleoIspettivo() > -1) && (TicketDetails.getComponenteNucleo() != "")) {%>
    <b> <%=TitoloNucleo.getSelectedValue(TicketDetails.getNucleoIspettivo())%>:</b>
    <%=TicketDetails.getComponenteNucleo() %>
    <%=(TicketDetails.getUo_uno()>0)?"<b>Per conto di</b>"+OiaNodo.load(TicketDetails.getUo_uno()+"").getNome()+"<br>":"<br>" %>
   <% } %>
   <% if(TicketDetails.getNucleoIspettivoDue() > -1) {%>
 	  <b><%=TitoloNucleoDue.getSelectedValue(TicketDetails.getNucleoIspettivoDue())%>:</b>
 	 <%=TicketDetails.getComponenteNucleoDue() %>
 	     <%=(TicketDetails.getUo_due()>0)? "<b>Per conto di</b>"+OiaNodo.load(TicketDetails.getUo_due()+"").getNome()+"<br>" :"<br>"%>
 	 
   <% } %> 
   <% if(TicketDetails.getNucleoIspettivoTre() > -1) {%>
 	 <b><%=TitoloNucleoTre.getSelectedValue(TicketDetails.getNucleoIspettivoTre())%>:</b>
 	 <%= TicketDetails.getComponenteNucleoTre() %>
 	     <%=(TicketDetails.getUo_tre()>0)?"<b>Per conto di</b>"+OiaNodo.load(TicketDetails.getUo_tre()+"").getNome()+"<br>" :"<br>"%>
 	 
   <% } %> 
   <% if(TicketDetails.getNucleoIspettivoQuattro() > -1) {%>
 	 <b><%=TitoloNucleoQuattro.getSelectedValue(TicketDetails.getNucleoIspettivoQuattro())%>:</b>
 	 <%= TicketDetails.getComponenteNucleoQuattro() %>
 	     <%=(TicketDetails.getUo_quattro()>0)?"<b>Per conto di</b>"+OiaNodo.load(TicketDetails.getUo_quattro()+"").getNome()+"<br>":"<br>" %>
 	 
   <% } %>   					 
   <% if(TicketDetails.getNucleoIspettivoCinque() > -1) {%>
 	  <b><%=TitoloNucleoCinque.getSelectedValue(TicketDetails.getNucleoIspettivoCinque())%>:</b>
 	 <%= TicketDetails.getComponenteNucleoCinque() %>
 	     <%=(TicketDetails.getUo_cinque()>0)?"<b>Per conto di</b>"+OiaNodo.load(TicketDetails.getUo_cinque()+"").getNome()+"<br>" :"<br>"%>
 	 
   <% } %>  
      <% if(TicketDetails.getNucleoIspettivoSei() > -1) {%>
 	  <b><%=TitoloNucleoSei.getSelectedValue(TicketDetails.getNucleoIspettivoSei())%>:</b>
 	 <%= TicketDetails.getComponenteNucleoSei() %>
 	     <%=(TicketDetails.getUo_sei()>0)?"<b>Per conto di</b>"+OiaNodo.load(TicketDetails.getUo_sei()+"").getNome()+"<br>":"<br>" %>
 	 
   <% } %>  
      <% if(TicketDetails.getNucleoIspettivoSette() > -1) {%>
 	  <b><%=TitoloNucleoSette.getSelectedValue(TicketDetails.getNucleoIspettivoSette())%>:</b>
 	 <%= TicketDetails.getComponenteNucleoSette() %>
 	     <%=(TicketDetails.getUo_sette()>0)? "<b>Per conto di</b>"+OiaNodo.load(TicketDetails.getUo_sette()+"").getNome()+"<br>" :"<br>"%>
 	 
   <% } %>  
      <% if(TicketDetails.getNucleoIspettivoOtto() > -1) {%>
 	  <b><%=TitoloNucleoOtto.getSelectedValue(TicketDetails.getNucleoIspettivoOtto())%>:</b>
 	 <%= TicketDetails.getComponenteNucleoOtto() %>
 	     <%=(TicketDetails.getUo_otto()>0)? "<b>Per conto di</b>"+OiaNodo.load(TicketDetails.getUo_otto()+"").getNome()+"<br>":"<br>" %>
 	 
   <% } %>  
      <% if(TicketDetails.getNucleoIspettivoNove() > -1) {%>
 	  <b><%=TitoloNucleoNove.getSelectedValue(TicketDetails.getNucleoIspettivoNove())%>:</b>
 	 <%= TicketDetails.getComponenteNucleoNove() %>
 	     <%=(TicketDetails.getUo_nove()>0)? "<b>Per conto di</b>"+OiaNodo.load(TicketDetails.getUo_nove()+"").getNome()+"<br>":"<br>" %>
 	 
   <% } %>  
      <% if(TicketDetails.getNucleoIspettivoDieci() > -1) {%>
 	  <b><%=TitoloNucleoDieci.getSelectedValue(TicketDetails.getNucleoIspettivoDieci())%>:</b>
 	 <%= TicketDetails.getComponenteNucleoDieci() %>
 	     <%=(TicketDetails.getUo_dieci()>0)? "<b>Per conto di</b>"+OiaNodo.load(TicketDetails.getUo_dieci()+"").getNome()+"<br>":"<br>" %>
 	 
   <% } %>  
  </td>
  </tr>
<%} %> 

<%
if(TicketDetails.getTipoCampione()==5 && TicketDetails.isCategoriaisAggiornata()==false)
{
%>
<input id="pendenti" value="Visualizza checklist pendenti" type="button">
<br/>
<br/>
<div id="label"></div>
<div id="elenco"></div>

<br/>
<br/>
<%} %>











