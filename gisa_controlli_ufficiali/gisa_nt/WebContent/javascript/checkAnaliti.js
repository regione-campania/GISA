/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function controllaTipoAnaliti(n_v){
	var flag = 0;
	var numA = document.getElementById("numeroAnaliti").value;
	var str = "";
	
	//controllo tra num verbale ed analiti	 
	if (numA>0){
		if (n_v.match("vpb$") ){ //BATTERIOLOGICO
			for(i=1;i<=numA;i++){
				str = document.getElementById("An"+i).textContent;
				if(str.match('BATTERIOLOGICO')){ 
					flag=1;
				}
			}
		} else {
			if (n_v.match("vpc$") ){ //CHIMICO
				for(i=1;i<=numA;i++){
					str = document.getElementById("An"+i).textContent;
					if(str.match('CHIMICO')){ 
						flag=1;
					}
				}
			}
		} 
	} else {
		flag=1;
	}
	return flag;
}