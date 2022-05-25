/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.noscia.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface DAO 
{
	//Lista tutti i records che soddisfano i filtri impostati nel dao
	//Miglioramenti: si puo' studiare qualcosa per impostare in automatico tutti i parametri della query senza scriverli sempre a mano
	public ArrayList<?> getItems( Connection connection ) throws SQLException;
	
	//Restituisce il primo record della lista se esiste, null altrimenti
	//Da usare quando si effettuano ricerche per chiave primaria che possono restituire un solo valore
	public Object getItem( Connection connection ) throws SQLException;
	
	
}
