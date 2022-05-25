/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function spinRight(thisID) {
  isNS4 = (document.layers) ? true : false;
  isIE4 = (document.all && !document.getElementById) ? true : false;
  isIE5 = (document.all && document.getElementById) ? true : false;
  isNS6 = (!document.all && document.getElementById) ? true : false;
  if (isNS4){
    elm = document.layers[thisID];
  } else if (isIE4) {
    elm = document.all[thisID];
  } else if (isIE5 || isNS6) {
    elm = document.getElementById(thisID);
    if (elm.value < spinMax) {
      elm.value = parseInt(elm.value) + 1;
    } else {
      elm.value = spinMax;
    }
  }
}
function spinLeft(thisID) {
  isNS4 = (document.layers) ? true : false;
  isIE4 = (document.all && !document.getElementById) ? true : false;
  isIE5 = (document.all && document.getElementById) ? true : false;
  isNS6 = (!document.all && document.getElementById) ? true : false;
  if (isNS4){
    elm = document.layers[thisID];
  } else if (isIE4) {
    elm = document.all[thisID];
  } else if (isIE5 || isNS6) {
    elm = document.getElementById(thisID);
    if (elm.value > spinMin) {
      elm.value = parseInt(elm.value) - 1;
    } else {
      elm.value = spinMin;
    }
  }
}
