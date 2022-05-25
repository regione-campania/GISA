/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.vam;

import java.util.ArrayList;

import it.us.web.bean.vam.MagazzinoMangimi;
import it.us.web.dao.hibernate.Persistence;

public class MagazzinoMangimiUtil {
	
	public static MagazzinoMangimi checkMangime(Persistence persistence, int idClinica, int idTipoAnimale, int idEtaAnimale, int idMangime) 
	{
		
		MagazzinoMangimi magazzinoMangime = null ;
		
		//Recupero per capire se quella clinica possiade già quel mangime
		ArrayList<MagazzinoMangimi> magazzinoMangimiList = (ArrayList<MagazzinoMangimi>) 
			persistence.getNamedQuery("CheckMangimeInClinica")
			.setInteger("idClinica",     idClinica)
			.setInteger("idTipoAnimale", idTipoAnimale)
			.setInteger("idEtaAnimale",  idEtaAnimale)
			.setInteger("idMangime",     idMangime)
			.list();
		
		if (magazzinoMangimiList.size() > 0) 
		{
			magazzinoMangime = (MagazzinoMangimi) magazzinoMangimiList.get(0);
		}
		
		return magazzinoMangime;
					
	}
}
