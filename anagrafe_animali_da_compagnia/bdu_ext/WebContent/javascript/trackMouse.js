/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
  if (document.layers) { // Netscape
      document.captureEvents(Event.MOUSEMOVE);
      document.onmousemove = captureMousePosition;
  } else if (document.all) { // Internet Explorer
      document.onmousemove = captureMousePosition;
  } else if (document.getElementById) { // Netcsape 6
      document.onmousemove = captureMousePosition;
  }
  // Global variables
  xMousePos = 0; // Horizontal position of the mouse on the screen
  yMousePos = 0; // Vertical position of the mouse on the screen
  xMousePosMax = 0; // Width of the page
  yMousePosMax = 0; // Height of the page
  
  function captureMousePosition(e) {
    if (document.all) {
      //IE
      xMousePos = window.event.x+document.body.scrollLeft;
      yMousePos = window.event.y+document.body.scrollTop;
      xMousePosMax = document.body.clientWidth+document.body.scrollLeft;
      yMousePosMax = document.body.clientHeight+document.body.scrollTop;
    } else if (document.getElementById || document.layers) {
      // NS6, NS4
      xMousePos = e.pageX;
      yMousePos = e.pageY;
      xMousePosMax = window.innerWidth+window.pageXOffset;
      yMousePosMax = window.innerHeight+window.pageYOffset;
    }
  }
