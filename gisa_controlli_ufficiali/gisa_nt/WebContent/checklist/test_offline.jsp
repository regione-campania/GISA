<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>


<div id ="elenco"></div>

<br><br>
<form>
	<fieldset >
		<legend>Aggiungi Nuovo Record</legend>
<table>
<tr><td>nome</td><td><input type="text" id ="nome"></td></tr>
<tr><td>cognome</td><td><input type="text" id ="cognome"></td></tr>
<tr><td colspan = '2'><input type="button" value = "Salva" id ="save"> </td></tr>

</table>
</fieldset>
<br><br>

<fieldset >
		<legend>Ricerca /Cancella Record</legend>
<table>
<tr><td>nome</td><td><input type="text" id ="nomesearch"></td></tr>
<tr><td colspan='2'><input type="button" value = "Carica Record" id ="load"> </td></tr>
<tr><td colspan='2'><input type="button" value = "cancella Record" id ="delete"> </td></tr>
<tr><td colspan='2'><input type="button" value = "Visualizza Tutti" id ="pendenti"> </td></tr>
</table>
</fieldset>

</form>
<%
if (request.getParameter("compatible").equals("si"))
{
%>
<script>
	

	// recupero le informazioni del browser
var AgntUsr=navigator.userAgent.toLowerCase();

// controllo se il browser utilizza i dom
var DomYes=document.getElementById?1:0;

// controllo se il browser è firefox
var NavYes=AgntUsr.indexOf('mozilla')!=-1&&AgntUsr.indexOf('compatible')==-1?1:0;

// controllo se il browser è explorer
var ExpYes=AgntUsr.indexOf('msie')!=-1?1:0;


if (ExpYes + '' != '1') {
	alert('Non sto in Explorer');

}




	var indexedDB = window.indexedDB || window.webkitIndexedDB || window.mozIndexedDB || window.msIndexedDB;
	var IDBTransaction = window.IDBTransaction || window.webkitIDBTransaction;
	var db;
			(
			
			function () 
			{     
           
				//---------------------------------- INIT DB
				function initDb() 
				{
				
					var request = indexedDB.open("GISA_OFFLINE_2",1);  
				
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
						alert('test errore apertura db');
					};
	 
					// apertura db: on upgradeneeded
					request.onupgradeneeded = function (evt) 
					{ 
					               
						var objectStore = evt.currentTarget.result.createObjectStore("databean", { keyPath: "nome", autoIncrement: false });
						objectStore.createIndex("databean", "databean", { unique: false });
					};
				}
				
  
 
				//---------------------------------- CONTENT LOADING
				function contentLoaded()
				{
		 
		 			alert('initdb');
					initDb();              
	alert('dopo initdb');
					document.getElementById('save').addEventListener("click",function()
							{
											console.log('SALVATAGGIO DATI IN LOCALE');
											
											// recupero le informazioni iniziali
											var nome = document.getElementById('nome').value ;
											var cognome = document.getElementById('cognome').value ;
							
												
												// per ogni risposta costruisco un databean
												databean = {
												  "nome": nome,
												  "cognome": cognome
												};

												
												try {
													var transaction = db.transaction("databean", IDBTransaction.READ_WRITE);
													var objectStore = transaction.objectStore("databean");
													var request = objectStore.put(databean);
													alert('Record Salvato Correttamente')
													document.getElementById('pendenti').click();
												}catch(e)
												{
													alert('errore');
												}
											
									
									
								
							});	// fine evento save-on-click
							
							
					
					//---------------------------------- LOADING DATA ENTRIES
					
					document.getElementById('load').addEventListener("click",function()
					{
						var count = 0 ;
						nome_corrente = document.getElementById('nomesearch').value ;
						var cursCount = 0;
						var transaction = db.transaction("databean", IDBTransaction.READ_WRITE);
						var objectStore = transaction.objectStore("databean");
	 					var request = objectStore.openCursor();
						console.log('CARICAMENTO DATI');
						request.onsuccess = function(evt) 
						{  
							var cursor = evt.target.result;  
							
							if (cursor) 
							{  
								
								
								var databean = cursor.value;
								//console.log('recupero id checklist da databean');

								
								var nome = databean.nome;
								var cognome = databean.cognome;
								
					
								
								if((nome==nome_corrente) )
								{
									count++ ;
									cursCount++;
									console.log('caricata');
								
								document.getElementById('nome').value = databean.nome ;
								document.getElementById('cognome').value =databean.cognome;
								console.log('DATI TROVATI E CARICATI');
								
							
								
								
							}
								//cursor.continue(); 
							}
							else   
							{
								
								if(cursCount>0)
									alert('La persona e\' stata caricata con successo');
								else{
									alert('per il nome specificato non esistono dati salvati in modalita\' OFFLINE');
										console.log('DATI NON TROVATI');
								}
									
								//console.log('Cursor: no more entries'); 
							}
							  
						};	// fine open-cursor-on-success
					
					}); // fine evento load-on-click
					
					
		
		 
				}	// fine contentLoaded
 
            window.addEventListener("DOMContentLoaded", contentLoaded, false); 
			})();  // fine funzione principale DB 


			
</script>
<%}
else
{
	%>
	<script>
	alert('stop '+document.getElementById('save'));
	
	function salva(){

		alert('salva on line')
		}
	
	</script>
	<%
}
%>



