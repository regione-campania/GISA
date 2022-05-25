/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.vam;

import it.us.web.bean.BUtente;
import java.util.Comparator;

public class ComparatorUtenti implements Comparator<BUtente>{
 
    @Override
    public int compare(BUtente u1, BUtente u2) 
    {
    	return u1.toString().toLowerCase().compareTo(u2.toString().toLowerCase());
    }
}
