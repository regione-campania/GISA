/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.dpat2019.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.aspcfs.modules.dpat2019.base.DpatWrapperSezioniNewBeanAbstract;
import org.json.JSONArray;


public class DpatWrapperSezioniBeanPreCong extends DpatWrapperSezioniNewBeanAbstract<DpatSezioneNewBeanPreCong >  {
	 
	
	public DpatWrapperSezioniBeanPreCong(int anno, Connection conn,boolean nonscaduti, boolean conFigli)
	{
		this.anno = anno;
		PreparedStatement pst = null;
		ResultSet rs = null;
		 

		try
		{
			pst = conn.prepareStatement("select * from dpat_sez_new where anno = ? "+(nonscaduti ? " and data_scadenza is null": "")+" and stato = 1 order by ordinamento,descrizione  ");
			pst.setInt(1, anno);
			rs = pst.executeQuery();
			
			setSezioni(new DpatSezioneNewBeanPreCong().buildList(conn, rs,nonscaduti,conFigli));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			
		}
		finally
		{
			try{pst.close();}catch(Exception ex){}
			try{rs.close();}catch(Exception ex){}
		}
	}
	
	public DpatWrapperSezioniBeanPreCong(){}
	
	@Override
	public int getStatoDopoModifica(Connection db, int anno) throws Exception {
		/*per questi lo stato e' sempre 1*/
		return 1;
	}
	
	
	 
}
