/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.vam;

import java.util.ArrayList;

import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.MagazzinoArticoliSanitari;
import it.us.web.bean.vam.MagazzinoFarmaci;
import it.us.web.dao.hibernate.Persistence;

public class MagazzinoArticoliSanitariUtil {
	
	public static MagazzinoArticoliSanitari checkArticoloSanitario(Persistence persistence, int idClinica, int idArticoloSanitario) 
	{
		
		MagazzinoArticoliSanitari magazzinoArticoliSanitari = null ;
		
		//Recupero per capire se quella clinica possiade già quell'articolo sanitario
		ArrayList<MagazzinoArticoliSanitari> magazzinoArticoliSanitariList = (ArrayList<MagazzinoArticoliSanitari>) 
			persistence.getNamedQuery("CheckArticoloSanitarioInClinica")
			.setInteger("idClinica", idClinica)
			.setInteger("idArticoloSanitario", idArticoloSanitario)
			.list();
		
		if (magazzinoArticoliSanitariList.size() > 0) 
		{
			magazzinoArticoliSanitari = (MagazzinoArticoliSanitari) magazzinoArticoliSanitariList.get(0);
		}
		
		return magazzinoArticoliSanitari;
					
	}
}
