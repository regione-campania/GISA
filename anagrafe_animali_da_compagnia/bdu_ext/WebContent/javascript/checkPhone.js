/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function checkPhone(phonein) {
	var stripped = phonein.replace(/[^0123456789\+]/g, '');
	
	//strip out acceptable non-numeric characters and make sure there is a number entered
	if (stripped.length > 0) {
    if (stripped.indexOf("+") == 0) {
			if ( (stripped.substr(1, ((phonein.length)-1))).indexOf("+") == -1 ) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}else if(phonein.length > 0){
    return false;
  }
	return true;
}


/**
 *  Code for doing a regular expression validation (TODO: Integrate it later)
    j.compile("[0-9]{3}-[0-9]{3}-[0-9]{4}");
    if (!j.test(form["Phone-Number"].value)) {
      alert("You must supply a valid US phone number.");
      return false;
    }
 *
**/
