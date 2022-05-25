/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function textCounter(field, countfield, maxlimit) {
  if (field.value.length > maxlimit) {
    field.value = field.value.substring(0, maxlimit);
    if (field.createTextRange) {
      var range = field.createTextRange();
      range.moveStart('character', field.value.length);
      range.collapse(false);
      range.select();
    }
    alert('Your message may not exceed ' + maxlimit + ' characters in length.');
  } else {
    countfield.value = maxlimit - field.value.length;
  }
}
