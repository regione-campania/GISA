/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 * Displays a confirmation to the user
 * @arg1 = URL to forward to if confirmation returns true
 */
function confirmCheckboxes(which) {
	var pass=false
	for(i=0;i<which.length;i++) {
		var tempobj = which.elements[i]
		if (tempobj.type=="checkbox"&&tempobj.checked==true) {
			pass=true
		}
	}
	
	if(!pass) {
		alert("Please select one or more groups first")
		return false
	} else {
		return true
	}
}

