/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 * Appends page x/y to url for reloading to same position
 */
function scrollReload(filename) {
  var posx = 0;
  var posy = 0;
  var d = null;
  if (document.documentElement && document.documentElement.scrollTop) {
    d = document.documentElement;
  } else {
    d = document.body;
  }
  if (window.scrollX) {
    posx = window.scrollX;
  } else {
    posx = d.scrollLeft;
  }
  if (window.scrollY) {
    posy = window.scrollY;
  } else {
    posy = d.scrollTop;
  }
  if (posx > 0 || posy > 0) {
    filename = filename + "&scrollLeft=" + posx + "&scrollTop=" + posy;
  }
  window.location.href = filename;
}
