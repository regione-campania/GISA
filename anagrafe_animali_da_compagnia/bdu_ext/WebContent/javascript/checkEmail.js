/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 * Checks to see if a valid email address is entered
 * @arg1 = email address to check
 */
function checkEmail(emailin) {
  if (emailin.length > 0){
    if ((emailin.length < 5) ||
        (emailin.indexOf('@',0) < 1) ||
        (emailin.lastIndexOf('@') != emailin.indexOf('@',0)) ||
        (emailin.indexOf('..',0) > -1) ||
        (emailin.indexOf('@.',0) > -1) ||
        (emailin.indexOf('.@',0) > -1) ||
        (emailin.indexOf(',',0) > -1)) {
        return false;
    }
  var i = emailin.search(/^([a-zA-Z0-9_\.\-])+@\w+([\.\-_]?\w+)*/);
  if (i == -1  || emailin.indexOf('.', 0) == -1 || emailin.lastIndexOf('.') >= (emailin.length - 2) || 
        emailin.lastIndexOf('.') < (emailin.length - 5)) {
      return false;
    }
  }
  return true;
}

function checkMultipleEmailString(emailin) {
//Needs a function to check each individual email that is separated by a comma.
  var result = checkEmail(emailin);
  return result;
}

