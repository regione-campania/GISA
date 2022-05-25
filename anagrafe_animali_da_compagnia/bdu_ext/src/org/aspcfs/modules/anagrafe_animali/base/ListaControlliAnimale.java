/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.anagrafe_animali.base;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.modules.base.SyncableList;

import java.sql.Timestamp;
import java.util.Vector;


public class ListaControlliAnimale extends Vector implements SyncableList {




	  private static Logger log = Logger.getLogger(org.aspcfs.modules.anagrafe_animali.base.ListaControlliAnimale.class);
	  static {
	    if (System.getProperty("DEBUG") != null) {
	      log.setLevel(Level.DEBUG);
	    }
	  }
	  
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getUniqueField() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setLastAnchor(Timestamp tmp) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLastAnchor(String tmp) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setNextAnchor(Timestamp tmp) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setNextAnchor(String tmp) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setSyncType(int tmp) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setSyncType(String tmp) {
		// TODO Auto-generated method stub
		
	}

	  

	public final static String tableName = "controlli";
	
	
	private int idAnimale = -1;
	private int tipoControllo; //leishmaniosi, ricketsiosi ecc
	private int numeroControllo;
	private java.sql.Timestamp dataPrelievo; //leish_data_prelievo_#
	private java.sql.Timestamp dataAccettazione; //leish_data_accettazione_#
	private int esito; //esito#




	}



