/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.vam;

import it.us.web.bean.vam.Trasferimento;
import it.us.web.bean.vam.TrasferimentoNoH;

import java.util.Comparator;

public class ComparatorTrasferimentiNoH implements Comparator<TrasferimentoNoH>{
 
    @Override
    public int compare(TrasferimentoNoH t1, TrasferimentoNoH t2) 
    {
    	int toReturn = 0;
    	
    	if(t1.getStato().getStatoOrder()>t2.getStato().getStatoOrder())
    		toReturn = -1;
    	else if(t1.getStato().getStatoOrder()==t2.getStato().getStatoOrder())
    	{
    		if(t1.getDataRichiesta().after(t2.getDataRichiesta()))
    			toReturn =  -1;
    		else if(!t1.getDataRichiesta().after(t2.getDataRichiesta()) && !t1.getDataRichiesta().before(t2.getDataRichiesta()))
    			toReturn =  0;
    		else if(t1.getDataRichiesta().before(t2.getDataRichiesta()))
    			toReturn =  1;
    			
    	}
    	else
    		toReturn =  1;
    	
    	return toReturn;
    }
}
