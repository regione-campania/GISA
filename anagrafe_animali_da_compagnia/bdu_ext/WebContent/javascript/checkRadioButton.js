/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function getSelectedRadio(buttonGroup) {
  if (!buttonGroup) {
    // there is no button group
    return -1;
  }
  // returns the array number of the selected radio button or -1 if no button is selected
  if (buttonGroup[0]) { // if the button group is an array (one button is not an array)
     for (var i=0; i<buttonGroup.length; i++) {
        if (buttonGroup[i].checked) {
           return i
        }
     }
  } else {
     if (buttonGroup.checked) { return 0; } // if the one button is checked, return zero
  }
  // if we get to this point, no radio button is selected
  return -1;
} // Ends the "getSelectedRadio" function

function getSelectedRadioValue(buttonGroup) {
  if (!buttonGroup) {
    // there is no button group
    return "";
  }
  // returns the value of the selected radio button or "" if no button is selected
  var i = getSelectedRadio(buttonGroup);
  if (i == -1) {
     return "";
  } else {
     if (buttonGroup[i]) { // Make sure the button group is an array (not just one button)
        return buttonGroup[i].value;
     } else { // The button group is just the one button, and it is checked
        return buttonGroup.value;
     }
  }
} // Ends the "getSelectedRadioValue" function

function setSelectedRadio(buttonGroup, setValue) {
  if (!buttonGroup) {
    // there is no button group
    return -1;
  }
  // returns the array number of the selected radio button or -1 if no button is selected
  if (buttonGroup[0]) { // if the button group is an array (one button is not an array)
     for (var i=0; i<buttonGroup.length; i++) {
        if (buttonGroup[i].value == setValue) {
           buttonGroup[i].checked = true;
           break;
        }
     }
     return 0;
  } else {
     if (buttonGroup.value == setValue) { buttonGroup.checked = true; return 0 } // if the one button is checked, return zero
  }
  // if we get to this point, no radio button is selected
  return -1;
}
