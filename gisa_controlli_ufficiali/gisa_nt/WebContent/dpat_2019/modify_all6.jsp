<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.Date"%>
<%@page import="org.aspcfs.modules.dpat2019.base.oia.OiaNodo"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@page import="org.aspcfs.modules.dpat2019.base.*"%>
<%@ page import="com.darkhorseventures.framework.actions.ActionContext"%>
<%@page import="org.aspcfs.modules.dpat2019.*" %>
<%@page import="org.aspcfs.modules.dpat2019.base.*" %>
<%@page import="org.aspcfs.modules.dpat2019.base.*" %>

<%-- <jsp:useBean id="dpat" class="org.aspcfs.modules.dpat.base.DpatAttribuzioneCompetenza" scope="request"/> --%>
<jsp:useBean id="dpat2" class="org.aspcfs.modules.dpat2019.base.DpatAttribuzioneCompetenzeNewBean" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="edit" class="java.lang.String" scope="request"/>
<jsp:useBean id="anno" class="java.lang.String" scope="request"/>
<jsp:useBean id="idPadre" class="java.lang.String" scope="request"/>
<jsp:useBean id="idSDC" type="java.lang.Integer" scope="request"/>
<jsp:useBean id="ListaAsl" class="org.aspcfs.utils.web.LookupList" scope="request"/> 

<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
 
 <script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
 

<script>

</script>
<body onload="resizeGlobalItemsPane('hide')">
<%@ include file="../initPage.jsp"%>
<table class="trails" cellspacing="0">
		<tr>
			<td width="100%"><div align="left"><a href="dpat2019.do">DPAT</a> &gt <a href="dpat2019.do">Allegati DPAT</a> &gt MODELLO 5 ASL <%=ListaAsl.getSelectedValue(dpat2.getIdAsl()) %>
			</div></td>	
		</tr>
	</table>

<head>
	<script type="text/javascript" src="javascript/jquery-2.0.0.min.js"></script>
	
	<link href="css/smart_wizard.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="javascript/jquery.smartWizard.js"></script>
	<link media="screen" rel="stylesheet" href="css/defaultTheme.css"></link>
	<script type="text/javascript" src="javascript/jquery.fixedheadertable.js"></script>
	
	
	<script type="text/javascript">
	//loadModalWindow();
	loadModalWindowCustom('<div style="color:red; font-size: 20px;">Attendere prego.... <br>(Se il browser dovesse visualizzare un messaggio di avviso secondo il quale lo script <br> sta impiegando troppo tempo,  cliccare su "continua"  e attendere il completamento)</div>');
	</script>
	
<style type="text/css">



   .table_sez td, .table_sez th {
        /* appearance */
        border: 1px solid #778899;
        
        /* size */
        /* padding: 5px; */
        }
        
        th.tooltipobsoleto, td.tooltipobsoleto {
                
                
        }
        
        td.tooltipobsoleto span, th.tooltipobsoleto span {
                position: absolute;
                width:140px;
                padding: 3px;                
                background: #000;
                color: #fff;
                text-align: center;
                visibility: hidden;
                border-radius: 5px;
                font-size: 8px;
        } 
                        
        td.tooltipobsoleto span:after {
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
                        
td:hover.tooltipobsoleto span, th:hover.tooltipobsoleto span {
                visibility: visible;
                opacity: 0.8;
                z-index: 999;
        }
        

         
        
td:hover.tooltipobsoleto span, th:hover.tooltipobsoleto span {
                visibility: visible;
                opacity: 0.8;
                z-index: 999;
        }
        
        
         td{
         text-align:center; 
    vertical-align:middle;
        } 
        
        input[type="text"] { /* Stili per il campo di testo e per la textarea */
    background: #fff; /*Colore di sfondo */
    border: 0px solid #323232; /* Bordo */
    color: #000; /* Colore del testo */
    height: 20px; /* Altezza */
    line-height: 30px; /* Altezza di riga */
    width: 30px; /* Larghezza */
    padding: 0 5px; /* Padding */
    text-align: center;
        }
        
        
        
</style>
	
	
	
	
</head>


<script type="text/javascript">



  $(document).ready(function() {
      // Initialize Smart Wizard
      loadModalWindowUnlock();
      
     // $('#wizard').smartWizard();
     
     
      $('#wizard').smartWizard(    		  
     {
      divHeadPaddingLeft:  0,
      divBodyPaddingLeft:   0,
      fixedTypeNumber:  2
      			
      	    });
     
      
  }); 
  
</script>





	<% if (dpat2.getElencoSezioni().getSezioni().size()>0){ %>
	<% if (dpat2.isCompleto()==false || (dpat2.getStrutturaAmbito()!=null && dpat2.getStrutturaAmbito().getStato_all6()!=2)){ %>
	<p align="center">
		<dhv:permission name="dpat-edit">
			<input id="salva" disabled=true type="button" value="Salva Definitivo"  onclick="javascript : if (confirm('ATTENZIONE!!!Chiudere definitivamente il modello 5? se clicchi OK non sarà MAI PIU possibile modificarlo.Se invece prevedi di apportare ancora modifiche clicca su ANNULLA')){window.location='dpat2019.do?command=SalvaDefinitivoCompetenze&combo_area=<%=(dpat2.getStrutturaAmbito()!=null) ? dpat2.getStrutturaAmbito().getId() : "-1"%>&id=<%=dpat2.getId()%>&idAsl=<%=dpat2.getIdAsl()%>&anno=<%=dpat2.getAnno()%>'}">
		</dhv:permission>
	</p>
	<%} }%>
	
	
	<%
	if(dpat2.getStrutturaAmbito()!=null && dpat2.getStrutturaAmbito().getId()>0)
	{
	%>
	<input type="button" value="Esporta  Modello 5 - Attribuzione competenze"
		onClick="location.href='dpat2019.do?command=DpatGeneraXlsModifyGeneraleCompetenzeNEW&combo_area=<%=dpat2.getStrutturaAmbito().getId()%>&idAsl=<%=dpat2.getStrutturaAmbito().getIdAsl()%>&anno=<%=dpat2.getStrutturaAmbito().getAnno()%>'" 
		style="background-color:#FF4D00; font-weight: bold;"/>
		
	<%} %>


	<br><br><br>


<% if (dpat2.getElencoSezioni().getSezioni().size()>0){ %>
<div id="wizard" class="swMain">
 
 
  	<%
  	for(int i=0;i< dpat2.getElencoSezioni().getSezioni().size();i++){ 
  		DpatSezioneNewBeanInterface sezione =(DpatSezioneNewBeanInterface) dpat2.getElencoSezioni().getSezioni().get(i);%>
		<div id="s_<%=sezione.getOid()+"_"+ i%>" >
		
			<table border="1"  id = "table<%=i %>" class="table_sez">
			<thead>
			<tr bgcolor="<%=sezione.getColor()%>">
			<th rowspan="2" bgcolor="red">
			
			<%=("MODELLO 5 ATTRIBUZIONE COMPETENZA DEI PIANI DI MONITORAGGIO E ATTIVITA'".indexOf(' ', 30) > -1) ? "MODELLO 5 ATTRIBUZIONE COMPETENZA DEI PIANI DI MONITORAGGIO E ATTIVITA'".substring(0, "MODELLO 5 ATTRIBUZIONE COMPETENZA DEI PIANI DI MONITORAGGIO E ATTIVITA'".indexOf(' ', 30)) 
					  			+ " <font size=\"1\">[...]</font>" : "MODELLO 5 ATTRIBUZIONE COMPETENZA DEI PIANI DI MONITORAGGIO E ATTIVITA'"%>
					  			</th>
					<th colspan="30" ><%=sezione.getDescrizione() %></th>
			</tr>
				<tr bgcolor="<%=sezione.getColor()%>">
				
					
					<% for (int j=0;j<sezione.getPianiAttivitaFigli().size();j++){ 
					 	 DpatPianoAttivitaNewBeanInterface piano = (DpatPianoAttivitaNewBeanInterface)sezione.getPianiAttivitaFigli().get(j);
					 	 int numInd= piano.getIndicatoriFigli().size();
					 	 /*for (int app=0; app<piano.getElencoAttivita().size();app++){
					 	 	DpatAttivita att =piano.getElencoAttivita().get(app);
					 	 	numInd=numInd+(att.getElencoIndicatori().size());
					 	  }*/ %>
					 	 <th style="font-weight: bolder;" id="p_<%=piano.getOid()%>" colspan="<%=numInd%>" >
					 	 	<%=piano.getDescrizione()%>
					 	 </th>
					<% }%>
					
				</tr>
				<!-- /*ELIMINO LIVELLO DELL'ATTIVITA DA SOLA */ -->
				
				<%-- <tr bgcolor="<%=sezione.getColor()%>" >
				<% for (int j=0;j<sezione.getPianiAttivitaFigli().size();j++){
					DpatPianoAttivitaNewBeanInterface currPiano  = (DpatPianoAttivitaNewBeanInterface)sezione.getPianiAttivitaFigli().get(j) ;
					//for (int k=0; k<currPiano.getElencoAttivita().size(); k++){
					 // DpatAttivita attivita =currPiano.getElencoAttivita().get(k);%>
					 <th class="tooltip" title="<%=currPiano.getDescrizione()%>" style="font-weight: bolder;" id="a_<%=currPiano.getOid()%>" colspan="<%=currPiano.getIndicatoriFigli().size()%>">
					  <span></span> 	
					<%=(currPiano.getDescrizione().indexOf(' ', 30) > -1) ? currPiano.getDescrizione().substring(0, currPiano.getDescrizione().indexOf(' ', 30)) 
					  			+ " <font size=\"1\">[...]</font>" : currPiano.getDescrizione()%>
					  </th>
				<% 	//}
					
				   } %>
				</tr> --%>
				
		 		<tr style ="background-color: rgb(221,217,195)">
		 		<th bgcolor="<%="yellow"%>"  >UU.OO.CC. <br> E UU.OO.SS.DD.</th> 
				<% for (int j=0;j<sezione.getPianiAttivitaFigli().size();j++){
					DpatPianoAttivitaNewBeanInterface piano =(DpatPianoAttivitaNewBeanInterface) sezione.getPianiAttivitaFigli().get(j) ;
							//for (int k=0; k<piano.getElencoAttivita().size(); k++){
							//	DpatAttivita att =   piano.getElencoAttivita().get(k) ;
								String value = "a";
								int charValue = value.charAt(0);
							  for(int ind=0;ind<piano.getIndicatoriFigli().size();ind++){
							  	DpatIndicatoreNewBeanAbstract indicatore =(DpatIndicatoreNewBeanAbstract)piano.getIndicatoriFigli().get(ind);%>
							  	<th class= "tooltip" title="<%= indicatore.getDescrizione()%>" style="font-weight: bolder;" id="i_<%=indicatore.getOid()%>" colspan="1">
							  	<% String next = String.valueOf( (char) (charValue + ind));%>
							  	
							  	<%--= ( indicatore.getNote()!=null && !indicatore.getNote().equals("") && indicatore.getNote().length()>30) ? 
							  	indicatore.getNote().substring(0, 10) + " <font size=\"1\">[...]</font>" : ((indicatore.getNote()!=null && !indicatore.getNote().equals("")) ? indicatore.getNote() : "")--%><br><br> 
							  	<span></span>
							  	<%=(indicatore.getDescrizione().length()>34 && indicatore.getDescrizione().indexOf(' ', 34) > -1) ? 
							  indicatore.getDescrizione().substring(0, indicatore.getDescrizione().indexOf(' ', 34)) + " <font size=\"1\">[...]</font>" : indicatore.getDescrizione()%></th>
						<% 	  }  %> 
							  
						<%	//}
					} %>
				</tr>
				
				<tr style ="background-color: rgb(221,217,195)">
		 		<th bgcolor="<%="yellow"%>"  >UU.OO.CC. <br> E UU.OO.SS.DD.</th> 
				<% for (int j=0;j<sezione.getPianiAttivitaFigli().size();j++){
					DpatPianoAttivitaNewBeanInterface piano =(DpatPianoAttivitaNewBeanInterface) sezione.getPianiAttivitaFigli().get(j) ;
						//for (int k=0; k<piano.getElencoAttivita().size(); k++){
							//DpatAttivita att =   piano.getElencoAttivita().get(k) ;
							String value = "a";
							int charValue = value.charAt(0);
						  for(int ind=0;ind<piano.getIndicatoriFigli().size();ind++){
						  	DpatIndicatoreNewBeanAbstract indicatore =(DpatIndicatoreNewBeanAbstract)piano.getIndicatoriFigli().get(ind);%>
						  	<th class= "tooltip" title="<%= indicatore.getDescrizione()%>" style="font-weight: bolder;" id="i_<%=indicatore.getOid()%>" colspan="1">
						  	<% String next = String.valueOf( (char) (charValue + ind));%>
						  	<%=indicatore.getAliasIndicatore() %>
						  	</th>
					<% 	  }  %> 
						  
					 <% //}
					} %>
				</tr>
				</thead>
				<tbody>
			<%
			
			for (int s=0;s<dpat2.getElencoStrutture().size();s++) {
					String color="#FFFFFF";
					String color_tr="#FFFFFF";
					OiaNodo struttura = (OiaNodo)dpat2.getElencoStrutture().get(s);
					if (struttura.getN_livello()==2) {color="#FFFF00";}
					else {color="#FFFFFF";}
					if (s%2 == 0)
						color_tr = "#fff";
					else 
						color_tr = "#C0C0C0";
					%>
					<tr bgcolor="<%=color_tr%>">
						<td class="tooltip" title="<%=struttura.getDescrizione_lunga() %>"   id="struttura_<%=struttura.getId()%>" >
							<span ></span>					
						<%=(struttura.getDescrizione_lunga().indexOf(' ', 30) > -1) ? 
								struttura.getDescrizione_lunga().substring(0, struttura.getDescrizione_lunga().indexOf(' ', 30)) + " <font size=\"1\">[...]</font>" : struttura.getDescrizione_lunga()%>
								
								
						</td>
					
					
					 
						
						<% for (int j=0;j<sezione.getPianiAttivitaFigli().size();j++){
								DpatPianoAttivitaNewBeanInterface p =(DpatPianoAttivitaNewBeanInterface)sezione.getPianiAttivitaFigli().get(j);
								//for (int k=0; k<p.getElencoAttivita().size(); k++){ 
									//DpatAttivita a = p.getElencoAttivita().get(k);
									 for(int ind=0;ind<p.getIndicatoriFigli().size();ind++){
									 	DpatIndicatoreNewBeanAbstract in = (DpatIndicatoreNewBeanAbstract)p.getIndicatoriFigli().get(ind);
									 	%>
									 	
							  	 
							  		 	<%
							  		 	System.out.println("ID INDICATORE"+in.getOid());
							  		 	if (struttura.getCompetenzeIndicatori().get(in.getOid().intValue())!=null)
							  		 	{
							  		 		System.out.println("Modello 5 : "+in.getOid() + " - "+ struttura.getCompetenzeIndicatori().get(in.getOid()));
							  		 	}
							  		 	
							  		 	%>
									 	<td  align="center" valign="">
									 	<input type="text" 
									 	onclick="document.getElementById('salva').disabled=false;setValore(this,<%=struttura.getId() %>,<%=in.getOid() %>,<%=dpat2.getId() %>,<%=User.getUserId() %>)" readonly="readonly" 
									 	value="<%=(struttura.getCompetenzeIndicatori().get(in.getOid().intValue())!= null && struttura.getCompetenzeIndicatori().get(in.getOid().intValue())==Boolean.TRUE ) ? "X" : "" %>"
									 	id="struttura_<%=struttura.getId()%>_s_<%=sezione.getOid()%>_p_<%=p.getOid()%>_a_<%=p.getOid()%>_i_<%=in.getOid()%>"/>
									 	</td>						
								<% 	 } %>
						 			
							<%	 //}
							
							} %>
						
					
					</tr>
				
			<% } %>
			
			</tbody>
			</table>   
		</div>	
   <% } %>



   
</div>
<%} else {%>
DATI DPAT NON PRESENTI
<%} %>

<script>
var stato=true;
function setValore(field,idStruttura, idIndicatore, idDpat,  userid){
	var idSDC = "<%=idSDC%>";
	
		if (field.value!= ''){
			field.value = '';
			PopolaCombo.aggiornaDpatCompetenzeStrutturaNEW( idStruttura, idIndicatore, idDpat, false, userid,function(){});
		} else {
			field.value = "X";
			PopolaCombo.aggiornaDpatCompetenzeStrutturaNEW( idStruttura, idIndicatore, idDpat, true, userid,function(){});	
		}
	
}

function checkStatoSDCCallback(value){
	stato=value;
}

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

</script>



</body>







