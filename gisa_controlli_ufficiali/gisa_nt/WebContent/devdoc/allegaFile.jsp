<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
      <%
       int maxFileSize=-1;
	   int mb1size = 1048576;
	    maxFileSize=Integer.parseInt(org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("MAX_SIZE_ALLEGATI"));
	   	String maxSizeString = String.format("%.2f", (double) maxFileSize/ (double) mb1size);
       %>
       
       <script>
       function rimuoviFile(id){
	    	 document.getElementById("file"+id).value="";
	    	 document.getElementById("tipoEstensione").value="";
			 document.getElementById("tipoModulo").style.background="transparent";
			 document.getElementById("idFlusso").style.background="transparent";
       }
       
       
function GetFileSize(fileid) {
	var input = document.getElementById('file1');
        file = input.files[0];
        if (file.size> <%=Integer.parseInt(org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("MAX_SIZE_ALLEGATI"))%>)
      	 	return false;
        return true;
		}
		
function isValid(str){
	 return !/[~`!#$%\^&*+=\\[\]\\';,/{}|\\":<>\?]/g.test(str);
	}

function checkFile(file){
	var fileCaricato = file;
	var errorString = '';
	
	var sintassiOk = true;
	var flussoOk = true;
	var flussoAggiuntoOk = true;
	var moduloOk = true;
	var msg = '';
	
	if (!GetFileSize(fileCaricato)){
		errorString+='\nErrore! Selezionare un file con dimensione superiore a 0 ed inferiore a <%=maxSizeString%> MB';
		alert(errorString);
		fileCaricato.value = "";
		return false;
	}
			
	var moduloPrincipale = document.getElementById("idTipoLabel").innerHTML;	
	var nome =fileCaricato.value;
	nome = nome.split(/(\\|\/)/g).pop();
	
	if (!isValid(nome)){
		errorString+='\nErrore! Il nome del file contiene caratteri non consentiti.';
		alert(errorString);
		fileCaricato.value = "";
		return false;
	}
	
// 	var modulo = nome.substring(nome.indexOf("Modulo")+6,nome.indexOf("-"));
// 	var flusso = nome.substring(nome.indexOf("-")+1,nome.indexOf("."));
// 	var estensione = nome.substring(nome.indexOf(".")+1,nome.length);
	
	var estensione = nome.substring(nome.lastIndexOf(".")+1,nome.length).toUpperCase();
	document.getElementById("tipoEstensione").value=estensione;
	
	if (document.getElementById("aggiuntaDaFlusso").value==''){
	var nuova = nome.replace(/[^0-9a-z]/gi, '').toUpperCase();
	var modulo = "";
	var flusso = "";
	
	nuova = nuova.substring(nuova.indexOf("MODULO") ); // A questo punto il nome  inizia con MODULO
	flusso = nuova.replace(/\D/g, '').substring(0,3); //Nel flusso metto i primi tre numeri presenti nel nome
	modulo = nuova.substring(nuova.indexOf("MODULO")+"MODULO".length, nuova.indexOf(flusso)); //Nel modulo metto i caratteri tra MODULO e il flusso
	
	document.getElementById("tipoModulo").value=modulo;
	
	if (document.getElementById("aggiuntaDaFlusso").value=='')
		document.getElementById("idFlusso").value=flusso;
	
	if (modulo.length!=1 && modulo.length!=2)
		moduloOk = false;
	
	if ( isNaN(flusso))
		flussoOk = false;
	
	if (flussoOk){
		if (parseInt(flusso)!=document.getElementById("idFlusso").value)
			flussoAggiuntoOk=false;
	}
	
	if (!flussoOk || !flussoAggiuntoOk || !moduloOk){
		sintassiOk = false;
		file.value = null;
	}
	
	if (flussoOk)
		recuperaFlusso();
	
	if (flussoOk)
		document.getElementById("idFlusso").style.background="lime";
	else{
		document.getElementById("idFlusso").style.background="red";
		msg+='Errore richiesta\n';
	}

	if (flussoOk) {
		if (flussoAggiuntoOk)
		document.getElementById("idFlusso").style.background="lime";
	else{
		document.getElementById("idFlusso").style.background="red";
		msg+='La richiesta indicata nel file non corrisponde a quella selezionata.\n';
	}
	}
	
	if (moduloOk)
		document.getElementById("tipoModulo").style.background="lime";
	else{
		document.getElementById("tipoModulo").style.background="red";
		msg+='Tipo di modulo non valido. (Es.sintassi: ModuloX-NNN.pdf)\n';
	}

	if (moduloOk &&  !moduloPrincipale.startsWith(modulo)){
		msg+='Errore! Impossibile caricare un modulo '+modulo+' in fase di aggiunta modulo '+moduloPrincipale;
		document.getElementById("tipoModulo").style.background="red";
	}
	
	
	if (msg!= ''){
		alert(msg);
		return false;
	}
	}
}
</script>

 
 <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" id = "tabVerbale">
    <tr>
      <th colspan="2">
        <img border="0" src="images/file.gif" align="absmiddle"><b>ALLEGA DOCUMENTO</b>
      </th>
    </tr>
  
  <tr class="containerBody">
      <td class="formLabel">Modulo Caricato</td>
      <td><input type="text" style="background-color: transparent; border:none" readonly id="tipoModulo" name="tipoModulo"/></td>
    </tr>
    
     <tr class="containerBody">
      <td class="formLabel">Richiesta</td>
      <td><input type="text" style="background-color: transparent; border:none" readonly id="idFlusso" name="idFlusso"/></td>
    </tr>
    
  
   <tr class="containerBody">
      <td class="formLabel">Estensione</td>
      <td><input type="text" style="background-color: transparent; border:none" readonly id="tipoEstensione" name="tipoEstensione"/></td>
    </tr>
    
      <tr class="containerBody">
      <td class="formLabel">
       File
       (Max. <%=maxSizeString %> MB)
       
      </td>
      <td>
        <input type="file" id="file1" name="file1" size="45"  required="required"  onChange="checkFile(this)">  <a href="#" onclick="rimuoviFile(1); return false;"><img src="images/delete.gif"></a>
     <br/>
      </td>
    </tr>
    
    
  </table>

  

