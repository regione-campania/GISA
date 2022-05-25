/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 * Checks to see if a valid URL is entered
 * @arg1 = url to check
 */
function checkURL(url){
  if (url.length > 0){
    url = padURL(url);
    var i = url.search(/^[A-Za-z]+:\/\/\w+([\.-]?\w+)*/);
    if (i == -1  || (url.indexOf('.', 0) == -1)) {
      return false;
    }
  }
  return true;
}

function padURL(url){
  if(url.indexOf('http://', 0) == -1){
    url = "http://" + url;
  }
  return url;
}
