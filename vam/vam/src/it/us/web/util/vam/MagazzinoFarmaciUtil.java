/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.vam;

import java.util.ArrayList;

import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.MagazzinoFarmaci;
import it.us.web.dao.hibernate.Persistence;

public class MagazzinoFarmaciUtil {
	
	public static MagazzinoFarmaci checkFarmaco (Persistence persistence, int idClinica, int idFarmaco, int idTipoFarmaco) {
		
		MagazzinoFarmaci mf = null ;
		
		//Recupero fper capire se quella clinica possiade già quel farmaco di quel tipo
		ArrayList<MagazzinoFarmaci> mfList = (ArrayList<MagazzinoFarmaci>) 
			persistence.getNamedQuery("CheckFarmacoInClinica")
			.setInteger("idClinica", idClinica)
			.setInteger("idFarmaco", idFarmaco)
			.setInteger("idTipoFarmaco", idTipoFarmaco)
			.list();
		
		if (mfList.size() > 0) {
			mf = (MagazzinoFarmaci) mfList.get(0);
		}
		
		return mf;
					
	}
}
