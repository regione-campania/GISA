<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

<%@page import="java.text.SimpleDateFormat"%><html manifest="offline.manifest"><head>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<meta http-equiv="content-type" content="text/html; charset=UTF-8"><link rel="stylesheet" type="text/css" href="CheckListImprese.do_files/colore_demo.css">	
<link rel="stylesheet" type="text/css" href="css/demo.css">		
<link rel="stylesheet" type="text/css" href="css/custom.css">	
<!-- import necessari al funzionamento della finestra modale di locking -->	
<link rel="stylesheet" type="text/css" href="css/custom.css">
<link rel="stylesheet" type="text/css" href="css/modalWindow.css">
<link rel="stylesheet" type="text/css" href="css/capitalize.css">		
<script src="javascript/modalWindow.js"></script>
<script src="javascript/jquerymini.js"></script>

<script>loadModalWindow();</script>
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
	$('.window').click(function (e) {
		//Cancel the link behavior
		e.preventDefault();
		
		$('#mask').hide();
		$('.window').hide();
	});

	$('.close').click(function (e) {
		//Cancel the link behavior
		e.preventDefault();
		
		$('#mask').hide();
		$('.window').hide();
		if (risposta==true &&  document.addAccountAudit.stato.value == 'Definitiva')
			document.addAccountAudit.submit();
		else
			if (document.addAccountAudit.stato.value == 'Temporanea')
				document.addAccountAudit.submit();
	});			
	
	//if mask is clicked
	$('#mask').click(function () {
		$(this).hide();
		$('.window').hide();
		if (risposta==true &&  document.addAccountAudit.stato.value == 'Definitiva')
			document.addAccountAudit.submit();
		else
			if (document.addAccountAudit.stato.value == 'Temporanea')
				document.addAccountAudit.submit();
	});			
	
});


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

</script>
<style>

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
	width: 375px;
	height: 358;
	display: none;
	z-index: 9999;
	padding: 20px;
}

#boxes

#dialog {
	width: 65%;
	height: 60%;
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




<input type="hidden" id="idChecklist" name="idChecklist"
	value="<%=request.getAttribute("idChecklist_corrente")%>" />


<div id="boxes">



<div id="dialog" class="window"><a href="#" class="close" /><font
	color="red">CHIUDI</font></a>
	<br><br>
	<h1>Guida Attivazione Plugin</h1>
	 <br><br>
	 <li><h3>Verificare che JAVA stia funzionando all'interno del vostro Browser(Firefox). Quindi aprite Firefox e selezionate dal menu "Strumenti" la voce "Componenti Aggiuntivi" come riporta la figura</h3></li>
<br><br>
<img src = "images/8.JPG" width="60%" height="70%"/>
<br><br>
<li><h3>Selezionare dalla schermata che si aprirà la voce "plugin" presente nel menu a sinistra.e verificare che nell'elenco sia presente "Java (Tm)". Cliccare quindi sul pulsante abilita presente a lato (destro).Se gia risulta abilitato non occorrerà fare nulla</h3></li>
<br><br>
<img src = "images/9.JPG" width="70%" height="80%"/>
<br><br>
<li><h3>A questo Punto è possibile accedere al sistema Gisa.A questo punto dopo aver effettuato l'accesso al sistema apparirà un messaggio come riportato nell'immagine sottostante. spuntare le duce voci evidenziate in rosso e fare click su esegui</h3></li>
<br><br>
<img src = "images/10.JPG" width="30%" height="30%"/>
<br><br>

<li><h3>
  Cliccare sull'icona mostrata nell'immagine seguente che si trova accanto alla barra degli indirizzi di firefox.
	 <br><br>
	 <IMG SRC="checklist/javatm1.JPG" NAME="javatm1.jpg" ALIGN=center BORDER=0></h3></li>
	 <br><br>
	 <li><h3>
	 Dopo aver cliccato sull'iconcina della figura precedente apparità una finestra come mostrata in figura, in cui
	 si chiede se attivare il plugin, cliccale la voce "Attiva tutti i plugin" .<br><br> 
	 	 <IMG SRC="checklist/javatm2.JPG" NAME="javatm2.jpg" ALIGN=center BORDER=0></h3></li>
	 	 <br><br>
	 Dopo aver attivato i plugin cliccare su chiudi e premere il pulsante F5 per aggiornare la pagina.Quindi compilare normalmente la checklist!
	 <br><br>
	 
	 Attenzione !! nel caso in cui Java non è abilitato nel vostro browser le risposte saranno salvate nella modalità online , 
	 ma in caso di assenza di connessione durante il salvataggio verrà perso il lavoro svolto. Si invita quindi a seguire i passi
	 necessari per garantire il salvataggio offline.
	 
</div> 

<!-- Mask to cover the whole screen -->
<div id="mask"></div>

</div>

<%
ControlloUfficiale.setActionChecklist();

%>


<script>

var flagJava ;
function controllo_java() 
{
	var nAgt = navigator.userAgent; 
	var fullVersion  = ''+parseFloat(navigator.appVersion); 
	verOffset=nAgt.indexOf("Firefox")
	fullVersion = nAgt.substring(verOffset+8);
	flagJava=navigator.javaEnabled();
	fullVersion = nAgt.substring(verOffset+8);
	//if (flagJava==false) 
	// alert('Attenzione!! java non presente quindi non sarà garantito il salvataggio offline.Seguire la guida.!');
	
}

function setParametri()
{

	try
	{
	document.Objapplet.getFieldForm('addAccountAudit',<%=User.getUserId()%> , '<%=User.getUsername()%>');
	}catch(e)
	{
	throw e;	
	}
}
var risposta ;
function save(btn)
{ 
	controllo_java();
	if(flagJava!=false)
	{

		try
		{
	setStato(btn);
	if (btn == 1)
	{
		risposta = checkForm() ;
		if(risposta==true)
		{

			loadModalWindow();
			alert('Le risposte stanno per essere trasmesse al Server. Attendere Il completamento');
			document.addAccountAudit.stato.value = 'Definitiva';
			
			console.log('Il server e\' UP, la checklist viene inviata a GISA');
			document.addAccountAudit.submit();
								
							
			
		}
	}
	else
	{
		
		loadModalWindow();
		
		document.addAccountAudit.stato.value = 'Temporanea';


	
				
				console.log('Il server e\' UP, la checklist viene inviata a GISA');
				document.addAccountAudit.submit();
					
	
		

		
	}
		}catch(e)
		{
			
			//alert('Errore nel salvare la checklist. Assicurarsi di aver Attivato il componente firefox altrimenti non è garantito il salvataggio Offline');
			loadModalWindowUnlock();
			//$('a[name=modal]').click();
			if (btn == 1)
			{
				risposta = checkForm() ;
				if(risposta==true)
				{
					document.addAccountAudit.stato.value = 'Definitiva';
					loadModalWindow();
					document.addAccountAudit.submit();
					
					
				}
			}
			else
			{
				document.addAccountAudit.stato.value = 'Temporanea';
				loadModalWindow();
				document.addAccountAudit.submit();
				
			}
			
			
		}
	}
	else
	{
		//$('a[name=modal]').click();
		if (btn == 1)
		{
			risposta = checkForm() ;
			if(risposta==true)
			{
				document.addAccountAudit.stato.value = 'Definitiva';
				loadModalWindow();
				document.addAccountAudit.submit();
				//document.addAccountAudit.submit();
			}
		}
		else
		{
			document.addAccountAudit.stato.value = 'Temporanea';
			loadModalWindow();
			document.addAccountAudit.submit();
			//document.addAccountAudit.submit();
		}

		}
}


</script>
 
<!-- 
<br>
<font color = "red">Attenzione! In caso di salvataggio definitivo o in caso di assenza di connessione durante il salvataggio temporaneo , il sistema chiedera' 
all'utente se desidera salvare il lavoro in locale. Accettare cliccando sul pulsante permetti.Questo messaggio apparirà anche nel caso di carica checklist.
</font> 
<br><br>
-->
				<a  style="display: none" href="#dialog" name = "modal" >view</a>

<input id = "btnSave" type="button" value="Salva Definitiva" onclick="javascript: save(1);">
<%
if (!Audit.getStato().equals("Definitiva"))
{
%>
<input id = "btnSave3" type="button" value="Salva Temporanea" onclick="javascript: save(0);">
<%} %>
<br/><br/>
<font color="red">Il Punteggio storico delle non conformità, non comprende il punteggio dei controlli ufficiali ancora aperti. Questi ultimi verranno conteggiati nella successiva sorveglianza.</font>
<br/>
<br/> 

<div id="salva_temp">

<br/>
<br/>
<%
if(request.getAttribute("ErroreChecklist")!=null)
{%>
<font color = "red">Attenzione! Si è verificato un errore durante il salvataggio. Contattare l'Help-Desk</font>
<%
}


%>


</div>

<input type="hidden" name="stato" value="<%="Definitiva" %>" />
<input type="hidden" name="idC" id = "idC"  value="<%=TicketDetails.getId() %>" />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr class="containerBody">
    <th colspan="2">
      <strong><dhv:label name="audit.Audit">Check List</dhv:label></strong>
    </th>
  </tr>
  <input type="hidden" id="TipoC" name="TipoC" value="<%=ControlloUfficiale.getTipoCampione() %>"/>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="audit.categoriaOsa">Tipo Checklist</dhv:label>
    </td>
    <td>
      <%= toHtml(OrgCategoriaRischioList.getSelectedValue(Audit.getTipoChecklist())) %>
      <input type = "hidden" id = "checklistname" value = " <%= OrgCategoriaRischioList.getSelectedValue(Audit.getTipoChecklist()) %>" />
    </td>
  </tr>
 
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Punteggio Check List
    </td>
    <td>
      <%= toHtml(String.valueOf(Audit.getLivelloRischio())) %>
     
    </td>
  </tr>

 
  <input type="hidden" name="dataCK" value="<%=Audit.getData1() %>"/>
</table>
</br>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
<%
int domandaElementId = 0;
String scriptDichiar = "idTypeList = new Array(); operazioneList = new Array(); notaList = new Array(); valoreRangeList = new Array();"; 
scriptDichiar += " idList = new Array(); parentIdList = new Array();grandParentIdList= new Array(); rispostaList = new Array(); puntiList = new Array(); requiredList = new Array();";
String scriptFunc = "";
int indexChecklistList = 0;

Iterator itrTypeList = typeList.iterator();
Iterator auditCkList=auditChecklist.iterator();
Iterator auditCkListType=auditChecklistType.iterator();
int numcapitolodisabilitati = 0;
boolean abilitato=false;

if ( itrTypeList.hasNext() ) {
	
	
	int progressivoCapitolo = 0;
	int i=0;
	Iterator auditCkListType1=auditChecklistType.iterator();
	while (itrTypeList.hasNext()) {
		
	CustomLookupElement thisTypeList = (CustomLookupElement) itrTypeList.next();
	 progressivoCapitolo =  progressivoCapitolo +1;
	int checklistTypeId = Integer.valueOf(thisTypeList.getValue("code"));
    String checklistDescription = thisTypeList.getValue("description");
    String checklistRange = thisTypeList.getValue("range");
   
    AuditChecklistType a =null;
   
    if( auditCkListType1.hasNext())
    {
    	 
   		 a =	(AuditChecklistType)auditCkListType1.next();
   		
    }

    //String is_disabilitato_solo_xlaprima = (String) thisTypeList.getValue("is_disabilitato_solo_xlaprima");
    if(a!=null)
    {
    	abilitato = a.isIs_abilitato();
    }
    CustomLookupList checklist = (CustomLookupList) checklistList.get(indexChecklistList);
    indexChecklistList++;
    scriptDichiar += "idTypeList[\""+ (indexChecklistList) +"\"] = " + checklistTypeId + ";";
    //scriptDichiar += "aggiornaChecklistType("+indexChecklistList+",'x','','');";

%>

<script>

domandePerCapitolo[<%=checklistTypeId %> ] = new Array();
sottoDomandePerCapitolo[<%=checklistTypeId %> ] = new Array();

</script>
  <tr class="containerBody">
    <th colspan="7" style="background-color: #ccff99; padding: 5px;"><%= checklistDescription%></th>
  </tr>
  <tr class="containerBody">
    <th>&nbsp;</th>
    <th width="50%">Domanda</th>
    <th width="50%">Ulteriore quesito in caso di risposta affermativa</th>
    <th>Modalità di controllo</th>
    <th>SI</th>
    <th>NO</th>
    <th>Punti</th>
  </tr>
  
   
<%
// abilitato indica se il capitolo salvato è stato risposto o no 

if(abilitato == false)
{
	
	  %>
	  <script>
	  capitolidadisabilitare[<%=numcapitolodisabilitati %>] = <%=checklistTypeId%>
	  progressiviCapitoli[<%=numcapitolodisabilitati %>] = <%=progressivoCapitolo%>
	
	  </script>
	  
	  <%		
	  numcapitolodisabilitati+=1;
	  
	 
}

		  
		  if(thisTypeList.isDisabilitabile()==true)
		  {
			  %>
			  
			  <tr  class="row4" id = "">
  
  <td width="50%" colspan="4" align="center">  QUESTO CAPITOLO DEVE ESSERE COMPILATO ?</td>
  
  <td align="center"><input type="radio" id ="risposta<%=checklistTypeId%>1" <%if(abilitato == true){%>checked="checked" <%} %> value ="1" class='domandaCapitolo' name = "disabilita<%=checklistTypeId%>1" onclick="disabilitaCapitolo('<%=checklistTypeId %>','si',<%=i %>,'<%=progressivoCapitolo %>')"/></td> 
	     <td align="center">
  	 	<input	type="radio" value = "2" id ="risposta<%=checklistTypeId%>2" <%if(abilitato == false){%>checked="checked" <%} %> class='domandaCapitolo' name = "disabilita<%=checklistTypeId%>1" onclick="disabilitaCapitolo('<%=checklistTypeId %>','no',<%=i %>,'<%=progressivoCapitolo %>')" />
  	 			
  	 </td>
  <td>&nbsp;</td>
</tr>
			  
			  <%
			  
		  }
		  else
		  {
			  %>
			  <input type = "hidden" id ="risposta<%=checklistTypeId %>1" class='domandaCapitolo' name = "disabilita<%=checklistTypeId %>1" value ="3">
			  <%
		  }
		

%>
<input type="hidden" name="isDisabilitabile" id="isDisabilitabile<%=checklistTypeId %>" value="<%=thisTypeList.isDisabilitabile() %>">

<input type="hidden" name="idLastDomanda" id="last" value="<%=Audit.getIdLastDomanda() %>">

  <%
  Iterator itrChecklist = checklist.iterator();
  if ( itrChecklist.hasNext() ) {
		
	String descrizione=null;
    String level = null;
    int rowid = 0;
    int rowid2 = 5;
    int rowidcurr = 0 ;
    String last_gp = "" ;
    int numDomanda =0;
    
    while (itrChecklist.hasNext()) {
    	i++; 
      rowid = (rowid != 1?1:2);
      CustomLookupElement thisChecklist = (CustomLookupElement) itrChecklist.next();
      boolean enabled = thisChecklist.getValue("enabled") == "true" ? true : false;
      boolean defaultItem = thisChecklist.getValue("default_item") == "true" ? true : false;
      String id = thisChecklist.getValue("id");
      String grandParentId = thisChecklist.getValue("grand_parents_id");
      String super_domanda = thisChecklist.getValue("super_domanda");
      String parentId = thisChecklist.getValue("parent_id");
      String domanda = thisChecklist.getValue("domanda");
      descrizione = thisChecklist.getValue("descrizione");
      String puntiNo = thisChecklist.getValue("punti_no");
      String puntiSi = thisChecklist.getValue("punti_si");
      level = thisChecklist.getValue("level");
      int code = id.startsWith("--") ? -1 : Integer.parseInt(id);
      //Iterator auditCkList=auditChecklist.iterator();
      //Iterator auditCkListType=auditChecklistType.iterator();
      
     
      
    boolean risposto	= false;
    Boolean risposta	= false;
    int punti			= 0;
   	String stato = "" ;
	AuditChecklist audiCkListTemp	= null;
	Iterator array					= auditChecklist.iterator();
	while( array.hasNext() && !risposto )
	{ 
		audiCkListTemp = (AuditChecklist)array.next();
		risposto = ( Integer.parseInt(id) == audiCkListTemp.getChecklistId() );
	}
	
	if( risposto )
	{
		
		risposta	= audiCkListTemp.getRisposta();
		code		= id.startsWith("--") ? -1 : Integer.parseInt(id);
		punti		= audiCkListTemp.getPunti();
		stato = audiCkListTemp.getStato();
	}
	
	scriptDichiar 	+= "rispostaList["+id+"]="+risposta+";";
	  scriptDichiar += "grandParentIdList["+id+"]="+grandParentId+";";
	  scriptDichiar += "parentIdList["+id+"]="+parentId+";";
      scriptDichiar += "idList[\""+ (domandaElementId++) +"\"] = " + code + ";";
      scriptDichiar += "aggiornaListElementModify3("+code+","+parentId+","+grandParentId+"," + ( (!stato.equalsIgnoreCase("non risposta"))?(risposto) ? ( (risposta) ? (puntiSi) : (puntiNo) ) : ("0") : "0" ) + ",'" + ((stato.equalsIgnoreCase("risposta"))?(risposto) ? ( (risposta) ? ("si") : ("no") ) : (""):("") ) + "','"+stato+"');";
      scriptFunc += "function func"+code+"(risp){";
      scriptFunc += "  if(risp == 'si') {";
      scriptFunc += "    document.getElementById(\"punti"+code+"\").value = " + puntiSi + ";";
      scriptFunc += "    aggiornaListElement2("+code+","+parentId+","+grandParentId+","+puntiSi+",risp,"+puntiNo+");";
      scriptFunc += "  }";
      scriptFunc += "  if(risp == 'no') {";
      scriptFunc += "    document.getElementById(\"punti"+code+"\").value = " + puntiNo + ";";
      scriptFunc += "    aggiornaListElement2("+code+","+parentId+","+grandParentId+","+puntiNo+",risp,"+puntiSi+");";
      scriptFunc += "  }";
      scriptFunc += "}";
      %>
      <script>
           
           <%if(parentId.equals("-1"))
       {
       %>
      

       	domandePerCapitolo[<%=checklistTypeId %> ][<%=numDomanda%> ]  = <%=code%>;
     <%

           }
       else
       {
     %>
     	
       	sottoDomandePerCapitolo[<%=checklistTypeId %> ][<%=numDomanda%> ]  = <%=code%>;
     <%
           }
           
           %>
          
           </script>
           <%
      
      
      
           if ((!grandParentId.equals("-1") && !grandParentId.equals(last_gp) ) )
           {
          	 
          	 if (rowid2==5)
          		 rowid2 = 6;
          	 else
          		 rowid2 = 5;
          	 rowidcurr = rowid2;
          	 last_gp = grandParentId ;
           }
           else
            if (parentId != null && !parentId.equals("-1") && grandParentId.equals("-1")) { 
              rowid = (rowid != 1?1:2);
              rowidcurr = rowid;
            } else {
          	  if (grandParentId.equals("-1"))
          	  {
          		  if(super_domanda!=null && super_domanda.equalsIgnoreCase("true")){
          			  if (rowid2==5)
          				  rowidcurr = 6;
          		    	 else
          		    		 rowidcurr = 5;
          		  }
          		  else
          		  {
          		  //rowid = (rowid != 1?1:2);
          	        rowidcurr =rowid;
          		  }
          	  }
            }
     
      
     
     
      auditCkList=auditChecklist.iterator();
      
     
        		  
        		  %>
        		  
        		  <%
        		  
        	
        		  %>
        		  
        		   <tr class="row<%= rowidcurr %>" id="<%=i %>">
	    <td valign="center"><%= level %></td>
  <%if (parentId == null || parentId.equals("-1")) {%>
        <td valign="center" <%if(super_domanda!=null && super_domanda.equalsIgnoreCase("true")){%>colspan="2"<%} %>><%= toHtml(domanda) %></td>
    	<%if(super_domanda ==null || super_domanda.equalsIgnoreCase("false")){%>
        <td>&nbsp;</td>
        <%} %>    
	
  <%} else {%>
        <td>&nbsp;</td><td valign="center"><%= toHtml(domanda) %></td>
  <%}%>
        <td valign="center"><%= toHtml(descrizione) %></td>
        
	       	        
        	  <%//}
        	  
        	String siChecked="";
         	String noChecked="";
         	
          scriptFunc += "function func2"+code+"(risp){";
  	      scriptFunc += "  if(risp == 'si') {";
  	      scriptFunc += "    document.getElementById(\"punti"+code+"\").value = " + puntiSi + ";";
  	      scriptFunc += "    aggiornaListElement2("+code+","+parentId+","+grandParentId+","+puntiSi+",risp,"+puntiNo+");";
  	      scriptFunc += "  }";
  	      scriptFunc += "  if(risp == 'no') {";
  	      scriptFunc += "    document.getElementById(\"punti"+code+"\").value = " + puntiNo + ";";
  	      scriptFunc += "    aggiornaListElement2("+code+","+parentId+","+grandParentId+","+puntiNo+",risp,"+puntiSi+");";
  	      scriptFunc += "  }";
  	      scriptFunc += "}"; 

     %>
     
     <%
     
     
     %>
     
     
     
    	 <td align="center">
    	 	<input	type="radio"
    	 			id="risposta<%= code%>1"
    	 			name="risposta<%= code%>"
    	 			value="1"
    	 			onclick="javascript:func<%= code%>('si'); lastDomanda(<%=i %>,<%=rowid %>);"
    	 			<%=(! stato.equalsIgnoreCase("non risposta"))?(risposto && risposta) ? ("checked=\"checked\"") : (""):("") %> />
    	 </td>
    	 
    	 <td align="center">
    	 	<input	type="radio"
    	 			id="risposta<%= code%>2"
    	 			name="risposta<%= code%>" 
    	 		
    	 			value="0"
    	 			onclick="javascript:func<%= code%>('no'); lastDomanda(<%=i %>,<%=rowid %>);"
    	 			<%=(! stato.equalsIgnoreCase("non risposta"))?(risposto && !risposta) ? ("checked=\"checked\"") : (""):("") %>/>
    	 </td>
    	 
         <td align="center"><%if(puntiNo.equals(puntiSi) && puntiNo.equals("0")){ %>&nbsp; <input style="width: 30px; background-color: #cccccc"  type="hidden" id="punti<%= code%>" name="punti<%= code%>" /><%}else{ %><input style="width: 30px; background-color: #cccccc" readonly type="text" id="punti<%= code%>" name="punti<%= code%>" value = "<%=punti %>" /> <%} %></td>  
	   
  	 </tr>
 <%
 numDomanda++;  	
  %>    
<%}//}//}}fine ciclo secondo while
%> 
<%
String nota = "";
String add="";
String sub="";
String operazione= "";
String numero="";
if(auditCkListType.hasNext())
{
	
	AuditChecklistType act=(AuditChecklistType)auditCkListType.next();
	operazione=act.getOperazione();
	if(operazione.equals("+")||operazione.equals("-"))
	{	
		nota=act.getNota();
		numero=Integer.toString(act.getValoreRange());
		String oper = act.getOperazione(); 
		String nt = act.getNota();
		//scriptDichiar += "\naggiornaChecklistType(" + indexChecklistList + ",'x','" + numero + "','');";
		scriptDichiar += "\naggiornaChecklistType(" + indexChecklistList + ",\""+oper+"\",\"" + numero + "\",\""+nota+"\");";
	}
}
if(operazione!=null)
{
	String color2= null;
	String color = null;
	if(operazione.equals("+"))
		add="checked";
	    
	if(operazione.equals("-"))
		sub="checked";
	    
	
} %>


<%


if(indexChecklistList != typeList.size())  {%>

<tr class="row<%= rowid+1%>">
  <td><%= Integer.parseInt(level)+1%></td>
  <td colspan="3">Esistono delle condizioni particolari non contemplate sopra che possono diminuire o aumentare il punteggio di rischio? 
  Se si, riportarle qui sotto aggiungendo o sottraendo un punteggio nel range +<%= checklistRange%>, -<%= checklistRange%> da scrivere nella casella a lato</td>
  <td>
    <input type="button"  <%= (operazione.equals("+")? ("style='background:#66ff00'"): ("style = 'background:#ffffff'")) %> id="btnAggiungiPunti<%= indexChecklistList%>" value="+" onclick="javascript:aggiornaChecklistType(<%= indexChecklistList%>,'+',valoreRange<%= indexChecklistList%>.value, '',<%= checklistRange%>);" checked="<%= add %>" disabled="disabled" />
  </td>
  <td>
    <input type="button" <%= (operazione.equals("-")? ("style'background:#66ff00'"): ("style = 'background:#ffffff'")) %> id="btnSottraiPunti<%= indexChecklistList%>" value="-" onclick="javascript:aggiornaChecklistType(<%= indexChecklistList%>,'-',valoreRange<%= indexChecklistList%>.value, '',<%= checklistRange%>);" checked="<%= sub %>" disabled="disabled"/>
  </td>
  <td>
    <input type="text" style="width: 30px;" id="valoreRange<%= indexChecklistList%>"  name="valoreRange<%= checklistTypeId%>" value="<%= numero %>" onmouseout="abilitaPulsanti(<%= indexChecklistList%>)"/>
    <input type="hidden" id="operazione<%= indexChecklistList%>" name="operazione<%= indexChecklistList%>"/>
  </td>
</tr>
<tr class="row<%= rowid+1%>" >
  <td colspan="7">
  <textarea rows="3" cols="80" id="nota<%= indexChecklistList%>"   name="nota<%= indexChecklistList%>" >
  <%=nota %>
  </textarea>
  </td>
</tr>

<%}  } else {%>
<tr class="containerBody">
<td colspan="7"><dhv:label name="">Nessuna domanda presente.</dhv:label></td>
</tr>
<%}%>


<%-- prova --%>
<% 
	int indice = 1 + indexChecklistList;
}
%>

<%if((ControlloUfficiale.getNumeroAudit()==0 || ControlloUfficiale.getNumeroAudit()==1) && Audit.isPrincipale()==true){
	  
	%>
	<tr >
	  <td colspan="1">&nbsp;</td>
	  <td colspan="3">Punteggio storico delle non conformità (N.B. tale punteggio corrisponde alla somma dei punteggi delle non conformità rilevate durante i controlli ufficiali degli ultimi 5 anni dalla data di oggi, ovviamente con l'esclusione dei punteggi delle check list compilate nell'ambito della sorveglianza).</br></td>
	  <td colspan="2">
	    <%--<input type="button" id="btnAggiungiPunti<%= indice%>" value="      +      " onclick="javascript:aggiornaTot(<%= indice%>, '+',punteggioUltimiAnni.value);"/>--%>
	  </td>
	  <td>
	    <input type="text" style="width: 80px; background-color: #cccccc" readonly id="punteggioUltimiAnni" name="punteggioUltimiAnni" value="<%=(Audit.isPrincipale())? Audit.getPunteggioUltimiAnni()+"" : "0"%>"/>
	    </td>
	</tr>

	<%
	}else{
	
		  %>

	<tr>
	  <td colspan="1">&nbsp;</td>
	  <td colspan="3">Punteggio delle Check List già Compilate nello Stesso Controllo</br></td>
	  <td colspan="2">
	      </td>
	  <td>
	    <input type="text" style="width: 80px; background-color: #cccccc" readonly <%if (request.getAttribute("punteggioCheckList")!=null){ %> value="<%= request.getAttribute("punteggioCheckList") %><%} %>"/>
	    </td>
	</tr>

	<input type="hidden" style="width: 80px; background-color: #cccccc"  id="punteggioUltimiAnni" name="punteggioUltimiAnni" value="0"/>
	    
	<%  
	}
} else {%>
<tr class="containerBody">
<td colspan="7"><dhv:label name="">Nessuna Check List presente.</dhv:label></td>
</tr>
<%}%>


<%-- fine prova --%>


<tr class="containerBody" style="background-color: #ffff66; font-weight: bolder;">
  <td colspan="3">&nbsp;</td>
  <td nowrap>Punteggio Totale di Questa Check List</td>
  <%--if(Audit.getLivelloRischioFinale()== -1){ --%>
  <td colspan="3"><input style="width: 80px; background-color: #cccccc" type="text" readonly id="livelloRischio" name="livelloRischio" value="<%= Audit.getLivelloRischio() %>">
 <input type="hidden" id="livelloRischioFinale" name="livelloRischioFinale" value= "<%= Audit.getLivelloRischio() %>"/>
 
</tr>

<tr class="containerBody" style="background-color: #ffff66; font-weight: bolder;">
  <td colspan="3">&nbsp;</td>
  <td nowrap>Categoria Rischio con il nuovo punteggio</td>
  <td colspan="3"><input style="width: 80px; background-color: #cccccc" type="text" readonly id="categoriaRischio" name="categoriaRischio" value=""/>
  <%-- <input style="width: 80px; background-color: #cccccc" type="hidden" readonly id="livelloRischioFinale" name="livelloRischioFinale" value=<%= Audit.getLivelloRischioFinale() %>>--%>
  </td>
</tr>

</table>
<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="accounts.accounts_add.AdditionalDetails">Additional Details</dhv:label></strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td valign="top" nowrap class="formLabel">
      <dhv:label name="audit.note">Note</dhv:label>
    </td>
    <td>
      <TEXTAREA name="note" ROWS="3" COLS="50"><%= toHtml(Audit.getNote())%></TEXTAREA>
    </td>
  </tr>
</table>
<br/>

<!-- SALVATAGGIO CHECKLIST OFFLINE -->

<input id = "btnSave" type="button" value="Salva Definitiva" onclick="javascript: save(1);">
<%
if (!Audit.getStato().equals("Definitiva"))
{
%>
<input id = "btnSave3" type="button" value="Salva Temporanea" onclick="javascript: save(0);">
<%} %>


<%
if (request.getAttribute("isSalvata")!=null)
{
	
	
	ControlloUfficiale.setActionChecklist();
	String isSalvata = (String)request.getAttribute("isSalvata") ;
	if (isSalvata.equals("true") )
	{

		
		%>
		<input type = "hidden" id = "path" value = "<%=ControlloUfficiale.getUrl_checklist()%>.do?command=Modify&aggiorna=true&idControllo=<%= Audit.getIdControllo()%>&idC=<%=TicketDetails.getId()%>&id=<%= Audit.getId() %>&orgId=<%= OrgDetails.getAltId() %>">
		<%
	
	}
	else
	{
		%>
				<input type = "hidden"  id = "path" name = "path" value = "<%=ControlloUfficiale.getUrl_checklist()%>.do?command=Add&orgId=<%= OrgDetails.getAltId() %>&idC=<%=TicketDetails.getId()%>&isPrincipale=false&idControllo=<%= Audit.getIdControllo()%>&accountSize=<%=Audit.getTipoChecklist() %>">
		
		
		<%
	}


}

%>



<input type = "hidden" name = "urlservlet" value = "ChecklistServlet"
<input id = "btnSave2" type="button" value="Salva Definitiva" onclick="save(1)">

<input type = "hidden" id = "idChecklist" value = "<%=Audit.getTipoChecklist() %>">
<input type="hidden" name="dosubmit" value="true" />
<input type="hidden" name="rowcount" value="0">
<input type="hidden" name="return" value="<%= request.getParameter("return") %>" />
<input type="hidden" name="idControllo" id = "idControllo" value="<%= Audit.getIdControllo()%>" />

<input type="hidden" name="id" id = "idAudit" value="<%= Audit.getId() %>" />
<input type="hidden" name="orgId" id = "orgId" value="<%= OrgDetails.getAltId() %>" />
<script language="JavaScript">
<%= scriptDichiar %>
<%= scriptFunc %>

 
for(z=0; z<capitolidadisabilitare.length;z++)
{
	
	 disabilitaCapitolo(capitolidadisabilitare[z],'no',-1,progressiviCapitoli[z]);
}
//loadModalWindowUnlock() ;
</script>

<script>loadModalWindowUnlock()</script>