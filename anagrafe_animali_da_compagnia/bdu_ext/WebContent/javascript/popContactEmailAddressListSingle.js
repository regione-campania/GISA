/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function popContactEmailAddressListSingle() {
  title  = 'Contact_Address';
  width  =  '575';
  height =  '400';
  resize =  'yes';
  bars   =  'yes';
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + ',screenX=' + posx + ',screenY=' + posy;
  popURL('ContactEmailAddressSelector.do?command=List&popup=true', title,width,height,resize,bars);
}

function popContactEmailAddressListSingle(contactId) {
  title  = 'Contact_Address';
  width  =  '575';
  height =  '400';
  resize =  'yes';
  bars   =  'yes';
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + ',screenX=' + posx + ',screenY=' + posy;
  popURL('ContactEmailAddressSelector.do?command=List&popup=true&contactId='+contactId, title,width,height,resize,bars);
}

function popContactEmailAddressListSingle(contactId, hiddenFieldId) {
  title  = 'Contact_Address';
  width  =  '575';
  height =  '400';
  resize =  'yes';
  bars   =  'yes';
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + ',screenX=' + posx + ',screenY=' + posy;
  popURL('ContactEmailAddressSelector.do?command=List&popup=true&contactId='+contactId+'&hiddenFieldId='+hiddenFieldId, title,width,height,resize,bars);
}

function setContactEmailAddress(email,type){
  var something = opener.populateEmailAddress(email,type);
  self.close();
}

function setContactEmailAddress(email,type, hiddenFieldId){
  var something = opener.populateEmailAddress(email,type, hiddenFieldId);
  self.close();
}

