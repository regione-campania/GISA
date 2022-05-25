/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

function controllaForm(form){
	formTest = true;
	message = "";
	
	if(form.problem.value=="" ){
		message += label("check.sanzioni.richiedente.selezionato","- Controllare che il campo \"Note\" sia stato popolato\r\n");
		formTest = false;

	}
	var provvedimenti = form.limitazioniFollowup.options;
	for (i=0 ; i<provvedimenti.length; i++)
	{
		if (provvedimenti[i].selected && provvedimenti[i].value =='-1')
		{
			message += label("check.sanzioni.richiedente.selezionato","- Controllare che il campo \"Provvedimenti\" sia stato popolato correttamente\r\n");
			formTest = false;
		}
		
	}
	


	if (formTest == false) {
		alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
		return false;
	} else {
		loadModalWindow();
		//form.submit();
		return true;
	}
}

function popLookupSelectMultiple(displayFieldId,hiddenFieldId,table) {
  title  = '_types';
  width  =  '500';
  height =  '450';
  resize =  'yes';
  bars   =  'no';
  
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  
  var selectedIds = '';
  var selectedDisplays ='';
  
  for (count=0; count<(document.getElementById(displayFieldId).length); count++) {
          
          if (document.getElementById(displayFieldId).options[count].value > -1) {
                  if (selectedIds.length > 0) {
                          selectedIds = selectedIds + '|';
                          selectedDisplays = selectedDisplays + '|';
                  }
                          
                  selectedIds = selectedIds + document.getElementById(displayFieldId).options[count].value;
                  selectedDisplays = selectedDisplays + escape(document.getElementById(displayFieldId).options[count].text);
          }
          
  }
  
  var params = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  var newwin=window.open('LookupSelector.do?command=PopupSelector&hiddenFieldId='+hiddenFieldId+'&displayFieldId='+displayFieldId+'&previousSelection=' + selectedIds + '&previousSelectionDisplay=' + selectedDisplays + '&table=' + table + '&listType=list', title, params);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}


function popContactTypeSelectMultiple(displayFieldId, category, contactId) {
  title  = '_types';
  width  =  '500';
  height =  '450';
  resize =  'yes';
  bars   =  'no';
  
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  
  var selectedIds = '';
  var selectedDisplays ='';
  
  for (count=0; count<(document.getElementById(displayFieldId).length); count++) {
          
          if (document.getElementById(displayFieldId).options[count].value > -1) {
                  if (selectedIds.length > 0) {
                          selectedIds = selectedIds + '|';
                          selectedDisplays = selectedDisplays + '|';
                  }
                          
                  selectedIds = selectedIds + document.getElementById(displayFieldId).options[count].value;
                  selectedDisplays = selectedDisplays + document.getElementById(displayFieldId).options[count].text;
          }
          
  }
  
  var params = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  var newwin=window.open('ExternalContacts.do?command=PopupSelector&reset=true&displayFieldId='+displayFieldId+'&previousSelection=' + selectedIds + '&previousSelectionDisplay=' + selectedDisplays + '&category=' +  category + '&contactId=' + contactId , title, params);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}



function popUpElencoCapi(codiceAzienda,idAllevamento) {
  title  = 'product_catalog_list';
  width  =  '500';
  height =  '800';
  resize =  'no';
  bars   =  'yes';
  
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  
  var selectedIds = '';
  var selectedDisplays ='';
  

  var params = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  var newwin=window.open('LookupSelector.do?command=ElencoCapi&codice_azienda='+codiceAzienda+'&id_allevamento='+idAllevamento , title, params);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}



function popProductCatalogSelectMultiple(displayFieldId, contractId) {
  title  = 'product_catalog_list';
  width  =  '500';
  height =  '450';
  resize =  'yes';
  bars   =  'yes';
  
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  
  var selectedIds = '';
  var selectedDisplays ='';
  
  for (count=0; count<(document.getElementById(displayFieldId).length); count++) {
          
          if (document.getElementById(displayFieldId).options[count].value > -1) {
                  if (selectedIds.length > 0) {
                          selectedIds = selectedIds + '|';
                          selectedDisplays = selectedDisplays + '|';
                  }
                          
                  selectedIds = selectedIds + document.getElementById(displayFieldId).options[count].value;
                  selectedDisplays = selectedDisplays + document.getElementById(displayFieldId).options[count].text;
          }
  }
  var params = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  var newwin=window.open('ProductsCatalog.do?command=PopupSelector&reset=true&displayFieldId='+displayFieldId+'&previousSelection=' + selectedIds + '&previousSelectionDisplay=' + selectedDisplays + '&contractId=' + contractId +'&listType=list' , title, params);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}


function popQuoteConditionSelectMultiple(displayFieldId,highLightedId,table,quoteId,currentIds,currentValues, type) {
  title  = '_types';
  width  =  '500';
  height =  '450';
  resize =  'yes';
  bars   =  'no';
  
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  
  var selectedIds = currentIds;
  var selectedDisplays = currentValues;
  
  var params = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  var newwin=window.open('QuotesConditions.do?command=PopupSelector&quoteId='+quoteId+'&displayFieldId='+displayFieldId+'&previousSelection=' + selectedIds + '&previousSelectionDisplay=' + selectedDisplays + '&table=' + table + '&type='+ type+ '', title, params);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}


function popAssetMaterialsSelectMultiple(displayFieldId,highLightedId,table,assetId,currentIds,currentQuantities) {
  title  = 'asset_material_types';
  width  =  '500';
  height =  '450';
  resize =  'yes';
  bars   =  'no';
  
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  
  var selectedIds = currentIds;
  var selectedQtys = currentQuantities;
  
  var params = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  var newwin=window.open('AssetMaterialsSelector.do?command=PopupSelector&assetId='+assetId+'&displayFieldId='+displayFieldId+'&previousSelection=' + selectedIds + '&previousSelectionQuantity='+ selectedQtys +'&table=' + table + '', title, params);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}


function popUserGroupsSelectMultiple(displayFieldId,highLightedId,table,userId,currentIds,currentValues, type) {
  title  = '_types';
  width  =  '500';
  height =  '450';
  resize =  'yes';
  bars   =  'no';
  
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  
  var selectedIds = currentIds;
  var selectedDisplays = currentValues;
  
  var params = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  var userString = '&userId='+userId;
  if (displayFieldId == 'campaign') {
    userString = '&campaignId='+userId;
  }
  var newwin=window.open('UserGroups.do?command=PopupSelector'+userString+'&displayFieldId='+displayFieldId+'&previousSelection=' + selectedIds + '&previousSelectionDisplay=' + selectedDisplays + '&table=' + table + '&type='+ type+ '', title, params);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}


function popUserGroupsListSingle(hiddenFieldId, displayFieldId, params) {
  title  = 'Contacts';
  width  =  '700';
  height =  '425';
  resize =  'yes';
  bars   =  'yes';
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  if(params != null && params != ""){
    params = '&' + params;
  }
  var newwin=window.open('UserGroups.do?command=PopupSingleSelector&listType=single&flushtemplist=true&selectedIds='+document.getElementById(hiddenFieldId).value+'&displayFieldId='+displayFieldId+'&hiddenFieldId='+hiddenFieldId + params, title, windowParams);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}

function popPortfolioCategoryListSingle(hiddenFieldId, displayFieldId, params) {
  title  = 'PortfolioCategory';
  width  =  '700';
  height =  '425';
  resize =  'yes';
  bars   =  'yes';
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  if(params != null && params != ""){
    params = '&' + params;
  }
  var newwin=window.open('PortfolioEditor.do?command=PopupSingleSelector&listType=single&flushtemplist=true&selectedIds='+document.getElementById(hiddenFieldId).value+'&displayFieldId='+displayFieldId+'&hiddenFieldId='+hiddenFieldId + params, title, windowParams);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}

function popActionPlansSelectMultiple(displayFieldId,highLightedId,categoryId,constantId,siteId,currentIds, type) {
  var selectedIds = currentIds;
  window.location.href= 'AdminCategories.do?command=PopupSelector&categoryId='+categoryId+'&siteId='+siteId+'&displayFieldId='+displayFieldId+'&previousSelection=' + selectedIds + '&categoryId=' + categoryId +'&constantId='+ constantId + '&type='+ type+ '';
}

function popLookupSelectSingle(displayFieldId, moduleId, lookupId) {
  title  = '_types';
  width  =  '500';
  height =  '450';
  resize =  'yes';
  bars   =  'no';
  
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  var params = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  var newwin=window.open('LookupSelector.do?command=PopupSingleSelector&displayFieldId='+displayFieldId+'&lookupId=' + lookupId + '&moduleId=' + moduleId + '', title, params);
  newwin.focus();

  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}

function popLookupSelectorAllegatiInBacheca(hiddenFieldId, displayFieldId, table, params) {
	  title  = '_types';
	  width  =  '500';
	  height =  '450';
	  resize =  'yes';
	  bars   =  'yes';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('GestioneBacheca.do?command=ListaAllegatiComunicazioniInterne&displayFieldId='+displayFieldId+'&hiddenFieldId=' + hiddenFieldId + '&table=' + table + params, title, windowParams);
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}

function popLookupSelector(hiddenFieldId, displayFieldId, table, params) {
  title  = '_types';
  width  =  '500';
  height =  '450';
  resize =  'yes';
  bars   =  'no';
  
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  var newwin=window.open('LookupSelector.do?command=PopupSelector&displayFieldId='+displayFieldId+'&hiddenFieldId=' + hiddenFieldId + '&table=' + table + params, title, windowParams);
  newwin.focus();

  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}

//inserito da Carmela
function popLookupSelectorCustomNew(displayFieldId2, table, params) {
  title  = '_types';
  width  =  '500';
  height =  '450';
  resize =  'yes';
  bars   =  'no';
  
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  
  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  var newwin=window.open('LookupSelector.do?command=PopupSelectorCustomNew&displayFieldId2=' + displayFieldId2 + '&table=' + table + params, title, windowParams);
  newwin.focus();

  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}


function popLookupSelectorDestinazioneCarni( inRegione, indiceDestinatario, tipo ) {
	  title  = '_types';
	  width  =  '500';
	  height =  '450';
	  resize =  'yes';
	  bars   =  'yes';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('LookupSelector.do?command=PopupSelectorDestinazioneCarni&inRegione=' + inRegione + '&indiceDestinatario=' + indiceDestinatario + '&tipo=' + tipo, title, windowParams);
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}


function popLookupSelectorCustomImprese(displayFieldId, displayFieldId2, displayFieldId3, table, params) {
	  title  = '_types';
	  width  =  '600';
	  height =  '450';
	  resize =  'yes';
	  bars   =  'no';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('LookupSelector.do?command=PopupSelectorCustomImprese&displayFieldId=' + displayFieldId + '&displayFieldId2=' + displayFieldId2 + '&displayFieldId3=' + displayFieldId3 + '&table=' + table + params, title, windowParams);
	  newwin.focus();

	  //alert(displayFieldId3);
	  
	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}

function popLookupSelectorCustomZone(displayFieldId,siteId) {
	
	
	  title  = '_types';
	  width  =  '500';
	  height =  '450';
	  resize =  'yes';
	  bars   =  'yes';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('LookupSelector.do?command=PopupSelectorCustomZone&displayFieldId='+displayFieldId+'&siteId='+siteId, title, windowParams);
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}

function popLookupSelectorCustomPuntiSbarco(displayFieldId,siteId) {
	
	
	  title  = '_types';
	  width  =  '500';
	  height =  '450';
	  resize =  'yes';
	  bars   =  'yes';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('LookupSelector.do?command=PopupSelectorCustomPuntiSbarco&displayFieldId='+displayFieldId+'&siteId='+siteId, title, windowParams);
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}

function popLookupSelectorCustomOperatoreAbusivo(displayFieldId) {
	
	
	  title  = '_types';
	  width  =  '500';
	  height =  '450';
	  resize =  'yes';
	  bars   =  'yes';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('LookupSelector.do?command=PopupOperatoreAbusivoAdd&displayFieldId='+displayFieldId, title, windowParams);
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
}


function popLookupSelectorCustomSpeditore(displayFieldId) {
	
	
	  title  = '_types';
	  width  =  '650';
	  height =  '450';
	  resize =  'yes';
	  bars   =  'yes';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('LookupSelector.do?command=PopupSelectorMacellazioneSpeditore&displayFieldId='+displayFieldId, title, windowParams);
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
}

function popLookupSelectorCustomLaboratoriHaccp(displayFieldId) {
	
	
	  title  = '_types';
	  width  =  '500';
	  height =  '450';
	  resize =  'yes';
	  bars   =  'yes';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('LookupSelector.do?command=PopupSelectorCustomLaboratoriHaccp&displayFieldId='+displayFieldId, title, windowParams);
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}



function popLookupSelectorCustomPdP(orgId,arraypdp) {
	
	
	  title  = '_types';
	  width  =  '500';
	  height =  '450';
	  resize =  'yes';
	  bars   =  'yes';
	  var idpdp='';
	  for(i=0;i<arraypdp.length;i++)
	  {
		  idpdp += arraypdp[i].value+';';
	  }
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('AcqueRete.do?command=List&sel='+idpdp+'&orgId='+orgId, title, windowParams);
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}

function popLookupSelectorCustomProgrammazioni(displayFieldId, displayFieldId2, table, params,id_asl) {
	  title  = '_types';
	  width  =  '800';
	  height =  '800'; 
	  resize =  'no';
	  bars   =  'si';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=1,STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('LookupSelector.do?command=PopupSelectorCustomProgrammazioni&sel=no&idAsl='+id_asl+'&displayFieldId=' + displayFieldId + '&displayFieldId2=' + displayFieldId2 + '&table=' + table + params, title, windowParams);
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}

function popLookupSelectorCustomPianiMonitoraggio(displayFieldId, displayFieldId2, table, params,id_asl) {
	  title  = '_types';
	  width  =  '500';
	  height =  '700';
	  resize =  'no';
	  bars   =  'si';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=1,STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('LookupSelector.do?command=PopupSelectorCustomPianiMonitoraggio&sel=no&idAsl='+id_asl+'&displayFieldId=' + displayFieldId + '&displayFieldId2=' + displayFieldId2 + '&table=' + table + params, title, windowParams);
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}
function popLookupSelectorCustomPianiMonitoraggioCu(displayFieldId, displayFieldId2, table, params,id_asl,array_campi_piani_selezionati) {
	  title  = '_types';
	  width  =  '600';
	  height =  '900';
	  resize =  'no';
	  bars   =  'si';
	  
	  var valori_piani_selezionati = '' ;
	  for(i=0;i<array_campi_piani_selezionati.length;i++)
	  {
			  if(array_campi_piani_selezionati[i].value !='-1' && array_campi_piani_selezionati[i].value!='' )
				  valori_piani_selezionati+='&listaPianiSelezionati='+array_campi_piani_selezionati[i].value;
	  }
		  
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=1,STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('LookupSelector.do?command=PopupSelectorPianiMonitoraggioCU&idAsl='+id_asl+'&displayFieldId=' + displayFieldId + '&displayFieldId2=' + displayFieldId2 +valori_piani_selezionati+'&table=' + table + params, title, windowParams);
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}

function popLookupSelectorCustomMatrici(displayFieldId, displayFieldId2, table, params) {
	  title  = '_types';
	  width  =  '500';
	  height =  '450';
	  resize =  'yes';
	  bars   =  'no';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('LookupSelector.do?command=PopupSelectorCustomMatrici&displayFieldId=' + displayFieldId + '&displayFieldId2=' + displayFieldId2 + '&table=' + table + params, title, windowParams);
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}


function setParentValue_matrice(code,description)
{
	window.opener.document.getElementById("codiceMatrice").value = code ;
	window.opener.document.getElementById("id_matrice").innerHTML = description ;
	window.close();
}
function setParentValue_piani(code,description)
{
	
	window.opener.document.getElementById("row_piano").style.display = "" ;
	window.opener.document.getElementById("piano_value").value = code ;
	window.opener.document.getElementById("piano").innerHTML = description ;
	
	
	
	window.close();
}


function setParentValue_piani_cu(code,description,level)
{
	if (level == '1')
	{
		window.opener.document.getElementById("pianoMonitoraggio1").value = code ;
		window.opener.document.getElementById("nazionali").innerHTML = description ;
		
		window.opener.document.getElementById("pianoMonitoraggio2").value = "-1" ;
		window.opener.document.getElementById("regionali").innerHTML = "" ;
		
		
		window.opener.document.getElementById("pianoMonitoraggio3").value = "-1" ;
		window.opener.document.getElementById("territoriali").innerHTML = "" ;
		
		window.close();
	}
	else
		if (level == '2')
		{
			window.opener.document.getElementById("pianoMonitoraggio2").value = code ;
			window.opener.document.getElementById("regionali").innerHTML = description ;
			
			window.opener.document.getElementById("pianoMonitoraggio3").value = "-1" ;
			window.opener.document.getElementById("territoriali").innerHTML = "" ;
			
			window.opener.document.getElementById("pianoMonitoraggio1").value = "-1" ;
			window.opener.document.getElementById("nazionali").innerHTML = "" ;
			
			
			window.close();
		}
		else
			if (level == '3')
			{
				
				window.opener.document.getElementById("pianoMonitoraggio1").value = "-1" ;
				window.opener.document.getElementById("nazionali").innerHTML = "" ;
				window.opener.document.getElementById("pianoMonitoraggio2").value = "-1" ;
				window.opener.document.getElementById("regionali").innerHTML = "" ;
				
				window.opener.document.getElementById("pianoMonitoraggio3").value = code ;
				window.opener.document.getElementById("territoriali").innerHTML = description ;
				window.close();
			}
}


function setParentValue_prove(code,description)
{
	window.opener.document.getElementById("codiceDenominazione").value = code ;
	window.opener.document.getElementById("id_prova").innerHTML = description ;
	window.close();
}

function popLookupSelectorCustomProve(displayFieldId, displayFieldId2, table, params) {
	  title  = '_types';
	  width  =  '500';
	  height =  '450';
	  resize =  'yes';
	  bars   =  'no';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('LookupSelector.do?command=PopupSelectorCustomProve&displayFieldId=' + displayFieldId + '&displayFieldId2=' + displayFieldId2 + '&table=' + table + params, title, windowParams);
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}
	
	
	
function popLookupSelectorCustomImpreseCU(displayFieldId, displayFieldId2, table, params,orgId) {
	  title  = '_types';
	  width  =  '500';
	  height =  '450';
	  resize =  'yes';
	  bars   =  'no';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('LookupSelector.do?command=PopupSelectorCustomImprese&orgId='+orgId+'&displayFieldId=' + displayFieldId + '&displayFieldId2=' + displayFieldId2 + '&table=' + table + params, title, windowParams);
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}
	
	
	function popLookupSelectorCustomStabilimentiCU(displayFieldId, displayFieldId2, table, params,orgId,tipo_selezione) {
	  title  = '_types';
	  width  =  '500';
	  height =  '450';
	  resize =  'yes';
	  bars   =  'no';
	 
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('LookupSelector.do?command=PopupSelectorCustomStabilimenti&tipo_selezione='+tipo_selezione+'&orgId='+orgId+'&displayFieldId=' + displayFieldId + '&displayFieldId2=' + displayFieldId2 + '&table=' + table + params, title, windowParams);
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}
	
	function popLookupSelectorCustomSOACU(displayFieldId, displayFieldId2, table, params,orgId,tipo_sel) {
		  title  = '_types';
		  width  =  '500';
		  height =  '450';
		  resize =  'yes';
		  bars   =  'no';
		  
		  var posx = (screen.width - width)/2;
		  var posy = (screen.height - height)/2;
		  
		  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
		  var newwin=window.open('LookupSelector.do?command=PopupSelectorCustomSOA&tipo_selezione='+tipo_sel+'&orgId='+orgId+'&displayFieldId=' + displayFieldId + '&displayFieldId2=' + displayFieldId2 + '&table=' + table + params, title, windowParams);
		  newwin.focus();

		  if (newwin != null) {
		    if (newwin.opener == null)
		      newwin.opener = self;
		  }
		}

function popLookupSelectorUtentiGisa(idasl) {
	  title  = '_types';
	  width  =  '500';
	  height =  '450';
	  resize =  'yes';
	  bars   =  'no';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('LookupSelector.do?command=PopupSelectorUtentiGisa&idasl='+idasl);
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}

function popLookupSelectorCheckImprese(displayFieldId, displayFieldId2, table, params) {
	  title  = '_types';
	  width  =  '500';
	  height =  '100';
	  resize =  'no';
	  bars   =  'no';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('LookupSelector.do?command=PopupSelectorCheckImprese&partitaIva=' + displayFieldId + '&displayFieldId2=' + displayFieldId2 + '&table=' + table + params, title, windowParams);
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}


function popLookupSelectorCheckImprese2(displayFieldId, displayFieldId2, table, params) {
	  title  = '_types';
	  width  =  '500';
	  height =  '100';
	  resize =  'no';
	  bars   =  'no';
	  if (document.getElementById('partitaIva').value != '' ) {
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('LookupSelector.do?command=PopupSelectorCheckImprese2&partitaIva=' + displayFieldId + '&displayFieldId2=' + displayFieldId2 + '&table=' + table + params, title, windowParams);
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	  }else
	  {
		  alert(" - Inserire Un Valore per la partita Iva da Controllare")
		  
	  }
	}


function popLookupSelectorCheckImpreseOk() {
	opener.document.getElementById("partitaIva").style.background = "green";
	window.close();
	
	}
function popLookupSelectorCheckImpreseRed() {
	
	opener.document.getElementById("partitaIva").style.background= "red";
	
	}
function popLookupSelectorCheckImpreseNo() {
	opener.document.getElementById("partitaIva").style.background = "";
	opener.document.getElementById("partitaIva").value = "";
	window.close();
	
	}


function popLookupSelectorAllerteImprese(displayFieldId, displayFieldId2, table, params,siteid) {
	  title  = '_types';
	  width  =  '500';
	  height =  '450';
	  resize =  'yes';
	  bars   =  'no';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('LookupSelector.do?command=PopupSelectorAllerteImprese&displayFieldId=' + displayFieldId + '&displayFieldId2=' + displayFieldId2 + '&table=' + table +'&siteid='+siteid+ params, title, windowParams);
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}


function popLookupSelectorSOA(displayFieldId, displayFieldId2, table, params,siteid) {
	  title  = '_types';
	  width  =  '500';
	  height =  '450';
	  resize =  'yes';
	  bars   =  'no';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('LookupSelector.do?command=PopupSelectorSOA&displayFieldId=' + displayFieldId + '&displayFieldId2=' + displayFieldId2 + '&table=' + table +'&siteid='+siteid+ params, title, windowParams);
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}


function popLookupSelectorAllerteStabilimenti(displayFieldId, displayFieldId2, table, params,siteid) {
	  title  = '_types';
	  width  =  '500';
	  height =  '450';
	  resize =  'yes';
	  bars   =  'no';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('LookupSelector.do?command=PopupSelectorAllerteStabilimenti&displayFieldId=' + displayFieldId + '&displayFieldId2=' + displayFieldId2 + '&table=' + table +'&siteid='+siteid+ params, title, windowParams);
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}


function popUpSceltaAudit(idc,orgid) {
	  title  = '_types';
	  width  =  '400';
	  height =  '100';
	  resize =  'false';
	  bars   =  'no';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	 
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('LookupSelector.do?command=popUpSceltaAudit&idc='+idc + '&orgid='+orgid, title, windowParams);
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}

function popUpSceltaAuditStab(idc,orgid) {
	  title  = '_types';
	  width  =  '400';
	  height =  '100';
	  resize =  'false';
	  bars   =  'no';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	 
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('LookupSelector.do?command=popUpSceltaAuditStab&idc='+idc + '&orgid='+orgid, title, windowParams);
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}


function popUpSceltaAuditModifica(idc,orgid) {
	  title  = '_types';
	  width  =  '400';
	  height =  '100';
	  resize =  'false';
	  bars   =  'no';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	 
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('LookupSelector.do?command=popUpSceltaAudit&update=true&idc='+idc + '&orgid='+orgid, title, windowParams);
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}



function popUpSceltaAuditModificaAllevamenti(idc,orgid) {
	  title  = '_types';
	  width  =  '400';
	  height =  '100';
	  resize =  'false';
	  bars   =  'no';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	 
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('LookupSelector.do?command=popUpSceltaAuditAllevamenti&update=true&idc='+idc + '&orgid='+orgid, title, windowParams);
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}


function popUpSceltaAuditModificaStab(idc,orgid) {
	  title  = '_types';
	  width  =  '400';
	  height =  '100';
	  resize =  'false';
	  bars   =  'no';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	 
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('LookupSelector.do?command=popUpSceltaAuditStab&update=true&idc='+idc + '&orgid='+orgid, title, windowParams);
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}




function popUpSceltaAuditUpdate(idc,orgid) {
	  title  = '_types';
	  width  =  '400';
	  height =  '100';
	  resize =  'false';
	  bars   =  'no';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	 
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('LookupSelector.do?command=popUpSceltaAudit?update=true&idc='+idc + '&orgid='+orgid, title, windowParams);
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}


function popLookupSelectorCustomNew2(displayFieldId,displayFieldId2, table, params) {
	  title  = '_types';
	  width  =  '500';
	  height =  '450';
	  resize =  'yes';
	  bars   =  'no';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('LookupSelector.do?command=PopupSelectorCustomNew&displayFieldId=' + displayFieldId + '&displayFieldId2=' + displayFieldId2 + '&table=' + table + params, title, windowParams);
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}

function closeCheckDia(){
	opener.document.getElementById("partitaIva").style.background = "green";
	
	 window.close();
	
}

function popLookupSelectorCustomVet(displayFieldId2, table, params) {
	  title  = '_types';
	  width  =  '500';
	  height =  '450';
	  resize =  'yes';
	  bars   =  'no';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('LookupSelector.do?command=PopupSelectorCustomVet&displayFieldId2=' + displayFieldId2 + '&table=' + table + params, title, windowParams);
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}
//inserito da Francesco
function popLookupSelectorCustom(displayFieldId, displayFieldId2, table, params) {
  title  = '_types';
  width  =  '500';
  height =  '450';
  resize =  'yes';
  bars   =  'no';
  
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  
  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  var newwin=window.open('LookupSelector.do?command=PopupSelectorCustom&displayFieldId=' + displayFieldId + '&displayFieldId2=' + displayFieldId2 + '&table=' + table + params, title, windowParams);
  newwin.focus();

  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}


function popLookupSelectorCustomDia(displayFieldId, displayFieldId2, table, params) {
	  title  = '_types';
	  width  =  '500';
	  height =  '450';
	  resize =  'yes';
	  bars   =  'no';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('LookupSelector.do?command=PopupSelectorCustomDia&displayFieldId=' + displayFieldId + '&displayFieldId2=' + displayFieldId2 + '&table=' + table + params, title, windowParams);
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}

function popLookupSelectorCustomCU(tipologia, displayFieldId, params) {
	title  = '_types';
	width  =  '500';
	height =  '450';
	resize =  'yes';
	bars   =  'yes';
			  
	var posx = (screen.width - width)/2;
	var posy = (screen.height - height)/2;
	var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	if(tipologia == "1"){
		var newwin = window.open('AccountVigilanza.do?command=Add&orgId='+displayFieldId+'&popup=true', title, windowParams);
	}
	else {
		var newwin=window.open('AbusivismiVigilanza.do?command=Add&orgId='+displayFieldId+'&popup=true', title, windowParams);
	}
	
	newwin.focus();

	if (newwin != null) {
		if (newwin.opener == null)
			newwin.opener = self;
		}
}



  function setParentValue(displayFieldId, fieldValue) {
    opener.document.getElementById(displayFieldId).value = fieldValue;
    window.close();
  }
  
  function setDestinatarioField( indiceDestinatario, orgId, ragioneSociale, tipo, hasNumCapi )
  {
	  	if(indiceDestinatario > 2)
	    	indiceDestinatario = indiceDestinatario-2;
	    opener.document.getElementById( 'destinatario_' + indiceDestinatario + "_id" ).value = orgId;
	    opener.document.getElementById( 'destinatario_label_' + indiceDestinatario ).innerHTML = ragioneSociale;
	    opener.document.getElementById( 'destinatario_' + indiceDestinatario + "_nome" ).value = ragioneSociale;
	    opener.document.getElementById( 'destinatario_' + indiceDestinatario + "_nome" ).onchange();
	    if(opener.gestioneObbligNumCapiDestCarniPostSelezione!=undefined)
	    	opener.gestioneObbligNumCapiDestCarniPostSelezione(indiceDestinatario, orgId, ragioneSociale, tipo, hasNumCapi);
	    window.close();
  }
  
  function setOperatoreField(ragioneSociale,tipo,num_reg,targa,orgIdOp)
  {
	    //opener.document.getElementById( 'ragione_sociale').value = ragioneSociale;
	    //opener.document.getElementById( 'tipo_operatore').value = tipo;
	    //opener.document.getElementById( 'num_reg').value = num_reg;
	    
	    var dim = opener.document.getElementById('dim');
	  	dim.value=parseInt(dim.value)+1;  
	  	addOperatoreControllato(tipo,ragioneSociale,num_reg,targa,dim.value,orgIdOp);
	    window.close();
  } 
  
  function setOperatoreFieldZone(ragioneSociale,citta,note,orgIdOp)
  {
	    //opener.document.getElementById( 'ragione_sociale').value = ragioneSociale;
	    //opener.document.getElementById( 'tipo_operatore').value = tipo;
	    //opener.document.getElementById( 'num_reg').value = num_reg;
	    
	    var dim = opener.document.getElementById('dim');
	  	dim.value=parseInt(dim.value)+1;  
	  	addOperatoreControllatoZone(ragioneSociale,citta,note,dim.value,orgIdOp);
	    window.close();
  } 

  function setSpeditoreField(nome,num_reg,prop,asl,comune,prov,stato,orgId)
  {
	  window.opener.document.getElementById("cd_dati_speditore").value = 'DENOMINAZIONE:'+nome+'\n'+
	  'CODICE AZIENDA:'+num_reg+'\n'+'PROPRIETARIO:'+prop+'\n'+'ASL:'+asl+'\n'+'STATO:'+stato+'\n'+
	  'PROVINCIA:'+prov+'\n'+'COMUNE:'+comune+'\n' ;
	  
	  window.opener.document.getElementById("cd_id_speditore").value = orgId;
	  window.close();
  } 
  
  
  function setLaboratorioField(ragioneSociale,num_reg,orgIdOp)
  {
	    
	    var dim = opener.document.getElementById('dim_lab');
	  	dim.value=parseInt(dim.value)+1;  
	  	addLaboratorioControllato(ragioneSociale,num_reg,dim.value,orgIdOp);
	    window.close();
  } 
  
  function clonaTextArea() {
	  
	  var dim = document.getElementById('dim_lab_noreg');
	  dim.value=parseInt(dim.value)+1; 
	  var tblBody = document.getElementById('tblClone_noreg').tBodies[0];
	  var newNode = tblBody.rows[2].cloneNode(true);
	   	
	   /*Imposto gli id dei nuovi campi*/
	   /*In questo caso 3 input di tipo text presi in ordine*/
		
	   newNode.id= 'riga_noreg_'+dim.value;
		for (i=0;i<newNode.getElementsByTagName('TEXTAREA').length;i++)
		{
			newNode.getElementsByTagName('TEXTAREA')[i].id =newNode.getElementsByTagName('TEXTAREA')[i].id + "_"+dim.value;
		  	//newNode.getElementsByTagName('TEXTAREA')[i].name = newNode.getElementsByTagName('TEXTAREA')[i].id + "_"+dim.value;
			
		  	if (i==0)
		  		newNode.getElementsByTagName('TEXTAREA')[i].value = '';
		
		}
		
		newNode.getElementsByTagName('a')[0].id="elimina"+"_"+dim.value;
		newNode.getElementsByTagName('a')[0].href ="javascript:eliminaTextArea("+dim.value+")"; 
		
	  	/*Lo rendo visibile*/
		newNode.style.display="";
	  	
	  	/*Aggancio il nodo*/
	  	//alert(clonato.parentNode)
	  	//clonato.parentNode.appendChild(clone);
	   tblBody.appendChild(newNode);
  }
 
  
  
  //inserito da Francesco
  function setParentValue2(displayFieldId, fieldValue, displayFieldId2, fieldValue2, displayFieldId3, fieldValue3) {
	 
	 
	  if (fieldValue != '01.50.00' && fieldValue>='01' && fieldValue<= '01.28.00' && fieldValue2.indexOf("Coltivazio") >= 0) 
	  {
		  alert("ATTENZIONE! L'INSERIMENTO DI UNA AZIENDA AGRICOLA DEVE AVVENIRE DALL'APPOSITO MODULO AZIENDE AGRICOLE.");
	  }
	  else
		  {
	  
      if(opener.document.getElementById("secondari")!=null)
	    	opener.document.getElementById("secondari").disabled=false;
    opener.document.getElementById(displayFieldId).value = fieldValue;
   
    try
    {
    	if( displayFieldId!='codici_selezionabili')
        	opener.document.getElementById(displayFieldId).onchange;
    
        if ( opener.document.getElementById(displayFieldId2)!=null) {
        	opener.document.getElementById(displayFieldId2).value = fieldValue2;
        	opener.document.getElementById(displayFieldId3).value = fieldValue3;
        	opener.document.getElementById(displayFieldId).onchange();
        }
       
    }
    catch(e)
    {
    }
    finally
    {
    	window.close();
    }
		  }
    
  }
  
  function setParentValue2Singola(displayFieldId, fieldValue, displayFieldId2, fieldValue2) {
		 
	  var clonato = opener.document.getElementById('la_stab_soa');
	  clonato.style.display="none";
	  clonato.getElementsByTagName('INPUT')[0].value = '';
	  clonato.getElementsByTagName('INPUT')[1].value = '';
	  if (clonato !=null)
	  {
	  clone=clonato.cloneNode(true);
	  flagSelezione = false ; 
	 
	  elem_num = opener.document.getElementById('num_linee');
	  
	  elem_num_int = parseInt(elem_num.value);
	  for(i=1 ;i<=elem_num_int ; i++)
	  {
		  
		  clonato.parentNode.removeChild(opener.document.getElementById('la_stab_soa'+i));
		  elem_num.value -=1 ; 
	  }
	  
	  }
	  
			  elem_num.value = (parseInt(elem_num.value) +1)+'' ;
				clone=clonato.cloneNode(true);
				flagSelezione = true ; 
				clone.getElementsByTagName('INPUT')[0].value = fieldValue;
				clone.getElementsByTagName('INPUT')[1].value = fieldValue2;
				clone.style.display="block";
				clone.id = "la_stab_soa"+elem_num.value;
				clonato.parentNode.appendChild(clone);
				
				
		
	  
     
    	window.close();
    
	  
    
  }
  
  function setParentValue2SingolaModifica(displayFieldId, fieldValue, displayFieldId2, fieldValue2) {
		
	  if (document.details.tipoCampione.value == "4")
	  {
	  var clonato = document.getElementById('la_stab_soa');
	  clonato.style.display="none";
	  clonato.getElementsByTagName('INPUT')[0].value = '';
	  clonato.getElementsByTagName('INPUT')[1].value = '';
	  if (clonato !=null)
	  {
	  clone=clonato.cloneNode(true);
	  flagSelezione = false ; 
	 
	  elem_num = document.getElementById('num_linee');
	  
	  elem_num_int = parseInt(elem_num.value);
	  for(i=1 ;i<=elem_num_int ; i++)
	  {
		  
		  clonato.parentNode.removeChild(opener.document.getElementById('la_stab_soa'+i));
		  elem_num.value -=1 ; 
	  }
	  
	  }
	  
				clone=clonato.cloneNode(true);
				flagSelezione = true ; 
				clone.getElementsByTagName('INPUT')[0].value = fieldValue;
				clone.getElementsByTagName('INPUT')[1].value = fieldValue2;
				clone.style.display="block";
				clone.id = "la_stab_soa"+elem_num.value;
				clonato.parentNode.appendChild(clone);
				
				
	  }
	  
    
  }
  
  function setParentValueMultipla(arrayValue,arrayDescrizioni){
	  	
	  	
	  
	  var clonato = opener.document.getElementById('la_stab_soa');
	  clonato.style.display="none"; 	
	  clonato.getElementsByTagName('INPUT')[0].value = '';
	  clonato.getElementsByTagName('INPUT')[1].value = '';
	  clone=clonato.cloneNode(true);
	  flagSelezione = false ; 
	 
	  elem_num = opener.document.getElementById('num_linee');
	  
	  elem_num_int = parseInt(elem_num.value);
	  for(i=1 ;i<=elem_num_int ; i++)
	  {
		  
		  clonato.parentNode.removeChild(opener.document.getElementById('la_stab_soa'+i));
		  elem_num.value -=1 ; 
	  }
	  
	  
	 
  	  
  	  
  	  
  	  
	  
	  
		for (i=0;i<arrayValue.length;i++)
		{
			if(arrayValue[i]!= null && arrayValue[i] != '')
			{
				  elem_num.value = (parseInt(elem_num.value) +1)+'' ;
				clone=clonato.cloneNode(true);
				flagSelezione = true ; 
				clone.getElementsByTagName('INPUT')[0].value = arrayValue[i];
				clone.getElementsByTagName('INPUT')[1].value = arrayDescrizioni[i];
				clone.style.display="block";
				clone.id = "la_stab_soa"+elem_num.value;
				clonato.parentNode.appendChild(clone);
				
				
				
			}
		}
		
		
		
		
		if(flagSelezione==false)
		{
			alert('Seleziona Almeno ina linea di attivita');
		}else
			window.close();
		
  }
  
  
  
  function setParentValueAudit(locationIn) {
	 
	  opener.document.addAccountAudit.submit();
	 // opener.location=locationIn
	   
	    window.close();
	  }
  
  function setParentValueAuditUpdate(locationIn) {
	  
	 
	 
	  opener.document.addAccountAudit.action=locationIn;
	 
	  opener.document.addAccountAudit.submit();
	 // opener.location=locationIn
	   
	    window.close();
	  }
  

  function setParentValueAuditAggiornaCategoria() {
	  opener.document.addAccountAudit.aggiorna.value='yes'; 

	  opener.document.addAccountAudit.submit();
	
	   
	    window.close();
	  }
  
  function setParentValueAuditAggiornaCategoriaModifica(locationIn) {
	  opener.document.addAccountAudit.aggiorna.value='yes'; 

	  opener.document.addAccountAudit.action=locationIn;
		 
	  opener.document.addAccountAudit.submit();
	 // opener.location=locationIn
	   
	    window.close();
	
	  
	  }
  
  function provaOrgani(){
	  	var maxElementi = 100;
	  	var elementi;
	  	var elementoClone;
	  	var tableClonata;
	  	var tabella;
	  	var selezionato;
	  	var x;
	  	elementi = document.getElementById('elementi');
	  	elementi.value=parseInt(elementi.value)+1;
	  	size = document.getElementById('size');
	  	size.value=parseInt(size.value)+1;
	  	var primo_elemento = document.main.lcso_patologia_1;
	  	var indice = parseInt(elementi.value) - 1;
	  	
	  	x = document.getElementById('lcso_patologia_'+String(indice));
	  	if(primo_elemento!=null && x==null){
	  		selezionato = document.main.lcso_patologia_1.selectedIndex;
	  	}else if(primo_elemento==null && x!=null){
	  		selezionato = x.selectedIndex;
	  	}
	  	var clonanbsp = document.getElementById('nbsp');
	  	var clonato = document.getElementById('row');
	  	var clonato2 = document.getElementById('tr');
	  	var clonato3 = document.getElementById('ww');
	  	
	  	/*clona riga vuota*/
	  	clone=clonanbsp.cloneNode(true);
		  	
	  	clone.getElementsByTagName('TD')[0].name = "nbsptr1_"+elementi.value;
	  	clone.getElementsByTagName('TD')[0].id = "nbsptr1_"+elementi.value;
	  	clone.getElementsByTagName('input')[0].name = "lcso_id_"+elementi.value;
	  	clone.id = "nbsp_"+elementi.value;
	  	
	  	/*Lo rendo visibile*/
	  	
	  	//clone.style.visibility="visible";
	  		  	
	  	/*Aggancio il nodo*/
	  	clonanbsp.parentNode.appendChild(clone);

	  	/*Lo rendo visibile*/
	  	clone.style.bgcolor="#EDEDED";
	  	clone.style.visibility="visible";
	  	
	  	/*clona organo*/
	  	clone=clonato3.cloneNode(true);
		  	
	  	clone.getElementsByTagName('SELECT')[0].name = "lcso_organo_"+elementi.value;
	  	clone.getElementsByTagName('SELECT')[0].id = "lcso_organo_"+elementi.value;
		clone.getElementsByTagName('SELECT')[0].onchange= function () {
			elemento_selezionato=document.getElementById('lcso_organo_'+String(indice+1)).value;
//			riga=indice+1;
//			alert("Nella funzione chiamata sull'onchange: indice_riga=" + riga + " value_organo=" + elemento_selezionato);
			vpm_seleziona_lookup_patologia_organo(indice+1, elemento_selezionato, -1);
		}
	  	
	  	clone.id = "ww_"+elementi.value;
	  	
	  	/*Aggancio il nodo*/
	  	clonato3.parentNode.appendChild(clone);

	  	/*Lo rendo visibile*/
	  	//clone.style.display="block";
	  	clone.style.visibility="visible";
	  	
	  	/*clona patologia*/	  	
	  	clone=clonato.cloneNode(true);
	  		  		  	
	  	clone.getElementsByTagName('SELECT')[0].name = "lesione_milza_" + elementi.value;
	  	clone.getElementsByTagName('SELECT')[0].id = "lesione_milza_" + elementi.value;
	  	clone.getElementsByTagName('SELECT')[0].selectedIndex = selezionato;
	  	clone.getElementsByTagName('SELECT')[0].multiple = 'multiple';
	  	clone.getElementsByTagName('SELECT')[0].size = 5;
	  	clone.getElementsByTagName('SELECT')[0].setAttribute('onchange','javascript:mostraLcsoPatologiaAltro(this,' + elementi.value + ')');
	  	
		clone.getElementsByTagName('SELECT')[1].name = "lesione_cuore_" + elementi.value;
	  	clone.getElementsByTagName('SELECT')[1].id = "lesione_cuore_" + elementi.value;
	  	clone.getElementsByTagName('SELECT')[1].selectedIndex = selezionato;
	  	clone.getElementsByTagName('SELECT')[1].multiple = 'multiple';
	  	clone.getElementsByTagName('SELECT')[1].size = 5;
	  	clone.getElementsByTagName('SELECT')[1].setAttribute('onchange','javascript:mostraLcsoPatologiaAltro(this,' + elementi.value + ')');
		clone.getElementsByTagName('SELECT')[2].name = "lesione_polmoni_" + elementi.value;
	  	clone.getElementsByTagName('SELECT')[2].id = "lesione_polmoni_" + elementi.value;
	  	clone.getElementsByTagName('SELECT')[2].selectedIndex = selezionato;
	  	clone.getElementsByTagName('SELECT')[2].multiple = 'multiple';
	  	clone.getElementsByTagName('SELECT')[2].size = 5;
	  	clone.getElementsByTagName('SELECT')[2].setAttribute('onchange','javascript:mostraLcsoPatologiaAltro(this,' + elementi.value + ')');
	  	clone.getElementsByTagName('SELECT')[3].name = "lesione_visceri_" + elementi.value;
	  	clone.getElementsByTagName('SELECT')[3].id = "lesione_visceri_" + elementi.value;
	  	clone.getElementsByTagName('SELECT')[3].selectedIndex = selezionato;
	  	clone.getElementsByTagName('SELECT')[3].multiple = 'multiple';
	  	clone.getElementsByTagName('SELECT')[3].size = 5;
	  	clone.getElementsByTagName('SELECT')[3].setAttribute('onchange','javascript:mostraLcsoPatologiaAltro(this,' + elementi.value + ')');
		clone.getElementsByTagName('SELECT')[4].name = "lesione_fegato_" + elementi.value;
	  	clone.getElementsByTagName('SELECT')[4].id = "lesione_fegato_" + elementi.value;
	  	clone.getElementsByTagName('SELECT')[4].selectedIndex = selezionato;
	  	clone.getElementsByTagName('SELECT')[4].multiple = 'multiple';
	  	clone.getElementsByTagName('SELECT')[4].size = 5;
	  	clone.getElementsByTagName('SELECT')[4].setAttribute('onchange','javascript:mostraLcsoPatologiaAltro(this,' + elementi.value + ')');
	  	
		clone.getElementsByTagName('SELECT')[5].name = "lesione_rene_" + elementi.value;
	  	clone.getElementsByTagName('SELECT')[5].id = "lesione_rene_" + elementi.value;
	  	clone.getElementsByTagName('SELECT')[5].selectedIndex = selezionato;
	  	clone.getElementsByTagName('SELECT')[5].multiple = 'multiple';
	  	clone.getElementsByTagName('SELECT')[5].size = 5;
	  	clone.getElementsByTagName('SELECT')[5].setAttribute('onchange','javascript:mostraLcsoPatologiaAltro(this,' + elementi.value + ')');
	  	
		clone.getElementsByTagName('SELECT')[6].name = "lesione_mammella_" + elementi.value;
	  	clone.getElementsByTagName('SELECT')[6].id = "lesione_mammella_" + elementi.value;
	  	clone.getElementsByTagName('SELECT')[6].selectedIndex = selezionato;
	  	clone.getElementsByTagName('SELECT')[6].multiple = 'multiple';
	  	clone.getElementsByTagName('SELECT')[6].size = 5;
	  	clone.getElementsByTagName('SELECT')[6].setAttribute('onchange','javascript:mostraLcsoPatologiaAltro(this,' + elementi.value + ')');
	  	
		clone.getElementsByTagName('SELECT')[7].name = "lesione_apparato_genitale_" + elementi.value;
	  	clone.getElementsByTagName('SELECT')[7].id = "lesione_apparato_genitale_" + elementi.value;
	  	clone.getElementsByTagName('SELECT')[7].selectedIndex = selezionato;
	  	clone.getElementsByTagName('SELECT')[7].multiple = 'multiple';
	  	clone.getElementsByTagName('SELECT')[7].size = 5;
	  	clone.getElementsByTagName('SELECT')[7].setAttribute('onchange','javascript:mostraLcsoPatologiaAltro(this,' + elementi.value + ')');
	  	
		clone.getElementsByTagName('SELECT')[8].name = "lesione_stomaco_" + elementi.value;
	  	clone.getElementsByTagName('SELECT')[8].id = "lesione_stomaco_" + elementi.value;
	  	clone.getElementsByTagName('SELECT')[8].selectedIndex = selezionato;
	  	clone.getElementsByTagName('SELECT')[8].multiple = 'multiple';
	  	clone.getElementsByTagName('SELECT')[8].size = 5;
	  	clone.getElementsByTagName('SELECT')[8].setAttribute('onchange','javascript:mostraLcsoPatologiaAltro(this,' + elementi.value + ')');
	  	
		clone.getElementsByTagName('SELECT')[9].name = "lesione_intestino_" + elementi.value;
	  	clone.getElementsByTagName('SELECT')[9].id = "lesione_intestino_" + elementi.value;
	  	clone.getElementsByTagName('SELECT')[9].selectedIndex = selezionato;
	  	clone.getElementsByTagName('SELECT')[9].multiple = 'multiple';
	  	clone.getElementsByTagName('SELECT')[9].size = 5;
	  	clone.getElementsByTagName('SELECT')[9].setAttribute('onchange','javascript:mostraLcsoPatologiaAltro(this,' + elementi.value + ')');
	  	
		clone.getElementsByTagName('SELECT')[10].name = "lesione_osteomuscolari_" + elementi.value;
	  	clone.getElementsByTagName('SELECT')[10].id = "lesione_osteomuscolari_" + elementi.value;
	  	clone.getElementsByTagName('SELECT')[10].selectedIndex = selezionato;
	  	clone.getElementsByTagName('SELECT')[10].multiple = 'multiple';
	  	clone.getElementsByTagName('SELECT')[10].size = 5;
	  	clone.getElementsByTagName('SELECT')[10].setAttribute('onchange','javascript:mostraLcsoPatologiaAltro(this,' + elementi.value + ')');
	  	
		clone.getElementsByTagName('SELECT')[11].name = "lesione_generici_" + elementi.value;
	  	clone.getElementsByTagName('SELECT')[11].id = "lesione_generici_" + elementi.value;
	  	clone.getElementsByTagName('SELECT')[11].selectedIndex = selezionato;
	  	clone.getElementsByTagName('SELECT')[11].multiple = 'multiple';
	  	clone.getElementsByTagName('SELECT')[11].size = 5;
	  	clone.getElementsByTagName('SELECT')[11].setAttribute('onchange','javascript:mostraLcsoPatologiaAltro(this,' + elementi.value + ')');
	  	
	  	clone.getElementsByTagName('SELECT')[12].name = "lesione_altro_" + elementi.value;
	  	clone.getElementsByTagName('SELECT')[12].id = "lesione_altro_" + elementi.value;
	  	clone.getElementsByTagName('SELECT')[12].selectedIndex = selezionato;
	  	clone.getElementsByTagName('SELECT')[12].multiple = 'multiple';
	  	clone.getElementsByTagName('SELECT')[12].size = 5;
	  	clone.getElementsByTagName('SELECT')[12].setAttribute('onchange','javascript:mostraLcsoPatologiaAltro(this,' + elementi.value + ')');
	  	
	  	var lcso_patologia_altro = clone.getElementsByClassName('lcso_patologia_altro_class')[0];
	  	lcso_patologia_altro.name = "lcso_patologiaaltro_" + elementi.value;
	  	lcso_patologia_altro.id = "lcso_patologiaaltro_" + elementi.value;
	  	
	  	
	  	clone.getElementsByTagName('TD')[1].innerHTML = clone.getElementsByTagName('TD')[1].innerHTML.replace( '--placeholder--', elementi.value );
	  	clone.id = "row_" + elementi.value;
	  	
	  	/*Aggancio il nodo*/
	  	clonato.parentNode.appendChild(clone);
	  	
	  	/*Lo rendo visibile*/
	  	//clone.style.display="block";
	  	clone.style.visibility="visible";

	  	//imposto l'evento per visualizzare e nascondere il campo stadio
//	  	alert( document.getElementById( "lcso_patologia_" + elementi.value ).onchange );
//	  	document.getElementById( "lcso_patologia_" + elementi.value ).onchange = "javascript:displayStadio(" + elementi.value + ")";
//	  	alert( document.getElementById( "lcso_patologia_" + elementi.value ).onchange );
	  	
	  	/*clona stadio*/
	  	clone=clonato2.cloneNode(true);
	  	
	  	clone.getElementsByTagName('SELECT')[0].name = "lcso_stadio_"+elementi.value;
	  	clone.getElementsByTagName('SELECT')[0].id = "lcso_stadio_"+elementi.value;
	  	clone.id = "stadio_"+elementi.value;
	  	
	  	/*Aggancio il nodo*/
	  	clonato2.parentNode.appendChild(clone);

	  	/*Lo rendo visibile*/
	  	clone.style.display="none";
	  	clone.style.visibility="visible";
	  	
	  }
  
  
  
  function clonaCoordinate(){
	  	

	  	
	  var clonato = document.getElementById('riga_coordinate_molluschi1');
	  clone=clonato.cloneNode(true);
	 
	  numcoordinate = document.getElementById('size_coordinate');
	  if(numcoordinate.value<=9)
	  {
	  numcoordinate.value = (parseInt(numcoordinate.value)) +1 ;
				clone=clonato.cloneNode(true);
				flagSelezione = true ; 
				clone.getElementsByTagName('INPUT')[0].value = "";
				clone.getElementsByTagName('INPUT')[0].name='latitudine_campione'+numcoordinate.value;
				clone.getElementsByTagName('INPUT')[0].id='latitudine_campione'+numcoordinate.value;
				
				clone.getElementsByTagName('INPUT')[1].value = "";
				clone.getElementsByTagName('INPUT')[1].name='longitudine_campione'+numcoordinate.value;
				clone.getElementsByTagName('INPUT')[1].id='longitudine_campione'+numcoordinate.value;
				
				var indice = parseInt(numcoordinate.value);
				clone.getElementsByTagName('A')[0].onclick=function()
				{
					removeCoordinate(indice) ;
				}
				
				clone.id='riga_coordinate_molluschi'+ numcoordinate.value ;
				clonato.parentNode.appendChild(clone);
	  }
		
  }
  
  function removeCoordinate(indice)
  {
	  
	  numcoordinate = document.getElementById('size_coordinate');
	  
	  var clonato = document.getElementById('riga_coordinate_molluschi1');
	  
	  if(parseInt(numcoordinate.value)>1)
	  {
	  clonato.parentNode.removeChild(document.getElementById('riga_coordinate_molluschi'+indice));
	
	  for(i=indice+1;i<=parseInt(numcoordinate.value);i++)
	  {
		  if(document.getElementById('riga_coordinate_molluschi'+i)!=null)
		  {
			  
			  resetCoordinate(i);
		  }
		  
	  }
	  
	  
	  
	  numcoordinate.value = numcoordinate.value-1 ;
	  }
	  else
	  {
		  document.getElementById('latitudine_campione1').value='';
		  document.getElementById('longitudine_campione1').value='';
	  }
	  
	  
  }
  
  function resetCoordinate(i)
  {
	  clone = document.getElementById('riga_coordinate_molluschi'+i);
	  
		clone.getElementsByTagName('INPUT')[0].name='latitudine_campione'+(i-1);
		clone.getElementsByTagName('INPUT')[0].id='latitudine_campione'+(i-1);
		
		clone.getElementsByTagName('INPUT')[1].name='longitudine_campione'+(i-1);
		clone.getElementsByTagName('INPUT')[1].id='longitudine_campione'+(i-1);
		
		clone.getElementsByTagName('A')[0].id=(i-1)+'';
		clone.getElementsByTagName('A')[0].onclick=function()
		{
			j=i-1;
			removeCoordinate(j) ;
		}
	
	  
	  document.getElementById('riga_coordinate_molluschi'+i).id='riga_coordinate_molluschi'+(i-1);
  }
  
  var posizione;
  
  
  
  var optionB11 = document.createElement("option");
  optionB11.text =  "ATTO B11 – rintracciabilità e sicurezza alimentare";
  optionB11.value = "5";
  
  function clonaNelPadrePiani(arrayValue,arrayDescrizioni){
		  
	  var flagCondizionalita = false ;
	  var flagCondizionalitaB11 = false ;
	  var clonato = document.getElementById('clonepiano');
	  clone=clonato.cloneNode(true);
	  flagSelezione = false ; 
	 
	 
		  elem_num_piani = document.getElementById('num_piani');
	  num_piani_int = parseInt(elem_num_piani.value);
	  for(i=1 ;i<=num_piani_int ; i++)
	  {
		 
			  clonato.parentNode.removeChild(document.getElementById('clonepiano'+i));
		  elem_num_piani.value -=1 ; 
	  }
	  
	  
	 	  indice = 1 ;
	 	  var hasPreavviso = false;
		for (i=0;i<arrayValue.length;i++)
		{
			clone = null ;
			if(arrayValue[i]!= null && arrayValue[i] != -1)
			{
				
				
				getCodiceInternoTipoPiano(arrayValue[i]);
				
				
				
				if ((codiceInternoTipoIspezione== '982' || codiceInternoTipoIspezione == '983') && flagCondizionalitaReturn=='true') 
				{
					var select = document.getElementById("condizionalita");
				//	rimuoviDaSelect(select, "5");
					hasPreavviso = true;
					flagCondizionalita = true  ;
					
					document.getElementById("condizionalitarow").style.display = "" ;
					document.getElementById("checklist_ba_tr").style.display = "" ;
					if(document.getElementById("Allev01") != null){
						document.getElementById("Allev01").style.display = "" ;
					}
					document.getElementById("cond_cb").checked='checked';
					//alert('iuuuuu1');
				}
				
				if ((codiceInternoTipoIspezione== '1483' ) && flagCondizionalitaReturn=='true') 
				{
					hasPreavviso = true;
					flagCondizionalitaB11 = true  ;
						
					document.getElementById("condizionalitab11row").style.display = "" ;
					document.getElementById("checklist_ba_tr").style.display = "" ;
					document.getElementById("cond_b11_cb").checked='checked';
					//alert('iuuuuu2');
					if(document.getElementById("Allev01") != null){
						document.getElementById("Allev01").style.display = "" ;
					}
				}
		
				  elem_num_piani.value = (parseInt(elem_num_piani.value) +1)+'' ;
				clone=clonato.cloneNode(true);
				flagSelezione = true ; 
				clone.getElementsByTagName('INPUT')[0].value = arrayValue[i];
				clone.getElementsByTagName('INPUT')[0].name='piano_monitoraggio'+indice;
				clone.getElementsByTagName('INPUT')[0].id='piano_monitoraggio'+indice;
				
				clone.getElementsByTagName('INPUT')[1].name='uo'+indice;
				clone.getElementsByTagName('INPUT')[1].id='uo'+indice;
				
				clone.getElementsByTagName('DIV')[0].name='uodescr'+indice;
				clone.getElementsByTagName('DIV')[0].id='uodescr'+indice;
			
				clone.getElementsByTagName('A')[0].id="ancora_"+indice;
				
				clone.getElementsByTagName('LABEL')[0].innerHTML = "<b>"+elem_num_piani.value+") </b>"+arrayDescrizioni[i] ;
				clone.getElementsByTagName('LABEL')[0].id="piano"+indice;
				clone.style.display="";
				clone.id = "clonepiano"+elem_num_piani.value;
				
				
				//clone.getElementsByTagName('A')[0].onclick= function(){setuo(this) };
				
				posizione=indice ;
				if (i==0)
					$('#clonepiano').after(clone)
					else
						$('#clonepiano'+i).after(clone);
				
//				popolaCampi(arrayValue[i], arrayDescrizioni[i] );
					//clonato.parentNode.appendChild(clonepiano);
				
					
				
				
				$('#ancora_'+posizione).click(function(evt) {
			      
			      var pos = this.id.split('_')[1] ;
			      viewuo(pos);

			    });
				
				indice++;
				
				//Gestione dati estesi
			
					
				
			}
			
			if (!hasPreavviso){
				if(document.getElementById("Allev01") != null){
					document.getElementById("Allev01").style.display = "none" ;
			}
			}
		}
		
		if (window.location.href.indexOf('AcqueRete')!=-1){
		gestioneCloneAcqueDiRete(clonato);
		}
		
		if (!flagCondizionalita){
			document.getElementById("condizionalitarow").style.display = "none" ;
			document.getElementById("cond_cb").checked='';
			clearSelected(document.getElementById("condizionalita"));
			
		}
		if (!flagCondizionalitaB11){
			document.getElementById("condizionalitab11row").style.display = "none" ;
			document.getElementById("cond_b11_cb").checked='';
			}
		if (!flagCondizionalita && !flagCondizionalitaB11)
			document.getElementById("checklist_ba_tr").style.display = "none" ;
		//Gestione dati estesi
//		if ( (typeof window.mostraEstesi != 'undefined')) {
//				  mostraEstesi();
//				}
	
	
  }
  
  function clearSelected(select){
	    var elements = select.options;

	    for(var i = 0; i < elements.length; i++){
	      elements[i].selected = false;
	    }
	  }
  
  function rimuoviDaSelect(select, valore){
	  for (var i=0; i<select.length; i++){
		  if (select.options[i].value == '5' ){
			  select.remove(i);
		  break;
		  }
		  }
  }
  function aggiungiASelect(select, opzione){
	  select.add(opzione);
  }
  
  function gestioneCloneAcqueDiRete(clonato){
	  clonato.parentNode.removeChild(document.getElementById('clonepiano'));
	 }
  
  function viewuo(ind) 
	{	
	  	document.getElementById('tipoIspezionePerContoDiDialog').value='piano';
		document.getElementById("posizionePianoDialog").value=ind ;
		$('#dialogPerContoDi' ).dialog('open');
	}

  function rimuoviClonaNelPadrePiani(){
	  
	  	if (opener !=null)
	  var clonato = opener.document.getElementById('clonepiano');
	  	else
	  		var clonato = document.getElementById('clonepiano');
	  clone=clonato.cloneNode(true);
	  flagSelezione = false ; 
	 
	  if (opener != null)
	  elem_num_piani = opener.document.getElementById('num_piani');
	  else
		  elem_num_piani = document.getElementById('num_piani');
	  num_piani_int = parseInt(elem_num_piani.value);
	 
		  while (document.getElementById('clonepiano'+i)!=null)
			  {
			  clonato.parentNode.removeChild(document.getElementById('clonepiano'+i));
	  }
	  
	 	//opener.document.getElementById('per_conto_di').style.display="" ;
		
		
}
  /*INIZIO SCRIPT ACQUE DI RETE*/
  
  function disabilitaAltri(fieltd){	
	  if (fieltd.checked)
		  fieltd.value='on';
	  else
		  fieltd.value='';
	  
	  var orgId = fieltd.name.split("_")[1];
	  var i = fieltd.name.split("_")[0].split("field")[1];
	  var tipo = document.getElementById("field9_"+orgId).value;
	 
		for (var k=4; k <=11 ; k++){
				if (k!=i && k!=9 && k!=8 && k!=10){
					if(fieltd.checked){
						document.getElementById("field"+k+"_"+orgId).disabled = 'true';
						document.getElementById("field"+k+"_"+orgId).checked = false;
						document.getElementById("field"+k+"_"+orgId).value='';
					}else{
						document.getElementById("field"+k+"_"+orgId).disabled = '';	
					}
				}
			}
  }
  
  
  function disabilitaAltri_(fieltd)
  {
	
	  var orgId = fieltd.name.split("_")[1];
	  var i = fieltd.name.split("_")[0].split("field")[1];
	 var tipo = document.getElementById("field9_"+orgId).value;
	 
	 if (tipo!=9 || i<=5 || i==10)
		 disabilitaAltriTutti(fieltd);
	 else
		 disabilitaAltriPrimiDue(fieltd);
  }
	 
  function disabilitaAltriTutti(fieltd){
	  //alert(fieltd.id);
	  var orgId = fieltd.name.split("_")[1];
	  var iid=fieltd.name.split("_")[0];
  		for (i=4; i <=10 ; i++){
  			if (i!=8 && i!=9)
  				if (("field"+i+"_"+orgId) != fieltd.id){
  					if (fieltd.checked){
  						document.getElementById("field"+i+"_"+orgId).disabled = 'true';
  						fieltd.value='on';
  					}else{	
  						document.getElementById("field"+i+"_"+orgId).disabled = '';
  					}
  				}
	  		}
  		if(iid=='field10'){
  			for (i=4; i <=11 ; i++){
  				if (i!=10 && i!=9 && i!=8){
  					if(fieltd.checked){
  						document.getElementById("field"+i+"_"+orgId).disabled = 'true';
  						document.getElementById("field"+i+"_"+orgId).checked = false;
  					}else{
  						document.getElementById("field"+i+"_"+orgId).disabled = '';	
  					}
  				}
  			}
  		}	
  }
  

  function disabilitaAltriPrimiDue(fieltd){
	  var orgId = fieltd.name.split("_")[1];
	  for (i=4; i <=10 ; i++){
			if (i!=6 && i!=7 && i!=8 && i!=9)
	  	//alert("field"+i+"_"+orgId+" --- "+fieltd.id);
  		//for (i=4; i <=5 ; i++){
  			if (("field"+i+"_"+orgId) != fieltd.id){
  				if (fieltd.checked) {
  					document.getElementById("field"+i+"_"+orgId).disabled = 'true';
  					fieltd.value='on'; 				
  				}else if (!document.getElementById("field6_"+orgId).checked && !document.getElementById("field7_"+orgId).checked){
  					document.getElementById("field"+i+"_"+orgId).disabled = '';
  				}
  			}
  		}	
  }
  
  function clonaNelPadreuntidiPrelievo(arrayValue,arrayDescrizioni, arrayTipi){
	  var clonato = opener.document.getElementById('riga_acque_di_rete');
	  clone=clonato.cloneNode(true);
	  flagSelezione = false ; 
	  indice = 0 ; 
	  
	  var isPianoAcqueNoRadio = false;
	  var isPianoAcqueRadio = false;
	  if (opener.document.getElementById("piano_monitoraggio1").value =="7429")
			isPianoAcqueRadio = true;
	  else if (opener.document.getElementById("piano_monitoraggio1").value =="7430")
			isPianoAcqueNoRadio = true;
	  
	  while(opener.document.getElementById('riga_acque_di_rete'+indice)!=null)
	  {
		  // CERCO L'ORGID SULLA RIGA CORRENTE
		  var orgId =  opener.document.getElementById('pdp'+indice).value;
		  // CERCO L'INDICE DELL'ORGID NELL'ARRAY DI QUELLI SELEZIONATI
		  var indiceOrgId = presenteInArray(orgId, arrayValue);
		  // SE HO TROVATO L'INDICE, NON DEVO CANCELLARE LA RIGA E LO RIMUOVO DALL'ARRAY
		  if (indiceOrgId>-1)
			  {
			  arrayValue.splice(indiceOrgId, 1);
			  arrayDescrizioni.splice(indiceOrgId, 1);
			  arrayTipi.splice(indiceOrgId, 1);
			  }
		  else // SE NON HO TROVATO L'INDICE, ALLORA HO DESELEZIONATO QUELL'ORG E CANCELLO LA RIGA
			  clonato.parentNode.removeChild(opener.document.getElementById('riga_acque_di_rete'+indice));
		  indice++ ;
		
	  }
	  
		
	 	  indice = 1 ;
	 	  // PER OGNI ORG ID RIMANENTE NELL'ARRAY (CHE NON ERA QUINDI GIA' PRESENTE NELLA TABELLA) AGGIUNGO LA RIGA
		for (i=0;i<arrayValue.length;i++)
		{
			
			if(arrayValue[i]!= null && arrayValue[i] != -1)
			{ 
				
				var tmp = arrayValue[i] ;
				clone=clonato.cloneNode(true);
				
				/*alert("0 - " + clone.getElementsByTagName('INPUT')[0].id);
				alert("1 - " + clone.getElementsByTagName('INPUT')[1].id);
				alert("2 - " + clone.getElementsByTagName('INPUT')[2].id);
				alert("3 - " + clone.getElementsByTagName('INPUT')[3].id);
				alert("4 - " + clone.getElementsByTagName('INPUT')[4].id);
				alert("5 - " + clone.getElementsByTagName('INPUT')[5].id);
				alert("6 - " + clone.getElementsByTagName('INPUT')[6].id);
				alert("7 - " + clone.getElementsByTagName('INPUT')[7].id);
				alert("8 - " + clone.getElementsByTagName('INPUT')[8].id);
				alert("9 - " + clone.getElementsByTagName('INPUT')[9].id);
				alert("10 - " + clone.getElementsByTagName('INPUT')[10].id);
				alert("11 - " + clone.getElementsByTagName('INPUT')[11].id);*/
				 
				flagSelezione = true ; 
				clone.getElementsByTagName('INPUT')[0].value = arrayValue[i];
				clone.getElementsByTagName('INPUT')[0].id ='pdp'+i;
				clone.getElementsByTagName('INPUT')[1].value = '';
				clone.getElementsByTagName('INPUT')[1].name = 'field1_'+arrayValue[i];
				clone.getElementsByTagName('INPUT')[1].id = clone.getElementsByTagName('INPUT')[1].name ;
				clone.getElementsByTagName('INPUT')[2].value = '';
				clone.getElementsByTagName('INPUT')[2].name = 'field2_'+arrayValue[i];
				clone.getElementsByTagName('INPUT')[2].id = clone.getElementsByTagName('INPUT')[2].name ;
				clone.getElementsByTagName('INPUT')[3].disabled = '';
				clone.getElementsByTagName('INPUT')[4].disabled = '';
				clone.getElementsByTagName('INPUT')[5].disabled = '';
				clone.getElementsByTagName('INPUT')[6].disabled = '';
				clone.getElementsByTagName('INPUT')[7].disabled = '';
				clone.getElementsByTagName('INPUT')[8].disabled = '';
				clone.getElementsByTagName('INPUT')[9].disabled = '';
				clone.getElementsByTagName('INPUT')[3].checked = '';
				clone.getElementsByTagName('INPUT')[3].name = 'field3_'+arrayValue[i];
				clone.getElementsByTagName('INPUT')[3].id = clone.getElementsByTagName('INPUT')[3].name ;
				clone.getElementsByTagName('INPUT')[3].onclick=function() {	disabilitaAltri(clone.getElementsByTagName('INPUT')[3])};
				clone.getElementsByTagName('INPUT')[3].value=''; 
				clone.getElementsByTagName('INPUT')[4].checked = '';
				clone.getElementsByTagName('INPUT')[4].name = 'field4_'+arrayValue[i];
				clone.getElementsByTagName('INPUT')[4].id = clone.getElementsByTagName('INPUT')[4].name ;
				clone.getElementsByTagName('INPUT')[5].checked = '';
				clone.getElementsByTagName('INPUT')[5].name = 'field5_'+arrayValue[i];
				clone.getElementsByTagName('INPUT')[5].id = clone.getElementsByTagName('INPUT')[5].name ;
				clone.getElementsByTagName('INPUT')[6].checked = '';
				clone.getElementsByTagName('INPUT')[6].name = 'field6_'+arrayValue[i];
				clone.getElementsByTagName('INPUT')[6].id = clone.getElementsByTagName('INPUT')[6].name ;
				clone.getElementsByTagName('INPUT')[7].checked = '';
				clone.getElementsByTagName('INPUT')[7].value = '';
				clone.getElementsByTagName('INPUT')[7].name = 'field7_'+arrayValue[i];
				clone.getElementsByTagName('INPUT')[7].id = clone.getElementsByTagName('INPUT')[7].name ;
				clone.getElementsByTagName('TEXTAREA')[0].name = 'field8_'+arrayValue[i];
				clone.getElementsByTagName('INPUT')[10].value = arrayTipi[i];
				clone.getElementsByTagName('INPUT')[10].name = 'field9_'+arrayValue[i];
				clone.getElementsByTagName('INPUT')[10].id = clone.getElementsByTagName('INPUT')[10].name ;

				clone.getElementsByTagName('INPUT')[8].checked = '';
				clone.getElementsByTagName('INPUT')[8].value = '';
				clone.getElementsByTagName('INPUT')[8].name = 'field10_'+arrayValue[i];
				clone.getElementsByTagName('INPUT')[8].id = clone.getElementsByTagName('INPUT')[8].name ;

				clone.getElementsByTagName('INPUT')[9].checked = '';
				clone.getElementsByTagName('INPUT')[9].value = '';
				clone.getElementsByTagName('INPUT')[9].name = 'field11_'+arrayValue[i];
				clone.getElementsByTagName('INPUT')[9].id = clone.getElementsByTagName('INPUT')[9].name ;

				
				clone.getElementsByTagName('LABEL')[0].innerHTML =arrayDescrizioni[i] ;
				clone.style.display="";
				clone.id = "riga_acque_di_rete"+i;
				clonato.parentNode.appendChild(clone);
				
				if (isPianoAcqueNoRadio){
					
					var field4 = opener.document.getElementById("field4_"+arrayValue[i]);
					var field5 = opener.document.getElementById("field5_"+arrayValue[i]);
					var field6 = opener.document.getElementById("field6_"+arrayValue[i]);
					var field7 = opener.document.getElementById("field7_"+arrayValue[i]);
					var field10 = opener.document.getElementById("field10_"+arrayValue[i]);
					var field11 = opener.document.getElementById("field11_"+arrayValue[i]);
					
					if (field4!=null){
						field4.disabled=false;
						field5.disabled=false;
						field6.disabled=false;
						field7.disabled=false;
						field11.disabled=false;
						
						field10.disabled = true;
						field10.checked = false;
						}
					
				}
				if (isPianoAcqueRadio){
					var field4 = opener.document.getElementById("field4_"+arrayValue[i]);
					var field5 = opener.document.getElementById("field5_"+arrayValue[i]);
					var field6 = opener.document.getElementById("field6_"+arrayValue[i]);
					var field7 = opener.document.getElementById("field7_"+arrayValue[i]);
					var field10 = opener.document.getElementById("field10_"+arrayValue[i]);
					var field11 = opener.document.getElementById("field11_"+arrayValue[i]);
					
					if (field10!=null){
						
						field4.checked=false;
						field5.checked=false;
						field6.checked=false;
						field7.checked=false;
						field11.checked=false;
						
						field4.disabled=true;
						field5.disabled=true;
						field6.disabled=true;
						field7.disabled=true;
						field11.disabled=true;

						field10.disabled=false;
						field10.checked=true;
						field10.onclick= function() { return false; }
						
					}
				}
				
				
				
				
				
				
				//clone.getElementsByTagName('INPUT')[3].onclick= "javascript: disabilitaAltri("+clone.getElementsByTagName('INPUT')[3]+","+arrayValue[i]+")";

					/*clone.getElementsByTagName('INPUT')[4].onclick=function() {var tmp = arrayValue[i] ; disabilitaAltri(clone.getElementsByTagName('INPUT')[4],tmp)};
					
					clone.getElementsByTagName('INPUT')[5].onclick=function() {var tmp = arrayValue[i] ; disabilitaAltri(clone.getElementsByTagName('INPUT')[5],tmp)};
					
					clone.getElementsByTagName('INPUT')[6].onclick=function() {var tmp = arrayValue[i] ;disabilitaAltri(clone.getElementsByTagName('INPUT')[6],tmp)};
					clonato.parentNode.appendChild(clone);*/
					
				
				indice++;
		 		
				
				
			}
		}
		
		//AL TERMINE AGGIORNO GLI INDICI DI RIGA DELLA TABELLA POICHE' TRA CLONE E REMOVE NON SONO PIU' CONSECUTIVI
		aggiornaIndiciDiRigaAcque(opener);
		
		window.close();
}


  function presenteInArray(val, array){
	  for (var i=0; i<array.length; i++)
		  if (val == array[i])
			  return i;
	  return -1;
	  
	  
  }
  
  function aggiornaIndiciDiRigaAcque(opener){
	  var indiceNew = 0;
	  var table = opener.document.getElementById("tblClone");
	  for (var i = 3, row; row = table.rows[i]; i++) {
		  var indiceOld = estraiIdAcque(row.innerHTML);
		  var riga = opener.document.getElementById('riga_acque_di_rete'+indiceOld);
		  riga.id = 'riga_acque_di_rete'+indiceNew; 
		  var pdp = opener.document.getElementById('pdp'+indiceOld);
		  pdp.id = 'pdp'+indiceNew;
		  indiceNew++; 
	     }  
	  }
	
   function estraiIdAcque(testo){
	  var id =testo.substring(testo.indexOf("id=\"pdp")+5,testo.length);
	  id = id.substring(2, id.indexOf("\""));
	  return id;
  }
  
   /*FINE SCRIPT ACQUE DI RETE*/
   
  function addAllegato(nomeFile,idDocumento,folderid,versione,estensione){
	  	var maxElementi = 100;
	  	var elementi;
	  	var elementoClone;
	  	var tableClonata;
	  	var tabella;
	  	elementi = opener.document.getElementById('elementi');
	  	elementi.value=parseInt(elementi.value)+1;
	  	size = opener.document.getElementById('size');
	  	size.value=parseInt(size.value)+1;
	  	
	  	var clonato = opener.document.getElementById('row');
	  	//var clonato2 = opener.document.getElementById('col');
	  	clone=clonato.cloneNode(true);
	  	tabella = opener.document.getElementById('tab');
	  	
	  	/*Imposto gli id dei nuovi campi*/
	  	/*In questo caso 3 input di tipo text presi in ordine*/
	  	
	  	clone.getElementsByTagName('INPUT')[0].id = "allegato_"+elementi.value;
		clone.getElementsByTagName('INPUT')[0].value = idDocumento;
		clone.getElementsByTagName('INPUT')[0].name = "allegato_"+elementi.value;
	  	clone.getElementsByTagName('INPUT')[1].id = "title"+elementi.value;
	  	clone.getElementsByTagName('INPUT')[1].value =nomeFile;
	  	clone.getElementsByTagName('INPUT')[1].name = "title_"+elementi.value;
	  	
		clone.getElementsByTagName('INPUT')[2].value =folderid;
	  	clone.getElementsByTagName('INPUT')[2].name = "folderid_"+elementi.value;
	  	
		clone.getElementsByTagName('INPUT')[3].value =estensione;
	  	clone.getElementsByTagName('INPUT')[3].name = "estensione_"+elementi.value;
	  	
		clone.getElementsByTagName('INPUT')[4].value =versione;
	  	clone.getElementsByTagName('INPUT')[4].name = "versione_"+elementi.value;
	  	
	  	
		clone.getElementsByTagName('LABEL')[0].id = "label"+elementi.value;
		clone.getElementsByTagName('LABEL')[0].innerHTML = "Allegato "+elementi.value+" - "+nomeFile+""+estensione;
	  	
	  	clone.id = "col_"+elementi.value;
	  	clone.getElementsByTagName('a')[0].id="collegamento_"+elementi.value;
		//clone.getElementsByTagName('a')[0].value = "allegato2";
	  	clone.getElementsByTagName('a')[0].href ="DocumentStoreManagementFiles.do?command=Download&documentStoreId="+idDocumento+"&fid="+folderid+"&ver="+versione; 
	  	
	  	//clone.getElementsByTagName('INPUT')[0].name = clone.getElementsByTagName('INPUT')[0].id;
	  	//clone.getElementsByTagName('INPUT')[1].id = "targa"+elementi.value;
	  	//clone.getElementsByTagName('INPUT')[1].name = clone.getElementsByTagName('INPUT')[0].id;

	  	/*Imposto l'id del nuovo nodo*/
	  	//clone.id="autoveicolo_"+elementi.value;

	  	/*Lo rendo visibile*/
	  	clone.style.display="block";
	  	//tabella.style.display="block";
	  	
	  	/*Aggancio il nodo*/
	  	clonato.parentNode.appendChild(clone);

	  	/*Lo rendo visibile*/
	  	//clone.style.display="block";
	  	//clonato.style.display="block";

	  	/*Controllo sul numero di elementi*/
	  	//if (elementi.value>parseInt(maxElementi)-1){
	  		//document.getElementById('addAutoveicolo').style.display="none";
	  	//}
	  	
	  	window.close();
	  }
  
  

  function addAllegatoDocumentale(nomeFile,idDocumento,estensione){
	  	var maxElementi = 100;
	  	var elementi;
	  	var elementoClone;
	  	var tableClonata;
	  	var tabella;
	  	elementi = opener.document.getElementById('elementi');
	  	elementi.value=parseInt(elementi.value)+1;
	  	size = opener.document.getElementById('size');
	  	size.value=parseInt(size.value)+1;
	  	
	  	var clonato = opener.document.getElementById('row');
	  	//var clonato2 = opener.document.getElementById('col');
	  	clone=clonato.cloneNode(true);
	  	tabella = opener.document.getElementById('tab');
	  	
	  	/*Imposto gli id dei nuovi campi*/
	  	/*In questo caso 3 input di tipo text presi in ordine*/
	  	
	  	clone.getElementsByTagName('INPUT')[0].id = "allegato_"+elementi.value;
		clone.getElementsByTagName('INPUT')[0].value = idDocumento;
		clone.getElementsByTagName('INPUT')[0].name = "allegato_"+elementi.value;
	  	clone.getElementsByTagName('INPUT')[1].id = "title"+elementi.value;
	  	clone.getElementsByTagName('INPUT')[1].value =nomeFile;
	  	clone.getElementsByTagName('INPUT')[1].name = "title_"+elementi.value;
	  			
		clone.getElementsByTagName('INPUT')[3].value =estensione;
	  	clone.getElementsByTagName('INPUT')[3].name = "estensione_"+elementi.value;
	  	
			  	
		clone.getElementsByTagName('LABEL')[0].id = "label"+elementi.value;
		clone.getElementsByTagName('LABEL')[0].innerHTML = "Allegato "+elementi.value+" - "+nomeFile+" - "+estensione;
	  	
	  	clone.id = "col_"+elementi.value;
	  	clone.getElementsByTagName('a')[0].id="collegamento_"+elementi.value;
		//clone.getElementsByTagName('a')[0].value = "allegato2";
	  	clone.getElementsByTagName('a')[0].href ="GestioneBacheca.do?command=DownloadPDF&codDocumento="+idDocumento+"&nomeDocumento="+nomeFile+"&tipoDocumento="+estensione; 
	  	
	  	//clone.getElementsByTagName('INPUT')[0].name = clone.getElementsByTagName('INPUT')[0].id;
	  	//clone.getElementsByTagName('INPUT')[1].id = "targa"+elementi.value;
	  	//clone.getElementsByTagName('INPUT')[1].name = clone.getElementsByTagName('INPUT')[0].id;

	  	/*Imposto l'id del nuovo nodo*/
	  	//clone.id="autoveicolo_"+elementi.value;

	  	/*Lo rendo visibile*/
	  	clone.style.display="block";
	  	//tabella.style.display="block";
	  	
	  	/*Aggancio il nodo*/
	  	clonato.parentNode.appendChild(clone);

	  	/*Lo rendo visibile*/
	  	//clone.style.display="block";
	  	//clonato.style.display="block";

	  	/*Controllo sul numero di elementi*/
	  	//if (elementi.value>parseInt(maxElementi)-1){
	  		//document.getElementById('addAutoveicolo').style.display="none";
	  	//}
	  	
	  	window.close();
	  }
  
  
  function getRisorsedaDpat(idqualifica)
  {
	  /*var id_unita_operative= '' ;
	  
	  NucleoIspettivo.getcomponentiNucleoIspettivo(idqualifica,document.getElementById('siteId').value,{async:false,callback:popolaNuvleoIspettivo});*/
	  popolaNuvleoIspettivo();
  }
  
  function popolaNuvleoIspettivo()
  {
	  
	  if(document.getElementById('nucleo_ispettivo_'+indiceNucleoIspettivo).value != '-1')
		  {
//	  if (arrayNominativi.length == 0 )
//	{
//		  document.getElementById("Utente_"+indiceNucleoIspettivo).style.display="block";
//		  document.getElementById("Utente_"+indiceNucleoIspettivo).value="";
//		  document.getElementById("risorse_"+indiceNucleoIspettivo).style.display="none";
//		  document.getElementById("link_"+indiceNucleoIspettivo).style.display="none";
//		  document.getElementById("risorse_"+indiceNucleoIspettivo).value="-1";
//		  document.getElementById('componenteid_'+(indiceNucleoIspettivo)).value="-1";
//		 
//		  
//		  
//		  
//	}
//	  else
//		  {
		  
		  document.getElementById("link_"+indiceNucleoIspettivo).style.display="block";

	
	  document.getElementById("Utente_"+indiceNucleoIspettivo).style.display="none";
	  document.getElementById("Utente_"+indiceNucleoIspettivo).value="";
	  document.getElementById("risorse_"+indiceNucleoIspettivo).style.display="block";
	  
	  
	//	  }
		  }
	  else
		  {
		  document.getElementById("Utente_"+indiceNucleoIspettivo).style.display="none";
		  document.getElementById("risorse_"+indiceNucleoIspettivo).style.display="none";
		  
		  document.getElementById("Utente_"+indiceNucleoIspettivo).value="";
		  document.getElementById("risorse_"+indiceNucleoIspettivo).value="-1";
		  
		  }
	  document.getElementById('componenteid_'+(indiceNucleoIspettivo)).style.display="none";
	  
	  
  }
  
//  function removeOptions(selectbox)
//  {
//      var j;
//      
//      for(j=selectbox.options.length-1;j>=0;j--)
//      {
//          selectbox.remove(j);
//      }
//      
//     
//  }
  
  var indiceNucleoIspettivo ; 
  var defValNucleo = '' ;
  function prova(val)
	{

	  var verifica = true ;
	  idnucleo = 1 ;

	  
	  while(document.getElementById('nucleo_ispettivo_'+idnucleo)!=null)
	  {
		  
		  if (idnucleo!=val)
		  {
			  
			  if ( (document.getElementById('risorse_'+idnucleo).value=='-1' && document.getElementById('Utente_'+idnucleo).value==''))
				  {
				  		document.getElementById('nucleo_ispettivo_'+val).value=document.getElementById('oldValueCombo'+val).value;
				  		
				  		verifica=false;
				  		alert("Attenzione Prima di effettuare cambiamenti completare i dati del nucleo ispettivo alla riga "+idnucleo);
				  		break;
				  
				  }
		  }
		  
		  idnucleo ++ ;
	  }
	  
	  if (verifica ==true)
		  {
	  indiceNucleoIspettivo = val ;

	  document.getElementById('componenteid_'+val).value='';
	  document.getElementById('risorse_'+val).value='-1';

valore=document.getElementById('nucleo_ispettivo_'+val).value;
getRisorsedaDpat(valore);
elementi = val;
		  }
	}
  
  function gestisciNucleo(val)
	{

	  var verifica = true ;
	  idnucleo = 1 ;

	  
	  while(document.getElementById('nucleo_ispettivo_'+idnucleo)!=null)
	  {
		  
		  if (idnucleo!=val)
		  {
			  
			  if ( (document.getElementById('risorse_'+idnucleo).value=='-1' && document.getElementById('Utente_'+idnucleo).value==''))
				  {
				  		document.getElementById('nucleo_ispettivo_'+val).value=document.getElementById('oldValueCombo'+val).value;
				  		
				  		verifica=false;
				  		alert("Attenzione Prima di effettuare cambiamenti completare i dati del nucleo ispettivo alla riga "+idnucleo);
				  		break;
				  
				  }
		  }
		  
		  idnucleo ++ ;
	  }
	  
	  if (verifica ==true)
		  {
	  indiceNucleoIspettivo = val ;

	  document.getElementById('componenteid_'+val).value='';
	  document.getElementById('risorse_'+val).value='-1';

valore=document.getElementById('nucleo_ispettivo_'+val).value;
getRisorsedaDpat(valore);
elementi = val;
		  }
	}
  
  
  
  
  
  
  function selezionaComponenteNucleo(val)
	{

	  indiceNucleoIspettivo = val ;

valore=document.getElementById('nucleo_ispettivo_'+val).value;
getRisorsedaDpat(valore);
elementi = val;

	}
  
 
  
  function popolaComponentiNucleo(val,defVal)
	{

	  indiceNucleoIspettivo = val ;
	  defValNucleo = defVal ;
valore=document.getElementById('nucleo_ispettivo_'+val).value;
fieldSelect = document.getElementById('risorse_'+val);

getRisorsedaDpat(valore);


elementi = val;



			
//			document.getElementById("Tpal_"+elementi).value = '-1' ;
//			document.getElementById("Veterinari_"+elementi).value = '-1' ;
//			document.getElementById("Utente_"+elementi).value = '-1' ;
//			document.getElementById("Ref_"+elementi).value = '-1' ;
//			document.getElementById("Amm_"+elementi).value = '-1' ;
//			document.getElementById("criuv_"+elementi).value = '-1' ;
//			document.getElementById('componenteid_'+elementi).value = '' ;
	}

  function inizializzaComponentiNucleo(campo,defVal)
	{
	  var index = -1;
	  var val = '';
	  var select =	 document.getElementById(campo);
	  
	  for (var i = 0; i<select.length; i++){
		  if (select[i].value == defVal){
			  index = i;
			  val= select[i].value; 
			  break;
		  }
	  }
	  select.selectedIndex = index;
	  select.value = val;
	  	}
  
  
  function clona(docum){
	 
	  	var maxElementi = 10;
	  	var elementi;
	  	var elementoClone;
	  	var tableClonata;
	  	var tabella;
	  	var selezionato;
	  	var x;
	  	elementi = docum.getElementById('elementi');
	  	if (elementi.value < maxElementi)
	  	{
		
	  	if (docum.getElementById('nucleo_ispettivo_'+elementi.value).value != '-1')
	  	{
	  	
	  		
	  	elementi.value=parseInt(elementi.value)+1;
	  	size = docum.getElementById('size');
	  	size.value=parseInt(size.value)+1;
	  	var primo_elemento = docum.getElementById('nucleo_ispettivo_1');
	  	var indice = parseInt(elementi.value) - 1;
	  	
	  	
	  	x = docum.getElementById('lcso_patologia_'+String(indice));
	  	if(primo_elemento!=null && x==null){
	  		selezionato = docum.getElementById('nucleo_ispettivo_1').selectedIndex;
	  	}else if(primo_elemento==null && x!=null){
	  		selezionato = x.selectedIndex;
	  	}
	  	var clonato = docum.getElementById('nucleo_ispettivo');
	  	
	  	
	  	/*clona riga vuota*/
	  	clone=clonato.cloneNode(true);
		clone.getElementsByTagName('SELECT')[0].name = "nucleo_ispettivo_"+elementi.value;
	  	clone.getElementsByTagName('SELECT')[0].id = "nucleo_ispettivo_"+String(indice+1);
	  	clone.getElementsByTagName('SELECT')[0].value = '-1';
	 
	  	clone.getElementsByTagName('SELECT')[0].value = '-1';

		clone.getElementsByTagName('SELECT')[0].onchange= function () 
		{
			prova(parseInt(indice)+1);
			document.getElementById('oldValueCombo'+elementi.value).value=clone.getElementsByTagName('SELECT')[0].value;
		}
		
		
		
	  	
	  	
	  	clone.getElementsByTagName('INPUT')[1].name = "Utente_"+elementi.value;
	  	clone.getElementsByTagName('INPUT')[1].id = "Utente_"+elementi.value;
	  	clone.getElementsByTagName('INPUT')[1].style.display="none";
	  	clone.getElementsByTagName('INPUT')[1].value = '';
		clone.getElementsByTagName('INPUT')[0].name = "componenteid_"+elementi.value;
	  	clone.getElementsByTagName('INPUT')[0].id = "componenteid_"+elementi.value;
	  	clone.getElementsByTagName('INPUT')[0].value = '';
	  	clone.getElementsByTagName('INPUT')[0].style.display="none";
	  	
	  	clone.getElementsByTagName('INPUT')[2].name = "risorse_"+elementi.value;
	  	clone.getElementsByTagName('INPUT')[2].id = "risorse_"+elementi.value;
	  	clone.getElementsByTagName('INPUT')[2].value = '-1';
	  	
		clone.getElementsByTagName('INPUT')[3].id = "oldValueCombo"+elementi.value;
	  	clone.getElementsByTagName('INPUT')[3].value = '-1';
	  	
	  	
		clone.getElementsByTagName('A')[0].id="link_"+elementi.value;
		clone.getElementsByTagName('A')[0].href="#";
	  	clone.getElementsByTagName('A')[0].onclick= function () 
		{
	  		
	  		mostraUtenti(parseInt(indice)+1);
		}
	  	
	  	clone.getElementsByTagName('div')[0].id="operazione_"+elementi.value;
	  	clone.getElementsByTagName('div')[0].innerHTML="<a  href='javascript:eliminaComponente("+elementi.value+");'>Elimina</a>";
	  	
	  	clone.getElementsByTagName('SELECT')[0].onfocus= function () 
		{
	  		
			document.getElementById('oldValueCombo'+elementi.value).value=clone.getElementsByTagName('SELECT')[0].value;

			
		}
	  	
	  	
	  	
	  	/*Lo rendo visibile*/
	
	  	clone.id = "nucleoispettivo_"+elementi.value;
	  	/*Aggancio il nodo*/
	  	clonato.parentNode.appendChild(clone);

	  	/*Lo rendo visibile*/
	  
	  	clone.style.visibility="visible";
	  	
	  	}
	  	
	  	}
	  }
	  
	  
	  function resetElementiNucleoIspettivo(numElementi)
	  {
	  	document.getElementById('size').value = (parseInt(numElementi)+1);
	  	document.getElementById('elementi').value = (parseInt(numElementi)+1);
	  }
	  

  

  function clonaSoaCUAllevamenti(siteid){
	  	var maxElementi = 100;
	  	var elementi;
	  	var elementoClone;
	  	var tableClonata;
	  	var tabella;
	  	elementi = opener.document.getElementById('elementi1');
	  	elementi.value=parseInt(elementi.value)+1;
	  	size = opener.document.getElementById('size1');
	  	size.value=parseInt(size.value)+1;
	  	
	  	var clonato = opener.document.getElementById('row');
	  	clone=clonato.cloneNode(true);
	  	tabella = opener.document.getElementById('tab');
	  	
	  	/*Imposto gli id dei nuovi campi*/
	  	/*In questo caso 3 input di tipo text presi in ordine*/
	  	
	  	clone.getElementsByTagName('INPUT')[0].id = "org"+"_"+elementi.value;
	  	clone.getElementsByTagName('INPUT')[1].id = "indirizzo"+"_"+elementi.value;
	  	clone.getElementsByTagName('INPUT')[2].id = "orgId"+"_"+elementi.value;
	  	clone.id = "row"+"_"+elementi.value;
	  	clone.getElementsByTagName('a')[0].id="elimina"+"_"+elementi.value;
	  	clone.getElementsByTagName('a')[0].href ="javascript:popLookupSelectorCuSoaAllevaElimina("+siteid+","+elementi.value+")" 
	  	
	  	//clone.getElementsByTagName('INPUT')[0].name = clone.getElementsByTagName('INPUT')[0].id;
	  	//clone.getElementsByTagName('INPUT')[1].id = "targa"+elementi.value;
	  	//clone.getElementsByTagName('INPUT')[1].name = clone.getElementsByTagName('INPUT')[0].id;

	  	/*Imposto l'id del nuovo nodo*/
	  	//clone.id="autoveicolo_"+elementi.value;

	  	/*Lo rendo visibile*/
	  	clone.style.visibility = "visible";
	  	
	  	
	  	/*Aggancio il nodo*/
	  	clonato.parentNode.appendChild(clone);

	  	/*Lo rendo visibile*/
	  	clone.style.visibility = "visible";
	  	

	  	/*Controllo sul numero di elementi*/
	  	//if (elementi.value>parseInt(maxElementi)-1){
	  		//document.getElementById('addAutoveicolo').style.display="none";
	  	//}
	  }

  
  function clonaSoaCUAllevamentiFuoriRegione(siteid){
  
	  	var maxElementi = 100;
	  	var elementi;
	  	var elementoClone;
	  	var tableClonata;
	  	var tabella;
	  	elementi = opener.document.getElementById('elementi1');
	  	elementi.value=parseInt(elementi.value)+1;
	  	size = opener.document.getElementById('size1');
	  	size.value=parseInt(size.value)+1;
	  	
	  	var clonato = opener.document.getElementById('row_fregione');
	  	clone=clonato.cloneNode(true);
	  	tabella = opener.document.getElementById('tab');
	  	
	  	/*Imposto gli id dei nuovi campi*/
	  	/*In questo caso 3 input di tipo text presi in ordine*/
	  	
	  	clone.getElementsByTagName('INPUT')[0].id = "org"+"_"+elementi.value;
	  	clone.getElementsByTagName('INPUT')[1].id = "indirizzo"+"_"+elementi.value;
	  	clone.getElementsByTagName('INPUT')[2].id = "orgId"+"_"+elementi.value;
	  	clone.id = "row"+"_"+elementi.value;
	  	clone.getElementsByTagName('a')[0].id="elimina"+"_"+elementi.value;
	  	clone.getElementsByTagName('a')[0].href ="javascript:popLookupSelectorCuSoaAllevaElimina("+siteid+","+elementi.value+")" 
	  	
	  	//clone.getElementsByTagName('INPUT')[0].name = clone.getElementsByTagName('INPUT')[0].id;
	  	//clone.getElementsByTagName('INPUT')[1].id = "targa"+elementi.value;
	  	//clone.getElementsByTagName('INPUT')[1].name = clone.getElementsByTagName('INPUT')[0].id;

	  	/*Imposto l'id del nuovo nodo*/
	  	//clone.id="autoveicolo_"+elementi.value;

	  	/*Lo rendo visibile*/
	  	clone.style.visibility = "visible";
	  	
	  	/*Aggancio il nodo*/
	  	clonato.parentNode.appendChild(clone);

	  	/*Lo rendo visibile*/
	  	clone.style.visibility = "visible";
	  	

	  	/*Controllo sul numero di elementi*/
	  	//if (elementi.value>parseInt(maxElementi)-1){
	  		//document.getElementById('addAutoveicolo').style.display="none";
	  	//}
	  }

  function setParentValue2_allerta_imprese(displayFieldId, fieldValue,siteid,indirizzo) {
	  	clonaRiga(siteid);
	  	index = opener.document.getElementById("elementi_"+siteid).value;
	 
	    opener.document.getElementById("org_"+siteid+"_"+index).value = fieldValue;
	    opener.document.getElementById("indirizzo_"+siteid+"_"+index).value = indirizzo;
	  	
	    //opener.document.getElementById("ticketid").value=ticketid;
	    window.close();
	  }
  
  
 function clonaRiga(siteId)
 {
	 	var maxElementi = 100;
	  	var elementi;
	  	var elementoClone;
	  	var tableClonata;
	  	var tabella;
	  	elementi = opener.document.getElementById('elementi_'+siteId);
	  	elementi.value=parseInt(elementi.value)+1;
	  	size = opener.document.getElementById('size_'+siteId);
	  	size.value=parseInt(size.value)+1;
	  	
	  	var clonato = opener.document.getElementById('row_'+siteId);
	  	clone=clonato.cloneNode(true);
	  	clone.getElementsByTagName('INPUT')[0].id = "org_"+siteId+"_"+elementi.value;
	  	clone.getElementsByTagName('INPUT')[1].id = "indirizzo_"+siteId+"_"+elementi.value;
	  	pos = size.value ;
	  	clone.getElementsByTagName('A')[0].href='javascript:popLookupSelectorAllerteImpreseElimina('+siteId+','+pos+')';
	  	/*Lo rendo visibile*/
	  	clone.style.display = "";
	  	clone.id = "row_"+siteId+"_"+size.value;
	  	/*Aggancio il nodo*/
	  	clonato.parentNode.appendChild(clone);

	  	
	 
 }
  
  function setParentValue2_utentiGisa(username,userid,ruolo) {
	 
	  
	 
	    opener.document.getElementById("username").value = username;
	    opener.document.getElementById("userid").value = userid;
	    opener.document.getElementById("ruolo").value = ruolo;
	  	
	    //opener.document.getElementById("ticketid").value=ticketid;
	    window.close();
	  }
  
  function setParentValue2_cusoa_allevamenti(displayFieldId, fieldValue,siteid,indirizzo,orgid) {
	  clonaSoaCUAllevamenti(siteid);
	  	index = opener.document.getElementById("elementi1").value;

	    opener.document.getElementById("org"+"_"+index).value = fieldValue;
	    opener.document.getElementById("indirizzo"+"_"+index).value = indirizzo;
	    opener.document.getElementById("orgId"+"_"+index).value = orgid;
	  	
	    //opener.document.getElementById("ticketid").value=ticketid;
	    window.close();
	  }
  
  
  function setParentValue2_cusoaFuoriRegione_allevamenti(displayFieldId, fieldValue,siteid,indirizzo,orgid) {
	  	clonaSoaCUAllevamentiFuoriRegione(siteid);
	  	index = opener.document.getElementById("elementi1").value;
	  
	 
	    opener.document.getElementById("org"+"_"+index).value = 'Inerisci denominazione soa fuori regione';
	    opener.document.getElementById("indirizzo"+"_"+index).value = 'Inserisci indirizzo soa fuori regione';
	    opener.document.getElementById("orgId"+"_"+index).value = orgid;
	  	
	    //opener.document.getElementById("ticketid").value=ticketid;
	    window.close();
	  }
  
  function setParentValue2_allerta(displayFieldId, fieldValue, displayFieldId2, fieldValue2,ticketid) {
	    opener.document.getElementById(displayFieldId).value = fieldValue;
	    opener.document.getElementById("ticketid").value=ticketid;
	    window.close();
	  }
  
  function setParentValue2_allerta2(displayFieldId, fieldValue, displayFieldId2, fieldValue2,ticketid,unitaMisura) {
	    opener.document.getElementById(displayFieldId).value = fieldValue;
	    opener.document.getElementById("ticketid").value=ticketid;
	    if(opener.document.getElementById("misura")!=null)
	    opener.document.getElementById("misura").innerHTML=unitaMisura;
	    if(opener.document.getElementById("misura1")!=null)
	    opener.document.getElementById("misura1").innerHTML=unitaMisura;
	    if(opener.document.getElementById("unitaMisura")!=null)
	    opener.document.getElementById("unitaMisura").value=unitaMisura;
	   
	    window.close();
	  }
  
  function setParentValue3_allerta3(displayFieldId, fieldValue, displayFieldId2, fieldValue2,ticketid,unitaMisura, idLdd, nomeFornitore, dataLista,dataChiusura) {
	    opener.document.getElementById(displayFieldId).value = fieldValue;
	    opener.document.getElementById("ticketid").value=ticketid;
	    if(opener.document.getElementById("misura")!=null)
	    opener.document.getElementById("misura").innerHTML=unitaMisura;
	    if(opener.document.getElementById("misura1")!=null)
	    opener.document.getElementById("misura1").innerHTML=unitaMisura;
	    if(opener.document.getElementById("unitaMisura")!=null)
	    opener.document.getElementById("unitaMisura").value=unitaMisura;
	    if(opener.document.getElementById("dataLista")!=null)
		    opener.document.getElementById("dataLista").value=dataLista;
	    if(opener.document.getElementById("idLdd")!=null)
		    opener.document.getElementById("idLdd").value=idLdd;
	    if(opener.document.getElementById("nomeFornitore")!=null)
		    opener.document.getElementById("nomeFornitore").value=nomeFornitore;
	    if(opener.document.getElementById("dataChiusura")!=null)
		    opener.document.getElementById("dataChiusura").value=dataChiusura;
	   
	    window.close();
	  }
  
  function setParentBuffer(displayFieldId, fieldValue, displayFieldId2, fieldValue2,id_buffer) {
	    opener.document.getElementById(displayFieldId).value = fieldValue; 
	    opener.document.getElementById("id_buffer").value = id_buffer; 
	    window.close();
	  }
  
  
  function setParentValue2_dia(field1,field3,field4,field5,field6,field7,field8,field9,field10,field11,field12,field13,field14,field15,field16,field17,field18,field19,field20,field21,field22,field23,field24,field25,field26,field27,field28,field29,field30,field31,
		  field32,field33,field34,field35,field36,field37,field38,field39,field40,field41,field42,field43,field44,field45,field46,field47,field48,field49,field50,field51,field52,field53,field54,field55,field56,field57,field58,field59,field60,field61,field62,field63,
	field64,field65,field66	  
  ) {
	    opener.document.addAccount.name.value = field1;
	   
	    opener.document.addAccount.dunsType.value = field3;
	    opener.document.addAccount.siteId.value = field4;
	    opener.document.addAccount.partitaIva.value = field5;
	   if(field6!="")
	    opener.document.addAccount.codiceFiscale.value = field6;
	   /*if(field7!="")
	   opener.document.addAccount.codiceFiscaleCorrentista.value = field7;
	   if(field8!="") 
	   opener.document.addAccount.alertText.value = field8;
	    */
	    opener.document.getElementById("div_codice2").style.display="none";
	    opener.document.getElementById("div_codice3").style.display="none";
	    opener.document.getElementById("div_codice4").style.display="none";
	    opener.document.getElementById("div_codice5").style.display="none";
	    opener.document.getElementById("div_codice6").style.display="none";
	    opener.document.getElementById("div_codice7").style.display="none";
	    opener.document.getElementById("div_codice8").style.display="none";
	    opener.document.getElementById("div_codice9").style.display="none";
	    
	    
	   /*
	    if(field9!=""){
	    opener.document.addAccount.codice1.value = field9;
	    opener.document.addAccount.cod1.value = field10;
	    opener.document.getElementById("div_codice1").style.display="";
	    
	    if(field11!=""){
		    opener.document.addAccount.codice2.value = field11;
		    opener.document.addAccount.cod2.value = field12;
		    opener.document.getElementById("div_codice2").style.display="";
		    if(field13!=""){
			    opener.document.addAccount.codice3.value = field13;
			    opener.document.addAccount.cod3.value = field14;
			    opener.document.getElementById("div_codice3").style.display="";
			 
			    if(field15!=""){
				    opener.document.addAccount.codice4.value = field15;
				    opener.document.addAccount.cod4.value = field16;
				    opener.document.getElementById("div_codice4").style.display="";
				    
				    if(field17!=""){
					    opener.document.addAccount.codice5.value = field17;
					    opener.document.addAccount.cod5.value = field18;
					    opener.document.getElementById("div_codice5").style.display="";
					    if(field19!=""){
						    opener.document.addAccount.codice6.value = field19;
						    opener.document.addAccount.cod6.value = field20;
						    opener.document.getElementById("div_codice6").style.display="";
						    if(field21!=""){
							    opener.document.addAccount.codice7.value = field21;
							    opener.document.addAccount.cod7.value = field22;
							    opener.document.getElementById("div_codice7").style.display="";
							    if(field23!=""){
								    opener.document.addAccount.codice8.value = field23;
								    opener.document.addAccount.cod8.value = field24;
								    opener.document.getElementById("div_codice8").style.display="";
								    if(field25!=""){
									    opener.document.addAccount.codice9.value = field25;
									    opener.document.addAccount.cod9.value = field26;
									    opener.document.getElementById("div_codice9").style.display="";
									    if(field27!=""){
										    opener.document.addAccount.codice10.value = field27;
										    opener.document.addAccount.cod10.value = field28;
										  
										    
										    }
									    }
								    }
							    }
						  
						    }
					    }
				    
				    }
			    }
		    }
	   
	    }
	    */
	  
	    if(field29=="Es. Commerciale") //fissa
	    {
	    	opener.document.getElementById("tipoD").checked=true;
	    	opener.document.getElementById("tipoD2").checked=false;
	    	//updateFormElementsNew(0);
	    }else{
	    	if(field29=="Autoveicolo"){ // mobile
	    	opener.document.getElementById("tipoD").checked=false;
	    	opener.document.getElementById("tipoD2").checked=true;
	    	//updateFormElementsNew(1);
	    	}
	    }
	    opener.document.addAccount.date1.value=field30;
	     
	    opener.document.addAccount.stageId.value=field31;
	    
	   if(field32!="null")
	    opener.document.addAccount.titoloRappresentante.value=field32;
	   if(field33!="null")
	   opener.document.addAccount.codiceFiscaleRappresentante.value=field33;
	   if(field34!="null")
	   opener.document.addAccount.nomeRappresentante.value=field34;
	   if(field35!="null")
	   opener.document.addAccount.cognomeRappresentante.value=field35;	
	   if(field36!="null")
	   opener.document.addAccount.dataNascitaRappresentante.value=field36;
	   if(field37!="null")
	   opener.document.addAccount.luogoNascitaRappresentante.value=field37;
	   if(field38!="null")
	   opener.document.addAccount.emailRappresentante.value=field38;
	   if(field39!="null")
	   opener.document.addAccount.telefonoRappresentante.value=field39;
	   if(field40!="null")
	   opener.document.addAccount.fax.value=field40;
	    
	   if(field41!="null")
	    opener.document.addAccount.address1city.value=field41;
	   if(field42!="null")
	   opener.document.addAccount.address1line1.value=field42;
	   if(field43!="null")
	   opener.document.addAccount.address1line2.value=field43;
	   if(field44!="null")
	   opener.document.addAccount.address1zip.value=field44;
	   if(field45!="null")
	   opener.document.addAccount.address1state.value=field45;
	    
	    
	    if(field29=="Es. Commerciale")
	    {
	    	
	    	 if(field46!="null")
	    	opener.document.addAccount.address2city.value=field46;
	    	 if(field47!="null")
	    	opener.document.addAccount.address2line1.value=field47;    
	    	 if(field48!="null")
	    	opener.document.addAccount.address2zip.value=field48;
	    	 if(field49!="null")
	    	opener.document.addAccount.address2state.value=field49;
	    	 if(field50!="null")
	    	opener.document.addAccount.address2latitude.value=field50
	    	 if(field51!="null")
	    	opener.document.addAccount.address2longitude.value=field51;
	    
	    }else{
	    	 opener.document.addAccount.address2state.value="";
	    	
	    }
	    if(field52!="null")
	    opener.document.addAccount.address3city.value=field52;
	    if(field53!="null")
	    opener.document.addAccount.address3line1.value=field53;
	    if(field54!="null")
	    opener.document.addAccount.address3zip.value=field54;
	    if(field55!="null")
	    opener.document.addAccount.address3state.value=field55;
	    if(field56!="null")
	    opener.document.addAccount.address3latitude.value=field56
	    if(field57!="null")
	    opener.document.addAccount.address3longitude.value=field57;
	    
	    if(field58!="null")
	    opener.document.addAccount.address4city.value=field58;
	    if(field59!="null")
	    opener.document.addAccount.address4line1.value=field59;
	    if(field60!="null")
	    opener.document.addAccount.address4zip.value=field60;
	    if(field61!="null")
	    opener.document.addAccount.address4state.value=field61;
	    if(field62!="null")
	    opener.document.addAccount.address4latitude.value=field62
	    if(field63!="null")
	    opener.document.addAccount.address4longitude.value=field63;
	    if(field64!="null")
	    opener.document.addAccount.TipoStruttura.value=field64;
	    if(field65!="null")
	    opener.document.addAccount.nomeCorrentista.value=field65;
	    if(field66!="null")
	    opener.document.addAccount.contoCorrente.value=field66;
	    
	    
	    
	    
	    
	    
	    window.close();
	  }
  
  
  
  
  function updateFormElementsNew(index) {
	  	
	  
	  	
	  	if(index==1){
	  		opener.document.getElementById("starMobil3").style.display="";
	  		opener.document.getElementById("starMobil4").style.display="";
	  		opener.document.getElementById("starMobil5").style.display="";
	  		opener.document.getElementById("starMobil8").style.display="";
	  		opener.document.getElementById("starMobil9").style.display="";
	  		if(opener.document.getElementById("starMobil10")) opener.document.getElementById("starMobil10").style.display="";
	  	}
	  	else if(index==0){
	  		opener.document.getElementById("starMobil3").style.display="none";
	  		opener.document.getElementById("starMobil4").style.display="none";
	  		opener.document.getElementById("starMobil5").style.display="none";
	  		opener.document.getElementById("starMobil8").style.display="none";
	  		opener.document.getElementById("starMobil9").style.display="none";
	  		if(opener.document.getElementById("starMobil10")) opener.document.getElementById("starMobil10").style.display="none";
	  	
	  	
	  	}
	  	

	  	
	    if (document.getElementById) {
	       elm1 = opener.document.getElementById("tipoVeicolo1"); //Nome
	       elm2 =opener.document.getElementById("targaVeicolo1"); //Cognome
	      /* elm3 = document.getElementById("codiceCont1"); // Nome (Organization)*/
	       elm4 = opener.document.getElementById("tipoStruttura1");
	       elm5 = opener.document.getElementById("addressLine");
	       elm6 = opener.document.getElementById("prov1");
	       elm7 = opener.document.getElementById("labelCap");
	       elm8 = opener.document.getElementById("stateProv1");
	       elm9 = opener.document.getElementById("latitude1");
	       elm10 = opener.document.getElementById("longitude1");
	       
	      if (index == 0) {
	        resetFormElementsNew();
	        
	      
	        
	        elm1.style.color="#cccccc";
	        opener.document.addAccount.tipoVeicolo.style.background = "#cccccc";
	        opener.document.addAccount.tipoVeicolo.value = "";
	        opener.document.addAccount.tipoVeicolo.disabled = true;
	        
	        elm2.style.color="#cccccc";
	        opener.document.addAccount.targaVeicolo.style.background = "#cccccc";
	        opener.document.addAccount.targaVeicolo.value = "";
	        opener.document.addAccount.targaVeicolo.disabled = true;
	    
	    /*    elm3.style.color="#cccccc";
	        document.addAccount.codiceCont.style.background = "#cccccc";
	        document.addAccount.codiceCont.value = "";
	        document.addAccount.codiceCont.disabled = true;*/
	        
	           elm4.style.color="#cccccc";
	           opener.document.getElementById("prov12").disabled = true;
	                
	        elm5.style.color="#cccccc";
	        opener.document.addAccount.addressline1.style.background = "#cccccc";
	        opener.document.addAccount.addressline1.value = "";
	        opener.document.addAccount.addressline1.disabled = true;
	        
	        elm6.style.color="#cccccc";
	        opener.document.getElementById("prov12").disabled = true;
	        opener.document.getElementById("prov12").selectedIndex=0;
	        
	        elm7.style.color="#cccccc";
	        opener.document.addAccount.addresszip.style.background = "#cccccc";
	        opener.document.addAccount.addresszip.value = "";
	        opener.document.addAccount.addresszip.disabled = true;
	        
	        elm8.style.color="#cccccc";
	        
	        elm9.style.color="#cccccc";
	        opener.document.addAccount.address3latitude.style.background = "#cccccc";
	        opener.document.addAccount.address3latitude.value = "";
	        opener.document.addAccount.address3latitude.disabled = true;
	        
	        elm10.style.color="#cccccc";
	        opener.document.addAccount.address3longitude.style.background = "#cccccc";
	        opener.document.addAccount.address3longitude.value = "";
	        opener.document.addAccount.address3longitude.disabled = true;
	        
	        elm4.style.color="#cccccc";
	        opener.document.addAccount.TipoStruttura.style.background = "#cccccc";
	        opener.document.addAccount.TipoStruttura.value = "";
	        opener.document.addAccount.TipoStruttura.disabled = true;
	                
	        
	       
	        opener.document.getElementById("prov12").disabled = false;
	        opener.document.addAccount.check.value = "es";
	        opener.document.addAccount.orgType.value = "11"; //Valore per PROPRIETARIO
	        
	        tipo1 = document.getElementById("tipoD");
	        if(tipo1!=null)
	        tipo1.checked = true;
	        
	        /*document.getElementById("codice1").value = "";
	        document.getElementById("codice2").value = "";
	        document.getElementById("codice3").value = "";
	        document.getElementById("codice4").value = "";
	        document.getElementById("codice5").value = "";
	        document.getElementById("codice6").value = "";
	        document.getElementById("codice7").value = "";
	        document.getElementById("codice8").value = "";
	        document.getElementById("codice9").value = "";
	        document.getElementById("codice10").value = "";*/
	        
	        
	      } else if (index == 1){
	      
	        resetFormElementsNew();
	        
	        opener.document.addAccount.address1type.style.background = "#000000";
	        opener.document.addAccount.address1type.disabled = false;
	        
	       elm5 = opener.document.getElementById("indirizzo1");
	       elm6 = opener.document.getElementById("prov2");
	       elm7 = opener.document.getElementById("cap1");
	       elm8 = opener.document.getElementById("stateProv2");
	       elm9 = opener.document.getElementById("latitude2");
	       elm10 = opener.document.getElementById("longitude2");
	       
	      	 elm5.style.color="#cccccc";
	      	opener.document.addAccount.indirizzo12.style.background = "#cccccc";
	      	opener.document.addAccount.indirizzo12.value = "";
	      	opener.document.addAccount.indirizzo12.disabled = true;
	        
	        elm6.style.color="#cccccc";
	        opener.document.getElementById("prov").disabled = true;
	        opener.document.getElementById("prov").selectedIndex=0;
	        
	        elm7.style.color="#cccccc";
	        opener.document.addAccount.cap.style.background = "#cccccc";
	        opener.document.addAccount.cap.value = "";
	        opener.document.addAccount.cap.disabled = true;
	        
	        elm8.style.color="#cccccc";
	        
	        elm9.style.color="#cccccc";
	        opener.document.addAccount.address2latitude.style.background = "#cccccc";
	        opener.document.addAccount.address2latitude.value = "";
	        opener.document.addAccount.address2latitude.disabled = true;
	        
	        elm10.style.color="#cccccc";
	        opener.document.addAccount.address2longitude.style.background = "#cccccc";
	        opener.document.addAccount.address2longitude.value = "";
	        opener.document.addAccount.address2longitude.disabled = true;
	        
	        
	    	/*elm3.style.color="#cccccc";
	        document.addAccount.codiceCont.style.background = "#cccccc";
	        document.addAccount.codiceCont.value = "";
	        document.addAccount.codiceCont.disabled = true;*/
	        
	        opener.document.getElementById("prov").disabled = false;
	        opener.document.addAccount.check.value = "autoveicolo";
	        opener.document.addAccount.orgType.value = "17"; //Valore per PROPRIETARIO
	        
	      } else if (index==2) {
	      	
	      	resetFormElementsNew();
	        
	        elm1.style.color="#cccccc";
	        opener.document.addAccount.tipoVeicolo.style.background = "#cccccc";
	        opener.document.addAccount.tipoVeicolo.value = "";
	        opener.document.addAccount.tipoVeicolo.disabled = true;
	    
	        elm2.style.color="#cccccc";
	        opener.document.addAccount.targaVeicolo.style.background = "#cccccc";
	        opener.document.addAccount.targaVeicolo.value = "";
	        opener.document.addAccount.targaVeicolo.disabled = true;
	        
	        opener.document.addAccount.check.value = "codiceCont";
	        opener.document.addAccount.orgType.value = "19"; //Valore per sindaco
	        
	      }
	    }
	
	    onLoad = 0;
	  }
  
  
  
  function resetFormElementsNew() {
  	   elm1 = opener.document.getElementById("tipoVeicolo1"); //Nome
  	 opener.document.addAccount.tipoVeicolo.style.background = "#ffffff";
  	opener.document.addAccount.tipoVeicolo.disabled = false;
      elm1.style.color = "#000000";
       
      elm2 = opener.document.getElementById("targaVeicolo1"); //Cognom
      opener.document.addAccount.targaVeicolo.style.background = "#ffffff";
      opener.document.addAccount.targaVeicolo.disabled = false;
      elm2.style.color = "#000000";
      
    /*  elm3 = document.getElementById("codiceCont1"); // Nome (Organization)
      document.addAccount.codiceCont.style.background = "#ffffff";
      document.addAccount.codiceCont.disabled = false;
      elm3.style.color = "#000000";*/
      
     	elm5 = opener.document.getElementById("addressLine"); // Nome (Organization)
     	opener.document.addAccount.addressline1.style.background = "#ffffff";
     	opener.document.addAccount.addressline1.disabled = false;
      	elm5.style.color = "#000000";
      	
      	elm6 = opener.document.getElementById("prov1"); // Nome (Organization)
      	opener.document.getElementById("prov").disabled = true;
      	elm6.style.color = "#000000";
      	
      	elm7 = opener.document.getElementById("labelCap"); // Nome (Organization)
      	opener.document.addAccount.addresszip.style.background = "#ffffff";
      	opener.document.addAccount.addresszip.disabled = false;
      	elm7.style.color = "#000000";
      
       elm8 = opener.document.getElementById("stateProv1"); // Nome (Organization)
      	elm8.style.color = "#000000";
      	
      
      		elm9 = opener.document.getElementById("latitude1"); // Nome (Organization)
      		opener.document.addAccount.address3latitude.style.background = "#ffffff";
      		opener.document.addAccount.address3latitude.disabled = false;
      		elm9.style.color = "#000000";
      	
      	
      		elm10 = opener.document.getElementById("longitude1"); // Nome (Organization)
      		opener.document.addAccount.address3longitude.style.background = "#ffffff";
      		opener.document.addAccount.address3longitude.disabled = false;
      		elm10.style.color = "#000000";
      	
      	
      	elm = opener.document.getElementById("tipoStruttura1"); // Nome (Organization)
      	opener.document.addAccount.TipoStruttura.style.background = "#ffffff";
      	opener.document.addAccount.TipoStruttura.disabled = false;
      	elm.style.color = "#000000";
      	
      elm12 = opener.document.getElementById("indirizzo1");
      opener.document.addAccount.indirizzo12.style.background = "#ffffff";
      opener.document.addAccount.indirizzo12.disabled = false;
      elm12.style.color = "#000000";
      
      elm17 = opener.document.getElementById("prov2");
      opener.document.getElementById("prov12").disabled = true;
      opener.document.getElementById("prov12").selectedIndex = 0;
      elm17.style.color = "#000000";
      
      opener.document.getElementById("prov").selectedIndex = 0;
      
      elm13 = opener.document.getElementById("cap1");
      opener.document.addAccount.cap.style.background = "#ffffff";
      opener.document.addAccount.cap.disabled = false;
      elm13.style.color = "#000000";
      
      elm14 = opener.document.getElementById("stateProv2");
      elm14.style.color = "#000000";
      
      elm15 = opener.document.getElementById("latitude2");
      opener.document.addAccount.address2latitude.style.background = "#ffffff";
      opener.document.addAccount.address2latitude.disabled = false;
      elm15.style.color = "#000000";
      
      elm16 = opener.document.getElementById("longitude2");
      opener.document.addAccount.address2longitude.style.background = "#ffffff";
      opener.document.addAccount.address2longitude.disabled = false;
      elm16.style.color = "#000000";
      
      opener.document.addAccount.address1type.style.background = "#ffffff";
      opener.document.addAccount.address1type.disabled = false;
      opener.document.addAccount.address1type.style.color="#000000"
      	
      
      
 }
  
   function setParentValue2New(displayFieldId2,fieldValue2, fieldName2 ) {
   var x = opener.document.getElementById(displayFieldId2);
   var i = x.length;
   var codice1= fieldName2 ;
    x.options[i] = new Option(codice1,fieldName2);
    //opener.document.forms[0].testselect.options[i] = new Option('new text','new value');
    window.close();
  }
  
   function setParentValue2N(displayFieldId2,fieldValue2, fieldName2 ) {
    opener.document.getElementById(displayFieldId2).value = fieldName2;
    if(displayFieldId2 != "codice10"){
    opener.document.getElementById("div_" + displayFieldId2).style.display = "block";
    }
    window.close();
  }
   function setParentValue3(displayFieldId,fieldValue,displayFieldId2,fieldValue2) {
	    opener.document.getElementById(displayFieldId2).value = fieldValue2;
	    opener.document.getElementById(displayFieldId2).onchange();
	    opener.document.getElementById(displayFieldId).value = fieldValue;
	    if(displayFieldId != "codice10"){
	    opener.document.getElementById("div_" + displayFieldId).style.display = "block";
	    /*opener.document.getElementById("div_" + displayFieldId2).style.display = "block";*/
	    }
	    window.close();
	  }

  function setParentList(selectedIds,selectedValues,listType,displayFieldId, hiddenFieldId, browserId){
    if(selectedValues.length == 0 && listType == "list"){
      opener.deleteOptions(displayFieldId);
		  opener.insertOption("None Selected","-1",displayFieldId);
      return true;
	  }
    var i = 0;
    if(listType == "list"){
      opener.deleteOptions(displayFieldId);
      for(i=0; i < selectedValues.length; i++) {
          opener.insertOption(selectedValues[i],selectedIds[i],displayFieldId);
      }
    }
    else if(listType == "single"){
        opener.document.getElementById(hiddenFieldId).value = selectedIds[i];
        opener.changeDivContent(displayFieldId,selectedValues[i]);
    }
  }

  function SetChecked(val,chkName,thisForm,browser) {
        var frm = document.forms[thisForm];
        var len = document.forms[thisForm].elements.length;
        var i=0;
        for( i=0 ; i<len ; i++) {
                if (frm.elements[i].name.indexOf(chkName)!=-1) {
                  frm.elements[i].checked=val;
                    highlight(frm.elements[i],browser);
              }
          }
  }
  
  
  function highlight(E,browser){
      if(E.checked){
        hL(E,browser);
      }
      else{
        dL(E,browser);
      }
    }
    
    function hL(E,browser){
      if (browser=="ie"){
          while (E.tagName!="TR"){
            E=E.parentElement;
          }
      }
      else{
        while (E.tagName!="TR"){
          E=E.parentNode;
          }
      }
      if(E.className.indexOf("hl")==-1){
         E.className = E.className+"hl";
      }
    }
    
    function dL(E,browser){
      if (browser=="ie"){
        while (E.tagName!="TR"){
          E=E.parentElement;
        }
      }
      else{
        while (E.tagName!="TR"){
          E=E.parentNode;
        }
      }
      E.className = E.className.substr(0,4);
    }
    
    function deleteOptions(optionListId){
     var frm = document.getElementById(optionListId);
     while (frm.options.length>0){
      deleteIndex=frm.options.length-1;
      frm.options[deleteIndex]=null;
     }
   }
    
    
   function insertOption(text,value,optionListId){
     var frm = document.getElementById(optionListId);
      
     if (frm.selectedIndex>0){
       insertIndex=frm.selectedIndex;
     }
     else{
       insertIndex= frm.options.length;
     }
     frm.options[insertIndex] = new Option(text,value);
    }

    
    function changeDivContent(divName, divContents) {
		if(document.layers){
			// Netscape 4 or equiv.
			divToChange = document.layers[divName];
			divToChange.document.open();
			divToChange.document.write(divContents);
			divToChange.document.close();
		} else if(document.all){
			// MS IE or equiv.
			divToChange = document.all[divName];
			divToChange.innerHTML = divContents;
		} else if(document.getElementById){
			// Netscape 6 or equiv.
      divToChange = document.getElementById(divName);
      divToChange.innerHTML = divContents;
		}
	}
  
  function selectAllOptions(obj) {
    var size = obj.options.length;
    var i = 0;
    
    for (i=0;i<size;i++) {
      if (obj.options[i].value != -1) {
          obj.options[i].selected = true;
      } else {
          obj.options[i].selected = false;
      }
    }
    
    return true;
  }
  
  function setParentHiddenField(hiddenFieldId, strValue) {
    document.getElementById(hiddenFieldId).value = strValue;
  }

   function clonaOperatoreAbusivo() {
	  
	  var dim = document.getElementById('dim_opAbusivo');
	  dim.value=parseInt(dim.value)+1; 
	  var tblBody = document.getElementById('tblClone_opAbusivo').tBodies[0];
	  var newNode = tblBody.rows[2].cloneNode(true);
	   	
	   /*Imposto gli id dei nuovi campi*/
	   /*In questo caso 3 input di tipo text presi in ordine*/
		
	   newNode.id= 'riga_opAbusivo_'+dim.value;
	   
	   
		for (i=0;i<newNode.getElementsByTagName('TEXTAREA').length;i++)
		{
			newNode.getElementsByTagName('TEXTAREA')[i].id =newNode.getElementsByTagName('TEXTAREA')[i].id + "_"+dim.value;
		  	newNode.getElementsByTagName('TEXTAREA')[i].name = newNode.getElementsByTagName('TEXTAREA')[i].id + "_"+dim.value;
		  	
			
		  	if (i==0)
		  		newNode.getElementsByTagName('TEXTAREA')[i].value = '';
		
		}	
		
		newNode.getElementsByTagName('INPUT')[0].id =newNode.getElementsByTagName('INPUT')[0].id + "_"+dim.value;
	  	newNode.getElementsByTagName('INPUT')[0].name = newNode.getElementsByTagName('INPUT')[0].id + "_"+dim.value;
		newNode.getElementsByTagName('INPUT')[1].id =newNode.getElementsByTagName('INPUT')[1].id + "_"+dim.value;
	  	newNode.getElementsByTagName('INPUT')[1].name = newNode.getElementsByTagName('INPUT')[1].id + "_"+dim.value;
	  	
		newNode.getElementsByTagName('a')[0].id="elimina"+"_"+dim.value;
		newNode.getElementsByTagName('a')[0].href ="javascript:eliminaTextAreaAbusivo("+dim.value+")"; 
		
	  	/*Lo rendo visibile*/
		newNode.style.display="";
	  	
	  	/*Aggancio il nodo*/
	  	//alert(clonato.parentNode)
	  	//clonato.parentNode.appendChild(clone);
	   tblBody.appendChild(newNode);
  }
 
  
  
  function addOperatoreControllato(tipoOperatore,ragioneSociale,numReg,targa,dim,orgIdOp){
	  	
	  	
	  	
	   var tblBody = opener.document.getElementById('tblClone_ps').tBodies[0];
	   var newNode = tblBody.rows[2].cloneNode(true);
	   	
	   /*Imposto gli id dei nuovi campi*/
	   /*In questo caso 3 input di tipo text presi in ordine*/
		
	   newNode.id= 'riga_ps'+dim;
	   
		for (i=0;i<newNode.getElementsByTagName('INPUT').length;i++)
		{
			newNode.getElementsByTagName('INPUT')[i].id =newNode.getElementsByTagName('INPUT')[i].id + "_"+dim;
		  	newNode.getElementsByTagName('INPUT')[i].name = newNode.getElementsByTagName('INPUT')[i].id + "_"+dim;
			
		  	if (i==0)
		  		newNode.getElementsByTagName('INPUT')[i].value = orgIdOp;
		
		}
		newNode.getElementsByTagName('P')[0].innerHTML = tipoOperatore;
		newNode.getElementsByTagName('P')[1].innerHTML = ragioneSociale;
		newNode.getElementsByTagName('P')[2].innerHTML = numReg;
		newNode.getElementsByTagName('P')[3].innerHTML = targa;
			
		newNode.getElementsByTagName('a')[0].id="elimina"+"_"+dim;
		newNode.getElementsByTagName('a')[0].href ="javascript:eliminaOperatore("+dim+")"; 
		
	  	/*Lo rendo visibile*/
		newNode.style.display="";
	  	
	  	/*Aggancio il nodo*/
	  	//alert(clonato.parentNode)
	  	//clonato.parentNode.appendChild(clone);
 	   tblBody.appendChild(newNode);
	   window.close();
	   
	  }
  
  
  function addOperatoreControllatoZone(ragioneSociale,citta,note,dim,orgIdOp){
	  	
	  	
	  	
	   var tblBody = opener.document.getElementById('tblClone_ps').tBodies[0];
	   var newNode = tblBody.rows[2].cloneNode(true);
	   	
	   /*Imposto gli id dei nuovi campi*/
	   /*In questo caso 3 input di tipo text presi in ordine*/
		
	   newNode.id= 'riga_ps'+dim;
	   
		for (i=0;i<newNode.getElementsByTagName('INPUT').length;i++)
		{
			newNode.getElementsByTagName('INPUT')[i].id =newNode.getElementsByTagName('INPUT')[i].id + "_"+dim;
		  	newNode.getElementsByTagName('INPUT')[i].name = newNode.getElementsByTagName('INPUT')[i].id + "_"+dim;
			
		  	if (i==0)
		  		newNode.getElementsByTagName('INPUT')[i].value = orgIdOp;
		
		}
		newNode.getElementsByTagName('P')[0].innerHTML = ragioneSociale;
		newNode.getElementsByTagName('P')[2].innerHTML = note;
		newNode.getElementsByTagName('P')[1].innerHTML = citta;
			
		newNode.getElementsByTagName('a')[0].id="elimina"+"_"+dim;
		newNode.getElementsByTagName('a')[0].href ="javascript:eliminaOperatore("+dim+")"; 
		
	  	/*Lo rendo visibile*/
		newNode.style.display="";
	  	
	  	/*Aggancio il nodo*/
	  	//alert(clonato.parentNode)
	  	//clonato.parentNode.appendChild(clone);
	   tblBody.appendChild(newNode);
	   window.close();
	   
	  }
  
  function addLaboratorioControllato(ragioneSociale,numReg,dim,orgIdOp){
	  	
	   var tblBody = opener.document.getElementById('tblClone').tBodies[0];
	   var newNode = tblBody.rows[2].cloneNode(true);
	   	
	   /*Imposto gli id dei nuovi campi*/
	   /*In questo caso 3 input di tipo text presi in ordine*/
		
	   newNode.id= 'riga_'+dim;
		for (i=0;i<newNode.getElementsByTagName('INPUT').length;i++)
		{
			newNode.getElementsByTagName('INPUT')[i].id =newNode.getElementsByTagName('INPUT')[i].id + "_"+dim;
		  	//newNode.getElementsByTagName('INPUT')[i].name = newNode.getElementsByTagName('INPUT')[i].id + "_"+dim;
			
		  	if (i==0)
		  		newNode.getElementsByTagName('INPUT')[i].value = orgIdOp;
		
		}
		
		newNode.getElementsByTagName('P')[0].innerHTML = ragioneSociale;
		newNode.getElementsByTagName('P')[1].innerHTML = numReg;
			
		newNode.getElementsByTagName('a')[0].id="elimina"+"_"+dim;
		newNode.getElementsByTagName('a')[0].href ="javascript:eliminaLaboratorio("+dim+")"; 
		
	  	/*Lo rendo visibile*/
		newNode.style.display="";
	  	
	  	/*Aggancio il nodo*/
	  	//alert(clonato.parentNode)
	  	//clonato.parentNode.appendChild(clone);
	   tblBody.appendChild(newNode);
	   window.close();
	   
	  }
  

  function eliminaOperatore(size)
  {	
	  var clonato = document.getElementById('riga_ps'+size);
  	  clonato.parentNode.removeChild(clonato);
  	  size = document.getElementById('dim');
  	  size.value=parseInt(size.value)-1;
  }
  
  function eliminaLaboratorio(size)
  {	
	  var clonato = document.getElementById('riga_'+size);
  	  clonato.parentNode.removeChild(clonato);
  	  size = document.getElementById('dim_lab');
  	  size.value=parseInt(size.value)-1;
  }
  
  function eliminaTextArea(size)
  {	
	  var clonato = document.getElementById('riga_noreg_'+size);
  	  clonato.parentNode.removeChild(clonato);
  	  size = document.getElementById('dim_lab_noreg');
  	  size.value=parseInt(size.value)-1;
  }
  
  function eliminaTextAreaAbusivo(size)
  {	
	  var clonato = document.getElementById('riga_opAbusivo_'+size);
  	  clonato.parentNode.removeChild(clonato);
  	  size = document.getElementById('dim_opAbusivo');
  	  size.value=parseInt(size.value)-1;
  }
  
  
  
  
  function popLookupSelectorAllerta(displayFieldId, displayFieldId2, table, params) {
	  title  = '_types';
	  width  =  '500';
	  height =  '800';
	  resize =  'yes';
	  bars   =  'yes';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('LookupSelector.do?command=PopupSelectorAllerta&displayFieldId=' + displayFieldId + '&displayFieldId2=' + displayFieldId2 + '&table=' + table + params, title, windowParams+'&filtroDesc=700');
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}
  
  function popLookupSelectorBuffer(displayFieldId, displayFieldId2, table, params) {
	  title  = '_types';
	  width  =  '500';
	  height =  '450';
	  resize =  'yes';
	  bars   =  'no';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('LookupSelector.do?command=PopupSelectorBuffer&displayFieldId=' + displayFieldId + '&displayFieldId2=' + displayFieldId2 + '&table=' + table + params, title, windowParams+'&filtroDesc=700');
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}
  
  
  function popLookupSelectorAllertaRicerca(displayFieldId, displayFieldId2, table, params) {
	  title  = '_types';
	  width  =  '600';
	  height =  '550';
	  resize =  'yes';
	  bars   =  'no';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('LookupSelector.do?command=PopupSelectorAllertaRicercaImprese&displayFieldId=' + displayFieldId + '&displayFieldId2=' + displayFieldId2 + '&table=' + table + params, title, windowParams+'&filtroDesc=700');
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}
  
  function popLookupSelectorDownloadModulo(nomeModulo) {
	  title  = '_types';
	  width  =  '300';
	  height =  '100';
	  resize =  'no';
	  bars   =  'no';
	  url = 'LookupSelector.do?command=PopupSelectorDownloadModulo&nomeModulo=' + nomeModulo;
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	 
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + ',screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open(url,title,windowParams);
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}
  
  
  function popLookupSelectorNuovoNodoOia(parametri_nuovo_nodo) {
	  
	  title  = '_types';
	  width  =  '615';
	  height =  '475';
	  resize =  'no';
	  bars   =  'no';
	  url = 'Oia.do?command=Nuovo&' + parametri_nuovo_nodo;
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	 
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + ',screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open(url,title,windowParams);
	  newwin.focus();

	}
  
  function popLookupSelectorModificaNodoOia(parametri_nuovo_nodo) {
	  title  = '_types';
	  width  =  '615';
	  height =  '475';
	  resize =  'no';
	  bars   =  'no';
	  url = 'Oia.do?command=Modifica&' + parametri_nuovo_nodo;
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	 
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + ',screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open(url,title,windowParams);
	  newwin.focus();

	}
  
  
  function popLookupSelectorModificaAssegnazioneNodoOia(parametri_nuovo_nodo) {
	  title  = '_types';
	  width  =  '615';
	  height =  '475';
	  resize =  'no';
	  bars   =  'no';
	  url = 'Oia.do?command=AssegnazioneCu&' + parametri_nuovo_nodo;
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	 
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + ',screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open(url,title,windowParams);
	  newwin.focus();

	}
  
  function popLookupSelectorAssegnazioneCuOia(parametri_nuovo_nodo) {
	  title  = '_types';
	  width  =  '615';
	  height =  '475';
	  resize =  'no';
	  bars   =  'no';
	  url = 'Oia.do?command=AssegnazioneCu&' + parametri_nuovo_nodo;
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	 
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + ',screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open(url,title,windowParams);
	  newwin.focus();

	}
  
  function popLookupSelectorNuovoUnitaCrisi(parametri_nuovo_nodo) {
	  title  = '_types';
	  width  =  '390';
	  height =  '225';
	  resize =  'no';
	  bars   =  'no';
	  url = 'UnitaCrisi.do?command=Nuovo&' + parametri_nuovo_nodo;
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	 
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + ',screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open(url,title,windowParams);
	  newwin.focus();

	}
  
  
  function popLookupSelectorModificaUnitaCrisi(parametri_nuovo_nodo) {
	  title  = '_types';
	  width  =  '390';
	  height =  '225';
	  resize =  'no';
	  bars   =  'no';
	  url = 'UnitaCrisi.do?command=Modifica&' + parametri_nuovo_nodo;
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	 
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + ',screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open(url,title,windowParams);
	  newwin.focus();

	}
  
  function openPopupLabDetails(orgId){
		var res;
		var result;
		window.open('LaboratoriHACCP.do?command=Details&popup=true&orgId='+orgId,'popupSelect',
			'height=300px,width=580px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');
  }
	
  		
  
  function clonaNelPadre(){ 
	  	var maxElementi = 100;
	  	var elementi;
	  	var elementoClone;
	  	var tableClonata;
	  	var tabella;
	  	var selezionato;
	  	var x;
	  	elementi = document.getElementById('elementi');
	  	elementi.value=parseInt(elementi.value)+1;
	  	size = document.getElementById('size');
	  	size.value=parseInt(size.value)+1;
	  var elementoCorrente = elementi.value ;
	  	var clonanbsp = document.getElementById('row');
	  	
	  	/*clona riga vuota*/
	  	clone=clonanbsp.cloneNode(true);
		id_field_new_impianto = 'impianto_'+size.value  		  	
	  	clone.getElementsByTagName('SELECT')[0].name = "categoria_" + elementi.value;
	  	clone.getElementsByTagName('SELECT')[0].id = "categoria_" + elementi.value;
	  	clone.getElementsByTagName('SELECT')[0].value = "-1";
	  
			
		clone.getElementsByTagName('SELECT')[1].name = "impianto_" + elementi.value;
	  	clone.getElementsByTagName('SELECT')[1].id = "impianto_" + elementi.value;
		clone.getElementsByTagName('SELECT')[1].value =  "-1";
		
		clone.getElementsByTagName('SELECT')[0].onchange = function () 
		{ 
			setComboImpiantiProdotti(document.getElementById('categoria_'+ elementoCorrente).value,"impianto_"+elementoCorrente,"prodotti_"+elementoCorrente);
			//setComboImpianti(document.getElementById('categoria_'+ elementoCorrente).value,"impianto_"+elementoCorrente);
			//setComboProdotti(document.getElementById('categoria_'+ elementoCorrente).value,"prodotti_"+elementoCorrente);
			
		}

		clone.getElementsByTagName('SELECT')[2].name = "prodotti_" + elementi.value;
	  	clone.getElementsByTagName('SELECT')[2].id = "prodotti_" + elementi.value;
		clone.getElementsByTagName('SELECT')[2].value =  "-1";
		
		//clone.getElementsByTagName('SELECT')[3].name = "classificazione_" + elementi.value;
	  	//clone.getElementsByTagName('SELECT')[3].id = "classificazione_" + elementi.value;
		//clone.getElementsByTagName('SELECT')[3].value =  "-1";
		
		statoIndomanda = 3 ;
//		clone.getElementsByTagName('SELECT')[3].name = "statoLab_" + elementi.value;
//	  	clone.getElementsByTagName('SELECT')[3].id = "statoLab_" + elementi.value;
//		clone.getElementsByTagName('SELECT')[3].value =  statoIndomanda;
		
//		CLONE.GETELEMENTSBYTAGNAME('SELECT')[5].NAME = "TIPOAUTORIZZAZZIONE_" + ELEMENTI.VALUE;
//	  	CLONE.GETELEMENTSBYTAGNAME('SELECT')[5].ID = "TIPOAUTORIZZAZZIONE_" + ELEMENTI.VALUE;
//		CLONE.GETELEMENTSBYTAGNAME('SELECT')[5].VALUE =  "-1";
		
		//clone.getElementsByTagName('INPUT')[0].name = "dateI_" + elementi.value;
	  	//clone.getElementsByTagName('INPUT')[0].id = "dateI_" + elementi.value;
		//clone.getElementsByTagName('INPUT')[0].value =  "";
		
		
		//clone.getElementsByTagName('INPUT')[1].name = "dateF_" + elementi.value;
	  	//clone.getElementsByTagName('INPUT')[1].id = "dateF_" + elementi.value;
		//clone.getElementsByTagName('INPUT')[1].value =  "";
		
		clone.getElementsByTagName('INPUT')[0].name = "statoLab_" + elementi.value;
	  	clone.getElementsByTagName('INPUT')[0].id = "statoLab_" + elementi.value;
		clone.getElementsByTagName('INPUT')[0].value =  statoIndomanda;
		
		 
		clone.getElementsByTagName('INPUT')[1].name = "riti_religiosi_" + elementi.value;
	  	clone.getElementsByTagName('INPUT')[1].id = "riti_religiosi_" + elementi.value;
		clone.getElementsByTagName('INPUT')[1].checked = false;
		
		clone.getElementsByTagName('INPUT')[2].name = "imballata_" + elementi.value;
	  	clone.getElementsByTagName('INPUT')[2].id = "imballata_" + elementi.value;
		clone.getElementsByTagName('INPUT')[2].checked = false;
		
		clone.getElementsByTagName('INPUT')[3].name = "non_imballata_" + elementi.value;
	  	clone.getElementsByTagName('INPUT')[3].id = "non_imballata_" + elementi.value;
		clone.getElementsByTagName('INPUT')[3].checked = false;
		
		
		
	  	
		if (clone.getElementsByTagName('INPUT')[4]!=null)
			{
		clone.getElementsByTagName('INPUT')[4].name = "dateI_" + elementi.value;
	  	clone.getElementsByTagName('INPUT')[4].id = "dateI_" + elementi.value ;
		clone.getElementsByTagName('INPUT')[4].value =  "";
			}
		
		/*clone.getElementsByTagName('INPUT')[5].name = "dateF_" + elementi.value;
	  	clone.getElementsByTagName('INPUT')[5].id = "dateF_" + elementi.value;
		clone.getElementsByTagName('INPUT')[5].value =  "";
		*/
		if (clone.getElementsByTagName('A').length>0)
		clone.getElementsByTagName('A')[0].href = "javascript:popCalendar('addAccount','dateI_" + elementi.value +"','it','IT','Europe/Berlin');"
		//clone.getElementsByTagName('A')[1].href = "javascript:popCalendar('addAccount','" + clone.getElementsByTagName('INPUT')[1].name +"','it','IT','Europe/Berlin');"
		
		
	  	
	  	clone.id = "row_" + elementi.value;
	  	clone.style.display=""
	  	/*Aggancio il nodo*/
	  	clonanbsp.parentNode.appendChild(clone);
	
	  
	 
	  }
  
  function clonaCoordinateMolluschi(){ 
	  	var maxElementi = 100;
	  	var elementi;
	  	var elementoClone;
	  	var tableClonata;
	  	var tabella;
	  	var selezionato;
	  	var x;
	  	
	  	elementi = document.getElementById('elementi');
	  	if(elementi.value<6){
	  	elementi.value=parseInt(elementi.value)+1;
	  	size = document.getElementById('size');
	  	size.value=parseInt(size.value)+1;
	  	var elementoCorrente = elementi.value ;
	  var clonanbsp = document.getElementById('riga');
	  	
	  	/*clona riga vuota*/
	  	clone=clonanbsp.cloneNode(true);
		
	  	clone.getElementsByTagName('INPUT')[0].name = "address" + elementi.value+ "latitude";
	  	clone.getElementsByTagName('INPUT')[0].id = "address" + elementi.value+ "latitude";;
	  	clone.getElementsByTagName('INPUT')[0].value = "0";
	  	
	  	clone.getElementsByTagName('INPUT')[1].name = "address" + elementi.value+ "longitude";
	  	clone.getElementsByTagName('INPUT')[1].id = "address" + elementi.value+ "longitude";;
	 	clone.getElementsByTagName('INPUT')[1].value = "0";
	  
	  	clone.id = "riga" + elementi.value;
	  	clone.style.display=""
	  	/*Aggancio il nodo*/
	  	clonanbsp.parentNode.appendChild(clone);
	  	}
	  
	 
	  }
  
  
  function popLookupSelectorModificaNodoDpat(parametri_nuovo_nodo) {
	  title  = '_types';
	  width  =  '615';
	  height =  '475';
	  resize =  'no';
	  bars   =  'no';
	  url = 'Dpat.do?command=Modifica&' + parametri_nuovo_nodo;
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	 
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + ',screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open(url,title,windowParams);
	  newwin.focus();

	}
  
function popLookupSelectorNuovoNodoDpat(parametri_nuovo_nodo) {
	  
	  title  = '_types';
	  width  =  '615';
	  height =  '475';
	  resize =  'no';
	  bars   =  'no';
	  url = 'Dpat.do?command=Nuovo&' + parametri_nuovo_nodo;
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	 
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + ',screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open(url,title,windowParams);
	  newwin.focus();

	}


function searchBdn(codAzienda,tipoAll){  

	Geocodifica.getAllevamentoProvenienza(codAzienda,'','',tipoAll,searchBdnCallBack);
	
}

function searchBdnCallBack (infoAllevamento)
{

	if (infoAllevamento != null)
		{
		denominazione='' ;
		tipoAllevamento ='';
		tipoHabitat='';
		sedeAllevamento = '' ;
		if (infoAllevamento.denominazione != null && infoAllevamento.denominazione != 'null')
			denominazione = infoAllevamento.denominazione;
		
		if (infoAllevamento.descrizioneTipoAllevamento != null && infoAllevamento.descrizioneTipoAllevamento != 'null')
			tipoAllevamento = infoAllevamento.descrizioneTipoAllevamento;
		
		if (infoAllevamento.comune != null && infoAllevamento.comune != 'null')
			sedeAllevamento = infoAllevamento.comune;
			
		if (infoAllevamento.denominazione != null && infoAllevamento.denominazione != 'null')
			{
			
			
			document.getElementById('infoAzienda').value="Denominazione : "+denominazione+" Sede in "+sedeAllevamento+" Tipo Allevamento :"+tipoAllevamento;
	
	
	document.getElementById('rag_soc_azienda_provenienza').value =denominazione;
	document.getElementById('id_codice_asl_azienda_prov').value =infoAllevamento.aslCodice;
	document.getElementById('denominazione_asl_azienda_prov').value =infoAllevamento.denominazione_asl;
	document.getElementById('cod_asl_azienda_prov').value =infoAllevamento.idAsl;

	
	
	
			}
		else{
			
			document.getElementById('infoAzienda').value="Azienda non in BDN";
		}

		}
	else
		{
		alert('Azienda non trovata in BDN');
		document.getElementById('infoAzienda').value="";
		}
	}
	
  
