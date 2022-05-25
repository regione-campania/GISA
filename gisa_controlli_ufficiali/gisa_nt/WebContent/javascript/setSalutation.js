/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 * Sets the salutation field with the text from the salutation drop list 
 * @arg1 = email address to check
 */
function fillSalutation(formName){
  var index = document.forms[formName].listSalutation.selectedIndex;
  var text = document.forms[formName].listSalutation.options[index].text;
  if (index > 0) {
    document.forms[formName].nameSalutation.value = text;
  } else {
    document.forms[formName].nameSalutation.value = "";
  }
}

