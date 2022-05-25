<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@page import="org.aspcfs.modules.dpat2019.base.*"%>
<%@ page import="com.darkhorseventures.framework.actions.ActionContext"%>
<jsp:useBean id="dpat" class="org.aspcfs.modules.dpat2019.base.Dpat" scope="request"/>
<jsp:useBean id="dsiList" class="java.util.HashMap" scope="request"/>
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

	
<head>
	<script type="text/javascript" src="javascript/jquery-2.0.0.min.js"></script>
	<link href="css/smart_wizard.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="javascript/jquery.smartWizard.js"></script>
	
	
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
	

 	
	
   td:hover.tooltip span {
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
    height: 30px; /* Altezza */
    line-height: 30px; /* Altezza di riga */
    width: 40px; /* Larghezza */
    padding: 0 5px; /* Padding */
    text-align: center;
	}
	
		
		.layout { /* Stili per il campo di testo e per la textarea */
   /* background: #fff; /*Colore di sfondo */
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


span.tooltip {
      position: absolute;
      width: 100px;
      height: 20px;
      line-height: 20px;
      padding: 10px;
      font-size: 14px;
      text-align: center;
      color: rgb(113, 157, 171);
      background: rgb(255, 255, 255);
      border: 4px solid rgb(255, 255, 255);
      border-radius: 5px;
      text-shadow: rgba(0, 0, 0, 0.1) 1px 1px 1px;
      box-shadow: rgba(0, 0, 0, 0.1) 1px 1px 2px 0px;
}

span.tooltip:after {
      content: "";
      position: absolute;
      width: 0;
      height: 0;
      border-width: 10px;
      border-style: solid;
      border-color: #FFFFFF transparent transparent transparent;
      top: 44px;
      left: 50px;
}
</style>
	
	
	
</head>

<!-- SERVER DOCUMENTALE -->
<%@ page import=" org.aspcfs.modules.util.imports.ApplicationProperties"%>
    <%@page import="java.net.InetAddress"%>
    <link rel="stylesheet" type="text/css" media="print" documentale_url="" href="css/dpat_print.css" />

<!-- INIT DOCUMENTALE -->
	<%@ include file="/gestione_documenti/initEncodingDocumentale.jsp" %>
<!-- FINE INIT DOCUMENTALE -->

	 <script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>
		<% if (dpat.isCompleto()==true){ %>
	  <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="PDF" style="width:130px" value="PDF Carichi di lavoro"	onClick="openRichiestaPDF_DPAT('<%=dpat.getIdAsl()%>', '<%=dpat.getAnno()%>', 'DPAT_Carichi');">
        <% } %>
 <!-- SERVER DOCUMENTALE -->
 
 <div class="documentaleStampare" style="display:none">
<div class="boxIdDocumento"></div> <div class="boxOrigineDocumento"><%@ include file="../../hostName.jsp" %></div>
</div>

<% if (dpat.getElencoSezioni().size()>0){ %>
<div id="wizard" class="swMain">
  <%
  	
  	for(int i=0;i< dpat.getElencoSezioniSplitted().size();i++){ 
  	
  		DpatSezione sezione = (DpatSezione)dpat.getElencoSezioniSplitted().get(i);%>
  		<div id="s_<%=sezione.getId()+"_"+ i%>">
			<table border="1" style="width:100%;" class="tocolorate" 
			
			
			<% if (i>0){ %>
			style="page-break-before:always"
			<% } %>
			
			
			>
			<thead>
			<tr>
		
					<th colspan="5" bgcolor="<%=sezione.getBgColor()%>"><%=sezione.getDescription() %></th>
			</tr>
				<tr>
					<th style="max-width: 50px;" rowspan="3">STRUTTURA</th>
					<th style="max-width: 70px;" rowspan="3">CARICO DI LAVORO EFFETTIVO ANNUALE MINIMO DI STRUTTURA IN U.I.</th>
					<% for (int j=0;j<sezione.getElencoPiani().size();j++){ 
					 	 DpatPiano piano = (DpatPiano)sezione.getElencoPiani().get(j);
					 	 int numInd=0;
					 	 for (int app=0; app<piano.getElencoAttivita().size();app++){
					 	 	DpatAttivita att = (DpatAttivita)piano.getElencoAttivita().get(app);
					 	 	numInd=numInd+(att.getElencoIndicatori().size()+1);%>
					 	 <%} %>
					 	 <th style="font-weight: bolder;" bgcolor="<%=sezione.getBgColor()%>" id="p_<%=piano.getId()%>" colspan="<%=numInd%>" >
					 	 	<%=fixEncoding(piano.getDescription())%>
					 	 </th>
					<% }%>
					<th rowspan="3" style="width : 3px" bgcolor="#FF0000"> <!--style="font-size: 4px;"--> SALDO TRA LE U.I. MINIME STABILITE E QUELLE DESTINATE ALL'EFFETTUAZIONE DELLE ATTIVITA'</th>
				</tr>
				<tr>
				<% for (int j=0;j<sezione.getElencoPiani().size();j++){
					for (int k=0; k<sezione.getElencoPiani().get(j).getElencoAttivita().size(); k++){
					  DpatAttivita attivita = sezione.getElencoPiani().get(j).getElencoAttivita().get(k);%>
					  <td style="font-weight: bolder;" class="tooltip" bgcolor="<%=sezione.getBgColor()%>" id="a_<%=attivita.getId()%>" colspan="<%=sezione.getElencoPiani().get(j).getElencoAttivita().get(k).getElencoIndicatori().size()+1%>">
					  <span><%= fixEncoding(attivita.getDescription())%></span>	<%=(attivita.getDescription().indexOf(' ', 60) > -1) ? fixEncoding(attivita.getDescription().substring(0, attivita.getDescription().indexOf(' ', 60))) 
					  			+ " <font size=\"1\">[...]</font>" : fixEncoding(attivita.getDescription())%>
					  </td>
				<% 	}} %>
				</tr>
		 		<tr>
		 		
		 		<% for (int j=0;j<sezione.getElencoPiani().size();j++){
					for (int k=0; k<sezione.getElencoPiani().get(j).getElencoAttivita().size(); k++){
					  String value = "a";
					  int charValue = value.charAt(0);
					  for(int ind=0;ind<sezione.getElencoPiani().get(j).getElencoAttivita().get(k).getElencoIndicatori().size();ind++){
					  	DpatIndicatore indicatore = sezione.getElencoPiani().get(j).getElencoAttivita().get(k).getElencoIndicatori().get(ind);%>
					  	<td style="font-weight: bolder;" bgcolor="#DCDCDC" id="i_<%=indicatore.getId()%>" colspan="1">
					  	<% String next = String.valueOf( (char) (charValue + ind));%>
					  	
					  	<%= ( indicatore.getNote()!=null && !indicatore.getNote().equals("") && indicatore.getNote().length()>34) ? 
					  	fixEncoding(indicatore.getNote().substring(0, 10) + " <font size=\"0.5\">[...]</font>") : ((indicatore.getNote()!=null && !indicatore.getNote().equals("")) ? fixEncoding(indicatore.getNote()) : "")%><br><br> 
					  	<span><%=(indicatore.getNote()!=null && !indicatore.getNote().equals("") )? fixEncoding(indicatore.getNote()+"\n"+indicatore.getDescription()) : fixEncoding(indicatore.getDescription())%></span>
					  	<%=( indicatore.getDescription().length()>34 &&  indicatore.getDescription().indexOf(' ', 34) > -1) ? 
					  	fixEncoding(indicatore.getDescription().substring(0, indicatore.getDescription().indexOf(' ', 34)) + " <font size=\"0.5\">[...]</font>") : fixEncoding(indicatore.getDescription())%></td>
				<% 	  }  %> 
					  <th bgcolor="#DCDCDC" colspan="1">U.I.</th>
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
						color_tr = "#C0C0C0";
					
					%>
					<tr bgcolor="<%=color_tr%>">
						<td style="max-width: 30px;"  class="tooltip" id="struttura_<%=struttura.getId()%>">
						<span><%=fixEncoding(struttura.getDescrizione_lunga()) %></span>
						<%=(struttura.getDescrizione_lunga().indexOf(' ', 60) > -1) ? 
								fixEncoding(struttura.getDescrizione_lunga().substring(0, struttura.getDescrizione_lunga().indexOf(' ', 60))) + " <font size=\"1\">[...]</font>" : fixEncoding(struttura.getDescrizione_lunga())%></td>
						<td><label type="text" 
								   name="struttura_carico_<%=struttura.getId()%>"
								   id="struttura_carico_<%=struttura.getId()%>_i_<%=i%>"><%=struttura.getCaricoInUi()%></label></td>
						<% for (int j=0;j<sezione.getElencoPiani().size();j++){
								DpatPiano p = (DpatPiano)sezione.getElencoPiani().get(j);
							for (int k=0; k<sezione.getElencoPiani().get(j).getElencoAttivita().size(); k++){ 
								DpatAttivita a = (DpatAttivita)sezione.getElencoPiani().get(j).getElencoAttivita().get(k);
								double sommaUi = 0 ;
							 for(int ind=0;ind<sezione.getElencoPiani().get(j).getElencoAttivita().get(k).getElencoIndicatori().size();ind++){
							 	DpatIndicatore in = (DpatIndicatore)sezione.getElencoPiani().get(j).getElencoAttivita().get(k).getElencoIndicatori().get(ind);%>
							 	
							 	 <%
							 
							 int ui = 0 ;
							 	
							 	if (dsiList.get(struttura.getCodiceInternoFK())!=null)
							 	{
							 		HashMap<Integer,DpatStrutturaIndicatore> temp = (HashMap<Integer,DpatStrutturaIndicatore>) dsiList.get(struttura.getCodiceInternoFK());
							 		if (temp.get(in.getId())!=null)
							 		{
							 			ui = temp.get(in.getId()).getUi();
							 			
							 			
							 			
							 			sommaUi  = temp.get(in.getId()).getSomma_ui();
							 		}
							 		
							 	}
							 %>
							 
							 	
							 	
							 	<td  <%if (in.getUiCalcolabile()==false) {%> bgcolor="#000000" <%}%>>
							 <label class="layout" id="struttura_<%=struttura.getId()%>_s_<%=sezione.getId()%>_p_<%=p.getId()%>_a_<%=a.getId()%>_i_<%=in.getId()%>">
							  <% 
							
							
							 %>
							 
							 <%=ui%>
							 
							 </label></span>
							 
							 	</td>						
						<% 	 } %>
					 			<td <% if (a.getUiCalcolabile()==true){ %>
					 						bgcolor="#A9A9A9" 
					 				<%} else { %> 
					 						bgcolor="#000000" 
					 				<%} %>
					 				id="struttura_<%=struttura.getId()%>_s_<%=sezione.getId()%>_p_<%=p.getId()%>_a_<%=a.getId()%>_somma"><%=sommaUi %></td>
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
				<td><label class="layout" disabled
							style="width: 30px;" 
							type="text" 
							id="ob_tot_<%=i%>" 
							><%=dpat.getObiettivo_in_ui()%></label></td>
				<% for (int j=0;j<sezione.getElencoPiani().size();j++){
				DpatPiano p = (DpatPiano)sezione.getElencoPiani().get(j);
				for (int k=0; k<sezione.getElencoPiani().get(j).getElencoAttivita().size(); k++){ 
				 DpatAttivita a = (DpatAttivita)sezione.getElencoPiani().get(j).getElencoAttivita().get(k);
				 for(int ind=0;ind<sezione.getElencoPiani().get(j).getElencoAttivita().get(k).getElencoIndicatori().size();ind++){
				  DpatIndicatore in = (DpatIndicatore)sezione.getElencoPiani().get(j).getElencoAttivita().get(k).getElencoIndicatori().get(ind);%>
					<td><label class="layout" disabled 
								style="width: 30px;" 
								type="text" id="ob_i_<%=in.getId()%>"
								><%=(int)sezione.getElencoPiani().get(j).getElencoAttivita().get(k).getElencoIndicatori().get(ind).getObiettivo_in_ui()%></label></td>			
				<%}%>
				 	<td></td>
			<%	 }} %>
			</tr>
			</tbody>
			</table>   
		</div>	
   <% } %>
  



</div>
<%} else {%>
DATI DPAT NON PRESENTI
<%} %>


</body>



