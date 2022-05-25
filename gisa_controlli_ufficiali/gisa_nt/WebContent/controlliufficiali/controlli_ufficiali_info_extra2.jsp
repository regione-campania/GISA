<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<!-- JQUERY UI PER FINESTRE DI CONFERMA  -->
<script type="text/javascript" src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/jquery-ui-1.9.2.custom.css" />


<style type="text/css">
input[disabled], select[disabled], checkbox[disabled], textarea[disabled]
{
background-color: #dcdcdc;
color: #dcdcdc;
cursor: default;
} 
.thEstesi {
   background-color: #FFCC66 !important;
}
.tdEstesi {
   background-color: #ededed !important;
}
.bottoneEstesi {
background-color: #bdcfff !important; 
color: #000000 !important;
}

</style>

<!-- RELATIVO AL NUOVO CALENDARIO CON MESE E ANNO FACILMENTE MODIFICABILI -->
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/dateControl.js"></script>

<script>

function removeHtml(text){
	text = text.replace('<b>', '');
	text = text.replace('</b>', '');
	return text;
}

function showHide(elem) {
	
	if (elem!=null){
		if (elem.style.display =='none')
			elem.style.display ='block';
		else
			elem.style.display ='none';
	}
	
}

function showHideDatiEstesi(elem) {
	
	var x = document.getElementsByClassName('tableEstesi');
	
	var i;
	for (i = 0; i < x.length; i++) {
		
	    if (x[i].id != elem.id){
	    	x[i].style.display='none';
	    }
	    else if (x[i].id == elem.id){
	    	if (x[i].style.display=='none')
	    		x[i].style.display='block';
	    	else
	    		x[i].style.display='none';	    			
	    }
	}
	
}

function showDatiEstesi() {
	var x = document.getElementsByClassName('tableEstesi');
	
	var i;
	for (i = 0; i < x.length; i++) {
		x[i].style.display='block';
	}
	
}

function openPopupCodiceAzienda(id, url) {
	
	if (document.getElementById("allev_origine_"+id).disabled!=true){
		
	
	setCodiceAziendaTemp(id);
	
	  title  = '_types';
	  width  =  '800';
	  height =  '600';
	  resize =  'yes';
	  bars   =  'yes';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open(url, title, windowParams);
	  //newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	} }

function gestisciInfoDomestici(campo){
	var num = campo.value;
			
	var i = 1;
	for (i=1; i<=num; i++){
		if (i==5)
			break;
		document.getElementById('data_acquisto_'+i).style.display='';
		document.getElementById('allev_origine_'+i).style.display='';
		document.getElementById('data_acquisto_'+i).disabled=false;
		document.getElementById('allev_origine_'+i).disabled=false;
		
		if (document.getElementById('asterisco_data_acquisto_'+i)==null)
			document.getElementById('data_acquisto_'+i).outerHTML+='<label id=\"asterisco_data_acquisto_'+i+'\"><font color=\"red\">*</font></label>';
		if (document.getElementById('asterisco_allev_origine_'+i)==null)
			document.getElementById('allev_origine_'+i).outerHTML+='<label id=\"asterisco_allev_origine_'+i+'\"><font color=\"red\">*</font></label>';
		
	}
	if (num<4){
		for (var k = i; k<=4; k++){
			document.getElementById('data_acquisto_'+k).style.display='none';
			document.getElementById('allev_origine_'+k).style.display='none';
			document.getElementById('data_acquisto_'+k).disabled=true;
			document.getElementById('allev_origine_'+k).disabled=true;
			if (document.getElementById('asterisco_data_acquisto_'+k)!=null)
				document.getElementById('asterisco_data_acquisto_'+k).outerHTML='';
			if (document.getElementById('asterisco_allev_origine_'+k)!=null)
				document.getElementById('asterisco_allev_origine_'+k).outerHTML='';
		}
	}
	
	aggiornaCampi();
	
}

</script>

<script>

function aggiornaCampi ()
{
	for (i=1; i<=4; i++){
		
		var disabledData = 	$("#datiEstesi").find("input[id*='data_acquisto_"+i+"']").prop('disabled');
		var disabledAllev = 	$("#datiEstesi").find("input[id*='allev_origine_"+i+"']").prop('disabled');
		$("#datiEstesi").find("input[id*='data_acquisto_"+i+"']").prop( "disabled", disabledData );
		$("#datiEstesi").find("input[id*='allev_origine_"+i+"']").prop( "disabled", disabledAllev );
		var obbligatorio = 'si';
		if (disabledData==true)
			obbligatorio = 'no';
		$("#datiEstesi").find("input[id*='data_acquisto_"+i+"']").attr( "obbligatorio", obbligatorio );
		$("#datiEstesi").find("input[id*='allev_origine_"+i+"']").attr( "obbligatorio", obbligatorio );
		$("#datiEstesi").find("input[id*='data_acquisto_"+i+"']").attr( "obbligatorio", obbligatorio );
		$("#datiEstesi").find("input[id*='allev_origine_"+i+"']").attr( "obbligatorio", obbligatorio );
		
	}
}

function setDataAcquisto (name)
{

	$("#datiEstesi").find("input[id*='"+name+"']").val($("#datiEstesi").find("input[id*='"+name+"']").val());
	
}

function setCodiceAziendaTemp (id)
{
	document.getElementById("allev_codice_temp").value = id;
	
}

function setCodiceAzienda (code)
{
	var id = document.getElementById("allev_codice_temp").value;
	var campo = "allev_origine_"+id;
	document.getElementById(campo).value = code;
	$("#datiEstesi").find("input[id*='"+campo+"']").val($("#datiEstesi").find("input[id*='"+campo+"']").val());
	
}

function checkCodiceAzienda(campo){
	var code = campo.value;
	
	var check = true;
	var esito = '';
	if (code.length!=8){
		check = false;
		esito = 'Lunghezza codice azienda non con corretta.';
		}
	else{
		var numeri1 = code.substring(0,3);
		var lettere1 = code.substring(3,5);
		var numeri2 = code.substring(5,8);
				
		if (!isLetter(lettere1) || !isNumber(numeri1) || !isNumber(numeri2)){
			check = false;
			esito = 'Formato codice azienda non corretto.';
		} 
	}
	if (check!=true){
		alert(esito);
		campo.value = '';
	}
}


function isLetter(x){
	var regex=/^[a-zA-Z]+$/;
    if (!x.match(regex))
    {
        return false;
    }
    else
    	return true;
}

function isNumber(x)
{
    var regex=/^[0-9]+$/;
    if (!x.match(regex))
    {
        return false;
    }
    else
    	return true;
}

function gestisciControlloDocumentale(campo){
	
	if (campo.value=='1'){
		
		document.getElementById("num_capi_macellati").disabled="";
		document.getElementById("data_acquisto_1").disabled="";
		document.getElementById("allev_origine_1").disabled="";
		document.getElementById("data_acquisto_2").disabled="";
		document.getElementById("allev_origine_2").disabled="";
		document.getElementById("data_acquisto_3").disabled="";
		document.getElementById("allev_origine_3").disabled="";
		document.getElementById("data_acquisto_4").disabled="";
		document.getElementById("allev_origine_4").disabled="";
	}
	else
	{
		
		document.getElementById("num_capi_macellati").disabled="disabled";
		document.getElementById("data_acquisto_1").disabled="disabled";
		document.getElementById("allev_origine_1").disabled="disabled";
		document.getElementById("data_acquisto_2").disabled="disabled";
		document.getElementById("allev_origine_2").disabled="disabled";
		document.getElementById("data_acquisto_3").disabled="disabled";
		document.getElementById("allev_origine_3").disabled="disabled";
		document.getElementById("data_acquisto_4").disabled="disabled";
		document.getElementById("allev_origine_4").disabled="disabled";
	}
}


</script>


<script>// INIZIO DEFINIZIONE FUNZIONI PER POPUP MODALE

checkform = false;
function popolaCampi(idPianoInput, descPianoInput){
	
	 if(typeof idPianoInput === 'undefined' || typeof descPianoInput === 'undefined'){
		 idPianoInput = -1;
		 descPianoInput='';
		 };
	
	
	var idPiano = -1 ;
	idPiano = idPianoInput;
	
	var idControllo = -1;
	
	<% if (TicketDetails!=null) { %>
	idControllo = <%=TicketDetails.getId()%>;
	<%	} %>
	
	if (document.getElementById('div'+idPiano)!=null)
		return false;
	
			//Gestione dei campi per il PNAA
	
				
				$.ajax({
			        type: "GET",
			        url: "<%=request.getContextPath() %>/ServletFormCU?motivazione_piano_campione="+ idPiano+"&idControllo="+idControllo+"&codice_interno="+idPiano,
			        async: false,
			        dataType: "json",
			        error: function(XMLHttpRequest, status, errorThrown) { ;
			            alert("oh no!");
			            alert(status);
			        },
			        success: function (data, status) {
			           	var  toinsert = '';
			        	var html = '';
			        	
			        	var proprietario = "";
			        	if (data.length == 0){
			        		showInvia = false;
			        	}else{
			        		showInvia = true;
			        	}
					if (data.length > 0){
					
	    	        	toinsert = toinsert + '<br/> <div id = "div'+idPiano+'" class="divEstesiClass"> <input type="button" class="bottoneEstesi" value="Dati estesi per '+removeHtml(descPianoInput)+'" onClick="showHideDatiEstesi(dati'+idPiano+')"/> <br/>'+
	    	        	'<table id="dati'+idPiano+'" cellpadding="2" cellspacing="0" border="0" width="50%"'+
		        		'class="tableEstesi" style="display:none">'+
		        	'<tr>'+
		        		'<th colspan="2" class="thEstesi"><strong><dhv:label name="">Dati da inserire per '+descPianoInput+'</dhv:label></strong>'+
		        		'</th>'
		        	'</tr>';
	}
			        	for (var i = 0; i < data.length; i++) {
				        	
			        	    var row = data[i];
			        	    		
			        	            var label = row['label'];
			        	            var labelLink = row['label_link'];
			        	   			var type = row['type'];
			        	   			var name = row['name'];
			        	   			var lookupname = row['lookup'];
			        	   			var value = row['value'];
			        	   			var javascript = row['javascript'];
			        	   			var controlli = row['controlli'];
			        	   			var label_controlli = row['label_data'];
			        	   			var size = row['size'];
			        	   			var obbligatorio = row['obbligatorio'];
			        	   			var link = row['link_value'];
			        	   										
			        	     	if (obbligatorio==true)
			        	     		obbligatorio = 'obbligatorio = "si" ';
			        	     	else
			        	     		obbligatorio = 'obbligatorio = "no" ';
			        	        		       	        	
			        	  if (type == 'data'){
			            	//  alert ('in if');
			        		  toinsert = toinsert + '<tr>  <td class="tdEstesi"> <dhv:label name="">'+label+'</dhv:label></td> <td>';
			                  
			        		  if(row['obbligatorio']==false)
			        		  		toinsert = toinsert + '<input  readonly label="'+label+'" type="text" '+obbligatorio+javascript+' id="'+name+'" name="'+name+'" size="10" value="'+value+'" nomecampo="'+name+'" tipocontrollo="'+controlli+'" labelcampo="'+label_controlli+'" />&nbsp;'
			        		  		else
			        		  			toinsert = toinsert + '<input  readonly label="'+label+'" type="text" '+obbligatorio+javascript+' id="'+name+'" name="'+name+'" size="10" value="'+value+'" nomecampo="'+name+'" tipocontrollo="'+controlli+'" labelcampo="'+label_controlli+'" /><font color="red">*</font>&nbsp;'
								
			        		  			
				              		toinsert = toinsert + '<a href="#"" onClick="cal19.select3(document.forms[0].'+name+',\'anchor19\',\'dd/MM/yyyy\'); return false;" NAME="anchor19" ID="anchor19"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a></td></tr>';
								 

			              }else if (type == 'select'){

								 html = row['html'];
			            	  
			        		//  var toinsert = toinsert + '<tr><td><a href="javascript:popUp(\'Soggetto.do?command=Search&tipologiaSoggetto=1&popup=true\');">Ricerca soggetto</a></td></tr>'
			        		   toinsert = toinsert + '<tr><td class="tdEstesi"> <dhv:label name="">'+label+'</dhv:label></td><td>'+html+'</td></tr>'
			            	  }else if (type == 'textarea'){
								toinsert = toinsert + '<tr><td  class="tdEstesi" nowrap>'+label+'</td><td><textarea rows="10" cols="10" name="'+name+'" id="'+name+'"></textarea></td></tr>';
				            	  
			            	  } else if (type == 'hidden'){

							
			          	  
			        		    toinsert = toinsert + '<input type = "'+type+'" label="'+label+'" name="'+name+'" id="'+name+'" '+obbligatorio+' value ="'+value+'"/>';
			          	  }
			            	  else if (type == 'link'){
				            	//  alert ('in if');
				        		  toinsert = toinsert + '<tr>  <td  class="tdEstesi"  > </td> <td>';
				        		  toinsert = toinsert + '<a href ="'+javascript+'" id="'+name+'" name="'+name+'">'+label+'</a>&nbsp; </td></tr>';
				         }
			            	  
			            	  else{
			            	 
			          		  if(row['obbligatorio']==false)
			        	  			var  toinsert = toinsert + '<tr><td class="tdEstesi" nowrap>'+label+'</td><td><input label="'+label+'" type = "'+type+'" ' + obbligatorio+' name="'+name+'" id="'+name+'" value="'+value+'" maxlength="'+size+'" '+javascript+' /></td></tr>';
			        	  			else
			        	  				var  toinsert = toinsert + '<tr><td nowrap  class="tdEstesi" >'+label+'</td><td><input label="'+label+'" type = "'+type+'" ' + obbligatorio+' name="'+name+'" id="'+name+'" value="'+value+'" maxlength="'+size+'" '+javascript+' /><font color="red">*</font></td></tr>';
			        	  //	alert(toinsert);
			        	  }
			        	}
						toinsert = toinsert + "</div>";
						
			        	var mydiv = document.getElementById("datiEstesi");
			            var newcontent = document.createElement('div');
			            newcontent.innerHTML =  toinsert;

			            while (newcontent.firstChild) {
			                mydiv.appendChild(newcontent.firstChild);
			            }

		        		//document.getElementById('datiEstesi').innerHTML = toinsert;
		        	
			              
			        },
			        complete: function() {
			        	
			        		
			         
			        	
			        }
			    });
		}
	
	


</script>

<script>

function checkFormExtra()
{
	var msg = 'Impossibile salvare il modulo perchè sono stati selezionati elementi con dati estesi non compilati. Controllare i seguenti campi: \n\n';
	var esito = true;
	
	var esitoDate =	lanciaControlloDate();
	if (esitoDate==false)
		return esitoDate;
	
	var elementi = getAllElementsWithAttribute('obbligatorio');
	for (var i = 0; i<elementi.length;i++){
		if ((!document.getElementById(elementi[i].id).disabled) && (document.getElementById(elementi[i].id).value=='' || document.getElementById(elementi[i].id).value=='-1')){
			msg =  msg + document.getElementById(elementi[i].id).getAttribute('label') + '\n';
			esito = false;
		}
	}
	
	if (esito==false){
		alert(msg);
		showDatiEstesi();
	}
	
	return esito;
}

function getAllElementsWithAttribute(attribute)
{
  var matchingElements = [];
  var allElements = document.getElementById("datiEstesi").getElementsByTagName('*');
  for (var i = 0, n = allElements.length; i < n; i++)
  {    if (allElements[i].getAttribute(attribute) !== null && allElements[i].getAttribute(attribute) === 'si')
    {
      // Element exists with attribute. Add to array.
      matchingElements.push(allElements[i]);
    }
  }
   return matchingElements;
}
</script>

<script>
function mostraEstesi(){
	//document.getElementById('datiEstesi').innerHTML = '';
	
	var arrayCodici = [];
	var i = 1;
	//Se la riga dei piani è visualizzabile (Ho scelto in piano di monitoraggio)
	
	
		
	var secondamodifica = false;
	
	while (document.getElementById('piano_monitoraggio'+i)!=null){
		secondamodifica = true;
		var cod = document.getElementById('piano_monitoraggio'+i).value;
		var lab = document.getElementById('piano'+i).innerHTML;
		getCodiceInternoTipoIspezione(cod);
		cod = codiceInternoTipoIspezione;
		arrayCodici[i-1] = cod;
		popolaCampi(cod, lab);
		i++;
		}
	
	if (secondamodifica==false){
		while (document.getElementsByName('piano_monitoraggio'+i)[0]!=null){
		var cod = document.getElementsByName('piano_monitoraggio'+i)[0].value;
		var lab = document.getElementById('piano'+i).innerHTML;
		getCodiceInternoTipoIspezione(cod);
		cod = codiceInternoTipoIspezione;
		arrayCodici[i-1] = cod;
		popolaCampi(cod, lab);
		i++;
		}
	}
	
	
	//Attivita
	var tipiispezione  = document.getElementsByName("tipoIspezione");
	for (j=0;j<tipiispezione.length ; j++)
	{
		
			getCodiceInternoTipoIspezione(tipiispezione[j].value);
			cod = codiceInternoTipoIspezione;
			lab = tipiispezione[j].innerHTML;
			if (lab=='')
				lab = 'ATTIVITA DI MACELLAZIONE DI ANIMALI A DOMICILIO';
			arrayCodici[i-1] = cod;
			popolaCampi(cod, lab);
			i++;
	
	}
		
		
	//Rimuovo i div di piani che non sono più selezionati
	 var allElements = document.getElementById("datiEstesi").getElementsByClassName('divEstesiClass');
	  for (var i = 0, n = allElements.length; i < n; i++)
	  {    
		  var id = allElements[i].id;
		  var idCodice = id.replace('div', '');
		  if (arrayCodici.indexOf(idCodice)==-1)
	    {
			  var mydiv = document.getElementById("datiEstesi");
	          mydiv.removeChild(allElements[i]);
	            
	    }
	  }
	  
	//Chiamo onchange su tutte le select per far attivare i javascript
		 var allSelectElements = document.getElementById("datiEstesi").getElementsByTagName('select');
		  for (var i = 0, n = allSelectElements.length; i < n; i++)
		  {    
			  allSelectElements[i].onchange();
		  }
}

</script>

<script>function controllaDatiEstesi(){
	mostraEstesi();
	return checkFormExtra();
}</script>


<input type="button" value="Gestisci campi estesi" onClick="mostraEstesi()" style="display:none"/>
<input type="button" value="invio" onClick="checkFormExtra()" style="display:none"/>

<script>
//mostraEstesi();
</script>


