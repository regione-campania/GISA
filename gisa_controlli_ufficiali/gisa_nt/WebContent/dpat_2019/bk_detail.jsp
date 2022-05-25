<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@page import="org.aspcfs.modules.dpat2019.base.*"%>
<%@ page import="com.darkhorseventures.framework.actions.ActionContext"%>
<jsp:useBean id="dpat" class="org.aspcfs.modules.dpat2019.base.Dpat" scope="request"/>
<jsp:useBean id="dsiList" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="edit" class="java.lang.String" scope="request"/>
<jsp:useBean id="anno" class="java.lang.String" scope="request"/>
<jsp:useBean id="idPadre" class="java.lang.String" scope="request"/>


<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>

<body onload="resizeGlobalItemsPane('hide')">
<%@ include file="../initPage.jsp"%>
<table class="trails" cellspacing="0">
		<tr>
			<td width="100%"><a href="Oia.do">Modellatore Organizzazione ASL</a> &gt <a href="dpat2019.do?&idAsl=<%=dpat.getIdAsl()%>&idPadre=<%=idPadre%>&anno=<%=anno%>">DPAT</a> &gt <a href="DpatSDC2019.do?command=AddModify&idAsl=<%=dpat.getIdAsl()%>&edit=view">Strumento di calcolo</a> &gt Carichi di lavoro</td>	
		</tr>
	</table>
<head>
	<script type="text/javascript" src="javascript/jquery-2.0.0.min.js"></script>
	<link href="css/smart_wizard.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="javascript/jquery.smartWizard.js"></script>
	
	<script type="text/javascript">
	loadModalWindow();
	</script>
	
<style type="text/css">
	td.tooltip {
		
		
	}
	
	td.tooltip span {
		position: absolute;
		width:140px;
		padding: 6px;		
		background: #000;
		color: #fff;
		text-align: center;
		visibility: hidden;
		border-radius: 5px;
	} 
			
	td.tooltipss span:after {
		content: '';
		position: absolute;
		top: 100%;
		left: 50%;
		margin-left: -8px;
		width: 0; height: 0;
		border-top: 8px solid black;
		border-right: 8px solid transparent;
		border-left: 8px solid transparent;
	}
			
	td:hover.tooltip span {
		visibility: visible;
		opacity: 0.8;
		z-index: 999;
	}
	

   td:	
	
   td:hover.tooltip span {
		visibility: visible;
		opacity: 0.8;
		z-index: 999;
	}
	
	
	td{
 	text-align:center; 
    vertical-align:middle;
	}
	
		
	input { /* Stili per il campo di testo e per la textarea */
    background: #fff; /*Colore di sfondo */
    border: 0px solid #323232; /* Bordo */
    color: #000; /* Colore del testo */
    height: 30px; /* Altezza */
    line-height: 30px; /* Altezza di riga */
    width: 300px; /* Larghezza */
    padding: 0 5px; /* Padding */
	}
	
	
	[data-tip] {
	position:relative;
	width:180px;

}
[data-tip]:before {
	content:'';
	/* hides the tooltip when not hovered */
	display:none;
	position:absolute;
	top:30px;
	left:35px;
	z-index:8;
	width:180px;
	height:0;
}
[data-tip]:after {
	display:none;
	content:attr(data-tip);
	position:absolute;
	top:35px;
	left:0px;
	padding:5px 8px;
	background:#1a1a1a;
	color:#fff;
	z-index:9;
	width:180px;
}
[data-tip]:hover:before,
[data-tip]:hover:after {
	display:block;
}
</style>
	
	
	
</head>


<script type="text/javascript">
  $(document).ready(function() {
      // Initialize Smart Wizard
      loadModalWindowUnlock();
      
      $('#wizard').smartWizard();
      
     
      // PER ABILITARE TUTTE LE SEZIONI ALLO STARTUP
      //  $('#wizard').smartWizard({enableAllSteps:'true'});
  }); 
  
</script>
<% if (dpat.getElencoSezioni().size()>0){ %>
<div id="wizard" class="swMain">
  <%ArrayList<String> colori = new ArrayList<String>();
  	colori.add(0, "#ABDC53"); //SEZ A
  	colori.add(1, "#00BFFF"); //SEZ B 	
  	colori.add(2, "#DA70D6"); //SEZ C
  	colori.add(3, "#FFBF00"); //SEZ D
  	colori.add(4, "#964B00"); //SEZ E  	
  	
  	for(int i=0;i< dpat.getElencoSezioni().size();i++){ 
  		DpatSezione sezione = (DpatSezione)dpat.getElencoSezioni().get(i);%>
		<div id="s_<%=sezione.getId()%>">
			<table border="1" style="width:100%;" class="tocolorate">
			<thead>
				<tr>
					<th rowspan="3">STRUTTURA</th>
					<th rowspan="3">CARICO DI LAVORO EFFETTIVO ANNUALE MINIMO DI STRUTTURA IN U.I.</th>
					<% for (int j=0;j<sezione.getElencoPiani().size();j++){ 
					 	 DpatPiano piano = (DpatPiano)sezione.getElencoPiani().get(j);
					 	 int numInd=0;
					 	 for (int app=0; app<piano.getElencoAttivita().size();app++){
					 	 	DpatAttivita att = (DpatAttivita)piano.getElencoAttivita().get(app);
					 	 	numInd=numInd+(att.getElencoIndicatori().size()+1);%>
					 	 <%} %>
					 	 <th bgcolor="<%=colori.get(i)%>" id="p_<%=piano.getId()%>" colspan="<%=numInd%>" >
					 	 	<%=piano.getDescription()%>
					 	 </th>
					<% }%>
					<th rowspan="3" style="width : 100px" bgcolor="#FF0000">SALDO TRA LE U.I. MINIME STABILITE E QUELLE DESTINATE ALL'EFFETTUAZIONE DELLE ATTIVITA'</th>
				</tr>
				<tr>
				<% for (int j=0;j<sezione.getElencoPiani().size();j++){
					for (int k=0; k<sezione.getElencoPiani().get(j).getElencoAttivita().size(); k++){
					  DpatAttivita attivita = sezione.getElencoPiani().get(j).getElencoAttivita().get(k);%>
					  <td class="tooltip" bgcolor="<%=colori.get(i)%>" id="a_<%=attivita.getId()%>" colspan="<%=sezione.getElencoPiani().get(j).getElencoAttivita().get(k).getElencoIndicatori().size()+1%>">
					  <span><%= attivita.getDescription()%></span>	<%=(attivita.getDescription().indexOf(' ', 60) > -1) ? attivita.getDescription().substring(0, attivita.getDescription().indexOf(' ', 60)) 
					  			+ " <font size=\"1\">[...]</font>" : attivita.getDescription()%>
					  </td>
				<% 	}} %>
				</tr>
		 		<tr>
				<% for (int j=0;j<sezione.getElencoPiani().size();j++){
					for (int k=0; k<sezione.getElencoPiani().get(j).getElencoAttivita().size(); k++){
					  for(int ind=0;ind<sezione.getElencoPiani().get(j).getElencoAttivita().get(k).getElencoIndicatori().size();ind++){
					  	DpatIndicatore indicatore = sezione.getElencoPiani().get(j).getElencoAttivita().get(k).getElencoIndicatori().get(ind);%>
					  	<td class="tooltip" bgcolor="<%=colori.get(i)%>" id="i_<%=indicatore.getId()%>" colspan="1">
					  	 <span><%= indicatore.getDescription()%></span>
					  	<%=(indicatore.getDescription().indexOf(' ', 60) > -1) ? 
					  indicatore.getDescription().substring(0, indicatore.getDescription().indexOf(' ', 60)) + " <font size=\"1\">[...]</font>" : indicatore.getDescription()%></td>
				<% 	  }  %> 
					  <td bgcolor="<%=colori.get(i)%>" colspan="1">U.I.</td>
			<%	}} %>
				</tr>
				</thead>
				<tbody>
			<% for (int s=0;s<dpat.getElencoStrutture().size();s++) {
					String color="#FFFFFF";
					String color_tr="#FFFFFF";
					DpatStruttura struttura = (DpatStruttura)dpat.getElencoStrutture().get(s);
					if (struttura.getN_livello()==2) {color="#FFFF00";}
					else {color="#FFFFFF";}
					if (s%2 == 0)
						color_tr = "#fff";
					else 
						color_tr = "#eee";
					
					%>
					<tr bgcolor="<%=color_tr%>">
						<td class="tooltip" bgcolor="<%=color%>" id="struttura_<%=struttura.getId()%>">
						<span><%=struttura.getDescrizione_lunga() %></span>
						<%=(struttura.getDescrizione_lunga().indexOf(' ', 60) > -1) ? 
								struttura.getDescrizione_lunga().substring(0, struttura.getDescrizione_lunga().indexOf(' ', 60)) + " <font size=\"1\">[...]</font>" : struttura.getDescrizione_lunga()%></td>
						<td><input type="text" 
								   style="width: 30px"
								   onchange="javascript: if (checkInt(this.id)==0){setElencoIdCelleCarichi(this.id,this.name,this.value);} else {alert('Inserire un valore intero');}" 
								   name="struttura_carico_<%=struttura.getId()%>"
								   id="struttura_carico_<%=struttura.getId()%>_i_<%=i%>" value="<%=struttura.getCaricoInUi()%>" disabled/></td>
						<% for (int j=0;j<sezione.getElencoPiani().size();j++){
								DpatPiano p = (DpatPiano)sezione.getElencoPiani().get(j);
							for (int k=0; k<sezione.getElencoPiani().get(j).getElencoAttivita().size(); k++){ 
								DpatAttivita a = (DpatAttivita)sezione.getElencoPiani().get(j).getElencoAttivita().get(k);
							 for(int ind=0;ind<sezione.getElencoPiani().get(j).getElencoAttivita().get(k).getElencoIndicatori().size();ind++){
							 	DpatIndicatore in = (DpatIndicatore)sezione.getElencoPiani().get(j).getElencoAttivita().get(k).getElencoIndicatori().get(ind);%>
							 	<td  <%if (in.getUiCalcolabile()==false) {%> bgcolor="#000000" <%}%>>
							 	<span data-tip="<%=struttura.getDescrizione_lunga() %> / <%=a.getDescription() %> / <%=in.getDescription() %>">	<input type="text" 
							 			value="0"
							 			<% if (in.getUiCalcolabile()==true){
							 					if (a.getUiCalcolabile()==true) {%>
							 						style="width:30px"
							 						onchange="javascript: if (checkInt(this.id)==0){setElencoIdCelleIndicatori(this.id,0);} else {alert('Inserire un valore intero');}"
							 				<% } else { %>
							 						style="width:30px"
							 						onchange="javascript: if (checkInt(this.id)==0){setElencoIdCelleIndicatori(this.id,1);} else {alert('Inserire un valore intero');}"
							 				<% } 
							 			  } else { %>
							 			  		onchange=""
							 			  		style="display:none"
							 			  <% } %>
							 			id="struttura_<%=struttura.getId()%>_s_<%=sezione.getId()%>_p_<%=p.getId()%>_a_<%=a.getId()%>_i_<%=in.getId()%>" /></span>
							 	</td>						
						<% 	 } %>
					 			<td <% if (a.getUiCalcolabile()==true){ %>
					 						bgcolor="#A9A9A9" 
					 				<%} else { %> 
					 						bgcolor="#000000" 
					 				<%} %>
					 				id="struttura_<%=struttura.getId()%>_s_<%=sezione.getId()%>_p_<%=p.getId()%>_a_<%=a.getId()%>_somma">0</td>
						<%	 }} %>
						
						<td id="struttura_<%=struttura.getId()%>_saldo_i_<%=i%>"><%=Math.round(struttura.getSaldo())%></td>
					</tr>
				
			<% } %>
			
			<tr bgcolor="#CEF6F5">
				<td>CARICO DI LAVORO (ESPRESSO IN U.I.) ANNUALE MINIMO TOTALE DELL'ASL</td>
				<td id="tot_carico_i_<%=i%>"><%=dpat.getCarico_in_ui()%></td>
			<% for (int j=0;j<sezione.getElencoPiani().size();j++){
				DpatPiano p = (DpatPiano)sezione.getElencoPiani().get(j);
				for (int k=0; k<sezione.getElencoPiani().get(j).getElencoAttivita().size(); k++){ 
				 DpatAttivita a = (DpatAttivita)sezione.getElencoPiani().get(j).getElencoAttivita().get(k);
				 for(int ind=0;ind<sezione.getElencoPiani().get(j).getElencoAttivita().get(k).getElencoIndicatori().size();ind++){
				  DpatIndicatore in = (DpatIndicatore)sezione.getElencoPiani().get(j).getElencoAttivita().get(k).getElencoIndicatori().get(ind);%>
					<td id="i_<%=in.getId()%>_tot"><%=(int)sezione.getElencoPiani().get(j).getElencoAttivita().get(k).getElencoIndicatori().get(ind).getCarico_in_ui()%></td>				
				<%}%>
				
				 	<td id="a_<%=a.getId()%>_tot"><%=(int)sezione.getElencoPiani().get(j).getElencoAttivita().get(k).getUi()%></td>
			<%	 }} %>
			<td id="tot_sal_i_<%=i%>"><%=Math.round(dpat.getSaldo())%></td>
			</tr>
			<tr bgcolor="#FFDEAD"> 
				<td>OBIETTIVO ASSEGNATO/PREVISTO DALLA REGIONE</td>
				<td><input disabled
							style="width: 30px;" 
							type="text" 
							id="ob_tot_<%=i%>" 
							value="<%=dpat.getObiettivo_in_ui()%>"
							onchange="javascript: if (checkInt(this.id)==0){updateObiettivoTot(this.id);} else {alert('Inserire un valore intero');}"/></td>
				<% for (int j=0;j<sezione.getElencoPiani().size();j++){
				DpatPiano p = (DpatPiano)sezione.getElencoPiani().get(j);
				for (int k=0; k<sezione.getElencoPiani().get(j).getElencoAttivita().size(); k++){ 
				 DpatAttivita a = (DpatAttivita)sezione.getElencoPiani().get(j).getElencoAttivita().get(k);
				 for(int ind=0;ind<sezione.getElencoPiani().get(j).getElencoAttivita().get(k).getElencoIndicatori().size();ind++){
				  DpatIndicatore in = (DpatIndicatore)sezione.getElencoPiani().get(j).getElencoAttivita().get(k).getElencoIndicatori().get(ind);%>
					<td><input disabled 
								style="width: 30px;" 
								type="text" id="ob_i_<%=in.getId()%>"
								onchange="javascript: if (checkInt(this.id)==0){updateObiettivoParz(this.id);} else {alert('Inserire un valore intero');}"
								value="<%=(int)sezione.getElencoPiani().get(j).getElencoAttivita().get(k).getElencoIndicatori().get(ind).getObiettivo_in_ui()%>"/></td>				
				<%}%>
				 	<td></td>
			<%	 }} %>
			</tr>
			</tbody>
			</table>   
		</div>	
   <% } %>
   <%-- 
   		<div id="saldo">
   		<table border="1" style="width : 200px">
   		<tr>
   			<th style="width : 100px">STRUTTURA</th>
   			<th style="width : 100px" bgcolor="#FF0000">SALDO TRA LE U.I. MINIME STABILITE E QUELLE DESTINATE ALL'EFFETTUAZIONE DELLE ATTIVITA'</th>
   		</tr>
   		<% for (int s=0;s<dpat.getElencoStrutture().size();s++) {
   					String color="#FFFFFF";
					DpatStruttura struttura = (DpatStruttura)dpat.getElencoStrutture().get(s);
					if (struttura.getN_livello()==2) color="#FFFF00";
					else color="#FFFFFF";%>
					<tr>
						<td bgcolor="<%=color%>" id="struttura_<%=struttura.getId()%>"><%=struttura.getDescrizione_lunga()%></td>
						<td id="struttura_<%=struttura.getId()%>_saldo"><%=Math.round(struttura.getSaldo())%></td>
					</tr>
			<% } %>
			<tr bgcolor="#CEF6F5">
				<td>TOTALE</td>
				<td id="tot_sal"><%=Math.round(dpat.getSaldo())%></td>
			</tr>
		</table>
		</div> --%>

<% for (int j=0;j<dsiList.size();j++){
		DpatStrutturaIndicatore dsi = (DpatStrutturaIndicatore)dsiList.get(j);%>
		<script>
			//LABEL PER DESCRIPTION
	  	     document.getElementById("p_<%=dsi.getIdPiano()%>").innerHTML="<%=dsi.getDescrPiano()%>"; 
	         document.getElementById("a_<%=dsi.getIdAttivita()%>").innerHTML="<%=dsi.getDescrAttivita()%>";
			 document.getElementById("i_<%=dsi.getIdIndicatore()%>").innerHTML="<%=dsi.getDescrIndicatore()%>";  
			
			//SOMMA UI E SINGOLE UI
			document.getElementById("struttura_<%=dsi.getIdStruttura()%>_s_<%=dsi.getIdSezione()%>_p_<%=dsi.getIdPiano()%>_a_<%=dsi.getIdAttivita()%>_somma").innerHTML='<%=Math.round(dsi.getSomma_ui())%>';
			document.getElementById("struttura_<%=dsi.getIdStruttura()%>_s_<%=dsi.getIdSezione()%>_p_<%=dsi.getIdPiano()%>_a_<%=dsi.getIdAttivita()%>_i_<%=dsi.getIdIndicatore()%>").value='<%=Math.round(dsi.getUi())%>';
		</script>		
<% } %> 

  <ul>
  <% for(int i=0;i< dpat.getElencoSezioni().size();i++){ 
  		DpatSezione sezione = (DpatSezione)dpat.getElencoSezioni().get(i);%>
 		<li><a href="#s_<%=sezione.getId()%>">
          <span class="stepDesc">
            <%=sezione.getDescription()%><br/>
          </span>
      </a></li>
  <% } %>  <%-- 
      <li><a href="#saldo">
          <span class="stepDesc">
            SALDO<br/>
          </span>
      </a></li> --%>
  </ul>
</div>
<%} else {%>
DATI DPAT NON PRESENTI
<%} %>

<script>
function checkInt(id){
	flag=0;
	var n = document.getElementById(id).value;
	if (isNaN(n)){
		flag=1;
	} else if (n==null || n=="" ){
		flag=1;
	}else if(!Number.isInteger){
		flag=1;
	}	
	return flag;
}



//IN id HO TUTTI I CAMPI NECESSARI QUINDI SPLITTO SUL CARATTERE _ E PRENDO QUELLO CHE SERVE AL MOMENTO 
	function setElencoIdCelleIndicatori(id,val){
	 	//SALVA CAMBIAMENTI SUL DB
	 	if (val==0){
	 		ricalcolaUiAttivitaParziale(id);
	 	}
		var s = id.split("_");
		var myId=s[0]+"_"+s[1]+"_"+s[2]+"_"+s[3]+"_"+s[4]+"_"+s[5]+"_"+s[6]+"_"+s[7]+"_somma";
		var ui = document.getElementById(id).value;
		var somma = parseFloat(document.getElementById(myId).innerHTML);
		var idDpat = "<%=dpat.getId()%>";
		var userId = "<%=User.getUserId()%>";
	 	PopolaCombo.updateSommaUi(id, parseInt(ui), somma, parseInt(idDpat), parseInt(userId),{callback:updateSommaUiCallback,async:false});
	 	
	 	ricalcolaUiIndicatoreTotale(id);
	 	if (val==0){
	 		ricalcolaUiAttivitaTotale(id);
	 	}
	 	ricalcolaSaldoParziale(id);
	 	ricalcolaSaldoTotale();
	}
	function updateSommaUiCallback(){
	}
	
	function setElencoIdCelleCarichi(id,name,val){
	 	
	 	//SALVA CAMBIAMENTI SUL DB
	 	var idDpat = "<%=dpat.getId()%>";
	 	var carico = document.getElementById(id).value;
		PopolaCombo.updateCaricoStruttura(parseInt(idDpat),id, parseInt(carico),{callback:updateCaricoStrutturaCallback,async:false});
		
		ricalcolaCaricoLavoro(id,name,val);
	 	var s = id.split("_");
	 	var myId = s[0]+"_"+s[2];
	 	ricalcolaSaldoParziale(myId);
		ricalcolaSaldoTotale();
	}
	function updateCaricoStrutturaCallback(){
	}
	
	//UI attivita per struttura
	function ricalcolaUiAttivitaParziale(id){
		var s = id.split("_");
		var idStrutt = s[1];
		var idAtt = s[7];
		PopolaCombo.getCoefficienti(id,idAtt,idStrutt,{callback:ricalcolaUiAttivitaParzialeCallback,async:false});
	}
	function ricalcolaUiAttivitaParzialeCallback(value){
		var ui=0.0;
		if (value.length>0){
		for (var i=0; i<value.length;i++){
			var s = value[i].split(";");
			var v = document.getElementById(s[0]).value;
			ui = ui + (v*s[1]);
		}
		var s = value[0].split("_i_");
		var id = s[0]+"_somma";
		document.getElementById(id).innerHTML=Math.round(ui);
		}
	}
	
	//UI per attività
	function ricalcolaUiAttivitaTotale(id){
		var s = id.split("_");
		var tot = 0.0;
		s[0]=""; s[1]="";
		var myId=s[2]+"_"+s[3]+"_"+s[4]+"_"+s[5]+"_"+s[6]+"_"+s[7]+"_somma";		
		var input = document.getElementsByTagName('td');
		for (var i=0;i<input.length;i++){
			var str = input[i].id;
			if (str.indexOf(myId)>0){
				tot = tot+parseFloat(input[i].innerHTML);
			}
		}
		var idTot=s[6]+"_"+s[7]+"_tot";
		document.getElementById(idTot).innerHTML=Math.round(tot);
	}
	
	//UI indicatore totoale
	function ricalcolaUiIndicatoreTotale(id){
		var s = id.split("_");
		var tot = 0.0;
		s[0]=""; s[1]="";
		var myId=s[2]+"_"+s[3]+"_"+s[4]+"_"+s[5]+"_"+s[6]+"_"+s[7]+"_"+s[8]+"_"+s[9];
		var input = document.getElementsByTagName('input');
		for (var i=0;i<input.length;i++){
			var str = input[i].id;
			if (str.indexOf(myId)>0){
				tot = tot+parseInt(input[i].value);
			}
		}
		var idTot=s[8]+"_"+s[9]+"_tot";
		document.getElementById(idTot).innerHTML=tot;
	}
	
	//SOMMA DEI CARICHI 
	function ricalcolaCaricoLavoro(id,name,val){
		var tot=0;
		var s = id.split("_");
		var index = s[4];
		var input = document.getElementsByTagName('input');
		for (var i=0;i<input.length;i++){
			var str = input[i].id;
			if (str.indexOf("struttura_carico_")==0){
				if (input[i].name==name){
					document.getElementById(input[i].id).value=val;
				}
				var splitted = input[i].id.split("_");
				if(splitted[4]==index){
					tot = tot+parseInt(input[i].value);
				}
			}
		}		
		var tots = document.getElementsByTagName('td');
		for(var i=0;i<tots.length;i++){
			var str = tots[i].id;
			if(str.indexOf("tot_carico_i_")==0){
				document.getElementById(tots[i].id).innerHTML=tot;
			}
		}  
	}
	
	//SALDO parziale per struttura     struttura_230_s_1_p_342_a_1_i_25    struttura_230_s_1_p_342_a_1_somma
	function ricalcolaSaldoParziale(id){
		var tot=0;
		var s = id.split("_");
		var myId=s[0]+"_"+s[1]+"_";
		var input = document.getElementsByTagName('td');
		for (var i=0;i<input.length;i++){
			var str = input[i].id;
			if (str.indexOf(myId)==0){
				if (str.indexOf("_somma")>0){
					tot = tot+parseInt(input[i].innerHTML);
				}
			}
		}
		myId = s[0]+"_carico_"+s[1]+"_i_0";
		var carico = parseInt(document.getElementById(myId).value);
		var saldo = carico-tot;
		myId = s[0]+"_"+s[1]+"_saldo_i_";
		var elencoSaldi = document.getElementsByTagName('td');
		for (var i=0;i<elencoSaldi.length;i++){
			var str = input[i].id;
			if (str.indexOf(myId)==0){
				document.getElementById(str).innerHTML=saldo;
			}
		}
	}
	
	//SALDO totale
	function ricalcolaSaldoTotale(){
		var input = document.getElementsByTagName('td');
		var tot = 0;
		for (var i=0;i<input.length;i++){
			var str = input[i].id;
			if (str.indexOf("a_")==0){
				if (str.indexOf("_tot")>0){
					tot = tot+parseInt(input[i].innerHTML);
				}
			}
		}
		var carico = parseInt(document.getElementById("tot_carico_i_0").innerHTML);
		var saldo = carico-tot;
		
		var elencoSaldi = document.getElementsByTagName('td');
		for (var i=0;i<elencoSaldi.length;i++){
			var str = input[i].id;
			if (str.indexOf('tot_sal_i_')==0){
				document.getElementById(str).innerHTML=saldo;
			}
		}
	}
	
	function updateObiettivoTot(id){
		var idDpat = '<%=dpat.getId()%>';
		var val = document.getElementById(id).value;
		var input = document.getElementsByTagName('input');
		for(var i=0;i<input.length;i++){
			var str = input[i].id;
			if (str.indexOf("ob_tot_")==0){
				document.getElementById(input[i].id).value=val;
			}
		}
		PopolaCombo.updateObiettivoTot(idDpat,val,{callback:updateObiettivoTotCallback,async:false});
	}
	function updateObiettivoTotCallback(value){}
	
	
	function updateObiettivoParz(id){
		var val = document.getElementById(id).value;
		var s = id.split("_");
		var myId = s[2];
		PopolaCombo.updateObiettivoParz(myId,val,{callback:updateObiettivoParzCallback,async:false});
	}
	function updateObiettivoParzCallback(value){}
	
	var edit = '<%=edit%>';
	if (edit=="false"){
		var elementi = document.getElementsByTagName('input');
		for (var z=0;z<elementi.length;z++){
			document.getElementById(elementi[z].id).disabled = true;
		}
	} else {
		var usrRoleId = '<%=User.getRoleId()%>';
		if(usrRoleId==1 || usrRoleId==53){
			var elementi2 = document.getElementsByTagName('input');
			for (var z=0;z<elementi2.length;z++){
				var str = elementi2[z].id;
				if (str.indexOf("ob_i_")==0){
					document.getElementById(elementi2[z].id).disabled = false;
				}
				if (str.indexOf("ob_tot_")==0){
					document.getElementById(elementi2[z].id).disabled = false;
				}
			}
		}
	}
</script>

</body>



