/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 * ARIA Collapsible Dropdown Listbox Example
 * @function onload
 * @desc Initialize the listbox example once the page has loaded
 */

window.addEventListener('load', function () {
  var button = document.getElementById('exp_button');
  var exListbox = new aria.Listbox(document.getElementById('exp_elem_list'));
  var listboxButton = new aria.ListboxButton(button, exListbox);
});

var aria = aria || {};

aria.ListboxButton = function (button, listbox) {
  this.button = button;
  this.listbox = listbox;
  this.registerEvents();
};

aria.ListboxButton.prototype.registerEvents = function () {
  this.button.addEventListener('click', this.showListbox.bind(this));
  this.button.addEventListener('keyup', this.checkShow.bind(this));
  this.listbox.listboxNode.addEventListener('blur', this.hideListbox.bind(this));
  this.listbox.listboxNode.addEventListener('keydown', this.checkHide.bind(this));
  this.listbox.setHandleFocusChange(this.onFocusChange.bind(this));
};

aria.ListboxButton.prototype.checkShow = function (evt) {
  var key = evt.which || evt.keyCode;

  switch (key) {
    case aria.KeyCode.UP:
    case aria.KeyCode.DOWN:
      evt.preventDefault();
      this.showListbox();
      this.listbox.checkKeyPress(evt);
      break;
  }
};

aria.ListboxButton.prototype.checkHide = function (evt) {
  var key = evt.which || evt.keyCode;

  switch (key) {
    case aria.KeyCode.RETURN:
    case aria.KeyCode.ESC:
      evt.preventDefault();
      this.hideListbox();
      this.button.focus();
      break;
  }
};

aria.ListboxButton.prototype.showListbox = function () {
  aria.Utils.removeClass(this.listbox.listboxNode, 'hidden');
  this.button.setAttribute('aria-expanded', 'true');
  this.listbox.listboxNode.focus();
};

aria.ListboxButton.prototype.hideListbox = function () {
  aria.Utils.addClass(this.listbox.listboxNode, 'hidden');
  this.button.removeAttribute('aria-expanded');
};

aria.ListboxButton.prototype.onFocusChange = function (focusedItem) {
  this.button.innerText = focusedItem.innerText;
};
