<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.dpat2019.base.oia.OiaNodo"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@page import="org.aspcfs.modules.dpat2019.base.*"%>
<%@ page import="com.darkhorseventures.framework.actions.ActionContext"%>
<jsp:useBean id="dpat" class="org.aspcfs.modules.dpat2019.base.DpatAttribuzioneCompetenza" scope="request"/>
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
	
		
	input type[text]{ /* Stili per il campo di testo e per la textarea */
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

<script type="text/javascript">

var helpers = {
		
	      _getScrollbarWidth: function() {
	          var scrollbarWidth = 0;

	          if (!scrollbarWidth) {
	            if (/msie/.test(navigator.userAgent.toLowerCase())) {
	              var $textarea1 = $('<textarea cols="10" rows="2"></textarea>')
	                    .css({ position: 'absolute', top: -1000, left: -1000 }).appendTo('body'),
	                  $textarea2 = $('<textarea cols="10" rows="2" style="overflow: hidden;"></textarea>')
	                    .css({ position: 'absolute', top: -1000, left: -1000 }).appendTo('body');

	              scrollbarWidth = $textarea1.width() - $textarea2.width() + 2; // + 2 for border offset
	              $textarea1.add($textarea2).remove();
	            } else {
	              var $div = $('<div />')
	                    .css({ width: 100, height: 100, overflow: 'auto', position: 'absolute', top: -1000, left: -1000 })
	                    .prependTo('body').append('<div />').find('div')
	                    .css({ width: '100%', height: 200 });

	              scrollbarWidth = 100 - $div.width();
	              $div.parent().remove();
	            }
	          }

	          return scrollbarWidth;
	        }
		
}

  $(document).ready(function() {
      // Initialize Smart Wizard
      loadModalWindowUnlock();
      
      $('#wizard').smartWizard();
     
      
   <%   for(int i=0;i< dpat.getElencoSezioni().size();i++){ 
    		DpatSezione sezione = dpat.getElencoSezioni().get(i);%>
  	<%-- 	<div id="s_<%=sezione.getId()+"_"+ i%>"> --%>
     // for (var i = 0; i < 4; i++){
    	<%-- alert('fixing ' +<%=i%>); --%> 
    	<%if (i > 0){%>
    	 $('#aa'+<%=i%>).show();
    	 $(s_<%=sezione.getId()+"_"+ i%>).show();
    	<%}	 %>
    	 
    	$('#aa'+<%=i%>).fixedHeaderTable({
    	    	id:  <%=i%>
    			
    	    }); 
    	 
    	    
    	    //  alert('1');
    	    var $tableBodyCell = $('#fht-tbody'+<%=i%>+' table tbody tr:first td');
    	    
    	//  alert('$tableBodyCell ' +$tableBodyCell.length);
    	    var $headerCell = $('#fht-thead'+<%=i%>+' thead tr:last th');
    	    var $headerCell1 = $('#fht-thead'+<%=i%>+' thead tr:nth-child(2) th');
    	 //   $('.myclass tr:nth-child(2)')
    	 
    	  var $headerCell1 = $('#fht-thead'+<%=i%>+' thead tr:nth-child(2) th');
    
	    var arr = [];

	    $headerCell1.each(function() {
	        var $this=$(this);
	        var colWidth = $this.width();
	      //  alert(colWidth);
	        arr.push(colWidth);
	        //alert(arr);
	    });

    	    

    	 //   alert('$headerCell1 '+$headerCell1.length);
    	    $tableBodyCell.each(function(index){
    	   // 	alert('dsds' + $(this).width());
    	   // 	alert(index);
//    	    	if (index == 0)
//    	    		 $headerCell.eq(index).width(135);
//    	        if (index == 1)
//    	        	$headerCell.eq(index).width(74);
    	    //    alert($headerCell1.length);
//     	    	if (index == 0 || index == 1)
//     	    		$headerCell1.eq(index).width($(this).width());
    	    		
//     	        if (index > 1 && index < $headerCell.length )
    	        	$headerCell.eq(index).width($(this).width());
    	        
//     	        if (index == $headerCell.length ){
//     	        	//alert('==');
    	        	
//     	        	//alert(helpers._getScrollbarWidth());
//     	        	$(this).width(arr[$headerCell1.length] + helpers._getScrollbarWidth());
//     	        //	$headerCell1.eq($headerCell1.length).width($(this).width()-helpers._getScrollbarWidth());
//     	        }
    	    });
    	    
    	  <%--   document.getElementById('aa'+<%=i%>).deleteTHead(); --%>
    	    
        	<%if (i > 0){%>
       	 $('#aa'+<%=i%>).hide();
       	 $(s_<%=sezione.getId()+"_"+ i%>).hide();
       	<%}	 %>
    <%  }%>
    
    

		
    /*         $('#aa0').fixedHeaderTable({
            
    		
      });  */
            
    /*            		
            $('#aa1').fixedHeaderTable({
            
    		
      }); 
            
            
            $('#aa2').fixedHeaderTable({
                
        		
            }); 
            
            
            $('#aa3').fixedHeaderTable({
                
        		
            }); 
            
       $('#aa4').fixedHeaderTable({
                
        		
            });
       
       $('#aa5').fixedHeaderTable({
           
   		
       });  */
     
      // PER ABILITARE TUTTE LE SEZIONI ALLO STARTUP
      //  $('#wizard').smartWizard({enableAllSteps:'true'});
       
/*       $(document).ready(function() {
    	    var $tableBodyCell = $('#tableBody tr:first td');
    	    var $headerCell = $('#tableHeader thead tr th');
    	    $tableBodyCell.each(function(index){
    	         $headerCell.eq(index).width($(this).width());
    	    });
    	}); */
    	
     
  }); 
  
</script>


<!-- SERVER DOCUMENTALE -->
<%@ page import=" org.aspcfs.modules.util.imports.ApplicationProperties"%>
    <%@page import="java.net.InetAddress"%>
    <link rel="stylesheet" type="text/css" media="print" documentale_url = "" href="css/dpat_print.css" />

<!-- INIT DOCUMENTALE -->
	<%@ include file="/gestione_documenti/initEncodingDocumentale.jsp" %>
<!-- FINE INIT DOCUMENTALE -->

<p align="left">
	 <script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>
		<% if (dpat.isCompleto()==true){ %>
	  <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="PDF" style="width:130px" value="PDF Allegato 6"	onClick="openRichiestaPDF_DPAT('<%=dpat.getIdAsl()%>', '<%=dpat.getAnno()%>', 'DPAT_All6');">
        <% } %>
</p>
 <!-- SERVER DOCUMENTALE -->


 <div class="documentaleStampare" style="display:none">
<div class="boxIdDocumento"></div> <div class="boxOrigineDocumento"><%@ include file="../../hostName.jsp" %></div>
</div>

<% if (dpat.getElencoSezioni().size()>0){ %>
<div id="wizard" class="swMain">
 
  	<%
  	for(int i=0;i< dpat.getElencoSezioni().size();i++){ 
  		DpatSezione sezione = (DpatSezione)dpat.getElencoSezioni().get(i);%>
		<div id="s_<%=sezione.getId()+"_"+ i%>" style="width: 100%">
			<table border ="1" id="aa<%=i%>" class="table_sez"
			
			<% if (i>0){ %>
			style="page-break-before:always"
			<% } %>
			>
			<thead>
			
			<tr bgcolor="<%=sezione.getBgColor()%>">
			<th rowspan="3" bgcolor="<%="red"%>">ALL. 6 - ATTRIBUZIONE COMPETENZA DEI PIANI DI MONITORAGGIO E ATTIVITA'</th>
					<th colspan="3" ><%=sezione.getDescription() %></th>
			</tr>
				<tr bgcolor="<%=sezione.getBgColor()%>">
				
					
					<% for (int j=0;j<sezione.getElencoPiani().size();j++){ 
					 	 DpatPiano piano = (DpatPiano)sezione.getElencoPiani().get(j);
					 	 int numInd=0;
					 	 for (int app=0; app<piano.getElencoAttivita().size();app++){
					 	 	DpatAttivita att = (DpatAttivita)piano.getElencoAttivita().get(app);
					 	 	numInd=numInd+(att.getElencoIndicatori().size());%>
					 	 <%} %>
					 	 <th style="font-weight: bolder;" id="p_<%=piano.getId()%>" colspan="<%=numInd%>" >
					 	 	<%=piano.getDescription()%>
					 	 	
					 	 </th>
					<% }%>
					
				</tr>
				<tr bgcolor="<%=sezione.getBgColor()%>" >
				<% for (int j=0;j<sezione.getElencoPiani().size();j++){
					DpatPiano currPiano = (DpatPiano)sezione.getElencoPiani().get(j) ;
					for (int k=0; k<currPiano.getElencoAttivita().size(); k++){
					  DpatAttivita attivita = (DpatAttivita)currPiano.getElencoAttivita().get(k);%>
					  <td class="tooltip" style="font-weight: bolder;" id="a_<%=attivita.getId()%>" colspan="<%=attivita.getElencoIndicatori().size()%>">
					  <span><%=attivita.getDescription()%></span> 	
					<%=(attivita.getDescription().indexOf(' ', 30) > -1) ? attivita.getDescription().substring(0, attivita.getDescription().indexOf(' ', 30)) 
					  			+ " <font size=\"1\">[...]</font>" : attivita.getDescription()%>
					  </td>
				<% 	}} %>
				</tr>
		 		<tr style ="background-color: rgb(221,217,195)">
		 		<td bgcolor="<%="yellow"%>"  >UU.OO.CC. E UU.OO.SS.DD.</td>
		 		
		 		
		 		<% for (int j=0;j<sezione.getElencoPiani().size();j++){
					DpatPiano piano = (DpatPiano)sezione.getElencoPiani().get(j) ;
					for (int k=0; k<piano.getElencoAttivita().size(); k++){
						DpatAttivita att = (DpatAttivita)piano.getElencoAttivita().get(k) ;
						String value = "a";
						int charValue = value.charAt(0);
					  for(int ind=0;ind<att.getElencoIndicatori().size();ind++){
					  	DpatIndicatore indicatore = (DpatIndicatore)att.getElencoIndicatori().get(ind);%>
					  	<th class= "tooltip" style="font-weight: bolder;" id="i_<%=indicatore.getId()%>" colspan="1">
					  	<% String next = String.valueOf( (char) (charValue + ind));%>
					  	
					  	<%= ( indicatore.getNote()!=null && !indicatore.getNote().equals("") && indicatore.getNote().length()>30) ? 
					  	indicatore.getNote().substring(0, 10) + " <font size=\"1\">[...]</font>" : ((indicatore.getNote()!=null && !indicatore.getNote().equals("")) ? indicatore.getNote() : "")%><br><br> 
					  	<span><%=(indicatore.getNote()!=null && !indicatore.getNote().equals("") )? indicatore.getNote()+"\n"+indicatore.getDescription() : indicatore.getDescription()%></span>
					  	<%=(indicatore.getDescription().length()>34 && indicatore.getDescription().indexOf(' ', 34) > -1) ? 
					  indicatore.getDescription().substring(0, indicatore.getDescription().indexOf(' ', 34)) + " <font size=\"1\">[...]</font>" : indicatore.getDescription()%></th>
				<% 	  }  %>	  
			<%	}} %>
				</tr>
				</thead>
				<tbody>
			<% for (int s=0;s<dpat.getElencoStrutture().size();s++) {
					String color="#FFFFFF";
					String color_tr="#FFFFFF";
					OiaNodo struttura = (OiaNodo)dpat.getElencoStrutture().get(s);
					if (struttura.getN_livello()==2) {color="#FFFF00";}
					else {color="#FFFFFF";}
					if (s%2 == 0)
						color_tr = "#fff";
					else 
						color_tr = "#C0C0C0";
					%>
					<tr bgcolor="<%=color_tr%>">
						<td   id="struttura_<%=struttura.getId()%>" >
						<span><%=struttura.getDescrizione_lunga() %></span>
					</td>
						
						<% for (int j=0;j<sezione.getElencoPiani().size();j++){
								DpatPiano p = (DpatPiano)sezione.getElencoPiani().get(j);
							for (int k=0; k<p.getElencoAttivita().size(); k++){ 
								DpatAttivita a = (DpatAttivita)p.getElencoAttivita().get(k);
							 for(int ind=0;ind<a.getElencoIndicatori().size();ind++){
							 	DpatIndicatore in = (DpatIndicatore)a.getElencoIndicatori().get(ind);
							 	
							 	%>
							 	
					  	 
					  		 	
							 	<td style="width: 10px;" align="center">
							 
							 	<%=(struttura.getCompetenzeIndicatori().get(in.getId())!= null && struttura.getCompetenzeIndicatori().get(in.getId())==Boolean.TRUE ) ? "X" : "&nbsp;" %>
							 	</td>						
						<% 	 } %>
					 			
						<%	 }} %>
						
					
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
</body>

</body>



