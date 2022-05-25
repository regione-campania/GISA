/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/


	//----------------------------------------- CREAZIONE DEL DATABASE
	
	var dbName = "gisa_offline";
    var DB = {};
    var indexedDB = window.indexedDB || window.webkitIndexedDB || window.mozIndexedDB;

    if ('webkitIndexedDB' in window) 
	{
        window.IDBTransaction = window.webkitIDBTransaction;
        window.IDBKeyRange = window.webkitIDBKeyRange;
    }

    DB.indexedDB = {};
    DB.indexedDB.db = null;

    DB.indexedDB.onerror = function(e) 
	{
		$('#modalWindowError').removeClass('unlocked').addClass('locked');
		console.log(e);
    };

	//----------------------------------------- APERTURA DEL DATABASE
	
    DB.indexedDB.open = function() 
	{
        var request = indexedDB.open(dbName);

        request.onsuccess = function(e)
		{
			var v = "2-beta"; 	
			DB.indexedDB.db = e.target.result;
			var db = DB.indexedDB.db;
			
			if (v!= db.version) 
			{
				var setVrequest = db.setVersion(v);

			
				setVrequest.onfailure = DB.indexedDB.onerror;
				setVrequest.onsuccess = function(e) 
				{
					if(db.objectStoreNames.contains("todo")) 
					{
						db.deleteObjectStore("todo");
					}

				  var store = db.createObjectStore("todo",{keyPath: "timeStamp"});
				  DB.indexedDB.getAllTodoItems();
				};
			} else {DB.indexedDB.getAllTodoItems();}
		 
        };
        request.onfailure = DB.indexedDB.onerror;
    }


	//----------------------------------------- DELETE DAL DATABASE
	
    DB.indexedDB.deleteTodo = function(id) 
	{
        var db = DB.indexedDB.db;
        var trans = db.transaction(["todo"], IDBTransaction.READ_WRITE);
        var store = trans.objectStore("todo");

        var request = store.delete(id);

        request.onsuccess = function(e) 
		{
          DB.indexedDB.getAllTodoItems();
        };

        request.onerror = function(e) 
		{
          console.log("Error Deleting: ", e);
        };
    };

	
	//----------------------------------------- CURSORE PER IL RECUPERO DEGLI ITEMS
	
    DB.indexedDB.getAllTodoItems = function() 
	{
        var todos = document.getElementById("todoItems");
        todos.innerHTML = "";

        var db = DB.indexedDB.db;
        var trans = db.transaction(["todo"], IDBTransaction.READ_WRITE);
        var store = trans.objectStore("todo");

        // Get everything in the store;
        var keyRange = IDBKeyRange.lowerBound(0);
        var cursorRequest = store.openCursor(keyRange);

        cursorRequest.onsuccess = function(e) 
		{
			var result = e.target.result;
			if(!!result == false)
				return;

			//addDeleteListener(result.value);
			
			result.continue();
        };

        cursorRequest.onerror = DB.indexedDB.onerror;
    };


	//----------------------------------------- CHECK IF SERVER IS UP
	
	function ifUp(url,onUp,onDown) {
				var RANDOM_DIGITS = 7; 
				var pow = Math.pow(10,RANDOM_DIGITS);
				var randStr = String(Math.floor(Math.random()*pow)+pow).substr(1 );
				var img = new Image();
				img.onload = onUp;
				img.onerror = onDown;
				img.src = url+"?"+randStr;
	}
	
	//----------------------------------------- INIT DB
	
    $(function() {
        console.log("-- lets start the party");
        DB.indexedDB.open();
		
		$('#addItems').click(function()
		{
			
			
			ifUp("http://www.gisacampania.it/centric_osa/images/centric/logo-centric.gif",
			function()
			{
				console.log('Il server è UP, la checklist viene inviata a GISA');
			},
			function()
			{
				
				
				console.log('Il server è DOWN, salvo la checklist in LOCALE');
				// recupero le informazioni iniziali
				var livelloRischio = $('#livelloRischio').val();
				var categoriaRischio = $('#categoriaRischio').val();
				var punteggioUltimiAnni = $('#punteggioUltimiAnni').val();
				
				/*
				var punteggioTotale = $('#livelloRischio').val();
				var categoriaRischio = $('#categoriaRischio').val();
				var punteggioStorico = $('#punteggioUltimiAnni').val();
				*/
				
				// costruisco l'array dei punteggi
				var arrayPunti = $('input[id^=punti]');
				var lenPunti = arrayPunti.size();
				
				// costruisco l'array delle risposte
				var arrayRisp = $('input[id^=risposta]');
				var iRisp = 0;
				var lenRisp = arrayRisp.size(); 
				
				// array delle risposte (escludendo i capitoli)
				var arrayRisp2 = new Array();
				var iRisp2 = 0;
				
				for(iRisp=0;iRisp<lenRisp;iRisp++)
				{
					var tmp = $(arrayRisp[iRisp]).attr('class');
					
					
					if(tmp!='domandaCapitolo')
					{
						arrayRisp2[iRisp2] = arrayRisp[iRisp];
						iRisp2++;
						//console.log(arrayRisp[iRisp].id+" aggiungo all'array");
					}
					
					/*
					else
						console.log(arrayRisp[iRisp].id+" è una domandaCapitolo");
					*/
				}
				
				var lenRisp2 = arrayRisp2.length;
				var idSi = 0;
				var idNo = 0;
				var idPunti = 0;
				var databean = null;
				
				var iSi = 0;
				var iNo = 0;
				var iPunti = 0;
				var iIndex = 1;
				
				
				//$('#modalWindow').removeClass('unlocked').addClass('locked');
				
				
				// scorro le risposte
				for (i=0;i<lenRisp2;i++)
				{
					
					
					iSi = i;
					iNo = i+1;
					iPunti = iNo - iIndex;
			
					idSi = arrayRisp2[iSi].id;
					idNo = arrayRisp2[iNo].id;
					idPunti = arrayPunti[iPunti].id;
					punteggio = $('#'+idPunti).val();
					//console.log("Punteggio "+punteggio);
					
					i++;
					iIndex++;
					
					checkedSi = $('#'+idSi+':checked').val();
					checkedNo = $('#'+idNo+':checked').val();
					
					if(checkedSi==undefined)
						checkedSi = 0
					else
						checkedSi = 1
						
					if(checkedNo==undefined)
						checkedNo = 0
					else
						checkedNo = 1
					
					
					//console.log("is checked: "+idSi+" : "+checkedSi);
					//console.log("is checked: "+idNo+" : "+checkedNo);
					

					// per ogni risposta costruisco un databean
					databean = {
					  "livelloRischio": livelloRischio,
					  "categoriaRischio":categoriaRischio,
					  "punteggioUltimiAnni":punteggioUltimiAnni,
					  "idSi":idSi,
					  "checkedSi":checkedSi,
					  "idNo":idNo,
					  "checkedNo":checkedNo,
					  "idPunti":idPunti,
					  "punteggio":punteggio,
					  "timeStamp": idPunti
					};
					
					/*
					console.log("----------------------------");
					console.log(databean.punteggioTotale);
					console.log(databean.categoriaRischio);
					console.log(databean.punteggioStorico);
					console.log(databean.idSi);
					console.log(databean.checkedSi);
					console.log(databean.idNo);
					console.log(databean.checkedNo);
					console.log(databean.idPunti);
					console.log(databean.timeStamp);
					console.log("----------------------------");
					*/
					
					var db = DB.indexedDB.db;
					var trans = db.transaction(['todo'], IDBTransaction.READ_WRITE);
					var store = trans.objectStore("todo");
					var request = store.put(databean);

					/*
					request.onsuccess = function(e) 
					{
					  console.log(databean.timeStamp+" store OK.");
					};
					*/

					request.onerror = function(e) {
					  $('#modalWindowError').removeClass('unlocked').addClass('locked');
					};
					
				
					
				}	
				
				//$('#modalWindowError').removeClass('unlocked').addClass('locked');
				$('#modalWindow').removeClass('locked').addClass('unlocked');
			
				//}
			});

			
				
		
		});
		
		
		$('#debugItems').click(function()
		{
			var db = DB.indexedDB.db;
			var trans = db.transaction(["todo"], IDBTransaction.READ_WRITE);
			var store = trans.objectStore("todo");

			var keyRange = IDBKeyRange.lowerBound(0);
			var cursorRequest = store.openCursor(keyRange);

			cursorRequest.onsuccess = function(e) 
			{
				var result = e.target.result;
				if(!!result == false)
					return;
				var databean = result.value;
			
				$('#livelloRischio').val(databean.livelloRischio);
				$('#categoriaRischio').val(databean.categoriaRischio);
				$('#punteggioUltimiAnni').val(databean.punteggioUltimiAnni);
				
				
				var currentId = '#'+databean.idPunti;
				$(currentId).val(databean.punteggio);
				if(databean.checkedSi==1)
				{
					//console.log("setto "+databean.idSi+" a true");
					$('input[id='+databean.idSi+']').attr('checked', true);
					document.getElementById(databean.idSi).disabled=false;
					document.getElementById(databean.idNo).disabled=false;
				}
				if(databean.checkedNo==1)
				{
					//console.log("setto "+databean.idNo+" a true");
					$('input[id='+databean.idNo+']').attr('checked', true);
					document.getElementById(databean.idSi).disabled=false;
					document.getElementById(databean.idNo).disabled=false;
				}
				//console.log("PUT INTO "+currentId+" VALUE "+databean.punteggio);
				
				/*
				console.log("----------------------------");
				console.log(databean.punteggioTotale);
				console.log(databean.categoriaRischio);
				console.log(databean.punteggioStorico);
				console.log(databean.idSi);
				console.log(databean.idNo);
				console.log(databean.idPunti);
				console.log(databean.timeStamp);
				console.log("----------------------------");
				*/
				
				//addDeleteListener(result.value);
				
				result.continue();
			};

			cursorRequest.onerror = DB.indexedDB.onerror;
			
		});
		

      });
	  
