/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
var base = "images/";
var p1 = new Array();
var p2 = new Array();
var lib = new Array();
function loadImages(newLib) {
  if (document.images) {
    lib = new Array(newLib);
    for (i=0;i<lib.length;i++) {
      p1[i] = new Image;
      p1[i].src = base + lib[i] + ".gif";
      p2[i] = new Image;
      p2[i].src = base + lib[i] + "-on.gif";
    }
  }
}
function over(i, c) {
  if (window.images) {
    window.images[lib[i] + c].src = p2[i].src;
  }
}
function out(i, c) {
  if (window.images) {
    window.images[lib[i] + c].src = p1[i].src;
  }
}
