/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.modules.suap.base.Stabilimento;
import org.aspcfs.utils.web.LookupList;

public class IndirizzoList extends Vector implements SyncableList {
	
	private int idProvincia ;
	private int idComune ;
	private String startComune ;
	
	private int toponimo ;
	
	public String getStartComune() {
		return startComune;
	}
	
	

	public int getToponimo() {
		return toponimo;
	}



	public void setToponimo(int toponimo) {
		this.toponimo = toponimo;
	}



	public void setStartComune(String startComune) {
		this.startComune = startComune;
	}

	public int getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(int idProvincia) {
		this.idProvincia = idProvincia;
	}

	public int getIdComune() {
		return idComune;
	}

	public void setIdComune(int idComune) {
		this.idComune = idComune;
	}

	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUniqueField() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setLastAnchor(Timestamp tmp) {
		// TODO Auto-generated method stub
		
	}

	public void setLastAnchor(String tmp) {
		// TODO Auto-generated method stub
		
	}

	public void setNextAnchor(Timestamp tmp) {
		// TODO Auto-generated method stub
		
	}

	public void setNextAnchor(String tmp) {
		// TODO Auto-generated method stub
		
	}

	public void setSyncType(int tmp) {
		// TODO Auto-generated method stub
		
	}
	
	public void buildList(Connection db)
	{
		int i = 0 ;
		try
		{
			
			LookupList lp = new LookupList(db,"lookup_toponimi");
			PreparedStatement pst = null;
			String sel;
			if(idComune == 5279)
			{
				sel = "select * from public.get_indirizzo_napoli(?,?)";
				pst = db.prepareStatement(sel);
				pst.setInt(1, toponimo);
				pst.setString(2, "%"+startComune+"%");
			}
			else
			{
				sel = "select distinct i.*,comuni1.cod_provincia,comuni1.nome as descrizione_comune,lp.description as descrizione_provincia from opu_indirizzo i " +
						"left join comuni1 on comuni1.id = i.comune " +
						"left join lookup_province lp on lp.code = comuni1.cod_provincia::int " +
						"where 1=1 " ;
				if(idComune>0)
					sel += " and i.comune = ? ";
				if(toponimo>0)
					sel += " and i.toponimo = ? ";
			
				sel += " and via ilike ? order by via limit 50";
				pst = db.prepareStatement(sel);
				if(idComune>0)
					pst.setInt(++i, idComune);
				if (toponimo>0)
					pst.setInt(++i, toponimo);
			
				pst.setString(++i, "%"+startComune+"%");
				}
			
				if(idComune>0)
				{
					ResultSet rs = pst.executeQuery() ;
					while (rs.next())
					{
						Indirizzo ind = new Indirizzo(rs);
						ind.setDescrizioneToponimo(lp.getSelectedValue(ind.getToponimo()));
						this.add(ind);
					}
				}
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	public List<Indirizzo> getLista(List<Indirizzo> lista)
	{
	
		Iterator<Indirizzo> itStab =  this.iterator();
		
		while (itStab.hasNext())
		{
			Indirizzo st = itStab.next();
			if (lista.size()>0)
			{
				for (Indirizzo operatore : lista)
				{
				
					if (st.compareTo(operatore,st)!=0)
					{
						if (!lista.contains(st))
							lista.add(st);
					}
				}
			}
			else
				lista.add(st);
			
		}
		return lista;
	}

	@Override
	public void setSyncType(String tmp) {
		// TODO Auto-generated method stub
		
	}

}
