/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils.jmesa;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jmesa.core.filter.FilterMatcher;

public class MyDateFilterMatcher implements FilterMatcher {
    public boolean evaluate(Object itemValue, String filterValue) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        
		Date item;
		try 
		{
			item = sdf2.parse(String.valueOf(itemValue));
			
			String inizio = (filterValue.split("-----")[0].equals("nullo"))?(""):(filterValue.split("-----")[0]);
			String fine   = (filterValue.split("-----")[1].equals("nullo"))?(""):(filterValue.split("-----")[1]);
			
			Date dataIni = null;
			if(!String.valueOf(inizio).equals(""))
				dataIni = sdf.parse(String.valueOf(inizio));
        
			Date dataFin = null;
			if(!String.valueOf(fine).equals(""))
				dataFin = sdf.parse(String.valueOf(fine));
        
			if(dataIni!=null && item.before(dataIni))
				return false;
			if(dataFin!=null && item.after(dataFin))
				return false;	
		}
		catch (ParseException e) 
		{
			e.printStackTrace();
			return false;
		}
		return true;
    }
}
