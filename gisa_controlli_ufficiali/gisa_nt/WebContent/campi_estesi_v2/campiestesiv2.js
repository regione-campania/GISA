/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
 

	

	 


	function saveCampi(evt)
	{
		 
		var wrappingform = evt.data.wrappingForm; 
		var idIstanzaVal = evt.data.idIstanzaVal;
		var idRelStabLp = evt.data.idRelazione;
		var idLinea = evt.data.idLinea;
		
		/*assieme alform che contiene la table con gli input, faccio viaggiareun hidden input con id istanza valore
		 * a cui appartengono e l'id rel stab lp 
		 */
		var hiddenInp = $("<input></input>").attr("type","hidden").attr("name","idIstanzaVal").val(idIstanzaVal );
		wrappingform.append(hiddenInp);
		 
		var hiddenInp2 = $("<input></input>").attr("type","hidden").attr("name","idRelStabLp").val( idRelStabLp);
		wrappingform.append(hiddenInp2);
		
		/*prima di inviare controllo i required */
		var allValid = true;
		wrappingform.find("[required]").each(function(indice){
			if($(this).prop("required") == true && ( $(this).val() == undefined || $(this).val().trim().length == 0 ) )
			{
				if(allValid)
				{
					allValid = false;
					alert("Uno o piu' campi obbligatori non sono stati valorizzati");
					 
				}
				else
				{
					/*niente poiche' gia' notificato */
				}
			}
		});
		
		if(!allValid)
			return;
		
		$.ajax({
			
			url : "StabilimentoSintesisAction.do?command=SalvaValoriCampiEstesiv2"
			,type : "post"
			,data : wrappingform.serialize()
			,success : function(risp)
			{
				if(risp.status == '0')
					alert("Salvataggio avvenuto con successo");
				else
					alert("Qualcosa e' andato storto");
				
				showCampiEstesi(idLinea,idRelStabLp);
			}
			 
			
		});
		
		 
			
		
		
	}


	function showCampiEstesi(idLinea, idRelazione, selettoriDOMs, selettoreDOMSpecifico)
	{
		$(selettoriDOMs).css("display","none");
		
		
		$.ajax({
				type : 'POST',
				url : 'StabilimentoSintesisAction.do?command=CercaCampiEstesiv2',
				data : {idLinea : idLinea, idRelazione : idRelazione},
				dataType : "json",
				success : function(risp)
				{
					 
					$(selettoreDOMSpecifico).html("");
					$(selettoreDOMSpecifico).html($("<td>LISTA OPERATORI DEL MERCATO</td>"));
					var td = $("<td></td>" );
					var table = $("<table></table>");
					td.append(table);
					$(selettoreDOMSpecifico).append(td);  
					 
					var idIstanzeVal = Object.keys(risp);
					var atLeastOne = false;
					for(var ind = 0; ind < idIstanzeVal.length; ind++)
					{
						var idIstanzaVal = idIstanzeVal[ind];
						var arrayCampiEstesi = risp[idIstanzaVal];
						
						var wrappingform = $("<form></form>",{name : 'wrappingformcampi'+idIstanzaVal}); /*questo form lo invio serializzato e serve anche per la separazione scope dei radio button */
						
						var table2 = $("<table></table>"); 
						wrappingform.append(table2);  
						
						table2.append($("<tr></tr>").append($("<th></th>" )/*.text(idIstanzaVal)*/));
						
						/*l'ordine dei campi e' indicato nella classe, quindi li ordino e poi li injecto */
						var ordered = {}; /*la chiave e' l'ordine e l'array associato sono tutti i campi che stanno a quel posto come ordinamento poiche' puo ' capitare che piu' di uno lo abbia uguale */
						var lastOrder = -1;
						for(var ind2 = 0; ind2 < arrayCampiEstesi.length; ind2++)
						{
							var ordineEstratto = 0;
							/*estraggo ordine-n dalla classe */
							try
							{
								var classe = $(arrayCampiEstesi[ind2].html).find("[class]").attr("class").match(/ordine-[0-9]*/)[0]; /*estrai per l'hml generato del campo i dom che hanno la classe settata (solo uno) e da questa tira fuori ordine-n */
								ordineEstratto = +(classe.split("-")[1]); /*levo la parte dopo - e la parso come int */
							}
							catch(e)
							{
								
								console.log("Errore con estrazione ordine dalla classe. Setto 0 di default");
							}
							
							if(!(ordineEstratto in  ordered))
							{
								ordered[ordineEstratto] = [];
							}
							/*lo aggiungo il campo */
							ordered[ordineEstratto].push(arrayCampiEstesi[ind2]);
							if(ordineEstratto > lastOrder)
							{
								lastOrder = ordineEstratto;
							}
						}
						
						for(var ind2 = 0; ind2 <=lastOrder; ind2++)
						{
							if(ind2 in ordered)
							{
								var campiStessoOrdine = ordered[ind2];
								for(var k = 0; k< campiStessoOrdine.length; k++)
								{
									atLeastOne = true;
									var tr = $("<tr></tr>");
									
									tr.html($("<td></td>").html(campiStessoOrdine[k].html));
									
									
									table2.append(tr);
								}
							}
						}
						
						/*for(var ind2 = 0; ind2 < arrayCampiEstesi.length; ind2++)
						{
							atLeastOne = true;
							var tr = $("<tr></tr>");
							
							tr.html($("<td></td>", ).html(arrayCampiEstesi[ind2].html));
						
							
							table2.append(tr);
							
						}
						*/
						
						
						/*todo riattivare salva e levare la chiamata jquery che mette readonly tutti gli input -------(RIATTIVA QUI)-----------------------------*/
						/*
						var trButt = $("<tr></tr>");
						table2.append(trButt);
						
						
						var but = $("<input></input>").val("SALVA").attr("type","button");
						
						but.on("click",{idRelazione : idRelazione, idLinea : idLinea, idIstanzaVal : idIstanzaVal, wrappingForm : wrappingform },saveCampi); //gli passo la tabella contenente i campi poiche' la mando direttamente in un form serializzato in ajax 
						trButt.append($("<td></td>",{align : 'center'}).append(but));
						---------------------------------------------------------------------------------------------------------------------------*/
						
						
						table.append( $("<td></td>").html(wrappingform) ); 
						
						/*(LEVA QUI)------------------------------------*/
						
						table2.find("input[type='text']").prop("readonly",true);
						
						/*-----------------------------------*/
					}
					
					if(!atLeastOne)
					{
						alert("Per la linea selezionata non ci sono campi aggiuntivi.");
						return;
					}
					else
						$(selettoreDOMSpecifico).css("display","");
					
				},
				error : function()
				{
					console.log("error");
				}
				
			});
	}

	 
 